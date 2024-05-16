<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,ztree"></t:base>
<t:datagrid name="riskPointList" checkbox="true" pagination="true" fitColumns="true" title="作业活动列表" actionUrl="tbActivityManageController.do?chooseInveRiskPointDatagrid&ids=${ids}&month=${month}&from=${from}" idField="id" fit="true" queryMode="group"  sortName="activityName" sortOrder="desc">
    <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
    <t:dgCol title="作业活动名称" field="activityName" width="120" query="true" queryMode="single"></t:dgCol>
    <t:dgCol title="专业" field="professionType" query="true" dictionary="proCate_gradeControl"  queryMode="single" width="120"></t:dgCol>
    <t:dgCol title="关联风险数量" field="count" width="120"></t:dgCol>
</t:datagrid>
<script>
    function getriskPointListSelectionsNewLine(field) {
        var ids = [];
        var rows = $('#riskPointList').datagrid('getSelections');
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i][field]);
        }
        return ids.join('\r\n');
    };
</script>
