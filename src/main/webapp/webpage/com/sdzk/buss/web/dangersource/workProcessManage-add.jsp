<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title><t:mutiLang langKey="common.role.info"/></title>
    <t:base type="jquery,easyui,tools"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="tBWorkProcessManageController.do?doAdd">
    <fieldset class="step">
        <div class="form">
            <label class="Validform_label">
                <font color="red">*</font>作业过程名称:
            </label>
            <input name="name" class="inputxt" datatype="*">
            <span class="Validform_checktip"></span>
        </div>
        <div class="form">
            <label class="Validform_label">
                <font color="red">*</font>专业:
            </label>
            <t:dictSelect id="yeProfession" field="major" typeGroupCode="proCate_gradeControl" hasLabel="false" defaultVal="" datatype="*"></t:dictSelect>
            <span class="Validform_checktip"></span>
        </div>
    </fieldset>
</t:formvalid>
</body>
</html>


