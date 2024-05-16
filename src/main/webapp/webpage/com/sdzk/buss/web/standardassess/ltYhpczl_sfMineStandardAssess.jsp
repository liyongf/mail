<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>安全生产标准化</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
    <script type="text/javascript">
        //编写自定义JS代码
        $(function() {
        });
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
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->井工煤矿->隐患排查治理</div>--%>
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
<div style="width: 95%; height: 100%;">
<div style="width: 100%; height: 1px;"></div>
<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable" align="center">
    <tr style="height: 40px;">
        <td colspan='8' align="center" style="font-size:20px;font-weight: bold;">煤矿事故隐患排查治理标准化评分表</td>
    </tr>
    <tr>
        <td class="value" colspan="8" style="text-align: right">
            <label class="Validform_label">
                <font color="red">*</font>考核月份:
            </label>
            <input id="ssaMonth" name="ssaMonth" type="text" style="width: 150px"
                   class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM'})" datatype="*" value='<fmt:formatDate value='${sfMineStandardAssessPage.ssaMonthDate}' type="date" pattern="yyyy-MM"/>'>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">考核月份</label>
        </td>
    </tr>
</table>

<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable" align="center">
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
    <td rowspan="3">
        <label class="Validform_label">
        一、<br/>工<br>作<br>机<br>制<br>（10分）
        </label>
    </td>
    <td rowspan="2">
        <label class="Validform_label">职责<br/>分工</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1．有负责事故隐患排查治理管理工作的部门
        </label>
    </td>
    <td><label class="Validform_label" name="lack1">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。无管理部门不得分
        </label>
    </td>
    <td class="value">
        <select name="lack1">
            
            <option value="Y" <c:if test="${scoreDetail.lack1=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1=='N' || scoreDetail.lack1==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark1" rows="3" class="inputxt">${scoreDetail.remark1}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
    
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        2．建立事故隐患排查治理工作责任体系，明确矿长全
        面负责、分管负责人负责分管范围内的事故隐患排查治
        理工作，各业务科室、生产组织单位（区队）、班组、
        岗位人员职责明确
        </label>
    </td>
    <td><label class="Validform_label" name="lack2">4</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。责任未
        分工或不明确不得分，
        矿领导不清楚职责 1
        人扣 2 分、部门负责人
        不清楚职责 1 人扣 0.5
        分
        </label>
    </td>
    <td class="value">
        <select name="lack2">
            
            <option value="Y" <c:if test="${scoreDetail.lack2=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2=='N' || scoreDetail.lack2==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark2" rows="3" class="inputxt">${scoreDetail.remark2}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td >
        <label class="Validform_label">分级<br/>管理</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        对排查出的事故隐患进行分级，并按照事故隐患等级明
        确相应层级的单位（部门）、人员负责治理、督办、验
        收
        </label>
    </td>
    <td><label class="Validform_label" name="lack3">4</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。未对事
        故隐患进行分级扣 2
        分，责任单位和人员不
        明确 1 项扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack3">
            
            <option value="Y" <c:if test="${scoreDetail.lack3=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3=='N' || scoreDetail.lack3==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark3" rows="3" class="inputxt">${scoreDetail.remark3}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="7">
        <label class="Validform_label">二、<br/>事<br/>故<br/>隐<br/>患<br/>排<br/>查<br/>（30分）</label>
    </td>
    <td >
        <label class="Validform_label">基础<br/>工作</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        编制事故隐患年度排查计划，并严格落实执行
        </label>
    </td>
    <td><label class="Validform_label" name="lack4">4</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。未编制
        排查计划或未落实执
        行不得分
        </label>
    </td>
    <td class="value">
        <select name="lack4">
            
            <option value="Y" <c:if test="${scoreDetail.lack4=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack4=='N' || scoreDetail.lack4==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score4" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark4" rows="3" class="inputxt">${scoreDetail.remark4}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>

    <td rowspan="4">
        <label class="Validform_label">周期<br/>范围</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1．矿长每月至少组织分管负责人及安全、生产、技术
        等业务科室、生产组织单位（区队）开展一次覆盖生产
        各系统和各岗位的事故隐患排查，排查前制定工作方
        案，明确排查时间、方式、范围、内容和参加人员
        </label>
    </td>
    <td><label class="Validform_label" name="lack5">6</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。未组织
        排查不得分，组织人
        员、范围、频次不符合
        要求 1 项扣 2 分，未制
        定工作方案扣 1 分、方
        案内容缺1项扣0.5分
        </label>
    </td>
    <td class="value">
        <select name="lack5">
            
            <option value="Y" <c:if test="${scoreDetail.lack5=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack5=='N' || scoreDetail.lack5==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score5}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark5" rows="3" class="inputxt">${scoreDetail.remark5}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        2.矿各分管负责人每旬组织相关人员对分管领域进行 1
        次全面的事故隐患排查
        </label>
    </td>
    <td><label class="Validform_label" name="lack6">6</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。分管负
        责人未组织排查不得
        分，组织人员、范围、
        频次不符合要求 1 项
        扣 2 分
        </label>
    </td>
    <td class="value">
        <select name="lack6">
            
            <option value="Y" <c:if test="${scoreDetail.lack6=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack6=='N' || scoreDetail.lack6==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score6" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score6}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark6" rows="3" class="inputxt">${scoreDetail.remark6}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        3．生产期间，每天安排管理、技术和安检人员进行巡
        查，对作业区域开展事故隐患排查
        </label>
    </td>
    <td><label class="Validform_label" name="lack7">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。未安排
        不得分，人员、范围、
        频次不符合要求 1 项
        扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack7">
            
            <option value="Y" <c:if test="${scoreDetail.lack7=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack7=='N' || scoreDetail.lack7==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score7" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score7}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark7" rows="3" class="inputxt">${scoreDetail.remark7}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        4．岗位作业人员作业过程中随时排查事故隐患
        </label>
    </td>
    <td><label class="Validform_label" name="lack8">4</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。未进行
        排查不得分
        </label>
    </td>
    <td class="value">
        <select name="lack8">
            
            <option value="Y" <c:if test="${scoreDetail.lack8=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack8=='N' || scoreDetail.lack8==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score8" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score8}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark8" rows="3" class="inputxt">${scoreDetail.remark8}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="2">
        <label class="Validform_label">登记<br/>上报</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1．建立事故隐患排查台账，逐项登记排查出的事故隐患
        </label>
    </td>
    <td><label class="Validform_label" name="lack9">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。未建立台账不
        得分，登记不全缺 1
        项扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack9">
            
            <option value="Y" <c:if test="${scoreDetail.lack9=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack9=='N' || scoreDetail.lack9==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score9" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score9}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark9" rows="3" class="inputxt">${scoreDetail.remark9}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        2．排查发现重大事故隐患后，及时向当地煤矿安全监
        管监察部门书面报告，并建立重大事故隐患信息档案
        </label>
    </td>
    <td><label class="Validform_label" name="lack10">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。不符合要求不
        得分
        </label>
    </td>
    <td class="value">
        <select name="lack10">
            
            <option value="Y" <c:if test="${scoreDetail.lack10=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack10=='N' || scoreDetail.lack10==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score10" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score10}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark10" rows="3" class="inputxt">${scoreDetail.remark10}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="6">
        <label class="Validform_label">三、<br/>事<br/>故<br/>隐<br/>患<br/>治<br/>理<br/>（25分）</label>
    </td>
    <td rowspan="4">
        <label class="Validform_label">分级<br/>治理</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1．事故隐患治理符合责任、措施、资金、时限、预案“五落实”要求
        </label>
    </td>
    <td><label class="Validform_label" name="lack11">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。不符合
        要求不得分
        </label>
    </td>
    <td class="value">
        <select name="lack11">
            
            <option value="Y" <c:if test="${scoreDetail.lack11=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack11=='N' || scoreDetail.lack11==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score11" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score11}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark11" rows="3" class="inputxt">${scoreDetail.remark11}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        2．重大事故隐患由矿长组织制定专项治理方案，并组织实施；治理方案按规定及时上报
        </label>
    </td>
    <td><label class="Validform_label" name="lack12">6</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。组织者
        不符合要求或未按方
        案组织实施不得分，治
        理方案未及时上报扣
        2 分
        </label>
    </td>
    <td class="value">
        <select name="lack12">
            
            <option value="Y" <c:if test="${scoreDetail.lack12=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack12=='N' || scoreDetail.lack12==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score12" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score12}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark12" rows="3" class="inputxt">${scoreDetail.remark12}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        3．不能立即治理完成的事故隐患，由治理责任单位（部
        门）主要责任人按照治理方案组织实施
        </label>
    </td>
    <td><label class="Validform_label" name="lack13">4</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。组织者
        不符合要求或未按方
        案组织实施不得分
        </label>
    </td>
    <td class="value">
        <select name="lack13">
            
            <option value="Y" <c:if test="${scoreDetail.lack13=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack13=='N' || scoreDetail.lack13==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score13" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score13}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark13" rows="3" class="inputxt">${scoreDetail.remark13}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        4．能够立即治理完成的事故隐患，当班采取措施，及
        时治理消除，并做好记录
        </label>
    </td>
    <td><label class="Validform_label" name="lack14">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。当班未
        采取措施或未及时治
        理不得分，不做记录扣
        3 分，记录不全 1 处扣
        0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack14">
            
            <option value="Y" <c:if test="${scoreDetail.lack14=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack14=='N' || scoreDetail.lack14==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score14" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score14}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark14" rows="3" class="inputxt">${scoreDetail.remark14}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="2">
        <label class="Validform_label">安全<br/>措施</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1．事故隐患治理有安全技术措施，并落实到位
        </label>
    </td>
    <td><label class="Validform_label" name="lack15">4</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。没有措
        施、措施不落实不得分
        </label>
    </td>
    <td class="value">
        <select name="lack15">
            
            <option value="Y" <c:if test="${scoreDetail.lack15=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack15=='N' || scoreDetail.lack15==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score15" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score15}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark15" rows="3" class="inputxt">${scoreDetail.remark15}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        2．对治理过程危险性较大的事故隐患，治理过程中现
        场有专人指挥，并设置警示标识；安检员现场监督
        </label>
    </td>
    <td><label class="Validform_label" name="lack16">4</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。现场没
        有专人指挥不得分、未
        设置警示标识扣 1 分、
        没有安检员监督扣 1
        分
        </label>
    </td>
    <td class="value">
        <select name="lack16">
            
            <option value="Y" <c:if test="${scoreDetail.lack16=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack16=='N' || scoreDetail.lack16==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score16" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score16}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark16" rows="3" class="inputxt">${scoreDetail.remark16}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="4">
        <label class="Validform_label">四、<br/>监<br/>督<br/>管<br/>理<br/>(20分)</label>
    </td>
    <td >
        <label class="Validform_label">治理<br/>督办</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1．事故隐患治理督办的责任单位（部门）和责任人员
        明确；<br/>
        2．对未按规定完成治理的事故隐患，由上一层级单位
        （部门）和人员实施督办；<br/>
        3．挂牌督办的重大事故隐患，治理责任单位（部门）
        及时记录治理情况和工作进展，并按规定上报
        </label>
    </td>
    <td><label class="Validform_label" name="lack17">7</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。督办责任不明
        确、不落实 1 次扣 2
        分，未实行提级督办 1
        次扣 2 分，未及时记录
        或上报 1 次扣 2 分
        </label>
    </td>
    <td class="value">
        <select name="lack17">
            
            <option value="Y" <c:if test="${scoreDetail.lack17=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack17=='N' || scoreDetail.lack17==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score17" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score17}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark17" rows="3" class="inputxt">${scoreDetail.remark17}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="2">
        <label class="Validform_label">验收<br/>销号</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1．煤矿自行排查发现的事故隐患完成治理后，由验收
        责任单位（部门）负责验收，验收合格后予以销号
        </label>
    </td>
    <td><label class="Validform_label" name="lack18">4</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。未进行
        验收不得分，验收单位
        不符合要求扣 2 分，验
        收不合格即销号的不
        得分
        </label>
    </td>
    <td class="value">
        <select name="lack18">
            
            <option value="Y" <c:if test="${scoreDetail.lack18=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack18=='N' || scoreDetail.lack18==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score18" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score18}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark18" rows="3" class="inputxt">${scoreDetail.remark18}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        2．负有煤矿安全监管职责的部门和煤矿安全监察机构
        检查发现的事故隐患，完成治理后，书面报告发现部门
        或其委托部门（单位）
        </label>
    </td>
    <td><label class="Validform_label" name="lack19">4</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。未按规
        定报告不得分
    </td>
    <td class="value">
        <select name="lack19">
            
            <option value="Y" <c:if test="${scoreDetail.lack19=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack19=='N' || scoreDetail.lack19==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score19" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score19}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark19" rows="3" class="inputxt">${scoreDetail.remark19}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">公示<br/>监督</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1．每月向从业人员通报事故隐患分布、治理进展情况；<br/>
        2．及时在井口（露天煤矿交接班室）或其他显著位置
        公示重大事故隐患的地点、主要内容、治理时限、责任
        人、停产停工范围；<br/>
        3．建立事故隐患举报奖励制度，公布事故隐患举报电
        话，接受从业人员和社会监督
        </label>
    </td>
    <td><label class="Validform_label" name="lack20">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。未定期
        通报、未及时公告扣 2
        分，通报和公告内容每
        缺 1 项扣 1 分，未设立
        举报电话扣 2 分，接到
        举报未核查或核实后
        未进行奖励扣 2 分
        </label>
    </td>
    <td class="value">
        <select name="lack20">
            
            <option value="Y" <c:if test="${scoreDetail.lack20=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack20=='N' || scoreDetail.lack20==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score20" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score20}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark20" rows="3" class="inputxt">${scoreDetail.remark20}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="5">
        <label class="Validform_label">五、<br/>保<br/>障<br/>措<br/>施<br/>(15分)</label>
    </td>
    <td >
        <label class="Validform_label">信息<br/>管理</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        采用信息化管理手段，实现对事故隐患排查治理记录统
        计、过程跟踪、逾期报警、信息上报的信息化管理
        </label>
    </td>
    <td><label class="Validform_label"  name="lack21">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。未采取
        信息化手段不得分，管
        理内容缺 1 项扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack21">
            
            <option value="Y" <c:if test="${scoreDetail.lack21=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack21=='N' || scoreDetail.lack21==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score21" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score21}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark21" rows="3" class="inputxt">${scoreDetail.remark21}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td >
        <label class="Validform_label">改进<br/>完善</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        矿长每月组织召开事故隐患治理会议，对一般事故隐
        患、重大事故隐患的治理情况进行通报，分析事故隐患
        产生的原因，提出加强事故隐患排查治理的措施，并编
        制月度事故隐患统计分析报告
        </label>
    </td>
    <td><label class="Validform_label" name="lack22">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。未召开会议定
        期通报、未编制报告不
        得分，报告内容不符合
        要求扣 2 分
        </label>
    </td>
    <td class="value">
        <select name="lack22">
            
            <option value="Y" <c:if test="${scoreDetail.lack22=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack22=='N' || scoreDetail.lack22==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score22" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score22}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark22" rows="3" class="inputxt">${scoreDetail.remark22}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td >
        <label class="Validform_label">资金<br/>保障</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        建立安全生产费用提取、使用制度。事故隐患排查治理
        工作资金有保障
        </label>
    </td>
    <td><label class="Validform_label" name="lack23">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。未建立
        并执行制度不得分，资
        金无保障扣 2 分
        </label>
    </td>
    <td class="value">
        <select name="lack23">
            
            <option value="Y" <c:if test="${scoreDetail.lack23=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack23=='N' || scoreDetail.lack23==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score23" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score23}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark23" rows="3" class="inputxt">${scoreDetail.remark23}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td >
        <label class="Validform_label">专项<br/>培训</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        每年至少组织安全管理技术人员进行 1 次事故隐患排查
        治理方面的专项培训
        </label>
    </td>
    <td><label class="Validform_label" name="lack24">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。未按要求开展
        培训不得分
        </label>
    </td>
    <td class="value">
        <select name="lack24">
            
            <option value="Y" <c:if test="${scoreDetail.lack24=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack24=='N' || scoreDetail.lack24==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score24" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score24}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark24" rows="3" class="inputxt">${scoreDetail.remark24}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td >
        <label class="Validform_label">考核<br/>管理</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1．建立日常检查制度，对事故隐患排查治理工作实施
        情况开展经常性检查；<br/>
        2．检查结果纳入工作绩效考核
        </label>
    </td>
    <td><label class="Validform_label" name="lack25">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。未建立制度、
        未执行制度不得分，检
        查结果未运用扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack25">
            
            <option value="Y" <c:if test="${scoreDetail.lack25=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack25=='N' || scoreDetail.lack25==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score25" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score25}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark25" rows="3" class="inputxt">${scoreDetail.remark25}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
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
    });

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

    $("input[name^='score']").on("blur",function(){calculate()});
    $("select[name^='lack']").on("change",function(){calculate()});
    $("select[name^='lack']").change();
    $("#ssaSumScore").attr("readonly","readonly");

</script>
</body>
