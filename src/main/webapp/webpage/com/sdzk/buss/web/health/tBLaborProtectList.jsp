<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBLaborProtectList" onDblClick="dblClickDetail" checkbox="true" fitColumns="true" title="职工劳动防护专项档案" actionUrl="tBLaborProtectController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
    <t:dgCol title="档案号"  field="tBEmployeeInfoEntity.fileNo"   hidden="true" query="true" queryMode="single"  width="120" align="center"></t:dgCol>
     <t:dgCol title="在岗编号"  field="tBEmployeeInfoEntity.postNumber"   hidden="true" query="true" queryMode="single"  width="120" align="center"></t:dgCol>
      <t:dgCol title="退休编号"  field="tBEmployeeInfoEntity.retireNumber"   hidden="true" query="true" queryMode="single"  width="120" align="center"></t:dgCol>
      <t:dgCol title="身份证号"  field="tBEmployeeInfoEntity.cardNumber"   hidden="true" query="true" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="车间名称"  field="unitName"   query="true" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="接触职业病危害因素"  field="hazardsFactor"    queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="个人防护用品名称"  field="protectEquipment"    queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="型号"  field="model"    queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="数量"  field="amount"    queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="领取人"  field="tBEmployeeInfoEntity.name"   query="true" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="领取日期"  field="receiveDate" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120" align="center"></t:dgCol>
      <%--
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" operationCode="delete" url="tBLaborProtectController.do?doDel&id={id}" />
   --%>
   <t:dgToolBar title="录入" operationCode="add" icon="icon-add" url="tBLaborProtectController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" operationCode="update" icon="icon-edit" url="tBLaborProtectController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除" operationCode="batchdelete"  icon="icon-remove" url="tBLaborProtectController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" operationCode="detail" icon="icon-search" url="tBLaborProtectController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" operationCode="import" icon="icon-put" funname="ImportXls"></t:dgToolBar>
  <t:dgToolBar title="导出" operationCode="export" icon="icon-putout" funname="ExportDocByT"></t:dgToolBar>
   <t:dgToolBar title="导入模板下载" operationCode="downloadTmpl" icon="icon-putout" funname="ImportXlsT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>	
 <script type="text/javascript">
     function dblClickDetail(rowIndex,rowData){
         var id=rowData.id;
         var url = "tBLaborProtectController.do?goUpdate&load=detail&id="+id;
         createdetailwindow("查看",url);
     }
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBLaborProtectListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBLaborProtectListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBLaborProtectListtb").find("input[name='receiveDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBLaborProtectListtb").find("input[name='receiveDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBLaborProtectListtb").find("input[name='receiveDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBLaborProtectController.do?upload', "tBLaborProtectList");
}

//导出
function ExportDocByT() {
	JeecgExcelExport("tBLaborProtectController.do?exportDocByT","tBLaborProtectList");
}

//模板下载
function ImportXlsT() {
	JeecgExcelExport("tBLaborProtectController.do?importXlsT","tBLaborProtectList");
}
 </script>