<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>隐患编辑</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
    <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
    <link href="plug-in/jQueryLabel/css/tab.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
    <script type="text/javascript" src="plug-in/jQueryLabel/js/tab.js"></script>
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
    <link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
    <%--<script type="text/javascript">--%>
        <%--$(function() {--%>
            <%--// //移除重大隐患--%>
            <%--$("select[name='hiddenDanger.hiddenNature']>option[value='1']").remove();--%>
            <%--$("select[name='hiddenDanger.hiddenNature']>option[value='2']").remove();--%>
            <%--$("select[name='hiddenDanger.hiddenNature']>option[value='3']").remove()--%>
        <%--});--%>

    <%--</script>--%>
</head>

<body style="width: 95%;margin: 20px;">
<t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table"
             action="tBHiddenDangerHandleController.do?doAdminEdit" tiptype="3" btnsub="btn" callback="callbackFun">
    <table style="width: ${width}px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="left"  colspan="4" class="value">
                <label class="Validform_label">
                    加<font color="red">*</font>内容必填！
                </label>
            </td>
        </tr>
    </table>
    <div id="handleHiddenData">
        <input type="hidden" name="id" value="${handle.id}"/>
        <input id="handlelStatus" name="handlelStatus" type="hidden" value="${handle.handlelStatus}"/>
        <input id="createName" name="createName" type="hidden" value="${handle.createName}">
        <input id="createBy" name="createBy" type="hidden" value="${handle.createBy}">
        <input id="createDate" name="createDate" type="hidden" value="${handle.createDate}">
        <input id="updateName" name="updateName" type="hidden" value="${handle.updateName}">
        <input id="updateBy" name="updateBy" type="hidden" value="${handle.updateBy }">
        <input id="updateDate" name="updateDate" type="hidden" value="${handle.updateDate}">
        <input id="reportName" name="reportName" type="hidden" value="${handle.reportName}">
        <input id="reportDate" name="reportDate" type="hidden" value="${handle.reportDate}">
        <input id="reportStatus" name="reportStatus" type="hidden" value="${handle.reportStatus}">
        <input id="reportGroupTime" name="reportGroupTime" type="hidden" value="${handle.reportGroupTime}"/>
        <input id="reportGroupMan" name="reportGroupMan.id" type="hidden" value="${handle.reportGroupMan.id}"/>
        <input id="reportGroupStatus" name="reportGroupStatus" type="hidden" value="${handle.reportGroupStatus}"/>
        <input id="rollBackRemark" name="rollBackRemark" type="hidden" value="${handle.rollBackRemark}"/>
    </div>

    <div class="easyui-accordion" style="width:${width}px;height:${height}px;">
        <div title="隐患详情" iconCls="icon-ok" selected="true" style="overflow:auto;padding:10px;">
            <div id="examHiddenData">
                <input type="hidden" name="hiddenDanger.id" value="${handle.hiddenDanger.id}"/>
                <input id="hiddenDanger.createName" name="hiddenDanger.createName" type="hidden" value="${handle.hiddenDanger.createName }">
                <input id="hiddenDanger.createBy" name="hiddenDanger.createBy" type="hidden" value="${handle.hiddenDanger.createBy }">
                <input id="hiddenDanger.createDate" name="hiddenDanger.createDate" type="hidden" value="${handle.hiddenDanger.createDate }">
                <input id="hiddenDanger.updateName" name="hiddenDanger.updateName" type="hidden" value="${handle.hiddenDanger.updateName }">
                <input id="hiddenDanger.updateBy" name="hiddenDanger.updateBy" type="hidden" value="${handle.hiddenDanger.updateBy }">
                <input id="hiddenDanger.updateDate" name="hiddenDanger.updateDate" type="hidden" value="${handle.hiddenDanger.updateDate }">
                <input id="hiddenDanger.examType" name="hiddenDanger.examType" type="hidden" value="${handle.hiddenDanger.examType}">
            </div>
            <table style="width: ${width-50}px;" cellpadding="0" cellspacing="1" class="formtable">
                <tr>
                    <td align="right">
                        <label class="Validform_label">
                            <font color="red">*</font>日期:
                        </label>
                    </td>
                    <td class="value">
                        <input id="hiddenDanger.examDate" name="hiddenDanger.examDate" type="text" style="width: 150px"  datatype="*" class="Wdate"
                               onClick="WdatePicker({maxDate:'%y-%M-%d',dateFmt: 'yyyy-MM-dd HH:mm:ss'})" value='<fmt:formatDate value='${handle.hiddenDanger.examDate}'
                               type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">日期</label>
                    </td>
                    <td align="right">
                        <label class="Validform_label">
                            <font color="red">*</font>班次:
                        </label>
                    </td>
                    <td class="value">
                        <t:dictSelect field="hiddenDanger.shift" type="list" extendJson="{\"datatype\":\"*\"}" typeGroupCode="workShift" defaultVal="${handle.hiddenDanger.shift}" hasLabel="false"  title="班次"></t:dictSelect>
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">班次</label>
                    </td>
                </tr>
                <tr>

                    <c:if test="${handle.hiddenDanger.examType ne'kjaqdjc'}">
                        <td align="right">
                            <label class="Validform_label">
                                <font color="red">*</font>检查人:
                            </label>
                        </td>
                        <td class="value">
                            <div id="magicsuggestFillCardMan" style="width: 300px;height: 15px"></div>
                            <input type="hidden" name="hiddenDanger.fillCardManId" id="fillCardManId" style="width: 150px" class="inputxt" datatype="*" value="${handle.hiddenDanger.fillCardManId}" >
                            <input id="fillCardManName" name="fillCardManName" type="hidden" style="width: 150px" class="inputxt" value="${handle.hiddenDanger.fillCardMan.realName}" >
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">检查人</label>
                        </td>
                        <td align="right">
                            <label class="Validform_label">
                                <font color="red">*</font>信息来源:
                            </label>
                        </td>
                        <td class="value">
                            <t:dictSelect field="hiddenDanger.manageType" type="list" extendJson="{\"datatype\":\"*\"}"
                                          typeGroupCode="manageType"  hasLabel="false"  title="管控类型" defaultVal="${handle.hiddenDanger.manageType}"></t:dictSelect>
                            <span class="Validform_checktip">请选择信息来源</span>
                            <label class="Validform_label" style="display: none;">信息来源</label>
                        </td>
                    </c:if>
                </tr>
                    <tr>
                        <td align="right">
                            <label class="Validform_label">
                                <font color="red">*</font>地点:
                            </label>
                        </td>
                        <c:if test="${handle.hiddenDanger.examType eq'kjaqdjc'}">
                            <td class="value" colspan="3">
                                <div id="magicsuggestAddress" style="width: 130px;height: 15px"></div>
                                <input type="hidden" name="hiddenDanger.address.id" id="address" datatype="*" value="${handle.hiddenDanger.address.id}">
                                <span class="Validform_checktip"></span>
                                <label class="Validform_label" style="display: none;">地点</label>
                            </td>
                        </c:if>
                        <c:if test="${handle.hiddenDanger.examType ne 'kjaqdjc'}">
                            <td class="value">
                                <div id="magicsuggestAddress" style="width: 130px;height: 15px"></div>
                                <input type="hidden" name="hiddenDanger.address.id" id="address" datatype="*" value="${handle.hiddenDanger.address.id}">
                                <span class="Validform_checktip"></span>
                                <label class="Validform_label" style="display: none;">地点</label>
                            </td>
                        </c:if>

                        <td align="right">
                            <label class="Validform_label">
                                <font color="red">*</font>隐患类型:
                            </label>
                        </td>
                        <td class="value">
                            <t:dictSelect field="hiddenDanger.riskType" type="list" extendJson="{\"datatype\":\"*\"}"
                                          typeGroupCode="risk_type"  hasLabel="false"  title="隐患类型" defaultVal="${handle.hiddenDanger.riskType}"></t:dictSelect>
                            <span class="Validform_checktip">请选择隐患类型</span>
                            <label class="Validform_label" style="display: none;">隐患类型</label>
                        </td>
                    </tr>

                <tr>
                    <td align="right">
                        <label class="Validform_label">
                            风险描述:
                        </label>
                    </td>
                    <td class="value" colspan="3">
                        <div id="magicsuggestRisk" style="width: 400px;height: auto"></div>
                        <input type="hidden" name="hiddenDanger.riskId.id" id="riskId" value="${handle.hiddenDanger.riskId.id}" style="width: 150px" class="inputxt" >
                        <font color="red" id="riskLevel1" style="display: none">&nbsp;&nbsp;&nbsp;此风险为重大风险！</font>
                        <font color="red" id="riskLevel2" style="display: none">&nbsp;&nbsp;&nbsp;此风险为较大风险！</font>
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">风险描述</label>
                    </td>
                </tr>



                <tr>
                    <td align="right">
                        <label class="Validform_label">
                            <font color="red">*</font>责任单位:
                        </label>
                    </td>
                    <td class="value">
                        <div id="magicsuggestDutyUnitSel" style="width: 130px;height: 15px"></div>
                        <input id="dutyUnitId" name="hiddenDanger.dutyUnit.id" type="hidden" style="width: 150px" class="inputxt" datatype="*" value="${handle.hiddenDanger.dutyUnit.id}">
                        <span class="Validform_checktip">请选择责任单位</span>
                        <label class="Validform_label" style="display: none;">责任单位</label>
                    </td>
                    <td align="right">
                        <label class="Validform_label">
                            <font color="red">*</font>责任人:
                        </label>
                    </td>
                    <td class="value">
                        <div id="magicsuggestDutyMan" style="width: 130px;height: 15px"></div>
                        <input id="dutyMan" name="hiddenDanger.dutyMan" type="hidden" style="width: 150px" class="inputxt" datatype="*"  value="${handle.hiddenDanger.dutyMan}">
                        <span class="Validform_checktip">请选择责任人</span>
                        <label class="Validform_label" style="display: none;">责任人</label>
                    </td>
                </tr>

                <tr>
                    <td align="right">
                        <label class="Validform_label">
                            <font color="red">*</font>隐患等级:
                        </label>
                    </td>
                    <td class="value" colspan="3">
                        <t:dictSelect field="hiddenDanger.hiddenNature" type="list" extendJson="{\"datatype\":\"*\"}"
                                      typeGroupCode="hiddenLevel" defaultVal="${handle.hiddenDanger.hiddenNature}" hasLabel="false"  title="隐患等级"></t:dictSelect>
                        <span class="Validform_checktip">请选择隐患等级</span>
                        <label class="Validform_label" style="display: none;">隐患等级</label>
                    </td>
                </tr>

                <tr>
                    <td align="right">
                        <label class="Validform_label">
                            <font color="red">*</font>问题描述:
                        </label>
                    </td>
                    <td class="value" colspan="3">
                        <textarea id="problemDesc" name="hiddenDanger.problemDesc" type="text" style="width: 440px;" datatype="*"  >${handle.hiddenDanger.problemDesc}</textarea>
                        <span class="Validform_checktip">请填写问题描述</span>
                        <label class="Validform_label" style="display: none;">问题描述</label>
                    </td>
                </tr>

                <tr>
                    <td align="right">
                        <label class="Validform_label">
                            隐患处理:
                        </label>
                    </td>
                    <td class="value" colspan="3">
                        <label>
                            <input type="radio" name="hiddenDanger.dealType" id="dealType_xianqi" value="1" ${handle.hiddenDanger.dealType eq "1"? 'checked="checked" ':""}  onclick="showTr('dealTypetr1');">限期整改
                        </label>
                        <label>
                            <input type="radio" name="hiddenDanger.dealType" id="dealType_xianchang" value="2" ${handle.hiddenDanger.dealType eq "2"? 'checked="checked" ':""} onclick="showTr('xcclTR');">现场处理
                        </label>
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">隐患处理</label>
                    </td>
                </tr>
                <tr id="dealTypetr1">
                    <td align="right">
                        <label class="Validform_label">
                            <font color="red">*</font>限期日期:
                        </label>
                    </td>
                    <td class="value" colspan="3">

                        <input id="limitDate" name="hiddenDanger.limitDate" type="text" style="width: 150px" datatype="limDate" value='<fmt:formatDate value='${handle.hiddenDanger.limitDate}' type="date" pattern="yyyy-MM-dd"/>'>
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">限期日期</label>
                    </td>
                </tr>
                <tr class="xcclTR" style="display: none">
                    <td align="right">
                        <label class="Validform_label">
                            <font color="red">*</font>复查人:
                        </label>
                    </td>
                    <td class="value" colspan="3">
                        <div id="reviewManNameSelect" style="width: 130px;height: 15px"></div>
                        <input id="reviewManId" name="hiddenDanger.reviewMan.id" type="hidden" style="width: 150px" class="inputxt" value="${handle.hiddenDanger.reviewMan.id}" >
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">复查人</label>
                    </td>
                </tr>

                <tr class="xcclTR" style="display: none">
                    <td align="right">
                        <label class="Validform_label">整改措施:
                        </label>
                    </td>
                    <td class="value">
                        <textarea name="rectMeasures" id="rectMeasuresXccl" class="inputxt" style="width: 280px; height: 50px;" >${handle.rectMeasures}</textarea>
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">整改措施</label>
                    </td>
                    <td align="right">
                        <label class="Validform_label">复查情况:
                        </label>
                    </td>
                    <td class="value">
                        <textarea name="reviewReport" id="reviewReportXccl" class="inputxt" style="width: 280px; height: 50px;">${handle.reviewReport}</textarea>
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">复查情况</label>
                    </td>
                </tr>
            </table>
        </div>
        <div title="整改信息" iconCls="icon-ok" id="modifyData" style="overflow:auto;padding:10px;">
            <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
                <tr class="pass">
                    <td align="right">
                        <label class="Validform_label">
                            <b style="color: red">*</b>整改时间:
                        </label>
                    </td>
                    <td class="value">
                        <input id="modifyDate" name="modifyDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" datatype="*"
                               ignore="checked"
                               value='<fmt:formatDate value='${handle.modifyDate}' type="date" pattern="yyyy-MM-dd"/>'>
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">整改时间</label>
                    </td>
                </tr>
                <tr class="pass">
                    <td align="right">
                        <label class="Validform_label">
                            <b style="color: red">*</b>整改人:
                        </label>
                    </td>
                    <td class="value">
                        <div id="modifySelect" style="width: 130px;height: 15px;" class="inputxt"></div>
                        <input id="modifyMan" name="modifyMan" type="hidden"
                               datatype="*"
                               value='${handle.modifyMan}'>
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">整改人</label>
                    </td>
                </tr>
                <tr class="pass">
                    <td align="right">
                        <label class="Validform_label">
                            <b style="color: red">*</b>整改班次:
                        </label>
                    </td>
                    <td class="value">
                        <t:dictSelect field="modifyShift" type="list" extendJson="{\"datatype\":\"*\"}"
                                      typeGroupCode="workShift" defaultVal="${handle.modifyShift}" hasLabel="false"  title="班次"></t:dictSelect>
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">整改班次</label>
                    </td>
                </tr>
                <tr class="pass">
                    <td align="right">
                        <label class="Validform_label">
                            <b style="color: red">*</b>整改措施:
                        </label>
                    </td>
                    <td class="value">
                        <textarea name="rectMeasures" id="rectMeasures" class="inputxt" style="width: 280px; height: 100px;" datatype="*">${handle.rectMeasures}</textarea>
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">整改措施</label>
                    </td>
                </tr>
            </table>
        </div>
        <div title="复查信息" iconCls="icon-ok" id="reviewData" style="overflow:auto;padding:10px;">
                <input type="hidden" name="reviewResult" value="${handle.reviewResult}"/>
                <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
                    <tr>
                        <td align="right">
                            <label class="Validform_label">
                                <b style="color: red">*</b>复查时间:
                            </label>
                        </td>
                        <td class="value">
                            <input id="reviewDate" name="reviewDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" datatype="*"
                                   ignore="checked"
                                   value='<fmt:formatDate value='${handle.reviewDate}' type="date" pattern="yyyy-MM-dd"/>'>
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">复查时间</label>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <label class="Validform_label">
                                <b style="color: red">*</b>复查人:
                            </label>
                        </td>
                        <td class="value">
                            <div id="reviewSelect" style="width: 130px;height: 15px;" class="inputxt"></div>
                            <input id="reviewMan" name="reviewMan" type="hidden"
                                   datatype="*"
                                   value='${handle.reviewMan}'>
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">复查人</label>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">
                            <label class="Validform_label">
                                <b style="color: red">*</b>复查班次:
                            </label>
                        </td>
                        <td class="value">
                            <t:dictSelect field="reviewShift" type="list" extendJson="{\"datatype\":\"*\"}"
                                          typeGroupCode="workShift" defaultVal="${handle.reviewShift}" hasLabel="false"  title="班次"></t:dictSelect>
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">复查班次</label>
                        </td>
                    </tr>
                    <tr class="pass">
                        <td align="right">
                            <label class="Validform_label">
                                <b style="color: red">*</b>复查情况:
                            </label>
                        </td>
                        <td class="value">
                            <textarea name="reviewReport" id="reviewReport" class="inputxt" style="width: 280px; height: 100px;" datatype="*">${handle.reviewReport}</textarea>
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">复查情况</label>
                        </td>
                    </tr>
                </table>
            </div>
    </div>
    <div class="ui_buttons" style="width:${width}px;padding-top:10px;text-align: center;">
        <input type="button" id="btn" value="保存" class="ui_state_highlight">
        <input type="button" id="btn_close" onclick="closeWindow();" value="关闭">
    </div>
</t:formvalid>
</body>
<script>

    var isFirstLoad = true;
    var modifySelect = getUserMagicSuggestWithValue($("#modifySelect"), $("#modifyMan"), "${handle.modifyMan}", false);
    var reviewSelect = getUserMagicSuggestWithValue($("#reviewSelect"), $("#reviewMan"), "${handle.reviewMan}", false);
    var reviewManNameSelect = getUserMagicSuggestWithValue($("#reviewManNameSelect"), $("input[name='hiddenDanger.reviewMan.id']"),"${handle.hiddenDanger.reviewMan.id}",false);
    var addressSelect = getAddressMagicSuggestWithValue($("#magicsuggestAddress"), $("#address"), "${handle.hiddenDanger.address.id}", false);

    var magicsuggestFillCardMan = "";
    var magicsuggestDutyManSelected = "";
    var magicsuggestDutyUnitSelSelected = "";
    var magicsuggestSuperviseManSelected = "";
    var magicsuggestSuperviseUnitSelected = "";
    var magicsuggestAcceptUnitSelected = "";
    var magicSuggestAcceptManSelected = "";
    var magicsuggestRiskSelSelected = "";

    $(document).ready(function () {
        var $b = $('#addKeysId');
        var $i = $('#addKeysinput');
        $i.keyup(function(e){
            if(e.keyCode == 13){
                $b.click();
            }
        });
        $b.click(function(){
            var name = $i.val().toLowerCase();
            if(name != '') setTips(name,-1);
            setTips(name,-1)
            $i.val('');
            $i.select();
        });

        $("#limitDate").Validform({
            tiptype:3,
            datatype : {
                limDate:function(gets,obj,curform,regxp){
                    var dealType = $("input[name='hiddenDanger.dealType']:checked").val();
                    if(dealType == "1" && gets == ""){
                        return false;
                    }else{
                        return true;
                    }
                },
                message: '请输入限期日期'
            }
        });

        $("#beginWellDate").attr("class","Wdate").click(
            function(){
                WdatePicker({
                    dateFmt:'yyyy-MM-dd HH:mm:ss',
                    maxDate:'%y-%M-%d'
                });
            });
        $("#endWellDate").attr("class","Wdate").click(
            function(){
                WdatePicker({
                    dateFmt:'yyyy-MM-dd HH:mm:ss',
                    minDate:'#F{$dp.$D(\'beginWellDate\',{H:+1});}',
                    maxDate:'#F{$dp.$D(\'beginWellDate\',{H:+20});}'
                });
            });
        $("#limitDate").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

        <%--magicsuggestAcceptUnitSelected = getDepartMagicSuggestWithValue($("#magicsuggestAcceptUnitSel"), $("#acceptUnit"), "${handle.hiddenDanger.acceptUnit}", false);--%>

        <%--magicSuggestAcceptManSelected = getUserMagicSuggestWithValue($("#magicsuggestAcceptMan"), $("#acceptMan"), "${handle.hiddenDanger.acceptMan}", false);--%>
        <%--$(magicSuggestAcceptManSelected).on('focus', function(c){--%>
            <%--var deptI = $('#acceptUnit').val();--%>
            <%--magicSuggestAcceptManSelected.setData("magicSelectController.do?getUserList&orgIds="+deptI);--%>
        <%--});--%>

        <%--magicsuggestSuperviseManSelected = getUserMagicSuggestAllowFreeEntries($('#magicsuggestSuperviseMan'),  $("#superviseMan"), "${handle.hiddenDanger.superviseMan}", false);--%>

        // $(magicsuggestSuperviseManSelected).on('focus', function(c){
        //     var deptId = $('#superviseUnitId').val();
        //     magicsuggestSuperviseManSelected.setData("magicSelectController.do?getUserList&orgIds="+deptId);
        // });

        <%--magicsuggestSuperviseUnitSelSelected = $('#magicsuggestSuperviseUnitSel').magicSuggest({--%>
            <%--allowFreeEntries: false,--%>
            <%--data:'magicSelectController.do?departSelectDataGridMagic',--%>
            <%--value:["${handle.hiddenDanger.superviseUnitId}"],--%>
            <%--valueField:'id',--%>
            <%--placeholder:'输入或选择',--%>
            <%--maxSelection:1,--%>
            <%--selectFirst: true,--%>
            <%--highlight: false,--%>
            <%--displayField:'departName'--%>
        <%--});--%>
        <%--$(magicsuggestSuperviseUnitSelSelected).on('selectionchange', function(c){--%>
            <%--$("#superviseUnitId").val(magicsuggestSuperviseUnitSelSelected.getValue());--%>
        <%--});--%>

        magicsuggestFillCardMan = $('#magicsuggestFillCardMan').magicSuggest({
            allowFreeEntries: true,
            data:'magicSelectController.do?getUserList',
            valueField:'id',
            value:'${handle.hiddenDanger.fillCardManId}'.split(","),
            placeholder:'输入或选择',
            maxSelection:10,
            selectFirst: true,
            highlight: false,
            matchField:['spelling','realName','userName','fullSpelling'],
            displayField:'realName'
        });
        $(magicsuggestFillCardMan).on('selectionchange', function(e,m){
            var obj = magicsuggestFillCardMan.getSelection();
            if(obj.length>0){
                $("#fillCardManName").val(obj[0].realName);
                $("#fillCardManId").val(magicsuggestFillCardMan.getValue());
            }else{
                $("#fillCardManName").val("");
                $("#fillCardManId").val("");
            }
        });

        magicsuggestRiskSelSelected = $('#magicsuggestRisk').magicSuggest({
            data: 'magicSelectController.do?getRiskList&riskId=${handle.hiddenDanger.riskId.id}',
            allowFreeEntries: false,
            value:["${handle.hiddenDanger.riskId.id}"],
            valueField: 'id',
            placeholder: '输入或选择',
            maxSelection: 1,
            selectFirst: true,
            highlight: false,
            displayField: 'risk_desc'
        });
        $(magicsuggestRiskSelSelected).on('selectionchange', function(e,m){
            $("#riskId").val(magicsuggestRiskSelSelected.getValue());
            var obj = magicsuggestRiskSelSelected.getSelection();
            if(obj.length>0){
                var riskLevel = obj[0].risk_level;
                if(riskLevel=="1"){
                    $("#riskLevel1").show();
                    $("#riskLevel2").hide();
                }else if(riskLevel=="2"){
                    $("#riskLevel2").show();
                    $("#riskLevel1").hide();
                }else{
                    $("#riskLevel1").hide();
                    $("#riskLevel2").hide();
                }
            }else{
                $("#riskLevel1").hide();
                $("#riskLevel2").hide();
            }
        });

        $(magicsuggestRiskSelSelected).on('focus', function(c){
            var address =$("#address").val();
            var riskType = $("select[name='hiddenDanger.riskType']>option:checked").val();
            var post =$("#post").val();
            if(post==undefined||post==null) {
                post="";
            }
            if(address==null||riskType==null||address==""||riskType==""){
                tip("请先选择地点和隐患类型");
            }else{
                magicsuggestRiskSelSelected.setData("magicSelectController.do?getRiskList&address="+address+"&post="+post+"&riskType="+riskType);
            }
        });

        magicsuggestDutyUnitSelSelected = $('#magicsuggestDutyUnitSel').magicSuggest({
            allowFreeEntries: false,
            data:'magicSelectController.do?departSelectDataGridMagic',
            value:["${handle.hiddenDanger.dutyUnit.id}"],
            valueField:'id',
            placeholder:'输入或选择',
            maxSelection:1,
            selectFirst: true,
            highlight: false,
            matchField:['spelling','departName','fullSpelling'],
            displayField:'departName'
        });

        $(magicsuggestDutyUnitSelSelected).on('selectionchange', function(c){
            if(!isFirstLoad){
                $("#dutyUnitId").val(magicsuggestDutyUnitSelSelected.getValue());
            }
            isFirstLoad = false;
        });

        magicsuggestDutyManSelected = getUserMagicSuggestAllowFreeEntries($('#magicsuggestDutyMan'),  $("#dutyMan"), "${handle.hiddenDanger.dutyMan}", false);

        $(magicsuggestDutyManSelected).on('focus', function(c){
            var deptId = $('#dutyUnitId').val();
            magicsuggestDutyManSelected.setData("magicSelectController.do?getUserList&orgIds="+deptId);
        });

        $("#attach").on("click", function(){
            var addressId=$("#address").val();
            if(addressId==null||addressId==''){
                tip("请先选择地点");
                return;
            }
            var problemDesc=$("#problemDesc").val();
            if(problemDesc==null||problemDesc==''){
                tip("请先填写问题描述");
                return;
            }
            $.ajax({
                url: "tBHiddenDangerExamController.do?attachRisk",
                type: 'post',
                data: {
                    addressId: addressId,
                    problemDesc:problemDesc
                },
                success: function (data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var dangerName = d.obj.dangerName;
                        $("#dangerName").val(dangerName);
                        $("#dangerName").blur();

                        var problemDesc = d.obj.problemDesc;
                        if($("#problemDesc").text()==null||$("#problemDesc").text()==""){
                            $("#problemDesc").text(problemDesc);
                        }
                        var hiddenLevel = d.obj.hiddenLevel;
                        if($("select[name='hiddenDanger.hiddenNature']").val()==null||$("select[name='hiddenDanger.hiddenNature']").val()==""){
                            $("select[name='hiddenDanger.hiddenNature']>option[value='"+hiddenLevel+"']").attr("selected","selected");
                        }

                        var yePossiblyHazard = d.obj.yePossiblyHazard;
                        $("#yePossiblyHazard").text(yePossiblyHazard);

                        var yeRiskGradeTemp = d.obj.yeRiskGradeTemp;
                        var alertColor = d.obj.alertColor;
                        colorValueFormatter(yeRiskGradeTemp,alertColor);

                        var id = d.obj.id;
                        $("input[name='hiddenDanger.dangerId.id']").val(id);
                        $("input[name='hiddenDanger.dangerId.id']").blur();
                    }else {
                        tip(d.msg);
                    }
                }
            });
        });

        if(${handle.handlelStatus eq '00' || handle.handlelStatus eq '1' || handle.handlelStatus eq '2' || handle.handlelStatus eq '12' || handle.handlelStatus eq '21' || handle.handlelStatus eq '3'}){
            if(${handle.handlelStatus eq '3' || handle.handlelStatus eq '12' || handle.handlelStatus eq '21'}){
                $("#reviewData").parent().hide();
            }else{
                $("#modifyData").parent().hide();
                $("#reviewData").parent().hide();
                $("#modifyDate").removeAttr("datatype");
                $("#modifyMan").removeAttr("datatype");
                $("select[name='modifyShift']").removeAttr("datatype");
                $("#rectMeasures").removeAttr("datatype");
            }
            $("#reviewDate").removeAttr("datatype");
            $("#reviewMan").removeAttr("datatype");
            $("select[name='reviewShift']").removeAttr("datatype");
            $("#reviewReport").removeAttr("datatype");
        }

        if(${handle.hiddenDanger.dealType eq '2'}){
            $("#dealTypetr1").hide();
            $(".xcclTR").show();
            $("#modifyData").parent().hide();
            $("#reviewData").parent().hide();
            $("#modifyDate").removeAttr("datatype");
            $("#modifyMan").removeAttr("datatype");
            $("select[name='modifyShift']").removeAttr("datatype");
            $("#rectMeasures").removeAttr("datatype");
            $("#reviewDate").removeAttr("datatype");
            $("#reviewMan").removeAttr("datatype");
            $("select[name='reviewShift']").removeAttr("datatype");
            $("#reviewReport").removeAttr("datatype");
            $("#rectMeasuresXccl").attr("name","rectMeasures");
            $("#rectMeasures").attr("name","rectMeasuresTemp");
            $("#reviewReportXccl").attr("name","reviewReport");
            $("#reviewReport").attr("name","reviewReportTemp");
        }else{
            $("#rectMeasuresXccl").attr("name","rectMeasuresTemp");
            $("#rectMeasures").attr("name","rectMeasures");
            $("#reviewReportXccl").attr("name","reviewReportTemp");
            $("#reviewReport").attr("name","reviewReport");
        }
    });

    function choose_fillCardMan(title) {
        if (typeof(windowapi) == 'undefined') {
            $.dialog({content: 'url:userController.do?userManySelectList', zIndex: 9999, title: ''+title+'', lock: true, width: 860, height: 480, left: '85%', top: '65%', opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_fillCardMan, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        } else {
            $.dialog({content: 'url:userController.do?userManySelectList', zIndex: 9999, title: ''+title+'', lock: true, parent: windowapi, width: 860, height: 480, left: '85%', top: '65%', opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_fillCardMan, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        }
    }
    function clickcallback_fillCardMan() {
        iframe = this.iframe.contentWindow;
        var realName = iframe.getuserSelectListSelections('realName');
        var choosed = $("input[name='hiddenDanger.teamMembers']").val();
        if(choosed != null && choosed.length >0){
            choosed = choosed+","+realName;
        }else{
            choosed = realName;
        };
        $("input[name='hiddenDanger.teamMembers']").val(choosed);
        $("input[name='hiddenDanger.teamMembers']").blur();
        var id = iframe.getuserSelectListSelections('id');
        var choosedId = $("input[name='hiddenDanger.itemUserId']").val();
        if(choosedId != null && $("input[name='hiddenDanger.itemUserId']").val.length >0){
            choosedId = choosedId+","+id;
        }else{
            choosedId = id;
        }
        $("input[name='hiddenDanger.itemUserId']").val(choosedId);
    }

    /**
     *清空组员
     */
    function clearTeamMembers(){
        $("#teamMembers").val("");
        $("#itemUserId").val("");
    }

    function choose_dangerSource(title){
        var addressId=$("#address").val();
        if(addressId==null||addressId==''){
            tip("请先选择地点");
            return;
        }
        var keyWords="";
        $("#myTags>a").each(function(){
            keyWords = keyWords+$(this).attr("title")+",";
        });
        var str = keyWords;
        keyWords = str.substring(0,str.length-1);
        if (typeof(windowapi) == 'undefined') {
            $.dialog({content: 'url:tBDangerSourceController.do?chooseDangerSource&keys='+encodeURI(encodeURI(keyWords))+"&addressId="+addressId, zIndex: 2100, title: ''+title+'', lock: true, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_dangerSource, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        } else {
            $.dialog({content: 'url:tBDangerSourceController.do?chooseDangerSource&keys='+encodeURI(encodeURI(keyWords))+"&addressId="+addressId, zIndex: 2100, title: ''+title+'', lock: true, parent: windowapi, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_dangerSource, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        }
    }

    function clickcallback_dangerSource(){
        iframe = this.iframe.contentWindow;
        var dangerName = iframe.gettBDangerSourceListSelections('hazard.hazardName');
        $("#dangerName").val(dangerName);
        $("#dangerName").blur();
        var problemDesc = iframe.gettBDangerSourceListSelections('yeMhazardDesc');
        if($("#problemDesc").text()==null||$("#problemDesc").text()==""){
            $("#problemDesc").text(problemDesc);
        }
        var hiddenLevel = iframe.gettBDangerSourceListSelections('hiddenLevel');
        if($("select[name='hiddenDanger.hiddenNature']").val()==null||$("select[name='hiddenDanger.hiddenNature']").val()==""){
            $("select[name='hiddenDanger.hiddenNature']>option[value='"+hiddenLevel+"']").attr("selected","selected");
        }
        var id = iframe.gettBDangerSourceListSelections('id');
        $("input[name='hiddenDanger.dangerId.id']").val(id);
        $("input[name='hiddenDanger.dangerId.id']").blur();

        var yePossiblyHazard = iframe.gettBDangerSourceListSelections('yePossiblyHazard');
        $("#yePossiblyHazard").text(yePossiblyHazard);

        var yeRiskGradeTemp = iframe.gettBDangerSourceListSelections('yeRiskGradeTemp');
        var alertColor = iframe.gettBDangerSourceListSelections('alertColor');
        colorValueFormatter(yeRiskGradeTemp,alertColor);
    }

    function colorValueFormatter(value,alertColor) {
        if(value != "") {
            var html = '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 80px; padding-left: 26px;" type="text" value="' + value + '"><span class="minicolors-swatch" style="top:0px;"><span class="minicolors-swatch-color" style="background-color: ' + alertColor + ';"></span></span></div></div>';
            $("#yeRiskGradeTemp").empty();
            $("#yeRiskGradeTemp").append(html);
        }
    }

    /**
     *保存回调函数
     */
    function callbackFun(data) {
        window.top["reload_tBHiddenDangerHandleList"].call();
        window.top["tip_tBHiddenDangerHandleList"].call(data);
        closeWindow();
    }

    /**
     *关闭当前tab
     */
    function closeWindow() {
        window.top.$('.J_menuTab.active>i[class="fa fa-times-circle"]').trigger("click");
    }

    function showTr(trid){
        if ("xcclTR" == trid) {
            $(".xcclTR").show();
            $("#dealTypetr1").hide();
            $("#limitDate").removeAttr("datatype");
            $("#rectMeasuresXccl").attr("name","rectMeasures");
            $("#rectMeasures").attr("name","rectMeasuresTemp");
            $("#reviewReportXccl").attr("name","reviewReport");
            $("#reviewReport").attr("name","reviewReportTemp");
            $("#reviewManId").attr("datatype", "*");
        } else if ("dealTypetr1" == trid) {
            $(".xcclTR").hide();
            $("#dealTypetr1").show();
            $("#limitDate").attr("datatype", "*");
            $("#rectMeasuresXccl").attr("name","rectMeasuresTemp");
            $("#rectMeasures").attr("name","rectMeasures");
            $("#reviewReportXccl").attr("name","reviewReportTemp");
            $("#reviewReport").attr("name","reviewReport");
            if("sjjc"!=${handle.hiddenDanger.examType}) {
                $("#reviewManId").removeAttr("datatype");
            }
        }
    }
</script>
</html>