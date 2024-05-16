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
    <div name="searchColums" >
        <br>
        <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px">
        <input  name="queryHandleStatus" value="1" type="hidden">
           <label>
                <input name="queryHandleStatusTem" type="radio" value="1" checked="checked">待审核</label>
           <label>
				<input name="queryHandleStatusTem" type="radio" value="2">已审核
           </label>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="hazardFactorsList" checkbox="true" pagination="true" fitColumns="false" title="${identificationTypeName}" actionUrl="riskIdentificationController.do?reviewListDatagrid" idField="id" fit="true" queryMode="group" onDblClick="godetail1" sortOrder="desc" sortName="updateDate">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="风险点Id"  field="address.id" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="岗位Id"  field="post.id" hidden="true" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="风险点"  field="address.address"  queryMode="single"  width="120" query="false"></t:dgCol>
      <c:if test="${newPost ne 'true'}">
          <t:dgCol title="岗位"  field="post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
      </c:if>
   <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="风险描述"  field="riskDesc" formatterjs="valueTitle"  queryMode="group"  width="200"></t:dgCol>
   <t:dgCol title="风险等级"  field="riskLevel" dictionary="factors_level" queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="危害因素和管控措施" align="center"  field="hazardFactortsNum" url="riskIdentificationController.do?wxysList&id={id}&load=detail"  queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="最高管控层级"  field="manageLevel" dictionary="identifi_mange_level" queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="最高管控责任人"  field="dutyManager"  queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="评估日期"  field="identifiDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="解除日期"  field="expDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
   <t:dgCol title="状态"  field="status" replace="待审批_1,退回_2,审批通过_3" queryMode="group"  width="100" ></t:dgCol>
   <t:dgCol title="信息来源"  field="identificationType" dictionary="identifi_from" queryMode="single" query="true"  width="120"  ></t:dgCol>
      <t:dgCol title="提交人"  field="submitMan" dictionary="t_s_base_user,id,realname,where 1=1" queryMode="single"  width="100"  ></t:dgCol>
      <t:dgCol title="审核人"  field="reviewMan" dictionary="t_s_base_user,id,realname,where 1=1" queryMode="single"  width="100"  ></t:dgCol>
      <t:dgToolBar title="查看" icon="icon-search" url="riskIdentificationController.do?goDetail" funname="godetail"></t:dgToolBar>
   <t:dgToolBar title="审批" icon="icon-edit" url="riskIdentificationController.do?goReview" funname="goReview"></t:dgToolBar>
      <t:dgToolBar title="批量审批" icon="icon-edit" url="riskIdentificationController.do?goAllReview" funname="goAllReview"></t:dgToolBar>
      <c:if test="${isAdmin eq '1'}">
          <t:dgToolBar title="管理员编辑" icon="icon-edit" url="riskIdentificationController.do?goUpdate" funname="goupdate"></t:dgToolBar>
          <t:dgToolBar title="管理员删除"  icon="icon-remove" url="riskIdentificationController.do?delRisk" funname="deleteALLSelect"></t:dgToolBar>
      </c:if>
  </t:datagrid>
  </div>
 </div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">


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

     function godetail(){
         var rows = $("#hazardFactorsList").datagrid('getSelections');
         if(rows== null || rows.length < 1){
             tip("请选择一条需要查看的数据!");
         }else{
             addOneTab("风险查看","riskIdentificationController.do?goAdd&identificationType="+rows[0]["identificationType"]+"&addressId="+rows[0]["address.id"]+"&id="+rows[0]["id"]+"&postId="+rows[0]["post.id"]+"&load=detail","default");
         }
     }

     function goReview(){
         //选中
         var rows = $("#hazardFactorsList").datagrid('getSelections');
         if(rows== null || rows.length != 1){
             tip("请选择一条要审批的数据!");
         }else{
             openwindow("审批", "riskIdentificationController.do?goApproval&id=" + rows[0].id, "hazardFactorsList", 900, 600);
         }
     }
     function goAllReview() {
         var rows = $("#hazardFactorsList").datagrid('getSelections');
         if (rows.length == 0) {
             tip("请选择要审批的数据");
         } else if (rows.length >= 1){
             var idsTemp = new Array();
             for (var i = 0; i < rows.length; i++) {
                 idsTemp.push(rows[i].id);
             }
             var idt = idsTemp.join(",");
             $.dialog.confirm("已勾选"+idsTemp.length+"条记录,是否确认批量审批？", function () {
                 openwindow("审批", "riskIdentificationController.do?goAllApproval&ids=" + idt, "hazardFactorsList", 900, 600);
             });
         }
     }
 $(document).ready(function(){
     var datagrid = $("#hazardFactorsListtb");
     datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");
     $("#tempSearchColums div[name='searchColumsCenter']").remove();

     datagrid.find("form[id='hazardFactorsListForm']>span:first").before($("#tempSearchColums div[name='addressSearchColums']").html());
     $("#tempSearchColums div[name='addressSearchColums']").remove();
     getAddressMagicSuggest($("#addressSelect"), $("[name='addressId']"));
     datagrid.find("form[id='hazardFactorsListForm']>span:first").before($("#tempSearchColums div[name='postSearchColums']").html());
     $("#tempSearchColums div[name='postSearchColums']").remove();
     getPostMagicSuggest($("#postSelect"), $("input[name='postId']"));

     $("a[iconcls='icon-reload']").hide();

     $("input[name='queryHandleStatusTem']").change(function() {
         var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
         $("input[name='queryHandleStatus']").val(selectedvalue);

         if(selectedvalue == "1"){
             //已提交，隐藏编辑、删除、录入、导入、导出、模板下载按钮
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
         }else{
             //草稿，显示编辑、删除、录入、导入、导出、模板下载按钮
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","none");
             $("span:contains('管理员编辑')").parents("a.l-btn").show();
         }
         hazardFactorsListsearch();
     });
 });

     window.top["reload_hazardFactorsListFrom"]=function(){
         $("#hazardFactorsList").datagrid( "reload");
     };

   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'hazardFactorsController.do?upload', "hazardFactorsList");
}

//导出
function ExportXls() {
	JeecgExcelExport("hazardFactorsController.do?exportXls","hazardFactorsList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("hazardFactorsController.do?exportXlsByT","hazardFactorsList");
}

     function goupdate(){
         var rows = $("#hazardFactorsList").datagrid('getSelections');
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