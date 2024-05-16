<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<t:base type="jquery,layui"></t:base>
<style>
    html {
        background-color: #185280;
    }
    .container {
        background-color: #185280;
        height:96vh;
    }
    .layui-row{
        background-color: #185280;
    }


    /* 偶数行背景色 */
    .layui-table[lay-even] tr:nth-child(even) {
        /* background-color: #aaffaa; */
        background-color: #eeffee;
    }

    /* 鼠标指向表格时,奇数行背景颜色 */
    .layui-table tbody tr:hover,.layui-table-hover {
        background-color: #eeffee;
    }

    /* 表格头部工具栏背景色 */
    .layui-table-tool {
        background-color: #185280;
    }

    /* 表格头部背景色 */
    .layui-table th {
        background-color: #185280; /* MediumSeaGreen */
        color: #fff;
        font-weight: 600;
    }
    td {
        background-color: #185280; /* MediumSeaGreen */
        color: #fff;

    }

</style>
<div class="container">
    <iframe id="prePdf" src=""
            width="96%" height="100%"></iframe>
</div>
<script>
    layui.use(['form', 'table','laydate'], function () {
        var $ = layui.jquery;

    });

    window.onload = function() {
        $.get("fileController.do?preFilePdf&id=${fileId}", function (result) {
            var data = JSON.parse(result);
            if (data.success) {
                if(data.obj === null || data.obj === undefined){
                    $("#prePdf").attr("src",'');
                }else{
                    $("#prePdf").attr("src",'<c:url value="/plug-in/pdfjs/web/viewer.html" />?file=/' + data.obj)
                }
            }else{
                layer.msg(data.msg, {icon: 2, time: 1800});
                $("#prePdf").attr("src",'');
            }
        })
    }
    
</script>
