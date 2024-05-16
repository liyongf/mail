<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>安全风险分级管控</title>
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
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->井工煤矿->安全风险分级管控</div>--%>
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
    <td colspan='87' align="center" style="font-size:20px;font-weight: bold;">煤矿安全风险分级管控标准化评分</td>
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
    <td>F
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
            一、<br>工<br>作<br>机<br>制<br>（10分）
        </label>
    </td>
    <td  rowspan="2">
        <label class="Validform_label">职责<br>分工</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label" >
            1．建立安全风险分级管控工作责任体系，矿长全面负责，分管负责人负责分管范围内的安全风险分级管控工作
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未建立责任体系不得分，随机抽查，矿领导1人不清楚职责扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack1">

            <option value="Y" <c:if test="${scoreDetail.lack1=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1=='N'||scoreDetail.lack1==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea id="vioFactDesc" name="remark1" rows="3" class="inputxt">${scoreDetail.remark1}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            2．有负责安全风险分级管控工作的管理部门
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未明确管理部门不得分
        </label>
    </td>
    <td class="value">
        <select name="lack2">

            <option value="Y" <c:if test="${scoreDetail.lack2=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2=='N'||scoreDetail.lack2==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">制度<br>建设</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            建立安全风险分级管控工作制度，明确安全风险的辨识范围、方法和安全风险的辨识、评估、管控工作流程
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未建立制度不得分，辨识范围、方法或工作流程1处不明确扣2分
        </label>
    </td>
    <td class="value">
        <select name="lack3">

            <option value="Y" <c:if test="${scoreDetail.lack3=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3=='N'||scoreDetail.lack3==null}">selected="selected"</c:if>>否 </option>
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
    <td  rowspan="5">
        <label class="Validform_label">
            二、<br>安<br>全<br>风<br>险<br>辨<br>识<br>评<br>估<br>（40分）
        </label>
    </td>
    <td>
        <label class="Validform_label">年度<br>辨识<br>评估</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            每年底矿长组织各分管负责人和相关业务科室、区队进行年度安全风险辨识，重点对井工煤矿瓦斯、水、火、煤尘、顶板、冲击地压及提升运输系统，露天煤矿边坡、爆破、机电运输等容易导致群死群伤事故的危险因素开展安全风险辨识；及时编制年度安全风险辨识评估报告，建立可能引发重特大事故的重大安全风险清单，并制定相应的管控措施；将辨识评估结果应用于确定下一年度安全生产工作重点，并指导和完善下一年度生产计划、灾害预防和处理计划、应急救援预案
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack4">
            10
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未开展辨识或辨识组织者不符合要求不得分，辨识内容（危险因素不存在的除外）缺1项扣2分，评估报告、风险清单、管控措施缺1项扣2分，辨识成果未体现缺1项扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack4">

            <option value="Y" <c:if test="${scoreDetail.lack4=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack4=='N'||scoreDetail.lack4==null}">selected="selected"</c:if>>否 </option>
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
        <label class="Validform_label">专项<br>辨识<br>评估</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            新水平、新采（盘）区、新工作面设计前，开展1次专项辨识：<br> 1.专项辨识由总工程师组织有关业务科室进行； <br> 2.重点辨识地质条件和重大灾害因素等方面存在的安全风险； <br> 3.补充完善重大安全风险清单并制定相应管控措施； <br> 4.辨识评估结果用于完善设计方案，指导生产工艺选择、生产系统布置、设备选型、劳动组织确定等
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack5">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未开展辨识不得分，辨识组织者不符合要求扣2分，辨识内容缺1项扣2分，风险清单、管控措施、辨识成果未在应用中体现缺1项扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack5">

            <option value="Y" <c:if test="${scoreDetail.lack5=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack5=='N'||scoreDetail.lack5==null}">selected="selected"</c:if>>否 </option>
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
    <td class="td-padding">
        <label class="Validform_label">
            生产系统、生产工艺、主要设施设备、重大灾害因素（露天煤矿爆破参数、边坡参数）等发生重大变化时，开展1次专项辨识： <br> 1.专项辨识由分管负责人组织有关业务科室进行；<br>  2.重点辨识作业环境、生产过程、重大灾害因素和设施设备运行等方面存在的安全风险；<br>  3.补充完善重大安全风险清单并制定相应的管控措施； <br> 4.辨识评估结果用于指导重新编制或修订完善作业规程、操作规程
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack6">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未开展辨识不得分，辨识组织者不符合要求扣2分，辨识内容缺1项扣2分，风险清单、管控措施、辨识成果未在应用中体现缺1项扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack6">

            <option value="Y" <c:if test="${scoreDetail.lack6=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack6=='N'||scoreDetail.lack6==null}">selected="selected"</c:if>>否 </option>
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
    <td class="td-padding">
        <label class="Validform_label">
            启封火区、排放瓦斯、突出矿井过构造带及石门揭煤等高危作业实施前，新技术、新材料试验或推广应用前，连续停工停产1个月以上的煤矿复工复产前，开展1次专项辨识：<br>  1.专项辨识由分管负责人组织有关业务科室、生产组织单位（区队）进行；<br>  2.重点辨识作业环境、工程技术、设备设施、现场操作等方面存在的安全风险； <br> 3.补充完善重大安全风险清单并制定相应的管控措施；<br>  4.辨识评估结果作为编制安全技术措施依据
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack7">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未开展辨识不得分，辨识组织者不符合要求扣2分，辨识内容缺1项扣2分，风险清单、管控措施、辨识成果未在应用中体现缺1项扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack7">

            <option value="Y" <c:if test="${scoreDetail.lack7=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack7=='N'||scoreDetail.lack7==null}">selected="selected"</c:if>>否 </option>
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
    <td class="td-padding">
        <label class="Validform_label">
            本矿发生死亡事故或涉险事故、出现重大事故隐患或所在省份发生重特大事故后，开展1次针对性的专项辨识； <br> 1.专项辨识由矿长组织分管负责人和业务科室进行； <br> 2.识别安全风险辨识结果及管控措施是否存在漏洞、盲区；<br> 3.补充完善重大安全风险清单并制定相应的管控措施； <br> 4.辨识评估结果用于指导修订完善设计方案、作业规程、操作规程、安全技术措施等技术文件
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack8">
            6
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未开展辨识不得分，辨识组织者不符合要求扣2分，辨识内容缺1项扣2分，风险清单、管控措施、辨识成果未在应用中体现缺1项扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack8">

            <option value="Y" <c:if test="${scoreDetail.lack8=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack8=='N'||scoreDetail.lack8==null}">selected="selected"</c:if>>否 </option>
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
    <td  rowspan="6">
        <label class="Validform_label">
            三、<br>安<br>全<br>风<br>险<br>管<br>控<br>（35分）
        </label>
    </td>
    <td rowspan="2">
        <label class="Validform_label">管控<br>措施</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.重大安全风险管控措施由矿长组织实施，有具体工作方案，人员、技术、资金有保障
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack9">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。组织者不符合要求、未制定方案不得分，人员、技术、资金不明确、不到位1项扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack9">

            <option value="Y" <c:if test="${scoreDetail.lack9=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack9=='N'||scoreDetail.lack9==null}">selected="selected"</c:if>>否 </option>
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
    <td class="td-padding">
        <label class="Validform_label">
            2.在划定的重大安全风险区域设定作业人数上限
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack10">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未设定人数上限不得分，超1人扣0.5分
        </label>
    </td>
    <td class="value">
        <select name="lack10">

            <option value="Y" <c:if test="${scoreDetail.lack10=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack10=='N'||scoreDetail.lack10==null}">selected="selected"</c:if>>否 </option>
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
    <td rowspan="2">
        <label class="Validform_label">定期<br>检查</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.矿长每月组织对重大安全风险管控措施落实情况和管控效果进行一次检查分析，针对管控过程中出现的问题调整完善管控措施，并结合年度和专项安全风险辨识评估结果，布置月度安全风险管控重点，明确责任分工
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack11">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未组织分析评估不得分，分析评估周期不符合要求，每缺1次扣3分，管控措施不做相应调整或月度管控重点不明确1处扣2分，责任不明确1处扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack11">

            <option value="Y" <c:if test="${scoreDetail.lack11=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack11=='N'||scoreDetail.lack11==null}">selected="selected"</c:if>>否 </option>
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
    <td class="td-padding">
        <label class="Validform_label">
            2.分管负责人每旬组织对分管范围内月度安全风险管控重点实施情况进行一次检查分析，检查管控措施落实情况，改进完善管控措施
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack12">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未组织分析评估不得分，分析评估周期不符合要求，每缺1次扣3分，管控措施不做相应调整1处扣2分
        </label>
    </td>
    <td class="value">
        <select name="lack12">

            <option value="Y" <c:if test="${scoreDetail.lack12=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack12=='N'||scoreDetail.lack12==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">现场<br>检查</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            按照《煤矿领导带班下井及安全监督检查规定》，执行煤矿领导带班制度，跟踪重大安全风险管控措施落实情况，发现问题及时整改
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack13">
            6
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未执行领导带班制度不得分，未跟踪管控措施落实情况或发现问题未及时整改1处扣2分
        </label>
    </td>
    <td class="value">
        <select name="lack13">

            <option value="Y" <c:if test="${scoreDetail.lack13=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack13=='N'||scoreDetail.lack13==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">公告<br>警示</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            在井口（露天煤矿交接班室）或存在重大安全风险区域的显著位置，公告存在的重大安全风险、管控责任人和主要管控措施
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack14">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。未公示不得分，公告内容和位置不符合要求1处扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack14">

            <option value="Y" <c:if test="${scoreDetail.lack14=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack14=='N'||scoreDetail.lack14==null}">selected="selected"</c:if>>否 </option>
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
    <td  rowspan="3">
        <label class="Validform_label">
            四、<br>保<br>障<br>措<br>施<br>（15分）
        </label>
    </td>
    <td>
        <label class="Validform_label">信息<br>管理</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            采用信息化管理手段，实现对安全风险记录、跟踪、统计、分析、上报等全过程的信息化管理
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack15">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。未实现信息化管理不得分，功能每缺1项扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack15">

            <option value="Y" <c:if test="${scoreDetail.lack15=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack15=='N'||scoreDetail.lack15==null}">selected="selected"</c:if>>否 </option>
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
    <td rowspan="2">
        <label class="Validform_label">教育<br>培训</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1．入井（坑）人员和地面关键岗位人员安全培训内容包括年度和专项安全风险辨识评估结果、与本岗位相关的重大安全风险管控措施
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack16">
            6
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。培训内容不符合要求1处扣 1分
        </label>
    </td>
    <td class="value">
        <select name="lack16">

            <option value="Y" <c:if test="${scoreDetail.lack16=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack16=='N'||scoreDetail.lack16==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score16" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.score16}" >
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
    <td class="td-padding">
        <label class="Validform_label">
            2．每年至少组织参与安全风险辨识评估工作的人员学习1次安全风险辨识评估技术
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack17">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未组织学习不得分，现场询问相关学习人员，1人未参加学习扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack17">

            <option value="Y" <c:if test="${scoreDetail.lack17=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack17=='N'||scoreDetail.lack17==null}">selected="selected"</c:if>>否 </option>
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
    <td  style="text-align: center;" colspan="2">
        <label class="Validform_label">
            <font color="red">*</font>汇总得分
        </label>
    </td>
    <td class="value" style="text-align: left;" colspan="6">
        <input id="ssaSumScore" name="ssaSumScore" type="text" style="width: 150px" class="inputxt" value="${sfMineStandardAssessPage.ssaSumScore}" datatype="/^100(\.0+)?$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内数字!" >
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

        //计算页面总得分
        $("input[name*='score']").on("blur",function(){calculate();});
        $("select[name*='lack']").on("change",function(){calculate();});

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

</script>
</body>

