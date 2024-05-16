<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>隐患检查</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<link href="plug-in/jQueryLabel/css/tab.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="plug-in/jQueryLabel/js/tab.js"></script>
<link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
    <link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">

<script type="text/javascript">
    var isFirstLoad = true;
//编写自定义JS代码
var address = "${tBHiddenDangerExamPage.address.address}";
var magicsuggestSelected = "";
var magicsuggestDutyManSelected = "";
var magicsuggestDutyUnitSelSelected = "";
$(function() {

    //此部分代码用来添加关键词字段，获取关键词，将其拼成一句，以逗号分隔，然后设置到隐藏的id为keyWord的隐藏input中提交
    $("#btn").on("click", function(){
        var keyWords="";
        $("#myTags>a").each(function(){
            keyWords = keyWords+$(this).attr("title")+",";
        });
        var str = keyWords;
        keyWords = str.substring(0,str.length-1);
        $("#keyWords").val(keyWords);
    });

    fillKeyWords();

    getUserMagicSuggestWithValue($("#reviewManNameSelect"), $("input[name='reviewMan.id']"),"${tBHiddenDangerExamPage.reviewMan.realName}",false);
    magicsuggestDutyUnitSelSelected = $('#magicsuggestDutyUnitSel').magicSuggest({
        allowFreeEntries: false,
        data:'magicSelectController.do?departSelectDataGridMagic',
        value:["${tBHiddenDangerExamPage.dutyUnit.id}"],
        valueField:'id',
        placeholder:'输入或选择',
        maxSelection:1,
        selectFirst: true,
        highlight: false,
        matchField:['spelling','departName','fullSpelling'],
        displayField:'departName'
    });

    $(magicsuggestDutyUnitSelSelected).on('selectionchange', function(c){
        if(!isFirstLoad){
            $("#dutyUnitId").val(magicsuggestDutyUnitSelSelected.getValue());
        }
        isFirstLoad = false;
    });
    magicsuggestSelected = $('#magicsuggest').magicSuggest({
        data:'magicSelectController.do?getAddressList',
        allowFreeEntries: false,
        valueField:'id',
        placeholder:'输入或选择',
        value:["${tBHiddenDangerExamPage.address.id}"],
        maxSelection:1,
        selectFirst: true,
        highlight: false,
        displayField:'address'
    });
    $(magicsuggestSelected).on('selectionchange', function(e,m){
        $("#address").val(magicsuggestSelected.getValue());
    });


    magicsuggestDutyManSelected = getUserMagicSuggestAllowFreeEntries($('#magicsuggestDutyMan'),  $("#dutyMan"), "${tBHiddenDangerExamPage.dutyMan}", false);

    $(magicsuggestDutyManSelected).on('focus', function(c){
        var deptId = $('#dutyUnitId').val();
        magicsuggestDutyManSelected.setData("magicSelectController.do?getUserList&orgIds="+deptId);
    });

    // 督办单位
    magicsuggestSuperviseUnitSelSelected = $('#magicsuggestSuperviseUnitSel').magicSuggest({
        allowFreeEntries: true,
        data:'magicSelectController.do?departSelectDataGridMagic',
        value:["${tBHiddenDangerExamPage.superviseUnitId}"],
        valueField:'id',
        placeholder:'输入或选择',
        maxSelection:1,
        selectFirst: true,
        highlight: false,
        matchField:['spelling','departName','fullSpelling'],
        displayField:'departName'
    });
    $(magicsuggestSuperviseUnitSelSelected).on('selectionchange', function(c){
        $("#superviseUnitId").val(magicsuggestSuperviseUnitSelSelected.getValue());
    });

    $("#beginWellDate").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
    $("#endWellDate").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
    $("#limitDate").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

    showTr(${tBHiddenDangerExamPage.dealType eq "1"? '"dealTypetr1" ':'"xcclTR"'});

    $("#limitDate").Validform({
        tiptype:3,
        datatype : {
            limDate:function(gets,obj,curform,regxp){
                var dealType = $("input[name='dealType']:checked").val();
                if(dealType == "1" && gets == ""){
                    return false;
                }else{
                    return true;
                }
            },
            message: '请输入限期日期'
        }
    });

    $("#limitShift").Validform({
        tiptype:3,
        datatype : {
            limShift:function(gets,obj,curform,regxp){
                var dealType = $("input[name='dealType']:checked").val();
                if(dealType == "1" && gets == ""){
                    return false;
                }else{
                    return true;
                }
            },
            message: '请选择限期班次'
        }
    });

    $("#reviewManId").Validform({
        tiptype:3,
        datatype : {
            reviewManValid:function(gets,obj,curform,regxp){
                var dealType = $("input[name='dealType']:checked").val();
                if(dealType == "2" && gets == ""){
                    return false;
                }else{
                    return true;
                }
            },
            message: '请选择复查人'
        }
    });
    //移除重大隐患
    $("select[name='hiddenNature']>option[value='1']").remove();

});
function choose_dangerSource(title){
    var addressId=$("#address").val();
    if(addressId==null||addressId==''){
        tip("请先选择地点");
        return;
    }
    var keyWords="";
    $("#myTags>a").each(function(){
        keyWords = keyWords+$(this).attr("title")+",";
    });
    var str = keyWords;
    keyWords = str.substring(0,str.length-1);
    if (typeof(windowapi) == 'undefined') {
        $.dialog({content: 'url:tBDangerSourceController.do?chooseDangerSource&keys='+encodeURI(encodeURI(keyWords))+"&addressId="+addressId, zIndex: 2100, title: ''+title+'', lock: true, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
            {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_dangerSource, focus: true},
            {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
            }}
        ]});
    } else {
        $.dialog({content: 'url:tBDangerSourceController.do?chooseDangerSource&keys='+encodeURI(encodeURI(keyWords))+"&addressId="+addressId, zIndex: 2100, title: ''+title+'', lock: true, parent: windowapi, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
            {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_dangerSource, focus: true},
            {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
            }}
        ]});
    }
}
function clickcallback_dangerSource(){
    iframe = this.iframe.contentWindow;
    var dangerName = iframe.gettBDangerSourceListSelections('hazard.hazardName');
    $("#dangerName").val(dangerName);
    $("#dangerName").blur();
    var problemDesc = iframe.gettBDangerSourceListSelections('yeMhazardDesc');
    if($("#problemDesc").text()==null||$("#problemDesc").text()==""){
        $("#problemDesc").text(problemDesc);
    }
    var hiddenLevel = iframe.gettBDangerSourceListSelections('hiddenLevel');
    if($("select[name='hiddenNature']").val()==null||$("select[name='hiddenNature']").val()==""){
        $("select[name='hiddenNature']>option[value='"+hiddenLevel+"']").attr("selected","selected");
    }
    var id = iframe.gettBDangerSourceListSelections('id');
    $("input[name='dangerId.id']").val(id);
    $("input[name='dangerId.id']").blur();

    var yePossiblyHazard = iframe.gettBDangerSourceListSelections('yePossiblyHazard');
    $("#yePossiblyHazard").text(yePossiblyHazard);

    var yeRiskGradeTemp = iframe.gettBDangerSourceListSelections('yeRiskGradeTemp');
    var alertColor = iframe.gettBDangerSourceListSelections('alertColor');
    colorValueFormatter(yeRiskGradeTemp,alertColor);
}

function choose_fillCardMan(title) {
    if (typeof(windowapi) == 'undefined') {
        $.dialog({content: 'url:userController.do?userManySelectList', zIndex: 9999, title: ''+title+'', lock: true, width: 860, height: 480, left: '85%', top: '65%', opacity: 0.4, button: [
            {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_fillCardMan, focus: true},
            {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
            }}
        ]});
    } else {
        $.dialog({content: 'url:userController.do?userManySelectList', zIndex: 9999, title: ''+title+'', lock: true, parent: windowapi, width: 860, height: 480, left: '85%', top: '65%', opacity: 0.4, button: [
            {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_fillCardMan, focus: true},
            {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
            }}
        ]});
    }
}
function clickcallback_fillCardMan() {
    iframe = this.iframe.contentWindow;
    var realName = iframe.getuserSelectListSelections('realName');
    var choosed = $("input[name='teamMembers']").val();
    if(choosed != null && choosed.length >0){
        choosed = choosed+","+realName;
    }else{
        choosed = realName;
    };
    $("input[name='teamMembers']").val(choosed);
    $("input[name='teamMembers']").blur();
    var id = iframe.getuserSelectListSelections('id');
    var choosedId = $("input[name='itemUserId']").val();
    if(choosedId != null && $("input[name='itemUserId']").val.length >0){
        choosedId = choosedId+","+id;
    }else{
        choosedId = id;
    }
    $("input[name='itemUserId']").val(choosedId);
}
function showTr(trid){
    if ("xcclTR" == trid) {
        $(".xcclTR").show();
        $("#dealTypetr1").hide();
        $("#limitDate").removeAttr("datatype");
   //    // $("#reviewManId").attr("datatype", "*");
    } else if ("dealTypetr1" == trid) {
        $(".xcclTR").hide();
        $("#dealTypetr1").show();
        $("#limitDate").attr("datatype", "*");
        //  $("#reviewManId").removeAttr("datatype");
    }
}
    /**
     *清空组员
     */
    function clearTeamMembers(){
        $("#teamMembers").val("");
        $("#itemUserId").val("");
    }

    function fillKeyWords(){
        var str = '${tBHiddenDangerExamPage.keyWords}';
        var strKeyWords=new Array(); //定义一数组

        strKeyWords = str.split(','); //字符分割
        for(var i=0; i < strKeyWords.length; i++){
            if(strKeyWords[i] != ""){
                setTips(strKeyWords[i], -1);
            }
        }
    }

    /**
     *关闭当前tab
     */
    function closeWindow() {
        window.top.$('.J_menuTab.active>i[class="fa fa-times-circle"]').trigger("click");
    }
    /**
     *保存回调函数  清空数据
     */
    function callbackFun(data) {
        window.top["reload_tBHiddenDangerExamList_${examType}"].call();
        window.top["tip_tBHiddenDangerExamList_${examType}"].call(data);
        closeWindow();
    }
    function formSubmit(reportStatus){
        $("#reportStatus").val(reportStatus);
        var dealType = $("input[name='dealType']:checked").val();
        if(dealType == "2"){
            if(reportStatus == "1"){
                $("#reviewManId").attr("datatype", "*");
            }
        }
        if(reportStatus == "0"){
            $("#reviewManId").removeAttr("datatype");
        }
        $("#btn").trigger("click");
    }

</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" action="tBHiddenDangerExamController.do?doUpdate" tiptype="3" btnsub="btn" callback="callbackFun">
<input id="id" name="id" type="hidden" value="${tBHiddenDangerExamPage.id }">
<input id="createName" name="createName" type="hidden" value="${tBHiddenDangerExamPage.createName }">
<input id="createBy" name="createBy" type="hidden" value="${tBHiddenDangerExamPage.createBy }">
<input id="createDate" name="createDate" type="hidden" value="${tBHiddenDangerExamPage.createDate }">
<input id="updateName" name="updateName" type="hidden" value="${tBHiddenDangerExamPage.updateName }">
<input id="updateBy" name="updateBy" type="hidden" value="${tBHiddenDangerExamPage.updateBy }">
<input id="updateDate" name="updateDate" type="hidden" value="${tBHiddenDangerExamPage.updateDate }">
<input id="examType" name="examType" type="hidden" value="${examType}">
<input id="flag" name="flag" type="hidden" value="${flag }">
<input type="hidden" id="selectedFineEmp" name="selectedFineEmp"/>
<input id="reportStatus" name="reportStatus" type="hidden" value="0"/>
<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
<tr>
    <td align="left"  colspan="4" class="value">
        <label class="Validform_label">
            加<font color="red">*</font>内容必填！
        </label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>日期:
        </label>
    </td>
    <td class="value">
        <input id="examDate" name="examDate" type="text" style="width: 150px" class="Wdate" datatype="*" onClick="WdatePicker({maxDate:'%y-%M-%d'})" value='<fmt:formatDate value='${tBHiddenDangerExamPage.examDate}' type="date" pattern="yyyy-MM-dd"/>'>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">日期</label>
    </td>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>班次:
        </label>
    </td>
    <td class="value">
        <t:dictSelect field="shift" type="list" extendJson="{\"datatype\":\"*\"}"
                      typeGroupCode="workShift" defaultVal="${tBHiddenDangerExamPage.shift}" hasLabel="false"  title="班次"></t:dictSelect>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">班次</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>地点:
        </label>
    </td>
    <td class="value">
        <div id="magicsuggest" style="width: 130px;height: 15px"></div>
        <input type="hidden" name="address.address" id="address" datatype="*" value="${tBHiddenDangerExamPage.address.id}">
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">地点</label>
    </td>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>组别:
        </label>
    </td>
    <td class="value">
        <t:dictSelect field="itemId" type="list" extendJson="{\"datatype\":\"*\"}"
                      typeGroupCode="group" defaultVal="${tBHiddenDangerExamPage.itemId}"
                      hasLabel="false" title="组别"></t:dictSelect>
        <c:if test="${examType eq 'glgbxj'}">
            <label>
                <input type="checkbox" name="isWithClass" value="1" ${tBHiddenDangerExamPage.itemId eq "1"?"checked='checked'":""} >带班
            </label>
        </c:if>
        <span class="Validform_checktip">请选择组别</span>
        <label class="Validform_label" style="display: none;">组别</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>组员:
        </label>
    </td>
    <td class="value" colspan="3">
        <input id="teamMembers" name="teamMembers" type="text" style="width: 440px;" datatype="*" onclick="choose_fillCardMan('选择组员')"  value="${tBHiddenDangerExamPage.itemUserNameTemp}" />
        <input id="itemUserId" name="itemUserId" type="hidden" style="width: 150px" class="inputxt" datatype="*" value="${tBHiddenDangerExamPage.itemUserId}" >
        <a id="" class="easyui-linkbutton l-btn l-btn-plain" onclick="clearTeamMembers();" icon="icon-redo" plain="true" href="#">
            <span>清空</span>
        </a>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">组员</label>
    </td>
</tr>

<%--<tr>
    <td align="right">
        <label class="Validform_label">
            关键字:
        </label>
    </td>
    <td class="value" colspan="3">

        <div class="rightAdd">
            <div id="mycard-plus" style="margin-top: 0px;">
                <div class="default-tag tagbtn">
                    <div class="clearfix">
                            &lt;%&ndash;  从字典读取关键字  &ndash;%&gt;
                        <t:keySelect  typeGroupCode="hiddenKeyWords"/>
                    </div>
                </div>
            </div>

            <div class="plus-tag-add">
                <ul class="Form FancyForm" style="margin-top: 8px;">
                    <li>
                        <input name="" type="text" class="stext" id="addKeysinput" maxlength="20" />
                        <label>输入关键字</label>
                        <button id="addKeysId" type="button"  class="Button RedButton" style="font-size:14px;">添加关键字</button>
                    </li>
                </ul>
            </div>
        </div>
    </td>
</tr>
    &lt;%&ndash;关键字&ndash;%&gt;
<tr>

    <td align="right">
        <label class="Validform_label">
            已选关键字:
        </label>
    </td>
    <td class="value" colspan="3">
            &lt;%&ndash;------------------------------------------------------------------------&ndash;%&gt;
        <div class="demo" style="margin-top:1px;overflow:hidden;">
            <input type="hidden" name="keyWords" id="keyWords" nullmsg="请输入关键字！" value="${tBHiddenDangerExamPage.keyWords}"/>  <!-- 这个input是隐藏的input，用来将数据提交 -->
            <div class="plus-tag tagbtn clearfix myTagsBorder" id="myTags"></div>
        </div>
            &lt;%&ndash;------------------------------------------------------------------------&ndash;%&gt;
    </td>
</tr>--%>

    <%--关联危险源--%>
<tr>
    <td align="right">
        <label class="Validform_label" id="dangerSourceLabel">
            <font color="red">*</font>危险源:
        </label>
    </td>
    <td class="value" colspan="3">
        <input id="dangerId.id" name="dangerId.id" type="hidden" style="width: 150px" class="inputxt" nullmsg="请选择危险源！" value="${tBHiddenDangerExamPage.dangerId.id}" datatype="*">
        <textarea id="dangerName" name="dangerName" style="width: 440px;" onclick="choose_dangerSource('选择危险源');">${tBHiddenDangerExamPage.dangerId.hazard.hazardName}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">危险源</label>
    </td>
</tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                风险描述:
            </label>
        </td>
        <td class="value" >
            <textarea id="yePossiblyHazard" name="yePossiblyHazard" style="width: 440px;" disabled="disabled">${tBHiddenDangerExamPage.dangerId.yePossiblyHazard}</textarea>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">风险描述</label>
        </td>

        <td align="right">
            <label class="Validform_label">
                风险等级:
            </label>
        </td>
        <td class="value" valign="middle" id="yeRiskGradeTemp">
            <div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left">
                <input class="minicolors-input" readOnly="true"
                       style="border:0px;width: 80px; padding-left: 26px;color: #333;" type="text"
                       value="${dangerSourceRiskValueTemp}">
								<span class="minicolors-swatch" style="top:0px;"><span
                                        class="minicolors-swatch-color"
                                        style="background-color: ${dangerSourceAlertColor};"></span></span>
            </div>
        </td>
    </tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>责任单位:
        </label>
    </td>
    <td class="value">
        <div id="magicsuggestDutyUnitSel" style="width: 130px;height: 15px"></div>

        <input id="dutyUnitId" name="dutyUnitId" type="hidden" style="width: 150px" class="inputxt" datatype="*" value="${tBHiddenDangerExamPage.dutyUnit.id}">
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">责任单位</label>
    </td>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>责任人:
        </label>
    </td>
    <td class="value">
        <div id="magicsuggestDutyMan" style="width: 130px;height: 15px"></div>
        <input id="dutyMan" name="dutyMan" type="hidden" style="width: 150px" class="inputxt" datatype="*" value="${tBHiddenDangerExamPage.dutyMan}">
        <span class="Validform_checktip">请选择或输入责任人</span>
        <label class="Validform_label" style="display: none;">责任人</label>

    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            督办单位:
        </label>
    </td>
    <td class="value" colspan="3">
        <div id="magicsuggestSuperviseUnitSel" style="width: 130px;height: 15px"></div>
        <input id="superviseUnitId" name="superviseUnitId" type="hidden" style="width: 150px" class="inputxt" value="${tBHiddenDangerExamPage.superviseUnitId}">
        <span class="Validform_checktip">请选择督办单位</span>
        <label class="Validform_label" style="display: none;">督办单位</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>隐患类别:
        </label>
    </td>
    <td class="value">
        <t:dictSelect field="hiddenCategory" type="list" extendJson="{\"datatype\":\"*\"}"
                      typeGroupCode="hiddenCate" defaultVal="${tBHiddenDangerExamPage.hiddenCategory}" hasLabel="false"  title="隐患类别"></t:dictSelect>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">隐患类别</label>
    </td>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>隐患等级:
        </label>
    </td>
    <td class="value">
        <t:dictSelect field="hiddenNature" type="list" extendJson="{\"datatype\":\"*\"}"
                      typeGroupCode="hiddenLevel" defaultVal="${tBHiddenDangerExamPage.hiddenNature}" hasLabel="false"  title="隐患等级"></t:dictSelect>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">隐患等级</label>
    </td>
</tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                <b style="color: red">*</b>隐患类型:
            </label>
        </td>
        <td class="value" colspan="3">
            <t:dictSelect id="hiddenType" field="hiddenType" defaultVal="${tBHiddenDangerExamPage.hiddenType}" typeGroupCode="hiddenType" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">隐患类型</label>
        </td>
    </tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>问题描述:
        </label>
    </td>
    <td class="value" colspan="3">
        <textarea id="problemDesc" name="problemDesc" type="text" style="width: 440px;" datatype="*" >${tBHiddenDangerExamPage.problemDesc}</textarea>
        <input type="button" id="attach" value="自动关联风险" class="ui_state_highlight" style="width: 80px;vertical-align: 100%">
        <span class="Validform_checktip">请填写问题描述</span>
        <label class="Validform_label" style="display: none;">问题描述</label>
    </td>

</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            隐患处理:
        </label>
    </td>
    <td class="value" colspan="3">
        <label>
            <input type="radio" name="dealType" id="dealType_xianqi" value="1" ${tBHiddenDangerExamPage.dealType eq "1"? 'checked="checked" ':""}  onclick="showTr('dealTypetr1');">限期整改
        </label>
        <label>
            <input type="radio" name="dealType" id="dealType_xianchang" value="2" ${tBHiddenDangerExamPage.dealType eq "2"? 'checked="checked" ':""} onclick="showTr('xcclTR');">现场处理
        </label>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">隐患处理</label>
    </td>
</tr>
    <tr id="dealTypetr1">
        <td align="right">
            <label class="Validform_label">
                <font color="red">*</font>限期日期:
            </label>
        </td>
        <td class="value" colspan="3">
            <input id="limitDate" name="limitDate" type="text" style="width: 150px" datatype="limDate" value='<fmt:formatDate value='${tBHiddenDangerExamPage.limitDate}' type="date" pattern="yyyy-MM-dd"/>'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">限期日期</label>
        </td>
    </tr>

    <tr class="xcclTR" style="display: none">
        <td align="right">
            <label class="Validform_label">复查人:
            </label>
        </td>
        <td class="value" colspan="3">
            <div id="reviewManNameSelect" style="width: 130px;height: 15px"></div>
            <input id="reviewManId" name="reviewMan.id" type="hidden" style="width: 150px" class="inputxt" value="${tBHiddenDangerExamPage.reviewMan.id}" >
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">复查人</label>
        </td>
    </tr>
<tr class="xcclTR" style="display: none">
    <td align="right">
        <label class="Validform_label">整改措施:
        </label>
    </td>
    <td class="value">
        <textarea name="handleEntity.rectMeasures" id="handleEntity.rectMeasures" class="inputxt" style="width: 280px; height: 50px;" >${tBHiddenDangerExamPage.handleEntity.rectMeasures}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">整改措施</label>
    </td>
    <td align="right">
        <label class="Validform_label">复查情况:
        </label>
    </td>
    <td class="value">
        <textarea name="handleEntity.reviewReport" id="handleEntity.reviewReport" class="inputxt" style="width: 280px; height: 50px;">${tBHiddenDangerExamPage.handleEntity.reviewReport}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">复查情况</label>
    </td>
</tr>
    <tr>
        <td colspan="4">
            <div class="ui_buttons" style="text-align: center;">
                <input type="button" id="btn" value="保存" class="ui_state_highlight" style="display:none">
                <input type="button" id="btn_draft" value="保存" class="ui_state_highlight" onclick="formSubmit('0')">
                <input type="button" id="btn_subRep" value="保存并上报" class="ui_state_highlight" onclick="formSubmit('1')">
                <input type="button" id="btn_close" onclick="closeWindow();" value="关闭">
            </div>
        </td>
    </tr>


</table>
</t:formvalid>

</body>
<script src = "webpage/com/sdzk/buss/web/hiddendanger/js/tBHiddenDangerExam.js"></script>
<script>
    //这部分的内容是从JQueryLable插件中的tab.js拿过来的，不然的话在页面刚加载时候，方法就被调用，等到加载完点击调用就无效了
    // 搜索
    (function(){
        var $b = $('#addKeysId');
        var $i = $('#addKeysinput');
        $i.keyup(function(e){
            if(e.keyCode == 13){
                $b.click();
            }
        });
        $b.click(function(){
            var name = $i.val().toLowerCase();
            if(name != '') setTips(name,-1);
            setTips(name,-1)
            $i.val('');
            $i.select();
        });
        if("false"=="${bRequiredDangerSouce}"){
            $("#dangerSourceLabel").find("font").remove();
            $("#dangerId\\.id").removeAttr("datatype");
        }

        $("#attach").on("click", function(){
            var addressId=$("#address").val();
            if(addressId==null||addressId==''){
                tip("请先选择地点");
                return;
            }
            var problemDesc=$("#problemDesc").val();
            if(problemDesc==null||problemDesc==''){
                tip("请先填写问题描述");
                return;
            }
            $.ajax({
                url: "tBHiddenDangerExamController.do?attachRisk",
                type: 'post',
                data: {
                    addressId: addressId,
                    problemDesc:problemDesc
                },
                success: function (data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var dangerName = d.obj.dangerName;
                        $("#dangerName").val(dangerName);
                        $("#dangerName").blur();

                        var problemDesc = d.obj.problemDesc;
                        if($("#problemDesc").text()==null||$("#problemDesc").text()==""){
                            $("#problemDesc").text(problemDesc);
                        }
                        var hiddenLevel = d.obj.hiddenLevel;
                        if($("select[name='hiddenNature']").val()==null||$("select[name='hiddenNature']").val()==""){
                            $("select[name='hiddenNature']>option[value='"+hiddenLevel+"']").attr("selected","selected");
                        }
                        var yePossiblyHazard = d.obj.yePossiblyHazard;
                        $("#yePossiblyHazard").text(yePossiblyHazard);

                        var yeRiskGradeTemp = d.obj.yeRiskGradeTemp;
                        var alertColor = d.obj.alertColor;
                        colorValueFormatter(yeRiskGradeTemp,alertColor);

                        var id = d.obj.id;
                        $("input[name='dangerId.id']").val(id);
                        $("input[name='dangerId.id']").blur();
                    }else {
                        tip(d.msg);
                    }
                }
            });
        });
    })();

    function colorValueFormatter(value,alertColor) {
        if(value != "") {

            var html = '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 80px; padding-left: 26px;" type="text" value="' + value + '"><span class="minicolors-swatch" style="top:0px;"><span class="minicolors-swatch-color" style="background-color: ' + alertColor + ';"></span></span></div></div>';
            $("#yeRiskGradeTemp").empty();
            $("#yeRiskGradeTemp").append(html);
        }
    }
</script>

