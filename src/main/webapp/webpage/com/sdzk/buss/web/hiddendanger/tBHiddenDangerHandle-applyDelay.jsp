<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>隐患延期</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
    <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
    <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBHiddenDangerHandleController.do?saveAllApplyIssues" callback="@Override noteSubmitCallback" >
    <input id="ids" name="ids" type="hidden" value="${ids}">

    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr class="pass">
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>申请限期日期:
                </label>
            </td>
            <td class="value">
                <input id="limitDateNew" name="limitDateNew" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({minDate:'%y-%M-%d'})" datatype="*"
                       ignore="checked"
                       value='<fmt:formatDate value='<%=new Date()%>' type="date" pattern="yyyy-MM-dd"/>'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">申请限期日期</label>
            </td>
        </tr>
        <tr class="pass">
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>申请人:
                </label>
            </td>
            <td class="value">
                <div id="applySelect" style="width: 130px;height: 15px;" class="inputxt"></div>
                <input id="applyMan" name="applyMan.id" type="hidden"
                       datatype="*"
                       value='${sessionUser.realName}'>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">申请人</label>
            </td>
        </tr>
        <tr class="pass">
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>申请原因:
                </label>
            </td>
            <td class="value">
                <textarea name="applyReason" id="applyReason" class="inputxt" style="width: 280px; height: 100px;" datatype="*"></textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">申请原因</label>
            </td>
        </tr>
        <tr class="pass">
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>受理人:
                </label>
            </td>
            <td class="value">
                <div id="acceptSelect" style="width: 130px;height: 15px;" class="inputxt"></div>
                <input id="acceptMan" name="acceptMan.id" type="hidden" datatype="*" />
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">受理人</label>
            </td>
        </tr>
    </table>
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
        win.reloadTable();
    }
</script>
</html>