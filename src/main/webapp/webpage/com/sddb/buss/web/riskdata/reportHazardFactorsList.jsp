<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <br>
        <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px">
        <input  name="reportStatusProvince" value="0" type="hidden">
           <label>
               <input name="reportStatus" type="radio" value="0" checked="checked">未上报
           </label>
           <label>
               <input name="reportStatus" type="radio" value="1">已上报
           </label>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="hazardFactorsList" checkbox="true" pagination="true" fitColumns="false" title="危害因素列表" actionUrl="reportController.do?reportHazardFactorsDataGrid" idField="id" fit="true" queryMode="group" sortOrder="desc" sortName="createDate">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" queryMode="single"  width="120" query="true"></t:dgCol>
            <t:dgCol title="专业"  field="major" query="true" dictionary="major" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="危害因素等级"  field="riskLevel" query="true" dictionary="factors_level" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="岗位"  field="postName" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="危害因素"  field="hazardFactors"   queryMode="group"  width="200"></t:dgCol>
            <t:dgCol title="管控措施"  field="manageMeasure" sortable="false"  queryMode="group"  width="400" ></t:dgCol>
            <t:dgCol title="驳回备注"  field="rollBackRemark"  queryMode="group"  width="400" ></t:dgCol>
            <t:dgCol title="创建时间"  field="createDate"  hidden="true"  width="400" ></t:dgCol>
            <t:dgToolBar title="查看" icon="icon-search" url="hazardFactorsController.do?goDetail" funname="detail"></t:dgToolBar>
            <t:dgToolBar title="上报煤监" icon="icon-putout" funname="reportToProvince" operationCode="reportToProvince"></t:dgToolBar>
            <t:dgToolBar title="从煤监撤回" icon="icon-put" funname="removeFromProvince" operationCode="removeFromProvince"></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        var datagrid = $("#hazardFactorsListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");
        $("#tempSearchColums div[name='searchColumsCenter']").remove();
        $("a[iconcls='icon-reload']").hide();
        $("#hazardFactorsList").datagrid('hideColumn', "rollBackRemark");
        $("input[name='reportStatus']").change(function() {
            var selectedValue = $("input[name='reportStatus']:checked").val();
            $("input[name='reportStatusProvince']").val(selectedValue);
            if(selectedValue == "0"){
                $("span:contains('上报煤监')").parents("a.l-btn").show();
                $("span:contains('从煤监撤回')").parents("a.l-btn").hide();
            }else{
                $("span:contains('上报煤监')").parents("a.l-btn").hide();
                $("span:contains('从煤监撤回')").parents("a.l-btn").show();
            }
            hazardFactorsListsearch();
        });
        $("span:contains('从煤监撤回')").parents("a.l-btn").hide();
    });

    function reportToProvince(){
        var rows = $("#hazardFactorsList").datagrid('getSelections');
        var reportUrl = "reportController.do?reportHazardFactorToProvince";
        if (rows.length == 0) {
            tip("请选择需要上报煤监的危害因素！");
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
        var rows = $("#hazardFactorsList").datagrid('getSelections');
        var reportUrl = "reportController.do?removeHazardFactorFromProvince";
        if (rows.length == 0) {
            tip("请选择需要撤回煤监的危害因素！");
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