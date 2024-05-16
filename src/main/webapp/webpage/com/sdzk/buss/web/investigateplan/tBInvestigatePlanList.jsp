<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="tempSearchColums" style="display: none">
    <div name="searchColumsCenter" >
          <span style="display:-moz-inline-box;display:inline-block;">
          <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap;  ">
            <input  name="queryHandleStatus" value="false" type="hidden">
            <label>
                <input id="queryHandleStatusTem_false" name="queryHandleStatusTem" type="radio" value="false" checked>
                未提交</label>
            <label style="margin-left: 15px;">
                <input id="queryHandleStatusTem_true" name="queryHandleStatusTem"  type="radio" value="true">
                已提交</label>
        </span>
         </span>
    </div>
</div>
<div class="easyui-layout" fit="true" id="main_list">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBInvestigatePlanList" checkbox="true" pagination="true" fitColumns="true" title="${title}" actionUrl="tBInvestigatePlanController.do?datagrid&investigateType=${investigateType}" idField="id" fit="true" queryMode="group" onLoadSuccess="loadSuccessed">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
      <c:if test="${investigateType eq 1}">
          <t:dgCol title="月份"  field="month" query="true"   queryMode="group"  width="120"></t:dgCol>
      </c:if>
      <c:if test="${investigateType ne 1}">
          <t:dgCol title="计划开始时间"  field="startTime" formatter="yyyy-MM-dd" query="true"   queryMode="group"  width="120"></t:dgCol>
          <t:dgCol title="计划结束时间"  field="endTime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
      </c:if>
      <%--<t:dgCol title="风险点类型" dictionary="investPlan_riskPoint_type" field="riskPointType" query="false"  queryMode="single"  sortable="false"  width="80"></t:dgCol>--%>
      <t:dgCol title="风险点" field="riskPointName" width="180" query="false"></t:dgCol>
      <%--<t:dgCol title="危险源" field="riskName" width="260" query="false"></t:dgCol>--%>
      <t:dgCol title="受理单位"  field="acceptDepart"  dictionary="t_s_depart,id,departname,where 1=1"  queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="受理时间"  field="acceptTime" formatter="yyyy-MM-dd hh:mm"   queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="排查人"  field="acceptUser" dictionary="t_s_base_user,id,realname,where 1=1"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="排查描述"  field="investigateDesc"  formatterjs="valueTitle"  queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="排查时间"  field="investigateTime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="派发人" field="createName" width="80"></t:dgCol>
      <t:dgCol title="派发时间" field="createDate" width="80" formatter="yyyy-MM-dd"></t:dgCol>
      <t:dgCol title="要求完成时间" hidden="true" field="completeTime" formatter="yyyy-MM-dd"   queryMode="group"  width="100"></t:dgCol>
      <t:dgCol title="状态"  field="status" dictionary="investigatePlan_status"  query="false"  queryMode="single"  sortable="false"  width="120"></t:dgCol>
      <%--<t:dgCol title="是否上报集团"  field="reportGroupStatus" dictionary="isReportToGroup"  query="false"  queryMode="single"  sortable="false"  width="120"></t:dgCol>--%>
      <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
      <t:dgFunOpt title="关联隐患" funname="showHiddenDanger(id)" urlclass="ace_button"  urlfont="fa-search"></t:dgFunOpt>

   <t:dgToolBar title="录入" operationCode="add" icon="icon-add" url="tBInvestigatePlanController.do?goAdd&investigateType=${investigateType}" width="800" height="400" funname="goAdd"></t:dgToolBar>
   <t:dgToolBar title="编辑" operationCode="update" icon="icon-edit" url="tBInvestigatePlanController.do?goUpdate&investigateType=${investigateType}" width="800" height="400" funname="goUpdate"></t:dgToolBar>
   <t:dgToolBar title="派发" operationCode="assign" icon="icon-edit" url="tBInvestigatePlanController.do?goAssign&investigateType=${investigateType}" width="700" height="200" funname="update"></t:dgToolBar>
   <t:dgToolBar title="删除" operationCode="delete" icon="icon-remove" url="tBInvestigatePlanController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" operationCode="detail" icon="icon-search" url="tBInvestigatePlanController.do?goDetail" funname="detail"></t:dgToolBar>
   <%--<t:dgToolBar title="排查计划上报集团" langArg="common.role" icon="icon-reload" url="tBInvestigatePlanController.do?uploadInvestigationPlan" funname="uploadInvestigationPlan"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
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
 <script src = "webpage/com/sdzk/buss/web/investigateplan/tBInvestigatePlanList.js"></script>
 <script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
     var li_east = 0;
     window.top["reload_tBInvestigatePlanList"]=function(){
         $("#tBInvestigatePlanList").datagrid( "load");
     };
     window.top["tip_tBInvestigatePlanList"]=function(){
         tip(this);
     };
 $(document).ready(function(){
     $("#tBInvestigatePlanListtb").find("input[name='month_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
     $("#tBInvestigatePlanListtb").find("input[name='month_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});

     $("#tBInvestigatePlanListtb").find("div[name='searchColums']>form>span:last").after($("#tempSearchColums div[name='searchColumsCenter']").html());
     $("#tempSearchColums").empty();

     $("input[name='queryHandleStatusTem']").on("change", function(){
         $("input[name='queryHandleStatus']").val($(this).val());
         if ($(this).val()=='true') {
             $("span:contains('编辑')").parents("a.l-btn").hide();
             $("span:contains('派发')").parents("a.l-btn").hide();
         } else {
             $("span:contains('编辑')").parents("a.l-btn").show();
             $("span:contains('派发')").parents("a.l-btn").show();
         }
         tBInvestigatePlanListsearch();
     });
 });

 function loadSuccessed (){
     if($("input[name='queryHandleStatus']").val()==null || $("input[name='queryHandleStatus']").val()==""){
         $("input[name='queryHandleStatus']").val("false");
         $("#queryHandleStatusTem_false").val("false");
         $("#queryHandleStatusTem_true").val("true");
         $("#queryHandleStatusTem_false").attr("checked","checked");
     }
 }

     function showHiddenDanger(id){
         $("#function-panel").panel(
                 {
                     href:"tBInvestigatePlanController.do?goHiddenList&planId="+id+"&type=detail"
                 }
         );
         if(li_east == 0){
             $('#main_list').layout('expand','east');
         }
     }
   
 
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

     function goAdd (title,addurl,gname,width,height){
         openwindow(title,addurl,gname,width,height);
     }
     function goUpdate (title,addurl,gname,width,height){
         var rowsData = $('#'+gname).datagrid('getSelections');
         if (!rowsData || rowsData.length==0) {
             tip('请选择'+title+'项目');
             return;
         }
         if (rowsData.length>1) {
             tip('请选择一条记录再'+title);
             return;
         }
         addurl += '&id='+rowsData[0].id;
         openwindow(title,addurl,gname,width,height);
     }

 //排查计划上报
 /**
  * 排查计划上报
  * 张赛超
  */
 function uploadInvestigationPlan(title,url,gname) {
     var ids = [];
     var rows = $("#"+gname).datagrid('getSelections');
     if (rows.length > 0) {
         $.dialog.setting.zIndex = getzIndex(true);
         $.dialog.confirm('你确定上报该数据吗?', function(r) {
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
                     beforeSend: function(data){
                         $.messager.progress({
                             text : "正在上报数据......",
                             interval : 100
                         });
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
     } else {
         tip("请选择需要上报的数据");
     }
 }

 </script>