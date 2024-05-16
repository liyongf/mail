<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tBAccidentLevelList" checkbox="true" width="900px" height="450px" fitColumns="true" title="管控人" actionUrl="tBDecisionAnalyseController.do?manageNumDatagrid&riskId=${riskId}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="管控人"  field="myUserId" dictionary="t_s_base_user,id,realname,where 1=1"   queryMode="single"  width="80" align="center"></t:dgCol>
            <t:dgCol title="管控类型"  field="manageType" dictionary="manageType"  queryMode="single"  sortable="false" width="90" align="center"></t:dgCol>
        </t:datagrid>
    </div>
</div>