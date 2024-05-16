<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2017/11/9
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script src="plug-in/jquery/jquery-3.1.1.js"></script>
<t:base type="easyui,tools,DatePicker"></t:base>
<html style="width: 98%;">
<head>
    <title>隐患部门分布</title>

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
<body style="height: 98%;">

    <div id="main" style="width: 98%;height:98%;"></div>
    <script type="text/javascript">
        var departList = "";
        var numList = "";
        var closeNumList = "";
        var notClosedNumList = "";

        $(function(){
            $.ajax({
                type: 'POST',
                url: 'homePageController.do?hiddenDangerByGroupData&flag=${flag}&startDate=${startDate}&endDate=${endDate}',
                data: { },
                success: function (data) {
                    var retData = $.parseJSON(data);

                    departList = retData.departList;
                    numList = retData.numList;
                    closeNumList = retData.closeNumList;
                    notClosedNumList = retData.notClosedNumList;

                    drawCharts(departList, numList, closeNumList, notClosedNumList);
                }
            });
        });


        function drawCharts(departList, numList, closeNumList, notClosedNumList){
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            option = {
                title : {
                    text: '隐患部门分布',
                    x: 'center'
                },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    bottom: 0,
                    data:['隐患总数','已闭环数量','未闭环数量']
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
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        data : eval(departList)
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'隐患总数',
                        type:'bar',
                        data: eval(numList)
                    },
                    {
                        name:'已闭环数量',
                        type:'bar',
                        data: eval(closeNumList)
                    },
                    {
                        name:'未闭环数量',
                        type:'bar',
                        data: eval(notClosedNumList)
                    },
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            myChart.on("click", focus);
            window.onresize = myChart.resize;
        }

        function focus(param) {
            var departName = param.name;
            var seriesName = param.seriesName;
            var isCloseLoop;
            if(seriesName == "已闭环数量"){
                isCloseLoop = 1;
            }else if(seriesName == "未闭环数量"){
                isCloseLoop = 0;
            }else{
                isCloseLoop = -1;
            }

            var url = "tBSafetyAnalyseController.do?goHiddenList&startDate=${startDate}&endDate=${endDate}&isCloseLoop="+isCloseLoop+"&departName="+encodeURI(encodeURI(departName));
            if(${flag eq '1'}){
                createdetailwindow("隐患列表", url, 800, 600);
            }else{
                /*parent.goDetailList("隐患列表", url);*/
                createdetailwindow("隐患列表", url, 800, 600);
            }
        }
    </script>

</body>
</html>
