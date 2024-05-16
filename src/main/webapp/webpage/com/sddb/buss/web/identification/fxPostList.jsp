<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hazardFactorsList" checkbox="false" pagination="true" fitColumns="true" title="${title}" actionUrl="riskIdentificationController.do?fxPostDatagrid&riskStatisticsTypeId=${riskStatisticsTypeId}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="单位"  field="postUnit.departName"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="岗位"  field="post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
      <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="风险等级"  field="riskLevel" dictionary="factors_level"  width="120" ></t:dgCol>
      <t:dgCol title="危害因素" align="center"  field="hazardFactortsPostNum" url="riskIdentificationController.do?wxysPostList&id={id}"  queryMode="group"  width="120" ></t:dgCol>

  </t:datagrid>
  </div>
 </div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">


 $(document).ready(function(){
     $("span[title='最高管控责任人']").css("width","100px");
 });
 

 </script>