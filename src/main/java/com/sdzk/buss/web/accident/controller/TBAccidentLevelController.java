package com.sdzk.buss.web.accident.controller;
import com.sdzk.buss.web.accident.entity.TBAccidentLevelEntity;
import com.sdzk.buss.web.accident.service.TBAccidentLevelServiceI;
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
 * @Description: 事故等级
 * @author onlineGenerator
 * @date 2016-05-06 08:53:25
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBAccidentLevelController")
public class TBAccidentLevelController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBAccidentLevelController.class);

	@Autowired
	private TBAccidentLevelServiceI tBAccidentLevelService;
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
	 * 事故等级列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/accident/tBAccidentLevelList");
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
	public void datagrid(TBAccidentLevelEntity tBAccidentLevel,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBAccidentLevelEntity.class, dataGrid);
        //查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAccidentLevel, request.getParameterMap());
		try{
		//自定义追加查询条件
            cq.addOrder("sortindex", SortDirection.asc);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
        cq.addOrder("createDate", SortDirection.desc);
		cq.add();
		this.tBAccidentLevelService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除事故等级
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBAccidentLevelEntity tBAccidentLevel, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBAccidentLevel = systemService.getEntity(TBAccidentLevelEntity.class, tBAccidentLevel.getId());
		message = "事故等级删除成功";
		try{
			tBAccidentLevelService.delete(tBAccidentLevel);
			systemService.addLog("事故等级\""+tBAccidentLevel.getId()+"\"删除成功", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		}catch(Exception e){
			e.printStackTrace();
			message = "事故等级删除失败";
			systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除事故等级
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "事故等级删除成功";
		try{
			for(String id:ids.split(",")){
				TBAccidentLevelEntity tBAccidentLevel = systemService.getEntity(TBAccidentLevelEntity.class, 
				id
				);
				tBAccidentLevelService.delete(tBAccidentLevel);
				systemService.addLog("事故等级\""+tBAccidentLevel.getId()+"\"删除成功", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "事故等级删除失败";
			systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加事故等级
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBAccidentLevelEntity tBAccidentLevel, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "事故等级添加成功";
		try{
			tBAccidentLevelService.save(tBAccidentLevel);
			systemService.addLog("事故等级\""+tBAccidentLevel.getId()+"\"添加成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}catch(Exception e){
			e.printStackTrace();
			message = "事故等级添加失败";
			systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新事故等级
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBAccidentLevelEntity tBAccidentLevel, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "事故等级更新成功";
		TBAccidentLevelEntity t = tBAccidentLevelService.get(TBAccidentLevelEntity.class, tBAccidentLevel.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBAccidentLevel, t);
			tBAccidentLevelService.saveOrUpdate(t);
			systemService.addLog("事故等级\""+t.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "事故等级更新失败";
			systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_UPDATE);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 事故等级新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBAccidentLevelEntity tBAccidentLevel, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBAccidentLevel.getId())) {
			tBAccidentLevel = tBAccidentLevelService.getEntity(TBAccidentLevelEntity.class, tBAccidentLevel.getId());
			req.setAttribute("tBAccidentLevelPage", tBAccidentLevel);
		}
		return new ModelAndView("com/sdzk/buss/web/accident/tBAccidentLevel-add");
	}

    //accidentLevelExist/
    /**
     * 验证该事故等级是否已存在
     */
    @RequestMapping(params = "accidentLevelExist")
    @ResponseBody
    public Map<String, String> accidentLevelExist(TBAccidentLevelEntity entity, HttpServletRequest request){
        Map<String, String> result = new HashedMap();
        String level = request.getParameter("param");
        entity.setAccidentlevel(level);
        if (accidentLevelExist(entity)){
            result.put("status", "n");
            result.put("info","此事故等级已存在！");
        } else {
            result.put("status", "y");
            result.put("info","通过信息验证！");
        }
        return result;
    }
    private boolean accidentLevelExist(TBAccidentLevelEntity entity){
        boolean result = true;
        CriteriaQuery cq = new CriteriaQuery(TBAccidentLevelEntity.class);
        cq.eq("accidentlevel",entity.getAccidentlevel());
        cq.add();
        List<TBAccidentLevelEntity> accidentLevelList = systemService.getListByCriteriaQuery(cq, false);
        if (accidentLevelList == null || accidentLevelList.size() == 0 || accidentLevelList.get(0).getId().equals(entity.getId()) ){
            result = false;
        }
        return result;
    }
	/**
	 * 事故等级编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBAccidentLevelEntity tBAccidentLevel, HttpServletRequest req) {
        String load = req.getParameter("load");
        req.setAttribute("load",load);
		if (StringUtil.isNotEmpty(tBAccidentLevel.getId())) {
			tBAccidentLevel = tBAccidentLevelService.getEntity(TBAccidentLevelEntity.class, tBAccidentLevel.getId());
			req.setAttribute("tBAccidentLevelPage", tBAccidentLevel);
		}
        if(StringUtils.isNotBlank(load)){
            return new ModelAndView("com/sdzk/buss/web/accident/tBAccidentLevel-detail");
        }else{
            return new ModelAndView("com/sdzk/buss/web/accident/tBAccidentLevel-update");
        }
		/*return new ModelAndView("com/sdzk/buss/web/accident/tBAccidentLevel-update");*/
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBAccidentLevelController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBAccidentLevelEntity tBAccidentLevel,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBAccidentLevelEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBAccidentLevel, request.getParameterMap());
		List<TBAccidentLevelEntity> tBAccidentLevels = this.tBAccidentLevelService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"事故等级");
		modelMap.put(NormalExcelConstants.CLASS,TBAccidentLevelEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("事故等级列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBAccidentLevels);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBAccidentLevelEntity tBAccidentLevel,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"事故等级");
    	modelMap.put(NormalExcelConstants.CLASS,TBAccidentLevelEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("事故等级列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TBAccidentLevelEntity> listTBAccidentLevelEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TBAccidentLevelEntity.class,params);
				for (TBAccidentLevelEntity tBAccidentLevel : listTBAccidentLevelEntitys) {
					tBAccidentLevelService.save(tBAccidentLevel);
					systemService.addLog("事故等级\""+tBAccidentLevel.getId()+"\"导入成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
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
