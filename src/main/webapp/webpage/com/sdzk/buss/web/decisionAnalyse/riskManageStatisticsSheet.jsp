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
                <td class="value" id="condition" style="text-align: left;width: 120px;background-color: #e0dcdc;">
                    <div id="dayCondition" style="display: none;">
                        <input id="startDate" name="startDate" value="${startDate}"
                               style="height: 30px; width: 100px; border-radius: 4px;"/>～
                        <input id="endDate" name="endDate" value="${endDate}"
                               style="height: 30px; width: 100px; border-radius: 4px;"/>
                    </div>
                </td>
                <td class="value" style="width:200px;text-align: left;background-color: #e0dcdc;">
                    月度: <input id="yearMonth" name="yearMonth" value="" />
                    <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="doQuery()">查询</a>
                </td>
            </tr>
        </table>
    </t:formvalid>
</div>

<div class="container-fluid">
    <div class="row-fluid">
        <div class="col-xs-12 col-md-6 col-lg-6" title="风险管控人数" style="height: 350px;padding: 20px;">
            <button type="button" class="btn btn-primary btn-xs"
                    onclick="fullScreen('风险管控人数','riskManageManageNumStatistics')"
                    style="position: absolute;top: 30px;right:30px; background-color: #61acf5; border-color: #61acf5;">
                <i class="fa fa-arrows-alt"></i>全屏
            </button>
            <iframe id="riskManageManageNumStatistics"
                    src='tBDecisionAnalyseController.do?goManageNumStatistics' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
        <div class="col-xs-12 col-md-6 col-lg-6" title="风险落实次数" style="height: 350px;padding: 20px;">
            <button type="button" class="btn btn-primary btn-xs"
                    onclick="fullScreen('风险落实次数','riskManageImplNumStatistics')"
                    style="position: absolute;top: 30px;right:30px; background-color: #61acf5; border-color: #61acf5;">
                <i class="fa fa-arrows-alt"></i>全屏
            </button>
            <iframe id="riskManageImplNumStatistics"
                    src='tBDecisionAnalyseController.do?goImplNumStatistics' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
        <div class="col-xs-12 col-md-6 col-lg-6" title="风险隐患数量" style="height: 350px;padding: 20px;">
            <button type="button" class="btn btn-primary btn-xs"
                    onclick="fullScreen('风险隐患数量','riskManageHdNumStatistics')"
                    style="position: absolute;top: 30px;right:30px; background-color: #61acf5; border-color: #61acf5;">
                <i class="fa fa-arrows-alt"></i>全屏
            </button>
            <iframe id="riskManageHdNumStatistics"
                    src='tBDecisionAnalyseController.do?goHdNumStatistics' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
        <div class="col-xs-12 col-md-6 col-lg-6" title="风险危害因素" style="height: 350px;padding: 20px;">
            <button type="button" class="btn btn-primary btn-xs"
                    onclick="fullScreen('风险危害因素','riskManageHazardFactortsNumStatistics')"
                    style="position: absolute;top: 30px;right:30px; background-color: #61acf5; border-color: #61acf5;">
                <i class="fa fa-arrows-alt"></i>全屏
            </button>
            <iframe id="riskManageHazardFactortsNumStatistics"
                    src='tBDecisionAnalyseController.do?goHazardFactortsNumStatistics' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
    </div>
</div>
</body>
<script>
    $(function () {
        $("#yearMonth").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
    });

    /**
     * 全屏
     */
    function fullScreen(title, iframeId) {
        var url = $("#" + iframeId).attr("src") + "&flag=1";
        createdetailwindow(title, url, $(window).width(), $(window).height());
    }

    /**
     * 查询结果返回
     */
    function doQuery() {
        var condition = "&startDate="+$("#yearMonth").val()+"&endDate="+$("#yearMonth").val();
        $('#riskManageManageNumStatistics').attr('src', "tBDecisionAnalyseController.do?goManageNumStatistics"+condition);
        $('#riskManageImplNumStatistics').attr('src', "tBDecisionAnalyseController.do?goImplNumStatistics"+condition);
        $('#riskManageHdNumStatistics').attr('src', "tBDecisionAnalyseController.do?goHdNumStatistics"+condition);
        $('#riskManageHazardFactortsNumStatistics').attr('src', "tBDecisionAnalyseController.do?goHazardFactortsNumStatistics"+condition);
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

