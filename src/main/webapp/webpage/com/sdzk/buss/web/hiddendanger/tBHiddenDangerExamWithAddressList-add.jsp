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
	  $(function() {

          //此部分代码用来添加关键词字段，获取关键词，将其拼成一句，以逗号分隔，然后设置到隐藏的id为keyWord的隐藏input中提交
          $("#btn").on("click", function(){
              var keyWords="";
              $("#myTags>a").each(function(){
                  keyWords = keyWords+$(this).attr("title")+",";
              });
              var str = keyWords;
              keyWords = str.substring(0,str.length-1);
              $("#keyWords").val(keyWords);
          });

		  getUserMagicSuggest($("#reviewManNameSelect"), $("input[name='reviewMan.id']"));
		  magicsuggestFillCardMan = $('#magicsuggestFillCardMan').magicSuggest({
			  allowFreeEntries: false,
			  data:'magicSelectController.do?getUserList',
			  valueField:'id',
			  placeholder:'输入或选择',
			  maxSelection:10,
			  selectFirst: true,
			  highlight: false,
              matchField:['spelling','realName','userName','fullSpelling'],
			  displayField:'realName'
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

		  magicsuggestSelected = $('#magicsuggest').magicSuggest({
			  data:'magicSelectController.do?getAddressList',
			  allowFreeEntries: false,
			  valueField:'id',
			  placeholder:'输入或选择',
			  maxSelection:1,
			  selectFirst: true,
			  highlight: false,
			  displayField:'address'
		  });
		  $(magicsuggestSelected).on('selectionchange', function(e,m){
			  $("#address").val(magicsuggestSelected.getValue());
		  });


          magicsuggestDutyManSelected = getUserMagicSuggestAllowFreeEntries($('#magicsuggestDutyMan'),  $("#dutyMan"), "", false);

          $(magicsuggestDutyManSelected).on('focus', function(c){
              var deptId = $('#dutyUnitId').val();
              magicsuggestDutyManSelected.setData("magicSelectController.do?getUserList&orgIds="+deptId);
          });

          $("#beginWellDate").attr("class", "Wdate").click(function () {
              WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
          });
          $("#endWellDate").attr("class", "Wdate").click(function () {
              WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'});
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
              displayField:'departName'
          });
          $(magicsuggestDutyUnitSelSelected).on('selectionchange', function(c){
              $("#dutyUnitId").val(magicsuggestDutyUnitSelSelected.getValue());
          });

          // 督办单位
          magicsuggestSuperviseUnitSelSelected = $('#magicsuggestSuperviseUnitSel').magicSuggest({
              allowFreeEntries: true,
              data:'magicSelectController.do?departSelectDataGridMagic',
              valueField:'id',
              placeholder:'输入或选择',
              maxSelection:1,
              selectFirst: true,
              highlight: false,
              matchField:['spelling','departName','fullSpelling'],
              displayField:'departName'
          });
          $(magicsuggestSuperviseUnitSelSelected).on('selectionchange', function(c){
              $("#superviseUnitId").val(magicsuggestSuperviseUnitSelSelected.getValue());
          });

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
          $("select[name='hiddenNature']>option[value='1']").remove();
	  });

	  function choose_dangerSource(title){
		  var addressId=$("#address").val();
		  if(addressId==null||addressId==''){
			  tip("请先选择地点");
			  return;
		  }
          var keyWords="";
          $("#myTags>a").each(function(){
              keyWords = keyWords+$(this).attr("title")+",";
          });
          var str = keyWords;
          keyWords = str.substring(0,str.length-1);
		  if (typeof(windowapi) == 'undefined') {
			  $.dialog({content: 'url:tBDangerSourceController.do?chooseDangerSource&keys='+encodeURI(encodeURI(keyWords))+"&addressId="+addressId, zIndex: 2100, title: ''+title+'', lock: true, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
				  {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_dangerSource, focus: true},
				  {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
				  }}
			  ]});
		  } else {
			  $.dialog({content: 'url:tBDangerSourceController.do?chooseDangerSource&keys='+encodeURI(encodeURI(keyWords))+"&addressId="+addressId, zIndex: 2100, title: ''+title+'', lock: true, parent: windowapi, width: 900, height: 450, left: '85%', top: '65%', opacity: 0.4, button: [
				  {name: '<t:mutiLang langKey="common.confirm"/>', callback: clickcallback_dangerSource, focus: true},
				  {name: '<t:mutiLang langKey="common.cancel"/>', callback: function () {
				  }}
			  ]});
		  }
	  }
	  function clickcallback_dangerSource(){
          iframe = this.iframe.contentWindow;
          var dangerName = iframe.gettBDangerSourceListSelections('hazard.hazardName');
          $("#dangerName").val(dangerName);
          $("#dangerName").blur();
		  var problemDesc = iframe.gettBDangerSourceListSelections('yeMhazardDesc');
		  if($("#problemDesc").text()==null||$("#problemDesc").text()==""){
			  $("#problemDesc").text(problemDesc);
		  }
		  var hiddenLevel = iframe.gettBDangerSourceListSelections('hiddenLevel');
		  if($("select[name='hiddenNature']").val()==null||$("select[name='hiddenNature']").val()==""){
			  $("select[name='hiddenNature']>option[value='"+hiddenLevel+"']").attr("selected","selected");
		  }
          var id = iframe.gettBDangerSourceListSelections('id');
          $("input[name='dangerId.id']").val(id);
          $("input[name='dangerId.id']").blur();

          var yePossiblyHazard = iframe.gettBDangerSourceListSelections('yePossiblyHazard');
          $("#yePossiblyHazard").text(yePossiblyHazard);
          var manageMeasure = iframe.gettBDangerSourceListSelections('manageMeasure');
          $("#manageMeasure").text(manageMeasure);

		  var yeRiskGradeTemp = iframe.gettBDangerSourceListSelections('yeRiskGradeTemp');
		  var alertColor = iframe.gettBDangerSourceListSelections('alertColor');
		  colorValueFormatter(yeRiskGradeTemp,alertColor);
	  }

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
		  function callbackFun(data){
              window.top["reload_tBHiddenDangerExamList_${examType}"].call();
              <%--window.top["tip_tBHiddenDangerExamList_${examType}"].call(data);--%>
              <%--closeWindow();--%>
              var examDate = $("#examDate").val();
              var shift = $("select[name='shift']>option:checked").val();
              var fillCardManId = $("#fillCardManId").val();
              var problemDesc = $("#problemDesc").val();
              <c:if test="${examType eq 'glgbxj'}">
              var isWithClass = $("input[name='isWithClass']").attr("checked");
              </c:if>
              tip(data.msg);
              $("#myTags a em").trigger("click");//清除关键字
              document.getElementById("formobj").reset();//清空输入内容
              $(".Validform_checktip").empty();//清空验证信息
              $("#examDate").val(examDate);//重置检查日期
              $("select[name='shift']>option[value='"+shift+"']").attr("selected","selected");//重置检查班次
              $("#fillCardManId").val(fillCardManId);//重置检查人id
              $("input[name='dangerId.id']").val("");
              $("#dangerName").val("");
              $("#problemDesc").text(problemDesc);
              $("select[name='hiddenNature']>option:first").attr("selected","selected");
              magicsuggestSelected.clear();//清空地点
              magicsuggestSuperviseUnitSelSelected.clear();//清空督办单位
//              magicsuggestDutyManSelected.clear();//清空责任人
//              magicsuggestDutyUnitSelSelected.clear();//清空责任单位
              <c:if test="${examType eq 'glgbxj'}">
              $("input[name='isWithClass']").attr("checked",isWithClass);
              </c:if>
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
  <t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" action="tBHiddenDangerExamController.do?doAdd" tiptype="3" btnsub="btn" callback="callbackFun">
					<input id="id" name="id" type="hidden" value="${tBHiddenDangerExamPage.id }">
					<input id="createName" name="createName" type="hidden" value="${tBHiddenDangerExamPage.createName }">
					<input id="createBy" name="createBy" type="hidden" value="${tBHiddenDangerExamPage.createBy }">
					<input id="createDate" name="createDate" type="hidden" value="${tBHiddenDangerExamPage.createDate }">
					<input id="updateName" name="updateName" type="hidden" value="${tBHiddenDangerExamPage.updateName }">
					<input id="updateBy" name="updateBy" type="hidden" value="${tBHiddenDangerExamPage.updateBy }">
					<input id="updateDate" name="updateDate" type="hidden" value="${tBHiddenDangerExamPage.updateDate }">
					<input id="examType" name="examType" type="hidden" value="${examType}">
					<input id="taskId" name="examType" type="hidden" value="${taskId}">
                    <input id="reportStatus" name="reportStatus" type="hidden" value="0"/>
                    <!-- 冗余   井口信息办 -->
                    <input id="from" name="from" type="hidden" value="${from}">
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
                                    value='<fmt:formatDate value='<%=new Date()%>' type="date" pattern="yyyy-MM-dd"/>'>
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
                                      typeGroupCode="workShift" defaultVal="${tBHiddenDangerExamPage.workShift}" hasLabel="false"  title="班次"></t:dictSelect>
							<span class="Validform_checktip">请选择班次</span>
							<label class="Validform_label" style="display: none;">班次</label>
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
						<input type="hidden" name="address.address" id="address" value="" class="inputxt" datatype="*">
							<span class="Validform_checktip">请选择地点</span>
							<label class="Validform_label" style="display: none;">地点</label>
						</td>
					<td align="right">
						<label class="Validform_label">
                            <font color="red">*</font>检查人:
						</label>
					</td>
					<td class="value">
						 <div id="magicsuggestFillCardMan" style="width: 300px;height: 15px"></div>
						 <input type="hidden" name="fillCardManId" id="fillCardManId" value="" style="width: 300px" class="inputxt" datatype="*" >
						 <input id="fillCardManName" name="fillCardManName" type="hidden" style="width: 150px" class="inputxt" >
                        <c:if test="${examType eq 'glgbxj'}">
                            <label>
                                <input type="checkbox" name="isWithClass" value="1">带班
                            </label>
                        </c:if>
							<span class="Validform_checktip">请选择检查人</span>
							<label class="Validform_label" style="display: none;">检查人</label>
						</td>
					</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
                        下井时间:
					</label>
				</td>
				<td class="value" colspan="3">
					<input type="text" name="beginWellDate" id="beginWellDate">--
					<input type="text" name="endWellDate" id="endWellDate">
					<span class="Validform_checktip">请选择下井时间</span>
					<label class="Validform_label" style="display: none;">下井时间</label>
				</td>
			</tr>




        <%--<tr>
            <td align="right">
                <label class="Validform_label">
                    关键字:
                </label>
            </td>
            <td class="value" colspan="3">

                <div class="rightAdd">
                    <div id="mycard-plus" style="margin-top: 0px;">
                        <div class="default-tag tagbtn">
                            <div class="clearfix">
                                    &lt;%&ndash;  从字典读取关键字  &ndash;%&gt;
                                <t:keySelect  typeGroupCode="hiddenKeyWords"/>
                            </div>
                        </div>
                    </div>

                    <div class="plus-tag-add">
                        <ul class="Form FancyForm" style="margin-top: 8px;">
                            <li>
                                <input name="" type="text" class="stext" id="addKeysinput" maxlength="20" />
                                <label>输入关键字</label>
                                <button id="addKeysId" type="button"  class="Button RedButton" style="font-size:14px;">添加关键字</button>
                            </li>
                        </ul>
                    </div>
                </div>
            </td>
        </tr>


            &lt;%&ndash;关键字&ndash;%&gt;
        <tr>

            <td align="right">
                <label class="Validform_label">
                    已选关键字:
                </label>
            </td>
            <td class="value" colspan="3">
                    &lt;%&ndash;------------------------------------------------------------------------&ndash;%&gt;
                <div class="demo" style="margin-top:1px;overflow:hidden;">
                    <input type="hidden" name="keyWords" id="keyWords" nullmsg="请输入关键字！"/>  <!-- 这个input是隐藏的input，用来将数据提交 -->
                    <div class="plus-tag tagbtn clearfix myTagsBorder" id="myTags"></div>
                </div>
                    &lt;%&ndash;------------------------------------------------------------------------&ndash;%&gt;
            </td>
        </tr>--%>
            <%--关联危险源--%>
        <tr>
			<c:if test="${hancheng ne 'true'}">
				<td align="right">
					<label class="Validform_label" id="dangerSourceLabel">
						<font color="red">*</font>危险源:
					</label>
				</td>
				<td class="value" colspan="3">
					<input id="dangerId.id" name="dangerId.id" type="hidden" style="width: 150px" class="inputxt" nullmsg="请选择危险源！" datatype="*">
					<textarea id="dangerName" name="dangerName" style="width: 440px;" onclick="choose_dangerSource('选择危险源');"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">危险源</label>
				</td>
			</c:if>
			<c:if test="${hancheng eq 'true'}">
				<td align="right">
					<label class="Validform_label" id="dangerSourceLabel">
						<font color="red">*</font>危险源:
					</label>
				</td>
				<td class="value">
					<input id="dangerId.id" name="dangerId.id" type="hidden" style="width: 150px" class="inputxt" nullmsg="请选择危险源！" datatype="*">
					<textarea id="dangerName" name="dangerName" style="width: 440px;" onclick="choose_dangerSource('选择危险源');"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">危险源</label>
				</td>
				<td align="right">
					<label class="Validform_label">
						管控措施:
					</label>
				</td>
				<td class="value" >
					<textarea id="manageMeasure" name="manageMeasure" style="width: 440px;" disabled="disabled"></textarea>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">管控措施</label>
				</td>
			</c:if>
        </tr>
	  <tr>
		  <td align="right">
			  <label class="Validform_label">
				  风险描述:
			  </label>
		  </td>
		  <td class="value" >
			  <textarea id="yePossiblyHazard" name="yePossiblyHazard" style="width: 440px;" disabled="disabled"></textarea>
			  <span class="Validform_checktip"></span>
			  <label class="Validform_label" style="display: none;">风险描述</label>
		  </td>

		  <td align="right">
			  <label class="Validform_label">
				  风险等级:
			  </label>
		  </td>
		  <td class="value" valign="middle" id="yeRiskGradeTemp">
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
						<span class="Validform_checktip">请选择或输入责任人</span>
						<label class="Validform_label" style="display: none;">责任人</label>
					</td>
					</tr>

	  <tr>
		  <td align="right">
			  <label class="Validform_label">
				  <font color="red">*</font>督办单位:
			  </label>
		  </td>
		  <td class="value" colspan="3">
			  <div id="magicsuggestSuperviseUnitSel" style="width: 130px;height: 15px"></div>
			  <input id="superviseUnitId" name="superviseUnitId" type="hidden" style="width: 150px" class="inputxt" datatype="*">
			  <span class="Validform_checktip">请选择督办单位</span>
			  <label class="Validform_label" style="display: none;">督办单位</label>
		  </td>
	  </tr>

			<tr>
				<td align="right">
					<label class="Validform_label">
                        <font color="red">*</font>隐患类别:
					</label>
				</td>
				<td class="value">
					<t:dictSelect field="hiddenCategory" type="list" extendJson="{\"datatype\":\"*\"}"
								  typeGroupCode="hiddenCate" defaultVal="11" hasLabel="false"  title="隐患类别"></t:dictSelect>
					<span class="Validform_checktip">请选择隐患类别</span>
					<label class="Validform_label" style="display: none;">隐患类别</label>
				</td>
				<td align="right">
					<label class="Validform_label">
                        <font color="red">*</font>隐患等级:
					</label>
				</td>
				<td class="value">
					<t:dictSelect field="hiddenNature" type="list" extendJson="{\"datatype\":\"*\"}"
								  typeGroupCode="hiddenLevel" defaultVal="${tBHiddenDangerExamPage.hiddenNature}" hasLabel="false"  title="隐患等级"></t:dictSelect>
					<span class="Validform_checktip">请选择隐患等级</span>
					<label class="Validform_label" style="display: none;">隐患等级</label>
				</td>
			</tr>
			<c:if test="${examType eq 'zyks'}">
				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>专业类型:
						</label>
					</td>
					<td class="value" colspan="3">
						<t:dictSelect field="proType" type="list" extendJson="{\"datatype\":\"*\"}"
									  typeGroupCode="proCate_gradeControl" defaultVal="" hasLabel="false"  title="专业类型"></t:dictSelect>
						<span class="Validform_checktip">请选择专业类型</span>
						<label class="Validform_label" style="display: none;">专业类型</label>
					</td>
				</tr>
			</c:if>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>隐患类型:
					</label>
				</td>
				<td class="value" colspan="3">
					<t:dictSelect id="hiddenType" field="hiddenType" defaultVal="" typeGroupCode="hiddenType" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">隐患类型</label>
				</td>
			</tr>
			<tr>
					<td align="right">
						<label class="Validform_label">
                            <font color="red">*</font>问题描述:
						</label>
					</td>

                    <td class="value" colspan="3">
                        <textarea id="problemDesc" name="problemDesc" type="text" style="width: 440px;"  datatype="*" ></textarea>
                        <input type="button" id="attach" value="自动关联风险" class="ui_state_highlight" style="width: 80px;vertical-align: 100%">
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
							  <input type="radio" name="dealType" id="dealType_xianqi" value="1" checked="checked"  onclick="showTr('dealTypetr1');">限期整改
						</label>
						<label>
							  <input type="radio" name="dealType" id="dealType_xianchang" value="2" onclick="showTr('xcclTR');">现场处理
						</label>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">隐患处理</label>
						</td>
                    <%--<td align="right">--%>
                        <%--<label class="Validform_label">--%>
                            <%--积分值:--%>
                        <%--</label>--%>
                    <%--</td>--%>
                    <%--<td class="value">--%>
                        <%--<input id="deductScores" name="deductScores" datatpe="d" type="text" style="width: 150px" class="inputxt" value="0" >--%>
                        <%--<span class="Validform_checktip"></span>--%>
                        <%--<label class="Validform_label" style="display: none;">积分值</label>--%>
                    <%--</td>--%>
					</tr>
			<tr id="dealTypetr1">
				<td align="right">
					<label class="Validform_label">
						<font color="red">*</font>限期日期:
					</label>
				</td>
				<td class="value" colspan="3">
					<input id="limitDate" name="limitDate" type="text" style="width: 150px"
						   class="Wdate" onClick="WdatePicker()" datatype="limDate" >
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">限期日期</label>
				</td>

			</tr>

			<tr class="xcclTR" style="display: none">
				<td align="right">
					<label class="Validform_label">
                        <font color="red">*</font>复查人:
					</label>
				</td>
				<td class="value" colspan="3">
					<div id="reviewManNameSelect" style="width: 130px;height: 15px"></div>
					<input id="reviewManId" name="reviewMan.id" type="hidden" style="width: 150px" class="inputxt">
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
                <textarea name="handleEntity.rectMeasures" id="handleEntity.rectMeasures" class="inputxt" style="width: 280px; height: 50px;" >${tBHiddenDangerExamPage.handleEntity.rectMeasures}</textarea>
                <span class="Validform_checktip"></span>
                <label class="Validform_label" style="display: none;">整改措施</label>
            </td>
            <td align="right">
                <label class="Validform_label">复查情况:
                </label>
            </td>
            <td class="value">
                <textarea name="handleEntity.reviewReport" id="handleEntity.reviewReport" class="inputxt" style="width: 280px; height: 50px;">${tBHiddenDangerExamPage.handleEntity.reviewReport}</textarea>
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
     //这部分的内容是从JQueryLable插件中的tab.js拿过来的，不然的话在页面刚加载时候，方法就被调用，等到加载完点击调用就无效了
     // 搜索
     (function(){
         var $b = $('#addKeysId');
         var $i = $('#addKeysinput');
         $i.keyup(function(e){
             if(e.keyCode == 13){
                 $b.click();
             }
         });
         $b.click(function(){
             var name = $i.val().toLowerCase();
             if(name != '') setTips(name,-1);
             setTips(name,-1)
             $i.val('');
             $i.select();
         });

         if("false"=="${bRequiredDangerSouce}"){
             $("#dangerSourceLabel").find("font").remove();
             $("#dangerId\\.id").removeAttr("datatype");
         }

         $("#attach").on("click", function(){
             var addressId=$("#address").val();
             if(addressId==null||addressId==''){
                 tip("请先选择地点");
                 return;
             }
             var problemDesc=$("#problemDesc").val();
             if(problemDesc==null||problemDesc==''){
                 tip("请先填写问题描述");
                 return;
             }
             $.ajax({
                 url: "tBHiddenDangerExamController.do?attachRisk",
                 type: 'post',
                 data: {
                     addressId: addressId,
                     problemDesc:problemDesc
                 },
                 success: function (data) {
                     var d = $.parseJSON(data);
                     if (d.success) {
                         var dangerName = d.obj.dangerName;
                         $("#dangerName").val(dangerName);
                         $("#dangerName").blur();

                         var problemDesc = d.obj.problemDesc;
                         if($("#problemDesc").text()==null||$("#problemDesc").text()==""){
                             $("#problemDesc").text(problemDesc);
                         }
                         var hiddenLevel = d.obj.hiddenLevel;
                         if($("select[name='hiddenNature']").val()==null||$("select[name='hiddenNature']").val()==""){
                             $("select[name='hiddenNature']>option[value='"+hiddenLevel+"']").attr("selected","selected");
                         }
                         var yePossiblyHazard = d.obj.yePossiblyHazard;
                         $("#yePossiblyHazard").text(yePossiblyHazard);
                         var manageMeasure = d.obj.manageMeasure;
                         $("#manageMeasure").text(manageMeasure);

						 var yeRiskGradeTemp = d.obj.yeRiskGradeTemp;
						 var alertColor = d.obj.alertColor;
						 colorValueFormatter(yeRiskGradeTemp,alertColor);

                         var id = d.obj.id;
                         $("input[name='dangerId.id']").val(id);
                         $("input[name='dangerId.id']").blur();
                     }else {
                         tip(d.msg);
                     }
                 }
             });
         });
     })();
	 function colorValueFormatter(value,alertColor) {
		 if(value != "") {

			 var html = '<div class="minicolors minicolors-theme-default minicolors-position-bottom minicolors-position-left"><input class="minicolors-input" readOnly="true" style="border:0px;width: 80px; padding-left: 26px;" type="text" value="' + value + '"><span class="minicolors-swatch" style="top:0px;"><span class="minicolors-swatch-color" style="background-color: ' + alertColor + ';"></span></span></div></div>';
			 $("#yeRiskGradeTemp").empty();
			 $("#yeRiskGradeTemp").append(html);
		 }
	 }

 </script>