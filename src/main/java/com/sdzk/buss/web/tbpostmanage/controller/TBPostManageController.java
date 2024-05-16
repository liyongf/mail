package com.sdzk.buss.web.tbpostmanage.controller;

import com.hp.hpl.sparta.ParseException;
import com.sdzk.buss.web.common.excelverify.PostManageExcelVerifyHandler;
import com.sdzk.buss.web.tbdangersourceapost.entity.TBDangerPostRelEntity;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import com.sdzk.buss.web.tbpostmanage.service.TBPostManageServiceI;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.beanvalidator.BeanValidators;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.*;

/**   
 * @Title: Controller  
 * @Description: 工种管理
 * @author onlineGenerator
 * @date 2017-07-21 14:42:43
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBPostManageController")
public class TBPostManageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBPostManageController.class);

	@Autowired
	private TBPostManageServiceI tBPostManageService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
	


	/**
	 * 工种管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/tbpostmanage/tBPostManageList");
	}

	/**
	 * 查看作业过程关联的风险
	 *
	 * @return
	 */
	@RequestMapping(params = "seeRiskList")
	public ModelAndView seeRiskList(HttpServletRequest request) {
		String postid=request.getParameter("id");
		request.setAttribute("postid",postid);
		request.setAttribute("year", DateUtils.getYear());
		return new ModelAndView("com/sdzk/buss/web/tbpostmanage/tbPostRelRiskList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TBPostManageEntity tBPostManage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String postName = tBPostManage.getPostName();
		tBPostManage.setPostName(null);
		CriteriaQuery cq = new CriteriaQuery(TBPostManageEntity.class, dataGrid);
        cq.eq("isDelete","0");
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPostManage, request.getParameterMap());
		try{
		//自定义追加查询条件
			if (StringUtil.isNotEmpty(postName)) {
				cq.like("postName", "%"+postName+"%");
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
        cq.addOrder("createDate", SortDirection.desc);
		cq.add();
        this.tBPostManageService.getDataGridReturn(cq, true);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0) {
            List<TBPostManageEntity> tempList = dataGrid.getResults();
            for(TBPostManageEntity bean : tempList) {
                //字典
                String ptStr = bean.getProfessionType();
                String ptName = DicUtil.getTypeNameByCode("proCate_gradeControl", ptStr);
                bean.setProfessionType(ptName);
            }
        }
		TagUtil.datagrid(response, dataGrid);
	}
    /**
     * 删除岗位管理  逻辑删除
     *
     * @return
     */
    @RequestMapping(params = "logicdel")
    @ResponseBody
    public AjaxJson logicdel(TBPostManageEntity tBPostManage, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        tBPostManage = systemService.getEntity(TBPostManageEntity.class, tBPostManage.getId());
        message = "岗位删除成功";
        tBPostManage.setIsDelete("1");
        tBPostManageService.updateEntitie(tBPostManage);
        systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        j.setMsg(message);
        return j;
    }

    /**
	 * 删除工种管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TBPostManageEntity tBPostManage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tBPostManage = systemService.getEntity(TBPostManageEntity.class, tBPostManage.getId());
		//判断工种是否已关联风险
		List<TBDangerPostRelEntity> postRelEntities = systemService.findByProperty(TBDangerPostRelEntity.class, "postId", tBPostManage.getId());
		if (postRelEntities != null && postRelEntities.size() > 0) {
			j.setMsg("该工种已关联风险, 无法删除!");
			return j;
		}
		message = "工种管理删除成功";
		try{
			tBPostManageService.delete(tBPostManage);
			//删除用户关联
			systemService.executeSql("delete from t_b_post_user where post_id='"+tBPostManage.getId()+"'");
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "工种管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除工种管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "工种管理删除成功";
		StringBuffer postName = new StringBuffer();
		try{
			for(String id:ids.split(",")){
				TBPostManageEntity tBPostManage = systemService.getEntity(TBPostManageEntity.class, id);
				//判断工种是否已关联风险
				List<TBDangerPostRelEntity> postRelEntities = systemService.findByProperty(TBDangerPostRelEntity.class, "postId", tBPostManage.getId());
				if (postRelEntities != null && postRelEntities.size() > 0) {
					if (StringUtil.isNotEmpty(postName.toString())) {
						postName.append(",");
					}
					postName.append(tBPostManage.getPostName());
					continue;
				}
				tBPostManageService.delete(tBPostManage);
				//删除用户关联
				systemService.executeSql("delete from t_b_post_user where post_id='"+tBPostManage.getId()+"'");
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
			if (StringUtil.isNotEmpty(postName.toString())) {
				message = postName.toString()+"已关联风险无法删除, 其余工种删除成功";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "工种管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
    /**
     * 批量删除工种管理
     *
     * @return
     */
    @RequestMapping(params = "logicdoBatchDel")
    @ResponseBody
    public AjaxJson logicdoBatchDel(String ids,HttpServletRequest request){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "工种管理删除成功";
        StringBuffer postName = new StringBuffer();
        try{
            for(String id:ids.split(",")){
                TBPostManageEntity tBPostManage = systemService.getEntity(TBPostManageEntity.class,id);
                //判断工种是否已关联风险
                List<TBDangerPostRelEntity> postRelEntities = systemService.findByProperty(TBDangerPostRelEntity.class, "postId", tBPostManage.getId());
                if (postRelEntities != null && postRelEntities.size() > 0) {
                    if (StringUtil.isNotEmpty(postName.toString())) {
                        postName.append(",");
                    }
                    postName.append(tBPostManage.getPostName());
                    continue;
                }
                tBPostManage.setIsDelete("1");
                tBPostManageService.updateEntitie(tBPostManage);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
            if (StringUtil.isNotEmpty(postName.toString())) {
                message = postName.toString()+"已关联风险无法删除, 其余工种删除成功";
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "工种管理删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

	/**
	 * 根据工种名称验证此工种逻辑上是否存在
	 * @param postName
	 * @return
     */
    private boolean checkPostExists(String postName){
        List<TBPostManageEntity> entitys = systemService.findByProperty(TBPostManageEntity.class, "postName", postName);
        if (entitys != null && entitys.size() > 0) {
            for(TBPostManageEntity bean : entitys){
                if(!(bean.getIsDelete().isEmpty())) {
                    if (bean.getIsDelete().equals("0")) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }


	@RequestMapping(params = "postExists")
	@ResponseBody
	public Map<String, String> postExists(HttpServletRequest request){
		Map<String, String> result = new HashMap();
		String postName = ResourceUtil.getParameter("param");
		if (checkPostExists(postName)){
			result.put("status", "n");
			result.put("info","此岗位已存在！");
		} else {
			result.put("status", "y");
			result.put("info","通过信息验证！");
		}
		return result;
	}

    @RequestMapping(params = "postExistsUpdate")
    @ResponseBody
    public AjaxJson postExistsUpdate(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String postName = request.getParameter("postName");
        if(StringUtils.isNotBlank(postName)){
            if(checkPostExists(postName)){
                message = "该岗位名称已存在，请勿重复添加";
                j.setSuccess(false);
            }else{
                message = "校验通过";
                j.setSuccess(true);
            }
        }else{
            message = "岗位名称不能为空";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
	/**
	 * 添加岗位管理
	 *
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TBPostManageEntity tBPostManage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "岗位添加成功";
		//检查工种是否已存在
		if (checkPostExists(tBPostManage.getPostName())) {
			message = "此岗位已存在";
			j.setMsg(message);
			return j;
		}
		try{
            tBPostManage.setIsDelete("0");
			tBPostManageService.save(tBPostManage);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "岗位添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新工种管理
	 *
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TBPostManageEntity tBPostManage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		message = "岗位更新成功";
		TBPostManageEntity t = tBPostManageService.get(TBPostManageEntity.class, tBPostManage.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tBPostManage, t);
			tBPostManageService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "岗位更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 工种管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TBPostManageEntity tBPostManage, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBPostManage.getId())) {
			tBPostManage = tBPostManageService.getEntity(TBPostManageEntity.class, tBPostManage.getId());
			req.setAttribute("tBPostManagePage", tBPostManage);
		}
		return new ModelAndView("com/sdzk/buss/web/tbpostmanage/tBPostManage-add");
	}
	/**
	 * 工种管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TBPostManageEntity tBPostManage, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBPostManage.getId())) {
			tBPostManage = tBPostManageService.getEntity(TBPostManageEntity.class, tBPostManage.getId());
			req.setAttribute("tBPostManagePage", tBPostManage);

		}
		return new ModelAndView("com/sdzk/buss/web/tbpostmanage/tBPostManage-update");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TBPostManageEntity tBPostManage,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TBPostManageEntity.class, dataGrid);
        try{
            initQueryListCondition(request, cq);
        }catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        cq.addOrder("postName", SortDirection.desc);
        cq.add();
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPostManage, request.getParameterMap());
		List<TBPostManageEntity> retList = this.tBPostManageService.getListByCriteriaQuery(cq,false);

        List<Map<String,Object>> postmanagelistmap = initExportList(retList);
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_postManage.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", postmanagelistmap);
        modelMap.put(NormalExcelConstants.FILE_NAME,"岗位列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
        private List<Map<String, Object>> initExportList(List<TBPostManageEntity> postManageList) {
        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
        if(postManageList!=null&&postManageList.size()>0) {
            for (TBPostManageEntity postManage : postManageList) {
                String str = postManage.getProfessionType();
                String retName = DicUtil.getTypeNameByCode("proCate_gradeControl", str);
                postManage.setProfessionType(retName);
            }
            for (TBPostManageEntity postManage : postManageList) {
                Map<String, Object> postMap = new HashMap<>();
                postMap.put("postName", postManage.getPostName());
                postMap.put("professionType",postManage.getProfessionType());
                mapList.add(postMap);
            }
        }
        return mapList;
    }
    private void initQueryListCondition(HttpServletRequest request, CriteriaQuery cq) throws ParseException {
        //自定义追加查询条件
        String tempPostName = request.getParameter("postName");
        if(StringUtils.isNotBlank(tempPostName)){
            try {
                String postName = new String(tempPostName.getBytes("iso8859-1"),"UTF-8");
                cq.like("postName", "%" + postName + "%");
                cq.add();
            }catch(Exception es){};
        }
        String professionType = request.getParameter("professionType");
        if(StringUtils.isNotBlank(professionType)){
            cq.eq("professionType",professionType);
            cq.add();
        }
        cq.eq("isDelete","0");
        cq.add();
    }
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TBPostManageEntity tBPostManage,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME,"岗位管理导入模板");
		TemplateExportParams templateExportParams = new TemplateExportParams();
		templateExportParams.setSheetNum(0);
		templateExportParams.setTemplateUrl("export/template/importTemp_postManage.xls");
		modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
		Map<String, Object> param =new HashMap<String, Object>();
		modelMap.put(TemplateExcelConstants.MAP_DATA,param);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name","tBPostManageController");
        return new ModelAndView("common/upload/pub_excel_upload");
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
			params.setTitleRows(1);
			params.setHeadRows(1);
			params.setNeedSave(false);
            params.setNeedVerfiy(true);
            params.setVerifyHanlder(new PostManageExcelVerifyHandler());
			try {
                ExcelImportResult<TBPostManageEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(),TBPostManageEntity.class,params);
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
                    List<TBPostManageEntity> listNew = new ArrayList<>();
                    for (int i = 0; i < result.getList().size(); i++){
                        TBPostManageEntity tbPostManage = result.getList().get(i);
                        if(checkPostExists(tbPostManage.getPostName())){
                            this.importUpdate(tbPostManage);
                            systemService.addLog("岗位列表\"" + tbPostManage.getPostName() + "\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
                        }else {
                            tbPostManage.setIsDelete("0");
                            listNew.add(tbPostManage);
                            systemService.save(tbPostManage);
                            systemService.addLog("岗位列表\"" + tbPostManage.getPostName() + "\"导入成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
                        }
                    }
//                    systemService.batchSave(listNew);
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
    /**
     * 导入时名称相同更新操作
     *
     * @param ids
     * @return
     */

    private void importUpdate(TBPostManageEntity tbPostManage) throws IllegalAccessException {
        String importPostName = tbPostManage.getPostName();
        String sql = "select id from t_b_post_manage where post_name = '"+importPostName+"'and is_delete = '0'";
        List<Map<String, Object>> idTemp = systemService.findForJdbc(sql);
        Map<String,Object> temp = idTemp.get(0);
        String id = temp.get("id").toString();
        tbPostManage.setIsDelete("0");
        tbPostManage.setId(id);
        TBPostManageEntity t = tBPostManageService.get(TBPostManageEntity.class, tbPostManage.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tbPostManage, t);
            tBPostManageService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TBPostManageEntity> list() {
		List<TBPostManageEntity> listTBPostManages=tBPostManageService.getList(TBPostManageEntity.class);
		return listTBPostManages;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TBPostManageEntity task = tBPostManageService.get(TBPostManageEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TBPostManageEntity tBPostManage, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBPostManageEntity>> failures = validator.validate(tBPostManage);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBPostManageService.save(tBPostManage);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tBPostManage.getId();
		URI uri = uriBuilder.path("/rest/tBPostManageController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TBPostManageEntity tBPostManage) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TBPostManageEntity>> failures = validator.validate(tBPostManage);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		try{
			tBPostManageService.saveOrUpdate(tBPostManage);
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
		tBPostManageService.deleteEntityById(TBPostManageEntity.class, id);
	}
}
