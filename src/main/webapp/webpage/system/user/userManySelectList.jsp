<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script>
    $(function() {
        var datagrid = $("#userSelectListtb");
        datagrid.find("div[name='searchColums']>span:last").before($("#tempSearchColums div[name='searchColums']").html());
    });
</script>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums">
        <span style="display:-moz-inline-box;margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="<t:mutiLang langKey="common.department"/>">
                <t:mutiLang langKey="common.department"/>：
            </span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">
            <input id="orgIds" name="orgIds" type="hidden">
            <input readonly="true" type="text" name="departname" style="width: 100px" onclick="choose_297e201048183a730148183ad85c0001()"/>
            </span>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">

    <div region="west" style="width: 200px;" title=" "  split="true">
        <ul id="formtree">
        </ul>
    </div>

<div region="center" style="" title="">


<t:datagrid name="userSelectList" title="common.operation" actionUrl="userController.do?datagrid&deptId=${deptId}&selected=${selected}"
    fit="true" fitColumns="false" idField="id" queryMode="group" sortName="createDate" checkbox="true" sortOrder="desc">
	<t:dgCol title="common.id" field="id" hidden="true"></t:dgCol>
    <t:dgCol title="拼音索引" field="pysy" query="true" queryMode="single" width="120"></t:dgCol>
	<t:dgCol title="common.username" sortable="false" field="userName" query="true" width="120"></t:dgCol>
	<%--<t:dgCol title="common.department" field="TSDepart_id" query="true" replace="${departsReplace}"></t:dgCol>--%>
	<t:dgCol title="common.department" sortable="false" field="TSDepart.departname" query="false" width="120"></t:dgCol>
	<t:dgCol title="部门编号" sortable="false" hidden="true" field="TSDepart.id" query="false" width="120"></t:dgCol>
	<t:dgCol title="common.real.name" field="realName" query="true" width="120"></t:dgCol>
	<t:dgCol title="common.role" field="userKey" width="120" ></t:dgCol>

	<t:dgCol title="common.createby" field="createBy" hidden="true" width="120"></t:dgCol>
	<t:dgCol title="common.createtime" field="createDate" formatter="yyyy-MM-dd" hidden="true" width="120"></t:dgCol>
	<t:dgCol title="common.updateby" field="updateBy" hidden="true"></t:dgCol>
	<t:dgCol title="common.updatetime" field="updateDate" formatter="yyyy-MM-dd" hidden="true"></t:dgCol>
	<t:dgCol title="common.status" sortable="true" field="status" width="120" replace="common.active_1,common.inactive_0,super.admin_-1" ></t:dgCol>

 </t:datagrid>
</div>
 </div>
<script type="text/javascript">

/**组织机构树*/
$(document).ready(function(){
    $('#formtree').tree({
        animate : true,
        url : 'tBSpecialEvaluationController.do?departmentTree',
        checkbox: true,
        onClick : function(node) {
            $(node.target).find(".tree-checkbox").trigger("click");
        },onCheck: function() {
            var nodes = $('#formtree').tree('getChecked');

                var ids = '';
                var param = $('#userSelectList').datagrid('options').queryParams;
                for (var i = 0; i < nodes.length; i++) {
//                    if ($('#formtree').tree('isLeaf', nodes[i].target)) {
                        if (ids != '') {
                            ids += ","
                        }
                        ids += nodes[i].id;
//                    }

            }
            param["orgIds"] = ids;

            $('#userSelectList').datagrid('reload', param);

        }
        });
    $(document).keydown(function(event){

        if(event.keyCode==13){
            userSelectListsearch();
        }
    });

    });


</script>
<script type="text/javascript">

//    var windowapi = frameElement.api, W = windowapi.opener;
    function choose_297e201048183a730148183ad85c0001() {
        if (typeof(windowapi) == 'undefined') {
            $.dialog({content: 'url:departController.do?departSelect', zIndex: 2300, title: '<t:mutiLang langKey="common.department.list"/>', lock: true, width: 400, height: 350, left: '85%', top: '65%', opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_297e201048183a730148183ad85c0001, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        } else {
            $.dialog({content: 'url:departController.do?departSelect', zIndex: 2300, title: '<t:mutiLang langKey="common.department.list"/>', lock: true, parent: windowapi, width: 400, height: 350, left: '85%', top: '65%', opacity: 0.4, button: [
                {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_297e201048183a730148183ad85c0001, focus: true},
                {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
                }}
            ]});
        }
    }
    function clearAll_297e201048183a730148183ad85c0001() {
        if ($('#departname').length >= 1) {
            $('#departname').val('');
            $('#departname').blur();
        }
        if ($("input[name='departname']").length >= 1) {
            $("input[name='departname']").val('');
            $("input[name='departname']").blur();
        }
        $('#orgIds').val("");
    }
    function clickcallback_297e201048183a730148183ad85c0001() {
        iframe = this.iframe.contentWindow;
        var departname = iframe.getdepartListSelections('departname');
        if ($('#departname').length >= 1) {
            $('#departname').val(departname);
            $('#departname').blur();
        }
        if ($("input[name='departname']").length >= 1) {
            $("input[name='departname']").val(departname);
            $("input[name='departname']").blur();
        }
        var id = iframe.getdepartListSelections('id');
        if (id !== undefined && id != "") {
            $('#orgIds').val(id);
            $("input[name='orgIds']").val(id);
        }
    }
</script>
