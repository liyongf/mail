<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%--<t:base type="jquery,easyui,tools,DatePicker"></t:base>--%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBDangerSourceList" checkbox="true" pagination="true" fitColumns="false" title="" actionUrl="tBDangerSourceController.do?seDangerSourceDatagrid&seId=${seId}" onLoadSuccess="sedsLoadSuccess" idField="id" fit="true" queryMode="group" sortName="isMajor,yeRecognizeTime" sortOrder="desc,desc">
      <t:dgCol title="唯一编号" field="id" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
      <t:dgCol title="辨识时间" field="yeRecognizeTime" query="true" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="隐患描述" field="yeMhazardDesc" queryMode="single" query="true" width="180"></t:dgCol>
      <t:dgCol title="专业" field="yeProfession" query="true" dictionary="proCate_gradeControl" queryMode="single"
               width="60" align="center"></t:dgCol>
      <t:dgCol title="危险源名称" field="hazard.hazardName" queryMode="single" width="80" align="center"></t:dgCol>
      <t:dgCol title="伤害类别" field="damageType" query="true" dictionary="danger_Category" queryMode="single"
               width="80" align="center"></t:dgCol>
      <t:dgCol title="风险描述" field="yePossiblyHazard" queryMode="single" query="true" width="180"></t:dgCol>
      <t:dgCol title="事故类型" field="yeAccidentTemp" queryMode="group" width="80" align="center"></t:dgCol>
      <t:dgCol title="作业活动" field="activity.id" dictionary="t_b_activity_manage where is_delete='0',id,activity_name" query="true" queryMode="single" width="100" align="center"></t:dgCol>
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
      <t:dgCol title="管控标准来源" field="docSource" queryMode="single" query="true" width="100" align="center"></t:dgCol>
      <t:dgCol title="章节条款" field="sectionName" queryMode="single" query="false" width="100" align="center"></t:dgCol>
      <t:dgCol title="标准内容" field="yeStandard" queryMode="single" query="true" width="180" align="center"></t:dgCol>
      <t:dgCol title="管控措施" field="manageMeasure" queryMode="group" width="180" align="center"></t:dgCol>
      <t:dgCol title="责任岗位" field="post.postName" queryMode="single" width="100" align="center"></t:dgCol>
      <t:dgCol title="隐患等级" field="hiddenLevel" dictionary="hiddenLevel" queryMode="single" query="true" width="100" align="center"></t:dgCol>
      <t:dgCol title="罚款金额(元)" field="fineMoney" queryMode="group" width="80" align="center"></t:dgCol>
      <t:dgCol title="风险状态" field="auditStatus" replace="待上报_1,审核中_2,审核退回_3,闭环_4" queryMode="single" width="60"
               align="center"></t:dgCol>
      <%--<t:dgCol title="危险源来源" field="origin" replace="通用_1,年度_2,专项_3" queryMode="group" width="80"
               align="center"></t:dgCol>--%>

      <c:if test="${type eq 'depart'}">
           <t:dgToolBar title="录入" icon="icon-add" funname="goAddDepartDangerSource"></t:dgToolBar>
          <t:dgToolBar title="编辑" icon="icon-edit" url="tBDangerSourceController.do?goUpdate&seId=${seId}" funname="goUpdateDepartDangerSource" ></t:dgToolBar>
          <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBDangerSourceController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
           <%--<t:dgToolBar title="编辑" icon="icon-edit" url="tBDangerSourceController.do?goUpdate&seId=${seId}" funname="goUpdateDepartDangerSource" ></t:dgToolBar>--%>
           <t:dgToolBar title="查看" icon="icon-search" url="tBDangerSourceController.do?goDetail&seId=${seId}" funname="detail" width="850" height="450"></t:dgToolBar>
           <%--<t:dgToolBar title="批量删除"  icon="icon-remove" url="tBDangerSourceController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>--%>
          <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
          <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
          <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
      </c:if>
      <c:if test="${type ne 'depart'}">
          <t:dgToolBar title="编辑" icon="icon-edit" url="tBDangerSourceController.do?goUpdate&seId=${seId}" funname="goUpdateDepartDangerSource" ></t:dgToolBar>
          <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBDangerSourceController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
          <t:dgToolBar title="查看" icon="icon-search" width="850" height="640" url="tBDangerSourceController.do?goDetail&seId=${seId}" funname="detail"></t:dgToolBar>
          <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
      </c:if>
   <%--<t:dgToolBar title="设置重大风险标记"  icon="icon-edit" url="tBDangerSourceController.do?doIsMajor" funname="markIsMajor"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="撤除重大风险标记"  icon="icon-edit" url="tBDangerSourceController.do?doIsMajor" funname="cancelIsMajor"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/dangersource/js/tBDangerSourceList.js"></script>
 <script type="text/javascript">
     $(function(){
         $("a[iconcls='icon-reload']").hide();
     })
     function sedsLoadSuccess(){
         var selectedvalue = $("input[name='statusTemp']:checked").val();
         if (selectedvalue != '0'){
             $("span:contains('录入')").parents("a.l-btn").hide();
             $("span:contains('编辑')").parents("a.l-btn").hide();
             $("span:contains('删除')").parents("a.l-btn").hide();
             $("span:contains('导入')").parents("a.l-btn").hide();
             $("span:contains('模板下载')").parents("a.l-btn").hide();
         }
     }
     window.top["reload_seDangerSourceList"]=function(){
         $("#tBDangerSourceList").datagrid( "load");
     };
     /**
     *跳转添加年度部门危险源页面
      */
     function goAddDepartDangerSource(){
         addOneTab("专项风险管控录入","tBDangerSourceController.do?goAddSeDangerSource&seId=${seId}","default");
     }
     /**
     *跳转到编辑年度危险源页面
      */
   function goUpdateDepartDangerSource(){
       //获取选中条目
       var rowsData = $('#tBDangerSourceList').datagrid('getSelections');
       if(rowsData == null || rowsData.length != 1){
           tip("请选择一条需要编辑的条目");
       }else{
           var id = rowsData[0].id;
           addOneTab("专项风险管控编辑","tBDangerSourceController.do?goUpdateSeDangerSource&id="+id,"default");
       }
   }
//导出
    function ExportXls() {
	JeecgExcelExport("tBDangerSourceController.do?exportXls&type=special&seId=${seId}&lec=${initParam.les}","tBDangerSourceList");
}
     //导入
     function ImportXls() {
         openuploadwin('专项风险辨识结果导入', 'tBDangerSourceController.do?upload&type=special&seId=${seId}', "tBDangerSourceList");
     }

     //模板下载
     function ExportXlsByT() {
         JeecgExcelExport("tBDangerSourceController.do?exportXlsByTForSpecial&lec=${initParam.les}","tBDangerSourceList");
     }
 </script>