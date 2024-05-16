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
<div id="tempStatusReportOut" style="display: none">
    <div name="tempStatusReport" >
        <br>
       <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px;text-align: center;width: 100%">
        <input  name="reportStatusProvince" value="0" type="hidden">
           <label>
               <input name="reportStatus" type="radio" value="0" checked="checked">
               待上报
           </label>
           <label>
               <input name="reportStatus" type="radio" value="1" >
               已上报
           </label>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="riskList" checkbox="true" onDblClick="dbClick" pagination="true" fitColumns="false" title="${identificationTypeName}" actionUrl="reportController.do?reportRiskListDataGrid&identificationType=${identificationType}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险点Id"  field="address.id" hidden="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="岗位Id"  field="post.id" hidden="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="风险点"  field="address.address"  queryMode="single"  width="120" query="false"></t:dgCol>
            <c:if test="${newPost ne 'true'}">
                <t:dgCol title="岗位"  field="post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
            </c:if>
            <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="风险描述"  field="riskDesc"   queryMode="group"  width="200"></t:dgCol>
            <t:dgCol title="风险等级"  field="riskLevel" query="true" dictionary="factors_level" queryMode="single"  width="120" ></t:dgCol>
            <t:dgCol title="危害因素和管控措施" align="center"  field="hazardFactortsNum" url="riskIdentificationController.do?wxysList&id={id}&load=detail" queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="最高管控层级"  field="manageLevel" query="true" dictionary="identifi_mange_level"  queryMode="single"  width="120" ></t:dgCol>
            <t:dgCol title="最高管控责任人"  field="dutyManager" query="true" queryMode="single"  width="120" ></t:dgCol>
            <t:dgCol title="评估日期"  field="identifiDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="解除日期"  field="expDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="状态"  field="status" dictionary="identifi_status"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="信息来源"  field="identificationType" dictionary="identifi_from" queryMode="single" query="true"  width="120" ></t:dgCol>
            <t:dgToolBar title="查看" icon="icon-search" url="riskIdentificationController.do?goDetail" funname="godetail"></t:dgToolBar>
            <t:dgToolBar title="上报煤监" icon="icon-putout" funname="reportToProvince" ></t:dgToolBar>
            <t:dgToolBar title="从煤监撤回" icon="icon-put" funname="removeFromProvince" ></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    function dbClick(rowIndex,rowData){
        addOneTab("风险查看","riskIdentificationController.do?goAdd&identificationType="+rowData["identificationType"]+"&addressId="+rowData["address.id"]+"&postId="+rowData["post.id"]+"&load=detail","default");
    }
    function godetail(){
        var rows = $("#riskList").datagrid('getSelections');
        if(rows== null || rows.length != 1){
            tip("请选择一条需要查看的数据!");
        }else{
            addOneTab("风险查看","riskIdentificationController.do?goAdd&identificationType="+rows[0]["identificationType"]+"&addressId="+rows[0]["address.id"]+"&postId="+rows[0]["post.id"]+"&load=detail","default");
        }
    }


    $(document).ready(function(){
        var datagrid = $("#riskListtb");
        datagrid.find("form[id='riskListForm']>span:first").before($("#tempSearchColums div[name='postSearchColums']").html());
        datagrid.find("form[id='riskListForm']>span:first").before($("#tempSearchColums div[name='addressSearchColums']").html());
        datagrid.find("form[id='riskListForm']>span:last").before($("#tempStatusReportOut div[name='tempStatusReport']").html());
        $("#tempSearchColums").empty();
        $("#tempStatusReportOut").empty();
        getAddressMagicSuggest($("#addressSelect"), $("[name='addressId']"));
        getPostMagicSuggest($("#postSelect"), $("input[name='postId']"));
        $("input[name='reportStatus']").change(function() {
            var selectedvalue = $("input[name='reportStatus']:checked").val();
            $("input[name='reportStatusProvince']").val(selectedvalue);
            riskListsearch();
            if(selectedvalue == '0'){
                $("span:contains('上报煤监')").parents("a.l-btn").show();
                $("span:contains('从煤监撤回')").parents("a.l-btn").hide();
            }else {
                $("span:contains('上报煤监')").parents("a.l-btn").hide();
                $("span:contains('从煤监撤回')").parents("a.l-btn").show();
            }
        });
        $("span:contains('从煤监撤回')").parents("a.l-btn").hide();
        $("span[title='最高管控责任人']").css("width","100px");
    });

    function reportToProvince(){
        var rows = $("#riskList").datagrid('getSelections');
        var reportUrl = "reportController.do?reportRiskToProvince";
        if (rows.length == 0) {
            tip("请选择需要上报煤监的风险！");
        } else if (rows.length >=1) {
            var idsTemp = new Array();
            for (var i = 0; i < rows.length; i++) {
                idsTemp.push(rows[i].id);
            }
            var idt = idsTemp.join(",");
            $.dialog.confirm("是否确认上报煤监"+idsTemp.length+"条记录？", function () {
                $.ajax({
                    url:reportUrl,
                    dataType:"JSON",
                    type:"POST",
                    async:false,
                    data:{
                        ids:idt
                    },
                    success : function(data){
                        var message = data.msg;
                        reloadTable();
                        tip(message);
                    },
                    error : function(){
                        tip("请求失败，请求重新登陆！");
                    }
                })
            });
        }
    }

    function removeFromProvince(){
        var rows = $("#riskList").datagrid('getSelections');
        var reportUrl = "reportController.do?removeRiskFromProvince";
        if (rows.length == 0) {
            tip("请选择需要撤回煤监的风险！");
        } else if (rows.length >=1) {
            var idsTemp = new Array();
            for (var i = 0; i < rows.length; i++) {
                idsTemp.push(rows[i].id);
            }
            var idt = idsTemp.join(",");
            $.dialog.confirm("是否确认撤回"+idsTemp.length+"条记录？", function () {
                $.ajax({
                    url:reportUrl,
                    dataType:"JSON",
                    type:"POST",
                    async:false,
                    data:{
                        ids:idt
                    },
                    success : function(data){
                        var message = data.msg;
                        reloadTable();
                        tip(message);
                    },
                    error : function(){
                        tip("请求失败，请求重新登陆！");
                    }
                })
            });
        }
    }

</script>