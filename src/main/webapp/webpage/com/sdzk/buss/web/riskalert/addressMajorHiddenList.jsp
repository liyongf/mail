<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBMajorHiddenDangerList" checkbox="false" pagination="true" fitColumns="false" title="" actionUrl="tBMajorHiddenDangerController.do?datagrid&hdLocation=${addressId}&queryHandleStatus=200,300,400,500" idField="id" fit="true" queryMode="group">
    <t:dgCol title="唯一标识"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="整改时限"  field="rectPeriod" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="隐患描述"  field="hdDesc"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="责任人"  field="dutyMan"    queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="责任单位"  field="dutyUnit.departname"    queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="隐患地点"  field="hdLocation" dictionary="t_b_address_info,id,address,where 1=1"   queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="危险源"  field="dangerId.hazard.hazardName"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
    <t:dgCol title="排查日期"  field="inveDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="隐患信息来源"  field="hdInfoSource"   dictionary="hiddenFrom"   queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="隐患等级"  field="hdLevel"  dictionary="hiddenLevel"  queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="隐患类别"  field="hdCate"  dictionary="hiddenCate"  queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="隐患专业"  field="hdMajor" query="false" dictionary="proCate_gradeControl"  queryMode="single"  width="120" align="center"></t:dgCol>
      <t:dgCol title="隐患类型"  field="hiddenType"  dictionary="hiddenType"  queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="隐患状态"  field="clStatus" dictionary="hdbiClStatus" query="false"  queryMode="single"  width="120" align="center"></t:dgCol>
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
    <t:dgCol title="销号日期"  field="cancelDate"  hidden="true"  formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>

   <%--<t:dgToolBar title="查看" icon="icon-search" url="tBMajorHiddenDangerController.do?goUpdate" funname="detail" operationCode="detail" ></t:dgToolBar>
   <t:dgToolBar title="五落实查看" icon="icon-search" url="tBMajorHiddenDangerController.do?goFiveImpl" funname="detail" operationCode="detailFiveImpl" ></t:dgToolBar>
   <t:dgToolBar title="查看历史" icon="icon-search" url="tBHiddenDangerHistoryController.do?list&fkHiddenInfoId" funname="noteHisttory" operationCode="detailHistory" ></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
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