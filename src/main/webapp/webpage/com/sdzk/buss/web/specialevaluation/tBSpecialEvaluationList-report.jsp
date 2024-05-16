<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <br>
       <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px;text-align: center;width: 100%">
           <input type="hidden" name="status" id="status" value="1" />
           <label>
               <input name="statusTemp" type="radio" value="1,2" >
               全部
           </label>
           <label>
               <input name="statusTemp" type="radio" value="0"  checked="checked">
               待上报
           </label>
           <label>
               <input name="statusTemp" type="radio" value="2" >
               已上报
           </label>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true" id="main_typegroup_list">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBSpecialEvaluationList" checkbox="true" pagination="true" fitColumns="true" title="专项风险评估" actionUrl="tBSpecialEvaluationController.do?datagrid&reportType=${reportType}" idField="id" fit="true" queryMode="group" sortName="time" sortOrder="desc">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="辨识名称"  field="name"   query="true"  queryMode="single"  width="120" align="center" formatterjs="valueTitle"></t:dgCol>
      <t:dgCol title="辨识时间"  field="time"  query="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="辨识地点"  field="location"   dictionary="t_b_address_info,id,address,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="辨识负责人"  field="leader" dictionary="t_s_base_user,id,realname,where 1=1"   queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="评估结论"  field="remark"    queryMode="group"  width="120" align="center" formatterjs="valueTitle"></t:dgCol>
      <t:dgCol title="风险数量"  field="dangerSourceCount"    queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="专项辨识类型"  field="type" dictionary="specialEvaluationType" query="true"   queryMode="single"  width="120" align="center"></t:dgCol>
    <%--<t:dgCol title="辨识人类型"  field="leaderType"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="辨识参加人员"  field="participant"    queryMode="group"  width="120"></t:dgCol>--%>
    <t:dgCol title="上报状态"  field="reportStatus"  dictionary="seReportStatus"  queryMode="group"  width="120" align="center"></t:dgCol>
    <%--<t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="创建日期"  field="createDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="更新人名称"  field="updateName" hidden="true"   queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="更新日期"  field="updateDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>--%>
      <t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>

      <t:dgFunOpt funname="setfunbyrole(id)" title="辨识结果"  urlclass="ace_button"></t:dgFunOpt>
   <%--<t:dgToolBar title="录入" icon="icon-add" url="tBSpecialEvaluationController.do?goAdd&type=${type}" funname="add"></t:dgToolBar>--%>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBSpecialEvaluationController.do?goUpdateOnReport&type=${type}" funname="update" operationCode="reportEdit"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBSpecialEvaluationController.do?LogicDoBatchDel" funname="deleteALLSelect" operationCode="batchDel"></t:dgToolBar>
   <t:dgToolBar title="克隆" icon="icon-add" url="tBSpecialEvaluationController.do?goClone" funname="clone" operationCode="clone"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBSpecialEvaluationController.do?goUpdate" funname="detail" operationCode="detail"></t:dgToolBar>
   <t:dgToolBar title="上报省局平台"  icon="icon-edit" url="tBSpecialEvaluationController.do?mineReport" funname="mineReport" operationCode="report"></t:dgToolBar>
   <%--<t:dgToolBar title="上报集团"  icon="icon-edit" url="tBSpecialEvaluationController.do?specialEvaluateReportToGroup" funname="specialEvaluateReportToGroup" operationCode="report"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
    <t:dgToolBar title="从省局平台撤回" icon="icon-undo" url="tBSpecialEvaluationController.do?toReportCallback" funname="toReportCallback" operationCode="undoReport"></t:dgToolBar>
      <c:if test="${isSunAdmin == 'YGADMIN'}">
          <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
      </c:if>
  </t:datagrid>
  </div>
 </div>
<div data-options="region:'east',
	title:'辨识结果',
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
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="userListpanel"></div>
</div>
 <script src = "webpage/com/sdzk/buss/web/specialevaluation/tBSpecialEvaluationList.js"></script>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
     var datagrid = $("#tBSpecialEvaluationListForm");
     datagrid.append($("#tempSearchColums div[name='searchColums']").html());
     $("#tempSearchColums").empty();

     $("a[iconcls='icon-reload']").hide();
//     $("span:contains('克隆')").parents("a.l-btn").hide();

     $("input[name='statusTemp']").change(function() {
         var selectedvalue = $("input[name='statusTemp']:checked").val();
         $("#status").val(selectedvalue);
         tBSpecialEvaluationListsearch();
         if(selectedvalue == '2'){
//             $("span:contains('录入')").parents("a.l-btn").show();
//             $("span:contains('编辑')").parents("a.l-btn").show();
//             $("span:contains('上报')").parents("a.l-btn").show();
//             $("span:contains('删除')").parents("a.l-btn").show();
             $("span:contains('撤回')").parents("a.l-btn").show();
             $("span:contains('上报省局平台')").parents("a.l-btn").hide();
             $("span:contains('克隆')").parents("a.l-btn").show();
             $("span:contains('编辑')").parents("a.l-btn").hide();
             $("span:contains('批量删除')").parents("a.l-btn").hide();
         } else {
//             $("span:contains('录入')").parents("a.l-btn").hide();
//             $("span:contains('编辑')").parents("a.l-btn").hide();
//             $("span:contains('上报')").parents("a.l-btn").hide();
//             $("span:contains('删除')").parents("a.l-btn").hide();
             $("span:contains('撤回')").parents("a.l-btn").hide();
             $("span:contains('上报省局平台')").parents("a.l-btn").show();
             $("span:contains('克隆')").parents("a.l-btn").show();
             $("span:contains('编辑')").parents("a.l-btn").show();
             $("span:contains('批量删除')").parents("a.l-btn").show();

         }
     });
     $("span:contains('撤回')").parents("a.l-btn").hide();
 });
/**
  * 撤回
 * @author
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
                     beforeSend: function(data){
                         $.messager.progress({
                             text : "正在上报数据......",
                             interval : 100
                         });
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
                         }else{
                             var msg = d.msg;
                             tip(msg);
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
         tip("请选择需要撤回的数据");
     }
}
 /**
  * 上报
  * @param title
  * @param url
  * @param gname
  */
 function mineReport(title,url,gname) {
     gridname=gname;
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
 function specialEvaluateReportToGroup(title,url,gname) {
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
 function setfunbyrole(id) {
     if(li_east == 0){
         $('#main_typegroup_list').layout('expand','east');
     }
     var rowIndex = $('#tBSpecialEvaluationList').datagrid('getRowIndex', id);//id是关键字值
     var data = $('#tBSpecialEvaluationList').datagrid('getData').rows[rowIndex];
     var name = data.name;//operator是属性值
     $('#main_typegroup_list').layout('panel','east').panel('setTitle', "辨识结果-"+name);
     $('#userListpanel').panel("refresh", "tBDangerSourceController.do?specialEvaluationDangerList&type=mine&seId="+id);
 }
 function loadSuccess() {
     $('#main_typegroup_list').layout('panel','east').panel('setTitle', "");
     $('#main_typegroup_list').layout('collapse','east');
     $('#userListpanel').empty();
 }

//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBSpecialEvaluationController.do?upload', "tBSpecialEvaluationList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBSpecialEvaluationController.do?exportXls&reportType=${reportType}","tBSpecialEvaluationList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBSpecialEvaluationController.do?exportXlsByT","tBSpecialEvaluationList");
}


 /**
  *  专项辨识克隆
  * */
 function clone(title,url,gname){
     var rows=$('#'+gname).datagrid("getSelections");
     if(rows.length==0){
         tip("请选择待克隆的专项辨识");
         return false;
     }
     if(rows.length>1){
         tip("请选择一条待克隆的专项辨识");
         return false;
     }
//     $.ajax({
//         type: 'POST',
//         url: 'tBSpecialEvaluationController.do?goClone',
//         dataType:"json",
//         async:false,
//         data: {
//             id:rows[0].id
//         },
//         success:function(data){
//             var result = jQuery.parseJSON(data);
//             openwindow("克隆","tBSpecialEvaluationController.do?goClone&id="+rows[0].id,"tBSpecialEvaluationList",800,600);
//         },
//         error:function(data){
//         }
//     });
     $.dialog({
         content: 'url:'+url+"&id="+rows[0].id,
         lock : true,
         //zIndex:1990,
         width:800,
         height:400,
         title:'克隆',
         opacity : 0.3,
         cache:false,
         ok: function(){
             iframe = this.iframe.contentWindow;
             saveObj();
             return false;
         },
         cancelVal: '关闭',
         cancel: true /*为true等价于function(){}*/
     })
 }

 /**
  *  阳光账号隐藏数据功能
  * */
 function sunHidden() {
     var rows = $("#tBSpecialEvaluationList").datagrid('getSelections');
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
             url: 'tBSpecialEvaluationController.do?sunshine',
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