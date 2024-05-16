<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>风险点克隆</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBAddressInfoController.do?doClone" >
					<input id="id" name="id" type="hidden" value="">
                    <input id="idOld" name = "idOld" type="hidden" value="${tBAddressInfoPage.id}">
		<table style="width: 90%;text-align: center" cellpadding="0" cellspacing="1" class="formtable">
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        被克隆风险点名称:
                    </label>
                </td>
                <td class="value" style="width:60%">
                    ${tBAddressInfoPage.address}
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <b style="color: red">*</b>新风险点名称:
                    </label>
                </td>
                <td class="value">
                    <input id="address" name="address" type="text" style="width: 150px" class="inputxt"
                           datatype="*" ajaxurl="tBAddressInfoController.do?addressExists"
                           value=''>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险点名称</label>
                </td>
            </tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/address/tBAddressInfo.js"></script>		
