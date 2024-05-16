<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="west" style="padding:0px;border:0px;width: 450px" split="true" >
        <t:datagrid name="riskManageMajorList" checkbox="true" pagination="true" fitColumns="false" title="${riskManageName}清单" actionUrl="riskManageController.do?datagridMajor&manageType=${manageType}" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="${riskManager}名称"  field="majorName"  queryMode="single"  width="150" query="true"></t:dgCol>
            <t:dgCol title="创建人"  field="createName"  queryMode="single"  width="100"></t:dgCol>
            <t:dgToolBar title="录入" icon="icon-add" url="riskManageController.do?goAddMajor&manageType=${manageType}" funname="add" operationCode="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="riskManageController.do?goUpdateMajor&manageType=${manageType}" funname="update" operationCode="update" ></t:dgToolBar>
            <t:dgToolBar title="删除"  icon="icon-remove" url="riskManageController.do?doDelMajor" funname="deleteSelect" operationCode="delete"></t:dgToolBar>
            <t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
            <t:dgFunOpt funname="addRisk(id,majorName)" title="添加风险" urlclass="ace_button" urlStyle="background-color:#18a689;"  urlfont="fa-list" operationCode="addRisk"></t:dgFunOpt>
        </t:datagrid>
    </div>
</div>
<div region="center" >
    <div tools="#tt" class="easyui-panel" title='已添加风险' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>
</div>

<script type="text/javascript">
    function addRisk(id,majorName) {

        $("#function-panel").panel(
            {
                title :majorName+ ':' + '已关联风险',
                href:"riskManageController.do?goRiskList&manageType=${manageType}&rel=rel&majorId=" + id
            }
        );
        //$('#function-panel').panel("refresh" );

    }

    function deleteSelect(){
        var rows = $("#riskManageMajorList").datagrid('getSelections');
        if(rows == null || rows.length ==0){
            tip("请选择要删除的数据");
        }else if(rows.length>1){
            tip("请选择要一条删除的数据");
        }else{
            $.dialog.confirm('删除${riskManager}会清空之前添加的风险，确定删除吗?', function() {
                $.ajax({
                    url : "riskManageController.do?doDelMajor",
                    type : 'post',
                    cache : false,
                    async: true,
                    data : {
                        id:rows[0].id
                    },
                    success : function(data) {
                        jQuery("#riskManageMajorList").datagrid('reload');
                        window.top.reload_riskManageList.call();
                    }
                });
            });
        }
    }
</script>