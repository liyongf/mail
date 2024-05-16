<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="postSearchColums" >
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="岗位">岗位：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="postSelect" style="width: 130px;height: 15px"></div>
                 <input id="postId" type="hidden" name="postId">
             </span>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="riskIdentificationPostList" checkbox="true" pagination="true" fitColumns="true" onDblClick="dblClickDetail" title="岗位风险辨识" actionUrl="riskIdentificationController.do?postDatagrid" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="岗位Id"  field="post.id" hidden="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="单位"  field="postUnit.departName"  queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="岗位"  field="post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
            <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="风险等级"  field="riskLevel" dictionary="factors_level" query="true"  width="120" ></t:dgCol>
            <t:dgCol title="危害因素"  align="center" field="hazardFactortsPostNum" url="riskIdentificationController.do?wxysPostList&id={id}&load=detail"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" ></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">
    function godetail(){
        var rows = $("#riskIdentificationPostList").datagrid('getSelections');
        if(rows== null || rows.length < 1){
            tip("请选择一条需要查看的数据!");
        }else{
            addOneTab("岗位风险辨识查看","riskIdentificationController.do?goAddPost&identificationType=4&postId="+rows[0]["post.id"]+"&id="+rows[0].id+"&load=detail","default");
        }
    }

    $(document).ready(function(){
        var datagrid = $("#riskIdentificationPostListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");
        $("#tempSearchColums div[name='searchColums']").remove();
        datagrid.find("form[id='riskIdentificationPostListForm']>span:first").before($("#tempSearchColums div[name='postSearchColums']").html());
        $("#tempSearchColums div[name='postSearchColums']").remove();
        getPostMagicSuggest($("#postSelect"), $("input[name='postId']"));
        $("a[iconcls='icon-reload']").hide();
    });


    //导出
    function ExportXls() {
        var rows = $("#riskIdentificationPostList").datagrid('getSelections');
        if (rows.length == 0) {
            JeecgExcelExport("riskIdentificationController.do?exportXlsPost&riskManage=true", "riskIdentificationPostList");
        } else if (rows.length >=1) {
            var idsTemp = new Array();
            for (var i = 0; i < rows.length; i++) {
                idsTemp.push(rows[i].id);
            }
            var idt = idsTemp.join(",");
            $.dialog.confirm("是否确认导出"+idsTemp.length+"条记录？", function () {
                JeecgExcelExport("riskIdentificationController.do?exportXlsPost&riskManage=true&ids="+idt, "riskIdentificationPostList");
            });
        }
    }

</script>