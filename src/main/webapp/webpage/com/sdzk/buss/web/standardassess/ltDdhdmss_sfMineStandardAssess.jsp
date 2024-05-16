<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>调度和地面设施</title>
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
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->露天煤矿->调度和地面设施</div>--%>
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
    <td colspan='8' align="center" style="font-size:20px;font-weight: bold;">煤矿调度和地面设施标准化评分表</td>
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
    <td  rowspan="4">
        <label class="Validform_label">
            一 、<br>调<br>度<br>基<br>础<br>工<br>作<br>（ 1 2 分 ）
        </label>
    </td>
    <td  rowspan="2">
        <label class="Validform_label">组织<br>机构</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.有调度指挥部门，岗位职责明确
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无调度指挥部门不得分，岗位职责不明确1处扣0.5分
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
    <td class="td-padding">
        <label class="Validform_label">
            2.每天 24h 专人值守，每班工作人员满足调度工作要求
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。人员配备不足或无值守人员不得分
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
        <label class="Validform_label">管理<br>制度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            制定并严格执行岗位安全生产责任制、调度值班制度、交接班制度、汇报制度、信息汇总分析制度、调度人员入井（坑）制度、业务学习制度、事故和突发事件信息报告与处理制度、文档管理制度等
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料与现场。每缺1项制度扣1分；制度内容不全或未执行，每处扣0.5分
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
        <label class="Validform_label">技术<br>支撑</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            备有《煤矿安全规程》规定的图纸、事故报告程序图（表）、矿领导值班、带班安排与统计表、生产计划表、重点工程进度图（表）、矿井灾害预防和处理计划、事故应急救援预案等，图（表）保持最新版本
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack4">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无矿井灾害预防和处理计划、事故应急救援预案不得分；每缺1种图（表）扣1分，图（表）未及时更新1处扣0.5分
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
    <td  rowspan="10">
        <label class="Validform_label">
            二 、<br>调<br>度<br>管<br>理<br>（ 2 5 分 ）
        </label>
    </td>
    <td  rowspan="2">
        <label class="Validform_label">组织<br>协调</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.掌握生产动态，协调落实生产作业计划，按规定处置生产中出现的各种问题，并准确记录
        </label>
    </td>
    <td rowspan="2">
        <label class="Validform_label" name="lack5">
            3
        </label>
    </td>
    <td rowspan = "2" style="text-align:left;">
        <label class="Validform_label">
            查资料。不符合要求1处扣0.5分
        </label>
    </td>
    <td rowspan = "2" class="value">
        <select name="lack5">
            
            <option value="Y" <c:if test="${scoreDetail.lack5=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack5=='N'||scoreDetail.lack5==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td rowspan = "2" class="value" style="max-width:150px;">
        <input  name="score5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score5}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value" rowspan = "2" >
        <textarea name="remark5" rows="3" class="inputxt">${scoreDetail.remark5}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            2.按规定及时上报安全生产信息，下达安全生产指令并跟踪落实、做好记录。
        </label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">应急<br>处置</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            出现险情或发生事故时，及时下达撤人指令、报告事故信息，按程序启动事故应急预案，跟踪现场处置情况并做好记录
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack6">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未授权调度员遇险情下达撤人调度指令、发现 1 次没有在出现险情下达撤人指令或未按程序启动事故应急预案或未及时跟踪现场处置情况不得分，记录不规范1处扣0.5分
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
        <label class="Validform_label">深入<br>现场</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            按规定深入现场，了解安全生产情况
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack7">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。每缺1人次深入现场扣1分
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
    <td  rowspan="3">
        <label class="Validform_label">调度<br>记录</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.值班记录整洁、清晰，完整、无涂改
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack8">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
    <td class="td-padding">
        <label class="Validform_label">
            2.有调度值班、交接班及安全生产情况统计等台账（记录）
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack9">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无台账（记录）的不得分；台账（记录）内容不完整、数据不准确1处扣0.5分
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
            3.有产、运、销、存的统计台账（运、销、存企业集中管理的除外），内容齐全，记录规范
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack10">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无台账（记录）的不得分；台账（记录）内容不完整、数据不准确1处扣0.5分
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
    <td  rowspan="2">
        <label class="Validform_label">调度<br>汇报</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.每班调度汇总有关安全生产信息
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack11">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查查资料。抽查1个月相关记录。缺少或内容不全，每1处扣0.5分
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
            2.按规定上报调度安全生产信息日报表、旬（周）、月调度安全生产信息统计表、矿领导值班带班情况统计表
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack12">
            6
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1处扣1分
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
        <label class="Validform_label">雨季<br>“三<br>防”</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            组织落实雨季“三防”相关工作，并做好记录
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack13">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。1处不符合要求不得分
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
    <td  rowspan="8">
        <label class="Validform_label">
            三 、<br>调<br>度<br>信<br>息<br>化<br>(27分)
        </label>
    </td>
    <td rowspan="3">
        <label class="Validform_label">通信<br>装备</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.装备调度通信系统，与主要硐室、生产场所（露天矿为无线通信系统）、应急救援单位、医院(井口保健站、急救站)、应急物资仓库及上级部门实现有线直拨
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack14">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣0.5分
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
            2. 有线调度通信系统有选呼、急呼、全呼、强插、强拆、录音等功能。调度工作台电话录音保存时间不少于3个月
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack15">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣0.5分
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
            3.按《煤矿安全规程》规定装备与重要工作场所直通的有线调度电话
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack16">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣0.5分
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
    <td rowspan="2">
        <label class="Validform_label">监控<br>系统</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.跟踪安全监控系统有关参数变化情况，掌握矿井安全生产状态
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack17">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣0.5分
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
            2.及时核实、处置系统预（报）警情况并做好记录
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack18">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。有1项预（报）警未处置扣0.5分
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
    <td>
        <label class="Validform_label">人员<br>位置<br>监测</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            3装备井下人员位置监测系统，准确显示井下总人数、人员时空分布情况，具有数据存储查询功能。矿调度室值班员监视人员位置等信息，填写运行日志
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack19">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。无系统或运行不正常、无数据存储查询功能不得分，数据不准确1处扣0.5分，未正常填写运行日志1次扣0.5分
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
    <td>
        <label class="Validform_label">图像<br>监视</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            矿调度室设置图像监视系统的终端显示装置，并实现信息的存储和查询
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack20">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。调度室无显示装置扣1分，显示装置运行不正常、存储或查询功能不全1处扣0.5分
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
    <td>
        <label class="Validform_label">信息<br>管理<br>系统</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            采用信息化手段对调度报表、生产安全事故统计表等数据进行处理，实现对煤矿安全生产信息跟踪、管理、预警、存储和传输功能
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack21">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。无管理信息系统或系统功能不全、运行不正常不得分；其他 1 处不符合要求扣 0.5 分
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
    <td  rowspan="2">
        <label class="Validform_label">
            四 、<br>岗<br>位<br>规<br>范<br>（ 4 分 ）
        </label>
    </td>
    <td>
        <label class="Validform_label">专业<br>技能</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.具备煤矿安全生产相关专业知识、掌握岗位相关知识；<br>2.人员经培训合格
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack22">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.5分
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
        <label class="Validform_label">规范<br>作业</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.严格执行岗位安全生产责任制；<br>2.无“三违”行为
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack23">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。发现“三违”不得分，不执行岗位责任制1人次扣0.5分
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
    <td  rowspan="2">
        <label class="Validform_label">
            五 、<br>文<br>明<br>生<br>产<br>(2分)
        </label>
    </td>
    <td  rowspan="2">
        <label class="Validform_label">文明<br>办公</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.设备、设施安装符合规定
        </label>
    </td>
    <td rowspan="2">
        <label class="Validform_label" name="lack24">
            2
        </label>
    </td>
    <td rowspan="2" style="text-align:left;">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣0.5分
        </label>
    </td>
    <td class="value" rowspan="2">
        <select name="lack24">
            
            <option value="Y" <c:if test="${scoreDetail.lack24=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack24=='N'||scoreDetail.lack24==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;" rowspan="2">
        <input  name="score24" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score24}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value" rowspan="2">
        <textarea name="remark24" rows="3" class="inputxt">${scoreDetail.remark24}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>

<tr style="display: none">
    <td  rowspan="2">
    </td>
    <td  rowspan="2">
    </td>
    <td class="td-padding">
    </td>
    <td rowspan="2">
        <label class="Validform_label" name="lack25">
            0
        </label>
    </td>
    <td rowspan="2" style="text-align:left;">
    </td>
    <td class="value" rowspan="2">
        <select name="lack25">
            <option value="Y">是 </option>
            <option value="N" selected="selected">否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;" rowspan="2">
        <input  name="score25" type="text" style="width: 150px" class="inputxt"  value="0" >
    </td>
</tr>

<tr>
    <td class="td-padding">
        <label class="Validform_label">
            2.图纸、资料、文件、牌板及工作场所清洁整齐、置物有序
        </label>
    </td>
</tr>
<tr>
    <td  rowspan="2">
        <label class="Validform_label">
            六 、<br>地<br>面<br>办<br>公<br>场<br>所<br>（2分）
        </label>
    </td>
    <td >
        <label class="Validform_label">办公<br>室</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            办公室配置满足工作需要，办公设施齐全、完好
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack26">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
    <td >
        <label class="Validform_label">会议<br>室</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            配置有会议室，设施齐全、完好
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack27">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
    <td  rowspan="3">
        <label class="Validform_label">
            七 、<br>两<br>堂<br>一<br>舍<br>（20分）
        </label>
    </td>
    <td>
        <label class="Validform_label">职工<br>食堂</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.基础设施齐全、完好，满足高峰和特殊时段职工就餐需要；<br>2.符合卫生标准要求，工作人员按要求持健康证上岗
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack28">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。基础设施不齐全扣1分，不符合卫生标准扣3分，未持证上岗的1人扣1分，不能满足就餐需求不得分
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
        <label class="Validform_label">职工<br>澡堂</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.职工澡堂设计合理，满足职工洗浴要求；<br>2.设有更衣室、浴室、厕所和值班室，设施齐全完好，有防滑、防寒、防烫等安全防护设施
        </label>
    </td>
    <td >
        <label class="Validform_label"  name="lack29">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查记录和现场。不能满足职工洗浴要求或脏乱的不得分，基础设施不全每缺1处扣1分，安全防护设施每缺1处扣1分
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
    <td>
        <label class="Validform_label">职工<br>宿舍<br>及洗<br>衣房</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.职工宿舍布局合理，人均面积不少于5㎡；<br>2.室内整洁，设施齐全、完好,物品摆放有序；<br>3.洗衣房设施齐全（洗、烘、熨），洗衣房、卫生间符合《工业企业设计卫生标准》的要求
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack30">
            7
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查记录和现场。职工宿舍不能满足人均面积5m2及以上、室内脏乱的不得分，其他不符合要求1处扣1分
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
    <td  rowspan="3">
        <label class="Validform_label">
            八 、<br>工<br>业<br>广<br>场<br>（6分）
        </label>
    </td>
    <td>
        <label class="Validform_label">工业<br>广场</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.工业广场设计符合规定要求，布局合理，工作区与生活区分区设置；<br>2.物料分类码放整齐；<br>3.煤仓及储煤场储煤能力满足煤矿生产能力要求；<br>4.停车场规划合理、划线分区，车辆按规定停放整齐，照明符合要求
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack31">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.5分
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
        <label class="Validform_label">工业<br>道路</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            工业道路应符合《厂矿道路设计规范》的要求,道路布局合理，实施硬化处理
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack32">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
        <label class="Validform_label">环境<br>卫生</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.依条件实施绿化；<br>2.厕所规模和数量适当、位置合理，设施完好有效，符合相应的卫生标准；<br>3.每天对储煤场、场内运煤道路进行整理、清洁，洒水降尘
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack33">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
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
        <label class="Validform_label">
            九 、<br>地<br>面<br>设<br>备<br>材<br>料<br>库<br>（ 2 分 ）
        </label>
    </td>
    <td>
        <label class="Validform_label">设备<br>库房</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.仓储配套设备设施齐全、完好；<br>2.不同性能的材料分区或专库存放并采取相应的防护措施；<br>3.货架布局合理，实行定置管理
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack34">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.5分
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
