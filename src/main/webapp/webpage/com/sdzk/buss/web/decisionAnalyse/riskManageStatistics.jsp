<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="riskManageStatisticsList" checkbox="true" onDblClick="dbClick" pagination="true" fitColumns="true" title="过程管控结果分析" actionUrl="tBDecisionAnalyseController.do?riskManageStatisticsDatagrid" idField="id" fit="true" queryMode="group" >
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险点"  field="address" dictionary="t_b_address_info,id,address" queryMode="single" sortable="false"  width="120" query="false"></t:dgCol>
            <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" sortable="false" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="风险描述"  field="riskDesc"  formatterjs="valueTitle"   queryMode="group" sortable="false"  width="200"></t:dgCol>
            <t:dgCol title="风险等级"  field="riskLevel"  dictionary="factors_level" queryMode="single"  width="120" ></t:dgCol>
            <t:dgCol title="危害因素"  field="hazardFactortsNum" url="riskIdentificationController.do?wxysList&id={id}&load=detail" sortable="false" queryMode="group"  width="120" align="center" ></t:dgCol>
            <t:dgCol title="管控人数"  field="manageNum" url="tBDecisionAnalyseController.do?manageNumList&id={id}&load=detail"    queryMode="group"  width="120" align="center" ></t:dgCol>
            <t:dgCol title="落实次数"  field="implNum" url="tBDecisionAnalyseController.do?implNumList&id={id}&load=detail"  queryMode="group"  width="120" align="center" ></t:dgCol>
            <t:dgCol title="隐患数量"  field="hdNum" url="tBDecisionAnalyseController.do?hdNumList&id={id}&load=detail"    queryMode="group"  width="120" align="center" ></t:dgCol>
            <t:dgCol title="月份"  field="manageDate" formatter="yyyy-MM"  queryMode="single" sortable="false" hidden="true" query="true" width="120" ></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $("#yearMonth").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
        var datagrid = $("#riskManageStatisticsListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums").html()).attr("style","text-align: center;");
        $("#riskManageStatisticsListForm").find("input[name='manageDate']").attr("class", "Wdate").attr("style", "height:30px;width:156px;").click(function () {
            WdatePicker({
                dateFmt: 'yyyy-MM',
            });
        });
    });
</script>