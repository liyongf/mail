<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>地点列表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
  <script type="text/javascript">
      var magicsuggestRiskTypeSelect = "";
      var magicsuggestManageManSelected = "";
      var magicsuggestRiskTypeSelect = "";
      $(function() {
  //编写自定义JS代码
          magicsuggestManageUnitSelected = $('#magicsuggestManageUnit').magicSuggest({
              allowFreeEntries: true,
              data:'magicSelectController.do?departSelectDataGridMagic',
              valueField:'departName',
              value: ['${tBAddressInfoPage.manageUnit}'],
              placeholder:'输入或选择',
              maxSelection:1,
              selectFirst: true,
              highlight: false,
              matchField:['spelling','departName','fullSpelling'],
              displayField:'departName'
          });
          $(magicsuggestManageUnitSelected).on('selectionchange', function(c){
              $("#manageUnit").val(magicsuggestManageUnitSelected.getValue());
              magicsuggestManageManSelected.setData("magicSelectController.do?getUserList&orgIds="+magicsuggestManageUnitSelected.getSelection()[0]["id"]);
          });


          magicsuggestManageManSelected = getUserMagicSuggestAllowFreeEntries($('#magicsuggestManageMan'),  $("#manageMan"),  "${tBAddressInfoPage.manageMan}", false);



          magicsuggestRiskTypeSelect = $('#riskTypeSelect').magicSuggest({
      data: 'magicSelectController.do?getRiskType',
      allowFreeEntries: false,
      valueField: 'typecode',
      value: '${tBAddressInfoPage.riskType}'!=''?'${tBAddressInfoPage.riskType}'.split(","):[],
      placeholder: '输入或选择',
      maxSelection: 20,
      selectFirst: true,
      highlight: false,
      displayField: 'typename'
  });
  $(magicsuggestRiskTypeSelect).on('selectionchange', function (e, m) {
      $("#riskType").val(magicsuggestRiskTypeSelect.getValue());
  });
	  })
  </script>
 </head>
 <body style="padding: 100px">
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBAddressInfoController.do?doBatchUpdate" >
					<input id="ids" name="ids" type="hidden" value="${ids}">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								管控单位:
							</label>
						</td>
						<td class="value">
							<div id="magicsuggestManageUnit" style="width: 130px;height: 15px"></div>
							<input id="manageUnit" name="manageUnit" type="hidden" style="width: 150px" class="inputxt"
								   value='${tBAddressInfoPage.manageUnit}'>
						</td>
					</tr>
            <tr>
                <td align="right">
                    <label class="Validform_label">
                        分管责任人:
                    </label>
                </td>
                <td class="value">
					<div id="magicsuggestManageMan" style="width: 130px;height: 15px"></div>
                    <input id="manageMan" name="manageMan" type="hidden" style="width: 150px" class="inputxt"
                           value='${tBAddressInfoPage.manageMan}'>
                </td>
            </tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								风险类型:
							</label>
						</td>
						<td class="value">
							<div id="riskTypeSelect" style="width: 260px;height: auto"></div>
							<input id="riskType" type="hidden" name="riskType" value="${tBAddressInfoPage.riskType}">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">风险类型</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								排查日期:
							</label>
						</td>
						<td class="value">
							<input id="investigationDate" name="investigationDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"
								   value='<fmt:formatDate value='${tBAddressInfoPage.investigationDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">排查日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								开始日期:
							</label>
						</td>
						<td class="value">
							<input id="startDate" name="startDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"
								   value='<fmt:formatDate value='${tBAddressInfoPage.startDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">开始日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								结束日期:
							</label>
						</td>
						<td class="value">
							<input id="endDate" name="endDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()"
								   value='<fmt:formatDate value='${tBAddressInfoPage.endDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">结束日期</label>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								备注:
							</label>
						</td>
						<td class="value">
							<textarea  id="remark" name="remark" cols="6" style="width: 80%">${tBAddressInfoPage.remark}</textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/address/tBAddressInfo.js"></script>		
