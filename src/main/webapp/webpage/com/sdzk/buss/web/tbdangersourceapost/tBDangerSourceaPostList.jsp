<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBDangerSourceaPostList" checkbox="true" pagination="true" fitColumns="false" title="岗位通用危险源库" actionUrl="tBDangerSourceaPostController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="辨识时间"  field="recognizeTime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="危险源名称"  field="dangerName"    queryMode="single" query="true" width="120"></t:dgCol>
      <t:dgCol title="责任部门"  field="resDepart"    queryMode="group"  width="120"></t:dgCol>
      <%--<t:dgCol title="是否是重大隐患"  field="ismajor" replace="否_0,是_1"   queryMode="group"  width="120"></t:dgCol>--%>
      <t:dgCol title="工种"  field="postId"  dictionary="t_b_post_manage,id, post_name,where 1=1" query="true"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="专业分类"  field="professionaltype" dictionary="proCate_gradeControl" query="true" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="事故类型"  field="accidentTypeTemp"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="风险类型"  field="riskType"  dictionary="hazardCate"   queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="风险等级"  field="riskLevel"  dictionary="riskLevel" query="true"  queryMode="single"  width="120"></t:dgCol>
      <c:if test="${initParam.les == 'no'}">
          <t:dgCol title="风险可能性"  field="riskPossibility" dictionary="probability" queryMode="group"  width="120"></t:dgCol>
          <t:dgCol title="风险损失"  field="riskLoss"   dictionary="hazard_fxss" queryMode="group"  width="120"></t:dgCol>
          <t:dgCol title="风险值"  field="riskValue"    queryMode="group"  width="120"></t:dgCol>
      </c:if>
      <c:if test="${initParam.les == 'yes'}">
          <t:dgCol title="风险可能性"  field="lecRiskPossibility" dictionary="lec_risk_probability" queryMode="group"  width="120"></t:dgCol>
          <t:dgCol title="风险损失"  field="lecRiskLoss"   dictionary="lec_risk_loss" queryMode="group"  width="120"></t:dgCol>
          <t:dgCol title="暴露在风险中的频率"  field="lecExposure"   dictionary="lec_exposure" queryMode="group"  width="120"></t:dgCol>
          <t:dgCol title="风险值"  field="lecRiskValue"    queryMode="group"  width="120"></t:dgCol>
      </c:if>
      <t:dgCol title="风险后果描述"  field="riskAffectDesc"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="责任措施"  field="respMeasures"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="管控措施"  field="mangMeasures"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="管控标准"  field="mangStandards"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="市场内部价"  field="internalMarketPrice"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="标准依据"  field="standardAccordance"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="依据条目"  field="basedEntry"    queryMode="group"  width="120"></t:dgCol>
      <%--<t:dgCol title="危险距离"  field="distance"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="周边情况及相互影响因素"  field="surrounding"    queryMode="group"  width="120"></t:dgCol>--%>
      <%--<t:dgCol title="监控措施"  field="monitor"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="应急措施"  field="emergency"    queryMode="group"  width="120"></t:dgCol>--%>
      <%--<t:dgCol title="备案号"  field="caseNum"    queryMode="group"  width="120"></t:dgCol>--%>
   <t:dgToolBar title="录入" icon="icon-add" url="tBDangerSourceaPostController.do?goAdd" funname="goAddDangerSource" operationCode="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBDangerSourceaPostController.do?goUpdate" funname="goUpdateDangerSource" operationCode="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBDangerSourceaPostController.do?doBatchDel" funname="deleteALLSelect" operationCode="batchdelete"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBDangerSourceaPostController.do?goUpdate" funname="detail" operationCode="detail"></t:dgToolBar>
   <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>--%>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT" operationCode="downloadTmpl"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls" operationCode="import"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/tbdangersourceapost/tBDangerSourceaPostList.js"></script>		
 <script type="text/javascript">
     window.top["reload_tBDangerSourcePostList"]=function(){
         $("#tBDangerSourceaPostList").datagrid( "load");
     };
 $(document).ready(function(){
 });

     /**
      *跳转添加页面
      */
     function goAddDangerSource(){
         addOneTab("工种关联风险录入","tBDangerSourceaPostController.do?goAdd","default");
     }

     /**
      *跳转到编辑页面
      */
     function goUpdateDangerSource(){
         //获取选中条目
         var rowsData = $('#tBDangerSourceaPostList').datagrid('getSelections');
         if(rowsData == null || rowsData.length != 1){
             tip("请选择一条需要编辑的条目");
         }else{
             var id = rowsData[0].id;
             addOneTab("工种关联风险编辑","tBDangerSourceaPostController.do?goUpdate&id="+id,"default");
         }
     }
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBDangerSourceaPostController.do?upload', "tBDangerSourceaPostList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBDangerSourceaPostController.do?exportXls&lec=${initParam.les}","tBDangerSourceaPostList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBDangerSourceaPostController.do?exportXlsByT&lec=${initParam.les}","tBDangerSourceaPostList");
}

 </script>