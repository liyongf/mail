<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<link rel="stylesheet" href="plug-in/Validform/css/divfrom.css" type="text/css">
<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css">
<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css">
<script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
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
</style>
<script>
    //声明全局变量 map、restLayer、url
    var map, restLayer, vectorLayer, markerlayer, marker, popup, url = "${supermapMineUrl}";
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
        //1.在指定DOM元素上初始化地图对象。
        //map = new SuperMap.Map("map");
        map = new SuperMap.Map("map", {
            controls: [
                new SuperMap.Control.Navigation({
                    dragPanOptions: {
                        enableKinetic: true
                    }
                })]
        });
//        panzoombar = new SuperMap.Control.PanZoomBar();
//        panzoombar.showSlider = true;
//        panzoombar.slideRatio = 0.5;
//        map.addControl(panzoombar);
        map.addControl(new SuperMap.Control.Zoom());
        map.addControl(new SuperMap.Control.ScaleLine());
        map.addControl(new SuperMap.Control.LayerSwitcher());
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
            refreshCheckNumberGis("allmine");
            $("div.smMap").css("background-color",'#000000'); //黑色背景
            //$("div.smMap").css("background-color",'#ffffff');   //白色背景

        },1000);
    }

</script>
<body onload="init()">
<div id="tempDiv" style="display: none;">
    <div style="padding:8px;padding-top: 0;background-color:#F5F5F5;">
        <p style="position: relative;left: 4px;top: 8px;width:51px;background-color: #F5F5F5;">等级图层</p>
        <div style="border:1px solid #C4C4C4;padding: 20px 40px;">
            <label><input id="level-a" name="Fruit" type="checkbox" onclick="levelastatue()" value="" />A级</label>
            <label style="margin-left:15px"><input id="level-b" name="Fruit" onclick="levelbstatue()" type="checkbox" value="" />B级</label>
            <label style="margin-left:15px"><input id="level-c" name="Fruit" onclick="levelcstatue()" type="checkbox" value="" />C级</label>
            <label style="margin-left:15px"><input id="level-d" name="Fruit" onclick="leveldstatue()" type="checkbox" value="" />D级、E级</label>
        </div>
    </div>
</div>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums">
        <span style="margin-top:5px;display:inline-block;font-size:0;">
        <span title="单位"
              style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">单位：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;font-size:0px;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
        <input class="inuptxt" type="hidden" style="width: 100px" name="dutyUnitId">
        <div id="orgSelect" style="width: 130px;height: 15px"></div>
        </span>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 3px;" class="easyui-panel" id="demopanle">
        <div class="editPane">
            <ul id="radioLayers">
            </ul>
        </div>
        <div id="map" style="position: absolute; left: 0px; right: 0px; width: auto; height: 100%;"></div>
    </div>
    <div region="west" style="width: 400px;" title="检查次数分区域列表" split="true">
        <t:datagrid name="hiddenCheckNumberList" checkbox="false" fitColumns="false" onLoadSuccess="loadSuccess"
                    onDblClick="filterAddress" pagination="false" title="检查次数" actionUrl="riskAlertManageController.do?hiddenCheckNumberDataGrid"
                    idField="id" fit="true" queryMode="group" >
            <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
            <t:dgCol title="地点ID" field="address" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="地点" field="addressname" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="经度"  field="lon"   hidden="true"  queryMode="group"  width="80"></t:dgCol>
            <t:dgCol title="纬度"  field="lat"  hidden="true"   queryMode="group"  width="80"></t:dgCol>
            <t:dgCol title="检查次数" align="center" field="checknum" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="隐患个数" align="center" field="hiddennum" queryMode="single" width="80"></t:dgCol>
            <t:dgCol title="起止时间" field="queryDate" formatter="yyyy-MM-dd" hidden="true" query="true" queryMode="group"
                     width="120"></t:dgCol>
            <t:dgCol title="单位ID" field="dutyUnitId" hidden="true" queryMode="single" query="false"
                     width="80"></t:dgCol>
            <t:dgCol title="单位" field="dutyUnit" hidden="true" queryMode="single" query="false" width="80"></t:dgCol>

            <t:dgCol title="煤层" field="belongLayer" hidden="true" queryMode="single" query="false" width="80"></t:dgCol>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>
            <t:dgToolBar title="全屏" icon="icon-full-screen" funname="showFullScreen" operationCode="fullScreen"></t:dgToolBar>
            <t:dgToolBar title="打印" operationCode="print" icon="icon-edit" funname="printMap"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
</body>
<script type="text/javascript">
    var needPanTo=0,global_lon, global_lat,global_addressId;
    function refreshCheckNumberGis(_addressId) {
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

        var alertLevelColor_A = "";
        var alertLevelColor_B = "";
        var alertLevelColor_C = "";
        var alertLevelColor_DE = "";

        $.ajax({
            type: 'POST',
            url: 'tBAlertLevelColorController.do?getAlertLevelColor',
            success: function (data) {
                var d = $.parseJSON(data);
                $(d.attributes.resultList).each(function (index, d) {
                    if (d.alertLevelName == "A") {
                        alertLevelColor_A = d.alertLevelColor;
                    } else if (d.alertLevelName == "B") {
                        alertLevelColor_B = d.alertLevelColor;
                    } else if (d.alertLevelName == "C") {
                        alertLevelColor_C = d.alertLevelColor;
                    } else if (d.alertLevelName == "DE") {
                        alertLevelColor_DE = d.alertLevelColor;
                    }
                });
            },
            error: function () {
            }
        });

        var url = "riskAlertManageController.do?getHiddenCheckNumberList&addressId="+_addressId+"&dutyUnitId=" + $("input[name='dutyUnitId']").val() + "&queryDate_begin=" + $("input[name='queryDate_begin']").val() + "&queryDate_end=" + $("input[name='queryDate_end']").val();

        $.ajax({
            type: 'POST',
            url: url,
            async:false,
            success: function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    if (pro)
                        window.top.$.messager.progress('close');
                    $(d.attributes.resultList).each(function (index, d) {
                        var addressid = d.addressId;
                        var addressName = d.addressName;
                        var lon = d.lon;
                        var lat = d.lat;
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

                            polygon_feature.attributes.addressId=addressid;
                            polygon_feature.attributes.addressName=addressName;
                            vectorLayer.addFeatures([polygon_feature]);
                        }

                        var checknum = checkUndefinedReturn(d.checknum);
                        var hiddennum_A = checkUndefinedReturn(d.hiddennum_A);
                        var hiddennum_B = checkUndefinedReturn(d.hiddennum_B);
                        var hiddennum_C = checkUndefinedReturn(d.hiddennum_C);
                        var hiddennum_DE = checkUndefinedReturn(d.hiddennum_DE);
                        var alertLevelColor = "";
                        var unitId = $("input[name='dutyUnitId']").val();
                        var iconUrl = "";
                        var level = "";

                        if (hiddennum_A > 0) {
                            level = 'a';
                            iconUrl = "plug-in/baidumap/image/levela.png";
                            alertLevelColor = alertLevelColor_A;
                            var sContent = "<table border='0' name='level-a' border='1' cellpadding='0' cellspacing='0' >";
                            sContent += "<tr style='background-color: #fffded;'><td align='left' colspan='2'><h4 style='margin:0;padding:0.3em 0;width:98px;'>&nbsp;检查次数:<span style='float: right;'>";
                            sContent += "<a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"\")'>"+ checknum + "</a>&emsp;</span></h4></td></tr>";
                            sContent += "<tr style='background-color: #fffded'><td align='center' style='padding:0.3em 0;font-weight:bolder;'>&nbsp;等级&nbsp;</td><td align='center' style='padding:0.3em 0;font-weight:bolder;'>&nbsp;问题&nbsp;</td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>A</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"2\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_A + "</h4></a></td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>B</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"3\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_B + "</h4></a></td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>C</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"4\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_C + "</h4></a></td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>DE</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"5,6\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_DE + "</h4></a></td></tr></table>";
                        } else if (hiddennum_B > 0) {
                            level = 'b';
                            iconUrl = "plug-in/baidumap/image/levelb.png";
                            alertLevelColor = alertLevelColor_B;
                            var sContent = "<table border='0' name='level-b' border='1' cellpadding='0' cellspacing='0' >";
                            sContent += "<tr style='background-color: #fffded;'><td align='left' colspan='2'><h4 style='margin:0;padding:0.3em 0;width:98px;'>&nbsp;检查次数:<span style='float: right;'>";
                            sContent += "<a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"\")'>"+ checknum + "</a>&emsp;</span></h4></td></tr>";
                            sContent += "<tr style='background-color: #fffded'><td align='center' style='padding:0.3em 0;font-weight:bolder;'>&nbsp;等级&nbsp;</td><td align='center' style='padding:0.3em 0;font-weight:bolder;'>&nbsp;问题&nbsp;</td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>A</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"2\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_A + "</h4></a></td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>B</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"3\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_B + "</h4></a></td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>C</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"4\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_C + "</h4></a></td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>DE</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"5,6\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_DE + "</h4></a></td></tr></table>";
                        } else if (hiddennum_C > 0) {
                            level = 'c';
                            iconUrl = "plug-in/baidumap/image/levelc.png";
                            alertLevelColor = alertLevelColor_C;
                            var sContent = "<table border='0' name='level-c' border='1' cellpadding='0' cellspacing='0' >";
                            sContent += "<tr style='background-color: #fffded;'><td align='left' colspan='2'><h4 style='margin:0;padding:0.3em 0;width:98px;'>&nbsp;检查次数:<span style='float: right;'>";
                            sContent += "<a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"\")'>"+ checknum + "</a>&emsp;</span></h4></td></tr>";
                            sContent += "<tr style='background-color: #fffded'><td align='center' style='padding:0.3em 0;font-weight:bolder;'>&nbsp;等级&nbsp;</td><td align='center' style='padding:0.3em 0;font-weight:bolder;'>&nbsp;问题&nbsp;</td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>A</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"2\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_A + "</h4></a></td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>B</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"3\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_B + "</h4></a></td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>C</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"4\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_C + "</h4></a></td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>DE</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"5,6\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_DE + "</h4></a></td></tr></table>";
                        } else if (hiddennum_DE > 0) {
                            level = 'd';
                            iconUrl = "plug-in/baidumap/image/leveld.png";
                            alertLevelColor = alertLevelColor_DE;
                            var sContent = "<table border='0' name='level-d' border='1' cellpadding='0' cellspacing='0' >";
                            sContent += "<tr style='background-color: #fffded;'><td align='left' colspan='2'><h4 style='margin:0;padding:0.3em 0;width:98px;'>&nbsp;检查次数:<span style='float: right;'>";
                            sContent += "<a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"\")'>"+ checknum + "</a>&emsp;</span></h4></td></tr>";
                            sContent += "<tr style='background-color: #fffded'><td align='center' style='padding:0.3em 0;font-weight:bolder;'>&nbsp;等级&nbsp;</td><td align='center' style='padding:0.3em 0;font-weight:bolder;'>&nbsp;问题&nbsp;</td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>A</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"2\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_A + "</h4></a></td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>B</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"3\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_B + "</h4></a></td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>C</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"4\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_C + "</h4></a></td></tr>";
                            sContent += "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>DE</h4></td>";
                            sContent += "<td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"5,6\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_DE + "</h4></a></td></tr></table>";
                        }

                        //添加标记
                        var size = new SuperMap.Size(20, 27);
                        var offset = new SuperMap.Pixel(-(size.w / 2), -size.h);
                        var icon = new SuperMap.Icon(iconUrl, size, offset);
                        marker = new SuperMap.Marker(new SuperMap.LonLat(lon, lat), icon);
                        marker.events.on({
                            "mouseover": function(){
                                $("#"+unitId+"_"+addressid).css("display","block");
                            },
                            "mouseout": function(){
                            },    //假如要在移动端的浏览器也实现点击弹框，则在注册touch类事件
                            "scope": marker
                        });
                        markerlayer.addMarker(marker);

                        //添加popup
                        contentHTML = "<div style='width:80px; font-size:12px;font-weight:bold ; opacity: 0.8'>";
                        contentHTML += sContent;
                        contentHTML += "<p style='position: absolute; left: 0px; bottom: -45px; text-align: center; background: rgb(255, 255, 255) none repeat scroll 0% 0%; color: red; font-weight: bold; width: 100px; height: 20px;'>"+addressName+"</p>";
                        contentHTML += "</div>";
                        popup = new SuperMap.Popup(
                                unitId+"_"+addressid,
                                new SuperMap.LonLat(lon,lat),
                                new SuperMap.Size(80, 100),
                                contentHTML,
                                true,
                                null
                        );

                        map.addPopup(popup);
                        $("#"+unitId+"_"+addressid).css('background','none');
                        $("#"+unitId+"_"+addressid+"_close").remove();
                        $("#"+unitId+"_"+addressid).attr("level",level);
                        $("#"+unitId+"_"+addressid+"_contentDiv").removeClass("smPopupContent");
                        $("#"+unitId+"_"+addressid).css("top",$("#"+unitId+"_"+addressid).position().top-30);
                        $("#"+unitId+"_"+addressid).css("left",$("#"+unitId+"_"+addressid).position().left-15);
                        $("#"+unitId+"_"+addressid).mouseenter(function(){
                            $(this).find("table").css("border", "2px solid #000000");
                            $(this).find("table").css("zIndex", "9999");
                            $("#"+unitId+"_"+addressid).css("display", "block");
                        });
                        $("#"+unitId+"_"+addressid).mouseleave(function(){
                            $(this).find("table").css("border","none");
                            $(this).find("table").css("zIndex","-1");
                            var level = $(this).attr("level");

                            //设置地点提示框是否可见：否
                            if(level == "a"){
                                if($('#level-a').attr('checked')){
                                    this.style.display = "block";
                                }else{
                                    this.style.display = "none";
                                }
                            }else if(level == "b"){
                                if($('#level-b').attr('checked')){
                                    this.style.display = "block";
                                }else{
                                    this.style.display = "none";
                                }
                            }else if(level == "c"){
                                if($('#level-c').attr('checked')){
                                    this.style.display = "block";
                                }else{
                                    this.style.display = "none";
                                }
                            }else{
                                if($('#level-d').attr('checked')){
                                    this.style.display = "block";
                                }else{
                                    this.style.display = "none";
                                }
                            }

                        });
                        popup.hide();
                    });
                    levelastatue();
                    levelbstatue();
                    levelcstatue();
                    leveldstatue();

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
    //数据加载成功后刷新地图，默认显示全矿
    function loadSuccess() {
        refreshCheckNumberGis("allmine");
    }
    //检查次数区域分布
    function showFullScreen() {
        var url = "riskAlertManageController.do?hiddenCheckNumberlist";
        createdetailwindow('检查次数区域分布', url, "100%", "100%");
    }

    //弹出隐患问题列表
    function showHiddenDangerList(unitId, addressid,hiddenNature) {
        var url = "riskAlertManageController.do?hiddenDangerList&hiddenNature="+hiddenNature+"&dutyunit=" + unitId + "&addressId=" + addressid + "&queryDate_begin=" + $("input[name='queryDate_begin']").val() + "&queryDate_end=" + $("input[name='queryDate_end']").val();
        createdetailwindow('问题列表', url, 800);
    }

    function checkUndefinedReturn(obj) {
        if (typeof(obj) == "undefined") {
            return 0;
        } else {
            return obj;
        }
    }

    function checkColor(obj) {
        if (obj.indexOf("#") > -1) {
            return obj;
        } else {
            return "#fffded";
        }
    }

    function filterAddress(rowIndex, rowData) {
        debugger;
        var belongLayer = rowData.belongLayer;
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
        //refreshCheckNumberGis(rowData.address);
    }

    $(function () {
        var datagrid = $("#hiddenCheckNumberListtb");
        datagrid.find("form[id='hiddenCheckNumberListForm']>span:last").after($("#tempSearchColums div[name='searchColums']").html());
        datagrid.find("form[id='hiddenCheckNumberListForm']").prepend($("#tempDiv").html());
        $("#tempDiv").empty();
        $("div[name='searchColums']").css("padding-bottom", "5px");
        $("div[class='panel datagrid']>div[class='panel-header']").hide();
        //给时间控件加上样式
        $("#hiddenCheckNumberListtb").find("input[name='queryDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#hiddenCheckNumberListtb").find("input[name='queryDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });

        $("#tempSearchColums").empty();
        getDepartMagicSuggest($("#orgSelect"), $("[name='dutyUnitId']"));

        <c:forEach items="${layerList}" var="layerEntity" varStatus="status">
        var layerName = "${layerEntity.layerDetailName}";
        var layerCode = "${layerEntity.id}";
        var li = "<li><input type='radio' value='" + layerCode + "' name='selectedLayer' onclick='switchLayer(\""+layerCode+"\");'" + (layerCode=="${initLayerCode}"?" checked":"") + ">" + layerName + "</li>";
        $("#radioLayers").append(li);
        </c:forEach>
    });

    //获取本月第一天
    function getCurrentDate(){
        var time = new Date();
        var year = time.getFullYear();
        var month = time.getMonth() + 1;
        var day = time.getDate();
        if (month < 10) {
            month = "0" + month;
        }
        $("input[name='queryDate_begin']").val(year + "-" + month + "-01");
    }


    //导出
    function ExportXls() {
        JeecgExcelExport("riskAlertManageController.do?exportXlsByAddress&dutyUnitId=" + $("input[name='dutyUnitId']").val(), "hiddenCheckNumberList");
    }

    function switchLayer(dstLayer){
        var selectedLayer = $("input[name='selectedLayer']:checked").val();
        if(curLayerCode==dstLayer){
            return;
        }

        var dstLayerConfig = layerConfigMap.get(dstLayer);
        var curLayerConfig = layerConfigMap.get(curLayerCode);
        map.removeLayer(markerlayer);
        map.removeLayer(vectorLayer);
        map.removeLayer(curLayerConfig.layer);

        map.addLayer(dstLayerConfig.layer);
        map.setCenter(new SuperMap.LonLat(dstLayerConfig.center.split(',')[0], dstLayerConfig.center.split(',')[1]), 0);
        map.addLayers([markerlayer,vectorLayer]);
        curLayerCode = dstLayer;
        $("input[name='selectedLayer'][value='"+dstLayer+"']").prop("checked","checked");
        map.zoomIn();
        map.zoomOut();
        refreshCheckNumberGis("allmine");
    }
</script>
<script type="text/javascript">
    function levelastatue(){
        if($('#level-a').attr('checked')){
            $("div[level='a']").css("display","block");
        }else{
            $("div[level='a']").css("display","none");
        }
    }
    function levelbstatue(){
        if($('#level-b').attr('checked')){
            $("div[level='b']").css("display","block");
        }else{
            $("div[level='b']").css("display","none");
        }
    }
    function levelcstatue(){
        if($('#level-c').attr('checked')){
            $("div[level='c']").css("display","block");
        }else{
            $("div[level='c']").css("display","none");
        }
    }
    function leveldstatue() {
        if($('#level-d').attr('checked')){
            $("div[level='d']").css("display","block");
        }else{
            $("div[level='d']").css("display","none");
        }
    }

</script>