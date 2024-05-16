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
<script type="text/javascript">
    $(function () {
        $('#container').highcharts({
            chart: {
                type: 'line'
            },
            title: {
                text: '${mineName}三违人次曲线'
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: [
                <c:forEach items="${retX}" var="x" varStatus="status">
                        "${x}"<c:if test="${!status.last}">,</c:if>
                </c:forEach>
                ]
            },
            yAxis: {
                title: {
                    text: '三<br/>违<br/>人<br/>次',
                    rotation:0
                }
            },
            tooltip: {
                enabled: false,
                formatter: function() {
                    return '<b>'+ this.series.name +'</b><br/>'+this.x +': '+ this.y +'°C';
                }
            },
            credits: {
                enabled: false
            },
            exporting: { enabled: false },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: false
                }
            } ,legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'middle',
                borderWidth: 0
            },exporting: { enabled: false },
            series: [{
                name:'本矿',
                data: [${datasb}]
            }]
        });
    });
</script>
<div id="container" style="width: 100%"></div>
