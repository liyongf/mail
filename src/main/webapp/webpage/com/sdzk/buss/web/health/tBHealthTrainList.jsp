<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBHealthTrainList" onDblClick="dblClickDetail" checkbox="true" fitColumns="true" title="职业安全卫生教育培训专项档案" actionUrl="tBHealthTrainController.do?datagrid" idField="id" fit="true" queryMode="group">
  <!-- 组合表头定义 -->
  <t:dgCombCol title="上岗前" colspan="4"></t:dgCombCol>
  <t:dgCombCol title="在岗期间" colspan="3"></t:dgCombCol>
  <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="single"  width="120"></t:dgCol>

    <t:dgCol title="职业健康培训id"  field="tBHealthTrainEntity.id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="档案号"  field="fileNo" align="center"  frozenColumn="true" query="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="在岗编号"  field="postNumber" align="center" frozenColumn="true" query="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="退休编号"  field="retireNumber" align="center" frozenColumn="true" query="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="姓名"  field="name"  query="true" align="center" frozenColumn="true" queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="性别"  field="gender"    queryMode="single" align="center" frozenColumn="true" dictionary="sex" width="40"></t:dgCol>
   <t:dgCol title="身份证号"  field="cardNumber"   query="true" align="center" frozenColumn="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="参加工作时间"  field="partiWorkDate" hidden="true" frozenColumn="true" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="现工作单位"  field="currWorkUnits"  align="center" query="true" frozenColumn="true" queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="现从事工种"  field="currTrade"   hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="接触职业危害种类"  field="jobHazardCategory"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="岗位类别"  field="postCategory" hidden="true"   queryMode="single" dictionary="postcategory" width="120"></t:dgCol>
   <t:dgCol title="岗位状态"  field="postStatus" hidden="true"   queryMode="single" dictionary="poststatus" width="120"></t:dgCol>
   <t:dgCol title="是否删除"  field="isDelete"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="培训类型"  field="tBHealthTrainEntity.prejobTrainType"  align="center" queryMode="group" dictionary="chk_category" width="120"></t:dgCol>
   <t:dgCol title="培训时间"  field="tBHealthTrainEntity.prejobTrainDate" align="center" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="综合成绩"  field="tBHealthTrainEntity.prejobConResult"  align="center"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="培训机构"  field="tBHealthTrainEntity.prejobTrainOrg"  align="center"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="培训时间"  field="tBHealthTrainEntity.duringTrainDate" align="center" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="培训机构"  field="tBHealthTrainEntity.duringTrainOrg"   align="center" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="综合成绩"  field="tBHealthTrainEntity.duringConResult"  align="center"  queryMode="group"  width="80"></t:dgCol>
   <t:dgToolBar title="编辑" operationCode="update" icon="icon-edit" url="tBHealthTrainController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" operationCode="detail" icon="icon-search" url="tBHealthTrainController.do?goUpdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="导入" operationCode="import" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" operationCode="export" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
   <t:dgToolBar title="导入模板下载" operationCode="downloadTmpl" icon="icon-putout" funname="ImportXlsT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
     function dblClickDetail(rowIndex,rowData){
         var id=rowData.id;
         var url = "tBHealthTrainController.do?goUpdate&load=detail&id="+id;
         createdetailwindow("查看",url);
     }
 $(document).ready(function(){
 		//给时间控件加上样式
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBHealthTrainController.do?upload', "tBHealthTrainList");
}

//导出
function ExportXlsByT() {
	JeecgExcelExport("tBHealthTrainController.do?exportXlsByT","tBHealthTrainList");
}

//模板下载
function ImportXlsT() {
	JeecgExcelExport("tBHealthTrainController.do?importXlsT","tBHealthTrainList");
}
 </script>