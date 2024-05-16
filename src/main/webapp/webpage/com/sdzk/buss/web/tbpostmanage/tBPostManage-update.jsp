<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>岗位管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
//      $(function(){
//          request.getAttribute("firstPostName");
//
//      });
$(function() {
    $("#postName").Validform({
        datatype: {
            postNameValid: function (gets, obj, curform, regxp) {
                if ("${tBPostManagePage.postName}" != gets) {
                    if (gets == null || gets.length <= 0) {
                        return false;
                    } else {
                        //唯一校验
                        var isVal = false;
                        var errMsg = "";
                        $.ajax({
                            url: 'tBPostManageController.do?postExistsUpdate',
                            type: 'post',
                            async: false,
                            data: {
                                postName: gets

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
                    return true;
                }
            },
            message: '岗位名称已经存在'
        }
    });
});
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBPostManageController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${tBPostManagePage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
                                <b style="color: red">*</b>岗位名称:
							</label>
						</td>
						<td class="value">
						     	 <input id="postName" name="postName" type="text" style="width: 150px" class="inputxt"
										 datatype="postNameValid" errormsg="岗位名称已存在！" >
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">岗位名称</label>
                            <script>
                                $("#postName").val("${tBPostManagePage.postName}");
                            </script>
						</td>
					</tr>
           <%-- <tr>
                <td align="right">
                    <label class="Validform_label">
                        <b style="color: red">*</b>所属专业:
                    </label>
                </td>
                <td class="value">
                        &lt;%&ndash;<input class="inputxt" id="professionType" name="professionType" ignore="ignore"  value="${tbActivityManagePage.professionType}" />&ndash;%&gt;
                    <t:dictSelect id="professionType" field="professionType" type="list" typeGroupCode="proCate_gradeControl" hasLabel="false"
                                  defaultVal="${tBPostManagePage.professionType}" datatype="*"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>--%>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/tbpostmanage/tBPostManage.js"></script>		
