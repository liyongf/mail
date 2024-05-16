<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <br>
        <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px">
        <input  name="queryHandleStatus" value="0" type="hidden">
           <label>
               <input name="queryHandleStatusTem" type="radio" value="all" >
               全部
           </label>
           <label>
            <input name="queryHandleStatusTem" type="radio" value="0" checked="checked">
									未完成
           </label>
           <label>
								<input name="queryHandleStatusTem" type="radio" value="1">
									已完成
           </label>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="riskManagePostTaskAllList" checkbox="true" pagination="true" fitColumns="true" title="岗位管控任务" actionUrl="riskManageTaskController.do?datagridPost" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="任务创建时间"  field="createDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" align="center" ></t:dgCol>
            <t:dgCol title="创建人"  field="createName"   queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="管控类型"  field="manageType" dictionary="manageType" hidden="false" queryMode="single"   width="120" align="center"></t:dgCol>
            <t:dgCol title="管控时间"  field="manageTime" formatter="yyyy-MM-dd" query="true"  queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="管控班次"  field="manageShift" dictionary="workShift" hidden="false" queryMode="single" query="true"  width="120" align="center"></t:dgCol>
            <t:dgCol title="备注"  field="remark"   queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgToolBar title="添加任务" icon="icon-add" url="riskManageTaskController.do?goAddPost&manageType=post" funname="add" operationCode="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="riskManageTaskController.do?goUpdatePost" funname="update" operationCode="update" ></t:dgToolBar>
            <t:dgToolBar title="删除"  icon="icon-remove" url="riskManageTaskController.do?doBatchDelAllPost" funname="deleteALLSelect" operationCode="batchdelete" ></t:dgToolBar>
            <t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
           <t:dgFunOpt funname="taskResultAdd(id)" title="管控任务清单列表"  urlclass="ace_button" operationCode="showRMT"></t:dgFunOpt>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">

    $(document).ready(function(){
        var datagrid = $("#riskManagePostTaskAllListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");

        $("input[name='queryHandleStatusTem']").change(function() {
            var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
            $("input[name='queryHandleStatus']").val(selectedvalue);
            riskManagePostTaskAllListsearch();
        });

    });

    window.top["reload_riskManagePostTaskAllList"]=function(){
        $("#riskManagePostTaskAllList").datagrid("reload");
    };

    function showRiskManageTask(id){
        var url = "riskManageTaskController.do?list&manageType=${manageType}&taskAllId="+id;
        addOneTab("管控任务清单",url,"default");
    }

    function taskResultAdd(id){
        var url = "riskManageResultController.do?postList&postTaskAllId="+id;
        addOneTab("管控任务清单",url,"default");
    }
    function deleteALLSelect(title,url,gname) {
        gridname=gname;
        var ids = [];
        var rows = $("#"+gname).datagrid('getSelections');
        if (rows.length > 0) {
            $.dialog.setting.zIndex = getzIndex(true);
            $.dialog.confirm('你确定永久删除该数据吗?', function(r) {
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
                        cache : false,
                        beforeSend: function(data){
                            $.messager.progress({
                                text : "正在删除风险清单......",
                                interval : 100
                            });
                        },
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
                        complete: function(data){
                            $.messager.progress('close');
                        }
                    });
                }
            });
        } else {
            tip("请选择需要删除的数据");
        }
    }
</script>