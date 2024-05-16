<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="implNumList" checkbox="true" width="900px" height="450px" fitColumns="true" title="落实" actionUrl="tBDecisionAnalyseController.do?implNumDatagrid&riskId=${riskId}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="危害因素"  field="hazardFactor.hazardFactors"  formatterjs="valueTitle"  queryMode="group"  width="500"></t:dgCol>
            <t:dgCol title="落实情况"  field="implDetail"  queryMode="group"  width="500" ></t:dgCol>
            <t:dgCol title="落实状态"  field="handleStatus" replace="未落实_0,已落实_1" queryMode="group"  width="500" ></t:dgCol>
            <t:dgCol title="备注"  field="remark" queryMode="group"  width="500" ></t:dgCol>
            <t:dgCol title="落实人"  field="createName" queryMode="single"  width="500" align="center"></t:dgCol>
        </t:datagrid>
    </div>
</div>