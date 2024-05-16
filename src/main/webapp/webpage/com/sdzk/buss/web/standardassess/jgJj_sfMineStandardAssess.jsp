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
</head>
<body>
<%--<c:if test="${from ne 'mineAssess'}">--%>
    <%--<!-- from :mineAssess 页面请求来源于矿井考核自评汇总的时候不显示该面包屑，否则显示面包屑 -->--%>
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->井工煤矿->掘进</div>--%>
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
        <td colspan='7' align="center" style="font-size:20px;font-weight: bold;">煤矿掘进标准化评分表</td>
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
    <td rowspan="2">
    	<label class="Validform_label">
        一、<br>生<br>产<br>组<br>织<br>（5分）
        </label>
    </td>
    <td>
        <label class="Validform_label">机械<br/>化程<br/>度</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.煤巷、半煤岩巷综合机械化程度不低于 50%；<br/>
        2.条件适宜的岩巷宜采用综合机械化掘进；<br/>
        3.采用机械装、运煤（矸）；<br/>
        4.材料、设备采用机械运输，人工运料距离不超过 300m
        </label>
    </td>
    <td><label class="Validform_label" name="lack1">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。煤巷、
        半煤岩巷综合机械化
        程度不符合要求、没有
        采用机械化装运煤
        （矸）不得分，条件适
        宜的岩巷没有采用综
        掘的扣 0.1 分，人工运
        料距离超过规定每增
        加 20m，扣 0.1 分
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
        <label class="Validform_label">劳动<br/>组织</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.掘进作业应按循环作业图表施工；<br/>
        2.完成考核周期内进尺计划；<br/>
        3.掘进队伍工种配备满足作业要求
        </label>
    </td>
    <td><label class="Validform_label" name="lack2">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。不符合
        要求 1 项扣 1 分
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
    <td rowspan="2">
        <label class="Validform_label">二、<br/>设<br/>备<br/>管<br/>理<br/>（15分）</label>
    </td>
    <td>
        <label class="Validform_label">掘进<br/>机械</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.掘进施工机（工）具完好；<br/>
        2.掘进机械设备完好，截割部运行时人员不在截割臂下
        停留和穿越，机身与煤（岩）壁之间不站人；综掘机铲
        板前方和截割臂附近无人时方可启动,停止工作和交接
        班时按要求停放综掘机，将切割头落地，并切断电源；
        移动电缆有吊挂、拖曳、收放、防拔脱装置，并且完好；
        掘进机、掘锚一体机、连续采煤机、梭车、锚杆钻车装
        设甲烷断电仪或者便携式甲烷检测报警仪；<br/>
        3.使用掘进机、掘锚一体机、连续采煤机掘进时，开机、
        退机、调机时发出报警信号，设备非操作侧设有急停按
        钮（连续采煤机除外），有前照明和尾灯；内外喷雾使
        用正常；<br/>
        4.安装机载照明的掘进机后配套设备（如锚杆钻车等）
        启动前开启照明；<br/>
        5.耙装机装设有封闭式金属挡绳栏和防耙斗出槽的护
        栏，固定钢丝绳滑轮的锚桩及其孔深和牢固程度符合作
        业规程规定，机身和尾轮应固定牢靠；上山施工倾角大
        于 20°时，在司机前方设有护身柱或挡板，并在耙装机
        前增设固定装置；在斜巷中使用耙装机时有防止机身下
        滑的措施。耙装机距工作面的距离符合作业规程规定。
        耙装机作业时有照明。高瓦斯、煤与瓦斯突出和有煤尘
        爆炸危险性的矿井煤巷、半煤岩巷掘进工作面和石门揭
        煤工作面，不使用钢丝绳牵引的耙装机
        </label>
    </td>
    <td><label class="Validform_label" name="lack3">8</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。不符合 要求 1 项扣 1 分
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
        <label class="Validform_label">运输<br/>系统</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.后运配套系统设备设施能力匹配；<br/>
        2.运输设备完好，电气保护齐全可靠；<br/>
        3.刮板输送机、带式输送机减速器与电动机实现软启动
        或软连接，液力偶合器不使用可燃性传动介质（调速型
        液力偶合器不受此限），使用合格的易熔塞和防爆片；
        开关上架，电气设备不被淋水；机头、机尾固定牢固；
        行人跨越处设过桥；<br/>
        4.带式输送机胶带阻燃和抗静电性能符合规定，有防打
        滑、防跑偏、防堆煤、防撕裂等保护装置，装设温度、
        烟雾监测装置和自动洒水装置；机头、机尾应有安全防
        护设施;机头处有防灭火器材；连续运输系统安设有连
        锁、闭锁控制装置，沿线安设有通信和信号装置；采用
        集中综合智能控制方式；上运时装设防逆转装置和制动
        装置，下运时装设软制动装置且装设有防超速保护装
        置；大于 16º 的斜巷中使用带式输送机设置防护网，并
        采取防止物料下滑、滚落等安全措施；机头尾处设置有
        扫煤器；支架编号管理；托辊齐全、运转正常；<br/>
        5.轨道运输设备安设符合要求，制动可靠，声光信号齐
        全；轨道铺设符合要求；钢丝绳及其使用符合《煤矿安
        全规程》要求；其他辅助运输设备符合规定
        </label>
    </td>
    <td><label class="Validform_label" name="lack4">7</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。不符合 要求 1 项扣 1 分
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
    <td rowspan="3">
        <label class="Validform_label">三、<br/>技<br/>术<br/>保<br/>障<br/>（10分）</label>
    </td>
    <td>
        <label class="Validform_label">监测<br/>控制</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.煤巷、半煤岩巷锚杆、锚索支护巷道进行顶板离层观
        测，并填写记录牌板；进行围岩观测并分析、预报；<br/>
        2.根据地质及水文地质预报制定安全技术措施，落实到
        位；<br/>
        3. 做到有疑必探，先探后掘
        </label>
    </td>
    <td><label class="Validform_label" name="lack5">2</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。1 项不符合要求不得分
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
        <label class="Validform_label">现场<br/>图牌<br/>板</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        作业场所安设巷道平面布置图、施工断面图、炮眼布置
        图、爆破说明书(断面截割轨迹图)、正规循环作业图表
        等，图牌板内容齐全、图文清晰、正确、保护完好，安
        设位置便于观看
        </label>
    </td>
    <td><label class="Validform_label" name="lack6">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。不符合要求 1处扣 1 分
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
        <label class="Validform_label">规程<br/>措施</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.作业规程编制、审批符合要求，矿总工程师定期组织
        对作业规程的贯彻、执行情况进行检查，地质及水文地
        质条件发生较大变化时，及时修改完善作业规程或补充
        安全措施并组织实施；<br/>
        2.作业规程中明确巷道施工工艺、临时支护及永久支护
        的形式和支护参数、永久支护距掘进工作面的距离等，
        并制定防止冒顶、片帮的安全措施；<br/>
        3.巷道开掘、贯通前组织现场会审并制定专门措施；<br/>
        4.过采空区、老巷、断层、破碎带和岩性突变地带等应
        有针对性措施
        </label>
    </td>
    <td><label class="Validform_label" name="lack7">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。无作业
        规程、审批手续不合格
        或无措施施工的扣 5
        分，其他 1 处不符合要
        求扣 1 分
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
    <td rowspan="2">
        <label class="Validform_label">四、<br/>岗<br/>位<br/>规<br/>定<br/>(10分)</label>
    </td>
    <td>
        <label class="Validform_label">专业<br/>技能</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.建立并执行本岗位安全生产责任制；<br/>
        2.管理和技术人员掌握作业规程，作业人员熟知本岗位
        操作规程和作业规程相关内容
        </label>
    </td>
    <td><label class="Validform_label" name="lack8">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。岗位安
        全生产责任制不全，每
        缺 1 个岗位扣 2 分,
        其他 1 处不符合要求
        扣 1 分
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
    <td>
        <label class="Validform_label">规范<br/>作业</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.现场作业人员按操作规程及作业规程、措施施工；<br/>
        2.无“三违”行为；<br/>
        3.零星工程有针对性措施，有管理人员跟班；<br/>
        4.作业前进行安全确认
        </label>
    </td>
    <td><label class="Validform_label" name="lack9">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。发现
        “三违”行为不得分，
        其他 1 处不符合要求
        扣 1 分
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
    <td rowspan="11">
        <label class="Validform_label">五、<br/>工<br/>程<br/>质<br/>量<br/>与<br/>安<br/>全<br/>(50分)</label>
    </td>
    <td>
        <label class="Validform_label">保障<br/>机制</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.建立工程质量考核制度，各种检查有现场记录；<br/>
        2.有班组检查验收记录
        </label>
    </td>
    <td><label class="Validform_label" name="lack10">5</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。班组无
        工程质量检查验收记
        录不得分，其他 1 处不
        符合要求扣 0.5 分
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
    <td >
        <label class="Validform_label">安全<br/>管控</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.永久支护距掘进工作面距离符合作业规程规定；<br/>
        2.执行敲帮问顶制度，无空顶作业，空帮距离符合规程
        规定；<br/>
        3.临时支护形式、数量、安装质量符合作业规程要求；<br/>
        4.架棚支护棚间装设有牢固的撑杆或拉杆，可缩性金属
        支架应用金属拉杆，距掘进工作面 10m 内架棚支护爆破
        前进行加固；<br/>
        5.无失修巷道，运输设备完好、各种安全设施齐全可靠；<br/>
        6.压风、供水系统压力等符合施工要求
        </label>
    </td>
    <td><label class="Validform_label" name="lack11">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。出现空顶作业
        不得分，不按规程、措
        施施工 1 处扣 3 分，其
        他1处不符合要求扣1
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
    <td rowspan="4">
        <label class="Validform_label">规格<br/>质量</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.巷道净宽误差符合以下要求：锚网（索）、锚喷、钢
        架喷射混凝土巷道有中线的 0mm～100mm，无中线的
        -50mm～200mm；刚性支架、预制混凝土块、钢筋混凝土
        弧板、钢筋混凝土巷道有中线的 0mm～50mm，无中线的
        -30mm～80mm；可缩性支架巷道有中线的 0mm～100mm，
        无中线的-50mm～100mm
        </label>
    </td>
    <td rowspan="4"><label class="Validform_label" name="lack12">12</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        测点 1 处不符合
        要求但不影响安全使
        用的扣 0.5 分，影响安
        全使用的扣 3 分
        </label>
    </td>
    <td class="value" rowspan="4">
        <select name="lack12">
            
            <option value="Y" <c:if test="${scoreDetail.lack12=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack12=='N' || scoreDetail.lack12==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>

    <td class="value" style="max-width:150px;" rowspan="4">
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
        2.巷道净高误差符合以下要求：锚网背（索）、锚喷巷
        道有腰线的 0mm～100mm，无腰线的-50mm～200mm；刚性
        支架巷道有腰线的-30mm～50mm，无腰线的-30 mm～
        50mm；钢架喷射混凝土、可缩性支架巷道-30mm ～
        100mm；裸体巷道有腰线的 0mm～150mm，无腰线的
        -30mm～200mm；预制混凝土、钢筋混凝土弧板、钢筋混
        凝土有腰线的 0mm～50mm，无腰线的-30mm～80mm
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        取不少于 3 个检查点检查，
        测点不符合要求但不
        影响安全使用的 1 处
        扣 0.5 分，影响安全使
        用的 1 处扣 3 分
        </label>
    </td>
</tr>

<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        3.巷道坡度偏差不得超过±1%。
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。
        少于 3 个检查点检查，
        不符合要求 1 处扣 1
        分
        </label>
    </td>
</tr>

<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        4.巷道水沟误差应符合以下要求：中线至内沿距离
        -50mm～50mm，腰线至上沿距离-20mm～20mm，深度、宽
        度-30mm～30mm，壁厚-10mm
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。按表 7-2 取不
        少于 3 个检查点现场
        检查，不符合要求 1
        处扣 0.5 分
        </label>
    </td>
</tr>

<tr>
    <td rowspan="4">
        <label class="Validform_label">内在<br/>质量</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.锚喷巷道喷层厚度不低于设计值 90%（现场每 25m 打
        一组观测孔，一组观测孔至少 3 个且均匀布置），喷射
        混凝土的强度符合设计要求，基础深度不小于设计值的
        90%
        </label>
    </td>
    <td rowspan="4"><label class="Validform_label" name="lack13">13</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。未检查
        喷射混凝土强度扣 6
        分，无观测孔扣 2 分，
        喷层厚度不符合要求
        1 处扣 1 分，其他 1 处
        不符合要求扣 0.5 分
        </label>
    </td>
    <td class="value" rowspan="4">
        <select name="lack13">
            
            <option value="Y" <c:if test="${scoreDetail.lack16=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack16=='N' || scoreDetail.lack16==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>
    <td class="value" style="max-width:150px;" rowspan="4">
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
        2.光面爆破眼痕率符合以下要求：硬岩不小于 80％、中
        硬岩不小于 50％、软岩周边成型符合设计轮廓；煤巷、
        半煤岩巷道超（欠）挖不超过 3 处（直径大于 500mm，
        深度：顶大于 250mm、帮大于 200mm）
        </label>
    </td>

    <td class = "td-padding">
        <label class="Validform_label">
        查现场和资料。没有进
        行眼痕检查扣 3 分，其
        他 1 处不符合要求扣
        0.5 分
        </label>
    </td>
</tr>
<tr>

    <td class = "td-padding">
        <label class="Validform_label">
        3.锚网索巷道锚杆（索）安装、螺母扭矩、抗拔力、网
        的铺设连接符合设计要求，锚杆（索）的间、排距偏差
        -100 mm～100mm，锚杆露出螺母长度 10mm～50mm（全螺
        纹锚杆 10mm～100mm），锚索露出锁具长度 150mm～
        250mm，锚杆与井巷轮廓线切线或与层理面、节理面裂
        隙面垂直，最小不小于 75°，抗拔力、预应力不小于设
        计值的 90%
        </label>
    </td>

    <td class = "td-padding">
        <label class="Validform_label">
        查现场。锚杆螺母扭矩
        连续 3 个不符合要求
        扣 5 分，抗拔力、预应
        力不符合要求1处扣1
        分，其他 1 处不符合要
        求扣 0.5 分
        </label>
    </td>
</tr>
<tr>
    <td class = "td-padding">
        <label class="Validform_label">
        4.刚性支架、钢架喷射混凝土、可缩性支架巷道偏差符
        合以下要求：支架间距不大于 50mm、梁水平度不大于
        40mm、支架梁扭距不大于 50mm、立柱斜度不大于 1°，
        水平巷道支架前倾后仰不大于 1°，柱窝深度不小于设
        计值；撑（或拉）杆、垫板、背板的位置、数量、安设
        形式符合要求；倾斜巷道每增加 5°支架迎山角增加 1°
        </label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。取不
        少于 3 个检查点现场
        检查，不符合要求 1
        处扣 0.5 分
        </label>
    </td>
</tr>

<tr>
    <td>
        <label class="Validform_label">材料<br/>质量</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.各种支架及其构件、配件的材质、规格，及背板和充
        填材质、规格符合设计要求；<br/>
        2.锚杆（索)的杆体及配件、网、锚固剂、喷浆材料等
        材质、品种、规格、强度等符合设计要求
        </label>
    </td>
    <td><label class="Validform_label" name="lack14">10</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查资料和现场。现场使
        用不合格材料不得分，
        其他 1 处不符合要求
        扣 1 分
        </label>
    </td>
    <td class="value">
        <select name="lack14">
            
            <option value="Y" <c:if test="${scoreDetail.lack20=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack20=='N' || scoreDetail.lack20==null}">selected="selected"</c:if>>否 </option>
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
        <label class="Validform_label">六、<br/>文<br/>明<br/>生<br/>产<br/>(10分)</label>
    </td>
    <td>
        <label class="Validform_label">灯光<br/>照明</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        转载点、休息地点、车场、图牌板及硐室等场所照明符
        合要求
        </label>
    </td>
    <td><label class="Validform_label" name="lack15">3</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。不符合要求 1
        处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack15">
            
            <option value="Y" <c:if test="${scoreDetail.lack21=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack21=='N' || scoreDetail.lack21==null}">selected="selected"</c:if>>否 </option>
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
    <td>
        <label class="Validform_label">作业<br/>环境</label>
    </td>
    <td class = "td-padding">
        <label class="Validform_label">
        1.现场整洁，无浮渣、淤泥、积水、杂物等，设备清洁，
        物料分类、集中码放整齐，管线吊挂规范；<br/>
        2.材料、设备标志牌齐全、清晰、准确，设备摆放、物
        料码放与胶带、轨道等留有足够的安全间隙；<br/>
        3.巷道至少每 100m 设置醒目的里程标志
        </label>
    </td>
    <td><label class="Validform_label" name="lack16">7</label></td>
    <td class = "td-padding">
        <label class="Validform_label">
        查现场。不符合要求 1
        处扣 0.5 分
        </label>
    </td>
    <td class="value">
        <select name="lack16">
            
            <option value="Y" <c:if test="${scoreDetail.lack22=='Y'}">selected="selected"</c:if>>是 </option>
            <option value="N" <c:if test="${scoreDetail.lack22=='N' || scoreDetail.lack22==null}">selected="selected"</c:if>>否 </option>
        </select>
    </td>
    <td class="value" style="max-width:150px;">
        <input name="score16" type="text" style="width: 150px" class="inputxt"  value="${scoreDetail.score16}" >
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
