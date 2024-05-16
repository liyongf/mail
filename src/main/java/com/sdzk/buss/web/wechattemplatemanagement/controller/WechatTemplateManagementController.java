package com.sdzk.buss.web.wechattemplatemanagement.controller;

import com.sdzk.buss.web.wechattemplatemanagement.entity.WechatTemplateManagementEntity;
import com.sdzk.buss.web.wechattemplatemanagement.service.WechatTemplateManagementServiceI;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.validation.Validator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**   
 * @Title: Controller  
 * @Description: 微信模板管理表
 * @author onlineGenerator
 * @date 2018-08-09 10:23:42
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/wechatTemplateManagementController")
public class WechatTemplateManagementController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(WechatTemplateManagementController.class);

	@Autowired
	private WechatTemplateManagementServiceI wechatTemplateManagementService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 微信模板管理表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/wechattemplatemanagement/wechatTemplateManagementList");
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
	public void datagrid(WechatTemplateManagementEntity wechatTemplateManagement,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(WechatTemplateManagementEntity.class, dataGrid);
		if(StringUtil.isEmpty(wechatTemplateManagement.getId())){
			cq.isNull("parentModelName");
		}else{
			cq.eq("parentModelName", wechatTemplateManagement.getId());
			wechatTemplateManagement.setId(null);
		}
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wechatTemplateManagement, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.wechatTemplateManagementService.getDataGridReturn(cq, true);
		TagUtil.treegrid(response, dataGrid);
	}
	
	/**
	 * 删除微信模板管理表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(WechatTemplateManagementEntity wechatTemplateManagement, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		wechatTemplateManagement = systemService.getEntity(WechatTemplateManagementEntity.class, wechatTemplateManagement.getId());
		message = "微信模板管理表删除成功";
		try{
			wechatTemplateManagementService.delete(wechatTemplateManagement);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信模板管理表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除微信模板管理表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids, HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信模板管理表删除成功";
		try{
			for(String id:ids.split(",")){
				WechatTemplateManagementEntity wechatTemplateManagement = systemService.getEntity(WechatTemplateManagementEntity.class, 
				id
				);
				wechatTemplateManagementService.delete(wechatTemplateManagement);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "微信模板管理表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加微信模板管理表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(WechatTemplateManagementEntity wechatTemplateManagement, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信模板管理表添加成功";
		try{
			if(StringUtil.isEmpty(wechatTemplateManagement.getParentModelName())){
				wechatTemplateManagement.setParentModelName(null);
			}
			wechatTemplateManagementService.save(wechatTemplateManagement);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "微信模板管理表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新微信模板管理表
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(WechatTemplateManagementEntity wechatTemplateManagement, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "微信模板管理表更新成功";
		WechatTemplateManagementEntity t = wechatTemplateManagementService.get(WechatTemplateManagementEntity.class, wechatTemplateManagement.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(wechatTemplateManagement, t);
			if(StringUtil.isEmpty(t.getParentModelName())){
				t.setParentModelName(null);
			}
			wechatTemplateManagementService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "微信模板管理表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 微信模板管理表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(WechatTemplateManagementEntity wechatTemplateManagement, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wechatTemplateManagement.getId())) {
			wechatTemplateManagement = wechatTemplateManagementService.getEntity(WechatTemplateManagementEntity.class, wechatTemplateManagement.getId());
			req.setAttribute("wechatTemplateManagementPage", wechatTemplateManagement);
		}
		return new ModelAndView("com/sdzk/buss/web/wechattemplatemanagement/wechatTemplateManagement-add");
	}
	/**
	 * 微信模板管理表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(WechatTemplateManagementEntity wechatTemplateManagement, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(wechatTemplateManagement.getId())) {
			wechatTemplateManagement = wechatTemplateManagementService.getEntity(WechatTemplateManagementEntity.class, wechatTemplateManagement.getId());
			req.setAttribute("wechatTemplateManagementPage", wechatTemplateManagement);
		}
		return new ModelAndView("com/sdzk/buss/web/wechattemplatemanagement/wechatTemplateManagement-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","wechatTemplateManagementController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(WechatTemplateManagementEntity wechatTemplateManagement, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(WechatTemplateManagementEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, wechatTemplateManagement, request.getParameterMap());
		List<WechatTemplateManagementEntity> wechatTemplateManagements = this.wechatTemplateManagementService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"微信模板管理表");
		modelMap.put(NormalExcelConstants.CLASS,WechatTemplateManagementEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信模板管理表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,wechatTemplateManagements);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(WechatTemplateManagementEntity wechatTemplateManagement, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"微信模板管理表");
    	modelMap.put(NormalExcelConstants.CLASS,WechatTemplateManagementEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("微信模板管理表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<WechatTemplateManagementEntity> listWechatTemplateManagementEntitys = ExcelImportUtil.importExcel(file.getInputStream(),WechatTemplateManagementEntity.class,params);
				for (WechatTemplateManagementEntity wechatTemplateManagement : listWechatTemplateManagementEntitys) {
					wechatTemplateManagementService.save(wechatTemplateManagement);
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
	
}
