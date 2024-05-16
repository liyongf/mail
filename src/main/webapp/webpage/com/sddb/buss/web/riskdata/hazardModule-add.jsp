<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>危害因素</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
      function moduleNameExists() {
          $("#moduleName").attr("ajaxurl","hazardFactorsController.do?moduleNameExists&riskType="+$("select[name='riskType']").val());
      }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hazardFactorsController.do?doAddModule" >
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>风险类型:
					</label>
				</td>
				<td class="value">
					<t:dictSelect field="riskType" type="list" datatype="*"
								  typeGroupCode="risk_type"  hasLabel="false"  title="风险类型" extendJson="{onclick:'moduleNameExists()'}"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">风险类型</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>模块名称:
					</label>
				</td>
				<td class="value">
					<input id="moduleName" name="moduleName" type="text" style="width: 150px" class="inputxt" datatype="*">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">模块名称</label>
				</td>
			</tr>
			</table>
		</t:formvalid>
 </body>
