<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="riskIdentificationList" checkbox="true" pagination="true" fitColumns="false" title="风险列表" actionUrl="riskManageResultController.do?hazardFactorRelRiskDatagrid&riskManageHazardFactor.id=${riskManageHazardFactorId}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险Id"  field="risk.id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险点"  field="risk.address.address"  queryMode="single"  width="120" query="true"></t:dgCol>
            <t:dgCol title="风险类型"  field="risk.riskType" dictionary="risk_type" query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="风险描述"  field="risk.riskDesc" formatterjs="valueTitle"  queryMode="group"  width="200"></t:dgCol>
            <t:dgCol title="风险等级"  field="risk.riskLevel"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="危害因素和管控措施" align="center"  field="risk.hazardFactortsNum"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="最高管控层级"  field="risk.manageLevel"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="最高管控责任人"  field="risk.dutyManager"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="评估日期"  field="risk.identifiDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="解除日期"  field="risk.expDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="状态"  field="risk.status" dictionary="identifi_status"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgToolBar title="查看" icon="icon-search" url="riskIdentificationController.do?goDetail" funname="detail"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
     });


    function detail(title,url, id,width,height) {
        var rowsData = $('#'+id).datagrid('getSelections');
        if (!rowsData || rowsData.length == 0) {
            tip('请选择查看项目');
            return;
        }
        if (rowsData.length > 1) {
            tip('请选择一条记录再查看');
            return;
        }
        url += '&load=detail&id='+rowsData[0]["risk.id"];
        createdetailwindow(title,url,width,height);
    }

</script>