<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="tBHiddenDangerHandleLogList" checkbox="true" pagination="true" fitColumns="true" title="隐患操作日志" actionUrl="tBHiddenDangerHandleController.do?logDatagrid&examId=${examId}" idField="id" fit="true" queryMode="group">
           <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
           <t:dgCol title="隐患id"  field="examId" hidden="true" queryMode="single"  width="120"></t:dgCol>
           <t:dgCol title="操作类型"  field="handleType" align="center" replace="录入_1,上报_2,整改_3,退回_4,复查_5,超期升级_6,督办_7"  width="120"></t:dgCol>
           <t:dgCol title="操作内容"  field="handleContent" align="center" queryMode="single"  width="360"></t:dgCol>
           <t:dgCol title="操作人ID" field="TSUser.userName" align="center" width="120"></t:dgCol>
           <t:dgCol title="操作人名" field="TSUser.realName" align="center" width="120"></t:dgCol>
           <t:dgCol title="操作日期" field="operatetime" align="center" query="true" queryMode="group" formatter="yyyy-MM-dd" width="200"></t:dgCol>
        </t:datagrid>
    </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
 });
 </script>