<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>短信记录</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBSmsController.do?save">
			<input id="id" name="id" type="hidden" value="${tBSmsPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							手机号:
						</label>
					</td>
					<td class="value">
							${tBSmsPage.mobilePhone}
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							内容:
						</label>
					</td>
					<td class="value">
							${tBSmsPage.content}
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							类型:
						</label>
					</td>
					<td class="value">
						<c:if test="${tBSmsPage.type eq '1'}">隐患上报</c:if>
						<c:if test="${tBSmsPage.type eq '2'}">隐患整改</c:if>
						<c:if test="${tBSmsPage.type eq '3'}">隐患超期</c:if>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							日期
						</label>
					</td>
					<td class="value">
						<fmt:formatDate value='${tBSmsPage.createDate}' type="date" pattern="yyyy-MM-dd"/>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>