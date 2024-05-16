/**
 * 设置magicSuggest下拉框
 * Created by dell on 2017/5/25.
 */
var departUrl="magicSelectController.do?departSelectDataGridMagic";
var userUrl="magicSelectController.do?getUserList";
var addressUrl="magicSelectController.do?getAddressList";
var postUrl="magicSelectController.do?getPostList";
var activityUrl = "magicSelectController.do?getActivityList";
var hazardUrl = "magicSelectController.do?getHazardList";
/**
 * 获取第一类危险源信息
 */
function  getHazardMagicSuggestWithValue(magicSeleter, inputSelecter, value, magicDisable){
    return getMagicSuggest(magicSeleter, inputSelecter, hazardUrl, value, magicDisable,1,'hazard_name',"id",false);
}
function  getHazardMagicSuggest(magicSeleter, inputSelecter){
    return getHazardMagicSuggestWithValue(magicSeleter, inputSelecter, null, false);
}
/**
 * 获取作业活动信息
 */
function  getActivityMagicSuggestWithValue(magicSeleter, inputSelecter, value, magicDisable){
    return getMagicSuggest(magicSeleter, inputSelecter, activityUrl, value, magicDisable,1,'activity_name',"id",false);
}
function  getActivityMagicSuggest(magicSeleter, inputSelecter){
    return getActivityMagicSuggestWithValue(magicSeleter, inputSelecter, null, false);
}
/**
 * 获取工种信息
 */
function  getPostMagicSuggestWithValue(magicSeleter, inputSelecter, value, magicDisable){
    return getMagicSuggest(magicSeleter, inputSelecter, postUrl, value, magicDisable,1,'post_name',"id",false);
}
function  getPostMagicSuggest(magicSeleter, inputSelecter){
    return getPostMagicSuggestWithValue(magicSeleter, inputSelecter, null, false);
}
/**
 * 获取部门信息
 */
function  getDepartMagicSuggestWithValue(magicSeleter, inputSelecter, value, magicDisable){
    return getMagicSuggest(magicSeleter, inputSelecter, departUrl, value, magicDisable,1,'departName',"id",false,['spelling','departName','fullSpelling']);
}
function  getDepartMagicSuggest(magicSeleter, inputSelecter){
    return getDepartMagicSuggestWithValue(magicSeleter, inputSelecter, null, false);
}
/**
 * 获取用户信息
 */
function  getUserMagicSuggestWithValue(magicSeleter, inputSelecter, value, magicDisable){
    return getMagicSuggest(magicSeleter, inputSelecter, userUrl, value, magicDisable,1,"realName","id",false,['spelling','realName','userName','fullSpelling']);
}

function  getUserMagicSuggestWithValueMax5(magicSeleter, inputSelecter, value, magicDisable){
    return getMagicSuggest(magicSeleter, inputSelecter, userUrl, value, magicDisable,20,"realName","id",false,['spelling','realName','userName','fullSpelling']);
}
function  getUserMagicSuggest(magicSeleter, inputSelecter){
    return getUserMagicSuggestWithValue(magicSeleter, inputSelecter, null, false);
}
/**
 * 获取用户信息-保存用户名称及允许用户输入
 */
function  getUserMagicSuggestAllowFreeEntries(magicSeleter, inputSelecter, value, magicDisable){
    return getMagicSuggest(magicSeleter, inputSelecter, userUrl, value, magicDisable,1,"realName","realName",true,['spelling','realName','userName','fullSpelling']);
}
/**
 * 获取施工地点
 */
function  getAddressMagicSuggestWithValue(magicSeleter, inputSelecter, value, magicDisable){
    return getMagicSuggest(magicSeleter, inputSelecter, addressUrl, value, magicDisable,1,"address","id",false);
}

function  getAddressMagicSuggestWithValueMax5(magicSeleter, inputSelecter, value, magicDisable){
    return getMagicSuggest(magicSeleter, inputSelecter, addressUrl, value, magicDisable,20,"address","id",false);
}
function  getAddressMagicSuggest(magicSeleter, inputSelecter){
    return getAddressMagicSuggestWithValue(magicSeleter, inputSelecter, null, false);
}
/**
 * 设置下拉框
 * */
function getMagicSuggest(magicSeleter, inputSelecter, url, value, magicDisable, maxSelection, displayField,valueField,allowFreeEntries,matchFields){
    var opt = {
        data:url,
        allowFreeEntries: allowFreeEntries,
        valueField:valueField,
        placeholder:'输入或选择',
        maxSelection:maxSelection,
        selectFirst: true,
        highlight: false,
        matchField:matchFields,
        displayField:displayField
    };
    if (value != null && value!="" && value!=undefined){
        opt.value=[value];
    }
    var selecter = magicSeleter.magicSuggest(opt);
    $(selecter).on('selectionchange', function(e,m){
        inputSelecter.val(selecter.getValue());
    });
    if(magicDisable){
        selecter.disable();
    }
    return selecter;
}
