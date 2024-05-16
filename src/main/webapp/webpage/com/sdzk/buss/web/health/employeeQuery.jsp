<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<style>td{	padding:0px;	text-align:center;	vertical-align:center;	}</style>
<div class="operations" style="text-align: left;margin-top: 20px;"> 
	<div class="bd3">
			<span style="display:-moz-inline-box;display:inline-block;">
			<span style="display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; position: relative;top: 3px" title="档案号">档案号：</span>
			<input type="text" id="fileNoQuery" name="fileNoQuery" value="${fileNoQuery}" style="width: 100px;height:28px;border-radius:4px;"  />
			</span>	
			<span style="display:-moz-inline-box;display:inline-block;">
			<span style="display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; position: relative;top: 3px" title="在岗编号">在岗编号：</span>
			<input type="text" id="postNumberQuery" name="postNumberQuery" value="${postNumberQuery}" style="width: 100px;height:28px;border-radius:4px;"  />
			</span>	
			<span style="display:-moz-inline-box;display:inline-block;">
			<span style="display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; position: relative;top: 3px" title="退休编号">退休编号：</span>
			<input type="text" id="retireNumberQuery" name="retireNumberQuery" value="${retireNumberQuery}" style="width: 100px;height:28px;border-radius:4px;"  />
			</span>	
			<span style="display:-moz-inline-box;display:inline-block;">
			<span style="display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; position: relative;top: 3px" title="身份证号">身份证号：</span>
			<input type="text" id="cardNumberQuery" name="cardNumberQuery" value="${cardNumberQuery}" style="width: 100px;height:28px;border-radius:4px;"  />
			</span>	
			<%--<span style="display:-moz-inline-box;display:inline-block;">--%>
			<%--<span style="display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; white-space:nowrap; position: relative;top: 3px" title="姓名">姓名：</span>--%>
			<%--<input name="employeeidQuery" type="hidden" id="employeeidQuery" value="${employeeidQuery}">--%>
			<%--&lt;%&ndash;<input readonly="readonly" name="nameQuery" type="text" style="width: 100px" class="inputxt" value="${nameQuery}">&ndash;%&gt;--%>
					     	 <%--&lt;%&ndash;<t:choose hiddenName="employeeidQuery" hiddenid="id" url="tBEmployeeInfoController.do?employeeselectlist" name="employeeselectlist"&ndash;%&gt;--%>
                          <%--&lt;%&ndash;icon="icon-search" title="职工列表" textname="name,fileNo,postNumber,retireNumber,cardNumber" inputTextname="nameQuery,fileNoQuery,postNumberQuery,retireNumberQuery,cardNumberQuery" isclear="true" isInit="true"></t:choose>&ndash;%&gt;--%>
			<%--</span>	--%>
			<span style="display:-moz-inline-box;display:inline-block;">
            	<span style="display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; white-space:nowrap; position: relative;top: 3px" title="姓名">
             	   姓名：
            	</span>
            	<span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
            		<input name="employeeidQuery" type="hidden" id="employeeidQuery" value="${employeeidQuery}">
					<input name="nameQuery" type="hidden"id="nameQuery" value="${nameQuery}">
                	<div id="employeeidQuerySelect" style="width: 150px;height: 15px;"></div>
        		</span>
        	</span>
			
			<span style="display:-moz-inline-box;display:inline-block;margin-top: 5px;margin-left: 20px;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="searchBtn">查询</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-reload" id="resetBtn">重置</a>
			</span>
			<span>
			<a class="easyui-linkbutton l-btn-plain" id="exportId"  href="#" icon="icon-putout" plain="true">
			<span>导出</span>
			</a>
			</span>
			
	</div>
</div>
<br>
 <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBHealthExamController.do?doUpdate" tiptype="3">
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable" align="center">
		<tr style="height: 40px;">
			     <td colspan='9' align="center" style="font-size:20px;font-weight: bold;">职工个人职业安全卫生档案</td>
			   </tr> 
			   <tr>
			   <td rowspan='4' colspan='2' align="center">
						<label class="Validform_label">
							基本信息
						</label>
					</td>
				<td  align="center" colspan='2'>
						<label class="Validform_label">
							姓名
						</label>
					</td>
					<td  class="value">
						  <input id="name" name="name" type="text" style="width: 150px"   value='${tBEmployeeInfoPage.name}'>
					</td>
				<td  align="center">
						<label class="Validform_label">
							性别
						</label>
					</td>
					<td  class="value">
						<t:dictSelect field="gender" type="list"
										typeGroupCode="sex" defaultVal="${tBEmployeeInfoPage.gender}" hasLabel="false"  title="性别"></t:dictSelect>
					</td>
					<td  align="center">
						<label class="Validform_label">
							身份证号
						</label>
					</td>
					<td  class="value">
						 <input id="cardNumber" name="cardNumber" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.cardNumber}'>
					</td>
				</tr>
				   <tr>
				<td  align="center" colspan='2'>
						<label class="Validform_label">
							参加工作时间
						</label>
					</td>
					<td  class="value">
						  <input id="partiWorkDate" name="partiWorkDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tBEmployeeInfoPage.partiWorkDate}' type="date" pattern="yyyy-MM-dd"/>'>
					</td>
				<td  align="center">
						<label class="Validform_label">
							（现）工作单位
						</label>
					</td>
					<td  class="value">
						<input id="currWorkUnits" name="currWorkUnits" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.currWorkUnits}'>
					</td>
					<td  align="center">
						<label class="Validform_label">
							从事工种
						</label>
					</td>
					<td  class="value">
						 <input id="currTrade" name="currTrade" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.currTrade}'>
					</td>
				</tr>
				<tr>
				<td  align="center" colspan='2'>
						<label class="Validform_label">
							岗位类别
						</label>
					</td>
					<td  class="value">
						  <t:dictSelect field="postCategory" type="list"
										typeGroupCode="postcategory" defaultVal="${tBEmployeeInfoPage.postCategory}" hasLabel="false"  title="岗位类别"></t:dictSelect>
					</td>
				<td  align="center">
						<label class="Validform_label">
							在岗状态
						</label>
					</td>
					<td  class="value">
						<t:dictSelect field="postStatus" type="list"
										typeGroupCode="poststatus" defaultVal="${tBEmployeeInfoPage.postStatus}" hasLabel="false"  title="岗位状态"></t:dictSelect>
					</td>
					<td  align="center">
						<label class="Validform_label">
							接触职业危害种类
						</label>
					</td>
					<td  class="value">
						 <input id="jobHazardCategory" name="jobHazardCategory" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.jobHazardCategory}'>
					</td>
				</tr>
				<tr>
				<td  align="center" colspan='2'>
						<label class="Validform_label">
							岗位变更简历
						</label>
					</td>
					<td  class="value" colspan='5' style="text-align:left;">
						 <textarea id="postChangeResume" style="width:500px;" class="inputxt" rows="10" name="postChangeResume">${tBEmployeeInfoPage.postChangeResume}</textarea>
					</td>
				</tr>
				<tr>
			   <td rowspan='27' align="center">
						<label class="Validform_label">
							职业健康<br>检查
						</label>
					</td>
					<td rowspan='8'>
						<label class="Validform_label">
							上岗前
						</label>
					</td>

					<td colspan="2" align="center">
						<label class="Validform_label">
							检查时间
						</label>
					</td>
					<td  class="value">
						<input id="prejobChkDate" name="prejobChkDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${empty tBEmployeeInfoPage.tBHealthExamEntity.prejobChkDate?"":tBEmployeeInfoPage.tBHealthExamEntity.prejobChkDate}' type="date" pattern="yyyy-MM-dd"/>'>
					</td>
					<td rowspan='12' colspan="2" align="center">
						<label class="Validform_label">
							职业健康疗养
						</label>
					</td>
					<td align="right" rowspan='3'>
							<label class="Validform_label">
								疗养时间:
							</label>
						</td>
						<td class="value" rowspan='3'>
									  <input id="recupDate" name="recupDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${empty tBEmployeeInfoPage.tBHealthRecuperateEntity.recupDate?"":tBEmployeeInfoPage.tBHealthRecuperateEntity.recupDate}' type="date" pattern="yyyy-MM-dd"/>'>
							<span class="Validform_checktip"></span>
						</td>
				</tr>
				 <tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查机构
						</label>
					</td>
					<td  class="value">
						     	 <input id="prejobChkOrg" name="prejobChkOrg" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.prejobChkOrg}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查机构</label>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查类别
						</label>
					</td>
					<td class="value">
									<t:dictSelect field="prejobChkCategory" type="list"
										typeGroupCode="chk_category" defaultVal="${tBEmployeeInfoPage.tBHealthExamEntity.prejobChkCategory}" hasLabel="false"  title="检查类别"></t:dictSelect>     
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查类别</label>
						</td>
				</tr>
				<tr>
					<td rowspan="3" align="center">
						<label class="Validform_label">
							检查结果
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							未见异常
						</label>
					</td>
					<td class="value">
						     	 <input id="prejobNoAbnormal" name="prejobNoAbnormal" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.prejobNoAbnormal}'>
					</td>
					<td align="right" rowspan='3'>
							<label class="Validform_label">
								疗养周期（年）:
							</label>
						</td>
						<td class="value" rowspan='3'>
						     	 <input id="recupCycle" name="recupCycle" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthRecuperateEntity.recupCycle}'>
						</td>
				</tr>
				<tr>
					<td align="center">
						<label class="Validform_label">
							职业禁忌
						</label>
					</td>
					<td class="value">
						     	 <input id="prejobBan" name="prejobBan" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.prejobBan}'>
					</td>
				</tr>
				<tr>
					<td align="center">
						<label class="Validform_label">
							其他疾病
						</label>
					</td>
					<td class="value">
						     	 <input id="prejobOtherDiseases" name="prejobOtherDiseases" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.prejobOtherDiseases}'>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							处置情况
						</label>
					</td>
					<td class="value">
						    <input id="prejobDisposition" name="prejobDisposition" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.prejobDisposition}'>
				</td>
				<td align="right" rowspan='2'>
							<label class="Validform_label">
								疗养 地点:
							</label>
						</td>
						<td class="value" rowspan='2'>
						     	 <input id="recupAddress" name="recupAddress" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthRecuperateEntity.recupAddress}'>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							是否书面告知
						</label>
					</td>
					<td class="value">
						     	 <input id="prejobIsNotify" name="prejobIsNotify" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.prejobIsNotify}'>
						</td>
				</tr>
				<tr>
					<td rowspan='8' align="center">
						<label class="Validform_label">
							在岗期间
						</label>
					</td>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查时间
						</label>
					</td>
					<td class="value">
							 <input id="duringChkDate" name="duringChkDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tBEmployeeInfoPage.tBHealthExamEntity.duringChkDate}' type="date" pattern="yyyy-MM-dd"/>'>
					</td>
					<td align="right" rowspan="2">
							<label class="Validform_label">
								下次疗养时间:
							</label>
						</td>
						<td class="value" rowspan="2">
							<input id="nextRecupDate" name="nextRecupDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tBEmployeeInfoPage.tBHealthRecuperateEntity.nextRecupDate}' type="date" pattern="yyyy-MM-dd"/>'>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查机构
						</label>
					</td>
					<td class="value">
					     	 <input id="duringChkOrg" name="duringChkOrg" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.duringChkOrg}'>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">检查机构在岗期间</label>
					</td>
				</tr>
				 <tr>
					<td rowspan="4" align="center">
						<label class="Validform_label">
							检查结果
						</label>
					</td>
					<td align="center">
						<label class="Validform_label">
							未见异常
						</label>
					</td>
					<td class="value">
						     	 <input id="duringNoAbnormal" name="duringNoAbnormal" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.duringNoAbnormal}'>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">检查结果未见异常</label>
						</td>
						<td align="right" rowspan="2">
							<label class="Validform_label">
								今年是否疗养:
							</label>
						</td>
						<td class="value" rowspan="2">
						     	 <input id="thisYearIsRecup" name="thisYearIsRecup" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthRecuperateEntity.thisYearIsRecup}'>
						</td>
				</tr>
				<tr>
					<td align="center">
						<label class="Validform_label">
							疑是职业病
						</label>
					</td>
					<td class="value">
						     	 <input id="duringOccupationDiseases" name="duringOccupationDiseases" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.duringOccupationDiseases}'>
						</td>
				</tr>
				<tr>
					<td align="center">
						<label class="Validform_label">
							职业禁忌
						</label>
					</td>
					<td class="value">
						     	 <input id="duringBan" name="duringBan" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.duringBan}'>
						</td>
					<td align="center" rowspan="15">
						<label class="Validform_label">
							职业健康培训
						</label>
					</td>
					<td align="center" rowspan="9">
						<label class="Validform_label">
							上岗前
						</label>
					</td>
					<td align="center" rowspan="3">
						<label class="Validform_label">
							培训类型
						</label>
					</td>
					<td class="value" rowspan="3">
									<t:dictSelect field="prejobTrainType" type="list"
										typeGroupCode="chk_category" defaultVal="${tBEmployeeInfoPage.tBHealthTrainEntity.prejobTrainType}" hasLabel="false"  title="培训类型上岗前"></t:dictSelect>
						</td>
				</tr>
				<tr>
					<td align="center">
						<label class="Validform_label">
							其他疾病
						</label>
					</td>
					<td class="value">
						     	 <input id="duringOtherDiseases" name="duringOtherDiseases" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.duringOtherDiseases}'>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							处置情况
						</label>
					</td>
					<td class="value">
						     	 <input id="duringDisposition" name="duringDisposition" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.duringDisposition}'>
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							是否书面告知
						</label>
					</td>
					<td class="value">
						  <input id="duringIsNotify" name="duringIsNotify" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.duringIsNotify}'>
					</td>
					<td align="center" rowspan="2">
						<label class="Validform_label">
							培训时间
						</label>
					</td>
					<td class="value" rowspan="2">
						<input id="prejobTrainDate" name="prejobTrainDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tBEmployeeInfoPage.tBHealthTrainEntity.prejobTrainDate}' type="date" pattern="yyyy-MM-dd"/>'>
					</td>
				</tr>
				<tr>
					<td rowspan='4' align="center">
						<label class="Validform_label">
							离岗时
						</label>
					</td>

					<td colspan="2" align="center">
						<label class="Validform_label">
							检查时间
						</label>
					</td>
					<td class="value">
									  <input id="leavingChkDate" name="leavingChkDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tBEmployeeInfoPage.tBHealthExamEntity.leavingChkDate}' type="date" pattern="yyyy-MM-dd"/>'>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查机构
						</label>
					</td>
					<td class="value">
						     	 <input id="leavingChkOrg" name="leavingChkOrg" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.leavingChkOrg}'>
						</td>
						<td align="center" rowspan="2">
						<label class="Validform_label">
							培训机构
						</label>
					</td>
					<td class="value" rowspan="2">
						     	 <input id="prejobTrainOrg" name="prejobTrainOrg" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthTrainEntity.prejobTrainOrg}'>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查结果
						</label>
					</td>
					<td class="value">
						     	 <input id="leavingChkResult" name="leavingChkResult" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.leavingChkResult}'>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							是否书面告知
						</label>
					</td>
					<td class="value">
						     	 <input id="leavingIsNotify" name="leavingIsNotify" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.leavingIsNotify}'>
					</td>
					<td align="center" rowspan="2">
						<label class="Validform_label">
							综合成绩
						</label>
					</td>
					<td class="value" rowspan="2">
						 <input id="prejobConResult" name="prejobConResult" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthTrainEntity.prejobConResult}'>
					</td>
				</tr>
				<tr>
					<td rowspan='3' align="center">
						<label class="Validform_label">
							离岗后
						</label>
					</td>

					<td colspan="2" align="center">
						<label class="Validform_label">
							离岗类型
						</label>
					</td>
					<td class="value">
									<t:dictSelect field="leavedType" type="radio"
										typeGroupCode="leaved_type" defaultVal="${tBEmployeeInfoPage.tBHealthExamEntity.leavedType}" hasLabel="false"  title="离岗类型离岗后"></t:dictSelect>     
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查时间
						</label>
					</td>
					<td class="value">
									  <input id="leavedChkDate" name="leavedChkDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tBEmployeeInfoPage.tBHealthExamEntity.leavedChkDate}' type="date" pattern="yyyy-MM-dd"/>'>
						</td>
					 <td rowspan='6' align="center">
						<label class="Validform_label">
							在岗期间
						</label>
					</td>
					<td align="center" rowspan="2">
						<label class="Validform_label">
							培训时间
						</label>
					</td>
					<td class="value" rowspan="2">
									  <input id="duringTrainDate" name="duringTrainDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tBEmployeeInfoPage.tBHealthTrainEntity.duringTrainDate}' type="date" pattern="yyyy-MM-dd"/>'>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查结果
						</label>
					</td>
					<td class="value">
						     	 <input id="leavedChkResult" name="leavedChkResult" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.leavedChkResult}'>
						</td>
				</tr>
				<tr>
					<td rowspan='4' align="center">
						<label class="Validform_label">
							应急检查
						</label>
					</td>

					<td colspan="2" align="center">
						<label class="Validform_label">
							检查时间
						</label>
					</td>
					<td class="value">
									  <input id="emergChkDate" name="emergChkDate" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" value='<fmt:formatDate value='${tBEmployeeInfoPage.tBHealthExamEntity.emergChkDate}' type="date" pattern="yyyy-MM-dd"/>'>
						</td>
					<td align="center" rowspan="2">
						<label class="Validform_label">
							培训机构
						</label>
					</td>
					<td class="value" rowspan="2">
						     	 <input id="duringTrainOrg" name="duringTrainOrg" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthTrainEntity.duringTrainOrg}'>
						</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<label class="Validform_label">
							检查种类
						</label>
					</td>
					<td class="value">
						     	 <input id="emergChkCategory" name="emergChkCategory" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.emergChkCategory}'>
						</td>
				</tr>
				<tr>
				<td colspan="2" align="center">
					<label class="Validform_label">
						检查机构
					</label>
				</td>
				<td class="value">
					     	 <input id="emergChkOrg" name="emergChkOrg" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.emergChkOrg}'>
					</td>
				
				<td align="center" rowspan="2">
						<label class="Validform_label">
							综合成绩
						</label>
					</td>
					<td class="value" rowspan="2">
						     	 <input id="duringConResult" name="duringConResult" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthTrainEntity.duringConResult}'>
						</td>
				</tr>
				<tr>
				<td colspan="2" align="center">
					<label class="Validform_label">
						处置情况
					</label>
				</td>
				<td class="value">
						     	 <input id="emergDisposition" name="emergDisposition" type="text" style="width: 150px" class="inputxt"  value='${tBEmployeeInfoPage.tBHealthExamEntity.emergDisposition}'>
						</td>
				</tr>
			</table>
		</t:formvalid>	
 <script type="text/javascript">
 $(function(){
	 $(".formtable :input").attr("disabled","true");
	 var selecter = "";
	 selecter = $("#employeeidQuerySelect").magicSuggest({
		 data:'tBEmployeeInfoController.do?datagridMagic',
		 allowFreeEntries: false,
		 valueField:'id',
		 placeholder:'输入或选择',
		 maxSelection:1,
		 queryParam:'name'+$(this).val(),
		 selectFirst: true,
		 highlight: false,
		 displayField:'name'
	 });
	 if ("${employeeidQuery}"!= null&& "${employeeidQuery}"!=""&&"${employeeidQuery}"!=undefined){
		 $(selecter).on('load', function(e,m){
			 selecter.setValue(["${employeeidQuery}"]);
			 $("#fileNoQuery").val("${fileNoQuery}");
			 $("#postNumberQuery").val("${postNumberQuery}");
			 $("#retireNumberQuery").val("${retireNumberQuery}");
			 $("#cardNumberQuery").val("${cardNumberQuery}");
		 });
	 }
	 $(selecter).on('selectionchange', function(e,m){
		 $("#employeeidQuery").val(selecter.getValue());
		 $("#nameQuery").val(selecter.getValue());
		 var obj = selecter.getSelection();
		 if(obj.length > 0){
			 $("#fileNoQuery").val(obj[0].fileNo);
			 $("#postNumberQuery").val(obj[0].postNumber);
			 $("#retireNumberQuery").val(obj[0].retireNumber);
			 $("#cardNumberQuery").val(obj[0].cardNumber);
		 }
	 });
 });
//重置
	$("#resetBtn").click(function() {
		window.location = "tBEmployeeInfoController.do?employeeQuery";
	});
	//查询
	$(".operations #searchBtn").click(function() {
		/* if(getParam()==""){
			tip('查询条件不允许为空');
			return true;
		} */
		
		window.location = "tBEmployeeInfoController.do?employeeQuery"+getParam();
	});
	//导出
	$("#exportId").click(function() {
		window.location = "tBEmployeeInfoController.do?exportXlsEmployQueryByT"+getParam();
	});
	
/* 	function query() {
		$.post("tBEmployeeInfoController.do?employeeQuery", getParam(), function(data) {

		});
	} */
	/*function getParam() {
		var param = {};
		$(".operations :input[name]").each(function() {
			var name = $(this).attr("name");
			var value = $(this).val();
			//判断多选情况
		 	if($(this).hasClass('combo-value')) {
				value = new Array();
				$(":input[name='" + name + "']").each(function() {
					value.push($(this).val());
				});
			} 
			param[name] = value;
		});
		return param;
	}*/
	function getParam() {
		var param = "";
		$(".operations :input[name]").each(function() {
			var name = $(this).attr("name");
			var value = $(this).val();
			if(value==""||value.trim()==""){
				return true;
			}
			param+="&"+name+"="+value;
		});
		return param;
	}
 </script>