<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>重大风险管控记录</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
	  $(function(){
		  getUserMagicSuggest($("#controllerSelect"), $("#controller"));
	  })
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBDsManageRecordController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${tBDsManageRecordPage.id }"/>
		<input id="dangerId" name="dangerId" type="hidden" value="${tBDsManageRecordPage.dangerId }"/>
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>管控人:
						</label>
					</td>
					<td class="value">
						<div id="controllerSelect" style="width: 130px;height: 15px"></div>
					     	 <input id="controller" name="controller" type="hidden" style="width: 150px" class="inputxt"
									datatype="*"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">管控人</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>管控时间:
						</label>
					</td>
					<td class="value">
							   <input id="controlleDate" name="controlleDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})"
									  datatype="*"
					      						/>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">管控时间</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>工作内容:
						</label>
					</td>
					<td class="value">
							<textarea id="workContent" name="workContent" rows="6" datatype="*" class="inputxt" style="height: auto;width: 80%;" ></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">工作内容</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>取得效果:
						</label>
					</td>
					<td class="value">
						<textarea id="achieveEffect" name="achieveEffect" rows="6"  datatype="*" class="inputxt" style="height: auto;width: 80%" ></textarea>
						<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">取得效果</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/dsManageRecord/tBDsManageRecord.js"></script>		
