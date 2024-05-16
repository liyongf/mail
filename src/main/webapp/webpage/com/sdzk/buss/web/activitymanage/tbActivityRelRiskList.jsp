<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-9-22
  Time: 下午3:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
    <div region="west" style="width: 200px;" title=" "  split="true">
        <ul id="formtree">
        </ul>
    </div>
    <div region="center" style="" title="">
        <t:datagrid name="tBDangerSourceList" checkbox="true" fitColumns="false" title="风险列表" actionUrl="tBDangerSourceController.do?activityDangerSourceDatagrid&activityid=${activityid}" idField="id" fit="true" queryMode="group" onLoadSuccess="loadSuccess">
            <t:dgCol title="唯一编号"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <%--<t:dgCol title="危险源来源"  field="origin" query="false" replace=" 通用_1,年度_2,专项_3"  width="120"></t:dgCol>--%>
            <t:dgCol title="辨识时间"  field="yeRecognizeYear" query="true" formatter="yyyy"  hidden="true"   queryMode="single" defaultVal='${year}'  width="60"></t:dgCol>
            <t:dgCol title="隐患描述"  field="yeMhazardDesc"  queryMode="single" query="true"  width="200"></t:dgCol>
            <t:dgCol title="危险源"  field="hazard.hazardName" query="false" queryMode="single"  width="60"></t:dgCol>
            <t:dgCol title="专业属性"  field="yeProfession" query="true"  dictionary="proCate_gradeControl"  queryMode="single"  width="60"></t:dgCol>
            <t:dgCol title="风险类型"  field="yeHazardCate" query="true"  dictionary="hazardCate"  queryMode="single"  width="60"></t:dgCol>
            <t:dgCol title="风险等级"  field="yeRiskGrade" dictionary="riskLevel" query="true"  queryMode="single"  width="60"></t:dgCol>
            <t:dgCol title="事故类型"  field="yeAccidentTemp"    queryMode="group"  width="60"></t:dgCol>
            <t:dgCol title="作业活动" field="activity.id" dictionary="t_b_activity_manage where is_delete='0',id,activity_name" query="true" queryMode="single" width="100" align="center"></t:dgCol>
            <c:if test="${initParam.les == 'no'}">
                <%--<t:dgCol title="可能性"  field="yeProbability" dictionary="probability"   queryMode="group"  width="120"></t:dgCol>--%>
                <%--<t:dgCol title="损失"  field="yeCost"  dictionary="hazard_fxss"  queryMode="group"  width="120"></t:dgCol>--%>
                <t:dgCol title="风险值"  field="riskValue"    queryMode="group"  width="120"></t:dgCol>
            </c:if>
            <c:if test="${initParam.les == 'yes'}">
                <%--<t:dgCol title="风险可能性"  field="lecRiskPossibility" dictionary="lec_risk_probability" queryMode="group"  width="120"></t:dgCol>--%>
                <%--<t:dgCol title="风险损失"  field="lecRiskLoss"   dictionary="lec_risk_loss" queryMode="group"  width="120"></t:dgCol>--%>
                <%--<t:dgCol title="暴露在风险中的频率"  field="lecExposure"   dictionary="lec_exposure" queryMode="group"  width="120"></t:dgCol>--%>
                <t:dgCol title="风险值"  field="lecRiskValue"    queryMode="group"  width="45"></t:dgCol>
            </c:if>
            <%--<t:dgCol title="风险管控措施"  field="manageMeasure"    queryMode="group"  width="120"></t:dgCol>--%>
            <t:dgCol title="风险描述"  field="yePossiblyHazard"    queryMode="single" query="true"  width="200"></t:dgCol>
            <t:dgCol title="管控标准来源"  field="docSource"  query="true"  queryMode="single"  width="200"></t:dgCol>
            <t:dgCol title="标准内容"  field="yeStandard"  query="true"  queryMode="single"  width="200"></t:dgCol>
            <t:dgToolBar title="查看" icon="icon-search" url="tBDangerSourceController.do?goDetail" funname="detail" width="850" height="450"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>

<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
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
                var param=$('#tBDangerSourceList').datagrid('options').queryParams;
                for(var i=0; i<nodes.length;i++){
                    if ($('#formtree').tree('isLeaf', nodes[i].target)) {
                        if(ids!=''){
                            ids+=","
                        }
                        ids+=nodes[i].id;
                    }
                }
                param["ids"]=ids;
                $('#tBDangerSourceList').datagrid('reload',param);
            }
        });
        $("#tBDangerSourceListForm").find("input[name='yeRecognizeYear']").attr("class", "Wdate").click(function () {
            WdatePicker({
                dateFmt: 'yyyy'
            });
        });
    });
</script>
<script>
    function addRelFunction (){
        var ids = [];
        var rows = $('#tBDangerSourceList').datagrid('getSelections');
        if(rows.length <=0){
            tip("请选择要关联的风险");
        }else{
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i].id);
            }
            ids.join(',');
            $.ajax({
                url: "tBDangerSourceController.do?saveAddressChooseDanger&ids="+ids+"&addressId=${excludeId}",
                type: 'POST',
                error: function(){
                },
                success: function(data){
                    data = $.parseJSON(data);
                    tip(data.msg);
                    window.top.reload_addressDangerSourceList.call();
                    $('#tBDangerSourceList').datagrid('reload');
                }
            });
        }
    }
    function searchReset() {
        document.getElementById("tBDangerSourceListForm").reset();
        var queryParams = $('#tBDangerSourceList').datagrid('options').queryParams;
        $('#tBDangerSourceListtb').find('*').each(function() {
            queryParams[$(this).attr('name')] = $(this).val();
        });
        $('#tBDangerSourceList').datagrid({
            url: 'tBDangerSourceController.do?activityDangerSourceDatagrid&activityid=${activityid}&field=id,id_begin,id_end,yeRecognizeYear,hazard.hazardName,yeProfession,yeHazardCate,yeRiskGrade,yeAccidentTemp,yeAccidentTemp_begin,yeAccidentTemp_end,activity.id,riskValue,riskValue_begin,riskValue_end,yeMhazardDesc,yePossiblyHazard,docSource,yeStandard,',
            pageNumber: 1
        });
    }
</script>
