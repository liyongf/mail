<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="tbActivityManageList" title="作业活动管理" actionUrl="tbActivityManageController.do?datagrid"
                    idField="id" fit="true" queryMode="group" sortName="activityName" sortOrder="desc">
            <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="作业活动名称" field="activityName"   width="120"  query="true" queryMode="single"></t:dgCol>
           <%-- <t:dgCol title="专业" field="professionType"  query="true"  dictionary="proCate_gradeControl"  width="120"  queryMode="single"></t:dgCol>--%>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tbActivityManageController.do?logicdel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o" urlStyle="background-color:#ec4758;"/>
            <t:dgOpenOpt title="查看关联的风险" url="tbActivityManageController.do?seeRiskList&id={id}" urlclass="ace_button"  urlfont="fa-trash-o" width="1000" height="500" />
            <t:dgToolBar title="录入" icon="icon-add" url="tbActivityManageController.do?addorupdate" funname="add" operationCode="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="tbActivityManageController.do?addorupdate" funname="update" operationCode="update"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="icon-search" url="tbActivityManageController.do?addorupdate" funname="detail" operationCode="detail"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls" operationCode="import"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>
            <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT" operationCode="exportTemplete"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    //导出
    function ExportXls() {
        JeecgExcelExport("tbActivityManageController.do?exportXls","tbActivityManageList");
    }
    //导入
    function ImportXls() {
        openuploadwin('作业活动导入', 'tbActivityManageController.do?upload', "tbActivityManageList");
    }
    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tbActivityManageController.do?exportXlsByT","tbActivityManageList");
    }
</script>