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
               <input name="queryHandleStatusTem" type="radio" value="all">
               全部
           </label>
           <label>
               <input name="queryHandleStatusTem" type="radio" value="0" checked="checked">
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
  <t:datagrid name="tBDangerSourceList" checkbox="true" pagination="true" fitColumns="false" title="年度风险列表" actionUrl="tBDangerSourceController.do?reportDangerSourceDatagrid" idField="id" fit="true" queryMode="group"  sortName="isMajor,yeRecognizeTime" sortOrder="desc,desc">
      <t:dgCol title="唯一编号" field="id" hidden="true" queryMode="group" width="120" align="center"></t:dgCol>
      <t:dgCol title="风险点类型" field="addressCate" query="true" dictionary="addressCate" queryMode="single" width="150" align="center"></t:dgCol>
      <t:dgCol title="上报人" field="createBy" dictionary="t_s_base_user,username,realname" width="150" align="center"></t:dgCol>
      <t:dgCol title="辨识时间" field="yeRecognizeTime" query="true" formatter="yyyy-MM-dd" queryMode="group" width="90" align="center"></t:dgCol>
      <t:dgCol title="隐患描述" field="yeMhazardDesc" queryMode="single" query="true" width="180" formatterjs="valueTitle"></t:dgCol>
      <t:dgCol title="专业" field="yeProfession" query="true" dictionary="proCate_gradeControl" queryMode="single"
               width="60" align="center"></t:dgCol>
      <t:dgCol title="危险源名称" field="hazard.id" dictionary="t_b_hazard_manage,id,hazard_name" queryMode="single" width="80" align="center"></t:dgCol>
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
      <t:dgCol title="风险等级" field="yeRiskGrade" hidden="true" dictionary="riskLevel" query="true" width="70" align="center"></t:dgCol>
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
      <t:dgToolBar title="上报省局平台" icon="icon-putout" url="tBDangerSourceController.do?mineReport" funname="goReport" operationCode="report"></t:dgToolBar>
      <t:dgToolBar title="全部上报省局平台" icon="icon-putout" url="tBDangerSourceController.do?mineReport" funname="goReportAll" operationCode="batchReport"></t:dgToolBar>
      <%--<t:dgToolBar title="上报集团" icon="icon-putout" url="tBDangerSourceController.do?reportYearRiskToGroup" funname="reportYearRiskToGroup" operationCode="reportToGroup"></t:dgToolBar>--%>
     <%-- <t:dgToolBar title="设置重大风险标记"  icon="icon-edit" url="tBDangerSourceController.do?doIsMajor" funname="markIsMajor" operationCode="setMajor"></t:dgToolBar>
      <t:dgToolBar title="撤除重大风险标记"  icon="icon-edit" url="tBDangerSourceController.do?doIsMajor" funname="cancelIsMajor" operationCode="cancelMajor"></t:dgToolBar>--%>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>
      <t:dgToolBar title="撤回" icon="icon-undo" url="tBDangerSourceController.do?mineCallback" funname="mineCallback" operationCode="undoReport"></t:dgToolBar>
      <t:dgToolBar title="一键复制" icon="icon-add" url="tBDangerSourceController.do?goOneKeyCopy" funname="goOneKeyCopy" operationCode="goOneKeyCopy"></t:dgToolBar>
      <t:dgToolBar title="关联风险点" icon="icon-add" onclick="chooseAddress('风险点')"></t:dgToolBar>
      <c:if test="${isSunAdmin == 'YGADMIN'}">
          <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
      </c:if>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/dangersource/js/tBDangerSourceList.js"></script>
 <script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">

 $(document).ready(function(){
     $("a[iconcls='icon-reload']").hide();
     var datagrid = $("#tBDangerSourceListtb");
     datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html());

     $("input[name='queryHandleStatusTem']").click(function() {
         var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
         $("input[name='queryHandleStatus']").val(selectedvalue);
         debugger
         tBDangerSourceListsearch();
         if(selectedvalue == '0'){
             $("span:contains('上报')").parents("a.l-btn").show();
             $("span:contains('重大风险')").parents("a.l-btn").show();
         } else {
             $("span:contains('上报')").parents("a.l-btn").hide();
             $("span:contains('重大风险')").parents("a.l-btn").hide();
         }
         if(selectedvalue == '1'){
             $("span:contains('撤回')").parents("a.l-btn").show();
         } else {
             $("span:contains('撤回')").parents("a.l-btn").hide();
         }
         if(selectedvalue == 'all'){
             $("span:contains('撤回')").parents("a.l-btn").hide();
             $("span:contains('上报')").parents("a.l-btn").hide();
             $("span:contains('重大风险')").parents("a.l-btn").hide();
         }else{
             $("span:contains('撤回')").parents("a.l-btn").show();
             $("span:contains('上报')").parents("a.l-btn").show();
             $("span:contains('重大风险')").parents("a.l-btn").show();
         }
         if(selectedvalue == '0'){
             $("span:contains('撤回')").parents("a.l-btn").hide();
         }
     });
     $("span:contains('撤回')").parents("a.l-btn").hide();
 });

 function goReportAll(title,url,gname) {
//         gridname=gname;
     var ids = [];

     $.ajax({
         url : url,
         type : 'post',
         cache : false,
         data : {
             ids : ids.join(',')
         },
         beforeSend: function(data){
             $.messager.progress({
                 text : "正在上报数据......",
                 interval : 100
             });
         },
         success : function(data) {
          //   debugger
             var d = $.parseJSON(data);
             if (d.success) {
                 tBDangerSourceListsearch();
                // reloadTable();
                // $("#"+gname).datagrid('unselectAll');
                 //ids='';
             }
             var msg = d.msg;
             tip(msg);
         },
         complete: function(date){
             $.messager.progress('close');
         },
         error:function(data){
             tip("操作失败");//should not reach here
         }
     });
 }

     /**
     *上报
      */
     function goReport(title,url,gname) {
//         gridname=gname;
         var ids = [];
         if(title!="全部上报"){
             var rows = $("#"+gname).datagrid('getSelections');
             if (rows.length > 0) {
                 for ( var i = 0; i < rows.length; i++) {
                     ids.push(rows[i].id);
                 }
             } else {
                tip("请选择需要上报的数据");
                 return;
            }
         }
         $.ajax({
             url : url,
             type : 'post',
             cache : false,
             data : {
                 ids : ids.join(',')
             },
             beforeSend: function(data){
                 $.messager.progress({
                     text : "正在上报数据......",
                     interval : 100
                 });
             },
             success : function(data) {
                 var d = $.parseJSON(data);
                 if (d.success) {
                     reloadTable();
                     $("#"+gname).datagrid('unselectAll');
                     ids='';
                 }
                 var msg = d.msg;
                 tip(msg);
             },
             complete: function(date){
                 $.messager.progress('close');
             },
             error:function(data){
                 tip("操作失败");//should not reach here
             }
         });
     }

 /**
  *上报集团
  */
 function reportYearRiskToGroup(title,url,gname) {
//         gridname=gname;
     var ids = [];
     if(title!="全部上报"){
         var rows = $("#"+gname).datagrid('getSelections');
         if (rows.length > 0) {
             for ( var i = 0; i < rows.length; i++) {
                 ids.push(rows[i].id);
             }
         } else {
             tip("请选择需要上报的数据");
             return;
         }
     }
     $.ajax({
         url : url,
         type : 'post',
         cache : false,
         data : {
             ids : ids.join(',')
         },
         beforeSend: function(data){
             $.messager.progress({
                 text : "正在上报数据......",
                 interval : 100
             });
         },
         success : function(data) {
             var d = $.parseJSON(data);
             if (d.success) {
                 reloadTable();
                 $("#"+gname).datagrid('unselectAll');
                 ids='';
             }
             var msg = d.msg;
             tip(msg);
         },
         complete: function(date){
             $.messager.progress('close');
         },
         error:function(data){
             tip("操作失败");//should not reach here
         }
     });
 }
 /**
  * 撤回
  */
 function mineCallback(title,url,gname) {
//     gridname=gname;
     var ids = [];
     var rows = $("#"+gname).datagrid('getSelections');
     if (rows.length > 0) {
         $.dialog.setting.zIndex = getzIndex(true);
         $.dialog.confirm('你确定撤回该数据吗?', function(r) {
             if (r) {
                 for ( var i = 0; i < rows.length; i++) {
                     ids.push(rows[i].id);
                 }
                 $.ajax({
                     url : url,
                     type : 'post',
                     data : {
                         ids : ids.join(',')
                     },
                     cache : false,
                     beforeSend: function(data){
                         $.messager.progress({
                             text : "正在撤回数据......",
                             interval : 100
                         });
                     },
                     success : function(data) {
                         var d = $.parseJSON(data);
                         if (d.success) {
                             var msg = d.msg;
                             tip(msg);
//                             reloadTable();
                             $("#"+gname).datagrid('unselectAll');
                             ids='';
                         }
                     },
                     complete: function(data){
                         $.messager.progress('close');

                         jQuery('#tBDangerSourceList').datagrid('reload');
                         jQuery('#tBDangerSourceList').treegrid('reload');

                     },
                     error:function(data){
                         tip("操作失败");//should not reach here
                     }
                 });
             }
         });
     } else {
         tip("请选择需要撤回的数据");
     }
 }
//导出
function ExportXls() {
	JeecgExcelExport("tBDangerSourceController.do?exportXls&type=yearReport&lec=${initParam.les}","tBDangerSourceList");
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
     if(value != ""){
         return '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 80px; padding-left: 26px;" type="text" value="' + value + '"><span class="minicolors-swatch" style="top:0px;"><span class="minicolors-swatch-color" style="background-color: ' + rec.alertColor + ';"></span></span></div></div>';
     }else{
         return value;
    }
 }
 
     //一键复制功能
     function goOneKeyCopy() {
         $.dialog({id:'dialog',title:'一键复制年度风险',zIndex:999,modal:true,content: 'url:tBDangerSourceController.do?goOneKeyCopy',lock:true,width: 420,height: 220});
     }

     // 关联风险点
     function chooseAddress (title) {
         var rows = $("#tBDangerSourceList").datagrid('getSelections');
         if(rows.length==0){
             tip("请选择风险!");
             return false;
         }else if(rows.length>1){
             tip("每次只能选择一个风险!");
             return false;
         }else{
             var dangerId = rows[0].id;
             $.dialog(
                 {content: "url:tBAddressInfoController.do?addresslist&dangerId="+dangerId, zIndex: 2100, title: '' + title + '', lock: true, parent: windowapi, width: 860, height: 480, left: '7%', top: '40%', opacity: 0.4,}
             );
         }
     }
 </script>