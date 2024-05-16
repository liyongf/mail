<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
 <!DOCTYPE html>
 <html>
 <head>
     <title>培训档案</title>
     <t:base type="jquery,layui"></t:base>
 </head>
 <body style="background-color: #ffffff;">
 <form class="layui-form" action="">
     <input type="hidden" id="id" name="id" value="${sfTrainingInfoPage.id }">
     <div class="layui-form layuimini-form">
         <div class="layui-form-item">
             <label class="layui-form-label required">培训类型</label>
             <div class="layui-input-block">
                 <select id="trainingType" name="trainingType" lay-search="" lay-verify="required">
                     <option value="">直接选择或搜索</option>
                     <c:forEach items="${trainingTypeList}" var="item">
                         <option data="${item.typecode}"
                                 value="${item.typecode}" ${sfTrainingInfoPage.trainingType eq item.typecode ? 'selected': ''}>${item.typename}</option>
                     </c:forEach>
                 </select>
             </div>
         </div>
         <div class="layui-form-item">
             <label class="layui-form-label required">培训日期</label>
             <div class="layui-input-block">
                     <input type="text" id="trainingDate" name="trainingDate" placeholder="请选择" lay-verify="required"
                            value="<fmt:formatDate value='${sfTrainingInfoPage.trainingDate}' type="date" pattern="yyyy-MM-dd"/>" class="layui-input">
             </div>
         </div>
         <div class="layui-form-item">
             <label class="layui-form-label required">培训主题</label>
             <div class="layui-input-block">
                     <input type="text" id="trainingTheme" name="trainingTheme" lay-verify="required" lay-reqtext="培训主题不能为空"
                            placeholder="请输入培训主题" value="${sfTrainingInfoPage.trainingTheme}" autocomplete="off" class="layui-input">
             </div>
         </div>
         <div class="layui-form-item">
             <label class="layui-form-label required">培训人</label>
             <div class="layui-input-block">
                 <select id="trainingPerson" name="trainingPerson" lay-search="" lay-verify="required">
                     <option value="">直接选择或搜索</option>
                     <c:forEach items="${allUser}" var="item">
                         <option data="${item.value}"
                                 value="${item.value}" ${sfTrainingInfoPage.trainingPerson eq item.value ? 'selected': ''}>${item.name}</option>
                     </c:forEach>
                 </select>
<%--                     <input type="text" id="trainingPerson" name="trainingPerson" lay-verify="required" lay-reqtext="培训人不能为空"--%>
<%--                            placeholder="请输入培训人" value="${sfTrainingInfoPage.trainingPerson}" autocomplete="off" class="layui-input">--%>
             </div>
         </div>
         <div class="layui-form-item">
             <label class="layui-form-label required">组织部门</label>
             <div class="layui-input-block">
                 <select id="trainingDepart" name="trainingDepart" lay-search="" lay-verify="required">
                     <option value="">直接选择或搜索</option>
                     <c:forEach items="${allDepart}" var="item">
                         <option data="${item.value}"
                                 value="${item.value}" ${sfTrainingInfoPage.trainingDepart eq item.value ? 'selected': ''}>${item.name}</option>
                     </c:forEach>
                 </select>
<%--                     <input type="text" id="trainingDepart" name="trainingDepart" lay-verify="required" lay-reqtext="组织部门不能为空"--%>
<%--                            placeholder="请输入组织部门" value="${sfTrainingInfoPage.trainingDepart}" autocomplete="off" class="layui-input">--%>
             </div>
         </div>
         <div class="layui-form-item">
             <label class="layui-form-label">
                 <button type="button" class="layui-btn layui-btn-sm layui-btn-normal required" id="fileBtn">添加文件</button>
             </label>
             <div class="layui-input-block">
                 <div class="layui-upload-list">
                     <table class="layui-table">
                         <thead>
                         <tr>
                             <th>文件名</th>
                             <th>状态</th>
                         </tr>
                         </thead>
                         <tbody id="fileTable"></tbody>
                     </table>
                 </div>
             </div>
         </div>
         <input type="hidden" name="fileId" id="fileId" value="${sfTrainingInfoPage.fileId}">
         <input type="hidden" name="fileName" id="fileName" value="${sfTrainingInfoPage.fileName}">
<%--         <div class="layui-form-item">--%>
<%--             <label class="layui-form-label required">文件id</label>--%>
<%--             <div class="layui-input-block">--%>
<%--                     <input type="text" id="fileId" name="fileId" lay-verify="required" lay-reqtext="文件id不能为空"--%>
<%--                            placeholder="请输入文件id" value="${sfTrainingInfoPage.fileId}" autocomplete="off" class="layui-input">--%>
<%--             </div>--%>
<%--         </div>--%>
<%--         <div class="layui-form-item">--%>
<%--             <label class="layui-form-label required">培训课件、签到表、现场图片等记录材料</label>--%>
<%--             <div class="layui-input-block">--%>
<%--                     <input type="text" id="fileName" name="fileName" lay-verify="required" lay-reqtext="培训课件、签到表、现场图片等记录材料不能为空"--%>
<%--                            placeholder="请输入培训课件、签到表、现场图片等记录材料" value="${sfTrainingInfoPage.fileName}" autocomplete="off" class="layui-input">--%>
<%--             </div>--%>
<%--         </div>--%>
         <button id="btn" class="layui-btn" style="display: none" lay-submit lay-filter="saveBtn">立即提交</button>
     </div>
 </form>
 <script>
     $(function () {
         var attachmentFile=$("#fileName").val();
         var fileId=$("#fileId").val();
         if(fileId==""){
             var tr = $(['<tr id="upload-'+ 0 +'">'
                 ,'<td>'+ "" +'</td>'
                 ,'<td></td>'
                 ,'</tr>'].join(''));
             $('#fileTable').html(tr);
         }else {
             var tr = $(['<tr id="upload-'+ 0 +'">'
                 ,'<td>'+ attachmentFile +'</td>'
                 ,'<td><span style="color: #5FB878;">上传成功</span></td>'
                 ,'</tr>'].join(''));
             $('#fileTable').html(tr);
         }

     });

     layui.use(['form', 'laydate','upload'], function () {
         var form = layui.form,
                 layer = layui.layer,
                 laydate = layui.laydate,
             upload=layui.upload,
                 $ = layui.$;

                 laydate.render({
                     elem: '#trainingDate',
                     trigger:'click'
                 });

         //监听提交
         form.on('submit(saveBtn)', function (data) {
             var fileId=$("#fileId").val();
             if (fileId === null||fileId === "") {
                 layer.msg("未上传文件禁止保存！", {icon: 0, time: 1800})
                 return false;
             }
             var index = parent.layer.msg("数据提交中",{icon:16,time:false,shade:0.3});
             $.ajax({
                 url:"sfTrainingInfoController.do?save",
                 type:"post",
                 data:data.field,
                 dataType:"json",
                 async : false,
                 success:function(res){
                     parent.layer.close(index);
                     var iframeIndex = parent.layer.getFrameIndex(window.name);
                     parent.layer.close(iframeIndex);
                     parent.layer.msg(res.msg, {icon: res.success==true?1:2, time: 1800});
                     parent.tools.tableReload();
                 }
             });
             return false;
         });

         var fileTable = $('#fileTable')	,uploadListIns = upload.render({
             elem: '#fileBtn'
             ,url: 'fileController/uploadFile.do' //改成您自己的上传接口
             ,accept: 'file'
             ,acceptMime: '.doc,.docx,.pdf'
             ,exts: 'docx|doc|pdf' //只允许上传excel文件
             ,choose: function(obj){
                 var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                 //读取本地文件
                 obj.preview(function(index, file, result){
                     var tr = $(['<tr id="upload-'+ index +'">'
                         ,'<td>'+ file.name +'</td>'
                         ,'<td>等待上传</td>'
                         ,'</tr>'].join(''));
                     fileTable.html(tr);
                 });
             }
             ,done: function(res, index, upload){
                 if(res.success){ //上传成功
                     var tr = fileTable.find('tr#upload-'+ index),tds = tr.children();
                     tds.eq(1).html('<span style="color: #5FB878;">上传成功</span>');
                     $("#fileId").val(res.obj.id);
                     $("#fileName").val(res.obj.fileName);
                     return delete this.files[index];
                 }
                 this.error(index, upload);
             }
             ,error: function(index, upload){
                 var tr = fileTable.find('tr#upload-'+ index),tds = tr.children();
                 tds.eq(1).html('<span style="color: #FF5722;">上传失败</span>');
                 $("#attachmentId").val("");
             }
         });

     });
 </script>
 </body>
 </html>