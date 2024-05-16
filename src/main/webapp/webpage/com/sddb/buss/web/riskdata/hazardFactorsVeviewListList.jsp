<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <br>
        <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px">
        <input  name="queryHandleStatus" value="0" type="hidden">
           <label>
               <input name="queryHandleStatusTem" type="radio" value="false" checked="checked"> 待审批
           </label>
           <label>
                <input name="queryHandleStatusTem" type="radio" value="true">已审批</label>

        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hazardFactorsList" checkbox="true" pagination="true" fitColumns="false" title="危害因素列表" actionUrl="hazardFactorsController.do?reviewDatagrid" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" queryMode="single"  width="120" query="true"></t:dgCol>
   <t:dgCol title="专业"  field="major" query="true" dictionary="major" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="设备"  field="equipment"  queryMode="single"  width="100" ></t:dgCol>
      <t:dgCol title="作业活动"  field="activity"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="危害因素等级"  field="riskLevel" query="true" dictionary="factors_level" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="危害因素"  field="hazardFactors" formatterjs="valueTitle"  queryMode="group"  width="200"></t:dgCol>
   <t:dgCol title="管控措施"  field="manageMeasure" formatterjs="valueTitle" queryMode="group"  width="400" ></t:dgCol>
      <t:dgCol title="管控标准来源"  field="docSource"  queryMode="single"  width="120" ></t:dgCol>
      <t:dgCol title="章节条款"  field="sectionName"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="信息来源"  field="from" query="true" dictionary="identifi_from" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="状态"  field="status" replace="待审批_1,退回_2,审批通过_3"  width="120" query="true"></t:dgCol>
      <t:dgCol title="提交人"  field="submitMan" dictionary="t_s_base_user,id,realname,where 1=1" queryMode="single"  width="100"  ></t:dgCol>
      <t:dgCol title="审核人"  field="reviewMan" dictionary="t_s_base_user,id,realname,where 1=1" queryMode="single"  width="100"  ></t:dgCol>

      <t:dgToolBar title="查看" icon="icon-search" url="hazardFactorsController.do?goDetail" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="审批" icon="icon-edit" url="hazardFactorsController.do?goReview" funname="goReview"></t:dgToolBar>
      <t:dgToolBar title="批量审批" icon="icon-edit" url="hazardFactorsController.do?goAllReview" funname="goAllReview"></t:dgToolBar>
      <c:if test="${isAdmin eq '1'}">
          <t:dgToolBar title="管理员编辑" icon="icon-edit" url="hazardFactorsController.do?goUpdate" funname="update"></t:dgToolBar>
      </c:if>
  </t:datagrid>
  </div>
 </div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>

 <script type="text/javascript">
     function goReview(){
         //选中
         var rows = $("#hazardFactorsList").datagrid('getSelections');
         if(rows== null || rows.length != 1){
             tip("请选择一条要审批的数据!");
         }else{
             openwindow("审批", "hazardFactorsController.do?goApproval&id=" + rows[0].id, "hazardFactorsList", 900, 600);
         }
     }

     function goAllReview() {
         var rows = $("#hazardFactorsList").datagrid('getSelections');
         if (rows.length == 0) {
             tip("请选择要审批的数据");
         } else if (rows.length >= 1){
             var idsTemp = new Array();
             for (var i = 0; i < rows.length; i++) {
                 idsTemp.push(rows[i].id);
             }
             var idt = idsTemp.join(",");
             $.dialog.confirm("已勾选"+idsTemp.length+"条记录,是否确认批量审批？", function () {
                 openwindow("审批", "hazardFactorsController.do?goAllApproval&ids=" + idt, "hazardFactorsList", 900, 600);
             });
         }
     }
 $(document).ready(function(){
     var datagrid = $("#hazardFactorsListtb");
     datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");
     $("#tempSearchColums div[name='searchColumsCenter']").remove();
     $("a[iconcls='icon-reload']").hide();

     $("input[name='queryHandleStatusTem']").change(function() {
         var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
         $("input[name='queryHandleStatus']").val(selectedvalue);

         if(selectedvalue == "true"){
             //已提交，隐藏编辑、删除、录入、导入、导出、模板下载按钮
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","none");
             $("span:contains('管理员编辑')").parents("a.l-btn").show();
         }else{
             //草稿，显示编辑、删除、录入、导入、导出、模板下载按钮
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
         }
         hazardFactorsListsearch();
     });
 });

     function goupdate(){
         var rows = $("#hazardFactorsList").datagrid('getSelections');
         if(rows== null || rows.length != 1){
             tip("请选择一条需要修改的数据!");
         }else{
             var identificationType = "${identificationType}";
             if(identificationType != '4'){
                 addOneTab("${identificationTypeName}录入","riskIdentificationController.do?goAdd&identificationType=${identificationType}&addressId="+rows[0]["address.id"]+"&id="+rows[0].id,"default");
             }else{
                 addOneTab("${identificationTypeName}录入","riskIdentificationController.do?goAdd&identificationType=${identificationType}&postId="+rows[0]["post.id"],"default");
             }
         }
     }
 </script>