package com.sdzk.buss.web.health.controller;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.excelverify.HealthRecuExcelVerifyHandler;
import com.sdzk.buss.web.health.entity.TBEmployeeInfoEntity;
import com.sdzk.buss.web.health.entity.TBHealthRecuperateEntity;
import com.sdzk.buss.web.health.service.TBEmployeeInfoServiceI;
import com.sdzk.buss.web.health.service.TBHealthRecuperateServiceI;
import com.sdzk.buss.web.health.vo.TBHealthRecuperateVO;
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
 * @Description: 职业健康疗养专项档案
 * @author onlineGenerator
 * @date 2016-03-03 16:44:40
 * @version V1.0
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBHealthRecuperateController")
public class TBHealthRecuperateController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(TBHealthRecuperateController.class);

	@Autowired
	private TBHealthRecuperateServiceI tBHealthRecuperateService;
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
	 * 职业健康疗养专项档案列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/health/tBHealthRecuperateList");
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
	public void datagrid(TBEmployeeInfoEntity tBEmployeeInfo,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid) {

		CriteriaQuery cq = new CriteriaQuery(TBEmployeeInfoEntity.class,
				dataGrid);
		// 查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				tBEmployeeInfo, request.getParameterMap());
		try {

			cq.eq("isDelete", Constants.IS_DELETE_N);
			// 自定义追加查询条件
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
        cq.addOrder("createDate", SortDirection.desc);
		cq.add();
		this.tBEmployeeInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 更新职业健康疗养专项档案
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBHealthRecuperateEntity tBHealthRecuperate,
			HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "职业健康疗养专项档案更新成功";

		if (StringUtil.isNotEmpty(tBHealthRecuperate.getId())) {
			TBHealthRecuperateEntity t = tBHealthRecuperateService.get(
					TBHealthRecuperateEntity.class, tBHealthRecuperate.getId());

			try {
				MyBeanUtils.copyBeanNotNull2Bean(tBHealthRecuperate, t);
				tBHealthRecuperateService.saveOrUpdate(t);
				systemService.addLog("职业健康疗养专项档案\""+t.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			} catch (Exception e) {
				e.printStackTrace();
				message = "职业健康疗养专项档案更新失败";
				systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPDATE);
				throw new BusinessException(e.getMessage());
			}
		} else {

			tBHealthRecuperateService.save(tBHealthRecuperate);
			systemService.addLog("职业健康疗养专项档案\""+tBHealthRecuperate.getId()+"\"添加成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
			// 更新职工个人信息表中的健康疗养id
			if (StringUtil.isNotEmpty(tBHealthRecuperate.getEmployeeId())) {
				TBEmployeeInfoEntity tBEmployeeInfoEntity = tBEmployeeInfoService.get(TBEmployeeInfoEntity.class, tBHealthRecuperate.getEmployeeId());
				tBEmployeeInfoEntity.settBHealthRecuperateEntity(tBHealthRecuperate);
				tBEmployeeInfoService.saveOrUpdate(tBEmployeeInfoEntity);
				systemService.addLog("职工个人信息\""+tBEmployeeInfoEntity.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 职业健康疗养专项档案编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBEmployeeInfoEntity tBEmployeeInfo,
			HttpServletRequest req) {
        String load = req.getParameter("load");
        req.setAttribute("load",load);
		TBEmployeeInfoEntity tBEmployeeInfoEntity = tBEmployeeInfoService.get(
				TBEmployeeInfoEntity.class, tBEmployeeInfo.getId());
		req.setAttribute("tBEmployeeInfoEntityPage", tBEmployeeInfoEntity);

		if (tBEmployeeInfoEntity.gettBHealthRecuperateEntity() != null
				&& StringUtil.isNotEmpty(tBEmployeeInfoEntity
                .gettBHealthRecuperateEntity().getId())) {
			TBHealthRecuperateEntity tBHealthRecuperate = tBHealthRecuperateService
					.getEntity(TBHealthRecuperateEntity.class,
							tBEmployeeInfoEntity.gettBHealthRecuperateEntity()
									.getId());
			req.setAttribute("tBHealthRecuperatePage", tBHealthRecuperate);
		} else {
			TBHealthRecuperateEntity tBHealthRecuperate = new TBHealthRecuperateEntity();
			tBHealthRecuperate.setEmployeeId(tBEmployeeInfo.getId());
			req.setAttribute("tBHealthRecuperatePage", tBHealthRecuperate);
		}
        if(StringUtils.isNotBlank(load)){
            return new ModelAndView("com/sdzk/buss/web/health/tBHealthRecuperate-detail");
        }else{
            return new ModelAndView("com/sdzk/buss/web/health/tBHealthRecuperate-update");
        }
		/*return new ModelAndView("com/sdzk/buss/web/health/tBHealthRecuperate-update");*/
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "tBHealthRecuperateController");
		req.setAttribute("importfun_name","importExcelHealthRecu");
		return new ModelAndView("common/upload/pub_excel_import");
	}

	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBHealthRecuperateEntity tBHealthRecuperate,
			HttpServletRequest request, HttpServletResponse response,
			DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBHealthRecuperateEntity.class,
				dataGrid);
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
				tBHealthRecuperate, request.getParameterMap());
		List<TBHealthRecuperateEntity> tBHealthRecuperates = this.tBHealthRecuperateService
				.getListByCriteriaQuery(cq, false);
		modelMap.put(NormalExcelConstants.FILE_NAME, "职业健康疗养专项档案");
		modelMap.put(NormalExcelConstants.CLASS, TBHealthRecuperateEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams(
				"职业健康疗养专项档案列表", "导出人:"
						+ ResourceUtil.getSessionUserName().getRealName(),
				"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tBHealthRecuperates);
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
    	modelMap.put(TemplateExcelConstants.FILE_NAME,"职业健康疗养专项档案");
    	TemplateExportParams templateExportParams = new TemplateExportParams();
    	templateExportParams.setSheetNum(0);
    	templateExportParams.setTemplateUrl("export/template/exportTemp_HealthRecuperate.xls");
    	modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
    	List<TBHealthRecuperateVO> tBHealthRecuperateVOList = new ArrayList<TBHealthRecuperateVO>();
    	try {
    		if(tBEmployeeInfos!=null&&tBEmployeeInfos.size()>0){
        		for(TBEmployeeInfoEntity t:tBEmployeeInfos){
        			TBHealthRecuperateVO tBHealthRecuperateVO = new TBHealthRecuperateVO();
        			MyBeanUtils.copyBeanNotNull2Bean(t, tBHealthRecuperateVO);
        			if(t.gettBHealthRecuperateEntity()!=null){
        				MyBeanUtils.copyBeanNotNull2Bean(t.gettBHealthRecuperateEntity(), tBHealthRecuperateVO);
        			}
        			
        			tBHealthRecuperateVOList.add(tBHealthRecuperateVO);
        		}
        	}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
    	for(TBHealthRecuperateVO v:tBHealthRecuperateVOList){
    		v.setPostStatus(DicUtil.getTypeNameByCode("postStatus", v.getPostStatus()));
    	}
    	
    	map.put("list", tBHealthRecuperateVOList);
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
		modelMap.put(TemplateExcelConstants.FILE_NAME,"职工健康疗养专项档案导入模板");
		TemplateExportParams templateExportParams = new TemplateExportParams();
    	templateExportParams.setSheetNum(0);
    	templateExportParams.setTemplateUrl("export/template/importTemp_HealthRecuperate.xls");
    	modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
    	 modelMap.put(TemplateExcelConstants.MAP_DATA,new HashMap<String, Object>());
    	return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	@RequestMapping(params = "importExcelHealthRecu", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcelHealthRecu(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
			MultipartFile file = entity.getValue();
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(false);
			params.setNeedVerfiy(true);
			params.setVerifyHanlder(new HealthRecuExcelVerifyHandler());
			try {
				ExcelImportResult<TBHealthRecuperateEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(), TBHealthRecuperateEntity.class, params);
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
						TBHealthRecuperateEntity healthRecu = result.getList().get(i);
						if(StringUtil.isEmpty(healthRecu.getCardNumberTemp())){
							continue;
						}
						//根据档案号查询员工信息
						CriteriaQuery cq = new CriteriaQuery(TBEmployeeInfoEntity.class);

						cq.eq("cardNumber", healthRecu.getCardNumberTemp());

						cq.eq("isDelete", Constants.IS_DELETE_N);
						cq.add();
						List<TBEmployeeInfoEntity>  oldemployeeList = tBEmployeeInfoService.getListByCriteriaQuery(cq, false);
						if(oldemployeeList!=null&&oldemployeeList.size()>0){
							TBEmployeeInfoEntity employee = oldemployeeList.get(0);
							TBHealthRecuperateEntity healthRecuEntity = employee.gettBHealthRecuperateEntity();
							if(healthRecuEntity!=null&& StringUtil.isNotEmpty(healthRecuEntity.getId())){
							    //更新职业健康检查
								MyBeanUtils.copyBeanNotNull2Bean(healthRecu, healthRecuEntity);
								tBHealthRecuperateService.saveOrUpdate(healthRecuEntity);
								systemService.addLog("职业健康疗养专项档案\""+healthRecuEntity.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
							}else{

								healthRecu.setEmployeeId(employee.getId());
								tBHealthRecuperateService.save(healthRecu);
								systemService.addLog("职业健康疗养专项档案\""+healthRecu.getId()+"\"导入成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
								//更新职工个人信息表中的健康检查id
								if(StringUtil.isNotEmpty(healthRecu.getEmployeeId())){
									TBEmployeeInfoEntity tBEmployeeInfoEntity = tBEmployeeInfoService.get(TBEmployeeInfoEntity.class, healthRecu.getEmployeeId());
									tBEmployeeInfoEntity.settBHealthRecuperateEntity(healthRecu);
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
