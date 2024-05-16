package com.sdzk.buss.web.riskalert.controller;

import com.sdzk.buss.web.riskalert.entity.TBAlertLevelColorEntity;
import com.sdzk.buss.web.riskalert.service.TBAlertLevelColorServiceI;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**   
 * @Title: Controller
 * @Description: 预警等级颜色
 * @author zhangdaihao
 * @date 2017-05-17 10:29:58
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAlertLevelColorController")
public class TBAlertLevelColorController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBAlertLevelColorController.class);

	@Autowired
	private TBAlertLevelColorServiceI tBAlertLevelColorService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 预警等级颜色列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/riskalert/tBAlertLevelColorList");
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
	public void datagrid(TBAlertLevelColorEntity tBAlertLevelColor,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBAlertLevelColorEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAlertLevelColor, request.getParameterMap());
		String belongMine = (String) ContextHolderUtils.getSession().getAttribute("belongMine");
		if(StringUtils.isNotBlank(belongMine)){
			cq.eq("belongMine",belongMine);
		}
		cq.add();
		this.tBAlertLevelColorService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除预警等级颜色
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBAlertLevelColorEntity tBAlertLevelColor, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBAlertLevelColor = systemService.getEntity(TBAlertLevelColorEntity.class, tBAlertLevelColor.getId());
		message = "预警等级颜色删除成功";
		try{
			tBAlertLevelColorService.delete(tBAlertLevelColor);
			systemService.addLog("预警等级颜色\""+tBAlertLevelColor.getId()+"\"删除成功", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		}catch(Exception e){
			e.printStackTrace();
			message = "预警等级颜色删除失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除预警等级颜色
	 *
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "预警等级颜色删除成功";
		try{
			for(String id:ids.split(",")){
				TBAlertLevelColorEntity tBAlertLevelColor = systemService.getEntity(TBAlertLevelColorEntity.class,id);
				tBAlertLevelColorService.delete(tBAlertLevelColor);
				systemService.addLog("预警等级颜色\""+tBAlertLevelColor.getId()+"\"删除成功", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "预警等级颜色删除失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加预警等级颜色
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBAlertLevelColorEntity tBAlertLevelColor, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "预警等级颜色添加成功";
		try{
			String belongMine = (String) ContextHolderUtils.getSession()
					.getAttribute("belongMine");
			tBAlertLevelColor.setBelongMine(belongMine);
			tBAlertLevelColorService.save(tBAlertLevelColor);
			systemService.addLog("预警等级颜色\""+tBAlertLevelColor.getId()+"\"添加成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}catch(Exception e){
			e.printStackTrace();
			message = "预警等级颜色添加失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_INSERT);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新预警等级颜色
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBAlertLevelColorEntity tBAlertLevelColor, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "预警等级颜色更新成功";
		TBAlertLevelColorEntity t = tBAlertLevelColorService.get(TBAlertLevelColorEntity.class, tBAlertLevelColor.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBAlertLevelColor, t);
			tBAlertLevelColorService.saveOrUpdate(t);
			systemService.addLog("预警等级颜色\""+t.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "预警等级颜色更新失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPDATE);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 预警等级颜色新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBAlertLevelColorEntity tBAlertLevelColor, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAlertLevelColor.getId())) {
			tBAlertLevelColor = tBAlertLevelColorService.getEntity(TBAlertLevelColorEntity.class, tBAlertLevelColor.getId());
			req.setAttribute("tBAlertLevelColorPage", tBAlertLevelColor);
		}
		return new ModelAndView("com/sdzk/buss/web/riskalert/tBAlertLevelColor-add");
	}
	/**
	 * 预警等级颜色编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBAlertLevelColorEntity tBAlertLevelColor, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAlertLevelColor.getId())) {
			tBAlertLevelColor = tBAlertLevelColorService.getEntity(TBAlertLevelColorEntity.class, tBAlertLevelColor.getId());
			req.setAttribute("tBAlertLevelColorPage", tBAlertLevelColor);
		}
		return new ModelAndView("com/sdzk/buss/web/riskalert/tBAlertLevelColor-update");
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBAlertLevelColorController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBAlertLevelColorEntity tBAlertLevelColor,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBAlertLevelColorEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAlertLevelColor, request.getParameterMap());
		List<TBAlertLevelColorEntity> tBAlertLevelColors = this.tBAlertLevelColorService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"预警等级颜色");
		modelMap.put(NormalExcelConstants.CLASS,TBAlertLevelColorEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("预警等级颜色列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBAlertLevelColors);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBAlertLevelColorEntity tBAlertLevelColor,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(NormalExcelConstants.FILE_NAME,"预警等级颜色");
		modelMap.put(NormalExcelConstants.CLASS,TBAlertLevelColorEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("预警等级颜色列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
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
			// 获取上传文件对象
			MultipartFile file = entity.getValue();
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TBAlertLevelColorEntity> listtBAlertLevelColorEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TBAlertLevelColorEntity.class, params);
				for (TBAlertLevelColorEntity tBAlertLevelColor : listtBAlertLevelColorEntitys) {
					tBAlertLevelColorService.save(tBAlertLevelColor);
					systemService.addLog("预警等级颜色\""+tBAlertLevelColor.getId()+"\"导入成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
				}
				j.setMsg("文件导入成功！");
				systemService.addLog(j.getMsg(), Globals.Log_Leavel_INFO, Globals.Log_Type_UPLOAD);
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				systemService.addLog(j.getMsg()+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPLOAD);
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
	 * 隐患编号校验
	 *
	 * @return
	 */
	@RequestMapping(params = "checkAlertLevel")
	@ResponseBody
	public AjaxJson checkAlertLevel(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String alertLevel = request.getParameter("alertLevel");
		String belongMine = (String) ContextHolderUtils.getSession().getAttribute("belongMine");
		if(StringUtils.isNotBlank(alertLevel)){
			//查询是否已经存在
			CriteriaQuery cq = new CriteriaQuery(TBAlertLevelColorEntity.class);
			try{
				cq.eq("alertLevelName",alertLevel);
				cq.eq("belongMine",belongMine);
			}catch(Exception e){
				e.printStackTrace();
			}
			cq.add();
			List<TBAlertLevelColorEntity> list = systemService.getListByCriteriaQuery(cq,false);
			if(list != null && !list.isEmpty()){
				message = "该预警等级已存在，请勿重复添加";
				j.setSuccess(false);
			}else{
				message = "校验通过";
				j.setSuccess(true);
			}
		}else{
			message = "预警等级名称不能为空";
			j.setSuccess(false);
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "getAlertLevelColor")
	@ResponseBody
	public AjaxJson getAlertLevelColor(HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> resMap = new HashMap<String, Object>();
		CriteriaQuery cq = new CriteriaQuery(TBAlertLevelColorEntity.class);
		cq.add();
		List<TBAlertLevelColorEntity> list = systemService.getListByCriteriaQuery(cq, false);
		resMap.put("resultList", list);
		j.setAttributes(resMap);
		return j;
	}

}
