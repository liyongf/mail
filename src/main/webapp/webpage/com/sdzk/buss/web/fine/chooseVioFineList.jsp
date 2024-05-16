<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
      <t:datagrid name="tBDangerSourceList" checkbox="true" pagination="true" fitColumns="true" title="关联罚款列表" actionUrl="tBFineController.do?chooseVioFineDataGrid&busId=${busId}" idField="id" fit="true" queryMode="group">
      <t:dgCol title="唯一编号"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>

          <t:dgCol title="罚款单号"  field="fineNum" width="120" align="center"></t:dgCol>
          <t:dgCol title="罚款日期"  field="fineDate" query="false" formatter="yyyy-MM-dd"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="责任单位"  field="dutyUnit.departname" query="false" width="120" align="center"></t:dgCol>
          <t:dgCol title="被罚款人"  field="beFinedMan" query="true" width="120" align="center"></t:dgCol>
          <t:dgCol title="罚款人"  field="fineMan" query="true"  queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
          <t:dgCol title="内容"  field="content"   hidden="false"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="罚款金额"  field="fineMoney"   hidden="false"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="罚款性质"  field="fineProperty" dictionary="fineProperty" query="false"  hidden="false" width="120" align="center"></t:dgCol>
      <%--<t:dgToolBar title="关联罚款" icon="icon-add" url="tBDangerSourceController.do?goAddressDangerSourceList&excludeId=${busId}" funname="choosefine"></t:dgToolBar>--%>

      <t:dgToolBar title="删除关联"  icon="icon-remove" url="tBFineController.do?doBatchDelVioRel&hiddenId=${busId}" funname="doBatchDelAddressRel"></t:dgToolBar>
      </t:datagrid>
  </div>
 </div>

<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
 <script type="text/javascript">

     window.top["reload_tBDangerSourceList"]=function(){
         $("#tBDangerSourceList").datagrid( "load");
     };

 $(document).ready(function(){
 });

 function choosefine(){
     openwindow('选择罚款','tBFineController.do?goVioRelFineList&hiddenId=${busId}',"",1000,500);

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