<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>隐患检查</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<link href="plug-in/ystep-master/css/ystep.css" rel="stylesheet">
	<script type="text/javascript" src="plug-in/ystep-master/js/ystep.js"></script>
	<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">

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

		$(function(){

			var interval;
			$(".container img").click(function cover(){
				$(this).addClass("zoom").fadeOut(700,append);
				function append(){
					$(this).removeClass("zoom").appendTo(".container").show();
					var name = $(".container").children("img").first().attr("alt");
					$(".name p").text("No "+name);
				}

			})

			function auto(){
				var play = $(".container").children("img").first();
				play.addClass("zoom").fadeOut(700,append);
				function append(){
					$(this).removeClass("zoom").appendTo(".container").show();
					var name = $(this).parent().children("img").first().attr("alt");
					$(".name p").text("No "+name);
				}
				interval = setTimeout(auto,5000);
			}

			$(".container img").hover(function(){
				stopPlay();
			},function(){
				interval = setTimeout(auto,5000);
			})

			function stopPlay(){
				clearTimeout(interval)
			}
			auto();

		});

        function goRiskDetails(){
            createdetailwindow("查看风险","tBDangerSourceController.do?goDetail&dangerId=${tBHiddenDangerExamPage.dangerId.id}",800,600);
        }
    </script>
</head>
<body>

<div title="流程图" id="flow" style="text-align:center;width: 80%;margin:20px auto;position: relative;top:40px;${tBHiddenDangerExamPage.handleEntity.handlelStatus=='00'?'display:none;':''}"></div>

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
	<table style="width: 80%;margin:0 auto;position: relative;top:60px;" cellpadding="3" cellspacing="1" class="formtable">
		<tr>
			<td align="center"  colspan="4" class="value">
				<label class="Validform_label">
					日期：<fmt:formatDate value='${tBHiddenDangerExamPage.examDate}' type="date" pattern="yyyy-MM-dd"/>
				</label>&emsp;
				<label class="Validform_label">
                    班次：
                    <%--${tBHiddenDangerExamPage.limitDateTemp }    这个代码是冉哥给的例子--%>
                    ${tBHiddenDangerExamPage.shiftTemp}
				</label>
				<label class="Validform_label">
					&nbsp;&nbsp;&nbsp;&nbsp;信息来源：${tBHiddenDangerExamPage.manageTypeTemp}
				</label>
			</td>
		</tr>
		<c:if test="${huayuan eq 'true'}">
			<tr>
				<td align="right">
					<label class="Validform_label">
						隐患单号:
					</label>
				</td>
				<td class="value" colspan="3">
						${tBHiddenDangerExamPage.hiddenNumber}

				</td>
			</tr>
		</c:if>
		<tr>
			<td align="right">
				<label class="Validform_label">
					风险点:
				</label>
			</td>
			<td class="value">
				${tBHiddenDangerExamPage.address.address}

			</td>
			<td align="right">
				<label class="Validform_label">
					检查人:
				</label>
			</td>
			<td class="value">
                    ${tBHiddenDangerExamPage.fillCardManNames}
			</td>
		</tr>

        <tr>
			<td align="right">
				<label class="Validform_label">
					责任单位:
				</label>
			</td>
			<td class="value">
				${tBHiddenDangerExamPage.dutyUnit.departname}
			</td>
			<td align="right">
				<label class="Validform_label">
					责任人:
				</label>
			</td>
			<td class="value">
				${tBHiddenDangerExamPage.dutyMan}
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					管控责任单位:
				</label>
			</td>
			<td class="value">
					${tBHiddenDangerExamPage.manageDutyUnit.departname}
			</td>
			<td align="right">
				<label class="Validform_label">
					管控责任人:
				</label>
			</td>
			<td class="value">
					${tBHiddenDangerExamPage.manageDutyManTemp}
			</td>
		</tr>

		<tr>
			<td align="right">
				<label class="Validform_label">
					隐患类型:
				</label>
			</td>
			<td class="value">
				${tBHiddenDangerExamPage.riskTypeTemp}
			</td>
			<td align="right">
				<label class="Validform_label">
					隐患等级:
				</label>
			</td>
			<td class="value">
				${tBHiddenDangerExamPage.hiddenNatureTemp}
			</td>
		</tr>
		<c:if test="${newPost ne 'true'}">
			<tr>
				<td align="right">
					<label class="Validform_label">
						岗位:
					</label>
				</td>
				<td class="value" colspan="3">
						${tBHiddenDangerExamPage.post.postName}
				</td>
			</tr>
		</c:if>
		<tr>
			<td align="right">
				<label class="Validform_label">
					风险描述:
				</label>
			</td>
			<td  class="value" colspan="3" width="80%" >
			<textarea  style="width:100%;height: 35px;overflow-y: auto">
					${tBHiddenDangerExamPage.riskId.riskDesc}
			</textarea>
			</td>
		</tr>
		<c:if test="${xiezhuang eq 'true'}">
			<tr>
				<td align="right">
					<label class="Validform_label">
						危害因素:
					</label>
				</td>
				<td  class="value" colspan="3" width="80%" >
			<textarea  style="width:100%;height: 35px;overflow-y: auto">
					${tBHiddenDangerExamPage.hazardFactorName}
			</textarea>
				</td>
			</tr>
		</c:if>
		<tr>
			<td align="right">
				<label class="Validform_label">
					问题描述:
				</label>
			</td>
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
				${tBHiddenDangerExamPage.dealType eq "1"? "限期整改":"现场处理"}
			</td>
            <%--<td align="right">--%>
                <%--<label class="Validform_label">--%>
                    <%--积分值:--%>
                <%--</label>--%>
            <%--</td>--%>
            <%--<td class="value">--%>
            	<%--${tBHiddenDangerExamPage.deductScores}--%>
            <%--</td>--%>
		</tr>
		<tr id="dealTypetr1" style="${tBHiddenDangerExamPage.dealType eq '1'? '':'display: none'}">
			<td align="right">
				<label class="Validform_label">
					限期日期:
				</label>
			</td>
			<td class="value" <c:if test="${beixulou ne 'true'}"> colspan="3" </c:if>>
				<fmt:formatDate value='${tBHiddenDangerExamPage.limitDate}' type="date" pattern="yyyy-MM-dd"/>
			</td>
			<c:if test="${beixulou eq 'true'}">
				<td align="right">
					<label class="Validform_label">
						限期班次:
					</label>
				</td>
				<td class="value">
						${tBHiddenDangerExamPage.limitShiftTemp}
				</td>
			</c:if>
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
								<%--<img src="${imagePath}" style="height: 100px;width:auto;"/>--%>
									<img src="uploadfile/${tBHiddenDangerExamPage.mobileId}/${imagePath}" style="height: 100px;width:auto;margin: 0 3px;" />
							</a>
						</c:forEach>
					</div>
				</td>
			</tr>
		</c:if>
	</table>
</t:formvalid>
<script>
	function goLargerimage(path){
		console.log(path);
		createdetailwindow("大图","tBHiddenDangerExamController.do?goLargerimage&path="+path+"",700,550);
	}
</script>

</body>