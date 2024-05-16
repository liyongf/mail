<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="departSearchColums">
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="责任部门">责任部门：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="departnameSuggest" style="width: 130px;height: 15px"></div>
                 <input id="departname" type="hidden" name="hiddenDanger.dutyUnit.departname">
             </span>
        </span>
    </div>
    <div name="searchColums" >
         <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="检查部门">检查部门：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="checkDepartNameSuggest" style="width: 130px;height: 15px"></div>
                 <input id="checkDepartName" type="hidden" name="checkDepartId">
             </span>
        </span>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">检查人：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="fillCardManSelect" style="width: 130px;height: 15px"></div>
                <input class="inuptxt" type="hidden" style="width: 100px" name="hiddenDanger.fillCardMan.id" id="fillCardManId">
             </span>
        </span>

        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="问题地点">问题地点：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="addressSelect" style="width: 130px;height: 15px"></div>
                 <input id="tBAddressInfoEntity_id" type="hidden" name="tBAddressInfoEntity_id">
             </span>
        </span>


        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="隐患等级">隐患等级：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 300px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="hiddenNatureSelect" style="width: 260px;height: auto"></div>
                 <input id="hiddenNatureList" type="hidden" name="hiddenNatureList">
             </span>
        </span>


        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="隐患类型">隐患类型：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 300px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="riskTypeSelect" style="width: 260px;height: auto"></div>
                 <input id="riskTypeList" type="hidden" name="riskTypeList">
             </span>
        </span>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="是否关联风险">是否关联风险：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 300px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                 <select name="riskIsRel"  >
                      <option>--请选择--</option>
                      <option value="1">是</option>
                      <option value="0">否</option>
                 </select>
             </span>
        </span>


    </div>
</div>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBHiddenDangerHandleList" onDblClick="dblClickDetail" onLoadSuccess="loadSuccess" autoLoadData="true" checkbox="true" fitColumns="false" title="综合查询" actionUrl="tBHiddenDangerHandleController.do?queryListDatagrid&homePage=${homePage}&startDate=${startDate}&endDate=${endDate}" idField="id" sortName="hiddenDanger.examDate" sortOrder="desc" fit="true" queryMode="group">
      <t:dgCol title="隐患检查id"  field="hiddenDanger.id" hidden="true" sortable="true" width="80" align="center"></t:dgCol>
      <c:if test="${huayuan eq 'true'}">
          <t:dgCol title="隐患编号"  field="hiddenDanger.hiddenNumber" query="true" sortable="false" width="80" align="center"></t:dgCol>
      </c:if>
   <%--<t:dgCol title="隐患来源"  field="hiddenDanger.origin"  query="false" dictionary="hiddenDangerOrigin" queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>--%>
   <t:dgCol title="发现时间"  field="hiddenDanger.examDate"  formatter="yyyy-MM-dd"  query="true" queryMode="group"  sortable="true" width="80" align="center"></t:dgCol>
   <t:dgCol title="检查班次"  field="hiddenDanger.shift" hidden="false" dictionary="workShift" query="true"  queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>
   <%--<t:dgCol title="危险源"  field="hiddenDanger.dangerId.hazard.hazardName"   queryMode="single"  sortable="true" width="120" align="center"></t:dgCol>--%>
   <%--<t:dgCol title="关联危险源来源"  field="hiddenDanger.dangerId.origin"   queryMode="single"  replace="年度_2,专项_3" sortable="false" width="120" align="center"></t:dgCol>--%>
   <t:dgCol title="问题地点"  field="hiddenDanger.address.address"   queryMode="single"  sortable="true" width="120" align="center"></t:dgCol>
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  sortable="false" width="120" align="center"></t:dgCol>
   <t:dgCol title="责任部门"  field="hiddenDanger.dutyUnit.departname" query="false" queryMode="single"  sortable="false" width="80" align="center"></t:dgCol>
   <t:dgCol title="责任人"  field="hiddenDanger.dutyMan" queryMode="group" sortable="true" width="120" align="center"></t:dgCol>

   <t:dgCol title="检查人"  field="hiddenDanger.fillCardManNames" queryMode="single"  sortable="false" width="100" align="center"></t:dgCol>
   <t:dgCol title="信息来源"  field="hiddenDanger.manageType"  query="true" dictionary="manageType" queryMode="single"  sortable="true" width="120" align="center"></t:dgCol>
   <%--<t:dgCol title="检查类型"  field="hiddenDanger.examType"  query="true" dictionary="examType" queryMode="single"  sortable="true" width="120" align="center"></t:dgCol>--%>
   <%--<t:dgCol title="隐患类型"  field="hiddenDanger.hiddenType"  query="true" dictionary="hiddenType"  queryMode="single"  sortable="true" width="70" align="center"></t:dgCol>--%>
      <t:dgCol title="隐患类型"  field="hiddenDanger.riskType"   dictionary="risk_type"  queryMode="single"  sortable="true" width="70" align="center"></t:dgCol>
   <t:dgCol title="隐患等级"  field="hiddenDanger.hiddenNature"   dictionary="hiddenLevel"  queryMode="single"  sortable="true" width="70" align="center"></t:dgCol>
   <t:dgCol title="隐患Id"  field="hiddenDanger.id"  hidden="true"></t:dgCol>

   <t:dgCol title="问题描述"  field="hiddenDanger.problemDesc" formatterjs="valueTitle"  queryMode="group"  sortable="false" width="300"></t:dgCol>
      <t:dgCol title="风险描述"  field="hiddenDanger.riskId.riskDesc" formatterjs="valueTitle"  queryMode="group"  sortable="false" width="250"></t:dgCol>
      <c:if test="${xiezhuang eq 'true'}">
          <t:dgCol title="危害因素"  field="hiddenDanger.hazardFactorName" queryMode="group" formatterjs="valueTitle" sortable="false" width="150" align="center"></t:dgCol>
      </c:if>
      <t:dgCol title="处理方式"  field="hiddenDanger.dealType" replace="限期整改_1,现场处理_2" width="80" align="center"></t:dgCol>
      <t:dgCol title="限期日期"  field="hiddenDanger.limitDate" formatter="yyyy-MM-dd" width="80" align="center"></t:dgCol>
      <c:if test="${beixulou eq 'true'}">
          <t:dgCol title="限期班次"  field="hiddenDanger.limitShift" dictionary="workShift" width="80" align="center"></t:dgCol>
      </c:if>
      <c:if test="${beixulou ne 'true'}">
          <t:dgCol title="限期班次"  field="hiddenDanger.limitShift" dictionary="workShift" hidden="true" width="80" align="center"></t:dgCol>
      </c:if>
      <t:dgCol title="整改日期"  field="modifyDate" formatter="yyyy-MM-dd" query="true"  queryMode="single"  sortable="true" width="80" align="center"></t:dgCol>
      <t:dgCol title="整改人"  field="modifyMan"  dictionary="t_s_base_user,id,realname,where 1=1"   queryMode="group"  sortable="true" width="100" align="center"></t:dgCol>
   <t:dgCol title="复查日期"  field="reviewDate" formatter="yyyy-MM-dd"   queryMode="group"  sortable="true" width="80" align="center"></t:dgCol>
   <t:dgCol title="复查人"  field="reviewMan"  dictionary="t_s_base_user,id,realname,where 1=1"  queryMode="group"  sortable="true" width="100" align="center"></t:dgCol>
      <c:if test="${beixulou eq 'true'}">
          <t:dgCol title="其他复查人"  field="reviewManOther" formatterjs="valueTitle"  width="100" align="center"></t:dgCol>
      </c:if>

      <t:dgCol title="处理状态"  field="handlelStatus" dictionary="handelStatus" query="true"  queryMode="single"  sortable="true" width="60" align="center"></t:dgCol>

      <t:dgCol title="驳回备注"  field="rollBackRemark" formatterjs="valueTitle" hidden="false" query="false" queryMode="group"  sortable="false" width="300"></t:dgCol>
   <t:dgCol title="录入人"  field="createName"   queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
   <t:dgCol title="录入日期"  field="createDate" formatter="yyyy-MM-dd"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  sortable="true" width="120" align="center"></t:dgCol>
   <%--<t:dgCol title="是否上报集团" dictionary="isReportToGroup" field="reportGroupStatus" hidden="false"  sortable="true" width="120" align="center"></t:dgCol>--%>

      <%--<t:dgToolBar title="上报省局平台" icon="icon-putout" url="tBHiddenDangerHandleController.do?hiddenDangerReport" funname="goReport" operationCode="report" ></t:dgToolBar>--%>
      <%--<t:dgToolBar title="上报集团" icon="icon-putout" url="tBHiddenDangerHandleController.do?reportGroup" funname="reportGroup" operationCode="reportGroup" ></t:dgToolBar>--%>
      <%--<t:dgToolBar title="全部上报省局平台" icon="icon-putout" url="tBHiddenDangerHandleController.do?hiddenDangerReport" funname="goReportAll" operationCode="goReportAll"></t:dgToolBar>--%>
      <%--<t:dgToolBar title="查看" icon="icon-search" url="riskManageResultController.do?updateHd" funname="detail" width="950" height="550" operationCode="detail"></t:dgToolBar>--%>
      <t:dgToolBar title="查看" icon="icon-search" url="tBHiddenDangerHandleController.do?goUpdate&load=detail" funname="detail" width="950" height="550" operationCode="detail"></t:dgToolBar>
      <t:dgToolBar operationCode="edit" title="管理员编辑" icon="icon-edit"  funname="goEdits" ></t:dgToolBar>
      <t:dgToolBar title="批量删除" icon="icon-remove" url="tBHiddenDangerHandleController.do?doBatchDel" funname="deleteALLSelect" operationCode="batchdelete"></t:dgToolBar>
      <%--<t:dgToolBar operationCode="edit" title="编辑" icon="icon-edit"  funname="goEdit" ></t:dgToolBar>&ndash;%&gt;--%>
      <%--<t:dgToolBar operationCode="rollback" title="驳回" icon="icon-edit"  funname="goRollback" ></t:dgToolBar>--%>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>
      <c:if test="${excelIsHide == '0'}">
          <t:dgToolBar title="已闭环隐患导入（实施人员使用）" icon="icon-put" funname="ImportXls"></t:dgToolBar>
          <t:dgToolBar title="已闭环模板下载（实施人员使用）" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
      </c:if>
      <t:dgToolBar title="查看日志" icon="icon-search" funname="searchLog" width="950" height="550" operationCode="log"></t:dgToolBar>

      <%--<t:dgToolBar title="生成安全日报表" icon="icon-putout" funname="ExportReportXls" operationCode="exportReport"></t:dgToolBar>--%>
      <c:if test="${isSunAdmin == 'YGADMIN'}">
          <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
      </c:if>
  </t:datagrid>
  </div>
 </div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
//     function detail(title,url, id,width,height) {
//         var rowsData = $('#'+id).datagrid('getSelections');
//         if (!rowsData || rowsData.length == 0) {
//             tip('请选择查看项目');
//             return;
//         }
//         if (rowsData.length > 1) {
//             tip('请选择一条记录再查看');
//             return;
//         }
//         url += '&load=detail&id='+rowsData[0]["hiddenDanger.id"];
//         createdetailwindow(title,url,width,height);
//     }

     /**
 * 上报到集团
 * */
 function reportGroup(){
     var ids = [];
     var rows = $("#tBHiddenDangerHandleList").datagrid('getSelections');
     if (rows.length > 0) {
         for ( var i = 0; i < rows.length; i++) {
             ids.push(rows[i].id);
         }
         $.ajax({
             url: "tBHiddenDangerHandleController.do?reportGroup&ids="+ids+"&tt="+new Date(),
             type: 'POST',
             error: function(){
             },
             success: function(data){
                 data = $.parseJSON(data);
                 tip(data.msg);
                 reloadTable();
             }
         });
     } else {
         tip("请选择需要上报的数据");
         return;
     }
 }

 function goReportAll(title,url,gname) {
     gridname=gname;
     var ids = [];
     var type = 'all';

     $.dialog.confirm('你确定上报全部数据吗?', function(r) {
         this.close();
         if (r) {
             $.ajax({
                 url : url,
                 type : 'post',
                 cache : false,
                 data : {
                     ids : ids.join(','),
                     type : type
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
                         tBHiddenDangerHandleListsearch();
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
     });
 }

     function goReport(title,url,gname) {
         gridname=gname;
         var ids = [];
         var type = 'choose';
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
         } else {
             type = 'all';
         }
         $.dialog.confirm('你确定上报所选中数据吗?', function(r) {
             this.close();
             if (r) {
                 $.ajax({
                     url : url,
                     type : 'post',
                     cache : false,
                     data : {
                         ids : ids.join(','),
                         type : type
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
         });
     }
     function addmask() {
         $.messager.progress({
             text : "正在上传数据......",
             interval : 100
         });
         setTimeout("$.messager.progress('close')",2000);
     }
     function dblClickDetail(rowIndex,rowData){
         var id=rowData.id;
         var url = "tBHiddenDangerHandleController.do?goUpdate&load=detail&id="+id;
         createdetailwindow("查看",url,950,600);
     }
     $(function() {
         var datagrid = $("#tBHiddenDangerHandleListtb");
         datagrid.find("form[id='tBHiddenDangerHandleListForm']>span:first").before($("#tempSearchColums div[name='departSearchColums']").html());
         datagrid.find("form[id='tBHiddenDangerHandleListForm']>span:last").before($("#tempSearchColums div[name='searchColums']").html());
         $("#tempSearchColums").empty();
         getDepartMagicSuggest($("#departnameSuggest"), $("#departname"));
         getDepartMagicSuggest($("#checkDepartNameSuggest"), $("#checkDepartName"));
         getAddressMagicSuggest($("#addressSelect"), $("[name='tBAddressInfoEntity_id']"));
         //getUserMagicSuggest($("#fillCardManSelect"), $("[name='hiddenDanger.fillCardMan.id']"));
         var magicsuggestFillCardManSelect = "";
         magicsuggestFillCardManSelect = $('#fillCardManSelect').magicSuggest({
             allowFreeEntries: true,
             data:'magicSelectController.do?getUserList',
             valueField:'id',
             placeholder:'输入或选择',
             maxSelection:1,
             selectFirst: true,
             matchField:['spelling','realName','userName','fullSpelling'],
             highlight: false,
             displayField:'realName'
         });
         $(magicsuggestFillCardManSelect).on('selectionchange', function (e, m) {
             $("#fillCardManId").val(magicsuggestFillCardManSelect.getValue());
         });
         var magicsuggestHiddenNatureSelect = "";
         magicsuggestHiddenNatureSelect = $('#hiddenNatureSelect').magicSuggest({
             data: 'magicSelectController.do?getHiddenNature',
             allowFreeEntries: false,
             valueField: 'typecode',
             placeholder: '输入或选择',
             maxSelection: 5,
             selectFirst: true,
             highlight: false,
             displayField: 'typename'
         });
         $(magicsuggestHiddenNatureSelect).on('selectionchange', function (e, m) {
             $("#hiddenNatureList").val(magicsuggestHiddenNatureSelect.getValue());
         });
         var magicsuggestRiskTypeSelect = "";
         magicsuggestRiskTypeSelect = $('#riskTypeSelect').magicSuggest({
             data: 'magicSelectController.do?getRiskType',
             allowFreeEntries: false,
             valueField: 'typecode',
             placeholder: '输入或选择',
             maxSelection: 20,
             selectFirst: true,
             highlight: false,
             displayField: 'typename'
         });
         $(magicsuggestRiskTypeSelect).on('selectionchange', function (e, m) {
             $("#riskTypeList").val(magicsuggestRiskTypeSelect.getValue());
         });
    });



//编辑
function goEdits(title,addurl,gname){
    var height = $(document).height()-100;
    var width = $(document).width()-60;
    var rows =$('#'+gname).datagrid("getSelections");
    if(rows.length==0){
        tip("请选择编辑的问题");
        return false;
    }else if(rows.length>1){
        tip("每次只能编辑一条问题");
        return false;
    }else{
        var id = rows[0].id;
        addOneTab('编辑','tBHiddenDangerHandleController.do?goAdminEdit&id='+id+'&width='+width+'&height='+height,'default');
    }
}
     //驳回
     function goRollback(title,addurl,gname){
         var rows=$('#'+gname).datagrid("getSelections");
         if(rows.length==0){
             tip("请选择驳回的问题");
             return false;
         }
         for(var i =0 ;i<rows.length;i++){
             var isEditable = checkEditable(rows[i].id);
             if(!isEditable){
                 tip("仅能操作待整改条目");
                 return false;
             }
         }
         var ids = new Array();
         for(var i =0 ;i<rows.length;i++){
             ids.push(rows[i].id);
         }
         var id =  ids.join(",");

         var okButton;
         if ($("#langCode").val() == 'en') {
             okButton = "Ok";
         } else {
             okButton = "确定";
         }
         $.dialog({
             id: 'LHG1976D',
             title: "驳回确认",
             max: false,
             min: false,
             drag: false,
             resize: false,
             content: 'url:tBHiddenDangerHandleController.do?goRollBack',
             lock: true,
             button: [{
                 name: okButton,
                 focus: true,
                 callback: function () {
                     iframe = this.iframe.contentWindow;
                     var rollBackRemark = $('#rollBackRemark', iframe.document).val();
                     $.ajax({
                         url: "tBHiddenDangerHandleController.do?doRollback",
                         type: "post",
                         async: true,
                         data: {'ids': id, rollBackRemark: rollBackRemark},
                         dataType: "json",
                         success: function (data) {
                             tip(data.msg);
                             /*if (data.success) {
                              reloadTable();
                              }*/
                             $("#tBHiddenDangerHandleList").datagrid("clearSelections");
                             $("#tBHiddenDangerHandleList").datagrid("reload");
                         },
                         error: function () {
                         }
                     });
                     this.close();
                     return false;
                 }
             },{
                 name: "关闭",
                 focus: true,
                 callback: function () {
                     this.close();
                 }
             }],
             close: function () {
             }
         });

     }

function checkEditable(id){
    var p = false;
    var isAdmin = false;
    var handleStatus = "";
    $.ajax({
        url:'tBHiddenDangerHandleController.do?getRoleAndHandleStatus',
        async:false,
        data:{id:id},
        success:function(data){
            data = $.parseJSON(data);
            isAdmin = data.isAdmin;
            handleStatus = data.handleStatus;
        },
        error:function(data){
        }
    });
    if(isAdmin){
        p = true;
    }else{
        if(handleStatus == "1"){
            //未整改
            p = true;
        }
    }
    return p;
}

//模板下载
function ExportXlsByT() {
    JeecgExcelExport("tBHiddenDangerHandleController.do?exportXlsByT","tBHiddenDangerHandleList");
}

//导入
function ImportXls() {
    openuploadwin('Excel导入', 'tBHiddenDangerHandleController.do?upload', "tBHiddenDangerHandleList");
}

//导出
function ExportXls() {
    var rows = $("#tBHiddenDangerHandleList").datagrid('getSelections');
    if (rows.length == 0) {
        JeecgExcelExport("tBHiddenDangerHandleController.do?queryListExportXls", "tBHiddenDangerHandleList");
    } else if (rows.length >=1) {
        var idsTemp = new Array();
        for (var i = 0; i < rows.length; i++) {
            idsTemp.push(rows[i].id);
        }
        var idt = idsTemp.join(",");
        $.dialog.confirm("是否确认导出"+idsTemp.length+"条记录？", function () {
            JeecgExcelExport("tBHiddenDangerHandleController.do?queryListExportXls&ids="+idt, "tBHiddenDangerHandleList");
        });
    }
}

     function ExportReportXls() {
         var okButton;
         if ($("#langCode").val() == 'en') {
             okButton = "Ok";
         } else {
             okButton = "确定";
         }

         $.dialog({
             id: 'LHG1976D',
             title: "选择日期",
             width:'250px',
             height:'100px',
             max: false,
             min: false,
             drag: false,
             resize: false,
             content: 'url:tBSafetyDailyReportController.do?goReport',
             lock: true,
             button: [{
                 name: okButton,
                 focus: true,
                 callback: function () {
                     iframe = this.iframe.contentWindow;
                     var reportDate = $('#reportDate', iframe.document).val();
                     if(reportDate != null && reportDate != ""){
                         JeecgExcelExport("tBSafetyDailyReportController.do?exportXls&reportDate="+reportDate,"tBHiddenDangerHandleList");
                     }else{
                         tip("请先选择报表日期！");
                     }
                     this.close();
                     return false;
                 }
             },{
                 name: "关闭",
                 focus: true,
                 callback: function () {
                     this.close();
                 }
             }],
             close: function () {
             }
         });
     }

     function replaceProblemDesc(){
         var problemDescCells = $("td[field='hiddenDanger.problemDesc']:gt(0)");
         problemDescCells.each(function(){
             var problemDesc = $(this).children().text();
             if(problemDesc.indexOf("\\r\\n") >= 0){
                 problemDesc = problemDesc.replace(/[\\r\\n]/g, '');
                 $(this).children().empty();
                 $(this).children().append(problemDesc);
             }
         });
     }
 function loadSuccess(){
     replaceProblemDesc();
     $("td[field='hiddenDanger.limitDate']:gt(0)").each(function(){
         var limitDate = $(this).children().text();
         if(limitDate  < '${dateNow}'){
             var status = $(this).parent().children("td[field='handlelStatus']").children().text();
             if(status == '未整改'){
                 $(this).parent().css("background-color","#FFD2D2");
             }
         }
     });
 }

/**
 *  阳光账号隐藏数据功能
 * */
function sunHidden() {
    var rows = $("#tBHiddenDangerHandleList").datagrid('getSelections');
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
            url: 'tBHiddenDangerHandleController.do?sunshine',
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
window.top["reload_tBHiddenDangerHandleList"]=function(){
    $("#tBHiddenDangerHandleList").datagrid( "load");
};
window.top["tip_tBHiddenDangerHandleList"]=function(){
    tip(this.msg);
};

    //查看日志
    function searchLog(){
        var rows = $("#tBHiddenDangerHandleList").datagrid('getSelections');
        if(rows.length==0){
            tip("请选择隐患!");
            return false;
        }else if(rows.length>1){
            tip("每次只能选择一个隐患查看日志!");
            return false;
        }else{
            var examId = rows[0]["hiddenDanger.id"];
            $.dialog(
                {content: "url:tBHiddenDangerHandleController.do?logList&examId="+examId, zIndex: 2100, title: "日志列表" , lock: true, parent: windowapi, width: 860, height: 480, left: '50%', top: '40%', opacity: 0.4,}
            );
        }
    }
 </script>