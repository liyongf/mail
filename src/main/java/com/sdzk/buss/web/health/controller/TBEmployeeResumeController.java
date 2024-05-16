package com.sdzk.buss.web.health.controller;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.excelverify.EmployeeResumeExcelVerifyHandler;
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
import org.jeecgframework.poi.excel.export.styler.ExcelExportStylerBorderImpl;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 职工职业履历档案
 * @author hansf
 * @date 2016-03-01 10:30:28
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBEmployeeResumeController")
public class TBEmployeeResumeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBEmployeeInfoController.class);

	@Autowired
	private TBEmployeeInfoServiceI tBEmployeeInfoService;
	@Autowired
	private TBLaborProtectServiceI tBLaborProtectService;
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
	 * 职工履历档案列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/health/tBEmployeeResumeList");
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
	 * 更新履历档案
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBEmployeeInfoEntity tBEmployeeInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "履历档案信息更新成功";
		TBEmployeeInfoEntity t = tBEmployeeInfoService.get(TBEmployeeInfoEntity.class, tBEmployeeInfo.getId());
		try {
//			MyBeanUtils.copyBeanNotNull2Bean(tBEmployeeInfo, t);
			t.setPostChangeResume(tBEmployeeInfo.getPostChangeResume());
			tBEmployeeInfoService.saveOrUpdate(t);
			systemService.addLog("履历档案信息\""+t.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
		} catch (Exception e) {
			e.printStackTrace();
			message = "履历档案信息更新失败";
			systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPDATE);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 职工履历档案编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBEmployeeInfoEntity tBEmployeeInfo, HttpServletRequest req) {
        String load = req.getParameter("load");
        req.setAttribute("load",load);
		if (StringUtil.isNotEmpty(tBEmployeeInfo.getId())) {
			tBEmployeeInfo = tBEmployeeInfoService.getEntity(TBEmployeeInfoEntity.class, tBEmployeeInfo.getId());
			req.setAttribute("tBEmployeeResumePage", tBEmployeeInfo);
		}
        if(StringUtils.isNotBlank(load)){
            return new ModelAndView("com/sdzk/buss/web/health/tBEmployeeResume-detail");
        }else{
            return new ModelAndView("com/sdzk/buss/web/health/tBEmployeeResume-update");
        }
		/*return new ModelAndView("com/sdzk/buss/web/health/tBEmployeeResume-update");*/
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBEmployeeResumeController");
		req.setAttribute("importfun_name","importExcelEmployeeResume");
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
		modelMap.put(NormalExcelConstants.FILE_NAME,"职工职业履历");
		modelMap.put(NormalExcelConstants.CLASS,TBEmployeeInfoEntity.class);
		ExportParams exportParams = new ExportParams("职工职业履历列表","导出信息");
		exportParams.setStyle(ExcelExportStylerBorderImpl.class);
        /* ExportParams("职工个人信息列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息")*/
		modelMap.put(NormalExcelConstants.PARAMS,exportParams);
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
    	modelMap.put(TemplateExcelConstants.FILE_NAME,"职工职业履历及劳动防护专项档案");
    	TemplateExportParams templateExportParams = new TemplateExportParams();
    	templateExportParams.setSheetNum(0);
    	templateExportParams.setTemplateUrl("export/template/exportTemp_EmployeeResume.xls");
    	modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
    	if(tBEmployeeInfos!=null&&tBEmployeeInfos.size()>0){
    		for(TBEmployeeInfoEntity t:tBEmployeeInfos){
    			t.setPostStatusTemp(DicUtil.getTypeNameByCode("postStatus", t.getPostStatus()));
    			//劳动防护用品
    			CriteriaQuery cqLabor = new CriteriaQuery(TBLaborProtectEntity.class);
    			cqLabor.createAlias("tBEmployeeInfoEntity", "employee");

    			cqLabor.eq("employee.id", t.getId());
    			cqLabor.addOrder("receiveDate", SortDirection.asc);
    			cqLabor.add();
    			List<TBLaborProtectEntity> laborList= tBLaborProtectService.getListByCriteriaQuery(cqLabor, false);
    			
    			if(laborList!=null&&laborList.size()>0){
    				String laborStr="";
    				for(TBLaborProtectEntity tBLaborProtectEntity:laborList){
    					laborStr += DateUtils.date2Str(tBLaborProtectEntity.getReceiveDate(), DateUtils.date_sdf_wz)+"  "+tBLaborProtectEntity.getProtectEquipment()+"  "+tBLaborProtectEntity.getAmount()+"\n";
    				}
    				t.setLaborProtectTemp(laborStr);
    			}
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
		modelMap.put(TemplateExcelConstants.FILE_NAME,"职工职业履历导入模板");
		TemplateExportParams templateExportParams = new TemplateExportParams();
    	templateExportParams.setSheetNum(0);
    	templateExportParams.setTemplateUrl("export/template/importTemp_EmployeeResume.xls");
    	modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
    	 modelMap.put(TemplateExcelConstants.MAP_DATA,new HashMap<String, Object>());
    	return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	@RequestMapping(params = "importExcelEmployeeResume", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcelEmployeeResume(HttpServletRequest request, HttpServletResponse response) {
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
			params.setVerifyHanlder(new EmployeeResumeExcelVerifyHandler());
			try {
				ExcelImportResult<TBEmployeeInfoEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(), TBEmployeeInfoEntity.class, params);

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
						TBEmployeeInfoEntity employee = (TBEmployeeInfoEntity)result.getList().get(i);
						//根据身份证号查询员工信息
						CriteriaQuery cq = new CriteriaQuery(TBEmployeeInfoEntity.class);
						if(StringUtil.isEmpty(employee.getCardNumber())){
							continue;
						}
						cq.eq("cardNumber", employee.getCardNumber());

						cq.eq("isDelete", Constants.IS_DELETE_N);
						cq.add();
						List<TBEmployeeInfoEntity>  oldemployeeList = tBEmployeeInfoService.getListByCriteriaQuery(cq, false);
						if(oldemployeeList!=null&&oldemployeeList.size()>0){
							TBEmployeeInfoEntity oldemployee = oldemployeeList.get(0);
							oldemployee.setPostChangeResume(employee.getPostChangeResume());
							tBEmployeeInfoService.saveOrUpdate(oldemployee);
							systemService.addLog("职工职业履历档案\""+oldemployee.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
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
