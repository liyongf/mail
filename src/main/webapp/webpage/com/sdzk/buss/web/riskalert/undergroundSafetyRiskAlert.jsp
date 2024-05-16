<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>


<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/baidumap/js/api.js"></script>
<script type="text/javascript" src="plug-in/baidumap/js/AreaRestriction_min.js"></script>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<script type="text/javascript" src="plug-in/jquery/jquery.jqprint.js"></script>
<link rel="stylesheet" href="plug-in/baidumap/css/print.css" type="text/css" media="print">
<style type="text/css">
    .anchorBL {
        display: none;
    }
</style>
<div id="tempDiv" style="display: none;">
    <div style="padding:8px;padding-top: 0;background-color:#F5F5F5;">
        <p style="position: relative;left: 4px;top: 8px;width:80px;background-color: #F5F5F5;">风险等级图层</p>
        <div style="border:1px solid #C4C4C4;padding: 20px 40px;">
            <label style="margin-left:00px"><input id="riskLevel1" name="riskLevel1" onclick="riskLevel1statue()" type="checkbox" value="1" checked="checked"/>重大风险</label>
            <label style="margin-left:15px"><input id="riskLevel2" name="riskLevel2" onclick="riskLevel2statue()" type="checkbox" value="" />较大风险</label>
            <label style="margin-left:15px"><input id="riskLevel3" name="riskLevel3" onclick="riskLevel3statue()" type="checkbox" value="" />一般风险</label>
            <label style="margin-left:15px"><input id="riskLevel4" name="riskLevel4" onclick="riskLevel4statue()" type="checkbox" value="" />低风险</label>
        </div>
    </div>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 3px;" class="easyui-panel" id="demopanle">
        <div id="map" style="height: 92%;width: 100%;"></div>
    </div>
    <div region="west" style="width: 420px;" title="地点列表" split="true">
        <t:datagrid name="tBAddressInfoList" checkbox="false" onDblClick="locationAddress" fitColumns="true" title=""
                    actionUrl="tBAddressInfoController.do?riskQueryAlertDatagrid" idField="id" fit="true" queryMode="group"
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
            <t:dgToolBar title="打印地图" icon="icon-print" funname="printMap" operationCode="print" ></t:dgToolBar>
            <t:dgToolBar title="全屏" icon="icon-full-screen" funname="showFullScreen"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    var global_id, global_address;

    var tileLayer = new BMap.TileLayer();
    tileLayer.getTilesUrl = function (tileCoord, zoom) {
        var x = tileCoord.x;
        var y = tileCoord.y;
        //return 'plug-in/baidumap/tiles/' + zoom + '/tile' + x + '_' + y + '.png';
        return '${mapPath}' + zoom + '/tile' + x + '_' + y + '.png';
    }
    var MyMap = new BMap.MapType('MyMap', tileLayer, {minZoom: 3, maxZoom: 8});
    var map = new BMap.Map('map', {mapType: MyMap});
    map.addControl(new BMap.NavigationControl());
    map.enableScrollWheelZoom(); // 允许滚动
    var b = new BMap.Bounds(new BMap.Point(48.406441, 2.865784), new BMap.Point(186.386167, 65.265558)); // 范围 左下角，右上角的点位置

    map.centerAndZoom(new BMap.Point(0,0), 4);

    //复杂的自定义覆盖物(地址图标显示)
    function ComplexCustomOverlay(point, text, mouseoverText, id,alertColor) {
        this._point = point;
        this._text = text;
        this._overText = mouseoverText;
        this._id = id;
        this._alertColor = alertColor;
    }
    ComplexCustomOverlay.prototype = new BMap.Overlay();
    ComplexCustomOverlay.prototype.initialize = function (map) {
        this._map = map;
        var alertColor = this._alertColor;
        var div = this._div = document.createElement("div");
        div.style.position = "absolute";
        div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
        div.style.backgroundColor = alertColor;
        div.style.color = "black";
        //div.style.height = "70px";
        div.style.width = "auto";
        div.style.padding = "0";
        div.style.lineHeight = "18px";
        div.style.whiteSpace = "nowrap";
        div.style.MozUserSelect = "none";
        div.style.fontSize = "8px";
        div.style.zIndex = "-1";
        var span = this._span = document.createElement("span");
        div.appendChild(span);
        span.appendChild(document.createTextNode(this._text));
        span.style.display = "none";
        var that = this;
        var arrow = this._arrow = document.createElement("div");
        if(alertColor=="#ff0000"){
            arrow.style.background = "url(plug-in/baidumap/image/levela.png) no-repeat";
        }
        if(alertColor=="#ff7300"){
            arrow.style.background = "url(plug-in/baidumap/image/levelb.png) no-repeat";
        }
        if(alertColor=="#fff719"){
            arrow.style.background = "url(plug-in/baidumap/image/levelc.png) no-repeat";
        }
        if(alertColor=="#0051ff"){
            arrow.style.background = "url(plug-in/baidumap/image/leveld.png) no-repeat";
        }

        arrow.style.position = "absolute";
        arrow.style.width = "20px";
        arrow.style.height = "27px";
        arrow.style.top = "20px";
        arrow.style.left = "0px";
        arrow.style.overflow = "hidden";
        div.appendChild(arrow);

        /*div.onmouseover = function () {
            this.style.backgroundColor = "#6BADCA";
            this.style.borderColor = "#0000ff";
            this.getElementsByTagName("span")[0].innerHTML = that._overText;
            arrow.style.backgroundPosition = "0px -20px";
        }

        div.onmouseout = function () {
            this.style.backgroundColor = alertColor;
            this.style.borderColor = "#BC3B3A";
            this.getElementsByTagName("span")[0].innerHTML = that._text;
            arrow.style.backgroundPosition = "0px 0px";
        }*/
        div.onmouseover = function () {
            this.style.border = "2px solid #000000";
            this.style.zIndex = "99999";
            this.getElementsByTagName("span")[0].innerHTML = that._overText;
            span.style.display = "block";
        }
        div.onmouseout = function () {
            this.style.border = "none";
            this.style.zIndex = "-1";
            this.getElementsByTagName("span")[0].innerHTML = that._text;
            span.style.display = "none";
        }

        map.getPanes().labelPane.appendChild(div);

        return div;
    }
    ComplexCustomOverlay.prototype.draw = function () {
        var map = this._map;
        var pixel = map.pointToOverlayPixel(this._point);
        this._div.style.left = pixel.x - parseInt(this._arrow.style.left) - 10 + "px";
        this._div.style.top = pixel.y -45 + "px";
    }
    //添加监听事件
    ComplexCustomOverlay.prototype.addEventListener = function (event, fun) {
        this._div['on' + event] = fun;
    }
    $(function () {
        var datagrid = $("#tBAddressInfoListtb");
        datagrid.find("form[id='tBAddressInfoListForm']").prepend($("#tempDiv").html());
        $("#tempDiv").empty();
    });
    function riskLevel1statue(){
        if($('#riskLevel1').attr('checked')){
            $("#riskLevel1").val("1");
        }else{
            $("#riskLevel1").val("");
        }
        $("#thisForm").submit();
        refreshAddress();
    }
    function riskLevel2statue(){
        if($('#riskLevel2').attr('checked')){
            $("#riskLevel2").val("2");
        }else{
            $("#riskLevel2").val("");
        }
        $("#thisForm").submit();
        refreshAddress();
    }
    function riskLevel3statue(){
        if($('#riskLevel3').attr('checked')){
            $("#riskLevel3").val("3");
        }else{
            $("#riskLevel3").val("");
        }
        $("#thisForm").submit();
        refreshAddress();
    }
    function riskLevel4statue(){
        if($('#riskLevel4').attr('checked')){
            $("#riskLevel4").val("4");
        }else{
            $("#riskLevel4").val("");
        }
        $("#thisForm").submit();
        refreshAddress();
    }
    function refreshAddress() {
        var pro = window.top.$.messager;
        if (pro) {
            window.top.$.messager.progress({
                text: '正在加载数据....',
                interval: 300
            });
        }
        var url = "tBAddressInfoController.do?getQueryAddressListWithLevel";
        $.ajax({
            type: 'POST',
            data:{'riskLevel':$("select[name='riskLevel']").val(),
                'riskLevel1':$("#riskLevel1").val(),
                'riskLevel2':$("#riskLevel2").val(),
                'riskLevel3':$("#riskLevel3").val(),
                'riskLevel4':$("#riskLevel4").val()},
            url: url,
            success: function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    if (pro)
                        window.top.$.messager.progress('close');
                    map.clearOverlays();
                    $(d.attributes.addressList).each(function (index, d) {
                        var txt = d.address;
                        var lon = d.lon;
                        var lat = d.lat;
                        var id = d.id;
                        var alertColor = d.alertColor;
                        var myCompOverlay = new ComplexCustomOverlay(new BMap.Point(lon, lat), txt, txt, id,alertColor);
                        map.addOverlay(myCompOverlay);

                        myCompOverlay.addEventListener("click", function () {
                            global_id = myCompOverlay._id;
                            global_address = myCompOverlay._text;
                            //TODO 弹出详情页
                            $.dialog({
                                id:'dialog',
                                title:'查看详情',
                                zIndex:2000,
                                modal:true,
                                content: 'url:tBAddressInfoController.do?detailWithRiskHiddenVio&addressId='+global_id,
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
    function locationAddress(rowIndex, rowData) {
        var new_point = new BMap.Point(rowData.lon, rowData.lat);
        map.panTo(new_point);
    }

    function loadSuccessed() {
        map.panTo(new BMap.Point(0,0));
        refreshAddress();
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

    function printMap(){
        $("#map").jqprint({
            debug: true,
            importCSS: true,
            printContainer: true,
            operaSupport: false
        });
    }
</script>
