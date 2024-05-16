<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="west" style="padding:0px;border:0px;width: 500px" split="true" >
        <t:datagrid name="hazardModuleList" checkbox="true" pagination="true" fitColumns="false" title="模块危害因素库" actionUrl="hazardFactorsController.do?datagridModule" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" queryMode="single"  width="150" query="true"></t:dgCol>
            <t:dgCol title="模块名称"  field="moduleName"   width="150" query="true"></t:dgCol>
            <%--<t:dgCol title="危害因素数量"  field="num"    width="150"></t:dgCol>--%>
            <t:dgToolBar title="录入" icon="icon-add" url="hazardFactorsController.do?goAddModule" funname="add" operationCode="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="hazardFactorsController.do?goUpdateModule" funname="update" operationCode="update" ></t:dgToolBar>
            <t:dgToolBar title="删除"  icon="icon-remove" url="hazardFactorsController.do?doBatchDelModular" funname="deleteALLSelect" operationCode="delete"></t:dgToolBar>
            <%--<t:dgToolBar title="查看" icon="icon-search" url="hazardFactorsController.do?goDetailModule" funname="detail"></t:dgToolBar>--%>
            <%--<t:dgCol title="操作" field="opt" width="150" align="center"></t:dgCol>--%>
            <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
            <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
            <t:dgCol title="common.operation" field="opt"></t:dgCol>
            <t:dgFunOpt funname="setfunbyrole(id,moduleName)" title="危害因素" urlclass="ace_button" urlStyle="background-color:#18a689;"  urlfont="fa-list"></t:dgFunOpt>
        </t:datagrid>
    </div>
</div>
<div region="center">
    <div tools="#tt" class="easyui-panel" title='已关联危害因素' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>
</div>

<script type="text/javascript">
    function setfunbyrole(id,moduleName) {
        $("#function-panel").panel(
            {
                title :moduleName+ ':' + '已关联危害因素',
                href:"hazardFactorsController.do?goModuleHazardList&rel=rel&modularId=" + id
            }
        );
        //$('#function-panel').panel("refresh" );

    }

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'hazardFactorsModuleController.do?uploadModule', "hazardModuleList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("hazardFactorsModuleController.do?exportXlsByT","hazardModuleList");
    }
</script>