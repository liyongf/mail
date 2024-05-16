

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
}
function browseFiles(inputId, file) {// 文件管理器，可多个上传共用
}
function decode(value, id) {//value传入值,id接受值
	var last = value.lastIndexOf("/");
	var filename = value.substring(last + 1, value.length);
	$("#" + id).text(decodeURIComponent(filename));
}

var clStatus = {"pendingSubmit":"100", "pendingVerify":"200", "pendingRect":"300","pendingAccept":"400", "pendingReview":"500", "finished":"600"};

/**
 * 录入/编辑
 * @param state
 */
function saveNoteInfo(state) {
    if (state == '1') {
        $("#clStatus").val(clStatus.pendingRect);
    } else {
        $("#clStatus").val(clStatus.pendingSubmit);
    }
    $('#btn_sub').click();
}
/**
 * 验收
 * @param state
 */
function saveNoteInfo_accept(state) {
    if (state == '1') {
        $("#clStatus").val(clStatus.pendingReview);
    } else {
        $("#clStatus").val(clStatus.pendingRect);
    }
    $('#btn_sub').click();
}
/**
 * 复查
 * @param state
 */
function saveNoteInfo_review(state) {
    if (state == '1') {
        $("#clStatus").val(clStatus.finished);
        $("#cancelDate").attr("datatype","*");
    } else {
        $("#clStatus").val(clStatus.pendingAccept);
        $("#cancelDate").removeAttr("datatype");
    }
    $('#btn_sub').click();
}
//重写了回调,然后自己控制关闭以及刷新
function noteSubmitCallback(data) {
    var win = frameElement.api.opener;
    win.tip("添加成功");
    frameElement.api.close();
    win.reloadTable();
}
function noteSubmitCallbackAdd(data){
    window.top["reload_tBMajorHiddenDangerList"].call(data);
    window.top.$('.J_menuTab.active>i[class="fa fa-times-circle"]').trigger("click");
}

