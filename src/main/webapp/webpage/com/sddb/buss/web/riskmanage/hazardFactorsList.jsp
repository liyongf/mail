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
        <t:datagrid name="hazardFactorsList" checkbox="true" pagination="true" fitColumns="true" title="" actionUrl="riskManageResultController.do?hazardFactorDatagrid&riskManageTaskEntity.id=${taskManageId}" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="危害因素ID"  field="hazardFactor.id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险类型"  field="hazardFactor.riskType" dictionary="risk_type" queryMode="single"  width="100" query="true"></t:dgCol>
            <t:dgCol title="专业"  field="hazardFactor.major" query="true" dictionary="major" queryMode="single"  width="100"></t:dgCol>
            <t:dgCol title="危害因素"  field="hazardFactor.hazardFactors"  formatterjs="valueTitle"  queryMode="group"  width="200"></t:dgCol>
            <t:dgCol title="危害因素等级"  field="hfLevel" dictionary="factors_level"  queryMode="group" sortable="false"  width="100" ></t:dgCol>
            <t:dgCol title="管控措施"  field="hfManageMeasure" formatterjs="valueTitle"  queryMode="group" sortable="false"  width="200" ></t:dgCol>
            <t:dgCol title="落实情况"  field="implDetail"  queryMode="group" formatterjs="valueTitle"  width="110" ></t:dgCol>
            <t:dgCol title="落实结论"  field="handleStatus" hidden="true" queryMode="group"  width="175" ></t:dgCol>
            <%--<t:dgCol title="备注"  field="remark" queryMode="group"  width="100" ></t:dgCol>--%>
            <t:dgCol title="风险数"  field="riskCount"  queryMode="group"  width="60" formatterjs="showRiskList" hidden="true" ></t:dgCol>
            <t:dgCol title="隐患数"  field="hdCount"  queryMode="group"  width="60" formatterjs="showHdList"   ></t:dgCol>

            <t:dgToolBar title="查看" icon="icon-search" url="riskManageResultController.do?goUpdate" funname="detail" height="600" ></t:dgToolBar>
            <c:if test="${load =='add'}">
                <t:dgCol title="操作" field="opt" width="140" align="center"></t:dgCol>
                <%--<t:dgFunOpt funname="addRisk(id)" title="新增风险"  urlclass="ace_button" exp="handleStatus#ne#1" ></t:dgFunOpt>--%>
                <t:dgFunOpt funname="goImpl(id)" title="落实到位"  urlclass="ace_button" exp="handleStatus#ne#1&&hdCount#eq#0" ></t:dgFunOpt>
                <t:dgFunOpt funname="addHd(id)" title="未落实到位"  urlclass="ace_button" exp="handleStatus#ne#1" ></t:dgFunOpt>
                <t:dgToolBar title="添加危害因素" icon="icon-add" url="hazardFactorsController.do?goAdd&taskManageId=${taskManageId}" funname="add" width="600" height="500"></t:dgToolBar>
                <%--<t:dgToolBar title="落实到位" icon="icon-edit" url="riskManageResultController.do?goUpdate" funname="update" height="600"></t:dgToolBar>--%>
                <t:dgToolBar title="批量落实到位" icon="icon-edit" url="riskManageResultController.do?goAllUpdate" funname="updateAll" height="600"></t:dgToolBar>
                <t:dgToolBar title="新增风险" icon="icon-add"  funname="addRiskNew"></t:dgToolBar>
                <t:dgToolBar title="补充隐患录入" icon="icon-add"  funname="addHiddenNew"></t:dgToolBar>

            </c:if>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript">

    function showRiskList(value,rec,index){
        var url = "riskManageResultController.do?showriskList&riskManageHazardFactorId="+rec.id;
        //return "<a href='#' onclick='createdetailwindow(\"风险列表\",\"" + url + "\",800,600);'>"+value+"</a>";
        return "<a href='#' onclick='addOneTab(\"已录风险列表\",\"" + url + "\",\"default\");'>"+value+"</a>";
    }
    function showHdList(value,rec,index){
        var url = "riskManageResultController.do?showHdList&riskManageHazardFactorId="+rec.id;
        //return "<a href='#' onclick='createdetailwindow(\"隐患列表\",\"" + url + "\",800,600);'>"+value+"</a>";
        return "<a href='#' onclick='addOneTab(\"已录隐患列表\",\"" + url + "\",\"default\");'>"+value+"</a>";
    }

    function addRisk(id){
        //riskIdentificationController.do?goAdd&identificationType=2
        var url = "riskManageResultController.do?addRisk&riskManageHazardFactorId="+id;
        //var url = "riskIdentificationController.do?goAdd&identificationType=${identificationType}&addressId="
        addOneTab("新增风险录入",url,"default");
    }
    function addRiskNew(){
        //riskIdentificationController.do?goAdd&identificationType=2
        var url = "riskManageResultController.do?addRiskNew&riskManageTaskId=${taskManageId}";
        //var url = "riskIdentificationController.do?goAdd&identificationType=${identificationType}&addressId="
        addOneTab("新增风险录入",url,"default");
    }

    function addHiddenNew(){
        $.ajax({
            url : "riskManageResultController.do?isAddHidden&taskAllId=${taskAllId}",
            type : 'post',
            cache : false,
            async: true,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d=="1") {
                    var url = "tBHiddenDangerExamController.do?newList&examType=yh&taskAllId=${taskAllId}";

                    addOneTab("补充隐患录入",url,"default");
                }else {
                    tip("超出隐患录入时间");
                }
            }
        });

    }


    function addHd(id){
        //tBHiddenDangerExamController.do?goAdd&examType=${examType}
        var url = "riskManageResultController.do?addHd&riskManageHazardFactorId="+id;
        addOneTab("新增隐患录入",url,"default");
    }

    $(document).ready(function(){
        var datagrid = $("#hazardFactorsListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums2 div[name='searchColums2']").html());

        $("input[type=radio][name='queryHandleStatusTem2']").change(function() {
            var selectedvalue = $("input[name='queryHandleStatusTem2']:checked").val();
            $("input[name='queryHandleStatus2']").val(selectedvalue);
            hazardFactorsListsearch();

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

    window.top["reload_hazardFactorsList_${taskManageId}"]=function(){
        //$("#hazardFactorsList").datagrid( "reload");
        //alert('reload_hazardFactorsList_');
        //debugger;
        //hazardFactorsListsearch();
    };
    window.top["tip_hazardFactorsList_${taskManageId}"]=function(){
        tip(this.msg);
    };

    function updateAll() {
        var rows = $("#hazardFactorsList").datagrid('getSelections');
        if (rows.length == 0) {
            tip("请选择要落实到位的数据");
        } else if (rows.length >= 1){
            var implDetail = true;
            for (var i = 0; i < rows.length; i++) {
                if(rows[i].hdCount!='0'){
                    implDetail=false;
                }
            }
            if(implDetail){
                var idsTemp = new Array();
                for (var i = 0; i < rows.length; i++) {
                    idsTemp.push(rows[i].id);
                }
                var idt = idsTemp.join(",");
                $.dialog.confirm("已勾选"+idsTemp.length+"条记录,是否确认批量落实到位？", function () {
                    openwindow("落实到位", "riskManageResultController.do?goAllUpdate&ids=" + idt, "hazardFactorsList", 600,450);
                });
            }else{
                tip("请选择未生成隐患的数据");
            }

        }
    }

    function update() {
        var rows = $("#hazardFactorsList").datagrid('getSelections');
        if (rows.length == 0) {
            tip("请选择要落实到位的数据");
        } else if (rows.length == 1){
            openwindow("落实到位", "riskManageResultController.do?goUpdate&id=" + rows[0].id, "hazardFactorsList", 600,450);
        }else if (rows.length >= 1){
            tip("请选择要一条落实到位的数据");
        }
    }


    function goImpl(id) {

            openwindow("落实到位", "riskManageResultController.do?goUpdate&id=" + id, "hazardFactorsList", 600,450);

    }


</script>