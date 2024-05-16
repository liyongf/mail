<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<link rel="stylesheet" href="plug-in/Validform/css/divfrom.css" type="text/css">
<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css">
<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css">
<script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
<script src="plug-in/supermap/dist/include-classic.js"></script>
<script type="text/javascript" src="plug-in/html2canvas/js/html2canvas.js"></script>
<script type="text/javascript" src="plug-in/html2canvas/js/html2canvas.min.js"></script>
<script type="text/javascript" src="plug-in/html2canvas/js/canvas2image.js"></script>
<script type="text/javascript" src="webpage/com/sdzk/buss/web/riskalert/js/supermap.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.jqprint.js"></script>
<style>
    .editPane {
        position:absolute;top:40px;left:50px;text-align:left;z-index: 1000;border-radius: 4px; color: #ffffff;
        background: transparent ;border: 1px solid transparent;box-shadow: 0 1px 1px rgba(0,0,0,.05);
    }
</style>
<script>
    var map, vectorLayer, testLayer;
    var curLayerCode = "${initLayerCode}";
    var polygonDrawer, modifyCtrl, selectCtrl;
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
    function init() {
        //1.在指定DOM元素上初始化地图对象。
        map = new SuperMap.Map("map", {
            controls: [
                new SuperMap.Control.Navigation(),
                new SuperMap.Control.Zoom(),
//                new SuperMap.Control.LayerSwitcher(),
                new SuperMap.Control.ScaleLine(),
                new SuperMap.Control.MousePosition()
            ], allOverlays: true,
            projection: "EPSG:3857"
        });
        layerSwitcher = new SuperMap.Control.LayerSwitcher();
        map.addControl(layerSwitcher);
        vectorLayer = new SuperMap.Layer.Vector("polygonLayer");
        vectorLayer.style=vectorLayerStyle;

        polygonDrawer = new SuperMap.Control.DrawFeature(vectorLayer, SuperMap.Handler.Polygon);
        polygonDrawer.events.on({"featureadded": polygonDrawCompleted});

        modifyCtrl = new SuperMap.Control.ModifyFeature(vectorLayer);
        modifyCtrl.events.on({"afterfeaturemodified": afterfeaturemodified });
        selectCtrl = new SuperMap.Control.SelectFeature(vectorLayer, {
            onSelect: function (feature) {
            },
            callbacks: {
                dblclick: function (feature) {

                }
            },
            hover: false,
            repeat: false
        });

        //各图层配置初始化
        <c:forEach items="${layerList}" var="layerEntity" varStatus="status">
            var layer = new SuperMap.Layer.TiledDynamicRESTLayer("${layerEntity.layerDetailName}", "${layerEntity.url}", {
            transparent: true,
            cacheEnabled: true
            }, {maxResolution: "auto",useCORS: true});
            layerConfigMap.set("${layerEntity.layerCode}",{"center":"${layerEntity.center}","url":"${layerEntity.url}","layerName":"${layerEntity.layerDetailName}","layer":layer,"isLoaded":false});
        </c:forEach>
        setTimeout(function(){
//            layerConfigMap.forEach(function (value, key) {
//                map.addLayer(value.layer);
//                if(key==curLayerCode){
//                    map.setCenter(new SuperMap.LonLat(value.center.split(',')[0],value.center.split(',')[1]), 0);
//                } else {
//                    value.layer.setVisibility(false);
//                }
//            });
            if(curLayerCode!=''){
                var layerConfig = layerConfigMap.get(curLayerCode);
                map.addLayer(layerConfig.layer);
                map.setCenter(new SuperMap.LonLat(layerConfig.center.split(',')[0],layerConfig.center.split(',')[1]), 0);
            }
            map.addLayers([vectorLayer]);
            map.addControl(polygonDrawer);
            map.addControl(modifyCtrl);
            map.addControl(selectCtrl);
            refreshAddress();
            $("div.smMap").css("background-color",'#000000'); //黑色背景
            //$("div.smMap").css("background-color",'#ffffff');   //白色背景
        },1000);
    }

    function deactiveAll() {
        polygonDrawer.deactivate();
        selectCtrl.deactivate();
        modifyCtrl.deactivate();
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

    function polygonDrawCompleted(drawGeometryArgs) {
        polygonDrawer.deactivate();
        selectCtrl.activate();
        var geometry = drawGeometryArgs.feature.geometry;
        vectorLayer.removeFeatures(drawGeometryArgs.feature);

        //保存要素点到服务器数据库
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
        add('录入', 'tBAddressInfoController.do?goAdd&lon=' + centroid.x + '&lat=' + centroid.y + '&pointStr=' +pointStr+"&belongLayer="+curLayerCode, "tBAddressInfoList");
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
        var addressId = drawGeometryArgs.feature.attributes.addressId;
        var msg = "确定要修改区域吗？";
        $.messager.confirm("修改区域",msg,function(r){
            if(r){
                $.ajax({
                    url: "tBAddressInfoController.do?doUpdateCoordinate",
                    type: 'POST',
                    data: {addressId:addressId,lng:centroid.x,lat:centroid.y,pointStr:pointStr},
                    error: function(){
                    },
                    success: function(data){
                        data = $.parseJSON(data);
                        tip(data.msg);
                        $("#tBAddressInfoList").datagrid("load");
                        global_lon = centroid.x;
                        global_lat = centroid.y;
                        global_addressId = addressId;
                        needPanTo=1;
                        return;
                    }
                });
            } else  {
                refreshAddress();
            }
        });

    }

</script>
<body onload="init()">
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
<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 3px;" class="easyui-panel" id="demopanle">
        <div id="tt" class="easyui-tabs" style="width: auto;" data-options="onSelect:function(t){$('#tt .panel-body').css('width','auto');showRiskList();}">
            <div title="图形化">
                <div class="editPane">
                    <ul id="radioLayers">
                    </ul>
                </div>
                <div id="map" style="position: absolute; left: 0px; right: 0px; width: auto; height: 90%;"></div></div>
            <div title="关联的风险">
                <iframe  id="riskList" src='' style="height: 92%;width: 100%;" frameborder="0"></iframe>
            </div>
            <div title="关联的单位">
                <iframe  id="addressDepartRelList" src='' style="height: 92%;width: 100%;" frameborder="0"></iframe>
            </div>
        </div>
    </div>
    <div region="west" style="width: 500px;" title="地点列表"  split="true">
        <t:datagrid name="tBAddressInfoList" checkbox="true" onDblClick="locationAddress" onClick="initAddressId" fitColumns="false" title="" actionUrl="tBAddressInfoController.do?datagrid" idField="id" fit="true" queryMode="group" onLoadSuccess="loadSuccessed">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="煤层"  field="belongLayer" hidden="true" dictionary="layer" query="false"  width="120"></t:dgCol>
            <t:dgCol title="风险点"  field="address"  query="false"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="类型"  field="cate"  query="false" hidden="true" dictionary="locationCate"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="关联风险数量" field="dangerSourceCount" queryMode="group" width="80"></t:dgCol>
            <t:dgCol title="是否显示"  field="isshow" dictionary="sf_yn" queryMode="group"  width="80"></t:dgCol>
            <t:dgCol title="分管领导"  field="manageMan" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="位置描述"  field="description"  hidden="true" query="false"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="经度"  field="lon"   hidden="true"  queryMode="group"  width="80"></t:dgCol>
            <t:dgCol title="纬度"  field="lat"  hidden="true"   queryMode="group"  width="80"></t:dgCol>
            <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="是否删除"  field="isDelete"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
            <t:dgToolBar title="录入" operationCode="add" icon="icon-add" url="tBAddressInfoController.do?goAdd" funname="addAddress"></t:dgToolBar>
            <t:dgToolBar title="编辑" operationCode="update" icon="icon-edit" url="tBAddressInfoController.do?goUpdate" funname="update"></t:dgToolBar>
            <t:dgToolBar title="更改坐标" operationCode="editCoordinate" icon="icon-edit" url="tBAddressInfoController.do?goUpdate" funname="editCoordinate"></t:dgToolBar>
            <t:dgToolBar title="删除"  icon="icon-remove" url="tBAddressInfoController.do?doBatchDel" operationCode="delete" funname="deleteALLSelect"></t:dgToolBar>
            <t:dgToolBar title="导出风险" icon="icon-putout"  funname="exportRelatedRisks" operationCode="export"></t:dgToolBar>
            <t:dgToolBar title="查看" operationCode="detail" icon="icon-search" url="tBAddressInfoController.do?goDetail" funname="detail"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export" ></t:dgToolBar>
            <%--<t:dgToolBar title="上报集团" operationCode="detail" icon="icon-search" url="tBAddressInfoController.do?reportGroup" funname="reportGroup"></t:dgToolBar>--%>
            <t:dgToolBar title="克隆" operationCode="clone" icon="icon-search" funname="cloneAddress"></t:dgToolBar>
            <c:if test="${isSunAdmin != 'YGADMIN'}">
                <t:dgToolBar title="全屏" icon="icon-full-screen" funname="showFullScreen" operationCode="fullScreen"></t:dgToolBar>
            </c:if>
            <c:if test="${isSunAdmin == 'YGADMIN'}">
                <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
            </c:if>
            <t:dgToolBar title="绘制风险区域" operationCode="drawPolygon" icon="icon-add" funname="drawPolygon"></t:dgToolBar>
            <t:dgToolBar title="编辑风险区域" operationCode="modifyFeature" icon="icon-edit" funname="modifyFeature"></t:dgToolBar>
            <%--<t:dgToolBar title="选择" operationCode="selectFeature" icon="icon-edit" funname="selectFeature"></t:dgToolBar>--%>
            <t:dgToolBar title="打印" operationCode="print" icon="icon-print" funname="printMap"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
</body>
<script type="text/javascript">
    var global_id, global_address, currIndex= 0, addRelFlag= 0, tabIndex=0;
    var needPanTo=0,global_lon, global_lat,global_addressId;
    var cursorHand = "default";
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
            $("#riskList").attr("src", "<%=request.getContextPath()%>/tBDangerSourceController.do?addressDangerSourceList&addressId="+id+"&addressName="+encodeURI(encodeURI(address)));
        } else if(index==2){
            $("#addressDepartRelList").attr("src", "<%=request.getContextPath()%>/tBAddressInfoController.do?addressDepartRelList&addressId="+id);
        }
    }

    function mapAddAddressListener(e){
        map.events.un({"click": mapAddAddressListener});
        map.div.style.cursor=cursorHand;
        var lonLatPoint = map.getLonLatFromPixel(new SuperMap.Pixel(e.xy.x,e.xy.y));
        debugger
        add('录入', 'tBAddressInfoController.do?goAdd&lon=' + lonLatPoint.lon + "&lat=" + lonLatPoint.lat + "&belongLayer="+curLayerCode, "tBAddressInfoList");
    }
    function addAddress() {
        tabIndex=0;
        $('#tt').tabs('select',0);
        //map.setDefaultCursor("crosshair");
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
                        url: "tBAddressInfoController.do?doUpdateCoordinate",
                        type: 'POST',
                        data: {addressId:addressId,lng:lng,lat:lat,belongLayer:curLayerCode},
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
            tabIndex=0;
            $('#tt').tabs('select',0);
            cursorHand = map.div.style.cursor;
            map.div.style.cursor="crosshair";
            map.events.un({"click": mapEditAddressListener});
            map.events.on({"click": mapEditAddressListener});
        }
    }

    function refreshAddress(){
        deactiveAll();
        selectFeature();
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
            async: false,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    if(pro)
                        window.top.$.messager.progress('close');
                    while(map.popups.length>0){
                        var popup = map.popups[0];
                        map.removePopup(popup);
                    }

                    vectorLayer.removeAllFeatures();
                    $(d.attributes.addressList).each(function(index, d) {
                        var txt = d.address ;
                        var lon = d.lon;
                        var lat = d.lat;
                        var id = d.id;
                        var belongLayer = d.belongLayer;
                        if(belongLayer!=curLayerCode){
                            return true;
                        }

                        //绘制多边形
                        var pointStr = d.pointStr;
                        if(pointStr!=null && pointStr!=''){
                            var pointArr = pointStr.split(";");
                            var points = [];
                            for (var i = 0, len = pointArr.length; i < len; i++) {
                                thisPointArr = pointArr[i].split(',');
                                var point = new SuperMap.Geometry.Point(thisPointArr[0], thisPointArr[1]);
                                points.push(point);
                            }
                            var linearRing = new SuperMap.Geometry.LinearRing(points);
                            var polygon = new SuperMap.Geometry.Polygon([linearRing]);
                            var polygon_feature = new SuperMap.Feature.Vector(polygon);

                            polygon_feature.attributes.addressId=id;
                            polygon_feature.attributes.addressName=txt;
                            vectorLayer.addFeatures([polygon_feature]);
                        }

                        //显示风险点名称
                        var contentHTML = "<div class='"+curLayerCode+"' style='position: absolute; background-color:#EE5D5B; border: 1px solid rgb(188, 59, 58); color: black; " +
                                "left: -10px; top: -32px; height: 18px; padding: 2px; line-height: 18px; white-space: nowrap; '><span>";
                        contentHTML += txt;
                        contentHTML += "</span><div name='arrow' style=\"background: rgba(0, 0, 0, 0) url('plug-in/baidumap/image/label.png') no-repeat scroll 0px 0px; position: absolute; width: 11px; height: 10px; top: 22px; left: 10px; overflow: hidden;\"></div></div>";
                        popup = new SuperMap.Popup(
                                id,
                                new SuperMap.LonLat(lon,lat),
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
                        changeSelectDivColor(global_addressId);
                    }
                }
            },
            error:function(){
            }
        });
    }

    function checkColor(obj) {
        if(obj=="undefined" || obj==null){
            return "#D3D3D3";
        }
        if (obj.indexOf("#") > -1) {
            return obj;
        } else {
            return "#fffded";
        }
    }
    //定位
    function locationAddress(rowIndex,rowData){
        var belongLayer = rowData.belongLayer;
        if(belongLayer=='undefined' || belongLayer==''){
            return;
        }
        if(belongLayer!=curLayerCode) {
            /**/
            global_lon = rowData.lon;
            global_lat = rowData.lat;
            global_addressId=rowData.id;
            needPanTo = 1;
            switchLayer(belongLayer);
        } else {
            map.panTo(new SuperMap.LonLat(rowData.lon,rowData.lat));
            changeSelectDivColor(rowData.id);
        }
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


    //导出
    function ExportXls() {
        JeecgExcelExport("tBAddressInfoController.do?exportXls","tBAddressInfoList");
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

    function reportGroup(){
        var selected = $("#tBAddressInfoList").datagrid('getSelections');
        if(selected != null && selected.length <1){
            tip("请选择要是上报的条目");
        }else{
            var ids = new Array();

            for (var i = 0; i < selected.length; i++) {
                ids.push(selected[i].id);
            }
            ids.join(',');
            $.ajax({
                url: "tBAddressInfoController.do?reportGroup&ids="+ids+"&tt="+new Date(),
                type: 'POST',
                error: function(){
                },
                success: function(data){
                    data = $.parseJSON(data);
                    tip(data.msg);
                }
            });
        }
    }

    function cloneAddress(){
        var selected = $("#tBAddressInfoList").datagrid('getSelections');
        if(selected != null && selected.length !=1){
            tip("请选择要克隆的一条条目");
        }else{
            createwindow("克隆风险点","tBAddressInfoController.do?goClone&id="+selected[0].id,700,400);
        }

    }

    function showFullScreen(){
        var url ="tBAddressInfoController.do?managelist";
        createdetailwindow('风险区域',url,"100%","100%");
    }

    /**
     *  阳光账号隐藏数据功能
     * */
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
    (function(){
        $(".datagrid-toolbar").css("height","auto");
        $(".datagrid-toolbar span").css("float","none");

        <c:forEach items="${layerList}" var="layerEntity" varStatus="status">
            var layerName = "${layerEntity.layerDetailName}";
            var layerCode = "${layerEntity.layerCode}";
            var li = "<li><input type='radio' value='" + layerCode + "' name='selectedLayer' onclick='switchLayer(\""+layerCode+"\");'" + (layerCode=="${initLayerCode}"?" checked":"") + ">" + layerName + "</li>";
            $("#radioLayers").append(li);
        </c:forEach>

    })();

    function switchLayer(dstLayer){
        var selectedLayer = $("input[name='selectedLayer']:checked").val();
        if(curLayerCode==dstLayer){
            return;
        }

        var dstLayerConfig = layerConfigMap.get(dstLayer);
        var curLayerConfig = layerConfigMap.get(curLayerCode);
        map.removeControl(polygonDrawer);
        map.removeControl(modifyCtrl);
        map.removeControl(selectCtrl);
        map.removeLayer(vectorLayer);
        map.removeLayer(curLayerConfig.layer);

        map.addLayer(dstLayerConfig.layer);
        map.setCenter(new SuperMap.LonLat(dstLayerConfig.center.split(',')[0], dstLayerConfig.center.split(',')[1]), 0);
        map.addLayers([vectorLayer]);
        map.addControl(polygonDrawer);
        map.addControl(modifyCtrl);
        map.addControl(selectCtrl);
        curLayerCode = dstLayer;
        $("input[name='selectedLayer'][value='"+dstLayer+"']").prop("checked","checked");
        map.zoomIn();
        map.zoomOut();
        refreshAddress();
    }

</script>