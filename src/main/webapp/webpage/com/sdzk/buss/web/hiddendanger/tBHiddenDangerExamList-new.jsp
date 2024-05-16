<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.jeecgframework.core.util.DicUtil" %>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<%
    String examType = (String)request.getAttribute("examType");
    String examTypeName = DicUtil.getTypeNameByCode("examType",examType);
%>

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
  <t:datagrid name="tBHiddenDangerExamList" onDblClick="dblClickDetail" checkbox="true" fitColumns="false" title="隐患检查" actionUrl="tBHiddenDangerExamController.do?datagrid&examType=${examType}&taskAllId=${taskAllId}" idField="id" fit="true" queryMode="group" sortName="examDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group" sortable="false" width="120"></t:dgCol>
      <c:if test="${huayuan eq 'true'}">
          <t:dgCol title="隐患编号"  field="hiddenNumber"  sortable="false" width="80" align="center"></t:dgCol>
      </c:if>
   <t:dgCol title="日期"  field="examDate"  formatter="yyyy-MM-dd" query="true"  queryMode="group" sortable="false" width="73" align="center"></t:dgCol>
      <t:dgCol title="班次" field="shift"  dictionary="workShift" align="center" width="37" query="true"></t:dgCol>
      <t:dgCol title="信息来源"  field="manageType" dictionary="manageType"  queryMode="single"  sortable="false" width="90" align="center"></t:dgCol>
   <t:dgCol title="地点"  field="address.address" queryMode="group" sortable="false" width="100" align="center"></t:dgCol>
      <t:dgCol title="隐患类型"  field="riskType" dictionary="risk_type"  queryMode="single"  sortable="false" width="90" align="center"></t:dgCol>
      <t:dgCol title="风险描述"  field="riskId.riskDesc" queryMode="group" formatterjs="valueTitle" sortable="false" width="150" align="center"></t:dgCol>
      <c:if test="${xiezhuang eq 'true'}">
          <t:dgCol title="危害因素"  field="hazardFactorName" queryMode="group" formatterjs="valueTitle" sortable="false" width="150" align="center"></t:dgCol>
      </c:if>
      <%--<t:dgCol title="危害因素"  field="hazardFactorName" queryMode="group" formatterjs="valueTitle" sortable="false" width="150" align="center"></t:dgCol>--%>
      <t:dgCol title="检查人"  field="fillCardManNames"    queryMode="group" sortable="false" width="67" align="center"></t:dgCol>
   <t:dgCol title="责任单位"  field="dutyUnit.departname"    queryMode="group" sortable="false" width="76" align="center"></t:dgCol>
   <t:dgCol title="责任人"  field="dutyMan"    queryMode="group" sortable="false" width="70" align="center"></t:dgCol>
      <c:if test="${newPost ne 'true'}">
          <t:dgCol title="岗位"  field="post.postName" queryMode="group" sortable="false" width="100" align="center"></t:dgCol>
      </c:if>
   <t:dgCol title="问题描述"  field="problemDesc"   formatterjs="valueTitle" queryMode="group" sortable="false" width="400"></t:dgCol>
   <t:dgCol title="隐患类别"  field="hiddenCategory"  hidden="true"  queryMode="group" sortable="false" width="78" align="center"></t:dgCol>
   <t:dgCol title="隐患等级"  field="hiddenNature" hidden="false" dictionary="hiddenLevel"  queryMode="group" sortable="false" width="78" align="center"></t:dgCol>
   <t:dgCol title="处理类型"  field="dealType" replace="限期整改_1,现场处理_2"  queryMode="group" sortable="false" width="68" align="center"></t:dgCol>
   <t:dgCol title="处理状态"  field="handleEntity.handlelStatus" dictionary="handelStatus"  queryMode="group" sortable="false" width="68" align="center"></t:dgCol>
   <t:dgCol title="限期日期"  field="limitDate" formatter="yyyy-MM-dd"   queryMode="group" sortable="false" width="80" align="center"></t:dgCol>
      <c:if test="${beixulou eq 'true'}">
          <t:dgCol title="限期班次"  field="limitShift"  dictionary="workShift" queryMode="single" sortable="false" width="120" align="center"></t:dgCol>
      </c:if>
      <c:if test="${beixulou ne 'true'}">
          <t:dgCol title="限期班次"  field="limitShift" hidden="true" dictionary="workShift" queryMode="single" sortable="false" width="120" align="center"></t:dgCol>
      </c:if>
   <t:dgCol title="复查人"  field="reviewMan"  hidden="true"   dictionary="t_s_base_user,id,realname,where 1=1"  queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="检查类型"  field="examType"  hidden="true"   queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="驳回备注"  field="rollBackRemarkTemp"  hidden="false" query="false" queryMode="group"  sortable="false" width="80"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
   <c:if test="${detail ne 'true'}">
       <t:dgToolBar  title="录入" icon="icon-add" url="tBHiddenDangerExamController.do?goAddNew&examType=${examType}&taskAllId=${taskAllId}" funname="goAdd"  width="950" height="400" ></t:dgToolBar>
   </c:if>
       <t:dgToolBar title="查看" icon="icon-search" url="tBHiddenDangerExamController.do?goUpdate&examType=${examType}" funname="detail" width="950" height="600" ></t:dgToolBar>
      <c:if test="${detail ne 'true'}">
       <t:dgToolBar title="编辑" icon="icon-edit" url="tBHiddenDangerExamController.do?goUpdate&examType=${examType}" funname="gotoUpdate" width="950" height="400" ></t:dgToolBar>
       <c:if test="${xiezhuang eq 'true'}">
           <t:dgToolBar title="编辑风险与危害因素" icon="icon-edit"  funname="gotoUpdateXiezhuang" width="950" height="400" ></t:dgToolBar>
       </c:if>
       <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBHiddenDangerExamController.do?doBatchDel" funname="deleteALLSelect" ></t:dgToolBar>
       <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" ></t:dgToolBar>
          <c:if test="${yhdr eq 'true'}">
              <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls" ></t:dgToolBar>
              <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT" ></t:dgToolBar>
          </c:if>
       <t:dgToolBar title="撤回" icon="icon-undo" url="tBHiddenDangerExamController.do?toReportCallback"  funname="toReportCallback" ></t:dgToolBar>
       <t:dgToolBar title="上报" icon="icon-putout" url="tBHiddenDangerExamController.do?reportHiddenDanger"  funname="reportHiddenDanger" ></t:dgToolBar>
       <t:dgToolBar  title="录入罚款" icon="icon-add" url="tBHiddenDangerExamController.do?goLinkFine" funname="goAddFine"  width="950" height="400" ></t:dgToolBar>
       <t:dgToolBar  title="查看罚款" icon="icon-add" url="tBHiddenDangerExamController.do?goLinkFine" funname="goLinkFine"  width="950" height="400" ></t:dgToolBar>
      </c:if>

  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">

     function goAddFine(){
         var rowsData = $('#tBHiddenDangerExamList').datagrid('getSelections');
         if(rowsData == null || rowsData.length != 1){
             tip("请选择一条需要关联的隐患");
         }else{
             createwindow('录入罚款','tBFineController.do?goAdd&hiddenId='+rowsData[0].id);
         }
     }
     function goLinkFine(){
         var rowsData = $('#tBHiddenDangerExamList').datagrid('getSelections');
         if(rowsData == null || rowsData.length != 1){
             tip("请选择一条隐患");
         }else{
             openwindow('关联罚款','tBFineController.do?chooseFineList&busId='+rowsData[0].id,"",1000,500);
         }
     }


     function valueTitle(value){
         return "<a title=\""+value+"\">"+value+"</a>";
     }
     window.top["reload_tBHiddenDangerExamList_${examType}"]=function(){
         $("#tBHiddenDangerExamList").datagrid( "load");
     };
     window.top["tip_tBHiddenDangerExamList_${examType}"]=function(){
         tip(this.msg);
     };
     function dblClickDetail(rowIndex,rowData){
         var id=rowData.id;
         createdetailwindow("查看","tBHiddenDangerExamController.do?goUpdate&examType=${examType}&load=detail&id="+id,950,600);
     }

     function goAdd(){
         addOneTab("<%=examTypeName%>-录入","tBHiddenDangerExamController.do?goAddNew&examType=${examType}&taskAllId=${taskAllId}","default");
     }

     function gotoUpdate(){
         //获取选中条目
         var rowsData = $('#tBHiddenDangerExamList').datagrid('getSelections');
         if(rowsData == null || rowsData.length != 1){
             tip("请选择一条需要编辑的隐患");
         }else{
             addOneTab("<%=examTypeName%>-编辑","tBHiddenDangerExamController.do?goUpdate&examType=${examType}&id="+rowsData[0].id,"default");
         }
     }

     function gotoUpdateXiezhuang(){
         //获取选中条目
         var rowsData = $('#tBHiddenDangerExamList').datagrid('getSelections');
         if(rowsData == null || rowsData.length != 1){
             tip("请选择一条需要编辑的隐患");
         }else{
             addOneTab("<%=examTypeName%>-编辑","tBHiddenDangerExamController.do?goUpdate&examType=${examType}&xiezhuangEdit=1&id="+rowsData[0].id,"default");
         }
     }
     function goDetail(){
         //获取选中条目
         var rowsData = $('#tBHiddenDangerExamList').datagrid('getSelections');
         if(rowsData == null || rowsData.length != 1){
             tip("请选择需要查看的条目");
         }else{
             addOneTab("<%=examTypeName%>-查看","tBHiddenDangerExamController.do?goUpdate&examType=${examType}&load=detail&id="+rowsData[0].id,"default");
         }

     }
 $(document).ready(function(){
     $("a[iconcls='icon-reload']").hide();
     var datagrid = $("#tBHiddenDangerExamListtb");
     datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");
     $("span:contains('录入')").parents("a.l-btn").show();
     $("span:contains('上报')").parents("a.l-btn").show();
     $("span:contains('撤回')").parents("a.l-btn").hide();
     $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
     $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","");
     $("span:contains('编辑风险与危害因素')").parents("a.l-btn").hide();
     $("input[name='queryHandleStatusTem']").change(function() {
         var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();

         $("input[name='queryHandleStatus']").val(selectedvalue);
         if(selectedvalue=="00"){
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","");
             $("span:contains('录入')").parents("a.l-btn").show();
             $("span:contains('上报')").parents("a.l-btn").show();
             $("span:contains('撤回')").parents("a.l-btn").hide();
             $("span:contains('模板下载')").parents("a.l-btn").show();
             $("span:contains('导入')").parents("a.l-btn").show();
             $("span:contains('编辑风险与危害因素')").parents("a.l-btn").hide();
         }else if(selectedvalue=="ALL"){//未整改显示，其他隐藏
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","none");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","none");
             $("span:contains('录入')").parents("a.l-btn").hide();
             $("span:contains('上报')").parents("a.l-btn").hide();
             $("span:contains('撤回')").parents("a.l-btn").show();
             $("span:contains('模板下载')").parents("a.l-btn").hide();
             $("span:contains('导入')").parents("a.l-btn").hide();
             $("span:contains('编辑风险与危害因素')").parents("a.l-btn").show();
         }else{
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","");
             $("span:contains('录入')").parents("a.l-btn").hide();
             $("span:contains('上报')").parents("a.l-btn").show();
             $("span:contains('撤回')").parents("a.l-btn").hide();
             $("span:contains('模板下载')").parents("a.l-btn").hide();
             $("span:contains('导入')").parents("a.l-btn").hide();
             $("span:contains('编辑风险与危害因素')").parents("a.l-btn").hide();
         }

         tBHiddenDangerExamListsearch();
     });

 		//给时间控件加上样式
 			$("#tBHiddenDangerExamListtb").find("input[name='beginWellDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='beginWellDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='endWellDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='endWellDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='limitDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='limitDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='examDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='examDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBHiddenDangerExamController.do?upload&examType=${examType}&taskAllId=${taskAllId}', "tBHiddenDangerExamList");
}

//导出
function ExportXls() {
    var rows=$("#tBHiddenDangerExamList").datagrid('getSelections');
    if(rows==0){
	JeecgExcelExport("tBHiddenDangerExamController.do?exportXls&examType=${examType}&taskAllId=${taskAllId}","tBHiddenDangerExamList");
    }else if(rows.length>=1){
        var idsTemp=new Array();
        for(var i=0;i<rows.length;i++){
            idsTemp.push(rows[i].id);
         }
        var idt = idsTemp.join(",");
        $.dialog.confirm("是否确认导出"+idsTemp.length+"条记录？", function () {
            JeecgExcelExport("tBHiddenDangerExamController.do?exportXls&examType=${examType}&taskAllId=${taskAllId}&ids="+idt,"tBHiddenDangerExamList");
        });
    }
}
//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBHiddenDangerExamController.do?exportXlsByT&examType=${examType}","tBHiddenDangerExamList");
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
      * 上报
      */
     function reportHiddenDanger(title,url,gname) {
         gridname = gname;
         var ids = [];
         var rows = $("#" + gname).datagrid('getSelections');
         if (rows.length > 0) {
             if("false"!="${bRequiredDangerSouce}"){
                 for (var i=0;i<rows.length;i++) {
                     var hazardName=rows[i]["dangerId.hazard.hazardName"];
                     if(hazardName==null||hazardName==''){
                         tip("存在未关联危险源的隐患,请先执行关联操作或勾除未关联的隐患。");
                         return;
                     }
                 }
             }
                 for(var i=0;i<rows.length;i++){
                     if(rows[i]["dealType"]=="2"){
                         var reviewMan = rows[i]["reviewMan"];
                         if(reviewMan == null||reviewMan ==""){
                             tip("存在复查人为空的隐患草稿，请先选择复查人。");
                             return;
                         }
                     }
                 }
             $.dialog.setting.zIndex = getzIndex(true);
             $.dialog.confirm('你确定上报该数据吗?', function (r) {
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
             tip("请选择需要上报的数据");
         }
     }
 </script>