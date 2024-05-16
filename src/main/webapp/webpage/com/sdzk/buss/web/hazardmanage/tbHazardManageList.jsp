<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
</script>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="tbHazardManageList" title="危险源管理" actionUrl="tbHazardManageController.do?datagrid"
                    idField="id" fit="true" queryMode="group" sortName="hazardName" sortOrder="desc">
            <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="危险源名称" field="hazardName"   width="120" query="true" queryMode="single"></t:dgCol>
            <t:dgCol title="种类" field="hazardType"  dictionary="dangerSource_type"  width="120" query="true" queryMode="single"></t:dgCol>
          <%--  <t:dgCol title="危害类别" hidden="true" field="damageType" dictionary="danger_Category" width="120" query="true" queryMode="single"></t:dgCol>
            <t:dgCol title="危害类别" field="tempDamageType"  width="120" ></t:dgCol>
            &lt;%&ndash;<t:dgCol title="所属专业" field="professionType"   width="120"></t:dgCol>&ndash;%&gt;
            <t:dgCol title="事故类别"  hidden="true" field="accidentType"  dictionary="accidentCate" width="120"  query="true" queryMode="single"></t:dgCol>
            <t:dgCol title="事故类别"   field="tempAccidentType"   width="120"  ></t:dgCol>--%>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tbHazardManageController.do?logicdel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
            <t:dgToolBar title="录入" icon="icon-add" url="tbHazardManageController.do?goAddHazard" funname="add" operationCode="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="tbHazardManageController.do?goUpdateHazard" funname="update" operationCode="update"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="icon-search" url="tbHazardManageController.do?godetail" funname="detail" operationCode="detail"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls" operationCode="import"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>
            <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT" operationCode="exportTemplete"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tbHazardManageController.do?upload', "tbHazardManageList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tbHazardManageController.do?exportXls","tbHazardManageList");
    }
    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tbHazardManageController.do?exportXlsByT","tbHazardManageList");
    }
</script>