<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script  type="text/javascript" src="plug-in/magicsuggest/magicsuggest-min.js"></script>
<link href="plug-in/magicsuggest/magicsuggest.css" rel="stylesheet">
<script type="text/javascript" src="webpage/common/js/magicSuggestSelect.js"></script>
<div id="tempSearchColums" style="display: none">
    <div name="searchColums" >
        <br>
        月份:&nbsp;&nbsp;<input id="yearMonth" name="yearMonth" value='<fmt:formatDate value='<%=new Date()%>' type="date" pattern="yyyy-MM"/>'>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;"
                  title="单位">单位：</span>
             <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="departnameSuggest" style="width: 130px;height: 15px"></div>
                 <input id="departId" type="hidden" name="departId">
             </span>
        </span>
        <span style="margin-top:5px;display:inline-block;font-size:0;">
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 100px;text-align:right;">人员：</span>
            <span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 190px;text-align:left;text-overflow:ellipsis;-o-text-overflow:ellipsis;">
                <div id="userSelect" style="width: 130px;height: 15px"></div>
                <input class="inuptxt" type="hidden" style="width: 100px" name="userId"
                       id="userId">
             </span>
        </span>
            <br>
        <input  name="queryType" value="user" type="hidden">
           <label>
            <input name="queryTypeTemp" type="radio" value="user" checked="checked">
									人员
           </label>
           <label>
               <input name="queryTypeTemp" type="radio" value="depart">
									单位
           </label>
        </span>
    </div>
</div>
<div class="easyui-layout" fit="true">
    <div region="center" style="padding:0px;border:0px">
        <t:datagrid name="statisticsList"  onDblClick="dbClick" pagination="true" fitColumns="false" title="考核扣分统计" actionUrl="assessmentScoreController.do?statisticsDatagrid" idField="id" fit="true" queryMode="group" >
            <t:dgCol title="主键"  field="id"  hidden="true"  queryMode="group"  width="240"></t:dgCol>
            <t:dgCol title="人员姓名"  field="realName" align="center" queryMode="single" sortable="false"  width="240" query="false"></t:dgCol>
            <t:dgCol title="单位名称"  field="departName" align="center" queryMode="single" sortable="false"  width="240" query="false"></t:dgCol>
            <t:dgCol title="辨识组织考核扣分"  field="score1"  queryMode="group"  width="240" align="center" sortable="false" ></t:dgCol>
            <t:dgCol title="管控措施考核扣分"  field="score2"   queryMode="group"  width="240" align="center" sortable="false" ></t:dgCol>
            <t:dgCol title="管控情况考核扣分"  field="score3"     queryMode="group"  width="240" align="center" sortable="false" ></t:dgCol>
            <t:dgCol title="隐患治理考核扣分"  field="score4"   queryMode="group"  width="240" align="center" sortable="false" ></t:dgCol>
            <t:dgCol title="管控组织考核扣分"  field="score5"     queryMode="group"  width="240" align="center" sortable="false" ></t:dgCol>
            <t:dgCol title="月份"  field="createDate" formatter="yyyy-MM"  queryMode="single" sortable="false" hidden="true" query="true" width="240" ></t:dgCol>
            <t:dgCol title="操作" field="opt" width="150" align="center"></t:dgCol>
            <t:dgFunOpt funname="goDetail(id)" title="查看详情"  urlclass="ace_button" ></t:dgFunOpt>
        </t:datagrid>
    </div>
</div>
<script type="text/javascript" src="plug-in/showvalue/showvalue.js"></script>
<script type="text/javascript">
    $(document).ready(function(){
        $("#statisticsList").datagrid('hideColumn', "departName");




        var datagrid = $("#statisticsListtb");
        datagrid.find("div[name='searchColums']>form>span:first").after($("#tempSearchColums").html()).attr("style","text-align: center;");
        $("#statisticsListForm").find("input[name='createDate']").attr("class", "Wdate").attr("style", "height:30px;width:156px;").click(function () {
            WdatePicker({
                dateFmt: 'yyyy-MM',
            });
        });
        datagrid.find("div[name='searchColums']>form>span:first").remove();
        datagrid.find("div[name='searchColums']").attr("style","text-align: center;");
        $("#tempSearchColums").empty();
        getDepartMagicSuggest($("#departnameSuggest"), $("#departId"));
        getUserMagicSuggest($("#userSelect"), $("#userId"));
        $("#yearMonth").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM'});});
        $("input[name='queryTypeTemp']").change(function() {
            var selectedvalue = $("input[name='queryTypeTemp']:checked").val();
            $("input[name='queryType']").val(selectedvalue);
            if(selectedvalue=="user"){

                $("#statisticsList").datagrid('showColumn', "realName");
                $("#statisticsList").datagrid('showColumn', "score1");
                $("#statisticsList").datagrid('showColumn', "score2");
                $("#statisticsList").datagrid('showColumn', "score5");
                $("#statisticsList").datagrid('hideColumn', "departName");
            }else if(selectedvalue=="depart"){
                $("#statisticsList").datagrid('hideColumn', "realName");
                $("#statisticsList").datagrid('hideColumn', "score1");
                $("#statisticsList").datagrid('hideColumn', "score2");
                $("#statisticsList").datagrid('hideColumn', "score5");
                $("#statisticsList").datagrid('showColumn', "departName");
            }
            statisticsListsearch();
        });


    });

    function goDetail(id) {
        var selectedvalue = $("input[name='queryTypeTemp']:checked").val();
        var yearMonth = $("#yearMonth").val();
        if(selectedvalue == null || selectedvalue == '' || selectedvalue == undefined){
            selectedvalue = "user";
        }
        $.ajax({
            url : "assessmentScoreController.do?isGoDetail",
            type : 'post',
            cache : false,
            async: true,
            success : function(data) {
                var d = $.parseJSON(data);
                if (d=="1") {
                    var url = "assessmentScoreController.do?goDetail&selectedvalue="+selectedvalue+"&yearMonth="+yearMonth+"&id="+id;
                    createdetailwindow('考核详情',url,800,500);
                }else{
                    tip("考核扣分配置未填写");
                }
            }
        });
    }
</script>