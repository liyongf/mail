<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%--<t:base type="jquery,easyui,tools,DatePicker"></t:base>--%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBRectProgressReportList" checkbox="false" pagination="true" fitColumns="true" title="" onLoadSuccess="rectLoadSuccess" actionUrl="tBRectProgressReportController.do?datagrid&fkHiddenInfoId=${fkHiddenInfoId}" idField="id" fit="true" queryMode="group" sortName="reportDate" sortOrder="desc">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="隐患基础信息表关联键"  field="fkHiddenInfoId" hidden="true"   queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="是否汇报完毕"  field="isComplete"  replace="是_1"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="汇报日期" query="true" field="reportDate" formatter="yyyy-MM-dd"   queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="汇报人"  field="reportPerson"  dictionary="t_s_base_user,id,realname,where1=1"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="整改进展汇报"  field="reportDesc"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人登录名"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人登陆名"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="更新时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
      <c:if test="${updateFlag eq 'false'}">
   <t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBRectProgressReportController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
   <t:dgToolBar title="录入" icon="icon-add" url="tBRectProgressReportController.do?goAdd&fkHiddenInfoId=${fkHiddenInfoId}" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBRectProgressReportController.do?goUpdate&fkHiddenInfoId=${fkHiddenInfoId}" funname="update"></t:dgToolBar>
      </c:if>
   <t:dgToolBar title="查看" icon="icon-search" url="tBRectProgressReportController.do?goUpdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/rectprogressreport/tBRectProgressReportList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 window.top["reload_tBRectProgressReportList"]=function(){
     $("#tBRectProgressReportList").datagrid( "load");
     if(typeof(this.msg)!='undefined' && this.msg!=null&&this.msg!=""){
         tip(this.msg);
     }
 };
 </script>