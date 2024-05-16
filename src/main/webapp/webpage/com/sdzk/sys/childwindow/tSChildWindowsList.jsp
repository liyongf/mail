<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="childWindowsList" title="首页子窗口" actionUrl="childWindowController.do?datagrid" idField="id"
                    queryMode="group" fit="true" fitColumns="false">
            <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="子窗口标题" field="childWindowTitle" align="center" query="true" width="150"></t:dgCol>
            <t:dgCol title="子窗口框架ID" field="childWindowFrameId" align="center" width="200"></t:dgCol>
            <t:dgCol title="子窗口地址" field="childWindowUrl" width="400"></t:dgCol>
            <t:dgCol title="是否显示全屏按钮" field="isShowFullScreenBtn" dictionary="yes_no" align="center" width="120"></t:dgCol>
            <t:dgCol title="是否中间窗口" field="isCenter" dictionary="yes_no" query="true" align="center" width="120"></t:dgCol>
            <t:dgCol title="是否可用" field="isUsed" dictionary="yes_no" query="true" align="center" width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" align="center" width="150"></t:dgCol>
            <t:dgFunOpt funname="editStatus(id,isUsed)" title="禁用" exp="isUsed#eq#1" urlclass="ace_button" urlfont="fa-cog"  urlStyle="background-color:#ff9b0c;"></t:dgFunOpt>
            <t:dgFunOpt funname="editStatus(id,isUsed)" title="启用" exp="isUsed#eq#0" urlclass="ace_button" urlfont="fa-cog"  ></t:dgFunOpt>
            <t:dgDelOpt title="删除" url="childWindowController.do?del&id={id}" urlclass="ace_button" urlfont="fa-trash-o" urlStyle="background-color:#ec4758;"/>
            <t:dgToolBar title="录入" icon="icon-add" url="childWindowController.do?addorupdate" funname="add"></t:dgToolBar>
            <t:dgToolBar title="编辑" icon="icon-edit" url="childWindowController.do?addorupdate" funname="update"></t:dgToolBar>
            <t:dgToolBar title="查看" icon="icon-search" url="childWindowController.do?addorupdate" funname="detail"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>

<script>
    function editStatus(id,isUsed){
        var url = "childWindowController.do?save&id=" + id + "&isUsed=";
        var msg = '';
        if ("0" == isUsed) {
            msg = '确定启用该记录吗?';
            url += "1";
        }else{
            msg = '确定禁用该记录吗?';
            url += "0";
        }

        $.dialog.confirm(msg, function () {
            reloadTable();
            doSubmit(url);
        }, function () {
        });

    }
</script>