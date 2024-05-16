<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="totalAccessList" title="评分汇总" fitColumns="false" checkbox="true"
                    actionUrl="totalAssesController.do?datagrid" idField="id" fit="true" queryMode="group">
            <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="煤矿名称"  field="a4"  dictionary="mineCode" query="true"  queryMode="single"  width="350" align="center"></t:dgCol>
            <t:dgCol title="年度" field="year"  width="350" align="center"></t:dgCol>
            <t:dgCol title="季度" field="quarter"  dictionary="quarter" query="true"  queryMode="single" defaultVal="${quarter}"  width="350"  align="center" ></t:dgCol>
            <t:dgCol title="打分" field="playScore"  width="350"  align="center" ></t:dgCol>
<%--
            <t:dgCol title="操作" field="opt" width="600"></t:dgCol>
--%>


        </t:datagrid>
    </div>
</div>




<script type="text/javascript">


</script>
