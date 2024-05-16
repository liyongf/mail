<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

    <div name="postSearchColums" >
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="岗位">岗位：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="postSelect" style="width: 130px;height: 15px"></div>
                 <input id="postId" type="hidden" name="postId">
             </span>
        </span>
    </div>

    <div name="modularSearchColums" >
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="风险点">模块：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                 <div id="modularSelect" style="width: 130px;height: 15px"></div>
                 <input id="modular" type="hidden" name="modular">
             </span>
        </span>
    </div>
</div>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="riskManageList" checkbox="true" pagination="true" fitColumns="false" title="" actionUrl="riskManageController.do?relRiskDatagrid&manageType=${manageType}&majorId=${majorId}&rel=${rel}&majorRelId=${majorRelId}" idField="id" fit="true" queryMode="group">
     <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="风险id"  field="risk.id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <c:if test="${manageType eq 'post'}">
        <t:dgCol title="岗位id"  field="risk.post.id" hidden="true"  queryMode="single"  width="120" query="false"></t:dgCol>
        <t:dgCol title="岗位"  field="risk.post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
    </c:if>
    <c:if test="${manageType ne 'post'}">
        <t:dgCol title="风险点id"  field="risk.address.id" hidden="true"  queryMode="single"  width="120" query="false"></t:dgCol>
        <t:dgCol title="风险点"  field="risk.address.address"  queryMode="single"  width="120" query="false"></t:dgCol>
    </c:if>
    <t:dgCol title="风险类型"  field="risk.riskType" dictionary="risk_type" query="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="风险描述"  field="risk.riskDesc"  formatterjs="valueTitle" queryMode="group"  width="200"></t:dgCol>
    <t:dgCol title="风险等级"  field="risk.riskLevel" dictionary="factors_level"   query="true"  width="120" ></t:dgCol>
    <t:dgCol title="危害因素和管控措施" align="center"  field="risk.hazardFactortsNum" url="riskIdentificationController.do?wxysList&riskManageId={id}&load=detail" queryMode="group"  width="120" ></t:dgCol>
    <t:dgCol title="最高管控层级"  field="risk.manageLevel" dictionary="identifi_mange_level"   query="true"  width="120" ></t:dgCol>
    <t:dgCol title="最高管控责任人"  field="risk.dutyManager"   query="true"  width="150" ></t:dgCol>
    <t:dgCol title="评估日期"  field="risk.identifiDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
    <t:dgCol title="解除日期"  field="risk.expDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
    <t:dgCol title="信息来源"  field="risk.identificationType" dictionary="identifi_from" hidden="false" queryMode="single" query="true"  width="120"></t:dgCol>
    <t:dgToolBar title="添加" icon="icon-add" funname="selectRisk" ></t:dgToolBar>
    <t:dgToolBar title="删除"  icon="icon-remove" url="riskManageController.do?doBatchDel&riskManageAll=major&majorId=${majorId}" funname="deleteALLSelect"  ></t:dgToolBar>
    <t:dgToolBar title="查看风险" icon="icon-search" url="riskIdentificationController.do?goDetail" funname="detail"></t:dgToolBar>
    <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" ></t:dgToolBar>
</t:datagrid>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">
    function selectRisk() {
        createdetailwindow('风险添加','riskManageController.do?selectRiskByMajorId&manageType=${manageType}&rel=noRel&majorId=${majorId}&majorRelId=${majorRelId}',1200,600);
    }
    window.top["reload_riskManageList"]=function(){
        $("#riskManageList").datagrid( "load");
    };

    function detail(title,url, id,width,height) {
        var rowsData = $('#'+id).datagrid('getSelections');
        if (!rowsData || rowsData.length == 0) {
            tip('请选择查看项目');
            return;
        }
        if (rowsData.length > 1) {
            tip('请选择一条记录再查看');
            return;
        }
        addOneTab("风险查看", "riskIdentificationController.do?goAdd&identificationType=" + rowsData[0]["risk.identificationType"] + "&addressId=" + rowsData[0]["risk.address.id"] + "&postId=" + rowsData[0]["risk.post.id"] + "&load=detail", "default");
    }

    //导出
    function ExportXls() {
        var rows = $("#riskManageList").datagrid('getSelections');
        if (rows.length == 0) {
            JeecgExcelExport("riskManageController.do?exportXls&manageType=${manageType}&myList=true", "riskManageList");
        } else if (rows.length >=1) {
            var idsTemp = new Array();
            for (var i = 0; i < rows.length; i++) {
                idsTemp.push(rows[i].id);
            }
            var idt = idsTemp.join(",");
            $.dialog.confirm("是否确认导出"+idsTemp.length+"条记录？", function () {
                JeecgExcelExport("riskManageController.do?exportXls&manageType=${manageType}&ids="+idt, "riskManageList");
            });
        }
    }

    $(document).ready(function(){
        var datagrid = $("#riskManageListtb");
        <c:if test="${manageType ne 'post'}">
        datagrid.find("form[id='riskManageListForm']>span:first").before($("#tempSearchColums div[name='addressSearchColums']").html());
        $("#tempSearchColums div[name='addressSearchColums']").remove();
        getAddressMagicSuggest($("#addressSelect"), $("[name='addressId']"));
        </c:if>
        <c:if test="${manageType eq 'post'}">
        datagrid.find("form[id='riskManageListForm']>span:first").before($("#tempSearchColums div[name='postSearchColums']").html());
        $("#tempSearchColums div[name='postSearchColums']").remove();
        getPostMagicSuggest($("#postSelect"), $("input[name='postId']"));
        </c:if>

        $("a[iconcls='icon-reload']").hide();


//        datagrid.find("form[id='riskManageListForm']").append($("#tempSearchColums div[name='modularSearchColums']").html());
//        $("#tempSearchColums div[name='modularSearchColums']").remove();
//
//        var magicsuggestModularSelect = $('#modularSelect').magicSuggest({
//            data:'magicSelectController.do?getModularList',
//            allowFreeEntries: false,
//            valueField:'id',
//            placeholder:'输入或选择',
//            maxSelection:1,
//            selectFirst: true,
//            highlight: false,
//            displayField:'module_name'
//        });
//        $(magicsuggestModularSelect).on('selectionchange', function(e,m){
//            $("#modular").val(magicsuggestModularSelect.getValue());
//        });


    });
</script> 