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
        var magicSuggestPostSelected = "";
        var magicSuggestActivitySelected = "";
        var magicSuggestHazardSelected = "";
        $(function() {
            magicSuggestPostSelected = getPostMagicSuggest($("#postname"), $("input[name='post.id']"));
            magicSuggestActivitySelected = getActivityMagicSuggest($("#activityname"), $("input[name='activity.id']"));
            magicSuggestHazardSelected = getHazardMagicSuggest($("#hazardname"), $("input[name='hazard.id']"));
        });

        function showTips(data){
            tip(data.msg);
            window.top.reload_tBDangerSourceList.call();
            setTimeout("$('#btn_close').click()",2*1000);
            doCloseTab();
        }
        function formSubmit(reportStatus){
            $("#reportStatus").val(reportStatus);
            $("#btn_sub").trigger("click");
        }
        function setIsMajorYes(){
            $("#yeRiskGrade").val("重大风险");
        }
    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" action="tBDangerSourceController.do?doAddDepartDangerSource" tiptype="3" callback="showTips" btnsub="btn_sub">
    <input id="id" name="id" type="hidden" value="${tBDangerSourcePage.id }"/>
    <input id="threshold_major" name="threshold_major" type="hidden" value="${threshold_major}"/>
    <input id="threshold_superior" name="threshold_superior" type="hidden" value="${threshold_superior}"/>
    <input id="threshold_commonly" name="threshold_commonly" type="hidden" value="${threshold_commonly}"/>
    <input id="threshold_lec_major" name="threshold_lec_major" type="hidden" value="${threshold_lec_major}"/>
    <input id="threshold_lec_superior" name="threshold_lec_superior" type="hidden" value="${threshold_lec_superior}"/>
    <input id="threshold_lec_commonly" name="threshold_lec_commonly" type="hidden" value="${threshold_lec_commonly}"/>
    <input id="reportStatus" name="reportStatus" type="hidden" value="0"/>
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    风险点类型:
                </label>
            </td>
            <td class="value">
                <t:dictSelect id="addressCate" field="addressCate" typeGroupCode="addressCate" extendJson="{'onchange':'fillFXDJFXZ();'}" hasLabel="false" defaultVal=""></t:dictSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">风险点类型</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>辨识时间:
                </label>
            </td>
            <td class="value">
                <input id="yeRecognizeTime" name="yeRecognizeTime" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" datatype="*" />
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">辨识时间</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>专业:
                </label>
            </td>
            <td class="value">
                <t:dictSelect id="yeProfession" field="yeProfession" typeGroupCode="proCate_gradeControl" hasLabel="false" defaultVal="" datatype="*"></t:dictSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">专业</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>危险源名称:
                </label>
            </td>
            <td class="value">
                <input type="hidden" name="hazard.id" id="hazard.id" datatype="*" />
                <div id="hazardname" name="hazardname" style="width: 130px;height: 15px"></div>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">危险源名称</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>伤害类别:
                </label>
            </td>
            <td class="value" colspan="3">
                <c:forEach items="${damageTypeList}" var="tsType" varStatus="index">
                    <label>
                        <input type="checkbox" name="damageType"  value="${tsType.typecode}" >${tsType.typename}
                    </label>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <c:if test="${index.index == 9}">
                        <br/>
                    </c:if>
                </c:forEach>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">伤害类别</label>
            </td>
                <%--<td class="value">
                    <t:dictSelect id="damageType" field="damageType" typeGroupCode="danger_Category" hasLabel="false" defaultVal="" datatype="*"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">伤害类别</label>
                </td>--%>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    事故类型:
                </label>
            </td>
            <td class="value" colspan="3">
                <c:forEach items="${tsTypeList}" var="tsType">
                    <label>
                        <input type="checkbox" name="yeAccident"  value="${tsType.typecode}" >${tsType.typename}
                    </label>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </c:forEach>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">事故类型</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险描述:
                </label>
            </td>
            <td class="value">
                <textarea id="yePossiblyHazard" name="yePossiblyHazard" class="inputxt" rows="3" style="width: 95%;" datatype="*" ></textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">风险描述</label>
            </td>
            <c:if test="${initParam.les == 'no'}">
            <td colspan="2" class="value"></td>
            </c:if>

            <c:if test="${initParam.les == 'yes'}">
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>作业活动:
                </label>
            </td>
            <td class="value">
                <input type="hidden" name="activity.id" id="activity.id" datatype="*" />
                <div id="activityname" name="activityname" style="width: 130px;height: 15px"></div>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">作业活动</label>
            </td>
            </c:if>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    是否重大风险:
                </label>
            </td>
            <td class="value" colspan="3">
                <label><input id="notMajor" name="isMajorTemp" type="radio" value="0" checked="checked" disabled />否</label>
                <label><input id="isMajor"  name="isMajorTemp" type="radio" value="1" onclick="setIsMajorYes()" disabled />是</label>
            </td>
        </tr>
        <c:if test="${initParam.les == 'no'}">
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>风险可能性:
                    </label>
                </td>
                <td class="value">
                    <t:dictSelect id="yeProbability" field="yeProbability" datatype="*" typeGroupCode="probability" extendJson="{'onchange':'fillFXDJFXZ();'}" hasLabel="false" defaultVal=""></t:dictSelect>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险可能性</label>
                </td>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>风险损失:
                    </label>
                </td>
                <td class="value">
                    <t:dictSelect id="yeCost" field="yeCost" datatype="*" typeGroupCode="hazard_fxss" extendJson="{'onchange':'fillFXDJFXZ();'}" hasLabel="false" defaultVal=""></t:dictSelect>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险损失</label>
                </td>
            </tr>
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
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>风险等级:
                    </label>
                </td>
                <td class="value" colspan="3">
                    <input id="yeRiskGrade" name="yeRiskGrade" readonly="true" type="text" style="width: 150px" class="inputxt" value="">
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险等级</label>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>风险类型:
                    </label>
                </td>
                <td class="value">
                    <t:dictSelect id="yeHazardCate" field="yeHazardCate" typeGroupCode="hazardCate" hasLabel="false" defaultVal="" datatype="*"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险类型</label>
                </td>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>作业活动:
                    </label>
                </td>
                <td class="value">
                    <input type="hidden" name="activity.id" id="activity.id" datatype="*" />
                    <div id="activityname" name="activityname" style="width: 130px;height: 15px"></div>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">作业活动</label>
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
                    <input id="lecRiskValue" name="lecRiskValue" readonly="true" type="text" style="width: 150px" class="inputxt" value="">
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险值</label>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>风险等级:
                    </label>
                </td>
                <td class="value">
                    <input id="yeRiskGrade" name="yeRiskGrade" readonly="true" type="text" style="width: 150px" class="inputxt" value="">
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险等级</label>
                </td>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>风险类型:
                    </label>
                </td>
                <td class="value">
                    <t:dictSelect id="yeHazardCate" field="yeHazardCate" typeGroupCode="hazardCate" hasLabel="false" defaultVal="" datatype="*"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">风险类型</label>
                </td>
            </tr>
        </c:if>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    管控标准来源:
                </label>
            </td>
            <td class="value">
                <input id="docSource" name="docSource" type="text" style="width: 150px"/>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">管控标准来源</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    章节条款:
                </label>
            </td>
            <td class="value">
                <input id="sectionName" name="sectionName" type="text" style="width: 150px" />
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">章节条款</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>标准内容:
                </label>
            </td>
            <td class="value">
                <textarea id="yeStandard" name="yeStandard" class="inputxt" rows="3" style="width: 95%;height:auto;" datatype="*" ></textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">标准内容</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>管控措施:
                </label>
            </td>
            <td class="value">
                <textarea id="manageMeasure" name="manageMeasure" class="inputxt" rows="3" style="width: 95%;" datatype="*" ></textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">管控措施</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>责任岗位:
                </label>
            </td>
            <td class="value">
                <input type="hidden" name="post.id" id="post.id" datatype="*" />
                <div id="postname" name="postname" style="width: 130px;height: 15px"></div>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">岗位</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>隐患描述:
                </label>
            </td>
            <td class="value">
                <textarea id="yeMhazardDesc" name="yeMhazardDesc" class="inputxt" rows="3" style="width: 95%;height:auto;" datatype="*" ></textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">隐患描述</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    隐患等级:
                </label>
            </td>
            <td class="value">
                <t:dictSelect id="hiddenLevel" field="hiddenLevel" typeGroupCode="hiddenLevel" hasLabel="false" defaultVal=""></t:dictSelect>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">隐患等级</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    罚款金额:
                </label>
            </td>
            <td class="value">
                <input id="fineMoney" name="fineMoney" type="text" style="width: 150px"/>&nbsp;元
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">罚款金额</label>
            </td>
        </tr>
        <tr>
            <td colspan="4">
                <div class="ui_buttons" style="text-align: center;">
                    <input type="button" id="btn_sub" style="display: none;">
                    <input type="button" id="btn_save" value="保存" class="ui_state_highlight" onclick="formSubmit('0')">
                    <input type="button" id="btn_subRep" value="保存并提交" class="ui_state_highlight" onclick="formSubmit('1')">
                    <input type="button" id="closeBtn" onclick="javaScript:doCloseTab();" value="关闭">
                </div>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>
<script src = "webpage/com/sdzk/buss/web/dangersource/js/tBDangerSource.js"></script>
