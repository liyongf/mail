<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>APP菜单管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function() {
      $('#cc').combotree({
          url : 'tSAppFunctionController.do?setParentFunctionname&selfId=${TSAppFunctionEntity.id}',
          width: 155,
          onSelect : function(node) {

          }
      });

      if($("#id").val()) {
          $('#cc').combotree('disable');
      }
      if('${empty pid}' == 'false') { // 设置新增页面时的父级
          $('#cc').combotree('setValue', '${pid}');
      }
  });

  $(function() {
      $('#cc').combotree({
          url : 'tSAppFunctionController.do?setParentFunctionname&selfId=${TSAppFunctionEntity.id}',
          panelHeight: 200,
          width: 157,
          onClick: function(node){
              $("#tSAppFunctionEntityId").val(node.id);
          }
      });

  });
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tSAppFunctionController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${tSAppFunctionPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tSAppFunctionPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tSAppFunctionPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tSAppFunctionPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tSAppFunctionPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tSAppFunctionPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tSAppFunctionPage.updateDate }">
					<input id="sysOrgCode" name="sysOrgCode" type="hidden" value="${tSAppFunctionPage.sysOrgCode }">
					<input id="sysCompanyCode" name="sysCompanyCode" type="hidden" value="${tSAppFunctionPage.sysCompanyCode }">
					<input id="bpmStatus" name="bpmStatus" type="hidden" value="${tSAppFunctionPage.bpmStatus }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						菜单名称:
					</label>
				</td>
				<td class="value">
					<input id="functionname" name="functionname" type="text" style="width: 150px" class="inputxt"
						   ignore="ignore"
						   value='${tSAppFunctionPage.functionname}'>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">菜单名称</label>
				</td>
			</tr>
			<tr>
						<td align="right">
							<label class="Validform_label">
								菜单等级:
							</label>
						</td>
						<td class="value">
							<select name="functionlevel" id="functionlevel" datatype="*">
								<option value="0" <c:if test="${tSAppFunctionPage.functionlevel eq 0}">selected="selected"</c:if>>
									<t:mutiLang langKey="main.function"/>
								</option>
								<option value="1" <c:if test="${tSAppFunctionPage.functionlevel>0}"> selected="selected"</c:if>>
									<t:mutiLang langKey="sub.function"/>
								</option>
							</select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">菜单等级</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								菜单顺序:
							</label>
						</td>
						<td class="value">
						     	 <input id="functionorder" name="functionorder" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${tSAppFunctionPage.functionorder}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">菜单顺序</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								菜单编码:
							</label>
						</td>
						<td class="value">
						     	 <input id="functioncode" name="functioncode" type="text" style="width: 150px" class="inputxt"  
						     	 ignore="ignore" 
						     	 value='${tSAppFunctionPage.functioncode}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">菜单编码</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								上级菜单:
							</label>
						</td>
						<td class="value">
							<input id="cc"  value="${tSAppFunctionPage.TSAppFunctionEntity.functionname}">
							<input id="tSAppFunctionEntityId" name="TSAppFunctionEntity.id" style="display: none;" value="${tSAppFunctionPage.TSAppFunctionEntity.id}">
						</td>
					</tr>

			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/tsappfunction/tSAppFunction.js"></script>		
