<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>专业</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
	<script type="text/javascript">
        var magicsuggestMajorSelSelected = "";
        $(function() {
            magicsuggestMajorSelSelected = $('#magicsuggestMajor').magicSuggest({
                data: 'magicSelectController.do?getMajorList&manageType=${manageType}',
                allowFreeEntries: false,
                value:["${riskManageMajorRelPage.majorId}"],
                valueField: 'id',
                placeholder: '输入或选择',
                maxSelection: 1,
                selectFirst: true,
                highlight: false,
                displayField: 'major_name'
            });
            $(magicsuggestMajorSelSelected).on('selectionchange', function (e, m) {
                $("#majorId").val(magicsuggestMajorSelSelected.getValue());
            });
        });
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskManageController.do?doUpdateMyMajor">

	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<input id="id" name="id" type="hidden"   value="${riskManageMajorRelPage.id}">
		<input id="majorOldId" name="majorOldId" type="hidden"   value="${riskManageMajorRelPage.majorId}">
		<tr>
			<td align="right">
				<label class="Validform_label">
					<b style="color: red">*</b>${riskManager}:
				</label>
			</td>
			<td class="value">
				<div id="magicsuggestMajor" style="width: 300px;height: 15px"></div>
				<input id="majorId" name="majorId" type="hidden" style="width: 150px" class="inputxt" datatype="*" value="${riskManageMajorRelPage.majorId}">
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">${riskManager}</label>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>
