<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
<head><title>查看事故报告</title></head>
<t:base type="jquery,easyui,tools,autocomplete"></t:base>
<body>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tBFineInfoList" checkbox="true" fitColumns="false" title="事故报告" actionUrl="tBAccidentController.do?fileDatagrid&busid=${busid}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="文件名"  field="attachmenttitle"    queryMode="group"  width="500"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="200"></t:dgCol>
            <t:dgDefOpt title="下载" url="commonController.do?viewFile&fileid={id}" urlclass="ace_button"  urlfont="fa-download" ></t:dgDefOpt>
            <t:dgOpenOpt title="预览" url="commonController.do?openViewFile&fileid={id}" urlclass="ace_button"  urlfont="fa fa-file-o" width="1000" height="500" />

        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        //给时间控件加上样式
    });

</script>
</body>
</html>