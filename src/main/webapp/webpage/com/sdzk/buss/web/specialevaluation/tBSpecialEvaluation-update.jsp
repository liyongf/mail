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
	  getUserMagicSuggestWithValue($("#leaderSelect"), $("#leader"), '${tBSpecialEvaluationPage.leader}', false);
	  getAddressMagicSuggestWithValue($("#locationSelect"), $("#location"), '${tBSpecialEvaluationPage.location}', false);
  });
  </script>
 </head>
 <body>
 <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBSpecialEvaluationController.do?doUpdate" >
	 <input id="id" name="id" type="hidden" value="${tBSpecialEvaluationPage.id }">
	 <input type="hidden" id="type" name="type" value="${tBSpecialEvaluationPage.type}" />
	 <input type="hidden" id="reportStatus" name="reportStatus" value="0" />
	 <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
         <tr>
             <td colspan="4" align="center" style="height: 50px;">
                 <b style="font-size: 14px;">专项风险辨识</b>
             </td>
         </tr>
		 <tr>
			 <td align="right">
				 <label class="Validform_label">
					 <b style="color: red">*</b>专项辨识名称:
				 </label>
			 </td>
			 <td class="value">
				 <input id="name" name="name" type="text" style="width: 150px" class="inputxt" value='${tBSpecialEvaluationPage.name}'
						datatype="*"
				 />
				 <span class="Validform_checktip"></span>
				 <label class="Validform_label" style="display: none;">专项辨识名称</label>
			 </td>
			 <td align="right">
				 <label class="Validform_label">
					 <b style="color: red">*</b>辨识人类型:
				 </label>
			 </td>
			 <td class="value">
				 <t:dictSelect field="leaderType" typeGroupCode="evaluationLeaderType"  hasLabel="false" defaultVal="${tBSpecialEvaluationPage.leaderType}" datatype="*"/>
				 <span class="Validform_checktip"></span>
				 <label class="Validform_label" style="display: none;">辨识人类型</label>
			 </td>
		 </tr>
		 <tr>
			 <td align="right">
				 <label class="Validform_label">
					 <b style="color: red">*</b>辨识负责人:
				 </label>
			 </td>
			 <td class="value">
				 <div id="leaderSelect" style="width: 130px;height: 15px"></div>
				 <input id="leader" name="leader" type="hidden" style="width: 150px" class="inputxt"
						datatype="*"
				 />
				 <span class="Validform_checktip"></span>
				 <label class="Validform_label" style="display: none;">辨识负责人</label>
			 </td>
			 <td align="right">
				 <label class="Validform_label">
					 <b style="color: red">*</b>辨识时间:
				 </label>
			 </td>
			 <td class="value">
				 <input id="time" name="time" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value="<fmt:formatDate value='${tBSpecialEvaluationPage.time}' type="date" pattern="yyyy-MM-dd"/>"
						datatype="*"
				 />
				 <span class="Validform_checktip"></span>
				 <label class="Validform_label" style="display: none;">辨识时间</label>
			 </td>
		 </tr>
		 <tr>
			 <td align="right">
				 <label class="Validform_label">
					 <b style="color: red">*</b>辨识参加人员:
				 </label>
			 </td>
			 <td class="value" colspan="3">
				 <input id="teamMembers" name="teamMembers" style="width: 440px;" datatype="*" type="text" value='${tBSpecialEvaluationPage.participantName}'
						onclick="choose_participant('选择辨识参加人员')"/>
				 <input id="participant" name="participant" type="hidden" style="width: 150px" class="inputxt"
						datatype="*" value='${tBSpecialEvaluationPage.participant}'
				 />
				 <a id="" class="easyui-linkbutton l-btn l-btn-plain" onclick="clearTeamMembers();" icon="icon-redo" plain="true"
					href="#">
					 <span>清空</span>
				 </a>
				 <span class="Validform_checktip"></span>
				 <label class="Validform_label" style="display: none;">辨识参加人员</label>
			 </td>
		 </tr>
		 <tr>
			 <td align="right">
				 <label class="Validform_label">
					 <b style="color: red">*</b>辨识地点:
				 </label>
			 </td>
			 <td class="value" colspan="3">
				 <div id="locationSelect" style="width: 130px;height: 15px"></div>
				 <input id="location" name="location" type="hidden" style="width: 150px" class="inputxt" value='${tBSpecialEvaluationPage.location}'
						datatype="*"
				 />
				 <span class="Validform_checktip"></span>
				 <label class="Validform_label" style="display: none;">辨识地点</label>
			 </td>
		 </tr>
		 <tr>
			 <td align="right">
				 <label class="Validform_label">
					 <b style="color: red">*</b>辨识结论:
				 </label>
			 </td>
			 <td class="value" colspan="3">
				 <textarea id="remark" name="remark" style="width: 80%;height: auto" rows="6" class="inputxt" datatype="*">${tBSpecialEvaluationPage.remark}</textarea>
				 <span class="Validform_checktip"></span>
				 <label class="Validform_label" style="display: none;">辨识结论</label>
			 </td>
		 </tr>
	 </table>
 </t:formvalid>
 </body>
