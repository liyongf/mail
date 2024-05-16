<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="riskManagePostList" checkbox="true" pagination="true" fitColumns="true" title="" actionUrl="riskManageController.do?relPostRiskDatagrid&postRelId=${postRelId}" idField="id" fit="true" queryMode="group">
     <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="风险id"  field="risk.id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
        <t:dgCol title="岗位id"  field="risk.post.id" hidden="true"  queryMode="single"  width="120" query="false"></t:dgCol>
    <t:dgCol title="单位"  field="risk.postUnit.departname"  queryMode="single"  width="120" query="false"></t:dgCol>
        <t:dgCol title="岗位"  field="risk.post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
    <t:dgCol title="风险类型"  field="risk.riskType" dictionary="risk_type" query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="风险等级"  field="risk.riskLevel" dictionary="factors_level"   query="true"  width="120" ></t:dgCol>
    <t:dgCol title="危害因素"  align="center" field="risk.hazardFactortsPostNum" url="riskIdentificationController.do?wxysPostList&riskManageId={id}&load=detail" queryMode="group"  width="120" ></t:dgCol>
   <t:dgToolBar title="添加" icon="icon-add" funname="selectRisk" ></t:dgToolBar>
    <t:dgToolBar title="删除"  icon="icon-remove" url="riskManageController.do?doBatchDelPost" funname="deleteALLSelect"  ></t:dgToolBar>
    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" ></t:dgToolBar>
</t:datagrid>
<script type="text/javascript">
    function selectRisk() {
        createdetailwindow('风险添加','riskManageController.do?selectRiskByPostId&postRelId=${postRelId}',1200,600);
    }
    window.top["reload_riskManagePostList"]=function(){
        $("#riskManagePostList").datagrid( "load");
    };


    //导出
    function ExportXls() {
        var rows = $("#riskManagePostList").datagrid('getSelections');
        if (rows.length == 0) {
            JeecgExcelExport("riskManageController.do?exportXlsPost", "riskManagePostList");
        } else if (rows.length >=1) {
            var idsTemp = new Array();
            for (var i = 0; i < rows.length; i++) {
                idsTemp.push(rows[i].id);
            }
            var idt = idsTemp.join(",");
            $.dialog.confirm("是否确认导出"+idsTemp.length+"条记录？", function () {
                JeecgExcelExport("riskManageController.do?exportXlsPost&ids="+idt, "riskManagePostList");
            });
        }
    }
</script> 