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
    <title>风险隐患数量</title>

    <script src="plug-in/ace/js/bootstrap.js"></script>
    <script src="plug-in/echarts/code/echarts.js"></script>
    <link href="plug-in/ace/css/bootstrap.css" rel="stylesheet">
</head>
<body style="height: 98%;">

<div id="main" style="width: 98%;height:98%;"></div>
<script type="text/javascript">
    var riskDescStr = "";
    var countStr = "";
    var riskIdStr = "";

    $(function(){
        $.ajax({
            type: 'POST',
            url: 'tBDecisionAnalyseController.do?hdNumStatistics',
            data: {
                flag:"${flag}",
                startDate:"${startDate}",
                endDate:"${endDate}"
            },
            success: function (data) {
                var retData = $.parseJSON(data);
                riskDescStr = retData.riskDescStr;
                countStr = retData.countStr;
                riskIdStr = retData.riskIdStr;
                drawCharts(riskDescStr,countStr,riskIdStr);
            }
        });
    });


    function drawCharts(riskDesc,countStr,riskIdStr){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        option = {
            title: {
                text: '风险管控',
            },
            tooltip: {
                trigger: 'item',
                formatter: function (params) {
                    var msgTemp = params.name.substring(0, params.name.lastIndexOf("-"));
                    var msg = "";
                    for (var i = 0; i < msgTemp.length; i=i+30) {
                        j=i+30;
                        msg+=msgTemp.substring(i, j)+"<br/>";
                    }
                    msg+="隐患数量:" + params.value;
                    return msg;
                },

            },
            legend: {
                data: ['隐患数量']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: {
                type: 'value',
                boundaryGap: [0, 0.01]
            },
            yAxis: {
                inverse: true,
                type: 'category',
                data: eval(riskDesc),
                axisLabel: {
                    color: "#000",
                    interval: 0,
                    formatter:
                        function (value) {
                            var msg = value.substring(0, value.lastIndexOf("-"));
                            if (msg.length > 10) {
                                return msg.substring(0, 10) + "...";
                            } else {
                                return msg;
                            }
                        }
                },
            },
            series: [
                {
                    name: '隐患数量',
                    type: 'bar',
                    data: eval(countStr),
                    itemStyle: {
                        normal: {
                            color: "#7cb5ec"
                            },
                        }
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        myChart.on("click", focus);
        window.onresize = myChart.resize;
    }
    function focus(param) {
        var riskId = param.name.substring(param.name.lastIndexOf("-")+1,param.name.length);
        var url = "tBDecisionAnalyseController.do?riskManageStatisticsMenu&type=hdNum&riskId="+riskId;
        if(${flag eq '1'}){
            createdetailwindow("风险列表", url, 800, 600);
        }else{
            parent.goDetailList("风险列表", url);
        }
    }
</script>

</body>
</html>

