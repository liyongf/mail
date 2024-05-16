<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBSmsList" title="短信记录" fitColumns="false" checkbox="true" actionUrl="tBSmsController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="内容" field="content" formatterjs="valueTitle"    width="600"></t:dgCol>
   <t:dgCol title="类型" field="type" replace="隐患上报_1,隐患整改_2,隐患超期_3"  width="150" query="true" align="center"></t:dgCol>
      <t:dgCol title="手机号" field="mobilePhone" query="true"  width="150"  align="center" ></t:dgCol>
      <t:dgCol title="日期"  field="createDate" formatter="yyyy-MM-dd" query="true" queryMode="group"  sortable="true" width="150" align="center"></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" url="tBSmsController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>