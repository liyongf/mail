package org.jeecgframework.web.system.controller.core;

import com.alibaba.fastjson.JSON;
import com.sdzk.buss.web.common.excelverify.UserExcelVerifyHandler;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostUser;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboBox;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.ValidForm;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.enums.SysThemesEnum;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.DataTableReturn;
import org.jeecgframework.tag.vo.datatable.DataTables;
import org.jeecgframework.web.system.entity.TSUserExportDicVO;
import org.jeecgframework.web.system.manager.ClientManager;
import org.jeecgframework.web.system.pojo.UserVo;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @ClassName: UserController
 * @Description: TODO(用户管理处理类)
 * @author 张代浩
 */
//@Scope("prototype")
@Controller
@RequestMapping("/userController")
public class UserController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(UserController.class);
    private static final String defaultPassword = "123456";

	private UserService userService;
	private SystemService systemService;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	/**
	 * 菜单列表
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "menu")
	public void menu(HttpServletRequest request, HttpServletResponse response) {
		SetListSort sort = new SetListSort();
		TSUser u = ResourceUtil.getSessionUserName();
		// 登陆者的权限
		Set<TSFunction> loginActionlist = new HashSet<TSFunction>();// 已有权限菜单
		List<TSRoleUser> rUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", u.getId());
		for (TSRoleUser ru : rUsers) {
			TSRole role = ru.getTSRole();
			List<TSRoleFunction> roleFunctionList = systemService.findByProperty(TSRoleFunction.class, "TSRole.id", role.getId());
			if (roleFunctionList.size() > 0) {
				for (TSRoleFunction roleFunction : roleFunctionList) {
					TSFunction function = (TSFunction) roleFunction.getTSFunction();
					loginActionlist.add(function);
				}
			}
		}
		List<TSFunction> bigActionlist = new ArrayList<TSFunction>();// 一级权限菜单
		List<TSFunction> smailActionlist = new ArrayList<TSFunction>();// 二级权限菜单
		if (loginActionlist.size() > 0) {
			for (TSFunction function : loginActionlist) {
				if (function.getFunctionLevel() == 0) {
					bigActionlist.add(function);
				} else if (function.getFunctionLevel() == 1) {
					smailActionlist.add(function);
				}
			}
		}
		// 菜单栏排序
		Collections.sort(bigActionlist, sort);
		Collections.sort(smailActionlist, sort);
		String logString = ListtoMenu.getMenu(bigActionlist, smailActionlist);
		// request.setAttribute("loginMenu",logString);
		try {
			response.getWriter().write(logString);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				response.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 用户列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "user")
	public String user(HttpServletRequest request) {
		// 给部门查询条件中的下拉框准备数据
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		request.setAttribute("departsReplace", RoletoJson.listToReplaceStr(departList, "departname", "id"));
		departList.clear();
		String gucheng = ResourceUtil.getConfigByName("gucheng");
		request.setAttribute("gucheng",gucheng);
		return "system/user/userList";
	}

	/**
	 * 用户信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "userinfo")
	public String userinfo(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		request.setAttribute("user", user);
		return "system/user/userinfo";
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping(params = "changepassword")
	public String changepassword(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		request.setAttribute("user", user);
		String gucheng = ResourceUtil.getConfigByName("gucheng");
		request.setAttribute("gucheng",gucheng);
		return "system/user/changepassword";
	}

	/**
	 * 修改密码
	 * 
	 * @return
	 */
	@RequestMapping(params = "savenewpwd")
	@ResponseBody
	public AjaxJson savenewpwd(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		TSUser user = ResourceUtil.getSessionUserName();
		String userid=oConvertUtils.getString(request.getParameter("id"));
		if(userid!=null){
			user = systemService.getEntity(TSUser.class, userid);
		}
		String password = oConvertUtils.getString(request.getParameter("password"));
		String newpassword = oConvertUtils.getString(request.getParameter("newpassword"));
		String pString = PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt());
		if (!pString.equals(user.getPassword())) {
			j.setMsg("原密码不正确");
			j.setSuccess(false);
		} else {
			try {
				user.setPassword(PasswordUtil.encrypt(user.getUserName(), newpassword, PasswordUtil.getStaticSalt()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			systemService.updateEntitie(user);
			j.setMsg("修改成功");

		}
		return j;
	}

	/**
	 * 
	 * 修改用户密码
	 * @author Chj
	 */
	
	@RequestMapping(params = "changepasswordforuser")
	public ModelAndView changepasswordforuser(TSUser user, HttpServletRequest req) {
		String userid=req.getParameter("ids");
		if (StringUtil.isNotEmpty(userid)) {
			user = systemService.getEntity(TSUser.class, userid);
			req.setAttribute("user", user);
			idandname(req, user);
			String gucheng = ResourceUtil.getConfigByName("gucheng");
			req.setAttribute("gucheng",gucheng);
			return new ModelAndView("system/user/changepassword");
		}
		if (StringUtil.isNotEmpty(user.getId())) {
			user = systemService.getEntity(TSUser.class, user.getId());
			req.setAttribute("user", user);
			idandname(req, user);
			//System.out.println(user.getPassword()+"-----"+user.getRealName());
		}
		String gucheng = ResourceUtil.getConfigByName("gucheng");
		req.setAttribute("gucheng",gucheng);
		return new ModelAndView("system/user/adminchangepwd");
	}

	@RequestMapping(params = "mima")
	@ResponseBody
	public AjaxJson mima(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
			try {
				List<String> userId = systemService.findListbySql("select id from t_s_base_user");
				for(String id :userId){
					TSUser user = systemService.get(TSUser.class,id);
					if(user!=null){
						user.setPassword(PasswordUtil.encrypt(user.getUserName(),"gcmk@123", PasswordUtil.getStaticSalt()));
						systemService.updateEntitie(user);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			j.setMsg("修改成功");
		return j;
	}
	
	/**
	 * 重置密码
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "savenewpwdforuser")
	@ResponseBody
	public AjaxJson savenewpwdforuser(HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();
		String id = oConvertUtils.getString(req.getParameter("id"));
		String password = oConvertUtils.getString(req.getParameter("password"));
		if (StringUtil.isNotEmpty(id)) {
			TSUser users = systemService.getEntity(TSUser.class,id);
			//System.out.println(users.getUserName());
			users.setPassword(PasswordUtil.encrypt(users.getUserName(), password, PasswordUtil.getStaticSalt()));
			users.setStatus(Globals.User_Normal);
			users.setActivitiSync(users.getActivitiSync());
			systemService.updateEntitie(users);	
			message = "用户: " + users.getUserName() + "密码重置成功";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} 
		
		j.setMsg(message);

		return j;
	}
	/**
	 * 锁定账户
	
	 * 
	 * @author pu.chen
	 */
	@RequestMapping(params = "lock")
	@ResponseBody
	public AjaxJson lock(String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		String message = null;
		TSUser user = systemService.getEntity(TSUser.class, id);
		if("admin".equals(user.getUserName())){
			message = "超级管理员[admin]不可操作";
			j.setMsg(message);
			return j;
		}
		String lockValue=req.getParameter("lockvalue");

		user.setStatus(new Short(lockValue));
		try{
		userService.updateEntitie(user);
		if("0".equals(lockValue)){
			message = "用户：" + user.getUserName() + "锁定成功!";
		}else if("1".equals(lockValue)){
			message = "用户：" + user.getUserName() + "激活成功!";
		}
		systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			message = "操作失败!";
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 得到角色列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "role")
	@ResponseBody
	public List<ComboBox> role(HttpServletResponse response, HttpServletRequest request, ComboBox comboBox) {
		String id = request.getParameter("id");
		List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
		List<TSRole> roles = new ArrayList<TSRole>();
		if (StringUtil.isNotEmpty(id)) {
			List<TSRoleUser> roleUser = systemService.findByProperty(TSRoleUser.class, "TSUser.id", id);
			if (roleUser.size() > 0) {
				for (TSRoleUser ru : roleUser) {
					roles.add(ru.getTSRole());
				}
			}
		}
		List<TSRole> roleList = systemService.getList(TSRole.class);
		comboBoxs = TagUtil.getComboBox(roleList, roles, comboBox);

		roleList.clear();
		roles.clear();

		return comboBoxs;
	}

	/**
	 * 得到部门列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "depart")
	@ResponseBody
	public List<ComboBox> depart(HttpServletResponse response, HttpServletRequest request, ComboBox comboBox) {
		String id = request.getParameter("id");
		List<ComboBox> comboBoxs = new ArrayList<ComboBox>();
		List<TSDepart> departs = new ArrayList();
		if (StringUtil.isNotEmpty(id)) {
			TSUser user = systemService.get(TSUser.class, id);
            // todo zhanggm 获取指定用户的组织机构列表
            List<TSDepart[]> resultList = systemService.findHql("from TSDepart d,TSUserOrg uo where d.id=uo.orgId and uo.id=?", id);
            for (TSDepart[] departArr : resultList) {
                departs.add(departArr[0]);
            }
        }
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		comboBoxs = TagUtil.getComboBox(departList, departs, comboBox);
		return comboBoxs;
	}
	@RequestMapping(params = "createSpell")
	@ResponseBody
	public AjaxJson createSpell(HttpServletRequest request) {

		String saveOld = request.getParameter("saveOld");
		AjaxJson j = new AjaxJson();
		CriteriaQuery cq = new CriteriaQuery(TSUser.class);
		try{
			Short[] userstate = new Short[]{Globals.User_Normal};
			cq.in("status", userstate);
			cq.eq("deleteFlag", Globals.Delete_Normal);
		}catch(Exception e){
			e.printStackTrace();
		}
		cq.add();
		List<TSUser> userList = systemService.getListByCriteriaQuery(cq,false);
		if(userList != null && !userList.isEmpty()){
			boolean isSave = Boolean.parseBoolean(saveOld);
			for(TSUser user : userList){
				String userName = user.getRealName();
				String fullSpell = com.sdzk.buss.web.common.utils.PinyinUtil.getPingYin((userName).toLowerCase());
				String spell = com.sdzk.buss.web.common.utils.PinyinUtil.getPinYinHeadChar((userName).toLowerCase());
				if(!isSave) {
					user.setFullSpelling(fullSpell.replaceAll(" ", ""));
					user.setSpelling(spell.replaceAll(" ", ""));
				}else{
					if(StringUtils.isBlank(user.getSpelling())){
						user.setSpelling(spell.replaceAll(" ", ""));
					}
					if(StringUtils.isBlank(user.getFullSpelling())){
						user.setFullSpelling(fullSpell.replaceAll(" ", ""));
					}
				}
				systemService.saveOrUpdate(user);
			}
		}
		j.setMsg("创建成功");

		return j;
	}
	/**
	 * easyuiAJAX用户列表请求数据 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	public void datagrid(TSUser user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		user.setRealName(null);
		user.setUserName(null);
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);

        Short[] userstate = new Short[]{Globals.User_Normal, Globals.User_ADMIN, Globals.User_Forbidden};
        cq.in("status", userstate);
//        cq.eq("deleteFlag", Globals.Delete_Normal);

        String orgIds = request.getParameter("orgIds");
        List<String> orgIdList = extractIdListByComma(orgIds);
        // 获取 当前组织机构的用户信息
        if (!CollectionUtils.isEmpty(orgIdList)) {
            CriteriaQuery subCq = new CriteriaQuery(TSUserOrg.class);
            subCq.setProjection(Property.forName("tsUser.id"));
            subCq.in("tsDepart.id", orgIdList.toArray());
            subCq.add();

            cq.add(Property.forName("id").in(subCq.getDetachedCriteria()));
        }

		//用户账号查询
		String username = request.getParameter("userName");
		if (StringUtil.isNotEmpty(username)) {
			cq.like("userName", "%"+username+"%");
		}
		//用户名称查询
		String realname = request.getParameter("realName");
		if (StringUtil.isNotEmpty(realname)) {
			cq.like("realName", "%"+realname+"%");
		}
		//组织机构查询
		String orgname = request.getParameter("userOrgList.tsDepart.departname");
		if (StringUtil.isNotEmpty(orgname)) {
			cq.add(Restrictions.sqlRestriction("this_.id in (select org.user_id from t_s_user_org org JOIN t_s_depart depart on org.org_id = depart.ID where depart.departname like'%"+orgname+"%')"));
		}

        cq.add();
        this.systemService.getDataGridReturn(cq, true);

        List<TSUser> cfeList = new ArrayList<TSUser>();
        for (Object o : dataGrid.getResults()) {
            if (o instanceof TSUser) {
                TSUser cfe = (TSUser) o;
                if (cfe.getId() != null && !"".equals(cfe.getId())) {
                    List<TSRoleUser> roleUser = systemService.findByProperty(TSRoleUser.class, "TSUser.id", cfe.getId());
                    if (roleUser.size() > 0) {
                        StringBuffer roleName = new StringBuffer();
                        for (TSRoleUser ru : roleUser) {
                            if (StringUtil.isNotEmpty(roleName.toString())){
                                roleName.append(",").append(ru.getTSRole().getRoleName());
                            } else {
                                roleName.append(ru.getTSRole().getRoleName());
                            }
                        }
                        cfe.setUserKey(roleName.toString());
                    }
                }
                cfeList.add(cfe);
            }
        }

        TagUtil.datagrid(response, dataGrid);
    }

	/**
	 * 用户删除选择对话框
	 * 
	 * @return
	 */
	@RequestMapping(params = "deleteDialog")
	public String deleteDialog(TSUser user,HttpServletRequest request) {
		request.setAttribute("user", user);
		return "system/user/user-delete";
	} 
	
	@RequestMapping(params = "delete")
	@ResponseBody
	public AjaxJson delete(TSUser user, HttpServletRequest req) {

//        String deleteType = "delete";       //默认全部使用逻辑删除，物理删除已被废弃
//
//		if (deleteType.equals("delete")) {
//			return this.del(user, req);
//		}else if (deleteType.equals("deleteTrue")) {
//			return this.trueDel(user, req);
//		}else{
//			AjaxJson j = new AjaxJson();
//
//			j.setMsg("删除逻辑参数异常,请重试.");
//			return j;
//		}

		AjaxJson j = new AjaxJson();
		try {
			String delete_flag = "0";
			String msg = "用户已置为有效";
			if (delete_flag.equals(ResourceUtil.getParameter("deleteFlag"))) {
				delete_flag = "1";
				msg = "用户已置为无效";
			}
			systemService.executeSql("update t_s_base_user set delete_flag="+delete_flag+" where id = '"+user.getId()+"'");
			j.setMsg(msg);
		} catch (Exception e) {
			j.setMsg("删除逻辑参数异常,请重试.");
		}
		return j;
	}

	/**
	 * 用户信息录入和更新
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TSUser user, HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if("admin".equals(user.getUserName())){
			message = "超级管理员[admin]不可删除";
			j.setMsg(message);
			return j;
		}
        user = systemService.getEntity(TSUser.class, user.getId());
//		List<TSRoleUser> roleUser = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
		if (!user.getStatus().equals(Globals.User_ADMIN)) {

			user.setDeleteFlag(Globals.Delete_Forbidden);
			userService.updateEntitie(user);
			message = "用户：" + user.getUserName() + "删除成功";

			
/**
			if (roleUser.size()>0) {
				// 删除用户时先删除用户和角色关系表
				delRoleUser(user);

                systemService.executeSql("delete from t_s_user_org where user_id=?", user.getId()); // 删除 用户-机构 数据

                userService.delete(user);
				message = "用户：" + user.getUserName() + "删除成功";
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			} else {
				userService.delete(user);
				message = "用户：" + user.getUserName() + "删除成功";
			}
**/	
		} else {
			message = "超级管理员不可删除";
		}

		j.setMsg(message);
		return j;
	}
	
	/**
	 * 真实删除
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "trueDel")
	@ResponseBody
	public AjaxJson trueDel(TSUser user, HttpServletRequest req) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if("admin".equals(user.getUserName())){
			message = "超级管理员[admin]不可删除";
			j.setMsg(message);
			return j;
		}
		user = systemService.getEntity(TSUser.class, user.getId());
		try {
			message = userService.trueDel(user);
		} catch (Exception e) {
			e.printStackTrace();
			message ="删除失败";
		}


		j.setMsg(message);
		return j;
	}

	/**
	 * 检查用户名
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "checkUser")
	@ResponseBody
	public ValidForm checkUser(HttpServletRequest request) {
		ValidForm v = new ValidForm();
		String userName=oConvertUtils.getString(request.getParameter("param"));
		String code=oConvertUtils.getString(request.getParameter("code"));
		List<TSUser> roles=systemService.findByProperty(TSUser.class,"userName",userName);
		if(roles.size()>0&&!code.equals(userName))
		{
			v.setInfo("用户名已存在");
			v.setStatus("n");
		}
		return v;
	}

	/**
	 * 用户录入
	 * 
	 * @param user
	 * @param req
	 * @return
	 */

	@RequestMapping(params = "saveUser")
	@ResponseBody
	public AjaxJson saveUser(HttpServletRequest req, TSUser user) {
		String message = null;
		AjaxJson j = new AjaxJson();
		// 得到用户的角色
		String roleid = oConvertUtils.getString(req.getParameter("roleid"));
		// 得到用户工种id
		String postId = oConvertUtils.getString(req.getParameter("postId"));
		String password = oConvertUtils.getString(req.getParameter("password"));
		if (StringUtil.isNotEmpty(user.getId())) {
			TSUser users = systemService.getEntity(TSUser.class, user.getId());
			users.setEmail(user.getEmail());
			users.setOfficePhone(user.getOfficePhone());
			users.setMobilePhone(user.getMobilePhone());
			users.setWeChatPhone(user.getWeChatPhone());
            systemService.executeSql("delete from t_s_user_org where user_id=?", user.getId());
            saveUserOrgList(req, user);

			users.setRealName(user.getRealName());
			users.setStatus(Globals.User_Normal);
			users.setActivitiSync(user.getActivitiSync());
			systemService.updateEntitie(users);
			List<TSRoleUser> ru = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
			systemService.deleteAllEntitie(ru);
			message = "用户: " + users.getUserName() + "更新成功";
			if (StringUtil.isNotEmpty(roleid)) {
				saveRoleUser(users, roleid);
			}
			systemService.executeSql("delete from t_b_post_user where user_id='"+user.getId()+"'");
			if (StringUtil.isNotEmpty(postId)) {
				savePostUser(users, postId);
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			TSUser users = systemService.findUniqueByProperty(TSUser.class, "userName",user.getUserName());
			if (users != null) {
				message = "用户: " + users.getUserName() + "已经存在";
			} else {
				user.setPassword(PasswordUtil.encrypt(user.getUserName(), password, PasswordUtil.getStaticSalt()));

				user.setStatus(Globals.User_Normal);
				user.setDeleteFlag(Globals.Delete_Normal);
				systemService.save(user);
                // todo zhanggm 保存多个组织机构
                saveUserOrgList(req, user);
				message = "用户: " + user.getUserName() + "添加成功";
				if (StringUtil.isNotEmpty(roleid)) {
					saveRoleUser(user, roleid);
				}
				systemService.executeSql("delete from t_b_post_user where user_id='"+user.getId()+"'");
				if (StringUtil.isNotEmpty(postId)) {
					savePostUser(user, postId);
				}
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			}

		}
		//更新用户信息
		systemService.initAllUserCach();
		j.setMsg(message);

		return j;
	}

	/**
	 * 个人信息更新
	 * @param req
	 * @param user
     * @return
     */
	@RequestMapping(params = "updateCurrentUser")
	@ResponseBody
	public AjaxJson updateCurrentUser(HttpServletRequest req, TSUser user) {
		String message = null;
		AjaxJson j = new AjaxJson();
		// 得到用户工种id
		String postId = oConvertUtils.getString(req.getParameter("postId"));
		if (StringUtil.isNotEmpty(user.getId())) {
			message = "用户信息更新成功";
			TSUser users = systemService.getEntity(TSUser.class, user.getId());
			users.setEmail(user.getEmail());
			users.setOfficePhone(user.getOfficePhone());
			users.setMobilePhone(user.getMobilePhone());
			systemService.executeSql("delete from t_b_post_user where user_id='"+user.getId()+"'");
			if (StringUtil.isNotEmpty(postId)) {
				savePostUser(users, postId);
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
			message = "用户信息不存在, 请重新登录或联系管理员";
		}
		j.setMsg(message);
		return j;
	}

    /**
     * 保存 用户-组织机构 关系信息
     * @param request request
     * @param user user
     */
    private void saveUserOrgList(HttpServletRequest request, TSUser user) {
        String orgIds = oConvertUtils.getString(request.getParameter("orgIds"));

        List<TSUserOrg> userOrgList = new ArrayList<TSUserOrg>();
        List<String> orgIdList = extractIdListByComma(orgIds);
        for (String orgId : orgIdList) {
            TSDepart depart = new TSDepart();
            depart.setId(orgId);

            TSUserOrg userOrg = new TSUserOrg();
            userOrg.setTsUser(user);
            userOrg.setTsDepart(depart);

            userOrgList.add(userOrg);
        }
        if (!userOrgList.isEmpty()) {
            systemService.batchSave(userOrgList);
        }
    }


    protected void saveRoleUser(TSUser user, String roleidstr) {
		String[] roleids = roleidstr.split(",");
		for (int i = 0; i < roleids.length; i++) {
			TSRoleUser rUser = new TSRoleUser();
			TSRole role = systemService.getEntity(TSRole.class, roleids[i]);
			rUser.setTSRole(role);
			rUser.setTSUser(user);
			systemService.save(rUser);

		}
	}

	protected void savePostUser(TSUser user, String postidstr) {
		String[] postids = postidstr.split(",");
		for (int i = 0; i < postids.length; i++) {
			TBPostUser pUser = new TBPostUser();
			TBPostManageEntity post = new TBPostManageEntity();
			post.setId(postids[i]);
			pUser.setTBPostManage(post);
			pUser.setTSUser(user);
			systemService.save(pUser);
		}
	}

	/**
	 * 用户选择角色跳转页面
	 * 
	 * @return
	 */
	@RequestMapping(params = "roles")
	public ModelAndView roles(HttpServletRequest request) {
		//--author：zhoujf-----start----date:20150531--------for: 编辑用户，选择角色,弹出的角色列表页面，默认没选中
		ModelAndView mv = new ModelAndView("system/user/users");
		String ids = oConvertUtils.getString(request.getParameter("ids"));
		mv.addObject("ids", ids);
		return mv;
	}

	/**
	 * 角色显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridRole")
	public void datagridRole(TSRole tsRole, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String rolename = tsRole.getRoleName();
		tsRole.setRoleName(null);
		CriteriaQuery cq = new CriteriaQuery(TSRole.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tsRole);
		if (StringUtil.isNotEmpty(rolename)) {
			cq.like("roleName", "%"+rolename+"%");
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 用户选择工种跳转页面
	 *
	 * @return
	 */
	@RequestMapping(params = "posts")
	public ModelAndView posts(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("system/user/posts");
		String ids = oConvertUtils.getString(request.getParameter("ids"));
		mv.addObject("ids", ids);
		return mv;
	}

	/**
	 * 角色显示列表
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridPost")
	public void datagridPost(TBPostManageEntity tsPost, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String postName = tsPost.getPostName();
		tsPost.setPostName(null);
		CriteriaQuery cq = new CriteriaQuery(TBPostManageEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tsPost);
		if (StringUtil.isNotEmpty(postName)) {
			cq.like("postName", "%"+postName+"%");
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * easyuiAJAX请求数据： 用户选择角色列表
	 * 
	 * @param user
	 * @param req
	 * @param user
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TSUser user, HttpServletRequest req) {

			List<TSDepart> departList = new ArrayList<TSDepart>();
		String departid = oConvertUtils.getString(req.getParameter("departid"));
		if(!StringUtil.isEmpty(departid)){
			departList.add((TSDepart)systemService.getEntity(TSDepart.class,departid));
		}else {
			departList.addAll((List)systemService.getList(TSDepart.class));
		}
		req.setAttribute("departList", departList);


        List<String> orgIdList = new ArrayList<String>();
       // TSDepart tsDepart = new TSDepart();
		if (StringUtil.isNotEmpty(user.getId())) {
			user = systemService.getEntity(TSUser.class, user.getId());
			
			req.setAttribute("user", user);
			idandname(req, user);
			getOrgInfos(req, user);
			postIdandName(req, user);
            orgIdList = systemService.findHql("select d.id from TSDepart d,TSUserOrg uo where d.id=uo.tsDepart.id and uo.tsUser.id=?", new String[]{user.getId()});
		}
		//req.setAttribute("tsDepart", tsDepart);
        req.setAttribute("orgIdList", JSON.toJSON(orgIdList));
		String gucheng = ResourceUtil.getConfigByName("gucheng");
		req.setAttribute("gucheng",gucheng);
        return new ModelAndView("system/user/user");
	}

	/**
	 * 用户信息
	 * @param req
	 * @return
     */
	@RequestMapping(params = "currentUserInfo")
	public ModelAndView userInfo(HttpServletRequest req) {
		TSUser user = ResourceUtil.getSessionUserName();
		List<String> orgIdList = new ArrayList<String>();
		// TSDepart tsDepart = new TSDepart();
		if (StringUtil.isNotEmpty(user.getId())) {
			user = systemService.getEntity(TSUser.class, user.getId());

			req.setAttribute("user", user);
			idandname(req, user);
			getOrgInfos(req, user);
			postIdandName(req, user);
			orgIdList = systemService.findHql("select d.departname from TSDepart d,TSUserOrg uo where d.id=uo.tsDepart.id and uo.tsUser.id=?", new String[]{user.getId()});
		}
		StringBuffer departname = new StringBuffer();
		for (String org: orgIdList) {
			if (StringUtil.isNotEmpty(departname.toString())) {
				departname.append(",");
			}
			departname.append(org);
		}
		req.setAttribute("departname", departname.toString());

		return new ModelAndView("system/user/currentUserInfo");
	}

    /**
     * 用户的登录后的组织机构选择页面
     * @param request request
     * @return 用户选择组织机构页面
     */
	@RequestMapping(params = "userOrgSelect")
	public ModelAndView userOrgSelect(HttpServletRequest request) {
		List<TSDepart> orgList = new ArrayList<TSDepart>();
		String userId = oConvertUtils.getString(request.getParameter("userId"));

        List<Object[]> orgArrList = systemService.findHql("from TSDepart d,TSUserOrg uo where d.id=uo.tsDepart.id and uo.tsUser.id=?", new String[]{userId});
        for (Object[] departs : orgArrList) {
            orgList.add((TSDepart) departs[0]);
        }
        request.setAttribute("orgList", orgList);

        TSUser user = systemService.getEntity(TSUser.class, userId);
        request.setAttribute("user", user);

		return new ModelAndView("system/user/userOrgSelect");
    }


	public void idandname(HttpServletRequest req, TSUser user) {
		List<TSRoleUser> roleUsers = systemService.findByProperty(TSRoleUser.class, "TSUser.id", user.getId());
		StringBuffer roleId = new StringBuffer();
		StringBuffer roleName = new StringBuffer();
		if (roleUsers.size() > 0) {
			for (TSRoleUser tRoleUser : roleUsers) {
				if (StringUtil.isNotEmpty(roleId.toString())) {
					roleId.append(",");
				}
				roleId.append(tRoleUser.getTSRole().getId());
				if (StringUtil.isNotEmpty(roleName.toString())) {
					roleName.append(",");
				}
				roleName.append(tRoleUser.getTSRole().getRoleName());
			}
		}
		req.setAttribute("id", roleId.toString());
		req.setAttribute("roleName", roleName.toString());

	}

	/**
	 * 工种ID和名称
	 * @param req
	 * @param user
     */
	private void postIdandName(HttpServletRequest req, TSUser user) {
		List<TBPostUser> postUsers = systemService.findByProperty(TBPostUser.class, "TSUser.id", user.getId());
		StringBuffer postId = new StringBuffer();
		StringBuffer postName =  new StringBuffer();
		if (postUsers.size() > 0) {
			for (TBPostUser postUser : postUsers) {
				if(StringUtil.isNotEmpty(postId.toString())) {
					postId.append(",");
				}
				postId.append(postUser.getTBPostManage().getId());
				if(StringUtil.isNotEmpty(postName.toString())) {
					postName.append(",");
				}
				postName.append(postUser.getTBPostManage().getPostName());
			}
		}
		req.setAttribute("postId", postId.toString());
		req.setAttribute("postName", postName.toString());

	}
	
	public void getOrgInfos(HttpServletRequest req, TSUser user) {
		List<TSUserOrg> tSUserOrgs = systemService.findByProperty(TSUserOrg.class, "tsUser.id", user.getId());
		StringBuffer orgIds = new StringBuffer();
		StringBuffer departname = new StringBuffer();
		if (tSUserOrgs.size() > 0) {
			for (TSUserOrg tSUserOrg : tSUserOrgs) {
				if(StringUtil.isNotEmpty(orgIds.toString())) {
					orgIds.append(",");
				}
				orgIds.append(tSUserOrg.getTsDepart().getId());
				if (StringUtil.isNotEmpty(departname.toString())) {
					departname.append(",");
				}
				departname.append(tSUserOrg.getTsDepart().getDepartname());
			}
		}
		req.setAttribute("orgIds", orgIds.toString());
		req.setAttribute("departname", departname.toString());

	}
	
	/**
	 * 根据部门和角色选择用户跳转页面
	 */
	@RequestMapping(params = "choose")
	public String choose(HttpServletRequest request) {
		List<TSRole> roles = systemService.loadAll(TSRole.class);
		request.setAttribute("roleList", roles);
		return "system/membership/checkuser";
	}

	/**
	 * 部门和角色选择用户的panel跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "chooseUser")
	public String chooseUser(HttpServletRequest request) {
		String departid = request.getParameter("departid");
		String roleid = request.getParameter("roleid");
		request.setAttribute("roleid", roleid);
		request.setAttribute("departid", departid);
		return "system/membership/userlist";
	}

	/**
	 * 部门和角色选择用户的用户显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridUser")
	public void datagridUser(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String departid = request.getParameter("departid");
		String roleid = request.getParameter("roleid");
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		if (departid.length() > 0) {
			cq.eq("TDepart.departid", oConvertUtils.getInt(departid, 0));
			cq.add();
		}
		String userid = "";
		if (roleid.length() > 0) {
			List<TSRoleUser> roleUsers = systemService.findByProperty(TSRoleUser.class, "TRole.roleid", oConvertUtils.getInt(roleid, 0));
			if (roleUsers.size() > 0) {
				for (TSRoleUser tRoleUser : roleUsers) {
					userid += tRoleUser.getTSUser().getId() + ",";
				}
			}
			cq.in("userid", oConvertUtils.getInts(userid.split(",")));
			cq.add();
		}
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 根据部门和角色选择用户跳转页面
	 */
	@RequestMapping(params = "roleDepart")
	public String roleDepart(HttpServletRequest request) {
		List<TSRole> roles = systemService.loadAll(TSRole.class);
		request.setAttribute("roleList", roles);
		return "system/membership/roledepart";
	}

	/**
	 * 部门和角色选择用户的panel跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "chooseDepart")
	public ModelAndView chooseDepart(HttpServletRequest request) {
		String nodeid = request.getParameter("nodeid");
		ModelAndView modelAndView = null;
		if (nodeid.equals("role")) {
			modelAndView = new ModelAndView("system/membership/users");
		} else {
			modelAndView = new ModelAndView("system/membership/departList");
		}
		return modelAndView;
	}

	/**
	 * 部门和角色选择用户的用户显示列表
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridDepart")
	public void datagridDepart(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
		systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 测试
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "test")
	public void test(HttpServletRequest request, HttpServletResponse response) {
		String jString = request.getParameter("_dt_json");
		DataTables dataTables = new DataTables(request);
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataTables);
		String username = request.getParameter("userName");
		if (username != null) {
			cq.like("userName", username);
			cq.add();
		}
		DataTableReturn dataTableReturn = systemService.getDataTableReturn(cq, true);
		TagUtil.datatable(response, dataTableReturn, "id,userName,mobilePhone,TSDepart_departname");
	}

	/**
	 * 用户列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "index")
	public String index() {
		return "bootstrap/main";
	}

	/**
	 * 用户列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "main")
	public String main() {
		return "bootstrap/test";
	}

	/**
	 * 测试
	 * 
	 * @return
	 */
	@RequestMapping(params = "testpage")
	public String testpage(HttpServletRequest request) {
		return "test/test";
	}

	/**
	 * 设置签名跳转页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "addsign")
	public ModelAndView addsign(HttpServletRequest request) {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		return new ModelAndView("system/user/usersign");
	}

	/**
	 * 用户录入
	 *
	 * @param req
	 * @return
	 */

	@RequestMapping(params = "savesign", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson savesign(HttpServletRequest req) {
		String message = null;
		UploadFile uploadFile = new UploadFile(req);
		String id = uploadFile.get("id");
		TSUser user = systemService.getEntity(TSUser.class, id);
		uploadFile.setRealPath("signatureFile");
		uploadFile.setCusPath("signature");
		uploadFile.setByteField("signature");
		uploadFile.setBasePath("resources");
		uploadFile.setRename(false);
		uploadFile.setObject(user);
		AjaxJson j = new AjaxJson();
		message = user.getUserName() + "设置签名成功";
		systemService.uploadFile(uploadFile);
		systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		j.setMsg(message);

		return j;
	}
	/**
	 * 测试组合查询功能
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "testSearch")
	public void testSearch(TSUser user, HttpServletRequest request,HttpServletResponse response,DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		if(user.getUserName()!=null){
			cq.like("userName", user.getUserName());
		}
		if(user.getRealName()!=null){
			cq.like("realName", user.getRealName());
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "changestyle")
	public String changeStyle(HttpServletRequest request) {
		TSUser user = ResourceUtil.getSessionUserName();
		if(user==null){
			return "login/login";
		}

		SysThemesEnum sysThemesEnum = SysThemesUtil.getSysTheme(request);
		request.setAttribute("indexStyle","hplus" /*sysThemesEnum.getStyle()*/);
		return "system/user/changestyle";
	}
	/**
	* @Title: saveStyle
	* @Description: 修改首页样式
	* @param request
	* @return AjaxJson    
	* @throws
	 */
	@RequestMapping(params = "savestyle")
	@ResponseBody
	public AjaxJson saveStyle(HttpServletRequest request,HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		j.setSuccess(Boolean.FALSE);
		TSUser user = ResourceUtil.getSessionUserName();
		if(user!=null){
			String indexStyle = request.getParameter("indexStyle");

			if(StringUtils.isNotEmpty(indexStyle)){
				Cookie cookie = new Cookie("JEECGINDEXSTYLE", indexStyle);
				//设置cookie有效期为一个月
				cookie.setMaxAge(3600*24*30);
				response.addCookie(cookie);
				logger.debug(" ----- 首页样式: indexStyle ----- "+indexStyle);
				j.setSuccess(Boolean.TRUE);
				j.setMsg("样式修改成功，请刷新页面");
			}

			try {
				 ClientManager.getInstance().getClient().getFunctions().clear();
			} catch (Exception e) {
			}

		}else{
			j.setMsg("请登录后再操作");
		}
		return j;
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","userController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TSUser tsUser,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tsUser, request.getParameterMap());

        Short[] userstate = new Short[]{Globals.User_Normal, Globals.User_ADMIN, Globals.User_Forbidden};
        cq.in("status", userstate);
        cq.eq("deleteFlag", Globals.Delete_Normal);

        String orgIds = request.getParameter("orgIds");
        List<String> orgIdList = extractIdListByComma(orgIds);
        // 获取 当前组织机构的用户信息
        if (!CollectionUtils.isEmpty(orgIdList)) {
            CriteriaQuery subCq = new CriteriaQuery(TSUserOrg.class);
            subCq.setProjection(Property.forName("tsUser.id"));
            subCq.in("tsDepart.id", orgIdList.toArray());
            subCq.add();

            cq.add(Property.forName("id").in(subCq.getDetachedCriteria()));
        }

        cq.add();
		List<TSUser> tsUsers = this.userService.getListByCriteriaQuery(cq,false);

		//导出的时候处理一下组织机构编码和角色编码
		for(int i=0;i<tsUsers.size();i++){
			TSUser user = tsUsers.get(i);
			//托管
			systemService.getSession().evict(user);
			String id = user.getId();
			List<TSRole> roles = systemService.getSession().createSQLQuery("select * from t_s_role where id in (select roleid from t_s_role_user where userid=:userid)")
					.addEntity(TSRole.class).setString("userid",id).list();
            StringBuffer roleCodes = new StringBuffer();
			for(TSRole role:roles){
                if (StringUtil.isNotEmpty(roleCodes.toString())) {
                    roleCodes.append(",").append(role.getRoleName());
                } else {
                    roleCodes.append(role.getRoleName());
                }
			}
			user.setRoleTemp(roleCodes.toString());
			List<TSDepart> departs = systemService.getSession().createSQLQuery("select * from t_s_depart where id in (select org_id from t_s_user_org where user_id=:userid)")
					.addEntity(TSDepart.class).setString("userid",id).list();
			StringBuffer departCodes = new StringBuffer();
			for(TSDepart depart:departs){
                if (StringUtil.isNotEmpty(departCodes.toString())) {
                    departCodes.append(",").append(depart.getDepartname());
                } else {
                    departCodes.append(depart.getDepartname());
                }
			}
			user.setDepartTemp(departCodes.toString());
		}
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_UserInfo.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String,List<TSUser>> map = new HashMap<>();
        map.put("list", tsUsers);
        modelMap.put(NormalExcelConstants.FILE_NAME,"用户信息表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}

	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TSUser tsUser,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME,"用户信息导入模板");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setScanAllsheet(true);
        templateExportParams.setSheetNum(1);
        templateExportParams.setTemplateUrl("export/template/importTemp_UserInfo.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String, Object> param =new HashMap<>();

        List<TSUserExportDicVO> userDicList = new ArrayList<TSUserExportDicVO>();
        //查询角色
		String roleSql = "select rolename from t_s_role";
		List<String> roleList = systemService.findListbySql(roleSql);

		//查询组织机构
		String departSql = "select departname from t_s_depart where delete_flag='0'";
		List<String> departList = systemService.findListbySql(departSql);

		//得到这几串数列的最长的一列，excel导出的行数即为最长一列的长度
		int[] listLength = {roleList.size(), departList.size()};         /*后面这三个查询的是lec的风险可能性，风险损失和暴露在风险中的概率*/
		int maxLength = listLength[0];
		for (int i = 0; i < listLength.length; i++) {   //开始循环一维数组
			if (listLength[i] > maxLength) {  //循环判断数组元素
				maxLength = listLength[i]; }  //赋值给num，然后再次循环
		}

		for (int j=0; j<maxLength; j++){
			TSUserExportDicVO vo = new TSUserExportDicVO();
			if (j<roleList.size()){
				vo.setRole(roleList.get(j));
			}
			if (j<departList.size()){
				vo.setDepart(departList.get(j));
			}

			userDicList.add(vo);
		}
		param.put("list", userDicList);

        modelMap.put(TemplateExcelConstants.MAP_DATA,param);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(1);
			params.setHeadRows(1);
			params.setNeedSave(true);
            params.setVerifyHanlder(new UserExcelVerifyHandler());
			try {
                ExcelImportResult<TSUser> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(),TSUser.class,params);
                if(result.isVerfiyFail()){
                    String uploadpathtemp = ResourceUtil.getConfigByName("uploadpathtemp");
                    String realPath = multipartRequest.getSession().getServletContext().getRealPath("/") + "/" + uploadpathtemp+"/";// 文件的硬盘真实路径
                    File fileTemp = new File(realPath);
                    if (!fileTemp.exists()) {
                        fileTemp.mkdirs();// 创建根目录
                    }
                    String name = DateUtils.getDataString(DateUtils.yyyymmddhhmmss)+".xls";
                    realPath+=name;
                    FileOutputStream fos = new FileOutputStream(realPath);
                    result.getWorkbook().write(fos);
                    fos.close();
                    Map<String, Object> attributes = new HashMap<String, Object>();
                    attributes.put("path", uploadpathtemp+"/"+name);
                    j.setAttributes(attributes);
                    j.setSuccess(false);
                    j.setMsg("导入数据校验失败");
                }else{
                    for (TSUser user : result.getList()) {
                        //保存用户信息
                        user.setPassword(PasswordUtil.encrypt(user.getUserName(), defaultPassword, PasswordUtil.getStaticSalt()));
						String gucheng = ResourceUtil.getConfigByName("gucheng");
						if(gucheng.equals("true")){
							user.setPassword(PasswordUtil.encrypt(user.getUserName(), "gcmk@123", PasswordUtil.getStaticSalt()));
						}
						user.setStatus(Globals.User_Normal);
                        user.setDeleteFlag(Globals.Delete_Normal);
                        systemService.save(user);
                        //保存用户组织信息
                        List<TSUserOrg> tsUserOrgs = user.getUserOrgList() ;
                        if (tsUserOrgs != null && tsUserOrgs.size() > 0){
                            for (TSUserOrg userOrg : tsUserOrgs) {
                                userOrg.setTsUser(user);
                            }
							systemService.batchSave(tsUserOrgs);
                        }
                        //保存用户角色信息
                        List<TSRoleUser> tsRoleUsers = user.getUserRoleList();
                        if (tsRoleUsers != null && tsRoleUsers.size() > 0){
                            for (TSRoleUser roleUser : tsRoleUsers){
                                roleUser.setTSUser(user);
                            }
							systemService.batchSave(tsRoleUsers);
                        }
                    }
					j.setSuccess(true);
                    j.setMsg("文件导入成功！");
                }
			} catch (Exception e) {
				j.setSuccess(false);
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}

	/**
	 * 选择用户跳转页面
	 *
	 * @return
	 */
	@RequestMapping(params = "userSelect")
	public String userSelect() {
		return "system/user/userSelect";
	}


    /**
     * 用户多选页面跳转
     *
     * @return
     */
    @RequestMapping(params = "userManySelectList")
    public String userManySelectList(HttpServletRequest request) {
        // 给部门查询条件中的下拉框准备数据
        String selected = request.getParameter("selected");
        request.setAttribute("selected",selected);
        List<TSDepart> departList = systemService.getList(TSDepart.class);
		request.setAttribute("departmentList",departList);
        request.setAttribute("departsReplace", RoletoJson.listToReplaceStr(departList, "departname", "id"));
        String deptId = request.getParameter("deptId");
        request.setAttribute("deptId",deptId);
        return "system/user/userManySelectList";
    }

    @RequestMapping(params = "getUserList")
    @ResponseBody
    public JSONArray getUserList(TSUser user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        //查询条件组装器
        StringBuffer sql = new StringBuffer("select u.id userid, u.realname, d.id departid, d.departname, us.post from t_s_base_user u join t_s_user us on u.id=us.id join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id where 1=1 ");

        sql.append("and u.status in ('").append(Globals.User_Normal).append("','").append(Globals.User_ADMIN).append("','").append(Globals.User_Forbidden).append("') ");
        sql.append("and u.delete_flag ='0' ");
        String selected = request.getParameter("selected");
        if(StringUtils.isNotBlank(selected)){
            sql.append("and u.status not in('").append(selected.replace(",","','")).append("') ");
        }
        //添加组织机构查询条件
        String orgIds = request.getParameter("orgIds");
        if (orgIds != null && orgIds.length() > 0){
            sql.append("and o.org_id in ('").append(orgIds.replace(",","','")).append("') ");
        }

        String deptId = request.getParameter("deptId");
        if (deptId != null && deptId.length() > 0){
            sql.append("and o.org_id in ('").append(deptId.replace(",","','")).append("') ");
        }

        List<Map<String, Object>> result = systemService.findForJdbc(sql.toString());

        List<UserVo> retList = new ArrayList<UserVo>();
        for (Map<String, Object> o : result) {
            UserVo vo = new UserVo();
            vo.setId(o.get("userid").toString());
            vo.setRealName(o.get("realname").toString());
            vo.setDepartId(o.get("departid").toString());
            vo.setDepartName(o.get("departname").toString());
            retList.add(vo);
        }
        JSONArray jsonArray = JSONArray.fromObject(retList);
        return jsonArray;
    }
}