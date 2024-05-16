<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>边坡</title>
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

    </style>
</head>
<body>
<%--<c:if test="${from ne 'mineAssess'}">--%>
    <%--<!-- from :mineAssess 页面请求来源于矿井考核自评汇总的时候不显示该面包屑，否则显示面包屑 -->--%>
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->露天煤矿->边坡</div>--%>
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
<table style="width: 98%;" cellpadding="0" cellspacing="1" class="formtable" align="center">
<tr style="height: 40px;">
    <td colspan='8' align="center" style="font-size:20px;font-weight: bold;">露天煤矿边坡标准化评分</td>
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
    <td  rowspan="7">
        <label class="Validform_label">
            一、<br>技<br>术<br>管<br>理<br>（20分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">组织保障</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            有部门负责边坡管理工作
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无部门负责不得分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">管理制度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            制定边坡管理制度，定期巡视边坡，有巡视记录
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无管理制度、不巡视或无巡视记录不得分，记录不完善1处扣1分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="2">
        <label class="Validform_label">资料管理</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.有完善、准确、详细的工程地质、水文地质勘探资料
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。资料缺项不得分
        </label>
    </td>
    <td class="value" >
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
    <td class="td-padding">
        <label class="Validform_label">
            2.有边坡设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack4">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无设计不得分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">气象预报</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            与当地气象部门建立天气预报及时通报机制
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack5">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未建立通报机制不得分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">技术措施</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            制定边坡稳定专项技术措施
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack6">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无技术措施不得分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">应急预案</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            制定滑坡应急预案并组织演练
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack7">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求不得分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="5">
        <label class="Validform_label">
            二、<br>采<br>场<br>边<br>坡<br>（30分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">稳定性分<br>析与评价</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            每年做边坡稳定性分析与评价
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack8">
            7
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未进行分析与评价不得分
        </label>
    </td>
    <td class="value" >
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
        <label class="Validform_label">稳定验算</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            每年做边坡稳定验算
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack9">
            7
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未做边坡稳定验算不得分
        </label>
    </td>
    <td class="value" >
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
        <label class="Validform_label">最终边坡</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            最终边坡到界后，稳定性达不到要求时，修改设计，并采取治理措施
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack10">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未修改设计或未采取治理措施不得分
        </label>
    </td>
    <td class="value" >
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
        <label class="Validform_label">到界平盘</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack11">
            6
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合设计不得分
        </label>
    </td>
    <td class="value" >
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
            2.临近到界的平盘采取控制爆破
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack12">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未采取控制爆破不得分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="5">
        <label class="Validform_label">
            三、<br>排<br>土<br>场<br>边<br>坡<br>（30分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">稳定性分<br>析与评价</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            定期做边坡稳定性分析与评价
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack13">
            7
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未进行分析与评价不得分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">稳定验算</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            定期做边坡稳定验算
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack14">
            7
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未进行边坡稳定验算不得分
        </label>
    </td>
    <td class="value" >
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
        <label class="Validform_label">稳定性管理</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.排土场到界前100m，采取措施
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack15">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。未采取措施不得分
        </label>
    </td>
    <td class="value" >
        <select name="lack15">
            
            <option value="Y" <c:if test="${scoreDetail.lack5=='Y'}">selected="selected"</c:if>>是 </option>
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
            2.内排土场基底有不利于边坡稳定的松软土岩时，按照设计要求进行处理
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack16">
            6
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未按要求进行处理不得分
        </label>
    </td>
    <td class="value" >
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
            3.排土场的排弃高度和边坡角，符合设计
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack17">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求不得分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="3">
        <label class="Validform_label">
            四、<br>不<br>稳<br>定<br>边<br>坡<br>（20分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">管理</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            对不稳定边坡实施重点监管，进行稳定性分析与评价
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack18">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求不得分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">监测</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            在不稳定边坡设监测网络，有监测记录
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack19">
            8
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未设监测网络或没有监测记录不得分，记录不符合要求1处扣2分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">防范</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            加强巡视，有巡视记录，发现滑坡征兆，撤出作业人员，设警示标志
        </label>
    </td>
    <td >
        <label class="Validform_label"  name="lack20">
            9
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。有危险未撤人不得分，无巡视记录扣3分，未设警示标志扣3分
        </label>
    </td>
    <td class="value" >
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
