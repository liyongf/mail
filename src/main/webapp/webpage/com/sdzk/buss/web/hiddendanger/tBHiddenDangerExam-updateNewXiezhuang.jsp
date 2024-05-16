<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>隐患检查</title>
	<t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
    <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
    <link href="plug-in/jQueryLabel/css/tab.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
    <script type="text/javascript" src="plug-in/jQueryLabel/js/tab.js"></script>
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
	<link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
	<script type="text/javascript">
		var isFirstLoad = true;
		//编写自定义JS代码
        var magicsuggestSelected = "";
		var magicsuggestDutyManSelected = "";
        var magicsuggestDutyUnitSelSelected = "";
		var magicsuggestFillCardMan = "";
        var magicsuggestHazardFactorSelSelected = "";
        var magicsuggestRiskSelSelected = "";
        var magicsuggestReviewManNameSelect = "";
        var magicsuggestPostSelSelected = "";
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
                console.log(keyWords);
            });

            fillKeyWords();
            magicsuggestReviewManNameSelect = getUserMagicSuggestWithValue($("#reviewManNameSelect"), $("input[name='reviewMan.id']"),"${tBHiddenDangerExamPage.reviewMan.id}",false);
            magicsuggestReviewManNameSelect.disable();
			magicsuggestSelected = $('#magicsuggest').magicSuggest({
                data:'magicSelectController.do?getAddressList',
                allowFreeEntries: false,
                valueField:'id',
                placeholder:'输入或选择',
                value:["${tBHiddenDangerExamPage.address.id}"],
                maxSelection:1,
                selectFirst: true,
                highlight: false,
                displayField:'address',
                disabled: true
            });
            $(magicsuggestSelected).on('selectionchange', function(e,m){
                $("#address").val(magicsuggestSelected.getValue());

            });
            magicsuggestPostSelSelected = $('#magicsuggestPost').magicSuggest({
                data: 'magicSelectController.do?getPostList',
                allowFreeEntries: false,
                value:["${tBHiddenDangerExamPage.post.id}"],
                valueField: 'id',
                placeholder: '输入或选择',
                maxSelection: 1,
                selectFirst: true,
                highlight: false,
                displayField: 'post_name',
                disabled: true
            });
            $(magicsuggestPostSelSelected).on('selectionchange', function (e, m) {
                $("#post").val(magicsuggestPostSelSelected.getValue());
            });

            magicsuggestRiskSelSelected = $('#magicsuggestRisk').magicSuggest({
                data: 'magicSelectController.do?getRiskList&riskId=${tBHiddenDangerExamPage.riskId.id}',
                allowFreeEntries: false,
                value:["${tBHiddenDangerExamPage.riskId.id}"],
                valueField: 'id',
                placeholder: '输入或选择',
                maxSelection: 1,
                selectFirst: true,
                highlight: false,
                displayField: 'risk_desc'
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
                    magicsuggestRiskSelSelected.setData("magicSelectController.do?getRiskList&address="+address+"&post="+post+"&riskType="+riskType);
                }
            });


            magicsuggestHazardFactorSelSelected = $('#magicsuggestHazardFactor').magicSuggest({
                data: 'magicSelectController.do?getHazardFactorList&hazardFactorId=${tBHiddenDangerExamPage.hazardFactorId}',
                allowFreeEntries: false,
                value:["${tBHiddenDangerExamPage.hazardFactorId}"],
                valueField: 'id',
                placeholder: '输入或选择',
                maxSelection: 1,
                selectFirst: true,
                highlight: false,
                displayField: 'hazard_factors'
            });
            $(magicsuggestHazardFactorSelSelected).on('selectionchange', function(e,m){
                $("#hazardFactorId").val(magicsuggestHazardFactorSelSelected.getValue());
                $("#hazardFactorName").val(magicsuggestHazardFactorSelSelected.getSelection()[0].hazard_factors);
            });

            $(magicsuggestHazardFactorSelSelected).on('focus', function(c){
                var riskId =$("#riskId").val();
                if(riskId==null||riskId==""){
                    tip("请先选择风险描述");
                }else{
                    magicsuggestHazardFactorSelSelected.setData("magicSelectController.do?getHazardFactorList&riskId="+riskId);
                }
            });


            magicsuggestDutyManSelected = getUserMagicSuggestAllowFreeEntries($('#magicsuggestDutyMan'),  $("#dutyMan"), "${tBHiddenDangerExamPage.dutyMan}", false);
            magicsuggestDutyManSelected.disable();
            $(magicsuggestDutyManSelected).on('focus', function(c){
                var deptId = $('#dutyUnitId').val();
                magicsuggestDutyManSelected.setData("magicSelectController.do?getUserList&orgIds="+deptId);
            });

            magicsuggestDutyUnitSelSelected = $('#magicsuggestDutyUnitSel').magicSuggest({
                allowFreeEntries: false,
                data:'magicSelectController.do?departSelectDataGridMagic',
                value:["${tBHiddenDangerExamPage.dutyUnit.id}"],
                valueField:'id',
                placeholder:'输入或选择',
                maxSelection:1,
                selectFirst: true,
                highlight: false,
                matchField:['spelling','departName','fullSpelling'],
                displayField:'departName',
                disabled: true
            });

            $(magicsuggestDutyUnitSelSelected).on('selectionchange', function(c){
				if(!isFirstLoad){
					$("#dutyUnitId").val(magicsuggestDutyUnitSelSelected.getValue());
				}
				isFirstLoad = false;
            });
            var jinyuan =$("#jinyuan").val();
            var luwa =$("#luwa").val();
            if(jinyuan == "true"||luwa=="true"){
                magicsuggestFillCardMan = $('#magicsuggestFillCardMan').magicSuggest({
                    allowFreeEntries: false,
                    data:'magicSelectController.do?getUserList',
                    valueField:'id',
                    value:[${fillcardmanids}],
                    placeholder:'输入或选择',
                    maxSelection:10,
                    selectFirst: true,
                    highlight: false,
                    matchField:['spelling','realName','userName','fullSpelling'],
                    displayField:'realName',
                    disabled: true
                });
            }else{
                magicsuggestFillCardMan = $('#magicsuggestFillCardMan').magicSuggest({
                    allowFreeEntries: true,
                    data:'magicSelectController.do?getUserList',
                    valueField:'id',
                    value:[${fillcardmanids}],
                    placeholder:'输入或选择',
                    maxSelection:10,
                    selectFirst: true,
                    highlight: false,
                    matchField:['spelling','realName','userName','fullSpelling'],
                    displayField:'realName',
                    disabled: true
                });
            }
			$(magicsuggestFillCardMan).on('selectionchange', function(e,m){
				if(!isFirstLoad){
					var obj = magicsuggestFillCardMan.getSelection();
					if(obj.length>0){
						$("#fillCardManName").val(obj[0].realName);
						$("#fillCardManId").val(magicsuggestFillCardMan.getValue());
					}else{
						$("#fillCardManName").val("");
						$("#fillCardManId").val("");
					}
				}
			});

			// 督办单位
            magicsuggestSuperviseUnitSelSelected = $('#magicsuggestSuperviseUnitSel').magicSuggest({
                allowFreeEntries: true,
                data:'magicSelectController.do?departSelectDataGridMagic',
                value:["${tBHiddenDangerExamPage.superviseUnitId}"],
                valueField:'id',
                placeholder:'输入或选择',
                maxSelection:1,
                selectFirst: true,
                highlight: false,
                matchField:['spelling','departName','fullSpelling'],
                displayField:'departName',
                disabled: true
            });
            $(magicsuggestSuperviseUnitSelSelected).on('selectionchange', function(c){
                $("#superviseUnitId").val(magicsuggestSuperviseUnitSelSelected.getValue());
            });



			showTr(${tBHiddenDangerExamPage.dealType eq "1"? '"dealTypetr1" ':'"xcclTR"'});

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

			$("#limitShift").Validform({
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

			var yeRiskGradeTemp = iframe.gettBDangerSourceListSelections('yeRiskGradeTemp');
			var alertColor = iframe.gettBDangerSourceListSelections('alertColor');
			colorValueFormatter(yeRiskGradeTemp,alertColor);
		}

		function showTr(trid){
            if ("xcclTR" == trid) {
                $(".xcclTR").show();
                $("#dealTypetr1").hide();
                $("#limitDate").removeAttr("datatype");
                $('select[name="limitShift"]').removeAttr("dataType");
               //    // $("#reviewManId").attr("datatype", "*");
            } else if ("dealTypetr1" == trid) {
                $(".xcclTR").hide();
                $("#dealTypetr1").show();
                $("#limitDate").attr("datatype", "*");
                $('select[name="limitShift"]').attr("dataType","*");
                //  $("#reviewManId").removeAttr("datatype");
            }
		}

        function fillKeyWords(){
            var str = '${tBHiddenDangerExamPage.keyWords}';
            var strKeyWords=new Array(); //定义一数组

            strKeyWords = str.split(','); //字符分割
            for(var i=0; i < strKeyWords.length; i++){
                if(strKeyWords[i] != ""){
                    setTips(strKeyWords[i], -1);
                }
            }
        }

        /**
         *关闭当前tab
         */
        function closeWindow() {
            window.top.$('.J_menuTab.active>i[class="fa fa-times-circle"]').trigger("click");
        }
        /**
         *保存回调函数  清空数据
         */
        function callbackFun(data) {
            window.top["reload_tBHiddenDangerExamList_${examType}"].call();
            window.top["tip_tBHiddenDangerExamList_${examType}"].call(data);
            closeWindow();
        }
        function formSubmit(reportStatus){

            $("#btn").trigger("click");
        }

	</script>


</head>
<body>
<t:formvalid formid="formobj" dialog="false" usePlugin="password" layout="table" action="tBHiddenDangerExamController.do?doUpdateXiezhuang" tiptype="3" btnsub="btn" callback="callbackFun">
	<input id="id" name="id" type="hidden" value="${tBHiddenDangerExamPage.id }">
	<input id="createName" name="createName" type="hidden" value="${tBHiddenDangerExamPage.createName }">
	<input id="createBy" name="createBy" type="hidden" value="${tBHiddenDangerExamPage.createBy }">
	<input id="createDate" name="createDate" type="hidden" value="${tBHiddenDangerExamPage.createDate }">
	<input id="updateName" name="updateName" type="hidden" value="${tBHiddenDangerExamPage.updateName }">
	<input id="updateBy" name="updateBy" type="hidden" value="${tBHiddenDangerExamPage.updateBy }">
	<input id="updateDate" name="updateDate" type="hidden" value="${tBHiddenDangerExamPage.updateDate }">
	<input id="flag" name="flag" type="hidden" value="${flag }">
	<input id="examType" name="examType" type="hidden" value="${examType}">
	<input id="jinyuan" name="jinyuan" type="hidden" value="${jinyuan}"/>
    <input id="luwa" name="luwa" type="hidden" value="${luwa}"/>
	<input type="hidden" id="selectedFineEmp" name="selectedFineEmp"/>
    <input id="reportStatus" name="reportStatus" type="hidden" value="0"/>
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
				<input id="examDate" name="examDate" type="text" style="width: 150px" datatype="*" readonly="true" class="Wdate"  value='<fmt:formatDate value='${tBHiddenDangerExamPage.examDate}' type="date" pattern="yyyy-MM-dd"/>'>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">日期</label>
			</td>
			<td align="right">
				<label class="Validform_label">
                    <font color="red">*</font>班次:
				</label>
			</td>
			<td class="value">
                    <t:dictSelect field="shift" type="list" extendJson="{\"datatype\":\"*\"}"
                                     typeGroupCode="workShift" defaultVal="${tBHiddenDangerExamPage.shift}"  hasLabel="false" readonly="readonly" title="班次"></t:dictSelect>
				<span class="Validform_checktip"></span>
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
				<input type="hidden" name="fillCardManId" id="fillCardManId" style="width: 150px" class="inputxt" datatype="*" value="${tBHiddenDangerExamPage.fillCardManId}" >
				<input id="fillCardManName" name="fillCardManName" type="hidden" style="width: 150px" class="inputxt" value="${tBHiddenDangerExamPage.fillCardMan.realName}" >
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">检查人</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>信息来源:
				</label>
			</td>
			<td class="value">
				<t:dictSelect field="manageType" type="list" extendJson="{\"datatype\":\"*\"}" readonly="readonly"
							  typeGroupCode="manageType"  hasLabel="false"  title="管控类型" defaultVal="${tBHiddenDangerExamPage.manageType}"></t:dictSelect>
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
				<input type="hidden" name="address.address" id="address" datatype="*" value="${tBHiddenDangerExamPage.address.id}">
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">地点</label>
			</td>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>隐患类型:
				</label>
			</td>
			<td class="value">
				<t:dictSelect field="riskType" type="list" extendJson="{\"datatype\":\"*\"}" readonly="readonly"
							  typeGroupCode="risk_type"  hasLabel="false"  title="隐患类型" defaultVal="${tBHiddenDangerExamPage.riskType}"></t:dictSelect>
				<span class="Validform_checktip">请选择隐患类型</span>
				<label class="Validform_label" style="display: none;">隐患类型</label>
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
					<div id="magicsuggestPost" style="width: 130px;height: 15px"></div>
					<input type="hidden" name="post.postname" id="post"  class="inputxt" value="${tBHiddenDangerExamPage.post.id}">
					<span class="Validform_checktip">请选择岗位</span>
					<label class="Validform_label" style="display: none;">岗位</label>
				</td>
			</tr>
		</c:if>
		<tr>
			<td align="right">
				<label class="Validform_label">
					<c:if test="${xiaogang eq 'true'}"><font color="red">*</font></c:if> 风险描述:
				</label>
			</td>
			<td class="value" <c:if test="${xiezhuang ne 'true'}">colspan="3"</c:if>>
				<div id="magicsuggestRisk" style="width: 400px;height: auto"></div>
				<input type="hidden" name="riskId.id" id="riskId" value="${tBHiddenDangerExamPage.riskId.id}" style="width: 150px" class="inputxt" <c:if test="${xiaogang eq 'true'}">datatype="*"</c:if> >
				<font color="red" id="riskLevel1" style="display: none">&nbsp;&nbsp;&nbsp;此风险为重大风险！</font>
				<font color="red" id="riskLevel2" style="display: none">&nbsp;&nbsp;&nbsp;此风险为较大风险！</font>
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">风险描述</label>
			</td>
			<c:if test="${xiezhuang eq 'true'}">
				<td align="right">
					<label class="Validform_label">
						危害因素:
					</label>
				</td>
				<td class="value">
					<div id="magicsuggestHazardFactor" style="width: 400px;height: auto"></div>
					<input type="hidden" name="hazardFactorId" id="hazardFactorId" value="${tBHiddenDangerExamPage.hazardFactorId}" style="width: 150px" class="inputxt"  >
					<input type="hidden" name="hazardFactorName" id="hazardFactorName" value="${tBHiddenDangerExamPage.hazardFactorName}" style="width: 150px" class="inputxt"  >
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">危害因素</label>
				</td>
		</c:if>
			<%--<td align="right">
				<label class="Validform_label">
					危害因素:
				</label>
			</td>
			<td class="value">
				<div id="magicsuggestHazardFactor" style="width: 300px;height: 15px"></div>
				<input type="hidden" name="hazardFactorId" id="hazardFactorId" value="${tBHiddenDangerExamPage.hazardFactorId}" style="width: 150px" class="inputxt"  >
				<input type="hidden" name="hazardFactorName" id="hazardFactorName" value="${tBHiddenDangerExamPage.hazardFactorName}" style="width: 150px" class="inputxt"  >
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">危害因素</label>
			</td>--%>
		</tr>
        <tr>
			<td align="right">
				<label class="Validform_label">
                    <font color="red">*</font>责任单位:
				</label>
			</td>
			<td class="value">
                <div id="magicsuggestDutyUnitSel" style="width: 130px;height: 15px"></div>
				<input id="dutyUnitId" name="dutyUnitId" type="hidden" style="width: 150px" class="inputxt" datatype="*" value="${tBHiddenDangerExamPage.dutyUnit.id}">
				<span class="Validform_checktip"></span>
				<label class="Validform_label" style="display: none;">责任单位</label>
			</td>
			<td align="right">
				<label class="Validform_label">
                    <font color="red">*</font>责任人:
				</label>
			</td>
			<td class="value">
				<div id="magicsuggestDutyMan" style="width: 130px;height: 15px"></div>
				<input id="dutyMan" name="dutyMan" type="hidden" style="width: 150px" class="inputxt" datatype="*"  value="${tBHiddenDangerExamPage.dutyMan}">
				<span class="Validform_checktip">请选择或输入责任人</span>
				<label class="Validform_label" style="display: none;">责任人</label>
			</td>
		</tr>


		<tr>
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>隐患等级:
				</label>
			</td>
			<td class="value" colspan="3">
				<t:dictSelect field="hiddenNature" type="list" extendJson="{\"datatype\":\"*\"}" readonly="readonly"
							  typeGroupCode="hiddenLevel" defaultVal="${tBHiddenDangerExamPage.hiddenNature}" hasLabel="false"  title="隐患等级"></t:dictSelect>
				<span class="Validform_checktip">请选择隐患等级</span>
				<label class="Validform_label" style="display: none;">隐患等级</label>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">
                    <font color="red">*</font>问题描述:
				</label>
			</td>
			<td class="value" colspan="3">
				<textarea id="problemDesc" name="problemDesc" type="text" style="width: 440px;" datatype="*" readonly="true"  >${tBHiddenDangerExamPage.problemDesc}</textarea>
                <span class="Validform_checktip"></span>
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
					${tBHiddenDangerExamPage.dealType eq "1"? '限期整改':""}
							${tBHiddenDangerExamPage.dealType eq "2"? '现场处理':""}
			</td>
		</tr>
		<tr id="dealTypetr1">
			<td align="right">
				<label class="Validform_label">
					<font color="red">*</font>限期日期:
				</label>
			</td>
			<td class="value" <c:if test="${beixulou ne 'true'}"> colspan="3" </c:if>>

				<input id="limitDate" name="limitDate" type="text" style="width: 150px" datatype="limDate" readonly="true"  value='<fmt:formatDate value='${tBHiddenDangerExamPage.limitDate}' type="date" pattern="yyyy-MM-dd"/>'>
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
							  typeGroupCode="workShift" defaultVal="${tBHiddenDangerExamPage.limitShift}" hasLabel="false"  title="限期班次"></t:dictSelect>
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
				<input id="reviewManId" name="reviewMan.id" type="hidden" style="width: 150px" class="inputxt" value="${tBHiddenDangerExamPage.reviewMan.id}">
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
            <textarea name="handleEntity.rectMeasures" readonly="true" id="handleEntity.rectMeasures" class="inputxt" style="width: 280px; height: 50px;" >${tBHiddenDangerExamPage.handleEntity.rectMeasures}</textarea>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">整改措施</label>
        </td>
        <td align="right">
            <label class="Validform_label">复查情况:
            </label>
        </td>
        <td class="value">
            <textarea name="handleEntity.reviewReport" readonly="true" id="handleEntity.reviewReport" class="inputxt" style="width: 280px; height: 50px;">${tBHiddenDangerExamPage.handleEntity.reviewReport}</textarea>
            <span class="Validform_checktip"></span>
            <label class="Validform_label" style="display: none;">复查情况</label>
        </td>
    </tr>
        <tr>
            <td colspan="4">
                <div class="ui_buttons" style="text-align: center;">
                    <input type="button" id="btn" value="保存" class="ui_state_highlight" style="display:none">
                    <input type="button" id="btn_draft" value="保存" class="ui_state_highlight" onclick="formSubmit('0')">
                     <input type="button" id="btn_close" onclick="closeWindow();" value="关闭">
                </div>
            </td>
        </tr>


	</table>
</t:formvalid>

</table>
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


