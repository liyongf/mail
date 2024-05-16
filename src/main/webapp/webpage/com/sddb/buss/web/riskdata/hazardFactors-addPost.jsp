<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>危害因素</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
	 <script type="text/javascript">
         function refresh(){
             var taskManagePostId =  $("#taskManagePostId").val();
             if(taskManagePostId!=''&&taskManagePostId!=null&&taskManagePostId!=undefined){
                 window.top.reload_riskManagePostResultList.call();
			 }
             var riskId =  $("#riskId").val();
             if(riskId!=''&&riskId!=null&&riskId!=undefined){
                 window.top.reload_whysPostList.call();
             }
             frameElement.api.opener.reloadTable();
             frameElement.api.close();

         }

	 </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="hazardFactorsController.do?doAddPost" callback="@Override refresh">
		<input id="id" name="id" type="hidden" value="${hazardFactorsPostEntityPage.id }"/>
	  <input id="taskManagePostId" name="taskManagePostId" type="hidden" value="${taskManagePostId }"/>
	  <input id="riskId" name="riskId" type="hidden" value="${riskId }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>危害因素:
					</label>
				</td>
				<td class="value">
					<textarea  id="hazardFactors" name="hazardFactors" cols="6" style="width: 80%"  datatype="*2-200">${hazardFactorsPostEntityPage.hazardFactors}</textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">危害因素</label>
				</td>
			</tr>
			</table>
		</t:formvalid>
 </body>
