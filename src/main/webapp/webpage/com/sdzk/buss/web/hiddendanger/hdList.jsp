<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
    <t:datagrid name="tBHiddenDangerHandleList" autoLoadData="true" checkbox="false" fitColumns="false" title="隐患列表" actionUrl="tBHiddenDangerHandleController.do?queryListDatagrid&riskManageTaskAllId=${riskManageTaskAllId}&riskManageTaskAllManageId=${riskManageTaskAllManageId}&riskManageTaskAllGRId=${riskManageTaskAllGRId}" idField="id" fit="true" queryMode="group">
        <t:dgCol title="发现时间"  field="hiddenDanger.examDate"  formatter="yyyy-MM-dd"  query="false" queryMode="group"  sortable="false" width="80" align="center"></t:dgCol>
        <t:dgCol title="检查班次"  field="hiddenDanger.shift" hidden="false" dictionary="workShift" query="false"  queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
       <t:dgCol title="预警颜色" field="alertColor" hidden="true"  width="80"></t:dgCol>
       <t:dgCol title="问题地点"  field="hiddenDanger.address.address"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
        <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
        <t:dgCol title="责任部门"  field="hiddenDanger.dutyUnit.departname" query="false" queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
        <t:dgCol title="责任人"  field="hiddenDanger.dutyMan" queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
        <t:dgCol title="检查人"  field="hiddenDanger.fillCardManNames" queryMode="single"  sortable="false" width="100" align="center"></t:dgCol>
        <t:dgCol title="信息来源"  field="hiddenDanger.manageType"  query="false" dictionary="manageType" queryMode="single"  sortable="true" width="120" align="center"></t:dgCol>
        <t:dgCol title="隐患等级"  field="hiddenDanger.hiddenNature"  query="false" dictionary="hiddenLevel"  queryMode="single"  sortable="false" width="70" align="center"></t:dgCol>

        <t:dgCol title="问题描述"  field="hiddenDanger.problemDesc"    queryMode="group"  sortable="false" width="300"></t:dgCol>
        <t:dgCol title="处理方式"  field="hiddenDanger.dealType" replace="限期整改_1,现场处理_2" width="80" align="center"></t:dgCol>
        <t:dgCol title="限期日期"  field="hiddenDanger.limitDate" formatter="yyyy-MM-dd" width="80" align="center"></t:dgCol>
        <t:dgCol title="限期班次"  field="hiddenDanger.limitShift" dictionary="workShift" hidden="true" width="80" align="center"></t:dgCol>
        <t:dgCol title="整改日期"  field="modifyDate" formatter="yyyy-MM-dd" query="false"  queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
        <t:dgCol title="整改人"  field="modifyMan"  dictionary="t_s_base_user,id,realname,where 1=1"   queryMode="group"  sortable="false" width="100" align="center"></t:dgCol>
        <t:dgCol title="复查日期"  field="reviewDate" formatter="yyyy-MM-dd"   queryMode="group"  sortable="false" width="80" align="center"></t:dgCol>
        <t:dgCol title="复查人"  field="reviewMan"  dictionary="t_s_base_user,id,realname,where 1=1"  queryMode="group"  sortable="false" width="100" align="center"></t:dgCol>
        <t:dgCol title="处理状态"  field="handlelStatus" dictionary="handelStatus" query="false"  queryMode="single"  sortable="false" width="60" align="center"></t:dgCol>

        <t:dgCol title="驳回备注"  field="rollBackRemark"  hidden="false" query="false" queryMode="group"  sortable="false" width="300"></t:dgCol>
        <t:dgCol title="录入人"  field="createName"   queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
        <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
        <t:dgCol title="录入日期"  field="createDate" formatter="yyyy-MM-dd"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
        <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
        <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
        <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
        <t:dgToolBar title="查看" icon="icon-search" url="tBHiddenDangerHandleController.do?goUpdate&load=detail" funname="detail" width="950" height="550"></t:dgToolBar>
    </t:datagrid>
  </div>
 </div>

<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });
 </script>