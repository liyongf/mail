<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>安全生产标准化</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
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
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->井工煤矿->采煤</div>--%>
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
<div style="width: 95%; height:100%;">
<div style="width:100%; height:1px;"></div>
<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable" align="center">
    <tr style="height: 40px;">
        <td colspan='7' align="center" style="font-size:20px;font-weight: bold;">煤矿采煤标准化评分表</td>
    </tr>
    <tr>
        <td class="value" colspan="7" style="text-align: right">
            <label class="Validform_label">
                <font color="red">*</font>考核月份:
            </label>
            <input id="ssaMonth" name="ssaMonth" type="text" style="width:150px"
                   class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM'})" datatype="*" value='<fmt:formatDate value='${sfMineStandardAssessPage.ssaMonthDate}' type="date" pattern="yyyy-MM"/>'
                    >
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">考核月份</label>
        </td>
    </tr>
</table>
<table style="width:100%;" cellpadding="0" cellspacing="1" class="formtable" align="center">
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
    <td rowspan="5">
    	<label class="Validform_label">
        一、<br>基<br>础<br>管<br>理<br>（15分）
        </label>
    </td>
    <td>
        <label class="Validform_label">监测</label>
    </td>
    <td class = "td-padding">
    	<label class="Validform_label">
        采煤工作面实行顶板动态和支护质量监测，进、回风巷实行顶板离层观测；有相关监测、观测记录，资料齐全
        </label>
    </td>
    <td><label class="Validform_label" name="lack1">3</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。未开展动态监测、观测和无记录资料不得分记录资料缺1项扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack1">
            
            <option value="Y" <c:if test="${scoreDetail.lack1=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1=='N' || scoreDetail.lack1==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score1}" >
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
    <td>
        <label class="Validform_label ">
            规程<br/>措施
        </label>
    </td>
    <td class = "td-padding">
    	<label class="Validform_label">
       1.作业规程符合《煤矿安全规程》等要求。采煤工作面地质条件发生变化时，及时修改作业规程或补充安全技术措施；<br/>
        2.矿总工程师组织人员定期对作业规程贯彻实施情况进行复审，且有复审意见；<br/>
        3.工作面安装、初次放顶、强制放顶、收尾、回撤、过地质构造带、过老巷、过煤柱、过冒顶区，以及托伪顶开采时，制定安全技术措施并组织实施；<br/>
        4.作业规程中支护方式的选择、支护强度的计算有依据；<br/>
        5.作业规程中各种附图完整规范；<br/>
        6.放顶煤开采工作面开采设计制定有防瓦斯、防灭火、防水等灾害治理专项安全技术措施，并按规定进行审批和验收
        </label>
    </td>
    <td ><label class="Validform_label" name="lack2">5</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查资料和现场。内容不全，每缺1项扣1分1项不符，合要求扣0.5分
        </label>
    </td>
    <td class="value">
        <select name="lack2">
            
            <option value="Y" <c:if test="${scoreDetail.lack2=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2=='N' || scoreDetail.lack2==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score2" type="text" style="width:150px;" class="inputxt"  value="${scoreDetail.score2}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
       1.有岗位安全生产责任制度；<br/>
        2.有工作面顶板管理制度，有支护质量检查、顶板动态监测和分析制度，有变化管理制度；<br/>
        3.有采煤作业规程编制、审批、复审、贯彻、实施制度；<br/>
        4.有工作面机械设备检修保养制度、乳化液泵站管理制度、文明生产管理制度、有工作面支护材料设备配件备用制度等
        </label>
    </td>
    <td><label class="Validform_label" name="lack3">3</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查资料。制度不全，每缺1项扣1分，1 项不符合要求扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack3">
            
            <option value="Y" <c:if test="${scoreDetail.lack3=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3=='N' || scoreDetail.lack3==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score3" type="text" style="width:150px;" class="inputxt"  value="${scoreDetail.score3}" >
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
        <label class="Validform_label">支护<br/>材料</label>
    </td>
    <td class = "td-padding">
    	<label class="Validform_label">
        支护材料有管理台账，单体液压支柱完好，使用期限超过 8 个月后，应进行检修和压力试验，记录齐全；现场备用支护材料和备件符合作业规程要求
        </label>
    </td>
    <td ><label class="Validform_label" name="lack4">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。不符合要求1处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack4">
            
            <option value="Y" <c:if test="${scoreDetail.lack4=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack4=='N' || scoreDetail.lack4==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score4" type="text" style="width:150px;" class="inputxt"  value="${scoreDetail.score4}" >
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
        <label class="Validform_label">采煤<br/>机械<br/>化</label>
    </td>
    <td class = "td-padding">
    	<label class="Validform_label">
        采煤工作面采用机械化开采
        </label>
    </td>
    <td><label class="Validform_label" name="lack5">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
            查现场。未使用机械化开采不得分
        </label>
    </td>
    <td class="value">
        <select name="lack5">
            
            <option value="Y" <c:if test="${scoreDetail.lack5=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack5=='N' || scoreDetail.lack5==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score5" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score5}" >
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
    <td rowspan="2">
    	<label class="Validform_label">
        二、<br>岗<br>位<br>规<br>范<br>（5分）
        </label>
    </td>
    <td>
        <label class="Validform_label">专业<br/>技能</label>
    </td>
    <td class = "td-padding">
    	<label class="Validform_label">
        管理和技术人员掌握相关的岗位职责、管理制度、技术 措施，作业人员掌握本岗位操作规程、作业规程相关内容和安全技术措施
        </label>
    </td>
    <td><label class="Validform_label" name="lack6">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查资料和现场。不符合要求1处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack6">
            
            <option value="Y" <c:if test="${scoreDetail.lack6=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack6=='N' || scoreDetail.lack6==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score6" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score6}" >
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
        <label class="Validform_label">规范<br/>作业</label>
    </td>
    <td class = "td-padding">
    	<label class="Validform_label">
        1.现场作业人员严格执行本岗位安全生产责任制，掌握本岗位相应的操作规程和安全措施，操作规范，无“三违”行为<br/>
        2.作业前进行安全确认；<br/>
        3.零星工程施工有针对性措施、有管理人员跟班
        </label>
    </td>
    <td><label class="Validform_label" name="lack7">3</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。发现“三违”不得分，其他不符合要求1处扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack7">
            
            <option value="Y" <c:if test="${scoreDetail.lack7=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack7=='N' || scoreDetail.lack7==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score7" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score7}" >
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
    <td rowspan="20">
        <label class="Validform_label">三、<br/>质<br/>量<br/>与<br/>安<br/>全<br/>(50分)</label>
    </td>
    <td rowspan="11">
        <label class="Validform_label">顶板<br/>管理</label>
    </td>
    <td class = "td-padding">
    	<label class="Validform_label">
       1.工作面液压支架初撑力不低于额定值的80%，有现场检测手段；单体液压支柱初撑力符合《煤矿安全规程》要求
       </label>
    </td>
    <td><label class="Validform_label" name="lack8">4</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。沿工作面均匀选10 个点现场测定，1点不符合要求扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack8">
            
            <option value="Y" <c:if test="${scoreDetail.lack8=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack8=='N' || scoreDetail.lack8==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score8" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score8}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        2.工作面支架中心距（支柱间排距）误差不超过100mm，侧护板正常使用，架间间隙不超过100mm（单体支柱间距误差不超过100mm）；支架（支柱）不超高使用，支架（支柱）高度与采高相匹配，控制在作业规程规定的范围内，支架的活柱行程不小于200mm（企业特殊定制支架、支柱以其技术指标为准）
    	</label>
    </td>
    <td><label class="Validform_label" name="lack9">4</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。沿工作面均匀选10 个点现场测定1点不符合要求扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack9">
            
            <option value="Y" <c:if test="${scoreDetail.lack9=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack9=='N' || scoreDetail.lack9==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score9" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score9}"/>
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
        3.液压支架接顶严实，相邻支架（支柱）顶梁平整，无
        明显错茬（不超过顶梁侧护板高的2/3），支架不挤不
        咬；采高大于3.0m或片帮严重时，应有防片帮措施；支
        架前梁（伸缩梁）梁端至煤壁顶板垮落高度不大于
        300mm。高档普采（炮采）工作面机道梁端至煤壁顶板
        垮落高度不大于200mm，超过200mm时采取有效措施
        </label>
    </td>
    <td><label class="Validform_label" name="lack10">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。不符合
        要求1处扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack10">
            
            <option value="Y" <c:if test="${scoreDetail.lack10=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack10=='N' || scoreDetail.lack10==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score10" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score10}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        4.支架顶梁与顶板平行,最大仰俯角不大于7°；支架垂
        直顶底板，歪斜角不大于5°；支柱垂直顶底板，仰俯
        角符合作业规程规定
        </label>
    </td>
    <td><label class="Validform_label" name="lack11">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。不符合
        要求1处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack11">
            
            <option value="Y" <c:if test="${scoreDetail.lack11=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack11=='N' || scoreDetail.lack11==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score11" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score11}" >
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
        5.工作面液压支架（支柱顶梁）端面距符合作业规程规
        定。工作面“三直一平”，液压支架（支柱）排成一条
        直线，其偏差不超过50mm。工作面伞檐长度大于1m时，
        其最大突出部分，薄煤层不超过150mm，中厚以上煤层
        不超过200mm；伞檐长度在1m及以下时，最突出部分薄
        煤层不超过200mm，中厚煤层不超过250mm
        </label>
    </td>
    <td><label class="Validform_label" name="lack12">4</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。不符合
        要求1处扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack12">
            
            <option value="Y" <c:if test="${scoreDetail.lack12=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack12=='N' || scoreDetail.lack12==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>
    <td class="value" style="max-width:150px;">
        <input  name="score12" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score12}" >
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
        6.工作面内液压支架（支柱）编号管理，牌号清晰
        </label>
    </td>
    <td><label class="Validform_label" name="lack13">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。不符合要求1处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack13">
            
            <option value="Y" <c:if test="${scoreDetail.lack13=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack13=='N' || scoreDetail.lack13==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score13" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score13}" >
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
        7.工作面内特殊支护齐全；局部悬顶和冒落不充分的，
        悬顶面积小于10m<sup>2</sup> 时应采取措施，悬顶面积大于10m <sup>2</sup>时
        应进行强制放顶。特殊情况下不能强制放顶时，应有加
        强支护的可靠措施和矿压观测监测手段
        </label>
    </td>
    <td><label class="Validform_label" name="lack14">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。1 处不符合要求不得分
        </label>
    </td>
    <td class="value">
        <select name="lack14">
            
            <option value="Y" <c:if test="${scoreDetail.lack14=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack14=='N' || scoreDetail.lack14==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score14" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score14}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        8.不随意留顶煤、底煤开采，留顶煤、托夹矸开采时，
        制定专项措施
        </label>
    </td>
    <td><label class="Validform_label" name="lack15">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。不符合
        要求1处扣 0.5 分留
        顶煤、托夹矸回采时无
        专项措施不得分
        </label>
    </td>
    <td class="value">
        <select name="lack15">
            
            <option value="Y" <c:if test="${scoreDetail.lack15=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack15=='N' || scoreDetail.lack15==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score15" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score15}" >
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
        9.工作面因顶板破碎或分层开采，需要铺设假顶时，按
        照作业规程的规定执行
        </label>
    </td>
    <td><label class="Validform_label" name="lack16">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。不符合
        要求1处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack16">
            
            <option value="Y" <c:if test="${scoreDetail.lack16=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack16=='N' || scoreDetail.lack16==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score16" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score16}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
       10.工作面控顶范围内顶底板移近量按采高不大于
       100mm/m；底板松软时，支柱应穿柱鞋，钻底小于100mm；
        工作面顶板不应出现台阶式下沉
        </label>
    </td>
    <td><label class="Validform_label" name="lack17">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。不符合
        要求1处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack17">
            
            <option value="Y" <c:if test="${scoreDetail.lack17=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack17=='N' || scoreDetail.lack17==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score17" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score17}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
       11.坚持开展工作面工程质量、顶板管理、规程落实情
        况的班评估工作，记录齐全，并放置在井下指定地点
        </label>
    </td>
    <td><label class="Validform_label" name="lack18">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。未进行
        班评估不得分记录不
        符合要求的1处扣0.5
        分
        </label>
    </td>
    <td class="value">
        <select name="lack18">
            
            <option value="Y" <c:if test="${scoreDetail.lack18=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack18=='N' || scoreDetail.lack18==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score18" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score18}" >
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
    <td rowspan="4">
        <label class="Validform_label">安全<br/>出口<br/>与端<br/>头支<br/>护</label>
    </td>
    <td class = "td-padding">
    	<label class="Validform_label">
       1.工作面安全出口畅通，人行道宽度不小于0.8m，综采
        （放）工作面安全出口高度不低于1.8m，其他工作面不
        低于1.6m。工作面两端第一组支架与巷道支护间距不大
        于0.5m，单体支柱初撑力符合《煤矿安全规程》规定
        </label>
    </td>
    <td><label class="Validform_label" name="lack19">4</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。1 处不符合要
        求不得分
        </label>
    </td>
    <td class="value">
        <select name="lack19">
            
            <option value="Y" <c:if test="${scoreDetail.lack19=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack19=='N' || scoreDetail.lack19==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score19" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score19}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        2. 条件适宜时，使用工作面端头支架和两巷超前支护
        液压支架
        </label>
    </td>
    <td><label class="Validform_label" name="lack20">1</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。1 处不符合要
        求不得分
        </label>
    </td>
    <td class="value">
        <select name="lack20">
            
            <option value="Y" <c:if test="${scoreDetail.lack20=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack20=='N' || scoreDetail.lack20==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score20" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score20}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        3.进、回风巷超前支护距离不小于20m，支柱柱距、排
        距允许偏差不大于100mm，支护形式符合作业规程规定；
        进、回风巷与工作面放顶线放齐（沿空留巷除外），控
        顶距应在作业规程中规定；挡矸有效
        </label>
    </td>
    <td><label class="Validform_label" name="lack21">4</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。超前支
        护距离不符合要求不
        得分，其他1处不符合
        要求扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack21">
            
            <option value="Y" <c:if test="${scoreDetail.lack21=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack21=='N' || scoreDetail.lack21==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score21" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score21}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        4.架棚巷道超前替棚距离、锚杆、锚索支护巷道退锚距
        离符合作业规程规定。
        </label>
    </td>
    <td><label class="Validform_label" name="lack22">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。不符合
        要求1处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack22">
            
            <option value="Y" <c:if test="${scoreDetail.lack22=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack22=='N' || scoreDetail.lack22==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score22" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score22}" >
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
    <td rowspan="5">
        <label class="Validform_label">安全<br/>设施</label>
    </td>
    <td class = "td-padding">
    	<label class="Validform_label">
       1.各转载点有喷雾灭尘装置，带式输送机机头、乳化液
        泵站、配电点等场所消防设施齐全
        </label>
    </td>
    <td><label class="Validform_label" name="lack23">3</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。不符合
        要求1处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack23">
            
            <option value="Y" <c:if test="${scoreDetail.lack23=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack23=='N' || scoreDetail.lack23==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score23" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score23}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        2.设备转动外露部位、溜煤眼及煤仓上口等人员通过的
        地点有可靠的安全防护设施
        </label>
    </td>
    <td><label class="Validform_label" name="lack24">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。1 处不符合要
        求不得分
        </label>
    </td>
    <td class="value">
        <select name="lack24">
            
            <option value="Y" <c:if test="${scoreDetail.lack24=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack24=='N' || scoreDetail.lack24==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score24" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score24}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        3.单体液压支柱有防倒措施；工作面倾角大于15º时，
        液压支架有防倒、防滑措施，其他设备有防滑措施；倾
        角大于25º时，有防止煤（矸）窜出伤人的措施
        </label>
    </td>
    <td><label class="Validform_label" name="lack25">3</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。不符合要求1
        处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack25">
            
            <option value="Y" <c:if test="${scoreDetail.lack25=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack25=='N' || scoreDetail.lack25==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>
    <td class="value" style="max-width:150px;">
        <input  name="score25" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score25}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        4.行人通过的输送机机尾设盖板；输送机行人跨越处有
        过桥；工作面刮板输送机信号闭锁符合要求
        </label>
    </td>
    <td><label class="Validform_label" name="lack26">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。不符合要求1
        处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack26">
            
            <option value="Y" <c:if test="${scoreDetail.lack26=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack26=='N' || scoreDetail.lack26==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score26" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score26}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        5.破碎机安全防护装置齐全有效
        </label>
    </td>
    <td><label class="Validform_label" name="lack27">1</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。不符合要求不
        得分
        </label>
    </td>
    <td class="value">
        <select name="lack27">
            
            <option value="Y" <c:if test="${scoreDetail.lack27=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack27=='N' || scoreDetail.lack27==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score27" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score27}" >
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
    <td rowspan="10">
        <label class="Validform_label">四、<br/>机<br/>电<br/>设<br/>备<br/>(20分)</label>
    </td>
    <td rowspan="3">
        <label class="Validform_label">设备<br/>选型</label>
    </td>
    <td class = "td-padding">
    	<label class="Validform_label">
       1.支护装备（泵站、支架及支柱）满足设计要求
       </label>
    </td>
    <td><label class="Validform_label" name="lack28">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。不符合
        要求不得分
        </label>
    </td>
    <td class="value">
        <select name="lack28">
            
            <option value="Y" <c:if test="${scoreDetail.lack28=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack28=='N' || scoreDetail.lack28==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score28" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score28}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        2.生产装备选型、配套合理，满足设计生产能力需要
        </label>
    </td>
    <td><label class="Validform_label" name="lack29">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。不符合
        要求不得分
        </label>
    </td>
    <td class="value">
        <select name="lack29">
            
            <option value="Y" <c:if test="${scoreDetail.lack29=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack29=='N' || scoreDetail.lack29==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score29" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score29}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        3.电气设备满足生产、支护装备安全运行的需要
        </label>
    </td>
    <td><label class="Validform_label" name="lack30">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。不符合
        要求不得分
        </label>
    </td>
    <td class="value">
        <select name="lack30">
            
            <option value="Y" <c:if test="${scoreDetail.lack30=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack30=='N' || scoreDetail.lack30==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score30" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score30}" >
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
    <td rowspan="7">
        <label class="Validform_label">设备<br/>管理</label>
    </td>
    <td class = "td-padding">
    	<label class="Validform_label">
       1.泵站：<br/>
        （1）乳化液泵站完好，综采工作面乳化液泵压力不小
        于 30MPa，炮采、高档普采工作面乳化液泵压力不小于
       18MPa，乳化液（浓缩液）浓度符合产品技术标准要求，
        并在作业规程中明确规定；<br/>
        （2）液压系统无漏、窜液，部件无缺损，管路无挤压；
        注液枪完好，控制阀有效；<br/>
        （3）采用电液阀控制时，净化水装置运行正常，水质、
        水量满足要求；<br/>
        （4）各种液压设备及辅件合格、齐全、完好，控制阀
        有效，耐压等级符合要求，操纵阀手把有限位装置<br/>
        </label>
    </td>
    <td><label class="Validform_label" name="lack31">4</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。不符合
        要求1处扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack31">
            
            <option value="Y" <c:if test="${scoreDetail.lack31=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack31=='N' || scoreDetail.lack31==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score31" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score31}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        2.采（刨）煤机：<br/>
        （1）采（刨）煤机完好；<br/>
        （2）采煤机有停止工作面刮板输送机的闭锁装置；<br/>
        （3）采（刨）煤机设置甲烷断电仪或者便携式甲烷检
        测报警仪，且灵敏可靠；<br/>
        （4）采（刨）煤机截齿、喷雾装置、冷却系统符合规
        定，内外喷雾有效；<br/>
        （5）采（刨）煤机电气保护齐全可靠；<br/>
        （6）刨煤机工作面至少每隔 30m 装设能随时停止刨头
        和刮板输送机的装置或向刨煤机司机发送信号的装置；
        有刨头位置指示器；<br/>
        （7）大中型采煤机使用软启动控制装置；<br/>
        （8）采煤机具备遥控控制功能
        </label>
    </td>
    <td><label class="Validform_label" name="lack32">3</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。第
        （1）～（6）项不符合
        要求1处扣 0.5 分，第
        （7）~（8）项不符合
        要求1处扣 0.1 分
        </label>
    </td>
    <td class="value">
        <select name="lack32">
            
            <option value="Y" <c:if test="${scoreDetail.lack32=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack32=='N' || scoreDetail.lack32==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score32" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score32}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        3.刮板输送机、转载机、破碎机：<br/>
        （1）刮板输送机、转载机、破碎机完好；<br/>
        （2）使用刨煤机采煤、工作面倾角大于12°时，配套
        的刮板输送机装设防滑、锚固装置；<br/>
        （3）刮板输送机机头、机尾固定可靠；<br/>
        （4）刮板输送机、转载机、破碎机的减速器与电动机
        软连接或采用软启动控制，液力偶合器不使用可燃性传
        动介质（调速型液力偶合器不受此限），使用合格的易
        熔塞和防爆片；<br/>
        （5）刮板输送机安设有能发出停止和启动信号的装置；<br/>
        （6）刮板输送机、转载机、破碎机电气保护齐全可靠，
        电机采用水冷方式时，水量、水压符合要求
        </label>
    </td>
    <td><label class="Validform_label" name="lack33">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。不符合
        要求1处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack33">
            
            <option value="Y" <c:if test="${scoreDetail.lack33=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack33=='N' || scoreDetail.lack33==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score33" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score33}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        4.带式输送机：<br/>
        （1）带式输送机完好，机架、托辊齐全完好，胶带不
        跑偏；<br/>
        （2）带式输送机电气保护齐全可靠；<br/>
        （3）带式输送机的减速器与电动机采用软连接或软启
        动控制，液力偶合器不使用可燃性传动介质（调速型液
        力偶合器不受此限），并使用合格的易熔塞和防爆片；<br/>
        （4）使用阻燃、抗静电胶带，有防打滑、防堆煤、防
        跑偏、防撕裂保护装置，有温度、烟雾监测装置，有自
        动洒水装置；<br/>
        （5）带式输送机机头、机尾固定牢固，机头有防护栏，
        有防灭火器材，机尾使用挡煤板、有防护罩。在大于16°
        的斜巷中带式输送机设置防护网，并采取防止物料下
        滑、滚落等安全措施；<br/>
        （6）连续运输系统有连锁、闭锁控制装置，全线安设
        有通信和信号装置；<br/>
        （7）上运式带式输送机装设防逆转装置和制动装置，
        下运式带式输送机装设软制动装置和防超速保护装置；<br/>
        （8）带式输送机安设沿线急停装置；<br/>
        （9）带式输送机系统宜采用无人值守集中综合智能控
        制方式
        </label>
    </td>
    <td><label class="Validform_label" name="lack34">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场和资料。第
        （1）～（8）项不符合
        要求1处扣 0.5 分，第
        （9）项不符合要求扣
        0.1 分
        </label>
    </td>
    <td class="value">
        <select name="lack34">
            
            <option value="Y" <c:if test="${scoreDetail.lack34=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack34=='N' || scoreDetail.lack34==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score34" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score34}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        5.辅助运输设备完好，制动可靠，安设符合要求，声光
        信号齐全；轨道铺设符合要求；钢丝绳及其使用符合《煤
        矿安全规程》要求，检验合格
        </label>
    </td>
    <td><label class="Validform_label" name="lack35">1</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。不符合要求1
        处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack35">
            
            <option value="Y" <c:if test="${scoreDetail.lack35=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack35=='N' || scoreDetail.lack35==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score35" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score35}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        6.通信系统畅通可靠，工作面每隔15m 及变电站、乳化液泵站、各转载点有语音通信装置；监测、监控设备运
        行正常，安设位置符合规定
        </label>
    </td>
    <td><label class="Validform_label" name="lack36">1</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。不符合要求1
        处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack36">
            
            <option value="Y" <c:if test="${scoreDetail.lack36=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack36=='N' || scoreDetail.lack36==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score36" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score36}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        7.小型电器排列整齐，干净整洁，性能完好；机电设备表面干净，无浮煤积尘；移动变电站完好；接地线安设规范；开关上架，电气设备不被淋水；移动电缆有吊挂、拖曳装置
    	</label>
    </td>
    <td><label class="Validform_label" name="lack37">1</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。1 处不符合要
        求不得分
        </label>
    </td>
    <td class="value">
        <select name="lack37">
            
            <option value="Y" <c:if test="${scoreDetail.lack37=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack37=='N' ||scoreDetail.lack37==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score37" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score37}" >
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
    <td rowspan="4">
        <label class="Validform_label">五、<br/>文<br/>明<br/>生<br/>产<br/>（10分）</label>
    </td>
    <td rowspan="3">
        <label class="Validform_label">面外<br/>环境</label>
    </td>
    <td class = "td-padding">
    	<label class="Validform_label">
       1.电缆、管线吊挂整齐，泵站、休息地点、油脂库、带式输送机机头和机尾等场所有照明；图牌板（工作面布置图、设备布置图、通风系统图、监测通信系统图、供电系统图、工作面支护示意图、正规作业循环图表、避灾路线图；炮采工作面增设炮眼布置图、爆破说明书等）齐全、清晰整洁；巷道每隔100m 设置醒目的里程标志
    	</label>
    </td>
    <td><label class="Validform_label" name="lack38">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。不符合要求1
        项扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack38">
            
            <option value="Y" <c:if test="${scoreDetail.lack38=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack38=='N' || scoreDetail.lack38==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score38" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score38}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        2.进、回风巷支护完整，无失修巷道；设备、物料与胶带、轨道等的安全距离符合规定，设备上方与顶板距离不小于 0.3m
        </label>
    </td>
    <td><label class="Validform_label" name="lack39">3</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。不符合要求1项扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack39">
            
            <option value="Y" <c:if test="${scoreDetail.lack39=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack39=='N' || scoreDetail.lack39==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score39" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score39}" >
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
    <td class = "td-padding">
    	<label class="Validform_label">
        3.巷道及硐室底板平整，无浮碴及杂物、无淤泥、无积水；管路、设备无积尘；物料分类码放整齐，有标志牌，设备、物料放置地点与通风设施距离大于 5m
        </label>
    </td>
    <td><label class="Validform_label" name="lack40">2</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。不符合要求1项扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack40">
            
            <option value="Y" <c:if test="${scoreDetail.lack40=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack40=='N' || scoreDetail.lack40==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>
    <td class="value" style="max-width:150px;">
        <input  name="score40" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score40}" >
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
    <td>
        <label class="Validform_label">面内<br/>环境</label>
    </td>
    <td class = "td-padding">
    	<label class="Validform_label">
        工作面内管路敷设整齐，支架内无浮煤、积矸，照明符合规定。
        </label>
    </td>
    <td><label class="Validform_label" name="lack41">3</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场。不符合要求1项扣1分
        </label>
    </td>
    <td class="value">
        <select name="lack41">
            
            <option value="Y" <c:if test="${scoreDetail.lack41=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack41=='N' || scoreDetail.lack41==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score41" type="text" style="width:150px" class="inputxt"  value="${scoreDetail.score41}" >
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
    <td  style="text-align: center;" colspan="2">
        <label class="Validform_label">
            <font color="red">*</font>汇总得分
        </label>
    </td>
    <td class="value" style="text-align: left;" colspan="6">
        <input id="ssaSumScore" name="ssaSumScore" type="text" style="width:150px" class="inputxt" value="${sfMineStandardAssessPage.ssaSumScore}" datatype="/^100(\.0+)?$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内数字!">
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
