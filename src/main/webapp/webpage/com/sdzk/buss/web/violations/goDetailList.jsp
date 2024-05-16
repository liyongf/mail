<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_typegroup_list" class="easyui-layout" fit="true">
<div region="center" style="padding:1px;">
    <t:datagrid name="tBThreeViolationsList" checkbox="false" fitColumns="false" title="三违信息" actionUrl="tBThreeViolationsController.do?datagridfinal&checkOrgIds=${unitCode}&isMonthQuery=${isMonthQuery}&queryMonth=${queryMonth}&vioDate_begin=${startDate}&vioDate_end=${endDate}" idField="id" fit="true" queryMode="group">
       <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="违章时间"  field="vioDate" formatter="yyyy-MM-dd" query="false" queryMode="group" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="违章地点"  field="addressTemp"  queryMode="single" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="违章单位"  field="vioUnitesNameTemp"  queryMode="single" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="工种"  field="workTypeTemp" hidden="true"  queryMode="single" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="违章人员"  field="vioPeopleTemp"  queryMode="single" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="违章分类"  field="vioCategory" dictionary="violaterule_wzfl" query="false"  queryMode="single" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="违章定性"  field="vioQualitative" dictionary="violaterule_wzdx" query="false"  queryMode="single" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="制止人"  field="stopPeopleTemp" hidden="true" queryMode="single" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="查出单位"  field="findUnitsTemp"  hidden="true"  queryMode="single" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="三违事实描述"  field="vioFactDesc" hidden="true"   queryMode="group" sortable="false" width="120"></t:dgCol>

       <t:dgCol title="所属煤矿"  field="belongMine"  hidden="true"  queryMode="group" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="创建人名称"  field="createName" hidden="true"   queryMode="group" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="创建人登录名称"  field="createBy"  hidden="true"  queryMode="group" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="创建日期"  field="createDate" formatter="yyyy-MM-dd" hidden="true"  queryMode="group" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="更新人名称"  field="updateName"  hidden="true"  queryMode="group" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="更新人登录名称"  field="updateBy" hidden="true"    queryMode="group" sortable="false" width="120"></t:dgCol>
       <t:dgCol title="更新日期"  field="updateDate" hidden="true" formatter="yyyy-MM-dd"   queryMode="group" sortable="false" width="120"></t:dgCol>

       <t:dgToolBar title="查看" icon="icon-search" url="tBThreeViolationsController.do?goUpdate" funname="detail"  width="900" height="600"></t:dgToolBar>
    </t:datagrid>
</div>
</div>
 <script type="text/javascript">

     $(document).ready(function(){
         var li_east = 0;
            //给时间控件加上样式
                $("#tBThreeViolationsListtb").find("input[name='vioDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){
                    WdatePicker({
                        dateFmt:'yyyy-MM'
                    });
                });
         $("#tBThreeViolationsListtb").find("input[name='vioDate_begin']").next().css("display","none");
         $("#tBThreeViolationsListtb").find("input[name='vioDate_end']").css("display","none");
                $("#tBThreeViolationsListtb").find("input[name='createDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
                $("#tBThreeViolationsListtb").find("input[name='createDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
                $("#tBThreeViolationsListtb").find("input[name='updateDate_begin']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
                $("#tBThreeViolationsListtb").find("input[name='updateDate_end']").attr("class","Wdate").attr("style","height:20px;width:90px;").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
     });


 </script>