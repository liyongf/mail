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

<table style="width: 100%;height: 100%">
    <tr>
        <td colspan="2" style="text-align: center">
            <form id="thisForm" action="tBDecisionAnalyseController.do?hazardFactorStatistics" method="post">
                分析类型：&nbsp;&nbsp;
                <t:dictSelect field="hazardFactorStatisticsType" type="list" extendJson="{\"datatype\":\"*\"}"
                              typeGroupCode="hazardFactorStatisticsType"  hasLabel="false"  title="分析类型" defaultVal="${hazardFactorStatisticsType}"></t:dictSelect>&nbsp;&nbsp;
                月份:&nbsp;&nbsp;<input id="yearMonth" name="yearMonth" value="${yearMonth}">&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="doQuery()" id="">
                    查询</a>
            </form>
        </td>
    </tr>
    <tr colspan="2" >
        <td>
            <div id="container2" style="width: 100%; height: 400px; margin: 0 auto"></div>
        </td>
    </tr>
    <tr colspan="2" style="height: 250px;">
        <td>
            <t:datagrid name="columnList" pagination="true"  fitColumns="true" title="风险管控措施检查分析" pageSize="5" height="250"
                        onLoadSuccess = "redrawColumn" actionUrl="tBDecisionAnalyseController.do?hazardFactorStatisticsDatagrid&yearMonth=${yearMonth}&hazardFactorStatisticsType=${hazardFactorStatisticsType}"
                        idField="id" fit="true" queryMode="group">
                <t:dgCol title="主键"  field="id"  hidden="true" queryMode="single"  width="120"></t:dgCol>
                <t:dgCol title="${hazardFactorStatisticsTypeName}" align="center" field="name" queryMode="single"  width="120"></t:dgCol>
                <t:dgCol title="管控次数"  field="countNum" align="center" queryMode="group"  width="120"></t:dgCol>
            </t:datagrid>
        </td>
    </tr>
</table>

<script type="text/javascript">
    function doQuery(){
        $("#thisForm").submit();
        redrawColumn();
    }
function  redrawColumn(){
    var rows = $("#columnList").datagrid("getRows");
    var nameArray = new Array();
    var countArray = new Array();
    var idArray = new Array();
    for(var i =0 ;i<rows.length;i++){
        var name = rows[i].name;
        nameArray.push(name);
        var count = rows[i].countNum;
        countArray.push(parseInt(count));
        var id = rows[i].id;
        idArray.push(id);
    }

   var series1 = {
        name: '管控次数',
        data: countArray,
       events: {
           click: function(e) {
                //弹出页面
                var riskIndex = e.point.x;
                var id = idArray[riskIndex];
                //goRiskDetail(id);
           }
       }
    };


    while (columnHighcharts.series.length >0) {
        columnHighcharts.series[0].remove();
    }
    columnHighcharts.xAxis[0].setCategories(nameArray);

    columnHighcharts.addSeries(series1);

    columnHighcharts.redraw();

}
    var columnHighcharts;
    $(function () {
        $("#yearMonth").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
        columnHighcharts = new Highcharts.Chart({
                chart: {
                    renderTo: 'container2',
                    type: 'column'
                },
                title: {
                    text: '风险管控措施检查分析'
                },
                xAxis: {
                    categories: [],
                    crosshair: true
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: '数量'
                    }
                },
                tooltip: {
                    headerFormat: '{point.key}<br>',
                    pointFormat: '{series.name}:' + '{point.y}',
                    footerFormat: '',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    }
                },
                series: {

                }
            });
        $("#lineListtb").hide();
        $("#columnListtb").hide();
    });



    function goRiskDetail(id){
        var yearMonth = $("input[name='yearMonth']").val();
        var url = "riskIdentificationController.do?fxList&riskStatisticsTypeId="+id+"&yearMonth="+yearMonth+"&riskStatisticsType=${riskStatisticsType}";
        createdetailwindow("查看",url,800,600);
    }



</script>