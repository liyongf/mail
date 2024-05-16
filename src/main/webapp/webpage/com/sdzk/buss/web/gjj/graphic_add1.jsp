<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<t:base type="jquery,layui"></t:base>
<style>
	.tip1{
		width: 90px;!important;
	}

	.tip2 {
		margin-left: 120px;!important;
	}
</style>
<body style="background-color: #ffffff;">
<div class="layuimini-container" style="border: 0px">
	<div class="layuimini-main">
		<form class="layui-form" action="" id="form" lay-filter="example">
			<input type="hidden" name="id" value="${sFPictureInfoPage.id}">
			<input type="hidden" name="graphicType" value="1">
			<div class="layui-form layuimini-form">
				<div class="layui-form-item">
					<label class="layui-form-label required">文件类型</label>
					<div class="layui-input-block tip2">
						<select id="fileType" name="fileType" lay-search="" lay-verify="required" lay-reqtext="请选择文件类型">
							<option value="">直接选择或搜索</option>
							<c:forEach items="${fileTypeList}" var="item">
								<option data="${item.typecode}"
										value="${item.typecode}" ${sFPictureInfoPage.fileType eq item.typecode ? 'selected': ''}>${item.typename}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label required">所属图层</label>
					<div class="layui-input-block tip2">
						<select id="layerId" name="layerId" lay-search="" lay-verify="required" lay-reqtext="请选择所属图层">
							<option value="">直接选择或搜索</option>
							<c:forEach items="${layerList}" var="item">
								<option data="${item.id}"
										value="${item.id}" ${sFPictureInfoPage.layerId eq item.id ? 'selected': ''}>${item.layerName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label required">文件名称</label>
					<div class="layui-input-block tip2">
						<input lay-verify="required" disabled lay-reqtext="请输入文件名称" type="text" id="fileName" name="fileName" value="${sFPictureInfoPage.fileName}" class="layui-input" />
					</div>
				</div>
				<div class="layui-form-item">
					<label class="layui-form-label required">四色图</label>
					<div class="layui-input-block tip2">
						<button type="button" class="layui-btn" id="uploadImg">图片上传</button>
						<input lay-verify="required" lay-reqtext="请上传图片" type="hidden" name="fileId" id="fileId" value="${sFPictureInfoPage.fileId}">
						<div class="layui-upload">
							<blockquote class="layui-elem-quote layui-quote-nm" style="padding:0">
								<div class="layui-upload-list uploader-list" style="height: 78px" id="imgList">
									<c:forEach items="${imgList}" var="item">
										<div id="img" class="file-iteme">
											<div class="handle" id="${item.id}" style="display: none;"><i class="layui-icon layui-icon-delete"></i></div>
											<img onclick="previewImg(this)" class="layui-upload-img" src="${item.path}">
										</div>
									</c:forEach>
								</div>
							</blockquote>
						</div>
					</div>
				</div>
			</div>
			<button id="btn" class="layui-btn" style="display: none" lay-submit lay-filter="sub" id="sub">立即提交</button>
		</form>
	</div>
</div>

<script>
	var imgNum=${imgNum};
	layui.use(['form', 'layedit', 'laydate',"upload"], function () {
		var form = layui.form, layer = layui.layer ,upload = layui.upload;

		checkImgNum();
		upload.render({
			elem: '#uploadImg'
			, url: 'sFPictureInfoController.do?uploadFile'
			, accept: 'images'
			, acceptMime: 'image/*'
			, done: function (res) {
				var data = res.obj;
				if (res.success) {
					$('#imgList').append(
							'<div id="" class="file-iteme">' +
							'<div class="handle" id="'+data.id+'"><i class="layui-icon layui-icon-delete"></i></div>' +
							'<img  onclick="previewImg(this)" class="layui-upload-img" src="' + data.path + '">' +
							'</div>'
					);
					var imgs = $("#fileId").val();
					if (imgs == null || imgs == '' || imgs == undefined) {
						imgs += data.id;
					} else {
						imgs += "," + data.id
					}
					$("#fileId").val(imgs)

					var fileName=$('#fileName').val()
					// if (fileName == null || fileName == '' || fileName == undefined){
					$("#fileName").val(data.name)
					// }
					imgNum++;
					checkImgNum();
				}
				layer.msg(res.msg, {icon: res.success == true ? 1 : 2, time: 1800})
			}
		});

		//监听提交
		form.on('submit(sub)', function (data) {
			// console.log(data.field)
			$.ajax({
				async : false,
				type : 'POST',
				data : data.field,
				url : 'sFPictureInfoController.do?save',// 请求的action路径
				success : function(data) {
					var d = $.parseJSON(data);
					var index = parent.layer.getFrameIndex(window.name); //获取当前窗口的name
					parent.layer.close(index);
					parent.layer.msg(d.msg, {icon: d.success==true?1:2, time: 1800});
					parent.tools.tableReload();
				}
			});
			return false;
		});

		//自定义验证规则
		form.verify({
			customFunFileType: function (value) {
				if(value != "${sFPictureInfoPage.fileType}") {
					var layerId=$("#layerId").val()
					var msg = "";
					$.ajax({
						async : false,
						type : 'GET',
						data : {
							layerId: layerId,
							fileType:value
						},
						url : 'sFPictureInfoController.do?propertyUnique',// 请求的action路径
						success : function(data) {
							var d = $.parseJSON(data);
							if (!d.success) {
								msg = d.msg;
							}
						}
					});
					return msg;
				}
			},	customLayerId: function (value) {
				if(value != "${sFPictureInfoPage.layerId}") {
					var fileType=$("#fileType").val()
					var msg = "";
					$.ajax({
						async : false,
						type : 'GET',
						data : {
							layerId: value,
							fileType:fileType
						},
						url : 'sFPictureInfoController.do?propertyUnique',// 请求的action路径
						success : function(data) {
							var d = $.parseJSON(data);
							if (!d.success) {
								msg = d.msg;
							}
						}
					});
					return msg;
				}
			}
		});
	});


	$(document).on("mouseenter mouseleave", ".file-iteme", function (event) {
		if (event.type === "mouseenter") {
			//鼠标悬浮
			$(this).children(".info").fadeIn("fast");
			$(this).children(".handle").fadeIn("fast");
		} else if (event.type === "mouseleave") {
			//鼠标离开
			$(this).children(".info").hide();
			$(this).children(".handle").hide();
		}
	});

	$(document).on("click", ".file-iteme .handle", function (event) {
		var id = $(this).attr("id")
		var that_ = this;
		// var data = JSON.parse(result);
		var imgs = $("#fileId").val();
		// 先替换中间的
		imgs = imgs.replace("," + id + ",", '').replace(",,",',');
		imgs = imgs.replace(id,'');
		// 去掉前后的,
		imgs = imgs.replace(/^,+/,"").replace(/,+$/,"");
		$("#fileId").val(imgs)
		$(that_).parent().remove();
		$("#fileName").val('')
		imgNum--;
		checkImgNum();
	});
	function checkImgNum() {
		if (imgNum >= 1) {
			$("#uploadImg").attr("disabled", "true");
			$("#uploadImg").addClass("layui-btn-disabled")
		} else {
			$("#uploadImg").removeAttr("disabled");
			$("#uploadImg").removeClass("layui-btn-disabled")
		}
	}
	function previewImg(obj) {
		var img = new Image();
		var bigImgSrc = obj.src;
		bigImgSrc = bigImgSrc.replace("reduce_","");
		img.src = bigImgSrc;
		var height = img.height + 50; //获取图片高度
		var width = img.width; //获取图片宽度
		var imgHtml = "<div style='text-align: center'><img src='" + bigImgSrc + "' style='width: 550px; height: 550px; object-fit: scale-down;'/></div>";
		//弹出层
		parent.layer.open({
			type: 1,
			shade: 0.8,
			offset: 'auto',
			area: ['600px','600px'],
			shadeClose:true,
			scrollbar: false,
			title: "图片预览",
			content: imgHtml,
		});
	}
</script>

</body>
</html>