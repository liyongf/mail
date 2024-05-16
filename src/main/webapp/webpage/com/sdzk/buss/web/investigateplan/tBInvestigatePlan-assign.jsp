<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>t_b_investigate_plan</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  $(document).ready(function(){
	  var acceptDepartSelect = getDepartMagicSuggestWithValue($('#acceptDepartSelect'), $("#acceptDepart"),"${tBInvestigatePlanPage.acceptDepart}",false);
	  <%--var acceptUserSelect = getUserMagicSuggestWithValue($('#acceptUserSelect'), $("#acceptUser"),"${tBInvestigatePlanPage.acceptUser}",false);--%>
	  var acceptUserSelect = getMagicSuggest($('#acceptUserSelect'), $("#acceptUser"), "magicSelectController.do?getUserList&orgIds=${tBInvestigatePlanPage.acceptDepart}", "${tBInvestigatePlanPage.acceptUser}", false,1,"realName");
	  $(acceptDepartSelect).one('focus', function(){
		  $(acceptDepartSelect).on('selectionchange', function (c) {
			  acceptUserSelect.clear(true);
			  $("#acceptUser").val("");
		  });
	  })
	  $(acceptUserSelect).on('focus', function (c) {
          acceptUserSelect.setData({});
		  //判断是否选择责任单位
		  var deptId = $('#acceptDepart').val();
		  acceptUserSelect.setData({});
		  if (deptId == null || deptId == "") {
			  tip("请先选择责任单位");
			  acceptUserSelect.clear(true);
			  return;
		  } else {
			  acceptUserSelect.setData("magicSelectController.do?getUserList&orgIds=" + deptId);
		  }
	  });
  })
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBInvestigatePlanController.do?doAssign" >
			<input id="id" name="id" type="hidden" value="${tBInvestigatePlanPage.id }">
			<input id="type" name="investigateType" type="hidden"  value="${investigateType}">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								受理单位:
							</label>
						</td>
						<td class="value">
							<div id="acceptDepartSelect" style="width: 130px;height: 15px"></div>
							<input id="acceptDepart" name="acceptDepart" type="hidden" style="width: 150px" class="inputxt"  datatype="*" value='${tBInvestigatePlanPage.acceptDepart}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">受理单位</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								受理人:
							</label>
						</td>
						<td class="value">
							<div id="acceptUserSelect" style="width: 130px;height: 15px"></div>
							<input id="acceptUser" name="acceptUser" type="hidden" style="width: 150px" class="inputxt"  value='${tBInvestigatePlanPage.acceptUser}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">受理人</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/investigateplan/tBInvestigatePlan.js"></script>		
