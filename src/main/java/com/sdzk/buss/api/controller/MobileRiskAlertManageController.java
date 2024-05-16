package com.sdzk.buss.api.controller;

import com.sdzk.buss.api.service.ApiServiceI;
import com.sdzk.buss.web.mapmanage.service.TBMapManageServiceI;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.ContextHolderUtils;
import org.jeecgframework.core.util.EhcacheUtil;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.base.Client;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/mobileRiskAlertManageController")
public class MobileRiskAlertManageController {
    @Autowired
    private ApiServiceI apiService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TBMapManageServiceI tbMapManageService;

    private String sessionCache="sessionCache";


    /**
     * 隐患三违分区域预警列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "mobileUndergroundSafetyRiskAlert")
    public ModelAndView mobileUndergroundSafetyRiskAlert(HttpServletRequest request) {
        HttpSession session = ContextHolderUtils.getSession();
        //TODO TOKEN验证
        //TODO token校验
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ModelAndView(new RedirectView("mobile/mobileLogin/login.do"));
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ModelAndView(new RedirectView("mobile/mobileLogin/login.do"));
            }
            EhcacheUtil.put(sessionCache, sessionId, user);
            Client client = new Client();
            client.setIp(IpUtil.getIpAddr(request));
            client.setLogindatetime(new Date());
            client.setUser(user);
            ClientManager.getInstance().addClinet(session.getId(), client);

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            request.setAttribute("date", sdf.format(date));
            String mapPath = tbMapManageService.getCurrentMapPath();
            request.setAttribute("mapPath",mapPath.substring(1));

            return new ModelAndView("com/sdzk/buss/web/riskalert/mobileUndergroundSafetyRiskAlert");
        }catch (Exception e) {
            LogUtil.error("隐患统计查询错误",e);
            return new ModelAndView(new RedirectView("mobile/mobileLogin/login.do"));
        }
    }

}
