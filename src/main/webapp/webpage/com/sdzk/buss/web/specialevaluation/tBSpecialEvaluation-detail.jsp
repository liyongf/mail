<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>t_b_special_evaluation</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
	 <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
	 <script src = "webpage/com/sdzk/buss/web/specialevaluation/tBSpecialEvaluation.js"></script>
	 <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
	  getUserMagicSuggestWithValue($("#leaderSelect"), $("#leader"), '${tBSpecialEvaluationPage.leader}', true);
  });
  </script>

 </head>
 <body>
 <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBSpecialEvaluationController.do?doUpdate" >
	 <input id="id" name="id" type="hidden" value="${tBSpecialEvaluationPage.id }">
	 <input type="hidden" id="type" name="type" value="${tBSpecialEvaluationPage.type}" />
	 <input type="hidden" id="reportStatus" name="reportStatus" value="0" />
	 <table style="width: 100%; margin-top: 50px;" cellpadding="0" cellspacing="1" class="formtable">
         <tr>
             <td colspan="4" align="center" style="height: 50px;">
                 <b style="font-size: 14px;">专项风险辨识</b>
             </td>
         </tr>
		 <tr>
			 <td align="right" style="width: 20%;">
				 <label class="Validform_label">
					 专项辨识名称:
				 </label>
			 </td>
			 <td class="value"  style="width: 30%;">
			 ${tBSpecialEvaluationPage.name}
			 </td>
			 <td align="right" style="width: 20%;">
				 <label class="Validform_label">
					 辨识人类型:
				 </label>
			 </td>
			 <td class="value" style="width: 30%;">
					 ${leaderType}
			 </td>
		 </tr>
		 <tr>
			 <td align="right">
				 <label class="Validform_label">
					 辨识负责人:
				 </label>
			 </td>
			 <td class="value">
					 ${leader}
			 </td>
			 <td align="right">
				 <label class="Validform_label">
					 辨识时间:
				 </label>
			 </td>
			 <td class="value">
				 <fmt:formatDate value='${tBSpecialEvaluationPage.time}' type="date" pattern="yyyy-MM-dd"/>
			 </td>
		 </tr>
		 <tr>
			 <td align="right">
				 <label class="Validform_label">
					 辨识参加人员:
				 </label>
			 </td>
			 <td class="value" colspan="3">
					 ${participant}
			 </td>
		 </tr>
		 <tr>
			 <td align="right">
				 <label class="Validform_label">
					 辨识地点:
				 </label>
			 </td>
			 <td class="value" colspan="3">
			 ${location}
			 </td>
		 </tr>
		 <tr>
			 <td align="right">
				 <label class="Validform_label">
					 辨识结论:
				 </label>
			 </td>
			 <td class="value" colspan="3">
			 ${tBSpecialEvaluationPage.remark}
			 </td>
		 </tr>
	 </table>
 </t:formvalid>
 </body>
