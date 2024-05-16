<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBAccidentInductrialinjuryList" onDblClick="dblClickDetail" checkbox="true" fitColumns="true" title="工伤等级" actionUrl="tBAccidentInductrialinjuryController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="工伤等级"  field="inductrialinjurylevel" query="true"   queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="认定标准"  field="standard"  query="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="显示颜色"  field="color"  formatterjs="colorValueFormatter"    queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="显示排序"  field="sortindex"    queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy" hidden="true"   queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
      <%--
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBAccidentInductrialinjuryController.do?doDel&id={id}" />
   --%>
   <t:dgToolBar title="录入" operationCode="add" icon="icon-add" url="tBAccidentInductrialinjuryController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" operationCode="update" icon="icon-edit" url="tBAccidentInductrialinjuryController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除" operationCode="batchdelete"  icon="icon-remove" url="tBAccidentInductrialinjuryController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" operationCode="detail" icon="icon-search" url="tBAccidentInductrialinjuryController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
     function dblClickDetail(rowIndex,rowData){
         var id=rowData.id;
         var url = "tBAccidentInductrialinjuryController.do?goUpdate&load=detail&id="+id;
         createdetailwindow("查看",url);
     }
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBAccidentInductrialinjuryListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAccidentInductrialinjuryListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAccidentInductrialinjuryListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAccidentInductrialinjuryListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 //颜色列格式化
 function colorValueFormatter(value,rec,index){
     return '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 124px; padding-left: 26px;" type="text" value="'+value+'"><span class="minicolors-swatch" style="top:0px;"><span class="minicolors-swatch-color" style="background-color: '+value+';"></span></span></div></div>';
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBAccidentInductrialinjuryController.do?upload', "tBAccidentInductrialinjuryList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBAccidentInductrialinjuryController.do?exportXls","tBAccidentInductrialinjuryList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBAccidentInductrialinjuryController.do?exportXlsByT","tBAccidentInductrialinjuryList");
}
 </script>