<%--
  Created by zhangsaichao
  User: Lenovo
  Date: 2018/3/23
  Time: 13:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker,autocomplete"></t:base>


<html>
<head>
    <script src="plug-in/ace/js/bootstrap.js"></script>
    <link href="plug-in/ace/css/bootstrap.css" rel="stylesheet">
    <title>隐藏数据页面</title>
</head>
<body>


<div class="container-fluid" style="height: 90%; position: relative; top: 80px; ">
    <div class="row-fluid">
        <%--=================================================================--%>
        <div id="riskDIv" style="display: none; ">
            <div class="col-xs-12 col-md-1 col-lg-1" style="height: 30%;"></div>
            <div id="hide_riskDIv" class="col-xs-12 col-md-10 col-lg-10" title="隐藏风险数据" style="height: 30%; text-align: center; display: flex;align-items: center;justify-content: center;  background-color: #f7f7f9;">
                <div style="position: absolute; top: 40%; margin-top: -65px; ">
                    <h4>隐藏所有所选级别的风险</h4>
                </div>
                <%--<div class="checkbox"><label><input type="checkbox" value=""> Check me out</label></div>--%>
                <%--<div style="position: absolute; bottom: 40%; margin-bottom: -65px; ">--%>
                    <%--<button type="button" class="btn btn-success" style="margin-right: 30px; ">隐藏对应的数据</button>--%>
                    <%--<button type="button" class="btn btn-warning">恢复隐藏的数据</button>--%>
                <%--</div>--%>
            </div>
            <div class="col-xs-12 col-md-1 col-lg-1" style="height: 30%;"></div>
            <div style="clear: both; "><%--这个div没用，只是用来撑开父div--%></div>
        </div>
        <%--=================================================================--%>
        <div id="hiddenDiv" style="display: none; ">
            <div class="col-xs-12 col-md-1 col-lg-1" style="height: 30%;"></div>
            <div id="hide_hiddenDiv" class="col-xs-12 col-md-10 col-lg-10" title="隐藏隐患数据" style="height: 30%; text-align: center; display: flex;align-items: center;justify-content: center;  background-color: #f7f7f9; margin-top: 10px; ">
                <div style="position: absolute; top: 40%; margin-top: -65px; ">
                    <h4>隐藏所有所选级别的隐患</h4>
                </div>
                <%--<div class="checkbox"><label><input type="checkbox" value=""> Check me out</label></div>--%>
                <%--<div style="position: absolute; bottom: 40%; margin-bottom: -65px; ">--%>
                    <%--<button type="button" class="btn btn-success" style="margin-right: 30px; " onclick="hiddenDangerByLevel()">隐藏对应的数据</button>--%>
                    <%--<button type="button" class="btn btn-warning" onclick="recoveHiddenDanger()">恢复隐藏的数据</button>--%>
                <%--</div>--%>
            </div>
            <div class="col-xs-12 col-md-1 col-lg-1" style="height: 30%;"></div>
            <div style="clear: both; "><%--这个div没用，只是用来撑开父div--%></div>
        </div>
        <%--=================================================================--%>
        <div id="threeVioDiv" style="display: none; ">
            <div class="col-xs-12 col-md-1 col-lg-1" style="height: 30%;"></div>
            <div id="hide_threeVioDiv" class="col-xs-12 col-md-10 col-lg-10" title="隐藏三违数据" style="height: 30%; text-align: center; display: flex;align-items: center;justify-content: center;  background-color: #f7f7f9; margin-top: 10px; ">
                <div style="position: absolute; top: 40%; margin-top: -65px; ">
                    <h4>隐藏所有所选级别的三违</h4>
                </div>
                <%--<div class="checkbox"><label><input type="checkbox" value=""> Check me out</label></div>--%>
                <%--<div style="position: absolute; bottom: 40%; margin-bottom: -65px; ">--%>
                    <%--<button type="button" class="btn btn-success" style="margin-right: 30px; ">隐藏对应的数据</button>--%>
                    <%--<button type="button" class="btn btn-warning">恢复隐藏的数据</button>--%>
                <%--</div>--%>
            </div>
            <div class="col-xs-12 col-md-1 col-lg-1" style="height: 30%;"></div>
            <div style="clear: both; "><%--这个div没用，只是用来撑开父div--%></div>
        </div>
        <%--=================================================================--%>
    </div>
</div>


</body>
</html>

<script>
    /**载入——初始化页面*/
    $(function () {
        var ja = ${ja};
        var hideDivNum = 0;

        for (var i=0; i<ja.length; i++){
            var title = ja[i].title;
            var isShow = ja[i].isShow;
            var divId = ja[i].divId;

            /**
             *  isShow 为 0，代表隐藏，不显示；isShow 为 1，代表显示；
             *  同时，如果div被隐藏了，那么它需要填满其他div空出来的位置
             */
            if (isShow == "0"){
                $("#"+divId).css("display", "none");
                hideDivNum ++;
            }else{
                $("#"+divId).css("display", "block");
            }

        }

        //算出来隐藏了多少个div，剩下的div如果需要填充满页面它们的高度为多少(%)
        var eachDIvHeight = 90;
        if (3-hideDivNum != 0){
            eachDIvHeight = 90 / (3-hideDivNum);
        }
        //重新设置div的高度
        $("div .col-xs-12").height(eachDIvHeight+"%");

        /**绘制 hiddenLevel 的 checkbox */
        drawHiddenLevelList();
        /**绘制 threeVioLevel 的 checkbox */
        drawThreeVioLevelList();
        /**绘制 riskLevel 的 checkbox */
        drawRiskLevelList();
    })

    /**
     * 绘制hiddenLevel的 checkbox
     * */
    function drawHiddenLevelList(){
        drawList(${hiddenLevelList}, "hiddenDiv", "hiddenLevel", "hideHiddenDangerByLevel");
    }

    /**
     * 绘制threeVioLevel的 checkbox
     * */
    function drawThreeVioLevelList(){
        drawList(${threeVioLevelList}, "threeVioDiv", "threeVioLevel", "hideThreeVioByLevel");
    }

    /**
     * 绘制riskLevel的 checkbox
     * */
    function drawRiskLevelList(){
        drawList(${riskLevelList}, "riskDIv", "riskLevel", "hideRiskByLevel");
    }

    /**
     *  用 bootstrap 的 checkbox 样式列出所有的隐患等级的复选框
     * */
    function drawList(hiddenLevelList, divName, inputName, functionName){

        for(var i=0; i<hiddenLevelList.length; i++){
            var oneCheckbox = generateOneCheckbox(hiddenLevelList[i], inputName, functionName);
            $("#hide_"+divName).append(oneCheckbox);
        }
        //第一个元素的位置出现了偏差，调整回来
        $("#"+divName).find("div .checkbox").eq(0).css("margin-top", "-6px");
    }

    /**生成一个  checkbox*/
    function generateOneCheckbox(obj, inputName, functionName){
        var checked = "";
        if (obj.isHide == 1) {
            checked = " checked ";
        }
        var ret = '<div class="checkbox" style="float: left; margin-right: 20px; "><label><input id="'+obj.id+'" type="checkbox" name="'+inputName+'" value="'+obj.typecode+'" '+checked+' onclick="'+functionName+'(this)"> '+obj.typename+'</label></div>';
        return ret;
    }

    /**隐藏选中等级的所有隐患*/
    function hideHiddenDangerByLevel(obj){
        hideByLevel(obj, "tBHideDataController.do?hideHiddenDangerByLevel");
    }

    /**隐藏选中等级的所有三违*/
    function hideThreeVioByLevel(obj){
        hideByLevel(obj, "tBHideDataController.do?hideThreeVioByLevel");
    }

    /**隐藏选中等级的所有风险*/
    function hideRiskByLevel(obj){
        hideByLevel(obj, "tBHideDataController.do?hideRiskByLevel");
    }

    /**隐藏选中等级的数据*/
    function hideByLevel(obj, url){
        var id = $(obj).attr("id");
        var isHide = 0;
        var name = $(obj).text();
        var msg = name + "数据已取消隐藏";
        if ($(obj).attr("checked") == "checked") {
            isHide = 1;
            msg = name + "数据隐藏成功";
        }
        $.ajax({
            type:"post",
            url:url,
            dataType:"json",//设置需要返回的数据类型
            data:{id:id, isHide: isHide},
            success:function(result){
                if (result.success) {
                    tip(msg);
                } else {
                    tip("设置失败");
                    if (isHide == 1){
                        $(obj).prop("checked",false);
                    } else {
                        $(obj).prop("checked",true);
                    }
                }
            },
            error:function(){
                tip("设置失败");
                if (isHide == 1){
                    $(obj).prop("checked",false);
                } else {
                    $(obj).prop("checked",true);
                }
            }
        });
    }

</script>

