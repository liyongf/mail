<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>文档来源管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script>
	  $(function() {
		  $('#cc').combotree({
			  url : 'tBDocumentSourceController.do?setParentDocSource&selfId=${tBDocumentSourcePage.id}',
			  panelHeight: 200,
			  width: 157,
			  onLoadSuccess:function(){
				  $('#cc').combotree('tree').tree("expandAll");
				  if('${empty pid}' == 'false') { // 设置新增页面时的父级
					  $('#cc').combotree('setValue', '${pid}');
				  }
				  //$('#cc').combotree('tree').combotree('collapseAll');
			  }
		  });

		  if($("#id").val()) {
			  $('#cc').combotree('disable');
		  }


	  });
  </script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBDocumentSourceController.do?save">
			<input id="id" name="id" type="hidden" value="${tBDocumentSourcePage.id }">
	  		<input id="isDelete" name="isDelete" type="hidden" value="${tBDocumentSourcePage.isDelete eq '1' ? '1':'0' }">
			<table style="width: 500px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							文档来源名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="docSourceName" name="docSourceName" 
							   value="${tBDocumentSourcePage.docSourceName}" datatype="*">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							上级分类:
						</label>
					</td>
					<td class="value">
						<input id="cc" name="parentDocSource.id" value="${tBDocumentSourcePage.parentDocSource.id}" />
						<%--<input id="parentDocSourceId" name="parentDocSource.id" type="hidden" value="${tBDocumentSourcePage.parentDocSource.id}">--%>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<%--<c:if test="${tBDocumentSourcePage.childDocSources.size() eq '0' || empty tBDocumentSourcePage.id}">--%>
				<%--</c:if>--%>
			</table>
		</t:formvalid>
 </body>