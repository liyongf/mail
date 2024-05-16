package com.sdzk.buss.web.health.controller;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.excelverify.HealthExamExcelVerifyHandler;
import com.sdzk.buss.web.health.entity.TBEmployeeInfoEntity;
import com.sdzk.buss.web.health.entity.TBHealthExamEntity;
import com.sdzk.buss.web.health.service.TBEmployeeInfoServiceI;
import com.sdzk.buss.web.health.service.TBHealthExamServiceI;
import com.sdzk.buss.web.health.vo.TBHealthExamVO;
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
 * @Description: 职业健康检查专项档案
 * @author hansf
 * @date 2016-03-03 10:28:51
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBHealthExamController")
public class TBHealthExamController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBHealthExamController.class);

	@Autowired
	private TBHealthExamServiceI tBHealthExamService;
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
	 * 职业健康检查专项档案列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/health/tBHealthExamList");
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


	/**
	 * 更新职业健康检查专项档案
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBHealthExamEntity tBHealthExam, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "职业健康检查专项档案更新成功";
		if(StringUtil.isNotEmpty(tBHealthExam.getId())){
			TBHealthExamEntity t = tBHealthExamService.get(TBHealthExamEntity.class, tBHealthExam.getId());
		
			try {
				MyBeanUtils.copyBeanNotNull2Bean(tBHealthExam, t);
				tBHealthExamService.saveOrUpdate(t);
				systemService.addLog("职业健康检查专项档案\""+t.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "职业健康检查专项档案更新失败";
				systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPDATE);
				throw new BusinessException(e.getMessage());
			}
		}else{

			tBHealthExamService.save(tBHealthExam);
			systemService.addLog("职业健康检查专项档案\""+tBHealthExam.getId()+"\"添加成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
			//更新职工个人信息表中的健康检查id
			if(StringUtil.isNotEmpty(tBHealthExam.getEmployeeId())){
				TBEmployeeInfoEntity tBEmployeeInfoEntity = tBEmployeeInfoService.get(TBEmployeeInfoEntity.class, tBHealthExam.getEmployeeId());
				tBEmployeeInfoEntity.settBHealthExamEntity(tBHealthExam);
				tBEmployeeInfoService.saveOrUpdate(tBEmployeeInfoEntity);
				systemService.addLog("职工个人信息\""+tBEmployeeInfoEntity.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			}

		}
		j.setMsg(message);
		return j;
	}
	


	/**
	 * 职业健康检查专项档案编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBEmployeeInfoEntity tBEmployeeInfo, HttpServletRequest req) {
        String load = req.getParameter("load");
        req.setAttribute("load",load);
		TBEmployeeInfoEntity tBEmployeeInfoEntity = tBEmployeeInfoService.get(TBEmployeeInfoEntity.class, tBEmployeeInfo.getId());
		req.setAttribute("tBEmployeeInfoEntityPage", tBEmployeeInfoEntity);
		if (tBEmployeeInfoEntity.gettBHealthExamEntity()!=null&& StringUtil.isNotEmpty(tBEmployeeInfoEntity.gettBHealthExamEntity().getId())) {
			TBHealthExamEntity tBHealthExam = tBHealthExamService.getEntity(TBHealthExamEntity.class, tBEmployeeInfoEntity.gettBHealthExamEntity().getId());
			req.setAttribute("tBHealthExamPage", tBHealthExam);

            String prejobChkCategoryCode = tBHealthExam.getPrejobChkCategory();
            String prejobChkCategoryName = DicUtil.getTypeNameByCode("chk_category",prejobChkCategoryCode);
            req.setAttribute("prejobChkCategoryName", prejobChkCategoryName);

            String leavedTypeCode = tBHealthExam.getLeavedType();
            String leavedTypeName = DicUtil.getTypeNameByCode("leaved_type", leavedTypeCode);
            req.setAttribute("leavedTypeName", leavedTypeName);
		}else{
			TBHealthExamEntity tBHealthExam = new TBHealthExamEntity();
			tBHealthExam.setEmployeeId(tBEmployeeInfo.getId());
			req.setAttribute("tBHealthExamPage", tBHealthExam);
		}
        if(StringUtils.isNotBlank(load)){
            return new ModelAndView("com/sdzk/buss/web/health/tBHealthExam-detail");
        }else{
            return new ModelAndView("com/sdzk/buss/web/health/tBHealthExam-update");
        }
		//return new ModelAndView("com/sdzk/buss/web/health/tBHealthExam-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBHealthExamController");
		req.setAttribute("importfun_name","importExcelHealthExam");
		return new ModelAndView("common/upload/pub_excel_import");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBHealthExamEntity tBHealthExam,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBHealthExamEntity.class, dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBHealthExam, request.getParameterMap());
		List<TBHealthExamEntity> tBHealthExams = this.tBHealthExamService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"职业健康检查专项档案");
		modelMap.put(NormalExcelConstants.CLASS,TBHealthExamEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("职业健康检查专项档案列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tBHealthExams);
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
			if(StringUtil.isNotEmpty(request.getParameter("currWorkUnits"))){
				tBEmployeeInfo.setCurrWorkUnits((new String(request.getParameter("currWorkUnits").getBytes("ISO-8859-1"),"UTF-8")));
				}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		cq.add();
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBEmployeeInfo, request.getParameterMap());
		List<TBEmployeeInfoEntity> tBEmployeeInfos = this.tBEmployeeInfoService.getListByCriteriaQuery(cq,false);
    	modelMap.put(TemplateExcelConstants.FILE_NAME,"职业健康检查专项档案");
    	TemplateExportParams templateExportParams = new TemplateExportParams();
    	templateExportParams.setSheetNum(0);
    	templateExportParams.setTemplateUrl("export/template/exportTemp_HealthExam.xls");
    	modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
    	List<TBHealthExamVO> tBHealthExamVOList = new ArrayList<TBHealthExamVO>();
    	try {
    		if(tBEmployeeInfos!=null&&tBEmployeeInfos.size()>0){
        		for(TBEmployeeInfoEntity t:tBEmployeeInfos){
        			TBHealthExamVO tBHealthExamVO = new TBHealthExamVO();
        			MyBeanUtils.copyBeanNotNull2Bean(t, tBHealthExamVO);
        			if(t.gettBHealthExamEntity()!=null){
        				MyBeanUtils.copyBeanNotNull2Bean(t.gettBHealthExamEntity(), tBHealthExamVO);
        			}
        			
        			tBHealthExamVOList.add(tBHealthExamVO);
//        			t.setPostStatusTemp(DicUtil.getTypeNameByCode("postStatus", t.getPostStatus()));
        		}
        	}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
    	for(TBHealthExamVO v:tBHealthExamVOList){
    		v.setPostStatus(DicUtil.getTypeNameByCode("postStatus", v.getPostStatus()));
    		v.setPrejobChkCategory(DicUtil.getTypeNameByCode("chk_category", v.getPrejobChkCategory()));
    		v.setLeavedType(DicUtil.getTypeNameByCode("leaved_type", v.getLeavedType()));
    	}
    	
    	map.put("list", tBHealthExamVOList);
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
		modelMap.put(TemplateExcelConstants.FILE_NAME,"职工健康检查专项档案导入模板");
		TemplateExportParams templateExportParams = new TemplateExportParams();
    	templateExportParams.setSheetNum(0);
    	templateExportParams.setTemplateUrl("export/template/importTemp_HealthExam.xls");
    	modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
    	 modelMap.put(TemplateExcelConstants.MAP_DATA,new HashMap<String, Object>());
    	return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	@RequestMapping(params = "importExcelHealthExam", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcelHealthExam(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
			MultipartFile file = entity.getValue();
			ImportParams params = new ImportParams();
			params.setTitleRows(5);
			params.setHeadRows(1);
			params.setNeedSave(false);
			params.setNeedVerfiy(true);
			params.setVerifyHanlder(new HealthExamExcelVerifyHandler());
			try {
				ExcelImportResult<TBHealthExamEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(), TBHealthExamEntity.class, params);
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
						TBHealthExamEntity healthExam = result.getList().get(i);
						if(StringUtil.isEmpty(healthExam.getCardNumberTemp())){
							continue;
						}
						//根据档案号查询员工信息
						CriteriaQuery cq = new CriteriaQuery(TBEmployeeInfoEntity.class);

						cq.eq("cardNumber", healthExam.getCardNumberTemp());

						cq.eq("isDelete", Constants.IS_DELETE_N);
						cq.add();
						List<TBEmployeeInfoEntity>  oldemployeeList = tBEmployeeInfoService.getListByCriteriaQuery(cq, false);
						if(oldemployeeList!=null&&oldemployeeList.size()>0){
							TBEmployeeInfoEntity employee = oldemployeeList.get(0);
							TBHealthExamEntity healthExamEntity = employee.gettBHealthExamEntity();
							if(healthExamEntity!=null&& StringUtil.isNotEmpty(healthExamEntity.getId())){
							    //更新职业健康检查
								MyBeanUtils.copyBeanNotNull2Bean(healthExam, healthExamEntity);
								tBHealthExamService.saveOrUpdate(healthExamEntity);
								systemService.addLog("职业健康检查信息\""+healthExamEntity.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
							}else{

								healthExam.setEmployeeId(employee.getId());
								tBHealthExamService.save(healthExam);
								systemService.addLog("职业健康检查信息\""+healthExam.getId()+"\"添加成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
								//更新职工个人信息表中的健康检查id
								if(StringUtil.isNotEmpty(healthExam.getEmployeeId())){
									TBEmployeeInfoEntity tBEmployeeInfoEntity = tBEmployeeInfoService.get(TBEmployeeInfoEntity.class, healthExam.getEmployeeId());
									tBEmployeeInfoEntity.settBHealthExamEntity(healthExam);
									tBEmployeeInfoService.saveOrUpdate(tBEmployeeInfoEntity);
									systemService.addLog("职工个人信息\""+tBEmployeeInfoEntity.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
								}
							}
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
