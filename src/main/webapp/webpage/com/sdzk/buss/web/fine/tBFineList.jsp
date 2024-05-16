<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBFineList" checkbox="true" pagination="true" fitColumns="true" title="罚款信息" actionUrl="tBFineController.do?datagrid" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
    <t:dgCol title="唯一标识"  field="id"  hidden="true" queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="罚款单号"  field="fineNum" width="120" align="center"></t:dgCol>
    <t:dgCol title="罚款日期"  field="fineDate" query="true" formatter="yyyy-MM-dd"  queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="责任单位"  field="dutyUnit.departname" query="true" width="120" align="center"></t:dgCol>
    <t:dgCol title="被罚款人"  field="beFinedMan" query="true" width="120" align="center"></t:dgCol>
    <t:dgCol title="罚款人"  field="fineMan" query="true"  queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
    <c:if test="${xiezhuang ne 'true'}">
      <t:dgCol title="录入人"  field="createBy"  hidden="false"  queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="录入人名称"  field="createName"  hidden="false"  queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="录入时间"  field="createDate" query="true" formatter="yyyy-MM-dd" hidden="false"  queryMode="group"  width="120" align="center"></t:dgCol>
    </c:if>
    <t:dgCol title="更新人登陆名"  field="updateBy"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="更新时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="内容"  field="content"   hidden="false"  queryMode="group"  width="120" align="center"></t:dgCol>
    <c:if test="${xiezhuang ne 'true'}">
      <t:dgCol title="罚款金额"  field="fineMoney"   hidden="false"  queryMode="group"  width="120" align="center"></t:dgCol>
    </c:if>
    <c:if test="${xiezhuang eq 'true'}">
      <t:dgCol title="责任人罚款金额"  field="fineMoney"   hidden="false"  queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="单位罚款金额"  field="unitFineMoney"   hidden="false"  queryMode="group"  width="120" align="center"></t:dgCol>
    </c:if>
     <t:dgCol title="罚款性质"  field="fineProperty" dictionary="fineProperty" query="true"  hidden="false" width="120" align="center"></t:dgCol>

    <t:dgToolBar title="录入" icon="icon-add" url="tBFineController.do?goAdd" funname="add" operationCode="add"></t:dgToolBar>
    <t:dgToolBar title="编辑" icon="icon-edit" url="tBFineController.do?goUpdate" funname="update" operationCode="update" ></t:dgToolBar>
    <t:dgToolBar title="查看" icon="icon-search" url="tBFineController.do?godetail" funname="detail" operationCode="detail"></t:dgToolBar>
    <t:dgToolBar title="作废" icon="icon-remove" url="tBFineController.do?cancel" funname="cancel" operationCode="cancel" ></t:dgToolBar>
    <t:dgToolBar title="导入" icon="icon-put" url="tBFineController.do?upload" funname="ImportXls" operationCode="import"></t:dgToolBar>
    <t:dgToolBar title="导出" icon="icon-putout" url="tBFineContriller.do?exportXls" funname="ExportXls" operationCode="export" ></t:dgToolBar>
    <t:dgToolBar title="模板下载" icon="icon-putout" url="tBFineContriller.do?ExportXlsByT" funname="ExportXlsByT" operationCode="exportTemplete" ></t:dgToolBar>


    <t:dgToolBar  title="关联隐患" icon="icon-add" url="tBHiddenDangerExamController.do?goLinkFine" funname="goLinkHidden"  width="950" height="400" operationCode="linkHidden"></t:dgToolBar>
    <t:dgToolBar  title="关联三违" icon="icon-add" url="tBHiddenDangerExamController.do?goLinkFine" funname="goLinkVio"  width="950" height="400" operationCode="linkVio"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){

 });


 function goLinkVio(){
     var rowsData = $('#tBFineList').datagrid('getSelections');
     if(rowsData == null || rowsData.length != 1){
         tip("请选择一条需要关联三违的罚款信息");
     }else{
         openwindow('关联三违','tBFineController.do?chooseVioList&busId='+rowsData[0].id,"",1000,500);
     }
 }


 function goLinkHidden(){
     var rowsData = $('#tBFineList').datagrid('getSelections');
     if(rowsData == null || rowsData.length != 1){
         tip("请选择一条需要关联隐患的罚款信息");
     }else{
         openwindow('关联隐患','tBFineController.do?chooseHiddenList&busId='+rowsData[0].id,"",1000,500);
     }
 }

 window.top["reload_tBFineList"]=function(){
     $("#tBFineList").datagrid( "load");
     if(typeof(this.msg)!='undefined' && this.msg!=null&&this.msg!=""){
         tip(this.msg);
     }
 };

 //导入
 function ImportXls() {
     openuploadwin('罚款信息导入', 'tBFineController.do?upload', "tBFineList");
 }

 //导出
 function ExportXls() {
     JeecgExcelExport("tBFineController.do?exportXls", "tBFineList");
 }

 //模板下载
 function ExportXlsByT() {
     JeecgExcelExport("tBFineController.do?exportXlsByT", "tBFineList");
 }

 function cancel(title,url,gname){
     gridname=gname;
     var ids = [];
     var rows = $("#" + gname).datagrid('getSelections');
     if (rows.length > 0) {

         $.dialog.setting.zIndex = getzIndex(true);
         $.dialog.confirm('你确定作废所选罚款信息吗?', function (r) {
             if (r) {
                 for (var i = 0; i < rows.length; i++) {
                     ids.push(rows[i].id);
                 }
                 $.ajax({
                     url: url,
                     type: 'post',
                     data: {
                         ids: ids.join(',')
                     },
                     cache: false,
                     success: function (data) {
                         var d = $.parseJSON(data);
                         if (d.success) {
                             var msg = d.msg;
                             tip(msg);
                             reloadTable();
                             $("#" + gname).datagrid('unselectAll');
                             ids = '';
                         }
                     }
                 });
             }
         });
     } else {
         tip("请选择需要作废的罚款信息");
     }
 }

 </script>