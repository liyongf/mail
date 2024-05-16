<%@ page import="org.jeecgframework.core.util.ResourceUtil" %>
<%@ page import="org.jeecgframework.web.system.pojo.base.TSUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    $(function() {
        $("#docSourceIdListPanel").panel({
            title :'文件来源分类列表'
        });
        $("#clauseDocListPanel").panel({
            title :'条款文件列表'
        });


        $('#docSourceId').tree({
            checkbox : false,
            url : 'tBDocumentSourceController.do?docSourceComboTree',
            onLoadSuccess : function(node) {
                expandAll();
            },
            onClick: function(node){
                var isLeaf = $('#docSourceId').tree('isLeaf', node.target);
                if(!isLeaf){
                    $('#docSourceId').combotree('clear');
                }else{
                    var id = encodeURI(node.id);
                    $("input[name='docSourceId']").val(id);
                    $("#clauseDocListPanel").panel(
                        {
                            title :'条款文件列表：'+node.text
                            /*,
                            href:"tBDangerSourceFileControl.do?clauseDocList&typecode=termsFile&docSourceId="+id*/
                        }
                    );
                    //$('#clauseDocListPanel').panel("refresh");
                    //由于这里动态修改datagrid的url属性后，传入后台的DataGrid中field属性丢失，这里重新传入field属性
                    var field="";
                    $(".datagrid-header-row").find("td").each(function(){
                        if(typeof($(this).attr("field")) != "undefined" && $(this).attr("field") != 'opt'){
                            field += $(this).attr("field") +",";
                        }
                    });
                    $('#fList').datagrid({
                        url:'systemController.do?documentListDoc&typecode=file&docSourceId='+id+'&field='+field
                    });
                }
            }
        });

        //页面加载完成后，默认显示全部分类的文件
        /*$("#clauseDocListPanel").panel(
            {
                title :'条款文件列表',
                href:"tBDangerSourceFileControl.do?clauseDocList&typecode=termsFile"
            }
        );
        $('#clauseDocListPanel').panel("refresh" );*/
    });

    function expandAll() {
        var node = $('#docSourceId').tree('getSelected');
        if (node) {
            $('#docSourceId').tree('expandAll', node.target);
        } else {
            $('#docSourceId').tree('expandAll');
        }
    }
</script>
<div class="easyui-layout" fit="true">
    <div region="west"style="width: 240px;padding: 1px;" split="true">
        <div class="easyui-panel" style="padding: 10px;" fit="true" border="false" id="docSourceIdListPanel">
            <ul id="docSourceId"></ul>
        </div>
    </div>
    <div region="center" >
        <div class="easyui-panel" style="padding: 5px;" fit="true" border="false" id="clauseDocListPanel">
            <%
                TSUser user = ResourceUtil.getSessionUserName();
            %>
            <script type="text/javascript">
                function doDeleteDocument(id,rowIndex){
                    var rows = $("#fList").datagrid('getRows');
                    var userName = rows[rowIndex]["TSUser.userName"];
                    if("<%=user.getUserName()%>" == "admin"){
                        delObj('systemController.do?delDocument&id='+id+'','fList');
                    }else if(userName == "<%=user.getUserName()%>"){
                        delObj('systemController.do?delDocument&id='+id+'','fList');
                    }else{
                        tip("只能删除自己上传的文件");
                    }
                }

                function addFun(title,url, id) {
                   /* var rowData = $('#'+id).datagrid('getSelected');
                    if (rowData) {
                        url += '&docSourceId='+rowData.id;
                    }*/
                    url += '&docSourceId='+$("input[name='docSourceId']").val();
                    add(title,url,'fList',500,300);
                }
            </script>
            <input type="hidden" name="docSourceId" />
            <t:datagrid name="fList" title="条款文件" actionUrl="systemController.do?documentListDoc&docSourceId=${docSourceId}&typecode=file" idField="id" fit="true" queryMode="group">
                <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
                <t:dgCol title="标题" field="documentTitle" width="10" ></t:dgCol>
                <t:dgCol title="文件名" field="attachmenttitle"  width="10"></t:dgCol>
                <t:dgCol title="文档分类" field="documentSource.docSourceName"  width="4"></t:dgCol>
                <t:dgCol title="上传人"  field="TSUser.realName"  width="4"></t:dgCol>
                <t:dgCol title="上传人编号" hidden="false"  field="TSUser.userName"  width="4"></t:dgCol>
                <t:dgCol title="上传时间" formatter="yyyy-MM-dd hh:mm"  field="createdate"  width="4"></t:dgCol>
                <t:dgCol title="类名" field="subclassname" hidden="true"></t:dgCol>
                <t:dgCol title="操作" field="opt" width="4"></t:dgCol>
                <t:dgDefOpt url="commonController.do?viewFile&fileid={id}&subclassname={subclassname}" title="下载"></t:dgDefOpt>
                <t:dgFunOpt funname="doDeleteDocument(id)" title="删除" urlclass="ace_button"  urlfont="fa-trash-o"></t:dgFunOpt>
                <t:dgToolBar title="文件录入" icon="icon-add" funname="addFun" url="systemController.do?addFiles&typecode=file" operationCode="add"></t:dgToolBar>
            </t:datagrid>
        </div>
    </div>
</div>
</div>
