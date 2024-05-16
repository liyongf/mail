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

<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="riskList" checkbox="true" pagination="true" fitColumns="false" title="风险列表" actionUrl="riskManageController.do?riskDatagrid&majorId=${majorId}&majorRelId=${majorRelId}&identificationType=${identificationType}&manageType=${manageType}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>

            <c:if test="${manageType ne 'post'}">
            <t:dgCol title="风险点"  field="address.address"  queryMode="single"  width="120" query="false"></t:dgCol>
            <t:dgCol title="风险点id"  field="address.id" hidden="true"  queryMode="single"  width="120" query="false"></t:dgCol>
            </c:if>

            <c:if test="${manageType eq 'post'}">
            <t:dgCol title="岗位id"  field="post.id" hidden="true"  queryMode="single"  width="120" query="false"></t:dgCol>
            <t:dgCol title="岗位"  field="post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
            </c:if>

            <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="风险描述"  field="riskDesc" formatterjs="valueTitle"  queryMode="group"  width="200"></t:dgCol>
            <t:dgCol title="风险等级"  field="riskLevel" dictionary="factors_level"	 queryMode="single"  width="120" query="true"></t:dgCol>
            <t:dgCol title="危害因素和管控措施" align="center"  field="hazardFactortsNum" url="riskIdentificationController.do?wxysList&id={id}&load=detail" queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="最高管控层级"  field="manageLevel" dictionary="identifi_mange_level" queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="最高管控责任人"  field="dutyManager"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="评估日期"  field="identifiDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="解除日期"  field="expDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="信息来源"  field="identificationType" dictionary="identifi_from" hidden="false" queryMode="single" query="true"  width="120"></t:dgCol>
            <t:dgToolBar title="添加" icon="icon-add"  width="500"  funname="addRel" ></t:dgToolBar>

        </t:datagrid>
    </div>
</div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">

    window.top["reload_riskList"]=function(){
        $("#riskList").datagrid( "load");
    };

    function addRel() {
        var majorId="${majorId}";
        var majorRelId="${majorRelId}";
        var manageType="${manageType}";
        var ids = [];
        var rowsData = $('#riskList').datagrid('getSelections');
        if (!rowsData || rowsData.length==0) {
            tip('请选择需要添加的数据');
            return;
        }else{
            for ( var i = 0; i < rowsData.length; i++){
                ids.push(rowsData[i].id);
            }
        }
        if (majorRelId==null||majorRelId==""||majorRelId==undefined) {
            $.ajax({
                url:'riskManageController.do?saveRelRisk',
                type: 'post',
                data:{
                    majorId : majorId,
                    majorRelId : majorRelId,
                    manageType : manageType,
                    ids : ids.join(',')
                },
                success:function (data) {
                    var data = $.parseJSON(data);
                    tip(data.msg);
                    window.top.reload_riskList.call();
                    window.top.reload_riskManageList.call();
                }
            });
        }else{
            $.ajax({
                url:'riskManageController.do?saveRelMyRisk',
                type: 'post',
                data:{
                    majorId : majorId,
                    majorRelId : majorRelId,
                    manageType : manageType,
                    ids : ids.join(',')
                },
                success:function (data) {
                    var data = $.parseJSON(data);
                    tip(data.msg);
                    window.top.reload_riskList.call();
                    window.top.reload_riskManageList.call();
                }
            });
        }
    }


    $(document).ready(function(){
        var datagrid = $("#riskListtb");
        <c:if test="${manageType ne 'post'}">
            datagrid.find("form[id='riskListForm']>span:first").before($("#tempSearchColums div[name='addressSearchColums']").html());
            $("#tempSearchColums div[name='addressSearchColums']").remove();
            getAddressMagicSuggest($("#addressSelect"), $("[name='addressId']"));
        </c:if>
        <c:if test="${manageType eq 'post'}">
            datagrid.find("form[id='riskListForm']>span:first").before($("#tempSearchColums div[name='postSearchColums']").html());
            $("#tempSearchColums div[name='postSearchColums']").remove();
            getPostMagicSuggest($("#postSelect"), $("input[name='postId']"));
        </c:if>

        $("a[iconcls='icon-reload']").hide();
        datagrid.find("form[id='riskListForm']").append($("#tempSearchColums div[name='modularSearchColums']").html());
        $("#tempSearchColums div[name='modularSearchColums']").remove();

        var magicsuggestModularSelect = $('#modularSelect').magicSuggest({
            data:'magicSelectController.do?getModularList',
            allowFreeEntries: false,
            valueField:'id',
            placeholder:'输入或选择',
            maxSelection:1,
            selectFirst: true,
            highlight: false,
            displayField:'module_name'
        });
        $(magicsuggestModularSelect).on('selectionchange', function(e,m){
            $("#modular").val(magicsuggestModularSelect.getValue());
        });
    });
</script>