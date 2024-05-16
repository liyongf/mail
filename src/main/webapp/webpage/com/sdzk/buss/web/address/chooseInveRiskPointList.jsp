<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="riskPointList" checkbox="true" fitColumns="true" title="风险点列表" actionUrl="tBAddressInfoController.do?chooseInveRiskPointDatagrid&ids=${ids}&month=${month}&from=${from}" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="风险点"  field="address"  query="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="关联风险数量"  field="dangerSourceCount"    queryMode="group"  width="80"></t:dgCol>
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