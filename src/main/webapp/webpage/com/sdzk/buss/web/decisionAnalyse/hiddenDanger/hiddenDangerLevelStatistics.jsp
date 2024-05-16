<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/21 0021
  Time: 下午 3:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script src="plug-in/jquery/jquery-3.1.1.js"></script>
<t:base type="easyui,tools,DatePicker"></t:base>
<html style="width: 98%;">
<head>
    <title>隐患等级分布</title>
    <script type="text/javascript" src="plug-in/hashMap/HashMap.js"></script>
    <script src="plug-in/ace/js/bootstrap.js"></script>
    <script src="plug-in/echarts/code/echarts.js"></script>
    <link href="plug-in/ace/css/bootstrap.css" rel="stylesheet">
</head>
<body style="height: 98%;">

<div id="main" style="width: 98%;height:98%;"></div>

<script type="text/javascript">
    var hiddenLevelMap = new HashMap();
    var hiddenLevels = "[";

    <c:forEach items="${hiddenLevelList}" var="hiddenLevel">
    hiddenLevelMap.put('${hiddenLevel.typecode}','${hiddenLevel.typename}');
    if(hiddenLevels.length > 1){
        hiddenLevels += ",";
    }
    hiddenLevels += "'${hiddenLevel.typename}'";
    </c:forEach>
    hiddenLevels += "]";
    console.log(hiddenLevels);
    $(function(){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        option = {
            title : {
                text: '隐患等级分布',
                x:'10',
                y:'10'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                x : 'center',
                y : 'bottom',
                data:eval(hiddenLevels)
            },
            toolbox: {
                show : false,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true,
                        type: ['pie', 'funnel']
                    },
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            series : [
                {
                    name:'隐患等级',
                    type:'pie',
                    radius : ['25%','75%'],
                    roseType : 'radius',
                    data:[
                        <c:forEach items="${resultList}" var="data" varStatus="index">
                        {
                            name:'${data.hiddenLevel}',
                            value:${data.count}
                        }
                        <c:if test="${!index.last}">, </c:if>
                        </c:forEach>
                    ],
                    itemStyle: {
                        normal: {
                            shadowBlur: 800,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    },
                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    animationDelay: function (idx) {
                        return Math.random() * 200;
                    }
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        myChart.on("click", focus);

        window.onresize = myChart.resize;
    });

    function focus(param) {
        var hiddenLevelName = param.name;
        var url = "tBSafetyAnalyseController.do?goHiddenList&startDate=${startDate}&endDate=${endDate}&hiddenLevelName="+encodeURI(encodeURI(hiddenLevelName));
        if(${flag eq '1'}){
            createdetailwindow("隐患列表", url, 800, 600);
        }else{
            parent.goDetailList("隐患列表", url);
        }
    }
</script>
</body>
</html>
