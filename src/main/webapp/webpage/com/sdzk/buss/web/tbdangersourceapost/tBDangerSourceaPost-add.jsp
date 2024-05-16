<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>岗位通用危险源</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
	<link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
	<script type="text/javascript">
		//编写自定义JS代码
		$(function(){
			var postSelect = getPostMagicSuggest($("#postSelect"), $("#post"));
		});
	</script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBDangerSourceaPostController.do?doAdd" tiptype="3" btnsub="btn_save" callback="@Override showTips">
	<input id="threshold_major" name="threshold_major" type="hidden" value="${threshold_major}"/>
	<input id="threshold_superior" name="threshold_superior" type="hidden" value="${threshold_superior}"/>
	<input id="threshold_commonly" name="threshold_commonly" type="hidden" value="${threshold_commonly}"/>
	<input id="threshold_lec_major" name="threshold_lec_major" type="hidden" value="${threshold_lec_major}"/>
	<input id="threshold_lec_superior" name="threshold_lec_superior" type="hidden" value="${threshold_lec_superior}"/>
	<input id="threshold_lec_commonly" name="threshold_lec_commonly" type="hidden" value="${threshold_lec_commonly}"/>
	<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>辨识时间:
				</label>
			</td>
			<td class="value">
				<input id="recognizeTime" name="recognizeTime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"
					   ignore="ignore"
					   value='<fmt:formatDate value='<%=new Date()%>' type="date" pattern="yyyy-MM-dd"/>'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">辨识时间</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>专业分类:
				</label>
			</td>
			<td class="value">
				<t:dictSelect id="professionaltype" field="professionaltype" typeGroupCode="proCate_gradeControl" hasLabel="false" defaultVal="" datatype="*"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">专业类型</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>风险类型:
				</label>
			</td>
			<td class="value">
				<t:dictSelect id="riskType" field="riskType" typeGroupCode="hazardCate" hasLabel="false" defaultVal="" datatype="*"></t:dictSelect>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">风险类型</label>
			</td>
			<!-- TODO -->
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>工种:
				</label>
			</td>
			<td class="value">
				<div id="postSelect" style="width: 130px;height: 15px"></div>
				<input id="post" name="post" type="hidden" style="width: 150px" class="inputxt"
					   datatype="*"
					   value=''>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">工种</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>事故类型:
				</label>
			</td>
			<td class="value" colspan="3">
				<c:forEach items="${tsTypeList}" var="tsType">
					<label>
						<input type="checkbox" name="accidentType"  value="${tsType.typecode}">${tsType.typename}
					</label>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</c:forEach>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">事故类型</label>
			</td>
		</tr>
		<%--<tr>--%>
			<%--<td align="right">--%>
				<%--<label class="Validform_label">--%>
					<%--<font color="red">*</font>重大隐患:--%>
				<%--</label>--%>
			<%--</td>--%>
			<%--<td class="value" colspan="3">--%>
				<%--<label><input name="ismajor" type="radio" value="0" checked="checked"/>否</label>--%>
				<%--<label><input name="ismajor" type="radio" value="1" />是</label>--%>
			<%--</td>--%>
		<%--</tr>--%>
		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>责任部门:
				</label>
			</td>
			<td class="value">
				<input id="resDepart" name="resDepart" type="text" style="width: 150px" class="inputxt"
					   ignore="ignore"
					   value=''>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">责任部门</label>
			</td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>内部市场价:
                </label>
            </td>
            <td class="value">
                <input id="internalMarketPrice" name="internalMarketPrice" type="text" style="width: 150px" class="inputxt"
                       ignore="ignore"
                       value=''>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">内部市场价</label>
            </td>
			<%--<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>危险距离:
				</label>
			</td>
			<td class="value">
				<input id="distance" name="distance" type="text" style="width: 150px" class="inputxt"
					   ignore="ignore"
					   value=''>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">危险距离</label>
			</td>--%>
		</tr>

        <c:if test="${initParam.les == 'no'}">
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>风险可能性:
                    </label>
                </td>
                <td class="value">
                    <t:dictSelect id="riskPossibility" field="riskPossibility" datatype="*" typeGroupCode="probability" extendJson="{'onchange':'fillFXDJFXZ();'}" hasLabel="false" defaultVal=""></t:dictSelect>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险可能性</label>
                </td>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>风险损失:
                    </label>
                </td>
                <td class="value">
                    <t:dictSelect id="riskLoss" field="riskLoss" datatype="*" typeGroupCode="hazard_fxss" extendJson="{'onchange':'fillFXDJFXZ();'}" hasLabel="false" defaultVal=""></t:dictSelect>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险损失</label>
                </td>
            </tr>
        </c:if>

        <c:if test="${initParam.les == 'yes'}">
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>风险可能性:
                    </label>
                </td>
                <td class="value">
                    <t:dictSelect id="lecRiskPossibility" field="lecRiskPossibility" datatype="*" typeGroupCode="lec_risk_probability" extendJson="{'onchange':'fillLECRiskValue();'}" hasLabel="false" defaultVal=""></t:dictSelect>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险可能性</label>
                </td>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>风险损失:
                    </label>
                </td>
                <td class="value">
                    <t:dictSelect id="lecRiskLoss" field="lecRiskLoss" datatype="*" typeGroupCode="lec_risk_loss" extendJson="{'onchange':'fillLECRiskValue();'}" hasLabel="false" defaultVal=""></t:dictSelect>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险损失</label>
                </td>
            </tr>

            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>暴露在危险中的概率:
                    </label>
                </td>
                <td class="value">
                    <t:dictSelect id="lecExposure" field="lecExposure" datatype="*" typeGroupCode="lec_exposure" extendJson="{'onchange':'fillLECRiskValue();'}" hasLabel="false" defaultVal=""></t:dictSelect>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">暴露在危险中的概率</label>
                </td>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>风险值:
                    </label>
                </td>
                <td class="value">
                    <input id="lecRiskValue" name="lecRiskValue" type="text" readonly="true" style="width: 150px" class="inputxt" value="">
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险值</label>
                </td>
            </tr>
        </c:if>

        <c:if test="${initParam.les == 'no'}">
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>风险值:
                    </label>
                </td>
                <td class="value">
                    <input id="riskValue" name="riskValue" readonly="true" type="text" style="width: 150px" class="inputxt" value="">
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险值</label>
                </td>
                <%--<td align="right">--%>
                    <%--<label class="Validform_label">--%>
                        <%--<font color="red">*</font>风险等级:--%>
                    <%--</label>--%>
                <%--</td>--%>
                <%--<td class="value" colspan="3">--%>
                    <%--<input id="riskLevel" name="riskLevel" readonly="true" type="text" style="width: 150px" class="inputxt" value="">--%>
                    <%--<span class="Validform_checktip"></span>--%>
                    <%--<label class="Validform_label" style="display: none;">风险等级</label>--%>
                <%--</td>--%>
                <%--<td align="right">--%>
                    <%--<label class="Validform_label">--%>
                        <%--<font color="red">*</font>风险等级:--%>
                    <%--</label>--%>
                <%--</td>--%>
                <%--<td class="value">--%>
                    <%--<t:dictSelect id="riskLevel" field="riskLevel" typeGroupCode="riskLevel"  hasLabel="false" defaultVal="" datatype="*"></t:dictSelect>--%>
                    <%--<span class="Validform_checktip"></span>--%>
                    <%--<label class="Validform_label" style="display: none;">风险等级</label>--%>
                <%--</td>--%>
            </tr>
        </c:if>
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>风险等级:
                    </label>
                </td>
                <td class="value" colspan="3">
                    <input id="riskLevel" name="riskLevel" readonly="true" type="text" style="width: 150px" class="inputxt" value="">
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险等级</label>
                </td>
                <%--<td align="right">--%>
                    <%--<label class="Validform_label">--%>
                        <%--<font color="red">*</font>风险等级:--%>
                    <%--</label>--%>
                <%--</td>--%>
                <%--<td class="value" colspan="3">--%>
                    <%--<t:dictSelect id="riskLevel" field="riskLevel" typeGroupCode="riskLevel"  hasLabel="false" defaultVal="" datatype="*"></t:dictSelect>--%>
                    <%--<span class="Validform_checktip"></span>--%>
                    <%--<label class="Validform_label" style="display: none;">风险等级</label>--%>
                <%--</td>--%>
            </tr>

		<tr>
			<%--<td align="right">--%>
				<%--<label class="Validform_label">--%>
					<%--<font color="red">*</font>备案号:--%>
				<%--</label>--%>
			<%--</td>--%>
			<%--<td class="value">--%>
				<%--<input id="caseNum" name="caseNum" type="text" style="width: 150px" class="inputxt"--%>
					   <%--datatype="*"--%>
					   <%--value=''>--%>
				<%--<span class="Validform_checktip"></span>--%>
				<%--<label class="Validform_label" style="display: none;">备案号</label>--%>
			<%--</td>--%>

		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>风险名称:
				</label>
			</td>
			<td class="value">
				<textarea id="dangerName" name="dangerName" class="inputxt" rows="3" style="width: 95%;height:auto;" datatype="*" ></textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">风险名称</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>管控标准:
				</label>
			</td>
			<td class="value">
				<textarea id="mangStandards" name="mangStandards" class="inputxt" rows="3" style="width: 95%;height:auto;" datatype="*" ></textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">管控标准</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>标准依据:
				</label>
			</td>
			<td class="value">
				<textarea id="standardAccordance" name="standardAccordance" class="inputxt" rows="3" style="width: 95%;height:auto;" datatype="*" ></textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">标准依据</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>依据条目:
				</label>
			</td>
			<td class="value">
				<textarea id="basedEntry" name="basedEntry" class="inputxt" rows="3" style="width: 95%;height:auto;" datatype="*" ></textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">依据条目</label>
			</td>
		</tr>

		<%--<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>监控措施:
				</label>
			</td>
			<td class="value">
				<textarea id="monitor" name="monitor" class="inputxt" rows="3" style="width: 95%;height:auto;" datatype="*" ></textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">监控措施</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>应急措施:
				</label>
			</td>
			<td class="value">
				<textarea id="emergency" name="emergency" class="inputxt" rows="3" style="width: 95%;height:auto;" datatype="*" ></textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">应急措施</label>
			</td>
		</tr>--%>
		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>责任措施:
				</label>
			</td>
			<td class="value">
				<textarea id="respMeasures" name="respMeasures" class="inputxt" rows="3" style="width: 95%;height:auto;" datatype="*" ></textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">责任措施</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>管控措施:
				</label>
			</td>
			<td class="value">
				<textarea id="mangMeasures" name="mangMeasures" class="inputxt" rows="3" style="width: 95%;height:auto;" datatype="*" ></textarea>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">管控措施</label>
			</td>
		</tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险后果描述:
                </label>
            </td>
            <td class="value" >
                <textarea id="riskAffectDesc" name="riskAffectDesc" class="inputxt" rows="3" style="width: 95%;height:auto;" datatype="*" ></textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">风险后果描述</label>
            </td>
        </tr>
		<tr>
			<td colspan="4">
				<div class="ui_buttons" style="text-align: center;">
					<input type="button" id="btn_save" value="保存" class="ui_state_highlight">
					<input type="button" id="closeBtn" onclick="javaScript:doCloseTab();" value="关闭">
				</div>
			</td>
		</tr>
	</table>
</t:formvalid>
</body>
<script src = "webpage/com/sdzk/buss/web/tbdangersourceapost/tBDangerSourceaPost.js"></script>
