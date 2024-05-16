<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>微信模板管理表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <style type="text/css">
  	.combo_self{height: 22px !important;width: 150px !important;}
  	.layout-header .btn {
	    margin:0;
	   float: none !important;
	}
	.btn-default {
	    height: 35px;
	    line-height: 35px;
	    font-size:14px;
	}
  </style>
  
  <script type="text/javascript">
	$(function(){
		$(".combo").removeClass("combo").addClass("combo combo_self");
		$(".combo").each(function(){
			$(this).parent().css("line-height","0px");
		});   
	});
  		
  		 /**树形列表数据转换**/
  function convertTreeData(rows, textField) {
      for(var i = 0; i < rows.length; i++) {
          var row = rows[i];
          row.text = row[textField];
          if(row.children) {
          	row.state = "open";
              convertTreeData(row.children, textField);
          }
      }
  }
  /**树形列表加入子元素**/
  function joinTreeChildren(arr1, arr2) {
      for(var i = 0; i < arr1.length; i++) {
          var row1 = arr1[i];
          for(var j = 0; j < arr2.length; j++) {
              if(row1.id == arr2[j].id) {
                  var children = arr2[j].children;
                  if(children) {
                      row1.children = children;
                  }
                  
              }
          }
      }
  }
  </script>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="wechatTemplateManagementController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${wechatTemplateManagementPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								父模块名称:
							</label>
						</td>
						<td class="value">
							<input id="parentModelName" name="parentModelName" type="text" style="width: 150px" class="inputxt easyui-combotree"  ignore="ignore" 
							value='${wechatTemplateManagementPage.parentModelName}'
							data-options="
				                    panelHeight:'220',
				                    url: 'wechatTemplateManagementController.do?datagrid&field=id,modelName',  
				                    loadFilter: function(data) {
				                    	var rows = data.rows || data;
				                    	var win = frameElement.api.opener;
				                    	var listRows = win.getDataGrid().treegrid('getData');
				                    	joinTreeChildren(rows, listRows);
				                    	convertTreeData(rows, 'modelName');
				                    	return rows; 
				                    },
				                    onSelect:function(node){
				                    	$('#parentModelName').val(node.id);
				                    },
				                    onLoadSuccess: function() {
				                    	var win = frameElement.api.opener;
				                    	var currRow = win.getDataGrid().treegrid('getSelected');
				                    	if(!'${wechatTemplateManagementPage.id}') {
				                    		//增加时，选择当前父菜单
				                    		if(currRow) {
				                    			$('#parentModelName').combotree('setValue', currRow.id);
				                    		}
				                    	}else {
				                    		//编辑时，选择当前父菜单
				                    		if(currRow) {
				                    			$('#parentModelName').combotree('setValue', currRow.parentModelName);
				                    		}
				                    	}
				                    }
				            "
							>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">父模块名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								模块名称:
							</label>
						</td>
						<td class="value">
						    <input id="modelName" name="modelName" type="text" style="width: 150px" class="inputxt"  ignore="ignore"  value='${wechatTemplateManagementPage.modelName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模块名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								模块类型:
							</label>
						</td>
						<td class="value">
            			<t:dictSelect field="modelType" type="list"
                              typeGroupCode="wechat_model_type" hasLabel="false"  title="统计表类型" defaultVal="${wechatTemplateManagementPage.modelType}"></t:dictSelect>
	                    <span class="Validform_checktip">请选择类型</span>
	                    <label class="Validform_label" style="display: none;">模块类型</label>
            			</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								模块内容:
							</label>
						</td>
						<td class="value" >
						  	 	<textarea id="modelContent" style="width:600px;" class="inputxt" rows="4" name="modelContent"  ignore="ignore" >${wechatTemplateManagementPage.modelContent}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">模块内容</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/jeecg/wechattemplatemanagement/wechatTemplateManagement.js"></script>		
