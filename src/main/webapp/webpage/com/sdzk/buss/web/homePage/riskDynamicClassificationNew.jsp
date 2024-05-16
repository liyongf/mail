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

    <script src="plug-in/ace/js/bootstrap.js"></script>
    <script src="plug-in/echarts/code/echarts-all.js"></script>
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

    $(function(){
        var nodeLists;
        var linkLists;

        $.ajax({
            type: 'POST',
            url: 'homePageController.do?riskDynamicClassficationDataNew',
            data: { },
            success: function (data) {
                var retData = $.parseJSON(data);

                nodeLists = retData.nodeLists;
                linkLists = retData.linkLists;
                drawCharts(nodeLists, linkLists);
            }
        });
    })

    function drawCharts(nodeLists, linkLists){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        option = {
            title : {
                text: '风险动态分级预警',
                x:'right',
                y:'bottom'
            },
            tooltip : {
                trigger: 'item',
                formatter: '{a} : {b}'
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
            color:[
                '#cccccc',
                '#F2080F',
                '#F09409',
                '#F0E803',
                '#0051ff'
            ],
            legend: {
                x: 'left',
                data:['风险点数量','重大风险','较大风险','一般风险','低风险']
            },
            series : [
                {
                    name : "风险点数量",
                    type: 'force',
                    ribbonType: false,
                    categories : [
                        {
                            name: '风险点数量'
                        },
                        {
                            name: '重大风险'
                        },
                        {
                            name: '较大风险'
                        },
                        {
                            name: '一般风险'
                        },
                        {
                            name: '低风险'
                        }
                    ],
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                textStyle: {
                                    color: '#333'
                                }
                            },
                            nodeStyle : {
                                brushType : 'both',
                                borderColor : 'rgba(255,215,0,0.4)',
                                borderWidth : 1
                            },
                            linkStyle: {
                                type: 'curve'
                            }
                        },
                        emphasis: {
                            label: {
                                show: false
                                // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
                            },
                            nodeStyle : {
                                //r: 30
                            },
                            linkStyle : {}
                        }
                    },
                    useWorker: false,
                    minRadius : 15,
                    maxRadius : 25,
                    gravity: 1.1,
                    scaling: 1.1,
                    roam: 'move',
                    nodes:
                        nodeLists,
                    links :
                        linkLists
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        window.onresize = myChart.resize;
        myChart.on(echarts.config.EVENT.CLICK, myclick);

    }
    function myclick(param) {
        if(param.dataIndex>0){
            //var url = 'riskAlertManageController.do?addressDangerSourceList&addressId=' + param.data.id;
            /*var url = "tBDangerSourceController.do?dangerSourceList&addressId="+param.data.id;
            createdetailwindow(param.name+' 关联隐患列表',url,800,400);*/
            var url = "tBDangerSourceController.do?addressLevelList&level="+param.data.level;
            createdetailwindow('风险点列表',url,700,400);
        }

    }

</script>

</body>
</html>
