<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<head>
    <title>风险区域管理</title>
</head>
<body onload="init()">
<div class="easyui-layout" fit="true">
    <link rel="stylesheet" href="plug-in/Validform/css/divfrom.css" type="text/css">
    <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css">
    <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css">
    <style>
        .editPane {
            position:absolute;top:50px;left:55px;text-align:left;z-index: 2000;border-radius: 4px; color: #ffffff;
            background: transparent ;border: 1px solid transparent;box-shadow: 0 1px 1px rgba(0,0,0,.05);
        }
    </style>
    <script type="text/javascript" src="plug-in/html2canvas/js/html2canvas.js"></script>
    <script type="text/javascript" src="plug-in/html2canvas/js/html2canvas.min.js"></script>
    <script type="text/javascript" src="plug-in/html2canvas/js/canvas2image.js"></script>
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/address/supermap.js"></script>
    <script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
    <script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
    <script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
    <script src="plug-in/supermap/dist/include-classic.js"></script>

    <script>
        var map, vectorLayer, testLayer;
        var curLayerCode = "${initLayerCode}";
        var polygonDrawer, modifyCtrl, selectCtrl,addAddrCtrl;
        var layerSwitcher;
        var vectorLayerStyle = {
            fillColor: "blue",
            fillOpacity: 0.5,
            hoverFillColor: "white",
            hoverFillOpacity: 0.8,
            strokeColor: "#ee9900",
            strokeOpacity: 1,
            strokeWidth: 1,
            strokeLinecap: "round",
            strokeDashstyle: "solid",
            hoverStrokeColor: "red",
            hoverStrokeOpacity: 1,
            hoverStrokeWidth: 0.2,
            pointRadius: 6,
            hoverPointRadius: 1,
            hoverPointUnit: "%",
            pointerEvents: "visiblePainted",
            cursor: "inherit",
            fontColor: "#000000",
            labelAlign: "cm",
            labelOutlineColor: "white",
            labelOutlineWidth: 3
        };
        var layerConfigMap = new Map();
        var addressAreaConfigMap = new Map();
        function init() {
            //1.在指定DOM元素上初始化地图对象。
            map = new SuperMap.Map("map", {
                controls: [
                    new SuperMap.Control.Navigation(),
                    new SuperMap.Control.Zoom(),
                    new SuperMap.Control.ScaleLine(),
                    new SuperMap.Control.MousePosition()
                ], allOverlays: true,
                projection: "EPSG:3857"
            });
            layerSwitcher = new SuperMap.Control.LayerSwitcher();
            /*map.addControl(layerSwitcher);*/
            vectorLayer = new SuperMap.Layer.Vector("区域");
            vectorLayer.style=vectorLayerStyle;

            polygonDrawer = new SuperMap.Control.DrawFeature(vectorLayer, SuperMap.Handler.Polygon);
            polygonDrawer.events.on({"featureadded": polygonDrawCompleted});

            modifyCtrl = new SuperMap.Control.ModifyFeature(vectorLayer);
            modifyCtrl.events.on({"afterfeaturemodified": afterfeaturemodified });
            selectCtrl = new SuperMap.Control.SelectFeature(vectorLayer, {
                onSelect: function (feature) {
                    $.messager.confirm("删除","确定要删除区域“"+feature.attributes.areaName+"”吗？",function(r){
                        if(r){
                            $.ajax({
                                url: "tBAddressSuperMapController.do?doDelSuperMapArea",
                                type: 'POST',
                                data: {id:feature.attributes.id},
                                error: function(){
                                },
                                success: function(data){
                                    data = $.parseJSON(data);
                                    tip(data.msg);
                                    $("#tBAddressInfoList").datagrid("load");
                                    global_lon = centroid.x;
                                    global_lat = centroid.y;
                                    global_addressId = attachAddressId;
                                    needPanTo=1;
                                    return;
                                }
                            });
                        } else  {
                            refreshAddress();
                        }
                    });
                },
                callbacks: {
                    dblclick: function (feature) {
                        global_id = feature.attributes.addressId;
                        global_address = feature.attributes.addressName;
                        switchAddressArea(global_id);
                    }
                },
                hover: false,
                repeat: false
            });
            addAddrCtrl = new SuperMap.Control.SelectFeature(vectorLayer, {
                onSelect: function (feature) {
                    alert(feature.attributes.areaName);
                }
            });

            //各图层配置初始化
            <c:forEach items="${layerList}" var="layerEntity" varStatus="status">
            var layer = new SuperMap.Layer.TiledDynamicRESTLayer("${layerEntity.layerDetailName}", "${layerEntity.url}", {
                transparent: true,
                cacheEnabled: true
            }, {maxResolution: "auto",useCORS: true});
            layerConfigMap.set("${layerEntity.id}",{"center":"${layerEntity.center}","url":"${layerEntity.url}","layerName":"${layerEntity.layerDetailName}","layer":layer,"isLoaded":false});
            </c:forEach>
            setTimeout(function(){
                if(curLayerCode!=''){
                    var layerConfig = layerConfigMap.get(curLayerCode);
                    map.addLayer(layerConfig.layer);
                    map.setCenter(new SuperMap.LonLat(layerConfig.center.split(',')[0],layerConfig.center.split(',')[1]), 0);
                    curLayer = layerConfig.layer;
                }

                map.addControl(polygonDrawer);
                map.addControl(modifyCtrl);
                map.addControl(selectCtrl);
                map.addLayers([vectorLayer]);
                $("div.smMap").css("background-color",'#000000'); //黑色背景
               // $("div.smMap").css("background-color",'#ffffff');   //白色背景
                refreshAddress();
            },1000);

            //各区域配置初始化
            <c:forEach items="${addressAreaList}" var="addressAreaEntity" varStatus="status">
            var addressArea = new SuperMap.Layer.TiledDynamicRESTLayer("${addressAreaEntity.address}", "${addressAreaEntity.spUrl}", {
                transparent: true,
                cacheEnabled: true
            }, {maxResolution: "auto",useCORS: true});
            addressAreaConfigMap.set("${addressAreaEntity.id}",{"center":"${addressAreaEntity.spCenter}","url":"${addressAreaEntity.spUrl}","layerName":"${addressAreaEntity.address}","layer":addressArea,"isLoaded":false});
            </c:forEach>
        }

        function deactiveAll() {
            polygonDrawer.deactivate();
            selectCtrl.deactivate();
            modifyCtrl.deactivate();
            addAddrCtrl.deactivate();
        }
        function drawPolygon() {
            deactiveAll();
            polygonDrawer.activate();
        }
        function modifyFeature() {
            deactiveAll();
            modifyCtrl.activate();
        }
        function selectFeature() {
            deactiveAll();
            selectCtrl.activate();
        }
        function attachPolygon() {
            var rows = $("#tBAddressInfoList").datagrid('getSelections');
            if(rows == null || rows.length <1){
                tip("请选择要关联区域的风险点！");
                return;
            }else if(rows.length > 1){
                tip("每次只能给一个风险点关联区域！");
                return;
            }else{
                var selected = $("#tBAddressInfoList").datagrid('getSelected');
                attachAddressId = selected.id;
            }
            deactiveAll();
            polygonDrawer.activate();
        }

        function polygonDrawCompleted(drawGeometryArgs) {
            polygonDrawer.deactivate();
            selectCtrl.activate();
            var geometry = drawGeometryArgs.feature.geometry;
            vectorLayer.removeFeatures(drawGeometryArgs.feature);

            //保存要素点到服务器数据库
            var pointStr = "";
            var linearRing = geometry.components[0];
            if(linearRing.components.length>51){
                tip("多边形区域不能超过50个点");
                return;
            }
            for(var i=0;i<linearRing.components.length;i++){
                var point = linearRing.components[i];
                pointStr = pointStr + point.x + "," + point.y + ";";
            }
            if(pointStr!=''){
                pointStr = pointStr.substr(0,pointStr.length-1);
            }
            var centroid = geometry.getCentroid();
            if(attachAddressId=="") {   //添加
                add('录入', 'tBAddressSuperMapController.do?goAddSuperMapArea&smLon=' + centroid.x + '&smLat=' + centroid.y + '&pointArray=' + pointStr + "&smBlayer.id=" + curLayerCode, "tBAddressInfoList");
            } else {        //关联
                var msg = "确定要关联该区域吗？";
                $.messager.confirm("关联区域",msg,function(r){
                    if(r){
                        $.ajax({
                            url: "tBAddressInfoController.do?doUpdateCoordinateSp",
                            type: 'POST',
                            data: {addressId:attachAddressId,spLon:centroid.x,spLat:centroid.y,pointStr:pointStr},
                            error: function(){
                            },
                            success: function(data){
                                data = $.parseJSON(data);
                                tip(data.msg);
                                $("#tBAddressInfoList").datagrid("load");
                                global_lon = centroid.x;
                                global_lat = centroid.y;
                                global_addressId = attachAddressId;
                                needPanTo=1;
                                return;
                            }
                        });
                    } else  {
                        refreshAddress();
                    }
                });
            }
        }
        function afterfeaturemodified(drawGeometryArgs){
            modifyCtrl.deactivate();
            var geometry = drawGeometryArgs.feature.geometry;
            //将修改保存到服务器数据库
            var pointStr = "";
            var linearRing = geometry.components[0];
            if(linearRing.components.length>51){
                tip("风险区域不能超过50个点");
                return;
            }
            for(var i=0;i<linearRing.components.length;i++){
                var point = linearRing.components[i];
                pointStr = pointStr + point.x + "," + point.y + ";";
            }
            if(pointStr!=''){
                pointStr = pointStr.substr(0,pointStr.length-1);
            }

            var centroid =  geometry.getCentroid();
            var areaId = drawGeometryArgs.feature.attributes.id;
            var msg = "确定要修改区域吗？";
            $.messager.confirm("修改区域",msg,function(r){
                if(r){
                    add('编辑', 'tBAddressSuperMapController.do?goUpdateSuperMapArea&smLon=' + centroid.x + '&smLat=' + centroid.y + '&pointArray=' + pointStr + "&id=" + areaId, "tBAddressInfoList");
                } else  {
                    refreshAddress();
                }
            });

        }

    </script>

    <div id="searchBtnDiv" style="display: none;">
        <div name="searchColums" id="searchColums">
            <input id="isShowSearchId" value="false" type="hidden" />
            <input id="_sqlbuilder" name="sqlbuilder" type="hidden" />
            <form onkeydown="if(event.keyCode==13){tBAddressInfoListsearch();return false;}" id="tBAddressInfoListForm">
                <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css" />
                <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css" />
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; " title="地点名称">地点名称：</span>
            <input onkeypress="EnterPress(event)" onkeydown="EnterPress()" name="address" style="width: 120px" class="inuptxt" type="text" />
        </span>
                <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="tBAddressInfoListsearch()">
                    查询
                </a>
                <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-reload" onclick="searchReset('tBAddressInfoList')">
                    重置
                </a>
            </form>
        </div>
    </div>
    <div region="center" style="padding: 3px;" class="easyui-panel" id="demopanle">
        <div id="tt" class="easyui-tabs" style="width: auto;" data-options="onSelect:function(t){$('#tt .panel-body').css('width','auto');showRiskList();}">
            <div title="图形化">
                <div class="editPane" id="editPane">
                    <ul id="radioLayers" style="list-style:none;">
                    </ul>
                </div>
                <div id="map" style="position: absolute; left: 0px; right: 0px; width: auto; height: 90%;"></div>
            </div>
            <div title="关联的风险">
                <%--仅展示数据，不做关联操作--%>
                <iframe  id="riskList" src='' style="height: 92%;width: 100%;" frameborder="0"></iframe>
            </div>
            <%--
            <div title="关联的单位">
                仅展示数据，不做关联操作
                <iframe  id="addressDepartRelList" src='' style="height: 92%;width: 100%;" frameborder="0"></iframe>
            </div>
            --%>
        </div>

    </div>
    <div region="west" style="width: 500px;" title="地点列表"  split="true">
        <t:datagrid name="tBAddressInfoList" checkbox="true" onDblClick="locationAddress" onClick="initAddressId" fitColumns="false" title="" actionUrl="tBAddressInfoController.do?datagrid" idField="id" fit="true" queryMode="group" onLoadSuccess="loadSuccessed">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险点"  field="address"  query="false"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="类型"  field="cate"  query="false" hidden="true" dictionary="locationCate"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="关联风险数量" field="dangerSourceCount" url="riskIdentificationController.do?fxList&addressId={id}&load=${load}&expDate=true" width="80"></t:dgCol>
            <t:dgCol title="是否显示"  field="isshow" dictionary="sf_yn"   queryMode="group"  width="80"></t:dgCol>
            <t:dgCol title="分管责任人"  field="manageMan" queryMode="single"  width="120" hidden="true"></t:dgCol>
            <t:dgCol title="日期是否完整"  field="isAll"  replace="是_0,否_1" queryMode="single"  width="120" ></t:dgCol>
            <t:dgCol title="位置描述"  field="description"  hidden="true" query="false"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="经度"  field="lon"   hidden="true"  queryMode="group"  width="80"></t:dgCol>
            <t:dgCol title="纬度"  field="lat"  hidden="true"   queryMode="group"  width="80"></t:dgCol>

            <t:dgCol title="superMap图层"   field="belongLayer.id"      queryMode="group"  width="120" hidden="true" ></t:dgCol>

            <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="是否删除"  field="isDelete"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <c:if test="${ewmgn eq true}">
                <t:dgCol title="二维码" field="opt" width="80"></t:dgCol>
                <t:dgDefOpt url="tBAddressInfoController.do?showOrDownByurl&id={id}" urlclass="ace_button" urlfont="fa-trash-o" title="下载"></t:dgDefOpt>
            </c:if>


            <t:dgToolBar title="录入" operationCode="add" icon="icon-add" url="tBAddressInfoController.do?goAdd" funname="addAddress"></t:dgToolBar>
            <t:dgToolBar title="编辑" operationCode="update" icon="icon-edit" url="tBAddressInfoController.do?goUpdate" funname="update" width="700" height="500"></t:dgToolBar>
            <t:dgToolBar title="更改坐标" operationCode="editCoordinate" icon="icon-edit" url="tBAddressInfoController.do?goUpdate" funname="editCoordinate"></t:dgToolBar>
            <t:dgToolBar title="删除"  icon="icon-remove" url="tBAddressInfoController.do?doBatchDel" operationCode="delete" funname="deleteALLSelect"></t:dgToolBar>
            <%--<t:dgToolBar title="导出风险" icon="icon-putout"  funname="exportRelatedRisks" operationCode="export"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" operationCode="detail" icon="icon-search" url="tBAddressInfoController.do?goDetail" funname="detail" width="700" height="500" ></t:dgToolBar>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export" ></t:dgToolBar>
            <c:if test="${isSunAdmin != 'YGADMIN'}">
                <t:dgToolBar title="全屏" icon="icon-full-screen" funname="showFullScreen" ></t:dgToolBar>
            </c:if>
            <c:if test="${isSunAdmin == 'YGADMIN'}">
                <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
            </c:if>
        </t:datagrid>
    </div>
</div>
</body>
<script type="text/javascript">
    var global_id, global_address, currIndex= 0, addRelFlag= 0;
    var needPanTo=0,global_lon, global_lat,global_addressId;
    var curBelongAddressId="";
    var curLayer;
    var cursorHand = "default";
    var attachAddressId = "";//要关联区域的风险点的ID
    function loadSuccessed(){
        if(addRelFlag==1){
            addRelFlag = 0;
            return;
        }
        refreshAddress();
    }

    function address(){
        alert("abc");
    }
    function mapAddAddressListener(e){
        map.events.un({"click": mapAddAddressListener});
        map.div.style.cursor=cursorHand;
        var lonLatPoint = map.getLonLatFromPixel(new SuperMap.Pixel(e.xy.x,e.xy.y));
        add('录入', 'tBAddressInfoController.do?goAdd&lon=' + lonLatPoint.lon + "&lat=" + lonLatPoint.lat+"&belongLayer.id="+curLayerCode, "tBAddressInfoList",'700','500');
    }
    function addAddress() {
        cursorHand = map.div.style.cursor;
        map.div.style.cursor="crosshair";
        map.events.un({"click": mapAddAddressListener});
        map.events.on({"click": mapAddAddressListener});
    }

    function mapEditAddressListener(e){
        map.events.un({"click": mapEditAddressListener});
        map.div.style.cursor=cursorHand;
        var lonLatPoint = map.getLonLatFromPixel(new SuperMap.Pixel(e.xy.x,e.xy.y));
        var selected = $("#tBAddressInfoList").datagrid('getSelected');
        var addressId = "";
        if(selected){
            addressId = selected.id;
            var lng = lonLatPoint.lon;
            var lat = lonLatPoint.lat;
            var msg = "确定要将\"" + selected.address + "\"的坐标由(" + selected.lon + "，" + selected.lat + ")更改为(" + lng + "，" + lat + ")吗？";
            $.messager.confirm("更改坐标",msg,function(r){
                if(r){
                    $.ajax({
                        /*url: "tBAddressInfoController.do?doUpdate",
                        type: 'POST',
                        data: {id:addressId,smLon:lng,smLat:lat,smLayerId:curLayerCode},*/
                        url: "tBAddressInfoController.do?doUpdateCoordinate",
                        type: 'POST',
                        data: {addressId:addressId,lng:lng,lat:lat,layerID:curLayerCode},
                        error: function(){
                        },
                        success: function(data){
                            data = $.parseJSON(data);
                            tip(data.msg);
                            $("#tBAddressInfoList").datagrid("load");
                            global_lon = lng;
                            global_lat = lat;
                            global_addressId=addressId;
                            needPanTo=1;
                        }
                    });
                }
            });
        }

    }
    function editCoordinate(){
        var rows = $("#tBAddressInfoList").datagrid('getSelections');
        if(rows == null || rows.length <1){
            tip("请选择要更改坐标的地点！");
            return;
        }else if(rows.length > 1){
            tip("每次只能更改一个地点的坐标！");
            return;
        }else{
            cursorHand = map.div.style.cursor;
            map.div.style.cursor="crosshair";
            map.events.un({"click": mapEditAddressListener});
            map.events.on({"click": mapEditAddressListener});
        }
    }

    //定位
    function locationAddress(rowIndex,rowData){
        alert('locationAddress');
        debugger;
        var belongLayer = rowData["belongLayer.id"];
        if(belongLayer==undefined || belongLayer==''){
            return;
        }
        global_lon = rowData.lon;
        global_lat = rowData.lat;
        global_addressId=rowData.id;
        needPanTo = 1;
        if(belongLayer!=curLayerCode) {
            switchLayer(belongLayer);
        } else {
            changeSelectDivColor(rowData.id);
            map.panTo(new SuperMap.LonLat(rowData.lon,rowData.lat));
        }
        changeSelectDivColor(rowData.id)
    }

    //改变双击选中的地点的颜色
    var previousPoint;
    function changeSelectDivColor(addressId) {
        //将前一个地点的颜色重置为粉红色
        if(previousPoint!=null && previousPoint!=""){
            previousPoint.css("backgroundColor","#EE5D5B");
        }
        var thisPoint = $("#"+addressId+"_contentDiv div:first");
        previousPoint = thisPoint;
        thisPoint.css("backgroundColor","#27a1ff");
    }



    //删除操作
    /**
     * 执行操作
     *
     * @param url
     * @param index
     */
    function doSubmit1(url, name, data) {
        gridname = name;
        var paramsData = data;
        if (!paramsData) {
            paramsData = new Object();
            if (url.indexOf("&") != -1) {
                var str = url.substr(url.indexOf("&") + 1);
                url = url.substr(0, url.indexOf("&"));
                var strs = str.split("&");
                for (var i = 0; i < strs.length; i++) {
                    paramsData[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
                }
            }
        }
        $.ajax({
            async: false,
            cache: false,
            type: 'POST',
            data: paramsData,
            url: url,// 请求的action路径
            error: function () {// 请求失败处理函数
            },
            success: function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    var msg = d.msg;
                    tip(msg);
                    reloadTable();
                    refreshAddress();
                }
            }
        });


    }

    /**
     * 创建询问窗口
     *
     * @param title
     * @param content
     * @param url
     */
    function createdialog1(title, content, url, name) {
        $.dialog.confirm(content, function () {
            doSubmit1(url, name);
            rowid = '';
        }, function () {
        }).zindex();
    }

    //删除调用函数
    function delObj1(id) {
        var url = "tBAddressInfoController.do?doDel&id=" + id;
        gridname = "tBAddressInfoList";
        createdialog1('停用确认 ', '确定停用该地点吗？', url, gridname);
    }

    //地点分布显示
    function showFullScreen(){
        /*var url ="tBAddressInfoController.do?managelist_supermap";
        createdetailwindow('风险区域',url,"100%","100%");*/
        window.open("tBAddressInfoController.do?managelist_supermap");
    }


    //经纬度保留两位小数
    function numFormatter(value, rec, index) {
        if(undefined==value || value=='')
            return '';
        else
            return parseFloat(value).toFixed(2);
    }

    $(function () {
        var fullScreen = "${fullScreen}";
        if(fullScreen == "full"){
            $("#fullScreenDiv").css("display","none");
            $("#editPane").css("top","20")
            $("#map").css("height","100%");
        }
        $(".datagrid-toolbar").css("height","auto");
        $(".datagrid-toolbar span").css("float","none");

        <c:forEach items="${layerList}" var="layerEntity" varStatus="status">
        var layerName = "${layerEntity.layerDetailName}";
        var id = "${layerEntity.id}";
        var li = "<li><input type='radio' value='" + id + "' name='selectedLayer' onclick='switchLayer(\""+id+"\");'" + (id=="${initLayerCode}"?" checked":"") + ">" + layerName + "</li>";
        $("#radioLayers").append(li);
        </c:forEach>
        //deactiveAll();
    });

    function switchLayer(dstLayerCode){
        //var selectedLayer = $("input[name='selectedLayer']:checked").val();
        $("#radioLayers").show();
        if(curLayerCode==dstLayerCode && curBelongAddressId==''){
            return;
        }

        map.removeControl(polygonDrawer);
        map.removeControl(modifyCtrl);
        map.removeControl(selectCtrl);
        map.removeLayer(vectorLayer);
        if(undefined!=curLayer){
            map.removeLayer(curLayer);
        }

        var dstLayerConfig = layerConfigMap.get(dstLayerCode);
        map.addLayer(dstLayerConfig.layer);
        map.setCenter(new SuperMap.LonLat(dstLayerConfig.center.split(',')[0], dstLayerConfig.center.split(',')[1]), 0);
        map.addLayers([vectorLayer]);
        map.addControl(polygonDrawer);
        map.addControl(modifyCtrl);
        map.addControl(selectCtrl);
        curLayerCode = dstLayerCode;
        curLayer = dstLayerConfig.layer;
        curBelongAddressId = "";
        $("input[name='selectedLayer'][value='"+dstLayerCode+"']").prop("checked","checked");
        map.zoomIn();
        map.zoomOut();
        refreshAddress();
    }

    function switchAddressArea(dstAddressId){
        var dstAddressAreaConfig = addressAreaConfigMap.get(dstAddressId);
        if(undefined==dstAddressAreaConfig){
            return;
        }

        map.removeControl(polygonDrawer);
        map.removeControl(modifyCtrl);
        map.removeControl(selectCtrl);
        map.removeLayer(vectorLayer);
        if(undefined!=curLayer){
            map.removeLayer(curLayer);
        }

        map.addLayer(dstAddressAreaConfig.layer);
        map.setCenter(new SuperMap.LonLat(dstAddressAreaConfig.center.split(',')[0], dstAddressAreaConfig.center.split(',')[1]), 0);
        map.addLayers([vectorLayer]);
        map.addControl(polygonDrawer);
        map.addControl(modifyCtrl);
        map.addControl(selectCtrl);
        curLayer = dstAddressAreaConfig.layer;
        curBelongAddressId = dstAddressId;
        refreshAddress();
        $("#radioLayers").hide();
    }
    function refreshAddress(){
        deactiveAll();
        //selectFeature();
        var pro=window.top.$.messager;
        if(pro){
            window.top.$.messager.progress({
                text : '正在加载数据....',
                interval : 300
            });
        }
        var url = "tBAddressInfoController.do?getAllAddressList";
        $.ajax({
            type : 'POST',
            url : url,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    if(pro)
                        window.top.$.messager.progress('close');
                    while(map.popups.length>0){
                        var popup = map.popups[0];
                        map.removePopup(popup);
                    }
                    $(d.attributes.addressList).each(function(index, d) {
                        var txt = d.address ;
                        var lon = d.lon;
                        var lat = d.lat;
                        var id = d.id;
                        var belongLayer = d.belongLayer;
                        if(belongLayer == null){
                            return;
                        }else if(belongLayer.id != curLayerCode){
                            return;
                        }
                        var contentHTML = "<div style='position: absolute; background-color:#EE5D5B; border: 1px solid rgb(188, 59, 58); color: black; " +
                            "left: -10px; top: -32px; height: 18px; padding: 2px; line-height: 18px; white-space: nowrap; '><span>";
                        contentHTML += txt;
                        contentHTML += "</span><div name='arrow' style=\"background: rgba(0, 0, 0, 0) url('plug-in/baidumap/image/label.png') no-repeat scroll 0px 0px; position: absolute; width: 11px; height: 10px; top: 22px; left: 10px; overflow: hidden;\"></div></div>";
                        popup = new SuperMap.Popup(
                            id,
                            new SuperMap.LonLat(lon,lat),
                            /*new SuperMap.Size(200, 30),*/
                            null,
                            contentHTML,
                            true,
                            null
                        );
                        map.addPopup(popup);
                        $("#"+id).css('background','none');
                        $("#"+id+"_close").remove();
                        $("#"+id).css("width","auto");
                        $("#"+id).css("height","auto");
                        $("#"+id+"_contentDiv").css("width","auto");
                        $("#"+id+"_contentDiv").css("height","auto");
                        $("#"+id+"_contentDiv").css("overflow","visible");
                        $("#"+id+"_contentDiv div:first").mouseenter(function(){
                            this.style.backgroundColor = "#6BADCA";
                            this.style.borderColor = "#0000ff";
                            $(this).find("[name='arrow']").css("background-position","0px -20px");

                        });
                        $("#"+id+"_contentDiv div:first").mouseleave(function(){
                            this.style.backgroundColor = "#EE5D5B";
                            this.style.borderColor = "#BC3B3A";
                            $(this).find("[name='arrow']").css("background-position","0px 0px");
                        });
                        $("#"+id+"_contentDiv div:first").click(function(){
                            global_id = id;
                            global_address = txt;
                            tabIndex=1;
                            $('#tt').tabs('select',1);
                            reloadDangerSourceList(global_id, global_address);
                        });

                    });

                    if(needPanTo==1){
                        needPanTo=0;
                        map.panTo(new SuperMap.LonLat(global_lon,global_lat));
                    }
                }
            },
            error:function(){
            }
        });
        deactiveAll();
    }

</script>
<script>
    var tabIndex=0;

    function exportRelatedRisks(){
        var rows=$("#tBAddressInfoList").datagrid('getSelections');
        if(rows==0){
            tip("请勾选要导出的关联风险");
        }else if(rows.length>=1){
            var idsTemp=new Array();
            for(var i=0;i<rows.length;i++){
                idsTemp.push(rows[i].id);
            }
            var idt=idsTemp.join(",");
            $.dialog.confirm("是否确认导出"+idsTemp.length+"条记录？",function(){
                JeecgExcelExport("tBDangerSourceController.do?exportRelatedRisks&id="+idt+"&lec=${initParam.les}","tBAddressInfoList");
            })
        }
    }
    function sunHidden() {
        var rows = $("#tBAddressInfoList").datagrid('getSelections');
        if (rows.length < 1) {
            tip("请选择需要隐藏的数据");
        } else {
            var idsTemp = new Array();
            for (var i = 0; i < rows.length; i++) {
                idsTemp.push(rows[i].id);
            }
            var idt = idsTemp.join(",");

            $.ajax({
                type: 'POST',
                url: 'tBAddressInfoController.do?sunshine',
                dataType:"json",
                async:true,
                cache: false,
                data: {
                    ids:idt
                },
                success:function(data){
                    var msg = data.msg;
                    tip(msg);
                    reloadTable();
                },
                error:function(data){
                }
            });
        }
    }
</script>
<script type="text/javascript">
    var global_id, global_address, currIndex= 0, addRelFlag= 0, tabIndex=0;
    var needPanTo=0,global_lon, global_lat;
    var cursorHand = "default";

    function showRiskList(){
        var tab = $('#tt').tabs('getSelected');
        var index = $('#tt').tabs('getTabIndex',tab);
        if(tabIndex==index){
            return;
        }
        tabIndex=index;
        if(global_id && global_address){
            reloadDangerSourceList(global_id, global_address);
        }
    }
    function initAddressId(rowIndex,rowData){
        global_id = rowData.id;
        global_address = rowData.address;
        reloadDangerSourceList(global_id, global_address);
    }
    function reloadDangerSourceList(id, address){
        var tab = $('#tt').tabs('getSelected');
        var index = $('#tt').tabs('getTabIndex',tab);
        if(index==1){
            $("#riskList").attr("src", "<%=request.getContextPath()%>/riskIdentificationController.do?fxList&addressId="+id+"&expDate=true");
        } else if(index==2){
            $("#addressDepartRelList").attr("src", "<%=request.getContextPath()%>/tBAddressInfoController.do?addressDepartRelList&addressId="+id);
        }
    }

    //定位
    function locationAddress(rowIndex,rowData){
        map.panTo(new SuperMap.LonLat(rowData.lon,rowData.lat));
        changeSelectDivColor(rowIndex,rowData);
    }

    //改变双击选中的地点的颜色
    var previousPoint;
    function changeSelectDivColor(rowIndex,rowData) {
        //将前一个地点的颜色重置为粉红色
        if(previousPoint!=null && previousPoint!=""){
            previousPoint.css("backgroundColor","#EE5D5B");
        }
        var thisPoint = $("#"+rowData.id+"_contentDiv div:first");
        previousPoint = thisPoint;
        thisPoint.css("backgroundColor","#27a1ff");

    }


    //导出
    function ExportXls() {

        var rows = $("#tBAddressInfoList").datagrid('getSelections');
        if (rows.length == 0) {
            JeecgExcelExport("tBAddressInfoController.do?exportXls", "tBAddressInfoList");
        } else if (rows.length >=1) {
            var idsTemp = new Array();
            for (var i = 0; i < rows.length; i++) {
                idsTemp.push(rows[i].id);
            }
            var idt = idsTemp.join(",");
            $.dialog.confirm("是否确认导出" + idsTemp.length + "条记录？", function () {
                JeecgExcelExport("tBAddressInfoController.do?exportXls&ids=" + idt, "tBAddressInfoList");
            });
        }
    }

    function loadSuccessed(){
        if(addRelFlag==1){
            addRelFlag = 0;
            return;
        }

        $("#tBAddressInfoListtb").prepend($("#searchBtnDiv").html());
        $("#searchBtnDiv").empty();
        refreshAddress();
        tabIndex=0;
        $('#tt').tabs('select',0);
        $('#riskList').contents().find("body").html("");
        $('#addressDepartRelList').contents().find("body").html("");
    }
    window.top["tip_addressDangerSourceList"]=function(){
        tip(this.msg);
    };
    window.top["reload_addressDangerSourceList"]=function(){
        addRelFlag=1;
        $('#tBAddressInfoList').datagrid('reload');
        $("#riskList")[0].contentWindow.tBDangerSourceListsearch();
    };
    window.top["reload_tBAddressInfoList"]=function(){
        addRelFlag=1;
        $('#tBAddressInfoList').datagrid('reload');
    };

    /**
     *  阳光账号隐藏数据功能
     * */

    (function(){
        $(".datagrid-toolbar").css("height","auto");
        $(".datagrid-toolbar span").css("float","none");
    })();
</script>
