<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>作业活动管理</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<script type="text/javascript">
    $(function() {
        $("#activityName").Validform({
            datatype: {
                activityNameValid: function (gets, obj, curform, regxp) {
                    if (("${tbActivityManagePage.activityName}" != gets)){
                        if (gets == null || gets.length <= 0) {
                            return false;
                        } else {
                            //唯一校验
                            var isVal = false;
                            var errMsg = "";
                            $.ajax({
                                url: 'tbActivityManageController.do?postExists',
                                type: 'post',
                                async: false,
                                data: {
                                    activityName: gets
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
                    } else {
                        if (gets == null || gets.length <= 0) {
                            return false;
                        }else {
                            return true;
                        }
                    }
                },
                message: '作业活动名称已经存在'
            }
        });
    });
  </script>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tbActivityManageController.do?save">
    <input id="id" name="id" type="hidden" value="${tbActivityManagePage.id }">
    <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b>作业活动名称:
                </label>
            </td>
            <td class="value">
                <input class="inputxt" id="activityName" name="activityName"   datatype="activityNameValid" errormsg="作业活动名称已存在！"/>
                <script>
                    $("#activityName").val("${tbActivityManagePage.activityName}");
                </script>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">作业活动名称</label>
            </td>
        </tr>
       <%-- <tr>
            <td align="right">
                <label class="Validform_label">
                    <b style="color: red">*</b> 专业:
                </label>
            </td>
            <td class="value">
                    &lt;%&ndash;<input class="inputxt" id="professionType" name="professionType" ignore="ignore"  value="${tbActivityManagePage.professionType}" />&ndash;%&gt;
                <t:dictSelect id="professionType" field="professionType" type="list" typeGroupCode="proCate_gradeControl" hasLabel="false"
                              defaultVal="${tbActivityManagePage.professionType}" datatype="*"></t:dictSelect>
                <span class="Validform_checktip"></span>
            </td>
        </tr>--%>
    </table>
</t:formvalid>
</body>