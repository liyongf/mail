<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/Highcharts-5.0.11/code/modules/exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/modules/offline-exporting.js"></script>
<script src="plug-in/highcharts4.2.1/highstock.js"></script>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
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
            <form id="thisForm" action="tBDecisionAnalyseController.do?loginHiddenStatisticsNew" method="post">

                <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="部门名称">部门名称：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="unitSelect" style="width: 130px;height: 15px"></div>
                 <input id="unitId" type="hidden" name="unitId" value="${unitId}">
             </span>
        </span>
                <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">人员：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="userSelect" style="width: 130px;height: 15px"></div>
                <input class="inuptxt" type="hidden" style="width: 100px" name="userId" id="userId" value="${userId}">
             </span>
        </span>
                </span>
                分析类型：&nbsp;&nbsp;
                <t:dictSelect field="loginHiddenStatisticsType" type="list" extendJson="{\"datatype\":\"*\"}"
                              typeGroupCode="loginHiddenStatisticsType"  hasLabel="false"  title="分析类型" defaultVal="${loginHiddenStatisticsType}"></t:dictSelect>&nbsp;&nbsp;
                <input id="startDate" name="startDate" value="${startDate}"> - <input id="endDate" name="endDate" value="${endDate}">
                <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="doQuery()" id="">
                    查询</a>
                <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-putout" onclick="ExportXls()" id="">
                    导出</a>
            </form>
        </td>
    </tr>
    <tr colspan="2" >
        <td>
            <div id="container2" style="width: 100%; height: 600px; margin: 0 auto">
                <t:datagrid name="statisticsList"  onDblClick="dbClick" pagination="true" fitColumns="true" title="${loginHiddenStatisticsTypeName}" actionUrl="tBDecisionAnalyseController.do?statisticsDatagrid&startDate=${startDate}&endDate=${endDate}&unitId=${unitId}&userId=${userId}" idField="id" fit="true" queryMode="group" >
                    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="240"></t:dgCol>
                    <t:dgCol title="部门名称"  field="departName" align="center" queryMode="single"   width="150" query="false" sortable="false"></t:dgCol>
                    <t:dgCol title="人员姓名"  field="realName" align="center" queryMode="single"   width="150" query="false" sortable="false"></t:dgCol>
                    <t:dgCol title="风险辨识数量"  field="count1"  queryMode="group"  width="120" align="center"  ></t:dgCol>
                    <t:dgCol title="风险审核数量"  field="count2"  queryMode="group"  width="120" align="center"  ></t:dgCol>
                    <t:dgCol title="隐患录入数量"  field="count3"  queryMode="group"  width="120" align="center"  ></t:dgCol>
                    <t:dgCol title="下井次数"  field="count10"  queryMode="group"  width="120" align="center"  ></t:dgCol>
                    <t:dgCol title="三违录入数量"  field="count11"  queryMode="group"  width="120" align="center"  ></t:dgCol>
                    <t:dgCol title="登录次数"  field="count4"  queryMode="group"  width="120" align="center"  ></t:dgCol>
                    <t:dgCol title="组织综合管控次数"  field="count5"  queryMode="group"  width="120" align="center"  ></t:dgCol>
                    <t:dgCol title="组织专业管控次数"  field="count6"  queryMode="group"  width="120" align="center"  ></t:dgCol>
                    <t:dgCol title="参与综合管控次数"  field="count7"  queryMode="group"  width="120" align="center"  ></t:dgCol>
                    <t:dgCol title="参与综合管控次数"  field="count8"  queryMode="group"  width="120" align="center"  ></t:dgCol>
                    <t:dgCol title="落实危害因素次数"  field="count9"  queryMode="group"  width="120" align="center"  ></t:dgCol>
                </t:datagrid>
            </div>
        </td>
    </tr>
    <tr colspan="2" style="height: 50px;display: none">
        <td>
        </td>
    </tr>
</table>

<script type="text/javascript">
    function doQuery(){
        $("#thisForm").submit();
    }

    $(function () {
        $("#startDate").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("#endDate").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        getDepartMagicSuggestWithValue($("#unitSelect"), $("#unitId"),"${unitId}", false);
        getUserMagicSuggestWithValue($('#userSelect'),  $("#userId"), "${userId}", false);
    });


    function ExportXls() {
        window.location.href = "tBDecisionAnalyseController.do?exportXlsDatagrid&startDate=${startDate}&endDate=${endDate}&unitId=${unitId}&userId=${userId}";
    }
</script>