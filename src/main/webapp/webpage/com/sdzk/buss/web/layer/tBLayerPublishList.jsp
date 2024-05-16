<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBLayerList" checkbox="false" pagination="true" fitColumns="true" title="" actionUrl="tBLayerController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="ID"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <%--<t:dgCol title="煤层"  field="layerCode" query="true" dictionary="layer"  width="120"></t:dgCol>--%>
   <t:dgCol title="矿图名称"  field="layerDetailName" query="true" width="120"></t:dgCol>
   <t:dgCol title="矿图文件"  field="dwgName"  queryMode="group"  width="120" formatterjs="downloadLink"></t:dgCol>
   <t:dgCol title="矿图文件路径"  field="dwgPath" hidden="true"  queryMode="group"  width="120"></t:dgCol>

   <t:dgCol title="超图路径"  field="url" hidden="true"  query="false"  width="200"></t:dgCol>
   <t:dgCol title="超图中心点"  field="center" hidden="true" query="false"  width="120"></t:dgCol>
   <t:dgCol title="是否显示"  field="isShow" query="true" dictionary="sf_yn" width="120"></t:dgCol>
   <t:dgCol title="发布类型"  field="publishType" dictionary="publishType" query="false" width="120"></t:dgCol>
   <t:dgCol title="备注"  field="remark" query="false" width="120"></t:dgCol>


   <t:dgCol title="创建人id"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建时间"  field="createDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人id"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改人"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="修改时间"  field="updateDate"  formatter="yyyy-MM-dd"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
   <%--<t:dgDelOpt title="删除" url="tBLayerController.do?doDel&id={id}" urlclass="ace_button"  urlfont="fa-trash-o"/>--%>
   <t:dgToolBar title="录入" icon="icon-add" url="tBLayerController.do?goAddPublish" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBLayerController.do?goUpdatePublish" funname="update"></t:dgToolBar>
   <t:dgToolBar title="删除"  icon="icon-remove" url="tBLayerController.do?doBatchDel" funname="delLayer"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBLayerController.do?goUpdate" funname="detail"></t:dgToolBar>
   <%--<t:dgToolBar title="导入" icon="icon-put" funname="ImportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"></t:dgToolBar>--%>
   <%--<t:dgToolBar title="模板下载" icon="icon-putout" funname="ExportXlsByT"></t:dgToolBar>--%>
  </t:datagrid>
  </div>
 </div>
 <script src = "webpage/com/sdzk/buss/web/layer/tBLayerList.js"></script>		
 <script type="text/javascript">
 $(document).ready(function(){
 });
 
   
 
//导入
function ImportXls() {
	openuploadwin('Excel导入', 'tBLayerController.do?upload', "tBLayerList");
}

//导出
function ExportXls() {
	JeecgExcelExport("tBLayerController.do?exportXls","tBLayerList");
}

//模板下载
function ExportXlsByT() {
	JeecgExcelExport("tBLayerController.do?exportXlsByT","tBLayerList");
}

function downloadLink(value,rec,index){
    var retStr = "";
    var filesLink = rec["dwgPath"];
    var files=value;
    if(undefined!=value && undefined!=filesLink && ""!=files && ""!=filesLink){
        var filesLinkArr = filesLink.split(',');
        var filesArr = files.split(',');
        for(var i=0;i<filesLinkArr.length;i++){
            if(i>0){
                retStr = retStr + "</br>";
            }
            retStr = retStr + "<a href=" + filesLinkArr[i] + ">" + filesArr[i] + "</a>";
        }
        return retStr;
    }
}

 function delLayer(title,url,gname) {
     gridname=gname;
     var ids = [];
     var selfIds = [];
     var rows = $("#"+gname).datagrid('getSelections');
     if (rows.length > 0) {
         $.dialog.setting.zIndex = getzIndex(true);
         $.dialog.confirm('你确定永久删除该数据吗?', function(r) {
             if (r) {
                 for ( var i = 0; i < rows.length; i++) {
                     ids.push(rows[i].id);
                     if(rows[i].publishType=='self'){
                         selfIds.push(rows[i].id);
                     }
                 }
                 if(selfIds.length > 0 && true != deleteFromSmServer(selfIds)){
                     return;
                 }
                 $.ajax({
                     url : url,
                     type : 'post',
                     data : {
                         ids : ids.join(',')
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
                     }
                 });
             }
         });
     } else {
         tip("请选择需要删除的数据");
     }
 }
 function deleteFromSmServer(ids){
     var flag = false;
     $.ajax({
         url : "http://${smHandleServer}:${smHandlePort}/supermap/smController/deleteService.do",
         type : 'post',
         data : {
             serverCert : '${serverCert}',
             smToken : '${smToken}',
             ids : ids.join(',')
         },
         cache : false,
         async : false,
         success : function(data) {
             var d = $.parseJSON(data);
             if (!d.success) {
                tip("矿图删除失败，原因："+d.msg);
                console.error("矿图删除失败，原因："+d.msg);
                flag = false;
             } else {
                 flag = true;
             }
         },
         error : function(XMLHttpRequest, textStatus, errorThrown){
             tip("矿图删除异常");
             console.error("矿图删除异常");
             flag = false;
         }
     });
     return flag;
 }
 </script>