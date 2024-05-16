<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="riskManageTaskAllManageList" title="任务管理"  fitColumns="false" checkbox="true" actionUrl="assessmentScoreController.do?gkTaskdatagrid&yearMonth=${yearMonth}&selectId=${selectId}" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
            <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="管控类型" field="manageType" dictionary="taskManageTypeTemp"  width="150" ></t:dgCol>
            <t:dgCol title="管控时间" field="manageTime" formatter="yyyy-MM-dd"  width="150"  queryMode="group"></t:dgCol>
            <t:dgCol title="管控名称" field="manageName"   width="150"></t:dgCol>
            <t:dgCol title="结束时间" field="endDate" formatter="yyyy-MM-dd"  width="150"  queryMode="group"></t:dgCol>
            <t:dgCol title="主要内容" field="mainContents"   width="300"></t:dgCol>
            <t:dgCol title="组织人员" field="organizerMan" dictionary="t_s_base_user,id,realname,where 1=1"   width="150"></t:dgCol>
            <t:dgCol title="任务状态" field="status" replace="进行中_0,已完成_1"   width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="200"></t:dgCol>
            <t:dgFunOpt funname="viewingProgress(id)" title="查看进度"  urlclass="ace_button" ></t:dgFunOpt>
        </t:datagrid>
    </div>
</div>

<script type="text/javascript">

    window.top["close_socreGkTaskList"]=function(){
        $.messager.progress('close');
        frameElement.api.close();
    };

    $(document).ready(function(){


    });

    window.top["reload_riskManageTaskAllManageList"]=function(){
        if(typeof(this.msg)!='undefined' && this.msg!=null&&this.msg!=""){
            tip(this.msg);
        }
        $("#riskManageTaskAllManageList").datagrid("reload");
    };


    function viewingProgress(id) {
        var url = "assessmentScoreController.do?viewingProgressList&riskManageTaskAllManageId="+id;
        createdetailwindow('任务进度',url,900,400);
    }

    //导出
    function ExportXls(id) {
        JeecgExcelExport("riskManageTaskAllManageController.do?exportXls&id="+id, "riskManageTaskAllManageList");
    }

    function deleteALLSelect(id) {
        $.dialog.confirm('你确定永久删除该任务吗?分配的任务会一同删除', function(r) {
            if (r) {
                $.ajax({
                    url : "riskManageTaskAllManageController.do?doBatchDelAll",
                    type : 'post',
                    data : {
                        taskAllManageId : id
                    },
                    cache : false,
                    beforeSend: function(data){
                        $.messager.progress({
                            text : "正在删除任务......",
                            interval : 100
                        });
                    },
                    success : function(data) {
                        var d = $.parseJSON(data);
                        if (d.success) {
                            var msg = d.msg;
                            tip(msg);
                            $("#riskManageTaskAllManageList").datagrid("reload");
                        }
                    },
                    complete: function(data){
                        $.messager.progress('close');
                    }
                });
            }
        });
    }


</script>