<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
<title>灯和风险点对应信息</title>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
  <script type="text/javascript">
  //编写自定义JS代码
  $(function(){
	 var addressSelect = getAddressMagicSuggest($('#addressSelect'), $("#addressId"));
  })
  </script>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBLightAddressController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${tBLightAddressPage.id }"/>
		<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>灯标识:
					</label>
				</td>
				<td class="value">
					<input id="lightId" name="lightId" type="text" style="width: 150px" datatype="*1-100"/>
					<span class="Validform_checktip"></span>
					<label class="Validform_label" style="display: none;">灯标识</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						<b style="color: red">*</b>风险点:
					</label>
				</td>
				<td class="value">
						<div id="addressSelect" style="width: 130px;height: 15px"></div>
						<input id="addressId" type="hidden" name="addressId" datatype="*">
						<span class="Validform_checktip"></span>
						<label class="Validform_label" style="display: none;">风险点</label>
				</td>
			</tr>
		</table>
	</t:formvalid>