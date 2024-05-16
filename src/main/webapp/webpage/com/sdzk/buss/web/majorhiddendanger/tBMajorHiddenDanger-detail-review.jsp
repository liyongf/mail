<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>重大隐患</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>

  <script type="text/javascript">
  //编写自定义JS代码
  </script>

 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBMajorHiddenDangerController.do?doUpdate" >
					<input id="id" name="id" type="hidden" value="${tBMajorHiddenDangerPage.id }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right">
							<label class="Validform_label">
								隐患地点:
							</label>
						</td>
						<td class="value">
                                ${tBMajorHiddenDangerPage.hdLocationName}
						</td>
						<td align="right">
							<label class="Validform_label">
								排查日期:
							</label>
						</td>
						<td class="value">
                            <fmt:formatDate value='${tBMajorHiddenDangerPage.inveDate}' type="date" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						隐患信息来源:
					</label>
				</td>
				<td class="value">
						${tBMajorHiddenDangerPage.hdInfoSourceTemp}
				</td>
				<td align="right">
					<label class="Validform_label">
						隐患等级:
					</label>
				</td>
				<td class="value">

						${tBMajorHiddenDangerPage.hdLevelTemp}
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						隐患类别:
					</label>
				</td>
				<td class="value">
						${tBMajorHiddenDangerPage.hdCateTemp}
				</td>
				<td align="right">
					<label class="Validform_label">
						隐患专业:
					</label>
				</td>
				<td class="value">
						${tBMajorHiddenDangerPage.hdMajorTemp}
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						隐患类型:
					</label>
				</td>
				<td class="value" colspan="3">
						${tBMajorHiddenDangerPage.hiddenTypeTemp}
				</td>
			</tr>
                    <%--<tr>--%>

                        <%--<td align="right">--%>
                            <%--<label class="Validform_label">--%>
                                <%--关键字:--%>
                            <%--</label>--%>
                        <%--</td>--%>
                        <%--<td class="value" colspan="3">--%>
                                <%--${tBMajorHiddenDangerPage.keyWords}--%>
                        <%--</td>--%>
                    <%--</tr>--%>
            <%--<tr>--%>
                <%--<td align="right">--%>
                    <%--<label class="Validform_label">--%>
                        <%--危险源:--%>
                    <%--</label>--%>
                <%--</td>--%>
                <%--<td class="value" colspan="3">--%>
                        <%--${tBMajorHiddenDangerPage.dangerId.hazard.hazardName}--%>
                <%--</td>--%>
            <%--</tr>--%>

					<tr>
						<td align="right">
							<label class="Validform_label">
								整改时限:
							</label>
						</td>
						<td class="value">
                            <fmt:formatDate value='${tBMajorHiddenDangerPage.rectPeriod}' type="date" pattern="yyyy-MM-dd"/>
						</td>
                        <td align="right">
                            <label class="Validform_label">
                                责任单位:
                            </label>
                        </td>
                        <td class="value">
                                ${tBMajorHiddenDangerPage.dutyUnitName}
                        </td>
					</tr>
				<tr>

					<td align="right">
						<label class="Validform_label">
							责任人:
						</label>
					</td>
					<td class="value" colspan="3">
                            ${tBMajorHiddenDangerPage.dutyMan}
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							隐患描述:
						</label>
					</td>
					<td class="value" colspan="3">
                            ${tBMajorHiddenDangerPage.hdDesc}
					</td>
				</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						整改单位:
					</label>
				</td>
				<td class="value">
                        ${tBMajorHiddenDangerPage.rectUnitName}
				</td>
				<td align="right">
					<label class="Validform_label">
						整改责任人:
					</label>
				</td>
				<td class="value">
                        ${tBMajorHiddenDangerPage.rectUnitName}
				</td>
			<tr>
				<td align="right">
					<label class="Validform_label">
						整改完成日期:
					</label>
				</td>
				<td class="value">
                    <fmt:formatDate value='${tBMajorHiddenDangerPage.rectTagartDt}' type="date" pattern="yyyy-MM-dd"/>
				</td>
				<td align="right">
					<label class="Validform_label">
						整改措施:
					</label>
				</td>
				<td class="value">
				${tBMajorHiddenDangerPage.rectMeasures}
				</td>
			</tr >
			<tr>
				<td align="right">
					<label class="Validform_label">
						验收时间:
					</label>
				</td>
				<td class="value">
                    <fmt:formatDate value='${tBMajorHiddenDangerPage.accepTime}' type="date" pattern="yyyy-MM-dd"/>
				</td>
				<td align="right">
					<label class="Validform_label">
						验收人:
					</label>
				</td>
				<td class="value">
                        ${tBMajorHiddenDangerPage.acceptorName}
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						验收情况:
					</label>
				</td>
				<td class="value" colspan="3">
                        ${tBMajorHiddenDangerPage.accepReport}
				</td>
			</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger.js"></script>

