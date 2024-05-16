<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>职工职业履历档案</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBEmployeeResumeController.do?doUpdate" tiptype="3">
					<input id="id" name="id" type="hidden" value="${tBEmployeeResumePage.id }">
					<input id="createName" name="createName" type="hidden" value="${tBEmployeeResumePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tBEmployeeResumePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tBEmployeeResumePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tBEmployeeResumePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tBEmployeeResumePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tBEmployeeResumePage.updateDate }">

		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
<tr>
						<td align="right">
							<label class="Validform_label">
								档案号:
							</label>
						</td>
						<td class="value">
						     	 <input id="fileNo" disabled="disabled" name="fileNo" type="text" style="width: 150px" class="inputxt" datatype="*" value='${tBEmployeeResumePage.fileNo}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								在岗编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="postNumber" disabled="disabled" name="postNumber" type="text" style="width: 150px" class="inputxt" value='${tBEmployeeResumePage.postNumber}'>
							
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								退休编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="retireNumber" disabled="disabled" name="retireNumber" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeResumePage.retireNumber}'>
						
						</td>
						<td align="right">
							<label class="Validform_label">
								姓名:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" disabled="disabled" type="text" style="width: 150px" class="inputxt" value='${tBEmployeeResumePage.name}'>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								岗位变更简历:
							</label>
						</td>
						<td class="value" colspan="3">
						  	 	<textarea id="postChangeResume" style="width:500px;" class="inputxt" rows="10" name="postChangeResume">${tBEmployeeResumePage.postChangeResume}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">岗位变更简历</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/health/js/tBEmployeeResume.js"></script>