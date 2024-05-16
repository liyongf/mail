<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 17-8-21
  Time: 下午1:31
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
            <input type="hidden" id="jsessionid" value="${pageContext.session.id}" />
            <label class="Validform_label"> <font color="red">*</font>文件标题: </label>
            <input name="documentTitle" id="documentTitle" datatype="*3-50">
            <span class="Validform_checktip">标题名称在3~50位字符,且不为空</span>
        </div>
        <div class="form">
            <t:upload name="fiels" buttonText="上传文件"  uploader="systemController.do;jsessionid=${pageContext.session.id}?saveFiles&docSourceId=${docSourceId}&typecode=${typecode}" extend="*.doc;*.docx;*.wps;*.txt;*.ppt;*.xls;*.xlsx;*.html;*.htm;*.pdf;*.png;*.jpg;*.PNG;*.JPG;*.jpeg" id="file_upload" formData="documentTitle"></t:upload>
        </div>
        <div class="form" id="filediv" style="height: 50px"></div>
    </fieldset>
</t:formvalid>
</body>
</html>