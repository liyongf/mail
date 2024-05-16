package com.sdzk.buss.web.system.controller;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by djs on 2017/3/21.
 */
@Scope("prototype")
@Controller
@RequestMapping("/sessionController")
public class SessionController extends BaseController {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @RequestMapping(params = "checkSession")
    @ResponseBody
    public AjaxJson checkSession(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        TSUser user = ResourceUtil.getSessionUserName();
        if(user == null){
            message = "会话已过期";
        }else{
            message = "";
        }
        j.setMsg(message);
        return j;
    }
}
