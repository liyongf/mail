<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:1px;">
  <t:datagrid name="tBAccidentLevelList" checkbox="true" width="900px" height="450px" fitColumns="true" title="危害因素" actionUrl="riskIdentificationController.do?whysListDatagrid&riskId=${riskId}" idField="id" fit="true" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="风险类型"  field="hazardFactorsEntity.riskType" dictionary="risk_type"   queryMode="single"  width="80" align="center"></t:dgCol>
   <t:dgCol title="专业"  field="hazardFactorsEntity.major" dictionary="major"  queryMode="single"  width="80" align="center"></t:dgCol>
      <t:dgCol title="设备"  field="equipment"  queryMode="single"  width="80" ></t:dgCol>
      <t:dgCol title="作业活动"  field="activity"  queryMode="single"  width="80"></t:dgCol>
      <t:dgCol title="岗位"  field="hazardFactorsEntity.postName"  queryMode="single"  width="80" align="center"></t:dgCol>
   <t:dgCol title="危害因素"  field="hazardFactorsEntity.hazardFactors"  queryMode="single"  width="135" align="center" formatterjs="valueTitle"></t:dgCol>
   <t:dgCol title="管控措施"  field="hfManageMeasure" queryMode="single"  width="135" align="center" formatterjs="valueTitle"></t:dgCol>
   <t:dgCol title="危害因素等级"  field="hfLevel" dictionary="factors_level" queryMode="single"  width="80" align="center"></t:dgCol>
      <t:dgCol title="管控标准来源"  field="docSource"  queryMode="single"  width="80" ></t:dgCol>
      <t:dgCol title="章节条款"  field="sectionName"  queryMode="single"  width="80"></t:dgCol>
   <t:dgCol title="管控单位"  field="manageDepart.departname" width="80" align="center"></t:dgCol>
   <t:dgCol title="管控责任人"  field="manageUser.realName"   queryMode="single"  width="80" align="center"></t:dgCol>
      <c:if test="${load ne 'detail'}">
      <t:dgToolBar title="编辑管控信息" icon="icon-edit"  funname="updateAll"  ></t:dgToolBar>
      <t:dgToolBar title="取消关联"  icon="icon-remove" url="riskIdentificationController.do?doBatchDelRel" funname="deleteSelect"  ></t:dgToolBar>
      </c:if>

      <c:if test="${isAdmin eq 'true'}">
          <%--风险降级功能--%>
          <t:dgToolBar title="取消关联"  icon="icon-remove" url="riskIdentificationController.do?doDelRel" funname="deleteSelect"  ></t:dgToolBar>
          <t:dgToolBar title="编辑危害因素等级" icon="icon-edit"  funname="editLevel"  ></t:dgToolBar>
      </c:if>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
     window.top["reload_whys"]=function(){
         tBAccidentLevelListsearch();
         if(typeof(this.msg)!='undefined' && this.msg!=null&&this.msg!=""){
             tip(this.msg);
         }
     };

     function updateAll(title,url,gname) {
         var ids = [];
         var rows = $("#"+gname).datagrid('getSelections');
         if (rows.length == 0) {
             tip("请选择待编辑问题");
         } else if (rows.length > 1){
             for ( var i = 0; i < rows.length; i++) {
                 ids.push(rows[i].id);
             }
             var idt=ids.join(",");
             $.dialog.setting.zIndex = getzIndex(true);
             $.dialog.confirm("是否确认批量编辑？", function (r) {
                 if(r){
                     openwindow("编辑", "riskIdentificationController.do?goAllUpdateRelInfo&ids=" + idt, "tBAccidentLevelList", 600, 350);
                 }
             });
         }else{
             openwindow("编辑","riskIdentificationController.do?goUpdateRelInfo&id="+rows[0].id,"tBAccidentLevelList",600,350);
         }
     }

     function deleteSelect(title,url,gname) {
         gridname=gname;
         var ids = [];
         var rows = $("#"+gname).datagrid('getSelections');
         if (rows.length > 0) {
             $.dialog.setting.zIndex = getzIndex(true);
             $.dialog.confirm('你确定取消关联该数据吗?', function(r) {
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
                         success : function(data) {
                             try{
                                 window.top.reload_hazardFactorsListFrom.call();
                             } catch(e){
                                 console.log("window.top.reload_hazardFactorsListFrom调用失败");
                             }
                             var d = $.parseJSON(data);
                             if (d.success) {
                                 var msg = d.msg;
                                 tip(msg);
//                                 reloadTable();
//                                 $("#"+gname).datagrid('unselectAll');
//                                 ids='';

                                 tBAccidentLevelListsearch();
                                 try{
                                     window.top.reload_hazardFactorsList.call();
                                 } catch(e){
                                     console.log("window.top.reload_hazardFactorsList调用失败");
                                 }

                                 try{
                                     window.top.reload_riskList.call();
                                 } catch(e){
                                     console.log("window.top.reload_riskList调用失败");
                                 }

                             }
                         }
                     });
                 }
             });
         } else {
             tip("请选择需要取消关联的数据");
         }
     }

 $(document).ready(function(){
 });

     function editLevel(title,url,gname) {
         var rows = $("#"+gname).datagrid('getSelections');
         if (rows.length != 1) {
             tip("请选择一条需要编辑的数据");
         }else{
             openwindow("编辑","riskIdentificationController.do?goEditLevel&id="+rows[0].id,"tBAccidentLevelList",600,350);
         }
     }
 </script>