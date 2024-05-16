<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="manageUserColums" >
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">管控人：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="manageUserSelect" style="width: 130px;height: 15px"></div>
                <input class="inuptxt" type="hidden" style="width: 100px" name="manageUser" id="manageUser">
             </span>
        </span>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">管控人单位：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="manageUserDepartUnitSelect" style="width: 130px;height: 15px"></div>
                <input class="inuptxt" type="hidden" style="width: 100px" name="manageUserDepartUnit" id="manageUserDepartUnit">
             </span>
        </span>
    </div>
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
        <t:datagrid name="riskManageRecordList" checkbox="true" pagination="true" fitColumns="true" title="${riskManageName}记录" actionUrl="riskManageTaskController.do?recordDatagrid&manageType=${manageType}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="任务创建时间"  field="createDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="管控类型"  field="manageType" dictionary="manageType" hidden="false" queryMode="single"   width="120" align="center"></t:dgCol>
            <t:dgCol title="管控时间"  field="manageTime" formatter="yyyy-MM-dd" query="true"  queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="管控班次"  field="manageShift" dictionary="workShift" hidden="false" queryMode="single" query="true"  width="120" align="center"></t:dgCol>
            <t:dgCol title="备注"  field="remark"   queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="管控人"  field="createName"   queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
            <t:dgFunOpt funname="showRiskManageTask(id)" title="管控任务清单列表"  urlclass="ace_button" operationCode="showRMT"></t:dgFunOpt>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">

    $(document).ready(function(){

        var datagrid = $("#riskManageRecordListtb");
        $("#riskManageRecordListForm").append($("#tempSearchColums div[name='manageUserColums']").html());
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");

        $("#tempSearchColums").empty();
        var  userSelected = "";
        userSelected = $('#manageUserSelect').magicSuggest({
            data: 'magicSelectController.do?getUserList',
            allowFreeEntries: false,
            valueField: 'realName',
            placeholder: '输入或选择',
            maxSelection: 1,
            selectFirst: true,
            highlight: false,
            matchField:['spelling','realName','userName','fullSpelling'],
            displayField: 'realName'
        });
        $(userSelected).on('selectionchange', function(e,m){
            $("#manageUser").val(userSelected.getValue());
        });

        getDepartMagicSuggest($("#manageUserDepartUnitSelect"), $("#manageUserDepartUnit"));
        $("input[name='queryHandleStatusTem']").change(function() {
            var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
            $("input[name='queryHandleStatus']").val(selectedvalue);
            riskManageRecordListsearch();
        });


    });

    window.top["reload_riskManageRecordList"]=function(){
        $("#riskManageRecordList").datagrid("reload");
        /*riskManageRecordListsearch();*/
    };

    function showRiskManageTask(id){
        var url = "riskManageTaskController.do?list&manageType=${manageType}&taskAllId="+id;
        addOneTab("管控任务清单",url,"default");
    }
</script>