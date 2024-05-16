<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>隐患整改</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
	 <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
  <script type="text/javascript">
  //编写自定义JS代码
	  $(function(){
		  var modifySelect = getUserMagicSuggestWithValue($("#modifySelect"), $("#modifyMan"), "${tBHiddenDangerHandle.modifyMan}", false);
		  $("input[name='checkStatus']").on("click",function(){
			  if($(this).val()=='0'){
				  $(".rollBack").show();
				  $(".pass").hide();
				  $("#rollBackRemark").attr("datatype","*");
				  $("#modifyDate").removeAttr("datatype");
				  $("#modifyMan").removeAttr("datatype");
				  $("select[name='modifyShift']").removeAttr("datatype");
				  $("#rectMeasures").removeAttr("datatype");
			  } else {
				  $(".rollBack").hide();
				  $(".pass").show();
				  $("#rollBackRemark").removeAttr("datatype");
				  $("#modifyDate").attr("datatype","*");
				  $("#modifyMan").attr("datatype","*");
                  $("select[name='modifyShift']").attr("datatype","*");
                  $("#rectMeasures").attr("datatype","*");
			  }
		  });

          var  modifyUnitSelect = $('#modifyUnitSelect').magicSuggest({
              allowFreeEntries: true,
              data:'magicSelectController.do?departSelectDataGridMagic',
              valueField:'id',
              placeholder:'输入或选择',
              maxSelection:1,
              selectFirst: true,
              highlight: false,
              matchField:['spelling','departName','fullSpelling'],
              displayField:'departName'
          });

          $(modifyUnitSelect).on('selectionchange', function(c){
              $("#modifyUnit").val(modifyUnitSelect.getValue());
              modifySelect.setData("magicSelectController.do?getUserList&orgIds="+modifyUnitSelect.getValue());
          });
	  })

	  function saveNoteInfo(state) {
		  if($("input[name='checkStatus']:checked").val()=='0'){
			  $("#modifyDate").val("");
			  $("#modifyMan").val("");
//			  $("#modifyShift>option[value='']").attr("selected","selected");
              $("select[name='modifyShift']").val("");
              $("#rectMeasures").empty();
		  } else {
			  $("#rollBackRemark").empty();
		  }
		  $('#btn_sub').click();
	  }

	  function noteSubmitCallback(data) {
		  var win = frameElement.api.opener;
		  win.tip(data.msg);
		  frameElement.api.close();
		  win.reloadTable();
	  }

	  var isLoadReady = 0;		//查看页面是否被点击过，是否已经加载
	  var tabIndex = 0;
	  function showDetailTable(){
		  var tab = $('#tt').tabs('getSelected');
		  var index = $('#tt').tabs('getTabIndex',tab);
		  if(tabIndex==index){
			  return;
		  }
		  reloadDetail();
	  }

	  function reloadDetail(){
		  var tab = $('#tt').tabs('getSelected');
		  var index = $('#tt').tabs('getTabIndex',tab);
		  if(index==1){
		      if(isLoadReady == 0){
                  isLoadReady = 1;
                  <%--$("#tbHiddenDangerDetailTab").attr("src", "<%=request.getContextPath()%>/tBHiddenDangerHandleController.do?goUpdate&load=detail&id=${tBHiddenDangerHandle.id}");--%>
				  $("#tbHiddenDangerDetailTab").attr("src", "<%=request.getContextPath()%>/riskManageResultController.do?updateHd&load=detail&id=${tBHiddenDangerHandle.hiddenDanger.id}");
			  }
              return;
		  } else{
			  return;
		  }
	  }

  </script>
 </head>
 <body>

		<div id="tt" class="easyui-tabs" data-options="onSelect:function(t){$('#tt .panel-body').css('width','auto').css('height', '550px'); showDetailTable(); }">
			<div title="整改">
				<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBHiddenDangerHandleController.do?saveModifyIssues" callback="@Override noteSubmitCallback" >
					<input id="id" name="id" type="hidden" value="${tBHiddenDangerHandle.id }">
						<%--<input id="checkStatus" name="checkStatus" type="hidden">--%>
					<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
						<tr>
							<td  class="Validform_label" colspan="4" align="center">
								<label><input type="radio" value="1" name="checkStatus" checked="checked"/>通过</label>
								<label><input type="radio" value="0" name="checkStatus" />不通过</label>
							</td>
						</tr>
						<tr class="pass">
							<td align="right">
								<label class="Validform_label">
									<b style="color: red">*</b>整改时间:
								</label>
							</td>
							<td class="value">
								<input id="modifyDate" name="modifyDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" datatype="*"
									   ignore="checked"
									   value='<fmt:formatDate value='${tBHiddenDangerHandle.modifyDate}' type="date" pattern="yyyy-MM-dd"/>'>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">整改时间</label>
							</td>
						</tr>
						<tr class="pass">
							<td align="right">
								<label class="Validform_label">
									整改单位:
								</label>
							</td>
							<td class="value">
								<div id="modifyUnitSelect" style="width: 130px;height: 15px;" class="inputxt"></div>
								<input id="modifyUnit" name="modifyUnit" type="hidden">
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">整改单位</label>
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
									   value='${tBHiddenDangerHandle.modifyMan}'>
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
											  typeGroupCode="workShift" defaultVal="${tBHiddenDangerHandle.modifyShift}" hasLabel="false"  title="班次"></t:dictSelect>
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
								<textarea name="rectMeasures" id="rectMeasures" class="inputxt" style="width: 280px; height: 100px;" datatype="*">${tBHiddenDangerHandle.rectMeasures}</textarea>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">整改措施</label>
							</td>
						</tr>
						<tr class="rollBack" style="display: none">
							<td align="right">
								<label class="Validform_label">
									<b style="color: red">*</b>驳回备注:
								</label>
							</td>
							<td class="value">
								<textarea name="rollBackRemark" id="rollBackRemark" class="inputxt" style="width: 280px; height: 100px;">${tBHiddenDangerHandle.rollBackRemark}</textarea>
								<span class="Validform_checktip"></span>
								<label class="Validform_label" style="display: none;">驳回备注</label>
							</td>
						</tr>
					</table>

					<div class="ui_main">
						<div class="ui_buttons">
							<input type="button" value="提交" onclick="saveNoteInfo();" class="ui_state_highlight">
						</div>
					</div>
				</t:formvalid>
			</div>
			<%----------------------------------------------------%>
			<div title="查看" style="width: 100%; height: 100%">
				<iframe  id="tbHiddenDangerDetailTab" src='' style="height: 90%;width: 100%;" frameborder="0"></iframe>
			</div>

		</div>
 </body>
