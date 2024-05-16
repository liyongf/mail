<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="tempSearchColums" style="display: none">
    <div name="searchColumsCenter" >
          <span style="display:-moz-inline-box;display:inline-block;">
          <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap;  ">
            <input  name="queryHandleStatus" value="false" type="hidden">
            <label>
            <input name="queryHandleStatusTem" type="radio" value="false" checked>
               草稿</label>
            <label style="margin-left: 15px;">
            <input name="queryHandleStatusTem"  type="radio" value="true">
                已提交</label>
        </span>
         </span>
    </div>
</div>
<%--<div class="wz-bt" >您当前的位置：考核评分管理->露天煤矿->职业卫生</div>--%>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="sfMineStandardAssessList" checkbox="true" fitColumns="true" title="职业卫生" actionUrl="sfMineStandardAssessController.do?datagrid&mineType=${mineType}&assessType=${assessType}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="唯一标识"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="考核月份"  field="ssaMonth" query="true" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="得分"  field="ssaSumScore"    queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="矿井类型"  field="ssaMineType"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="考核类型"  field="ssaAssessType"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="当前状态"  field="ssaCurrentStatus"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人登录名"  field="createBy"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"    queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd"   queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人登陆名"  field="updateBy"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新时间"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>

   <t:dgToolBar title="录入" operationCode="add" icon="icon-add" onclick="addbytab()" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" operationCode="update" icon="icon-edit" onclick="editbytab()"  funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除"  operationCode="batchdelete" icon="icon-remove" url="sfMineStandardAssessController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" operationCode="detail" icon="icon-search" width="1000" height="800" url="sfMineStandardAssessController.do?goUpdate&mineType=${mineType}&assessType=${assessType}" funname="detail"></t:dgToolBar>
  
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/standardassess/js/sfMineStandardAssessList.js"></script>		
 <script type="text/javascript">
 
 function addbytab() {
		document.location="sfMineStandardAssessController.do?goAdd&mineType=${mineType}&assessType=${assessType}";
	}
 
 function editbytab() {
	 var rows = $("#sfMineStandardAssessList").datagrid("getSelections");
	 	if(!rows || rows.length == 0){
			tip('请选择一行记录');
			return;
		}
		if (rows.length > 1) {
			tip('请选择一条记录');
			return;
		}
		var id=rows[0].id;
		document.location="sfMineStandardAssessController.do?goUpdate&mineType=${mineType }&assessType=${assessType}&id="+id;
	}
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#sfMineStandardAssessListtb").find("input[name='ssaMonth']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
 			$("#sfMineStandardAssessListtb").find("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#sfMineStandardAssessListtb").find("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#sfMineStandardAssessListtb").find("input[name='updateDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#sfMineStandardAssessListtb").find("input[name='updateDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

 			 var datagrid = $("#sfMineStandardAssessListtb");
 		     datagrid.find("div[name='searchColums']>form>span:last").after($("#tempSearchColums div[name='searchColumsCenter']").html());
 		    $("#tempSearchColums div[name='searchColumsCenter']").remove();
 		    $("a[iconcls='icon-reload']").hide();

 		     $("input[name='queryHandleStatusTem']").change(function() {
 		         var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
 		         $("input[name='queryHandleStatus']").val(selectedvalue);
 		      
 		         if(selectedvalue == "true"){
 		             //已提交，隐藏编辑、删除、录入、导入、导出、模板下载按钮
 		         //    $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","none");
 		         //    $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","none");
 		            $("div[class='datagrid-toolbar']>span:first>a[icon='icon-add']").css("display","none");
 		         }else{
 		             //草稿，显示编辑、删除、录入、导入、导出、模板下载按钮
 		          //   $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
 		          //   $("div[class='datagrid-toolbar']>span:first>a[icon='icon-remove']").css("display","");
 		            $("div[class='datagrid-toolbar']>span:first>a[icon='icon-add']").css("display","");
 		         }
 		        sfMineStandardAssessListsearch();
 		     });
 });
 
 </script>