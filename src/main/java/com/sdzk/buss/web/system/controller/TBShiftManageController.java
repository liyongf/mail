package com.sdzk.buss.web.system.controller;

import com.sdzk.buss.web.system.entity.TBShiftManageEntity;
import com.sdzk.buss.web.system.service.TBShiftManageServiceI;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 班次管理
 * @author onlineGenerator
 * @date 2016-06-06 14:41:39
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBShiftManageController")
public class TBShiftManageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBShiftManageController.class);

	@Autowired
	private TBShiftManageServiceI tBShiftManageService;
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
	 * 班次管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/yk/buss/web/system/tBShiftManageList");
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
	public void datagrid(TBShiftManageEntity tBShiftManage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBShiftManageEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBShiftManage, request.getParameterMap());

        cq.addOrder("orderNo", SortDirection.asc);
		cq.add();
		this.tBShiftManageService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除班次管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBShiftManageEntity tBShiftManage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBShiftManage = systemService.getEntity(TBShiftManageEntity.class, tBShiftManage.getId());
		message = "班次管理删除成功";
		try{
			tBShiftManageService.delete(tBShiftManage);
			systemService.addLog("班次管理\""+tBShiftManage.getId()+"\"删除成功", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		}catch(Exception e){
			e.printStackTrace();
			message = "班次管理删除失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除班次管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "班次管理删除成功";
		try{
			for(String id:ids.split(",")){
				TBShiftManageEntity tBShiftManage = systemService.getEntity(TBShiftManageEntity.class, id);
				tBShiftManageService.delete(tBShiftManage);
				systemService.addLog("班次管理\""+tBShiftManage.getId()+"\"删除成功", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "班次管理删除失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加班次管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBShiftManageEntity tBShiftManage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "班次管理添加成功";
		try{
			tBShiftManageService.save(tBShiftManage);
			systemService.addLog("班次管理\""+tBShiftManage.getId()+"\"添加成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}catch(Exception e){
			e.printStackTrace();
			message = "班次管理添加失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_INSERT);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

    /**
     * 设置班次启用状态
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "setShiftStaus")
    @ResponseBody
    public AjaxJson setShiftStaus(HttpServletRequest request) throws Exception{
        String id = request.getParameter("id");
        TBShiftManageEntity shiftManageEntity = this.systemService.getEntity(TBShiftManageEntity.class,id);
        String status = shiftManageEntity.getIsUsed();
        if("1".equals(status)){
            shiftManageEntity.setIsUsed("0");
        }else{
            shiftManageEntity.setIsUsed("1");
        }
        this.systemService.saveOrUpdate(shiftManageEntity);
		systemService.addLog("班次管理\""+shiftManageEntity.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		AjaxJson j = new AjaxJson();
        j.setMsg("操作成功");
        return j;
    }
	
	/**
	 * 更新班次管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBShiftManageEntity tBShiftManage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "班次管理更新成功";
		TBShiftManageEntity t = tBShiftManageService.get(TBShiftManageEntity.class, tBShiftManage.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBShiftManage, t);
			tBShiftManageService.saveOrUpdate(t);
			systemService.addLog("班次管理\""+t.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "班次管理更新失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPDATE);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 班次管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBShiftManageEntity tBShiftManage, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBShiftManage.getId())) {
			tBShiftManage = tBShiftManageService.getEntity(TBShiftManageEntity.class, tBShiftManage.getId());
			req.setAttribute("tBShiftManagePage", tBShiftManage);
		}
		return new ModelAndView("com/yk/buss/web/system/tBShiftManage-add");
	}
	/**
	 * 班次管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBShiftManageEntity tBShiftManage, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBShiftManage.getId())) {
			tBShiftManage = tBShiftManageService.getEntity(TBShiftManageEntity.class, tBShiftManage.getId());
			req.setAttribute("tBShiftManagePage", tBShiftManage);
		}
		return new ModelAndView("com/yk/buss/web/system/tBShiftManage-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBShiftManageController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBShiftManageEntity tBShiftManage,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBShiftManageEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBShiftManage, request.getParameterMap());
		List<TBShiftManageEntity> tBShiftManages = this.tBShiftManageService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"班次管理");
		modelMap.put(NormalExcelConstants.CLASS,TBShiftManageEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("班次管理列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBShiftManages);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBShiftManageEntity tBShiftManage,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"班次管理");
    	modelMap.put(NormalExcelConstants.CLASS,TBShiftManageEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("班次管理列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
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
				List<TBShiftManageEntity> listTBShiftManageEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TBShiftManageEntity.class, params);
				for (TBShiftManageEntity tBShiftManage : listTBShiftManageEntitys) {
					tBShiftManageService.save(tBShiftManage);
					systemService.addLog("班次管理\""+tBShiftManage.getId()+"\"导入成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
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
}
