<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>危害因素</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskManageTaskController.do?doUpdatePost" >
		<input id="id" name="id" type="hidden" value="${riskManagePostTaskAllPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>管控时间:
					</label>
				</td>
				<td class="value">
					<input id="manageTime" name="manageTime" type="text" style="width: 150px" datatype="*"  class="Wdate" onClick="WdatePicker()"
						   value='<fmt:formatDate value='${riskManagePostTaskAllPage.manageTime}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">管控时间</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<font color="red">*</font>管控班次:
					</label>
				</td>
				<td class="value">
					<t:dictSelect field="manageShift" type="manageShift" extendJson="{\"datatype\":\"*\"}"
								  typeGroupCode="workShift" hasLabel="false" defaultVal="${riskManagePostTaskAllPage.manageShift}"  title="管控班次"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">管控班次</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						备注:
					</label>
				</td>
				<td class="value">
					<textarea id="remark" name="remark" type="text" style="width: 440px;" >${riskManagePostTaskAllPage.remark}</textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>
			</table>
		</t:formvalid>
 </body>
