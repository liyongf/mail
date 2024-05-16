

//通用弹出式文件上传
function commonUpload(callback){
    $.dialog({
           content: "url:systemController.do?commonUpload",
           lock : true,
           title:"文件上传",
           zIndex:2100,
           width:700,
           height: 200,
           parent:windowapi,
           cache:false,
       ok: function(){
               var iframe = this.iframe.contentWindow;
               iframe.uploadCallback(callback);
                   return true;
       },
       cancelVal: '关闭',
       cancel: function(){
       } 
   });
}
function browseImages(inputId, Img) {// 图片管理器，可多个上传共用
		var finder = new CKFinder();
		finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
			$("#" + Img).attr("src", fileUrl);
			$("#" + inputId).attr("value", fileUrl);
		};
		finder.resourceType = 'Images';// 指定ckfinder只为图片进行管理
		finder.selectActionData = inputId; //接收地址的input ID
		finder.removePlugins = 'help';// 移除帮助(只有英文)
		finder.defaultLanguage = 'zh-cn';
		finder.popup();
	}
function browseFiles(inputId, file) {// 文件管理器，可多个上传共用
	var finder = new CKFinder();
	finder.selectActionFunction = function(fileUrl, data) {//设置文件被选中时的函数 
		$("#" + file).attr("href", fileUrl);
		$("#" + inputId).attr("value", fileUrl);
		decode(fileUrl, file);
	};
	finder.resourceType = 'Files';// 指定ckfinder只为文件进行管理
	finder.selectActionData = inputId; //接收地址的input ID
	finder.removePlugins = 'help';// 移除帮助(只有英文)
	finder.defaultLanguage = 'zh-cn';
	finder.popup();
}
function decode(value, id) {//value传入值,id接受值
	var last = value.lastIndexOf("/");
	var filename = value.substring(last + 1, value.length);
	$("#" + id).text(decodeURIComponent(filename));
}

/**
 *关闭当前tab
 */
function doCloseTab(){
    window.top.$('.J_menuTab.active>i[class="fa fa-times-circle"]').trigger("click");
}


//<link href="plug-in/jquery-ui-1.12.1/jquery-ui.css" rel="stylesheet">
//<script src="plug-in/jquery-ui-1.12.1/jquery-ui.js"></script>
document.write("<link href=\"plug-in/jquery-ui-1.12.1/jquery-ui.css\" rel=\"stylesheet\">");
document.write("<script src=\"plug-in/jquery-ui-1.12.1/jquery-ui.js\"></script>");
$("#problemDesc").bind('input propertychange', function() {
	var addressId=$("#address").val();
	if(addressId==null||addressId==''){
		return;
	}
	var problemDesc=$("#problemDesc").val();
	if(problemDesc==null||problemDesc==''){
		return;
	}


	$( "#problemDesc" ).autocomplete({
		source:function(request, response){
			$.ajax({
				url: "tBHiddenDangerExamController.do?getSimilarRisk",
				type: 'POST',
				data: {
					addressId: addressId,
					problemDesc:problemDesc
				},
				success: function (data) {
					var d = $.parseJSON(data);
					if (d.success) {
						var dsArr = d.obj;
						var descArr = [];
						for(var i=0;i<dsArr.length;i++){
							//descArr.push({id:dsArr[i].id, label: dsArr[i].desc});
							descArr.push(dsArr[i].desc);
						}
						response(descArr);

					}else {
						//tip(d.msg); need to prompt
					}
				}
			});

		}
	});

});