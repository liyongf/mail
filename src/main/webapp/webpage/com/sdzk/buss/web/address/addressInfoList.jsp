<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
<span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="问题地点">地点：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="addressSelect" style="width: 130px;height: 15px"></div>
                 <input id="addressId" type="hidden" name="addressId">
             </span>
        </span>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="风险点状态">风险点状态：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 300px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                 <select name="addressStatus"  >
                      <option>--请选择--</option>
                      <option value="1">当前管控风险点</option>
                      <option value="0">全部</option>
                 </select>
             </span>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="addressList" checkbox="true" pagination="true" fitColumns="true" title="地点列表" actionUrl="tBAddressInfoController.do?addressInfoDatagrid" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键" field="id" hidden="true" queryMode="group" width="120"></t:dgCol>
            <t:dgCol title="地点名称" field="address" queryMode="single" width="120"  align="center"></t:dgCol>
            <t:dgCol title="关联风险数量" field="dangerSourceCount" url="riskIdentificationController.do?fxList&addressId={id}&expDate=true" width="80"  align="center"></t:dgCol>
            <t:dgCol title="风险类型" field="riskType" dictionary="risk_Type" queryMode="single" width="120" align="center" ></t:dgCol>
            <t:dgCol title="管控单位" field="manageUnit" queryMode="single" width="120" align="center"></t:dgCol>
            <t:dgCol title="分管责任人" field="manageMan" queryMode="single" width="120" align="center"></t:dgCol>
            <t:dgCol title="排查日期" field="investigationDate"  query="true"  formatter="yyyy-MM-dd"  queryMode="group" width="120" align="center"></t:dgCol>
            <t:dgCol title="开始日期" field="startDate"  query="true"  formatter="yyyy-MM-dd"  queryMode="group" width="120" align="center"></t:dgCol>
            <t:dgCol title="结束日期" field="endDate"  query="true"  formatter="yyyy-MM-dd"  queryMode="group" width="120" align="center"></t:dgCol>
            <%--<t:dgCol title="位置描述" field="description"  query="false" queryMode="single" width="120" align="center"></t:dgCol>
            <t:dgCol title="备注" field="remark"     queryMode="group" width="120" align="center"></t:dgCol>--%>
            <t:dgToolBar title="查看" icon="icon-search" url="tBAddressInfoController.do?goDetail" funname="detail" width="700" height="500" ></t:dgToolBar>
            <t:dgToolBar title="批量编辑" icon="icon-edit"  funname="goEdits" ></t:dgToolBar>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls"  ></t:dgToolBar>
        </t:datagrid>
    </div>
</div>

<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">
    $(function() {
        var datagrid = $("#addressListtb");
        datagrid.find("form[id='addressListForm']>span:last").after($("#tempSearchColums div[name='searchColums']").html());
        $("#tempSearchColums").empty();
        getAddressMagicSuggest($("#addressSelect"), $("[name='addressId']"));
    });

    function ExportXls() {

        var rows = $("#addressList").datagrid('getSelections');
        if (rows.length == 0) {
            JeecgExcelExport("tBAddressInfoController.do?exportXls", "addressList");
        } else if (rows.length >=1) {
            var idsTemp = new Array();
            for (var i = 0; i < rows.length; i++) {
                idsTemp.push(rows[i].id);
            }
            var idt = idsTemp.join(",");
            $.dialog.confirm("是否确认导出" + idsTemp.length + "条记录？", function () {
                JeecgExcelExport("tBAddressInfoController.do?exportXls&ids=" + idt, "addressList");
            });
        }
    }
    //批量编辑
    function goEdits(){
        var rows = $("#addressList").datagrid('getSelections');
        if(rows.length==0){
            tip("请选择编辑的问题");
            return false;
        }else{
            var ids = new Array();
            for (var i = 0; i < rows.length; i++) {
                ids.push(rows[i].id);
            }
            var url = "tBAddressInfoController.do?goBatchUpdate&ids="+ids;
            createwindow("批量编辑",url,950,600);
        }
    }
</script>