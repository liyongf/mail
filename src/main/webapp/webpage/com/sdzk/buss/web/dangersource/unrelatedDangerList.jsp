<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
      <t:datagrid name="tBDangerSourceList" checkbox="false" fitColumns="false"  title="危险源列表"
                  actionUrl="tBDangerSourceController.do?unrelatedDatagrid" idField="id" fit="true" queryMode="group" sortName="yeRecognizeTime" sortOrder="desc">
          <t:dgCol title="唯一编号" field="id" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
          <t:dgCol title="辨识时间" field="yeRecognizeTime" query="true" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
          <t:dgCol title="隐患描述" field="yeMhazardDesc" queryMode="single" query="true" width="180"></t:dgCol>
          <t:dgCol title="专业" field="yeProfession" query="true" dictionary="proCate_gradeControl" queryMode="single"
                   width="60" align="center"></t:dgCol>
          <t:dgCol title="危险源名称" field="hazard.hazardName" queryMode="single" width="80" align="center"></t:dgCol>
          <t:dgCol title="伤害类别" field="damageType" query="true" dictionary="danger_Category" queryMode="single"
                   width="80" align="center"></t:dgCol>
          <t:dgCol title="风险描述" field="yePossiblyHazard" queryMode="single" query="true" width="180"></t:dgCol>
          <t:dgCol title="事故类型" field="yeAccidentTemp" queryMode="group" width="80" align="center"></t:dgCol>
          <t:dgCol title="作业活动" field="activity.id" dictionary="t_b_activity_manage where is_delete='0',id,activity_name" query="true" queryMode="single" width="100" align="center"></t:dgCol>
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
          <t:dgCol title="责任岗位" field="post.postName" queryMode="single" width="100" align="center"></t:dgCol>
          <t:dgCol title="隐患等级" field="hiddenLevel" dictionary="hiddenLevel" queryMode="single" query="true" width="100" align="center"></t:dgCol>
          <t:dgCol title="罚款金额(元)" field="fineMoney" queryMode="group" width="80" align="center"></t:dgCol>
          <t:dgCol title="风险状态" field="auditStatus" replace="待上报_1,审核中_2,审核退回_3,闭环_4" queryMode="single" width="60"
                   align="center"></t:dgCol>
          <%--<t:dgCol title="危险源来源" field="origin" replace="通用_1,年度_2,专项_3" queryMode="group" width="80"
                   align="center"></t:dgCol>--%>
          <t:dgToolBar title="查看" operationCode="detail" icon="icon-search" funname="detail" url="tBDangerSourceController.do?goDetail"></t:dgToolBar>
      </t:datagrid>
  </div>
 </div>
<script src = "webpage/com/sdzk/buss/web/dangersource/js/tBDangerSourceList.js"></script>
 <script type="text/javascript">

     function parseAddress(data){
         var parsed = [];
         $.each(data.rows,function(index,row){
             parsed.push({data:row,result:row.attachmenttitle,value:row.id});
         });
         return parsed;
     }
     /**
      * 选择后回调
      *
      * @param {Object} data
      */
     function callBackAddress(data) {
         if(data==null){
             $("[name='addressauto']").val("");
             $("[name='fileId']").val("");
         }else{
             $("[name='addressauto']").val(data.attachmenttitle);
             $("[name='fileId']").val(data.id);
             var standardSource = $("#standardSource").val();
             $.ajax({
                 url: "tBItemController.do?itemList&fileId="+data.id+"&standardSource="+standardSource+"&tt="+new Date(),
                 type: 'POST',
                 error: function(){
                 },
                 success: function(data){
                     $("#itemId").html("");
                     var map = new HashMap();
                     data = $.parseJSON(data);
                     for(var i =0 ;i<data.length;i++){
                         var option = "<option value=\""+data[i].id+"\">"+data[i].itemName+"</option>";
                         $("#itemId").append(option);
                         map.put(data[i].id,data[i].manageStandards);
                     }
                     var itemSelected = $("#itemId").val();
                     var managerStandards = map.get(itemSelected);
                     $("#manageStandards").val(managerStandards);
                 }
             });
         }
     }

     /**
      * 每一个选择项显示的信息
      *
      * @param {Object} data
      */
     function formatItemAddress(data) {
         return data.attachmenttitle;
     }


     function setDangerSourceStaus(id,status){
         if(status == "1"){
             doSetDangerSourceStatus("是否将该危险源置为无效?",id);
         }else{
             doSetDangerSourceStatus("是否将该危险源置为有效?",id);
         }
     }

     function doSetDangerSourceStatus(msg,id){
         $.dialog.confirm(msg, function(){
             $.ajax({
                 url: "tBDangerSourceController.do?setDangerSourceStatus&id="+id+"&tt="+new Date(),
                 type: 'POST',
                 error: function(){
                 },
                 success: function(data){
                     data = $.parseJSON(data);
                     tip(data.msg);
                     reloadTable();
                 }
             });
         });
     }
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBDangerSourceListtb").find("input[name='createDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBDangerSourceListtb").find("input[name='updateDate']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     //$("a[iconcls='icon-reload']").hide();
     $('#riskType').combotree({
         url : 'tBManageObjectController.do?setPFunction&selfId=${tbManageObjectEntity.id}',
         width: 155,
         onSelect : function(node) {
             $('#riskType').val(node.id);
         }
     });
     $(document).keydown(function(event){
         if(event.keyCode==13){
             tBDangerSourceListsearch();
         }
     });
 });
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBDangerSourceController.do?upload', "tBDangerSourceList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBDangerSourceController.do?exportXls","tBDangerSourceList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBDangerSourceController.do?exportXlsByT","tBDangerSourceList");
}
     function abcd(){
         window.location.href = "tBDangerSourceController.do?goAdd";
     }
 function goupdate(){
     var url = "tBDangerSourceController.do?goUpdate";
     var rowsData = $('#tBDangerSourceList').datagrid('getSelections');
     if (!rowsData || rowsData.length==0) {
         tip('请选择编辑项目');
         return;
     }
     if (rowsData.length>1) {
         tip('请选择一条记录再编辑');
         return;
     }
     url += '&id='+rowsData[0].id;
     window.location.href =url;
 }
 </script>