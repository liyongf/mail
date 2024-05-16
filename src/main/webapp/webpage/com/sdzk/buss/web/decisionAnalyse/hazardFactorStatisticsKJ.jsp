<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 17-7-14
  Time: 上午9:08
  To change this template use File | Settings | File Templates.
  Author:张赛超
  QQ:1228310398
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/Highcharts-5.0.11/code/highcharts.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-3d.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/modules/exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/modules/offline-exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-zh_CN.js"></script>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<style media="print">
    <%--隐藏页眉页脚--%>
    @page {
        size: auto;  /* auto is the initial value */
        margin: 0mm; /* this affects the margin in the printer settings */
    }
    <%--隐藏无关的元素--%>
    #abc {display:none}
</style>
<html>
<head>
    <title>风险管控措施检查分析</title>
</head>
<body style="width: 99%">
<table style="width: 100%;height: auto">
    <tr>
        <td style="text-align: center">
            <form id="thisForm" action="tBDecisionAnalyseController.do?hazardFactorStatistics" method="post">


                分析类型：&nbsp;&nbsp;
                <t:dictSelect field="hazardFactorStatisticsType" type="list" extendJson="{\"datatype\":\"*\"}"
                              typeGroupCode="hazardFactorStatisticsType"  hasLabel="false"  title="分析类型" defaultVal="${hazardFactorStatisticsType}"></t:dictSelect>&nbsp;&nbsp;
                年度: &nbsp;<input id="year" name="year"  />&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="doQuery()" id="">
                    查询
                </a>
            </form>

        </td>
    </tr>

    <tr></tr>

    <tr>
        <td>
            <div id="container" style="min-width:400px;height:400px"></div>
        </td>
    </tr>
</table>

<%--这个div限制table的宽高--%>
<div style="width: 100%; height: 50%;">
<%--下面添加统计图表--%>
    <%--<table id="jeecgEasyUIList" >--%>
        <%--<thead>--%>
        <%--<tr>--%>
            <%--<th field="month" width="30">月份</th>--%>
            <%--<th field="riskNum" width="30">风险数</th>--%>
            <%--<th field="dangerNum" width="30">隐患数</th>--%>
            <%--<th field="majorRiskNum" width="30">重大风险数</th>--%>
            <%--<th field="total" width="30">总数</th>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<c:forEach items="${list}" var="tsType">--%>
                <%--<td field="vio_qualitative${tsType.typecode}" width="30">${tsType.typename}</td>--%>
                <%--<td field="vio_qualitative${tsType.typecode}" width="30">${tsType.typename}</td>--%>
                <%--<td field="vio_qualitative${tsType.typecode}" width="30">${tsType.typename}</td>--%>
                <%--<td field="vio_qualitative${tsType.typecode}" width="30">${tsType.typename}</td>--%>
                <%--<td field="vio_qualitative${tsType.typecode}" width="30">${tsType.typename}</td>--%>
            <%--</c:forEach>--%>
        <%--</tr>--%>
        <%--</thead>--%>
    <%--</table>--%>

    <t:datagrid name="lineList" fitColumns="true" title="${year}风险管控措施检查分析" pagination="false"
                onLoadSuccess = "tipMsg" actionUrl="tBDecisionAnalyseController.do?hazardFactorStatisticsKJDatagrid&year=${year}"
                idField="id" fit="true" queryMode="group">
        <%--<t:dbCol title="唯一标示" field="id" hidden="true" width="120"></t:dbCol>--%>
        <t:dgCol title="月份"  field="month" queryMode="single"  width="120"></t:dgCol>
        <t:dgCol title="管控次数"  field="danger" queryMode="group"  width="120"></t:dgCol>
    </t:datagrid>

</div>

</body>
</html>

<script type="text/javascript">
    $(function () {
        $("#year").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy'});});
        $("input[name='year']").val("${year}");
        redrawChart();
    });

    function doQuery(){
        $("#thisForm").submit();
    }

    function redrawChart(){

        $('#container').highcharts({
            chart: {
                type: 'line'
            },
            title: {
                text: '${year}风险管控效果分析'
            },
            xAxis: {
                categories: ['一月', '二月', '三月', '四月', '五月', '六月',
                    '七月', '八月', '九月', '十月', '十一月', '十二月']
            },
            yAxis: {
                title: {
                    text: '数量'
                },
                labels: {
                    formatter: function () {
                        return this.value ;
                    }
                }
            },
            tooltip: {
                crosshairs: true,
                shared: true
            },
            plotOptions: {
                spline: {
                    marker: {
                        radius: 4,
                        lineColor: '#666666',
                        lineWidth: 1
                    }
                }
            },
            series: [
                /*{
                    name: '风险',
                    data: [${riskData}],
                    /!*events:{
                        click:function(e){
                            var riskDate = changeMonth(e.point.x);
                            createdetailwindow("查看","tBDecisionAnalyseController.do?riskList&statisticType=1&year=${year}&&yearMonth=" + riskDate,800,600);
                            //createdetailwindow("查看","tBDecisionAnalyseController.do?dangerList&statisticType=1&year=${year}&&yearMonth=" + riskDate,800,600);
                        }
                    }*!/
                },*/
                {
                    name: '管控次数',
                    marker: {
                        symbol: 'diamond'
                    },
                    data: [${countNumData}],
                    /*events:{
                        click:function(e){
                            var dangerData = changeMonth(e.point.x);
                            createdetailwindow("查看","tBDecisionAnalyseController.do?mineYearDangerList&statisticType=1&yearMonth=" + dangerData,800,600);
                        }
                    }*/
                }
            ]
        });
    }

    function changeMonth(m){
        var ym;
        m = m + 1;
        if(m < 10){
            ym = ${year} + "-0" + m;
        }else if(m <=12){
            ym = ${year} + "-" + m;
        }
        return ym;
    }

</script>
