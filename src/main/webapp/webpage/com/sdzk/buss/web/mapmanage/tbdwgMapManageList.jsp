<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="tBdwgMapManageList" checkbox="true" pagination="true" fitColumns="true" title="矿图列表" actionUrl="tbMapManageController.do?datagrid&uploadType=${uploadType}" idField="id" fit="true" queryMode="group" sortName="uploadTime" sortOrder="desc">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="上传时间"  field="uploadTime" formatter="yyyy-MM-dd hh:mm:ss" query="true"  queryMode="group" sortable="false" width="80" align="center"></t:dgCol>
            <t:dgCol title="上传人"  field="uploadName" queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="uploadBy"  field="uploadBy" hidden="true" queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="状态"  field="status" replace="未处理_0,处理中_1,处理完成_2" queryMode="group"  width="120" align="center"></t:dgCol>

            <t:dgToolBar title="矿图上传" icon="icon-add" url="tbMapManageController.do?goUploaddwg" funname="add" operationCode="add"></t:dgToolBar>
            <%--<t:dgToolBar title="设置为当前版本"  icon="icon-edit" url="tbMapManageController.do?doUpdate" funname="setUsed" operationCode="setused" ></t:dgToolBar>--%>
            <t:dgToolBar title="批量删除"  icon="icon-remove" url="tbMapManageController.do?doDel" funname="goDel" operationCode="batchdelete" ></t:dgToolBar>
        </t:datagrid>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function(){
        $("#tBMapManageListForm").find("input[name='uploadTime_begin']").attr("class", "Wdate").attr("style", "height:30px;width:156px;").click(function () {
            WdatePicker({
                dateFmt: 'yyyy-MM-dd'
            });
        });
        $("#tBMapManageListForm").find("input[name='uploadTime_end']").attr("class", "Wdate").attr("style", "height:30px;width:156px;").click(function () {
            WdatePicker({
                dateFmt: 'yyyy-MM-dd'
            });
        });
    });
    
  /* function setUsed() {
        var rows = $("#tBMapManageList").datagrid('getSelections');
        if (rows.length < 1) {
            tip("请先选择一条数据");
        }else if (rows.length < 1) {
            tip("只能选择一条数据作为当前矿图版本");
        } else {
            $.dialog.confirm('确定将矿图替换为选中的版本吗?', function(r) {
                this.close();
                if (r) {
                    var id = rows[0].id;

                    $.ajax({
                        type: 'POST',
                        url: 'tbMapManageController.do?doUpdate',
                        dataType:"json",
                        async:true,
                        cache: false,
                        data: {
                            id:id
                        },
                        success:function(data){
                            var msg = data.msg;
                            tip(msg);
                            reloadTable();
                        },
                        error:function(data){
                        }
                    });
                }
            });
        }
    }*/

    function goDel() {
        var rows = $("#tBdwgMapManageList").datagrid('getSelections');
        if (rows.length < 1) {
            tip("请选择要删除的数据");
        } else {
            var idsTemp = new Array();
            for (var i = 0; i < rows.length; i++) {
                if(rows[i].isUsed != '1'){
                    idsTemp.push(rows[i].id);
                }
            }
            var idt = idsTemp.join(",");

            $.dialog.confirm('确定选中的矿图删除吗？（正在使用的矿图不会被删除）', function(r) {
                this.close();
                if (r) {
                    $.ajax({
                        type: 'POST',
                        url: 'tbMapManageController.do?doDel',
                        dataType:"json",
                        async:true,
                        cache: false,
                        data: {
                            ids:idt
                        },
                        success:function(data){
                            var msg = data.msg;
                            tip(msg);
                            reloadTable();
                        },
                        error:function(data){
                        }
                    });
                }
            });
        }
    }
</script>