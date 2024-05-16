<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>重大隐患五落实</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
		<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBMajorHiddenDangerController.do?doUpdateFiveImpl" >
					<input id="id" name="id" type="hidden" value="${tBMajorHiddenDangerPage.id }">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
					<tr>
						<td align="right" rowspan="2">
							<label class="Validform_label">
								纳入治理计划情况:
							</label>
						</td>
						<td class="value">
							<label>
								<input id="isGovePlanAchieve" name="isGovePlanAchieve" type="checkbox" value="1" ${tBMajorHiddenDangerPage.isGovePlanAchieve eq '1'?'checked="checked"':''} />&nbsp;&nbsp;&nbsp;&nbsp;已到位
							</label>
						</td>
						<td align="right">
							纳入治理计划日期:
						</td>
						<td class="value">
                            <fmt:formatDate value='${tBMajorHiddenDangerPage.govePlanDate}' type="date" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
					<tr>
						<td class="value" colspan="4">
							<textarea  id="govePlan" name="govePlan"  style="width: 80%;height: auto;" class="inputxt" rows="6">${tBMajorHiddenDangerPage.govePlan}</textarea>
						</td>
					</tr>
			<tr>
				<td align="right" rowspan="2">
					<label class="Validform_label">
						整改责任到位情况:
					</label>
				</td>
				<td class="value">
					<label>
						<input id="isRespAchieve" name="isRespAchieve" type="checkbox" value="1" ${tBMajorHiddenDangerPage.isRespAchieve eq '1'?'checked="checked"':''} />&nbsp;&nbsp;&nbsp;&nbsp;已到位
					</label>
				</td>
				<td align="right">
					整改责任到位日期:
				</td>
				<td class="value">
                    <fmt:formatDate value='${tBMajorHiddenDangerPage.respAchDate}' type="date" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<td class="value" colspan="4">
					<textarea  id="respAch" name="respAch"  style="width: 80%;height: auto;" class="inputxt" rows="6">${tBMajorHiddenDangerPage.respAch}</textarea>
				</td>
			</tr>
			<tr>
				<td align="right" rowspan="2">
					<label class="Validform_label">
						整改资金到位情况:
					</label>
				</td>
				<td class="value">
					<label>
						<input id="isFundAchieve" name="isFundAchieve" type="checkbox" value="1" ${tBMajorHiddenDangerPage.isFundAchieve eq '1'?'checked="checked"':''} />&nbsp;&nbsp;&nbsp;&nbsp;已到位
					</label>
				</td>
				<td align="right">
					整改资金到位日期:
				</td>
				<td class="value">
                    <fmt:formatDate value='${tBMajorHiddenDangerPage.fundAchDate}' type="date" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<td class="value" colspan="4">
					<textarea  id="fundAch" name="fundAch"  style="width: 80%;height: auto;" class="inputxt" rows="6">${tBMajorHiddenDangerPage.fundAch}</textarea>
				</td>
			</tr>
			<tr>
				<td align="right" rowspan="2">
					<label class="Validform_label">
						整改预案到位情况:
					</label>
				</td>
				<td class="value">
					<label>
						<input id="isPlanAchieve" name="isPlanAchieve" type="checkbox" value="1" ${tBMajorHiddenDangerPage.isPlanAchieve eq '1'?'checked="checked"':''} />&nbsp;&nbsp;&nbsp;&nbsp;已到位
					</label>
				</td>
				<td align="right">
					整改预案到位日期:
				</td>
				<td class="value">
                    <fmt:formatDate value='${tBMajorHiddenDangerPage.planAchDate}' type="date" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<td class="value" colspan="4">
					<textarea  id="planAch" name="planAch"  style="width: 80%;height: auto;" class="inputxt" rows="6">${tBMajorHiddenDangerPage.planAch}</textarea>
				</td>
			</tr>
			<tr>
				<td align="right" rowspan="2">
					<label class="Validform_label">
						治理措施到位情况:
					</label>
				</td>
				<td class="value">
					<label>
						<input id="isMeasureAchieve" name="isMeasureAchieve" type="checkbox" value="1" ${tBMajorHiddenDangerPage.isMeasureAchieve eq '1'?'checked="checked"':''} />&nbsp;&nbsp;&nbsp;&nbsp;已到位
					</label>
				</td>
				<td align="right">
					治理措施到位日期:
				</td>
				<td class="value">
                    <fmt:formatDate value='${tBMajorHiddenDangerPage.measureAchDate}' type="date" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<td class="value" colspan="4">
					<textarea  id="measureAch" name="measureAch"  style="width: 80%;height: auto;" class="inputxt" rows="6">${tBMajorHiddenDangerPage.measureAch}</textarea>
				</td>
			</tr>
			<tr>
				<td align="right" rowspan="2">
					<label class="Validform_label">
						整改目标到位情况:
					</label>
				</td>
				<td class="value">
					<label>
						<input id="isGoalAchieve" name="isGoalAchieve" type="checkbox" value="1" ${tBMajorHiddenDangerPage.isGoalAchieve eq '1'?'checked="checked"':''} />&nbsp;&nbsp;&nbsp;&nbsp;已到位
					</label>
				</td>
				<td align="right">
					整改目标到位日期:
				</td>
				<td class="value">
                    <fmt:formatDate value='${tBMajorHiddenDangerPage.goalAchDate}' type="date" pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<td class="value" colspan="4">
					<textarea  id="goalAch" name="goalAch"  style="width: 80%;height: auto;" class="inputxt" rows="6">${tBMajorHiddenDangerPage.goalAch}</textarea>
				</td>
			</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger.js"></script>		
