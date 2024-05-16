<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>职工劳动防护专项档案</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
     <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
     <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
     <script type="text/javascript">
         //编写自定义JS代码
     </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBLaborProtectController.do?doUpdate" tiptype="3">
					<input id="id" name="id" type="hidden" value="${tBLaborProtectPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tBLaborProtectPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tBLaborProtectPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tBLaborProtectPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tBLaborProtectPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tBLaborProtectPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tBLaborProtectPage.updateDate }">
					<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								车间名称:
							</label>
						</td>
						<td class="value">
                            <label class="Validform_label">${tBLaborProtectPage.unitName}</label>
                            <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">单位名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								接触职业病危害因素:
							</label>
						</td>
						<td class="value">
                            <label class="Validform_label">${tBLaborProtectPage.hazardsFactor}</label>
                            <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">接触职业病危害因素</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								个人防护用品名称:
							</label>
						</td>
						<td class="value">
                            <label class="Validform_label">${tBLaborProtectPage.protectEquipment}</label>
                            <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">个人防护用品名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								型号:
							</label>
						</td>
						<td class="value">
                            <label class="Validform_label">${tBLaborProtectPage.model}</label>
                            <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">型号</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								数量:
							</label>
						</td>
						<td class="value">
                            <label class="Validform_label">${tBLaborProtectPage.amount}</label>
                            <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">数量</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								领取人:
							</label>
						</td>
						<td class="value">
                            <label class="Validform_label">${tBLaborProtectPage.tBEmployeeInfoEntity.name}</label>
                            <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">领取人</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								领取日期:
							</label>
						</td>
						<td class="value">
                            <fmt:formatDate value='${tBLaborProtectPage.receiveDate}' type="date" pattern="yyyy-MM-dd"/>
                            <span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">领取日期</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/health/js/tBLaborProtect.js"></script>