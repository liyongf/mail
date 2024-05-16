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

        /*Author:  张赛超*/
        .inputxt{
            margin-top: 20px;
        }
    </style>
    <style>/* tr{	height:30px;} */</style>
</head>
<body>
<%--<c:if test="${from ne 'mineAssess'}">--%>
    <%--<!-- from :mineAssess 页面请求来源于矿井考核自评汇总的时候不显示该面包屑，否则显示面包屑 -->--%>
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->井工煤矿->机电</div>--%>
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
        <td colspan='7' align="center" style="font-size:20px;font-weight: bold;">煤矿机电标准化评分表</td>
    </tr>
    <tr>
        <td class="value" colspan="7" style="text-align: right">
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
    <td rowspan="8">
        <label class="Validform_label">
        一、<br>设<br>备<br>与<br>指<br>标<br>（15分）
        </label>
    </td>
    <td>
        <label class="Validform_label">设备<br/>证标</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.机电设备有产品合格证；<br/>
        2.纳入安标管理的产品有煤矿矿用产品安全标志，使用
        地点符合规定；<br/>
        3.防爆设备有防爆合格证
        </label>
    </td>
    <td><label class="Validform_label" name="lack1">4</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。1 台不
        符合要求不得分
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
    <td>
        <label class="Validform_label">设备<br/>完好</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        机电设备综合完好率不低于 90%
        </label>
    </td>
    <td><label class="Validform_label" name="lack2">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。每降低
        1 个百分点扣 0.5 分
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
    <td>
        <label class="Validform_label">固定<br/>设备</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        大型在用固定设备完好
        </label>
    </td>
    <td><label class="Validform_label" name="lack3">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。1 台不
        完好不得分
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
    <td>
        <label class="Validform_label">小型<br/>电器</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        小型电器设备完好率不低于 95%
        </label>
    </td>
    <td><label class="Validform_label" name="lack4">1.5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。每降低
        1 个百分点扣 0.5 分
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
    <td>
        <label class="Validform_label">矿灯</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        在用矿灯完好率 100%，使用合格的双光源矿灯。完好矿灯总数应多出常用矿灯人数的 10%以上
        </label>
    </td>
    <td><label class="Validform_label" name="lack5">1.5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。井下发
        现 1 盏红灯扣 0.3 分，
        1 盏灭灯、不合格灯不
        得分，完好矿灯总数未
        满足要求不得分
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
    <td>
        <label class="Validform_label">机电<br/>事故<br/>率</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        机电事故率不高于 1%
        </label>
    </td>
    <td><label class="Validform_label" name="lack6">1</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。机电事故率达
        不到要求不得分
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
    <td>
        <label class="Validform_label">设备<br/>待修<br/>率</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        设备待修率不高于 5%
        </label>
    </td>
    <td><label class="Validform_label" name="lack7">1</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。设备待
        修率每增加 1 个百分
        点扣 0.5 分
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
    <td>
        <label class="Validform_label">设备<br/>大修<br/>改造</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        设备更新改造按计划执行，设备大修计划应完成 90%以上
        </label>
    </td>
    <td><label class="Validform_label" name="lack8">1</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。无更新改造年
        度计划或未完成不得
        分；无大修计划或计划
        完成率全年低于 90%，
        上半年低于 30%不得
        分
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
    <td rowspan="7">
        <label class="Validform_label">二、<br/>煤<br/>矿<br/>机<br/>械<br/>（20分）</label>
    </td>
    <td>
        <label class="Validform_label">主提<br/>升( <br/>立斜<br/>井绞<br/>车 )<br/>系统</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.提升系统能力满足矿井安全生产需要；<br/>
        2.各种安全保护装置符合《煤矿安全规程》规定；<br/>
        3.立井提升装置的过卷过放、提升容器和载荷等符合《煤矿
        安全规程》规定；<br/>
        4.提升装置、连接装置及提升钢丝绳符合《煤矿安全规
        程》规定；<br/>
        5.制动装置可靠，副井及负力提升的系统使用可靠的电
        气制动；<br/>
        6.立井井口及各水平阻车器、安全门、摇台等与提升信
        号闭锁；<br/>
        7.提升速度大于 3m/s 的立井提升系统内，安设有防撞梁
        和缓冲托罐装置；单绳缠绕式双滚筒绞车安设有地锁和
        离合器闭锁；<br/>
        8.斜井提升制动减速度达不到要求时应设二级制动装
        置；<br/>
        9.提升系统通信、信号装置完善，主副井绞车房有能与
        矿调度室直通电话；<br/>
        10.上、下井口及各水平安设有摄像头，机房有视频监视
        器；<br/>
        11.机房安设有应急照明装置；<br/>
        12.使用低耗、先进、可靠的电控装置；<br/>
        13.主井提升宜采用集中远程监控，可不配司机值守，但
        应设图像监视，并定时巡检
        </label>
    </td>
    <td><label class="Validform_label" name="lack9">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。第 1～
        9项提人绞车1处不符
        合要求“煤矿机械”大
        项不得分，其他绞车不
        符合要求 1 处扣 1 分，
        第 10、11 项不符合要
        求 1 处扣 0.5 分，其他
        项不符合要求 1 处扣
        0.1 分
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
    <td>
        <label class="Validform_label">主提<br/>升( <br/>带式<br/>输送<br/>机 )<br/>系统</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.钢丝绳牵引带式输送机：<br/>
        ⑴运输能力满足矿井、采区安全生产需要，人货不混乘，
        不超速运人；<br/>
        ⑵各种保护装置符合《煤矿安全规程》规定；<br/>
        ⑶在输送机全长任何地点装设便于搭乘人员或其他人员
        操作的紧急停车装置；<br/>
        ⑷上、下人地点设声光信号、语音提示和自动停车装置，
        卸煤口及终点下人处设有防止人员坠入及进入机尾的安
        全设施和保护；<br/>
        ⑸上、下人和装、卸载处装设有摄像头，机房有视频监
        视器；<br/>
        ⑹输送带、滚筒、托辊等材质符合规定，滚筒、托辊转
        动灵活，带面无损坏、漏钢丝等现象；<br/>
        ⑺机房安设有与矿调度室直通电话；<br/>
        ⑻使用低耗、先进、可靠的电控装置；<br/>
        ⑼采用集中远程监控，实现无人值守<br/>
        2.滚筒驱动带式输送机：<br/>
        ⑴运输能力满足矿井、采区安全生产需要；<br/>
        ⑵电动机保护齐全可靠；<br/>
        ⑶装设有防滑、防跑偏、防堆煤、防撕裂和输送带张紧
        力下降保护装置，以及温度、烟雾监测和自动洒水装置；<br/>
        ⑷上运运输机装设防逆转和制动装置，下运运输机装设
        有软制动装置且装设防超速装置；<br/>
        ⑸减速器与电动机采用软连接或采用软启动控制，液力
        偶合器不使用可燃性传动介质（调速型液力偶合器不受
        此限）；<br/>
        ⑹输送带、滚筒、托辊等材质符合规定，滚筒、托辊转
        动灵活，带面无损坏、漏钢丝等现象；<br/>
        ⑺倾斜井巷使用的钢丝绳芯输送机有钢丝绳芯及接头状
        态检测装备；<br/>
        ⑻钢丝绳芯输送机设有沿线紧急停车、闭锁装置，装、
        卸载处设有摄像头；<br/>
        ⑼机头、机尾及搭接处设有照明，转动部位设有防护栏
        和警示牌，行人跨越处设有过桥；<br/>
        ⑽连续运输系统安设有连锁、闭锁控制装置，沿线安设
        有通信和信号装置；<br/>
        ⑾集中控制硐室安设有与矿调度室直通电话；<br/>
        ⑿使用低耗、先进、可靠的电控装置；<br/>
        ⒀采用集中远程监控，实现无人值守
        </label>
    </td>
    <td><label class="Validform_label" name="lack10">4</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。
        第⑴～⑸项提人带式
        输送机 1 处不符合要
        求扣 4 分，其他带式输
        送机 1 处不符合要求
        扣 1 分，第⑹、⑺项 1
        处不符合要求扣 0.5
        分，其他项 1 处不符合
        要求扣 0.1 分
        查现场和资料。
        第⑴～⑻项不符合要
        求 1 处扣 1 分，第⑼～
        ⑾项不符合要求 1 处
        扣 0.5 分，其他项不符
        合要求 1 处扣 0.1 分
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
    <td>
        <label class="Validform_label">主通<br/>风机<br/>系统</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.主要通风机性能满足矿井通风安全需要；<br/>
        2.电动机保护齐全、可靠；<br/>
        3.使用在线监测装置，并且具备通风机轴承、电动机轴
        承、电动机定子绕组温度检测和超温报警功能，具备振
        动监测及报警功能；<br/>
        4. 每月 倒机、检查 1 次；<br/>
        5.安设有与矿调度室直通的电话；<br/>
        6.机房设有水柱计、电流表、电压表等仪表，并定期校
        准；<br/>
        7.机房安设应急照明装置；<br/>
        8.使用低耗、先进、可靠的电控装置
        </label>
    </td>
    <td><label class="Validform_label" name="lack11">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。第 1～
        6项不符合要求1处扣
        1 分，第 7 项不符合要
        求扣 0.5 分，其他项不
        符合要求 1 处扣 0.1
        分
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
    <td>
        <label class="Validform_label">压风<br/>系统</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1. 供风能力满足矿井安全生产需要；<br/>
        2.压缩机、储气罐及管路设置符合《煤矿安全规程》和
        《特种设备安全法》等规定；<br/>
        3.电动机保护齐全可靠；<br/>
        4.压力表、安全阀、释压阀设置齐全有效，定期校准；<br/>
        5.油质符合规定，有可靠的断油保护；<br/>
        6.水冷压缩机水质符合要求，有可靠断水保护；<br/>
        7.风冷压缩机冷却系统及环境符合规定；<br/>
        8.温度保护齐全、可靠，定值准确；<br/>
        9.井下压缩机运转时有人监护；<br/>
        10.机房安设有应急照明装置；<br/>
        11.使用低耗、先进、可靠的电控装置；<br/>
        12.地面压缩机采用集中远程监控，实现无人值守
        </label>
    </td>
    <td><label class="Validform_label" name="lack12">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。第 1～
        9项不符合要求1处扣
        1 分，第 10 项不符合
        要求 1 处扣 0.5 分，其
        他项不符合要求 1 处
        扣 0.1 分
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
    <td>
        <label class="Validform_label">排水<br/>系统</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1. 矿井及采区主排水系统：<br/>
        ⑴排水能力满足矿井、采区安全生产需要；<br/>
        ⑵泵房及出口，水泵、管路及配电、控制设备，水仓蓄
        水能力等符合《煤矿安全规程》规定；<br/>
        ⑶有可靠的引水装置；<br/>
        ⑷设有高、低水位声光报警装置；<br/>
        ⑸电动机保护装置齐全、可靠；<br/>
        ⑹排水设施、水泵联合试运转、水仓清理等符合《煤矿
        安全规程》规定；<br/>
        ⑺ 水泵房安设有与矿调度室直通电话；<br/>
        ⑻ 各种仪表齐全，及时校准；<br/>
        ⑼ 使用低耗、先进、可靠的电控装置；<br/>
        ⑽ 采用集中远程监控，实现无人值守。<br/>
        2.其他排水地点：<br/>
        ⑴ 排水设备及管路符合规定要求；<br/>
        ⑵ 设备完好，保护齐全、可靠；<br/>
        ⑶ 排水能力满足安全生产需要；<br/>
        ⑷ 使用小型自动排水装置
        </label>
    </td>
    <td><label class="Validform_label" name="lack13">4</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。第 1
        项中的第⑴～⑺小项
        不符合要求 1 处扣 1
        分，其他项不符合要求
        1 处扣 0.1 分；第 2 项
        中的第⑴～⑶项不符
        合要求 1 处扣 0.5 分，
        第⑷项不符合要求扣
        0.1 分
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
    <td>
        <label class="Validform_label">瓦斯<br/>发电<br/>系统</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.抽采泵出气侧管路系统装设防回火、防回气、防爆炸
        的安全装置；<br/>
        2.根据输送方式的不同，设置甲烷、流量、压力、温度、
        一氧化碳等各种监测传感器；<br/>
        3.超温、断水等保护齐全、可靠；<br/>
        4.压力表、水位计、温度表等仪器仪表齐全、有效；5.
        机房安设有应急照明；<br/>
        6.电气设备防爆性能符合要求，保护齐全可靠；<br/>
        7.阀门装置灵活；<br/>
        8.机房有防烟火、防静电、防雷电措施
        </label>
    </td>
    <td><label class="Validform_label" name="lack14">1.5</label></td>
    <td class = "td-padding">
    	<label class="Validform_label">
        查现场，不符合要求 1
        处扣 0.5 分
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
    <td>
        <label class="Validform_label">地面<br/>供热<br/>降温<br/>系统</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.热水锅炉：<br/>
        ⑴安设有温度计、安全阀、压力表、排污阀；<br/>
        ⑵按规定安设可靠的超温报警和自动补水装置；<br/>
        ⑶系统中有减压阀，热水循环系统定压措施和循环水膨
        胀装置可靠，有高低压报警和连锁保护；<br/>
        ⑷停电保护、电动机及其他各种保护灵敏可靠；<br/>
        ⑸有特种设备使用登记证和年检报告；<br/>
        ⑹安全阀、仪器仪表按规定检验，有检验报告；<br/>
        ⑺水质合格，有检验报告<br/>
        2.蒸汽锅炉：<br/>
        ⑴安设有双色水位计或两个独立的水位表；<br/>
        ⑵按规定安设可靠的高低水位报警和自动补水装置；<br/>
        ⑶按规定安设压力表、安全阀、排污阀；<br/>
        ⑷按规定安设可靠的超压报警器和连锁保护装置；<br/>
        ⑸温度保护、熄火保护、停电自锁保护以及电动机和其
        他各种保护灵敏、可靠；<br/>
        ⑹有特种设备使用登记证和年检报告；<br/>
        ⑺安全阀、仪器仪表按规定检验，有检验报告。<br/>
        ⑻水质合格，有检验报告<br/>
        3.热风炉：<br/>
        ⑴安设有防火门和栅栏，有防烟、防火、超温安全连锁
        保护装置，有 CO 检测和洒水装置；<br/>
        ⑵电动机及其他各种保护灵敏、可靠；<br/>
        ⑶出风口处电缆有防护措施；<br/>
        ⑷锅炉距离入风井口不少于 20 米；<br/>
        ⑸有国家或者当地煤炭安全监察部门颁发的安全性能合
        格证<br/>
        4.地面降温系统：<br/>
        ⑴设备完好；<br/>
        ⑵各类保护齐全可靠；<br/>
        ⑶各种阀门、安全阀灵活可靠；<br/>
        ⑷仪表正常，有检验报告；<br/>
        ⑸水质合格，有化验记录
        </label>
    </td>
    <td><label class="Validform_label" name="lack15">1.5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场，不符合要求 1
        处扣 0.5 分
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
    <td rowspan="11">
        <label class="Validform_label">三、<br/>煤<br/>矿<br/>电<br/>器<br/>（30分）</label>
    </td>
    <td>
        <label class="Validform_label">地面<br/>供电<br/>系统</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.有供电设计及供电系统图，供电能力满足矿井安全生
        产需要；<br/>
        2.矿井供电主变压器运行方式符合规定；<br/>
        3.主要通风机、提升人员的绞车、抽采瓦斯泵、压风机
        以及地面安全监控中心等主要设备供电符合《煤矿安全
        规程》规定；<br/>
        4.各种保护设置齐全、定值准确、动作灵敏可靠，高压
        配出侧装设有选择性的接地保护；<br/>
        5.变电所有可靠的操作电源；<br/>
        6.直供电机开关或带有电容器的开关有欠压保护；<br/>
        7.高压开关柜具有防止带电合闸、防止带接地合闸、防
        止误入带电间隔、防止带电合接地线、防止带负荷拉刀
        闸和通信功能；<br/>
        8.反送电开关柜加锁且有明显标志；<br/>
        9.矿井 6000V 及以上电网单相接地电容电流符合《煤矿
        安全规程》规定；<br/>
        10.电气工作票、操作票符合《电力安全工作规程》的要
        求；<br/>
        11.防雷设施齐全、可靠；<br/>
        12.供电电压、功率因数、谐波参数符合规定；<br/>
        13.矿井主要变电所实现综合自动化保护和控制，实现无
        人值守；<br/>
        14.变电所有应急照明装置；<br/>
        15.矿井变电所安设有与电力调度及矿调度室直通电话，<br/>
        并有录音功能
        </label>
    </td>
    <td><label class="Validform_label" name="lack16">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。第 1
        项 1 处不符合要求不
        得分，第 2、3 项不符
        合要求 1 项扣 3 分，第
        4～10 项不符合要求 1
        处扣 1 分，第 11～15
        项不符合要求 1 处扣
        0.5 分
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
    <td rowspan="10">
        <label class="Validform_label">井下<br/>供电<br/>系统</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.井下供配电网络：<br/>
        ⑴各水平中央变电所、采区变电所、主排水泵房和下山
        开采的采区泵房供电线路符合《煤矿安全规程》规定，
        运行方式合理；<br/>
        ⑵各级变电所运行管理符合规定；<br/>
        ⑶矿井、采区及采掘工作面等供电地点均有合格的供电
        系统设计，符合现场实际；<br/>
        ⑷按规定进行继电保护核算、检查和整定；<br/>
        ⑸中央变电所安装有选择性接地保护装置；<br/>
        ⑹配电网路开关分断能力、可靠动作系数和动、热稳定
        性以及电缆的热稳定性符合规定；<br/>
        ⑺实行停送电审批和工作票制度；<br/>
        ⑻井下变电所、配电点悬挂与实际相符的供电系统图；<br/>
        ⑼调度室、变电所有停送电记录；<br/>
        ⑽变电所及高压配电点设有与矿调度室直通电话；<br/>
        ⑾变电所设置符合《煤矿安全规程》规定；<br/>
        ⑿采区变电所专人值班或关门加锁并定期巡检；<br/>
        ⒀采用集中远程监控，实现无人值守
        </label>
    </td>
    <td><label class="Validform_label" name="lack17">4</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。 第⑴
        项 1 处不符合要求不
        得分，第⑵～⑿不符合
        要求 1 处扣 0.5 分，其
        他项不符合要求 1 处
        扣 0.1 分
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
    <td class = "td-padding">
        <label class="Validform_label">
        2.防爆电气设备及小型电器防爆合格率 100%
        </label>
    </td>
    <td><label class="Validform_label" name="lack18">4</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。高瓦
        斯、突出矿井中，以及
        低瓦斯矿井主要进风
        巷以外区域出现 1 处
        失爆“煤矿电气”大项
        不得分，其他区域发现
        1 处失爆扣 4 分
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
        3.采掘工作面供电：<br/>
        ⑴配电点设置符合《煤矿安全规程》规定；<br/>
        ⑵掘进工作面“三专两闭锁” 设置齐全、灵敏可靠；<br/>
        ⑶采煤工作面瓦斯电闭锁设置齐全、灵敏可靠；<br/>
        ⑷按要求试验，有试验记录
        </label>
    </td>
    <td><label class="Validform_label" name="lack19">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查 现 场 和 资 料 。 第
        (1)-(3)项 1 处不符合
        要求不得分；第(4)项
        不符合要求1处扣0.5
        分
        </label>
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
    <td class = "td-padding">
        <label class="Validform_label">
        4.高压供电装备：<br/>
        ⑴高压控制设备装有短路、过负荷、接地和欠压释放保
        护；<br/>
        ⑵向移动变电站和高压电动机供电的馈电线上装有有选
        择性的动作于跳闸的单相接地保护；<br/>
        ⑶真空高压隔爆开关装设有过电压保护；<br/>
        ⑷推广设有通信功能的装备
        </label>
    </td>
    <td><label class="Validform_label" name="lack20">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场或资料。不符合
        要求 1 处扣 0.5 分
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
    <td class = "td-padding">
        <label class="Validform_label">
        5.低压供电装备：<br/>
        ⑴采区变电所、移动变电站或者配电点引出的馈电线上
        有短路、过负荷和漏电保护；<br/>
        ⑵有检漏或选择性的漏电保护；<br/>
        ⑶按要求试验，有试验记录；<br/>
        ⑷推广设有通信功能的装备
        </label>
    </td>
    <td><label class="Validform_label" name="lack21">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场或资料。不符合
        要求 1 处扣 0.5 分
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
    <td class = "td-padding">
        <label class="Validform_label">
        6.变压器及电动机控制设备：<br/>
        ⑴40KW 及以上电动机使用真空磁力启动器控制；<br/>
        ⑵干式变压器、移动变电站过负荷、短路等保护齐全可
        靠；<br/>
        ⑶低压电动机控制设备有短路、过负荷、单相断线、漏
        电闭锁保护及远程控制功能
        </label>
    </td>
    <td><label class="Validform_label" name="lack22">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。甩保
        护、铜铁保险、开关前
        盘带电 1 处扣 1 分，其
        他 1 处不符合要求扣
        0.5 分
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
    <td class = "td-padding">
        <label class="Validform_label">
        7.保护接地符合《煤矿井下保护接地装置的安装、检查、
        测定工作细则》的要求
        </label>
    </td>
    <td><label class="Validform_label" name="lack23">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场或资料。不符合
        要求 1 处扣 0.5 分
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
    <td class = "td-padding">
        <label class="Validform_label">
        8.信号照明系统：<br/>
        ⑴井下信号、照明等其他 220V 单相供电系统使用综合保
        护装置；<br/>
        ⑵保护齐全、可靠
        </label>
    </td>
    <td><label class="Validform_label" name="lack24">1</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场或资料。不符合
        要求 1 处扣 0.5 分
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
    <td class = "td-padding">
        <label class="Validform_label">
        9. 电缆及接线工艺：<br/>
        ⑴动力电缆和各种信号、监控监测电缆使用煤矿用电缆；<br/>
        ⑵电缆接头及接线方式和工艺符合要求，无“羊尾巴”、
        “鸡爪子”、明接头；<br/>
        ⑶各种电缆按规定敷设（吊挂），合格率不低于 95%；<br/>
        ⑷各种电气设备接线工艺符合要求
        </label>
    </td>
    <td><label class="Validform_label" name="lack25">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。高瓦
        斯、突出矿井井下全范
        围以及低瓦斯矿井采
        区石门以里出现 1 处
        动力电缆不符合要求
        “煤矿电气”大项不得
        分，其他区域发现 1
        处不符合要求扣 3 分；
        36V 以上信号电缆不
        符合要求 1 处扣 0.5
        分；本安电缆及电气设
        备接线工艺不符合要
        求 1 处扣 0.2 分；电缆
        合格率每降低 1 个百
        分点扣 0.5 分
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
    <td class = "td-padding">
        <label class="Validform_label">
        10. 井上下防雷电装置符合《煤矿安全规程》规定
        </label>
    </td>
    <td><label class="Validform_label" name="lack26">1</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。不符合
        要求 1 处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack26">
            
            <option value="Y" <c:if test="${scoreDetail.lack26=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack26=='N' || scoreDetail.lack26==null}">selected="selected"</c:if>>否 </option>
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
    <td rowspan="13">
        <label class="Validform_label">四、<br/>基<br/>础<br/>管<br/>理<br/>（23分）</label>
    </td>
    <td rowspan="2">
        <label class="Validform_label">组织<br/>保障</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.有负责机电管理工作的职能机构，有负责供电、电缆、
        小型电器、防爆、设备、配件、油脂、输送带、钢丝绳
        等日常管理工作职能部门
        </label>
    </td>
    <td><label class="Validform_label" name="lack27">1.5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。无机构不得
        分，其他 1 处不符合要
        求扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack27">
            
            <option value="Y" <c:if test="${scoreDetail.lack27=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack27=='N' || scoreDetail.lack27==null}">selected="selected"</c:if>>否 </option>
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

    <td class = "td-padding">
        <label class="Validform_label">
        2.矿及生产区队配有机电管理和技术人员，责任、分工明确
        </label>
    </td>
    <td><label class="Validform_label" name="lack28">1.5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。未配备人员不
        得分，其他 1 处不符合
        要求扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack28">
            
            <option value="Y" <c:if test="${scoreDetail.lack28=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack28=='N' || scoreDetail.lack28==null}">selected="selected"</c:if>>否 </option>
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
    <td rowspan="2">
        <label class="Validform_label">管理<br/>制度</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.矿、专业管理部门建有以下制度（规程）：岗位安全生
        产责任制，操作规程，停送电管理、设备定期检修、电
        气试验测试、干部上岗检查、设备管理、机电事故统计
        分析、防爆设备入井安装验收、电缆管理、小型电器管
        理、油脂管理、配件管理、阻燃胶带管理、杂散电流管
        理以及钢丝绳管理等制度
        </label>
    </td>
    <td><label class="Validform_label" name="lack29">1.5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。内容不全，每
        缺 1 种制度（规程）扣
        0.5 分，制度（规程）
        执行不到位1处扣0.5
        分
        </label>
    </td>
    <td class="value">
        <select name="lack29">
            
            <option value="Y" <c:if test="${scoreDetail.lack29=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack29=='N' || scoreDetail.lack29==null}">selected="selected"</c:if>>否 </option>
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
    <td class = "td-padding">
        <label class="Validform_label">
        2.机房、硐室有以下制度、图纸和记录：<br/>
        ⑴有操作规程，岗位责任、设备包机、交接班、巡回检
        查、保护试验、设备检修以及要害场所管理等制度；<br/>
        ⑵有设备技术特征、设备电气系统图、液压（制动）系
        统图、润滑系统图；<br/>
        ⑶有设备运转、检修、保护试验、干部上岗、交接班、
        事故、外来人员、钢丝绳检查（或其他专项检查）等记
        录
        </label>
    </td>
    <td><label class="Validform_label" name="lack30">1.5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。内容不
        全，每缺 1 种扣 0.5
        分，执行不到位 1 处扣
        0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack30">
            
            <option value="Y" <c:if test="${scoreDetail.lack30=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack30=='N' || scoreDetail.lack30==null}">selected="selected"</c:if>>否 </option>
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
    <td rowspan="5">
        <label class="Validform_label">技术<br/>管理</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.机电设备选型论证、购置、安装、使用、维护、检修、
        更新改造、报废等综合管理及程序符合相关规定，档案
        资料齐全
        </label>
    </td>
    <td><label class="Validform_label" name="lack31">1</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。不符合
        要求 1 处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack31">
            
            <option value="Y" <c:if test="${scoreDetail.lack31=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack31=='N' || scoreDetail.lack31==null}">selected="selected"</c:if>>否 </option>
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
    <td class = "td-padding">
        <label class="Validform_label">
        2.设备技术信息档案齐全，管理人员明确；主变压器、
        主要通风机、提升机、压风机、主排水泵、锅炉等大型
        主要设备做到一台一档
        </label>
    </td>
    <td><label class="Validform_label" name="lack32">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。无电子档案或
        无具体人员管理档案
        不得分，其他 1 处不符
        合要求扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack32">
            
            <option value="Y" <c:if test="${scoreDetail.lack32=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack32=='N' || scoreDetail.lack32==null}">selected="selected"</c:if>>否 </option>
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
    <td class = "td-padding">
        <label class="Validform_label">
        3.矿井主提升、排水、压风、供热、供水、通讯、井上
        下供电等系统和井下电气设备布置等图纸齐全，并及时
        更新
        </label>
    </td>
    <td><label class="Validform_label" name="lack33">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。缺 1 种图不得
        分，图纸与实际不相符
        1 处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack33">
            
            <option value="Y" <c:if test="${scoreDetail.lack33=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack33=='N' || scoreDetail.lack33==null}">selected="selected"</c:if>>否 </option>
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
    <td class = "td-padding">
        <label class="Validform_label">
        4.各岗位操作规程、措施及保护试验要求等与实际运行
        的设备相符
        </label>
    </td>
    <td><label class="Validform_label" name="lack34">1</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。不符合
        要求 1 处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack34">
            
            <option value="Y" <c:if test="${scoreDetail.lack34=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack34=='N' || scoreDetail.lack34==null}">selected="selected"</c:if>>否 </option>
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
    <td class = "td-padding">
        <label class="Validform_label">
        5.持续有效地开展全矿机电专业技术专项检查与分析工
        </label>
        作
    </td>
    <td><label class="Validform_label" name="lack35">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。未开展工作不
        得分，工作开展效果不
        好 1 次扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack35">
            
            <option value="Y" <c:if test="${scoreDetail.lack35=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack35=='N' || scoreDetail.lack35==null}">selected="selected"</c:if>>否 </option>
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
    <td rowspan="4">
        <label class="Validform_label">设备<br/>技术<br/>性能<br/>测试</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.大型固定设备更新改造有设计，有验收测试结果和联
        合验收报告
        </label>
    </td>
    <td><label class="Validform_label" name="lack36">1</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。没有或不符合
        要求不得分
        </label>
    </td>
    <td class="value">
        <select name="lack36">
            
            <option value="Y" <c:if test="${scoreDetail.lack36=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack36=='N' || scoreDetail.lack36==null}">selected="selected"</c:if>>否 </option>
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
    <td class = "td-padding">
        <label class="Validform_label">
        2.主提升设备、主排水泵、主要通风机、压风机及锅炉、
        瓦斯抽采泵等按《煤矿安全规程》检测；检测周期符合
        《煤矿在用安全设备检测检验目录（第一批）》或其他
        规定要求
        </label>
    </td>
    <td><label class="Validform_label" name="lack37">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。不符合要求 1
        处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack37">
            
            <option value="Y" <c:if test="${scoreDetail.lack37=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack37=='N' || scoreDetail.lack37==null}">selected="selected"</c:if>>否 </option>
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
    <td class = "td-padding">
        <label class="Validform_label">
        3.主绞车的主轴、制动杆件、天轮轴、连接装置以及主
        要通风机的主轴、叶片等主要设备的关键零部件探伤符
        合规定
        </label>
    </td>
    <td><label class="Validform_label" name="lack38">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。不符合要求 1
        处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack38">
            
            <option value="Y" <c:if test="${scoreDetail.lack38=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack38=='N' || scoreDetail.lack38==null}">selected="selected"</c:if>>否 </option>
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
    <td class = "td-padding">
        <label class="Validform_label">
        4.按规定进行防坠器试验、电气试验、防雷设施及接地
        电阻等测试
        </label>
    </td>
    <td><label class="Validform_label" name="lack39">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。不符合要求 1
        处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack39">
            
            <option value="Y" <c:if test="${scoreDetail.lack39=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack39=='N' || scoreDetail.lack39==null}">selected="selected"</c:if>>否 </option>
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
    <td rowspan="2">
        <label class="Validform_label">五、<br/>岗<br/>位<br/>规<br/>范<br/>（5分）</label>
    </td>
    <td>
        <label class="Validform_label">专业<br/>技能</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.管理和技术人员掌握相关的岗位职责、规程、设计、
        措施；<br/>
        2.作业人员掌握本岗位相应的操作规程和安全措施
        </label>
    </td>
    <td><label class="Validform_label" name="lack40">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。不符合要求 1
        处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack40">
            
            <option value="Y" <c:if test="${scoreDetail.lack40=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack40=='N' || scoreDetail.lack40==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">规范<br/>作业</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.严格执行岗位安全生产责任制；<br/>
        2.无“三违”行为；<br/>
        3.作业前进行安全确认
        </label>
    </td>
    <td><label class="Validform_label" name="lack41">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。发现 “三违”
        不得分；不执行岗位责
        任制、未进行安全确认
        1 人次扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack41">
            
            <option value="Y" <c:if test="${scoreDetail.lack41=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack41=='N' || scoreDetail.lack41==null}">selected="selected"</c:if>>否 </option>
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
    <td rowspan="5">
        <label class="Validform_label">六、<br/>文<br/>明<br/>生<br/>产<br/>(7分)</label>
    </td>
    <td>
        <label class="Validform_label">设备<br/>设置</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.井下移动电气设备上架，小型电器设置规范、可靠；<br/>
        2.标志牌内容齐全；<br/>
        3.防爆电气设备和小型防爆电器有防爆入井检查合格
        证；<br/>
        4.各种设备表面清洁，无锈蚀
        </label>
    </td>
    <td><label class="Validform_label" name="lack42">1.5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。不符合要求 1
        处扣 0.2 分
        </label>
    </td>
    <td class="value">
        <select name="lack42">
            
            <option value="Y" <c:if test="${scoreDetail.lack42=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack42=='N' || scoreDetail.lack42==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">管网</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.各种管路应每100m设置标识，标明管路规格、用途、
        长度、管路编号等；<br/>
        2.管路敷设（吊挂）符合要求，稳固；<br/>
        3.无锈蚀，无跑、冒、滴、漏
        </label>
    </td>
    <td><label class="Validform_label" name="lack43">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。不符合要求 1
        处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack43">
            
            <option value="Y" <c:if test="${scoreDetail.lack43=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack43=='N' || scoreDetail.lack43==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">机房<br/>卫生</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.机房硐室、机道和电缆沟内外卫生清洁；<br/>
        2.无积水，无油垢，无杂物；<br/>
        3.电缆、管路排列整齐
        </label>
    </td>
    <td><label class="Validform_label" name="lack44">1.5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。卫生不好或电
        缆排列不整齐 1 处扣
        0.2 分，其他 1 处不符
        合要求扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack44">
            
            <option value="Y" <c:if test="${scoreDetail.lack44=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack44=='N' || scoreDetail.lack44==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">照明</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        机房、硐室以及巷道等照明符合《煤矿安全规程》要求
        </label>
    </td>
    <td><label class="Validform_label" name="lack45">1</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。不符合要求 1
        处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack45">
            
            <option value="Y" <c:if test="${scoreDetail.lack45=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack45=='N' || scoreDetail.lack45==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">器材<br/>工具</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        消防器材、电工操作绝缘用具齐全合格
        </label>
    </td>
    <td><label class="Validform_label" name="lack46">1</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。消防器材、绝
        缘用具欠缺、失效或无
        合格证 1 处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack46">
            
            <option value="Y" <c:if test="${scoreDetail.lack46=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack46=='N' || scoreDetail.lack46==null}">selected="selected"</c:if>>否 </option>
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
