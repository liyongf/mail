<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
    <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" action="tBDangerSourceController.do?doOneKeyCopy" tiptype="3" callback="showTips" btnsub="btn_sub">
        <table style="width: 100%;margin-top:25px;" cellpadding="2" cellspacing="2">
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        <font color="red">*</font>复制自：
                    </label>
                </td>
                <td class="value">
                    <input id="cloneFrom" name="cloneFrom" type="text" class="Wdate" datatype="*" value='<fmt:formatDate value='${previousYear}' type="date" pattern="yyyy"/>'  style="width: 160px;" />
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
                    <input id="cloneTo" name="cloneTo" type="text" class="Wdate" datatype="*" value='<fmt:formatDate value='${thisYear}' type="date" pattern="yyyy"/>'  disabled="disabled" style="width: 160px;" />
                    <span class="Validform_checktip"></span>
                    <label class="Validform_label" style="display: none;">目标日期</label>
                </td>
            </tr>

            <tr style="height: 10px;"></tr>

            <tr>
                <td colspan="2">
                    <span style="color: #ff9b00;">提示：</span>一键复制功能用来将上一年已审核的风险复制到今年。同时，还会一并将风险点关联的风险复制过来。
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <span style="color: red;">警告：</span>一键复制功能请慎用！一旦复制之后，风险点的关联关系将会被刷新为本年度的关联关系！
                </td>
            </tr>
        </table>
    </t:formvalid>
    </div>
</div>
<script>
    $(document).ready(function(){
        $("#cloneFrom").attr("class","Wdate").attr("style","width: 160px;").click(function(){WdatePicker({readOnly:true,dateFmt:'yyyy',maxDate:'{%y-1}-%M-%d'});});
//        $("#cloneTo").attr("class","Wdate").attr("style","width: 160px;").click(function(){WdatePicker({readOnly:true,dateFmt:'yyyy',maxDate:'%y-%M-%d'});});
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
                        url: "tBDangerSourceController.do?doOneKeyCopy&from=" + $("#cloneFrom").val() + "&to=" + $("#cloneTo").val(),
                        type: 'POST',
                        error: function () {
                        },
                        success: function (data) {
                            data = $.parseJSON(data);
//                            W.showMsg(data.msg);
//                            W.reloadP();
                            api.close();
                        }
                    });
                    api.close();
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