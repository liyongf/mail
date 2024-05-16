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
    <div name="postSearchColums" >
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="岗位">岗位：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="postSelect" style="width: 130px;height: 15px"></div>
                 <input id="postId" type="hidden" name="postId">
             </span>
        </span>
    </div>
    <div name="searchColums" >
        <br>
        <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px">
        <input  name="queryHandleStatus" value="0" type="hidden">
           <label>
               <input name="queryHandleStatusTem" type="radio" value="0" checked="checked"> 草稿
           </label>
           <label>
                <input name="queryHandleStatusTem" type="radio" value="1">已上报</label>
           <label>
				<input name="queryHandleStatusTem" type="radio" value="2">已退回
           </label>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hazardFactorsList" checkbox="true" pagination="true" fitColumns="false" onDblClick="dblClickDetail" title="${identificationTypeName}" actionUrl="riskIdentificationController.do?mainDatagrid&identificationType=${identificationType}&riskManageHazardFactorId=${riskManageHazardFactorId}&riskTaskId=${riskTaskId}&riskManageTaskAllId=${riskManageTaskAllId}&riskManageTaskAllId=${riskManageTaskAllId}&riskManageTaskAllIdYH=${riskManageTaskAllIdYH}" idField="id" fit="true" queryMode="group" sortOrder="desc" sortName="createDate">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="风险点Id"  field="address.id" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="岗位Id"  field="post.id" hidden="true" queryMode="single"  width="120"></t:dgCol>
      <c:if test="${identificationType ne '4'}">
   <t:dgCol title="风险点"  field="address.address"  queryMode="single"  width="120" query="false"></t:dgCol>
      </c:if>
      <c:if test="${identificationType eq '4'}">
          <t:dgCol title="岗位"  field="post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
      </c:if>
   <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" query="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="风险描述"  field="riskDesc"  formatterjs="valueTitle" queryMode="group"  width="200"></t:dgCol>
   <t:dgCol title="风险等级"  field="riskLevel" dictionary="factors_level" queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="危害因素和管控措施" align="center"  field="hazardFactortsNum" url="riskIdentificationController.do?wxysList&id={id}&load=detail" queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="最高管控层级"  field="manageLevel" dictionary="identifi_mange_level" queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="最高管控责任人"  field="dutyManager"  queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="评估日期"  field="identifiDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="解除日期"  field="expDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="信息来源"  field="identificationType" dictionary="identifi_from" queryMode="single" query="true"  width="120" ></t:dgCol>

      <c:if test="${identificationType eq '3'}">
          <t:dgCol title="专项辨识类型"  field="specificType"  queryMode="single" dictionary="specificType" width="120" query="false"></t:dgCol>
          <t:dgCol title="专项辨识名称"  field="specificName"  queryMode="single"  width="120" query="false"></t:dgCol>
      </c:if>
   <t:dgToolBar title="录入" icon="icon-add" url="riskIdentificationController.do?goAdd&identificationType=${identificationType}&riskTaskId=${riskTaskId}" funname="goadd" ></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="riskIdentificationController.do?goUpdate" funname="goupdate"  ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="riskIdentificationController.do?doBatchDel" funname="deleteALLSelect"  ></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="riskIdentificationController.do?goDetail" funname="godetail"></t:dgToolBar>
   <t:dgToolBar title="提交审批" icon="icon-edit" url="riskIdentificationController.do?goReview" funname="goReview"></t:dgToolBar>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <%--导入风险数据--%>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXlsRisk"></t:dgToolBar>
   <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">

     function dblClickDetail(rowIndex,rowData){
         // var id=rowData.id;
         // var url = "tBHealthTrainController.do?goUpdate&load=detail&id="+id;
         addOneTab("${identificationTypeName}录入","riskIdentificationController.do?goAdd&identificationType=${identificationType}&addressId="+rowData["address.id"]+"&postId="+rowData["post.id"]+"&id="+rowData.id+"&load=detail","default");
         // createdetailwindow("查看",url);
     }
     function godetail(){
         var rows = $("#hazardFactorsList").datagrid('getSelections');
         if(rows== null || rows.length < 1){
             tip("请选择一条需要查看的数据!");
         }else{
             addOneTab("${identificationTypeName}查看","riskIdentificationController.do?goAdd&identificationType=${identificationType}&riskTaskId=${riskTaskId}&riskManageTaskAllId=${riskManageTaskAllId}&addressId="+rows[0]["address.id"]+"&postId="+rows[0]["post.id"]+"&id="+rows[0].id+"&load=detail","default");
         }
     }
     function goupdate(){
         var rows = $("#hazardFactorsList").datagrid('getSelections');
         if(rows== null || rows.length != 1){
             tip("请选择一条需要修改的数据!");
         }else{
             var identificationType = "${identificationType}";
             if(identificationType != '4'){
                addOneTab("${identificationTypeName}录入","riskIdentificationController.do?goAdd&identificationType=${identificationType}&riskTaskId=${riskTaskId}&riskManageTaskAllId=${riskManageTaskAllId}&riskManageTaskAllIdYH=${riskManageTaskAllIdYH}&addressId="+rows[0]["address.id"]+"&id="+rows[0].id,"default");
             }else{
                 addOneTab("${identificationTypeName}录入","riskIdentificationController.do?goAdd&identificationType=${identificationType}&riskTaskId=${riskTaskId}&riskManageTaskAllId=${riskManageTaskAllId}&riskManageTaskAllIdYH=${riskManageTaskAllIdYH}&postId="+rows[0]["post.id"],"default");
             }
         }
     }
     function goadd(){
         addOneTab("${identificationTypeName}录入","riskIdentificationController.do?goAdd&identificationType=${identificationType}&riskTaskId=${riskTaskId}&riskManageTaskAllId=${riskManageTaskAllId}&riskManageTaskAllIdYH=${riskManageTaskAllIdYH}&addressId=","default");
     }
     function goReview(){
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
                         /*$.ajax({
                             url : "riskIdentificationController.do?doReport",
                             type : 'post',
                             cache : false,
                             data : {
                                 ids : ids.join(',')
                             },
                             beforeSend: function(data){
                                 $.messager.progress({
                                     text : "正在提交数据......",
                                     interval : 100
                                 });
                             },
                             success : function(data) {
                                 var d = $.parseJSON(data);
                                 if (d.success) {
                                     reloadTable();
                                     $("#hazardFactorsList").datagrid('unselectAll');
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
                         });*/
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
 $(document).ready(function(){
     var datagrid = $("#hazardFactorsListtb");
     datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");
     $("#tempSearchColums div[name='searchColums']").remove();
     <c:if test="${identificationType ne '4'}">
         datagrid.find("form[id='hazardFactorsListForm']>span:first").before($("#tempSearchColums div[name='addressSearchColums']").html());
         $("#tempSearchColums div[name='addressSearchColums']").remove();
         getAddressMagicSuggest($("#addressSelect"), $("[name='addressId']"));
     </c:if>
     <c:if test="${identificationType eq '4'}">
         datagrid.find("form[id='hazardFactorsListForm']>span:first").before($("#tempSearchColums div[name='postSearchColums']").html());
         $("#tempSearchColums div[name='postSearchColums']").remove();
        getPostMagicSuggest($("#postSelect"), $("input[name='postId']"));
     </c:if>
     $("a[iconcls='icon-reload']").hide();

     if("${identificationType}"!=""){
         datagrid.find("[name='identificationType']").val("${identificationType}");
         datagrid.find("[name='identificationType']").attr("disabled","disabled");
     }
     $("input[name='queryHandleStatusTem']").change(function() {
         var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
         $("input[name='queryHandleStatus']").val(selectedvalue);

         if(selectedvalue == "1"){
             //已提交，隐藏编辑、删除、录入、导入、导出、模板下载按钮
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","none");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","none");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-add']").css("display","none");
         }else{
             //草稿，显示编辑、删除、录入、导入、导出、模板下载按钮
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-add']").css("display","");
         }
         hazardFactorsListsearch();
     });

     //风险管控显示新录入风险列表时不允许录入（因为录入可能会录入不能地点）
     if("${riskManageHazardFactorId}"!=""||"${riskManageTaskAllId}"!=""){
         $("a[icon='icon-add']").remove()
     }
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'hazardFactorsController.do?upload', "hazardFactorsList");
}

//导入风险数据
function ImportXlsRisk() {
 openuploadwin('Excel导入', 'riskHelpController.do?upload&riskTaskId=${riskTaskId}', "riskList");
}

//导出
function ExportXls() {
	JeecgExcelExport("hazardFactorsController.do?exportXls","hazardFactorsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("hazardFactorsController.do?exportXlsByT","hazardFactorsList");
}

 </script>