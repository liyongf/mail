<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>事故</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="plug-in/ckeditor_new/ckeditor.js"></script>
    <script type="text/javascript" src="plug-in/ckfinder/ckfinder.js"></script>
    <script type="text/javascript">
        function uploadFile(data){
            var message = data.msg;
            if($(".uploadify-queue-item").length>0){
                upload();
            }else{
                frameElement.api.opener.showTip(message);
                frameElement.api.opener.reloadTable();
                frameElement.api.close();
            }
        }
        var delAttachmentId = "";
        function delAttachment(attachmentId){
            if(delAttachmentId != ""){
                delAttachmentId = delAttachmentId+","+attachmentId;
            }else{
                delAttachmentId = delAttachmentId+attachmentId;
            }
            $("#delAttachmentId").val(delAttachmentId);
            $("#"+attachmentId).remove();
        }
    </script>
</head>
<body>
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" callback="@Override uploadFile" action="tBAccidentController.do;jsessionid=${pageContext.session.id}?doUpdate" tiptype="3">
<input id="id" name="id" type="hidden" value="${tBAccidentPage.id }">
<input id="delAttachmentId" name="delAttachmentId" type="hidden" value="">
<table style="width: 800px;" cellpadding="0" cellspacing="1" class="formtable">
<tr>
    <td align="right">
        <label class="Validform_label">
            事故单位:
        </label>
    </td>
    <td class="value" colspan="3">
        <label class="Validform_label" id="labeldeptName">${tBAccidentPage.dept.departname}</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            事故编号:
        </label>
    </td>
    <td class="value" colspan="3">
        <label class="Validform_label">${tBAccidentPage.accidentcode}</label>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">事故编号</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            事故名称:
        </label>
    </td>
    <td class="value" colspan="3">
        <label class="Validform_label">${tBAccidentPage.accidentname}</label>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">事故名称</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            发生地点:
        </label>
    </td>
    <td class="value" colspan="3">
        <label class="Validform_label">${tBAccidentPage.happenaddress}</label>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">发生地点</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            发生时间:
        </label>
    </td>
    <td class="value">
        <fmt:formatDate value='${tBAccidentPage.happentime}' type="date" pattern="yyyy-MM-dd"/>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">发生时间</label>
    </td>
    <td align="right">
        <label class="Validform_label">
            事故类型:
        </label>
    </td>
    <td class="value">
        <label class="Validform_label">${tBAccidentPage.accidentTypeTemp}</label>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">事故类型</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            事故等级:
        </label>
    </td>
    <td class="value" colspan="3">
        <select name="accidentlevel.id" id="accidentlevel.id" style="display:none">
            <c:forEach items="${levelEntityList}" var="accidentLevel">
                <option value="${accidentLevel.id}" ${ tBAccidentPage.accidentlevel.id eq accidentLevel.id ?"selected='selected'":""}>${accidentLevel.accidentlevel}</option>
            </c:forEach>
        </select>
        <label class="Validform_label" id="labelaccidentlevel.id">333</label>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">事故等级</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            事故经过:
        </label>
    </td>
    <td class="value" colspan="3">
        <textarea id="accidentdetail" name="accidentdetail" style="width: 460px;">${tBAccidentPage.accidentdetail}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">事故经过</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>死亡人数:
        </label>
    </td>
    <td class="value">
        <label class="Validform_label">${tBAccidentPage.deathnum}</label>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">死亡人数</label>
    </td>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>重伤人数:
        </label>
    </td>
    <td class="value">
        <label class="Validform_label">${tBAccidentPage.heavywoundnum}</label>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">重伤人数</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>轻伤人数:
        </label>
    </td>
    <td class="value">
        <label class="Validform_label">${tBAccidentPage.minorwoundnum}</label>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">轻伤人数</label>
    </td>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>直接损失:
        </label>
    </td>
    <td class="value">
        <label class="Validform_label">${tBAccidentPage.directdamage}(万元)</label>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">直接损失</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            <font color="red">*</font>间接损失:
        </label>
    </td>
    <td class="value">
        <label class="Validform_label">${tBAccidentPage.consequentialloss}(万元)</label>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">间接损失</label>
    </td>
    <td align="right">
        <label class="Validform_label">
            起因物:
        </label>
    </td>
    <td class="value">
        <label class="Validform_label">${tBAccidentPage.cause}</label>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">起因物</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            直接原因:
        </label>
    </td>
    <td class="value" colspan="3">
        <textarea id="immediatecause" name="immediatecause" style="width: 460px;">${tBAccidentPage.immediatecause}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">直接原因</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            间接原因:
        </label>
    </td>
    <td class="value" colspan="3">
        <textarea id="remotecause" name="remotecause" style="width: 460px;">${tBAccidentPage.remotecause}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">间接原因</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            事故教训:
        </label>
    </td>
    <td class="value" colspan="3">
        <textarea id="accidentlesson" name="accidentlesson" style="width: 460px;">${tBAccidentPage.accidentlesson}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">事故教训</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">
            防御措施:
        </label>
    </td>
    <td class="value" colspan="3">
        <textarea id="securityclampdown" name="securityclampdown" style="width: 460px;">${tBAccidentPage.securityclampdown}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">防御措施</label>
    </td>
</tr>
<tr>
    <td align="right">
        <label class="Validform_label">事故报告:
        </label>
    </td>
    <td class="value" style="text-align: left" colspan="3">
        <table>
            <c:forEach items="${list}" var="tsAttachment">
                <tr id="${tsAttachment.id}">
                    <td><label class="Validform_label"><a href="commonController.do?viewFile&fileid=${tsAttachment.id}">${tsAttachment.attachmenttitle}</a></label></td>
                </tr>
            </c:forEach>
        </table>
    </td>
</tr>
</table>
</t:formvalid>
</body>
<script src = "webpage/com/sdzk/buss/web/accident/js/tBAccident.js"></script>
<script type="text/javascript">
    var  myselect=document.getElementById("accidentlevel.id");
    var index=myselect.selectedIndex ;
    myselect.options[index].text;
    document.getElementById("labelaccidentlevel.id").innerHTML=myselect.options[index].text;
</script>