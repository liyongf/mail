<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2017/11/9
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<html style="width: 98%;">
<head>
    <title>重大风险</title>
    <script type="text/javascript" src="plug-in/hashMap/HashMap.js"></script>
</head>
<body style="background-color: #FFFFFF;">
<%--<div style="font-family: 'sans-serif'; font-size: 18px; font-weight: bold; text-align: center; margin-bottom: 8px; ">重大风险</div>--%>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="majorRiskList" title="重大风险列表" actionUrl="homePageController.do?majorRiskDatagrid&beginDate=${beginDate}&endDate=${endDate}" fit="true" sortName="relHiddenDanger" sortOrder="desc" fitColumns="true">
            <t:dgCol title="id" field="id" hidden="true" width="120" ></t:dgCol>
            <t:dgCol title="关联隐患ID" field="relHiddenDangerIds"  width="120" hidden="true" ></t:dgCol>
            <c:if test="${yunhe eq 'true'}">
                <t:dgCol title="风险点" field="address"  width="200" formatterjs="formatTZ"  ></t:dgCol>
                <t:dgCol title="风险类型" field="riskType" dictionary="risk_type"  width="200"   ></t:dgCol>
            </c:if>
            <c:if test="${yunhe ne 'true'}">
                <t:dgCol title="风险名称" field="riskName"  width="200" formatterjs="formatTZ"  ></t:dgCol>
            </c:if>
            <%--<t:dgCol title="风险等级" field="riskLevel" align="center" dictionary="riskLevel" width="100" ></t:dgCol>
            <t:dgCol title="关联隐患" field="relHiddenDanger" align="center" formatterjs="format"  width="80" ></t:dgCol>--%>
            <%--<t:dgCol title="操作" field="opt" width="100"></t:dgCol>--%>
            <%--<t:dgCol title="关联隐患" field="opt"  width="120" ></t:dgCol>--%>
            <%--<t:dgFunOpt title="查看" funname="dblClickDetail(id, rowIndex, rowData)"></t:dgFunOpt>--%>
        </t:datagrid>
    </div>
</div>

</body>
<script>
    function dblClickDetail(id){
        createdetailwindow("查看","homePageController.do?majorRiskRelHiddenDanger&ids="+id,950,600);
    }

    function format(value,row,index){
        var id=row.id;
        return "<a href='#' onclick='dblClickDetail(\""+id+"\")'>"+value+"</a>";
    }

    function dblClickDetailTZ(id){
        var url = "riskIdentificationController.do?querylist&riskLevel=1";
        addOneTab("风险列表",url,"default");
    }

    function formatTZ(value,row,index){
        var id=row.id;
        return "<a title=\""+value+"\" href='#' onclick='dblClickDetailTZ(\""+id+"\")'>"+value+"</a>";
    }
</script>
</html>
