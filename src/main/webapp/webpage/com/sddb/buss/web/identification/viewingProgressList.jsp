<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
    <t:datagrid name="viewingProgressList"   autoLoadData="true" checkbox="false" fitColumns="true" title="" actionUrl="riskTaskController.do?participantManDatagrid&riskTaskId=${riskTaskId}&taskType=${taskType}" idField="id" fit="true" queryMode="group">
        <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
        <t:dgCol title="参与人员"  field="participantManId.realName"  query="false"  queryMode="single"  width="120"></t:dgCol>
        <c:if test="${taskType ne '4'}">
            <t:dgCol title="风险数量（草稿）" align="center" field="risk0Num" url="riskIdentificationController.do?fxList&riskTaskParticipantRelId={id}&status=0" width="120"></t:dgCol>
            <t:dgCol title="风险数量（待审核）" align="center" field="risk1Num" url="riskIdentificationController.do?fxList&riskTaskParticipantRelId={id}&status=1" width="120"></t:dgCol>
            <t:dgCol title="风险数量（已退回）" align="center" field="risk2Num" url="riskIdentificationController.do?fxList&riskTaskParticipantRelId={id}&status=2" width="120"></t:dgCol>
            <t:dgCol title="风险数量（已审核）" align="center" field="risk3Num" url="riskIdentificationController.do?fxList&riskTaskParticipantRelId={id}&status=3" width="120"></t:dgCol>
        </c:if>
        <c:if test="${taskType eq '4'}">
            <t:dgCol title="风险数量" align="center" field="riskPostNum" url="riskIdentificationController.do?fxPostList&riskTaskParticipantRelId={id}" width="120"></t:dgCol>
        </c:if>
        <t:dgCol title="辨识状态" field="status" replace="辨识中_0,辨识完成_1" width="120"></t:dgCol>
    </t:datagrid>
  </div>
 </div>
<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });
 </script>