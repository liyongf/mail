<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="operationList" title="管控清单" actionUrl="riskController.do?statisticsDataGrid" queryMode="group" idField="id" checkbox="true">
	<t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="管控周期" field="taskManagerControl.inveDate" formatter="yyyy-MM-dd" query="true" queryMode="group" width="100"></t:dgCol>
	<t:dgCol title="风险点" field="addressInfoEntity.address" query="true" width="100"></t:dgCol>
	<t:dgCol title="危险源主键" hidden="true" field="dangerSourceEntity.id" width="100"></t:dgCol>
	<t:dgCol title="危险源" field="dangerSourceEntity.hazard.hazardName" query="true"></t:dgCol>
	<t:dgCol title="风险类型" field="dangerSourceEntity.yeHazardCate" query="true" dictionary="hazardCate"></t:dgCol>
	<t:dgCol title="风险描述" field="dangerSourceEntity.yePossiblyHazard" width="100"></t:dgCol>
	<t:dgCol title="风险等级" field="dangerSourceEntity.yeRiskGrade" query="true" dictionary="riskLevel" width="100"></t:dgCol>
	<t:dgCol title="隐患数量" field="controlTimes" width="100"></t:dgCol>
</t:datagrid>
<script type="text/javascript">

    //导出
    function ExportXls() {
        JeecgExcelExport("riskController.do?exportXls","operationList");
    }


    window.top["reload_operationList"]=function(){
        $("#operationList").datagrid( "load");
    };

    function goDetail(rowIndex,rowData){
        var rows=$("#operationList").datagrid('getSelections');
        if(rows.length != 1){
            tip("请选择一条查看");
		}else{
            createdetailwindow("查看", "tBDangerSourceController.do?goDetail&type=detail&id="+rows[0]["dangerSourceEntity.id"], 840, 460);
		}

    }
    function goLinkAddress(){
        openwindow('选择风险点','riskController.do?goOrderAddressList&taskId=${taskId}',"",1000,500);
    }

function editoperation(operationId,operationname)
{
}
</script>
