package com.sdzk.buss.api.controller;

import com.sdzk.buss.api.model.ApiResultJson;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.EhcacheUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @user weizhen
 * 版本更新
 */
@Controller
@RequestMapping("/mobile")
public class MobileAppUpdateController {

    @Autowired
    private SystemService systemService;
    private String sessionCache="sessionCache";

    /**
     * APP版本更新
     * @return
     */
    @RequestMapping("/appUpdate")
    @ResponseBody
    public ApiResultJson appUpdate(HttpServletRequest request){
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser) EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            //每次访问都去读取，避免每次版本更新更改版本号都需要重启服务
            String versionCode = systemService.getConfigFromDb("mobile_version_code");
            String version = systemService.getConfigFromDb("mobile_version_name");
            String versionDesc = systemService.getConfigFromDb("mobile_version_desc");
            String appName = systemService.getConfigFromDb("mobile_apk_name");
            String updateType = appName.split("\\.")[1];
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("versionCode",versionCode);
            map.put("version",version);
            map.put("versionDesc",versionDesc);
            map.put("appName",appName);
            map.put("updateType",updateType);
            return new ApiResultJson(map);
        } catch (Exception e) {
            LogUtil.error("版本更新错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }


}