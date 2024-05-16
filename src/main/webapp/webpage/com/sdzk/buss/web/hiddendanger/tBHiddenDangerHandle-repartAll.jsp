<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>隐患复查</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
	 <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
  <script type="text/javascript">
  //编写自定义JS代码
	  $(function(){
		  var reviewSelect = getUserMagicSuggestWithValue($("#reviewSelect"), $("#reviewMan"), "${tBHiddenDangerHandle.reviewMan}", false);
	  })
	  function saveNoteInfo(state) {
		  $('#btn_sub').click();
	  }

	  function noteSubmitCallback(data) {
		  var win = frameElement.api.opener;
		  win.tip("添加成功");
		  frameElement.api.close();
		  win.reloadTable();
	  }
      function showLimitDate(ishidden){
          if("1" == ishidden){
              $("#limitDateTr").hide();
              $("#limitDate").removeAttr("datatype");
          }else{
              $("#limitDateTr").show();
              $("#limitDate").attr("datatype",'*');
          }
      }
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBHiddenDangerHandleController.do?saveAllRepart" callback="@Override noteSubmitCallback" >
			<input id="id" name="id" type="hidden" value="${tBHiddenDangerHandle.id }">
			<input id="ids" name="ids" type="hidden" value="${tBHiddenDangerHandle.ids}">
			<%--<input id="reviewResult" name="reviewResult" type="hidden" value="1">--%>
			<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td  class="Validform_label" colspan="4" align="center">
						<label><input type="radio" value="1" name="reviewResult" checked="checked" onclick="showLimitDate('1');"/>通过</label>
						<label><input type="radio" value="0" name="reviewResult" onclick="showLimitDate('0');" />不通过</label>
					</td>
				</tr>
                <tr id="limitDateTr" style="display: none">
                    <td align="right">
                        <label class="Validform_label">
                            <b style="color: red">*</b>限期日期:
                        </label>
                    </td>
                    <td class="value">
                        <input id="limitDate" name="limitDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({minDate:'%y-%M-%d'})"
                               ignore="checked"
                               value=''>
                        <span class="Validform_checktip">原限期日期为:<fmt:formatDate value='${tBHiddenDangerHandle.hiddenDanger.limitDate}' type="date" pattern="yyyy-MM-dd"/></span>
                        <label class="Validform_label" style="display: none;">限期日期</label>
                    </td>
                </tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>复查时间:
						</label>
					</td>
					<td class="value">
						<input id="reviewDate" name="reviewDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" datatype="*"
							   ignore="checked"
							   value='<fmt:formatDate value='${tBHiddenDangerHandle.reviewDate}' type="date" pattern="yyyy-MM-dd"/>'>
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
							   value='${tBHiddenDangerHandle.reviewMan}'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">复查人</label>
					</td>
				</tr>
				<c:if test="${beixulou eq 'true'}">
					<tr>
						<td align="right">
							<label class="Validform_label">
								其他复查人:
							</label>
						</td>
						<td class="value">
							<textarea name="reviewManOther" id="reviewManOther" class="inputxt" style="width: 280px; height: 30px;">${tBHiddenDangerHandle.reviewManOther}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">其他复查人</label>
						</td>
					</tr>
				</c:if>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>复查班次:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="reviewShift" type="list" extendJson="{\"datatype\":\"*\"}"
									  typeGroupCode="workShift" defaultVal="${tBHiddenDangerHandle.reviewShift}" hasLabel="false"  title="班次"></t:dictSelect>
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
						<textarea name="reviewReport" id="reviewReport" class="inputxt" style="width: 280px; height: 100px;" datatype="*">${tBHiddenDangerHandle.reviewReport}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">复查情况</label>
					</td>
				</tr>
			</table>
			<div class="ui_main">
				<div class="ui_buttons">
					<input type="button" value="提交" onclick="saveNoteInfo();" class="ui_state_highlight">
				</div>
			</div>
		</t:formvalid>
 </body>
