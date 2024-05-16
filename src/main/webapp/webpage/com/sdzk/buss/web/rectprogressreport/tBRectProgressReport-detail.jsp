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
	  var rectUnitSelect = getDepartMagicSuggestWithValue($("#rectUnitSelect"), $("#rectUnit"),"${tBMajorHiddenDangerPage.rectUnit}", true);
	  var rectManSelect = getUserMagicSuggestWithValue($("#rectManSelect"), $("#rectMan"),"${tBMajorHiddenDangerPage.rectMan}", true);
//      alert(rectManSelect.getName());
//      console.log(rectManSelect);
	  if (${tBRectProgressReportPage.isComplete=='1'}) {
		  $(".rectifyInfo").show();
	  }
  })
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBRectProgressReportController.do?doUpdate" callback="@Override noteSubmitCallback" >
					<input id="id" name="id" type="hidden" value="${tBRectProgressReportPage.id }">
			<input id="fkHiddenInfoId" name="fkHiddenInfoId" type="hidden" value="${tBRectProgressReportPage.fkHiddenInfoId }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								汇报日期:
							</label>
						</td>
						<td class="value">
                            <fmt:formatDate value='${tBRectProgressReportPage.reportDate}' type="date" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								汇报人:
							</label>
						</td>
						<td class="value">
                                ${tBRectProgressReportPage.reportPersonName}
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								整改进展:
							</label>
						</td>
						<td class="value">
                                ${tBRectProgressReportPage.reportDesc}
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								是否汇报完毕:
							</label>
						</td>
						<td class="value">
                                ${tBRectProgressReportPage.isComplete=='1'?'是':'否'}
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
					<fmt:formatDate value='${tBMajorHiddenDangerPage.rectTagartDt}' type="date" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr class = "rectifyInfo" style="display: none;">
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>整改措施:
					</label>
				</td>
				<td class="value">
                        ${tBMajorHiddenDangerPage.rectMeasures}
				</td>
			</tr >
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/rectprogressreport/tBRectProgressReport.js"></script>		
