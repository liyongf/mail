<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<%
    session.setAttribute("lang","zh-cn");
%>
<base href="${webRoot}/">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link rel="stylesheet" href="plug-in/Validform/css/divfrom.css" type="text/css">
<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css">
<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css">
<script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
<script src="plug-in/supermap/dist/include-classic.js"></script>
<script>
    var map, restLayer, pointVector, lineVector, animatorVector, popup, carAnimatorVector, metroAnimatorVector, trainAnimatorVector,
            url = "${supermapMineUrl}";
    var popups = {};
    var carPopups = [];
    var carStyle =
    {
        externalGraphic: "plug-in/supermap/images/blueCar.png",
        allowRotate: true,
        graphicWidth: 32,
        graphicHeight: 32
    };

    var metroStyle = {
        fillColor: "#c165f6",
        pointRadius: 3,
        strokeColor: "#c165f6",
        strokeWidth: 5
    };
    var trainStyle =
    {
        fillColor: "#c165f6",
        fillOpacity: 1,
        strokeOpacity: 0,
        pointRadius: 5
    };
    function init() {
        //1.在指定DOM元素上初始化地图对象。
        map = new SuperMap.Map("map", {
            controls: [
                new SuperMap.Control.Navigation({
                    dragPanOptions: {
                        enableKinetic: true
                    }
                })]
        });
        map.addControl(new SuperMap.Control.Zoom());
        map.addControl(new SuperMap.Control.ScaleLine());
        map.addControl(new SuperMap.Control.LayerSwitcher());
        map.addControl(new SuperMap.Control.MousePosition());
        //2.初始化图层。
        //创建分块动态REST图层，该图层显示iserver 8C 服务发布的地图,
        //其中"想要"为图层名称，url图层的服务地址，{transparent: true}设置到url的可选参数
        restLayer = new SuperMap.Layer.TiledDynamicRESTLayer("restLayer", url, null, { maxResolution: "auto" });
        //监听图层信息加载完成事件，异步加载图层。
        restLayer.events.on({ "layerInitialized": addLayer });

        //风险点图层
        pointVector = new SuperMap.Layer.Vector("vectorPointLayer");

        //轨迹直线图层
        lineVector = new SuperMap.Layer.Vector("vectorLineLayer");

        //车辆动画模拟图层
        carAnimatorVector = new SuperMap.Layer.AnimatorVector("Cars", {}, {
            repeat: true,
            speed: 0.01,
            startTime: 0,
            endTime: 3
        });

        //地铁修建动画模拟图层
        metroAnimatorVector = new SuperMap.Layer.AnimatorVector("Metro", {rendererType: "StretchLine"}, {
            repeat: true,
            //设置速度为每帧播放0.05的数据
            speed: 0.01,
            //开始时间为0
            startTime: 0,
            //每秒渲染12帧
            frameRate: 60,
            //结束时间设置为10
            endTime: 3
        });

        //火车动画模拟图层
        trainAnimatorVector = new SuperMap.Layer.AnimatorVector("Train", {rendererType: "TadpolePoint"}, {
            //设置速度为每帧播放0.02小时的数据
            speed: 0.01,
            //开始时间为0晨
            startTime: 0,
            //结束时间设置为最后运行结束的火车结束时间
            endTime: 3
        });

        carAnimatorVector.events.on({"drawfeaturestart": drawCarStart});
        carAnimatorVector.animator.events.on({"firstframestart": carFramestart});
    }
    function addLayer() {
        map.addLayers([restLayer,pointVector,lineVector,carAnimatorVector,metroAnimatorVector,trainAnimatorVector]);
        //map.setCenter(new SuperMap.LonLat(19512122.67 , 4207466.42 ), 0);
        map.setCenter(new SuperMap.LonLat(${supermapMineCenter}), 0);
        refreshAddress();
        window.setTimeout(tracePath,100);
        //tracePath();
    }

</script>
<body onload="init()">
<div class="easyui-layout" fit="true">
    <div region="center" style="padding: 3px;" class="easyui-panel" id="demopanle">
        <div id="map" style="position: absolute; left: 0px; right: 0px; width: auto; height: 99%;"></div>
    </div>
    <%--    <div region="west" style="width: 500px; display: none" title="地点列表"  split="true">

        </div>--%>
</div>
</body>
<script type="text/javascript">
    //点层和标注
    function refreshAddress(){
        var pro=window.top.$.messager;
        if(pro){
            window.top.$.messager.progress({
                text : '正在加载数据....',
                interval : 300
            });
        }
        /*var url = "tBLightAddressController.do?getAllAddressList";*/
        var url = "mobile/mobileLightAddressController.do?getAllAddressList";
        $.ajax({
            type : 'POST',
            url : url,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    if(pro)
                    window.top.$.messager.progress('close');
                    while(map.popups.length>0){
                        var popup = map.popups[0];
                        map.removePopup(popup);
                    }
                    pointVector.removeAllFeatures();
                    var pointlayers = [];
                    $(d.attributes.addressList).each(function(index, d) {
                        var txt = d.address ;
                        var lon = d.lon;
                        var lat = d.lat;
                        var id = d.id;

                        //添加点
                        var point = new SuperMap.Geometry.Point(lon,lat);
                        var pointlayer = new SuperMap.Feature.Vector(point);
                        pointlayer.style = {
                            fillColor: "#FF00FF",
                            strokeColor: "yellow",
                            pointRadius: 8
                        };
                        pointlayers.push(pointlayer);

                        //添加文本
                        var contentHTML = "<div style='position: absolute; background-color:#EE5D5B; border: 1px solid rgb(188, 59, 58); color: black; " +
                            "left: -10px; top: -32px; height: 18px; padding: 2px; line-height: 18px; white-space: nowrap; '><span>";
                        contentHTML += txt;
                        contentHTML += "</span><div name='arrow' style=\"background: rgba(0, 0, 0, 0) url('plug-in/baidumap/image/label.png') no-repeat scroll 0px 0px; position: absolute; width: 11px; height: 10px; top: 22px; left: 10px; overflow: hidden;\"></div></div>";
                        popup = new SuperMap.Popup(
                            id,
                            new SuperMap.LonLat(lon,lat),
                            /*new SuperMap.Size(200, 30),*/
                            null,
                            contentHTML,
                            true,
                            null
                        );
                        map.addPopup(popup);
                        $("#"+id).css('background','none');
                        $("#"+id+"_close").remove();
                        $("#"+id).css("width","auto");
                        $("#"+id).css("height","auto");
                        $("#"+id+"_contentDiv").css("width","auto");
                        $("#"+id+"_contentDiv").css("height","auto");
                        $("#"+id+"_contentDiv").css("overflow","visible");
                        $("#"+id+"_contentDiv div:first").mouseenter(function(){
                            this.style.backgroundColor = "#6BADCA";
                            this.style.borderColor = "#0000ff";
                            $(this).find("[name='arrow']").css("background-position","0px -20px");
                        });
                        $("#"+id+"_contentDiv div:first").mouseleave(function(){
                            this.style.backgroundColor = "#EE5D5B";
                            this.style.borderColor = "#BC3B3A";
                            $(this).find("[name='arrow']").css("background-position","0px 0px");
                        });
                        $("#"+id+"_contentDiv div:first").click(function(){
                            //
                        });
                    });
                    pointVector.addFeatures(pointlayers);
                }
            },
            error:function(){
            }
        });
    }

    function tracePath(){
        var pro=window.top.$.messager;
        if(pro){
            window.top.$.messager.progress({
                text : '正在加载数据....',
                interval : 300
            });
        }
        /*var url = "tBLightAddressController.do?getTrack";*/
        var url = "mobile/mobileLightAddressController.do?getTrack";

        $.ajax({
            type : 'POST',
            url : url,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    if(pro)
                        window.top.$.messager.progress('close');
                    lineVector.removeAllFeatures();
                    carAnimatorVector.removeAllFeatures();
                    metroAnimatorVector.removeAllFeatures();
                    var lines = []; //绘制线路
                    var cars = [];  //车辆监控模拟
                    var lineFeatures = [];  //地铁修建模拟
                    var pointFeatures = []; //火车监控模拟
                    var maxEndTime = 0;
                    $(d.obj).each(function(index, path) {
                        var userId = path.userId;
                        var userName = path.userName;
                        var points = [];
                        var arrP1 = [];
                        if(path.accessList.length>maxEndTime){
                            maxEndTime = path.accessList.length;
                        }
                        $(path.accessList).each(function(index, access) {
                            var addressId = access.addressId;
                            var addressName = access.addressName;
                            var arrivalTime = access.arrivalTime;
                            var lon = access.lon;
                            var lat = access.lat;
                            points.push(new SuperMap.Geometry.Point(lon,lat));

                            //车辆监控模拟
                            var car = new SuperMap.Feature.Vector(new SuperMap.Geometry.Point(lon,lat),
                                    {
                                        FEATUREID: userId,
                                        TIME: index,
                                        userId:userId,
                                        userName:userName,
                                        addressId:addressId,
                                        addressName:addressName,
                                        arrivalTime:arrivalTime
                                    }, carStyle
                            );
                            cars.push(car);


                            //地铁修建模拟
                            var point1 = new SuperMap.Geometry.Point(lon,lat);
                            point1.userId = userId;
                            point1.addressId = addressId;
                            point1.addressName = addressName;
                            arrP1.push(point1);
                            var lineString1;
                            if(arrP1.length==1){
                                //lineString1 = new SuperMap.Geometry.LineString([point1,point1]);
                                arrP1.push(point1);
                            }
                            if(arrP1.length>1) {
                                lineString1 = new SuperMap.Geometry.LineString(arrP1);
                            }
                            var multiLineString = new SuperMap.Geometry.MultiLineString([lineString1]);
                            var lineFeature = new SuperMap.Feature.Vector(multiLineString, {
                                FEATUREID: userId,
                                TIME: index
                            }, metroStyle);
                            lineFeatures.push(lineFeature);

                            //火车监控模拟
                            var pointFeature = new SuperMap.Feature.Vector(new SuperMap.Geometry.Point(lon,lat), {
                                FEATUREID: userId,
                                TIME: index
                            }, trainStyle);
                            pointFeatures.push(pointFeature);
                        });

                        //绘制线路
                        var lineString = new SuperMap.Geometry.LineString(points);
                        var line = new SuperMap.Feature.Vector(lineString);
                        line.style = {
                            fillColor: "#FF0000",
                            fillOpacity: 0.4,
                            strokeColor: "#FF0000",
                            strokeOpacity: 1,
                            strokeWidth: 3,
                            pointRadius: 6
                        };
                        lines.push(line);
                    });

                    //绘制线路
                    lineVector.addFeatures(lines);

                    //车辆监控模拟
                    carAnimatorVector.addFeatures(cars);
                    carAnimatorVector.animator.setEndTime(maxEndTime);
                    carAnimatorVector.animator.start();

                    //地铁修建模拟
                    metroAnimatorVector.addFeatures(lineFeatures);
                    metroAnimatorVector.animator.setEndTime(maxEndTime);
                    metroAnimatorVector.animator.start();

                    trainAnimatorVector.addFeatures(pointFeatures);
                    trainAnimatorVector.animator.setEndTime(maxEndTime);
                    trainAnimatorVector.animator.start();

                }
            },
            error:function(){
            }
        });

    }
    function carFramestart(a){
        for(var i=0; i<carPopups.length; i++){
            map.removePopup(carPopups[i]);
        }
        carPopups=[];
    }
    function drawCarStart(feature){
        var featureId = feature.attributes.FEATUREID;
        var contentHTML = "";
        contentHTML += "<span style='font-size:12px; line-height: 12px;background-color: #fff'>";
        contentHTML +=  "检查人:";
        contentHTML +=  feature.attributes.userName;
        contentHTML +=  "</br>检查时间:";
        contentHTML +=  feature.attributes.arrivalTime;
        contentHTML +=  "</br>风险点:";
        contentHTML +=  feature.attributes.addressName;
        contentHTML += "</span>";

        var url = "mobile/mobileLightAddressController.do?getHds";
        $.ajax({
            type : 'POST',
            url : url,
            async:false,
            data: {
                userId: feature.attributes.userId,
                userName: feature.attributes.userName,
                arrivalTime: feature.attributes.arrivalTime,
                addressId: feature.attributes.addressId,
                addressName: feature.attributes.addressName
            },
            success : function(data) {
                var d = $.parseJSON(data);
                if (d.success) {
                    if(d.obj.length>0) {
                        var hdTb = "<table border='0' border='1' cellpadding='0' cellspacing='0' >";
                        $(d.obj).each(function (index, hd) {
                            hdTb += "<tr style='background-color: #fffded'><td align='center' style='padding:0.3em 0;font-weight:bolder;'>&nbsp;责任单位&nbsp;</td><td align='center' style='padding:0.3em 0;font-weight:bolder;'>&nbsp;责任人&nbsp;</td><td align='center' style='padding:0.3em 0;font-weight:bolder;'>&nbsp;隐患描述&nbsp;</td></tr>";
                            hdTb += "<tr style='background-color: red'><td align='center'>";
                            hdTb += hd.dutyUnit;
                            hdTb += "</td><td align='center'>";
                            hdTb += hd.dutyMan;
                            hdTb += "</td><td align='center'>";
                            hdTb += hd.hdDesc;
                            hdTb += "</td></tr></table>";
                        });
                        contentHTML += hdTb;
                    }

/*                    popup = new SuperMap.Popup("d",
                            new SuperMap.LonLat(feature.geometry.x, feature.geometry.y),
                            *//*new SuperMap.Size((feature.attributes.addressName.length + 1) * 12, 12),*//*
                            null,
                            contentHTML,
                            false);
                    popup.setOpacity(0.8);
                    popup.setBackgroundColor("#fff");*/

                    var popup = new SuperMap.Popup.FramedCloud("popwin",
                            new SuperMap.LonLat(feature.geometry.x, feature.geometry.y),
                            null,
                            contentHTML,
                            null,
                            true);

                    carPopups.push(popup);
                    map.addPopup(popup);
                }
            },
            error:function(){
            }
        });

    }
/*
     (function(){
     $(".datagrid-toolbar").css("height","auto");
     $(".datagrid-toolbar span").css("float","none");
     })();
*/


</script>
