<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>分值配置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  //保存回调
  function saveCallback(data) {

      var win = frameElement.api.opener;


      if (data.success == true){
          win.tip(data.msg);
          frameElement.api.close();
          win.ruleScoreListsearch();
      }else{
          tip(data.msg);
      }
  }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskRuleScoreManagerController.do?doAdd" callback="@Override saveCallback">
		<input id="id" name="id" type="hidden" value="${tBAddressInfoPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>隐患等级:
					</label>
				</td>
				<td class="value">
					<t:dictSelect field="riskType" type="list" extendJson="{\"datatype\":\"*\"}"
								  typeGroupCode="hiddenLevel"  hasLabel="false"  title="隐患等级"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">隐患等级</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>分值:
					</label>
				</td>
				<td class="value">
					<input id="score" name="score" type="text" style="width: 150px" class="inputxt" datatype="d">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">分值</label>
				</td>
			</tr>
			</table>
		</t:formvalid>
 </body>
