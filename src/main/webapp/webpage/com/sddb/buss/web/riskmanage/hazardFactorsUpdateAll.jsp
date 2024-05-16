<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>危害因素</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
  <script type="text/javascript">

  //编写自定义JS代码
	  $(function(){
		  $(":input[name='riskType']").attr("disabled","true");
		  $(":input[name='major']").attr("disabled","true");
		  $(":input[name='riskLevel']").attr("disabled","true");
		  $(":input[name='hazardFactors']").attr("disabled","true");
		  $(":input[name='manageMeasure']").attr("disabled","true");
	  });
  function save() {
      $("#btn").trigger("click");

  }
  function refresh(){
      window.top.reload_riskManageResultList.call();
	  frameElement.api.opener.reloadTable();
	  frameElement.api.close();
  }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskManageResultController.do?doAllUpdate" btnsub="btn" callback="@Override refresh">
		<input id="ids" name="ids" type="hidden" value="${ids }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>管控措施落实情况:
					</label>
				</td>
				<td class="value">
					<textarea  id="implDetail" name="implDetail" cols="6" rows="5" style="width: 80%" datatype="*">已落实</textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">管控措施落实情况</label>
				</td>
			</tr>

			<%--<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>危害因素管控是否落实:
					</label>
				</td>
				<td  class="Validform_label" colspan="4">
					<label><input type="radio" value="1" name="handleStatus"   checked="checked"   />落实</label>
					<label><input type="radio" value="0" name="handleStatus"/>未落实</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						备注:
					</label>
				</td>
				<td class="value">
					<textarea  id="remark" name="remark" cols="6" rows="5" style="width: 80%" ></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>--%>
			<tr>
				<td class="value" colspan="4">
					<div class="ui_buttons" style="text-align: right;">
						<input type="button" id="btn" value="保存" class="ui_state_highlight" style="display:none">
						<input type="button" onclick="save();" class="ui_state_highlight" value="确定">
					</div>
				</td>
			</tr>
		</table>
		</t:formvalid>
 </body>
