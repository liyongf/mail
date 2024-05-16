<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>事故</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function() {
//      $('#deptName').combotree({
//          url : 'tBThreeViolationsController.do?setTSDeparts',
//          width: 460,
//          onSelect : function(node) {
//              $('#deptName').val(node.id);
//              $('#deptid').val(node.id);
//          }
//      });
	  var magicsuggestSelected = getDepartMagicSuggest($("#deptNameSelect"), $("#deptName"));
	  $(magicsuggestSelected).on('selectionchange', function(e,m){
		  $("#deptid").val(magicsuggestSelected.getValue());
	  });
  });
  function uploadFile(data){
      if($(".uploadify-queue-item").length>0){
          $("#bussId").val(data.attributes.id);
          upload();
      }else{
          frameElement.api.opener.reloadTable();
          frameElement.api.close();
      }
  }
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" callback="@Override uploadFile" action="tBAccidentController.do;jsessionid=${pageContext.session.id}?doAdd" tiptype="3">
					<input id="id" name="id" type="hidden" value="${tBAccidentPage.id }">
		<table style="width: 800px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							事故单位:
						</label>
					</td>
					<td class="value" colspan="3">
						<div id="deptNameSelect" style="width: 130px;height: 15px"></div>
					     	 <input id="deptName" name="deptName" type="hidden" style="width: 460px" class="inputxt" >
					     	 <input id="deptid" name="deptid" type="hidden" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">事故单位</label>
						</td>
                    </tr>
                <tr>
					<td align="right">
						<label class="Validform_label">
							事故编号:
						</label>
					</td>
					<td class="value" colspan="3">
					     	 <input id="accidentcode" name="accidentcode" type="text" style="width: 460px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">事故编号</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							事故名称:
						</label>
					</td>
					<td class="value" colspan="3">
					     	 <input id="accidentname" name="accidentname" type="text" style="width: 460px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">事故名称</label>
						</td>
                    </tr>
                <tr>
                    <td align="right">
                        <label class="Validform_label">
                            发生地点:
                        </label>
                    </td>
                    <td class="value" colspan="3">
                        <input id="happenaddress" name="happenaddress" type="text" style="width: 460px" class="inputxt" >
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">发生地点</label>
                    </td>
                </tr>
                <tr>
					<td align="right">
						<label class="Validform_label">
							发生时间:
						</label>
					</td>
					<td class="value">
					     	 <input id="happentime" name="happentime" type="text" style="width: 150px"  class="Wdate" onClick="WdatePicker({maxDate:'%y-%M-%d'})">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">发生时间</label>
						</td>
                    <td align="right">
                        <label class="Validform_label">
                            事故类型:
                        </label>
                    </td>
                    <td class="value">

                        <t:dictSelect field="accidenttype" type="list"
                                      typeGroupCode="accidentCate"  hasLabel="false"  title="事故类型"></t:dictSelect>
                        <span class="Validform_checktip"></span>
                        <label class="Validform_label" style="display: none;">事故类型</label>
                    </td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							事故等级:
						</label>
					</td>
					<td class="value" colspan="3">
                        <select name="accidentlevel.id" id="accidentlevel.id">
                            <c:forEach items="${levelEntityList}" var="accidentLevel">
                                <option value="${accidentLevel.id}">${accidentLevel.accidentlevel}</option>
                            </c:forEach>
                        </select>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">事故等级</label>
						</td>
                    </tr>
                <tr>
					<td align="right">
						<label class="Validform_label">
							事故经过:
						</label>
					</td>
					<td class="value" colspan="3">
                        <textarea id="accidentdetail" name="accidentdetail" style="width: 460px;"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">事故经过</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
                            <font color="red">*</font>死亡人数:
						</label>
					</td>
					<td class="value">
					     	 <input id="deathnum" name="deathnum" type="text" style="width: 150px" class="inputxt" datatype="n">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">死亡人数</label>
						</td>
					<td align="right">
						<label class="Validform_label">
                            <font color="red">*</font>重伤人数:
						</label>
					</td>
					<td class="value">
					     	 <input id="heavywoundnum" name="heavywoundnum" type="text" style="width: 150px" class="inputxt" datatype="n">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">重伤人数</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
                            <font color="red">*</font>轻伤人数:
						</label>
					</td>
					<td class="value">
					     	 <input id="minorwoundnum" name="minorwoundnum" type="text" style="width: 150px" class="inputxt" datatype="n">
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">轻伤人数</label>
						</td>
					<td align="right">
						<label class="Validform_label">
                            <font color="red">*</font>直接损失:
						</label>
					</td>
					<td class="value">
					     	 <input id="directdamage" name="directdamage" type="text" style="width: 150px" class="inputxt" datatype="d">(万元)
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">直接损失</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
                            <font color="red">*</font>间接损失:
						</label>
					</td>
					<td class="value">
					     	 <input id="consequentialloss" name="consequentialloss" type="text" style="width: 150px" class="inputxt" datatype="d">(万元)
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">间接损失</label>
						</td>
					<td align="right">
						<label class="Validform_label">
							起因物:
						</label>
					</td>
					<td class="value">
					     	 <input id="cause" name="cause" type="text" style="width: 150px" class="inputxt" >
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">起因物</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							直接原因:
						</label>
					</td>
					<td class="value" colspan="3">
                        <textarea id="immediatecause" name="immediatecause" style="width: 460px;"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">直接原因</label>
						</td>
                    </tr>
                <tr>
					<td align="right">
						<label class="Validform_label">
							间接原因:
						</label>
					</td>
					<td class="value" colspan="3">
                        <textarea id="remotecause" name="remotecause" style="width: 460px;"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">间接原因</label>
						</td>
					</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							事故教训:
						</label>
					</td>
					<td class="value" colspan="3">
                        <textarea id="accidentlesson" name="accidentlesson" style="width: 460px;"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">事故教训</label>
						</td>
                    </tr>
                <tr>
					<td align="right">
						<label class="Validform_label">
							防御措施:
						</label>
					</td>
					<td class="value" colspan="3">
                        <textarea id="securityclampdown" name="securityclampdown" style="width: 460px;"></textarea>
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">防御措施</label>
						</td>
					</tr>
                <tr>
                    <td>
                        <label class="Validform_label">
                        上传事故报告:
                        </label>
                    </td>
                    <td class="value" colspan="3">
                        <input type="hidden" id="bussId" name="bussId" />
                            <t:upload name="fiels" buttonText="上传事故报告" uploader="tBAccidentController.do;jsessionid=${pageContext.session.id}?doAddFiels&typecode=${typecode}" multi="true" extend="office" id="file_upload" formData="bussId,jsessionid"></t:upload>
                        <div id="filediv" style="height: 50px">

                        </div>
                    </td>
                </tr>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/accident/js/tBAccident.js"></script>