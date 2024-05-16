<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>分值配置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  //保存回调
  function saveCallback(data) {

      var win = frameElement.api.opener;


      if (data.success == true){
          win.tip(data.msg);
          frameElement.api.close();
          win.riskRuleListsearch();
      }else{
          tip(data.msg);
      }
  }

  function valBetweenEnd() {
      var scoreBetween = $("#scoreBetween").val();
      var scoreEnd = $("#scoreEnd").val();

      if((scoreBetween == null || scoreBetween.length <=0)&&(scoreEnd == null || scoreEnd.length <=0)){
          tip("请输入分值区间");
          return false;
	  }else{
          //校验是否是数字
          if(scoreBetween != null && scoreBetween.length >0){
              var isNum = isNumber(scoreBetween);
              if(!isNum){
                  tip("请输入正确的分值");
                  return false;
			  }
		  }
          if(scoreEnd != null && scoreEnd.length >0){
              var isNum = isNumber(scoreEnd);
              if(!isNum){
                  tip("请输入正确的分值");
                  return false;
              }
          }
	  }
      return true;
  }

  function isNumber(value) {         //验证是否为数字
      var patrn = /^(-)?\d+(\.\d+)?$/;
      if (patrn.exec(value) == null || value == "") {
          return false
      } else {
          return true
      }
  }

  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="riskRuleController.do?doAdd" beforeSubmit="valBetweenEnd" callback="@Override saveCallback">
		<input id="id" name="id" type="hidden" value="${riskRuleManagerEntity.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>风险等级:
					</label>
				</td>
				<td class="value">
					<t:dictSelect field="riskType" type="list" extendJson="{\"datatype\":\"*\"}"
								  typeGroupCode="riskLevel"  hasLabel="false"  title="风险等级"></t:dictSelect>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">风险等级</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>分值区间:
					</label>
				</td>
				<td class="value">
					<input id="scoreBetween" name="scoreBetween" type="text" style="width: 150px" class="inputxt" >~
					<input id="scoreEnd" name="scoreEnd" type="text" style="width: 150px" class="inputxt" >
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">分值区间</label>
				</td>
			</tr>
			</table>
		</t:formvalid>
 </body>
