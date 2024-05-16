<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
    <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" action="tBDangerSourceController.do?doAddDepartDangerSource" tiptype="3" callback="showTips" btnsub="btn_sub">
        <table style="width: 100%;margin-top:25px;" cellpadding="2" cellspacing="2">
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>复制自：
                    </label>
                </td>
                <td class="value">
                    <input id="cloneFrom" name="cloneFrom" type="text" class="Wdate" datatype="*"/>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">被复制年份</label>
                </td>
            </tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>复制至日期：
                    </label>
                </td>
                <td class="value">
                    <input id="cloneTo" name="cloneTo" type="text" class="Wdate" datatype="*"/>
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">目标日期</label>
                </td>
            </tr>
        </table>
    </t:formvalid>
    </div>
</div>
<script>
    $(document).ready(function(){
        $("#cloneFrom").attr("class","Wdate").attr("style","width: 160px;").click(function(){WdatePicker({readOnly:true,dateFmt:'yyyy',maxDate:'{%y-1}-%M-%d'});});
        $("#cloneTo").attr("class","Wdate").attr("style","width: 160px;").click(function(){WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'});});
    });

    var api = frameElement.api, W = api.opener, D = W.document;
    api.button(
        {
            name: '确认',
            callback: function () {
                if($("#cloneFrom").val() == ""){
                    $("#cloneFrom").next().empty();
                    $("#cloneFrom").next().attr("class","Validform_checktip Validform_wrong").append("请填写被复制年份！");
                }
                if($("#cloneTo").val() == ""){
                    $("#cloneTo").next().empty();
                    $("#cloneTo").next().attr("class","Validform_checktip Validform_wrong").append("请填写目标日期！");
                }
                if($("#cloneFrom").val() != "" && $("#cloneTo").val() != ""){
                    $.ajax({
                        url: "tBDangerSourceController.do?doCloneYearDanger&from=" + $("#cloneFrom").val() + "&to=" + $("#cloneTo").val(),
                        type: 'POST',
                        error: function () {
                        },
                        success: function (data) {
                            data = $.parseJSON(data);
                            W.showMsg(data.msg);
                            W.reloadP();
                            api.close();
                        }
                    });
                }
                return false;
            },
            focus: true
        },
        {
            name: '取消'
        }
    );
</script>