<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <br>
        <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px">
        <input  name="queryHandleStatus" value="1" type="hidden">
           <label>
               <input name="queryHandleStatusTem" type="radio" value="1" checked="checked">
               待处理
           </label>
           <label>
            <input name="queryHandleStatusTem" type="radio" value="2">
									已闭合
           </label>
        </span>
    </div>
</div>

<div class="easyui-layout" fit="true" id="system_function_functionList">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBAddressInfoList" checkbox="true" pagination="true" fitColumns="false" title="管控任务管理" actionUrl="riskController.do?taskManagerCheckDatagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="风险点" field="addressInfoEntity.address" query="true" width="100"></t:dgCol>
      <t:dgCol title="排查时间" field="taskManagerControl.inveDate" formatter="yyyy-MM-dd" query="true" width="100"></t:dgCol>
      <t:dgCol title="排查类别" field="taskManagerControl.inveType"  dictionary="investigatePlan_type" query="true" width="100"></t:dgCol>
      <t:dgCol title="危险源主键" hidden="true" field="dangerSourceEntity.id" width="100"></t:dgCol>
      <t:dgCol title="危险源" field="dangerSourceEntity.hazard.hazardName" query="true"></t:dgCol>
      <t:dgCol title="风险类型" field="dangerSourceEntity.yeHazardCate" query="true" dictionary="hazardCate"></t:dgCol>
      <t:dgCol title="风险描述" field="dangerSourceEntity.yePossiblyHazard" width="100"></t:dgCol>
      <t:dgCol title="风险等级" field="dangerSourceEntity.yeRiskGrade" query="true" dictionary="riskLevel" width="100"></t:dgCol>
      <t:dgCol title="状态" field="status" query="false" hidden="true" width="100"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="300"></t:dgCol>

      <t:dgFunOpt funname="goAddHidden(id)" title="问题录入" urlclass="ace_button" urlfont="fa-cog" exp="status#eq#1"></t:dgFunOpt>
      <t:dgFunOpt funname="closeTaskOrder(id)" title="闭合" urlclass="ace_button" urlfont="fa-cog"  exp="status#eq#1"></t:dgFunOpt>

      <t:dgFunOpt funname="viewHidden(id)" title="查看问题" urlclass="ace_button" urlfont="fa-cog"></t:dgFunOpt>
   <t:dgToolBar  title="批量闭合" icon="icon-edit" url="riskController.do?goUpdateTaskManager" funname="closeAll"  width="950" height="400" operationCode="update"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
</div>

 <script type="text/javascript">

         function viewHidden(taskId){
             addOneTab("问题查看","tBHiddenDangerHandleController.do?queryList&taskId="+taskId,"default");
         }

     function goAddHidden(taskId){
         addOneTab("录入","tBHiddenDangerExamController.do?goAdd&examType=thrplus&taskId="+taskId,"default");
     }


     function closeAll(){
         var rows = $("#tBAddressInfoList").datagrid('getSelections');
         if(rows.length <=0){
             tip("请选择需要闭合的数据");
         }else{
             var ids = new Array();;
             for(var i =0 ;i<rows.length;i++){
                 ids.push(rows[i].id);
             }

             $.ajax({
                 url: "riskController.do?closeAllTaskOrder",
                 type: 'post',
                 data: {
                     ids: ids.join(",")
                 },
                 cache: false,
                 success: function (data) {
                     var d = $.parseJSON(data);
                     if (d.success) {
                         var msg = d.msg;
                         tip(msg);
                         reloadTable();
                     }
                 }
             });
         }
     }
     function closeTaskOrder(taskId){
         $.dialog.confirm('你确定闭合吗?', function (r) {
             if (r) {

                 $.ajax({
                     url: "riskController.do?closeTaskOrder",
                     type: 'post',
                     data: {
                         taskId: taskId
                     },
                     cache: false,
                     success: function (data) {
                         var d = $.parseJSON(data);
                         if (d.success) {
                             var msg = d.msg;
                             tip(msg);
                             reloadTable();
                         }
                     }
                 });
             }
         });
     }
     function goDistribution(title,url,gname) {
         gridname = gname;
         var ids = [];
         var rows = $("#" + gname).datagrid('getSelections');
         if (rows.length > 0) {

             $.dialog.setting.zIndex = getzIndex(true);
             $.dialog.confirm('你确定派发该数据吗?', function (r) {
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
             tip("请选择需要派发的数据");
         }
     }

 $(document).ready(function(){
     $("a[iconcls='icon-reload']").hide();
     $("input[name='inveDate_begin']").val("${start}");
     $("input[name='inveDate_end']").val("${end}");
     var datagrid = $("#tBAddressInfoListtb");
     datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");

     $("input[name='queryHandleStatusTem']").change(function() {
         var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();

         $("input[name='queryHandleStatus']").val(selectedvalue);
         if(selectedvalue=="1"){
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","");
             $("span:contains('批量闭合')").parents("a.l-btn").show();
         }else if(selectedvalue=="2") {//未整改显示，其他隐藏
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display", "none");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display", "none");
             $("span:contains('批量闭合')").parents("a.l-btn").hide();
         }

         tBAddressInfoListsearch();
     });
 });

 </script>