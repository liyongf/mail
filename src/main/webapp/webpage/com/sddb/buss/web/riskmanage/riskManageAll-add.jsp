<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>管控任务</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
		function showWait() {
            $.messager.progress({
                text : "正在保存风险清单......"
            });
        }
        function hideWait() {
            $.messager.progress('close');
            window.top.reload_riskManageTaskAllList.call();
            frameElement.api.opener.reloadTable();
            frameElement.api.close();
        }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskManageTaskController.do?doAdd" beforeSubmit="showWait" callback="@Override hideWait">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>管控时间:
					</label>
				</td>
				<td class="value">
					<input id="manageTime" name="manageTime" type="text" style="width: 150px" datatype="*"  class="Wdate" onClick="WdatePicker()"
						   value='<fmt:formatDate value='<%=new Date()%>' type="date" pattern="yyyy-MM-dd"/>'>
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
								  typeGroupCode="workShift" hasLabel="false"  title="管控班次"></t:dictSelect>
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
					<textarea id="remark" name="remark" type="text" style="width: 440px;" ></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>
			</table>
	  <input id="manageType" name="manageType" type="hidden" style="width: 150px" datatype="*" value="${manageType}">

		</t:formvalid>
 </body>
