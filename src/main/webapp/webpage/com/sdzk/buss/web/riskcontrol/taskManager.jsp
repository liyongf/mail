<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <br>
        <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px">
        <input  name="queryHandleStatus" value="0" type="hidden">
           <label>
               <input name="queryHandleStatusTem" type="radio" value="0" checked="checked">
               草稿
           </label>
           <label>
            <input name="queryHandleStatusTem" type="radio" value="1">
									已派发
           </label>
        </span>
    </div>
</div>

<div class="easyui-layout" fit="true" id="system_function_functionList">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBAddressInfoList" checkbox="true" pagination="true" fitColumns="false" title="管控任务管理" actionUrl="riskController.do?taskManagerDatagrid" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="排查时间"  field="inveDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" query="true"></t:dgCol>
    <t:dgCol title="排查类别"  field="inveType"  dictionary="investigatePlan_type"  queryMode="single"  width="120" query="true"></t:dgCol>
      <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
      <t:dgFunOpt funname="operationDetail(id)" title="管控清单" urlclass="ace_button" urlfont="fa-cog"></t:dgFunOpt>
   <t:dgToolBar  title="创建任务" icon="icon-add" url="riskController.do?goAddTaskManager" funname="add"  width="950" height="400" operationCode="add"></t:dgToolBar>
   <t:dgToolBar  title="修改" icon="icon-edit" url="riskController.do?goUpdateTaskManager" funname="update"  width="950" height="400" operationCode="update"></t:dgToolBar>
   <t:dgToolBar  title="删除" icon="icon-remove " url="riskController.do?deleteALLSelect" funname="deleteALLSelect"  width="950" height="400" operationCode="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar  title="派发" icon="icon-edit" url="riskController.do?doDistribution" funname="goDistribution"  width="950" height="400" operationCode="goDistribution"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<div data-options="region:'east',
	title:'',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
     style="width: 600px; overflow: hidden;">
    <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="operationDetailpanel"></div>
</div>
</div>

 <script type="text/javascript">
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
         if(selectedvalue=="0"){
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","");
             $("span:contains('创建任务')").parents("a.l-btn").show();
             $("span:contains('修改')").parents("a.l-btn").show();
             $("span:contains('删除')").parents("a.l-btn").show();
             $("span:contains('派发')").parents("a.l-btn").show();
         }else if(selectedvalue=="1") {//未整改显示，其他隐藏
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display", "none");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display", "none");
             $("span:contains('创建任务')").parents("a.l-btn").hide();
             $("span:contains('修改')").parents("a.l-btn").hide();
             $("span:contains('删除')").parents("a.l-btn").hide();
             $("span:contains('派发')").parents("a.l-btn").hide();
         }

         tBAddressInfoListsearch();
     });
 });
 function operationDetail(taskId)
 {
     if(li_east == 0){
         $('#system_function_functionList').layout('expand','east');
     }
     $('#operationDetailpanel').panel("refresh", "riskController.do?taskManagerOrderList&taskId=" +taskId);
 }
 </script>