<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>

<div id="tempSearchColums" style="display: none">
    <div name="searchColums" style="z-index: 9999">
         <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">地点：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">

                <div id="addressSelect" style="width: 130px;height: 15px"></div>
                <input id="addressId" type="hidden" name="addressId">
                 </span>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="tBLightAddressList" checkbox="true" pagination="true" fitColumns="true" title="三违信息" actionUrl="tBLightAddressController.do?datagrid" idField="id" fit="true" queryMode="group" sortName="createDate" sortOrder="desc">
   <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="120" align="center"></t:dgCol>
   <t:dgCol title="灯标识"  field="lightId" query="true"  queryMode="single" sortable="false" width="80" align="center"></t:dgCol>
   <t:dgCol title="风险点"  field="addressName" query="false"  queryMode="group" sortable="false" width="80" align="center"></t:dgCol>

   <t:dgToolBar title="录入" icon="icon-add" url="tBLightAddressController.do?goAdd" funname="add" operationCode="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="tBLightAddressController.do?goUpdate" funname="update" operationCode="update" ></t:dgToolBar>
   <t:dgToolBar title="批量删除"  icon="icon-remove" url="tBLightAddressController.do?doBatchDel" funname="deleteALLSelect" operationCode="batchdelete" ></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="tBLightAddressController.do?goUpdate" funname="detail" operationCode="detail" ></t:dgToolBar>

  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(document).ready(function(){
    //初始化查询条件
     $("#tBLightAddressListForm>span:last").after($("#tempSearchColums div[name='searchColums']").html());
     $("#tempSearchColums").empty();
     var addressSelect = getAddressMagicSuggest($('#addressSelect'), $("#addressId"));

     //重置按钮时间绑定
     $("a[iconcls='icon-reload']").on("click", function(){
         addressSelect.clear();
     });
 });

 </script>