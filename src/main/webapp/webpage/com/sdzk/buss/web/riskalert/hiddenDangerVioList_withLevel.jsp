<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <link rel="stylesheet" href="plug-in/Validform/css/divfrom.css" type="text/css">
    <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css">
    <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css">
    <script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
    <script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
    <script
            type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
    <script type="text/javascript" src="plug-in/baidumap/js/api.js"></script>
    <script type="text/javascript" src="plug-in/baidumap/js/AreaRestriction_min.js"></script>
    <script type="text/javascript" src="plug-in/jquery/jquery.jqprint.js"></script>
    <link rel="stylesheet" href="plug-in/baidumap/css/print.css" type="text/css" media="print">
    <style type="text/css">
        /*隐藏百度logo*/
        .anchorBL{
            display: none;
        }

    </style>
    <div id="tempDiv" style="display: none;">
        <div style="padding:8px;padding-top: 0;background-color:#F5F5F5;">
            <div style="position: relative;left: 4px;top: 8px;width:51px;background-color: #F5F5F5;">等级图层</div>
            <div style="border:1px solid #C4C4C4;padding: 20px 40px;">
                <label><input id="level-a" name="Fruit" type="checkbox" onclick="levelastatue()" value="" />A级</label>
                <label style="margin-left:15px"><input id="level-b" name="Fruit" onclick="levelbstatue()" type="checkbox" value="" />B级</label>
                <label style="margin-left:15px"><input id="level-c" name="Fruit" onclick="levelcstatue()" type="checkbox" value="" />C级</label>
                <label style="margin-left:15px"><input id="level-d" name="Fruit" onclick="leveldstatue()" type="checkbox" value="" />D级、E级</label>
            </div>
        </div>
    </div>
    <div region="center" style="padding: 3px;" class="easyui-panel" id="demopanle">
        <div id="map" style="width:100%;height:100%"></div>
    </div>

    <div region="west" style="width: 400px;" title="单位隐患三违列表" split="true">
        <%--<div style="padding:8px;background-color:#F5F5F5;border-left:1pt solid #A4D3EE;border-right: 1pt solid #A4D3EE;">
            <div style="position: relative;left: 4px;top: 8px;width:51px;background-color: #F5F5F5;">等级图层</div>
            <div style="border:1px solid #C4C4C4;padding: 20px 40px;">
                <label><input id="level-a" name="Fruit" type="checkbox" value="" />A级</label>
                <label style="margin-left:15px"><input id="level-b" name="Fruit" type="checkbox" value="" />B级</label>
                <label style="margin-left:15px"><input id="level-c" name="Fruit" type="checkbox" value="" />C级</label>
                <label style="margin-left:15px"><input id="level-d" name="Fruit" type="checkbox" value="" />D级、E级</label>
            </div>
        </div>--%>
        <t:datagrid name="hiddenDangerVioList" checkbox="false" autoLoadData="false" fitColumns="false"
                    onDblClick="filterUnit" onLoadSuccess="loadSuccess" pagination="false" title="隐患三违列表"
                    actionUrl="riskAlertManageController.do?hiddenDangerVioDataGrid" idField="id" fit="true"
                    queryMode="group" >
            <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
            <t:dgCol title="单位ID" field="units" hidden="true" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="单位" field="unitname" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="隐患个数" align="center" field="hiddennum" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="三违个数" align="center" field="vionum" queryMode="single" width="120"></t:dgCol>
            <t:dgCol title="起止时间" field="queryDate" formatter="yyyy-MM-dd" hidden="true" query="true"
                     queryMode="group" width="120"></t:dgCol>
            <t:dgToolBar title="隐患三违单位分布柱状图" icon="icon-bar" funname="showHiddenBarChart"
                         operationCode="showPicture"></t:dgToolBar>
            <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
            <t:dgToolBar title="打印地图" icon="icon-print" funname="printMap" operationCode="print" ></t:dgToolBar>
            <t:dgToolBar title="全屏" icon="icon-full-screen" funname="showFullScreen"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    var backGroundColor = "";
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
    function ComplexCustomOverlay(point, text, mouseoverText, addressName) {
        this._point = point;
        this._text = text;
        this._overText = mouseoverText;

        //这里是新加的
        this._address = addressName;
    }
    ComplexCustomOverlay.prototype = new BMap.Overlay();
    ComplexCustomOverlay.prototype.initialize = function (map) {
        this._map = map;
        var div = this._div = document.createElement("div");
        div.style.position = "absolute";
        div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
        div.style.backgroundColor = backGroundColor;
        div.style.color = "white";
        //div.style.height = "70px";
        div.style.width = "70px";
        div.style.padding = "0";
        div.style.lineHeight = "18px";
        div.style.whiteSpace = "nowrap";
        div.style.MozUserSelect = "none";
        div.style.fontSize = "8px";
        //div.style.border = "2px solid #000000";
        var span = this._span = document.createElement("span");

        div.appendChild(span);
        span.appendChild(document.createTextNode(this._text));
        span.style.display = "none"
        var that = this;

        var arrow = this._arrow = document.createElement("div");
        var leveltemp = this._text.substring(24, 31);
        if(leveltemp=="level-a"){
            span.className = "level-a";
            arrow.style.background = "url(plug-in/baidumap/image/levela.png) no-repeat";
        }
        if(leveltemp=="level-b"){
            span.className = "level-b";
            arrow.style.background = "url(plug-in/baidumap/image/levelb.png) no-repeat";
        }
        if(leveltemp=="level-c"){
            span.className = "level-c";
            arrow.style.background = "url(plug-in/baidumap/image/levelc.png) no-repeat";
        }
        if(leveltemp=="level-d"){
            span.className = "level-d";
            arrow.style.background = "url(plug-in/baidumap/image/leveld.png) no-repeat";
        }
        arrow.style.position = "absolute";
        arrow.style.width = "20px";
        arrow.style.height = "27px";
        arrow.style.top = "80px";
        arrow.style.left = "0px";
        arrow.style.overflow = "hidden";

        div.appendChild(arrow);
        div.getElementsByTagName("span")[0].innerHTML = that._text;

        //这是新加的地点提示框    Author:张赛超
        var addressTip = document.createElement("p");
        addressTip.style.position = "absolute";
        addressTip.style.left = "-17px";
        addressTip.style.bottom = "-22px";
        addressTip.style.display = "block";
        //addressTip.innerHTML = G_addressName;
        addressTip.innerHTML = this._address;
        addressTip.style.textAlign = "center";
        addressTip.style.background = "#FFFFFF";
        addressTip.style.color = "red";
        addressTip.style.fontWeight = "bold";
        addressTip.style.width = "100px";
        addressTip.style.height = "20px";
        div.appendChild(addressTip);
        addressTip.style.display = "none";
        //新添加提示框结束

        div.onmouseover = function () {
            /*this.style.backgroundColor = "#6BADCA";
             this.style.borderColor = "#0000ff";
             this.getElementsByTagName("span")[0].innerHTML = that._overText;
             arrow.style.backgroundPosition = "0px -20px";*/
            this.style.border = "2px solid #000000";
            this.style.zIndex = "99999";

            //设置地点提示框是否可见：是
            span.style.display = "block";
            addressTip.style.display = "block";

        }

        div.onmouseout = function () {
            /* this.style.backgroundColor = "#EE5D5B";
             this.style.borderColor = "#BC3B3A";
             this.getElementsByTagName("span")[0].innerHTML = that._text;
             arrow.style.backgroundPosition = "0px 0px";*/
            this.style.border = "none";
            this.style.zIndex = "-1";

            //设置地点提示框是否可见：否
            if(span.className == "level-a"){
                if($('#level-a').attr('checked')){
                    span.style.display = "block";
                }else{
                    span.style.display = "none";
                }
            }else if(span.className == "level-b"){
                if($('#level-b').attr('checked')){
                    span.style.display = "block";
                }else{
                    span.style.display = "none";
                }

            }else if(span.className == "level-c"){
                if($('#level-c').attr('checked')){
                    span.style.display = "block";
                }else{
                    span.style.display = "none";
                }

            }else{
                if($('#level-d').attr('checked')){
                    span.style.display = "block";
                }else{
                    span.style.display = "none";
                }

            }


            addressTip.style.display = "none";

        }

        map.getPanes().labelPane.appendChild(div);
        return div;
    }
    ComplexCustomOverlay.prototype.draw = function () {
        var map = this._map;
        var pixel = map.pointToOverlayPixel(this._point);
        this._div.style.left = pixel.x - parseInt(this._arrow.style.left) - 10 + "px";
        this._div.style.top = pixel.y - 25 - parseInt(this._arrow.style.top) + "px";
    }


    function refreshVioHiddenGis(unitId) {
        var pro = window.top.$.messager;
        if (pro) {
            window.top.$.messager.progress({
                text: '正在加载数据....',
                interval: 300
            });
        }

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
            }, error: function () {
            }
        });

        var url = "riskAlertManageController.do?getHiddenDangerVioListByUnitAndLevel&type=${type}&unitId=" + unitId + "&queryDate_begin=" + $("input[name='queryDate_begin']").val() + "&queryDate_end=" + $("input[name='queryDate_end']").val();
        $.ajax({
            type: 'POST',
            url: url,
            success: function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    if (pro)
                        window.top.$.messager.progress('close');
                    map.clearOverlays();
                    $(d.attributes.resultList).each(function (index, d) {
                        var addressName = d.addressName;

                        //这里将addressName变为全局变量G_addressName，否则变量跳不出闭包
                        // window.G_addressName = addressName;

                        var addressid = d.addressId;
                        var lon = d.lon;
                        var lat = d.lat;
                        var hiddennum_A = checkUndefinedReturn(d.hiddennum_A);
                        var vionum_A = checkUndefinedReturn(d.vionum_A);
                        var hiddennum_B = checkUndefinedReturn(d.hiddennum_B);
                        var vionum_B = checkUndefinedReturn(d.vionum_B);
                        var hiddennum_C = checkUndefinedReturn(d.hiddennum_C);
                        var vionum_C = checkUndefinedReturn(d.vionum_C);
                        var hiddennum_DE = checkUndefinedReturn(d.hiddennum_DE);
                        var vionum_DE = checkUndefinedReturn(d.vionum_DE);
                        var alertLevelColor = "";

                        if(hiddennum_A+vionum_A>0){
                            alertLevelColor = alertLevelColor_A ;
                            var sContent = "<table border='0' name='level-a' border='1' cellpadding='0' cellspacing='0' ><tr style='background-color: #fffded'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>等级</h4></td><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>隐患</h4></td><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>三违</h4></td></tr>"

                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>A</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"2\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_A + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"1\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_A + "</h4></a></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>B</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"3\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_B + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"2\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_B + "</h4></a></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>C</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"4\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_C + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"3\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_C + "</h4></a></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>DE</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"5,6\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_DE + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"4,5\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_DE + "</h4></a></td></tr>"
                                    + "</table>";
                        }else if(hiddennum_B+vionum_B)
                        {
                            alertLevelColor = alertLevelColor_B ;
                            var sContent = "<table border='0' name='level-b' border='1' cellpadding='0' cellspacing='0' ><tr style='background-color: #fffded'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>等级</h4></td><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>隐患</h4></td><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>三违</h4></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>A</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"2\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_A + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"1\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_A + "</h4></a></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>B</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"3\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_B + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"2\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_B + "</h4></a></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>C</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"4\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_C + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"3\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_C + "</h4></a></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>DE</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"5,6\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_DE + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"4,5\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_DE + "</h4></a></td></tr>"
                                    + "</table>";
                        }else if(hiddennum_C+vionum_C)
                        {
                            alertLevelColor = alertLevelColor_C ;
                            var sContent = "<table border='0' name='level-c' border='1' cellpadding='0' cellspacing='0' ><tr style='background-color: #fffded'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>等级</h4></td><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>隐患</h4></td><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>三违</h4></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>A</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"2\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_A + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"1\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_A + "</h4></a></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>B</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"3\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_B + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"2\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_B + "</h4></a></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>C</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"4\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_C + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"3\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_C + "</h4></a></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>DE</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"5,6\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_DE + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"4,5\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_DE + "</h4></a></td></tr>"
                                    + "</table>";
                        }else if(hiddennum_DE+vionum_DE)
                        {
                            alertLevelColor = alertLevelColor_DE ;
                            var sContent = "<table border='0' name='level-d' border='1' cellpadding='0' cellspacing='0' ><tr style='background-color: #fffded'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>等级</h4></td><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>隐患</h4></td><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>三违</h4></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>A</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"2\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_A + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"1\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_A + "</h4></a></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>B</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"3\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_B + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"2\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_B + "</h4></a></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>C</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"4\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_C + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"3\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_C + "</h4></a></td></tr>"
                                    + "<tr style='background-color: " + checkColor(alertLevelColor) + "'><td align='center'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>DE</h4></td><td align='center'><a style='color:black;' onclick='showHiddenDangerList(\"" + unitId + "\",\"" + addressid + "\",\"5,6\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + hiddennum_DE + "</h4></a></td><td align='center'><a style='color:black;' onclick='showThreeVioList(\"" + unitId + "\",\"" + addressid + "\",\"4,5\")'><h4 style='margin:0 0 0px 0;padding:0.1em 0'>" + vionum_DE + "</h4></a></td></tr>"
                                    + "</table>";
                        }


                        var myCompOverlay = new ComplexCustomOverlay(new BMap.Point(lon, lat), sContent, sContent, addressName);
                        map.addOverlay(myCompOverlay);
                    });
                    levelastatue();
                    levelbstatue();
                    levelcstatue();
                    leveldstatue();
                }
            },
            error: function () {
            }


        });

    }

    function checkUndefinedReturn(obj) {
        if (typeof(obj) == "undefined") {
            return 0;
        } else {
            return obj;
        }
    }

    function checkColor(obj){
        if (obj.indexOf("#") > -1) {
            return obj;
        } else {
            return "#fffded";
        }
    }

    //数据加载成功后刷新地图，默认显示全矿
    function loadSuccess() {
        map.panTo(new BMap.Point(0,0));
        refreshVioHiddenGis("allmine");
        $("div[class='panel datagrid']>div[class='panel-header']").hide();
        if("${citeSource}"=="home"){
            $("div[class='panel layout-panel layout-panel-west layout-split-west']>div[class='panel-header']").hide();
        }
    }
    function filterUnit(rowIndex, rowData) {
        refreshVioHiddenGis(rowData.units);
    }
    //弹出三违列表
    function showThreeVioList(unitId, addressid,vioLevel) {
        var url = "riskAlertManageController.do?threeVioList&vioLevel="+vioLevel+"&vioUnits=" + unitId + "&vioAddressId=" + addressid + "&queryDate_begin=" + $("input[name='queryDate_begin']").val() + "&queryDate_end=" + $("input[name='queryDate_end']").val();
        createdetailwindow('三违列表', url, 800);
    }
    //弹出隐患问题列表
    function showHiddenDangerList(unitId, addressid,hiddenNature) {
        var url = "riskAlertManageController.do?hiddenDangerList&hiddenNature="+hiddenNature+"&dutyunit=" + unitId + "&addressId=" + addressid + "&queryDate_begin=" + $("input[name='queryDate_begin']").val() + "&queryDate_end=" + $("input[name='queryDate_end']").val();
        createdetailwindow('问题列表', url, 800);
    }
    //弹出隐患三违单位分布柱状图
    function showHiddenBarChart() {
        var url = "riskAlertManageController.do?showHiddenVioUnitDisBarChart&type=${type}&queryDate_begin=" + $("input[name='queryDate_begin']").val() + "&queryDate_end=" + $("input[name='queryDate_end']").val();
        createdetailwindownobutton('隐患三违单位分布柱状图', url, 1000, 430);
    }

    //全屏显示
    function showFullScreen() {
        var url = "riskAlertManageController.do?hiddenDangerViolist&type=${type}";
        createdetailwindow('单位隐患分布', url, "100%", "100%");
    }

    $(function () {
        var datagrid = $("#hiddenDangerVioListtb");
        datagrid.find("form[id='hiddenDangerVioListForm']").prepend($("#tempDiv").html());
        $("#tempDiv").empty();
        //给时间控件加上样式
       $("#hiddenDangerVioListtb").find("input[name='queryDate_begin']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#hiddenDangerVioListtb").find("input[name='queryDate_end']").attr("class", "Wdate").attr("style", "height:20px;width:90px;").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("input[name='queryDate_begin']").val("${startTime}");
        $("div[class='panel datagrid']>div[class='panel-header']").hide();
        if("${citeSource}"=="home"){
            $("div[class='panel layout-panel layout-panel-west layout-split-west']>div[class='panel-header']").hide();
        }
         setTimeout(hiddenDangerVioListsearch, 200);
    });
    //导出
    function ExportXls() {
        JeecgExcelExport("riskAlertManageController.do?exportXlsByType&type=${type}", "hiddenDangerVioList");
    }

</script>
<script type="text/javascript">
    function levelastatue(){
        if($('#level-a').attr('checked')){
            $("span[class='level-a']").css("display","block");
        }else{
            $("span[class='level-a']").css("display","none");
        }
    }
    function levelbstatue(){
        if($('#level-b').attr('checked')){
            $("span[class='level-b']").css("display","block");
        }else{
            $("span[class='level-b']").css("display","none");
        }
    }
    function levelcstatue(){
        if($('#level-c').attr('checked')){
            $("span[class='level-c']").css("display","block");
        }else{
            $("span[class='level-c']").css("display","none");
        }
    }
    function leveldstatue() {
        if($('#level-d').attr('checked')){
            $("span[class='level-d']").css("display","block");
        }else{
            $("span[class='level-d']").css("display","none");
        }
    }
    document.getElementById('level-a').onclick = function(){
        levelastatue();
    }

    document.getElementById('level-b').onclick = function(){
        levelbstatue();
    }

    document.getElementById('level-c').onclick = function(){
        levelcstatue();
    }

    document.getElementById('level-d').onclick = function(){
        leveldstatue();
    }

    //重写重置按钮时间
    $("a[iconcls='icon-reload']").bind("click", function(){
        $("input[name='queryDate_begin']").val("${startTime}");
    });

    function createdetailwindownobutton(title, addurl,width,height) {
        width = width?width:700;
        height = height?height:400;
        if(width=="100%" || height=="100%"){
            width = window.top.document.body.offsetWidth;
            height =window.top.document.body.offsetHeight-100;
        }
        if(typeof(windowapi) == 'undefined'){
            $.dialog({
                content: 'url:'+addurl,
                lock : true,
                width:width,
                height: height,
                title:title,
                opacity : 0.3,
                cache:false,
                max:false,
                min:false,
                drag:false,
                cancelVal: '关闭',
                cancel: true /*为true等价于function(){}*/
            }).zindex();
        }else{
            W.$.dialog({
                content: 'url:'+addurl,
                lock : true,
                width:width,
                height: height,
                parent:windowapi,
                title:title,
                opacity : 0.3,
                cache:false,
                max:false,
                min:false,
                drag:false,
                cancelVal: '关闭',
                cancel: true /*为true等价于function(){}*/
            }).zindex();
        }

    }

    (function(){
        $(".datagrid-toolbar").css("height","auto");
        $(".datagrid-toolbar span").css("float","none");
    })();
    function printMap(){
        $("#map").jqprint({
            debug: true,
            importCSS: true,
            printContainer: true,
            operaSupport: false
        });
    }

</script>