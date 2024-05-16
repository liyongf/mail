<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>危险源管理</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<script type="text/javascript">
    $(function() {
        $("#hazardName").Validform({
            datatype: {
                hazardNameValid: function (gets, obj, curform, regxp) {
                        if (gets == null || gets.length <= 0) {
                            return false;
                        } else {
                            //唯一校验
                            var isVal = false;
                            var errMsg = "";
                            $.ajax({
                                url: 'tbHazardManageController.do?postExists',
                                type: 'post',
                                async: false,
                                data: {
                                    hazardName: gets
                                },
                                success: function (data) {
                                    var d = $.parseJSON(data);
                                    isVal = d.success;
                                    errMsg = d.message;
                                },
                                error: function () {
                                }
                            });
                            return isVal;
                        }
                },
                message: '危险源名称已经存在'
            }
        });
    });
</script>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table"
             action="tbHazardManageController.do?save" tiptype="3" callback="showTips">
    <input id="id" name="id" type="hidden" value="${tbHazardManagePage.id }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>危险源名称:
                </label>
            </td>
            <td class="value">
                <input class="inputxt" id="hazardName" name="hazardName"   datatype="hazardNameValid" errormsg="危险源名称已存在"/>
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>种类:
                </label>
            </td>
            <td class="value">
                <%--<input class="inputxt" id="hazardType" name="hazardType"   value="${tbHazardManagePage.hazardType}" />--%>
                <t:dictSelect id="hazardType" field="hazardType" type="list" typeGroupCode="dangerSource_type" hasLabel="false"
                              defaultVal="${tbHazardManagePage.hazardType}" datatype="*"></t:dictSelect>
                <span class="Validform_checktip"></span>
            </td>
        </tr>
 <%--       <tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    危险类别:
                </label>
            </td>
            <td class="value" colspan="3">
                <c:forEach items="${dangersTypeList}" var="tsType">
                    <label>
                        &lt;%&ndash;<input type="checkbox" name="damageType" value="${tsType.typecode}" ${fn:indexOf(tbHazardManagePage.damageType, tsType.typecode)>=0 ? "checked='checked'":""}>${tsType.typename}&ndash;%&gt;
                        <input type="checkbox" name="damageType" value="${tsType.typecode}" >${tsType.typename}
                    </label>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                </c:forEach>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">危险类别</label>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">
                    事故类别:
                </label>
            </td>
            <td class="value" colspan="3">
                <c:forEach items="${tsTypeList}" var="tsType">
                    <label>
                        <input type="checkbox" name="accidentType" value="${tsType.typecode}" ${fn:indexOf(tbHazardManagePage.accidentType, tsType.typecode)>=0 ? "checked='checked'":""}>${tsType.typename}
                    </label>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                </c:forEach>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">事故类别</label>
            </td>
        </tr>--%>
    </table>
</t:formvalid>
</body>
<script type="text/javascript">
</script>
