package org.jeecgframework.web.system.controller.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sdzk.buss.web.common.excelverify.DepartExcelVerifyHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


/**
 * 部门信息处理类
 * 
 * @author 张代浩
 * 
 */
//@Scope("prototype")
@Controller
@RequestMapping("/departController")
public class DepartController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DepartController.class);
	private UserService userService;
	private SystemService systemService;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 部门列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "depart")
	public ModelAndView depart() {
		return new ModelAndView("system/depart/departList");
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

		@RequestMapping(params = "delUserOrg")
		@ResponseBody
		public AjaxJson delUserOrg(@RequestParam(required=true)String userid,@RequestParam(required=true)String departid) {
			AjaxJson ajaxJson = new AjaxJson();
			try {
				List<TSUserOrg> userOrgList = this.systemService.findByProperty(TSUserOrg.class, "tsUser.id", userid);
				if(userOrgList.size() == 1){
					ajaxJson.setSuccess(false);
					ajaxJson.setMsg("当前用户只包含有当前组织机构关系，不可删除，请切换用户的组织机构关系");
				}else{
					String sql = "delete from t_s_user_org where user_id = '"+userid+"' and org_id = '"+departid+"'";
					this.systemService.executeSql(sql);
					ajaxJson.setMsg("成功删除用户对应的组织机构关系");
				}
			} catch (Exception e) {
				LogUtil.log("删除用户对应的组织机构关系失败", e.getMessage());
				ajaxJson.setSuccess(false);
				ajaxJson.setMsg(e.getMessage());
			}
			return ajaxJson;
		}

	/**
	 * 删除部门：
	 * <ul>
     *     组织机构下存在子机构时
     *     <li>不允许删除 组织机构</li>
	 * </ul>
	 * <ul>
     *     组织机构下存在用户时
     *     <li>不允许删除 组织机构</li>
	 * </ul>
	 * <ul>
     *     组织机构下 不存在子机构 且 不存在用户时
     *     <li>删除 组织机构-角色 信息</li>
     *     <li>删除 组织机构 信息</li>
	 * </ul>
	 * @return 删除的结果信息
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TSDepart depart, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		depart = systemService.getEntity(TSDepart.class, depart.getId());
        message = MutiLangUtil.paramDelSuccess("common.department");
        //若父节点下的单位均已逻辑删除，允许删除父节点
        boolean isdelete=true;
        for(TSDepart d:depart.getTSDeparts()){
              if(d.getDeleteFlag()==1){

              }else{
                  isdelete=false;
                  break;
              }
        }
        if (depart.getTSDeparts().size() == 0||isdelete==true) {
            Long userCount = systemService.getCountForJdbc("select count(1) from t_s_user_org o,t_s_base_user u where o.user_id=u.id and u.delete_flag=0 and o.org_id='" + depart.getId() + "'");
            if(userCount == 0) { // 组织机构下没有用户时，该组织机构才允许删除。
                systemService.executeSql("delete from t_s_role_org where org_id=?", depart.getId());
//                systemService.delete(depart);
                systemService.executeSql("update t_s_depart set delete_flag='1' where id=?", depart.getId());


                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }else{

            	message = MutiLangUtil.getMutiLangInstance().getLang("common.department.hasuser");

            }
        } else {

//            message = MutiLangUtil.paramDelFail("common.department");
            message = "组织机构下有部门，不允许删除";
        }

        j.setMsg(message);
		return j;
	}


	public void upEntity(TSDepart depart) {
		List<TSUser> users = systemService.findByProperty(TSUser.class, "TSDepart.id", depart.getId());
		if (users.size() > 0) {
			for (TSUser tsUser : users) {
				//tsUser.setTSDepart(null);
				//systemService.saveOrUpdate(tsUser);
				systemService.delete(tsUser);
			}
		}
	}

	/**
	 * 添加部门
	 * 
	 * @param depart
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TSDepart depart, HttpServletRequest request) {
		String message = null;
		// 设置上级部门
		String pid = request.getParameter("TSPDepart.id");
		if (pid.equals("")) {
			depart.setTSPDepart(null);
		}
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(depart.getId())) {
            message = MutiLangUtil.paramUpdSuccess("common.department");
			userService.saveOrUpdate(depart);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} else {
            message = MutiLangUtil.paramAddSuccess("common.department");
			userService.save(depart);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}

        j.setMsg(message);
		return j;
	}
	@RequestMapping(params = "add")
	public ModelAndView add(TSDepart depart, HttpServletRequest req) {
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		req.setAttribute("departList", departList);
//        这个if代码段没有用吧，注释之
//		if (StringUtil.isNotEmpty(depart.getId())) {
//			TSDepart tspDepart = new TSDepart();
//			TSDepart tsDepart = new TSDepart();
//			depart = systemService.getEntity(TSDepart.class, depart.getId());
//			tspDepart.setId(depart.getId());
//			tspDepart.setDepartname(depart.getDepartname());
//			tsDepart.setTSPDepart(tspDepart);
//			req.setAttribute("depart", tsDepart);
//		}
//        depart.setDeleteFlag(Short.valueOf("0"));
        req.setAttribute("pid", depart.getId());
		return new ModelAndView("system/depart/depart");
	}
	/**
	 * 部门列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "update")
	public ModelAndView update(TSDepart depart, HttpServletRequest req) {
		List<TSDepart> departList = systemService.getList(TSDepart.class);
		req.setAttribute("departList", departList);
		if (StringUtil.isNotEmpty(depart.getId())) {
			depart = systemService.getEntity(TSDepart.class, depart.getId());
			req.setAttribute("depart", depart);
		}
		String sjdw = ResourceUtil.getConfigByName("sjdw");
		req.setAttribute("sjdw", sjdw);
		return new ModelAndView("system/depart/depart");
	}
	
	/**
	 * 父级权限列表
	 * 
	 * @param request
	 * @param comboTree
	 * @return
	 */
	@RequestMapping(params = "setPFunction")
	@ResponseBody
	public List<ComboTree> setPFunction(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
		if(null != request.getParameter("selfId")){
			cq.notEq("id", request.getParameter("selfId"));
		}
		if (comboTree.getId() != null) {
			cq.eq("TSPDepart.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("TSPDepart");
		}
		cq.eq("deleteFlag",Short.parseShort("0"));
		cq.addOrder("orgCode", SortDirection.asc);

		cq.add();
		List<TSDepart> departsList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "departname", "TSDeparts");

		TSDepart defaultDepart = new TSDepart();
		defaultDepart.setId("");
		defaultDepart.setDepartname("请选择组织机构");
		departsList.add(0, defaultDepart);

		comboTrees = systemService.ComboTree(departsList, comboTreeModel, null, true);
		return comboTrees;

	}

	@RequestMapping(params = "createSpell")
	@ResponseBody
	public AjaxJson createSpell(HttpServletRequest request) {

		String saveOld = request.getParameter("saveOld");
		AjaxJson j = new AjaxJson();
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
		try{

		}catch(Exception e){
			e.printStackTrace();
		}
		cq.add();
		List<TSDepart> departList = systemService.getListByCriteriaQuery(cq,false);
		if(departList != null && !departList.isEmpty()){
			boolean isSave = Boolean.parseBoolean(saveOld);
			for(TSDepart depart : departList){
				String departName = depart.getDepartname();
				String fullSpell = com.sdzk.buss.web.common.utils.PinyinUtil.getPingYin((departName).toLowerCase());
				String spell = com.sdzk.buss.web.common.utils.PinyinUtil.getPinYinHeadChar((departName).toLowerCase());
				if(!isSave) {
					depart.setFullSpelling(fullSpell.replaceAll(" ", ""));
					depart.setSpelling(spell.replaceAll(" ", ""));
				}else{
					if(StringUtils.isBlank(depart.getSpelling())){
						depart.setSpelling(spell.replaceAll(" ", ""));
					}
					if(StringUtils.isBlank(depart.getFullSpelling())){
						depart.setFullSpelling(fullSpell.replaceAll(" ", ""));
					}
				}
				systemService.saveOrUpdate(depart);
			}
		}
		j.setMsg("创建成功");

		return j;
	}

	/**
	 * 部门列表，树形展示
	 * @param request
	 * @param response
	 * @param treegrid
	 * @return
	 */
	@RequestMapping(params = "departgrid")
	@ResponseBody
	public Object departgrid(TSDepart tSDepart,HttpServletRequest request, HttpServletResponse response, TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
		if("yes".equals(request.getParameter("isSearch"))){
			treegrid.setId(null);
			tSDepart.setId(null);
		} 
		if(null != tSDepart.getDepartname()){
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSDepart);
		}
		if (treegrid.getId() != null) {
			cq.eq("TSPDepart.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("TSPDepart");
		}

		cq.addOrder("orgCode", SortDirection.asc);
        cq.eq("deleteFlag", Globals.Delete_Normal);     //Globals.Delete_Normal 代表正常，即未删除     Globals.Delete_Forbidden 代表删除

		cq.add();
		List<TreeGrid> departList =null;
		departList=systemService.getListByCriteriaQuery(cq, false);
		if(departList.size()==0&&tSDepart.getDepartname()!=null){ 
			cq = new CriteriaQuery(TSDepart.class);
			TSDepart parDepart = new TSDepart();
			tSDepart.setTSPDepart(parDepart);
			org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSDepart);
		    departList =systemService.getListByCriteriaQuery(cq, false);
		}
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("departname");
		treeGridModel.setParentText("TSPDepart_departname");
		treeGridModel.setParentId("TSPDepart_id");
		treeGridModel.setSrc("description");
		treeGridModel.setIdField("id");
		treeGridModel.setChildList("TSDeparts");
        Map<String,Object> fieldMap = new HashMap<String, Object>();
        fieldMap.put("orgCode", "orgCode");
        fieldMap.put("orgType", "orgType");
		fieldMap.put("mobile", "mobile");
		fieldMap.put("fax", "fax");
		fieldMap.put("address", "address");
		fieldMap.put("order", "departOrder");
		fieldMap.put("spelling", "spelling");
		fieldMap.put("fullSpelling", "fullSpelling");
        treeGridModel.setFieldMap(fieldMap);
        treeGrids = systemService.treegrid(departList, treeGridModel);

        JSONArray jsonArray = new JSONArray();
        for (TreeGrid treeGrid : treeGrids) {
            jsonArray.add(JSON.parse(treeGrid.toJson()));
        }
        return jsonArray;
	}
	//----
	/**
	 * 方法描述:  查看成员列表
	 * 作    者： yiming.zhang
	 * 日    期： Dec 4, 2013-8:53:39 PM
	 * @param request
	 * @param departid
	 * @return 
	 * 返回类型： ModelAndView
	 */
	@RequestMapping(params = "userList")
	public ModelAndView userList(HttpServletRequest request, String departid) {
		request.setAttribute("departid", departid);
		return new ModelAndView("system/depart/departUserList");
	}
	
	/**
	 * 方法描述:  成员列表dataGrid
	 * 作    者：
	 * 日    期： Dec 4, 2013-10:40:17 PM
	 * @param user
	 * @param request
	 * @param response
	 * @param dataGrid 
	 * 返回类型： void
	 */
	@RequestMapping(params = "userDatagrid")
	public void userDatagrid(TSUser user,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        String oldDepartid = user.getDepartid();
		if(user!=null&&user.getDepartid()!=null){
			user.setDepartid(null);//设置用户的所属部门的查询条件为空；
		}


		String username = user.getUserName();
		user.setUserName(null);
		String realname = user.getRealName();
		user.setRealName(null);


		CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);
		String departid = oConvertUtils.getString(request.getParameter("departid"));
		if (!StringUtil.isEmpty(departid)) {

			DetachedCriteria dc = cq.getDetachedCriteria();
			DetachedCriteria dcDepart = dc.createCriteria("userOrgList");

			dcDepart.add(Restrictions.eq("tsDepart.id", departid));
            String sql = "delete from t_s_user_org where user_id = '"+user+"' and org_id = '"+oldDepartid+"'";
            this.systemService.executeSql(sql);
            // 这种方式也是可以的
//            DetachedCriteria dcDepart = dc.createAlias("userOrgList", "userOrg");
//            dcDepart.add(Restrictions.eq("userOrg.tsDepart.id", departid));

		}
		Short[] userstate = new Short[] { Globals.User_Normal, Globals.User_ADMIN,Globals.User_Forbidden };
		cq.in("status", userstate);
        cq.eq("deleteFlag", Globals.Delete_Normal);
		if (StringUtil.isNotEmpty(username)) {
			cq.like("userName", "%"+username+"%");
		}
		if (StringUtil.isNotEmpty(realname)) {
			cq.like("realName", "%"+realname+"%");
		}
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	//----

    /**
     * 获取机构树-combotree
     * @param request
     * @return
     */
    @RequestMapping(params = "getOrgTree")
    @ResponseBody
    public List<ComboTree> getOrgTree(HttpServletRequest request) {
//        findHql不能处理is null条件
//        List<TSDepart> departsList = systemService.findHql("from TSPDepart where TSPDepart.id is null");
        List<TSDepart> departsList = systemService.findByQueryString("from TSDepart where TSPDepart.id is null and deleteFlag = '0'");
        List<ComboTree> comboTrees = new ArrayList<ComboTree>();
        ComboTreeModel comboTreeModel = new ComboTreeModel("id", "departname", "TSDeparts");
        comboTrees = systemService.ComboTree(departsList, comboTreeModel, null, true);
        return comboTrees;
    }

    /**
     * 添加 用户到组织机构 的页面  跳转
     * @param req request
     * @return 处理结果信息
     */
    @RequestMapping(params = "goAddUserToOrg")
    public ModelAndView goAddUserToOrg(HttpServletRequest req) {
        req.setAttribute("orgId", req.getParameter("orgId"));
        return new ModelAndView("system/depart/noCurDepartUserList");
    }
    /**
     * 获取 除当前 组织之外的用户信息列表
     * @param request request
     * @return 处理结果信息
     */
    @RequestMapping(params = "addUserToOrgList")
    public void addUserToOrgList(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String orgId = request.getParameter("orgId");
		String username = user.getUserName();
		user.setUserName(null);
		String realname = user.getRealName();
		user.setRealName(null);
        CriteriaQuery cq = new CriteriaQuery(TSUser.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, user);

        // 获取 当前组织机构的用户信息
        CriteriaQuery subCq = new CriteriaQuery(TSUserOrg.class);
        subCq.setProjection(Property.forName("tsUser.id"));
        subCq.eq("tsDepart.id", orgId);
        subCq.add();
		if (StringUtil.isNotEmpty(username)) {
			cq.like("userName", "%"+username+"%");
		}
		if (StringUtil.isNotEmpty(realname)) {
			cq.like("realName", "%"+realname+"%");
		}
        cq.add(Property.forName("id").notIn(subCq.getDetachedCriteria()));
        cq.eq("deleteFlag", Globals.Delete_Normal);
        cq.add();

        this.systemService.getDataGridReturn(cq, true);
        if(dataGrid != null && dataGrid.getResults() != null){
            List<TSUser> userList = dataGrid.getResults();
            if(null!=userList && userList.size()>0){
                for(int i=0;i<userList.size();i++){
                    TSUser u =userList.get(i);
                    u.setDepartTemp("");
                    String sql = "select d.departname from t_s_user_org uo, t_s_depart d " +
                            " where uo.org_id=d.id and d.delete_flag='0' and uo.user_id='" + u.getId() + "'";
                    List<String> departnameList = this.systemService.findListbySql(sql);
                    if(null!=departnameList && departnameList.size()>0){
                        u.setDepartTemp(departnameList.get(0));
                    }
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }
    /**
     * 添加 用户到组织机构
     * @param req request
     * @return 处理结果信息
     */
    @RequestMapping(params = "doAddUserToOrg")
    @ResponseBody
    public AjaxJson doAddUserToOrg(HttpServletRequest req) {
    	String message = null;
        AjaxJson j = new AjaxJson();
        TSDepart depart = systemService.getEntity(TSDepart.class, req.getParameter("orgId"));
        saveOrgUserList(req, depart);
        message =  MutiLangUtil.paramAddSuccess("common.user");
//      systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        j.setMsg(message);

        return j;
    }
    /**
     * 保存 组织机构-用户 关系信息
     * @param request request
     * @param depart depart
     */
    private void saveOrgUserList(HttpServletRequest request, TSDepart depart) {
        String orgIds = oConvertUtils.getString(request.getParameter("userIds"));

        List<TSUserOrg> userOrgList = new ArrayList<TSUserOrg>();
        List<String> userIdList = extractIdListByComma(orgIds);
        for (String userId : userIdList) {
            //清除用户原组织机构(一个用户只能属于一个部门）
            List<TSUserOrg> uoList = systemService.findByProperty(TSUserOrg.class, "tsUser.id", userId);
            if(null!=uoList && uoList.size()>0){
                this.systemService.deleteAllEntitie(uoList);
            }
            //清除结束
            TSUser user = new TSUser();
            user.setId(userId);

            TSUserOrg userOrg = new TSUserOrg();
            userOrg.setTsUser(user);
            userOrg.setTsDepart(depart);

            userOrgList.add(userOrg);
        }
        if (!userOrgList.isEmpty()) {
            systemService.batchSave(userOrgList);
        }
    }

    /**
     * 用户选择机构列表跳转页面
     *
     * @return
     */
    @RequestMapping(params = "departSelect")
    public String departSelect(HttpServletRequest req) {

    	req.setAttribute("orgIds", req.getParameter("orgIds"));

        return "system/depart/departSelect";
    }
    /**
     * 角色显示列表
     *
     * @param response response
     * @param dataGrid dataGrid
     */
    @RequestMapping(params = "departSelectDataGrid")
    public void datagridRole(HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 用户选择机构列表跳转页面(树列表)
     *
     * @return
     */
    @RequestMapping(params = "orgSelect")
    public String orgSelect(HttpServletRequest req) {
    	
    	req.setAttribute("orgIds", req.getParameter("orgIds"));

        return "system/depart/orgSelect";
    }
    
	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","departController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TSDepart tsDepart, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tsDepart, request.getParameterMap());
		cq.addOrder("orgCode", SortDirection.asc);
		List<TSDepart> tsDeparts = this.systemService.getListByCriteriaQuery(cq,false);
        if (tsDeparts != null && tsDeparts.size() > 0) {
            for(TSDepart depart : tsDeparts) {
                if (DepartExcelVerifyHandler.companyCode.equals(depart.getOrgType())) {
                    depart.setOrgTypeTemp(DepartExcelVerifyHandler.company);
                } else if (DepartExcelVerifyHandler.departmentCode.equals(depart.getOrgType())) {
                    depart.setOrgTypeTemp(DepartExcelVerifyHandler.department);
                } else if (DepartExcelVerifyHandler.positionCode.equals(depart.getOrgType())) {
                    depart.setOrgTypeTemp(DepartExcelVerifyHandler.position);
                }
                TSDepart superiorDepart = depart.getTSPDepart();
                if (superiorDepart != null) {
                    depart.setSuperiorDepart(superiorDepart.getDepartname());
                }
            }
        }

        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_DepartInfo.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String,List<TSDepart>> map = new HashMap<>();
        map.put("list", tsDeparts);
        modelMap.put(NormalExcelConstants.FILE_NAME,"组织机构表");
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
	public String exportXlsByT(TSDepart tsDepart,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME,"组织机构导入模板");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/importTemp_DepartInfo.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String, Object> param =new HashMap<>();
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
			try {
                Set<String> departName = new HashSet<>();
                List<TSDepart> tsDeparts = ExcelImportUtil.importExcel(file.getInputStream(),TSDepart.class,params);
                for (TSDepart depart : tsDeparts) {
                    departName.add(depart.getDepartname());
                }
                DepartExcelVerifyHandler handler = new DepartExcelVerifyHandler();
                handler.setDepartNames(departName);
                params.setVerifyHanlder(handler);
                ExcelImportResult<TSDepart> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(),TSDepart.class,params);

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
                } else {
                    Map<String, TSDepart> departs = new HashMap<>();
                    Map<String, TSDepart> departMap = handler.getDepartMap();
                    for (TSDepart depart : result.getList()) {
                        if (StringUtil.isNotEmpty(depart.getSuperiorDepart())) {
                            TSDepart superDepart = depart.getTSPDepart()==null?departMap.get(depart.getSuperiorDepart()):depart.getTSPDepart();
                            if (superDepart != null) {
                                depart.setTSPDepart(superDepart);
                                superDepart.getSubDepart().add(depart);
                                if (StringUtil.isNotEmpty(superDepart.getId()) && departs.get(superDepart.getId()) == null){
                                    departs.put(superDepart.getId(), superDepart);
                                }
                            }
                        } else {
                            departs.put(depart.toString(), depart);
                        }
                    }
                    for (String key : departs.keySet()) {
                        TSDepart saveDepart = departs.get(key);
                        TSDepart superDepart = saveDepart.getTSPDepart();
                        if (superDepart != null && StringUtil.isNotEmpty(superDepart.getId())) {
                            saveDepart(saveDepart, superDepart.getId());
                        } else {
                            saveDepart(saveDepart, null);
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

    private void saveDepart(TSDepart depart, String pid){
        if (StringUtil.isEmpty(depart.getId())){
            if(oConvertUtils.isNotEmpty(pid)){
                TSDepart paretDept = systemService.findUniqueByProperty(TSDepart.class, "id", pid);
                String localMaxCode  = getMaxLocalCode(paretDept.getOrgCode());
                depart.setOrgCode(YouBianCodeUtil.getSubYouBianCode(paretDept.getOrgCode(), localMaxCode));
            }else{
                String localMaxCode  = getMaxLocalCode(null);
                depart.setOrgCode(YouBianCodeUtil.getNextYouBianCode(localMaxCode));
            }
            depart.setDeleteFlag(Short.valueOf("0"));
            systemService.save(depart);
        }
        List<TSDepart> subDepart = depart.getSubDepart();
        if (subDepart != null && subDepart.size() > 0) {
            for(TSDepart tsDepart : subDepart) {
                saveDepart(tsDepart, depart.getId());
            }
        }
    }
    //TODO
    private synchronized String getMaxLocalCode(String parentCode){
        if(oConvertUtils.isEmpty(parentCode)){
            parentCode = "";
        }
        int localCodeLength = parentCode.length() + YouBianCodeUtil.zhanweiLength;
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT org_code FROM t_s_depart");

        if(ResourceUtil.getJdbcUrl().indexOf(JdbcDao.DATABSE_TYPE_SQLSERVER)!=-1){
            sb.append(" where LEN(org_code) = ").append(localCodeLength);
        }else{
            sb.append(" where LENGTH(org_code) = ").append(localCodeLength);
        }

        if(oConvertUtils.isNotEmpty(parentCode)){
            sb.append(" and  org_code like '").append(parentCode).append("%'");
        }

        sb.append(" ORDER BY org_code DESC");
        List<Map<String, Object>> objMapList = systemService.findForJdbc(sb.toString(), 1, 1);
        String returnCode = null;
        if(objMapList!=null && objMapList.size()>0){
            returnCode = (String)objMapList.get(0).get("org_code");
        }

        return returnCode;
    }

	@RequestMapping(params = "getDepartInfo")
	@ResponseBody
	public AjaxJson getDepartInfo(HttpServletRequest request, HttpServletResponse response){
		
		AjaxJson j = new AjaxJson();
		
		String orgIds = request.getParameter("orgIds");
		
		String[] ids = new String[]{}; 
		if(StringUtils.isNotBlank(orgIds)){
			orgIds = orgIds.substring(0, orgIds.length()-1);
			ids = orgIds.split("\\,");
		}
		
		String parentid = request.getParameter("parentid");
		
		List<TSDepart> tSDeparts = new ArrayList<TSDepart>();
		
		StringBuffer hql = new StringBuffer(" from TSDepart t where 1=1 ");
		if(StringUtils.isNotBlank(parentid)){
			
			TSDepart dePart = this.systemService.getEntity(TSDepart.class, parentid);
			
			hql.append(" and TSPDepart = ?");
			tSDeparts = this.systemService.findHql(hql.toString(), dePart);
		} else {
			hql.append(" and t.orgType = ?");
			tSDeparts = this.systemService.findHql(hql.toString(), "1");
		}
		List<Map<String,Object>> dateList = new ArrayList<Map<String,Object>>();
		if(tSDeparts.size()>0){
			Map<String,Object> map = null;
			String sql = null;
			 Object[] params = null;
			for(TSDepart depart:tSDeparts){
				map = new HashMap<String,Object>();
				map.put("id", depart.getId());
				map.put("name", depart.getDepartname());
				
				if(ids.length>0){
					for(String id:ids){
						if(id.equals(depart.getId())){
							map.put("checked", true);
						}
					}
				}
				
				if(StringUtils.isNotBlank(parentid)){
					map.put("pId", parentid);
				} else{
					map.put("pId", "1");
				}
				//根据id判断是否有子节点
				sql = "select count(1) from t_s_depart t where t.parentdepartid = ?";
				params = new Object[]{depart.getId()};
				long count = this.systemService.getCountForJdbcParam(sql, params);
				if(count>0){
					map.put("isParent",true);
				}
				dateList.add(map);
			}
		}
		net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(dateList);
		j.setMsg(jsonArray.toString());
		return j;
	}

}
