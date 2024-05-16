package com.sdzk.sys.childwindow.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sdzk.sys.childwindow.entity.TSRoleChildWindowEntity;
import com.sdzk.sys.childwindow.entity.TSUserChildWindowEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.util.MyBeanUtils;

import com.sdzk.sys.childwindow.entity.TSChildWindowsEntity;
import com.sdzk.sys.childwindow.service.TSChildWindowsServiceI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**   
 * @Title: Controller
 * @Description: 首页子窗口
 * @author zhangdaihao
 * @date 2018-03-19 10:11:07
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/childWindowController")
public class TSChildWindowsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSChildWindowsController.class);

	@Autowired
	private TSChildWindowsServiceI childWindowsService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 首页子窗口列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/sys/childwindow/tSChildWindowsList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TSChildWindowsEntity tSChildWindows,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSChildWindowsEntity.class, dataGrid);
		//查询条件组装器
		try{
			String childWindowTitle = request.getParameter("childWindowTitle");
			if(StringUtils.isNotBlank(childWindowTitle)){
				cq.like("childWindowTitle","%"+childWindowTitle+"%");
			}

			String isCenter = request.getParameter("isCenter");
			if(StringUtils.isNotBlank(isCenter)){
				cq.eq("isCenter",isCenter);
			}

			String isUsed = request.getParameter("isUsed");
			if(StringUtils.isNotBlank(isUsed)){
				cq.eq("isUsed",isUsed);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		cq.add();
		this.childWindowsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除首页子窗口
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TSChildWindowsEntity tSChildWindows, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "首页子窗口删除成功";

		tSChildWindows = systemService.getEntity(TSChildWindowsEntity.class, tSChildWindows.getId());
		childWindowsService.delete(tSChildWindows);

		systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加首页子窗口
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TSChildWindowsEntity tSChildWindows, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(tSChildWindows.getId())) {
			message = "首页子窗口更新成功";
			TSChildWindowsEntity t = childWindowsService.get(TSChildWindowsEntity.class, tSChildWindows.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(tSChildWindows, t);
				childWindowsService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "首页子窗口更新失败";
			}
		} else {
			message = "首页子窗口添加成功";
			tSChildWindows.setIsUsed("1");
			childWindowsService.save(tSChildWindows);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 首页子窗口列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TSChildWindowsEntity tSChildWindows, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSChildWindows.getId())) {
			tSChildWindows = childWindowsService.getEntity(TSChildWindowsEntity.class, tSChildWindows.getId());
			req.setAttribute("tSChildWindowsPage", tSChildWindows);
		}
		return new ModelAndView("com/sdzk/sys/childwindow/tSChildWindows");
	}

	/**
	 * 角色-子窗口关联列表页面跳转
	 * @param roleId
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "goEditRoleChildWin")
	public ModelAndView goEditRoleChildWin(String roleId, HttpServletRequest req) {
		CriteriaQuery cq = new CriteriaQuery(TSChildWindowsEntity.class);
		cq.eq("isUsed","1");
		cq.addOrder("createDate", SortDirection.asc);
		cq.add();
		List<TSChildWindowsEntity> childWindowList = systemService.getListByCriteriaQuery(cq,false);
		List<TSChildWindowsEntity> centerList = new ArrayList<>();
		List<TSChildWindowsEntity> aroundList = new ArrayList<>();

		if(!childWindowList.isEmpty() && childWindowList.size()>0){
			for (TSChildWindowsEntity entity : childWindowList) {
				String isCenter = entity.getIsCenter();
				if("1".equals(isCenter)){
					centerList.add(entity);
				}else{
					aroundList.add(entity);
				}
			}
		}
		req.setAttribute("centerList",centerList);
		req.setAttribute("aroundList",aroundList);

		cq = new CriteriaQuery(TSRoleChildWindowEntity.class);
		cq.eq("roleId",roleId);
		cq.add();
		List<TSRoleChildWindowEntity> roleChildWindowList = systemService.getListByCriteriaQuery(cq,false);
		if(!roleChildWindowList.isEmpty() && roleChildWindowList.size()>0){
			for (TSRoleChildWindowEntity entity : roleChildWindowList) {
				req.setAttribute(entity.getPosition(),entity.getChildWindowId());
			}
		}

		req.setAttribute("roleId",roleId);
		return new ModelAndView("com/sdzk/sys/childwindow/tSRoleChildWindow");
	}

	/**
	 * 保存角色-子窗口关联
	 * @param roleId
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveRoleChildWin")
	@ResponseBody
	public AjaxJson saveRoleChildWin(String roleId,HttpServletRequest request) {
		String message = "权限更新成功！";
		AjaxJson j = new AjaxJson();

		String left_top = request.getParameter("left_top");
		String center_top = request.getParameter("center_top");
		String right_top = request.getParameter("right_top");
		String left_bottom = request.getParameter("left_bottom");
		String center_bottom = request.getParameter("center_bottom");
		String right_bottom = request.getParameter("right_bottom");
		List<TSRoleChildWindowEntity> list = new ArrayList<>();

		try{
			if(StringUtils.isNotBlank(left_top)){
				systemService.executeSql("delete from t_s_role_child_window where role_id='"+roleId+"' and position='left_top'");
				TSRoleChildWindowEntity entity = new TSRoleChildWindowEntity(roleId,"left_top",left_top);
				list.add(entity);
			}
			if(StringUtils.isNotBlank(center_top)){
				systemService.executeSql("delete from t_s_role_child_window where role_id='"+roleId+"' and position='center_top'");
				TSRoleChildWindowEntity entity = new TSRoleChildWindowEntity(roleId,"center_top",center_top);
				list.add(entity);
			}
			if(StringUtils.isNotBlank(right_top)){
				systemService.executeSql("delete from t_s_role_child_window where role_id='"+roleId+"' and position='right_top'");
				TSRoleChildWindowEntity entity = new TSRoleChildWindowEntity(roleId,"right_top",right_top);
				list.add(entity);
			}
			if(StringUtils.isNotBlank(left_bottom)){
				systemService.executeSql("delete from t_s_role_child_window where role_id='"+roleId+"' and position='left_bottom'");
				TSRoleChildWindowEntity entity = new TSRoleChildWindowEntity(roleId,"left_bottom",left_bottom);
				list.add(entity);
			}
			if(StringUtils.isNotBlank(center_bottom)){
				systemService.executeSql("delete from t_s_role_child_window where role_id='"+roleId+"' and position='center_bottom'");
				TSRoleChildWindowEntity entity = new TSRoleChildWindowEntity(roleId,"center_bottom",center_bottom);
				list.add(entity);
			}
			if(StringUtils.isNotBlank(right_bottom)){
				systemService.executeSql("delete from t_s_role_child_window where role_id='"+roleId+"' and position='right_bottom'");
				TSRoleChildWindowEntity entity = new TSRoleChildWindowEntity(roleId,"right_bottom",right_bottom);
				list.add(entity);
			}
			systemService.batchSave(list);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch (Exception e){
			e.printStackTrace();
			message = "权限更新失败";
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_ERROR);
		}

		j.setMsg(message);
		return j;
	}

	/**
	 * 用户-子窗口关联列表页面跳转
	 * @param roleId
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "goEditUserChildWin")
	public ModelAndView goEditUserChildWin(String roleId, HttpServletRequest req) {
		CriteriaQuery cq = new CriteriaQuery(TSChildWindowsEntity.class);
		cq.eq("isUsed","1");
		cq.addOrder("createDate", SortDirection.asc);
		cq.add();
		List<TSChildWindowsEntity> childWindowList = systemService.getListByCriteriaQuery(cq,false);
		List<TSChildWindowsEntity> centerList = new ArrayList<>();
		List<TSChildWindowsEntity> aroundList = new ArrayList<>();

		if(!childWindowList.isEmpty() && childWindowList.size()>0){
			for (TSChildWindowsEntity entity : childWindowList) {
				String isCenter = entity.getIsCenter();
				if("1".equals(isCenter)){
					centerList.add(entity);
				}else{
					aroundList.add(entity);
				}
			}
		}
		req.setAttribute("centerList",centerList);
		req.setAttribute("aroundList",aroundList);

		TSUser user = ResourceUtil.getSessionUserName();
		cq = new CriteriaQuery(TSUserChildWindowEntity.class);
		cq.eq("userId",user.getId());
		cq.add();
		List<TSUserChildWindowEntity> userChildWindowList = systemService.getListByCriteriaQuery(cq,false);
		if(!userChildWindowList.isEmpty() && userChildWindowList.size()>0){
			for (TSUserChildWindowEntity entity : userChildWindowList) {
				req.setAttribute(entity.getPosition(),entity.getChildWindowId());
			}
		}
		req.setAttribute("userId",user.getId());
		return new ModelAndView("com/sdzk/sys/childwindow/tSUserChildWindow");
	}

	/**
	 * 保存用户-子窗口关联
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "saveUserChildWin")
	@ResponseBody
	public AjaxJson saveUserChildWin(HttpServletRequest request) {
		String message = "配置成功！";
		AjaxJson j = new AjaxJson();
		TSUser user = ResourceUtil.getSessionUserName();

		String left_top = request.getParameter("left_top");
		String center_top = request.getParameter("center_top");
		String right_top = request.getParameter("right_top");
		String left_bottom = request.getParameter("left_bottom");
		String center_bottom = request.getParameter("center_bottom");
		String right_bottom = request.getParameter("right_bottom");
		List<TSUserChildWindowEntity> list = new ArrayList<>();

		try{
			if(StringUtils.isNotBlank(left_top)){
				systemService.executeSql("delete from t_s_user_child_window where user_id='"+user.getId()+"' and position='left_top'");
				TSUserChildWindowEntity entity = new TSUserChildWindowEntity(user.getId(),"left_top",left_top);
				list.add(entity);
			}
			if(StringUtils.isNotBlank(center_top)){
				systemService.executeSql("delete from t_s_user_child_window where user_id='"+user.getId()+"' and position='center_top'");
				TSUserChildWindowEntity entity = new TSUserChildWindowEntity(user.getId(),"center_top",center_top);
				list.add(entity);
			}
			if(StringUtils.isNotBlank(right_top)){
				systemService.executeSql("delete from t_s_user_child_window where user_id='"+user.getId()+"' and position='right_top'");
				TSUserChildWindowEntity entity = new TSUserChildWindowEntity(user.getId(),"right_top",right_top);
				list.add(entity);
			}
			if(StringUtils.isNotBlank(left_bottom)){
				systemService.executeSql("delete from t_s_user_child_window where user_id='"+user.getId()+"' and position='left_bottom'");
				TSUserChildWindowEntity entity = new TSUserChildWindowEntity(user.getId(),"left_bottom",left_bottom);
				list.add(entity);
			}
			if(StringUtils.isNotBlank(center_bottom)){
				systemService.executeSql("delete from t_s_user_child_window where user_id='"+user.getId()+"' and position='center_bottom'");
				TSUserChildWindowEntity entity = new TSUserChildWindowEntity(user.getId(),"center_bottom",center_bottom);
				list.add(entity);
			}
			if(StringUtils.isNotBlank(right_bottom)){
				systemService.executeSql("delete from t_s_user_child_window where user_id='"+user.getId()+"' and position='right_bottom'");
				TSUserChildWindowEntity entity = new TSUserChildWindowEntity(user.getId(),"right_bottom",right_bottom);
				list.add(entity);
			}
			systemService.batchSave(list);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch (Exception e){
			e.printStackTrace();
			message = "配置失败";
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_ERROR);
		}

		j.setMsg(message);
		return j;
	}

	/**
	 * 首页配置预览跳转
	 * @param request
	 * @return
	 */
	/*@RequestMapping(params = "goPreview")
	public ModelAndView goPreview(HttpServletRequest request) {
		String left_top = request.getParameter("left_top");
		String center_top = request.getParameter("center_top");
		String right_top = request.getParameter("right_top");
		String left_bottom = request.getParameter("left_bottom");
		String center_bottom = request.getParameter("center_bottom");
		String right_bottom = request.getParameter("right_bottom");

		return new ModelAndView("com/sdzk/sys/childwindow/preview");
	}*/
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TSChildWindowsEntity> list() {
		List<TSChildWindowsEntity> listTSChildWindowss=childWindowsService.getList(TSChildWindowsEntity.class);
		return listTSChildWindowss;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TSChildWindowsEntity task = childWindowsService.get(TSChildWindowsEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TSChildWindowsEntity tSChildWindows, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TSChildWindowsEntity>> failures = validator.validate(tSChildWindows);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		childWindowsService.save(tSChildWindows);

		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tSChildWindows.getId();
		URI uri = uriBuilder.path("/rest/tSChildWindowsController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TSChildWindowsEntity tSChildWindows) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TSChildWindowsEntity>> failures = validator.validate(tSChildWindows);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		childWindowsService.saveOrUpdate(tSChildWindows);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		childWindowsService.deleteEntityById(TSChildWindowsEntity.class, id);
	}
}
