<%@ page import="org.jeecgframework.core.enums.SysThemesEnum" %>
<%@ page import="org.jeecgframework.core.util.SysThemesUtil" %>
<%--
  Created by IntelliJ IDEA.
  Author: 张赛超
  Date: 17-7-3
  Time: 上午8:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.jeecgframework.core.util.SysThemesUtil,org.jeecgframework.core.enums.SysThemesEnum"%>
<%@ include file="/context/mytags.jsp"%>
<%
    session.setAttribute("lang","zh-cn");
    SysThemesEnum sysTheme = SysThemesUtil.getSysTheme(request);
    String lhgdialogTheme = SysThemesUtil.getLhgdialogTheme(sysTheme);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>矿版双防机制管理信息系统</title>
    <link rel="shortcut icon" href="images/favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="webpage/login/css/index.css" />
    <script type="text/javascript" src="webpage/login/js/jquery-1.9.1.min.js"></script>
    <t:base type="tools"></t:base>
    <%--弹出框效果，引用自plug-in目录下的JQueryPromptBox--%>
    <link rel="stylesheet" type="text/css" href="plug-in/JQueryPromptBox/css/xcConfirm.css"/>
    <script src="plug-in/JQueryPromptBox/js/xcConfirm.js" type="text/javascript" charset="utf-8"></script>
</head>

<body>
<div class="header"></div>

<div class="context">
    <form id="loinForm" class="form-horizontal"  check="loginController.do?checkuser"  role="form" action="loginController.do?login"  method="post">
        <h1><i></i>用户登录<b></b></h1>
        <div class="userinfo">
            <input type="text" id="userName" name="userName" placeholder="请输入用户名"/>
            <i></i>
        </div>
        <div class="userinfo">
            <input type="password" id="password" name="password" placeholder="请输入密码"/>
            <b></b>
        </div>
        <button type="button" id="but_login" name="subButton" onclick="checkUser()">登录</button>
    </form>
</div>
<div class="foot">技术支持：中国矿业大学 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;产品服务热线：400-156-8180</div>



<script type="text/javascript" src="plug-in/jquery/jquery.cookie.js"></script>
<script type="text/javascript" src="plug-in/mutiLang/en.js"></script>
<script type="text/javascript" src="plug-in/mutiLang/zh-cn.js"></script>
<script type="text/javascript" src="plug-in/login/js/jquery.tipsy.js"></script>


<script type="text/javascript">

    //输入验证码，回车登录
    $(document).keydown(function(e){
        if(e.keyCode == 13) {
            $("#but_login").click();
        }
    });

    //验证用户信息
    function checkUser(){
        if(!validForm()){
            return false;
        }
        var obj= newLogin();
        if(obj.updatePw){
            createwindow('修改密码','userController.do?changepasswordforuser&ids='+obj.id,800,600);
        }else{
            $("#loinForm").submit();
        }
    }
    //表单验证
    function validForm(){
        if($.trim($("#userName").val()).length==0){
//        showErrorMsg("请输入用户名");
            window.wxc.xcConfirm("请输入用户名", window.wxc.xcConfirm.typeEnum.warning);
            return false;
        }

        if($.trim($("#password").val()).length==0){
            //showErrorMsg("请输入密码");
            var txt2=  "请输入密码";
            window.wxc.xcConfirm(txt2, window.wxc.xcConfirm.typeEnum.warning);
            return false;
        }
        return true;
    }

    //登录处理函数
    function newLogin(orgId) {
        var obj=new Object();
        obj.updatePw=false;
        var actionurl=$('form').attr('action');//提交路径
        var checkurl=$('form').attr('check');//验证路径
        var formData = new Object();
        var data=$(":input").each(function() {
            formData[this.name] =$("#"+this.name ).val();
        });
        formData['orgId'] = orgId ? orgId : "";

        $.ajax({
            async : false,
            cache : false,
            type : 'POST',
            url : checkurl,// 请求的action路径
            data : formData,
            error : function() {// 请求失败处理函数
            },
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    if (d.attributes.updatePassword == "true") {
                        obj.id=d.attributes.userid;
                        obj.updatePw=true;
                    }else {
                    if (d.attributes.orgNum > 1) {
                        //用户拥有多个部门，需选择部门进行登录
                        var title, okButton;
                        if($("#langCode").val() == 'en') {
                            title = "Please select Org";
                            okButton = "Ok";
                        } else {
                            title = "请选择组织机构";
                            okButton = "确定";
                        }
                        $.dialog({
                            id: 'LHG1976D',
                            title: title,
                            max: false,
                            min: false,
                            drag: false,
                            resize: false,
                            content: 'url:userController.do?userOrgSelect&userId=' + d.attributes.user.id,
                            lock:true,
                            button : [ {
                                name : okButton,
                                focus : true,
                                callback : function() {
                                    iframe = this.iframe.contentWindow;
                                    var orgId = $('#orgId', iframe.document).val();

                                    formData['orgId'] = orgId ? orgId : "";
                                    $.ajax({
                                        async : false,
                                        cache : false,
                                        type : 'POST',
                                        url : 'loginController.do?changeDefaultOrg',// 请求的action路径
                                        data : formData,
                                        error : function() {// 请求失败处理函数
                                        },
                                        success : function(data) {
                                            window.location.href = actionurl;
                                        }
                                    });

                                    this.close();
                                    return false;
                                }
                            }],
                            close: function(){
                                setTimeout("window.location.href='"+actionurl+"'", 10);
                            }
                        });
                    } else {
                        window.location.href = actionurl;
                    }
                } }else {
                    //showErrorMsg(d.msg);
                    var txt3=  "用户名或密码错误！";
                    window.wxc.xcConfirm(txt3, window.wxc.xcConfirm.typeEnum.error);
                }
            }
        });
        return obj;
    }
</script>

</body>
</html>
