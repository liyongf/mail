<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBDsManageRecordList" checkbox="true" pagination="true" fitColumns="false" title="重大风险管控记录" actionUrl="tBDsManageRecordController.do?datagrid&dangerId=${id}" idField="id" fit="true" queryMode="group" sortName="controlleDate" sortOrder="desc">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="危险源id"  field="dangerId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="管控人"  field="controller" dictionary="t_s_base_user,id,realname,where 1=1"   queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="管控时间"  field="controlleDate" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="工作内容"  field="workContent"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="取得效果"  field="achieveEffect"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
   <%--<t:dgDelOpt title="删除" url="tBDsManageRecordController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>--%>
   <t:dgToolBar title="录入" icon="icon-add" url="tBDsManageRecordController.do?goAdd&dangerId=${id}" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBDsManageRecordController.do?goUpdate" funname="update"  ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBDsManageRecordController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBDsManageRecordController.do?goUpdate" funname="detail" ></t:dgToolBar>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/dsManageRecord/tBDsManageRecordList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
////导入
//function ImportXls() {
//	openuploadwin('Excel导入', 'tBDsManageRecordController.do?upload', "tBDsManageRecordList");
//}
//
////导出
//function ExportXls() {
//	JeecgExcelExport("tBDsManageRecordController.do?exportXls","tBDsManageRecordList");
//}
//
////模板下载
//function ExportXlsByT() {
//	JeecgExcelExport("tBDsManageRecordController.do?exportXlsByT","tBDsManageRecordList");
//}

 </script>