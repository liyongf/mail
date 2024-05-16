<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2017/11/9
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<html>
<head>
    <title>矿版双防系统首页</title>

    <script src="plug-in/ace/js/bootstrap.js"></script>
    <script src="plug-in/echarts/code/echarts.js"></script>
    <link href="plug-in/ace/css/bootstrap.css" rel="stylesheet">

    <script>
        $(function(){
            $("#startDate").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:doQuery});});
            $("#endDate").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:doQuery});});
            $("input[name='startDate']").val("${startDate}");
            $("input[name='endDate']").val("${endDate}");

            var ulWidth = ${retList.size()} * 1000;
            $("#myUl").css("width", ulWidth+"px");
        })

    </script>
</head>
<body>
    <div style="float: left; margin-left: 40px; margin-top: 5px; ">
        <form id="thisForm" action="tBDecisionAnalyseController.do?hiddenDangerUnbindDangerSource" method="post">
            <input id="startDate" name="startDate" value="${startDate}" style="height: 23px; width: 110px; border-radius: 5px; " />
             &nbsp;~&nbsp;
            <input id="endDate" name="endDate" value="${endDate}" style="height: 23px; width: 110px; border-radius: 5px; " />
            &nbsp;&nbsp;&nbsp;
            <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="doQuery()" id="" style="margin-top: 2px; ">刷新</a>
        </form>
    </div>

    <%--这里是隐患超期未整改公告--%>
    <div style="float: right; margin-top: 0px; margin-bottom: 8px; margin-right: 40px; overflow: hidden;">
        <marquee direction=left scrollamount=6 onmouseover="this.stop()" onmouseout="this.start()" style="height:30px; width: 1000px">
            <c:forEach items="${retList}" var="notice">
                <%--<a href='#' onclick='openwindow("公告详情","tBNoticeController.do?goUpdate&load=detail&noticeId=${notice.id}","",800,600);'>--%>
                    <ul id="myUl">
                        <li style="height: auto;width: 1000px; float: left;">
                            超期隐患公告：${notice.fineDate} 责任部门：${notice.dutyUnit} 责任人：${notice.dutyMan} 隐患描述： ${notice.desc}
                        </li>
                    </ul>
                <%--</a>--%>
            </c:forEach>
        </marquee>
        &emsp;
        <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-user-set" onclick="setChildWinByUser()" >配置我的首页</a>
    </div>

    <div style="clear: both; margin-top: 5px; margin-bottom: 5px;"></div>

    <div class="container-fluid" style="height: 90%;">
        <div class="row-fluid">
            <div class="col-xs-12 col-md-3 col-lg-3" title="待办任务" style="height: 50%;">
                <button type="button" class="btn btn-primary btn-xs" onclick="fullScreen('待办任务','waitingTaskFrame')" style="position: absolute;top: 10px;right:10px; background-color: #24ddf3; border-color: #24ddf3;">全屏</button>
                <iframe id="waitingTaskFrame" src='homePageController.do?waitingTask' style="height: 100%;width: 100%;" frameborder="0"></iframe>
            </div>
            <div class="col-xs-12 col-md-6 col-lg-6" title="双防总体趋势" style="height: 50%;">
                <button type="button" class="btn btn-primary btn-xs" onclick="fullScreen('双防总体趋势','overTrendFrame')" style="position: absolute;top: 10px;right:10px; background-color: #24ddf3; border-color: #24ddf3;">全屏</button>
                <iframe id="overTrendFrame" src='homePageController.do?overallTrend' style="height: 100%;width: 100%;" frameborder="0"></iframe>
            </div>
            <div class="col-xs-12 col-md-3 col-lg-3" title="重大风险" style="height: 50%;">
                <%--<button type="button" class="btn btn-primary btn-xs" onclick="fullScreen('重大风险','majorRiskFrame')" style="position: absolute;top: 10px;right:10px; background-color: #24ddf3; border-color: #24ddf3;">全屏</button>--%>
                <iframe id="majorRiskFrame" src='homePageController.do?majorRisk' style="height: 100%;width: 100%;" frameborder="0"></iframe>
            </div>
            <div class="col-xs-12 col-md-3 col-lg-3" title="风险动态分级预警" style="height: 50%;">
                <button type="button" class="btn btn-primary btn-xs" onclick="fullScreen('风险动态分级预警','riskDynaFrame')" style="position: absolute;top: 10px;right:10px; background-color: #24ddf3; border-color: #24ddf3;">全屏</button>
                <iframe id="riskDynaFrame" src='homePageController.do?riskDynamicClassification' style="height: 100%;width: 100%;" frameborder="0"></iframe>
            </div>
            <div class="col-xs-12 col-md-6 col-lg-6" title="隐患部门分布" style="height: 50%;">
                <button type="button" class="btn btn-primary btn-xs" onclick="fullScreen('隐患部门分布','hiddDangerByGroupFrame')" style="position: absolute;top: 10px;right:10px; background-color: #24ddf3; border-color: #24ddf3;">全屏</button>
                <iframe id="hiddDangerByGroupFrame" src='homePageController.do?hiddenDangerByGroup' style="height: 100%;width: 100%;" frameborder="0"></iframe>
            </div>
            <div class="col-xs-12 col-md-3 col-lg-3" title="隐患专业分布" style="height: 50%;">
                <button type="button" class="btn btn-primary btn-xs" onclick="fullScreen('隐患专业分布','hiddDangerByProfFrame')" style="position: absolute;top: 10px;right:10px; background-color: #24ddf3; border-color: #24ddf3;">全屏</button>
                <iframe id="hiddDangerByProfFrame" src='homePageController.do?hiddenDangerByProfession' style="height: 100%;width: 100%;" frameborder="0"></iframe>
            </div>
        </div>
    </div>

    <script>
        function doQuery() {
            var startDate = $("input[name='startDate']").val();
            var endDate = $("input[name='endDate']").val();

//            $('#waitingTaskFrame').attr('src', "homePageController.do?waitingTask&startDate="+startDate+"&endDate="+endDate);
            $('#overTrendFrame').attr('src', "homePageController.do?overallTrend&startDate="+startDate+"&endDate="+endDate);
//            $('#majorRiskFrame').attr('src', "homePageController.do?majorRisk&startDate="+startDate+"&endDate="+endDate);
//            $('#riskDynaFrame').attr('src', "homePageController.do?riskDynamicClassification&startDate="+startDate+"&endDate="+endDate);
//            $('#hiddDangerByGroupFrame').attr('src', "homePageController.do?hiddenDangerByGroup&startDate="+startDate+"&endDate="+endDate);
//            $('#hiddDangerByProfFrame').attr('src', "homePageController.do?hiddenDangerByProfession&startDate="+startDate+"&endDate="+endDate);
        }

        function fullScreen(title,iframeId){
            var url = $("#"+iframeId).attr("src");
            if("hiddDangerByGroupFrame" == iframeId){
                url += "&flag=1";
            }
            createdetailwindow(title, url, $(document).width(), $(document).height());
            //addOneTab(title,url,null);
        }

        function setChildWinByUser() {
            if (typeof(windowapi) == 'undefined') {
                $.dialog({content: 'url:childWindowController.do?goEditUserChildWin', zIndex: 2100, title: '首页配置', lock: true, width: 800, height: 350, left: '45%', top: '45%', opacity: 0.4, button: [
                    {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_childWinByUser, focus: true},
                    {name: '<t:mutiLang langKey="common.close"/>', callback: function () {
                    }}
                ]});
            } else {
                $.dialog({content: 'url:childWindowController.do?goEditUserChildWin', zIndex: 2100, title: '首页配置', lock: true, parent: windowapi, width: 800, height: 350, left: '45%', top: '45%', opacity: 0.4, button: [
                    {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_childWinByUser, focus: true},
                    {name: '<t:mutiLang langKey="common.close"/>', callback: function () {
                    }}
                ]});
            }
        }

        function clickcallback_childWinByUser(){
            iframe = this.iframe.contentWindow;
            var left_top = iframe.getValueByElementName('left_top');
            var center_top = iframe.getValueByElementName('center_top');
            var right_top = iframe.getValueByElementName('right_top');
            var left_bottom = iframe.getValueByElementName('left_bottom');
            var center_bottom = iframe.getValueByElementName('center_bottom');
            var right_bottom = iframe.getValueByElementName('right_bottom');

            $.ajax({
                url: 'childWindowController.do?saveUserChildWin',
                type: 'post',
                data: {
                    left_top: left_top,
                    center_top: center_top,
                    right_top: right_top,
                    left_bottom: left_bottom,
                    center_bottom: center_bottom,
                    right_bottom: right_bottom
                },
                beforeSend: function(data){
                    $.messager.progress({
                        text : "正在保存......",
                        interval : 100
                    });
                },
                cache: false,
                success: function (data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var msg = d.msg;
                        tip(msg);
                    }else{
                        var msg = d.msg;
                        tip(msg);
                    }
                },
                complete: function(data){
                    $.messager.progress('close');
                },
                error:function(data){
                    tip("操作失败");
                }
            });
        }
    </script>
</body>
</html>
