<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>首页子窗口</title>
    <t:base type="jquery,easyui,tools"></t:base>
    <style>
        .inputxt{
            border: 1px solid #D7D7D7;
            border-radius: 3PX;
            height: 14px;
            padding: 7px 0 7px 5px!important;
            line-height: 14PX;
            font-size: 12px;
            display: inline-block;
            width: 150px;
        }
    </style>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="childWindowController.do?save">
    <input id="id" name="id" type="hidden" value="${tSChildWindowsPage.id }">
    <input class="inputxt" id="isUsed" name="isUsed" type="hidden" value="${tSChildWindowsPage.isUsed}"/>
    <table style="width: 700px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    子窗口标题:
                </label>
            </td>
            <td class="value" style="width:70%">
                <input class="inputxt" id="childWindowTitle" name="childWindowTitle" ignore="ignore" style="width:80%"
                       value="${tSChildWindowsPage.childWindowTitle}"/>
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    子窗口框架ID:
                </label>
            </td>
            <td class="value">
                <input class="inputxt" id="childWindowFrameId" name="childWindowFrameId" ignore="ignore" style="width:80%"
                       value="${tSChildWindowsPage.childWindowFrameId}"/>
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    子窗口地址:
                </label>
            </td>
            <td class="value">
                <input class="inputxt" id="childWindowUrl" name="childWindowUrl" ignore="ignore" style="width:80%"
                       value="${tSChildWindowsPage.childWindowUrl}"/>
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    是否显示全屏按钮:
                </label>
            </td>
            <td class="value">
                <input name="isShowFullScreenBtn" type="radio" value="1" /> 是&nbsp;
                <input name="isShowFullScreenBtn" type="radio" value="0" /> 否
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    是否中间窗口:
                </label>
            </td>
            <td class="value">
                <input name="isCenter" type="radio" value="1" /> 是&nbsp;
                <input name="isCenter" type="radio" value="0" /> 否
                <span class="Validform_checktip"></span>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>
<script>
    $(document).ready(function(){
        if(${not empty tSChildWindowsPage.isCenter}){
            $("input[name='isCenter'][value='${tSChildWindowsPage.isCenter}']").attr("checked","checked");
        }else{
            $("input[name='isCenter'][value='0']").attr("checked","checked");
        }

        if(${not empty tSChildWindowsPage.isShowFullScreenBtn}){
            $("input[name='isShowFullScreenBtn'][value='${tSChildWindowsPage.isShowFullScreenBtn}']").attr("checked","checked");
        }else{
            $("input[name='isShowFullScreenBtn'][value='1']").attr("checked","checked");
        }
    });
</script>