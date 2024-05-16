<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>t_b_investigate_plan</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBInvestigatePlanController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${tBInvestigatePlanPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<c:if test="${tBInvestigatePlanPage.investigateType ne '1'}">
				<tr>
					<td align="right">
						<label class="Validform_label">
							计划开始时间:
						</label>
					</td>
					<td class="value">
								  <input id="startTime" name="startTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" disabled="disabled"
								  ignore="ignore" value='<fmt:formatDate value='${tBInvestigatePlanPage.startTime}' type="date" pattern="yyyy-MM-dd"/>'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">计划开始时间</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							计划结束时间:
						</label>
					</td>
					<td class="value">
								  <input id="endTime" name="endTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" disabled="disabled"
								  ignore="ignore" value='<fmt:formatDate value='${tBInvestigatePlanPage.endTime}' type="date" pattern="yyyy-MM-dd"/>'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">计划结束时间</label>
					</td>
				</tr>
			</c:if>
			<c:if test="${tBInvestigatePlanPage.investigateType eq '1'}">
				<tr>
					<td align="right">
						<label class="Validform_label">
							计划时间:
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="planMonth" name="planMonth" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM'})" disabled="disabled"
							   ignore="ignore" value='<fmt:formatDate value='${tBInvestigatePlanPage.startTime}' type="date" pattern="yyyy-MM"/>'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">计划开始时间</label>
					</td>
				</tr>
			</c:if>
				<%--<tr>
					<td align="right">
						<label class="Validform_label">
							风险点类型:
						</label>
					</td>
					<td class="value">
						<input id="riskPointType" name="riskPointType" type="text" style="width: 150px" class="inputxt" ignore="ignore" value='${tBInvestigatePlanPage.riskPointType}'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">风险点类型(1=区域;2=作业)</label>
					</td>
				</tr>--%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							风险点:
						</label>
					</td>
					<td class="value" colspan="3">
						<textarea id="riskPointName" name="riskPointName" readonly="true" style="width: 480px;" rows="5" class="inputxt" ignore="ignore">${tBInvestigatePlanPage.riskPointName}</textarea>
					</td>
				</tr>
				<%--<tr>--%>
					<%--<td align="right">--%>
						<%--<label class="Validform_label">--%>
							<%--危险源:--%>
						<%--</label>--%>
					<%--</td>--%>
					<%--<td class="value" colspan="3">--%>
						<%--<textarea id="riskName" name="riskName" readonly="true" style="width: 480px;" rows="5" class="inputxt" ignore="ignore">${tBInvestigatePlanPage.riskName}</textarea>--%>
					<%--</td>--%>
				<%--</tr>--%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							要求完成时间:
						</label>
					</td>
					<td class="value" colspan="3">
						  <input id="completeTime" name="completeTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" disabled="disabled"
						  ignore="ignore" value='<fmt:formatDate value='${tBInvestigatePlanPage.completeTime}' type="date" pattern="yyyy-MM-dd"/>'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">要求完成时间</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							受理单位:
						</label>
					</td>
					<td class="value">
						<input id="acceptDepart" name="acceptDepart" readonly="true" type="text" style="width: 150px" class="inputxt" ignore="ignore" value='${tBInvestigatePlanPage.acceptDepartName}'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">受理单位</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							受理人:
						</label>
					</td>
					<td class="value">
						<input id="acceptUser" name="acceptUser" readonly="true" type="text" style="width: 150px" class="inputxt"ignore="ignore"value='${tBInvestigatePlanPage.acceptUserRealName}'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">受理人</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							受理时间:
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="acceptTime" name="acceptTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" disabled="disabled"
								  ignore="ignore" value='<fmt:formatDate value='${tBInvestigatePlanPage.acceptTime}' type="date" pattern="yyyy-MM-dd"/>'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">受理时间</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>排查时间:
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="investigateTime" name="investigateTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" placeholder="选择排查时间"
								  ignore="ignore" value='<fmt:formatDate value='${tBInvestigatePlanPage.investigateTime}' type="date" pattern="yyyy-MM-dd"/>'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">排查时间</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>排查描述:
						</label>
					</td>
					<td class="value" colspan="3">
						<textarea id="investigateDesc" name="investigateDesc" style="width: 480px;" rows="5" class="inputxt" ignore="ignore" placeholder="请输入排查描述" >${tBInvestigatePlanPage.investigateDesc}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">排查描述</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/investigateplan/tBInvestigatePlan.js"></script>		
