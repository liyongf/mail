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
                            <label class="layui-form-label">培训主题</label>
                            <div class="layui-input-inline">
                                <input type="text" id="trainingTheme" name="trainingTheme" autocomplete="off"
                                       class="layui-input">
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">培训类型</label>
                            <div class="layui-input-inline">
                                <select id="trainingType" name="trainingType" lay-search="">
                                    <option value=""></option>
                                    <c:forEach items="${trainingTypeList}" var="item">
                                        <option value="${item.typecode}">${item.typename}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="layui-inline">
                            <label class="layui-form-label">培训日期</label>
                            <div class="layui-input-inline">
                                <input type="text" name="trainingDate" id="trainingDate" placeholder="请选择"
                                       readonly="" autocomplete="off" class="layui-input">
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
                    <button class="layui-btn layui-btn-radius layui-btn-sm layui-btn-danger data-delete-btn">删除</button>
                </c:if>
            </div>
        </script>

        <table class="layui-hide" id="currentTableId" lay-filter="currentTableFilter"></table>

        <script type="text/html" id="currentTableBar">
            <a class="layui-btn layui-btn-xs data-count-edit" lay-event="edit">编辑</a>
            <a class="layui-btn layui-btn-xs layui-btn-normal data-count-download" lay-event="download">下载</a>
            <a class="layui-btn layui-btn-xs layui-btn-normal data-count-preview" lay-event="preview">预览</a>
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
            elem: '#trainingDate',
            trigger:'click'
            //range: '~',//时间范围
            //calendar: true  //开启公历节日
        });

        table.render({
            elem: '#currentTableId',
            url: 'sfTrainingInfoController.do?datagrid',
            toolbar: '#toolbarDemo',
            defaultToolbar: [],
            cols: [[
                {type: "checkbox", width: 50, fixed: "left"},
                {field: 'id', width: 80, title: 'ID', hide: true},
                {field: 'stateFlag', width: 100, title: '矿端数据操作标识', hide: true},
                {field: 'trainingType', minWidth: 100, title: '培训类型'},
                {field: 'trainingDate', minWidth: 100, title: '培训日期', templet: function(d){return formateDate(d.trainingDate)}},
                {field: 'trainingTheme', minWidth: 100, title: '培训主题'},
                {field: 'trainingPerson', minWidth: 100, title: '培训人'},
                {field: 'trainingDepart', minWidth: 100, title: '组织部门'},
                // {field: 'fileId', width: 100, title: '文件id'},
                // {field: 'fileName', minWidth: 100, title: '培训课件、签到表、现场图片等记录材料'},
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
                var index = layer.open({
                    title: '编辑',
                    type: 2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
                    shade: 0.2,
                    maxmin: false,
                    area: ['80%', '80%'],
                    content: 'sfTrainingInfoController.do?addorupdate&id=' + data.id,
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
                layer.confirm('真的删除行么', function (index) {
                    obj.del();
                    layer.close(index);
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
                var index = layer.open({
                    title: '添加培训档案',
                    type: 2,//0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）。
                    shade: 0.2,
                    maxmin: false,
                    area: ['80%', '80%'],
                    content: 'sfTrainingInfoController.do?addorupdate',
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

            // 监听删除操作
            $(".data-delete-btn").on("click", function () {
                var checkStatus = table.checkStatus('currentTableId');
                var data = checkStatus.data;
                if (data.length == 0) {
                    layer.msg("请至少选择一项", {icon: 0, time: 1800})
                    return;
                }
//            layer.alert(JSON.stringify(data));
                var ids = "";
                $.each(data, function (i, val) {
                    if (ids == "") {
                        ids = val.id;
                    } else {
                        ids += "," + val.id;
                    }
                });
                layer.confirm("确认删除勾选的数据？", {icon: 3, title: "确认"}, function (index) {
                    $.post("sfTrainingInfoController.do?batchDel", {ids: ids}, function (result) {
//                        layer.alert(JSON.stringify(result));
//                        layer.close(index);
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