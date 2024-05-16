<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tSAppFunctionList" checkbox="false" treegrid="true" pagination="false" fitColumns="false" title="APP菜单管理" actionUrl="tSAppFunctionController.do?functionGrid" idField="id" fit="true" queryMode="group">
      <t:dgCol title="菜单名称"  field="functionname"  treefield="text"  queryMode="single"  width="240"></t:dgCol>
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="菜单顺序"  field="functionorder"  treefield="fieldMap.functionorder"  queryMode="single"  width="120" align="center"></t:dgCol>
    <t:dgCol title="菜单编码"  field="functioncode"  treefield="fieldMap.functioncode"  queryMode="single"  width="120" align="center"></t:dgCol>

   <t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
   <t:dgDelOpt title="删除" url="tSAppFunctionController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tSAppFunctionController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tSAppFunctionController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tSAppFunctionController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/tsappfunction/tSAppFunctionList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tSAppFunctionController.do?upload', "tSAppFunctionList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tSAppFunctionController.do?exportXls","tSAppFunctionList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tSAppFunctionController.do?exportXlsByT","tSAppFunctionList");
}

 </script>