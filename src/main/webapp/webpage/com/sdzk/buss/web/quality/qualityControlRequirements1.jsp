
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/layui.js"></script>
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/js/treeTable/treeTable.js"></script>
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/js/admin.js"></script>
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/js/formX.js"></script>
<%--
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/js/tableX.js"></script>
--%>
<%--
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/js/contextMenu.js"></script>
--%>

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
<!-- service-content -->
<!-- 正文开始 -->
<!-- 正文开始 -->
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md3">
            <div class="layui-card">
                <div class="layui-card-body" style="height: 690px;">
                    <!-- 表格工具栏 -->
                    <form class="layui-form toolbar">
                        <div class="layui-form-item" style="margin-top: 20px; margin-bottom: 15px;">
                            <div class="layui-inline">
                                <div class="layui-input-inline">
                                    <input id="yearTotalAsses" name="year" class="layui-input" placeholder="请选择年份" readonly="readonly" />
                                </div>
                            </div>
                            <div class="layui-inline" id="duokuang">
                                <div class="layui-input-inline">
                                    <select id="duokuangselect" name="a4" style="width: 250px; height: 30px;"></select>
                                </div>
                            </div>
                            <div class="layui-inline">&emsp;
                                <button id="search" class="layui-btn icon-btn" lay-filter="totalassesTbSearch" lay-submit>
                                    <i class="layui-icon">&#xe615;</i>搜索
                                </button>
                            </div>
                        </div>
                    </form>
                    <!-- 数据表格 -->
                    <table id="totalassesTable" hebing="111" lay-filter="totalassesTable"></table>
                </div>
            </div>
        </div>
        <div class="layui-col-md9">
            <div class="layui-card">
                <div class="layui-card-body" id="qualityControlModScriptTable1" style="height: 690px;  overflow:auto">
                    <!-- 数据表格 -->
                    <table id="qualitycontrolModuleTableView1" lay-filter="qualitycontrolModuleTableView1" class="layui-table" style="table-layout: fixed; margin-top: 15px;">
                        <colgroup>
                            <col style="width: 5%;">
                            <col style="width: 10%;">
                            <col style="width: 17%;">
                            <col style="width: 10%;">
                            <col style="width: 10%;">
                            <col style="width: 10%;">
                            <col style="width: 10%;">
                            <col style="width: 28%;">
                        </colgroup>
                        <thead>
                        <tr>
                            <th style="text-align: center;">序号</th>
                            <th colspan="2" style="text-align: center;">管理要素</th>
                            <th style="text-align: center;">标准分值</th>
                            <th style="text-align: center;">权重（ai）</th>
                            <th style="text-align: center;">打分</th>
                            <th style="text-align:center;">得分</th>
                            <th style="text-align:center;">操作</th>
                        </tr>
                        </thead>
                        <script id="QualityControlModuleList1" type="text/html">
                            <form class="layui-form toolbar" lay-filter="QualityControlRequirementsForm">
                                {{# layui.each(d, function(index, item){ }}
                                <tr>
                                    <!-- <td><a href="{{item.dispatchUrl}}" target="_blank">{{item.projectName1}}</a></td> -->
                                    <td style="text-align:center;" class="text-center">{{item.num}}</td>
                                    <td class="text-center">{{item.moduleName1}}</td>
                                    <td class="text-center">{{item.moduleName}}</td>
                                    <td style="text-align:center;" class="text-center">{{item.score}}</td>
                                    <td style="text-align:center;" class="text-center">{{item.theWeight}}</td>
                                    <td style="text-align:center;" class="text-center">{{item.playScore}}</td>
                                    <td style="text-align:center;" class="text-center">{{item.scoreTa}}</td>
                                    <td style="text-align:center;" class="text-center">
                                        {{# if(item.status == 1){ }}
                                        <a class="layui-btn layui-btn-xs icon-btn" href="qualityControlRequirements.do?goCheck&moduleIds={{item.idList}}&totalAssesId={{item.totalAssesId}}&status=1&score={{item.score}}&theWeight={{item.theWeight}}">开始打分</a>
                                        <button class="layui-btn layui-btn-xs icon-btn onclickCalc" totalAssesId="{{item.totalAssesId}}" moduleIds="{{item.idList}}">计算得分</button>
                                        <button class="layui-btn layui-btn-xs icon-btn onclickDeat" totalAssesId="{{item.totalAssesId}}" moduleIds="{{item.idList}}">结束评分</button> {{# } }} {{# if(item.status == 2){ }}
                                        <a class="layui-btn layui-btn-xs icon-btn" href="qualityControlRequirements.do?goCheck&moduleIds={{item.idList}}&totalAssesId={{item.totalAssesId}}&status=2">查看</a> {{# } }}
                                    </td>
                                </tr>
                                {{# }); }} {{# if(d.length === 0){ }} 无数据 {{# } }}
                            </form>
                        </script>
                        <tbody id="QualityControlModuleListView1">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    layui.use(['layer', 'form', 'table', 'util', 'admin',  'laydate', 'formX'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var table = layui.table;
        var util = layui.util;
        var admin = layui.admin;
        var laydate = layui.laydate;
        var formX = layui.formX;
        //var ssss=<%=request.getAttribute("csmk")%>
        var duokuangcode;


        var date = new Date();
        var yearDate = date.getFullYear(); //获取完整的年份(4位)
        var monthDate = '0' + (date.getMonth() + 1); //获取当前月份(0-11,0代表1月)
        var duokuang = "";
        //var dataduokuang=<%=request.getAttribute("list")%>
        var duokuangFlag = false;//是否多矿
        var baseServer = '';
        var dataduokuang = [{"codDesc":"阳城煤矿","code":"ycmk"},{"codDesc":"金桥煤矿","code":"jqmk"},{"codDesc":"花园煤矿","code":"hymk"},
                             {"codDesc":"运河煤矿","code":"yhmk"},{"codDesc":"霄云煤矿","code":"xymk"},{"codDesc":"安居煤矿","code":"ajmk"},
                             {"codDesc":"义桥煤矿","code":"yqmk"},{"codDesc":"金源煤矿","code":"jymk"}];
        // 数据方式
        formX.renderSelect({
            elem: "#duokuangselect",
            data: dataduokuang,
            name: 'codDesc',
            value: 'code',
            hint: '请选择',
            initValue: '',
            done: function() {}
        });
        if(!duokuangFlag) {
            $("#duokuang").addClass("layui-hide");
        }

        /* 渲染时间选择 */
        laydate.render({
            elem: '#yearTotalAsses',
            type: 'year',
            range: false,
            trigger: 'click'
        });
        /* 渲染表格 */
        var insTb = table.render({
            elem: '#totalassesTable',
            url: baseServer + 'totalAsses/findByPage.do',
            where:{dataduokuang: JSON.stringify(dataduokuang),  duokuangFlag: duokuangFlag},
            method: 'POST',
            page: false, //开启分页
            parseData: function(res) { //res 即为原始返回的数据
                if (res.code == 200) {
                    res.code = 0;
                    return {
                        "code": res.code, //解析接口状态
                        "msg": res.msg, //解析提示文本
                        "count": res.datas.total, //解析数据长度
                        "data": res.datas.list //解析数据列表
                    };
                }
            },
            defaultToolbar: [],
            cellMinWidth: 100,
            height: '520',
            toolbar: ['<p>',
                '<button lay-event="selectShuaXin" class="layui-btn layui-btn-sm icon-btn" style="color: white ;background-color: lightskyblue;">计算</button>&nbsp;',
                '<button lay-event="edityearCz" class="layui-btn layui-btn-sm icon-btn">重置</button>',
                '</p>'
            ].join(''),
            defaultToolbar: [],
            cols: [
                [{
                    field: 'year',
                    title: '年份',
                    width: 100,
                    align: 'center',
                    sort: false,
                    unsize: true
                }, {
                    field: 'month',
                    title: '季度',
                    width: 100,
                    align: 'center',
                    sort: false,
                    unsize: true
                },

                    {
                    field: 'scores',
                    title: '得分',
                    align: 'center',
                    sort: false,
                    unsize: true
                }]
            ],
            done: function(res, curr, count) {

                $('#totalassesTable').next().find('.layui-table-body').find("table").find("tbody").children("tr").each(function(index, val) {
                    $($(".layui-table-fixed .layui-table-body tbody tr")[index])
                        .height($(
                            val).height('100'));
                });
                // 合并纵行
                merges('totalassesTable', [0]);
                $('#totalassesTable+.layui-table-view .layui-table-body tbody>tr').eq(${jdTemp})
                    .trigger('click');
            }
        });


        /* 合并相同单元格 */
        function merges(tableId, indexs, fields, sort) {
            if (typeof fields === 'boolean') {
                sort = fields;
                fields = undefined;
            }
            var $tb = $('[lay-filter="' + tableId + '"]+.layui-table-view>.layui-table-box>.layui-table-body>table');
            var $trs = $tb.find('>tbody>tr');
            // 循环合并每一列
            for (var i = 0; i < indexs.length; i++) {
                if (fields) {
                    merge(tableId, indexs[i], fields[i]);
                } else {
                    merge(tableId, indexs[i]);
                }
            }
            $trs.find('[del="true"]').remove();  // 移除多余的单元格
            // 监听排序事件
            if (sort === undefined || sort) {
                table.on('sort(' + tableId + ')', function () {
                    merges(tableId, indexs, fields, false);
                });
            }

            // 合并一列
            function merge(tableId, index, field) {
                var data = table.cache[tableId];
                if (data.length > 0) {
                    var lastValue, spanNum = 1;
                    if (field) {
                        lastValue = data[0][field];
                    } else {
                        lastValue = $trs.eq(0).find('td').eq(index).find('.layui-table-cell').html();
                    }
                    for (var i = 1; i < data.length; i++) {
                        var currentValue;
                        if (field) {
                            currentValue = data[i][field];
                        } else {
                            currentValue = $trs.eq(i).find('td').eq(index).find('.layui-table-cell').html();
                        }
                        if (currentValue === lastValue) {
                            spanNum++;
                            if (i === data.length - 1) {
                                $trs.eq(i - spanNum + 1).find('td').eq(index).attr('rowspan', spanNum);
                                for (var j = 1; j < spanNum; j++) {
                                    $trs.eq(i - j + 1).find('td').eq(index).attr('del', 'true');
                                }
                            }
                        } else {
                            $trs.eq(i - spanNum).find('td').eq(index).attr('rowspan', spanNum);
                            for (var k = 1; k < spanNum; k++) {
                                $trs.eq(i - k).find('td').eq(index).attr('del', 'true');
                            }
                            spanNum = 1;
                            lastValue = currentValue;
                        }
                    }
                }
            }
        };




        /* 表格搜索 */
        form.on('submit(totalassesTbSearch)', function(data) {
            data.field.dataduokuang = JSON.stringify(dataduokuang);
            data.field.duokuangFlag = JSON.stringify(duokuangFlag);
            insTb.reload({
                where: data.field,
            });
            return false;
        });
        /* 表格头工具栏点击事件 */
        table.on('toolbar(totalassesTable)', function(obj) {
            if (obj.event === 'edityearCz') { // 重置
                admin.req(baseServer + 'totalAsses/deleteByDate.do',{dataduokuang: JSON.stringify(dataduokuang),  duokuangFlag: duokuangFlag}, function(res) {
                    if (200 == res.code) {
                        layer.msg(res.msg, {
                            icon: 1
                        });
                        insTb.reload({});
                    } else {
                        layer.msg(res.msg, {
                            icon: 2
                        });
                    }
                }, 'get');
            } else if (obj.event === 'selectShuaXin') {
                insTb.reload({});
            }
        });

        /* 监听行单击事件 */
        table.on('row(totalassesTable)', function(obj) {
            obj.tr.addClass('layui-table-click').siblings().removeClass('layui-table-click');
            yearDate = obj.data.year;
            monthDate = obj.data.month;
            if (duokuangFlag) {
                duokuangcode =  obj.data.a4;
            }
            renderData(yearDate, monthDate,duokuangcode)
        });

        function calc(totalAssesId, moduleIds) {
            admin.req(baseServer + 'detailsAsses/calculateScore.do', {
                totalAssesId: totalAssesId,
                moduleId: moduleIds
            }, function(res) {
                if (200 == res.code) {
                    renderData(yearDate, monthDate,duokuangcode)
                } else {
                    layer.msg(res.msg, {
                        icon: 2
                    });
                }
            }, 'POST')
        }

        /*计算分数*/
        function onclickCalc() {
            $('.onclickCalc').click(function() {
                //获取id
                var totalAssesId = this.attributes.totalAssesId.value;
                //表id
                var moduleIds = this.attributes.moduleIds.value;
                calc(totalAssesId, moduleIds);
            })
        }

        /*结束打分 并计算分数*/
        function onclickDeat() {
            $('.onclickDeat').click(function() {
                //获取id
                var totalAssesId = this.attributes.totalAssesId.value;
                //表id
                var moduleIds = this.attributes.moduleIds.value;
                layer.confirm('确定要结束打分吗？', {
                    skin: 'layui-layer-admin',
                    shade: .1
                }, function(i) {
                    admin.req(baseServer + 'totalAsses/update.do', {
                        id: totalAssesId,
                        a2: 2
                    }, function(res) {
                        calc(totalAssesId, moduleIds);
                        if (200 == res.code) {
                            layer.msg(res.msg, {
                                icon: 1
                            });
                            renderData(yearDate, monthDate,duokuangcode)
                        } else {
                            layer.msg(res.msg, {
                                icon: 2
                            });
                        }
                    }, 'post')
                })
            })
        }

        /*动态渲染复杂表格*/
        function renderData(data1, data2,duokuangcode) {
            admin.req(baseServer + 'qualityControlModule/findByList.do', {
                year: data1,
                month: data2,
                duokuangCode: duokuangcode
            }, function(data) {
                var html = admin
                    .util
                    .tpl($('#QualityControlModuleList1').html(), data.datas);
                $("#QualityControlModuleListView1").html(html);
                colSpan('qualitycontrolModuleTableView1')
                autoRowSpan();
                onclickDeat();
                onclickCalc();
            }, 'POST');
        }

        /*表格合并*/
        function colSpan(tableId) {
            var maxRow = 3,
                val, count, start, valtd;
            count = 1;
            val = "";
            var $tb = $('#qualityControlModScriptTable1'); //获取目标tabel
            var $trs = $("#QualityControlModuleListView1>tr");
            for (var i = 0; i < $trs.length; i++) { //遍历所有行
                for (var col = maxRow - 1; col >= 1; col--) { //遍历每一行的每一个td

                    if (val == $($trs[i]).children('td').eq(col).html()) { //判断前一个td和后一个td是否相同
                        count++;
                        $trs[i].cells[col].colSpan = count;
                        var wid = 0;
                        for (var j = 1; j < count; j++) {
                            wid += parseInt($($trs[i].cells[j + col]).width())
                            $trs[i].cells[j + col].style.display = "none"; //相邻的有相同的值，第一个设置colspan，后面的隐藏
                        }
                        $trs[i].cells[col].width = parseInt($($trs[i].cells[col]).width()) + parseInt(wid);
                    } else {
                        if (count > 1) {
                            count = 1;
                        }
                        val = $($trs[i]).children('td').eq(col).html();
                        valtd = $trs[i].cells[col];
                    }
                }
            }
        }

        function autoRowSpan(row, col, j) {
            var tb = $('#qualityControlModScriptTable1'); //获取目标tabel
            var lastValue = "";
            var maxRow = 3;
            var $trs = $("#QualityControlModuleListView1>tr");
            var rowspan = 1;
            for (var index = maxRow - 1; index >= 0; index--) { //遍历每一行的每一个td
                if ($trs.length > 0) {
                    var lastValue, spanNum = 1;
                    lastValue = $trs.eq(0).find('td').eq(index).html();
                    for (var i = 1; i < $trs.length; i++) {
                        var currentValue = $trs.eq(i).find('td').eq(index).html();
                        if (currentValue === lastValue) {
                            spanNum++;
                            if (i === $trs.length - 1) {
                                $trs.eq(i - spanNum + 1).find('td').eq(index).attr('rowspan', spanNum);
                                for (var j = 1; j < spanNum; j++) {
                                    $trs.eq(i - j + 1).find('td').eq(index).attr('del', 'true');
                                }
                            }
                        } else {
                            $trs.eq(i - spanNum).find('td').eq(index).attr('rowspan', spanNum);
                            for (var k = 1; k < spanNum; k++) {
                                $trs.eq(i - k).find('td').eq(index).attr('del', 'true');
                            }
                            spanNum = 1;
                            lastValue = currentValue;
                        }
                    }
                }
                $trs.find('[del="true"]').remove(); // 移除多余的单元格
            }
        }
        $(function() {
            if(${not empty year}){
                $('#yearTotalAsses').val(${year});
                $('#search').click();
            }
        });
    });


</script>
</body>
</html>