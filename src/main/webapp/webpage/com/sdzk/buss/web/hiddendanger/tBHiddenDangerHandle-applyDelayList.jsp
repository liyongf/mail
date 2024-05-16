<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tBHiddenDangerHandleList" checkbox="true" onLoadSuccess="replaceProblemDesc" fitColumns="false" title="申请延期隐患" actionUrl="tBHiddenDangerHandleController.do?applyDelayDatagrid" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="隐患检查主键"  field="hiddenDanger.id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="原限期时间"  field="hiddenDanger.limitDate"  formatter="yyyy-MM-dd"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="申请限期时间"  field="hiddenDanger.applyEntity.limitDateNew"  formatter="yyyy-MM-dd"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="申请原因"  field="hiddenDanger.applyEntity.applyReason"    sortable="false" width="120"></t:dgCol>
            <t:dgCol title="申请人"  field="hiddenDanger.applyEntity.applyMan.realName" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="受理人"  field="hiddenDanger.applyEntity.acceptMan.realName" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="检查班次"  field="hiddenDanger.shift" hidden="false" dictionary="workShift" query="false"  queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="信息来源"  field="hiddenDanger.manageType" dictionary="manageType"  queryMode="single"  sortable="false" width="90" align="center"></t:dgCol>
            <t:dgCol title="问题地点"  field="hiddenDanger.address.address"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="检查人"  field="hiddenDanger.fillCardManNames" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="问题描述"  field="hiddenDanger.problemDesc" formatterjs="valueTitle"  queryMode="group"  sortable="false" width="120"></t:dgCol>
            <t:dgCol title="隐患类型"  field="hiddenDanger.riskType" dictionary="risk_type"  queryMode="single"  sortable="false" width="90" align="center"></t:dgCol>
            <t:dgCol title="隐患等级"  field="hiddenDanger.hiddenNature"   dictionary="hiddenLevel"  queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="责任部门"  field="hiddenDanger.dutyUnit.departname" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="责任人"  field="hiddenDanger.dutyMan"   queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="发现时间"  field="hiddenDanger.examDate"  formatter="yyyy-MM-dd"  query="false" queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="危险源"  field="hiddenDanger.dangerId.hazard.hazardName"   queryMode="single" hidden="true" sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="整改人"  field="modifyMan" dictionary="t_s_base_user,id,realname,where 1=1"  query="false" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="问题分类"  field="hiddenDanger.examType" hidden="true"  dictionary="examType" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
            <c:if test="${beixulou eq 'true'}">
                <t:dgCol title="限期班次"  field="hiddenDanger.limitShift"  dictionary="workShift"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            </c:if>
            <c:if test="${beixulou ne 'true'}">
                <t:dgCol title="限期班次"  field="hiddenDanger.limitShift"  dictionary="workShift" hidden="true" queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            </c:if>
            <t:dgCol title="复查日期"  field="reviewDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="复查人"  field="reviewMan.realName" dictionary="t_s_base_user,id,realname,where 1=1" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="处理状态"  field="handlelStatus" dictionary="handelStatus"   queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="驳回备注"  field="rollBackRemark" formatterjs="valueTitle" hidden="false" query="false" queryMode="group"  sortable="false" width="300"></t:dgCol>
            <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>

            <t:dgToolBar operationCode="dealApply" title="延期批复" icon="icon-edit"  funname="dealApply"></t:dgToolBar>
            <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="查看" icon="icon-search" url="riskManageResultController.do?updateHd" width="900" height="600" funname="detail" operationCode="detail"></t:dgToolBar>--%>
            <t:dgToolBar title="查看" icon="icon-search" url="tBHiddenDangerHandleController.do?goUpdate&load=detail" funname="detail" width="950" height="550" operationCode="detail"></t:dgToolBar>
            <c:if test="${isSunAdmin == 'YGADMIN'}">
                <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
            </c:if>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">
//    function detail(title,url, id,width,height) {
//        var rowsData = $('#'+id).datagrid('getSelections');
//        if (!rowsData || rowsData.length == 0) {
//            tip('请选择查看项目');
//            return;
//        }
//        if (rowsData.length > 1) {
//            tip('请选择一条记录再查看');
//            return;
//        }
//        url += '&load=detail&id='+rowsData[0]["hiddenDanger.id"];
//        createdetailwindow(title,url,width,height);
//    }
function dealApply() {
    var rows = $("#tBHiddenDangerHandleList").datagrid('getSelections');
    if (rows.length == 0) {
        tip("请选择一条隐患。");
    } else if (rows.length > 1) {
        tip("请选择<a style='color: red;font-size:100% '>一条</a>隐患");
    }else{
        openwindow("延期批复","tBHiddenDangerHandleController.do?goDealApply&id="+rows[0].id,"tBHiddenDangerHandleList",500,600);
    }
}

//导出
function ExportXls() {
    var rows = $("#tBHiddenDangerHandleList").datagrid('getSelections');
    if (rows.length == 0) {
       tip("请选择需要导出的申请延期的隐患。");
    } else if (rows.length >=1) {
        var idsTemp = new Array();
        for (var i = 0; i < rows.length; i++) {
            idsTemp.push(rows[i].id);
        }
        var idt = idsTemp.join(",");
        $.dialog.confirm("是否确认导出"+idsTemp.length+"条记录？", function () {
            JeecgExcelExport("tBHiddenDangerHandleController.do?exportExcel&ids="+idt, "tBHiddenDangerHandleList");
        });
    }
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

/**
 *  阳光账号隐藏数据功能
 * */
function sunHidden() {
    var rows = $("#tBHiddenDangerHandleList").datagrid('getSelections');
    if (rows.length < 1) {
        tip("请选择需要隐藏的数据");
    } else {
        var idsTemp = new Array();
        for (var i = 0; i < rows.length; i++) {
            idsTemp.push(rows[i].id);
        }
        var idt = idsTemp.join(",");

        $.ajax({
            type: 'POST',
            url: 'tBHiddenDangerHandleController.do?sunshine',
            dataType:"json",
            async:true,
            cache: false,
            data: {
                ids:idt
            },
            success:function(data){
                var msg = data.msg;
                tip(msg);
                reloadTable();
            },
            error:function(data){
            }
        });
    }
}
</script>