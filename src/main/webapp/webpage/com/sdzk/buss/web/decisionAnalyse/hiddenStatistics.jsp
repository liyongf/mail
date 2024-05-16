<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/Highcharts-5.0.11/code/highcharts.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-zh_CN.js"></script>
<script  type="text/javascript" src="plug-in/hashMap/HashMap.js"></script>
<script src="plug-in/hplus/js/bootstrap.min.js?v=3.3.6"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts.js"></script>
<script type="text/javascript" src="webpage/common/js/excelReport.js"></script>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<html>
<head>
    <title>风险管控缺陷分析</title>
</head>
<body style="width: 99%">
<table style="width: 100%;height: auto; margin-top: 10px;">
    <tr>
        <td style="text-align: center">
            <form id="thisForm" action="tBDecisionAnalyseController.do?hiddenStatistics" method="post">
                <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;"
                      title="问题地点">风险点：</span>
                <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="addressSelect" style="width: 130px;height:auto"></div>
                 <input id="addressId" type="hidden" name="addressId" value="${addressId}">
             </span>

                隐患类型：&nbsp;&nbsp;

                <t:dictSelect field="riskType" type="list" extendJson="{\"datatype\":\"*\"}"
                              typeGroupCode="risk_type"  hasLabel="false"  title="隐患类型" defaultVal="${riskType}"></t:dictSelect>&nbsp;&nbsp;
                <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;" title="责任部门">责任部门：</span>
                <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="departnameSuggest" style="width: 130px;height: 15px"></div>
                 <input id="departId" type="hidden" name="departId" value="${departId}">
             </span>
                年度: &nbsp;<input id="queryDate" name="queryDate"  />&nbsp;&nbsp;&nbsp;&nbsp;
                <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="doQuery(0)">
                    查询
                </a>
            </form>
        </td>
    </tr>

    <tr>
        <td>
            <div id="container" style="min-width:400px;height:550px;margin-top: 8px;"></div>
        </td>
    </tr>

    <tr>
        <td>
            <div class="col-xs-12 col-md-6 col-lg-6" title="风险管控缺陷分析" style="width:100%;background-color:white;">
                <table style="width: 90%;margin:0 auto;position: relative;top: 60px;" cellpadding="5" cellspacing="0"
                       class="table table-responsive table-bordered table-hover table-striped" id="hiddenStatistics">
                    <tr>
                        <td id="year" align="center" style="font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;">${queryDate}年</td>
                        <td align="center" style="font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;">1月</td>
                        <td align="center" style="font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;">2月</td>
                        <td align="center" style="font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;">3月</td>
                        <td align="center" style="font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;">4月</td>
                        <td align="center" style="font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;">5月</td>
                        <td align="center" style="font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;">6月</td>
                        <td align="center" style="font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;">7月</td>
                        <td align="center" style="font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;">8月</td>
                        <td align="center" style="font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;">9月</td>
                        <td align="center" style="font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;">10月</td>
                        <td align="center" style="font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;">11月</td>
                        <td align="center" style="font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;">12月</td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
</table>

</body>
</html>

<script type="text/javascript">
    var chart;

    $(function () {
        $("#queryDate").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy'});});
        $("input[name='queryDate']").val("${year}");
        getAddressMagicSuggestWithValue($("#addressSelect"), $("[name='addressId']"),'${addressId}',false);
        getDepartMagicSuggestWithValue($("#departnameSuggest"), $("#departId"),'${departId}',false);
        doQuery(0);


    });

    function doQuery(isReset){
        if (isReset) {
            $("#queryDate").val("${year}");
        }
        $("#year").empty().append($("#queryDate").val()+"年");
        $.ajax({
            type: 'POST',
            url: 'tBDecisionAnalyseController.do?hiddenStatisticsDatagrid',
            data: {
                queryYear: $("#queryDate").val(),
                addressId: $("#addressId").val(),
                riskType: $("select[name='riskType']>option:checked").val(),
                departId: $("#departId").val(),
            },
            success: function (data) {
                var retData = $.parseJSON(data);
                var totalNum = retData.totalNum;
                var newNum = retData.newNum;
                var closeNum = retData.closeNum;
                redrawCharts(totalNum, newNum, closeNum);
            }
        });
    }

    function redrawCharts(totalNum, newNum, closeNum){
        $("#container").highcharts({
            title: {
                text: '风险管控缺陷分析'
            },
            credits: {
                enabled: false
            },
            xAxis: {
                categories: [ '一月', '二月','三月','四月','五月','六月','七月','八月','九月','十月','十一月','十二月' ]
            },
            yAxis: {
                title: {
                    text: '数<br/><br/>量<br/><br/>(个)',
                    rotation:0
                },
                allowDecimals:false
            },
            series: [{
                type: 'spline',
                name: '全部',
                data: eval("[" + totalNum + "]"),
                events: {
                    click: function(e) {
                        var month = e.point.category;
                        goHiddenStatisticsCol(monthFormat(month),1);
                    }
                }
            },{
                type: 'column',
                name: '新增隐患数量',
                data: eval("[" + newNum + "]"),
                events: {
                    click: function(e) {
                        var month = e.point.category;
                        goHiddenStatisticsCol(monthFormat(month),2);
                    }
                }
            },{
                type: 'column',
                name: '闭环隐患数量',
                data: eval("[" + closeNum + "]"),
                events: {
                    click: function(e) {
                        var month = e.point.category;
                        goHiddenStatisticsCol(monthFormat(month),3);
                    }
                }
            }]
        });
        redrawTable(totalNum, newNum, closeNum);
    }

    function redrawTable(totalNum, newNum, closeNum){
        $("#hiddenStatistics").find("tr:gt(0)").each(function(){
            $(this).remove();
        });

        var newNumList = newNum.split(",");
        var closeNumList = closeNum.split(",");
        var html = "<tr><td align='center' style='font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;'>隐患增加数量</td>";
        var html1 = "<tr><td align='center' style='font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;'>隐患闭环数量</td>";
        for(var i=0;i<newNumList.length;i++){
            html += getTdHtml(newNumList[i]);
            html1 += getTdHtml(closeNumList[i]);
        }
        html += "</tr>";
        html1 += "</tr>";
        $("#hiddenStatistics").append(html);
        $("#hiddenStatistics").append(html1);
    }

    function getTdHtml(count){
        return "<td align='center' style='font-weight: bold;font-size: 14px;border:1px solid #DDDDDD;font-weight: normal;'>"+ count +"</td>";
    }

    function goHiddenStatisticsCol(month,type){
        createdetailwindow("查看","tBDecisionAnalyseController.do?hdNumList&month="+month+"&year="+$("#queryDate").val()+"&addressId="+$("#addressId").val()+"&departId="+$("#departId").val()+"&riskType="+$("select[name='riskType']>option:checked").val()+"&type="+type+"",800,600);
    }

    function monthFormat(month) {
        var retMonth = "";
        switch (month){
            case "一月":
                retMonth = "01";    break;
            case "二月":
                retMonth = "02";    break;
            case "三月":
                retMonth = "03";    break;
            case "四月":
                retMonth = "04";    break;
            case "五月":
                retMonth = "05";    break;
            case "六月":
                retMonth = "06";    break;
            case "七月":
                retMonth = "07";    break;
            case "八月":
                retMonth = "08";    break;
            case "九月":
                retMonth = "09";    break;
            case "十月":
                retMonth = "10";    break;
            case "十一月":
                retMonth = "11";    break;
            case "十二月":
                retMonth = "12";    break;
        }
        return retMonth;
    }

</script>
