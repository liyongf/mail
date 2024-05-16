<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!-- 矿年度危险源审核 -->
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <br>
       <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px;text-align: center;width: 100%">
        <input  name="queryHandleStatus" value="0" type="hidden">
           <label>
               <input name="queryHandleStatusTem" type="radio" value="all" >
               全部
           </label>
           <label>
               <input name="queryHandleStatusTem" type="radio" value="0" checked="checked">
               待审核
           </label>
           <label>
               <input name="queryHandleStatusTem" type="radio" value="1" >
               已审核
           </label>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBDangerSourceList" checkbox="true" pagination="true" fitColumns="false" title="年度风险列表" actionUrl="tBDangerSourceController.do?reviewDangerSourceDatagrid" idField="id" fit="true" queryMode="group" sortName="isMajor,yeRecognizeTime" sortOrder="desc,desc">
      <t:dgCol title="唯一编号" field="id" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
      <t:dgCol title="风险点类型" field="addressCate" query="true" dictionary="addressCate" queryMode="single" width="150" align="center"></t:dgCol>
      <t:dgCol title="上报人" field="createBy" dictionary="t_s_base_user,username,realname" width="150" align="center"></t:dgCol>
      <t:dgCol title="辨识时间" field="yeRecognizeTime" query="true" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="隐患描述" field="yeMhazardDesc" queryMode="single" query="true" width="180" formatterjs="valueTitle"></t:dgCol>
      <t:dgCol title="专业" field="yeProfession" query="true" dictionary="proCate_gradeControl" queryMode="single"
               width="80" align="center"></t:dgCol>
      <t:dgCol title="危险源名称" field="hazard.hazardName" queryMode="single" width="80" align="center"></t:dgCol>
      <t:dgCol title="伤害类别" field="damageType" query="true" dictionary="danger_Category" queryMode="single"
               width="120" align="center"></t:dgCol>
      <t:dgCol title="风险描述" field="yePossiblyHazard" queryMode="single" query="true" width="180" formatterjs="valueTitle"></t:dgCol>
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
      <%--<t:dgCol title="风险等级" field="yeRiskGrade" dictionary="riskLevel" query="true" queryMode="single" width="70"--%>
               <%--align="center"></t:dgCol>--%>
      <t:dgCol title="风险等级" field="yeRiskGrade" hidden="true" dictionary="riskLevel" query="true" queryMode="single" width="70" align="center"></t:dgCol>
      <t:dgCol title="预警颜色" field="alertColor" hidden="true" queryMode="group" width="80"></t:dgCol>
      <t:dgCol title="风险等级" field="yeRiskGradeTemp"  formatterjs="colorValueFormatter" sortable="false" queryMode="single" width="70"
               align="center"></t:dgCol>
      <t:dgCol title="风险类型" field="yeHazardCate" query="true" dictionary="hazardCate" queryMode="single" width="60"
               align="center"></t:dgCol>
      <t:dgCol title="管控标准来源" field="docSource" queryMode="single" query="true" width="100" align="center" formatterjs="valueTitle"></t:dgCol>
      <t:dgCol title="章节条款" field="sectionName" queryMode="single" query="false" width="100" align="center" formatterjs="valueTitle"></t:dgCol>
      <t:dgCol title="标准内容" field="yeStandard" queryMode="single" query="true" width="180" align="center" formatterjs="valueTitle"></t:dgCol>
      <t:dgCol title="管控措施" field="manageMeasure" queryMode="group" width="180" align="center" formatterjs="valueTitle"></t:dgCol>
      <t:dgCol title="责任岗位" field="post.postName" queryMode="single" width="100" align="center"></t:dgCol>
      <t:dgCol title="隐患等级" field="hiddenLevel" dictionary="hiddenLevel" queryMode="single" query="true" width="100" align="center"></t:dgCol>
      <t:dgCol title="罚款金额(元)" field="fineMoney" queryMode="group" width="80" align="center"></t:dgCol>
      <t:dgCol title="风险状态" field="auditStatus" replace="待上报_1,审核中_2,审核退回_3,闭环_4" queryMode="single" width="60"
               align="center"></t:dgCol>
      <%--<t:dgCol title="危险源来源" field="origin" replace="通用_1,年度_2,专项_3" queryMode="group" width="80"
               align="center"></t:dgCol>--%>

      <t:dgToolBar title="查看" icon="icon-search" url="tBDangerSourceController.do?goDetail" funname="detail" width="850" height="450" operationCode="detail"></t:dgToolBar>
      <t:dgToolBar title="审核" icon="icon-putout" url="tBDangerSourceController.do?goUpdate" funname="goReview" operationCode="review"></t:dgToolBar>
      <t:dgToolBar title="全部审核" icon="icon-putout" url="tBDangerSourceController.do?goReviewDangerSource" funname="reviewAll" operationCode="reviewAll"></t:dgToolBar>
     <%-- <t:dgToolBar title="设置重大风险标记"  icon="icon-edit" url="tBDangerSourceController.do?doIsMajor" funname="markIsMajor" operationCode="setMajor" ></t:dgToolBar>
      <t:dgToolBar title="撤除重大风险标记"  icon="icon-edit" url="tBDangerSourceController.do?doIsMajor" funname="cancelIsMajor" operationCode="cancelMajor"></t:dgToolBar>--%>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="icon-edit" funname="editDangerSource" operationCode="editDangerSource"></t:dgToolBar>
      <t:dgToolBar title="批量删除" icon="icon-remove" funname="deleteAllSelectedLogic" operationCode="batchDelete"></t:dgToolBar>
      <t:dgToolBar title="去审" icon="icon-undo" url="tBDangerSourceController.do?reviewCallback" funname="reviewCallback" operationCode="undoReview"></t:dgToolBar>
      <t:dgToolBar title="全部去审" icon="icon-undo" url="tBDangerSourceController.do?undoReviewAll" funname="undoReviewAll" operationCode="undoReviewAll"></t:dgToolBar>
      <c:if test="${isSunAdmin == 'YGADMIN'}">
          <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
      </c:if>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/dangersource/js/tBDangerSourceList.js"></script>
 <script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">

     window.top["reload_tBDangerSourceListOnCheck"]=function(){
         $("#tBDangerSourceList").datagrid( "reload");
     };

     /**批量逻辑删除*/
     function deleteAllSelectedLogic(){
         var rowsData = $('#tBDangerSourceList').datagrid('getSelections');
         if(rowsData == null || rowsData.length < 1){
             tip("请选择需要删除的条目");
         }else{
             var ids = new Array();
             for (var i = 0; i < rowsData.length; i++) {
                 ids.push(rowsData[i].id);
             }
             var id = ids.join(",");
             $.ajax({
                 url : 'tBDangerSourceController.do?canModifyDangerSource',
                 type : 'post',
                 async: false,
                 data : {
                     ids:id
                 },
                 success : function(data) {
                     var result = jQuery.parseJSON(data);
                     console.log(result);
                     if('2' == result.msg){
                         deleteALLSelect('批量删除','tBDangerSourceController.do?logicDoBatchDel','tBDangerSourceList',null,null);
                     }else{
                         tip("选择危险源中包含已审核,不能删除,请重新选择");
                     }
                 },
                 error:function(){
                 }
             });
         }
     }
     /**
      *跳转到编辑年度危险源页面
      */
     function editDangerSource(){
         //获取选中条目
         var rowsData = $('#tBDangerSourceList').datagrid('getSelections');
         if(rowsData == null || rowsData.length != 1){
             tip("请选择一条需要编辑的条目");
         }else{
             var id = rowsData[0].id;
             //校验是否可以修改：待审核状态可以编辑
             $.ajax({
                 url : 'tBDangerSourceController.do?canModifyDangerSource',
                 type : 'post',
                 async: false,
                 data : {
                     id:id
                 },
                 success : function(data) {
                     var result = jQuery.parseJSON(data);
                     console.log(result);
                     if("2" == result.msg ){
                         addOneTab("年度部门危险源编辑","tBDangerSourceController.do?goUpdateDangerSourceOnCheck&id="+id,"default");
                     }else{
                         tip("该危险源已审核,不能编辑,请重新选择");
                     }
                 },
                 error:function(){
                 }
             });

         }
     }

 $(document).ready(function(){
     $("a[iconcls='icon-reload']").hide();
     var datagrid = $("#tBDangerSourceListtb");
     datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html());

     $("input[name='queryHandleStatusTem']").change(function() {
         var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
         $("input[name='queryHandleStatus']").val(selectedvalue);
         tBDangerSourceListsearch();
         if(selectedvalue == '0'){
             $("span:contains('审核')").parents("a.l-btn").show();
             $("span:contains('重大风险')").parents("a.l-btn").show();
         } else {
             $("span:contains('审核')").parents("a.l-btn").hide();
             $("span:contains('重大风险')").parents("a.l-btn").hide();
         }
         if(selectedvalue == '1'){
             $("span:contains('去审')").parents("a.l-btn").show();
             $("span:contains('编辑')").parents("a.l-btn").hide();
             $("span:contains('批量删除')").parents("a.l-btn").hide();

         } else {
             $("span:contains('去审')").parents("a.l-btn").hide();
             $("span:contains('编辑')").parents("a.l-btn").show();
             $("span:contains('批量删除')").parents("a.l-btn").show();
         }
     });

     $("span:contains('去审')").parents("a.l-btn").hide();
 });


     /**
     *审核
      */
     function goReview() {
         var rowsData = $('#tBDangerSourceList').datagrid('getSelections');
         if(rowsData == null || rowsData.length < 1){
             tip("请选择需要审核的条目");
         }else{
             var ids = new Array();
             for (var i = 0; i < rowsData.length; i++) {
                 ids.push(rowsData[i].id);
             }
             var id = ids.join(",");
             $.ajax({
                 url : 'tBDangerSourceController.do?canReviewDangerSource',
                 type : 'post',
                 async: false,
                 data : {
                     ids:id
                 },
                 success : function(data) {
                     var result = jQuery.parseJSON(data);
//                     console.log(result);
                     if("2" == result.msg){
                         createwindow("审核", "tBDangerSourceController.do?goReviewDangerSource&ids="+id,null,null);
                     }else{
                         tip("选择危险源中包含已审核（审核退回）数据,不能重复审核,请重新选择");
                     }
                 },
                 error:function(){
                 }
             });

         }
     }
 /**
  *全部审核
  */
 function reviewAll(title,url,gname) {
     gridname=gname;
     $.dialog.setting.zIndex = getzIndex(true);
     $.dialog.confirm('本操作将审核全部待审核的风险，你确定全部审核吗?', function(r) {
         if (r) {
             createwindow("审核", url,null,null);
         }
     });

 }
 /**
  * 去审
  */
    function reviewCallback(title,url,gname) {
     gridname=gname;
     var ids = [];
     var rows = $("#"+gname).datagrid('getSelections');
     if (rows.length > 0) {
         for ( var j = 0; j < rows.length; j++) {
             if (rows[j].reportStatus=='2') {
                 tip("只能选择未上报省局平台的年度风险!");
                 return;
             }
             if (rows[j].auditStatus!='4') {
                 tip("只能选择已闭环的年度风险!");
                 return;
             }
         }
         $.dialog.setting.zIndex = getzIndex(true);
         for ( var i = 0; i < rows.length; i++) {
             ids.push(rows[i].id);
         }
         $.ajax({
             url :  'tBDangerSourceController.do?calculateAddressNum',
             type : 'post',
             data : {
                 ids : ids.join(",")
             },
             cache : false,
             success : function(data){
                 var result = $.parseJSON(data);
                 $.dialog.confirm('共'+rows.length+'条风险，关联'+result.msg+'个风险点，你确定去审这些数据吗?', function(r) {
                 if (r) {
                     $.ajax({
                         url : url,
                         type : 'post',
                         data : {
                             ids : ids.join(',')
                         },
                         cache : false,
                         success : function(data) {
                             var d = $.parseJSON(data);
                             if (d.success) {
                                 var msg = d.msg;
                                 tip(msg);
                                 reloadTable();
                                 $("#"+gname).datagrid('unselectAll');
                                 ids='';
                             }
                         }
                     });
                 }
             });
             },
             error : function(data){
                 tip(data);
             }
         });
     } else {
         tip("请选择需要去审的数据");
     }
 }
 /**
  * 全部去审
  */
 function undoReviewAll(title,url,gname) {
     gridname=gname;
     $.dialog.setting.zIndex = getzIndex(true);
     $.dialog.confirm('本操作将去审未上报省局平台的风险，并且将删除它们与风险点的关联关系，你确定全部去审吗?', function(r) {
         if (r) {
             $.ajax({
                 url : url,
                 type : 'post',
                 cache : false,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     if (d.success) {
                         var msg = d.msg;
                         tip(msg);
                         reloadTable();
                         $("#"+gname).datagrid('unselectAll');
                     }
                 }
             });
         }
     });

 }
//导出
function ExportXls() {
	JeecgExcelExport("tBDangerSourceController.do?exportXls&type=yearReview&lec=${initParam.les}","tBDangerSourceList");
}

     /**
      *  阳光账号隐藏数据功能
      * */
     function sunHidden() {
         var rows = $("#tBDangerSourceList").datagrid('getSelections');
         if (rows.length < 1) {
             tip("请选择需要隐藏的数据");
         } else {
             var idsTemp = new Array();
             for (var i = 0; i < rows.length; i++) {
                 idsTemp.push(rows[i].id);
             }
             var idt = idsTemp.join(",");

             $.ajax({
                 type: 'POST',
                 url: 'tBDangerSourceController.do?sunshine',
                 dataType:"json",
                 async:true,
                 cache: false,
                 data: {
                     ids:idt
                 },
                 success:function(data){
                     var msg = data.msg;
                     tip(msg);
                     reloadTable();
                 },
                 error:function(data){
                 }
             });
         }
     }
     //颜色列格式化
     function colorValueFormatter(value, rec, index) {
//     if(value != ""){
         return '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 80px; padding-left: 26px;" type="text" value="' + value + '"><span class="minicolors-swatch" style="top:0px;"><span class="minicolors-swatch-color" style="background-color: ' + rec.alertColor + ';"></span></span></div></div>';
//     }else{
//         return value;
//     }
     }
 </script>