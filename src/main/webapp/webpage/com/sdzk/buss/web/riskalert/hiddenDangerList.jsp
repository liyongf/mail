<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBHiddenDangerExamList" checkbox="false" fitColumns="false" title="隐患列表" actionUrl="riskAlertManageController.do?hiddenDangerdatagrid&queryDate_begin=${queryDate_begin}&queryDate_end=${queryDate_end}&addressId=${addressId}&dutyunit=${dutyunit}&danger_id=${danger_id}&hiddenNature=${hiddenNature}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="日期"  field="examDate"  formatter="yyyy-MM-dd" queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="班次"  field="shift"  dictionary="workShift"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="地点"  field="address.address"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="检查人"  field="fillCardManNames"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="责任单位"  field="dutyUnit.departname"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="责任人"  field="dutyMan"    queryMode="group"  width="120"></t:dgCol>
   <%--<t:dgCol title="危险源"  field="dangerId.dangerName"    queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="风险等级"  field="dangerId.riskLevel"   dictionary="riskLevel" queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="风险类型"  field="dangerId.manageObjectEntity.manageObjectName"    queryMode="group"  width="120"></t:dgCol>--%>
   <t:dgCol title="问题描述"  field="problemDesc"    queryMode="group"  width="300"></t:dgCol>
   <t:dgCol title="隐患类别"  field="hiddenCategory"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="隐患等级"  field="hiddenNatureTemp" hidden="false"       queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="下井时间开始"  field="beginWellDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="下井时间结束"  field="endWellDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="经过路线"  field="routeLine" hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="处理类型"  field="dealType" hidden="true" replace="限期整改_1,现场处理_2"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="限期日期"  field="limitDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120"></t:dgCol>
<%--
   <t:dgCol title="限期班次"  field="limitShift"  dictionary="t_b_shift_manage,id,shift_name,where belong_mine = '${belongMine}' and is_used = 1"  queryMode="group"  width="120"></t:dgCol>
--%>
   <t:dgCol title="复查人"  field="reviewMan"   dictionary="t_s_base_user,id,realname,where 1=1" hidden="true"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="检查类型"  field="examType"  hidden="true"   queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="问题状态"  field="handleEntity.handlelStatus" dictionary="handelStatus"   queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="所属煤矿"  field="belongMine"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="检查类型"  field="examType" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgToolBar title="查看" icon="icon-search" funname="goDetail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
     function goDetail(){
         //获取选中条目
         var rowsData = $('#tBHiddenDangerExamList').datagrid('getSelections');
         if(rowsData == null || rowsData.length != 1){
             tip("请选择需要查看的条目");
         }else{
             var examType = rowsData[0].examType;
             var url = "tBHiddenDangerExamController.do?goUpdate&examType="+examType+"&load=detail&id="+rowsData[0].id;
             createdetailwindow("查看",url,950,600);
         }
     }
 $(document).ready(function(){
     $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","none");
     $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","none");

 		//给时间控件加上样式
 			$("#tBHiddenDangerExamListtb").find("input[name='beginWellDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='beginWellDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='endWellDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='endWellDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='limitDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='limitDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='examDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBHiddenDangerExamListtb").find("input[name='examDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 });
 
 </script>