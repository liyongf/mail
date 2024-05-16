<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>隐患检查</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <link href="plug-in/ystep-master/css/ystep.css" rel="stylesheet">
    <script type="text/javascript" src="plug-in/ystep-master/js/ystep.js"></script>

    <script type="text/javascript">
        $(document).ready(function(){
            var from = "${from}";
            if("universalDangerList" != from){
                $.ajax({
                    type: 'POST',
                    url: 'tBDangerSourceController.do?getHandleStepList',
                    data: {dangerId:"${tBDangerSourcePage.id}"},
                    success:function(data){
                        var result = jQuery.parseJSON(data);
                        console.log(data);
                        $("#flow").loadStep({
                            size: "large",
                            color: "blue",
                            steps: result
                        });
                        var currentStep = "${fn:length(handleList)}";
                        var step = parseInt(currentStep);
                        $("#flow").setStep(step);
                    },
                    error:function(data){
                    }
                });
            }

        });
    </script>
</head>
<body>
<div title="流程图" id="flow" style="text-align:center;width: 80%;margin:20px auto;position: relative;top:60px;"></div>

<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBHiddenDangerExamController.do?doUpdate" tiptype="1">
<input id="id" name="id" type="hidden" value="${tBDangerSourcePage.id }">
<input type="hidden" id="selectedFineEmp" name="selectedFineEmp"/>
<table style="width: 80%;margin:0 auto;position: relative;top: 60px;" cellpadding="0" cellspacing="1" class="formtable">
    <tr>
        <td align="center"  colspan="4" class="value">
            <label class="Validform_label">
                辨识时间：<fmt:formatDate value='${tBDangerSourcePage.yeRecognizeTime}' type="date" pattern="yyyy-MM-dd"/>
            </label>
        </td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                专业分类:
            </label>
        </td>
        <td class="value">
            <label>${tBDangerSourcePage.yeProfessiontemp}</label>
        </td>
        <td align="right">
            <label class="Validform_label">
                事故类型:
            </label>
        </td>
        <td class="value">
            <label>${tBDangerSourcePage.yeAccidentTemp}</label>
        </td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                责任部门:
            </label>
        </td>
        <td class="value" colspan="3">
            <label>
                    ${tBDangerSourcePage.yeResDepart}
            </label>
        </td>
        <%--<td align="right">
            <label class="Validform_label">
                危险距离:
            </label>
        </td>
        <td class="value">
            <label>${tBDangerSourcePage.yeDistance}</label>
        </td>--%>
    </tr>

    <c:if test="${initParam.les == 'no'}">
    <tr>
        <td align="right">
            <label class="Validform_label">
                可能性:
            </label>
        </td>
        <td class="value">
            <label>${tBDangerSourcePage.yeProbabilityTemp}</label>
        </td>
        <td align="right">
            <label class="Validform_label">
                损失:
            </label>
        </td>
        <td class="value">
            <label>${tBDangerSourcePage.yeCostTemp}</label>
        </td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                风险值:
            </label>
        </td>
        <td class="value" colspan="3">
            <label>${tBDangerSourcePage.riskValue}</label>
        </td>
    </tr>
    </c:if>
    <c:if test="${initParam.les == 'yes'}">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    可能性:
                </label>
            </td>
            <td class="value">
                <label>${tBDangerSourcePage.lecRiskPossibilityTemp}</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    损失:
                </label>
            </td>
            <td class="value">
                <label>${tBDangerSourcePage.lecRiskLossTemp}</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    人员暴露于危险环境中的频繁程度:
                </label>
            </td>
            <td class="value">
                <label>${tBDangerSourcePage.lecExposureTemp}</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    风险值:
                </label>
            </td>
            <td class="value" colspan="3">
                <label>${tBDangerSourcePage.lecRiskValue}</label>
            </td>
        </tr>
    </c:if>

    <tr>
        <td align="right">
            <label class="Validform_label">
                风险等级:
            </label>
        </td>
        <td class="value">${tBDangerSourcePage.yeRiskGradeTemp}</td>
        <td align="right">
            <label class="Validform_label">
                风险类型:
            </label>
        </td>
        <td class="value">${tBDangerSourcePage.yeHazardCateTemp}</td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                备案号:
            </label>
        </td>
        <td class="value" colspan="3">${tBDangerSourcePage.yeCaseNum}</td>
    </tr>
        <%--<tr>
            <td align="right">
                <label class="Validform_label">
                    具体位置:
                </label>
            </td>
            <td class="value" colspan="3">
                <label>${tBDangerSourcePage.yeLocation}</label>
            </td>
        </tr>--%>
    <tr>
        <td align="right">
            <label class="Validform_label">
                危险源描述:
            </label>
        </td>
        <td class="value" colspan="3">
            <label>${tBDangerSourcePage.yeMhazardDesc}</label>
        </td>
    </tr>
    <%--<tr>
        <td align="right">
            <label class="Validform_label">
                确定依据:
            </label>
        </td>
        <td class="value" colspan="3">
            <label>${tBDangerSourcePage.yeReference}</label>
        </td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                周边情况及相互影响因素:
            </label>
        </td>
        <td class="value" colspan="3" width="80%">
            <label>${tBDangerSourcePage.yeSurrounding}</label>
        </td>
    </tr>--%>
    <tr>
        <td align="right">
            <label class="Validform_label">
                管控标准:
            </label>
        </td>
        <td class="value" colspan="3" width="80%">
            <label>${tBDangerSourcePage.yeStandard}</label>
        </td>
    </tr>
    <%--<tr>
        <td align="right">
            <label class="Validform_label">
                监控措施:
            </label>
        </td>
        <td class="value" colspan="3" width="80%">
            <label>${tBDangerSourcePage.yeMonitor}</label>
        </td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                应急措施:
            </label>
        </td>
        <td class="value" colspan="3" width="80%">
            <label>${tBDangerSourcePage.yeEmergency}</label>
        </td>
    </tr>--%>
    <tr>
        <td align="right">
            <label class="Validform_label">
                可能造成的风险及后果描述:
            </label>
        </td>
        <td class="value" colspan="3" width="80%">
            <label>${tBDangerSourcePage.yePossiblyHazard}</label>
        </td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                管控措施:
            </label>
        </td>
        <td class="value" colspan="3" width="80%">
            <label>${tBDangerSourcePage.manageMeasure}</label>
        </td>
    </tr>

    <%-----------------------------------------以下 是审批历史
    <tr>
        <td align="center" colspan="4">
            <label class="Validform_label">
                处理历史:
            </label>
        </td>
    </tr>
    <tr>
        <td align="center" colspan="4">
            <table style="width:100%">
                <tr>
                    <td align="center">
                        <label class="Validform_label">
                            处理类型
                        </label>
                    </td>
                    <td align="center">
                        <label class="Validform_label">
                            处理人
                        </label>
                    </td>
                    <td align="center">
                        <label class="Validform_label">
                            处理时间
                        </label>
                    </td>
                    <td align="center">
                        <label class="Validform_label">
                            备注
                        </label>
                    </td>
                </tr>
                <c:forEach items="${handleList}" var="handleStepInfo" varStatus="index">
                    <tr>
                        <td align="center" class="value">
                            <label class="Validform_label">
                                    ${handleStepInfo.dealStepName}
                            </label>
                        </td>
                        <td align="center" class="value">
                            <label class="Validform_label">
                                    ${handleStepInfo.dealManName}
                            </label>
                        </td>
                        <td align="center" class="value">
                            <label class="Validform_label">
                                <fmt:formatDate value='${handleStepInfo.dealTime}' type="date" pattern="yyyy-MM-dd"/>
                            </label>
                        </td>
                        <td align="center" class="value">
                            <label class="Validform_label">
                                    ${handleStepInfo.dealDesc}
                            </label>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </td>
    </tr>
    --------------------------------------------%>
</table>
</t:formvalid>
</body>

<script>

</script>

