<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/Highcharts-5.0.11/code/highcharts.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-3d.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/modules/exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/modules/offline-exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-zh_CN.js"></script>
<style media="print">
    <%--隐藏页眉页脚--%>
    @page {
        size: auto;  /* auto is the initial value */
        margin: 0mm; /* this affects the margin in the printer settings */
    }
    <%--隐藏无关的元素--%>
    #abc {display:none}
</style>
<table style="width: 100%;height: auto">
    <tr>
        <td style="text-align: center">
            <form id="thisForm" action="tBDecisionAnalyseController.do?majorHDAndRiskReport" method="post">
                开始时间: <input id="beginMonth" name="beginMonth" value="${yearMonth}" />
                <input id="endMonth" name="endMonth"  />
                <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="doQuery()" id="">
                    查询
                </a>
            </form>
        </td>
    </tr>
    <tr></tr>
    <tr>
        <td>
            <div id="containerone" style="float:left;width: 49%; height: 350px;padding-right: 15px;padding-top: 15px"></div>
            <div id="containertwo" style="float:left;width: 49%; height: 350px;padding-top: 15px"></div>
        </td>
    </tr>
    <tr>
        <td>
            <div id="containerthree" style="float:left;width: 49%; height: 350px;padding-right: 15px;padding-top: 15px"></div>
            <div id="containerfour"  style="float:left;width: 49%; height: 350px;padding-top: 15px"></div>
        </td>
    </tr>
</table>
<script type="text/javascript">
    $(function () {
        $("#beginMonth").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
        $("#endMonth").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
        $("input[name='beginMonth']").val("${yearMonth}");
    });
    var columnHighchartsone;
    var columnHighchartstwo;
    var columnHighchartsthree;
    var columnHighchartsfour;

    var planCountStr;
    var riskCountStr;
    var hiddenCountStr;

    var forAcceptStr;
    var forReplyStr;
    var yetReplyStr;


    function redrawCharts(){

        columnHighchartsone = new Highcharts.Chart({
            chart: {
                renderTo: 'containerone',
                type: 'column'
            },
            title: {
                text: '排查计划统计'
            },
            xAxis: {
                categories: ['月排查计划','旬排查计划','周排查计划'],
                crosshair: true
            },
            yAxis: {
                min: 0,
                title: {
                    text: '数量'
                }
            },
            legend: {
                align: 'right',
                x: -30,
                verticalAlign: 'top',
                y: 25,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.x + '</b><br/>' +
                            this.series.name + ': ' + this.y + '个<br/>';
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            }
        });

        columnHighchartstwo = new Highcharts.Chart({
            chart: {
                renderTo: 'containertwo',
                type: 'column'
            },
            title: {
                text: '排查计划完成情况统计'
            },
            xAxis: {
                categories: ['月排查计划','旬排查计划','周排查计划'],
                crosshair: true
            },
            yAxis: {
                min: 0,
                title: {
                    text: '数量'
                }
            },
            legend: {
                align: 'right',
                x: -30,
                verticalAlign: 'top',
                y: 25,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.x + '</b><br/>' +
                            this.series.name + ': ' + this.y + '个<br/>'
                            ;
                }
            },
            plotOptions: {
                column: {
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            }
        });

        columnHighchartsthree = new Highcharts.Chart({
            chart: {
                renderTo: 'containerthree',
                type: 'column'
            },
            title: {
                text: '排查风险数量统计'
            },
            xAxis: {
                categories: ['月排查计划','旬排查计划','周排查计划'],
                crosshair: true
            },
            yAxis: {
                min: 0,
                title: {
                    text: '数量'
                }
            },
            legend: {
                align: 'right',
                x: -30,
                verticalAlign: 'top',
                y: 25,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.x + '</b><br/>' +
                            this.series.name + ': ' + this.y + '个<br/>';
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            }
        });

        columnHighchartsfour = new Highcharts.Chart({
            chart: {
                renderTo: 'containerfour',
                type: 'column'
            },
            title: {
                text: '关联隐患数量统计'
            },
            xAxis: {
                categories: ['月排查计划','旬排查计划','周排查计划'],
                crosshair: true
            },
            yAxis: {
                min: 0,
                title: {
                    text: '数量'
                }
            },
            legend: {
                align: 'right',
                x: -30,
                verticalAlign: 'top',
                y: 25,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.background2) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false
            },
            tooltip: {
                formatter: function () {
                    return '<b>' + this.x + '</b><br/>' +
                            this.series.name + ': ' + this.y + '个<br/>' ;
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    pointPadding: 0.2,
                    borderWidth: 0
                }
            }
        });




        var series0 = {
            name: '数量',
            data: eval("[" + planCountStr + "]"),
            events: {
                click: function(e) {
                    //弹出页面
                    if(e.point.x==0)
                        goCheckPlanCountDetail("1");
                    else if(e.point.x==1)
                        goCheckPlanCountDetail("2");
                    else if(e.point.x==2)
                        goCheckPlanCountDetail("3");
                }
            }
        };
        var series21 = {
            name: '待受理',
            data: eval("[" + forAcceptStr + "]"),
            events: {
                click: function(e) {
                    //弹出页面
                    if(e.point.x==0)
                        goPlanForAcceptDetail(1);
                    else if(e.point.x==1)
                        goPlanForAcceptDetail(2);
                    else if(e.point.x==2)
                        goPlanForAcceptDetail(3);
                }
            }
        };
        var series22 = {
            name: '待回复',
            data: eval("[" + forReplyStr + "]"),
            events: {
                click: function(e) {
                    //弹出页面
                    if(e.point.x==0)
                        goPlanForReplyDetail(1);
                    else if(e.point.x==1)
                        goPlanForReplyDetail(2);
                    else if(e.point.x==2)
                        goPlanForReplyDetail(3);
                }
            }
        };
        var series23 = {
            name: '已回复',
            data: eval("[" + yetReplyStr + "]"),
            events: {
                click: function(e) {
                    //弹出页面
                    if(e.point.x==0)
                        goPlanYetReplyDetail(1);
                    else if(e.point.x==1)
                        goPlanYetReplyDetail(2);
                    else if(e.point.x==2)
                        goPlanYetReplyDetail(3);
                }
            }
        };
        var series3 = {
            name: '数量',
            data: eval("[" + riskCountStr + "]"),
            events: {
                click: function(e) {
                    //弹出页面
                    if(e.point.x==0)
                        goPlanYetRiskDetail(1);
                    else if(e.point.x==1)
                        goPlanYetRiskDetail(2);
                    else if(e.point.x==2)
                        goPlanYetRiskDetail(3);
                }
            }
        };
        var series4 = {
            name: '数量',
            data: eval("[" + hiddenCountStr + "]"),
            events: {
                click: function(e) {
                    //弹出页面
                    if(e.point.x==0)
                        goPlanYetHiddenDetail(1);
                    else if(e.point.x==1)
                        goPlanYetHiddenDetail(2);
                    else if(e.point.x==2)
                        goPlanYetHiddenDetail(3);
                }
            }
        };



        while (columnHighchartsone.series.length >0) {
            columnHighchartsone.series[0].remove();
        }
        while (columnHighchartstwo.series.length >0) {
            columnHighchartstwo.series[0].remove();
        }
        while (columnHighchartsthree.series.length >0) {
            columnHighchartsthree.series[0].remove();
        }
        while (columnHighchartsfour.series.length >0) {
            columnHighchartsfour.series[0].remove();
        }

        columnHighchartsone.addSeries(series0);
        columnHighchartstwo.addSeries(series21);
        columnHighchartstwo.addSeries(series22);
        columnHighchartstwo.addSeries(series23);
        columnHighchartsthree.addSeries(series3);
        columnHighchartsfour.addSeries(series4);

        columnHighchartsone.redraw();
        columnHighchartstwo.redraw();
        columnHighchartsthree.redraw();
        columnHighchartsfour.redraw();
    }


    function doQuery(){
        //$("#thisForm").submit();
        $.ajax({
            type: 'POST',
            url: 'tBHiddenDangerCheckPlanCountController.do?checkPlanCountQuery',
            data: {
                beginMonth: $("input[name='beginMonth']").val(),
                endMonth:$("input[name='endMonth']").val()
            },
            success: function (data) {
                var retData = $.parseJSON(data);
                var retObj = retData.obj;
                planCountStr = retObj.planCount;
                riskCountStr = retObj.riskCount;
                hiddenCountStr = retObj.hiddenCount;

                forAcceptStr = retObj.planForAcceptCount;
                forReplyStr = retObj.planForReplyCount;
                yetReplyStr = retObj.planYetReplyCount;

                redrawCharts();
            }, error: function () {
            }
        });
    }

    function goMajorHiddenDangerDetail(bReport){
        var yearMonth = $("input[name='yearMonth']").val();
        if(yearMonth==""){
            tip('月份不能为空！');
            return;
        }
        var url = "tBDecisionAnalyseController.do?majorHiddenDangerList&statisticType=5&yearMonth="+yearMonth+"&bReport="+bReport;
        createdetailwindow("查看",url,800,600);
    }


    function goCheckPlanCountDetail(type){
        var beginMonth = $("input[name='beginMonth']").val();
        var endMonth = $("input[name='endMonth']").val();
        var url = "tBHiddenDangerCheckPlanCountController.do?checkPlanCountList&beginMonth="+beginMonth+"&type="+type+"&endMonth"+endMonth;
        createdetailwindow("查看",url,800,600);
    }

    function goPlanForAcceptDetail(type){
        var beginMonth = $("input[name='beginMonth']").val();
        var endMonth = $("input[name='endMonth']").val();
        var url = "tBHiddenDangerCheckPlanCountController.do?planForAcceptList&beginMonth="+beginMonth+"&type="+type+"&endMonth"+endMonth;
        createdetailwindow("查看",url,800,600);
    }
    function goPlanForReplyDetail(type){
        var beginMonth = $("input[name='beginMonth']").val();
        var endMonth = $("input[name='endMonth']").val();
        var url = "tBHiddenDangerCheckPlanCountController.do?planForReplyList&beginMonth="+beginMonth+"&type="+type+"&endMonth"+endMonth;
        createdetailwindow("查看",url,800,600);
    }
    function goPlanYetReplyDetail(type){
        var beginMonth = $("input[name='beginMonth']").val();
        var endMonth = $("input[name='endMonth']").val();
        var url = "tBHiddenDangerCheckPlanCountController.do?planYetReplyList&beginMonth="+beginMonth+"&type="+type+"&endMonth"+endMonth;
        createdetailwindow("查看",url,800,600);
    }
    function goPlanYetHiddenDetail(type){
        var beginMonth = $("input[name='beginMonth']").val();
        var endMonth = $("input[name='endMonth']").val();
        var url = "tBHiddenDangerCheckPlanCountController.do?planYetHiddenList&beginMonth="+beginMonth+"&type="+type+"&endMonth"+endMonth;
        createdetailwindow("查看",url,800,600);
    }
    function goPlanYetRiskDetail(type){
        var beginMonth = $("input[name='beginMonth']").val();
        var endMonth = $("input[name='endMonth']").val();
        var url = "tBHiddenDangerCheckPlanCountController.do?planYetRiskList&beginMonth="+beginMonth+"&type="+type+"&endMonth"+endMonth;
        createdetailwindow("查看",url,800,600);
    }




    function goMajorDangerSourceDetail(bReport){
        var yearMonth = $("input[name='yearMonth']").val();
        if(yearMonth==""){
            tip('月份不能为空！');
            return;
        }
        var url = "tBDecisionAnalyseController.do?dangerList&bMajor=1&statisticType=5&yearMonth="+yearMonth+"&bReport="+bReport;
        createdetailwindow("查看",url,800,600);
    }
    doQuery();
</script>