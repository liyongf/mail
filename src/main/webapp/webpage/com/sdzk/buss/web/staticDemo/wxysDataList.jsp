<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBAccidentLevelList" onDblClick="dblClickDetail" checkbox="true" width="900px" height="450px" fitColumns="true" title="危害因素" actionUrl="statiController.do?whysDataListDatagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="风险类型"  field="fxlx"  queryMode="single" query="true" dictionary="a" width="120" align="center"></t:dgCol>
   <t:dgCol title="危险源名称"  field="wxymc"  queryMode="single" query="true" dictionary="b"  width="120" align="center"></t:dgCol>
   <t:dgCol title="专业"  field="zy" query="true" dictionary="c" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="危害因素"  field="accidentlevel"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="管控措施"  field="standard" queryMode="single"  width="120" align="center"></t:dgCol>

      <t:dgToolBar title="增加" icon="icon-add" url="tBAccidentController.do?goAdd" width="850" funname="add"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="icon-edit" url="tBAccidentController.do?goUpdate" width="850" funname="update"></t:dgToolBar>
      <t:dgToolBar title="删除" icon="icon-remove" url="tBAccidentController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">

 $(document).ready(function(){
 		//给时间控件加上样式
 });
 </script>