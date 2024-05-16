package com.sddb.buss.riskdata.controller;

import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import com.sddb.buss.riskdata.entity.HazardModuleEntity;
import com.sddb.buss.riskdata.entity.HazardModuleRelEntity;
import com.sddb.buss.riskdata.entity.HazardModuleVoEntity;
import com.sddb.buss.riskdata.service.HazardFactorsServiceI;
import com.sddb.common.Constants;
import com.sddb.common.excelverify.HazardFactorsExcelVerifyHandler;
import com.sddb.common.excelverify.HazardFactorsModuleExcelVerifyHandler;
import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("prototype")
@Controller
@RequestMapping("/hazardFactorsModuleController")
public class HazardFactorsModuleController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private HazardFactorsServiceI hazardFactorsService;
    private String message;


    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(HazardFactorsEntity hazardFactorsEntity, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME,"模块危害因素导入模板");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(1);
        templateExportParams.setScanAllsheet(true);

        Map<String, Object> param =new HashMap<String, Object>();
        templateExportParams.setTemplateUrl("export/template/importTemp_hazardModule.xls");
        Map<String, List<String>> dicListMap = new HashMap();

        //查询风险类型
        String riskTypeSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='risk_type')";
        List<String> riskTypeList = systemService.findListbySql(riskTypeSql);

        dicListMap.put("riskTypeList", riskTypeList);
        List<HazardModuleVoEntity> dicVOList = new ArrayList<HazardModuleVoEntity>();

        //得到这几串数列的最长的一列，excel导出的行数即为最长一列的长度
        int[] listLength = {dicListMap.get("riskTypeList").size()};
        int maxLength = listLength[0];
        for (int i = 0; i < listLength.length; i++) {   //开始循环一维数组
            if (listLength[i] > maxLength) {  //循环判断数组元素
                maxLength = listLength[i];
            }  //赋值给num，然后再次循环
        }

        for (int j = 0; j < maxLength; j++) {
            HazardModuleVoEntity vo = new HazardModuleVoEntity();
            if (j < dicListMap.get("riskTypeList").size()) {
                vo.setRiskTypeTemp(dicListMap.get("riskTypeList").get(j));
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
            params.setTitleRows(0);
            params.setHeadRows(1);
            params.setNeedSave(false);
            params.setNeedVerfiy(true);
            params.setVerifyHanlder(new HazardFactorsModuleExcelVerifyHandler());
            try {
                ExcelImportResult<HazardModuleVoEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(),HazardModuleVoEntity.class,params);
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
                    CriteriaQuery cq = new CriteriaQuery(HazardFactorsEntity.class);
                    cq.eq("isDel","0");
                    cq.eq("status","3");
                    cq.add();
                    List<HazardFactorsEntity> hazardFactorsEntityList =systemService.getListByCriteriaQuery(cq,false);
                    Map<String, String> hazardFactorsEntityMap = new HashMap<>();
                    for(HazardFactorsEntity entity1 : hazardFactorsEntityList) {
                        String riskType = entity1.getRiskType();
                        String hazard = entity1.getHazardFactors();
                        hazardFactorsEntityMap.put(entity1.getId(),riskType+hazard );
                    }

                    String sql = "SELECT id,risk_type,module_name from t_b_hazard_module";
                    List<Map<String, Object>> hazardModuleList = systemService.findForJdbc(sql);
                    Map<String, String> hazardModuleMap = new HashMap<>();
                    if (hazardModuleList !=null && hazardModuleList.size()>0) {
                        for (Map<String, Object> obj : hazardModuleList) {
                            String riskType = String.valueOf(obj.get("risk_type"));
                            String moduleName = String.valueOf(obj.get("module_name"));
                            String id = String.valueOf(obj.get("id"));
                            hazardModuleMap.put(riskType+moduleName, id);
                        }
                    }
                    systemService.addLog("模块危害因素库开始导入", Globals.Log_Type_UPLOAD,Globals.Log_Type_UPLOAD);
                    for (int i = 0; i < result.getList().size(); i++) {
                        HazardModuleVoEntity hazardModuleVoEntity = result.getList().get(i);
                        String riskType = DicUtil.getTypeCodeByName("risk_type", hazardModuleVoEntity.getRiskTypeTemp());
                        String moduleName = hazardModuleVoEntity.getModuleNameTemp();

                        String moduleId = hazardModuleMap.get(riskType+moduleName);
                        if (StringUtil.isEmpty(moduleId)){
                            HazardModuleEntity hazardModuleEntity = new HazardModuleEntity();
                            hazardModuleEntity.setModuleName(moduleName);
                            hazardModuleEntity.setRiskType(riskType);
                            systemService.save(hazardModuleEntity);
                            moduleId=hazardModuleEntity.getId();
                            hazardModuleMap.put(riskType+moduleName, hazardModuleEntity.getId());
                        }
                        String hazard = hazardModuleVoEntity.getHazardFactorsTemp();
                        List<String> hazardFactorsList = new ArrayList<>();
                        for(String key: hazardFactorsEntityMap.keySet()){
                            if(hazardFactorsEntityMap.get(key).equals(riskType+hazard)){
                                hazardFactorsList.add(key);
                            }
                        }
                        if(hazardFactorsList.size()>0&&hazardFactorsList!=null){
                            for(String hazardFactorsId:hazardFactorsList){
                                String insertSql = "insert into t_b_hazard_module_rel (id, modular_id, hazard_id) select " +
                                        "'"+UUIDGenerator.generate()+"'," +
                                        "'"+moduleId+"'," +
                                        "'"+hazardFactorsId+"' " +
                                        "from dual where not exists " +
                                        "(select 1 from t_b_hazard_module_rel where modular_id='"+moduleId+"' and hazard_id='"+hazardFactorsId+"')";
                                systemService.executeSql(insertSql);
                            }
                        }
                    }
                    systemService.addLog("模块危害因素库导入完成", Globals.Log_Type_UPLOAD,Globals.Log_Type_UPLOAD);
                    hazardFactorsEntityMap.clear();
                    hazardModuleMap.clear();



                    //List<HazardModuleRelEntity> hazardModuleRelEntityList = new ArrayList<HazardModuleRelEntity>();
//                    for (int i = 0; i < result.getList().size(); i++) {
//                        HazardModuleVoEntity hazardModuleVoEntity = result.getList().get(i);
//                        HazardModuleEntity hazardModuleEntity = new HazardModuleEntity();
//                        hazardModuleEntity.setModuleName(hazardModuleVoEntity.getModuleNameTemp());
//                        hazardModuleEntity.setRiskType(DicUtil.getTypeCodeByName("risk_type", hazardModuleVoEntity.getRiskTypeTemp()));
//                        String sql = "SELECT id from t_b_hazard_module WHERE risk_type = '"+hazardModuleEntity.getRiskType()+"' and module_name = '"+hazardModuleEntity.getModuleName()+"'";
//                        List<String> moduleId = systemService.findListbySql(sql);
//                        if(moduleId.size()>0){
//                            for(HazardFactorsEntity hazardFactorsEntity:hazardFactorsEntityList){
//                                if(hazardFactorsEntity.getRiskType().equals(hazardModuleEntity.getRiskType())
//                                        &&hazardFactorsEntity.getHazardFactors().equals(hazardModuleVoEntity.getHazardFactorsTemp())){
//                                    HazardModuleRelEntity hazardModuleRelEntity = new HazardModuleRelEntity();
//                                    hazardModuleRelEntity.setHazardId(hazardFactorsEntity.getId());
//                                    hazardModuleRelEntity.setModularId(moduleId.get(0));
//                                    //hazardModuleRelEntityList.add(hazardModuleRelEntity);
//                                    systemService.save(hazardModuleRelEntity);
//                                }
//                            }
//                        }else{
//                            systemService.save(hazardModuleEntity);
//                            for(HazardFactorsEntity hazardFactorsEntity:hazardFactorsEntityList){
//                                if(hazardFactorsEntity.getRiskType().equals(hazardModuleEntity.getRiskType())
//                                        &&hazardFactorsEntity.getHazardFactors().equals(hazardModuleVoEntity.getHazardFactorsTemp())){
//                                    HazardModuleRelEntity hazardModuleRelEntity = new HazardModuleRelEntity();
//                                    hazardModuleRelEntity.setHazardId(hazardFactorsEntity.getId());
//                                    hazardModuleRelEntity.setModularId(hazardModuleEntity.getId());
//                                   // hazardModuleRelEntityList.add(hazardModuleRelEntity);
//                                    systemService.save(hazardModuleRelEntity);
//                                }
//                            }
//                        }
//                    }
                    //systemService.batchSave(hazardModuleRelEntityList);
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



    @RequestMapping(params = "uploadModule")
    public ModelAndView uploadModule(HttpServletRequest req) {
        req.setAttribute("controller_name","hazardFactorsModuleController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }
}
