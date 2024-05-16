<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>运输</title>
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
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->露天煤矿->运输</div>--%>
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
        <td colspan='8' align="center" style="font-size:20px;font-weight: bold;">露天煤矿运输标准化评分</td>
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
<div id="tt" class="easyui-tabs" style="width: auro;" data-options="onSelect:function(t){$('#tt .panel-body').css('width','auto');}">

<div title="公路运输标准化评分">
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
    <td  rowspan="6">
        <label class="Validform_label">
            一、<br>运<br>输<br>道<br>路<br>规<br>格<br>及<br>参<br>数<br>（28分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">路面宽度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_1">
            10
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣2分
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
        <label class="Validform_label">路面坡度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_2">
            10
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣2分
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
    <td  rowspan="1">
        <label class="Validform_label">交叉路口</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            设视线角
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_3">
            2
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

    <td  rowspan="1">
        <label class="Validform_label">变坡点</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            道路凸凹变坡点设竖曲线
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_4">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣1分
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
        <label class="Validform_label">干道路拱、<br> 路肩</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_5">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣1分
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
        <label class="Validform_label">最小曲线<br>半径、超高<br>及加宽</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_6">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣1分
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
            二、<br>运<br>输<br>道<br>路<br>质<br>量<br>管<br>理<br>（32分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">道路平整度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.主干道路面起伏不超过设计的0.2m；<br>
            2.半干线或移动线路路面起伏不超过设计的0.3m
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_7">
            12
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
        <label class="Validform_label">道路排水</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            根据当地气象条件设置相应的排水系统
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_8">
            10
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣2分
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
        <label class="Validform_label">道路整洁度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            路面整洁，无散落物料
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_9">
            10
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
    <td  rowspan="4">
        <label class="Validform_label">
            三、<br>运<br>输<br>道<br>路<br>安<br>全<br>管<br>理<br>（16分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">安全挡墙</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            高度不低于矿用卡车轮胎直径的2/5
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_10">
            6
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
    <td  rowspan="1">
        <label class="Validform_label">洒水 降尘</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            洒水抑制扬尘，冬季采用雾状喷洒、间隔分段喷洒或其他措施
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_11">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。扬尘未得到有效控制1处扣1分
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
        <label class="Validform_label">道路封堵</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            废弃路段及时封堵
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_12">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。未封堵不得分
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
        <label class="Validform_label">车辆管理</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            进入矿坑的小型车辆配齐警示旗和警示灯
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_13">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1次扣1分
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
    <td  rowspan="3">
        <label class="Validform_label">
            四、<br>道<br>路<br>标<br>志<br>与<br>养<br>护<br>（14分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">反光标识</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            主要运输路段的转弯、交叉处有夜间能识别的反光标识
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
    <td  rowspan="1">
        <label class="Validform_label">道路养护</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            配备必需的养路设备，定期进行养护
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_15">
            6
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。道路养护不到位或设备配备不满足要求1处扣1分
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
        <label class="Validform_label">警示标志</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            根据具体情况设置警示标志
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_16">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
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
        <label class="Validform_label">
            五、<br>岗<br>位<br>规<br>范<br>（5分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">专业技能及<br>作业规范</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.建立并执行本岗位安全生产责任制； <br>
            2.掌握本岗位操作规程、作业规程；<br>
            3.按操作规程作业，无“三违”行为；<br>
            4.作业前进行安全确认
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_17">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。岗位安全生产责任制不全，每缺1个岗位扣3分,发现1人“三违” 不得分，其他1处不符合要求扣1分
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
            3.各种记录规范，页面整洁；<br>
            4.休息室、工具室卫生清洁，物品摆放整齐，插头、插座无裸露破损
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_18">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。1项不符合要求扣1分
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
<div title="矿铁路运输标准化评分">
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
    <td  rowspan="3">
        <label class="Validform_label">
            一、<br>技<br>术<br>管<br>理<br>（21分）
        </label>
    </td>
    <td  rowspan="2">
        <label class="Validform_label">技术规划</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.铁路运输系统重点工程有年度设计计划并按计划执行
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
    <td class="td-padding">
        <label class="Validform_label">
            2.有铁路运输系统平面图
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_2">
            7
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。无平面图不得分，与现场不符1处扣1分
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
        <label class="Validform_label">安全措施</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            遇临时或特殊工程，制定安全技术措施，并按程序进行审批
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_3">
            7
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求不得分
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
    <td  rowspan="20">
        <label class="Validform_label">
            二、<br>质<br>量<br>与<br>标<br>准<br>（54分）
        </label>
    </td>
    <td  rowspan="6">
        <label class="Validform_label">铁道线路</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_4">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。无设计不得分
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
    <td class="td-padding">
        <label class="Validform_label">
            2.损伤扣件及时补充、更换
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_5">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
    <td class="td-padding">
        <label class="Validform_label">
            3.目视直线直顺，曲线圆顺
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_6">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
    <td class="td-padding">
        <label class="Validform_label">
            4.空吊板不连续出现5块以上
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_7">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
    <td class="td-padding">
        <label class="Validform_label">
            5.无连续3处以上瞎缝
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_8">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
            6.重伤钢轨有标记、有措施
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_9">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
    <td  rowspan="6">
        <label class="Validform_label">架线</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.接触网高度符合《煤炭工业铁路技术管理规定》
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_10">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求不得分
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
    <td class="td-padding">
        <label class="Validform_label">
            2.各种标志齐全完整、明显清晰
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_11">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。1处不符合要求扣0.5分
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
    <td class="td-padding">
        <label class="Validform_label">
            3.各部线夹安装适当、排列整齐
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_12">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
    <td class="td-padding">
        <label class="Validform_label">
            4.角形避雷器间隙标准误差不超过1mm
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_13">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
    <td class="td-padding">
        <label class="Validform_label">
            5.木质电柱坠线有绝缘装置
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_14">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
            6.维修、检查记录翔实
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_15">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1项扣0.5分
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
    <td  rowspan="4">
        <label class="Validform_label">信号</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.机械室管理制度、检测记录齐全翔实
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_16">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1项扣0.5分
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
    <td class="td-padding">
        <label class="Validform_label">
            2.信号显示距离符合标准
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_17">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求不得分
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
            3.转辙装置各部螺丝紧固，绝缘件完好，道岔正常转换
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_18">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
    <td class="td-padding">
        <label class="Validform_label">
            4.信号外线路铺设符合《煤炭工业铁路技术管理规定》
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_19">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
    <td  rowspan="4">
        <label class="Validform_label">车站</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.有车站行车工作细则
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_20">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
    <td class="td-padding">
        <label class="Validform_label">
            2.日志、图表等填写规范
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_21">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1项扣0.5分
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
    <td class="td-padding">
        <label class="Validform_label">
            3.行车用语符合标准
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_22">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
    <td class="td-padding">
        <label class="Validform_label">
            4.安全设施齐全完好
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_23">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
    <td  rowspan="3">
        <label class="Validform_label">
            三、<br>设<br>备<br>管<br>理<br>（15分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">内业管理</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            有档案、台账，统计数字完整清晰
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_24">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1项扣0.5分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_24">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_24=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_24=='N'||scoreDetail.lack2_24==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_24" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_24}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_24" rows="3" class="inputxt">${scoreDetail.remark2_24}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">设备状态</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            设备技术状态标准、安全装置齐全、灵活可靠
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_25">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_25">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_25=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_25=='N'||scoreDetail.lack2_25==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_25" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_25}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_25" rows="3" class="inputxt">${scoreDetail.remark2_25}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">设备使用</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            定人操作、定期保养
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_26">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣0.5分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_26">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_26=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_26=='N'||scoreDetail.lack2_26==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_26" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_26}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_26" rows="3" class="inputxt">${scoreDetail.remark2_26}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">
            四、<br>岗<br>位<br>规<br>范<br>(5分)
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
        <label class="Validform_label" name="lack2_27">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。岗位安全生产责任制不全，每缺1个岗位扣3分,发现1人“三违” 不得分，其他1处不符合要求扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_27">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_27=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_27=='N'||scoreDetail.lack2_27==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_27" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_27}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_27" rows="3" class="inputxt">${scoreDetail.remark2_27}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">
            五、<br>文<br>明<br>生<br>产<br>(5分)
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">作业环境</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.驾驶室干净整洁，室内各设施保持完好； <br>
            2.各类物资摆放规整； <br>
            3.各种记录规范，页面整洁
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2_28">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。1项不符合要求扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack2_28">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_28=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_28=='N'||scoreDetail.lack2_28==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2_28" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2_28}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2_28" rows="3" class="inputxt">${scoreDetail.remark2_28}</textarea>
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
<div title="带式输送机/破碎站运输标准化评分">
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
    <td align="center" colspan="2">
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
            一、<br>技<br>术<br>管<br>理<br>（20分）
        </label>
    </td>
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">设计</label>
    </td>
    <td class="td-padding" colspan="2">
        <label class="Validform_label">
            符合设计并按设计作业
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_1">
            10
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求不得分
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
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">记录</label>
    </td>
    <td class="td-padding" colspan="2">
        <label class="Validform_label">
            设备运行、检修、维修和人员交接班记录翔实
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_2">
            10
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1项扣1分
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
    <td  rowspan="13">
        <label class="Validform_label">
            二、<br>作<br>业<br>管<br>理<br>（35分）
        </label>
    </td>
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">巡视</label>
    </td>
    <td class="td-padding" colspan="2">
        <label class="Validform_label">
            定时检查设备运行状况，记录齐全
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_3">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1项扣1分
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
    <td  rowspan="4">
        <label class="Validform_label">带式输送机</label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">机头机尾排水</label>
    </td>
    <td  class="td-padding" colspan="2">
        <label class="Validform_label">
            无积水
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_4">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分
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
        <label class="Validform_label">最大倾角</label>
    </td>
    <td  class="td-padding" colspan="2">
        <label class="Validform_label">
            符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_5">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求不得分
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
    <td  rowspan="1">
        <label class="Validform_label">分流站</label>
    </td>
    <td  class="td-padding" colspan="2">
        <label class="Validform_label">
            分流站伸缩头有集控调度指令方可操作，设备运转部位及其周围无人员和其他障碍物，不造成物料堆积洒落
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_6">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求不得分
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
    <td  rowspan="1">
        <label class="Validform_label">清料</label>
    </td>
    <td  class="td-padding" colspan="2">
        <label class="Validform_label">
            沿线清料及时，无洒物，不影响行车、检修作业；结构架上积料及时清理，不磨损托辊、输送带或滚筒
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_7">
            2
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
    <td  rowspan="8">
        <label class="Validform_label">破碎站</label>
    </td>
    <td  rowspan="5">
        <label class="Validform_label">半固定式破碎站</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            清料
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            破碎站工作平台及其下面没有洒料
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_8">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
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
    <td class="td-padding">
        <label class="Validform_label">
            卸料口挡车器
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            挡车器高度达到运煤汽车轮胎直径的2/5以上
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_9">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
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
    <td class="td-padding">
        <label class="Validform_label">
            减速机
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            各部减速机无渗、漏油现象
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_10">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和记录。不符合要求1处扣1分
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
    <td class="td-padding">
        <label class="Validform_label">
            板式给料机
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            链节、链板无变形，托轮无滞转
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_11">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和记录。不符合要求1处扣1分
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
    <td class="td-padding">
        <label class="Validform_label">
            润滑系统
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            管路、阀门、油泵运行可靠，无渗、漏油
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_12">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和记录。不符合要求1处扣1分
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
    <td  rowspan="3">
        <label class="Validform_label">自移式破碎机</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            液压系统
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            液压管路、俯仰调节液压缸等无渗漏，液压泵及液压马达运行平稳、无噪音，液压系统各部运行温度正常
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_13">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分。
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
    <td class="td-padding">
        <label class="Validform_label">
            板式给料机
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            承载托轮无滞转，链节无裂纹，刮板无变形翘曲
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_14">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣2分。
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
    <td class="td-padding">
        <label class="Validform_label">
            减速机
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            破碎辊、大小回转减速机、板式给料机驱动减速机、行走减速机、排料胶带等各种减速机无渗漏
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_15">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack3_15">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_15=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_15=='N'||scoreDetail.lack3_15==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3_15" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_15}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3_15" rows="3" class="inputxt">${scoreDetail.remark3_15}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="8">
        <label class="Validform_label">
            三、<br>安<br>全<br>管<br>理<br>（35分）
        </label>
    </td>
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">启动间隔</label>
    </td>
    <td class="td-padding" colspan="2">
        <label class="Validform_label">
            2次启动间隔时间不少于5min
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_16">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack3_16">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_16=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_16=='N'||scoreDetail.lack3_16==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3_16" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_16}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3_16" rows="3" class="inputxt">${scoreDetail.remark3_16}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">启动要求</label>
    </td>
    <td class="td-padding" colspan="2">
        <label class="Validform_label">
            设备准备运转前，司机检查设备并确认无危及设备和人身安全的情况，向集控调度汇报后方可启动设备
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_17">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack3_17">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_17=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_17=='N'||scoreDetail.lack3_17==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3_17" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_17}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3_17" rows="3" class="inputxt">${scoreDetail.remark3_17}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">消防设施</label>
    </td>
    <td class="td-padding" colspan="2">
        <label class="Validform_label">
            齐全有效，有检查记录
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_18">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack3_18">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_18=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_18=='N'||scoreDetail.lack3_18==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3_18" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_18}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3_18" rows="3" class="inputxt">${scoreDetail.remark3_18}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">带式输送机</label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">安全保护装<br>置</label>
    </td>
    <td class="td-padding" colspan="2">
        <label class="Validform_label">
            1.设置防止输送带跑偏、驱动滚筒打滑、纵向撕裂和溜槽堵塞等保护装置；上行带式输送机设置防止输送带逆转保护装置，下行带式输送机设置防止超速保护装置；<br>
            2.沿线设置紧急连锁停车装置；<br>
            3.在驱动、传动和自动拉紧装置的旋转部件周围设置防护装置
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_19">
            9
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack3_19">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_19=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_19=='N'||scoreDetail.lack3_19==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3_19" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_19}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3_19" rows="3" class="inputxt">${scoreDetail.remark3_19}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="2">
        <label class="Validform_label">半固定式破<br>碎站</label>
    </td>
    <td>
        <label class="Validform_label">除铁器</label>
    </td>
    <td class="td-padding" colspan="2">
        <label class="Validform_label">
            运行有效
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_20">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack3_20">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_20=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_20=='N'||scoreDetail.lack3_20==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3_20" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_20}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3_20" rows="3" class="inputxt">${scoreDetail.remark3_20}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">大块处理</label>
    </td>
    <td class="td-padding" colspan="2">
        <label class="Validform_label">
            处理料仓内的特大块物料时有安全技术措施。
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_21">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack3_21">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_21=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_21=='N'||scoreDetail.lack3_21==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3_21" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_21}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3_21" rows="3" class="inputxt">${scoreDetail.remark3_21}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="2">
        <label class="Validform_label">自移式破<br>碎机</label>
    </td>
    <td>
        <label class="Validform_label">与挖掘设<br>备距离</label>
    </td>
    <td class="td-padding" colspan="2">
        <label class="Validform_label">
            符合矿相关规定
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_22">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack3_22">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_22=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_22=='N'||scoreDetail.lack3_22==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3_22" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_22}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3_22" rows="3" class="inputxt">${scoreDetail.remark3_22}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">大块处理</label>
    </td>
    <td class="td-padding" colspan="2">
        <label class="Validform_label">
            处理料仓内的特大块物料时有安全技术措施。
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_23">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack3_23">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_23=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_23=='N'||scoreDetail.lack3_23==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3_23" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_23}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3_23" rows="3" class="inputxt">${scoreDetail.remark3_23}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">
            四、<br>岗<br>位<br>规<br>范<br>（5分）
        </label>
    </td>
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">专业技能<br>及规范作业</label>
    </td>
    <td class="td-padding" colspan="2">
        <label class="Validform_label">
            1.建立并执行本岗位安全生产责任制；<br>
            2.掌握本岗位操作规程、作业规程；<br>
            3.按操作规程作业，无“三违”行为；<br>
            4.作业前进行安全确认
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_24">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。岗位安全生产责任制不全，每缺1个岗位扣3分,发现1人“三违” 不得分，其他1处不符合要求扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack3_24">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_24=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_24=='N'||scoreDetail.lack3_24==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3_24" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_24}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3_24" rows="3" class="inputxt">${scoreDetail.remark3_24}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="1">
        <label class="Validform_label">
            五、<br>文<br>明<br>生<br>产<br>（5分）
        </label>
    </td>
    <td  rowspan="1" colspan="2">
        <label class="Validform_label">作业环境</label>
    </td>
    <td class="td-padding" colspan="2">
        <label class="Validform_label">
            1.驾驶室干净整洁，室内各设施保持完好；<br>
            2.各类物资摆放规整； <br>
            3.各种记录规范，页面整洁
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3_25">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。1项不符合要求扣1分
        </label>
    </td>
    <td class="value" >
        <select name="lack3_25">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_25=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_25=='N'||scoreDetail.lack3_25==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3_25" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3_25}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3_25" rows="3" class="inputxt">${scoreDetail.remark3_25}</textarea>
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
    <td class="value" style="text-align: left;" colspan="9">
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
        <td class="value" style="text-align: left;" colspan="8">
            <input id="ssaSumScore" name="ssaSumScore" type="text" style="width: 150px" class="inputxt" value="${sfMineStandardAssessPage.ssaSumScore}" datatype="/^100(\.0+)$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内数字!">
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">汇总得分</label>
        </td>
    </tr>
    <tr class="luru" height="40">
        <td class="upload" colspan="9" style="text-align: right;" align="right">
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
