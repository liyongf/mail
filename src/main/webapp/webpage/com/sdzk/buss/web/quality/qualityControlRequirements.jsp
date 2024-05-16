
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/layui.js"></script>
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/lay/modules/tree.js"></script>
<%--
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/js/treeTable/treeTable.js"></script>
--%>
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/js/admin.js"></script>
    <script type="text/javascript" src="webpage/com/sdzk/buss/web/quality/layui/js/formX.js"></script>
    <link rel="stylesheet" type="text/css" href="webpage/com/sdzk/buss/web/quality/layui/css/layui.css">

    <!--加载meta IE兼容文件-->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<!-- content -->
<!-- 正文开始 -->
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-inline" style="width: 100%; height: 50px; margin-top: 10px;">
            <span style="line-height: 50px; margin-left: 30px; font-size: 12px;">
                表名:&nbsp;
                <select id="parkID" name="id" style="width: 400px; line-height: 50px; height: 30px;font-size: 12px"></select>
            </span>
            <span class="dafenCode layui-hide" style="line-height: 50px; margin-left: 30px; font-size: 12px;">
                打分点:&nbsp;
                <select id="placeCode" name="placeCode" style="width: 250px; height: 30px;font-size: 12px"></select>
            </span>
            <div class="text-right" style="float: right; margin-right: 30px; margin-top: 10px;">
                <a id="goOutClick" class="layui-btn"
                   style="background-color: lightskyblue; color: white;">&nbsp;&nbsp;&nbsp;返回&nbsp;&nbsp;&nbsp;</a>
            </div>
        </div>
        <div class="layui-card-body" id="qualityControlReqScriptTable" style="margin-top: 10px;">
            <!-- 数据表格 -->
            <table id="qualitycontrolrequirementsTableView" lay-filter="qualitycontrolrequirementsTableView"
                   style="table-layout: fixed; border: 1px solid #dddcdc;">
                <colgroup>
                    <col style="width: 4%;">
                    <col style="width: 5%;">
                    <col style="width: 6%;">
                    <col style="width: 10%;">
                    <col style="width: 25%;">
                    <col style="width: 5%;">
                    <col style="width: 25%;">
                    <col style="width: 5%;">
                    <col style="width: 10%;">
                    <col style="width: 5%;">
                    <col style="width: 5%;">
                </colgroup>
                <thead>
                <tr style="background-color: #f2f2f2;">
                    <th style="text-align: center; border: 1px solid #dddcdc;">项目</th>
                    <th colspan="2" style="text-align: center; border: 1px solid #dddcdc;">项目名称</th>
                    <th colspan="2" style="text-align: center; border: 1px solid #dddcdc;">基本要求</th>
                    <th style="text-align: center; border: 1px solid #dddcdc;">标准分值</th>
                    <th style="text-align: center; border: 1px solid #dddcdc;">评分方法</th>
                    <th style="text-align: center; border: 1px solid #dddcdc;">是否多点</th>
                    <th style="text-align: center; border: 1px solid #dddcdc;">得分</th>
                    <%--<th style="text-align: center; border: 1px solid #dddcdc;">操作</th>--%>
                </tr>
                </thead>
                <script id="QualityControlRequirementsList" type="text/html">
                    <form class="layui-form toolbar" lay-filter="QualityControlRequirementsForm">
                        {{# layui.each(d, function(index, item){ }}
                        <tr>
                            <!-- <td><a href="{{item.dispatchUrl}}" target="_blank">{{item.projectName1}}</a></td> -->
                            <td style="text-align: center; border: 1px solid #dddcdc; padding-left: 25px">
                                <p style="writing-mode:tb-rl">{{item.projectName1}}</p>
                            </td>
                            <td style="text-align: center; border: 1px solid #dddcdc; ">{{item.projectName2}}</td>
                            <td style="text-align: center; border: 1px solid #dddcdc; ">{{item.projectName}}</td>
                            <td style="border: 1px solid #dddcdc; padding: 10px;">{{item.levelTypeName}}</td>
                            <td style="border: 1px solid #dddcdc; padding: 10px;">{{item.requirements}}</td>
                            <td style="text-align: center; border: 1px solid #dddcdc; " a5="{{item.a5}}">
                                {{item.standardScore}}</td>
                            <td style="border: 1px solid #dddcdc; padding: 10px;" a5="{{item.a5}}">{{item.scoreMethod}}
                            </td>
                            {{# if(item.a1 == 1){ }}
                            <td style="text-align: center; border: 1px solid #dddcdc; ">否</td>
                            {{#  } }}
                            {{# if(item.a1 == 2){ }}
                            <td style="text-align: center; border: 1px solid #dddcdc; ; color: red">是</td>
                            {{#  } }}
                            {{# if(item.status == 1){ }}
                            <td style="text-align: center; border: 1px solid #dddcdc; padding:10px;">
                                <input id="scoreDetails{{index}}" qcpA2="{{item.qcpA2}}"
                                       standardScore="{{item.standardScore}}" totalAssesId="{{item.totalAssesId}}"
                                       treeId="{{item.treeId}}" a1="{{item.a1}}" a5="{{item.a5}}" reqId="{{item.id}}"
                                       name="scoreDetails" class="layui-input scoreDetails" placeholder="选择输入分数" value=""
                                       autocomplete="off" />
                               <%-- <input id="{{item.id}}"  value="" type="hidden" />--%>
                            </td>
                           <%-- <td style="text-align: center; border: 1px solid #dddcdc; ">
                                <button qurId="{{item.id}}"
                                        class="layui-btn layui-btn-sm icon-btn qualityControlLuRu"><i
                                        class="layui-icon">&#xe654;</i>录入</button>

                            </td>--%>
                            {{#  } }}
                            {{# if(item.status == 2){ }}
                            <td style="text-align: center; border: 1px solid #dddcdc; ">
                                <input id="scoreDetails{{index}}" standardScore="{{item.standardScore}}"
                                       treeId="{{item.treeId}}" qcpA2="{{item.qcpA2}}" a1="{{item.a1}}" reqId="{{item.id}}"
                                       name="scoreDetails" class="layui-input scoreDetails" a5="{{item.a5}}"
                                       readonly="readonly" placeholder="0" style="border:0px;" value=""
                                       autocomplete="off" />
                                <%--<input id="{{item.id}}"  value="" type="hidden" />--%>
                            </td>
                           <%-- <td style="text-align: center; border: 1px solid #dddcdc; ">
                              &lt;%&ndash;  <a class="layui-btn layui-btn-sm icon-btn"
                                   href="#/closedloopmanage/HiddenDangerExam/qurId={{item.id}}/{{item.url}}"><i
                                        class="layui-icon">&#xe615;</i>查看</a>&ndash;%&gt;
                                  <button qurId="{{item.id}}"
                                          class="layui-btn layui-btn-sm icon-btn qualityControlChaKan"><i
                                          class="layui-icon">&#xe615;</i>查看</button>


                            </td>--%>
                            {{#  } }}
                        </tr>
                        {{# }); }} {{# if(d.length === 0){ }} 无数据 {{# } }}
                    </form>
                </script>
                <tbody id="QualityControlRequirementsListView">
                </tbody>
            </table>
        </div>
    </div>
</div>


<!-- end-content -->

<!-- end-footer -->
<script>
    layui.use(['layer', 'form', 'table', 'util', 'admin',  'upload', 'laydate', 'formX'], function () {
        var $ = layui.jquery;
        var layer = layui.layer;
        var form = layui.form;
        var laydate = layui.laydate;
        var table = layui.table;
        var util = layui.util;
        var admin = layui.admin;
        var upload = layui.upload;
        var formX = layui.formX;
        var router = layui.router();
        var moduleIds = getQueryString("moduleIds");
        var totalAssesId = getQueryString("totalAssesId");
        var status = getQueryString("status");
        var score = getQueryString("score");
        var theWeight = getQueryString("theWeight");
        var ss=<%=request.getAttribute("mineCode")%>
        var reqId = '';
        var parkId;
        var doubleQ = 'moduleIds=' + moduleIds + '/totalAssesId=' + totalAssesId + '/status=' + status;
        var baseServer = '';
        var tableName = "zlkh";
        function getQueryString(name) {

            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");

            var r = window.location.search.substr(1).match(reg);

            if (r != null) return unescape(decodeURIComponent(r[2]));

            return null;

        }
        var moduleList = new Array();
        if (moduleIds != '') {
            moduleList = moduleIds.split(",");
            if (moduleList.length > 0) {
                parkId = moduleList[moduleList.length-1]
                renderData(parkId);
            }
        }
        //var data3029 = [{"codDesc":"点3","code":"30290003"},{"codDesc":"点2","code":"30290002"},{"codDesc":"点1","code":"30290001"}];
        var data3029 = ${data3029};
        // 数据方式
        formX.renderSelect({
            elem: "#placeCode",
            data: data3029,
            name: 'codDesc',
            value: 'code',
            hint: '请选择',
            //initValue: '30290001',
            initValue: '${codeTemp}',
            done: function() {}
        });
        /*渲染下拉菜单*/
        admin.req(baseServer+'qualityControlModule/findByIdList.do', {
            idList: moduleIds,
        }, function (data) {
            if (data.code == 200) {
                if (data) {
                    var parknames = [];
                    for (var i = 0, len = data.datas.length; i < len; i++) {
                        var parkdata = data.datas[i];
                        //拼接成多个<option><option/>
                        parknames.push('<option value="' + parkdata.id + '">' + parkdata
                                .moduleName +
                            '</option>')
                    }
                    $("#parkID").html(parknames.join(
                        ' ')); //根据parkID(根据你自己的ID写)填充到select标签中
                }
            }
        }, 'post');

        // select下拉框选中触发事件
        $('#parkID').change(function () {
            var options = $("#parkID option:selected"); //获取选中的项
            parkId = options.val();
            renderData(parkId);
        });

        function chakanClick() {
            $('.chakan').click(function () {
                var a1 = this.attributes.a1.value;
                var url = this.attributes.url.value;
                var code = '';
                if (a1 == 2) {
                    code = $('#placeCode').val();
                }
                if (code == '') {
                    window.location.href = url + '/code=null';
                } else {
                    window.location.href = url + '/code=' + code;
                }
            })
        }

        // select下拉框选中触发事件
        $('#placeCode').change(function () {
            var options = $("#parkID option:selected"); //获取选中的项
            parkId = options.val();
            selectChange(parkId);
        });

        function selectChange(parkId) {
            var codeName = $('#placeCode').val();
            $('input[a1="2"]').val('');
            if (parkId != '' && codeName != '') {
                admin.req(baseServer+'detailsAsses/findByPage.do', {
                    moduleId: parkId,
                }, function (data) {
                    if (status == 2) {
                        $('input[name="scoreDetails"]').val('0');
                        $('.chakanTd').addClass('layui-hide');
                    }
                    var list = data.datas.list;
                    for (var i = 0; i < list.length; i++) {
                        if (totalAssesId == list[i].totalAssesId && parkId == list[i]
                            .moduleId) {
                            if (list[i].placeCode == undefined) {
                                $('input[reqId="' + list[i].requirementsId + '"]')
                                    .val(data
                                        .datas[i].scoreDetails);
                                if (status == 2) {
                                    if (list[i].a3 == 1) {
                                        $('.chakanTd').removeClass('layui-hide');
                                        $('a[reqId="' + list[i].requirementsId +
                                            '"]').removeClass('layui-hide');
                                    } else {
                                        $('span[reqId="' + list[i].requirementsId +
                                            '"]').removeClass('layui-hide');
                                    }
                                }
                            } else {
                                if (codeName == list[i].placeCode) {
                                    var id = $('input[reqId="' + list[i].requirementsId +
                                        '"]')[0].id
                                    layui.data(tableName + "11", {
                                        key: id,
                                        value: list[i].id
                                    });
                                    $('input[reqId="' + list[i].requirementsId +
                                        '"]').val(
                                        list[i].scoreDetails)
                                    if (status == 2) {
                                        if (list[i].a3 == 1) {
                                            $('.chakanTd').removeClass('layui-hide');
                                            $('a[reqId="' + list[i].requirementsId +
                                                '"]').removeClass('layui-hide');
                                        } else {
                                            $('span[reqId="' + list[i].requirementsId +
                                                '"]').removeClass('layui-hide');
                                        }
                                    }
                                }
                            }
                        }
                    }
                }, 'post');
            }
        }

        /*渲染表格*/
        function renderData(parkId) {
            admin.req(baseServer+'qualityControlProject/findByList.do', {
                moduleId: parkId,
                status: status,
                url: doubleQ,
                totalAssesId: totalAssesId,
                theWeight: theWeight,
                score: score,
                moduleIds: moduleIds
            }, function (data) {
                var html = admin
                    .util
                    .tpl($('#QualityControlRequirementsList').html(), data.datas);
                $("#QualityControlRequirementsListView").html(html);
                colSpan('qualitycontrolrequirementsTableView');
                autoRowSpan();
                inputBlurEdit();
                luRuClick();
                showInit();
                chaKanClick();
                for (var i = 0; i < data.datas.length; i++) {
                    if (data.datas[i].a1 == 2) {
                        $('.dafenCode').removeClass('layui-hide');
                        return false;
                    } else {
                        $('.dafenCode').addClass('layui-hide');
                    }
                }
            }, 'POST');
        }

        function luRuClick() {

            $('.qualityControlLuRu').click(function () {
                reqId = this.attributes.qurId.value;
                console.log(reqId);
                var deatilAccessId=$('input[id="' + reqId + '"]').val();
                var fs=$('input[reqId="'+reqId+'"]').val();
                console.log(fs);
                if (fs == '' || fs==null) {
                    layer.msg('请先打分在录入！', {
                        icon: 2
                    });
                    return false;
                }else{
                    addOneTab("隐患录入","tBHiddenDangerExamController.do?goQualityAdd&mineCode="+ss+"&deatilAccessId="+deatilAccessId,"default");

                }
            })
        }
        function chaKanClick(){
            $('.qualityControlChaKan').click(function () {
                reqId = this.attributes.qurId.value;

                var deatilAccessId=$('input[id="' + reqId + '"]').val();
                addOneTab("隐患录入","tBHiddenDangerExamController.do?listQuality&deatilAccessId="+deatilAccessId,"default");
            })
        }
        function showInit() {
            admin.req(baseServer+'detailsAsses/findByPage.do', {
                moduleId: parkId,
            }, function (data) {
                if (status == 2) {
                    $('input[name="scoreDetails"]').val('0');
                    $('.chakanTd').addClass('layui-hide');
                }
                var list = data.datas.list;
                for (var i = 0; i < list.length; i++) {
                    if (totalAssesId == list[i].totalAssesId && parkId == list[i]
                        .moduleId) {
                        if (list[i].placeCode == undefined) {

                            $('input[reqId="' + list[i].requirementsId +
                                '"]').val(
                                list[i].scoreDetails)
                            if (status == 2) {
                                if (list[i].a3 == 1) {
                                    $('.chakanTd').removeClass('layui-hide');
                                    $('a[reqId="' + list[i].requirementsId +
                                        '"]').removeClass('layui-hide');
                                } else {
                                    $('span[reqId="' + list[i].requirementsId +
                                        '"]').removeClass('layui-hide');
                                }
                            }
                        } else {
                            if ('${codeTemp}' == list[i].placeCode) {
                                var id = $('input[reqId="' + list[i].requirementsId +
                                    '"]')[0].id
                                layui.data(tableName + "11", {
                                    key: id,
                                    value: list[i].id
                                });
                                $('input[reqId="' + list[i].requirementsId +
                                    '"]').val(
                                    list[i].scoreDetails)
                                if (status == 2) {
                                    if (list[i].a3 == 1) {
                                        $('.chakanTd').removeClass('layui-hide');
                                        $('a[reqId="' + list[i].requirementsId +
                                            '"]').removeClass('layui-hide');
                                    } else {
                                        $('span[reqId="' + list[i].requirementsId +
                                            '"]').removeClass('layui-hide');
                                    }
                                }
                            }
                        }
                    }
                }
            }, 'post');
        }

        function inputBlurEdit() {
            if (status == 1) {
                $('input[name="scoreDetails"]').change(function () {
                    var that = this;
                    var treeId = this.attributes.treeId.value;
                    var reqId = this.attributes.reqId.value;
                    var standardScore = this.attributes.standardScore.value;
                    var a1 = this.attributes.a1.value;
                    var a5 = this.attributes.a5.value;
                    var qcpA2 = this.attributes.qcpA2.value;
                    var detailsId = '';
                    var sum = 0;
                    var placeCode;
                    var scoreDetails = $(this).val(); //分数
                    var options = $("#parkID option:selected"); //获取选中的项
                    var parkId = options.val(); //表名id
                    if (a5 != 'undefined' && a5 != 'null') {
                        var findInput = $(this).parents().find(
                            "tbody[id='QualityControlRequirementsListView']").find(
                            "input[a5='" + a5 + "']");
                        $(findInput).each(function (d) {
                            var num = findInput[d].value;
                            if (num != '') {
                                sum = parseInt(sum) + parseInt(num);
                            }
                        });
                        if (parseInt(sum) > parseInt(standardScore)) {
                            layer.msg('该项的分数和不能超过' + standardScore + '分', {
                                icon: 2
                            });
                            $(this).val('');
                            return false;
                        }
                    }
                    if (a1 == 2) {
                        var options = $('#placeCode option:selected'); //获取选中的项
                        placeCode = options.val(); //打分点code
                        if (placeCode == '') {
                            layer.msg('请选择打分点', {
                                icon: 2
                            });
                            $(this).val('');
                            return false;
                        }
                    }
                    if (scoreDetails != '' && totalAssesId != '') {
                        reg = /^(\d{1,2}(\.\d{1,2})?|100)$/; //0-100含小数小数点后最多两位
                        if (!reg.test(scoreDetails)) {
                            layer.msg('只能输入0-100之间的数字或小数', {
                                icon: 2
                            });
                            $(this).val('');
                            return false;
                        }
                        if (parseFloat(scoreDetails) > parseFloat(standardScore)) {
                            layer.msg('分数不能大于标准分值', {
                                icon: 2
                            });
                            $(this).val('');
                            return false;
                        }
                        var url = baseServer+"detailsAsses/insert.do";
                        var cache = layui.data(tableName + "11");
                        var idsss = that.id;
                        if (cache) {
                            detailsId = cache[idsss];
                        }
                        admin.req(url, {
                            projectTreeId: treeId,
                            requirementsId: reqId,
                            moduleId: parkId,
                            scoreDetails: scoreDetails,
                            placeCode: placeCode,
                            totalAssesId: totalAssesId,
                            id: detailsId,
                            a1: a1,
                            a2: qcpA2
                        }, function (res) {
                            if (200 == res.code) {
                                layui.data(tableName + "11", {
                                    key: idsss,
                                    value: res.datas
                                });
                                layer.msg('新增成功', {
                                    icon: 1
                                });
                            } else {
                                layer.msg(res.msg, {
                                    icon: 2
                                });
                            }
                        }, 'POST');
                        return false;
                    }
                })
            }
        }

       /* function luRuClick() {
            $('.qualityControlLuRu').click(function () {
                reqId = this.attributes.qurId.value;
                var a1 = this.attributes.a1.value;
                admin.req(baseServer+'detailsAsses/findByPage', {
                    moduleId: parkId,
                    requirementsId: reqId,
                    totalAssesId: totalAssesId
                }, function (res) {
                    if (200 == res.code) {
                        var count = res.datas.length;
                        if (count == 0) {
                            layer.msg('请先打分，再来录入隐患', {
                                icon: 2
                            });
                            return false;
                        } else {
                            showEditModelqualityControlLuRu(a1);
                        }
                    }
                }, 'POST');
            })
        }
*/
        goOutClick();
        /*返回按钮的点击事件*/
        function goOutClick() {
            $('#goOutClick').click(function () {
                var findInput = $(this).parents().find(
                    "tbody[id='QualityControlRequirementsListView']").find(
                    'input[name="scoreDetails"]');
                var str = '0';
                $(findInput).each(function (d) {
                    if (findInput[d].value == '') {
                        str = findInput[d].value;
                    }
                });
                window.location.href = "qualityControlRequirements.do?list&totalAssesId=${totalAssesId}";
                layui.index.closeTab('#/qualitycontrol/QualityControlRequirements');
                /*if (str == '') {
                    layer.msg('分数没有录完，请检查！', {
                        icon: 2
                    });
                    return false;
                } else {
                    window.location.href = "qualityControlRequirements.do?list";
                    layui.index.closeTab('#/qualitycontrol/QualityControlRequirements');
                }*/
            })
        }




        /*跨列合并*/
        function colSpan(tableId) {
            var maxRow = 6,
                val, count, start, valtd;
            count = 1;
            val = "";
            var $tb = $('#qualityControlReqScriptTable'); //获取目标tabel
            var $trs = $("#QualityControlRequirementsListView>tr");
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
                        $trs[i].cells[col].width = parseInt($($trs[i].cells[col]).width()) + parseInt(
                            wid);
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

        /*跨行合并*/
        function autoRowSpan(row, col, j) {
            var tb = $('#qualityControlReqScriptTable'); //获取目标tabel
            var maxRow = 7;
            var $trs = $("#QualityControlRequirementsListView>tr");
            var rowspan = 1;
            for (var index = maxRow - 1; index >= 0; index--) { //遍历每一行的每一个td
                if ($trs.length > 0) {
                    var lastValue, lastAtt, a5, spanNum = 1;
                    lastValue = $trs.eq(0).find('td').eq(index).html();
                    if (index == 5 || index == 6) {
                        lastAtt = $trs.eq(0).find('td')[index].attributes.a5.value;
                    }
                    for (var i = 1; i < $trs.length; i++) {
                        var currentValue = $trs.eq(i).find('td').eq(index).html();
                        if (currentValue === lastValue) {
                            if (index == 5 || index == 6) {
                                if ($trs.eq(i).find('td')[index].attributes.a5 != undefined) {
                                    a5 = $trs.eq(i).find('td')[index].attributes.a5.value;
                                    if (lastAtt != 'undefined' && a5 != 'undefined' &&  lastAtt != 'null' && a5 != 'null' && lastAtt.toString() == a5
                                        .toString()) {
                                        spanNum++;
                                        $trs.eq(i - spanNum + 1).find('td').eq(index).attr('rowspan',
                                            spanNum);
                                        for (var j = 1; j < spanNum; j++) {
                                            $trs.eq(i - j + 1).find('td').eq(index).attr('del', 'true');
                                        }
                                    } else {
                                        lastAtt = a5;
                                    }
                                }
                            } else {
                                spanNum++;
                                if (i === $trs.length - 1) {
                                    $trs.eq(i - spanNum + 1).find('td').eq(index).attr('rowspan',
                                        spanNum);
                                    for (var j = 1; j < spanNum; j++) {
                                        $trs.eq(i - j + 1).find('td').eq(index).attr('del', 'true');
                                    }
                                }
                            }
                        } else {
                            if (index == 5 || index == 6) {
                                if ($trs.eq(i).find('td')[index].attributes.a5 != undefined) {
                                    a5 = $trs.eq(i).find('td')[index].attributes.a5.value;
                                    if (lastAtt != 'undefined' && a5 != 'undefined' && lastAtt != 'null' && a5 != 'null' && lastAtt.toString() == a5
                                        .toString()) {
                                        $trs.eq(i - spanNum).find('td').eq(index).attr('rowspan', spanNum);
                                        for (var k = 1; k < spanNum; k++) {
                                            $trs.eq(i - k).find('td').eq(index).attr('del', 'true');
                                        }
                                    } else {
                                        lastAtt = a5;
                                    }
                                }
                            } else {
                                $trs.eq(i - spanNum).find('td').eq(index).attr('rowspan', spanNum);
                                for (var k = 1; k < spanNum; k++) {
                                    $trs.eq(i - k).find('td').eq(index).attr('del', 'true');
                                }
                            }
                            spanNum = 1;
                            lastValue = currentValue;
                        }
                    }
                }
                $trs.find('[del="true"]').remove(); // 移除多余的单元格
            }
        }
    });
</script>
</body>
</html>