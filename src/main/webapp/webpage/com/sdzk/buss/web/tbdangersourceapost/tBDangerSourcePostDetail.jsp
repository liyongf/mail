<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.jeecgframework.core.util.DicUtil" %>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>岗位通用危险源详情</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript">
    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" layout="table">
    <table style="width: 100%" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    辨识时间:
                </label>
            </td>
            <td class="value">
                <fmt:formatDate value='${tBDangerSourceaPostPage.recognizeTime}' type="date" pattern="yyyy-MM-dd"/>
            </td>
            <td align="right">
                <label class="Validform_label">
                    专业分类:
                </label>
            </td>
            <td class="value">
                ${tBDangerSourceaPostPage.professionaltypeTemp}
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    风险类型:
                </label>
            </td>
            <td class="value">
                ${tBDangerSourceaPostPage.riskTypeTemp}
            </td>
            <td align="right">
                <label class="Validform_label">
                    工种:
                </label>
            </td>
            <td class="value">
                ${tBDangerSourceaPostPage.postName}
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    事故类型:
                </label>
            </td>
            <td class="value" colspan="3" style="width:75%">
                ${tBDangerSourceaPostPage.accidentTypeTemp}
            </td>
        </tr>
        <%--<tr>--%>
            <%--<td align="right">--%>
                <%--<label class="Validform_label">--%>
                    <%--重大隐患:--%>
                <%--</label>--%>
            <%--</td>--%>
            <%--<td class="value" colspan="3" style="width:75%">--%>
                <%--<c:if test="${tBDangerSourceaPostPage.ismajor ne '1'}">否</c:if>--%>
                <%--<c:if test="${tBDangerSourceaPostPage.ismajor eq '1'}">是</c:if>--%>
            <%--</td>--%>
        <%--</tr>--%>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    责任部门:
                </label>
            </td>
            <td class="value">
                ${tBDangerSourceaPostPage.resDepart}
            </td>
            <td align="right">
                <label class="Validform_label">
                    市场内部价:
                </label>
            </td>
            <td class="value">
                    ${tBDangerSourceaPostPage.internalMarketPrice}
            </td>
            <%--<td align="right">
                <label class="Validform_label">
                    危险距离:
                </label>
            </td>
            <td class="value">
            ${tBDangerSourceaPostPage.distance}
            </td>--%>
        </tr>
        <c:if test="${initParam.les == 'no'}">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    风险可能性:
                </label>
            </td>
            <td class="value">
                ${tBDangerSourceaPostPage.riskPossibilityTemp}
            </td>
            <td align="right">
                <label class="Validform_label">
                    风险损失:
                </label>
            </td>
            <td class="value">
                ${tBDangerSourceaPostPage.riskLossTemp}
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">风险损失</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    风险值:
                </label>
            </td>
            <td class="value">
                ${tBDangerSourceaPostPage.riskValue}
            </td>
            <td align="right">
                <label class="Validform_label">
                    风险等级:
                </label>
            </td>
            <td class="value">
                ${tBDangerSourceaPostPage.riskLevelTemp}
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">风险等级</label>
            </td>
        </tr>
        </c:if>

        <c:if test="${initParam.les == 'yes'}">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    风险可能性:
                </label>
            </td>
            <td class="value">
                    ${tBDangerSourceaPostPage.lecRiskPossibilityTemp}
            </td>
            <td align="right">
                <label class="Validform_label">
                    风险损失:
                </label>
            </td>
            <td class="value">
                    ${tBDangerSourceaPostPage.lecRiskLossTemp}
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">风险损失</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    暴露在危险中的概率:
                </label>
            </td>
            <td class="value">
                    ${tBDangerSourceaPostPage.lecExposureTemp}
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">暴露在危险中的概率</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    风险值:
                </label>
            </td>
            <td class="value">
                    ${tBDangerSourceaPostPage.lecRiskValue}
            </td>
        </tr>
        </c:if>
        <%--<tr>--%>
            <%--<td align="right">--%>
                <%--<label class="Validform_label">--%>
                    <%--备案号:--%>
                <%--</label>--%>
            <%--</td>--%>
            <%--<td class="value">--%>
                <%--${tBDangerSourceaPostPage.caseNum}--%>
            <%--</td>--%>

        <%--</tr>--%>
        <c:if test="${initParam.les == 'no'}">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    风险名称:
                </label>
            </td>
            <td class="value" colspan="3">
                ${tBDangerSourceaPostPage.dangerName}
            </td>
        </tr>
        </c:if>
        <c:if test="${initParam.les == 'yes'}">
            <td align="right">
                <label class="Validform_label">
                    风险等级:
                </label>
            </td>
            <td class="value">
                    ${tBDangerSourceaPostPage.riskLevelTemp}
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">风险等级</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    风险名称:
                </label>
            </td>
            <td class="value">
                    ${tBDangerSourceaPostPage.dangerName}
            </td>
        </c:if>

        <tr>
            <td align="right">
                <label class="Validform_label">
                    管控标准:
                </label>
            </td>
            <td class="value" colspan="3" style="width:75%">
                ${tBDangerSourceaPostPage.mangStandards}
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    标准依据:
                </label>
            </td>
            <td class="value" colspan="3" style="width:75%">
                ${tBDangerSourceaPostPage.standardAccordance}
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    依据条目:
                </label>
            </td>
            <td class="value" colspan="3" style="width:75%">
                ${tBDangerSourceaPostPage.basedEntry}
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    风险后果描述:
                </label>
            </td>
            <td class="value" colspan="3" style="width:75%">
                ${tBDangerSourceaPostPage.riskAffectDesc}
            </td>
        </tr>
        <%--<tr>
            <td align="right">
                <label class="Validform_label">
                    周边情况及相互影响因素:
                </label>
            </td>
            <td class="value" colspan="3" style="width:75%">
                ${tBDangerSourceaPostPage.surrounding}
            </td>
        </tr>--%>
        <%--<tr>
            <td align="right">
                <label class="Validform_label">
                    监控措施:
                </label>
            </td>
            <td class="value" colspan="3" style="width:75%">
                ${tBDangerSourceaPostPage.monitor}
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    应急措施:
                </label>
            </td>
            <td class="value" colspan="3" style="width:75%">
                ${tBDangerSourceaPostPage.emergency}
            </td>
        </tr>--%>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    责任措施:
                </label>
            </td>
            <td class="value" colspan="3" style="width:75%">
                ${tBDangerSourceaPostPage.respMeasures}
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    管控措施:
                </label>
            </td>
            <td class="value" colspan="3" style="width:75%">
                ${tBDangerSourceaPostPage.mangMeasures}
            </td>
        </tr>
    </table>
</t:formvalid>
</body>