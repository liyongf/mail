<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>员工诚信考核表</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
    <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
    <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
    <link href="plug-in/jQueryLabel/css/tab.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="plug-in/jQueryLabel/js/tab.js"></script>
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
    <link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
</head>
<body>
<t:formvalid formid="formobj" dialog="false" layout="table" action="assessmentScoreController.do?save"  btnsub="btn" callback="callbackFun" >
    <input id="id" name="id" type="hidden" value="${assessMentScore.id }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable" align="center">
        <tr>
            <td align="center">
                <label class="Validform_label">
                    序号
                </label>
            </td>
            <td align="center">
                <label class="Validform_label" style="min-width:150px;">
                </label>
            </td>
        </tr>
        <tr>
            <td  rowspan="5">
                <label class="Validform_label">
                    一
                </label>
            </td>

            <td class="td-padding">
                <label class="Validform_label" >
                    管控效果考核 (${hiddenManageScore}分)
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每出现一次重大管控不到位，管控单位扣${assessMentScore.hdManageDuty1}分，共${hiddenManage1Num}次，${hiddenManage1Score}分。<a href="javascript:click('3.1')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每出现一次较大管控不到位，管控单位扣${assessMentScore.hdManageDuty2}分，共${hiddenManage2Num}次，${hiddenManage2Score}分。<a href="javascript:click('3.2')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每出现一次一般管控不到位，管控单位扣${assessMentScore.hdManageDuty3}分，共${hiddenManage3Num}次，${hiddenManage3Score}分。<a href="javascript:click('3.3')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每出现一次低级管控不到位，管控单位扣${assessMentScore.hdManageDuty4}分，共${hiddenManage4Num}次，${hiddenManage4Score}分。<a href="javascript:click('3.4')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>


        <tr>
            <td  rowspan="5">
                <label class="Validform_label">
                    二
                </label>
            </td>

            <td class="td-padding">
                <label class="Validform_label" >
                    隐患治理考核 (${hiddenScore}分)
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每一条重大隐患超期未整改，责任单位扣${assessMentScore.hdDutyUnit1}分，共${hidden1Num}次，${hidden1Score}分。<a href="javascript:click('4.1')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每一条A级隐患超期未整改，责任单位扣${assessMentScore.hdDutyUnit2}分，共${hidden2Num}次，${hidden2Score}分。<a href="javascript:click('4.2')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每一条B级隐患超期未整改，责任单位扣${assessMentScore.hdDutyUnit3}分，共${hidden3Num}次，${hidden3Score}分。<a href="javascript:click('4.3')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每一条C级隐患超期未整改，责任单位扣${assessMentScore.hdDutyUnit4}分，共${hidden4Num}次，${hidden4Score}分。<a href="javascript:click('4.4')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
    </table>
</t:formvalid>

<script type="text/javascript">
    function click(type) {
        var url = "assessmentScoreController.do?detailList&type="+type+"&yearMonth=${yearMonth}&selectId=${selectId}&selectType=depart";
        createdetailwindow('详情',url,900,500);
    }
</script>
</body>

