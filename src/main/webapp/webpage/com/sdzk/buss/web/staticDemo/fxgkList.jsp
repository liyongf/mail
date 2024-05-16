<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<div id="main_depart_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBAccidentLevelList" onDblClick="dblClickDetail" checkbox="true" fitColumns="true" onClick="queryUsersByDepart" title="风险数据库" actionUrl="statiController.do?fxListDatagrid" idField="id" fit="true" queryMode="group">
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
      <t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
      <t:dgFunOpt funname="queryUsersByDepart()" title="风险录入" urlclass="ace_button" urlStyle="background-color:#1a7bb9;" urlfont="fa-database"></t:dgFunOpt>
      <t:dgFunOpt funname="queryUsersByDepart()" title="隐患录入" urlclass="ace_button" urlStyle="background-color:#1a7bb9;" urlfont="fa-database"></t:dgFunOpt>
      <t:dgToolBar title="新建管控清单" icon="icon-add" url="tBAccidentController.do?goAdd" width="850" funname="goadd"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<div data-options="region:'east',
	title:'<t:mutiLang langKey="member.list"/>',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
     style="width: 400px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="userListpanel"></div>
</div>
 <script type="text/javascript">

 $(document).ready(function(){
 		//给时间控件加上样式
 			$("span[title='最高管控责任人']").css("width","100px");
 });

 function queryUsersByDepart(){
     var title = '';
     if(li_east == 0 || $('#main_depart_list').layout('panel','east').panel('options').title != title){
         $('#main_depart_list').layout('expand','east');
     }
     <%--$('#eastPanel').panel('setTitle','<t:mutiLang langKey="member.list"/>');--%>
     $('#main_depart_list').layout('panel','east').panel('setTitle', title);
     $('#main_depart_list').layout('panel','east').panel('resize', {width: 500});
     $('#userListpanel').panel("refresh", "statiController.do?wxys" );
 }

 function goadd(){
     addOneTab("新建管控清单","statiController.do?goAdd","default");
 }
 </script>