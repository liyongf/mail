<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBSpecialReportList" title="专项辨识报告" actionUrl="tBSpecialReportController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="专项工作基本情况" field="baseCondition"   width="120"></t:dgCol>
   <t:dgCol title="单位负责人" field="chargeMan"   width="120"></t:dgCol>
   <t:dgCol title="辨识组织" field="identifyGroup"   width="120"></t:dgCol>
   <t:dgCol title="辨识评估方法" field="identifyMethod"   width="120"></t:dgCol>
   <t:dgCol title="辨识结论" field="identifyResult"   width="120"></t:dgCol>
   <t:dgCol title="任务id" field="taskId"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBSpecialReportController.do?del&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tBSpecialReportController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBSpecialReportController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBSpecialReportController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>