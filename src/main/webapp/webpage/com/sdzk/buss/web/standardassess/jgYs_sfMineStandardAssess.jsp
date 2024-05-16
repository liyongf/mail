<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>运输</title>
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
</head>
<body>
<%--<c:if test="${from ne 'mineAssess'}">--%>
    <%--<!-- from :mineAssess 页面请求来源于矿井考核自评汇总的时候不显示该面包屑，否则显示面包屑 -->--%>
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->井工煤矿->运输</div>--%>
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
        <td colspan='7' align="center" style="font-size:20px;font-weight: bold;">煤矿运输标准化评分表</td>
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
    <td rowspan="3">
        <label class="Validform_label">
        一、<br>巷<br>道<br>硐<br>室<br>（8分）
        </label>
    </td>
    <td>
        <label class="Validform_label">巷道<br/>车场</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.巷道支护完整，巷道（包括管、线、电缆）与运输设
        备最突出部分之间的最小间距符合《煤矿安全规程》规
        定；<br/>
        2.车场、车房、巷道曲线半径、巷道连接方式、运输方
        式设计合理，符合《煤矿安全规程》及有关规定要求
        </label>
    </td>
    <td rowspan="3"><label class="Validform_label" name="lack1">8</label></td>
    <td class = "td-padding" rowspan="3">
        <label class="Validform_label">
        查现场。不符合要求 1
        处扣 2 分
        </label>
    </td>
    <td class="value" rowspan="3">
        <select name="lack1">
            
            <option value="Y" <c:if test="${scoreDetail.lack1=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack1=='N' || scoreDetail.lack1==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;" rowspan="3">
        <input  name="score1" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score1}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value" rowspan="3" >
        <textarea name="remark1"  rows="3" class="inputxt">${scoreDetail.remark1}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">硐室<br/>车房</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        斜巷信号硐室、躲避硐、运输绞车车房、候车室、调度
        站、人车库、充电硐室、错车硐室、车辆检修硐室等符
        合《煤矿安全规程》及有关规定要求
        </label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">装卸<br/>载站</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        车辆装载站、卸载站和转载站符合《煤矿安全规程》及
        有关规定要求
        </label>
    </td>
</tr>

<tr>
    <td rowspan="11">
        <label class="Validform_label">
        二、<br>运<br>输<br>线<br>路<br>（32分）
        	</label>
    </td>

    <td rowspan="6">
        <label class="Validform_label">轨道<br/>（道<br/>路）<br/>系统</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.运行 7t 及以上机车、3t 及以上矿车，或者运送 15t
        及以上载荷的矿井主要水平运输大巷、车场、主要运输
        石门、采区主要上下山、地面运输系统轨道线路使用不
        小于 30kg/m 的钢轨；其他线路使用不小于 18kg/m 的钢
        轨
        </label>
    </td>
    <td rowspan="6"><label class="Validform_label" name="lack2">18</label></td>
    <td class = "td-padding" >
        <label class="Validform_label">
        查现场。1 处不符合要
        求扣 3 分，单项扣至
        10 分为止
        </label>
    </td>
    <td class="value" rowspan="6">
        <select name="lack2">

            <option value="Y" <c:if test="${scoreDetail.lack2=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack2=='N' || scoreDetail.lack2==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;" rowspan="6">
        <input  name="score2" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score2}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value" rowspan="6" >
        <textarea name="remark2"  rows="3" class="inputxt">${scoreDetail.remark2}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        2.主要运输线路（主要运输大巷和主要运输石门、井底
        车场、主要绞车道，地面运煤、运矸干线和集中运载站
        车场的轨道）及行驶人车的轨道线路质量达到以下要
        求：<br/>
        ⑴接头平整度：轨面高低和内侧错差不大于 2mm；<br/>
        ⑵轨距：直线段和加宽后的曲线段允许偏差为-2mm～
        5mm；<br/>
        ⑶水平：直线段及曲线段加高后两股钢轨偏差不大于
        5mm；<br/>
        ⑷轨缝不大于 5mm；<br/>
        ⑸扣件齐全、牢固,与轨型相符；<br/>
        ⑹轨枕规格及数量应符合标准要求，间距偏差不超过
        50mm；<br/>
        ⑺道碴粒度及铺设厚度符合标准要求，轨枕下应捣实；<br/>
        ⑻曲线段设置轨距拉杆
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。抽查 1～3 条
        巷道，接头平整度、轨
        距、水平不符合要求 1
        处扣 0.5 分，其他 1
        处不合格扣 0.2 分，单
        项扣至 10 分为止
        </label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        3.其他轨道线路不得有杂拌道（异型轨道长度小于 50m
        为杂拌道），质量应达到以下要求：<br/>
        ⑴接头平整度：轨面高低和内侧错差不大于 2mm；<br/>
        ⑵轨
        距：直线段和加宽后的曲线段允许偏差为-2mm～6mm；<br/>
        ⑶水平：直线段及曲线段加高后两股钢轨偏差不大于
        8mm；<br/>
        ⑷轨缝不大于 5mm；<br/>
        ⑸扣件齐全、牢固,与轨型相符；<br/>
        ⑹轨枕规格及数量符合标准要求，间距偏差不超过
        50mm；<br/>
        ⑺道碴粒度及铺设厚度符合标准要求，轨枕下应捣实
        </label>
    </td>
    <td class = "td-padding" >
        <label class="Validform_label">
        查现场。抽查 1～3 条
        巷道 ， 接头平整度、
        轨距、水平不符合要求
        1 处扣 0.3 分，其他 1
        处不合格扣 0.1 分，单
        项扣至 7 分为止
        </label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        4.异型轨道线路、齿轨线路质量符合设计及说明书要求
        </label>
    </td>
    <td class = "td-padding" >
        <label class="Validform_label">
        查现场。不符合要求 1
        处扣 1 分，单项扣至 5
        分为止
        </label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        5.单轨吊车线路达到以下要求：<br/>
        ⑴下轨面接头间隙直线段不大于 3mm；<br/>
        ⑵接头高低和左右允许偏差分别为 2mm 和 1mm；<br/>
        ⑶接头摆角垂直不大于 7°，水平不大于 3°；<br/>
        ⑷水平弯轨曲率半径不小于 4m，垂直弯轨曲率半径不小
        于 l0m；<br/>
        ⑸起始端、终止端设置轨端阻车器
        </label>
    </td>
    <td class = "td-padding" >
        <label class="Validform_label">
        查现场。轨端阻车器不
        符合要求扣 3 分，其他
        1 处不符合要求扣 0.5
        分，单项扣至 5 分为止
        </label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        6.无轨胶轮车主要道路采用混凝土、铺钢板等方式硬化
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。不符合要求 1
        处扣 1 分，单项扣至 5
        分为止
        </label>
    </td>
</tr>

<tr>
    <td rowspan="2">
        <label class="Validform_label">道岔</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.道岔轨型不低于线路轨型，无非标准道岔，道岔质量
        达到以下要求：<br/>
        ⑴轨距按标准加宽后及辙岔前后轨距偏差不大于+3mm；<br/>
        ⑵水平偏差不大于 5mm；<br/>
        ⑶接头平整度：轨面高低及内侧错差不大于 2mm；<br/>
        ⑷尖轨尖端与基本轨密贴，间隙不大于 2mm，无跳动，
        尖轨损伤长度不超过 100mm，在尖轨顶面宽 20mm 处与基
        本轨高低差不大于 2mm；<br/>
        ⑸心轨和护轨工作边间距按标准轨距减小 28mm 后，偏
        差+2mm；<br/>
        ⑹扣件齐全、牢固，与轨型相符；<br/>
        ⑺轨枕规格及数量符合标准要求，间距偏差不超过
        50mm，轨枕下应捣实<br/>
        </label>
    </td>
    <td rowspan="2"><label class="Validform_label" name="lack3">5</label></td>
    <td class = "td-padding" >
        <label class="Validform_label">
        查现场。轨距、水平、
        接头平整度、尖轨、心
        轨和护轨工作边间距
        不符合要求 1 处扣 1
        分，其他 1 处不符合要
        求扣 0.5 分
        </label>
    </td>
    <td class="value" rowspan="2">
        <select name="lack3">
            
            <option value="Y" <c:if test="${scoreDetail.lack3=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack3=='N' || scoreDetail.lack3==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;" rowspan="2">
        <input  name="score3" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score3}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value" rowspan="2" >
        <textarea name="remark3"  rows="3" class="inputxt">${scoreDetail.remark3}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        2.单轨吊道岔达到以下要求：<br/>
        ⑴道岔框架 4 个悬挂点的受力应均匀，固定点数均匀分
        布不少于 7 处；<br/>
        ⑵下轨面接头轨缝不大于 3mm；<br/>
        ⑶轨道无变形，活动轨动作灵敏，准确到位；<br/>
        ⑷机械闭锁可靠；<br/>
        ⑸连接轨断开处设有轨端阻车器
        </label>
    </td>
    <td class = "td-padding" >
        <label class="Validform_label">
        查现场。机械闭锁、轨
        端阻车器不符合要求
        1 处扣 3 分，其他 1 处
        不符合要求扣 1 分
        </label>
    </td>
</tr>

<tr>
    <td >
        <label class="Validform_label">窄轨<br/> 架线<br/>电机<br/>车牵<br/>引网<br/>络</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.敷设质量达到以下要求：<br/>
        ⑴架空线悬挂高度：自轨面算起，架空线悬挂高度在行
        人的巷道内、车场内以及人行道与运输巷道交叉的地方
        不小于 2m；在不行人的巷道内不小于 1.9m；在井底车
        场内，从井底到乘车场不小于 2.2m；在地面或工业场地
        内，不与其他道路交叉的地方不小于 2.2m；<br/>
        ⑵架空线与巷道顶或棚梁之间的距离不小于 0.2m；悬吊
        绝缘子距架空线的距离，每侧不超过 0.25m；<br/>
        ⑶架空线悬挂点的间距，直线段内不超过 5m，曲线段内
        符合规定；<br/>
        ⑷架空线直流电压不超过 600V；<br/>
        ⑸两平行钢轨之间每隔 50m 连接 1 根断面不小于 50mm
        <sup>2</sup>
        的铜线或者其他具有等效电阻的导线。线路上所有钢轨
        接缝处，用导线或者采用轨缝焊接工艺加以连接。连接
        后每个接缝处的电阻符合要求；<br/>
        ⑹不回电的轨道与架线电机车回电轨道之间，应加以绝
        缘。第一绝缘点设在 2 种轨道的连接处；第二绝缘点设
        在不回电的轨道上，其与第一绝缘点之间的距离应大于
        1 列车的长度。在与架线电机车线路相连通的轨道上有
        钢丝绳跨越时，钢丝绳不得与轨道相接触；<br/>
        ⑺绝缘点应经常检查维护，保持可靠绝缘；<br/>
        2.电机车架空线巷道乘人车场装备有架空线自动停送
        电开关
        </label>
    </td>
    <td><label class="Validform_label" name="lack4">4</label></td>
    <td class = "td-padding" >
        <label class="Validform_label">
        查现场。架空线悬挂高
        度、架空线与巷道顶或
        棚梁之间的距离、悬吊
        绝缘子距架空线的距
        离、架空线悬挂点间
        距、架空线直流电压绝
        缘点不符合要求 1 处
        扣 2 分。其他 1 处不符
        合要求扣 0.5 分。架空
        线巷道乘人车场未装
        备有架空线自动停送
        电开关的不得分
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
        <textarea name="remark4"  rows="3" class="inputxt">${scoreDetail.remark4}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>

<tr>
    <td rowspan="2">
        <label class="Validform_label">运输<br/> 方式<br/>改善</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.长度超过 1.5km 的主要运输平巷或者高差超过 50m 的
        人员上下的主要倾斜井巷，应采用机械方式运送人员
        </label>
    </td>
    <td rowspan="2"><label class="Validform_label" name="lack5">5</label></td>
    <td class = "td-padding" >
        <label class="Validform_label">
        查现场。1 处不符合要
        求扣 5 分
        </label>
    </td>
    <td class="value" rowspan="2">
        <select name="lack5">

            <option value="Y" <c:if test="${scoreDetail.lack5=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack5=='N' || scoreDetail.lack5==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;" rowspan="2">
        <input  name="score5" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score5}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value" rowspan="2" >
        <textarea name="remark5"  rows="3" class="inputxt">${scoreDetail.remark5}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        2.逐步淘汰斜巷（井）人车提升，采用其他方式运送人员； <br/>
        3.水平单翼距离超过 4000m 时，有缩短运输距离的有效
        措施； <br/>
        4.采用其他运输方式替代多级、多段运输
        </label>
    </td>
    <td class = "td-padding" >
        <label class="Validform_label">
        查现场。不符合要求 1
        处扣 0.1 分
        </label>
    </td>
</tr>

<tr>
    <td rowspan="9">
        <label class="Validform_label">三、<br/>运<br/>输<br/>设<br/>备<br/>（22分）</label>
    </td>
    <td rowspan="2">
        <label class="Validform_label">设备<br/>管理</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.在用运输设备综合完好率不低于 90%；矿车、专用车
        完好率不低于 85%；运送人员设备完好率 100%
        </label>
    </td>
    <td rowspan="2"><label class="Validform_label" name="lack6">5</label></td>
    <td class = "td-padding" >
        <label class="Validform_label">
        查现场。完好率每降低
        1 个百分点扣 0.2 分。
        综合完好率低于 70%
        扣 5 分，矿车、专用车
        完好率低于 60%扣 5
        分。运送人员设备 1
        台不完好扣 2.5 分
        </label>
    </td>
    <td class="value" rowspan="2">
        <select name="lack6">
            
            <option value="Y" <c:if test="${scoreDetail.lack6=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack6=='N' || scoreDetail.lack6==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;" rowspan="2">
        <input  name="score6" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score6}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value" rowspan="2">
        <textarea name="remark6"  rows="3" class="inputxt">${scoreDetail.remark6}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        2.人行车、架空乘人装置、机车、调度绞车、无极绳连
        续牵引车、绳牵引卡轨车、绳牵引单轨吊车、单轨吊车、
        齿轨车、无轨胶轮车、矿车、专用车等运输设备编号管
        理
        </label>
    </td>
    <td class = "td-padding" >
        <label class="Validform_label">
        查现场。不符合要求 1
        处扣 0.5 分
        </label>
    </td>
</tr>

<tr>
    <td>
        <label class="Validform_label">普通<br/>轨斜<br/>巷<br/>（井）<br/>人车</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.制动装置齐全、灵敏、可靠；<br/>
        2.装备有跟车工在运行途中任何地点都能发送紧急停
        车信号的装置，并具有通话和信号发送、接收功能，灵
        敏可靠
        </label>
    </td>
    <td rowspan="7"><label class="Validform_label" name="lack7">17</label></td>
    <td class = "td-padding" >
        <label class="Validform_label">
        查现场。1 处不符合要
        求“运输设备”大项不
        得分
        </label>
    </td>
    <td class="value" rowspan="7">
        <select name="lack7">
            
            <option value="Y" <c:if test="${scoreDetail.lack7=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack7=='N' || scoreDetail.lack7==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;" rowspan="7">
        <input  name="score7" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score7}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value" rowspan="7">
        <textarea name="remark7"  rows="3" class="inputxt">${scoreDetail.remark7}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>

<tr>
    <td>
        <label class="Validform_label">架空<br/>乘人<br/>装置</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.架空乘人装置正常运行。每日至少对整个装置进行 1
        次检查；<br/>
        2.工作制动装置和安全制动装置齐全、可靠；<br/>
        3.运行坡度、运行速度不得超过《煤矿安全规程》规定；<br/>
        4.装设超速、打滑、全程急停、防脱绳、变坡点防掉绳、<br/>
        张紧力下降、越位等保护装置，并达到齐全、灵敏、可
        靠；<br/>
        5.有断轴保护措施；<br/>
        6.各上下人地点装备通信信号装置，具备通话和信号发
        送接收功能，灵敏、可靠；<br/>
        7.沿线设有延时启动声光预警信号及便于人员操作的
        紧急停车装置；<br/>
        8.减速器应设置油温检测装置，当油温异常时能发出报
        警信号；<br/>
        9.钢丝绳安全系数、插接长度、断丝面积、直径减小量、
        锈蚀程度符合《煤矿安全规程》规定
        </label>
    </td>
    <td class = "td-padding" rowspan="6">
        <label class="Validform_label">
        查现场和资料。未按规
        定装设有关装置扣 2
        分，其他 1 处不符合要
        求扣 1 分，单项扣至 10
        分为止
        </label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">机车</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.制动装置符合规定，齐全、可靠；<br/>
        2.列车或者单独机车前有照明、后有红灯；<br/>
        3.警铃(喇叭)、连接装置和撒砂装置完好；<br/>
        4.同一水平行驶 5 台及以上机车时，装备机车运输集中
        信号控制系统及机车通信设备；同一水平行驶 7 台及以
        上机车时，装备机车运输监控系统；<br/>
        5.新建投产的大型矿井的井底车场和运输大巷，装备机
        车运输监控系统或者运输集中信号控制系统；<br/>
        6.防爆蓄电池机车或者防爆柴油机动力机车装备甲烷
        断电仪或者便携式甲烷检测报警仪；<br/>
        7.防爆柴油机动力机车装备自动保护装置和防灭火装
        置
        </label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">调度<br/>绞车</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.安装符合设计要求，固定可靠；<br/>
        2.制动装置符合规定，齐全、可靠；<br/>
        3.钢丝绳安全系数、断丝面积、直径减小量、锈蚀程度
        以及滑头、保险绳插接长度符合《煤矿安全规程》规定；
        4.声光信号齐全、完好
        </label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">无极<br/>绳连<br/>续牵<br/>引车、<br/>绳牵<br/>引卡<br/>轨车、<br/>绳牵<br/>引单<br/>轨吊<br/>车</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.闸灵敏可靠，使用正常；<br/>
        2.装备越位、超速、张紧力下降等安全保护装置，并正
        常使用；<br/>
        3.设置司机与相关岗位工之间的信号联络装置；设有跟
        车工时，应设置跟车工与牵引绞车司机联络用的信号和
        通信装置；<br/>
        4.驱动部、各车场设置行车报警和信号装置；<br/>
        5.钢丝绳安全系数、插接长度、断丝面积、直径减小量、
        锈蚀程度符合《煤矿安全规程》规定
        </label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">单轨<br/>吊车</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.具备 2 路以上相对独立回油的制动系统；<br/>
        2.设置既可手动又能自动的安全闸，并正常使用；<br/>
        3.超速保护、甲烷断电仪、防灭火设备等装置齐全、可
        靠；<br/>
        4.机车设置车灯和喇叭，列车的尾部设置红灯；<br/>
        5.柴油单轨吊车的发动机排气超温、冷却水超温、尾气
        水箱水位、润滑油压力等保护装置灵敏、可靠；<br/>
        6.蓄电池单轨吊车装备蓄电池容量指示器及漏电监测
        保护装置，且齐全、可靠
        </label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">无轨<br/>胶轮<br/>车</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.车辆转向系统、制动系统、照明系统、警示装置等完
        好可靠,车辆自带防止停车自溜的设施或工具；<br/>
        2.装备自动保护装置、便携式甲烷检测报警仪、防灭火
        设备等安全保护装置；<br/>
        3.行驶 5 台及以上无轨胶轮车时，装备车辆位置监测系
        统；<br/>
        4.装备有通信设备；<br/>
        5.运送人员应使用专用人车；<br/>
        6.载人或载货数量在额定范围内；<br/>
        7.运行速度，运人时不超过 25km/h，运送物料时不超过
        40km/h,车辆不空挡滑行
        </label>
    </td>
</tr>

<tr>
    <td rowspan="4">
        <label class="Validform_label">四、<br/>运<br/>输<br/>安<br/>全<br/>设<br/>施<br/>(20分)</label>
    </td>
    <td>
        <label class="Validform_label">挡车<br/>装置<br/>和跑<br/>车防<br/>护装<br/>置</label>
    </td>

    <td class = "td-padding">
        <label class="Validform_label">
        挡车装置和跑车防护装置齐全、可靠，并正常使用
        </label>
    </td>
    <td><label class="Validform_label" name="lack8">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。1 处不符合要求不得分
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
        <textarea name="remark8"  rows="3" class="inputxt">${scoreDetail.remark8}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">安全<br/>警示</label>
    </td>

    <td class = "td-padding">
        <label class="Validform_label">
        1.斜巷各车场及中间通道口装备有声光行车报警装置，
        并使用正常；<br/>
        2.斜巷双钩提升装备错码信号；<br/>
        3.弯道、井底车场、其他人员密集的地点、顶车作业区
        装备有声光预警信号装置，关键部位道岔装备有道岔位
        置指示器；<br/>
        4.各乘人地点悬挂有明显的停车位置指示牌；<br/>
        5.斜巷车场悬挂最大提升车辆数及最大提升载荷数的
        明确标识；<br/>
        6.无轨胶轮车运输巷道各岔口、错车点、弯道、车场等
        处设有行车指示等安全标志和信号；<br/>
        7.有轨运输与无轨运输交叉处、有轨运输行人通行处等
        危险路段设置有限速和警示装置
        </label>
    </td>
    <td><label class="Validform_label" name="lack9">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。未按规定装设1 处扣 1 分，
        装设但不符合要求 1 处扣 0.5分
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
        <textarea name="remark9"  rows="3" class="inputxt">${scoreDetail.remark9}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>

<tr>
    <td>
        <label class="Validform_label">物料<br/>捆绑</label>
    </td>

    <td class = "td-padding">
        <label class="Validform_label">
        捆绑固定牢固可靠，有防跑防滑措施
        </label>
    </td>
    <td rowspan="2"><label class="Validform_label" name="lack10">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。1 处不符合要
        求不得分
        </label>
    </td>

    <td class="value" rowspan="2">
        <select name="lack10">
            
            <option value="Y" <c:if test="${scoreDetail.lack10=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack10=='N' || scoreDetail.lack10==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;" rowspan="2">
        <input  name="score10" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score10}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value" rowspan="2">
        <textarea name="remark10"  rows="3" class="inputxt">${scoreDetail.remark10}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">连接<br/>装置</label>
    </td>

    <td class = "td-padding">
        <label class="Validform_label">
        保险链（绳）、连接环（链）、连接杆、插销、滑头及其连接方式符合规定
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">&nbsp;
    </td>
</tr>

<tr>
    <td rowspan="5">
        <label class="Validform_label">五、<br/>运<br/>输<br/>管<br/>理<br/>(10分)</label>
    </td>
    <td>
        <label class="Validform_label">组织<br/>保障</label>
    </td>

    <td class = "td-padding">
        <label class="Validform_label">
        有负责运输管理工作的机构
        </label>
    </td>
    <td rowspan="2"><label class="Validform_label" name="lack11">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。不符合要求 1处扣 2 分
        </label>
    </td>

    <td class="value" rowspan="2">
        <select name="lack11">
            
            <option value="Y" <c:if test="${scoreDetail.lack11=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack11=='N' || scoreDetail.lack11==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;" rowspan="2">
        <input  name="score11" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score11}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value" rowspan="2">
        <textarea name="remark11"  rows="3" class="inputxt">${scoreDetail.remark11}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td>
        <label class="Validform_label">制度<br/>保障</label>
    </td>

    <td class = "td-padding">
        <label class="Validform_label">
        包含以下内容：<br/>
        1.岗位安全生产责任制度<br/>
        2.运输设备运行、检修、检测等管理规定；<br/>
        3.运输安全设施检查、试验等管理规定；<br/>
        4.轨道线路检查、维修等管理规定；<br/>
        5.辅助运输安全事故汇报管理规定
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。每缺 1 种或每1 处不符合要求扣 0.5分
        </label>
    </td>
</tr>

<tr>
    <td>
        <label class="Validform_label">技术<br/>资料</label>
    </td>

    <td class = "td-padding">
        <label class="Validform_label">
        1.有运输设备、设施、线路的图纸、技术档案、维修记
        录；<br/>
        2.施工作业规程、技术措施符合有关规定；<br/>
        3.运输系统、设备选型和能力计算资料齐全
        </label>
    </td>
    <td rowspan="3"><label class="Validform_label" name="lack12">7</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。每缺 1 种或每1 处不符合要求扣 0.5分
        </label>
    </td>

    <td class="value" rowspan="3">
        <select name="lack12">
            
            <option value="Y" <c:if test="${scoreDetail.lack12=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack12=='N' || scoreDetail.lack12==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;" rowspan="3">
        <input  name="score12" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score12}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value" rowspan="3">
        <textarea name="remark12"  rows="3" class="inputxt">${scoreDetail.remark12}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td rowspan="2">
        <label class="Validform_label">检测<br/>检验</label>
    </td>

    <td class = "td-padding">
        <label class="Validform_label">
        1.更新或大修及使用中的斜巷（井）人车，有完整的重
        载全速脱钩测试报告及连接装置的探伤报告
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。不符合要求扣7 分
        </label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        2.新投用机车应测定制动距离，之后每年测定 1 次，有
        完整的制动距离测试报告；<br/>
        3.斜巷提升连接装置每年进行 1 次 2 倍于其最大静荷重
        的拉力试验，有完整的拉力试验报告；<br/>
        4.架空乘人装置、单轨吊车、无轨胶轮车、齿轨车、卡
        轨车、无极绳连续牵引车等按《煤矿安全规程》或相关
        规范要求进行检测、检验、试验，有完整的检测、检验、
        试验报告
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料。不符合要求 1处扣 1 分
        </label>
    </td>
</tr>

<tr>
    <td rowspan="2">
        <label class="Validform_label">六、<br/>岗<br/>位<br/>规<br/>范<br/>（5分）</label>
    </td>
    <td>
        <label class="Validform_label">专业<br/>技能</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.管理和技术人员掌握相关的规程、规范等有关内容；<br/>
        2.作业人员掌握本岗位操作规程、安全措施
        </label>
    </td>
    <td rowspan="2"><label class="Validform_label" name="lack13">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。不符合要求 1 处扣 0.5 分
        </label>
    </td>
    <td class="value" rowspan="2">
        <select name="lack13">
            
            <option value="Y" <c:if test="${scoreDetail.lack13=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack13=='N' || scoreDetail.lack13==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;" rowspan="2">
        <input  name="score13" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score13}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value" rowspan="2">
        <textarea name="remark13"  rows="3" class="inputxt">${scoreDetail.remark13}</textarea>
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
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。发现 1 人“三
        违”扣 5 分；其他不符
        合要求 1 处扣 1 分。
        </label>
    </td>
</tr>

<tr>
    <td rowspan="2">
        <label class="Validform_label">七、<br/>文<br/>明<br/>生<br/>产<br/>（3分）</label>
    </td>
    <td rowspan="2">
        <label class="Validform_label">作业<br/>场所</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.运输线路、设备硐室、车间等卫生整洁，设备清洁，
        材料分类、集中码放整齐
        </label>
    </td>
    <td rowspan="2"><label class="Validform_label" name="lack14">3</label></td>
    <td class = "td-padding" rowspan="2">
    	<label class="Validform_label">
        查资料和现场。不符合要求 1 处扣 0.2 分
        </label>
    </td>
    <td class="value" rowspan="2">
        <select name="lack14">
            
            <option value="Y" <c:if test="${scoreDetail.lack14=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack14=='N' || scoreDetail.lack14==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;" rowspan="2">
        <input  name="score14" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score14}" >
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">得分</label>
    </td>
    <td class="value" rowspan="2">
        <textarea name="remark14"  rows="3" class="inputxt">${scoreDetail.remark14}</textarea>
        <span class="Validform_checktip"></span>
        <label class="Validform_label" style="display: none;">备注</label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        2.主要运输线路水沟畅通，巷道无淤泥、积水。水沟侧
        作为人行道时，盖板齐全、稳固
        </label>
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
                            console.log(d);
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
