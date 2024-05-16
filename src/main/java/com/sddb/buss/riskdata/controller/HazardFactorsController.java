package com.sddb.buss.riskdata.controller;

import com.sddb.buss.identification.entity.RiskFactortsPostRel;
import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.identification.entity.RiskIdentificationPostEntity;
import com.sddb.buss.riskdata.entity.HazardFactorsPostEntity;
import com.sddb.buss.riskdata.entity.HazardModuleEntity;
import com.sddb.buss.riskdata.service.HazardFactorsServiceI;
import com.sddb.buss.riskmanage.entity.*;
import com.sddb.common.Constants;
import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import com.sddb.common.excelverify.HazardFactorsExcelVerifyHandler;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Scope("prototype")
@Controller
@RequestMapping("/hazardFactorsController")
public class HazardFactorsController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private HazardFactorsServiceI hazardFactorsService;
    private String message;

    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/sddb/buss/web/riskdata/hazardFactorsList");
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(HazardFactorsEntity hazardFactorsEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String queryHandleStatus = request.getParameter("queryHandleStatus");
        String riskType = request.getParameter("riskType");
        String major = request.getParameter("major");
        String riskLevel = request.getParameter("riskLevel");
        String from = request.getParameter("from");
        CriteriaQuery cq = new CriteriaQuery(HazardFactorsEntity.class, dataGrid);
        //cq.createAlias("hazardFactor","hazardFactor");
        try{
            cq.eq("isDel",Constants.HAZARDFACTORS_IS_DEL_FALSE);
            if(StringUtils.isBlank(queryHandleStatus) || Constants.HAZARDFACTORS_STATUS_DRAFT.equals(queryHandleStatus)){
                cq.eq("status",Constants.HAZARDFACTORS_STATUS_DRAFT);
            }else if(Constants.HAZARDFACTORS_STATUS_ROLLBACK.equals(queryHandleStatus)){
                cq.eq("status",Constants.HAZARDFACTORS_STATUS_ROLLBACK);
            }else{
                cq.in("status",new String[]{Constants.HAZARDFACTORS_STATUS_TOVIEW,Constants.HAZARDFACTORS_STATUS_REVIEW});
            }

            if(StringUtils.isNotBlank(riskType)){
                cq.eq("riskType",riskType);
            }
            if(StringUtils.isNotBlank(major)){
                cq.eq("major",major);
            }
            if(StringUtils.isNotBlank(riskLevel)){
                cq.eq("riskLevel",riskLevel);
            }
            if(StringUtils.isNotBlank(from)){
                cq.eq("from",from);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(HazardFactorsEntity hazardFactorsEntity ,HttpServletRequest request) {
        if(StringUtil.isNotEmpty(hazardFactorsEntity.getId())) {
            hazardFactorsEntity = systemService.getEntity(HazardFactorsEntity.class, hazardFactorsEntity.getId());
            request.setAttribute("hazardFactorsEntityPage", hazardFactorsEntity);
        }
        String taskManageId = request.getParameter("taskManageId");
        request.setAttribute("taskManageId",taskManageId);
        String jinqiao = ResourceUtil.getConfigByName("jinqiao");
        request.setAttribute("jinqiao",jinqiao);
        return new ModelAndView("com/sddb/buss/web/riskdata/hazardFactors-add");
    }

    @RequestMapping(params = "goAddPost")
    public ModelAndView goAddPost(HazardFactorsPostEntity hazardFactorsPostEntity , HttpServletRequest request) {
        if(StringUtil.isNotEmpty(hazardFactorsPostEntity.getId())) {
            hazardFactorsPostEntity = systemService.getEntity(HazardFactorsPostEntity.class, hazardFactorsPostEntity.getId());
            request.setAttribute("hazardFactorsPostEntityPage", hazardFactorsPostEntity);
        }
        String taskManagePostId = request.getParameter("taskManagePostId");
        request.setAttribute("taskManagePostId",taskManagePostId);
        String riskId = request.getParameter("riskId");
        request.setAttribute("riskId",riskId);
        return new ModelAndView("com/sddb/buss/web/riskdata/hazardFactors-addPost");
    }


    @RequestMapping(params = "goAddRisk")
    public ModelAndView goAddNew(HazardFactorsEntity hazardFactorsEntity ,HttpServletRequest request) {
        if(StringUtil.isNotEmpty(hazardFactorsEntity.getId())) {
            hazardFactorsEntity = systemService.getEntity(HazardFactorsEntity.class, hazardFactorsEntity.getId());
            request.setAttribute("hazardFactorsEntityPage", hazardFactorsEntity);
        }
        String riskId = request.getParameter("riskId");
        request.setAttribute("riskId",riskId);
        return new ModelAndView("com/sddb/buss/web/riskdata/hazardFactors-addRisk");
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(HazardFactorsEntity hazardFactorsEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "危害因素添加成功";
        try{
            hazardFactorsEntity.setIsDel(Constants.HAZARDFACTORS_IS_DEL_FALSE);
            hazardFactorsEntity.setFrom(Constants.HAZARDFACTORS_FROM_BASE);
            hazardFactorsEntity.setStatus(Constants.HAZARDFACTORS_STATUS_DRAFT);
            String status = request.getParameter("status");
            if(StringUtil.isNotEmpty(status)){
                hazardFactorsEntity.setStatus(status);
            }
            String taskManageId = request.getParameter("taskManageId");
            RiskManageTaskEntity riskManageTaskEntity = new RiskManageTaskEntity();
            if(StringUtil.isNotEmpty(taskManageId)){
                hazardFactorsEntity.setStatus("1");
                riskManageTaskEntity = systemService.getEntity(RiskManageTaskEntity.class,taskManageId);
                hazardFactorsEntity.setFrom("riskManage_"+riskManageTaskEntity.getManageType());
            }
            systemService.save(hazardFactorsEntity);
            if(StringUtil.isNotEmpty(status)){
                String riskId = request.getParameter("riskId");
                if(StringUtil.isNotEmpty(riskId)){
                    RiskIdentificationEntity t = systemService.get(RiskIdentificationEntity.class, riskId);
                    RiskFactortsRel rel = new RiskFactortsRel();
                    rel.setHazardFactorsEntity(hazardFactorsEntity);
                    rel.setRiskIdentificationEntity(t);
                    rel.setHfLevel(hazardFactorsEntity.getRiskLevel());
                    rel.setHfManageMeasure(hazardFactorsEntity.getManageMeasure());
                    systemService.save(rel);
                    t.setStatus(Constants.RISK_IDENTIFI_STATUS_DRAFT);
                    systemService.saveOrUpdate(t);
                    hazardFactorsEntity.setSubmitMan(ResourceUtil.getSessionUserName().getId());
                    hazardFactorsEntity.setFrom(t.getIdentificationType());
                    systemService.saveOrUpdate(hazardFactorsEntity);
                }
            }
            if(StringUtil.isNotEmpty(taskManageId)){
                riskManageTaskEntity = systemService.getEntity(RiskManageTaskEntity.class,taskManageId);
                riskManageTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED);
                systemService.saveOrUpdate(riskManageTaskEntity);
                String riskId = riskManageTaskEntity.getRisk().getId();
                if(StringUtil.isNotEmpty(riskId)){
                    RiskIdentificationEntity t = systemService.get(RiskIdentificationEntity.class, riskId);
                    RiskFactortsRel rel = new RiskFactortsRel();
                    rel.setHazardFactorsEntity(hazardFactorsEntity);
                    rel.setRiskIdentificationEntity(t);
                    rel.setHfLevel(hazardFactorsEntity.getRiskLevel());
                    rel.setHfManageMeasure(hazardFactorsEntity.getManageMeasure());
                    systemService.save(rel);
                    hazardFactorsEntity.setSubmitMan(ResourceUtil.getSessionUserName().getId());
                    systemService.saveOrUpdate(hazardFactorsEntity);
                    RiskManageHazardFactorEntity riskManageHazardFactorEntity = new RiskManageHazardFactorEntity();
                    riskManageHazardFactorEntity.setRiskManageTaskEntity(riskManageTaskEntity);
                    riskManageHazardFactorEntity.setTaskId(riskManageTaskEntity.getTaskId());
                    riskManageHazardFactorEntity.setRisk(riskManageTaskEntity.getRisk());
                    riskManageHazardFactorEntity.setManageType(riskManageTaskEntity.getManageType());
                    riskManageHazardFactorEntity.setHazardFactor(hazardFactorsEntity);
                    riskManageHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
                    this.systemService.save(riskManageHazardFactorEntity);
                    RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,riskManageTaskEntity.getTaskAllId());
                    riskManageTaskAllEntity.setStatus("0");
                    systemService.saveOrUpdate(riskManageTaskAllEntity);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "危害因素添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doAddPost")
    @ResponseBody
    public AjaxJson doAddPost(HazardFactorsPostEntity hazardFactorsPostEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "危害因素添加成功";
        try{
            hazardFactorsPostEntity.setIsDel(Constants.HAZARDFACTORS_IS_DEL_FALSE);
            systemService.save(hazardFactorsPostEntity);
            String taskManagePostId = request.getParameter("taskManagePostId");
            if(StringUtil.isNotEmpty(taskManagePostId)){
                RiskManagePostTaskEntity riskManagePostTaskEntity = systemService.getEntity(RiskManagePostTaskEntity.class,taskManagePostId);
                riskManagePostTaskEntity.setHandleStatus(Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED);
                systemService.saveOrUpdate(riskManagePostTaskEntity);
                String riskId = riskManagePostTaskEntity.getPostRisk().getId();
                if(StringUtil.isNotEmpty(riskId)){
                    RiskIdentificationPostEntity t = systemService.get(RiskIdentificationPostEntity.class, riskId);
                    RiskFactortsPostRel rel = new RiskFactortsPostRel();
                    rel.setHazardFactorsPostEntity(hazardFactorsPostEntity);
                    rel.setRiskIdentificationPostEntity(t);
                    systemService.save(rel);
                    RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity = new RiskManagePostHazardFactorEntity();
                    riskManagePostHazardFactorEntity.setRiskManagePostTaskEntity(riskManagePostTaskEntity);
                    riskManagePostHazardFactorEntity.setPostTaskId(riskManagePostTaskEntity.getPostTaskId());
                    riskManagePostHazardFactorEntity.setPostRisk(riskManagePostTaskEntity.getPostRisk());
                    riskManagePostHazardFactorEntity.setManageType(riskManagePostTaskEntity.getManageType());
                    riskManagePostHazardFactorEntity.setHazardFactorPost(hazardFactorsPostEntity);
                    riskManagePostHazardFactorEntity.setHandleStatus(Constants.RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED);
                    this.systemService.save(riskManagePostHazardFactorEntity);
                    RiskManagePostTaskAllEntity riskManagePostTaskAllEntity = systemService.getEntity(RiskManagePostTaskAllEntity.class,riskManagePostTaskEntity.getPostTaskAllId());
                    if(riskManagePostHazardFactorEntity!=null){
                        riskManagePostTaskAllEntity.setStatus("0");
                    }
                    systemService.saveOrUpdate(riskManagePostTaskAllEntity);
                }
            }
            String riskId = request.getParameter("riskId");
            if(StringUtil.isNotEmpty(riskId)){
                RiskIdentificationPostEntity riskIdentificationPostEntity = systemService.getEntity(RiskIdentificationPostEntity.class,riskId);
                RiskFactortsPostRel riskFactortsPostRel = new RiskFactortsPostRel();
                riskFactortsPostRel.setHazardFactorsPostEntity(hazardFactorsPostEntity);
                riskFactortsPostRel.setRiskIdentificationPostEntity(riskIdentificationPostEntity);
                systemService.saveOrUpdate(riskFactortsPostRel);
                }
        }catch(Exception e){
            e.printStackTrace();
            message = "危害因素添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(HazardFactorsEntity hazardFactorsEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(hazardFactorsEntity.getId())) {
            hazardFactorsEntity = systemService.getEntity(HazardFactorsEntity.class, hazardFactorsEntity.getId());
            req.setAttribute("hazardFactorsPage", hazardFactorsEntity);
        }
        String jinqiao = ResourceUtil.getConfigByName("jinqiao");
        req.setAttribute("jinqiao",jinqiao);
        return new ModelAndView("com/sddb/buss/web/riskdata/hazardFactors-update");
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(HazardFactorsEntity hazardFactorsEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "危害因素更新成功";
        HazardFactorsEntity t = systemService.get(HazardFactorsEntity.class, hazardFactorsEntity.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(hazardFactorsEntity, t);
            systemService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "危害因素更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goDetail")
    public ModelAndView goDetail(HazardFactorsEntity hazardFactorsEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(hazardFactorsEntity.getId())) {
            hazardFactorsEntity = systemService.getEntity( HazardFactorsEntity.class, hazardFactorsEntity.getId());
            req.setAttribute("hazardFactorsPage", hazardFactorsEntity);
        }
        return new ModelAndView("com/sddb/buss/web/riskdata/hazardFactors-detail");
    }

    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids,HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "删除成功";
        try{
            for(String id:ids.split(",")){
                HazardFactorsEntity hazardFactorsEntity = systemService.getEntity(HazardFactorsEntity.class, id);
                hazardFactorsEntity.setIsDel(Constants.HAZARDFACTORS_IS_DEL_TRUE);
                systemService.saveOrUpdate(hazardFactorsEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(HazardFactorsEntity hazardFactorsEntity, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        /*modelMap.put(TemplateExcelConstants.FILE_NAME,"危害因素导入模板");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(new Integer[]{0,1});
        templateExportParams.setDataSheetNum(2);
        templateExportParams.setTemplateUrl("export/template/importTemp_hazardFactors.xlsx");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String, Object> param =new HashMap<String, Object>();
        modelMap.put(TemplateExcelConstants.MAP_DATA,param);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;*/
        modelMap.put(TemplateExcelConstants.FILE_NAME,"基础危害因素导入模板");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(1);
        templateExportParams.setScanAllsheet(true);

        Map<String, Object> param =new HashMap<String, Object>();
        templateExportParams.setTemplateUrl("export/template/importTemp_hazardFactors.xls");
        Map<String, List<String>> dicListMap = new HashMap();

        //查询风险类型
        String riskTypeSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='risk_type')";
        List<String> riskTypeList = systemService.findListbySql(riskTypeSql);

        //查询专业
        String majorSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='major')";
        List<String> majorList = systemService.findListbySql(majorSql);

        //查询危害因素等级
        String riskLevelSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='factors_level')";
        List<String> riskLevelList = systemService.findListbySql(riskLevelSql);

        dicListMap.put("riskTypeList", riskTypeList);
        dicListMap.put("majorList", majorList);
        dicListMap.put("riskLevelList", riskLevelList);
        List<HazardFactorsEntity> dicVOList = new ArrayList<HazardFactorsEntity>();

        //得到这几串数列的最长的一列，excel导出的行数即为最长一列的长度
        int[] listLength = {dicListMap.get("riskTypeList").size(), dicListMap.get("majorList").size(), dicListMap.get("riskLevelList").size()};
        int maxLength = listLength[0];
        for (int i = 0; i < listLength.length; i++) {   //开始循环一维数组
            if (listLength[i] > maxLength) {  //循环判断数组元素
                maxLength = listLength[i];
            }  //赋值给num，然后再次循环
        }

        for (int j = 0; j < maxLength; j++) {
            HazardFactorsEntity vo = new HazardFactorsEntity();
            if (j < dicListMap.get("riskTypeList").size()) {
                vo.setRiskTypeTemp(dicListMap.get("riskTypeList").get(j));
            }
            if (j < dicListMap.get("majorList").size()) {
                vo.setMajorTemp(dicListMap.get("majorList").get(j));
            }
            if (j < dicListMap.get("riskLevelList").size()) {
                vo.setRiskLevelTemp(dicListMap.get("riskLevelList").get(j));
            }
            dicVOList.add(vo);
        }

        //将字典赋值到param中，写到sheet2中
        param.put("dicVoList", dicVOList);
        modelMap.put(TemplateExcelConstants.PARAMS, templateExportParams);
        modelMap.put(TemplateExcelConstants.MAP_DATA, param);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
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
            params.setVerifyHanlder(new HazardFactorsExcelVerifyHandler());
            try {
                ExcelImportResult<HazardFactorsEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(),HazardFactorsEntity.class,params);
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
                    hazardFactorsService.importDataSava(result);
                    j.setMsg("文件导入成功！");
                }

            } catch (Exception e) {
                j.setMsg("文件导入失败！");
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

    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name","hazardFactorsController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    @RequestMapping(params = "exportXls")
    public String exportXls(HazardFactorsEntity hazardFactorsEntity,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {
        String queryHandleStatus = request.getParameter("queryHandleStatus");
        String riskType = request.getParameter("riskType");
        String major = request.getParameter("major");
        String ids = request.getParameter("ids");
        CriteriaQuery cq = new CriteriaQuery(HazardFactorsEntity.class, dataGrid);

        try{
            if(StringUtils.isNotBlank(ids)){
                cq.in("id",ids.split(","));
            }else {
                cq.eq("isDel", Constants.HAZARDFACTORS_IS_DEL_FALSE);
                if (StringUtils.isBlank(queryHandleStatus) || Constants.HAZARDFACTORS_STATUS_DRAFT.equals(queryHandleStatus)) {
                    cq.eq("status", Constants.HAZARDFACTORS_STATUS_DRAFT);
                } else if (Constants.HAZARDFACTORS_STATUS_ROLLBACK.equals(queryHandleStatus)) {
                    cq.eq("status", Constants.HAZARDFACTORS_STATUS_ROLLBACK);
                } else {
                    cq.in("status", new String[]{Constants.HAZARDFACTORS_STATUS_TOVIEW, Constants.HAZARDFACTORS_STATUS_REVIEW});
                }


                if (StringUtils.isNotBlank(riskType)) {
                    cq.eq("riskType", riskType);
                }
                if (StringUtils.isNotBlank(major)) {
                    cq.eq("major", major);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        List<HazardFactorsEntity> retList = this.systemService.getListByCriteriaQuery(cq, false);
        if(retList != null && !retList.isEmpty()){
            for(HazardFactorsEntity bean : retList){
                //专业
                String major2 = bean.getMajor();
                if(StringUtils.isNotBlank(major2)){
                    String majorTemp = DicUtil.getTypeNameByCode("major", major2);
                    bean.setMajorTemp(majorTemp);
                }
                //风险类型
                String riskType2 = bean.getRiskType();
                if(StringUtils.isNotBlank(riskType2)){
                    String riskTypeTemp = DicUtil.getTypeNameByCode("risk_type", riskType2);
                    bean.setRiskTypeTemp(riskTypeTemp);
                }
                //危害等级
                String riskLevel = bean.getRiskLevel();
                if(StringUtils.isNotBlank(riskLevel)){
                    String risklevelTemp = DicUtil.getTypeNameByCode("factors_level", riskLevel);
                    bean.setRiskLevelTemp(risklevelTemp);
                }

            }
        }
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetName("危害因素导出列表");

        templateExportParams.setSheetNum(0);
      //  templateExportParams.setTemplateUrl("export/template/exportTemp_departDangerSourceLec_new.xls");
        templateExportParams.setTemplateUrl("export/template/exportTemp_hazardFactors.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", retList);
        modelMap.put(NormalExcelConstants.FILE_NAME,"危害因素导出列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }



    @RequestMapping(params = "goReview")
    public ModelAndView goReview (HttpServletRequest req) {
        String ids = req.getParameter("ids");
        req.setAttribute("ids", ids);
        return new ModelAndView("com/sddb/buss/web/riskdata/hazardFactorsReview");
    }

    @RequestMapping(params = "doReview")
    @ResponseBody
    public AjaxJson doReview(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "上报成功";
        String[] idAdday = ids.split(",");
        for(String id : idAdday){
            HazardFactorsEntity bean = systemService.getEntity(HazardFactorsEntity.class,id);
            bean.setStatus(Constants.HAZARDFACTORS_STATUS_TOVIEW);
            bean.setSubmitMan(ResourceUtil.getSessionUserName().getId());
            systemService.saveOrUpdate(bean);
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "reviewList")
    public ModelAndView reviewList(HttpServletRequest request) {
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        boolean isAdmin = false;
        CriteriaQuery cqru = new CriteriaQuery(TSRoleUser.class);
        try{
            cqru.eq("TSUser.id",sessionUser.getId());
        }catch(Exception e){
            e.printStackTrace();
        }
        cqru.add();
        List<TSRoleUser> roleList = systemService.getListByCriteriaQuery(cqru,false);
        if(roleList != null && !roleList.isEmpty()){
            for(TSRoleUser ru : roleList){
                TSRole role = ru.getTSRole();
                if(role != null && role.getRoleName().equals("管理员")){
                    isAdmin = true;
                    break;
                }
            }
        }
        if (isAdmin) { //管理员可以查看所有的
            request.setAttribute("isAdmin","1");
        }
        return new ModelAndView("com/sddb/buss/web/riskdata/hazardFactorsVeviewListList");
    }

    @RequestMapping(params = "reviewDatagrid")
    public void reviewDatagrid(HazardFactorsEntity hazardFactorsEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String queryHandleStatus = request.getParameter("queryHandleStatus");
        String riskType = request.getParameter("riskType");
        String major = request.getParameter("major");
        String status = request.getParameter("status");
        CriteriaQuery cq = new CriteriaQuery(HazardFactorsEntity.class, dataGrid);
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        boolean isAdmin = false;
        CriteriaQuery cqru = new CriteriaQuery(TSRoleUser.class);
        try{
            cqru.eq("TSUser.id",sessionUser.getId());
        }catch(Exception e){
            e.printStackTrace();
        }
        cqru.add();
        List<TSRoleUser> roleList = systemService.getListByCriteriaQuery(cqru,false);
        if(roleList != null && !roleList.isEmpty()){
            for(TSRoleUser ru : roleList){
                TSRole role = ru.getTSRole();
                if(role != null && role.getRoleName().equals("管理员")){
                    isAdmin = true;
                    break;
                }
            }
        }
        try{
            cq.eq("isDel",Constants.HAZARDFACTORS_IS_DEL_FALSE);
            if(StringUtils.isBlank(queryHandleStatus) || "false".equals(queryHandleStatus) || "0".equals(queryHandleStatus)){
                cq.eq("status",Constants.HAZARDFACTORS_STATUS_TOVIEW);
            }else{
                cq.in("status",new String[]{Constants.HAZARDFACTORS_STATUS_REVIEW,Constants.HAZARDFACTORS_STATUS_ROLLBACK});
            }
            if(StringUtils.isNotBlank(status)){
                cq.eq("status",status);
            }
            if(StringUtils.isNotBlank(riskType)){
                cq.eq("riskType",riskType);
            }
            if(StringUtils.isNotBlank(major)){
                cq.eq("major",major);
            }
            if (!isAdmin) { //管理员可以查看所有的
                cq.eq("reviewMan",sessionUser.getId());
            }
            String riskLevel = request.getParameter("riskLevel");
            if(StringUtils.isNotBlank(riskLevel)){
                cq.eq("riskLevel",riskLevel);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goApproval")
    public ModelAndView goApproval (HttpServletRequest req) {
        String id = req.getParameter("id");
        HazardFactorsEntity hazardFactorsEntity =null;
        if (StringUtil.isNotEmpty(id)) {
            hazardFactorsEntity = systemService.getEntity(HazardFactorsEntity.class, id);
            req.setAttribute("hazardFactorsEntity", hazardFactorsEntity);
        }
        return new ModelAndView("com/sddb/buss/web/riskdata/hazardFactorsVeviewListList-checkAll");
    }


    @RequestMapping(params = "goAllApproval")
    public ModelAndView goAllApproval (HttpServletRequest req) {
        String ids = req.getParameter("ids");
        req.setAttribute("ids", ids);
        return new ModelAndView("com/sddb/buss/web/riskdata/hazardFactorsVeviewListList-AllApproval");
    }

    @RequestMapping(params = "saveApproval")
    @ResponseBody
    public AjaxJson saveApproval(HazardFactorsEntity hazardFactorsEntity,HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        message = "危害因素审批成功";
        HazardFactorsEntity t = systemService.get(HazardFactorsEntity.class, hazardFactorsEntity.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(hazardFactorsEntity, t);
            systemService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "危害因素审批失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "saveAllApproval")
    @ResponseBody
    public AjaxJson saveAllApproval(HazardFactorsEntity hazardFactorsEntity,HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        message = "危害因素审批成功";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ids = request.getParameter("ids");
        String status = request.getParameter("status");
        String modifyMan = request.getParameter("modifyMan");
        String rollBackRemark = request.getParameter("rollBackRemark");
        String date = request.getParameter("modifyDate");
        Date modifyDate = new Date();
        try {
            if(StringUtil.isNotEmpty(date)){
                modifyDate = sdf.parse(request.getParameter("modifyDate"));
            }
            for(String id:ids.split(",")) {
                HazardFactorsEntity t = systemService.get(HazardFactorsEntity.class, id);
                if(status.equals("3")){
                    t.setStatus(status);
                    t.setModifyDate(modifyDate);
                    t.setModifyMan(modifyMan);
                    systemService.saveOrUpdate(t);
                }else{
                    t.setStatus(status);
                    t.setRollBackRemark(rollBackRemark);
                    systemService.saveOrUpdate(t);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "moduleList")
    public ModelAndView ModularList(HttpServletRequest request) {
        return new ModelAndView("com/sddb/buss/web/riskdata/hazardModuleList");
    }

    @RequestMapping(params = "goAddModule")
    public ModelAndView goAddModule(HazardModuleEntity hazardModuleEntity , HttpServletRequest request) {
        return new ModelAndView("com/sddb/buss/web/riskdata/hazardModule-add");
    }

    @RequestMapping(params = "moduleNameExists")
    @ResponseBody
    public Map<String, String> moduleNameExists(HttpServletRequest request){
        Map<String, String> result = new HashMap();
        String moduleName = ResourceUtil.getParameter("param");
        String riskType = request.getParameter("riskType");
        if (checkModuleNameExists(moduleName,riskType)){
            result.put("status", "n");
            result.put("info","此模块已存在！");
        } else {
            result.put("status", "y");
            result.put("info","通过信息验证！");
        }
        return result;
    }
    private boolean checkModuleNameExists(String moduleName,String riskType){
        String sql = "SELECT * FROM t_b_hazard_module WHERE module_name = '"+moduleName+"' and risk_type = '"+riskType+"'";
        List<String> moduleList = systemService.findListbySql(sql);
        if(moduleList!=null&&moduleList.size()>0){
            return  true;
        }
        return false;
    }

    @RequestMapping(params = "doAddModule")
    @ResponseBody
    public AjaxJson doAddModule(HazardModuleEntity hazardModuleEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "模块危害因素添加成功";
        try{
            String moduleName = hazardModuleEntity.getModuleName().replaceAll("\n","").replaceAll("\r","").replaceAll(" ","");
            hazardModuleEntity.setModuleName(moduleName);
            systemService.save(hazardModuleEntity);
        }catch(Exception e){
            e.printStackTrace();
            message = "模块危害因素添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "datagridModule")
    public void datagridModule(HazardModuleEntity hazardModuleEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String riskType = request.getParameter("riskType");
        String moduleName = request.getParameter("moduleName");
        CriteriaQuery cq = new CriteriaQuery(HazardModuleEntity.class, dataGrid);
        try{
            if(StringUtils.isNotBlank(riskType)){
                cq.eq("riskType",riskType);
            }
            if(StringUtils.isNotBlank(moduleName)){
                cq.like("moduleName", "%"+moduleName+"%");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goUpdateModule")
    public ModelAndView goUpdateModule(HazardModuleEntity hazardModuleEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(hazardModuleEntity.getId())) {
            hazardModuleEntity = systemService.getEntity(HazardModuleEntity.class, hazardModuleEntity.getId());
            req.setAttribute("hazardModulePage", hazardModuleEntity);
        }
        return new ModelAndView("com/sddb/buss/web/riskdata/hazardModule-update");
    }
    @RequestMapping(params = "doUpdateModule")
    @ResponseBody
    public AjaxJson doUpdateModule(HazardModuleEntity hazardModuleEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "模块危害因素更新成功";
        HazardModuleEntity t = systemService.get(HazardModuleEntity.class, hazardModuleEntity.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(hazardModuleEntity, t);
            systemService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "模块危害因素更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goDetailModule")
    public ModelAndView goDetailModule(HazardModuleEntity hazardModuleEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(hazardModuleEntity.getId())) {
            hazardModuleEntity = systemService.getEntity( HazardModuleEntity.class, hazardModuleEntity.getId());
            req.setAttribute("hazardModulePage", hazardModuleEntity);
        }
        return new ModelAndView("com/sddb/buss/web/riskdata/hazardModule-detail");
    }


    @RequestMapping(params = "datagridModuleHazardRel")
    public void datagridModuleHazardRel(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        this.hazardFactorsService.getModularHazardRelList(request, dataGrid);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goModuleHazardList")
    public ModelAndView goModuleHazardList(HazardModuleEntity hazardModuleEntity, HttpServletRequest req) {
        String modularId = req.getParameter("modularId");
        req.setAttribute("modularId",modularId);
        String rel = req.getParameter("rel");
        req.setAttribute("rel",rel);
        hazardModuleEntity = systemService.getEntity(HazardModuleEntity.class,modularId);
        req.setAttribute("riskType",hazardModuleEntity.getRiskType());
        if(StringUtil.isNotEmpty(rel) && rel.equals("noRel")){
            return new ModelAndView("com/sddb/buss/web/riskdata/modularHazardNoRelList");
        }
        return new ModelAndView("com/sddb/buss/web/riskdata/modularHazardRelList");
    }
    @RequestMapping(params = "addRelBatch")
    @ResponseBody
    public AjaxJson addRelBatch(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "关联成功";
        try{
            if(StringUtil.isNotEmpty(request.getParameter("modularId")) && StringUtil.isNotEmpty(request.getParameter("harmId"))){
                String id[] = request.getParameter("harmId").split(",");
                StringBuffer idSb = new StringBuffer();
                if(id != null && id.length > 0){
                    for(int i = 0 ; i < id.length ; i++){
                        StringBuffer sb = new StringBuffer();
                        sb.append("INSERT INTO t_b_hazard_module_rel (`id`, `modular_id`, `hazard_id`) VALUES ('");
                        sb.append(UUIDGenerator.generate());
                        sb.append("', '");
                        sb.append(request.getParameter("modularId"));
                        sb.append("', '");
                        sb.append(id[i]);
                        sb.append("');");
                        this.systemService.executeSql(sb.toString());
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "关联失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    @RequestMapping(params = "delRelBatch")
    @ResponseBody
    public AjaxJson delRelBatch(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "关联成功";
        try{
            if(StringUtil.isNotEmpty(request.getParameter("modularId")) && StringUtil.isNotEmpty(request.getParameter("harmId"))){
                String id[] = request.getParameter("harmId").split(",");
                StringBuffer idSb = new StringBuffer();
                if(id != null && id.length > 0){
                    for(int i = 0 ; i < id.length ; i++){
                        if(StringUtil.isNotEmpty(idSb.toString())){
                            idSb.append(",");
                        }
                        idSb.append("'").append(id[i]).append("'");
                    }
                }
                StringBuffer delSb = new StringBuffer();
                delSb.append("DELETE FROM t_b_hazard_module_rel WHERE modular_id = '").append(request.getParameter("modularId")).append("' AND hazard_id in(").append(idSb.toString()).append(");");
                this.systemService.executeSql(delSb.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "关联失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    @RequestMapping(params = "doBatchDelModular")
    @ResponseBody
    public AjaxJson doBatchDelModular(String ids,HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "删除成功";
        try{
            for(String id:ids.split(",")){
                StringBuffer delSb1 = new StringBuffer();
                delSb1.append("DELETE FROM t_b_hazard_module WHERE id = '").append(id).append("' ");
                StringBuffer delSb2 = new StringBuffer();
                delSb2.append("DELETE FROM t_b_hazard_module_rel WHERE modular_id = '").append(id).append("' ");
                this.systemService.executeSql(delSb2.toString());
                this.systemService.executeSql(delSb1.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    @RequestMapping(params = "getMyModularList")
    @ResponseBody
    public JSONArray getMyAddressList(HttpServletRequest request){
        String riskType = request.getParameter("riskType");
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT id,module_name FROM t_b_hazard_module WHERE risk_type = '").append(riskType).append("' ");
        JSONArray jsonArray = JSONArray.fromObject(this.systemService.findForJdbc(sb.toString()));
        return  jsonArray;
    }
    //取消关联
    @RequestMapping(params = "doBatchDelRel")
    @ResponseBody
    public AjaxJson doBatchDelRel(HttpServletRequest request){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "取消成功";
        try{
            String ids = request.getParameter("ids");
            String riskId = request.getParameter("riskId");
            for(String id:ids.split(",")){
                String sql = "DELETE FROM t_b_risk_factors_rel WHERE hazard_factors_id ='"+id+"' and risk_identification_id='"+riskId+"'";

                String selectStateFlagSql="select state_flag stateFlag from sf_risk_measure where risk_factors_id='"+id+"' and risk_code='"+riskId+"' limit 1";
                Map<String, Object> stateFlag = systemService.findOneForJdbc(selectStateFlagSql);
                String riskMeasureSql="";
                if (stateFlag!=null&&stateFlag.get("stateFlag")!=null&&"0".equals(stateFlag.get("stateFlag").toString())){
                    riskMeasureSql="update `sf_risk_measure` set state_flag='3',is_upload='1',depart='' where risk_code='"+riskId+"' and risk_factors_id='"+id+"'";
                }else {
                    riskMeasureSql="DELETE FROM `sf_risk_measure` where risk_code='"+riskId+"' and risk_factors_id='"+id+"'";
                }
                systemService.executeSql(riskMeasureSql);
                systemService.executeSql(sql);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "取消失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
}
