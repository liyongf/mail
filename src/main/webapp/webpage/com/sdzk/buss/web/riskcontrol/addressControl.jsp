<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBAddressInfoList" checkbox="true" pagination="true" fitColumns="false" title="我管控的地点" actionUrl="riskController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="地点名称"  field="address.address"    queryMode="single"  width="120" query="true"></t:dgCol>
   <t:dgToolBar  title="添加" icon="icon-add" url="tBHiddenDangerExamController.do?goLinkFine" funname="goLinkAddress"  width="950" height="400" operationCode="goLinkAddress"></t:dgToolBar>
   <t:dgToolBar title="删除"  icon="icon-remove" url="riskController.do?doBatchDel" funname="deleteALLSelect" operationCode="batchdelete" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
     window.top["reload_tBDangerSourceList"]=function(){
         $("#tBAddressInfoList").datagrid( "load");
     };


     function goLinkAddress(){
         openwindow('选择风险点','riskController.do?goRelAddressList',"",1000,500);
     }
 $(document).ready(function(){
 });
 
 </script>