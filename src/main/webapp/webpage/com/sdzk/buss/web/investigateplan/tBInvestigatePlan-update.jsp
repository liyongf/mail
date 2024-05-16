<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>t_b_investigate_plan</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
	<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
	<script type="text/javascript">
		//编写自定义JS代码
		$(document).ready(function(){
			var acceptDepartSelect = getDepartMagicSuggestWithValue($('#acceptDepartSelect'), $("#acceptDepart"),"${tBInvestigatePlanPage.acceptDepart}",false);
			var acceptUserSelect = getUserMagicSuggestWithValue($('#acceptUserSelect'), $("#acceptUser"),"${tBInvestigatePlanPage.acceptUser}",false);
			$(acceptDepartSelect).one('focus', function(){
				$(acceptDepartSelect).on('selectionchange', function (c) {
					acceptUserSelect.clear(true);
					$("#acceptUser").val("");
				});
			})
			$(acceptUserSelect).on('focus', function (c) {
                acceptUserSelect.setData({});
				//判断是否选择责任单位
				var deptId = $('#acceptDepart').val();
				acceptUserSelect.setData({});
				if (deptId == null || deptId == "") {
					tip("请先选择责任单位");
					acceptUserSelect.clear(true);
					return;
				} else {
					acceptUserSelect.setData("magicSelectController.do?getUserList&orgIds=" + deptId);
				}
			});
		})
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBInvestigatePlanController.do?doUpdate" tiptype="3">
	<input id="id" name="id" type="hidden" value="${tBInvestigatePlanPage.id }">
	<input id="type" name="investigateType" type="hidden"  value="${investigateType}">
	<input id="saveOrCommit" name="saveOrCommit" type="hidden" value="save">
	<table style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>计划时间:
				</label>
			</td>
			<td class="value" colspan="3">
				<c:if test="${investigateType eq '1'}">
					<input id="month" name="month" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM'})"
						   datatype="*"
						   value='<fmt:formatDate value='${tBInvestigatePlanPage.startTime}' type="date" pattern="yyyy-MM"/>'>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">计划时间</label>
				</c:if>
				<c:if test="${investigateType ne '1'}">
					<input id="startTime" name="startTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"
						   datatype="*"
						   value='<fmt:formatDate value='${tBInvestigatePlanPage.startTime}' type="date" pattern="yyyy-MM-dd"/>'>
					<input id="endTime" name="endTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"
						   datatype="*"
						   value='<fmt:formatDate value='${tBInvestigatePlanPage.endTime}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">计划时间</label>

				</c:if>
			</td>
		</tr>
		<tr style="display: none">
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>风险点类型:
				</label>
			</td>
			<td class="value" colspan="3">
				<t:dictSelect field="riskPointType" typeGroupCode="investPlan_riskPoint_type" hasLabel="false" datatype="*" defaultVal="1"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">风险点类型</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>风险点:
				</label>
			</td>
			<td class="value" colspan="2">
				<input id="riskPointIds" name="riskPointIds" type="hidden" style="width: 150px" class="inputxt" value="${tBInvestigatePlanPage.riskPointIds}">
				<textarea id="riskPointName" name="riskPointName" style="width: 95%;" datatype="*" class="inputxt" onclick="choose_riskPoint('选择风险点');"  readonly="true">${tBInvestigatePlanPage.riskPointName}</textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">风险点</label>
			</td>
			<td class="value">
				<div class="ui_buttons" style="text-align: left;">
					<input type="button" onclick="choose_riskPoint('选择风险点');" value="选择">
					<c:if test="${investigateType ne '1'}">
						<input type="button" onclick="choose_riskPoint('选择风险点','month');" value="从月度计划中选择">
					</c:if>
					<input type="button" onclick="clean_riskPoint();" value="清空">
				</div>
			</td>
		</tr>
		<%--<tr>--%>
			<%--<td align="right">--%>
				<%--<label class="Validform_label">--%>
					<%--<font color="red">*</font>危险源所关联的隐患描述:--%>
				<%--</label>--%>
			<%--</td>--%>
			<%--<td class="value" colspan="2" style="border-right-width: 0px">--%>
				<%--<input id="riskIds" name="riskIds" type="hidden" style="width: 150px" class="inputxt" value="${tBInvestigatePlanPage.riskIds}">--%>
				<%--<textarea id="riskName" name="riskName" style="width: 95%" datatype="*" class="inputxt" onclick="choose_risk('选择危险源');"  readonly="true">${tBInvestigatePlanPage.riskName}</textarea>--%>
				<%--<span class="Validform_checktip"></span>--%>
				<%--<label class="Validform_label" style="display: none;">危险源</label>--%>
			<%--</td>--%>
			<%--<td class="value" style="border-left-width: 0px">--%>
				<%--<div class="ui_buttons" style="text-align: left;">--%>
					<%--<input type="button" onclick="choose_risk('选择危险源');" value="选择">--%>
					<%--<c:if test="${investigateType ne '1'}">--%>
						<%--<input type="button" onclick="choose_risk('选择危险源','month');" value="从月度计划中选择">--%>
					<%--</c:if>--%>
					<%--<input type="button" onclick="clean_risk();" value="清空">--%>
				<%--</div>--%>
			<%--</td>--%>
		<%--</tr>--%>
		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>要求完成时间:
				</label>
			</td>
			<td class="value">
				<input id="completeTime" name="completeTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"
					   datatype="*"
					   value='<fmt:formatDate value='${tBInvestigatePlanPage.completeTime}' type="date" pattern="yyyy-MM-dd"/>'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">要求完成时间</label>
			</td>
			<td align="right">
			</td>
			<td class="value">
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>受理单位:
				</label>
			</td>
			<td class="value">
				<div id="acceptDepartSelect" style="width: 130px;height: 15px"></div>
				<input id="acceptDepart" name="acceptDepart" type="hidden" style="width: 150px" class="inputxt"  datatype="*" value='${tBInvestigatePlanPage.acceptDepart}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">受理单位</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					受理人:
				</label>
			</td>
			<td class="value">
				<div id="acceptUserSelect" style="width: 130px;height: 15px"></div>
				<input id="acceptUser" name="acceptUser" type="hidden" style="width: 150px" class="inputxt"  value='${tBInvestigatePlanPage.acceptUser}'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">受理人</label>
			</td>
		</tr>
		<tr>
			<td class="value" colspan="4">
				<div class="ui_buttons" style="text-align: center;">
					<input type="button" style="display: none" id="btn_sub">
					<input type="button" onclick="save('save');" class="ui_state_highlight" value="保存">
					<input type="button" onclick="save('commit');" value="提交">
				</div>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>
<script src = "webpage/com/sdzk/buss/web/investigateplan/tBInvestigatePlan.js"></script>
