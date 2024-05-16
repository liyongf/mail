package com.sdzk.buss.web.dangersource.controller;

import com.hp.hpl.sparta.ParseException;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.excelverify.ActivityManageExcelVerifyHandler;
import com.sdzk.buss.web.dangersource.entity.TbActivityManageEntity;
import com.sdzk.buss.web.dangersource.service.TbActivityManageServiceI;
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
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
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
import javax.validation.Validator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**   
 * @Title: Controller
 * @Description: 作业活动管理
 * @author zhangdaihao
 * @date 2017-09-18 11:09:20
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tbActivityManageController")
public class TbActivityManageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TbActivityManageController.class);

	@Autowired
	private TbActivityManageServiceI tbActivityManageService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	


	/**
	 * 作业活动管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/activitymanage/tbActivityManageList");
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
    public void datagrid(TbActivityManageEntity tbActivityManage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TbActivityManageEntity.class, dataGrid);
        String activityName=tbActivityManage.getActivityName();
        tbActivityManage.setActivityName(null);
        cq.eq("isDelete","0");
        cq.add();
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tbActivityManage, request.getParameterMap());
        try{
            //自定义追加查询条件
            if (StringUtil.isNotEmpty(activityName)) {
                cq.like("activityName", "%"+activityName+"%");
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tbActivityManageService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**检查数据库中逻辑上是否存在**/
    private boolean checkPostExists(String activityName){
        List<TbActivityManageEntity> entitys = systemService.findByProperty(TbActivityManageEntity.class, "activityName",activityName);
        if (entitys != null && entitys.size() > 0) {
            for(TbActivityManageEntity bean : entitys){
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
    /**查重**/
    @RequestMapping(params = "postExists")
    @ResponseBody
    public AjaxJson postExists(HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        String activityName = request.getParameter("activityName");
        if(StringUtils.isNotBlank(activityName)){
            if(checkPostExists(activityName)){
                message = "该作业活动名称已存在，请勿重复添加";
                j.setSuccess(false);
            }else{
                message = "校验通过";
                j.setSuccess(true);
            }
        }else{
            message = "作业活动名称不能为空";
            j.setSuccess(false);
        }
        j.setMsg(message);
        return j;
    }
    /**
	 * 删除作业活动管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TbActivityManageEntity tbActivityManage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		tbActivityManage = systemService.getEntity(TbActivityManageEntity.class, tbActivityManage.getId());
		message = "作业活动删除成功";
		tbActivityManageService.delete(tbActivityManage);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}
    /**
     * 删除作业活动管理  逻辑删除
     *
     * @return
     */
    @RequestMapping(params = "logicdel")
    @ResponseBody
    public AjaxJson logicdel(TbActivityManageEntity tbActivityManage, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        tbActivityManage = systemService.getEntity(TbActivityManageEntity.class, tbActivityManage.getId());
        message = "作业活动删除成功";
        tbActivityManage.setIsDelete("1");
        tbActivityManageService.updateEntitie(tbActivityManage);
        systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
        j.setMsg(message);
        return j;
    }

    private List<Map<String, Object>> initExportList(List<TbActivityManageEntity> activityList) {
        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
        if(activityList!=null&&activityList.size()>0) {
            for (TbActivityManageEntity activity : activityList) {
                String str = activity.getProfessionType();
                String retName = DicUtil.getTypeNameByCode("proCate_gradeControl", str);
                activity.setProfessionType(retName);
            }
            for (TbActivityManageEntity activity : activityList) {
                Map<String, Object> activityMap = new HashMap<>();
                activityMap.put("activityName", activity.getActivityName());
                activityMap.put("professionType",activity.getProfessionType());
                mapList.add(activityMap);
            }
        }
            return mapList;
        }
    /**
     * 综合查询导出
     * @param tbActivityManage
     * @param request
     * @param response
     * @param dataGrid
     * @param modelMap
     * @return
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TbActivityManageEntity tbActivityManage,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TbActivityManageEntity.class, dataGrid);
        try{
            initQueryListCondition(request, cq);
        }catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        cq.addOrder("activityName", SortDirection.desc);
        cq.add();
        List<TbActivityManageEntity> retList = this.tbActivityManageService.getListByCriteriaQuery(cq, false);
        List<Map<String,Object>> activitymaplist = initExportList(retList);
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_activityManage.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", activitymaplist);
        modelMap.put(NormalExcelConstants.FILE_NAME,"作业活动列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }
    private void initQueryListCondition(HttpServletRequest request, CriteriaQuery cq) throws ParseException {
        //自定义追加查询条件
        String tempActivityName = request.getParameter("activityName");
        if(StringUtils.isNotBlank(tempActivityName)){
            try {
                String activityName = new String(tempActivityName.getBytes("iso8859-1"),"utf-8");
                cq.like("activityName", "%" + activityName + "%");
            }catch(Exception es){};
        }
        String professionType = request.getParameter("professionType");
        if(StringUtils.isNotBlank(professionType)){
            cq.eq("professionType",professionType);
        }
        cq.eq("isDelete","0");
    }
    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TbActivityManageEntity tbActivityManage,HttpServletRequest request,HttpServletResponse response
            , DataGrid dataGrid,ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME,"作业活动管理导入模板");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/importTemp_activityManage.xls");
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
        req.setAttribute("controller_name","tbActivityManageController");
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
            params.setVerifyHanlder(new ActivityManageExcelVerifyHandler());
            try {
                ExcelImportResult<TbActivityManageEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(), TbActivityManageEntity.class, params);
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
                    List<TbActivityManageEntity> listNew = new ArrayList<>();
                    for (int i = 0; i < result.getList().size(); i++){
                        TbActivityManageEntity tbActivityManage = result.getList().get(i);
                        if(checkPostExists(tbActivityManage.getActivityName())){
                            this.importUpdate(tbActivityManage);
                            systemService.addLog("作业活动列表\"" + tbActivityManage.getActivityName() + "\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
                        }else
                        {
                            tbActivityManage.setIsDelete("0");
                            listNew.add(tbActivityManage);
                            systemService.save(tbActivityManage);
                            systemService.addLog("作业活动列表\"" + tbActivityManage.getActivityName() + "\"导入成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
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
    private void importUpdate(TbActivityManageEntity tbActivityManage) {
            String message = null;
            String importActivityName = tbActivityManage.getActivityName();
            String sql = "select id from t_b_activity_manage where activity_name = '"+importActivityName+"'and is_delete = '0'";
            List<Map<String, Object>> idTemp = systemService.findForJdbc(sql);
            Map<String,Object> objTemp = idTemp.get(0);
            String  id = objTemp.get("id").toString();
            tbActivityManage.setId(id);
            TbActivityManageEntity t = tbActivityManageService.get(TbActivityManageEntity.class, id);
            try {
                MyBeanUtils.copyBeanNotNull2Bean(tbActivityManage, t);
                tbActivityManageService.saveOrUpdate(t);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    /**
	 * 添加作业活动管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(TbActivityManageEntity tbActivityManage, HttpServletRequest request) {
		String message = null;
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(tbActivityManage.getId())) {
			message = "作业活动更新成功";
			TbActivityManageEntity t = tbActivityManageService.get(TbActivityManageEntity.class, tbActivityManage.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(tbActivityManage, t);
				tbActivityManageService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = "作业活动更新失败";
			}
		} else {
			message = "作业活动添加成功";
            tbActivityManage.setIsDelete("0");
			tbActivityManageService.save(tbActivityManage);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 作业活动管理列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TbActivityManageEntity tbActivityManage, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tbActivityManage.getId())) {
			tbActivityManage = tbActivityManageService.getEntity(TbActivityManageEntity.class, tbActivityManage.getId());
			req.setAttribute("tbActivityManagePage", tbActivityManage);
		}
		return new ModelAndView("com/sdzk/buss/web/activitymanage/tbActivityManage");
	}
    /**
     * 查看作业过程关联的风险
     *
     * @return
     */
    @RequestMapping(params = "seeRiskList")
    public ModelAndView seeRiskList(HttpServletRequest request) {
        String activityid=request.getParameter("id");
        request.setAttribute("activityid",activityid);
        request.setAttribute("year", DateUtils.getYear());
        return new ModelAndView("com/sdzk/buss/web/activitymanage/tbActivityRelRiskList");
    }

    /**
     * 作业过程列表页面跳转
     */
    @RequestMapping(params = "chooseInveRiskPoint")
    public ModelAndView chooseInveRiskPoint(HttpServletRequest request) {
        request.setAttribute("ids", ResourceUtil.getParameter("ids"));
        request.setAttribute("month", ResourceUtil.getParameter("month"));
        request.setAttribute("from", ResourceUtil.getParameter("from"));
        return new ModelAndView("com/sdzk/buss/web/dangersource/chooseInveRiskPointList");
    }


    /**
     * 作业过程列表页面 加载数据
     */
    @RequestMapping(params = "chooseInveRiskPointDatagrid")
    public void chooseInveRiskPointDatagrid(TbActivityManageEntity tbActivityManageEntity, HttpServletRequest request,
                                            HttpServletResponse response, DataGrid dataGrid) {
        String name = tbActivityManageEntity.getActivityName();
        tbActivityManageEntity.setActivityName(null);
        CriteriaQuery cq = new CriteriaQuery(TbActivityManageEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq,
                tbActivityManageEntity);
        String ids = ResourceUtil.getParameter("ids");
        if (StringUtil.isNotEmpty(ids)) {
            cq.notIn("id", ids.split(","));
        }
        if (StringUtil.isNotEmpty(name)) {
            cq.like("activityName", "%"+name+"%");
        }
        cq.eq("isDelete","0");
        /** 从月度计划中选择 **/
        if ("plan".equals(ResourceUtil.getParameter("from"))) {
            String month = ResourceUtil.getParameter("month");
            if (StringUtil.isNotEmpty(month)) {
                month = month.substring(0, 7)+"-01";
                cq.add(Restrictions.sqlRestriction("id in (select obj_id from t_b_investigate_plan_rel rel join t_b_investigate_plan plan on rel.plan_id=plan.id where rel.rel_type='"+Constants.INVESTIGATEPLAN_REL_TYPE_RISKPOINT+"' and rel.poit_type='"+Constants.INVESTIGATEPLAN_RISKPOINT_TYPE_WORK+"' and plan.start_time='"+month+"')"));
            } else {
                cq.add(Restrictions.sqlRestriction("id in (select obj_id from t_b_investigate_plan_rel where rel_type='"+Constants.INVESTIGATEPLAN_REL_TYPE_RISKPOINT+"' and poit_type='"+Constants.INVESTIGATEPLAN_RISKPOINT_TYPE_WORK+"')"));
            }
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        List<TbActivityManageEntity> tempList = dataGrid.getResults();
        if(tempList != null && !tempList.isEmpty()){
            for(TbActivityManageEntity relBean : tempList){
                Map<String,Object> count = systemService.findOneForJdbc("select count(1) total from t_b_danger_source where YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+") and is_delete=0 and AUDIT_STATUS="+Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE+" and activity_id ='"+relBean.getId()+"'");
                relBean.setCount(String.valueOf(count.get("total")));
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }
}
