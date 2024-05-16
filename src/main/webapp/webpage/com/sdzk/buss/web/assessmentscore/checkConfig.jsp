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
    <script type="text/javascript">
        //var magicsuggestRiskMan = "";
        var magicsuggestTaskMan = "";
        var magicsuggestTaskMan2 = "";
        $(function () {
            /*magicsuggestRiskMan = $('#magicsuggestRiskMan').magicSuggest({
                allowFreeEntries: false,
                data:'magicSelectController.do?getUserList',
                valueField:'id',
                placeholder:'输入或选择',
                maxSelection:10,
                selectFirst: true,
                highlight: false,
                matchField:['spelling','realName','userName','fullSpelling'],
                displayField:'realName',
                value: '${assessMentScore.riskMan}'!=''?'${assessMentScore.riskMan}'.split(","):[],
            });
            $(magicsuggestRiskMan).on('selectionchange', function(e,m){
                $("#riskMan").val(magicsuggestRiskMan.getValue());
            });*/
            magicsuggestTaskMan = $('#magicsuggestTaskMan').magicSuggest({
                allowFreeEntries: false,
                data:'magicSelectController.do?getUserList',
                valueField:'id',
                placeholder:'输入或选择',
                maxSelection:30,
                selectFirst: true,
                highlight: false,
                matchField:['spelling','realName','userName','fullSpelling'],
                displayField:'realName',
                value: '${assessMentScore.taskMan}'!=''?'${assessMentScore.taskMan}'.split(","):[],
            });
            $(magicsuggestTaskMan).on('selectionchange', function(e,m){
                $("#taskMan").val(magicsuggestTaskMan.getValue());
            });
            magicsuggestTaskMan2 = $('#magicsuggestTaskMan2').magicSuggest({
                allowFreeEntries: false,
                data:'magicSelectController.do?getUserList',
                valueField:'id',
                placeholder:'输入或选择',
                maxSelection:30,
                selectFirst: true,
                highlight: false,
                matchField:['spelling','realName','userName','fullSpelling'],
                displayField:'realName',
                value: '${assessMentScore.taskMan2}'!=''?'${assessMentScore.taskMan2}'.split(","):[],
            });
            $(magicsuggestTaskMan2).on('selectionchange', function(e,m){
                $("#taskMan2").val(magicsuggestTaskMan2.getValue());
            });
        })

    </script>
    <%--<style>
        td{
            padding:0px;
            text-align:center;
            vertical-align:center;
        }
        .td-padding{
            padding-left: 8px !important;
            padding-right: 8px !important;
            text-align: left !important;
        }
        .td-padding label{
            text-align: left !important;
            word-wrap:break-word !important;
            word-break: break-all !important;
            white-space: inherit !important;
        }
        label{
            padding:0 !important;
        }
        tr{
            height:30px;
        }
        .inputxt{
            margin-top: 20px;
        }
    </style>--%>
</head>
<body>
<t:formvalid formid="formobj" dialog="false" layout="table" action="assessmentScoreController.do?save"  btnsub="btn" callback="callbackFun" >
    <input id="id" name="id" type="hidden" value="${assessMentScore.id }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable" align="center">
        <tr style="height: 40px;">
            <td class="value" colspan="3" style="font-size:20px;font-weight: bold;text-align: center">考核扣分配置</td>
        </tr>
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
                    辨识组织考核
                </label>
            </td>
        </tr>
        <tr>
            <%--<td class="td-padding">
                <label class="Validform_label">
                    <span style="float: left;margin-top: 5px">管理人员</span>
                    <div id="magicsuggestRiskMan" style="width:605px;height: auto;"></div>
                    <input type="hidden" name="riskMan" id="riskMan" value="" class="inputxt">
                    未按规定时间内组织辨识扣<input id="riskManScore" name="riskManScore" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.riskManScore}"/>分
                </label>
            </td>--%>
                <td class="td-padding">
                    <label class="Validform_label">参与人员未按规定时间内进行风险辨识扣<input id="risk" name="risk" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.risk}"/>分
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
                    管控任务考核
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    未按照规定时间内完成管控任务扣
                    <input id="task" name="task" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.task}"/>分
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
                    管控效果考核
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每出现一次重大管控不到位，管控单位扣
                    <input id="hdManageDuty1" name="hdManageDuty1" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdManageDuty1}"/>分,
                    管控责任人扣
                    <input id="hdManageMan1" name="hdManageMan1" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdManageMan1}"/>分
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每出现一次较大管控不到位，管控单位扣
                    <input id="hdManageDuty2" name="hdManageDuty2" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdManageDuty2}"/>分,
                    管控责任人扣
                    <input id="hdManageMan2" name="hdManageMan2" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdManageMan2}"/>分
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每出现一次一般管控不到位，管控单位扣
                    <input id="hdManageDuty3" name="hdManageDuty3" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdManageDuty3}"/>分,
                    管控责任人扣
                    <input id="hdManageMan3" name="hdManageMan3" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdManageMan3}"/>分
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每出现一次低级管控不到位，管控单位扣
                    <input id="hdManageDuty4" name="hdManageDuty4" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdManageDuty4}"/>分,
                    管控责任人扣
                    <input id="hdManageMan4" name="hdManageMan4" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdManageMan4}"/>分
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
                    隐患治理考核
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每一条重大隐患超期未整改，责任单位扣
                    <input id="hdDutyUnit1" name="hdDutyUnit1" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdDutyUnit1}"/>分,
                    责任人扣
                    <input id="hdDutyMan1" name="hdDutyMan1" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdDutyMan1}"/>分
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每一条A级隐患超期未整改，责任单位扣
                    <input id="hdDutyUnit2" name="hdDutyUnit2" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdDutyUnit2}"/>分,
                    责任人扣
                    <input id="hdDutyMan2" name="hdDutyMan2" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdDutyMan2}"/>分
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每一条B级隐患超期未整改，责任单位扣
                    <input id="hdDutyUnit3" name="hdDutyUnit3" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdDutyUnit3}"/>分,
                    责任人扣
                    <input id="hdDutyMan3" name="hdDutyMan3" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdDutyMan3}"/>分
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    每一条C级隐患超期未整改，责任单位扣
                    <input id="hdDutyUnit4" name="hdDutyUnit4" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdDutyUnit4}"/>分,
                    责任人扣
                    <input id="hdDutyMan4" name="hdDutyMan4" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.hdDutyMan4}"/>分
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
                    管控组织考核
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    <span style="float: left;margin-top: 5px">综合管控管理人员</span>
                    <div id="magicsuggestTaskMan" style="width:605px;height: auto;"></div>
                    <input type="hidden" name="taskMan" id="taskMan" value="" class="inputxt">
                    未按规定时间内组织管控扣<input id="taskManScore" name="taskManScore" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.taskManScore}"/>分
                </label>
            </td>
        </tr>
        <tr>
            <td class="td-padding">
                <label class="Validform_label">
                    <span style="float: left;margin-top: 5px">专项管控管理人员</span>
                    <div id="magicsuggestTaskMan2" style="width:605px;height: auto;"></div>
                    <input type="hidden" name="taskMan2" id="taskMan2" value="" class="inputxt">
                    未按规定时间内组织管控扣<input id="taskManScore2" name="taskManScore2" type="text" style="width: 30px" oninput = "value=value.replace(/[^\d]/g,'')" value="${assessMentScore.taskManScore2}"/>分
                </label>
            </td>
        </tr>
        <tr>
            <td colspan="4">
                <div class="ui_buttons" style="text-align: center;">
                    <input type="button" id="btn" value="保存" class="ui_state_highlight" style="display:none">
                    <input type="button" id="btn_draft" value="保存" class="ui_state_highlight" onclick="formSubmit()">
                </div>
            </td>
        </tr>
    </table>
</t:formvalid>

<script type="text/javascript">


    function formSubmit() {
        $("#btn").trigger("click");
    }

    //保存回调
    function callbackFun(data) {
        tip(data.msg);
    }
</script>
</body>

