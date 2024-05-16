<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>三违信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>

	 <script>
         function goLargerimage(path){
             console.log(path);
             createdetailwindow("大图","tBThreeViolationsController.do?goLargerimage&path="+path+"",700,550);
         }
	 </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBThreeViolationsController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${tBThreeViolationsPage.id }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>违章时间:
							</label>
						</td>
						<td class="value">
                            <fmt:formatDate value='${tBThreeViolationsPage.vioDate}' type="date" pattern="yyyy-MM-dd"/>
						</td>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>班次:
							</label>
						</td>
						<td class="value">
                                ${tBThreeViolationsPage.shiftTemp}
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>违章地点:
							</label>
						</td>
						<td class="value">
                                ${tBThreeViolationsPage.addressTemp}
						</td>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>违章单位:
							</label>
						</td>
						<td class="value">
                                ${tBThreeViolationsPage.vioUnitesNameTemp}
						</td>
					</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>违章人员:
					</label>
				</td>
				<td class="value">
                        ${tBThreeViolationsPage.vioPeopleTemp}
				</td>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>违章分类:
							</label>
						</td>
						<td class="value">
                                ${tBThreeViolationsPage.vioCategoryTemp}
						</td>
					</tr>
			<c:if test="${xinchazhuang eq 'true'}">
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>职工编号:
						</label>
					</td>
					<td class="value" colspan="3">
							${tBThreeViolationsPage.employeeNum}
					</td>
				</tr>
			</c:if>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>三违级别:
							</label>
						</td>
						<td class="value">
                                ${tBThreeViolationsPage.vioLevelTemp}
						</td>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>违章定性:
					</label>
				</td>
				<td class="value">
                        ${tBThreeViolationsPage.vioQualitativeTemp}
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						查处单位:
					</label>
				</td>
				<td class="value">
                        ${tBThreeViolationsPage.findUnitsTemp}
				</td>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>制止人:
							</label>
						</td>
						<td class="value">
                                ${tBThreeViolationsPage.stopPeopleTemp}
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								三违事实描述:
							</label>
						</td>
						<td class="value">
								${tBThreeViolationsPage.vioFactDesc}
						</td>
						<td align="right">
							<label class="Validform_label">
								<c:if test="${henghe eq 'true'}">
									处理意见:
								</c:if>
								<c:if test="${henghe ne 'true'}">
									备注:
								</c:if>
							</label>
						</td>
						<td class="value">
								${tBThreeViolationsPage.remark}
						</td>
					</tr>

				<%--轮播图片的遍历 /sdzk_mine  test="${!empty list}">--%>
				<c:if test="${!empty imagelists}" >
					<tr >
						<td align="right">
							<label class="Validform_label">
								现场图片：
							</label>
						</td>
						<td class="value" colspan="3">
							<div >
								<c:forEach items="${imagelists}" var="imagePath" >
									<a href="#" onclick="goLargerimage('uploadfile/${mobileTBThreeViolationsPage.mobileId}/${imagePath}')" >
											<%--<img src="${imagePath}" style="height: 100px;width:auto;"/>--%>
										<img src="uploadfile/${mobileTBThreeViolationsPage.mobileId}/${imagePath}" style="height: 100px;width:auto;margin: 0 3px;" />
									</a>
								</c:forEach>
							</div>
						</td>
					</tr>
				</c:if>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/violations/tBThreeViolations.js"></script>
