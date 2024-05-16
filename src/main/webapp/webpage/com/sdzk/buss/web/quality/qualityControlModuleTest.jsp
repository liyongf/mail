
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/layui.js"></script>
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/lay/modules/tree.js"></script>
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/js/treeTable/treeTable.js"></script>
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/js/admin.js"></script>
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/js/formX.js"></script>

    <link rel="stylesheet" type="text/css" href="webpage/com/sdzk/buss/web/quality/layui/css/layui.css">
<%--
    <link rel="stylesheet" type="text/css" href="webpage/com/sdzk/buss/web/quality/static/css/main.css">
--%>
    <!--加载meta IE兼容文件-->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

</head>
<body>
<!-- about-content -->
<!-- 正文开始 -->
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-body" style="padding: 10px;">
                    <h3 style="margin: 10px;">质量管控模块列表</h3>
                    <button id="qualitycontrolmodulAddTreeLeft" class="layui-btn layui-btn-sm icon-btn"><i
                            class="layui-icon">&#xe654;</i>添加</button>
                    <!-- 左数 -->
                    <div id="qualitycontrolmoduleTreeLeft" style="height: 615px; overflow: auto; margin-top: 10px;">
                        <ul id="toolbarTreeLeft" class="dtree" data-id="0"></ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-body" style="padding: 10px;">
                    <h3 style="margin: 10px;">质量管控项目</h3>
                    <button id="qualitycontrolmodulAddTree" class="layui-btn layui-btn-sm icon-btn"><i
                            class="layui-icon">&#xe654;</i>添加</button>
                    <!-- 中树 -->
                    <div id="qualitycontrolmoduleTree" style="height: 615px; overflow: auto; margin-top: 10px;">
                        <ul id="toolbarTree1" class="dtree" data-id="0"></ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-md6" id="qualitycontrolmoduleForm">
            <div class="layui-card">
                <div class="layui-card-body" style="padding: 10px;">
                    <h3 style="margin: 10px;">质量管控详情</h3>
                    <!-- 数据表格 -->
                    <table id="qualitycontrolrequirementsTable" lay-filter="qualitycontrolrequirementsTable"></table>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 模块表单弹窗 -->
<script type="text/html" id="qualitycontrolmoduleEditDialog">
    <form id="qualitycontrolmoduleEditForm" lay-filter="qualitycontrolmoduleEditForm" class="layui-form model-form">
        <input name="id" type="hidden" />
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">模块名:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input name="moduleName" placeholder="模块名" class="layui-input" lay-verType="tips" lay-verify="required" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item" id="isAssesDivLeft">
            <label class="layui-form-label layui-form-required">是否考核:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <select name="isAsses">
                    <option value="1" selected="selected">是</option>
                    <option value="2">否</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">煤矿类型:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <select name="typeCode">
                    <option value="1" selected="selected">通用</option>
                    <option value="2">井矿</option>
                    <option value="3">露天煤矿</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">类型:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <select name="a2">
                    <option value="1" selected="selected">模块</option>
                    <option value="2">表</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">分数:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input name="score" placeholder="分数" class="layui-input" lay-verType="tips" value="100" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">权重:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input name="theWeight" placeholder="权重" class="layui-input" lay-verType="tips" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">排序:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input name="sort" placeholder="排序" class="layui-input" lay-verType="tips" lay-verify="required" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item text-right" style="width: 160px;float: right">
            <button class="layui-btn" lay-filter="qualitycontrolmoduleEditSubmit" lay-submit>保存</button>
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
        </div>
    </form>
</script>
<!-- 二级以上树表单弹窗 -->
<script type="text/html" id="qualitycontrolmoduleEditTree1DialogLeft">
    <form id="qualitycontrolmoduleEditTree1FormLeft" lay-filter="qualitycontrolmoduleEditTree1FormLeft" class="layui-form model-form">
        <input id="projectIdLeft" name="id" type="hidden" />
        <div class="layui-form-item" id="projectName1DivLeft">
            <label class="layui-form-label">上级名:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input id="modlueNameParent" placeholder="上级名" class="layui-input" lay-verType="tips" readOnly="readOnly" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">模块名:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input id="moduleName" name="moduleName" placeholder="模块名" class="layui-input" lay-verType="tips" lay-verify="required" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item" id="isAssesModuleLeft">
            <label class="layui-form-label layui-form-required">是否考核:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <select id="isAssesModule" name="isAsses">
                    <option value="">请选择</option>
                    <option value="1" selected="selected">是</option>
                    <option value="2">否</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">煤矿类型:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <select id="typeCodeModule" name="typeCode">
                    <option value="1" selected="selected">通用</option>
                    <option value="2">井矿</option>
                    <option value="3">露天煤矿</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">类型:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <select id="a2LeftModule" name="a2">
                    <option value="1" selected="selected">模块</option>
                    <option value="2">表</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">分数:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input id="scoreModule" name="score" placeholder="分数" class="layui-input" lay-verType="tips" value="" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">权重:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input id="theWeightModule" name="theWeight" placeholder="权重" class="layui-input" lay-verType="tips" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">排序:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input id="sortModule" name="sort" placeholder="排序" class="layui-input" lay-verType="tips" lay-verify="required" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item text-right" style="margin-right: 50px">
            <button class="layui-btn" lay-filter="qualitycontrolmoduleEditTree1Submit" lay-submit>保存</button>
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
        </div>
    </form>
</script>
<!-- 一级树表单弹窗 -->
<script type="text/html" id="qualitycontrolmoduleEditTreeDialog">
    <form id="qualitycontrolmoduleEditTreeForm" lay-filter="qualitycontrolmoduleEditTreeForm" class="layui-form model-form">
        <input name="id" type="hidden" />
        <input id="moduleId" name="moduleId" type="hidden" />
        <div class="layui-form-item">
            <label class="layui-form-label">模块名:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input id="moduleName" name="moduleName" placeholder="模块名" class="layui-input" lay-verType="tips" readOnly="readOnly" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">项目名:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input name="projectName" placeholder="项目名" class="layui-input" lay-verType="tips" lay-verify="required" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item" id="statusFirstDivCenter">
            <label class="layui-form-label layui-form-required">是否考核:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <select name="status">
                    <option value="">请选择</option>
                    <option value="1" selected="selected">是</option>
                    <option value="2">否</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">打分点:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input type="radio" lay-filter="a1CenterFrist" class="a11" name="a11" value="1" title="单点" checked="">
                <input type="radio" lay-filter="a1CenterFrist" class="a11" name="a11" value="2" title="多点">
            </div>
        </div>
        <div class="layui-form-item" id="a2FristDivCenter">
            <label class="layui-form-label layui-form-required">考核方式:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input type="radio" lay-filter="a2CenterFrist" name="a21" class="a21" value="1" title="取最低分" checked="">
                <input type="radio" lay-filter="a2CenterFrist" name="a21" class="a21" value="2" title="取最高分">
                <input type="radio" lay-filter="a2CenterFrist" name="a21" class="a21" value="3" title="取平均分">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">总分数:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input name="totalScore" placeholder="总分数" class="layui-input" lay-verType="tips" lay-verify="required" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">排序:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input name="sort" placeholder="排序" class="layui-input" lay-verType="tips" lay-verify="required" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item text-right" style="width: 160px;float: right">
            <button class="layui-btn" lay-filter="qualitycontrolmoduleEditTreeSubmit" lay-submit>保存</button>
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
        </div>
    </form>
</script>
<!-- 二级以上树表单弹窗 -->
<script type="text/html" id="qualitycontrolmoduleEditTree1Dialog">
    <form id="qualitycontrolmoduleEditTree1Form" lay-filter="qualitycontrolmoduleEditTree1Form" class="layui-form model-form">
        <input id="projectId" name="id" type="hidden" />
        <div class="layui-form-item" id="projectName1Div">
            <label class="layui-form-label">上级名:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input id="projectName1" placeholder="上级名" class="layui-input" lay-verType="tips" readOnly="readOnly" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">项目内容:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input id="projectName2" name="projectName" placeholder="项目内容" class="layui-input" lay-verType="tips" lay-verify="required" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item" id="statusDivCenter">
            <label class="layui-form-label layui-form-required">是否考核:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <select id="statusCenter" name="status">
                    <option value="">请选择</option>
                    <option value="1" selected="selected">是</option>
                    <option value="2">否</option>
                </select>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">打分点:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input type="radio" lay-filter="a1Center" name="a1" value="1" title="单点" checked="">
                <input type="radio" lay-filter="a1Center" name="a1" value="2" title="多点">
            </div>
        </div>
        <div class="layui-form-item" id="a2DivCenter">
            <label class="layui-form-label layui-form-required">考核方式:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input type="radio" lay-filter="a2Center" name="a2" value="1" title="取最低分" checked="">
                <input type="radio" lay-filter="a2Center" name="a2" value="2" title="取最高分">
                <input type="radio" lay-filter="a2Center" name="a2" value="3" title="取平均分">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label layui-form-required">排序:</label>
            <div class="layui-input-block" style="margin-right: 50px">
                <input id="sort2" name="sort" placeholder="排序" class="layui-input" lay-verType="tips" lay-verify="required" autocomplete="off" />
            </div>
        </div>
        <div class="layui-form-item text-right" style="margin-right: 50px">
            <button class="layui-btn" lay-filter="qualitycontrolmoduleEditTree1Submit" lay-submit>保存</button>
            <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
        </div>
    </form>
</script>
<!-- 基本要求表单弹窗 -->
<script type="text/html" id="qualitycontrolrequirementsEditDialog">
    <form id="qualitycontrolrequirementsEditForm" lay-filter="qualitycontrolrequirementsEditForm" class="layui-form model-form">
        <input name="id" type="hidden" />
        <table>
            <tr>
                <td colspan="2">
                    <div class="layui-form-item">
                        <label class="layui-form-label layui-form-required">上级名:</label>
                        <div class="layui-input-block" style="margin-right: 50px">
                            <input id="parentName" placeholder="上级名" class="layui-input" lay-verType="tips" lay-verify="required" readOnly="readOnly" />
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="layui-form-item">
                        <label class="layui-form-label layui-form-required">层级</label>
                        <div class="layui-input-block" style="margin-right: 50px">
                            <select id="qualitycontrolrequirementsLevel" name="level" lay-verify="required"></select>
                        </div>
                    </div>
                </td>
                <td>
                    <div id="qualitycontrolrequirementsLevelTypeDiv" class="layui-form-item layui-hide">
                        <label class="layui-form-label layui-form-required">层级类型</label>
                        <div class="layui-input-block" style="margin-right: 50px">
                            <select id="qualitycontrolrequirementsLevelType" name="levelType"></select>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="layui-form-item">
                        <label class="layui-form-label">基本要求:</label>
                        <div class="layui-input-block" style="margin-right: 50px;">
                              <textarea class="layui-textarea" id="demoEditor" style="display: none;">
                          </textarea>
                        </div>
                    </div>
                </td>
                <td>
                    <div class="layui-form-item">
                        <label class="layui-form-label layui-form-required">评分方法:</label>
                        <div class="layui-input-block" style="margin-right: 50px">
                              <textarea class="layui-textarea" id="demoEditor11" style="display: none">
                            </textarea>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="layui-form-item">
                        <label class="layui-form-label layui-form-required">标准分值:</label>
                        <div class="layui-input-block" style="margin-right: 50px">
                            <input name="standardScore" placeholder="标准分值" class="layui-input" lay-verType="tips" lay-verify="required" autocomplete="off" />
                        </div>
                    </div>
                </td>
                <td>
                    <div class="layui-form-item">
                        <label class="layui-form-label layui-form-required">排序:</label>
                        <div class="layui-input-block" style="margin-right: 50px">
                            <input name="a2" placeholder="排序" class="layui-input" lay-verType="tips" lay-verify="required" autocomplete="off" />
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <div class="layui-form-item text-right" style="margin-right: 50px">
                        <button class="layui-btn" lay-filter="qualitycontrolrequirementsEditSubmit" lay-submit>保存</button>
                        <button class="layui-btn layui-btn-primary" type="button" ew-event="closeDialog">取消</button>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</script>


<script type="text/javascript">
    layui.use(["layer","form","table", "util", "tree", "layedit", 'admin', 'formX'], function() {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var util = layui.util;
        var tree = layui.tree;
        var layedit = layui.layedit;
        var admin = layui.admin;
        var formX = layui.formX;
        var radioA1;
        var radioA2;
        var selObj5;
        var selObj2;
        var selObj3;
        var dataCount;
        var insTb1;
        var selObj1, treeData1; // 左树选中数据
        var selObj, treeData; // 中树选中数据
        var isSingle = true;
        var  baseServer = 'http://localhost:8087';
        /* 渲染树形 */
        function renderTreeLeft() {
            admin.req('qualityControlModuleController.do?findByPage', {}, function(res) {
                var data = res.datas;
                console.log(data);
                for (var i = 0; i < data.length; i++) {
                    if (data[i].totalScore != undefined) {
                        data[i].title = data[i].moduleName;
                    } else {
                        data[i].title = data[i].moduleName;
                    }
                    data[i].id = data[i].id;
                    data[i].spread = true;
                }

                treeData1 = layui.treeTable.pidToChildren(data, 'id', 'parentId');
                tree.render({
                    elem: '#qualitycontrolmoduleTreeLeft',
                    onlyIconControl: true,
                    data: treeData1,
                    edit: ['add', 'update', 'del'], //开启操作节点的图标
                    customOperate: true,
                    operate: function(obj) {
                        var type = obj.type; //得到操作类型：add、edit、del
                        var data = obj.data; //得到当前节点的数据
                        var elem = obj.elem; //得到当前节点元素
                        var parentId = data.parentId;
                        var name = data.title;
                        var id = data.id;
                        selObj5 = obj;
                        if (type === 'add') { //增加节点
                            showEditId1ModelLeft()
                        } else if (type === 'update') { //修改节点
                            if (parentId == 0) {
                                showEditModel(obj.data);
                            } else {
                                showEditId1ModelLeft(obj.data)
                            }
                        } else if (type === 'del') { //删除节点
                            doDel(obj);
                        }
                    },
                    click: function(obj) {
                        selObj1 = obj;
                        $('#qualitycontrolmoduleTreeLeft').find('.ew-tree-click')
                            .removeClass(
                                'ew-tree-click');
                        $(obj.elem).children('.layui-tree-entry').addClass('ew-tree-click');
                        isFirst = true;
                        renderTree(obj.data.id);
                    }
                });
                $('#qualitycontrolmoduleTreeLeft').find(
                    '.layui-tree-entry:first>.layui-tree-main>.layui-tree-txt')
                    .trigger('click');
            }, 'post');
        }
        renderTreeLeft();
        var isFirst = false;
        /* 渲染树形 */
        function renderTree(data) {
            admin.req('qualityControlProjectController.do?findByPage', {
                moduleId: data
            }, function(res) {
                var data = res.datas;
                for (var i = 0; i < data.length; i++) {
                    if (data[i].totalScore != undefined) {
                        data[i].title = data[i].projectName + '  (' + data[i].totalScore + '分)';
                    } else {
                        data[i].title = data[i].projectName;
                    }
                    data[i].id = data[i].id;
                    data[i].spread = true;
                }
                treeData = layui.treeTable.pidToChildren(data, 'id', 'parentId');
                tree.render({
                    elem: '#qualitycontrolmoduleTree',
                    onlyIconControl: true,
                    data: treeData,
                    edit: ['add', 'update', 'del'], //开启操作节点的图标
                    customOperate: true,
                    operate: function(obj) {
                        var type = obj.type; //得到操作类型：add、edit、del
                        var data = obj.data; //得到当前节点的数据
                        var elem = obj.elem; //得到当前节点元素
                        var parentId = data.parentId;
                        var name = data.title;
                        var id = data.id;
                        selObj2 = obj;
                        if (type === 'add') { //增加节点
                            showEditId1Model()
                        } else if (type === 'update') { //修改节点
                            if (parentId == 0) {
                                showEditIdModel(obj.data);
                            } else {
                                showEditId1Model(obj.data)
                            }
                        } else if (type === 'del') { //删除节点
                            doDelId(obj);
                        }
                    },
                    click: function(obj) {
                        selObj = obj;
                        $('#qualitycontrolmoduleTree').find('.ew-tree-click').removeClass(
                            'ew-tree-click');
                        $(obj.elem).children('.layui-tree-entry').addClass('ew-tree-click');
                        isFirst = true;
                        insTb1 = table.render({
                            elem: '#qualitycontrolrequirementsTable',
                            url: 'qualityControlRequirementsController.do?findByPage',
                            where: {
                                a1: obj.data.id,
                            },
                            method: 'POST',
                            page: false, //开启分页
                            parseData: function(res) { //res 即为原始返回的数据
                                if (res.code == 200) {
                                    res.code = 0;
                                    return {
                                        "code": res.code, //解析接口状态
                                        "msg": res.msg, //解析提示文本
                                        "count": res.datas.total, //解析数据长度
                                        "data": res.datas //解析数据列表
                                    };
                                }
                            },
                            toolbar: ['<p>',
                                '<button lay-event="addreq" class="layui-btn layui-btn-sm icon-btn"><i class="layui-icon">&#xe654;</i>添加</button>&nbsp;',
                                '<button lay-event="editreq" class="layui-btn layui-btn-sm icon-btn"><i class="layui-icon">&#xe642;</i>修改</button>&nbsp;',
                                '<button lay-event="yesScore" class="layui-btn layui-btn-sm icon-btn">考核</button>&nbsp;',
                                '<button lay-event="noScore" class="layui-btn layui-btn-sm icon-btn">不考核</button>&nbsp;',
                                '<button lay-event="delreq" class="layui-btn layui-btn-sm layui-btn-danger icon-btn"><i class="layui-icon">&#xe640;</i>删除</button>&nbsp;',
                                '<button lay-event="hebing" class="layui-btn layui-btn-sm icon-btn">是附加项</button>',
                                '<button lay-event="qxhebing" class="layui-btn layui-btn-sm icon-btn">非附加项</button>&nbsp;',
                                '</p>'
                            ].join(''),
                            defaultToolbar: [],
                            cellMinWidth: 100,
                            height: 655,
                            cols: [
                                [{
                                    type: 'checkbox'
                                }, {
                                    type: 'numbers',
                                    title: '序号'
                                }, {
                                    field: 'requirements',
                                    title: '基本要求',
                                    sort: false
                                }, {
                                    field: 'standardScore',
                                    title: '标准分值',
                                    width: 100,
                                    sort: false
                                }, {
                                    field: 'scoreMethod',
                                    title: '评分方法',
                                    sort: false
                                }, {
                                    field: 'levelName',
                                    title: '层级',
                                    width: 80,
                                    sort: false
                                }, {
                                    field: 'levelTypeName',
                                    title: '层级类型',
                                    sort: false
                                }, {
                                    field: 'a2',
                                    title: '排序',
                                    sort: false
                                }, {
                                    field: 'a3',
                                    title: '是否考核',
                                    templet: function(d) {
                                        if (d.a3 == 1) {
                                            return '是'
                                        } else {
                                            return '否'
                                        }
                                    },
                                    sort: false
                                }, {
                                    field: 'a4',
                                    title: '是否附加项',
                                    templet: function(d) {
                                        if (d.a4 == 1) {
                                            return '是'
                                        } else {
                                            return '否'
                                        }
                                    },
                                    sort: false
                                }]
                            ],
                            done: function(res, curr, count) {
                                dataCount = count;
                                $('#qualitycontrolrequirementsTable+.layui-table-view .layui-table-body tbody>tr:first')
                                    .trigger('click');
                            }
                        })
                    }
                });
                $('#qualitycontrolmoduleTree').find(
                    '.layui-tree-entry:first>.layui-tree-main>.layui-tree-txt')
                    .trigger('click');
            }, 'post');
        }
        renderTree();
        var isFirst = false;

        /* 表格头工具栏点击事件 */
        table.on('toolbar(qualitycontrolrequirementsTable)', function(obj) {
            if (obj.event === 'addreq') { // 添加
                showEditModelName();
            } else if (obj.event === 'delreq') { // 删除
                var checkRows = table.checkStatus('qualitycontrolrequirementsTable');
                if (checkRows.data.length === 0) {
                    layer.msg('请选择要删除的数据', {
                        icon: 2
                    });
                    return;
                }
                var ids = checkRows.data.map(function(d) {
                    return d.id;
                });
                doDelName({
                    ids: ids
                });
            } else if (obj.event === 'editreq') { // 修改
                var checkRows = table.checkStatus('qualitycontrolrequirementsTable');
                if (checkRows.data.length === 0) {
                    layer.msg('请选择要修改的数据', {
                        icon: 2
                    });
                    return;
                }
                showEditModelName(selObj3.data);
            } else if (obj.event === 'yesScore') { //考核
                var checkRows = table.checkStatus('qualitycontrolrequirementsTable');
                if (checkRows.data.length === 0) {
                    layer.msg('请选择要考核的数据', {
                        icon: 2
                    });
                    return;
                }
                var ids = checkRows.data.map(function(d) {
                    return d.id;
                });
                yesScoreData({
                    ids: ids
                })
            } else if (obj.event === 'noScore') { //不考核
                var checkRows = table.checkStatus('qualitycontrolrequirementsTable');
                if (checkRows.data.length === 0) {
                    layer.msg('请选择要取消考核的数据', {
                        icon: 2
                    });
                    return;
                }
                var ids = checkRows.data.map(function(d) {
                    return d.id;
                });
                noScoreData({
                    ids: ids
                })
            } else if (obj.event === 'hebing') { //是附加项
                var checkRows = table.checkStatus('qualitycontrolrequirementsTable');
                if (checkRows.data.length === 0) {
                    layer.msg('请选择要修改的数据', {
                        icon: 2
                    });
                    return;
                }
                var ids = checkRows.data.map(function(d) {
                    return d.id;
                });
                hebingScore({
                    ids: ids
                })
            } else if (obj.event === 'qxhebing') { //不是附加项
                var checkRows = table.checkStatus('qualitycontrolrequirementsTable');
                if (checkRows.data.length === 0) {
                    layer.msg('请选择要修改的数据', {
                        icon: 2
                    });
                    return;
                }
                var ids = checkRows.data.map(function(d) {
                    return d.id;
                });
                notHebingScore({
                    ids: ids
                })
            }
        });

        /* 监听行单击事件 */
        table.on('row(qualitycontrolrequirementsTable)', function(obj) {
            selObj3 = obj;
            obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
        });

        $('#qualitycontrolmodulAddTreeLeft').on('click', function() {
            showEditModel();
        })
        /* zuo 添加/修改一级 */
        function showEditModel(mData) {
            admin.open({
                type: 1,
                area: '600px',
                title: (mData ? '修改' : '添加'),
                content: $('#qualitycontrolmoduleEditDialog').html(),
                success: function(layero, dIndex) {
                    if (mData) {
                        $('#isAssesDivLeft').removeClass('layui-hide')
                    } else {
                        $('#isAssesDivLeft').addClass('layui-hide')
                    }
                    // 回显表单数据
                    form.val('qualitycontrolmoduleEditForm', mData);
                    // 表单提交事件
                    form.on('submit(qualitycontrolmoduleEditSubmit)', function(data) {
                        var loadIndex = layer.load(2);
                        var url;
                        if (mData) {
                            url = "qualityControlModuleController.do?update";
                        } else {
                            url = "qualityControlModuleController.do?insert";
                            data.field.isAsses = 1;
                        }
                        data.field.parentId = 0;
                        admin.req(url, data.field, function(res) {
                            layer.close(loadIndex);
                            if (200 == res.code) {
                                layer.close(dIndex);
                                layer.msg(res.msg, {
                                    icon: 1
                                });
                                renderTreeLeft();
                            } else {
                                layer.msg(res.msg, {
                                    icon: 2
                                });
                            }
                        },'POST'); // 实际项目可以是mData?'put':'post'
                        return false;
                    });
                }
            });
        }

        /* 按钮的点击事件 */
        form.on('radio(a1CenterFrist)', function(data) {
            if (data.value == 1) {
                $('#a2FristDivCenter').addClass('layui-hide');
            } else {
                $('#a2FristDivCenter').removeClass('layui-hide');
            }
        });

        form.on('radio(a1Center)', function(data) {
            if (data.value == 1) {
                $('#a2DivCenter').addClass('layui-hide');
            } else {
                $('#a2DivCenter').removeClass('layui-hide');
            }
        });

        /* zuo 添加第二级以后的*/
        function showEditId1ModelLeft(mData) {
            admin.open({
                type: 1,
                area: '600px',
                title: (mData ? '修改' : '添加'),
                content: $('#qualitycontrolmoduleEditTree1DialogLeft').html(),
                success: function(layero, dIndex) {
                    if (mData) {
                        $('#projectName1DivLeft').addClass('layui-hide');
                        $('#isAssesModuleLeft').removeClass('layui-hide')
                    } else {
                        $('#isAssesModuleLeft').addClass('layui-hide')
                    }
                    $('#modlueNameParent').val(selObj5.data.moduleName)
                    // 回显表单数据
                    form.val('qualitycontrolmoduleEditTree1FormLeft', mData);
                    // 表单提交事件
                    form.on('submit(qualitycontrolmoduleEditTree1FormLeft)', function(data) {
                        var loadIndex = layer.load(2);
                        var url;
                        if (mData) {
                            url = "qualityControlModuleController.do?update";
                            data.field.id = selObj5.data.id;
                            data.field.isAsses = $('#isAssesModule').val();
                        } else {
                            url = "qualityControlModuleController.do?insert";
                            data.field.parentId = selObj5.data.id;
                            data.field.isAsses = 1;
                        }
                        data.field.moduleName = $('#moduleName').val();
                        data.field.typeCode = $('#typeCodeModule').val();
                        data.field.a2 = $('#a2LeftModule').val();
                        data.field.score = $('#scoreModule').val();
                        data.field.theWeight = $('#theWeightModule').val();
                        data.field.sort = $('#sortModule').val();
                        admin.req(url, data.field, function(res) {
                            layer.close(loadIndex);
                            if (200 == res.code) {
                                layer.close(dIndex);
                                layer.msg(res.msg, {
                                    icon: 1
                                });
                                renderTreeLeft();
                            } else {
                                layer.msg(res.msg, {
                                    icon: 2
                                });
                            }
                        }, 'POST'); // 实际项目可以是mData?'put':'post'
                        return false;
                    });
                }
            });
        }

        /* 删除 */
        function doDel(obj) {
            layer.confirm('确定要删除选中数据吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function(i) {
                layer.close(i);
                var loadIndex = layer.load(2);
                admin.req('qualityControlModuleController.do?deleteById', {
                    id: obj.data ? obj.data.id : '',
                }, function(res) {
                    layer.close(loadIndex);
                    if (200 == res.code) {
                        layer.msg(res.msg, {
                            icon: 1
                        });
                        renderTreeLeft();
                    } else {
                        layer.msg(res.msg, {
                            icon: 2
                        });
                    }
                }, 'post');
            });
        }
        $('#qualitycontrolmodulAddTree').on('click', function() {
            showEditIdModel();
        })
        /*添加/修改第一级树*/
        function showEditIdModel(mData) {
            admin.open({
                type: 1,
                area: '600px',
                title: (mData ? '修改' : '添加'),
                content: $('#qualitycontrolmoduleEditTreeDialog').html(),
                success: function(layero, dIndex) {
                    form.render('radio', 'qualitycontrolmoduleEditTreeForm')
                    $('#moduleId').val(selObj1.data.id);
                    $('#moduleName').val(selObj1.data.moduleName);
                    if (mData) {
                        $('#statusFirstDivCenter').removeClass('layui-hide');
                        $("input:radio[name='a11'][value='" + mData.a1 + "']").attr("checked",
                            true);
                        if (mData.a1 == '2') {
                            $('#a2FristDivCenter').removeClass('layui-hide');
                            $("input:radio[name='a21'][value='" + mData.a2 + "']").attr("checked",
                                true);
                        } else {
                            $('#a2FristDivCenter').addClass('layui-hide');
                        }
                    } else {
                        $('#a2FristDivCenter').addClass('layui-hide');
                        $('#statusFirstDivCenter').addClass('layui-hide')
                    }
                    // 回显表单数据
                    form.val('qualitycontrolmoduleEditTreeForm', mData);
                    // 表单提交事件
                    form.on('submit(qualitycontrolmoduleEditTreeSubmit)', function(data) {
                        var loadIndex = layer.load(2);
                        var url;
                        if (mData) {
                            url = "qualityControlProjectController.do?update";
                        } else {
                            url = "qualityControlProjectController.do?insert";
                        }
                        data.field.parentId = 0;
                        data.field.type = 1;
                        data.field.a1 = $('input:radio[name="a11"]:checked').val();
                        if (data.field.a1 == 2) {
                            data.field.a2 = $('input:radio[name="a21"]:checked').val();
                        } else {
                            data.field.a2 = 0;
                        }
                        admin.req(url, data.field, function(res) {
                            layer.close(loadIndex);
                            if (200 == res.code) {
                                layer.close(dIndex);
                                layer.msg(res.msg, {
                                    icon: 1
                                });
                                renderTree(selObj1.data.id);
                            } else {
                                layer.msg(res.msg, {
                                    icon: 2
                                });
                            }
                        }, 'POST'); // 实际项目可以是mData?'put':'post'
                        return false;
                    });
                }
            });
        }


        /*添加第二级以后的*/
        function showEditId1Model(mData) {
            admin.open({
                type: 1,
                area: '600px',
                title: (mData ? '修改' : '添加'),
                content: $('#qualitycontrolmoduleEditTree1Dialog').html(),
                success: function(layero, dIndex) {
                    form.render('radio', 'qualitycontrolmoduleEditTree1Form')
                    if (mData) {
                        $('#projectName1Div').addClass('layui-hide');
                        $('#statusDivCenter').removeClass('layui-hide')
                        if (mData.a1 == '2') {
                            $('#a2DivCenter').removeClass('layui-hide');
                        } else {
                            $('#a2DivCenter').addClass('layui-hide');
                        }
                    } else {
                        $('#a2DivCenter').addClass('layui-hide');
                        $('#statusDivCenter').addClass('layui-hide')
                    }
                    $('#projectName1').val(selObj2.data.projectName)
                    // 回显表单数据
                    form.val('qualitycontrolmoduleEditTree1Form', mData);
                    // 表单提交事件
                    form.on('submit(qualitycontrolmoduleEditTree1Form)', function(data) {
                        var loadIndex = layer.load(2);
                        var url;
                        if (mData) {
                            url = "qualityControlProjectController.do?update";
                            data.field.id = selObj2.data.id;
                            data.field.status = $('#statusCenter').val();
                        } else {
                            url = "qualityControlProjectController.do?insert";
                            data.field.type = 2;
                            data.field.moduleId = selObj1.data.id;
                            data.field.parentId = selObj2.data.id;
                        }
                        data.field.projectName = $('#projectName2').val();
                        data.field.sort = $('#sort2').val();
                        data.field.a1 = $('input:radio[name="a1"]:checked').val();
                        if (data.field.a1 == 2) {
                            data.field.a2 = $('input:radio[name="a2"]:checked').val();
                        } else {
                            data.field.a2 = 0;
                        }
                        admin.req(url, data.field, function(res) {
                            layer.close(loadIndex);
                            if (200 == res.code) {
                                layer.close(dIndex);
                                layer.msg(res.msg, {
                                    icon: 1
                                });
                                renderTree(selObj1.data.id);
                            } else {
                                layer.msg(res.msg, {
                                    icon: 2
                                });
                            }
                        },'POST'); // 实际项目可以是mData?'put':'post'
                        return false;
                    });
                }
            });
        }

        /* 删除 */
        function doDelId(obj) {
            layer.confirm('确定要删除选中数据吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function(i) {
                layer.close(i);
                var loadIndex = layer.load(2);
                admin.req('qualityControlProjectController.do?deleteById', {
                    id: obj.data ? obj.data.id : '',
                }, function(res) {
                    layer.close(loadIndex);
                    if (200 == res.code) {
                        layer.msg(res.msg, {
                            icon: 1
                        });
                        renderTree(selObj1.data.id);
                    } else {
                        layer.msg(res.msg, {
                            icon: 2
                        });
                    }
                }, 'post');
            });
        }
        /* 显示表单弹窗 */
        function showEditModelName(mData) {
            admin.open({
                type: 1,
                area: '1410px',
                title: (mData ? '修改' : '添加'),
                content: $('#qualitycontrolrequirementsEditDialog').html(),
                success: function(layero, dIndex) {
                    var qualitycontrolrequirementsLevel = '30260001';
                    var qualitycontrolrequirementsLevelType;
                    var index = layedit.build('demoEditor');
                    var index1 = layedit.build('demoEditor11');
                    if (mData) {
                        if (mData.level != undefined) {
                            qualitycontrolrequirementsLevel = mData.level.toString();
                        }
                        if (mData.levelType != undefined) {
                            qualitycontrolrequirementsLevelType = mData.levelType.toString();
                        }
                        layedit.setContent(index, mData.requirements, false);
                        layedit.setContent(index1, mData.scoreMethod, false);
                    }

                    var data3026 = [{"codDesc":"一层","code":"30260001"},{"codDesc":"二层","code":"30260002"}];
                    var data3027 = [{"codDesc":"板式给料机","code":"30270004"},{"codDesc":"润滑系统","code":"30270005"},
                        {"codDesc":"卸料口挡车器","code":"30270002"},{"codDesc":"减速机","code":"30270003"},
                        {"codDesc":"清料","code":"30270001"},{"codDesc":"液压系统","code":"30270006"}];
                    // 数据方式
                    formX.renderSelect({
                        elem: "#qualitycontrolrequirementsLevel",
                        data: data3026,
                        name: 'codDesc',
                        value: 'code',
                        hint: '请选择',
                        initValue: qualitycontrolrequirementsLevel,
                        done: function() {}
                    });

                    // 数据方式
                    formX.renderSelect({
                        elem: "#qualitycontrolrequirementsLevelType",
                        data: data3027,
                        name: 'codDesc',
                        value: 'code',
                        hint: '请选择',
                        initValue: qualitycontrolrequirementsLevelType,
                        done: function() {}
                    });

                    if (qualitycontrolrequirementsLevel == '30260001' ||
                        qualitycontrolrequirementsLevel == undefined) {
                        $('#qualitycontrolrequirementsLevelTypeDiv').addClass('layui-hide');
                        $('#qualitycontrolrequirementsLevelType').val('')
                    } else {
                        $('#qualitycontrolrequirementsLevelTypeDiv').removeClass('layui-hide');
                    }
                    $('#parentName').val(selObj.data.projectName);
                    // 回显表单数据
                    form.val('qualitycontrolrequirementsEditForm', mData);
                    // 表单提交事件
                    form.on('submit(qualitycontrolrequirementsEditSubmit)', function(data) {
                        var loadIndex = layer.load(2);
                        var url;
                        if (mData) {
                            url = "qualityControlRequirementsController.do?update";
                        } else {
                            url = "qualityControlRequirementsController.do?insert";
                            data.field.a3 = 1;
                        }
                        data.field.a1 = selObj.data.id;
                        data.field.requirements = layedit.getContent(index);
                        data.field.scoreMethod = layedit.getContent(index1);
                        admin.req(url, data.field, function(res) {
                            layer.close(loadIndex);
                            if (200 == res.code) {
                                layer.close(dIndex);
                                layer.msg(res.msg, {
                                    icon: 1
                                });
                                insTb1.reload({});
                            } else {
                                layer.msg(res.msg, {
                                    icon: 2
                                });
                            }
                        }, 'POST'); // 实际项目可以是mData?'put':'post'
                        return false;
                    });
                }
            });
        }
        /*下拉菜单的选中事件*/
        $('#qualitycontrolrequirementsLevel').ready(function() {
            // select下拉框选中触发事件
            form.on("select", function(data) {
                if (data.value == '30260001' || data.value == '') {
                    $('#qualitycontrolrequirementsLevelTypeDiv').addClass('layui-hide');
                } else {
                    $('#qualitycontrolrequirementsLevelTypeDiv').removeClass('layui-hide');
                }
            });
        });

        /* 考核 */
        function yesScoreData(obj) {
            layer.confirm('确定要考核选中数据吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function(i) {
                layer.close(i);
                var loadIndex = layer.load(2);
                admin.req('qualityControlRequirementsController.do?updateByIds', {
                    ids: obj.ids ? obj.ids.join(',') : '',
                    a3: '1',
                }, function(res) {
                    layer.close(loadIndex);
                    if (200 == res.code) {
                        layer.msg(res.msg, {
                            icon: 1
                        });
                        insTb1.reload({});
                    } else {
                        layer.msg(res.msg, {
                            icon: 2
                        });
                    }
                }, 'post');
            });
        }

        /* 不考核 */
        function noScoreData(obj) {
            layer.confirm('确定要取消考核选中数据吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function(i) {
                layer.close(i);
                var loadIndex = layer.load(2);
                admin.req('qualityControlRequirementsController.do?updateByIds', {
                    ids: obj.ids ? obj.ids.join(',') : '',
                    a3: '2',
                }, function(res) {
                    layer.close(loadIndex);
                    if (200 == res.code) {
                        layer.msg(res.msg, {
                            icon: 1
                        });
                        insTb1.reload({});
                    } else {
                        layer.msg(res.msg, {
                            icon: 2
                        });
                    }
                }, 'post');
            });
        }
        /*是附加项*/
        function hebingScore(obj) {
            layer.confirm('确定该数据是附加项吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function(i) {
                layer.close(i);
                var loadIndex = layer.load(2);
                admin.req('qualityControlRequirementsController.do?updateA4ByIds', {
                    ids: obj.ids ? obj.ids.join(',') : '',
                    a4: '1',
                }, function(res) {
                    layer.close(loadIndex);
                    if (200 == res.code) {
                        layer.msg(res.msg, {
                            icon: 1
                        });
                        insTb1.reload({});
                    } else {
                        layer.msg(res.msg, {
                            icon: 2
                        });
                    }
                }, 'post');
            });
        }
        /*不是附加项*/
        function notHebingScore(obj) {
            layer.confirm('确定该数据不是附加项吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function(i) {
                layer.close(i);
                var loadIndex = layer.load(2);
                admin.req('qualityControlRequirementsController.do?updateA4ByIds', {
                    ids: obj.ids ? obj.ids.join(',') : '',
                    a4: '',
                }, function(res) {
                    layer.close(loadIndex);
                    if (200 == res.code) {
                        layer.msg(res.msg, {
                            icon: 1
                        });
                        insTb1.reload({});
                    } else {
                        layer.msg(res.msg, {
                            icon: 2
                        });
                    }
                }, 'post');
            });
        }

        /* 删除 */
        function doDelName(obj) {
            layer.confirm('确定要删除选中数据吗？', {
                skin: 'layui-layer-admin',
                shade: .1
            }, function(i) {
                layer.close(i);
                var loadIndex = layer.load(2);
                admin.req('qualityControlRequirementsController.do?deleteById', {
                    id: obj.data ? obj.data.id : '',
                    ids: obj.ids ? obj.ids.join(',') : ''
                }, function(res) {
                    layer.close(loadIndex);
                    if (200 == res.code) {
                        layer.msg(res.msg, {
                            icon: 1
                        });
                        insTb1.reload({});
                    } else {
                        layer.msg(res.msg, {
                            icon: 2
                        });
                    }
                }, 'post');
            });
        }
    });
</script>
</body>
</html>
