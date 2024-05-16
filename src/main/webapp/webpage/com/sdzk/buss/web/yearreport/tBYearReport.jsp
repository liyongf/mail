<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>年度辨识报告</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
     <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
     <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
     <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
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
 <body style="overflow-y: auto" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBYearReportController.do?save&taskId=${taskId}" btnsub="btn">
			<input id="id" name="id" type="hidden" value="${tBYearReportPage.id }">
            <input id="checkData" name="checkData" type="hidden" value="" />
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" id="tab">
				<tr>
					<td align="right">
						<label class="Validform_label">
							单位负责人:
						</label>
					</td>
					<td class="value" colspan="4">
						<input class="inputxt" id="chargeMan" name="chargeMan" ignore="ignore"  value="${tBYearReportPage.chargeMan}" />
						<span class="Validform_checktip"></span>
					</td>
                    <td align="right">
                        <label class="Validform_label">
                            矿井名称:
                        </label>
                    </td>
                    <td class="value" colspan="4">
                        <input class="inputxt" id="mineName" name="mineName" ignore="ignore"  value="${tBYearReportPage.mineName}" />
                        <span class="Validform_checktip"></span>
                    </td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							矿井概况:
						</label>
					</td>
					<td class="value" colspan="4">
						<textarea id="mineStatus" name="mineStatus" style="width: 350px;" placeholder="请输入矿井概况" class="inputxt" rows="5">${tBYearReportPage.mineStatus}</textarea>
						<span class="Validform_checktip"></span>
					</td>
                    <td align="right">
                        <label class="Validform_label">
                            主要参考依据:
                        </label>
                    </td>
                    <td class="value" colspan="4">
                        <textarea id="mainRefer" name="mainRefer" style="width: 350px;" placeholder="请输入主要参考依据" class="inputxt" rows="5">${tBYearReportPage.mainRefer}</textarea>
                        <span class="Validform_checktip"></span>
                    </td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							矿井主要灾害:
						</label>
					</td>
					<td class="value" colspan="4">
						<textarea id="mainDanger" name="mainDanger" style="width: 350px;" placeholder="请输入主要参考依据" class="inputxt" rows="5">${tBYearReportPage.mainDanger}</textarea>
						<span class="Validform_checktip"></span>
					</td>
                    <td align="right">
                        <label class="Validform_label">
                            矿井主要生产系统:
                        </label>
                    </td>
                    <td class="value" colspan="4">
                        <textarea id="mainSystem" name="mainSystem" style="width: 350px;" placeholder="请输入矿井主要生产系统" class="inputxt" rows="5">${tBYearReportPage.mainSystem}</textarea>
                        <span class="Validform_checktip"></span>
                    </td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							辨识范围:
						</label>
					</td>
					<td class="value" colspan="4">
						<textarea id="identifyRange" name="identifyRange" style="width: 350px;" placeholder="请输入辨识范围" class="inputxt" rows="5">${tBYearReportPage.identifyRange}</textarea>
						<span class="Validform_checktip"></span>
					</td>
                    <td align="right">
                        <label class="Validform_label">
                            辨识评估方法:
                        </label>
                    </td>
                    <td class="value" colspan="4">
                        <textarea id="identifyMethod" name="identifyMethod" style="width: 350px;" placeholder="请输入辨识评估方法" class="inputxt" rows="5">${tBYearReportPage.identifyMethod}</textarea>
                        <span class="Validform_checktip"></span>
                    </td>
				</tr>
                <tr>
                    <td align="right">
                        <label class="Validform_label">
                            辨识组织:
                        </label>
                    </td>
                    <td class="value" colspan="8">
                        <textarea id="identifyGroup" name="identifyGroup" style="width: 350px;" placeholder="请输入辨识组织" class="inputxt" rows="5">${tBYearReportPage.identifyGroup}</textarea>
                        <span class="Validform_checktip"></span>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <label class="Validform_label">
                            组织表:
                        </label>
                    </td>
                    <td class="value" colspan="10">
                        <button type="button" class="add ace_button" onclick="addTr()">添加一条</button>
                    </td>
                </tr>
                <c:forEach items="${mapList}" var="maper" varStatus="status">
                    <tr>
                        <td align="right">
                            <label class="Validform_label">
                                专业组:
                            </label>
                        </td>
                        <td class="value">
                                <%--<div id="addressSelect0" style="width: 130px;height: auto"></div>--%>
                            <input id="professGroup${status.index}" name="professGroup${status.index}" type="text" style="width: 150px" class="inputxt" datatype="*" value="${maper.profess_group}" >
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">专业组</label>
                        </td>
                        <td align="right">
                            <label class="Validform_label">
                                分管领导:
                            </label>
                        </td>
                        <td class="value">
                                <%--<div id="checkManSelect0" style="width: 130px;height: auto"></div>--%>
                            <input id="chargeLeader${status.index}" name="chargeLeader${status.index}" type="text" style="width: 150px" class="inputxt"   datatype="*" value="${maper.charge_leader}" >
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">分管领导</label>
                        </td>
                        <td align="right">
                            <label class="Validform_label">
                                牵头部门:
                            </label>
                        </td>
                        <td class="value">
                                <%--<div id="checkManSelect0" style="width: 130px;height: auto"></div>--%>
                            <input id="leadDepart${status.index}" name="leadDepart${status.index}" type="text" style="width: 150px" class="inputxt"   datatype="*" value="${maper.lead_depart}" >
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">牵头部门</label>
                        </td>
                        <td align="right">
                            <label class="Validform_label">
                                责任单位:
                            </label>
                        </td>
                        <td class="value">
                                <%--<div id="checkManSelect0" style="width: 130px;height: auto"></div>--%>
                            <input id="dutyDepart${status.index}" name="dutyDepart${status.index}" type="text" style="width: 150px" class="inputxt"   datatype="*" value="${maper.duty_depart}" >
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">责任单位</label>
                            <button type="button" class="del ace_button" onclick="delTr(this);">删除此条</button>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="8">
                        <div class="ui_buttons" style="text-align: center;">
                            <input type="button" id="btn" value="保存" class="ui_state_highlight" onclick="formSubmit()">
                            <input type="button" id="btn_close" onclick="closeWindow();" value="关闭">
                        </div>
                    </td>
                </tr>
			</table>
		</t:formvalid>
 </body>
 <script type="text/javascript">
     var count = ${count};
     $(function () {
         /**为确定按钮绑定组装 Json 数据事件*/
         // $("input[value='保存']", window.parent.document).bind("click", function (){ getCheckData() });
     })
     function addTr(){
         count++;

         var trHtml = oneTr(count);
         //获取table最后一行 $("#tab tr:last")
         //获取table第一行 $("#tab tr").eq(0)
         //获取table倒数第二行 $("#tab tr").eq(-2)
         var $tr = $("#tab tr").eq(-2);
         if($tr.size()==0){
             alert("指定的table id或行数不存在！");
             return;
         }
         $tr.after(trHtml);
         // getAddressMagicSuggestWithValue($('#addressSelect'+count+''),  $('#addressId'+count+''), "", false);
         // getUserMagicSuggestWithValueMax5($('#checkManSelect'+count+''),  $('#checkManId'+count+''), "", false);
     }
     /**生成一行*/
     function oneTr(cnt){
         return '<tr>\n' +
             '            <td align="right">\n' +
             '                <label class="Validform_label">\n' +
             '                    专业组:\n' +
             '                </label>\n' +
             '            </td>\n' +
             '            <td class="value">\n' +
             '                <input id="professGroup'+cnt+'" name="professGroup'+cnt+'" type="text" style="width: 150px" class="inputxt">\n' +
             '                <span class="Validform_checktip"></span>\n' +
             '                <label class="Validform_label" style="display: none;">专业组</label>\n' +
             '            </td>\n' +
             '            <td align="right">\n' +
             '                <label class="Validform_label">\n' +
             '                    分管领导:\n' +
             '                </label>\n' +
             '            </td>\n' +
             '            <td class="value">\n' +
             '                <input id="chargeLeader'+cnt+'" name="chargeLeader'+cnt+'" type="text" style="width: 150px" class="inputxt">\n' +
             '                <span class="Validform_checktip"></span>\n' +
             '                <label class="Validform_label" style="display: none;">分管领导</label>\n' +
             '            </td>\n' +
             '            <td align="right">\n' +
             '                <label class="Validform_label">\n' +
             '                    牵头部门:\n' +
             '                </label>\n' +
             '            </td>\n' +
             '            <td class="value">\n' +
             '                <input id="leadDepart'+cnt+'" name="leadDepart'+cnt+'" type="text" style="width: 150px" class="inputxt" >\n' +
             '                <span class="Validform_checktip"></span>\n' +
             '                <label class="Validform_label" style="display: none;">牵头部门</label>\n' +
             '            </td>\n' +
             '            <td align="right">\n' +
             '                <label class="Validform_label">\n' +
             '                    责任单位:\n' +
             '                </label>\n' +
             '            </td>\n' +
             '            <td class="value" colspan="3">\n' +
             '                <input id="dutyDepart'+cnt+'" name="dutyDepart'+cnt+'" type="text" style="width: 150px" class="inputxt" >\n' +
             '                <span class="Validform_checktip"></span>\n' +
             '                <label class="Validform_label" style="display: none;">责任单位</label>\n' +
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
             if($("input[id^='professGroup"+j+"']").val()!=null && $("input[id^='chargeLeader"+j+"']").val()!=null && $("input[id^='leadDepart"+j+"']").val()!=null && $("input[id^='dutyDepart"+j+"']").val()!=null){
                 jo.professGroup = $("input[id='professGroup"+j+"']").val();
                 jo.chargeLeader =$("input[id^='chargeLeader"+j+"']").val();
                 jo.leadDepart =$("input[id^='leadDepart"+j+"']").val();
                 jo.dutyDepart =$("input[id^='dutyDepart"+j+"']").val();
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
     function formSubmit(){
         // alert(1);
         getCheckData();
         $("#btn").trigger("click");

     }
     function closeWindow() {
         frameElement.api.close();
     }
 </script>