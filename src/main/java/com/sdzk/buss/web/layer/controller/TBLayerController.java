package com.sdzk.buss.web.layer.controller;
import com.sddb.common.Constants;
import com.sdzk.buss.web.common.taskProvince.TBReportDeleteIdEntity;
import com.sdzk.buss.web.layer.entity.TBLayerEntity;
import com.sdzk.buss.web.layer.service.TBLayerServiceI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.pojo.base.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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

import javax.validation.Validator;

/**
 * @Title: Controller  
 * @Description: 超图图层
 * @author onlineGenerator
 * @date 2018-06-09 10:42:08
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBLayerController")
public class TBLayerController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBLayerController.class);

	@Autowired
	private TBLayerServiceI tBLayerService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 超图图层列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/layer/tBLayerList");
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
	public void datagrid(TBLayerEntity tBLayer,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBLayerEntity.class, dataGrid);
		//查询条件组装器
		//org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBLayer, request.getParameterMap());
		if(StringUtil.isNotEmpty(tBLayer.getLayerCode())){
			cq.eq("layerCode",tBLayer.getLayerCode());
		}
		if(StringUtil.isNotEmpty(tBLayer.getLayerDetailName())){
			cq.like("layerDetailName", "%"+tBLayer.getLayerDetailName()+"%");
		}
		if(StringUtil.isNotEmpty(tBLayer.getUrl())){
			cq.like("url", "%" + tBLayer.getUrl() + "%");
		}
		if(StringUtil.isNotEmpty(tBLayer.getIsShow())){
			cq.eq("isShow",tBLayer.getIsShow());
		}
		if(StringUtil.isNotEmpty(tBLayer.getRemark())){
			cq.like("remark","%"+tBLayer.getRemark()+"%");
		}
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBLayerService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除超图图层
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBLayerEntity tBLayer, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tBLayer = systemService.getEntity(TBLayerEntity.class, tBLayer.getId());
		message = "煤层删除成功";
		try{
			tBLayerService.delete(tBLayer);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "煤层删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除超图图层
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "矿图删除成功";
		try{
			for(String id:ids.split(",")){
				TBLayerEntity tBLayer = systemService.getEntity(TBLayerEntity.class, 
				id
				);
				TBReportDeleteIdEntity reportDeleteIdEntity = new TBReportDeleteIdEntity();
				reportDeleteIdEntity.setDeleteId(id);
				reportDeleteIdEntity.setType("layer");
				systemService.save(reportDeleteIdEntity);
				tBLayerService.delete(tBLayer);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "矿图删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加超图图层
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBLayerEntity tBLayer, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "煤层添加成功";
		try{
			tBLayerService.save(tBLayer);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "煤层添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新超图图层
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBLayerEntity tBLayer, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "煤层更新成功";
		TBLayerEntity t = tBLayerService.get(TBLayerEntity.class, tBLayer.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBLayer, t);
			tBLayerService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "煤层更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 超图图层新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBLayerEntity tBLayer, HttpServletRequest req) {
		tBLayer.setId(UUIDGenerator.generate());
		req.setAttribute("tBLayerPage", tBLayer);
		return new ModelAndView("com/sdzk/buss/web/layer/tBLayer-add");
	}
	/**
	 * 超图图层编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBLayerEntity tBLayer, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBLayer.getId())) {
			tBLayer = tBLayerService.getEntity(TBLayerEntity.class, tBLayer.getId());
			req.setAttribute("tBLayerPage", tBLayer);
		}
		return new ModelAndView("com/sdzk/buss/web/layer/tBLayer-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBLayerController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBLayerEntity tBLayer,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBLayerEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBLayer, request.getParameterMap());
		List<TBLayerEntity> tBLayers = this.tBLayerService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"煤层");
		modelMap.put(NormalExcelConstants.CLASS,TBLayerEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("煤层列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBLayers);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBLayerEntity tBLayer,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"煤层");
    	modelMap.put(NormalExcelConstants.CLASS,TBLayerEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("煤层列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TBLayerEntity> listTBLayerEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TBLayerEntity.class,params);
				for (TBLayerEntity tBLayer : listTBLayerEntitys) {
					tBLayerService.save(tBLayer);
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


	/**
	 * 超图煤层列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "publishList")
	public ModelAndView publishList(HttpServletRequest request) {
		String smHandleServer = ResourceUtil.getConfigByName("smHandleServer");
		String smHandlePort = ResourceUtil.getConfigByName("smHandlePort");
		String smToken = ResourceUtil.getConfigByName("smToken");
		request.setAttribute("smHandleServer",smHandleServer);
		request.setAttribute("smHandlePort",smHandlePort);
//		req.setAttribute("smHandlePort","8888");
		request.setAttribute("smToken",smToken);
		request.setAttribute("serverCert", ResourceUtil.getServerCert());
		return new ModelAndView("com/sdzk/buss/web/layer/tBLayerPublishList");
	}

	/**
	 * 添加超图图层
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAddPublish")
	@ResponseBody
	public AjaxJson doAddPublish(TBLayerEntity tBLayer, HttpServletRequest request, TSDocument document) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "矿图添加成功";
		try{
			if(StringUtil.isNotEmpty(tBLayer.getUrl())){
				String smHandleServer = ResourceUtil.getConfigByName("smHandleServer");
				tBLayer.setUrl(tBLayer.getUrl().replace("localhost",smHandleServer));
			}
			tBLayerService.save(tBLayer);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

			if(ServletFileUpload.isMultipartContent(request)) {
				document.setId(null);
				TSTypegroup tsTypegroup = systemService.getTypeGroup("filetype", "文档分类");
				TSType tsType = systemService.getType("dwg", "矿图", tsTypegroup);

				if (document != null) {
					document.setStatus(1);
					document.setSubclassname(MyClassLoader.getPackPath(document));
					document.setCreatedate(DateUtils.gettimestamp());
					document.setTSType(tsType);
					document.setBusinessKey(tBLayer.getId());
					UploadFile uploadFile = new UploadFile(request, document);
					uploadFile.setCusPath("dwg");
					uploadFile.setSwfpath("swfpath");
					document = systemService.uploadFile(uploadFile);
					systemService.addLog("矿图上传成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPLOAD);

					tBLayer.setDwgName(document.getAttachmenttitle());
					tBLayer.setDwgPath(document.getRealpath());
					this.systemService.saveOrUpdate(tBLayer);
				}
			} else {
				message = "矿图添加成功，但未更新发布矿图";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "矿图添加失败";
//			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新超图图层
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdatePublish")
	@ResponseBody
	public AjaxJson doUpdatePublish(TBLayerEntity tBLayer, HttpServletRequest request, TSDocument document) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "矿图更新成功";
		TBLayerEntity t = tBLayerService.get(TBLayerEntity.class, tBLayer.getId());
		try {
			if(StringUtil.isNotEmpty(tBLayer.getUrl())){
				String smHandleServer = ResourceUtil.getConfigByName("smHandleServer");
				tBLayer.setUrl(tBLayer.getUrl().replace("localhost",smHandleServer));
			}
			MyBeanUtils.copyBeanNotNull2Bean(tBLayer, t);
			tBLayerService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);

			if(ServletFileUpload.isMultipartContent(request))  {
				//先删除老的dwg文件
				if(StringUtil.isNotEmpty(t.getDwgPath())) {
					File oldDwgFile = new File(t.getDwgPath());
					if (oldDwgFile.exists()) {
						oldDwgFile.delete();
					}
				}

				document.setId(null);
				TSTypegroup tsTypegroup = systemService.getTypeGroup("filetype", "文档分类");
				TSType tsType = systemService.getType("dwg", "矿图", tsTypegroup);
				if (document != null) {
					document.setStatus(1);
					document.setSubclassname(MyClassLoader.getPackPath(document));
					document.setCreatedate(DateUtils.gettimestamp());
					document.setTSType(tsType);
					document.setBusinessKey(t.getId());
					UploadFile uploadFile = new UploadFile(request, document);
					uploadFile.setCusPath("dwg");
					uploadFile.setSwfpath("swfpath");
					document = systemService.uploadFile(uploadFile);
					systemService.addLog("矿图上传成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPLOAD);

					t.setDwgName(document.getAttachmenttitle());
					t.setDwgPath(document.getRealpath());
					this.systemService.saveOrUpdate(t);
				}
			} else {
				message = "矿图信息更新成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "矿图更新失败";
//			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 超图图层新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAddPublish")
	public ModelAndView goAddPublish(TBLayerEntity tBLayer, HttpServletRequest req) {
		tBLayer.setId(UUIDGenerator.generate());
		req.setAttribute("tBLayerPage", tBLayer);

		String topDepartName = "unknown";
		String sql = "select GROUP_CONCAT(departname) topDepartNames from t_s_depart where parentdepartid is null and delete_flag!='1'";
		List<String> topDepartNameList = systemService.findListbySql(sql);
		if(null!=topDepartNameList && topDepartNameList.size()>0){
			topDepartName = topDepartNameList.get(0);
		}
		req.setAttribute("mineName",topDepartName);

		String smHandleServer = ResourceUtil.getConfigByName("smHandleServer");
		String smHandlePort = ResourceUtil.getConfigByName("smHandlePort");
		String smToken = ResourceUtil.getConfigByName("smToken");
		req.setAttribute("smHandleServer",smHandleServer);
		req.setAttribute("smHandlePort",smHandlePort);
//		req.setAttribute("smHandlePort","8888");
		req.setAttribute("smToken",smToken);

		TSUser user = ResourceUtil.getSessionUserName();
		req.setAttribute("operatorUsername",user.getUserName());
		req.setAttribute("operatorRealname",user.getRealName());
		req.setAttribute("serverCert",ResourceUtil.getServerCert());
		return new ModelAndView("com/sdzk/buss/web/layer/tBLayer-addPublish");
	}

	/**
	 * 超图图层编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdatePublish")
	public ModelAndView goUpdatePublish(TBLayerEntity tBLayer, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBLayer.getId())) {
			tBLayer = tBLayerService.getEntity(TBLayerEntity.class, tBLayer.getId());
			req.setAttribute("tBLayerPage", tBLayer);
		}

		String topDepartName = "unknown";
		String sql = "select GROUP_CONCAT(departname) topDepartNames from t_s_depart where parentdepartid is null and delete_flag!='1'";
		List<String> topDepartNameList = systemService.findListbySql(sql);
		if(null!=topDepartNameList && topDepartNameList.size()>0){
			topDepartName = topDepartNameList.get(0);
		}
		req.setAttribute("mineName",topDepartName);

		String smHandleServer = ResourceUtil.getConfigByName("smHandleServer");
		String smHandlePort = ResourceUtil.getConfigByName("smHandlePort");
		String smToken = ResourceUtil.getConfigByName("smToken");
		req.setAttribute("smHandleServer",smHandleServer);
		req.setAttribute("smHandlePort",smHandlePort);
//		req.setAttribute("smHandlePort","8888");
		req.setAttribute("smToken",smToken);

		TSUser user = ResourceUtil.getSessionUserName();
		req.setAttribute("operatorUsername",user.getUserName());
		req.setAttribute("operatorRealname",user.getRealName());
		req.setAttribute("serverCert",ResourceUtil.getServerCert());

		if(Constants.SUPERMAP_PUBLISH_TYPE_FIRM.equals(tBLayer.getPublishType())){
			return new ModelAndView("com/sdzk/buss/web/layer/tBLayer-update");
		}
		if(Constants.SUPERMAP_PUBLISH_TYPE_SELF.equals(tBLayer.getPublishType())){
			return new ModelAndView("com/sdzk/buss/web/layer/tBLayer-updatePublish");
		}
		return new ModelAndView("com/sdzk/buss/web/layer/tBLayer-updatePublish");
	}


}
