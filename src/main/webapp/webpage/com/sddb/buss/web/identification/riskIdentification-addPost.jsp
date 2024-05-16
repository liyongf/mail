<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<title>危害因素</title>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<link href="plug-in/jQueryLabel/css/tab.css" type="text/css" rel="stylesheet" />
<link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
<script type="text/javascript" src="plug-in/jQueryLabel/js/tab.js"></script>

<div class="easyui-layout" fit="true"  id="main_majorHiddenDangerList">
    <div region="center" style="padding:0px;border:0px" >
        <div id = "searchColumn" style="display: none">
        <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskIdentificationController.do?doAdd" >
            <input id="id" name="id" type="hidden" value="${riskIdentificationPage.id }"/>
            <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
                <tr>
                    <c:if test="${identificationType ne '4'}">
                        <td align="right">
                            <label class="Validform_label">
                                <b style="color: red">*</b>风险点:
                            </label>
                        </td>
                        <td class="value">
                            <div id="magicsuggest" style="width: 130px;height: 15px"></div>
                            <input type="hidden" name="address.address" id="address" value="" class="inputxt" datatype="*">
                            <span class="Validform_checktip" style="display: inline;">请选择地点</span>
                            <div class="ui_buttons" style="text-align: left;display: inline">
                                <input type="button" id="btn" value="克隆已辨识风险点" onclick="showAddress();" class="ui_state_highlight" >
                            </div>
                            <label class="Validform_label" style="display: none;">地点</label>
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">风险点</label>
                        </td>
                    </c:if>
                    <c:if test="${identificationType eq '4'}">
                        <td align="right">
                            <label class="Validform_label">
                                <b style="color: red">*</b>岗位:
                            </label>
                        </td>
                        <td class="value">
                            <input type="hidden" name="post.id" id="post.id" datatype="*" />
                            <div id="postname" name="postname" style="width: 130px;height: 15px"></div>
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">岗位</label>
                            <div class="ui_buttons" style="text-align: left;display: inline">
                                <input type="button" id="btn" value="克隆已辨识岗位" onclick="showAddress();" class="ui_state_highlight" >
                            </div>
                            <label class="Validform_label" style="display: none;">岗位</label>
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">岗位</label>
                        </td>
                    </c:if>
                    <td align="right">
                        <label class="Validform_label">
                            <b style="color: red">*</b>信息来源:
                        </label>
                    </td>
                    <td class="value">
                        <t:dictSelect field="identificationType" type="list" datatype="*" id="identificationType"
                                      typeGroupCode="identifi_from" defaultVal="${identificationType}" hasLabel="false" readonly="readonly" title="信息来源"></t:dictSelect>
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">信息来源</label>
                    </td>
                </tr>
                <c:if test="${identificationType eq '3'}">
                    <tr>
                        <td align="right">
                            <label class="Validform_label">
                                专项辨识类型:
                            </label>
                        </td>
                        <td class="value">
                            <c:if test="${load eq 'detail'}">
                                <t:dictSelect field="specificType" type="list"  id="specificType"
                                              typeGroupCode="specificType"  hasLabel="false" defaultVal="${riskIdentificationPage.specificType}" readonly="readonly"  title="专项辨识类型"></t:dictSelect>
                            </c:if>
                            <c:if test="${load ne 'detail'}">
                                <t:dictSelect field="specificType" type="list"  id="specificType"
                                              typeGroupCode="specificType" hasLabel="false" defaultVal="${riskIdentificationPage.specificType}" title="专项辨识类型"></t:dictSelect>
                            </c:if>
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">专项辨识类型</label>
                        </td>
                        <td align="right">
                            <label class="Validform_label">
                                专项辨识名称:
                            </label>
                        </td>
                        <td class="value">
                            <c:if test="${load eq 'detail'}">
                                <input id="specificName" name="specificName" type="text" style="width: 150px"  value="${riskIdentificationPage.specificName}" readonly="readonly"/>
                            </c:if>
                            <c:if test="${load ne 'detail'}">
                                <input id="specificName" name="specificName" type="text" style="width: 150px"  value="${riskIdentificationPage.specificName}"/>
                            </c:if>
                            <span class="Validform_checktip"></span>
                            <label class="Validform_label" style="display: none;">专项辨识名称</label>
                        </td>
                    </tr>
                </c:if>
               <%-- <tr>
                    <td align="right">
                        <label class="Validform_label">
                            <b style="color: red">*</b>参加辨识人员:
                        </label>
                    </td>
                    <td class="value" colspan="3">
                        <div id="identifiManSelect" style="width: 80%;height: 15px;"></div>
                        <input id="identifiManId" name="identifiManId" type="hidden" style="width: 150px;overflow-x: hidden" class="inputxt">
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">参加辨识人员</label>
                    </td>
                </tr>--%>
                <tr>
                    <td align="right">
                        <label class="Validform_label">
                            <b style="color: red">*</b>风险类型选择:
                        </label>
                    </td>
                    <td class="value" colspan="3">
                        <div class="ui_buttons" style="text-align: left;">
                            <c:forEach items="${riskTypes}" var="riskType" varStatus="i">
                                <input type="button" id="${riskType.typecode}_btn" value="${riskType.typename}" class="ui_state_highlight" onclick="addRisk('${riskType.typecode}')">
                                <c:if test="${i.index==10}">
                                    <br> <br>
                                </c:if>
                                <c:if test="${i.index==21}">
                                    <br> <br>
                                </c:if>
                            </c:forEach>
                        </div>
                    </td>
                </tr>
            </table>
        </t:formvalid>
        </div>
            <div class="easyui-layout" fit="true">
                <div region="center" style="padding:0px;border:0px;height: 100%" >
                <t:datagrid name="hazardFactorsList" checkbox="true" pagination="true"  onClick="toModify" fitColumns="true" title="" height="" onLoadSuccess="dataGridLoadSuccess" actionUrl="riskIdentificationController.do?datagridPost&identificationType=${identificationType}&load=${load}" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
                    <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group" query="true"  width="120"></t:dgCol>
                   <%-- <c:if test="${identificationType ne '4'}">
                        <t:dgCol title="风险点"  field="address.address"  queryMode="single"  width="120"></t:dgCol>
                    </c:if>
                    <c:if test="${identificationType eq '4'}">
                        <t:dgCol title="岗位"  field="post.postName"  queryMode="single"  width="120"></t:dgCol>
                    </c:if>--%>
                    <t:dgCol title="岗位"  field="post.postName"  queryMode="single"  width="120" query="false"></t:dgCol>
                    <t:dgCol title="单位"  field="postUnit.departName"  queryMode="single"  width="120"></t:dgCol>
                    <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" queryMode="single"  width="120"></t:dgCol>
                   <%-- <t:dgCol title="风险描述"  field="riskDesc"   queryMode="group"  width="200"></t:dgCol>--%>
                    <t:dgCol title="风险等级"  field="riskLevel" dictionary="factors_level" queryMode="group"  width="120" ></t:dgCol>
                    <t:dgCol title="危害因素"  align="center" field="hazardFactortsPostNum" url="riskIdentificationController.do?wxysPostList&id={id}&load=${load}" queryMode="group"  width="120" ></t:dgCol>
                  <%--  <t:dgCol title="最高管控层级"  field="manageLevel" dictionary="identifi_mange_level" queryMode="group"  width="120" ></t:dgCol>
                    <t:dgCol title="最高管控责任人"  field="dutyManager"  queryMode="group"  width="120" ></t:dgCol>
                    <t:dgCol title="评估日期"  field="identifiDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
                    <t:dgCol title="解除日期"  field="expDate" formatter="yyyy-MM-dd"  queryMode="group"  width="120" ></t:dgCol>
                    <t:dgCol title="状态"  field="status" dictionary="identifi_status"  queryMode="group"  width="120" ></t:dgCol>--%>
                    <%--<t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>--%>
                  <%--  <t:dgFunOpt funname="toModifyOpt(id)" title="开始辨识"  urlclass="ace_button"></t:dgFunOpt>--%>
                    <c:if test="${load ne 'detail'}">
                        <t:dgToolBar title="删除"  icon="icon-remove" url="riskIdentificationController.do?doBatchDel" funname="doDel"></t:dgToolBar>
                        <t:dgToolBar title="提交审核" icon="icon-edit" url="riskIdentificationController.do?goReview" funname="goReview"></t:dgToolBar>
                    </c:if>
                </t:datagrid>
            </div>
        </div>
    </div>
</div>

<div data-options="region:'east',
                title:'编辑',
                collapsed:true,
                split:true,
                border:false,
                onExpand : function(){
                    li_east = 1;
                },
                onCollapse : function() {
                    li_east = 0;
                }"
     style="width: 1000px; overflow: hidden;z-index: 99;" id="eastPanel">
    <div class="easyui-panel" style="padding: 1px;" fit="true" border="false" id="main_rectList"></div>
</div>
<div class="row border-bottom" ></div>



<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">


    function toModifyOpt(id){

        var width = $("div.row.border-bottom").width();
        if(li_east == 0){
            $('#main_majorHiddenDangerList').layout('expand','east');
        }
        $('#main_majorHiddenDangerList').layout('panel','east').panel('setTitle', "编辑");
        $('#main_majorHiddenDangerList').layout('panel','east').panel('resize',{width:width+2});
        // var rows = $("#hazardFactorsList").datagrid('getSelections');
        $('#main_rectList').panel("refresh", "riskIdentificationController.do?toEditDetailInfo&riskId="+id+"&load=${load}");
    }



    window.top["reload_hazardFactorsList"]=function(){
        var identificationType = "${identificationType}";
        if(identificationType != '4') {
            var param = $('#hazardFactorsList').datagrid('options').queryParams;
            param["addressId"] = $("#address").val();
            $('#hazardFactorsList').datagrid('reload', param);
        }else {
            var param = $('#hazardFactorsList').datagrid('options').queryParams;
            var postId = document.getElementById("post.id").value;
            param["post.id"] = postId;
            $('#hazardFactorsList').datagrid('reload', param);
        }
    };
    function showMsg(){
        $("#address").val(magicsuggestSelected.getValue());
        var param=$('#hazardFactorsList').datagrid('options').queryParams;
        param["addressId"]=$("#address").val();
        $('#hazardFactorsList').datagrid('reload',param);
        //查询上次辨识人
        $.ajax({
            url : "riskIdentificationController.do?getLastIdentifiMan&load=${load}",
            type : 'post',
            cache : false,
            data : {
                address: $("#address").val()
            },
            success : function(data) {
                userSelected.setValue(eval(data));
            }
        });
        if($("#address").val() == "" || $("#address").val() == null){
            $(".ui_state_highlight").attr('disabled',false);
        }
    }
    function showPostCloneMsg(){
        var param=$('#hazardFactorsList').datagrid('options').queryParams;
        var postId = document.getElementById("post.id").value;
        param["post.id"]=postId;
        $('#hazardFactorsList').datagrid('reload',param);
        //查询上次辨识人
        $.ajax({
            url : "riskIdentificationController.do?getLastIdentifiMan&load=${load}",
            type : 'post',
            cache : false,
            data : {
                postId: postId,
                identificationType: $("#identificationType").val()
            },
            success : function(data) {
                userSelected.setValue(eval(data));
            }
        });

        if(postId == "" || postId == null){
            $(".ui_state_highlight").attr('disabled',false);
        }
    }

    function  showAddress() {
        var identificationType = "${identificationType}";
        if(identificationType != '4'){
            if($("#address").val().length >0){
                openwindow("克隆风险点","riskIdentificationController.do?goClone&id="+$("#address").val()+"&identificationType=${identificationType}","",700,400);
            }else{
                tip("请先选择风险点");
            }
        }else{
            var postId = document.getElementById("post.id").value;
            if(postId.length >0){
                openwindow("岗位风险点","riskIdentificationController.do?goClone&id="+postId+"&identificationType=${identificationType}","",700,400);
            }else{
                tip("请先选择岗位");
            }
        }
    }
    function goReview(){
        var rows = $("#hazardFactorsList").datagrid('getSelections');
        var identifiManId = $("#identifiManId").val();
        if(rows ==null || rows.length <=0){
            tip("请选择需要提交审核的数据");
        }else{
            var ids = new Array();
            for(var i=0;i<rows.length;i++){
                ids.push(rows[i].id);
            }
            $.ajax({
                url : "riskIdentificationController.do?isReport",
                type : 'post',
                cache : false,
                async: true,
                data : {
                    ids:ids.join("\',\'"),
                },
                success : function(data) {
                    var d = $.parseJSON(data);
                    if (d=="1") {
                        openwindow("提交审批", "riskIdentificationController.do?goReport&ids=" + ids, "hazardFactorsList", 600, 450);
                        /*$.ajax({
                            url : "riskIdentificationController.do?doReport",
                            type : 'post',
                            cache : false,
                            async: true,
                            data : {
                                ids:ids.join(","),
                                identifiManId:identifiManId
                            },
                            success : function(data) {
                                var d = $.parseJSON(data);
                                if (d.success) {
                                    tip(d.msg);
                                    var param=$('#hazardFactorsList').datagrid('options').queryParams;
                                    param["addressId"]=$("#address").val();
                                    $('#hazardFactorsList').datagrid('reload',param);
                                }
                            }
                        });*/
                    }else if(d=="0"){
                        tip("风险信息尚未填写完整");
                    }else if(d=="-1"){
                        tip("关联中存在未审核的危害因素");
                    }else{
                        tip("请选择未提交审核的风险");
                    }
                }
            });
        }
    }

    function dataGridLoadSuccess(){

        var rows = $("#hazardFactorsList").datagrid('getRows');
        for(var j =0 ;j<rows.length;j++){
            //$("#"+rows[j].riskType+"_btn").attr('disabled',true);
        }
    }
    function doDel(){
        var rows = $("#hazardFactorsList").datagrid('getSelections');
        if(rows == null || rows.length <=0){
            tip("请选择要删除的数据");
        }else{
            var ids = new Array();
            var typecode = new Array();
            for(var i=0;i<rows.length;i++){
                ids.push(rows[i].id);
                typecode.push(rows[i].riskType);
            }
            $.dialog.confirm('你确定永久删除该数据吗?', function (r) {
                //校验是否已提交审核
                $.ajax({
                    url: "riskIdentificationController.do?isCanDel",
                    type: 'post',
                    cache: false,
                    async: true,
                    data: {
                        ids: ids.join(",")
                    },
                    success: function (data) {
                        var d = $.parseJSON(data);
                        if (d.success) {
                            $.ajax({
                                url: "riskIdentificationController.do?delRisk",
                                type: 'post',
                                cache: false,
                                async: true,
                                data: {
                                    ids: ids.join(",")
                                },
                                success: function (data) {
                                    $('#main_majorHiddenDangerList').layout('collapse', 'east');
                                    $('#main_rectList').html("");
                                    var d = $.parseJSON(data);
                                    if (d.success) {
                                        //恢复按钮点击
                                        for (var j = 0; j < typecode.length; j++) {
                                            $("#" + typecode[j] + "_btn").attr('disabled', false);
                                        }
                                    }
                                    var param = $('#hazardFactorsList').datagrid('options').queryParams;
                                    param["addressId"] = $("#address").val();
                                    $('#hazardFactorsList').datagrid('reload', param);
                                }
                            });
                        } else {
                            tip("已提交审核数据无法删除，请重新选择");
                        }
                    }
                });
            });
        }
    }
    var magicsuggestSelected = "";
    var magicSuggestPostSelected = "";
    var userSelected = "";
    $(function() {
        // $(".layout-button-right").bind('click', function () {
        //     $('.datagrid-wrap.panel-body.panel-body-noheader').parent().css('width', '100%');
        //     $('.datagrid-wrap.panel-body.panel-body-noheader').css('width', '100%');
        //     jQuery("#hazardFactorsList").datagrid('reload');
        // });
        var datagrid = $("#hazardFactorsListtb");
        datagrid.find("div[name='searchColums']").empty();
        datagrid.find("div[name='searchColums']").append($("#searchColumn").html());
        $("#searchColumn").remove();

        var addressId = "${addressId}";
        var postId = "${postId}";

        if(postId.length >0){
            magicSuggestPostSelected = getPostMagicSuggestWithValue($("#postname"), $("input[name='post.id']"),"${postId}",false);
        }else{
            magicSuggestPostSelected = getPostMagicSuggest($("#postname"), $("input[name='post.id']"));
        }
        if(addressId.length >0) {
            magicsuggestSelected = $('#magicsuggest').magicSuggest({
                data: 'magicSelectController.do?getAddressList',
                allowFreeEntries: false,
                valueField: 'id',
                placeholder: '输入或选择',
                maxSelection: 1,
                value:['${addressId}'],
                selectFirst: true,
                highlight: false,
                displayField: 'address'
            });
        }else{
            magicsuggestSelected = $('#magicsuggest').magicSuggest({
                data: 'magicSelectController.do?getAddressList',
                allowFreeEntries: false,
                valueField: 'id',
                placeholder: '输入或选择',
                maxSelection: 1,
                selectFirst: true,
                highlight: false,
                displayField: 'address'
            });
        }

        var firstLoad = true;
        $(magicsuggestSelected).on('selectionchange', function(e,m){
            $("#address").val(magicsuggestSelected.getValue());
            var param=$('#hazardFactorsList').datagrid('options').queryParams;
            param["addressId"]=$("#address").val();
            $('#hazardFactorsList').datagrid('reload',param);
            if(firstLoad){
                //查询上次辨识人
                $.ajax({
                    url : "riskIdentificationController.do?getLastIdentifiMan&load=${load}",
                    type : 'post',
                    cache : false,
                    data : {
                        address: $("#address").val(),
                        identificationType: $("#identificationType").val()
                    },
                    success : function(data) {
                        userSelected.setValue(eval(data));
                    }
                });
                //设置firstLoad为false
                firstLoad = false;
            }
            if($("#address").val() == "" || $("#address").val() == null){
                $(".ui_state_highlight").attr('disabled',false);
            }
        });

        $(magicSuggestPostSelected).on('selectionchange', function(e,m){
            var param=$('#hazardFactorsList').datagrid('options').queryParams;
            var postId = document.getElementById("post.id").value;
            param["post.id"]=postId;
            $('#hazardFactorsList').datagrid('reload',param);
            if(firstLoad){
                //查询上次辨识人
                $.ajax({
                    url : "riskIdentificationController.do?getLastIdentifiMan&load=${load}",
                    type : 'post',
                    cache : false,
                    data : {
                        postId: postId,
                        identificationType: $("#identificationType").val()
                    },
                    success : function(data) {
                        userSelected.setValue(eval(data));
                    }
                });

                //设置firstLoad为false
                firstLoad = false;
            }
            if(postId == "" || postId == null){
                $(".ui_state_highlight").attr('disabled',false);
            }
        });

        userSelected = $('#identifiManSelect').magicSuggest({
            data: 'magicSelectController.do?getUserList',
            allowFreeEntries: false,
            valueField: 'id',
            placeholder: '输入或选择',
            maxSelection: 10,
            selectFirst: true,
            highlight: false,
            matchField:['spelling','realName','userName','fullSpelling'],
            displayField: 'realName'
        });
        $(userSelected).on('selectionchange', function(e,m){
            $("#identifiManId").val(userSelected.getValue());
        });
        var load = "${load}";
        if("detail" == load){
            $("form input,textarea,select").attr("disabled","true");
            userSelected.disable();
            magicsuggestSelected.disable();
        }
    });
    function addRisk(riskType){
        var identificationType = "${identificationType}";

        var address =  "";
        var postId = "";

        if(identificationType == "2" || identificationType == '3' || identificationType == '5'
                || identificationType == 'riskManage_comprehensive' || identificationType == 'riskManage_profession' || identificationType == 'riskManage_team'
                || identificationType == 'riskManage_group' || identificationType == 'riskManage_post'){
            address =  $("#address").val();
        }else{
            postId = document.getElementById("post.id").value;
        }
        var identifiManId = $("#identifiManId").val();
        if(identificationType != '4') {
            if (address == null || address.length <= 0) {
                tip("请选择风险点");
                return;
            }
        }else{
            if(postId == null || postId.length <=0){
                tip("请选择岗位");
                return;
            }
        }
        if(identifiManId == null || identifiManId.length <=0) {
            tip("请选择参加辨识人员");
            return;
        }
        var specificType = $("select[name='specificType']>option:checked").val();
        var specificName = $("#specificName").val();
        $.ajax({
            url : "riskIdentificationController.do?saveRiskTemp",
            type : 'post',
            cache : false,
            data : {
                address:address,
                postId:postId,
                identificationType:$("#identificationType").val(),
                identifiManId:identifiManId,
                riskType:riskType,
                riskManageHazardFactorId:'${riskManageHazardFactorId}',
                specificType:specificType,
                specificName:specificName
            },
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    var param=$('#hazardFactorsList').datagrid('options').queryParams;
                    if(identificationType != '4') {
                        param["addressId"]=address;
                    }else{
                        param["post.id"]=postId;
                    }
                    $('#hazardFactorsList').datagrid('reload',param);
                   // $("#"+riskType+"_btn").attr('disabled',true);
                }
            }
        });
    }
    function toModify(rowIndex,rowData){
        return;
        <%--if(li_east == 0){--%>
            <%--$('#main_majorHiddenDangerList').layout('expand','east');--%>
        <%--}--%>
        <%--$('#main_majorHiddenDangerList').layout('panel','east').panel('setTitle', "编辑");--%>
        <%--$('#main_majorHiddenDangerList').layout('panel','west').panel('setTitle', "编辑");--%>
        <%--// var rows = $("#hazardFactorsList").datagrid('getSelections');--%>
        <%--$('#main_rectList').panel("refresh", "riskIdentificationController.do?toEditDetailInfo&riskId="+rowData.id+"&load=${load}");--%>
    }


    //风险管控-危害因素管控-风险录入
    $(function(){
        setTimeout(initInfo,500);
    })
    function initInfo() {

        var addressId = "${addressId}";
        if(addressId!=""){
            magicsuggestSelected.disable();
        }

        if($("#identifiManId").val()==""){
            var userId = "${userId}";
            if(userId!=undefined && userId!="") {
                userSelected.setValue([userId]);
            }
        }

        var riskType = "${riskType}";
        if(riskType!=undefined && riskType!=""){
            var disabled = $("#"+riskType+"_btn").attr("disabled");
            if(disabled==undefined) {
                if(addressId!=""){
                    //addRisk(riskType);
                    $("#"+riskType+"_btn").click();
                }
            }
        }
    }
</script>