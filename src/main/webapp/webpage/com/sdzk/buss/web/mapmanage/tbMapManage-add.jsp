<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/12/26 0026
  Time: 上午 9:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>文件列表</title>
    <t:base type="jquery,easyui,tools"></t:base>
    <style type="text/css">
        #documentTitle{
            width: 200px;
            height: 24px;
            margin-left: -30px;
            margin-top: 4px;
        }
    </style>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="upload">
    <fieldset class="step">
        <div class="form">
            <t:upload name="fiels" buttonText="选择文件..." uploader="tbMapManageController.do;jsessionid=${pageContext.session.id}?doUpload" extend="*.zip" id="file_upload"></t:upload>
        </div>
        <div class="form" id="filediv" style="height: 50px"></div>
    </fieldset>
</t:formvalid>
</body>
</html>
