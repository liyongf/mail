<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>重大隐患验收</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
	 <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
	 <script src = "webpage/com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger.js"></script>
	 <script type="text/javascript">
  //编写自定义JS代码
	  $(function(){
		  var acceptorSelect = getUserMagicSuggestWithValue($("#acceptorSelect"), $("#acceptor"), "${tBMajorHiddenDangerPage.acceptor}", false);
	  })
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBMajorHiddenDangerController.do?doUpdate" callback="@Override noteSubmitCallback">
					<input id="id" name="id" type="hidden" value="${tBMajorHiddenDangerPage.id }">
			<input id="clStatus" name="clStatus" type="hidden">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>验收时间:
							</label>
						</td>
						<td class="value">
							<input id="accepTime" name="accepTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" datatype="*"
								   ignore="checked"
								   value='<fmt:formatDate value='${tBMajorHiddenDangerPage.accepTime}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">验收时间</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>验收人:
							</label>
						</td>
						<td class="value">
							<div id="acceptorSelect" style="width: 130px;height: 15px;"></div>
							<input id="acceptor" name="acceptor" type="hidden" style="width: 150px" class="inputxt"
								   datatype="*"
								   value='${tBMajorHiddenDangerPage.acceptor}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">验收人</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>验收情况:
							</label>
						</td>
						<td class="value">
							<textarea id="accepReport" name="accepReport" style="width: 80%;height: auto;" class="inputxt" rows="6" datatype="*">${tBMajorHiddenDangerPage.accepReport}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">验收情况</label>
						</td>
					</tr>
			</table>
			<div class="ui_main">
				<div class="ui_buttons">
					<input type="button" value="通过" onclick="saveNoteInfo_accept('1');" class="ui_state_highlight">
					<input type="button" value="驳回" onclick="saveNoteInfo_accept('0');">
				</div>
			</div>
		</t:formvalid>
 </body>
