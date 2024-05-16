<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">检查人：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="fillCardManSelect" style="width: 130px;height: 15px"></div>
                <input class="inuptxt" type="hidden" style="width: 100px" name="hiddenDanger.fillCardMan.id">
             </span>
        </span>

        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="问题地点">问题地点：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="addressSelect" style="width: 130px;height: 15px"></div>
                 <input id="tBAddressInfoEntity_id" type="hidden" name="tBAddressInfoEntity_id">
             </span>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBHiddenDangerHandleList" onDblClick="dblClickDetail" onLoadSuccess="replaceProblemDesc" checkbox="false" pagination="true" fitColumns="false" title="日常隐患列表" actionUrl="tBDecisionAnalyseController.do?hiddenDangerDatagrid&statisticType=${statisticType}&yearMonth=${yearMonth}&unitId=${unitId}" idField="id" fit="true" queryMode="group" sortName="hiddenDanger.examDate" sortOrder="desc">
      <t:dgCol title="发现时间"  field="hiddenDanger.examDate"  formatter="yyyy-MM-dd"  query="false" queryMode="group"  sortable="false" width="80" align="center"></t:dgCol>
      <t:dgCol title="检查班次"  field="hiddenDanger.shift" hidden="false" dictionary="workShift" query="true"  queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
      <%--<t:dgCol title="危险源"  field="hiddenDanger.dangerId.hazard.hazardName"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>--%>
      <t:dgCol title="问题地点"  field="hiddenDanger.address.address"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="责任部门"  field="hiddenDanger.dutyUnit.departname" query="false" queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
      <t:dgCol title="责任人"  field="hiddenDanger.dutyMan" queryMode="group" sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="检查人"  field="hiddenDanger.fillCardManNames" queryMode="single"  sortable="false" width="100" align="center"></t:dgCol>
      <t:dgCol title="信息来源"  field="hiddenDanger.manageType"  query="true" dictionary="manageType" queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="隐患等级"  field="hiddenDanger.hiddenNature"  query="true" dictionary="hiddenLevel"  queryMode="single"  sortable="false" width="70" align="center"></t:dgCol>

      <t:dgCol title="问题描述"  field="hiddenDanger.problemDesc"    queryMode="group"  sortable="false" width="300"></t:dgCol>
      <t:dgCol title="处理方式"  field="hiddenDanger.dealType" replace="限期整改_1,现场处理_2" width="80" align="center"></t:dgCol>
      <t:dgCol title="限期日期"  field="hiddenDanger.limitDate" formatter="yyyy-MM-dd" width="80" align="center"></t:dgCol>
<%--
      <t:dgCol title="限期班次"  field="hiddenDanger.limitShift" dictionary="workShift"  width="80" align="center"></t:dgCol>
--%>
      <t:dgCol title="整改日期"  field="modifyDate" formatter="yyyy-MM-dd" query="true"  queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
      <t:dgCol title="整改人"  field="modifyMan"  dictionary="t_s_base_user,id,realname,where 1=1"   queryMode="group"  sortable="false" width="100" align="center"></t:dgCol>
      <t:dgCol title="复查日期"  field="reviewDate" formatter="yyyy-MM-dd"   queryMode="group"  sortable="false" width="80" align="center"></t:dgCol>
      <t:dgCol title="复查人"  field="reviewMan"  dictionary="t_s_base_user,id,realname,where 1=1"  queryMode="group"  sortable="false" width="100" align="center"></t:dgCol>
      <t:dgCol title="处理状态"  field="handlelStatus" dictionary="handelStatus" query="true"  queryMode="single"  sortable="false" width="60" align="center"></t:dgCol>

      <t:dgCol title="驳回备注"  field="rollBackRemark"  hidden="false" query="false" queryMode="group"  sortable="false" width="300"></t:dgCol>
      <t:dgCol title="录入人"  field="createName"   queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="录入日期"  field="createDate" formatter="yyyy-MM-dd"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>


      <t:dgToolBar title="查看" icon="icon-search" url="tBDangerSourceController.do?goDetail" funname="goDetail" width="850" height="450" ></t:dgToolBar>

  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">

     function dblClickDetail(rowIndex,rowData){
         var id=rowData.id;
         var url = "tBHiddenDangerHandleController.do?goUpdate&load=detail&id="+id;
         createdetailwindow("查看",url,950,400);
     }
     $(function() {
         var datagrid = $("#tBHiddenDangerHandleListtb");
         datagrid.find("form[id='tBHiddenDangerHandleListForm']>span:first").before($("#tempSearchColums div[name='departSearchColums']").html());
         datagrid.find("form[id='tBHiddenDangerHandleListForm']>span:last").before($("#tempSearchColums div[name='searchColums']").html());
         $("#tempSearchColums").empty();
         //getDepartMagicSuggest($("#departnameSuggest"), $("#departname"));
         getAddressMagicSuggest($("#addressSelect"), $("[name='tBAddressInfoEntity_id']"));
         getUserMagicSuggest($("#fillCardManSelect"), $("[name='hiddenDanger.fillCardMan.id']"));
     });

    function goDetail(){
        var rowsData = $('#tBHiddenDangerHandleList').datagrid('getSelections');
        if(rowsData == null || rowsData.length != 1){
            tip("请选择一条需要查看的条目");
        }else{
            var url = 'tBHiddenDangerHandleController.do?goUpdate&load=detail&id='+rowsData[0]["id"];
            createdetailwindow("查看",url,850,450);
        }
    }

     function replaceProblemDesc(){
         var problemDescCells = $("td[field='hiddenDanger.problemDesc']:gt(0)");
         problemDescCells.each(function(){
             var problemDesc = $(this).children().text();
             if(problemDesc.indexOf("\\r\\n") >= 0){
                 problemDesc = problemDesc.replace(/[\\r\\n]/g, '');
                 $(this).children().empty();
                 $(this).children().append(problemDesc);
             }
         });
     }
 </script>