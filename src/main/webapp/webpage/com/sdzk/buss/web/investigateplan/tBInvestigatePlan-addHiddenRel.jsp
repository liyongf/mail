<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html style="width: 90%;">
<head>
	<title>t_b_investigate_plan</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
	<script>
		function choose_hiddenDanger(title){
			if (typeof(windowapi) == 'undefined') {
				$.dialog({content: 'url:tBHiddenDangerHandleController.do?chooseHiddenDanger&planId=${planId}', zIndex: 2100, title: ''+title+'', lock: true, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
					{name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_hiddenDanger, focus: true},
					{name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
					}}
				]});
			} else {
				$.dialog({content: 'url:tBHiddenDangerHandleController.do?chooseHiddenDanger&planId=${planId}', zIndex: 2100, title: ''+title+'', lock: true, parent: windowapi, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
					{name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_hiddenDanger, focus: true},
					{name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
					}}
				]});
			}
		}

		function clickcallback_hiddenDanger(){
			iframe = this.iframe.contentWindow;
			var problemDesc = iframe.gettBHiddenDangerHandleListSelections('hiddenDanger.problemDesc');
			var str = "";
			for(var i=0;i<problemDesc.length;i++){
				str += (i+1) +"." + problemDesc[i];
				if(i+1 != problemDesc.length){
					str += "\r\n";
				}
			}
			$("#hiddenName").text(str);
			$("#hiddenName").blur();
			var id = iframe.gettBHiddenDangerHandleListSelections('id');
			$("input[name='hiddenId']").val(id);
		}
	</script>
</head>
<body>
	<table style="width: 350px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr style="height: 30px;">
			<td align="left">
				<label class="Validform_label" style="font-size: 14px;">
					新增关联隐患列表：
				</label>
			</td>
		</tr>
		<tr>
			<td class="value">
				<textarea id="hiddenName" name="hiddenName" style="width: 350px;" placeholder="点此选择隐患" class="inputxt" rows="10" onclick="choose_hiddenDanger('选择隐患');" readonly="true"></textarea>
				<input id="hiddenId" name="hiddenId" type="hidden" />
			</td>
		</tr>
	</table>
</body>
