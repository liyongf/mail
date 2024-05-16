<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>

<%
    session.setAttribute("lang","zh-cn");
%>
<base href="${webRoot}">
<%--<meta name="viewport" content="width=device-width, initial-scale=1.0">--%>

<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript" src="plug-in/baidumap/js/api.js"></script>
<script type="text/javascript" src="plug-in/baidumap/js/AreaRestriction_min.js"></script>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<style type="text/css">
    .anchorBL {
        display: none;
    }
</style>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 3px;" class="easyui-panel" id="demopanle">
        <div id="map" style="height: 92%;width: 100%;"></div>
    </div>
    <div region="west" style="width: 0px;" title="地点列表" split="true">
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
        div.style.border = "1px solid #BC3B3A";
        div.style.color = "black";
        div.style.height = "18px";
        div.style.padding = "2px";
        div.style.lineHeight = "18px";
        div.style.whiteSpace = "nowrap";
        div.style.MozUserSelect = "none";
        div.style.fontSize = "12px";
        var span = this._span = document.createElement("span");
        div.appendChild(span);
        span.appendChild(document.createTextNode(this._text));
        var that = this;

        var arrow = this._arrow = document.createElement("div");
        arrow.style.background = "url(plug-in/baidumap/image/label.png) no-repeat";
        arrow.style.position = "absolute";
        arrow.style.width = "11px";
        arrow.style.height = "10px";
        arrow.style.top = "22px";
        arrow.style.left = "10px";
        arrow.style.overflow = "hidden";
        div.appendChild(arrow);

        div.onmouseover = function () {
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
        }

        map.getPanes().labelPane.appendChild(div);

        return div;
    }
    ComplexCustomOverlay.prototype.draw = function () {
        var map = this._map;
        var pixel = map.pointToOverlayPixel(this._point);
        this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
        this._div.style.top = pixel.y - 30 + "px";
    }
    //添加监听事件
    ComplexCustomOverlay.prototype.addEventListener = function (event, fun) {
        this._div['on' + event] = fun;
    }


    function refreshAddress() {
        var pro = window.top.$.messager;
        if (pro) {
            window.top.$.messager.progress({
                text: '正在加载数据....',
                interval: 300
            });
        }
        var url = "tBAddressInfoController.do?getAllAddressListWithLevel";
        $.ajax({
            type: 'POST',
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
</script>
