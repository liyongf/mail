<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBHiddenDangerHandleList"  onLoadSuccess="replaceProblemDesc" checkbox="true" title="隐患查询" actionUrl="tBHiddenDangerHandleController.do?datagrid&risk_type=${risk_type}&pro_type=${pro_type}&departId=${departId}&wxy=${wxy}&riskLevel=${riskLevel}&month=${month}&query_type=${query_type}" sortName="hiddenDanger.examDate" sortOrder="desc" idField="id" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="隐患检查主键"  field="hiddenDanger.id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <c:if test="${huayuan eq 'true'}">
          <t:dgCol title="隐患编号"  field="hiddenDanger.hiddenNumber" query="true" sortable="false" width="80" align="center"></t:dgCol>
      </c:if>
   <t:dgCol title="责任部门" field="hiddenDanger.dutyUnit.departname" queryMode="single"  sortable="false" width="150" align="center"></t:dgCol>
   <t:dgCol title="责任人"  field="hiddenDanger.dutyMan" queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="发现时间"  field="hiddenDanger.examDate"  formatter="yyyy-MM-dd"  query="true" queryMode="group"  sortable="false" width="80" align="center"></t:dgCol>
   <%--<t:dgCol title="危险源"  field="hiddenDanger.dangerId.hazard.hazardName"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>--%>
   <t:dgCol title="问题地点"  field="hiddenDanger.address.address"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="检查班次"  field="hiddenDanger.shift" hidden="false" dictionary="workShift" query="true"  queryMode="single"  sortable="false" width="60" align="center"></t:dgCol>
   <t:dgCol title="检查人"  field="hiddenDanger.fillCardManNames" queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
   <%--<t:dgCol title="检查类型"  field="hiddenDanger.examType"  query="true" dictionary="examType" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>--%>
      <t:dgCol title="信息来源"  field="hiddenDanger.manageType"  query="true" dictionary="manageType" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="隐患等级"  field="hiddenDanger.hiddenNature"  query="true" dictionary="hiddenLevel"  queryMode="single"  sortable="false" width="60" align="center"></t:dgCol>

   <t:dgCol title="问题描述"  field="hiddenDanger.problemDesc" formatterjs="valueTitle" queryMode="group"  sortable="false" width="300"></t:dgCol>
   <t:dgCol title="处理状态"  field="handlelStatus" dictionary="handelStatus" query="true"  queryMode="single"  sortable="false" width="70" align="center"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>

   <%--<t:dgToolBar title="查看" icon="icon-search" url="riskManageResultController.do?updateHd" funname="detail" width="950" height="550" operationCode="detail"></t:dgToolBar>--%>
      <t:dgToolBar title="查看" icon="icon-search" url="tBHiddenDangerHandleController.do?goUpdate&load=detail" funname="detail" width="950" height="550" operationCode="detail"></t:dgToolBar>
   <c:if test="${isSunAdmin == 'YGADMIN'}">
    <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
   </c:if>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
//     function detail(title,url, id,width,height) {
//         var rowsData = $('#'+id).datagrid('getSelections');
//
//         if (!rowsData || rowsData.length == 0) {
//             tip('请选择查看项目');
//             return;
//         }
//         if (rowsData.length > 1) {
//             tip('请选择一条记录再查看');
//             return;
//         }
//         url += '&load=detail&id='+rowsData[0]["hiddenDanger.id"];
//         createdetailwindow(title,url,width,height);
//     }
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

     /**
      *  阳光账号隐藏数据功能
      * */
     function sunHidden() {
         var rows = $("#tBHiddenDangerHandleList").datagrid('getSelections');
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
                 url: 'tBHiddenDangerHandleController.do?sunshine',
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
 </script>