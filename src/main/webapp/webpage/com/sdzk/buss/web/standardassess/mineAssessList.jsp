<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
<style>
    #function-panel td{
        padding:0px;
        text-align:center;
        vertical-align:center;
    }
    #function-panel .td-padding{
        padding-left: 8px !important;
        padding-right: 8px !important;
        text-align: left !important;
    }
    #function-panel .td-padding label{
        text-align: left !important;
        word-wrap:break-word !important;
        word-break: break-all !important;
        white-space: inherit !important;
    }
    #function-panel label{
        padding:0 !important;
    }
    #function-panel tr{
        height:30px;
    }
</style>
<div class="easyui-layout" fit="true">

    <div region="center" style="padding:0px;border:0px;">

        <t:datagrid name="mineAssessList" checkbox="false" queryMode="group" title="asdf" fit="false" pagination="false"
                    actionUrl="sfMineStandardAssessController.do?mineAssessListDatagrid&mineType=${mineType}&ssasMonth=${ssasMonth}" idField="id" >

            <t:dgCol title="唯一标识"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="考核类型"  field="ssaAssessType"  dictionary="ssaAssessType"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="考核月份"  field="ssasMonth" hidden="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="得分"  field="ssaSumScore"    queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
            <c:if test="${load ne 'detail'}">
                <t:dgFunOpt funname="setfunbyrole(id)" title="编辑"  urlclass="ace_button"></t:dgFunOpt>
            </c:if>
            <c:if test="${load eq 'detail'}">
                <t:dgFunOpt funname="setfunbyrole(id)" title="查看"  urlclass="ace_button"></t:dgFunOpt>
            </c:if>
        </t:datagrid>

    </div>
    <div>
        <table style="width: 100%">
            <tr>
                <td>
                    月份:

                </td>
                <td>
                    <input type="text" id="ssasMonth" name="ssasMonth" readonly value="${entity.ssasMonth}" style="width: 70px">
                </td>
                <td>
                    总得分:
                </td>
                <td>
                    <input type="text" name="ssasSumScore" id="ssasSumScore" value="${entity.ssasSumScore}" readonly style="width: 70px">
                </td>
            </tr>

            <tr>
                <td colspan="4" style="text-align: right">
                    <%--<c:if test="${load ne 'detail'}">--%>
                    <%--<a  class="easyui-linkbutton" href="JavaScript:doCommitMineStandarAssess('${entity.id}');" iconCls="icon-ok">提交</a>--%>
                    <%--</c:if>--%>
                    <%--<a href="javascript:gobackList()" class="easyui-linkbutton" id="btn_reset" iconCls="icon-back">返回</a>--%>
                    <div class="ui_buttons" style="text-align: right;">
                        <%--<c:if test="${load ne 'detail'}">--%>
                            <%--<input type="button" onclick="doCommitMineStandarAssess('${entity.id}');" value="提交" class="ui_state_highlight">--%>
                        <%--</c:if>--%>
                            <input type="button" id="btn_reset" onclick="gobackList();" value="返回">
                    </div>
                </td>
            </tr>

        </table>
    </div>
</div>
<div region="east" style="width: 1000px;" split="true">
    <div tools="#abc" class="easyui-panel" title='${load eq 'detail'?"查看":"编辑上报"}' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<div id="abc"></div>
</div>
<script type="text/javascript">
    //设置按钮可用
    function setBtnEnable(){
        $("#btn_reset").removeAttr("disabled");
    }

    function gobackList(){
        var mineType = "${mineType}";
        if("1" == mineType){
            document.location="sfMineStandardAssessController.do?sfMineStandardAssessScoreList&mineType=1";
        }else if("2" == mineType){
            document.location="sfMineStandardAssessController.do?sfMineStandardAssessScoreList&mineType=2";
        }

    }

    function doCommitMineStandarAssess(masterId){
        $.ajax({
            cache: true,
            type: "POST",
            url:"sfMineStandardAssessController.do?doCommitMineStandarAssess",
            data:{
                id:masterId
            },
            async: false,
            error: function(request) {
                alert("Connection error");
            },
            success: function(data) {
                //刷新总得分数据
                tip("提交成功");
                document.location="sfMineStandardAssessController.do?sfMineStandardAssessScoreList&mineType=1";
            }
        });
    }
    function setfunbyrole(id,rowIndex) {
        //选中
        var row = $("#mineAssessList").datagrid('getRows')[rowIndex];
        var cellData = $("table[class='datagrid-btable']:last tr:eq("+rowIndex+") td:eq(2) div").html();
            $("#function-panel").panel(
                    {
                        title : cellData+'考核:' + '${load eq "detail"?"查看":"编辑上报"}',
                        href:"sfMineStandardAssessController.do?goUpdate&load=${load}&from=mineAssess&masterId=${entity.id}&mineType=${mineType }&assessType="+row.ssaAssessType+"&id="+id,
                        onLoad : function(){
                            setBtnEnable();
                            initTabsData();
                            <c:if test="${load eq 'detail'}">
                            $("tr[class='luru']").css("display","none");
                            </c:if>
                        }
                    }
            );
    }

    $(document).ready(function(){
        $("#mineAssessListtb").hide();
        $("div[class='panel-header']").hide();
        //$("#btn_reset").attr('disabled',false);
        setBtnEnable();
    });

    function initTabsData(){
        if ($(".tabs").length > 0) {
            if ($("input[name^='ignore']").length > 0){
                $("input[name^='ignore']").each(function () {
                    var title = $(this).attr("title");
                    var tab_option = $('#tt').tabs('getTab', title).panel('options').tab;
                    if ($(this).attr("checked") == 'checked') {
                        tab_option.hide();
                    } else {
                        if (showFirstTab) {
                            tab_option.trigger("click");
                            showFirstTab = false;
                        }
                    }
                });
            }
            if ($("#cjdy").length > 0) {
                if ($("#cjdy>option:selected").val() == "N") {
                    var tab_option = $('#tt').tabs('getTab', "煤矿防治冲击地压").panel('options').tab;
                    tab_option.hide();
                }
            }
        }
    }

</script>
