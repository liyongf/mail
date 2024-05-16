<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span title="事故等级" style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">事故等级：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">
                <select name="accidentlevel"  style="width: 94px">
                    <c:forEach items="${levelEntityList}" var="accidentLevel" >
                        <option value="${accidentLevel.id}" ${ tBAccidentPage.accidentlevel.id eq accidentLevel.id ?"selected='selected'":""}>${accidentLevel.accidentlevel}</option>
                    </c:forEach>
                </select>
            </span>
        </span>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span title="死亡人数" style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">死亡人数：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;font-size:0px;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">
                <input class="inuptxt" type="text" style="width: 90px" name="deathnum_begin">
                <span style="display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;">~</span>
                <input class="inuptxt" type="text" style="width: 90px" name="deathnum_end">
            </span>
        </span>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span title="重伤人数" style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">重伤人数：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;font-size:0px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">
                <input class="inuptxt" type="text" style="width: 90px" name="heavywoundnum_begin">
                <span style="display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;">~</span>
                <input class="inuptxt" type="text" style="width: 90px" name="heavywoundnum_end">
            </span>
        </span>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span title="轻伤人数" style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" >轻伤人数：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;font-size:0px;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">
                <input class="inuptxt" type="text" style="width: 90px" name="minorwoundnum_begin">
                <span style="display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;">~</span>
                <input class="inuptxt" type="text" style="width: 90px" name="minorwoundnum_end">
            </span>
        </span>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span title="直接损失" style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" >直接损失：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;font-size:0px;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">
                <input class="inuptxt" type="text" style="width: 90px" name="directdamage_begin">
                <span style="display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;">~</span>
                <input class="inuptxt" type="text" style="width: 90px" name="directdamage_end">
            </span>
        </span>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span title="间接损失" style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" >间接损失：</span>
                <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;font-size:0px;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; ">
                    <input class="inuptxt" type="text" style="width: 90px" name="consequentialloss_begin">
                    <span style="display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;">~</span>
                    <input class="inuptxt" type="text" style="width: 90px" name="consequentialloss_end">
                </span>
        </span>
    </div>
</div>

<div id="main_typegroup_list" class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid  name="tBAccidentList" onDblClick="dblClickDetail" checkbox="true" fitColumns="false" title="事故" actionUrl="tBAccidentController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="事故单位"  field="dept.departname"    queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="事故编号"  field="accidentcode" query="true"   queryMode="single"  width="90" align="center"></t:dgCol>
   <t:dgCol title="事故名称"  field="accidentname"   query="true"   queryMode="single"  width="90" align="center"></t:dgCol>
   <t:dgCol title="发生时间"  field="happentime" formatter="yyyy-MM-dd"  query="true"   queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="发生地点"  field="happenaddress"  query="true"   queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="事故类型"  field="accidenttype" dictionary="accidentCate" query="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="事故等级"  field="accidentlevel.accidentlevel"    queryMode="single" width="120" align="center"></t:dgCol>

   <t:dgCol title="事故经过"  field="accidentdetail"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="死亡人数"  field="deathnum"    queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="重伤人数"  field="heavywoundnum"    queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="轻伤人数"  field="minorwoundnum"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="直接损失"  field="directdamage"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="间接损失"  field="consequentialloss"    queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="起因物"  field="cause"    queryMode="group"  width="120" align="center"></t:dgCol>

   <t:dgCol title="直接原因"  field="immediatecause"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="间接原因"  field="remotecause"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="事故教训"  field="accidentlesson"    queryMode="group"  width="120"></t:dgCol>

   <t:dgCol title="防御措施"  field="securityclampdown"    queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy" hidden="true"   queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName" hidden="true"   queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy" hidden="true"   queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>

   <t:dgToolBar title="录入" operationCode="add" icon="icon-add" url="tBAccidentController.do?goAdd" width="850" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" operationCode="update" url="tBAccidentController.do?goUpdate" width="850" funname="update"></t:dgToolBar>
   <t:dgToolBar title="批量删除" operationCode="batchdelete" icon="icon-remove" url="tBAccidentController.do?doBatchDel" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" operationCode="detail" icon="icon-search" url="tBAccidentController.do?goUpdate" width="850" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="查看事故报告" icon="icon-search" funname="accidentReport" operationCode="detailReport" ></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
<div data-options="region:'east',
	title:'mytitle',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
     style="width: 400px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="userListpanel"></div>
</div>
 <script type="text/javascript">
     function dblClickDetail(rowIndex,rowData){
         var id=rowData.id;
         var url = "tBAccidentController.do?goUpdate&load=detail&id="+id;
         createdetailwindow("查看",url);
     }
     function fineDetail(id){
         var title = '事故报告: ';
         if(li_east == 0){
             $('#main_typegroup_list').layout('expand','east');
         }
         $('#main_typegroup_list').layout('panel','east').panel('setTitle', title);
         $('#userListpanel').panel("refresh", "tBAccidentController.do?goViewFile&busid="+id);

     }
     function accidentReport(){
         //获取选中条目
         var rowsData = $('#tBAccidentList').datagrid('getSelections');
         if(rowsData == null || rowsData.length != 1){
             tip("请选择一条需要查看的条目");
         }else{
             //addOneTab("查看","tBAccidentController.do?goViewFile&busid="+rowsData[0].id);
             openwindow("查看","tBAccidentController.do?goViewFile&busid="+rowsData[0].id,"tBAccidentList",900,400);
         }
     }
 $(document).ready(function(){
 		//给时间控件加上样式
 			$("#tBAccidentListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAccidentListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAccidentListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAccidentListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAccidentListtb").find("input[name='happentime_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
 			$("#tBAccidentListtb").find("input[name='happentime_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

     var datagrid = $("#tBAccidentListtb");
     //datagrid.find("div[name='searchColums']>span:last").before($("#tempSearchColums div[name='searchColums']").html());
     datagrid.find("div[name='searchColums']>form>span:last").after($("#tempSearchColums div[name='searchColums']").html());
 });
 function showTip(message){
     tip(message);
 }
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBAccidentController.do?upload', "tBAccidentList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBAccidentController.do?exportXls","tBAccidentList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBAccidentController.do?exportXlsByT","tBAccidentList");
}
 </script>