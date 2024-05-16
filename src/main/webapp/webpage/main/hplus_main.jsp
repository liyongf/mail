<%--
  Created by IntelliJ IDEA.
  User: wangkun
  Date: 2016/4/23
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp"%>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title><t:mutiLang langKey="双防矿版系统"/></title>

    <meta name="keywords" content="响应式后台">
    <meta name="description" content="基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

    <link rel="shortcut icon" href="images/favicon.ico">
    <link href="plug-in/hplus/css/bootstrap.min.css" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link rel="stylesheet" href="plug-in/ace/assets/css/font-awesome.min.css" />
    <!--[if IE 7]>
    <link rel="stylesheet" href="plug-in/ace/assets/css/font-awesome-ie7.min.css" />
    <![endif]-->
    <!-- Sweet Alert -->
    <link href="plug-in-ui/hplus/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/animate.css" rel="stylesheet">
    <link href="plug-in/hplus/css/style.css" rel="stylesheet">
    <!--右键菜单-->
    <link href="plug-in/hplus/smartMenu.css" rel="stylesheet">
    <style>
        .onoffswitch-switch {
            display: block;
            width: 18px;
            margin: 0px;
            border: none;
            position: absolute;
            top: 0;
            bottom: 0;
            right: 36px;
            -webkit-transition: all 0.3s ease-in 0s;
            transition: all 0.3s ease-in 0s;
        }
        .onoffswitch-inner:before {
            content: "展开";
            padding-left: 7px;
            background-color: #126abd;
            color: #FFFFFF;
            font-size: 12px;
        }
        .onoffswitch-inner:after {
            content: "收起";
            padding-right: 7px;
            background-color: #126abd;
            color: #FFFFFF;
            text-align: right;
            font-size: 12px;
        }
    </style>
</head>

<body class="fixed-sidebar full-height-layout gray-bg fixed-nav skin-1" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation" style="z-index: 1991;">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <t:menu style="hplus" menuFun="${menuMap}"></t:menu>
            </ul>
            <div class="onoffswitch-div" style="position: absolute;left: 15px;bottom:70px;">
                <div class="switch">
                    <div class="onoffswitch">
                        <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
                        <label class="onoffswitch-label" for="collapsemenu" style="border: none;">
                            <span class="onoffswitch-inner"></span>
                            <span class="onoffswitch-switch" style="background: url('plug-in/img/menu-folder-backcolor.png') no-repeat"></span>
                        </label>
                    </div>
                </div>
            </div>
        </div>
        <div style="float:left;width:5px;"></div>
    </nav>

    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-fixed-top" role="navigation" style="margin-bottom: 0;">
                <div class="navbar-header" style="height: 60px;width:850px;">
                   <%-- <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                    <form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                        <div class="form-group">

                        </div>
                    </form>--%>
                </div>

                <ul class="nav navbar-top-links navbar-right">
                    <li class="dropdown hidden-xs" style="border-bottom:0px;">
                        <a class="right-sidebar-toggle" aria-expanded="false">
                            联网:
                            <c:if test="${flag eq false}">
                                <span style="background-color: red">${pingSuccess }</span>
                            </c:if>
                            <c:if test="${flag eq true}">
                                <span style="background-color: green">${pingSuccess }</span>
                            </c:if>
                        </a>
                    </li>
                    <li class="dropdown" style="border-bottom:0px;" onfocus="bindFrameClick()">
                    	<a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                                <span ><strong class="font-bold">${userName }</strong></span>
                                <span >${roleName }<b class="caret"></b></span>
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <li>
                                <a href="javascript:add('<t:mutiLang langKey="common.change.password"/>','userController.do?changepassword','',550,200)">
                                    <t:mutiLang langKey="common.change.password"/>
                                </a>
                            </li>
                            <li><a href="javascript:openwindow('<t:mutiLang langKey="common.profile"/>','userController.do?userinfo')"><t:mutiLang langKey="common.profile"/></a></li>
                            <li><a href="javascript:openwindow('<t:mutiLang langKey="common.ssms.getSysInfos"/>','tSSmsController.do?getSysInfos')"><t:mutiLang langKey="common.ssms.getSysInfos"/></a></li>
                          <%--  <li><a href="javascript:add('<t:mutiLang langKey="common.change.style"/>','userController.do?changestyle','',550,250)"><t:mutiLang langKey="common.my.style"/></a></li>--%>
                            <li><a href="javascript:clearLocalstorage()"><t:mutiLang langKey="common.clear.localstorage"/></a></li>

                            <!-- <li><a href="http://yun.jeecg.org" target="_blank">云应用中心</li> -->
                           <!--  <li class="divider"></li>
                            <li><a href="javascript:logout()">注销</a></li> -->
                        </ul>
                    </li>
                    <li class="dropdown" style="border-bottom:0px;">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-bell"></i><span id="sumTasks" class="label label-primary" style="background-color: #e42c2c;top: 10px;right: 65px;">0</span>待办任务<b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <li style="margin-right: 0;">
                                <a href="javascript:addOneTab('隐患整改','tBHiddenDangerHandleController.do?checkList','default');" onclick="window.parent.moduleClick('yhpczlLi')">
                                    隐患闭环待整改：<span id="sumDZG" style="float: right;">0&nbsp;条</span>
                                </a>
                            </li>
                            <li style="margin-right: 0;">
                                <a href="javascript:addOneTab('隐患复查','tBHiddenDangerHandleController.do?repartList','default');" onclick="window.parent.moduleClick('yhpczlLi')">
                                    隐患闭环待复查：<span id="sumDFC" style="float: right;">0&nbsp;条</span>
                                </a>
                            </li>
                            <c:if test="${lilou eq true}">
                                <li style="margin-right: 0;">
                                    <a href="javascript:addOneTab('隐患延期','tBHiddenDangerHandleController.do?applyDelayList','default');" onclick="window.parent.moduleClick('yhpczlLi')">
                                        隐患延期申请：<span id="sumYQSQ" style="float: right;">0&nbsp;条</span>
                                    </a>
                                </li>
                                <li style="margin-right: 0;">
                                    <a href="javascript:addOneTab('隐患整改','tBHiddenDangerHandleController.do?checkList','default');" onclick="window.parent.moduleClick('yhpczlLi')">
                                        隐患不通过：<span id="sumBTG" style="float: right;">0&nbsp;条</span>
                                    </a>
                                </li>
                                <li style="margin-right: 0;">
                                    <a href="javascript:addOneTab('已录隐患列表','tBHiddenDangerExamController.do?newList','default');" onclick="window.parent.moduleClick('yhpczlLi')">
                                        隐患录入驳回：<span id="sumLRBH" style="float: right;">0&nbsp;条</span>
                                    </a>
                                </li>
                            </c:if>
                            <li class="divider"></li>
                           <%-- <li style="margin-right: 0;">
                                <a href="javascript:addOneTab('隐患整改列表','tBMajorHiddenDangerController.do?rectifyList','default');" onclick="window.parent.moduleClick('yhpczlLi')">
                                    重大隐患待整改：<span id="sumMDZG" style="float: right;">0条</span>
                                </a>
                            </li>
                            &lt;%&ndash;<li style="margin-right: 0;">
                                <a href="javascript:addOneTab('隐患验收列表','tBMajorHiddenDangerController.do?pendingAcceptList','default');" onclick="window.parent.moduleClick('yhpczlLi')">
                                    重大隐患待验收：<span id="sumMDYS" style="float: right;">0条</span>
                                </a>
                            </li>&ndash;%&gt;
                            <li style="margin-right: 0;">
                                <a href="javascript:addOneTab('隐患复查列表','tBMajorHiddenDangerController.do?pendingReviewList','default');" onclick="window.parent.moduleClick('yhpczlLi')">
                                    重大隐患待复查：<span id="sumMDFC" style="float: right;">0条</span>
                                </a>
                            </li>--%>
                        </ul>
                    </li>
                    <%--<li class="dropdown hidden-xs" style="border-bottom:0px;">
                        <a class="right-sidebar-toggle" aria-expanded="false">
                            <i class="fa fa-tasks"></i> 主题
                        </a>
                    </li>--%>
                      <li class="dropdown" style="border-bottom:0px;">
                     <a href="javascript:logout()" class="roll-nav roll-right"><i class="fa fa fa-sign-out"></i> 退出</a>
                     </li>
                </ul>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="loginController.do?hplushome">首页</a>
                </div>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            
            <!-- 
            <a href="javascript:logout()" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
             -->
        </div>
        <div class="row J_mainContent" id="content-main" style="margin-left:-13px;">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="loginController.do?hplushome" frameborder="0" data-id="loginController.do?hplushome" seamless></iframe>
        </div>
        <div class="footer">
            <div class="pull-right">&copy; <a href="#" target="_blank"></a>
        </div>
        </div>
    </div>
</div>

<!-- 全局js -->
<script src="plug-in-ui/hplus/js/jquery.min.js?v=2.1.4"></script>
<script src="plug-in-ui/hplus/js/bootstrap.min.js?v=3.3.6"></script>
<script src="plug-in-ui/hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="plug-in-ui/hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>

<!-- 自定义js -->
<script src="plug-in/hplus.js"></script>
<!--右键菜单-->
<script type="text/javascript" src="plug-in/hplus/jquery-smartMenu.js"></script>
<script type="text/javascript" src="plug-in/hplus/contabs.js"></script>
<t:base type="tools"></t:base>
<!-- 第三方插件 -->
<script src="plug-in-ui/hplus/js/plugins/pace/pace.min.js"></script>
<!-- Sweet alert -->
<script src="plug-in-ui/hplus/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="plug-in/jquery-plugs/storage/jquery.storageapi.min.js"></script>

<!-- 弹出TAB -->
<script type="text/javascript" src="plug-in/hplus/hplus-tab.js"></script>
<script>
    var ref = window.setInterval("checkTaskList()",1000*60*5);

    function logout(){
        layer.confirm('您确定要注销吗？', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(){
            location.href="loginController.do?logout";
        }, function(){
            return;
        });
    }
    function clearLocalstorage(){
        var storage=$.localStorage;
        if(!storage)
            storage=$.cookieStorage;
        storage.removeAll();
        layer.msg("浏览器缓存清除成功!");
    }
    function moduleClick(moduleId){
        $("#"+moduleId+" a").eq(0).trigger("click");
    }
    $(document).ready(function(){
        checkTaskList();
    });

    function checkTaskList(){
        var total = 0;
        $.ajax({
            url:"tBHiddenDangerHandleController.do?checkTask",
            type:"GET",
            dataType:"JSON",
            async: false,
            success:function(data){
                if(data.success){
                    var dzg = data.obj.dzg;
                    var dfc = data.obj.dfc;
                    var lilou = data.obj.lilou;
                    if(lilou==true){
                        var yqsq =data.obj.yqsq;
                        var btg = data.obj.btg;
                        var lrbh = data.obj.lrbh;
                        total = dzg+dfc+yqsq+btg+lrbh;
                        $("#sumYQSQ").empty();
                        $("#sumYQSQ").append(yqsq+"&nbsp;条");
                        $("#sumBTG").empty();
                        $("#sumBTG").append(btg+"&nbsp;条");
                        $("#sumLRBH").empty();
                        $("#sumLRBH").append(lrbh+"&nbsp;条");
                    }else {
                        total = dzg+dfc;
                    }


                    $("#sumDZG").empty();
                    $("#sumDZG").append(dzg+"&nbsp;条");
                    $("#sumDFC").empty();
                    $("#sumDFC").append(dfc+"&nbsp;条");

                    $("#sumTasks").empty();
                    $("#sumTasks").append(total);
                }
            }
        });

        $.ajax({
            url:"tBMajorHiddenDangerController.do?checkTask",
            type:"GET",
            dataType:"JSON",
            async: false,
            success:function(data){
                if(data.success){
                    var dzg = data.obj.dzg;
                    var dfc = data.obj.dfc;
                    total += dzg+dfc;
                    $("#sumMDZG").empty();
                    $("#sumMDZG").append(dzg+"&nbsp;条");
                    $("#sumMDFC").empty();
                    $("#sumMDFC").append(dfc+"&nbsp;条");
                   /* $("#sumTasks").empty();
                    $("#sumTasks").append(total);*/
                }
            }
        });
    }

    function goAllNotice(){
        var addurl = "noticeController.do?noticeList";
        createdetailwindow("公告", addurl, 800, 400);
    }

    function goNotice(id){
        var addurl = "noticeController.do?goNotice&id="+id;
        createdetailwindow("通知公告详情", addurl, 750, 600);
    }

    function goAllMessage(){
        var addurl = "tSSmsController.do?getSysInfos";
        createdetailwindow("通知", addurl, 800, 400);
    }

    function goMessage(id){
        var title = $("#"+id+"_title").val();
        var content = $("#"+id+"_content").val();
        $("#msgId").val(id);
        $("#msgTitle").html(title);
        $("#msgContent").html(content);
        var status = $("#"+id+"_status").val();
        if(status==1){
            $("#msgStatus").html("未读");
        }else{
            $("#msgStatus").html("已读");
        }

        $('.theme-popover-mask').fadeIn(100);
        $('.theme-popover').slideDown(200);
    }

    function readMessage(){
        var msgId = $("#msgId").val();
        var url = "tSSmsController.do?readMessage";
        $.ajax({
            url:url,
            type:"GET",
            dataType:"JSON",
            data:{
                messageId:msgId
            },
            success:function(data){
                if(data.success){
                    $("#msgStatus").html("已读");
                    $("#"+msgId+"_status").val('2');
                }
            }
        });
    }

    //个人信息弹出层回缩
    function frameBodyClick(){ 
		$(".count-info").attr("aria-expanded","false").parent().removeClass("open");
	}
    //新增iframe中绑定click事件回调父级函数
    function bindFrameClick(){
    	$(".J_iframe").contents().find("body").attr("onclick", "parent.frameBodyClick()"); 
    }

</script>
</body>

</html>
