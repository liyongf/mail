<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<t:datagrid name="userList" title="common.operation" autoLoadData="false" actionUrl="userController.do?datagrid" fit="true" fitColumns="true" idField="id" queryMode="group" sortName="userKey,status,createDate,userName" sortOrder="desc,desc,asc,desc">
	<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="common.username" sortable="false" field="userName" query="true"></t:dgCol>
	<t:dgCol title="common.real.name" field="realName" query="true"></t:dgCol>
	<%--<t:dgCol title="common.department" field="TSDepart_id" query="true" replace="${departsReplace}"></t:dgCol>--%>
	<t:dgCol title="common.department" sortable="false" field="userOrgList.tsDepart.departname" query="true"></t:dgCol>
	<t:dgCol title="common.role" field="userKey" ></t:dgCol>
	<t:dgCol title="common.createby" field="createBy" hidden="true"></t:dgCol>
	<t:dgCol title="common.createtime" field="createDate" formatter="yyyy-MM-dd" hidden="false"></t:dgCol>
	<t:dgCol title="common.updateby" field="updateBy" hidden="true"></t:dgCol>
	<t:dgCol title="common.updatetime" field="updateDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
	<t:dgCol title="common.status" sortable="true" field="status" replace="common.active_1,common.inactive_0,super.admin_-1" ></t:dgCol>
	<t:dgCol title="是否有效" sortable="true" field="deleteFlag" query="true" replace="有效_0,无效_1" ></t:dgCol>
	<t:dgCol title="简拼" field="spelling" query="false"></t:dgCol>
	<t:dgCol title="全拼" field="fullSpelling" query="false"></t:dgCol>
	<t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
	<t:dgFunOpt funname="deleteDialog(id,userName,deleteFlag)" title="置为无效" exp="deleteFlag#eq#0" urlclass="ace_button" urlfont="fa-cog"  urlStyle="background-color:#ec4758;"></t:dgFunOpt>
	<t:dgFunOpt funname="deleteDialog(id,userName,deleteFlag)" title="置为有效" exp="deleteFlag#eq#1" urlclass="ace_button" urlfont="fa-cog"  ></t:dgFunOpt>
	<t:dgToolBar title="common.add.param" langArg="common.user" icon="icon-add" url="userController.do?addorupdate" funname="add" operationCode="add"></t:dgToolBar>
	<t:dgToolBar title="common.edit.param" langArg="common.user" icon="icon-edit" url="userController.do?addorupdate" funname="update" operationCode="update"></t:dgToolBar>
	<t:dgToolBar title="common.password.reset" icon="icon-edit" url="userController.do?changepasswordforuser" funname="update" operationCode="reset"></t:dgToolBar>
	<t:dgToolBar title="common.lock.user" icon="icon-edit" url="userController.do?lock&lockvalue=0" funname="lockObj" operationCode="lockUser" ></t:dgToolBar>
	<t:dgToolBar title="common.unlock.user" icon="icon-edit" url="userController.do?lock&lockvalue=1" funname="unlockObj" operationCode="unlockUser" ></t:dgToolBar>
	<t:dgToolBar title="excelImport" icon="icon-put" funname="ImportXls" operationCode="import"></t:dgToolBar>
	<t:dgToolBar title="excelOutput" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>
	<t:dgToolBar title="templateDownload" icon="icon-putout" funname="ExportXlsByT" operationCode="downloadTmpl"></t:dgToolBar>
	<t:dgToolBar title="批量创建检索" icon="icon-putout" funname="createSpell" operationCode="createSpell"></t:dgToolBar>
	
</t:datagrid>
<script>
    function createSpell(){
        $.dialog.confirm('是否保留已创建检索?', function (r) {
            if (r) {
                createSe(true);
            }
        },function(){
            createSe(false);
        });

    }

    function createSe(saveOld){
        $.ajax({
            url: 'userController.do?createSpell',
            type: 'post',
            async:false,
            data:{
                saveOld:saveOld
            },
            beforeSend: function(data){
                $.messager.progress({
                    text : "正在创建......",
                    interval : 100
                });
            },
            cache: false,
            success: function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    var msg = d.msg;
                    tip(msg);
                    userListsearch();
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


    function mima(){
        $.ajax({
            url: 'userController.do?mima',
            type: 'post',
            async:false,
            beforeSend: function(data){
                $.messager.progress({
                    text : "正在修改......",
                    interval : 100
                });
            },
            cache: false,
            success: function (data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    var msg = d.msg;
                    tip(msg);
                    userListsearch();
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

    $(function() {
        $("select[name='deleteFlag']").val("0");
		setTimeout(
            userListsearch(),1000);
//        var datagrid = $("#userListtb");
//		datagrid.find("div[name='searchColums']").find("form#userListForm").append($("#realNameSearchColums div[name='searchColumsRealName']").html());
//		$("#realNameSearchColums").html('');
//        datagrid.find("div[name='searchColums']").find("form#userListForm").append($("#tempSearchColums div[name='searchColums']").html());
//        $("#tempSearchColums").html('');
	});
</script>
<%--<div id="realNameSearchColums" style="display: none;">--%>
	<%--<div name="searchColumsRealName">--%>
		<%--<t:userSelect hasLabel="true" selectedNamesInputId="realName" windowWidth="1000px" windowHeight="600px" title="用户名称"></t:userSelect>--%>
	<%--</div>--%>
<%--</div>--%>
<%--<div id="tempSearchColums" style="display: none;">--%>
    <%--<div name="searchColums">--%>
       <%--<t:departSelect hasLabel="true" selectedNamesInputId="orgNames"></t:departSelect>--%>
    <%--</div>--%>
<%--</div>--%>
<script type="text/javascript">
function deleteDialog(id,userName,deleteFlag){
    var url = "userController.do?delete&id=" + id + "&deleteFlag=" + deleteFlag;
    //	createwindow("确认", url, 200, 100);
	var msg = '确认设置该用户为无效状态吗?';
	if ("1" == deleteFlag) {
		var msg = '确认设置该用户为有效状态吗?';
	}
    var admin="admin";
    if (userName==admin) {
        tip("管理员不允许设置");
        return;
    }
    else {
        $.dialog.confirm(msg, function () {
            reloadTable();
            doSubmit(url);
        }, function () {
        });
    }
}
function lockObj(title,url, id) {

	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('<t:mutiLang langKey="common.please.select.edit.item"/>');
		return;
	}
		url += '&id='+rowsData[0].id;

	$.dialog.confirm('<t:mutiLang langKey="common.lock.user.tips"/>', function(){
		lockuploadify(url, '&id');
	}, function(){
	});
}
function unlockObj(title,url, id) {
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('<t:mutiLang langKey="common.please.select.edit.item"/>');
		return;
	}
		url += '&id='+rowsData[0].id;

	$.dialog.confirm('<t:mutiLang langKey="common.unlock.user.tips"/>', function(){
		lockuploadify(url, '&id');
	}, function(){
	});
}


function lockuploadify(url, id) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
			var msg = d.msg;
				tip(msg);
				reloadTable();
			}
		}
	});
}
</script>

<script type="text/javascript">
	//导入
	function ImportXls() {
		openuploadwin('Excel导入', 'userController.do?upload', "userList");
	}

	//导出
	function ExportXls() {
		JeecgExcelExport("userController.do?exportXls", "userList");
	}

	//模板下载
	function ExportXlsByT() {
		JeecgExcelExport("userController.do?exportXlsByT", "userList");
	}

    function searchReset(name) {
        $("#" + name + "tb").find(":input").val("");
        $("select[name='deleteFlag']").val("0");
        var queryParams = $('#userList').datagrid('options').queryParams;
        $('#userListtb').find('*').each(function () {
            queryParams[$(this).attr('name')] = $(this).val();
        });
        $('#userList').datagrid({
            url: 'userController.do?datagrid&field=id,userName,realName,userOrgList.tsDepart.departname,userKey,createBy,createDate,updateBy,updateDate,status,deleteFlag,',
            pageNumber: 1
        });
    }
</script>