<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBMajorHiddenDangerList" checkbox="true" pagination="true" fitColumns="true" title="重大隐患" actionUrl="tBMajorHiddenDangerController.do?datagrid" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
    <t:dgCol title="唯一标识"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="整改时限"  field="rectPeriod" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="隐患描述"  field="hdDesc"  formatterjs="valueTitle"  queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="责任人"  field="dutyMan"    queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="责任单位"  field="dutyUnit.departname"    queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="隐患地点"  field="hdLocation" dictionary="t_b_address_info,id,address,where 1=1"   queryMode="group"  width="120" align="center"></t:dgCol>
      <%--<t:dgCol title="危险源"  field="dangerId.hazard.hazardName"   queryMode="single"  sortable="false" width="120" align="center"></t:dgCol>--%>
    <t:dgCol title="排查日期"  field="inveDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="隐患信息来源"  field="hdInfoSource"   dictionary="hiddenFrom"   queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="隐患等级"  field="hdLevel"  dictionary="hiddenLevel"  queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="隐患类别"  field="hdCate"  dictionary="hiddenCate"  queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="隐患专业"  field="hdMajor" query="true" dictionary="proCate_gradeControl"  queryMode="single"  width="120" align="center"></t:dgCol>
      <t:dgCol title="隐患类型"  field="hiddenType"  dictionary="hiddenType"  queryMode="group"  width="120" align="center"></t:dgCol>

      <t:dgCol title="隐患状态"  field="clStatus" dictionary="hdbiClStatus" query="true"  queryMode="single"  width="120" align="center"></t:dgCol>
    <t:dgCol title="上报省局平台状态" formatterjs="formatIsReported" field="reportStatus"  width="120" align="center"></t:dgCol>

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
    <%--<t:dgCol title="是否上报集团" dictionary="isReportToGroup" field="reportGroupStatus" hidden="false"  width="120" align="center"></t:dgCol>--%>

   <t:dgToolBar title="录入" icon="icon-add" url="tBMajorHiddenDangerController.do?goAdd" funname="noteAdd" operationCode="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBMajorHiddenDangerController.do?goUpdate" funname="noteUpdate" operationCode="update" ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBMajorHiddenDangerController.do?doBatchDel" funname="doDeleteALLSelect" operationCode="batchdelete" ></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBMajorHiddenDangerController.do?goUpdate" funname="detail" operationCode="detail" ></t:dgToolBar>
   <t:dgToolBar title="五落实查看" icon="icon-search" url="tBMajorHiddenDangerController.do?goFiveImpl" funname="detail" operationCode="detailFiveImpl" ></t:dgToolBar>
   <t:dgToolBar title="查看历史" icon="icon-search" url="tBHiddenDangerHistoryController.do?list&fkHiddenInfoId" funname="noteHisttory" operationCode="detailHistory" ></t:dgToolBar>
   <t:dgToolBar title="督办查看" icon="icon-search" url="tBMajorHiddenDangerController.do?supervise" funname="detail" operationCode="detailSupervise" ></t:dgToolBar>
   <t:dgToolBar title="重大隐患上报" icon="icon-putout" url="tBMajorHiddenDangerController.do?majorDangerReport" funname="doMajorDangerReport" operationCode="report" ></t:dgToolBar>
   <t:dgToolBar title="督办同步" icon="icon-reload" url="tBMajorHiddenDangerController.do?synSupervise" funname="synSupervise" operationCode="synchronize" ></t:dgToolBar>
   <%--<t:dgToolBar title="上报集团" icon="icon-reload" url="tBMajorHiddenDangerController.do?reportGroup" funname="reportGroup" operationCode="reportGroup" ></t:dgToolBar>--%>
      <t:dgToolBar title="撤回" icon="icon-undo" url="tBMajorHiddenDangerController.do?toCallback"  funname="toCallback" operationCode="undoReport"></t:dgToolBar>
    <c:if test="${isSunAdmin == 'YGADMIN'}">
      <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
    </c:if>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
 $(document).ready(function(){
 });

 window.top["reload_tBMajorHiddenDangerList"]=function(){
     $("#tBMajorHiddenDangerList").datagrid( "load");
     if(typeof(this.msg)!='undefined' && this.msg!=null&&this.msg!=""){
         tip(this.msg);
     }
 };
 /**
  * 添加督办同步
  * */
 function synSupervise(title,url,gname){
     gridname=gname
     //校验隐患状态是否可以编辑,只有草稿状态才可以修改
     $.ajax({
         type: 'POST',
         url: url,
         cache : false,
         beforeSend: function(data){
             $.messager.progress({
                 text : "正在同步督办数据......",
                 interval : 100
             });
         },
         success:function(data){
             var result = jQuery.parseJSON(data);
             tip(result.msg);
             reloadTable();
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
  * 添加隐患
  * */
 function noteAdd(){
     addOneTab("重大隐患录入","tBMajorHiddenDangerController.do?goAdd","default");
 }

 /**
  * 上报到集团
  * */
function reportGroup(){
    var rows = $("#tBMajorHiddenDangerList").datagrid('getSelections');
    if(rows.length < 1 ){
        tip("请选择要上报的隐患!!!");
    }else{
        var ids = [];
        for ( var i = 0; i < rows.length; i++) {
            ids.push(rows[i].id);
        }
        $.ajax({
            url: "tBMajorHiddenDangerController.do?reportGroup&ids="+ids+"&tt="+new Date(),
            type: 'POST',
            error: function(){
            },
            success: function(data){
                data = $.parseJSON(data);
                tip(data.msg);
                reloadTable();
            }
        });
    }
}

 /**
  *隐患修改，只有待提交状态的因患才可以修改
  **/
 function noteUpdate(){
     //取得选中条目
     var rows = $("#tBMajorHiddenDangerList").datagrid('getSelections');
     if(rows.length < 1 ){
         tip("请选择要编辑的隐患!!!");
     }else if(rows.length > 1){
         tip("请选择一条记录再编辑!!!");
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
                 console.log(result);
                 if("100" == result.msg){
                     addOneTab("重大隐患编辑","tBMajorHiddenDangerController.do?goUpdate&id="+rows[0].id,"default");
                 }else{
                     tip("隐患提交之后无法修改，请重新选择");
                 }
             },
             error:function(data){
             }
         });
     }
 }

 /**
  *
  * 删除隐患，只有草稿状态的隐患可以删除
  *
  * */
 function doDeleteALLSelect(){
     //获取选中条目
     var rows = $("#tBMajorHiddenDangerList").datagrid('getSelections');
     if(rows== null || rows.length <=0){
         tip("请选择要删除的隐患!!!");
     }else{
         //校验隐患状态
         var canDelete = true;
         for(var i =0 ;i<rows.length;i++){
             //查询隐患状态
             $.ajax({
                 type: 'POST',
                 url: 'tBMajorHiddenDangerController.do?getHdbiClStatus',
                 async:false,
                 data: {
                     id:rows[i].id
                 },
                 success:function(data){
                     var result = jQuery.parseJSON(data);
                     if("100" != result.msg){
                         tip("只能删除未提交隐患，请重新选择");
                         canDelete = false;
                     }
                 },
                 error:function(data){
                 }
             });
         }
         if(canDelete){
             deleteALLSelect('批量删除','tBMajorHiddenDangerController.do?doBatchDel','tBMajorHiddenDangerList',null,null);
         }
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


    /**
    * 重大隐患上报
     * 冉哥给的方法，如何传递选中的多行的ID
    */
     function doMajorDangerReport(){
        //获取选中条目
        var rows = $("#tBMajorHiddenDangerList").datagrid('getSelections');
        if(rows== null || rows.length <=0){
            tip("请选择要上报的重大隐患!");
        }else{
            //校验隐患状态
            var ids = [];
            for ( var i = 0; i < rows.length; i++) {
                ids.push(rows[i].id);
            }
            $.ajax({
                url : 'tBMajorHiddenDangerController.do?majorDangerReport',
                type : 'post',
                data: {
                    ids:ids.join(',')
                },
                beforeSend: function(data){
                    $.messager.progress({
                        text : "正在上报数据......",
                        interval : 100
                    });
                },
                cache : false,
                success:function(data){
//                $.messager.progress('close');
                    var d = $.parseJSON(data);
                    tip(d.msg);

                    reloadTable();
                },
                complete: function(date){
                    $.messager.progress('close');
                },
                error:function(data){
                    tip("操作失败");//should not reach here
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

 function formatIsReported (value, rec, index) {
     if (value == '1') {
         return '已上报';
     } else {
         return '未上报';
     }
 }

 /**
  * 撤回
  */
 function toCallback(title,url,gname) {
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