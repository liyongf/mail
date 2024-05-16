<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/3/22 0022
  Time: 下午 1:53
  To change this template use File | Settings | File Templates.
--%>
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
        <input  name="queryHandleStatus" value="00" type="hidden">
           <label>
               <input name="queryHandleStatusTem" type="radio" value="00" checked="checked">
               草稿
           </label>
           <label>
               <input name="queryHandleStatusTem" type="radio" value="ALL">
               已上报
           </label>
           <label>
               <input name="queryHandleStatusTem" type="radio" value="ROLLBACK">
               已退回
           </label>
        </span>
    </div>
</div>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tBHiddenDangerExamList" checkbox="true"  onDblClick="dblClickDetail" onLoadSuccess="loadSuccess" autoLoadData="true" fitColumns="false" title="隐患列表"
                    actionUrl="riskManageResultController.do?hazardFactorRelHdDatagrid&riskManageHazardFactor.id=${riskManageHazardFactorId}&riskManageTaskAllId=${riskManageTaskAllId}"
                    idField="id" sortName="hd.examDate" sortOrder="desc" fit="true" queryMode="group">
            <t:dgCol title="隐患检查id"  field="hd.id" hidden="true" query="false" queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="隐患处理id"  field="hd.handleEntity.id" hidden="true" query="false" queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="隐患来源"  field="hd.origin" hidden="true" query="false" dictionary="hiddenDangerOrigin" queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="发现时间"  field="hd.examDate"  formatter="yyyy-MM-dd"  query="true" queryMode="group"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="检查班次"  field="hd.shift" hidden="false" dictionary="workShift" query="false"  queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="问题地点"  field="hd.address.address"   queryMode="single"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
            <t:dgCol title="责任部门"  field="hd.dutyUnit.departname" query="false" queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
            <t:dgCol title="责任人"  field="hd.dutyMan" queryMode="group" sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="检查人"  field="hd.fillCardManNames" queryMode="single"  sortable="false" width="100" align="center"></t:dgCol>
            <t:dgCol title="信息来源"  field="hd.manageType"  query="false" dictionary="manageType" queryMode="single"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="隐患等级"  field="hd.hiddenNature"  query="false" dictionary="hiddenLevel"  queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>
            <c:if test="${newPost ne 'true' and manageType eq 'post'}">
                <t:dgCol title="岗位"  field="hd.post.postName"     queryMode="single"  width="80" align="center"></t:dgCol>
            </c:if>
            <t:dgCol title="问题描述"  field="hd.problemDesc"    queryMode="group"  sortable="false" width="300"></t:dgCol>
            <t:dgCol title="处理方式"  field="hd.dealType" replace="限期整改_1,现场处理_2" width="80" align="center"></t:dgCol>
            <t:dgCol title="限期日期"  field="hd.limitDate" formatter="yyyy-MM-dd" width="80" align="center"></t:dgCol>
            <c:if test="${beixulou eq 'true'}">
                <t:dgCol title="限期班次"  field="hd.limitShift" dictionary="workShift" width="80" align="center"></t:dgCol>
            </c:if>
            <c:if test="${beixulou ne 'true'}">
                <t:dgCol title="限期班次"  field="hd.limitShift" dictionary="workShift" hidden="true" width="80" align="center"></t:dgCol>
            </c:if>
            <t:dgCol title="整改日期"  field="hd.handleEntity.modifyDate" formatter="yyyy-MM-dd" query="false"  queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="整改人"  field="hd.handleEntity.modifyMan"  dictionary="t_s_base_user,id,realname,where 1=1"   queryMode="group"  sortable="true" width="100" align="center"></t:dgCol>
            <t:dgCol title="复查日期"  field="hd.handleEntity.reviewDate" formatter="yyyy-MM-dd"   queryMode="group"  sortable="true" width="80" align="center"></t:dgCol>
            <t:dgCol title="复查人"  field="hd.handleEntity.reviewMan"  dictionary="t_s_base_user,id,realname,where 1=1"  queryMode="group"  sortable="true" width="100" align="center"></t:dgCol>
            <t:dgCol title="处理状态"  field="hd.handleEntity.handlelStatus" dictionary="handelStatus" query="false"  queryMode="single"  sortable="true" width="60" align="center"></t:dgCol>

            <t:dgCol title="驳回备注"  field="hd.handleEntity.rollBackRemark"  hidden="true" query="false" queryMode="group"  sortable="false" width="300"></t:dgCol>
            <t:dgCol title="录入人"  field="hd.handleEntity.createName"   queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="创建人登录名称"  field="hd.handleEntity.createBy"  hidden="true"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="录入日期"  field="hd.handleEntity.createDate" formatter="yyyy-MM-dd"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="更新人名称"  field="hd.handleEntity.updateName"  hidden="true"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="更新人登录名称"  field="hd.handleEntity.updateBy"  hidden="true"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
            <t:dgCol title="更新日期"  field="hd.handleEntity.updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>

            <t:dgToolBar title="查看隐患" icon="icon-search" url="riskManageResultController.do?updateHd" funname="gotodetail" width="950" height="550" ></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="riskManageResultController.do?updateHd" funname="gotoUpdate" width="950" height="550" ></t:dgToolBar>
            <t:dgToolBar title="撤回" icon="icon-undo" url="tBHiddenDangerExamController.do?toReportCallback"  funname="toReportCallback" ></t:dgToolBar>
            <t:dgToolBar title="批量删除"  icon="icon-remove" url="riskManageResultController.do?delHd" funname="deleteALLSelect" ></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    function dblClickDetail(rowIndex,rowData){
        var id=rowData["hd.id"];
        var url = "tBHiddenDangerHandleController.do?goUpdate&load=detail&id="+id;
        //createdetailwindow("查看",url,950,600);
        addOneTab("查看","riskManageResultController.do?updateHd&load=detail&id="+id,"default");
    }

    $(function() {
        $("a[iconcls='icon-reload']").hide();
        var datagrid = $("#tBHiddenDangerExamListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");
        $("span:contains('上报')").parents("a.l-btn").show();
        $("span:contains('撤回')").parents("a.l-btn").hide();
        $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
        $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","");

        $("input[name='queryHandleStatusTem']").change(function() {
            var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();

            $("input[name='queryHandleStatus']").val(selectedvalue);
            if(selectedvalue=="00"){
                $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
                $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","");
                $("span:contains('撤回')").parents("a.l-btn").hide();
            }else if(selectedvalue=="ALL"){//未整改显示，其他隐藏
                $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","none");
                $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","none");
                $("span:contains('撤回')").parents("a.l-btn").show();
            }else{
                $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
                $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","");
                $("span:contains('撤回')").parents("a.l-btn").hide();
            }

            tBHiddenDangerExamListsearch();
        });
    });


    function replaceProblemDesc(){
        var problemDescCells = $("td[field='hd.problemDesc']:gt(0)");
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
    }

    function gotodetail(title,url, id,width,height) {
//        var rowsData = $('#'+id).datagrid('getSelections');
//        if (!rowsData || rowsData.length == 0) {
//            tip('请选择查看项目');
//            return;
//        }
//        if (rowsData.length > 1) {
//            tip('请选择一条记录再查看');
//            return;
//        }
//        url += '&load=detail&id='+rowsData[0]["hd.handleEntity.id"];
//        createdetailwindow(title,url,width,height);


        //获取选中条目
        var rowsData = $('#tBHiddenDangerExamList').datagrid('getSelections');
        if(rowsData == null || rowsData.length != 1){
            tip("请选择一条需要查看的隐患");
        }else{
            addOneTab("查看","riskManageResultController.do?updateHd&load=detail&manageType=${manageType}&id="+rowsData[0]["hd.id"],"default");
        }
    }

    function gotoUpdate(){
        //获取选中条目
        var rowsData = $('#tBHiddenDangerExamList').datagrid('getSelections');
        if(rowsData == null || rowsData.length != 1){
            tip("请选择一条需要编辑的隐患");
        }else{
            addOneTab("编辑","riskManageResultController.do?updateHd&id="+rowsData[0]["hd.id"],"default");
        }
    }

    /**
     * 撤回
     */
    function toReportCallback(title,url,gname) {
        gridname = gname;
        var ids = [];
        var rows = $("#" + gname).datagrid('getSelections');
        if (rows.length > 0) {

            $.dialog.setting.zIndex = getzIndex(true);
            $.dialog.confirm('你确定撤回该数据吗?', function (r) {
                if (r) {
                    for (var i = 0; i < rows.length; i++) {
                        ids.push(rows[i]["hd.id"]);
                    }
                    $.ajax({
                        url: url,
                        type: 'post',
                        data: {
                            ids: ids.join(',')
                        },
                        cache: false,
                        success: function (data) {
                            var d = $.parseJSON(data);
                            if (d.success) {
                                var msg = d.msg;
                                tip(msg);
                                reloadTable();
                                $("#" + gname).datagrid('unselectAll');
                                ids = '';
                            }
                        }
                    });
                }
            });
        } else {
            tip("请选择需要撤回的数据");
        }
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
                        ids.push(rows[i]["hd.id"]);
                    }
                    $.ajax({
                        url : url,
                        type : 'post',
                        data : {
                            ids : ids.join(',')
                        },
                        cache : false,
                        success : function(data) {
                            var d = $.parseJSON(data);
                            if (d.success) {
                                var msg = d.msg;
                                tip(msg);
                                reloadTable();
                                $("#"+gname).datagrid('unselectAll');
                                ids='';
                            }
                        }
                    });
                }
            });
        } else {
            tip("请选择需要删除的数据");
        }
    }

    window.top["reload_hdList_${riskManageHazardFactorId}"]=function(){
        $("#tBHiddenDangerExamList").datagrid( "reload");
        //alert('reload_hazardFactorsList_');
        //debugger;
        //hazardFactorsListsearch();
    };
    window.top["tip_hdList_${riskManageHazardFactorId}"]=function(){
        tip(this.msg);
    };
</script>
