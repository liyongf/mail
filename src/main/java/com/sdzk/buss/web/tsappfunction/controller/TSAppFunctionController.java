package com.sdzk.buss.web.tsappfunction.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sdzk.buss.web.tsappfunction.entity.TSAppFunctionEntity;
import com.sdzk.buss.web.tsappfunction.service.TSAppFunctionServiceI;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.ComboTree;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.TreeGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.tag.vo.easyui.ComboTreeModel;
import org.jeecgframework.tag.vo.easyui.TreeGridModel;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.net.URI;
import java.util.*;

/**   
 * @Title: Controller  
 * @Description: APP菜单管理
 * @author onlineGenerator
 * @date 2018-08-24 09:05:41
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tSAppFunctionController")
public class TSAppFunctionController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSAppFunctionController.class);

	@Autowired
	private TSAppFunctionServiceI tSAppFunctionService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * APP菜单管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/tsappfunction/tSAppFunctionList");
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
	public void datagrid(TSAppFunctionEntity tSAppFunction,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSAppFunctionEntity.class, dataGrid);
		//查询条件组装器
		HqlGenerateUtil.installHql(cq, tSAppFunction, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tSAppFunctionService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 权限列表
	 */
	@RequestMapping(params = "functionGrid")
	@ResponseBody
	public Object functionGrid(HttpServletRequest request, TreeGrid treegrid, Integer type) {
		CriteriaQuery cq = new CriteriaQuery(TSAppFunctionEntity.class);
		String selfId = request.getParameter("selfId");
		if (selfId != null) {
			cq.notEq("id", selfId);
		}
		if (treegrid.getId() != null) {
			cq.eq("TSAppFunctionEntity.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("TSAppFunctionEntity");
		}
		cq.addOrder("functionorder", SortDirection.asc);
		cq.add();

		//获取装载数据权限的条件HQL
		cq = HqlGenerateUtil.getDataAuthorConditionHql(cq, new TSAppFunctionEntity());
		cq.add();


		List<TSAppFunctionEntity> functionList = systemService.getListByCriteriaQuery(cq, false);

//		Collections.sort(functionList, new NumberComparator());

		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setTextField("functionname");
		treeGridModel.setParentText("TSAppFunctionEntity_functionname");
		treeGridModel.setParentId("TSAppFunctionEntity_id");
		treeGridModel.setIdField("id");
		treeGridModel.setChildList("TSAppFunctionEntitys");

		treeGridModel.setCode("functionname");
		treeGridModel.setIcon("functionname");
		treeGridModel.setSrc("functionname");
		// 添加排序字段
		treeGridModel.setOrder("functionorder");
		Map<String,Object> fieldMap = new HashMap<String, Object>();
		fieldMap.put("functioncode", "functioncode");
		fieldMap.put("functionorder", "functionorder");
		treeGridModel.setFieldMap(fieldMap);

		treeGrids = systemService.treegrid(functionList, treeGridModel);

		JSONArray jsonArray = new JSONArray();
		for (TreeGrid treeGrid : treeGrids) {
			jsonArray.add(JSON.parse(treeGrid.toJson()));
		}
		return jsonArray;
	}

	@RequestMapping(params = "setParentFunctionname")
	@ResponseBody
	public List<ComboTree> setParentFunctionname(HttpServletRequest request, ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSAppFunctionEntity.class);
		if(null != request.getParameter("selfId")){
			cq.notEq("id", request.getParameter("selfId"));
		}
		if (comboTree.getId() != null) {
			cq.eq("TSAppFunctionEntity.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("TSAppFunctionEntity");
		}
		cq.add();
		List<TSAppFunctionEntity> markList = systemService.getListByCriteriaQuery(cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "functionname", "TSAppFunctionEntitys");

		comboTrees = systemService.ComboTree(markList, comboTreeModel, null, false);
		return comboTrees;

	}

	/**
	 * 删除APP菜单管理
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSAppFunctionEntity tSAppFunction, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tSAppFunction = systemService.getEntity(TSAppFunctionEntity.class, tSAppFunction.getId());
		message = "APP菜单管理删除成功";
		try{
			tSAppFunctionService.delete(tSAppFunction);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "APP菜单管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除APP菜单管理
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "APP菜单管理删除成功";
		try{
			for(String id:ids.split(",")){
				TSAppFunctionEntity tSAppFunction = systemService.getEntity(TSAppFunctionEntity.class,
				id
				);
				tSAppFunctionService.delete(tSAppFunction);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "APP菜单管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加APP菜单管理
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSAppFunctionEntity tSAppFunction, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "APP菜单管理添加成功";
		// 设置上级菜单
		String pid = request.getParameter("TSAppFunctionEntity.id");
		if (pid.equals("")) {
			tSAppFunction.setTSAppFunctionEntity(null);
		}
		try{
			tSAppFunctionService.save(tSAppFunction);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "APP菜单管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新APP菜单管理
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSAppFunctionEntity tSAppFunction, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "APP菜单管理更新成功";
		// 设置上级菜单
		String pid = request.getParameter("TSAppFunctionEntity.id");
		if (pid.equals("")) {
			tSAppFunction.setTSAppFunctionEntity(null);
		}
		TSAppFunctionEntity t = tSAppFunctionService.get(TSAppFunctionEntity.class, tSAppFunction.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tSAppFunction, t);
			tSAppFunctionService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "APP菜单管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * APP菜单管理新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSAppFunctionEntity tSAppFunction, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSAppFunction.getId())) {
			tSAppFunction = tSAppFunctionService.getEntity(TSAppFunctionEntity.class, tSAppFunction.getId());
			req.setAttribute("tSAppFunctionPage", tSAppFunction);
		}
		return new ModelAndView("com/sdzk/buss/web/tsappfunction/tSAppFunction-add");
	}
	/**
	 * APP菜单管理编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSAppFunctionEntity tSAppFunction, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSAppFunction.getId())) {
			tSAppFunction = tSAppFunctionService.getEntity(TSAppFunctionEntity.class, tSAppFunction.getId());
			req.setAttribute("tSAppFunctionPage", tSAppFunction);
		}
		return new ModelAndView("com/sdzk/buss/web/tsappfunction/tSAppFunction-update");
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tSAppFunctionController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TSAppFunctionEntity tSAppFunction, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TSAppFunctionEntity.class, dataGrid);
		HqlGenerateUtil.installHql(cq, tSAppFunction, request.getParameterMap());
		List<TSAppFunctionEntity> tSAppFunctions = this.tSAppFunctionService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"APP菜单管理");
		modelMap.put(NormalExcelConstants.CLASS,TSAppFunctionEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("APP菜单管理列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tSAppFunctions);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TSAppFunctionEntity tSAppFunction, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"APP菜单管理");
    	modelMap.put(NormalExcelConstants.CLASS,TSAppFunctionEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("APP菜单管理列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
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
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TSAppFunctionEntity> listTSAppFunctionEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TSAppFunctionEntity.class,params);
				for (TSAppFunctionEntity tSAppFunction : listTSAppFunctionEntitys) {
					tSAppFunctionService.save(tSAppFunction);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
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
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TSAppFunctionEntity> list() {
		List<TSAppFunctionEntity> listTSAppFunctions=tSAppFunctionService.getList(TSAppFunctionEntity.class);
		return listTSAppFunctions;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TSAppFunctionEntity task = tSAppFunctionService.get(TSAppFunctionEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TSAppFunctionEntity tSAppFunction, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TSAppFunctionEntity>> failures = validator.validate(tSAppFunction);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tSAppFunctionService.save(tSAppFunction);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tSAppFunction.getId();
		URI uri = uriBuilder.path("/rest/tSAppFunctionController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TSAppFunctionEntity tSAppFunction) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TSAppFunctionEntity>> failures = validator.validate(tSAppFunction);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tSAppFunctionService.saveOrUpdate(tSAppFunction);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tSAppFunctionService.deleteEntityById(TSAppFunctionEntity.class, id);
	}
}
