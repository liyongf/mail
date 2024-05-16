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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hazardFactorsController.do?doUpdate" >
		<input id="id" name="id" type="hidden" value="${hazardFactorsPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>风险类型:
					</label>
				</td>
				<td class="value">
					<t:dictSelect field="riskType" type="list" datatype="*"
								  typeGroupCode="risk_type" defaultVal="${hazardFactorsPage.riskType}" hasLabel="false"  title="风险类型"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">风险类型</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>专业:
					</label>
				</td>
				<td class="value">
					<t:dictSelect field="major" type="list" datatype="*"
								  typeGroupCode="major" defaultVal="${hazardFactorsPage.major}" hasLabel="false"  title="专业"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">专业</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						设备:
					</label>
				</td>
				<td class="value">
					<input id="equipment" name="equipment" type="text" style="width: 150px" value="${hazardFactorsPage.equipment}" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">设备</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						作业活动:
					</label>
				</td>
				<td class="value">
					<input id="activity" name="activity" type="text" style="width: 150px" value="${hazardFactorsPage.activity}" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">作业活动</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>危害因素等级:
					</label>
				</td>
				<td class="value">
					<t:dictSelect field="riskLevel" type="list" datatype="*"
								  typeGroupCode="factors_level" defaultVal="${hazardFactorsPage.riskLevel}" hasLabel="false"  title="危害因素等级"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">危害因素等级</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>岗位:
					</label>
				</td>
				<td class="value">
					<input id="postName" name="postName" type="text" style="width: 150px" class="inputxt" datatype="*" value="${hazardFactorsPage.postName}" >
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">岗位</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>危害因素:
					</label>
				</td>
				<td class="value">
					<textarea  id="hazardFactors" name="hazardFactors" cols="6" style="width: 80%" datatype="*2-200">${hazardFactorsPage.hazardFactors}</textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">危害因素</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>管控措施:
					</label>
				</td>
				<td class="value">
					<textarea  id="manageMeasure" name="manageMeasure" cols="6" style="width: 80%" datatype="*2-200">${hazardFactorsPage.manageMeasure}</textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">管控措施</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						管控标准来源:
					</label>
				</td>
				<td class="value">
					<input id="docSource" name="docSource" type="text" style="width: 150px" value="${hazardFactorsPage.docSource}"/>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">管控标准来源</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						章节条款:
					</label>
				</td>
				<td class="value">
					<input id="sectionName" name="sectionName" type="text" style="width: 150px" value="${hazardFactorsPage.sectionName}"/>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">章节条款</label>
				</td>
			</tr>
			</table>
		</t:formvalid>
 </body>
