/**
 * 计算填充风险登记和风险值  并自动计算风险等级
 */
function fillFXDJFXZ(){
    var riskMay = $("#riskPossibility").val();
    var riskLoss = $("#riskLoss").val();
    var threshold_major = $("#threshold_major").val();
    var threshold_superior = $("#threshold_superior").val();
    var threshold_commonly = $("#threshold_commonly").val();
    if(riskMay!= null && riskMay != "" && riskLoss != null && riskLoss !=""){
        var mut = riskMay * riskLoss;
        $("#riskValue").val(mut);
        if(mut>=threshold_major) {
            $("#riskLevel").val("重大风险");
        }else{
            if(mut>=threshold_superior) {
                $("#riskLevel").val("较大风险");
            }else{
                if(mut>=threshold_commonly) {
                    $("#riskLevel").val("一般风险");
                }else{
                    $("#riskLevel").val("低风险");
                }
            }
        }
    }
}
/**
 * 计算填充LEC风险值  并自动计算风险等级
 * */
function fillLECRiskValue(){
    var lecRiskPossibility = $("#lecRiskPossibility").val();
    var lecRiskLoss = $("#lecRiskLoss").val();
    var lecExposure = $("#lecExposure").val();
    var threshold_lec_major = $("#threshold_lec_major").val();
    var threshold_lec_superior = $("#threshold_lec_superior").val();
    var threshold_lec_commonly = $("#threshold_lec_commonly").val();
    if(lecRiskPossibility!= null && lecRiskPossibility != "" && lecRiskLoss != null && lecRiskLoss !="" && lecExposure != null && lecExposure != ""){
        var mut = lecRiskPossibility * lecRiskLoss * lecExposure;
        $("#lecRiskValue").val(mut);
        if(mut>=threshold_lec_major) {
            $("#riskLevel").val("重大风险");
        }else{
            if(mut>=threshold_lec_superior) {
                $("#riskLevel").val("较大风险");
            }else{
                if(mut>=threshold_lec_commonly) {
                    $("#riskLevel").val("一般风险");
                }else{
                    $("#riskLevel").val("低风险");
                }
            }
        }
    }
}

/**
 *关闭当前tab
 */
function doCloseTab(){

    var cur = window.top.$('a[class="active J_menuTab"]');
    if(cur.length ==0){
        cur = window.top.$('a[class="J_menuTab active"]');
    }
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

function showTips(data){
    tip(data.msg);
    window.top.reload_tBDangerSourcePostList.call();
    setTimeout("$('#btn_close').click()",2*1000);
    doCloseTab();
}