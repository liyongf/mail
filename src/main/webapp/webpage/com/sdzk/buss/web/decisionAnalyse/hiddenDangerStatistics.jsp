<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%
    session.setAttribute("lang","zh-cn");
%>
<base href="${webRoot}/">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/Highcharts-5.0.11/code/highcharts.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-3d.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-zh_CN.js"></script>

<table style="width: 100%;height: auto;margin-top:25px;">
    <tr>
        <td colspan="2" style="text-align: center">
            <form id="thisForm" method="post">
                日期:&nbsp;&nbsp;<input id="date" name="date" value="${date}">&nbsp;&nbsp;&nbsp;&nbsp;
                <a class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="doQuery()" id="">
                    查询</a>
            </form>
        </td>
    </tr>
    <tr>
        <td style="width: 90%">
            <div id="hdTypeContainer" style="width: 90%; height: 400px; margin: 0 auto"></div>
        </td>
    </tr>
    <tr>
        <td style="width: 90%">
            <div id="hdLevelContainer" style="width: 90%; height: 400px; margin: 0 auto"></div>
        </td>
    </tr>
</table>

<script type="text/javascript">

    function doQuery(){
        hdTypeFillSeries(hdTypePieSeries);
        hdLevelFillSeries(hdLevelPieSeries);
    }
    var hdTypePieSeries;
    var hdTypeChart;
    var hdLevelPieSeries;
    var hdLevelChart;
    $(function () {
        $("#date").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("div[class='panel-header']").hide();
        hdTypeChart = new Highcharts.Chart({
            chart: {
                renderTo: 'hdTypeContainer',
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 45,
                    beta: 0
                } ,
                events: {
                    load: function() {
                        hdTypePieSeries = this.series[0];
                        setInterval(function(){hdTypeFillSeries(hdTypePieSeries);}, 300000);
                    }
                }
            },
            exporting: {
                enabled:false
            },
            credits: {
                enabled:false
            },
            title: {
                text: '<b>隐患类型统计</b>'
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.point.name +'</b>: '+ this.percentage.toFixed(2) +'%'+" （数量："+this.point.y+"）";
                }
            },
            plotOptions: {

                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    depth: 50,
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.2f} %',
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    },
                    showInLegend:true
                }
            },
            /*legend:{
                layout :'vertical',
                verticalAlign :'top',
                align :'left',
                x:20,
                y:50
            },*/
            series: [{
                type: 'pie',
                name: 'Browser share',
                data: [
                ],
                events: {
                    click: function(e) {

                    }
                }
            }]
        });

        hdLevelChart = new Highcharts.Chart({
            chart: {
                renderTo: 'hdLevelContainer',
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 45,
                    beta: 0
                } ,
                events: {
                    load: function() {
                        hdLevelPieSeries = this.series[0];
                        setInterval(function(){hdLevelFillSeries(hdLevelPieSeries);}, 300000);
                    }
                }
            },
            exporting: {
                enabled:false
            },
            credits: {
                enabled:false
            },
            title: {
                text: '<b>隐患级别统计</b>'
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.point.name +'</b>: '+ this.percentage.toFixed(2) +'%'+" （数量："+this.point.y+"）";
                }
            },
            plotOptions: {

                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    depth: 50,
                    dataLabels: {
                        enabled: true,
                        format: '<b>{point.name}</b>: {point.percentage:.2f} %',
                        style: {
                            color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                        }
                    },
                    showInLegend:true
                }
            },
            series: [{
                type: 'pie',
                name: 'Browser share',
                data: [
                ],
                events: {
                    click: function(e) {

                    }
                }
            }]
        });
        hdTypeFillSeries(hdTypePieSeries);
        hdLevelFillSeries(hdLevelPieSeries);
    })
    function hdTypeFillSeries(series){
        var date = $("#date").val();
        $.ajax({
            type:"post",
            url:"mobile/mobileDecisionAnalyseController.do?hdType",
            data:{date:date},
            dataType:"json",//设置需要返回的数据类型
            success:function(data){
                series.setData(data.pieData.pieData);
            },
            error:function(){
            }
        });
    }
    function hdLevelFillSeries(series){
        var date = $("#date").val();
        $.ajax({
            type:"post",
            url:"mobile/mobileDecisionAnalyseController.do?hdLevel",
            data:{date:date},
            dataType:"json",//设置需要返回的数据类型
            success:function(data){
                series.setData(data.pieData.pieData);
            },
            error:function(){
            }
        });
    }

</script>