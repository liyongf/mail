<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%@ page import="org.jeecgframework.core.util.DicUtil" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
      <t:datagrid name="riskTaskList" title="风险辨识任务" fitColumns="false" checkbox="true"
                  actionUrl="riskTaskController.do?datagridReport" idField="id" fit="true" queryMode="group" sortOrder="desc" sortName="createDate">
          <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
          <t:dgCol title="辨识活动类型" field="taskType" dictionary="risk_task_type" width="120" sortable="false" align="center" query="true"></t:dgCol>
          <t:dgCol title="辨识活动名称" field="taskName" width="120" sortable="false" align="center" ></t:dgCol>
          <t:dgCol title="开始日期" field="startDate" formatter="yyyy-MM-dd" width="120" align="center" query="true" queryMode="group"></t:dgCol>
          <t:dgCol title="结束日期" field="endDate" formatter="yyyy-MM-dd" width="120" align="center" query="true" queryMode="group"></t:dgCol>
          <t:dgCol title="组织人员" field="organizerMan" dictionary="t_s_base_user,id,realname,where 1=1" width="120" sortable="false" align="center" ></t:dgCol>
          <t:dgCol title="参与人员" field="participantManNames" width="200" sortable="false" align="center" ></t:dgCol>
          <t:dgCol title="任务状态" field="status" width="200" replace="完成_1,进行中_0" sortable="false" align="center" query="true"></t:dgCol>
          <t:dgCol title="participant"  field="participant"  queryMode="single" hidden="true"  width="60" align="center"></t:dgCol>
          <t:dgCol title="organizerOrCreateBy"  field="organizerOrCreateBy"  queryMode="single" hidden="true"  width="60" align="center"></t:dgCol>
          <t:dgCol title="exportDoc"  field="exportDoc"  queryMode="single" hidden="true"  width="60" align="center"></t:dgCol>
          <t:dgCol title="isUpdate"  field="isUpdate"  queryMode="single" hidden="true"  width="60" align="center"></t:dgCol>
          <t:dgCol title="操作" field="opt" width="500"></t:dgCol>
          <%--<t:dgFunOpt funname="addRisk(id)" title="开始辨识"  urlclass="ace_button" exp="participant#eq#0"></t:dgFunOpt>--%>
          <%--<t:dgFunOpt funname="endRisk(id)" title="结束辨识"  urlclass="ace_button" exp="participant#eq#0"></t:dgFunOpt>--%>
          <%--<t:dgFunOpt funname="viewingProgress(id)" title="查看进度"  urlclass="ace_button" ></t:dgFunOpt>--%>
          <%--<t:dgFunOpt funname="endTask(id)" title="结束任务"  urlclass="ace_button" exp="organizerOrCreateBy#eq#0"></t:dgFunOpt>--%>
          <t:dgFunOpt funname="openPage(id,taskType)" title="生成辨识报告"  urlclass="ace_button" exp="organizerOrCreateBy#eq#0&&exportDoc#eq#0&&isUpdate#eq#0"></t:dgFunOpt>
          <t:dgFunOpt funname="openPage(id,taskType)" title="编辑辨识报告"  urlclass="ace_button" exp="organizerOrCreateBy#eq#0&&exportDoc#eq#0&&isUpdate#eq#1"></t:dgFunOpt>
          <t:dgFunOpt funname="ExportDoc(id,taskType)" title="查看辨识报告"  urlclass="ace_button" exp="organizerOrCreateBy#eq#0&&exportDoc#eq#0"></t:dgFunOpt>
         <%-- <t:dgFunOpt funname="deleteSelect(id)" title="删除"  urlclass="ace_button" exp="organizerOrCreateBy#eq#0"/>
          <t:dgToolBar title="录入" icon="icon-add" url="riskTaskController.do?addorupdate" funname="add"></t:dgToolBar>
          <t:dgToolBar title="编辑" icon="icon-edit" url="riskTaskController.do?addorupdate" funname="updateAll"></t:dgToolBar>--%>
          <t:dgToolBar title="查看" icon="icon-search" url="riskTaskController.do?addorupdate&detail=true" funname="detail"></t:dgToolBar>
      </t:datagrid>
  </div>
 </div>




<script type="text/javascript">
    function addRisk(id){
        $.ajax({
            url : "riskTaskController.do?isAddRisk",
            type : 'post',
            cache : false,
            async: true,
            data : {
                id:id
            },
            success : function(data) {
                var d = $.parseJSON(data);
                if (d=="0") {
                    tip("辨识已结束");
                }else{
                    var url = "riskIdentificationController.do?list&riskTaskId="+id;
                    addOneTab("风险辨识",url,"default");
                }
            }
        });

    }
    function viewingProgress(id) {
        var url = "riskTaskController.do?viewingProgressList&riskTaskId="+id;
        createdetailwindow('任务进度',url,700,400);
    }
    function ExportDoc(id,taskType) {
        $.ajax({
            url : "riskTaskController.do?isExportDoc",
            type : 'post',
            cache : false,
            async: true,
            data : {
                id:id
            },
            success : function(data) {
                var d = $.parseJSON(data);
                if (d=="1") {
                    tip("任务未结束");
                }else{
                    if(taskType=='3'){
                        JeecgExcelExport("riskTaskController.do?exportDoc3&riskTaskId="+id,"riskTaskList");
                    }else{
                        JeecgExcelExport("riskTaskController.do?exportDoc&riskTaskId="+id,"riskTaskList");
                    }

                }
            }
        });
    }

    function openPage(id,taskType) {
        $.ajax({
            url : "riskTaskController.do?isExportDoc",
            type : 'post',
            cache : false,
            async: true,
            data : {
                id:id
            },
            success : function(data) {
                var d = $.parseJSON(data);
                if (d=="1") {
                    tip("任务未结束");
                }else{
                    if(taskType=='3'){
                        openwindow("填写数据", "tBSpecialReportController.do?goAdd&ids=" + id, "riskTaskList", 1200, 600);
                    }else{
                        openwindow("填写数据", "tBYearReportController.do?goAdd&ids=" + id, "riskTaskList", 1200, 600);
                    }

                }
            }
        });
    }

    function endRisk(id){
        $.ajax({
            url : "riskTaskController.do?isEndRisk",
            type : 'post',
            cache : false,
            async: true,
            data : {
                id:id
            },
            success : function(data) {
                var d = $.parseJSON(data);
                if (d=="1") {
                    tip("存在草稿或未审核的风险");
                }else{
                    $.ajax({
                           url : "riskTaskController.do?endRisk",
                           type : 'post',
                           cache : false,
                           async: true,
                           data : {
                               id:id
                           },
                           success : function(data) {
                               var d = $.parseJSON(data);
                               if (d.success) {
                                   tip(d.msg);
                                   $('#riskTaskList').datagrid('reload');
                               }
                           }
                       });
                }
            }
        });
    }

    function updateAll(title,url,gname) {
        var ids = [];
        var rows = $("#"+gname).datagrid('getSelections');
        if (rows.length != 1) {
            tip("请选择一条需要编辑的数据");
        } else {
            $.ajax({
                url : "riskTaskController.do?isDeleteSelect",
                type : 'post',
                cache : false,
                async: true,
                data : {
                    id:rows[0].id
                },
                success : function(data) {
                    var d = $.parseJSON(data);
                    if (d=="1") {
                        tip("辨识人员已开始辨识");
                    }else{
                        update(title,url,'riskTaskList');
                    }
                }
            });
        }
    }

    function endTask(id){
        $.ajax({
            url : "riskTaskController.do?isEndTask",
            type : 'post',
            cache : false,
            async: true,
            data : {
                id:id
            },
            success : function(data) {
                var d = $.parseJSON(data);
                if (d=="1") {
                    tip("参与人还未结束辨识");
                }else{
                    $.ajax({
                        url : "riskTaskController.do?endTask",
                        type : 'post',
                        cache : false,
                        async: true,
                        data : {
                            id:id
                        },
                        success : function(data) {
                            var d = $.parseJSON(data);
                            if (d.success) {
                                tip(d.msg);
                                $('#riskTaskList').datagrid('reload');
                            }
                        }
                    });
                }
            }
        });
    }

    function deleteSelect(id) {
        $.ajax({
            url : "riskTaskController.do?isDeleteSelect",
            type : 'post',
            cache : false,
            async: true,
            data : {
                id:id
            },
            success : function(data) {
                var d = $.parseJSON(data);
                if (d=="1") {
                    tip("已存在辨识的风险");
                }else{
                    $.ajax({
                        url : "riskTaskController.do?del",
                        type : 'post',
                        cache : false,
                        async: true,
                        data : {
                            id:id
                        },
                        success : function(data) {
                            var d = $.parseJSON(data);
                            if (d.success) {
                                tip(d.msg);
                                $('#riskTaskList').datagrid('reload');
                            }
                        }
                    });
                }
            }
        });
    }


</script>