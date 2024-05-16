<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>管控任务</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
  <script type="text/javascript">
		function showWait() {
            $.messager.progress({
                text : "正在生成风险清单......"
            });

        }
        function hideWait(data) {
            $.messager.progress('close');
            window.top.reload_riskManageTaskAllList.call(data);
            frameElement.api.opener.reloadTable();
            frameElement.api.close();
        }
        var  magicsuggestSelected = "";
        var magicSuggestPostSelected = "";
        $(function () {
            $("select[name='manageType']>option[value='comprehensive']").remove();
            $("select[name='manageType']>option[value='profession']").remove();



            $("[name='manageType']").change (function () {
                if($("[name='manageType']").val()=='comprehensive' || $("[name='manageType']").val()=='profession' || $("[name='manageType']").val()=='team'|| $("[name='manageType']").val()=='group'){
                    $("#addressManage").show();
                    $("#postManage").hide();
                    $("#reviewManId").attr("datatype", "*");
                    $("#postId").removeAttr("datatype");
                } else if($("[name='manageType']").val()=='post' ){
                    $("#postManage").show();
                    $("#addressManage").hide();
                    $("#postId").attr("datatype", "*");
                    $("#addressId").removeAttr("datatype");
                }else{
                    $("#addressManage").hide();
                    $("#postManage").hide();
                    $("#addressId").removeAttr("datatype");
                    $("#postId").removeAttr("datatype");
                }
            });

            magicsuggestSelected = $('#magicsuggest').magicSuggest({
                data: 'magicSelectController.do?getAddressList',
                allowFreeEntries: false,
                valueField: 'id',
                value: '${addressId}'!=''?'${addressId}'.split(","):[],
                placeholder: '输入或选择',
                maxSelection: 20,
                selectFirst: true,
                highlight: false,
                displayField: 'address'
            });
            $(magicsuggestSelected).on('selectionchange', function(e,m){
                $("#addressId").val(magicsuggestSelected.getValue());
            });

            magicSuggestPostSelected = getPostMagicSuggestWithValue($("#magicsuggestPost"), $("input[name='postId']"),"",false);
        })
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskManageTaskController.do?doAddNew" beforeSubmit="showWait" callback="@Override hideWait">
		<table style="width: 750px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>管控时间:
					</label>
				</td>
				<td class="value" colspan="3">
					<input id="manageTime" name="manageTime" type="text" style="width: 150px" datatype="*"  class="Wdate" onClick="WdatePicker()"
						   value='<fmt:formatDate value='<%=new Date()%>' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">管控时间</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<font color="red">*</font>管控类型:
					</label>
				</td>
				<td class="value">
					<t:dictSelect field="manageType" type="manageType" extendJson="{\"datatype\":\"*\"}"
								  typeGroupCode="taskManageType" hasLabel="false"  title="管控类型"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">管控类型</label>
				</td>
				<td align="right">
					<label class="Validform_label">
						<font color="red">*</font>管控班次:
					</label>
				</td>
				<td class="value">
					<t:dictSelect field="manageShift" type="manageShift" extendJson="{\"datatype\":\"*\"}"
								  typeGroupCode="workShift" hasLabel="false"  title="管控班次"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">管控班次</label>
				</td>
			</tr>
			<tr id="addressManage" style="display: none">
				<td align="right">
					<label class="Validform_label">常用地点:
					</label>
				</td>
				<td class="value" colspan="3" >

					<%--<c:forEach items="${addressList}" var="address" varStatus="status">
						<span style="width:150px;display: inline-block;">
							<label >
								<input name="addressId" value="${address.id}" type="checkbox">${address.address}
							</label>
						</span>
					</c:forEach>--%>

					<div id="magicsuggest" style="width:300px;height: auto"></div>
					<input type="hidden" name="addressId" id="addressId" value="" class="inputxt" datatype="*">
					<span class="Validform_checktip">请选择地点</span>
					<label class="Validform_label" style="display: none;">地点</label>


				</td>
			</tr>

			<tr id="postManage" style="display: none">
				<td align="right">
					<label class="Validform_label">岗位:
					</label>
				</td>
				<td class="value" colspan="3" >

						<%--<c:forEach items="${addressList}" var="address" varStatus="status">
                            <span style="width:150px;display: inline-block;">
                                <label >
                                    <input name="addressId" value="${address.id}" type="checkbox">${address.address}
                                </label>
                            </span>
                        </c:forEach>--%>

					<div id="magicsuggestPost" style="width:130px;height: auto"></div>
					<input type="hidden" name="postId" id="postId" value="" class="inputxt" datatype="*">
					<span class="Validform_checktip">请选择岗位</span>
					<label class="Validform_label" style="display: none;">岗位</label>


				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						备注:
					</label>
				</td>
				<td class="value" colspan="3">
					<textarea id="remark" name="remark" type="text" style="width: 440px;" ></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">备注</label>
				</td>
			</tr>
			</table>
		</t:formvalid>
 </body>
