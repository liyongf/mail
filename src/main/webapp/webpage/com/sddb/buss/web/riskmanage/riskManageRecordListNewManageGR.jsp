<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">创建人：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="createManSelect" style="width: 130px;height: 15px"></div>
                <input class="inuptxt" type="hidden" style="width: 100px" name="createMan" id="createMan">
             </span>
        </span>
        <br>
        <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px;text-align: center;width: 100%">
            <form id="thisForm" action="riskManageTaskController.do?manageRecordNew" method="post">
                    <label style="margin-left:15px"><input id="type1" name="typeAll" onclick="doQuery()" type="radio"
                                                           value="1"
                                                           <c:if test="${typeAll eq '1'}">checked="checked"</c:if>/>上级派发</label>
                    <label style="margin-left:15px"><input id="type2" name="typeAll" onclick="doQuery()" type="radio"
                                                           value="2"
                                                           <c:if test="${typeAll eq '2'}">checked="checked"</c:if>/>个人任务</label>
            </form>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="riskManageTaskAllList" checkbox="true" pagination="true"  fitColumns="false" title="任务管理" actionUrl="riskManageTaskAllManageController.do?datagridGR" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="管控类型"  field="manageType" dictionary="manageType" hidden="false" queryMode="single" query="true"   width="120" align="center"></t:dgCol>
            <t:dgCol title="管控时间"  field="manageTime" formatter="yyyy-MM-dd" query="true"  queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="管控班次"  field="manageShift" dictionary="workShift" hidden="false" queryMode="single" query="true"  width="120" align="center"></t:dgCol>
            <t:dgCol title="创建人"  field="createNameTemp"   queryMode="group"  width="120" align="center"></t:dgCol>
            <t:dgCol title="备注"  field="remark"   queryMode="group"  width="200" align="center"></t:dgCol>
            <t:dgCol title="风险数"  field="riskCount" align="center"  queryMode="group"  width="120" url="riskIdentificationController.do?fxList&riskManageTaskAllGRId={id}"   ></t:dgCol>
            <t:dgCol title="隐患数"  field="hdCount" align="center"  queryMode="group"  width="120" url="riskManageTaskAllManageController.do?hdList&riskManageTaskAllGRId={id}"></t:dgCol>
            <t:dgCol title="危害因素落实数量"  field="implCount" align="center"  queryMode="group"  width="120"  url="riskIdentificationController.do?wxysImplList&riskManageTaskAllId={id}" ></t:dgCol>
            <t:dgCol title="状态"  field="status" replace="进行中_0,已完成_1" query="true" width="120" align="center" ></t:dgCol>
            <t:dgCol title="操作" field="opt" width="150" align="center"></t:dgCol>
            <t:dgFunOpt funname="viewingSurvey(id,riskCount,hdCount,implCount)" title="查看概况"  urlclass="ace_button" ></t:dgFunOpt>
            <t:dgFunOpt funname="viewingProgress(id,manageType)" title="查看进度"  urlclass="ace_button" ></t:dgFunOpt>
        </t:datagrid>
    </div>
</div>

<script type="text/javascript">

    $(document).ready(function(){
        var datagrid = $("#riskManageTaskAllListtb");
        datagrid.find("form[id='riskManageTaskAllListForm']>span:last").after($("#tempSearchColums div[name='searchColums']").html());
        $("#tempSearchColums").empty();
        getUserMagicSuggestWithValue($('#createManSelect'),  $("#createMan"), "", false);
        $("select[name='manageType']>option[value='comprehensive']").remove();
        $("select[name='manageType']>option[value='profession']").remove();
    });


    function doQuery(){
        $("#thisForm").submit();
    }

    function viewingSurvey(id,riskCount,hdCount,implCount) {
        var url = "riskManageTaskAllManageController.do?goSurveyGR&riskManageTaskAllId="+id+"&riskCount="+riskCount+"&hdCount="+hdCount+"&implCount="+implCount;
        createdetailwindow('任务概况',url,700,400);
    }
    function viewingProgress(id,manageType) {
        var manage = manageType;
        if (manage=="comprehensive"||manage=="profession"||manage=="team"||manage=="group") {
            var url = "riskManageTaskController.do?list&taskAllId="+id;
            addOneTab("管控任务清单",url,"default");
        }else if(manage=="post"){
            var url = "riskManageTaskController.do?postList&postTaskAllId="+id;
            addOneTab("管控任务清单",url,"default");
        }else{
            var url = "tBHiddenDangerExamController.do?newList&detail=true&examType=yh&taskAllId="+id;
            addOneTab("隐患查看",url,"default");
        }
    }

</script>