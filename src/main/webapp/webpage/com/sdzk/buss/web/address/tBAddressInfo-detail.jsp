<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>地点信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body>
 <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" >
 <table style="width: 80%;margin:0 auto;position: relative;top:60px;" cellpadding="3" cellspacing="1" class="formtable">
     <tr>
         <td align="right">
             <label class="Validform_label">
                 煤层:
             </label>
         </td>
         <td class="value">
             ${tBAddressInfoPage.belongLayer.layerDetailName}
         </td>
     </tr>
 <tr>
     <td align="right" style="width:10%">
         <label class="Validform_label">
             经度:
         </label>
     </td>
     <td class="value" style="width:40%"> ${tBAddressInfoPage.lon}
     </td>
 </tr>
 <tr>
     <td align="right" style="width:10%">
         <label class="Validform_label">
             纬度:
         </label>
     </td>
     <td class="value" style="width:40%">
             ${tBAddressInfoPage.lat}
     </td>
 </tr>
 <tr>
     <td align="right">
         <label class="Validform_label">
             地点名称:
         </label>
     </td>
     <td class="value">
             ${tBAddressInfoPage.address}
     </td>
 </tr>
     <tr>
         <td align="right">
             <label class="Validform_label">
                 管控单位:
             </label>
         </td>
         <td class="value">
                 ${tBAddressInfoPage.manageUnit}
         </td>
     </tr>
     <tr>
         <td align="right">
             <label class="Validform_label">
                 分管责任人:
             </label>
         </td>
         <td class="value">
                 ${tBAddressInfoPage.manageMan}
         </td>
     </tr>
     <tr>
         <td align="right">
             <label class="Validform_label">
                 风险类型:
             </label>
         </td>
         <td class="value">
                 ${tBAddressInfoPage.riskTypeTemp}
         </td>
     </tr>
 <%--<tr>--%>
     <%--<td align="right">--%>
         <%--<label class="Validform_label">--%>
             <%--类型:--%>
         <%--</label>--%>
     <%--</td>--%>
     <%--<td class="value">--%>
             <%--${tBAddressInfoPage.cate}--%>
     <%--</td>--%>
 <%--</tr>--%>
     <tr>
         <td align="right">
             <label class="Validform_label">
                 是否显示:
             </label>
         </td>
         <td class="value">
             <c:if test="${tBAddressInfoPage.isshow eq 'Y'}">是</c:if>
             <c:if test="${tBAddressInfoPage.isshow eq 'N'}">否</c:if>
         </td>
     </tr>
     <tr>
         <td align="right">
             <label class="Validform_label">
                 位置描述:
             </label>
         </td>
         <td class="value">
                 ${tBAddressInfoPage.description}
         </td>
     </tr>
     <tr>
         <td align="right">
             <label class="Validform_label">
                 排查日期:
             </label>
         </td>
         <td class="value">
             <fmt:formatDate value='${tBAddressInfoPage.investigationDate}' type="date" pattern="yyyy-MM-dd"/>
         </td>
     </tr>
     <tr>
         <td align="right">
             <label class="Validform_label">
                 开始日期:
             </label>
         </td>
         <td class="value">
             <fmt:formatDate value='${tBAddressInfoPage.startDate}' type="date" pattern="yyyy-MM-dd"/>
         </td>
     </tr>
     <tr>
         <td align="right">
             <label class="Validform_label">
                 结束日期:
             </label>
         </td>
         <td class="value">
             <fmt:formatDate value='${tBAddressInfoPage.endDate}' type="date" pattern="yyyy-MM-dd"/>
         </td>
     </tr>
     <tr>
         <td align="right">
             <label class="Validform_label">
                 备注:
             </label>
         </td>
         <td class="value">
                 ${tBAddressInfoPage.remark}
         </td>
     </tr>
  </table>
 </t:formvalid>
 </body>
  <script src = "webpage/com/sdzk/buss/web/riskalert/js/tBAddressInfo.js"></script>