<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp" %>
<!DOCTYPE html>
<t:base type="jquery,layui"></t:base>
<style>
    .tip1{
        width: 90px;!important;
    }

    .tip2 {
        margin-left: 120px;!important;
    }
</style>
<body style="background-color: #ffffff;">
<div class="layuimini-container" style="border: 0px">
    <div class="layuimini-main">
        <form class="layui-form" action="" id="form" lay-filter="example">
            <input type="hidden" name="id" value="${sfReportInfoPage.id}">
            <input type="hidden" name="graphicType" value="1">
            <div class="layui-form-item">
                <label class="layui-form-label tip1">报告类型<i class="red-point">*</i></label>
                <div class="layui-input-block tip2">
                    <select id="reportType" name="reportType" lay-search="" lay-verify="required" lay-reqtext="请选择报告类型">
                        <option value="">直接选择或搜索</option>
                        <c:forEach items="${fileTypeList}" var="item">
                            <option data="${item.typecode}"
                                    value="${item.typecode}" ${sfReportInfoPage.reportType eq item.typecode ? 'selected': ''}>${item.typename}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label tip1">报告名称<i class="red-point">*</i></label>
                <div class="layui-input-block tip2">
                    <input type="text" id="reportName" name="reportName" lay-verify="required" lay-reqtext="请输入报告名称" value="${sfReportInfoPage.reportName}" class="layui-input" />
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label tip1">形成日期<i class="red-point">*</i></label>
                <div class="layui-input-block tip2">
                    <input type="text" id="upDate" name="upDate" placeholder="请选择" lay-verify="required" lay-reqtext="请选择形成日期"
                           value="<fmt:formatDate value='${sfReportInfoPage.upDate}' type="date" pattern="yyyy-MM-dd"/>" class="layui-input" />
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
            <input type="hidden" name="fileId" id="fileId" value="${sfReportInfoPage.fileId}">
            <input type="hidden" name="fileName" id="fileName" value="${sfReportInfoPage.fileName}">
            <button id="btn" class="layui-btn" style="display: none" lay-submit lay-filter="sub" id="sub">立即提交</button>
        </form>
    </div>
</div>

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
    layui.use(['form', 'layedit', 'laydate',"upload"], function () {
        var form = layui.form,
            layer = layui.layer,
            upload = layui.upload,
            laydate = layui.laydate,
            $ = layui.$;

        laydate.render({
            elem: '#upDate',
            trigger:'click'
        });

        var fileTable = $('#fileTable')	,uploadListIns = upload.render({
            elem: '#fileBtn'
            ,url: 'sFReportInfoController.do?uploadFile' //改成您自己的上传接口
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
                    $("#fileName").val(res.obj.name);
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

        //监听提交
        form.on('submit(sub)', function (data) {
            $.ajax({
                async : false,
                type : 'POST',
                data : data.field,
                url : 'sFReportInfoController.do?save',// 请求的action路径
                success : function(data) {
                    var d = $.parseJSON(data);
                    var index = parent.layer.getFrameIndex(window.name); //获取当前窗口的name
                    parent.layer.close(index);
                    parent.layer.msg(d.msg, {icon: d.success==true?1:2, time: 1800});
                    parent.tools.tableReload();
                }
            });
            return false;
        });

    });



    $(document).on("mouseenter mouseleave", ".file-iteme", function (event) {
        if (event.type === "mouseenter") {
            //鼠标悬浮
            $(this).children(".info").fadeIn("fast");
            $(this).children(".handle").fadeIn("fast");
        } else if (event.type === "mouseleave") {
            //鼠标离开
            $(this).children(".info").hide();
            $(this).children(".handle").hide();
        }
    });

    $(document).on("click", ".file-iteme .handle", function (event) {
        var id = $(this).attr("id")
        var that_ = this;
        // var data = JSON.parse(result);
        var imgs = $("#fileId").val();
        // 先替换中间的
        imgs = imgs.replace("," + id + ",", '').replace(",,",',');
        imgs = imgs.replace(id,'');
        // 去掉前后的,
        imgs = imgs.replace(/^,+/,"").replace(/,+$/,"");
        $("#fileId").val(imgs)
        $(that_).parent().remove();
        $("#fileName").val('')
    });
</script>

</body>
</html>