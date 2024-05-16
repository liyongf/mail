<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>任务管理</title>
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
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskManageTaskAllManageController.do?save" beforeSubmit="showWait" callback="@Override hideWait">
			<input id="id" name="id" type="hidden" value="${riskManageTaskAllManagePage.id }">
	        <input id="checkData" name="checkData" type="hidden" value="" />
			<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable" id="tab">
				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>管控时间:
						</label>
					</td>
					<td class="value">
						<fmt:formatDate value='${riskManageTaskAllManagePage.manageTime}' type="date" pattern="yyyy-MM-dd"/>
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>管控类型:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="manageType" type="list" extendJson="{\"datatype\":\"*\"}" readonly="readonly"
									  typeGroupCode="taskManageTypeTemp" defaultVal="${riskManageTaskAllManagePage.manageType}" hasLabel="false"  title="管控类型"></t:dictSelect>
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>管控名称:
						</label>
					</td>
					<td class="value">
							${riskManageTaskAllManagePage.manageName}
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>组织人员:
						</label>
					</td>
					<td class="value">
						<div id="organizerManSelect" style="width: 130px;height: 15px"></div>
						<input id="organizerMan" name="organizerMan" type="hidden" style="width: 150px" class="inputxt"   datatype="*"  value="${organizerMan}" readonly="readonly">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">组织人员</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>结束时间:
						</label>
					</td>
					<td class="value" >
						<fmt:formatDate value='${riskManageTaskAllManagePage.endDate}' type="date" pattern="yyyy-MM-dd"/>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">结束日期</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>主要内容:
						</label>
					</td>
					<td class="value" colspan="3">
							${riskManageTaskAllManagePage.mainContents}
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							检查地点与检查人:
						</label>
					</td>
					<td class="value" colspan="5">
						<button type="button" class="add ace_button" onclick="addTr()">添加一条</button>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>检查地点:
						</label>
					</td>
					<td class="value">
						<div id="addressSelect0" style="width: 200px;height: auto"></div>
						<input id="addressId0" name="addressId0" type="hidden" style="width: 150px" class="inputxt"   datatype="*"  >
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">检查地点</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							选择检查人单位:
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
							<font color="red">*</font>检查人:
						</label>
					</td>
					<td class="value">
						<div id="checkManSelect0" style="width: 200px;height: auto"></div>
						<input id="checkManId0" name="checkManId0" type="hidden" style="width: 150px" class="inputxt"   datatype="*"  >
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">检查人</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>

 <script type="text/javascript">
     var magicsuggestOrganizerManSelect = "";
     var magicsuggestAddressSelect0 = "";
     var magicsuggestCheckManSelect0 = "";
     var  magicsuggestUnitSelSelect0 = "";
     var count = 0;
     $(function () {

         magicsuggestOrganizerManSelect = getUserMagicSuggestWithValue($('#organizerManSelect'),  $("#organizerMan"), "${organizerMan}", false);
         magicsuggestAddressSelect0 = getAddressMagicSuggestWithValueMax5($('#addressSelect0'),  $("#addressId0"), "", false);
         magicsuggestCheckManSelect0 = getUserMagicSuggestWithValueMax5($('#checkManSelect0'),  $("#checkManId0"), "", false);
         magicsuggestOrganizerManSelect.disable();
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
             magicsuggestCheckManSelect0.setData("magicSelectController.do?getUserList&orgIds="+$('#unitId0').val());
         });


         /**为确定按钮绑定组装 Json 数据事件*/
         $("input[value='确定']", window.parent.document).bind("click", function (){ getCheckData() });




     })


     /**下面是操作表格的方法*/
     /**
      * 为table指定行添加一行
      * tab 表id
      * row 行数，如：0->第一行 1->第二行 -2->倒数第二行 -1->最后一行
      * trHtml 添加行的html代码
      */
     function addTr(){
         count++;

         var trHtml = oneTr(count);
         //获取table最后一行 $("#tab tr:last")
         //获取table第一行 $("#tab tr").eq(0)
         //获取table倒数第二行 $("#tab tr").eq(-2)
         var $tr = $("#tab tr:last");
         if($tr.size()==0){
             alert("指定的table id或行数不存在！");
             return;
         }
         $tr.after(trHtml);
         var magicsuggestUnitSelTemp = "magicsuggestUnitSel"+count;
         var magicsuggestCheckManSelectTemp = "magicsuggestCheckManSelect"+count;
         window[magicsuggestCheckManSelectTemp] = getUserMagicSuggestWithValueMax5($('#checkManSelect'+count+''),  $('#checkManId'+count+''), "", false);
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
             window[magicsuggestCheckManSelectTemp].setData("magicSelectController.do?getUserList&orgIds="+$('#unitId'+count+'').val());
         });
         getAddressMagicSuggestWithValueMax5($('#addressSelect'+count+''),  $('#addressId'+count+''), "", false);


     }



     /**删除一行*/
     function delTr($this){
         $($this).parent().parent().remove();
     }

     /**生成一行*/
     function oneTr(cnt){
         return '<tr>\n' +
             '            <td align="right">\n' +
             '                <label class="Validform_label">\n' +
             '                    <font color="red">*</font>检查地点:\n' +
             '                </label>\n' +
             '            </td>\n' +
             '            <td class="value">\n' +
             '                <div id="addressSelect'+cnt+'" style="width: 200px;height: auto"></div>\n' +
             '                <input id="addressId'+cnt+'" name="addressId'+cnt+'" type="hidden" style="width: 150px" class="inputxt" datatype="*" >\n' +
             '                <span class="Validform_checktip"></span>\n' +
             '                <label class="Validform_label" style="display: none;">检查地点</label>\n' +
             '            </td>\n' +
             '            <td align="right">\n' +
             '\t\t\t\t\t\t\t<label class="Validform_label">\n' +
             '\t\t\t\t\t\t\t\t选择检查人单位:\n' +
             '\t\t\t\t\t\t\t</label>\n' +
             '\t\t\t\t\t\t</td>\n' +
             '\t\t\t\t\t\t<td class="value">\n' +
             '\t\t\t\t\t\t\t<div id="magicsuggestUnitSel'+cnt+'" style="width: 130px;height: 15px"></div>\n' +
             '\t\t\t\t\t\t\t<input id="unitId'+cnt+'" name="unitId'+cnt+'" type="hidden" style="width: 150px" class="inputxt">\n' +
             '\t\t\t\t\t\t\t<span class="Validform_checktip"></span>\n' +
             '\t\t\t\t\t\t\t<label class="Validform_label" style="display: none;"></label>\n' +
             '\t\t\t\t\t\t</td>\n' +
             '            <td align="right">\n' +
             '                <label class="Validform_label">\n' +
             '                    <font color="red">*</font>检查人:\n' +
             '                </label>\n' +
             '            </td>\n' +
             '            <td class="value">\n' +
             '                <div id="checkManSelect'+cnt+'" style="width: 200px;height: auto"></div>\n' +
             '                <input id="checkManId'+cnt+'" name="checkManId'+cnt+'" type="hidden" style="width: 150px" class="inputxt" datatype="*" >\n' +
             '                <span class="Validform_checktip"></span>\n' +
             '                <label class="Validform_label" style="display: none;">检查人</label>\n' +
             '                <button type="button" class="del ace_button" onclick="delTr(this);">删除此条</button>\n' +
             '            </td>\n' +
             '        </tr>';
     }



     /**将数据组装成为 Json 格式，传送到服务器后台。全局变量 count 记录了有多少行 tr */
     function getCheckData(){

         var data = "";
         //开始组装data数据
         var dataJson = [];
         for (var j=0; j<count+1; j++){
             var jo = {};
             if($("input[id^='addressId"+j+"']").val()!=null && $("input[id^='checkManId"+j+"']").val()!=null){
                 jo.address = $("input[id='addressId"+j+"']").val();
                 jo.checkMan =$("input[id^='checkManId"+j+"']").val();
                 dataJson.push(jo);
             }
         }
         data = JSON.stringify(dataJson);
         //将组装好的数据放到input里面，等待提交到后台
         $("#checkData").val(data);
     }

     function showWait() {
         $.messager.progress({
             text : "正在生成任务......"
         });

     }
     function hideWait(data) {
         $.messager.progress('close');
         window.top.reload_riskManageTaskAllManageList.call(data);
         frameElement.api.opener.reloadTable();
         frameElement.api.close();
     }


 </script>