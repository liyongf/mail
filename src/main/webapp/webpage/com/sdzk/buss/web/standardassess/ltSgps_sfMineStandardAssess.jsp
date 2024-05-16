<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>疏干排水</title>
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
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->露天煤矿->疏干排水</div>--%>
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
    <td colspan='8' align="center" style="font-size:20px;font-weight: bold;">露天煤矿疏干排水标准化评分</td>
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
    <td  rowspan="6">
        <label class="Validform_label">
            一、<br>技<br>术<br>管<br>理<br>（20分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">组织保障</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            有负责疏干排水工作的部门
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求不得分
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
            建立水文地质预测预报、疏干排水技术管理及疏干巷道雨季人员撤离制度
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。缺1项制度扣2分
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
    <td  rowspan="1">
        <label class="Validform_label">技术资料</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.综合水文地质图；<br>
            2.综合水文地质柱状图；<br>
            3.疏干排水系统平面图；<br>
            4.矿区地下水等水位线图；<br>
            5.疏干巷道竣工资料；<br>
            6.疏干巷道井上下对照图
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3">
            12
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。每缺1种图纸扣5分；图纸信息不全1处扣2分
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
    <td  rowspan="1">
        <label class="Validform_label">规划及计划</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            有防治水中长期规划、年度疏干排水计划及措施，并组织实施
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack4">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。无规划、计划及措施、未组织实施不得分
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
        <label class="Validform_label">水文地质</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查明地下水来水方向、渗透系数等；受地下水影响较大和已经进行疏干排水工程的边坡，进行地下水位、水压及涌水量观测并有记录，分析地下水对边坡稳定的影响程度及疏干效果，制定地下水治理措施
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack5">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。未查明、无记录、无措施不得分,记录不全1处扣2分
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
        <label class="Validform_label">疏干水再<br>利用</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            疏干水、矿坑水，可直接利用的或经处理后可利用的要回收利用
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack6">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。应利用未利用的不得分
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
    <td  rowspan="5">
        <label class="Validform_label">
            二、<br>疏<br>干<br>排<br>水<br>系<br>统<br>（55分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">设备设施</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.地面排水沟渠、储水池、防洪泵、防洪管路等设施完备，排水能力满足要求； <br>
            2.地下水疏干水泵、管道和运行控制等设备完好，满足疏干设计；<br>
            3.疏干巷道排水设备满足巷道涌水量要求
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack7">
            10
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣5分
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
    <td>
        <label class="Validform_label">地面排水</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.用露天采场深部做储水池排水时，有安全措施，备用水泵的能力不小于工作水泵能力的50%； <br>
            2.采场内的主排水泵站设置备用电源,当供电线路发生故障时,备用电源能担负最大排水负荷；<br>
            3.排水泵电源控制柜设置在储水池上部台阶，加高基础，远离低洼处，避免洪水淹没和冲刷； <br>
            4.储水池周围设置挡墙或护栏，检修平台符合GB 4053.4，上下梯子符合GB 4053.1～4053.2；<br>
            5.矿区外地表水对采场有影响时，有阻隔治理措施
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack8">
            10
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。第1和第2项不符合要求不得分，其他不符合要求1处扣2分
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
        <label class="Validform_label">地下水疏干</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.疏干工程应超前采矿工程，疏干降深满足采矿要求； <br>
            2.有涌水点的采剥台阶，设置相应的疏干排水设施；<br>
            3.因地下水位升高，可能造成排土场或采场滑坡的，应进行地下水疏干或采取有效措施进行治理；<br>
            4．疏干管路应根据需要配置控制阀、逆止阀、泄水阀、放气阀等装置，管路及阀门无漏水现象；<br>
            5.疏干井地下（半地下）泵房应设通风装置；<br>
            6.免维护疏干巷道有防火措施、排水通畅； <br>
            7.严寒地区疏干排水系统有防冻措施
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack9">
            10
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣2分
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
        <label class="Validform_label">现场管理</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.在矿床疏干漏斗范围内，地面出现裂缝、塌陷，圈定范围加以防护，设置警示标识，制定安全措施<br>
            2.进入疏干井地下（半地下）泵房前进行通风，检测气体合格后方可进入；<br>
            3.现场备用排水泵处于完好状态；<br>
            4.现场有配电系统图、水泵操作流程图；<br>
            5.检查疏干排水系统，有记录；<br>
            6.地埋管路堤坝应进行整形处理，疏干井、明排水泵周围设检修平台，外围疏干现场设检修通道；<br>
            7.疏干巷道运行设施完好，运行记录完整；<br>
            8.维护疏干巷道时，有防火、通风措施；<br>
            9.疏干巷道符合《矿井地质规程》
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack10">
            20
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣5分
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
    <td>
        <label class="Validform_label">疏干集中<br>控制系统</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.主机运行状态良好；<br>
            2.分站通讯状况良好； <br>
            3.主站采集的电流、电压、温度等数据准确，采集系统无异常或缺陷；<br>
            4.远程启动、停止、复位指令可靠；<br>
            5.停泵、通讯异常报警正常；<br>
            6.有完好的集控备用系统和备用电源
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack11">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣2分
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
    <td  rowspan="1">
        <label class="Validform_label">
            三、<br>岗<br>位<br>规<br>范<br>（5分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">专业技能及<br>规范作业</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.建立并执行本岗位安全生产责任制；<br>
            2.掌握本岗位操作规程、作业规程； <br>
            3.按操作规程作业，无“三违”行为； <br>
            4.作业前进行安全确认
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack12">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。岗位安全生产责任制不全，每缺1个岗位扣3分,发现1人“三违” 不得分，其他1处不符合要求扣1分
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
    <td  rowspan="1">
        <label class="Validform_label">
            四、<br>文<br>明<br>生<br>产<br>（5分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">作业环境</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1环境干净整洁，各设施保持完好； <br>
            2.各类物资摆放规整； <br>
            3.各种记录规范，页面整洁
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack13">
            5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。1项不符合要求扣1分
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
