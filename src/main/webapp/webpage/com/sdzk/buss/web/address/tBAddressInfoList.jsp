<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBAddressInfoList" checkbox="true" pagination="true" fitColumns="false" title="地点列表" actionUrl="tBAddressInfoController.do?datagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="地点名称"  field="address"    queryMode="single"  width="120" query="true"></t:dgCol>
    <t:dgCol title="经度"  field="lon"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="纬度"  field="lat"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="是否显示"  field="isshow"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="是否删除"  field="isDelete"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBAddressInfoController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tBAddressInfoController.do?goAdd" funname="add" operationCode="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBAddressInfoController.do?goUpdate" funname="update" operationCode="update" ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBAddressInfoController.do?doBatchDel" funname="deleteALLSelect" operationCode="batchdelete" ></t:dgToolBar>
   <%--<t:dgToolBar title="查看" icon="icon-search" url="tBAddressInfoController.do?goUpdate" funname="detail"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/address/tBAddressInfoList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBAddressInfoController.do?upload', "tBAddressInfoList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBAddressInfoController.do?exportXls","tBAddressInfoList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBAddressInfoController.do?exportXlsByT","tBAddressInfoList");
}

 </script>