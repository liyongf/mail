<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>微信发送</title>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
    <link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
    <script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
    <link href="plug-in/jQueryLabel/css/tab.css" type="text/css" rel="stylesheet" />
    <link href="plug-in/lhgDialog/skins/metrole.css" rel="stylesheet" id="lhgdialoglink">
    <script type="text/javascript" src="plug-in/jQueryLabel/js/tab.js"></script>
    <link href="plug-in/minicolors/jquery.minicolors.css" rel="stylesheet">
<script type="text/javascript">

	var modelType = "${modelType}";
	var  HandleId= "${tBHiddenDangerHandle.id}";
	$(function() {
      	//自定义js代码
        var beNoticeManSelect = "";
        beNoticeManSelect = $('#beNoticeManSelect').magicSuggest({
            allowFreeEntries: false,
            data:'magicSelectController.do?getUserList',
            valueField:'id',
            value:[${beNoticeMan}],
            placeholder:'输入或选择',
            maxSelection:10,
            selectFirst: true,
            highlight: false,
            matchField:['spelling','realName','userName','fullSpelling'],
            displayField:'realName'
        });
        $(beNoticeManSelect).on('selectionchange', function(e,m){
            var obj = beNoticeManSelect.getSelection();
            if(obj.length>0){
                $("#beNoticeMan").val(beNoticeManSelect.getValue());
            }else{
                $("#beNoticeMan").val("");
            }

        });
      
        //初始化下拉框
    	$.ajax({
    	     url:"<%=basePath%>/magicSelectController.do?getWeChatModelList", 
    	     type:"post",    
    	     data:{modelType:modelType,HandleId:HandleId},
    	     dataType:"json",
    	     success:function(data){
	    	     var jsondata=JSON.stringify(data.obj);
	    	     var models = eval('(' + jsondata + ')');
	    	     $.each(models,function(a,b){
	    	     	$("#modelType").append("<option value='"+b.id+"'>"+b.modelName+"</option>");
	    	     });   
    	     }
    	});
    	$("#content").val("sb");
        /* magicsuggestWeChatModelSelected = getWeChatModelMagicSuggest($('#magicsuggestWeChatModel'),  $("#zModelType"), "", false);

        $(magicsuggestWeChatModelSelected).on('focus', function(c){
            var modelType = "${modelType}";
            magicsuggestWeChatModelSelected.setData("magicSelectController.do?getWeChatModelList&modelType="+modelType);
        }); */
        
	}); 
	
	function getContent(obj){
		//根据id将拼成的文本放入输入框中
    	var modelId=$("#modelType  option:selected").val();
		
    	$.ajax({
   	     url:"<%=basePath%>/magicSelectController.do?getWeChatContentById", 
   	     type:"post",    
   	     data:{modelType:modelType,modelId:modelId,HandleId:HandleId},
   	     dataType:"json",
   	     success:function(data){
	    	     var jsondata=JSON.stringify(data.obj);
	    	     $("#contentData").val(data.obj);
   	     }
   	});
    }
	
	function formSubmit(){
		var contentdate = $("#contentData").val();
		if(contentdate==null||contentdate==""){
			tip("请选择子模块类型！");
		}else{
			$("#btn_sub").trigger("click");
		}
    }
	
</script>
</head>
<body>
<t:formvalid formid="formobj" layout="div" dialog="true" action="tBHiddenDangerHandleController.do?sendNotice" btnsub="btn_sub">
    <input id="id" name="id" type="hidden" value="${tBHiddenDangerHandle.id }">
    <table style="width: 100%;height: 60%" cellpadding="0" cellspacing="1" class="formtable">
	    	<tr>
						<td align="right">
							<label class="Validform_label">
								模块类型:
							</label>
						</td>
						<td class="value">
            			<t:dictSelect field="modelType" type="list"
                              typeGroupCode="wechat_model_type" hasLabel="false"  title="统计表类型" readonly="readonly" defaultVal="${modelType}"></t:dictSelect>
	                    <span class="Validform_checktip">模块类型</span>
	                    <label class="Validform_label" style="display: none;">模块类型</label>
            			</td>
			</tr>
			<tr>
					<td align="right">
							<label class="Validform_label">
								子模块类型:
							</label>
					</td>
					<td class="value">
						<select id="modelType" name="modelType" style="width:155px" onchange="getContent(this)">
				          	<option value="">请选择---</option>
				        </select>
            			<!-- <div id="magicsuggestWeChatModel" style="width: 130px;height: 15px"></div>
		                	<input id="zModelType" name="zModelType"  style="width: 150px" type ="hidden" class="inputxt" datatype="*">
		                	<span class="Validform_checktip"></span>
		                	<label class="Validform_label" style="display: none;">子模块类型</label>
            			</td> -->
			</tr>
			
	        <tr>
	            <td align="right">
	                <label class="Validform_label">被通知人: </label>
	            </td>
	            <td class="value">
	                <div id="beNoticeManSelect" style="width: 260px;height: 15px"></div>
	                <input id="beNoticeMan" name="beNoticeMan"  style="width: 200px" type ="hidden" class="inputxt" datatype="*">可多选
	                <span class="Validform_checktip"></span>
	                <label class="Validform_label" style="display: none;">被通知人</label>
	            </td>
	             
	        </tr>
       
         <tr height="250px">
           <td align="right">
                <label class="Validform_label">通知内容: </label>
            </td>
            <td class="value" rowspan="12">
                <textarea id="contentData" name="contentData" style="width: 100%;height: 80%" datatype="*" value=""></textarea>请选择子模块获取内容
                <span class="Validform_checktip"></span>
	            <label class="Validform_label" style="display: none;">通知内容</label>
            </td>
        </tr>
        <tr>
    <td colspan="4">
        <div class="ui_buttons" style="text-align: center;">
            <input type="button" id="send" value="发送" class="ui_state_highlight" onclick="formSubmit()">
            <!-- <input type="button" id="closeBtn" onclick="javaScript:doCloseTab();" value="关闭"> -->
        </div>
    </td>
</tr>
    </table>
</t:formvalid>
</body>
</html>
