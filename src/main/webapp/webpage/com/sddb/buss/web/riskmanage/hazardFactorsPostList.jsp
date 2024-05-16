<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%--<t:base type="jquery,easyui,tools,DatePicker"></t:base>--%>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<div id="tempSearchColums2" style="display: none">
    <div name="searchColums2" >
        <br>
       <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px;text-align: center;width: 100%">
        <input  name="queryHandleStatus2" value="0" type="hidden">
           <label>
               <input name="queryHandleStatusTem2" type="radio" value="all" >
               全部
           </label>
           <label>
               <input name="queryHandleStatusTem2" type="radio" value="0"  checked="checked">
               未落实到位
           </label>
           <label>
               <input name="queryHandleStatusTem2" type="radio" value="1" >
               已落实到位
           </label>
        </span>
    </div>
</div>

<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="hazardFactorsPostList" checkbox="true" pagination="true" fitColumns="true" title="" actionUrl="riskManageResultController.do?hazardFactorPostDatagrid&riskManagePostTaskEntity.id=${taskManagePostId}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="危害因素ID"  field="hazardFactorPost.id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="危害因素"  field="hazardFactorPost.hazardFactors"  formatterjs="valueTitle" query="true"  width="550"></t:dgCol>
            <t:dgCol title="落实情况"  field="implDetail"  queryMode="group"  width="400" ></t:dgCol>
            <t:dgCol title="落实结论"  field="handleStatus" hidden="true" queryMode="group"  width="450" ></t:dgCol>
           <%-- <t:dgCol title="备注"  field="remark" queryMode="group"  width="400" ></t:dgCol>--%>
            <t:dgCol title="风险数"  field="riskCount"  queryMode="group"  width="200" formatterjs="showRiskList" hidden="true" ></t:dgCol>
            <t:dgCol title="隐患数"  field="hdCount"  queryMode="group"  width="200" formatterjs="showHdList" ></t:dgCol>

            <t:dgToolBar title="查看" icon="icon-search" url="riskManageResultController.do?goUpdatePost" funname="detail" height="450" ></t:dgToolBar>
            <c:if test="${load =='add'}">
                <t:dgCol title="操作" field="opt" width="500" align="center"></t:dgCol>
                <%--<t:dgFunOpt funname="addRisk(id)" title="新增风险"  urlclass="ace_button" exp="handleStatus#ne#1" ></t:dgFunOpt>--%>
                <t:dgFunOpt funname="goImpl(id)" title="落实到位"  urlclass="ace_button" exp="handleStatus#ne#1&&hdCount#eq#0" ></t:dgFunOpt>
                <t:dgFunOpt funname="addHd(id)" title="未落实到位"  urlclass="ace_button" exp="handleStatus#ne#1" ></t:dgFunOpt>
                <t:dgToolBar title="添加危害因素" icon="icon-add" url="hazardFactorsController.do?goAddPost&taskManagePostId=${taskManagePostId}" funname="add" width="600" height="350"></t:dgToolBar>
                <%--<t:dgToolBar title="落实到位" icon="icon-edit" url="riskManageResultController.do?goUpdatePost" funname="update" height="450"></t:dgToolBar>--%>
                <t:dgToolBar title="批量落实到位" icon="icon-edit" url="riskManageResultController.do?goAllUpdatePost" funname="updateAll" height="450"></t:dgToolBar>
                <t:dgToolBar title="新增风险" icon="icon-add"  funname="addRiskNew"></t:dgToolBar>
                <t:dgToolBar title="补充隐患录入" icon="icon-add"  funname="addHiddenNew"></t:dgToolBar>
            </c:if>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">

    function showRiskList(value,rec,index){
        var url = "riskManageResultController.do?showriskPostList&riskManagePostHazardFactorId="+rec.id;
        //return "<a href='#' onclick='createdetailwindow(\"风险列表\",\"" + url + "\",800,600);'>"+value+"</a>";
        return "<a href='#' onclick='addOneTab(\"已录风险列表\",\"" + url + "\",\"default\");'>"+value+"</a>";
    }
    function showHdList(value,rec,index){
        var url = "riskManageResultController.do?showHdPostList&riskManagePostHazardFactorId="+rec.id;
        //return "<a href='#' onclick='createdetailwindow(\"隐患列表\",\"" + url + "\",800,600);'>"+value+"</a>";
        return "<a href='#' onclick='addOneTab(\"已录隐患列表\",\"" + url + "\",\"default\");'>"+value+"</a>";
    }

    function addRisk(id){
        //riskIdentificationController.do?goAdd&identificationType=2
        var url = "riskManageResultController.do?addPostRisk&riskManagePostHazardFactorId="+id;
        //var url = "riskIdentificationController.do?goAdd&identificationType=${identificationType}&addressId="
        addOneTab("新增风险录入",url,"default");
    }

    function addRiskNew(){
        //riskIdentificationController.do?goAdd&identificationType=2
        var url = "riskManageResultController.do?addPostRiskNew&riskManageTaskId=${taskManagePostId}";
        //var url = "riskIdentificationController.do?goAdd&identificationType=${identificationType}&addressId="
        addOneTab("新增风险录入",url,"default");
    }
    function addHd(id){
        //tBHiddenDangerExamController.do?goAdd&examType=${examType}
        var url = "riskManageResultController.do?addHdPost&riskManagePostHazardFactorId="+id;
        addOneTab("新增隐患录入",url,"default");
    }

    function addHiddenNew(){
        var url = "tBHiddenDangerExamController.do?newList&examType=yh&taskAllId=${postTaskAllId}";

        addOneTab("补充隐患录入",url,"default");
    }

    $(document).ready(function(){
        var datagrid = $("#hazardFactorsPostListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums2 div[name='searchColums2']").html());

        $("input[type=radio][name='queryHandleStatusTem2']").change(function() {
            var selectedvalue = $("input[name='queryHandleStatusTem2']:checked").val();
            $("input[name='queryHandleStatus2']").val(selectedvalue);
            hazardFactorsPostListsearch();

            if(selectedvalue=='0'){
                $("a span:contains('新增风险')").show();
                $("a span:contains('添加危害因素')").show();
                $("a span:contains('落实到位')").show();
                $("a span:contains('批量落实到位')").show();
            } else {
                $("a span:contains('新增风险')").hide();
                $("a span:contains('添加危害因素')").hide();
                $("a span:contains('落实到位')").hide();
                $("a span:contains('批量落实到位')").hide();
            }

        });
    });

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
        url += '&load=detail&id='+rowsData[0].id;
        createdetailwindow(title,url,width,height);
    }

    window.top["reload_hazardFactorsPostList_${taskManagePostId}"]=function(){
        //$("#hazardFactorsPostList").datagrid( "reload");
        //alert('reload_hazardFactorsPostList_');
        //debugger;
        //hazardFactorsPostListsearch();
    };
    window.top["tip_hazardFactorsPostList_${taskManagePostId}"]=function(){
        tip(this.msg);
    };

    function updateAll() {
        var rows = $("#hazardFactorsPostList").datagrid('getSelections');
        if (rows.length == 0) {
            tip("请选择要落实到位的数据");
        } else if (rows.length >= 1){
            var implDetail = true;
            for (var i = 0; i < rows.length; i++) {
                if(rows[i].hdCount!='0'){
                    implDetail=false;
                }
            }
            if(implDetail) {
                var idsTemp = new Array();
                for (var i = 0; i < rows.length; i++) {
                    idsTemp.push(rows[i].id);
                }
                var idt = idsTemp.join(",");
                $.dialog.confirm("已勾选" + idsTemp.length + "条记录,是否确认批量落实到位？", function () {
                    openwindow("落实到位", "riskManageResultController.do?goAllUpdatePost&ids=" + idt, "hazardFactorsPostList", 600, 450);
                });
            }else{
                tip("请选择未生成隐患的数据");
            }
        }
    }

    function update() {
        var rows = $("#hazardFactorsPostList").datagrid('getSelections');
        if (rows.length == 0) {
            tip("请选择要落实到位的数据");
        } else if (rows.length == 1){
            openwindow("落实到位", "riskManageResultController.do?goUpdatePost&id=" + rows[0].id, "hazardFactorsPostList", 600,450);
        }else if (rows.length >= 1){
            tip("请选择要一条落实到位的数据");
        }
    }


    function goImpl(id) {

            openwindow("落实到位", "riskManageResultController.do?goUpdatePost&id=" + id, "hazardFactorsPostList", 600,450);

    }
</script>