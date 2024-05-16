<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,layui"></t:base>
<div class="layuimini-container">
    <div class="layuimini-main">
        <fieldset class="table-search-fieldset">
            <legend>搜索信息</legend>
            <div style="margin: 10px 10px 10px 10px">
                <form class="layui-form layui-form-pane" action="" lay-filter="searchForm">
                    <div class="layui-form-item">
                        <div class="layui-inline">
                            <label class="layui-form-label">风险类型</label>
                            <div class="layui-input-inline">
                                <select id="select" name="riskType" lay-search="">
                                    <option value="">选择或搜索选择</option>
                                    <c:forEach items="${riskTypeList}" var="item">
                                        <option value="${item.typecode}">${item.typename}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">风险描述</label>
                            <div class="layui-input-inline">
                                <input type="text" id="text" name="riskDesc" autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <button type="submit" class="layui-btn layui-btn-primary" lay-submit
                                    lay-filter="data-search-btn"><i class="fa fa-search"></i> 搜索
                            </button>
                            <button type="button" class="layui-btn layui-btn-primary" lay-submit
                                    lay-filter="data-refresh-btn"><i class="fa fa-refresh"></i> 重置
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </fieldset>

        <script type="text/html" id="toolbarDemo">
            <div class="layui-btn-container">
                <c:if test="${roleOpt.contains('add') || roleOpt eq ''}">
                    <button class="layui-btn layui-btn-radius layui-btn-sm data-add-btn">添加</button>
                </c:if>
<%--                <c:if test="${roleOpt.contains('edit') || roleOpt eq ''}">--%>
<%--                    <button class="layui-btn layui-btn-radius layui-btn-sm layui-btn-warm data-edit-btn">编辑</button>--%>
<%--                </c:if>--%>
<%--                <c:if test="${roleOpt.contains('delete') || roleOpt eq ''}">--%>
<%--                    <button class="layui-btn layui-btn-radius layui-btn-sm layui-btn-danger data-delete-btn">删除</button>--%>
<%--                </c:if>--%>
            </div>
        </script>

        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>

        <script type="text/html" id="currentTableBar">
            {{#  if(d.sfPlanInfoId === "") { }}
            <a class="layui-btn layui-btn-xs " lay-event="add">添加</a>
            {{#   }else{ }}
            <a class="layui-btn layui-btn-xs data-count-edit" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-xs layui-btn-danger data-count-delete" lay-event="delete">删除</a>
            <a class="layui-btn layui-btn-xs layui-btn-normal data-count-download" lay-event="download">下载</a>
            <a class="layui-btn layui-btn-xs layui-btn-normal data-count-preview" lay-event="preview">预览</a>
            {{#   } }}
        </script>

    </div>
</div>
<script>
    layui.use(['form', 'table', 'laydate'], function () {
        var $ = layui.jquery,
                form = layui.form,
                table = layui.table,
                laydate = layui.laydate,
                util = layui.util,
                layuimini = layui.layuimini;

        laydate.render({
            elem: '#date',
            trigger:'click'
            //range: '~',//时间范围
            //calendar: true  //开启公历节日
        });

        table.render({
            elem: '#currentTableId',
            url: 'sfPlanInfoController.do?datagrid',
            // toolbar: '#toolbarDemo',
            defaultToolbar: [],
            cols: [[
                {type: "checkbox", width: 50, fixed: "left"},
                {field: 'id', width: 80, title: 'ID', hide: true},
                {field: 'sfPlanInfoId', width: 80, title: 'sfPlanInfoId', hide: true},//文档id
                {field: 'addressName', minWidth: 100, title: '风险点'},
                {field: 'riskDesc', minWidth: 100, title: '风险描述'},
                {field: 'riskType', minWidth: 100, title: '风险类型'},
                {field: 'fileName', minWidth: 100, title: '文件名'},
                // {field: 'status', minWidth: 100, title: '文件是否上传'},
                // {field: 'riskLevel', minWidth: 100, title: '风险等级'},
                {field: 'identifiDate', minWidth: 100, title: '评估日期', templet: function(d){return formateDate(d.identifiDate)}},
                {field: 'expDate', minWidth: 100, title: '解除日期', templet: function(d){return formateDate(d.expDate)}},
                {title: '操作', minWidth: 300, templet: '#currentTableBar', fixed: "right", align: "center"}
            ]],
            limits: [10, 15, 20, 25, 50, 100],
            limit: 10,
            page: true,
            done: function (res, curr, count) {//返回的数据，当前页，数据总数
            }
        });

        // 绑定表格工具栏按钮的触发事件
        bindTableToolbarFunction();

        // 监听搜索操作
        form.on('submit(data-search-btn)', function (data) {
            var result = JSON.stringify(data.field);
//            layer.alert(result, {
//                title: '最终的搜索信息'
//            });
            //执行搜索重载
            reloadTable(result);
            return false;
        });

        // 监听重置操作
        form.on('submit(data-refresh-btn)', function (data) {
            $(".layui-form-pane")[0].reset();
            reloadTable(null);
        });

        table.on('tool(currentTableFilter)', function (obj) {
            var data = obj.data;
            if (obj.event === 'edit') {
                if (data.sfPlanInfoId == null) {
                    layer.msg("未上传文件禁止编辑", {icon: 0, time: 1800})
                    return;
                }
                var index = layer.open({
                    title: '编辑',
                    type: 2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                    shade: 0.2,
                    maxmin: false,
                    area: ['80%', '80%'],
                    content: 'sfPlanInfoController.do?addorupdate&id=' + data.sfPlanInfoId,
                    btn: ['提交', '取消'],
                    yes: function (index, layero) {
                        var body = layer.getChildFrame('body', index); //得到iframe页面层的BODY
                        var iframeBtn = body.find('#btn');//得到iframe页面层的提交按钮
                        iframeBtn.click();//模拟iframe页面层的提交按钮点击
                    },
                    btn2: function () {
                        layer.closeAll();
                    }
                });
                return false;
            } else if (obj.event === 'delete') {
                if (data.sfPlanInfoId == null) {
                    layer.msg("未上传文件不能删除", {icon: 0, time: 1800})
                    return;
                }
                layer.confirm("真的删除对应文件吗？", {icon: 3, title: "确认"}, function (index) {
                    $.post("sfPlanInfoController.do?batchDel", {ids: data.sfPlanInfoId}, function (result) {
                        layer.msg("删除成功", {icon: 1, time: 1800});
                        var data = form.val("searchForm");
                        reloadTable(JSON.stringify(data));
                    });
                });
            }else if (obj.event==='add'){
                // if (data.sfPlanInfoId != null) {
                //     layer.msg("未上传文件不能删除", {icon: 0, time: 1800})
                //     return;
                // }
                var index = layer.open({
                    title: '添加重大风险管控方案',
                    type: 2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）。
                    shade: 0.2,
                    maxmin: false,
                    area: ['80%', '80%'],
                    content: 'sfPlanInfoController.do?addorupdate&riskCode='+ data.id,
                    btn: ['提交', '取消'],
                    yes: function (index, layero) {
                        var body = layer.getChildFrame('body', index); //得到iframe页面层的BODY
                        var iframeBtn = body.find('#btn');//得到iframe页面层的提交按钮
                        iframeBtn.click();//模拟iframe页面层的提交按钮点击
                    },
                    btn2: function () {
                        layer.closeAll();
                    }
                });
            }else if (obj.event === 'download') {
                window.location.href = "fileController.do?downloadFile&fileId="+data.fileId
            }else if(obj.event === 'preview'){
                parent.layer.open({
                    type: 2,
                    shade: 0,
                    area: ['1000px','600px'],
                    title: "预览",
                    content:"fileController.do?previewFile&fileId="+data.fileId,
                    btn: ['关闭']
                });
            }
        });

        // 绑定表格工具栏按钮的触发事件
        function bindTableToolbarFunction() {
            // 监听添加操作
            $(".data-add-btn").on("click", function () {
                var checkStatus = table.checkStatus('currentTableId');
                var data = checkStatus.data;
                if (data.length == 0 || data.length > 1) {
                    layer.msg("请选择一项再操作", {icon: 0, time: 1800})
                    return;
                }
                var index = layer.open({
                    title: '添加重大风险管控方案',
                    type: 2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）。
                    shade: 0.2,
                    maxmin: false,
                    area: ['80%', '80%'],
                    content: 'sfPlanInfoController.do?addorupdate&riskCode='+ data[0].id,
                    btn: ['提交', '取消'],
                    yes: function (index, layero) {
                        var body = layer.getChildFrame('body', index); //得到iframe页面层的BODY
                        var iframeBtn = body.find('#btn');//得到iframe页面层的提交按钮
                        iframeBtn.click();//模拟iframe页面层的提交按钮点击
                    },
                    btn2: function () {
                        layer.closeAll();
                    }
                });
                //layer.full(index);
                return false;
            });

            // 监听编辑操作
            $(".data-edit-btn").on("click", function () {
                var checkStatus = table.checkStatus('currentTableId');
                var data = checkStatus.data;
                if (data.length == 0 || data.length > 1) {
                    layer.msg("请选择一项再操作", {icon: 0, time: 1800})
                    return;
                }
                var index = layer.open({
                    title: '编辑重大风险管控方案',
                    type: 2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）。
                    shade: 0.2,
                    maxmin: false,
                    area: ['50%', '50%'],
                    content: 'sfPlanInfoController.do?addorupdate&riskId=' + data[0].id,
                    btn: ['提交', '取消'],
                    yes: function (index, layero) {
                        var body = layer.getChildFrame('body', index); //得到iframe页面层的BODY
                        var iframeBtn = body.find('#btn');//得到iframe页面层的提交按钮
                        iframeBtn.click();//模拟iframe页面层的提交按钮点击
                    },
                    btn2: function () {
                        layer.closeAll();
                    }
                });
                return false;
            });


            // 监听删除操作
            $(".data-delete-btn").on("click", function () {
                var checkStatus = table.checkStatus('currentTableId');
                var data = checkStatus.data;
                if (data.length == 0) {
                    layer.msg("请至少选择一项", {icon: 0, time: 1800})
                    return;
                }
                var ids = "";
                $.each(data, function (i, val) {
                    if (ids == "") {
                        ids = val.id;
                    } else {
                        ids += "," + val.id;
                    }
                });
                layer.confirm("确认删除勾选的数据？", {icon: 3, title: "确认"}, function (index) {
                    $.post("sfPlanInfoController.do?batchDel", {ids: ids}, function (result) {
                        layer.msg("删除成功", {icon: 1, time: 1800});
                        var data = form.val("searchForm");
                        reloadTable(JSON.stringify(data));
                    });
                });
            });

        }

        function reloadTable(searchParams) {
            table.reload('currentTableId', {
                page: {
                    curr: 1
                }
                , where: {
                    searchParams: searchParams
                },
                done: function (res, curr, count) {
                    //改变行颜色
                    //bindTableColor(res);
                }
            }, 'data');
            // 绑定表格工具栏按钮的触发事件
            bindTableToolbarFunction();
        }

        function formateDate(d){
            if(typeof d === "undefined" || d === null || d === ""){
                return ""
            }else{
                return util.toDateString(d, "yyyy-MM-dd");
            }
        }

        var _tools = {
            tableReload: function () {
                reloadTable(null);
            }
        }
        window.tools = _tools;
    });
</script>