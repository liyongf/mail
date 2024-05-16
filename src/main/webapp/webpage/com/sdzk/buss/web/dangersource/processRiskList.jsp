<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%--<t:datagrid name="userList" title="user.manage" actionUrl="roleController.do?roleUserDatagrid&roleId=${roleId}" fit="true" fitColumns="true" idField="id">--%>
<%--	<t:dgCol title="common.id" field="id" hidden="true" ></t:dgCol>--%>
<%--	<t:dgCol title="common.username" sortable="false" field="userName" width="5"></t:dgCol>--%>
<%--	<t:dgCol title="common.real.name" field="realName" width="5"></t:dgCol>--%>
<%--</t:datagrid>--%>

<t:datagrid name="tBDangerSourceList" title="风险列表" actionUrl="tBWorkProcessManageController.do?workProcessDangerSourceDatagrid&workid=${id}" fit="true" fitColumns="true" idField="id" queryMode="group">
    <t:dgCol title="唯一编号"  field="id" hidden="true"   queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="隐患描述"  field="danger.yeMhazardDesc"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="风险等级"  field="danger.yeRiskGrade" dictionary="riskLevel" query="true"  queryMode="single"  width="120" align="center"></t:dgCol>
    <t:dgCol title="风险类型"  field="danger.yeHazardCate" query="true"  dictionary="hazardCate"  queryMode="single"  width="120" align="center"></t:dgCol>
    <t:dgCol title="common.operation" field="opt"></t:dgCol>

    <t:dgFunOpt funname="deleteDialog(id)" title="common.delete" urlclass="ace_button"  urlfont="fa-trash-o" urlStyle="background-color:#ec4758;"></t:dgFunOpt>
    <t:dgFunOpt funname="goDetail(id)" title="查看" urlclass="ace_button"   urlfont="fa-search"></t:dgFunOpt>
    <t:dgToolBar title="关联风险" icon="icon-add" funname="chooseDangerSource"></t:dgToolBar>
</t:datagrid>
<script type="text/javascript">

    $(document).ready(function(){
    });
    function deleteDialog(id){
        var url = "tBWorkProcessManageController.do?delProcessRisk&id=" + id;
//	createwindow("确认", url, 200, 100);

        $.dialog.confirm('确定删除该关联关系吗?', function(){
            reloadTable();
            reloadP();
            doSubmit(url);
        }, function(){
        });
    }
    function goDetail(id){
            var url = 'tBWorkProcessManageController.do?goDetail&from=universalDangerList&load=detail&id='+id;
            createdetailwindow("查看",url,850,450);
    }
    function chooseDangerSource(){
        $.dialog({id:'dialog',title:'危险源选择',zIndex:999,modal:true,content: 'url:tBWorkProcessManageController.do?chooseDangerSource2Rel&id=${id}',lock:true,width: 800,height: 500});
    }
    function showMsg(msg){
        tip(msg);
    }
    function reloadP(){
        $('#tBDangerSourceList').datagrid('reload');
        $('#roleList').datagrid('reload');
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tBDangerSourceController.do?exportRelXls","tBDangerSourceList");
    }
</script>
