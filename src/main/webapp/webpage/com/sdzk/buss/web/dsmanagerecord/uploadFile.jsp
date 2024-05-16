<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>重大风险管控方案</title>
<t:base type="jquery,easyui,tools"></t:base>

<script type="text/javascript">
	function uploadFile(){
		if($("#filediv>div").length>0){
			return upload();
		} else {
			tip("请先上传重大风险管控方案");
			return false;
		}
	}
	function doDelete(attachmentId, url, paramsData){
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			data : paramsData,
			url : url,// 请求的action路径
			error : function() {// 请求失败处理函数
			},
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					var msg = d.msg;
					tip(msg);
					$("#"+attachmentId).remove();
				}
			}
		});
	}
	function delAttachment(attachmentId, attachmentName) {
		var title="确定删除";
		var content="'确定删除["+attachmentName+"]吗?";
		var url="tBDsManageRecordController.do?doDelFile&id="+attachmentId;
		var paramsData = "[id:'"+attachmentId+"']";
		$.dialog.setting.zIndex = getzIndex(true);

		var navigatorName = "Microsoft Internet Explorer";
		if( navigator.appName == navigatorName ){
			$.dialog.confirm(content, function(){
				doDelete(attachmentId, url,paramsData);
				rowid = '';
			}, function(){
			});
		}else{
			layer.open({
				title:title,
				content:content,
				icon:7,
				yes:function(index){
					doDelete(attachmentId, url,paramsData);
					rowid = '';
				},
				btn:['确定','取消'],
				btn2:function(index){
					layer.close(index);
				}
			});
		}

	}
</script>

</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="table" dialog="true" beforeSubmit="uploadFile">
	<input name="bussId" id="bussId" type="hidden" value="${bussId}">
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" style="width: 70px;">
				<label class="Validform_label">管控方案:
				</label>
			</td>
			<td  class="value" style="text-align: left">

				<table style="width:100%;">
					<c:forEach items="${list}" var="tsAttachment">
						<tr id="${tsAttachment.id}">
							<td style="width:80%;"><a href="commonController.do?viewFile&fileid=${tsAttachment.id}">${tsAttachment.attachmenttitle}</a></td>
							<td><a onclick="delAttachment('${tsAttachment.id}','${tsAttachment.attachmenttitle}');">删除</a></td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right" style="width: 70px;">
				<label class="Validform_label">上传方案:
				</label>
			</td>
			<td  class="value">
				<t:upload name="fiels" buttonText="浏览..." uploader="tBDsManageRecordController.do;jsessionid=${pageContext.session.id}?doAddFiels" multi="true" extend="office" id="file_upload" formData="bussId,jsessionid"></t:upload>
				<div id="filediv" style="min-height: 50px;"></div>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>
</html>
