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
		$(function(){
			$("#detailRisk").removeAttr("disabled");
		})
		function detail_risk(inveId){
			var url = 'url:tBDangerSourceController.do?chooseInveDangerSource&inveId='+inveId;
			if (typeof(windowapi) == 'undefined') {
				$.dialog({content: url, zIndex: 9999, title: '已关联危险源', lock: true, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
					{name: '关闭', callback: function () {
					}}
				]});
			} else {
				$.dialog({content: url, zIndex: 9999, title: '已关联危险源', lock: true, parent: windowapi, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
					{name: '关闭', callback: function () {
					}}
				]});
			}
		}
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBInvestigatePlanController.do?doUpdate" tiptype="3">
<div id="tt" class="easyui-tabs" style="width: auto;" data-options="onSelect:function(t){$('#tt .panel-body').css('width','auto');}">
	<div title="排查计划信息">
			<input id="id" name="id" type="hidden" value="${tBInvestigatePlanPage.id }">
			<input id="type" name="investigateType" type="hidden"  value="${investigateType}">
			<input id="saveOrCommit" name="saveOrCommit" type="hidden" value="save">
			<table style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							计划时间:
						</label>
					</td>
					<td class="value" colspan="3">
						<c:if test="${investigateType eq '1'}">
							<fmt:formatDate value='${tBInvestigatePlanPage.startTime}' type="date" pattern="yyyy-MM"/>
						</c:if>
						<c:if test="${investigateType ne '1'}">
							<fmt:formatDate value='${tBInvestigatePlanPage.startTime}' type="date" pattern="yyyy-MM-dd"/>至<fmt:formatDate value='${tBInvestigatePlanPage.endTime}' type="date" pattern="yyyy-MM-dd"/>
						</c:if>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							风险点类型:
						</label>
					</td>
					<td class="value" colspan="3">
							${tBInvestigatePlanPage.riskPointTypeDesc}
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							风险点:
						</label>
					</td>
					<td class="value" colspan="3">
							${tBInvestigatePlanPage.riskPointName}
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							危险源:
						</label>
					</td>
					<td class="value" colspan="3" style="border-right-width: 0px">
						<div class="ui_buttons" style="text-align: left;">
							<input type="button" id="detailRisk" onclick="detail_risk('${tBInvestigatePlanPage.id}');" value="查看详情">
						</div>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							要求完成时间:
						</label>
					</td>
					<td class="value" colspan="3">
						<fmt:formatDate value='${tBInvestigatePlanPage.completeTime}' type="date" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							派发人:
						</label>
					</td>
					<td class="value">
							${tBInvestigatePlanPage.createName}
					</td>
					<td align="right">
						<label class="Validform_label">
							派发时间:
						</label>
					</td>
					<td class="value">
						<fmt:formatDate value='${tBInvestigatePlanPage.createDate}' type="date" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							受理单位:
						</label>
					</td>
					<td class="value">
							${tBInvestigatePlanPage.acceptDepartName}
					</td>
					<td align="right">
						<label class="Validform_label">
							受理时间:
						</label>
					</td>
					<td class="value">
						<fmt:formatDate value='${tBInvestigatePlanPage.acceptTime}' type="date" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							排查人:
						</label>
					</td>
					<td class="value">
							${tBInvestigatePlanPage.acceptUserRealName}
					</td>
					<td align="right">
						<label class="Validform_label">
							排查时间:
						</label>
					</td>
					<td class="value">
						<fmt:formatDate value='${tBInvestigatePlanPage.investigateTime}' type="date" pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							排查描述:
						</label>
					</td>
					<td class="value" colspan="3">
							${tBInvestigatePlanPage.investigateDesc}
					</td>
				</tr>
			</table>
	</div>
	<div title="历史记录">
		<table style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="center" style="width: 25%">
					<label class="Validform_label">
						处理日期
					</label>
				</td>
				<td align="center" style="width: 15%">
					<label class="Validform_label">
						处理状态
					</label>
				</td>
				<td align="center" style="width: 15%">
					<label class="Validform_label">
						处理人
					</label>
				</td>
				<td align="center" style="width: 45%">
					<label class="Validform_label">
						处理说明
					</label>
				</td>
			</tr>
			<c:forEach var="hist" items="${histList}">
				<tr>
					<td class="value">
						${hist.createDate}
					</td>
					<td class="value">
						${hist.planStatusDesc}
					</td>
					<td class="value">
						${hist.createName}
					</td>
					<td class="value">
						${hist.histDesc}
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
</t:formvalid>
</body>
