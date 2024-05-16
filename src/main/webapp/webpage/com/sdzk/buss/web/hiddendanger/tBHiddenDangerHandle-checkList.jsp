<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<style type="text/css">

</style>
<div id="tempSearchColums" style="display: none">
    <div name="addressSearchColums" >
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="问题地点">问题地点：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="addressSelect" style="width: 130px;height: 15px"></div>
                 <input id="tBAddressInfoEntity_id" type="hidden" name="tBAddressInfoEntity_id">
             </span>
        </span>
    </div>
    <div name="searchColums" >
    <br>
    <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px;text-align: center;width: 100%">
        <input  name="queryHandleStatus" value="1" type="hidden">
           <label>
               <input name="queryHandleStatusTem" type="radio" value="all" >全部
           </label>
           <label>
				<input name="queryHandleStatusTem" type="radio" value="3">待复查
           </label>
           <label>
				<input name="queryHandleStatusTem" type="radio" value="1" checked>未整改
           </label>
        </span>
</div>
</div>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBHiddenDangerHandleList" checkbox="true" onLoadSuccess="replaceProblemDesc" fitColumns="false" title="隐患整改" actionUrl="tBHiddenDangerHandleController.do?checkDatagrid" idField="id" fit="true" queryMode="group">
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <c:if test="${huayuan eq 'true'}">
          <t:dgCol title="隐患编号"  field="hiddenDanger.hiddenNumber" query="true" sortable="false" width="80" align="center"></t:dgCol>
      </c:if>
      <t:dgCol title="隐患检查主键"  field="hiddenDanger.id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="整改日期"  field="modifyDate" formatter="yyyy-MM-dd" query="true" queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="检查班次"  field="hiddenDanger.shift" hidden="false" dictionary="workShift" query="true"  queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="信息来源"  field="hiddenDanger.manageType" dictionary="manageType"  queryMode="single"  sortable="false" width="90" align="center"></t:dgCol>
      <t:dgCol title="问题地点"  field="hiddenDanger.address.address"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="检查人"  field="hiddenDanger.fillCardManNames" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="问题描述"  field="hiddenDanger.problemDesc"  formatterjs="valueTitle"  queryMode="group"  sortable="false" width="120"></t:dgCol>
      <t:dgCol title="隐患类型"  field="hiddenDanger.riskType" dictionary="risk_type"  queryMode="single"  sortable="false" width="90" align="center"></t:dgCol>
      <t:dgCol title="隐患等级"  field="hiddenDanger.hiddenNature"   dictionary="hiddenLevel"  queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="限期日期"  field="hiddenDanger.limitDate"  formatter="yyyy-MM-dd"   queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="责任部门"  field="hiddenDanger.dutyUnit.departname" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="责任人"  field="hiddenDanger.dutyMan"   queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="发现时间"  field="hiddenDanger.examDate"  formatter="yyyy-MM-dd"  query="true" queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <%--<t:dgCol title="危险源"  field="hiddenDanger.dangerId.hazard.hazardName"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>--%>
      <t:dgCol title="整改人"  field="modifyMan" dictionary="t_s_base_user,id,realname,where 1=1"  query="true" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
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
      <t:dgCol title="延期结果"  field="tempString" formatterjs="titleApply"  dictionary="applyDealStatus"  query="true" queryMode="single"  sortable="false" width="80"></t:dgCol>
      <t:dgCol title="驳回备注"  field="rollBackRemark" formatterjs="valueTitle" hidden="false" query="false" queryMode="group"  sortable="false" width="300"></t:dgCol>
      <t:dgCol title="驳回原因"  field="hiddenDanger.applyEntity.refuseReason"  hidden="true" query="false" queryMode="group"  sortable="false" width="300"></t:dgCol>
      <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>

      <%--<t:dgToolBar  url="tBHiddenDangerHandleController.do?goModifyAllIssues" title="批量整改" icon="icon-putout" funname="editAllSelect" operationCode="updateAll"></t:dgToolBar>--%>
      <t:dgToolBar operationCode="update" title="整改" icon="icon-edit"  funname="editAllSelect"></t:dgToolBar>
      <t:dgToolBar operationCode="applyDelay" title="申请延期" icon="icon-edit"  funname="applyDelay"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>
      <%--<t:dgToolBar title="编辑" icon="icon-edit" url="tBHiddenDangerHandleController.do?goUpdate&load=update" funname="update" operationCode="update"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="查看" icon="icon-search" url="riskManageResultController.do?updateHd" width="900" height="600" funname="detail" operationCode="detail"></t:dgToolBar>--%>
      <t:dgToolBar title="查看" icon="icon-search" url="tBHiddenDangerHandleController.do?goUpdate&load=detail" funname="detail" width="950" height="550" operationCode="detail"></t:dgToolBar>
      <t:dgToolBar title="撤回" icon="icon-undo" url="tBHiddenDangerHandleController.do?toReportCallback"  funname="toReportCallback" operationCode="undoReport"></t:dgToolBar>
      <c:if test="${isSunAdmin == 'YGADMIN'}">
          <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
      </c:if>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">

     function titleApply(val,row){
         if(val=="2"){
             return "<a title=\"新的限期日期："+row['hiddenDanger.limitDate']+"\">已延期</a>";
         }else if(val=="00"){
             return "<a title=\"驳回原因:："+row['hiddenDanger.applyEntity.refuseReason']+"\">已被驳回</a>";
         }else if(val=="1"){
             return "<a title=\"申请审核中\">申请审核中</a>";
         }else if(val=="0"){
             return  "<a title=\"未申请\">未申请</a>";
         }
     }


     function applyDelay(){
         var hiddenDangerSelected = "";
         var rows = $("#tBHiddenDangerHandleList").datagrid("getSelections");
         if(rows == null || rows.length < 1){
             tip("请选择要延期整改的隐患！！！");
             return;
         }else{
             for (var i = 0; i < rows.length; i++) {
                 if(i==0){
                     hiddenDangerSelected += rows[i].id;
                 }else{
                     hiddenDangerSelected +=","+ rows[i].id;
                 }
             }
             $.ajax({
                 type:'POST',
                 url:'tBHiddenDangerHandleController.do?checkHandleStatusSum',
                 dataType:"json",
                 async:false,
                 data:{
                     ids:hiddenDangerSelected
                 },
                 success:function(data){
                     if(Boolean(data)){
                         $.dialog.confirm("已勾选"+rows.length+"条记录,是否确认延期？", function () {
                             createwindow("申请延期", "tBHiddenDangerHandleController.do?goApplyDelayAllIssues&ids=" + hiddenDangerSelected, 400, 400);
                         });
                     }else{
                         tip("此隐患已整改，请刷新列表后重新选择！");
                     }
                 },
                 error:function(data){
                     tip(data);
                 }
             })
         }
     }

     function dealApply() {
         var rows = $("#tBHiddenDangerHandleList").datagrid('getSelections');
         if (rows.length == 0) {
             tip("请选择一条隐患。");
         } else if (rows.length > 1) {
             tip("请选择<a style='color: red;font-size:100% '>一条</a>隐患");
         }else{
             openwindow("延期申请","tBHiddenDangerHandleController.do?goDealApply&id="+rows[0].id,"tBHiddenDangerHandleList",500,600);
         }
     }

    function doBDel(){
        var hiddenDangerHandleSelected="";
        //取得选中条目
        var rows = $("#tBHiddenDangerHandleList").datagrid('getSelections');
        if(rows == null || rows.length <1){
            tip("请选择要删除的未整改隐患!!!");
            return;
        }else{
            $.ajax({
                type: 'POST',
                url: 'tBHiddenDangerHandleController.do?checkHandelStatus',
                dataType:"json",
                async:false,
                data: {
                    id:rows[0].id
                },
                success:function(data){
                    var result = jQuery.parseJSON(data);
                    if("1" == result || "4" == result){
                        for(var i=0;i<rows.length;i++){
                            hiddenDangerHandleSelected += rows[i].id+",";
                        }
                        $.ajax({
                            url:"tBHiddenDangerHandleController.do?doBatchDel",
                            type:"post",
                            data:{'ids':hiddenDangerHandleSelected},
                            dataType:"json",
                            success:function(data){
                                tip(data.msg);
                                if(data.success){
                                    reloadTable();
                                }
                            },
                            error:function(){
                            }
                        });
                    }else{
                        tip("此隐患已整改，请刷新列表后重新选择");
                    }
                },
                error:function(data){
                }
            });
        }
    }
//    function editRow(){
//        var rows=$("#tBHiddenDangerHandleList").datagrid('getSelections');
//        if(rows.length==0){
//            tip("请选择待整改问题");
//            return false;
//        }
//        $.ajax({
//            type: 'POST',
//            url: 'tBHiddenDangerHandleController.do?checkHandelStatus',
//            dataType:"json",
//            async:false,
//            data: {
//                id:rows[0].id
//            },
//            success:function(data){
//                var result = jQuery.parseJSON(data);
//                if("1" == result || "4" == result){
//                    openwindow("编辑","tBHiddenDangerHandleController.do?goModifyIssues&id="+rows[0].id,"tBHiddenDangerHandleList",600,400);
//                    console.log();
//                }else{
//                    tip("此隐患已整改，请刷新列表后重新选择");
//                }
//            },
//            error:function(data){
//            }
//        });
//    }

    function editAllSelect() {
        var rows = $("#tBHiddenDangerHandleList").datagrid('getSelections');
        if (rows.length == 0) {
            tip("请选择待整改问题");
        } else if (rows.length > 1){
            var idsTemp = new Array();
            for (var i = 0; i < rows.length; i++) {
                idsTemp.push(rows[i].id);
            }
            var idt = idsTemp.join(",");
            $.dialog.confirm("已勾选"+idsTemp.length+"条记录,是否确认批量整改？", function () {
                openwindow("整改", "tBHiddenDangerHandleController.do?goModifyAllIssues&ids=" + idt, "tBHiddenDangerHandleList", 900, 600);
            });
    }else{
            $.ajax({
                type: 'POST',
                url: 'tBHiddenDangerHandleController.do?checkHandelStatus',
                dataType:"json",
                async:false,
                data: {
                    id:rows[0].id
                },
                success:function(data){
                    var result = jQuery.parseJSON(data);
                    if("1" == result || "4" == result){
                        openwindow("整改","tBHiddenDangerHandleController.do?goModifyIssues&id="+rows[0].id,"tBHiddenDangerHandleList",900,600);
                    }else{
                        tip("此隐患已整改，请刷新列表后重新选择");
                    }
                },
                error:function(data){
                }
            });
        }
    }


     $(function() {
         //初始时候默认页面为未整改，隐藏整改日期列
         $("#tBHiddenDangerHandleList").datagrid('hideColumn', "modifyDate");

         $("#tBHiddenDangerHandleListForm").append($("#tempSearchColums div[name='addressSearchColums']").html());
         $("a[iconcls='icon-reload']").hide();
         var datagrid = $("#tBHiddenDangerHandleListtb");
         datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html());
         $("span:contains('撤回')").parents("a.l-btn").hide();
         $("#tempSearchColums").empty();

         getAddressMagicSuggest($("#addressSelect"), $("[name='tBAddressInfoEntity_id']"));

         $("input[name='queryHandleStatusTem']").change(function() {
             var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
             $("input[name='queryHandleStatus']").val(selectedvalue);
             tBHiddenDangerHandleListsearch();
             if(selectedvalue == '1'){
                 $("span:contains('整改')").parents("a.l-btn").show();
                 $("span:contains('撤回')").parents("a.l-btn").hide();

                //未整改时，隐藏整改日期列
                 $("#tBHiddenDangerHandleList").datagrid('hideColumn', "modifyDate");
             } else if(selectedvalue == '3'){
                 $("span:contains('撤回')").parents("a.l-btn").show();
                 $("span:contains('整改')").parents("a.l-btn").hide();

                 //待复查时显示整改日期列
                 $("#tBHiddenDangerHandleList").datagrid('showColumn', "modifyDate");
             }else{
                 $("span:contains('撤回')").parents("a.l-btn").hide();
                 $("span:contains('整改')").parents("a.l-btn").hide();

                 //全部时显示整改日期列
                 $("#tBHiddenDangerHandleList").datagrid('showColumn', "modifyDate");
             }
         });
     });

    //导出
    function ExportXls() {
        var rows = $("#tBHiddenDangerHandleList").datagrid('getSelections');
        if (rows.length == 0) {
            JeecgExcelExport("tBHiddenDangerHandleController.do?exportExcel", "tBHiddenDangerHandleList");
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
     * 撤回
     */
    function toReportCallback(title,url,gname) {
        gridname = gname;
        var ids = [];
        var rows = $("#" + gname).datagrid('getSelections');
        if (rows.length > 0) {
            for (var j = 0; j < rows.length; j++) {
                if (rows[j].handlelStatus != '3') {
                    tip("只能选择未复查的隐患!");
                    return;
                }
            }
            $.dialog.setting.zIndex = getzIndex(true);
            $.dialog.confirm('你确定撤回该数据吗?', function (r) {
                if (r) {
                    for (var i = 0; i < rows.length; i++) {
                        ids.push(rows[i].id);
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

//     function detail(title,url, id,width,height) {
//         var rowsData = $('#'+id).datagrid('getSelections');
//
//         if (!rowsData || rowsData.length == 0) {
//             tip('请选择查看项目');
//             return;
//         }
//         if (rowsData.length > 1) {
//             tip('请选择一条记录再查看');
//             return;
//         }
//         url += '&load=detail&id='+rowsData[0]["hiddenDanger.id"];
//         createdetailwindow(title,url,width,height);
//     }
 </script>