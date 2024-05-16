<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="addressSearchColums" >
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="风险点">风险点：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="addressSelect" style="width: 130px;height: 15px"></div>
                 <input id="addressId" type="hidden" name="addressId">
             </span>
        </span>
    </div>
    <c:if test="${newPost ne 'true'}">
        <div name="postSearchColums" >
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="岗位">岗位：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="postSelect" style="width: 130px;height: 15px"></div>
                 <input id="postId" type="hidden" name="postId">
             </span>
        </span>
        </div>
    </c:if>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="riskList" checkbox="true" onDblClick="dbClick" pagination="true" fitColumns="false" title="风险列表" actionUrl="tBDecisionAnalyseController.do?riskDatagrid&statisticType=${statisticType}&specialid=${specialid}&year=${year}&yearMonth=${yearMonth}&bMajor=${bMajor}&bReport=${bReport}&unitId=${unitId}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险点Id"  field="address.id" hidden="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="岗位Id"  field="post.id" hidden="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="风险点"  field="address.address"  queryMode="single"  width="120" query="false"></t:dgCol>
            <c:if test="${newPost ne 'true'}">
                <t:dgCol title="岗位"  field="post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
            </c:if>
            <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="风险描述"  field="riskDesc"  formatterjs="valueTitle" queryMode="group"  width="200"></t:dgCol>
            <t:dgCol title="风险等级"  field="riskLevel" query="true" dictionary="factors_level" queryMode="single"  width="120" ></t:dgCol>
            <t:dgCol title="危害因素和管控措施" align="center"  field="hazardFactortsNum" url="riskIdentificationController.do?wxysList&id={id}&load=detail" queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="最高管控层级"  field="manageLevel" query="true" dictionary="identifi_mange_level"  queryMode="single"  width="120" ></t:dgCol>
            <t:dgCol title="最高管控责任人"  field="dutyManager" query="true" queryMode="single"  width="120" ></t:dgCol>
            <t:dgCol title="评估日期"  field="identifiDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="解除日期"  field="expDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="状态"  field="status" dictionary="identifi_status"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="信息来源"  field="identificationType" dictionary="identifi_from" queryMode="single" query="true"  width="120" ></t:dgCol>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        var datagrid = $("#riskListtb");
        datagrid.find("form[id='riskListForm']>span:first").before($("#tempSearchColums div[name='postSearchColums']").html());
        datagrid.find("form[id='riskListForm']>span:first").before($("#tempSearchColums div[name='addressSearchColums']").html());
        $("#tempSearchColums").empty();
        getAddressMagicSuggest($("#addressSelect"), $("[name='addressId']"));
        getPostMagicSuggest($("#postSelect"), $("input[name='postId']"));
        $("span[title='最高管控责任人']").css("width","100px");
    });
</script>