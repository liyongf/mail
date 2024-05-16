<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBAccidentLevelList" onDblClick="dblClickDetail" checkbox="true" fitColumns="true" title="风险数据库" actionUrl="statiController.do?fxListDatagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="风险点"  field="accidentlevel" dictionary="a" query="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="风险类型"  field="standard" query="true" dictionary="b"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="风险描述"  field="color"   queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="风险等级"  field="sortindex" dictionary="c"   queryMode="single" query="true" width="120" align="center"></t:dgCol>
   <t:dgCol title="危害因素"  field="createName" url="statiController.do?wxysList" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="最高管控层级"  field="createBy" dictionary="d"  queryMode="single" query="true"  width="120" align="center"></t:dgCol>
   <t:dgCol title="最高管控责任人"  field="createDate" dictionary="e" queryMode="single" query="true"  width="140" align="center"></t:dgCol>
   <t:dgCol title="评估日期"  field="updateName"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="解除日期"  field="updateBy"   queryMode="group"  width="120" align="center"></t:dgCol>

  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">

 $(document).ready(function(){
 		//给时间控件加上样式
 			$("span[title='最高管控责任人']").css("width","100px");
 });
 </script>