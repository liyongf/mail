<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
  <t:datagrid name="csList"         title="危害因素" actionUrl="statiController.do?whysDatagrid" fit="true" fitColumns="true" idField="id" queryMode="group">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="危害因素"  field="accidentlevel"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="管控措施"  field="standard" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="危害因素等级"  field="color" dictionary="a"   query="true" queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="管控单位"  field="sortindex" dictionary="b"   queryMode="single" query="true" width="120" align="center"></t:dgCol>
   <t:dgCol title="管控责任人"  field="createName" dictionary="c" query="true"  queryMode="single"  width="120" align="center"></t:dgCol>
   <t:dgCol title="管控方案落实情况"  field="ls" width="120" align="center"></t:dgCol>
   <t:dgCol title="隐患数量"  field="sl" dictionary="c" query="true"  queryMode="single"  width="120" align="center"></t:dgCol>

  </t:datagrid>
