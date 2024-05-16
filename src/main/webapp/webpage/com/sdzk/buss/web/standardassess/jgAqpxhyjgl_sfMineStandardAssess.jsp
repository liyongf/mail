
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>安全培训和应急管理</title>
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
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->井工煤矿->安全培训和应急管理</div>--%>
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
    <td colspan='8' align="center" style="font-size:20px;font-weight: bold;">煤矿安全培训和应急管理标准化评分表</td>
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
    <td  rowspan="19">
        <label class="Validform_label">
            一、<br>安<br>全<br>培<br>训<br>（共50分)
        </label>
    </td>
    <td  rowspan="4">
        <label class="Validform_label">基础<br>保障</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.有负责安全生产培训工作的机构，配备满足工作需求的人员
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1处扣1分
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
            2.建立并执行安全培训管理制度
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未建立制度不得分；制度不完善1处扣0.5分。
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
    <td class="td-padding">
        <label class="Validform_label">
            3.具备安全培训条件的煤矿，按规定配备师资和装备、设施；不具备培训条件的煤矿，可委托其他机构进行安全培训
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。场所、师资、设施等不符合要求或欠缺 1 处扣 0.2分；不具备条件且未委托培训的不得分
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
    <td class="td-padding">
        <label class="Validform_label">
            4.按照规定比例提取安全培训经费，做到专款专用
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack4">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未提取培训经费不得分，经费不足扣1分，未做到专款专用扣1分
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
    <td  rowspan="9">
        <label class="Validform_label">组织<br>实施</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.有年度培训计划并组织实施
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack5">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无计划不得分
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
            2.培训对象覆盖所有从业人员(包括劳务派遣者)
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack6">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。培训对象欠缺1种扣0.2分
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
            3.安全培训学时符合规定
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack7">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1处扣0.5分
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
            4.针对不同专业的培训对象和培训类别，开展有针对性的培训；使用新工艺、新技术、新设备、新材料时，对有关从业人员实施针对性安全再培训
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack8">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。培训无针对性扣1分，其他1处不符合要求扣0.5分
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
            5.特种作业人员经专门的安全技术培训(含复训)并考核合格
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack9">
            6
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。 1人不符合要求不得分
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
            6.主要负责人和职业卫生管理人员接受职业卫生培训；接触职业病危害因素的从业人员上岗前接受职业卫生培训和在岗期间的定期职业卫生培训
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack10">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。主要负责人未经过职业卫生培训扣1分;其他1人不符合要求扣0.2分
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
    <td class="td-padding">
        <label class="Validform_label">
            7.井工煤矿从事采煤、掘进、机电、运输、通风、地测等工作的班组长任职前接受专门的安全培训并经考核合格
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack11">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查查资料和现场。1人不符合要求扣0.2分
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
            8. 组织有关人员开展应急预案培训，熟练掌握应急预案相关内容
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack12">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.2分
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
    <td class="td-padding">
        <label class="Validform_label">
            9.煤矿主要负责人和安全生产管理人员自任职之日起6个月内，通过安全培训主管部门组织的安全生产知识和管理能力考核
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack13">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。1人不符合要求不得分
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
    <td rowspan="3">
        <label class="Validform_label">持证<br>上岗</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.特种作业人员持《特种作业人员操作资格证》上岗
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack14">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1人扣1分
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
            2.煤矿主要负责人和安全生产管理人员通过考核取得合格证上岗
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack15">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1人扣1分
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
            3.煤矿其他人员经培训合格取得培训合格证上岗
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack16">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1人扣0.2分
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
    <td rowspan="3">
        <label class="Validform_label">培训<br>档案</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.建立安全生产教育和培训档案，内容包含各类别、各专业安全培训的时间、内容、参加人员、考核结果等
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack17">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未建立档案不得分；档案内容不完整每缺1项扣0.2分
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
            2.建立健全特种作业人员培训、复训档案
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack18">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未建立档案不得分；档案内容不完整每缺1项扣0.2分
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
    <td class="td-padding">
        <label class="Validform_label">
            3.档案管理规范
        </label>
    </td>
    <td>
        <label class="Validform_label" name="lack19">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.2分
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
    <td  rowspan="10">
        <label class="Validform_label">
            二 、<br>班<br>组<br>安<br>全<br>建<br>设<br>(共10分)
        </label>
    </td>
    <td>
        <label class="Validform_label">制度<br>建设</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            建立并严格执行下列制度：<br>1.班组长安全生产责任制；<br>2.班前、班后会和交接班制度；<br>3.班组安全生产标准化和文明生产管理制度；<br>4.学习制度；<br>5.安全承诺制度；<br>6.民主管理班务公开制度；<br>7.安全绩效考核制度
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack20">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。每缺1项制度扣0.2分，1项制度不严格执行不得分
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
    <td rowspan="5">
        <label class="Validform_label">组织<br>建设</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.安全生产目标明确，有群众安全监督员（不得由班组长兼任）
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack21">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1项扣0.2分
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
    <td class="td-padding">
        <label class="Validform_label">
            2.班组建有民主管理机构，并组织开展班组民主活动
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack22">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未建立机构不得分；民主活动开展不符合要求扣0.5 分
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
    <td class="td-padding">
        <label class="Validform_label">
            3.开展班组建设创先争优活动、组织优秀班组和优秀班组长评选活动，建立表彰奖励机制
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack23">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。未建立机制或未开展活动不得分
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
    <td class="td-padding">
        <label class="Validform_label">
            4.建立班组长选聘、使用、培养机制
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack24">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未建立机制不得分
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
    <td class="td-padding">
        <label class="Validform_label">
            5.赋予班组长及职工在安全生产管理、规章制度制定、安全奖罚、民主评议等方面的知情权、参与权、表达权、监督权
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack25">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.2分
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
    <td rowspan="4">
        <label class="Validform_label">现场<br>管理</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.班前有安全工作安排，班组长督促落实作业前进行安全确认
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack26">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1项扣0.2分
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
    <td class="td-padding">
        <label class="Validform_label">
            2.严格执行交接班制度，交接重点内容包括隐患及整改、安全状况、安全条件及安全注意事项
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack27">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1处扣0.2分
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
    <td class="td-padding">
        <label class="Validform_label">
            3.组织班组正规循环作业和规范操作
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack28">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.2分
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
    <td class="td-padding">
        <label class="Validform_label">
            4.井工煤矿实施班组工程（工作）质量巡回检查，严格工程（工作）质量验收
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack29">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.2分
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
    <td  rowspan="19">
        <label class="Validform_label">
            三 、<br>应<br>急<br>管<br>理<br>(共40分)
        </label>
    </td>
    <td>
        <label class="Validform_label">机构<br>和职<br>责</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.建立应急救援工作日常管理领导机构、工作机构和应急救援指挥机构，人员配备满足工作需要，职责明确；<br>2.有固定的应急救援指挥场所
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack30">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.2分
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
        <label class="Validform_label">制度<br>建设</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            建立健全以下制度：<br>1.事故监测与预警制度；<br>2.应急值守制度；<br>3.应急信息报告和传递制度；<br>4.应急投入及资源保障制度；<br>5.应急预案管理制度；<br>6.应急演练制度；<br>7.应急救援队伍管理制度；<br>8.应急物资装备管理制度；<br>9.安全避险设施管理和使用制度；<br>10.应急资料档案管理制度
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack31">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。每缺1项制度扣1分，制度内容不完善1处扣0.2分
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
        <label class="Validform_label">应急<br>保障</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.配备应急救援物资、装备或设施，建立台账，按规定储存、维护、保养、更新、定期检查等；<br>2.有可靠的信息通讯和传递系统，保持最新的内部和外部应急响应通讯录；<br>3.配置必需的急救器材和药品；与就近的医疗机构签订急救协议；<br>4.建立覆盖本煤矿所有专项应急预案相关专业的技术专家库
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack32">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.5分
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
        <label class="Validform_label">安全<br>避险<br>系统</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            按规定建立完善井下安全避险设施；每年由总工程师组织开展安全避险系统有效性评估
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack33">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.2分
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
        <label class="Validform_label">应急<br>广播<br>系统</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            井下设置应急广播系统，井下人员能够清晰听到应急指令
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack34">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。未建立系统不得分；1处生产作业地点不能够听到应急指令扣0.5分
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
    <td>
        <label class="Validform_label">个体<br>防护<br>装备</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            按规定配置足量的自救器，入井人员随身携带；矿井避灾路线上按需求设置自救器补给站
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack35">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。自救器的备用量不足10％扣0.5分；其他 1 人（处）不符合要求扣0.2分
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
    <td>
        <label class="Validform_label">紧急<br>处置<br>权限</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            明确授予带班人员、班组长、瓦斯检查工、调度人员遇险处置权和紧急避险权
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack36">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。权力未明确不得分
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
    <td>
        <label class="Validform_label">技术<br>资料</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.井工煤矿应急指挥中心备有最新的采掘工程平面图、矿井通风系统图、井上下对照图、井下避灾路线图、灾害预防与处理计划、应急预案；<br>2.露天煤矿应急指挥中心备有最新的采剥、排土工程平面图和运输系统图、防排水系统图及排水设备布置图、井工老空区与露天矿平面对照图、应急救援预案
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack37">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。每缺1项扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack37">

            <option value="Y" <c:if test="${scoreDetail.lack37=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack37=='N'||scoreDetail.lack37==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score37" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score37}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark37" rows="3" class="inputxt">${scoreDetail.remark37}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="2">
        <label class="Validform_label">队伍<br>建设</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.煤矿有矿山救护队为其服务
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack38">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求不得分
        </label>
    </td>
    <td class="value">
        <select name="lack38">

            <option value="Y" <c:if test="${scoreDetail.lack38=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack38=='N'||scoreDetail.lack38==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score38" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score38}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark38" rows="3" class="inputxt">${scoreDetail.remark38}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            2.井工煤矿不具备设立矿山救护队条件的应组建兼职救护队，并与就近的救护队签订救护协议。兼职救护队按照《矿山救护规程》的相关规定配备器材和装备，实施军事化管理，器材和装备完好，定期接受专职矿山救护队的业务培训和技术指导，按照计划实施应急施救训练和演练
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack39">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。没有矿山救护队为本矿服务的或未签订救护协议不得分，其他 1 处不符合要求扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack39">

            <option value="Y" <c:if test="${scoreDetail.lack39=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack39=='N'||scoreDetail.lack39==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score39" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score39}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark39" rows="3" class="inputxt">${scoreDetail.remark39}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="4">
        <label class="Validform_label">应急<br>预案</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.预案编制与修订<br>（1）按照《生产安全事故应急预案管理办法》编制应急预案，并按规定及时修订；<br>（2）按规定组织应急预案的评审，形成书面评审结果。评审通过的应急预案由煤矿主要负责人签署公布，及时发放<br>（3）应急预案与煤矿所在地政府的生产安全事故应急预案相衔接
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack40">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未编制应急预案的“应急预案”项不得分；应急预案修订不及时不得分；应急预案有欠缺1处扣0.5 分，应急预案未组织评审不得分，评审证据资料、签署和发放管理环节1处不符合要求扣0.5分，应急预案发放不及时扣1 分，应急预案未与政府预案相衔接扣0.5分
        </label>
    </td>
    <td class="value">
        <select name="lack40">

            <option value="Y" <c:if test="${scoreDetail.lack40=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack40=='N'||scoreDetail.lack40==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score40" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score40}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark40" rows="3" class="inputxt">${scoreDetail.remark40}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            2.按照应急预案和灾害预防与处理计划的相关内容，针对重点工作场所、重点岗位的风险特点制定应急处置卡
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack41">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.2分
        </label>
    </td>
    <td class="value">
        <select name="lack41">

            <option value="Y" <c:if test="${scoreDetail.lack41=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack41=='N'||scoreDetail.lack41==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score41" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score41}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark41" rows="3" class="inputxt">${scoreDetail.remark41}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            3.按照分级属地管理的原则，按规定时限、程序完成应急预案上报并进行告知性备案
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack42">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未按照规定上报、备案不得分
        </label>
    </td>
    <td class="value">
        <select name="lack42">

            <option value="Y" <c:if test="${scoreDetail.lack42=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack42=='N'||scoreDetail.lack42==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score42" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score42}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark42" rows="3" class="inputxt">${scoreDetail.remark42}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            4.煤矿发生事故在第一时间启动应急预案，实施应急响应、组织应急救援；并按照规定的时限、程序上报事故信息
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack43">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1处扣0.5分
        </label>
    </td>
    <td class="value">
        <select name="lack43">

            <option value="Y" <c:if test="${scoreDetail.lack43=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack43=='N'||scoreDetail.lack43==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score43" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score43}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark43" rows="3" class="inputxt">${scoreDetail.remark43}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="3">
        <label class="Validform_label">应急<br>演练</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.有应急演练规划、年度计划和演练工作方案，内容符合相关规定
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack44">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1处扣0.2分
        </label>
    </td>
    <td class="value">
        <select name="lack44">

            <option value="Y" <c:if test="${scoreDetail.lack44=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack44=='N'||scoreDetail.lack44==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score44" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score44}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark44" rows="3" class="inputxt">${scoreDetail.remark44}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            2.按规定3年内完成所有综合应急预案和专项应急预案演练
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack45">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求不得分
        </label>
    </td>
    <td class="value">
        <select name="lack45">

            <option value="Y" <c:if test="${scoreDetail.lack45=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack45=='N'||scoreDetail.lack45==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score45" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score45}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark45" rows="3" class="inputxt">${scoreDetail.remark45}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            3.应急预案及演练、灾害预防和处理计划的实施由矿长组织；记录翔实完整，并进行评估、总结
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack46">
            4
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。演练和计划的实施组织主体不符合要求不得分；其他1处不符合要求扣0.5分
        </label>
    </td>
    <td class="value">
        <select name="lack46">

            <option value="Y" <c:if test="${scoreDetail.lack46=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack46=='N'||scoreDetail.lack46==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score46" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score46}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark46" rows="3" class="inputxt">${scoreDetail.remark46}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="2">
        <label class="Validform_label">资料<br>档案</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.应急资料归档保存， 连续完整，保存期限不少于2年
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack47">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1处扣0.5分
        </label>
    </td>
    <td class="value">
        <select name="lack47">

            <option value="Y" <c:if test="${scoreDetail.lack47=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack47=='N'||scoreDetail.lack47==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score47" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score47}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark47" rows="3" class="inputxt">${scoreDetail.remark47}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class="td-padding">
        <label class="Validform_label">
            2.应急管理档案内容完整真实（应包括组织机构、工作制度、应急预案、上报备案、应急演练、应急救援、协议文书等）管理规范
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack48">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.2分
        </label>
    </td>
    <td class="value">
        <select name="lack48">

            <option value="Y" <c:if test="${scoreDetail.lack48=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack48=='N'||scoreDetail.lack48==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score48" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score48}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark48" rows="3" class="inputxt">${scoreDetail.remark48}</textarea>
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
        <input id="ssaSumScore" name="ssaSumScore" type="text" style="width: 150px" class="inputxt" value="${sfMineStandardAssessPage.ssaSumScore}" datatype="/^100(\.0+)?$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/"  errormsg="请填写100以内数字!">
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">汇总得分</label>
    </td>
</tr>
<tr class="luru" height="40">
    <td class="upload" colspan="8" style="text-align: right;" align="right">
        <div class="ui_buttons" style="text-align: center;">
            <input type="button" onclick='saveMineStandardAssess(1)' value="保存" class="ui_state_highlight">
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
