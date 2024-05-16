<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="tBHiddenDangerExamList" checkbox="true" pagination="true" fitColumns="true" title="隐患列表" actionUrl="tBHiddenDangerExamController.do?hiddenDangerDatagrid&replaceId=${replaceId}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="问题描述"  field="problemDesc"  queryMode="group"  width="180" align="center" formatterjs="valueTitle"></t:dgCol>
            <t:dgCol title="隐患类别"  field="hiddenCategory" dictionary="hiddenCate" queryMode="group"  width="100" align="center"></t:dgCol>
            <t:dgCol title="隐患类型"  field="hiddenType" dictionary="hiddenType" queryMode="group"  width="100" align="center"></t:dgCol>
            <t:dgCol title="隐患等级"  field="hiddenNature" dictionary="hiddenLevel" queryMode="group"  width="100" align="center"></t:dgCol>
            <t:dgCol title="备注"  field="remark"  queryMode="group"  width="120" align="center" formatterjs="valueTitle"></t:dgCol>
            <t:dgCol title="创建日期"  field="createDate"  formatter="yyyy-MM-dd" query="true" queryMode="group"  width="120"></t:dgCol>
            <t:dgToolBar title="查看详情" icon="icon-search" url="" funname="detail('隐患详情')"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
    });

    function detail (title) {
        var rows = $("#tBHiddenDangerExamList").datagrid('getSelections');
        if(rows.length==0){
            tip("请选择隐患!");
            return false;
        }else if(rows.length>1){
            tip("每次只能选择一个隐患!");
            return false;
        }else{
            var hid = rows[0].id;
            $.dialog(
                {content: "url:tBHiddenDangerExamController.do?goRiskDetail&replaceId=${replaceId}&load=detail&hid="+hid, zIndex: 2100, title: '' + title + '',  parent: windowapi, lock: true, width: 760, height: 380, left: '87%', top: '43%', opacity: 0.4,}
            );
        }
    }
</script>