package com.sdzk.buss.web.common.excelverify;

import org.hibernate.Query;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;

import java.util.ArrayList;
import java.util.List;

public class UserExcelVerifyHandler implements IExcelVerifyHandler<TSUser> {
    private SystemService systemService = (SystemService) ApplicationContextUtil.getContext().getBean("systemService");

	@Override
	public ExcelVerifyHanlderResult verifyHandler(TSUser obj) {
		StringBuilder builder = new StringBuilder();
		boolean success = true;
		if(StringUtil.isEmpty(obj.getUserName())){
			builder.append("用户帐号不能为空。");
		} else {
            obj.setUserName(obj.getUserName().trim());
            CriteriaQuery cq = new CriteriaQuery(TSUser.class);
            cq.eq("userName", obj.getUserName());
            cq.add();
            List<TSUser> users = systemService.getListByCriteriaQuery(cq,false);
            if (users != null && users.size() > 0) {
                builder.append("用户帐号["+obj.getUserName()+"]已存在。");
            }
        }
		if(StringUtil.isEmpty(obj.getRealName())){
			builder.append("真实姓名不能为空。");
		}else{
            obj.setRealName(obj.getRealName().trim());
        }
		if(StringUtil.isEmpty(obj.getRoleTemp())){
			builder.append("角色列名称不对，请下载导出模板填写！！");
		} else {
            obj.setRoleTemp(obj.getRoleTemp().trim());
            List<TSRoleUser> userRole = new ArrayList<>();
            for(String roleName : obj.getRoleTemp().split(",")) {
                List<TSRole> roles = systemService.findByProperty(TSRole.class, "roleName", roleName.trim());
                if (roles == null || roles.size() == 0){
                    builder.append("角色["+roleName+"]不存在。");
                } else {
                    TSRoleUser tsRoleUser = new TSRoleUser();
                    tsRoleUser.setTSUser(obj);
                    tsRoleUser.setTSRole(roles.get(0));
                    userRole.add(tsRoleUser);
                }
            }
            obj.setUserRoleList(userRole);
        }
		if (StringUtil.isEmpty(obj.getDepartTemp())) {
			builder.append("组织机构不能为空。");
		} else {
            obj.setDepartTemp(obj.getDepartTemp().trim());
            List<TSUserOrg> userOrgs = new ArrayList<>();
            for(String departName : obj.getDepartTemp().split(",")) {
                CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
                cq.eq("deleteFlag", Short.parseShort("0"));
                cq.eq("departname",departName);
                cq.add();
                List<TSDepart> tsDeparts = systemService.getListByCriteriaQuery(cq, false);
                //List<TSDepart> tsDeparts = systemService.findByProperty(TSDepart.class, "departname", departName);
                if (tsDeparts == null || tsDeparts.size() == 0){
                    builder.append("组织机构["+departName+"]不存在。");
                } else {
                    //TODO 组织机构名称有可能重复
                    TSUserOrg userOrg = new TSUserOrg();
                    userOrg.setTsUser(obj);
                    userOrg.setTsDepart(tsDeparts.get(0));
                    userOrgs.add(userOrg);
                }
            }
            obj.setUserOrgList(userOrgs);
		}
        /*手机
         */
        if(StringUtil.isNotEmpty(obj.getMobilePhone())){
            obj.setMobilePhone(obj.getMobilePhone().trim());
        }
        /*
        邮箱
         */
        if(StringUtil.isNotEmpty(obj.getEmail())){
            obj.setEmail(obj.getEmail().trim());
        }
        /*
        办公电话
         */
        if(StringUtil.isNotEmpty(obj.getOfficePhone())){
            obj.setOfficePhone(obj.getOfficePhone().trim());
        }
        /*
        企业微信账号
         */
        if(StringUtil.isNotEmpty(obj.getWeChatPhone())){
            obj.setWeChatPhone(obj.getWeChatPhone().trim());
        }
		if(StringUtil.isNotEmpty(builder.toString())){
			success = false;
		}
		return new ExcelVerifyHanlderResult(success,builder.toString());
	}



}
