<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <%--<span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">工作过程：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="fillCardManSelect" style="width: 130px;height: 15px"></div>
                <input class="inuptxt" type="hidden" style="width: 100px" name="hiddenDanger.fillCardMan.id">
             </span>
        </span>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="风险区域">风险区域：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="addressSelect" style="width: 130px;height: 15px"></div>
                 <input id="tBAddressInfoEntity_id" type="hidden" name="tBAddressInfoEntity_id">
             </span>
        </span>--%>
        <span style="display:-moz-inline-box;display:inline-block;margin-top: 8px;text-align: center;width: 100%">
            <input name="status" value="3" type="hidden" />
            <label style="margin-right: 5px;">
                <input name="statusTemp" type="radio" value="3" checked>&nbsp;待受理
            </label>
            <label style="margin-right: 5px;">
                <input name="statusTemp" type="radio" value="4">&nbsp;待回复
            </label>
            <label>
                <input name="statusTemp" type="radio" value="5">&nbsp;已回复
            </label>
        </span>
    </div>
    <div name="additionSearchColums" >
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="受理单位">受理单位：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="acceptDeptSelect" style="width: 120px;height: 16px"></div>
                 <input id="acceptDept_id" type="hidden" name="acceptDept_id">
             </span>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true" id="main_list">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="tBInvestigatePlanList" checkbox="true" pagination="true" fitColumns="false" title="排查计划受理列表" actionUrl="tBInvestigatePlanController.do?acceptDatagrid&investType=${investType}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <c:if test="${investType eq '1'}">
                <t:dgCol title="月份" field="startTime" formatter="yyyy-MM"  query="false" queryMode="single"  width="80"></t:dgCol>
                <t:dgCol title="月份" field="queryMonth" formatter="yyyy-MM" hidden="true" query="true" queryMode="single"  width="80"></t:dgCol>
            </c:if>
            <c:if test="${investType ne '1'}">
                <t:dgCol title="计划开始时间" field="startTime" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="80"></t:dgCol>
                <t:dgCol title="计划结束时间" field="endTime" formatter="yyyy-MM-dd"   queryMode="group"  width="80"></t:dgCol>
            </c:if>
            <t:dgCol title="风险点类型" dictionary="investPlan_riskPoint_type" field="riskPointType" query="false"  queryMode="single"  sortable="false"  width="80"></t:dgCol>
            <t:dgCol title="风险点" field="riskPointName" width="180" query="false"></t:dgCol>
            <%--<t:dgCol title="危险源" field="riskName" width="260" query="false"></t:dgCol>--%>
            <t:dgCol title="受理单位"  field="acceptDepartName"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="受理时间"  field="acceptTime" formatter="yyyy-MM-dd hh:mm"   queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="排查人"  field="acceptUserRealName"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="排查描述"  field="investigateDesc"  formatterjs="valueTitle"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="排查时间"  field="investigateTime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="派发人" field="createName" width="80"></t:dgCol>
            <t:dgCol title="派发时间" field="createDate" width="80" formatter="yyyy-MM-dd"></t:dgCol>
            <t:dgCol title="要求完成时间" hidden="true" field="completeTime" formatter="yyyy-MM-dd"   queryMode="group"  width="100"></t:dgCol>
            <t:dgCol title="状态" field="status" dictionary="investigatePlan_status"  query="false"  queryMode="single"  sortable="false"  width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <t:dgFunOpt title="关联隐患" funname="showHiddenDanger(id)" urlclass="ace_button"  urlfont="fa-search"></t:dgFunOpt>
            <t:dgToolBar title="查看" operationCode="detail" icon="icon-search" url="tBInvestigatePlanController.do?goDetail" funname="detail"></t:dgToolBar>
            <t:dgToolBar title="受理" operationCode="accept" icon="icon-edit" url="tBInvestigatePlanController.do?doAccept" funname="accept"></t:dgToolBar>
            <t:dgToolBar title="驳回" operationCode="rollback" icon="icon-edit" url="" funname="rollback"></t:dgToolBar>
            <t:dgToolBar title="回复" operationCode="reply" icon="icon-edit" url="tBInvestigatePlanController.do?doReply" funname="reply"></t:dgToolBar>
            <%--<t:dgToolBar title="关联隐患" icon="icon-search" url="" funname="showHiddenDanger(id)"></t:dgToolBar>--%>
        </t:datagrid>
    </div>
</div>
<div data-options="region:'east',
	title:'关联隐患列表',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
     style="width: 600px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="function-panel"></div>
</div>
<%--<div region="east" style="width: 600px;" split="true">--%>
    <%--<div tools="#tt" class="easyui-panel" title='关联隐患列表' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>--%>
<%--</div>--%>
<%--<div id="tt"></div>--%>
</div>
<script src = "webpage/com/sdzk/buss/web/investigateplan/tBInvestigatePlanList.js"></script>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">
    var li_east = 0;
    $(document).ready(function(){
        $("#tBInvestigatePlanListtb").find("input[name='queryMonth']").attr("class","Wdate").attr("style","height:30px;width:120px;").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
        $("#tBInvestigatePlanListtb").find("input[name='startTime_begin']").attr("class","Wdate").attr("style","height:30px;width:120px;").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
        $("#tBInvestigatePlanListtb").find("input[name='startTime_end']").attr("class","Wdate").attr("style","height:30px;width:120px;").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});

        var datagrid = $("#tBInvestigatePlanListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html());
        datagrid.find("form[id='tBInvestigatePlanListForm']").append($("#tempSearchColums div[name='additionSearchColums']").html());
        $("#tempSearchColums").empty();
        getDepartMagicSuggest($("#acceptDeptSelect"),$("#acceptDept_id"));

        $("span:contains('回复')").parents("a.l-btn").hide();
        /*getAddressMagicSuggest($("#addressSelect"), $("[name='tBAddressInfoEntity_id']"));
        getUserMagicSuggest($("#fillCardManSelect"), $("[name='hiddenDanger.fillCardMan.id']"));*/

        $("input[name='statusTemp']").change(function() {
            var selectedvalue = $("input[name='statusTemp']:checked").val();
            $("input[name='status']").val(selectedvalue);
            tBInvestigatePlanListsearch();
            if(selectedvalue == '3'){
                $("span:contains('受理')").parents("a.l-btn").show();
                $("span:contains('驳回')").parents("a.l-btn").show();
                $("span:contains('回复')").parents("a.l-btn").hide();
            } else if(selectedvalue == '4'){
                $("span:contains('受理')").parents("a.l-btn").hide();
                $("span:contains('驳回')").parents("a.l-btn").hide();
                $("span:contains('回复')").parents("a.l-btn").show();
            }else{
                $("span:contains('受理')").parents("a.l-btn").hide();
                $("span:contains('驳回')").parents("a.l-btn").hide();
                $("span:contains('回复')").parents("a.l-btn").hide();
            }
        });
    });



    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tBInvestigatePlanController.do?upload', "tBInvestigatePlanList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tBInvestigatePlanController.do?exportXls","tBInvestigatePlanList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tBInvestigatePlanController.do?exportXlsByT","tBInvestigatePlanList");
    }

    function showHiddenDanger(id){
        $("#function-panel").panel(
            {
                href:"tBInvestigatePlanController.do?goHiddenList&planId="+id
            }
        );

        if(li_east == 0){
            $('#main_list').layout('expand','east');
        }
    }

    function accept(title,url,gname){
        var ids = [];
        var rows = $("#"+gname).datagrid('getSelections');
        if (rows.length > 0) {
            $.dialog.setting.zIndex = getzIndex(true);
            $.dialog.confirm('确认受理选中的排查计划吗?', function(r) {
                if (r) {
                    for (var i = 0; i < rows.length; i++) {
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
                                text : "正在受理......",
                                interval : 100
                            });
                        },
                        success : function(data) {
                            var d = $.parseJSON(data);
                            if (d.success) {
                                var msg = d.msg;
                                tip(msg);
                                $("#"+gname).datagrid('unselectAll');
                                ids='';
                            }
                        },
                        complete: function(data){
                            $.messager.progress('close');
                            jQuery('#tBInvestigatePlanList').datagrid('reload');
                        },
                        error:function(data){
                            tip("操作失败");//should not reach here
                        }
                    });
                }
            });
        } else {
            tip("请选择需要受理的排查计划");
        }
    }

    function rollback(title,url,gname){
        var rows = $("#"+gname).datagrid('getSelections');
        if (rows.length == 0) {
            tip("请选择需要驳回的排查计划");
        }else if(rows.length > 1){
            tip("每次只能驳回一个排查计划");
        }else{
            var okButton;
            if ($("#langCode").val() == 'en') {
                okButton = "Ok";
            } else {
                okButton = "确定";
            }
            var ids = new Array();
            for(var i =0 ;i<rows.length;i++){
                ids.push(rows[i].id);
            }
            var id =  ids.join(",");
            $.dialog({
                id: 'LHG1976D',
                title: "驳回确认",
                max: false,
                min: false,
                drag: false,
                resize: false,
                content: 'url:tBInvestigatePlanController.do?goRollback&id='+rows[0].id,
                lock: true,
                button: [{
                    name: okButton,
                    focus: true,
                    callback: function () {
                        iframe = this.iframe.contentWindow;
                        var rollBackRemark = $('#rollBackReason', iframe.document).val();
                        $.ajax({
                            url: "tBInvestigatePlanController.do?doRollback",
                            type: "post",
                            async: true,
                            data: {'ids': id, rollBackReason: rollBackRemark},
                            dataType: "json",
                            success: function (data) {
                                tip(data.msg);
                                $("#tBInvestigatePlanList").datagrid("clearSelections");
                                $("#tBInvestigatePlanList").datagrid("reload");
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
    }

    function reply(title,url,gname){
        var rows = $("#"+gname).datagrid('getSelections');
        if (rows.length == 0) {
            tip("请选择需要回复的排查计划");
        }else if(rows.length > 1){
            tip("每次只能回复一个排查计划");
        }else{
            var okButton;
            if ($("#langCode").val() == 'en') {
                okButton = "Ok";
            } else {
                okButton = "确定";
            }
            var ids = new Array();
            for(var i =0 ;i<rows.length;i++){
                ids.push(rows[i].id);
            }
            var id =  ids.join(",");
            $.dialog({
                id: 'LHG1976E',
                title: "排查计划回复",
                max: false,
                min: false,
                drag: false,
                resize: false,
                content: 'url:tBInvestigatePlanController.do?goReply&id='+rows[0].id,
                lock: true,
                button: [{
                    name: okButton,
                    focus: true,
                    callback: function () {
                        iframe = this.iframe.contentWindow;
                        var investigateTime = $('#investigateTime', iframe.document).val();
                        var investigateDesc = $('#investigateDesc', iframe.document).val();
                        $.ajax({
                            url: "tBInvestigatePlanController.do?doReply",
                            type: "post",
                            async: true,
                            data: {'ids': id, investigateTime: investigateTime,investigateDesc:investigateDesc},
                            dataType: "json",
                            success: function (data) {
                                tip(data.msg);
                                $("#tBInvestigatePlanList").datagrid("clearSelections");
                                $("#tBInvestigatePlanList").datagrid("reload");
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
    }

    function searchReset(str){
        $("#acceptDeptSelect").magicSuggest().clear();
        if(${investType eq '1'}){
            $("input[name='queryMonth']").val("");
        }else{
            $("input[name='startTime_begin']").val("");
            $("input[name='startTime_end']").val("");
        }
        tBInvestigatePlanListsearch();
    }
 </script>