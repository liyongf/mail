<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!--360浏览器优先以webkit内核解析-->


    <title></title>

    <link rel="shortcut icon" href="images/favicon.ico">
    <link href="plug-in-ui/hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="plug-in-ui/hplus/css/animate.css" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/style.css?v=4.1.0" rel="stylesheet">
    <style type="text/css">
        /* 头部logo 开始  */
        body,html{height: 100%;width: 100%;}

        .main{width: 100%;height: 100%;clear: both;position: relative;overflow: auto;min-width: 1320px}
        .main .menu_tree{width: 291px;height: 100%;background-color: pink;position: absolute;left: 0px;top: 0px}
        .main .m_context{width: 100%;height: 100%;padding-left: 0px;background-color: #D0E1F8;}
        .main .m_context .menu_nav{height: 63px;background-color: green;}
        /* 模块代码开始  */
        .main .m_context .model_menu{padding-top: 90px;background-color:#D0E1F8;padding-left: 150px;}
        .main .m_context .model_menu a{width: 21%;height:250px;border-radius: 10px;display: inline-block;font-size: 28px;font-weight: bold;text-align: center;color:#fff;line-height: 250px;margin-right: 3%;margin-bottom: 65px;box-shadow: 4px 5px 5px rgba(108, 122, 135, 0.9);position: relative;overflow: hidden;}
        .main .m_context .model_menu a img{width:70%;position: absolute;}
        .main .m_context .model_menu .m1{border:1px solid #0A6DBA; background: linear-gradient(to bottom,#3cb5e1,#0a6dba);}
        .main .m_context .model_menu .m1 img{right: -30px;bottom:-10px}
        .main .m_context .model_menu .m2{border:1px solid #ED9930; background: linear-gradient(to bottom,#f6c76e,#ea8821);}
        .main .m_context .model_menu .m2 img{right: -20px;bottom:-60px}
        .main .m_context .model_menu .m3{border:1px solid #10914E; background: linear-gradient(to bottom,#51CDA0,#10914E);}
        .main .m_context .model_menu .m3 img{right: -10px;bottom:-60px;width: 60%;}
        .main .m_context .model_menu .m4{border:1px solid #C03C55; background: linear-gradient(to bottom,#E2879E,#BC354E);}
        .main .m_context .model_menu .m4 img{right: -30px;bottom:-60px;width: 60%;}
        .main .m_context .model_menu .m5{border:1px solid #A95AD6; background: linear-gradient(to bottom,#D5A3EC,#A353D3);}
        .main .m_context .model_menu .m5 img{right: -30px;bottom:-20px;width: 60%;}
        .main .m_context .model_menu .m6{border:1px solid #598CE3; background: linear-gradient(to bottom,#A3C5F3,#5285E1);}
        .main .m_context .model_menu .m6 img{right: -30px;bottom:-20px;width: 60%;}
        /* 模块代码结束  */
        .m_context h1{background: url('plug-in/hplus/images/331.png') no-repeat;height:347px;margin-top: -250px}
    </style>

</head>

<body class="gray-bg">


<!-- 全局js -->
<script src="plug-in-ui/hplus/js/jquery.min.js?v=2.1.4"></script>
<script src="plug-in/hplus/js/bootstrap.min.js?v=3.3.6"></script>
<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>

<!-- 自定义js -->
<script src="plug-in-ui/hplus/js/content.js"></script>

<div class="main">
    <div class="m_context">
        <!-- 模块开始  -->
        <div class="model_menu">
            <p>
                <a href="javascript:void(0)" onclick="window.parent.moduleClick('aqfxfjgkLi')" class = "m1">
                    安全风险分级管控
                    <img src="plug-in/hplus/images/model1.png"/>
                </a>

                <a href="javascript:void(0)" onclick="window.parent.moduleClick('yhpczlLi')" class = "m2">
                    隐患排查治理
                    <img src="plug-in/hplus/images/model2.png"/>
                </a>
                <a href="javascript:void(0)" onclick="window.parent.moduleClick('aqbzhkhLi')" class = "m3">
                    安全标准化考核
                    <img src="plug-in/hplus/images/model3.png"/>
                </a>
            </p>
            <p>
                <a href="javascript:void(0)" onclick="window.parent.moduleClick('aqjcfxLi')" class = "m4">
                    安全决策分析
                    <img src="plug-in/hplus/images/model4.png"/>
                </a>
                <a href="javascript:void(0)" onclick="window.parent.moduleClick('fzglLi')" class = "m5">
                    辅助管理
                    <img src="plug-in/hplus/images/model5.png"/>
                </a>
                <a href="javascript:void(0)" onclick="window.parent.moduleClick('xtjkLi')" class = "m6">
                    系统监控
                    <img src="plug-in/hplus/images/model6.png"/>
                </a>
            </p>
        </div>
        <!-- 模块结束  -->
        <h1></h1>
    </div>
</div>
</body>

</html>
