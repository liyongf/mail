<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>隐患检查</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBHiddenDangerExamController.do?doUpdate" tiptype="1">
<input id="id" name="id" type="hidden" value="${tBDangerSourcePage.id }">
<input type="hidden" id="selectedFineEmp" name="selectedFineEmp"/>
<table style="width: 80%;margin:0 auto;position: relative;top: 60px;" cellpadding="0" cellspacing="1" class="formtable">
    <tr>
        <td align="right">
            <label class="Validform_label">
                <font color="red">*</font>辨识时间:
            </label>
        </td>
        <td class="value"><fmt:formatDate value='${tBDangerSourcePage.yeRecognizeTime}' type="date" pattern="yyyy-MM-dd"/></td>
        <td align="right">
            <label class="Validform_label">
                <font color="red">*</font>专业:
            </label>
        </td>
        <td class="value">${tBDangerSourcePage.yeProfessiontemp}</td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                <font color="red">*</font>伤害类别:
            </label>
        </td>
        <td class="value">${tBDangerSourcePage.damageTypeTemp}</td>
        <td align="right">
            <label class="Validform_label">
                <font color="red">*</font>风险描述:
            </label>
        </td>
        <td class="value" style="width: 35%;">${tBDangerSourcePage.yePossiblyHazard}</td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                <font color="red">*</font>事故类型:
            </label>
        </td>
        <td class="value">${tBDangerSourcePage.yeAccidentTemp}</td>
        <td align="right">
            <label class="Validform_label">
                是否重大风险:
            </label>
        </td>
        <td class="value">
            <c:if test="${tBDangerSourcePage.isMajor ne '1'}">否</c:if>
            <c:if test="${tBDangerSourcePage.isMajor eq '1'}">是</c:if>
        </td>
    </tr>
    <c:if test="${initParam.les == 'no'}">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险可能性:
                </label>
            </td>
            <td class="value">${tBDangerSourcePage.yeProbabilityTemp}</td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险损失:
                </label>
            </td>
            <td class="value">${tBDangerSourcePage.yeCostTemp}</td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险值:
                </label>
            </td>
            <td class="value">${tBDangerSourcePage.riskValue}</td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险等级:
                </label>
            </td>
            <td class="value">${tBDangerSourcePage.yeRiskGradeTemp}</td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险类型:
                </label>
            </td>
            <td class="value" colspan="3">${tBDangerSourcePage.yeHazardCateTemp}</td>
        </tr>
    </c:if>

    <c:if test="${initParam.les == 'yes'}">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险可能性:
                </label>
            </td>
            <td class="value">${tBDangerSourcePage.lecRiskPossibilityTemp}
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险损失:
                </label>
            </td>
            <td class="value">${tBDangerSourcePage.lecRiskLossTemp}</td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>暴露在危险中的概率:
                </label>
            </td>
            <td class="value">${tBDangerSourcePage.lecExposureTemp}</td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险值:
                </label>
            </td>
            <td class="value">${tBDangerSourcePage.lecRiskValue}</td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险等级:
                </label>
            </td>
            <td class="value">${tBDangerSourcePage.yeRiskGradeTemp}</td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险类型:
                </label>
            </td>
            <td class="value">${tBDangerSourcePage.yeHazardCateTemp}</td>
        </tr>
    </c:if>
    <tr>
        <td align="right">
            <label class="Validform_label">
                管控标准来源:
            </label>
        </td>
        <td class="value">${tBDangerSourcePage.docSource}</td>
        <td align="right">
            <label class="Validform_label">
                章节条款:
            </label>
        </td>
        <td class="value">${tBDangerSourcePage.sectionName}</td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                <font color="red">*</font>标准内容:
            </label>
        </td>
        <td class="value">${tBDangerSourcePage.yeStandard}</td>
        <td align="right" style="width: 100px;">
            <label class="Validform_label">
                <font color="red">*</font>管控措施:
            </label>
        </td>
        <td class="value" style="width: 35%;">${tBDangerSourcePage.manageMeasure}</td>
    </tr>
    <tr>
        <td align="right" style="width: 100px;">
            <label class="Validform_label">
                <font color="red">*</font>隐患描述:
            </label>
        </td>
        <td class="value" colspan="3">${tBDangerSourcePage.yeMhazardDesc}</td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                <font color="red">*</font>隐患等级:
            </label>
        </td>
        <td class="value">${tBDangerSourcePage.hiddenLevelTemp}</td>
        <td align="right">
            <label class="Validform_label">
                罚款金额:
            </label>
        </td>
        <td class="value">${tBDangerSourcePage.fineMoney}&nbsp;元</td>
    </tr>
</table>
</t:formvalid>
</body>

