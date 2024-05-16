<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!-- 部门上报危险源 -->
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
               <input name="queryHandleStatusTem" type="radio" value="0"  checked="checked">
               待上报
           </label>
           <label>
               <input name="queryHandleStatusTem" type="radio" value="1" >
               已上报
           </label>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBDangerSourceList" checkbox="true" pagination="true" fitColumns="false" title="部门年度风险上报" actionUrl="tBDangerSourceController.do?departReportDangerSourceDatagrid" idField="id" fit="true" queryMode="group" sortName="isMajor,yeRecognizeTime" sortOrder="desc,desc">
      <t:dgCol title="唯一编号" field="id" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
      <t:dgCol title="风险点类型" field="addressCate" hidden="false" dictionary="addressCate" queryMode="single" width="150" align="center"></t:dgCol>
      <t:dgCol title="上报人" field="createBy" dictionary="t_s_base_user,username,realname" width="150" align="center"></t:dgCol>
      <t:dgCol title="辨识时间" field="yeRecognizeTime" query="true" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="隐患描述" field="yeMhazardDesc" queryMode="single" query="true" width="180" formatterjs="valueTitle"></t:dgCol>
      <t:dgCol title="专业" field="yeProfession" query="true" dictionary="proCate_gradeControl" queryMode="single"
               width="80" align="center"></t:dgCol>
      <t:dgCol title="危险源名称" field="hazard.id" queryMode="group" width="80" align="center" dictionary="t_b_hazard_manage,id,hazard_name"></t:dgCol>
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

      <t:dgToolBar title="年度辨识复制" icon="icon-add" url="" funname="goCloneYearDanger" operationCode="clone"></t:dgToolBar>
      <t:dgToolBar title="从通用库复制" icon="icon-add" funname="chooseDangerSource" operationCode="relDangerSource"></t:dgToolBar>
      <t:dgToolBar title="录入" icon="icon-add" funname="goAddDepartDangerSource" operationCode="add"></t:dgToolBar>
      <t:dgToolBar title="编辑" icon="icon-edit" url="tBDangerSourceController.do?goUpdate"
                   funname="goUpdateDepartDangerSource" operationCode="update"></t:dgToolBar>
      <t:dgToolBar title="查看" icon="icon-search" url="tBDangerSourceController.do?goDetail" funname="detail" width="850"
                   height="450" operationCode="detail"></t:dgToolBar>
      <t:dgToolBar title="上报审核" icon="icon-putout" url="tBDangerSourceController.do?doReport2Mine" funname="goReport"
                   operationCode="report"></t:dgToolBar>
      <t:dgToolBar title="全部上报审核" icon="icon-putout" url="tBDangerSourceController.do?doReportAll2Mine" funname="doReportAll"
                   operationCode="reportAll"></t:dgToolBar>
      <t:dgToolBar title="批量删除" icon="icon-remove" url="tBDangerSourceController.do?doBatchDel"
                   funname="doDeleteALLSelect" operationCode="batchdelete"></t:dgToolBar>
   <%--   <t:dgToolBar title="设置重大风险标记" icon="icon-edit" url="tBDangerSourceController.do?doIsMajor" funname="markIsMajor"
                   operationCode="setMajor"></t:dgToolBar>
      <t:dgToolBar title="撤除重大风险标记" icon="icon-edit" url="tBDangerSourceController.do?doIsMajor" funname="cancelIsMajor"
                   operationCode="cancelMajor"></t:dgToolBar>--%>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>
      <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls" operationCode="import"></t:dgToolBar>
      <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT" operationCode="downloadTmpl"></t:dgToolBar>
      <t:dgToolBar title="撤回" icon="icon-undo" url="tBDangerSourceController.do?toReportCallback"
                   funname="toReportCallback" operationCode="undoReport"></t:dgToolBar>
      <t:dgToolBar title="撤回全部" icon="icon-undo" url="tBDangerSourceController.do?undoReportAll"
                   funname="undoReportAll" operationCode="undoReportAll"></t:dgToolBar>
      <c:if test="${isSunAdmin == 'YGADMIN'}">
          <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
      </c:if>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/dangersource/js/tBDangerSourceList.js"></script>
 <script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
 function goCloneYearDanger(){
     $.dialog({id:'dialog',title:'批量复制年度风险',zIndex:999,modal:true,content: 'url:tBDangerSourceController.do?goCloneYearDanger',lock:true,width: 420,height: 150});
 }

 function chooseDangerSource(){
     $.dialog({id:'dialog',title:'危险源选择',zIndex:2100,modal:true,content: 'url:tBDangerSourceController.do?associateMajorGeneralDangerSource',lock:true,width: 1200,height: 600});
 }
 function showMsg(msg){
     tip(msg);
 }
 function reloadP(){
     $('#tBDangerSourceList').datagrid('reload');
 }
     window.top["reload_tBDangerSourceList"]=function(){
         $("#tBDangerSourceList").datagrid( "load");
     };

 $(document).ready(function(){
     $("a[iconcls='icon-reload']").hide();
     var datagrid = $("#tBDangerSourceListtb");
     datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html());

     $("input[name='queryHandleStatusTem']").change(function() {
         var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
         $("input[name='queryHandleStatus']").val(selectedvalue);
         tBDangerSourceListsearch();
         if(selectedvalue == '0'){
             $("span:contains('编辑')").parents("a.l-btn").show();
             $("span:contains('上报')").parents("a.l-btn").show();
             $("span:contains('删除')").parents("a.l-btn").show();
             $("span:contains('重大风险')").parents("a.l-btn").show();
             $("span:contains('导入')").parents("a.l-btn").show();
             $("span:contains('模板下载')").parents("a.l-btn").show();
             $("span:contains('从通用库')").parents("a.l-btn").show();
         } else {
             $("span:contains('编辑')").parents("a.l-btn").hide();
             $("span:contains('上报')").parents("a.l-btn").hide();
             $("span:contains('删除')").parents("a.l-btn").hide();
             $("span:contains('重大风险')").parents("a.l-btn").hide();
             $("span:contains('导入')").parents("a.l-btn").hide();
             $("span:contains('模板下载')").parents("a.l-btn").hide();
             $("span:contains('从通用库')").parents("a.l-btn").hide();
         }
         if(selectedvalue == '1'){
             $("span:contains('撤回')").parents("a.l-btn").show();
             $("span:contains('录入')").parents("a.l-btn").hide();
             $("span:contains('关联危险源')").parents("a.l-btn").hide();
         } else {
             $("span:contains('撤回')").parents("a.l-btn").hide();
             $("span:contains('录入')").parents("a.l-btn").show();
             $("span:contains('关联危险源')").parents("a.l-btn").show();
         }
     });
     $("span:contains('撤回')").parents("a.l-btn").hide();
 });
     function doDeleteALLSelect(){
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
                     if("1" == result.msg || "3" == result.msg){
                         deleteALLSelect('批量删除','tBDangerSourceController.do?doBatchDel','tBDangerSourceList',null,null);
                     }else{
                         tip("选择危险源中包含已上报,不能删除,请重新选择");
                     }
                 },
                 error:function(){
                 }
             });
         }
     }

     /**
     *跳转添加年度部门危险源页面
      */
     function goAddDepartDangerSource(){
         addOneTab("年度部门危险源录入","tBDangerSourceController.do?goAddDepartDangerSource","default");
     }

     /**
     *跳转到编辑年度危险源页面
      */
   function goUpdateDepartDangerSource(){
       //获取选中条目
       var rowsData = $('#tBDangerSourceList').datagrid('getSelections');
       if(rowsData == null || rowsData.length != 1){
           tip("请选择一条需要编辑的条目");
       }else{
           var id = rowsData[0].id;
           //校验是否可以修改：待上报状态可以编辑
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
                   if("1" == result.msg || "3" == result.msg){
                       addOneTab("年度部门危险源编辑","tBDangerSourceController.do?goUpdateDepartDangerSource&id="+id,"default");
                   }else{
                       tip("该危险源已上报,不能编辑,请重新选择");
                   }
               },
               error:function(){
               }
           });

       }
   }
     /**
     *上报
      */
     function goReport() {
         var rowsData = $('#tBDangerSourceList').datagrid('getSelections');
         if(rowsData == null || rowsData.length < 1){
             tip("请选择需要上报的条目");
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
                     if("1" == result.msg || "3" == result.msg){
                         $.ajax({
                             type: "POST",
                             async: false,
                             url: "tBDangerSourceController.do?checkForAudit",
                             data : {
                                 ids:id
                             },
                             dataType: "json",
                             success: function (d) {
                                 if(d.success){
                                     $.dialog.confirm("是否确认上报选中的危险源？", function() {
                                         $.ajax({
                                             type: "POST",
                                             url: "tBDangerSourceController.do?doReport2Mine",
                                             data : {
                                                 ids:id
                                             },
                                             dataType: "json",
                                             success: function (data) {
                                                 tip(data.msg);
                                                 $("#tBDangerSourceList").datagrid("load");
                                             },
                                             error:function(){
                                             }
                                         });
                                     });
                                 }else{
                                     tip(d.msg);
                                 }

                             },
                             error:function(){
                             }
                         });

                         /**/
                     }else{

                         tip("选择危险源中包含已上报,不能重复上报,请重新选择");
                     }
                 },
                 error:function(){
                 }
             });

         }
     }

 /**
  *全部上报
  */
 function doReportAll(title,url,gname) {
     gridname=gname;
     $.dialog.setting.zIndex = getzIndex(true);
     $.dialog.confirm('本操作将上报审核本部门全部风险，你确定全部上报吗?', function(r) {
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
     /**
      * 撤回
      */
     function toReportCallback(title,url,gname) {
         gridname = gname;
         var ids = [];
         var rows = $("#" + gname).datagrid('getSelections');
         if (rows.length > 0) {
             for (var j = 0; j < rows.length; j++) {
                 if (rows[j].auditStatus != '2') {
                     tip("只能选择未审核的年度风险!");
                     return;
                 }
             }
             $.dialog.setting.zIndex = getzIndex(true);
             $.dialog.confirm('你确定撤回该数据吗?', function (r) {
                 if (r) {
                     for (var i = 0; i < rows.length; i++) {
                         ids.push(rows[i].id);
                     }
                     $.ajax({
                         url: url,
                         type: 'post',
                         data: {
                             ids: ids.join(',')
                         },
                         cache: false,
                         success: function (data) {
                             var d = $.parseJSON(data);
                             if (d.success) {
                                 var msg = d.msg;
                                 tip(msg);
                                 reloadTable();
                                 $("#" + gname).datagrid('unselectAll');
                                 ids = '';
                             }
                         }
                     });
                 }
             });
         } else {
             tip("请选择需要撤回的数据");
         }
     }
 /**
  * 全部撤回
  */
 function undoReportAll(title,url,gname) {
     gridname=gname;
     $.dialog.setting.zIndex = getzIndex(true);
     $.dialog.confirm('本操作将撤回本部门全部待审核的风险，你确定全部撤回吗?', function(r) {
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
     });

 }

//导出
function ExportXls() {
	JeecgExcelExport("tBDangerSourceController.do?exportXls&type=departReport&lec=${initParam.les}","tBDangerSourceList");
}
     //导入
     function ImportXls() {
         openuploadwin('部门年度风险导入', 'tBDangerSourceController.do?upload&type=departReport', "tBDangerSourceList");
     }

     //模板下载
     function ExportXlsByT() {
         JeecgExcelExport("tBDangerSourceController.do?exportXlsByTForDepartReport&lec=${initParam.les}","tBDangerSourceList");
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