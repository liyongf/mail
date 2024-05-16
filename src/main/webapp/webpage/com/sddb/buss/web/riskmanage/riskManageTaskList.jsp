<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div class="easyui-layout" fit="true"  id="main_majorHiddenDangerList">
    <div region="center" style="padding:0px;border:0px" >
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
            <div name="searchColums" >
                <br>
                <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px;text-align: center;width: 100%">
        <input  name="queryHandleStatus" value="0" type="hidden">
           <label>
               <input name="queryHandleStatusTem" type="radio" value="all" >
               全部
           </label>
           <label>
               <input name="queryHandleStatusTem" type="radio" value="0"  checked="checked">
               未完成
           </label>
           <label>
               <input name="queryHandleStatusTem" type="radio" value="1" >
               已完成
           </label>
        </span>
            </div>
        </div>
        <div class="easyui-layout" fit="true" id="main_typegroup_list">
            <div region="center" style="padding:0px;border:0px">
                <t:datagrid name="riskManageTaskList" checkbox="true" pagination="true" fitColumns="true" title="${riskManageName}记录" actionUrl="riskManageTaskController.do?relRiskDatagrid&manageType=${manageType}&taskAllId=${taskAllId}" idField="id" fit="true" queryMode="group">
                    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
                    <t:dgCol title="风险id"  field="risk.id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>

                    <c:if test="${jinda eq 'true'}">
                        <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
                    </c:if>
                    <c:if test="${jinda ne 'true'}">
                        <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group"  width="120" ></t:dgCol>
                    </c:if>
                    <c:if test="${manageType ne 'post'}">
                        <t:dgCol title="风险点"  field="risk.address.address"  queryMode="single"  width="120" query="false"></t:dgCol>
                        <t:dgCol title="风险点id"  field="risk.address.id" hidden="true"  queryMode="single"  width="120" query="false"></t:dgCol>
                    </c:if>
                    <c:if test="${manageType eq 'post'}">
                        <t:dgCol title="岗位id"  field="risk.post.id" hidden="true"  queryMode="single"  width="120" query="false"></t:dgCol>
                        <t:dgCol title="岗位"  field="risk.post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
                    </c:if>

                    <t:dgCol title="风险类型"  field="risk.riskType" dictionary="risk_type" query="true" queryMode="single"  width="120"></t:dgCol>
                    <%--<t:dgCol title="风险辨识类型"  field="risk.identificationType" hidden="true" queryMode="single"  width="120"></t:dgCol>--%>
                    <t:dgCol title="风险描述"  field="risk.riskDesc" formatterjs="valueTitle"   queryMode="group"  width="200"></t:dgCol>
                    <t:dgCol title="风险等级"  field="risk.riskLevel" dictionary="factors_level"  query="true"  width="120" ></t:dgCol>
                    <%--<t:dgCol title="危害因素"  field="risk.hazardFactortsNum"  queryMode="group"  width="120" ></t:dgCol>--%>
                    <t:dgCol title="危害因素已完成数"  field="hazardFactorNumFinished" hidden="true" queryMode="group"  width="120" ></t:dgCol>
                    <t:dgCol title="危害因素总数"  align="center" field="hazardFactorNum" hidden="true" queryMode="group"  width="120" ></t:dgCol>
                    <t:dgCol title="危害因素"  field="risk.hazardFactortsNum"  queryMode="group"  width="120" formatterjs="showHazardFactorProcess" align="center" ></t:dgCol>
                    <t:dgCol title="最高管控层级"  field="risk.manageLevel" dictionary="identifi_mange_level" query="true"  width="120" ></t:dgCol>
                    <t:dgCol title="最高管控责任人"  field="risk.dutyManager"  query="true"  width="120" ></t:dgCol>
                    <t:dgCol title="评估日期"  field="risk.identifiDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
                    <t:dgCol title="解除日期"  field="risk.expDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
                    <t:dgCol title="信息来源"  field="risk.identificationType" dictionary="identifi_from" hidden="false" queryMode="single" query="true"  width="120"></t:dgCol>
                    <%--<t:dgToolBar title="添加" icon="icon-add" funname="selectRisk" ></t:dgToolBar>
                    <t:dgToolBar title="删除"  icon="icon-remove" url="riskManageTaskController.do?doBatchDel" funname="deleteALLSelect"  ></t:dgToolBar>--%>
                    <t:dgToolBar title="查看风险" icon="icon-search" url="riskIdentificationController.do?goDetail" funname="detail" ></t:dgToolBar>
                    <t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
                    <t:dgFunOpt funname="showHazardFactorsList(id)" title="查看管控结果"  urlclass="ace_button" ></t:dgFunOpt>
                </t:datagrid>
            </div>
        </div>
    </div>
</div>
<div data-options="region:'east',
	title:'危害因素列表',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
     style="width: 1000px; overflow: hidden;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="hazardFactorListpanel"></div>
</div>
<div class="row border-bottom" ></div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">

    $(document).ready(function(){
        var datagrid = $("#riskManageTaskListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html());
        $("a[iconcls='icon-reload']").hide();
        <c:if test="${manageType ne 'post'}">
            datagrid.find("form[id='riskManageTaskListForm']>span:first").before($("#tempSearchColums div[name='addressSearchColums']").html());
            $("#tempSearchColums div[name='addressSearchColums']").remove();
            getAddressMagicSuggest($("#addressSelect"), $("[name='addressId']"));
        </c:if>
        <c:if test="${manageType eq 'post'}">
            datagrid.find("form[id='riskManageTaskListForm']>span:first").before($("#tempSearchColums div[name='postSearchColums']").html());
            $("#tempSearchColums div[name='postSearchColums']").remove();
            getPostMagicSuggest($("#postSelect"), $("input[name='postId']"));
        </c:if>

        $("input[type=radio][name='queryHandleStatusTem']").change(function() {
            var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
            $("input[name='queryHandleStatus']").val(selectedvalue);
            riskManageTaskListsearch();
        });

        datagrid.find("form[id='riskManageTaskListForm'] span [name='risk.dutyManager']").prev().css("width","100");

    });

    function selectRisk(){
        $.dialog({id:'dialog',title:'风险添加',zIndex:2100,modal:true,content: 'url:riskManageTaskController.do?selectRisk&manageType=${manageType}&taskAllId=${taskAllId}',lock:true,width: 1200,height: 600});
    }

    function showMsg(msg){
        tip(msg);
    }
    function reloadP(){
        $('#riskManageTaskList').datagrid('reload');
    }


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

    function showHazardFactorsList(id){
        var width = $("div.row.border-bottom").width();
        if(li_east == 0){
            $('#main_majorHiddenDangerList').layout('expand','east');
        }
        $('#main_majorHiddenDangerList').layout('panel','east').panel('setTitle', "危害因素列表");
        $('#main_majorHiddenDangerList').layout('panel','east').panel('resize',{width:width+2});
        $('#hazardFactorListpanel').panel("refresh", "riskManageResultController.do?hazardFactorList&load=detail&taskManageId="+id);
    }

    function showHazardFactorProcess(value,rec,index){
        return rec.hazardFactorNumFinished+"/"+rec.hazardFactorNum;
        //return "<a href='#' onclick='showHazardFactorsList(\"" + rec.id + "\");'>"+rec.hazardFactorNumFinished+"/"+rec.hazardFactorNum+"</a>";
    }
</script>