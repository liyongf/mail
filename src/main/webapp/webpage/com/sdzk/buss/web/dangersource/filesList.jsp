<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 17-8-21
  Time: 下午1:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="org.jeecgframework.web.system.pojo.base.TSUser" %>
<%@ page import="org.jeecgframework.core.util.ResourceUtil" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<%
    TSUser user = ResourceUtil.getSessionUserName();
%>
<script type="text/javascript">
    function doDeleteDocument(id,rowIndex){
        var rows = $("#fList").datagrid('getRows');
        var userName = rows[rowIndex]["TSUser.userName"];
        if("<%=user.getUserName()%>" == "admin"){
            delObj('systemController.do?delDocument&id='+id+'','fList');
        }else if(userName == "<%=user.getUserName()%>"){
            delObj('systemController.do?delDocument&id='+id+'','fList');
        }else{
            tip("只能删除自己上传的文件");
        }
    }
</script>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 1px;">
     <t:datagrid name="fList" title="${typename}" actionUrl="systemController.do?documentList&typecode=${typecode}" idField="id" fit="true" queryMode="group">
        <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
        <t:dgCol title="标题" field="documentTitle" width="10" query="true" ></t:dgCol>
        <t:dgCol title="文件名" field="attachmenttitle" width="10"></t:dgCol>
        <t:dgCol title="上传人"  field="TSUser.realName" width="4"></t:dgCol>
        <t:dgCol title="上传人编号" hidden="false"  field="TSUser.userName" width="4"></t:dgCol>
        <t:dgCol title="上传时间"   field="createdate" width="4" formatter="yyyy-MM-dd"  query="true" queryMode="group"  ></t:dgCol>
        <t:dgCol title="类名" field="subclassname" hidden="true"></t:dgCol>
        <t:dgCol title="操作" field="opt" width="4"></t:dgCol>
        <t:dgDefOpt url="commonController.do?viewFile&fileid={id}&subclassname={subclassname}" urlclass="ace_button" urlfont="fa-trash-o" title="下载"></t:dgDefOpt>
        <t:dgOpenOpt width="800" height="700" url="commonController.do?openViewFile&fileid={id}&subclassname={subclassname}" urlfont="fa-trash-o" urlclass="ace_button" title="预览"></t:dgOpenOpt>
        <t:dgFunOpt funname="doDeleteDocument(id)" title="删除" urlclass="ace_button"  urlfont="fa-trash-o"></t:dgFunOpt>
        <t:dgToolBar title="文件录入" icon="icon-add" funname="add" url="systemController.do?addFiles&typecode=${typecode}" operationCode="add"></t:dgToolBar>
         <c:if test="${isSunAdmin == 'YGADMIN'}">
             <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
         </c:if>
    </t:datagrid></div>
</div>
<script>
    /**
     *  阳光账号隐藏数据功能
     * */
    function sunHidden() {
        var rows = $("#fList").datagrid('getSelections');
        if (rows.length < 1) {
            tip("请选择需要隐藏的数据");
        } else {
            var idsTemp = new Array();
            for (var i = 0; i < rows.length; i++) {
                idsTemp.push(rows[i].id);
            }
            var idt = idsTemp.join(",");

            $.ajax({
                type: 'POST',
                url: 'systemController.do?sunshine',
                dataType:"json",
                async:true,
                cache: false,
                data: {
                    ids:idt
                },
                success:function(data){
                    var msg = data.msg;
                    tip(msg);
                    reloadTable();
                },
                error:function(data){
                }
            });
        }
    }
</script>
