<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="managePostRiskList" checkbox="true" pagination="true" fitColumns="true" title="管控风险列表" actionUrl="riskManageTaskController.do?postRiskDatagrid" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险id"  field="risk.id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="岗位id"  field="risk.post.id" hidden="true"  queryMode="single"  width="120" query="false"></t:dgCol>
            <t:dgCol title="单位"  field="risk.postUnit.departname"  queryMode="single"  width="120" query="false"></t:dgCol>
            <t:dgCol title="岗位"  field="risk.post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
            <t:dgCol title="风险类型"  field="risk.riskType" dictionary="risk_type" query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="风险等级"  field="risk.riskLevel" dictionary="factors_level"	 query="true"   width="120" ></t:dgCol>
            <t:dgCol title="危害因素"  align="center" field="risk.hazardFactortsPostNum"  url="riskIdentificationController.do?wxysPostList&riskManageId={id}&load=detail"  queryMode="group"  width="120" ></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">

    function detail(title,url, id,width,height) {
        var rowsData = $('#'+id).datagrid('getSelections');

        if (!rowsData || rowsData.length == 0) {
            tip('请选择查看项目');
            return;
        }
        if (rowsData.length > 1) {
            tip('请选择一条记录再查看');
            return;
        }

        addOneTab("风险查看","riskIdentificationController.do?goAdd&identificationType="+rowsData[0]["risk.identificationType"]+"&addressId="+rowsData[0]["risk.address.id"]+"&load=detail","default");
//        url += '&load=detail&id='+rowsData[0]["risk.id"];
//        createdetailwindow(title,url,width,height);
    }


    var api = frameElement.api, W = api.opener, D = W.document;

    api.button(
            {
                name: '保存',
                callback: function(){
                    var ids = [];
                    var rows = $('#managePostRiskList').datagrid('getSelections');
                    if(rows.length <=0){
                        api.close();
                    }else{
                        for (var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        ids.join(',');
                        $.ajax({
                            url: "riskManageTaskController.do?saveRelPostRisk&postTaskAllId=${postTaskAllId}&ids="+ids+"&tt="+new Date(),
                            type: 'POST',
                            beforeSend: function(data){
                                $.messager.progress({
                                    text : "正在保存数据......",
                                    interval : 100
                                });
                            },
                            error: function(){
                            },
                            success: function(data){
                                data = $.parseJSON(data);
                                W.showMsg(data.msg);
                                W.reloadP();
                                api.close();
                            }
                        });
                    }
                    return false;

                },
                focus: true
            },
            {
                name: '取消'
            }
    );
</script>