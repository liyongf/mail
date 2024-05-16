<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户信息</title>
<t:base type="jquery,easyui,tools"></t:base>
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="userController.do?updateCurrentUser" tiptype="3" btnsub="btn_submit" callback="@Override callback">
	<input id="id" name="id" type="hidden" value="${user.id }">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="25%" nowrap>
                <label class="Validform_label">  <t:mutiLang langKey="common.username"/>:  </label>
            </td>
			<td class="value" width="85%">
                    ${user.userName }
            </td>
		</tr>
		<tr>
			<td align="right" width="10%" nowrap><label class="Validform_label"> <t:mutiLang langKey="common.real.name"/>: </label></td>
			<td class="value" width="10%">
                    ${user.realName }
            </td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.department"/>: </label></td>
			<td class="value">
                ${departname }
            </td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.role"/>: </label></td>
			<td class="value" nowrap>
                    ${roleName }
            </td>
		</tr>
        <tr>
            <td align="right"><label class="Validform_label"> 工种: </label></td>
            <td class="value" nowrap>
                <input id="postId" name="postId" type="hidden" value="${postId}"/>
                <input name="postName" id="postName" class="inputxt" value="${postName }" readonly="readonly"/>
                <t:choose hiddenName="postId" hiddenid="id" textname="postName" url="userController.do?posts" name="tBPostManageList" icon="icon-search" title="工种列表" isclear="true" isInit="true"></t:choose>
                <span class="Validform_checktip">工种可多选</span>
            </td>
        </tr>
		<tr>
			<td align="right" nowrap><label class="Validform_label">  <t:mutiLang langKey="common.phone"/>: </label></td>
			<td class="value">
                <input class="inputxt" id="mobilePhone" name="mobilePhone" value="${user.mobilePhone}" datatype="m" errormsg="手机号码不正确" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.tel"/>: </label></td>
			<td class="value">
                <input class="inputxt" id="officePhone" name="officePhone" value="${user.officePhone}" datatype="n" errormsg="办公室电话不正确" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.common.mail"/>: </label></td>
			<td class="value">
                <input class="inputxt" id="email" name="email" value="${user.email}" datatype="e" errormsg="邮箱格式不正确!" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
        <tr class="luru" height="40">
            <td class="upload" colspan="2" style="text-align: right;" align="right">
                <div class="ui_buttons" style="text-align: center;">
                    <input type="button" id="btn_submit" value="保存" class="ui_state_highlight">
                </div>
            </td>
        </tr>
	</table>
</t:formvalid>
</body>
<script>
    function callback(data){
        if (data.success) {
            tip(data.msg);
        } else {
            tip("用户信息更新失败!");
        }
        $("#Validform_msg").hide();
    }
</script>