<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>隐患检查</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
  <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
  <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
  <link href="plug-in/jQueryLabel/css/tab.css" type="text/css" rel="stylesheet" />
  <script type="text/javascript" src="plug-in/jQueryLabel/js/tab.js"></script>
  <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
	 <link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
  <script type="text/javascript">
      //编写自定义JS代码
	  var magicsuggestSelected = "";
	  var magicsuggestDutyManSelected = "";
      var magicsuggestDutyUnitSelSelected = "";
	  var magicsuggestFillCardMan = "";
      var magicsuggestPostSelSelected = "";
      var magicsuggestRiskSelSelected = "";
      var magicsuggestManageDutyUnitSelSelected = "";
      var magicsuggestManageDutyManSelected = "";

	  $(function() {

		  getUserMagicSuggestWithValue($("#reviewManNameSelect"), $("input[name='reviewMan.id']"),"${hde.reviewMan.id}",false);
		  magicsuggestSelected = $('#magicsuggest').magicSuggest({
			  data:'magicSelectController.do?getAddressList',
			  allowFreeEntries: false,
			  valueField:'id',
			  placeholder:'输入或选择',
			  maxSelection:1,
			  selectFirst: true,
			  highlight: false,
			  displayField:'address',
			  value: ['${hde.address.id}'],
			  disabled: true
		  });
		  $(magicsuggestSelected).on('selectionchange', function(e,m){
			  $("#address").val(magicsuggestSelected.getValue());
		  });
          magicsuggestPostSelSelected = $('#magicsuggestPost').magicSuggest({
              data: 'magicSelectController.do?getPostList',
              allowFreeEntries: false,
              valueField: 'id',
              placeholder: '输入或选择',
              maxSelection: 1,
              selectFirst: true,
              highlight: false,
              displayField: 'post_name',
              value: ['${hde.post.id}'],
              disabled: true
          });
          $(magicsuggestPostSelSelected).on('selectionchange', function (e, m) {
              $("#post").val(magicsuggestPostSelSelected.getValue());
          });

          magicsuggestRiskSelSelected = $('#magicsuggestRisk').magicSuggest({
              data: 'magicSelectController.do?getRiskList&riskId=${hde.riskId.id}',
              allowFreeEntries: false,
              valueField: 'id',
              value: ['${hde.riskId.id}'],
              placeholder: '输入或选择',
              maxSelection: 1,
              selectFirst: true,
              highlight: false,
              displayField: 'risk_desc',
          });
          $(magicsuggestRiskSelSelected).on('selectionchange', function(e,m){
              $("#riskId").val(magicsuggestRiskSelSelected.getValue());
              var obj = magicsuggestRiskSelSelected.getSelection();
              if(obj.length>0){
                  var riskLevel = obj[0].risk_level;
                  if(riskLevel=="1"){
                      $("#riskLevel1").show();
                      $("#riskLevel2").hide();
                  }else if(riskLevel=="2"){
                      $("#riskLevel2").show();
                      $("#riskLevel1").hide();
                  }else{
                      $("#riskLevel1").hide();
                      $("#riskLevel2").hide();
                  }
              }else{
                  $("#riskLevel1").hide();
                  $("#riskLevel2").hide();
              }
          });

          $(magicsuggestRiskSelSelected).on('focus', function(c){
              var address =$("#address").val();
              var riskType = $("select[name='riskType']>option:checked").val();
              var post =$("#post").val();
              if(post==undefined||post==null) {
                  post="";
              }
              if(address==null||riskType==null||address==""||riskType==""){
                  tip("请先选择地点和隐患类型");
              }else{
                  magicsuggestRiskSelSelected.setData("magicSelectController.do?getRiskList&riskId=${hde.riskId.id}&address="+address+"&post="+post+"&riskType="+riskType);
              }
          });

          magicsuggestDutyManSelected = getUserMagicSuggestAllowFreeEntries($('#magicsuggestDutyMan'),  $("#dutyMan"), "${hde.dutyMan}", false);

          $(magicsuggestDutyManSelected).on('focus', function(c){
              var deptId = $('#dutyUnitId').val();
              magicsuggestDutyManSelected.setData("magicSelectController.do?getUserList&orgIds="+deptId);
          });

          magicsuggestDutyUnitSelSelected = $('#magicsuggestDutyUnitSel').magicSuggest({
              allowFreeEntries: false,
              data:'magicSelectController.do?departSelectDataGridMagic',
              valueField:'id',
              placeholder:'输入或选择',
              maxSelection:1,
              selectFirst: true,
              highlight: false,
              matchField:['spelling','departName','fullSpelling'],
              displayField:'departName',
			  value:['${hde.dutyUnit.id}'],
			  disabled: '${load}'=='detail'
          });
          $(magicsuggestDutyUnitSelSelected).on('selectionchange', function(c){
              $("#dutyUnitId").val(magicsuggestDutyUnitSelSelected.getValue());
          });




          magicsuggestManageDutyManSelected = getUserMagicSuggestWithValue($('#magicsuggestManageDutyManId'),  $("#manageDutyManId"), "${hde.manageDutyManId}", false);

          $(magicsuggestManageDutyManSelected).on('focus', function(c){
              var deptId = $('#manageDutyUnitId').val();
              magicsuggestManageDutyManSelected.setData("magicSelectController.do?getUserList&orgIds="+deptId);
          });

          magicsuggestManageDutyUnitSelSelected = $('#magicsuggestManageDutyUnitSel').magicSuggest({
              allowFreeEntries: false,
              data:'magicSelectController.do?departSelectDataGridMagic',
              valueField:'id',
              value:["${hde.manageDutyUnit.id}"],
              placeholder:'输入或选择',
              maxSelection:1,
              selectFirst: true,
              highlight: false,
              matchField:['spelling','departName','fullSpelling'],
              displayField:'departName'
          });
          $(magicsuggestManageDutyUnitSelSelected).on('selectionchange', function(c){
              $("#manageDutyUnitId").val(magicsuggestManageDutyUnitSelSelected.getValue());
          });

          $(magicsuggestDutyManSelected).on('selectionchange', function(c){
              $("#dutyManId").val(magicsuggestDutyManSelected.getSelection()[0]["id"]);
          });



          magicsuggestFillCardMan = $('#magicsuggestFillCardMan').magicSuggest({
			  allowFreeEntries: false,
			  data:'magicSelectController.do?getUserList',
			  valueField:'id',
			  placeholder:'输入或选择',
			  maxSelection:10,
			  selectFirst: true,
			  highlight: false,
			  matchField:['spelling','realName','userName','fullSpelling'],
			  displayField:'realName',
			  value: "${hde.fillCardManId}".split(","),
			  disabled: '${load}'=='detail'
		  });
		  $(magicsuggestFillCardMan).on('selectionchange', function(e,m){
			  var obj = magicsuggestFillCardMan.getSelection();
			  if(obj.length>0){
				  $("#fillCardManName").val(obj[0].realName);
				  $("#fillCardManId").val(magicsuggestFillCardMan.getValue());
			  }else{
				  $("#fillCardManName").val("");
				  $("#fillCardManId").val("");
			  }
		  });

		  $("#limitDate").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		  showTr(${hde.dealType eq "1"? '"dealTypetr1" ':'"xcclTR"'});
		  $("#limitDate").Validform({
			  tiptype:3,
			  datatype : {
				  limDate:function(gets,obj,curform,regxp){
					  var dealType = $("input[name='dealType']:checked").val();
					  if(dealType == "1" && gets == ""){
						  return false;
					  }else{
						  return true;
					  }
				  },
				  message: '请输入限期日期'

			  }
		  });

		  $("select[name='limitShift']").Validform({
			  tiptype:3,
			  datatype : {
				  limShift:function(gets,obj,curform,regxp){
					  var dealType = $("input[name='dealType']:checked").val();
					  if(dealType == "1" && gets == ""){
						  return false;
					  }else{
						  return true;
					  }
				  },
				  message: '请选择限期班次'
			  }
		  });

		  $("#reviewManId").Validform({
			  tiptype:3,
			  datatype : {
				  reviewManValid:function(gets,obj,curform,regxp){
					  var dealType = $("input[name='dealType']:checked").val();
					  if(dealType == "2" && gets == ""){
						  return false;
					  }else{
						  return true;
					  }
				  },
				  message: '请选择复查人'
			  }
		  });

          //移除重大隐患
          $("select[name='hiddenNature']>option[value='1']").css("background-color","#e80000");
          var taiping = $("#taiping").val();
          var hegang = $("#hegang").val();
          if(taiping!="true"&&hegang!="true"){
              $("select[name='hiddenNature']>option[value='5']").remove();
          }

          $("select[name='hiddenNature']>option[value='6']").remove();
	  });

		  function showTr(trid){
              if ("xcclTR" == trid) {
                  $(".xcclTR").show();
                  $("#dealTypetr1").hide();
                  $("#limitDate").removeAttr("datatype");
                 //    // $("#reviewManId").attr("datatype", "*");
              } else if ("dealTypetr1" == trid) {
                  $(".xcclTR").hide();
                  $("#dealTypetr1").show();
                  $("#limitDate").attr("datatype", "*");
                  //  $("#reviewManId").removeAttr("datatype");
              }
		  }
		  /**
		   *关闭当前tab
		   */
		  function closeWindow(){
              window.top.$('.J_menuTab.active>i[class="fa fa-times-circle"]').trigger("click");
		  }
		  /**
		   *保存回调函数  清空数据
		   */
		  function callbackFun(data) {
		      var taskAll =  $("#taskAll").val();
              var riskManageHazardFactorId =  $("#riskManageHazardFactorId").val();
              var riskManagePostHazardFactorId =  $("#riskManagePostHazardFactorId").val();
		      if(taskAll=='true'){
                  window.top["reload_hdList_"].call();
                  window.top["tip_hdList_"].call(data);
			  }else {
		          if(riskManageHazardFactorId!=null&&riskManageHazardFactorId!=undefined&&riskManageHazardFactorId!='' ){
                      window.top["reload_hdList_${hde.riskManageHazardFactorId}"].call();
                      window.top["tip_hdList_${hde.riskManageHazardFactorId}"].call(data);
				  }
                  if(riskManagePostHazardFactorId!=null&&riskManagePostHazardFactorId!=undefined&&riskManagePostHazardFactorId!='' ){
                      window.top["reload_hdList_${hde.riskManagePostHazardFactorId}"].call();
                      window.top["tip_hdList_${hde.riskManagePostHazardFactorId}"].call(data);
                  }

			  }

			  closeWindow();
		  }
      function formSubmit(reportStatus){
          $("#reportStatus").val(reportStatus);
          var dealType = $("input[name='dealType']:checked").val();
          if(dealType == "2"){
              if(reportStatus == "1"){
                  $("#reviewManId").attr("datatype", "*");
              }
          }
          if(reportStatus == "0"){
              $("#reviewManId").removeAttr("datatype");
          }
          $("#btn").trigger("click");
      }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" action="tBHiddenDangerExamController.do?doUpdate" tiptype="3" btnsub="btn" callback="callbackFun">
	  <input id="id" name="id" type="hidden" value="${hde.id}"/>
	  <input id="reportStatus" name="reportStatus" type="hidden" value="0"/>
	  <input id="examType" name="examType" type="hidden" value="${hde.manageType}">
	  <input id="manageType" name="manageType" type="hidden" value="${hde.manageType}">
	  <input id="riskType" name="riskType" type="hidden" value="${hde.riskType}">
	  <input id="taskAll" name="taskAll" type="hidden" value="${taskAll}">
	  <input id="riskManageHazardFactorId" name="riskManageHazardFactorId" type="hidden" value="${hde.riskManageHazardFactorId}">
	  <input id="riskManagePostHazardFactorId" name="riskManagePostHazardFactorId" type="hidden" value="${hde.riskManagePostHazardFactorId}">
	  <input id="taiping" name="taiping" type="hidden" value="${taiping}">
	  <input id="hegang" name="hegang" type="hidden" value="${hegang}">
  <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="left"  colspan="4" class="value">
                <label class="Validform_label">
                    加<font color="red">*</font>内容必填！
                </label>
            </td>
        </tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>日期:
				</label>
			</td>
			<td class="value">
					 <input id="examDate" name="examDate" type="text" style="width: 150px" class="Wdate" datatype="*" onClick="WdatePicker({minDate:'2017-5-1',maxDate:'%y-%M-%d'})"
							value='<fmt:formatDate value='${hde.examDate}' type="date" pattern="yyyy-MM-dd"/>'>
					<span class="Validform_checktip">请选择日期</span>
					<label class="Validform_label" style="display: none;">日期</label>
				</td>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>班次:
				</label>
			</td>
			<td class="value">
				<t:dictSelect field="shift" type="list" extendJson="{\"datatype\":\"*\"}"
							  typeGroupCode="workShift" defaultVal="${hde.shift}" hasLabel="false"  title="班次"></t:dictSelect>
					<span class="Validform_checktip">请选择班次</span>
					<label class="Validform_label" style="display: none;">班次</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>检查人:
				</label>
			</td>
			<td class="value">
				 <div id="magicsuggestFillCardMan" style="width: 300px;height: 15px"></div>
				 <input type="hidden" name="fillCardManId" id="fillCardManId" value="" style="width: 300px" class="inputxt" datatype="*" >
				 <input id="fillCardManName" name="fillCardManName" type="hidden" style="width: 150px" class="inputxt" >
				<span class="Validform_checktip">请选择检查人</span>
				<label class="Validform_label" style="display: none;">检查人</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>信息来源:
				</label>
			</td>
			<td class="value">
				<t:dictSelect field="manageType" type="list" extendJson="{\"datatype\":\"*\"}"
							  typeGroupCode="manageType" defaultVal="${hde.manageType}" hasLabel="false"  title="信息来源" readonly="readonly"></t:dictSelect>
				<span class="Validform_checktip">请选择信息来源</span>
				<label class="Validform_label" style="display: none;">信息来源</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>地点:
				</label>
			</td>
			<td class="value">
				<div id="magicsuggest" style="width: 130px;height: 15px"></div>
				<input type="hidden" name="address.address" id="address" value="${hde.address.id}" class="inputxt" datatype="*">
				<span class="Validform_checktip">请选择地点</span>
				<label class="Validform_label" style="display: none;">地点</label>
			</td>
		  <td align="right">
			  <label class="Validform_label">
				  <font color="red">*</font>隐患类型:
			  </label>
		  </td>
		  <td class="value">
			  <t:dictSelect field="riskType" type="list" extendJson="{\"datatype\":\"*\"}"
							typeGroupCode="risk_type" defaultVal="${hde.riskType}" hasLabel="false"  title="风险类型" readonly="readonly"></t:dictSelect>
			  <span class="Validform_checktip">请选择隐患类型</span>
			  <label class="Validform_label" style="display: none;">隐患类型</label>
		  </td>

		</tr>
	  <tr>
		  <td align="right">
			  <label class="Validform_label">
				  风险描述:
			  </label>
		  </td>
		  <td class="value" colspan="3">
			  <div id="magicsuggestRisk" style="width: 400px;height: auto"></div>
			  <input type="hidden" name="riskId.id" id="riskId" value="${hde.riskId.id}"  style="width: 150px" class="inputxt"  >
			  <font color="red" id="riskLevel1" style="display: none">&nbsp;&nbsp;&nbsp;此风险为重大风险！</font>
			  <font color="red" id="riskLevel2" style="display: none">&nbsp;&nbsp;&nbsp;此风险为较大风险！</font>
			  <span class="Validform_checktip"></span>
			  <label class="Validform_label" style="display: none;">风险描述</label>
		  </td>
	  </tr>
        <tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>责任单位:
				</label>
			</td>
			<td class="value">
				<div id="magicsuggestDutyUnitSel" style="width: 130px;height: 15px"></div>
					 <input id="dutyUnitId" name="dutyUnitId" type="hidden" style="width: 150px" class="inputxt" datatype="*">
					<span class="Validform_checktip">请选择责任单位</span>
					<label class="Validform_label" style="display: none;">责任单位</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>责任人:
				</label>
			</td>
			<td class="value">
				<div id="magicsuggestDutyMan" style="width: 130px;height: 15px"></div>
				<input type="hidden" name="dutyMan" id="dutyMan" value="" class="inputxt" datatype="*">
				<input id="dutyManId" name="dutyManId" type="hidden" style="width: 150px" class="inputxt"  value="${hde.dutyManId}">
				<span class="Validform_checktip">请选择或输入责任人</span>
				<label class="Validform_label" style="display: none;">责任人</label>
			</td>
		</tr>


	  <tr>
		  <td align="right">
			  <label class="Validform_label">
				  管控责任单位:
			  </label>
		  </td>
		  <td class="value">
			  <div id="magicsuggestManageDutyUnitSel" style="width: 130px;height: 15px"></div>
			  <input id="manageDutyUnitId" name="manageDutyUnitId" type="hidden" style="width: 150px" class="inputxt" value="${hde.manageDutyUnit.id}">
			  <span class="Validform_checktip"></span>
			  <label class="Validform_label" style="display: none;">管控责任单位</label>
		  </td>
		  <td align="right">
			  <label class="Validform_label">
				  管控责任人:
			  </label>
		  </td>
		  <td class="value">
			  <div id="magicsuggestManageDutyManId" style="width: 130px;height: 15px"></div>
			  <input type="hidden" name="manageDutyManId" id="manageDutyManId" value="" style="width: 150px" class="inputxt"  value="${hde.manageDutyManId}">
			  <span class="Validform_checktip"></span>
			  <label class="Validform_label" style="display: none;">管控责任人</label>
		  </td>
	  </tr>



	  <tr>
			<%--<td align="right">--%>
				<%--<label class="Validform_label">--%>
					<%--<b style="color: red">*</b>隐患类型:--%>
				<%--</label>--%>
			<%--</td>--%>
			<%--<td class="value">--%>
				<%--<t:dictSelect id="hiddenType" field="hiddenType" defaultVal="${hde.hiddenType}" typeGroupCode="hiddenType" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>--%>
				<%--<span class="Validform_checktip"></span>--%>
				<%--<label class="Validform_label" style="display: none;">隐患类型</label>--%>
			<%--</td>--%>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>隐患等级:
				</label>
			</td>
			<td class="value" colspan="3">
				<t:dictSelect field="hiddenNature" type="list" extendJson="{\"datatype\":\"*\"}"
							  typeGroupCode="hiddenLevel" defaultVal="${hde.hiddenNature}" hasLabel="false"  title="隐患等级"></t:dictSelect>
				<span class="Validform_checktip">请选择隐患等级</span>
				<label class="Validform_label" style="display: none;">隐患等级</label>
			</td>
		</tr>
	  <c:if test="${newPost ne 'true' and manageType eq 'post'}">
		  <tr>
			  <td align="right">
				  <label class="Validform_label">
					  岗位:
				  </label>
			  </td>
			  <td class="value" colspan="3">
				  <div id="magicsuggestPost" style="width: 130px;height: 15px"></div>
				  <input type="hidden" name="post.postname" id="post" value="" class="inputxt" value="${hde.post.id}">
				  <span class="Validform_checktip">请选择岗位</span>
				  <label class="Validform_label" style="display: none;">岗位</label>
			  </td>
		  </tr>
	  </c:if>

	<tr>
		<td align="right">
			<label class="Validform_label">
				<font color="red">*</font>问题描述:
			</label>
		</td>

		<td class="value" colspan="3">
			<textarea id="problemDesc" name="problemDesc" type="text" style="width: 440px;"  datatype="*" >${hde.problemDesc}</textarea>
			<span class="Validform_checktip">请填写问题描述</span>
			<label class="Validform_label" style="display: none;">问题描述</label>
		</td>
	</tr>
	<tr>
		<td align="right">
			<label class="Validform_label">
				隐患处理:
			</label>
		</td>
		<td class="value" colspan="3">
			<label>
				  <input type="radio" name="dealType" id="dealType_xianqi" value="1" ${hde.dealType eq '1' ?"checked='checked'":""}  onclick="showTr('dealTypetr1');">限期整改
			</label>
			<label>
				  <input type="radio" name="dealType" id="dealType_xianchang" value="2" ${hde.dealType eq '2' ?"checked='checked'":""}  onclick="showTr('xcclTR');">现场处理
			</label>
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">隐患处理</label>
		</td>
	</tr>
	<tr id="dealTypetr1">
		<td align="right">
			<label class="Validform_label">
				<font color="red">*</font>限期日期:
			</label>
		</td>
		<td class="value" <c:if test="${beixulou ne 'true'}"> colspan="3" </c:if>>
			<input id="limitDate" name="limitDate" type="text" style="width: 150px"
				   class="Wdate" onClick="WdatePicker()" datatype="limDate" value='<fmt:formatDate value='${hde.limitDate}' type="date" pattern="yyyy-MM-dd"/>' >
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">限期日期</label>
		</td>
		<c:if test="${beixulou eq 'true'}">
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>限期班次:
				</label>
			</td>
			<td class="value">
				<t:dictSelect field="limitShift" type="list" extendJson="{\"datatype\":\"*\"}"
							  typeGroupCode="workShift" defaultVal="${hde.limitShift}" hasLabel="false"  title="限期班次"></t:dictSelect>
				<span class="Validform_checktip">请选择限期班次</span>
				<label class="Validform_label" style="display: none;">限期班次</label>
			</td>
		</c:if>
	</tr>

	<tr class="xcclTR" style="display: none">
		<td align="right">
			<label class="Validform_label">
				<font color="red">*</font>复查人:
			</label>
		</td>
		<td class="value" colspan="3">
			<div id="reviewManNameSelect" style="width: 130px;height: 15px"></div>
			<input id="reviewManId" name="reviewMan.id" type="hidden" style="width: 150px" class="inputxt" value="${hde.reviewMan.id}" >
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">复查人</label>
		</td>
	</tr>
	<tr class="xcclTR" style="display: none">
		<td align="right">
			<label class="Validform_label">整改措施:
			</label>
		</td>
		<td class="value">
			<textarea name="handleEntity.rectMeasures" id="handleEntity.rectMeasures" class="inputxt" style="width: 280px; height: 50px;" >${hde.handleEntity.rectMeasures}</textarea>
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">整改措施</label>
		</td>
		<td align="right">
			<label class="Validform_label">复查情况:
			</label>
		</td>
		<td class="value">
			<textarea name="handleEntity.reviewReport" id="handleEntity.reviewReport" class="inputxt" style="width: 280px; height: 50px;">${hde.handleEntity.reviewReport}</textarea>
			<span class="Validform_checktip"></span>
			<label class="Validform_label" style="display: none;">复查情况</label>
		</td>
	</tr>
	<tr>
		<td colspan="4">
			<div class="ui_buttons" style="text-align: center;">
				<input type="button" id="btn" value="保存" class="ui_state_highlight">
				<input type="button" id="btn_subRep" value="保存并上报" class="ui_state_highlight" onclick="formSubmit('1')">
				<input type="button" id="btn_close" onclick="closeWindow();" value="关闭">
			</div>
		</td>
	</tr>
	</table>
</t:formvalid>
 </body>
 <script src = "webpage/com/sdzk/buss/web/hiddendanger/js/tBHiddenDangerExam.js"></script>
 <script>
     (function(){
		<%--if("${load}"=='detail'){--%>
			<%--$(":input").attr("disabled","true");--%>
		<%--}--%>

     })();
 </script>