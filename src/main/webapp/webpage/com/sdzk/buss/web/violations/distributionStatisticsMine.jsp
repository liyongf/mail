<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>三违分布统计</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/Highcharts-5.0.11/code/highcharts.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-3d.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/modules/exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/modules/offline-exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-zh_CN.js"></script>
<style media="print">
    <%--隐藏页眉页脚--%>
    @page {
        size: auto;  /* auto is the initial value */
        margin: 0mm; /* this affects the margin in the printer settings */
    }
    <%--隐藏无关的元素--%>
    #abc {display:none}
</style>
<link rel="stylesheet" href="plug-in/graphreport/css/report.css" type="text/css">
<SCRIPT type="text/javascript">
    var deptList = new Array();
    <c:forEach items="${unitsName}" var="unit" varStatus="status">
        deptList.push("${unit}");
    </c:forEach>
    var deptnames1 = new Array(); //deptnames1是用来放置处理后的数组
    for(var i = 0; i < deptList.length;i++){ //deptnames是处理前的数组
        var deptname = deptList[i];
        var str = deptname.charAt(0);
        for( var j = 1; j < deptname.length; j++){
            str = str + '<br/>' + deptname.charAt(j);
        }
        deptnames1.push(str);
    }
    var chart;
    var chartPie;

    $(function () {
        $("#containerPie").hide();
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'container',
                type: 'column',
                margin: [ 50, 50, 100, 80]
            },
            title: {
                text: '三违分布统计'
            },
            xAxis: {
                categories:deptnames1,
                labels: {
                    rotation: 0,
                    align: 'right',
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: '人<br/>次',
                    rotation:0
                }
            },
            credits: {
                enabled: false
            },
            legend: {
                enabled: false
            },
            tooltip: {
                pointFormat: '{point.y}</b>'
            },credits: {
                enabled: false
            },
            series: [{
                data: [${datasb}]
            }]
        });

        $("#queryMonth").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){
            WdatePicker({dateFmt:'yyyy-MM'});
        });
        $("#startDate").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){
            WdatePicker({dateFmt:'yyyy-MM-dd'});
        });
        $("#endDate").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){
            WdatePicker({dateFmt:'yyyy-MM-dd'});
        });

        var isMonthQuery = "${isMonthQuery}";
        var startDate = "${startDate}";
        var endDate = "${endDate}";
        $("#isMonthQuery").children("option[value="+isMonthQuery+"]").attr("selected","selected");
        if(isMonthQuery == '0'){
            $("#additionCondition").show();
            $("#queryMonth").val("");
            $("#queryMonth").hide();

            $("#startDate").val(startDate);
            $("#endDate").val(endDate);
        }else if(isMonthQuery == '1'){
            var queryMonth = "${queryMonth}";
            $("#queryMonth").val(queryMonth);

            $("#startDate").val("");
            $("#endDate").val("");
            $("#additionCondition").hide();
            $("#queryMonth").show();
        }else{
            $("#additionCondition").hide();
            $("#queryMonth").show();
        }

        chartPie = new Highcharts.Chart({
            chart: {
                renderTo: 'containerPie',
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 45,
                    beta: 0
                }
            },
            title: {
                text:  '三违人次'
            },
            tooltip: {

                formatter: function() {
                    return '<b>'+ this.point.name +'</b>: '+ this.percentage.toFixed(0) +' %';
                }
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    depth: 35,
                    dataLabels: {
                        enabled: true,
                        formatter: function() {
                            return '<b>'+ this.point.name +'</b>: '+ this.percentage.toFixed(0) +' %';
                        }
                    }
                }
            },exporting: { enabled: false },
            series: [{
                type: 'pie',
                name: '',
                data: [
                    <c:forEach items="${retMap}" var="unitMap" varStatus="status">
                    ["${unitMap.unit}" ,${unitMap.count}]<c:if test="${!status.last}">,</c:if>
                    </c:forEach>
                ]
            }]
        });
    });

function distributionStatisticsJTsearch(){
   $("#thisForm").submit();
}
function distributionStatisticsJTsearchReset(){
    $("#startDate").val("");
    $("#endDate").val("");
    $("#thisForm").submit();
}
function showColumn(){
    $("#container").show();
    $("#containerPie").hide();
}
function showPie(){
    $("#container").hide();
    $("#containerPie").show();
}
function changeQueryType(){
    var type = $("#isMonthQuery option:selected").val();
    if(type=="1"){
        $("#additionCondition").hide();
        $("#queryMonth").show();
    }else{
        $("#additionCondition").show();
        $("#queryMonth").hide();
    }
}
</SCRIPT>
</head>
<body style="overflow: scroll">
<form id="thisForm" name="thisForm" action="tBThreeViolationsController.do?distributionStatisticsMine" method="post">
<%--<div id="tempSearchColums">
    <div name="searchColums">
        <span style="display:inline-block;">
            <span style="width: 120px; text-align: right; overflow: hidden; vertical-align: middle; display: inline-block; white-space: nowrap; -ms-text-overflow: ellipsis; -o-text-overflow: ellipsis;" title="日期">
             	   日期：
            </span>
           <input readonly="true" type="text" id="startDate" name="startDate" style="width: 100px" value="${startDate.substring(0,7)}" />
        </span>
    </div>
    <div class="datagrid-toolbar" style="height:30px;">
        <span style="float:right">
        <a id="" class="easyui-linkbutton l-btn" onclick="distributionStatisticsJTsearch()" iconcls="icon-search" href="#">
        <span >查询</span>
        </a>
        <a id="" class="easyui-linkbutton l-btn" onclick="distributionStatisticsJTsearchReset()" iconcls="icon-reload" href="#">
        <span>重置</span>
        </a>
        </span>
    </div>
</div>--%>
    <fieldset class="queryfieldset">
        <legend>信息查询</legend>
        <div id="tempSearchColums">
            <div name="searchColums">
                <span style="display:inline-block;margin-left: 50px;">
                    查询方式：
                    <select name="isMonthQuery" id="isMonthQuery" onchange="changeQueryType()">
                        <option value="1">按月查询</option>
                        <option value="0">按起止时间查询</option>
                    </select>
                </span>
                <span style="display:inline-block;">
                    <span style="width: 120px; text-align: right; overflow: hidden; vertical-align: middle; display: inline-block; white-space: nowrap; -ms-text-overflow: ellipsis; -o-text-overflow: ellipsis;" title="日期">
                           查询时间：
                    </span>
                    <input type="text" id="queryMonth" name="queryMonth" style="width: 100px" value="" />
                    <span id="additionCondition" style="display: none;">
                        <input type="text" id="startDate" name="startDate" style="width: 100px" value=""/>
                        <span style="width: 8px; text-align: right;">~</span>
                        <input type="text" id="endDate" name="endDate" style="width: 100px" value=""/>
                    </span>
                </span>
                <span style="float:right;margin-right: 10px;margin-bottom: 10px;">
                    <a id="111" class="easyui-linkbutton l-btn" onclick="distributionStatisticsJTsearch()" iconcls="icon-search"
                       href="#">
                        <span>查询</span>
                    </a>
                    <a id="555" class="easyui-linkbutton l-btn" onclick="distributionStatisticsJTsearchReset()" iconcls="icon-reload"
                       href="#">
                        <span>重置</span>
                    </a>
                </span>
            </div>
        </div>
    </fieldset>
    <div id="tabpanel_items" class="tabpanel js-um-tab" style="padding-top: 10px;">
        <ul class="borders">
            <li class="on" datatype="1" onclick="showColumn();">柱状图</li>
            <li datatype="1"  onclick="showPie();">饼状图</li>
        </ul>
    </div>
<div id="container" style="min-width:700px;height:100%"></div>
<div id="containerPie" style="min-width:700px;height:100%"></div>
</form>
</body>
</html>
