package com.sdzk.buss.web.tbdangersourceapost.controller;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.excelverify.DangerSourcePostExcelVerifyHandler;
import com.sdzk.buss.web.tbdangersourceapost.entity.TBDangerPostRelEntity;
import com.sdzk.buss.web.tbdangersourceapost.entity.TBDangerSourceaPostEntity;
import com.sdzk.buss.web.tbdangersourceapost.service.TBDangerSourceaPostServiceI;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**   
 * @Title: Controller  
 * @Description: 岗位通用危险源
 * @author onlineGenerator
 * @date 2017-07-24 10:59:23
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBDangerSourceaPostController")
public class TBDangerSourceaPostController extends BaseController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBDangerSourceaPostController.class);

	@Autowired
	private TBDangerSourceaPostServiceI tBDangerSourceaPostService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 岗位通用危险源列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/tbdangersourceapost/tBDangerSourceaPostList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TBDangerSourceaPostEntity tBDangerSourceaPost,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String dangerSourceName = tBDangerSourceaPost.getDangerName();
		tBDangerSourceaPost.setDangerName(null);
		String postId = tBDangerSourceaPost.getPostId();
		CriteriaQuery cq = new CriteriaQuery(TBDangerSourceaPostEntity.class, dataGrid);
		tBDangerSourceaPost.setPostId(null);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSourceaPost, request.getParameterMap());
		try{
		//自定义追加查询条件
			if (StringUtil.isNotEmpty(dangerSourceName)) {
				cq.like("dangerName", "%"+dangerSourceName+"%");
			}
			if (StringUtil.isNotEmpty(postId)) {
				cq.add(Restrictions.sqlRestriction(" this_.id in (select danger_id from t_b_danger_post_rel where post_id ='"+postId+"') "));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tBDangerSourceaPostService.getDataGridReturn(cq, true);
		List<TBDangerSourceaPostEntity> entities = dataGrid.getResults();
		if (entities != null && entities.size() > 0){
			for (TBDangerSourceaPostEntity entity : entities) {
				List<TBDangerPostRelEntity> postRelEntitys = systemService.findByProperty(TBDangerPostRelEntity.class, "dangerId", entity.getId());
				if (postRelEntitys != null && postRelEntitys.size() > 0){
					entity.setPostId(postRelEntitys.get(0).getPostId());
				}
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * 删除岗位通用危险源
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBDangerSourceaPostEntity tBDangerSourceaPost, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tBDangerSourceaPost = systemService.getEntity(TBDangerSourceaPostEntity.class, tBDangerSourceaPost.getId());
		message = "岗位通用危险源删除成功";
		try{
			tBDangerSourceaPostService.delete(tBDangerSourceaPost);
			systemService.executeSql("delete from t_b_danger_post_rel where danger_id='"+tBDangerSourceaPost.getId()+"'");
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "岗位通用危险源删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除岗位通用危险源
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "岗位通用危险源删除成功";
		try{
			for(String id:ids.split(",")){
				TBDangerSourceaPostEntity tBDangerSourceaPost = systemService.getEntity(TBDangerSourceaPostEntity.class, 
				id
				);
				tBDangerSourceaPostService.delete(tBDangerSourceaPost);
				systemService.executeSql("delete from t_b_danger_post_rel where danger_id='"+tBDangerSourceaPost.getId()+"'");
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "岗位通用危险源删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加岗位通用危险源列表
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBDangerSourceaPostEntity tBDangerSourceaPost, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "岗位通用危险源添加成功";
		try{
			tBDangerSourceaPostService.save(tBDangerSourceaPost);
			String postId = ResourceUtil.getParameter("post");
			if (StringUtil.isNotEmpty(postId)){
				TBDangerPostRelEntity entity = new TBDangerPostRelEntity();
				entity.setDangerId(tBDangerSourceaPost.getId());
				entity.setPostId(postId);
				systemService.save(entity);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "岗位通用危险源添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新岗位通用危险源
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBDangerSourceaPostEntity tBDangerSourceaPost, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "岗位通用危险源更新成功";
		TBDangerSourceaPostEntity t = tBDangerSourceaPostService.get(TBDangerSourceaPostEntity.class, tBDangerSourceaPost.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBDangerSourceaPost, t);
			tBDangerSourceaPostService.saveOrUpdate(t);
			String postId = ResourceUtil.getParameter("post");
			if (StringUtil.isNotEmpty(postId)){
				systemService.executeSql("delete from t_b_danger_post_rel where danger_id='"+tBDangerSourceaPost.getId()+"'");
				TBDangerPostRelEntity entity = new TBDangerPostRelEntity();
				entity.setDangerId(tBDangerSourceaPost.getId());
				entity.setPostId(postId);
				systemService.save(entity);
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "岗位通用危险源更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 岗位通用危险源新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBDangerSourceaPostEntity tBDangerSourceaPost, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBDangerSourceaPost.getId())) {
			tBDangerSourceaPost = tBDangerSourceaPostService.getEntity(TBDangerSourceaPostEntity.class, tBDangerSourceaPost.getId());
			req.setAttribute("tBDangerSourceaPostPage", tBDangerSourceaPost);
		}
		TSTypegroup tsTypegroup=systemService.getTypeGroup("accidentCate","事故类型");
		List<TSType> tsTypeList = tsTypegroup.getTSTypes();
		req.setAttribute("tsTypeList",tsTypeList);
		String threshold_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "重大风险阀值");
		String threshold_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "较大风险阀值");
		String threshold_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "一般风险阀值");

		String threshold_lec_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "重大风险阀值");
		String threshold_lec_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "较大风险阀值");
		String threshold_lec_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "一般风险阀值");

		req.setAttribute("threshold_major", StringUtils.isBlank(threshold_major)?"25":threshold_major);
		req.setAttribute("threshold_superior", StringUtils.isBlank(threshold_superior)?"16":threshold_superior);
		req.setAttribute("threshold_commonly", StringUtils.isBlank(threshold_commonly)?"8":threshold_commonly);
		req.setAttribute("threshold_lec_major", StringUtils.isBlank(threshold_lec_major)?"270":threshold_lec_major);
		req.setAttribute("threshold_lec_superior", StringUtils.isBlank(threshold_lec_superior)?"140":threshold_lec_superior);
		req.setAttribute("threshold_lec_commonly", StringUtils.isBlank(threshold_lec_commonly)?"70":threshold_lec_commonly);

		return new ModelAndView("com/sdzk/buss/web/tbdangersourceapost/tBDangerSourceaPost-add");
	}
	/**
	 * 岗位通用危险源编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBDangerSourceaPostEntity tBDangerSourceaPost, HttpServletRequest req) {
		String load = ResourceUtil.getParameter("load");
		String post = null;
		TSTypegroup tsTypegroup=systemService.getTypeGroup("accidentCate","事故类型");
		List<TSType> tsTypeList = tsTypegroup.getTSTypes();
		req.setAttribute("tsTypeList",tsTypeList);
		if (StringUtil.isNotEmpty(tBDangerSourceaPost.getId())) {
			tBDangerSourceaPost = tBDangerSourceaPostService.getEntity(TBDangerSourceaPostEntity.class, tBDangerSourceaPost.getId());

            //LEC风险可能性
            String lecRiskPossibility = "";
            if(tBDangerSourceaPost.getLecRiskPossibility() != null){
                lecRiskPossibility = tBDangerSourceaPost.getLecRiskPossibility().toString();
            }
            if(StringUtils.isNotBlank(lecRiskPossibility)){
                String lecRiskPossibilityTemp = DicUtil.getTypeNameByCode("lec_risk_probability",lecRiskPossibility);
                tBDangerSourceaPost.setLecRiskPossibilityTemp(lecRiskPossibilityTemp);
            }
            //LEC风险损失
            String lecRiskLoss = "";
            if(tBDangerSourceaPost.getLecRiskLoss() != null){
                lecRiskLoss = tBDangerSourceaPost.getLecRiskLoss().toString();
            }
            if(StringUtils.isNotBlank(lecRiskLoss)){
                String lecRiskLossTemp = DicUtil.getTypeNameByCode("lec_risk_loss",lecRiskLoss);
                tBDangerSourceaPost.setLecRiskLossTemp(lecRiskLossTemp);
            }
            //LEC人员暴露于危险环境中的频繁程度
            String lecExposure = "";
            if(tBDangerSourceaPost.getLecExposure() != null){
                lecExposure = tBDangerSourceaPost.getLecExposure().toString();
            }
            if(StringUtils.isNotBlank(lecExposure)){
                String lecExposureTemp = DicUtil.getTypeNameByCode("lec_exposure",lecExposure);
                tBDangerSourceaPost.setLecExposureTemp(lecExposureTemp);
            }

			req.setAttribute("tBDangerSourceaPostPage", tBDangerSourceaPost);
			List<TBDangerPostRelEntity> postRelEntitys = systemService.findByProperty(TBDangerPostRelEntity.class, "dangerId", tBDangerSourceaPost.getId());
			if (postRelEntitys != null && postRelEntitys.size() > 0){
				post = postRelEntitys.get(0).getPostId();
			}
		}
		req.setAttribute("post", post);
		if ("detail".equals(load)) {
			if (StringUtil.isNotEmpty(post)) {
				TBPostManageEntity postManageEntity = systemService.get(TBPostManageEntity.class, post);
				if (postManageEntity != null) {
					tBDangerSourceaPost.setPostName(postManageEntity.getPostName());
				}
			}
			return new ModelAndView("com/sdzk/buss/web/tbdangersourceapost/tBDangerSourcePostDetail");
		}

		String threshold_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "重大风险阀值");
		String threshold_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "较大风险阀值");
		String threshold_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "一般风险阀值");

		String threshold_lec_major = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "重大风险阀值");
		String threshold_lec_superior = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "较大风险阀值");
		String threshold_lec_commonly = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "一般风险阀值");

		req.setAttribute("threshold_major", StringUtils.isBlank(threshold_major)?"25":threshold_major);
		req.setAttribute("threshold_superior", StringUtils.isBlank(threshold_superior)?"16":threshold_superior);
		req.setAttribute("threshold_commonly", StringUtils.isBlank(threshold_commonly)?"8":threshold_commonly);
		req.setAttribute("threshold_lec_major", StringUtils.isBlank(threshold_lec_major)?"270":threshold_lec_major);
		req.setAttribute("threshold_lec_superior", StringUtils.isBlank(threshold_lec_superior)?"140":threshold_lec_superior);
		req.setAttribute("threshold_lec_commonly", StringUtils.isBlank(threshold_lec_commonly)?"70":threshold_lec_commonly);
		return new ModelAndView("com/sdzk/buss/web/tbdangersourceapost/tBDangerSourceaPost-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tBDangerSourceaPostController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBDangerSourceaPostEntity tBDangerSourceaPost,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		String dangerSourceName = tBDangerSourceaPost.getDangerName();
		tBDangerSourceaPost.setDangerName(null);
		String postId = tBDangerSourceaPost.getPostId();
		CriteriaQuery cq = new CriteriaQuery(TBDangerSourceaPostEntity.class, dataGrid);
		tBDangerSourceaPost.setPostId(null);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSourceaPost, request.getParameterMap());
		try{
			//自定义追加查询条件
			if (StringUtil.isNotEmpty(dangerSourceName)) {
				cq.like("dangerName", "%"+dangerSourceName+"%");
			}
			if (StringUtil.isNotEmpty(postId)) {
				cq.add(Restrictions.sqlRestriction(" this_.id in (select danger_id from t_b_danger_post_rel where post_id ='"+postId+"') "));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		List<TBDangerSourceaPostEntity> tBDangerSourceaPosts = this.tBDangerSourceaPostService.getListByCriteriaQuery(cq,false);
		List<Map<String, Object>> list = systemService.findForJdbc("SELECT rel.danger_id, post.post_name FROM t_b_danger_post_rel rel JOIN t_b_post_manage post ON rel.post_id = post.id");
		if (tBDangerSourceaPosts != null && tBDangerSourceaPosts.size()>0) {
			Map<String, String> dangerPostMap = new HashMap<>();
			if ( list != null && list .size() > 0) {
				for (Map<String, Object> map : list) {
					dangerPostMap.put((String) map.get("danger_id"), (String) map.get("post_name"));
				}
			}
			for (TBDangerSourceaPostEntity bean : tBDangerSourceaPosts) {
                //LEC风险可能性
                String lecRiskPossibility = String.valueOf(bean.getLecRiskPossibility());
                if(org.apache.commons.lang3.StringUtils.isNotBlank(lecRiskPossibility)){
                    String lecRiskPossibilityTemp = DicUtil.getTypeNameByCode("lec_risk_probability",lecRiskPossibility);
                    bean.setLecRiskPossibilityTemp(lecRiskPossibilityTemp);
                }

                //LEC风险损失
                String lecRiskLoss = String.valueOf(bean.getLecRiskLoss());
                if(org.apache.commons.lang3.StringUtils.isNotBlank(lecRiskLoss)){
                    String lecRiskLossTemp = DicUtil.getTypeNameByCode("lec_risk_loss",lecRiskLoss);
                    bean.setLecRiskLossTemp(lecRiskLossTemp);
                }
                //LEC人员暴露于危险环境中的频繁程度
                String lecExposure = String.valueOf(bean.getLecExposure());
                if(org.apache.commons.lang3.StringUtils.isNotBlank(lecExposure)){
                    String lecExposureTemp = DicUtil.getTypeNameByCode("lec_exposure",lecExposure);
                    bean.setLecExposureTemp(lecExposureTemp);
                }
				bean.setPostName(dangerPostMap.get(bean.getId()));
				bean.setIsmajorTemp("1".equals(bean.getIsmajor())?"是":"否");
			}
		}

		modelMap.put(NormalExcelConstants.FILE_NAME,"岗位通用危险源列表");
		TemplateExportParams templateExportParams = new TemplateExportParams();
		templateExportParams.setSheetNum(0);

        String lec = request.getParameter("lec");
        if(lec.equals("no")){
            templateExportParams.setTemplateUrl("export/template/exportTemp_dangerSourcePost.xls");
        }else{
            templateExportParams.setTemplateUrl("export/template/exportTemp_dangerSourcePostLec.xls");
        }

		modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
		Map<String,Object> map = new HashMap<>();
		map.put("list", tBDangerSourceaPosts);
		modelMap.put(TemplateExcelConstants.MAP_DATA,map);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBDangerSourceaPostEntity tBDangerSourceaPost,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {

		modelMap.put(TemplateExcelConstants.FILE_NAME,"岗位通用危险源导入模板");
		TemplateExportParams templateExportParams = new TemplateExportParams();
		templateExportParams.setSheetNum(0);
        String lec = request.getParameter("lec");
        if(lec.equals("no")){
		    templateExportParams.setTemplateUrl("export/template/importTemp_dangerSourcePost.xls");
        }else{
            templateExportParams.setTemplateUrl("export/template/importTemp_dangerSourcePostLec.xls");
        }
		modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
		Map<String, Object> param =new HashMap<>();
		modelMap.put(TemplateExcelConstants.MAP_DATA,param);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}

    /**
     * 导入数据
     * */
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
//			params.setTitleRows(1);
			params.setHeadRows(1);
			params.setNeedSave(false);
			params.setNeedVerfiy(true);
			params.setVerifyHanlder(new DangerSourcePostExcelVerifyHandler());
			try {
				ExcelImportResult<TBDangerSourceaPostEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(),TBDangerSourceaPostEntity.class,params);
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
						TBDangerSourceaPostEntity tBDangerSource = result.getList().get(i);
						tBDangerSource.setIsmajor("0");
						/**
						 * 计算风险值
						 */
                        if(tBDangerSource.getRiskPossibility() != null && tBDangerSource.getRiskPossibility() != "" && tBDangerSource.getRiskLoss() != null && tBDangerSource.getRiskLoss() != ""){
                            int riskValue = Integer.parseInt(tBDangerSource.getRiskPossibility()) * Integer.parseInt(tBDangerSource.getRiskLoss());
                            tBDangerSource.setRiskValue(String.valueOf(riskValue));
                        }
                        /**
                         * 计算LEC风险值
                         * */
                        if(tBDangerSource.getLecRiskPossibility() != null && tBDangerSource.getLecRiskLoss() != null && tBDangerSource.getLecExposure() != null){
                            double lecRiskValue = tBDangerSource.getLecRiskPossibility() * tBDangerSource.getLecRiskLoss() * tBDangerSource.getLecExposure();
                            tBDangerSource.setLecRiskValue(lecRiskValue);
                        }
 						systemService.save(tBDangerSource);
						//保存工种与风险关系表
						List<String> postIds = systemService.findListbySql("select id from t_b_post_manage where post_name = '"+tBDangerSource.getPostName()+"'");
						if (postIds != null && postIds.size() > 0) {
							TBDangerPostRelEntity relEntity =  new TBDangerPostRelEntity();
							relEntity.setDangerId(tBDangerSource.getId());
							relEntity.setPostId(postIds.get(0));
							systemService.save(relEntity);
						}
						systemService.addLog("岗位通用危险源\""+tBDangerSource.getId()+"\"导入成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);

					}
					j.setMsg("文件导入成功！");
					systemService.addLog(j.getMsg(),Globals.Log_Leavel_INFO,Globals.Log_Type_UPLOAD);
				}

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
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TBDangerSourceaPostEntity> list() {
		List<TBDangerSourceaPostEntity> listTBDangerSourceaPosts=tBDangerSourceaPostService.getList(TBDangerSourceaPostEntity.class);
		return listTBDangerSourceaPosts;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TBDangerSourceaPostEntity task = tBDangerSourceaPostService.get(TBDangerSourceaPostEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TBDangerSourceaPostEntity tBDangerSourceaPost, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBDangerSourceaPostEntity>> failures = validator.validate(tBDangerSourceaPost);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBDangerSourceaPostService.save(tBDangerSourceaPost);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tBDangerSourceaPost.getId();
		URI uri = uriBuilder.path("/rest/tBDangerSourceaPostController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TBDangerSourceaPostEntity tBDangerSourceaPost) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBDangerSourceaPostEntity>> failures = validator.validate(tBDangerSourceaPost);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBDangerSourceaPostService.saveOrUpdate(tBDangerSourceaPost);
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
		tBDangerSourceaPostService.deleteEntityById(TBDangerSourceaPostEntity.class, id);
	}
}
