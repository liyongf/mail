<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<script  type="text/javascript" src="plug-in/hashMap/HashMap.js"></script>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<div class="easyui-layout" fit="true">
	<div region="south" split="true" title="" style="height: 180px;">
		<t:formvalid formid="thisForm" dialog="true" usePlugin="password" layout="table" action="hazardFactorsController.do?doAdd" tiptype="3">
			<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>最高管控层级:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="manageLevel" type="list" datatype="*" id="manageLevel"
									  typeGroupCode="identifi_mange_level" defaultVal="${riskIdentificationEntity.manageLevel}" hasLabel="false"  title="信息来源"></t:dictSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">最高管控层级</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>最高管控责任人:
						</label>
					</td>
					<td class="value">
						<div id="vioPeopleSelect" style="width: 130px;height: 15px"></div>
						<input id="dutyManager" name="dutyManager" type="hidden" datatype="*">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">最高管控责任人</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>风险等级:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="riskLevel" type="list" datatype="*" id="riskLevel"
									  typeGroupCode="factors_level" defaultVal="${riskIdentificationEntity.riskLevel}" hasLabel="false"  title="风险类型"></t:dictSelect>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">风险等级</label>
					</td>
					
				<c:if test="${load ne 'detail'}">
						<td align="right" rowspan="2">
							<div class="ui_buttons" style="text-align: left;">
								<input type="button" style="height: 100px;width: 100%" id="btn" value="保存" class="ui_state_highlight" onclick="doModifyInfo();">
							</div>
						</td>
				</c:if>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>评估日期:
						</label>
					</td>
					<td class="value">
						<input id="identifiDate" name="identifiDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="*" value='<fmt:formatDate value='${ideDate}' type="date" pattern="yyyy-MM-dd"/>'/>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">评估日期</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>解除日期:
						</label>
					</td>
					<td class="value">
						<input id="expDate" name="expDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker()" datatype="*" value='<fmt:formatDate value='${riskIdentificationEntity.expDate}' type="date" pattern="yyyy-MM-dd"/>'/>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">解除日期</label>
					</td>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>风险描述:
						</label>
					</td>
					<td class="value">
						<textarea  id="riskDesc" name="riskDesc" cols="6" style="width: 300px" datatype="*">${riskIdentificationEntity.riskDesc}</textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">风险描述</label>
					</td>
				</tr>
			</table>
		</t:formvalid>
	</div>
	<%--<div region="west" style="width: 200px;" title=" "  split="true">--%>
		<%--<ul id="formtree">--%>
		<%--</ul>--%>
		<%--</ul>--%>
	<%--</div>--%>
	<div region="center"  title="">
		<t:datagrid name="tBDangerSourceList" checkbox="true" fitColumns="true" title="危害因素列表" actionUrl="riskIdentificationController.do?majorFactList&riskId=${riskId}" autoLoadData="false" onLoadSuccess="dataGridLoadSuccess" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
			<t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
			<t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" defaultVal="${riskTypeTemp}" queryMode="single" query="true" width="80"></t:dgCol>
			<t:dgCol title="专业"  field="majors" dictionary="major" queryMode="single" query="true" hidden='true' width="80"></t:dgCol>
			<t:dgCol title="岗位"  field="postName"  queryMode="single" width="80"></t:dgCol>
			<t:dgCol title="危害因素"  field="hazardFactors"   queryMode="group" formatterjs="valueTitle" width="120"></t:dgCol>
			<t:dgCol title="管控措施"  field="manageMeasureTemp"  queryMode="group" sortable="false"  formatterjs="valueTitle" width="140" extendParams="editor: { type: 'textarea',options: { valueField:'manageMeasureTempNew',  required: true}}"></t:dgCol>
			<t:dgCol title="危害因素等级"  field="riskLevelTemp" sortable="false" query="true"  dictionary="factors_level" queryMode="single"  width="80" extendParams="editor: { type: 'combobox',options: { valueField:'typecode',textField:'typename',data: [${dic}],  required: true}}"></t:dgCol>
			<t:dgCol title="状态"  field="statusTemp" hidden="true" queryMode="group"  width="120" ></t:dgCol>
			<c:if test="${load ne 'detail'}">
				<t:dgToolBar title="添加关联" icon="icon-add" url="tBDangerSourceController.do?saveAddressChooseDanger" funname="addRelFunction" width="850" height="450"></t:dgToolBar>

				<t:dgToolBar  title="编辑" icon="icon-edit"  funname="editRow"></t:dgToolBar>
				<t:dgToolBar  title="取消编辑" icon="icon-undo" funname="reject"></t:dgToolBar>
				<t:dgToolBar  title="保存并关联" icon="icon-save" url="riskIdentificationController.do?saveModifyLevel" funname="saveData"></t:dgToolBar>

				<t:dgToolBar title="取消关联"  icon="icon-remove" url="hazardFactorsController.do?doBatchDelRel&riskId=${riskId}" funname="deleteSelect" ></t:dgToolBar>
				<t:dgToolBar title="补充危害因素" icon="icon-add" url="hazardFactorsController.do?goAddRisk&riskId=${riskId}" funname="goAdd" width="600" height="600"></t:dgToolBar>

			</c:if>
		</t:datagrid>
	</div>
</div>
<div id="tempSearchColums" style="display: none">
	<div name="modularSearchColums" >
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="风险点">模块：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 160px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                 <div id="modularSelect" style="width: 130px;height: 15px"></div>
                 <input id="modular" type="hidden" name="modular">
             </span>
	</div>
	<div name="searchColums" >
		<span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="岗位">岗位：</span>
		<span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                 <div id="magicsuggestPostName" style="width: 130px;height: 15px"></div>
                 <input id="postName" type="hidden" name="postName">
             </span>
		<span style="display:-moz-inline-box;display:inline-block;margin-top: 10px">
			关键词检索:<input id="keyword" name="keyword" type="text">
        </span>
		<span style="display:-moz-inline-box;display:inline-block;margin-top: 10px">
        <input id="relStatus" name="relStatus" value="0" type="hidden">
           <label>
            <input id="queryRelStatus" name="queryRelStatus" type="checkbox">已关联
           </label>
        </span>
	</div>
</div>
<link rel="stylesheet" type="text/css" href="plug-in/lhgDialog/skins/default.css">
<script type="text/javascript">

    window.top["reload_tBDangerSourceList"]=function(){
//        tBDangerSourceListsearch();
        $('#tBDangerSourceList').datagrid('reload');
        reloadLevel()
    };

    var magicsuggestPostNameSelSelected = "";
    function goAdd (title,addurl,gname,width,height){
        openwindow(title,addurl,gname,width,height);
    }
    $(document).ready(function(){
        var riskType = "${riskType}";
        var datagrid = $("#tBDangerSourceListtb");
        if(riskType != "no"){
            /*datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='modularSearchColums']").html());*/
            $("#tBDangerSourceListForm").append($("#tempSearchColums div[name='modularSearchColums']").html());
            $("#tempSearchColums div[name='modularSearchColums']").remove();

            /*modular查询条件*/
            var magicsuggestModularSelect = $('#modularSelect').magicSuggest({
                data:'hazardFactorsController.do?getMyModularList&riskType=${riskType}',
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
//                var roots = $('#formtree').tree('getRoots');//返回tree的所有根节点数组
//				for ( var i = 0; i < roots.length; i++) {
//					var node = $('#formtree').tree('find', roots[i].id);//查找节点
//					$('#formtree').tree('check', node.target);//将得到的节点选中
//				}
            });
		}else{
            $("#tempSearchColums div[name='modularSearchColums']").remove();
		}
        $("#tBDangerSourceListForm").append($("#tempSearchColums div[name='searchColums']").html());
        $("#tempSearchColums div[name='searchColums']").remove();
        magicsuggestPostNameSelSelected = $('#magicsuggestPostName').magicSuggest({
            data: 'magicSelectController.do?getPostName',
            allowFreeEntries: true,
            valueField: 'post_name',
            placeholder: '输入或选择',
            maxSelection: 1,
            selectFirst: true,
            highlight: false,
            displayField: 'post_name'
        });
        $(magicsuggestPostNameSelSelected).on('selectionchange', function (e, m) {
            $("#postName").val(magicsuggestPostNameSelSelected.getValue());
        });

        setTimeout(function() {
            $("span:contains('取消关联')").parents("a.l-btn").hide();
            $("span:contains('取消编辑')").parents("a.l-btn").hide();
            $("span:contains('保存并关联')").parents("a.l-btn").hide();
        }, 250);

        tBDangerSourceListsearch();

    });

    $("input[name='queryRelStatus']").change(function() {
        var selectedvalue = "";
        if ($("input[name='queryRelStatus']").attr("checked")) {
            selectedvalue="1";
        }else{
            selectedvalue="0";
		}
        $("input[name='relStatus']").val(selectedvalue);
        if(selectedvalue=="0"){
            $("span:contains('补充危害因素')").parents("a.l-btn").show();
            $("span:contains('添加关联')").parents("a.l-btn").show();
            $("span:contains('编辑')").parents("a.l-btn").show();
            $("span:contains('取消关联')").parents("a.l-btn").hide();
        }else if(selectedvalue=="1"){
            $("span:contains('补充危害因素')").parents("a.l-btn").hide();
            $("span:contains('添加关联')").parents("a.l-btn").hide();
            $("span:contains('编辑')").parents("a.l-btn").hide();
            $("span:contains('取消关联')").parents("a.l-btn").show();
        }
//        var roots = $('#formtree').tree('getRoots');//返回tree的所有根节点数组
//        for ( var i = 0; i < roots.length; i++) {
//            var node = $('#formtree').tree('find', roots[i].id);//查找节点
//            $('#formtree').tree('check', node.target);//将得到的节点选中
//        }
        tBDangerSourceListsearch();
    });



    function deleteSelect(title,url,gname) {
        gridname=gname;
        var ids = [];
        var rows = $("#"+gname).datagrid('getSelections');
        if (rows.length > 0) {
            $.dialog.setting.zIndex = getzIndex(true);
            $.dialog.confirm('你确定取消关联该数据吗?', function(r) {
                if (r) {
                    for ( var i = 0; i < rows.length; i++) {
                        ids.push(rows[i].id);
                    }
                    $.ajax({
                        url : url,
                        type : 'post',
                        data : {
                            ids : ids.join(',')
                        },
                        cache : false,
                        success : function(data) {
                            var d = $.parseJSON(data);
                            if (d.success) {
                                var msg = d.msg;
                                tip(msg);
                                reloadTable();
                                $("#"+gname).datagrid('unselectAll');
                                ids='';
                                window.top.reload_hazardFactorsList.call();
                            }
                        }
                    });
                }
            });
        } else {
            tip("请选择需要取消关联的数据");
        }
    }

    function goHazardReview(){
        //选中
        var rows = $("#tBDangerSourceList").datagrid('getSelections');
        if(rows== null || rows.length < 1){
            tip("请选择一条要提交审批的数据!");
        }else{
            var ids = new Array();
            for(var i =0 ;i<rows.length ;i++){
                ids.push(rows[i].id);
            }
            var idt = ids.join(",");
            $.dialog.confirm("已勾选"+ids.length+"条记录,是否确认提交审批？", function () {
                openwindow("提交审批", "hazardFactorsController.do?goReview&ids=" + idt, "hazardFactorsList", 600, 450);
            });
            /*$.ajax({
                url : "hazardFactorsController.do?goReview",
                type : 'post',
                cache : false,
                data : {
                    ids : ids.join(',')
                },
                beforeSend: function(data){
                    $.messager.progress({
                        text : "正在提交数据......",
                        interval : 100
                    });
                },
                success : function(data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        reloadTable();
                        $("#hazardFactorsList").datagrid('unselectAll');
                        ids='';
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
            });*/
        }
    }

    function dataGridLoadSuccess(){
        $("td[field='statusTemp']:gt(0)").each(function(){
            var status = $(this).children().text();
            if(status  != '3'){
                $(this).parent().css("background-color","#FFD2D2");
            }
        });
    }

	function doModifyInfo(){
        var setting = {
            tiptype:3
        };
        var  demo=$("#thisForm").Validform(setting);

        if(demo.check()){
			var id = "${riskId}";
			var manageLevel = $("#manageLevel").val();
			var dutyManager = $("#dutyManager").val();
			var riskLevel = $("#riskLevel").val();
			var identifiDate = $("#identifiDate").val();
			var expDate = $("#expDate").val();
			var riskDesc = $("#riskDesc").val();

			$.ajax({
				url : "riskIdentificationController.do?updateRiskTemp",
				type : 'post',
				cache : false,
				data : {
					id:id,
					manageLevel:manageLevel,
					dutyManager:dutyManager,
					riskLevel:riskLevel,
					identifiDate:identifiDate,
					expDate:expDate,
					riskDesc:riskDesc
				},
				success : function(data) {
					var d = $.parseJSON(data);
					if (d.success) {
						hazardFactorsListsearch();
                        $('#tBDangerSourceList').datagrid('reload');
                        $('.layout-button-right').trigger('click');
					}
					tip(d.msg);
				}
			});
		}
	}
    var isSelectedAll = false;
//    var sAddressCate = "";
    var isFirstLoad = true;

    /**组合一类危险源树**/
//    function combineTree(sAddressCate){
//        var Gparams;
//        $('#formtree').empty();
//        $('#formtree').tree({
//            animate : true,
//            url : 'riskIdentificationController.do?majorTree',
//            checkbox: true,
//            onClick : function(node) {
//                $(node.target).find(".tree-checkbox").trigger("click");
//            },onCheck: function(){
//                var nodes = $('#formtree').tree('getChecked');
//
//                var ids='';
//                var param=$('#tBDangerSourceList').datagrid('options').queryParams;
//                for(var i=0; i<nodes.length;i++){
//                    if ($('#formtree').tree('isLeaf', nodes[i].target)) {
//                        if(ids!=''){
//                            ids+=","
//                        }
//                        ids+=nodes[i].id;
//                    }
//                    if(i==(nodes.length-1)){
//                        param["majors"]=ids;
//                        Gparams = param;
//                    }
//                }
//                param["majors"]=ids;
//                var relStatus = $("#relStatus").val();
//                if(relStatus!=undefined&&relStatus!=null&&relStatus!="") {
//                    param["relStatus"]=relStatus;
//				}
//                var modular = $("#modular").val();
//                if(modular!=undefined&&modular!=null&&modular!="") {
//                    param["modular"]=modular;
//                }
//                if(!isFirstLoad){
//                    $('#tBDangerSourceList').datagrid('reload',param);
//                }
//            },
//            onLoadSuccess: function(){
//                if(isFirstLoad){
//                    $('#tBDangerSourceList').datagrid('reload',Gparams);
//                }
//                isFirstLoad = false;
//            }
//        });
//    }

    $(document).ready(function(){
        $("span:contains('取消关联')").parents("a.l-btn").hide();
        $("a[iconcls='icon-reload']").hide();
        var vioPeopleSelect = getUserMagicSuggestAllowFreeEntries($('#vioPeopleSelect'), $("#dutyManager"));
//        combineTree(sAddressCate);
        var load = "${load}";
        if("detail" == load){
            $("form input,textarea,select").attr("disabled","true");
            vioPeopleSelect.disable();
		}
		var dutyManager = "${riskIdentificationEntity.dutyManager}";
		if(dutyManager != "null" && dutyManager != ""){
        	vioPeopleSelect.setValue(['${riskIdentificationEntity.dutyManager}']);
		}

        // $("#identifiManSelect").find("input[id='magicsuggestInputId']").trigger("click");
        $("#identifiManSelect").find("input[id='magicsuggestInputId']").css("width","80%");
    });

</script>
<script>
    function addRelFunction (){
        var ids = [];
        var rows = $('#tBDangerSourceList').datagrid('getSelections');
        var allrows = $('#tBDangerSourceList').datagrid('getRows');
        if((rows.length<=0 && isSelectedAll==false) || (allrows.length<=0 && isSelectedAll==true)){
            tip("请选择要关联的危害因素");
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
                url: "riskIdentificationController.do?saveRekFactors&ids="+ids+"&riskId=${riskId}",
                type: 'POST',
                error: function(){
                },
                success: function(data){
                    isSelectedAll = false;
                    data = $.parseJSON(data);
                    tip(data.msg);
                    hazardFactorsListsearch();
                    $('#tBDangerSourceList').datagrid('reload');
                    reloadLevel()
                }
            });
        }
    }
    function reloadLevel(){
        $.ajax({
            url: "riskIdentificationController.do?getRiskLevel&riskId=${riskId}",
            type: 'POST',
            error: function(){
            },
            success: function(data){
                data = $.parseJSON(data);
                $("#riskLevel").val(data.obj);
            }
        });
	}
</script>
<script type="text/javascript">function editRow(title,addurl,gname){
    var rows=$('#tBDangerSourceList').datagrid('getSelections');
    if(rows.length==0){
        tip("请选择需要编辑的数据");
        return false;
    }
    $("span:contains('取消编辑')").parents("a.l-btn").show();
    $("span:contains('保存并关联')").parents("a.l-btn").show();
    for(var i=0;i<rows.length;i++){
        var index= $('#'+gname).datagrid('getRowIndex', rows[i]);
        $('#'+gname).datagrid('beginEdit', index);
    }
}
    //此部分为编辑并保存功能

    //保存数据
    function saveData(title,addurl,gname){
        if(!endEdit(gname))
            return false;
        var uprows=$('#'+gname).datagrid("getChanges","updated");
        if(uprows.length<=0){
            tip("没有需要保存的数据！")
            return false;
        }
        var result={};
        for(var i=0;i<uprows.length;i++){
            for(var d in uprows[i]){
                result["issues["+i+"]."+d]=uprows[i][d];
            }
        }
        $.ajax({
            url:"${pageContext.request.contextPath}/"+addurl+"&riskId=${riskId}",
            type:"post",
            data:result,
            dataType:"json",
            success:function(data){
                tip(data.msg);
                hazardFactorsListsearch();
                $('#tBDangerSourceList').datagrid('reload');
                reloadLevel();
            },
            error:function(){
            }
        });
    }
    //结束编辑
    function endEdit(gname){
        $("span:contains('取消编辑')").parents("a.l-btn").hide();
        $("span:contains('保存并关联')").parents("a.l-btn").hide();
        var  editIndex = $('#'+gname).datagrid('getRows').length-1;
        for(var i=0;i<=editIndex;i++){
            if($('#'+gname).datagrid('validateRow', i))
                $('#'+gname).datagrid('endEdit', i);
            else
                return false;
        }
        return true;
    }
    //取消编辑
    function reject(title,addurl,gname){
        $('#'+gname).datagrid('clearChecked');
        $('#'+gname).datagrid('rejectChanges');
        $("span:contains('取消编辑')").parents("a.l-btn").hide();
        $("span:contains('保存并关联')").parents("a.l-btn").hide();
    }
    function reloadRiskType() {
        window.top.reload_riskType.call();
    }
</script>