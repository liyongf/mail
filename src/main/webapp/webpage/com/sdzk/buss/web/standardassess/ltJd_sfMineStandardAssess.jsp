<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>机电</title>
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
    <%--<div class="wz-bt" >您当前的位置：考核评分管理->露天煤矿->机电</div>--%>
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
        <td colspan='8' align="center" style="font-size:20px;font-weight: bold;">露天煤矿机电标准化评分</td>
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
<div style="width: auto; height: 200px;">
<div style="width: auto; height: 1px;"></div>
<div id="tt" class="easyui-tabs" style="width: auto" data-options="onSelect:function(t){$('#tt .panel-body').css('width','auto');}">
<div title="设备管理">
<table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

<tr>
    <td>
        <label class="Validform_label">
            项目
        </label>
    </td>
    <td>
        <label class="Validform_label">
            项目内容
        </label>
    </td>
    <td>
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
            一、<br>设<br>备<br>管<br>理<br>（15分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">设备证标</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            机电设备有产品合格证，纳入安标管理的产品有煤矿矿用产品安全标志
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack1">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。1台设备不符合要求不得分
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
        <label class="Validform_label">设备完好</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            机电设备综合完好率不低于90%
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack2">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。每低于1个百分点扣0.5分
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
        <label class="Validform_label">管理制度</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            机电设备管理、机电设备事故管理制度
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack3">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。缺1项制度扣1分
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
        <label class="Validform_label">待修设备</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            设备待修率不高于5%
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack4">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。每增加1个百分点扣0.5分
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
    <td rowspan="1">
        <label class="Validform_label">机电事故</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            机电事故率不高于1%
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack5">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。超过1%不得分
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
    <td rowspan="1">
        <label class="Validform_label">设备大修改造</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            有设备更新大修计划并按计划执行
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack6">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。无计划或计划完成率全年低于90%、上半年低于30%不得分
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
    <td rowspan="1">
        <label class="Validform_label">设备档案</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            齐全完整
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack7">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不齐全完整1项扣1分
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
</table>
</div>
<div title="钻机">
    <table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

        <tr>
            <td>
                <label class="Validform_label">
                    项目
                </label>
            </td>
            <td>
                <label class="Validform_label">
                    项目内容
                </label>
            </td>
            <td>
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
                    二、<br>钻<br>机<br>（13分）
                </label>
            </td>
            <td  rowspan="1">
                <label class="Validform_label">技术要求</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.机上设施、装置符合移交时的各项技术标准和要求；<br>
                    2.检修和运行记录完整翔实
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack8">
                    3
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查资料。不符合要求1处扣1分
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
            <td  rowspan="1">
                <label class="Validform_label">电气部分</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.供电电缆及接地完好，外皮无破损； <br>
                    2.行走时电缆远离履带； <br>
                    3.配电系统保护齐全，定时整定并有记录，机上保存最新记录；<br>
                    4.使用直流控制的操作系统，直流开关灭弧装置正常，开关性能良好； <br>
                    5.机上各电气开关标识明确，停开有明显标识；<br>
                    6.各照明设备性能良好，固定牢靠
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack9">
                    5
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查资料和现场。不符合要求1处扣1分
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
            <td rowspan="1">
                <label class="Validform_label">机械部分</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.液压管保护完好，护套绑扎牢固，管路无破损、不漏油；<br>
                    2.钻塔起落装置、托架完好，连接件无松动、裂纹、开焊等； <br>
                    3.储杆装置完好，换杆系统灵活可靠；<br>
                    4.以内燃机为动力的钻机，三滤齐全，转速、液压、流量满足钻孔和行走要求，系统无渗漏，内燃机转速正常，启动、停车灵活可靠；<br>
                    5.液压系统用油符合说明书要求，按规定保养，工作时油温正常
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack10">
                    3
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查现场。不符合要求1处扣1分
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
            <td rowspan="1">
                <label class="Validform_label">辅助设施</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.电热和正压通风设备运行良好； <br>
                    2.机上消防设施完好可靠
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack11">
                    2
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查现场。不符合要求1处扣1分
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
    </table>
</div>
<div title="挖掘机 ">
    <table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

        <tr>
            <td>
                <label class="Validform_label">
                    项目
                </label>
            </td>
            <td>
                <label class="Validform_label">
                    项目内容
                </label>
            </td>
            <td>
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
                    三、<br>挖<br>掘<br>机<br>（20分）
                </label>
            </td>
            <td  rowspan="1">
                <label class="Validform_label">技术要求</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.设施、装置符合移交时的各项技术标准和要求； <br>
                    2.检修、运行、 交接班记录完整翔实 
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack45">
                    2
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查资料。不符合要求 1 处扣 2 分
                </label>
            </td>
            <td class="value" >
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
            <td  rowspan="1">
                <label class="Validform_label">电气部分</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.电缆尾杆长度适当，以防转向和倒车时压伤电缆；<br>
                    2.配电系统的各项保护齐全，计算机和显示系统工作正常，诊断警报可靠；<br>
                    3.配变电系统工作正常，机上电缆入槽，无过热，槽内清洁无杂物，保持通畅，盖板齐全，无松动；<br>
                    4.司机操作系统灵活可靠；<br>
                    5.电机不过热； <br>
                    6.维修所用连接电源安全可靠； <br>
                    7.大臂、司机室内外和机房照明正常可靠；<br>
                    8.各种电线、电缆连接可靠，绑扎固定；<br>
                    9.电器柜加锁，通风良好，无积尘
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack12">
                    8
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查现场。不符合要求1处扣1分
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
                <label class="Validform_label">机械部分</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.空气压缩系统工作正常，压缩机无漏油、跑风，气压正常，无杂音；<br>
                    2.提升（推压）钢丝绳无断股，绷绳断丝不超限，开门绳无扭结、无断股，各导绳绳轮转动良好； <br>
                    3.铲斗斗齿无缺损；<br>
                    4.铲斗插销、斗门开合自如，旋转时门缝不漏料；<br>
                    5.推压机构润滑正常，通道无积油；<br>
                    6.天轮润滑良好，无裂纹，磨损不超限； <br>
                    7.推压齿条无缺牙断齿；<br>
                    8.回转齿圈和滚道润滑正常，无缺齿，磨损不超限；<br>
                    9.提升滚筒无裂纹； <br>
                    10.履带运行正常无断裂，张紧装置定位牢固可靠, 张紧适度，辊轮转动灵活，滚道无损坏； <br>
                    11.A形架无裂纹；<br>
                    12.制动系统工作正常，不发生过卷；<br>
                    13.减速传动装置有安全罩，不漏油
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack13">
                    7
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查现场。不符合要求1处扣1分
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
                <label class="Validform_label">辅助设施</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.机顶人行道防滑垫完整、粘贴可靠，各种扶手、挡链连接可靠、使用方便；<br>
                    2.机房清洁无杂物，消防设施齐全，警报装置正常，润滑室通风正常；<br>
                    3.梯子抽动自如，信号准确；<br>
                    4.配重箱无破裂，配重量符合标准；<br>
                    5.机下和司机室联络信号灵活可靠
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack14">
                    3
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查现场。不符合要求1处扣1分
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
    </table>
</div>
<div title="矿用卡车 ">
<table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

<tr>
    <td>
        <label class="Validform_label">
            项目
        </label>
    </td>
    <td>
        <label class="Validform_label">
            项目内容
        </label>
    </td>
    <td>
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
    <td  rowspan="8">
        <label class="Validform_label">
            四、<br>矿<br>用<br>卡<br>车<br>（10分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">技术要求</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.设施、装置符合移交验收时的要求； <br>
            2.检修、运行和交接班记录翔实；<br>
            3.移动检查装置（PTU）使用正常，有记录，定期存档
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack15">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.5分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">动力设施</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.风机等的传动皮带运转正常，无超限磨损；<br>
            2.发动机冷却液温度正常，系统工作良好；<br>
            3.发动机怠速声均匀无杂音； <br>
            4.启动电池连接良好，无闪络（火花）痕迹；<br>
            5.增压器接管无裂痕，固定牢靠，有防火布；<br>
            6.排烟管无裂缝；<br>
            7.发动机和发电机（液压马达）连接正常；<br>
            8.发电机通风管道无漏风，软接头良好；<br>
            9.电动轮通风正常； <br>
            10.电拖动开关箱无变形，闭锁正常；<br>
            11.电子监控系统显示正常；<br>
            12.电子加速踏板（俗称油门踏板）工作正常； <br>
            13.车辆上的插件无松动，工作正常； <br>
            14.缓行减速工作正常；电阻栅不过热，无过热烧损痕迹，通风冷却正常；<br>
            15.冷却通风及过热警告系统工作正常； <br>
            16.各种仪表显示正常；<br>
            17.电动轮无环火，换向器表面无烧蚀痕迹，碳刷压力和高度正常，刷架定位正常；<br>
            18.照明和倒车信号指示正确，联动无误，灯光正常
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack16">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。1处不符合要求扣0.5分
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
    <td rowspan="1">
        <label class="Validform_label">机械部分</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.制动系统可靠； <br>
            2.悬挂装置完好，工作正常； <br>
            3.关节联结，润滑良好；<br>
            4.举升系统完好，工作正常；<br>
            5.鼻锥连接正常，润滑良好；<br>
            6.平衡杆无弯曲，连接点润滑良好； <br>
            7.箱斗和机架间衬垫良好，无缺损，车架无裂痕；<br>
            8.转向系统调整正常；<br>
            9.轮胎与轮辋匹配，打石器完整，灵活无断裂；<br>
            10.定期查验防滚架（ROPS）架构，螺丝紧固适当；<br>
            11.油尺和油箱视窗保持完好；<br>
            12.各种管线固定，接口完好
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack17">
            3
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣0.5分
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
    <td rowspan="1">
        <label class="Validform_label">辅助部分</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.集中润滑系统完好，各人工注油嘴保持通畅，按规定注油；<br>
            2.司机室采暖设备、雨刷齐备完好；<br>
            3.司机座位调整和方向盘调整适合司机操作；<br>
            4.安全带、门锁、门窗使用灵活，玻璃完整；<br>
            5.司机上下车梯子完整，固定可靠； <br>
            6.消防设施完好； <br>
            7.轮胎管理符合技术要求，定时换位，检查胎温、胎压、花纹及胎面，并作好记录、存档
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack18">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣0.5分
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
</table>
</div>
<div title="连续工艺 ">
<table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

<tr>
    <td>
        <label class="Validform_label">
            项目
        </label>
    </td>
    <td align="center" colspan="2">
        <label class="Validform_label">
            项目内容
        </label>
    </td>
    <td>
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
            评分
        </label>
    </td>
    <td>
        <label class="Validform_label">
            备注
        </label>
    </td>
</tr>
<tr>
    <td  rowspan="12">
        <label class="Validform_label">
            五、<br>连<br>续<br>工<br>艺<br>（10分）
        </label>
    </td>
    <td  rowspan="3">
        <label class="Validform_label">轮斗挖掘机</label>
    </td>
    <td>
        <label class="Validform_label">技术要求</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.设施、装置符合移交时的各项技术标准和要求；<br>
            2.检修、运行、交接班记录完整翔实
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack19">
            0.5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1处扣0.1分
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
    <td>
        <label class="Validform_label">机械部分</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.制动性能良好，制动部件完好；<br>
            2.钢丝绳磨损和断丝不超限，滑轮无裂痕，紧固端无松动；<br>
            3.减速器通气孔干净、畅通，减速器油位、油质合格；<br>
            4.润滑部件完好、齐全；<br>
            5.防倾翻安全钩间隙不超限；<br>
            6.履带张紧适度，履带板无断裂； <br>
            7.斗轮体的锥体和圆弧导料板、斜溜料板、溜槽和档板磨损不超限；<br>
            8.变幅机构张力值不超限； <br>
            9.钢结构无开焊、变形、断裂现象、防腐完好，各部连接螺栓紧固齐全；<br>
            10.胶带机驱动滚筒及改向滚筒包胶磨损不超限；<br>
            11.胶带损伤、磨损不超限；<br>
            12.清扫器齐全有效； <br>
            13.各转动部位防护罩、防护网齐全有效； <br>
            14.消防设施齐全有效
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack20">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.2分
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
    <td>
        <label class="Validform_label">电气部分</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.各种安全保护装置齐全有效； <br>
            2.机上固定电缆理顺、捆绑、入槽或挂钩固定、布置整齐，接线规范、无裸露接头； <br>
            3.电器柜内无积尘，电气元器件齐全、无破损、标识明确，柜内布线整齐，按规定捆绑；<br>
            4.配电室及时上锁；<br>
            5.电机接线盒、风翅、风罩齐全、无破损；<br>
            6.电气保护接地齐全、规范；<br>
            7.室外电气控制箱、操作箱箱体完好，箱内元器件齐全、无破损、无积尘、及时上锁；<br>
            8.室外照明灯具完好
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack21">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.2分
        </label>
    </td>
    <td class="value" >
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
    <td rowspan="3">
        <label class="Validform_label">排土机</label>
    </td>
    <td>
        <label class="Validform_label">技术要求</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.设施、装置符合移交时的各项技术标准和要求；<br>
            2.检修、运行、交接班记录完整翔实
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack22">
            0.5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣0.1分
        </label>
    </td>
    <td class="value" >
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
        <label class="Validform_label">机械部分</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.制动性能良好，制动部件完好；<br>
            2.钢丝绳磨损和断丝不超限，滑轮无裂痕，紧固端无松动；<br>
            3.减速器通气孔干净、畅通，减速器油位、油质合格；<br>
            4.润滑部件完好、齐全；<br>
            5.防倾翻安全钩间隙不超限；<br>
            6.履带张紧适度，履带板无断裂；<br>
            7.夹轨器状态正常； <br>
            8. 钢结构无开焊、变形、断裂现象，防腐有效，各部连接螺栓紧固齐全；<br>
            9.胶带机驱动滚筒及改向滚筒包胶磨损不超限；<br>
            10.胶带损伤、磨损不超限；<br>
            11.清扫器齐全、有效；<br>
            12.各转动部位防护罩、防护网齐全、有效； <br>
            13.消防设施齐全、有效
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack23">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.2分
        </label>
    </td>
    <td class="value" >
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
    <td>
        <label class="Validform_label">电气部分</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.各种安全保护装置齐全有效；<br>
            2.机上固定电缆理顺、捆绑、入槽或挂钩固定、布置整齐，接线规范、无裸露接头；<br>
            3.电器柜内无积尘，电气元器件齐全、无破损、标识明确，柜内布线整齐，按规定捆绑；<br>
            4.配电室及时上锁；<br>
            5.电机接线盒、风翅、风罩齐全、无破损；<br>
            6.电气保护接地齐全、规范；<br>
            7.室外电气控制箱、操作箱箱体完好，箱内元器件齐全、无破损、无积尘、及时上锁；<br>
            8.室外照明灯具完好
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack24">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.2分
        </label>
    </td>
    <td class="value" >
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
    <td rowspan="3">
        <label class="Validform_label">带式输送机</label>
    </td>
    <td>
        <label class="Validform_label">技术要求</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.机上设施、装置符合移交时的各项技术标准和要求； <br>
            2.检修和运行记录完整翔实
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack25">
            0.5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣0.1分
        </label>
    </td>
    <td class="value" >
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
        <label class="Validform_label">机械部分</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.制动性能良好，制动部件完好；<br>
            2.钢丝绳磨损和断丝不超限，滑轮无裂痕，紧固端无松动； <br>
            3.减速器通气孔干净、畅通，减速器油位、油质合格；<br>
            4.钢结构无开焊、变形、断裂现象，防腐有效，各部连接螺栓紧固齐全；<br>
            5.受料槽圆钢、母板、耐磨板等各部位焊接牢固，磨损不过限，不伤及母板,挡料胶条夹板无变形，部件无损坏或不全，防冲击装置使用完好；<br>
            6.托辊组部件完好；<br>
            7.胶带损伤、磨损不超限；<br>
            8.清扫器齐全有效；<br>
            9.驱动滚筒及改向滚筒包胶磨损不超限；<br>
            10.分流站伸缩头行走机构部件处于完好状态；<br>
            11.各转动部位防护罩、防护网齐全、有效； <br>
            12.消防设施齐全有效
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack26">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.2分
        </label>
    </td>
    <td class="value" >
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
        <label class="Validform_label">电气部分</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.各种安全保护装置齐全有效,带式输送机检修时使用检修开关并上锁，启动预警时间不少于20s；<br>
            2.机上固定电缆理顺、捆绑、入槽或挂钩固定、布置整齐，接线规范，无裸露接头； <br>
            3.电器柜内无积尘，电气元器件齐全、无破损、标识明确，柜内布线整齐，按规定捆绑；<br>
            4.配电室及时上锁；<br>
            5.电机接线盒、风翅、风罩齐全、无破损；<br>
            6.电气保护接地齐全、规范；<br>
            7.室外电气控制箱、操作箱箱体完好，箱内元器件齐全、无破损、无积尘、及时上锁
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack27">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.2分
        </label>
    </td>
    <td class="value" >
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
    <td rowspan="3">
        <label class="Validform_label">破碎站</label>
    </td>
    <td>
        <label class="Validform_label">技术要求</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.设施、装置符合移交时的各项技术标准和要求；<br>
            2.检修和运行记录完整翔实
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack28">
            0.5
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料。不符合要求1处扣0.1分
        </label>
    </td>
    <td class="value" >
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
        <label class="Validform_label">机械部分</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.制动性能良好，制动部件完好；<br>
            2.减速器通气孔干净、畅通，减速器油位、油质合格；<br>
            3.钢结构无开焊、变形、断裂现象,防腐有效，各部连接螺栓紧固齐全；<br>
            4.板式给料机链节、驱动轮磨损不过限,给料机链板不变形；<br>
            5.破碎辊的破碎齿、边齿磨损不过限、不松动；<br>
            6.受料槽圆钢、母板、耐磨板等各部位焊接牢固，磨损不过限，不伤及母板,挡料胶条夹板无变形，部件无损坏或不全，防冲击装置使用完好；<br>
            7.胶带损伤、磨损不超限；<br>
            8.驱动滚筒及改向滚筒包胶磨损不超限； <br>
            9.各转动部位防护罩、防护网齐全有效；<br>
            10.消防设施齐全有效
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack29">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.2分
        </label>
    </td>
    <td class="value" >
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
        <label class="Validform_label">电气部分</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.各种安全保护装置齐全有效；<br>
            2.机上固定电缆理顺、捆绑、入槽或挂钩固定、布置整齐，接线规范、无裸露接头；<br>
            3.电器柜内无积尘，电气元器件齐全、无破损、标识明确，柜内布线整齐，按规定捆绑；<br>
            4.配电室上锁；<br>
            5.电机接线盒、风翅、风罩齐全、无破损；<br>
            6.电气保护接地齐全规范； <br>
            7.室外电气控制箱、操作箱箱体完好，箱内元器件齐全、无破损、无积尘、上锁；<br>
            8.室外照明灯具完好
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack30">
            1
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。1处不符合要求扣0.2分
        </label>
    </td>
    <td class="value" >
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
</table>
</div>
<div title="电机车(单斗—铁路工艺)">
    <table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

        <tr>
            <td>
                <label class="Validform_label">
                    项目
                </label>
            </td>
            <td>
                <label class="Validform_label">
                    项目内容
                </label>
            </td>
            <td>
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
                    六、<br>电<br>机<br>车<br>︵<br>单<br>斗<br>—<br>铁<br>路<br>工<br>艺<br>︶<br>（10分）
                </label>
            </td>
            <td  rowspan="1">
                <label class="Validform_label">技术要求</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.设施、装置符合移交时的各项技术标准和要求；<br>
                    2.检修、运行及交接班记录完整翔实
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack31">
                    2
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查资料。不符合要求1处扣0.5分
                </label>
            </td>
            <td class="value" >
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
            <td  rowspan="1">
                <label class="Validform_label">设施要求</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.正旁弓子无裂纹、无折损，编组铜线烧损和折损率不大于15%，气筒不跑气； <br>
                    2.车棚盖不漏雨，避雷器完好，探照灯射程达80m以上，主隔离开关无烧损，接触面积达75%以上；<br>
                    3.主电阻室连接铜带无松弛和烧损，导线片间距离不小于原有的66%；<br>
                    4.高压室的导线绝缘无腐蚀老化，接线头无烧损、脱焊，连锁装置正常；<br>
                    5.蓄电池箱底无腐蚀，滑道无破损，零件完整； <br>
                    6.机械室内辅助电机的保护网完整、轴承不漏油、动作可靠；<br>
                    7.台车、联结器、轮轴、牵引电动机各零件紧固、无缺失，润滑良好
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack32">
                    4
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查现场。不符合要求1处扣0.5分
                </label>
            </td>
            <td class="value" >
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
            <td rowspan="1">
                <label class="Validform_label">辅助设施</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.司机室仪表齐全、完整、灵活，电热器完好，操作开关齐全完整、动作灵活；<br>
                    2.消防设施齐全有效
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
            <td class="value" >
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
            <td rowspan="1">
                <label class="Validform_label">机车自翻车</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.各机件齐全完好、无松动、不漏油，磨损符合要求，制动灵活；<br>
                    2.转动装置、台车连接处润滑良好、不缺油
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack34">
                    2
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查现场。不符合要求1处扣0.5分
                </label>
            </td>
            <td class="value" >
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
    </table>
</div>
<div title="辅助机械设备">
    <table style="width: 95%;"  cellpadding="0" cellspacing="1" class="formtable" >

        <tr>
            <td>
                <label class="Validform_label">
                    项目
                </label>
            </td>
            <td>
                <label class="Validform_label">
                    项目内容
                </label>
            </td>
            <td>
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
                    七、<br>辅<br>助<br>机<br>械<br>设<br>备<br>（10分）
                </label>
            </td>
            <td  rowspan="1">
                <label class="Validform_label">技术要求</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.各机件齐全完好、无松动、不漏油，磨损符合要求，制动灵活； <br>
                    2.转动装置、台车连接处润滑良好、不缺油
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack35">
                    2
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查现场。不符合要求1处扣0.5分
                </label>
            </td>
            <td class="value" >
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
            <td  rowspan="1">
                <label class="Validform_label">电气部分</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.工作照明、各部仪表、蜂鸣器工作正常；<br>
                    2.控制装置、监控面板、报警装置工作正常，接线柱螺栓、各种保险开关不缺失；<br>
                    3.各种电线、电缆连接可靠，绑扎固定良好；各部插头联接紧固； <br>
                    4.发电机皮带、风扇皮带等运转正常，张紧度符合要求、无超限磨损；<br>
                    5.电瓶搭线连接良好，无闪络（火花）痕迹，可维护电瓶电解液液位满足使用要求
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack36">
                    3
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查现场。不符合要求1处扣0.5分
                </label>
            </td>
            <td class="value" >
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
            <td  rowspan="1">
                <label class="Validform_label">机械部分</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.发动机冷却液温度正常，系统工作良好；<br>
                    2.发动机怠速声均匀无杂音、排烟正常；<br>
                    3.涡轮增压器岐管联接正常，无裂痕，固定牢固；<br>
                    4.排烟管、消音器无裂纹；<br>
                    5.机油、液压油、齿轮油油位、油质、温度、密封正常；<br>
                    6.制动装置部件齐全完好，制动性能可靠；<br>
                    7.传动装置工作正常，无漏油现象，各部分润滑良好；<br>
                    8.液压元件工作正常，液压回路密封良好，无漏油现象，液压传动系统工作安全可靠；<br>
                    9.钢结构件无开焊、变形或断裂现象，侧机架无漏油现象，铲刀、铲角、斗齿等磨损程度符合要求
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack37">
                    3
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查现场。不符合要求1处扣0.5分
                </label>
            </td>
            <td class="value" >
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
            <td  rowspan="1">
                <label class="Validform_label">辅助设施</label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    1.驾驶室仪表齐全有效，电器完好，操作开关齐全完整、动作灵活，安全装置，工作可靠； <br>
                    2.司机室清洁无杂物，消防设施齐全有效
                </label>
            </td>
            <td >
                <label class="Validform_label" name="lack38">
                    2
                </label>
            </td>
            <td class="td-padding">
                <label class="Validform_label">
                    查现场。不符合要求1处扣0.5分
                </label>
            </td>
            <td class="value" >
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

    </table>
</div>
<div title="供电管理">
<table style="width: 95%; height: 90%"  cellpadding="0" cellspacing="1" class="formtable" >

<tr>
    <td>
        <label class="Validform_label">
            项目
        </label>
    </td>
    <td>
        <label class="Validform_label">
            项目内容
        </label>
    </td>
    <td>
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
    <td  rowspan="10">
        <label class="Validform_label">
            八、<br>供<br>电<br>管<br>理<br>（12分）
        </label>
    </td>
    <td  rowspan="1">
        <label class="Validform_label">断路器和互感器</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.油位正常；<br>
            2.本体及高压套管无渗漏
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack39">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求不得分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">开关柜</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.内设断路器或负荷开关完好；<br>
            2.内设电压、电流互感器完好； <br>
            3.母线支撑瓶无损，连接螺栓无松动；<br>
            4.开关柜各种保护完好
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack40">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求不得分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">变电站</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            有供电监控系统，35kV、110kV变电站2小时排查、巡视1次，检查隔离开关、引线、设备卡有无发热、放电现象，记录齐全
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack41">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查资料和现场。不符合要求1处扣0.5分
        </label>
    </td>
    <td class="value" >
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
    <td  rowspan="1">
        <label class="Validform_label">电力电缆防护区</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.电力电缆防护区（两侧各0.75m）内不堆放垃圾、矿渣、易燃易爆及有害的化学物品；<br>
            2.电缆线路标识符合《电力电缆工程技术导则》
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack42">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
        </label>
    </td>
    <td class="value" >
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
    <td rowspan="1">
        <label class="Validform_label">配电室</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.配电室不渗、漏水，内、外墙皮完好，挡鼠板、防护网齐全并符合要求，非工作人员进入要登记；<br>
            2.配电室外有“禁止攀登、高压危险”、“配电重地、闲人免进”等警示标示；<br>
            3.周围无杂草、柴垛等易燃物；<br>
            4.配电室内电缆沟使用合格盖板，出口封堵完好；<br>
            5.按规定安装电容器并固定牢固
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack43">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场。不符合要求1处扣0.5分
        </label>
    </td>
    <td class="value" >
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
    <td rowspan="1">
        <label class="Validform_label">变压器</label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            1.柱上安装的变压器，底座距地面不小于2.5m；<br>
            2.露天安装的变压器悬挂“禁止攀登、高压危险”的标示牌；<br>
            3.横梁、电缆套管等使用镀锌件；<br>
            4.线路杆号、名称、色标及柱上开关（包括电缆分线箱、环网柜名称和编号）正确清楚，电缆牌齐全并与实际相符；<br>
            5.高、低压同杆架设，横担间最小垂直距离：直线杆1.2m，分支和转角杆1.0m；<br>
            6.柱上开关、配电台架、10kV电缆线路（超过50m的两端）安装避雷器，避雷器按要求定期试验；<br>
            7.配电设备的接地线使用直径不小于16mm的圆钢或截面积不小于100 mm2的接地体，接地电阻符合要求；<br>
            8.表计无损坏，安装规范、牢固、无歪斜，表尾用供电所专用钳封印； <br>
            9. 带风扇通风冷却的变压器能自动或手动投入运行
        </label>
    </td>
    <td >
        <label class="Validform_label" name="lack44">
            2
        </label>
    </td>
    <td class="td-padding">
        <label class="Validform_label">
            查现场和资料。不符合要求1处扣0.5分
        </label>
    </td>
    <td class="value" >
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
</table>
</div>
</div>
<table style="width: 100%;"  cellpadding="0" cellspacing="1" class="formtable" align="center">

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
        $("#tt").find("div[class='tabs-wrap']").css("width","auto");
        $("input[name^='score']").on("blur", function(){
            calculate();
        });
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
