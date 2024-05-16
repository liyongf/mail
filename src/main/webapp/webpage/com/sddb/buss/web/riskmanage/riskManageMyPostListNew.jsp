<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px" >
        <t:datagrid name="riskManageMyPostList" checkbox="true" pagination="true" fitColumns="false" title="我的岗位管控清单" actionUrl="riskManageController.do?datagridMyPost" idField="id" fit="true" queryMode="group" onLoadSuccess="loadSuccessFun">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="岗位"  field="post.postName"  queryMode="single"  width="100"></t:dgCol>
            <t:dgToolBar title="选择我的岗位" icon="icon-add"  url="riskManageController.do?goAddMyPost" funname="add" operationCode="add"></t:dgToolBar>
            <t:dgToolBar title="编辑我的岗位" icon="icon-edit" url="riskManageController.do?goUpdateMyPost" funname="update" operationCode="update" ></t:dgToolBar>
            <t:dgCol title="common.operation" field="opt" width="150"></t:dgCol>
            <t:dgFunOpt funname="addRisk(id)" title="添加风险" urlclass="ace_button" urlStyle="background-color:#18a689;"  urlfont="fa-list" operationCode="addRisk"></t:dgFunOpt>
        </t:datagrid>
    </div>
</div>
<div region="east" style="width: 1000px;" split="true">
    <div tools="#tt" class="easyui-panel" title='已添加风险' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>
</div>

<script type="text/javascript">
    function loadSuccessFun() {
        var rows = $("#riskManageMyPostList").datagrid("getRows");
        addRisk(rows[0].id);
    }
   function add(){
        $.ajax({
            type: 'POST',
            url: 'riskManageController.do?getMyPost',
            dataType:"json",
            async:false,
            data: {
            },
            success:function(data){
                var result = jQuery.parseJSON(data);
                if("1" == result){
                    gridname="riskManageMyPostList";
                    createwindow("录入", "riskManageController.do?goAddMyPost");
                }else{
                    tip("我的岗位已选择");
                }
            },
            error:function(data){
            }
        });
    }

    function update(){
        var rows = $("#riskManageMyPostList").datagrid('getSelections');
        if(rows.length!=1){
            tip("请选择要编辑得数据");
        }else{
            $.dialog.confirm('编辑岗位会清空之前添加的风险，确定更换岗位吗?', function() {
                createwindow("编辑", "riskManageController.do?goUpdateMyPost&id="+rows[0].id+"");
            });
        }
    }

    function addRisk(id) {

        $("#function-panel").panel(
            {
                title :'已关联风险',
                href:"riskManageController.do?goPostRiskList&postRelId=" + id

            }
        );
    }
</script>