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
    <title>隐患类型分布</title>

    <script src="plug-in/ace/js/bootstrap.js"></script>
    <script src="plug-in/echarts/code/echarts.js"></script>
    <link href="plug-in/ace/css/bootstrap.css" rel="stylesheet">
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
        var indicatorList = "";
        var valueList = "";

        $(function(){
            $.ajax({
                type: 'POST',
                url: 'homePageController.do?hiddenDangerByProfessionData&startDate=${startDate}&endDate=${endDate}',
                data: { },
                success: function (data) {
                    var retData = $.parseJSON(data);
                    indicatorList = retData.indicatorList;
                    valueList = retData.valueList;

                    drawCharts(indicatorList, valueList);
                }
            });
        })

        function drawCharts(indicatorList, valueList){
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            option = {
                title : {
                    text: '隐患类型分布',
                    subtext: '隐患类型分布占比',
                    x: 'left'
                },
                tooltip : {
                    trigger: 'axis'
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
                radar: {
                    name: {
                        textStyle: {
                            color: '#333333',
                        }
                    },
                    indicator : eval(indicatorList),
                },

                calculable : true,
                series : [
                    {
                        name: '类型分布占比',
                        type: 'radar',
                        data : [
                            {
                                value : eval(valueList),
                                name : '隐患数量'
                             }
                         ]
                    }
            ]
        };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            window.onresize = myChart.resize;
        }

    </script>

</body>
</html>
