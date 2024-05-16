<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>隐患整改</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
    <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
    <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
    <script type="text/javascript">
        //编写自定义JS代码
        $(function(){
            var modifySelect = getUserMagicSuggestWithValue($("#modifySelect"), $("#modifyMan"), "${tBHiddenDangerHandle.modifyMan}", false);
            $("input[name='checkStatus']").on("click",function(){
                if($(this).val()=='0'){
                    $(".rollBack").show();
                    $(".pass").hide();
                    $("#rollBackRemark").attr("datatype","*");
                    $("#modifyDate").removeAttr("datatype");
                    $("#modifyMan").removeAttr("datatype");
                    $("select[name='modifyShift']").removeAttr("datatype");
                    $("#rectMeasures").removeAttr("datatype");
                } else {
                    $(".rollBack").hide();
                    $(".pass").show();
                    $("#rollBackRemark").removeAttr("datatype");
                    $("#modifyDate").attr("datatype","*");
                    $("#modifyMan").attr("datatype","*");
                    $("#modifyShift").attr("datatype","*");
                    $("#rectMeasures").attr("datatype","*");
                }
            });
        })
        function saveNoteInfo(state) {
            if($("input[name='checkStatus']:checked").val()=='0'){
                $("#modifyDate").val("");
                $("#modifyMan").val("");
                $("#modifyShift>option[value='']").attr("selected","selected");
                $("#rectMeasures").empty();
            } else {
                $("#rollBackRemark").empty();
            }
            $('#btn_sub').click();
        }

        function noteSubmitCallback(data) {
            var win = frameElement.api.opener;
            win.tip(data.msg);
            frameElement.api.close();
            win.reloadTable();
        }
    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBHiddenDangerHandleController.do?saveAllModifyIssues" callback="@Override noteSubmitCallback" >
    <input id="id" name="id" type="hidden" value="${tBHiddenDangerHandle.id }">
    <input id="ids" name="ids" type="hidden" value="${tBHiddenDangerHandle.ids}">
    <%--<input id="checkStatus" name="checkStatus" type="hidden">--%>
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td  class="Validform_label" colspan="4" align="center">
                <label><input type="radio" value="1" name="checkStatus" checked="checked"/>通过</label>
                <label><input type="radio" value="0" name="checkStatus" />不通过</label>
            </td>
        </tr>
        <tr class="pass">
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>整改时间:
                </label>
            </td>
            <td class="value">
                <input id="modifyDate" name="modifyDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" datatype="*"
                       ignore="checked"
                       value='<fmt:formatDate value='${tBHiddenDangerHandle.modifyDate}' type="date" pattern="yyyy-MM-dd"/>'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">整改时间</label>
            </td>
        </tr>
        <tr class="pass">
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>整改人:
                </label>
            </td>
            <td class="value">
                <div id="modifySelect" style="width: 130px;height: 15px;" class="inputxt"></div>
                <input id="modifyMan" name="modifyMan" type="hidden"
                       datatype="*"
                       value='${tBHiddenDangerHandle.modifyMan}'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">整改人</label>
            </td>
        </tr>
        <tr class="pass">
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>整改班次:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="modifyShift" type="list" extendJson="{\"datatype\":\"*\"}"
                typeGroupCode="workShift" defaultVal="${tBHiddenDangerHandle.modifyShift}" hasLabel="false"  title="班次"></t:dictSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">整改班次</label>
            </td>
        </tr>
        <tr class="pass">
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>整改措施:
                </label>
            </td>
            <td class="value">
                <textarea name="rectMeasures" id="rectMeasures" class="inputxt" style="width: 280px; height: 100px;" datatype="*">${tBHiddenDangerHandle.rectMeasures}</textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">整改措施</label>
            </td>
        </tr>
        <tr class="rollBack" style="display: none">
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>驳回备注:
                </label>
            </td>
            <td class="value">
                <textarea name="rollBackRemark" id="rollBackRemark" class="inputxt" style="width: 280px; height: 100px;">${tBHiddenDangerHandle.rollBackRemark}</textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">驳回备注</label>
            </td>
        </tr>
    </table>
    <div class="ui_main">
        <div class="ui_buttons">
            <input type="button" value="提交" onclick="saveNoteInfo();" class="ui_state_highlight">
        </div>
    </div>
</t:formvalid>
</body>