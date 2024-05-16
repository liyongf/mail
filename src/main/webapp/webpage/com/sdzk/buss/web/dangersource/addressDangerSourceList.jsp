<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
      <t:datagrid name="tBDangerSourceList" checkbox="true" pagination="true" fitColumns="true" title="${addressName}-关联风险列表" actionUrl="tBDangerSourceController.do?addressDangerSourceDatagrid&addressId=${addressId}" idField="id" fit="true" queryMode="group">
      <t:dgCol title="唯一编号"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <c:if test="${anju == 'true'}">
      <t:dgCol title="风险描述"  field="yeMhazardDesc" queryMode="single" query="true" width="120"></t:dgCol>
          </c:if>
          <c:if test="${anju == 'false'}">
              <t:dgCol title="隐患描述"  field="yeMhazardDesc" queryMode="single" query="true" width="120"></t:dgCol>
          </c:if>
      <t:dgCol title="风险等级"  field="yeRiskGrade" dictionary="riskLevel" queryMode="single"  width="120" align="center"></t:dgCol>
      <t:dgCol title="风险类型"  field="yeHazardCate" dictionary="hazardCate"  queryMode="single"  width="120" align="center"></t:dgCol>
      <t:dgCol title="危险源来源"  field="origin" replace="通用_1,年度_2,专项_3"   queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgToolBar title="关联风险" icon="icon-add" url="tBDangerSourceController.do?goAddressDangerSourceList&excludeId=${addressId}" funname="chooseDangerSource"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="icon-search" url="tBDangerSourceController.do?goDetail" funname="detail" width="850" height="450"></t:dgToolBar>
      <t:dgToolBar title="删除关联"  icon="icon-remove" url="tBDangerSourceController.do?doBatchDelAddressRel&addressId=${addressId}" funname="doBatchDelAddressRel"></t:dgToolBar>
      </t:datagrid>
  </div>
 </div>

<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
 <script type="text/javascript">
 $(document).ready(function(){
 });

 function chooseDangerSource(){
     openwindow('风险选择','tBDangerSourceController.do?goAddressDangerSourceList&excludeId=${addressId}',"",1000,500);

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