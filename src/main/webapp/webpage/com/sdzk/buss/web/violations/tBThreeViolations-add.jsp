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
  $(function(){
      <c:if test="${hid eq '1'}">
          var vioUnitsSelect = getDepartMagicSuggestWithValue($("#vioUnitsSelect"), $("#vioUnits"),"${dutyUnitId}",false);
          var vioPeopleSelect = getUserMagicSuggestAllowFreeEntries($('#vioPeopleSelect'),  $("#vioPeople"), "${dutyMan}", false);
          var vioAddressSelect = getAddressMagicSuggestWithValue($("#vioAddressSelect"), $("#vioAddress"),"${address}",false);
	  </c:if>
      <c:if test="${hid ne '1'}">
	      var vioUnitsSelect = getDepartMagicSuggest($("#vioUnitsSelect"), $("#vioUnits"));
		  var vioPeopleSelect = getUserMagicSuggestAllowFreeEntries($('#vioPeopleSelect'), $("#vioPeople"));
		  var vioAddressSelect = getAddressMagicSuggest($('#vioAddressSelect'), $("#vioAddress"));
      </c:if>

	  var findUnitsSelect = getDepartMagicSuggest($("#findUnitsSelect"), $("#findUnits"));
	  //var stopPeopleSelect = getUserMagicSuggestAllowFreeEntries($('#stopPeopleSelect'), $("#stopPeople"));
      var stopPeopleSelect = "";
      stopPeopleSelect = $('#stopPeopleSelect').magicSuggest({
          allowFreeEntries: true,
          data:'magicSelectController.do?getUserList',
          valueField:'realName',
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

  function showTr(trid){
      if ("1" == trid) {
          $(".xcclTR").show();
          $("select[name='fineProperty']").attr("datatype", "*");
          $("#fineMoney").attr("datatype", "/^([1-9]\\d*|[0]{1,1})$/");
      } else if ("0" == trid) {
          $(".xcclTR").hide();
          $("select[name='fineProperty']").removeAttr("datatype");
          $("#fineMoney").removeAttr("datatype");
      }
  }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBThreeViolationsController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${tBThreeViolationsPage.id }"/>
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>违章时间:
						</label>
					</td>
					<td class="value">
                        <c:if test="${hid eq '1'}">
                            <input id="vioDate" name="vioDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})"
                                   datatype="*" value='<fmt:formatDate value='${examDate}' type="date" pattern="yyyy-MM-dd"/>'/>
                        </c:if>
                        <c:if test="${hid ne '1'}">
                            <input id="vioDate" name="vioDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})"
                                   datatype="*"/>
                        </c:if>

							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">违章时间</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>班次:
						</label>
					</td>
					<td class="value">
                        <c:if test="${hid eq '1'}">
                            <t:dictSelect id="shift" field="shift" defaultVal="${shift}" typeGroupCode="workShift" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>
                        </c:if>
                        <c:if test="${hid ne '1'}">
                            <t:dictSelect id="shift" field="shift" defaultVal="" typeGroupCode="workShift" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>
                        </c:if>
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
                        <c:if test="${hid eq '1'}">
                            <input id="vioAddress" type="hidden" name="vioAddress" datatype="*" value="${address}">
                        </c:if>
                        <c:if test="${hid ne '1'}">
                            <input id="vioAddress" type="hidden" name="vioAddress" datatype="*">
                        </c:if>

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
                        <c:if test="${hid eq '1'}">
                            <input id="vioUnits" name="vioUnits" type="hidden" datatype="*" value="${dutyUnitId}">
                        </c:if>
                        <c:if test="${hid ne '1'}">
                            <input id="vioUnits" name="vioUnits" type="hidden" datatype="*">
                        </c:if>

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

                    <c:if test="${hid eq '1'}">
                        <input id="vioPeople" name="vioPeople" type="hidden" datatype="*" value="${dutyMan}">
                    </c:if>
                    <c:if test="${hid ne '1'}">
                        <input id="vioPeople" name="vioPeople" type="hidden" datatype="*">
                    </c:if>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">违章人员</label>
				</td>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>违章分类:
					</label>
				</td>
				<td class="value">
					<t:dictSelect id="vioCategory" field="vioCategory" defaultVal="" typeGroupCode="violaterule_wzfl" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>
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
						<input id="employeeNum" name="employeeNum" type="text" datatype="*" />
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
					<t:dictSelect id="vioLevel" extendJson="{\"datatype\":\"*\"}" field="vioLevel" defaultVal="0" typeGroupCode="vio_level" hasLabel="false" ></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">三违级别</label>
				</td>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>违章定性:
					</label>
				</td>
				<td class="value">
					<t:dictSelect id="vioQualitative" field="vioQualitative" defaultVal="0" typeGroupCode="violaterule_wzdx" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>
					<%--<input name="vioQualitative" value="" type="hidden">--%>
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
					<input id="findUnits" name="findUnits" type="hidden">
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
					<input id="stopPeople" name="stopPeople" type="hidden" datatype="*">
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
					<textarea id="vioFactDesc" name="vioFactDesc"  rows="3" style="width: 80%;height: auto;" class="inputxt"></textarea>
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
					<textarea id="remark" name="remark"  rows="3" style="width: 80%;height: auto;" class="inputxt"></textarea>
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
					     	 <%--<input id="workType" name="workType" type="text" style="width: 80%" class="inputxt" --%>
					     	  <%----%>
					     	  <%--ignore="ignore"--%>
					     	  <%--/>--%>
							<%--<span class="Validform_checktip"></span>--%>
							<%--<label class="Validform_label" style="display: none;">工种</label>--%>
						<%--</td>--%>
				<%--</tr>--%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否存在罚款:
						</label>
					</td>
					<td class="value" colspan="3">
						<label>
							<input type="radio" name="isFine" id="isFine_1" value="1"  onclick="showTr('1');">是
						</label>
						<label>
							<input type="radio" name="isFine" id="isFine_0" value="0" checked="checked" onclick="showTr('0');">否
						</label>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;"></label>
					</td>
				</tr>
				<tr class="xcclTR" style="display: none">
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>罚款性质:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="fineProperty" type="list"
									  typeGroupCode="fineProperty" hasLabel="false"  title="罚款性质"></t:dictSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">罚款性质</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>罚款金额:
						</label>
					</td>
					<td class="value">
						<input id="fineMoney" name="fineMoney" type="text" style="width: 150px"  errormsg="罚款金额必须是正整数"/>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">罚款金额</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/violations/tBThreeViolations.js"></script>		
