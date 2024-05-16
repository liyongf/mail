<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>t_b_danger_source</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
      function valReviewResult(reviewResult){
          if("1" == reviewResult){
              $("#remark").removeAttr("datatype");
              $("span[class='Validform_checktip Validform_wrong']").hide();
			  $("#remarkRequired").hide();
          }else if("0" == reviewResult){
              $("#remark").attr("datatype","*");
			  $("#remarkRequired").show();
          }
      }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBDangerSourceController.do?doReviewDangerSource" >
		<input id="ids" name="ids" type="hidden" value="${ids }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							审核结果:
						</label>
					</td>
					<td class="value">
						<label><input type="radio" name="reviewResult" onchange="valReviewResult('1')" value="1" checked="checked"/>审核通过</label>
						<label><input type="radio" name="reviewResult" onchange="valReviewResult('0')" value="0" />驳回</label>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">审核结果</label>
						</td>
                    </tr>
            <tr>
					<td align="right">
						<label class="Validform_label">
							<font id="remarkRequired" color="red" style="display: none;">*</font>备注:
						</label>
					</td>
					<td class="value">
                            <textarea id="remark" name="remark" style="width: 440px;height: auto" rows="3" class="inputxt"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">备注</label>
						</td>
					</tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/dangersource/js/tBDangerSource.js"></script>
