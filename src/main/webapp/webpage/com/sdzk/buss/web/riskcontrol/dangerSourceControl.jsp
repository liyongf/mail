<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBAddressInfoList" checkbox="true" pagination="true" fitColumns="false" title="我的管控清单" actionUrl="riskController.do?dangerSourceControlDatagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="危险源"  field="dangerSourceEntity.hazard.hazardName"    queryMode="single"  width="120" query="true"></t:dgCol>
    <t:dgCol title="风险类型"  field="dangerSourceEntity.yeHazardCate" dictionary="hazardCate"   queryMode="single"  width="120" query="true"></t:dgCol>
    <t:dgCol title="风险描述"  field="dangerSourceEntity.yePossiblyHazard"    queryMode="single"  width="120" query="true"></t:dgCol>
    <t:dgCol title="风险等级"  field="dangerSourceEntity.yeRiskGrade"  dictionary="riskLevel"  queryMode="single"  width="120" query="true"></t:dgCol>
   <t:dgToolBar  title="添加" icon="icon-add" url="tBHiddenDangerExamController.do?goLinkFine" funname="goLinkAddress"  width="950" height="400" operationCode="goLinkAddress"></t:dgToolBar>
   <t:dgToolBar title="删除"  icon="icon-remove" url="riskController.do?doBatchDel" funname="deleteALLSelect" operationCode="batchdelete" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
     window.top["reload_tBDangerSourceList"]=function(){
         $("#tBAddressInfoList").datagrid( "load");
     };

     function deleteALLSelect (){
         var ids = [];
         var rows = $('#tBAddressInfoList').datagrid('getSelections');
         if(rows.length<=0 ){
             tip("请选择要删除的风险");
         }else{
             for (var i = 0; i < rows.length; i++) {
                 ids.push(rows[i].id);
             }
             ids.join(',');

             $.ajax({
                 url: "riskController.do?deleteDanger&ids="+ids,
                 type: 'GET',
                 error: function(){
                 },
                 success: function(data){
                     data = $.parseJSON(data);
                     tip(data.msg);
                     $('#tBAddressInfoList').datagrid('reload');
                     window.top.reload_tBDangerSourceList.call();
                 }
             });
         }
     }

     function goLinkAddress(){
         openwindow('选择风险','riskController.do?goRelDangerSourceList',"",1000,500);
     }
 $(document).ready(function(){
 });
 
 </script>