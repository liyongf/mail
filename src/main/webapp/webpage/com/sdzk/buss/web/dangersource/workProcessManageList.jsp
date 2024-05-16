<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,ztree"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
 <%--<t:datagrid name="roleList" title="作业过程列表" actionUrl="tBWorkProcessManageController.do?workGrid"  idField="id" sortName="name" sortOrder="asc" queryMode="group" checkbox="true" pagination="true">--%>
     <t:datagrid name="roleList" checkbox="true"  pagination="true" fitColumns="true" title="作业过程管理" actionUrl="tBWorkProcessManageController.do?workGrid" idField="id" fit="true" queryMode="group" sortName="name" sortOrder="desc">
     <t:dgCol title="主键" field="id" hidden="true"></t:dgCol>
     <t:dgCol title="作业过程名称" field="name" width="120" query="true" queryMode="single"></t:dgCol>
     <t:dgCol title="专业" field="major" query="true" dictionary="proCate_gradeControl"  queryMode="single" width="120"></t:dgCol>
     <t:dgCol title="关联风险数量" field="count" width="120"></t:dgCol>
     <t:dgCol title="common.operation" field="opt" width="150"></t:dgCol>
     <t:dgFunOpt funname="dangerListByWork(id,name)" title="关联风险" urlclass="ace_button"  urlfont="fa-user" ></t:dgFunOpt>

     <t:dgToolBar title="录入" langArg="common.role" icon="icon-add" url="tBWorkProcessManageController.do?goAdd" funname="add"></t:dgToolBar>
     <t:dgToolBar title="编辑" langArg="common.role" icon="icon-edit" url="tBWorkProcessManageController.do?goUpdate" funname="update"></t:dgToolBar>
     <t:dgToolBar title="批量删除" langArg="common.role" icon="icon-remove" url="tBWorkProcessManageController.do?goUpdate" operationCode="delete" funname="doDeleteALLSelect"></t:dgToolBar>
     <%--<t:dgToolBar title="作业过程上报" langArg="common.role" icon="icon-reload" url="tBWorkProcessManageController.do?uploadWorkProcess" funname="uploadWorkProcess"></t:dgToolBar>--%>
 </t:datagrid>
</div>
</div>
<div region="east" style="width:800px" split="true">
<div tools="#tt" class="easyui-panel" title='关联的风险' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>
</div>
<script src = "webpage/com/sdzk/buss/web/specialevaluation/tBSpecialEvaluationList.js"></script>
<script type="text/javascript">
    function doDeleteALLSelect(){
        var rowsData = $('#roleList').datagrid('getSelections');
        if(rowsData == null || rowsData.length < 1){
            tip("请选择需要删除的条目");
        }else{
            var ids = new Array();
            for (var i = 0; i < rowsData.length; i++) {
                ids.push(rowsData[i].id);
            }
            deleteALLSelect('批量删除','tBWorkProcessManageController.do?doDel','roleList',null,null);
        }
    }
function setfunbyrole(id,roleName) {
 $("#function-panel").panel(
         {
             title :roleName+ ':' + '<t:mutiLang langKey="current.permission"/>',
             href:"roleController.do?fun&roleId=" + id
         }
 );
 //$('#function-panel').panel("refresh" );

}

function dangerListByWork(id,name) {
 $("#function-panel").panel(
         {
             title :name+ ':' + '关联的风险',
             href:"tBWorkProcessManageController.do?riskList&id=" + id
         }
 );

 //$('#function-panel').panel("refresh" );

}

//删除作业过程
function delWorkPro(id){
 var tabName= 'roleList';
 var url= 'tBWorkProcessManageController.do?doDel&id='+id;
 $.dialog.confirm('<t:mutiLang langKey="confirm.delete.this.record"/>', function(){
     doSubmit(url,tabName);
     rowid = '';
     $("#function-panel").html("");//删除作业过程后，清空关联的风险
 }, function(){
 });
}
//导入
function ImportXls() {
 openuploadwin('Excel导入', 'roleController.do?upload', "roleList");
}

//导出
function ExportXls() {
 JeecgExcelExport("roleController.do?exportXls", "roleList");
}

//模板下载
function ExportXlsByT() {
 JeecgExcelExport("roleController.do?exportXlsByT", "roleList");
}

//作业过程上报
/**
 * 作业过程上报
 * 张赛超
 */
function uploadWorkProcess(title,url,gname) {
    var ids = [];
    var rows = $("#"+gname).datagrid('getSelections');
    if (rows.length > 0) {
        $.dialog.setting.zIndex = getzIndex(true);
        $.dialog.confirm('你确定上报该数据吗?', function(r) {
            if (r) {
                for ( var i = 0; i < rows.length; i++) {
                    ids.push(rows[i].id);
                }
                $.ajax({
                    url : url,
                    type : 'post',
                    data : {
                        ids : ids.join(',')
                    },
                    beforeSend: function(data){
                        $.messager.progress({
                            text : "正在上报数据......",
                            interval : 100
                        });
                    },
                    cache : false,
                    success : function(data) {
                        var d = $.parseJSON(data);
                        if (d.success) {
                            var msg = d.msg;
                            tip(msg);
                            reloadTable();
                            $("#"+gname).datagrid('unselectAll');
                            ids='';
                        }
                    },
                    complete: function(date){
                        $.messager.progress('close');
                    },
                    error:function(data){
                        tip("操作失败");//should not reach here
                    }
                });
            }
        });
    } else {
        tip("请选择需要上报的数据");
    }
}

</script>
