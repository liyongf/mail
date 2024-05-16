<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBAccidentLevelList" checkbox="true" width="900px" height="450px" fitColumns="true" title="危害因素" actionUrl="riskIdentificationController.do?whysImplListDatagrid&riskManageTaskAllId=${riskManageTaskAllId}&riskManageTaskAllManageId=${riskManageTaskAllManageId}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="风险类型"  field="hazardFactor.riskType" dictionary="risk_type" queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="专业"  field="hazardFactor.major" dictionary="major" queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="危害因素"  field="hazardFactor.hazardFactors"  formatterjs="valueTitle"  queryMode="group"  width="200"></t:dgCol>
      <t:dgCol title="危害因素等级"  field="hfLevel" dictionary="factors_level"  queryMode="group" sortable="false"  width="100" ></t:dgCol>
      <t:dgCol title="管控措施"  field="hfManageMeasure" formatterjs="valueTitle"  queryMode="group" sortable="false"  width="200" ></t:dgCol>
      <t:dgCol title="落实情况"  field="implDetail"  queryMode="group" formatterjs="valueTitle"  width="110" ></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 </script>