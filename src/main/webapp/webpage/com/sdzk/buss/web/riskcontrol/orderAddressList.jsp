<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBAddressInfoList" checkbox="true" pagination="true" fitColumns="false" title="选择风险点" actionUrl="riskController.do?chooseDatagrid&taskId=${taskId}" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="地点名称"  field="address.address"  queryMode="single"  width="120" query="true"></t:dgCol>
    <t:dgCol title="周期内管控次数"  field="controlTimes"    queryMode="single"  width="120" query="false"></t:dgCol>
    <t:dgCol title="周期周期" hidden="true" field="controlTime" formatter="yyyy-MM-dd"  queryMode="group"  width="120" query="true"></t:dgCol>
    <t:dgCol title="地点主键"  field="address.id"  hidden="true"  queryMode="single"  width="120" query="false"></t:dgCol>
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
                 ids.push(rows[i]["address.id"]);
             }
             ids.join(',');

             $.ajax({
                 url: "riskController.do?saveTaskOrder&taskId=${taskId}&addressIds="+ids,
                 type: 'POST',
                 error: function(){
                 },
                 success: function(data){
                     isSelectedAll = false;
                     data = $.parseJSON(data);
                     tip(data.msg);
                     $('#tBAddressInfoList').datagrid('reload');
                     window.top.reload_operationList.call();
                 }
             });
         }
     }
 $(document).ready(function(){

     $("input[name='controlTime_begin']").val("${start}");
     $("input[name='controlTime_end']").val("${end}");
 });
 
 </script>