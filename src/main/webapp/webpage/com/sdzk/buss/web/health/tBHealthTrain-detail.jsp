<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>职业安全卫生教育培训专项档案</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBHealthTrainController.do?doUpdate" tiptype="3">
					<input id="id" name="id" type="hidden" value="${tBHealthTrainPage.id }">
					<input id="employeeId" name="employeeId" type="hidden" value="${tBHealthTrainPage.employeeId }">

					<input id="createName" name="createName" type="hidden" value="${tBHealthTrainPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tBHealthTrainPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tBHealthTrainPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tBHealthTrainPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tBHealthTrainPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tBHealthTrainPage.updateDate }">
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
			     <td colspan='4' align="center" ><strong>职工职业健康培训</strong></td>
			   </tr> 
			   <tr>
			   <td rowspan='7' align="center">
						<label class="Validform_label">
							职业健康培训
						</label>
					</td>
			   <td rowspan='4' align="center">
						<label class="Validform_label">
							上岗前
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							培训类型
						</label>
					</td>
					<td class="value">
                        <label class="Validform_label">${prejobTrainTypeName}</label>
                        <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">培训类型</label>
						</td>
				</tr>
				 <tr>
					<td align="center">
						<label class="Validform_label">
							培训时间
						</label>
					</td>
					<td class="value">
                        <fmt:formatDate value='${tBHealthTrainPage.prejobTrainDate}' type="date" pattern="yyyy-MM-dd"/>

							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">培训时间</label>
						</td>
				</tr>
				 <tr>
					<td align="center">
						<label class="Validform_label">
							综合成绩
						</label>
					</td>
					<td class="value">
                        <label class="Validform_label">${tBHealthTrainPage.prejobConResult}</label>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">综合成绩</label>
						</td>
				</tr>
				<tr>
					<td align="center">
						<label class="Validform_label">
							培训机构
						</label>
					</td>
					<td class="value">
                        <label class="Validform_label">${tBHealthTrainPage.prejobTrainOrg}</label>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">培训机构</label>
						</td>
				</tr>
				<tr>
			   <td rowspan='4' align="center">
						<label class="Validform_label">
							在岗期间
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							培训时间
						</label>
					</td>
					<td class="value">
                        <fmt:formatDate value='${tBHealthTrainPage.duringTrainDate}' type="date" pattern="yyyy-MM-dd"/>

							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">培训时间</label>
						</td>
				</tr>
				<tr>
					<td align="center">
						<label class="Validform_label">
							培训机构
						</label>
					</td>
					<td class="value">
                        <label class="Validform_label">${tBHealthTrainPage.duringTrainOrg}</label>

							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">培训机构</label>
						</td>
				</tr>
				<tr>
					<td align="center">
						<label class="Validform_label">
							综合成绩
						</label>
					</td>
					<td class="value">
                        <label class="Validform_label">${tBHealthTrainPage.duringConResult}</label>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">综合成绩</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/health/js/tBHealthTrain.js"></script>