package com.sdzk.buss.web.health.controller;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.excelverify.LaborProtectExcelVerifyHandler;
import com.sdzk.buss.web.health.entity.TBEmployeeInfoEntity;
import com.sdzk.buss.web.health.entity.TBLaborProtectEntity;
import com.sdzk.buss.web.health.service.TBEmployeeInfoServiceI;
import com.sdzk.buss.web.health.service.TBLaborProtectServiceI;
import org.apache.commons.lang3.StringUtils;
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
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateWordConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 职工劳动防护专项档案
 * @author hansf
 * @date 2016-03-02 08:52:54
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBLaborProtectController")
public class TBLaborProtectController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBLaborProtectController.class);

	@Autowired
	private TBLaborProtectServiceI tBLaborProtectService;
	@Autowired
	private TBEmployeeInfoServiceI tBEmployeeInfoService;
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
	 * 职工劳动防护专项档案列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/health/tBLaborProtectList");
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
	public void datagrid(TBLaborProtectEntity tBLaborProtect,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBLaborProtectEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBLaborProtect, request.getParameterMap());
        cq.addOrder("createDate", SortDirection.desc);
		cq.add();
		this.tBLaborProtectService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除职工劳动防护专项档案
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBLaborProtectEntity tBLaborProtect, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBLaborProtect = systemService.getEntity(TBLaborProtectEntity.class, tBLaborProtect.getId());
		message = "职工劳动防护专项档案删除成功";
		try{
			tBLaborProtectService.delete(tBLaborProtect);
			systemService.addLog("职工劳动防护专项档案\""+tBLaborProtect.getId()+"\"删除成功", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		}catch(Exception e){
			e.printStackTrace();
			message = "职工劳动防护专项档案删除失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除职工劳动防护专项档案
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "职工劳动防护专项档案删除成功";
		try{
			for(String id:ids.split(",")){
				TBLaborProtectEntity tBLaborProtect = systemService.getEntity(TBLaborProtectEntity.class,id);
				tBLaborProtectService.delete(tBLaborProtect);
				systemService.addLog("职工劳动防护专项档案\""+tBLaborProtect.getId()+"\"删除成功", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "职工劳动防护专项档案删除失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加职工劳动防护专项档案
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBLaborProtectEntity tBLaborProtect, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "职工劳动防护专项档案添加成功";
		try{
			tBLaborProtectService.save(tBLaborProtect);
			systemService.addLog("职工劳动防护专项档案\""+tBLaborProtect.getId()+"\"添加成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}catch(Exception e){
			e.printStackTrace();
			message = "职工劳动防护专项档案添加失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_INSERT);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新职工劳动防护专项档案
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBLaborProtectEntity tBLaborProtect, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "职工劳动防护专项档案更新成功";
		TBLaborProtectEntity t = tBLaborProtectService.get(TBLaborProtectEntity.class, tBLaborProtect.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBLaborProtect, t);
			tBLaborProtectService.saveOrUpdate(t);
			systemService.addLog("职工劳动防护专项档案\""+t.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "职工劳动防护专项档案更新失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPDATE);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 职工劳动防护专项档案新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBLaborProtectEntity tBLaborProtect, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBLaborProtect.getId())) {
			tBLaborProtect = tBLaborProtectService.getEntity(TBLaborProtectEntity.class, tBLaborProtect.getId());
			req.setAttribute("tBLaborProtectPage", tBLaborProtect);
		}
		return new ModelAndView("com/sdzk/buss/web/health/tBLaborProtect-add");
	}
	/**
	 * 职工劳动防护专项档案编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBLaborProtectEntity tBLaborProtect, HttpServletRequest req) {
        String load = req.getParameter("load");
        req.setAttribute("load",load);
		if (StringUtil.isNotEmpty(tBLaborProtect.getId())) {
			tBLaborProtect = tBLaborProtectService.getEntity(TBLaborProtectEntity.class, tBLaborProtect.getId());
			req.setAttribute("tBLaborProtectPage", tBLaborProtect);
		}
        if(StringUtils.isNotBlank(load)){
            return new ModelAndView("com/sdzk/buss/web/health/tBLaborProtect-detail");
        }else{
            return new ModelAndView("com/sdzk/buss/web/health/tBLaborProtect-update");
        }
		/*return new ModelAndView("com/sdzk/buss/web/health/tBLaborProtect-update");*/
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBLaborProtectController");
		req.setAttribute("importfun_name","importExcelLaborProtect");
		return new ModelAndView("common/upload/pub_excel_import");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBLaborProtectEntity tBLaborProtect,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBLaborProtectEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBLaborProtect, request.getParameterMap());
		List<TBLaborProtectEntity> tBLaborProtects = this.tBLaborProtectService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"职工劳动防护专项档案");
		modelMap.put(NormalExcelConstants.CLASS,TBLaborProtectEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("职工劳动防护专项档案列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBLaborProtects);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBLaborProtectEntity tBLaborProtect,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"职工劳动防护专项档案");
    	modelMap.put(NormalExcelConstants.CLASS,TBLaborProtectEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("职工劳动防护专项档案列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
    	"导出信息"));
    	modelMap.put(NormalExcelConstants.DATA_LIST,new ArrayList());
    	return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	
	/**
	 * 导出Word 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportDocByT")
	public String exportDocByT(TBLaborProtectEntity tBLaborProtect,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBLaborProtectEntity.class, dataGrid);
		try {
			if(StringUtil.isNotEmpty(request.getParameter("unitName"))){
				tBLaborProtect.setUnitName((new String(request.getParameter("unitName").getBytes("ISO-8859-1"),"UTF-8")));
			}
			if(StringUtil.isNotEmpty(request.getParameter("tBEmployeeInfoEntity.name"))){
				tBLaborProtect.gettBEmployeeInfoEntity().setName((new String(request.getParameter("tBEmployeeInfoEntity.name").getBytes("ISO-8859-1"),"UTF-8")));
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBLaborProtect, request.getParameterMap());
		cq.add();
		String beginDate = request.getParameter("receiveDate_begin");
		String endDate = request.getParameter("receiveDate_end");
		List<TBLaborProtectEntity> tBLaborProtectList = this.tBLaborProtectService.getListByCriteriaQuery(cq, false);
		Map<String,Object> map = new HashMap<String, Object>();
		if(beginDate.trim().equals("")){
			map.put("Q1", "----");
		}else{
			map.put("Q1", beginDate);
		}
		if(endDate.trim().equals("")){
			map.put("Q2", "----");
		}else{
			map.put("Q2", endDate);
		}
		map.put("list", tBLaborProtectList);
		modelMap.put(TemplateWordConstants.FILE_NAME,"劳动防护用品发放档案");
		modelMap.put(TemplateWordConstants.MAP_DATA,map);
		modelMap.put(TemplateWordConstants.URL,"export/template/exportTemp_LaborProtect.docx");
		return TemplateWordConstants.JEECG_TEMPLATE_WORD_VIEW;
	}
	
	/**
	 * 导入excel模板下载
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "importXlsT")
	public String importXlsT(HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME,"职工个人防护用品领取记录导入模板");
		TemplateExportParams templateExportParams = new TemplateExportParams();
    	templateExportParams.setSheetNum(0);
    	templateExportParams.setTemplateUrl("export/template/importTemp_laborProtect.xls");
    	modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
    	 modelMap.put(TemplateExcelConstants.MAP_DATA,new HashMap<String, Object>());
    	return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	@RequestMapping(params = "importExcelLaborProtect", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcelLaborProtect(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
			MultipartFile file = entity.getValue();
			ImportParams params = new ImportParams();
			params.setTitleRows(1);
			params.setHeadRows(1);
			params.setNeedSave(false);
			params.setNeedVerfiy(true);
			params.setVerifyHanlder(new LaborProtectExcelVerifyHandler());
			try {
				ExcelImportResult<TBLaborProtectEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(), TBLaborProtectEntity.class, params);
				if(result.isVerfiyFail()){
					String uploadpathtemp = ResourceUtil.getConfigByName("uploadpathtemp");
                    // 文件的硬盘真实路径
					String realPath = multipartRequest.getSession().getServletContext().getRealPath("/") + "/" + uploadpathtemp+"/";
					File fileTemp = new File(realPath);
					if (!fileTemp.exists()) {
                        // 创建根目录
						fileTemp.mkdirs();
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
					for (int i = 0; i < result.getList().size(); i++) {
						TBLaborProtectEntity labor = result.getList().get(i);
						if(StringUtil.isEmpty(labor.getFileNoTemp())){
							continue;
						}
						//根据档案号查询员工信息
						CriteriaQuery cq = new CriteriaQuery(TBEmployeeInfoEntity.class);

						cq.eq("fileNo", labor.getFileNoTemp());

						cq.eq("isDelete", Constants.IS_DELETE_N);
						cq.add();
						List<TBEmployeeInfoEntity>  oldemployeeList = tBEmployeeInfoService.getListByCriteriaQuery(cq, false);
						if(oldemployeeList!=null&&oldemployeeList.size()>0){
							TBEmployeeInfoEntity employee = oldemployeeList.get(0);
							labor.settBEmployeeInfoEntity(employee);

							tBLaborProtectService.save(labor);
							systemService.addLog("职工劳动防护专项档案\""+labor.getId()+"\"导入成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
						}
				    }
					j.setMsg("文件导入成功！");
					systemService.addLog(j.getMsg(), Globals.Log_Leavel_INFO, Globals.Log_Type_UPLOAD);
				}
				
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
}
