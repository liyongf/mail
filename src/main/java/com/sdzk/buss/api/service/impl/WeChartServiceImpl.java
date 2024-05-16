package com.sdzk.buss.api.service.impl;

import com.sdzk.buss.api.service.WeChartServiceI;
import net.sf.json.JSONObject;
import org.jeecgframework.core.util.HttpClientUtils;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("weChartService")
@Transactional
public class WeChartServiceImpl implements WeChartServiceI {
    @Autowired
    private SystemService systemService;

    //图灵机器人apiUrl和apiKey
    private String apiUrl = "http://www.tuling123.com/openapi/api";
    private String apiKey = "6fb9650a90fe4353acb2bc1dade8e16b";

    /**
     *  图灵机器人交互
     * */
    public String tuLing(String message){
        String returnMsg = "";
        JSONObject jo = new JSONObject();

        jo.put("key", apiKey);
        jo.put("info", message);
        jo.put("userid", "163039");

        try {
            returnMsg = HttpClientUtils.get(apiUrl, jo);
        } catch (NetServiceException e) {
            e.printStackTrace();
        }

        System.out.println("图灵机器人返回信息：" + returnMsg);
        return returnMsg;
    }

}
