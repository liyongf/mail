<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>部门信息</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">
	$(function() {
		$('#cc').combotree({
			url : 'departController.do?setPFunction&selfId=${depart.id}',
            width: 155,
            onSelect : function(node) {

             //   changeOrgType(node.id);

            }
        });
		
		/*var parentId = $('#cc').val();
        if(!parentId) { // 第一级，只显示公司选择项
            var orgTypeSelect = $("#orgType");
            var companyOrgType = '<option value="1" <c:if test="${orgType=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.company"/></option>';
            orgTypeSelect.empty();
            orgTypeSelect.append(companyOrgType);
        } else { // 非第一级，不显示公司选择项
            $("#orgType option:first").remove();
        }*/

        if($("#id").val()) {
            if('${sjdw}' == 'true') { // 设置新增页面时的父级
                //$('#cc').combotree('disable');
            }else{
                $('#cc').combotree('disable');
            }

        }
        if('${empty pid}' == 'false') { // 设置新增页面时的父级
            $('#cc').combotree('setValue', '${pid}');
        }
	});

/*    function changeOrgType(parentId) { // 处理组织类型，不显示公司选择项
        var orgTypeSelect = $("#orgType");
        if(parentId!=null && parentId!='') {
            var bumen = '<option value="2" <c:if test="${orgType=='2'}">selected="selected"</c:if>><t:mutiLang langKey="common.department"/></option>';
            var gangwei = '<option value="3" <c:if test="${orgType=='3'}">selected="selected"</c:if>><t:mutiLang langKey="common.position"/></option>';
            orgTypeSelect.empty();
            orgTypeSelect.append(bumen).append(gangwei);
        }else{
        	var orgTypeSelect = $("#orgType");
            var companyOrgType = '<option value="1" <c:if test="${orgType=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.company"/></option>';
            orgTypeSelect.empty();
            orgTypeSelect.append(companyOrgType);
        }
    }*/

</script>
</head>
<body>
<t:formvalid formid="formobj" layout="div" dialog="true" action="systemController.do?saveDepart">
    <input id="id" name="id" type="hidden" value="${depart.id }">
    <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
        <tr>
            <td align="right">
                <label class="Validform_label"> <t:mutiLang langKey="common.department.name"/>: </label>
            </td>
            <td class="value" colspan="3">
                <input name="departname" class="inputxt" type="text" value="${depart.departname }"  datatype="s1-20">
                <span class="Validform_checktip"><t:mutiLang langKey="departmentname.rang1to20"/></span>
            </td>
            <td align="right">
                <label class="Validform_label"> <t:mutiLang langKey="position.desc"/>: </label>
            </td>
            <td class="value" colspan="3">
                <input name="description" class="inputxt" value="${depart.description }">
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label"> <t:mutiLang langKey="parent.depart"/>: </label>
            </td>
            <td class="value" colspan="3">
                <input id="cc" name="TSPDepart.id" value="${depart.TSPDepart.id}">
            </td>
            <td align="right">
                <input type="hidden" name="orgCode" value="${depart.orgCode }">
                <label class="Validform_label"> <t:mutiLang langKey="common.org.type"/>: </label>
            </td>
            <td class="value" colspan="3">
                <t:dictSelect field="orgType" type="list"
                              typeGroupCode="orgtype"  hasLabel="false"  title="机构类型"></t:dictSelect>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label"> <t:mutiLang langKey="common.mobile"/>: </label>
            </td>
            <td class="value" colspan="3">
                <input name="mobile" class="inputxt" value="${depart.mobile }">
            </td>
            <td align="right">
                <label class="Validform_label"> <t:mutiLang langKey="common.fax"/>: </label>
            </td>
            <td class="value" colspan="3">
                <input name="fax" class="inputxt" value="${depart.fax }">
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label"> <t:mutiLang langKey="common.address"/>: </label>
            </td>
            <td class="value" colspan="3">
                <input name="address" class="inputxt" value="${depart.address }">
                <span class="Validform_checktip"></span>
            </td>
            <td align="right">
                <label class="Validform_label">一级电话: </label>
            </td>
            <td class="value" colspan="3">
                <input name="pho1" class="inputxt" value="${depart.pho1 }">
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">一级微信号: </label>
            </td>
            <td class="value" colspan="3">
                <input name="wx1" class="inputxt" value="${depart.wx1 }">
                <span class="Validform_checktip"></span>
            </td>
            <td align="right">
                <label class="Validform_label">二级电话: </label>
            </td>
            <td class="value" colspan="3">
                <input name="pho2" class="inputxt" value="${depart.pho2 }">
                <span class="Validform_checktip"></span>
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">二级微信号: </label>
            </td>
            <td class="value" colspan="3">
                <input name="wx2" class="inputxt" value="${depart.wx2 }">
                <span class="Validform_checktip"></span>
            </td>
            <td align="right">
                <label class="Validform_label">三级电话: </label>
            </td>
            <td class="value" colspan="3">
                <input name="pho3" class="inputxt" value="${depart.pho3 }">
            </td>
        </tr>
        <tr>
            <td align="right">
                <label class="Validform_label">三级微信号: </label>
            </td>
            <td class="value" colspan="3">
                <input name="wx3" class="inputxt" value="${depart.wx3 }">
                <span class="Validform_checktip"></span>
            </td>
            <td align="right">

            </td>
            <td class="value" colspan="3">

            </td>
        </tr>
    </table>
</t:formvalid>
</body>
</html>
