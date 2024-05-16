<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="riskPostList" checkbox="true" pagination="true" fitColumns="true" title="风险列表" actionUrl="riskManageController.do?riskPostDatagrid&postRelId=${postRelId}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="岗位id"  field="post.id" hidden="true"  queryMode="single"  width="120" query="false"></t:dgCol>
            <t:dgCol title="单位"  field="postUnit.departname"  queryMode="single"  width="120" query="false"></t:dgCol>
            <t:dgCol title="岗位"  field="post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
            <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="风险等级"  field="riskLevel" dictionary="factors_level"	 queryMode="single"  width="120" query="true"></t:dgCol>
            <t:dgCol title="危害因素"  align="center" field="hazardFactortsPostNum" url="riskIdentificationController.do?wxysPostList&id={id}&load=detail" queryMode="group"  width="120" ></t:dgCol>
            <t:dgToolBar title="添加" icon="icon-add"  width="500"  funname="addRel" ></t:dgToolBar>

        </t:datagrid>
    </div>
</div>
<script type="text/javascript">

    window.top["reload_riskPostList"]=function(){
        $("#riskPostList").datagrid( "load");
    };

    function addRel() {
        var postRelId="${postRelId}";
        var ids = [];
        var rowsData = $('#riskPostList').datagrid('getSelections');
        if (!rowsData || rowsData.length==0) {
            tip('请选择需要添加的数据');
            return;
        }else{
            for ( var i = 0; i < rowsData.length; i++){
                ids.push(rowsData[i].id);
            }
        }
            $.ajax({
                url:'riskManageController.do?saveRelMyPostRisk',
                type: 'post',
                data:{
                    postRelId : postRelId,
                    ids : ids.join(',')
                },
                success:function (data) {
                    var data = $.parseJSON(data);
                    tip(data.msg);
                    window.top.reload_riskPostList.call();
                    window.top.reload_riskManagePostList.call();
                }
            });

    }
</script>