<%--
  Created by IntelliJ IDEA.
  User: xuran
  Date: 2018/3/22
  Time: 9:25
  Name: 违章变化趋势
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools"></t:base>

<script src="plug-in/ace/js/bootstrap.js"></script>
<script src="plug-in/echarts/code/echarts.js"></script>
<link href="plug-in/ace/css/bootstrap.css" rel="stylesheet">
<html>
<head>
    <title>违章变化趋势</title>
</head>
<body>
<div id="main" style="width: 98%;height:98%;"></div>

<script type="text/javascript">
    var vioDateList = "";
    var numList = "";

    $(function(){
        $.ajax({
            type: 'POST',
            url: 'threeVioAnalyseController.do?threeVioTrendStatistics&startDate=${startDate}&endDate=${endDate}',
            data: { },
            success: function (data) {
                var retData = $.parseJSON(data);

                vioDateList = retData.vioDateList;
                numList = retData.numList;
                drawCharts(vioDateList, numList);
            }
        });
    });

    function drawCharts(vioDateList, numList){
        var myChart = echarts.init(document.getElementById('main'));
        option = {
            title : {
                text: '违章变化趋势',
                x: 'center',
            },
            tooltip : {
                trigger: 'axis'
            },
//            legend: {
//                data:['最高气温','最低气温']
//            },
//            toolbox: {
//                show : true,
//                feature : {
//                    mark : {show: true},
//                    dataView : {show: true, readOnly: false},
//                    magicType : {show: true, type: ['line', 'bar']},
//                    restore : {show: true},
//                    saveAsImage : {show: true}
//                }
//            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : vioDateList,
//                    axisLabel:{
//                        interval: 0,
//                        rotate:40
//                    }
                }
            ],
            yAxis : [
                {
                    type : 'value',
//                    axisLabel : {
//                        formatter: '{value} °C'
//                    }
                }
            ],
            series : [
                {
                    name:'违章数',
                    type:'line',
                    data:numList
//                    markPoint : {
//                        data : [
//                            {type : 'max', name: '最大值'},
//                            {type : 'min', name: '最小值'}
//                        ]
//                    },
//                    markLine : {
//                        data : [
//                            {type : 'average', name: '平均值'}
//                        ]
//                    }
                }
            ]
        };

        myChart.setOption(option);

        function eConsole(param) {
            threeVioList(param.name);
        }
        myChart.on('click', eConsole);
    }

    function threeVioList(vioDate){
        if (typeof(windowapi) == 'undefined') {
            $.dialog({content: "url:threeVioAnalyseController.do?threeVioList&vioDate="+vioDate, zIndex: getzIndex(), title: '违章列表', lock: true, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
                <%--{name: '<t:mutiLang langKey="common.confirm"/>', callback: function () {}, focus: true},--%>
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        } else {
            $.dialog({content: "url:threeVioAnalyseController.do?threeVioList&vioDate="+vioDate, zIndex: getzIndex(), title: '违章列表', lock: true, parent: windowapi, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
                <%--{name: '<t:mutiLang langKey="common.confirm"/>', callback: function () {}, focus: true},--%>
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        }
    }

</script>
</body>
</html>
