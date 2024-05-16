package com.sdzk.buss.web.health.controller;


import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.excelverify.EmployeeExcelVerifyHandler;
import com.sdzk.buss.web.health.entity.TBEmployeeInfoEntity;
import com.sdzk.buss.web.health.service.TBEmployeeInfoServiceI;

import net.sf.json.JSONArray;
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
import org.jeecgframework.poi.excel.export.styler.ExcelExportStylerBorderImpl;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
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
 * @Description: 职工个人信息
 * @author hansf
 * @date 2016-02-29 15:39:23
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBEmployeeInfoController")
public class TBEmployeeInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBEmployeeInfoController.class);

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
	 * 职工个人信息列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/health/tBEmployeeInfoList");
	}
	
	/**
	 * 职工个人信息选择列表 
	 * 
	 * @return
	 */
	@RequestMapping(params = "employeeselectlist")
	public ModelAndView employeeselectlist(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/health/employeeSelectList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TBEmployeeInfoEntity tBEmployeeInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TBEmployeeInfoEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBEmployeeInfo, request.getParameterMap());
		try{
			cq.eq("isDelete", Constants.IS_DELETE_N);
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
        cq.addOrder("createDate", SortDirection.desc);
		cq.add();
		this.tBEmployeeInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params = "datagridMagic")
	@ResponseBody
	public JSONArray datagridMagic() {
		String name = ResourceUtil.getParameter("name");
		String sql = "select id, name,file_no fileNo,post_number postNumber, retire_number retireNumber,card_number cardNumber   from t_b_employee_info where is_delete=0 and name like '%"+name+"%'";
		List<TBEmployeeInfoEntity> list = tBEmployeeInfoService.findObjForJdbc(sql,1, 100, TBEmployeeInfoEntity.class);
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		for (TBEmployeeInfoEntity entity : list){
			Map<String, String> obj = new HashMap<String, String>();

			obj.put("id",entity.getId());
			obj.put("name",entity.getName());
			obj.put("fileNo",entity.getFileNo()==null?"":entity.getFileNo());
			obj.put("postNumber",entity.getPostNumber()==null?"":entity.getPostNumber());
			obj.put("retireNumber",entity.getRetireNumber()==null?"":entity.getRetireNumber());
			obj.put("cardNumber",entity.getCardNumber()==null?"":entity.getCardNumber());
			result.add(obj);
		}
		JSONArray jsonArray = JSONArray.fromObject(result);
		return jsonArray;
	}

	/**
	 * 删除职工个人信息
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBEmployeeInfoEntity tBEmployeeInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBEmployeeInfo = systemService.getEntity(TBEmployeeInfoEntity.class, tBEmployeeInfo.getId());
		message = "职工个人信息删除成功";
		try{
			tBEmployeeInfo.setIsDelete(Constants.IS_DELETE_Y);
			tBEmployeeInfoService.saveOrUpdate(tBEmployeeInfo);
			//tBEmployeeInfoService.delete(tBEmployeeInfo);
			systemService.addLog("职工个人信息\""+tBEmployeeInfo.getId()+"\"删除成功（isDelete更新）", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
		}catch(Exception e){
			e.printStackTrace();
			message = "职工个人信息删除失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除职工个人信息
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "职工个人信息删除成功";
		try{
			for(String id:ids.split(",")){
				TBEmployeeInfoEntity tBEmployeeInfo = systemService.getEntity(TBEmployeeInfoEntity.class, 
				id
				);
				tBEmployeeInfo.setIsDelete(Constants.IS_DELETE_Y);
				tBEmployeeInfoService.saveOrUpdate(tBEmployeeInfo);
				//tBEmployeeInfoService.delete(tBEmployeeInfo);
				systemService.addLog("职工个人信息\""+tBEmployeeInfo.getId()+"\"删除成功（isDelete更新）", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "职工个人信息删除失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加职工个人信息
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBEmployeeInfoEntity tBEmployeeInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "职工个人信息添加成功";
		try{
			tBEmployeeInfo.setIsDelete(Constants.IS_DELETE_N);
			tBEmployeeInfoService.save(tBEmployeeInfo);
			systemService.addLog("职工个人信息\""+tBEmployeeInfo.getId()+"\"添加成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
		}catch(Exception e){
			e.printStackTrace();
			message = "职工个人信息添加失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_INSERT);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新职工个人信息
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBEmployeeInfoEntity tBEmployeeInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "职工个人信息更新成功";
		TBEmployeeInfoEntity t = tBEmployeeInfoService.get(TBEmployeeInfoEntity.class, tBEmployeeInfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBEmployeeInfo, t);
			tBEmployeeInfoService.saveOrUpdate(t);
			systemService.addLog("职工个人信息\""+t.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "职工个人信息更新失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPDATE);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 职工个人信息新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBEmployeeInfoEntity tBEmployeeInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBEmployeeInfo.getId())) {
			tBEmployeeInfo = tBEmployeeInfoService.getEntity(TBEmployeeInfoEntity.class, tBEmployeeInfo.getId());
			req.setAttribute("tBEmployeeInfoPage", tBEmployeeInfo);
		}
		return new ModelAndView("com/sdzk/buss/web/health/tBEmployeeInfo-add");
	}
	/**
	 * 职工个人信息编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBEmployeeInfoEntity tBEmployeeInfo, HttpServletRequest req) {
        String load = req.getParameter("load");
        req.setAttribute("load",load);
		if (StringUtil.isNotEmpty(tBEmployeeInfo.getId())) {
			tBEmployeeInfo = tBEmployeeInfoService.getEntity(TBEmployeeInfoEntity.class, tBEmployeeInfo.getId());
			req.setAttribute("tBEmployeeInfoPage", tBEmployeeInfo);
		}
        if(StringUtils.isNotBlank(load)){
            String genderCode = tBEmployeeInfo.getGender();
            req.setAttribute("genderName", DicUtil.getTypeNameByCode("sex", genderCode));
            String postCategoryCode = tBEmployeeInfo.getPostCategory();
            req.setAttribute("postCategoryName", DicUtil.getTypeNameByCode("postCategory", postCategoryCode));
            String postStatusCode = tBEmployeeInfo.getPostStatus();
            req.setAttribute("postStatusName", DicUtil.getTypeNameByCode("poststatus", postStatusCode));
            return new ModelAndView("com/sdzk/buss/web/health/tBEmployeeInfo-detail");
        }else{
            return new ModelAndView("com/sdzk/buss/web/health/tBEmployeeInfo-update");
        }
		//return new ModelAndView("com/sdzk/buss/web/health/tBEmployeeInfo-update");

	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBEmployeeInfoController");
		req.setAttribute("importfun_name","importExcelEmployee");
		return new ModelAndView("common/upload/pub_excel_import");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBEmployeeInfoEntity tBEmployeeInfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBEmployeeInfoEntity.class, dataGrid);
		cq.eq("isDelete", Constants.IS_DELETE_N);
		cq.add();
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBEmployeeInfo, request.getParameterMap());
		List<TBEmployeeInfoEntity> tBEmployeeInfos = this.tBEmployeeInfoService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"职工个人信息");
		modelMap.put(NormalExcelConstants.CLASS,TBEmployeeInfoEntity.class);
		ExportParams exportParams = new ExportParams("职工个人信息列表","导出信息");
		exportParams.setStyle(ExcelExportStylerBorderImpl.class);
		modelMap.put(NormalExcelConstants.PARAMS,exportParams/* ExportParams("职工个人信息列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息")*/);
		modelMap.put(NormalExcelConstants.DATA_LIST,tBEmployeeInfos);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBEmployeeInfoEntity tBEmployeeInfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		Map<String,Object> map = new HashMap<String, Object>();
		CriteriaQuery cq = new CriteriaQuery(TBEmployeeInfoEntity.class, dataGrid);
		cq.eq("isDelete", Constants.IS_DELETE_N);
		try {
			if(StringUtil.isNotEmpty(request.getParameter("name"))){
			tBEmployeeInfo.setName((new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8")));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		cq.add();
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBEmployeeInfo, request.getParameterMap());
		List<TBEmployeeInfoEntity> tBEmployeeInfos = this.tBEmployeeInfoService.getListByCriteriaQuery(cq,false);
    	modelMap.put(TemplateExcelConstants.FILE_NAME,"职工个人基本信息");
    	TemplateExportParams templateExportParams = new TemplateExportParams();
    	templateExportParams.setSheetNum(0);
    	templateExportParams.setTemplateUrl("export/template/exportTemp_Employee.xls");
    	modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
    	if(tBEmployeeInfos!=null&&tBEmployeeInfos.size()>0){
    		for(TBEmployeeInfoEntity t:tBEmployeeInfos){
    			t.setPostStatusTemp(DicUtil.getTypeNameByCode("postStatus", t.getPostStatus()));
    		}
    	}
    	map.put("list", tBEmployeeInfos);
    	modelMap.put(TemplateExcelConstants.MAP_DATA,map);
    	return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
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
		modelMap.put(TemplateExcelConstants.FILE_NAME,"职工个人基本信息导入模板");
		TemplateExportParams templateExportParams = new TemplateExportParams();
    	templateExportParams.setSheetNum(0);
    	templateExportParams.setTemplateUrl("export/template/importTemp_Employee.xls");
    	modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
    	 modelMap.put(TemplateExcelConstants.MAP_DATA,new HashMap<String, Object>());
    	return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcelEmployee", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcelEmployee(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(1);
			params.setHeadRows(1);
			params.setNeedSave(false);
			params.setNeedVerfiy(true);
			params.setVerifyHanlder(new EmployeeExcelVerifyHandler());
			try {
				ExcelImportResult<TBEmployeeInfoEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(), TBEmployeeInfoEntity.class, params);
				if(result.isVerfiyFail()){
					String uploadpathtemp = ResourceUtil.getConfigByName("uploadpathtemp");
					String realPath = multipartRequest.getSession().getServletContext().getRealPath("/") + "/" + uploadpathtemp+"/";// 文件的硬盘真实路径
					File fileTemp = new File(realPath);
					if (!fileTemp.exists()) {
						fileTemp.mkdirs();// 创建根目录
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
						TBEmployeeInfoEntity employee = (TBEmployeeInfoEntity)result.getList().get(i);
						//根据身份证号查询员工信息
						CriteriaQuery cq = new CriteriaQuery(TBEmployeeInfoEntity.class);

						cq.eq("cardNumber", employee.getCardNumber());
						cq.eq("isDelete", Constants.IS_DELETE_N);
						cq.add();
						List<TBEmployeeInfoEntity>  oldemployeeList = tBEmployeeInfoService.getListByCriteriaQuery(cq, false);
						if(oldemployeeList!=null&&oldemployeeList.size()>0){
							TBEmployeeInfoEntity oldemployee = oldemployeeList.get(0);
							MyBeanUtils.copyBeanNotNull2Bean(employee, oldemployee);
							tBEmployeeInfoService.saveOrUpdate(oldemployee);
							systemService.addLog("职工个人信息\""+oldemployee.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
						}else{
							employee.setIsDelete(Constants.IS_DELETE_N);
							tBEmployeeInfoService.save(employee);
							systemService.addLog("职工个人信息\""+employee.getId()+"\"导入成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);

						}
//				        System.out.println(ReflectionToStringBuilder.toString(result.getList().get(i)));
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

	/**
	 * 职工个人信息查询_页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "employeeQuery")
	public ModelAndView employeeQuery(HttpServletRequest request) {
		String fileNoQuery = request.getParameter("fileNoQuery");//档案号
		String postNumberQuery = request.getParameter("postNumberQuery");//在岗编号
		String retireNumberQuery = request.getParameter("retireNumberQuery");//退休编号
		String cardNumberQuery = request.getParameter("cardNumberQuery");//身份证号
		String nameQuery = request.getParameter("nameQuery");//姓名
		String employeeidQuery = request.getParameter("employeeidQuery");//姓名ID
		if(StringUtil.isNotEmpty(fileNoQuery)|| StringUtil.isNotEmpty(postNumberQuery)||
				StringUtil.isNotEmpty(retireNumberQuery)||
				StringUtil.isNotEmpty(cardNumberQuery)|| StringUtil.isNotEmpty(nameQuery)){
		//查询基本信息
		CriteriaQuery cq = new CriteriaQuery(TBEmployeeInfoEntity.class);

		cq.eq("fileNo", fileNoQuery);
		cq.eq("postNumber", postNumberQuery);
		cq.eq("retireNumber", retireNumberQuery);
		cq.eq("cardNumber", cardNumberQuery);
		cq.eq("id", employeeidQuery);
		cq.eq("isDelete", Constants.IS_DELETE_N);
		cq.add();
		List<TBEmployeeInfoEntity> tBEmployeeInfoEntityList= this.tBEmployeeInfoService.getListByCriteriaQuery(cq, false);
		if(tBEmployeeInfoEntityList!=null&&tBEmployeeInfoEntityList.size()==1){
			request.setAttribute("tBEmployeeInfoPage", tBEmployeeInfoEntityList.get(0));
		}
		}
		request.setAttribute("fileNoQuery", fileNoQuery);
		request.setAttribute("postNumberQuery", postNumberQuery);
		request.setAttribute("retireNumberQuery", retireNumberQuery);
		request.setAttribute("cardNumberQuery", cardNumberQuery);
		request.setAttribute("employeeidQuery", employeeidQuery);
		if(StringUtil.isNotEmpty(nameQuery)){
			try {
				request.setAttribute("nameQuery", new String(nameQuery.getBytes("ISO-8859-1"), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("com/sdzk/buss/web/health/employeeQuery");
	}
	
	/**
	 * 导出excel 使模板（职工个人职业安全卫生档案导出）
	 * 
	 * @param request
	 * @param response
	 */

	@RequestMapping(params = "exportXlsEmployQueryByT")
	public String exportXlsEmployQueryByT(TBEmployeeInfoEntity tBEmployeeInfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		Map<String,Object> map = new HashMap<String, Object>();
		String fileNoQuery = request.getParameter("fileNoQuery");//档案号
		String postNumberQuery = request.getParameter("postNumberQuery");//在岗编号
		String retireNumberQuery = request.getParameter("retireNumberQuery");//退休编号
		String cardNumberQuery = request.getParameter("cardNumberQuery");//身份证号
		String nameQuery = request.getParameter("nameQuery");//姓名
		String employeeidQuery = request.getParameter("employeeidQuery");//姓名ID
		modelMap.put(TemplateExcelConstants.FILE_NAME,"职工个人基本信息");
    	TemplateExportParams templateExportParams = new TemplateExportParams();
    	templateExportParams.setSheetNum(0);
    	templateExportParams.setTemplateUrl("export/template/exportTemp_EmployeeInfo.xls");
    	modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
		if(StringUtil.isNotEmpty(fileNoQuery)|| StringUtil.isNotEmpty(postNumberQuery)||
				StringUtil.isNotEmpty(retireNumberQuery)||
				StringUtil.isNotEmpty(cardNumberQuery)|| StringUtil.isNotEmpty(nameQuery)){
		//查询基本信息
		CriteriaQuery cq = new CriteriaQuery(TBEmployeeInfoEntity.class);

		cq.eq("fileNo", fileNoQuery);
		cq.eq("postNumber", postNumberQuery);
		cq.eq("retireNumber", retireNumberQuery);
		cq.eq("cardNumber", cardNumberQuery);
		cq.eq("id", employeeidQuery);
		cq.eq("isDelete", Constants.IS_DELETE_N);
		cq.add();
		List<TBEmployeeInfoEntity> tBEmployeeInfoEntityList= this.tBEmployeeInfoService.getListByCriteriaQuery(cq, false);
		
		if(tBEmployeeInfoEntityList!=null&&tBEmployeeInfoEntityList.size()==1){
			TBEmployeeInfoEntity tBEmployeeInfoEntity = tBEmployeeInfoEntityList.get(0);
	    	tBEmployeeInfoEntity.setPostStatusTemp(DicUtil.getTypeNameByCode("postStatus", tBEmployeeInfoEntity.getPostStatus()));
	    	if(tBEmployeeInfoEntity.gettBHealthExamEntity()!=null&& StringUtil.isNotEmpty(tBEmployeeInfoEntity.gettBHealthExamEntity().getId())){
	    		tBEmployeeInfoEntity.gettBHealthExamEntity().setPrejobChkCategory(DicUtil.getTypeNameByCode("chk_category", tBEmployeeInfoEntity.gettBHealthExamEntity().getPrejobChkCategory()));
	    		tBEmployeeInfoEntity.gettBHealthExamEntity().setLeavedType(DicUtil.getTypeNameByCode("leaved_type", tBEmployeeInfoEntity.gettBHealthExamEntity().getLeavedType()));
	    	}
	    	if(tBEmployeeInfoEntity.gettBHealthTrainEntity()!=null&& StringUtil.isNotEmpty(tBEmployeeInfoEntity.gettBHealthTrainEntity().getId())){
	    		tBEmployeeInfoEntity.gettBHealthTrainEntity().setPrejobTrainType(DicUtil.getTypeNameByCode("chk_category", tBEmployeeInfoEntity.gettBHealthTrainEntity().getPrejobTrainType()));
	    	
	    	}
	    	MyBeanUtils.copyBean2Map(map, tBEmployeeInfoEntity);
		}
		}
		modelMap.put(TemplateExcelConstants.MAP_DATA,map);
    	return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}


}
