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
</t:formvalid>
<t:tabs id="tt" iframe="false" tabPosition="top" fit="true" width="100%" >
    <t:tab iframe="riskIdentificationController.do?goCloneZX&riskTaskId=${riskTaskId}&identificationType=${identificationType}&toCloneAddressId=${toCloneAddressId}&riskManageTaskId=${riskManageTaskId}&fl=zx" title="专项" heigth="300px" id="d"></t:tab>
    <t:tab iframe="riskIdentificationController.do?goCloneZX&riskTaskId=${riskTaskId}&identificationType=${identificationType}&toCloneAddressId=${toCloneAddressId}&riskManageTaskId=${riskManageTaskId}&fl=nd" title="年度" heigth="330px" id="d"></t:tab>
</t:tabs>
</body>
