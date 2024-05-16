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
            <form id="thisForm" action="tBDecisionAnalyseController.do?riskHiddenDepartDistribution" method="post">
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
            <t:datagrid name="columnList" pagination="true"  fitColumns="true" title="单位隐患列表" pageSize="5" height="250"
                        onLoadSuccess = "redrawColumn" actionUrl="tBDecisionAnalyseController.do?riskHiddenListByYearMonthDatagrid&yearMonth=${yearMonth}"
                        idField="id" fit="true" queryMode="group">
                <t:dgCol title="主键"  field="id"  hidden="true" queryMode="single"  width="120"></t:dgCol>
                <t:dgCol title="单位名称" align="center" field="departname" queryMode="single"  width="120"></t:dgCol>
                <%--<t:dgCol title="风险数量"  field="risknum" align="center" queryMode="single"  width="120"></t:dgCol>--%>
                <t:dgCol title="隐患数量"  field="hiddennum" align="center" queryMode="group"  width="120"></t:dgCol>
              <%--  <t:dgCol title="重大隐患数量"  field="zdTotal" queryMode="group"  width="120"></t:dgCol>--%>
            </t:datagrid>
        </td>
    </tr>
</table>

<script type="text/javascript">
    function detailList(month, dangerType, mineName){
       // openwindow("","statisticalAnalysisController.do?hiddenDangerList&yearMonth=${yearMonth}&month="+month+"&dangerType="+dangerType+"&mineName="+mineName,"sfHiddenDangerBasicInfoList",1000,560);
    }
    function doQuery(){
        $("#thisForm").submit();
        redrawColumn();
    }
    var risknumArray = new Array();
    var unitIdArray = new Array();
function  redrawColumn(){
    var rows = $("#columnList").datagrid("getRows");
    var departnameArray = new Array();
    //var dzArray = new Array();
    var hiddenArray = new Array();
    risknumArray = [];
    for(var i =0 ;i<rows.length;i++){
        var departname = rows[i].departname;
        departnameArray.push(departname);
   //     var dz = rows[i].zdTotal;
    //    dzArray.push(parseInt(dz));
        var fx = rows[i].risknum;
        risknumArray.push(parseInt(fx))
        var hidden = rows[i].hiddennum;
        hiddenArray.push(parseInt(hidden));
        var uid = rows[i].id;
        unitIdArray.push(uid);
    }

/*   var series0 = {
        name: '重大隐患数量',
        data: dzArray,
       events: {
           click: function(e) {
               detailList('','major', e.point.category);
           }
       }
    };*/
   var series1 = {
        name: '隐患数量',
        data: hiddenArray,
       events: {
           click: function(e) {
                //弹出页面
                var riskIndex = e.point.x;
                var unitId = unitIdArray[riskIndex];
                goHiddenDangerDetail(unitId);
           }
       }
    };
    var series2 = {
        name: '风险数量',
        data:risknumArray,
        events: {
            click: function(e) {
                //弹出页面
                var dangerIndex = e.point.x;
                var unitId = unitIdArray[dangerIndex];
                goDangerDetail(unitId);
            }
        }
    };

    while (columnHighcharts.series.length >0) {
        columnHighcharts.series[0].remove();
    }
    columnHighcharts.xAxis[0].setCategories(departnameArray);
    //columnHighcharts.addSeries(series0);
    /*columnHighcharts.addSeries(series2);*/
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
                    text: '单位隐患分布'
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
     //   $("div[class='panel-header']").hide();
    });


    /**
     * Author：张赛超
     * */
    function goHiddenDangerDetail(unitId){
        var yearMonth = $("input[name='yearMonth']").val();
        if(yearMonth==""){
            tip('月份不能为空！');
            return;
        }
        var url = "tBDecisionAnalyseController.do?hiddenDangerList&statisticType=2&year=${year}&yearMonth="+yearMonth+"&unitId="+unitId;
        createdetailwindow("查看",url,800,600);
    }
    function goDangerDetail(unitId){
        var yearMonth = $("input[name='yearMonth']").val();
        if(yearMonth==""){
            tip('月份不能为空！');
            return;
        }
        var url = "tBDecisionAnalyseController.do?dangerList&statisticType=2&year=${year}&yearMonth="+yearMonth+"&unitId="+unitId;
        createdetailwindow("查看",url,800,600);
    }


</script>