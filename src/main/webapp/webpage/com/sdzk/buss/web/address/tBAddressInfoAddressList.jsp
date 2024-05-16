<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
    <link rel="stylesheet" href="plug-in/Validform/css/divfrom.css" type="text/css">
    <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css">
    <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css">
    <script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
    <script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
    <script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
    <script type="text/javascript" src="plug-in/baidumap/js/api.js"></script>
    <script type="text/javascript" src="plug-in/baidumap/js/AreaRestriction_min.js"></script>
    <script type="text/javascript" src="plug-in/jquery/jquery.jqprint.js"></script>
    <link rel="stylesheet" href="plug-in/baidumap/css/print.css" type="text/css" media="print">
    <div id="searchBtnDiv" style="display: none;">
        <div name="searchColums" id="searchColums">
            <input id="isShowSearchId" value="false" type="hidden" />
            <input id="_sqlbuilder" name="sqlbuilder" type="hidden" />
            <form onkeydown="if(event.keyCode==13){tBAddressInfoListsearch();return false;}" id="tBAddressInfoListForm">
                <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css" />
                <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css" />
                <span style="display:-moz-inline-box;display:inline-block;">
                    <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; " title="地点名称">地点名称：</span>
                    <input onkeypress="EnterPress(event)" onkeydown="EnterPress()" name="address" style="width: 120px" class="inuptxt" type="text" />
                </span>
                <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="tBAddressInfoListsearch()">
                    查询
                </a>
                <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-reload" onclick="searchReset('tBAddressInfoList')">
                    重置
                </a>
            </form>
        </div>
    </div>
    <div region="center" style="width: 500px;" title="地点列表"  split="true">
         <t:datagrid name="tBAddressInfoList" checkbox="true" fitColumns="false" title="" actionUrl="tBAddressInfoController.do?dangerSourceAddressDatagrid&dangerId=${dangerId}" idField="id" fit="true" queryMode="group" onLoadSuccess="loadSuccessed">
               <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
               <t:dgCol title="风险点"  field="address"  query="false"  queryMode="single"  width="120"></t:dgCol>
               <t:dgCol title="类型"  field="cate"  query="false" hidden="true" dictionary="locationCate"  queryMode="single"  width="120"></t:dgCol>
               <t:dgCol title="关联风险数量" field="dangerSourceCount" queryMode="group" width="80"></t:dgCol>
               <t:dgCol title="是否显示"  field="isshow" dictionary="sf_yn"   queryMode="group"  width="80"></t:dgCol>
               <t:dgCol title="分管责任人"  field="manageMan" queryMode="single"  width="120"></t:dgCol>
               <t:dgCol title="位置描述"  field="description"  hidden="true" query="false"  queryMode="single"  width="120"></t:dgCol>
               <t:dgCol title="经度"  field="lon"   hidden="true"  queryMode="group"  width="80"></t:dgCol>
               <t:dgCol title="纬度"  field="lat"  hidden="true"   queryMode="group"  width="80"></t:dgCol>
               <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
               <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
               <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
               <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
               <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
               <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
               <t:dgCol title="是否删除"  field="isDelete"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
               <t:dgToolBar title="选择风险点" icon="icon-add" onclick="chooseAddress('选择风险点')"></t:dgToolBar>
               <t:dgToolBar title="删除关联"  icon="icon-remove" url="tBAddressInfoController.do?doBatchDelDangerRel&dangerId=${dangerId}" funname="doBatchDelAddressRel"></t:dgToolBar>
               <c:if test="${isSunAdmin == 'YGADMIN'}">
                   <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
               </c:if>
         </t:datagrid>
    </div>
</div>
<script type="text/javascript">

    function loadSuccessed(){
        $("#tBAddressInfoListtb").prepend($("#searchBtnDiv").html());
        $("#searchBtnDiv").empty();
    }

    window.top["tip_tBAddressInfoList"]=function(){
        tip(this.msg);
    };

    window.top["reload_tBAddressInfoList"]=function(){
        $('#tBAddressInfoList').datagrid('reload');
    };

    /**
     *  阳光账号隐藏数据功能
     * */
    function sunHidden() {
        var rows = $("#tBAddressInfoList").datagrid('getSelections');
        if (rows.length < 1) {
            tip("请选择需要隐藏的数据");
        } else {
            var idsTemp = new Array();
            for (var i = 0; i < rows.length; i++) {
                idsTemp.push(rows[i].id);
            }
            var idt = idsTemp.join(",");

            $.ajax({
                type: 'POST',
                url: 'tBAddressInfoController.do?sunshine',
                dataType:"json",
                async:true,
                cache: false,
                data: {
                    ids:idt
                },
                success:function(data){
                    var msg = data.msg;
                    tip(msg);
                    reloadTable();
                },
                error:function(data){
                }
            });
        }
    }

    (function(){
        $(".datagrid-toolbar").css("height","auto");
        $(".datagrid-toolbar span").css("float","none");
    })();

    function chooseAddress (title) {
        $.dialog(
            {content: "url:tBAddressInfoController.do?addressAddlist&excludeId=${dangerId}", zIndex: 2100, title: '' + title + '', lock: true, parent: windowapi, width: 860, height: 480, left: '96%', top: '40%', opacity: 0.4,}
        );
    }

    function doBatchDelAddressRel(title,url,gname) {
        var ids = [];
        var rows = $("#tBAddressInfoList").datagrid('getSelections');
        if (rows.length > 0) {
            $.dialog.confirm('确定删除该关联关系吗?', function(r) {
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
                                window.top.reload_tBAddressInfoAddList.call();
                                $('#tBAddressInfoAddList').datagrid('reload');
                                ids='';
                            }
                        }
                    });
                }
            });
        } else {
            tip("请选择需要删除的关联关系");
        }
    }
</script>
