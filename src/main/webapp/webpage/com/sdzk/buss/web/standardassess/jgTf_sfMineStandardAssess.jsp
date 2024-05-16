<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>通风录入</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
     <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
  <script type="text/javascript">
  //编写自定义JS代码
  
  </script>
    <style>
        td{	
        	padding:0px;
        	text-align:center;
        	vertical-align:center;
        	}
        .td-padding{
        	padding-left: 8px !important;
        	padding-right: 8px !important;
        	text-align: left !important;
        }
        .td-padding label{
        	text-align: left !important;
        	word-wrap:break-word !important;
        	word-break: break-all !important;
        	white-space: inherit !important;
        	}
        label{
        	padding:0 !important;
        	}
        tr{
        	height:30px;
        	}

        /*Author:  张赛超*/
        .inputxt{
            margin-top: 20px;
        }
    </style>
 </head>
 <body>
  <%--<c:if test="${from ne 'mineAssess'}">--%>
     <%--<!-- from :mineAssess 页面请求来源于矿井考核自评汇总的时候不显示该面包屑，否则显示面包屑 -->--%>
 <%--<div class="wz-bt" >您当前的位置：考核评分管理->井工煤矿->通风</div>--%>
 <%--</c:if>--%>
 <t:formvalid formid="formobj" dialog="true" layout="table" action="sfMineStandardAssessController.do?doAdd" tiptype="3" btnsub="btn_sub" callback="@Override saveCallback" >
 <input type="hidden" id="btn_sub" class="btn_sub">
 <input id="id" name="id" type="hidden" value="${sfMineStandardAssessPage.id }">
 <input id="ssaMineType" name="ssaMineType" type="hidden" value="${mineType }">
 <input id="ssaAssessType" name="ssaAssessType" type="hidden" value="${assessType }">
 <input id="ssaCurrentStatus" name="ssaCurrentStatus" type="hidden" value="${sfMineStandardAssessPage.ssaCurrentStatus }">
 <input id="createBy" name="createBy" type="hidden" value="${sfMineStandardAssessPage.createBy }">
 <input id="createName" name="createName" type="hidden" value="${sfMineStandardAssessPage.createName }">
 <input id="createDate" name="createDate" type="hidden" value="${sfMineStandardAssessPage.createDate }">
 <input id="updateBy" name="updateBy" type="hidden" value="${sfMineStandardAssessPage.updateBy }">
 <input id="updateName" name="updateName" type="hidden" value="${sfMineStandardAssessPage.updateName }">
 <input id="updateDate" name="updateDate" type="hidden" value="${sfMineStandardAssessPage.updateDate }">

          <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable" align="center">
              <tr style="height: 40px;">
                  <td colspan='7' align="center" style="font-size:20px;font-weight: bold;">煤矿通风标准化评分</td>
              </tr>
              <tr>
                  <td class="value" colspan="6" style="text-align: left">
                      <label class="Validform_label">
                          不参与考核:
                      </label>
                      <label>
                          <input type="checkbox" name="ignore1" value="Y" title='通风系统' <c:if test="${scoreDetail.ignore1=='Y'}">checked="checked"</c:if>/>通风系统
                      </label>
                      <label>
                          <input type="checkbox" name="ignore2" value="Y" title='局部通风' <c:if test="${scoreDetail.ignore2=='Y'}">checked="checked"</c:if>/>局部通风
                      </label>
                      <label>
                          <input type="checkbox" name="ignore3" value="Y" title='通风设施' <c:if test="${scoreDetail.ignore3=='Y'}">checked="checked"</c:if>/>通风设施
                      </label>
                      <label>
                          <input type="checkbox" name="ignore4" value="Y" title='瓦斯管理' <c:if test="${scoreDetail.ignore4=='Y'}">checked="checked"</c:if>/>瓦斯管理
                      </label>
                      <label>
                          <input type="checkbox" name="ignore5" value="Y" title='突出防治' <c:if test="${scoreDetail.ignore5=='Y'}">checked="checked"</c:if>/>突出防治
                      </label>
                      <label>
                          <input type="checkbox" name="ignore6" value="Y" title='瓦斯抽采' <c:if test="${scoreDetail.ignore6=='Y'}">checked="checked"</c:if>/>瓦斯抽采
                      </label>
                      <label>
                          <input type="checkbox" name="ignore7" value="Y" title='安全监控' <c:if test="${scoreDetail.ignore7=='Y'}">checked="checked"</c:if>/>安全监控
                      </label>
                      <label>
                          <input type="checkbox" name="ignore8" value="Y" title='防灭火' <c:if test="${scoreDetail.ignore8=='Y'}">checked="checked"</c:if>/>防灭火
                      </label>
                      <label>
                          <input type="checkbox" name="ignore9" value="Y" title='粉尘防治' <c:if test="${scoreDetail.ignore9=='Y'}">checked="checked"</c:if>/>粉尘防治
                      </label>
                      <label>
                          <input type="checkbox" name="ignore10" value="Y" title='井下爆破' <c:if test="${scoreDetail.ignore10=='Y'}">checked="checked"</c:if>/>井下爆破
                      </label>
                      <label>
                          <input type="checkbox" name="ignore11" value="Y" title='基础管理' <c:if test="${scoreDetail.ignore11=='Y'}">checked="checked"</c:if>/>基础管理
                      </label>
                  </td>
				  
                  <td class="value" style="text-align: right">
                      <label class="Validform_label">
                          <font color="red">*</font>考核月份:
                      </label>
                      <input id="ssaMonth" name="ssaMonth" type="text" style="width: 150px"
                             class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM'})" datatype="*" value='<fmt:formatDate value='${sfMineStandardAssessPage.ssaMonthDate}' type="date" pattern="yyyy-MM"/>'
                              >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">考核月份</label>
				  </td>
              </tr>
          </table>
 <div style="width: auto; height: 200px;">
 <div style="width: auto; height: 1px;"></div>
 <div id="tt" class="easyui-tabs" data-options="onSelect:function(t){$('#tt .panel-body').css('width','auto');}">
              <div title="通风系统">
              <table style="width: 95%;" cellpadding="0" cellspacing="1"  class="formtable" >

              <tr>
                  <td align="center">
                      <label class="Validform_label">
                          项目
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label">
                          项目内容
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label" style="min-width:150px;">
                          基本要求
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          标准分值
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label" style="min-width:150px;">
                          评分方法
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          是否缺项
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          得分
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          备注
                      </label>
                  </td>
              </tr>
              <tr>
                  <td  rowspan="9">
                      <label class="Validform_label">
                          一、<br>通<br>风<br>系<br>统<br>（100分）
                      </label>
                  </td>
                  <td  rowspan="4">
                      <label class="Validform_label">系统<br>管理</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.全矿井、一翼或者一个水平通风系统改变时，编制通风设计及安全技术措施，经企业技术负责人审批；巷道
                          贯通前应当制定贯通专项措施，经矿总工程师审批；井下爆炸物品库、充电硐室、采区变电所、实现采区变电
                          所功能的中央变电所有独立的通风系统
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack1_1">
                          20
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。改变通风系统（巷道贯通）无审批措施的扣10分，其他1处不符合要求扣5分
                      </label>
                  </td>
                  <td class="value" >
                      <select name="lack1_1">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack1_1=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack1_1=='N'||scoreDetail.lack1_1==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score1_1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_1}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark1_1" rows="3" class="inputxt">${scoreDetail.remark1_1}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.井下没有违反《煤矿安全规程》规定的扩散通风、采空区通风和利用局部通风机通风的采煤工作面；对于允许布置的串联通风，制定安全技术措施，其中开拓新水平和准备新采区的开掘巷道的回风引入生产水平的进风中的安全技术措施，经企业技术负责人审批，其他串联通风的安全技术措施，经矿总工程师审批
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack1_2">
                          20
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场和资料。不符合要求1处扣10分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack1_2">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack1_2=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack1_2=='N'||scoreDetail.lack1_2==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score1_2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_2}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark1_2" rows="3" class="inputxt">${scoreDetail.remark1_2}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          3.采区专用回风巷不用于运输、安设电气设备，突出区不行人；专用回风巷道维修时制定专项措施，经矿总工程师审批
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack1_3">
                          5
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场和资料。不符合要求1处扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack1_3">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack1_3=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack1_3=='N'||scoreDetail.lack1_3==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score1_3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_3}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark1_3" rows="3" class="inputxt">${scoreDetail.remark1_3}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          4.装有主要通风机的回风井口的防爆门符合规定，每6个月检查维修1次；每季度至少检查1次反风设施；制定年度全矿性反风技术方案，按规定审批，实施有总结报告，并达到反风效果
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack1_4">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。未进行反风演习扣5分，其他1处不符合要求扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack1_4">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack1_4=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack1_4=='N'||scoreDetail.lack1_4==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score1_4" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_4}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark1_4" rows="3" class="inputxt">${scoreDetail.remark1_4}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="5">
                      <label class="Validform_label">风量<br>配置</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.新安装的主要通风机投入使用前，进行1次通风机性能测定和试运转工作，投入使用后每5年至少进行1次性能测定；矿井通风阻力测定符合《煤矿安全规程》规定
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack1_5">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料。通风机性能或者通风阻力未测定的不得分，其他1处不符合要求扣1分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack1_5">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack1_5=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack1_5=='N'||scoreDetail.lack1_5==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score1_5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_5}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark1_5" rows="3" class="inputxt">${scoreDetail.remark1_5}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.矿井每年进行1次通风能力核定；每10天至少进行1次井下全面测风，井下各硐室和巷道的供风量满足计算所需风量
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack1_6">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。未进行通风能力核定不得分，其他1处不符合要求扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack1_6">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack1_6=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack1_6=='N'||scoreDetail.lack1_6==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score1_6" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_6}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark1_6" rows="3" class="inputxt">${scoreDetail.remark1_6}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          3.矿井有效风量率不低于85％；矿井外部漏风率每年至少测定1次，外部漏风率在无提升设备时不得超过5％，有提升设备时不得超过15％
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack1_7">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料。未测定扣5分，有效风量率每低、外部漏风率每高1个百分点扣1分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack1_7">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack1_7=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack1_7=='N'||scoreDetail.lack1_7==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score1_7" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_7}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark1_7" rows="3" class="inputxt">${scoreDetail.remark1_7}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          4.采煤工作面进、回风巷实际断面不小于设计断面的2/3；其他通风巷道实际断面不小于设计断面的4/5；矿井通风系统的阻力符合AQ1028规定；矿井内各地点风速符合《煤矿安全规程》规定
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack1_8">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场和资料。巷道断面1处（长度按5m计）不符或者阻力超规定扣2分；风速不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack1_8">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack1_8=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack1_8=='N'||scoreDetail.lack1_8==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score1_8" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_8}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark1_8" rows="3" class="inputxt">${scoreDetail.remark1_8}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          5.矿井主要通风机安设监测系统，能够实时准确监测风机运行状态、风量、风压等参数
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack1_9">
                          5
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。未安监测系统的不得分，其他1处不符合要求扣1分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack1_9">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack1_9=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack1_9=='N'||scoreDetail.lack1_9==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score1_9" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_9}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark1_9" rows="3" class="inputxt">${scoreDetail.remark1_9}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
				<tr>
					<td  style="text-align: center;" colspan="2">
						<label class="Validform_label">
							<font color="red">*</font>单项得分
						</label>
					</td>
					<td class="value" style="text-align: left;" colspan="6">
						<input id="ssaSumScore1" idx='1' name="ssaSumScore1" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore1}" datatype="/^100(\.0+)?$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">单项得分</label>
					</td>
				</tr>
              </table>
                  </div>
                  <div title="局部通风">
                  <table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

                  <tr>
                      <td align="center">
                          <label class="Validform_label">
                              项目
                          </label>
                      </td>
                      <td align="center">
                          <label class="Validform_label">
                              项目内容
                          </label>
                      </td>
                      <td align="center">
                          <label class="Validform_label" style="min-width:150px;">
                              基本要求
                          </label>
                      </td>
                      <td>
                          <label class="Validform_label">
                              标准分值
                          </label>
                      </td>
                      <td>
                          <label class="Validform_label" style="min-width:150px;">
                              评分方法
                          </label>
                      </td>
                      <td>
                          <label class="Validform_label">
                              是否缺项
                          </label>
                      </td>
                      <td>
                          <label class="Validform_label">
                              得分
                          </label>
                      </td>
                      <td>
                          <label class="Validform_label">
                              备注
                          </label>
                      </td>
                  </tr>
                  <tr>
                      <td  rowspan="6">
                          <label class="Validform_label">
                              二、<br>局<br>部<br>通<br>风<br>（100分）
                          </label>
                      </td>
                      <td  rowspan="2">
                          <label class="Validform_label">装备<br>措施</label>
                      </td>
                      <td class="td-padding">
                          <label class="Validform_label">
                              1.掘进通风方式符合《煤矿安全规程》规定，采用局部通风机供风的掘进巷道应安设同等能力的备用局部通风机，实现自动切换。局部通风机的安装、使用符合《煤矿安全规程》规定，实行挂牌管理，不发生循环风；不出现无计划停风，有计划停风前制定专项通风安全技术措施
                          </label>
                      </td>
                      <td >
                          <label class="Validform_label" name="lack2_1">
                              35
                          </label>
                      </td>
                      <td class="td-padding">
                          <label class="Validform_label">
                              查现场和资料。1处发生循环风不得分；无计划停风1次扣10分；其他1处不符合要求扣2分
                          </label>
                      </td>
                      <td class="value" >
                          <select name="lack2_1">
                              
                              <option value="Y" <c:if test="${scoreDetail.lack2_1=='Y'}">selected="selected"</c:if>>是 </option>
                              <option value="N" <c:if test="${scoreDetail.lack2_1=='N'||scoreDetail.lack2_1==null}">selected="selected"</c:if>>否 </option>
                          </select>
                      </td>

                      <td class="value" style="max-width:150px;">
                          <input  name="score2_1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_1}" >
                          <span class="Validform_checktip"></span>
                          <label class="Validform_label" style="display: none;">得分</label>
                      </td>
                      <td class="value">
                          <textarea name="remark2_1" rows="3" class="inputxt">${scoreDetail.remark2_1}</textarea>
                          <span class="Validform_checktip"></span>
                          <label class="Validform_label" style="display: none;">备注</label>
                      </td>
                  </tr>
                  <tr>
                      <td class="td-padding">
                          <label class="Validform_label">
                              2.局部通风机设备齐全，装有消音器（低噪声局部通风机和除尘风机除外），吸风口有风罩和整流器，高压部位有衬垫；局部通风机及其启动装置安设在进风巷道中，地点距回风口大于10m，且10m范围内巷道支护完好，无淋水、积水、淤泥和杂物；局部通风机离巷道底板高度不小于0.3m
                          </label>
                      </td>
                      <td >
                          <label class="Validform_label" name="lack2_2">
                              15
                          </label>
                      </td>
                      <td class="td-padding">
                          <label class="Validform_label">
                              查现场。不符合要求1处扣2分
                          </label>
                      </td>
                      <td class="value">
                          <select name="lack2_2">
                              
                              <option value="Y" <c:if test="${scoreDetail.lack2_2=='Y'}">selected="selected"</c:if>>是 </option>
                              <option value="N" <c:if test="${scoreDetail.lack2_2=='N'||scoreDetail.lack2_2==null}">selected="selected"</c:if>>否 </option>
                          </select>
                      </td>

                      <td class="value" style="max-width:150px;">
                          <input  name="score2_2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_2}" >
                          <span class="Validform_checktip"></span>
                          <label class="Validform_label" style="display: none;">得分</label>
                      </td>
                      <td class="value">
                          <textarea name="remark2_2" rows="3" class="inputxt">${scoreDetail.remark2_2}</textarea>
                          <span class="Validform_checktip"></span>
                          <label class="Validform_label" style="display: none;">备注</label>
                      </td>
                  </tr>

                  <tr>
                      <td rowspan="4">
                          <label class="Validform_label">风筒<br>敷设</label>
                      </td>
                      <td class="td-padding">
                          <label class="Validform_label">
                              1.风筒末端到工作面的距离和自动切换的交叉风筒接头的规格、安设标准符合作业规程规定
                          </label>
                      </td>
                      <td >
                          <label class="Validform_label" name="lack2_3">
                              10
                          </label>
                      </td>
                      <td class="td-padding">
                          <label class="Validform_label">
                              查现场和资料。不符合要求1处扣5分
                          </label>
                      </td>
                      <td class="value">
                          <select name="lack2_3">
                              
                              <option value="Y" <c:if test="${scoreDetail.lack2_3=='Y'}">selected="selected"</c:if>>是 </option>
                              <option value="N" <c:if test="${scoreDetail.lack2_3=='N'||scoreDetail.lack2_3==null}">selected="selected"</c:if>>否 </option>
                          </select>
                      </td>

                      <td class="value" style="max-width:150px;">
                          <input  name="score2_3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_3}" >
                          <span class="Validform_checktip"></span>
                          <label class="Validform_label" style="display: none;">得分</label>
                      </td>
                      <td class="value">
                          <textarea name="remark2_3" rows="3" class="inputxt">${scoreDetail.remark2_3}</textarea>
                          <span class="Validform_checktip"></span>
                          <label class="Validform_label" style="display: none;">备注</label>
                      </td>
                  </tr>
                  <tr>
                      <td class="td-padding">
                          <label class="Validform_label">
                              2.使用抗静电、阻燃风筒，实行编号管理。风筒接头严密，无破口（末端20m除外），无反接头；软质风筒接头反压边，硬质风筒接头加垫、螺钉紧固
                          </label>
                      </td>
                      <td >
                          <label class="Validform_label" name="lack2_4">
                              15
                          </label>
                      </td>
                      <td class="td-padding">
                          <label class="Validform_label">
                              查现场。使用非抗静电、非阻燃风筒不得分；其他1处不符合要求扣0.5分
                          </label>
                      </td>
                      <td class="value">
                          <select name="lack2_4">
                              
                              <option value="Y" <c:if test="${scoreDetail.lack2_4=='Y'}">selected="selected"</c:if>>是 </option>
                              <option value="N" <c:if test="${scoreDetail.lack2_4=='N'||scoreDetail.lack2_4==null}">selected="selected"</c:if>>否 </option>
                          </select>
                      </td>

                      <td class="value" style="max-width:150px;">
                          <input  name="score2_4" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_4}" >
                          <span class="Validform_checktip"></span>
                          <label class="Validform_label" style="display: none;">得分</label>
                      </td>
                      <td class="value">
                          <textarea name="remark2_4" rows="3" class="inputxt">${scoreDetail.remark2_4}</textarea>
                          <span class="Validform_checktip"></span>
                          <label class="Validform_label" style="display: none;">备注</label>
                      </td>
                  </tr>
                  <tr>
                      <td class="td-padding">
                          <label class="Validform_label">
                              3.风筒吊挂平、直、稳，软质风筒逢环必挂，硬质风筒每节至少吊挂2处；风筒不被摩擦、挤压
                          </label>
                      </td>
                      <td >
                          <label class="Validform_label" name="lack2_5">
                              15
                          </label>
                      </td>
                      <td class="td-padding">
                          <label class="Validform_label">
                              查现场。不符合要求1处扣0.5分
                          </label>
                      </td>
                      <td class="value">
                          <select name="lack2_5">
                              
                              <option value="Y" <c:if test="${scoreDetail.lack2_5=='Y'}">selected="selected"</c:if>>是 </option>
                              <option value="N" <c:if test="${scoreDetail.lack2_5=='N'||scoreDetail.lack2_5==null}">selected="selected"</c:if>>否 </option>
                          </select>
                      </td>

                      <td class="value" style="max-width:150px;">
                          <input  name="score2_5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_5}" >
                          <span class="Validform_checktip"></span>
                          <label class="Validform_label" style="display: none;">得分</label>
                      </td>
                      <td class="value">
                          <textarea name="remark2_5" rows="3" class="inputxt">${scoreDetail.remark2_5}</textarea>
                          <span class="Validform_checktip"></span>
                          <label class="Validform_label" style="display: none;">备注</label>
                      </td>
                  </tr>
                  <tr>
                      <td class="td-padding">
                          <label class="Validform_label">
                              4.风筒拐弯处用弯头或者骨架风筒缓慢拐弯，不拐死弯；异径风筒接头采用过渡节，无花接
                          </label>
                      </td>
                      <td >
                          <label class="Validform_label" name="lack2_6">
                              10
                          </label>
                      </td>
                      <td class="td-padding">
                          <label class="Validform_label">
                              查现场。不符合要求1处扣1分
                          </label>
                      </td>
                      <td class="value">
                          <select name="lack2_6">
                              
                              <option value="Y" <c:if test="${scoreDetail.lack2_6=='Y'}">selected="selected"</c:if>>是 </option>
                              <option value="N" <c:if test="${scoreDetail.lack2_6=='N'||scoreDetail.lack2_6==null}">selected="selected"</c:if>>否 </option>
                          </select>
                      </td>

                      <td class="value" style="max-width:150px;">
                          <input  name="score2_6" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_6}" >
                          <span class="Validform_checktip"></span>
                          <label class="Validform_label" style="display: none;">得分</label>
                      </td>
                      <td class="value">
                          <textarea name="remark2_6" rows="3" class="inputxt">${scoreDetail.remark2_6}</textarea>
                          <span class="Validform_checktip"></span>
                          <label class="Validform_label" style="display: none;">备注</label>
                      </td>
                  </tr>

					<tr>
						<td  style="text-align: center;" colspan="2">
							<label class="Validform_label">
								<font color="red">*</font>单项得分
							</label>
						</td>
						<td class="value" style="text-align: left;" colspan="6">
							<input id="ssaSumScore2" idx='2' name="ssaSumScore2" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore2}" datatype="/^100(\.0+)?$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">单项得分</label>
						</td>
					</tr>
                  </table>
                  </div>
              <div title="通风设施">
              <table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

              <tr>
                  <td align="center">
                      <label class="Validform_label">
                          项目
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label">
                          项目内容
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label" style="min-width:150px;">
                          基本要求
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          标准分值
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label" style="min-width:150px;">
                          评分方法
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          是否缺项
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          得分
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          备注
                      </label>
                  </td>
              </tr>
              <tr>
                  <td  rowspan="11">
                      <label class="Validform_label">
                          三、<br>通<br>风<br>设<br>施<br>（100分）
                      </label>
                  </td>
                  <td  rowspan="4">
                      <label class="Validform_label">设施<br>管理</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.及时构筑通风设施（指永久密闭、风门、风窗和风桥），设施墙（桥）体采用不燃性材料构筑，其厚度不小于0.5m（防突风门、风窗墙体不小于0.8m），严密不漏风
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack3_1">
                          15
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。应建未建或者构筑不及时不得分；其他1处不符合要求扣10分
                      </label>
                  </td>
                  <td class="value" >
                      <select name="lack3_1">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack3_1=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack3_1=='N'||scoreDetail.lack3_1==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score3_1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_1}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark3_1" rows="3" class="inputxt">${scoreDetail.remark3_1}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.密闭、风门、风窗墙体周边按规定掏槽，墙体与煤岩接实，四周有不少于0.1m的裙边，周边及围岩不漏风；墙面平整、无裂缝、重缝和空缝，并进行勾缝或者抹面或者喷浆，抹面的墙面1m2内凸凹深度不大于10mm
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack3_2">
                          7
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack3_2">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack3_2=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack3_2=='N'||scoreDetail.lack3_2==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score3_2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_2}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark3_2" rows="3" class="inputxt">${scoreDetail.remark3_2}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          3.设施5m范围内支护完好，无片帮、漏顶、杂物、积水和淤泥
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack3_3">
                          4
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。1处不符合要求不得分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack3_3">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack3_3=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack3_3=='N'||scoreDetail.lack3_3==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score3_3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_3}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark3_3" rows="3" class="inputxt">${scoreDetail.remark3_3}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          4.设施统一编号，每道设施有规格统一的施工说明及检查维护记录牌
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack3_4">
                          4
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。1处不符合要求不得分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack3_4">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack3_4=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack3_4=='N'||scoreDetail.lack3_4==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score3_4" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_4}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark3_4" rows="3" class="inputxt">${scoreDetail.remark3_4}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="2">
                      <label class="Validform_label">密闭</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.密闭位置距全风压巷道口不大于5m，设有规格统一的瓦斯检查牌板和警标，距巷道口大于2m的设置栅栏；密闭前无瓦斯积聚。所有导电体在密闭处断开（在用的管路采取绝缘措施处理除外）
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack3_5">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack3_5">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack3_5=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack3_5=='N'||scoreDetail.lack3_5==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score3_5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_5}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark3_5" rows="3" class="inputxt">${scoreDetail.remark3_5}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.密闭内有水时设有反水池或者反水管，采空区密闭设有观测孔、措施孔，且孔口设置阀门或者带有水封结构
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack3_6">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack3_6">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack3_6=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack3_6=='N'||scoreDetail.lack3_6==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score3_6" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_6}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark3_6" rows="3" class="inputxt">${scoreDetail.remark3_6}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="3">
                      <label class="Validform_label">风门<br>风窗</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.每组风门不少于2道，其间距不小于5m（通车风门间距不小于1列车长度），主要进、回风巷之间的联络巷设具有反向功能的风门，其数量不少于2道；通车风门按规定设置和管理，并有保护风门及人员的安全措施
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack3_7">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack3_7">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack3_7=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack3_7=='N'||scoreDetail.lack3_7==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score3_7" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_7}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark3_7" rows="3" class="inputxt">${scoreDetail.remark3_7}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.风门能自动关闭，并连锁，使2道风门不能同时打开；门框包边沿口，有衬垫，四周接触严密，门扇平整不漏风；风窗有可调控装置，调节可靠
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack3_8">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack3_8">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack3_8=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack3_8=='N'||scoreDetail.lack3_8==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score3_8" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_8}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark3_8" rows="3" class="inputxt">${scoreDetail.remark3_8}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          3.风门、风窗水沟处设有反水池或者挡风帘，轨道巷通车风门设有底槛，电缆、管路孔堵严，风筒穿过风门（风窗）墙体时，在墙上安装与胶质风筒直径匹配的硬质风筒
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack3_9">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack3_9">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack3_9=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack3_9=='N'||scoreDetail.lack3_9==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score3_9" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_9}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark3_9" rows="3" class="inputxt">${scoreDetail.remark3_9}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="2">
                      <label class="Validform_label">风桥</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.风桥两端接口严密，四周为实帮、实底，用混凝土浇灌填实；桥面规整不漏风
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack3_10">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack3_10">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack3_10=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack3_10=='N'||scoreDetail.lack3_10==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score3_10" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_10}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark3_10" rows="3" class="inputxt">${scoreDetail.remark3_10}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.风桥通风断面不小于原巷道断面的4/5，呈流线型，坡度小于30°；风桥上、下不安设风门、调节风窗等
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack3_11">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack3_11">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack3_11=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack3_11=='N'||scoreDetail.lack3_11==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score3_11" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_11}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark3_11" rows="3" class="inputxt">${scoreDetail.remark3_11}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>

				<tr>
					<td  style="text-align: center;" colspan="2">
						<label class="Validform_label">
							<font color="red">*</font>单项得分
						</label>
					</td>
					<td class="value" style="text-align: left;" colspan="6">
						<input id="ssaSumScore3" idx='3' name="ssaSumScore3" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore3}" datatype="/^100(\.0+)?$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">单项得分</label>
					</td>
				</tr>
              </table>
              </div>
              <div title="瓦斯管理">
              <table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

              <tr>
                  <td align="center">
                      <label class="Validform_label">
                          项目
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label">
                          项目内容
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label" style="min-width:150px;">
                          基本要求
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          标准分值
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label" style="min-width:150px;">
                          评分方法
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          是否缺项
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          得分
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          备注
                      </label>
                  </td>
              </tr>
              <tr>
                  <td  rowspan="8">
                      <label class="Validform_label">
                          四、<br>瓦<br>斯<br>管<br>理<br>（100分）
                      </label>
                  </td>
                  <td  rowspan="2">
                      <label class="Validform_label">鉴定<br>及措<br>施</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.按《煤矿安全规程》规定进行煤层瓦斯含量、瓦斯压力等参数测定和矿井瓦斯等级鉴定及瓦斯涌出量测定
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack4_1">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料。未鉴定、测定不得分
                      </label>
                  </td>
                  <td class="value" >
                      <select name="lack4_1">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack4_1=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack4_1=='N'||scoreDetail.lack4_1==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score4_1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_1}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark4_1" rows="3" class="inputxt">${scoreDetail.remark4_1}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.编制年度瓦斯治理技术方案及安全技术措施，并严格落实
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack4_2">
                          15
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料。未编制1项扣5分；其他1处不符合要求扣1分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack4_2">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack4_2=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack4_2=='N'||scoreDetail.lack4_2==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score4_2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_2}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark4_2" rows="3" class="inputxt">${scoreDetail.remark4_2}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="3">
                      <label class="Validform_label">瓦斯<br>检查</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.矿长、总工程师、爆破工、采掘区队长、通风区队长、工程技术人员、班长、流动电钳工、安全监测工等下井时，携带便携式甲烷检测报警仪。瓦斯检查工下井时携带便携式甲烷检测报警仪和光学瓦斯检测仪
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack4_3">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场或者资料。不符合要求1处扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack4_3">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack4_3=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack4_3=='N'||scoreDetail.lack4_3==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score4_3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_3}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark4_3" rows="3" class="inputxt">${scoreDetail.remark4_3}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.瓦斯检查符合《煤矿安全规程》规定；瓦斯检查工在井下指定地点交接班，有记录
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack4_4">
                          15
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack4_4">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack4_4=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack4_4=='N'||scoreDetail.lack4_4==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score4_4" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_4}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark4_4" rows="3" class="inputxt">${scoreDetail.remark4_4}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          3.瓦斯检查做到井下记录牌、瓦斯检查手册、瓦斯检查班报（台账）“三对口”；瓦斯检查日报及时上报矿长、总工程师签字，并有记录
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack4_5">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。不符合要求1处扣1分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack4_5">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack4_5=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack4_5=='N'||scoreDetail.lack4_5==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score4_5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_5}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark4_5" rows="3" class="inputxt">${scoreDetail.remark4_5}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="3">
                      <label class="Validform_label">现场<br>管理</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.采掘工作面及其他地点的瓦斯浓度符合《煤矿安全规程》规定；瓦斯超限立即切断电源，并撤出人员，查明瓦斯超限原因，落实防治措施
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack4_6">
                          15
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。瓦斯超限1次扣5分；其他1处不符合要求扣1分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack4_6">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack4_6=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack4_6=='N'||scoreDetail.lack4_6==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score4_6" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_6}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark4_6" rows="3" class="inputxt">${scoreDetail.remark4_6}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.临时停风地点停止作业、切断电源、撤出人员、设置栅栏和警示标志；长期停风区在24h内封闭完毕。停风区内甲烷或者二氧化碳浓度达到3.0%或者其他有害气体浓度超过《煤矿安全规程》规定不立即处理时，在24h内予以封闭，并切断通往封闭区的管路、轨道和电缆等导电物体
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack4_7">
                          15
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。未按规定执行1项扣10分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack4_7">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack4_7=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack4_7=='N'||scoreDetail.lack4_7==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score4_7" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_7}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark4_7" rows="3" class="inputxt">${scoreDetail.remark4_7}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          3.瓦斯排放按规定编制专项措施，经矿总工程师批准，并严格执行，且有记录；采煤工作面不使用局部通风机稀释瓦斯
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack4_8">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料。无措施或者未执行不得分；其他1处不符合要求扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack4_8">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack4_8=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack4_8=='N'||scoreDetail.lack4_8==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score4_8" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_8}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark4_8" rows="3" class="inputxt">${scoreDetail.remark4_8}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>

				<tr>
					<td  style="text-align: center;" colspan="2">
						<label class="Validform_label">
							<font color="red">*</font>单项得分
						</label>
					</td>
					<td class="value" style="text-align: left;" colspan="6">
						<input id="ssaSumScore4" idx='4' name="ssaSumScore4" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore4}" datatype="/^100(\.0+)?$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">单项得分</label>
					</td>
				</tr>
              </table>
              </div>
              <div title="突出防治">
              <table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

              <tr>
                  <td align="center">
                      <label class="Validform_label">
                          项目
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label">
                          项目内容
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label" style="min-width:150px;">
                          基本要求
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          标准分值
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label" style="min-width:150px;">
                          评分方法
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          是否缺项
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          得分
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          备注
                      </label>
                  </td>
              </tr>
              <tr>
                  <td  rowspan="4">
                      <label class="Validform_label">
                          五、<br>突<br>出<br>防<br>治<br>（100分）
                      </label>
                  </td>
                  <td  rowspan="3">
                      <label class="Validform_label">突出<br>管理</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.编制矿井、水平、采区及井巷揭穿突出煤层的防突专项设计，经企业技术负责人审批，并严格执行
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack5_1">
                          25
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。未编审设计不得分；执行不严格1处扣15分
                      </label>
                  </td>
                  <td class="value" >
                      <select name="lack5_1">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack5_1=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack5_1=='N'||scoreDetail.lack5_1==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score5_1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score5_1}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark5_1" rows="3" class="inputxt">${scoreDetail.remark5_1}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.区域预测结果、区域防突措施、保护效果检验、保护范围考察结果经企业技术负责人审批；预抽煤层瓦斯区域防突措施效果检验及区域验证结果经矿总工程师审批，按预测、检验结果，采取相应防突措施
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack5_2">
                          25
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场和资料。未审批不得分；执行不严格1处扣15分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack5_2">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack5_2=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack5_2=='N'||scoreDetail.lack5_2==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score5_2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score5_2}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark5_2" rows="3" class="inputxt">${scoreDetail.remark5_2}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          3.突出煤层采掘工作面编制防突专项设计及安全技术措施，经矿总工程师审批，实施中及时按现场实际作出补充修改，并严格执行
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack5_3">
                          25
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。未编审设计及措施或者未执行不得分；执行不严格1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack5_3">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack5_3=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack5_3=='N'||scoreDetail.lack5_3==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score5_3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score5_3}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark5_3" rows="3" class="inputxt">${scoreDetail.remark5_3}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td>
                      <label class="Validform_label">设备<br>设施</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          压风自救装置、自救器、防突风门、避难硐室等安全防护设备设施符合《防治煤与瓦斯突出规定》要求
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack5_4">
                          25
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。不符合要求1处扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack5_4">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack5_4=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack5_4=='N'||scoreDetail.lack5_4==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score5_4" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score5_4}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark5_4" rows="3" class="inputxt">${scoreDetail.remark5_4}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
			<tr>
				<td  style="text-align: center;" colspan="2">
					<label class="Validform_label">
						<font color="red">*</font>单项得分
					</label>
				</td>
				<td class="value" style="text-align: left;" colspan="6">
					<input id="ssaSumScore5" idx='5' name="ssaSumScore5" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore5}" datatype="/^100(\.0+)?$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">单项得分</label>
				</td>
			</tr>
              </table>
              </div>
              <div title="瓦斯抽采">
                  <table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

                      <tr>
                          <td align="center">
                              <label class="Validform_label">
                                  项目
                              </label>
                          </td>
                          <td align="center">
                              <label class="Validform_label">
                                  项目内容
                              </label>
                          </td>
                          <td align="center">
                              <label class="Validform_label" style="min-width:150px;">
                                  基本要求
                              </label>
                          </td>
                          <td>
                              <label class="Validform_label">
                                  标准分值
                              </label>
                          </td>
                          <td>
                              <label class="Validform_label" style="min-width:150px;">
                                  评分方法
                              </label>
                          </td>
                          <td>
                              <label class="Validform_label">
                                  是否缺项
                              </label>
                          </td>
                          <td>
                              <label class="Validform_label">
                                  得分
                              </label>
                          </td>
                          <td>
                              <label class="Validform_label">
                                  备注
                              </label>
                          </td>
                      </tr>
                      <tr>
                          <td  rowspan="7">
                              <label class="Validform_label">
                                  六、<br>瓦<br>斯<br>抽<br>采<br>（100分）
                              </label>
                          </td>
                          <td  rowspan="2">
                              <label class="Validform_label">抽采<br>系统</label>
                          </td>
                          <td class="td-padding">
                              <label class="Validform_label">
                                  1.瓦斯抽采设施、抽采泵站符合《煤矿安全规程》要求
                              </label>
                          </td>
                          <td >
                              <label class="Validform_label" name="lack6_1">
                                  15
                              </label>
                          </td>
                          <td class="td-padding">
                              <label class="Validform_label">
                                  查现场和资料。不符合要求1处扣5分
                              </label>
                          </td>
                          <td class="value" >
                              <select name="lack6_1">
                                  
                                  <option value="Y" <c:if test="${scoreDetail.lack6_1=='Y'}">selected="selected"</c:if>>是 </option>
                                  <option value="N" <c:if test="${scoreDetail.lack6_1=='N'||scoreDetail.lack6_1==null}">selected="selected"</c:if>>否 </option>
                              </select>
                          </td>

                          <td class="value" style="max-width:150px;">
                              <input  name="score6_1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score6_1}" >
                              <span class="Validform_checktip"></span>
                              <label class="Validform_label" style="display: none;">得分</label>
                          </td>
                          <td class="value">
                              <textarea name="remark6_1" rows="3" class="inputxt">${scoreDetail.remark6_1}</textarea>
                              <span class="Validform_checktip"></span>
                              <label class="Validform_label" style="display: none;">备注</label>
                          </td>
                      </tr>
                      <tr>
                          <td class="td-padding">
                              <label class="Validform_label">
                                  2.编制瓦斯抽采工程（包括钻场、钻孔、管路、抽采巷等）设计，并按设计施工
                              </label>
                          </td>
                          <td >
                              <label class="Validform_label" name="lack6_2">
                                  15
                              </label>
                          </td>
                          <td class="td-padding">
                              <label class="Validform_label">
                                  查现场和资料。不符合要求1处扣2分
                              </label>
                          </td>
                          <td class="value">
                              <select name="lack6_2">
                                  
                                  <option value="Y" <c:if test="${scoreDetail.lack6_2=='Y'}">selected="selected"</c:if>>是 </option>
                                  <option value="N" <c:if test="${scoreDetail.lack6_2=='N'||scoreDetail.lack6_2==null}">selected="selected"</c:if>>否 </option>
                              </select>
                          </td>

                          <td class="value" style="max-width:150px;">
                              <input  name="score6_2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score6_2}" >
                              <span class="Validform_checktip"></span>
                              <label class="Validform_label" style="display: none;">得分</label>
                          </td>
                          <td class="value">
                              <textarea name="remark6_2" rows="3" class="inputxt">${scoreDetail.remark6_2}</textarea>
                              <span class="Validform_checktip"></span>
                              <label class="Validform_label" style="display: none;">备注</label>
                          </td>
                      </tr>

                      <tr>
                          <td rowspan="5">
                              <label class="Validform_label">检查<br>与管<br>理</label>
                          </td>
                          <td class="td-padding">
                              <label class="Validform_label">
                                  1.对瓦斯抽采系统的瓦斯浓度、压力、流量等参数实时监测，定期人工检测比对，泵站每2h至少1次，主干、支管及抽采钻场每周至少1次，根据实际测定情况对抽采系统进行及时调节
                              </label>
                          </td>
                          <td >
                              <label class="Validform_label" name="lack6_3">
                                  15
                              </label>
                          </td>
                          <td class="td-padding">
                              <label class="Validform_label">
                                  查资料和现场。未按规定检测核实的1次扣5分，其他1处不符合要求扣2分
                              </label>
                          </td>
                          <td class="value">
                              <select name="lack6_3">
                                  
                                  <option value="Y" <c:if test="${scoreDetail.lack6_3=='Y'}">selected="selected"</c:if>>是 </option>
                                  <option value="N" <c:if test="${scoreDetail.lack6_3=='N'||scoreDetail.lack6_3==null}">selected="selected"</c:if>>否 </option>
                              </select>
                          </td>

                          <td class="value" style="max-width:150px;">
                              <input  name="score6_3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score6_3}" >
                              <span class="Validform_checktip"></span>
                              <label class="Validform_label" style="display: none;">得分</label>
                          </td>
                          <td class="value">
                              <textarea name="remark6_3" rows="3" class="inputxt">${scoreDetail.remark6_3}</textarea>
                              <span class="Validform_checktip"></span>
                              <label class="Validform_label" style="display: none;">备注</label>
                          </td>
                      </tr>
                      <tr>
                          <td class="td-padding">
                              <label class="Validform_label">
                                  2.井上下敷设的瓦斯管路，不得与带电物体接触并应当有防止砸坏管路的措施。每10天至少检查1次抽采管路系统，并有记录。抽采管路无破损、无漏气、无积水；抽采管路离地面高度不小于0.3m（采空区留管除外）
                              </label>
                          </td>
                          <td >
                              <label class="Validform_label" name="lack6_4">
                                  15
                              </label>
                          </td>
                          <td class="td-padding">
                              <label class="Validform_label">
                                  查资料和现场。管路损坏或者与带电物体接触不得分；其他1处不符合要求扣1分
                              </label>
                          </td>
                          <td class="value">
                              <select name="lack6_4">
                                  
                                  <option value="Y" <c:if test="${scoreDetail.lack6_4=='Y'}">selected="selected"</c:if>>是 </option>
                                  <option value="N" <c:if test="${scoreDetail.lack6_4=='N'||scoreDetail.lack6_4==null}">selected="selected"</c:if>>否 </option>
                              </select>
                          </td>

                          <td class="value" style="max-width:150px;">
                              <input  name="score6_4" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score6_4}" >
                              <span class="Validform_checktip"></span>
                              <label class="Validform_label" style="display: none;">得分</label>
                          </td>
                          <td class="value">
                              <textarea name="remark6_4" rows="3" class="inputxt">${scoreDetail.remark6_4}</textarea>
                              <span class="Validform_checktip"></span>
                              <label class="Validform_label" style="display: none;">备注</label>
                          </td>
                      </tr>
                      <tr>
                          <td class="td-padding">
                              <label class="Validform_label">
                                  3.抽采钻场及钻孔设置管理牌板，数据填写及时、准确，有记录和台账
                              </label>
                          </td>
                          <td >
                              <label class="Validform_label" name="lack6_5">
                                  15
                              </label>
                          </td>
                          <td class="td-padding">
                              <label class="Validform_label">
                                  查资料和现场。不符合要求1处扣0.5分
                              </label>
                          </td>
                          <td class="value">
                              <select name="lack6_5">
                                  
                                  <option value="Y" <c:if test="${scoreDetail.lack6_5=='Y'}">selected="selected"</c:if>>是 </option>
                                  <option value="N" <c:if test="${scoreDetail.lack6_5=='N'||scoreDetail.lack6_5==null}">selected="selected"</c:if>>否 </option>
                              </select>
                          </td>
                          <td class="value" style="max-width:150px;">
                              <input  name="score6_5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score6_5}" >
                              <span class="Validform_checktip"></span>
                              <label class="Validform_label" style="display: none;">得分</label>
                          </td>
                          <td class="value">
                              <textarea name="remark6_5" rows="3" class="inputxt">${scoreDetail.remark6_5}</textarea>
                              <span class="Validform_checktip"></span>
                              <label class="Validform_label" style="display: none;">备注</label>
                          </td>
                      </tr>
                      <tr>
                          <td class="td-padding">
                              <label class="Validform_label">
                                  4.高瓦斯、突出矿井计划开采的煤量不超出瓦斯抽采的达标煤量，生产准备及回采煤量和抽采达标煤量保持平衡
                              </label>
                          </td>
                          <td >
                              <label class="Validform_label" name="lack6_6">
                                  15
                              </label>
                          </td>
                          <td class="td-padding">
                              <label class="Validform_label">
                                  查资料。不符合要求不得分
                              </label>
                          </td>
                          <td class="value">
                              <select name="lack6_6">
                                  
                                  <option value="Y" <c:if test="${scoreDetail.lack6_6=='Y'}">selected="selected"</c:if>>是 </option>
                                  <option value="N" <c:if test="${scoreDetail.lack6_6=='N'||scoreDetail.lack6_6==null}">selected="selected"</c:if>>否 </option>
                              </select>
                          </td>

                          <td class="value" style="max-width:150px;">
                              <input  name="score6_6" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score6_6}" >
                              <span class="Validform_checktip"></span>
                              <label class="Validform_label" style="display: none;">得分</label>
                          </td>
                          <td class="value">
                              <textarea name="remark6_6" rows="3" class="inputxt">${scoreDetail.remark6_6}</textarea>
                              <span class="Validform_checktip"></span>
                              <label class="Validform_label" style="display: none;">备注</label>
                          </td>
                      </tr>
                      <tr>
                          <td class="td-padding">
                              <label class="Validform_label">
                                  5.矿井瓦斯抽采率符合《煤矿瓦斯抽采达标暂行规定》要求
                              </label>
                          </td>
                          <td >
                              <label class="Validform_label" name="lack6_7">
                                  10
                              </label>
                          </td>
                          <td class="td-padding">
                              <label class="Validform_label">
                                  查资料。不符合要求不得分
                              </label>
                          </td>
                          <td class="value">
                              <select name="lack6_7">
                                  
                                  <option value="Y" <c:if test="${scoreDetail.lack6_7=='Y'}">selected="selected"</c:if>>是 </option>
                                  <option value="N" <c:if test="${scoreDetail.lack6_7=='N'||scoreDetail.lack6_7==null}">selected="selected"</c:if>>否 </option>
                              </select>
                          </td>

                          <td class="value" style="max-width:150px;">
                              <input  name="score6_7" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score6_7}" >
                              <span class="Validform_checktip"></span>
                              <label class="Validform_label" style="display: none;">得分</label>
                          </td>
                          <td class="value">
                              <textarea name="remark6_7" rows="3" class="inputxt">${scoreDetail.remark6_7}</textarea>
                              <span class="Validform_checktip"></span>
                              <label class="Validform_label" style="display: none;">备注</label>
                          </td>
                      </tr>
					<tr>
						<td  style="text-align: center;" colspan="2">
							<label class="Validform_label">
								<font color="red">*</font>单项得分
							</label>
						</td>
						<td class="value" style="text-align: left;" colspan="6">
							<input id="ssaSumScore6" idx='6' name="ssaSumScore6" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore6}" datatype="/^100(\.0+)?$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">单项得分</label>
						</td>
					</tr>
                  </table>
              </div>
              <div title="安全监控">
              <table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

              <tr>
                  <td align="center">
                      <label class="Validform_label">
                          项目
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label">
                          项目内容
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label" style="min-width:150px;">
                          基本要求
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          标准分值
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label" style="min-width:150px;">
                          评分方法
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          是否缺项
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          得分
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          备注
                      </label>
                  </td>
              </tr>
              <tr>
                  <td  rowspan="8">
                      <label class="Validform_label">
                          七、<br>安<br>全<br>监<br>控<br>（100分）
                      </label>
                  </td>
                  <td  rowspan="4">
                      <label class="Validform_label">装备<br>设置</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.矿井安全监控系统具备“风电、甲烷电、故障”闭锁及手动控制断电闭锁功能和实时上传监控数据的功能；传感器、分站备用量不少于应配备数量的20%
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack7_1">
                          15
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。系统功能不全扣5分，其他1处不符合要求扣2分
                      </label>
                  </td>
                  <td class="value" >
                      <select name="lack7_1">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack7_1=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack7_1=='N'||scoreDetail.lack7_1==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score7_1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score7_1}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark7_1" rows="3" class="inputxt">${scoreDetail.remark7_1}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.安全监控设备的种类、数量、位置、报警浓度、断电浓度、复电浓度、电缆敷设等符合《煤矿安全规程》规定，设备性能、仪器精度符合要求，系统装备实行挂牌管理
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack7_2">
                          15
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。报警、断电、复电1处不符合要求扣5分；其他1处不符合要求扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack7_2">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack7_2=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack7_2=='N'||scoreDetail.lack7_2==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score7_2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score7_2}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark7_2" rows="3" class="inputxt">${scoreDetail.remark7_2}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          3.安全监控系统的主机双机热备，连续运行。当工作主机发生故障时，备用主机应在5min内自动投入工作。中心站设双回路供电，并配备不小于2h在线式不间断电源。中心站设备设有可靠的接地装置和防雷装置。站内设有录音电话
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack7_3">
                          15
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场或资料。不符合要求1处扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack7_3">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack7_3=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack7_3=='N'||scoreDetail.lack7_3==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score7_3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score7_3}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark7_3" rows="3" class="inputxt">${scoreDetail.remark7_3}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          4.分站、传感器等在井下连续使用6～12个月升井全面检修，井下监控设备的完好率为100%，监控设备的待修率不超过20%，并有检修记录
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack7_4">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料或现场。未按规定升井检修1次（台）扣3分，其他1处不符合要求扣1分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack7_4">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack7_4=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack7_4=='N'||scoreDetail.lack7_4==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score7_4" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score7_4}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark7_4" rows="3" class="inputxt">${scoreDetail.remark7_4}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="1">
                      <label class="Validform_label">检测<br>试验</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          安全监控设备每月至少调校、测试1次；采用载体催化元件的甲烷传感器每15天使用标准气样和空气样在设备设置地点至少调校1次，并有调校记录；甲烷电闭锁和风电闭锁功能每15天测试1次，其中，对可能造成局部通风机停电的，每半年测试1次，并有测试签字记录
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack7_5">
                          15
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。不符合要求1处扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack7_5">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack7_5=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack7_5=='N'||scoreDetail.lack7_5==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score7_5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score7_5}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark7_5" rows="3" class="inputxt">${scoreDetail.remark7_5}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="2">
                      <label class="Validform_label">监控<br>设备</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.安全监控设备中断运行或者出现异常情况，查明原因，采取措施及时处理，其间采用人工检测，并有记录
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack7_6">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack7_6">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack7_6=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack7_6=='N'||scoreDetail.lack7_6==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score7_6" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score7_6}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark7_6" rows="3" class="inputxt">${scoreDetail.remark7_6}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.安全监控系统显示和控制终端设置在矿调度室，24h有监控人员值班
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack7_7">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场和资料。1处不符合要求不得分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack7_7">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack7_7=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack7_7=='N'||scoreDetail.lack7_7==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score7_7" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score7_7}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark7_7" rows="3" class="inputxt">${scoreDetail.remark7_7}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="1">
                      <label class="Validform_label">资料<br>管理</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          有监控系统运行状态记录、运行日志，安全监控日报表经矿长、总工程师签字；建立监控系统数据库，系统数据有备份并保存2年以上
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack7_8">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。数据无备份或者数据库缺少数据扣5分，其他1处不符合要求扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack7_8">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack7_8=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack7_8=='N'||scoreDetail.lack7_8==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score7_8" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score7_8}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark7_8" rows="3" class="inputxt">${scoreDetail.remark7_8}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
			<tr>
				<td  style="text-align: center;" colspan="2">
					<label class="Validform_label">
						<font color="red">*</font>单项得分
					</label>
				</td>
				<td class="value" style="text-align: left;" colspan="6">
					<input id="ssaSumScore7" idx='7' name="ssaSumScore7" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore7}" datatype="/^100(\.0+)?$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">单项得分</label>
				</td>
			</tr>
              </table>
              </div>
              <div title="防灭火">
              <table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

              <tr>
                  <td align="center">
                      <label class="Validform_label">
                          项目
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label">
                          项目内容
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label" style="min-width:150px;">
                          基本要求
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          标准分值
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label" style="min-width:150px;">
                          评分方法
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          是否缺项
                      </label>
                  </td>
                  <td>
                  <label class="Validform_label">
                      得分
                  </label>
              </td>
                  <td>
                      <label class="Validform_label">
                          备注
                      </label>
                  </td>
              </tr>
              <tr>
                  <td  rowspan="10">
                      <label class="Validform_label">
                          八、<br>防<br>灭<br>火<br>（100分）
                      </label>
                  </td>
                  <td  rowspan="4">
                      <label class="Validform_label">防治<br>措施</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.按《煤矿安全规程》规定进行煤层的自燃倾向性鉴定，制定矿井防灭火措施，建立防灭火系统，并严格执行
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack8_1">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。未鉴定不得分，其他1处不符合要求扣5分
                      </label>
                  </td>
                  <td class="value" >
                      <select name="lack8_1">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack8_1=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack8_1=='N'||scoreDetail.lack8_1==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score8_1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score8_1}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark8_1" rows="3" class="inputxt">${scoreDetail.remark8_1}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.开采自燃、容易自燃煤层的采掘工作面作业规程有防止自然发火的技术措施，并严格执行
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack8_2">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。不符合要求1处扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack8_2">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack8_2=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack8_2=='N'||scoreDetail.lack8_2==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>

                  <td class="value" style="max-width:150px;">
                      <input  name="score8_2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score8_2}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark8_2" rows="3" class="inputxt">${scoreDetail.remark8_2}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          3.井下易燃物存放符合规定，进行电焊、气焊和喷灯焊接等作业符合《煤矿安全规程》规定，每次焊接制定安全措施，经矿长批准，并严格执行
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack8_3">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。不符合要求1处扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack8_3">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack8_3=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack8_3=='N'||scoreDetail.lack8_3==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score8_3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score8_3}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark8_3" rows="3" class="inputxt">${scoreDetail.remark8_3}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          4.每处火区建有火区管理卡片，绘制火区位置关系图；启封火区有计划和安全措施，并经企业技术负责人批准
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack8_4">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料。不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack8_4">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack8_4=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack8_4=='N'||scoreDetail.lack8_4==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score8_4" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score8_4}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark8_4" rows="3" class="inputxt">${scoreDetail.remark8_4}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="4">
                      <label class="Validform_label">设施<br>设备</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.按《煤矿安全规程》规定设置井上、下消防材料库，配足消防器材，且每季度至少检查1次
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack8_5">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。缺消防材料库不得分，其他1处不符合要求扣1分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack8_5">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack8_5=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack8_5=='N'||scoreDetail.lack8_5==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score8_5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score8_5}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark8_5" rows="3" class="inputxt">${scoreDetail.remark8_5}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.按《煤矿安全规程》规定井下爆炸物品库、机电设备硐室、检修硐室、材料库等地点的支护和风门、风窗采用不燃性材料，并配备有灭火器材，其种类、数量、规格及存放地点，均在灾害预防和处理计划中明确规定
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack8_6">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。不符合要求1处扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack8_6">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack8_6=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack8_6=='N'||scoreDetail.lack8_6==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score8_6" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score8_6}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark8_6" rows="3" class="inputxt">${scoreDetail.remark8_6}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          3.矿井设有地面消防水池和井下消防管路系统，每隔100m(在带式输送机的巷道中每隔50m)设置支管和阀门，并正常使用。地面消防水池保持不少于200m3的水量，每季度至少检查1次
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack8_7">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。无消防水池或者水量不足不得分；缺支管、阀门，1处扣2分；其他1处不符合要求扣0.5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack8_7">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack8_7=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack8_7=='N'||scoreDetail.lack8_7==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score8_7" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score8_7}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark8_7" rows="3" class="inputxt">${scoreDetail.remark8_7}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          4.开采容易自燃和自燃煤层，确定煤层自然发火标志气体及临界值，开展自然发火预测预报工作，建立监测系统；在开采设计中明确选定自然发火观测站或者观测点，每周进行1次观测分析。发现异常，立即采取措施处理
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack8_8">
                          15
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。无监测系统不得分，1处预测预报不符合要求扣5分，其他1处不符合要求，扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack8_8">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack8_8=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack8_8=='N'||scoreDetail.lack8_8==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score8_8" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score8_8}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark8_8" rows="3" class="inputxt">${scoreDetail.remark8_8}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="1">
                      <label class="Validform_label">控制<br>指标</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          无一氧化碳超限作业，采空区密闭内及其他地点无超过35℃的高温点（因地温和水温影响的除外）
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack8_9">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。有超限作业不得分；其他1处不符合要求扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack8_9">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack8_9=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack8_9=='N'||scoreDetail.lack8_9==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score8_9" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score8_9}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark8_9" rows="3" class="inputxt">${scoreDetail.remark8_9}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>

              <tr>
                  <td rowspan="1">
                      <label class="Validform_label">封闭<br>时限</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          及时封闭与采空区连通的巷道及各类废弃钻孔；采煤工作面回采结束后45天内进行永久性封闭
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack8_10">
                          5
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。1处不符合要求，扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack8_10">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack8_10=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack8_10=='N'||scoreDetail.lack8_10==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score8_10" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score8_10}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark8_10" rows="3" class="inputxt">${scoreDetail.remark8_10}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
			<tr>
				<td  style="text-align: center;" colspan="2">
					<label class="Validform_label">
						<font color="red">*</font>单项得分
					</label>
				</td>
				<td class="value" style="text-align: left;" colspan="6">
					<input id="ssaSumScore8" idx='8' name="ssaSumScore8" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore8}" datatype="/^100(\.0+)?$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">单项得分</label>
				</td>
			</tr>
              </table>
              </div>
              <div title="粉尘防治">
              <table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

              <tr>
                  <td align="center">
                      <label class="Validform_label">
                          项目
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label">
                          项目内容
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label" style="min-width:150px;">
                          基本要求
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          标准分值
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label" style="min-width:150px;">
                          评分方法
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          是否缺项
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          得分
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          备注
                      </label>
                  </td>
              </tr>
              <tr>
                  <td  rowspan="9">
                      <label class="Validform_label">
                          九、<br>粉<br>尘<br>防<br>治<br>（100分）
                      </label>
                  </td>
                  <td  rowspan="1">
                      <label class="Validform_label">鉴定<br>及措<br>施</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          按《煤矿安全规程》规定鉴定煤尘爆炸性；制定年度综合防尘、预防和隔绝煤尘爆炸措施，并组织实施
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack9_1">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。未鉴定或者无措施不得分；其他1处不符合要求扣2分
                      </label>
                  </td>
                  <td class="value" >
                      <select name="lack9_1">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack9_1=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack9_1=='N'||scoreDetail.lack9_1==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score9_1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score9_1}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark9_1" rows="3" class="inputxt">${scoreDetail.remark9_1}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="4">
                      <label class="Validform_label">设备<br>设施</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.按照AQ1020规定建立防尘供水系统；防尘管路吊挂平直，不漏水；管路三通阀门便于操作
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack9_2">
                          15
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。未建立系统不得分，缺管路1处扣5分，其他1处不符合要求扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack9_2">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack9_2=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack9_2=='N'||scoreDetail.lack9_2==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score9_2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score9_2}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark9_2" rows="3" class="inputxt">${scoreDetail.remark9_2}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.运煤（矸）转载点设有喷雾装置，采掘工作面回风巷至少设置2道风流净化水幕，净化水幕和其他地点的喷雾装置符合AQ1020规定
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack9_3">
                          15
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。缺装置1处扣5分；其他1处不符合要求扣1分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack9_3">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack9_3=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack9_3=='N'||scoreDetail.lack9_3==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score9_3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score9_3}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark9_3" rows="3" class="inputxt">${scoreDetail.remark9_3}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          3.按《煤矿安全规程》要求安设隔爆设施，且每周至少检查1次，隔爆设施安装的地点、数量、水量或者岩粉量及安装质量符合AQ1020规定
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack9_4">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。未设隔爆设施，1处扣5分；其他1处不符合要求扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack9_4">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack9_4=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack9_4=='N'||scoreDetail.lack9_4==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score9_4" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score9_4}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark9_4" rows="3" class="inputxt">${scoreDetail.remark9_4}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          4.采煤机、掘进机内外喷雾装置使用正常；液压支架和放顶煤工作面的放煤口安设喷雾装置，降柱、移架或者放煤时同步喷雾，喷雾压力符合《煤矿安全规程》要求；破碎机安装有防尘罩和喷雾装置或者除尘器
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack9_5">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。缺外喷雾装置或者喷雾效果不好1处扣5分；其他1处不符合要求扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack9_5">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack9_5=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack9_5=='N'||scoreDetail.lack9_5==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score9_5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score9_5}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark9_5" rows="3" class="inputxt">${scoreDetail.remark9_5}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="4">
                      <label class="Validform_label">防除<br>尘措<br>施</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.采用湿式钻孔或者孔口除尘措施，爆破使用水炮泥，爆破前后冲洗煤壁巷帮；炮掘工作面安设有移动喷雾装置，爆破时开启使用
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack9_6">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。未湿式钻孔或者无措施扣5分；其他1处不符合要求扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack9_6">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack9_6=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack9_6=='N'||scoreDetail.lack9_6==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score9_6" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score9_6}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark9_6" rows="3" class="inputxt">${scoreDetail.remark9_6}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.喷射混凝土时，采用潮喷或者湿喷工艺，并装设除尘装置。在回风侧100m范围内至少安设2道净化水幕
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack9_7">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack9_7">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack9_7=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack9_7=='N'||scoreDetail.lack9_7==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score9_7" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score9_7}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark9_7" rows="3" class="inputxt">${scoreDetail.remark9_7}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          3.采煤工作面按《煤矿安全规程》规定采取煤层注水措施，注水设计符合AQ1020规定
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack9_8">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。采煤工作面未注水1处扣5分；其他1处不符合要求扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack9_8">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack9_8=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack9_8=='N'||scoreDetail.lack9_8==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score9_8" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score9_8}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark9_8" rows="3" class="inputxt">${scoreDetail.remark9_8}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          4.定期冲洗巷道积尘或者撒布岩粉。主要大巷、主要进回风巷每月至少冲洗1次，其他巷道冲洗周期或者撒布岩粉由矿总工程师确定。巷道中无连续长5m、厚度超过2mm的煤尘堆积
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack9_9">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。煤尘堆积超限1处扣5分；其他1处不符合要求扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack9_9">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack9_9=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack9_9=='N'||scoreDetail.lack9_9==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score9_9" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score9_9}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark9_9" rows="3" class="inputxt">${scoreDetail.remark9_9}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
				<tr>
					<td  style="text-align: center;" colspan="2">
						<label class="Validform_label">
							<font color="red">*</font>单项得分
						</label>
					</td>
					<td class="value" style="text-align: left;" colspan="6">
						<input id="ssaSumScore9" idx='9' name="ssaSumScore9" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore9}" datatype="/^100(\.0+)?$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">单项得分</label>
					</td>
				</tr>
              </table>
              </div>
              <div title="井下爆破">
              <table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

              <tr>
                  <td align="center">
                      <label class="Validform_label">
                          项目
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label">
                          项目内容
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label" style="min-width:150px;">
                          基本要求
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          标准分值
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label" style="min-width:150px;">
                          评分方法
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          是否缺项
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          得分
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          备注
                      </label>
                  </td>
              </tr>
              <tr>
                  <td  rowspan="6">
                      <label class="Validform_label">
                          十、<br>井<br>下<br>爆<br>破<br>（100分）
                      </label>
                  </td>
                  <td  rowspan="2">
                      <label class="Validform_label">物品<br>管理</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.井下爆炸物品库、爆炸物品贮存及运输符合《煤矿安全规程》规定
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack10_1">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value" >
                      <select name="lack10_1">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack10_1=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack10_1=='N'||scoreDetail.lack10_1==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score10_1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score10_1}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark10_1" rows="3" class="inputxt">${scoreDetail.remark10_1}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.爆炸物品领退、电雷管编号制度健全，发放前电雷管进行导通试验
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack10_2">
                          20
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。未进行导通试验扣10分，缺1项制度扣5分
                      </label>
                  </td>
                  <td class="value" >
                      <select name="lack10_2">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack10_2=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack10_2=='N'||scoreDetail.lack10_2==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score10_2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score10_2}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark10_2" rows="3" class="inputxt">${scoreDetail.remark10_2}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="4">
                      <label class="Validform_label">爆破<br>管理</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.爆破作业执行“一炮三检”、“三人连锁”制度，采取停送电（突出煤层）、撤人、设岗警戒措施。特殊情况下的爆破作业，制定安全技术措施，经矿总工程师批准后执行
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack10_3">
                          20
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。1处不符合要求不得分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack10_3">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack10_3=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack10_3=='N'||scoreDetail.lack10_3==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score10_3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score10_3}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark10_3" rows="3" class="inputxt">${scoreDetail.remark10_3}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.编制爆破作业说明书，并严格执行。现场设置爆破图牌板
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack10_4">
                          15
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。无爆破说明书或者不执行不得分，其他1处不符合要求扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack10_4">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack10_4=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack10_4=='N'||scoreDetail.lack10_4==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score10_4" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score10_4}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark10_4" rows="3" class="inputxt">${scoreDetail.remark10_4}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          3.爆炸物品现场存放、引药制作符合《煤矿安全规程》规定
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack10_5">
                          15
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。不符合要求1处扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack10_5">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack10_5=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack10_5=='N'||scoreDetail.lack10_5==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score10_5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score10_5}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark10_5" rows="3" class="inputxt">${scoreDetail.remark10_5}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          4.残爆、拒爆处理符合《煤矿安全规程》规定
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack10_6">
                          20
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场和资料。不符合要求不得分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack10_6">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack10_6=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack10_6=='N'||scoreDetail.lack10_6==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score10_6" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score10_6}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark10_6" rows="3" class="inputxt">${scoreDetail.remark10_6}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
				<tr>
					<td  style="text-align: center;" colspan="2">
						<label class="Validform_label">
							<font color="red">*</font>单项得分
						</label>
					</td>
					<td class="value" style="text-align: left;" colspan="6">
						<input id="ssaSumScore10" idx='10' name="ssaSumScore10" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore10}" datatype="/^100(\.0+)$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">单项得分</label>
					</td>
				</tr>
              </table>
              </div>
              <div title="基础管理">
              <table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

              <tr>
                  <td align="center">
                      <label class="Validform_label">
                          项目
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label">
                          项目内容
                      </label>
                  </td>
                  <td align="center">
                      <label class="Validform_label" style="min-width:150px;">
                          基本要求
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          标准分值
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label" style="min-width:150px;">
                          评分方法
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          是否缺项
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          得分
                      </label>
                  </td>
                  <td>
                      <label class="Validform_label">
                          备注
                      </label>
                  </td>

              </tr>
              <tr>
                  <td  rowspan="8">
                      <label class="Validform_label">
                          十<br>一、<br>基<br>础<br>管<br>理<br>（100分）
                      </label>
                  </td>
                  <td  rowspan="1">
                      <label class="Validform_label">组织<br>保障</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          按规定设有负责通风管理、瓦斯管理、安全监控、防尘、防灭火、瓦斯抽采、防突和爆破管理等工作的管理机构
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack11_1">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料。未设置机构不得分，机构不完善扣5分
                      </label>
                  </td>
                  <td class="value" >
                      <select name="lack11_1">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack11_1=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack11_1=='N'||scoreDetail.lack11_1==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score11_1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score11_1}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark11_1" rows="3" class="inputxt">${scoreDetail.remark11_1}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td  rowspan="2">
                      <label class="Validform_label">工作<br>制度</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.有完善矿井通风、瓦斯防治、综合防尘、防灭火和安全监控等专业管理制度，各工种有岗位安全生产责任制和操作规程，并严格执行
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack11_2">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。缺制度或者操作规程不得分；其他1处不符合要求扣5分
                      </label>
                  </td>
                  <td class="value" >
                      <select name="lack11_2">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack11_2=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack11_2=='N'||scoreDetail.lack11_2==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score11_2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score11_2}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark11_2" rows="3" class="inputxt">${scoreDetail.remark11_2}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.制定瓦斯防治中长期规划和年度计划。矿每月至少召开1次通风工作例会，总结安排年、季、月通风工作，并有记录
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack11_3">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料。缺1项计划或者总结扣5分，其他1处不符合要求扣2分
                      </label>
                  </td>
                  <td class="value" >
                      <select name="lack11_3">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack11_3=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack11_3=='N'||scoreDetail.lack11_3==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score11_3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score11_3}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark11_3" rows="3" class="inputxt">${scoreDetail.remark11_3}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="1">
                      <label class="Validform_label">资料<br>管理</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          有通风系统图、分层通风系统图、通风网络图、通风系统立体示意图、瓦斯抽采系统图、安全监控系统图、防尘系统图、防灭火系统图等；有测风记录、通风值班记录、通风（反风）设施检查及维修记录、粉尘冲洗记录、防灭火检查记录；有密闭管理台账、煤层注水台账、瓦斯抽采台账等；安全监控及防突方面的记录、报表、账卡、测试检验报告等资料符合AQ1029及《防治煤与瓦斯突出规定》要求，并与现场实际相符
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack11_4">
                          20
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。图纸、记录、台账等资料缺1种扣2分，与现场实际不符1处扣5分；其他1处不符合要求扣0.5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack11_4">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack11_4=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack11_4=='N'||scoreDetail.lack11_4==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score11_4" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score11_4}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark11_4" rows="3" class="inputxt">${scoreDetail.remark11_4}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="1">
                      <label class="Validform_label">仪器<br>仪表</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          按检测需要配备检测仪器，每类仪器的备用量不小于应配备使用数量的20%，仪器的调校、维护及收发和送检工作有专门人员负责，按期进行调校、检验，确保仪器完好
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack11_5">
                          20
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。仪器数量不足或者无专门人员负责扣5分，其他不符要求1台次扣2分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack11_5">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack11_5=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack11_5=='N'||scoreDetail.lack11_5==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score11_5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score11_5}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark11_5" rows="3" class="inputxt">${scoreDetail.remark11_5}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td rowspan="3">
                      <label class="Validform_label">岗位<br>规范</label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          1.管理和技术人员掌握相关的岗位职责、管理制度、技术措施
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack11_6">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查资料和现场。不符合要求1处扣5分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack11_6">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack11_6=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack11_6=='N'||scoreDetail.lack11_6==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score11_6" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score11_6}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark11_6" rows="3" class="inputxt">${scoreDetail.remark11_6}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          2.现场作业人员严格执行本岗位安全生产责任制；掌握本岗位相应的操作规程和安全措施，操作规范；.无“三违”行为
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack11_7">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。发现“三违”不得分，不执行岗位责任制、不规范操作1人次扣3分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack11_7">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack11_7=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack11_7=='N'||scoreDetail.lack11_7==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score11_7" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score11_7}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark11_7" rows="3" class="inputxt">${scoreDetail.remark11_7}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
              <tr>
                  <td class="td-padding">
                      <label class="Validform_label">
                          3.作业前对作业范围内空气环境、设备运行状态及巷道支护和顶底板完好状况等实时观测，进行安全确认
                      </label>
                  </td>
                  <td >
                      <label class="Validform_label" name="lack11_8">
                          10
                      </label>
                  </td>
                  <td class="td-padding">
                      <label class="Validform_label">
                          查现场。1人次不确认扣3分，其他1处不符合要求扣1分
                      </label>
                  </td>
                  <td class="value">
                      <select name="lack11_8">
                          
                          <option value="Y" <c:if test="${scoreDetail.lack11_8=='Y'}">selected="selected"</c:if>>是 </option>
                          <option value="N" <c:if test="${scoreDetail.lack11_8=='N'||scoreDetail.lack11_8==null}">selected="selected"</c:if>>否 </option>
                      </select>
                  </td>
                  <td class="value" style="max-width:150px;">
                      <input  name="score11_8" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score11_8}" >
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">得分</label>
                  </td>
                  <td class="value">
                      <textarea name="remark11_8" rows="3" class="inputxt">${scoreDetail.remark11_8}</textarea>
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">备注</label>
                  </td>
              </tr>
			<tr>
				<td  style="text-align: center;" colspan="2">
					<label class="Validform_label">
						<font color="red">*</font>单项得分
					</label>
				</td>
				<td class="value" style="text-align: left;" colspan="6">
					<input id="ssaSumScore11" idx='11' name="ssaSumScore11" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore11}" datatype="/^100(\.0+)?$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">单项得分</label>
				</td>
			</tr>
              </table>
              </div>
              </div>

          <table style="width: 100%;"  cellpadding="0" cellspacing="1" class="formtable" align="center">

              <tr>
                  <td  style="text-align: center;" colspan="2">
                      <label class="Validform_label">
                          <font color="red">*</font>汇总得分
                      </label>
                  </td>
                  <td class="value" style="text-align: left;" colspan="6">
                      <input id="ssaSumScore" name="ssaSumScore" type="text" style="width: 150px" class="inputxt" value="${sfMineStandardAssessPage.ssaSumScore}" datatype="/^100(\.0+)?$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内数字!">
                      <span class="Validform_checktip"></span>
                      <label class="Validform_label" style="display: none;">汇总得分</label>
                  </td>
              </tr>
              <tr class="luru" height="40">
                  <td class="upload" colspan="8" style="text-align: right;" align="right">
                      <div class="ui_buttons" style="text-align: center;">
                          <c:if test="${sfMineStandardAssessPage.ssaCurrentStatus ne '2' }">
                              <input type="button" onclick='saveMineStandardAssess(1)' value="保存" class="ui_state_highlight">
                              <c:if test="${from ne 'mineAssess'}">
                                  <!--from:mineAssess 页面请求来源于矿井考核自评汇总，不显示提交和返回按钮，否则显示该按钮 -->
                                  <input type="button" onclick='saveMineStandardAssess(2)' value="提交" class="ui_state_highlight">
                                  <input type="button" onclick="backList();" value="返回">
                              </c:if>
                          </c:if>
                          <c:if test="${sfMineStandardAssessPage.ssaCurrentStatus eq '2' }">
                              <input type="button" onclick='saveMineStandardAssess(2)' value="保存" class="ui_state_highlight">
                              <%--<c:if test="${from ne 'mineAssess'}">--%>
                              <%--<!--from:mineAssess 页面请求来源于矿井考核自评汇总，不显示提交和返回按钮，否则显示该按钮 -->--%>
                              <%--<input type="button" onclick='saveMineStandardAssess(2)' value="提交" class="ui_state_highlight">--%>
                              <input type="button" onclick="backList();" value="返回">
                              <%--</c:if>--%>
                          </c:if>
                      </div>
                  </td>
              </tr>
          </table>
      </div>

		</t:formvalid>
  <script src = "webpage/com/sdzk/buss/web/standardassess/js/sfMineStandardAssess.js"></script>
  <script type="text/javascript">
     var reqFrom = "${from}";
     var load = "${load}";
	 var showFirstTab = true;
     if("detail" == load){
         $("form input,textarea,select").attr("disabled","true");
     }
     $(document).ready(function(){
        $("div[class='tabs-wrap']").css("width","auto");
        if(reqFrom == 'mineAssess' ){
            $("#formobj").find("input[class='inputxt']").attr("class","");
        }
        $("input[name^='score1_']").on("blur", function(){
            calculateSum("score1_","lack1_","ssaSumScore1");
            calculateSumLocal();
        });
        $("input[name^='score2_']").on("blur", function(){
            calculateSum("score2_","lack2_","ssaSumScore2");
            calculateSumLocal();
        });
        $("input[name^='score3_']").on("blur", function(){
            calculateSum("score3_","lack3_","ssaSumScore3");
            calculateSumLocal();
        });
        $("input[name^='score4_']").on("blur", function(){
            calculateSum("score4_","lack4_","ssaSumScore4");
            calculateSumLocal();
        });
        $("input[name^='score5_']").on("blur", function(){
            calculateSum("score5_","lack5_","ssaSumScore5");
            calculateSumLocal();
        });
        $("input[name^='score6_']").on("blur", function(){
            calculateSum("score6_","lack6_","ssaSumScore6");
            calculateSumLocal();
        });
        $("input[name^='score7_']").on("blur", function(){
            calculateSum("score7_","lack7_","ssaSumScore7");
            calculateSumLocal();
        });
        $("input[name^='score8_']").on("blur", function(){
            calculateSum("score8_","lack8_","ssaSumScore8");
            calculateSumLocal();
        });
        $("input[name^='score9_']").on("blur", function(){
            calculateSum("score9_","lack9_","ssaSumScore9");
            calculateSumLocal();
        });
        $("input[name^='score10_']").on("blur", function(){
            calculateSum("score10_","lack10_","ssaSumScore10");
            calculateSumLocal();
        });
        $("input[name^='score11_']").on("blur", function(){
            calculateSum("score11_","lack11_","ssaSumScore11");
            calculateSumLocal();
        });
		//不参与考核项checkbox点击事件
		$("input[name^='ignore']").on("click", function(){
			var title = $(this).attr("title");
			var tab_option = $('#tt').tabs('getTab',title).panel('options').tab;
			//1 隐藏所有div内容
			$(".tabs-panels>div").hide();
			//2 设置汇总得分为0
		    $("input[name='ssaSumScore']").val(0);
			//3 根据是否勾选checkbox显示或隐藏tab页
			if($(this).attr("checked")=='checked'){
				//3.1.1 取第一个未隐藏的tab,显示其内容
				tab_option.siblings().each(function(){
					if(!$(this).is(":hidden")){
						$(this).trigger("click");
						return false;
					}
				});
				//3.1.2 隐藏当前tab页
				tab_option.hide();
                //3.1.3 设置tab页的form field
                var index = $(this).attr("name").substring(6);
                $("input[name^='score"+index+"_']").val(0);
                $("input[name^='score"+index+"_']").removeAttr("readonly");
                $("input[name^='ssaSumScore"+index+"']").val(0);
                $("select[name^='lack"+index+"_']").val("N");
			} else {
				//3.2.1 显示当前tab页及其内容 
				tab_option.trigger("click");
				tab_option.show();
			}
			//4. 重新计算汇总得分
            calculateSumLocal();
		});
		//初始化隐藏不参与考核项tab
         if ($(".tabs").length > 0) {
             $("input[name^='ignore']").each(function () {
                 var title = $(this).attr("title");
                 var tab_option = $('#tt').tabs('getTab', title).panel('options').tab;
                 if ($(this).attr("checked") == 'checked') {
                     tab_option.hide();
                 } else {
                     if (showFirstTab) {
                         tab_option.trigger("click");
                         showFirstTab = false;
                     }
                 }
             });
         }
     });
	 
	 function calculateSumLocal(){
		 var totalSum = 100;
		 $("input[name^='ssaSumScore']").each(function(){
			 var idx = $(this).attr("idx");
			 var ignore = $("input[name='ignore"+idx+"']");
			 var name = $(this).attr("name");
			 if(name!='ssaSumScore' && ignore.attr("checked")!="checked"){
				var value = $(this).val();
				if(value != null && value != ''){
					if(totalSum>parseFloat(value)){
						totalSum = parseFloat(value);
					}
				} else {
					totalSum=0;
					return false;
				}
			 }
		 })
		 $("input[name='ssaSumScore']").val(totalSum.toFixed(2));
	 }
     function backList(){
         document.location="sfMineStandardAssessController.do?list&mineType=${mineType }&assessType=${assessType}";
     }

     function saveMineStandardAssess(state) {
         if(state == "1"){
             //保存
             $("#ssaCurrentStatus").val("1");
         }else{
             //提交
             $("#ssaCurrentStatus").val("2");
         }
         if(reqFrom == 'mineAssess'){
             $("#ssaCurrentStatus").val("2");
             var url ="sfMineStandardAssessController.do?doAdd";
             //提交表单
             $.ajax({
                 cache: true,
                 type: "POST",
                 url:url,
                 data:$('#formobj').serialize(),
                 async: false,
                 error: function(request) {
                     alert("Connection error");
                 },
                 success: function(data1) {
                     //刷新列表
                     mineAssessListsearch();

                     $("div[class='panel-header']").hide();
                     //刷新总得分数据
                     var masterId = "${masterId}";
                     $.ajax({
                         cache: true,
                         type: "POST",
                         url:"sfMineStandardAssessController.do?getSfMineStandardAssessScore",
                         data:{
                             id:masterId,
                             type:"${from}"
                         },
                         async: false,
                         error: function(request) {
                             alert("Connection error");
                         },
                         success: function(data) {
                             //刷新总得分数据
                             var d = $.parseJSON(data);
                             $("#ssasMonth").val(d.obj.ssasMonth);
                             $("#ssasSumScore").val(d.obj.ssasSumScore);
                         }
                     });
                 }
             });
         }else{
             $('#btn_sub').click();
         }
     }

     //保存回调
     function saveCallback(data) {
         if (data.success == true){
             if($("#ssaCurrentStatus").val()=="1"){//保存
                 parent.tip(data.msg);
                 document.location="sfMineStandardAssessController.do?goUpdate&mineType=${mineType}&assessType=${assessType}&id="+data.attributes.assessId;

             }else{//提交
                 document.location="sfMineStandardAssessController.do?list&mineType=${mineType}&assessType=${assessType}";
             }
         }else{
             parent.tip(data.msg);
         }
     }
 </script>
 </body>
