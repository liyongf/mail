<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<script src="plug-in/supermap/dist/include-classic.js"></script>
<script type="text/javascript" src="plug-in/html2canvas/js/html2canvas.js"></script>
<script type="text/javascript" src="plug-in/html2canvas/js/html2canvas.min.js"></script>
<script type="text/javascript" src="plug-in/html2canvas/js/canvas2image.js"></script>
<script type="text/javascript" src="webpage/com/sdzk/buss/web/riskalert/js/supermap.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.jqprint.js"></script>
<style>
    .editPane {
        position:absolute;top:10px;left:50px;text-align:left;z-index: 1000;border-radius: 4px; color: #ffffff;
        background: transparent ;border: 1px solid transparent;box-shadow: 0 1px 1px rgba(0,0,0,.05);
    }
    .editStatus {
        position:absolute;top:10px;right:10px;text-align:left;z-index: 1000;border-radius: 4px; color: #ffffff;
        background: transparent ;border: 1px solid transparent;box-shadow: 0 1px 1px rgba(0,0,0,.05);
    }
</style>
<script>
    //声明全局变量 map、restLayer、url
    var map, restLayer, markerlayer, marker, popup, url = "${supermapMineUrl}";
    var curLayerCode = "${initLayerCode}";
    var vectorLayerStyle = {
        fillColor: "blue",
        fillOpacity: 0.2,
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
        //map = new SuperMap.Map("map");
        map = new SuperMap.Map("map", {
            controls: [
                new SuperMap.Control.Navigation({
                    dragPanOptions: {
                        enableKinetic: true
                    }
                })]
        });

        map.addControl(new SuperMap.Control.Zoom());
        map.addControl(new SuperMap.Control.ScaleLine());
        //map.addControl(new SuperMap.Control.LayerSwitcher());
        map.addControl(new SuperMap.Control.MousePosition());

        markerlayer = new SuperMap.Layer.Markers("markerLayer");
        vectorLayer = new SuperMap.Layer.Vector("polygonLayer");
        vectorLayer.style=vectorLayerStyle;

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
            }
            map.addLayers([markerlayer,vectorLayer]);
            refreshAddress();
            $("div.smMap").css("background-color",'#000000'); //黑色背景
            //$("div.smMap").css("background-color",'#ffffff');   //白色背景
        },1000);
    }
</script>
<body onload="init()">
<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 3px;" class="easyui-panel" id="demopanle">
        <div class="editPane">
            <ul id="radioLayers">
            </ul>
        </div>
        <div class="editStatus" style="width: 211px;height: 109px">

        	<%--<ul id="radioStatus">
	            <li><input type="radio" value="2" name="selectedStatus" onclick="refreshAddress()" checked="checked">动态风险预警</li>
	            <li><input type="radio" value="1" name="selectedStatus" onclick="refreshAddressStatic()">静态风险预警</li>
            </ul>--%>
            <%--<div id="zydj">
                专&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;业:<t:dictSelect id="proCate_gradeControl" field="proCate_gradeControl" defaultVal="" extendJson="{'onchange':'refreshAddress();'}" typeGroupCode="proCate_gradeControl" hasLabel="false"></t:dictSelect>
                <br>
                风险等级:<t:dictSelect id="riskLevel" field="riskLevel" defaultVal="" typeGroupCode="riskLevel" extendJson="{'onchange':'refreshAddress();'}" hasLabel="false"></t:dictSelect>
            </div>--%>
        </div>
        <div id="map" style="position: absolute; left: 0px; right: 0px; width: auto; height: 100%;"><a href="javascript:addOneTab('风险辨识审核','riskIdentificationController.do?reviewList','default');"><label></label></a></div>
    </div>
   <%-- <div region="west" style="width: 400px;" title="地点列表" split="true" collapsed="true" >
        <t:datagrid name="tBAddressInfoList" checkbox="false" onDblClick="locationAddress" fitColumns="true" title=""
                    actionUrl="tBAddressInfoController.do?dynamicRiskAlertDatagrid" idField="id" fit="true" queryMode="group"
                    onLoadSuccess="loadSuccessed">
            <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
            <t:dgCol title="地点名称" field="address" query="true" queryMode="single" width="180" align="center"></t:dgCol>
            &lt;%&ndash;<t:dgCol title="静态风险等级" field="staticRiskLevel" formatterjs="staticColorValueFormatter" query="false" queryMode="single" width="100"
                     align="center"></t:dgCol>&ndash;%&gt;
            <t:dgCol title="动态风险等级" field="riskLevel" formatterjs="colorValueFormatter" query="false" queryMode="single" width="100"
            align="center"></t:dgCol>
            <t:dgCol title="分管领导" field="manageMan" hidden="true" queryMode="single" width="100" align="center"></t:dgCol>
            <t:dgCol title="经度" field="lon" hidden="true" queryMode="group" width="80"></t:dgCol>
            <t:dgCol title="纬度" field="lat" hidden="true" queryMode="group" width="80"></t:dgCol>
            <t:dgCol title="煤层" field="belongLayer.id" hidden="true" queryMode="group" width="80"></t:dgCol>
            <t:dgCol title="静态预警颜色" field="staticAlertColor" hidden="true" queryMode="group" width="80"></t:dgCol>
            <t:dgCol title="动态预警颜色" field="alertColor" hidden="true" queryMode="group" width="80"></t:dgCol>
            <t:dgToolBar title="查看详情" operationCode="detail" icon="icon-search" url="tBAddressInfoController.do?detailWithRiskHiddenVio_new" funname="detail" height="600" width="800"></t:dgToolBar>
            <t:dgToolBar title="全屏" icon="icon-full-screen" funname="showFullScreen" operationCode="fullScreen"></t:dgToolBar>
            <t:dgToolBar title="打印" operationCode="print" icon="icon-edit" funname="printMap"></t:dgToolBar>
        </t:datagrid>
    </div>--%>
</div>
</body>
<script type="text/javascript">
    function mapAddAddressListener(e){
        addOneTab('风险管控预警图','riskAlertManageController.do?goUndergroundSafetyRiskAlert_dynamic','default');
    }
    var needPanTo=0,global_lon, global_lat,global_addressId;
    function refreshAddress() {
        map.events.un({"click": mapAddAddressListener});
        map.events.on({"click": mapAddAddressListener});
        $("#zydj").show();
        var pro = window.top.$.messager;
        if (pro) {
            window.top.$.messager.progress({
                text: '正在加载数据....',
                interval: 300
            });
        }
        markerlayer.clearMarkers();
        while(map.popups.length>0){
            var popup = map.popups[0];
            map.removePopup(popup);
        }
        vectorLayer.removeAllFeatures();
        var url = "tBAddressInfoController.do?getAllAddressListWithLevel_new";
        $.ajax({
            type: 'POST',
            data:{
                proCate_gradeControl:$("#proCate_gradeControl").val(),
                riskLevel:$("#riskLevel").val()
            },
            url: url,
            success: function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    if (pro)
                        window.top.$.messager.progress('close');
                    $(d.attributes.addressList).each(function (index, d) {
                        var txt = d.address;
                        var lon = d.lon;
                        var lat = d.lat;
                        var id = d.id;
                        var alertColor = d.alertColor;
                        var belongLayer = d.belongLayer;
                        if(belongLayer!=curLayerCode){
                            return true;
                        }
                        var iconUrl = "";
                        var level = d.risk_level;

                        if(level=='1'){
                            iconUrl = "plug-in/baidumap/image/levela.png";
                        }else if (level=='2') {
                        	iconUrl = "plug-in/baidumap/image/levelb.png";
						}else if (level=='3') {
                        	iconUrl = "plug-in/baidumap/image/levelc.png";
						}else if (level=='4') {
                        	iconUrl = "plug-in/baidumap/image/leveld.png";
						} else {
                            return true;
                        }
                        
                        //添加标记
                        var size = new SuperMap.Size(27, 27);
                        var offset = new SuperMap.Pixel(-(size.w / 2), -size.h);
                        var icon = new SuperMap.Icon(iconUrl, size, offset);
                        marker = new SuperMap.Marker(new SuperMap.LonLat(lon, lat), icon);
                        marker.events.on({
                            "mouseover": function(){
                                $("#"+id).css("display","block");
                            },
                            "mouseout": function(){
                            	$("#"+id).css("display","none");
                            },    //假如要在移动端的浏览器也实现点击弹框，则在注册touch类事件
                            "click": function(){
                            	//createdetailwindow("查看详情","tBAddressInfoController.do?detailWithRiskHiddenVio&addressId="+id,800,550);
                            	$.dialog({
                                    id:'dialog',
                                    title:'查看详情',
                                    zIndex:2000,
                                    modal:true,
                                    content: 'url:tBAddressInfoController.do?detailWithRiskHiddenVio_new&addressId='+id,
                                    lock:true,
                                    width: 800,
                                    height: 600,
                                    button: [
                                        {
                                            name: '关闭', callback: function () {}
                                        }
                                    ]
                                });
                        	},
                            "scope": marker
                        });
                        markerlayer.addMarker(marker);
                        
                        
                        //添加文本
                        /* var contentHTML = "<div style='position: absolute; background-color:"+alertColor+"; border: 1px solid rgb(188, 59, 58); color: black; " +
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
                        ); */
                        var contentHTML = "<div style='width:80px; font-size:12px;font-weight:bold ; opacity: 0.8'>";
                        contentHTML += "<p style='position: absolute; left: -2px; bottom: -35px; text-align: center; background: rgb(255, 255, 255) none repeat scroll 0% 0%; color: red; font-weight: bold; width: 110px; height: 20px;'>"+txt+"</p>";
                        contentHTML += "</div>";
                        popup = new SuperMap.Popup(
                        		id,
                                new SuperMap.LonLat(lon,lat),
                                new SuperMap.Size(80, 100),
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
                        /* $("#"+id+"_contentDiv div:first").mouseenter(function(){
                            this.style.backgroundColor = "#6BADCA";
                            this.style.borderColor = "#0000ff";
                            $(this).find("[name='arrow']").css("background-position","0px -20px");

                        });
                        $("#"+id+"_contentDiv div:first").mouseleave(function(){
                            this.style.backgroundColor = alertColor;
                            this.style.borderColor = "#BC3B3A";
                            $(this).find("[name='arrow']").css("background-position","0px 0px");
                        }); */
                        popup.hide();
                        $("#"+id+"_contentDiv div:first").click(function(){
                            $.dialog({
                                id:'dialog',
                                title:'查看详情',
                                zIndex:2000,
                                modal:true,
                                content: 'url:tBAddressInfoController.do?detailWithRiskHiddenVio_new&addressId='+id,
                                lock:true,
                                width: 720,
                                height: 520,
                                button: [
                                    {
                                        name: '关闭', callback: function () {}
                                    }
                                ]
                            });
                        });


                    });
                    if(needPanTo==1){
                        needPanTo=0;
                        map.panTo(new SuperMap.LonLat(global_lon,global_lat));
                    }
                }
            },
            error: function () {
            }
        });
    }

    function refreshAddressStatic() {
        $("#zydj").hide();
        var pro = window.top.$.messager;
        if (pro) {
            window.top.$.messager.progress({
                text: '正在加载数据....',
                interval: 300
            });
        }
        markerlayer.clearMarkers();
        while(map.popups.length>0){
            var popup = map.popups[0];
            map.removePopup(popup);
        }
        vectorLayer.removeAllFeatures();
        var url = "tBAddressInfoController.do?getAllAddressListWithLevelStatic";
        $.ajax({
            type: 'POST',
            url: url,
            success: function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    if (pro)
                        window.top.$.messager.progress('close');
                    $(d.attributes.addressList).each(function (index, d) {
                        var txt = d.address;
                        var lon = d.lon;
                        var lat = d.lat;
                        var id = d.id;
                        var staticAlertColor = d.staticAlertColor;
                        var belongLayer = d.belongLayer;
                        if(belongLayer!=curLayerCode){
                            return true;
                        }
                        //绘制多边形
                        var iconUrl = "";
                        var level = d.risk_level;
                        if(level=='1'){
                            iconUrl = "plug-in/baidumap/image/levela.png";
                        }else if (level=='2') {
                        	iconUrl = "plug-in/baidumap/image/levelb.png";
						}else if (level=='3') {
                        	iconUrl = "plug-in/baidumap/image/levelc.png";
						}else if (level=='4') {
                        	iconUrl = "plug-in/baidumap/image/leveld.png";
						}
                        
                        //添加标记
                        var size = new SuperMap.Size(27, 27);
                        var offset = new SuperMap.Pixel(-(size.w / 2), -size.h);
                        var icon = new SuperMap.Icon(iconUrl, size, offset);
                        marker = new SuperMap.Marker(new SuperMap.LonLat(lon, lat), icon);

                        marker.events.on({
                            "mouseover": function(){
                                $("#"+id).css("display","block");
                            },
                            "mouseout": function(){
                            	$("#"+id).css("display","none");
                            },    //假如要在移动端的浏览器也实现点击弹框，则在注册touch类事件
                            "click": function(){
                            	//createdetailwindow("查看详情","tBAddressInfoController.do?detailWithRiskHiddenVio&type=static&addressId="+id,800,550);
                            	$.dialog({
                                    id:'dialog',
                                    title:'查看详情',
                                    zIndex:2000,
                                    modal:true,
                                    content: 'url:tBAddressInfoController.do?detailWithRiskHiddenVio_new&type=static&addressId='+id,
                                    lock:true,
                                    width: 800,
                                    height: 600,
                                    button: [
                                        {
                                            name: '关闭', callback: function () {}
                                        }
                                    ]
                                });
                        	},
                            "scope": marker
                        });
                        markerlayer.addMarker(marker);

                        //添加文本
                        var contentHTML = "<div style='width:80px; font-size:12px;font-weight:bold ; opacity: 0.8'>";
                        contentHTML += "<p style='position: absolute; left: -2px; bottom: -35px; text-align: center; background: rgb(255, 255, 255) none repeat scroll 0% 0%; color: red; font-weight: bold; width: 110px; height: 20px;'>"+txt+"</p>";
                        contentHTML += "</div>";
                        popup = new SuperMap.Popup(
                        		id,
                                new SuperMap.LonLat(lon,lat),
                                new SuperMap.Size(80, 100),
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
                            this.style.backgroundColor = staticAlertColor;
                            this.style.borderColor = "#BC3B3A";
                            $(this).find("[name='arrow']").css("background-position","0px 0px");
                        });
                        popup.hide();
                        $("#"+id+"_contentDiv div:first").click(function(){
                            $.dialog({
                                id:'dialog',
                                title:'查看详情',
                                zIndex:2000,
                                modal:true,
                                content: 'url:tBAddressInfoController.do?detailWithRiskHiddenVio_new&type=static&addressId='+id,
                                lock:true,
                                width: 720,
                                height: 520,
                                button: [
                                    {
                                        name: '关闭', callback: function () {}
                                    }
                                ]
                            });
                        });


                    });
                    if(needPanTo==1){
                        needPanTo=0;
                        map.panTo(new SuperMap.LonLat(global_lon,global_lat));
                    }
                }
            },
            error: function () {
            }
        });
    }
    
    //定位
    function locationAddress(rowIndex,rowData){
        var belongLayer = rowData["belongLayer.id"];
        if(belongLayer=='undefined' || belongLayer==''){
            return;
        }
        global_lon = rowData.lon;
        global_lat = rowData.lat;
        global_addressId=rowData.id;
        needPanTo = 1;
        if(belongLayer!=curLayerCode) {
            switchLayer(belongLayer);
        } else {
            map.panTo(new SuperMap.LonLat(rowData.lon,rowData.lat));
        }
    }
    function loadSuccessed() {
        //refreshAddress();
    }

    //颜色列格式化
    function colorValueFormatter(value, rec, index) {
        if(value != ""){
            return '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 80px; padding-left: 26px;" type="text"><span class="minicolors-swatch" style="top:0px;width:80%"><span class="minicolors-swatch-color" style="background-color: ' + rec.alertColor + ';"></span></span></div></div>';
        }else{
            return value;
        }
    }
    
    function staticColorValueFormatter(value, rec, index) {
        if(value != ""){
            return '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 80px; padding-left: 26px;" type="text"><span class="minicolors-swatch" style="top:0px;width:80%"><span class="minicolors-swatch-color" style="background-color: ' + rec.staticAlertColor + ';"></span></span></div></div>';
        }else{
            return value;
        }
    }

    function showFullScreen() {
        var url = "riskAlertManageController.do?goUndergroundSafetyRiskAlert";
        createdetailwindow('井下安全风险预警', url, "100%", "100%");
    }

    $(function (){
        <c:forEach items="${layerList}" var="layerEntity" varStatus="status">
        var layerName = "${layerEntity.layerDetailName}";
        var layerCode = "${layerEntity.id}";
        var li = "<li><input type='radio' value='" + layerCode + "' name='selectedLayer' onclick='switchLayer(\""+layerCode+"\");'" + (layerCode=="${initLayerCode}"?" checked":"") + ">" + layerName + "</li>";
        $("#radioLayers").append(li);
        </c:forEach>
    });

    function switchLayer(dstLayer){
        var selectedLayer = $("input[name='selectedLayer']:checked").val();
        if(curLayerCode==dstLayer){
            return;
        }

        var dstLayerConfig = layerConfigMap.get(dstLayer);
        var curLayerConfig = layerConfigMap.get(curLayerCode);
        map.removeLayer(vectorLayer);
        map.removeLayer(curLayerConfig.layer);

        map.addLayer(dstLayerConfig.layer);
        map.setCenter(new SuperMap.LonLat(dstLayerConfig.center.split(',')[0], dstLayerConfig.center.split(',')[1]), 0);
        map.addLayers([vectorLayer]);
        curLayerCode = dstLayer;
        $("input[name='selectedLayer'][value='"+dstLayer+"']").prop("checked","checked");
        map.zoomIn();
        map.zoomOut();
        refreshAddress();
    }
</script>
