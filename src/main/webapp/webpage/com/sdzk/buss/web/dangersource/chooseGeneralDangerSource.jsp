<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
    <%--    <div region="west" style="width: 200px;" title=" "  split="true">
            <ul id="formtree">
            </ul>
        </div>--%>
    <div region="center" style="padding:1px;">
        <t:datagrid name="tBGeneralDangerSourceList" checkbox="true" fitColumns="false" title="通用危险源列表" actionUrl="tBDangerSourceController.do?initChooseGeneralDanger" idField="id" fit="true" queryMode="group"  onLoadSuccess="loadSuccess">
            <t:dgCol title="唯一编号" field="id" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
            <t:dgCol title="辨识时间"  field="yeRecognizeYear" query="false" formatter="yyyy"  hidden="true"   queryMode="single" defaultVal='${year}'  width="60"></t:dgCol>
            <t:dgCol title="辨识时间" field="yeRecognizeTime" query="false" formatter="yyyy-MM-dd"  hidden="true" queryMode="group" width="90" align="center"></t:dgCol>
            <t:dgCol title="隐患描述" field="yeMhazardDesc" queryMode="single" query="true" width="180"></t:dgCol>
            <t:dgCol title="专业" field="yeProfession" query="true" dictionary="proCate_gradeControl" queryMode="single"
                     width="60" align="center"></t:dgCol>
            <%--<t:dgCol title="危险源名称" field="hazard.hazardName" hidden="true"  queryMode="single" width="80" align="center"></t:dgCol>--%>
            <t:dgCol title="伤害类别" field="damageType" query="true" dictionary="danger_Category" queryMode="single"
                     width="80" align="center"></t:dgCol>
            <t:dgCol title="风险描述" field="yePossiblyHazard" queryMode="single" query="true" width="180"></t:dgCol>
            <t:dgCol title="事故类型" field="yeAccidentTemp" queryMode="group" width="80" align="center"></t:dgCol>
            <t:dgCol title="作业活动" field="activity.id" dictionary="t_b_activity_manage where is_delete='0',id,activity_name" query="false" hidden="true"  queryMode="single" width="100" align="center"></t:dgCol>
            <%--<t:dgCol title="是否重大风险" field="isMajor" replace="是_1,否_0" queryMode="group" width="80"
                     align="center"></t:dgCol>--%>
            <c:if test="${initParam.les == 'no'}">
                <t:dgCol title="可能性" field="yeProbability" dictionary="probability" queryMode="group" width="50" align="center"></t:dgCol>
                <t:dgCol title="损失" field="yeCost" dictionary="hazard_fxss" queryMode="group" width="50" align="center"></t:dgCol>
                <t:dgCol title="风险值" field="riskValue" queryMode="group" width="50" align="center"></t:dgCol>
            </c:if>
            <c:if test="${initParam.les == 'yes'}">
                <t:dgCol title="可能性" field="lecRiskPossibility" dictionary="lec_risk_probability" queryMode="group"
                         width="50" align="center"></t:dgCol>
                <t:dgCol title="损失" field="lecRiskLoss" dictionary="lec_risk_loss" queryMode="group" width="50" align="center"></t:dgCol>
                <t:dgCol title="暴露频率" field="lecExposure" dictionary="lec_exposure" queryMode="group"
                         width="50" align="center"></t:dgCol>
                <t:dgCol title="风险值" field="lecRiskValue" queryMode="group" width="50" align="center"></t:dgCol>
            </c:if>
            <t:dgCol title="风险等级" field="yeRiskGrade" dictionary="riskLevel" query="true" queryMode="single" width="70"
                     align="center"></t:dgCol>
            <t:dgCol title="风险类型" field="yeHazardCate" query="true" dictionary="hazardCate" queryMode="single" width="60"
                     align="center"></t:dgCol>
            <t:dgCol title="管控标准来源" field="docSource" queryMode="single" query="true" width="100" align="center"></t:dgCol>
            <t:dgCol title="章节条款" field="sectionName" queryMode="single" query="false" width="100" align="center"></t:dgCol>
            <t:dgCol title="标准内容" field="yeStandard" queryMode="single" query="true" width="180" align="center"></t:dgCol>
            <t:dgCol title="管控措施" field="manageMeasure" queryMode="group" width="180" align="center"></t:dgCol>
            <%--<t:dgCol title="责任岗位" field="post.postName" queryMode="single" hidden="true" width="100" align="center"></t:dgCol>--%>
            <t:dgCol title="隐患等级" field="hiddenLevel" dictionary="hiddenLevel" queryMode="single" query="true" width="100" align="center"></t:dgCol>
            <t:dgCol title="罚款金额(元)" field="fineMoney" queryMode="group" width="80" align="center"></t:dgCol>
            <t:dgCol title="风险状态" field="auditStatus" replace="待上报_1,审核中_2,审核退回_3,闭环_4" queryMode="single"  hidden="true" width="60"
                     align="center"></t:dgCol>
            <%--<t:dgCol title="危险源来源" field="origin" replace="通用_1,年度_2,专项_3" queryMode="group" width="80"
                     align="center"></t:dgCol>--%>
            <t:dgToolBar title="查看" icon="icon-search" url="tBDangerSourceController.do?goGeneralDangerDetail" funname="detail" width="850" height="450"></t:dgToolBar>

        </t:datagrid>
    </div>
</div>

<div style="display: none">
    <t:formvalid formid="formobj" layout="div" dialog="true" action="" beforeSubmit="setUserIds">
    </t:formvalid>
</div>

<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
<script src = "webpage/com/sdzk/buss/web/dangersource/js/tBDangerSourceList.js"></script>
<script type="text/javascript">
    function loadSuccess(){
        var yeRecognizeYear =  $("input[name='yeRecognizeYear']").val();
        if (yeRecognizeYear==null||yeRecognizeYear==''){
            $("input[name='yeRecognizeYear']").val("${year}")
        }
    }
    $(document).ready(function(){
        $('#formtree').tree({
            animate : true,
            url : 'tBAddressInfoController.do?dangerSourceTree',
            checkbox: true,
            onClick : function(node) {
                $(node.target).find(".tree-checkbox").trigger("click");
            },onCheck: function(){
                var nodes = $('#formtree').tree('getChecked');
                var ids='';
                var param=$('#tBGeneralDangerSourceList').datagrid('options').queryParams;
                for(var i=0; i<nodes.length;i++){
                    if ($('#formtree').tree('isLeaf', nodes[i].target)) {
                        if(ids!=''){
                            ids+=","
                        }
                        ids+=nodes[i].id;
                    }
                }
                param["ids"]=ids;
                $('#tBGeneralDangerSourceList').datagrid('reload',param);
            }
        });
        $("#tBGeneralDangerSourceListForm").find("input[name='yeRecognizeYear']").attr("class", "Wdate").click(function () {
            WdatePicker({
                dateFmt: 'yyyy'
            });
        });
    });
</script>
<script>
    var api = frameElement.api, W = api.opener, D = W.document;

    api.button(
            {
                name: '保存',
                callback: function(){
                    var ids = [];
                    var rows = $('#tBGeneralDangerSourceList').datagrid('getSelections');
                    if(rows.length <=0){
                        api.close();
                    }else{
                        for (var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        ids.join(',');
                        $.ajax({
                            url: "tBDangerSourceController.do?saveChooseGeneralDanger&ids="+ids+"&tt="+new Date(),
                            type: 'POST',

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