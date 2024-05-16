<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,autocomplete"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <%--<t:datagrid name="tBDangerSourceList" checkbox="false" pagination="true" fitColumns="false" title=""
                    actionUrl="tBDangerSourceController.do?addressDangerDatagrid&addressId=${addressId}"
                    idField="id" fit="true" queryMode="group" sortName="yeRiskGrade" sortOrder="asc">
            <t:dgCol title="唯一编号" field="id" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
            <t:dgCol title="风险等级" field="yeRiskGrade" hidden="true" dictionary="riskLevel" queryMode="single" width="120" align="center"></t:dgCol>
            <t:dgCol title="风险等级" field="alertLevel" formatterjs="colorValueFormatter" queryMode="single" width="120" align="center"></t:dgCol>
            <t:dgCol title="隐患描述" field="yeMhazardDesc" queryMode="single" query="false" width="120"></t:dgCol>
            <t:dgCol title="危险源" field="hazard.hazardName" queryMode="single" width="80" align="center"></t:dgCol>
            <t:dgCol title="伤害类别" field="damageType" query="false" dictionary="danger_Category" queryMode="single"
                     width="80" align="center"></t:dgCol>
            <t:dgCol title="作业活动" field="activity.activityName" queryMode="single" width="100" align="center"></t:dgCol>
            <t:dgCol title="风险描述" field="yePossiblyHazard" queryMode="group" width="180"></t:dgCol>
            <t:dgCol title="预警颜色" field="alertColor" hidden="true" queryMode="group" width="80"></t:dgCol>
            <t:dgCol title="风险类型" field="yeHazardCate" dictionary="hazardCate" queryMode="single" width="60"
                     align="center"></t:dgCol>
        </t:datagrid>--%>
            <t:datagrid name="hazardFactorsList" checkbox="false" pagination="true" fitColumns="false" title="关联风险" actionUrl="riskIdentificationController.do?queryListByAddressDatagrid&addressId=${addressId}&expDate=true" idField="id" fit="true" queryMode="group">
                <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
                <t:dgCol title="风险点Id"  field="address.id" hidden="true" queryMode="single"  width="120"></t:dgCol>
                <t:dgCol title="风险点"  field="address.address"  queryMode="single"  width="120" ></t:dgCol>
                <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type"  queryMode="single"  width="120"></t:dgCol>
                <t:dgCol title="风险描述"  field="riskDesc" formatterjs="valueTitle"  queryMode="group"  width="200"></t:dgCol>
                <t:dgCol title="风险等级"  field="riskLevel"  dictionary="factors_level" queryMode="single"  width="120" ></t:dgCol>
                <t:dgCol title="危害因素和管控措施" align="center"  field="hazardFactortsNum" url="riskIdentificationController.do?wxysList&id={id}&load=detail"  queryMode="group"  width="120" ></t:dgCol>
                <t:dgCol title="最高管控层级"  field="manageLevel"  dictionary="identifi_mange_level"  queryMode="single"  width="120" ></t:dgCol>
                <t:dgCol title="最高管控责任人"  field="dutyManager"  queryMode="single"  width="120" ></t:dgCol>
                <t:dgCol title="评估日期"  field="identifiDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
                <t:dgCol title="解除日期"  field="expDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>


            </t:datagrid>
    </div>
</div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">
    //颜色列格式化
    function colorValueFormatter(value, rec, index) {
        if(value != ""){
            return '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 80px; padding-left: 26px;" type="text" value="' + value + '"><span class="minicolors-swatch" style="top:0px;"><span class="minicolors-swatch-color" style="background-color: ' + rec.alertColor + ';"></span></span></div></div>';
        }else{
            return value;
        }
    }
</script>