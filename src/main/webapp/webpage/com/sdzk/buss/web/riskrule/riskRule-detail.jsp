<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>分值配置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body>
 <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" >
     <table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
         <tr>
             <td align="right">
                 <label class="Validform_label">
                     <b style="color: red">*</b>风险等级:
                 </label>
             </td>
             <td class="value">
                 <t:dictSelect field="riskType" type="list" extendJson="{\"datatype\":\"*\"}" readonly="readonly" defaultVal="${riskRuleManagerEntity.riskType}"
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
                 <input id="scoreBetween" name="scoreBetween" type="text" style="width: 150px" class="inputxt" value="${riskRuleManagerEntity.scoreBetween}">~
                 <input id="scoreEnd" name="scoreEnd" type="text" style="width: 150px" class="inputxt" value="${riskRuleManagerEntity.scoreEnd}">
                 <span class="Validform_checktip"></span>
                 <label class="Validform_label" style="display: none;">分值区间</label>
             </td>
         </tr>
     </table>
 </t:formvalid>
 </body>