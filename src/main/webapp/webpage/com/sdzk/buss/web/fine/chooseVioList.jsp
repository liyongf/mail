<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
      <t:datagrid name="tBDangerSourceList" checkbox="true" pagination="true" fitColumns="true" title="关联三违列表" actionUrl="tBFineController.do?chooseFineVioDataGrid&busId=${busId}" idField="id" fit="true" queryMode="group">
          <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="违章时间"  field="vioDate" formatter="yyyy-MM-dd" query="true"  queryMode="group" sortable="false" width="80" align="center"></t:dgCol>
          <t:dgCol title="班次"  field="shift" hidden="false" dictionary="workShift" query="true"  queryMode="single" sortable="false" width="60" align="center"></t:dgCol>
          <t:dgCol title="查处单位"  field="findUnits"  dictionary="t_s_depart,id,departname,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="违章地点"  field="vioAddress"  dictionary="t_b_address_info,id,address,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="违章单位"  field="vioUnits"  dictionary="t_s_depart,id,departname,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="违章人员"  field="vioPeople"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="违章分类"  field="vioCategory" dictionary="violaterule_wzfl" query="true"  queryMode="single" sortable="false" width="100" align="center"></t:dgCol>
          <%--<t:dgCol title="违章定性"  field="vioQualitative" dictionary="violaterule_wzdx" query="true"  queryMode="single" sortable="false" width="100" align="center"></t:dgCol>--%>
          <t:dgCol title="三违级别"  field="vioLevel" dictionary="vio_level" query="true"  queryMode="single" sortable="false" width="100" align="center"></t:dgCol>
          <t:dgCol title="制止人"  field="stopPeople"  queryMode="group"  width="120" align="center"></t:dgCol>

          <t:dgCol title="三违事实描述"  field="vioFactDesc"  formatterjs="valueTitle"  queryMode="group"  width="120"></t:dgCol>
          <t:dgCol title="备注"  field="remark"  formatterjs="valueTitle"  queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgToolBar title="关联三违" icon="icon-add" url="tBDangerSourceController.do?goHiddenVioReList&excludeId=${busId}" funname="choosefine"></t:dgToolBar>

      <t:dgToolBar title="删除关联"  icon="icon-remove" url="tBFineController.do?doBatchDelfineVioRel&fineId=${busId}" funname="doBatchDelAddressRel"></t:dgToolBar>
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
     openwindow('选择三违','tBFineController.do?goRelVioFineList&hiddenId=${busId}',"",1000,500);

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