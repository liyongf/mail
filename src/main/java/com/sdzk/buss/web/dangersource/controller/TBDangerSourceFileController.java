package com.sdzk.buss.web.dangersource.controller;

import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Lenovo on 17-8-21.
 */

@Scope("prototype")
@Controller
@RequestMapping("/tBDangerSourceFileControl")
public class TBDangerSourceFileController {
    @Autowired
    private SystemService systemService;

    @RequestMapping(params = "filesList")
    public ModelAndView list(HttpServletRequest request) {
        String type = request.getParameter("type");
        request.setAttribute("typecode",type);

        //根据字典编码获取字典名称
        String typename = DicUtil.getTypeNameByCode("fieltype", type);
        request.setAttribute("typename",typename);

        /******************************判断是不是阳光管理员	然后决定是否显示隐藏按钮**************************/
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if(isSunRole){
            request.setAttribute("isSunAdmin", "YGADMIN");
        }else{
            request.setAttribute("isSunAdmin", "common");
        }
        /***************************************************************************************************************/

        return new ModelAndView("com/sdzk/buss/web/dangersource/filesList");
    }
}
