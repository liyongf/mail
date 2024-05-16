<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <br>
        <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px">
        <input  name="queryHandleStatus" value="0" type="hidden">
           <label>
               <input name="queryHandleStatusTem" type="radio" value="all" >
               全部
           </label>
           <label>
            <input name="queryHandleStatusTem" type="radio" value="0" checked="checked">
									未完成
           </label>
           <label>
								<input name="queryHandleStatusTem" type="radio" value="1">
									已完成
           </label>
        </span>
    </div>
</div>
<div id="tempDiv" style="display: none;">
    <div style="padding:8px;padding-top: 0;background-color:#F5F5F5;">
        <div style="position: relative;left: 4px;top: 8px;width:51px;background-color: #F5F5F5;">管控层级</div>
        <div style="border:1px solid #C4C4C4;padding: 20px 40px;">
            <label><input id="level-1" name="manageLevel" onclick="setManageLevel(1)" type="radio" value="1" <c:if test="${manangeLevel eq '1'}">checked="checked"</c:if>/>矿级</label>
            <label style="margin-left:15px"><input id="level-2" name="manageLevel" onclick="setManageLevel(2)" type="radio" value="2" <c:if test="${manangeLevel eq '2'}">checked="checked"</c:if>/>部室</label>
            <label style="margin-left:15px"><input id="level-3" name="manageLevel" onclick="setManageLevel(3)" type="radio" value="3" <c:if test="${manangeLevel eq '3'}">checked="checked"</c:if>/>区队</label>
            <label style="margin-left:15px"><input id="level-4" name="manageLevel" onclick="setManageLevel(4)" type="radio" value="4" <c:if test="${manangeLevel eq '4'}">checked="checked"</c:if>/>班组</label>
            <br>
            <label>每条风险管控危害因素条数:<input id="manageNum" name="manageNum" type="text" style="width: 80px" oninput = "value=value.replace(/[^\d]/g,'')" value="${manageNum}"/></label>
            <br>
            <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;text-align:right;" title="专业">专业：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 300px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="majorSelect" style="width: 260px;height: auto"></div>
                 <input id="majorList" type="hidden" name="majorList" value="${major}" class="inputxt" >
             </span>
        </span>

        </div>
    </div>
</div>
<div class="easyui-layout" fit="true">
    <div region="west" style="padding:0px;border:0px;width: 450px" split="true" >
        <t:datagrid name="myManageList" checkbox="false" pagination="true" fitColumns="false" pageSize="20" title="我的管控清单" actionUrl="riskManageController.do?datagridMyManage" idField="id" fit="true" queryMode="group" >
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="风险类型"  field="riskType" dictionary="risk_type" queryMode="single"  width="140" align="center"></t:dgCol>
            <t:dgCol title="激活状态"  field="isDel" formatterjs="colorValueFormatter" queryMode="single" query="true"  width="60" align="center"></t:dgCol>
            <t:dgCol title="common.operation" field="opt" width="100"></t:dgCol>
            <t:dgFunOpt funname="deleteDialog(id,isDel)" title="取消激活" exp="isDel#eq#0" urlclass="ace_button" urlfont="fa-cog" ></t:dgFunOpt>
            <t:dgFunOpt funname="deleteDialog(id,isDel)" title="置为激活" exp="isDel#eq#1" urlclass="ace_button" urlfont="fa-cog"  ></t:dgFunOpt>
            <t:dgToolBar title="全选" icon="icon-add"  funname="allDel(id)"  width="800" height="500"></t:dgToolBar>
            <%--<t:dgFunOpt funname="goDetail(id)" title="查看风险" urlclass="ace_button" urlStyle="background-color:#18a689;"  urlfont="fa-list"></t:dgFunOpt>--%>
        </t:datagrid>
    </div>
</div>
<%--<div region="center">
    <div tools="#tt" class="easyui-panel" title='风险列表' style="padding: 10px;" fit="true" border="false" id="function-panel"></div>
</div>
<div id="tt"></div>--%>
<div region="center">
    <t:datagrid name="riskManageTaskAllList" checkbox="true" pagination="true" onLoadSuccess="loadSuccess" fitColumns="false" title="我的管控任务" actionUrl="riskManageTaskController.do?datagrid" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
        <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
        <t:dgCol title="任务创建时间"  field="createDate" formatter="yyyy-MM-dd"  queryMode="group"  width="100" align="center"></t:dgCol>
        <t:dgCol title="创建人"  field="createNameTemp"   queryMode="group"  width="80" align="center"></t:dgCol>
        <t:dgCol title="管控类型"  field="manageType" dictionary="manageType" hidden="false" queryMode="single"   width="120" align="center"></t:dgCol>
        <t:dgCol title="管控时间"  field="manageTime" formatter="yyyy-MM-dd" query="true"  queryMode="group"  width="100" align="center"></t:dgCol>
        <t:dgCol title="管控名称"  field="taskAllManage.manageName"   queryMode="group"  width="130" align="center"></t:dgCol>
        <t:dgCol title="结束时间"  field="taskAllManage.endDate" formatter="yyyy-MM-dd"   queryMode="group"  width="100" align="center"></t:dgCol>
        <t:dgCol title="管控班次"  field="manageShift" dictionary="workShift" hidden="false" queryMode="single" query="true"  width="80" align="center"></t:dgCol>
        <t:dgCol title="备注"  field="remark"   queryMode="group"  width="115" align="center" formatterjs="valueTitle"></t:dgCol>
        <t:dgCol title="风险数"  field="riskCount" align="center"  queryMode="group"  width="50" formatterjs="showRiskList" ></t:dgCol>
        <t:dgCol title="隐患数"  field="hdCount" align="center"  queryMode="group"  width="50" formatterjs="showHdList"></t:dgCol>
        <t:dgCol title="isExport"  field="isExport"  hidden="true" queryMode="group"  width="120" align="center"></t:dgCol>
        <%--<t:dgCol title="状态"  field="status" replace="未下发_0,已下发_1" query="true"  width="120" ></t:dgCol>--%>
        <t:dgToolBar title="添加任务" icon="icon-add" url="riskManageTaskController.do?goAddNew" funname="add"  width="800" height="400"></t:dgToolBar>
        <t:dgCol title="状态"  field="status"  hidden="true"  width="120" ></t:dgCol>
        <t:dgCol title="endTask"  field="endTask"  hidden="true"  width="120" ></t:dgCol>
        <%--<t:dgToolBar title="编辑" icon="icon-edit" url="riskManageTaskController.do?goUpdate" funname="update"  ></t:dgToolBar>--%>
        <%--<t:dgToolBar title="查看" icon="icon-search" url="riskManageTaskController.do?goDetail" funname="detail"></t:dgToolBar>--%>
        <c:if test="${wenhe eq 'true'}">
            <t:dgToolBar title="删除"  icon="icon-remove" url="riskManageTaskController.do?doBatchDelAll" funname="deleteALLSelect" operationCode="deleteALLSelect"></t:dgToolBar>
        </c:if>
        <c:if test="${wenhe ne 'true'}">
            <t:dgToolBar title="删除"  icon="icon-remove" url="riskManageTaskController.do?doBatchDelAll" funname="deleteALLSelect"  ></t:dgToolBar>
        </c:if>
        <t:dgCol title="操作" field="opt" width="225" align="center"></t:dgCol>
        <t:dgFunOpt funname="taskResultAdd(id,manageType)" title="开始管控"  urlclass="ace_button" exp="status#eq#0" ></t:dgFunOpt>
        <t:dgFunOpt funname="addHiddenNew(id)" title="隐患录入"  urlclass="ace_button" exp="isExport#eq#0&&status#eq#0"></t:dgFunOpt>
        <t:dgFunOpt funname="ExportXls(id,manageType)" title="导出"  urlclass="ace_button" exp="isExport#eq#0&&status#eq#0"></t:dgFunOpt>
        <t:dgFunOpt funname="addRiskNew(id)" title="风险录入"  urlclass="ace_button" exp="isExport#eq#1&&status#eq#0"></t:dgFunOpt>
        <t:dgFunOpt funname="endTask(id)" title="结束任务"  urlclass="ace_button" exp="endTask#eq#0&&status#eq#0"></t:dgFunOpt>
        <t:dgFunOpt funname="taskResultAdd(id,manageType)" title="查看"  urlclass="ace_button" exp="status#eq#1"></t:dgFunOpt>
    </t:datagrid>
</div>
</div>



<script type="text/javascript">

    function goDetail(id) {

        $("#function-panel").panel(
            {
                title :'风险列表',
                href:"riskManageController.do?goRiskDetail&myManageId=" + id
            }
        );
    }

    function deleteDialog(id,deleteFlag){
        $.ajax({
            url : "riskManageController.do?isDel",
            type : 'post',
            cache : false,
            async: true,
            data : {
                id:id,
            },
            success : function(data) {
                var d = $.parseJSON(data);
                if (d=="1") {
                    var url = "riskManageController.do?delete&id=" + id + "&deleteFlag=" + deleteFlag;
                    var msg = '确认设置该风险类型为无效状态吗?';
                    if ("1" == deleteFlag) {
                        var msg = '确认设置该风险类型为有效状态吗?';
                    }
                    //$.dialog.confirm(msg, function () {
                        reloadTable();
                        doSubmit(url);
                    /*}, function () {
                    });*/
                }else if(d=="0"){
                    tip("请选择管控层级,专业");
                }
            }
        });

    }

    function colorValueFormatter(value,rec,index){
        if(value=="1"){
            return '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 22px; padding-left: 26px;" type="text" value=""><span class="minicolors-swatch" style="top:0px;"><span class="minicolors-swatch-color" style="background-color:#e9e9e9;"></span></span></div></div>';
        }else{
            return '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 22px; padding-left: 26px;" type="text" value=""><span class="minicolors-swatch" style="top:0px;"><span class="minicolors-swatch-color" style="background-color: #f50a0a;"></span></span></div></div>';

        }
    }
    var magicsuggestMajorSelect = "";
    var load= true;
    $(function () {
       /* $("a[iconcls='icon-search']").hide();*/
        $("a[iconcls='icon-reload']").hide();
        var datagrid = $("#myManageListtb");
        datagrid.find("form[id='myManageListForm']").prepend($("#tempDiv").html());
        datagrid.find("form[id='myManageListForm']>span:last").hide();
        $("#tempDiv").empty();
        $(':checkbox[type="checkbox"]').each(function(){
            $(this).click(function(){
                if($(this).attr('checked')){
                    $(':checkbox[type="checkbox"][name='+$(this).attr("name")+']').removeAttr('checked');
                    $(this).attr('checked','checked');
                }
            });
        });


        if(${major ne ''}){
            load= true;
        }else{
            load= false;
        }
        magicsuggestMajorSelect = $('#majorSelect').magicSuggest({
            data: 'magicSelectController.do?getMajor',
            allowFreeEntries: false,
            valueField: 'typecode',
            value: '${major}'!=''?'${major}'.split(","):[],
            placeholder: '输入或选择',
            maxSelection: 20,
            selectFirst: true,
            highlight: false,
            displayField: 'typename'
        });
        $(magicsuggestMajorSelect).on('selectionchange', function (c) {
            $("#majorList").val(magicsuggestMajorSelect.getValue());
            var major = $("#majorList").val();
            if (major != "") {
                $.ajax({
                    url: "riskManageController.do?setMajor",
                    type: 'post',
                    cache: false,
                    async: true,
                    data: {
                        major: major
                    },
                    success: function (data) {
                        var d = $.parseJSON(data);
                        if (!load) {
                            tip(d.msg);
                        }
                        load = false;
                        $("#myManageList").datagrid("reload");
                    }
                });
            }
        });

        $("#manageNum").blur(function () {
            var manageNum = $("#manageNum").val();
            if (manageNum != "") {
                $.ajax({
                    url: "riskManageController.do?setManageNum",
                    type: 'post',
                    cache : false,
                    async: true,
                    data: {
                        manageNum: manageNum
                    },
                    success: function (data) {
                        var d = $.parseJSON(data);
                        tip(d.msg);
                    }
                });
            }
        });
    });



    function setManageLevel(manageLevel) {
        if (manageLevel != "") {
            $.ajax({
                url: "riskManageController.do?setManageLevel",
                type: 'post',
                cache : false,
                async: true,
                data: {
                    manageLevel: manageLevel
                },
                success: function (data) {
                    var d = $.parseJSON(data);
                    tip(d.msg);
                }
            });
        }
    }

    $(document).ready(function(){
        var datagrid = $("#riskManageTaskAllListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");

        $("input[name='queryHandleStatusTem']").change(function() {
            var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();
            $("input[name='queryHandleStatus']").val(selectedvalue);
            riskManageTaskAllListsearch();
        });

    });

    window.top["reload_riskManageTaskAllList"]=function(){
        if(typeof(this.msg)!='undefined' && this.msg!=null&&this.msg!=""){
            tip(this.msg);
        }
        $("#riskManageTaskAllList").datagrid("reload");
    };

    window.top["reload_riskManagePostTaskAllList"]=function(){
        if(typeof(this.msg)!='undefined' && this.msg!=null&&this.msg!=""){
            tip(this.msg);
        }
        $("#riskManageTaskAllList").datagrid("reload");
    };






    function taskResultAdd(id,manageType){
        var manage = manageType;
        if (manage=="comprehensive"||manage=="profession"||manage=="team"||manage=="group") {
            var url = "riskManageResultController.do?listNew&taskAllId="+id;
            addOneTab("管控任务清单",url,"default");
        }else if(manage=="post"){
            var url = "riskManageResultController.do?postList&postTaskAllId="+id;
            addOneTab("管控任务清单",url,"default");
        }else{
            var url = "tBHiddenDangerExamController.do?newList&examType=yh&taskAllId="+id;
            addOneTab("隐患录入",url,"default");
        }
    }


    function addHiddenNew(id){
        $.ajax({
            url : "riskManageResultController.do?isAddHidden&taskAllId="+id,
            type : 'post',
            cache : false,
            async: true,
            data : {
                id:id,
            },
            success : function(data) {
                var d = $.parseJSON(data);
                if (d=="1") {
                    var url = "tBHiddenDangerExamController.do?newList&examType=yh&taskAllId="+id;

                    addOneTab("补充隐患录入",url,"default");
                }else {
                    tip("超出隐患录入时间");
                }
            }
        });

    }
    function deleteALLSelect(title,url,gname) {
        gridname=gname;
        var ids = [];
        var rows = $("#"+gname).datagrid('getSelections');
        if (rows.length > 0) {
            $.dialog.setting.zIndex = getzIndex(true);
            $.dialog.confirm('你确定永久删除该数据吗?', function(r) {
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
                        beforeSend: function(data){
                            $.messager.progress({
                                text : "正在删除任务......",
                                interval : 100
                            });
                        },
                        success : function(data) {
                            var d = $.parseJSON(data);
                            if (d.success) {
                                var msg = d.msg;
                                tip(msg);
                                reloadTable();
                                $("#"+gname).datagrid('unselectAll');
                                ids='';
                            }
                        },
                        complete: function(data){
                            $.messager.progress('close');
                        }
                    });
                }
            });
        } else {
            tip("请选择需要删除的数据");
        }
    }

    function allDel(id){
        $.ajax({
            url : "riskManageController.do?isDel",
            type : 'post',
            cache : false,
            async: true,
            data : {
                id:id,
            },
            success : function(data) {
                var d = $.parseJSON(data);
                if (d=="1") {
                    $.ajax({
                        url : "riskManageController.do?all",
                        type : 'post',
                        cache : false,
                        async: true,
                        data : {
                        },
                        success : function(data) {
                            var d = $.parseJSON(data);
                            if (d=="0") {
                                tip("激活成功");
                                $("#myManageList").datagrid("reload");
                            }
                        }
                    });
                }else if(d=="0"){
                    tip("请选择管控层级,专业");
                }
            }
        });
    }

    function loadSuccess(){
        $("td[field='taskAllManage.manageName']:gt(0)").each(function(){
            var name = $(this).children().text();
            if(name  != ''){
                $(this).parent().css("background-color","#FFD2D2");
            }
        });
    }

    //导出
    function ExportXls(id,manageType) {
        if(manageType=="post"){
            JeecgExcelExport("riskManageTaskController.do?exportXlsPost&id="+id, "riskManageTaskAllList");
        }else{
            JeecgExcelExport("riskManageTaskController.do?exportXls&id="+id, "riskManageTaskAllList");
        }

    }


    function showRiskList(value,rec,index){
        var url = "riskManageResultController.do?showriskListTaskAll&riskManageTaskAllId="+rec.id;
        //return "<a href='#' onclick='createdetailwindow(\"风险列表\",\"" + url + "\",800,600);'>"+value+"</a>";

        if(rec.isExport=='1'){
            url = "riskManageResultController.do?showriskListTaskAll&riskManageTaskAllIdYH="+rec.id;
        }
        return "<a href='#' onclick='addOneTab(\"已录风险列表\",\"" + url + "\",\"default\");'>"+value+"</a>";
    }
    function showHdList(value,rec,index){
        if(rec.manageType == 'comprehensive' || rec.manageType == 'profession' || rec.manageType == 'team' || rec.manageType == 'group'){
            var url = "riskManageResultController.do?showHdListTaskAll&riskManageTaskAllId="+rec.id;
        }else if(rec.manageType == 'post' ){
            var url = "riskManageResultController.do?showHdPostLisTaskAll&riskManageTaskAllId="+rec.id;
        }else{
            var url = "tBHiddenDangerExamController.do?newList&examType=yh&taskAllId="+rec.id;
        }

        //return "<a href='#' onclick='createdetailwindow(\"隐患列表\",\"" + url + "\",800,600);'>"+value+"</a>";
        return "<a href='#' onclick='addOneTab(\"已录隐患列表\",\"" + url + "\",\"default\");'>"+value+"</a>";
    }


    function endTask(id){
        $.dialog.confirm('你确定结束任务吗?', function(r) {
            $.ajax({
                url : "riskManageTaskController.do?endTask&id="+id,
                type : 'post',
                cache : false,
                success : function(data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var msg = d.msg;
                        tip(msg);
                        $("#riskManageTaskAllList").datagrid("reload");
                    }
                },
                complete: function(data){
                    $.messager.progress('close');
                }
            });
        })
    }

    function addRiskNew(id){

        var url = "riskIdentificationController.do?goAdd&riskManageTaskAllIdYH="+id;
        addOneTab("新增风险录入",url,"default");
    }
</script>