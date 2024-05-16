<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
    <t:datagrid name="addressList"   autoLoadData="true" checkbox="false" fitColumns="true" title="" actionUrl="tBDangerSourceController.do?addressDatagrid&level=${level}" idField="id" fit="true" queryMode="group">
        <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
        <t:dgCol title="风险点"  field="address"  query="false"  queryMode="single"  width="120"></t:dgCol>
        <t:dgCol title="未闭环隐患数量" field="hiddenCount" url="tBDangerSourceController.do?dangerSourceList&addressId={id}" width="120"></t:dgCol>
    </t:datagrid>
  </div>
 </div>

<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });
 </script>