<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="wechatTemplateManagementList" checkbox="true" pagination="true" treegrid="true" treeField="modelName" fitColumns="true" title="微信模板管理表" actionUrl="wechatTemplateManagementController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="id"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="父模块名称"  field="parentModelName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="模块名称"  field="modelName"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="模块类型"  field="modelType" dictionary="wechat_model_type" hidden="false"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="模块内容"  field="modelContent"  queryMode="group"  width="360"></t:dgCol>
   <t:dgCol title="备用"  field="remark"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="wechatTemplateManagementController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="wechatTemplateManagementController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="wechatTemplateManagementController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="wechatTemplateManagementController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="wechatTemplateManagementController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/jeecg/wechattemplatemanagement/wechatTemplateManagementList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
		$("#wechatTemplateManagementList").treegrid({
 				 onExpand : function(row){
 					var children = $("#wechatTemplateManagementList").treegrid('getChildren',row.id);
 					 if(children.length<=0){
 					 	row.leaf=true;
 					 	$("#wechatTemplateManagementList").treegrid('refresh', row.id);
 					 }
 				}
 		});
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'wechatTemplateManagementController.do?upload', "wechatTemplateManagementList");
}

//导出
function ExportXls() {
	JeecgExcelExport("wechatTemplateManagementController.do?exportXls","wechatTemplateManagementList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("wechatTemplateManagementController.do?exportXlsByT","wechatTemplateManagementList");
}

/**
 * 获取表格对象
 * @return 表格对象
 */
function getDataGrid(){
	var datagrid = $('#'+gridname);
	return datagrid;
}
 </script>