<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>职业健康疗养专项档案</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBHealthRecuperateController.do?doUpdate" tiptype="3">
					<input id="id" name="id" type="hidden" value="${tBHealthRecuperatePage.id }">
					<input id="employeeId" name="employeeId" type="hidden" value="${tBHealthRecuperatePage.employeeId }">
						<input id="createName" name="createName" type="hidden" value="${tBHealthRecuperatePage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tBHealthRecuperatePage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tBHealthRecuperatePage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tBHealthRecuperatePage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tBHealthRecuperatePage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tBHealthRecuperatePage.updateDate }">

					
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable"> 
		<tr>
						<td align="right">
							<label class="Validform_label">
								档案号:
							</label>
						</td>
						<td class="value">
                            <label class="Validform_label">${tBEmployeeInfoEntityPage.fileNo}</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								在岗编号:
							</label>
						</td>
						<td class="value">
                            <label class="Validform_label">${tBEmployeeInfoEntityPage.postNumber}</label>
						</td>
					</tr>
					<tr>
					<td align="right">
							<label class="Validform_label">
								姓名:
							</label>
						</td>
						<td class="value">
                            <label class="Validform_label">${tBEmployeeInfoEntityPage.name}</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								退休编号:
							</label>
						</td>
						<td class="value">
                            <label class="Validform_label">${tBEmployeeInfoEntityPage.retireNumber}</label>
						</td>
					</tr>
					<tr>
			     <td colspan='4' align="center" ><strong>疗养信息</strong></td>
			   </tr> 
					<tr>
						<td align="right">
							<label class="Validform_label">
								疗养时间:
							</label>
						</td>
						<td class="value">
                            <fmt:formatDate value='${tBHealthRecuperatePage.recupDate}' type="date" pattern="yyyy-MM-dd"/>
                            <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">疗养时间</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								疗养周期（年）:
							</label>
						</td>
						<td class="value">
                            <label class="Validform_label">${tBHealthRecuperatePage.recupCycle}</label>
                            <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">疗养周期年</label>
						</td>
					</tr>
					<tr>
						
						<td align="right">
							<label class="Validform_label">
								疗养地点:
							</label>
						</td>
						<td colspan="3" class="value">
						     	<%--  <input id="recupAddress" name="recupAddress" type="text" style="width: 150px" class="inputxt"  value='${tBHealthRecuperatePage.recupAddress}'> --%>
							<textarea id="recupAddress" style="width:460px;" class="inputxt" rows="3" name="recupAddress">${tBHealthRecuperatePage.recupAddress}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">疗养地点</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								下次疗养时间:
							</label>
						</td>
						<td class="value">
                            <fmt:formatDate value='${tBHealthRecuperatePage.nextRecupDate}' type="date" pattern="yyyy-MM-dd"/>
                            <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">下次疗养时间</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								今年是否疗养:
							</label>
						</td>
						<td class="value">
                            <label class="Validform_label">${tBHealthRecuperatePage.thisYearIsRecup}</label>
                            <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">今年是否疗养</label>
						</td>
					</tr>
	
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/health/js/tBHealthRecuperate.js"></script>