<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>

<div id="tempSearchColums" style="display: none">
    <div name="searchColums" style="z-index: 9999">
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span  style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="违章单位">
             	   违章单位：
            </span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <input id="checkOrgIds" name="checkOrgIds" type="hidden">
                <div id="checkOrgSelect" style="width: 130px;height: 15px"></div>
            </span>
        </span>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">违章人员：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="checkManSelect" style="width: 130px;height: 15px"></div>
                <input id="checkManIds" name="checkManIds" type="hidden">
            </span>
        </span>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span  style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="查处单位">
             	   查处单位：
            </span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <input id="dutyOrgIds" name="dutyOrgIds" type="hidden">
               <div id="dutyOrgSelect" style="width: 130px;height: 15px"></div>
            </span>
        </span>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">制止人：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="dutyManNameSelect" style="width: 130px;height: 15px"></div>
                <input id="dutyManIds"  name="dutyManIds" type="hidden">
             </span>
        </span>

         <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="违章地点">违章地点：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">

                <div id="addressSelect" style="width: 130px;height: 15px"></div>
                <input id="tBAddressInfoEntityId" type="hidden" name="tBAddressInfoEntityId">
                 </span>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBThreeViolationsList" checkbox="true" pagination="true" fitColumns="true" title="三违信息" actionUrl="tBThreeViolationsController.do?datagrid" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="违章时间"  field="vioDate" formatter="yyyy-MM-dd" query="true"  queryMode="group" sortable="false" width="80" align="center"></t:dgCol>
      <t:dgCol title="违章时间"  field="queryMonth" formatter="yyyy-MM" hidden="true" query="true"  queryMode="single" sortable="false" width="80" align="center"></t:dgCol>
      <t:dgCol title="班次"  field="shift" hidden="false" dictionary="workShift" query="true"  queryMode="single" sortable="false" width="60" align="center"></t:dgCol>
      <t:dgCol title="查处单位"  field="findUnits"  dictionary="t_s_depart,id,departname,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="违章地点"  field="vioAddress"  dictionary="t_b_address_info,id,address,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
    <t:dgCol title="违章单位"  field="vioUnits"  dictionary="t_s_depart,id,departname,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
      <t:dgCol title="违章人员"  field="vioPeople"   queryMode="group"  width="120" align="center"></t:dgCol>
      <c:if test="${xinchazhuang eq 'true'}">
          <t:dgCol title="职工编号"  field="employeeNum"  queryMode="group"  width="120" align="center"></t:dgCol>
      </c:if>
      <t:dgCol title="违章分类"  field="vioCategory" dictionary="violaterule_wzfl" query="true"  queryMode="single" sortable="false" width="100" align="center"></t:dgCol>
      <%--<t:dgCol title="违章定性"  field="vioQualitative" dictionary="violaterule_wzdx" query="true"  queryMode="single" sortable="false" width="100" align="center"></t:dgCol>--%>
      <t:dgCol title="三违级别"  field="vioLevel" dictionary="vio_level" query="true"  queryMode="single" sortable="false" width="100" align="center"></t:dgCol>
      <t:dgCol title="制止人"  field="stopPeople"  queryMode="group"  width="120" align="center"></t:dgCol>

      <t:dgCol title="三违事实描述"  field="vioFactDesc"  formatterjs="valueTitle"  queryMode="group"  width="120"></t:dgCol>
      <c:if test="${henghe eq 'true'}">
          <t:dgCol title="处理意见"  field="remark"  formatterjs="valueTitle"  queryMode="group"  width="120" align="center"></t:dgCol>
      </c:if>
      <c:if test="${henghe ne 'true'}">
          <t:dgCol title="备注"  field="remark"  formatterjs="valueTitle"  queryMode="group"  width="120" align="center"></t:dgCol>
      </c:if>
      <t:dgCol title="罚款金额" field="fineMoneyTemp" queryMode="group"  width="120" align="center"></t:dgCol>
    <%--<t:dgCol title="工种"  field="workType" hidden="true"   queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="创建人名称"  field="createName" hidden="true"    queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="更新人名称"  field="updateName" hidden="true"   queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
    <%--<t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>--%>
   <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
   <%--<t:dgDelOpt title="删除" url="tBThreeViolationsController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>--%>
   <t:dgToolBar title="录入三违及罚款" icon="icon-add" url="tBThreeViolationsController.do?goAdd" funname="add" operationCode="add"  width="950" height="450"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBThreeViolationsController.do?goUpdate" funname="update" operationCode="update"  width="950" height="450" ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBThreeViolationsController.do?doBatchDel" funname="deleteALLSelect" operationCode="batchdelete" ></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBThreeViolationsController.do?goUpdate" funname="detail" operationCode="detail" ></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls" operationCode="import"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export" ></t:dgToolBar>
      <t:dgToolBar  title="录入罚款" icon="icon-add" url="tBHiddenDangerExamController.do?goLinkFine" funname="goAddFine"  width="950" height="400" operationCode="goAddFine"></t:dgToolBar>
      <t:dgToolBar  title="查看罚款" icon="icon-add" url="tBHiddenDangerExamController.do?goLinkFine" funname="goLinkFine"  width="950" height="400" ></t:dgToolBar>
   <%--<t:dgToolBar title="三违信息上报" icon="icon-reload" url="tBThreeViolationsController.do?uploadThreeViolence" funname="uploadThreeViolence"></t:dgToolBar>--%>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT" operationCode="downloadTmpl"></t:dgToolBar>
      <c:if test="${isSunAdmin == 'YGADMIN'}">
          <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
      </c:if>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/violations/tBThreeViolationsList.js"></script>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
     function goAddFine(){
         var rowsData = $('#tBThreeViolationsList').datagrid('getSelections');
         if(rowsData == null || rowsData.length != 1){
             tip("请选择一条需要关联的三违");
         }else{
             createwindow('录入罚款','tBFineController.do?goAdd&vioId='+rowsData[0].id);
         }
     }

     function goLinkFine(){
         var rowsData = $('#tBThreeViolationsList').datagrid('getSelections');
         if(rowsData == null || rowsData.length != 1){
             tip("请选择一条需要关联罰款的三违");
         }else{
             openwindow('关联罚款','tBFineController.do?chooseVioFineList&busId='+rowsData[0].id,"",1000,500);
         }
     }
 $(document).ready(function(){
    //初始化查询条件
     $("#tBThreeViolationsListForm>span:last").after($("#tempSearchColums div[name='searchColums']").html());

     var html = "<span style='display:-moz-inline-box;display:inline-block;'>";
     html += "<span style='vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap;' title='查询方式'>查询方式：</span>";
     html += "<select style='width:100px;' name='isMonthQuery' onchange='changeQueryType();'>";
     html += "<option value='1'>按月份</option><option value='0'>按起止时间</option></select></span>";

     $("#tBThreeViolationsListForm").prepend(html);
     //先隐藏按时间查询
     $("#tBThreeViolationsListForm").find("input[name='vioDate_begin']").parent().hide();
     $("#tBThreeViolationsListForm").find("input[name='queryMonth']").attr("class", "Wdate").attr("style", "height:30px;width:156px;").click(function () {
         WdatePicker({
             dateFmt: 'yyyy-MM'
         });
     });
     $("#tBThreeViolationsListForm").find("input[name='vioDate_begin']").attr("class", "Wdate").attr("style", "height:30px;width:90px;").click(function () {
         WdatePicker({
             dateFmt: 'yyyy-MM-dd'
         });
     });
     $("#tBThreeViolationsListForm").find("input[name='vioDate_end']").attr("class", "Wdate").attr("style", "height:30px;width:90px;").click(function () {
         WdatePicker({
             dateFmt: 'yyyy-MM-dd'
         });
     });

     //初始化列表
     $("#tempSearchColums").empty();
     var checkOrgSelect = getDepartMagicSuggest($("#checkOrgSelect"), $("#checkOrgIds"));
     var dutyOrgSelect = getDepartMagicSuggest($("#dutyOrgSelect"), $("#dutyOrgIds"));
     var dutyManNameSelect = getUserMagicSuggestAllowFreeEntries($('#dutyManNameSelect'), $("#dutyManIds"));
     var checkManSelect = getUserMagicSuggestAllowFreeEntries($('#checkManSelect'), $("#checkManIds"));
     var addressSelect = getAddressMagicSuggest($('#addressSelect'), $("#tBAddressInfoEntityId"));

     //重置按钮时间绑定
     $("a[iconcls='icon-reload']").on("click", function(){
         checkOrgSelect.clear();
         dutyOrgSelect.clear();
         dutyManNameSelect.clear();
         checkManSelect.clear();
         addressSelect.clear();
     });
 });

//导出

 function ExportXls() {
     var rows = $("#tBThreeViolationsList").datagrid('getSelections');
     if (rows.length == 0) {
         JeecgExcelExport("tBThreeViolationsController.do?exportXls", "tBThreeViolationsList");
     } else if (rows.length >=1) {
         var idsTemp = new Array();
         for (var i = 0; i < rows.length; i++) {
             idsTemp.push(rows[i].id);
         }
         var idt = idsTemp.join(",");
         $.dialog.confirm("是否确认导出"+idsTemp.length+"条记录？", function () {
             JeecgExcelExport("tBThreeViolationsController.do?exportXls&ids="+idt, "tBThreeViolationsList");
         });
     }
 }
 //导入
 function ImportXls() {
     openuploadwin('三违导入', 'tBThreeViolationsController.do?upload', "tBThreeViolationsList");
 }

 //模板下载
 function ExportXlsByT() {
     JeecgExcelExport("tBThreeViolationsController.do?exportXlsByT","tBThreeViolationsList");
 }

 function changeQueryType(){
     var type = $("select[name='isMonthQuery']").val();
     if(type=="1"){
         $("#tBThreeViolationsListForm").find("input[name='vioDate_begin']").parent().hide();
         $("#tBThreeViolationsListForm").find("input[name='queryMonth']").parent().show();
     }else{
         $("#tBThreeViolationsListForm").find("input[name='vioDate_begin']").parent().show();
         $("#tBThreeViolationsListForm").find("input[name='queryMonth']").parent().hide();
     }
 }

 //作业过程上报
 /**
  * 作业过程上报
  *
  * 张赛超
  */
 function uploadThreeViolence(title,url,gname) {
     var ids = [];
     var rows = $("#"+gname).datagrid('getSelections');
     if (rows.length > 0) {
         $.dialog.setting.zIndex = getzIndex(true);
         $.dialog.confirm('你确定上报该数据吗?', function(r) {
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
                     beforeSend: function(data){
                         $.messager.progress({
                             text : "正在上报数据......",
                             interval : 100
                         });
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
     } else {
         tip("请选择需要上报的数据");
     }
 }

 /**
  *  阳光账号隐藏数据功能
  * */
 function sunHidden() {
     var rows = $("#tBThreeViolationsList").datagrid('getSelections');
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
             url: 'tBThreeViolationsController.do?sunshine',
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