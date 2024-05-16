<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>排土</title>
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
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->露天煤矿->排土</div>--%>
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
        <td colspan='8' align="center" style="font-size:20px;font-weight: bold;">露天煤矿排土场标准化评分</td>
    </tr>
    <tr>
        <td class="value" colspan="8" style="text-align: right">
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
<div title="卡车/铁路排土场标准化评分">
<table style="width: 95%;" cellpadding="0" cellspacing="1" class="formtable" >

<tr>
    <td align="center">
        <label class="Validform_label">
            项目
        </label>
    </td>
    <td align="center" colspan="2">
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
    <td  rowspan="5">
        <label class="Validform_label">
            一、<br>技<br>术<br>管<br>理<br>（25分）
        </label>
    </td>
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">设计</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            有设计并按设计作业
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_1">
            5
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

    <td  rowspan="1" colspan="2">
        <label class="Validform_label">排土场控制</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            排弃后实测的各项技术数据符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_2">
            6
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1项扣2分
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
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">复垦、绿化</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            排弃到界的平盘按计划复垦、绿化
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_3">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
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

    <td  rowspan="1" colspan="2">
        <label class="Validform_label">安全距离</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            内排土场最下一个台阶的坡底线与坑底采掘工作面之间的安全距离不小于设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_4">
            6
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求不得分
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

    <td  rowspan="1" colspan="2">
        <label class="Validform_label">巡视</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            定期对排土场巡视，记录齐全
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_5">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无巡视记录不得分，不符合要求1处扣1分
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
    <td  rowspan="9">
        <label class="Validform_label">
            二、<br>排<br>土<br>工<br>作<br>面<br>规<br>格<br>参<br>数<br>管<br>理<br>（30分）
        </label>
    </td>
    <td  rowspan="2" colspan="2">
        <label class="Validform_label">台阶高度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_6">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
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
    <td class="td-padding">
        <label class="Validform_label">
            2.特殊区段高段排弃时，制定安全技术措施
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_7">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求不得分
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
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">工作面平整度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            作业平盘平整，50m范围内误差不超过0.5m
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_8">
            4
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
    <td  rowspan="3">
        <label class="Validform_label">卡车</label>
    </td>
    <td>
        <label class="Validform_label">排土线</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            排土线顶部边缘整齐，50m范围内误差不超过2m
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_9">
            3
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
    <td>
        <label class="Validform_label">反坡</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            排土工作面向坡顶线方向有3%～5%的反坡
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_10">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
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
    <td>
        <label class="Validform_label">安全挡墙</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            排土工作面卸载区有连续的安全挡墙，车型小于240t时安全挡墙高度不低于轮胎直径的0.4倍，车型大于240t时安全挡墙高度不低于轮胎直径的0.35倍。不同车型在同一地点排土时，按最大车型的要求修筑安全挡墙
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_11">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
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
    <td rowspan="3">
        <label class="Validform_label">铁路</label>
    </td>
    <td>
        <label class="Validform_label">标志完整</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            排土线常用的信号标志齐全，位置明显
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_12">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
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
    <td>
        <label class="Validform_label">排土宽度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            不小于22m，不大于24m
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_13">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
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
    <td>
        <label class="Validform_label">受土坑<br>安全距离</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            线路中心至受土坑坡顶距离不小于1.5m，雨季不小于1.9m
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_14">
            3
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
    <td  rowspan="10">
        <label class="Validform_label">
            三、<br>排<br>土<br>作<br>业<br>管<br>理<br>（25分）
        </label>
    </td>
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">照明</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            排土工作面夜间排弃时配有照明设备
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_15">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。无照明设备1处扣1分
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
    <td  rowspan="9">
        <label class="Validform_label">排土安全</label>
    </td>
    <td  class="td-padding" colspan="2">
        <label class="Validform_label">
            1.风氧化煤、煤矸石、粉煤灰按设计排弃
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_16">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。1处不符合要求扣1分
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
    <td  class="td-padding" colspan="2">
        <label class="Validform_label">
            2.当发现危险裂缝时立即停止作业，向调度室汇报，并制定安全措施
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_17">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求不得分
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
    <td  rowspan="4">
        <label class="Validform_label">卡车</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            （1）卡车排土和推土机作业时，设备之间保持足够的安全距离
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_18">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
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
    <td class="td-padding">
        <label class="Validform_label">
            （2）排土工作线至少保证2台卡车能同时排土作业
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_19">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
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
    <td class="td-padding">
        <label class="Validform_label">
            （3）排土时卡车垂直排土工作线，不能高速倒车冲撞安全挡墙
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_20">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。冲撞安全挡墙不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_20">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_20=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_20=='N'||scoreDetail.lack1_20==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_20" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_20}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_20" rows="3" class="inputxt">${scoreDetail.remark1_20}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            （4）推土机不平行于坡顶线方向推土
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_21">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。平行推土不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_21">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_21=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_21=='N'||scoreDetail.lack1_21==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_21" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_21}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_21" rows="3" class="inputxt">${scoreDetail.remark1_21}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="3">
        <label class="Validform_label">铁路</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            （1）列车进入排土线翻车房以里线路，由排土人员指挥列车运行
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_22">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_22">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_22=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_22=='N'||scoreDetail.lack1_22==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_22" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_22}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_22" rows="3" class="inputxt">${scoreDetail.remark1_22}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            （2）翻车时两人操作，执行复唱制度
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_23">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_23">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_23=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_23=='N'||scoreDetail.lack1_23==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_23" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_23}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_23" rows="3" class="inputxt">${scoreDetail.remark1_23}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            （3）工作面整洁，各种材料堆放整齐
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_24">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_24">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_24=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_24=='N'||scoreDetail.lack1_24==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_24" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_24}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_24" rows="3" class="inputxt">${scoreDetail.remark1_24}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="3">
        <label class="Validform_label">
            四、<br>安<br>全<br>管<br>理<br>（10分）
        </label>
    </td>
    <td  rowspan="2" colspan="2">
        <label class="Validform_label">安全挡墙</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1．上下平盘同时进行排土作业或下平盘有运输道路、联络道路时，在下平盘修筑安全挡墙
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_25">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_25">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_25=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_25=='N'||scoreDetail.lack1_25==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_25" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_25}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_25" rows="3" class="inputxt">${scoreDetail.remark1_25}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            2．最终边界的坡底沿征用土地的界线修筑1条安全挡墙
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_26">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。无挡墙不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_26">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_26=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_26=='N'||scoreDetail.lack1_26==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_26" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_26}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_26" rows="3" class="inputxt">${scoreDetail.remark1_26}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">到界平盘</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            最终边界到界前100m，采取措施提高边坡的稳定性
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_27">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。未采取措施不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_27">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_27=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_27=='N'||scoreDetail.lack1_27==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_27" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_27}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_27" rows="3" class="inputxt">${scoreDetail.remark1_27}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">
            五、<br>岗<br>位<br>规<br>范<br>（5分）
        </label>
    </td>
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">专业技能<br>及规范作业</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.建立并执行本岗位安全生产责任制；<br>
            2.掌握本岗位操作规程、作业规程； <br>
            3.按操作规程作业，无“三违”行为；<br>
            4.作业前进行安全确认
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_28">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。岗位安全生产责任制不全，每缺1个岗位扣3分,发现1人“三违” 不得分，其他1处不符合要求扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_28">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_28=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_28=='N'||scoreDetail.lack1_28==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_28" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_28}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_28" rows="3" class="inputxt">${scoreDetail.remark1_28}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">
            六、<br>文<br>明<br>生<br>产<br>（5分）
        </label>
    </td>
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">作业环境</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.驾驶室干净整洁，室内各设施保持完好；<br>
            2.各类物资摆放规整； <br>
            3.各种记录规范，页面整洁
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_29">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack1_29">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_29=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_29=='N'||scoreDetail.lack1_29==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_29" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1_29}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1_29" rows="3" class="inputxt">${scoreDetail.remark1_29}</textarea>
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
    <td class="value" style="text-align: left;" colspan="7">
        <input id="ssaSumScore1" name="ssaSumScore1" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore1}" datatype="/^100(\.0+)$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">单项得分</label>
    </td>
</tr>
</table>
</div>
<div title="排土机排土场标准化评分">
<table style="width: 95%;" cellpadding="0" cellspacing="1" class="formtable" >

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
            一、<br>技<br>术<br>管<br>理<br>（30分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">设计</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            有设计并按设计作业
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
    <td  rowspan="1">
        <label class="Validform_label">排土场控制</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            排弃后实测的各项技术数据符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_2">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1项扣1分
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
        <label class="Validform_label">复垦、绿化</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            排弃到界的平盘按计划复垦、绿化
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_3">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
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
        <label class="Validform_label">安全距离</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            排土场最下一个台阶的坡底线与征地界线之间的安全距离符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_4">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求不得分
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
        <label class="Validform_label">巡视</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            定期对排土场巡视，记录齐全
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_5">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无巡视记录不得分，不符合要求1处扣1分
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
    <td  rowspan="1">
        <label class="Validform_label">上排高度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_6">
            4
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
        <label class="Validform_label">下排高度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合设计，超高时制定安全措施
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_7">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合设计或无安全措施不得分
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
    <td  rowspan="5">
        <label class="Validform_label">
            二、<br>工<br>作<br>面<br>规<br>格<br>参<br>数<br>（25分）
        </label>
    </td>
    <td  rowspan="2">
        <label class="Validform_label">台阶高度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_8">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合设计不得分
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
    <td class="td-padding">
        <label class="Validform_label">
            2.特殊区段高段排弃时，制定安全技术措施
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_9">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求不得分
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
        <label class="Validform_label">排土线</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            沿上排坡底线、下排坡顶线方向30m内误差不超过1m
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_10">
            5
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
        <label class="Validform_label">工作面平整度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            排土机工作面平顺，在30m内误差不超过0.3m
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_11">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
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
        <label class="Validform_label">安全挡墙</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            排土工作面到界结束后，距离检修道路近的地段在下排坡顶设有连续的安全挡墙
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_12">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
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
    <td  rowspan="6">
        <label class="Validform_label">
            三、<br>排<br>土<br>作<br>业<br>管<br>理<br>（20分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">联合作业</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            推土机及时对排弃工作面进行平整，不在坡顶线平行推土
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_13">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。平行推土不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_13">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_13=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_13=='N'||scoreDetail.lack2_13==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_13" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_13}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_13" rows="3" class="inputxt">${scoreDetail.remark2_13}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="2">
        <label class="Validform_label">排土安全</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.推土机对出现的沉降裂缝及时碾压补料
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_14">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_14">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_14=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_14=='N'||scoreDetail.lack2_14==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_14" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_14}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_14" rows="3" class="inputxt">${scoreDetail.remark2_14}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            2.排土时排土机距离下排坡顶的安全距离符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_15">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_15">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_15=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_15=='N'||scoreDetail.lack2_15==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_15" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_15}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_15" rows="3" class="inputxt">${scoreDetail.remark2_15}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">照明</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            排土工作面夜间排弃时配有照明设备
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_16">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_16">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_16=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_16=='N'||scoreDetail.lack2_16==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_16" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_16}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_16" rows="3" class="inputxt">${scoreDetail.remark2_16}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="2">
        <label class="Validform_label">气候影响</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.雨季重点观察排土场有无滑坡迹象，有问题及时向有关部门汇报
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_17">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_17">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_17=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_17=='N'||scoreDetail.lack2_17==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_17" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_17}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_17" rows="3" class="inputxt">${scoreDetail.remark2_17}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            2.雨天持续时间较长、雨量较大时，排土机停止作业，停放在安全地带
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_18">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_18">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_18=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_18=='N'||scoreDetail.lack2_18==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_18" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_18}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_18" rows="3" class="inputxt">${scoreDetail.remark2_18}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="3">
        <label class="Validform_label">
            四、<br>安<br>全<br>管<br>理<br>(15分)
        </label>
    </td>
    <td  rowspan="2">
        <label class="Validform_label">安全挡墙</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.上下平盘同时进行排土作业或下平盘有运输道路、联络道路时，下平盘有安全挡墙
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_19">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_19">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_19=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_19=='N'||scoreDetail.lack2_19==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_19" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_19}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_19" rows="3" class="inputxt">${scoreDetail.remark2_19}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            2.最终边界的坡底沿征用土地的界线修筑1条安全挡墙
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_20">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。无挡墙不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_20">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_20=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_20=='N'||scoreDetail.lack2_20==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_20" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_20}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_20" rows="3" class="inputxt">${scoreDetail.remark2_20}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">到界平盘</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            最终边界到界前100m，采取措施，提高边坡的稳定性
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_21">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。未采取措施不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_21">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_21=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_21=='N'||scoreDetail.lack2_21==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_21" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_21}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_21" rows="3" class="inputxt">${scoreDetail.remark2_21}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">
            五、<br>岗<br>位<br>规<br>范<br>（5分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">专业技能<br>及作业规范</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.建立并执行本岗位安全生产责任制；<br>
            2.掌握本岗位操作规程、作业规程； <br>
            3.按操作规程作业，无“三违”行为；<br>
            4.作业前进行安全确认
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_22">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。岗位安全生产责任制不全，每缺1个岗位扣3分,发现1人“三违” 不得分，其他1处不符合要求扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_22">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_22=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_22=='N'||scoreDetail.lack2_22==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_22" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_22}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_22" rows="3" class="inputxt">${scoreDetail.remark2_22}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">
            六、<br>文<br>明<br>生<br>产<br>（5分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">作业环境</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.驾驶室干净整洁，室内各设施保持完好；<br>
            2.各类物资摆放规整；<br>
            3.各种记录规范，页面整洁
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_23">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。1项不符合要求扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_23">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_23=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_23=='N'||scoreDetail.lack2_23==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_23" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_23}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_23" rows="3" class="inputxt">${scoreDetail.remark2_23}</textarea>
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
