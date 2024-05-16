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
               待处理
           </label>
           <label>
               <input name="queryHandleStatusTem" type="radio" value="1" >
               已处理
           </label>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true" id="main_typegroup_list">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="riskManagePostResultList" checkbox="true" pagination="true" fitColumns="true" title="岗位管控结果" actionUrl="riskManageResultController.do?relPostRiskDatagrid&postTaskAllId=${postTaskAllId}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险id"  field="postRisk.id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <c:if test="${jinda eq 'true'}">
                <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd"  queryMode="group"  width="130" ></t:dgCol>
            </c:if>
            <c:if test="${jinda ne 'true'}">
                <t:dgCol title="创建时间"  field="createDate" formatter="yyyy-MM-dd hh:mm:ss"  queryMode="group"  width="130" ></t:dgCol>
            </c:if>
            <t:dgCol title="状态"  field="handleStatus"  hidden="true"  queryMode="group"  width="130" ></t:dgCol>
            <t:dgCol title="岗位id"  field="postRisk.post.id" hidden="true"  queryMode="single"  width="120" query="false"></t:dgCol>
            <t:dgCol title="单位"  field="postRisk.postUnit.departname"  queryMode="single"  width="120" query="false"></t:dgCol>
            <t:dgCol title="岗位"  field="postRisk.post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
            <t:dgCol title="风险类型"  field="postRisk.riskType" dictionary="risk_type" query="true" queryMode="single"  width="120"></t:dgCol>
            <t:dgCol title="风险等级"  field="postRisk.riskLevel" dictionary="factors_level"	 query="true"  width="120" ></t:dgCol>
            <t:dgCol title="危害因素已完成数"  field="hazardFactorNumFinished" hidden="true" queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="危害因素总数"  align="center" field="hazardFactorNum" hidden="true" queryMode="group"  width="120" ></t:dgCol>
            <t:dgCol title="危害因素"  field="postRisk.hazardFactortsNum"  queryMode="group"  width="120" formatterjs="showHazardFactorProcess" align="center"></t:dgCol>
           <%-- <t:dgToolBar title="添加" icon="icon-add" funname="selectRisk" ></t:dgToolBar>--%>
            <t:dgToolBar title="删除"  icon="icon-remove" url="riskManageTaskController.do?doBatchDelPost&postTaskAllId=${postTaskAllId}" funname="deleteALLSelect"  ></t:dgToolBar>
            <t:dgToolBar title="补充隐患录入" icon="icon-add"  funname="addHiddenNew"></t:dgToolBar>
            <t:dgCol title="操作" field="opt" width="200" align="center"></t:dgCol>
            <t:dgFunOpt funname="showHazardFactorsList(id)" title="开始管控"  urlclass="ace_button" exp="handleStatus#eq#0" ></t:dgFunOpt>
            <t:dgFunOpt funname="showHazardFactorsList(id)" title="查看"  urlclass="ace_button" exp="handleStatus#eq#1"></t:dgFunOpt>
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
     style="width: 900px; overflow: hidden;z-index: 99;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="hazardFactorListpanel"></div>
</div>
<div class="row border-bottom" ></div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">

    $(document).ready(function(){
        var datagrid = $("#riskManagePostResultListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html());
        $("a[iconcls='icon-reload']").hide();
        $("input[type=radio][name='queryHandleStatusTem']").change(function() {
            var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
            $("input[name='queryHandleStatus']").val(selectedvalue);
            riskManagePostResultListsearch();
        });
    });

    function selectRisk(){
        $.dialog({id:'dialog',title:'风险添加',zIndex:2100,modal:true,content: 'url:riskManageTaskController.do?selectPostRisk&postTaskAllId=${postTaskAllId}',lock:true,width: 1200,height: 600});
    }

    function showMsg(msg){
        tip(msg);
    }
    function reloadP(){
        $('#riskManagePostResultList').datagrid('reload');
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
        $('#hazardFactorListpanel').panel("refresh", "riskManageResultController.do?hazardFactorPostList&load=add&postTaskAllId=${postTaskAllId}&taskManagePostId="+id);
    }


    function showHazardFactorProcess(value,rec,index){
        return rec.hazardFactorNumFinished+"/"+rec.hazardFactorNum;
        //return "<a href='#' onclick='showHazardFactorsList(\"" + rec.id + "\");'>"+rec.hazardFactorNumFinished+"/"+rec.hazardFactorNum+"</a>";
    }

    function loadSuccess() {
        $('#main_typegroup_list').layout('panel','east').panel('setTitle', "");
        $('#main_typegroup_list').layout('collapse','east');
        $('#hazardFactorListpanel').empty();
    }

    window.top["reload_riskManagePostResultList"]=function(){
        $("#riskManagePostResultList").datagrid("reload");
        riskManagePostResultListsearch();
        window.top.reload_riskManagePostTaskAllList.call();
    };

    function addHiddenNew(){
        var url = "tBHiddenDangerExamController.do?newList&examType=yh&taskAllId=${postTaskAllId}";

        addOneTab("补充隐患录入",url,"default");
    }
</script>