package com.sdzk.buss.web.rectprogressreport.controller;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.hiddendangerhistory.service.TBHiddenDangerHistoryServiceI;
import com.sdzk.buss.web.majorhiddendanger.entity.TBMajorHiddenDangerEntity;
import com.sdzk.buss.web.rectprogressreport.entity.TBRectProgressReportEntity;
import com.sdzk.buss.web.rectprogressreport.service.TBRectProgressReportServiceI;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**   
 * @Title: Controller  
 * @Description: 整改进度汇报
 * @author onlineGenerator
 * @date 2017-06-22 10:22:13
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBRectProgressReportController")
public class TBRectProgressReportController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBRectProgressReportController.class);

	@Autowired
	private TBRectProgressReportServiceI tBRectProgressReportService;
	@Autowired
	private SystemService systemService;
    @Autowired
    private UserService userService;
	@Autowired
	private Validator validator;
	@Autowired
	private TBHiddenDangerHistoryServiceI tbHiddenDangerHistoryService;
	


	/**
	 * 整改进度汇报列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("fkHiddenInfoId", ResourceUtil.getParameter("fkHiddenInfoId"));
        request.setAttribute("updateFlag", ResourceUtil.getParameter("updateFlag"));
		return new ModelAndView("com/sdzk/buss/web/rectprogressreport/tBRectProgressReportList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TBRectProgressReportEntity tBRectProgressReport,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBRectProgressReportEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBRectProgressReport, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBRectProgressReportService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除整改进度汇报
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBRectProgressReportEntity tBRectProgressReport, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tBRectProgressReport = systemService.getEntity(TBRectProgressReportEntity.class, tBRectProgressReport.getId());
		message = "整改进度汇报删除成功";
		try{
			tBRectProgressReportService.delete(tBRectProgressReport);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "整改进度汇报删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除整改进度汇报
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "整改进度汇报删除成功";
		try{
			for(String id:ids.split(",")){
				TBRectProgressReportEntity tBRectProgressReport = systemService.getEntity(TBRectProgressReportEntity.class, 
				id
				);
				tBRectProgressReportService.delete(tBRectProgressReport);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "整改进度汇报删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加整改进度汇报
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBRectProgressReportEntity tBRectProgressReport, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "整改进度汇报添加成功";
		try{
			tBRectProgressReportService.save(tBRectProgressReport);
			if (Constants.MAJOR_HIDDEN_DANGER_RECTIFY_COMPLETE.equals(tBRectProgressReport.getIsComplete())) {
				//整改完成时更新重大隐患表中的整改信息
				TBMajorHiddenDangerEntity majorHiddenDangerEntity = systemService.getEntity(TBMajorHiddenDangerEntity.class, tBRectProgressReport.getFkHiddenInfoId());
				if (majorHiddenDangerEntity!=null) {
					String oldStatus =  majorHiddenDangerEntity.getClStatus();
					majorHiddenDangerEntity.setRectMan(ResourceUtil.getParameter("rectMan"));
					majorHiddenDangerEntity.setRectMeasures(ResourceUtil.getParameter("rectMeasures"));
					majorHiddenDangerEntity.setRectTagartDt(DateUtils.str2Date(ResourceUtil.getParameter("rectTagartDt"), DateUtils.date_sdf));
					majorHiddenDangerEntity.setRectUnit(ResourceUtil.getParameter("rectUnit"));
					majorHiddenDangerEntity.setClStatus(Constants.HIDDEN_DANGER_CLSTATUS_ACCEPT);
					systemService.saveOrUpdate(majorHiddenDangerEntity);
					tbHiddenDangerHistoryService.saveHistory(oldStatus, majorHiddenDangerEntity);
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "整改进度汇报添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新整改进度汇报
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBRectProgressReportEntity tBRectProgressReport, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "整改进度汇报更新成功";
		TBRectProgressReportEntity t = tBRectProgressReportService.get(TBRectProgressReportEntity.class, tBRectProgressReport.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBRectProgressReport, t);
			tBRectProgressReportService.saveOrUpdate(t);
			if (Constants.MAJOR_HIDDEN_DANGER_RECTIFY_COMPLETE.equals(tBRectProgressReport.getIsComplete())) {
				//整改完成时更新重大隐患表中的整改信息
				TBMajorHiddenDangerEntity majorHiddenDangerEntity = systemService.getEntity(TBMajorHiddenDangerEntity.class, tBRectProgressReport.getFkHiddenInfoId());
				if (majorHiddenDangerEntity!=null) {
					String oldStatus =  majorHiddenDangerEntity.getClStatus();
					majorHiddenDangerEntity.setRectMan(ResourceUtil.getParameter("rectMan"));
					majorHiddenDangerEntity.setRectMeasures(ResourceUtil.getParameter("rectMeasures"));
					majorHiddenDangerEntity.setRectTagartDt(DateUtils.str2Date(ResourceUtil.getParameter("rectTagartDt"), DateUtils.date_sdf));
					majorHiddenDangerEntity.setRectUnit(ResourceUtil.getParameter("rectUnit"));
					majorHiddenDangerEntity.setClStatus(Constants.HIDDEN_DANGER_CLSTATUS_ACCEPT);
					systemService.saveOrUpdate(majorHiddenDangerEntity);
					tbHiddenDangerHistoryService.saveHistory(oldStatus, majorHiddenDangerEntity);
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "整改进度汇报更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 整改进度汇报新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBRectProgressReportEntity tBRectProgressReport, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBRectProgressReport.getId())) {
			tBRectProgressReport = tBRectProgressReportService.getEntity(TBRectProgressReportEntity.class, tBRectProgressReport.getId());
		}
		req.setAttribute("tBRectProgressReportPage", tBRectProgressReport);
		return new ModelAndView("com/sdzk/buss/web/rectprogressreport/tBRectProgressReport-add");
	}
	/**
	 * 整改进度汇报编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBRectProgressReportEntity tBRectProgressReport, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBRectProgressReport.getId())) {
			tBRectProgressReport = tBRectProgressReportService.getEntity(TBRectProgressReportEntity.class, tBRectProgressReport.getId());
		}
        TSUser rp = userService.getEntity(TSUser.class,tBRectProgressReport.getReportPerson());
        tBRectProgressReport.setReportPersonName(rp.getRealName());
		req.setAttribute("tBRectProgressReportPage", tBRectProgressReport);
		if (StringUtil.isNotEmpty(tBRectProgressReport.getFkHiddenInfoId())){
			TBMajorHiddenDangerEntity entity = systemService.get(TBMajorHiddenDangerEntity.class, tBRectProgressReport.getFkHiddenInfoId());
			req.setAttribute("tBMajorHiddenDangerPage", entity);
		}
		if ("detail".equals(ResourceUtil.getParameter("load"))) {
			return new ModelAndView("com/sdzk/buss/web/rectprogressreport/tBRectProgressReport-detail");
		}
		return new ModelAndView("com/sdzk/buss/web/rectprogressreport/tBRectProgressReport-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBRectProgressReportController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBRectProgressReportEntity tBRectProgressReport,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBRectProgressReportEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBRectProgressReport, request.getParameterMap());
		List<TBRectProgressReportEntity> tBRectProgressReports = this.tBRectProgressReportService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"整改进度汇报");
		modelMap.put(NormalExcelConstants.CLASS,TBRectProgressReportEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("整改进度汇报列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBRectProgressReports);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBRectProgressReportEntity tBRectProgressReport,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"整改进度汇报");
    	modelMap.put(NormalExcelConstants.CLASS,TBRectProgressReportEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("整改进度汇报列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TBRectProgressReportEntity> listTBRectProgressReportEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TBRectProgressReportEntity.class,params);
				for (TBRectProgressReportEntity tBRectProgressReport : listTBRectProgressReportEntitys) {
					tBRectProgressReportService.save(tBRectProgressReport);
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
	public List<TBRectProgressReportEntity> list() {
		List<TBRectProgressReportEntity> listTBRectProgressReports=tBRectProgressReportService.getList(TBRectProgressReportEntity.class);
		return listTBRectProgressReports;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TBRectProgressReportEntity task = tBRectProgressReportService.get(TBRectProgressReportEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TBRectProgressReportEntity tBRectProgressReport, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBRectProgressReportEntity>> failures = validator.validate(tBRectProgressReport);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBRectProgressReportService.save(tBRectProgressReport);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tBRectProgressReport.getId();
		URI uri = uriBuilder.path("/rest/tBRectProgressReportController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TBRectProgressReportEntity tBRectProgressReport) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBRectProgressReportEntity>> failures = validator.validate(tBRectProgressReport);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBRectProgressReportService.saveOrUpdate(tBRectProgressReport);
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
		tBRectProgressReportService.deleteEntityById(TBRectProgressReportEntity.class, id);
	}
}
