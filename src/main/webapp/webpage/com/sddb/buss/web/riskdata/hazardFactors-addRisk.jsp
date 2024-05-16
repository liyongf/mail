<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>危害因素</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
	 <script type="text/javascript">
         var magicsuggestPostNameSelSelected = "";
         $(function() {
             magicsuggestPostNameSelSelected = $('#magicsuggestPostName').magicSuggest({
                 data: 'magicSelectController.do?getPostName',
                 allowFreeEntries: true,
                 valueField: 'post_name',
                 placeholder: '输入或选择',
                 maxSelection: 1,
                 selectFirst: true,
                 highlight: false,
                 displayField: 'post_name'
             });
             $(magicsuggestPostNameSelSelected).on('selectionchange', function (e, m) {
                 $("#postName").val(magicsuggestPostNameSelSelected.getValue());
             });

			 var modifySelect = getUserMagicSuggestWithValue($("#modifySelect"), $("#reviewMan"), "", false);
         });

         function close() {
			window.close();
         }

         function save() {
             $("#btn").trigger("click");
             window.top.reload_tBDangerSourceList.call();
         }
	 </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hazardFactorsController.do?doAdd&status=3" btnsub="btn">
		<input id="id" name="id" type="hidden" value="${hazardFactorsEntityPage.id }"/>
	  <input id="riskId" name="riskId" type="hidden" value="${riskId }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>风险类型:
					</label>
				</td>
				<td class="value">
					<t:dictSelect field="riskType" type="list" datatype="*"
								  typeGroupCode="risk_type" defaultVal="${hazardFactorsEntityPage.riskType}" hasLabel="false"  title="风险类型"></t:dictSelect>
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
								  typeGroupCode="major" defaultVal="${hazardFactorsEntityPage.major}" hasLabel="false"  title="专业"></t:dictSelect>
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
					<input id="equipment" name="equipment" type="text" style="width: 150px" />
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
					<input id="activity" name="activity" type="text" style="width: 150px" />
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
								  typeGroupCode="factors_level" defaultVal="${hazardFactorsEntityPage.riskLevel}" hasLabel="false"  title="危害因素等级"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">危害因素等级</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<font color="red">*</font>岗位:
					</label>
				</td>
				<td class="value">
					<div id="magicsuggestPostName" style="width: 130px;height: 15px"></div>
					<input type="hidden" name="postName" id="postName" value="" style="width: 150px" class="inputxt" datatype="*" >
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">岗位</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						管控标准来源:
					</label>
				</td>
				<td class="value">
					<input id="docSource" name="docSource" type="text" style="width: 150px"/>
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
					<input id="sectionName" name="sectionName" type="text" style="width: 150px" />
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">章节条款</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>危害因素:
					</label>
				</td>
				<td class="value">
					<textarea  id="hazardFactors" name="hazardFactors" cols="6" style="width: 80%"  datatype="*2-200">${hazardFactorsEntityPage.hazardFactors}</textarea>
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
					<textarea  id="manageMeasure" name="manageMeasure" cols="6" style="width: 80%" datatype="*2-200">${hazardFactorsEntityPage.manageMeasure}</textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">管控措施</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>审批人:
					</label>
				</td>
				<td class="value">
					<div id="modifySelect" style="width: 130px;height: 15px;" class="inputxt"></div>
					<input id="reviewMan" name="reviewMan" type="hidden"
						   datatype="*"
						   value=''>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">审批人</label>
				</td>
			</tr>
			<tr>
				<td class="value" colspan="4">
					<div class="ui_buttons" style="text-align: right;">
						<input type="button" id="btn" value="保存" class="ui_state_highlight" style="display:none">
						<input type="button" onclick="save();" class="ui_state_highlight" value="保存并关联">
					</div>
				</td>
			</tr>
			</table>
		</t:formvalid>
 </body>
