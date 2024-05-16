<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>预警等级颜色</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
	 <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
	 <script type="text/javascript" src="plug-in/minicolors/jquery.minicolors.min.js"></script>
	 <link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
	 <script type="text/javascript">
		 //编写自定义JS代码
		 $(function(){
			 $("#alertLevelColor").minicolors({
				 control: $(this).attr('data-control') || 'hue',
				 defaultValue: $(this).attr('data-defaultValue') || '',
				 //	inline: $(this).attr('data-inline') === 'true',
				 letterCase:'lowercase',
				 //	opacity: $(this).attr('data-opacity'),
				 position:'bottom left',
				 change: function(hex, opacity) {
					 var log;
					 try {
						 log = hex ? hex : 'transparent';
						 if( opacity ) log += ', ' + opacity;
					 } catch(e) {}
				 },
				 theme: 'default'
			 });

			 $("#alertLevelName").Validform({
				 tiptype:3,
				 datatype : {
					 alertLevel:function(gets,obj,curform,regxp){
						 if("${tBAlertLevelColorPage.alertLevelName}" != gets){
							 if(gets == null || gets.length <=0){
								 return false;
							 }else{
								 //唯一校验
								 var isVal = false;
								 var errMsg = "";
								 $.ajax({
									 url : 'tBAlertLevelColorController.do?checkAlertLevel',
									 type : 'post',
									 async: false,
									 data : {
										 alertLevel:gets
									 },
									 success : function(data) {
										 var d = $.parseJSON(data);
										 isVal = d.success ;
										 errMsg = d.message;
									 },
									 error:function(){
									 }
								 });
								 return isVal;
							 }
						 }else{
							 return true;
						 }
					 },
					 message: '预警等级已经存在'
				 }
			 });
		 });
	 </script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBAlertLevelColorController.do?doUpdate">
			<input id="id" name="id" type="hidden" value="${tBAlertLevelColorPage.id }">
			<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							预警等级:
						</label>
					</td>
					<td class="value">
						<%--<input class="inputxt" id="alertLevelName" name="alertLevelName" value="${tBAlertLevelColorPage.alertLevelName}" datatype="*">--%>
						<select name="alertLevelName" id="alertLevelName" width="150" datatype="alertLevel" errormsg="该预警等级已存在">
							<option value="">---请选择---</option>
							<option value="A">A</option>
							<option value="B">B</option>
							<option value="C">C</option>
							<option value="DE">DE</option>
						</select>
						<script>
							$("#alertLevelName").val("${tBAlertLevelColorPage.alertLevelName}");
						</script>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">预警等级</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							预警颜色:
						</label>
					</td>
					<td class="value">
						<input id="alertLevelColor" name="alertLevelColor" type="text" style="width: 124px;padding-left:26px;" class="inputxt" datatype="*" value='${tBAlertLevelColorPage.alertLevelColor}'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">预警颜色</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>