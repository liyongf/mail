<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!-- 通用危险源 -->

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBDangerSourceList" checkbox="true" pagination="true" fitColumns="false" title="部门年度风险列表" actionUrl="tBDangerSourceController.do?departDangerSourceDatagrid" idField="id" fit="true" queryMode="group" sortName="danger.isMajor,danger.yeRecognizeTime" sortOrder="desc,desc">
      <t:dgCol title="唯一编号" field="id" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
      <t:dgCol title="辨识时间" field="yeRecognizeTime" query="true" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="隐患描述" field="yeMhazardDesc" queryMode="single" query="true" width="180"></t:dgCol>
      <t:dgCol title="专业" field="yeProfession" query="true" dictionary="proCate_gradeControl" queryMode="single"
               width="60" align="center"></t:dgCol>
      <t:dgCol title="危险源名称" field="hazard.hazardName" queryMode="single" width="80" align="center"></t:dgCol>
      <t:dgCol title="作业活动" field="activity.activityName" queryMode="single" width="100" align="center"></t:dgCol>
      <t:dgCol title="伤害类别" field="damageType" query="true" dictionary="danger_Category" queryMode="single"
               width="80" align="center"></t:dgCol>
      <t:dgCol title="风险描述" field="yePossiblyHazard" queryMode="group" width="180"></t:dgCol>
      <t:dgCol title="事故类型" field="yeAccidentTemp" queryMode="group" width="80" align="center"></t:dgCol>
      <%--<t:dgCol title="是否重大风险" field="isMajor" replace="是_1,否_0" queryMode="group" width="80"
               align="center"></t:dgCol>--%>
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
      <t:dgCol title="风险等级" field="yeRiskGrade" dictionary="riskLevel" query="true" queryMode="single" width="70"
               align="center"></t:dgCol>
      <t:dgCol title="风险类型" field="yeHazardCate" query="true" dictionary="hazardCate" queryMode="single" width="60"
               align="center"></t:dgCol>
      <t:dgCol title="管控标准来源" field="docSource" queryMode="single" query="false" width="100" align="center"></t:dgCol>
      <t:dgCol title="章节条款" field="sectionName" queryMode="single" query="false" width="100" align="center"></t:dgCol>
      <t:dgCol title="标准内容" field="yeStandard" queryMode="group" width="180" align="center"></t:dgCol>
      <t:dgCol title="管控措施" field="manageMeasure" queryMode="group" width="180" align="center"></t:dgCol>
      <t:dgCol title="责任岗位" field="post.postName" queryMode="single" width="100" align="center"></t:dgCol>
      <t:dgCol title="隐患等级" field="hiddenLevel" dictionary="hiddenLevel" queryMode="single" query="true" width="100" align="center"></t:dgCol>
      <t:dgCol title="罚款金额(元)" field="fineMoney" queryMode="group" width="80" align="center"></t:dgCol>
      <t:dgCol title="风险状态" field="auditStatus" replace="待上报_1,审核中_2,审核退回_3,闭环_4" queryMode="single" width="60"
               align="center"></t:dgCol>
      <%--<t:dgCol title="危险源来源" field="origin" replace="通用_1,年度_2,专项_3" queryMode="group" width="80"
               align="center"></t:dgCol>--%>

    <t:dgToolBar title="关联危险源" icon="icon-add" funname="chooseDangerSource" operationCode="relDangerSource"></t:dgToolBar>
    <t:dgToolBar title="查看" icon="icon-search" url="tBDangerSourceController.do?goDetail&from=universalDangerList" funname="goDetail" width="850" height="450" operationCode="detail"></t:dgToolBar>
    <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/dangersource/js/tBDangerSourceList.js"></script>
 <script type="text/javascript">

 $(document).ready(function(){
 });
function goDetail(){
    var rowsData = $('#tBDangerSourceList').datagrid('getSelections');
    if(rowsData == null || rowsData.length != 1){
        tip("请选择一条需要查看的条目");
    }else{
        var url = 'tBDangerSourceController.do?goDetail&from=universalDangerList&load=detail&id='+rowsData[0]["danger.id"];
        createdetailwindow("查看",url,850,450);
    }
}
 function chooseDangerSource(){
     $.dialog({id:'dialog',title:'危险源选择',zIndex:999,modal:true,content: 'url:tBDangerSourceController.do?chooseDangerSource2Rel',lock:true,width: 800,height: 500});
 }
 function showMsg(msg){
     tip(msg);
 }
 function reloadP(){
     $('#tBDangerSourceList').datagrid('reload');
 }

//导出
function ExportXls() {
	JeecgExcelExport("tBDangerSourceController.do?exportRelXls","tBDangerSourceList");
}
 </script>