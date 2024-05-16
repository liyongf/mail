<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
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
                            <label class="layui-form-label">所属图层</label>
                            <div class="layui-input-inline">
                                <select name="layerId" lay-search="">
                                    <option value="">选择或搜索选择</option>
                                    <c:forEach items="${layerList}" var="item">
                                        <option data="${item.id}" value="${item.id}">${item.layerName}</option>
                                    </c:forEach>
                                </select>
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
                <c:if test="${roleOpt.contains('delete') || roleOpt eq ''}">
                    <button class="layui-btn layui-btn-radius layui-btn-sm layui-btn-danger data-delete-btn">批量删除                  </button>
                </c:if>
            </div>
        </script>

        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>

        <script type="text/html" id="currentTableBar">
            <c:if test="${roleOpt.contains('edit') || roleOpt eq ''}">
                <a class="layui-btn layui-btn-xs layui-btn-warm data-count-edit" lay-event="edit">编辑</a>
            </c:if>
            <c:if test="${roleOpt.contains('delete') || roleOpt eq ''}">
                <a class="layui-btn layui-btn-xs layui-btn-danger data-count-delete" lay-event="delete">删除</a>
            </c:if>
        </script>

    </div>
</div>
<script>
    layui.use(['form', 'table'], function () {
        var $ = layui.jquery,
            form = layui.form,
            util = layui.util,
            table = layui.table;

        table.render({
            elem: '#currentTableId',
            url: 'sFPictureInfoController.do?data&type=1',
            toolbar: '#toolbarDemo',
            defaultToolbar: [],
            cols: [[
                {type: "checkbox", fixed: "left"},
                {field: 'id', title: 'ID', hide: true},
                {field: 'fileTypeName',width:200, title: '文件类型'},
                {field: 'layerName',width:200, title: '所属图层'},
                {field: 'fileName',width:300, title: '图形文件名称'},
                {field: 'fileRealPath',width:300, title: '文件路径'},
                {field: 'createDate', width: 150, title: '创建时间', templet: function(d){return formateDate(d.createDate)}},
                {field: 'updateDate', width: 150, title: '更新时间', templet: function(d){return formateDate(d.updateDate)}},
                {title: '操作', minWidth: 150, templet: '#currentTableBar', fixed: "right", align: "center"}
            ]],
            limits: [10, 15, 20, 25, 50, 100],
            limit: 10,
            page: true
        });

        bindEvent();

        function formateDate(d){
            if(typeof d === "undefined" || d === null || d === ""){
                return ""
            }else{
                return util.toDateString(d, "yyyy-MM-dd");
            }
        }

        // 监听搜索操作
        form.on('submit(data-search-btn)', function (data) {
            var result = JSON.stringify(data.field);
            //执行搜索重载
            reloadTable(result);
            return false;
        });

        // 监听重置操作
        form.on('submit(data-refresh-btn)', function (data) {
            $(".layui-form-pane")[0].reset();
            reloadTable(null);
        });

        function reloadTable(result) {
            table.reload('currentTableId', {
                page: {
                    curr: 1
                }
                , where: {
                    searchParams: result
                }
            }, 'data');

            bindEvent();
        }

        function bindEvent() {

            // 监听添加操作
            $(".data-add-btn").on("click", function () {
                layer.open({
                    type: 2
                    , title: '添加'
                    , area: ['80%', '80%']
                    , shade: 0
                    , maxmin: true
                    , content: 'sFPictureInfoController.do?addorupdateLayui&type=1'
                    , btn: ['提交', '取消'] //只是为了演示
                    , yes: function (index, layero) {
                        var body = layer.getChildFrame('body', index);
                        var iframeBtn = body.find('#btn');
                        iframeBtn.click();
                    }
                    , btn2: function () {
                        layer.closeAll();
                    }
                });
                return false;
            });

            // 监听删除操作
            $(".data-delete-btn").on("click", function () {
                layer.confirm('真的批量删除这些记录吗', function (index) {
                    var checkStatus = table.checkStatus('currentTableId')
                    var data = checkStatus.data;
                    if (data.length == 0) {
                        layer.msg("请至少选择一项", {icon: 0, time: 1800})
                        return;
                    }
                    var ids = '';
                    for (i = 0; i < data.length; i++) {
                        var row = data[i];
                        ids += row.id + ',';
                    }
                    $.ajax({
                        async: false,
                        type: 'GET',
                        data: {ids: ids},
                        url: 'sFPictureInfoController.do?batchDel',
                        success: function (data) {
                            var d = $.parseJSON(data);
                            layer.msg(d.msg, {icon: d.success == true ? 1 : 2, time: 1800});
                            layer.close(index)
                            reloadTable()
                        }
                    });
                });
            });

            table.on('tool(currentTableFilter)', function (obj) {
                var id = obj.data.id;
                if (obj.event === 'delete') {
                    layer.confirm('真的删除该行么', function (index) {
                        $.ajax({
                            async: false,
                            type: 'GET',
                            data: {ids: id},
                            url: 'sFPictureInfoController.do?batchDel',// 请求的action路径
                            success: function (data) {
                                var d = $.parseJSON(data);
                                layer.msg(d.msg, {icon: d.success == true ? 1 : 2, time: 1800});
                                layer.close(index)
                                reloadTable()
                            }
                        });
                    });
                } else if (obj.event === 'edit') {
                    var id = obj.data.id;
                    layer.open({
                        type: 2
                        , title: '编辑'
                        , area: ['600px', '500px']
                        , shade: 0
                        , maxmin: true
                        , content: 'sFPictureInfoController.do?addorupdateLayui&type=1&id=' + id
                        , btn: ['提交', '取消'] //只是为了演示
                        , yes: function (index, layero) {
                            var body = layer.getChildFrame('body', index); //得到iframe页面层的BODY
                            var iframeBtn = body.find('#btn');//得到iframe页面层的提交按钮
                            iframeBtn.click();//模拟iframe页面层的提交按钮点击
                        }
                        , btn2: function () {
                            layer.closeAll();
                        }
                    });
                }
            });
        }

        var _tools = {
            tableReload: function () {
                reloadTable();
            }
        }
        window.tools = _tools;
    });


</script>