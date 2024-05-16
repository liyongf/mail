<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
    <t:datagrid name="viewingProgressTaskList"   autoLoadData="true" checkbox="false" fitColumns="true" title="" actionUrl="riskManageTaskAllManageController.do?checkManDatagrid&riskManageTaskAllManageId=${riskManageTaskAllManageId}" idField="id" fit="true" queryMode="group">
        <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
        <t:dgCol title="任务Id"  field="taskAllId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
        <t:dgCol title="检查地点"  field="checkAddress"  dictionary="t_b_address_info,id,address,where 1=1" query="false"   queryMode="single"  width="190"></t:dgCol>
        <t:dgCol title="检查人" field="checkMan" dictionary="t_s_base_user,id,realname,where 1=1" width="80"></t:dgCol>
        <t:dgCol title="新增风险"  field="riskCount" align="center"  queryMode="group"  width="50"  url="riskIdentificationController.do?fxList&riskManageTaskAllId={taskAllId}"></t:dgCol>
        <t:dgCol title="新增隐患"  field="hdCount" align="center"  queryMode="group"  width="50"   url="riskManageTaskAllManageController.do?hdList&riskManageTaskAllId={taskAllId}" ></t:dgCol>
        <t:dgCol title="危害因素落实数量"  field="implCount" align="center"  queryMode="group"  width="100"  url="riskIdentificationController.do?wxysImplList&riskManageTaskAllId={taskAllId}" ></t:dgCol>
        <t:dgCol title="任务状态" field="status" replace="未完成_0,已完成_1,任务已删除_-1" width="120"></t:dgCol>
        <t:dgCol title="任务Id" field="taskAllId" hidden="true" width="120"></t:dgCol>
        <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
        <t:dgFunOpt funname="showRiskManageTask(id)" title="管控任务清单列表"  urlclass="ace_button" exp="status#ne#-1"></t:dgFunOpt>
    </t:datagrid>
  </div>
 </div>
<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });

 function showRiskManageTask(id){
     var url = "riskManageTaskController.do?list&checkRelId="+id;
     addOneTab("管控任务清单",url,"default");
     window.top.close_scoreDetailUser.call();
     window.top.close_socreGkTaskList.call();
     $.messager.progress('close');
     frameElement.api.close();

 }

 </script>