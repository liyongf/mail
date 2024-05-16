<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid title="风险管控状态清单" name="tBDangerSourceList" checkbox="true" pagination="true" fitColumns="false" actionUrl="tBDangerSourceController.do?riskControlDatagrid" idField="id" fit="true" queryMode="group">
            <t:dgCol title="唯一编号"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险描述"  field="yePossiblyHazard"  queryMode="group"  width="350" formatterjs="valueTitle" align="center"></t:dgCol>
            <c:if test="${initParam.les == 'no'}">
                <t:dgCol title="可能性"  field="yeProbability" dictionary="probability" queryMode="group"  width="120" align="center"></t:dgCol>
                <t:dgCol title="损失值"  field="yeCost" dictionary="hazard_fxss" queryMode="group"  width="120" align="center"></t:dgCol>
                <t:dgCol title="风险值"  field="riskValue"  queryMode="group"  width="120" align="center"></t:dgCol>
            </c:if>
            <c:if test="${initParam.les == 'yes'}">
                <t:dgCol title="可能性" field="lecRiskPossibility" dictionary="lec_risk_probability" queryMode="group" width="50" align="center"></t:dgCol>
                <t:dgCol title="损失" field="lecRiskLoss" dictionary="lec_risk_loss" queryMode="group" width="50" align="center"></t:dgCol>
                <t:dgCol title="暴露频率" field="lecExposure" dictionary="lec_exposure" queryMode="group" width="50" align="center"></t:dgCol>
                <t:dgCol title="风险值" field="lecRiskValue" queryMode="group" width="50" align="center"></t:dgCol>
            </c:if>
            <t:dgCol title="风险等级" field="yeRiskGrade" hidden="true" dictionary="riskLevel" queryMode="single" width="70" align="center"></t:dgCol>
            <t:dgCol title="预警颜色" field="alertColor" hidden="true" queryMode="group" width="80"></t:dgCol>
            <t:dgCol title="风险等级" field="yeRiskGradeTemp"  formatterjs="colorValueFormatter" sortable="false" queryMode="single" width="180" align="center"></t:dgCol>
            <t:dgCol title="事故类型"  field="yeAccidentTemp"  queryMode="group"  width="250" align="center"></t:dgCol>
            <t:dgCol title="伤害类别" field="damageType" dictionary="danger_Category" queryMode="single" width="250" align="center"></t:dgCol>
            <t:dgCol title="专业"  field="yeProfession" dictionary="proCate_gradeControl" queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="关联隐患数量"  field="riskNum" queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgToolBar title="查看隐患" icon="icon-search" onclick="lookRisk('查看隐患')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">

    //颜色列格式化
    function colorValueFormatter(value, rec, index) {
        return '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 80px; padding-left: 26px;" type="text" value="' + value + '"><span class="minicolors-swatch" style="top:0px;"><span class="minicolors-swatch-color" style="background-color: ' + rec.alertColor + ';"></span></span></div></div>';
    }

    function lookRisk (title) {
        var rows = $("#tBDangerSourceList").datagrid('getSelections');
        if(rows.length==0){
            tip("请选择风险!");
            return false;
        }else if(rows.length>1){
            tip("每次只能选择一个风险!");
            return false;
        }else{
            var dangerId = rows[0].id;
            $.dialog(
                {content: "url:tBHiddenDangerExamController.do?hiddenDangerList&dangerId="+dangerId, zIndex: 2100, title: '' + title + '',  parent: windowapi,lock: true, width: 860, height: 480, left: '7%', top: '43%', opacity: 0.4,}
            );
        }
    }
</script>