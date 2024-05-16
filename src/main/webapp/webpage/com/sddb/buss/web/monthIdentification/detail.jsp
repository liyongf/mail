<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>隐患检查</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js?v=20200309"></script>
    <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
    <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
    <link href="plug-in/jQueryLabel/css/tab.css" type="text/css" rel="stylesheet" />
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
    <script type="text/javascript" src="plug-in/jQueryLabel/js/tab.js"></script>
    <link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
    <style>
        .comments {
            width: 100%;
            height: 90%;
            overflow: auto;
            word-break: break-all;
        }
    </style>
    <script type="text/javascript">
        //编写自定义JS代码
        var magicsuggestSelected = "";


        $(function() {
            magicsuggestSelected = $('#magicsuggest').magicSuggest({
                data: 'magicSelectController.do?getAddressList',
                allowFreeEntries: false,
                valueField: 'id',
                placeholder: '输入或选择',
                maxSelection: 1,
                selectFirst: true,
                highlight: false,
                displayField: 'address',
                value:["${data.address}"]
            })
            $("textarea").each(function(index,item){
                makeExpandingArea(item);
            })
        });
        function makeExpandingArea(el) {
            var timer = null;
            //由于ie8有溢出堆栈问题，故调整了这里
            var setStyle = function(el, auto) {
                if (auto) el.style.height = 'auto';
                el.style.height = el.scrollHeight + 'px';
            }
            var delayedResize = function(el) {
                if (timer) {
                    clearTimeout(timer);
                    timer = null;
                }
                timer = setTimeout(function() {
                    setStyle(el)
                }, 200);
            }
            if (el.addEventListener) {
                el.addEventListener('input', function() {
                    setStyle(el, 1);
                }, false);
                setStyle(el)
            } else if (el.attachEvent) {
                el.attachEvent('onpropertychange', function() {
                    setStyle(el)
                })
                setStyle(el)
            }
            if (window.VBArray && window.addEventListener) { //IE9
                el.attachEvent("onkeydown", function() {
                    var key = window.event.keyCode;
                    if (key == 8 || key == 46) delayedResize(el);

                });
                el.attachEvent("oncut", function() {
                    delayedResize(el);
                }); //处理粘贴
            }
        }
        function closeWindow() {
            window.top.$('.J_menuTab.active>i[class="fa fa-times-circle"]').trigger("click");
        }
    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="false" usePlugin="password"  layout="table" action="" tiptype="3" btnsub="btn" callback="callbackFun">
    <input id="id" name="id" type="hidden" value="${data.id }">

    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="left"  colspan="2" class="value">
                <label class="Validform_label">
                    加<font color="red">*</font>内容必填！
                </label>
            </td>
            <c:if test="${month eq 'true'}">
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>月份 :
                </label>
            </td>
            <td class="value">
                <input id="month" name="month"  style="width: 250px" class="inputxt" datatype="*" value="${data.month}">
            </td>
            </c:if>
            <c:if test="${month ne 'true'}">
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>季度 :
                    </label>
                </td>
                <td class="value">
                    <input id="month" name="month"  style="width: 250px" class="inputxt" datatype="*" value="${quarter}">
                </td>
            </c:if>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>信息来源:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="identificationType" type="list" extendJson="{\"datatype\":\"*\"}"
                              typeGroupCode="month_risk_source" defaultVal="${data.identificationType}" hasLabel="false"  title="信息来源"></t:dictSelect>
                <label class="Validform_label" style="display: none;">信息来源</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>落实资金 (万元):
                </label>
            </td>
            <td class="value">
                <input id="month" name="month"  style="width: 150px" class="inputxt" datatype="*" value="${data.fund}">
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>单位专业:
                </label>
            </td>
            <td class="value">
                <input id="month" name="month"  style="width: 250px" class="inputxt" datatype="*" value="${data.unitSpecialty}">
            </td>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>风险地点:
                    </label>
                </td>
                <td class="value">
                    <div id="magicsuggest" style="width: 230px;height: 15px"></div>
                    <input type="hidden" name="address.address" id="address" value="" class="inputxt" datatype="*">
                    <label class="Validform_label" style="display: none;">地点</label>
                </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险类型:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="riskType" type="list" extendJson="{\"datatype\":\"*\"}"
                              typeGroupCode="risk_type" defaultVal="${data.riskType}" hasLabel="false"  title="风险类型"></t:dictSelect>
                <label class="Validform_label" style="display: none;">风险类型</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险等级:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="riskLevel" type="list" extendJson="{\"datatype\":\"*\"}"
                              typeGroupCode="factors_level" defaultVal="${data.riskLevel}" hasLabel="false"  title="风险等级"></t:dictSelect>
                <label class="Validform_label" style="display: none;">风险等级</label>
            </td>
        </tr>

        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>风险描述:
                </label>
            </td>
            <td class="value" colspan="3">
                <textarea id="problemDesc" name="problemDesc" class="comments" type="text" style="width: 740px;"  datatype="*" >${data.riskDesc}</textarea>
                <label class="Validform_label" style="display: none;">风险描述</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>危害因素:
                </label>
            </td>
            <td class="value" colspan="3">
                <textarea id="hazardFactors" name="hazardFactors" class="comments" type="text" style="width: 740px;"  datatype="*" >${data.hazardFactors}</textarea>
                <label class="Validform_label" style="display: none;">危害因素</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>管控措施:
                </label>
            </td>
            <td class="value" colspan="3">
                <textarea id="controlMeasures" name="controlMeasures" class="comments" type="text" style="width: 740px;"  datatype="*" >${data.controlMeasures}</textarea>
                <label class="Validform_label" style="display: none;">管控措施</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red"></font>预计解决时间 :
                </label>
            </td>
            <td class="value">
                <input id="solveTime" name="solveTime"  style="width: 250px" class="inputxt" datatype="*" value="${data.solveTime}">
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red"></font>预案名称:
                </label>
            </td>
            <td class="value">
                <input id="planName" name="planName"  style="width: 280px" class="inputxt" datatype="*" value="${data.planName}">
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red"></font>管控单位及责任人 :
                </label>
            </td>
            <td class="value">
                <input id="dutyManager" name="dutyManager"  style="width: 250px" class="inputxt" datatype="*" value="${data.dutyManager}">
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red"></font>最高管控层级及责任人:
                </label>
            </td>
            <td class="value">
                <input id="manageLevel" name="manageLevel"  style="width: 280px" class="inputxt" datatype="*" value="${data.manageLevel}">
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red"></font>技术指导部门及负责人 :
                </label>
            </td>
            <td class="value">
                <input id="technical" name="technical"  style="width: 250px" class="inputxt" datatype="*" value="${data.technical}">
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red"></font>监管部门及责任人:
                </label>
            </td>
            <td class="value">
                <input id="supervision" name="supervision"  style="width: 280px" class="inputxt" datatype="*" value="${data.supervision}">
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red"></font>备注:
                </label>
            </td>
            <td class="value" colspan="3">
                <textarea id="remark" name="remark" class="comments" type="text" style="width: 743px;"  datatype="*" >${data.remark}</textarea>
                <label class="Validform_label" style="display: none;">备注</label>
            </td>
        </tr>
    </table>
</t:formvalid>
</body>

