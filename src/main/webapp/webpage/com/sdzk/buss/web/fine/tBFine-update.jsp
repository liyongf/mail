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
        var magicsuggestFineManSelected = "";
        var magicsuggestDutyUnitSelected="";
        var magicsuggestBeFinedManSelected="";

        function showTips(data){
            tip(data.msg);
            window.top.reload_tBFineList.call();
            setTimeout("$('#btn_close').click()",2*1000);
            doCloseTab();
        }
        function formSubmit(reportStatus){
            $("#reportStatus").val(reportStatus);
            $("#btn_sub").trigger("click");
        }

        $(function() {
            /*magicsuggestFineManSelected = getUserMagicSuggestAllowFreeEntries($('#magicsuggestFineMan'),  $("#fineMan"), "${tbFinePage.fineMan}", false);

            $(magicsuggestBeFinedManSelected).on('focus', function(c){
                var deptId = $('#dutyUnit').val();
                magicsuggestBeFinedManSelected.setData("magicSelectController.do?getUserList&orgIds="+deptId);
            });*/

            magicsuggestFineManSelected = $('#magicsuggestFineMan').magicSuggest({
                allowFreeEntries: true,
                data:'magicSelectController.do?getUserList',
                valueField:'realName',
                value:[${fineMans}],
                placeholder:'输入或选择',
                maxSelection:10,
                selectFirst: true,
                matchField:['spelling','realName','userName','fullSpelling'],
                highlight: false,
                displayField:'realName'
            });
            $(magicsuggestFineManSelected).on('selectionchange', function(e,m){
                var obj = magicsuggestFineManSelected.getSelection();
                if(obj.length>0){
                    $("#fineMan").val(magicsuggestFineManSelected.getValue());
                }else{
                    $("#fineMan").val("");
                }
            });

            //选择被罚款部门
            magicsuggestDutyUnitSelected = getDepartMagicSuggestWithValue($('#magicsuggestDutyUnit'), $("#dutyUnit"),"${tbFinePage.dutyUnit.id}", false);
            //选择被罚款人
            magicsuggestBeFinedManSelected = getUserMagicSuggestAllowFreeEntries($('#magicsuggestBeFinedMan'),  $("#beFinedMan"), "${tbFinePage.beFinedMan}", false);

        });

    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBFineController.do?doUpdate" tiptype="3" callback="showTips" btnsub="btn_sub">
    <input id="id" name="id" type="hidden" value="${tbFinePage.id }"/>
    <input id="fineNum" name="fineNum" type="hidden" value="${tbFinePage.fineNum }"/>
    <input id="createBy" name="createBy" type="hidden" value="${tbFinePage.createBy }"/>
    <input id="createDate" name="createDate" type="hidden" value="${tbFinePage.createDate }"/>
    <input id="createName" name="createName" type="hidden" value="${tbFinePage.createName }"/>
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>罚款时间:
                </label>
            </td>
            <td class="value">
                <input id="fineDate" name="fineDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" datatype="*" value="<fmt:formatDate value='${tbFinePage.fineDate}' type="date" pattern="yyyy-MM-dd"/>" />
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">罚款时间</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>罚款人:
                </label>
            </td>
            <td class="value">
                <div id="magicsuggestFineMan" style="width: 200px;height: 15px"></div>
                <input type="hidden" name="fineMan" id="fineMan" value="" style="width: 150px" class="inputxt" datatype="*" value="${tbFinePage.fineMan}" >
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">罚款人</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>罚款性质:
                </label>
            </td>
            <td class="value">
                <t:dictSelect field="fineProperty" type="list" extendJson="{\"datatype\":\"*\"}"
                              typeGroupCode="fineProperty" defaultVal="${tbFinePage.fineProperty}" hasLabel="false"  title="罚款性质"></t:dictSelect>
                <span class="Validform_checktip">请选择罚款性质</span>
                <label class="Validform_label" style="display: none;">罚款性质</label>
            </td>
            <td align="right">
                <label class="Validform_label">
                    <font color="red">*</font>问题描述:
                </label>
            </td>
            <td class="value">
                <textarea id="content" name="content" class="inputxt" rows="3" style="width: 95%;" datatype="*">${tbFinePage.content}</textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">问题描述</label>
            </td>
        </tr>

        <c:if test="${xiezhuang ne 'true'}">
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>责任人部门:
                    </label>
                </td>
                <td class="value">
                    <div id="magicsuggestDutyUnit" style="width: 130px;height: 15px"></div>
                    <input id="dutyUnit" name="dutyUnit.id" type="hidden" style="width: 150px" class="inputxt" datatype="*" value="${tbFinePage.dutyUnit.id}">
                    <span class="Validform_checktip">请选择责任人部门</span>
                    <label class="Validform_label" style="display: none;">责任人部门</label>
                </td>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>责任人姓名:
                    </label>
                </td>
                <td class="value">
                    <div id="magicsuggestBeFinedMan" style="width: 130px;height: 15px"></div>
                    <input type="hidden" name="beFinedMan" id="beFinedMan" value="" style="width: 150px" class="inputxt" datatype="*" value="${tbFinePage.beFinedMan}" >
                    <span class="Validform_checktip">请选择或输入责任人姓名</span>
                    <label class="Validform_label" style="display: none;">责任人姓名</label>
                </td>
            </tr>

            <tr>
                    <%--<td align="right">--%>
                    <%--<label class="Validform_label">--%>
                    <%--被罚款人:--%>
                    <%--</label>--%>
                    <%--</td>--%>
                    <%--<td class="value">--%>
                    <%--<input id="docSource" name="docSource" type="text" style="width: 150px"/>--%>
                    <%--<span class="Validform_checktip"></span>--%>
                    <%--<label class="Validform_label" style="display: none;">被罚款人</label>--%>
                    <%--</td>--%>
                <td align="right">
                    <label class="Validform_label">
                        罚款金额:
                    </label>
                </td>
                <td class="value" colspan="3">
                    <input id="fineMoney" name="fineMoney" type="text" style="width: 150px" datatype="/^([1-9]\d*|[0]{1,1})$/" errormsg="罚款金额必须是正整数" value="${tbFinePage.fineMoney}"/>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">罚款金额</label>
                </td>
            </tr>
        </c:if>
        <c:if test="${xiezhuang eq 'true'}">
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>责任(人)部门:
                    </label>
                </td>
                <td class="value">
                    <div id="magicsuggestDutyUnit" style="width: 130px;height: 15px"></div>
                    <input id="dutyUnit" name="dutyUnit.id" type="hidden" style="width: 150px" class="inputxt" datatype="*" value="${tbFinePage.dutyUnit.id}">
                    <span class="Validform_checktip">请选择责任(人)部门</span>
                    <label class="Validform_label" style="display: none;">责任(人)部门</label>
                </td>
                <td align="right">
                    <label class="Validform_label">
                        责任人姓名:
                    </label>
                </td>
                <td class="value">
                    <div id="magicsuggestBeFinedMan" style="width: 130px;height: 15px"></div>
                    <input type="hidden" name="beFinedMan" id="beFinedMan" value="" style="width: 150px" class="inputxt"  value="${tbFinePage.beFinedMan}" >
                    <span class="Validform_checktip">请选择或输入责任人姓名</span>
                    <label class="Validform_label" style="display: none;">责任人姓名</label>
                </td>
            </tr>

            <tr>
                <td align="right">
                    <label class="Validform_label">
                        责任人罚款金额:
                    </label>
                </td>
                <td class="value">
                    <input id="fineMoney" name="fineMoney" type="text" style="width: 150px" datatype="/^([1-9]\d*|[0]{1,1})$/" errormsg="罚款金额必须是正整数" value="${tbFinePage.fineMoney}"/>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">责任人罚款金额</label>
                </td>
                <td align="right">
                    <label class="Validform_label">
                        单位罚款金额:
                    </label>
                </td>
                <td class="value">
                    <input id="unitFineMoney" name="unitFineMoney" type="text" style="width: 150px" datatype="/^([1-9]\d*|[0]{1,1})$/" errormsg="罚款金额必须是正整数" value="${tbFinePage.unitFineMoney}"/>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">单位罚款金额</label>
                </td>
            </tr>
        </c:if>


    </table>
</t:formvalid>
</body>