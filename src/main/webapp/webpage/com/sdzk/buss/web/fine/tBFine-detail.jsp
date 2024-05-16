<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>t_b_danger_source</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
    <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
    <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
    <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
    <script type="text/javascript">
        var magicsuggestFineManSelected = "";
        var magicsuggestDutyUnitSelected="";
        var magicsuggestBeFinedManSelected="";

        function showTips(data){
            tip(data.msg);
            window.top.reload_tBFineList.call();
            setTimeout("$('#btn_close').click()",2*1000);
            doCloseTab();
        }
        function formSubmit(reportStatus){
            $("#reportStatus").val(reportStatus);
            $("#btn_sub").trigger("click");
        }

    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBFineController.do?doUpdate" tiptype="3" callback="showTips" btnsub="btn_sub">
    <input id="id" name="id" type="hidden" value="${tbFinePage.id }"/>
    <input id="fineNum" name="fineNum" type="hidden" value="${tbFinePage.fineNum }"/>
    <input id="createBy" name="createBy" type="hidden" value="${tbFinePage.createBy }"/>
    <input id="createDate" name="createDate" type="hidden" value="${tbFinePage.createDate }"/>
    <input id="createName" name="createName" type="hidden" value="${tbFinePage.createName }"/>
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>罚款时间:
                </label>
            </td>
            <td class="value">
                <input id="fineDate" name="fineDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" datatype="*" value="<fmt:formatDate value='${tbFinePage.fineDate}' type="date" pattern="yyyy-MM-dd"/>" />
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">罚款时间</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>罚款人:
                </label>
            </td>
            <td class="value">
                    ${tbFinePage.fineMan}
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>罚款性质:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="fineProperty" type="list" extendJson="{\"datatype\":\"*\"}"
                typeGroupCode="fineProperty" defaultVal="${tbFinePage.fineProperty}" hasLabel="false"  title="罚款性质"></t:dictSelect>
                <span class="Validform_checktip">请选择罚款性质</span>
                <label class="Validform_label" style="display: none;">罚款性质</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>问题描述:
                </label>
            </td>
            <td class="value">
                <textarea id="content" name="content" class="inputxt" rows="3" style="width: 95%;" datatype="*">${tbFinePage.content}</textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">问题描述</label>
            </td>
        </tr>

        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>责任人部门:
                </label>
            </td>
            <td class="value">
                    ${tbFinePage.dutyUnit.departname}
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>责任人姓名:
                </label>
            </td>
            <td class="value">
                    ${tbFinePage.beFinedMan}
            </td>
        </tr>

        <tr>
            <td align="right">
                <label class="Validform_label">
                    罚款金额:
                </label>
            </td>
            <td class="value" colspan="3">
                <input id="fineMoney" name="fineMoney" type="text" style="width: 150px" datatype="/^([1-9]\d*|[0]{1,1})$/" errormsg="罚款金额必须是正整数" value="${tbFinePage.fineMoney}"/>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">罚款金额</label>
            </td>
        </tr>

    </table>
</t:formvalid>
</body>