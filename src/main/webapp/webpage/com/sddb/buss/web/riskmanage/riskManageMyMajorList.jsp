<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="west" style="padding:0px;border:0px;width: 450px" split="true" >
        <t:datagrid name="riskManageMyMajorList" checkbox="true" pagination="true" fitColumns="false" title="我的${riskManager}管控清单" actionUrl="riskManageController.do?datagridMyMajor&manageType=${manageType}" idField="id" fit="true" queryMode="group" onLoadSuccess="loadSuccessFun">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="${riskManager}"  field="majorNameTemp"  queryMode="single"  width="100"></t:dgCol>
            <t:dgToolBar title="选择我的${riskManager}" icon="icon-add"  url="riskManageController.do?goAddMyMajor" funname="add" operationCode="add"></t:dgToolBar>
            <t:dgToolBar title="编辑我的${riskManager}" icon="icon-edit" url="riskManageController.do?goUpdateMyMajor" funname="update" operationCode="update" ></t:dgToolBar>
            <t:dgCol title="common.operation" field="opt" width="150"></t:dgCol>
            <t:dgFunOpt funname="addRisk(id)" title="添加风险" urlclass="ace_button" urlStyle="background-color:#18a689;"  urlfont="fa-list" operationCode="addRisk"></t:dgFunOpt>
        </t:datagrid>
    </div>
</div>
<div region="center">
    <div tools="#tt" class="easyui-panel" title='已添加风险' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>
</div>

<script type="text/javascript">
    window.top["reload_riskManageMyMajorList"]=function(){
        $("#riskManageMyMajorList").datagrid( "load");
    };
    function loadSuccessFun() {
        var rows = $("#riskManageMyMajorList").datagrid("getRows");
        addRisk(rows[0].id);
    }
   function add(){
        $.ajax({
            type: 'POST',
            url: 'riskManageController.do?getMyMajor&manageType=${manageType}',
            dataType:"json",
            async:false,
            data: {
            },
            success:function(data){
                var result = jQuery.parseJSON(data);
                if("1" == result){
                    gridname="riskManageMyMajorList";
                    createwindow("录入", "riskManageController.do?goAddMyMajor&manageType=${manageType}");
                }else{
                    tip("我的${riskManager}已选择");
                }
            },
            error:function(data){
            }
        });
    }

    function update(){
        var rows = $("#riskManageMyMajorList").datagrid('getSelections');
        if(rows.length!=1){
            tip("请选择要编辑得数据");
        }else{
            $.dialog.confirm('编辑${riskManager}会清空之前添加的风险，确定更换${riskManager}吗?', function() {
                createwindow("编辑", "riskManageController.do?goUpdateMyMajor&manageType=${manageType}&id="+rows[0].id+"");
            });
        }
    }

    function addRisk(id) {

        $("#function-panel").panel(
            {
                title :'已关联风险',
                href:"riskManageController.do?goRiskList&manageType=${manageType}&rel=rel&majorRelId=" + id
            }
        );
    }
</script>