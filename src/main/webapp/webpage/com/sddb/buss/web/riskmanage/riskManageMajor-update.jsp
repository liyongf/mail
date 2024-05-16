<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>危害因素</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
     <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
         <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
	 <script type="text/javascript">
         var magicsuggestPostNameSelSelected = "";
         $(function() {
             magicsuggestPostNameSelSelected = $('#magicsuggestPostName').magicSuggest({
                 data: 'magicSelectController.do?getPostNameNoExists',
                 allowFreeEntries: true,
                 value:["${riskManageMajorPage.majorName}"],
                 valueField: 'post_name',
                 placeholder: '输入或选择',
                 maxSelection: 1,
                 selectFirst: true,
                 highlight: false,
                 displayField: 'post_name'
             });
             $(magicsuggestPostNameSelSelected).on('selectionchange', function (e, m) {
                 $("#majorName").val(magicsuggestPostNameSelSelected.getValue());
             });
         });

	 </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskManageController.do?doUpdateMajor" >
		<input id="id" name="id" type="hidden" value="${riskManageMajorPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<c:if test="${riskManageMajorPage.manageType eq 'post'}">
				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>岗位:
						</label>
					</td>
					<td class="value">
						<div id="magicsuggestPostName" style="width: 130px;height: 15px"></div>
						<input type="hidden" name="majorName" id="majorName" value="" style="width: 150px" class="inputxt" datatype="*" value="${riskManageMajorPage.majorName }">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">岗位</label>
					</td>
				</tr>
			</c:if>
			<c:if test="${riskManageMajorPage.manageType ne 'post'}">
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>${riskManager}名称:
						</label>
					</td>
					<td class="value">
						<input id="majorName" name="majorName" type="text" style="width: 150px" class="inputxt" datatype="*" value="${riskManageMajorPage.majorName }" ajaxurl="riskManageController.do?majorNameExists&manageType=${manageType}&riskManageMajorId=${riskManageMajorPage.id}">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">${riskManager}名称</label>
					</td>
				</tr>
			</c:if>
			</table>
		</t:formvalid>
 </body>
