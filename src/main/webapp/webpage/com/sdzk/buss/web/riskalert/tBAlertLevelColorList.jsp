<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBAlertLevelColorList" checkbox="true" title="预警等级颜色" actionUrl="tBAlertLevelColorController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="预警等级"  field="alertLevelName"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="预警颜色"  field="alertLevelColor" formatterjs="colorValueFormatter"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属煤矿" field="belongMine" hidden="true" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="tBAlertLevelColorController.do?doDel&id={id}" />
   <t:dgToolBar title="录入" operationCode="add" icon="icon-add" url="tBAlertLevelColorController.do?goAdd" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" operationCode="update" icon="icon-edit" url="tBAlertLevelColorController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除" operationCode="batchdelete" icon="icon-remove" url="tBAlertLevelColorController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<script type="text/javascript">
    //颜色列格式化
    function colorValueFormatter(value,rec,index){
        return '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 124px; padding-left: 26px;" type="text" value="'+value+'"><span class="minicolors-swatch" style="top:0px;"><span class="minicolors-swatch-color" style="background-color: '+value+';"></span></span></div></div>';
    }
</script>