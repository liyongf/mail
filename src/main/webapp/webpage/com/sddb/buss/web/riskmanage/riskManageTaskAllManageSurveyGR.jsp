<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>危害因素</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script type="text/javascript">
        //编写自定义JS代码
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskManageTaskController.do?doUpdate" >
	<input id="id" name="id" type="hidden" value="${riskManageTaskAllPage.id }"/>
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					<b style="color: red">*</b>管控时间:
				</label>
			</td>
			<td class="value" colspan="3">
				<fmt:formatDate value='${riskManageTaskAllPage.manageTime}' type="date" pattern="yyyy-MM-dd"/>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					管控类型:
				</label>
			</td>
			<td class="value">
					${taskManageType}
				<span class="Validform_checktip"></span>
			</td>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>管控班次:
				</label>
			</td>
			<td class="value">
					${manageShift}
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					备注:
				</label>
			</td>
			<td class="value" colspan="3">
				${riskManageTaskAllPage.remark}
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">备注</label>
			</td>
		</tr>
		<c:if test="${manageTypeTemp eq '1'}">
			<tr>
				<td align="right">
					<label class="Validform_label">
						检查地点:
					</label>
				</td>
				<td class="value" >
					${addressName}
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">
						检查人:
					</label>
				</td>
				<td class="value">
						${riskManageTaskAllPage.createName}
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						任务概况
					</label>
				</td>
				<td class="value" colspan="3">
					本次任务共发现风险${riskCount}条，隐患${hdCount}条,危害因素落实${implCount}条
					<span class="Validform_checktip"></span>
				</td>
			</tr>
		</c:if>
		<c:if test="${manageTypeTemp eq '2'}">
			<tr>
				<td align="right">
					<label class="Validform_label">
						检查岗位:
					</label>
				</td>
				<td class="value">
						${postName}
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">
						检查人:
					</label>
				</td>
				<td class="value">
						${riskManageTaskAllPage.createName}
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						任务概况
					</label>
				</td>
				<td class="value" colspan="3">
					本次任务共发现风险${riskCount}条，隐患${hdCount}条,危害因素落实${implCount}条
					<span class="Validform_checktip"></span>
				</td>
			</tr>
		</c:if>
		<c:if test="${manageTypeTemp eq '3'}">
			<tr>
				<td align="right">
					<label class="Validform_label">
						检查人:
					</label>
				</td>
				<td class="value" colspan="3">
						${riskManageTaskAllPage.createName}
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						任务概况
					</label>
				</td>
				<td class="value" colspan="3">
					本次任务共发现风险${riskCount}条，隐患${hdCount}条
					<span class="Validform_checktip"></span>
				</td>
			</tr>
		</c:if>

	</table>
</t:formvalid>
</body>
