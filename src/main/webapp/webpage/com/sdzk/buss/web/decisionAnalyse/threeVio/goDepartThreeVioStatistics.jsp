<%--
  Created by IntelliJ IDEA.
  User: xuran
  Date: 2018/3/22
  Time: 9:25
  Name: 部门三违分布
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
    <title>部门三违分布</title>
</head>
<body>
<div id="main" style="width: 98%;height:98%;"></div>

<script type="text/javascript">
    var departList = "";
    var departIdList = "";
    var numList = "";

    $(function(){
        $.ajax({
            type: 'POST',
            url: 'threeVioAnalyseController.do?departThreeVioStatistics&startDate=${startDate}&endDate=${endDate}',
            data: { },
            success: function (data) {
                var retData = $.parseJSON(data);
                departList = retData.departList;
                departIdList = retData.departIdList;
                numList = retData.numList;

                drawCharts(departList, numList);
            }
        });
    });


    function drawCharts(departList, numList){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        option = {
            title : {
                text: '部门违章分布',
                x: 'center'
            },
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    data : departList
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'三违总数',
                    type:'bar',
                    data: numList
                },
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);

        function eConsole(param) {
            threeVioList(departIdList[param.dataIndex]);
        }
        myChart.on('click', eConsole);
    }

    function threeVioList(departId){
        if (typeof(windowapi) == 'undefined') {
            $.dialog({content: "url:threeVioAnalyseController.do?threeVioList&departId="+departId, zIndex: getzIndex(), title: '违章列表', lock: true, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
                <%--{name: '<t:mutiLang langKey="common.confirm"/>', callback: function () {}, focus: true},--%>
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        } else {
            $.dialog({content: "url:threeVioAnalyseController.do?threeVioList&departId="+departId, zIndex: getzIndex(), title: '违章列表', lock: true, parent: windowapi, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
                <%--{name: '<t:mutiLang langKey="common.confirm"/>', callback: function () {}, focus: true},--%>
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        }
    }

</script>
</body>
</html>
