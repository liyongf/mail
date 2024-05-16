<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="whysPostList" checkbox="true" width="900px" height="450px" fitColumns="true" title="危害因素" actionUrl="riskIdentificationController.do?whysPostListDatagrid&riskId=${riskId}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="危害因素"  field="hazardFactorsPostEntity.hazardFactors"  queryMode="single"  width="120" align="center" formatterjs="valueTitle"></t:dgCol>
      <c:if test="${load ne 'detail'}">
          <t:dgToolBar title="添加" icon="icon-add" url="hazardFactorsController.do?goAddPost&riskId=${riskId}" funname="add" width="600" height="350"></t:dgToolBar>
          <t:dgToolBar title="取消关联"  icon="icon-remove" url="riskIdentificationController.do?doBatchDelPostRel" funname="deleteSelect"  ></t:dgToolBar>
      </c:if>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
     window.top["reload_whysPostList"]=function(){
         whysPostListsearch();
         if(typeof(this.msg)!='undefined' && this.msg!=null&&this.msg!=""){
             tip(this.msg);
         }
     };



     function deleteSelect(title,url,gname) {
         gridname=gname;
         var ids = [];
         var rows = $("#"+gname).datagrid('getSelections');
         if (rows.length > 0) {
             $.dialog.setting.zIndex = getzIndex(true);
             $.dialog.confirm('你确定取消关联该数据吗?', function(r) {
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
                                 window.top.reload_riskIdentificationPostList.call();
                             }
                         }
                     });
                 }
             });
         } else {
             tip("请选择需要取消关联的数据");
         }
     }

 </script>