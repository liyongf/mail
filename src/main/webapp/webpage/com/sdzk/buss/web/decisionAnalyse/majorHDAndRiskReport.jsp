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
                月度: <input id="yearMonth" name="yearMonth" value="${yearMonth}" />
                <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="doQuery()" id="">
                    查询
                </a>
            </form>
        </td>
    </tr>
    <tr></tr>
    <tr>
        <td>
            <div id="container" style="width: 100%; height: 400px; margin: 0 auto"></div>
        </td>
    </tr>
</table>
 <script type="text/javascript">
 $(function () {
     $("#yearMonth").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
     $("input[name='yearMonth']").val("${yearMonth}");
 });
 var columnHighcharts;
 var reportStr;
 var unreportStr;
 function redrawCharts(){

     columnHighcharts = new Highcharts.Chart({
         chart: {
             renderTo: 'container',
             type: 'column'
         },
         title: {
             text: '重大隐患与风险月度统计'
         },
         xAxis: {
             categories: ['重大隐患','重大风险'],
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
                         this.series.name + ': ' + this.y + '个<br/>' +
                         '总量: ' + this.point.stackTotal +'个';
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
         name: '未上报',
         data: eval("["+unreportStr+"]"),
         events: {
             click: function(e) {
                 //弹出页面
                 if(e.point.x==0)
                     goMajorHiddenDangerDetail(0);
                 else if(e.point.x==1)
                     goMajorDangerSourceDetail(0);
             }
         }
     };

     var series1 = {
         name: '已上报',
         data: eval("[" + reportStr + "]"),
         events: {
             click: function(e) {
                 //弹出页面
                 if(e.point.x==0)
                     goMajorHiddenDangerDetail(1);
                 else if(e.point.x==1)
                     goMajorDangerSourceDetail(1);
             }
         }
     };

     while (columnHighcharts.series.length >0) {
         columnHighcharts.series[0].remove();
     }
     columnHighcharts.addSeries(series1);
     columnHighcharts.addSeries(series0);
     columnHighcharts.redraw();
 }
 function doQuery(){
     //$("#thisForm").submit();
     $.ajax({
         type: 'POST',
         url: 'tBDecisionAnalyseController.do?majorHDAndRiskReportQuery',
         data: {
            yearMonth: $("input[name='yearMonth']").val()
         },
         success: function (data) {
             var retData = $.parseJSON(data);
             var retObj = retData.obj;
             reportStr = retObj.report;
             unreportStr = retObj.unreport;
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
 function goMajorDangerSourceDetail(bReport){
     var yearMonth = $("input[name='yearMonth']").val();
     if(yearMonth==""){
         tip('月份不能为空！');
         return;
     }
     /*var url = "tBDecisionAnalyseController.do?dangerList&bMajor=1&statisticType=5&yearMonth="+yearMonth+"&bReport="+bReport;*/
     var url = "tBDecisionAnalyseController.do?riskList&bMajor=1&statisticType=5&yearMonth="+yearMonth+"&bReport="+bReport;
     createdetailwindow("查看",url,800,600);
 }
 doQuery();
 </script>