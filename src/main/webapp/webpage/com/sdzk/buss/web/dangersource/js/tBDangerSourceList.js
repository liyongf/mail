/**
 * 标记重大风险标记
 * */
function markIsMajor(){
    var rowsData = $('#tBDangerSourceList').datagrid('getSelections');
    if(rowsData == null || rowsData.length < 1){
        tip("请选择需要标记的条目");
    }else{
        var ids = new Array();
        for (var i = 0; i < rowsData.length; i++) {
            if (rowsData[i].isMajor=='1') {
                tip("只能选择未设置重大风险标记的条目");
                return;
            }
            ids.push(rowsData[i].id);
        }
        var id = ids.join(",");
        createdialog('确认 ', '确定要设置所选中记录为重大风险吗?', "tBDangerSourceController.do?doIsMajor&isMajor=1&ids="+id,"tBDangerSourceList");
    }
}
/**
 * 标记重大风险标记
 * */
function cancelIsMajor(){
    var rowsData = $('#tBDangerSourceList').datagrid('getSelections');
    if(rowsData == null || rowsData.length < 1){
        tip("请选择需要标记的条目");
    }else{
        var ids = new Array();
        for (var i = 0; i < rowsData.length; i++) {
            if (rowsData[i].isMajor=='0') {
                tip("只能选择已设置重大风险标记的条目");
                return;
            }
            ids.push(rowsData[i].id);
        }
        var id = ids.join(",");
        createdialog('确认 ', '确定要撤除所选中记录的重大风险标记吗?', "tBDangerSourceController.do?doIsMajor&isMajor=0&ids="+id,"tBDangerSourceList");
    }
}

function formatIsMajor (value, rec, index) {
    if (value == '1') {
        return '是';
    } else {
        return '否';
    }
}