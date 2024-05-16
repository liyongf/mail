<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>关联用户</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
	 <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
	 <script type="text/javascript">
  //编写自定义JS代码
  var magicsuggestDepartSelSelected = "";
  var magicsuggestUserSelected="";
  $(function() {
      magicsuggestDepartSelSelected = $('#magicsuggestdepartSel').magicSuggest({
          allowFreeEntries: false,
          data: 'magicSelectController.do?departSelectDataGridMagic',
          valueField: 'id',
          placeholder: '输入或选择',
          maxSelection: 1,
          selectFirst: true,
          highlight: false,
          displayField: 'departName'
      });
      $(magicsuggestDepartSelSelected).on('selectionchange', function (c) {
          $("#depart").val(magicsuggestDepartSelSelected.getValue());
      });

      magicsuggestUserSelected = getUserMagicSuggestWithValue($('#magicsuggestUser'), $("#user"), "", false);

      $(magicsuggestUserSelected).on('focus', function (c) {
          var deptId = $('#depart').val();
          magicsuggestUserSelected.setData("magicSelectController.do?getUserList&orgIds=" + deptId);
      });
  })
  function saveNoteInfo(state) {

      $('#btn_sub').click();
  }
  function noteSubmitCallback(data) {
      var win = frameElement.api.opener;
      win.tip(data.msg);
      frameElement.api.close();
      win.tBWexinInfoListsearch();
  }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="WeixinChat.do?doAdd" callback="@Override noteSubmitCallback">
	  <input id="id" name="id" type="hidden" value="${WexinOpenId.id }">
	  <input id="wexinStatus" name="wexinStatus" type="hidden" value="1">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>昵称:
						</label>
					</td>
					<td class="value"  style="display:none" colspan="3">
						<input id="nickname" name="nickname" type="text" style="width: 150px" class="inputxt" value="${WexinOpenId.nickname}" />
						<span class="Validform_checktip"></span>
					</td>
					<td class="value">
							${WexinOpenId.nickname}
					</td>

				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>OPENID:
						</label>
					</td>
					<td class="value" style="display:none"  colspan="3">
							<div id="leaderSelect" style="width: 130px;height: 15px"></div>
					     	 <input id="openId" name="openId"  style="width: 150px" class="inputxt" value="${WexinOpenId.openId}" />
							<span class="Validform_checktip"></span>
					</td>
					<td class="value">
							${WexinOpenId.openId}
					</td>
				</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<font color="red">*</font>用户单位:
					</label>
				</td>
				<td class="value"  colspan="3">
					<div id="magicsuggestdepartSel" style="width: 130px;height: 15px"></div>
					<input id="depart" name="depart" type="hidden" style="width: 150px" class="inputxt" datatype="*">
					<span class="Validform_checktip">请关联用户单位</span>
					<label class="Validform_label" style="display: none;">用户单位</label>
				</td>
			</tr>
				<tr>
				<td align="right">
					<label class="Validform_label">
						<font color="red">*</font>关联用户:
					</label>
				</td>
				<td class="value" colspan="3">
					<div id="magicsuggestUser" style="width: 130px;height: 15px"></div>
					<input type="hidden" name="user" id="user" value="" class="inputxt" datatype="*">
					<span class="Validform_checktip">请关联用户</span>
					<label class="Validform_label" style="display: none;">关联用户</label>
				</td>
			</tr>
			</table>
	  <div class="ui_main">
		  <div class="ui_buttons">
			  <input type="button" value="提交" onclick="saveNoteInfo();" class="ui_state_highlight">
		  </div>
	  </div>
		</t:formvalid>
 </body>
 <script type="text/javascript">





 </script>