<%--
  Created by IntelliJ IDEA.
  User: xuran
  Date: 2018/3/22
  Time: 9:24
  Name: 三违等级分布
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools"></t:base>
<script src="plug-in/Highcharts-5.0.11/code/highcharts.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-3d.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-zh_CN.js"></script>
<html>
<head>
    <title>三违等级分布</title>
</head>
<body>
<div id="main" style="width: 98%;height:98%;"></div>

</body>
<script type="text/javascript">
    var threeVioLevelChart;
    var threeVioLevelPieSeries;
    $(function () {
        threeVioLevelChart = new Highcharts.Chart({
            chart: {
                renderTo: 'main',
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 45,
                    beta: 0
                },
                events: {
                    load: function () {
                        threeVioLevelPieSeries = this.series[0];
//                        setInterval(function () {
//                            hdLevelFillSeries(threeVioLevelPieSeries);
//                        }, 300000);
                    }
                }
            },
            exporting: {
                enabled: false
            },
            credits: {
                enabled: false
            },
            title: {
                text: '<b>违章级别</b>'
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.point.name + '</b>: ' + this.percentage.toFixed(2) + '%' + " （数量：" + this.point.y + "）";
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
                    showInLegend: true
                }
            },
            series: [{
                type: 'pie',
                name: 'Browser share',
                data: [],
                events: {
                    click: function (e) {
                        threeVioList(e.point.id)
                    }
                }
            }]
        });
        threeVioLevelFillSeries(threeVioLevelPieSeries);
    })

    function threeVioLevelFillSeries(series){
        $.ajax({
            type:"post",
            url:"threeVioAnalyseController.do?threeVioLevelStatistics&startDate=${startDate}&endDate=${endDate}",
            dataType:"json",//设置需要返回的数据类型
            success:function(data){
                series.setData(data.pieData.pieData);
            },
            error:function(){
            }
        });
    }

    function threeVioList(threeVioLevel){
        if (typeof(windowapi) == 'undefined') {
            $.dialog({content: "url:threeVioAnalyseController.do?threeVioList&threeVioLevel="+threeVioLevel, zIndex: getzIndex(), title: '违章列表', lock: true, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
                <%--{name: '<t:mutiLang langKey="common.confirm"/>', callback: function () {}, focus: true},--%>
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        } else {
            $.dialog({content: "url:threeVioAnalyseController.do?threeVioList&threeVioLevel="+threeVioLevel, zIndex: getzIndex(), title: '违章列表', lock: true, parent: windowapi, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
                <%--{name: '<t:mutiLang langKey="common.confirm"/>', callback: function () {}, focus: true},--%>
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        }
    }
</script>
</html>
