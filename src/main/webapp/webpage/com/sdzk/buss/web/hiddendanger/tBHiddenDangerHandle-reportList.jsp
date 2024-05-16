<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="departSearchColums">
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="责任部门">责任部门：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="departnameSuggest" style="width: 130px;height: 15px"></div>
                 <input id="departname" type="hidden" name="hiddenDanger.dutyUnit.departname">
             </span>
        </span>
    </div>
    <div name="searchColums" >
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">检查人：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="fillCardManSelect" style="width: 130px;height: 15px"></div>
                <input class="inuptxt" type="hidden" style="width: 100px" name="hiddenDanger.fillCardMan.id">
             </span>
        </span>

        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="问题地点">问题地点：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="addressSelect" style="width: 130px;height: 15px"></div>
                 <input id="tBAddressInfoEntity_id" type="hidden" name="tBAddressInfoEntity_id">
             </span>
        </span>
    </div>
</div>
<div id="tempStatusReportOut" style="display: none">
    <div name="tempStatusReport" >
        <br>
       <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px;text-align: center;width: 100%">
        <input  name="reportStatusFront" value="1" type="hidden">
           <label>
               <input name="reportStatus" type="radio" value="1" checked="checked">
               待上报
           </label>
           <label>
               <input name="reportStatus" type="radio" value="2" >
               未闭环已上报
           </label>
           <label>
               <input name="reportStatus" type="radio" value="3" >
               闭环已上报
           </label>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tBHiddenDangerHandleList" onDblClick="dblClickDetail" onLoadSuccess="loadSuccess" autoLoadData="true" checkbox="true" fitColumns="false" title="综合查询" actionUrl="tBHiddenDangerHandleController.do?reportListDataGrid" idField="id" sortName="hiddenDanger.examDate" sortOrder="desc" fit="true" queryMode="group">
            <t:dgCol title="隐患来源"  field="hiddenDanger.origin"  query="false" dictionary="hiddenDangerOrigin" queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="发现时间"  field="hiddenDanger.examDate"  formatter="yyyy-MM-dd"  query="true" queryMode="group"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="检查班次"  field="hiddenDanger.shift" hidden="false" dictionary="workShift" query="true"  queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="危险源"  field="hiddenDanger.dangerId.hazard.hazardName"   queryMode="single"  sortable="true" width="120" align="center"></t:dgCol>

            <%--<t:dgCol title="关联危险源来源"  field="hiddenDanger.dangerId.origin"   queryMode="single"  replace="年度_2,专项_3" sortable="false" width="120" align="center"></t:dgCol>--%>
            <t:dgCol title="问题地点"  field="hiddenDanger.address.address"   queryMode="single"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="责任部门"  field="hiddenDanger.dutyUnit.departname" query="false" queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
            <t:dgCol title="责任人"  field="hiddenDanger.dutyMan" queryMode="group" sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="检查人"  field="hiddenDanger.fillCardManNames" queryMode="single"  sortable="false" width="100" align="center"></t:dgCol>
            <t:dgCol title="检查类型"  field="hiddenDanger.examType"  query="true" dictionary="examType" queryMode="single"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="隐患等级"  field="hiddenDanger.hiddenNature"  query="true" dictionary="hiddenLevel"  queryMode="single"  sortable="true" width="70" align="center"></t:dgCol>

            <t:dgCol title="问题描述"  field="hiddenDanger.problemDesc"  formatterjs="valueTitle"  queryMode="group"  sortable="false" width="300"></t:dgCol>
            <t:dgCol title="处理方式"  field="hiddenDanger.dealType" replace="限期整改_1,现场处理_2" width="80" align="center"></t:dgCol>
            <t:dgCol title="限期日期"  field="hiddenDanger.limitDate" formatter="yyyy-MM-dd" width="80" align="center"></t:dgCol>
            <t:dgCol title="限期班次"  field="hiddenDanger.limitShift" dictionary="workShift" hidden="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="整改日期"  field="modifyDate" formatter="yyyy-MM-dd" query="true"  queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="整改人"  field="modifyMan"  dictionary="t_s_base_user,id,realname,where 1=1"   queryMode="group"  sortable="true" width="100" align="center"></t:dgCol>
            <t:dgCol title="复查日期"  field="reviewDate" formatter="yyyy-MM-dd"   queryMode="group"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="复查人"  field="reviewMan"  dictionary="t_s_base_user,id,realname,where 1=1"  queryMode="group"  sortable="true" width="100" align="center"></t:dgCol>
            <t:dgCol title="处理状态"  field="handlelStatus" dictionary="handelStatus" query="true"  queryMode="single"  sortable="true" width="60" align="center"></t:dgCol>

            <t:dgCol title="驳回备注"  field="rollBackRemark"  formatterjs="valueTitle" hidden="false" query="false" queryMode="group"  sortable="false" width="300"></t:dgCol>
            <t:dgCol title="录入人"  field="createName"   queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="录入日期"  field="createDate" formatter="yyyy-MM-dd"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>

            <t:dgToolBar title="上报省局平台" icon="icon-putout" url="tBHiddenDangerHandleController.do?hiddenDangerReport" funname="goReport" operationCode="report" ></t:dgToolBar>
            <t:dgToolBar title="全部上报省局平台" icon="icon-putout" url="tBHiddenDangerHandleController.do?hiddenDangerReport" funname="goReportAll" operationCode="goReportAll"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="icon-search" url="tBHiddenDangerHandleController.do?goUpdate&load=detail" funname="detail" width="950" height="550" operationCode="detail"></t:dgToolBar>
            <t:dgToolBar operationCode="undoReport" title="撤回" icon="icon-edit" url="tBHiddenDangerHandleController.do?toReportHiddenDangerCallback" funname="toReportCallback" ></t:dgToolBar>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>
            <c:if test="${isSunAdmin == 'YGADMIN'}">
                <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
            </c:if>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">
/**
 * 撤回
 */
function toReportCallback(title,url,gname) {
    var ids = [];
    var rows = $("#"+gname).datagrid('getSelections');
    if (rows.length > 0) {
        $.dialog.setting.zIndex = getzIndex(true);
        $.dialog.confirm('你确定撤回该数据吗?', function(r) {
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
                            text : "正在撤回数据......",
                            interval : 100
                        });
                    },
                    success : function(data) {
                        var d = $.parseJSON(data);
                        if (d.success) {
                            var msg = d.msg;
                            tip(msg);
//                             reloadTable();
                            $("#"+gname).datagrid('unselectAll');
                            ids='';
                        }
                    },
                    complete: function(data){
                        $.messager.progress('close');

                        jQuery("#"+gname).datagrid('reload');
                        jQuery("#"+gname).treegrid('reload');

                    },
                    error:function(data){
                        tip("操作失败");//should not reach here
                    }
                });
            }
        });
    } else {
        tip("请选择需要撤回的数据");
    }
}

function goReportAll(title,url,gname) {
    gridname=gname;
    var ids = [];
    var type = $("input[name='reportStatus']:checked").val();

    $.dialog.confirm('你确定上报全部数据吗?', function(r) {
        this.close();
        if (r) {
            $.ajax({
                url : url,
                type : 'post',
                cache : false,
                data : {
                    ids : ids.join(','),
                    type : type
                },
                beforeSend: function(data){
                    $.messager.progress({
                        text : "正在上报数据......",
                        interval : 100
                    });
                },
                success : function(data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        tBHiddenDangerHandleListsearch();
                    }
                    var msg = d.msg;
                    tip(msg);
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
}

function goReport(title,url,gname) {
    gridname=gname;
    var ids = [];
    var type = 'choose';
    if(title!="全部上报"){
        var rows = $("#"+gname).datagrid('getSelections');
        if (rows.length > 0) {
            for ( var i = 0; i < rows.length; i++) {
                ids.push(rows[i].id);
            }
        } else {
            tip("请选择需要上报的数据");
            return;
        }
    } else {
        type =  $("input[name='reportStatus']:checked").val();
    }
    $.dialog.confirm('你确定上报所选中数据吗?', function(r) {
        this.close();
        if (r) {
            $.ajax({
                url : url,
                type : 'post',
                cache : false,
                data : {
                    ids : ids.join(','),
                    type : type
                },
                beforeSend: function(data){
                    $.messager.progress({
                        text : "正在上报数据......",
                        interval : 100
                    });
                },
                success : function(data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        reloadTable();
                        $("#"+gname).datagrid('unselectAll');
                        ids='';
                    }
                    var msg = d.msg;
                    tip(msg);
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
}
function addmask() {
    $.messager.progress({
        text : "正在上传数据......",
        interval : 100
    });
    setTimeout("$.messager.progress('close')",2000);
}
function dblClickDetail(rowIndex,rowData){
    var id=rowData.id;
    var url = "tBHiddenDangerHandleController.do?goUpdate&load=detail&id="+id;
    createdetailwindow("查看",url,950,600);
}
$(function() {
    var datagrid = $("#tBHiddenDangerHandleListtb");
    datagrid.find("form[id='tBHiddenDangerHandleListForm']>span:first").before($("#tempSearchColums div[name='departSearchColums']").html());
    datagrid.find("form[id='tBHiddenDangerHandleListForm']>span:last").before($("#tempSearchColums div[name='searchColums']").html());
    datagrid.find("div[name='searchColums']").append($("#tempStatusReportOut div[name='tempStatusReport']").html());
    $("#tempSearchColums").empty();
    getDepartMagicSuggest($("#departnameSuggest"), $("#departname"));
    getAddressMagicSuggest($("#addressSelect"), $("[name='tBAddressInfoEntity_id']"));
    getUserMagicSuggest($("#fillCardManSelect"), $("[name='hiddenDanger.fillCardMan.id']"));
    $("input[name='reportStatus']").change(function() {
        var selectedvalue = $("input[name='reportStatus']:checked").val();
        $("input[name='reportStatusFront']").val(selectedvalue);
        tBHiddenDangerHandleListsearch();
        if(selectedvalue == '1'){
            $("span:contains('上报省局平台')").parents("a.l-btn").show();
            $("span:contains('全部上报省局平台')").parents("a.l-btn").show();
            $("span:contains('撤回')").parents("a.l-btn").hide();
        } else if(selectedvalue == '2') {
            $("span:contains('上报省局平台')").parents("a.l-btn").show();
            $("span:contains('全部上报省局平台')").parents("a.l-btn").show();
            $("span:contains('撤回')").parents("a.l-btn").show();
        }else{
            $("span:contains('上报省局平台')").parents("a.l-btn").hide();
            $("span:contains('全部上报省局平台')").parents("a.l-btn").hide();
            $("span:contains('撤回')").parents("a.l-btn").show();
        }
    });
    $("span:contains('撤回')").parents("a.l-btn").hide();
});


//导出
function ExportXls() {
    var rows = $("#tBHiddenDangerHandleList").datagrid('getSelections');
    if (rows.length == 0) {
        JeecgExcelExport("tBHiddenDangerHandleController.do?reportListExportXls", "tBHiddenDangerHandleList");
    } else if (rows.length >=1) {
        var idsTemp = new Array();
        for (var i = 0; i < rows.length; i++) {
            idsTemp.push(rows[i].id);
        }
        var idt = idsTemp.join(",");
        $.dialog.confirm("是否确认导出"+idsTemp.length+"条记录？", function () {
            JeecgExcelExport("tBHiddenDangerHandleController.do?queryListExportXls&ids="+idt, "tBHiddenDangerHandleList");
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
function loadSuccess(){
    replaceProblemDesc();
    $("td[field='hiddenDanger.limitDate']:gt(0)").each(function(){
        var limitDate = $(this).children().text();
        if(limitDate  < '${dateNow}'){
            var status = $(this).parent().children("td[field='handlelStatus']").children().text();
            if(status == '未整改'){
                $(this).parent().css("background-color","#FFD2D2");
            }
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