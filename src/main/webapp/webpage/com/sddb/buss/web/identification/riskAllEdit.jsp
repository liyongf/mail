<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>风险辨识审核</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
    <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
    <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
    <script type="text/javascript">
        //编写自定义JS代码
        $(function(){
            var vioPeopleSelect = getUserMagicSuggestAllowFreeEntries($('#vioPeopleSelect'), $("#dutyManager"));
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
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskIdentificationController.do?doAllEdit" callback="@Override noteSubmitCallback" >
    <input id="ids" name="ids" type="hidden" value="${ids }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>最高管控层级:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="manageLevel" type="list" datatype="*" id="manageLevel"
                              typeGroupCode="identifi_mange_level" hasLabel="false"  title="信息来源"></t:dictSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">最高管控层级</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>最高管控责任人:
                </label>
            </td>
            <td class="value">
                <div id="vioPeopleSelect" style="width: 130px;height: 15px"></div>
                <input id="dutyManager" name="dutyManager" type="hidden" datatype="*">
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">最高管控责任人</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>评估日期:
                </label>
            </td>
            <td class="value">
                <input id="identifiDate" name="identifiDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="*" value='<fmt:formatDate value='${ideDate}' type="date" pattern="yyyy-MM-dd"/>'/>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">评估日期</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>解除日期:
                </label>
            </td>
            <td class="value">
                <input id="expDate" name="expDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="*" value='<fmt:formatDate value='${riskIdentificationEntity.expDate}' type="date" pattern="yyyy-MM-dd"/>'/>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">解除日期</label>
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