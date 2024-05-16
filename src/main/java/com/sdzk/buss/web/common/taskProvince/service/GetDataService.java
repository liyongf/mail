package com.sdzk.buss.web.common.taskProvince.service;

import org.jeecgframework.core.common.model.json.AjaxJson;

import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2019/1/3.
 */
public interface GetDataService {
    public  List<Map<String,Object>> getData(Map<String, String> columnNeed, String tableName, List<String> conditions);
    public AjaxJson postData(Object data, String reportUrl);
}
