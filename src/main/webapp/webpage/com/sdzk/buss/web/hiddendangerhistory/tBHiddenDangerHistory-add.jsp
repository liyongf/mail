<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>隐患闭环流程处理历史</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBHiddenDangerHistoryController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${tBHiddenDangerHistoryPage.id }"/>
		<input id="fkHiddenInfoId" name="fkHiddenInfoId" type="hidden" value="${tBHiddenDangerHistoryPage.fkHiddenInfoId }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							处理时间:
						</label>
					</td>
					<td class="value">
							   <input id="dealTime" name="dealTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" 
					      						 datatype="*" 
					      						ignore="checked"
					      						/>    
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">处理时间</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							处理环节:
						</label>
					</td>
					<td class="value">
					     	 <input id="dealStep" name="dealStep" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">处理环节</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							说明:
						</label>
					</td>
					<td class="value">
					     	 <input id="dealDesc" name="dealDesc" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">说明</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							处理人名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="dealUserName" name="dealUserName" type="text" style="width: 150px" class="inputxt" 
					     	  
					     	  ignore="ignore"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">处理人名称</label>
						</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/hiddendangerhistory/tBHiddenDangerHistory.js"></script>		
