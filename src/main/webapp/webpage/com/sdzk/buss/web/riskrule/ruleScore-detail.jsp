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
 <table style="width: 80%;margin:0 auto;position: relative;top:60px;" cellpadding="3" cellspacing="1" class="formtable">
     <tr>
         <td align="right">
             <label class="Validform_label">
                 <b style="color: red">*</b>隐患等级:
             </label>
         </td>
         <td class="value">
             <t:dictSelect field="riskType" type="list" extendJson="{\"datatype\":\"*\"}" defaultVal="${tbriskRuleScoreManagerEntity.riskType}"
                           typeGroupCode="hiddenLevel"  hasLabel="false"  title="隐患等级" readonly="readonly"></t:dictSelect>
             <span class="Validform_checktip"></span>
             <label class="Validform_label" style="display: none;">隐患等级</label>
         </td>
     </tr>
     <tr>
         <td align="right">
             <label class="Validform_label">
                 <b style="color: red">*</b>分值:
             </label>
         </td>
         <td class="value">
             <input id="score" name="score" type="text" style="width: 150px" value="${tbriskRuleScoreManagerEntity.score}" class="inputxt" datatype="*">
             <span class="Validform_checktip"></span>
             <label class="Validform_label" style="display: none;">分值</label>
         </td>
     </tr>
  </table>
 </t:formvalid>
 </body>