<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<link rel="stylesheet" href="plug-in/Validform/css/divfrom.css" type="text/css">
<link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css">
<link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css">
<script type="text/javascript" src="plug-in/Validform/js/Validform_v5.3.1_min_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/Validform_Datatype_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/Validform/js/datatype_zh-cn.js"></script>
<script type="text/javascript" src="plug-in/baidumap/js/api.js"></script>
<script type="text/javascript" src="plug-in/baidumap/js/AreaRestriction_min.js"></script>
<script type="text/javascript" src="plug-in/jquery/jquery.jqprint.js"></script>
<link rel="stylesheet" href="plug-in/baidumap/css/print.css" type="text/css" media="print">
<style type="text/css">
    /*隐藏百度logo*/
    .anchorBL {
        display: none;
    }
    .tabs-wrap{
        overflow: visible;
    }
</style>
<div id="searchBtnDiv" style="display: none;">
    <div name="searchColums" id="searchColums">
        <input id="isShowSearchId" value="false" type="hidden" />
        <input id="_sqlbuilder" name="sqlbuilder" type="hidden" />
        <form onkeydown="if(event.keyCode==13){tBAddressInfoListsearch();return false;}" id="tBAddressInfoListForm">
            <link rel="stylesheet" href="plug-in/Validform/css/style.css" type="text/css" />
            <link rel="stylesheet" href="plug-in/Validform/css/tablefrom.css" type="text/css" />
            <span style="display:-moz-inline-box;display:inline-block;">
                <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 90px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; " title="地点名称">地点名称：</span>
                <input onkeypress="EnterPress(event)" onkeydown="EnterPress()" name="address" style="width: 120px" class="inuptxt" type="text" />
            </span>
            <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-search" onclick="tBAddressInfoListsearch()">
                查询
            </a>
            <a href="#" class="easyui-linkbutton l-btn" iconcls="icon-reload" onclick="searchReset('tBAddressInfoList')">
                重置
            </a>
        </form>
    </div>
</div>
<div region="center" style="padding: 3px;" class="easyui-panel" id="demopanle">
<div id="tt" class="easyui-tabs" style="width: auto;" data-options="onSelect:function(t){$('#tt .panel-body').css('width','auto');showRiskList();}">
    <div title="图形化"><div id="map" style="height: 92%;width: 100%;"></div></div>
    <div title="关联的风险">
        <iframe  id="riskList" src='' style="height: 92%;width: 100%;" frameborder="0"></iframe>
    </div>
    <div title="关联的单位">
            <iframe  id="addressDepartRelList" src='' style="height: 92%;width: 100%;" frameborder="0"></iframe>
    </div>
</div>

</div>
<div region="west" style="width: 500px;" title="地点列表"  split="true">
 <t:datagrid name="tBAddressInfoList" checkbox="true" onDblClick="locationAddress" onClick="initAddressId" fitColumns="false" title="" actionUrl="tBAddressInfoController.do?datagrid" idField="id" fit="true" queryMode="group" onLoadSuccess="loadSuccessed">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="风险点"  field="address"  query="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="类型"  field="cate"  query="false" hidden="true" dictionary="locationCate"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="关联风险数量" field="dangerSourceCount" queryMode="group" width="80"></t:dgCol>
   <t:dgCol title="是否显示"  field="isshow" dictionary="sf_yn"   queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="分管责任人"  field="manageMan" queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="位置描述"  field="description"  hidden="true" query="false"  queryMode="single"  width="120"></t:dgCol>
   <t:dgCol title="经度"  field="lon"   hidden="true"  queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="纬度"  field="lat"  hidden="true"   queryMode="group"  width="80"></t:dgCol>
   <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
   <t:dgCol title="是否删除"  field="isDelete"  hidden="true"  queryMode="single"  width="120"></t:dgCol>
   <t:dgToolBar title="录入" operationCode="add" icon="icon-add" url="tBAddressInfoController.do?goAdd" funname="addAddress"></t:dgToolBar>
   <t:dgToolBar title="编辑" operationCode="update" icon="icon-edit" url="tBAddressInfoController.do?goUpdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="更改坐标" operationCode="editCoordinate" icon="icon-edit" url="tBAddressInfoController.do?goUpdate" funname="editCoordinate"></t:dgToolBar>
   <t:dgToolBar title="删除"  icon="icon-remove" url="tBAddressInfoController.do?doBatchDel" operationCode="delete" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="导出风险" icon="icon-putout"  funname="exportRelatedRisks" operationCode="export"></t:dgToolBar>
   <t:dgToolBar title="查看" operationCode="detail" icon="icon-search" url="tBAddressInfoController.do?goDetail" funname="detail"></t:dgToolBar>
     <t:dgToolBar title="导出" icon="icon-putout" funname="ExportXls" operationCode="export" ></t:dgToolBar>
     <t:dgToolBar title="打印地图" icon="icon-print" funname="printMap" operationCode="print" ></t:dgToolBar>
   <%--<t:dgToolBar title="上报集团" operationCode="detail" icon="icon-search" url="tBAddressInfoController.do?reportGroup" funname="reportGroup"></t:dgToolBar>--%>
   <t:dgToolBar title="克隆" operationCode="clone" icon="icon-search" funname="cloneAddress"></t:dgToolBar>
   <c:if test="${isSunAdmin != 'YGADMIN'}">
       <t:dgToolBar title="全屏" icon="icon-full-screen" funname="showFullScreen" ></t:dgToolBar>
   </c:if>
   <c:if test="${isSunAdmin == 'YGADMIN'}">
       <t:dgToolBar title="隐藏" icon="icon-tip" funname="sunHidden"></t:dgToolBar>
   </c:if>
 </t:datagrid>
</div>



</div>
<script type="text/javascript">
    var global_id, global_address, currIndex= 0, addRelFlag= 0, tabIndex=0;
    function exportRelatedRisks(){
        var rows=$("#tBAddressInfoList").datagrid('getSelections');
        if(rows==0){
            tip("请勾选要导出的关联风险");
        }else if(rows.length>=1){
            var idsTemp=new Array();
            for(var i=0;i<rows.length;i++){
                idsTemp.push(rows[i].id);
            }
            var idt=idsTemp.join(",");
            $.dialog.confirm("是否确认导出"+idsTemp.length+"条记录？",function(){
                JeecgExcelExport("tBDangerSourceController.do?exportRelatedRisks&id="+idt+"&lec=${initParam.les}","tBAddressInfoList");
            })
        }
    }
    function showRiskList(){
        var tab = $('#tt').tabs('getSelected');
        var index = $('#tt').tabs('getTabIndex',tab);
        if(tabIndex==index){
            return;
        }
        tabIndex=index;
        if(global_id && global_address){
            reloadDangerSourceList(global_id, global_address);
        }
    }
    function initAddressId(rowIndex,rowData){
        global_id = rowData.id;
        global_address = rowData.address;
        reloadDangerSourceList(global_id, global_address);
    }
    function reloadDangerSourceList(id, address){
        var tab = $('#tt').tabs('getSelected');
        var index = $('#tt').tabs('getTabIndex',tab);
        if(index==1){
            $("#riskList").attr("src", "<%=request.getContextPath()%>/tBDangerSourceController.do?addressDangerSourceList&addressId="+id+"&addressName="+encodeURI(encodeURI(address)));
        } else if(index==2){
            $("#addressDepartRelList").attr("src", "<%=request.getContextPath()%>/tBAddressInfoController.do?addressDepartRelList&addressId="+id);
        }
    }
 var tileLayer = new BMap.TileLayer();
  tileLayer.getTilesUrl = function(tileCoord, zoom) {
    var x = tileCoord.x;
    var y = tileCoord.y;
    //return 'plug-in/baidumap/tiles/' + zoom + '/tile' + x + '_' + y + '.png';
    return '${mapPath}' + zoom + '/tile' + x + '_' + y + '.png';
}
var MyMap = new BMap.MapType('MyMap', tileLayer, {minZoom: 3, maxZoom: 8});
var map = new BMap.Map('map', {mapType: MyMap});
map.addControl(new BMap.NavigationControl());
map.enableScrollWheelZoom(); // 允许滚动
var b = new BMap.Bounds(new BMap.Point(48.406441, 2.865784),new BMap.Point(186.386167, 65.265558)); // 范围 左下角，右上角的点位置

map.centerAndZoom(new BMap.Point(0,0), 4);


 function addAddress() {
     tabIndex=0;
     $('#tt').tabs('select',0);
     map.setDefaultCursor("crosshair");
     map.removeEventListener("click", showInfo);
     map.addEventListener("click", showInfo);
 }
 function showInfo(e) {
     map.removeEventListener("click", showInfo);
     add('录入', 'tBAddressInfoController.do?goAdd&lon=' + e.point.lng + "&lat=" + e.point.lat, "tBAddressInfoList");

     map.setDefaultCursor("hand");
 }

function editCoordinate(){
    var rows = $("#tBAddressInfoList").datagrid('getSelections');
    if(rows == null || rows.length <1){
        tip("请选择要更改坐标的地点！");
        return;
    }else if(rows.length > 1){
        tip("每次只能更改一个地点的坐标！");
        return;
    }else{
        tabIndex=0;
        $('#tt').tabs('select',0);
        map.setDefaultCursor("crosshair");
        map.removeEventListener("click", updateCoordinate);
        map.addEventListener("click", updateCoordinate);
    }
}

function updateCoordinate(e){
    map.removeEventListener("click", updateCoordinate);
    var selected = $("#tBAddressInfoList").datagrid('getSelected');
    var addressId = "";
    if(selected){
        addressId = selected.id;
        var lng = e.point.lng;
        var lat = e.point.lat;
        var msg = "确定要将\"" + selected.address + "\"的坐标由(" + selected.lon + "，" + selected.lat + ")更改为(" + lng + "，" + lat + ")吗？";
        $.messager.confirm("更改坐标",msg,function(r){
            if(r){
                $.ajax({
                    url: "tBAddressInfoController.do?doUpdateCoordinate",
                    type: 'POST',
                    data: {addressId:addressId,lng:lng,lat:lat},
                    error: function(){
                    },
                    success: function(data){
                        data = $.parseJSON(data);
                        tip(data.msg);
                        $("#tBAddressInfoList").datagrid("load");
                    }
                });
            }
        });
    }
    map.setDefaultCursor("hand");
}

 
//复杂的自定义覆盖物(地址图标显示)
 function ComplexCustomOverlay(point, text, mouseoverText, id){
   this._point = point;
   this._text = text;
   this._overText = mouseoverText;
   this._id = id;
 }
 ComplexCustomOverlay.prototype = new BMap.Overlay();
 ComplexCustomOverlay.prototype.initialize = function(map){
   this._map = map;
   var div = this._div = document.createElement("div");
   div.style.position = "absolute";
   div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
   div.style.backgroundColor = "#EE5D5B";
   div.style.border = "1px solid #BC3B3A";
   div.style.color = "white";
   div.style.height = "18px";
   div.style.padding = "2px";
   div.style.lineHeight = "18px";
   div.style.whiteSpace = "nowrap";
   div.style.MozUserSelect = "none";
   div.style.fontSize = "12px";
   var span = this._span = document.createElement("span");
   div.appendChild(span);
   span.appendChild(document.createTextNode(this._text));      
   var that = this;

   var arrow = this._arrow = document.createElement("div");
   arrow.style.background = "url(plug-in/baidumap/image/label.png) no-repeat";
   arrow.style.position = "absolute";
   arrow.style.width = "11px";
   arrow.style.height = "10px";
   arrow.style.top = "22px";
   arrow.style.left = "10px";
   arrow.style.overflow = "hidden";
   div.appendChild(arrow);

   var myZindex = 0;
   div.onmouseover = function(){
     this.style.backgroundColor = "#6BADCA";
     this.style.borderColor = "#0000ff";
     myZindex = $(this).css('z-index');
     $(this).css("z-index", 10000000);
     this.getElementsByTagName("span")[0].innerHTML = that._overText;
     arrow.style.backgroundPosition = "0px -20px";
   }

   div.onmouseout = function(){
     this.style.backgroundColor = "#EE5D5B";
     this.style.borderColor = "#BC3B3A";
     $(this).css("z-index", myZindex);
     this.getElementsByTagName("span")[0].innerHTML = that._text;
     arrow.style.backgroundPosition = "0px 0px";
   }

   map.getPanes().labelPane.appendChild(div);
   
   return div;
 }
 ComplexCustomOverlay.prototype.draw = function(){
   var map = this._map;
   var pixel = map.pointToOverlayPixel(this._point);
   this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
   this._div.style.top  = pixel.y - 30 + "px";
 }
 //添加监听事件
 ComplexCustomOverlay.prototype.addEventListener = function(event,fun){
    this._div['on'+event] = fun;
 }


    function refreshAddress(){
	 var pro=window.top.$.messager;
		if(pro){
			window.top.$.messager.progress({
				text : '正在加载数据....',
				interval : 300
			});
		}
	 var url = "tBAddressInfoController.do?getAllAddressList";
		$.ajax({
			type : 'POST',
			url : url,
			success : function(data) {
				var d = $.parseJSON(data);
				if (d.success) {
					if(pro)
						window.top.$.messager.progress('close');
					map.clearOverlays(); 
					$(d.attributes.addressList).each(function(index, d) {
						var txt = d.address ;
						var lon = d.lon;
						var lat = d.lat;
                        var id = d.id;
						var myCompOverlay = new ComplexCustomOverlay(new BMap.Point(lon,lat),txt,txt,id);
						map.addOverlay(myCompOverlay);

                        myCompOverlay.addEventListener("click", function(){
                            global_id = myCompOverlay._id;
                            global_address = myCompOverlay._text;
                            tabIndex=1;
                            $('#tt').tabs('select',1);
                            reloadDangerSourceList(global_id, global_address);
                        });
					});
				}
			},
            error:function(){
            }
		});
 }

 //定位
 function locationAddress(rowIndex,rowData){
	 var new_point = new BMap.Point(rowData.lon,rowData.lat);
		map.panTo(new_point);  
		changeSelectDivColor(rowIndex,rowData);
 }
 
 //改变双击选中的地点的颜色
    var previousPoint;
    function changeSelectDivColor(rowIndex,rowData) {
        //将前一个地点的颜色重置为粉红色
        if(previousPoint!=null && previousPoint!=""){
            previousPoint.closest("div").css("background-color","#EE5D5B");
        }
        var thisPoint = $("span:contains("+rowData.address+")");
        previousPoint = thisPoint;
        thisPoint.closest("div").css("background-color","#27a1ff");
    }
 
 //删除操作
 /**
 * 执行操作
 * 
 * @param url
 * @param index
 */
function doSubmit1(url,name,data) {
	gridname=name;
	var paramsData = data;
	if(!paramsData){
		paramsData = new Object();
		if (url.indexOf("&") != -1) {
			var str = url.substr(url.indexOf("&")+1);
			url = url.substr(0,url.indexOf("&"));
			var strs = str.split("&");
			for(var i = 0; i < strs.length; i ++) {
				paramsData[strs[i].split("=")[0]]=(strs[i].split("=")[1]);
			}
		}      
	}
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		data : paramsData,
		url : url,// 请求的action路径
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			var d = $.parseJSON(data);
			if (d.success) {
				var msg = d.msg;
				tip(msg);
				reloadTable();
			}
		}
	});
}
 
/**
 * 创建询问窗口
 * 
 * @param title
 * @param content
 * @param url
 */
function createdialog1(title, content, url,name) {
	$.dialog.confirm(content, function(){
		doSubmit1(url,name);
		rowid = '';
	}, function(){
	}).zindex();
}

//删除调用函数
function delObj1(id) {
	var url = "tBAddressInfoController.do?doDel&id="+id;
	gridname="tBAddressInfoList";
	createdialog1('删除确认 ', '确定删除该记录吗 ?', url,gridname);
}

 //导出
 function ExportXls() {
     JeecgExcelExport("tBAddressInfoController.do?exportXls","tBAddressInfoList");
 }

    function loadSuccessed(){
        if(addRelFlag==1){
            addRelFlag = 0;
            return;
        }

        $("#tBAddressInfoListtb").prepend($("#searchBtnDiv").html());
        $("#searchBtnDiv").empty();
        map.panTo(new BMap.Point(0,0));
        refreshAddress();
        tabIndex=0;
        $('#tt').tabs('select',0);
        $('#riskList').contents().find("body").html("");
        $('#addressDepartRelList').contents().find("body").html("");
    }
    window.top["tip_addressDangerSourceList"]=function(){
        tip(this.msg);
    };
    window.top["reload_addressDangerSourceList"]=function(){
        addRelFlag=1;
        $('#tBAddressInfoList').datagrid('reload');
        $("#riskList")[0].contentWindow.tBDangerSourceListsearch();
    };
    window.top["reload_tBAddressInfoList"]=function(){
        addRelFlag=1;
        $('#tBAddressInfoList').datagrid('reload');
    };

function reportGroup(){
   // debugger
    var selected = $("#tBAddressInfoList").datagrid('getSelections');
    if(selected != null && selected.length <1){
        tip("请选择要是上报的条目");
    }else{
        var ids = new Array();

        for (var i = 0; i < selected.length; i++) {
            ids.push(selected[i].id);
//            alert(selected[i].id);
        }
        ids.join(',');
        $.ajax({
            url: "tBAddressInfoController.do?reportGroup&ids="+ids+"&tt="+new Date(),
            type: 'POST',
            error: function(){
            },
            success: function(data){
                data = $.parseJSON(data);
                tip(data.msg);
            }
        });
    }
}

    function cloneAddress(){
        var selected = $("#tBAddressInfoList").datagrid('getSelections');
        if(selected != null && selected.length !=1){
            tip("请选择要克隆的一条条目");
        }else{
            createwindow("克隆风险点","tBAddressInfoController.do?goClone&id="+selected[0].id,700,400);
        }

    }

    function showFullScreen(){
        var url ="tBAddressInfoController.do?managelist";
        createdetailwindow('风险区域',url,"100%","100%");
    }

    /**
     *  阳光账号隐藏数据功能
     * */
    function sunHidden() {
        var rows = $("#tBAddressInfoList").datagrid('getSelections');
        if (rows.length < 1) {
            tip("请选择需要隐藏的数据");
        } else {
            var idsTemp = new Array();
            for (var i = 0; i < rows.length; i++) {
                idsTemp.push(rows[i].id);
            }
            var idt = idsTemp.join(",");

            $.ajax({
                type: 'POST',
                url: 'tBAddressInfoController.do?sunshine',
                dataType:"json",
                async:true,
                cache: false,
                data: {
                    ids:idt
                },
                success:function(data){
                    var msg = data.msg;
                    tip(msg);
                    reloadTable();
                },
                error:function(data){
                }
            });
        }
    }
    (function(){
        $(".datagrid-toolbar").css("height","auto");
        $(".datagrid-toolbar span").css("float","none");
    })();
    function printMap(){
        $("#map").jqprint({
            debug: true,
            importCSS: true,
            printContainer: true,
            operaSupport: false
        });
    }

</script>
