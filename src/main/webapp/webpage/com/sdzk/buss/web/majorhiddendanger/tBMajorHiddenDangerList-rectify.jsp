<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="tempSearchColums" style="display: none">
    <div name="searchColumsCenter" >
          <span style="display:-moz-inline-box;display:inline-block;">
          <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap;  ">
              <input  name="queryHandleStatus" value="300" type="hidden">
            <label>
                <input name="queryHandleStatusTem" type="radio" value="false" checked>
                待整改</label>
            <label style="margin-left: 15px;">
                <input name="queryHandleStatusTem"  type="radio" value="true">
                已整改</label>
        </span>
         </span>
    </div>
</div>
<div class="easyui-layout" fit="true" id="main_majorHiddenDangerList">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBMajorHiddenDangerList" checkbox="false" pagination="true" fitColumns="true" onLoadSuccess="loadSuccess" title="重大隐患" actionUrl="tBMajorHiddenDangerController.do?datagrid&clStatus=300" idField="id" fit="true" queryMode="group" sortName="rectPeriod" sortOrder="asc">
      <t:dgCol title="唯一标识"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="整改时限"  field="rectPeriod" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="隐患描述"  field="hdDesc"  formatterjs="valueTitle"  queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="责任人"  field="dutyMan"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="责任单位"  field="dutyUnit.departname"    queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="隐患地点"  field="hdLocation" dictionary="t_b_address_info,id,address,where 1=1"   queryMode="group"  width="120" align="center"></t:dgCol>
      <%--<t:dgCol title="危险源"  field="dangerId.hazard.hazardName"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>--%>
      <t:dgCol title="排查日期"  field="inveDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="隐患信息来源"  field="hdInfoSource"   dictionary="hiddenFrom"   queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="隐患等级"  field="hdLevel"  dictionary="hiddenLevel"  queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="隐患类别"  field="hdCate"  dictionary="hiddenCate"  queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="隐患专业"  field="hdMajor" query="true" dictionary="proCate_gradeControl"  queryMode="single"  width="120" align="center"></t:dgCol>
      <t:dgCol title="隐患类型"  field="hiddenType"  dictionary="hiddenType"  queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="隐患状态"  field="clStatus" dictionary="hdbiClStatus" queryMode="single"  width="120" align="center"></t:dgCol>

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
      <t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
      <t:dgFunOpt funname="setfunbyrole(id)" title="整改汇报" urlclass="ace_button"></t:dgFunOpt>

      <%--<t:dgToolBar title="整改汇报" icon="icon-edit" url="tBMajorHiddenDangerController.do?goUpdate&load=rectify" funname="noteUpdate" operationCode="update" ></t:dgToolBar>--%>
      <t:dgToolBar title="查看" icon="icon-search" url="tBMajorHiddenDangerController.do?goUpdate" funname="detail" operationCode="detail" ></t:dgToolBar>
      <t:dgToolBar title="五落实录入" icon="icon-edit" url="tBMajorHiddenDangerController.do?goFiveImpl" funname="update" operationCode="addFiveImpl" ></t:dgToolBar>
      <t:dgToolBar title="查看历史" icon="icon-search" url="tBHiddenDangerHistoryController.do?list&fkHiddenInfoId" funname="noteHisttory" operationCode="detailHistory" ></t:dgToolBar>
      <t:dgToolBar title="督办查看" icon="icon-search" url="tBMajorHiddenDangerController.do?supervise" funname="detail" operationCode="detailSupervise" ></t:dgToolBar>
      <t:dgToolBar title="撤回" icon="icon-undo" url="tBMajorHiddenDangerController.do?toRectifyCallback"  funname="toRectifyCallback" operationCode="undoReport"></t:dgToolBar>
      <c:if test="${isSunAdmin == 'YGADMIN'}">
          <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
      </c:if>
  </t:datagrid>
  </div>
 </div>
<div data-options="region:'east',
	title:'整改汇报',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
     style="width: 600px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="main_rectList"></div>
</div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
     var datagrid = $("#tBMajorHiddenDangerListtb");
     datagrid.find("div[name='searchColums']>form>span:last").after($("#tempSearchColums div[name='searchColumsCenter']").html());
     $("#tempSearchColums div[name='searchColumsCenter']").remove();
     $("a[iconcls='icon-reload']").hide();
     $("span:contains('撤回')").parents("a.l-btn").hide();

     $("input[name='queryHandleStatusTem']").change(function() {
         var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
         if (selectedvalue == "true") {
             //已整改 ，隐藏整改按钮
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display", "none");
             $("input[name='queryHandleStatus']").val("400,500,600");
             $("span:contains('撤回')").parents("a.l-btn").show();

         } else {
             //待整改，显示整改按钮
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display", "");
             $("input[name='queryHandleStatus']").val("300");
             $("span:contains('撤回')").parents("a.l-btn").hide();

         }
         tBMajorHiddenDangerListsearch();
     });
 });

 window.top["reload_tBMajorHiddenDangerList_rectify"]=function(){
     $("#tBMajorHiddenDangerList").datagrid( "load");
     if(typeof(this.msg)!='undefined' && this.msg!=null&&this.msg!=""){
         tip(this.msg);
     }
 };
 function setfunbyrole(id,name) {
     if(li_east == 0){
         $('#main_majorHiddenDangerList').layout('expand','east');
     }
     $('#main_majorHiddenDangerList').layout('panel','east').panel('setTitle', "整改进度汇报");
     $('#main_rectList').panel("refresh", "tBRectProgressReportController.do?list&fkHiddenInfoId="+id+"&updateFlag="+$("input[name='queryHandleStatusTem']:checked").val());
 }
 function loadSuccess() {
     $('#main_majorHiddenDangerList').layout('panel','east').panel('setTitle', "");
     $('#main_majorHiddenDangerList').layout('collapse','east');
     $('#main_rectList').empty();
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
 /**
  *隐患修改，只有待提交状态的因患才可以修改
  **/
 function noteUpdate(){
     //取得选中条目
     var rows = $("#tBMajorHiddenDangerList").datagrid('getSelections');
     if(rows== null || rows.length < 1){
         tip("请选择要编辑的隐患！");
     }else if(rows.length > 1){
         tip("请选择一条记录后再编辑！");
     }else{
         //校验隐患状态是否可以编辑,只有草稿状态才可以修改
         $.ajax({
             type: 'POST',
             url: 'tBMajorHiddenDangerController.do?getHdbiClStatus',
             async:false,
             data: {
                 id:rows[0].id
             },
             success:function(data){
                 var result = jQuery.parseJSON(data);
                 if("300" == result.msg){
                     addOneTab("重大隐患整改汇报","tBRectProgressReportController.do?list&fkHiddenInfoId="+rows[0].id,"default");
                 }else{
                     tip("隐患提交之后无法修改，请重新选择");
                 }
             },
             error:function(data){
             }
         });
     }
 }
function formatIsListed (value, rec, index) {
    if (value == '1') {
        return '已挂牌督办';
    } else {
        return '未挂牌督办';
    }
}
 /**
  * 撤回
  */
 function toRectifyCallback(title,url,gname) {
     gridname = gname;
     var ids = [];
     var rows = $("#" + gname).datagrid('getSelections');
     if (rows.length > 0) {

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
  *  阳光账号隐藏数据功能
  * */
 function sunHidden() {
     var rows = $("#tBMajorHiddenDangerList").datagrid('getSelections');
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
             url: 'tBMajorHiddenDangerController.do?sunshine',
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
 </script>