<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">组织人员：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="organizerManSelect" style="width: 130px;height: 15px"></div>
                <input class="inuptxt" type="hidden" style="width: 100px" name="organizerMan" id="organizerMan">
             </span>
        </span>
        <br>
        <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px;text-align: center;width: 100%">
            <form id="thisForm" action="riskManageTaskController.do?manageRecordNew" method="post">
                    <label style="margin-left:15px"><input id="type1" name="typeAll" onclick="doQuery()" type="radio"
                                                           value="1"
                                                           <c:if test="${typeAll eq '1'}">checked="checked"</c:if>/>上级派发</label>
                    <label style="margin-left:15px"><input id="type2" name="typeAll" onclick="doQuery()" type="radio"
                                                           value="2"
                                                           <c:if test="${typeAll eq '2'}">checked="checked"</c:if>/>个人任务</label>
            </form>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="riskManageTaskAllManageList" title="任务管理" checkbox="true"  fitColumns="false" actionUrl="riskManageTaskAllManageController.do?datagrid&recordList=true" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
            <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="管控类型" field="manageType" dictionary="taskManageTypeTemp"  width="120" query="true" align="center"></t:dgCol>
            <t:dgCol title="管控时间" field="manageTime" formatter="yyyy-MM-dd"  width="120" query="true" queryMode="group" align="center"></t:dgCol>
            <t:dgCol title="管控名称" field="manageName"   width="120" align="center"></t:dgCol>
            <t:dgCol title="主要内容" field="mainContents"   width="200" align="center"></t:dgCol>
            <t:dgCol title="创建人" field="createName" width="120" align="center"></t:dgCol>
            <t:dgCol title="组织人员" field="organizerMan" dictionary="t_s_base_user,id,realname,where 1=1"   width="120" align="center"></t:dgCol>
            <t:dgCol title="风险数"  field="riskCount" align="center"  queryMode="group"  width="120" url="riskIdentificationController.do?fxList&riskManageTaskAllManageId={id}"  ></t:dgCol>
            <t:dgCol title="隐患数"  field="hdCount" align="center"  queryMode="group"  width="120" url="riskManageTaskAllManageController.do?hdList&riskManageTaskAllManageId={id}" ></t:dgCol>
            <t:dgCol title="危害因素落实数量"  field="implCount" align="center"  queryMode="group"  width="120"  url="riskIdentificationController.do?wxysImplList&riskManageTaskAllManageId={id}" ></t:dgCol>
            <t:dgCol title="任务状态" field="status" replace="进行中_0,已完成_1"  query="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="150"></t:dgCol>
            <t:dgFunOpt funname="viewingSurvey(id,riskCount,hdCount,implCount)" title="查看概况"  urlclass="ace_button" ></t:dgFunOpt>
            <t:dgFunOpt funname="viewingProgress(id)" title="查看进度"  urlclass="ace_button" ></t:dgFunOpt>
            <%--<t:dgToolBar title="编辑" icon="icon-edit" url="riskManageTaskAllManageController.do?addorupdate" funname="update"></t:dgToolBar>--%>
            <%--<t:dgToolBar title="查看" icon="icon-search" url="riskManageTaskAllManageController.do?addorupdate" funname="detail"></t:dgToolBar>--%>
        </t:datagrid>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function(){
        var datagrid = $("#riskManageTaskAllManageListtb");
        datagrid.find("form[id='riskManageTaskAllManageListForm']>span:last").after($("#tempSearchColums div[name='searchColums']").html());
        $("#tempSearchColums").empty();
        getUserMagicSuggestWithValue($('#organizerManSelect'),  $("#organizerMan"), "", false);

    });

    window.top["reload_riskManageTaskAllManageList"]=function(){
        $("#riskManageTaskAllManageList").datagrid("reload");
    };
    function doQuery(){
        $("#thisForm").submit();
    }

    function viewingSurvey(id,riskCount,hdCount,implCount) {
        var url = "riskManageTaskAllManageController.do?goSurvey&riskManageTaskAllManageId="+id+"&riskCount="+riskCount+"&hdCount="+hdCount+"&implCount="+implCount;
        createdetailwindow('任务概况',url,700,400);
    }

    function viewingProgress(id) {
        var url = "riskManageTaskAllManageController.do?viewingProgressList&riskManageTaskAllManageId="+id;
        createdetailwindow('任务进度',url,900,400);
    }

    //导出
    function ExportXls(id) {
        JeecgExcelExport("riskManageTaskAllManageController.do?exportXls&id="+id, "riskManageTaskAllManageList");
    }


</script>