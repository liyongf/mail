

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

/**
 * 选择辨识参加人员
 */
function choose_participant(title) {
    if (typeof(windowapi) == 'undefined') {
        $.dialog({content: 'url:userController.do?userManySelectList', zIndex: 5000, title: '' + title + '', lock: true, width: 860, height: 480, left: '50%', top: '65%', opacity: 0.4, button: [
            {name: '确定', callback: clickcallback_participant, focus: true},
            {name: '关闭', callback: function () {
            }}
        ]});
    } else {
        $.dialog({content: 'url:userController.do?userManySelectList', zIndex: 5000, title: '' + title + '', lock: true, parent: windowapi, width: 860, height: 480, left: '50%', top: '65%', opacity: 0.4, button: [
            {name: '确定', callback: clickcallback_participant, focus: true},
            {name: '关闭', callback: function () {
            }}
        ]});
    }
}
/**
 * 选择辨识参加人员回调
 */
function clickcallback_participant() {
    iframe = this.iframe.contentWindow;
    var realName = iframe.getuserSelectListSelections('realName');
    var choosed = $("input[name='teamMembers']").val();
    if (choosed != null && choosed.length > 0) {
        choosed = choosed + "," + realName;
    } else {
        choosed = realName;
    }
    $("input[name='teamMembers']").val(choosed);
    $("input[name='teamMembers']").blur();
    var id = iframe.getuserSelectListSelections('id');
    var choosedId = $("input[name='participant']").val();
    if (choosedId != null && $("input[name='participant']").val().length > 0) {
        choosedId = choosedId + "," + id;
    } else {
        choosedId = id;
    }
    $("input[name='participant']").val(choosedId);

}
/**
 * 清空辨识参加人员
 */
function clearTeamMembers() {
    $("#teamMembers").val("");
    $("#participant").val("");
}

/**
 *关闭当前tab
 */
function doCloseTab(){

    var cur = window.top.$('a[class="active J_menuTab"]');
    if(cur.length ==0){
        cur = window.top.$('a[class="J_menuTab active"]');
    }
    // debugger
    var closeTabId =$(cur).attr("data-id");
    if ($(cur).prev('.J_menuTab').size()) {
        var activeId = $(cur).prev('.J_menuTab:last').attr("data-id");
        $(cur).prev('.J_menuTab:last').addClass('active');
        window.top.$('.J_mainContent .J_iframe').each(function () {
            if ($(this).attr("data-id") == activeId) {
                $(this).show().siblings('.J_iframe').hide();
                return false;
            }
        });

        //  移除当前选项卡
        $(cur).remove();

        // 移除tab对应的内容区
        $('.J_mainContent .J_iframe').each(function () {
            if ($(this).data('id') == closeTabId) {
                $(this).remove();
                return false;
            }
        });
    }
    return false;
}