<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/hashMap/HashMap.js"></script>
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

<script type="text/javascript">
    var char2;

    var size = "${msg}";
    $(function () {
        var time = new Date();
        var year = time.getFullYear();
        var month = time.getMonth()+1;
        if (month < 10) {
            month = "0" + month;
        }

        $("#queryMonth").val(year+"-"+month);
        $("#queryMonth").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){
            WdatePicker({dateFmt:'yyyy-MM'});
        });
        $("#startDate").val(year+"-"+month+"-"+"01");
        $("#startDate").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){
            WdatePicker({dateFmt:'yyyy-MM-dd'});
        });
        $("#endDate").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){
            WdatePicker({dateFmt:'yyyy-MM-dd'});
        });

        var isMonthQuery = "${isMonthQuery}";
        $("#isMonthQuery").children("option[value="+isMonthQuery+"]").attr("selected","selected");
        if(isMonthQuery == '0'){
            $("#additionCondition").show();
            $("#queryMonth").val("");
            $("#queryMonth").hide();
        }else if(isMonthQuery == '1'){
            $("#startDate").val("");
            $("#endDate").val("");
            $("#additionCondition").hide();
            $("#queryMonth").show();
        }else{
            $("#additionCondition").hide();
            $("#queryMonth").show();
        }

        /////////////////////begin///////////////////////
        char2 = new Highcharts.Chart({
            chart: {
                renderTo: 'container2',
                type: 'column'
            },
            colors:['#4412FA','#FFFF00','#FFC000','#FF0000'],
            title: {
                text: '三违性质统计'
            },
            xAxis: {
                categories:[]
            },
            credits: {
                enabled: false
            },
            yAxis: {
                min: 0,
                title: {
                    text: '三<br/>违<br/>次<br/>数',
                    rotation:0
                },
                stackLabels: {
                    enabled: true,
                    style: {
                        fontWeight: 'bold',
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                    }
                }
            },
            legend: {
                align: 'right',
                x: -70,
                verticalAlign: 'top',
                y: 20,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
                            this.series.name +': '+ this.y +'<br/>'+
                            '合计三违: '+ this.point.stackTotal;
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
                    }
                }
            }
            /* series: []*/
        });

        //////////////////////end/////////////////////

    });
    function initSeries(){
        var series = new Array();
        <c:forEach items="${sessionScope.retList}" var="belongMineMap" varStatus="status">
        series.push({name: '${belongMineMap.belongMine}',data: [${belongMineMap.charData}]});

        </c:forEach>
        return series;
    }
    function getDetail(unitCode){
        var type = $("#isMonthQuery option:selected").val();
        var queryMonth = $("#queryMonth").val();
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        createdetailwindow("查看","tBThreeViolationsController.do?goDetailList&unitCode="+unitCode+"&isMonthQuery="+type+"&queryMonth="+queryMonth+"&startDate="+startDate+"&endDate="+endDate,800,600);
        //createdetailwindow('查看','tBThreeViolationsController.do?goDetailList&unitCode='+unitCode+"&startDate="+$("#startDate").val(),800,600);
    }
    function resetTable(){
        $("#startDate").val("");
        $("#endDate").val("");
        $("#queryMonth").val("");
        $("#isMonthQuery").children("option:first").attr("selected","selected");
        changeQueryType();
        tBThreeViolationsListsearch();
    }
    function tBThreeViolationsListsearch(){
        var type = $("#isMonthQuery option:selected").val();
        var queryMonth = $("#queryMonth").val();
        var startDate = $("#startDate").val();
        var endDate = $("#endDate").val();
        $('#jeecgEasyUIList').datagrid({
            title: '三违性质统计',
            idField: 'id',
            fit:false,
            loadMsg: '数据加载中...',
            pageSize: 10,
            pagination:false,
            sortOrder:'asc',
            rownumbers:true,
            singleSelect:true,
            fitColumns:true,
            showFooter:true,
            url:'tBThreeViolationsController.do?qualitativeAnalysisDatagrid&isMonthQuery='+type+'&queryMonth='+queryMonth+'&startDate='+startDate+'&endDate='+endDate,
            toolbar: '#tb2',
            loadFilter: function(data){
                return getData(data);
            }

        });
    }

    function changeQueryType(){
        var type = $("#isMonthQuery option:selected").val();
        if(type=="1"){
            $("#additionCondition").hide();
            $("#queryMonth").show();
        }else{
            $("#additionCondition").show();
            $("#queryMonth").hide();
        }
    }
</script>

<div class="easyui-layout" fit="true">

    <div region="center" style="overflow-y:scroll;padding: 1px;height:200px;">
        <%--<div style="text-align: center">

            日期:<input type="text" id="startDate" name="startDate"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <a class="easyui-linkbutton l-btn" onclick="tBThreeViolationsListsearch()" iconcls="icon-search" href="#">
                <span>查询</span>
            </a>
            <a class="easyui-linkbutton l-btn" onclick="resetTable()" iconcls="icon-reload" href="#">
                <span>重置</span>
            </a>
        </div>--%>
        <fieldset class="queryfieldset">
            <legend>信息查询</legend>
            <div id="tempSearchColums">
                <div name="searchColums">
                <span style="display:inline-block;margin-left: 50px;">
                    查询方式：
                    <select name="isMonthQuery" id="isMonthQuery" onchange="changeQueryType()">
                        <option value="1">按月查询</option>
                        <option value="0">按起止时间查询</option>
                    </select>
                </span>
                <span style="display:inline-block;">
                    <span style="width: 120px; text-align: right; overflow: hidden; vertical-align: middle; display: inline-block; white-space: nowrap; -ms-text-overflow: ellipsis; -o-text-overflow: ellipsis;" title="日期">
                           查询时间：
                    </span>
                    <input type="text" id="queryMonth" name="queryMonth" style="width: 100px"  value="${startDate}" />
                    <span id="additionCondition" style="display: none;">
                        <input type="text" id="startDate" name="startDate" style="width: 100px" value="${startDate}"/>
                        <span style="width: 8px; text-align: right;">~</span>
                        <input type="text" id="endDate" name="endDate" style="width: 100px" value="${endDate}"/>
                    </span>
                </span>
                <span style="float:right;margin-right: 10px;margin-bottom: 10px;">
                    <a id="" class="easyui-linkbutton l-btn" onclick="tBThreeViolationsListsearch()" iconcls="icon-search"
                       href="#">
                        <span>查询</span>
                    </a>
                    <a id="" class="easyui-linkbutton l-btn" onclick="resetTable()" iconcls="icon-reload"
                       href="#">
                        <span>重置</span>
                    </a>
                </span>
                </div>
            </div>
        </fieldset>
        <div id="container2" ></div>
        <table id="jeecgEasyUIList" >
            <thead>
            <tr>
                <th field="belongMine" width="30">煤矿名称</th>
                <c:forEach items="${list}" var="tsType">
                    <th field="vio_qualitative${tsType.typecode}" width="30">${tsType.typename}</th>
                </c:forEach>
                <th field="count" width="30">合计</th>
                <th field="detail" width="30">查看详情</th>
            </tr>
            </thead>
        </table>

        <script type="text/javascript">
            // 编辑初始化数据
            function getData(data){
                var series=char2.series;
                while(series.length > 0) {
                    series[0].remove(false);
                }
                char2.xAxis[0].setCategories(new Array());
                char2.redraw();

                var rows = [];
                var total = data.total;
                var unitArray = new Array();//4柱图
                var map = new HashMap();
                for(var i=0; i<data.rows.length; i++){
                    var deptname = data.rows[i].units;
                    var str = deptname.charAt(0);
                    for( var j = 1; j < deptname.length; j++){
                        str = str + '<br/>' + deptname.charAt(j);
                    }
                    unitArray.push(str);
                    <c:forEach items="${list}" var="tsType" varStatus="status">
                    var vio_qualitative${tsType.typecode} = map.get("${tsType.typecode}");
                    if(vio_qualitative${tsType.typecode} == null){
                        vio_qualitative${tsType.typecode} = new Array();
                        map.put("${tsType.typecode}",vio_qualitative${tsType.typecode});
                    }
                    vio_qualitative${tsType.typecode}.push(data.rows[i].vio_qualitative${tsType.typecode});
                    </c:forEach>
                    rows.push({
                        belongMine: data.rows[i].units,
                        <c:forEach items="${list}" var="tsType" varStatus="status">
                        vio_qualitative${tsType.typecode}: data.rows[i].vio_qualitative${tsType.typecode} ,
                        </c:forEach>
                        count: data.rows[i].count,
                        detail:'<button onclick="getDetail(\''+data.rows[i].unitCode+'\');" >查看详情</button>'
                    });
                }
                char2.xAxis[0].setCategories(unitArray);
                <c:forEach items="${list}" var="tsType" varStatus="status">
                char2.addSeries({
                    name: '${tsType.typename}',
                    data: eval("["+map.get("${tsType.typecode}")+"]")
                }, true);
                </c:forEach>
                var newData={"total":total,"rows":rows};
                return newData;
            }
            // 筛选
            function jeecgEasyUIListsearchbox(value,name){
                var queryParams=$('#jeecgEasyUIList').datagrid('options').queryParams;
                queryParams[name]=value;
                queryParams.searchfield=name;
                $('#jeecgEasyUIList').datagrid('load');
            }
            // 刷新
            function reloadTable(){
                $('#jeecgEasyUIList').datagrid('reload');
            }

            // 设置datagrid属性
            $('#jeecgEasyUIList').datagrid({
                title: '三违性质统计',
                idField: 'id',
                fit:false,
                loadMsg: '数据加载中...',
                pageSize: 10,
                pagination:false,
                sortOrder:'asc',
                rownumbers:true,
                singleSelect:true,
                fitColumns:true,
                showFooter:true,
                url:'tBThreeViolationsController.do?qualitativeAnalysisDatagrid&isMonthQuery=${isMonthQuery}&queryMonth=${queryMonth}&startDate=${startDate}&endDate=${endDate}',
                toolbar: '#tb2',
                loadFilter: function(data){
                    return getData(data);
                }

            });
            //设置分页控件
            $('#jeecgEasyUIList').datagrid('getPager').pagination({
                pageSize: 10,
                pageList: [10,20,30],
                beforePageText: '',
                afterPageText: '/{pages}',
                displayMsg: '{from}-{to}共{total}条',
                showPageList:true,
                showRefresh:true,
                onBeforeRefresh:function(pageNumber, pageSize){
                    $(this).pagination('loading');
                    $(this).pagination('loaded');
                }
            });
            // 设置筛选
            $('#jeecgEasyUIListsearchbox').searchbox({
                searcher:function(value,name){
                    jeecgEasyUIListsearchbox(value,name);
                },
                menu:'#jeecgEasyUIListmm',
                prompt:'请输入查询关键字'
            });
        </script></div>
</div>