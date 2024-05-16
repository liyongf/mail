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
    var barchart;
    $(function () {
    	barchart = $('#container').highcharts({
            chart: {
                type: 'column',
                backgroundColor : '#ffffff'
            },
            title: {
                text: '隐患三违单位分布柱状图',
                style: {
                    color: "#000000",
                    fontSize: "18px"
                }
            },
            xAxis: {
                labels: {
                    style: {
                        color: '#000000'
                    }
                },
                categories: [${dataunits}]
            },
            yAxis: {
                min: 0,
                title: {
                    text: '三违问题数',
                    style: {
                        color: "#000000",
                        fontWeight: 'normal'
                    }
                },
                labels: {
                    style: {
                        color: '#333333'
                    }
                },
                stackLabels: {
                    enabled: true,
                    style: {
                        fontWeight: 'bold',
                        color: '#444',
                        textShadow: '0 0 6px #AAA'
                    }
                },
                gridLineColor: '#CCC'
            },
            legend: {
                align: 'right',
                x: -70,
                verticalAlign: 'top',
                y: 20,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false,
                itemStyle: {
                    color: '#333333',
                    fontWeight: 'bold'
                },
                itemHoverStyle: {
                    color: '#000000'
                },
                itemHiddenStyle: {
                    color: '#CCC'
                }
            },
            tooltip: {
                backgroundColor: {
                    linearGradient: [0, 0, 0, 60],
                    stops: [
                        [0, '#FFFFFF'],
                        [1, '#E0E0E0']
                    ]
                },
                borderWidth: 1,
                borderColor: '#AAA',
                style: {
                    color: '#333333'
                },
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
                        this.series.name +': '+ this.y +'<br/>'+
                        '总数: '+ this.point.stackTotal;
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
                    }
                }
            },
            credits: {
                enabled: false
            },
            series: [{
                name: '问题个数',
                data: [${datahiddennum}]
            }, {
                name: '三违个数',
                data: [${datavionum}]
            }]
        });
    });				
</SCRIPT>
</head>
<body style="background-color:#FFFFFF;overflow: auto;">
<form id="thisForm" name="thisForm" action="riskAlertManageController.do?showHiddenVioUnitDisBarChart" method="post">
<div id="container" style="min-width:700px;height:100%"></div>
</form>
</body>
</html>
