<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 2017/11/9
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<html style="width: 98%;">
<head>
    <title>待办任务</title>
    <script src="plug-in/hplus/js/bootstrap.min.js?v=3.3.6"></script>
    <link href="plug-in-ui/hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <script type="text/javascript" src="plug-in/hashMap/HashMap.js"></script>
    <script>
        $(function () {
            $.ajax({
                type: 'POST',
                url: 'homePageController.do?homePageData',
                data: { },
                success: function (data) {
                    var retData = $.parseJSON(data);

                    var daizhenggai = retData.daizhenggai;     //待整改
                    var daipf = retData.daipf;          //待批复
                    var daifucha = retData.daifucha;                //待复查
                    var daishenhe = retData.daishenhe;           //待审核
                    var y_weishouli = retData.y_weishouli;         //未受理计划 月
                    var x_weishouli = retData.x_weishouli;         //未受理计划 旬
                    var z_weishouli = retData.z_weishouli;         //未受理计划 周
                    var endtimelist=retData.endtimelist;          //系统公告
                    var riskTaskList=retData.riskTaskList;
                    var manageTaskList=retData.manageTaskList;
                    drawTable(daizhenggai,daifucha,daishenhe, y_weishouli, x_weishouli, z_weishouli,endtimelist,riskTaskList,manageTaskList,daipf);
                }
            });
        })

        function drawTable(a, b, c, d, e, f, g,h,i,j){
            if("true"=="${yunhe}"){
                $("label")[0].innerHTML = g;
                $("label")[1].innerHTML = a;
                $("label")[2].innerHTML = b;
                $("label")[3].innerHTML = c;
                $("label")[4].innerHTML = h;
                $("label")[5].innerHTML = i;
                if(g>0){
                    $($("label")[0]).attr("style", "color:#ff0000");
                }
            }else if("true"=="${jinyuan}"){
                $("label")[0].innerHTML = a;
                $("label")[1].innerHTML = j;
                $("label")[2].innerHTML = b;
                $("label")[3].innerHTML = c;
                $("label")[4].innerHTML = h;
                $("label")[5].innerHTML = i;
                $("label")[6].innerHTML = g;
            }else{
                $("label")[0].innerHTML = a;
                $("label")[1].innerHTML = b;
                $("label")[2].innerHTML = c;
                $("label")[3].innerHTML = h;
                $("label")[4].innerHTML = i;
                $("label")[5].innerHTML = g;
            }

        }
    </script>
</head>
<body>
    <div>
        <div style="font-family: 'sans-serif'; font-size: 18px; font-weight: bold; text-align: center; margin-bottom: 8px; margin-top: 15px;">待办任务</div>
        <table style="margin:0 auto;position: relative;top:10px;" cellpadding="4" cellspacing="4"
            class="table table-responsive table-bordered table-hover table-striped">
            <%--<thead>--%>
                <%--<tr>--%>
                    <%--<td align="center" colspan="2">--%>
                        <%--待办任务--%>
                    <%--</td>--%>
                <%--</tr>--%>
            <%--</thead>--%>
                <c:if test="${yunhe eq 'true'}">
                    <tr>
                        <td align="left">
                            通知公告：
                        </td>
                        <td align="left">
                            <a href="javascript:addOneTab('系统公告','noticeController.do?tSNotice','default')" onclick="window.parent.moduleClick('fzglLi')"><label></label> 条</a>
                        </td>
                    </tr>
                </c:if>
            <tr>
                <td align="left">
                    待整改的隐患：
                </td>
                <td align="left">
                    <a href="javascript:addOneTab('隐患整改','tBHiddenDangerHandleController.do?checkList','default');" onclick="window.parent.moduleClick('yhpczlLi')"><label></label> 条</a>
                </td>
            </tr>
                <c:if test="${jinyuan eq 'true'}">
                    <tr>
                        <td align="left">
                            待延期批复的隐患：
                        </td>
                        <td align="left">
                            <a href="javascript:addOneTab('隐患延期','tBHiddenDangerHandleController.do?applyDelayList','default')" onclick="window.parent.moduleClick('yhpczlLi')"><label></label> 条</a>
                        </td>
                    </tr>
                </c:if>
            <tr>
                <td align="left">
                    待复查的隐患：
                </td>
                <td align="left">
                    <a href="javascript:addOneTab('隐患复查','tBHiddenDangerHandleController.do?repartList','default');" onclick="window.parent.moduleClick('yhpczlLi')"><label></label> 条</a>
                </td>
            </tr>
            <tr>
                <td align="left">
                    待审核的风险：
                </td>
                <td align="left">
                    <a href="javascript:addOneTab('风险辨识审核','riskIdentificationController.do?reviewList','default');"><label></label> 条</a>
                </td>
            </tr>
                <tr>
                    <td align="left">
                        待处理的辨识任务：
                    </td>
                    <td align="left">
                        <a href="javascript:addOneTab('辨识评估','riskTaskController.do?list','default');"><label></label> 条</a>
                    </td>
                </tr>
                <tr>
                    <td align="left">
                        待处理的管控任务：
                    </td>
                    <td align="left">
                        <a href="javascript:addOneTab('我的任务','riskManageController.do?myManageList','default');" ><label></label> 条</a>
                    </td>
                </tr>
            <%--<tr>
                <td align="left">
                    未受理排查计划：
                </td>
                <td align="left">
                    月
                    <a href="javascript:addOneTab('月排查计划受理','tBInvestigatePlanController.do?acceptList&investType=1','default');" onclick="window.parent.moduleClick('yhpczlLi')"><label></label> 条</a>，
                    旬
                    <a href="javascript:addOneTab('旬排查计划受理','tBInvestigatePlanController.do?acceptList&investType=2','default');" onclick="window.parent.moduleClick('yhpczlLi')"><label></label> 条</a>，
                    周
                    <a href="javascript:addOneTab('周排查计划受理','tBInvestigatePlanController.do?acceptList&investType=3','default');" onclick="window.parent.moduleClick('yhpczlLi')"><label></label> 条</a>
                </td>
            </tr>--%>
                <c:if test="${yunhe ne 'true'}">
                    <tr>
                        <td align="left">
                            通知公告：
                        </td>
                        <td align="left">
                            <a href="javascript:addOneTab('系统公告','noticeController.do?tSNotice','default')" onclick="window.parent.moduleClick('fzglLi')"><label></label> 条</a>
                        </td>
                    </tr>
                </c:if>

            <%--<tr>--%>
                <%--<td align="left">--%>
                    <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：旬--%>
                    <%--<a href="javascript:addOneTab('隐患复查','tBInvestigatePlanController.do?acceptList&investType=2','default');" onclick="window.parent.moduleClick('yhpczlLi')"><label></label> 条</a>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td align="left">--%>
                    <%--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：周--%>
                    <%--<a href="javascript:addOneTab('隐患复查','tBInvestigatePlanController.do?acceptList&investType=3','default');" onclick="window.parent.moduleClick('yhpczlLi')"><label></label> 条</a>--%>
                <%--</td>--%>
            <%--</tr>--%>

        </table>
    </div>
</body>
</html>
