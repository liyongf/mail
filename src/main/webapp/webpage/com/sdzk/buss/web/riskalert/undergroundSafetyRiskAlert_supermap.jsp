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
<script>
    //声明全局变量 map、restLayer、url
    var map, restLayer, markerlayer, marker, popup, url = "${supermapMineUrl}";
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
        panzoombar = new SuperMap.Control.PanZoomBar();
        panzoombar.showSlider = true;
        panzoombar.slideRatio = 0.5;
        map.addControl(panzoombar);
        map.addControl(new SuperMap.Control.ScaleLine());
        map.addControl(new SuperMap.Control.LayerSwitcher());
        map.addControl(new SuperMap.Control.MousePosition());
        restLayer = new SuperMap.Layer.TiledDynamicRESTLayer("restLayer", url, null, { maxResolution: "auto",useCORS: true });
        restLayer.events.on({ "layerInitialized": addLayer });

        markerlayer = new SuperMap.Layer.Markers("markerLayer");
        vectorLayer = new SuperMap.Layer.Vector("polygonLayer");
        vectorLayer.style=vectorLayerStyle;
    }
    function addLayer() {
        map.addLayers([restLayer,markerlayer,vectorLayer]);
        map.setCenter(new SuperMap.LonLat(${supermapMineCenter}), 0);
        refreshAddress();
    }
</script>
<body onload="init()">
<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 3px;" class="easyui-panel" id="demopanle">
        <div id="map" style="position: absolute; left: 0px; right: 0px; width: auto; height: 100%;"></div>
    </div>
    <div region="west" style="width: 400px;" title="地点列表" split="true">
        <t:datagrid name="tBAddressInfoList" checkbox="false" onDblClick="locationAddress" fitColumns="true" title=""
                    actionUrl="tBAddressInfoController.do?riskAlertDatagrid" idField="id" fit="true" queryMode="group"
                    onLoadSuccess="loadSuccessed">
            <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
            <t:dgCol title="地点名称" field="address" query="true" queryMode="single" width="180" align="center"></t:dgCol>
            <t:dgCol title="风险等级" field="riskLevel" formatterjs="colorValueFormatter" query="false" queryMode="single" width="100"
                     align="center"></t:dgCol>
            <t:dgCol title="分管领导" field="manageMan" hidden="true" queryMode="single" width="100" align="center"></t:dgCol>
            <t:dgCol title="经度" field="lon" hidden="true" queryMode="group" width="80"></t:dgCol>
            <t:dgCol title="纬度" field="lat" hidden="true" queryMode="group" width="80"></t:dgCol>
            <t:dgCol title="预警颜色" field="alertColor" hidden="true" queryMode="group" width="80"></t:dgCol>
            <t:dgToolBar title="查看详情" operationCode="detail" icon="icon-search" url="tBAddressInfoController.do?detailWithRiskHiddenVio" funname="detail"></t:dgToolBar>
            <t:dgToolBar title="全屏" icon="icon-full-screen" funname="showFullScreen" operationCode="fullScreen"></t:dgToolBar>
            <t:dgToolBar title="打印" operationCode="print" icon="icon-edit" funname="printMap"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
</body>
<script type="text/javascript">
    function refreshAddress() {
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
        var url = "tBAddressInfoController.do?getAllAddressListWithLevel";
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
                        var alertColor = d.alertColor;

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

                        //添加标记
                        //addMaker(lon,lat);
                        var size = new SuperMap.Size(44, 33);
                        var offset = new SuperMap.Pixel(-(size.w / 2), -size.h);
                        var icon = new SuperMap.Icon('plug-in/supermap/src/classic/theme/images/marker.png', size, offset);
                        marker = new SuperMap.Marker(new SuperMap.LonLat(lon, lat), icon);
                        //markerlayer.addMarker(marker);

                        //添加文本
                        //var contentHTML = "<div style='position: absolute; background-color: rgb(211, 211, 211); border: 1px solid rgb(188, 59, 58); color: black; " +
                        //        "height: 18px; padding: 2px; line-height: 18px; white-space: nowrap; -moz-user-select: none; font-size: 12px; left: -10px; top: -32px;'><span>";
                        var contentHTML = "<div style='position: absolute; background-color:"+alertColor+"; border: 1px solid rgb(188, 59, 58); color: black; " +
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
                            this.style.backgroundColor = alertColor;
                            this.style.borderColor = "#BC3B3A";
                            $(this).find("[name='arrow']").css("background-position","0px 0px");
                        });
                        $("#"+id+"_contentDiv div:first").click(function(){
                            $.dialog({
                                id:'dialog',
                                title:'查看详情',
                                zIndex:2000,
                                modal:true,
                                content: 'url:tBAddressInfoController.do?detailWithRiskHiddenVio&addressId='+id,
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
                }
            },
            error: function () {
            }
        });
    }

    //定位
    function locationAddress(rowIndex,rowData){
        map.panTo(new SuperMap.LonLat(rowData.lon,rowData.lat));
        //changeSelectDivColor(rowIndex,rowData);
    }
    function loadSuccessed() {
        //refreshAddress();
    }

    //颜色列格式化
    function colorValueFormatter(value, rec, index) {
        if(value != ""){
            return '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 80px; padding-left: 26px;" type="text" value="' + value + '"><span class="minicolors-swatch" style="top:0px;"><span class="minicolors-swatch-color" style="background-color: ' + rec.alertColor + ';"></span></span></div></div>';
        }else{
            return value;
        }
    }

    function showFullScreen() {
        var url = "riskAlertManageController.do?goUndergroundSafetyRiskAlert";
        createdetailwindow('井下安全风险预警', url, "100%", "100%");
    }
</script>
