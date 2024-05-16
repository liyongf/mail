<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="addressSearchColums" >
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="风险点">风险点：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="addressSelect" style="width: 130px;height: 15px"></div>
                 <input id="addressId" type="hidden" name="addressId">
             </span>
        </span>
    </div>
<c:if test="${newPost ne 'true'}">
    <div name="postSearchColums" >
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="岗位">岗位：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="postSelect" style="width: 130px;height: 15px"></div>
                 <input id="postId" type="hidden" name="postId">
             </span>
        </span>
    </div>
</c:if>
</div>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="riskList" checkbox="true" onDblClick="dbClick" pagination="true" fitColumns="false" title="${identificationTypeName}" actionUrl="riskIdentificationController.do?queryListDatagrid&identificationType=${identificationType}" idField="id" fit="true" queryMode="group" sortName="expDate" sortOrder="desc" >
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="风险点Id"  field="address.id" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="岗位Id"  field="post.id" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="风险点"  field="address.address"  queryMode="single"  width="120" query="false"></t:dgCol>
      <c:if test="${newPost ne 'true'}">
          <t:dgCol title="岗位"  field="post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
      </c:if>
   <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="风险描述"  field="riskDesc"  formatterjs="valueTitle" queryMode="group"  width="200"></t:dgCol>
   <t:dgCol title="风险等级"  field="riskLevel" query="true" dictionary="factors_level" queryMode="single" defaultVal="${riskLevel}" width="120" ></t:dgCol>
   <t:dgCol title="危害因素和管控措施" align="center"  field="hazardFactortsNum" url="riskIdentificationController.do?wxysList&id={id}&load=detail" queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="最高管控层级"  field="manageLevel" query="true" dictionary="identifi_mange_level"  queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="最高管控责任人"  field="dutyManager" query="true" queryMode="single"  width="120" ></t:dgCol>
   <t:dgCol title="评估日期"  field="identifiDate" formatter="yyyy-MM-dd"  query="true" queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="解除日期"  field="expDate" formatter="yyyy-MM-dd" query="true"  queryMode="group"  width="120" ></t:dgCol>
  <t:dgCol title="状态"  field="status" dictionary="identifi_status"  queryMode="group"  width="120" ></t:dgCol>
  <t:dgCol title="信息来源"  field="identificationType" dictionary="identifi_from" queryMode="single" query="true"  width="120" ></t:dgCol>
      <t:dgCol title="年度"  field="queryYear" formatter="yyyy" hidden="true" query="true"  queryMode="single" sortable="false" width="80" align="center"></t:dgCol>
  <t:dgToolBar title="查看" icon="icon-search" url="riskIdentificationController.do?goDetail" funname="godetail"></t:dgToolBar>
      <c:if test="${isAdmin eq '1'}">
          <t:dgToolBar title="动态调整风险等级" icon="icon-edit" url="riskIdentificationController.do?wxysList&load=detail&isAdmin=true"  funname="updateLevel" ></t:dgToolBar>
          <t:dgToolBar title="管理员编辑" icon="icon-edit" url="riskIdentificationController.do?goUpdate" funname="goupdate"></t:dgToolBar>
          <t:dgToolBar title="管理员删除"  icon="icon-remove" url="riskIdentificationController.do?delRisk" funname="deleteALLSelect"></t:dgToolBar>
      </c:if>

      <%-- 导入只由内部人员使用，不提供给矿上  --%>
      <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls" ></t:dgToolBar> --%>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
     window.top["reload_riskList"]=function(){
         riskListsearch();
     };
     //导入
     function ImportXls() {
         openuploadwin('Excel导入', 'riskHelpController.do?upload', "riskList");
     }

     function dbClick(rowIndex,rowData){
         addOneTab("风险查看","riskIdentificationController.do?goAdd&identificationType="+rowData["identificationType"]+"&addressId="+rowData["address.id"]+"&postId="+rowData["post.id"]+"&load=detail","default");
     }
     function godetail(){
         var rows = $("#riskList").datagrid('getSelections');
         if(rows== null || rows.length != 1){
             tip("请选择一条需要查看的数据!");
         }else{
             addOneTab("风险查看","riskIdentificationController.do?goAdd&identificationType="+rows[0]["identificationType"]+"&addressId="+rows[0]["address.id"]+"&postId="+rows[0]["post.id"]+"&id="+rows[0].id+"&load=detail","default");
         }
     }


     $(document).ready(function(){
         var datagrid = $("#riskListtb");
         datagrid.find("form[id='riskListForm']>span:first").before($("#tempSearchColums div[name='postSearchColums']").html());
         datagrid.find("form[id='riskListForm']>span:first").before($("#tempSearchColums div[name='addressSearchColums']").html());
         datagrid.find("input[name='queryYear']").attr("class", "Wdate").attr("style", "height:30px;width:100px;").click(function () {
             WdatePicker({
                 dateFmt: 'yyyy'
             });
         });
         $("#tempSearchColums").empty();
         getAddressMagicSuggest($("#addressSelect"), $("[name='addressId']"));
         getPostMagicSuggest($("#postSelect"), $("input[name='postId']"));
         $("span[title='最高管控责任人']").css("width","100px");
         $("select[name='identificationType']>option[value='1']").remove();
         $("select[name='identificationType']>option[value='4']").remove();
    });
     //导出
     function ExportXls() {
         var rows = $("#riskList").datagrid('getSelections');
         if (rows.length == 0) {
             //tip("请选择需要导出的数据!");
             JeecgExcelExport("riskIdentificationController.do?exportXls", "riskList");
         } else if (rows.length >=1) {
             var idsTemp = new Array();
             for (var i = 0; i < rows.length; i++) {
                 idsTemp.push(rows[i].id);
             }
             var idt = idsTemp.join(",");
             $.dialog.confirm("是否确认导出"+idsTemp.length+"条记录？", function () {
                 JeecgExcelExport("riskIdentificationController.do?exportXls&ids="+idt, "riskList");
             });
         }
     }


     function editRiskLevel(){
         //选中
         var rows = $("#hazardFactorsList").datagrid('getSelections');
         if(rows== null || rows.length < 1){
             tip("请选择一条要提交审批的数据!");
         }else{
             var ids = new Array();
             for(var i =0 ;i<rows.length ;i++){
                 ids.push(rows[i].id);
             }
             $.ajax({
                 url : "riskIdentificationController.do?isReport",
                 type : 'post',
                 cache : false,
                 async: true,
                 data : {
                     ids:ids.join("\',\'"),
                 },
                 success : function(data) {
                     var d = $.parseJSON(data);
                     if (d=="1") {
                         openwindow("提交审批", "riskIdentificationController.do?goReport&ids=" + ids, "hazardFactorsList", 600, 450);
                     }else if(d=="0"){
                         tip("风险信息尚未填写完整");
                     }else if(d=="-1"){
                         tip("关联中存在未审核的危害因素");
                     }else{
                         tip("请选择未提交的风险");
                     }
                 }
             });
         }
     }

     function updateLevel() {
         var rows = $("#riskList").datagrid('getSelections');
         if (rows.length != 1) {
            tip("请选择要编辑的一条数据");
         } else{
             var url = "riskIdentificationController.do?wxysList&load=detail&isAdmin=true&id="+rows[0].id
             createdetailwindow('动态调整风险等级',url,900,450);
         }
     }

     function deleteALLSelect(title,url,gname) {
         gridname=gname;
         var ids = [];
         var rows = $("#"+gname).datagrid('getSelections');
         if (rows.length > 0) {
             $.dialog.setting.zIndex = getzIndex(true);
             $.dialog.confirm('你确定永久删除该风险吗?', function(r) {
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
                                 text : "正在删除数据......",
                                 interval : 100
                             });
                         },
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
                         complete: function(data){
                             $.messager.progress('close');
                         }
                     });
                 }
             });
         } else {
             tip("请选择需要删除的数据");
         }
     }

     function goupdate(){
         var rows = $("#riskList").datagrid('getSelections');
         if(rows== null || rows.length != 1){
             tip("请选择一条需要修改的数据!");
         }else{
             $.ajax({
                 url : "riskIdentificationController.do?getIdentificationType",
                 type : 'post',
                 cache : false,
                 async: true,
                 data : {
                     id:rows[0].id
                 },
                 success : function(data) {
                     var identificationType = $.parseJSON(data);
                     if(identificationType != '4'){
                         addOneTab("录入","riskIdentificationController.do?goAdd&identificationType="+identificationType+"&addressId="+rows[0]["address.id"]+"&id="+rows[0].id,"default");
                     }else{
                         addOneTab("录入","riskIdentificationController.do?goAdd&identificationType="+identificationType+"&postId="+rows[0]["post.id"],"default");
                     }
                 }
             });
         }
     }
 </script>