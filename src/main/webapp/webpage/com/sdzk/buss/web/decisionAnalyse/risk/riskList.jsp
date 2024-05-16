<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>

<t:datagrid name="tBDangerSourceList" fitColumns="false" title="风险列表" actionUrl="riskAnalyseController.do?riskDatagrid&riskLevel=${riskLevel}&seType=${seType}&departId=${departId}&hazardId=${hazardId}" idField="id" fit="true" queryMode="group">
    <t:dgCol title="唯一编号" field="id" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
    <t:dgCol title="辨识时间"  field="yeRecognizeYear" query="false" formatter="yyyy"  hidden="true"   queryMode="single" width="60"></t:dgCol>
    <t:dgCol title="辨识时间" field="yeRecognizeTime" query="false" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
    <t:dgCol title="隐患描述" field="yeMhazardDesc" queryMode="single" query="false" width="180"></t:dgCol>
    <t:dgCol title="专业" field="yeProfession" query="false" dictionary="proCate_gradeControl" queryMode="single"
             width="60" align="center"></t:dgCol>
    <t:dgCol title="危险源名称" field="hazard.hazardName" queryMode="single" width="80" align="center"></t:dgCol>
    <t:dgCol title="伤害类别" field="damageType" query="false" dictionary="danger_Category" queryMode="single"
             width="80" align="center"></t:dgCol>
    <t:dgCol title="风险描述" field="yePossiblyHazard" queryMode="single" query="false" width="180"></t:dgCol>
    <t:dgCol title="事故类型" field="yeAccidentTemp" queryMode="group" width="80" align="center"></t:dgCol>
    <t:dgCol title="作业活动" field="activity.id" dictionary="t_b_activity_manage where is_delete='0',id,activity_name" query="false" queryMode="single" width="100" align="center"></t:dgCol>
    <c:if test="${initParam.les == 'no'}">
        <t:dgCol title="可能性" field="yeProbability" dictionary="probability" queryMode="group" width="50" align="center"></t:dgCol>
        <t:dgCol title="损失" field="yeCost" dictionary="hazard_fxss" queryMode="group" width="50" align="center"></t:dgCol>
        <t:dgCol title="风险值" field="riskValue" queryMode="group" width="50" align="center"></t:dgCol>
    </c:if>
    <c:if test="${initParam.les == 'yes'}">
        <t:dgCol title="可能性" field="lecRiskPossibility" dictionary="lec_risk_probability" queryMode="group"
                 width="50" align="center"></t:dgCol>
        <t:dgCol title="损失" field="lecRiskLoss" dictionary="lec_risk_loss" queryMode="group" width="50" align="center"></t:dgCol>
        <t:dgCol title="暴露频率" field="lecExposure" dictionary="lec_exposure" queryMode="group"
                 width="50" align="center"></t:dgCol>
        <t:dgCol title="风险值" field="lecRiskValue" queryMode="group" width="50" align="center"></t:dgCol>
    </c:if>
    <t:dgCol title="风险等级" field="yeRiskGrade" dictionary="riskLevel" query="false" queryMode="single" width="70"
             align="center"></t:dgCol>
    <t:dgCol title="风险类型" field="yeHazardCate" query="false" dictionary="hazardCate" queryMode="single" width="60"
             align="center"></t:dgCol>
    <t:dgCol title="管控标准来源" field="docSource" queryMode="single" query="false" width="100" align="center"></t:dgCol>
    <t:dgCol title="章节条款" field="sectionName" queryMode="single" query="false" width="100" align="center"></t:dgCol>
    <t:dgCol title="标准内容" field="yeStandard" queryMode="single" query="false" width="180" align="center"></t:dgCol>
    <t:dgCol title="管控措施" field="manageMeasure" queryMode="group" width="180" align="center"></t:dgCol>
    <t:dgCol title="责任岗位" field="post.postName" queryMode="single" width="100" align="center"></t:dgCol>
    <t:dgCol title="隐患等级" field="hiddenLevel" dictionary="hiddenLevel" queryMode="single" query="false" width="100" align="center"></t:dgCol>
    <t:dgCol title="罚款金额(元)" field="fineMoney" queryMode="group" width="80" align="center"></t:dgCol>
    <t:dgCol title="风险状态" field="auditStatus" replace="待上报_1,审核中_2,审核退回_3,闭环_4" queryMode="single" width="60"
             align="center"></t:dgCol>
    <t:dgToolBar title="查看" icon="icon-search" url="tBDangerSourceController.do?goDetail" funname="detail" width="850" height="450"></t:dgToolBar>

</t:datagrid>

<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">