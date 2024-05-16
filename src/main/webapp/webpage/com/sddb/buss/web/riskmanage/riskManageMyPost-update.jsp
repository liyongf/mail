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
        var magicsuggestPostSelSelected = "";
        $(function() {
            magicsuggestPostSelSelected = $('#magicsuggestPost').magicSuggest({
                data: 'magicSelectController.do?getPostList',
                allowFreeEntries: false,
                value:["${riskManagePostRelPage.post.id}"],
                valueField: 'id',
                placeholder: '输入或选择',
                maxSelection: 1,
                selectFirst: true,
                highlight: false,
                displayField: 'post_name'
            });
            $(magicsuggestPostSelSelected).on('selectionchange', function (e, m) {
                $("#postId").val(magicsuggestPostSelSelected.getValue());
            });
        });
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskManageController.do?doUpdateMyPost">

	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<input id="id" name="id" type="hidden"   value="${riskManagePostRelPage.id}">
		<input id="postOldId" name="postOldId" type="hidden"   value="${riskManagePostRelPage.post.id}">
		<tr>
			<td align="right">
				<label class="Validform_label">
					<b style="color: red">*</b>岗位:
				</label>
			</td>
			<td class="value">
				<div id="magicsuggestPost" style="width: 300px;height: 15px"></div>
				<input id="postId" name="postId" type="hidden" style="width: 150px" class="inputxt" datatype="*" value="${riskManagePostRelPage.post.id}">
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">岗位</label>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>
