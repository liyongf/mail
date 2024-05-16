<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>岗位管理</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
  </script>
 </head>
 <body>
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBPostManageController.do?doAdd" >
		<input id="id" name="id" type="hidden" value="${tBPostManagePage.id }"/>
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
                            <b style="color: red">*</b>岗位名称:
						</label>
					</td>
					<td class="value">
					     	 <input id="postName" name="postName" type="text" style="width: 150px" class="inputxt" 
					     	  datatype="*" ajaxurl="tBPostManageController.do?postExists"
					     	  />
							<span class="Validform_checktip"></span>
							<label class="Validform_label" style="display: none;">岗位名称</label>
						</td>
				</tr>
           <%-- <tr>
                <td align="right">
                    <label class="Validform_label">
                        <b style="color: red">*</b> 所属专业:
                    </label>
                </td>
                <td class="value">
                        &lt;%&ndash;<input class="inputxt" id="professionType" name="professionType" ignore="ignore"  value="${tbActivityManagePage.professionType}" />&ndash;%&gt;
                    <t:dictSelect id="professionType" field="professionType" type="list" typeGroupCode="proCate_gradeControl" hasLabel="false"
                                  defaultVal="${tBPostManagePage.professionType}" datatype="*"></t:dictSelect>
                    <span class="Validform_checktip"></span>
                </td>
            </tr>--%>
			</table>
		</t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/tbpostmanage/tBPostManage.js"></script>		
