<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>
<script src="plug-in/Highcharts-5.0.11/code/highcharts.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-zh_CN.js"></script>
<script  type="text/javascript" src="plug-in/hashMap/HashMap.js"></script>
<script src="plug-in/hplus/js/bootstrap.min.js?v=3.3.6"></script>
<div class="easyui-layout" fit="true">
    <%--<div region="west" style="width: 200px;" title=" "  split="true">--%>
        <%--<ul id="formtree">--%>
        <%--</ul>--%>
    <%--</div>--%>
  <div region="center" style="" title="">
      <t:datagrid name="tBDangerSourceList" checkbox="true" fitColumns="false" title="三违列表" actionUrl="tBFineController.do?fineVioFineDatagrid&hiddenId=${hiddenId}" idField="id" fit="true" queryMode="group" onLoadSuccess="loadSuccess">
          <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="违章时间"  field="vioDate" formatter="yyyy-MM-dd" query="true"  queryMode="group" sortable="false" width="80" align="center"></t:dgCol>
          <t:dgCol title="违章时间"  field="queryMonth" formatter="yyyy-MM" hidden="true"   queryMode="single" sortable="false" width="80" align="center"></t:dgCol>
          <t:dgCol title="班次"  field="shift" hidden="false" dictionary="workShift" query="true"  queryMode="single" sortable="false" width="60" align="center"></t:dgCol>
          <t:dgCol title="查处单位"  field="findUnits"  dictionary="t_s_depart,id,departname,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="违章地点"  field="vioAddress"  dictionary="t_b_address_info,id,address,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="违章单位"  field="vioUnits"  dictionary="t_s_depart,id,departname,where 1=1"  queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="违章人员"  field="vioPeople"   queryMode="group"  width="120" align="center"></t:dgCol>
          <t:dgCol title="违章分类"  field="vioCategory" dictionary="violaterule_wzfl" query="true"  queryMode="single" sortable="false" width="100" align="center"></t:dgCol>
          <%--<t:dgCol title="违章定性"  field="vioQualitative" dictionary="violaterule_wzdx" query="true"  queryMode="single" sortable="false" width="100" align="center"></t:dgCol>--%>
          <t:dgCol title="三违级别"  field="vioLevel" dictionary="vio_level" query="true"  queryMode="single" sortable="false" width="100" align="center"></t:dgCol>
          <t:dgCol title="制止人"  field="stopPeople"  queryMode="group"  width="120" align="center"></t:dgCol>

          <t:dgCol title="三违事实描述"  field="vioFactDesc"  formatterjs="valueTitle"  queryMode="group"  width="120"></t:dgCol>
          <t:dgCol title="备注"  field="remark"  formatterjs="valueTitle"  queryMode="group"  width="120" align="center"></t:dgCol>
          <%--<t:dgToolBar title="全部关联" icon="icon-add" url="tBDangerSourceController.do?relAll&addressId=${hiddenId}" funname="relAll" width="850" height="450"></t:dgToolBar>--%>
          <%--<t:dgToolBar title="取消全选" icon="icon-undo"  funname="uodoChooseAll" width="850" height="450"></t:dgToolBar>--%>
          <t:dgToolBar title="添加关联" icon="icon-add" url="tBDangerSourceController.do?saveAddressChooseDanger" funname="addRelFunction" width="850" height="450"></t:dgToolBar>
      </t:datagrid>
  </div>
 </div>

<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
<script type="text/javascript">

    function valueTitle(value){
        return "<a title=\""+value+"\">"+value+"</a>";
    }

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
     //   combineTree(sAddressCate);

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
        $.dialog.confirm('确定关联吗?', function(r) {

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
                        tip("关联成功");
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
            tip("请选择要关联的罰款");
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
                url: "tBFineController.do?saveRelChooseVioFine&ids="+ids+"&hiddenId=${hiddenId}",
                type: 'POST',
                error: function(){
                },
                success: function(data){
                    isSelectedAll = false;
                    data = $.parseJSON(data);
                    tip(data.msg);
                    $('#tBDangerSourceList').datagrid('reload');
                    window.top.reload_tBDangerSourceList.call();
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