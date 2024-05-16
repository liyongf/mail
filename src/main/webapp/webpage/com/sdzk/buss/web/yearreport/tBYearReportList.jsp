<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBYearReportList" title="年度辨识报告" actionUrl="tBYearReportController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="单位负责人" field="chargeMan"   width="120"></t:dgCol>
   <t:dgCol title="矿井名称" field="mineName"   width="120"></t:dgCol>
   <t:dgCol title="矿井概况" field="mineStatus"   width="120"></t:dgCol>
   <t:dgCol title="主要参考依据" field="mainRefer"   width="120"></t:dgCol>
   <t:dgCol title="矿井主要灾害" field="mainDanger"   width="120"></t:dgCol>
   <t:dgCol title="矿井主要生产系统" field="mainSystem"   width="120"></t:dgCol>
   <t:dgCol title="辨识范围" field="identifyRange"   width="120"></t:dgCol>
   <t:dgCol title="辨识评估方法" field="identifyMethod"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBYearReportController.do?del&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tBYearReportController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBYearReportController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBYearReportController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>