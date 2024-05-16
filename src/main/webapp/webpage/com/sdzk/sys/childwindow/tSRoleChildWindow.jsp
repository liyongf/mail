<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>角色-子窗口关联</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <style>
        td{
            height:35px;
            text-indent:5px;
        }
    </style>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="childWindowController.do?saveRoleChildWin">
    <input id="roleId" name="roleId" type="hidden" value="${roleId}">
    <table style="width: 800px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <th colspan="2" style="height: 32px;text-align: center;font-size: 1.2em;">
                窗口权限配置
            </th>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    左上：
                </label>
            </td>
            <td class="value" style="width:80%;">
                <c:forEach items="${aroundList}" var="around" >
                    <input type="radio" name="left_top" value="${around.id}" /> ${around.childWindowTitle}&emsp;
                </c:forEach>
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    中上：
                </label>
            </td>
            <td class="value">
                <c:forEach items="${centerList}" var="center" >
                    <input type="radio" name="center_top" value="${center.id}" /> ${center.childWindowTitle}&emsp;
                </c:forEach>
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    右上：
                </label>
            </td>
            <td class="value">
                <c:forEach items="${aroundList}" var="around" >
                    <input type="radio" name="right_top" value="${around.id}" /> ${around.childWindowTitle}&emsp;
                </c:forEach>
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    左下：
                </label>
            </td>
            <td class="value">
                <c:forEach items="${aroundList}" var="around" >
                    <input type="radio" name="left_bottom" value="${around.id}" /> ${around.childWindowTitle}&emsp;
                </c:forEach>
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    中下：
                </label>
            </td>
            <td class="value">
                <c:forEach items="${centerList}" var="center" >
                    <input type="radio" name="center_bottom" value="${center.id}" /> ${center.childWindowTitle}&emsp;
                </c:forEach>
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    右下：
                </label>
            </td>
            <td class="value">
                <c:forEach items="${aroundList}" var="around" >
                    <input type="radio" name="right_bottom" value="${around.id}" /> ${around.childWindowTitle}&emsp;
                </c:forEach>
                <span class="Validform_checktip"></span>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>

<script>
    $(document).ready(function(){
        $("input[name='left_top'][value='${left_top}']").attr("checked","checked");
        $("input[name='center_top'][value='${center_top}']").attr("checked","checked");
        $("input[name='right_top'][value='${right_top}']").attr("checked","checked");
        $("input[name='left_bottom'][value='${left_bottom}']").attr("checked","checked");
        $("input[name='center_bottom'][value='${center_bottom}']").attr("checked","checked");
        $("input[name='right_bottom'][value='${right_bottom}']").attr("checked","checked");
    });

    function getValueByElementName(name) {
        return $("input[name='"+name+"']:checked").val();
    }

    function getRoleId(){
        return "${roleId}";
    }
</script>