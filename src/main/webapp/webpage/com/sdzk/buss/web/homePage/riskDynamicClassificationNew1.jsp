<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2017/11/9
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<html style="width: 98%;">
<head>
    <title>风险动态分级预警</title>

    <script src="plug-in/hplus/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="plug-in/Highcharts-5.0.11/code/highcharts.js"></script>
    <link href="plug-in-ui/hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <script type="text/javascript" src="plug-in/hashMap/HashMap.js"></script>
    <style media="print">
        <%--隐藏页眉页脚--%>
        @page {
            size: auto;  /* auto is the initial value */
            margin: 0mm; /* this affects the margin in the printer settings */
        }
        <%--隐藏无关的元素--%>
        #abc {display:none}
    </style>
</head>
<body>
<div style="font-family: 'sans-serif'; font-size: 18px; font-weight: bold; text-align: center; margin-bottom: 8px; margin-top: 15px;">风险动态分级预警</div>
<div id="main" style="width: 98%;height:80%;"></div>

<script type="text/javascript">
    $(function () {
        doQuery(0);
    });
    function doQuery() {
        $.ajax({
            type: 'POST',
            url: 'homePageController.do?riskDynamicClassficationDataNew1',
            data: {
            },
            success: function (data) {
                var retData = $.parseJSON(data);
                var aNum = retData.aNum;
                var bNum = retData.bNum;
                var cNum = retData.cNum;
                var dNum = retData.dNum;

                redrawCharts(aNum,bNum,cNum,dNum);
            }
        });
    }

    function redrawCharts(aNum,bNum,cNum,dNum) {
        $('#main').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: '', style: {
                    font: '20px', color: '#333333'   //这边是改标题的字体大小和颜色
                }
            },
            tooltip: {
                headerFormat: '{series.name}<br>',
                pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
            },
            colors:[
                '#F2080F',
                '#F09409',
                '#F0E803',
                '#0051ff'
            ],
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false,
                    },
                    showInLegend: true
                }
            },

            series: [{
                type: 'pie',
                name: '风险点等级分布',
                data: [
                    {
                        name: '重大风险点',
                        y: eval(aNum),

                        events: {
                            click: function (e) {
                                myclick("1");
                            }
                        }
                    }, {
                        name: '较大风险点',
                        y: eval(bNum),
                        events: {
                            click: function (e) {
                                myclick("2");
                            }
                        }
                    },
                    {
                        name: '一般风险点',
                        y: eval(cNum),

                        events: {
                            click: function (e) {
                                myclick("3");
                            }
                        }
                    },
                    {
                        name: '低风险点',
                        y: eval(dNum),

                        events: {
                            click: function (e) {
                                myclick("4");
                            }
                        }
                    }
                ]
            }]
        });
    }
    function myclick(level) {
        var url = "tBDangerSourceController.do?addressLevelList&level="+level;
        createdetailwindow('风险点列表',url,700,400);
    }
</script>
</body>
</html>
