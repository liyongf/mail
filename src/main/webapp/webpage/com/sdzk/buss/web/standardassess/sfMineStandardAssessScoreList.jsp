<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<%--<div id="tempSearchColums" style="display: none">--%>
    <%--<div name="searchColumsCenter" >--%>
          <%--<span style="display:-moz-inline-box;display:inline-block;">--%>
          <%--<span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap;  ">--%>
            <%--<input  name="queryHandleStatus" value="false" type="hidden">--%>
            <%--<label>--%>
                <%--<input name="queryHandleStatusTem" type="radio" value="false" checked>--%>
                <%--未提交</label>--%>
            <%--<label style="margin-left: 15px;">--%>
                <%--<input name="queryHandleStatusTem"  type="radio" value="true">--%>
                <%--已提交</label>--%>
        <%--</span>--%>
         <%--</span>--%>
    <%--</div>--%>
<%--</div>--%>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px;">
        <t:datagrid name="sfMineStandardAssessScoreList" fit="true" checkbox="true" queryMode="group" title="矿井考核自评汇总" actionUrl="sfMineStandardAssessController.do?sfMineStandardAssessScoreDatagrid&mineType=${mineType}" idField="id" onDblClick="goViewScore">
            <t:dgCol title="唯一标识"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="考核月份"  field="ssasMonth" query="true" queryMode="single"  width="120" align="center"></t:dgCol>
            <t:dgCol title="得分"  field="ssasSumScore"    queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgToolBar title="编辑" operationCode="update"  icon="icon-edit"  funname="editScore"></t:dgToolBar>
            <t:dgToolBar title="查看" operationCode="detail"  icon="icon-search" width="1000" height="800" url="sfMineStandardAssessController.do?goUpdate&mineType=${mineType}&assessType=${assessType}" funname="viewScore"></t:dgToolBar>
        </t:datagrid>
        </div>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        //给时间控件加上样式
        $("#sfMineStandardAssessScoreListtb").find("input[name='ssasMonth']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});

        var datagrid = $("#sfMineStandardAssessScoreListtb");
        datagrid.find("div[name='searchColums']>form>span:last").after($("#tempSearchColums div[name='searchColumsCenter']").html());
        $("#tempSearchColums div[name='searchColumsCenter']").remove();
        $("a[iconcls='icon-reload']").hide();

//        $("input[name='queryHandleStatusTem']").change(function() {
//            var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
//            $("input[name='queryHandleStatus']").val(selectedvalue);
//
//            if(selectedvalue == "true"){
//                //已提交，隐藏编辑、删除、录入、导入、导出、模板下载按钮
//                $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
//            }else{
//                //草稿，显示编辑、删除、录入、导入、导出、模板下载按钮
//                $("div[class='datagrid-toolbar']>span:first>a[icon='icon-edit']").css("display","");
//            }
//            sfMineStandardAssessScoreListsearch();
//        });
    });
    function editScore() {
        //选中
        var rows = $("#sfMineStandardAssessScoreList").datagrid('getSelections');
        if(rows== null || rows.length != 1){
            tip("请选择一条要编辑上报的考核数据!");
        }else{
            document.location="sfMineStandardAssessController.do?goMineAssessList&id="+rows[0].id+"&mineType=${mineType}&ssasMonth=" + rows[0].ssasMonth;
        }
    }

    function goViewScore(rowIndex,rowData){
        document.location="sfMineStandardAssessController.do?goMineAssessList&load=detail&id="+rowData.id+"&mineType=${mineType}&ssasMonth=" + rowData.ssasMonth;
    }

    function viewScore() {
        //选中
        var rows = $("#sfMineStandardAssessScoreList").datagrid('getSelections');
        if(rows== null || rows.length != 1){
            tip("请选择一条要查看的考核数据!");
        }else{
            document.location="sfMineStandardAssessController.do?goMineAssessList&load=detail&id="+rows[0].id+"&mineType=${mineType}&ssasMonth=" + rows[0].ssasMonth;
        }
    }

</script>
