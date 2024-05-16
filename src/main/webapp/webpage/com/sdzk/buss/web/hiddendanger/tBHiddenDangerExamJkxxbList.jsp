<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.jeecgframework.core.util.DicUtil" %>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<%
    String examType = (String)request.getAttribute("examType");
    String examTypeName = DicUtil.getTypeNameByCode("examType",examType);
%>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <br>
       <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px">
        <input  name="queryHandleStatus" value="ALL" type="hidden">
           <label>
            <input name="queryHandleStatusTem" type="radio" value="ALL" checked="checked">
									已上报
           </label>
           <label>
								<input name="queryHandleStatusTem" type="radio" value="ROLLBACK">
									已退回
           </label>
        </span>
    </div>
</div>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBHiddenDangerExamList" onDblClick="dblClickDetail" checkbox="true" fitColumns="false" title="隐患检查" actionUrl="tBHiddenDangerExamController.do?jkxxbDatagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="日期"  field="examDate"  formatter="yyyy-MM-dd" query="true"  queryMode="group"  sortable="false" width="120"></t:dgCol>
      <t:dgCol title="班次"  field="shift" hidden="false" dictionary="workShift" queryMode="single"  sortable="false" width="120"></t:dgCol>
      <t:dgCol title="危险源"  field="dangerId.hazard.hazardName"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>
      <t:dgCol title="地点"  field="address.address"    queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="检查人"  field="fillCardMan.realName"    queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="责任单位"  field="dutyUnit.departname"    queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="责任人"  field="dutyMan"    queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="问题描述"  field="problemDesc"    queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="隐患类别"  field="hiddenCategory"  hidden="true"  queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="隐患等级"  field="hiddenNature" hidden="true"       queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="下井时间开始"  field="beginWellDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="下井时间结束"  field="endWellDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="经过路线"  field="routeLine" hidden="true"   queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="处理类型"  field="dealType" replace="限期整改_1,现场处理_2"  queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="处理状态"  field="handleEntity.handlelStatus" dictionary="handelStatus"  queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="限期日期"  field="limitDate" formatter="yyyy-MM-dd"   queryMode="group"  sortable="false" width="120"></t:dgCol>
      <t:dgCol title="限期班次"  field="limitShift" hidden="false" dictionary="t_b_shift_manage,id,shift_name,where is_used = 1" queryMode="single"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="复查人"  field="reviewMan"   dictionary="t_s_base_user,id,realname,where 1=1" hidden="true"   queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="检查类型"  field="examType"  hidden="true"   queryMode="group"  sortable="false" width="120"></t:dgCol>
      <t:dgCol title="驳回备注"  field="rollBackRemarkTemp"  hidden="false" query="false" queryMode="group"  sortable="false" width="300"></t:dgCol>
      <t:dgCol title="创建人"  field="createBy"  hidden="true"   queryMode="group"  sortable="false" width="120"></t:dgCol>
   <t:dgCol title="检查类型"  field="examType"  hidden="true"   queryMode="group"  sortable="false" width="120"></t:dgCol>

      <t:dgToolBar  title="录入" icon="icon-add" url="tBHiddenDangerExamController.do?goAddJkxxb" funname="goAdd"  width="950" height="400" operationCode="add"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="icon-search" url="tBHiddenDangerExamController.do?goUpdate&examType=${examType}" funname="goDetail" width="950" height="400" operationCode="detail"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="icon-edit" url="tBHiddenDangerExamController.do?goUpdate&examType=${examType}" funname="goUpdate" width="950" height="400" operationCode="update"></t:dgToolBar>
      <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBHiddenDangerExamController.do?doBatchDel" funname="doDeleteALLSelect" operationCode="batchdelete"></t:dgToolBar>

  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">

     function dblClickDetail(rowIndex,rowData){
         var id=rowData.id;
         createdetailwindow("查看","tBHiddenDangerExamController.do?goUpdate&examType=${examType}&load=detail&id="+id,950,600);
     }


     function doDeleteALLSelect(){
         //获取选中条目
         var rowsData = $('#tBHiddenDangerExamList').datagrid('getSelections');
         if(rowsData == null || rowsData.length <= 0){
             tip("请选择需要删除的条目");
         }else{
             var isOk = true;
             var loginUser ="${loginUser}";
             for(var i =0;i<rowsData.length;i++){
                 var createBy = rowsData[i].createBy;
                 if(createBy != loginUser){
                     isOk = false;
                 }
             }
             if(isOk){
                 deleteALLSelect('批量删除','tBHiddenDangerExamController.do?doBatchDel','tBHiddenDangerExamList',null,null)
             }else{
                 tip("只能删除自己录入的数据");
             }

         }

     }
     function goUpdate(){
         //获取选中条目
         var rowsData = $('#tBHiddenDangerExamList').datagrid('getSelections');
         if(rowsData == null || rowsData.length != 1){
             tip("请选择需要编辑的条目");
         }else{
             var createBy = rowsData[0].createBy;
             var loginUser ="${loginUser}";
             if(createBy == loginUser){
                 update('编辑','tBHiddenDangerExamController.do?goUpdate&examType='+rowsData[0].examType,'tBHiddenDangerExamList',950,400)
             }else{
                 tip("只能编辑自己录入的数据");
             }

         }

     }

     function goDetail(){
         //获取选中条目
         var rowsData = $('#tBHiddenDangerExamList').datagrid('getSelections');
         if(rowsData == null || rowsData.length != 1){
             tip("请选择需要查看的条目");
         }else{
             addOneTab("<%=examTypeName%>-查看","tBHiddenDangerExamController.do?goUpdate&examType=${examType}&load=detail&id="+rowsData[0].id,"default");
         }

     }
     /**
     *跳转到选择检查类型界面
      */
     function goAdd(){
       //  createwindow("", "tBHiddenDangerExamController.do?goChooseJkxxbType",400,200);
         var okButton;
         if($("#langCode").val() == 'en') {
             okButton = "Ok";
         } else {
             okButton = "确定";
         }
         $.dialog({
             id: 'LHG1976D',
             title: "选择检查类型",
             max: false,
             min: false,
             drag: false,
             resize: false,
             content: 'url:tBHiddenDangerExamController.do?goChooseJkxxbType' ,
             lock:true,
             button : [ {
                 name : okButton,
                 focus : true,
                 callback : function() {
                     iframe = this.iframe.contentWindow;
                     var examType = $('#examType', iframe.document).val();
                     addOneTab("井口信息办录入","tBHiddenDangerExamController.do?goAdd&examType="+examType+"&from=jkxxb","default");
                     this.close();
                     return false;
                 }
             }],
             close: function(){
               //  window.location.href = actionurl;
             }
         });
     }

 $(document).ready(function(){
     $("a[iconcls='icon-reload']").hide();
     $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","none");
     $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","none");
     var datagrid = $("#tBHiddenDangerExamListtb");
     datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");
     $("input[name='queryHandleStatusTem']").change(function() {
         var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();

         $("input[name='queryHandleStatus']").val(selectedvalue);
         if(selectedvalue=="ALL"){//未整改显示，其他隐藏
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","none");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","none");
         }else{
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","");
         }

         tBHiddenDangerExamListsearch();


     });

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