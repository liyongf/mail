<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,ztree"></t:base>
<link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
<div class="easyui-layout" fit="true">
    <div region="west" style="padding: 1px;width:300px" title="部门列表">
        <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="functionListPanel">
            <a id="addRelBtn" onclick="mysubmit();">添加关联关系</a>
            <%--<a id="selecrAllBtn" onclick="selecrAll();"><t:mutiLang langKey="select.all" /></a>--%>
            <a id="resetBtn" onclick="reset();"><t:mutiLang langKey="common.reset" /></a>
            <ul id="functionid" class="ztree"></ul>
        </div>
    </div>
  <div region="center" style="padding:1px;">
      <div class="panel-header" style=""><div class="panel-title">${addressName}-关联部门列表</div></div>
      <div style="display:block;padding: 5px 0px;">
          <span style="display:-moz-inline-box;display:inline-block;">
                <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; " title="分管责任人">分管责任人：</span>
                <input id="manageMan" name="manageMan" style="width: 120px" class="inuptxt" type="text" value="${manageMan}">
          </span>
      </div>
      <div>
          <div style="height: 400px;display:block">
          <t:datagrid name="addressDepartRelList" checkbox="false" fitColumns="false"  title="" actionUrl="tBAddressInfoController.do?addressDepartRelDatagrid&addressId=${addressId}" idField="id" fit="true" onLoadSuccess="loadSuccess" queryMode="group" pagination="false">
              <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
              <t:dgCol title="责任单位"  field="departId"  dictionary="t_s_depart,id,departname" query="false"  queryMode="single"  width="120"></t:dgCol>
              <t:dgCol title="责任人"  field="dutyMan"  query="false" queryMode="single"  width="120" extendParams="editor:'text'"></t:dgCol>
              <t:dgCol title="操作" field="opt" width="120"></t:dgCol>
              <t:dgFunOpt funname="doBatchDelAddressDepartRell(id)" title="删除" urlclass="ace_button"  urlfont="fa-trash-o"></t:dgFunOpt>
              <%--&lt;%&ndash;<t:dgToolBar title="关联部门" icon="icon-add" url="tBDangerSourceController.do?goAddressDangerSourceList&excludeId=${addressId}" funname="chooseDepart"></t:dgToolBar>&ndash;%&gt;--%>
              <%--<t:dgToolBar title="编辑责任人"  icon="icon-edit" funname="editRow"></t:dgToolBar>--%>
              <%--<t:dgToolBar title="保存" icon="icon-save" url="tBAddressInfoController.do?saveDutyMan" funname="saveData"></t:dgToolBar>--%>
              <%--<t:dgToolBar title="取消编辑" icon="icon-undo" funname="reject"></t:dgToolBar>--%>
              <%--<t:dgToolBar title="删除关联"  icon="icon-remove" url="tBAddressInfoController.do?doBatchDelAddressDepartRel" funname="doBatchDelAddressDepartRell"></t:dgToolBar>--%>
          </t:datagrid>
          </div>
      </div>
      <div class="ui_buttons" style="text-align: left;">
          <input type="button" id="btn_save" value="保存" class="ui_state_highlight" onclick="saveData('addressDepartRelList')">
          <input type="button" id="btn_reset" value="重置" onclick="resetData()">
      </div>
  </div>
 </div>

 <script type="text/javascript">
     function resetData(){
         $("input[type='text']").each(function(){
             $(this).val($(this).attr("oldValue"));
             $(this).css("border","");
         });
     }
     //编辑责任人
     function loadSuccess(){
         var rows=$('#addressDepartRelList').datagrid("getRows")
         for(var i=0;i<rows.length;i++){
             var index= $('#addressDepartRelList').datagrid('getRowIndex', rows[i]);
             $('#addressDepartRelList').datagrid('beginEdit', index);
         }
         //分管领导及责任人修改时改变文本框颜色
         $("input[type='text']").css("border","");
         $("input[type='text']").on("change",function(){
             $(this).css("border","2px solid #000");
         });
         $("input[type='text']").each(function(){
             $(this).attr("oldValue",$(this).val());
         });
     }
     //结束编辑
     function endEdit(gname){
         var  editIndex = $('#'+gname).datagrid('getRows').length-1;
         for(var i=0;i<=editIndex;i++){
             if($('#'+gname).datagrid('validateRow', i))
                 $('#'+gname).datagrid('endEdit', i);
             else
                 return false;
         }
         return true;
     }
     //保存数据
     function saveData(gname){
         if(!endEdit(gname))
             return false;
         var rows=$('#'+gname).datagrid("getChanges","inserted");
         var uprows=$('#'+gname).datagrid("getChanges","updated");
         var manageMan=$("#manageMan").val();
         var oldManageMan=$("#manageMan").attr("oldValue");
         rows=rows.concat(uprows);
         if(oldManageMan==manageMan && rows.length==0){
             var rows=$('#addressDepartRelList').datagrid("getRows")
             for(var i=0;i<rows.length;i++){
                 var index= $('#addressDepartRelList').datagrid('getRowIndex', rows[i]);
                 $('#addressDepartRelList').datagrid('beginEdit', index);
             }
             tip("无修改项,暂不需要保存");
             return;
         }
         var result= new Array();
         for(var i=0;i<rows.length;i++){
             var entity={};
             for(var d in rows[i]){
                 entity[d]=rows[i][d];
             }
             result.push(entity);
         }
         $.ajax({
             url:"<%=basePath%>/tBAddressInfoController.do?saveDutyMan",
             type:"post",
             data:{saveData:JSON.stringify(result),manageMan:manageMan,addressId:"${addressId}"},
             dataType:"json",
             success:function(data){
                 tip(data.msg);
                 if(data.success){
                     $('#addressDepartRelList').datagrid('reload');
                     window.top.reload_tBAddressInfoList.call();
                 }
             }
         })
     }
    //生成部门树
     var setting = {
         check: {
             enable: true
         },
         data: {
             simpleData: {
                 enable: true
             }
         },
         async: {
             enable: true,
             url:"tBAddressInfoController.do?setDepartRelList&&addressId=${addressId}",
             dataFilter: filter
         },
         callback: {
             beforeAsync: function(){},
             onAsyncSuccess: function(event, treeId, treeNode, msg){
                 var zTree = $.fn.zTree.getZTreeObj("functionid");
                 var nodes = zTree.getCheckedNodes();
                 for(var i = 0; i < nodes.length; i++){
                     var node = nodes[i];
                     if(node.checkedOld && !node.isParent){
                         node.chkDisabled = true; //表示显示checkbox
                         zTree.updateNode(node);
                     }
                 }
                 expandAll();
             },
             onAsyncError: function(){},
             onClick: function (event, treeId, treeNode){
                 var id = treeNode.tId;
                 $("#"+id+"_check").trigger("click");
             },
             onCheck: function(event, treeId, treeNode){
             }
         }
     };
     function filter(treeId, parentNode, childNodes) {
         if (!childNodes) return null;
         for (var i=0, l=childNodes.length; i<l; i++) {
             childNodes[i].name = childNodes[i].text;
             if (childNodes[i].children != null) {
                 childNodes[i].nodes = childNodes[i].children;
                 filter(null, childNodes[i], childNodes[i].nodes);//递归设置子节点
             }
         }
         return childNodes;
     }
     function expandAll() {
         var zTree = $.fn.zTree.getZTreeObj("functionid");
         zTree.expandAll(true);
     }
     function selecrAll() {
         var zTree = $.fn.zTree.getZTreeObj("functionid");
         zTree.checkAllNodes(true);
     }
     function reset() {
         $.fn.zTree.init($("#functionid"), setting);
     }
     function mysubmit() {
         var departIds = GetNode();
         if(departIds==null||departIds==''){
             tip("请选择需要关联的部门");
             return;
         }
         doSubmit("tBAddressInfoController.do?saveAddressDepartRel&addressId=${addressId}&departIds="+departIds, "addressDepartRelList");
         $.fn.zTree.init($("#functionid"), setting);
     }
     function GetNode() {
         var zTree = $.fn.zTree.getZTreeObj("functionid");
         var node = zTree.getCheckedNodes(true);
         //加入实际被选中的节点
         var cnodes = '';
         for ( var i = 0; i < node.length; i++) {
             if(!node[i].isParent){
                 cnodes += node[i].id + ',';
             }
         }
         cnodes = cnodes.substring(0, cnodes.length - 1);
         return cnodes;
     }
 $(document).ready(function(){
     $.fn.zTree.init($("#functionid"), setting);
     $('#addRelBtn').linkbutton({});
     $('#resetBtn').linkbutton({});
 });

 function doBatchDelAddressDepartRell(id) {
     $.dialog.confirm('确定删除该关联关系吗?', function(r) {
         if (r) {
             $.ajax({
                 url : "tBAddressInfoController.do?doBatchDelAddressDepartRel",
                 type : 'post',
                 data : {
                     ids : id
                 },
                 cache : false,
                 success : function(data) {
                     var d = $.parseJSON(data);
                     if (d.success) {
                         var msg = d.msg;
                         tip(msg);
                         reloadTable();
                     }
                 }
             });
         }
     });
 }
 </script>