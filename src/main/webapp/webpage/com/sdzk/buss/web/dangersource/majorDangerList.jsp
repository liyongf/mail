<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!-- 重大风险管控 -->

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBDangerSourceList" checkbox="true" pagination="true" fitColumns="false" title="重大风险管控" actionUrl="tBDangerSourceController.do?majorDangerSourceDatagrid" idField="id" fit="true" queryMode="group" sortName="isMajor,yeRecognizeTime" sortOrder="desc,desc">
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
      <%--<t:dgCol title="风险等级" field="yeRiskGrade" dictionary="riskLevel" query="true" queryMode="single" width="70"--%>
               <%--align="center"></t:dgCol>--%>
      <t:dgCol title="风险等级" field="yeRiskGrade" hidden="true" dictionary="riskLevel" query="true" queryMode="single" width="70" align="center"></t:dgCol>
      <t:dgCol title="预警颜色" field="alertColor" hidden="true" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="风险等级" field="yeRiskGradeTemp"  formatterjs="colorValueFormatter" sortable="false" queryMode="single" width="70"
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
      <t:dgToolBar title="管控记录" icon="icon-add" url="tBDsManageRecordController.do?list" funname="goManageRecordList" operationCode="manageRecord" ></t:dgToolBar>
      <t:dgToolBar title="管控方案" icon="icon-search" url="tBDsManageRecordController.do?goAddFiels" funname="update" operationCode="manageFile" ></t:dgToolBar>
      <t:dgToolBar title="上报省局平台" icon="icon-putout" url="tBDangerSourceController.do?reportMajorYearRisk" funname="goReport" operationCode="report" ></t:dgToolBar>
      <%--<t:dgToolBar title="上报集团" icon="icon-putout" url="tBDangerSourceController.do?reportMajorYearRiskToGroup" funname="reportMajorYearRiskToGroup" operationCode="reportGroup"></t:dgToolBar>--%>
      <t:dgToolBar title="查看" icon="icon-search" url="tBDangerSourceController.do?goDetail" funname="goDetail" width="850" height="450" operationCode="detail"></t:dgToolBar>
      <c:if test="${isSunAdmin == 'YGADMIN'}">
          <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
      </c:if>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/dangersource/js/tBDangerSourceList.js"></script>
 <script type="text/javascript">

 $(document).ready(function(){
 });
 /**
  *上报集团
  */
 function reportMajorYearRiskToGroup(title,url,gname) {
     var ids = [];
     var rows = $("#"+gname).datagrid('getSelections');
     if (rows.length > 0) {
         for ( var i = 0; i < rows.length; i++) {
             ids.push(rows[i].id);
         }
     } else {
         tip("请选择需要上报的数据");
         return;
     }
     $.ajax({
         url : url,
         type : 'post',
         cache : false,
         data : {
             ids : ids.join(',')
         },
         beforeSend: function(data){
             $.messager.progress({
                 text : "正在上报数据......",
                 interval : 100
             });
         },
         success : function(data) {
             var d = $.parseJSON(data);
             if (d.success) {
                 reloadTable();
                 $("#"+gname).datagrid('unselectAll');
                 ids='';
             }
             var msg = d.msg;
             tip(msg);
         },
         complete: function(date){
             $.messager.progress('close');
         },
         error:function(data){
             tip("操作失败");//should not reach here
         }
     });
 }
 /**
  * 查看风险详情
  * */
function goDetail(){
    var rowsData = $('#tBDangerSourceList').datagrid('getSelections');
    if(rowsData == null || rowsData.length != 1){
        tip("请选择一条需要查看的条目");
    }else{
        var url = 'tBDangerSourceController.do?goDetail&from=universalDangerList&load=detail&id='+rowsData[0]["id"];
        createdetailwindow("查看",url,850,450);
    }
}
 /**
  * 查看管控记录
  */
 function goManageRecordList(){
     var rowsData = $('#tBDangerSourceList').datagrid('getSelections');
     if(rowsData == null || rowsData.length != 1){
         tip("请选择一条需要查看的条目");
     }else{
         addOneTab("重大风险管控记录","tBDsManageRecordController.do?list&id="+rowsData[0]["id"],"default");
     }
 }
 function goReport(title,url,gname) {
     gridname=gname;
     var ids = [];
     var type = 'choose';
     if(title!="全部上报"){
         var rows = $("#"+gname).datagrid('getSelections');
         if (rows.length > 0) {
             for ( var i = 0; i < rows.length; i++) {
                 ids.push(rows[i].id);
             }
         } else {
             tip("请选择需要上报的数据");
             return;
         }
     } else {
         type = 'all';
     }
     $.dialog.confirm('你确定上报所选中数据吗?', function(r) {
         this.close();
         if (r) {
             $.ajax({
                 url : url,
                 type : 'post',
                 cache : false,
                 data : {
                     ids : ids.join(','),
                     type : type
                 },
                 beforeSend: function(data){
                     $.messager.progress({
                         text : "正在上报数据......",
                         interval : 100
                     });
                 },
                 success : function(data) {
                     var d = $.parseJSON(data);
                     if (d.success) {
                         reloadTable();
                         $("#"+gname).datagrid('unselectAll');
                         ids='';
                     }
                     var msg = d.msg;
                     tip(msg);
                 },
                 complete: function(date){
                     $.messager.progress('close');
                 },
                 error:function(data){
                     tip("操作失败");//should not reach here
                 }
             });
         }
     });
 }

 /**
  *  阳光账号隐藏数据功能
  * */
 function sunHidden() {
     var rows = $("#tBDangerSourceList").datagrid('getSelections');
     if (rows.length < 1) {
         tip("请选择需要隐藏的数据");
     } else {
         var idsTemp = new Array();
         for (var i = 0; i < rows.length; i++) {
             idsTemp.push(rows[i].id);
         }
         var idt = idsTemp.join(",");

         $.ajax({
             type: 'POST',
             url: 'tBDangerSourceController.do?sunshine',
             dataType:"json",
             async:true,
             cache: false,
             data: {
                 ids:idt
             },
             success:function(data){
                 var msg = data.msg;
                 tip(msg);
                 reloadTable();
             },
             error:function(data){
             }
         });
     }
 }
 //颜色列格式化
 function colorValueFormatter(value, rec, index) {
//     if(value != ""){
     return '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 80px; padding-left: 26px;" type="text" value="' + value + '"><span class="minicolors-swatch" style="top:0px;"><span class="minicolors-swatch-color" style="background-color: ' + rec.alertColor + ';"></span></span></div></div>';
//     }else{
//         return value;
//     }
 }
 </script>