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
						     	 <input id="fileNo" disabled="disabled"  type="text" style="width: 150px" class="inputxt" value='${tBEmployeeInfoEntityPage.fileNo}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								在岗编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="postNumber" disabled="disabled"  type="text" style="width: 150px" class="inputxt" value='${tBEmployeeInfoEntityPage.postNumber}'>
							
						</td>
					</tr>
					<tr>
					<td align="right">
							<label class="Validform_label">
								姓名:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" disabled="disabled" type="text" style="width: 150px" class="inputxt" value='${tBEmployeeInfoEntityPage.name}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								退休编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="retireNumber" disabled="disabled"  type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoEntityPage.retireNumber}'>
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
									<t:dictSelect field="prejobTrainType" type="list"
										typeGroupCode="chk_category" defaultVal="${tBHealthTrainPage.prejobTrainType}" hasLabel="false"  title="培训类型上岗前"></t:dictSelect>     
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
									  <input id="prejobTrainDate" name="prejobTrainDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value='<fmt:formatDate value='${tBHealthTrainPage.prejobTrainDate}' type="date" pattern="yyyy-MM-dd"/>'>
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
						     	 <input id="prejobConResult" name="prejobConResult" type="text" style="width: 150px" class="inputxt"  value='${tBHealthTrainPage.prejobConResult}' ignore="ignore" datatype="/^\d+(\.{0,1}\d+){0,1}$/">
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
						     	 <input id="prejobTrainOrg" name="prejobTrainOrg" type="text" style="width: 150px" class="inputxt"  value='${tBHealthTrainPage.prejobTrainOrg}'>
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
									  <input id="duringTrainDate" name="duringTrainDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value='<fmt:formatDate value='${tBHealthTrainPage.duringTrainDate}' type="date" pattern="yyyy-MM-dd"/>'>
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
						     	 <input id="duringTrainOrg" name="duringTrainOrg" type="text" style="width: 150px" class="inputxt"  value='${tBHealthTrainPage.duringTrainOrg}'>
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
						     	 <input id="duringConResult" name="duringConResult" type="text" style="width: 150px" class="inputxt"  value='${tBHealthTrainPage.duringConResult}' ignore="ignore" datatype="/^\d+(\.{0,1}\d+){0,1}$/">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">综合成绩</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/health/js/tBHealthTrain.js"></script>