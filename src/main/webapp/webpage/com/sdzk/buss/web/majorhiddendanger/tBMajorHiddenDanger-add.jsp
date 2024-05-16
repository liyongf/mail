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
	 <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
	 <script src = "webpage/com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger.js"></script>
     <link href="plug-in/jQueryLabel/css/tab.css" type="text/css" rel="stylesheet" />
     <script type="text/javascript" src="plug-in/jQueryLabel/js/tab.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
	  $(function(){

          //此部分代码用来添加关键词字段，获取关键词，将其拼成一句，以逗号分隔，然后设置到隐藏的id为keyWord的隐藏input中提交
          $("#btn_sub").on("click", function(){
              var keyWords="";
              $("#myTags>a").each(function(){
                  keyWords = keyWords+$(this).attr("title")+",";
              });
              var str = keyWords;
              keyWords = str.substring(0,str.length-1);
              $("#keyWords").val(keyWords);
          });

		  getAddressMagicSuggest($('#hdLocationSelect'), $("#hdLocation"));
		  var magicsuggestDutyUnitSelSelected = $('#magicsuggestDutyUnitSel').magicSuggest({
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


          var magicsuggestDutyManSelected = getUserMagicSuggestAllowFreeEntries($('#magicsuggestDutyMan'),  $("#dutyMan"), "${tBHiddenDangerExamPage.dutyMan}", false);

          $(magicsuggestDutyManSelected).on('focus', function(c){
              var deptId = $('#dutyUnitId').val();
              magicsuggestDutyManSelected.setData("magicSelectController.do?getUserList&orgIds="+deptId);
          });
	  })
  function choose_dangerSource(title){
	  var addressId=$("#hdLocation").val();
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
	  if($("#hdDesc").text()==null||$("#hdDesc").text()==''){
		  var hdDesc = iframe.gettBDangerSourceListSelections('yeMhazardDesc');
		  $("#hdDesc").text(hdDesc);
	  }
      var id = iframe.gettBDangerSourceListSelections('id');
      $("input[name='dangerId.id']").val(id);
      $("input[name='dangerId.id']").blur();
  }

  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true"  layout="table" action="tBMajorHiddenDangerController.do?doAdd" callback="@Override noteSubmitCallbackAdd" tiptype="3">
		<input id="id" name="id" type="hidden" value="${tBMajorHiddenDangerPage.id }"/>
	    <input id="clStatus" name="clStatus" type="hidden">

        <%--默认省局挂牌督办和分局挂牌督办为未挂牌--%>
        <input id="isLsSub" name="isLsSub" type="hidden" value="0">
        <input id="isLsProv" name="isLsProv" type="hidden" value="0">

		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>隐患地点:
						</label>
					</td>
					<td class="value">
						<div id="hdLocationSelect" style="width: 130px;height: 15px"></div>
					     	 <input id="hdLocation" name="hdLocation" type="hidden" style="width: 150px" class="inputxt" datatype="*"/>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">隐患地点</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>排查日期:
						</label>
					</td>
					<td class="value">
							   <input id="inveDate" name="inveDate" type="text" style="width: 150px" class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})" datatype="*" />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">排查日期</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>隐患信息来源:
						</label>
					</td>
					<td class="value">
						<t:dictSelect id="hdInfoSource" field="hdInfoSource" defaultVal="" typeGroupCode="hiddenFrom" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">隐患信息来源</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>隐患等级:
						</label>
					</td>
					<td class="value">
						<%--<t:dictSelect id="hdLevel" field="hdLevel" defaultVal="1" typeGroupCode="hiddenLevel" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>--%>
                        重大隐患<input type="hidden" value="1" name="hdLevel">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">隐患等级</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>隐患类别:
						</label>
					</td>
					<td class="value">
						<t:dictSelect id="hdCate" field="hdCate" defaultVal="" typeGroupCode="hiddenCate" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">隐患类别</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>隐患专业:
						</label>
					</td>
					<td class="value">
						<t:dictSelect id="hdMajor" field="hdMajor" defaultVal="" typeGroupCode="proCate_gradeControl" hasLabel="false" extendJson="{\"datatype\":\"*\"}" ></t:dictSelect>
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">隐患专业</label>
                    </td>
                </tr>
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

            <%--&lt;%&ndash;关键字——开始&ndash;%&gt;--%>
            <%--<tr>--%>
                <%--<td align="right">--%>
                    <%--<label class="Validform_label">--%>
                        <%--关键字:--%>
                    <%--</label>--%>
                <%--</td>--%>
                <%--<td class="value" colspan="3">--%>

                    <%--<div class="rightAdd">--%>
                        <%--<div id="mycard-plus" style="margin-top: 0px;">--%>
                            <%--<div class="default-tag tagbtn">--%>
                                <%--<div class="clearfix">--%>
                                        <%--&lt;%&ndash;  从字典读取关键字  &ndash;%&gt;--%>
                                    <%--<t:keySelect  typeGroupCode="hiddenKeyWords"/>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</div>--%>

                        <%--<div class="plus-tag-add">--%>
                            <%--<ul class="Form FancyForm" style="margin-top: 8px;">--%>
                                <%--<li>--%>
                                    <%--<input name="" type="text" class="stext" id="addKeysinput" maxlength="20" />--%>
                                    <%--<label>输入关键字</label>--%>
                                    <%--<button id="addKeysId" type="button"  class="Button RedButton" style="font-size:14px;">添加关键字</button>--%>
                                <%--</li>--%>
                            <%--</ul>--%>
                        <%--</div>--%>
                    <%--</div>--%>

                <%--</td>--%>
            <%--</tr>--%>
                <%--&lt;%&ndash;关键字&ndash;%&gt;--%>
            <%--<tr>--%>
                <%--<td align="right">--%>
                    <%--<label class="Validform_label">--%>
                        <%--已选关键字:--%>
                    <%--</label>--%>
                <%--</td>--%>
                <%--<td class="value" colspan="3">--%>
                        <%--&lt;%&ndash;------------------------------------------------------------------------&ndash;%&gt;--%>
                    <%--<div class="demo" style="margin-top:1px;overflow:hidden;">--%>
                        <%--<input type="hidden" name="keyWords" id="keyWords" nullmsg="请输入关键字！"/>  <!-- 这个input是隐藏的input，用来将数据提交 -->--%>
                        <%--<div class="plus-tag tagbtn clearfix myTagsBorder" id="myTags"></div>--%>
                    <%--</div>--%>
                        <%--&lt;%&ndash;------------------------------------------------------------------------&ndash;%&gt;--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--&lt;%&ndash;关键字——结束&ndash;%&gt;--%>

            <%--<tr>--%>
                <%--<td align="right">--%>
                    <%--<label class="Validform_label">--%>
						<%--<font color="red">*</font>危险源:--%>
                    <%--</label>--%>
                <%--</td>--%>
                <%--<td class="value" colspan="3">--%>
                    <%--<input id="dangerId.id" name="dangerId.id" type="hidden" datatype="*" style="width: 150px" class="inputxt" nullmsg="请选择危险源！">--%>
                    <%--<textarea id="dangerName" name="dangerName" style="width: 440px;" onclick="choose_dangerSource('选择危险源');"></textarea>--%>
                    <%--<span class="Validform_checktip"></span>--%>
                    <%--<label class="Validform_label" style="display: none;">危险源</label>--%>
                <%--</td>--%>
            <%--</tr>--%>

				<tr>
					<td align="right">
						<label class="Validform_label">
							<font color="red">*</font>责任单位:
						</label>
					</td>
					<td class="value">
						<div id="magicsuggestDutyUnitSel" style="width: 130px;height: 15px"></div>
						<input id="dutyUnitId" name="dutyUnit.id" type="hidden" style="width: 150px" class="inputxt" datatype="*">
						<%--<span class="Validform_checktip">请选择责任单位</span>--%>
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
						<%--<span class="Validform_checktip">请选择责任人</span>--%>
						<label class="Validform_label" style="display: none;">责任人</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							<b style="color: red">*</b>整改时限:
						</label>
					</td>
					<td class="value">
						<input id="rectPeriod" name="rectPeriod" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker()" datatype="*"/>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">整改时限</label>
					</td>
					<td align="right">
						<label class="Validform_label">
						</label>
					</td>
					<td class="value">
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							隐患描述:
						</label>
					</td>
					<td class="value" colspan="3">
						<textarea  id="hdDesc" name="hdDesc" style="height: auto;width: 80%;" class="inputxt" rows="6"></textarea>
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">隐患描述</label>
					</td>
				</tr>
            <tr>
                <td colspan="4">
                    <div class="ui_buttons" style="text-align: center;">
                        <input type="button" value="确定" onclick="saveNoteInfo('0');" class="ui_state_highlight">
                        <input type="button" value="提交" onclick="saveNoteInfo('1');">
                    </div>
                </td>
            </tr>
			</table>
		</t:formvalid>
 </body>
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
     })();
 </script>
