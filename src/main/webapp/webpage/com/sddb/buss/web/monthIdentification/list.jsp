<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.jeecgframework.core.util.DicUtil" %>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<%
    String examType = (String)request.getAttribute("examType");
    String examTypeName = DicUtil.getTypeNameByCode("examType",examType);
%>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="addressSearchColums" >
        <span style="margin-top:2px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="风险点">风险点：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="addressSelect" style="width: 130px;height: 15px"></div>
                 <input id="addressId" type="hidden" name="addressId">
             </span>
        </span>
    </div>
</div>

<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="monthRiskList" onDblClick="dblClickDetail" checkbox="true" fitColumns="false" title="月度风险管控清单" actionUrl="monthRiskIdentificationController.do?datagrid&type=${type}" idField="id" fit="true" queryMode="group" >
      <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
      <t:dgCol title="月份"  field="month"   width="150" align="center"></t:dgCol>
      <t:dgCol title="单位专业"  field="unitSpecialty"   width="150" align="center"></t:dgCol>
      <t:dgCol title="风险地点"  field="addressNameTemp" sortable="false" width="150" align="center"></t:dgCol>
      <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" query="true" align="center" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="风险等级"  field="riskLevel" query="true" dictionary="factors_level" align="center" queryMode="single" defaultVal="${riskLevel}" width="120" ></t:dgCol>
      <t:dgCol title="风险描述"  field="riskDesc"  formatterjs="valueTitle" queryMode="group"  width="200"></t:dgCol>
      <t:dgCol title="危害因素"  field="hazardFactors"  formatterjs="valueTitle" queryMode="group"  width="200"></t:dgCol>
      <t:dgCol title="管控措施"  field="controlMeasures"  formatterjs="valueTitle" width="250" align="center"></t:dgCol>
      <t:dgCol title="落实资金（万元）"  field="fund"   width="100" align="center"></t:dgCol>
      <t:dgCol title="预计解决时间"  field="solveTime"   width="150" align="center"></t:dgCol>
      <t:dgCol title="预案名称"  field="planName"  formatterjs="valueTitle"  width="150" align="center"></t:dgCol>
      <t:dgCol title="管控单位及责任人"  field="dutyManager"   width="150" align="center"></t:dgCol>
      <t:dgCol title="最高管控层级及责任人"  field="manageLevel"   width="150" align="center"></t:dgCol>
      <t:dgCol title="技术指导部门及负责人"  field="technical"   width="150" align="center"></t:dgCol>
      <t:dgCol title="监管部门及责任人"  field="supervision"   width="150" align="center"></t:dgCol>
      <t:dgCol title="信息来源"  field="identificationType" dictionary="month_risk_source" query="true" align="center" queryMode="single"  width="120"></t:dgCol>
      <t:dgCol title="备注"  field="remark"   width="150" align="center"></t:dgCol>
      <t:dgCol title="月份" field="yeRecognizeTimeTemp" query="true" formatter="yyyy-MM" queryMode="single" hidden="true" width="90" align="center"></t:dgCol>

      <t:dgToolBar title="查看" icon="icon-search" url="monthRiskIdentificationController.do?goUpdate" funname="goDetail" width="950" height="600" ></t:dgToolBar>
      <t:dgToolBar title="批量删除"  icon="icon-remove" url="monthRiskIdentificationController.do?doBatchDel" funname="deleteALLSelect" ></t:dgToolBar>
      <t:dgToolBar title="导入" icon="icon-put" funname="ImportXls" operationCode="import"></t:dgToolBar>
      <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export"></t:dgToolBar>
      <t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT" operationCode="downloadTmpl"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
     function valueTitle(value){
         return "<a title=\""+value+"\">"+value+"</a>";
     }
     //导出
     function ExportXls() {
         JeecgExcelExport("monthRiskIdentificationController.do?exportXls&type=${type}","monthRiskList");
     }

     //导入
     function ImportXls() {
         openuploadwin('月度风险管控清单', 'monthRiskIdentificationController.do?upload&type=${type}', "monthRiskList");
     }
     //模板下载
     function ExportXlsByT() {
         JeecgExcelExport("monthRiskIdentificationController.do?exportXlsByT&type=${type}","monthRiskList");
     }
     function dblClickDetail(rowIndex,rowsData){
             createdetailwindow("月度风险管控清单","monthRiskIdentificationController.do?goDetail&id="+rowsData.id,950,600);
     }
     function goDetail(){
         //获取选中条目
         var rowsData = $('#monthRiskList').datagrid('getSelections');
         if(rowsData == null || rowsData.length != 1){
             tip("请选择需要查看的条目");
         }else{
             createdetailwindow("月度风险管控清单","monthRiskIdentificationController.do?goDetail&id="+rowsData[0].id,950,600);
         }

     }
     function deleteALLSelect(title,url,gname) {
         gridname=gname;
         var ids = [];
         var rows = $("#monthRiskList").datagrid('getSelections');
         if (rows.length > 0) {
             $.dialog.setting.zIndex = getzIndex(true);
             $.dialog.confirm('你确定永久删除'+rows.length+'条记录吗吗?', function(r) {
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
 $(document).ready(function(){

     var datagrid = $("#monthRiskListtb");
     datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");
     $("#tempSearchColums div[name='searchColums']").remove();
     datagrid.find("form[id='monthRiskListForm']>span:first").before($("#tempSearchColums div[name='addressSearchColums']").html());
     $("#tempSearchColums div[name='addressSearchColums']").remove();

     getAddressMagicSuggest($("#addressSelect"), $("[name='addressId']"));
     $("#monthRiskListtb").find("input[name='yeRecognizeTimeTemp']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});

     $(".icon-reload").bind("click", function(){
         window.location.reload();
     });
 });

         window.top["reload_monthRiskListtb"]=function(){
         $("#monthRiskList").datagrid( "load");
         if(typeof(this.msg)!='undeauthd' && this.msg!=null&&this.msg!=""){
             tip(this.msg);
         }
     };
 </script>
