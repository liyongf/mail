<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
      <t:datagrid name="tBDangerSourceList" checkbox="true" pagination="true" fitColumns="true" title="关联隐患列表" actionUrl="tBFineController.do?chooseFineHiddenDataGrid&busId=${busId}" idField="id" fit="true" queryMode="group">
      <t:dgCol title="唯一编号"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>

          <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group" sortable="false" width="120"></t:dgCol>
          <t:dgCol title="日期"  field="examDate"  formatter="yyyy-MM-dd" query="true"  queryMode="group" sortable="false" width="73" align="center"></t:dgCol>
          <t:dgCol title="班次" field="shift"  dictionary="workShift" align="center" width="37" query="true"></t:dgCol>
          <t:dgCol title="危险源"  field="dangerId.hazard.hazardName"   queryMode="single"  sortable="false" width="64" align="center"></t:dgCol>
          <t:dgCol title="地点"  field="address.address" queryMode="group" sortable="false" width="100" align="center"></t:dgCol>
          <t:dgCol title="责任单位"  field="dutyUnit.departname"    queryMode="group" sortable="false" width="76" align="center"></t:dgCol>
          <t:dgCol title="责任人"  field="dutyMan"    queryMode="group" sortable="false" width="70" align="center"></t:dgCol>
          <t:dgCol title="督办单位"  field="superviseUnitId" dictionary="t_s_depart,id,departname,where 1=1" queryMode="group"  sortable="false" width="76" align="center"></t:dgCol>
          <t:dgCol title="问题描述"  field="problemDesc"   formatterjs="valueTitle" queryMode="group" sortable="false" width="400"></t:dgCol>
          <t:dgCol title="隐患等级"  field="hiddenNature" hidden="false" dictionary="hiddenLevel"  queryMode="group" sortable="false" width="78" align="center"></t:dgCol>
          <t:dgCol title="限期日期"  field="limitDate" formatter="yyyy-MM-dd"   queryMode="group" sortable="false" width="80" align="center"></t:dgCol>
      <t:dgToolBar title="关联隐患" icon="icon-add" url="tBDangerSourceController.do?goHiddenVioReList&excludeId=${busId}" funname="choosefine"></t:dgToolBar>

      <t:dgToolBar title="删除关联"  icon="icon-remove" url="tBFineController.do?doBatchDelfineHiddenRel&fineId=${busId}" funname="doBatchDelAddressRel"></t:dgToolBar>
      </t:datagrid>
  </div>
 </div>

<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
 <script type="text/javascript">
     function valueTitle(value){
         return "<a title=\""+value+"\">"+value+"</a>";
     }
     window.top["reload_tBDangerSourceList"]=function(){
         $("#tBDangerSourceList").datagrid( "load");
     };

 $(document).ready(function(){
 });

 function choosefine(){
     openwindow('选择隐患','tBFineController.do?goRelHiddenFineList&hiddenId=${busId}',"",1000,500);

 }


 function doBatchDelAddressRel(title,url,gname) {
     gridname=gname;
     var ids = [];
     var rows = $("#"+gname).datagrid('getSelections');
     if (rows.length > 0) {
         $.dialog.setting.zIndex = getzIndex(true);
         $.dialog.confirm('确定删除该关联关系吗?', function(r) {
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
         tip("请选择需要删除的关联关系");
     }
 }

 </script>