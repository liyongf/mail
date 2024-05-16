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
        <t:datagrid name="riskManageAllTaskList" checkbox="true" pagination="true" fitColumns="true" title="${riskManageName}录入" actionUrl="riskManageResultController.do?datagrid&manageType=${manageType}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="任务创建时间"  field="createDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="创建人"  field="createName"   queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="管控类型"  field="manageType" dictionary="manageType" hidden="false" queryMode="single"   width="120"></t:dgCol>
            <t:dgCol title="管控时间"  field="manageTime" formatter="yyyy-MM-dd" query="true"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="管控班次"  field="manageShift" dictionary="workShift" hidden="false" queryMode="single" query="true"  width="120"></t:dgCol>
            <t:dgCol title="备注"  field="remark"   queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="操作" field="opt" width="200" align="center"></t:dgCol>
            <t:dgFunOpt funname="taskResultAdd(id)" title="管控任务录入结果"  urlclass="ace_button" operationCode="taskResultAdd"></t:dgFunOpt>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    function taskResultAdd(id){
        var url = "riskManageResultController.do?list&manageType=${manageType}&taskAllId="+id;
        addOneTab("管控任务结果",url,"default");
    }
    $(document).ready(function(){
        var datagrid = $("#riskManageAllTaskListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");

        $("input[name='queryHandleStatusTem']").change(function() {
            var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
            $("input[name='queryHandleStatus']").val(selectedvalue);
            riskManageAllTaskListsearch();
        });

    });
</script>