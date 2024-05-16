<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>地质灾害防治与测量录入</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
    <script type="text/javascript">
        //编写自定义JS代码
        $(function() {
            //$('#tt').tabs('select','煤矿防治冲击地压');
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

        /*Author:  张赛超*/
        .inputxt{
            margin-top: 20px;
        }
    </style>
</head>
<body>
<%--<c:if test="${from ne 'mineAssess'}">--%>
    <%--<!-- from :mineAssess 页面请求来源于矿井考核自评汇总的时候不显示该面包屑，否则显示面包屑 -->--%>
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->井工煤矿->地质灾害防治与测量</div>--%>
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
        <td colspan='7' align="center" style="font-size:20px;font-weight: bold;">地质灾害防治与测量评分表</td>
    </tr>
    <tr>
        <td class="value" colspan="6" style="text-align: left">
            <label class="Validform_label">
                冲击地压:
            </label>
			<select name="cjdy" id="cjdy">
				<option value="Y" <c:if test="${scoreDetail.cjdy!='N'}">selected="selected"</c:if>>有</option>
				<option value="N" <c:if test="${scoreDetail.cjdy=='N'}">selected="selected"</c:if>>无</option>
			</select>
            <label class="Validform_label">
                水文地质类型:
            </label>
			<select name="swdzlx" id="swdzlx">
				<option value="1" <c:if test="${scoreDetail.swdzlx!='2'}">selected="selected"</c:if>>简单和中等 </option>
				<option value="2" <c:if test="${scoreDetail.swdzlx=='2'}">selected="selected"</c:if>>复杂及以上 </option>
			</select>
		</td>
		<td class="value" style="text-align: right">
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
<div id="tt" class="easyui-tabs" data-options="onSelect:function(t){$('#tt .panel-body').css('width','auto');}">
<div title="技术管理标准化">
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
        一、<br>规<br>章<br>制<br>度<br>（50分）
        	</label>
    </td>
    <td>
        <label class="Validform_label">制度<br/>建设</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        有以下制度：<br/>
        1.地质灾害防治技术管理、预测预报、地测安全办公会议制度；<br/>
        2.地测资料、技术报告审批制度；<br/>
        3.图纸的审批、发放、回收和销毁制度；<br/>
        4.资料收集、整理、定期分析、保管、提供制度；<br/>
        5.隐蔽致灾地质因素普查制度；<br/>
        6.岗位安全生产责任制度;
        </label>
    </td>
    <td><label class="Validform_label" name="lack1_1">15</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。每缺 1 项制度扣 5 分；制度有缺陷 1处扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack1_1">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_1=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_1=='N' ||scoreDetail.lack1_1==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label " style="text-align: left;">
            资料<br/>管理
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        图纸、资料、文件等分类保管，存档管理，电子文档定期备份
        </label>
    </td>
    <td ><label class="Validform_label" name="lack1_2">15</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。
        未分类保管扣5 分;
        存档不齐，每缺1 种扣 3 分;
        电子文档备份不全，每缺 1 种扣2 分
        </label>
    </td>
    <td class="value">
        <select name="lack1_2">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_2=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_2=='N' ||scoreDetail.lack1_2==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_2" type="text" style="width: 150px;" class="inputxt"  value="${scoreDetail.score1_2}" >
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
    <td>
        <label class="Validform_label">岗位<br>规范</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.管理和技术人员掌握相关的岗位职责、管理制度、技术措施；<br/>
        2.现场作业人员严格执行本岗位安全生产责任制，
        掌握本岗位相应的操作规程和安全措施，操作规范，无“三违”行为；<br/>
        3.作业前进行安全确认
        </label>
    </td>
    <td><label class="Validform_label" name="lack1_3">20</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。发现“三违”不得分;
        不执行岗位责任制、不规范操作 1 人次扣 3 分
        </label>
    </td>
    <td class="value">
        <select name="lack1_3">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_3=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_3=='N' ||scoreDetail.lack1_3==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score1_3" type="text" style="width: 150px;" class="inputxt"  value="${scoreDetail.score1_3}" >
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
    <td rowspan="2">
        <label class="Validform_label">
            二、<br/>组<br/>织<br/>保<br/>障<br/>与<br/>装<br/>备<br/>（50分）
        </label>
    </td>
    <td>
        <label class="Validform_label">组织<br/>保障</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        矿井按规定设立负责地质灾害防治与测量部门，配备相关人员
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1_4">
            25
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。未按要求设置部门不得分；
        设置不健全扣 10 分；
        人员配备不能满足要求扣 5 分
        </label>
    </td>
    <td class="value">
        <select name="lack1_4">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_4=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_4=='N' ||scoreDetail.lack1_4==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>
    <td class="value" style="max-width:150px;">
        <input  name="score1_4" type="text" style="width: 150px;" class="inputxt"  value="${scoreDetail.score1_4}" >
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
    <td>
        <label class="Validform_label">装备<br/>管理</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.工器具、装备完好，满足规定和工作需要；<br/>
        2.地质工作至少采用一种有效的物探装备；<br/>
        3.采用计算机制图；<br/>
        4.地测信息系统与上级公司联网并能正常使用<br/>
        </label>
    </td>
    <td><label class="Validform_label" name="lack1_5">25</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        <label class="Validform_label">
            查资料和现场。
            因装备不足或装备落后而影响安全生产的不得分；
            装备不能正常使用 1台扣 2 分；
            无物探装备扣 5 分；
            未采用计算机制图扣 10 分；
            地测信息系统未与上级公司扣 10 分；
            不能正常使用扣 5 分
            </label>
        </label>
    </td>
    <td class="value">
        <select name="lack1_5">
            
            <option value="Y" <c:if test="${scoreDetail.lack1_5=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1_5=='N' ||scoreDetail.lack1_5==null}">selected="selected"</c:if>>否 </option>
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
<div title="煤矿地质标准化">
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
    <td  rowspan="2">
        <label class="Validform_label">
            一、<br>基<br>础<br>工<br>作<br>（20分）
        </label>
    </td>
    <td>
        <label class="Validform_label">地质<br/>观测<br/>与分<br/>析</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.按《煤矿地质工作规定》要求进行地质观测与资料编录、综合分析；<br/>
        2.综合分析资料能满足生产工作需要
        </label>
    </td>
    <td ><label class="Validform_label" name="lack2_1">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。未开展地质观测、无观测资料或综合分析资料不能满足生产需要不得分，
        资料无针对性扣 5 分，
        地质观测与资料编录不及时、内容不完
        整、原始记录不规范 1 处扣 2
        分
        </label>
    </td>
    <td class="value">
        <select name="lack2_1">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_1=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_1=='N' || scoreDetail.lack2_1==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">
            致灾<br/>因素<br/>普查<br/>地质<br/>类型<br/>划分
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.按规定查明影响煤矿安全生产的各种隐蔽致灾地质因素；<br/>
        2.按“就高不就低”原则划分煤矿地质类型，
        出现影响煤矿地质类型划分的突水和煤与瓦斯突出等地质条件变化时，在 1 年内重新进行地质类型划分
        </label>
    </td>
    <td><label class="Validform_label" name="lack2_2">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。矿井隐蔽致灾地质因素普查不全面，每缺 1 类扣 5分；
        普查方法不当扣 2 分；
        未按原则划分煤矿地质类型扣5分；
        未及时划分煤矿地质类型不得分
        </label>
    </td>
    <td class="value">
        <select name="lack2_2">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_2=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_2=='N' || scoreDetail.lack2_2==null}">selected="selected"</c:if>>否 </option>
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
    <td  rowspan="5">
        <label class="Validform_label">
            二、<br/>基<br/>础<br/>资<br/>料<br/>（35分）
        </label>
    </td>
    <td>
        <label class="Validform_label">地质<br/>报告</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        有满足不同生产阶段要求的地质报告，按期修编，并按要求审批
        </label>
    </td>
    <td ><label class="Validform_label" name="lack2_3">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。地质类型划分报告、生产地质报告、隐蔽致灾地质因素普查报告不全，每缺 1 项扣 3 分；
        地质报告未按期修编1 次扣 3 分；
        未按要求审批 1次扣 2 分
        </label>
    </td>
    <td class="value">
        <select name="lack2_3">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_3=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_3=='N' || scoreDetail.lack2_3==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">地质<br/>说明<br/>书</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        采掘工程设计施工前，按时提交由总工程师批准
        的采区地质说明书、回采工作面地质说明书、掘
        进工作面地质说明书；井巷揭煤前，探明煤层厚
        度、地质、构造、瓦斯地质、水文地质及顶底板
        等地质条件，编制揭煤地质说明书
        </label>
    </td>
    <td><label class="Validform_label" name="lack2_4">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。资料不全，每缺 1 项
        扣 2 分；地质说明书未经批准
        扣 2 分；文字、原始资料、图
        纸数字不符，内容不全，1 处
        扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack2_4">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_4=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_4=='N' || scoreDetail.lack2_4==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">采后<br/>总结</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        采煤工作面和采区结束后，按规定进行采后总结
        </label>
    </td>
    <td ><label class="Validform_label" name="lack2_5">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。采后总结不全，每缺
        1 份扣 3 分，内容不符合规定
        1 次扣 3 分
        </label>
    </td>
    <td class="value">
        <select name="lack2_5">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_5=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_5=='N' || scoreDetail.lack2_5==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">台账<br/>图纸</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.有《煤矿地质工作规定》要求必备的台账、图件等地质基础资料；<br/>
        2.图件内容符合《煤矿地质测量图技术管理规定》要求，图种齐全有电子文档
        </label>
    </td>
    <td><label class="Validform_label" name="lack2_6">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。台账不全，每缺 1 种扣 3 分；
        台账内容不全不清，1 处扣 1 分；
        检查全部地质图纸，图种不全的，每缺 1 种扣5 分；
        图幅不全扣 2 分；无电子文档扣 2 分；
        未及时更新 1处扣 1 分，图例、注记不规范1 处扣 1 分；
        素描图不全，每缺 1 处扣 3 分，要素内容不全1 处扣 1 分；
        日常用图中采掘工程及地质内容未及时填绘的 1 处扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack2_6">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_6=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_6=='N' || scoreDetail.lack2_6==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">原始<br/>记录</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.有专用原始记录本，分档按时间顺序保存；<br/>
        2.记录内容齐全，字迹、草图清楚
        </label>
    </td>
    <td><label class="Validform_label" name="lack2_7">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。记录本不全，每缺 1种扣 3 分；
        其他 1 处不符合要求扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack2_7">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_7=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_7=='N' || scoreDetail.lack2_7==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">
            三、<br/>预<br/>测<br/>预<br/>报<br/>（10分）
        </label>
    </td>
    <td>
        <label class="Validform_label">地质<br/>预报</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        地质预报内容符合《煤矿地质工作规定》要求:
        内容齐全，有年报、月报和临时性预报，并以年
        为单位装订成册，归档保存
        </label>
    </td>
    <td><label class="Validform_label" name="lack2_8">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。采掘地点预报不全，
        每缺 1 个采掘工作面扣 5 分，
        预报内容不符合规定、预报有
        疏漏、失误 1 处扣 1 分，未经
        批准 1 次扣 2 分；未预报造成
        工程事故本项不得分
        </label>
    </td>
    <td class="value">
        <select name="lack2_8">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_8=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_8=='N' || scoreDetail.lack2_8==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">
            四、<br/>瓦<br/>斯<br/>地<br/>质<br/>（15分）
        </label>
    </td>
    <td>
        <label class="Validform_label">瓦斯<br/>地质</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.突出矿井及高瓦斯矿井每年编制并至少更新 1
        次各主采煤层瓦斯地质图，规范填绘瓦斯赋存采掘进度、煤层赋存条件、地质构造、被保护范围
        等内容，图例符号绘制统一，字体规范；<br/>
        2.采掘工作面距保护边缘不足 50m 前，编制发放
        临近未保护区通知单，按规定揭露煤层及断层，
        探测设计及探测报告及时无误；<br/>
        3.根据瓦斯地质图及时进行瓦斯地质预报
        </label>
    </td>
    <td><label class="Validform_label" name="lack2_9">15</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。瓦斯预报错误造成工
        程事故或误揭煤层及断层的
        不得分；未编制下发临近未保
        护区通知单的，1 次扣 2 分；
        未编制揭煤探测设计及探测
        报告扣 5 分；其他 1 项不符合
        要求扣 1 分；
        </label>
    </td>
    <td class="value">
        <select name="lack2_9">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_9=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_9=='N' || scoreDetail.lack2_9==null}">selected="selected"</c:if>>否 </option>
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
    <td rowspan="3">
        <label class="Validform_label">
            五、<br/>资<br/>源<br/>回<br/>收<br/>及<br/>储<br/>量<br/>管<br/>理<br/>（20分）
        </label>
    </td>
    <td>
        <label class="Validform_label">储量<br/>估算<br/>图</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        有符合《矿山储量动态管理要求》规定的各种图纸，内容符合储量、损失量计算图要求
        </label>
    </td>
    <td><label class="Validform_label" name="lack2_10">6</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。图种不全，每缺 1 种扣 2 分，其他 1 项不符合要求扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack2_10">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_10=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_10=='N' || scoreDetail.lack2_10==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">储量<br/>估算<br/>成果<br/>台账</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        有符合《矿山储量动态管理要求》规定的储量计
        算台账和损失量计算台账，种类齐全、填写及时、
        准确，有电子文档
        </label>
    </td>
    <td ><label class="Validform_label" name="lack2_11">6</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。每种台账至少抽查 1
        本，台账不全或未按规定及时
        填写的，每缺 1 种扣 2 分；台
        账内容不全、数据前后矛盾
        的，1 处扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack2_11">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_11=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_11=='N' || scoreDetail.lack2_11==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">统计<br/>管理</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.储量动态清楚，损失量及构成原因等准确；<br/>
        2.储量变动批文、报告完整，按时间顺序编号、
        合订；<br/>
        3.定期分析回采率，能如实反映储量损失情况；<br/>
        4.采区、工作面结束有损失率分析报告；<br/>
        5.每半年进行 1 次全矿回采率总结；<br/>
        6.三年内丢煤通知单完整无缺，按时间顺序编
        号、合订；<br/>
        7.采区、工作面回采率符合要求
        </label>
    </td>
    <td><label class="Validform_label" name="lack2_12">8</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。回采率达不到要求不得分，其他 1 项不符合要求扣2 分
        </label>
    </td>
    <td class="value">
        <select name="lack2_12">
            
            <option value="Y" <c:if test="${scoreDetail.lack2_12=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2_12=='N' || scoreDetail.lack2_12==null}">selected="selected"</c:if>>否 </option>
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
<div title="煤矿测量标准化">
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
    <td  rowspan="5">
        <label class="Validform_label">
            一、<br>基<br>础<br>工<br>作<br>（40分）
        </label>
    </td>
    <td>
        <label class="Validform_label">控制<br/>系统</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.测量控制系统健全，精度符合《煤矿测量规程》要求；<br/>
        2.及时延长井下基本控制导线和采区控制导线
        </label>
    </td>
    <td><label class="Validform_label" name="lack3_1">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。控制点精度不
        符合要求 1 处扣 1 分；井下控
        制导线延长不及时 1 处扣 2
        分；未按规定敷设相应等级导
        线或导线精度达不到要求的，
        1 处扣 2 分
        </label>
    </td>
    <td class="value">
        <select name="lack3_1">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_1=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_1=='N' || scoreDetail.lack3_1==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">测量<br/>重点</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.贯通、开掘、放线变更、停掘停采线、过断层、
        冲击地压带、突出区域、过空间距离小于巷高或
        巷宽 4 倍的相邻巷道等重点测量工作，执行通知单制度；<br/>
        2.通知单按规定提前发送到施工单位、有关人员和相关部门
        </label>
    </td>
    <td ><label class="Validform_label" name="lack3_2">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。贯通及过巷通知单未
        按要求发送、开掘及停头通知
        单发放不及时的，1 次扣 5 分；
        巷道掘进到特殊地段时漏发
        通知单的，1 次扣 3 分；其他
        通知单，1 处错误扣 2 分漏
        发扣 3 分
        </label>
    </td>
    <td class="value">
        <select name="lack3_2">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_2=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_2=='N' || scoreDetail.lack3_2==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">
            贯通<br/>精度
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        贯通精度满足设计要求,两井贯通和一井内3000m 以上贯通测量工程应有设计，
        并按规定审批和总结
        </label>
    </td>
    <td><label class="Validform_label" name="lack3_3">8</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。两井间贯通或
        3000m以上贯通测量工程未编
        制贯通测量设计书或未经审批、
        没有总结的，每缺 1 项扣3 分；
        贯通后重要方向误差超过允许偏差值的，1 处扣 5 分
        </label>
    </td>
    <td class="value">
        <select name="lack3_3">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_3=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_3=='N' || scoreDetail.lack3_3==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">中腰<br/>线标<br/>定</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
    	中腰线标定符合《煤矿测量规程》要求 
        </label>
    </td>
    <td><label class="Validform_label" name="lack3_4">6</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。掘进方向偏差超过限差 1 处扣 3 分
        </label>
    </td>
    <td class="value">
        <select name="lack3_4">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_4=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_4=='N' || scoreDetail.lack3_4==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">原始<br/>记录<br/>及成<br/>果台<br/>账</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.导线测量、水准测量、联系测量、井巷施工标定、
        陀螺定向测量等外业记录本齐全,并分档按
        时间顺序保存,记录内容齐全，书写工整无涂改；<br/>
        2.测量成果计算资料和台账齐全
        </label>
    </td>
    <td><label class="Validform_label" name="lack3_5">6</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。无专用记录本扣 2 分；
        无目录、索引、编号，导致查
        找困难扣 1 分；记录本不全，
        每缺 1 种扣 3 分，无编号 1 处
        扣 1 分；误差超限 1 处扣 2 分；
        原始记录内容不全1处扣1分；
        无测量成果计算资料和标定解
        算台账扣 5 分，测量成果计算
        资料和标定解算台账中数据不
        全或错误的，1 处扣 2 分
        </label>
    </td>
    <td class="value">
        <select name="lack3_5">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_5=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_5=='N' || scoreDetail.lack3_5==null}">selected="selected"</c:if>>否 </option>
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
    <td rowspan="2">
        <label class="Validform_label">二、<br/>基<br/>本<br/>矿<br/>图<br/>(40分)</label>
    </td>
    <td>
        <label class="Validform_label">测量<br/>矿图</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        有采掘工程平面图、工业广场平面图、井上下对
        照图、井底车场图、井田区域地形图、保安煤柱
        图、井筒断面图、主要巷道平面图等《煤矿测量
        规程》规定的基本矿图
        </label>
    </td>
    <td><label class="Validform_label" name="lack3_6">20</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。图种不全，每缺 1 种扣 4 分
        </label>
    </td>
    <td class="value">
        <select name="lack3_6">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_6=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_6=='N' || scoreDetail.lack3_6==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">矿图<br/>要求</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.基本矿图采用计算机绘制，内容、精度符合《煤矿测量规程》要求；<br/>
        2.图符、线条、注记等符合《煤矿地质测量图例》要求<br/>
        3.图面清洁、层次分明，色泽准确适度，文字清晰，并按图例要求的字体进行注记；<br/>
        4.采掘工程平面图每月填绘 1 次，井上下对照图每季度填绘 1 次，图面表达和注记无矛盾；<br/>
        5.数字化底图至少每季度备份 1 次
        </label>
    </td>
    <td><label class="Validform_label" name="lack3_7">20</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。图符不符合要求 1 种
        扣 2 分；图例、注记不规范 1
        处扣 0.5 分；填绘不及时 1 处
        扣 2 分；无数字化底图或未按
        时备份数据扣 2 分
        </label>
    </td>
    <td class="value">
        <select name="lack3_7">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_7=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_7=='N' || scoreDetail.lack3_7==null}">selected="selected"</c:if>>否 </option>
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
    <td rowspan="2">
        <label class="Validform_label">
            三、<br/>沉<br/>陷<br/>观<br/>测<br/>控<br/>制<br/>（20分）
        </label>
    </td>
    <td>
        <label class="Validform_label">地表<br/>移动</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.进行地面沉陷观测；<br/>
        2.提供符合矿井情况的有关岩移参数
        </label>
    </td>
    <td><label class="Validform_label" name="lack3_8">15</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。未进行地面沉
        陷观测扣 10 分，岩移参数提
        供不符合要求 1 处扣 3 分
        </label>
    </td>
    <td class="value">
        <select name="lack3_8">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_8=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_8=='N' || scoreDetail.lack3_8==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">资料<br/>台账</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.及时填绘采煤沉陷综合治理图；<br/>
        2.建立地表塌陷裂缝治理台账、村庄搬迁台账；<br/>
        3.绘制矿井范围内受采动影响土地塌陷图表
        </label>
    </td>
    <td><label class="Validform_label" name="lack3_9">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。不符合要求 1 处扣 1分
        </label>
    </td>
    <td class="value">
        <select name="lack3_9">
            
            <option value="Y" <c:if test="${scoreDetail.lack3_9=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3_9=='N' || scoreDetail.lack3_9==null}">selected="selected"</c:if>>否 </option>
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
<div title="煤矿防治水标准化">
<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
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
            一、<br>水<br>文<br>地<br>质<br>基<br>础<br>工<br>作<br>（45分）
        </label>
    </td>
    <td>
        <label class="Validform_label">基础<br/>工作</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.按《煤矿防治水规定》要求进行水文地质观测；<br/>
        2.开展水文地质类型划分工作，发生重大及以上
        突（透）水事故后，恢复生产前应重新确定；<br/>
        3.对井田范围内及周边矿井采空区位置和积水情
        况进行调查分析并做好记录，<br/>制定相应的安全技术
        措施
        </label>
    </td>
    <td><label class="Validform_label" name="lack4_1">15</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。水文地质观测
        不符合《煤矿防治水规定》1
        处扣 2 分；未及时划分水文地
        质类型扣 5 分；采空区有 1 处
        积水情况不清楚扣 2 分；未制
        定相应的安全技术措施扣 5分
        </label>
    </td>
    <td class="value">
        <select name="lack4_1">
            
            <option value="Y" <c:if test="${scoreDetail.lack4_1=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack4_1=='N' || scoreDetail.lack4_1==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score4_1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_1}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark4_1" rows="3" class="inputxt">${scoreDetail.remark4_1}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">
            基础<br/>资料
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.有井上、井下和不同观测内容的专用原始记录
        本，记录规范，保存完好；<br/>
        2.按《煤矿防治水规定》要求编制水文地质报告、
        矿井水文地质类型划分报告、水文地质补充勘探
        报告，按规定修编、审批水文地质报告；<br/>
        3.建立防治水基础台账（含水文钻孔管理台账）
        和计算机数据库，并每季度修正 1 次
        </label>
    </td>
    <td><label class="Validform_label" name="lack4_2">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。每缺1种报告扣4分，
        每缺 1 种台账扣 2 分；无水文
        钻孔管理记录或台账记录不
        全，1 处扣 2 分；其他 1 处不
        符合要求扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack4_2">
            
            <option value="Y" <c:if test="${scoreDetail.lack4_2=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack4_2=='N' || scoreDetail.lack4_2==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score4_2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_2}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark4_2" rows="3" class="inputxt">${scoreDetail.remark4_2}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">
            水文<br/>图纸
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.绘制有矿井充水性图、矿井涌水量与各种相关
        因素动态曲线图、矿井综合水文地质图、矿井综
        合水文地质柱状图、矿井水文地质剖面图，图种
        齐全有电子文档，图纸内容全面、准确；<br/>
        2.在采掘工程平面图和充水性图上准确标明井
        田范围内及周边采空区的积水范围、积水量、积
        水标高、积水线、探水线、警戒线
        </label>
    </td>
    <td><label class="Validform_label" name="lack4_3">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。每缺1种图纸扣3分；
        图纸电子文档缺 1 种扣 2 分；
        图种内容有矛盾的 1 处扣 1分；
        积水区及其参数未在采掘工程平面图和充水性图上标明的 1 处扣 5 分；
        参数标注有误的 1 处扣 2 分
        </label>
    </td>
    <td class="value">
        <select name="lack4_3">
            
            <option value="Y" <c:if test="${scoreDetail.lack4_3=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack4_3=='N' || scoreDetail.lack4_3==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score4_3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_3}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark4_3" rows="3" class="inputxt">${scoreDetail.remark4_3}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">水害<br/>预报</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.年报、月报、临时预报应包含突水危险性评价
        和水害处理意见等内容，预报内容齐全、下达及
        时；<br/>
        2.在水害威胁区域进行采掘前，应查清水文地质
        条件，编制水文地质情况分析报告，报告编制、
        审批程序符合规定；<br/>
        3.水文地质类型中等及以上的矿井，年初编制年
        度水害分析预测表及水害预测图；<br/>
        4.编制矿井中长期防治水规划及年度防治水计
        划，并组织实施
        </label>
    </td>
    <td><label class="Validform_label" name="lack4_4">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。因预报失误造成事故
        不得分，预报缺 1 次扣 2 分，
        预报不能指导生产的 1 次扣 2
        分；图表不符、描述不准确 1
        处扣 1 分；预报下发不及时 1
        次扣 2 分；审批、接收手续不
        齐全 1 次扣 1 分；突水危险性
        评价缺 1 次扣 2 分；无年度水
        害分析图表扣 2 分；无中长期
        防治水规划或年度防治水计
        划扣 3 分未组织实施扣 5 分
        </label>
    </td>
    <td class="value">
        <select name="lack4_4">
            
            <option value="Y" <c:if test="${scoreDetail.lack4_4=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack4_4=='N' || scoreDetail.lack4_4==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score4_4" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_4}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark4_4" rows="3" class="inputxt">${scoreDetail.remark4_4}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="5">
        <label class="Validform_label">二、<br/> 防<br/>治<br/>水<br/>工<br/>程<br/>(50分)</label>
    </td>
    <td>
        <label class="Validform_label">系统<br/>建立</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.矿井防排水系统健全，能力满足《煤矿防治水
        规定》要求；<br/>
        2.水文地质类型复杂、极复杂的矿井建立水文动
        态观测系统
        </label>
    </td>
    <td ><label class="Validform_label" name="lack4_5">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。防排水系统达
        不到规定要求不得分；未按规
        定建观测系统不得分系统运
        行不正常扣 5 分
        </label>
    </td>
    <td class="value">
        <select name="lack4_5">
            
            <option value="Y" <c:if test="${scoreDetail.lack4_5=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack4_5=='N' || scoreDetail.lack4_5==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>
    <td class="value" style="max-width:150px;">
        <input  name="score4_5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_5}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark4_5" rows="3" class="inputxt">${scoreDetail.remark4_5}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">技术<br/>要求</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.井上、井下各项防治水工程有设计方案和施工
        安全技术措施，并按程序审批，工程结束提交总
        结报告及验收报告；<br/>
        2.制定采掘工作面超前探放水专项安全技术措
        施，探测资料和记录齐全；<br/>
        3.探放水工程设计有单孔设计；井下探放水采用
        专用钻机，由专业人员和专职探放水队伍施工；<br/>
        4.对井田内井下和地面的所有水文钻孔每半年
        进行 1 次全面排查，记录详细；<br/>
        5.防水煤柱留设按规定程序审批；<br/>
        6.制定并严格执行雨季“三防”措施
        </label>
    </td>
    <td ><label class="Validform_label" name="lack4_6">15</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。各类防治水工
        程设计及措施不完善扣 5 分；
        未经审批扣 3 分；验收、总结
        报告内容不全 1 处扣 1 分；对
        充水因素不清地段未坚持“有
        掘必探”扣 10 分；单孔设计
        未达到要求扣 3 分；无定期排
        查分析记录，每缺1次扣2分；
        防水煤柱未按规定程序审批
        扣 3 分；未执行雨季“三防”
        措施扣 2 分
        </label>
    </td>
    <td class="value">
        <select name="lack4_6">
            
            <option value="Y" <c:if test="${scoreDetail.lack4_6=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack4_6=='N' || scoreDetail.lack4_6==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score4_6" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_6}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark4_6" rows="3" class="inputxt">${scoreDetail.remark4_6}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">工程<br/>质量</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        防治水工程质量均符合设计要求
        </label>
    </td>
    <td ><label class="Validform_label" name="lack4_7">15</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。工程质量未达
        到设计标准 1 次扣 5 分；探放
        水施工不符合规定 1 处扣 5
        分；超前探查钻孔不符合设计
        1 处扣 2 分
        </label>
    </td>
    <td class="value">
        <select name="lack4_7">
            
            <option value="Y" <c:if test="${scoreDetail.lack4_7=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack4_7=='N' || scoreDetail.lack4_7==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score4_7" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_7}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark4_7" rows="3" class="inputxt">${scoreDetail.remark4_7}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">疏干<br/>带压<br/>开采</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        用物探和钻探等手段查明疏干、带压开采工作面
        隐伏构造、构造破碎带及其含（导）水情况，制
        定防治水措施
        </label>
    </td>
    <td ><label class="Validform_label" name="lack4_8">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。疏干、带压开
        采存在地质构造没有查明不
        得分;其他 1 项不符合要求扣
        1 分
        </label>
    </td>
    <td class="value">
        <select name="lack4_8">
            
            <option value="Y" <c:if test="${scoreDetail.lack4_8=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack4_8=='N' || scoreDetail.lack4_8==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>
    <td class="value" style="max-width:150px;">
        <input  name="score4_8" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_8}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark4_8" rows="3" class="inputxt">${scoreDetail.remark4_8}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">辅助<br/>工程</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.积水能够及时排出；<br/>
        2.按规定及时清理水仓、水沟，保证排水畅通
        </label>
    </td>
    <td ><label class="Validform_label" name="lack4_9">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。排积水不及时，影响
        生产扣 4 分；未及时清理水
        仓、水沟扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack4_9">
            
            <option value="Y" <c:if test="${scoreDetail.lack4_9=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack4_9=='N' || scoreDetail.lack4_9==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>
    <td class="value" style="max-width:150px;">
        <input  name="score4_9" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_9}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark4_9" rows="3" class="inputxt">${scoreDetail.remark4_9}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">三、<br/>水<br/>害<br/>预<br/>警<br/>(5分)</label>
    </td>
    <td>
        <label class="Validform_label">水害<br/>预警</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        对断层水、煤层顶底板水、陷落柱水、地表水等
        威胁矿井生产的各种水害进行检测、诊断，发现
        异常及时预警预控
        </label>
    </td>
    <td ><label class="Validform_label" name="lack4_10">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。未进行水害检
        测、诊断或异常情况未及时预
        警不得分
        </label>
    </td>
    <td class="value">
        <select name="lack4_10">
            
            <option value="Y" <c:if test="${scoreDetail.lack4_10=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack4_10=='N' || scoreDetail.lack4_10==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>
    <td class="value" style="max-width:150px;">
        <input  name="score4_10" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score4_10}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark4_10" rows="3" class="inputxt">${scoreDetail.remark4_10}</textarea>
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
        <input id="ssaSumScore4" name="ssaSumScore4" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore4}" datatype="/^100(\.0+)$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">单项得分</label>
    </td>
</tr>
</table>
</div>
<div title="煤矿防治冲击地压">
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
    <td>
        <label class="Validform_label" >
            一、<br>基<br>础<br>管<br>理<br>（10分）
        </label>
    </td>
    <td>
        <label class="Validform_label">组织<br/>保障</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.按规定设立专门的防冲机构并配备专门防冲
        技术人员；健全防冲岗位责任制及冲击地压分
        析、监测预警、定期检查、验收等制度；<br/>
        2.冲击地压矿井每周召开 1 次防冲分析会，
        防冲技术人员每天对防冲工作分析 1 次
        </label>
    </td>
    <td><label class="Validform_label" name="lack5_1">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。无管理机构不
        得分；岗位责任制及冲击危险
        性分析、监测预警、检查验收
        制度不全，每缺 1 项扣 2 分；
        人员不足，每缺 1 人扣 1 分；
        其他 1 处不符合要求扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack5_1">
            
            <option value="Y" <c:if test="${scoreDetail.lack5_1=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack5_1=='N' || scoreDetail.lack5_1==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score5_1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score5_1}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark5_1" rows="3" class="inputxt">${scoreDetail.remark5_1}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="2">
        <label class="Validform_label">
            二、<br/>防<br/>冲<br/>技<br/>术<br/>(40分)
        </label>
    </td>
    <td>
        <label class="Validform_label">
            技术<br/>支撑
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.冲击地压矿井应进行煤岩层冲击倾向性鉴定，
        开采具有冲击倾向性的煤层，应进行冲击危险性
        评价；<br/>
        2.冲击地压矿井应编制中长期防冲规划与年度
        防冲计划；<br/>
        3.按规定编制防冲专项设计,按程序进行审批；<br/>
        4.冲击危险性预警指标按规定审批；<br/>
        5.有冲击地压危险的采掘工作面有防冲安全技
        术措施并按规定及时审批
        </label>
    </td>
    <td ><label class="Validform_label" name="lack5_2">20</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。未进行冲击倾向性鉴
        定、冲击危险性评价，或未编
        制中长期防冲规划与年度防
        冲计划、无防冲专项设计或未
        确定冲击危险性预警指标不
        得分；工作面设计不符合防冲
        规定 1 项扣 5 分，作业规程中
        无防冲专项安全技术措施扣5
        分；采掘工作面防冲安全技术
        措施审批不及时 1 次扣 5 分
        </label>
    </td>
    <td class="value">
        <select name="lack5_2">
            
            <option value="Y" <c:if test="${scoreDetail.lack5_2=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack5_2=='N' || scoreDetail.lack5_2==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score5_2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score5_2}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark5_2" rows="3" class="inputxt">${scoreDetail.remark5_2}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">
            监测<br/>预警
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.建立冲击地压区域监测和局部监测预警系统，
        实时监测冲击危险性；<br/>
        2.区域监测系统应覆盖所有冲击地压危险区域，
        经评价冲击危险程度高的采掘工作面应安装应
        力在线监测系统；<br/>
        3.监测系统运行正常，出现故障时及时处理；<br/>
        4.监测指标发现异常时，应采用钻屑法及时进行
        现场验证
        </label>
    </td>
    <td><label class="Validform_label" name="lack5_3">20</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。未建立区域及
        局部监测预警系统不得分；
        监测系统故障处理不及时 1 次
        扣 2 分；区域监测系统布置不
        合理 1 处扣 1 分；发现异常未
        及时验证 1 次扣 3 分
        </label>
    </td>
    <td class="value">
        <select name="lack5_3">
            
            <option value="Y" <c:if test="${scoreDetail.lack5_3=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack5_3=='N' || scoreDetail.lack5_3==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score5_3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score5_3}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark5_3" rows="3" class="inputxt">${scoreDetail.remark5_3}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="2">
        <label class="Validform_label">三、<br/>防<br/>冲<br/>措<br/>施<br/>（30分）</label>
    </td>
    <td>
        <label class="Validform_label">区域<br/>防冲<br/>措施</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        冲击地压矿井开拓方式、开采顺序、巷道布置、
        采煤工艺等符合规定；保护层采空区原则不留煤
        柱，留设煤柱时，按规定审批
        </label>
    </td>
    <td ><label class="Validform_label" name="lack5_4">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
    	查资料和现场。不符合要求 1处扣 5 分
        </label>
    </td>
    <td class="value">
        <select name="lack5_4">
            
            <option value="Y" <c:if test="${scoreDetail.lack5_4=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack5_4=='N' || scoreDetail.lack5_4==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score5_4" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score5_4}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark5_4" rows="3" class="inputxt">${scoreDetail.remark5_4}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">局部<br/>防冲<br/>措施</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.钻机等各类装备满足矿井防冲工作需要；<br/>
        2.实施钻孔卸压时，钻孔直径、深度、间距等参
        数应在设计中明确规定，钻孔直径不小于 100mm，
        并制定安全防护措施；<br/>
        3.实施爆破卸压时，装药方式、装药长度、装药
        量、封孔长度以及连线方式、起爆方式等参数应
        在设计中明确规定，并制定安全防护措施；<br/>
        4.实施煤层预注水时，注水方式、注水压力、注
        水时间等应在设计中明确规定；<br/>
        5.有冲击地压危险的采煤工作面推进速度应在
        作业规程中明确规定并执行；<br/>
        6.冲击地压危险工作面实施解危措施后，应进效果检验
        </label>
    </td>
    <td><label class="Validform_label" name="lack5_5">20</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。不落实防冲措施不得分；
        1 项落实不到位扣5 分
        </label>
    </td>
    <td class="value">
        <select name="lack5_5">
            
            <option value="Y" <c:if test="${scoreDetail.lack5_5=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack5_5=='N' || scoreDetail.lack5_5==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score5_5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score5_5}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark5_5" rows="3" class="inputxt">${scoreDetail.remark5_5}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">四、<br/>防<br/>护<br/>措<br/>施<br/>(10分)</label>
    </td>
    <td>
        <label class="Validform_label">安全<br/>防护</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.煤层爆破作业的躲炮距离不小于 300m；<br/>
        2.冲击危险区采取限员、限时措施，设置压风自
        救系统，设立醒目的防冲警示牌、防冲避灾路线
        图；<br/>
        3.冲击地压危险区存放的设备、材料应采取固定
        措施，码放高度不应超过 0.8m；大型设备、备用
        材料应存放在采掘应力集中区以外；<br/>
        4.冲击危险区各类管路吊挂高度不应高于 0.6m,
        电缆吊挂应留有垂度；<br/>
        5.U 型钢支架卡缆、螺栓等采取防崩措施；<br/>
        6.加强冲击地压危险区巷道支护，采煤工作面两
        巷超前支护范围和支护强度符合作业规程规定；<br/>
        7.严重冲击地压危险区域采掘工作面作业人员
        佩戴个人防护装备
        </label>
    </td>
    <td><label class="Validform_label" name="lack5_6">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。爆破作业躲炮
        时间和距离不符合要求 1 次
        扣 2 分；
        未采取限员限时措施扣 5 分；
        未设置压风自救系统扣 5 分，
        压风自救系统不完善1 处扣 2 分；图牌板不全，每
        缺 1 块扣 2 分；悬挂不醒目、
        不规范 1 处扣 2 分；通信线路
        未防护扣 4 分；巷道不畅通 1
        处扣 2 分，设备材料码放、管
        线吊挂不符合要求 1 处扣 2
        分；锚索、U 型钢支架卡缆、
        螺栓等未采取防崩措施 1 处
        扣 2 分；有冲击地压危险的采
        掘工作面作业人员未佩戴个
        人防护装备，发现 1 人扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack5_6">
            
            <option value="Y" <c:if test="${scoreDetail.lack5_6=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack5_6=='N' || scoreDetail.lack5_6==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score5_6" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score5_6}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark5_6" rows="3" class="inputxt">${scoreDetail.remark5_6}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">五、<br/>基<br/>础<br/>资<br/>料<br/>(10分)</label>
    </td>
    <td>
        <label class="Validform_label">台账<br/>资料</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.作业规程中防冲措施编制内容齐全、规范，图
        文清楚、保存完好，执行、考核记录齐全；<br/>
        2.建立钻孔、爆破、注水等施工参数台账，上图
        管理；<br/>
        3.现场作业记录齐全、真实、有据可查，报表、
        阶段性工作总结齐全、规范；<br/>
        4.建立冲击地压记录卡和统计表
        </label>
    </td>
    <td><label class="Validform_label" name="lack5_7">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。防冲措施内容不齐全
        1 处扣 2 分内容不规范 1 处
        扣 1 分；未建立台账或未上图
        管理扣 5 分，台账和图纸不
        全，每缺 1 次扣 1 分；现场作
        业记录、报表、阶段性工作总
        结等不齐全 1 项扣 2 分发生
        冲击地压不及时上报、无记录
        或瞒报不得分
        </label>
    </td>
    <td class="value">
        <select name="lack5_7">
            
            <option value="Y" <c:if test="${scoreDetail.lack5_7=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack5_7=='N' || scoreDetail.lack5_7==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;">
        <input  name="score5_7" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score5_7}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value">
        <textarea name="remark5_7" rows="3" class="inputxt">${scoreDetail.remark5_7}</textarea>
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
        <input id="ssaSumScore5" name="ssaSumScore5" type="text" style="width: 150px" class="inputxt" value="${scoreDetail.ssaSumScore5}" datatype="/^100(\.0+)$|^[1-9]\d(\.\d+)?$|^\d(\.\d+)?$/" errormsg="请填写100以内的数字!">
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
            <label class="Validform_label" style="display: none;">汇总得分 </label>
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
<script src = "webpage/com/sdzk/buss/web/standardassess/js/sfMineStandardAssess.js"></script>
<script type="text/javascript">

    var reqFrom = "${from}";
    var load = "${load}";
    if("detail" == load){
        $("form input,textarea,select").attr("disabled","true");
    }
    $(function(){
        if(reqFrom == 'mineAssess'){
            $("#formobj").find("input[class='inputxt']").attr("class","");
        }

        $("div[class='tabs-wrap']").css("width","auto");
        if(location.href.indexOf("load=detail")!=-1){
            $("tr[class='luru']").css("display","none");
        }
        $("input[name^='score1_']").on("blur", function(){
            calculateSum("score1_","lack1_","ssaSumScore1");
            calculateSumLocal();
        });
        $("input[name^='score2_']").on("blur", function(){
            calculateSum("score2_","lack2_","ssaSumScore2");
            calculateSumLocal();
        });
        $("input[name^='score3_']").on("blur", function(){
            calculateSum("score3_","lack3_","ssaSumScore3");
            calculateSumLocal();
        });
        $("input[name^='score4_']").on("blur", function(){
            calculateSum("score4_","lack4_","ssaSumScore4");
            calculateSumLocal();
        });
        $("input[name^='score5_']").on("blur", function(){
            calculateSum("score5_","lack5_","ssaSumScore5");
            calculateSumLocal();
        });
		$("#cjdy").on("change",function(){
			var val = $(this).find(":selected").val();
            var tab_option = $('#tt').tabs('getTab',"煤矿防治冲击地压").panel('options').tab;
			if(val=="Y"){
				tab_option.show();
			} else {
				if($(".tabs-selected .tabs-title").text()=="煤矿防治冲击地压"){
					$(".tabs>li:first").trigger("click");
				}
                //设置tab的form field
                $("input[name^='score5_']").val(0);
                $("input[name^='score5_']").removeAttr("readonly");
                $("input[name^='ssaSumScore5']").val(0);
                $("select[name^='lack5_']").val("N");
				tab_option.hide();
			}
			calculateSumLocal();
		});
		$("#swdzlx").on("change",function(){
			calculateSumLocal();
		});
        if ($(".tabs").length > 0) {
            if ($("#cjdy>option:selected").val() == "N") {
                var tab_option = $('#tt').tabs('getTab', "煤矿防治冲击地压").panel('options').tab;
                tab_option.hide();
            }
        }
    });

	 function calculateSumLocal(){
		 var totalSum = 0;
		 var jsglScore = $("#ssaSumScore1").val();
		 var dzScore = $("#ssaSumScore2").val();
		 var clScore = $("#ssaSumScore3").val();
		 var fzsScore = $("#ssaSumScore4").val();
		 var fzcjdyScore = $("#ssaSumScore5").val();
		 var cjdy = $("#cjdy>option:selected").val();
		 var swdzlx = $("#swdzlx>option:selected").val();
		 if (jsglScore !=null && jsglScore != ""){
			jsglScore = parseFloat(jsglScore);
		 }
		 if (dzScore !=null && dzScore != ""){
			dzScore = parseFloat(dzScore);
		 }
		 if (clScore !=null && clScore != ""){
			clScore = parseFloat(clScore);
		 }
		 if (fzsScore !=null && fzsScore != ""){
			fzsScore = parseFloat(fzsScore);
		 }
		 if (fzcjdyScore !=null && fzcjdyScore != ""){
			fzcjdyScore = parseFloat(fzcjdyScore);
		 }
		 if(cjdy=='N' && swdzlx=='1'){
			 totalSum = jsglScore*0.15+dzScore*0.3+clScore*0.25+fzsScore*0.3;
		 } else if(cjdy=='N' && swdzlx=='2'){
			 totalSum = jsglScore*0.15+dzScore*0.25+clScore*0.2+fzsScore*0.4;
		 } else if(cjdy=='Y' && swdzlx=='1'){
			 totalSum = jsglScore*0.15+dzScore*0.2+clScore*0.15+fzsScore*0.2+fzcjdyScore*0.3;
		 } else if(cjdy=='Y' && swdzlx=='2'){
			 totalSum = jsglScore*0.15+dzScore*0.15+clScore*0.15+fzsScore*0.25+fzcjdyScore*0.3;
		 }
		 $("input[name='ssaSumScore']").val(totalSum.toFixed(2));
	 }

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
</t:formvalid>
</body>

