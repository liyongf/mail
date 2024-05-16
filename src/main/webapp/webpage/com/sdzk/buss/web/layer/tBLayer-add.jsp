<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>超图图层</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBLayerController.do?doAdd" >
					<input id="id" name="id" type="hidden" value="${tBLayerPage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<%--<tr>
					<td align="right">
						<label class="Validform_label">
							图层名称:
						</label>
					</td>
					<td class="value">
							<t:dictSelect field="layerCode" type="list" extendJson="{\"datatype\":\"*\"}"
										  typeGroupCode="layer" hasLabel="false"  title="图层名称"></t:dictSelect>
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
					     	 <input id="layerDetailName" name="layerDetailName" type="text" style="width: 300px" datatype="*" class="inputxt"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">矿图名称</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>超图路径:
						</label>
					</td>
					<td class="value">
					     	 <input id="url" name="url" type="text" style="width: 300px" class="inputxt"  datatype="*" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">超图路径</label>
						</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>超图中心点:
						</label>
					</td>
					<td class="value">
						<input id="center" name="center" type="text" style="width: 300px" class="inputxt"  datatype="*" />
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">超图中心点</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							图层是否显示:
						</label>
					</td>
					<td class="value">
							<t:dictSelect field="isShow" type="list" extendJson="{\"datatype\":\"*\"}"
										  typeGroupCode="sf_yn" defaultVal="Y" hasLabel="false"  title="是否显示"></t:dictSelect>
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
							<textarea id="remark" name="remark" ignore="ignore" rows="5" style="width: 300px;"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
				</tr>
				
				
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/layer/tBLayer.js"></script>		
