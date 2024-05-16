<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>三违信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  var magicDisable = ${load eq 'detail'?'true':'false'};
  $(function(){
	  $("#vioLevel").on("change",function(){
		  var vioLevel = $(this).val();
		  if(vioLevel == "A"){
			  $("#vioQualitativeTemp").val("3");//严重三违
			  $("input[name='vioQualitative']").val("3");
		  }else if(vioLevel == "B"){
			  $("#vioQualitativeTemp").val("2");//比较严重三违
			  $("input[name='vioQualitative']").val("2");
		  }else if(vioLevel == "C"){
			  $("#vioQualitativeTemp").val("1");//典型三违
			  $("input[name='vioQualitative']").val("1");
		  }else if(vioLevel == "D"){
			  $("#vioQualitativeTemp").val("0");//一般三违
			  $("input[name='vioQualitative']").val("0");
		  }else if(vioLevel == "E"){
			  $("#vioQualitativeTemp").val("0");//一般三违
			  $("input[name='vioQualitative']").val("0");
		  }else{}
	  });

	  /**
	   * 初始化下拉框
	   */
	  var vioUnitsSelect = getDepartMagicSuggestWithValue($("#vioUnitsSelect"), $("#vioUnits"),"${tBThreeViolationsPage.vioUnits}",magicDisable);
	  var findUnitsSelect = getDepartMagicSuggestWithValue($("#findUnitsSelect"), $("#findUnits"),"${tBThreeViolationsPage.findUnits}",magicDisable);
	//  var stopPeopleSelect = getUserMagicSuggestWithValue($('#stopPeopleSelect'), $("#stopPeople"),"${tBThreeViolationsPage.stopPeople}",magicDisable);
	 // var vioPeopleSelect = getUserMagicSuggestWithValue($('#vioPeopleSelect'), $("#vioPeople"),"${tBThreeViolationsPage.vioPeople}",magicDisable);
	  var vioAddressSelect = getAddressMagicSuggestWithValue($('#vioAddressSelect'), $("#vioAddress"),"${tBThreeViolationsPage.vioAddress}",magicDisable);

      //var stopPeopleSelect = getUserMagicSuggestAllowFreeEntries($('#stopPeopleSelect'),  $("#stopPeople"), "${tBThreeViolationsPage.stopPeople}", false);
      var vioPeopleSelect = getUserMagicSuggestAllowFreeEntries($('#vioPeopleSelect'),  $("#vioPeople"), "${tBThreeViolationsPage.vioPeople}", false);
      var stopPeopleSelect = "";
      stopPeopleSelect = $('#stopPeopleSelect').magicSuggest({
          allowFreeEntries: true,
          data:'magicSelectController.do?getUserList',
          valueField:'realName',
          value:[${stopPeoples}],
          placeholder:'输入或选择',
          maxSelection:10,
          selectFirst: true,
          matchField:['spelling','realName','userName','fullSpelling'],
          highlight: false,
          displayField:'realName'
      });
      $(stopPeopleSelect).on('selectionchange', function(e,m){
          var obj = stopPeopleSelect.getSelection();
          if(obj.length>0){
              $("#stopPeople").val(stopPeopleSelect.getValue());
          }else{
              $("#stopPeople").val("");
          }
      });
  })
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBThreeViolationsController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${tBThreeViolationsPage.id }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>违章时间:
							</label>
						</td>
						<td class="value">
									  <input id="vioDate" name="vioDate" type="text" style="width:  150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})"
											 datatype="*"
									    value='<fmt:formatDate value='${tBThreeViolationsPage.vioDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">违章时间</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>班次:
							</label>
						</td>
						<td class="value">
							<t:dictSelect id="shift" field="shift" defaultVal="${tBThreeViolationsPage.shift}" typeGroupCode="workShift" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">班次</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>违章地点:
							</label>
						</td>
						<td class="value">
							<div id="vioAddressSelect" style="width: 130px;height: 15px"></div>
							<input id="vioAddress" type="hidden" name="vioAddress" datatype="*" value='${tBThreeViolationsPage.vioAddress}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">违章地点</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>违章单位:
							</label>
						</td>
						<td class="value">
							<div id="vioUnitsSelect" style="width: 130px;height: 15px"></div>
							<input id="vioUnits" name="vioUnits" type="hidden" datatype="*" value='${tBThreeViolationsPage.vioUnits}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">违章单位</label>
						</td>
					</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>违章人员:
					</label>
				</td>
				<td class="value">
					<div id="vioPeopleSelect" style="width: 130px;height: 15px"></div>
					<input id="vioPeople" name="vioPeople" type="hidden" datatype="*" value='${tBThreeViolationsPage.vioPeople}'>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">违章人员</label>
				</td>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>违章分类:
							</label>
						</td>
						<td class="value">
							<t:dictSelect id="vioCategory" field="vioCategory" defaultVal="${tBThreeViolationsPage.vioCategory}" typeGroupCode="violaterule_wzfl" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">违章分类</label>
						</td>
					</tr>
			<c:if test="${xinchazhuang eq 'true'}">
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>职工编号:
						</label>
					</td>
					<td class="value" colspan="3">
						<input id="employeeNum" name="employeeNum" type="text" datatype="*" value='${tBThreeViolationsPage.employeeNum}'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">职工编号</label>
					</td>
				</tr>
			</c:if>
					<tr>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>三违级别:
							</label>
						</td>
						<td class="value">
							<t:dictSelect id="vioLevel" extendJson="{\"datatype\":\"*\"}" field="vioLevel" defaultVal="${tBThreeViolationsPage.vioLevel}" typeGroupCode="vio_level" hasLabel="false" ></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">三违级别</label>
						</td>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>违章定性:
					</label>
				</td>
				<td class="value">
					<t:dictSelect id="vioQualitative" field="vioQualitative" defaultVal="${tBThreeViolationsPage.vioQualitative}" typeGroupCode="violaterule_wzdx" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>
					<%--<input name="vioQualitative" type="hidden" value='${tBThreeViolationsPage.vioQualitative}'>--%>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">违章定性</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						查处单位:
					</label>
				</td>
				<td class="value">
					<div id="findUnitsSelect" style="width: 130px;height: 15px"></div>
					<input id="findUnits" name="findUnits" type="hidden" value='${tBThreeViolationsPage.findUnits}'>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">查处单位</label>
				</td>
						<td align="right">
							<label class="Validform_label">
								<b style="color: red">*</b>制止人:
							</label>
						</td>
						<td class="value">
							<div id="stopPeopleSelect" style="width: 200px;height: 15px"></div>
							<input id="stopPeople" name="stopPeople" type="hidden" datatype="*" value='${tBThreeViolationsPage.stopPeople}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">制止人</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								三违事实描述:
							</label>
						</td>
						<td class="value">
							<textarea id="vioFactDesc" name="vioFactDesc"  rows="6" style="width: 80%;height: auto;" class="inputxt">${tBThreeViolationsPage.vioFactDesc}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">三违事实描述</label>
						</td>
						<td align="right">
							<label class="Validform_label">
								<c:if test="${henghe eq 'true'}">
									处理意见:
								</c:if>
								<c:if test="${henghe ne 'true'}">
									备注:
								</c:if>
							</label>
						</td>
						<td class="value">
							<textarea id="remark" name="remark"  rows="6" style="width: 80%;height: auto;" class="inputxt">${tBThreeViolationsPage.remark}</textarea>
							<span class="Validform_checktip"></span>
							<c:if test="${henghe eq 'true'}">
								<label class="Validform_label" style="display: none;">处理意见:</label>
							</c:if>
							<c:if test="${henghe ne 'true'}">
								<label class="Validform_label" style="display: none;">备注:</label>
							</c:if>
						</td>
					</tr>
			<%--<tr>--%>
				<%--<td align="right">--%>
					<%--<label class="Validform_label">--%>
						<%--工种:--%>
					<%--</label>--%>
				<%--</td>--%>
				<%--<td class="value">--%>
					<%--<input id="workType" name="workType" type="text" style="width:  80%" class="inputxt"--%>
						   <%--ignore="ignore"--%>
						   <%--value='${tBThreeViolationsPage.workType}'>--%>
					<%--<span class="Validform_checktip"></span>--%>
					<%--<label class="Validform_label" style="display: none;">工种</label>--%>
				<%--</td>--%>
			<%--</tr>--%>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/violations/tBThreeViolations.js"></script>		
