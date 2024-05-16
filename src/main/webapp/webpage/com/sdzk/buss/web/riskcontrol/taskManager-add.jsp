<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>三违信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
  })
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskController.do?doAddTaskManager" >
		<input id="id" name="id" type="hidden" value="${taskManagerControl.id }"/>
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>排查时间:
						</label>
					</td>
					<td class="value">
							   <input id="inveDate" name="inveDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()"
									  datatype="*"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">排查时间</label>
						</td>
				</tr>
			<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>排查类别:
						</label>
					</td>
					<td class="value">
						<t:dictSelect id="inveType" field="inveType" defaultVal="" typeGroupCode="investigatePlan_type" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">排查类别</label>
						</td>
				</tr>

			</table>
		</t:formvalid>
 </body>
