<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>延期批复</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
    <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
    <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"  action="tBHiddenDangerHandleController.do?doDealApply" callback="@Override noteSubmitCallback" >
    <input id="id" name="id" type="hidden" value="${tBHiddenDangerApplyPage.id}"/>
    <input id="hiddenDangerId" name="hiddenDangerId.id" type="hidden" value="${tBHiddenDangerApplyPage.hiddenDangerId.id}"/>
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td  class="Validform_label" colspan="4" align="center">
                <label><input type="radio" value="1" name="dealFlag" checked="checked"/>批准</label>
                <label><input type="radio" value="0" name="dealFlag" />驳回</label>
            </td>
        </tr>
        <tr  class="pass">
            <td align="right">
                <label class="Validform_label">
                    申请限期日期:
                </label>
            </td>
            <td class="value" colspan="3">
                <fmt:formatDate value='${tBHiddenDangerApplyPage.limitDateNew}' type="date" pattern="yyyy-MM-dd"/>
            </td>
        </tr>
        <tr class="pass">
            <td align="right">
                <label class="Validform_label">
                    申请人:
                </label>
            </td>
            <td class="value">
                    ${tBHiddenDangerApplyPage.applyMan.realName}
            </td>
        </tr>
        <tr class="pass">
            <td align="right">
                <label class="Validform_label">
                    申请原因：
                </label>
            </td>
            <td class="value" colspan="3">
                <textarea disabled = "true" style="width:100%;height: 50px;overflow-y: auto" id="applyReason">
                        ${tBHiddenDangerApplyPage.applyReason}
                </textarea>
            </td>
        </tr>
        <tr class="pass">
            <td align="right">
                <label class="Validform_label">
                    申请次数:
                </label>
            </td>
            <td class="value">
                    ${tBHiddenDangerApplyPage.dealTime}
            </td>
        </tr>
        <tr class="pass">
            <td align="right">
                <label class="Validform_label">
                    受理人:
                </label>
            </td>
            <td class="value">
                    ${tBHiddenDangerApplyPage.acceptMan.realName}
            </td>
        </tr>
        <tr class="pass">
            <td  class="Validform_label" colspan="4" align="center">
                <label><input type="radio" value="0" name="timeTrueFlag" checked="checked"/>按申请日期</label>
                <label><input type="radio" value="1" name="timeTrueFlag" />重新指定限期日期</label>
            </td>
        </tr>
        <tr class="newTime" style="display: none">
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>新的限期日期:
                </label>
            </td>
            <td class="value">
                <input id="limitDateNewTrue" name="limitDateNewTrue" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({minDate:'%y-%M-%d'})"
                       ignore="checked"
                       value='<fmt:formatDate value='${tBHiddenDangerApplyPage.limitDateNew}' type="date" pattern="yyyy-MM-dd"/>'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">新的限期日期</label>
            </td>
        </tr>
        <tr class="rollBack" style="display: none">
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>驳回原因:
                </label>
            </td>
            <td class="value">
                <textarea name="refuseReason" id="refuseReason" class="inputxt" style="width: 280px; height: 100px;"></textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">驳回原因</label>
            </td>
        </tr>
    </table>
    <div class="ui_main">
        <div class="ui_buttons">
            <input name="applyConfirm" type="button" value="批准" onclick="saveNoteInfo();" class="ui_state_highlight">
        </div>
    </div>
</t:formvalid>
</body>
<script type="text/javascript">
    $(function(){
        getUserMagicSuggestWithValue($("#applySelect"),$("#applyMan"),"${sessionUser.id}",false);

        getUserMagicSuggestWithValue($("#acceptSelect"),$("#acceptMan"),"",false);
    });
    function noteSubmitCallback(data) {
        var win = frameElement.api.opener;
        win.tip(data.msg);
        frameElement.api.close();
        debugger
        win.reloadTable();
    }
        $(function(){
            $("input[name='dealFlag']").on("click",function(){
                if($(this).val()=='0'){
                    $(".rollBack").show();
                    $(".pass").hide();
                    $(".newTime").hide();
                    $("#refuseReason").attr("datatype","*");
                    $("input[name='applyConfirm']").val("驳回");
                } else {
                    $(".rollBack").hide();
                    $(".pass").show();
                    if($("input[name='timeTrueFlag']:checked").val()=='1'){
                        $(".newTime").show();
                    }
                    $("#refuseReason").removeAttr("datatype");
                    $("input[name='applyConfirm']").val("批准");
                }
            });
        });
        $(function() {
            $("input[name='timeTrueFlag']").on("click",function(){
                if ($(this).val()=='1') {
                    $(".newTime").show();
                    $("#limitDateNewTrue").attr("datatype", "*");
                } else {
                    $(".newTime").hide();
                    $("#limitDateNewTrue").removeAttr("daatatype");
                }
            });
        });

        function saveNoteInfo() {
            if($("input[name='dealFlag']:checked").val()=='1'){
                $("#refuseReason").empty();
            }else{
                $("limitDateNewTrue").removeAttr("datatype");
                $("#limitDateNewTrue").val("");
            }
            $('#btn_sub').click();
        };
</script>
</html>