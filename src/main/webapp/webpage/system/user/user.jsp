<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户信息</title>
<t:base type="jquery,easyui,tools"></t:base>
    <script>
 //        update-start--Author:zhangguoming  Date:20140826 for：将combobox修改为combotree
        function setOrgIds() {
            var orgIds = $("#orgSelect").combotree("getValues");
            $("#orgIds").val(orgIds);
        }
        $(function() {
            $("#orgSelect").combotree({
                onChange: function(n, o) {
                    if($("#orgSelect").combotree("getValues") != "") {
                        $("#orgSelect option").eq(1).attr("selected", true);
                    } else {
                        $("#orgSelect option").eq(1).attr("selected", false);
                    }
                }
            });
            $("#orgSelect").combotree("setValues", ${orgIdList});
        });




		$(function(){
			$("#departname").prev().hide();
		});
    </script>
</head>
<body  scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="userController.do?saveUser" beforeSubmit="setOrgIds">
	<input id="id" name="id" type="hidden" value="${user.id }">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="25%" nowrap>
                <label class="Validform_label">  <t:mutiLang langKey="common.username"/>:  </label>
            </td>
			<td class="value" width="85%">
                <c:if test="${user.id!=null }"> ${user.userName } </c:if>
                <c:if test="${user.id==null }">
                    <input id="userName" class="inputxt" name="userName" validType="t_s_base_user,userName,id" value="${user.userName }" datatype="s2-50" />
                    <span class="Validform_checktip"> 请填写2到50位字符！</span>
                </c:if>
            </td>
		</tr>
		<tr>
			<td align="right" width="10%" nowrap><label class="Validform_label"> <t:mutiLang langKey="common.real.name"/>: </label></td>
			<td class="value" width="10%">
                <input id="realName" class="inputxt" name="realName" value="${user.realName }" datatype="s2-10">
                <span class="Validform_checktip"><t:mutiLang langKey="fill.realname"/></span>
            </td>
		</tr>
		<c:if test="${user.id==null }">
            <c:if test="${gucheng eq 'true'}">
                <tr>
                    <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.password"/>: </label></td>
                    <td class="value">
                        <input type="password" class="inputxt" value="" name="password"  datatype="/^(?![A-z0-9]+$)(?![A-z~@*()_]+$)(?![0-9~@*()_]+$)([A-z0-9~@*()_]{8,})$/" errormsg="密码至少8个字符,包含字母、数字、特殊符号！" />
                        <span class="Validform_checktip">密码至少8个字符,包含字母、数字、特殊符号！</span>
                    </td>
                </tr>
                <tr>
                    <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.repeat.password"/>: </label></td>
                    <td class="value">
                        <input id="repassword" class="inputxt" type="password" value="${user.password}" recheck="password" datatype="/^(?![A-z0-9]+$)(?![A-z~@*()_]+$)(?![0-9~@*()_]+$)([A-z0-9~@*()_]{8,})$/" errormsg="两次输入的密码不一致！">
                        <span class="Validform_checktip"><t:mutiLang langKey="请重复密码"/></span>
                    </td>
                </tr>
            </c:if>
            <c:if test="${gucheng ne 'true'}">
                <tr>
                    <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.password"/>: </label></td>
                    <td class="value">
                        <input type="password" class="inputxt" value="" name="password" plugin="passwordStrength" datatype="*" errormsg="" />
                        <span class="passwordStrength" style="display: none;">
                        <span><t:mutiLang langKey="common.weak"/></span>
                        <span><t:mutiLang langKey="common.middle"/></span>
                        <span class="last"><t:mutiLang langKey="common.strong"/></span>
                    </span>
                        <span class="Validform_checktip">请填写密码</span>
                    </td>
                </tr>
                <tr>
                    <td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.repeat.password"/>: </label></td>
                    <td class="value">
                        <input id="repassword" class="inputxt" type="password" value="${user.password}" recheck="password" datatype="*" errormsg="两次输入的密码不一致！">
                        <span class="Validform_checktip"><t:mutiLang langKey="请重复密码"/></span>
                    </td>
                </tr>
            </c:if>
		</c:if>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.department"/>: </label></td>
			<td class="value">
                <select class="easyui-combotree" data-options="url:'departController.do?getOrgTree', multiple:false, cascadeCheck:false"
                        id="orgSelect" name="orgSelect" datatype="select1">
                    <c:forEach items="${departList}" var="depart">
                        <option value="${depart.id }">${depart.departname}</option>
                    </c:forEach>
                </select>

                
               <%-- <input id="departname" name="departname" type="text" readonly="readonly" class="inputxt" datatype="*" value="${departname}">--%>
                <input id="orgIds" name="orgIds" type="hidden" value="${orgIds}">
               <%-- <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" id="departSearch" onclick="openDepartmentSelect()">选择</a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo" id="departRedo" onclick="callbackClean()">清空</a>--%>
                <span class="Validform_checktip">选择部门</span>
            </td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.role"/>: </label></td>
			<td class="value" nowrap>
                <input id="roleid" name="roleid" type="hidden" value="${id}"/>
                <input name="roleName" id="roleName" class="inputxt" value="${roleName }" readonly="readonly" datatype="*" />
                <t:choose hiddenName="roleid" hiddenid="id" textname="roleName" url="userController.do?roles" name="roleList" icon="icon-search" title="common.role.list" isclear="true" isInit="true"></t:choose>
                <span class="Validform_checktip"><t:mutiLang langKey="role.muti.select"/></span>
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
                <input class="inputxt" name="mobilePhone" value="${user.mobilePhone}" datatype="m" errormsg="手机号码不正确" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.tel"/>: </label></td>
			<td class="value">
                <input class="inputxt" name="officePhone" value="${user.officePhone}" datatype="n" errormsg="办公室电话不正确" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.common.mail"/>: </label></td>
			<td class="value">
                <input class="inputxt" name="email" value="${user.email}" datatype="e" errormsg="邮箱格式不正确!" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
        <tr>
            <td align="right"><label class="Validform_label">企业微信账号</label></td>
            <td class="value">
                <input class="inputxt" name="weChatPhone" value="${user.weChatPhone}" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
        </tr>
	</table>
</t:formvalid>
</body>