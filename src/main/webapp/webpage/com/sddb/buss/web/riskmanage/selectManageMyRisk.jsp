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
        <t:datagrid name="riskList" checkbox="true" pagination="true" fitColumns="false" title="风险列表" actionUrl="riskManageController.do?riskMyDatagrid&identificationType=${identificationType}&manageType=${manageType}" idField="id" fit="true" queryMode="group">
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
            <%--<t:dgCol title="风险辨识类型"  field="identificationType" hidden="true" queryMode="single"  width="120"></t:dgCol>--%>
            <t:dgCol title="风险描述"  field="riskDesc" formatterjs="valueTitle"  queryMode="group"  width="200"></t:dgCol>
            <t:dgCol title="风险等级"  field="riskLevel" dictionary="factors_level"	 queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="危害因素和管控措施" align="center"  field="hazardFactortsNum" url="riskIdentificationController.do?wxysList&id={id}&load=detail"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="最高管控层级"  field="manageLevel" dictionary="identifi_mange_level" queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="最高管控责任人"  field="dutyManager"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="评估日期"  field="identifiDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="解除日期"  field="expDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="信息来源"  field="identificationType" dictionary="identifi_from" hidden="false" queryMode="single" query="true"  width="120"></t:dgCol>
            <%--<t:dgToolBar title="查看" icon="icon-search" url="riskIdentificationController.do?goDetail" funname="detail"></t:dgToolBar>--%>

        </t:datagrid>
    </div>
</div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">
    var api = frameElement.api, W = api.opener, D = W.document;

    api.button(
            {
                name: '保存',
                callback: function(){
                    var ids = [];
                    var rows = $('#riskList').datagrid('getSelections');
                    if(rows.length <=0){
                        api.close();
                    }else{
                        for (var i = 0; i < rows.length; i++) {
                            ids.push(rows[i].id);
                        }
                        ids.join(',');
                        $.ajax({
                            url: "riskManageController.do?saveRelMyRisk&manageType=${manageType}&ids="+ids+"&tt="+new Date(),
                            type: 'POST',
                            error: function(){
                            },
                            success: function(data){
                                data = $.parseJSON(data);
                                W.showMsg(data.msg);
                                W.reloadP();
                                api.close();
                            }
                        });
                    }
                    return false;

                },
                focus: true
            },
            {
                name: '取消'
            }
    );

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

        addOneTab("风险查看","riskIdentificationController.do?goAdd&identificationType="+rowsData[0]["identificationType"]+"&addressId="+rowsData[0]["address.id"]+"&load=detail","default");
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