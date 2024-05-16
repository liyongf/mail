<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="tempSearchColums" style="display: none">
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
  <t:datagrid name="hazardFactorsList" checkbox="true" pagination="true" fitColumns="false" title="危害因素列表" actionUrl="hazardFactorsController.do?datagrid" idField="id" fit="true" queryMode="group" sortOrder="desc" sortName="createDate">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" queryMode="single"  width="120" query="true"></t:dgCol>
   <t:dgCol title="专业"  field="major" query="true" dictionary="major" queryMode="single"  width="100"></t:dgCol>
      <t:dgCol title="设备"  field="equipment"  queryMode="single"  width="100" ></t:dgCol>
      <t:dgCol title="作业活动"  field="activity"  queryMode="single"  width="100"></t:dgCol>
   <t:dgCol title="危害因素等级"  field="riskLevel" query="true" dictionary="factors_level" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="岗位"  field="postName" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="危害因素"  field="hazardFactors"  formatterjs="valueTitle" queryMode="group"  width="200"></t:dgCol>
   <t:dgCol title="管控措施"  field="manageMeasure" formatterjs="valueTitle" sortable="false"  queryMode="group"  width="400" ></t:dgCol>
      <t:dgCol title="管控标准来源"  field="docSource"  queryMode="single"  width="120" ></t:dgCol>
      <t:dgCol title="章节条款"  field="sectionName"  queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="信息来源"  field="from" query="true" dictionary="identifi_from" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="驳回备注"  field="rollBackRemark"  queryMode="group"  width="400" ></t:dgCol>
      <t:dgCol title="创建时间"  field="createDate"  hidden="true"  width="400" ></t:dgCol>
   <t:dgToolBar title="录入" icon="icon-add" url="hazardFactorsController.do?goAdd" width="600"  height="600" funname="add" operationCode="add" ></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="hazardFactorsController.do?goUpdate" width="600"  height="600" funname="update" operationCode="update"  ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="hazardFactorsController.do?doBatchDel" funname="deleteALLSelect" operationCode="batchdelete" ></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="hazardFactorsController.do?goDetail" funname="detail" width="600"  height="600"></t:dgToolBar>
   <t:dgToolBar title="提交审批" icon="icon-edit" url="hazardFactorsController.do?goReview" funname="goReview"></t:dgToolBar>
   <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>
   <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>
   <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
 <script type="text/javascript">
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
             $.dialog.confirm("已勾选"+ids.length+"条记录,是否确认提交审批？", function () {
                 $.ajax({
                     url : "hazardFactorsController.do?doReview",
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
                 });
             });

         }
     }
 $(document).ready(function(){
     var datagrid = $("#hazardFactorsListtb");
     datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");
     $("#tempSearchColums div[name='searchColumsCenter']").remove();
     $("a[iconcls='icon-reload']").hide();
     $("#hazardFactorsList").datagrid('hideColumn', "rollBackRemark");
     $("input[name='queryHandleStatusTem']").change(function() {
         var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
         $("input[name='queryHandleStatus']").val(selectedvalue);

         if(selectedvalue == "1"){
             $("#hazardFactorsList").datagrid('hideColumn', "rollBackRemark");
             //已提交，隐藏编辑、删除、录入、导入、导出、模板下载按钮
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","none");
           //  $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","none");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-add']").css("display","none");
         }else if(selectedvalue == "2"){
             $("#hazardFactorsList").datagrid('showColumn', "rollBackRemark");
             //草稿，显示编辑、删除、录入、导入、导出、模板下载按钮
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-add']").css("display","");
         }else{
             $("#hazardFactorsList").datagrid('hideColumn', "rollBackRemark");
             //草稿，显示编辑、删除、录入、导入、导出、模板下载按钮
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","");
             $("div[class='datagrid-toolbar']>span:first>a[icon='icon-add']").css("display","");
         }
         hazardFactorsListsearch();
     });
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'hazardFactorsController.do?upload', "hazardFactorsList");
}

//导出
function ExportXls() {
	//JeecgExcelExport("hazardFactorsController.do?exportXls","hazardFactorsList");

    var rows = $("#hazardFactorsList").datagrid('getSelections');
    if (rows.length == 0) {
        JeecgExcelExport("hazardFactorsController.do?exportXls", "hazardFactorsList");
    } else if (rows.length >=1) {
        var idsTemp = new Array();
        for (var i = 0; i < rows.length; i++) {
            idsTemp.push(rows[i].id);
        }
        var idt = idsTemp.join(",");
        $.dialog.confirm("是否确认导出" + idsTemp.length + "条记录？", function () {
            JeecgExcelExport("hazardFactorsController.do?exportXls&ids=" + idt, "hazardFactorsList");
        });
    }
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("hazardFactorsController.do?exportXlsByT","hazardFactorsList");
}

 </script>