<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title><t:mutiLang langKey="lang.maintain"/></title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="mutiLangController.do?save">
			<input id="id" name="id" type="hidden" value="${mutiLangPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							<t:mutiLang langKey="common.languagekey"/>:
						</label>
					</td>
					<td class="value">
						<label>${mutiLangPage.langKey}</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<t:mutiLang langKey="common.content"/>:
						</label>
					</td>
					<td class="value">
						<label>${mutiLangPage.langContext}</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<t:mutiLang langKey="common.language"/>:
						</label>
					</td>
					<td class="value">
						<label>${mutiLangPage.langCode}</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>