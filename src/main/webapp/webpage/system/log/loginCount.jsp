<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 17-9-23
  Time: 下午2:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>

<div id="logListtb" style="padding: 10px; height: 50px">
    <div name="departSearchColums" style="float:left;">
            <span style="margin-top:5px;display:inline-block;font-size:0;">
                <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="单位">单位：</span>
                 <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                    <div id="departnameSuggest" style="width: 130px;height: 18px"></div>
                     <input id="departname" type="hidden" name="departname">
                 </span>
            </span>
    </div>
    <div name="searchColums" style="float:left;margin-top: 5px;">
        用户名:
        <input type="text" name="username" id="username" style="width: 160px; height: 32px;"  >
        <span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;" title="操作时间 ">
            <t:mutiLang langKey="operate.time"/>: </span>
            <input type="text" name="operatetime_begin" id="operatetime_begin" style="width: 160px; height: 32px;" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'operatetime_end\')}',dateFmt:'yyyy-MM-dd'})">~
            <input type="text" name="operatetime_end" id="operatetime_end" style="width: 160px; height: 32px; margin-right: 20px;" class="Wdate"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'operatetime_begin\')}',dateFmt:'yyyy-MM-dd'})" />
        </span>

        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="logListsearch();"><t:mutiLang langKey="common.query"/></a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="clearSearch();"><t:mutiLang langKey="common.clear"/></a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-putout" onclick="ExportXls();"><t:mutiLang langKey="excelOutput"/></a>
    </div>
</div>

<t:datagrid title="登录查询" name="logList" actionUrl="logController.do?loginCountDatagrid" pageSize="500" queryMode="group" sortName="daytime,logintime" sortOrder="desc,desc">
    <t:dgCol title="用户账号" field="username" width="200" query="true" queryMode="single"></t:dgCol>
    <t:dgCol title="用户姓名" field="realname" width="200"></t:dgCol>
    <t:dgCol title="登录天数" field="daytime" width="200" sortable="true"></t:dgCol>
    <c:if test="${longyun eq 'true'}">
        <t:dgCol title="pc登录次数" field="pcLogintime" width="200" sortable="true"></t:dgCol>
        <t:dgCol title="app登录次数" field="appLogintime" width="200" sortable="true"></t:dgCol>
    </c:if>
    <c:if test="${longyun ne 'true'}">
        <t:dgCol title="登录次数" field="logintime" width="200" sortable="true"></t:dgCol>
    </c:if>
</t:datagrid>

<script type="text/javascript">
    $(function() {
        var datagrid = $("#logListtb");
        datagrid.find("form[id='logListForm']>span:first").before($("#logListtb div[name='departSearchColums']").html());
        datagrid.find("form[id='logListForm']>span:last").before($("#tlogListtb div[name='searchColums']").html());
        getDepartMagicSuggest($("#departnameSuggest"), $("#departname"));
    });

    function logListsearch(){
        var username = $("#username").val();
        var operatetime_begin = $("#operatetime_begin").val();
        var operatetime_end = $("#operatetime_end").val();
        var departname = $("#departname").val();
            $("#logList").datagrid('load',{
                username:username,
                operatetime_begin : operatetime_begin,
                operatetime_end : operatetime_end,
                departname:departname
            });
    }

    function clearSearch(){
        $("#username").val("");
        $("#operatetime_begin").val("");
        $("#operatetime_end").val("");
        $("#logList").datagrid('load',{});
    }
    //导出
    function ExportXls() {
        JeecgExcelExport("logController.do?exportXlsFineSummary","logList");
    }
</script>

