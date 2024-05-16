package com.sdzk.buss.web.hiddendanger.controller;

import com.sddb.buss.riskmanage.entity.RiskManageTaskAllEntity;
import com.sdzk.buss.api.service.WeChartGetToken;
import com.sdzk.buss.api.utils.WebChatUtil;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.excelverify.HiddenDangerHandleExcelVerifyHandler;
import com.sdzk.buss.web.common.taskProvince.TBReportDeleteIdEntity;
import com.sdzk.buss.web.common.utils.SMSSenderUtil;
import com.sdzk.buss.web.hiddendanger.entity.*;
import com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerApplyServiceI;
import com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerExamServiceI;
import com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerHandleLogServiceI;
import com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerHandleServiceI;
import com.sdzk.buss.web.hiddendanger.service.impl.UploadThread;
import com.sdzk.buss.web.investigateplan.entity.TBInvestigatePlanEntity;
import com.sdzk.buss.web.quartz.QuartzJob;
import com.sdzk.buss.web.quartz.service.QrtzManagerServiceI;
import com.sdzk.buss.web.riskUpgrade.service.RiskUpgradeServiceI;
import com.sdzk.buss.web.system.entity.TBSunshineEntity;
import com.sdzk.sys.synctocloud.service.SyncToCloudService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
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
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.quartz.Scheduler;
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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.sdzk.buss.web.common.service.SfService;


/**
 * @Title: Controller
 * @Description: t_b_hidden_danger_handle
 * @author onlineGenerator
 * @date 2016-04-28 13:28:05
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBHiddenDangerHandleController")
public class TBHiddenDangerHandleController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBHiddenDangerHandleController.class);
    @Resource(name="quartzScheduler")
    private Scheduler scheduler;
    @Autowired
    private RiskUpgradeServiceI riskUpgradeService;
    @Autowired
    private QrtzManagerServiceI qrtzManagerServiceI;
    @Autowired
    private SyncToCloudService syncToCloudService;

    @Autowired
    private TBHiddenDangerApplyServiceI tbHiddenDangerApplyService;
    @Autowired
    private TBHiddenDangerHandleServiceI tBHiddenDangerHandleService;
    @Autowired
    private TBHiddenDangerExamServiceI tBHiddenDangerExamService;
    @Autowired
    private SystemService systemService;

    @Autowired
    private WeChartGetToken weChartGetToken;
    @Autowired
    private TBHiddenDangerExamServiceI tbHiddenDangerExamService;
    @Autowired
    private TBHiddenDangerHandleLogServiceI tBHiddenDangerHandleLogService;

    @Autowired
    private SfService sfService;


    /**
     * 综合查询
     * @param request
     * @return
     */
    @RequestMapping(params = "queryList")
    public ModelAndView queryList(HttpServletRequest request) {
        /******************************判断是不是阳光管理员	然后决定是否显示隐藏按钮**************************/
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if(isSunRole){
            request.setAttribute("isSunAdmin", "YGADMIN");
        }else{
            request.setAttribute("isSunAdmin", "common");
        }
        /***************************************************************************************************************/
        //已闭环模板下载导入是否显示
        String sql = "select * from t_s_type where id = '4028834a63dcf1d70163dd0e45060004' and typename='已闭环模板下载导入是否显示' and is_hide='0'";
        List<String> list = systemService.findListbySql(sql);
        if(list.size()>0){
            request.setAttribute("excelIsHide",0);
        }
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = sdf.format(d);
        request.setAttribute("dateNow",dateNow);
        String anju = ResourceUtil.getConfigByName("anju");
        request.setAttribute("anju",anju);
        String homePage = request.getParameter("homePage");
        if(StringUtil.isNotEmpty(homePage)){
            request.setAttribute("homePage",homePage);
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            request.setAttribute("startDate",startDate);
            request.setAttribute("endDate",endDate);
        }
        String xiezhuang = ResourceUtil.getConfigByName("xiezhuang");
        request.setAttribute("xiezhuang",xiezhuang);
        String beixulou = ResourceUtil.getConfigByName("beixulou");
        request.setAttribute("beixulou",beixulou);
        String huayuan = ResourceUtil.getConfigByName("huayuan");
        request.setAttribute("huayuan",huayuan);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandleQueryList");
    }

    /**
     * 超期未整改列表
     * @param request
     * @return
     */
    @RequestMapping(params = "notRectifyList")
    public ModelAndView notRectifyList(HttpServletRequest request) {
        /******************************判断是不是阳光管理员	然后决定是否显示隐藏按钮**************************/
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if(isSunRole){
            request.setAttribute("isSunAdmin", "YGADMIN");
        }else{
            request.setAttribute("isSunAdmin", "common");
        }
        /***************************************************************************************************************/

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = sdf.format(d);
        request.setAttribute("dateNow",dateNow);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandleNotRectifyList");
    }

    /**
     * 本矿隐患上报
     * @param request
     * @return
     */
    @RequestMapping(params = "hiddenDangerReportList")
    public ModelAndView hiddenDangerReportList(HttpServletRequest request) {
        /******************************判断是不是阳光管理员	然后决定是否显示隐藏按钮**************************/
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if(isSunRole){
            request.setAttribute("isSunAdmin", "YGADMIN");
        }else{
            request.setAttribute("isSunAdmin", "common");
        }
        /***************************************************************************************************************/

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = sdf.format(d);
        request.setAttribute("dateNow",dateNow);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandle-reportList");
    }


    @RequestMapping(params = "reportGroup")
    @ResponseBody
    public AjaxJson reportGroup(HttpServletRequest request) {
        String ids = request.getParameter("ids");
        AjaxJson j = new AjaxJson();
        j.setMsg("上报成功");
        if (StringUtil.isNotEmpty(ids)) {
            j = tBHiddenDangerHandleService.hiddenDangerReportToGroup(ids);
        } else {
            j.setMsg("请选择要上报的数据");
        }
        return j;
    }

    private String getNameById(String table,String field, String id) {
        List<String> name = systemService.findListbySql("select "+field+" from "+table+" where id = '"+id+"'");
        if (name != null && name.size() > 0) {
            return name.get(0);
        }
        return null;
    }
    /**
     * 选择关联隐患
     * @param request
     * @return
     */
    @RequestMapping(params = "chooseHiddenDanger")
    public ModelAndView chooseHiddenDanger(HttpServletRequest request) {
        String planId = request.getParameter("planId");
        if(StringUtils.isNotBlank(planId)){
            TBInvestigatePlanEntity investigatePlan = systemService.getEntity(TBInvestigatePlanEntity.class,planId);
            if(investigatePlan != null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                request.setAttribute("startDate",sdf.format(investigatePlan.getStartTime()));
                request.setAttribute("endDate",sdf.format(investigatePlan.getEndTime()));
            }
        }
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/chooseHiddenDangerHandle");
    }

    @RequestMapping(params = "chooseHiddenListDataGrid")
    public void chooseHiddenListDataGrid(TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class, dataGrid);
        try{
            cq.createAlias("hiddenDanger","hiddenDanger");
            String findTimeStart = request.getParameter("hiddenDanger.examDate_begin");
            String findTimeEnd = request.getParameter("hiddenDanger.examDate_end");
            if(StringUtils.isNotBlank(findTimeStart)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.ge("hiddenDanger.examDate",sdf.parse(findTimeStart));
            }
            if(StringUtils.isNotBlank(findTimeEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.le("hiddenDanger.examDate",sdf.parse(findTimeEnd));
            }

            String manageType = request.getParameter("hiddenDanger.manageType");
            if(StringUtils.isNotBlank(manageType)){
                cq.eq("hiddenDanger.manageType", manageType);
            }
            String handlelStatus = request.getParameter("handlelStatus");
            if(StringUtils.isNotBlank(handlelStatus)){
                cq.eq("handlelStatus", handlelStatus);
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        cq.addOrder("hiddenDanger.examDate", SortDirection.desc);
        cq.add();
        this.tBHiddenDangerHandleService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "queryListDatagrid")
    public void queryListDatagrid(TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class, dataGrid);
        try{
            initQueryListCondition(request, cq);
            String riskManageTaskAllManageId = request.getParameter("riskManageTaskAllManageId");
            String riskManageTaskAllId = request.getParameter("riskManageTaskAllId");
            String riskManageTaskAllGRId = request.getParameter("riskManageTaskAllGRId");
            if(StringUtil.isNotEmpty(riskManageTaskAllManageId)|| StringUtil.isNotEmpty(riskManageTaskAllId)|| StringUtil.isNotEmpty(riskManageTaskAllGRId)){
                //记录不做出去草稿的处理
            }else{
                cq.notEq("handlelStatus", Constants.HANDELSTATUS_DRAFT);
            }
            String homePage = request.getParameter("homePage");
            if(StringUtil.isNotEmpty(homePage)){
                cq.notEq("handlelStatus", Constants.REVIEWSTATUS_PASS);
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                if(StringUtils.isNotBlank(startDate)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cq.ge("hiddenDanger.examDate",sdf.parse(startDate));
                }
                if(StringUtils.isNotBlank(endDate)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cq.le("hiddenDanger.examDate",sdf.parse(endDate));
                }
            }
            String isHome = request.getParameter("isHome");
            if(StringUtil.isNotEmpty(isHome)){
                String[] clStatus = new String[]{
                        Constants.HANDELSTATUS_REPORT,
                        Constants.HANDELSTATUS_ROLLBACK_CHECK
                };
                cq.in("handlelStatus",clStatus);
            }

            String manageType = request.getParameter("hiddenDanger.manageType");
            if(StringUtil.isNotEmpty(manageType)){
                cq.eq("hiddenDanger.manageType",manageType);
            }

            String hiddenNatureList = request.getParameter("hiddenNatureList");
            if(StringUtil.isNotEmpty(hiddenNatureList)){
                List<String> hiddenNatureListTemp = new ArrayList<>();
                for(String hiddenNature : hiddenNatureList.split(",")){
                    hiddenNatureListTemp.add(hiddenNature);
                }
                cq.in("hiddenDanger.hiddenNature",hiddenNatureListTemp.toArray());
            }
            String riskTypeList = request.getParameter("riskTypeList");

            if(StringUtil.isNotEmpty(riskTypeList)){
                List<String> riskTypeListTemp = new ArrayList<>();
                for(String riskType : riskTypeList.split(",")){
                    riskTypeListTemp.add(riskType);
                }
                cq.in("hiddenDanger.riskType",riskTypeListTemp.toArray());
            }

            String riskIsRel = request.getParameter("riskIsRel");
            if(StringUtil.isNotEmpty(riskIsRel)){
                if (riskIsRel.equals("0")){
                    cq.or(Restrictions.isNull("hiddenDanger.riskId.id"), Restrictions.eq("hiddenDanger.riskId.id", ""));
                }else if(riskIsRel.equals("1")){
                    String sql = "SELECT id from t_b_risk_identification";
                    List<String> riskIds = systemService.findListbySql(sql);
                    cq.in("hiddenDanger.riskId.id",riskIds.toArray());
                }
            }

            //任务记录

            if(StringUtil.isNotEmpty(riskManageTaskAllManageId)){
                cq.add(Restrictions.sqlRestriction("this_.hidden_danger_id in (SELECT temp.id id FROM ( SELECT id FROM t_b_hidden_danger_exam WHERE task_all_id IN ( SELECT id FROM t_b_risk_manage_task_all WHERE task_all_manage_id = '"+riskManageTaskAllManageId+"' ) UNION ALL SELECT rmrh.hd_id id FROM t_b_risk_manage_task_all_manage tam LEFT JOIN t_b_risk_manage_task_all ta ON tam.id = ta.task_all_manage_id LEFT JOIN t_b_risk_manage_task t ON t.task_all_id = ta.id LEFT JOIN t_b_risk_manage_hazard_factor rmhf ON t.id = rmhf.risk_manage_task_id LEFT JOIN t_b_risk_manage_rel_hd rmrh ON rmrh.risk_manage_hazard_factor_id = rmhf.id WHERE tam.id = '"+riskManageTaskAllManageId+"' AND rmrh.hd_id IS NOT NULL AND rmrh.hd_id != '' ) temp)"));
            }


            if(StringUtil.isNotEmpty(riskManageTaskAllId)){
                cq.add(Restrictions.sqlRestriction("this_.hidden_danger_id in (SELECT temp.id id from (SELECT id FROM t_b_hidden_danger_exam WHERE task_all_id = '"+riskManageTaskAllId+"' UNION ALL   " +
                        "SELECT\n" +
                        "\trmrh.hd_id\n" +
                        "FROM\n" +
                        "\tt_b_risk_manage_task_all ta\n" +
                        "LEFT JOIN t_b_risk_manage_task t ON t.task_all_id = ta.id\n" +
                        "LEFT JOIN t_b_risk_manage_hazard_factor rmhf on t.id = rmhf.risk_manage_task_id\n" +
                        "LEFT JOIN t_b_risk_manage_rel_hd rmrh on rmrh.risk_manage_hazard_factor_id = rmhf.id\n" +
                        "WHERE ta.id = '"+riskManageTaskAllId+"' ) temp)"));
            }
            if(StringUtil.isNotEmpty(riskManageTaskAllGRId)){
                RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.get(RiskManageTaskAllEntity.class,riskManageTaskAllGRId);
                if(riskManageTaskAllEntity!=null){
                    String manage = riskManageTaskAllEntity.getManageType();
                    if(StringUtil.isNotEmpty(manage)){
                        if(manage.equals("comprehensive")||manage.equals("profession")||manage.equals("team")||manage.equals("group")){
                            String hdCountSql = "SELECT\n" +
                                    "\ttemp.id id\n" +
                                    "FROM\n" +
                                    "\t(\n" +
                                    "\t\tSELECT\n" +
                                    "\t\t\tid\n" +
                                    "\t\tFROM\n" +
                                    "\t\t\tt_b_hidden_danger_exam\n" +
                                    "\t\tWHERE\n" +
                                    "\t\t\ttask_all_id IS NOT NULL\n" +
                                    "\t\tAND task_all_id = '"+riskManageTaskAllGRId+"'\n" +
                                    "\t\tUNION ALL\n" +
                                    "\t\t\tSELECT\n" +
                                    "\t\t\t\trmrh.hd_id id\n" +
                                    "\t\t\tFROM\n" +
                                    "\t\t\t\tt_b_risk_manage_task_all ta\n" +
                                    "\t\t\tLEFT JOIN t_b_risk_manage_task t ON t.task_all_id = ta.id\n" +
                                    "\t\t\tLEFT JOIN t_b_risk_manage_hazard_factor rmhf ON t.id = rmhf.risk_manage_task_id\n" +
                                    "\t\t\tLEFT JOIN t_b_risk_manage_rel_hd rmrh ON rmrh.risk_manage_hazard_factor_id = rmhf.id\n" +
                                    "\t\t\twhere\tta.id = '"+riskManageTaskAllGRId+"' and rmrh.hd_id is not null \n" +
                                    "\t) temp  where temp.id is not null  GROUP BY temp.id";
                            cq.add(Restrictions.sqlRestriction("this_.hidden_danger_id in ("+hdCountSql+")"));
                        }else if(manage.equals("post")){
                            String hdPostCountSql = "SELECT\n" +
                                    "\ttemp.id id\n" +
                                    "FROM\n" +
                                    "\t(\n" +
                                    "\t\tSELECT\n" +
                                    "\t\t\tid\n" +
                                    "\t\tFROM\n" +
                                    "\t\t\tt_b_hidden_danger_exam\n" +
                                    "\t\tWHERE\n" +
                                    "\t\t\ttask_all_id = '"+riskManageTaskAllGRId+"'\n" +
                                    "\t\tUNION ALL\n" +
                                    "\t\t\tSELECT\n" +
                                    "\t\t\t\trmrh.hd_id id\n" +
                                    "\t\t\tFROM\n" +
                                    "\t\t\t\tt_b_risk_manage_task_all ta\n" +
                                    "\t\t\tLEFT JOIN t_b_risk_manage_post_task t ON t.post_task_all_id = ta.id\n" +
                                    "\t\t\tLEFT JOIN t_b_risk_manage_post_hazard_factor rmhf ON t.id = rmhf.risk_manage_post_task_id\n" +
                                    "\t\t\tLEFT JOIN t_b_risk_manage_post_rel_hd rmrh ON rmrh.risk_manage_post_hazard_factor_id = rmhf.id\n" +
                                    "\t\t\tWHERE\n" +
                                    "\t\t\t\tta.id = '"+riskManageTaskAllGRId+"'\n" +
                                    "\t) temp\n" +
                                    "GROUP BY\n" +
                                    "\ttemp.id";
                            cq.add(Restrictions.sqlRestriction("this_.hidden_danger_id in ("+hdPostCountSql+")"));
                        }else{
                            String hdYHCountSql = "SELECT  id from\n" +
                                    "t_b_hidden_danger_exam   WHERE  task_all_id  = '"+riskManageTaskAllGRId+"'  ";

                            cq.add(Restrictions.sqlRestriction("this_.hidden_danger_id in ("+hdYHCountSql+")"));
                        }
                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        //cq.addOrder("hiddenDanger.examDate", SortDirection.desc);

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser u = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ u.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunCommonUser").equals(userRole)){
                isSunRole = true;
            }
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if (isSunRole){
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_hidden_danger_handle'";
            List<String> sunList = systemService.findListbySql(sunSql);
            if (sunList!=null && sunList.size()>0){
                String[] sunString = new String[sunList.size()];
                for (int i=0; i<sunList.size(); i++){
                    sunString[i] = sunList.get(i);
                }
                cq.notIn("id", sunString);
            }
        }
        /*************************************************************/

        cq.add();
        cq.getDetachedCriteria().addOrder(expiredOrder.getOrder("handlelStatus"));
        this.tBHiddenDangerHandleService.getDataGridReturn(cq, true);
        String lilou = ResourceUtil.getConfigByName("ewmgn");
        if(dataGrid != null && dataGrid.getResults() != null){
            List<TBHiddenDangerHandleEntity> list = dataGrid.getResults();
            for(TBHiddenDangerHandleEntity t : list){
                if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                    String[] ids = t.getHiddenDanger().getFillCardManId().split(",");
                    String name = "";

                    for(String id : ids){
                        TSUser user = systemService.getEntity(TSUser.class,id);
                        if(user!=null){
                            if(StringUtils.isNotBlank(lilou) && "true".equals(lilou)){
                                if(name==""){
                                    name = name +user.getRealName();
                                }else{
                                    name = name + "," +user.getRealName();
                                }
                            }else {
                                if(name==""){
                                    name = name + user.getUserName()+"-"+user.getRealName();
                                }else{
                                    name = name + "," + user.getUserName()+"-"+user.getRealName();
                                }
                            }

                        }else if(StringUtil.isNotEmpty(id)){
                            if (name == "") {
                                name = name + id;
                            } else {
                                name = name + "," + id;
                            }
                        }
                    }
                    t.getHiddenDanger().setFillCardManNames(name);
                }
                if(t.getHiddenDanger().getDangerId() != null && StringUtils.isNotBlank(t.getHiddenDanger().getDangerId().getYeRiskGrade())){

                    String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", t.getHiddenDanger().getDangerId().getYeRiskGrade());

                    if("重大风险".equals(yeRiskGradeTemp)){
                        t.setAlertColor(Constants.ALERT_COLOR_ZDFX);
                    }else if("较大风险".equals(yeRiskGradeTemp)){
                        t.setAlertColor(Constants.ALERT_COLOR_JDFX);
                    }else if("一般风险".equals(yeRiskGradeTemp)){
                        t.setAlertColor(Constants.ALERT_COLOR_YBFX);
                    }else{
                        t.setAlertColor(Constants.ALERT_COLOR_DFX);
                    }
                    t.setYeRiskGradeTemp(yeRiskGradeTemp);
                }
                if(Constants.HIDDENCHECK_EXAMTYPE_SHANGJI.equals(t.getHiddenDanger().getExamType())){
                    t.getHiddenDanger().setFillCardManNames(t.getHiddenDanger().getSjjcCheckMan());
                }

                //如果origin为空，那么认为来自本矿
                if (t.getHiddenDanger()!=null){
                    if (StringUtil.isEmpty(t.getHiddenDanger().getOrigin())){
                        t.getHiddenDanger().setOrigin("1");
                    }
                }
            }

        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "notRectifyListDatagrid")
    public void notRectifyListDatagrid(TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class, dataGrid);
        try{
            initQueryListCondition(request, cq);
            cq.notEq("handlelStatus", Constants.HANDELSTATUS_DRAFT);
            cq.eq("handlelStatus","1");
            Date d = new Date();
            cq.le("hiddenDanger.limitDate",d);
        }catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser u = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ u.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunCommonUser").equals(userRole)){
                isSunRole = true;
            }
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if (isSunRole){
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_hidden_danger_handle'";
            List<String> sunList = systemService.findListbySql(sunSql);
            if (sunList!=null && sunList.size()>0){
                String[] sunString = new String[sunList.size()];
                for (int i=0; i<sunList.size(); i++){
                    sunString[i] = sunList.get(i);
                }
                cq.notIn("id", sunString);
            }
        }
        /*************************************************************/

        cq.add();
        this.tBHiddenDangerHandleService.getDataGridReturn(cq, true);
        String lilou = ResourceUtil.getConfigByName("ewmgn");
        if(dataGrid != null && dataGrid.getResults() != null){
            List<TBHiddenDangerHandleEntity> list = dataGrid.getResults();
            for(TBHiddenDangerHandleEntity t : list){
                if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                    String[] ids = t.getHiddenDanger().getFillCardManId().split(",");
                    String name = "";

                    for(String id : ids){
                        TSUser user = systemService.getEntity(TSUser.class,id);
                        if(user!=null){
                            if(StringUtils.isNotBlank(lilou) && "true".equals(lilou)){
                                if(name==""){
                                    name = name +user.getRealName();
                                }else{
                                    name = name + "," +user.getRealName();
                                }
                            }else {
                                if(name==""){
                                    name = name + user.getUserName()+"-"+user.getRealName();
                                }else{
                                    name = name + "," + user.getUserName()+"-"+user.getRealName();
                                }
                            }
                        }else if(StringUtil.isNotEmpty(id)){
                            if(name==""){
                                name = name + id;
                            }else{
                                name = name + "," + id;
                            }
                        }
                    }
                    t.getHiddenDanger().setFillCardManNames(name);
                }
                if(t.getHiddenDanger().getDangerId() != null && StringUtils.isNotBlank(t.getHiddenDanger().getDangerId().getYeRiskGrade())){

                    String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", t.getHiddenDanger().getDangerId().getYeRiskGrade());

                    if("重大风险".equals(yeRiskGradeTemp)){
                        t.setAlertColor(Constants.ALERT_COLOR_ZDFX);
                    }else if("较大风险".equals(yeRiskGradeTemp)){
                        t.setAlertColor(Constants.ALERT_COLOR_JDFX);
                    }else if("一般风险".equals(yeRiskGradeTemp)){
                        t.setAlertColor(Constants.ALERT_COLOR_YBFX);
                    }else{
                        t.setAlertColor(Constants.ALERT_COLOR_DFX);
                    }
                    t.setYeRiskGradeTemp(yeRiskGradeTemp);
                }
                if(Constants.HIDDENCHECK_EXAMTYPE_SHANGJI.equals(t.getHiddenDanger().getExamType())){
                    t.getHiddenDanger().setFillCardManNames(t.getHiddenDanger().getSjjcCheckMan());
                }

                //如果origin为空，那么认为来自本矿
                if (t.getHiddenDanger()!=null){
                    if (StringUtil.isEmpty(t.getHiddenDanger().getOrigin())){
                        t.getHiddenDanger().setOrigin("1");
                    }
                }
            }

        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "sendNotice")
    @ResponseBody
    public AjaxJson sendNotice(WeChatSendEntity weChat, HttpServletRequest req){
        AjaxJson j = new AjaxJson();
        String ids = req.getParameter("beNoticeMan");
        String content = req.getParameter("contentData");
        weChat.setIsSuccess("0");
        //发送微信通知
        try{
            if(ids!=null&&ids!=""){
                String problemDesc = content;
                String idString = "'"+ids+"'";
                idString = idString.replace(",", "','");
                List<String> officePhones=systemService.findListbySql("select u.weChatPhone from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.id in(" + idString + ")");
                for (String wnumber:officePhones) {
                    if (wnumber!=null&&StringUtils.isNotBlank(wnumber)) {
                        WebChatUtil.sendWeChatMessageToUser(wnumber,problemDesc);
                    }
                }
            }
        }catch(Exception e){
            logger.error(ExceptionUtil.getExceptionMessage(e));
            weChat.setIsSuccess("1");
            j.setMsg("微信发送出错，请联系管理员！");
        }
        weChat.setBeNoticeMan(ids);
        weChat.setContent("SYSTEMTEMP:"+content);
        try {
            systemService.save(weChat);
        } catch (Exception e) {
            j.setMsg("保存信息出错，请联系管理员！");
        }


        return j;
    }

    @RequestMapping(params = "goNotice")
    public ModelAndView goNotice (TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest req) {
        String modelType = req.getParameter("modelType");//模板类型
        if (StringUtils.isNotBlank(modelType)) {
            req.setAttribute("modelType", modelType);
        }
        if (StringUtil.isNotEmpty(tBHiddenDangerHandle.getId())) {
            tBHiddenDangerHandle = tBHiddenDangerHandleService.getEntity(TBHiddenDangerHandleEntity.class, tBHiddenDangerHandle.getId());
            req.setAttribute("tBHiddenDangerHandle", tBHiddenDangerHandle);
        }
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/weChat-send-yh");
    }

    @RequestMapping(params = "notRectifyDatagrid")
    public void notRectifyDatagrid(TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class, dataGrid);
        try{

            Date d = new Date();

            initQueryListCondition(request, cq);
            cq.notEq("handlelStatus", Constants.HANDELSTATUS_DRAFT);
            cq.eq("handlelStatus","1");
            String timeDays = request.getParameter("timeDays");
            cq.createAlias("hiddenDanger","hiddenDanger");
            cq.createAlias("hiddenDanger.address","address");
            cq.eq("address.isShowData","1");
            if(StringUtils.isBlank(timeDays)){
                timeDays = "0";
            }
            int days = 0;
            try{
                days = Integer.parseInt(timeDays);
            }catch(NumberFormatException e){
                days = 0;
            }
            Calendar ca = Calendar.getInstance();
            ca.setTime(d);
            ca.add(Calendar.DATE , days);

            cq.le("hiddenDanger.limitDate",ca.getTime());
        }catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser u = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ u.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunCommonUser").equals(userRole)){
                isSunRole = true;
            }
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if (isSunRole){
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_hidden_danger_handle'";
            List<String> sunList = systemService.findListbySql(sunSql);
            if (sunList!=null && sunList.size()>0){
                String[] sunString = new String[sunList.size()];
                for (int i=0; i<sunList.size(); i++){
                    sunString[i] = sunList.get(i);
                }
                cq.notIn("id", sunString);
            }
        }
        /*************************************************************/

        cq.add();
        this.tBHiddenDangerHandleService.getDataGridReturn(cq, true);
        String lilou = ResourceUtil.getConfigByName("ewmgn");
        if(dataGrid != null && dataGrid.getResults() != null){
            List<TBHiddenDangerHandleEntity> list = dataGrid.getResults();
            for(TBHiddenDangerHandleEntity t : list){
                if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                    String[] ids = t.getHiddenDanger().getFillCardManId().split(",");
                    String name = "";

                    for(String id : ids){
                        TSUser user = systemService.getEntity(TSUser.class,id);
                        if(user!=null){
                            if(StringUtils.isNotBlank(lilou) && "true".equals(lilou)){
                                if(name==""){
                                    name = name +user.getRealName();
                                }else{
                                    name = name + "," +user.getRealName();
                                }
                            }else {
                                if(name==""){
                                    name = name + user.getUserName()+"-"+user.getRealName();
                                }else{
                                    name = name + "," + user.getUserName()+"-"+user.getRealName();
                                }
                            }
                        }else if(StringUtil.isNotEmpty(id)){
                            if(name==""){
                                name = name + id;
                            }else{
                                name = name + "," + id;
                            }
                        }
                    }
                    t.getHiddenDanger().setFillCardManNames(name);
                }
                if(t.getHiddenDanger().getDangerId() != null && StringUtils.isNotBlank(t.getHiddenDanger().getDangerId().getYeRiskGrade())){

                    String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", t.getHiddenDanger().getDangerId().getYeRiskGrade());

                    if("重大风险".equals(yeRiskGradeTemp)){
                        t.setAlertColor(Constants.ALERT_COLOR_ZDFX);
                    }else if("较大风险".equals(yeRiskGradeTemp)){
                        t.setAlertColor(Constants.ALERT_COLOR_JDFX);
                    }else if("一般风险".equals(yeRiskGradeTemp)){
                        t.setAlertColor(Constants.ALERT_COLOR_YBFX);
                    }else{
                        t.setAlertColor(Constants.ALERT_COLOR_DFX);
                    }
                    t.setYeRiskGradeTemp(yeRiskGradeTemp);
                }
                if(Constants.HIDDENCHECK_EXAMTYPE_SHANGJI.equals(t.getHiddenDanger().getExamType())){
                    t.getHiddenDanger().setFillCardManNames(t.getHiddenDanger().getSjjcCheckMan());
                }

                //如果origin为空，那么认为来自本矿
                if (t.getHiddenDanger()!=null){
                    if (StringUtil.isEmpty(t.getHiddenDanger().getOrigin())){
                        t.getHiddenDanger().setOrigin("1");
                    }
                }
            }

        }
        TagUtil.datagrid(response, dataGrid);
    }
    /**
     * easyui AJAX请求数据
     *   本矿隐患上报查询
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "reportListDataGrid")
    public void reportListDataGrid(TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class, dataGrid);
        String reportStatus = request.getParameter("reportStatusFront");
        try{
            initQueryListCondition(request, cq);
            cq.notEq("handlelStatus", Constants.HANDELSTATUS_DRAFT);
            if(StringUtils.isNotBlank(reportStatus)&&reportStatus!=null){
                cq.eq("reportStatus",reportStatus);
            }else{
                cq.eq("reportStatus","1");
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        //cq.addOrder("hiddenDanger.examDate", SortDirection.desc);

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser u = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ u.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunCommonUser").equals(userRole)){
                isSunRole = true;
            }
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if (isSunRole){
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_hidden_danger_handle'";
            List<String> sunList = systemService.findListbySql(sunSql);
            if (sunList!=null && sunList.size()>0){
                String[] sunString = new String[sunList.size()];
                for (int i=0; i<sunList.size(); i++){
                    sunString[i] = sunList.get(i);
                }
                cq.notIn("id", sunString);
            }
        }
        /*************************************************************/

        cq.add();
        this.tBHiddenDangerHandleService.getDataGridReturn(cq, true);
        String lilou = ResourceUtil.getConfigByName("ewmgn");
        if(dataGrid != null && dataGrid.getResults() != null){
            List<TBHiddenDangerHandleEntity> list = dataGrid.getResults();
            for(TBHiddenDangerHandleEntity t : list){
                if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                    String[] ids = t.getHiddenDanger().getFillCardManId().split(",");
                    String name = "";

                    for(String id : ids){
                        TSUser user = systemService.getEntity(TSUser.class,id);
                        if(user!=null){
                            if(StringUtils.isNotBlank(lilou) && "true".equals(lilou)){
                                if(name==""){
                                    name = name +user.getRealName();
                                }else{
                                    name = name + "," +user.getRealName();
                                }
                            }else {
                                if(name==""){
                                    name = name + user.getUserName()+"-"+user.getRealName();
                                }else{
                                    name = name + "," + user.getUserName()+"-"+user.getRealName();
                                }
                            }
                        }else if(StringUtil.isNotEmpty(id)){
                            if(name==""){
                                name = name + id;
                            }else{
                                name = name + "," + id;
                            }
                        }
                    }
                    t.getHiddenDanger().setFillCardManNames(name);
                }
                if(t.getHiddenDanger().getDangerId() != null && StringUtils.isNotBlank(t.getHiddenDanger().getDangerId().getYeRiskGrade())){

                    String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", t.getHiddenDanger().getDangerId().getYeRiskGrade());

                    if("重大风险".equals(yeRiskGradeTemp)){
                        t.setAlertColor(Constants.ALERT_COLOR_ZDFX);
                    }else if("较大风险".equals(yeRiskGradeTemp)){
                        t.setAlertColor(Constants.ALERT_COLOR_JDFX);
                    }else if("一般风险".equals(yeRiskGradeTemp)){
                        t.setAlertColor(Constants.ALERT_COLOR_YBFX);
                    }else{
                        t.setAlertColor(Constants.ALERT_COLOR_DFX);
                    }
                    t.setYeRiskGradeTemp(yeRiskGradeTemp);
                }
                if(Constants.HIDDENCHECK_EXAMTYPE_SHANGJI.equals(t.getHiddenDanger().getExamType())){
                    t.getHiddenDanger().setFillCardManNames(t.getHiddenDanger().getSjjcCheckMan());
                }
            }

        }
        TagUtil.datagrid(response, dataGrid);
    }
    private void initQueryListCondition(HttpServletRequest request, CriteriaQuery cq) throws ParseException {
        //自定义追加查询条件
        cq.createAlias("hiddenDanger","hiddenDanger");

        //隐患类型
        String hiddenType = request.getParameter("hiddenDanger.hiddenType");
        if(StringUtil.isNotEmpty(hiddenType)){
            cq.eq("hiddenDanger.hiddenType",hiddenType);
        }
        String findTimeStart = request.getParameter("hiddenDanger.examDate_begin");
        String findTimeEnd = request.getParameter("hiddenDanger.examDate_end");
        if(StringUtils.isNotBlank(findTimeStart)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cq.ge("hiddenDanger.examDate",sdf.parse(findTimeStart));
        }
        if(StringUtils.isNotBlank(findTimeEnd)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cq.le("hiddenDanger.examDate",sdf.parse(findTimeEnd));
        }
        String checkShift = request.getParameter("hiddenDanger.shift");
        if(StringUtils.isNotBlank(checkShift)){
            cq.eq("hiddenDanger.shift",checkShift);
        }
        String examType = request.getParameter("hiddenDanger.examType");
        if(StringUtils.isNotBlank(examType)){
            if("jkxxblr".equals(examType)){
                cq.eq("hiddenDanger.isFromJkxxb","1");
            }else{
                cq.eq("hiddenDanger.examType",examType);
            }
        }
        String hiddenNature = request.getParameter("hiddenDanger.hiddenNature");
        if(StringUtils.isNotBlank(hiddenNature)){
            cq.eq("hiddenDanger.hiddenNature",hiddenNature);
        }
        String modifyDate = request.getParameter("modifyDate");
        if(StringUtils.isNotBlank(modifyDate)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cq.ge("modifyDate",sdf.parse(modifyDate+" 00:00:00"));
            cq.le("modifyDate",sdf.parse(modifyDate+" 23:59:59"));
        }
        String fillCaredManId = request.getParameter("hiddenDanger.fillCardMan.id");
        if(StringUtils.isNotBlank(fillCaredManId)){
            //cq.eq("hiddenDanger.fillCardMan.id",fillCaredManId);
            TSUser user = systemService.getEntity(TSUser.class,fillCaredManId);
            String name = "无";
            if(user!=null){
                name = user.getRealName();
            }
            String sql = "SELECT id FROM t_s_base_user WHERE realname = '"+fillCaredManId+"'";
            List<String> ids = systemService.findListbySql(sql);
            String id = "无";
            if(ids!=null&&ids.size()>0){
                id= ids.get(0);
            }
            cq.or(
                    Restrictions.eq("hiddenDanger.fillCardMan.id",fillCaredManId),
                    Restrictions.or(
                            Restrictions.eq("hiddenDanger.fillCardManId",fillCaredManId),
                            Restrictions.or(
                                    Restrictions.like("hiddenDanger.fillCardManId",fillCaredManId+",%"),
                                    Restrictions.or(
                                            Restrictions.like("hiddenDanger.fillCardManId","%,"+fillCaredManId),
                                            Restrictions.or(
                                                    Restrictions.like("hiddenDanger.fillCardManId","%,"+fillCaredManId+",%"),
                                                    Restrictions.or(
                                                            Restrictions.like("hiddenDanger.fillCardManId","%"+id+"%"),
                                                            Restrictions.like("hiddenDanger.fillCardManId","%"+name+"%")
                                                    )
                                            )
                                    )
                            )
                    )
            );
        }
        String checkDepartId = request.getParameter("checkDepartId");
        if(StringUtil.isNotEmpty(checkDepartId)){
            cq.add(Restrictions.sqlRestriction(" this_.id in (select h.id hid from t_b_hidden_danger_exam e, t_b_hidden_danger_handle h where e.id=h.hidden_danger_id   and EXISTS (\t\n" +
                    " select * from t_s_base_user u, t_s_user_org uo\n" +
                    " where uo.user_id=u.id and uo.org_id='"+checkDepartId+"'\n" +
                    " and (FIND_IN_SET(u.id,e.fill_card_manids) or FIND_IN_SET(u.realName,REPLACE(e.fill_card_manids,' ',',')) )\n" +
                    "\t))"));
        }
        cq.createAlias("hiddenDanger.address","address");
        cq.eq("address.isShowData","1");
        String address = request.getParameter("tBAddressInfoEntity_id");
        if(StringUtils.isNotBlank(address)){
            cq.eq("address.id",address);
            String dynamic = request.getParameter("dynamic");
            if(StringUtil.isNotEmpty(dynamic)){
                String[] clStatus = new String[]{
                        Constants.HANDELSTATUS_REPORT,
                        Constants.HANDELSTATUS_ROLLBACK_CHECK
                };
                cq.in("handlelStatus",clStatus);
            }
        }

        String departname = request.getParameter("hiddenDanger.dutyUnit.departname");
        if(StringUtils.isNotBlank(departname)){
            cq.eq("hiddenDanger.dutyUnit.id",departname);
        }
        String handlelStatus = request.getParameter("handlelStatus");
        if(StringUtils.isNotBlank(handlelStatus)){
            cq.eq("handlelStatus",handlelStatus);
        }
        String hiddenNumber = request.getParameter("hiddenDanger.hiddenNumber");
        if(StringUtils.isNotBlank(hiddenNumber)){
            cq.like("hiddenDanger.hiddenNumber","%"+hiddenNumber+"%");
        }
    }

    /**
     * 综合查询导出
     * @param tBHiddenDangerHandle
     * @param request
     * @param response
     * @param dataGrid
     * @param modelMap
     * @return
     */
    @RequestMapping(params = "queryListExportXls")
    public String queryListExportXls(TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {

        StringBuffer sql = new StringBuffer();
        String shift  = "zz.shift";
        String groupCode = "workShift";
        shift = replaceBySql(shift,groupCode);
        String examType = "zz.exam_type";
        String groupCodeExamType = "examType";
        String manageType = "zz.manage_type";
        String groupCodeManageType = "manageType";
        examType = replaceBySql(examType,groupCodeExamType);
        manageType = replaceBySql(manageType,groupCodeManageType);
        String hiddenNature = replaceBySql("zz.hidden_nature","hiddenLevel");
        String handelStatus = replaceBySql("zz.handlel_status","handelStatus");
        String hiddenType = replaceBySql("zz.hidden_type","hiddenType");
        String riskType = replaceBySql("zz.risk_type","risk_type");
        sql.append("select zz.hidden_number hiddenNumber,zz.id,DATE_FORMAT(zz.exam_date,'%Y/%m/%d') examDateTemp,"+shift+"  checkShiftTemp,\n" +
                "ta.hazard_name dangerSource,tb.address addressTemp,tc.departname dutyUnitDepartname,zz.duty_man dutyManTemp,mtu.departname manageDutyUnitDepartname,mtm.realname manageDutyManTemp,tf.fillCardManNames fillCardManNames,\n"+examType+
                "  examTypeTemp,"+hiddenNature+"  hiddenNatureTemp,"+riskType+"  riskTypeTemp,zz.problem_desc problemDescTemp,\n" + manageType+
                "  manageTypeTemp,\n" +
                "REPLACE(REPLACE(zz.deal_type,'1','限期整改'),'2','现场处理') dealTypeTemp,\n" +
                "DATE_FORMAT(zz.limit_date,'%Y/%m/%d') limitDatetemp,DATE_FORMAT(zz.modify_date,'%Y/%m/%d') modifyDateTemp,\n" +
                "IFNULL(td.realname,zz.modify_man) modifyManTemp,DATE_FORMAT(zz.review_date,'%Y/%m/%d') reviewDate,\n" +
                "te.realname reviewManTemp,"+handelStatus+" handlelStatusTemp,zz.rect_measures rectMeasuresTemp,zz.review_report reviewReportTemp,"+hiddenType+" hiddenTypeTemp,zz.roll_back_remark rollBackRemarkTemp,\n" +
                "zz.manage_type manageType\n" +
                "FROM\n" +
                "(");
        StringBuffer sqlTemp = new StringBuffer();
        sqlTemp.append("select hde.hidden_number,hde.id,hde.sjjc_check_man, hde.exam_date, hde.manage_type, hde.shift,hde.danger_id,hde.address,hde.duty_unit,hde.duty_man,hde.manage_duty_unit,hde.manage_duty_man_id,hde.fill_card_manids,hde.exam_type,hde.hidden_nature,hde.risk_type,hde.hidden_type,hde.problem_desc,hde.deal_type,hde.limit_date,hdh.modify_date,hdh.modify_man,hdh.review_date,hdh.review_man,hdh.handlel_status,hdh.rect_measures,hdh.review_report,hdh.roll_back_remark from t_b_hidden_danger_handle hdh left join t_b_hidden_danger_exam hde on hdh.hidden_danger_id = hde.id where 1=1 ");
        String idsRequest = request.getParameter("ids");
        if(StringUtils.isNotBlank(idsRequest)&&idsRequest!=null){
            StringBuffer idsTemp = new StringBuffer();
            for(String idTemp : idsRequest.split(",")){
                idsTemp.append("'"+idTemp+"',");
            }
            String idsCollections = idsTemp.toString().substring(0,(idsTemp.lastIndexOf(",")));
            sqlTemp.append(" and hdh.id in ("+idsCollections+") ");
        }else {
            try {
                sqlTemp = initQueryListConditionBySql(request, sqlTemp);
                sqlTemp.append(" and hdh.handlel_status <> '"+ Constants.HANDELSTATUS_DRAFT+"' ");
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException(e.getMessage());
            }
        }
        sql.append(sqlTemp);
        sql.append(") zz LEFT JOIN \n" +
                "(select ds.id,h.hazard_name from t_b_danger_source  ds LEFT JOIN t_b_hazard_manage h on ds.hazard_manage_id = h.id  ) ta on zz.danger_id = ta.id \n" +
                "LEFT JOIN (SELECT t.id,t.address from t_b_address_info t) tb ON zz.address = tb.id \n" +
                "LEFT JOIN (SELECT d.id,d.departname from t_s_depart d) tc on zz.duty_unit = tc.id\n" +
                "LEFT JOIN (SELECT b.id,b.realname from t_s_base_user b) td on zz.modify_man = td.id\n" +
                "LEFT JOIN (SELECT b.id,b.realname from t_s_base_user b) te on zz.review_man = te.id\n" +
                "LEFT JOIN (SELECT d.id,d.departname from t_s_depart d) mtu on zz.manage_duty_unit = mtu.id\n" +
                "LEFT JOIN (SELECT b.id,b.realname from t_s_base_user b) mtm on zz.manage_duty_man_id = mtm.id\n" +
                "LEFT JOIN (SELECT a.id,case a.exam_type when 'sjjc' then a.sjjc_check_man else " +
                /* "GROUP_CONCAT(tf.realname) " +*/
                "a.fill_card_manids " +
                "END fillCardManNames from ("+sqlTemp.toString()+") a\n" +
                "LEFT JOIN (SELECT b.id,b.realname from t_s_base_user b) tf on FIND_IN_SET(tf.id,a.fill_card_manids)\n" +
                "GROUP BY a.id" +
                ") tf on zz.id = tf.id  order by zz.exam_date desc");
        List<Map<String,Object>> retList = systemService.findForJdbc(sql.toString());
        String xinan = ResourceUtil.getConfigByName("xinan");
        String lilou = ResourceUtil.getConfigByName("ewmgn");
        for(int i = 0;i<retList.size();i++){
            String useridArray = String.valueOf(retList.get(i).get("fillCardManNames"));
            String[] ids = useridArray.split(",");
            String name = "";
            for(String id : ids){
                TSUser user = systemService.getEntity(TSUser.class,id);
                if(user!=null){
                    if(StringUtils.isNotBlank(lilou) && "true".equals(lilou)){
                        if(name==""){
                            name = name +user.getRealName();
                        }else{
                            name = name + "," +user.getRealName();
                        }
                    }else {
                        if(name==""){
                            name = name + user.getUserName()+"-"+user.getRealName();
                        }else{
                            name = name + "," + user.getUserName()+"-"+user.getRealName();
                        }
                    }
                }else if(StringUtil.isNotEmpty(id)){
                    if (name == "") {
                        name = name + id;
                    } else {
                        name = name + "," + id;
                    }
                }
                String daxing = ResourceUtil.getConfigByName("daxing");
                if(xinan.equals("true")||daxing.equals("true")){
                    name="";
                    if(user!=null){
                        if(name==""){
                            name = name +user.getRealName();
                        }else{
                            name = name + "," +user.getRealName();
                        }
                    }else if(StringUtil.isNotEmpty(id)){
                        if (name == "") {
                            name = name + id;
                        } else {
                            name = name + "," + id;
                        }
                    }
                }
            }
            retList.get(i).put("fillCardManNames",name);
            retList.get(i).put("num",i+1);
        }
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_HiddenDanger.xls");
        if(xinan.equals("true")){
            templateExportParams.setTemplateUrl("export/template/exportTemp_HiddenDangerxinan.xls");
        }
        String huayuan = ResourceUtil.getConfigByName("huayuan");
        if(huayuan.equals("true")){
            templateExportParams.setTemplateUrl("export/template/exportTemp_HiddenDangerhuayuan.xls");
        }
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", retList);
        modelMap.put(NormalExcelConstants.FILE_NAME,"问题导出列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }
    private StringBuffer initQueryListConditionBySql(HttpServletRequest request, StringBuffer sql) throws Exception {
        //隐患类型
        String hiddenType = request.getParameter("hiddenDanger.hiddenType");
        if(StringUtil.isNotEmpty(hiddenType)){
            sql.append(" and hde.hidden_type = '"+hiddenType+"' ");
        }
        String findTimeStart = request.getParameter("hiddenDanger.examDate_begin");
        String findTimeEnd = request.getParameter("hiddenDanger.examDate_end");
        if(StringUtils.isNotBlank(findTimeStart)){
            sql.append( " and hde.exam_date >='"+findTimeStart+"'");
        }
        if(StringUtils.isNotBlank(findTimeEnd)){
            sql.append(" and hde.exam_date <='"+findTimeEnd+"' ");
        }
        String checkShift = request.getParameter("hiddenDanger.shift");
        if(StringUtils.isNotBlank(checkShift)){
            sql.append(" and hde.shift = '"+checkShift+"' ");
        }
        String examType = request.getParameter("hiddenDanger.examType");
        if(StringUtils.isNotBlank(examType)){
            if("jkxxblr".equals(examType)){
                sql.append(" and hde.isFromJkxxb = '1' ");
            }else{
                sql.append(" and hde.exam_type ='"+examType+"' ");
            }
        }
        String hiddenNature = request.getParameter("hiddenDanger.hiddenNature");
        if(StringUtils.isNotBlank(hiddenNature)){
            sql.append(" and hde.hidden_nature ='"+hiddenNature+"' ");
        }
        String hiddenNatureList = request.getParameter("hiddenNatureList");
        if(StringUtil.isNotEmpty(hiddenNatureList)){
            String hiddenNatureListTemp = "''";
            for(String hiddenNatureTemp : hiddenNatureList.split(",")){
                hiddenNatureListTemp+=",'"+hiddenNatureTemp+"'";
            }
            sql.append(" and hde.hidden_nature in ("+hiddenNatureListTemp+") ");
        }
        String riskTypeList = request.getParameter("riskTypeList");

        if(StringUtil.isNotEmpty(riskTypeList)){
            String riskTypeListTemp ="''";
            for(String riskTypeTemp : riskTypeList.split(",")){
                riskTypeListTemp+=",'"+riskTypeTemp+"'";
            }
            sql.append(" and hde.risk_type in ("+riskTypeListTemp+") ");
        }
        String modifyDate = request.getParameter("modifyDate");
        if(StringUtils.isNotBlank(modifyDate)){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String modifyDateBegin = sdf.parse(modifyDate+" 00:00:00").toString();
            String modifyDateEnd = sdf.parse(modifyDate+" 23:59:59").toString();
            sql.append(" and hdh.modify_date >= '"+modifyDateBegin+"' ");
            sql.append(" and hdh.modify_date <= '"+modifyDateEnd+"' ");
        }
        String fillCaredManId = request.getParameter("hiddenDanger.fillCardMan.id");
        if(StringUtils.isNotBlank(fillCaredManId)){
            TSUser user = systemService.getEntity(TSUser.class,fillCaredManId);
            if(user!=null){
                sql.append(" and (FIND_IN_SET('"+fillCaredManId+"',hde.FILL_CARD_MANIDS) or FIND_IN_SET('"+user.getRealName()+"',hde.FILL_CARD_MANIDS))");
            }else{
                fillCaredManId= new String(request.getParameter("hiddenDanger.fillCardMan.id").getBytes("ISO-8859-1"),"UTF-8");
                List<String> ids = systemService.findListbySql("SELECT id FROM t_s_base_user WHERE realname = '"+fillCaredManId+"'");
                String id = "无";
                if(ids!=null&&ids.size()>0){
                    id= ids.get(0);
                }
                sql.append(" and (FIND_IN_SET('"+fillCaredManId+"',hde.FILL_CARD_MANIDS) or FIND_IN_SET('"+id+"',hde.FILL_CARD_MANIDS))");
            }

        }
        String address = request.getParameter("tBAddressInfoEntity_id");
        if(StringUtils.isNotBlank(address)){
            sql.append(" and hde.address ='"+address+"' ");
        }
        String departname = request.getParameter("hiddenDanger.dutyUnit.departname");
        if(StringUtils.isNotBlank(departname)){
            sql.append(" and hde.duty_unit ='"+departname+"' ");
        }
        String handlelStatus = request.getParameter("handlelStatus");
        if(StringUtils.isNotBlank(handlelStatus)){
            sql.append(" and hdh.handlel_status = '"+handlelStatus+"' ");
        }


        return sql;
    }
    private String replaceBySql(String first,String groupCode){
        List<TSType> types = ResourceUtil.allTypes.get(groupCode.toLowerCase());

        if(types.size()>9){
            //字典中的1和10，优先replace字典编码为10的，避免出现编码为10的“顶板”被替换为“冲击地压0”
            Collections.sort(types, new Comparator<TSType>() {
                @Override
                public int compare(TSType o1, TSType o2) {
                    String code1 = o1.getTypecode();
                    String code2 = o2.getTypecode();

                    if(code1.length()>code2.length()){
                        return -1;
                    }else{
                        return 0;
                    }
                }
            });
        }

        for(TSType type : types){
            first = "REPLACE("+first+",'"+type.getTypecode()+"','"+type.getTypename()+"')";
        }
        return first;
    }

    /**
     * 本矿上报导出
     * @param tBHiddenDangerHandle
     * @param request
     * @param response
     * @param dataGrid
     * @param modelMap
     * @return
     */
    @RequestMapping(params = "reportListExportXls")
    public String reportListExportXls(TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {

        List<TBHiddenDangerHandleEntity> retList = new ArrayList<>();
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class,dataGrid);
        String reportStatus = request.getParameter("reportStatusFront");
        String idTemp = request.getParameter("ids");
        if(StringUtils.isNotBlank(idTemp)&&idTemp!=null){
            List<String> idList = new ArrayList<>();
            for(String id : idTemp.split(",")){
                idList.add(id);
            }

            cq.in("id",idList.toArray());
            cq.add();

        }else {
            try {
                initQueryListCondition(request, cq);
                cq.notEq("handlelStatus", Constants.HANDELSTATUS_DRAFT);
                if(StringUtils.isNotBlank(reportStatus)&&reportStatus!=null){
                    cq.eq("reportStatus",reportStatus);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException(e.getMessage());
            }
            cq.addOrder("hiddenDanger.examDate", SortDirection.desc);
            cq.add();

        }
        retList = this.tBHiddenDangerHandleService.getListByCriteriaQuery(cq, false);
        List<TSUser> userList = systemService.getList(TSUser.class);
        Map<String, String> userMap = new HashMap<>();
        for (TSUser user : userList) {
            userMap.put(user.getId(), user.getRealName());
        }
        Map<String, String> userNameMap = new HashMap<>();
        for (TSUser user : userList) {
            userNameMap.put(user.getId(), user.getUserName());
        }
        for(TBHiddenDangerHandleEntity t : retList){
            if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                String[] useids = t.getHiddenDanger().getFillCardManId().split(",");
                StringBuffer userNames = new StringBuffer();
                for(String userId : useids){
                    if(StringUtils.isNotBlank(userNames.toString())) {
                        userNames.append(",");
                        if(userMap.get(userId)==null&& StringUtil.isNotEmpty(userId)){
                            userNames.append(userId);
                        }else {
                            userNames.append(userMap.get(userId)+"-"+userNameMap.get(userId));
                        }
                    }else{
                        if(userMap.get(userId)==null&& StringUtil.isNotEmpty(userId)){
                            userNames.append(userId);
                        }else{
                            userNames.append(userMap.get(userId)+"-"+userNameMap.get(userId));
                        }
                    }
                }
                t.getHiddenDanger().setFillCardManNames(userNames.toString());
            }
            if(Constants.HIDDENCHECK_EXAMTYPE_SHANGJI.equals(t.getHiddenDanger().getExamType())){
                t.getHiddenDanger().setFillCardManNames(t.getHiddenDanger().getSjjcCheckMan());
            }
        }
        List<Map<String,Object>> mapList = initExportList(retList);
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_HiddenDanger.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", mapList);
        modelMap.put(NormalExcelConstants.FILE_NAME,"问题导出列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }
    /**
     * 整改列表
     * @param request
     * @return
     */
    @RequestMapping(params = "checkList")
    public ModelAndView checkList(HttpServletRequest request) {
        /******************************判断是不是阳光管理员	然后决定是否显示隐藏按钮**************************/
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if(isSunRole){
            request.setAttribute("isSunAdmin", "YGADMIN");
        }else{
            request.setAttribute("isSunAdmin", "common");
        }
        /***************************************************************************************************************/
        String beixulou = ResourceUtil.getConfigByName("beixulou");
        request.setAttribute("beixulou",beixulou);
        String huayuan = ResourceUtil.getConfigByName("huayuan");
        request.setAttribute("huayuan",huayuan);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandle-checkList");
    }

    /**
     * 整改列表
     * @param request
     * @return
     */
    @RequestMapping(params = "applyDelayList")
    public ModelAndView applyDelayList(HttpServletRequest request) {
        /******************************判断是不是阳光管理员	然后决定是否显示隐藏按钮**************************/
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if(isSunRole){
            request.setAttribute("isSunAdmin", "YGADMIN");
        }else{
            request.setAttribute("isSunAdmin", "common");
        }
        /***************************************************************************************************************/
        String beixulou = ResourceUtil.getConfigByName("beixulou");
        request.setAttribute("beixulou",beixulou);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandle-applyDelayList");
    }
    /**
     *延期列表获取数据
     */
    @RequestMapping(params = "applyDelayDatagrid")
    public void applyDelayDatagrid(TBHiddenDangerHandleEntity tbHiddenDangerHandle, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class, dataGrid);
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        try{
            cq.createAlias("hiddenDanger","hiddenDangerExam");
            cq.createAlias("hiddenDangerExam.address","address");
            cq.eq("address.isShowData","1");
            cq.createAlias("hiddenDangerExam.applyEntity","apply");

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

            if(!isAdmin){
                cq.createAlias("apply.acceptMan","acceptUser");
                cq.add(
                        Restrictions.eq("acceptUser.id",sessionUser.getId())
                );
            }
            cq.add(
                    Restrictions.eq("apply.dealStatus","1")
            );
        }catch (Exception e){
            e.printStackTrace();
        }
        this.tBHiddenDangerHandleService.getDataGridReturn(cq, true);
        String lilou = ResourceUtil.getConfigByName("ewmgn");
        if(dataGrid != null && dataGrid.getResults() != null){
            List<TBHiddenDangerHandleEntity> list = dataGrid.getResults();
            for(TBHiddenDangerHandleEntity t : list){
                if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                    String[] ids = t.getHiddenDanger().getFillCardManId().split(",");
                    String name = "";

                    for(String id : ids){
                        TSUser user = systemService.getEntity(TSUser.class,id);
                        if(user!=null){
                            if(StringUtils.isNotBlank(lilou) && "true".equals(lilou)){
                                if(name==""){
                                    name = name +user.getRealName();
                                }else{
                                    name = name + "," +user.getRealName();
                                }
                            }else {
                                if(name==""){
                                    name = name + user.getUserName()+"-"+user.getRealName();
                                }else{
                                    name = name + "," + user.getUserName()+"-"+user.getRealName();
                                }
                            }
                        }else if(StringUtil.isNotEmpty(id)){
                            if(name==""){
                                name = name + id;
                            }else{
                                name = name + "," + id;
                            }
                        }
                    }

                    t.getHiddenDanger().setFillCardManNames(name);
                }else{
                    /**
                     *  上级检查中，检查人使用的字段是sjjcCheckMan，而其他页面，检查人使用的字段是fillCardManId，所以，为了使页面没有空字段，这里判断一步，如果fillCardMan为空，直接将sjjcCheckMan赋值给fillCardMan
                     * */
                    t.getHiddenDanger().setFillCardManNames(t.getHiddenDanger().getSjjcCheckMan());
                }
            }

        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 整改列表获取数据
     * @param tBHiddenDangerHandle
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "checkDatagrid")
    public void checkDatagrid(TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class, dataGrid);
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        //查询条件组装器
        try{

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
            if(!isAdmin){
                /*cq.add(
                        Restrictions.eq("hiddenDanger.dutyUnit.id", sessionUser.getCurrentDepart().getId())
                );*/
                //增加责任人权限
                cq.add(Restrictions.or(Restrictions.eq("hiddenDanger.dutyUnit.id", sessionUser.getCurrentDepart().getId()),
                        Restrictions.eq("hiddenDanger.dutyMan", sessionUser.getRealName())));
            }
            String  queryHandleStatusTem = request.getParameter("queryHandleStatus");
            String[] handleStatusArray = { Constants.HANDELSTATUS_REPORT,  Constants.HANDELSTATUS_ROLLBACK_CHECK};
            if(StringUtils.isNotBlank(queryHandleStatusTem)){
                if(Constants.HANDELSTATUS_REPORT.equals(queryHandleStatusTem)){
                    cq.in("handlelStatus", handleStatusArray);
                }else if(Constants.HANDELSTATUS_REVIEW.equals(queryHandleStatusTem)){
                    cq.eq("handlelStatus", Constants.HANDELSTATUS_REVIEW);
                }else if("all".equals(queryHandleStatusTem)){
                    cq.notEq("handlelStatus", Constants.HANDELSTATUS_DRAFT);
                }
            }else{
                cq.in("handlelStatus",handleStatusArray);
            }

            cq.createAlias("hiddenDanger","hiddenDanger");
            cq.createAlias("hiddenDanger.address","address");
            cq.eq("address.isShowData","1");
            String findTimeStart = request.getParameter("hiddenDanger.examDate_begin");
            String findTimeEnd = request.getParameter("hiddenDanger.examDate_end");
            if(StringUtils.isNotBlank(findTimeStart)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.ge("hiddenDanger.examDate",sdf.parse(findTimeStart));
            }
            if(StringUtils.isNotBlank(findTimeEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.le("hiddenDanger.examDate",sdf.parse(findTimeEnd));
            }

            String modifyDateBegin = request.getParameter("modifyDate_begin");
            String modifyDateEnd = request.getParameter("modifyDate_end");
            if(StringUtils.isNotBlank(modifyDateEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cq.le("modifyDate",sdf.parse(modifyDateEnd+" 23:59:59"));
            }
            if(StringUtils.isNotBlank(modifyDateBegin)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cq.ge("modifyDate",sdf.parse(modifyDateBegin+" 00:00:00"));
            }
            String checkPeopleName = request.getParameter("modifyMan");
            if(StringUtils.isNotBlank(checkPeopleName)){
                cq.like("modifyMan","%"+checkPeopleName+"%");
            }
            String checkShift = request.getParameter("hiddenDanger.shift");
            if(StringUtils.isNotBlank(checkShift)){
                cq.eq("hiddenDanger.shift",checkShift);
            }
            String address = request.getParameter("tBAddressInfoEntity_id");
            if(StringUtils.isNotBlank(address)){
                cq.eq("hiddenDanger.address.id",address);
            }
            String tempString = request.getParameter("tempString");
            if(StringUtils.isNotBlank(tempString)&&tempString!=null){
                if(!tempString.equals("0")){
                    cq.createAlias("hiddenDanger.applyEntity","apply");
                    cq.add(
                            Restrictions.eq("apply.dealStatus",tempString)
                    );
                }else{
                    cq.isNull("hiddenDanger.applyEntity.id");
                }

            }
            String hiddenNumber = request.getParameter("hiddenDanger.hiddenNumber");
            if(StringUtil.isNotEmpty(hiddenNumber)){
                cq.like("hiddenDanger.hiddenNumber","%"+hiddenNumber+"%");

            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        cq.addOrder("hiddenDanger.examDate", SortDirection.desc);

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser u = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ u.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunCommonUser").equals(userRole)){
                isSunRole = true;
            }
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if (isSunRole){
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_hidden_danger_handle'";
            List<String> sunList = systemService.findListbySql(sunSql);
            if (sunList!=null && sunList.size()>0){
                String[] sunString = new String[sunList.size()];
                for (int i=0; i<sunList.size(); i++){
                    sunString[i] = sunList.get(i);
                }
                cq.notIn("id", sunString);
            }
        }
        /*************************************************************/

        cq.add();
        this.tBHiddenDangerHandleService.getDataGridReturn(cq, true);
        String lilou = ResourceUtil.getConfigByName("ewmgn");
        if(dataGrid != null && dataGrid.getResults() != null){
            List<TBHiddenDangerHandleEntity> list = dataGrid.getResults();
            for(TBHiddenDangerHandleEntity t : list){
                if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                    String[] ids = t.getHiddenDanger().getFillCardManId().split(",");
                    String name = "";

                    for(String id : ids){
                        TSUser user = systemService.getEntity(TSUser.class,id);
                        if(user!=null){
                            if(StringUtils.isNotBlank(lilou) && "true".equals(lilou)){
                                if(name==""){
                                    name = name +user.getRealName();
                                }else{
                                    name = name + "," +user.getRealName();
                                }
                            }else {
                                if(name==""){
                                    name = name + user.getUserName()+"-"+user.getRealName();
                                }else{
                                    name = name + "," + user.getUserName()+"-"+user.getRealName();
                                }
                            }
                        }else if(StringUtil.isNotEmpty(id)){
                            if(name==""){
                                name = name + id;
                            }else{
                                name = name + "," + id;
                            }
                        }
                    }

                    t.getHiddenDanger().setFillCardManNames(name);
                }else{
                    /**
                     *  上级检查中，检查人使用的字段是sjjcCheckMan，而其他页面，检查人使用的字段是fillCardManId，所以，为了使页面没有空字段，这里判断一步，如果fillCardMan为空，直接将sjjcCheckMan赋值给fillCardMan
                     * */
                    t.getHiddenDanger().setFillCardManNames(t.getHiddenDanger().getSjjcCheckMan());
                }
                if(t.getHiddenDanger().getApplyEntity()==null||StringUtils.isBlank(t.getHiddenDanger().getApplyEntity().toString())){
                    t.setTempString("0");
                }else {
                    t.setTempString(t.getHiddenDanger().getApplyEntity().getDealStatus());
                }
            }

        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 待上报记录-撤回待审核的记录
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "toReportCallback")
    @ResponseBody
    public AjaxJson toReportCallback(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "撤回成功";
        try {
            if(StringUtil.isNotEmpty(ids)){
                for(String id : ids.split(",")){
                    TBHiddenDangerHandleEntity t = systemService.get(TBHiddenDangerHandleEntity.class, id);
                    if (Constants.HANDELSTATUS_REVIEW.equals(t.getHandlelStatus())) {
                        t.setHandlelStatus(Constants.HANDELSTATUS_REPORT);
                        t.setModifyMan("");
                        t.setModifyDate(null);
                        systemService.saveOrUpdate(t);
                    }
                }

                /**将矿版整改的状态同步到集团*/
                try{
                    if (StringUtil.isNotEmpty(ids)){
                        tBHiddenDangerHandleService.hiddenDangerReportToGroup(ids);
                    }
                }catch (Exception e){/**这个try-catch为了防止当网络出现问题的时候，导致系统报错无法整改的问题*/ System.out.println("隐患整改状态同步到集团失败");}
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "撤回失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 复查列表
     * @param request
     * @return
     */
    @RequestMapping(params = "repartList")
    public ModelAndView repartList(HttpServletRequest request) {
        String beixulou = ResourceUtil.getConfigByName("beixulou");
        request.setAttribute("beixulou",beixulou);
        String huayuan = ResourceUtil.getConfigByName("huayuan");
        request.setAttribute("huayuan",huayuan);
        /******************************判断是不是阳光管理员	然后决定是否显示隐藏按钮**************************/
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if(isSunRole){
            request.setAttribute("isSunAdmin", "YGADMIN");
        }else{
            request.setAttribute("isSunAdmin", "common");
        }
        /***************************************************************************************************************/
        String longyun = ResourceUtil.getConfigByName("longyun");
        request.setAttribute("longyun",longyun);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandle-repartList");
    }

    /**
     * 复查列表获取数据
     * @param tBHiddenDangerHandle
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "repartDatagrid")
    public void repartDatagrid(TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        TSUser sessionUser = ResourceUtil.getSessionUserName();
        String sql = "select h.id hid from t_b_hidden_danger_exam e, t_b_hidden_danger_handle h where e.id=h.hidden_danger_id  ";

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ sessionUser.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunCommonUser").equals(userRole)){
                isSunRole = true;
            }
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if (isSunRole){
            sql += " and h.id not in(select column_id from t_b_sunshine where table_name = 't_b_hidden_danger_handle')";
        }
        /*************************************************************/
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
        boolean isAJY= false;
        if(roleList != null && !roleList.isEmpty()){
            for(TSRoleUser ru : roleList){
                TSRole role = ru.getTSRole();
                if(role != null && role.getRoleName().equals("安监员")){
                    isAJY = true;
                    break;
                }
            }
        }
        String ezhuang = ResourceUtil.getConfigByName("ezhuang");
        if(ezhuang.equals("true")){
            if (!isAdmin) { //管理员可以查看所有的待复查记录
                sql = sql + " and EXISTS (\t\n" +
                        " select * from t_s_base_user u, t_s_user_org uo\n" +
                        " where u.id='"+sessionUser.getId()+"' and (u.username=e.create_by or FIND_IN_SET(u.id,e.fill_card_manids) or FIND_IN_SET(u.realName,REPLACE(e.fill_card_manids,' ',',')) or u.id=e.review_man)\n" +
                        "\t)";
            }
        }else{
            if (!isAdmin&&!isAJY) { //管理员可以查看所有的待复查记录
                String departId = sessionUser.getCurrentDepart().getId();
                sql = sql + " and (EXISTS (\t\n" +
                        " select * from t_s_base_user u, t_s_user_org uo\n" +
                        " where uo.user_id=u.id and uo.org_id='" + departId + "'\n" +
                        " and (u.username=e.create_by or FIND_IN_SET(u.id,e.fill_card_manids) or FIND_IN_SET(u.realName,REPLACE(e.fill_card_manids,' ',',')) or u.id=e.review_man) or  e.manage_duty_unit = '" + departId + "' or e.manage_duty_man_id = '" + sessionUser.getId() + "') \n" +
                        "\t)";
            }
        }

        try{
            //自定义追加查询条件
            String  queryHandleStatusTem = request.getParameter("queryHandleStatus");
            if(StringUtils.isNotBlank(queryHandleStatusTem)){
                if(Constants.REVIEWSTATUS_PASS.equals(queryHandleStatusTem)){
                    sql =  sql + " and h.handlel_status in ('" + Constants.REVIEWSTATUS_PASS + "')";
                }else if(Constants.HANDELSTATUS_REVIEW.equals(queryHandleStatusTem)){
                    sql =  sql + " and h.handlel_status in ('" + Constants.HANDELSTATUS_REVIEW + "')";
                }else if(Constants.HANDELSTATUS_ROLLBACK_CHECK.equals(queryHandleStatusTem)){
                    sql =  sql + " and h.handlel_status in ('" + Constants.HANDELSTATUS_ROLLBACK_CHECK + "')";//复查未通过，退回整改
                }
            }else{
                sql =  sql + " and h.handlel_status in ('" + Constants.HANDELSTATUS_REVIEW + "')";
            }

            String findTimeStart = request.getParameter("hiddenDanger.examDate_begin");
            String findTimeEnd = request.getParameter("hiddenDanger.examDate_end");
            if(StringUtils.isNotBlank(findTimeStart)){
                sql =  sql + " and e.exam_date>= '" + findTimeStart + "'";
            }
            if(StringUtils.isNotBlank(findTimeEnd)){
                sql =  sql + " and e.exam_date<= '" + findTimeEnd + "'";
            }
            String modifyShift = request.getParameter("modifyShift");
            if(StringUtils.isNotBlank(modifyShift)){
                sql =  sql + " and h.modify_shift = '" + modifyShift + "'";
            }

            String modifyDateBegin = request.getParameter("modifyDate_begin");
            String modifyDateEnd = request.getParameter("modifyDate_end");
            if(StringUtils.isNotBlank(modifyDateEnd)){
                sql =  sql + " and h.modify_date <= '" + modifyDateEnd + " 23:59:59'";
            }
            if(StringUtils.isNotBlank(modifyDateBegin)){
                sql =  sql + " and h.modify_date >= '" + modifyDateBegin + " 00:00:00'";
            }
            String checkPeopleName = request.getParameter("modifyMan");
            if(StringUtils.isNotBlank(checkPeopleName)){
                sql =  sql + " and h.modify_man = '" + checkPeopleName + "'";
            }
            String reviewMan=request.getParameter("hiddenDanger.reviewMan");
            if(StringUtils.isNotBlank(reviewMan)){
                sql=sql+"and e.review_man= '"+reviewMan+"'";
            }

            String dutyUnit = request.getParameter("hiddenDanger.dutyUnit.departname");
            if(StringUtils.isNotBlank(dutyUnit)){
                sql =  sql + " and e.duty_unit = '" + dutyUnit + "'";
            }
            String examType = request.getParameter("hiddenDanger.examType");
            if(StringUtils.isNotBlank(examType)){
                if("jkxxblr".equals(examType)){
                    sql =  sql + " and e.isFromJkxxb = '1'";
                }else{
                    sql =  sql + " and e.exam_type = '" + examType + "'";
                }
            }

            String fillCaredManId = request.getParameter("hiddenDanger.fillCardMan.id");
            if(StringUtils.isNotBlank(fillCaredManId)){
                sql =  sql + " and ( (e.fill_card_man='" + fillCaredManId + "')";
                sql =  sql + " or (e.fill_card_manids = '" + fillCaredManId + "')  ";
                sql =  sql + " or (e.fill_card_manids like '" + fillCaredManId + ",%')  ";
                sql =  sql + " or (e.fill_card_manids like '%," + fillCaredManId + "')  ";
                sql =  sql + " or (e.fill_card_manids like '%," + fillCaredManId + ",%') ";
                TSUser user  = systemService.getEntity(TSUser.class,fillCaredManId);
                if(user!=null){
                    sql =  sql + " or (e.fill_card_manids like '%" + user.getRealName() + "%') ";
                }
                List<String> ids = systemService.findListbySql("SELECT id FROM t_s_base_user WHERE realname = '"+fillCaredManId+"'");
                String id = "无";
                if(ids!=null&&ids.size()>0){
                    id= ids.get(0);
                }
                sql =  sql + " or (e.fill_card_manids like '%" + id + "%') ";
                sql =  sql + " ) ";
            }

            String examShift = request.getParameter("hiddenDanger.shift");
            if(StringUtils.isNotBlank(examShift)){
                sql =  sql + " and e.shift = '" + examShift + "'";
            }

            String address = request.getParameter("tBAddressInfoEntity_id");
            if(StringUtils.isNotBlank(address)){
                sql =  sql + " and e.address = '" + address + "' ";
            }

            String manageType = request.getParameter("hiddenDanger.manageType");
            if(StringUtil.isNotEmpty(manageType)){
                sql = sql + " and e.manage_type='" + manageType + "'";
            }
            String hiddenNumber = request.getParameter("hiddenDanger.hiddenNumber");
            if(StringUtil.isNotEmpty(hiddenNumber)){
                sql = sql + " and e.hidden_number like '%" + hiddenNumber + "%'";
            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }

        sql =  sql + " order by e.exam_date desc ";
//        List<Map<String, Object>>  hList = systemService.findForJdbc(sql);
//        List<TBHiddenDangerHandleEntity> hdhList = new ArrayList<>();
//        if(null!=hList && hList.size()>0){
//            for(int i = 0 ; i < hList.size() ; i++) {
//                String hdhId = (String)hList.get(i).get("hid");
//                List<TBHiddenDangerHandleEntity> hdheList = systemService.findByProperty(TBHiddenDangerHandleEntity.class,"id", hdhId);
//                if(null!=hdheList && hdheList.size()>0){
//                    hdhList.add(hdheList.get(0));
//                }
//            }
//        }
        List<String> hList = systemService.findListbySql(sql);
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class,dataGrid);
        cq.createAlias("hiddenDanger","hiddenDanger");
        cq.createAlias("hiddenDanger.address","address");
        cq.eq("address.isShowData","1");
        if(hList.size()>0&&hList!=null){
            cq.in("id",hList.toArray());
        }else{
            cq.isNull("id");
        }
        cq.add();
        this.tBHiddenDangerHandleService.getDataGridReturn(cq, true);
//        int currentPage = dataGrid.getPage();
//        int pageSize = dataGrid.getRows();
//        int endIndex = pageSize * currentPage;
//
//        if(pageSize *currentPage > hdhList.size()){
//            endIndex = hdhList.size();
//        }
//        List<TBHiddenDangerHandleEntity> retList = hdhList.subList(pageSize *(currentPage -1), endIndex);
//        dataGrid.setResults(retList);
//        dataGrid.setTotal(hdhList.size());
        String lilou = ResourceUtil.getConfigByName("ewmgn");
        if(dataGrid != null && dataGrid.getResults() != null){
            List<TBHiddenDangerHandleEntity> list = dataGrid.getResults();
            for(TBHiddenDangerHandleEntity t : list){
                if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                    String[] ids = t.getHiddenDanger().getFillCardManId().split(",");
                    String name = "";

                    for(String id : ids){
                        TSUser user = systemService.getEntity(TSUser.class,id);
                        if(user!=null){
                            if(StringUtils.isNotBlank(lilou) && "true".equals(lilou)){
                                if(name==""){
                                    name = name +user.getRealName();
                                }else{
                                    name = name + "," +user.getRealName();
                                }
                            }else {
                                if(name==""){
                                    name = name + user.getUserName()+"-"+user.getRealName();
                                }else{
                                    name = name + "," + user.getUserName()+"-"+user.getRealName();
                                }
                            }
                        }else if(StringUtil.isNotEmpty(id)){
                            if(name==""){
                                name = name + id;
                            }else{
                                name = name + "," + id;
                            }
                        }
                    }

                    t.getHiddenDanger().setFillCardManNames(name);
                }else{
                    /**
                     *  上级检查中，检查人使用的字段是sjjcCheckMan，而其他页面，检查人使用的字段是fillCardManId，所以，为了使页面没有空字段，这里判断一步，如果fillCardMan为空，直接将sjjcCheckMan赋值给fillCardMan
                     * */
                    t.getHiddenDanger().setFillCardManNames(t.getHiddenDanger().getSjjcCheckMan());
                }
            }

        }
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 问题查询列表
     * @param request
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        String wxy = request.getParameter("wxy");
        request.setAttribute("wxy",wxy);
        String riskLevel = request.getParameter("riskLevel");
        request.setAttribute("riskLevel",riskLevel);
        String month = request.getParameter("month");
        request.setAttribute("month",month);
        String departId = request.getParameter("departId");
        request.setAttribute("departId",departId);
        String proType = request.getParameter("pro_type");
        request.setAttribute("pro_type",proType);
        String riskType = request.getParameter("risk_type");
        request.setAttribute("risk_type",riskType);
        String queryType = request.getParameter("queryType");
        request.setAttribute("query_type",queryType);

        /******************************判断是不是阳光管理员	然后决定是否显示隐藏按钮**************************/
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if(isSunRole){
            request.setAttribute("isSunAdmin", "YGADMIN");
        }else{
            request.setAttribute("isSunAdmin", "common");
        }
        /***************************************************************************************************************/
        String huayuan = ResourceUtil.getConfigByName("huayuan");
        request.setAttribute("huayuan",huayuan);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandleList");
    }

    /**
     * 问题列表异步获取数据
     * @param tBHiddenDangerHandle
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class, dataGrid);
        String wxy = request.getParameter("wxy");
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        try{
            //自定义追加查询条件
            String proType = request.getParameter("pro_type");
            if(StringUtils.isNotBlank(proType)){
                cq.createAlias("hiddenDanger.dangerId","dangerId");
                cq.eq("dangerId.proType",proType);
            }
            String departId = request.getParameter("departId");
            if(StringUtils.isNotBlank(departId)){
                cq.eq("hiddenDanger.dutyUnit.id",departId);
            }
            cq.createAlias("hiddenDanger","hiddenDanger");
            cq.createAlias("hiddenDanger.address","address");
            cq.eq("address.isShowData","1");
            String month = request.getParameter("month");
            String riskType =request.getParameter("risk_type");
            if(StringUtils.isNotBlank(riskType)){
                cq.createAlias("hiddenDanger.dangerId","dangerId");
                cq.eq("dangerId.manageObjectEntity.id",riskType);
            }
            String riskLevel = request.getParameter("riskLevel");
            //修改为管理员角色
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
            if(!isAdmin&&StringUtils.isBlank(wxy)&&StringUtils.isBlank(month) && StringUtils.isBlank(departId) && StringUtils.isBlank(proType) && StringUtils.isBlank(riskType) && StringUtils.isBlank(riskLevel)){
                cq.eq("hiddenDanger.dutyUnit.id",sessionUser.getCurrentDepart().getId());
            }
            String findTimeStart = request.getParameter("hiddenDanger.examDate_begin");
            String findTimeEnd = request.getParameter("hiddenDanger.examDate_end");
            if(StringUtils.isNotBlank(findTimeStart)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.ge("hiddenDanger.examDate",sdf.parse(findTimeStart));
            }
            if(StringUtils.isNotBlank(findTimeEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.le("hiddenDanger.examDate",sdf.parse(findTimeEnd));
            }
            String checkShift = request.getParameter("hiddenDanger.shift");
            if(StringUtils.isNotBlank(checkShift)){
                cq.eq("hiddenDanger.shift",checkShift);
            }
            if(StringUtils.isNotBlank(wxy)){
                cq.eq("hiddenDanger.dangerId.id",wxy);
            }
            if(StringUtils.isNotBlank(riskLevel)){
                cq.createAlias("hiddenDanger","hiddenDanger");
                cq.createAlias("hiddenDanger.dangerId","dangerId");
                cq.eq("dangerId.riskLevel",riskLevel);
            }
            String examType = request.getParameter("hiddenDanger.examType");
            if(StringUtils.isNotBlank(examType)){
                if("jkxxblr".equals(examType)){
                    cq.eq("hiddenDanger.isFromJkxxb","1");
                }else{
                    cq.eq("hiddenDanger.examType",examType);
                }
            }
            String hiddenNature = request.getParameter("hiddenDanger.hiddenNature");
            if(StringUtils.isNotBlank(hiddenNature)){
                cq.eq("hiddenDanger.hiddenNature",hiddenNature);
            }
            String modifyDate = request.getParameter("modifyDate");
            if(StringUtils.isNotBlank(modifyDate)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cq.ge("modifyDate",sdf.parse(modifyDate+" 00:00:00"));
                cq.le("modifyDate",sdf.parse(modifyDate+" 23:59:59"));
            }
            String modifyShift = request.getParameter("modifyShift");
            if(StringUtils.isNotBlank(modifyShift)){
                cq.eq("modifyShift",modifyShift);
            }
            String fillCaredManId = request.getParameter("hiddenDanger.fillCardMan.id");
            if(StringUtils.isNotBlank(fillCaredManId)){
                //cq.eq("hiddenDanger.fillCardMan.id",fillCaredManId);
                cq.or(
                        Restrictions.eq("hiddenDanger.fillCardMan.id",fillCaredManId),
                        Restrictions.or(
                                Restrictions.eq("hiddenDanger.fillCardManId",fillCaredManId),
                                Restrictions.or(
                                        Restrictions.like("hiddenDanger.fillCardManId",fillCaredManId+",%"),
                                        Restrictions.or(
                                                Restrictions.like("hiddenDanger.fillCardManId","%,"+fillCaredManId),
                                                Restrictions.like("hiddenDanger.fillCardManId","%,"+fillCaredManId+",%")
                                        )
                                )
                        )
                );
            }
            String address = request.getParameter("tBAddressInfoEntity_id");
            if(StringUtils.isNotBlank(address)){
                cq.eq("hiddenDanger.address.id",address);
            }
            String handlelStatus = request.getParameter("handlelStatus");
            if(StringUtils.isNotBlank(handlelStatus)){
                cq.eq("handlelStatus",handlelStatus);
            }
            if(StringUtils.isNotBlank(month)){
                if("this".equals(month)){//查询本月
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.MONTH, 0);
                    c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
                    Calendar ca = Calendar.getInstance();
                    ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));

                    cq.ge("hiddenDanger.examDate", c.getTime());
                    cq.le("hiddenDanger.examDate",ca.getTime());
                }else if("last".equals(month)){//查询上月
                    Calendar cal1 = Calendar.getInstance();// 获取当前日期
                    cal1.add(Calendar.MONTH, -1);
                    cal1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
                    // 获取前月的最后一天
                    Calendar cale = Calendar.getInstance();
                    cale.set(Calendar.DAY_OF_MONTH,cale.getActualMaximum(Calendar.DAY_OF_MONTH));
                    cq.ge("hiddenDanger.examDate", cal1.getTime());
                    cq.le("hiddenDanger.examDate",cale.getTime());
                }
            }
            cq.notEq("handlelStatus", Constants.HANDELSTATUS_DRAFT);

            String manageType = request.getParameter("hiddenDanger.manageType");
            if(StringUtil.isNotEmpty(manageType)){
                cq.eq("hiddenDanger.manageType",manageType);
            }
            String hiddenNumber = request.getParameter("hiddenDanger.hiddenNumber");
            if(StringUtil.isNotEmpty(hiddenNumber)){
                cq.like("hiddenDanger.hiddenNumber","%"+hiddenNumber+"%");

            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        cq.addOrder("hiddenDanger.examDate", SortDirection.desc);

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser u = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ u.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunCommonUser").equals(userRole)){
                isSunRole = true;
            }
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if (isSunRole){
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_hidden_danger_handle'";
            List<String> sunList = systemService.findListbySql(sunSql);
            if (sunList!=null && sunList.size()>0){
                String[] sunString = new String[sunList.size()];
                for (int i=0; i<sunList.size(); i++){
                    sunString[i] = sunList.get(i);
                }
                cq.notIn("id", sunString);
            }
        }
        /*************************************************************/

        cq.add();
        this.tBHiddenDangerHandleService.getDataGridReturn(cq, true);
        String lilou = ResourceUtil.getConfigByName("ewmgn");
        if(dataGrid != null && dataGrid.getResults() != null){
            List<TBHiddenDangerHandleEntity> list = dataGrid.getResults();
            for(TBHiddenDangerHandleEntity t : list){
                if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                    String[] ids = t.getHiddenDanger().getFillCardManId().split(",");
                    String name = "";

                    for(String id : ids){
                        TSUser user = systemService.getEntity(TSUser.class,id);
                        if(user!=null){
                            if(StringUtils.isNotBlank(lilou) && "true".equals(lilou)){
                                if(name==""){
                                    name = name +user.getRealName();
                                }else{
                                    name = name + "," +user.getRealName();
                                }
                            }else {
                                if(name==""){
                                    name = name + user.getUserName()+"-"+user.getRealName();
                                }else{
                                    name = name + "," + user.getUserName()+"-"+user.getRealName();
                                }
                            }
                        }else if(StringUtil.isNotEmpty(id)){
                            if(name==""){
                                name = name + id;
                            }else{
                                name = name + "," + id;
                            }
                        }
                    }

                    t.getHiddenDanger().setFillCardManNames(name);
                }else{
                    /**
                     *  上级检查中，检查人使用的字段是sjjcCheckMan，而其他页面，检查人使用的字段是fillCardManId，所以，为了使页面没有空字段，这里判断一步，如果fillCardMan为空，直接将sjjcCheckMan赋值给fillCardMan
                     * */
                    t.getHiddenDanger().setFillCardManNames(t.getHiddenDanger().getSjjcCheckMan());
                }
            }

        }


        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 导出
     * @param tBHiddenDangerHandle
     * @param request
     * @param response
     * @param dataGrid
     * @param modelMap
     * @return
     */
    @RequestMapping(params = "exportExcel")
    public String exportExcel(TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class, dataGrid);
        String wxy = request.getParameter("wxy");
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        List<TBHiddenDangerHandleEntity> retList  = new ArrayList<TBHiddenDangerHandleEntity>();
        String idTemp = request.getParameter("ids");
        if(StringUtils.isNotBlank(idTemp)&&idTemp!=null){
            List<String> idList = new ArrayList<>();
            for(String id : idTemp.split(",")){
                idList.add(id);
            }

            cq.in("id",idList.toArray());
            cq.add();
            retList = this.tBHiddenDangerHandleService.getListByCriteriaQuery(cq, false);
        }else {
            try {
                //自定义追加查询条件
                String proType = request.getParameter("pro_type");
                if (StringUtils.isNotBlank(proType)) {
                    cq.createAlias("hiddenDanger.dangerId", "dangerId");
                    cq.eq("dangerId.proType", proType);
                }
                String departId = request.getParameter("departId");
                if (StringUtils.isNotBlank(departId)) {
                    cq.eq("hiddenDanger.dutyUnit.id", departId);
                }
                String queryHandleStatusTem = request.getParameter("queryHandleStatus");
                String[] handleStatusArray = {Constants.HANDELSTATUS_REPORT, Constants.HANDELSTATUS_ROLLBACK_CHECK};
                if (StringUtils.isNotBlank(queryHandleStatusTem)) {
                    if (Constants.HANDELSTATUS_REPORT.equals(queryHandleStatusTem)) {
                        cq.in("handlelStatus", handleStatusArray);
                    } else if (Constants.HANDELSTATUS_REVIEW.equals(queryHandleStatusTem)) {
                        cq.eq("handlelStatus", Constants.HANDELSTATUS_REVIEW);
                    }
                }

                cq.createAlias("hiddenDanger", "hiddenDanger");
                String month = request.getParameter("month");
                String riskType = request.getParameter("risk_type");
                if (StringUtils.isNotBlank(riskType)) {
                    cq.createAlias("hiddenDanger.dangerId", "dangerId");
                    cq.eq("dangerId.manageObjectEntity.id", riskType);
                }
                String riskLevel = request.getParameter("riskLevel");
                //修改为管理员角色
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
                if (!isAdmin&&!DicUtil.modifyPower(sessionUser.getId())) {
                    cq.eq("hiddenDanger.dutyUnit.id", sessionUser.getCurrentDepart().getId());
                }
                String findTimeStart = request.getParameter("hiddenDanger.examDate_begin");
                String findTimeEnd = request.getParameter("hiddenDanger.examDate_end");
                if (StringUtils.isNotBlank(findTimeStart)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cq.ge("hiddenDanger.examDate", sdf.parse(findTimeStart));
                }
                if (StringUtils.isNotBlank(findTimeEnd)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cq.le("hiddenDanger.examDate", sdf.parse(findTimeEnd));
                }
                String checkShift = request.getParameter("hiddenDanger.shift");
                if (StringUtils.isNotBlank(checkShift)) {
                    cq.eq("hiddenDanger.shift", checkShift);
                }
                if (StringUtils.isNotBlank(wxy)) {
                    cq.eq("hiddenDanger.dangerId.id", wxy);
                }
                if (StringUtils.isNotBlank(riskLevel)) {
                    cq.createAlias("hiddenDanger", "hiddenDanger");
                    cq.createAlias("hiddenDanger.dangerId", "dangerId");
                    cq.eq("dangerId.riskLevel", riskLevel);
                }
                String examType = request.getParameter("hiddenDanger.examType");
                if (StringUtils.isNotBlank(examType)) {
                    if ("jkxxblr".equals(examType)) {
                        cq.eq("hiddenDanger.isFromJkxxb", "1");
                    } else {
                        cq.eq("hiddenDanger.examType", examType);
                    }
                }
                String hiddenNature = request.getParameter("hiddenDanger.hiddenNature");
                if (StringUtils.isNotBlank(hiddenNature)) {
                    cq.eq("hiddenDanger.hiddenNature", hiddenNature);
                }
                String modifyDate = request.getParameter("modifyDate");
                if (StringUtils.isNotBlank(modifyDate)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    cq.ge("modifyDate", sdf.parse(modifyDate + " 00:00:00"));
                    cq.le("modifyDate", sdf.parse(modifyDate + " 23:59:59"));
                }
                String checkPeopleName = request.getParameter("modifyMan");
                if (StringUtils.isNotBlank(checkPeopleName)) {
                    cq.like("modifyMan", "%" + checkPeopleName + "%");
                }
                String modifyShift = request.getParameter("modifyShift");
                if (StringUtils.isNotBlank(modifyShift)) {
                    cq.eq("modifyShift", modifyShift);
                }
                String fillCaredManId = request.getParameter("hiddenDanger.fillCardMan.id");
                if (StringUtils.isNotBlank(fillCaredManId)) {
                    //cq.eq("hiddenDanger.fillCardMan.id",fillCaredMainId);
                    cq.or(
                            Restrictions.eq("hiddenDanger.fillCardMan.id", fillCaredManId),
                            Restrictions.or(
                                    Restrictions.eq("hiddenDanger.fillCardManId", fillCaredManId),
                                    Restrictions.or(
                                            Restrictions.like("hiddenDanger.fillCardManId", fillCaredManId + ",%"),
                                            Restrictions.or(
                                                    Restrictions.like("hiddenDanger.fillCardManId", "%," + fillCaredManId),
                                                    Restrictions.like("hiddenDanger.fillCardManId", "%," + fillCaredManId + ",%")
                                            )
                                    )
                            )
                    );
                }
                String address = request.getParameter("tBAddressInfoEntity_id");
                if (StringUtils.isNotBlank(address)) {
                    cq.eq("hiddenDanger.address.id", address);
                }
                String handlelStatus = request.getParameter("handlelStatus");
                if (StringUtils.isNotBlank(handlelStatus)) {
                    cq.eq("handlelStatus", handlelStatus);
                }
                if (StringUtils.isNotBlank(month)) {
                    if ("this".equals(month)) {//查询本月
                        Calendar c = Calendar.getInstance();
                        c.add(Calendar.MONTH, 0);
                        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
                        Calendar ca = Calendar.getInstance();
                        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));

                        cq.ge("hiddenDanger.examDate", c.getTime());
                        cq.le("hiddenDanger.examDate", ca.getTime());
                    } else if ("last".equals(month)) {//查询上月
                        Calendar cal1 = Calendar.getInstance();// 获取当前日期
                        cal1.add(Calendar.MONTH, -1);
                        cal1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
                        // 获取前月的最后一天
                        Calendar cale = Calendar.getInstance();
                        cale.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
                        cq.ge("hiddenDanger.examDate", cal1.getTime());
                        cq.le("hiddenDanger.examDate", cale.getTime());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException(e.getMessage());
            }
            cq.addOrder("hiddenDanger.examDate", SortDirection.desc);
            cq.add();
        }
        retList = this.tBHiddenDangerHandleService.getListByCriteriaQuery(cq, false);
        List<TSUser> userList = systemService.getList(TSUser.class);
        Map<String, String> userMap = new HashMap<>();
        for (TSUser user : userList) {
            userMap.put(user.getId(), user.getRealName());
        }
        Map<String, String> userNameMap = new HashMap<>();
        for (TSUser user : userList) {
            userNameMap.put(user.getId(), user.getUserName());
        }
        for(TBHiddenDangerHandleEntity t : retList){
            //转换fillCardManId为fillCardManNames
            if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                String[] useids = t.getHiddenDanger().getFillCardManId().split(",");
                StringBuffer userNames = new StringBuffer();
                for(String userId : useids){
                    if(StringUtils.isNotBlank(userNames.toString())) {
                        userNames.append(",");
                        if(userMap.get(userId)==null&& StringUtil.isNotEmpty(userId)){
                            userNames.append(userId);
                        }else {
                            userNames.append(userMap.get(userId)+"-"+userNameMap.get(userId));
                        }
                    }else{
                        if(userMap.get(userId)==null&& StringUtil.isNotEmpty(userId)){
                            userNames.append(userId);
                        }else{
                            userNames.append(userMap.get(userId)+"-"+userNameMap.get(userId));
                        }
                    }
                }

                t.getHiddenDanger().setFillCardManNames(userNames.toString());
            }
        }

        List<Map<String,Object>> mapList = initExportList(retList);
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_HiddenDanger.xls");
        String huayuan = ResourceUtil.getConfigByName("huayuan");
        if(huayuan.equals("true")){
            templateExportParams.setTemplateUrl("export/template/exportTemp_HiddenDangerhuayuan.xls");
        }
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", mapList);
        modelMap.put(NormalExcelConstants.FILE_NAME,"问题导出列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    /**
     * 批量删除隐患
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String message = "删除成功";
        try{
            for(String id:ids.split(",")){
                TBHiddenDangerHandleEntity tBHiddenDangerHandle = systemService.getEntity(TBHiddenDangerHandleEntity.class, id);
                TBReportDeleteIdEntity reportDeleteIdEntity = new TBReportDeleteIdEntity();
                reportDeleteIdEntity.setDeleteId(id);
                reportDeleteIdEntity.setType("hd");
                systemService.save(reportDeleteIdEntity);
                //删除隐患和风险管控的关联关系
                String sql = "delete from t_b_risk_manage_rel_hd where hd_id = '" + tBHiddenDangerHandle.getHiddenDanger().getId() + "'";
                systemService.executeSql(sql);

                //先行删除关联延期申请
                TBHiddenDangerExamEntity hiddenDangerExam = tBHiddenDangerHandle.getHiddenDanger();
                List<TBHiddenDangerApplyEntity> tbHiddenDangerApplyEntities = systemService.findByProperty(TBHiddenDangerApplyEntity.class,"hiddenDangerId",hiddenDangerExam);
                if(tbHiddenDangerApplyEntities.size()>0&&tbHiddenDangerApplyEntities!=null){
                    for(TBHiddenDangerApplyEntity tbHiddenDangerApplyEntity : tbHiddenDangerApplyEntities){
                        systemService.delete(tbHiddenDangerApplyEntity);
                    }
                }
                //重写delete功能 同时删除相关联表格数据
                tBHiddenDangerHandleService.deleteHidden(tBHiddenDangerHandle);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

                //删除阳光账号列表的关联数据
                List<TBSunshineEntity> tbSunshineEntityList = systemService.findByProperty(TBSunshineEntity.class, "columnId", id);
                if (tbSunshineEntityList!=null && tbSunshineEntityList.size()>0){
                    systemService.deleteAllEntitie(tbSunshineEntityList);
                }
                //更新隐患上报关联表数据
                sfService.deleteSfHiddenRel(hiddenDangerExam.getId());
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            throw new BusinessException(e.getMessage());
        }

        j.setMsg(message);
        return j;
    }

    /**
     * 隐患整改页面
     * @param tBHiddenDangerHandle
     * @param req
     * @return
     */
    @RequestMapping(params = "goModifyIssues")
    public ModelAndView goModifyIssues (TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tBHiddenDangerHandle.getId())) {
            tBHiddenDangerHandle = tBHiddenDangerHandleService.getEntity(TBHiddenDangerHandleEntity.class, tBHiddenDangerHandle.getId());
            req.setAttribute("tBHiddenDangerHandle", tBHiddenDangerHandle);
        }
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandle-check");
    }
    /**
     * 隐患批量整改页面
     * @param req
     * @return
     */
    @RequestMapping(params = "goModifyAllIssues")
    public ModelAndView goModifyAllIssues (HttpServletRequest req) {
        String ids = req.getParameter("ids");
        int indexFirst = ids.indexOf(",");
        String id = ids.substring(0,indexFirst);
        TBHiddenDangerHandleEntity tBHiddenDangerHandle = new TBHiddenDangerHandleEntity();
        tBHiddenDangerHandle.setId(id);
        if (StringUtil.isNotEmpty(tBHiddenDangerHandle.getId())) {
            tBHiddenDangerHandle = tBHiddenDangerHandleService.getEntity(TBHiddenDangerHandleEntity.class, tBHiddenDangerHandle.getId());
            tBHiddenDangerHandle.setIds(ids);
            req.setAttribute("tBHiddenDangerHandle", tBHiddenDangerHandle);
        }
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandle-checkAll");
    }
    /**
     * 批量整改通过
     */
    @RequestMapping(params = "saveAllModifyIssues")
    @ResponseBody
    public AjaxJson saveAllModifyIssues(TBHiddenDangerHandleEntity assessIssue){
        AjaxJson j = new AjaxJson();
        int success = 0;
        int other = 0;
        if (StringUtil.isNotEmpty(assessIssue.getIds())){
            List<String> examIds = new ArrayList<>();
            String ids = assessIssue.getIds();
            try{
                for(String id:ids.split(",")){
                    TBHiddenDangerHandleEntity tBHiddenDangerHandle = systemService.getEntity(TBHiddenDangerHandleEntity.class, id);
                    if (Constants.HANDELSTATUS_REPORT.equals(tBHiddenDangerHandle.getHandlelStatus())
                            || Constants.HANDELSTATUS_ROLLBACK_CHECK.equals(tBHiddenDangerHandle.getHandlelStatus())) {
                        saveIssues(assessIssue,tBHiddenDangerHandle);
                        //国家局上报 更新sf_hidden_rel关联表
                        sfService.saveOrUpdateSfHiddenRel(tBHiddenDangerHandle.getHiddenDanger().getId());
                        examIds.add(tBHiddenDangerHandle.getHiddenDanger().getId());
                        success++;
                    } else {
                        other++;
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
                j.setMsg("整改失败");
                throw new BusinessException(e.getMessage());
            }

            if(!examIds.isEmpty() && examIds.size()>0){
                syncToCloudService.hiddenDangerBatchReport(StringUtils.join(examIds, ","));
            }
        }
        String checkStatus = ResourceUtil.getParameter("checkStatus");
        if ("0".equals(checkStatus)) {
            j.setMsg(success+"条退回成功"+other+"条已被其他用户处理.");
            /**将矿版整改的状态同步到集团*/
            try{
                if (StringUtil.isNotEmpty(assessIssue.getIds())){
                    //tBHiddenDangerHandleService.hiddenDangerReportToGroup(assessIssue.getIds());
                    //tBHiddenDangerHandleService.reportToGroupByThread(assessIssue.getIds());
                    UploadThread uploadThread = new UploadThread(assessIssue.getIds());
                    Thread thread = new Thread(uploadThread);
                    thread.start();
                }
            }catch (Exception e){/**这个try-catch为了防止当网络出现问题的时候，导致系统报错无法整改的问题*/ System.out.println("隐患整改状态同步到集团失败");}
        } else {
            j.setMsg(success+"条整改成功,"+other+"条已被其他用户处理.");
            /**将矿版整改的状态同步到集团*/
            try{
                if (StringUtil.isNotEmpty(assessIssue.getIds())){
                    //tBHiddenDangerHandleService.hiddenDangerReportToGroup(assessIssue.getIds());
                    //tBHiddenDangerHandleService.reportToGroupByThread(assessIssue.getIds());
                    UploadThread uploadThread = new UploadThread(assessIssue.getIds());
                    Thread thread = new Thread(uploadThread);
                    thread.start();
                }
            }catch (Exception e){/**这个try-catch为了防止当网络出现问题的时候，导致系统报错无法整改的问题*/ System.out.println("隐患整改状态同步到集团失败");}
        }
        return j;
    }

    /**
     * 隐患批量延期
     */
    @RequestMapping(params = "goApplyDelayAllIssues")
    public ModelAndView goApplyDelayAllIssues (HttpServletRequest req){
        String ids = req.getParameter("ids");
        req.setAttribute("ids",ids);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandle-applyDelay");
    }

    /**
     * 隐患批量延期申请
     */
    @RequestMapping(params = "saveAllApplyIssues")
    @ResponseBody
    public AjaxJson saveAllApplyIssues(TBHiddenDangerApplyEntity issues, HttpServletRequest req) throws Exception{
        AjaxJson j = new AjaxJson();
        String message = "隐患延期申请成功!";
        String ids = req.getParameter("ids");
        for(String id : ids.split(",")){
            TBHiddenDangerHandleEntity tbHiddenDangerHandle = systemService.getEntity(TBHiddenDangerHandleEntity.class,id);
            issues.setHiddenDangerId(tbHiddenDangerHandle.getHiddenDanger());
            if(checkApplyExists(tbHiddenDangerHandle.getHiddenDanger().getId())){
                //更新
                applyUpdate(issues);
                message = "隐患延期已重新申请成功！";
                systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
            }else{
                issues.setDealStatus("1");
                applyAdd(issues);
                systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            }

            //国家局上报 更新sf_hidden_rel关联表
            systemService.executeSql("UPDATE `sf_hidden_rel` set state_flag='"+Constants.GJJ_STATE_FLAG_2+"' where hidden_id='"+tbHiddenDangerHandle.getHiddenDanger().getId()+"'");
        }
        j.setMsg(message);
        return j;
    }

    /**新增申请*/
    private void applyAdd(TBHiddenDangerApplyEntity tbHiddenDangerApply) throws Exception{
        String hiddenDangerId = tbHiddenDangerApply.getHiddenDangerId().getId();
        TBHiddenDangerExamEntity relateHiddenDanger = systemService.getEntity(TBHiddenDangerExamEntity.class,hiddenDangerId);
        List<TBHiddenDangerApplyEntity> tempList = systemService.findByProperty(TBHiddenDangerApplyEntity.class,"hiddenDangerId.id",hiddenDangerId);
        tbHiddenDangerApply.setDealTime(String.valueOf(tempList.size()+1));
        TBHiddenDangerApplyEntity targetApply = new TBHiddenDangerApplyEntity();
        MyBeanUtils.copyBeanNotNull2Bean(tbHiddenDangerApply,targetApply);
        tbHiddenDangerApplyService.save(targetApply);
        relateHiddenDanger.setApplyEntity(targetApply);
        tbHiddenDangerExamService.saveOrUpdate(relateHiddenDanger);

    }

    /**
     * 判断此隐患是否已经申请
     * @param hiddenDangerId
     * @return
     */
    private boolean checkApplyExists(String hiddenDangerId){
        List<TBHiddenDangerApplyEntity> entitys = systemService.findByProperty(TBHiddenDangerApplyEntity.class, "hiddenDangerId.id",hiddenDangerId);
        if (entitys != null && entitys.size() > 0) {
            for(TBHiddenDangerApplyEntity bean : entitys){
                if(!(bean.getDealStatus().isEmpty())) {
                    if ((bean.getDealStatus()).equals("1")) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    /**更新申请*/
    private void applyUpdate(TBHiddenDangerApplyEntity tbHiddenDangerApply) throws IllegalAccessException {
        String hiddenDangerId = tbHiddenDangerApply.getHiddenDangerId().getId();
        List<TBHiddenDangerApplyEntity> tempList = systemService.findByProperty(TBHiddenDangerApplyEntity.class,"hiddenDangerId.id",hiddenDangerId);
        for(TBHiddenDangerApplyEntity bean : tempList){
            if(!(bean.getDealStatus().isEmpty())) {
                if ((bean.getDealStatus()).equals("1")) {
                    try {
                        MyBeanUtils.copyBeanNotNull2Bean(tbHiddenDangerApply,bean);
                        tbHiddenDangerApplyService.saveOrUpdate(bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    /**
     * 延期申请处理
     */
    @RequestMapping(params = "goDealApply")
    public ModelAndView goDealApply(HttpServletRequest req){
        String id = req.getParameter("id");
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerApplyEntity.class);
        cq.createAlias("hiddenDangerId","hiddenDangerExam");
        cq.createAlias("hiddenDangerExam.handleEntity","hiddenDangerHandle");
        cq.eq("hiddenDangerHandle.id",id);
        cq.eq("dealStatus","1");
        cq.add();
        List<TBHiddenDangerApplyEntity> tempList = systemService.getListByCriteriaQuery(cq,false);
        if(tempList.size()>0&&tempList!=null){
            req.setAttribute("tBHiddenDangerApplyPage",tempList.get(0));
        }
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandle-dealApply");
    }
    /**
     * 延期申请处理
     */
    @RequestMapping(params = "doDealApply")
    @ResponseBody
    public AjaxJson doDealApply(TBHiddenDangerApplyEntity template, HttpServletRequest req){
        AjaxJson j = new AjaxJson();
        TBHiddenDangerApplyEntity targetApply = systemService.getEntity(TBHiddenDangerApplyEntity.class,template.getId());
        TBHiddenDangerExamEntity targetHiddenDanger = systemService.getEntity(TBHiddenDangerExamEntity.class,template.getHiddenDangerId().getId());
        String message="隐患已按照申请延期成功！";
        String dealFlag = req.getParameter("dealFlag");
        String timeTrueFlag = req.getParameter("timeTrueFlag");
        if(dealFlag!=null&&StringUtils.isNotBlank(dealFlag)){
            if(dealFlag.equals("1")){
                template.setDealStatus("2");
                template.setRefuseReason(null);
                if(timeTrueFlag!=null&&StringUtils.isNotBlank(timeTrueFlag)){
                    if(timeTrueFlag.equals("1")){
//                   String limitDateNewTrue = req.getParameter("limitDateNewTrue");
                        if(template.getLimitDateNewTrue()!=null&&StringUtils.isNotBlank(template.getLimitDateNewTrue().toString())){
                            targetHiddenDanger.setLimitDate(template.getLimitDateNewTrue());
                            systemService.saveOrUpdate(targetHiddenDanger);
                            message = "隐患已按照指定日期延期！";
                            try{
                                MyBeanUtils.copyBeanNotNull2Bean(template, targetApply);
                                tbHiddenDangerApplyService.saveOrUpdate(targetApply);
                            }catch(Exception e){

                            }
                        }
                    }else{
                        targetHiddenDanger.setLimitDate(targetApply.getLimitDateNew());
                        systemService.saveOrUpdate(targetHiddenDanger);
                        targetApply.setLimitDateNewTrue(targetApply.getLimitDateNew());
                        targetApply.setDealStatus("2");
                        tbHiddenDangerApplyService.saveOrUpdate(targetApply);
                    }
                }
            }else{
                targetApply.setDealStatus("00");
                targetApply.setRefuseReason(template.getRefuseReason());
                tbHiddenDangerApplyService.saveOrUpdate(targetApply);
                message = "隐患延期申请已经被驳回！";
            }
        }

        //国家局上报 更新sf_hidden_rel关联表
        systemService.executeSql("UPDATE `sf_hidden_rel` set state_flag='"+Constants.GJJ_STATE_FLAG_2+"' where hidden_id='"+targetHiddenDanger.getId()+"'");
        j.setMsg(message);
        return j;
    }

    /**
     * 整改
     */
    public void  saveIssues(TBHiddenDangerHandleEntity template, TBHiddenDangerHandleEntity Issue){
        if (StringUtil.isNotEmpty(Issue.getId())) {
            TBHiddenDangerHandleEntity t =tBHiddenDangerHandleService.get(TBHiddenDangerHandleEntity.class, Issue.getId());
            try {
                String checkStatus = ResourceUtil.getParameter("checkStatus");
                if ("0".equals(checkStatus)) {
                    t.setHandlelStatus(Constants.HANDELSTATUS_ROLLBACK_REPORT);
                    t.setRollBackRemark(template.getRollBackRemark());
                } else {
                    t.setHandlelStatus(Constants.HANDELSTATUS_REVIEW);
                    t.setModifyDate(template.getModifyDate());
                    t.setModifyMan(template.getModifyMan());
                    t.setModifyShift(template.getModifyShift());
                    t.setRectMeasures(template.getRectMeasures());

                }
                tBHiddenDangerHandleService.saveOrUpdate(t);
                TBHiddenDangerExamEntity tBHiddenDangerExamTemp = t.getHiddenDanger();

                if (!"0".equals(checkStatus)) {
                    //发送短信通知，通知复查人
                    try {

                        String createBy = tBHiddenDangerExamTemp.getCreateBy();
                        //获取录入人电话
                        List<String> mobilePhones = systemService.findListbySql("select u.mobilePhone from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.username='" + createBy + "'");
                        //获取录入人id
                        List<String> createbyid=systemService.findListbySql("select u.id from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.username='" + createBy + "'");
                        //获取录入人openid
                        String openidsql="select openid from t_b_WexinOpenId  where userId='"+createbyid.get(0)+"'";
                        List<String> openid=systemService.findListbySql(openidsql);
                        String createTime = DateUtils.formatDate(tBHiddenDangerExamTemp.getCreateDate(), "yyyy-MM-dd HH:mm");
                        String addressName = tBHiddenDangerExamTemp.getAddress().getAddress();
                        String problemDesc = tBHiddenDangerExamTemp.getProblemDesc();
                        String limitDate = DateUtils.formatDate(tBHiddenDangerExamTemp.getLimitDate(), "yyyy-MM-dd");
                        TSUser modifyMan = systemService.get(TSUser.class, Issue.getModifyMan());
                        String modifyManName = Issue.getModifyMan();
                        if (modifyMan != null) {
                            modifyManName = modifyMan.getRealName();
                        }
                        String rectMeasures = Issue.getRectMeasures();
                        String modifyDate = DateUtils.formatDate(Issue.getModifyDate(), "yyyy-MM-dd");
                        String content = "【双防平台】通知：尊敬的双防用户，您好！您发布的隐患已整改完成：" + createTime +
                                " 地点：" + addressName + "，内容：" + problemDesc +
                                "，限期日期为：" + limitDate + "，整改人为：" + modifyManName + "，整改内容为：" + rectMeasures + ",整改完成时间为：" + modifyDate;
                        if(openid.size()>0){
                            for (String openids : openid) {

                                postCheckTemplate(openids,createTime,addressName,problemDesc,limitDate,modifyManName,rectMeasures,modifyDate);
                            }
                        }else if (mobilePhones != null && StringUtils.isNotBlank(mobilePhones.get(0))) {
                            SMSSenderUtil.sendSMS(content,mobilePhones.get(0), Constants.SMS_TYPE_HIDDEN_DANGER_RECTIFY);
                        }
                        //企业微信通知
                        List<String> weChatPhones=systemService.findListbySql("select u.weChatPhone from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.username='" + createBy + "'");
                        if(weChatPhones != null && weChatPhones.size()>0 && StringUtils.isNotBlank(weChatPhones.get(0))){
                            WebChatUtil.sendWeChatMessageToUser(weChatPhones.get(0),content);
                        }
                    } catch (Exception e) {
                        logger.error(ExceptionUtil.getExceptionMessage(e));
                    }
                }

                //删除隐患升级定时任务
                qrtzManagerServiceI.removeJob(scheduler,t.getHiddenDanger().getId());
                riskUpgradeService.execute(t.getHiddenDanger().getId());
                if (Constants.HANDELSTATUS_REVIEW.equals(t.getHandlelStatus())) {
                    systemService.addLog("问题\""+t.getId()+"\"整改信息更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
                } else {
                    systemService.addLog("问题\""+t.getId()+"\"驳回成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
                }

                if ("0".equals(checkStatus)) {
                    tBHiddenDangerHandleLogService.addLog(t.getHiddenDanger().getId(), Globals.Log_Type_RETURN,"隐患整改退回");
                } else {
                    tBHiddenDangerHandleLogService.addLog(t.getHiddenDanger().getId(), Globals.Log_Type_RECTIFY,"隐患整改通过");
                }

                int handleStep = 0;
                TBHiddenDangerExamEntity tBHiddenDangerExam = t.getHiddenDanger();
                CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleStepEntity.class);
                cq.eq("hiddenDanger.id",tBHiddenDangerExam.getId());
                cq.addOrder("handleStep", SortDirection.asc);
                cq.add();
                List<TBHiddenDangerHandleStepEntity> handleStepList = systemService.getListByCriteriaQuery(cq,false);
                if(!handleStepList.isEmpty() && handleStepList.size() > 0 ){
                    handleStep = handleStepList.get((handleStepList.size()-1)).getHandleStep() + 1;

                    TBHiddenDangerHandleStepEntity handleStepEntity = new TBHiddenDangerHandleStepEntity();
                    if ("0".equals(checkStatus)) {
                        handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
                        handleStepEntity.setHandleStep(handleStep);
                        handleStepEntity.setHandleDate(new Date());
                        TSUser user = ResourceUtil.getSessionUserName();
                        handleStepEntity.setHandleMan(user.getRealName());
                        handleStepEntity.setRemark(template.getRollBackRemark());
                        handleStepEntity.setHandleStatus(t.getHandlelStatus());
                    } else {
                        handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
                        handleStepEntity.setHandleStep(handleStep);
                        handleStepEntity.setHandleDate(getHandleTime(template.getModifyDate()));
                        TSBaseUser user = systemService.get(TSBaseUser.class, template.getModifyMan());
                        handleStepEntity.setHandleMan(user!=null?user.getRealName():"");
                        handleStepEntity.setHandleStatus(t.getHandlelStatus());
                    }

                    systemService.save(handleStepEntity);
                }
            } catch (Exception e) {
                e.printStackTrace();
                systemService.addLog("问题整改信息更新失败："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPLOAD);
            }
        }
    }
    /**
     * 整改通过
     * @param assessIssue
     * @return
     */
    @RequestMapping(params = "saveModifyIssues")
    @ResponseBody
    public AjaxJson saveModifyIssues(TBHiddenDangerHandleEntity assessIssue){
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(assessIssue.getId())) {
            TBHiddenDangerHandleEntity t =tBHiddenDangerHandleService.get(TBHiddenDangerHandleEntity.class, assessIssue.getId());
            try {
                String checkStatus = ResourceUtil.getParameter("checkStatus");
                if ("0".equals(checkStatus)) {
                    t.setHandlelStatus(Constants.HANDELSTATUS_ROLLBACK_REPORT);
                    t.setRollBackRemark(assessIssue.getRollBackRemark());
                    j.setMsg("隐患驳回成功！");

                    /**将矿版整改的状态同步到集团*/
                    try{
                        if (StringUtil.isNotEmpty(assessIssue.getId())) {
                            //tBHiddenDangerHandleService.hiddenDangerReportToGroup(assessIssue.getId());
                            //tBHiddenDangerHandleService.reportToGroupByThread(assessIssue.getId());
                            UploadThread uploadThread = new UploadThread(assessIssue.getId());
                            Thread thread = new Thread(uploadThread);
                            thread.start();
                        }
                    }catch (Exception e){/**这个try-catch为了防止当网络出现问题的时候，导致系统报错无法整改的问题*/ System.out.println("隐患整改状态同步到集团失败");}
                } else {
                    t.setHandlelStatus(Constants.HANDELSTATUS_REVIEW);
                    t.setModifyDate(assessIssue.getModifyDate());
                    t.setModifyMan(assessIssue.getModifyMan());
                    t.setModifyShift(assessIssue.getModifyShift());
                    t.setRectMeasures(assessIssue.getRectMeasures());
                    j.setMsg("隐患整改成功！");

                    /**将矿版整改的状态同步到集团*/
                    try{
                        if (StringUtil.isNotEmpty(assessIssue.getId())) {
                            //tBHiddenDangerHandleService.hiddenDangerReportToGroup(assessIssue.getId());
                            //tBHiddenDangerHandleService.reportToGroupByThread(assessIssue.getId());
                            UploadThread uploadThread = new UploadThread(assessIssue.getId());
                            Thread thread = new Thread(uploadThread);
                            thread.start();
                        }
                    }catch (Exception e){/**这个try-catch为了防止当网络出现问题的时候，导致系统报错无法整改的问题*/ System.out.println("隐患整改状态同步到集团失败");}
                }
                //国家局上报 更新sf_hidden_rel关联表
                systemService.executeSql("UPDATE `sf_hidden_rel` set state_flag='"+Constants.GJJ_STATE_FLAG_2+"' where hidden_id='"+t.getHiddenDanger().getId()+"'");
                tBHiddenDangerHandleService.saveOrUpdate(t);
                TBHiddenDangerExamEntity tBHiddenDangerExam = t.getHiddenDanger();

                if (!"0".equals(checkStatus)){
                    //发送短信通知，通知复查人
                    try {

                        String createBy = tBHiddenDangerExam.getCreateBy();
                        //获取录入人电话
                        List<String> mobilePhones = systemService.findListbySql("select u.mobilePhone from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.username='" + createBy + "'");
                        List<String> createbyid=systemService.findListbySql("select u.id from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.username='" + createBy + "'");
                        String openidsql = "select openid from t_b_WexinOpenId  where userId='";
                        if (createbyid!=null && createbyid.size()>0){
                            openidsql += createbyid.get(0);
                        }
                        openidsql += "'";

                        List<String> openid=systemService.findListbySql(openidsql);
                        String createTime = DateUtils.formatDate(tBHiddenDangerExam.getCreateDate(), "yyyy-MM-dd HH:mm");
                        String addressName = tBHiddenDangerExam.getAddress().getAddress();
                        String problemDesc = tBHiddenDangerExam.getProblemDesc();
                        String limitDate = DateUtils.formatDate(tBHiddenDangerExam.getLimitDate(), "yyyy-MM-dd");
                        TSUser modifyMan = systemService.get(TSUser.class, assessIssue.getModifyMan());
                        String modifyManName = assessIssue.getModifyMan();
                        if (modifyMan != null) {
                            modifyManName = modifyMan.getRealName();
                        }
                        String rectMeasures = assessIssue.getRectMeasures();
                        String modifyDate = DateUtils.formatDate(assessIssue.getModifyDate(), "yyyy-MM-dd");
                        String content = "【双防平台】通知：尊敬的双防用户，您好！您发布的隐患已整改完成：" + createTime +
                                " 地点：" + addressName + "，内容：" + problemDesc +
                                "，限期日期为：" + limitDate + "，整改人为：" + modifyManName + "，整改内容为：" + rectMeasures + ",整改完成时间为：" + modifyDate;
                        if(openid.size()>0){
                            for (String openids : openid) {
                                postCheckTemplate(openids,createTime,addressName,problemDesc,limitDate,modifyManName,rectMeasures,modifyDate);
                            }
                        }else if (mobilePhones != null && mobilePhones.size()>0 && StringUtils.isNotBlank(mobilePhones.get(0))) {
                            SMSSenderUtil.sendSMS(content,mobilePhones.get(0), Constants.SMS_TYPE_HIDDEN_DANGER_RECTIFY);
                        }
                        //企业微信通知
                        List<String> weChatPhones=systemService.findListbySql("select u.weChatPhone from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.username='" + createBy + "'");
                        if(weChatPhones != null && weChatPhones.size()>0 && StringUtils.isNotBlank(weChatPhones.get(0))){
                            WebChatUtil.sendWeChatMessageToUser(weChatPhones.get(0),content);
                        }
                    } catch (Exception e) {
                        logger.error(ExceptionUtil.getExceptionMessage(e));
                    }
                }
                //删除隐患升级定时任务
                qrtzManagerServiceI.removeJob(scheduler, t.getHiddenDanger().getId());
                riskUpgradeService.execute(t.getHiddenDanger().getId());

                if (Constants.HANDELSTATUS_REVIEW.equals(t.getHandlelStatus())) {
                    systemService.addLog("问题\""+t.getId()+"\"整改信息更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
                } else {
                    systemService.addLog("问题\""+t.getId()+"\"驳回成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
                }

                if ("0".equals(checkStatus)) {
                    tBHiddenDangerHandleLogService.addLog(t.getHiddenDanger().getId(), Globals.Log_Type_RETURN,"隐患整改退回");
                } else {
                    tBHiddenDangerHandleLogService.addLog(t.getHiddenDanger().getId(), Globals.Log_Type_RECTIFY,"隐患整改通过");
                }

                int handleStep = 0;

                CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleStepEntity.class);
                cq.eq("hiddenDanger.id",tBHiddenDangerExam.getId());
                cq.addOrder("handleStep", SortDirection.asc);
                cq.add();
                List<TBHiddenDangerHandleStepEntity> handleStepList = systemService.getListByCriteriaQuery(cq,false);
                if(!handleStepList.isEmpty() && handleStepList.size() > 0 ){
                    handleStep = handleStepList.get((handleStepList.size()-1)).getHandleStep() + 1;

                    TBHiddenDangerHandleStepEntity handleStepEntity = new TBHiddenDangerHandleStepEntity();
                    if ("0".equals(checkStatus)) {
                        handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
                        handleStepEntity.setHandleStep(handleStep);
                        handleStepEntity.setHandleDate(new Date());
                        TSUser user = ResourceUtil.getSessionUserName();
                        handleStepEntity.setHandleMan(user.getRealName());
                        handleStepEntity.setRemark(assessIssue.getRollBackRemark());
                        handleStepEntity.setHandleStatus(t.getHandlelStatus());
                    } else {
                        handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
                        handleStepEntity.setHandleStep(handleStep);
                        handleStepEntity.setHandleDate(getHandleTime(t.getModifyDate()));
                        TSBaseUser user = systemService.get(TSBaseUser.class, t.getModifyMan());
                        handleStepEntity.setHandleMan(user!=null?user.getRealName():"");
                        handleStepEntity.setHandleStatus(t.getHandlelStatus());
                    }

                    systemService.save(handleStepEntity);
                }
            } catch (Exception e) {
                e.printStackTrace();
                j.setMsg("整改失败！");
                systemService.addLog("问题整改信息更新失败："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPLOAD);
            }
        }
        return j;
    }

    @RequestMapping(params = "queryTimeOutList")
    public ModelAndView queryTimeOutList(HttpServletRequest request) {
        /******************************判断是不是阳光管理员	然后决定是否显示隐藏按钮**************************/
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        if(isSunRole){
            request.setAttribute("isSunAdmin", "YGADMIN");
        }else{
            request.setAttribute("isSunAdmin", "common");
        }
        /***************************************************************************************************************/

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNow = sdf.format(d);
        request.setAttribute("dateNow",dateNow);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/timeoutList");
    }

    /**
     * 隐患复查页面
     * @param tBHiddenDangerHandle
     * @param req
     * @return
     */
    @RequestMapping(params = "goReviewIssues")
    public ModelAndView goReviewIssues (TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest req) {
        String beixulou = ResourceUtil.getConfigByName("beixulou");
        req.setAttribute("beixulou",beixulou);
        if (StringUtil.isNotEmpty(tBHiddenDangerHandle.getId())) {
            tBHiddenDangerHandle = tBHiddenDangerHandleService.getEntity(TBHiddenDangerHandleEntity.class, tBHiddenDangerHandle.getId());
            req.setAttribute("tBHiddenDangerHandle", tBHiddenDangerHandle);
        }
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandle-repart");
    }

    /**
     * 隐患批量复查页面
     * @param req
     * @return
     */
    @RequestMapping(params = "goReviewAllIssues")
    public ModelAndView goReviewAllIssues (HttpServletRequest req) {
        String beixulou = ResourceUtil.getConfigByName("beixulou");
        req.setAttribute("beixulou",beixulou);
        String ids = req.getParameter("ids");
        int indexFirst = ids.indexOf(",");
        String id = ids.substring(0,indexFirst);
        TBHiddenDangerHandleEntity tBHiddenDangerHandle = new TBHiddenDangerHandleEntity();
        tBHiddenDangerHandle.setId(id);
        if (StringUtil.isNotEmpty(tBHiddenDangerHandle.getId())) {
            tBHiddenDangerHandle = tBHiddenDangerHandleService.getEntity(TBHiddenDangerHandleEntity.class, tBHiddenDangerHandle.getId());
            tBHiddenDangerHandle.setIds(ids);
            req.setAttribute("tBHiddenDangerHandle", tBHiddenDangerHandle);
        }
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandle-repartAll");
    }


    /**
     * 保存问题复查数据
     * @param assessIssue
     * @return
     */
    @RequestMapping(params = "saveRepart")
    @ResponseBody
    public AjaxJson saveRepart(TBHiddenDangerHandleEntity assessIssue, HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        TBHiddenDangerHandleEntity t =tBHiddenDangerHandleService.get(TBHiddenDangerHandleEntity.class, assessIssue.getId());
        try {
            if("0".equals(assessIssue.getReviewResult())){
                t.setReviewMan(null);
                t.setReviewDate(null);
                t.setReviewShift(null);
                t.setReviewResult(null);
                t.setReviewReport(null);
                t.setModifyDate(null);
                t.setModifyMan(null);
                t.setModifyShift(null);
                t.setRectMeasures(null);
                t.setRollBackRemark(null);
                //复查不通过，退回整改
                t.setHandlelStatus(Constants.HANDELSTATUS_ROLLBACK_CHECK);
                //复查不合格，退回整改 是否添加升级任务，待确定
                TBHiddenDangerExamEntity hiddenDangerExamEntity = t.getHiddenDanger();
                String limitDate = request.getParameter("limitDate");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date limit = sdf.parse(limitDate);
                hiddenDangerExamEntity.setLimitDate(limit);
                systemService.saveOrUpdate(hiddenDangerExamEntity);

                //TODO 添加升级任务
                String job_name = hiddenDangerExamEntity.getId();
                Date limitDate2 = hiddenDangerExamEntity.getLimitDate();
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                String limitStr = sdf.format(limitDate2);
                limitStr = limitStr + " 23:59:59";
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    limitDate2 = sdf.parse(limitStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(limitDate2);
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH) + 1;
                int day = calendar.get(calendar.DATE);
                int hour = calendar.get(calendar.HOUR_OF_DAY);
                int minute = calendar.get(calendar.MINUTE);
                int second = calendar.get(calendar.SECOND);

                StringBuffer sb = new StringBuffer();
                sb.append(second).append(" ").append(minute).append(" ").append(hour).append(" ").append(day).append(" ").append(month).append(" ").append("? ").append(year);
                riskUpgradeService.execute(t.getHiddenDanger().getId());
                try{
                    qrtzManagerServiceI.addJob(scheduler,hiddenDangerExamEntity.getId(), job_name, QuartzJob.class, sb.toString());
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                t.setReviewMan(assessIssue.getReviewMan());
                t.setReviewManOther(assessIssue.getReviewManOther());
                t.setReviewDate(assessIssue.getReviewDate());
                t.setReviewShift(assessIssue.getReviewShift());
                t.setReviewReport(assessIssue.getReviewReport());
                t.setReviewResult(assessIssue.getReviewResult());
                t.setHandlelStatus(Constants.REVIEWSTATUS_PASS);
                //隐患等级降为初始等级
                TBHiddenDangerExamEntity hiddenDangerExamEntity = t.getHiddenDanger();
                hiddenDangerExamEntity.setHiddenNature(hiddenDangerExamEntity.getHiddenNatureOriginal());
                systemService.saveOrUpdate(hiddenDangerExamEntity);
                riskUpgradeService.execute(t.getHiddenDanger().getId());
            }
            tBHiddenDangerHandleService.saveOrUpdate(t);

            //国家局上报 更新sf_hidden_rel关联表
            systemService.executeSql("UPDATE `sf_hidden_rel` set state_flag='"+Constants.GJJ_STATE_FLAG_2+"' where hidden_id='"+t.getHiddenDanger().getId()+"'");
            /**将矿版整改的状态同步到集团*/
            try{
                if (StringUtil.isNotEmpty(assessIssue.getId())) {
                    //tBHiddenDangerHandleService.hiddenDangerReportToGroup(assessIssue.getId());
                    //tBHiddenDangerHandleService.reportToGroupByThread(assessIssue.getId());
                    UploadThread uploadThread = new UploadThread(assessIssue.getId());
                    Thread thread = new Thread(uploadThread);
                    thread.start();
                }
            }catch (Exception e){/**这个try-catch为了防止当网络出现问题的时候，导致系统报错无法整改的问题*/ System.out.println("隐患整改状态同步到集团失败");}
            systemService.addLog("问题\""+t.getId()+"\"复查信息更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
            if("0".equals(assessIssue.getReviewResult())){
                tBHiddenDangerHandleLogService.addLog(t.getHiddenDanger().getId(), Globals.Log_Type_RETURN,"隐患复查退回");
            }else{
                tBHiddenDangerHandleLogService.addLog(t.getHiddenDanger().getId(), Globals.Log_Type_REVIEW,"隐患复查通过");
            }
            int handleStep = 0;
            TBHiddenDangerExamEntity tBHiddenDangerExam = t.getHiddenDanger();
            CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleStepEntity.class);
            cq.eq("hiddenDanger.id",tBHiddenDangerExam.getId());
            cq.addOrder("handleStep", SortDirection.asc);
            cq.add();
            List<TBHiddenDangerHandleStepEntity> handleStepList = systemService.getListByCriteriaQuery(cq,false);
            if(!handleStepList.isEmpty() && handleStepList.size() > 0 ){
                handleStep = handleStepList.get((handleStepList.size()-1)).getHandleStep() + 1;

                TBHiddenDangerHandleStepEntity handleStepEntity = new TBHiddenDangerHandleStepEntity();
                handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
                handleStepEntity.setHandleStep(handleStep);
                handleStepEntity.setHandleDate(getHandleTime(t.getReviewDate()));
                TSBaseUser user = systemService.get(TSBaseUser.class, t.getReviewMan());
                handleStepEntity.setHandleMan(user!=null?user.getRealName():"");
                handleStepEntity.setHandleStatus(t.getHandlelStatus());
                handleStepEntity.setRemark(assessIssue.getReviewReport());

                systemService.save(handleStepEntity);
            }

        } catch (Exception e) {
            e.printStackTrace();
            systemService.addLog("问题复查信息更新失败："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPLOAD);
        }
        return j;
    }
    /**
     * 批量复查通过
     */
    @RequestMapping(params = "saveAllRepart")
    @ResponseBody
    public AjaxJson saveAllRepart(TBHiddenDangerHandleEntity assessIssue, HttpServletRequest request){
        String ids = request.getParameter("ids");
        AjaxJson j = new AjaxJson();
        for(String id:ids.split(",")) {
            TBHiddenDangerHandleEntity t = tBHiddenDangerHandleService.get(TBHiddenDangerHandleEntity.class, id);
            try {
                if ("0".equals(assessIssue.getReviewResult())) {
                    t.setReviewMan(null);
                    t.setReviewDate(null);
                    t.setReviewShift(null);
                    t.setReviewResult(null);
                    t.setReviewReport(null);
                    t.setModifyDate(null);
                    t.setModifyMan(null);
                    t.setModifyShift(null);
                    t.setRectMeasures(null);
                    t.setRollBackRemark(null);
                    //复查不通过，退回整改
                    t.setHandlelStatus(Constants.HANDELSTATUS_ROLLBACK_CHECK);
                    //复查不合格，退回整改 是否添加升级任务，待确定
                    TBHiddenDangerExamEntity hiddenDangerExamEntity = t.getHiddenDanger();
                    String limitDate = request.getParameter("limitDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date limit = sdf.parse(limitDate);
                    hiddenDangerExamEntity.setLimitDate(limit);
                    systemService.saveOrUpdate(hiddenDangerExamEntity);
                    //国家局上报 更新sf_hidden_rel关联表
                    systemService.executeSql("UPDATE `sf_hidden_rel` set state_flag='"+Constants.GJJ_STATE_FLAG_2+"' where hidden_id='"+t.getHiddenDanger().getId()+"'");
                    //TODO 添加升级任务
                    String job_name = hiddenDangerExamEntity.getId();
                    Date limitDate2 = hiddenDangerExamEntity.getLimitDate();
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String limitStr = sdf.format(limitDate2);
                    limitStr = limitStr + " 23:59:59";
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        limitDate2 = sdf.parse(limitStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(limitDate2);
                    int year = calendar.get(calendar.YEAR);
                    int month = calendar.get(calendar.MONTH) + 1;
                    int day = calendar.get(calendar.DATE);
                    int hour = calendar.get(calendar.HOUR_OF_DAY);
                    int minute = calendar.get(calendar.MINUTE);
                    int second = calendar.get(calendar.SECOND);

                    StringBuffer sb = new StringBuffer();
                    sb.append(second).append(" ").append(minute).append(" ").append(hour).append(" ").append(day).append(" ").append(month).append(" ").append("? ").append(year);
                    riskUpgradeService.execute(t.getHiddenDanger().getId());
                    try {
                        qrtzManagerServiceI.addJob(scheduler, hiddenDangerExamEntity.getId(), job_name, QuartzJob.class, sb.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    t.setReviewMan(assessIssue.getReviewMan());
                    t.setReviewManOther(assessIssue.getReviewManOther());
                    t.setReviewDate(assessIssue.getReviewDate());
                    t.setReviewShift(assessIssue.getReviewShift());
                    t.setReviewReport(assessIssue.getReviewReport());
                    t.setReviewResult(assessIssue.getReviewResult());
                    t.setHandlelStatus(Constants.REVIEWSTATUS_PASS);
                    //隐患等级降为初始等级
                    TBHiddenDangerExamEntity hiddenDangerExamEntity = t.getHiddenDanger();
                    hiddenDangerExamEntity.setHiddenNature(hiddenDangerExamEntity.getHiddenNatureOriginal());
                    systemService.saveOrUpdate(hiddenDangerExamEntity);
                    riskUpgradeService.execute(t.getHiddenDanger().getId());
                }
                tBHiddenDangerHandleService.saveOrUpdate(t);
                /**将矿版整改的状态同步到集团*/
                try {
                    if (StringUtil.isNotEmpty(assessIssue.getId())) {
                        //tBHiddenDangerHandleService.hiddenDangerReportToGroup(assessIssue.getId());
                        //tBHiddenDangerHandleService.reportToGroupByThread(assessIssue.getId());
                        UploadThread uploadThread = new UploadThread(assessIssue.getId());
                        Thread thread = new Thread(uploadThread);
                        thread.start();
                    }
                } catch (Exception e) {/**这个try-catch为了防止当网络出现问题的时候，导致系统报错无法整改的问题*/System.out.println("隐患整改状态同步到集团失败");
                }
                systemService.addLog("问题\"" + t.getId() + "\"复查信息更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);

                int handleStep = 0;
                TBHiddenDangerExamEntity tBHiddenDangerExam = t.getHiddenDanger();
                CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleStepEntity.class);
                cq.eq("hiddenDanger.id", tBHiddenDangerExam.getId());
                cq.addOrder("handleStep", SortDirection.asc);
                cq.add();
                List<TBHiddenDangerHandleStepEntity> handleStepList = systemService.getListByCriteriaQuery(cq, false);
                if (!handleStepList.isEmpty() && handleStepList.size() > 0) {
                    handleStep = handleStepList.get((handleStepList.size() - 1)).getHandleStep() + 1;

                    TBHiddenDangerHandleStepEntity handleStepEntity = new TBHiddenDangerHandleStepEntity();
                    handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
                    handleStepEntity.setHandleStep(handleStep);
                    handleStepEntity.setHandleDate(getHandleTime(t.getReviewDate()));
                    TSBaseUser user = systemService.get(TSBaseUser.class, t.getReviewMan());
                    handleStepEntity.setHandleMan(user != null ? user.getRealName() : "");
                    handleStepEntity.setHandleStatus(t.getHandlelStatus());
                    handleStepEntity.setRemark(assessIssue.getReviewReport());

                    systemService.save(handleStepEntity);
                }

            } catch (Exception e) {
                e.printStackTrace();
                systemService.addLog("问题复查信息更新失败：" + e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPLOAD);
            }
        }
        return j;
    }
    /**
     * 获取当前状态
     * @param id
     * @return
     */
    @RequestMapping(params = "checkHandelStatus")
    @ResponseBody
    public String checkHandelStatus(String id){
        TBHiddenDangerHandleEntity entity = systemService.get(TBHiddenDangerHandleEntity.class, id);
        if (entity != null){
            return entity.getHandlelStatus();
        }
        return null;
    }

    /**
     * 多条隐患状态判断
     * @param ids
     * @return
     */
    @RequestMapping(params = "checkHandleStatusSum")
    @ResponseBody
    public boolean checkHandleStatusSum(String ids){
        Boolean flag = true;
        for(String id:ids.split(",")){
            TBHiddenDangerHandleEntity entity = systemService.get(TBHiddenDangerHandleEntity.class, id);
            if (entity != null){
                if(!(entity.getHandlelStatus().equals("1")||entity.getHandlelStatus().equals("4"))){
                    flag = false;
                    return flag;
                }
            }
        }
        return flag;
    }

    /**
     * 问题复查导出
     * @param tBHiddenDangerHandle
     * @param request
     * @param response
     * @param dataGrid
     * @param modelMap
     * @return
     */
    @RequestMapping(params = "repartExportXls")
    public String repartExportXls(TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class, dataGrid);
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
        boolean isAJY= false;
        if(roleList != null && !roleList.isEmpty()){
            for(TSRoleUser ru : roleList){
                TSRole role = ru.getTSRole();
                if(role != null && role.getRoleName().equals("安监员")){
                    isAJY = true;
                    break;
                }
            }
        }
        try{
            //自定义追加查询条件
            cq.createAlias("hiddenDanger","hiddenDanger");
            String roleName = sessionUser.getUserKey();
            if (!isAdmin&&!isAJY&&!DicUtil.reviewPower(sessionUser.getId())){
                StringBuffer sb = new StringBuffer();
                sb.append("select user.username from t_s_base_user as user,t_s_user_org as rel ").append("where user.id=rel.user_id ");
                String departId = sessionUser.getCurrentDepart().getId();
                if(StringUtils.isNotBlank(departId)){
                    sb.append("and org_id='").append(departId).append("'");
                }
                List<String> userNameList = systemService.findListbySql(sb.toString());
                cq.or(Restrictions.in("createBy",userNameList.toArray()), Restrictions.eq("hiddenDanger.manageDutyUnit.id", sessionUser.getCurrentDepart().getId()));
            } else if(StringUtils.isNotBlank(roleName) && roleName.indexOf("井口信息办")>=0){
                cq.add(
                        Restrictions.or(Restrictions.eq("hiddenDanger.isFromJkxxb", "1"),
                                Restrictions.eq("createBy", sessionUser.getUserName()))
                );
            }else{

            }

            String  queryHandleStatusTem = request.getParameter("queryHandleStatus");
            if(StringUtils.isNotBlank(queryHandleStatusTem)){
                if(Constants.REVIEWSTATUS_PASS.equals(queryHandleStatusTem)){
                    cq.eq("handlelStatus", Constants.REVIEWSTATUS_PASS);
                }else if(Constants.HANDELSTATUS_REVIEW.equals(queryHandleStatusTem)){
                    cq.eq("handlelStatus", Constants.HANDELSTATUS_REVIEW);
                }else if(Constants.HANDELSTATUS_ROLLBACK_CHECK.equals(queryHandleStatusTem)){
                    cq.eq("handlelStatus", Constants.HANDELSTATUS_ROLLBACK_CHECK);//复查未通过，退回整改
                }
            }else{
                cq.eq("handlelStatus", Constants.HANDELSTATUS_REVIEW);
            }


            String findTimeStart = request.getParameter("hiddenDanger.examDate_begin");
            String findTimeEnd = request.getParameter("hiddenDanger.examDate_end");
            if(StringUtils.isNotBlank(findTimeStart)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.ge("hiddenDanger.examDate",sdf.parse(findTimeStart));
            }
            if(StringUtils.isNotBlank(findTimeEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.le("hiddenDanger.examDate",sdf.parse(findTimeEnd));
            }
            String modifyDateBegin = request.getParameter("modifyDate_begin");
            String modifyDateEnd = request.getParameter("modifyDate_end");
            if(StringUtils.isNotBlank(modifyDateEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cq.le("modifyDate",sdf.parse(modifyDateEnd+" 23:59:59"));
            }
            if(StringUtils.isNotBlank(modifyDateBegin)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cq.ge("modifyDate",sdf.parse(modifyDateBegin+" 00:00:00"));
            }
            String checkPeopleName = request.getParameter("modifyMan");
            if(StringUtils.isNotBlank(checkPeopleName)){
                cq.like("modifyMan","%"+checkPeopleName+"%");
            }
            String dutyUnit = request.getParameter("hiddenDanger.dutyUnit.departname");
            if(StringUtils.isNotBlank(dutyUnit)){
                cq.eq("hiddenDanger.dutyUnit.id",dutyUnit);
            }
            String examType = request.getParameter("hiddenDanger.examType");
            if(StringUtils.isNotBlank(examType)){
                if("jkxxblr".equals(examType)){
                    cq.eq("hiddenDanger.isFromJkxxb","1");
                }else{
                    cq.eq("hiddenDanger.examType",examType);
                }
            }
            String fillCaredManId = request.getParameter("hiddenDanger.fillCardMan.id");
            if(StringUtils.isNotBlank(fillCaredManId)){
                //cq.eq("hiddenDanger.fillCardMan.id",fillCaredManId);
                cq.or(
                        Restrictions.eq("hiddenDanger.fillCardMan.id",fillCaredManId),
                        Restrictions.or(
                                Restrictions.eq("hiddenDanger.fillCardManId",fillCaredManId),
                                Restrictions.or(
                                        Restrictions.like("hiddenDanger.fillCardManId",fillCaredManId+",%"),
                                        Restrictions.or(
                                                Restrictions.like("hiddenDanger.fillCardManId","%,"+fillCaredManId),
                                                Restrictions.like("hiddenDanger.fillCardManId","%,"+fillCaredManId+",%")
                                        )
                                )
                        )
                );
            }

            String examShift = request.getParameter("hiddenDanger.shift");
            if(StringUtils.isNotBlank(examShift)){
                cq.eq("hiddenDanger.shift",examShift);
            }
            String hiddenNumber = request.getParameter("hiddenDanger.hiddenNumber");
            if(StringUtil.isNotEmpty(hiddenNumber)){
                cq.like("hiddenDanger.hiddenNumber","%"+hiddenNumber+"%");

            }
        }catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        cq.addOrder("hiddenDanger.examDate", SortDirection.desc);
        cq.add();
        List<TBHiddenDangerHandleEntity> retList = this.tBHiddenDangerHandleService.getListByCriteriaQuery(cq, false);

        List<TSUser> userList = systemService.getList(TSUser.class);
        Map<String, String> userMap = new HashMap<>();
        for (TSUser user : userList) {
            userMap.put(user.getId(), user.getRealName());
        }
        Map<String, String> userNameMap = new HashMap<>();
        for (TSUser user : userList) {
            userNameMap.put(user.getId(), user.getUserName());
        }
        for(TBHiddenDangerHandleEntity t : retList){
            if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                String[] useids = t.getHiddenDanger().getFillCardManId().split(",");
                StringBuffer userNames = new StringBuffer();
                for(String userId : useids){
                    if(StringUtils.isNotBlank(userNames.toString())) {
                        userNames.append(",");
                        if(userMap.get(userId)==null&& StringUtil.isNotEmpty(userId)){
                            userNames.append(userId);
                        }else {
                            userNames.append(userMap.get(userId)+"-"+userNameMap.get(userId));
                        }
                    }else{
                        if(userMap.get(userId)==null&& StringUtil.isNotEmpty(userId)){
                            userNames.append(userId);
                        }else{
                            userNames.append(userMap.get(userId)+"-"+userNameMap.get(userId));
                        }
                    }
                }

                t.getHiddenDanger().setFillCardManNames(userNames.toString());
            }else{
                /**
                 *  上级检查中，检查人使用的字段是sjjcCheckMan，而其他页面，检查人使用的字段是fillCardManId，所以，为了使页面没有空字段，这里判断一步，如果fillCardMan为空，直接将sjjcCheckMan赋值给fillCardMan
                 * */
                t.getHiddenDanger().setFillCardManNames(t.getHiddenDanger().getSjjcCheckMan());
            }
        }
        List<Map<String,Object>> mapList = initExportList(retList);
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_HiddenDanger.xls");
        String huayuan = ResourceUtil.getConfigByName("huayuan");
        if(huayuan.equals("true")){
            templateExportParams.setTemplateUrl("export/template/exportTemp_HiddenDangerhuayuan.xls");
        }
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", mapList);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(new Date());
        modelMap.put(NormalExcelConstants.FILE_NAME,today+"问题导出列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    private List<Map<String, Object>> initExportList(List<TBHiddenDangerHandleEntity> retList){
//        List<TSUser> userList = systemService.getList(TSUser.class);
//        Map<String, String> userMap = new HashMap<>();
//        for (TSUser user : userList) {
//            userMap.put(user.getId(), user.getRealName());
//        }
        //从retList得到excel需要的数据结构
        List<Map<String,Object>> mapList = new ArrayList<Map<String, Object>>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(retList!=null&&retList.size()>0){
            for(TBHiddenDangerHandleEntity t : retList){
                Map<String, Object> map = new HashMap<String, Object>();
                TBHiddenDangerExamEntity hiddenDangerExam = t.getHiddenDanger();
                if(null!=hiddenDangerExam){
                    if(null!=hiddenDangerExam.getDutyUnit()) {
                        map.put("dutyUnitDepartname", hiddenDangerExam.getDutyUnit().getDepartname());
                    }
                    map.put("dutyManTemp", hiddenDangerExam.getDutyMan());
                    if(null!=hiddenDangerExam.getExamDate()) {
                        map.put("examDateTemp", sdf.format(hiddenDangerExam.getExamDate()));
                    }
                    if(null!=hiddenDangerExam.getAddress()) {
                        map.put("addressTemp", hiddenDangerExam.getAddress().getAddress());
                    }
                    if (StringUtils.isNotBlank(hiddenDangerExam.getShift())){
                        map.put("checkShiftTemp", DicUtil.getTypeNameByCode("workShift", hiddenDangerExam.getShift()));
                    }
                    //上级检查的检查人使用的是sjjcCheckMan，而其他录入页面使用的是fillCardManId，所以，这里需要判断一下
                    if (StringUtil.isNotEmpty(hiddenDangerExam.getFillCardManNames())){
                        map.put("fillCardManNames", hiddenDangerExam.getFillCardManNames());
                    }else{
                        map.put("fillCardManNames", hiddenDangerExam.getSjjcCheckMan());
                    }
                    if (StringUtils.isNotBlank(t.getHiddenDanger().getExamType())){
                        map.put("examTypeTemp", DicUtil.getTypeNameByCode("examType", t.getHiddenDanger().getExamType()));
                    }
                    if (StringUtils.isNotBlank(t.getHiddenDanger().getHiddenNature())){
                        map.put("hiddenNatureTemp", DicUtil.getTypeNameByCode("hiddenLevel", t.getHiddenDanger().getHiddenNature()));
                    }
                    if (StringUtils.isNotBlank(t.getHandlelStatus())){
                        map.put("handlelStatusTemp", DicUtil.getTypeNameByCode("handelStatus", t.getHandlelStatus()));
                    }
                    map.put("problemDescTemp",hiddenDangerExam.getProblemDesc());
                    map.put("dealTypeTemp","1".equals(hiddenDangerExam.getDealType())? "限期整改":"现场整改");
                    if(null!=hiddenDangerExam.getLimitDate()) {
                        map.put("limitDatetemp", sdf.format(hiddenDangerExam.getLimitDate()));
                    }
                    if(null!=t.getModifyDate()) {
                        map.put("modifyDateTemp", sdf.format(t.getModifyDate()));
                    }
                    if (StringUtils.isNotBlank(t.getModifyShift())) {
                        map.put("modifyShiftTemp", DicUtil.getTypeNameByCode("workShift", t.getModifyShift()));
                    }
                    if (StringUtil.isNotEmpty(t.getModifyMan())){
                        TSBaseUser modifyMan = systemService.get(TSBaseUser.class, t.getModifyMan());
                        map.put("modifyManTemp",modifyMan!=null?modifyMan.getRealName():t.getModifyMan());
                    }

                    if(null!=t.getReviewDate()) {
                        map.put("reviewDate", sdf.format(t.getReviewDate()));
                    }
                    if (StringUtils.isNotBlank(t.getReviewShift())) {
                        map.put("reviewShiftTemp", DicUtil.getTypeNameByCode("workShift", t.getReviewShift()));
                    }
                    if(StringUtil.isNotEmpty(t.getReviewMan())){
                        TSBaseUser reviewMan = systemService.get(TSBaseUser.class, t.getReviewMan());
                        map.put("reviewManTemp",reviewMan!=null?reviewMan.getRealName():null);
                    }
                    map.put("rollBackRemarkTemp",t.getRollBackRemark());
                    if (t.getHiddenDanger() != null) {
                        if(t.getHiddenDanger().getDangerId() != null){
                            if(t.getHiddenDanger().getDangerId().getHazard() != null){
                                if(t.getHiddenDanger().getDangerId().getHazard().getHazardName() != null){
                                    map.put("dangerSource", t.getHiddenDanger().getDangerId().getHazard().getHazardName());
                                }
                            }
                        }
                    }
                    map.put("hiddenTypeTemp", DicUtil.getTypeNameByCode("hiddenType", t.getHiddenDanger().getHiddenType()));

                    String manageType = hiddenDangerExam.getManageType();
                    if(StringUtil.isNotEmpty(manageType)){
                        String manageTypeTemp = DicUtil.getTypeNameByCode("manageType",manageType);
                        map.put("manageTypeTemp",manageTypeTemp);
                    }
                    String riskType = hiddenDangerExam.getRiskType();
                    if(StringUtil.isNotEmpty(riskType)){
                        String riskTypeTemp = DicUtil.getTypeNameByCode("risk_type",riskType);
                        map.put("riskTypeTemp",riskTypeTemp);
                    }
                    if(StringUtil.isNotEmpty(hiddenDangerExam.getManageDutyUnit())){
                        map.put("manageDutyUnitDepartname",hiddenDangerExam.getManageDutyUnit().getDepartname());
                    }
                    if(StringUtil.isNotEmpty(hiddenDangerExam.getManageDutyManId())){
                        TSUser user = systemService.get(TSUser.class,hiddenDangerExam.getManageDutyManId());
                        map.put("manageDutyManTemp",user.getRealName());
                    }
                    map.put("hiddenNumber",hiddenDangerExam.getHiddenNumber());
                }
                mapList.add(map);
            }
        }
        return mapList;
    }
    /**
     * 导出复查单
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "repartExportReXls")
    public String repartExportReXls(TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class, dataGrid);
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        try{
            //自定义追加查询条件
            cq.eq("createBy",sessionUser.getUserName());
            String  queryHandleStatusTem = request.getParameter("queryHandleStatus");
            if(StringUtils.isNotBlank(queryHandleStatusTem)){
                if(Constants.REVIEWSTATUS_PASS.equals(queryHandleStatusTem)){
                    cq.eq("handlelStatus", Constants.REVIEWSTATUS_PASS);
                }else if(Constants.HANDELSTATUS_REVIEW.equals(queryHandleStatusTem)){
                    cq.eq("handlelStatus", Constants.HANDELSTATUS_REVIEW);
                }else if(Constants.HANDELSTATUS_ROLLBACK_CHECK.equals(queryHandleStatusTem)){
                    cq.eq("handlelStatus", Constants.HANDELSTATUS_ROLLBACK_CHECK);//复查未通过，退回整改
                }
            }else{
                cq.eq("handlelStatus", Constants.HANDELSTATUS_REVIEW);
            }

            cq.createAlias("hiddenDanger","hiddenDanger");
            String findTimeStart = request.getParameter("hiddenDanger.examDate_begin");
            String findTimeEnd = request.getParameter("hiddenDanger.examDate_end");
            if(StringUtils.isNotBlank(findTimeStart)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.ge("hiddenDanger.examDate",sdf.parse(findTimeStart));
            }
            if(StringUtils.isNotBlank(findTimeEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.le("hiddenDanger.examDate",sdf.parse(findTimeEnd));
            }
            String examType = request.getParameter("hiddenDanger.examType");
            if(StringUtils.isNotBlank(examType)){
                if("jkxxblr".equals(examType)){
                    cq.eq("hiddenDanger.isFromJkxxb","1");
                }else{
                    cq.eq("hiddenDanger.examType",examType);
                }
            }
            String modifyDateBegin = request.getParameter("modifyDate_begin");
            String modifyDateEnd = request.getParameter("modifyDate_end");
            if(StringUtils.isNotBlank(modifyDateEnd)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cq.le("modifyDate",sdf.parse(modifyDateEnd+" 23:59:59"));
            }
            if(StringUtils.isNotBlank(modifyDateBegin)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cq.ge("modifyDate",sdf.parse(modifyDateBegin+" 00:00:00"));
            }
            String checkPeopleName = request.getParameter("modifyMan.realName");
            if(StringUtils.isNotBlank(checkPeopleName)){
                cq.like("modifyMan","%"+checkPeopleName+"%");
            }

        }catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        List<TBHiddenDangerHandleEntity> retList = this.tBHiddenDangerHandleService.getListByCriteriaQuery(cq, false);
        List<TSUser> userList = systemService.getList(TSUser.class);
        Map<String, String> userMap = new HashMap<>();
        for (TSUser user : userList) {
            userMap.put(user.getId(), user.getRealName());
        }
        Map<String, String> userNameMap = new HashMap<>();
        for (TSUser user : userList) {
            userNameMap.put(user.getId(), user.getUserName());
        }
        for(TBHiddenDangerHandleEntity t : retList){
            if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                String[] useids = t.getHiddenDanger().getFillCardManId().split(",");
                StringBuffer userNames = new StringBuffer();
                for(String userId : useids){
                    if(StringUtils.isNotBlank(userNames.toString())) {
                        userNames.append(",");
                        if(userMap.get(userId)==null&& StringUtil.isNotEmpty(userId)){
                            userNames.append(userId);
                        }else {
                            userNames.append(userMap.get(userId)+"-"+userNameMap.get(userId));
                        }
                    }else{
                        if(userMap.get(userId)==null&& StringUtil.isNotEmpty(userId)){
                            userNames.append(userId);
                        }else{
                            userNames.append(userMap.get(userId)+"-"+userNameMap.get(userId));
                        }
                    }
                }


                t.getHiddenDanger().setFillCardManNames(userNames.toString());
            }
        }
        List<TBHiddenDangerHandleVO> relist = new ArrayList<TBHiddenDangerHandleVO>();
        Integer id =0  ;
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_ReHiddenDanger.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        if(retList!=null&&retList.size()>0){
            for(TBHiddenDangerHandleEntity t : retList){
                id = id+1 ;
                TBHiddenDangerHandleVO vo = new TBHiddenDangerHandleVO() ;
                vo.setId(id);
                String departname  = t.getHiddenDanger().getDutyUnit().getDepartname() ;
                vo.setDepartname(departname);
                Date examDate = t.getHiddenDanger().getExamDate() ;
                vo.setExamDate(examDate);
                String address = t.getHiddenDanger().getAddress().getAddress() ;
                vo.setAddress(address);
                TSUser fillCardMan = systemService.getEntity(TSUser.class,t.getHiddenDanger().getFillCardMan());
                //TSUser fillCardMan = t.getHiddenDanger().getFillCardMan() ;
                vo.setRealName(fillCardMan!=null?fillCardMan.getRealName():null);
                String problemDesc = t.getHiddenDanger().getProblemDesc() ;
                vo.setProblemDesc(problemDesc);
                Date reviewDate =t.getReviewDate() ;
                vo.setReviewDate(reviewDate);
                if (StringUtil.isNotEmpty(t.getReviewMan())){
                    TSBaseUser reviewMan = systemService.get(TSBaseUser.class, t.getReviewMan());
                    vo.setReviewMan(reviewMan!=null?reviewMan.getRealName():null);
                }
                vo.setHandlelStatusTemp(DicUtil.getTypeNameByCode("handelStatus",t.getHandlelStatus()));
                relist.add(vo);
            }

        }
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("list", relist);
        modelMap.put(NormalExcelConstants.FILE_NAME,"问题导出列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }


    /**
     * 编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBHiddenDangerHandleEntity tBHiddenDangerHandle, HttpServletRequest req) {
        String load = req.getParameter("load");
        String flag = req.getParameter("flag");
        String id = req.getParameter("id");
        if (tBHiddenDangerHandle == null && StringUtil.isNotEmpty(id)){
            tBHiddenDangerHandle = systemService.getEntity(TBHiddenDangerHandleEntity.class, id);
        }

        req.setAttribute("flag",flag);
        if (StringUtil.isNotEmpty(tBHiddenDangerHandle.getId())) {
            tBHiddenDangerHandle = tBHiddenDangerHandleService.getEntity(TBHiddenDangerHandleEntity.class, tBHiddenDangerHandle.getId());
            req.setAttribute("tBHiddenDangerHandlePage", tBHiddenDangerHandle);
            String examType =tBHiddenDangerHandle.getHiddenDanger().getExamType();
            if(load.equals("detail")){
                return new ModelAndView("redirect:tBHiddenDangerExamController.do?goUpdate&load=detail&examType="+examType+"&id="+tBHiddenDangerHandle.getHiddenDanger().getId());
            }else{
                return new ModelAndView("redirect:tBHiddenDangerExamController.do?goUpdate&load=update&flag="+flag+"&examType="+examType+"&id="+tBHiddenDangerHandle.getHiddenDanger().getId());
            }

        }
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandle-update");
    }

    /**
     *日常隐患上报
     * @param ids
     * @param type all=全部上报
     * @return
     */
    @RequestMapping(params = "hiddenDangerReport")
    @ResponseBody
    public AjaxJson hiddenDangerReport(String ids, String type){
        if ("1".equals(type)) {//全部上报时,获取所有未上报的记录
            List<String> idList = systemService.findListbySql("select s.id  from t_b_hidden_danger_handle s where  s.report_date is null || s.report_status = '1'");
            if (idList != null && idList.size()>0) {
                ids = StringUtil.joinString(idList, ",");
            }
        }else if("2".equals(type)){
            List<String> idList = systemService.findListbySql("select s.id  from t_b_hidden_danger_handle s where  s.report_status = '2'");
            if (idList != null && idList.size()>0) {
                ids = StringUtil.joinString(idList, ",");
            }
        }
        return tBHiddenDangerHandleService.hiddenDangerReport(ids,false);
    }
    /**
     * 日常隐患撤回
     */
    @RequestMapping(params = "toReportHiddenDangerCallback")
    @ResponseBody
    public AjaxJson toReportHiddenDangerCallback(String ids){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "撤回成功";
        try {
            if(StringUtil.isNotEmpty(ids)){
                //调用煤监局接口删除已上报煤监局数据
                Map<String,String> retMap =tBHiddenDangerHandleService.toReportHiddenDangerCallback(ids);
                if(null==retMap || !Constants.LOCAL_RESULT_CODE_SUCCESS.equals(retMap.get("code"))){
                    message = "撤回失败";
                    if(null!=retMap && retMap.containsKey("message")){
                        message = retMap.get("message");
                    }
                    j.setMsg(message);
                    return j;
                }
            }
        } catch (Exception e) {
            message = "撤回失败";
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 格式化处理时间
     * @param date
     * @return
     */
    private Date getHandleTime(Date date){
        String dateTime = DateUtils.date2Str(date, DateUtils.date_sdf) +" "+ DateUtils.formatShortDateTime();
        return DateUtils.str2Date(dateTime, DateUtils.datetimeFormat);
    }

    /**
     * 获取待办任务
     * @param req
     * @return
     */
    @RequestMapping(params = "checkTask")
    @ResponseBody
    public AjaxJson checkTask(HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        try{
            TSUser sessionUser = ResourceUtil.getSessionUserName();
            StringBuffer sb = new StringBuffer();
            JSONObject jo = new JSONObject();
            //修改为管理员角色
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
            boolean isAJY= false;
            if(roleList != null && !roleList.isEmpty()){
                for(TSRoleUser ru : roleList){
                    TSRole role = ru.getTSRole();
                    if(role != null && role.getRoleName().equals("安监员")){
                        isAJY = true;
                        break;
                    }
                }
            }
            //待整改、复查未通过
//            sb.append("select count(1) count from t_b_hidden_danger_handle hdh LEFT JOIN t_b_hidden_danger_exam hde on hdh.hidden_danger_id=hde.id where 1=1 and hdh.handlel_status in ('1','4') and hde.duty_man='").append(sessionUser.getRealName()).append("'");
            sb.append("SELECT count(1) count from t_b_hidden_danger_handle hdh LEFT JOIN t_b_hidden_danger_exam hde on hdh.hidden_danger_id=hde.id where  hdh.handlel_status in ('1','4')  ");
            if(!isAdmin&&!DicUtil.modifyPower(sessionUser.getId())){
                //新增责任人权限
                sb.append(" AND (hde.duty_unit IN (\n" +
                        "SELECT org_id\n" +
                        "FROM t_s_base_user tsbu,t_s_user_org\n" +
                        "where tsbu.ID = t_s_user_org.user_id AND tsbu.username = '"+sessionUser.getUserName()+
                        "') or hde.duty_man = '"+sessionUser.getRealName()+"')");
            }
            List<BigInteger> dzgList = systemService.findListbySql(sb.toString());
            if(!dzgList.isEmpty() && dzgList.size()>0){
                jo.put("dzg",dzgList.get(0).intValue());
            }else{
                jo.put("dzg",0);
            }
            sb.setLength(0);
            //待复查
//            sb.append("select count(1) count from t_b_hidden_danger_handle hdh LEFT JOIN t_b_hidden_danger_exam hde on hdh.hidden_danger_id=hde.id where 1=1 and hdh.handlel_status='3' and hde.create_by='").append(sessionUser.getUserName()).append("' and hde.create_name='").append(sessionUser.getRealName()).append("'");
            sb.append("SELECT count(1) count from t_b_hidden_danger_handle hdh LEFT JOIN t_b_hidden_danger_exam hde on hdh.hidden_danger_id=hde.id where  hdh.handlel_status='3'  ");
            String ezhuang = ResourceUtil.getConfigByName("ezhuang");
            if(ezhuang.equals("true")){
                if(!isAdmin){
                    sb.append("and EXISTS (\t\n" +
                            "\t\tselect * from t_s_base_user u where '"+sessionUser.getId()+"'=u.id and (u.username=hde.create_by or FIND_IN_SET(u.id,hde.fill_card_manids) or FIND_IN_SET(u.realname,REPLACE(hde.fill_card_manids,' ',',')))\n" +
                            "\t)");
                }
            }else{
                if(!isAdmin&&!isAJY&&!DicUtil.reviewPower(sessionUser.getId())){
                    sb.append("and (EXISTS (\t\n" +
                            "\t\tselect * from t_s_base_user u, t_s_user_org uo\n" +
                            "\t\twhere uo.user_id=u.id and uo.org_id='" + sessionUser.getCurrentDepart().getId() + "'\n" +
                            "\t\tand (u.username=hde.create_by or FIND_IN_SET(u.id,hde.fill_card_manids) or FIND_IN_SET(u.realname,REPLACE(hde.fill_card_manids,' ',',')))\n" +
                            "\t) or  hde.manage_duty_unit = '" + sessionUser.getCurrentDepart().getId() + "' or hde.manage_duty_man_id = '" + sessionUser.getId() + "')");
                }
            }

            List<BigInteger> dfcList = systemService.findListbySql(sb.toString());
            if(!dfcList.isEmpty() && dfcList.size()>0){
                jo.put("dfc",dfcList.get(0).intValue());
            }else{
                jo.put("dfc",0);
            }
            //李楼特有
            String lilou = ResourceUtil.getConfigByName("ewmgn");
            if(StringUtils.isNotBlank(lilou) && "true".equals(lilou)){
                //隐患延期申请
                sb.setLength(0);
                sb.append("select count(1) count from t_b_hidden_danger_exam hde \n" +
                        "left join t_b_address_info address on hde.address=address.id\n" +
                        "left join t_b_hidden_danger_apply apply on hde.apply_delay=apply.id\n" +
                        "where address.isShowData='1'\n" +
                        "and apply.deal_status='1' ");
                if(!isAdmin){
                    sb.append(" and apply.accept_man='"+sessionUser.getId()+"' ");
                }
                List<BigInteger> yqsqList = systemService.findListbySql(sb.toString());
                if(!yqsqList.isEmpty() && yqsqList.size()>0){
                    jo.put("yqsq",yqsqList.get(0).intValue());
                }else{
                    jo.put("yqsq",0);
                }
                //隐患不通过
                sb.setLength(0);
                sb.append("select count(1) count from t_b_hidden_danger_exam hde \n" +
                        "left join t_b_address_info address on hde.address=address.id\n" +
                        "left join t_b_hidden_danger_apply apply on hde.apply_delay=apply.id\n" +
                        "where address.isShowData='1'\n" +
                        "and apply.deal_status='00' ");
                if(!isAdmin&&!DicUtil.modifyPower(sessionUser.getId())){
                    //新增责任人权限
                    sb.append(" AND (hde.duty_unit IN (\n" +
                            "SELECT org_id\n" +
                            "FROM t_s_base_user tsbu,t_s_user_org\n" +
                            "where tsbu.ID = t_s_user_org.user_id AND tsbu.username = '"+sessionUser.getUserName()+
                            "') or hde.duty_man = '"+sessionUser.getRealName()+"')");
                }
                List<BigInteger> btgList = systemService.findListbySql(sb.toString());
                if(!btgList.isEmpty() && btgList.size()>0){
                    jo.put("btg",btgList.get(0).intValue());
                }else{
                    jo.put("btg",0);
                }
                //隐患录入驳回
                sb.setLength(0);
                sb.append("select count(1) count from t_b_hidden_danger_handle hdh \n" +
                        "left join t_b_hidden_danger_exam hde on hdh.hidden_danger_id=hde.id\n" +
                        "left join t_b_address_info address on hde.address=address.id \n" +
                        "where address.isShowData='1' \n" +
                        "and hdh.handlel_status='2' ");
                if(!isAdmin){
                    /*sb.append("and EXISTS (\t\n" +
                            "\t\tselect * from t_s_base_user u where '"+sessionUser.getId()+"'=u.id and (u.username=hde.create_by or FIND_IN_SET(u.id,hde.fill_card_manids) or FIND_IN_SET(u.realname,REPLACE(hde.fill_card_manids,' ',',')))\n" +
                            "\t)");*/
                    sb.append("and EXISTS (\t\n" +
                            "\t\tselect * from t_s_base_user u where '"+sessionUser.getId()+"'=u.id and (u.username=hde.create_by )\n" +
                            "\t)");
                }
                List<BigInteger> lrbhList = systemService.findListbySql(sb.toString());
                if(!lrbhList.isEmpty() && lrbhList.size()>0){
                    jo.put("lrbh",lrbhList.get(0).intValue());
                }else{
                    jo.put("lrbh",0);
                }
                jo.put("lilou",true);
            }else {
                jo.put("lilou",false);
            }

            j.setObj(jo);
        }catch (Exception e) {
            j.setSuccess(false);
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 综合查询-管理员编辑
     * @param request
     * @return
     */
    @RequestMapping(params = "goAdminEdit")
    public ModelAndView goAdminEdit(HttpServletRequest request) {
        String id = request.getParameter("id");
        TBHiddenDangerHandleEntity handle = systemService.getEntity(TBHiddenDangerHandleEntity.class,id);
        request.setAttribute("handle",handle);
        request.setAttribute("width",request.getParameter("width"));
        request.setAttribute("height",request.getParameter("height"));
        if(handle.getHiddenDanger().getDangerId() != null && StringUtils.isNotBlank(handle.getHiddenDanger().getDangerId().getYeRiskGrade())){
            //风险等级
            String dangerSourceRiskValue;
            dangerSourceRiskValue = DicUtil.getTypeNameByCode("riskLevel",handle.getHiddenDanger().getDangerId().getYeRiskGrade());
            request.setAttribute("dangerSourceRiskValueTemp",dangerSourceRiskValue);
            //风险颜色
            String dangerSourceAlertColor;
            if("重大风险".equals(dangerSourceRiskValue)){
                dangerSourceAlertColor= Constants.ALERT_COLOR_ZDFX;
            }else if("较大风险".equals(dangerSourceRiskValue)){
                dangerSourceAlertColor= Constants.ALERT_COLOR_JDFX;
            }else if("一般风险".equals(dangerSourceRiskValue)){
                dangerSourceAlertColor= Constants.ALERT_COLOR_YBFX;
            }else{
                dangerSourceAlertColor= Constants.ALERT_COLOR_DFX;
            }
            request.setAttribute("dangerSourceAlertColor",dangerSourceAlertColor);

            String examType = handle.getHiddenDanger().getExamType();
            if(Constants.HIDDENCHECK_EXAMTYPE_KUANGJINGANQUANDAJIANCHA.equals(examType)){
                //return 矿井安全大检查  修改、查看页面
                String itemUserId = handle.getHiddenDanger().getItemUserId();
                if(StringUtils.isNotBlank(itemUserId)){
                    String ids [] = itemUserId.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String idtemp : ids){
                        TSUser user = systemService.getEntity(TSUser.class,idtemp);
                        if(user != null){
                            if(StringUtils.isNotBlank(sb.toString())){
                                sb.append(",");
                            }
                            sb.append(user.getRealName());
                        }

                    }
                    handle.getHiddenDanger().setItemUserNameTemp(sb.toString());
                }
            }
        }
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/adminEdit");
    }

    @RequestMapping(params = "doAdminEdit")
    @ResponseBody
    public AjaxJson doAdminEdit(TBHiddenDangerHandleEntity tbHiddenDangerHandle, HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String message = "修改成功";
        try{
            TBHiddenDangerExamEntity examEntity =  systemService.getEntity(TBHiddenDangerExamEntity.class,tbHiddenDangerHandle.getHiddenDanger().getId());

            TBHiddenDangerExamEntity tBHiddenDangerExam = tbHiddenDangerHandle.getHiddenDanger();
            String address = tBHiddenDangerExam.getAddress().getId();
            if(StringUtils.isNotBlank(address)){
                TBAddressInfoEntity addressInfoEntity = systemService.getEntity(TBAddressInfoEntity.class,address);
                tBHiddenDangerExam.setAddress(addressInfoEntity);
            }
            String dutyUnitId = tBHiddenDangerExam.getDutyUnit().getId();
            if(StringUtils.isNotBlank(dutyUnitId)){
                TSDepart tsd = this.systemService.getEntity(TSDepart.class,dutyUnitId);
                tBHiddenDangerExam.setDutyUnit(tsd);
            }

            MyBeanUtils.copyBeanNotNull2Bean(tBHiddenDangerExam, examEntity);
            tbHiddenDangerExamService.saveOrUpdate(examEntity);
            //隐患清单编辑 --国家局上报数据标志更新
            sfService.saveOrUpdateSfHiddenRel(examEntity.getId());
            TBHiddenDangerHandleEntity handleEntity = systemService.getEntity(TBHiddenDangerHandleEntity.class,tbHiddenDangerHandle.getId());
            tbHiddenDangerHandle.setHiddenDanger(examEntity);
            MyBeanUtils.copyBeanNotNull2Bean(tbHiddenDangerHandle, handleEntity);
            tBHiddenDangerHandleService.saveOrUpdate(handleEntity);
            String sql = "SELECT id,realname from t_s_base_user";
            List<Map<String, Object>> userList = systemService.findForJdbc(sql);
            Map<String, String> userListMap = new HashMap<>();
            if (userList !=null && userList.size()>0) {
                for (Map<String, Object> obj : userList) {
                    String id = String.valueOf(obj.get("id"));
                    String realname = String.valueOf(obj.get("realname"));
                    userListMap.put(id, realname);
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String realname = userListMap.get(handleEntity.getModifyMan());
            String modifyDate = null;
            if(StringUtil.isNotEmpty(handleEntity.getModifyDate())){
                modifyDate = sdf.format(handleEntity.getModifyDate());
            }
            String updateSql = "update t_b_hidden_danger_handle_step SET handle_date = '"+modifyDate+"',handle_man = '"+realname+"'  WHERE handle_step = '2' and exam_id = '"+handleEntity.getHiddenDanger().getId()+"'";
            systemService.executeSql(updateSql);
            realname = userListMap.get(handleEntity.getReviewMan());
            String reviewDate = null;
            if(StringUtil.isNotEmpty(handleEntity.getReviewDate())){
                reviewDate = sdf.format(handleEntity.getReviewDate());
            }
            updateSql = "update t_b_hidden_danger_handle_step SET handle_date = '"+reviewDate+"',handle_man = '"+realname+"'  WHERE handle_step = '3' and exam_id = '"+handleEntity.getHiddenDanger().getId()+"'";
            systemService.executeSql(updateSql);
            //查看日志
            updateSql = "UPDATE t_b_hidden_danger_handle_log SET userid = '"+handleEntity.getModifyMan()+"',operatetime='"+modifyDate+"' WHERE handle_type = '3' and  exam_id = '"+handleEntity.getHiddenDanger().getId()+"'";
            systemService.executeSql(updateSql);
            updateSql = "UPDATE t_b_hidden_danger_handle_log SET userid = '"+handleEntity.getReviewMan()+"',operatetime='"+reviewDate+"' WHERE handle_type = '5' and  exam_id = '"+handleEntity.getHiddenDanger().getId()+"'";
            systemService.executeSql(updateSql);
        }catch (Exception e){
            e.printStackTrace();
            message = "修改失败";
        }
        j.setMsg(message);
        return j;
    }

    /**
     *  阳光账号隐藏数据操作
     *  @return
     * */
    @RequestMapping(params = "sunshine")
    @ResponseBody
    public AjaxJson sunShine(HttpServletRequest request, HttpServletResponse response){
        String message = "隐藏成功！";
        AjaxJson j = new AjaxJson();
        String ids = request.getParameter("ids");

        try{
            if(StringUtil.isNotEmpty(ids)){
                for (String id : ids.split(",")){
                    //这个表用来保存哪些是隐藏的
                    List<TBSunshineEntity> sunshineEntityList = systemService.findByProperty(TBSunshineEntity.class, "columnId", id);
                    //如果需要隐藏的数据已经存在，那么删除该数据
                    if (sunshineEntityList!=null && sunshineEntityList.size()>0){
                        systemService.deleteAllEntitie(sunshineEntityList);
                    }

                    TBSunshineEntity sunshineEntity = new TBSunshineEntity();
                    sunshineEntity.setTableName("t_b_hidden_danger_handle");
                    sunshineEntity.setColumnId(id);

                    systemService.save(sunshineEntity);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            message = "隐藏失败";
            throw new BusinessException(e.getMessage());
        }

        j.setMsg(message);
        return j;
    }


    public JSONObject postCheckTemplate(String openid,String createTime,String addressName,String problemDesc,
                                        String limitDate,String modifyManName,String rectMeasures,String modifyDate) throws IOException {
        //   测试地址 http://liuran.tunnel.qydev.com/WeixinChat.do?postCheckTemplate
        //   记得判断如果id被关联多个时 遍历一下
        JSONObject jo = new JSONObject();
        String access_token = "";
        String sql = "select accessToken from t_b_token";
        List<String> tmpList = systemService.findListbySql(sql);
        if (!tmpList.isEmpty() && tmpList.size() > 0) {
            access_token = tmpList.get(0);
        } else {
            String appid = ResourceUtil.getConfigByName("appID");
            String appsecret = ResourceUtil.getConfigByName("appsecret");
            access_token = weChartGetToken.getAccessToken(appid, appsecret);
        }
        String templateCheck_id= ResourceUtil.getConfigByName("templateCheck_id");
        JSONObject postJO = new JSONObject();
        JSONObject text=new JSONObject();
        JSONObject first=new JSONObject();
        JSONObject keyword1=new JSONObject();
        JSONObject keyword2=new JSONObject();
        JSONObject keyword3=new JSONObject();
        JSONObject keyword4=new JSONObject();
        JSONObject keyword5=new JSONObject();
        JSONObject remark=new JSONObject();

//        openid="oHNqj1HtUlGBotWPQdFMqV9JdDSk";
//        createTime="2018年3月32日";
//        addressName="A515";
//        problemDesc="殴打小朋友";
//        limitDate="1995年";
//        modifyManName="习近平";
//        rectMeasures="打哭就行";
//        modifyDate="1997年";

        postJO.put("touser",openid);
        postJO.put("template_id",templateCheck_id);
        first.put("value",createTime);
        first.put("color","#007f80");
        keyword1.put("value",addressName);
        keyword1.put("color","#007f80");
        keyword2.put("value",problemDesc);
        keyword2.put("color","#007f80");
        keyword3.put("value",limitDate);
        keyword3.put("color","#007f80");
        keyword4.put("value",modifyManName);
        keyword4.put("color","#007f80");
        keyword5.put("value",rectMeasures);
        keyword5.put("color","#007f80");
        remark.put("value",modifyDate);
        remark.put("color","#007f80");

        text.put("first",first);
        text.put("keyword1",keyword1);
        text.put("keyword2",keyword2);
        text.put("keyword3",keyword3);
        text.put("keyword4",keyword4);
        text.put("keyword5",keyword5);
        text.put("remark",remark);
        postJO.put("data",text);

        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;

        try {
            String returnData = HttpClientUtils.post(url, postJO.toString());
            jo = JSONObject.fromObject(returnData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo;
    }

    /************************************************* 隐患操作日志 start **********************************************/
    @RequestMapping(params = "logList")
    public ModelAndView logList(HttpServletRequest request) {
        request.setAttribute("examId", ResourceUtil.getParameter("examId"));
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerHandleLogList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param
     */

    @RequestMapping(params = "logDatagrid")
    public void datagrid(TBHiddenDangerHandleLogEntity tBHiddenDangerHandleLog, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleLogEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBHiddenDangerHandleLog, request.getParameterMap());
        try{
            String examId = request.getParameter("examId");
            cq.eq("examId",examId);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBHiddenDangerHandleLogService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
    /************************************************* 隐患操作日志  end  **********************************************/

    /**
     * 导出excel 使模板
     *
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME,"已闭环隐患导入模板");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(1);
        templateExportParams.setScanAllsheet(true);

        Map<String, Object> param =new HashMap<String, Object>();
        templateExportParams.setTemplateUrl("export/template/importTemp_hiddenDangerHandle.xls");
        List<TBHiddenDangerHandleExportDicVO> dicVOList = new ArrayList<TBHiddenDangerHandleExportDicVO>();

        //查询检查类型
        String examTypeSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='examType')";
        List<String> examTypeList = systemService.findListbySql(examTypeSql);
        //查询班次
        String shiftSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='workShift')";
        List<String> shiftList = systemService.findListbySql(shiftSql);
        //查询地点
        String addressListSql = "select address from t_b_address_info where is_delete='0'";
        List<String> addressList = systemService.findListbySql(addressListSql);
        //查询组别
        String itemIdSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='group')";
        List<String> itemIdList = systemService.findListbySql(itemIdSql);
        //查询组员
        String itemUserIdSql = "select u.realname realName from t_s_base_user u join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id where u.delete_flag='0' and u.status in ('1','0','-1')";
        List<String> itemUserIdList = systemService.findListbySql(itemUserIdSql);
        //查询检查人
        String fillCardManSql = "select u.realname realName from t_s_base_user u join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id where u.delete_flag='0' and u.status in ('1','0','-1')";
        List<String> fillCardManList = systemService.findListbySql(fillCardManSql);
        //查询责任单位
        String dutyUnitSql = "select departName from t_s_depart where delete_flag='0'";
        List<String> dutyUnitList = systemService.findListbySql(dutyUnitSql);
        //查询责任人
        String dutyManSql = "select u.realname realName from t_s_base_user u join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id where u.delete_flag='0' and u.status in ('1','0','-1')";
        List<String> dutyManList = systemService.findListbySql(dutyManSql);
        //查询督办单位
        String superviseUnitSql = "select departName from t_s_depart where delete_flag='0'";
        List<String> superviseUnitList = systemService.findListbySql(superviseUnitSql);
        //查询隐患类别
        String hiddenDangerCategorySql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='hiddenCate')";
        List<String> hiddenDangerCategoryList = systemService.findListbySql(hiddenDangerCategorySql);
        //查询隐患等级
        String hiddenDangerLevelSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='hiddenLevel')";
        List<String> hiddenDangerLevelList = systemService.findListbySql(hiddenDangerLevelSql);
        //查询隐患类型
        String hiddenDangerTypeSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='hiddenType')";
        List<String> hiddenDangerTypeList = systemService.findListbySql(hiddenDangerTypeSql);
        //查询处理方式
        List<String> processModeList = new ArrayList<String>();
        processModeList.add("限期整改");
        processModeList.add("现场处理");
        //查询整改人
        String modifyManSql = "select u.realname realName from t_s_base_user u join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id where u.delete_flag='0' and u.status in ('1','0','-1')";
        List<String> modifyManList = systemService.findListbySql(modifyManSql);
        //查询整改班次
        String modifyShiftSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='workShift')";
        List<String> modifyShiftList = systemService.findListbySql(modifyShiftSql);
        //查询复查人
        String reviewManSql = "select u.realname realName from t_s_base_user u join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id where u.delete_flag='0' and u.status in ('1','0','-1')";
        List<String> reviewManList = systemService.findListbySql(reviewManSql);
        //查询复查班次
        String reviewShiftSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='workShift')";
        List<String> reviewShiftList = systemService.findListbySql(reviewShiftSql);

        //得到这几串数列的最长的一列，excel导出的行数即为最长一列的长度
        int[] listLength = {examTypeList.size(),shiftList.size(),addressList.size(),itemIdList.size(), itemUserIdList.size(),
                fillCardManList.size(),dutyUnitList.size(),dutyManList.size(),superviseUnitList.size(),
                hiddenDangerCategoryList.size(), hiddenDangerLevelList.size(),hiddenDangerTypeList.size(),processModeList.size(),
                modifyManList.size(), modifyShiftList.size(),reviewManList.size(),reviewShiftList.size()};         /*后面这个查询的除了公有以外的私有的*/
        int maxLength = listLength[0];
        for (int i = 0; i < listLength.length; i++) {   //开始循环一维数组
            if (listLength[i] > maxLength) {  //循环判断数组元素
                maxLength = listLength[i]; }  //赋值给num，然后再次循环
        }
        for (int j=0; j<maxLength; j++) {
            TBHiddenDangerHandleExportDicVO vo = new TBHiddenDangerHandleExportDicVO();
            if (j < examTypeList.size()) {
                vo.setExamType(examTypeList.get(j));
            }
            if (j < shiftList.size()) {
                vo.setShift(shiftList.get(j));
            }
            if (j < addressList.size()) {
                vo.setAddress(addressList.get(j));
            }
            if (j < itemIdList.size()) {
                vo.setItemId(itemIdList.get(j));
            }
            if (j < itemUserIdList.size()) {
                vo.setItemUserId(itemUserIdList.get(j));
            }
            if (j < fillCardManList.size()) {
                vo.setFillCardMan(fillCardManList.get(j));
            }
            if (j < dutyUnitList.size()) {
                vo.setDutyUnit(dutyUnitList.get(j));
            }
            if (j < dutyManList.size()) {
                vo.setDutyMan(dutyManList.get(j));
            }
            if (j < superviseUnitList.size()) {
                vo.setSuperviseUnit(superviseUnitList.get(j));
            }
            if (j < hiddenDangerCategoryList.size()) {
                vo.setHiddenDangerCategory(hiddenDangerCategoryList.get(j));
            }
            if (j < hiddenDangerLevelList.size()) {
                vo.setHiddenDangerLevel(hiddenDangerLevelList.get(j));
            }
            if (j < hiddenDangerTypeList.size()) {
                vo.setHiddenDangerType(hiddenDangerTypeList.get(j));
            }
            if (j < processModeList.size()) {
                vo.setProcessMode(processModeList.get(j));
            }
            if (j < modifyManList.size()) {
                vo.setModifyMan(modifyManList.get(j));
            }
            if (j < modifyShiftList.size()) {
                vo.setModifyShift(modifyShiftList.get(j));
            }
            if (j < reviewManList.size()) {
                vo.setReviewMan(reviewManList.get(j));
            }
            if (j < reviewShiftList.size()) {
                vo.setReviewShift(reviewShiftList.get(j));
            }
            dicVOList.add(vo);
        }
        //将字典赋值到param中，写到sheet1中
        param.put("dicVoList", dicVOList);
        modelMap.put(TemplateExcelConstants.PARAMS, templateExportParams);
        modelMap.put(TemplateExcelConstants.MAP_DATA, param);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    /****************************************************导入*************************************************************/
    /**
     * 导入功能跳转
     *
     * @return
     */

    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name","tBHiddenDangerHandleController");
        req.setAttribute("function_name", "importExcelT");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    //导入隐患
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcelT", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcelT(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        String message = "";
        List<TBAddressInfoEntity> addressList = systemService.findByProperty(TBAddressInfoEntity.class,"isDelete","0");
        Map<String, TBAddressInfoEntity> addressMap = new HashedMap();
        if (addressList != null && addressList.size()>0) {
            for (TBAddressInfoEntity entity : addressList) {
                addressMap.put(entity.getAddress(), entity);
            }
        }
        List<TSDepart> departList = systemService.findByProperty(TSDepart.class,"deleteFlag",new Short("0"));
        Map<String, TSDepart> departMap = new HashedMap();
        if (departList != null && departList.size()>0) {
            for (TSDepart entity : departList) {
                departMap.put(entity.getDepartname(), entity);
            }
        }
        List<TSUser> userList = systemService.findByProperty(TSUser.class,"deleteFlag",new Short("0"));
        Map<String, TSUser> userMap = new HashedMap();
        if (userList != null && userList.size()>0) {
            for (TSUser entity : userList) {
                userMap.put(entity.getRealName().replaceAll(" ",""), entity);
            }
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setNeedSave(false);
            params.setNeedVerfiy(true);
            params.setVerifyHanlder(new HiddenDangerHandleExcelVerifyHandler(addressMap,departMap,userMap));
            try {
                ExcelImportResult<TBHiddenDangerHandleExportEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(),TBHiddenDangerHandleExportEntity.class,params);
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
                    List<TBHiddenDangerExamEntity> examEntityList = new ArrayList<>();
                    for (int i = 0; i < result.getList().size(); i++) {
                        TBHiddenDangerExamEntity tBHiddenDangerExam = new TBHiddenDangerExamEntity();
                        TBHiddenDangerHandleExportEntity tBHiddenDangerHandleExport = result.getList().get(i);
                        //挂牌督办默认设置为未挂牌
                        tBHiddenDangerExam.setIsLsProv(Constants.HDBIISLS_STATE_UNDO);
                        tBHiddenDangerExam.setIsLsSub(Constants.HDBIISLS_STATE_UNDO);
                        tBHiddenDangerExam.setReportStatus(Constants.REPORT_STATUS_N);
                        tBHiddenDangerExam.setExamType(tBHiddenDangerHandleExport.getExamType());
                        tBHiddenDangerExam.setExamDate(tBHiddenDangerHandleExport.getExamDate());
                        tBHiddenDangerExam.setShift(tBHiddenDangerHandleExport.getShift());
                        tBHiddenDangerExam.setAddress(tBHiddenDangerHandleExport.getAddressId());
                        if ("sjjc".equals(tBHiddenDangerExam.getExamType())) {
                            tBHiddenDangerExam.setSjjcDept(tBHiddenDangerHandleExport.getSjjcDept());
                            tBHiddenDangerExam.setSjjcCheckMan(tBHiddenDangerHandleExport.getSjjcCheckMan());
                            tBHiddenDangerExam.setRemark(tBHiddenDangerHandleExport.getRemark());
                        } else if ("kjaqdjc".equals(tBHiddenDangerExam.getExamType())) {
                            tBHiddenDangerExam.setItemId(tBHiddenDangerHandleExport.getItemId());
                            tBHiddenDangerExam.setItemUserId(tBHiddenDangerHandleExport.getItemUserId());
                        } else {
                            tBHiddenDangerExam.setSjjcCheckMan(tBHiddenDangerHandleExport.getFillCardManName());
                            tBHiddenDangerExam.setFillCardManId(tBHiddenDangerHandleExport.getFillCardManId());
                        }
                        tBHiddenDangerExam.setDutyUnit(tBHiddenDangerHandleExport.getDutyUnitId());
                        tBHiddenDangerExam.setDutyMan(tBHiddenDangerHandleExport.getDutyMan());
                        tBHiddenDangerExam.setSuperviseUnitId(tBHiddenDangerHandleExport.getSuperviseUnitId().getId());
                        tBHiddenDangerExam.setHiddenCategory(tBHiddenDangerHandleExport.getHiddenCategory());
                        tBHiddenDangerExam.setHiddenNature(tBHiddenDangerHandleExport.getHiddenDangerLevel());
                        tBHiddenDangerExam.setHiddenNatureOriginal(tBHiddenDangerHandleExport.getHiddenDangerLevel());
                        tBHiddenDangerExam.setHiddenType(tBHiddenDangerHandleExport.getHiddenDangerType());
                        tBHiddenDangerExam.setProblemDesc(tBHiddenDangerHandleExport.getProblemDesc());
                        tBHiddenDangerExam.setDealType(tBHiddenDangerHandleExport.getDealType());
                        message = "隐患检查添加成功";
                        //处理表添加一条数据
                        TBHiddenDangerHandleEntity handleEntity = new TBHiddenDangerHandleEntity();
                        handleEntity.setHiddenDanger(tBHiddenDangerExam);
                        String dealType = tBHiddenDangerExam.getDealType();
                        if (Constants.DEALTYPE_XIANCAHNG.equals(dealType)) {//现场处理
                            tBHiddenDangerExam.setReviewMan(tBHiddenDangerHandleExport.getReviewManId());
                            tBHiddenDangerExam.setLimitDate(null);
                            handleEntity.setRectMeasures(tBHiddenDangerHandleExport.getRectMeasures());
                            handleEntity.setReviewReport(tBHiddenDangerHandleExport.getReviewReport());
                            handleEntity.setReviewMan(tBHiddenDangerExam.getReviewMan() != null ? tBHiddenDangerExam.getReviewMan().getId() : null);
                            handleEntity.setModifyDate(tBHiddenDangerExam.getExamDate());
                            handleEntity.setModifyShift(tBHiddenDangerExam.getShift());
                            handleEntity.setModifyMan(tBHiddenDangerExam.getDutyMan());
                            handleEntity.setReviewDate(tBHiddenDangerExam.getExamDate());
                            handleEntity.setReviewShift(tBHiddenDangerExam.getShift());
                            handleEntity.setReviewResult("1");
                            handleEntity.setHandlelStatus(Constants.REVIEWSTATUS_PASS);
                        } else {//限期整改
                            tBHiddenDangerExam.setLimitDate(tBHiddenDangerHandleExport.getLimitDate());
                            handleEntity.setModifyDate(tBHiddenDangerHandleExport.getModifyDate());
                            handleEntity.setModifyShift(tBHiddenDangerHandleExport.getModifyShift());
                            handleEntity.setModifyMan(tBHiddenDangerHandleExport.getModifyMan());
                            handleEntity.setReviewDate(tBHiddenDangerHandleExport.getReviewDate());
                            handleEntity.setReviewShift(tBHiddenDangerHandleExport.getReviewShift());
                            handleEntity.setReviewMan(tBHiddenDangerHandleExport.getReviewMan());
                            handleEntity.setRectMeasures(tBHiddenDangerHandleExport.getRectMeasures());
                            handleEntity.setReviewReport(tBHiddenDangerHandleExport.getReviewReport());
                            handleEntity.setReviewResult("1");
                            handleEntity.setHandlelStatus(Constants.REVIEWSTATUS_PASS);
                        }
                        tBHiddenDangerExam.setHandleEntity(handleEntity);
                        if (tBHiddenDangerExam.getProblemDesc() != null && tBHiddenDangerExam.getProblemDesc() != "") {
                            tBHiddenDangerExam.setProblemDesc(tBHiddenDangerExam.getProblemDesc().trim());
                        }
                        if (tBHiddenDangerExam.getRemark() != null && tBHiddenDangerExam.getRemark() != "") {
                            tBHiddenDangerExam.setRemark(tBHiddenDangerExam.getRemark().trim());
                        }
                        tBHiddenDangerExam.setHiddenNatureOriginal(tBHiddenDangerExam.getHiddenNature());
                        systemService.save(tBHiddenDangerExam);
                        examEntityList.add(tBHiddenDangerExam);
                        //添加隐患上报国家局标识
                        sfService.saveOrUpdateSfHiddenRel(tBHiddenDangerExam.getId());
                    }

                    List<String> idList = new ArrayList<>();
                    if(!examEntityList.isEmpty() && examEntityList.size()>0){
                        for(TBHiddenDangerExamEntity bean:examEntityList){
                            idList.add(bean.getId());
                        }
                        syncToCloudService.hiddenDangerBatchReport(StringUtils.join(idList, ","));
                    }
                    j.setSuccess(true);
                    j.setMsg("文件导入成功！");
                    systemService.addLog(j.getMsg(), Globals.Log_Leavel_INFO, Globals.Log_Type_UPLOAD);
                    for (TBHiddenDangerExamEntity tBHiddenDangerExam : examEntityList){
                        systemService.addLog("隐患检查\"" + tBHiddenDangerExam.getId() + "\"导入成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
                        //添加处理步骤
                        TSUser user = ResourceUtil.getSessionUserName();
                        TBHiddenDangerHandleStepEntity handleStepEntity = new TBHiddenDangerHandleStepEntity();
                        handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
                        handleStepEntity.setHandleStep(1);
                        if(Constants.DEALTYPE_XIANQI.equals(tBHiddenDangerExam.getDealType())){
                            handleStepEntity.setHandleDate(new Date());
                            handleStepEntity.setHandleMan(user.getRealName());
                            handleStepEntity.setHandleStatus(Constants.HANDELSTATUS_REPORT);
                        } else {
                            handleStepEntity.setHandleDate(tBHiddenDangerExam.getExamDate());
                            handleStepEntity.setHandleMan(tBHiddenDangerExam.getCreateName());
                            handleStepEntity.setHandleStatus(Constants.REVIEWSTATUS_PASS);
                        }
                        systemService.save(handleStepEntity);
                        tBHiddenDangerHandleLogService.addLog(tBHiddenDangerExam.getId(), Globals.Log_Type_REPORT,"隐患上报成功");
                        if (Constants.DEALTYPE_XIANQI.equals(tBHiddenDangerExam.getDealType())) {
                            int handleStep = 0;
                            CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleStepEntity.class);
                            cq.eq("hiddenDanger.id",tBHiddenDangerExam.getId());
                            cq.addOrder("handleStep", SortDirection.asc);
                            cq.add();
                            List<TBHiddenDangerHandleStepEntity> handleStepList = systemService.getListByCriteriaQuery(cq,false);
                            if(!handleStepList.isEmpty() && handleStepList.size() > 0 ){
                                handleStep = handleStepList.get((handleStepList.size()-1)).getHandleStep() + 1;
                                handleStepEntity = new TBHiddenDangerHandleStepEntity();
                                handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
                                handleStepEntity.setHandleStep(handleStep);
                                handleStepEntity.setHandleDate(getHandleTime(tBHiddenDangerExam.getHandleEntity().getModifyDate()));
                                TSBaseUser baseUser = systemService.get(TSBaseUser.class, tBHiddenDangerExam.getHandleEntity().getModifyMan());
                                handleStepEntity.setHandleMan(baseUser!=null?baseUser.getRealName():"");
                                handleStepEntity.setHandleStatus("3");
                                systemService.save(handleStepEntity);
                            }
                            tBHiddenDangerHandleLogService.addLog(tBHiddenDangerExam.getId(), Globals.Log_Type_RECTIFY,"隐患整改通过");
                            handleStep = 0;
                            cq = new CriteriaQuery(TBHiddenDangerHandleStepEntity.class);
                            cq.eq("hiddenDanger.id",tBHiddenDangerExam.getId());
                            cq.addOrder("handleStep", SortDirection.asc);
                            cq.add();
                            handleStepList = systemService.getListByCriteriaQuery(cq,false);
                            if(!handleStepList.isEmpty() && handleStepList.size() > 0 ){
                                handleStep = handleStepList.get((handleStepList.size()-1)).getHandleStep() + 1;
                                handleStepEntity = new TBHiddenDangerHandleStepEntity();
                                handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
                                handleStepEntity.setHandleStep(handleStep);
                                handleStepEntity.setHandleDate(getHandleTime(tBHiddenDangerExam.getHandleEntity().getReviewDate()));
                                TSBaseUser baseUser = systemService.get(TSBaseUser.class, tBHiddenDangerExam.getHandleEntity().getReviewMan());
                                handleStepEntity.setHandleMan(baseUser!=null?baseUser.getRealName():"");
                                handleStepEntity.setHandleStatus("5");
                                handleStepEntity.setRemark(tBHiddenDangerExam.getHandleEntity().getReviewReport());
                                systemService.save(handleStepEntity);
                            }
                            tBHiddenDangerHandleLogService.addLog(tBHiddenDangerExam.getId(), Globals.Log_Type_REVIEW,"隐患复查通过");
                        }
                    }
                }
            } catch (Exception e) {
                j.setSuccess(false);
                j.setMsg("文件导入失败！");
                systemService.addLog(j.getMsg()+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPLOAD);
                logger.error(ExceptionUtil.getExceptionMessage(e));
            }finally{
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    LogUtil.error("日常隐患导入失败",e);
                }
            }
        }
        return j;
    }
}
