<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 17-8-18
  Time: 下午4:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="tBMajorHiddenDangerList" onDblClick="dblClickDetail" checkbox="false" pagination="true" fitColumns="false" title="" actionUrl="tBHiddenDangerCheckPlanCountController.do?planYetReplyListDatagrid&beginMonth=${beginMonth}&endMonth=${endMonth}&type=${type}" idField="id" fit="true" queryMode="group" >
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="计划开始时间"  field="startTime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="计划结束时间"  field="endTime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险点类型"  field="riskPointType"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="要求完成时间"  field="completeTime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="受理单位"  field="acceptDepartName"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="受理人"  field="acceptUserRealName"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="受理时间"  field="acceptTime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="排查时间"  field="investigateTime" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="排查描述"  field="investigateDesc"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="状态"  field="status"    queryMode="group"  width="120"></t:dgCol>
            <t:dgToolBar title="查看" icon="icon-search" url="tBInvestigatePlanController.do?goUpdate" funname="detail"></t:dgToolBar>

        </t:datagrid>
    </div>
</div>
<script src = "webpage/com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDangerList.js"></script>
<script type="text/javascript">

    $(document).ready(function(){
    });
    function dblClickDetail(rowIndex,rowData){
        var id=rowData.id;
        var url = "tBMajorHiddenDangerController.do?goUpdate&load=detail&id="+id;
        createdetailwindow("查看",url,950,400);
    }
    function formatIsListed (value, rec, index) {
        if (value == '1') {
            return '已挂牌督办';
        } else {
            return '未挂牌督办';
        }
    }

    /**
     * 查看处理历史
     * */
    function noteHisttory(){
        //取得选中条目
        var rows = $("#tBMajorHiddenDangerList").datagrid('getSelections');
        if(rows== null || rows.length < 1){
            tip("请选择查看项目!!!");
        }else if(rows.length > 1){
            tip("请选择一条记录再查看!!!");
        }
        else{
            openwindow("查看历史","tBHiddenDangerHistoryController.do?list&fkHiddenInfoId="+rows[0].id,"tBMajorHiddenDangerList",700,600);
        }
    }
</script>
