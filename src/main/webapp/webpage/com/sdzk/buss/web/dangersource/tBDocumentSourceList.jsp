<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBDocumentSourceList" title="文档来源管理" actionUrl="tBDocumentSourceController.do?docSourceGrid" idField="id" treegrid="true" pagination="false" fit="true">
   <t:dgCol title="主键" field="id" treefield="id" hidden="true"></t:dgCol>
   <t:dgCol title="文档来源名称" field="docSourceName" treefield="text" ></t:dgCol>
   <%--<t:dgCol title="父节点ID" field="parentSourceId"   width="120"></t:dgCol>
   <t:dgCol title="是否删除" field="isDelete" hidden="true" width="120"></t:dgCol>--%>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBDocumentSourceController.do?del&id={id}" />
   <t:dgToolBar title="文档来源录入" icon="icon-add" url="tBDocumentSourceController.do?addorupdate" operationCode="add" funname="addFun"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBDocumentSourceController.do?addorupdate" operationCode="edit" funname="updateFun"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>

<script type="text/javascript">
    function addFun(title,url, id) {
        var rowData = $('#'+id).datagrid('getSelected');
        if (rowData) {
            url += '&parentDocSource.id='+rowData.id;
        }
        add(title,url,'tBDocumentSourceList',500,300);
    }

    function updateFun(title,url, id) {
        /*var rowData = $('#'+id).datagrid('getSelected');
        if (rowData) {
            url += '&parentDocSource.id='+rowData.id;
        }*/
        update(title,url,'tBDocumentSourceList',500,300);
    }
</script>