package com.sddb.buss.identification.controller;

import com.sddb.buss.identification.entity.MonthRiskExcelVerifyHandler;
import com.sddb.buss.identification.entity.MonthRiskExportDicVO;
import com.sddb.buss.identification.entity.MonthRiskIdentificationEntity;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Scope("prototype")
@Controller
@RequestMapping("/monthRiskIdentificationController")
public class MonthRiskIdentificationController {

    @Autowired
    private SystemService systemService;
    private static final Logger logger = Logger.getLogger(MonthRiskIdentificationController.class);

    @RequestMapping(params = "list")
    public ModelAndView redList(HttpServletRequest request) {
      String type=  request.getParameter("type");
        request.setAttribute("type",type);
        if(type.equals("month")){
            return new ModelAndView("com/sddb/buss/web/monthIdentification/list");
        }else{
            Date now=new Date();
            request.setAttribute("year", DateUtils.date2Str(now,new SimpleDateFormat("yyyy")));
            return new ModelAndView("com/sddb/buss/web/monthIdentification/quarterList");
        }

    }
    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        String type=  req.getParameter("type");
        req.setAttribute("controller_name", "monthRiskIdentificationController");
        req.setAttribute("function_name", "importExcelT&type="+type);
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导入
     *
     * @return
     */
    @RequestMapping(params = "importExcelT", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcelT(HttpServletRequest request, HttpServletResponse response) {
        String type=  request.getParameter("type");
        AjaxJson j = new AjaxJson();
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        List<TBAddressInfoEntity> addressList = systemService.findByProperty(TBAddressInfoEntity.class,"isDelete","0");
        Map<String, TBAddressInfoEntity> addressMap = new HashedMap();
        if (addressList != null && addressList.size()>0) {
            for (TBAddressInfoEntity entity : addressList) {
                addressMap.put(entity.getAddress(), entity);
            }
        }

        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setNeedSave(false);
            params.setNeedVerfiy(true);
            params.setVerifyHanlder(new MonthRiskExcelVerifyHandler(addressMap,type));
            try {
                ExcelImportResult<MonthRiskIdentificationEntity> result = ExcelImportUtil.importExcelVerify(file.getInputStream(), MonthRiskIdentificationEntity.class, params);
                if (result.isVerfiyFail()) {
                    String uploadpathtemp = ResourceUtil.getConfigByName("uploadpathtemp");
                    String realPath = multipartRequest.getSession().getServletContext().getRealPath("/") + "/" + uploadpathtemp + "/";// 文件的硬盘真实路径
                    File fileTemp = new File(realPath);
                    if (!fileTemp.exists()) {
                        fileTemp.mkdirs();// 创建根目录
                    }
                    String name = DateUtils.getDataString(DateUtils.yyyymmddhhmmss) + ".xls";
                    realPath += name;
                    FileOutputStream fos = new FileOutputStream(realPath);
                    result.getWorkbook().write(fos);
                    fos.close();
                    Map<String, Object> attributes = new HashMap<String, Object>();
                    attributes.put("path", uploadpathtemp + "/" + name);
                    j.setAttributes(attributes);
                    j.setSuccess(false);
                    j.setMsg("导入数据校验失败");
                } else {
                    List<MonthRiskIdentificationEntity> list = new ArrayList<>();
                    for (int i = 0; i < result.getList().size(); i++) {
                        MonthRiskIdentificationEntity MonthRiskIdentificationEntity = (MonthRiskIdentificationEntity)result.getList().get(i);
                        list.add(MonthRiskIdentificationEntity);
                    }
                    systemService.batchSave(list);
                    j.setMsg("文件导入成功！");
                    systemService.addLog(j.getMsg(), Globals.Log_Leavel_INFO, Globals.Log_Type_UPLOAD);
                }
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                systemService.addLog(j.getMsg() + "：" + e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPLOAD);
                logger.error(ExceptionUtil.getExceptionMessage(e));
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return j;
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(MonthRiskIdentificationEntity monthRiskIdentificationEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws ParseException {
        String addressId = request.getParameter("addressId");
        String riskType = request.getParameter("riskType");
        String riskLevel = request.getParameter("riskLevel");
        String identificationType = request.getParameter("identificationType");
        String yeRecognizeTimeTemp = request.getParameter("yeRecognizeTimeTemp");
        String type=  request.getParameter("type");
        String quarter=  request.getParameter("quarter");

        CriteriaQuery cq = new CriteriaQuery(MonthRiskIdentificationEntity.class, dataGrid);
        cq.eq("isDel","0");
        if(StringUtils.isNotEmpty(addressId)){
            cq.eq("address",addressId);
        }
        if(StringUtils.isNotEmpty(type)){
            cq.eq("type",type);
        }
        if(StringUtils.isNotEmpty(riskType)){
            cq.eq("riskType",riskType);
        }
        if(StringUtils.isNotEmpty(riskLevel)){
            cq.eq("riskLevel",riskLevel);
        }
        if(StringUtils.isNotEmpty(identificationType)){
            cq.eq("identificationType",identificationType);
        }
        if(StringUtils.isNotEmpty(yeRecognizeTimeTemp)){
            cq.eq("month",yeRecognizeTimeTemp);
        }
        if(StringUtils.isNotEmpty(quarter)){
            cq.eq("quarter",quarter);
        }

        Map orderMap=new HashMap();
        orderMap.put("month", SortDirection.desc);
        cq.setOrder(orderMap);
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null) {
            if (dataGrid.getResults().size() > 0) {
                List<MonthRiskIdentificationEntity> list = dataGrid.getResults();
                for (MonthRiskIdentificationEntity t : list) {
                    /**地点*/
                    if (StringUtils.isNotEmpty(t.getAddress())) {
                        TBAddressInfoEntity addressInfoEntity = systemService.getEntity(TBAddressInfoEntity.class, t.getAddress());
                        if (addressInfoEntity != null) {
                            t.setAddressNameTemp(addressInfoEntity.getAddress());
                        }
                    }
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);

    }
    /**
     * 批量删除
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(HttpServletRequest request){
        String ids = request.getParameter("ids");
        AjaxJson j = new AjaxJson();
        String message = "删除成功";
        try {
            for (String id : ids.split(",")) {
                String sql = "update t_b_month_risk_identification  set is_del='1',update_date=now() where id = '" + String.valueOf(id) + "'";
                systemService.executeSql(sql);
            }
        }catch (Exception e){
            e.printStackTrace();
            message = "删除异常";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    /**
     * 查看页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goDetail")
    public ModelAndView goDetail(MonthRiskIdentificationEntity redYellowEntity, HttpServletRequest request) {
        CriteriaQuery cq = new CriteriaQuery(MonthRiskIdentificationEntity.class);
        String id = request.getParameter("id");
        if(StringUtils.isNotEmpty(id)){
            MonthRiskIdentificationEntity  entity= systemService.getEntity(MonthRiskIdentificationEntity.class,id);
            request.setAttribute("data",entity);
            if(entity.getType().equals("month")){
                request.setAttribute("month",true);
            }else{
                request.setAttribute("quarter",entity.getMonth()+"年 "+ DicUtil.getTypeNameByCode("quarter",entity.getQuarter()));
            }
        }
        return new ModelAndView("com/sddb/buss/web/monthIdentification/detail");
    }
    /**
     * 模板下载
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(ModelMap modelMap, HttpServletRequest request) {

        TemplateExportParams templateExportParams = new TemplateExportParams();
        Map<String, Object> map = new HashMap<String, Object>();
        String type = request.getParameter("type");
        if(type.equals("month")){
            map.put("title", "月度风险管控清单导入模版");
            modelMap.put(TemplateExcelConstants.FILE_NAME, "月度风险管控清单导入模板");
            templateExportParams.setTemplateUrl("export/template/importTemp_monthRisk.xls");
        }else{
            map.put("title", "季度风险管控清单导入模版");
            modelMap.put(TemplateExcelConstants.FILE_NAME, "季度风险管控清单导入模板");
            templateExportParams.setTemplateUrl("export/template/importTemp_quarterRisk.xls");
        }


        //查询    风险类型          0
        String riskTypeSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='risk_type')";
        List<String> riskTypeList = systemService.findListbySql(riskTypeSql);

        //查询    地点          1
        String addressListSql = "select address from t_b_address_info where is_delete='0'";
        List<String> addressList = systemService.findListbySql(addressListSql);

        //查询    风险等级          2
        String riskLevelSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='factors_level')";
        List<String> riskLevelList = systemService.findListbySql(riskLevelSql);

        //查询    信息来源          3
        String identificationTypeSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='month_risk_source')";
        List<String> identificationTypeList = systemService.findListbySql(identificationTypeSql);

        //查询    季度         4
        String quarterSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='quarter')";
        List<String> quarterList = systemService.findListbySql(quarterSql);


        List<MonthRiskExportDicVO> dicVOList = new ArrayList<MonthRiskExportDicVO>();

        int[] listLength = {riskTypeList.size(),addressList.size(),riskLevelList.size(),identificationTypeList.size(),quarterList.size()};
        int maxLength = listLength[0];
        for (int i = 0; i < listLength.length; i++) {   //开始循环一维数组
            if (listLength[i] > maxLength) {  //循环判断数组元素
                maxLength = listLength[i];
            }  //赋值给num，然后再次循环
        }
        for (int j = 0; j < maxLength; j++) {
            MonthRiskExportDicVO vo = new MonthRiskExportDicVO();
            if (j < riskTypeList.size()) {
                vo.setRiskType(riskTypeList.get(j));
            }
            if (j < addressList.size()) {
                vo.setAddress(addressList.get(j));
            }
            if (j < riskLevelList.size()) {
                vo.setRiskLevel(riskLevelList.get(j));
            }
            if (j < identificationTypeList.size()) {
                vo.setInfoSources(identificationTypeList.get(j));
            }
            if (j < quarterList.size()) {
                vo.setQuarter(quarterList.get(j));
            }
            dicVOList.add(vo);
        }
        //将字典赋值到map中，写到sheet2中
        map.put("dicVoList", dicVOList);
        templateExportParams.setSheetNum(1);
        templateExportParams.setScanAllsheet(true);
        modelMap.put(TemplateExcelConstants.PARAMS, templateExportParams);

        modelMap.put(TemplateExcelConstants.MAP_DATA, map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    @RequestMapping(params = "exportXls")
    public String exportXls(MonthRiskIdentificationEntity MonthRiskIdentificationEntity, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) throws ParseException {
        String addressId = request.getParameter("addressId");
        String riskType = request.getParameter("riskType");
        String riskLevel = request.getParameter("riskLevel");
        String identificationType = request.getParameter("identificationType");
        String yeRecognizeTimeTemp = request.getParameter("yeRecognizeTimeTemp");
        String type=  request.getParameter("type");
        String quarter=  request.getParameter("quarter");


        CriteriaQuery cq = new CriteriaQuery(MonthRiskIdentificationEntity.class, dataGrid);
        cq.eq("isDel","0");
        if(StringUtils.isNotEmpty(addressId)){
            cq.eq("address",addressId);
        }
        if(StringUtils.isNotEmpty(type)){
            cq.eq("type",type);
        }
        if(StringUtils.isNotEmpty(riskType)){
            cq.eq("riskType",riskType);
        }
        if(StringUtils.isNotEmpty(riskLevel)){
            cq.eq("riskLevel",riskLevel);
        }
        if(StringUtils.isNotEmpty(identificationType)){
            cq.eq("identificationType",identificationType);
        }
        if(StringUtils.isNotEmpty(yeRecognizeTimeTemp)){
            cq.eq("month",yeRecognizeTimeTemp);
        }
        if(StringUtils.isNotEmpty(quarter)){
            cq.eq("quarter",quarter);
        }
        Map orderMap=new HashMap();
        orderMap.put("month", SortDirection.desc);
        cq.setOrder(orderMap);
        cq.add();
        this.systemService.getDataGridReturn(cq, false);
        if (dataGrid != null && dataGrid.getResults() != null) {
            if (dataGrid.getResults().size() > 0) {
                List<MonthRiskIdentificationEntity> list = dataGrid.getResults();
                for (MonthRiskIdentificationEntity t : list) {
                    t.setRiskTypeTemp(DicUtil.getTypeNameByCode("risk_type",t.getRiskType()));
                    t.setRiskLevelTemp(DicUtil.getTypeNameByCode("factors_level",t.getRiskLevel()));
                    if(type.equals("quarter")){
                        t.setQuarterTemp(DicUtil.getTypeNameByCode("quarter",t.getQuarter()));
                    }
                    t.setIdentificationTypeTemp(DicUtil.getTypeNameByCode("month_risk_source",t.getIdentificationType()));
                    /**地点*/
                    if(StringUtils.isNotEmpty(t.getAddress())){
                        TBAddressInfoEntity addressInfoEntity = systemService.getEntity(TBAddressInfoEntity.class, t.getAddress());
                        if(addressInfoEntity!=null){
                            t.setAddressNameTemp(addressInfoEntity.getAddress());
                        }
                    }
                }
            }
        }
        TemplateExportParams templateExportParams = new TemplateExportParams();
        if(type.equals("quarter")){
            modelMap.put(NormalExcelConstants.FILE_NAME, "季度风险管控清单");
            templateExportParams.setTemplateUrl("export/template/exportTemp_quarterRisk.xls");
        }else{
            modelMap.put(NormalExcelConstants.FILE_NAME, "月度风险管控清单");
            templateExportParams.setTemplateUrl("export/template/exportTemp_monthRisk.xls");
        }

        modelMap.put(TemplateExcelConstants.PARAMS, templateExportParams);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", dataGrid.getResults());
        modelMap.put(TemplateExcelConstants.MAP_DATA, map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }
}
