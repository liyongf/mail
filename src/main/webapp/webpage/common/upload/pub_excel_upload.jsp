<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>通用Excel导入${controller_name}</title>
	<t:base type="jquery,easyui,tools,layui"></t:base>
	<script type="text/javascript">
		$(function () {
			var attachmentFile=$("#fileName").val();
			if(attachmentFile=="" || attachmentFile == undefined){
				var tr = $(['<tr id="upload-'+ 0 +'">'
					,'<td>'+ "" +'</td>'
					,'<td></td>'
					,'</tr>'].join(''));
				$('#fileTable').html(tr);
			}else {
				var tr = $(['<tr id="upload-'+ 0 +'">'
					,'<td>'+ attachmentFile +'</td>'
					,'<td><span style="color: #5FB878;">上传成功</span></td>'
					,'</tr>'].join(''));
				$('#fileTable').html(tr);
			}

		});
		$(function(){
			$("#validatediv").hide();
			$("#fileName").val("");
		});
		// 使用layui.upload.render()方法初始化上传组件
		layui.use('upload', function(){
			var upload = layui.upload;

			// 执行实例
			var fileTable = $('#fileTable')	,uploadInst = upload.render({
				elem: '#uploadSuccess', // 绑定上传按钮
				url: "${controller_name}.do;jsessionid=${pageContext.session.id}?${empty function_name?'importExcel':function_name}", // 上传接口
				acceptMime: '.xls,.xlsx',
				exts: 'xls|xlsx', //只允许上传excel文件
				choose: function(obj){
					// var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
					//读取本地文件
					obj.preview(function(index, file, result){
						var tr = $(['<tr id="upload-'+ index +'">'
							,'<td>'+ file.name +'</td>'
							,'<td>等待上传</td>'
							,'</tr>'].join(''));
						fileTable.html(tr);
					});
				},
				done: function(res,index){
					if(res.success){
						// 上传完毕回调
						$("#validAId").hide();
						layer.msg(res.msg, {icon: 1});

						var tr = fileTable.find('tr#upload-'+ index),tds = tr.children();
						tds.eq(1).html('<span style="color: #5FB878;">上传成功</span>');
						return delete this.files[index];
					}else{
						// 请求异常回调
						if(res.attributes != null){
							$("#validAId").attr("href",res.attributes.path);
							$("#validatediv").show();;
						}
						layer.msg(res.msg, {icon: 2});

						var tr = fileTable.find('tr#upload-'+ index),tds = tr.children();
						tds.eq(1).html('<span style="color: #FF5722;">上传失败</span>');
						$("#attachmentId").val("");
					}
				},
				error: function(index){
					var tr = fileTable.find('tr#upload-'+ index),tds = tr.children();
					tds.eq(1).html('<span style="color: #FF5722;">上传失败</span>');
					$("#attachmentId").val("");
				}
			});
		});
	</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="upload">
	<fieldset class="step">
		<div class="form"><button type="button" style="float: left; font-size: 16px" id="uploadSuccess">选择要导入的文件</button>
		</div>
		<div class="form">
			<div class="layui-upload-list">
				<table class="layui-table">
					<thead>
					<tr>
						<th>文件名</th>
						<th>状态</th>
					</tr>
					</thead>
					<tbody id="fileTable"></tbody>
				</table>
			</div>
		</div>
		<div class="form" id="validatediv" style="height: 50px">
			<div class="uploadify-queue-item" id="SWFUpload_0_2">
				<a id="validAId" href="#"><span class="fileName" style="color:red;">数据校验文件下载</span></a>
			</div>
		</div>
	</fieldset>
</t:formvalid>
</body>
</html>