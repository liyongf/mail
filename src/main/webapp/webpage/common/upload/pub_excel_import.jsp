<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>通用Excel导入${controller_name}</title>
<t:base type="jquery,easyui,tools"></t:base>
 <script type="text/javascript">
 $(function(){
	 $("#validatediv").hide();
 });
 	function uploadSuccess(d,file,response){
 		var win = frameElement.api.opener;
 		if(d.success){
 			$("#validatediv").hide();
 			win.reloadTable();
 	 		win.tip(d.msg);
 	 		frameElement.api.close();
 		}else{	
 			win.tip(d.msg);
 			$("#validAId").attr("href",d.attributes.path);
 			$("#validatediv").show();;
 		}
 		
 	}
 	//上传成功后的回调
 	function queueCompleteCall(){ 
 		//var win = frameElement.api.opener;win.reloadTable();
 		//win.tip(serverMsg);
 		//frameElement.api.close();
 		};
  </script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" beforeSubmit="upload">
	<fieldset class="step">
	<div class="form"><t:upload name="fiels" dialog="false" callback="queueCompleteCall" onUploadSuccess="uploadSuccess" buttonText="选择要导入的文件" uploader="${controller_name}.do;jsessionid=${pageContext.session.id}?${importfun_name}" extend="*.xls;*.xlsx" id="file_upload" formData="documentTitle"></t:upload></div>
	<div class="form" id="filediv" style="height: 50px"></div>
	<div class="form" id="validatediv" style="height: 50px">
	<div class="uploadify-queue-item" id="SWFUpload_0_2">
	<a id="validAId" href="#"><span class="fileName" style="color:red;">数据校验文件下载</span></a>
	</div>
	</div>
	</fieldset>
</t:formvalid>
</body>
</html>
