<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBAddressInfoList" checkbox="true" pagination="true" fitColumns="false" title="选择风险" actionUrl="riskController.do?getDangerSourceDatagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="危险源"  field="hazard.hazardName"    queryMode="single"  width="120" query="true"></t:dgCol>
      <t:dgCol title="风险类型"  field="yeHazardCate" dictionary="hazardCate"   queryMode="single"  width="120" query="true"></t:dgCol>
      <t:dgCol title="风险描述"  field="yePossiblyHazard"    queryMode="single"  width="120" query="true"></t:dgCol>
      <t:dgCol title="风险等级"  field="yeRiskGrade"  dictionary="riskLevel"  queryMode="single"  width="120" query="true"></t:dgCol>
   <t:dgToolBar  title="添加" icon="icon-add" url="tBHiddenDangerExamController.do?goLinkFine" funname="addRelFunction"  width="950" height="400" operationCode="goLinkAddress"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
     function addRelFunction (){
         var ids = [];
         var rows = $('#tBAddressInfoList').datagrid('getSelections');
         if(rows.length<=0 ){
             tip("请选择要关联的风险点");
         }else{
             for (var i = 0; i < rows.length; i++) {
                 ids.push(rows[i].id);
             }
             ids.join(',');

             $.ajax({
                 url: "riskController.do?saveRelChooseDangerSource&ids="+ids,
                 type: 'POST',
                 error: function(){
                 },
                 success: function(data){
                     isSelectedAll = false;
                     data = $.parseJSON(data);
                     tip(data.msg);
                     $('#tBAddressInfoList').datagrid('reload');
                     window.top.reload_tBDangerSourceList.call();
                 }
             });
         }
     }
 $(document).ready(function(){
 });
 
 </script>