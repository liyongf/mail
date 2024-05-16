<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="riskRuleList" checkbox="true" pagination="true" fitColumns="false" title="规则配置列表" actionUrl="riskRuleController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="风险等级"  field="riskType" dictionary="riskLevel"   queryMode="single"  width="120" query="true"></t:dgCol>
    <t:dgCol title="分值"  field="scoreTemp"  hidden="false"  queryMode="group"  width="120"></t:dgCol>
   <t:dgToolBar title="录入" icon="icon-add" url="riskRuleController.do?goAdd" funname="add" operationCode="add"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="riskRuleController.do?goDetail" funname="detail" operationCode="detail"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="riskRuleController.do?goUpdate" funname="update" operationCode="update" ></t:dgToolBar>
   <t:dgToolBar title="删除"  icon="icon-remove" url="riskRuleController.do?doBatchDel" funname="deleteALLSelect" operationCode="batchdelete" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 });
 </script>