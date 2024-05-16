<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<script src="plug-in/Highcharts-5.0.11/code/modules/exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/modules/offline-exporting.js"></script>
<script src="plug-in/highcharts4.2.1/highstock.js"></script>
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
            <form id="thisForm" action="tBDecisionAnalyseController.do?riskStatistics" method="post">
                分析类型：&nbsp;&nbsp;

                <t:dictSelect field="riskStatisticsType" type="list" extendJson="{\"datatype\":\"*\"}"
                              typeGroupCode="riskStatisticsType"  hasLabel="false"  title="分析类型" defaultVal="${riskStatisticsType}"></t:dictSelect>&nbsp;&nbsp;
                月份:&nbsp;&nbsp;<input id="yearMonth" name="yearMonth" value="${yearMonth}">&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="doQuery()" id="">
                    查询</a>
            </form>
        </td>
    </tr>
    <tr colspan="2" >
        <td>
            <div id="container2" style="width: 100%; height: 600px; margin: 0 auto"></div>
        </td>
    </tr>
    <tr colspan="2" style="height: 50px;display: none">
        <td>
            <t:datagrid name="columnList" pagination="true"  fitColumns="true" title="新增风险列表" pageSize="5" height="250"
                        onLoadSuccess = "redrawColumn" actionUrl="tBDecisionAnalyseController.do?riskStatisticsDatagrid&yearMonth=${yearMonth}&riskStatisticsType=${riskStatisticsType}"
                        idField="id" fit="true" queryMode="group">
                <t:dgCol title="主键"  field="id"  hidden="true" queryMode="single"  width="120"></t:dgCol>
                <t:dgCol title="${riskStatisticsTypeName}名称" align="center" field="name" queryMode="single"  width="120"></t:dgCol>
                <t:dgCol title="新增风险数量"  field="riskNum" align="center" queryMode="group"  width="120"></t:dgCol>
            </t:datagrid>
        </td>
    </tr>
</table>

<script type="text/javascript">
    function doQuery(){
        $("#thisForm").submit();
        redrawColumn();
    }
    var risknumArray = new Array();
    var idArray = new Array();
function  redrawColumn(){
    var rows = $("#columnList").datagrid("getRows");
    var nameArray = new Array();
    var riskArray = new Array();
    risknumArray = [];
    for(var i =0 ;i<rows.length;i++){
        var name = rows[i].name;
        nameArray.push(name);
        var risk = rows[i].riskNum;
        riskArray.push(parseInt(risk));
        var id = rows[i].id;
        idArray.push(id);
    }

   var series1 = {
        name: '新增风险数量',
        data: riskArray,
       events: {
           click: function(e) {
                //弹出页面
                var riskIndex = e.point.x;
                var id = idArray[riskIndex];
                goRiskDetail(id);
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
                    text: '风险辨识效果分析'
                },
                xAxis: {
                    categories: [],
                    crosshair: true,
                    min:0, //别忘了这里
                    max:10
                },
            scrollbar:{
                enabled:true //是否产生滚动条
            },
                yAxis: {
                    min: 0,
                    title: {
                        text: '数量'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
                            '<td style="padding:0"><b>{point.y} </b></td></tr>',
                    footerFormat: '</table>',
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