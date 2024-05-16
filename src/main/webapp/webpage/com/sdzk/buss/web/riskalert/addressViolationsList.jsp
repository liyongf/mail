<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,autocomplete"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:1px;">
        <t:datagrid name="tBThreeViolationsList" checkbox="false" pagination="true" fitColumns="false" title="" actionUrl="tBThreeViolationsController.do?datagrid&tBAddressInfoEntityId=${addressId}" idField="id" fit="true" queryMode="group" >
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="违章时间"  field="vioDate" formatter="yyyy-MM-dd" query="false"  queryMode="group"  width="80" align="center"></t:dgCol>
            <t:dgCol title="违章时间"  field="queryMonth" formatter="yyyy-MM" hidden="true" query="false"  queryMode="single"  width="80" align="center"></t:dgCol>
            <t:dgCol title="班次"  field="shift" hidden="false" dictionary="workShift" query="false"  queryMode="single"  width="60" align="center"></t:dgCol>
            <t:dgCol title="查处单位"  field="findUnits"  dictionary="t_s_depart,id,departname,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="违章地点"  field="vioAddress"  dictionary="t_b_address_info,id,address,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="违章单位"  field="vioUnits"  dictionary="t_s_depart,id,departname,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="违章人员"  field="vioPeople"  queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="违章分类"  field="vioCategory" dictionary="violaterule_wzfl" query="false"  queryMode="single"  width="100" align="center"></t:dgCol>
            <t:dgCol title="三违级别"  field="vioLevel" dictionary="vio_level" query="false"  queryMode="single"  width="100" align="center"></t:dgCol>
            <t:dgCol title="制止人"  field="stopPeople"   queryMode="group"  width="120" align="center"></t:dgCol>

            <t:dgCol title="三违事实描述"  field="vioFactDesc"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="备注"  field="remark"    queryMode="group"  width="120" align="center"></t:dgCol>
            <%--<t:dgToolBar title="查看" icon="icon-search" url="tBThreeViolationsController.do?goUpdate" funname="detail" operationCode="detail" ></t:dgToolBar>--%>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
</script>