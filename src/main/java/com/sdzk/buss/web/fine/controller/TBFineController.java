package com.sdzk.buss.web.fine.controller;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.excelverify.FineListExcelVerifyHandler;
import com.sdzk.buss.web.dangersource.entity.TBDangerAddresstRelEntity;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.fine.entity.TBFineEntity;
import com.sdzk.buss.web.fine.service.TBFineServiceI;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleStepEntity;
import com.sdzk.buss.web.quartz.QuartzJob;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
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
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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

/**   
 * @Title: Controller  
 * @Description: 重大隐患
 * @author onlineGenerator
 * @date 2017-09-26 10:21:57
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tBFineController")
public class TBFineController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TBFineController.class);

	@Autowired
	private TBFineServiceI tbFineService;
    @Autowired
    private UserService userService;

	@Autowired
	private SystemService systemService;

    /**
     * 自动生成单号
     * */
    private String generateNum(){
        String strNum = "";
        Date strDate = DateUtils.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");

        List<TBFineEntity> tbFineEntityList = systemService.getList(TBFineEntity.class);
        strNum = strNum + tbFineEntityList.size()%1000;

        while(strNum.length() < 4){
            strNum = "0" + strNum;
        }
        String temp = DateUtils.date2Str(strDate,sdf);
        temp = temp.substring(2, temp.length());

        return temp + strNum;
    }


	/**
	 * 罚款列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
        String xiezhuang = ResourceUtil.getConfigByName("xiezhuang");
        request.setAttribute("xiezhuang",xiezhuang);
		return new ModelAndView("com/sdzk/buss/web/fine/tBFineList");
	}


    @RequestMapping(params = "chooseVioList")
    public ModelAndView chooseVioList(HttpServletRequest request) {
        request.setAttribute("busId",ResourceUtil.getParameter("busId"));

        return new ModelAndView("com/sdzk/buss/web/fine/chooseVioList");
    }


    @RequestMapping(params = "goVioRelFineList")
    public ModelAndView goVioRelFineList(HttpServletRequest request) {
        request.setAttribute("hiddenId",ResourceUtil.getParameter("hiddenId"));
        return new ModelAndView("com/sdzk/buss/web/fine/goVioRelFineList");
    }

    /**
     * 数据组装
     * datagrid
     * */
    @RequestMapping(params = "datagrid")
    public void datagrid(TBFineEntity tbFineEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBFineEntity.class, dataGrid);

        //清空查询数据
        tbFineEntity.setFineDate(null);
        tbFineEntity.setBeFinedMan(null);
        tbFineEntity.setFineMan(null);
        tbFineEntity.setCreateDate(null);
        tbFineEntity.setFineProperty(null);

        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tbFineEntity, request.getParameterMap());

        try{
            //根据日期筛选
            String fineDateBegin = request.getParameter("fineDate_begin");
            String fineDateEnd = request.getParameter("fineDate_end");
            if(StringUtils.isNotBlank(fineDateBegin)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.ge("fineDate",sdf.parse(fineDateBegin));
            }
            if(StringUtils.isNotBlank(fineDateEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.le("fineDate",sdf.parse(fineDateEnd));
            }

            //根据责任单位筛选
            String depart = request.getParameter("dutyUnit.departname");
            if(StringUtil.isNotEmpty(depart)){
                cq.like("dutyUnit.departname", "%"+depart+"%");
            }

            //根据被罚款人筛选
            String beFinedMan = request.getParameter("beFinedMan");
            if(StringUtil.isNotEmpty(beFinedMan)){
                cq.like("beFinedMan", "%"+beFinedMan+"%");
            }

            //根据罚款人筛选
            String fineMan = request.getParameter("fineMan");
            if(StringUtil.isNotEmpty(fineMan)){
                cq.like("fineMan", "%"+fineMan+"%");
            }

            //根据创建时间筛选
            String createDateBegin = request.getParameter("createDate_begin");
            String createDateEnd = request.getParameter("createDate_end");
            if(StringUtils.isNotBlank(createDateBegin)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.ge("createDate",sdf.parse(createDateBegin));
            }
            if(StringUtils.isNotBlank(createDateEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.le("createDate",sdf.parse(createDateEnd));
            }

            //根据罚款性质筛选
            String fineProperty = request.getParameter("fineProperty");
            if(StringUtil.isNotEmpty(fineProperty)){
                cq.eq("fineProperty", fineProperty);
            }

            cq.eq("isValidity", "0");
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tbFineService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBFineEntity tbFineEntity, HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBFineEntity.class, dataGrid);

        //清空查询数据
        tbFineEntity.setFineDate(null);
        tbFineEntity.setBeFinedMan(null);
        tbFineEntity.setFineMan(null);
        tbFineEntity.setCreateDate(null);
        tbFineEntity.setFineProperty(null);

        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tbFineEntity, request.getParameterMap());

        try{

            //根据日期筛选
            String fineDateBegin = request.getParameter("fineDate_begin");
            String fineDateEnd = request.getParameter("fineDate_end");
            if(StringUtils.isNotBlank(fineDateBegin)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.ge("fineDate",sdf.parse(fineDateBegin));
            }
            if(StringUtils.isNotBlank(fineDateEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.le("fineDate",sdf.parse(fineDateEnd));
            }

            //根据责任单位筛选
            String depart = request.getParameter("dutyUnit.departname");
            if(StringUtil.isNotEmpty(depart)){
                cq.like("dutyUnit.departname", "%"+depart+"%");
            }

            //根据被罚款人筛选
            String beFinedMan = request.getParameter("beFinedMan");
            if(StringUtil.isNotEmpty(beFinedMan)){
                cq.like("beFinedMan", "%"+beFinedMan+"%");
            }

            //根据罚款人筛选
            String fineMan = request.getParameter("fineMan");
            if(StringUtil.isNotEmpty(fineMan)){
                cq.like("fineMan", "%"+fineMan+"%");
            }

            //根据创建时间筛选
            String createDateBegin = request.getParameter("createDate_begin");
            String createDateEnd = request.getParameter("createDate_end");
            if(StringUtils.isNotBlank(createDateBegin)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.ge("createDate",sdf.parse(createDateBegin));
            }
            if(StringUtils.isNotBlank(createDateEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.le("createDate",sdf.parse(createDateEnd));
            }

            //根据罚款性质筛选
            String fineProperty = request.getParameter("fineProperty");
            if(StringUtil.isNotEmpty(fineProperty)){
                cq.eq("fineProperty", fineProperty);
            }

            cq.eq("isValidity", "0");

        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        List<TBFineEntity> fineList = this.tbFineService.getListByCriteriaQuery(cq, false);
        if(fineList != null && !fineList.isEmpty()){
            for(TBFineEntity bean : fineList){
                //罚款性质转换
                if(StringUtil.isNotEmpty(bean.getFineProperty())){
                    if(StringUtil.isNotEmpty(DicUtil.getTypeNameByCode("fineProperty", bean.getFineProperty()))){
                        bean.setFinePropertyTemp(DicUtil.getTypeNameByCode("fineProperty", bean.getFineProperty()));
                    }
                }
                //罚款时间格式化
                if(StringUtil.isNotEmpty(bean.getFineDate())){
                    bean.setFineDateTemp(DateUtils.date2Str(bean.getFineDate(), DateUtils.date_sdf));
                }
                //录入时间格式化
                if(StringUtil.isNotEmpty(bean.getCreateDate())){
                    bean.setCreateDateTemp(DateUtils.date2Str(bean.getCreateDate(), DateUtils.date_sdf));
                }
            }
        }
        TemplateExportParams templateExportParams = new TemplateExportParams();

        templateExportParams.setSheetName("罚款导出列表");
        templateExportParams.setSheetNum(0);
        String xiezhuang = ResourceUtil.getConfigByName("xiezhuang");
        if(xiezhuang.equals("true")){
            templateExportParams.setTemplateUrl("export/template/exportTemp_FineListXZ.xls");
        }else{
            templateExportParams.setTemplateUrl("export/template/exportTemp_FineList.xls");
        }


        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", fineList);

        modelMap.put(NormalExcelConstants.FILE_NAME,"罚款详情导出列表");

        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name","tBFineController");
        req.setAttribute("function_name", "importExcelT");

        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 根据模板导入部门风险或专项风险
     * @param request
     * @param response
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcelT", method = RequestMethod.POST)
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
            params.setVerifyHanlder(new FineListExcelVerifyHandler());
            try {
                ExcelImportResult<TBFineEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(),TBFineEntity.class,params);
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
                    for (int i = 0; i < result.getList().size(); i++){
                        TBFineEntity tbFineEntity = result.getList().get(i);
                        String xiezhuang = ResourceUtil.getConfigByName("xiezhuang");
                        if(xiezhuang.equals("true")){
                            tbFineEntity.setFineMoney(tbFineEntity.getFineMoneyTemp());
                        }
                        systemService.save(tbFineEntity);
                        systemService.addLog("罚款信息\""+tbFineEntity.getFineNum()+"\"导入成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
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

    /**
     * 导出excel 模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBFineEntity tbFineEntity,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME,"罚款信息导入模板");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        String xiezhuang = ResourceUtil.getConfigByName("xiezhuang");
        request.setAttribute("xiezhuang",xiezhuang);
        if(xiezhuang.equals("true")){
            templateExportParams.setTemplateUrl("export/template/importTemp_FineListXZ.xls");
        }else{
            templateExportParams.setTemplateUrl("export/template/importTemp_FineList.xls");
        }

        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String, Object> param =new HashMap<>();
        modelMap.put(TemplateExcelConstants.MAP_DATA,param);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }


    /**
     * 添加年度危险源
     * @param tbFineEntity
     * @param req
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBFineEntity tbFineEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tbFineEntity.getId())) {
            tbFineEntity = tbFineService.getEntity(TBFineEntity.class, tbFineEntity.getId());
        }
        tbFineEntity.setFineNum(generateNum());
        tbFineEntity.setIsValidity("0");
        req.setAttribute("tBFinePage", tbFineEntity);

        String hiddenId = req.getParameter("hiddenId");
        String vioId = req.getParameter("vioId");
        req.setAttribute("vioId",vioId);
        req.setAttribute("hiddenId",hiddenId);
        String xiezhuang = ResourceUtil.getConfigByName("xiezhuang");
        req.setAttribute("xiezhuang",xiezhuang);


        return new ModelAndView("com/sdzk/buss/web/fine/tBFine-add");
    }
    @RequestMapping(params = "chooseFineList")
    public ModelAndView chooseFineList(HttpServletRequest request) {
        String xiezhuang = ResourceUtil.getConfigByName("xiezhuang");
        request.setAttribute("xiezhuang",xiezhuang);

        request.setAttribute("busId",ResourceUtil.getParameter("busId"));

        return new ModelAndView("com/sdzk/buss/web/fine/chooseFineList");
    }
    @RequestMapping(params = "doBatchDelAddressRel")
    @ResponseBody
    public AjaxJson doBatchDelAddressRel(TBFineEntity tbFineEntity, HttpServletRequest request) {
        String message = "刪除成功";
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        String hiddenId = request.getParameter("hiddenId");
        try{
            if(StringUtils.isNotBlank(ids)){
                String idArray[] = ids.split(",");
                for(String str : idArray){
                    String sql = "delete from t_b_hidden_vio_fine_rel  where fine_id = '"+str+"' and hidden_id = '"+hiddenId+"'";
                    systemService.executeSql(sql);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            message = "罚款信息添加失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "chooseFineDataGrid")
    public void chooseFineDataGrid(TBFineEntity tbFineEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String hiddenId = request.getParameter("busId");
        String beFinedMan = request.getParameter("beFinedMan");
        String fineMan = request.getParameter("fineMan");

        CriteriaQuery cq = new CriteriaQuery(TBFineEntity.class, dataGrid);
        try{
            cq.add(Restrictions.sqlRestriction(" this_.id in (select fine_id from t_b_hidden_vio_fine_rel where hidden_id = '"+hiddenId+"')"));
            if(StringUtils.isNotBlank(beFinedMan)) {
                cq.like("beFinedMan", "%"+beFinedMan+"%");
            }
            if(StringUtils.isNotBlank(fineMan)) {
                cq.like("fineMan", "%"+fineMan+"%");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.tbFineService.getDataGridReturn(cq, true);

        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "chooseVioFineDataGrid")
    public void chooseVioFineDataGrid(TBFineEntity tbFineEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String vioId = request.getParameter("busId");
        String beFinedMan = request.getParameter("beFinedMan");
        String fineMan = request.getParameter("fineMan");
        CriteriaQuery cq = new CriteriaQuery(TBFineEntity.class, dataGrid);
        try{
            cq.add(Restrictions.sqlRestriction(" this_.id in (select fine_id from t_b_hidden_vio_fine_rel where vio_id = '"+vioId+"')"));
            if(StringUtils.isNotBlank(beFinedMan)) {
                cq.like("beFinedMan", "%"+beFinedMan+"%");
            }
            if(StringUtils.isNotBlank(fineMan)) {
                cq.like("fineMan", "%"+fineMan+"%");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.tbFineService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "chooseVioFineList")
    public ModelAndView chooseVioFineList(HttpServletRequest request) {
        request.setAttribute("busId",ResourceUtil.getParameter("busId"));

        return new ModelAndView("com/sdzk/buss/web/fine/chooseVioFineList");
    }
    @RequestMapping(params = "doBatchDelVioRel")
    @ResponseBody
    public AjaxJson doBatchDelVioRel(TBFineEntity tbFineEntity, HttpServletRequest request) {
        String message = "刪除成功";
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        String hiddenId = request.getParameter("hiddenId");
        try{
            if(StringUtils.isNotBlank(ids)){
                String idArray[] = ids.split(",");
                for(String str : idArray){
                    String sql = "delete from t_b_hidden_vio_fine_rel  where fine_id = '"+str+"' and vio_id = '"+hiddenId+"'";
                    systemService.executeSql(sql);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            message = "罚款信息添加失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goRelHiddenFineList")
    public ModelAndView goRelHiddenFineList(HttpServletRequest request) {
        request.setAttribute("hiddenId",ResourceUtil.getParameter("hiddenId"));
        return new ModelAndView("com/sdzk/buss/web/fine/goRelHiddenFineList");
    }

    @RequestMapping(params = "goRelVioFineList")
    public ModelAndView goRelVioFineList(HttpServletRequest request) {
        request.setAttribute("hiddenId",ResourceUtil.getParameter("hiddenId"));
        return new ModelAndView("com/sdzk/buss/web/fine/goRelVioFineList");
    }
    @RequestMapping(params = "doBatchDelfineHiddenRel")
    @ResponseBody
    public AjaxJson doBatchDelfineHiddenRel(TBFineEntity tbFineEntity, HttpServletRequest request) {
        String message = "刪除成功";
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        String fineId = request.getParameter("fineId");
        try{
            if(StringUtils.isNotBlank(ids)){
                String idArray[] = ids.split(",");
                for(String str : idArray){
                    String sql = "delete from t_b_hidden_vio_fine_rel  where hidden_id = '"+str+"' and fine_id = '"+fineId+"'";
                    systemService.executeSql(sql);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            message = "罚款信息添加失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "chooseFineHiddenDataGrid")
    public void chooseFineHiddenDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String fineId = request.getParameter("busId");
        String examDate_begin = request.getParameter("examDate_begin");
        String examDate_end = request.getParameter("examDate_end");
        String shift = request.getParameter("shift");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerExamEntity.class, dataGrid);
        try{

            if(StringUtils.isNotBlank(examDate_begin)){
                cq.ge("examDate",sdf.parse(examDate_begin));
            }
            if(StringUtils.isNotBlank(examDate_end)){
                cq.le("examDate",sdf.parse(examDate_end));
            }
            if(StringUtils.isNotBlank(shift)){
                cq.eq("shift",shift);
            }
            cq.add(Restrictions.sqlRestriction(" this_.id in (select hidden_id from t_b_hidden_vio_fine_rel where fine_id = '"+fineId+"')"));
            cq.createAlias("address","address");
        //    cq.eq("address.isShowData","1");
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.tbFineService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "chooseHiddenList")
    public ModelAndView chooseHiddenList(HttpServletRequest request) {
        request.setAttribute("busId",ResourceUtil.getParameter("busId"));

        return new ModelAndView("com/sdzk/buss/web/fine/chooseHiddenList");
    }




    @RequestMapping(params = "chooseFineVioDataGrid")
    public void chooseFineVioDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String fineId = request.getParameter("busId");
        String vioDate_begin = request.getParameter("vioDate_begin");
        String vioDate_end = request.getParameter("vioDate_end");
        String shift = request.getParameter("shift");
        String vioCategory = request.getParameter("vioCategory");
        String vioLevel = request.getParameter("vioLevel");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        CriteriaQuery cq = new CriteriaQuery(TBThreeViolationsEntity.class, dataGrid);
        try{
            if(StringUtils.isNotBlank(vioDate_begin)){
                cq.ge("vioDate",sdf.parse(vioDate_begin));
            }
            if(StringUtils.isNotBlank(vioDate_end)){
                cq.le("vioDate",sdf.parse(vioDate_end));
            }
            if(StringUtils.isNotBlank(shift)){
                cq.eq("shift",shift);
            }
            if(StringUtils.isNotBlank(vioCategory)){
                cq.eq("vioCategory",vioCategory);
            }
            if(StringUtils.isNotBlank(vioLevel)){
                cq.eq("vioLevel",vioLevel);
            }
            cq.add(Restrictions.sqlRestriction(" this_.id in (select vio_id from t_b_hidden_vio_fine_rel where fine_id = '"+fineId+"')"));
        //    cq.add(Restrictions.sqlRestriction(" this_.VIO_ADDRESS not in (select id from t_b_address_info where  isShowData= '0' )"));
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.tbFineService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
    @RequestMapping(params = "doBatchDelfineVioRel")
    @ResponseBody
    public AjaxJson doBatchDelfineVioRel(TBFineEntity tbFineEntity, HttpServletRequest request) {
        String message = "刪除成功";
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");
        String fineId = request.getParameter("fineId");
        try{
            if(StringUtils.isNotBlank(ids)){
                String idArray[] = ids.split(",");
                for(String str : idArray){
                    String sql = "delete from t_b_hidden_vio_fine_rel  where vio_id = '"+str+"' and fine_id = '"+fineId+"'";
                    systemService.executeSql(sql);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            message = "罚款信息添加失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "saveVioRelChooseFine")
    @ResponseBody
    public AjaxJson saveVioRelChooseFine(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        String ids = request.getParameter("ids");//选择的罰款
        String hiddenId = ResourceUtil.getParameter("hiddenId");
        try{

            if(StringUtil.isNotEmpty(ids)){

                String[] idArray = ids.split(",");
                TBDangerAddresstRelEntity entity= null;
                for(String id : idArray){
                    String key = UUIDGenerator.generate();
                    String sql = "insert into t_b_hidden_vio_fine_rel (id,vio_id,fine_id) values('"+key+"','"+hiddenId+"','"+id+"')";
                    systemService.executeSql(sql);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "添加失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "saveRelChooseVioFine")
    @ResponseBody
    public AjaxJson saveRelChooseVioFine(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        String ids = request.getParameter("ids");//选择的罰款
        String fineId = ResourceUtil.getParameter("hiddenId");
        try{

            if(StringUtil.isNotEmpty(ids)){

                String[] idArray = ids.split(",");
                TBDangerAddresstRelEntity entity= null;
                for(String id : idArray){
                    String key = UUIDGenerator.generate();
                    String sql = "insert into t_b_hidden_vio_fine_rel (id,fine_id,vio_id) values('"+key+"','"+fineId+"','"+id+"')";
                    systemService.executeSql(sql);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "saveRelChooseHiddenFine")
    @ResponseBody
    public AjaxJson saveRelChooseHiddenFine(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        String ids = request.getParameter("ids");//选择的罰款
        String fineId = ResourceUtil.getParameter("hiddenId");
        try{

            if(StringUtil.isNotEmpty(ids)){

                String[] idArray = ids.split(",");
                TBDangerAddresstRelEntity entity= null;
                for(String id : idArray){
                    String key = UUIDGenerator.generate();
                    String sql = "insert into t_b_hidden_vio_fine_rel (id,fine_id,hidden_id) values('"+key+"','"+fineId+"','"+id+"')";
                    systemService.executeSql(sql);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "saveRelChooseFine")
    @ResponseBody
    public AjaxJson saveRelChooseFine(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        String ids = request.getParameter("ids");//选择的罰款
        String hiddenId = ResourceUtil.getParameter("hiddenId");
        try{

            if(StringUtil.isNotEmpty(ids)){

                String[] idArray = ids.split(",");
                TBDangerAddresstRelEntity entity= null;
                for(String id : idArray){
                    String key = UUIDGenerator.generate();
                    String sql = "insert into t_b_hidden_vio_fine_rel (id,hidden_id,fine_id) values('"+key+"','"+hiddenId+"','"+id+"')";
                    systemService.executeSql(sql);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "添加失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "fineVioDatagrid")
    public void fineVioDatagrid(TBFineEntity tbFineEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String hiddenId = request.getParameter("hiddenId");
//        String sql = "select fine_id from t_b_hidden_vio_fine_rel where hidden_id = '"+hiddenId+"'";
//        List<String> fineIdList = systemService.findListbySql(sql);
//        if(fineIdList != null && !fineIdList.isEmpty()){
        CriteriaQuery cq = new CriteriaQuery(TBFineEntity.class, dataGrid);
        try{
//                cq.notIn("id",fineIdList.toArray());
            cq.add(Restrictions.sqlRestriction(" this_.id not in (select fine_id from t_b_hidden_vio_fine_rel where vio_id = '"+hiddenId+"' )"));

        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.tbFineService.getDataGridReturn(cq, true);
//        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "fineVioFineDatagrid")
    public void fineVioFineDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String fineId = request.getParameter("hiddenId");
//        String sql = "select fine_id from t_b_hidden_vio_fine_rel where hidden_id = '"+hiddenId+"'";
//        List<String> fineIdList = systemService.findListbySql(sql);
//        if(fineIdList != null && !fineIdList.isEmpty()){
        String vioDate_begin = request.getParameter("vioDate_begin");
        String vioDate_end = request.getParameter("vioDate_end");
        String shift = request.getParameter("shift");
        String vioCategory = request.getParameter("vioCategory");
        String vioLevel = request.getParameter("vioLevel");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        CriteriaQuery cq = new CriteriaQuery(TBThreeViolationsEntity.class, dataGrid);
        try{
            if(StringUtils.isNotBlank(vioDate_begin)){
                cq.ge("vioDate",sdf.parse(vioDate_begin));
            }
            if(StringUtils.isNotBlank(vioDate_end)){
                cq.le("vioDate",sdf.parse(vioDate_end));
            }
            if(StringUtils.isNotBlank(shift)){
                cq.eq("shift",shift);
            }
            if(StringUtils.isNotBlank(vioCategory)){
                cq.eq("vioCategory",vioCategory);
            }
            if(StringUtils.isNotBlank(vioLevel)){
                cq.eq("vioLevel",vioLevel);
            }
//                cq.notIn("id",fineIdList.toArray());

            cq.add(Restrictions.sqlRestriction(" this_.id not in (select vio_id from t_b_hidden_vio_fine_rel where  fine_id= '"+fineId+"' and vio_id is not null)"));
        //    cq.add(Restrictions.sqlRestriction(" this_.VIO_ADDRESS not in (select id from t_b_address_info where  isShowData= '0' )"));

        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.tbFineService.getDataGridReturn(cq, true);
//        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "fineHiddenDatagrid")
    public void fineHiddenDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String fineId = request.getParameter("hiddenId");
//        String sql = "select fine_id from t_b_hidden_vio_fine_rel where hidden_id = '"+hiddenId+"'";
//        List<String> fineIdList = systemService.findListbySql(sql);
//        if(fineIdList != null && !fineIdList.isEmpty()){
        String examDate_begin = request.getParameter("examDate_begin");
        String examDate_end = request.getParameter("examDate_end");
        String shift = request.getParameter("shift");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerExamEntity.class, dataGrid);
        try{
            if(StringUtils.isNotBlank(examDate_begin)){
                cq.ge("examDate",sdf.parse(examDate_begin));
            }
            if(StringUtils.isNotBlank(examDate_end)){
                cq.le("examDate",sdf.parse(examDate_end));
            }
            if(StringUtils.isNotBlank(shift)){
                cq.eq("shift",shift);
            }
//                cq.notIn("id",fineIdList.toArray());
            cq.createAlias("address","address");
        //    cq.eq("address.isShowData","1");
            cq.add(Restrictions.sqlRestriction(" this_.id not in (select hidden_id from t_b_hidden_vio_fine_rel where  fine_id= '"+fineId+"' and hidden_id is not null)"));

        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        this.tbFineService.getDataGridReturn(cq, true);
//        }
        TagUtil.datagrid(response, dataGrid);
    }
    /**
     * 添加隐患检查
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBFineEntity tbFineEntity, HttpServletRequest request) {
        String message = "添加罚款成功";
        AjaxJson j = new AjaxJson();
        if(StringUtil.isEmpty(tbFineEntity.getFineNum())){
            tbFineEntity.setFineNum(generateNum());
        }

        try{
            tbFineEntity.setIsValidity("0");
            tbFineService.save(tbFineEntity);
            String hiddenId = request.getParameter("hiddenId");
            if(StringUtils.isNotBlank(hiddenId)){
                String key = UUIDGenerator.generate();
                String sql = "insert into t_b_hidden_vio_fine_rel (id,fine_id,hidden_id) values('"+key+"','"+tbFineEntity.getId()+"','"+hiddenId+"')";
                systemService.executeSql(sql);
            }

            String vioId = request.getParameter("vioId");
            if(StringUtils.isNotBlank(vioId)){
                String key = UUIDGenerator.generate();
                String sql = "insert into t_b_hidden_vio_fine_rel (id,fine_id,vio_id) values('"+key+"','"+tbFineEntity.getId()+"','"+vioId+"')";
                systemService.executeSql(sql);
            }
            systemService.addLog("罚款信息\""+tbFineEntity.getId()+"\"添加成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);

        }catch(Exception e){
            e.printStackTrace();
            message = "罚款信息添加失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 罚款编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBFineEntity tbFineEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tbFineEntity.getId())) {
            tbFineEntity = tbFineService.getEntity(TBFineEntity.class, tbFineEntity.getId());
            req.setAttribute("tbFinePage", tbFineEntity);
            String fineMans = "";
            String fineMan = tbFineEntity.getFineMan();
            if(StringUtils.isNotBlank(fineMan)){
                String[] fineManArry = fineMan.split(",");



                for(String fineManName : fineManArry){
                    if(fineMans == ""){
                        fineMans = fineMans + '"' + fineManName + '"';
                    }else{
                        fineMans = fineMans + ',' + '"'+ fineManName + '"';
                    }
                }
                req.setAttribute("fineMans",fineMans);
            }

        }
        String xiezhuang = ResourceUtil.getConfigByName("xiezhuang");
        req.setAttribute("xiezhuang",xiezhuang);
        return new ModelAndView("com/sdzk/buss/web/fine/tBFine-update");
    }


    /**
     * 罚款编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "godetail")
    public ModelAndView godetail(TBFineEntity tbFineEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tbFineEntity.getId())) {
            tbFineEntity = tbFineService.getEntity(TBFineEntity.class, tbFineEntity.getId());
            req.setAttribute("tbFinePage", tbFineEntity);
        }
        return new ModelAndView("com/sdzk/buss/web/fine/tBFine-detail");
    }


    /**
     * 罚款编辑处理
     * */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBFineEntity tbFineEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "罚款信息更新成功";
//        TBFineEntity t = tbFineService.get(TBFineEntity.class, tbFineEntity.getId());
        try {
            tbFineEntity.setIsValidity("0");
            tbFineService.saveOrUpdate(tbFineEntity);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "罚款信息更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 作废罚款信息
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "cancel")
    @ResponseBody
    public AjaxJson toReportCallback(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "所选罚款信息作废成功";
        try {
            if(StringUtil.isNotEmpty(ids)){
                for(String id : ids.split(",")){
                    TBFineEntity t = tbFineService.get(TBFineEntity.class, id);

                    t.setIsValidity("1");
                    tbFineService.save(t);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "操作失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

 }
