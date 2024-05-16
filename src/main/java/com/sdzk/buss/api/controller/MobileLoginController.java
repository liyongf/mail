package com.sdzk.buss.api.controller;

/**
* Created by zsc on 17-9-22.
*/

import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.api.service.ApiServiceI;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* Created by dell on 2017/7/8.
* 同步隐患接口
*/
@Controller
@RequestMapping("/mobile/mobileLogin")
public class MobileLoginController {
    @Autowired
    private ApiServiceI apiService;
    @Autowired
    private SystemService systemService;
    /**
     * 手机登录接口
     *
     * @return
     */
    @RequestMapping("login")
    @ResponseBody
    public ApiResultJson login(String token, HttpServletRequest request){
    //TODO token验证之后加
        try {
            String longyun = ResourceUtil.getConfigByName("longyun");
            if(!longyun.equals("true")){
                String temp = request.getParameter("key");
                String key = PasswordUtil.encrypt("admin", temp, PasswordUtil.getStaticSalt());
                String sql = "select id from t_b_app_key where app_key = '"+key+"'";
                List<String> list = systemService.findListbySql(sql);
              /*  if (list == null || list.size() == 0) {
                    return new ApiResultJson(ApiResultJson.CODE_202,"系统地址不正确",null);
                }*/
            }
            TSUser user = new TSUser();
            user.setUserName(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));
            if (StringUtils.isBlank(user.getUserName()) ||
                    StringUtils.isBlank(user.getPassword())) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户名密码不能为空",null);
            }
            return apiService.mobileLogin(user);
        } catch (Exception e) {
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }


    @RequestMapping("logout")
    @ResponseBody
    public ApiResultJson logout(String token, HttpServletRequest request){
    //TODO token验证之后加
        try {
            return apiService.mobileLogout(request.getParameter("sessionId"));
        } catch (Exception e) {
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }


    /**
     * 手机修改密码接口
     *
     * @return
     */
    @RequestMapping("changePassword")
    @ResponseBody
    public ApiResultJson savenewpwd(String token, HttpServletRequest request) {
    //TODO token验证之后加
        try {
            TSUser user = new TSUser();
            user.setUserName(request.getParameter("username"));
            user.setPassword(request.getParameter("password"));
            String newPassword = request.getParameter("newPassword");
            if (StringUtils.isBlank(user.getUserName()) ||
                    StringUtils.isBlank(user.getPassword())) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户名密码不能为空",null);
            }
            if (StringUtils.isBlank(newPassword)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"新密码不能为空",null);
            }
            return apiService.changePassword(user,newPassword);
        } catch (Exception e) {
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }


}

