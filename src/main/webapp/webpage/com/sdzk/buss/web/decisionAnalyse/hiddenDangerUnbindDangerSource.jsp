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
        <td colspan="2" style="text-align: center">
            <form id="thisForm" action="tBDecisionAnalyseController.do?hiddenDangerUnbindDangerSource" method="post">
                月度: <input id="yearMonth" name="yearMonth" value="${yearMonth}" />
                <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="doQuery()" id="">
                    查询
                </a>
            </form>
        </td>
    </tr>

    <tr>
        <td style="width: 50%">
            <div id="container" style="width: 100%; height: 400px; margin: 0 auto"></div>
        </td>
        <td>
            <t:datagrid name="columnList" fitColumns="true" title="${yearMonth}月度未关联风险隐患列表" pageSize="5" pageList="5"
                        onLoadSuccess = "redrawCharts" actionUrl="tBDecisionAnalyseController.do?hiddenDangerUnbindDangerSourceQuery&yearMonth=${yearMonth}"
                        idField="id" fit="true" queryMode="group">
                <t:dgCol title="单位id"  field="unitId" queryMode="single" hidden="true" align="center" width="120"></t:dgCol>
                <t:dgCol title="单位名称"  field="unitName" queryMode="single"  align="center" width="120"></t:dgCol>
                <t:dgCol title="隐患数量"  field="hiddenTotal" queryMode="group"  align="center" width="120"></t:dgCol>
            </t:datagrid>
        </td>
    </tr>

</table>
 <script type="text/javascript">
 $(function () {
     $("#yearMonth").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
     $("input[name='yearMonth']").val("${yearMonth}");

 });
 var unitIdArray = new Array();
 function redrawCharts(){
     var rows = $("#columnList").datagrid("getRows");
     var unitNameArray = new Array();
     var hiddenArray = new Array();
     unitIdArray = [];
     for(var i =0 ;i<rows.length;i++){
         var unitId = rows[i].unitId;
         unitIdArray.push(unitId);
         var unitName = rows[i].unitName;
         unitNameArray.push(unitName);
         var hidden = rows[i].hiddenTotal;
         hiddenArray.push(parseInt(hidden));
     }

     columnHighcharts = new Highcharts.Chart({
         chart: {
             renderTo: 'container',
             type: 'column'
         },
         title: {
             text: '未关联风险隐患分布'
         },
         xAxis: {
             categories: unitNameArray,
             crosshair: true
         },
         yAxis: {
             min: 0,
             title: {
                 text: '数量'
             }
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

     var series0 = {
         name: '未关联风险隐患数量',
         data: hiddenArray,
         events: {
             click: function(e) {
                 //弹出页面
                 var unitIndex = e.point.x;
                 var unitId = unitIdArray[unitIndex];
                 goHiddenDangerDetail(unitId);
             }
         }
     };

     while (columnHighcharts.series.length >0) {
         columnHighcharts.series[0].remove();
     }
     columnHighcharts.addSeries(series0);
     columnHighcharts.redraw();
 }
 function doQuery(){
     var yearMonth = $("input[name='yearMonth']").val();
     if(yearMonth==""){
         tip('请填写查询月份！');
         return;
     }
     $("#thisForm").submit();

 }

 function goHiddenDangerDetail(unitId){
     var yearMonth = $("input[name='yearMonth']").val();
     if(yearMonth==""){
         tip('月份不能为空！');
         return;
     }
     var url = "tBDecisionAnalyseController.do?hiddenDangerList&statisticType=4&yearMonth="+yearMonth+"&unitId="+unitId;
     createdetailwindow("查看",url,800,600);

 }

 </script>