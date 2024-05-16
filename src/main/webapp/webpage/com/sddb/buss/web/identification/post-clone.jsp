<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>风险点克隆</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  //编写自定义JS代码
      function doClone2(){
          var rows = $("#hazardFactorsList").datagrid('getSelections');
          if(rows == null || rows.length <=0){
              tip("请选择克隆的岗位");
          }else{
              var postId = rows[0].id;
              $.ajax({
                  url : "riskIdentificationController.do?doClone",
                  type : 'post',
                  cache : false,
                  async: true,
                  data : {
                      postId:postId,
                      identificationType:'${identificationType}',
                      toCloneAddressId :'${toCloneAddressId}',
                      riskTaskId :'${riskTaskId}'
                  },
                  beforeSend: function(data){
                      $.messager.progress({
                          text : "正在克隆数据......",
                          interval : 100
                      });
                  },
                  success : function(data) {
                      $.messager.progress('close');
                      if (d.success) {
//                          var win = frameElement.api.opener;
//                          win.tip(d.msg);
//                          win.showMsg();
//                          frameElement.api.close();
                          window.top.reload_hazardFactorsList.call();
                          tip(d.msg);
                      } else {
                          tip("克隆失败！！");
                      }
                  }
              });
          }
      }
  </script>
 </head>
 <body>
 <t:datagrid name="hazardFactorsList" checkbox="false" pagination="true" fitColumns="false" title="选择克隆岗位" height="5000px" actionUrl="riskIdentificationController.do?clonePostDatagrid&identificationType=${identificationType}" idField="id" fit="true" queryMode="group">
     <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
     <t:dgCol title="岗位"  field="postName"  queryMode="single"  width="120" query="true"></t:dgCol>
     <t:dgToolBar title="克隆"  icon="icon-edit" url="riskIdentificationController.do?doClone&identificationType=${identificationType}" funname="doClone2"  ></t:dgToolBar>
 </t:datagrid>
 </body>
