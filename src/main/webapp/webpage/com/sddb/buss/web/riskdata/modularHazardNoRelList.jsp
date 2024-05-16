<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,ztree"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="hazardFactorsNoRelList" checkbox="true" pagination="true" fitColumns="true" title="" actionUrl="hazardFactorsController.do?datagridModuleHazardRel&modularId=${modularId}&rel=${rel}&riskType=${riskType}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险类型"  field="risk_type" dictionary="risk_type"  queryMode="single"  width="120" ></t:dgCol>
            <t:dgCol title="岗位"  field="postName"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="专业"  field="major" dictionary="major" query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="危害因素等级"  field="riskLevel" dictionary="factors_level" query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="危害因素"  field="hazard_factors" formatterjs="valueTitle"  queryMode="group"  width="200"></t:dgCol>
            <t:dgCol title="管控措施"  field="manage_measure" formatterjs="valueTitle" queryMode="group"  width="400" ></t:dgCol>
            <t:dgCol title="创建时间"  field="create_date"  queryMode="group" hidden="true" width="400" ></t:dgCol>
            <t:dgToolBar title="添加关联" icon="icon-add"  width="500"  funname="addRelHazardBatch" ></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">
    window.top["reload_hazardFactorsNoRelList"]=function(){
        $("#hazardFactorsNoRelList").datagrid( "load");
    };
    function addRelHazardBatch() {
        var modularId="${modularId}";
        var ids = [];
        var rowsData = $('#hazardFactorsNoRelList').datagrid('getSelections');
        if (!rowsData || rowsData.length==0) {
            tip('请选择需要关联的数据');
            return;
        }else{
            for ( var i = 0; i < rowsData.length; i++){
                ids.push(rowsData[i].id);
            }
        }
        $.ajax({
            url:'hazardFactorsController.do?addRelBatch',
            type: 'post',
            data:{
                modularId : modularId,
                harmId : ids.join(',')
            },
            success:function (data) {
                var data = $.parseJSON(data);
                tip(data.msg);
                window.top.reload_hazardFactorsRelList.call();
                window.top.reload_hazardFactorsNoRelList.call();
            }
        });
    }
</script>