<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>采装</title>
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

        /*Author: 张赛超*/
        /*QQ:128310398*/
        .inputxt{
            margin-top: 20px;
        }
    </style>
</head>
<body>
<%--<c:if test="${from ne 'mineAssess'}">--%>
    <%--<!-- from :mineAssess 页面请求来源于矿井考核自评汇总的时候不显示该面包屑，否则显示面包屑 -->--%>
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->露天煤矿->采装</div>--%>
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
        <td colspan='7' align="center" style="font-size:20px;font-weight: bold;">露天煤矿采装标准化评分</td>
    </tr>
    <tr>
        <td class="value" colspan="7" style="text-align: right">
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
<div id="tt" class="easyui-tabs" style="width: auto" data-options="onSelect:function(t){$('#tt .panel-body').css('width','auto');}">
<div title="单斗挖掘机采装标准化评分">
<table style="width: 95%;" cellpadding="0" cellspacing="1" class="formtable" >

<tr>
    <td>
        <label class="Validform_label">
            项目
        </label>
    </td>
    <td>
        <label class="Validform_label">
            项目内容
        </label>
    </td>
    <td>
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
    <td  rowspan="2">
        <label class="Validform_label">
            一、<br>技<br>术<br>管<br>理<br>（10分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">设计</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            有采矿设计并按设计作业，设计中有对安全和质量的要求
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_1">
            6
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求不得分
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

    <td  rowspan="1">
        <label class="Validform_label">规格参数</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合采矿设计、技术规范
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_2">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无测量验收资料不得分，不符合设计、规范1项扣1分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="4">
        <label class="Validform_label">
            二、<br>采<br>装<br>工<br>作<br>面<br>（27分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">台阶高度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合设计，不大于挖掘机最大挖掘高度
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_3">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。超过规定高度1处扣2分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">坡面角</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_4">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合设计要求1处扣2分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">平盘宽度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            采装最小工作平盘宽度，满足采装、运输、钻孔设备安全运行和供电通讯线路、供排水系统、安全挡墙等的正常布置
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_5">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。以100m为1个检查区，工作面平盘宽度小于设计1处扣2分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">作业面<br>整洁度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            及时清理，保持平整、干净
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_6">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="3">
        <label class="Validform_label">
            三、<br>采<br>装<br>平<br>盘<br>工<br>作<br>面<br>（22分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">帮面</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            齐整，在30m之内误差不超过2m
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_7">
            7
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">底面</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            平整，在30m之内，需爆破的岩石平盘误差不超过1.0m，其他平盘误差不超过0.5m
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_8">
            10
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">伞檐</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            工作面坡顶不出现0.5m及以上的伞檐
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_9">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="5">
        <label class="Validform_label">
            四、<br>采<br>装<br>设<br>备<br>操<br>作<br>管<br>理<br>（19分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">联合作业</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            当挖掘机、前装机、卡车、推土机联合作业时，制定联合作业措施，并有可靠的联络信号
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_10">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。无联合作业措施以及有效联络信号不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_10">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_10=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_10=='N'||scoreDetail.lack1_10==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_10" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_10}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_10" rows="3" class="inputxt">${scoreDetail.remark1_10}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">装车质量</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            以月末测量验收为准，装车统计量与验收量之间的误差在5%之内
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_11">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。误差超过5%不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_11">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_11=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_11=='N'||scoreDetail.lack1_11==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_11" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_11}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_11" rows="3" class="inputxt">${scoreDetail.remark1_11}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">装车标准</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            采装设备在装车时，不装偏车，不刮、撞、砸设备
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_12">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_12">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_12=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_12=='N'||scoreDetail.lack1_12==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_12" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_12}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_12" rows="3" class="inputxt">${scoreDetail.remark1_12}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">作业标准</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            挖掘机作业时，履带板不悬空作业。挖掘机扭转方向角满足设备技术要求，不强行扭角调方向
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_13">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_13">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_13=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_13=='N'||scoreDetail.lack1_13==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_13" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_13}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_13" rows="3" class="inputxt">${scoreDetail.remark1_13}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">工作面</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            采装工作面电缆摆放整齐，平盘无积水
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_14">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_14">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_14=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_14=='N'||scoreDetail.lack1_14==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_14" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_14}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_14" rows="3" class="inputxt">${scoreDetail.remark1_14}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="3">
        <label class="Validform_label">
            五、<br>采<br>装<br>安<br>全<br>管<br>理<br>(12分)
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">特殊条件<br>作业处理</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            在挖掘过程中发现台阶崩落或有滑动迹象，工作面有伞檐或大块物料，遇有未爆炸药包或雷管、有塌陷危险的采空区或自然发火区、有松软岩层可能造成挖掘机下沉，以及发现不明地下管线或其他不明障碍物等危险时，立即停止作业，撤到安全地点，并报告调度室
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_15">
            6
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_15">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_15=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_15=='N'||scoreDetail.lack1_15==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_15" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_15}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_15" rows="3" class="inputxt">${scoreDetail.remark1_15}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">坡度限制</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            挖掘机不在大于规定的坡度上作业
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_16">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_16">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_16=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_16=='N'||scoreDetail.lack1_16==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_16" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_16}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_16" rows="3" class="inputxt">${scoreDetail.remark1_16}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">采掘安全</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            挖掘机不能挖炮孔和安全挡墙
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_17">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_17">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_17=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_17=='N'||scoreDetail.lack1_17==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_17" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_17}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_17" rows="3" class="inputxt">${scoreDetail.remark1_17}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">
            六、<br>岗<br>位<br>规<br>范<br>(5分)
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">专业技能<br>及作业规范</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.建立并执行本岗位安全生产责任制； <br>2.掌握本岗位操作规程、作业规程； <br>3.按操作规程作业，无“三违”行为；<br> 4.作业前进行安全确认
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_18">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。岗位安全生产责任制不全，每缺1个岗位扣3分,发现1人“三违” 不得分，其他1处不符合要求扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_18">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_18=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_18=='N'||scoreDetail.lack1_18==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_18" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_18}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_18" rows="3" class="inputxt">${scoreDetail.remark1_18}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">
            七、<br>文<br>明<br>生<br>产<br>(5分)
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">作业环境</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.驾驶室干净整洁，室内各设施保持完好；<br> 2.各类物资摆放规整； <br>3.各种记录规范，页面整洁
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_19">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1项扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_19">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_19=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_19=='N'||scoreDetail.lack1_19==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_19" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_19}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_19" rows="3" class="inputxt">${scoreDetail.remark1_19}</textarea>
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
        <input id="ssaSumScore1" name="ssaSumScore1" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore1}" datatype="/^100(\.0+)$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">单项得分</label>
    </td>
</tr>
</table>
</div>
<div title="轮斗挖掘机采装标准化评分">
<table style="width: 95%;" cellpadding="0" cellspacing="1" class="formtable" >

<tr>
    <td>
        <label class="Validform_label">
            项目
        </label>
    </td>
    <td>
        <label class="Validform_label">
            项目内容
        </label>
    </td>
    <td>
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
    <td  rowspan="1">
        <label class="Validform_label">
            一、<br>技<br>术<br>管<br>理<br>（7分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">设计</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            有采矿设计并按设计作业，设计中有对安全和质量的要求
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_1">
            7
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求不得分
        </label>
    </td>
    <!----------------表2--------------->
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
    <td  rowspan="4">
        <label class="Validform_label">
            二、<br>采<br>装<br>工<br>作<br>面<br>（18分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">开采高度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合设计，不大于轮斗挖掘机最大挖掘高度
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_2">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">采掘带宽度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_3">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">侧坡面角</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_4">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">工作面</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.工作平盘标高与设计误差不超过0.5m；<br> 2.工作平盘宽度与设计误差不超过1.0m；<br> 3.台阶坡顶线平直度在30m内误差不超过1.0m；<br> 4.工作平盘平整度符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_5">
            6
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="2">
        <label class="Validform_label">
            三、<br>设<br>备<br>操<br>作<br>管<br>理<br>（35分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">联合作业</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            当轮斗挖掘机、胶带机、排土机联合作业时，制定联合作业措施，并有可靠的联络信号
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_6">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求不得分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">作业管理</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.工作面的开切方法、作业方式、切割方式、开采参数以及台阶组合形式按设计施工；<br>
            2.开机后注意地表，观察工作表面情况，地面要有专人指挥； <br>
            3.设备作业时，人员不进入作业区域和上下设备，在危及人身安全的作业范围内，人员和设备不能停留或者通过；<br>
            4.设备作业时，司机注意监视仪表显示及其它信号，并观察采掘工作面情况，发现异常及时采取措施；<br>
            5.斗轮工作装置不带负荷启动； <br>
            6.消防器材齐全有效
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_7">
            30
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。第3项和第5项不符合要求不得分，其他不符合要求1处扣3分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_7">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_7=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_7=='N'||scoreDetail.lack2_7==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_7" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_7}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_7" rows="3" class="inputxt">${scoreDetail.remark2_7}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="3">
        <label class="Validform_label">
            四、<br>安<br>全<br>管<br>理<br>（30分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">特殊作业</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.工作面松软或者有含水沉陷危险，采取安全措施，防止设备陷落；<br>
            2.风速达到20m/s时不开机；<br>
            3.长距离行走时，有专人指挥，斗轮体最下部距地表不小于3m，斗臂朝向行走方向； <br>
            4.作业时，如遇大石块，应采取措施
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_8">
            15
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合第1和2小项要求不得分，其他不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_8">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_8=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_8=='N'||scoreDetail.lack2_8==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_8" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_8}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_8" rows="3" class="inputxt">${scoreDetail.remark2_8}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">坡度限制</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            行走和作业时，工作面坡度符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_9">
            7
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_9">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_9=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_9=='N'||scoreDetail.lack2_9==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_9" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_9}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_9" rows="3" class="inputxt">${scoreDetail.remark2_9}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">作业环境</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            夜间作业有足够照明
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_10">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_10">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_10=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_10=='N'||scoreDetail.lack2_10==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_10" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_10}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_10" rows="3" class="inputxt">${scoreDetail.remark2_10}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">
            五、<br>岗<br>位<br>规<br>范<br>(5分)
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">专业技能及<br>作业规范</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.建立并执行本岗位安全生产责任制；<br>
            2.掌握本岗位操作规程、作业规程；<br>
            3.按操作规程作业，无“三违”行为； <br>
            4.作业前进行安全确认
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_11">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。岗位安全生产责任制不全，每缺1个岗位扣3分,发现1人“三违” 不得分，其他1处不符合要求扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_11">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_11=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_11=='N'||scoreDetail.lack2_11==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_11" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_11}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_11" rows="3" class="inputxt">${scoreDetail.remark2_11}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">
            六、<br>文<br>明<br>生<br>产<br>(5分)
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">作业环境</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.驾驶室干净整洁，室内各设施保持完好；<br> 2.各类物资摆放规整； <br>3.各种记录规范，页面整洁
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_12">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_12">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_12=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_12=='N'||scoreDetail.lack2_12==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_12" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_12}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_12" rows="3" class="inputxt">${scoreDetail.remark2_12}</textarea>
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
        <input id="ssaSumScore2" name="ssaSumScore2" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore2}" datatype="/^100(\.0+)$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">单项得分</label>
    </td>
</tr>
</table>
</div>
<div title="拉斗挖掘机采装标准化评分">
<table style="width: 95%;" cellpadding="0" cellspacing="1" class="formtable" >

<tr>
    <td>
        <label class="Validform_label">
            项目
        </label>
    </td>
    <td>
        <label class="Validform_label">
            项目内容
        </label>
    </td>
    <td>
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
    <td  rowspan="2">
        <label class="Validform_label">
            一、<br>技<br>术<br>管<br>理<br>（7分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">设计</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            有采矿设计并按设计作业，设计中有对安全和质量的要求
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_1">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求不得分
        </label>
    </td>
    <!------------------------表3------------------------>
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
    <td  rowspan="1">
        <label class="Validform_label">规格参数</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合采矿设计、技术规范
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_2">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无测量验收资料不得分，不符合设计、规范1处扣1分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="5">
        <label class="Validform_label">
            二、<br>采<br>装<br>工<br>作<br>面<br>（40分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">台阶高度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_3">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。超过高度1处扣2分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">坡面角</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合设计，坡面平整，坡顶无浮石、伞檐
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_4">
            10
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣4分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">工作面平<br>盘宽度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_5">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。以100m为1个检查区，工作面平盘宽度小于设计1处扣2分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="2">
        <label class="Validform_label">作业平盘</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.作业平盘密实度、平整度符合设计，作业时横向坡度不大于2%、纵向坡度不大于3%
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_6">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣3分
        </label>
    </td>
    <td class="value" >
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
    <td class="td-padding">
        <label class="Validform_label">
            2.及时清理，平整、干净，采装、维修、辅助设备安全运行，供排水系统等正常布置
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_7">
            6
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="3">
        <label class="Validform_label">
            三、<br>操<br>作<br>管<br>理<br>（25分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">联合作业</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            工程设备（如推土机、前装机）进入拉斗铲150m作业范围内作业时，做好呼唤应答，将铲斗置于安全位置，制动系统处于制动状态，拉斗铲停稳后，方可通知工程设备进入
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_8">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求不得分。
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">作业管理</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.作业时，行走靴外边缘距坡顶线的安全距离符合设计，底盘中心线与高台阶坡底线距离不小于35m，设备操作不过急倒逆；<br>
            2. 设备作业时，人员不能进入作业区域和上下设备，在危及人身安全的作业范围内，人员和设备不能停留或者通过； <br>
            3.回转时，铲斗不拖地回转，人员不上下设备，装有物料的铲斗不从未覆盖的电缆上方回转； <br>
            4.作业时，不急回转、急提升、急下放、急回拉、急刹车，不强行挖掘爆破后未解体的大块；<br>
            5.尾线摆放规范、电缆无破皮；<br>
            6.各部滑轮、偏心轮、辊子完好，各部润滑点润滑良好
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_9">
            15
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。1处不符合要求不得分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">工作面</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            采装工作面电缆摆放整齐，平盘无积水，扩展平台边缘无裂缝、下陷、滑落
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_10">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。1处不符合要求扣1分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="2">
        <label class="Validform_label">
            四、<br>安<br>全<br>管<br>理<br>（18分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">特殊作业</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.雨雪天地表湿滑、有积水时，处理后方可作业；<br>
            2.避雷装置完好有效，雷雨天时不作业，人员不上下设备； <br>
            3.遇大雾或扬沙天气时，做好呼唤应答，必要时停止作业
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_11">
            9
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。1处不符合要求不得分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">坡度限制</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            走铲时横向坡度不大于5%、纵向坡度不大于10%
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_12">
            9
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。1处不符合要求扣2分
        </label>
    </td>
    <td class="value" >
        <select name="lack3_12">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_12=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_12=='N'||scoreDetail.lack3_12==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3_12" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_12}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3_12" rows="3" class="inputxt">${scoreDetail.remark3_12}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">
            五、<br>岗<br>位<br>规<br>范<br>(5分)
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">专业技能及<br>作业规范</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.建立并执行本岗位安全生产责任制；<br>
            2.掌握本岗位操作规程、作业规程； <br>
            3.按操作规程作业，无“三违”行为； <br>
            4.作业前进行安全确认
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_13">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。岗位安全生产责任制不全，每缺1个岗位扣3分,发现1人“三违” 不得分，其他1处不符合要求扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack3_13">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_13=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_13=='N'||scoreDetail.lack3_13==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3_13" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_13}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3_13" rows="3" class="inputxt">${scoreDetail.remark3_13}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">
            六、<br>文<br>明<br>生<br>产<br>(5分)
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">作业环境</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.驾驶室干净整洁，室内各设施保持完好；<br>
            2.各类物资摆放规整； <br>
            3.各种记录规范，页面整洁； <br>
            4.驾驶室内部各种标示牌齐全完整
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_14">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1项扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack3_14">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_14=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_14=='N'||scoreDetail.lack3_14==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3_14" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_14}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3_14" rows="3" class="inputxt">${scoreDetail.remark3_14}</textarea>
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
        <input id="ssaSumScore3" name="ssaSumScore3" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore3}" datatype="/^100(\.0+)$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">单项得分</label>
    </td>
</tr>
</table>
</div>
</div>
<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable" align="center">

    <tr>
        <td  style="text-align: center;" colspan="2">
            <label class="Validform_label">
                <font color="red">*</font>汇总得分
            </label>
        </td>
        <td class="value" style="text-align: left;" colspan="6">
            <input id="ssaSumScore" name="ssaSumScore" type="text" style="width: 150px" class="inputxt" value="${sfMineStandardAssessPage.ssaSumScore}" datatype="/^100(\.0+)$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内数字!">
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
    if("detail" == load){
        $("form input,textarea,select").attr("disabled","true");
    }
    $(document).ready(function(){
        if(reqFrom == 'mineAssess'){
            $("#formobj").find("input[class='inputxt']").attr("class","");
        }
        $("#tt").find("div[class='tabs-wrap']").css("width","auto");
        $("input[name^='score1_']").on("blur", function(){
            calculateSum("score1_","lack1_","ssaSumScore1");
            calculateTotalSum();
        });
        $("input[name^='score2_']").on("blur", function(){
            calculateSum("score2_","lack2_","ssaSumScore2");
            calculateTotalSum();
        });
        $("input[name^='score3_']").on("blur", function(){
            calculateSum("score3_","lack3_","ssaSumScore3");
            calculateTotalSum();
        });
    });

    function backList(){
        document.location="sfMineStandardAssessController.do?list&mineType=${mineType }&assessType=${assessType}";
    }

    function saveMineStandardAssess(state) {

        /*  Author: 张赛超   */
        var totalScore = $("#ssaSumScore").val();
        var totalScore1 = $("#ssaSumScore1").val();
        var totalScore2 = $("#ssaSumScore2").val();
        var totalScore3 = $("#ssaSumScore3").val();
        if(totalScore == "" || totalScore == null){
            totalScore = 0;
            $("#ssaSumScore").attr("value",totalScore);
        }
        if(totalScore1 == "" || totalScore1 == null){
            totalScore1 = 0;
            $("#ssaSumScore1").attr("value",totalScore1);
        }
        if(totalScore2 == "" || totalScore2 == null){
            totalScore2 = 0;
            $("#ssaSumScore2").attr("value",totalScore2);
        }
        if(totalScore3 == "" || totalScore3 == null){
            totalScore3 = 0;
            $("#ssaSumScore3").attr("value",totalScore3);
        }
        /*  QQ:1228310398   */

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
