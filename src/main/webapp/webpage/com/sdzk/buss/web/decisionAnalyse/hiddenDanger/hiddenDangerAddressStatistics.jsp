<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/21 0021
  Time: 下午 3:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script src="plug-in/jquery/jquery-3.1.1.js"></script>
<t:base type="easyui,tools,DatePicker"></t:base>
<html style="width: 98%;">
<head>
    <title>隐患地点分布</title>

    <script src="plug-in/ace/js/bootstrap.js"></script>
    <script src="plug-in/echarts/code/echarts.js"></script>
    <link href="plug-in/ace/css/bootstrap.css" rel="stylesheet">
</head>
<body style="height: 98%;">

<div id="main" style="width: 98%;height:98%;"></div>
<script type="text/javascript">
    var addressNameStr = "";
    var countStr = "";

    $(function(){
        $.ajax({
            type: 'POST',
            url: 'tBSafetyAnalyseController.do?hiddenDangerAddressStatistics',
            data: {
                flag:"${flag}",
                startDate:"${startDate}",
                endDate:"${endDate}"
            },
            success: function (data) {
                var retData = $.parseJSON(data);

                addressNameStr = retData.addressNameStr;
                countStr = retData.countStr;

                drawCharts(addressNameStr,countStr);
            }
        });
    });


    function drawCharts(addressNameStr,countStr){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        option = {
            title : {
                text: '隐患地点分布',
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
                data:['隐患数量']
            },
            toolbox: {
                show : false,
                orient: 'vertical',
                x: 'right',
                y: 'center',
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : eval(addressNameStr)
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'隐患数量',
                    type:'bar',
                    data: eval(countStr),
                    markPoint : {
                        data : [
                            {type : 'max', name: '最大值'}
                        ]
                    }
                }
            ],
            color : ['#424b54']
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        myChart.on("click", focus);
        window.onresize = myChart.resize;
    }

    function focus(param) {
        var addressName = param.name;
        var url = "tBSafetyAnalyseController.do?goHiddenList&startDate=${startDate}&endDate=${endDate}&addressName="+encodeURI(encodeURI(addressName));
        if(${flag eq '1'}){
            createdetailwindow("隐患列表", url, 800, 600);
        }else{
            parent.goDetailList("隐患列表", url);
        }
    }
</script>

</body>
</html>

