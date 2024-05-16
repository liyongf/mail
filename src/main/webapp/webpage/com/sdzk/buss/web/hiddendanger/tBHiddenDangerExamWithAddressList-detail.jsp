<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>隐患检查</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <link href="plug-in/ystep-master/css/ystep.css" rel="stylesheet">
    <link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
    <script type="text/javascript" src="plug-in/ystep-master/js/ystep.js"></script>
    <script type="text/javascript">

        $(document).ready(function(){
            $.ajax({
                type: 'POST',
                url: 'tBHiddenDangerExamController.do?getHandleStepList',
                data: {examId:"${tBHiddenDangerExamPage.id}"},
                success:function(data){
                    var result = jQuery.parseJSON(data);
                    $("#flow").loadStep({
                        size: "large",
                        color: "blue",
                        steps: result
                    });
                    var currentStep = "${fn:length(handleStepList)}";
                    var step = parseInt(currentStep);
                    $("#flow").setStep(step);
                },
                error:function(data){
                }
            });
        });

        function goRiskDetails(){
            createdetailwindow("查看风险","tBDangerSourceController.do?goDetail&dangerId=${tBHiddenDangerExamPage.dangerId.id}",800,600);
        }
    </script>
</head>
<body>
<div title="流程图" id="flow" style="text-align:center;width: 80%;margin:20px auto;position: relative;top:60px;${tBHiddenDangerExamPage.handleEntity.handlelStatus=='00'?'display:none;':''}"></div>

<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBHiddenDangerExamController.do?doUpdate" tiptype="1">
<input id="id" name="id" type="hidden" value="${tBHiddenDangerExamPage.id }">
<input id="createName" name="createName" type="hidden" value="${tBHiddenDangerExamPage.createName }">
<input id="createBy" name="createBy" type="hidden" value="${tBHiddenDangerExamPage.createBy }">
<input id="createDate" name="createDate" type="hidden" value="${tBHiddenDangerExamPage.createDate }">
<input id="updateName" name="updateName" type="hidden" value="${tBHiddenDangerExamPage.updateName }">
<input id="updateBy" name="updateBy" type="hidden" value="${tBHiddenDangerExamPage.updateBy }">
<input id="updateDate" name="updateDate" type="hidden" value="${tBHiddenDangerExamPage.updateDate }">
<input id="examType" name="examType" type="hidden" value="${examType}">
<input type="hidden" id="selectedFineEmp" name="selectedFineEmp"/>
<table style="width: 80%;margin:0 auto;position: relative;top: 60px;" cellpadding="0" cellspacing="1" class="formtable">
<%--<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">--%>

    <tr>
        <td align="center"  colspan="4" class="value">
            <label class="Validform_label">
                日期：<fmt:formatDate value='${tBHiddenDangerExamPage.examDate}' type="date" pattern="yyyy-MM-dd"/>
            </label>&emsp;
            <label class="Validform_label">
                班次：${tBHiddenDangerExamPage.shiftTemp}
            </label>
            <label class="Validform_label">
                &nbsp;&nbsp;&nbsp;&nbsp;隐患来源：${tBHiddenDangerExamPage.originTemp}
            </label>
        </td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                地点:
            </label>
        </td>
        <td class="value">
            <label>${tBHiddenDangerExamPage.address.address}</label>
        </td>
        <td align="right">
            <label class="Validform_label">
                检查人:
            </label>
        </td>
        <td class="value">
            <label>${tBHiddenDangerExamPage.fillCardManNames}</label>
            <c:if test="${examType eq 'glgbxj'}">
                <label>
                    ${ tBHiddenDangerExamPage.isWithClass eq "1"?"带班":""}
                </label>
            </c:if>
        </td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                下井时间:
            </label>
        </td>
        <td class="value" colspan="3">
            <label>
                    <fmt:formatDate value='${tBHiddenDangerExamPage.beginWellDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>&emsp;至&emsp;<fmt:formatDate value='${tBHiddenDangerExamPage.endWellDate}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
            </label>
        </td>
    </tr>

    <tr>
        <td align="right">
            <label class="Validform_label">
                危险源:
            </label>
        </td>
        <td class="value" colspan="3">
            <label>${tBHiddenDangerExamPage.dangerId.hazard.hazardName}</label>
        </td>
    </tr>

        <%--关键字--%>
   <%-- <tr>
        <td align="right">
            <label class="Validform_label">
                关键字:
            </label>
        </td>
        <td class="value" colspan="3">
            <label>${tBHiddenDangerExamPage.keyWords}</label>
        </td>
    </tr>--%>
        <%--关键字--%>
    <tr>
        <td align="right">
            <label class="Validform_label">
                风险等级:
            </label>
        </td>
        <td class="value" colspan="3">
            <label>
                <c:if test="${not empty dangerSourceRiskValueTemp}">
                    <div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left">
                        <input class="minicolors-input" readOnly="true"
                               style="border:0px;width: 80px; padding-left: 26px;color: #333;" type="text"
                               value="${dangerSourceRiskValueTemp}">
								<span class="minicolors-swatch" style="top:0px;"><span
                                        class="minicolors-swatch-color"
                                        style="background-color: ${dangerSourceAlertColor};"></span></span>
                    </div>
                    </div>
                </c:if>
            </label>
        </td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                风险描述:
            </label>
        </td>
        <td class="value" colspan="3">
            <a href="#" onclick="goRiskDetails()">${tBHiddenDangerExamPage.dangerId.yePossiblyHazard}</a>
        </td>
    </tr>

    <tr>
        <td align="right">
            <label class="Validform_label">
                责任单位:
            </label>
        </td>
        <td class="value">
            <label>${tBHiddenDangerExamPage.dutyUnit.departname}</label>
        </td>
        <td align="right">
            <label class="Validform_label">
                责任人:
            </label>
        </td>
        <td class="value">
            <label>${tBHiddenDangerExamPage.dutyMan}</label>
        </td>
    </tr>

    <tr>
        <td align="right">
            <label class="Validform_label">
                督办单位:
            </label>
        </td>
        <td class="value" colspan="3">
                ${departNameTemp}
        </td>
    </tr>

    <tr>
        <td align="right">
            <label class="Validform_label">
                隐患类别:
            </label>
        </td>
        <td class="value">
            <label>${tBHiddenDangerExamPage.hiddenCategoryTemp}</label>
        </td>
        <td align="right">
            <label class="Validform_label">
                隐患等级:
            </label>
        </td>
        <td class="value">
            <label>${tBHiddenDangerExamPage.hiddenNatureTemp}</label>
        </td>
    </tr>
    <c:if test="${examType eq 'zyks'}">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    专业类型:
                </label>
            </td>
            <td class="value" colspan="3">
                <label>${tBHiddenDangerExamPage.proTypeTemp}</label>
            </td>
        </tr>
    </c:if>
    <tr>
        <td align="right">
            <label class="Validform_label">
                隐患类型:
            </label>
        </td>
        <td class="value" colspan="3">
                ${tBHiddenDangerExamPage.hiddenTypeTemp}
        </td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                问题描述:
            </label>
        </td>
        <%--<td class="value" colspan="3" width="80%">--%>
            <%--<label>${tBHiddenDangerExamPage.problemDesc}</label>--%>
        <%--</td>--%>
        <td  class="value" colspan="3" width="80%" >
            <textarea  style="width:100%;height: 50px;overflow-y: auto">
                    ${tBHiddenDangerExamPage.problemDesc}
            </textarea>
        </td>
    </tr>
    <tr>
        <td align="right">
            <label class="Validform_label">
                隐患处理:
            </label>
        </td>
        <td class="value" colspan="3">
            <label>${tBHiddenDangerExamPage.dealType eq "1"? "限期整改":"现场处理"}</label>
        </td>
    </tr>
    </tr>
    <tr id="dealTypetr1" style="${tBHiddenDangerExamPage.dealType eq '1'? '':'display: none'}">
        <td align="right">
            <label class="Validform_label">
                限期日期:
            </label>
        </td>
        <td class="value" colspan="3">
            <label><fmt:formatDate value='${tBHiddenDangerExamPage.limitDate}' type="date" pattern="yyyy-MM-dd"/></label>
        </td>
    </tr>
    <c:if test="${tBHiddenDangerExamPage.handleEntity.handlelStatus eq '4'||tBHiddenDangerExamPage.handleEntity.handlelStatus eq '5'||tBHiddenDangerExamPage.handleEntity.handlelStatus eq '3'}">
        <tr>
            <td align="right">
                <label class="Validform_label">
                    整改措施：
                </label>
            </td>
            <td class="value" colspan="3">
                <textarea style="width:100%;height: 50px;overflow-y: auto">
                        ${tBHiddenDangerExamPage.modifyRemarkTemp}
                </textarea>
            </td>
        </tr>
    </c:if>
    <c:if test="${tBHiddenDangerExamPage.handleEntity.handlelStatus eq '5'}">
        <tr >
            <td align="right">
                <label class="Validform_label">
                    复查情况：
                </label>
            </td>
            <td class="value" colspan="3">
                <div style="${tBHiddenDangerExamPage.handleEntity.handlelStatus eq '5'? '':'display: none'}">
                    <textarea style="width:100%;height: 50px;overflow-y: auto">
                            ${tBHiddenDangerExamPage.reviewDetailTemp}
                    </textarea>
                </div>
            </td>
        </tr>
    </c:if>
        <%--轮播图片的遍历 /sdzk_mine  test="${!empty list}">--%>
    <c:if test="${!empty imagelists}" >
        <tr >
            <td align="right">
                <label class="Validform_label">
                    现场图片：
                </label>
            </td>
            <td class="value" colspan="3">
                <div >
                    <c:forEach items="${imagelists}" var="imagePath" >
                        <a href="#" onclick="goLargerimage('uploadfile/${tBHiddenDangerExamPage.mobileId}/${imagePath}')" >
                            <%--<img src="http://localhost:8090/sdzk_mine/${imagePath}" style="height: 100px;width:auto;"/>--%>
                                <img src="uploadfile/${tBHiddenDangerExamPage.mobileId}/${imagePath}" style="height: 100px;width:auto;margin: 0 3px;"/>
                        </a>
                    </c:forEach>
                </div>
            </td>
        </tr>
    </c:if>

</table>
</t:formvalid>

<script >
    function goLargerimage(path){
        createdetailwindow("大图","tBHiddenDangerExamController.do?goLargerimage&path="+path,700,550);
    }
</script>
</body>
<script src = "webpage/com/sdzk/buss/web/hiddendanger/js/tBHiddenDangerExam.js"></script>