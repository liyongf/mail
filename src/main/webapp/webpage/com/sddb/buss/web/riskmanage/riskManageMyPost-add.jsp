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

      $(function() {
          getPostMagicSuggest($("#postSelect"), $("input[name='postId']"));
      });
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskManageController.do?doAddMyPost">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>岗位:
					</label>
				</td>
				<td class="value">
					<div id="postSelect" style="width: 300px;height: 15px"></div>
					<input id="postId" name="postId" type="hidden" style="width: 150px" class="inputxt" datatype="*" >
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">岗位</label>
				</td>
			</tr>
			</table>
		</t:formvalid>
 </body>
