<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div id="tempSearchColums" style="display: none">
<div name="searchColums" >
    <br>
    <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px;text-align: center;width: 100%">
        <input  name="wexinStatus" value="0" type="hidden">
           <label>
               <input name="wexinStatusTem" type="radio" value="all" >全部
           </label>
           <label>
				<input name="wexinStatusTem" type="radio" value="1">已关联
           </label>
           <label>
				<input name="wexinStatusTem" type="radio" value="0" checked>未关联
           </label>
        </span>
</div>
</div>
<div class="easyui-layout" fit="true">

    <div region="center" style="padding:0px;border:0px">
    <t:datagrid name="tBWexinInfoList" checkbox="true" pagination="true" fitColumns="false" title="微信列表" actionUrl="WeixinChat.do?datagrid" idField="id" fit="true" queryMode="group">
        <t:dgCol title="主键"  field="id"  hidden="true" sortable="false"  align="center" width="120"></t:dgCol>
        <t:dgCol title="昵称"  field="nickname"  align="center" sortable="false" query="true"  width="150"></t:dgCol>
        <t:dgCol title="OPENID"  field="openId"  align="center" sortable="false"  width="240"></t:dgCol>
        <t:dgCol title="真实姓名"  field="userTemp"    align="center" sortable="false" width="120"></t:dgCol>
        <t:dgToolBar title="关联用户" icon="icon-add" url="WeixinChat.do?goAddUser" funname="chooseUser" operationCode="update"></t:dgToolBar>


    </t:datagrid>
</div>
</div>
<script type="text/javascript">

    $(function() {
        var datagrid = $("#tBWexinInfoListtb");
        datagrid.find("form[id='tBWexinInfoListForm']").append($("#tempSearchColums div[name='searchColums']").html());
        $("#tempSearchColums").empty();

        $("input[name='wexinStatusTem']").change(function () {
            var selectedvalue = $("input[name='wexinStatusTem']:checked").val();
            $("input[name='wexinStatus']").val(selectedvalue);
            tBWexinInfoListsearch();
            if (selectedvalue == '0') {
                $("div[class='datagrid-toolbar']>span:first").find("a").each(function(){
                    $("span:contains('关联用户')").parents("a.l-btn").show();
                })

            } else if (selectedvalue == '1') {
                $("div[class='datagrid-toolbar']>span:first").find("a").each(function(){
                    $("span:contains('关联用户')").parents("a.l-btn").hide();
                })

            } else {
                $("div[class='datagrid-toolbar']>span:first").find("a").each(function(){
                    $("span:contains('关联用户')").parents("a.l-btn").hide();
                })

            }

        });
    });
 function chooseUser() {
     var rowsData = $('#tBWexinInfoList').datagrid('getSelections');
     if(rowsData == null || rowsData.length != 1) {
         tip("请选择一条需要关联的数据");
     }else{
         openwindow('用户选择','WeixinChat.do?goAddUser&id='+rowsData[0].id,"",1000,500);
     }

 }
</script>