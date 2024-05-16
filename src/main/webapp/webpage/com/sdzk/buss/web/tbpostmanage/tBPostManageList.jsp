<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <%--<t:datagrid name="tBPostManageList" checkbox="true" pagination="true" fitColumns="true" title="工种管理" actionUrl="tBPostManageController.do?datagrid"--%>
        <%--idField="id" fit="true" queryMode="group">--%>
        <t:datagrid name="tBPostManageList" title="岗位管理" actionUrl="tBPostManageController.do?datagrid"
                    idField="id" fit="true"  checkbox="true"  queryMode="group" sortName="createDate,postName" sortOrder="desc,desc">
            <t:dgCol title="主键"  field="id"  hidden="true"    width="500"></t:dgCol>
            <t:dgCol title="岗位名称"  field="postName" query="true"   queryMode="single"  width="120"></t:dgCol>
           <%-- <t:dgCol title="专业"  field="professionType" query="true"  dictionary="proCate_gradeControl" queryMode="single"  width="120"></t:dgCol>--%>
            <t:dgCol title="createName"  field="createName"  hidden="true"    width="120"></t:dgCol>
            <t:dgCol title="createBy"  field="createBy"  hidden="true"   width="120"></t:dgCol>
            <t:dgCol title="createDate"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  width="120"></t:dgCol>
            <t:dgCol title="updateName"  field="updateName"  hidden="true"   width="120"></t:dgCol>
            <t:dgCol title="updateBy"  field="updateBy"  hidden="true"    width="120"></t:dgCol>
            <t:dgCol title="updateDate"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"    width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgDelOpt title="删除" url="tBPostManageController.do?logicdel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>
            <%--<t:dgOpenOpt title="查看关联的风险" url="tBPostManageController.do?seeRiskList&id={id}" urlclass="ace_button"  urlfont="fa-search" width="1000" height="500" />--%>
            <t:dgToolBar title="录入" icon="icon-add" url="tBPostManageController.do?goAdd" funname="add" operationCode="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="tBPostManageController.do?goUpdate" funname="update" operationCode="update" ></t:dgToolBar>
            <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBPostManageController.do?logicdoBatchDel" funname="deleteALLSelect" operationCode="batchdelete" ></t:dgToolBar>
            <t:dgToolBar title="查看" icon="icon-search" url="tBPostManageController.do?goUpdate" funname="detail" operationCode="detail"></t:dgToolBar>
            <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls" operationCode="import"></t:dgToolBar>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>
            <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT" operationCode="exportTemplete"></t:dgToolBar>
            <%--<t:dgToolBar title="导出风险" icon="icon-putout" url="tBDangerSourceController.do?postRelDangerExportXls&postId={id}" funname="postRelDangerExportXls" width="850" height="450"></t:dgToolBar>--%>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    //导出
    function postRelDangerExportXls(url) {
        var rows=$("#tBPostManageList").datagrid('getSelections');
        if(rows==0){
            tip("请勾选要导出的关联风险");
        }else if(rows.length>=1){
            var idsTemp=new Array();
            for(var i=0;i<rows.length;i++){
                idsTemp.push(rows[i].id);
            }
            var idt=idsTemp.join(",");
            $.dialog.confirm("是否确认导出"+idsTemp.length+"条记录？",function(){
                JeecgExcelExport("tBDangerSourceController.do?postRelDangerExportXls&postId="+idt,"tBPostManageList");
            })
        }
    }

    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tBPostManageController.do?upload', "tBPostManageList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tBPostManageController.do?exportXls","tBPostManageList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tBPostManageController.do?exportXlsByT","tBPostManageList");
    }
</script>