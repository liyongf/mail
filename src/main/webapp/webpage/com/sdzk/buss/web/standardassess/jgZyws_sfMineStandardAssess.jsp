<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>职业卫生</title>
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
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->井工煤矿->职业卫生</div>--%>
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
    <td colspan='8' align="center" style="font-size:20px;font-weight: bold;">煤矿职业卫生准化评分</td>
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
            一、<br>职<br>业<br>卫<br>生<br>管<br>理<br>（24分）
        </label>
    </td>
    <td>
        <label class="Validform_label">组织<br>保障</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            建有职业病危害防治领导机构；有负责职业病危害防治管理的机构，配备专职职业卫生管理人员
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无领导机构、管理机构不得分；无专职人员扣1分
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
        <textarea name="remark1" rows="3" class="inputxt">${scoreDetail.remark1}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">责任<br>落实</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            明确煤矿主要负责人为煤矿职业危害防治工作的第一责任人；明确职业病危害防治领导机构、负责职业病危害防治管理的机构和人员的职责
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未明确第一责任人或未明确领导机构、管理机构及人员职责不得分；机构没有正常开展工作扣1分
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
        <label class="Validform_label">制度<br>完善</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            按规定建立完善职业病危害防治相关制度，主要包括： 职业病危害防治责任制度、职业病危害警示与告知制度、职业病危害项目申报制度、职业病防护设施管理制度、职业病个体防护用品管理制度、职业病危害日常监测及检测、评价管理制度、建设项目职业卫生“三同时”制度、劳动者职业健康监护及其档案管理制度、职业病诊断、鉴定及报告制度、职业病危害防治经费保障及使用管理制度、职业卫生档案管理制度、职业病危害事故应急管理制度及法律、法规、规章规定的其他职业病危害防治制度
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未建立制度不得分；制度不全，每缺1项扣1分；制度内容不符合要求或未能及时修订 1项扣0.5分
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
    <td>
        <label class="Validform_label">经费<br>保障</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            职业病危害防治专项经费满足工作需要
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack4">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未提取经费或经费不能满足需要不得分
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
    <td>
        <label class="Validform_label">工作<br>计划</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            有职业病危害防治规划、年度计划和实施方案；年度计划应包括目标、指标、进度安排、保障措施、考核评价方法等内容。实施方案应包括时间、进度、实施步骤、技术要求、考核内容、验收方法等内容
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack5">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无规划不得分，无年度计划、实施方案扣1分；相关要素不全的，每缺1项扣0.5分
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
    <td>
        <label class="Validform_label">档案<br>管理</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            分年度建立职业卫生档案，内容包括作业场所职业病危害因素种类清单、岗位分布以及作业人员接触情况等资料，职业病防护设施基本信息及其配置、使用、维护、检修与更换等记录，作业场所职业病危害因素检测、评价报告与记录，职业病个体防护用品配备、发放、维护与更换等记录，煤矿主要负责人、职业卫生管理人员和劳动者的职业卫生培训资料，职业病危害事故报告与应急处置记录，劳动者职业健康检查结果汇总资料，存在职业禁忌证、职业健康损害或者职业病的劳动者处理和安置情况记录，职业病危害项目申报情况记录，其他有关职业卫生管理的资料或者文件
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack6">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未建立档案不得分；档案缺项，每缺1项扣1分
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
    <td>
        <label class="Validform_label">危害<br>告知</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            与劳动者订立或者变更劳动合同时，应将作业过程中可能产生的职业病危害及其后果、防护措施和相关待遇等如实告知劳动者，并在劳动合同中载明
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack7">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料，抽查10份劳动合同。未全部进行告知不得分
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
    <td>
        <label class="Validform_label">工伤<br>保险</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            为存在劳动关系的劳动者（含劳务派遣工）足额缴纳工伤保险
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack8">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未全部参加工伤保险不得分
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
    <td>
        <label class="Validform_label">检测<br>评价</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            每年进行一次作业场所职业病危害因素检测，每3年进行一次职业病危害现状评价；根据检测、评价结果，制定整改措施；检测、评价结果向煤矿安全监察机构报告
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack9">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未按周期检测评价不得分；其他1处不符合要求扣1分
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
    <td>
        <label class="Validform_label">个体<br>防护</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            按照《煤矿职业卫生个体防护用品配备标准》（AQ1051）为劳动者（含劳务派遣工）发放符合要求的个体防护用品，做好记录，并指导和督促劳动者正确使用,严格执行劳动防护用品过期销毁制度
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack10">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。现场抽查4个岗位，每个岗位抽查1人，未按照AQ1051发放个体防护用品的，每缺1项扣1分， 1人1项用品不符合要求扣0.5分，每发现1人未使用个体防护用品扣0.5分；无个体防护用品发放登记记录扣2分，记录不完整、不清楚的，扣0.5分
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
    <td>
        <label class="Validform_label">公告<br>警示</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            在醒目位置设置公告栏，公布工作场所职业病危害因素检测结果。对产生严重职业病危害的作业岗位，应在其醒目位置设置警示标识和警示说明，载明产生职业病危害的种类、后果、预防以及应急救援措施等内容
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack11">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。未按规定公布检测结果不得分，公告栏公布不全，每缺1项扣1分，警示标识和警示说明缺失、内容不全1处扣0.5分
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
    <td  rowspan="12">
        <label class="Validform_label">
            二、<br>职<br>业<br>病<br>危<br>害<br>（42分）
        </label>
    </td>
    <td>
        <label class="Validform_label">监测<br>人员</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            配备职业病危害因素监测人员；监测人员经培训合格后上岗作业
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack12">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未配备人员或未经培训合格不得分
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
    <td rowspan="6">
        <label class="Validform_label">粉尘</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.按规定配备2台（含）以上粉尘采样器或直读式粉尘浓度测定仪等粉尘浓度测定设备
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack13">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。无设备不得分；配备监测仪器不足扣1分
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
    <td class="td-padding">
        <label class="Validform_label">
            2.采煤工作面回风巷、掘进工作面回风流设置有粉尘浓度传感器，并接入安全监控系统
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack14">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。粉尘浓度传感器设置不符合要求1处扣1分，未接入安全监控系统扣1分
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
    <td class="td-padding">
        <label class="Validform_label">
            3.粉尘监测地点布置符合规定
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack15">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。监测地点不符合要求1处扣1分
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
    <td class="td-padding">
        <label class="Validform_label">
            4.粉尘监测周期符合规定，总粉尘浓度井工煤矿每月测定2次、露天煤矿每月测定1次或采用实时在线监测；粉尘分散度每6个月测定1次或采用实时在线监测；呼吸性粉尘浓度每月测定1次；粉尘中游离二氧化硅含量每6个月测定1次,在变更工作面时也须测定1次；开采深度大于200m 的露天煤矿,在气压较低的季节应当适当增加测定次数
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack16">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。总粉尘、呼吸性粉尘浓度监测周期不符合要求且未采用实时在线监测不得分；其他监测周期不符合要求1处扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack16">
            
            <option value="Y" <c:if test="${scoreDetail.lack16=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack16=='N'||scoreDetail.lack16==null}">selected="selected"</c:if>>否 </option>
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
    <td class="td-padding">
        <label class="Validform_label">
            5.采用定点监测、个体监测方法对粉尘进行监测
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack17">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求不得分
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
    <td class="td-padding">
        <label class="Validform_label">
            6.粉尘浓度不超过规定：粉尘短时间定点监测结果不超过时间加权平均容许浓度的2倍；粉尘定点长时间监测、个体工班监测结果不超过时间加权平均容许浓度
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack18">
            10
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。无监测数据,不得分；浓度每超标1项扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack18">
            
            <option value="Y" <c:if test="${scoreDetail.lack18=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack18=='N'||scoreDetail.lack18==null}">selected="selected"</c:if>>否 </option>
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
    <td rowspan="3">
        <label class="Validform_label">噪声</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.按规定配备有2台（含）以上噪声测定仪器，作业场所噪声至少每6个月监测1次
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack19">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。测定仪器每缺1台扣1分；监测周期不符合要求扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack19">
            
            <option value="Y" <c:if test="${scoreDetail.lack19=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack19=='N'||scoreDetail.lack19==null}">selected="selected"</c:if>>否 </option>
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
    <td class="td-padding">
        <label class="Validform_label">
            2.噪声监测地点布置符合规定
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack20">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1处扣0.5分
        </label>
    </td>
    <td class="value">
        <select name="lack20">
            
            <option value="Y" <c:if test="${scoreDetail.lack20=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack20=='N'||scoreDetail.lack20==null}">selected="selected"</c:if>>否 </option>
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
    <td class="td-padding">
        <label class="Validform_label">
            3.劳动者接触噪声8h或40h等效声级不超过85dB(A)
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack21">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。无监测数据不得分，声级超过规定发现1处扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack21">
            
            <option value="Y" <c:if test="${scoreDetail.lack21=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack21=='N'||scoreDetail.lack21==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">高温</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            采掘工作面回风流和机电设备硐室设置温度传感器；采掘工作面空气温度超过26℃、机电设备硐室超过30℃时,缩短超温地点工作人员的工作时间,并给予高温保健待遇；采掘工作面的空气温度超过30℃、机电设备硐室超过34℃时停止作业；有热害的井工煤矿应当采取通风等非机械制冷降温措施，无法达到环境温度要求时,采用机械制冷降温措施。
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack22">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。1处不符合要求不得分。
        </label>
    </td>
    <td class="value">
        <select name="lack22">
            
            <option value="Y" <c:if test="${scoreDetail.lack22=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack22=='N'||scoreDetail.lack22==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">化学<br>毒物</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            对作业环境中氧化氮、一氧化碳、二氧化硫浓度每3个月至少监测1次，对硫化氢浓度每月至少监测1次；化学毒物等职业病危害因素浓度/强度符合规定
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack23">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未进行监测或危害因素浓度/强度超过规定不得分；监测项目不全，每缺1项扣1分；监测周期不符合要求1项扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack23">
            
            <option value="Y" <c:if test="${scoreDetail.lack23=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack23=='N'||scoreDetail.lack23==null}">selected="selected"</c:if>>否 </option>
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
    <td  rowspan="6">
        <label class="Validform_label">
            三、<br>职<br>业<br>健<br>康<br>防<br>护<br>（18分）
        </label>
    </td>
    <td>
        <label class="Validform_label">上岗<br>前检<br>查</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            组织新录用人员和转岗人员进行上岗前职业健康检查，检查机构具备职业健康检查资质，形成职业健康检查评价报告；不安排未经上岗前职业健康检查和有职业禁忌证的劳动者从事接触职业病危害的作业
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack24">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未安排检查或者检查机构无资质、无职业健康检查评价报告、检查项目不符合规定不得分；其他1人不符合要求扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack24">
            
            <option value="Y" <c:if test="${scoreDetail.lack24=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack24=='N'||scoreDetail.lack24==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">在岗<br>期间<br>检查</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            按规定周期组织在岗人员进行职业健康检查，检查机构具备职业健康检查资质，形成职业健康检查评价报告；发现与所从事的职业相关的健康损害的劳动者，调离原工作岗位并妥善安置
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack25">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未安排检查、周期不符合规定或者检查机构无资质、无职业健康检查评价报告、检查项目不符合规定不得分；健康损害劳动者未调离、安置的，发现1人扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack25">
            
            <option value="Y" <c:if test="${scoreDetail.lack25=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack25=='N'||scoreDetail.lack25==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">离岗<br>检查</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            准备调离或脱离作业及岗位人员组织进行离岗职业健康检查，检查机构具备职业健康检查资质，形成职业健康检查评价报告
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack26">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未安排检查或者检查机构无资质的、无报告的、检查项目不符合规定的，不得分；离岗检查有遗漏的，发现1人扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack26">
            
            <option value="Y" <c:if test="${scoreDetail.lack26=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack26=='N'||scoreDetail.lack26==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score26" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score26}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark26" rows="3" class="inputxt">${scoreDetail.remark26}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">应急<br>检查</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            对遭受或可能遭受急性职业病危害的劳动者进行健康检查和医学观察
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack27">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未对劳动者进行健康检查和医学观察的不得分
        </label>
    </td>
    <td class="value">
        <select name="lack27">
            
            <option value="Y" <c:if test="${scoreDetail.lack27=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack27=='N'||scoreDetail.lack27==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score27" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score27}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark27" rows="3" class="inputxt">${scoreDetail.remark27}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">结果<br>告知</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            按规定将职业健康检查结果书面告知劳动者
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack28">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未将职业健康检查结果书面告知劳动者不得分；每遗漏1人扣0.5分
        </label>
    </td>
    <td class="value">
        <select name="lack28">
            
            <option value="Y" <c:if test="${scoreDetail.lack28=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack28=='N'||scoreDetail.lack28==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score28" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score28}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark28" rows="3" class="inputxt">${scoreDetail.remark28}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">监护<br>档案</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            建立劳动者个人职业健康监护档案，并按照有关规定的期限妥善保存；档案包括劳动者个人基本情况、劳动者职业史和职业病危害接触史、历次职业健康检查结果及处理情况、职业病诊疗等资料；劳动者离开时应如实、无偿为劳动者提供职业健康监护档案复印件并签章
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack29">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未建立档案或未按要求向劳动者提供复印件不得分；档案内容不全，每缺1项扣1分；未指定人员负责保管扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack29">
            
            <option value="Y" <c:if test="${scoreDetail.lack29=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack29=='N'||scoreDetail.lack29==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score29" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score29}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark29" rows="3" class="inputxt">${scoreDetail.remark29}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td  rowspan="5">
        <label class="Validform_label">
            四、<br>职<br>业<br>病<br>诊<br>断<br>鉴<br>定<br>（12分）
        </label>
    </td>
    <td>
        <label class="Validform_label">职业<br>病诊<br>断</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            安排劳动者、疑似职业病病人进行职业病诊断
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack30">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。走访询问不少于10名职业病危害严重的重点岗位的劳动者，有1人提出职业病诊断申请而被无理由拒绝或未安排疑似职业病病人进行职业病诊断不得分
        </label>
    </td>
    <td class="value">
        <select name="lack30">
            
            <option value="Y" <c:if test="${scoreDetail.lack30=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack30=='N'||scoreDetail.lack30==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score30" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score30}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark30" rows="3" class="inputxt">${scoreDetail.remark30}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">职业<br>病病<br>人待<br>遇</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            保障职业病病人依法享受国家规定的职业病待遇
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack31">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。走访询问不少于5名职业病患者，有1人未保证职业病待遇不得分
        </label>
    </td>
    <td class="value">
        <select name="lack31">
            
            <option value="Y" <c:if test="${scoreDetail.lack31=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack31=='N'||scoreDetail.lack31==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score31" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score31}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark31" rows="3" class="inputxt">${scoreDetail.remark31}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">治疗、<br>定期<br>检查<br>和康<br>复</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            安排职业病病人进行治疗、定期检查、康复
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack32">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。对照职业病病人名单和诊断病例档案，检查职业病病人治疗、定期检查和康复记录，1项1人次未安排扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack32">
            
            <option value="Y" <c:if test="${scoreDetail.lack32=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack32=='N'||scoreDetail.lack32==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score32" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score32}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark32" rows="3" class="inputxt">${scoreDetail.remark32}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">职业<br>病病<br>人安<br>置</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            将职业病病人调离接触职业病危害岗位并妥善安置
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack33">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。1人未按规定安置不得分
        </label>
    </td>
    <td class="value">
        <select name="lack33">
            
            <option value="Y" <c:if test="${scoreDetail.lack33=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack33=='N'||scoreDetail.lack33==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score33" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score33}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark33" rows="3" class="inputxt">${scoreDetail.remark33}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">诊断、<br>鉴定<br>资料</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            提供职业病诊断、伤残等级鉴定所需要的职业史和职业病危害接触史、作业场所职业病危害因素检测结果等资料
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack34">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。走访询问不少于5名当事人，煤矿不提供或不如实提供有关资料不得分
        </label>
    </td>
    <td class="value">
        <select name="lack34">
            
            <option value="Y" <c:if test="${scoreDetail.lack34=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack34=='N'||scoreDetail.lack34==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score34" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score34}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark34" rows="3" class="inputxt">${scoreDetail.remark34}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="2">
        <label class="Validform_label">
            五、<br>工<br>会<br>监<br>督<br>（4分）
        </label>
    </td>
    <td rowspan="2">
        <label class="Validform_label">工会<br>监督<br>与维<br>权</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            设立劳动保护监督检查委员会
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack35">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求不得分
        </label>
    </td>
    <td class="value">
        <select name="lack35">
            
            <option value="Y" <c:if test="${scoreDetail.lack35=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack35=='N'||scoreDetail.lack35==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score35" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score35}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark35" rows="3" class="inputxt">${scoreDetail.remark35}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            对职业病防治工作进行监督，维护劳动者的合法权益
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack36">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。工会组织未依法对职业病防治工作进行监督不得分，开展监督活动没有记录扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack36">
            
            <option value="Y" <c:if test="${scoreDetail.lack36=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack36=='N'||scoreDetail.lack36==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score36" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score36}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark36" rows="3" class="inputxt">${scoreDetail.remark36}</textarea>
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
