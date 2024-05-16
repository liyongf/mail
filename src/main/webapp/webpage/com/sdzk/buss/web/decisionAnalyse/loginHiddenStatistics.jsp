<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="loginHiddenStatisticsList" checkbox="true" onDblClick="dbClick" pagination="true" fitColumns="true" title="管理人员管控情况分析" actionUrl="tBDecisionAnalyseController.do?loginHiddenStatisticsDatagrid" idField="id" fit="true" queryMode="group" >
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="人员姓名"  field="realName"  queryMode="single" sortable="false"  width="120" query="false"></t:dgCol>
            <t:dgCol title="登录次数"  field="loginNum"  queryMode="group"  width="120" align="center" ></t:dgCol>
            <t:dgCol title="录入隐患数量"  field="hiddenNum"   queryMode="group"  width="120" align="center" ></t:dgCol>
            <t:dgCol title="检查危害因素次数"  field="hazardNum"     queryMode="group"  width="120" align="center" ></t:dgCol>
            <t:dgCol title="月份"  field="createDate" formatter="yyyy-MM"  queryMode="single" sortable="false" hidden="true" query="true" width="120" ></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $("#yearMonth").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
        var datagrid = $("#loginHiddenStatisticsListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums").html()).attr("style","text-align: center;");
        $("#loginHiddenStatisticsListForm").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:30px;width:156px;").click(function () {
            WdatePicker({
                dateFmt: 'yyyy-MM',
            });
        });
    });
</script>