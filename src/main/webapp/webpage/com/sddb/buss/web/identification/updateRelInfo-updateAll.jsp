<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>危害因素</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  $(document).ready(function(){
      var vioPeopleSelect = getUserMagicSuggest($('#vioPeopleSelect'), $("input[name='manageUser.id']"));
      var vioUnitsSelect = getDepartMagicSuggest($("#vioUnitsSelect"), $("input[name='manageDepart.id']"));
  });

  function refreshFactortsList(data){
	  window.top.reload_whys.call(data);
      frameElement.api.close();
  }

  function saveInfo() {
      $('#btn_sub').click();
  }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskIdentificationController.do?doAllUpdateRelInfo" callback="@Override refreshFactortsList">
		<input id="ids" name="ids" type="hidden" value="${ids}"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>管控单位:
					</label>
				</td>
				<td class="value">
					<div id="vioUnitsSelect" style="width: 130px;height: 15px"></div>
					<input id="manageDepart.id" name="manageDepart.id" type="hidden" datatype="*">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">管控单位</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>管控责任人:
					</label>
				</td>
				<td class="value">
					<div id="vioPeopleSelect" style="width: 130px;height: 15px"></div>
					<input id="manageUser.id" name="manageUser.id" type="hidden" datatype="*">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">管控责任人</label>
				</td>
			</tr>
			</table>
	  <div class="ui_main">
		  <div class="ui_buttons">
			  <input type="button" value="确定" onclick="saveInfo();" class="ui_state_highlight">
		  </div>
	  </div>
		</t:formvalid>
 </body>