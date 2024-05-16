<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<t:datagrid name="tBHiddenDangerHandleList" checkbox="true" onDblClick="dblClickDetail" onLoadSuccess="replaceProblemDesc" autoLoadData="true" fitColumns="false" title="隐患列表" actionUrl="tBInvestigatePlanController.do?hiddenListDatagrid&planId=${planId}" idField="id" fit="true" queryMode="group">
    <t:dgCol title="发现时间"  field="hiddenDanger.examDate"  formatter="yyyy-MM-dd"  query="false" queryMode="group"  sortable="false" width="80" align="center"></t:dgCol>
    <t:dgCol title="检查班次"  field="hiddenDanger.shift" hidden="false" dictionary="workShift" query="false"  queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
    <%--<t:dgCol title="危险源"  field="hiddenDanger.dangerId.hazard.hazardName"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>--%>
    <t:dgCol title="问题地点"  field="hiddenDanger.address.address"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
    <t:dgCol title="责任部门"  field="hiddenDanger.dutyUnit.departname" query="false" queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
    <t:dgCol title="责任人"  field="hiddenDanger.dutyMan"  queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
    <t:dgCol title="检查人"  field="hiddenDanger.showReviewManName" queryMode="single"  sortable="false" width="100" align="center"></t:dgCol>
    <%--<t:dgCol title="检查类型"  field="hiddenDanger.examType"  query="true" dictionary="examType" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>--%>
    <t:dgCol title="信息来源"  field="hiddenDanger.manageType"  query="false" dictionary="manageType" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
    <t:dgCol title="隐患等级"  field="hiddenDanger.hiddenNature"  query="false" dictionary="hiddenLevel"  queryMode="single"  sortable="false" width="70" align="center"></t:dgCol>

    <t:dgCol title="问题描述"  field="hiddenDanger.problemDesc"    queryMode="group"  sortable="false" width="300"></t:dgCol>
    <t:dgCol title="处理方式"  field="hiddenDanger.dealType" replace="限期整改_1,现场处理_2" width="80" align="center"></t:dgCol>
    <t:dgCol title="限期日期"  field="hiddenDanger.limitDate" formatter="yyyy-MM-dd" width="80" align="center"></t:dgCol>
    <t:dgCol title="限期班次"  field="hiddenDanger.limitShift" dictionary="workShift"  width="80" align="center"></t:dgCol>
    <t:dgCol title="整改日期"  field="modifyDate" formatter="yyyy-MM-dd" query="false"  queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
    <t:dgCol title="整改人"  field="modifyMan"  dictionary="t_s_base_user,id,realname,where 1=1"   queryMode="group"  sortable="false" width="100" align="center"></t:dgCol>
    <t:dgCol title="复查日期"  field="reviewDate" formatter="yyyy-MM-dd"   queryMode="group"  sortable="false" width="80" align="center"></t:dgCol>
    <t:dgCol title="复查人"  field="reviewMan"  dictionary="t_s_base_user,id,realname,where 1=1"  queryMode="group"  sortable="false" width="100" align="center"></t:dgCol>
    <t:dgCol title="处理状态"  field="handlelStatus" dictionary="handelStatus" query="false"  queryMode="single"  sortable="false" width="60" align="center"></t:dgCol>

    <t:dgCol title="驳回备注"  field="rollBackRemark"  hidden="true" query="false" queryMode="group"  sortable="false" width="300"></t:dgCol>
    <t:dgCol title="录入人"  field="createName" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
    <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
    <t:dgCol title="录入日期"  field="createDate" hidden="true" formatter="yyyy-MM-dd"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
    <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
    <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
    <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
    <c:if test="${type ne 'detail'}">
        <%--<t:dgToolBar title="添加关联隐患" icon="icon-add" url="tBInvestigatePlanController.do?goAddHiddenRel&planId=${planId}" funname="goAddHiddenRel"></t:dgToolBar>--%>
        <t:dgToolBar title="添加关联隐患" icon="icon-add" url="tBInvestigatePlanController.do?goAddHiddenRel&planId=${planId}" funname="choose_hiddenDanger"></t:dgToolBar>
    </c:if>
    <t:dgToolBar title="查看" icon="icon-search" url="tBHiddenDangerHandleController.do?goUpdate&load=detail" funname="detail" width="950" height="550"></t:dgToolBar>
    <c:if test="${type ne 'detail'}">
        <t:dgToolBar title="移除" icon="icon-remove" url="tBInvestigatePlanController.do?doBatchDelHidden&planId=${planId}" funname="deleteALLSelect"></t:dgToolBar>
    </c:if>
</t:datagrid>
<input type="hidden" name="hiddenId" id="hiddenId" />

<script>
    function dblClickDetail(rowIndex,rowData){
        var id=rowData.id;
        var url = "tBHiddenDangerHandleController.do?goUpdate&load=detail&id="+id;
        createdetailwindow("查看",url,950,400);
    }

    function replaceProblemDesc(){
        var problemDescCells = $("td[field='hiddenDanger.problemDesc']:gt(0)");
        problemDescCells.each(function(){
            var problemDesc = $(this).children().text();
            if(problemDesc.indexOf("\\r\\n") >= 0){
                problemDesc = problemDesc.replace(/[\\r\\n]/g, '');
                $(this).children().empty();
                $(this).children().append(problemDesc);
            }
        });
    }

    function goAddHiddenRel(){
        var okButton;
        if ($("#langCode").val() == 'en') {
          okButton = "Ok";
        } else {
          okButton = "确定";
        }
        $.dialog({
            id: 'LHG1976D',
            title: "添加关联隐患",
            max: false,
            min: false,
            drag: true,
            resize: false,
            content: 'url:tBInvestigatePlanController.do?goAddHiddenRel&planId=${planId}',
            lock: true,
            button: [{
                name: okButton,
                focus: true,
                callback: function () {
                    iframe = this.iframe.contentWindow;
                    var hiddenId = $('#hiddenId', iframe.document).val();
                    $.ajax({
                        url: "tBInvestigatePlanController.do?doAddHidden",
                        type: "post",
                        async: true,
                        data: {planId: '${planId}', hiddenIds: hiddenId},
                        dataType: "json",
                        success: function (data) {
                            tip(data.msg);
                            $("#tBHiddenDangerHandleList").datagrid("clearSelections");
                            $("#tBHiddenDangerHandleList").datagrid("reload");
                        },
                        error: function () {
                        }
                    });
                    this.close();
                    return false;
                }
            },{
                name: "关闭",
                focus: true,
                callback: function () {
                    this.close();
                }
            }],
            close: function () {
            }
        });
    }

    function choose_hiddenDanger(){
        if (typeof(windowapi) == 'undefined') {
            $.dialog({content: 'url:tBHiddenDangerHandleController.do?chooseHiddenDanger&planId=${planId}', zIndex: 2100, title: '选择隐患', lock: true, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_hiddenDanger, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        } else {
            $.dialog({content: 'url:tBHiddenDangerHandleController.do?chooseHiddenDanger&planId=${planId}', zIndex: 2100, title: '选择隐患', lock: true, parent: windowapi, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_hiddenDanger, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        }
    }

    function clickcallback_hiddenDanger(){
        iframe = this.iframe.contentWindow;
        var id = iframe.gettBHiddenDangerHandleListSelections('id');
        $("input[name='hiddenId']").val(id);
        var hiddenIds = $("input[name='hiddenId']").val();
        $.ajax({
            url: "tBInvestigatePlanController.do?doAddHidden",
            type: "post",
            async: true,
            data: {planId: '${planId}', hiddenIds: hiddenIds},
            dataType: "json",
            success: function (data) {
                tip(data.msg);
                $("#tBHiddenDangerHandleList").datagrid("clearSelections");
                $("#tBHiddenDangerHandleList").datagrid("reload");
            },
            error: function () {
            }
        });
    }
</script>

