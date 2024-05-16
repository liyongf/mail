<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
      <t:datagrid name="tBMajorHiddenDangerList" onDblClick="dblClickDetail" checkbox="false" pagination="true" fitColumns="false" title="重大隐患" actionUrl="tBDecisionAnalyseController.do?majorHiddenDangerDatagrid&statisticType=${statisticType}&yearMonth=${yearMonth}&bReport=${bReport}" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
          <t:dgCol title="唯一标识"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改时限"  field="rectPeriod" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="隐患描述"  field="hdDesc"    queryMode="group"  width="120"></t:dgCol>
          <t:dgCol title="责任人"  field="dutyMan"    queryMode="group"  width="120"></t:dgCol>
          <t:dgCol title="责任单位"  field="dutyUnit.departname"    queryMode="group"  width="120"></t:dgCol>
          <t:dgCol title="隐患地点"  field="hdLocation" dictionary="t_b_address_info,id,address,where 1=1"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="危险源"  field="dangerId.hazard.hazardName"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
          <t:dgCol title="排查日期"  field="inveDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="隐患信息来源"  field="hdInfoSource"   dictionary="hiddenFrom"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="隐患等级"  field="hdLevel"  dictionary="hiddenLevel"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="隐患类别"  field="hdCate"  dictionary="hiddenType"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="隐患专业"  field="hdMajor" query="true" dictionary="proCate_gradeControl"  queryMode="single"  width="120" align="center"></t:dgCol>
          <t:dgCol title="隐患状态"  field="clStatus" dictionary="hdbiClStatus" query="true"  queryMode="single"  width="120" align="center"></t:dgCol>
          <t:dgCol title="核查日期"  field="verifyDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="核查单位"  field="verifyUnit" hidden="true"    queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="核查情况"  field="verifyStatus"   hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="核查人员"  field="verifyMan"   hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改单位"  field="rectUnit"  hidden="true"  dictionary="t_s_depart,id,departname,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改责任人"  field="rectMan"  hidden="true"   dictionary="t_s_base_user,id,realname,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改措施"  field="rectMeasures"  hidden="true"    queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改完成日期"  field="rectTagartDt"  hidden="true"  formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="验收人"  field="acceptor"  hidden="true"   dictionary="t_s_base_user,id,realname,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="验收时间"  field="accepTime"  hidden="true"  formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="验收情况"  field="accepReport"  hidden="true"    queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="复查人"  field="reviewer"  hidden="true"   dictionary="t_s_base_user,id,realname,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="复查时间"  field="reviewTime"  hidden="true"  formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="复查情况"  field="reviewReport"  hidden="true"    queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="分局挂牌督办"  field="isLsSub"    formatterjs="formatIsListed" queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="省局挂牌督办"  field="isLsProv"    formatterjs="formatIsListed" queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="销号日期"  field="cancelDate"  hidden="true"  formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改目标到位情况"  field="goalAch"  hidden="true"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改目标到位日期"  field="goalAchDate"  hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改目标是否到位"  field="isGoalAchieve"   hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改责任到位说明"  field="respAch"   hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改责任到位日期"  field="respAchDate"  hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改责任是否到位"  field="isRespAchieve"   hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="治理措施到位情况"  field="measureAch"   hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="治理措施到位日期"  field="measureAchDate"  hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="治理措施是否到位"  field="isMeasureAchieve"   hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改资金到位情况"  field="fundAch"  hidden="true"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改资金到位日期"  field="fundAchDate"  hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改资金是否到位"  field="isFundAchieve"   hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改预案到位情况"  field="planAch"   hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改预案到位日期"  field="planAchDate"  hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="整改预案是否到位"  field="isPlanAchieve"   hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="纳入治理计划情况"  field="govePlan"   hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="纳入治理计划日期"  field="govePlanDate"  hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="纳入治理计划情况是否到位"  field="isGovePlanAchieve"   hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="创建人登录名"  field="createBy"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="更新人登陆名"  field="updateBy"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="更新时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>

          <t:dgToolBar title="查看" icon="icon-search" url="tBMajorHiddenDangerController.do?goUpdate" funname="detail"></t:dgToolBar>
          <t:dgToolBar title="五落实查看" icon="icon-search" url="tBMajorHiddenDangerController.do?goFiveImpl" funname="detail"></t:dgToolBar>
          <t:dgToolBar title="查看历史" icon="icon-search" url="tBHiddenDangerHistoryController.do?list&fkHiddenInfoId" funname="noteHisttory"></t:dgToolBar>
          <t:dgToolBar title="督办查看" icon="icon-search" url="tBMajorHiddenDangerController.do?supervise" funname="detail"></t:dgToolBar>
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