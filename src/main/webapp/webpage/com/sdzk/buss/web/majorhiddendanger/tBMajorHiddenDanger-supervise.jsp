<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>挂牌督办详情查看</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	 <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
	 <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
	 <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
  <script type="text/javascript">
  <%--//编写自定义JS代码--%>
  <%--$(function(){--%>
	  <%--getAddressMagicSuggestWithValue($('#hdLocationSelect'), $("#hdLocation"),"${tbMajorSupervisePage.hdLocation}",true);--%>
  <%--})--%>
  </script>
 </head>
 <body>
 <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="tBMajorHiddenDangerController.do?doUpdate" >

     <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
         <c:if test="${not empty provListedInfo}">

             <tr style="height: 50px;">
                 <td align="center"  colspan="4" class="value">
                     <label class="Validform_label" style="font-family:'微软雅黑' ;font-size: 16px; letter-spacing: 5px;">
                         省局挂牌督办
                     </label>
                 </td>
             </tr>

             <tr>
                 <td align="right">
                     <label class="Validform_label">
                         挂牌督办时间:
                     </label>
                 </td>
                 <td class="value">
                     <div id="hdLocationSelect" style="width: 130px;height: 0px"></div>
                         <fmt:formatDate value="${provListedInfo.lsiLsTime}" pattern="yyyy-MM-dd" type="date" />
                     <span class="Validform_checktip"></span>
                     <label class="Validform_label" style="display: none;">挂牌督办时间</label>
                 </td>

                 <td align="right">
                     <label class="Validform_label">
                         挂牌督办级别:
                     </label>
                 </td>
                 <td class="value">
                     <div id="hdLocationSelect" style="width: 130px;height: 0px"></div>
                     <c:if test="${provListedInfo.lsiIsLevel eq '1'}">
                         分局督办
                     </c:if>
                     <c:if test="${provListedInfo.lsiIsLevel eq '2'}">
                         省局督办
                     </c:if>
                     <span class="Validform_checktip"></span>
                     <label class="Validform_label" style="display: none;">挂牌督办级别</label>
                 </td>
             </tr>

             <tr>
                 <td align="right">
                     <label class="Validform_label">
                         挂牌督办单位:
                     </label>
                 </td>
                 <td class="value">
                     <div id="hdLocationSelect" style="width: 130px;height: 0px"></div>
                         ${provListedInfo.lsiLsUnit}
                         <%--省局挂牌督办--%>
                     <span class="Validform_checktip"></span>
                     <label class="Validform_label" style="display: none;">挂牌督办单位</label>
                 </td>

                 <td align="right">
                     <label class="Validform_label">
                         挂牌督办文号:
                     </label>
                 </td>
                 <td class="value">
                     <div id="hdLocationSelect" style="width: 130px;height: 0px"></div>
                         ${provListedInfo.lsiShNum}
                     <span class="Validform_checktip"></span>
                     <label class="Validform_label" style="display: none;">挂牌督办文号</label>
                 </td>
             </tr>
         </c:if>
<%--————————————————————————————————————————————————————————————————————--%>
         <c:if test="${not empty subListedInfo}">
             <tr style="height: 50px;">
                 <td align="center"  colspan="4" class="value">
                     <label class="Validform_label" style="font-family:'微软雅黑' ;font-size: 16px; letter-spacing: 5px;">
                         分局挂牌督办
                     </label>
                 </td>
             </tr>

             <tr>
                 <td align="right">
                     <label class="Validform_label">
                         挂牌督办时间:
                     </label>
                 </td>
                 <td class="value">
                     <div id="hdLocationSelect" style="width: 130px;height: 0px"></div>
                         ${subListedInfo.lsiLsTime}
                     <span class="Validform_checktip"></span>
                     <label class="Validform_label" style="display: none;">挂牌督办时间</label>
                 </td>

                 <td align="right">
                     <label class="Validform_label">
                         挂牌督办级别:
                     </label>
                 </td>
                 <td class="value">
                     <div id="hdLocationSelect" style="width: 130px;height: 0px"></div>
                         ${subListedInfo.lsiIsLevel}
                     <span class="Validform_checktip"></span>
                     <label class="Validform_label" style="display: none;">挂牌督办级别</label>
                 </td>
             </tr>

             <tr>
                 <td align="right">
                     <label class="Validform_label">
                         挂牌督办单位:
                     </label>
                 </td>
                 <td class="value">
                     <div id="hdLocationSelect" style="width: 130px;height: 0px"></div>
                         <%--${subListedInfo.lsiLsUnit}--%>
                         分局挂牌督办
                     <span class="Validform_checktip"></span>
                     <label class="Validform_label" style="display: none;">挂牌督办单位</label>
                 </td>

                 <td align="right">
                     <label class="Validform_label">
                         挂牌督办文号:
                     </label>
                 </td>
                 <td class="value">
                     <div id="hdLocationSelect" style="width: 130px;height: 0px"></div>
                         ${subListedInfo.lsiShNum}
                     <span class="Validform_checktip"></span>
                     <label class="Validform_label" style="display: none;">挂牌督办文号</label>
                 </td>
             </tr>
         </c:if>
         <%--————————————————————————————————————————————————————————————————--%>
         <%-- 如果挂牌督办级别不存在，那么则无挂牌督办信息，提示无挂牌督办信息。 --%>
         <c:if test="${null eq subListedInfo}">
             <tr style="height: 50px;">
                 <td align="center"  colspan="4" class="value">
                     <label class="Validform_label" style="font-family:'微软雅黑' ;font-size: 16px; letter-spacing: 5px;">
                         无挂牌督办信息！
                     </label>
                 </td>
             </tr>
         </c:if>
     </table>

 </t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/majorhiddendanger/tBMajorHiddenDanger.js"></script>		
