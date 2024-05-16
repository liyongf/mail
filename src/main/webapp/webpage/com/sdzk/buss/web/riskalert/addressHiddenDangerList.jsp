<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBHiddenDangerHandleList" onDblClick="dblClickDetail" onLoadSuccess="loadSuccess" autoLoadData="true" checkbox="false" fitColumns="false" title="" actionUrl="tBHiddenDangerHandleController.do?queryListDatagrid&tBAddressInfoEntity_id=${addressId}&dynamic=true" idField="id" fit="true" queryMode="group">
   <t:dgCol title="发现时间"  field="hiddenDanger.examDate"  formatter="yyyy-MM-dd"  query="false" queryMode="group"  sortable="false" width="80" align="center"></t:dgCol>
   <t:dgCol title="检查班次"  field="hiddenDanger.shift" hidden="false" dictionary="workShift" query="false"  queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
  <%-- <t:dgCol title="危险源"  field="hiddenDanger.dangerId.hazard.hazardName"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>--%>
   <%--<t:dgCol title="关联危险源来源"  field="hiddenDanger.dangerId.origin"   queryMode="single"  replace="年度_2,专项_3" sortable="false" width="120" align="center"></t:dgCol>--%>
      <t:dgCol title="预警颜色" field="alertColor" hidden="true"  width="80"></t:dgCol>
      <%--<t:dgCol title="风险等级" field="yeRiskGradeTemp"  formatterjs="colorValueFormatter" sortable="false" queryMode="single" width="70" align="center"></t:dgCol>--%>
   <t:dgCol title="问题地点"  field="hiddenDanger.address.address"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="责任部门"  field="hiddenDanger.dutyUnit.departname" query="false" queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
   <t:dgCol title="责任人"  field="hiddenDanger.dutyMan" queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="检查人"  field="hiddenDanger.fillCardManNames" queryMode="single"  sortable="false" width="100" align="center"></t:dgCol>
      <t:dgCol title="信息来源"  field="hiddenDanger.manageType"  query="false" dictionary="manageType" queryMode="single"  sortable="true" width="120" align="center"></t:dgCol>
   <t:dgCol title="隐患等级"  field="hiddenDanger.hiddenNature"  query="false" dictionary="hiddenLevel"  queryMode="single"  sortable="false" width="70" align="center"></t:dgCol>

   <t:dgCol title="问题描述"  field="hiddenDanger.problemDesc"    queryMode="group"  sortable="false" width="300"></t:dgCol>
      <t:dgCol title="处理方式"  field="hiddenDanger.dealType" replace="限期整改_1,现场处理_2" width="80" align="center"></t:dgCol>
      <t:dgCol title="限期日期"  field="hiddenDanger.limitDate" formatter="yyyy-MM-dd" width="80" align="center"></t:dgCol>
      <t:dgCol title="限期班次"  field="hiddenDanger.limitShift" dictionary="workShift" hidden="true" width="80" align="center"></t:dgCol>
      <t:dgCol title="整改日期"  field="modifyDate" formatter="yyyy-MM-dd" query="false"  queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
      <t:dgCol title="整改人"  field="modifyMan"  dictionary="t_s_base_user,id,realname,where 1=1"   queryMode="group"  sortable="false" width="100" align="center"></t:dgCol>
   <t:dgCol title="复查日期"  field="reviewDate" formatter="yyyy-MM-dd"   queryMode="group"  sortable="false" width="80" align="center"></t:dgCol>
   <t:dgCol title="复查人"  field="reviewMan"  dictionary="t_s_base_user,id,realname,where 1=1"  queryMode="group"  sortable="false" width="100" align="center"></t:dgCol>
   <t:dgCol title="处理状态"  field="handlelStatus" dictionary="handelStatus" query="false"  queryMode="single"  sortable="false" width="60" align="center"></t:dgCol>

      <t:dgCol title="驳回备注"  field="rollBackRemark"  hidden="false" query="false" queryMode="group"  sortable="false" width="300"></t:dgCol>
   <t:dgCol title="录入人"  field="createName"   queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="录入日期"  field="createDate" formatter="yyyy-MM-dd"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>

      <%--<t:dgToolBar title="查看" icon="icon-search" url="tBHiddenDangerHandleController.do?goUpdate&load=detail" funname="detail" width="950" height="550" operationCode="detail"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
     function dblClickDetail(rowIndex,rowData){
         var id=rowData.id;
         var url = "tBHiddenDangerHandleController.do?goUpdate&load=detail&id="+id;
         createdetailwindow("查看",url,950,600);
     }

     function replaceProblemDesc(){
         var problemDescCells = $("td[field='hiddenDanger.problemDesc']:gt(0)");
         problemDescCells.each(function(){
             var problemDesc = $(this).children().text();
             if(problemDesc.indexOf("\\r\\n") >= 0){
                 problemDesc = problemDesc.replace(/[\\r\\n]/g, '');
                 $(this).children().empty();
                 $(this).children().append(problemDesc);
             }
         });
     }
 function loadSuccess(){
     replaceProblemDesc();
     $("td[field='hiddenDanger.limitDate']:gt(0)").each(function(){
         var limitDate = $(this).children().text();
         if(limitDate  < '${dateNow}'){
             var status = $(this).parent().children("td[field='handlelStatus']").children().text();
             if(status == '未整改'){
                 $(this).parent().css("background-color","#FFD2D2");
             }
         }
     });
 }
     //颜色列格式化
     function colorValueFormatter(value, rec, index) {
        if(value != ""){
             return '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 80px; padding-left: 26px;" type="text" value="' + value + '"><span class="minicolors-swatch" style="top:0px;"><span class="minicolors-swatch-color" style="background-color: ' + rec.alertColor + ';"></span></span></div></div>';
         }else{
             return value;
         }
     }
 </script>