<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <br>
       <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px">
        <input  name="queryHandleStatus" value="00" type="hidden">
           <label>
               <input name="queryHandleStatusTem" type="radio" value="00" checked="checked">
               待审核
           </label>
           <label>
               <input name="queryHandleStatusTem" type="radio" value="ALL">
               已审核
           </label>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="tBHxdList" checkbox="true" pagination="true" autoLoadData="true" fitColumns="true" title="审核信息" actionUrl="tBHxdController.do?datagrid1" idField="id" fit="true">
            <t:dgCol title="序号"  field="id"  hidden="true" width="120" align="center" query="true"></t:dgCol>
            <t:dgCol title="提交部门"  field="dept" width="120" align="center"></t:dgCol>
            <t:dgCol title="提交人"  field="tjr" width="120" align="center"></t:dgCol>
            <t:dgCol title="提交日期"  field="tjrq" width="120" align="center"></t:dgCol>
            <t:dgCol title="详情" field="opt" width="100" align="center"></t:dgCol>
            <t:dgFunOpt funname="operationDetail(id)" title="修改详情" urlclass="ace_button" urlfont="fa-cog"  ></t:dgFunOpt>
            <t:dgCol title="处理日期"  field="clrq" width="120" align="center"></t:dgCol>
            <t:dgCol title="状态"  field="status" width="120" align="center"></t:dgCol>

        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        var datagrid = $("#tBHxdListtb");
        //datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");
        datagrid.find("div[name='searchColums']").html($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");
        $("#tBHxdListtb .datagrid-toolbar").hide();
//
//     var rows = [];
//     rows.push({'num':1,'dept':'安监处'});
//     var datasource = { total: rows.length, rows: rows };
//     $("#columnList").datagrid('loadData', datasource);
    });

    function operationDetail(id){
        addOneTab("修改详情","tBHxdController.do?goUpdate1","default");
    }


</script>
	