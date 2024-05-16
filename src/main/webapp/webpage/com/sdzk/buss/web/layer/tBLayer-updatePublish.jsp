<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>矿图图层</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
	  //send to local server to save
	  function uploadToLocalServer(){
		  var formData = new FormData($('#formobj')[0]);
		  if(document.getElementById("dwgFile").files.length > 0){
			  formData.append("dwgFile", document.getElementById("dwgFile").files[0]);
		  }

		  $.ajax({
			  url: $("#formobj").attr('action'),
			  type: 'post',
			  data: formData,
			  contentType: false,
			  processData: false,
			  async: false,
			  success: function (data) {
				  var d = $.parseJSON(data);
				  frameElement.api.opener.tip(d.msg);
				  frameElement.api.opener.reloadTable();
				  frameElement.api.close();
			  },
			  error:function(){
				  frameElement.api.opener.tip("保存本地失败");
				  frameElement.api.opener.reloadTable();
				  frameElement.api.close();
			  }
		  });
	  }
	  function uploadToSmServer(){
		  var flag = true;
		  //如果没有文件上传，则直接提交；
		  if(document.getElementById("dwgFile").files.length == 0){
			  return true;
		  }
		  //send to remote supermap server to publish
		  var formData = new FormData($('#formobj')[0]);
		  formData.append("dwgFile", document.getElementById("dwgFile").files[0]);
		  formData.append("layerId", formData.get("id"));
		  formData.delete("id");
		  $.ajax({
			  url: "http://${smHandleServer}:${smHandlePort}/supermap/smController/updateService.do",
			  type: 'post',
			  data: formData,
			  contentType: false,
			  processData: false,
			  async: false,
              beforeSend: function(data){
                  $.messager.progress({
                      text : "正在上传矿图，请耐心等待，不要关闭页面......",
                      interval : 100
                  });
              },
			  success: function (data) {
				  var d = $.parseJSON(data);
				  if (d.success) {
					  var info = d.obj;
					  $("#url").val(info.smUrl);
					  $("#center").val(info.smCenter);
					  uploadToLocalServer();
					  flag = false;
				  }else {
					  console.error(d.msg);
//					  frameElement.api.opener.tip(d.msg);
//					  frameElement.api.opener.reloadTable();
//					  frameElement.api.close();

					  tip(d.msg);
                      $.messager.progress('close');
					  flag = false;
				  }
			  },
			  error:function(XMLHttpRequest, textStatus, errorThrown){
				  console.error('矿图上传异常');
//				  frameElement.api.opener.tip("矿图上传异常");
//				  frameElement.api.opener.reloadTable();
//				  frameElement.api.close();

				  tip("矿图上传异常");
                  $.messager.progress('close');
                  flag = false;
			  }
		  });

		  return flag;
	  }
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBLayerController.do?doUpdatePublish" beforeSubmit="uploadToSmServer();">
			<input id="serverCert" name="serverCert" type="hidden" value="${serverCert}"/>
			<input id="id" name="id" type="hidden" value="${tBLayerPage.id }"/>
			<input id="mineName" name="mineName" type="hidden" value="${mineName }"/>
			<input id="smToken" name="smToken" type="hidden" value="${smToken }"/>
			<input id="operatorUsername" name="operatorUsername" type="hidden" value="${operatorUsername }"/>
			<input id="operatorRealname" name="operatorRealname" type="hidden" value="${operatorRealname }"/>
			<input id="url" name="url" type="hidden" value="${tBLayerPage.url }"/>
			<input id="center" name="center" type="hidden" value="${tBLayerPage.center }"/>
			<input id="oldLayerName" name="oldLayerName" type="hidden" value="${tBLayerPage.layerDetailName}"/>

			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<%--<tr>
						<td align="right">
							<label class="Validform_label">
								图层名称:
							</label>
						</td>
						<td class="value">
							<t:dictSelect field="layerCode" type="list" extendJson="{\"datatype\":\"*\"}"
										  typeGroupCode="layer" hasLabel="false"  title="图层名称" defaultVal="${tBLayerPage.layerCode}"></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">图层名称</label>
						</td>
					</tr>--%>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<font color="red">*</font>矿图名称:
							</label>
						</td>
						<td class="value">
						    <input id="layerDetailName" name="layerDetailName" type="text" style="width: 300px" class="inputxt"  datatype="*"  value='${tBLayerPage.layerDetailName}'/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">矿图名称</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<font color="red">*</font>图层是否显示:
							</label>
						</td>
						<td class="value">
							<t:dictSelect field="isShow" type="list" extendJson="{\"datatype\":\"*\"}"
										  typeGroupCode="sf_yn" defaultVal="${tBLayerPage.isShow}" hasLabel="false"  title="是否显示"></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">图层是否显示</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
							<textarea id="remark" name="remark" ignore="ignore" rows="5" style="width: 300px;">${tBLayerPage.remark}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								上传矿图:
							</label>
						</td>
						<td class="value" colspan="3">
							<input type="file" name="dwgFile" id="dwgFile" />
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/layer/tBLayer.js"></script>		
