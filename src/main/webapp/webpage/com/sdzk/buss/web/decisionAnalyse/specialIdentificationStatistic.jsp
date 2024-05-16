<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/Highcharts-5.0.11/code/highcharts.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-3d.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/modules/exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/modules/offline-exporting.js"></script>
<script src="plug-in/Highcharts-5.0.11/code/highcharts-zh_CN.js"></script>
<style media="print">
    <%--隐藏页眉页脚--%>
    @page {
        size: auto;  /* auto is the initial value */
        margin: 0mm; /* this affects the margin in the printer settings */
    }
    <%--隐藏无关的元素--%>
    #abc {display:none}
</style>

<table style="width: 100%;height: auto;margin-top:25px;">
    <tr>
        <td colspan="2" style="text-align: center">
            <form id="thisForm" action="statisticalAnalysisController.do?trendChange&type=month" method="post">
                开始时间:&nbsp;&nbsp;<input id="yearMonthStart" name="yearMonthStart" value="${yearMonthStart}">~
                结束时间:&nbsp;&nbsp;<input id="yearMonthEnd" name="yearMonthEnd" value="${yearMonthEnd}">
                &nbsp;&nbsp;&nbsp;&nbsp;
                <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="doQuery()" id="">查询</a>
            </form>
        </td>
    </tr>
    <tr>
        <td style="width: 90%">
            <div id="container" style="width: 90%; height: 400px; margin: 0 auto"></div>
        </td>
    </tr>
    <tr>
        <td>
            <table id="jeecgEasyUIList" >
                <thead>
                <tr>
                    <th field="address" width="30">风险点</th>
                    <th field="riskType" width="30">风险类型</th>
                    <th field="riskDesc" width="45">风险描述</th>
                    <th field="riskLevel" width="30">风险等级</th>
                    <th field="hazardFactortsNum" width="30">危害因素</th>
                    <th field="manageLevel" width="30">最高管控层级</th>
                    <th field="dutyManager" width="30">最高管控责任人</th>
                    <th field="identifiDate" width="30">评估日期</th>
                    <th field="expDate" width="30">解除日期</th>
                    <th field="specificType" width="30">专项辨识类型</th>
                    <th field="specificName" width="30">专项辨识风险名称</th>
                </tr>
                </thead>
            </table>
        </td>
    </tr>
</table>

<script type="text/javascript">
    function valueTitle(value){
        return "<a title=\""+value+"\">"+value+"</a>";
    }
    var type ="";
    function doQuery(){
        //  $("#thisForm").submit();
        specialEval(specialPieSeries);
         refreshGrid(type);
    }
    var specialPieSeries;
    var specialChart;
    $(function () {
        $("#yearMonthStart").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("#yearMonthEnd").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
        $("div[class='panel-header']").hide();
        $("#columnListtb").hide();

        var timeStart =new Date();
        var yearMonthBegin = getFirstDayOfYear(timeStart);
        $("#yearMonthStart").val(yearMonthBegin);

        var timeEnd = new Date();
        var yearMonthStop = timeFormat(timeEnd);
        $("#yearMonthEnd").val(yearMonthStop);


        specialChart = new Highcharts.Chart({
            chart: {
                renderTo: 'container',
                type: 'pie',
                options3d: {
                    enabled: true,
                    alpha: 45,
                    beta: 0
                } ,
                events: {
                    load: function() {
                        specialPieSeries = this.series[0];
                        setInterval(function(){specialEval(specialPieSeries);}, 300000);
                    }
                }
            },
            credits: {
                enabled:false
            },
            title: {
                text: '<b>专项辨识分布</b>'
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.point.name +'</b>: '+ this.percentage.toFixed(2) +'%'+" （数量："+this.point.y+"）";
                }
            },
            plotOptions: {

                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    depth: 50,
                    dataLabels: {
                        enabled: true,
                        formatter: function() {
                            return/* '<b>'+ this.point.name +'</b>: '+*/ this.percentage.toFixed(2) +' %';
                        }
                    },
                    showInLegend:true
                }
            },
            series: [{
                type: 'pie',
                name: 'Browser share',
                data: [
                ],
                events: {
                    click: function(e) {
                        var pointName = e.point.name;
                        var ct = changeClickType(pointName);
                        refreshGrid(ct);

                        if(pointName == "暂无数据"){
                            alert(pointName);
                        }else{
                            /*   $.dialog({id:'info',title:e.point.name+'问题隐患信息',content: 'url:tBHiddenDangerHandleController.do?list&pro_type='+e.point.id+"&time=last",cancelVal: '关闭',cancel: true,width: '800px',height: 650 });*/
                        }

                    }
                }
            }]
        });
        specialEval(specialPieSeries);
        refreshGrid(type);
    })
    //日期格式化，返回值形式为yy-mm-dd
    function timeFormat(date) {
        if (!date || typeof(date) === "string") {
            this.error("参数异常，请检查...");
        }
        var y = date.getFullYear(); //年
        var m = date.getMonth() + 1; //月
        var d = date.getDate(); //日
        if (m < 10) {
            m = "0" + m;
        }
        if (d < 10) {
            d = "0" + d;
        }
        return y + "-" + m + "-" + d;
    }
    //获取当年第一天
    function getFirstDayOfYear (date) {
        date.setDate(1);
        date.setMonth(0);
        return timeFormat(date);
    }

    function specialEval(series){
        var yearMonthStartTemp = $("#yearMonthStart").val();
        var yearMonthEndTemp = $("#yearMonthEnd").val();
        $.ajax({
            type:"post",
            url:"tBDecisionAnalyseController.do?specialByEvalType",
            data:{yearMonthStart:yearMonthStartTemp,yearMonthEnd:yearMonthEndTemp},
            dataType:"json",//设置需要返回的数据类型
            success:function(data){
                series.setData(data.pieData.pieData);
            },
            error:function(){
            }
        });
    }

    // 设置datagrid属性
    $('#jeecgEasyUIList').datagrid({
        title: '专项辨识列表',
        idField: 'id',
        fit:false,
        loadMsg: '数据加载中...',
        pageSize: 10,
        pagination:true,
        sortOrder:'asc',
        rownumbers:true,
        singleSelect:true,
        fitColumns:true,
        showFooter:true,
        url:'tBDecisionAnalyseController.do?specialidetificationDatagrid&yearMonthStart=${yearMonthStart}&yearMonthEnd=${yearMonthEnd}',
        toolbar: '#tb2',
        onBeforeRefresh:function(pageNumber, pageSize){
            $(this).pagination('loading');
            $(this).pagination('loaded');
        },
        loadFilter: function(data){
            return getData(data);
        }
    });

    function refreshGrid(clickType){
        var yearMonthStart = $("#yearMonthStart").val();
        var yearMonthEnd = $("#yearMonthEnd").val();
        $('#jeecgEasyUIList').datagrid({
            title: '专项辨识列表',
            idField: 'id',
            fit:false,
            loadMsg: '数据加载中...',
            pageSize: 10,
            pagination:true,
            sortOrder:'asc',
            rownumbers:true,
            singleSelect:true,
            fitColumns:true,
            showFooter:true,
            url:'tBDecisionAnalyseController.do?specialidetificationDatagrid&yearMonthStart='+yearMonthStart+"&yearMonthEnd="+yearMonthEnd+"&clickType="+clickType,
            toolbar: '#tb2',
            onBeforeRefresh:function(pageNumber, pageSize){
                $(this).pagination('loading');
                $(this).pagination('loaded');
            },
            loadFilter: function(data){
                return getData(data);
            }
        });
    }

    // 编辑初始化数据
    function getData(data){
        var rows = [];
        var total = data.total;
        for(var i=0; i<data.rows.length; i++){
            rows.push({
                address: data.rows[i].address,
                riskType: data.rows[i].riskType,
                riskDesc :"<a title=\""+data.rows[i].riskDesc+"\">"+data.rows[i].riskDesc+"</a>",
                riskLevel:data.rows[i].riskLevel,
                hazardFactortsNum: '<input type="button" style="color: red" onclick="getDetail(\''+data.rows[i].id+'\');" value=\"'+data.rows[i].hazardFactortsNum+'\" />',
                manageLevel: data.rows[i].manageLevel,
                dutyManager: data.rows[i].dutyManager,
                identifiDate: data.rows[i].identifiDate,
                expDate: data.rows[i].expDate,
                specificType: data.rows[i].specificType,
                specificName: data.rows[i].specificName
            });
        }
        var newData={"total":total,"rows":rows};
        return newData;
    }
    function getDetail(id){
        createdetailwindow("查看","riskIdentificationController.do?wxysList&load=detail&id="+id,900,500);
    }
    function valueTitle(value){
        alert("1111")
        return "<a title=\""+value+"\">"+value+"</a>";
    }

    /**
     * 将饼状图中对应的汉字转换为数据库中对应的英文字段，对应字段如下：
     * 高危作业：gwzy
     * 新工作面：xgzm
     * 事故后：sgh
     * 工艺设备：gysb
     * 停复工及新技术：tfgjxjs
     */
    function changeClickType(t){
        var type;
        if(t=="建设项目可行及投入生产前"){
            type = "1"
        }else if(t=="新水平采区工作面设计和投入生产前"){
            type = "2";
        }else if(t=="系统工艺设备重大变化前"){
            type = "3";
        }else if(t=="灾害因素重大变化"){
            type = "4";
        }else if(t=="高危作业实施前"){
            type = "5";
        }else if(t=="新材料设备技术工艺试验或推广应用前"){
            type = "6";
        }else if(t=="复工复产前"){
            type = "7";
        }else if(t=="事故后"){
            type = "8";
        }else{
            type = "";
        }
        return type;
    }
</script>