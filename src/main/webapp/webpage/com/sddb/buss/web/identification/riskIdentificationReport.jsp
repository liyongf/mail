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
            var modifySelect = getUserMagicSuggestWithValue($("#modifySelect"), $("#reviewMan"), "", false);
        })
        function saveNoteInfo(state) {
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
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskIdentificationController.do?doReport" callback="@Override noteSubmitCallback" >
    <input id="ids" name="ids" type="hidden" value="${ids }">
    <input id="identifiManId" name="identifiManId" type="hidden" value="${identifiManId }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr class="pass">
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>审批人:
                </label>
            </td>
            <td class="value">
                <div id="modifySelect" style="width: 130px;height: 15px;" class="inputxt"></div>
                <input id="reviewMan" name="reviewMan" type="hidden"
                       datatype="*"
                       value=''>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">审批人</label>
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