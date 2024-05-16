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
</head>
<style>
    table,table tr th, table tr td { border:1px solid #000000; }
    table { width: 200px; min-height: 25px; line-height: 25px; text-align: center; border-collapse: collapse;}
</style>
<body>
<div style="text-align: center;"><span style="font-size:20px;font-weight: bold; text-align: center">原数据</span></div>
<div style="border: 1px #000000 solid;padding-bottom: 100px;margin: 10px;">
<div>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBFineController.do?doUpdate" tiptype="3" callback="showTips" btnsub="btn_sub">
<table style="width: 100%;table-layout: fixed;border-spacing: 0;border-collapse: collapse;" cellpadding="0" cellspacing="1" class="formtable" align="center">
    <%--<tr style="height: 40px;">--%>
        <%--<td colspan='10' align="center" style="font-size:20px;font-weight: bold;">原数据</td>--%>
    <%--</tr>--%>
    <tr>
        <td>序号</td>
        <td>风险点</td>
        <td>风险类型</td>
        <td>风险描述</td>
        <td>风险等级</td>
        <td>危害因素</td>
        <td>最高管控层级</td>
        <td>最高管控责任人</td>
        <td>评估日期</td>
        <td>解除日期</td>
    </tr>
    <tr>
        <td>1</td>
        <td>4305工作面</td>
        <td>煤尘爆炸</td>
        <td>煤尘具有爆炸性，爆炸指数37.21%，可发生煤尘爆炸事故。</td>
        <td>重大风险</td>
        <td>按钮</td>
        <td>1</td>
        <td>贾长玉</td>
        <td>2018-02-01</td>
        <td>2018-06-01</td>
    </tr>


</table>
</t:formvalid>
</div>
<div style="height: 10px"></div>
<div>
    <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBFineController.do?doUpdate" tiptype="3" callback="showTips" btnsub="btn_sub">
        <table style="width: 100%;table-layout: fixed;border-spacing: 0;border-collapse: collapse;" cellpadding="0" cellspacing="1" class="formtable" align="center">
            <tr>
                <td>危害因素</td>
                <td>管控措施</td>
                <td>危害因素等级</td>
                <td>管控单位</td>
                <td>管控责任人</td>
                <td>信息来源</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>


        </table>
    </t:formvalid>
</div>
</div>

<div style="height: 100px"></div>
<div style="text-align: center;"><span style="font-size:20px;font-weight: bold; text-align: center">提交修改数据</span></div>
<div style="border: 1px #000000 solid;padding-bottom: 100px;margin: 10px;">
<div>
    <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBFineController.do?doUpdate" tiptype="3" callback="showTips" btnsub="btn_sub">
        <table style="width: 100%;table-layout: fixed;border-spacing: 0;border-collapse: collapse;" cellpadding="0" cellspacing="1" class="formtable" align="center">
            <%--<tr style="height: 40px;">--%>
                <%--<td colspan='10' align="center" style="font-size:20px;font-weight: bold;">提交修改数据</td>--%>
            <%--</tr>--%>
            <tr>
                <td>序号</td>
                <td>风险点</td>
                <td>风险类型</td>
                <td>风险描述</td>
                <td>风险等级</td>
                <td>危害因素</td>
                <td>最高管控层级</td>
                <td>最高管控责任人</td>
                <td>评估日期</td>
                <td>解除日期</td>
            </tr>
            <tr>
                <td>1</td>
                <td>4305工作面</td>
                <td>煤尘爆炸</td>
                <td>煤尘具有爆炸性，爆炸指数37.21%，可发生煤尘爆炸事故。</td>
                <td>重大风险</td>
                <td>按钮</td>
                <td>1</td>
                <td>贾长玉</td>
                <td>2018-02-01</td>
                <td>2018-06-01</td>
            </tr>

        </table>
    </t:formvalid>
</div>
<div style="height: 10px"></div>
<div>
    <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBFineController.do?doUpdate" tiptype="3" callback="showTips" btnsub="btn_sub">
        <table style="width: 100%;table-layout: fixed;border-spacing: 0;border-collapse: collapse;" cellpadding="0" cellspacing="1" class="formtable" align="center">
            <tr>
                <td>危害因素</td>
                <td>管控措施</td>
                <td>危害因素等级</td>
                <td>管控单位</td>
                <td>管控责任人</td>
                <td>信息来源</td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
            </tr>

        </table>
    </t:formvalid>
</div>
</div>
<div style="height: 20px"></div>
<div class="ui_buttons" style="text-align: center;">
    <input onclick="test(1)" value="同意" class="ui_state_highlight" type="button">
    <input onclick="back(2)" value="驳回" class="ui_state_highlight" type="button">
    <input onclick="back(3);" value="驳回意见上传" type="button">

</div>
</body>
<html>