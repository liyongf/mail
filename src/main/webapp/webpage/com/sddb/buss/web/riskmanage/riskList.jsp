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
</div>
<%@include file="/context/mytags.jsp"%>
<t:datagrid name="riskList" checkbox="false" pagination="true" fitColumns="false" title="" actionUrl="riskManageController.do?queryListByRiskTypeDatagrid&riskType=${riskType}&manageLevel=${manageLevel}&rel=${rel}" idField="id" fit="true" queryMode="group">
    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
    <t:dgCol title="风险点Id"  field="address.id" hidden="true" queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="风险点"  field="address.address"  queryMode="single"  width="120" ></t:dgCol>
    <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type"  queryMode="single"  width="120"></t:dgCol>
    <t:dgCol title="风险描述"  field="riskDesc" formatterjs="valueTitle"  queryMode="group"  width="200"></t:dgCol>
    <t:dgCol title="风险等级"  field="riskLevel"  dictionary="factors_level" queryMode="single" query="true"  width="120" ></t:dgCol>
    <t:dgCol title="危害因素和管控措施" align="center"  field="hazardFactortsNum" url="riskIdentificationController.do?wxysList&id={id}&load=detail"  queryMode="group"  width="120" ></t:dgCol>
    <t:dgCol title="最高管控层级"  field="manageLevel"  dictionary="identifi_mange_level"  queryMode="single"  width="120" ></t:dgCol>
    <t:dgCol title="最高管控责任人"  field="dutyManager"  queryMode="single"  width="120" ></t:dgCol>
    <t:dgCol title="评估日期"  field="identifiDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
    <t:dgCol title="解除日期"  field="expDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
</t:datagrid>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">

    $(document).ready(function(){
        var datagrid = $("#riskListtb");

        datagrid.find("form[id='riskListForm']>span:first").before($("#tempSearchColums div[name='addressSearchColums']").html());
        $("#tempSearchColums div[name='addressSearchColums']").remove();
        getAddressMagicSuggest($("#addressSelect"), $("[name='addressId']"));
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