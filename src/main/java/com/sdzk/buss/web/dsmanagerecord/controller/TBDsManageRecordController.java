package com.sdzk.buss.web.dsmanagerecord.controller;

import com.sdzk.buss.web.dsmanagerecord.entity.TBDsManageRecordEntity;
import com.sdzk.buss.web.dsmanagerecord.service.TBDsManageRecordServiceI;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.*;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**   
 * @Title: Controller  
 * @Description: 重大风险管控记录
 * @author onlineGenerator
 * @date 2017-06-29 10:24:12
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBDsManageRecordController")
public class TBDsManageRecordController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBDsManageRecordController.class);

	@Autowired
	private TBDsManageRecordServiceI tBDsManageRecordService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 重大风险管控记录列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		request.setAttribute("id", ResourceUtil.getParameter("id"));
		return new ModelAndView("com/sdzk/buss/web/dsmanagerecord/tBDsManageRecordList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TBDsManageRecordEntity tBDsManageRecord,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBDsManageRecordEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDsManageRecord, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_controlleDate_begin = request.getParameter("controlleDate_begin");
		String query_controlleDate_end = request.getParameter("controlleDate_end");
		if(StringUtil.isNotEmpty(query_controlleDate_begin)){
			cq.ge("controlleDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_controlleDate_begin));
		}
		if(StringUtil.isNotEmpty(query_controlleDate_end)){
			cq.le("controlleDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_controlleDate_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBDsManageRecordService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除重大风险管控记录
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBDsManageRecordEntity tBDsManageRecord, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tBDsManageRecord = systemService.getEntity(TBDsManageRecordEntity.class, tBDsManageRecord.getId());
		message = "重大风险管控记录删除成功";
		try{
			tBDsManageRecordService.delete(tBDsManageRecord);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "重大风险管控记录删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除重大风险管控记录
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "重大风险管控记录删除成功";
		try{
			for(String id:ids.split(",")){
				TBDsManageRecordEntity tBDsManageRecord = systemService.getEntity(TBDsManageRecordEntity.class, 
				id
				);
				tBDsManageRecordService.delete(tBDsManageRecord);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "重大风险管控记录删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加重大风险管控记录
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBDsManageRecordEntity tBDsManageRecord, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "重大风险管控记录添加成功";
		try{
			tBDsManageRecordService.save(tBDsManageRecord);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "重大风险管控记录添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新重大风险管控记录
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBDsManageRecordEntity tBDsManageRecord, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "重大风险管控记录更新成功";
		TBDsManageRecordEntity t = tBDsManageRecordService.get(TBDsManageRecordEntity.class, tBDsManageRecord.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBDsManageRecord, t);
			tBDsManageRecordService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "重大风险管控记录更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 重大风险管控记录新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBDsManageRecordEntity tBDsManageRecord, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBDsManageRecord.getId())) {
			tBDsManageRecord = tBDsManageRecordService.getEntity(TBDsManageRecordEntity.class, tBDsManageRecord.getId());
		}
		req.setAttribute("tBDsManageRecordPage", tBDsManageRecord);
		return new ModelAndView("com/sdzk/buss/web/dsmanagerecord/tBDsManageRecord-add");
	}
	/**
	 * 重大风险管控记录编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBDsManageRecordEntity tBDsManageRecord, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBDsManageRecord.getId())) {
			tBDsManageRecord = tBDsManageRecordService.getEntity(TBDsManageRecordEntity.class, tBDsManageRecord.getId());
			req.setAttribute("tBDsManageRecordPage", tBDsManageRecord);
		}
		if("detail".equals(ResourceUtil.getParameter("load"))){
			if (StringUtil.isNotEmpty(tBDsManageRecord.getController())) {
				TSBaseUser user = systemService.get(TSBaseUser.class, tBDsManageRecord.getController());
				req.setAttribute("controllerName", user!=null?user.getRealName():null);
			}
			return new ModelAndView("com/sdzk/buss/web/dsmanagerecord/tBDsManageRecord-detail");
		}
		return new ModelAndView("com/sdzk/buss/web/dsmanagerecord/tBDsManageRecord-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBDsManageRecordController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBDsManageRecordEntity tBDsManageRecord,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBDsManageRecordEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDsManageRecord, request.getParameterMap());
		List<TBDsManageRecordEntity> tBDsManageRecords = this.tBDsManageRecordService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"重大风险管控记录");
		modelMap.put(NormalExcelConstants.CLASS,TBDsManageRecordEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("重大风险管控记录列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBDsManageRecords);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBDsManageRecordEntity tBDsManageRecord,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
    	modelMap.put(NormalExcelConstants.FILE_NAME,"重大风险管控记录");
    	modelMap.put(NormalExcelConstants.CLASS,TBDsManageRecordEntity.class);
    	modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("重大风险管控记录列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
				List<TBDsManageRecordEntity> listTBDsManageRecordEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TBDsManageRecordEntity.class,params);
				for (TBDsManageRecordEntity tBDsManageRecord : listTBDsManageRecordEntitys) {
					tBDsManageRecordService.save(tBDsManageRecord);
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
	public List<TBDsManageRecordEntity> list() {
		List<TBDsManageRecordEntity> listTBDsManageRecords=tBDsManageRecordService.getList(TBDsManageRecordEntity.class);
		return listTBDsManageRecords;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TBDsManageRecordEntity task = tBDsManageRecordService.get(TBDsManageRecordEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TBDsManageRecordEntity tBDsManageRecord, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBDsManageRecordEntity>> failures = validator.validate(tBDsManageRecord);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBDsManageRecordService.save(tBDsManageRecord);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tBDsManageRecord.getId();
		URI uri = uriBuilder.path("/rest/tBDsManageRecordController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TBDsManageRecordEntity tBDsManageRecord) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBDsManageRecordEntity>> failures = validator.validate(tBDsManageRecord);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBDsManageRecordService.saveOrUpdate(tBDsManageRecord);
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
		tBDsManageRecordService.deleteEntityById(TBDsManageRecordEntity.class, id);
	}

	/**
	 * 重大风险管控报告列表
	 * @param id
	 * @param req
     * @return
     */
	@RequestMapping(params = "goAddFiels")
	public ModelAndView goAddFiels(String id, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(id)) {
			List<String> list = this.systemService.findListbySql("select tsa.id FROM t_s_document as document , t_s_attachment as tsa where document.id = tsa.id and document.status = '1' and businesskey = '"+id+"'");
			List<TSAttachment> tsaList = new ArrayList<TSAttachment>();
			if(list != null && list.size() >0){
				for(String idTemp : list){
					//判断文件是否可用
					TSAttachment tsAttachment = this.systemService.getEntity(TSAttachment.class,idTemp);
					tsaList.add(tsAttachment);
				}
			}
			req.setAttribute("list",tsaList);
			req.setAttribute("bussId",id);
		}
		return new ModelAndView("com/sdzk/buss/web/dsmanagerecord/uploadFile");
	}

	/**
	 * 删除报告
	 * @param id
	 * @return
     */
	@RequestMapping(params = "doDelFile")
	@ResponseBody()
	public AjaxJson doDelFile(String id){
		String message = "删除成功";
		AjaxJson j = new AjaxJson();
		try {
			this.systemService.updateBySqlString(" update t_s_document set  status = 0 where id = '"+id+"' ");
			systemService.addLog("文件\""+id+"\"状态更新成功",Globals.Log_Leavel_INFO,Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "删除失败";
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "doAddFiels")
	@ResponseBody
	public AjaxJson doAddFiels( HttpServletRequest request,TSDocument document) {
		String message = "添加成功";
		// 文件标题
		String documentTitle = oConvertUtils.getString(request.getParameter("documentTitle"));
		AjaxJson j = new AjaxJson();

		String bussId = request.getParameter("bussId");
		TSTypegroup tsTypegroup=systemService.getTypeGroup("fieltype","文档分类");
		TSType tsType = systemService.getType("emergencyFile","附件", tsTypegroup);
		// 文件ID
		String fileKey = oConvertUtils.getString(request.getParameter("fileKey"));
		if(document != null){
			if (StringUtil.isNotEmpty(fileKey)) {
				document.setId(fileKey);
				document = systemService.getEntity(TSDocument.class, fileKey);
				document.setDocumentTitle(documentTitle);
			}
			document.setStatus(1);
			document.setSubclassname(MyClassLoader.getPackPath(document));
			document.setCreatedate(DateUtils.gettimestamp());
			document.setTSType(tsType);
			document.setBusinessKey(bussId);
			UploadFile uploadFile = new UploadFile(request, document);
			uploadFile.setCusPath("files");
			uploadFile.setSwfpath("swfpath");
			Map<String, Object> attributes = new HashMap<String, Object>();
			document = systemService.uploadFile(uploadFile);
			systemService.addLog("文件上传成功",Globals.Log_Leavel_INFO,Globals.Log_Type_UPLOAD);
			attributes.put("url", document.getRealpath());
			attributes.put("fileKey", document.getId());
			attributes.put("name", document.getAttachmenttitle());
			attributes.put("viewhref", "commonController.do?objfileList&fileKey=" + document.getId());
			attributes.put("delurl", "commonController.do?delObjFile&fileKey=" + document.getId());
		}
		j.setMsg(message);
		return j;
	}
}
