package com.sdzk.buss.web.common.taskProvince;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.taskProvince.service.GetDataService;
import net.sf.json.JSONArray;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2019/1/9.
 */
@Component("reportLoginCountNum")
public class ReportLoginCountNum {
    @Autowired
    private SystemService systemService;
    @Autowired
    private GetDataService getDataService;
    public void run(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报登录统计开始===================");//调整
        try {
            String sql = "SELECT count(id) logonCount, LEFT (operatetime, 7) uploadMonth   FROM t_s_log WHERE loglevel = '1' and operatetype = '1' GROUP BY LEFT (operatetime, 7)";//调整
            List<Map<String,Object>> retList =  systemService.findForJdbc(sql);
            JSONArray jsonArray = JSONArray.fromObject(retList);
            if(jsonArray.size()>0){
                String reportUrl = ResourceUtil.getConfigByName("loginCountNumReportToProvince");//调整
                getDataService.postData(jsonArray,reportUrl);
            }
        } catch (Exception e) {
            org.jeecgframework.core.util.LogUtil.error("定时上报登录统计失败", e);//调整
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报登录统计结束===================");//调整
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报登录统计总耗时"+times+"毫秒");//调整
    }
}
