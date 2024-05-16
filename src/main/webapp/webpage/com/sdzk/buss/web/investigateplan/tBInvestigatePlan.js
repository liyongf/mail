$(document).ready(function(){
    $("select[name='riskPointType']").change(function(){
        //清空风险点关联
        $("#riskPointName").text("");
        $("#riskPointIds").val("");
        //清空危险源关联
        $("#riskName").text("");
        $("#riskIds").val("");
    });
    getDepartMagicSuggestWithValue($('#acceptDepartSelect'), $("#acceptDepart"),"${tBInvestigatePlanPage.acceptDepart}",false);
    getUserMagicSuggestWithValue($('#acceptUserSelect'), $("#acceptUser"),"${tBInvestigatePlanPage.acceptUser}",false);
})
function choose_riskPoint(title, type){
    var url;
    var riskPointType = $("select[name='riskPointType']>option:selected").val();
    var ids = $("#riskPointIds").val();
    if (riskPointType == "1") {
        url = 'url:tBAddressInfoController.do?chooseInveRiskPoint&ids='+ids;
    } else if (riskPointType == "2") {
        url = 'url:tbActivityManageController.do?chooseInveRiskPoint&ids='+ids;
    } else {
        tip("请先选择风险点类型");
        return;
    }
    /**从月度计划中选择**/
    if (type == "month") {
        url = url + "&from=plan&month="+$("#startTime").val();
    }
    if (typeof(windowapi) == 'undefined') {
        $.dialog({content: url, zIndex: 9999, title: ''+title+'', lock: true, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
            {name: '确定', callback: clickcallback_riskPoint, focus: true},
            {name: '关闭', callback: function () {
            }}
        ]});
    } else {
        $.dialog({content: url, zIndex: 9999, title: ''+title+'', lock: true, parent: windowapi, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
            {name: '确定', callback: clickcallback_riskPoint, focus: true},
            {name: '关闭', callback: function () {
            }}
        ]});
    }
}
function clickcallback_riskPoint(){
    var iframe = this.iframe.contentWindow;
    var riskPointType = $("select[name='riskPointType']>option:selected").val();
    var riskPoint;
    var riskPointName = $("#riskPointName").text();
    var riskPointIds = $("#riskPointIds").val();
    if (riskPointType == "1") {
        riskPoint = iframe.getriskPointListSelectionsNewLine('address');
    } else if (riskPointType == "2") {
        riskPoint = iframe.getriskPointListSelectionsNewLine('activityName');
    }
    if (riskPointName != null && riskPointName != "") {
        if (riskPoint != null && riskPoint != "") {
            $("#riskPointName").text(riskPointName+"\r\n"+riskPoint);
        }
    } else {
        $("#riskPointName").text(riskPoint);
    }
    $("#riskPointName").blur();
    var id = iframe.getriskPointListSelections('id');
    if (riskPointIds != null && riskPointIds != "") {
        if (id != null && id != "") {
            $("#riskPointIds").val(riskPointIds+","+id);
        }
    } else {
        $("#riskPointIds").val(id);
        //清空危险源
        $("#riskName").text("");
        $("#riskIds").val("");
    }
}

function choose_risk(title, type){
    var riskPointIds = $("#riskPointIds").val();
    if (riskPointIds == null || riskPointIds == "") {
        tip("请先选择风险点");
        return;
    }
    var riskPointType = $("select[name='riskPointType']>option:selected").val();
    var ids = $("#riskIds").val();
    var url = 'url:tBDangerSourceController.do?chooseInveDangerSource&riskPointIds='+riskPointIds+'&riskPointType='+riskPointType+"&ids="+ids;
    /**从月度计划中选择**/
    if (type == "month") {
        url = url + "&from=plan&month="+$("#startTime").val();
    }
    if (typeof(windowapi) == 'undefined') {
        $.dialog({content: url, zIndex: 9999, title: ''+title+'', lock: true, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
            {name: '确定', callback: clickcallback_risk, focus: true},
            {name: '关闭', callback: function () {
            }}
        ]});
    } else {
        $.dialog({content: url, zIndex: 9999, title: ''+title+'', lock: true, parent: windowapi, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
            {name: '确定', callback: clickcallback_risk, focus: true},
            {name: '关闭', callback: function () {
            }}
        ]});
    }
}
function clickcallback_risk(){
    var iframe = this.iframe.contentWindow;
    var dangerName = iframe.gettBDangerSourceListSelectionsNewLine('yeMhazardDesc');
    var riskName = $("#riskName").text();
    var riskIds = $("#riskIds").val();
    debugger;
    if (riskName != null && riskName != "") {
        if (dangerName != null && dangerName != "") {
            $("#riskName").text(riskName+"\r\n"+dangerName);
        }
    } else {
        $("#riskName").text(dangerName);
    }
    $("#riskName").blur();
    var id = iframe.gettBDangerSourceListSelections('id');
    $("#riskIds").val(id);
    if (riskIds != null && riskIds != "") {
        if (id != null && id != "") {
            $("#riskIds").val(riskIds+","+id);
        }
    } else {
        $("#riskIds").val(id);
    }
}

function clean_riskPoint(){
    //清空风险点关联
    $("#riskPointName").text("");
    $("#riskPointIds").val("");
    //清空危险源关联
    $("#riskName").text("");
    $("#riskIds").val("");
}

function clean_risk(){
    //清空危险源关联
    $("#riskName").text("");
    $("#riskIds").val("");
}

function save(saveOrCommit) {
    $("#saveOrCommit").val(saveOrCommit);
    $("#btn_sub").click();
    window.top.reload_tBInvestigatePlanList.call();
    window.top.tip_tBInvestigatePlanList.call("保存成功");
    frameElement.api.close();
}