<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>安全生产标准化</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="sfMineStandardAssessController.do?doUpdate" tiptype="1" >
					<input id="id" name="id" type="hidden" value="${sfMineStandardAssessPage.id }">
					<input id="ssaMineType" name="ssaMineType" type="hidden" value="${sfMineStandardAssessPage.ssaMineType }">
					<input id="ssaAssessType" name="ssaAssessType" type="hidden" value="${sfMineStandardAssessPage.ssaAssessType }">
					<input id="ssaCurrentStatus" name="ssaCurrentStatus" type="hidden" value="${sfMineStandardAssessPage.ssaCurrentStatus }">
					<input id="createBy" name="createBy" type="hidden" value="${sfMineStandardAssessPage.createBy }">
					<input id="createName" name="createName" type="hidden" value="${sfMineStandardAssessPage.createName }">
					<input id="createDate" name="createDate" type="hidden" value="${sfMineStandardAssessPage.createDate }">
					<input id="updateBy" name="updateBy" type="hidden" value="${sfMineStandardAssessPage.updateBy }">
					<input id="updateName" name="updateName" type="hidden" value="${sfMineStandardAssessPage.updateName }">
					<input id="updateDate" name="updateDate" type="hidden" value="${sfMineStandardAssessPage.updateDate }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								考核月份:
							</label>
						</td>
						<td class="value">
									  <input id="ssaMonth" name="ssaMonth" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${sfMineStandardAssessPage.ssaMonth}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">考核月份</label>
						</td>
					<tr>
						<td align="right">
							<label class="Validform_label">
								得分:
							</label>
						</td>
						<td class="value">
						     	 <input id="ssaSumScore" name="ssaSumScore" type="text" style="width: 150px" class="inputxt"  value='${sfMineStandardAssessPage.ssaSumScore}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">得分</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/standardassess/js/sfMineStandardAssess.js"></script>		
