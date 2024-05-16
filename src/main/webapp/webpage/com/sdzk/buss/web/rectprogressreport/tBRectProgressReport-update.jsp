<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>整改进度汇报</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
	 <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
	 <script src = "webpage/com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
	  var reportPersonSelect = getUserMagicSuggestWithValue($("#reportPersonSelect"), $("#reportPerson"),"${tBRectProgressReportPage.reportPerson}", false);
	  var rectUnitSelect = getDepartMagicSuggestWithValue($("#rectUnitSelect"), $("#rectUnit"),"${tBMajorHiddenDangerPage.rectUnit}", false);
	  var rectManSelect = getUserMagicSuggestWithValue($("#rectManSelect"), $("#rectMan"),"${tBMajorHiddenDangerPage.rectMan}", false);
	  $("#isComplete").on("click", function(){
		  if ($(this).attr('checked')) {
			  //显示整改责任人填写页面
			  $(".rectifyInfo").show();
			  $(".rectifyInfo td>textarea").removeAttr("disabled");
			  $(".rectifyInfo td>input").removeAttr("disabled");
			  $(".rectifyInfo td>textarea").attr("datatype","*");
			  $(".rectifyInfo td>input").attr("datatype","*");
		  } else {
			  //隐藏整改责任人填写页面
			  $(".rectifyInfo").hide();
			  $(".rectifyInfo td>textarea").attr("disabled","disabled");
			  $(".rectifyInfo td>input").attr("disabled","disabled");
			  $(".rectifyInfo td>textarea").removeAttr("datatype");
			  $(".rectifyInfo td>input").removeAttr("datatype");
			  $(".rectifyInfo td>textarea").val("");
			  $(".rectifyInfo td>input").val("");
			  rectUnitSelect.clear();
			  rectManSelect.clear();
		  }
	  });
	  if ($("#isComplete").attr('checked')) {
		  $(".rectifyInfo").show();
		  $(".rectifyInfo td>textarea").removeAttr("disabled");
		  $(".rectifyInfo td>input").removeAttr("disabled");
		  $(".rectifyInfo td>textarea").attr("datatype","*");
		  $(".rectifyInfo td>input").attr("datatype","*");
	  }
  })
  //重写了回调,然后自己控制关闭以及刷新
  function noteSubmitCallback(data) {
      if ($("#isComplete").attr('checked')){
          window.top["reload_tBMajorHiddenDangerList_rectify"].call(data);
      } else {
          window.top["reload_tBRectProgressReportList"].call(data);
      }
      frameElement.api.close();
  }
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBRectProgressReportController.do?doUpdate" tiptype="3" callback="@Override noteSubmitCallback" >
					<input id="id" name="id" type="hidden" value="${tBRectProgressReportPage.id }">
			<input id="fkHiddenInfoId" name="fkHiddenInfoId" type="hidden" value="${tBRectProgressReportPage.fkHiddenInfoId }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
                                <b style="color: red">*</b>汇报日期:
							</label>
						</td>
						<td class="value">
									  <input id="reportDate" name="reportDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" datatype="*"
									  ignore="checked"
									    value='<fmt:formatDate value='${tBRectProgressReportPage.reportDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">汇报日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
                                <b style="color: red">*</b>汇报人:
							</label>
						</td>
						<td class="value">
							<div id="reportPersonSelect" style="height: 15px;width: 130px"></div>
						     	 <input id="reportPerson" name="reportPerson" type="hidden" style="width: 150px" class="inputxt" datatype="*"
                                        value='${tBRectProgressReportPage.reportPerson}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">汇报人</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
                                <b style="color: red">*</b>整改进展:
							</label>
						</td>
						<td class="value">
							<textarea id="reportDesc" name="reportDesc" style="width: 80%;height: auto;" class="inputxt" datatype="*">${tBRectProgressReportPage.reportDesc}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">整改进展</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								是否汇报完毕:
							</label>
						</td>
						<td class="value">
						     	 <input id="isComplete" name="isComplete" type="checkbox"
						     	 value='1' ${tBRectProgressReportPage.isComplete=='1'?'checked':''}>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否汇报完毕</label>
						</td>
					</tr>
			<tr class = "rectifyInfo" style="display: none;">
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>整改单位:
					</label>
				</td>
				<td class="value">
					<div id="rectUnitSelect" style="height: 15px;width: 130px"></div>
					<input id="rectUnit" name="rectUnit" type="hidden" disabled="disabled"
						   value='${tBMajorHiddenDangerPage.rectUnit}'>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">整改单位</label>
				</td>
			</tr>
			<tr class = "rectifyInfo" style="display: none;">
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>整改责任人:
					</label>
				</td>
				<td class="value">
					<div id="rectManSelect" style="height: 15px;width: 130px"></div>
					<input id="rectMan" name="rectMan" type="hidden" disabled="disabled"
						   value='${tBMajorHiddenDangerPage.rectMan}'>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">整改责任人</label>
				</td>
			<tr class = "rectifyInfo" style="display: none;">
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>整改完成日期:
					</label>
				</td>
				<td class="value">
					<input id="rectTagartDt" name="rectTagartDt" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" disabled="disabled"
						   value='<fmt:formatDate value='${tBMajorHiddenDangerPage.rectTagartDt}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">整改完成日期</label>
				</td>
			</tr>
			<tr class = "rectifyInfo" style="display: none;">
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>整改措施:
					</label>
				</td>
				<td class="value">
					<textarea id="rectMeasures" name="rectMeasures" style="width: 80%;height: auto;" class="inputxt" disabled="disabled">${tBMajorHiddenDangerPage.rectMeasures}</textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">整改措施</label>
				</td>
			</tr >
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/rectprogressreport/tBRectProgressReport.js"></script>		
