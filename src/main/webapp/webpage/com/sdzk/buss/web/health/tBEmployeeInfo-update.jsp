<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>职工个人信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBEmployeeInfoController.do?doUpdate" tiptype="3">
					<input id="id" name="id" type="hidden" value="${tBEmployeeInfoPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tBEmployeeInfoPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tBEmployeeInfoPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tBEmployeeInfoPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tBEmployeeInfoPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tBEmployeeInfoPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tBEmployeeInfoPage.updateDate }">
					<input id="isDelete" name="isDelete" type="hidden" value="${tBEmployeeInfoPage.isDelete }">
					<%-- <input id="healthExamId" name="tBHealthExamEntity.id" type="hidden" value="${tBEmployeeInfoPage.tBHealthExamEntity.id }">
					<input id="healthRecuperateId" name="tBHealthRecuperateEntity.id " type="hidden" value="${tBEmployeeInfoPage.tBHealthRecuperateEntity.id  }">
					<input id="healthTrainId" name="tBHealthTrainEntity.id" type="hidden" value="${tBEmployeeInfoPage.tBHealthTrainEntity.id }"> --%>
					<input id="postChangeResume" name="postChangeResume" type="hidden" value="${tBEmployeeInfoPage.postChangeResume }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								档案号:
							</label>
						</td>
						<td class="value">
						     	 <input id="fileNo" name="fileNo" type="text" style="width: 150px" class="inputxt"  readonly="readonly" value='${tBEmployeeInfoPage.fileNo}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">档案号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								在岗编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="postNumber" name="postNumber" type="text" style="width: 150px" class="inputxt"  readonly="readonly" value='${tBEmployeeInfoPage.postNumber}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">在岗编号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								退休编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="retireNumber" name="retireNumber" type="text" ignore="ignore"  datatype="*" validType="t_b_employee_info,retire_number" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.retireNumber}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">退休编号</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								姓名:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" type="text" style="width: 150px" class="inputxt" datatype="*" value='${tBEmployeeInfoPage.name}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">姓名</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								性别:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="gender" type="list"
										typeGroupCode="sex" defaultVal="${tBEmployeeInfoPage.gender}" hasLabel="false"  title="性别" datatype="*"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">性别</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								身份证号:
							</label>
						</td>
						<td class="value">
						     	 <input id="cardNumber" name="cardNumber" type="text" style="width: 150px" class="inputxt" datatype="*"  value='${tBEmployeeInfoPage.cardNumber}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">身份证号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								参加工作时间:
							</label>
						</td>
						<td class="value">
									  <input id="partiWorkDate" name="partiWorkDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value='<fmt:formatDate value='${tBEmployeeInfoPage.partiWorkDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">参加工作时间</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								现工作单位:
							</label>
						</td>
						<td class="value">
						     	 <input id="currWorkUnits" name="currWorkUnits" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.currWorkUnits}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">现工作单位</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								现从事工种:
							</label>
						</td>
						<td class="value">
						     	 <input id="currTrade" name="currTrade" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.currTrade}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">现从事工种</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								接触职业危害种类:
							</label>
						</td>
						<td class="value">
						     	 <input id="jobHazardCategory" name="jobHazardCategory" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.jobHazardCategory}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">接触职业危害种类</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								岗位类别:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="postCategory" type="list"
										typeGroupCode="postcategory" defaultVal="${tBEmployeeInfoPage.postCategory}" hasLabel="false" datatype="*" title="岗位类别"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">岗位类别</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								岗位状态:
							</label>
						</td>
						<td class="value">
									<t:dictSelect field="postStatus" type="list"
										typeGroupCode="poststatus" defaultVal="${tBEmployeeInfoPage.postStatus}" hasLabel="false" datatype="*" title="岗位状态"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">岗位状态</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/health/js/tBEmployeeInfo.js"></script>