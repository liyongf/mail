    <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchCls" style="display: none">
    <div name="searchCls" >
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">整改人：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis; ">
                <div name="modifyManSelect" style="width: 130px;height: 15px"></div>
                <input class="inuptxt" type="hidden" style="width: 100px" name="modifyMan">
             </span>
        </span>
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">检查人：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis; ">
                <div name="magicsuggestCheck" style="width: 130px;height: 15px" id="magicsuggestCheck"></div>
                <input class="inuptxt" type="hidden" style="width: 100px" name="hiddenDanger.fillCardMan.id" id="fillCardManId">
             </span>
        </span>
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">责任部门：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="orgSelect" style="width: 130px;height: 15px"></div>
                <input class="inuptxt" type="hidden" style="width: 100px" name="hiddenDanger.dutyUnit.departname">
             </span>
        </span>
        <span style="display:-moz-inline-box;display:inline-block;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; " title="问题地点">问题地点：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="addressSelect" style="width: 130px;height: 15px"></div>
                 <input id="tBAddressInfoEntity_id" type="hidden" name="tBAddressInfoEntity_id">
             </span>
        </span>
    </div>
</div>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <br>
       <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px;text-align: center;width: 100%">
        <input  name="queryHandleStatus" value="3" type="hidden">
           <%--<label>--%>
            <%--<input name="queryHandleStatusTem" type="radio" value="0" >--%>
									<%--全部--%>
           <%--</label>--%>

           <label>
               <input name="queryHandleStatusTem" type="radio" value="5">
               已复查
           </label>
           <label>
               <input name="queryHandleStatusTem" type="radio" value="3" checked>
               未复查
           </label>
        </span>
    </div>
</div>
    <input id="beixulou" type="hidden" name="beixulou" value="${beixulou}">
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBHiddenDangerHandleList" checkbox="true" onLoadSuccess="replaceProblemDesc" fitColumns="false" title="问题复查" actionUrl="tBHiddenDangerHandleController.do?repartDatagrid" idField="id" fit="true" queryMode="group" sortOrder="desc" sortName="reviewDate">
      <t:dgCol title="隐患检查主键"  field="hiddenDanger.id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <c:if test="${huayuan eq 'true'}">
          <t:dgCol title="隐患编号"  field="hiddenDanger.hiddenNumber" query="true" sortable="false" width="80" align="center"></t:dgCol>
      </c:if>
      <t:dgCol title="复查人"  field="reviewMan" dictionary="t_s_base_user,id,realname,where 1=1"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <c:if test="${beixulou eq 'true'}">
          <t:dgCol title="其他复查人"  field="reviewManOther" formatterjs="valueTitle"  width="100" align="center"></t:dgCol>
      </c:if>
      <t:dgCol title="复查日期"  field="reviewDate" formatter="yyyy-MM-dd" queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="复查结果"  field="reviewResult"  dictionary="repartStatus" queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="整改人"  field="modifyMan"  query="false" dictionary="t_s_base_user,id,realname,where 1=1"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="整改日期"  field="modifyDate" formatter="yyyy-MM-dd" query="true" queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="整改班次"  field="modifyShift" hidden="false"  dictionary="workShift" query="true"  queryMode="single"  sortable="true" width="120" align="center"></t:dgCol>
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="责任部门"  field="hiddenDanger.dutyUnit.departname" queryMode="single" query="false"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="责任人"  field="hiddenDanger.dutyMan"  queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
      <c:if test="${longyun eq 'true'}">
          <t:dgCol title="管控单位" field="hiddenDanger.manageDutyUnit.departname" width="100" align="center"></t:dgCol>
          <t:dgCol title="管控责任人" field="hiddenDanger.manageDutyManId" dictionary="t_s_base_user,id,realname,where 1=1" width="100" align="center"></t:dgCol>
      </c:if>
      <t:dgCol title="发现时间"  field="hiddenDanger.examDate"  formatter="yyyy-MM-dd"  query="true" queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="问题地点"  field="hiddenDanger.address.address"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="检查班次"  field="hiddenDanger.shift" hidden="false"  dictionary="workShift" query="true"  queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="检查人"  field="hiddenDanger.fillCardManNames" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="复查人"  field="hiddenDanger.reviewMan.realName"  hidden="true"   dictionary="t_s_base_user,id,realname,where 1=1"  queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
      <%--<t:dgCol title="检查类型"  field="hiddenDanger.examType" query="true"  dictionary="examType" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>--%>
      <t:dgCol title="信息来源"  field="hiddenDanger.manageType" query="true"  dictionary="manageType" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
      <%--<t:dgCol title="危险源"  field="hiddenDanger.dangerId.hazard.hazardName"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>--%>
      <t:dgCol title="隐患等级"  field="hiddenDanger.hiddenNature"   dictionary="hiddenLevel"  queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>

      <t:dgCol title="问题描述"  field="hiddenDanger.problemDesc"  formatterjs="valueTitle"  queryMode="group"  sortable="false" width="120"></t:dgCol>
      <t:dgCol title="隐患类型"  field="hiddenDanger.riskType" dictionary="risk_type"  queryMode="single"  sortable="false" width="90" align="center"></t:dgCol>
   <t:dgCol title="处理状态"  field="handlelStatus" dictionary="handelStatus"   queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>

      <%--<t:dgToolBar title="查看" icon="icon-search" url="riskManageResultController.do?updateHd" funname="detail" operationCode="detail"></t:dgToolBar>--%>
      <t:dgToolBar title="查看" icon="icon-search" url="tBHiddenDangerHandleController.do?goUpdate&load=detail" funname="detail" width="950" height="550" operationCode="detail"></t:dgToolBar>
      <t:dgToolBar operationCode="update" title="复查" icon="icon-edit"  funname="editRow"></t:dgToolBar>
      <t:dgToolBar operationCode="updateAll" title="批量复查" icon="icon-edit"  funname="editAllRow"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>
      <%--<t:dgToolBar title="导出复查单" icon="icon-putout" funname="ExportReXls" operationCode="export"></t:dgToolBar>--%>
      <c:if test="${isSunAdmin == 'YGADMIN'}">
          <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
      </c:if>
  </t:datagrid>
  </div>
 </div>
    <script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
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
     function editRow(title,addurl,gname){
         var rows=$('#'+gname).datagrid("getSelections");
         if(rows.length==0){
             tip("请选择待复查问题");
             return false;
         }
         if(rows.length>1){
             tip("请选择一条待复查问题");
             return false;
         }
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
                 if("3" == result){
                     openwindow("复查","tBHiddenDangerHandleController.do?goReviewIssues&id="+rows[0].id,"tBHiddenDangerHandleList",600,450);
                 }else{
                     tip("此隐患已复查，请刷新列表后重新选择");
                 }
             },
             error:function(data){
             }
         });
     }

     function editAllRow() {
         var rows = $("#tBHiddenDangerHandleList").datagrid('getSelections');
         if (rows.length == 0) {
             tip("请选择待复查问题");
         } else if (rows.length > 1){
             var idsTemp = new Array();
             for (var i = 0; i < rows.length; i++) {
                 idsTemp.push(rows[i].id);
             }
             var idt = idsTemp.join(",");
             $.dialog.confirm("已勾选"+idsTemp.length+"条记录,是否确认批量复查？", function () {
                 openwindow("复查", "tBHiddenDangerHandleController.do?goReviewAllIssues&ids=" + idt, "tBHiddenDangerHandleList", 600, 450);
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
                     if("3" == result){
                         openwindow("复查","tBHiddenDangerHandleController.do?goReviewIssues&id="+rows[0].id,"tBHiddenDangerHandleList",600,450);
                     }else{
                         tip("此隐患已复查，请刷新列表后重新选择");
                     }
                 },
                 error:function(data){
                 }
             });
         }
     }


     var magicsuggestCheckManSelected = "";
     $(function() {
         var beixulou = $("#beixulou").val();
         //初始时候默认页面为未复查，隐藏已复查列
         $("#tBHiddenDangerHandleList").datagrid('hideColumn', "reviewMan");
         $("#tBHiddenDangerHandleList").datagrid('hideColumn', "reviewDate");
         $("#tBHiddenDangerHandleList").datagrid('hideColumn', "reviewResult");
            if(beixulou=="true"){
                $("#tBHiddenDangerHandleList").datagrid('hideColumn', "reviewManOther");
            }
         $("a[iconcls='icon-reload']").hide();
         var datagrid = $("#tBHiddenDangerHandleListtb");
             datagrid.find("form[id='tBHiddenDangerHandleListForm']>span:last").after($("#tempSearchCls div[name='searchCls']").html());
             datagrid.find("form[id='tBHiddenDangerHandleListForm']").append($("#tempSearchColums div[name='searchColums']").html());
             $("#tempSearchColums").empty();
             $("#tempSearchCls").empty();
             $("input[name='queryHandleStatusTem']").change(function() {
                 var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();

                 $("input[name='queryHandleStatus']").val(selectedvalue);
                 if(selectedvalue=="3"){//未复查显示，其他隐藏
                     $("div[class='datagrid-toolbar']>span:first").find("a").each(function(){
                         $("span:contains('复查')").parents("a.l-btn").show();

                         //隐藏已复查列
                         $("#tBHiddenDangerHandleList").datagrid('hideColumn', "reviewMan");
                         $("#tBHiddenDangerHandleList").datagrid('hideColumn', "reviewDate");
                         $("#tBHiddenDangerHandleList").datagrid('hideColumn', "reviewResult");
                         if(beixulou=="true"){
                             $("#tBHiddenDangerHandleList").datagrid('hideColumn', "reviewManOther");
                         }
                     });
                 }else{
                     $("div[class='datagrid-toolbar']>span:first").find("a").each(function(){
                         $("span:contains('复查')").parents("a.l-btn").hide();

                        //显示已复查列
                         $("#tBHiddenDangerHandleList").datagrid('showColumn', "reviewMan");
                         $("#tBHiddenDangerHandleList").datagrid('showColumn', "reviewDate");
                         $("#tBHiddenDangerHandleList").datagrid('showColumn', "reviewResult");
                         if(beixulou=="true"){
                             $("#tBHiddenDangerHandleList").datagrid('showColumn', "reviewManOther");
                         }
                     });
                 }

                 tBHiddenDangerHandleListsearch();
             });
             //getUserMagicSuggest($('div[name="magicsuggestCheck"]'), $("[name='hiddenDanger.fillCardMan.id']"));
         var magicsuggestCheckSelect = "";
         magicsuggestCheckSelect = $('#magicsuggestCheck').magicSuggest({
             allowFreeEntries: true,
             data:'magicSelectController.do?getUserList',
             valueField:'id',
             placeholder:'输入或选择',
             maxSelection:1,
             selectFirst: true,
             matchField:['spelling','realName','userName','fullSpelling'],
             highlight: false,
             displayField:'realName'
         });
         $(magicsuggestCheckSelect).on('selectionchange', function (e, m) {
             $("#fillCardManId").val(magicsuggestCheckSelect.getValue());
         });
             getUserMagicSuggest($('div[name="modifyManSelect"]'), $("[name='modifyMan']"));
             getDepartMagicSuggest($("#orgSelect"), $("[name='hiddenDanger.dutyUnit.departname']"));
             getAddressMagicSuggest($("#addressSelect"), $("[name='tBAddressInfoEntity_id']"));

     });

    //导出
    function ExportXls() {
        JeecgExcelExport("tBHiddenDangerHandleController.do?repartExportXls","tBHiddenDangerHandleList");
    }
    //导出复查单
    function ExportReXls() {
        JeecgExcelExport("tBHiddenDangerHandleController.do?repartExportReXls","tBHiddenDangerHandleList");
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