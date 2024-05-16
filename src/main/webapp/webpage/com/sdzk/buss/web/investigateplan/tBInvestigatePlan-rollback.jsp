<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html style="width: 90%;">
<head>
	<title>t_b_investigate_plan</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
</head>
<body>
	<table style="width: 350px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr style="height: 30px;">
			<td align="left">
				<label class="Validform_label">
					驳回原因：
				</label>
			</td>
		</tr>
		<tr>
			<td class="value">
				<textarea id="rollBackReason" name="rollBackReason" style="width: 350px;" placeholder="请输入驳回原因" class="inputxt" rows="5"></textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">驳回原因</label>
			</td>
		</tr>
	</table>
</body>
