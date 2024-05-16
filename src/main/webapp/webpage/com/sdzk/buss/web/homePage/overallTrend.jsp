<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2017/11/9
  Time: 16:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<html style="width: 98%;">
<head>
    <title>双防总体趋势</title>

    <script src="plug-in/ace/js/bootstrap.js"></script>
    <script src="plug-in/echarts/code/echarts.js"></script>
    <link href="plug-in/ace/css/bootstrap.css" rel="stylesheet">
    <script language="javascript" src="plug-in/jquery/jquery.jqprint.js"></script>


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

    <div id="main" style="width: 98%;height:98%;"></div>
    <script type="text/javascript">

        $(function(){
            var addrNum;
            var yearRiskNum;
            var closeHiddNum;

            $.ajax({
                type: 'POST',
                url: 'homePageController.do?overallTrendData',
                data: {
                    startDate: "${startDate}",
                    endDate: "${endDate}"
                },
                success: function (data) {
                    var retData = $.parseJSON(data);

                    addrNum = retData.addrNum;
                    yearRiskNum = retData.yearRiskNum;
                    closeHiddNum = retData.closeHiddNum;

                    drawCharts(addrNum, yearRiskNum, closeHiddNum);
                }
            });
        });

        function getMax(realNum,isRisk) {
            var num = parseInt(realNum);
            var max = 100;
            if(isRisk){
                max = 5000;
            }
            if (num > max) {
                var i = parseInt(num / max);
                max = max * (i + 2);
            }
            return max;
        }

        function drawCharts(addrNum, yearRiskNum, closeHiddNum){
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            var maxAddrNum = getMax(addrNum,0);
            var maxYearRiskNum = getMax(yearRiskNum,1);
            var maxCloseHiddNum = getMax(closeHiddNum,0);

            var option = {
                title : {
                    text: '双防总体趋势',
                    x: 'center'
                },
                tooltip : {
                    formatter: "{a}:{c} "
                },
                toolbox: {
                    show : true,
                    feature : {
                        saveAsImage : {show: true,title : '导出图片'},
                        myprint:{   //自定义按钮
                            show:true,//是否显示
                            title:'打印', //鼠标移动上去显示的文字
                            icon:'image://plug-in/easyui/themes/icons/print.png', //图标
                            onclick:function(option1) {
                                window.print();//开始打印
                            }
                        }
                    }
                },
                series : [
                    {
                        name:'未闭环隐患总数',
                        type:'gauge',
                        z: 3,
                        min:0,
                        max:maxCloseHiddNum,
                        splitNumber:10,
                        axisLine: {            // 坐标轴线
                            lineStyle: {       // 属性lineStyle控制线条样式
                                width: 10
                            }
                        },
                        axisTick: {            // 坐标轴小标记
                            length :10,        // 属性length控制线长
                            lineStyle: {       // 属性lineStyle控制线条样式
                                color: 'auto'
                            }
                        },
                        splitLine: {           // 分隔线
                            length :20,         // 属性length控制线长
                            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                color: 'auto'
                            }
                        },
                        title : {
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: 'bolder',
                                fontSize: 20,
                                fontStyle: 'italic'
                            }
                        },
                        detail : {
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: 'bolder'
                            }
                        },
                        data:[{value: eval(closeHiddNum), name: '未闭环隐患总数'}]
                    },
                    {
                        name:'风险点数量',
                        type:'gauge',
                        center : ['25%', '55%'],    // 默认全局居中
                        radius : '50%',
                        min:0,
                        max:maxAddrNum,
                        startAngle:270,
                        endAngle:45,
                        splitNumber:10,
                        axisLine: {            // 坐标轴线
                            lineStyle: {       // 属性lineStyle控制线条样式
                                width: 8
                            }
                        },
                        axisTick: {            // 坐标轴小标记
                            length :12,        // 属性length控制线长
                            lineStyle: {       // 属性lineStyle控制线条样式
                                color: 'auto'
                            }
                        },
                        splitLine: {           // 分隔线
                            length :20,         // 属性length控制线长
                            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                color: 'auto'
                            }
                        },
                        pointer: {
                            width:5
                        },
                        title : {
                            offsetCenter: [0, '-30%'],       // x, y，单位px
                        },
                        detail : {
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: 'bolder'
                            }
                        },
                        data:[{value: eval(addrNum), name: '风险点数量'}]
                    },
                    {
                        name:'风险数',
                        type:'gauge',
                        center : ['75%', '55%'],    // 默认全局居中
                        radius : '50%',

                        min:0,
                        max:maxYearRiskNum,
                        startAngle: 135,
                        endAngle: -90,
                        splitNumber:10,
                        axisLine: {            // 坐标轴线
                            lineStyle: {       // 属性lineStyle控制线条样式
                                width: 8
                            }
                        },
                        axisTick: {            // 坐标轴小标记
                            length :12,        // 属性length控制线长
                            lineStyle: {       // 属性lineStyle控制线条样式
                                color: 'auto'
                            }
                        },
                        splitLine: {           // 分隔线
                            length :20,         // 属性length控制线长
                            lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
                                color: 'auto'
                            }
                        },
                        pointer: {
                            width:5
                        },
                        title : {
                            offsetCenter: [0, '-30%'],       // x, y，单位px
                        },
                        detail : {
                            textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
                                fontWeight: 'bolder'
                            }
                        },
                        data:[{value: eval(yearRiskNum), name: '风险数'}]
                    }
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            myChart.on("click", focus);
            window.onresize = myChart.resize;
        }
        function focus(param) {
            var seriesName = param.seriesName;
            if(seriesName=="风险点数量"){
                addOneTab('风险区域管理','tBAddressInfoController.do?managelist_supermap','default');
            }
            if(seriesName=="未闭环隐患总数"){
                addOneTab('综合查询','tBHiddenDangerHandleController.do?queryList&homePage=1','default');
            }
            if(seriesName=="风险数"){
                addOneTab('风险管控清单','riskIdentificationController.do?querylist&startDate=${startDate}&endDate=${endDate}','default');
            }
        }

    </script>

</body>
</html>
