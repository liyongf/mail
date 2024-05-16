<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<title>灯和风险点对应信息</title>

<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBLightAddressController.do?doUpdate" >
	<input id="id" name="id" type="hidden" value="${tBLightAddressPage.id }">
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					<b style="color: red">*</b>灯标识:
				</label>
			</td>
			<td class="value">
				${tBLightAddressPage.lightId}
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					<b style="color: red">*</b>风险点:
				</label>
			</td>
			<td class="value">
				${tBLightAddressPage.addressName}
			</td>

		</tr>
	</table>
</t:formvalid>
