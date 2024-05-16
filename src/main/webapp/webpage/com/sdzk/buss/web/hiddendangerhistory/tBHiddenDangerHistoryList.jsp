<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBHiddenDangerHistoryList" checkbox="false" pagination="true" fitColumns="false" title="历史记录" actionUrl="tBHiddenDangerHistoryController.do?datagrid&fkHiddenInfoId=${fkHiddenInfoId}" idField="id" fit="true" queryMode="group" sortName="dealTime" sortOrder="desc" onLoadSuccess="onLoadSuccess">
    <t:dgCol title="唯一标识"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="隐患基础信息表关联键"  field="fkHiddenInfoId"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="处理人"  field="dealUserName"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="处理时间"  field="dealTime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="处理环节"  field="dealStep"  hidden="true"   queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="说明"  field="dealDesc"    queryMode="group"  width="400" ></t:dgCol>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/hiddendangerhistory/tBHiddenDangerHistoryList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
function onLoadSuccess(){
    $("td[field='dealDesc']>div").css("display","block");
    $("td[field='dealDesc']>div").css("word-wrap","break-word");
    $("td[field='dealDesc']>div").css("white-space","normal");
}
 </script>