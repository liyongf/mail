<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>t_b_investigate_plan</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
    <link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
    <script type="text/javascript">
    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="">
    <div style="margin-top: 10px;margin-bottom: 10px;">
        <table style="width: 100%;" cellpadding="3" cellspacing="1" class="formtable">
            <tr>
                <td align="right">风险点：</td>
                <td class="value">${tBAddressInfoPage.address}</td>
                <td align="right">分管领导：</td>
                <td class="value" colspan="3">${tBAddressInfoPage.manageMan}</td>
            </tr>
        </table>
    </div>
</t:formvalid>
<div>风险：${countRisk}&nbsp;&nbsp;&nbsp;&nbsp;隐患：${countHidden}&nbsp;&nbsp;&nbsp;&nbsp;</div>
<t:tabs id="tt" iframe="false" tabPosition="top" fit="true" width="100%" >
    <t:tab iframe="riskAlertManageController.do?addressDangerSourceList&addressId=${tBAddressInfoPage.id}" title="风险（${countRisk}）" heigth="475px" width="800px" id="a"></t:tab>
    <t:tab iframe="riskAlertManageController.do?addressHiddenDangerList&addressId=${tBAddressInfoPage.id}" title="隐患（${countHidden}）" heigth="475px" id="b"></t:tab>
    <%-- <t:tab iframe="riskAlertManageController.do?addressMajorHiddenList&addressId=${tBAddressInfoPage.id}" title="重大隐患（${countMajor}）" heigth="400px" id="c"></t:tab> --%>
    <%--<t:tab iframe="riskAlertManageController.do?addressViolationsList&addressId=${tBAddressInfoPage.id}" title="三违" heigth="450px" id="d"></t:tab>--%>
</t:tabs>
</body>