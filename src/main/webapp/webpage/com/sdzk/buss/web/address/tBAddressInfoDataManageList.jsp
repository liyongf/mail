<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <br>
        <span style="display:-moz-inline-box;display:inline-block;margin-top: 10px">
        <input  name="isShowData" value="1" type="hidden">
           <label>
            <input name="queryHandleStatusTem" type="radio" value="1" checked="checked">
									显示风险点相关隐患三违数据
           </label>
           <label>
								<input name="queryHandleStatusTem" type="radio" value="0">
									隐藏风险点相关隐患三违数据
           </label>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="tBAddressInfoList" checkbox="true" pagination="true" fitColumns="false" title="地点列表" actionUrl="tBAddressInfoController.do?dataManageDatagrid" idField="id" fit="true" queryMode="group">
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="地点名称"  field="address"    queryMode="single"  width="120" query="true"></t:dgCol>
            <t:dgCol title="经度"  field="lon"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="纬度"  field="lat"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="是否显示"  field="isshow"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="是否删除"  field="isDelete"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="创建人名称"  field="createName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新人登录名称"  field="updateBy"  hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgCol title="更新日期"  field="updateDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group"  width="120"></t:dgCol>
            <t:dgToolBar title="隐藏相关数据" icon="icon-add" url="tBAddressInfoController.do?goAdd" funname="hiddenAddressData" operationCode="hiddenAddressData"></t:dgToolBar>
            <t:dgToolBar title="显示相关数据" icon="icon-edit" url="tBAddressInfoController.do?goUpdate" funname="showAddressData" operationCode="showAddressData" ></t:dgToolBar>
        </t:datagrid>
    </div>
</div>
<script src = "webpage/com/sdzk/buss/web/address/tBAddressInfoList.js"></script>
<script type="text/javascript">

    function showAddressData(){
        var url = "tBAddressInfoController.do?showAddressData";
        setAddressData(url);
    }

    function hiddenAddressData(){
        var url = "tBAddressInfoController.do?hiddenAddressData";
        setAddressData(url);
    }
    function setAddressData(url){
        var rowsData = $('#tBAddressInfoList').datagrid('getSelections');
        if(rowsData == null || rowsData.length <1){
            tip("请选择需要隐藏的风险点");
        }else{
            var ids = new Array();
            for(var i =0 ;i<rowsData.length;i++){
                ids.push(rowsData[i].id);
            }
            $.ajax({
                url: url,
                type: 'post',
                data: {
                    ids: ids.join(',')
                },
                cache: false,
                success: function (data) {
                    var d = $.parseJSON(data);
                    if (d.success) {
                        var msg = d.msg;
                        tip(msg);
                        tBAddressInfoListsearch();
                    }
                }
            });
        }
    }
    $(document).ready(function(){
        $("a[iconcls='icon-reload']").hide();
        var datagrid = $("#tBAddressInfoListtb");
        datagrid.find("div[name='searchColums']").append($("#tempSearchColums div[name='searchColums']").html()).attr("style","text-align: center;");


        $("span:contains('隐藏相关数据')").parents("a.l-btn").show();
        $("span:contains('显示相关数据')").parents("a.l-btn").hide();
        $("input[name='queryHandleStatusTem']").change(function() {

                 var selectedvalue = $("input[name='queryHandleStatusTem']:checked").val();

            $("input[name='isShowData']").val(selectedvalue);
            if(selectedvalue=="0"){
                $("span:contains('显示相关数据')").parents("a.l-btn").show();
                $("span:contains('隐藏相关数据')").parents("a.l-btn").hide();
            }else{
                $("span:contains('隐藏相关数据')").parents("a.l-btn").show();
                $("span:contains('显示相关数据')").parents("a.l-btn").hide();
            }

            tBAddressInfoListsearch();
        });

    });



    //导入
    function ImportXls() {
        openuploadwin('Excel导入', 'tBAddressInfoController.do?upload', "tBAddressInfoList");
    }

    //导出
    function ExportXls() {
        JeecgExcelExport("tBAddressInfoController.do?exportXls","tBAddressInfoList");
    }

    //模板下载
    function ExportXlsByT() {
        JeecgExcelExport("tBAddressInfoController.do?exportXlsByT","tBAddressInfoList");
    }

</script>