<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBHealthRecuperateList" onDblClick="dblClickDetail" checkbox="true" fitColumns="true" title="职业健康疗养专项档案" actionUrl="tBHealthRecuperateController.do?datagrid" idField="id" fit="true" queryMode="group">
  <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
    <t:dgCol title="职业健康疗养id"  field="tBHealthRecuperateEntity.id"  hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="档案号"  field="fileNo"   query="true" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="在岗编号"  field="postNumber"   query="true" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="退休编号"  field="retireNumber"   query="true" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="姓名"  field="name"  query="true" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="性别"  field="gender"    queryMode="single" dictionary="sex" width="120" align="center"></t:dgCol>
   <t:dgCol title="身份证号"  field="cardNumber"   query="true" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="参加工作时间"  field="partiWorkDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="现工作单位"  field="currWorkUnits"   query="true" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="现从事工种"  field="currTrade"   hidden="true" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="接触职业危害种类"  field="jobHazardCategory"  hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="岗位类别"  field="postCategory" hidden="true"   queryMode="single" dictionary="postcategory" width="120" align="center"></t:dgCol>
   <t:dgCol title="岗位状态"  field="postStatus" hidden="true"   queryMode="single" dictionary="poststatus" width="120" align="center"></t:dgCol>
   <t:dgCol title="是否删除"  field="isDelete"  hidden="true"  queryMode="single"  width="120" align="center"></t:dgCol>
    <t:dgCol title="疗养时间"  field="tBHealthRecuperateEntity.recupDate" sortable="false" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="疗养周期(年)" field="tBHealthRecuperateEntity.recupCycle"  sortable="false"   queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="疗养地点"  field="tBHealthRecuperateEntity.recupAddress"  sortable="false"   queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="下次疗养时间"  field="tBHealthRecuperateEntity.nextRecupDate" sortable="false"  formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="今年是否疗养"  field="tBHealthRecuperateEntity.thisYearIsRecup"  sortable="false"   queryMode="group"  width="120" align="center"></t:dgCol>

   <t:dgToolBar title="编辑" operationCode="update" icon="icon-edit" url="tBHealthRecuperateController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" operationCode="detail" icon="icon-search" url="tBHealthRecuperateController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" operationCode="import" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" operationCode="export" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
   <t:dgToolBar title="导入模板下载" operationCode="downloadTmpl" icon="icon-putout" funname="ImportXlsT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>	
 <script type="text/javascript">
     function dblClickDetail(rowIndex,rowData){
         var id=rowData.id;
         var url = "tBHealthRecuperateController.do?goUpdate&load=detail&id="+id;
         createdetailwindow("查看",url);
     }
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBHealthRecuperateListtb").find("input[name='recupDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHealthRecuperateListtb").find("input[name='recupDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHealthRecuperateListtb").find("input[name='nextRecupDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHealthRecuperateListtb").find("input[name='nextRecupDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHealthRecuperateListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHealthRecuperateListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHealthRecuperateListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHealthRecuperateListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBHealthRecuperateController.do?upload', "tBHealthRecuperateList");
}

//导出
function ExportXlsByT() {
	JeecgExcelExport("tBHealthRecuperateController.do?exportXlsByT","tBHealthRecuperateList");
}

//模板下载
function ImportXlsT() {
	JeecgExcelExport("tBHealthRecuperateController.do?importXlsT","tBHealthRecuperateList");
}
 </script>