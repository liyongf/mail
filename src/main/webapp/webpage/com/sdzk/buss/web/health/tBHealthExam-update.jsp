<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>职业健康检查专项档案</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
  <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
 	<table style="width: 650px;" cellpadding="0" cellspacing="1" class="formtable">
<tr>
						<td align="right">
							<label class="Validform_label">
								档案号:
							</label>
						</td>
						<td class="value">
						     	 <input id="fileNo" disabled="disabled" name="fileNo" type="text" style="width: 150px" class="inputxt" value='${tBEmployeeInfoEntityPage.fileNo}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								在岗编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="postNumber" disabled="disabled" name="postNumber" type="text" style="width: 150px" class="inputxt" value='${tBEmployeeInfoEntityPage.postNumber}'>
							
						</td>
					</tr>
					<tr>
					<td align="right">
							<label class="Validform_label">
								姓名:
							</label>
						</td>
						<td class="value">
						     	 <input id="name" name="name" disabled="disabled" type="text" style="width: 150px" class="inputxt" value='${tBEmployeeInfoEntityPage.name}'>
						</td>
						<td align="right">
							<label class="Validform_label">
								退休编号:
							</label>
						</td>
						<td class="value">
						     	 <input id="retireNumber" disabled="disabled" name="retireNumber" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoEntityPage.retireNumber}'>
						
						</td>
					</tr>
			</table>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBHealthExamController.do?doUpdate" tiptype="3">
					<input id="id" name="id" type="hidden" value="${tBHealthExamPage.id }">
					<input id="employeeId" name="employeeId" type="hidden" value="${tBHealthExamPage.employeeId }">
						<input id="createName" name="createName" type="hidden" value="${tBHealthExamPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tBHealthExamPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tBHealthExamPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tBHealthExamPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tBHealthExamPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tBHealthExamPage.updateDate }">

		<table style="width: 650px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			     <td colspan='5' align="center" ><strong>职工职业健康检查专项档案</strong></td>
			   </tr> 
			   <tr>
			   <td rowspan='27' align="center">
						<label class="Validform_label">
							职业健康<br>检查
						</label>
					</td>
					<td rowspan='8' align="center">
						<label class="Validform_label">
							上岗前
						</label>
					</td>

					<td colspan="2" align="center">
						<label class="Validform_label">
							检查时间
						</label>
					</td>
					<td  class="value">
								  <input id="prejobChkDate" name="prejobChkDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value='<fmt:formatDate value='${tBHealthExamPage.prejobChkDate}' type="date" pattern="yyyy-MM-dd"/>'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">检查时间</label>
					</td>
				</tr>
				 <tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查机构
						</label>
					</td>
					<td  class="value">
						     	 <input id="prejobChkOrg" name="prejobChkOrg" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.prejobChkOrg}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查机构</label>
						</td>
				</tr>
				 <tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查类别
						</label>
					</td>
					<td class="value">
									<t:dictSelect field="prejobChkCategory" type="list"
										typeGroupCode="chk_category" defaultVal="${tBHealthExamPage.prejobChkCategory}" hasLabel="false"  title="检查类别"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查类别</label>
						</td>
				</tr>
				 <tr>
					<td rowspan="3" align="center">
						<label class="Validform_label">
							检查结果
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							未见异常
						</label>
					</td>
					<td class="value">
						     	 <input id="prejobNoAbnormal" name="prejobNoAbnormal" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.prejobNoAbnormal}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查结果未见异常</label>
					</td>
				</tr>
				<tr>
					<td align="center">
						<label class="Validform_label">
							职业禁忌
						</label>
					</td>
					<td class="value">
						     	 <input id="prejobBan" name="prejobBan" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.prejobBan}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查结果职业禁忌</label>
					</td>
				</tr>
				<tr>
					<td align="center">
						<label class="Validform_label">
							其他疾病
						</label>
					</td>
					<td class="value">
						     	 <input id="prejobOtherDiseases" name="prejobOtherDiseases" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.prejobOtherDiseases}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查结果其他疾病</label>
					</td>
				</tr>
				 <tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							处置情况
						</label>
					</td>
					<td class="value">
						     	 <input id="prejobDisposition" name="prejobDisposition" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.prejobDisposition}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">处置情况</label>
						</td>
				</tr>
				 <tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							是否书面告知
						</label>
					</td>
					<td class="value">
						     	 <input id="prejobIsNotify" name="prejobIsNotify" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.prejobIsNotify}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否书面告知</label>
						</td>
				</tr>
				<tr>
					<td rowspan='8' align="center">
						<label class="Validform_label">
							在岗期间
						</label>
					</td>

					<td colspan="2" align="center">
						<label class="Validform_label">
							检查时间
						</label>
					</td>
					<td class="value">
									  <input id="duringChkDate" name="duringChkDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value='<fmt:formatDate value='${tBHealthExamPage.duringChkDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查时间</label>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查机构
						</label>
					</td>
					<td class="value">
					     	 <input id="duringChkOrg" name="duringChkOrg" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.duringChkOrg}'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">检查机构在岗期间</label>
					</td>
				</tr>
				 <tr>
					<td rowspan="4" align="center">
						<label class="Validform_label">
							检查结果
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							未见异常
						</label>
					</td>
					<td class="value">
						     	 <input id="duringNoAbnormal" name="duringNoAbnormal" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.duringNoAbnormal}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查结果未见异常</label>
						</td>
				</tr>
				 <tr>
					<td align="center">
						<label class="Validform_label">
							疑是职业病
						</label>
					</td>
					<td class="value">
						     	 <input id="duringOccupationDiseases" name="duringOccupationDiseases" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.duringOccupationDiseases}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查结果疑是职业病在岗期间</label>
						</td>
				</tr>
				<tr>
					<td align="center">
						<label class="Validform_label">
							职业禁忌
						</label>
					</td>
					<td class="value">
						     	 <input id="duringBan" name="duringBan" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.duringBan}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查结果职业禁忌在岗期间</label>
						</td>
				</tr>
				<tr>
					<td align="center">
						<label class="Validform_label">
							其他疾病
						</label>
					</td>
					<td class="value">
						     	 <input id="duringOtherDiseases" name="duringOtherDiseases" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.duringOtherDiseases}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查结果其他疾病</label>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							处置情况
						</label>
					</td>
					<td class="value">
						     	 <input id="duringDisposition" name="duringDisposition" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.duringDisposition}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">处置情况</label>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							是否书面告知
						</label>
					</td>
					<td class="value">
						     	 <input id="duringIsNotify" name="duringIsNotify" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.duringIsNotify}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否书面告知</label>
					</td>
				</tr>
				<tr>
					<td rowspan='4' align="center">
						<label class="Validform_label">
							离岗时
						</label>
					</td>

					<td colspan="2" align="center">
						<label class="Validform_label">
							检查时间
						</label>
					</td>
					<td class="value">
									  <input id="leavingChkDate" name="leavingChkDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value='<fmt:formatDate value='${tBHealthExamPage.leavingChkDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查时间</label>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查机构
						</label>
					</td>
					<td class="value">
						     	 <input id="leavingChkOrg" name="leavingChkOrg" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.leavingChkOrg}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查机构</label>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查结果
						</label>
					</td>
					<td class="value">
						     	 <input id="leavingChkResult" name="leavingChkResult" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.leavingChkResult}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查结果</label>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							是否书面告知
						</label>
					</td>
					<td class="value">
						     	 <input id="leavingIsNotify" name="leavingIsNotify" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.leavingIsNotify}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">是否书面告知</label>
						</td>
				</tr>
				<tr>
					<td rowspan='3' align="center">
						<label class="Validform_label">
							离岗后
						</label>
					</td>

					<td colspan="2" align="center">
						<label class="Validform_label">
							离岗类型
						</label>
					</td>
					<td class="value">
									<t:dictSelect field="leavedType" type="radio"
										typeGroupCode="leaved_type" defaultVal="${tBHealthExamPage.leavedType}" hasLabel="false"  title="离岗类型离岗后"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">离岗类型</label>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查时间
						</label>
					</td>
					<td class="value">
									  <input id="leavedChkDate" name="leavedChkDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value='<fmt:formatDate value='${tBHealthExamPage.leavedChkDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查时间</label>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查结果
						</label>
					</td>
					<td class="value">
						     	 <input id="leavedChkResult" name="leavedChkResult" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.leavedChkResult}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查结果</label>
						</td>
				</tr>
				<tr>
					<td rowspan='4' align="center">
						<label class="Validform_label">
							应急检查
						</label>
					</td>

					<td colspan="2" align="center">
						<label class="Validform_label">
							检查时间
						</label>
					</td>
					<td class="value">
									  <input id="emergChkDate" name="emergChkDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value='<fmt:formatDate value='${tBHealthExamPage.emergChkDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查时间</label>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查种类
						</label>
					</td>
					<td class="value">
						     	 <input id="emergChkCategory" name="emergChkCategory" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.emergChkCategory}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查种类</label>
						</td>
				</tr>
				<tr>
				<td colspan="2" align="center">
					<label class="Validform_label">
						检查机构
					</label>
				</td>
				<td class="value">
					     	 <input id="emergChkOrg" name="emergChkOrg" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.emergChkOrg}'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">检查机构</label>
					</td>
				</tr>
				<tr>
				<td colspan="2" align="center">
					<label class="Validform_label">
						处置情况
					</label>
				</td>
				<td class="value">
						     	 <input id="emergDisposition" name="emergDisposition" type="text" style="width: 150px" class="inputxt"  value='${tBHealthExamPage.emergDisposition}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">处置情况</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/health/js/tBHealthExam.js"></script>