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
            <td  rowspan="2">
                <label class="Validform_label">
                    一
                </label>
            </td>

            <td class="td-padding">
                <label class="Validform_label" >
                    辨识组织考核 (${riskScore}分)
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">参与人员未按规定时间内进行风险辨识扣${assessMentScore.risk}分，共${riskNum}次，${riskScore}分。<a href="javascript:click('1')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
        <tr>
            <td  rowspan="2">
                <label class="Validform_label">
                    二
                </label>
            </td>

            <td class="td-padding">
                <label class="Validform_label" >
                    管控任务考核 (${taskAllManageScore}分)
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    未按照规定时间内完成管控任务扣${assessMentScore.task}分，共${taskAllManageNum}次，${taskAllManageScore}分。<a href="javascript:click('2')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
        <tr>
            <td  rowspan="5">
                <label class="Validform_label">
                    三
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
                    每出现一次重大管控不到位，管控责任人扣${assessMentScore.hdManageMan1}分，共${hiddenManage1Num}次，${hiddenManage1Score}分。<a href="javascript:click('3.1')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每出现一次较大管控不到位，管控责任人扣${assessMentScore.hdManageMan2}分，共${hiddenManage2Num}次，${hiddenManage2Score}分。<a href="javascript:click('3.2')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每出现一次一般管控不到位，管控责任人扣${assessMentScore.hdManageMan3}分，共${hiddenManage3Num}次，${hiddenManage3Score}分。<a href="javascript:click('3.3')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每出现一次低级管控不到位，管控责任人扣${assessMentScore.hdManageMan4}分，共${hiddenManage4Num}次，${hiddenManage4Score}分。<a href="javascript:click('3.4')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>


        <tr>
            <td  rowspan="5">
                <label class="Validform_label">
                    四
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
                    每一条重大隐患超期未整改，责任人扣${assessMentScore.hdDutyMan1}分，共${hidden1Num}次，${hidden1Score}分。<a href="javascript:click('4.1')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每一条A级隐患超期未整改，责任人扣${assessMentScore.hdDutyMan2}分，共${hidden2Num}次，${hidden2Score}分。<a href="javascript:click('4.2')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每一条B级隐患超期未整改，责任人扣${assessMentScore.hdDutyMan3}分，共${hidden3Num}次，${hidden3Score}分。<a href="javascript:click('4.3')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每一条C级隐患超期未整改，责任人扣${assessMentScore.hdDutyMan4}分，共${hidden4Num}次，${hidden4Score}分。<a href="javascript:click('4.4')" style="float: right">查看&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
                </label>
            </td>
        </tr>



        <tr>
            <td  rowspan="3">
                <label class="Validform_label">
                    五
                </label>
            </td>

            <td class="td-padding">
                <label class="Validform_label" >
                    管控组织考核 (${taskManScore}分)
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    <c:if test="${isTaskMan eq '0'}">
                        不属于综合管控管理人员
                    </c:if>
                    <c:if test="${isTaskMan eq '1'}">
                        未按规定时间内组织管控扣${assessMentScore.taskManScore}分，共${taskManNum}次，${taskMan1Score}分。
                    </c:if>
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    <c:if test="${isTaskMan2 eq '0'}">
                        不属于专项管控管理人员
                    </c:if>
                    <c:if test="${isTaskMan2 eq '1'}">
                        未按规定时间内组织管控扣${assessMentScore.taskManScore2}分，共${taskMan2Num}次，${taskMan2Score}分。
                    </c:if>
                </label>
            </td>
        </tr>
    </table>
</t:formvalid>

<script type="text/javascript">
    function click(type) {
        var url = "assessmentScoreController.do?detailList&type="+type+"&yearMonth=${yearMonth}&selectId=${selectId}&selectType=user";
        createdetailwindow('详情',url,900,500);
    }

    window.top["close_scoreDetailUser"]=function(){
       $.messager.progress('close');
       frameElement.api.close();
    };
</script>
</body>

