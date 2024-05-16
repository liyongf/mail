<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>风险辨识任务</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js?v=20190716"></script>
	 <link href="plug-in/jQueryLabel/css/tab.css" type="text/css" rel="stylesheet" />
	 <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
	 <script type="text/javascript" src="plug-in/jQueryLabel/js/tab.js"></script>
	 <link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
	 <style>
		 .add{
			 background-color: #128eed;!important;
		 }
		 .del{
			 background-color: #ec2f2f;!important;
		 }
	 </style>
 </head>
 <body >
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskTaskController.do?save">
			<input id="id" name="id" type="hidden" value="${riskTaskPage.id }">
	  		<input id="checkData" name="checkData" type="hidden" value="" />
			<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable" id="tab">
				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>辨识活动类型:
						</label>
					</td>
					<td class="value" colspan="3">
						<t:dictSelect field="taskType" type="list" extendJson="{\"datatype\":\"*\"}" readonly="readonly"
									  typeGroupCode="risk_task_type" defaultVal="${riskTaskPage.taskType}" hasLabel="false"  title="辨识活动类型"></t:dictSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">辨识活动类型</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>辨识活动名称:
						</label>
					</td>
					<td class="value" colspan="3">
							${riskTaskPage.taskName}
					</td>
				</tr>
				<tr id="speIdeTypeTr" <c:if test="${'3' ne riskTaskPage.taskType}">style="display: none;"</c:if>>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>专项辨识类型:
						</label>
					</td>
					<td class="value" colspan="3">
						<c:if test="${'3' ne riskTaskPage.taskType}">
							<t:dictSelect field="speIdeType" type="list"  id="speIdeType" readonly="readonly"
										  typeGroupCode="specificType"  hasLabel="false" defaultVal="${riskTaskPage.speIdeType}" title="专项辨识类型"></t:dictSelect>
						</c:if>
						<c:if test="${'3' eq riskTaskPage.taskType}">
							<t:dictSelect field="speIdeType" type="list"  id="speIdeType" extendJson="{\"datatype\":\"*\"}" readonly="readonly"
										  typeGroupCode="specificType"  hasLabel="false" defaultVal="${riskTaskPage.speIdeType}" title="专项辨识类型"></t:dictSelect>
						</c:if>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">专项辨识类型</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>开始日期:
						</label>
					</td>
					<td class="value" colspan="3">
						<fmt:formatDate value='${startDate}' type="date" pattern="yyyy-MM-dd"/>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">开始日期</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>结束日期:
						</label>
					</td>
					<td class="value" colspan="3">
						<fmt:formatDate value='${riskTaskPage.endDate}' type="date" pattern="yyyy-MM-dd"/>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">结束日期</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>组织人员:
						</label>
					</td>
					<td class="value" colspan="3">
						<div id="organizerManSelect" style="width: 130px;height: 15px"></div>
						<input id="organizerMan" name="organizerMan" type="hidden" style="width: 150px" class="inputxt"   datatype="*"  value="${organizerMan}">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">组织人员</label>
					</td>
				</tr>
				<c:if test="${detail ne 'true'}">
					<tr>
						<td align="right">
							<label class="Validform_label">
								参与人员:
							</label>
						</td>
						<td class="value" colspan="3">
							<button type="button" class="add ace_button" onclick="addTr()">添加一条</button>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">
								选择参与人员单位:
							</label>
						</td>
						<td class="value">
							<div id="magicsuggestUnitSel0" style="width: 130px;height: 15px"></div>
							<input id="unitId0" name="unitId0" type="hidden" style="width: 150px" class="inputxt">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;"></label>
						</td>
						<td align="right">
							<label class="Validform_label">
								<font color="red">*</font>选择参与人员:
							</label>
						</td>
						<td class="value">
							<div id="participantManSelect0" style="width: 200px;height: auto"></div>
							<input id="participantMan0" name="participantMan0" type="hidden" style="width: 150px" class="inputxt"   datatype="*"  value="${riskTaskPage.participantMan}">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">选择参与人员</label>
						</td>
					</tr>
				</c:if>

				<c:if test="${detail eq 'true'}">
					<tr>
						<td align="right">
							<label class="Validform_label">
								<font color="red">*</font>参与人员:
							</label>
						</td>
						<td class="value" colspan="3">
							<div id="participantManSelect" style="width: 300px;height: auto"></div>
							<input id="participantMan" name="participantMan" type="hidden" style="width: 150px" class="inputxt"   datatype="*"  value="${riskTaskPage.participantMan}">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">选择参与人员</label>
						</td>
					</tr>
				</c:if>
			</table>
		</t:formvalid>
 </body>

<script type="text/javascript">
    var count = 0;
    var magicsuggestOrganizerManSelect = "";
    var magicsuggestParticipantManSelect = "";
    var magicsuggestUnitSelSelect = "";
    var magicsuggestUnitSelSelect0 = "";
    var magicsuggestParticipantManSelect0 = "";

    /**将数据组装成为 Json 格式，传送到服务器后台。全局变量 count 记录了有多少行 tr */
    function getCheckData(){

        var data = "";
        //开始组装data数据
        var dataJson = [];
        for (var j=0; j<count+1; j++){
            var jo = {};
            if($("input[id^='participantMan"+j+"']").val()!=null){
                jo.participantMan =$("input[id^='participantMan"+j+"']").val();
                dataJson.push(jo);
            }
        }
        data = JSON.stringify(dataJson);
        //将组装好的数据放到input里面，等待提交到后台
        $("#checkData").val(data);
    }

    /**删除一行*/
    function delTr($this){
        $($this).parent().parent().remove();
    }

    function addTr(){
        count++;
        var trHtml = oneTr(count);
        var $tr = $("#tab tr:last");
        if($tr.size()==0){
            alert("指定的table id或行数不存在！");
            return;
        }
        $tr.after(trHtml);
        var magicsuggestUnitSelTemp = "magicsuggestUnitSel"+count;
        var participantManSelectTemp = "participantManSelect"+count;
		window[participantManSelectTemp] = getUserMagicSuggestWithValueMax5($('#participantManSelect'+count+''),  $('#participantMan'+count+''), "", false);

        /*window[participantManSelectTemp] = $('#participantManSelect'+count+'').magicSuggest({
            allowFreeEntries: false,
            data:'magicSelectController.do?getUserList',
            valueField:'id',
            placeholder:'输入或选择',
            maxSelection:5,
            selectFirst: true,
            matchField:['spelling','realName','userName','fullSpelling'],
            highlight: false,
            displayField:'realName'
        });

        $(window[participantManSelectTemp]).on('selectionchange', function(e,m){
            $("#participantMan"+count+"").val(window[participantManSelectTemp].getValue());
        });*/
        window[magicsuggestUnitSelTemp] = $('#magicsuggestUnitSel'+count+'').magicSuggest({
            allowFreeEntries: false,
            data:'magicSelectController.do?departSelectDataGridMagic',
            valueField:'id',
            placeholder:'输入或选择',
            maxSelection:1,
            selectFirst: true,
            highlight: false,
            matchField:['spelling','departName','fullSpelling'],
            displayField:'departName'
        });
        $(window[magicsuggestUnitSelTemp]).on('selectionchange', function(c){
            $("#unitId"+count+"").val(window[magicsuggestUnitSelTemp].getValue());
            window[participantManSelectTemp].setData("magicSelectController.do?getUserList&orgIds="+$('#unitId'+count+'').val());
        });

    }


    /**生成一行*/
    function oneTr(cnt){
        return '<tr>\n' +
            '\t\t\t\t\t\t<td align="right">\n' +
            '\t\t\t\t\t\t\t<label class="Validform_label">\n' +
            '\t\t\t\t\t\t\t\t选择参与人员单位:\n' +
            '\t\t\t\t\t\t\t</label>\n' +
            '\t\t\t\t\t\t</td>\n' +
            '\t\t\t\t\t\t<td class="value">\n' +
            '\t\t\t\t\t\t\t<div id="magicsuggestUnitSel'+cnt+'" style="width: 130px;height: 15px"></div>\n' +
            '\t\t\t\t\t\t\t<input id="unitId'+cnt+'" name="unitId'+cnt+'" type="hidden" style="width: 150px" class="inputxt">\n' +
            '\t\t\t\t\t\t\t<span class="Validform_checktip"></span>\n' +
            '\t\t\t\t\t\t\t<label class="Validform_label" style="display: none;"></label>\n' +
            '\t\t\t\t\t\t</td>\n' +
            '\t\t\t\t\t\t<td align="right">\n' +
            '\t\t\t\t\t\t\t<label class="Validform_label">\n' +
            '\t\t\t\t\t\t\t\t<font color="red">*</font>选择参与人员:\n' +
            '\t\t\t\t\t\t\t</label>\n' +
            '\t\t\t\t\t\t</td>\n' +
            '\t\t\t\t\t\t<td class="value">\n' +
            '\t\t\t\t\t\t\t<div id="participantManSelect'+cnt+'" style="width: 200px;height: auto"></div>\n' +
            '\t\t\t\t\t\t\t<input id="participantMan'+cnt+'" name="participantMan'+cnt+'" type="hidden" style="width: 150px" class="inputxt"   datatype="*"  value="${riskTaskPage.participantMan}">\n' +
            '\t\t\t\t\t\t\t<span class="Validform_checktip"></span>\n' +
            '\t\t\t\t\t\t\t<label class="Validform_label" style="display: none;">选择参与人员</label>\n' +
            '                <button type="button" class="del ace_button" onclick="delTr(this);">删除此条</button>\n' +
            '\t\t\t\t\t\t</td>\n' +
            '\t\t\t\t\t</tr>';
    }








    $(function () {
        magicsuggestOrganizerManSelect = getUserMagicSuggestWithValue($('#organizerManSelect'),  $("#organizerMan"), "${organizerMan}", false);
        magicsuggestOrganizerManSelect.disable();
        magicsuggestParticipantManSelect = $('#participantManSelect').magicSuggest({
            allowFreeEntries: false,
            data:'magicSelectController.do?getUserList',
            valueField:'id',
            value: '${riskTaskPage.participantMan}'!=''?'${riskTaskPage.participantMan}'.split(","):[],
            placeholder:'输入或选择',
            maxSelection:5,
            selectFirst: true,
            matchField:['spelling','realName','userName','fullSpelling'],
            highlight: false,
            displayField:'realName'
        });

        $(magicsuggestParticipantManSelect).on('selectionchange', function(e,m){
			$("#participantMan").val(magicsuggestParticipantManSelect.getValue());
        });


        magicsuggestUnitSelSelect = $('#magicsuggestUnitSel').magicSuggest({
            allowFreeEntries: false,
            data:'magicSelectController.do?departSelectDataGridMagic',
            valueField:'id',
            placeholder:'输入或选择',
            maxSelection:1,
            selectFirst: true,
            highlight: false,
            matchField:['spelling','departName','fullSpelling'],
            displayField:'departName'
        });
        $(magicsuggestUnitSelSelect).on('selectionchange', function(c){
            $("#unitId").val(magicsuggestUnitSelSelect.getValue());
            magicsuggestParticipantManSelect.setData("magicSelectController.do?getUserList&orgIds="+$('#unitId').val());
        });



        $(magicsuggestParticipantManSelect).on('focus', function(c){
            var deptId = $('#unitId').val();
            magicsuggestParticipantManSelect.setData("magicSelectController.do?getUserList&orgIds="+deptId);
        });

        magicsuggestParticipantManSelect0 = $('#participantManSelect0').magicSuggest({
            allowFreeEntries: false,
            data:'magicSelectController.do?getUserList',
            valueField:'id',
            placeholder:'输入或选择',
            maxSelection:5,
            selectFirst: true,
            matchField:['spelling','realName','userName','fullSpelling'],
            highlight: false,
            displayField:'realName'
        });

        $(magicsuggestParticipantManSelect0).on('selectionchange', function(e,m){
            $("#participantMan0").val(magicsuggestParticipantManSelect0.getValue());
        });


        magicsuggestUnitSelSelect0 = $('#magicsuggestUnitSel0').magicSuggest({
            allowFreeEntries: false,
            data:'magicSelectController.do?departSelectDataGridMagic',
            valueField:'id',
            placeholder:'输入或选择',
            maxSelection:1,
            selectFirst: true,
            highlight: false,
            matchField:['spelling','departName','fullSpelling'],
            displayField:'departName'
        });
        $(magicsuggestUnitSelSelect0).on('selectionchange', function(c){
            $("#unitId0").val(magicsuggestUnitSelSelect0.getValue());
            magicsuggestParticipantManSelect0.setData("magicSelectController.do?getUserList&orgIds="+$('#unitId0').val());
        });



        $(magicsuggestParticipantManSelect).on('focus', function(c){
            var deptId = $('#unitId').val();
            magicsuggestParticipantManSelect.setData("magicSelectController.do?getUserList&orgIds="+deptId);
        });

		$("select[name='taskType']").on("change", function(){
			var val = $("select[name='taskType']>option:selected").val();
			if ("3"==val) {
				$("#speIdeTypeTr").css("display","");
				$("#speIdeType").attr("datatype","*");
			} else {
				$("#speIdeTypeTr").css("display","none");
				$("#speIdeType").removeAttr("datatype");
			}
		})

        /**为确定按钮绑定组装 Json 数据事件*/
        $("input[value='确定']", window.parent.document).bind("click", function (){ getCheckData() });
    });

</script>