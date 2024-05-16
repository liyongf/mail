<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2017/11/9
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<html>
<head>
    <title>矿版双防系统首页</title>

    <script src="plug-in/ace/js/bootstrap.js"></script>
    <script src="plug-in/echarts/code/echarts.js"></script>
    <link href="plug-in/ace/css/bootstrap.css" rel="stylesheet">

    <script>
        $(function () {
            $("#startDate").attr("class", "Wdate").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd', onpicked: doQuery});
            });
            $("#endDate").attr("class", "Wdate").click(function () {
                WdatePicker({dateFmt: 'yyyy-MM-dd', onpicked: doQuery});
            });
            $("input[name='startDate']").val("${startDate}");
            $("input[name='endDate']").val("${endDate}");

            var ulWidth =
            ${retList.size()} *
            1000;
            $("#myUl").css("width", ulWidth + "px");
        })
        function showDetail(examId){
            createdetailwindow("查看","tBHiddenDangerExamController.do?goUpdate&load=detail&id="+examId,950,600);
        }
    </script>
</head>
<body style="background-color: #ececec;">

<div style="float: left; margin-left: 40px; margin-top: 5px; ">
    <form id="thisForm" action="tBDecisionAnalyseController.do?hiddenDangerUnbindDangerSource" method="post">
        <input id="startDate" name="startDate" value="${startDate}"
               style="height: 23px; width: 110px; border-radius: 5px; "/>
        &nbsp;~&nbsp;
        <input id="endDate" name="endDate" value="${endDate}" style="height: 23px; width: 110px; border-radius: 5px; "/>
        &nbsp;&nbsp;&nbsp;
        <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="doQuery()" id=""
           style="margin-top: 2px; ">刷新</a>
    </form>
</div>

<div style="float: right; margin-top: 0px; margin-bottom: 8px; margin-right: 40px; overflow: hidden;">
    <marquee direction=left scrollamount=4 onmouseover="this.stop()" onmouseout="this.start()"
             style="height:30px; width: 1000px">
        <c:forEach items="${retList}" var="notice">
            <ul id="myUl">
                <li style="height: auto;width: 1000px; float: left;">
                    <a href="#" onclick="showDetail('${notice.id}');">
                    超期隐患公告：<img src="images/42_0000.gif"/>${notice.fineDate}&emsp;责任部门：${notice.dutyUnit}&emsp;责任人：${notice.dutyMan}&emsp;隐患描述：${notice.desc}
                    </a>
                </li>
            </ul>
        </c:forEach>
    </marquee>
    &emsp;
    <a href="#" class="easyui-linkbutton" onclick="setChildWinByUser()">
        <c:if test="${retList.size() gt 0}">
            <img src="images/011k.gif" width="28px" height="28px"/>
        </c:if>
        <i class="fa fa-gear"></i>&nbsp;配置我的首页</a>
</div>

<div style="clear: both; margin-top: 5px; margin-bottom: 5px;"></div>

<div class="container-fluid" style="height: 90%;background-color: #ececec;">
    <div class="row-fluid">
        <div class="col-xs-12 col-md-3 col-lg-3" title="${childWindowSetting.leftTop.childWindowTitle}"
             style="height: 50%;padding: 10px;">
            <c:if test="${childWindowSetting.leftTop.isShowFullScreenBtn eq '1'}">
                <button type="button" class="btn btn-primary btn-xs"
                        onclick="fullScreen('${childWindowSetting.leftTop.childWindowTitle}','${childWindowSetting.leftTop.childWindowFrameId}')"
                        style="position: absolute;top: 20px;right:20px; background-color: #61acf5; border-color: #61acf5;">
                    全屏
                </button>
            </c:if>
            <iframe id="${childWindowSetting.leftTop.childWindowFrameId}"
                    src='${childWindowSetting.leftTop.childWindowUrl}' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
        <div class="col-xs-12 col-md-6 col-lg-6" title="${childWindowSetting.centerTop.childWindowTitle}"
             style="height: 50%;padding: 10px;">
            <c:if test="${childWindowSetting.centerTop.isShowFullScreenBtn eq '1'}">
                <button type="button" class="btn btn-primary btn-xs"
                        onclick="fullScreen('${childWindowSetting.centerTop.childWindowTitle}','${childWindowSetting.centerTop.childWindowFrameId}')"
                        style="position: absolute;top: 20px;right:20px; background-color: #61acf5; border-color: #61acf5;">
                    <i class="fa fa-arrows-alt"></i>全屏
                </button>
            </c:if>
            <iframe id="${childWindowSetting.centerTop.childWindowFrameId}"
                    src='${childWindowSetting.centerTop.childWindowUrl}' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
        <div class="col-xs-12 col-md-3 col-lg-3" title="${childWindowSetting.rightTop.childWindowTitle}"
             style="height: 50%;padding: 10px;">
            <c:if test="${childWindowSetting.rightTop.isShowFullScreenBtn eq '1'}">
                <button type="button" class="btn btn-primary btn-xs"
                        onclick="fullScreen('${childWindowSetting.rightTop.childWindowTitle}','${childWindowSetting.rightTop.childWindowFrameId}')"
                        style="position: absolute;top: 20px;right:20px; background-color: #61acf5; border-color: #61acf5;">
                    <i class="fa fa-arrows-alt"></i>全屏
                </button>
            </c:if>
            <iframe id="${childWindowSetting.rightTop.childWindowFrameId}"
                    src='${childWindowSetting.rightTop.childWindowUrl}' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
        <div class="col-xs-12 col-md-3 col-lg-3" title="${childWindowSetting.leftBottom.childWindowTitle}"
             style="height: 50%;padding: 10px;">
            <c:if test="${childWindowSetting.leftBottom.isShowFullScreenBtn eq '1'}">
                <button type="button" class="btn btn-primary btn-xs"
                        onclick="fullScreen('${childWindowSetting.leftBottom.childWindowTitle}','${childWindowSetting.leftBottom.childWindowFrameId}')"
                        style="position: absolute;top: 20px;right:20px; background-color: #61acf5; border-color: #61acf5;">
                    <i class="fa fa-arrows-alt"></i>全屏
                </button>
            </c:if>
            <iframe id="${childWindowSetting.leftBottom.childWindowFrameId}"
                    src='${childWindowSetting.leftBottom.childWindowUrl}' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
        <div class="col-xs-12 col-md-6 col-lg-6" title="${childWindowSetting.centerBottom.childWindowTitle}"
             style="height: 50%;padding: 10px;">
            <c:if test="${childWindowSetting.centerBottom.isShowFullScreenBtn eq '1'}">
                <button type="button" class="btn btn-primary btn-xs"
                        onclick="fullScreen('${childWindowSetting.centerBottom.childWindowTitle}','${childWindowSetting.centerBottom.childWindowFrameId}')"
                        style="position: absolute;top: 20px;right:20px; background-color: #61acf5; border-color: #61acf5;">
                    <i class="fa fa-arrows-alt"></i>全屏
                </button>
            </c:if>
            <iframe id="${childWindowSetting.centerBottom.childWindowFrameId}"
                    src='${childWindowSetting.centerBottom.childWindowUrl}' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
        <div class="col-xs-12 col-md-3 col-lg-3" title="${childWindowSetting.rightBottom.childWindowTitle}"
             style="height: 50%;padding: 10px;">
            <c:if test="${childWindowSetting.rightBottom.isShowFullScreenBtn eq '1'}">
                <button type="button" class="btn btn-primary btn-xs"
                        onclick="fullScreen('${childWindowSetting.rightBottom.childWindowTitle}','${childWindowSetting.rightBottom.childWindowFrameId}')"
                        style="position: absolute;top: 20px;right:20px; background-color: #61acf5; border-color: #61acf5;">
                    <i class="fa fa-arrows-alt"></i>全屏
                </button>
            </c:if>
            <iframe id="${childWindowSetting.rightBottom.childWindowFrameId}"
                    src='${childWindowSetting.rightBottom.childWindowUrl}' style="height: 100%;width: 100%;"
                    frameborder="0"></iframe>
        </div>
    </div>
</div>

<script>
    function doQuery() {
        var startDate = $("input[name='startDate']").val();
        var endDate = $("input[name='endDate']").val();
       // $('#overTrendFrame').attr('src', "homePageController.do?overallTrend&startDate=" + startDate + "&endDate=" + endDate);
        $('#hiddDangerByGroupFrame').attr('src', "homePageController.do?hiddenDangerByGroup&startDate="+startDate+"&endDate="+endDate);
        $('#hiddDangerByProfFrame').attr('src', "homePageController.do?hiddenDangerByProfession&startDate="+startDate+"&endDate="+endDate);
    }

    function fullScreen(title, iframeId) {
        var url = $("#" + iframeId).attr("src");
        if ("hiddDangerByGroupFrame" == iframeId) {
            url += "&flag=1";
        }
        createdetailwindow(title, url, $(window).width(), $(window).height());
    }

    function setChildWinByUser() {
        if (typeof(windowapi) == 'undefined') {
            $.dialog({
                content: 'url:childWindowController.do?goEditUserChildWin',
                zIndex: 2100,
                title: '首页配置',
                lock: true,
                width: 800,
                height: 350,
                left: '45%',
                top: '45%',
                opacity: 0.4,
                button: [
                    {
                        name: '<t:mutiLang langKey="common.confirm"/>',
                        callback: clickcallback_childWinByUser,
                        focus: true
                    },
                    {
                        name: '<t:mutiLang langKey="common.close"/>', callback: function () {}
                    }
                ]
            });
        } else {
            $.dialog({
                content: 'url:childWindowController.do?goEditUserChildWin',
                zIndex: 2100,
                title: '首页配置',
                lock: true,
                parent: windowapi,
                width: 800,
                height: 350,
                left: '45%',
                top: '45%',
                opacity: 0.4,
                button: [
                    {
                        name: '<t:mutiLang langKey="common.confirm"/>',
                        callback: clickcallback_childWinByUser,
                        focus: true
                    },
                    {
                        name: '<t:mutiLang langKey="common.close"/>', callback: function () {}
                    }
                ]
            });
        }
    }

    function clickcallback_childWinByUser() {
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
            beforeSend: function (data) {
                $.messager.progress({
                    text: "正在保存......",
                    interval: 100
                });
            },
            cache: false,
            success: function (data) {
                var d = $.parseJSON(data);
                var msg = d.msg;
                tip(msg);
            },
            complete: function (data) {
                $.messager.progress('close');
                setTimeout(refreshWindow, 1000 * 2);
            },
            error: function (data) {
                tip("操作失败");
            }
        });
    }

    function refreshWindow() {
        window.location.href = "loginController.do?hplushome";
    }
</script>
</body>
</html>
