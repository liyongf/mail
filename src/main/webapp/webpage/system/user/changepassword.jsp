<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户信息</title>
<t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" refresh="false" dialog="true" action="userController.do?savenewpwd" usePlugin="password" layout="table">
	<input id="id" type="hidden" name="id" value="${user.id }">
	<table style="width: 550px" cellpadding="0" cellspacing="1" class="formtable">
		<tbody>
			<tr>
				<td align="right" width="10%"><span class="filedzt">原密码:</span></td>
				<td class="value"><input type="password" value="" name="password" class="inputxt" datatype="*" errormsg="请输入原密码" /> <span class="Validform_checktip"> 请输入原密码 </span></td>
			</tr>
			<c:if test="${gucheng eq 'true'}">
				<tr>
					<td align="right"><span class="filedzt">新密码:</span></td>
					<td class="value"><input type="password" value="" name="newpassword" class="inputxt" datatype="/^(?![A-z0-9]+$)(?![A-z~@*()_]+$)(?![0-9~@*()_]+$)([A-z0-9~@*()_]{8,})$/" errormsg="密码至少8个字符,包含字母、数字、特殊符号！" /> <span
							class="Validform_checktip"> 密码至少8个字符,包含字母、数字、特殊符号！ </span> </td>
				</tr>
				<tr>
					<td align="right"><span class="filedzt">重复密码:</span></td>
					<td class="value"><input id="newpassword" type="password" recheck="newpassword" class="inputxt" datatype="/^(?![A-z0-9]+$)(?![A-z~@*()_]+$)(?![0-9~@*()_]+$)([A-z0-9~@*()_]{8,})$/" errormsg="两次输入的密码不一致！"> <span class="Validform_checktip"></span></td>
				</tr>
			</c:if>
			<c:if test="${gucheng ne 'true'}">
				<tr>
					<td align="right"><span class="filedzt">新密码:</span></td>
					<td class="value"><input type="password" value="" name="newpassword" class="inputxt" plugin="passwordStrength" datatype="/(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\W_]).{8,}/" errormsg="密码至少8个字符,包含大写字母、小写字母、数字、特殊符号！" /> <span
							class="Validform_checktip"> 密码需要：密码至少8个字符,包含大写字母、小写字母、数字、特殊符号！ </span> <span class="passwordStrength" style="display: none;"> <b>密码强度：</b> <span>弱</span><span>中</span><span class="last">强</span> </span></td>
				</tr>
				<tr>
					<td align="right"><span class="filedzt">重复密码:</span></td>
					<td class="value"><input id="newpassword" type="password" recheck="newpassword" class="inputxt" datatype="/(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[\W_]).{8,}/" errormsg="两次输入的密码不一致！"> <span class="Validform_checktip"></span></td>
				</tr>
			</c:if>
		</tbody>
	</table>
</t:formvalid>
</body>