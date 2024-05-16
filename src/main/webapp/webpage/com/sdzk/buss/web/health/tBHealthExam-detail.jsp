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
                        <fmt:formatDate value='${tBHealthExamPage.prejobChkDate}' type="date" pattern="yyyy-MM-dd"/>
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
                        <label class="Validform_label">${tBHealthExamPage.prejobChkOrg}</label>
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
                        <label class="Validform_label">${prejobChkCategoryName}</label>
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
                        <label class="Validform_label">${tBHealthExamPage.prejobNoAbnormal}</label>
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
                        <label class="Validform_label">${tBHealthExamPage.prejobBan}</label>
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
                        <label class="Validform_label">${tBHealthExamPage.prejobOtherDiseases}</label>
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
                        <label class="Validform_label">${tBHealthExamPage.prejobDisposition}</label>
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
                        <label class="Validform_label">${tBHealthExamPage.prejobIsNotify}</label>
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
                        <fmt:formatDate value='${tBHealthExamPage.duringChkDate}' type="date" pattern="yyyy-MM-dd"/>
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
                        <label class="Validform_label">${tBHealthExamPage.duringChkOrg}</label>
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
                        <label class="Validform_label">${tBHealthExamPage.duringNoAbnormal}</label>
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
                        <label class="Validform_label">${tBHealthExamPage.duringOccupationDiseases}</label>
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
                        <label class="Validform_label">${tBHealthExamPage.duringBan}</label>
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
                        <label class="Validform_label">${tBHealthExamPage.duringOtherDiseases}</label>
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
                        <label class="Validform_label">${tBHealthExamPage.duringDisposition}</label>
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
                        <label class="Validform_label">${tBHealthExamPage.duringIsNotify}</label>
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
                        <fmt:formatDate value='${tBHealthExamPage.leavingChkDate}' type="date" pattern="yyyy-MM-dd"/>
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
                        <label class="Validform_label">${tBHealthExamPage.leavingChkOrg}</label>
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
                        <label class="Validform_label">${tBHealthExamPage.leavingChkResult}</label>
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
                        <label class="Validform_label">${tBHealthExamPage.leavingIsNotify}</label>
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
                        <label class="Validform_label">${leavedTypeName}</label>
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
                        <fmt:formatDate value='${tBHealthExamPage.leavedChkDate}' type="date" pattern="yyyy-MM-dd"/>
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
                        <label class="Validform_label">${tBHealthExamPage.leavedChkResult}</label>
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
                        <fmt:formatDate value='${tBHealthExamPage.emergChkDate}' type="date" pattern="yyyy-MM-dd"/>
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
                        <label class="Validform_label">${tBHealthExamPage.emergChkCategory}</label>
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
                    <label class="Validform_label">${tBHealthExamPage.emergChkOrg}</label>
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
                    <label class="Validform_label">${tBHealthExamPage.emergDisposition}</label>
                    <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">处置情况</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/health/js/tBHealthExam.js"></script>