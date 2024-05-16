<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>风险克隆</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
     <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
     <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
     <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
     <link href="plug-in/jQueryLabel/css/tab.css" type="text/css" rel="stylesheet" />
     <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
  <script type="text/javascript">
  //编写自定义JS代码
      function doClone2(){
          var speIdeTask = $("#speIdeTask").val();
          if(speIdeTask == null || speIdeTask =='' || speIdeTask==undefined){
              tip("请选择克隆的临时施工辨识活动");
          }else{
              $.ajax({
                  url : "riskIdentificationController.do?doClone",
                  type : 'post',
                  cache : false,
                  async: true,
                  data : {
                      speIdeTaskId:speIdeTask,
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
                      var d = $.parseJSON(data);
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
 <t:formvalid formid="thisForm" dialog="true" usePlugin="password" layout="table" action="hazardFactorsController.do?doAdd" tiptype="1">
     <table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
         <tr>
             <td align="right">
                 <label class="Validform_label">
                     <b style="color: red">*</b>临时施工辨识活动:
                 </label>
             </td>
             <td class="value">

                 <div id="speIdeTaskSelect" style="width: 130px;height: 15px"></div>
                 <input id="speIdeTask" name="speIdeTask" type="hidden" datatype="*">
                 <span class="Validform_checktip"></span>
                 <label class="Validform_label" style="display: none;">临时施工辨识活动</label>
             </td>
         </tr>

         <tr>
             <td align="right" colspan="2">
                 <div class="ui_buttons" style="text-align: center;">
                     <input type="button" style="height: 50px;width: 200px;font-size: 20px" id="btn" value="保存" class="ui_state_highlight" onclick="doClone2();">
                 </div>
             </td>
         </tr>
     </table>
 </t:formvalid>
 <script>
     $(document).ready(function(){
         var speIdeTaskSelect = $('#speIdeTaskSelect').magicSuggest({
             allowFreeEntries: false,
             data:'magicSelectController.do?getLinShiIdeTask&riskTaskId=${riskTaskId}',
             valueField:'id',
             placeholder:'输入或选择',
             maxSelection:1,
             selectFirst: true,
             highlight: false,
             matchField:['taskname'],
             displayField:'taskname'
         });
         $(speIdeTaskSelect).on('selectionchange', function(e,m){
             $("#speIdeTask").val(speIdeTaskSelect.getValue());
         });
     })
 </script>
 </body>
