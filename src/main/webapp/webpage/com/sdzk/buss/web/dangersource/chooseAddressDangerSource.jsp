<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<script src="plug-in/Highcharts-5.0.11/code/highcharts.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-zh_CN.js"></script>
<script  type="text/javascript" src="plug-in/hashMap/HashMap.js"></script>
<script src="plug-in/hplus/js/bootstrap.min.js?v=3.3.6"></script>
<div class="easyui-layout" fit="true">
    <div region="west" style="width: 200px;" title=" "  split="true">
        <ul id="formtree">
        </ul>
    </div>
  <div region="center" style="" title="">
      <t:datagrid name="tBDangerSourceList" checkbox="true" fitColumns="false" title="风险列表" actionUrl="tBDangerSourceController.do?addressDangerSourceDatagrid&excludeId=${excludeId}" idField="id" fit="true" queryMode="group" onLoadSuccess="loadSuccess">
          <t:dgCol title="唯一编号"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
          <%--<t:dgCol title="危险源来源"  field="origin" query="false" replace=" 通用_1,年度_2,专项_3"  width="120"></t:dgCol>--%>
          <t:dgCol title="辨识时间"  field="yeRecognizeYear" query="true" formatter="yyyy"  hidden="true"   queryMode="single" defaultVal='${year}'  width="60"></t:dgCol>
          <t:dgCol title="危险源"  field="hazard.hazardName" query="false" queryMode="single"  width="60"></t:dgCol>
          <t:dgCol title="风险描述"  field="yePossiblyHazard"  query="true"  queryMode="single"  width="200"></t:dgCol>
          <t:dgCol title="专业属性"  field="yeProfession" query="true"  dictionary="proCate_gradeControl"  queryMode="single"  width="60"></t:dgCol>
          <t:dgCol title="风险类型"  field="yeHazardCate" query="true"  dictionary="hazardCate"  queryMode="single"  width="60"></t:dgCol>
          <t:dgCol title="风险等级"  field="yeRiskGrade" dictionary="riskLevel" query="true"  queryMode="single"  width="60"></t:dgCol>
          <t:dgCol title="事故类型"  field="yeAccidentTemp"    queryMode="group"  width="60"></t:dgCol>
          <t:dgCol title="作业活动" field="activity.id" dictionary="t_b_activity_manage where is_delete='0',id,activity_name" query="true" queryMode="single" width="100" align="center"></t:dgCol>
          <c:if test="${initParam.les == 'no'}">
              <%--<t:dgCol title="可能性"  field="yeProbability" dictionary="probability"   queryMode="group"  width="120"></t:dgCol>--%>
              <%--<t:dgCol title="损失"  field="yeCost"  dictionary="hazard_fxss"  queryMode="group"  width="120"></t:dgCol>--%>
              <t:dgCol title="风险值"  field="riskValue"    queryMode="group"  width="120"></t:dgCol>
          </c:if>
          <c:if test="${initParam.les == 'yes'}">
              <%--<t:dgCol title="风险可能性"  field="lecRiskPossibility" dictionary="lec_risk_probability" queryMode="group"  width="120"></t:dgCol>--%>
              <%--<t:dgCol title="风险损失"  field="lecRiskLoss"   dictionary="lec_risk_loss" queryMode="group"  width="120"></t:dgCol>--%>
              <%--<t:dgCol title="暴露在风险中的频率"  field="lecExposure"   dictionary="lec_exposure" queryMode="group"  width="120"></t:dgCol>--%>
              <t:dgCol title="风险值"  field="lecRiskValue"    queryMode="group"  width="45"></t:dgCol>
          </c:if>
          <%--<t:dgCol title="风险管控措施"  field="manageMeasure"    queryMode="group"  width="120"></t:dgCol>--%>

          <t:dgCol title="隐患描述"  field="yeMhazardDesc"  queryMode="single" query="true"  width="200"></t:dgCol>
          <t:dgCol title="管控标准来源"  field="docSource"  query="true"  queryMode="single"  width="200"></t:dgCol>
          <t:dgCol title="标准内容"  field="yeStandard"  query="true"  queryMode="single"  width="200"></t:dgCol>
          <t:dgToolBar title="查看" icon="icon-search" url="tBDangerSourceController.do?goDetail" funname="detail" width="850" height="450"></t:dgToolBar>
          <t:dgToolBar title="全部关联" icon="icon-add" url="tBDangerSourceController.do?relAll&addressId=${excludeId}" funname="relAll" width="850" height="450"></t:dgToolBar>
          <%--<t:dgToolBar title="取消全选" icon="icon-undo"  funname="uodoChooseAll" width="850" height="450"></t:dgToolBar>--%>
          <t:dgToolBar title="添加关联" icon="icon-add" url="tBDangerSourceController.do?saveAddressChooseDanger" funname="addRelFunction" width="850" height="450"></t:dgToolBar>
      </t:datagrid>
  </div>
 </div>

<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
<script type="text/javascript">
    var isSelectedAll = false;
    var sAddressCate = "";
    var isFirstLoad = true;

    /**组合一类危险源树**/
    function combineTree(sAddressCate){
        var Gparams;
        $('#formtree').empty();
        $('#formtree').tree({
            animate : true,
            url : 'tBAddressInfoController.do?dangerSourceTree&addressCate='+sAddressCate,
            checkbox: true,
            onClick : function(node) {
                $(node.target).find(".tree-checkbox").trigger("click");
            },onCheck: function(){
                var nodes = $('#formtree').tree('getChecked');

                var ids='';
                var param=$('#tBDangerSourceList').datagrid('options').queryParams;
                for(var i=0; i<nodes.length;i++){
                    if ($('#formtree').tree('isLeaf', nodes[i].target)) {
                        if(ids!=''){
                            ids+=","
                        }
                        ids+=nodes[i].id;
                    }
                    if(i==(nodes.length-1)){
                        param["ids"]=ids;
                        Gparams = param;
                    }
                }
                param["ids"]=ids;
                if(!isFirstLoad){
                    $('#tBDangerSourceList').datagrid('reload',param);
                }
            },
            onLoadSuccess: function(){
                if(isFirstLoad){
                    $('#tBDangerSourceList').datagrid('reload',Gparams);
                }
                isFirstLoad = false;
            }
        });
    }

    /**一类危险源打钩勾**/
    function treeSelected(){
        $("#formtree").find("span[class='tree-checkbox tree-checkbox0']").addClass("tree-checkbox1").removeClass("tree-checkbox0");
    }

    function loadSuccess(){
        var yeRecognizeYear =  $("input[name='yeRecognizeYear']").val();
        if (yeRecognizeYear==null||yeRecognizeYear==''){
            $("input[name='yeRecognizeYear']").val("${year}")
        }
//         if(isSelectedAll == true){
//             $("input[name='ck']").attr("checked",true);
//         }
    }

    $(document).ready(function(){
        combineTree(sAddressCate);

        $("#tBDangerSourceListForm").find("input[name='yeRecognizeYear']").attr("class", "Wdate").click(function () {
            WdatePicker({
                dateFmt: 'yyyy'
            });
        });
        $("span:contains('全选')").parents("a.l-btn").show();
        $("span:contains('取消全选')").parents("a.l-btn").show();

        var addressCateMap = new HashMap();
        var i=0;
        <c:forEach items="${addressCateList}" var="addressCateList">
        i++;
        addressCateMap.put('typename'+i,'${addressCateList.typename}');
        addressCateMap.put('typecode'+i,'${addressCateList.typecode}');
        </c:forEach>

        $(".layout-panel-west .panel-title").append("<select id='addressCate' style='height: 30px;margin:0;padding:0; '>" +
            "<option value='' style='margin: 0;padding:0; '>" + "---请选择---" + "</option>" +
            "</select>");
        drawAddressCateSelect(addressCateMap,i);

        $("#addressCate").change(function(){
            var t = $("#addressCateTemp");
            if(t){
                t.remove();
            }
            $("#tBDangerSourceListForm").append("<input type='hidden' id='addressCateTemp' name='addressCate' value='' />");
            $("#addressCateTemp").val($("#addressCate").val());
            sAddressCate = $("#addressCate").val();

            var param = {"addressCate":sAddressCate};
            $('#tBDangerSourceList').datagrid('reload',param);
            isFirstLoad = true;
            combineTree(sAddressCate);
        });
    });

    //画一类危险源树上面的单选下拉框
    function drawAddressCateSelect(addressCateMap,max){
        for(var i=1;i<max+1;i++){
            $("#addressCate").append("<option value='"+ addressCateMap.get('typecode'+i)+ "'>"+ addressCateMap.get('typename'+i) +"</option>");
        }
    }

    var isSaveAll = "0";
    /**全部关联**/
    function relAll(){
        $.dialog.setting.zIndex = getzIndex(true);
        $.dialog.confirm('确定关联全部危险源吗?', function(r) {

            var nodes = $('#formtree').tree('getChecked');

            var treeIds='';
            var param=$('#tBDangerSourceList').datagrid('options').queryParams;
            for(var i=0; i<nodes.length;i++){
                if ($('#formtree').tree('isLeaf', nodes[i].target)) {
                    if(treeIds!=''){
                        treeIds+=","
                    }
                    treeIds+=nodes[i].id;
                }
            }

            if (r) {
                $.ajax({
                    url : "tBDangerSourceController.do?relAll&addressId=${excludeId}",
                    type : 'post',
                    data : {
                        "yeRecognizeYear":$("input[name='yeRecognizeYear']").val(),
                        "yeProfession":$("select[name='yeProfession']").val(),
                        "yeRiskGrade":$("select[name='yeRiskGrade']").val(),
                        "yeMhazardDesc":$("input[name='yeMhazardDesc']").val(),
                        "yePossiblyHazard":$("input[name='yePossiblyHazard']").val(),
                        "addressCate":sAddressCate,
                        "ids":treeIds
                    },
                    cache : false,
                    success : function() {
                        tip("全部关联成功");
                        window.top.reload_addressDangerSourceList.call();
                        $('#tBDangerSourceList').datagrid('reload');
                    }
                });
            }
        });
    }
</script>
<script>
    function addRelFunction (){
        var ids = [];
        var rows = $('#tBDangerSourceList').datagrid('getSelections');
        var allrows = $('#tBDangerSourceList').datagrid('getRows');
        if((rows.length<=0 && isSelectedAll==false) || (allrows.length<=0 && isSelectedAll==true)){
            tip("请选择要关联的风险");
        }else{
            if(isSelectedAll==false){
                for (var i = 0; i < rows.length; i++) {
                    ids.push(rows[i].id);
                }
                ids.join(',');
            }else{
                rows = allrows;
                for (var i = 0; i < rows.length; i++) {
                    ids.push(rows[i].id);
                }
                ids.join(',');
            }

            $.ajax({
                url: "tBDangerSourceController.do?saveAddressChooseDanger&ids="+ids+"&addressId=${excludeId}",
                type: 'POST',
                error: function(){
                },
                success: function(data){
                    isSelectedAll = false;
                    data = $.parseJSON(data);
                    tip(data.msg);
                    window.top.reload_addressDangerSourceList.call();
                    $('#tBDangerSourceList').datagrid('reload');
                }
            });
        }
    }
    function chooseAll(){
        isSelectedAll = true;
//        $("span:contains('全选')").parents("a.l-btn").hide();
//        $("span:contains('全不选')").parents("a.l-btn").show();
        $("input[name='ck']").attr("checked",true);

        $.dialog.confirm('你确定关联全部风险吗?', function(r) {
            this.close();
            if (r) {
                $.ajax({
                    url : url,
                    type : 'post',
                    cache : false,
                    data : {
                        ids : ids.join(','),
                        type : type
                    },
                    beforeSend: function(data){
                        $.messager.progress({
                            text : "正在上报数据......",
                            interval : 100
                        });
                    },
                    success : function(data) {
                        var d = $.parseJSON(data);
                        if (d.success) {
                            tBHiddenDangerHandleListsearch();
                        }
                        var msg = d.msg;
                        tip(msg);
                    },
                    complete: function(date){
                        $.messager.progress('close');
                    },
                    error:function(data){
                        tip("操作失败");//should not reach here
                    }
                });
            }
        });
    }
    function uodoChooseAll(){
        isSelectedAll = false;
        $("input[name='ck']").attr("checked",false);
//        $("span:contains('全选')").parents("a.l-btn").show();
//        $("span:contains('全不选')").parents("a.l-btn").hide();
    }
    function searchReset() {
        document.getElementById("tBDangerSourceListForm").reset();
        var queryParams = $('#tBDangerSourceList').datagrid('options').queryParams;
        $('#tBDangerSourceListtb').find('*').each(function() {
            queryParams[$(this).attr('name')] = $(this).val();
        });
        $('#tBDangerSourceList').datagrid({
            url: 'tBDangerSourceController.do?addressDangerSourceDatagrid&excludeId=${excludeId}&field=id,id_begin,id_end,yeRecognizeYear,hazard.hazardName,yeProfession,yeHazardCate,yeRiskGrade,yeAccidentTemp,yeAccidentTemp_begin,yeAccidentTemp_end,lecRiskValue,lecRiskValue_begin,lecRiskValue_end,yeMhazardDesc,yePossiblyHazard,yePossiblyHazard_begin,yePossiblyHazard_end,yeStandard,yeStandard_begin,yeStandard_end,',
            pageNumber: 1
        });
    }
    //
    //    window.onload = function(){
    //        treeSelected();
    //    };
</script>