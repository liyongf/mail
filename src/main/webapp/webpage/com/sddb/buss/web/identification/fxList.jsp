<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hazardFactorsList" checkbox="false" pagination="true" fitColumns="false" title="${title}" actionUrl="riskIdentificationController.do?queryListByAddressDatagrid&addressId=${addressId}&expDate=${expDate}&riskTaskParticipantRelId=${riskTaskParticipantRelId}&status=${status}&riskStatisticsType=${riskStatisticsType}&yearMonth=${yearMonth}&riskStatisticsTypeId=${riskStatisticsTypeId}&riskManageTaskAllManageId=${riskManageTaskAllManageId}&riskManageTaskAllId=${riskManageTaskAllId}&riskManageTaskAllGRId=${riskManageTaskAllGRId}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="风险点Id"  field="address.id" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="风险点"  field="address.address"  queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="风险描述"  field="riskDesc" formatterjs="valueTitle"  queryMode="group"  width="200"></t:dgCol>
   <t:dgCol title="风险等级"  field="riskLevel"  dictionary="factors_level" queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="危害因素和管控措施" align="center"  field="hazardFactortsNum" url="riskIdentificationController.do?wxysList&id={id}&load=detail"  queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="最高管控层级"  field="manageLevel"  dictionary="identifi_mange_level"  queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="最高管控责任人"  field="dutyManager"  queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="评估日期"  field="identifiDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="解除日期"  field="expDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
      <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
      <t:dgCol title="状态"  field="status" dictionary="identifi_status"  queryMode="group"  width="120" ></t:dgCol>

  </t:datagrid>
  </div>
 </div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">


 $(document).ready(function(){
     $("span[title='最高管控责任人']").css("width","100px");
 });
 

 </script>