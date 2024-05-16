package com.sdzk.buss.web.dangersource.controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hp.hpl.sparta.ParseException;
import com.sdzk.buss.web.common.excelverify.HazardManageExcelVerifyHandler;
import com.sdzk.buss.web.dangersource.entity.TBHazardManageExportDicVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.service.SystemService;

import com.sdzk.buss.web.dangersource.entity.TbHazardManageEntity;
import com.sdzk.buss.web.dangersource.service.TbHazardManageServiceI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.jeecgframework.core.beanvalidator.BeanValidators;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.net.URI;
import org.springframework.http.MediaType;
import org.springframework.web.util.UriComponentsBuilder;

/**   
 * @Title: Controller
 * @Description: 第一类危险源名称管理
 * @author zhangdaihao
 * @date 2017-09-18 10:58:46
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tbHazardManageController")
public class TbHazardManageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TbHazardManageController.class);

	@Autowired
	private TbHazardManageServiceI tbHazardManageService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 第一类危险源名称管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/sdzk/buss/web/hazardmanage/tbHazardManageList");
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
	public void datagrid(TbHazardManageEntity tbHazardManage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TbHazardManageEntity.class, dataGrid);
        String hazardName=tbHazardManage.getHazardName();
        tbHazardManage.setHazardName(null);
        String accidentType=tbHazardManage.getAccidentType();
        tbHazardManage.setAccidentType(null);
        String damageType = tbHazardManage.getDamageType();
        tbHazardManage.setDamageType(null);
        cq.eq("isDelete","0");
        cq.add();
		//查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tbHazardManage, request.getParameterMap());
        try{
            //自定义追加查询条件
            if (StringUtil.isNotEmpty(hazardName)) {
                cq.like("hazardName", "%"+hazardName+"%");
            }
            cq.add();
            if(StringUtil.isNotEmpty(accidentType))
            {
                cq.like("accidentType","%"+accidentType+"%");
            }
            cq.add();
            if(StringUtil.isNotEmpty(damageType))
            {
                cq.like("damageType","%"+damageType+"%");
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tbHazardManageService.getDataGridReturn(cq, true);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TbHazardManageEntity> tempList = dataGrid.getResults();
            for(TbHazardManageEntity bean : tempList){
                //字典
                String dtStr = bean.getDamageType();
                if(StringUtils.isNotBlank(dtStr)){
                    String [] dtArray = dtStr.split(",");
                    StringBuffer dt = new StringBuffer();
                    for(String str : dtArray){
                        String dtName = DicUtil.getTypeNameByCode("danger_Category", str);
                        if(StringUtils.isNotBlank(dt.toString())){
                            dt.append(",");
                        }
                        dt.append(dtName);
                    }
                    bean.setTempDamageType(dt.toString());
                }
                //字典
                String atStr = bean.getAccidentType();
                if(StringUtils.isNotBlank(atStr)){
                    String [] atArray = atStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : atArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setTempAccidentType(sb.toString());
                }
                //字典
                String htStr = bean.getHazardType();
                String htName = DicUtil.getTypeNameByCode("dangerSource_type", htStr);
                bean.setHazardType(htName);
            }
        }
        TagUtil.datagrid(response, dataGrid);
	}
    /**
     * 删除第一类危险源名称管理 逻辑删除
     *
     * @return
     */
    @RequestMapping(params = "logicdel")
    @ResponseBody
    public AjaxJson logicdel(TbHazardManageEntity tbHazardManage, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        tbHazardManage = systemService.getEntity(TbHazardManageEntity.class, tbHazardManage.getId());
        message = "危险源删除成功";
        tbHazardManage.setIsDelete("1");
        tbHazardManageService.updateEntitie(tbHazardManage);
        systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

        j.setMsg(message);
        return j;
    }
    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name","tbHazardManageController");
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
            params.setVerifyHanlder(new HazardManageExcelVerifyHandler());
            try {
                ExcelImportResult<TbHazardManageEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(), TbHazardManageEntity.class, params);
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
                    List<TbHazardManageEntity> listNew = new ArrayList<>();
                    for (int i = 0; i < result.getList().size(); i++){
                        TbHazardManageEntity tbHazardManage = result.getList().get(i);
                        if(checkPostExists(tbHazardManage.getHazardName())){
                            this.importUpdate(tbHazardManage);
                            systemService.addLog("危险源列表\"" + tbHazardManage.getHazardName() + "\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
                        }else {
                            tbHazardManage.setIsDelete("0");
                            listNew.add(tbHazardManage);
                            systemService.save(tbHazardManage);
                            systemService.addLog("危险源列表\"" + tbHazardManage.getHazardName() + "\"导入成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
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

    private void importUpdate(TbHazardManageEntity tbHazardManage) throws IllegalAccessException {
        String importHazardName = tbHazardManage.getHazardName();
        String sql = "select id from t_b_hazard_manage where hazard_name = '"+importHazardName+"'and is_delete = '0'";
        List<Map<String, Object>> idTemp = systemService.findForJdbc(sql);
        Map<String,Object> temp = idTemp.get(0);
        String id = temp.get("id").toString();
        tbHazardManage.setIsDelete("0");
        tbHazardManage.setId(id);
        TbHazardManageEntity t = tbHazardManageService.get(TbHazardManageEntity.class, tbHazardManage.getId());
            try {
                MyBeanUtils.copyBeanNotNull2Bean(tbHazardManage, t);
                tbHazardManageService.saveOrUpdate(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TbHazardManageEntity tBHazardManage,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TbHazardManageEntity.class, dataGrid);
        try{
            initQueryListCondition(request, cq);
        }catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        cq.addOrder("hazardName", SortDirection.desc);
        cq.add();
//		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBPostManage, request.getParameterMap());
        List<TbHazardManageEntity> retList = this.tbHazardManageService.getListByCriteriaQuery(cq,false);
        List<Map<String,Object>> hazardmanagelistmap = initExportList(retList);
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_hazardManage.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", hazardmanagelistmap);
        modelMap.put(NormalExcelConstants.FILE_NAME,"危险源列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }
    private List<Map<String, Object>> initExportList(List<TbHazardManageEntity> hazardManageList) {
        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
        if(hazardManageList!=null&&hazardManageList.size()>0) {
            for (TbHazardManageEntity hazardManage : hazardManageList) {
                //字典
                String dtStr = hazardManage.getDamageType();
                if(StringUtils.isNotBlank(dtStr)){
                    String [] dtArray = dtStr.split("\\W+");
                    StringBuffer dt = new StringBuffer();
                    for(String str : dtArray){
                        String dtName = DicUtil.getTypeNameByCode("danger_Category", str);
                        if(StringUtils.isNotBlank(dt.toString())){
                            dt.append(",");
                        }
                        dt.append(dtName);
                    }
                    hazardManage.setDamageType(dt.toString());
                }
                //字典
                String atStr = hazardManage.getAccidentType();
                if(StringUtils.isNotBlank(atStr)){
                    String [] atArray = atStr.split("\\W+");
                    StringBuffer atsb = new StringBuffer();
                    for(String atstr : atArray){
                        String atretName = DicUtil.getTypeNameByCode("accidentCate", atstr);
                        if(StringUtils.isNotBlank(atsb.toString())){
                            atsb.append(",");
                        }
                        atsb.append(atretName);
                    }
                    hazardManage.setAccidentType(atsb.toString());
                }
                //字典
                String dststr = hazardManage.getHazardType();
                String retName = DicUtil.getTypeNameByCode("dangerSource_type", dststr);
                hazardManage.setHazardType(retName);
            }
            for (TbHazardManageEntity hazardManage : hazardManageList) {
                Map<String, Object> hazardMap = new HashMap<>();
                hazardMap.put("hazardName", hazardManage.getHazardName());
                hazardMap.put("hazardType",hazardManage.getHazardType());
                hazardMap.put("damageType",hazardManage.getDamageType());
                hazardMap.put("accidentType",hazardManage.getAccidentType());
                mapList.add(hazardMap);
            }
        }
        return mapList;
    }
    private void initQueryListCondition(HttpServletRequest request, CriteriaQuery cq) throws ParseException {
        //自定义追加查询条件
        String tempHazardName = request.getParameter("hazardName");
        if(StringUtils.isNotBlank(tempHazardName)){
            try {
               String hazardName =  new String(tempHazardName.getBytes("iso8859-1"), "UTF-8");
                cq.like("hazardName","%"+hazardName+"%");
                cq.add();
            }catch(Exception es){};
        }
        String hazardType = request.getParameter("hazardType");
        if(StringUtils.isNotBlank(hazardType)){
            cq.eq("hazardType",hazardType);
        }
        String damageType = request.getParameter("damageType");
        if(StringUtils.isNotBlank(damageType)){
            cq.like("damageType","%"+damageType+"%");
        }
        String accidentType = request.getParameter("accidentType");
        if(StringUtils.isNotBlank(accidentType)){
            cq.like("accidentType","%"+accidentType+"%");
        }
        cq.eq("isDelete","0");
    }

    private boolean checkPostExists(String hazardName){
        List<TbHazardManageEntity> entitys = systemService.findByProperty(TbHazardManageEntity.class, "hazardName",hazardName);
        if (entitys != null && entitys.size() > 0) {
            for(TbHazardManageEntity bean : entitys){
                if(!(bean.getIsDelete().isEmpty())) {
                    if ((bean.getIsDelete()).equals("0")) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }
    /**查重**/
    @RequestMapping(params = "postExists")
    @ResponseBody
    public AjaxJson postExists(HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        String hazardName = request.getParameter("hazardName");
        if(StringUtils.isNotBlank(hazardName)){
            if(checkPostExists(hazardName)){
                message = "该危险源名称已存在，请勿重复添加";
                j.setSuccess(false);
            }else{
                message = "校验通过";
                j.setSuccess(true);
            }
        }else{
            message = "危险源名称不能为空";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TbHazardManageEntity tbHazardManage,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME,"危险源管理导入模板");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(1);
        templateExportParams.setScanAllsheet(true);
        templateExportParams.setTemplateUrl("export/template/importTemp_hazardManage.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String, Object> param =new HashMap<String, Object>();

        //查询    危险源种类
        String sql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='dangerSource_type')";
        List<String> typeList = systemService.findListbySql(sql);
        List<TBHazardManageExportDicVO> list = new ArrayList<TBHazardManageExportDicVO>();
        for (int i=0; i<typeList.size(); i++){
            TBHazardManageExportDicVO vo = new TBHazardManageExportDicVO();
            vo.setType(typeList.get(i));

            list.add(vo);
        }
        param.put("typeList", list);

        modelMap.put(TemplateExcelConstants.MAP_DATA,param);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

	/**
	 * 删除第一类危险源名称管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TbHazardManageEntity tbHazardManage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tbHazardManage = systemService.getEntity(TbHazardManageEntity.class, tbHazardManage.getId());
		message = "危险源删除成功";
		tbHazardManageService.delete(tbHazardManage);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加第一类危险源名称管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TbHazardManageEntity tbHazardManage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(tbHazardManage.getId())) {
			message = "危险源更新成功";
			TbHazardManageEntity t = tbHazardManageService.get(TbHazardManageEntity.class, tbHazardManage.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(tbHazardManage, t);
				tbHazardManageService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "危险源更新失败";
			}
		} else {
			message = "危险源添加成功";
            tbHazardManage.setIsDelete("0");
			tbHazardManageService.save(tbHazardManage);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}
    /**
     * 更新第一类危险源  --编辑
     * @param tbHazardManage
     * @param req
     * @return
     */
    @RequestMapping(params = "goUpdateHazard")
    public ModelAndView goUpdateDepartDangerSource(TbHazardManageEntity tbHazardManage, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tbHazardManage.getId())) {
            tbHazardManage = tbHazardManageService.getEntity(TbHazardManageEntity.class, tbHazardManage.getId());
            req.setAttribute("tbHazardManagePage", tbHazardManage);
        }

        TSTypegroup tsTypegroup=systemService.getTypeGroup("accidentCate","事故类型");
        List<TSType> tsTypeList = tsTypegroup.getTSTypes();
        req.setAttribute("tsTypeList",tsTypeList);

        TSTypegroup dTypegroup=systemService.getTypeGroup("danger_Category","危险类别");
        List<TSType> dTypeList = dTypegroup.getTSTypes();
        req.setAttribute("dangersTypeList",dTypeList);

        return new ModelAndView("com/sdzk/buss/web/hazardmanage/tbHazardManage_update");
    }
    /**
     * 添加第一类危险源 --新增
     * @param tBHazardManage
     * @param req
     * @return
     */
    @RequestMapping(params = "goAddHazard")
    public ModelAndView goAddDepartDangerSource(TbHazardManageEntity tBHazardManage, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBHazardManage.getId())) {
            tBHazardManage = tbHazardManageService.getEntity(TbHazardManageEntity.class, tBHazardManage.getId());
            req.setAttribute("tbHazardManagePage", tBHazardManage);
        }

        TSTypegroup tsTypegroup=systemService.getTypeGroup("accidentCate","事故类型");
        List<TSType> tsTypeList = tsTypegroup.getTSTypes();
        req.setAttribute("tsTypeList",tsTypeList);

        TSTypegroup dTypegroup=systemService.getTypeGroup("danger_Category","危险类别");
        List<TSType> dTypeList = dTypegroup.getTSTypes();
        req.setAttribute("dangersTypeList",dTypeList);

        return new ModelAndView("com/sdzk/buss/web/hazardmanage/tbHazardManage_add");
    }
	/**
	 * 第一类危险源名称管理列表 --查看
	 * 
	 * @return
	 */
	@RequestMapping(params = "godetail")
	public ModelAndView godetail(TbHazardManageEntity tbHazardManage, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tbHazardManage.getId())) {
			tbHazardManage = tbHazardManageService.getEntity(TbHazardManageEntity.class, tbHazardManage.getId());
			req.setAttribute("tbHazardManagePage", tbHazardManage);
		}
        TSTypegroup tsTypegroup=systemService.getTypeGroup("accidentCate","事故类型");
        List<TSType> tsTypeList = tsTypegroup.getTSTypes();
        req.setAttribute("tsTypeList",tsTypeList);

        TSTypegroup dTypegroup=systemService.getTypeGroup("danger_Category","危险类别");
        List<TSType> dTypeList = dTypegroup.getTSTypes();
        req.setAttribute("dangersTypeList",dTypeList);

		return new ModelAndView("com/sdzk/buss/web/hazardmanage/tbHazardManage_update");
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TbHazardManageEntity> list() {
		List<TbHazardManageEntity> listTbHazardManages=tbHazardManageService.getList(TbHazardManageEntity.class);
		return listTbHazardManages;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TbHazardManageEntity task = tbHazardManageService.get(TbHazardManageEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TbHazardManageEntity tbHazardManage, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TbHazardManageEntity>> failures = validator.validate(tbHazardManage);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		tbHazardManageService.save(tbHazardManage);

		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tbHazardManage.getId();
		URI uri = uriBuilder.path("/rest/tbHazardManageController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TbHazardManageEntity tbHazardManage) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TbHazardManageEntity>> failures = validator.validate(tbHazardManage);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		tbHazardManageService.saveOrUpdate(tbHazardManage);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		tbHazardManageService.deleteEntityById(TbHazardManageEntity.class, id);
	}
}
