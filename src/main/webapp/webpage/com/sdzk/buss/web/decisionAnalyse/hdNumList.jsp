<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="hdNumList" checkbox="true" width="900px" height="450px" fitColumns="false" title="隐患" actionUrl="tBDecisionAnalyseController.do?hdNumListDatagrid&riskId=${riskId}&addressId=${addressId}&riskType=${riskType}&departId=${departId}&year=${year}&month=${month}&type=${type}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="发现时间"  field="examDate"  formatter="yyyy-MM-dd"   queryMode="group"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="检查班次"  field="shift" hidden="false" dictionary="workShift"   queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="问题地点"  field="address.address"   queryMode="single"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="责任部门"  field="dutyUnit.departname"  queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
            <t:dgCol title="责任人"  field="dutyMan" queryMode="group" sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="检查人"  field="fillCardManNames" queryMode="single"  sortable="false" width="100" align="center"></t:dgCol>
            <t:dgCol title="信息来源"  field="manageType"   dictionary="manageType" queryMode="single"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="隐患等级"  field="hiddenNature"   dictionary="hiddenLevel"  queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="问题描述"  field="problemDesc"    queryMode="group"  sortable="false" width="300"></t:dgCol>
            <t:dgCol title="处理方式"  field="dealType" replace="限期整改_1,现场处理_2" width="80" align="center"></t:dgCol>
            <t:dgCol title="限期日期"  field="limitDate" formatter="yyyy-MM-dd" width="80" align="center"></t:dgCol>
            <t:dgCol title="限期班次"  field="limitShift" dictionary="workShift" hidden="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="整改日期"  field="handleEntity.modifyDate" formatter="yyyy-MM-dd"   queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="整改人"  field="handleEntity.modifyMan"  dictionary="t_s_base_user,id,realname,where 1=1"   queryMode="group"  sortable="true" width="100" align="center"></t:dgCol>
            <t:dgCol title="复查日期"  field="handleEntity.reviewDate" formatter="yyyy-MM-dd"   queryMode="group"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="复查人"  field="handleEntity.reviewMan"  dictionary="t_s_base_user,id,realname,where 1=1"  queryMode="group"  sortable="true" width="100" align="center"></t:dgCol>
            <t:dgCol title="处理状态"  field="handleEntity.handlelStatus" dictionary="handelStatus"   queryMode="single"  sortable="true" width="60" align="center"></t:dgCol>

            <t:dgCol title="驳回备注"  field="handleEntity.rollBackRemark"  hidden="true"  queryMode="group"  sortable="false" width="300"></t:dgCol>
            <t:dgCol title="录入人"  field="handleEntity.createName"   queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>

        </t:datagrid>
    </div>
</div>