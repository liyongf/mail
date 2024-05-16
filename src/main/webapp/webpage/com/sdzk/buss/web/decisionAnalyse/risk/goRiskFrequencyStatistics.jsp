<%--
  Created by IntelliJ IDEA.
  User: xuran
  Date: 2018/3/22
  Time: 9:25
  Name: 风险频次
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
    <title>风险频次</title>
</head>
<body>
<div id="main" style="width: 98%;height:98%;"></div>

<script type="text/javascript">
    var riskNameList = "";
    var numList = "";
    var riskIdList = "";

    $(function(){
        $.ajax({
            type: 'POST',
            url: 'riskAnalyseController.do?riskFrequencyStatistics&startDate=${startDate}&endDate=${endDate}',
            data: { },
            success: function (data) {
                var retData = $.parseJSON(data);

                riskNameList = retData.riskNameList;
                numList = retData.numList;
                riskIdList = retData.riskIdList;

                drawCharts(riskNameList, numList);
            }
        });
    });

    function drawCharts(riskNameList, numList){
        var myChart = echarts.init(document.getElementById('main'));
        option = {
            title : {
                text: '风险频次',
                x: 'center',
            },
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid:{   //绘图区调整
                x:120,  //左留白
                y:50,   //上留白
                x2:10,  //右留白
                y2:10   //下留白
            },
            calculable : true,
            xAxis : [
                {
                    show:false,
                    type : 'value',
                    boundaryGap : [0, 0],
                    position: 'top'
                }
            ],
            yAxis : [
                {
                    type : 'category',
                    data : riskNameList,
                    axisLine:{show:false},     //坐标轴
                    axisTick:[{    //坐标轴小标记
                        show:false
                    }],
                    axisLabel:{
                        textStyle:{
//                            fontSize:'30'
                        }
                    }
                }
            ],
            series : [
                {
                    name:'风险频次',
                    type:'bar',
//                    tooltip:{show:false},
                    barMinHeight:15,  //最小柱高
                    barWidth: 20,  //柱宽度
                    barMaxWidth:100,   //最大柱宽度
                    data:numList,
                    itemStyle:{
                        normal:{    //柱状图颜色
                            color:'#ff6600',
                            label:{
                                show: true,   //显示文本
                                position: 'insideRight',  //数据值位置
                                textStyle:{
                                    color:'#FFF',
//                                    fontSize:'30'
                                }
                            }
                        }
                    }
                }
            ]
        };
//          window.onresize = function () {  //适应页面
//              myChartContainer();
//              myChart.resize();
//          }
        myChart.setOption(option);

        function eConsole(param) {
            riskList(riskIdList[param.dataIndex]);
        }
        myChart.on('click', eConsole);
    }

    function riskList(hazardId){
        if (typeof(windowapi) == 'undefined') {
            $.dialog({content: "url:riskAnalyseController.do?riskList&hazardId="+hazardId, zIndex: getzIndex(), title: '风险列表', lock: true, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
                <%--{name: '<t:mutiLang langKey="common.confirm"/>', callback: function () {}, focus: true},--%>
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        } else {
            $.dialog({content: "url:riskAnalyseController.do?riskList&hazardId="+hazardId, zIndex: getzIndex(), title: '风险列表', lock: true, parent: windowapi, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
                <%--{name: '<t:mutiLang langKey="common.confirm"/>', callback: function () {}, focus: true},--%>
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        }
    }

</script>
</body>
</html>
