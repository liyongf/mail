<%--
  Created by IntelliJ IDEA.
  User: xuran
  Date: 2018/3/22
  Time: 9:24
  Name: 专项辨识类型分布
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
    <title>专项辨识类型分布</title>
</head>
<body>
<div id="main" style="width: 98%;height:98%;"></div>
<script type="text/javascript">
    var speEvaTypeChart;
    var speEvaTypePieSeries;
    $(function () {
        speEvaTypeChart = new Highcharts.Chart({
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
                        speEvaTypePieSeries = this.series[0];
//                        setInterval(function () {
//                            hdLevelFillSeries(speEvaTypePieSeries);
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
                text: '<b>专项辨识类型分布</b>'
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
                        riskList(e.point.id);
                    }
                }
            }]
        });
        speEvaTypeFillSeries(speEvaTypePieSeries);
    })

    function speEvaTypeFillSeries(series){
        $.ajax({
            type:"post",
            url:"riskAnalyseController.do?speEvaTypeStatistics",
            dataType:"json",//设置需要返回的数据类型
            success:function(data){
                series.setData(data.pieData.pieData);
            },
            error:function(){
            }
        });
    }

    function riskList(seType){
        if (typeof(windowapi) == 'undefined') {
            $.dialog({content: "url:riskAnalyseController.do?riskList&seType="+seType, zIndex: getzIndex(), title: '风险列表', lock: true, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
                <%--{name: '<t:mutiLang langKey="common.confirm"/>', callback: function () {}, focus: true},--%>
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        } else {
            $.dialog({content: "url:riskAnalyseController.do?riskList&seType="+seType, zIndex: getzIndex(), title: '风险列表', lock: true, parent: windowapi, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
                <%--{name: '<t:mutiLang langKey="common.confirm"/>', callback: function () {}, focus: true},--%>
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        }
    }
</script>

</body>
</html>
