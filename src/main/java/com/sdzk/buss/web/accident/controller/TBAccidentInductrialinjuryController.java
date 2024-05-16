package com.sdzk.buss.web.accident.controller;
import com.sdzk.buss.web.accident.entity.TBAccidentInductrialinjuryEntity;
import com.sdzk.buss.web.accident.service.TBAccidentInductrialinjuryServiceI;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.annotation.Scope;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import org.apache.commons.collections.map.HashedMap;

/**
 * @Title: Controller
 * @Description: 工伤等级
 * @author onlineGenerator
 * @date 2016-05-06 09:31:32
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAccidentInductrialinjuryController")
public class TBAccidentInductrialinjuryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBAccidentInductrialinjuryController.class);

	@Autowired
	private TBAccidentInductrialinjuryServiceI tBAccidentInductrialinjuryService;
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
	 * 工伤等级列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/accident/tBAccidentInductrialinjuryList");
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
	public void datagrid(TBAccidentInductrialinjuryEntity tBAccidentInductrialinjury,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBAccidentInductrialinjuryEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAccidentInductrialinjury, request.getParameterMap());
        try{
		//自定义追加查询条件
            cq.addOrder("sortindex", SortDirection.asc);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
        cq.addOrder("createDate", SortDirection.desc);
		cq.add();
		this.tBAccidentInductrialinjuryService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除工伤等级
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBAccidentInductrialinjuryEntity tBAccidentInductrialinjury, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBAccidentInductrialinjury = systemService.getEntity(TBAccidentInductrialinjuryEntity.class, tBAccidentInductrialinjury.getId());
		message = "工伤等级删除成功";
		try{
			tBAccidentInductrialinjuryService.delete(tBAccidentInductrialinjury);
			systemService.addLog("工伤等级\""+tBAccidentInductrialinjury.getId()+"\"删除成功", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		}catch(Exception e){
			e.printStackTrace();
			message = "工伤等级删除失败";
			systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除工伤等级
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "工伤等级删除成功";
		try{
			for(String id:ids.split(",")){
				TBAccidentInductrialinjuryEntity tBAccidentInductrialinjury = systemService.getEntity(TBAccidentInductrialinjuryEntity.class, 
				id
				);
				tBAccidentInductrialinjuryService.delete(tBAccidentInductrialinjury);
				systemService.addLog("工伤等级\""+tBAccidentInductrialinjury.getId()+"\"删除成功", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "工伤等级删除失败";
			systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加工伤等级
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBAccidentInductrialinjuryEntity tBAccidentInductrialinjury, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        message = "工伤等级添加成功";
		try{
			tBAccidentInductrialinjuryService.save(tBAccidentInductrialinjury);
			systemService.addLog("工伤等级\""+tBAccidentInductrialinjury.getId()+"\"添加成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}catch(Exception e){
			e.printStackTrace();
			message = "工伤等级添加失败";
			systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新工伤等级
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBAccidentInductrialinjuryEntity tBAccidentInductrialinjury, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "工伤等级更新成功";
		TBAccidentInductrialinjuryEntity t = tBAccidentInductrialinjuryService.get(TBAccidentInductrialinjuryEntity.class, tBAccidentInductrialinjury.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBAccidentInductrialinjury, t);
			tBAccidentInductrialinjuryService.saveOrUpdate(t);
			systemService.addLog("工伤等级\""+t.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "工伤等级更新失败";
			systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_UPDATE);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 工伤等级新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBAccidentInductrialinjuryEntity tBAccidentInductrialinjury, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAccidentInductrialinjury.getId())) {
			tBAccidentInductrialinjury = tBAccidentInductrialinjuryService.getEntity(TBAccidentInductrialinjuryEntity.class, tBAccidentInductrialinjury.getId());
			req.setAttribute("tBAccidentInductrialinjuryPage", tBAccidentInductrialinjury);
		}
		return new ModelAndView("com/sdzk/buss/web/accident/tBAccidentInductrialinjury-add");
	}
	/**
	 * 工伤等级编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBAccidentInductrialinjuryEntity tBAccidentInductrialinjury, HttpServletRequest req) {
        String load = req.getParameter("load");
        req.setAttribute("load",load);
		if (StringUtil.isNotEmpty(tBAccidentInductrialinjury.getId())) {
			tBAccidentInductrialinjury = tBAccidentInductrialinjuryService.getEntity(TBAccidentInductrialinjuryEntity.class, tBAccidentInductrialinjury.getId());
			req.setAttribute("tBAccidentInductrialinjuryPage", tBAccidentInductrialinjury);
		}
        if(StringUtils.isNotBlank(load)){
            return new ModelAndView("com/sdzk/buss/web/accident/tBAccidentInductrialinjury-detail");
        }else{
            return new ModelAndView("com/sdzk/buss/web/accident/tBAccidentInductrialinjury-update");
        }
		/*return new ModelAndView("com/sdzk/buss/web/accident/tBAccidentInductrialinjury-update");*/
	}


    //accidentLevelExist/
    /**
     * 验证该工伤等级是否已存在
     */
    @RequestMapping(params = "accidentInductrialInjuryExist")
    @ResponseBody
    public Map<String, String> accidentInductrialInjuryExist(TBAccidentInductrialinjuryEntity entity, HttpServletRequest request){
        Map<String, String> result = new HashedMap();
        String level = request.getParameter("param");
        entity.setInductrialinjurylevel(level);
        if (accidentInductrialInjuryExist(entity)){
            result.put("status", "n");
            result.put("info","此工伤等级已存在！");
        } else {
            result.put("status", "y");
            result.put("info","通过信息验证！");
        }
        return result;
    }
    private boolean accidentInductrialInjuryExist(TBAccidentInductrialinjuryEntity entity){
        boolean result = true;
        CriteriaQuery cq = new CriteriaQuery(TBAccidentInductrialinjuryEntity.class);
        cq.eq("inductrialinjurylevel",entity.getInductrialinjurylevel());
        cq.add();
        List<TBAccidentInductrialinjuryEntity> list = systemService.getListByCriteriaQuery(cq, false);
        if (list == null || list.size() == 0 || list.get(0).getId().equals(entity.getId())){
            result = false;
        }
        return result;
    }
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBAccidentInductrialinjuryController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBAccidentInductrialinjuryEntity tBAccidentInductrialinjury,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBAccidentInductrialinjuryEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAccidentInductrialinjury, request.getParameterMap());
		List<TBAccidentInductrialinjuryEntity> tBAccidentInductrialinjurys = this.tBAccidentInductrialinjuryService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"工伤等级");
		modelMap.put(NormalExcelConstants.CLASS,TBAccidentInductrialinjuryEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("工伤等级列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBAccidentInductrialinjurys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBAccidentInductrialinjuryEntity tBAccidentInductrialinjury,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"工伤等级");
    	modelMap.put(NormalExcelConstants.CLASS,TBAccidentInductrialinjuryEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("工伤等级列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TBAccidentInductrialinjuryEntity> listTBAccidentInductrialinjuryEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TBAccidentInductrialinjuryEntity.class,params);
				for (TBAccidentInductrialinjuryEntity tBAccidentInductrialinjury : listTBAccidentInductrialinjuryEntitys) {
					tBAccidentInductrialinjuryService.save(tBAccidentInductrialinjury);
					systemService.addLog("工伤等级\""+tBAccidentInductrialinjury.getId()+"\"导入成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
				}
				j.setMsg("文件导入成功！");
				systemService.addLog(j.getMsg(),Globals.Log_Leavel_INFO,Globals.Log_Type_UPLOAD);
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				systemService.addLog(j.getMsg()+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_UPLOAD);
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
