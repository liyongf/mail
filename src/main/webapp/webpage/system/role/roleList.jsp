<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,ztree"></t:base>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="roleList" title="common.role.list" actionUrl="roleController.do?roleGrid"  idField="id" sortName="createDate" sortOrder="desc" queryMode="group">
	<t:dgCol title="common.code" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="common.role.code" field="roleCode"></t:dgCol>
	<t:dgCol title="common.role.name" field="roleName" query="true"></t:dgCol>
	<t:dgCol title="common.createby" field="createBy" hidden="true"></t:dgCol>
	<t:dgCol title="common.createtime" field="createDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
	<t:dgCol title="common.updateby" field="updateBy" hidden="true"></t:dgCol>
	<t:dgCol title="common.updatetime" field="updateDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
	<t:dgCol title="common.operation" field="opt"></t:dgCol>
	<t:dgFunOpt funname="userListbyrole(id,roleName)" title="查看用户" urlclass="ace_button"  urlfont="fa-user"></t:dgFunOpt>
	<t:dgFunOpt funname="setfunbyrole(id,roleName)" title="菜单权限" urlclass="ace_button" urlStyle="background-color:#18a689;"  urlfont="fa-list"></t:dgFunOpt>
	<t:dgFunOpt funname="setChildWinByRole(id,roleName)" title="首页权限" urlclass="ace_button" urlStyle="background-color:#ff9b0c;"  urlfont="fa-cog"></t:dgFunOpt>
    <t:dgFunOpt funname="setAppfunbyrole(id,roleName)" title="APP权限设置" urlclass="ace_button" urlStyle="background-color:#18a683;"  urlfont="fa-cog"></t:dgFunOpt>
	<t:dgFunOpt funname="delRole(id)" title="common.delete" urlclass="ace_button"  urlStyle="background-color:#ec4758;" urlfont="fa-trash-o"></t:dgFunOpt>
	<t:dgToolBar title="common.add.param" langArg="common.role" icon="icon-add" url="roleController.do?addorupdate" funname="add" operationCode="add"></t:dgToolBar>
	<t:dgToolBar title="common.edit.param" langArg="common.role" icon="icon-edit" url="roleController.do?addorupdate" funname="update" operationCode="update"></t:dgToolBar>
	<t:dgToolBar title="excelImport" icon="icon-put" funname="ImportXls" operationCode="import"></t:dgToolBar>
	<t:dgToolBar title="excelOutput" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>
	<t:dgToolBar title="templateDownload" icon="icon-putout" funname="ExportXlsByT" operationCode="downloadTmpl"></t:dgToolBar>
</t:datagrid></div>
</div>
<div region="east" style="width: 600px;" split="true">
<div tools="#tt" class="easyui-panel" title='<t:mutiLang langKey="permission.set"/>' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>
</div>
<script type="text/javascript">
function setfunbyrole(id,roleName) {
	$("#function-panel").panel(
		{
			title :roleName+ ':' + '<t:mutiLang langKey="current.permission"/>',
			href:"roleController.do?fun&roleId=" + id
		}
	);
	//$('#function-panel').panel("refresh" );
	
}

function setChildWinByRole(id,roleName) {
    if (typeof(windowapi) == 'undefined') {
        $.dialog({content: 'url:childWindowController.do?goEditRoleChildWin&roleId='+id, zIndex: 2100, title: '首页权限 - '+roleName, lock: true, width: 800, height: 350, left: '45%', top: '45%', opacity: 0.4, button: [
            {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_childWinByRole, focus: true},
            {name: '<t:mutiLang langKey="common.close"/>', callback: function () {
            }}
        ]});
    } else {
        $.dialog({content: 'url:childWindowController.do?goEditRoleChildWin&roleId='+id, zIndex: 2100, title: '首页权限 - '+roleName, lock: true, parent: windowapi, width: 800, height: 350, left: '45%', top: '45%', opacity: 0.4, button: [
            {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_childWinByRole, focus: true},
            {name: '<t:mutiLang langKey="common.close"/>', callback: function () {
            }}
        ]});
    }
}

function clickcallback_childWinByRole(){
    iframe = this.iframe.contentWindow;
    var left_top = iframe.getValueByElementName('left_top');
    var center_top = iframe.getValueByElementName('center_top');
    var right_top = iframe.getValueByElementName('right_top');
    var left_bottom = iframe.getValueByElementName('left_bottom');
    var center_bottom = iframe.getValueByElementName('center_bottom');
    var right_bottom = iframe.getValueByElementName('right_bottom');
    var roleId = iframe.getRoleId();

    $.ajax({
        url: 'childWindowController.do?saveRoleChildWin',
        type: 'post',
        data: {
            left_top: left_top,
            center_top: center_top,
            right_top: right_top,
            left_bottom: left_bottom,
            center_bottom: center_bottom,
            right_bottom: right_bottom,
			roleId:roleId
        },
        beforeSend: function(data){
            $.messager.progress({
                text : "正在保存......",
                interval : 100
            });
        },
        cache: false,
        success: function (data) {
            var d = $.parseJSON(data);
            if (d.success) {
                var msg = d.msg;
                tip(msg);
            }else{
                var msg = d.msg;
                tip(msg);
            }
        },
        complete: function(data){
            $.messager.progress('close');
        },
        error:function(data){
            tip("操作失败");
        }
    });
}

function setAppfunbyrole(id,roleName) {
    $("#function-panel").panel(
            {
                title :roleName+ ':' + '<t:mutiLang langKey="current.permission"/>',
                href:"roleController.do?appFun&roleId=" + id
            }
    );
    //$('#function-panel').panel("refresh" );

}

function userListbyrole(id,roleName) {
	$("#function-panel").panel(
		{
			title :roleName+ ':' + '<t:mutiLang langKey="common.user"/>',
			href:"roleController.do?userList&roleId=" + id
		}
	);
	//$('#function-panel').panel("refresh" );
}

//删除角色
function delRole(id){
	var tabName= 'roleList';
	var url= 'roleController.do?delRole&id='+id;
	$.dialog.confirm('<t:mutiLang langKey="confirm.delete.this.record"/>', function(){
		doSubmit(url,tabName);
		rowid = '';
		$("#function-panel").html("");//删除角色后，清空对应的权限
	}, function(){
	});
}
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'roleController.do?upload', "roleList");
}

//导出
function ExportXls() {
	JeecgExcelExport("roleController.do?exportXls", "roleList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("roleController.do?exportXlsByT", "roleList");
}
</script>
