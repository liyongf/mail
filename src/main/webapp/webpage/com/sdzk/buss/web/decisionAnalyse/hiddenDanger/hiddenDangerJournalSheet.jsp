<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>隐患报表</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script src="plug-in/ace/js/bootstrap.js"></script>
    <script src="plug-in/echarts/code/echarts.js"></script>
    <link href="plug-in/ace/css/bootstrap.css" rel="stylesheet">
</head>
<body style="background-color: #e0dcdc;">
<div style="margin-top: 10px;">
    <t:formvalid formid="queryCondition" dialog="true" layout="table" action="">
        <table style="width: 390px;background-color: #e0dcdc;" id="conditionTable" align="center" cellpadding="0" cellspacing="1"
               class="formtable">
            <tr style="background-color: #e0dcdc;">
                <td align="right" style="width: 80px;background-color: #e0dcdc;">
                    <label class="Validform_label">
                        统计维度：
                    </label>
                </td>
                <td class="value" style="width:120px;text-align: left;background-color: #e0dcdc;">
                    <select name="dimension" id="dimension" style="width: 120px;" onchange="changeDimension();">
                        <option value="week" selected="selected">按周</option>
                        <option value="month">按月</option>
                        <option value="day">按日</option>
                    </select>
                </td>
                <td class="value" id="condition" style="text-align: left;width: 120px;background-color: #e0dcdc;">
                    <div id="weekCondition" style="display: inline;">
                        <select name="week" id="week" style="width: 120px;">
                            <option value="lastWeek">上周</option>
                            <option value="thisWeek" selected="selected">本周</option>
                        </select>
                    </div>
                    <div id="monthCondition" style="display: none;">
                        <select name="month" id="month" style="width: 120px;">
                            <option value="lastMonth">上月</option>
                            <option value="thisMonth" selected="selected">本月</option>
                        </select>
                    </div>
                    <div id="dayCondition" style="display: none;">
                        <input id="startDate" name="startDate" value="${startDate}"
                               style="height: 30px; width: 100px; border-radius: 4px;"/>～
                        <input id="endDate" name="endDate" value="${endDate}"
                               style="height: 30px; width: 100px; border-radius: 4px;"/>
                    </div>
                </td>
                <td class="value" style="width:50px;text-align: left;background-color: #e0dcdc;">
                    <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="doQuery()">查询</a>
                </td>
            </tr>
        </table>
    </t:formvalid>
</div>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-xs-12 col-md-6 col-lg-6" title="隐患地点分布" style="height: 350px;padding: 20px;">
            <button type="button" class="btn btn-primary btn-xs"
                    onclick="fullScreen('隐患地点分布','hiddenDangerAddressStatistics')"
                    style="position: absolute;top: 30px;right:30px; background-color: #61acf5; border-color: #61acf5;">
                <i class="fa fa-arrows-alt"></i>全屏
            </button>
            <iframe id="hiddenDangerAddressStatistics"
                    src='tBSafetyAnalyseController.do?goHiddenDangerAddressStatistics&startDate=${startDate}&endDate=${endDate}' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
        <div class="col-xs-12 col-md-6 col-lg-6" title="隐患等级分布" style="height: 350px;padding: 20px;">
            <button type="button" class="btn btn-primary btn-xs"
                    onclick="fullScreen('隐患等级分布','hiddenDangerLevelStatistics')"
                    style="position: absolute;top: 30px;right:30px; background-color: #61acf5; border-color: #61acf5;">
                <i class="fa fa-arrows-alt"></i>全屏
            </button>
            <iframe id="hiddenDangerLevelStatistics"
                    src='tBSafetyAnalyseController.do?goHiddenDangerLevelStatistics&startDate=${startDate}&endDate=${endDate}' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>

        <%--修改为类型--%>
        <div class="col-xs-12 col-md-6 col-lg-6" title="隐患类型分布" style="height: 350px;padding: 20px;">
            <button type="button" class="btn btn-primary btn-xs"
                    onclick="fullScreen('隐患类型分布','hiddenDangerProfessionStatistics')"
                    style="position: absolute;top: 30px;right:30px; background-color: #61acf5; border-color: #61acf5;">
                <i class="fa fa-arrows-alt"></i>全屏
            </button>
            <iframe id="hiddenDangerProfessionStatistics"
                    src='tBSafetyAnalyseController.do?goHiddenDangerProfessionStatistics&startDate=${startDate}&endDate=${endDate}' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
        <div class="col-xs-12 col-md-6 col-lg-6" title="超期隐患列表" style="height: 350px;padding: 20px;">
            <iframe id="overdueHiddenDangerList"
                    src='tBSafetyAnalyseController.do?goOverdueHiddenDangerList&startDate=${startDate}&endDate=${endDate}' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
        <div class="col-xs-12 col-md-6 col-lg-6" title="隐患单位分布" style="height: 350px;padding: 20px;">
            <button type="button" class="btn btn-primary btn-xs"
                    onclick="fullScreen('隐患单位分布','hiddDangerByGroupFrame')"
                    style="position: absolute;top: 30px;right:30px; background-color: #61acf5; border-color: #61acf5;">
                <i class="fa fa-arrows-alt"></i>全屏
            </button>
            <iframe id="hiddDangerByGroupFrame"
                    src='homePageController.do?hiddenDangerByGroup&startDate=${startDate}&endDate=${endDate}' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
        <div class="col-xs-12 col-md-6 col-lg-6" title="未闭环隐患列表" style="height: 350px;padding: 20px;">
            <iframe id="unclosedHiddenDangerList"
                    src='tBSafetyAnalyseController.do?goUnclosedHiddenDangerList&startDate=${startDate}&endDate=${endDate}' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        $("#startDate").attr("class", "Wdate").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("#endDate").attr("class", "Wdate").click(function () {
            WdatePicker({dateFmt: 'yyyy-MM-dd'});
        });
        $("input[name='startDate']").val("${startDate}");
        $("input[name='endDate']").val("${endDate}");

        $("#week").on("change",function () {
           changeQueryCondition();
        });
        $("#month").on("change",function () {
            changeQueryCondition();
        });
    });

    /**
     * 修改统计维度
     */
    function changeDimension() {
        var val = $("#dimension").find("option:selected").val();
        if ("day" == val) {
            $("#conditionTable").css("width", "500px");
            $("#condition").css("width", "220px");

            $("#weekCondition").css("display", "none");
            $("#monthCondition").css("display", "none");
            $("#dayCondition").css("display", "inline");

            $("#startDate").val("");
            $("#endDate").val("");
        } else {
            $("#conditionTable").css("width", "390px");
            $("#condition").css("width", "120px");
            if ("week" == val) {
                $("#weekCondition").css("display", "inline");
                $("#monthCondition").css("display", "none");
                $("#dayCondition").css("display", "none");
            }
            if ("month" == val) {
                $("#weekCondition").css("display", "none");
                $("#monthCondition").css("display", "inline");
                $("#dayCondition").css("display", "none");
            }
        }
        changeQueryCondition();
    }

    /**
     * 全屏
     */
    function fullScreen(title, iframeId) {
        var url = $("#" + iframeId).attr("src") + "&flag=1";
        createdetailwindow(title, url, $(window).width(), $(window).height());
    }

    /**
     * 查询条件变更
     */
    function changeQueryCondition(){
        debugger
        var dimension = $("#dimension").find("option:selected").val();
        var week = $("#week").find("option:selected").val();
        var month = $("#month").find("option:selected").val();

        if(dimension != "day"){
            $.ajax({
                url: 'tBSafetyAnalyseController.do?getDateRangeByDimension',
                type: 'post',
                data: {
                    dimension: dimension,
                    month: month,
                    week: week
                },
                cache: false,
                success: function (data) {
                    var d = $.parseJSON(data);
                    var startDate = d.startDate;
                    var endDate = d.endDate;
                    debugger
                    $("#startDate").val(startDate);
                    $("#endDate").val(endDate);
                }
            });
        }
    }

    /**
     * 查询结果返回
     */
    function doQuery() {
        var condition = "&startDate="+$("#startDate").val()+"&endDate="+$("#endDate").val();

        $('#hiddenDangerAddressStatistics').attr('src', "tBSafetyAnalyseController.do?goHiddenDangerAddressStatistics"+condition);
        $('#hiddenDangerLevelStatistics').attr('src', "tBSafetyAnalyseController.do?goHiddenDangerLevelStatistics"+condition);
        $('#hiddenDangerProfessionStatistics').attr('src', "tBSafetyAnalyseController.do?goHiddenDangerProfessionStatistics"+condition);
        $('#overdueHiddenDangerList').attr('src', "tBSafetyAnalyseController.do?goOverdueHiddenDangerList"+condition);
        $('#hiddDangerByGroupFrame').attr('src', "homePageController.do?hiddenDangerByGroup"+condition);
        $('#unclosedHiddenDangerList').attr('src', "tBSafetyAnalyseController.do?goUnclosedHiddenDangerList"+condition);
    }

    /**
     * 详情列表页
     * @param title
     * @param url
     */
    function goDetailList(title,url){
        createdetailwindow(title, url, 800, 600);
    }
</script>

