package com.sdzk.buss.web.decisionAnalyse.controller;

import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.identification.entity.RiskIdentificationPostEntity;
import com.sddb.buss.riskmanage.entity.RiskManageEntity;
import com.sddb.buss.riskmanage.entity.RiskManageHazardFactorEntity;
import com.sddb.buss.riskmanage.entity.RiskManagePostTaskEntity;
import com.sddb.buss.riskmanage.entity.RiskManageRelHd;
import com.sdzk.buss.web.assessmentscore.entity.AssessmentScoreEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.DateRangeUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.decisionAnalyse.entity.MineYearRiskTrendEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.decisionAnalyse.entity.DecisionAnalyseVO;
import com.sdzk.buss.web.decisionAnalyse.service.TBDecisionAnalyseServiceI;
import com.sdzk.buss.web.majorhiddendanger.entity.TBMajorHiddenDangerEntity;
import com.sdzk.buss.web.specialevaluation.entity.TBSpecialEvaluationEntity;
import net.sf.json.JSONObject;
import oracle.sql.DATE;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.DataGridReturn;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import org.jeecgframework.web.system.pojo.base.TSDepart;


/**
 * @Title: Controller
 * @Description: 隐患检查
 * @author onlineGenerator
 * @date 2016-04-21 10:24:05
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBDecisionAnalyseController")
public class TBDecisionAnalyseController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBDecisionAnalyseController.class);

    @Autowired
    private SystemService systemService;
    @Autowired
    private TBDecisionAnalyseServiceI tBDecisionAnalyseService;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }





    /**
     * 日常隐患列表跳转（决策分析弹出页面）
     * @param request
     * @return
     */
    @RequestMapping(params = "hiddenDangerList")
    public ModelAndView hiddenDangerList( HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>();
        //1：矿年度风险隐患变化趋势 2：月度风险隐患部门分布情况 3：专项辨识情况统计 4：未关联危险源隐患列表 5：重大隐患风险上报情况统计
        String statisticType=request.getParameter("statisticType");
        params.put("statisticType",statisticType);
        String yearMonth=request.getParameter("yearMonth");
        params.put("yearMonth",yearMonth);
        String unitId=request.getParameter("unitId");
        params.put("unitId",unitId);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hiddenDangerList",params);
    }

    /**
     * 日常隐患弹出列表
     * @param tBHiddenDangerHandle
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "hiddenDangerDatagrid")
    public void hiddenDangerDatagrid(TBHiddenDangerHandleEntity tBHiddenDangerHandle,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class, dataGrid);
        //1：矿年度风险隐患变化趋势 2：月度风险隐患部门分布情况 3：专项辨识情况统计 4：未关联危险源隐患列表 5：重大隐患风险上报情况统计
        String statisticType=ResourceUtil.getParameter("statisticType");
        //查询条件组装器
        //org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBHiddenDangerHandle, request.getParameterMap());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            //自定义追加查询条件
            cq.createAlias("hiddenDanger","hiddenDanger");
            String findTimeStart = request.getParameter("hiddenDanger.examDate_begin");
            String findTimeEnd = request.getParameter("hiddenDanger.examDate_end");
            if(StringUtils.isNotBlank(findTimeStart)){
                cq.ge("hiddenDanger.examDate",dateSdf.parse(findTimeStart));
            }
            if(StringUtils.isNotBlank(findTimeEnd)){
                cq.le("hiddenDanger.examDate",dateSdf.parse(findTimeEnd));
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
                cq.ge("modifyDate",sdf.parse(modifyDate+" 00:00:00"));
                cq.le("modifyDate",sdf.parse(modifyDate+" 23:59:59"));
            }
            String fillCaredMainId = request.getParameter("hiddenDanger.fillCardMan.id");
            if(StringUtils.isNotBlank(fillCaredMainId)){
                //cq.eq("hiddenDanger.fillCardMan.id",fillCaredMainId);
                cq.or(
                        Restrictions.eq("hiddenDanger.fillCardMan.id",fillCaredMainId),
                        Restrictions.or(
                                Restrictions.eq("hiddenDanger.fillCardManId",fillCaredMainId),
                                Restrictions.or(
                                        Restrictions.like("hiddenDanger.fillCardManId",fillCaredMainId+",%"),
                                        Restrictions.or(
                                                Restrictions.like("hiddenDanger.fillCardManId","%,"+fillCaredMainId),
                                                Restrictions.like("hiddenDanger.fillCardManId","%,"+fillCaredMainId+",%")
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

            if(statisticType!=null){
                if(statisticType.equals("1")){
                    String yearMonth=ResourceUtil.getParameter("yearMonth");
                    //这边查询的是隐患
                    cq.createAlias("hiddenDanger","hiddenDanger");
                    if(StringUtil.isNotEmpty(yearMonth)){
                        String from = yearMonth+"-01 00:00:00";
                        String end = DateUtils.getLastDay(from);
                        end = end + " 23:59:59";
                        cq.ge("hiddenDanger.examDate",sdf.parse(from));
                        cq.le("hiddenDanger.examDate", sdf.parse(end));
                        cq.eq("handlelStatus", Constants.REVIEWSTATUS_PASS);
                    }

                } else  if(statisticType.equals("2")){
                    String yearMonth = ResourceUtil.getParameter("yearMonth");
                    String unitId = ResourceUtil.getParameter("unitId");
                    cq.createAlias("hiddenDanger","hiddenDanger");
//                    cq.isNotNull("hiddenDanger.dangerId");
                    if(StringUtil.isNotEmpty(yearMonth)){
                        String from = yearMonth+"-01 00:00:00";
                        String end = DateUtils.getLastDay(from);
                        end = end + " 23:59:59";
                        cq.ge("hiddenDanger.examDate",sdf.parse(from));
                        cq.le("hiddenDanger.examDate",sdf.parse(end));
                    }
                    if(StringUtil.isNotEmpty(unitId)){
                        cq.eq("hiddenDanger.dutyUnit.id", unitId);
                    }
                    cq.addOrder("hiddenDanger.examDate", SortDirection.desc);

                }else  if(statisticType.equals("3")){

                }else  if(statisticType.equals("4")){
                    String yearMonth=ResourceUtil.getParameter("yearMonth");
                    String unitId=ResourceUtil.getParameter("unitId");
                    cq.createAlias("hiddenDanger","hiddenDanger");
                    cq.or(Restrictions.isNull("hiddenDanger.riskId.id"), Restrictions.eq("hiddenDanger.riskId.id", ""));
                    cq.or(Restrictions.isNull("hiddenDanger.riskManageHazardFactorId"), Restrictions.eq("hiddenDanger.riskManageHazardFactorId", ""));
                    cq.notEq("handlelStatus","00");
//                    cq.or(Restrictions.isNull("hiddenDanger.dangerId"), Restrictions.eq("hiddenDanger.dangerId", ""));
                    if(StringUtil.isNotEmpty(yearMonth)){
                        String from = yearMonth+"-01 00:00:00";
                        String end = DateUtils.getLastDay(from);
                        end = end + " 23:59:59";
                        cq.ge("hiddenDanger.examDate",sdf.parse(from));
                        cq.le("hiddenDanger.examDate",sdf.parse(end));
                    }
                    if(StringUtil.isNotEmpty(unitId)){
                        cq.eq("hiddenDanger.dutyUnit.id", unitId);
                    }
                    cq.addOrder("hiddenDanger.examDate", SortDirection.desc);

                }else  if(statisticType.equals("5")){

                }

            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
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
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null) {
            if (dataGrid.getResults().size() > 0){
                List<TBHiddenDangerHandleEntity> list = dataGrid.getResults();
                for (TBHiddenDangerHandleEntity t : list) {
                    String names = "";

                    String querySql = "select fill_card_manids man from t_b_hidden_danger_exam where id = '" + String.valueOf(t.getHiddenDanger().getId()) + "'";
                    List<Map<String, Object>> maplist = systemService.findForJdbc(querySql, null);
                    for (Map map : maplist) {
                        String mans = String.valueOf(map.get("man"));
                        if (StringUtils.isNotBlank(mans)) {
                            String[] userIdArray = mans.split(",");

                            for (String userid : userIdArray) {
                                TSUser tsUser = systemService.getEntity(TSUser.class, userid);
                                if (tsUser != null) {
                                    if (names == "") {
                                        names = names + tsUser.getRealName() + "-" + tsUser.getUserName();
                                    } else {
                                        names = names + "," + tsUser.getRealName() + "-" + tsUser.getUserName();
                                    }
                                }else if(StringUtil.isNotEmpty(userid)){
                                    if (names == "") {
                                        names = names + userid;
                                    } else {
                                        names = names + "," + userid;
                                    }
                                }
                            }
                        }
                    }
                    t.getHiddenDanger().setFillCardManNames(names);
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 重大隐患列表跳转（决策分析弹出页面）
     * @param request
     * @return
     */
    @RequestMapping(params = "majorHiddenDangerList")
    public ModelAndView majorHiddenDangerList( HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>();
        //1：矿年度风险隐患变化趋势 2：月度风险隐患部门分布情况 3：专项辨识情况统计 4：未关联危险源隐患列表 5：重大隐患风险上报情况统计
        String statisticType=request.getParameter("statisticType");
        params.put("statisticType",statisticType);
        String yearMonth=request.getParameter("yearMonth");
        params.put("yearMonth",yearMonth);
        String bReport=request.getParameter("bReport");
        params.put("bReport",bReport);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/majorHiddenDangerList",params);
    }

    /**
     * 重大隐患弹出列表
     * @param /tBMajorHiddenDangerEntity
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "majorHiddenDangerDatagrid")
    public void majorHiddenDangerDatagrid(TBMajorHiddenDangerEntity tBMajorHiddenDanger,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBMajorHiddenDangerEntity.class, dataGrid);
        //1：矿年度风险隐患变化趋势 2：月度风险隐患部门分布情况 3：专项辨识情况统计 4：未关联危险源隐患列表 5：重大隐患风险上报情况统计
        String statisticType=ResourceUtil.getParameter("statisticType");

        String bReport=ResourceUtil.getParameter("bReport");

        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBMajorHiddenDanger, request.getParameterMap());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            if(statisticType!=null){
                if(statisticType.equals("1")){
                    String yearMonth=ResourceUtil.getParameter("yearMonth");
                    if(StringUtil.isNotEmpty(yearMonth)){
                        String from = yearMonth+"-01 00:00:00";
                        String end = DateUtils.getLastDay(from);
                        end = end + " 23:59:59";
                        cq.ge("rectPeriod",sdf.parse(from));
                        cq.le("rectPeriod",sdf.parse(end));
                    }
                    cq.addOrder("createDate", SortDirection.desc);

                } else  if(statisticType.equals("2")){

                }else  if(statisticType.equals("3")){

                }else  if(statisticType.equals("4")){

                }else  if(statisticType.equals("5")){
                    String yearMonth=ResourceUtil.getParameter("yearMonth");
                    if(Constants.REPORT_STATUS_Y.equals(bReport)){
                        cq.eq("reportStatus",Constants.REPORT_STATUS_Y);
                    }
                    else if(Constants.REPORT_STATUS_N.equals(bReport)){
                        cq.add(
                                Restrictions.or(Restrictions.eq("reportStatus", Constants.REPORT_STATUS_N),
                                        Restrictions.isNull("reportStatus"))
                        );
                    }

                    if(StringUtil.isNotEmpty(yearMonth)){
                        String from = yearMonth+"-01 00:00:00";
                        String end = DateUtils.getLastDay(from);
                        end = end + " 23:59:59";
                        cq.ge("inveDate",sdf.parse(from));
                        cq.le("inveDate",sdf.parse(end));
                    }

                    cq.addOrder("inveDate", SortDirection.desc);
                }

            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
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
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_major_hidden_danger'";
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
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 未关联危险源隐患情况统计页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "hiddenDangerUnbindDangerSource")
    public ModelAndView hiddenDangerUnbindDangerSource(HttpServletRequest request) {
        String yearMonth = request.getParameter("yearMonth");
        if(StringUtil.isEmpty(yearMonth)){
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            yearMonth = sdf.format(date);
        }
        request.setAttribute("yearMonth", yearMonth);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hiddenDangerUnbindDangerSource");
    }

    /**
     * 未关联危险源隐患情况统计查询
     * @param request
     * @return
     */
    @RequestMapping(params = "hiddenDangerUnbindDangerSourceQuery")
    public void hiddenDangerUnbindDangerSourceQuery(String yearMonth, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        String sql = "select duty_unit unitId, count(*) count from t_b_hidden_danger_exam e, t_b_hidden_danger_handle h where e.id=h.hidden_danger_id and (e.risk_id is null or risk_id='') and (e.risk_manage_hazard_factor_id is null or e.risk_manage_hazard_factor_id='')  and h.handlel_status!='00' ";
        if(StringUtil.isNotEmpty(yearMonth)){
            sql = sql + " and DATE_FORMAT(exam_date,'%Y-%m')='" + yearMonth + "' ";
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
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
            sql += " and h.id not in(select column_id from t_b_sunshine)";
        }
        /*************************************************************/

        sql = sql + " group by duty_unit order by count(*) desc";
        List<Map<String,Object>> list = systemService.findForJdbc(sql);
        dataGrid.setTotal(list.size());
        list = systemService.findForJdbc(sql,dataGrid.getPage(),dataGrid.getRows());
        List<DecisionAnalyseVO> retList = new ArrayList<DecisionAnalyseVO>();
        if(null!=list && !list.isEmpty()){
            for(Map<String,Object> mapTemp : list){
                DecisionAnalyseVO vo = new DecisionAnalyseVO();
                String unitName = "";
                String unitId = String.valueOf(mapTemp.get("unitId"));
                vo.setUnitId(unitId);
                TSDepart unit = systemService.getEntity(TSDepart.class,unitId);
                if(null!=unit){
                    unitName = unit.getDepartname();
                }
                vo.setUnitName(unitName);
                String count = String.valueOf(mapTemp.get("count"));
                vo.setHiddenTotal(count);
                retList.add(vo);
            }
        }
        dataGrid.setResults(retList);
        TagUtil.datagrid(response, dataGrid);

    }

    /**
     * 专项辨识情况跳转
     * @param request
     * @return
     */

    /**
     * 重大隐患风险上报情况统计页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "majorHDAndRiskReport")
    public ModelAndView majorHDAndRiskReport(HttpServletRequest request) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        request.setAttribute("yearMonth", sdf.format(date));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/majorHDAndRiskReport");
    }

    /**
     * 重大隐患风险上报情况统计查询
     * @param request
     * @return
     */
    @RequestMapping(params = "majorHDAndRiskReportQuery")
    @ResponseBody
    public AjaxJson majorHDAndRiskReportQuery(String yearMonth, HttpServletRequest request) {
        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunCommonUser").equals(userRole)){
                isSunRole = true;
            }
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        /*************************************************************/

        AjaxJson j = new AjaxJson();
        String reportStr = "";
        String unreportStr = "";
        //计算重大隐患中上报与未上报数量
        String majorHiddenDangerReportedCount = "0";
        String majorHiddenDangerUnreportedCount = "0";
        String majorHiddenDangerReportedSql = "select count(*) count from t_b_major_hidden_danger where report_status='"
                + Constants.REPORT_STATUS_Y + "' and cl_status = '"+Constants.HIDDEN_DANGER_CLSTATUS_FINISHED+"' and HD_LOCATION not in (select id from t_b_address_info where isShowData = '0' ) and cl_status='"+Constants.HIDDEN_DANGER_CLSTATUS_FINISHED+"'";
        String majorHiddenDangerUnreportedSql = "select count(*) count from t_b_major_hidden_danger where report_status!='"
                + Constants.REPORT_STATUS_Y + "' and cl_status = '"+Constants.HIDDEN_DANGER_CLSTATUS_FINISHED+"' and HD_LOCATION not in (select id from t_b_address_info where isShowData = '0' ) and cl_status='"+Constants.HIDDEN_DANGER_CLSTATUS_FINISHED+"'";
        if(StringUtil.isNotEmpty(yearMonth)){
            majorHiddenDangerReportedSql = majorHiddenDangerReportedSql + " and DATE_FORMAT(inve_date,'%Y-%m')='"
                    + yearMonth + "'";
            majorHiddenDangerUnreportedSql = majorHiddenDangerUnreportedSql + " and DATE_FORMAT(inve_date,'%Y-%m')='"
                    + yearMonth + "'";
        }
        /********************************阳光账号过滤数据****************************/
        if (isSunRole){
            majorHiddenDangerReportedSql += " and id not in (select column_id from t_b_sunshine)";
        }
        /*******************************************************************************/

        List<Map<String,Object>> list = systemService.findForJdbc(majorHiddenDangerReportedSql);
        if(null!=list && !list.isEmpty()){
            Map<String,Object> data = list.get(0);
            majorHiddenDangerReportedCount = String.valueOf(data.get("count"));
        }
        list = systemService.findForJdbc(majorHiddenDangerUnreportedSql);
        if(null!=list && !list.isEmpty()){
            Map<String,Object> data = list.get(0);
            majorHiddenDangerUnreportedCount = String.valueOf(data.get("count"));
        }

        reportStr = reportStr + majorHiddenDangerReportedCount + ",";
        unreportStr = unreportStr + majorHiddenDangerUnreportedCount + ",";

        //计算重大风险中上报与未上报数量
        String majorRiskReportedCount = "0";
        String majorRiskUnreportedCount = "0";
        /*String majorRiskReportedSql = "select count(*) count from (select * from t_b_danger_source where YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+")) d where ismajor='" +
                Constants.IS_MAJORDangerSource_Y + "' and report_status='" +
                Constants.DANGER_SOURCE_REPORT_REPORT + "' and audit_status='"+Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE+"'";
        String majorRiskUnreportedSql = "select count(*) count from (select * from t_b_danger_source where YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+")) d where ismajor='" +
                Constants.IS_MAJORDangerSource_Y + "' and (report_status is null or report_status='" +
                Constants.DANGER_SOURCE_REPORT_UNREPORT + "') and audit_status= '"+Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE+"' ";*/
        String majorRiskReportedSql = "SELECT count(*) count FROM t_b_risk_identification WHERE (is_del = '0' or is_del is null) and status = '3' and risk_level = '1' AND report_status_province = '1' ";
        String majorRiskUnreportedSql = "SELECT count(*) count FROM t_b_risk_identification WHERE (is_del = '0' or is_del is null) and status = '3' and risk_level = '1' AND (report_status_province = '0' or report_status_province is null) ";
        if(StringUtil.isNotEmpty(yearMonth)){
            majorRiskReportedSql = majorRiskReportedSql + "AND DATE_FORMAT(identifi_date, '%Y-%m') <= '"+yearMonth+"' AND DATE_FORMAT(exp_date, '%Y-%m') >= '"+yearMonth+"' ";
            majorRiskUnreportedSql = majorRiskUnreportedSql + "AND DATE_FORMAT(identifi_date, '%Y-%m') <= '"+yearMonth+"' AND DATE_FORMAT(exp_date, '%Y-%m') >= '"+yearMonth+"' ";
        }
        /********************************阳光账号过滤数据****************************/
        if (isSunRole){
            majorRiskUnreportedSql += " and id not in (select column_id from t_b_sunshine)";
        }
        /*******************************************************************************/

        list = systemService.findForJdbc(majorRiskReportedSql);
        if(null!=list && !list.isEmpty()){
            Map<String,Object> data = list.get(0);
            majorRiskReportedCount = String.valueOf(data.get("count"));
        }
        list = systemService.findForJdbc(majorRiskUnreportedSql);
        if(null!=list && !list.isEmpty()){
            Map<String,Object> data = list.get(0);
            majorRiskUnreportedCount = String.valueOf(data.get("count"));
        }

        reportStr = reportStr + majorRiskReportedCount + ",";
        unreportStr = unreportStr + majorRiskUnreportedCount + ",";

        Map<String,String> retMap = new HashMap<>();
        retMap.put("report",reportStr);
        retMap.put("unreport",unreportStr);
        j.setObj(retMap);
        return j;
    }
    /**
     * 专项辨识情况跳转
     * @param request
     * @return
     */

    @RequestMapping(params = "specialIdentificationStatistic")
    public ModelAndView specialIdentificationStatistic(HttpServletRequest request) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        calendar.add(calendar.YEAR, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String yearMonthStart = format.format(calendar.getTime());
        String yearMonthEnd = format.format(new Date());
        request.setAttribute("yearMonthStart",yearMonthStart);
        request.setAttribute("yearMonthEnd",yearMonthEnd);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/specialIdentificationStatistic");
    }

    @RequestMapping(params = "hiddenDangerStatistics")
    public ModelAndView test(HttpServletRequest request) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        request.setAttribute("date", sdf.format(date));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hiddenDangerStatistics");
    }
    /**
     * 专项辨识情况图表数据
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "specialByEvalType")
    @ResponseBody
    public JSONObject specialByEvalType(HttpServletRequest request) throws Exception {
        String yearMonthStart = request.getParameter("yearMonthStart");
        String yearMonthEnd = request.getParameter("yearMonthEnd");
        StringBuffer sb = new StringBuffer();
        sb.append("select count(1) as count,e.specific_type type from t_b_risk_identification e where 1=1 and e.status = '3' and e.is_del = '0' and identification_type = '3' AND (specific_type != '' or specific_type is not null) ");

        if(StringUtils.isNotBlank(yearMonthStart) && StringUtils.isNotBlank(yearMonthEnd)){
            sb.append(" and DATE_FORMAT(e.identifi_date,'%Y-%m-%d') between '").append(yearMonthStart).append("' and '").append(yearMonthEnd).append("'");
        }
        sb.append(" group by e.specific_type");
        List<Map<String, Object>> queryList = systemService.findForJdbc(sb.toString());
        String result = "{\'pieData\':[";
        if(queryList != null && !queryList.isEmpty()){
            int index = 0;
            for(int i =0 ;i<queryList.size();i++){
                index++;
                Map<String,Object> tempMap = queryList.get(i);
                if(tempMap.get("type") != null && tempMap.get("count") != null){
                    String type = tempMap.get("type").toString();
                    String count = tempMap.get("count").toString();
                    String evaluationTypeName = DicUtil.getTypeNameByCode("specificType",type);
                    result = result + "{\'name\':\'" +evaluationTypeName
                            + "\'," + "\'id\':\'" + type
                            + "\'," + "\'y\':"
                            + Integer.valueOf(count)
                            + "},";
                }
            }
        }else{
            result = result + "{\'name\':\'暂无数据\'," + "\'id\':\'暂无数据\'," + "\'y\':100,\'color\':\'#68AD30\'},";
        }
        result += "]}";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pieData",result);
        return jsonObject;
    }


    /**
     * 矿年度风险隐患变化趋势
     * Author：张赛超
     * */
    @RequestMapping(params = "mineYearRiskTrendReport")
    public ModelAndView mineYearRiskTrendReport(HttpServletRequest request) {
        String year = request.getParameter("year");
        if(StringUtils.isBlank(year)){
            Calendar ca = Calendar.getInstance();
            year =  ca.get(Calendar.YEAR)+"";
        }
        request.setAttribute("year",year);
        generateDate(request,year);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/mineYearRiskTrendReport");
    }

    /**组装矿年度风险隐患变化趋势数据*/
    private List<MineYearRiskTrendEntity> generateDate(HttpServletRequest request, String year){
        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
        List<String> userRoleList = systemService.findListbySql(userRoleSql);
        for (String userRole : userRoleList){
            if(ResourceUtil.getConfigByName("sunCommonUser").equals(userRole)){
                isSunRole = true;
            }
            if(ResourceUtil.getConfigByName("sunAdmin").equals(userRole)){
                isSunRole = true;
            }
        }
        /*************************************************************/


        String majorDangerData = "";        //highcharts中data数据——重大隐患
        String dangerData = "";             //highcharts中data数据——隐患
        String riskData = "";               //highcharts中data数据——风险

        String yearMonth = "";
        int iMonth = 1;
        int majorDangerNum = 0; //重大隐患
        int dangerNum = 0;      //隐患
        int riskNum = 0;        //风险
        int total = 0;          //总数

        if(StringUtils.isBlank(year)){
            Calendar ca = Calendar.getInstance();
            year = ca.get(Calendar.YEAR)+"";
        }
        String addressId = request.getParameter("addressId");
        String riskType = request.getParameter("riskType");

        List<TBMajorHiddenDangerEntity> majorDangerLists = new ArrayList();
        List<TBHiddenDangerHandleEntity> dangerLists = new ArrayList();
        //统计风险需要统计普通风险和专项风险两部分 //修改
        /*List<TBDangerSourceEntity> riskLists = new ArrayList();
        List<TBSpecialEvaluationEntity> specialRiskLists = new ArrayList();*/
        List<RiskIdentificationEntity> riskLists = new ArrayList();
        //返回结果
        List<MineYearRiskTrendEntity> resultList = new ArrayList();
        Date nowDate = new Date();

        Calendar  to  =  Calendar.getInstance();
        to.setTime(nowDate);
        //只要年月
        int nowDateMonth = to.get(Calendar.MONTH);
        String yearTemp = String.valueOf(to.get(Calendar.YEAR));
        if(!yearTemp.equals(year)){
            nowDateMonth = 11;
        }
        //这里查询一年的十二个月份
        for(;iMonth <= nowDateMonth+1; iMonth++){
            if(iMonth < 10){
                yearMonth = year + "-0" + iMonth;
            }else{
                yearMonth = year + "-" + iMonth;
            }

            /**统计重大隐患——开始*/
            String majorCq = "select id from t_b_major_hidden_danger where inve_date like '" + yearMonth + "%' and HD_LOCATION not in (select id from t_b_address_info where isShowData = '0' ) and cl_status='"+Constants.HIDDEN_DANGER_CLSTATUS_FINISHED+"'";
            /**********************阳光账号过滤部分数据开始********************/
            if (isSunRole){
                majorCq += (" and id not in (select column_id from t_b_sunshine)");
            }
            /**********************************************************************/
            majorDangerLists = systemService.findListbySql(majorCq);
            //获取当月对应的重大隐患的数量
            majorDangerNum = majorDangerLists.size();
            /**统计重大隐患——结束*/

            /**统计隐患——开始*/
            String dangerCq = "select id from t_b_hidden_danger_exam where address not in (select id from t_b_address_info where isShowData = '0' ) and id in(select hidden_danger_id from t_b_hidden_danger_handle where handlel_status = '"+Constants.REVIEWSTATUS_PASS+"'";
            /**********************阳光账号过滤部分数据开始********************/
            if (isSunRole){
                dangerCq += (" and id not in (select column_id from t_b_sunshine)");
            }
            /**********************************************************************/
            dangerCq += ") and  exam_date like '"+ yearMonth +"%'";
            if(StringUtil.isNotEmpty(addressId)){
                dangerCq += " and  address = '"+ addressId +"'";
            }
            if(StringUtil.isNotEmpty(riskType)){
                dangerCq += " and  risk_type = '"+ riskType +"'";
            }
            dangerLists = systemService.findListbySql(dangerCq);
            //获取当月对应的隐患的数量
            dangerNum = dangerLists.size();
            /**统计隐患——结束*/

            /**统计风险——开始*/
            /*int num1 = 0; int num2 = 0;
            String riskCq = "select id from (select * from t_b_danger_source where YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+")) d where origin='2' and is_delete='0' and ye_recognize_time >= '"+year+"-01-01' and ye_recognize_time <= '"+yearMonth+"-31' and audit_status = '"+Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE+"'";

            *//**********************阳光账号过滤部分数据开始********************//*
            if (isSunRole){
                riskCq += (" and id not in (select column_id from t_b_sunshine)");
            }
            *//**********************************************************************//*
            riskLists = systemService.findListbySql(riskCq);
            //获取之前每个月对应的专项风险数量
            num1 = riskLists.size();
            String specialRiskCq = "select id from (select * from t_b_danger_source where YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+")) d where origin='3' and ye_recognize_time >= '"+year+"-01-01' and ye_recognize_time <= '"+yearMonth+"-31' and audit_status = '"+Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE+"'";

            *//**********************阳光账号过滤部分数据开始********************//*
            if (isSunRole){
                specialRiskCq += (" and id not in (select column_id from t_b_sunshine)");
            }
            *//**********************************************************************//*
            specialRiskLists = systemService.findListbySql(specialRiskCq);
            //获取当月对应的专项风险数量
            num2 = specialRiskLists.size();
            riskNum = num1 + num2;*/
            String sql = "SELECT id FROM t_b_risk_identification where (is_del = '0' or is_del is null ) and status = '3' and identifi_date <= '"+yearMonth+"-31' and exp_date >= '"+yearMonth+"-31'";
            if (isSunRole){
                sql += (" and id not in (select column_id from t_b_sunshine)");
            }
            if(StringUtil.isNotEmpty(addressId)){
                sql += " and  address_id = '"+ addressId +"'";
            }
            if(StringUtil.isNotEmpty(riskType)){
                sql += " and  risk_type = '"+ riskType +"'";
            }
            riskLists = systemService.findListbySql(sql);
            riskNum =riskLists.size();
            /**统计风险——结束*/
            //计算总数
            total = majorDangerNum + dangerNum + riskNum;

            MineYearRiskTrendEntity vo = new MineYearRiskTrendEntity();
            vo.setMonth(yearMonth);
            vo.setMajorDanger(majorDangerNum + "");
            vo.setDanger(dangerNum + "");
            vo.setRisk(riskNum + "");
            vo.setTotal(total + "");
            resultList.add(vo);
        }

        for(MineYearRiskTrendEntity single : resultList){
            majorDangerData = majorDangerData + "," + single.getMajorDanger();
            dangerData = dangerData + "," + single.getDanger();
            riskData = riskData + "," + single.getRisk();
        }
        majorDangerData = majorDangerData.substring(1, majorDangerData.length());
        dangerData = dangerData.substring(1 ,dangerData.length());
        riskData = riskData.substring(1, riskData.length());

        request.setAttribute("majorDangerData",majorDangerData);
        request.setAttribute("dangerData",dangerData);
        request.setAttribute("riskData",riskData);

        return resultList;
    }


    /**
     * easyui AJAX请求数据
     *
     * 矿年度隐患风险变化趋势datagrid页面组装
     *
     */
    @RequestMapping(params = "mineYearRiskTrendDatagrid")
    public void queryListDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String year = request.getParameter("year");
        if(StringUtils.isBlank(year)){
            Calendar ca = Calendar.getInstance();
            year =  ca.get(Calendar.YEAR)+"";
        }
        request.setAttribute("year",year);
        String addressId = request.getParameter("addressId");
        request.setAttribute("addressId",addressId);
        String riskType = request.getParameter("riskType");
        request.setAttribute("riskType",riskType);
        dataGrid.setResults(generateDate(request,year));
        TagUtil.datagrid(response, dataGrid);

    }

    /**
     * 专项辨识统计列表
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "specialidetificationDatagrid")
    public void specialidetificationDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String isMonthQuery = request.getParameter("isMonthQuery");
        request.setAttribute("isMonthQuery",isMonthQuery);
        String yearMonthStart = request.getParameter("yearMonthStart");
        String yearMonthEnd = request.getParameter("yearMonthEnd");
        //获取传递过来的type，type代表专项辨识分布中饼图的类型
        String clickType = request.getParameter("clickType");

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ri.id id, ri.specific_type specificType, ri.specific_name specificName, DATE_FORMAT( ri.identifi_date, '%Y-%m-%d' ) identifiDate, DATE_FORMAT(ri.exp_date, '%Y-%m-%d') expDate, ai.address, ri.risk_type riskType, ri.risk_desc riskDesc, ri.risk_level riskLevel, COUNT(rfr.id) hazardFactortsNum, ri.duty_manager dutyManager, ri.manage_level manageLevel FROM t_b_risk_identification ri LEFT JOIN t_b_address_info ai ON ai.id = ri.address_id LEFT JOIN t_b_risk_factors_rel rfr ON ri.id = rfr.risk_identification_id WHERE 1 = 1 AND ri.STATUS = '3' AND ri.is_del = '0' AND ri.identification_type = '3' AND ( ri.specific_type != '' OR ri.specific_type IS NOT NULL ) ");
        if(StringUtils.isNotBlank(yearMonthStart) && StringUtils.isNotBlank(yearMonthEnd)){
         sb.append(" and DATE_FORMAT(ri.identifi_date,'%Y-%m-%d') between'"+yearMonthStart+"' and '"+yearMonthEnd+"'");
        }
        if(StringUtil.isNotEmpty(clickType)){
            sb.append("and ri.specific_type='" + clickType + "'");
        }
        sb.append(" GROUP BY ri.id");
        int currentPage = dataGrid.getPage();
        int pageSize = dataGrid.getRows();
        int endIndex = pageSize * currentPage;
        List<Map<String, Object>> retResult =  systemService.findForJdbc(sb.toString());

        List<String> retKey = new ArrayList<String>();
        List<Map<String, Object>> sonResult =  systemService.findForJdbc(sb.toString());
        if(retResult != null ){
            if(pageSize *currentPage > retResult.size()){
                endIndex = retResult.size();
            }
             sonResult = retResult.subList(pageSize *(currentPage -1), endIndex);
        }
        if (sonResult != null && sonResult.size() > 0) {
            for (Map<String, Object> map: sonResult) {
                map.put("riskType", DicUtil.getTypeNameByCode("risk_type", (String) map.get("riskType")).equals("")?(String) map.get("riskType"):DicUtil.getTypeNameByCode("risk_type", (String) map.get("riskType")));
                map.put("riskLevel", DicUtil.getTypeNameByCode("factors_level", (String) map.get("riskLevel")).equals("")?(String) map.get("riskLevel"):DicUtil.getTypeNameByCode("factors_level", (String) map.get("riskLevel")));
                map.put("specificType", DicUtil.getTypeNameByCode("specificType", (String) map.get("specificType")).equals("")?(String) map.get("specificType"):DicUtil.getTypeNameByCode("specificType", (String) map.get("specificType")));
                map.put("manageLevel", DicUtil.getTypeNameByCode("identifi_mange_level", (String) map.get("manageLevel")).equals("")?(String) map.get("manageLevel"):DicUtil.getTypeNameByCode("identifi_mange_level", (String) map.get("manageLevel")));
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", retResult == null?0:retResult.size());
        jsonObject.put("rows", sonResult);
   //     request.getSession().setAttribute("retList", retList);
        responseDatagrid(response, jsonObject);
    }

    /**
     * 风险列表跳转（决策分析弹出页面）
     * @param request
     * @return
     */
    @RequestMapping(params = "dangerList")
    public ModelAndView dangerList( HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>();
        //1：矿年度风险隐患变化趋势 2：月度风险隐患部门分布情况 3：专项辨识情况统计 4：未关联危险源隐患列表 5：重大隐患风险上报情况统计
        String statisticType=request.getParameter("statisticType");
        String specialid=request.getParameter("specialid");
        String unitId=request.getParameter("unitId");

        String bMajor=request.getParameter("bMajor");
        String yearMonth=request.getParameter("yearMonth");
        String year=request.getParameter("year");
        String bReport=request.getParameter("bReport");

        params.put("statisticType", statisticType);
        params.put("specialid", specialid);
        params.put("unitId", unitId);
        request.setAttribute("yearMonth", yearMonth);
        request.setAttribute("year", year);
        params.put("bMajor",bMajor);
        params.put("yearMonth",yearMonth);
        params.put("bReport",bReport);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/dangerList",params);
    }

    /**
     * 风险列表跳转（决策分析弹出页面）
     * @param request
     * @return
     */
    @RequestMapping(params = "riskList")
    public ModelAndView riskList( HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>();
        //1：矿年度风险隐患变化趋势 2：月度风险隐患部门分布情况 3：专项辨识情况统计 4：未关联危险源隐患列表 5：重大隐患风险上报情况统计
        String statisticType=request.getParameter("statisticType");
        String specialid=request.getParameter("specialid");
        String unitId=request.getParameter("unitId");

        String bMajor=request.getParameter("bMajor");
        String yearMonth=request.getParameter("yearMonth");
        String year=request.getParameter("year");
        String bReport=request.getParameter("bReport");

        params.put("statisticType", statisticType);
        params.put("specialid", specialid);
        params.put("unitId", unitId);
        request.setAttribute("yearMonth", yearMonth);
        request.setAttribute("year", year);
        params.put("bMajor",bMajor);
        params.put("yearMonth",yearMonth);
        params.put("bReport",bReport);
        String newPost = ResourceUtil.getConfigByName("newPost");
        request.setAttribute("newPost",newPost);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/riskList",params);
    }

    /**
     * 危险源查询
     * @param tBDangerSource
     * @param request
     * @param response
     * @param dataGrid
     * 风险
     */
    @RequestMapping(params = "dangerSourceDatagrid")
    public void dangerSourceDatagrid(TBDangerSourceEntity tBDangerSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        String dangerSourceName = tBDangerSource.getYeMhazardDesc();
        tBDangerSource.setYeMhazardDesc(null);
        String possiblyHazard = tBDangerSource.getYePossiblyHazard();
        tBDangerSource.setYePossiblyHazard(null);
        String docSource = tBDangerSource.getDocSource();
        tBDangerSource.setDocSource(null);
        String standard = tBDangerSource.getYeStandard();
        tBDangerSource.setYeStandard(null);
        //1：矿年度风险隐患变化趋势 2：月度风险隐患部门分布情况 3：专项辨识情况统计 4：未关联危险源隐患列表 5：重大隐患风险上报情况统计
        String statisticType=ResourceUtil.getParameter("statisticType");
        String yearMonth=ResourceUtil.getParameter("yearMonth");
        String year=ResourceUtil.getParameter("year");
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBDangerSource, request.getParameterMap());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            if(statisticType!=null){
                cq.eq("isDelete",Constants.IS_DELETE_N);
                if(statisticType.equals("1")){

                    yearMonth = ResourceUtil.getParameter("yearMonth");
                    //风险来自于专项风险和非专项风险，通用来源对应1，本矿来源对应2，专项来源对应3
                    String sql = "select id from t_b_danger_source where origin <> 1 and ye_recognize_time >= '"+year+"-01-01' and ye_recognize_time <= '"+yearMonth+"-31' and audit_status = '"+Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE+"'";
                    List<Map<String, Object>> result = systemService.findForJdbc(sql);

                    if (result != null && result.size() >0) {
                        String[] param = new String[result.size()];
                        for (int i = 0 ; i < param.length; i++) {
                            param[i] = (String) result.get(i).get("id");
                        }
                        cq.in("id", param);
                    } else {
                        cq.eq("id", "null");
                    }

                } else  if(statisticType.equals("2")){

//                    String yearMonth = ResourceUtil.getParameter("yearMonth");
                    String unitId = ResourceUtil.getParameter("unitId");

                    String sql = "SELECT * FROM t_b_danger_source WHERE id IN ( SELECT a.danger_id FROM t_b_address_depart_rel ddr, t_b_danger_address_rel a, ( SELECT * FROM t_b_danger_source WHERE YE_RISK_GRADE IN ( SELECT t.typecode FROM t_s_type t WHERE t.typegroupid = ( SELECT g.id FROM t_s_typegroup g WHERE g.typegroupcode = 'riskLevel' ) AND t.is_hide = 0 )) ds WHERE ds.id = a.danger_id AND ddr.depart_id = '"+unitId+"' AND ddr.address_id = a.address_id ) and ye_recognize_time <= '"+yearMonth+"-31' and audit_status = '"+Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE+"';";
                    List<Map<String, Object>> result = systemService.findForJdbc(sql);
                    if (result != null && result.size() >0) {
                        String[] param = new String[result.size()];
                        for (int i = 0 ; i < param.length; i++) {
                            param[i] = (String) result.get(i).get("id");
                        }
                        cq.in("id", param);
                    } else {
                        cq.eq("id", "null");
                    }

                }else  if(statisticType.equals("3")){
                    String specialid = ResourceUtil.getParameter("specialid");
                    String sql = "select danger_source_id from t_b_se_ds_relation where sepcial_evaluation_id = '"+specialid+"'";
                    List<Map<String, Object>> result = systemService.findForJdbc(sql);
                    if (result != null && result.size() >0) {
                        String[] param = new String[result.size()];
                        for (int i = 0 ; i < param.length; i++) {
                            param[i] = (String) result.get(i).get("danger_source_id");
                        }
                        cq.in("id", param);
                    } else {
                        cq.eq("id", "null");
                    }
                }else  if(statisticType.equals("4")){

                }else  if(statisticType.equals("5")){
                    String bMajor=ResourceUtil.getParameter("bMajor");
                    if(StringUtil.isNotEmpty(bMajor)){
                        cq.eq("isMajor",bMajor);
                    }

                    String bReport=ResourceUtil.getParameter("bReport");
                    if(Constants.REPORT_STATUS_Y.equals(bReport)){
                        cq.eq("reportStatus",Constants.DANGER_SOURCE_REPORT_REPORT);
                    }
                    else if(Constants.REPORT_STATUS_N.equals(bReport)){
                        cq.add(
                                Restrictions.or(Restrictions.eq("reportStatus", Constants.DANGER_SOURCE_REPORT_UNREPORT),
                                        Restrictions.isNull("reportStatus"))
                        );
                    }

                    if(StringUtil.isNotEmpty(yearMonth)){
                        if(StringUtils.isBlank(year)){
                            year = yearMonth.substring(0,4);
                        }
                        String start = year+"-01-01 00:00:00";
                        String from = yearMonth+"-01 00:00:00";
                        String end = DateUtils.getLastDay(from);
                        end = end + " 23:59:59";
                        cq.ge("yeRecognizeTime",sdf.parse(from));
                        cq.ge("yeRecognizeTime",sdf.parse(start));
                        cq.le("yeRecognizeTime",sdf.parse(end));
                    }

                    cq.addOrder("yeRecognizeTime", SortDirection.desc);
                }
            }

            if (StringUtils.isNotBlank(possiblyHazard)) {
                cq.like("yePossiblyHazard","%"+possiblyHazard+"%");
            }
            if (StringUtils.isNotBlank(docSource)) {
                cq.like("docSource","%"+docSource+"%");
            }
            if (StringUtils.isNotBlank(standard)) {
                cq.like("yeStandard","%"+standard+"%");
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
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
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_danger_source'";
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
//        DataGridReturn dataGridReturn = this.systemService.getDataGridReturn(cq, true);
        queryByDangerSourceName(dangerSourceName, cq, dataGrid);
        if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() > 0){
            List<TBDangerSourceEntity> tempList = dataGrid.getResults();
            for(TBDangerSourceEntity bean : tempList){
                //字典
                String sgxlStr = bean.getYeAccident();
                if(StringUtils.isNotBlank(sgxlStr)){
                    String [] sgxlArray = sgxlStr.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String str : sgxlArray){
                        String retName = DicUtil.getTypeNameByCode("accidentCate", str);
                        if(StringUtils.isNotBlank(sb.toString())){
                            sb.append(",");
                        }
                        sb.append(retName);
                    }
                    bean.setYeAccidentTemp(sb.toString());
                }

            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "riskDatagrid")
    public void riskDatagrid(RiskIdentificationEntity riskIdentificationEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        //1：矿年度风险隐患变化趋势  5：重大隐患风险上报情况统计
        String statisticType=ResourceUtil.getParameter("statisticType");
        String yearMonth=ResourceUtil.getParameter("yearMonth");
        String year=ResourceUtil.getParameter("year");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String address = request.getParameter("address.address");
        String postName = request.getParameter("post.postName");
        String riskType = request.getParameter("riskType");
        String riskLevel = request.getParameter("riskLevel");
        String manageLevel = request.getParameter("manageLevel");
        String dutyManager = request.getParameter("dutyManager");
        String addressId = request.getParameter("addressId");
        String postId = request.getParameter("postId");
        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
        try {
            cq.eq("status", com.sddb.common.Constants.RISK_IDENTIFI_STATUS_REVIEW);
            cq.eq("isDel", com.sddb.common.Constants.RISK_IS_DEL_FALSE);
            if(StringUtils.isNotBlank(address)){
                cq.createAlias("address","address");
                cq.like("address.address","%"+address+"%");
            }
            if(StringUtils.isNotBlank(postName)){
                cq.createAlias("post","post");
                cq.like("post.postName","%"+postName+"%");
            }
            if(StringUtils.isNotBlank(riskType)){
                cq.eq("riskType",riskType);
            }
            if(StringUtils.isNotBlank(riskLevel)){
                cq.eq("riskLevel",riskLevel);
            }
            if(StringUtils.isNotBlank(manageLevel)){
                cq.eq("manageLevel",manageLevel);
            }
            if(StringUtils.isNotBlank(dutyManager)){
                cq.like("dutyManager","%"+dutyManager+"%");
            }

            if(StringUtils.isNotBlank(addressId)){
                cq.createAlias("address","address");
                cq.eq("address.id",addressId);
            }
            if(StringUtils.isNotBlank(postId)){
                cq.createAlias("post","post");
                cq.eq("post.id",postId);
            }
            if(statisticType!=null){
                if(statisticType.equals("1")){
                    yearMonth = ResourceUtil.getParameter("yearMonth");
                   // String sql = "select id from t_b_danger_source where origin <> 1 and ye_recognize_time >= '"+year+"-01-01' and ye_recognize_time <= '"+yearMonth+"-31' and audit_status = '"+Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE+"'";
                   String sql = " SELECT id FROM t_b_risk_identification where (is_del = '0' or is_del is null ) and status = '3' and identifi_date <= '"+yearMonth+"-31' and exp_date >= '"+yearMonth+"-31'";
                    List<Map<String, Object>> result = systemService.findForJdbc(sql);

                    if (result != null && result.size() >0) {
                        String[] param = new String[result.size()];
                        for (int i = 0 ; i < param.length; i++) {
                            param[i] = (String) result.get(i).get("id");
                        }
                        cq.in("id", param);
                    } else {
                        cq.eq("id", "null");
                    }
                } else  if(statisticType.equals("5")){
                    String bMajor=ResourceUtil.getParameter("bMajor");
                    if(StringUtil.isNotEmpty(bMajor)){
                        cq.eq("riskLevel",bMajor);
                    }

                    String bReport=ResourceUtil.getParameter("bReport");
                    if(StringUtil.isNotEmpty(bReport)){
                        if(bReport.equals("1")){
                            cq.eq("reportStatusProvince",1);
                        } else if(bReport.equals("0")){
                            cq.add(
                                    Restrictions.or(Restrictions.eq("reportStatusProvince",0),
                                            Restrictions.isNull("reportStatusProvince"))
                            );
                        }

                    }
                    if(StringUtil.isNotEmpty(yearMonth)){
                        cq.add(Restrictions.sqlRestriction(" this_.id in (SELECT id FROM t_b_risk_identification WHERE DATE_FORMAT(identifi_date, '%Y-%m') <= '"+yearMonth+"' AND DATE_FORMAT(exp_date, '%Y-%m') >= '"+yearMonth+"' )"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
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
            String sunSql = "select column_id from t_b_sunshine where table_name='t_b_danger_source'";
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
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskIdentificationEntity> list = dataGrid.getResults();
            for (RiskIdentificationEntity bean : list) {
                List<RiskFactortsRel> relList = bean.getRelList();
                if (relList == null) {
                    bean.setHazardFactortsNum("0");
                }
                bean.setHazardFactortsNum(relList.size() + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    private void queryByDangerSourceName(String dangerSourceName, CriteriaQuery cq, DataGrid dataGrid) {
        if (StringUtils.isBlank(dangerSourceName)) {
            this.systemService.getDataGridReturn(cq, true);
        } else if (!dangerSourceName.contains(" ")){
            cq.like("yeMhazardDesc","%"+dangerSourceName+"%");
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
        } else {
            cq.like("yeMhazardDesc","%"+dangerSourceName.split(" ")[0]+"%");
            cq.add();
            this.systemService.getDataGridReturn(cq, false);
            if(dataGrid != null && dataGrid.getResults() != null && dataGrid.getResults().size() >0){
                List<TBDangerSourceEntity> tempList= dataGrid.getResults();
                List<TBDangerSourceEntity> resultList = new ArrayList<TBDangerSourceEntity>();
                for(TBDangerSourceEntity t : tempList){
                    String dangerSourceNameTemp = dangerSourceName;

                    if(StringUtils.isNotBlank(dangerSourceNameTemp)){
                        String dangerNames[] = dangerSourceNameTemp.split(" ");
                        boolean val = valDangerSource(t,dangerNames);
                        if(val){
                            resultList.add(t);
                        }
                    }else{
                        resultList.add(t);
                    }
                }
                int currentPage = dataGrid.getPage();
                int pageSize = dataGrid.getRows();
                int endIndex = pageSize * currentPage;

                if(pageSize *currentPage > resultList.size()){
                    endIndex = resultList.size();
                }

                List<TBDangerSourceEntity> retList = resultList.subList(pageSize *(currentPage -1), endIndex);

                dataGrid.setResults(retList);
                dataGrid.setTotal(resultList.size());
            }
        }
    }

    public boolean valDangerSource(TBDangerSourceEntity dangerSourceEntity, String[] dangerNames){
        boolean val = true;
        if(dangerNames!= null){
            for(String nameTemp:dangerNames){
                if(org.apache.commons.lang.StringUtils.isNotBlank(nameTemp)){
                    String dangerName = dangerSourceEntity.getYeMhazardDesc();
                    if(dangerName.indexOf(nameTemp) < 0){
                        val = false;
                    }
                }
            }
        }
        return val;
    }


    /**
     * 月度风险隐患部门分布情况 //风险没有部门了 去除风险
     * @param request
     * @return
     */
    @RequestMapping(params = "riskHiddenDepartDistribution")
    public ModelAndView riskHiddenDepartDistribution(HttpServletRequest request) {
        String yearMonth = request.getParameter("yearMonth");
        if(StringUtils.isBlank(yearMonth)){
             SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
            yearMonth = date_sdf.format(new Date());
        }
        request.setAttribute("yearMonth",yearMonth);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/riskHiddenDepartDistribution");
    }

    /**
     * 各单位风险隐患数统计
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "riskHiddenListByYearMonthDatagrid")
    public void riskHiddenListByYearMonthDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String yearMonth = request.getParameter("yearMonth");
        String sql = "select dep.id,dep.departname,IFNULL(risktemp.risknum,0) risknum,IFNULL(hiddentemp.hiddennum,0) hiddennum from t_s_depart dep " +
                " left join (select count(distinct ds.id) risknum,ddr.depart_id from t_b_address_depart_rel ddr, t_b_danger_address_rel a ,(select * from t_b_danger_source where YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+")) ds where ds.id=a.danger_id AND ddr.address_id=a.address_id and DATE_FORMAT(ds.ye_recognize_time,'%Y-%m')='" +
                yearMonth+"'"+
                "  group by ddr.depart_id ) risktemp on risktemp.depart_id=dep.id " +
                "left join (select count(1) hiddennum,hde.duty_unit from t_b_hidden_danger_exam hde where hde.address not in (select address.id from t_b_address_info address where address.isshowdata = '0')  and";

        /*******************阳光账号排除部分内容*******************/
        //判断用户的角色是否是阳光账号
        Boolean isSunRole = false;

        TSUser user = ResourceUtil.getSessionUserName();
        String userRoleSql = "select rolecode from t_s_role where id in (select roleid from t_s_role_user where userid = '"+ user.getId() +"')";
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
            sql += " hde.id not in (select hidden_danger_id from t_b_hidden_danger_handle where id in (select column_id from t_b_sunshine)) and";
        }
        /*************************************************************/

         sql += " DATE_FORMAT(hde.exam_date,'%Y-%m')='" +yearMonth+
                "'   group by hde.duty_unit) hiddentemp on hiddentemp.duty_unit=dep.id where (IFNULL(risktemp.risknum,0)+IFNULL(hiddentemp.hiddennum,0))>0 order by (IFNULL(risktemp.risknum,0)+IFNULL(hiddentemp.hiddennum,0)) desc" ;
        List<Map<String, Object>> quertList = systemService.findForJdbc(sql);
        dataGrid.setTotal(quertList.size());
        dataGrid.setResults(quertList);
        TagUtil.datagrid(response, dataGrid);
    }

    public void responseDatagrid(HttpServletResponse response, JSONObject jObject) {
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store");
        try {
            PrintWriter pw=response.getWriter();
            pw.write(jObject.toString());
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 矿年度风险隐患变化趋势点击跳转页面
     * Author：张赛超
     * */
    @RequestMapping(params = "mineYearDangerList")
    public ModelAndView mineYearDangerList( HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>();
        //1：矿年度风险隐患变化趋势 2：月度风险隐患部门分布情况 3：专项辨识情况统计 4：未关联危险源隐患列表 5：重大隐患风险上报情况统计
        String statisticType=request.getParameter("statisticType");
        params.put("statisticType",statisticType);
        String yearMonth=request.getParameter("yearMonth");
        params.put("yearMonth",yearMonth);

        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hiddenDangerList",params);

    }


    /**
     * 矿年度风险隐患变化趋势——重大隐患点击跳转页面
     * */

    @RequestMapping(params = "mineYearMajorDangerList")
    public ModelAndView mineYearMajorDangerList( HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>();
        //1：矿年度风险隐患变化趋势——重大隐患
        String yearMonth=request.getParameter("yearMonth");
        params.put("yearMonth", yearMonth);

        request.setAttribute("yearMonth", yearMonth);
        request.setAttribute("statisticType", "5");
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/majorHiddenDangerList",params);

    }

    /**
     * 过程管控结果分析
     * */

    @RequestMapping(params = "riskManageStatistics")
    public ModelAndView riskManageStatistics( HttpServletRequest request) {
        String yearMonth = request.getParameter("yearMonth");
        if(StringUtils.isBlank(yearMonth)){
            SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
            yearMonth = date_sdf.format(new Date());
        }
        request.setAttribute("yearMonth",yearMonth);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/riskManageStatistics");
    }

    @RequestMapping(params = "riskManageStatisticsDatagrid")
    public void riskManageStatisticsDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        List<Map<String,Object>> queryList = new ArrayList<>();
        String manageDate = request.getParameter("manageDate");
        String sort = dataGrid.getSort();
        String sortOrder = dataGrid.getOrder();
        StringBuffer sql = new StringBuffer();
        if(StringUtil.isNotEmpty(sort)){
            if(sort.equals("manageNum")){
                sql.append("SELECT COUNT( DISTINCT rm.risk_id, rm.my_user_id ) manageNum, ri.id, ri.address_id address, ri.risk_type riskType, ri.risk_desc riskDesc, ri.risk_level riskLevel FROM t_b_risk_manage_task rmt LEFT JOIN t_b_risk_manage rm ON rm.risk_id = rmt.risk_id ");
            }
            if(sort.equals("implNum")){
                sql.append("SELECT COUNT(DISTINCT rmhf.impl_detail,rmhf.id) implNum, ri.id, ri.address_id address, ri.risk_type riskType, ri.risk_desc riskDesc, ri.risk_level riskLevel FROM t_b_risk_manage_hazard_factor rmhf LEFT JOIN t_b_risk_manage_task rmt ON rmhf.risk_id = rmt.risk_id ");
            }
            if(sort.equals("hdNum")){
                sql.append("SELECT COUNT(DISTINCT rmhd.id,rmhd.risk_manage_hazard_factor_id) hdNum, ri.id, ri.address_id address, ri.risk_type riskType, ri.risk_desc riskDesc, ri.risk_level riskLevel FROM t_b_risk_manage_task rmt LEFT JOIN t_b_risk_manage_hazard_factor rm ON rmt.risk_id = rm.risk_id LEFT JOIN t_b_risk_manage_rel_hd rmhd ON rmhd.risk_manage_hazard_factor_id = rm.id  ");
            }
            if(sort.equals("riskLevel")){
                sql.append("SELECT ri.id, ri.address_id address, ri.risk_type riskType, ri.risk_desc riskDesc, ri.risk_level riskLevel FROM t_b_risk_manage_task rmt LEFT JOIN t_b_risk_manage_hazard_factor rm ON rmt.risk_id = rm.risk_id LEFT JOIN t_b_risk_manage_rel_hd rmhd ON rmhd.risk_manage_hazard_factor_id = rm.id  ");
            }
        }else{
            sql.append("SELECT COUNT( DISTINCT rm.risk_id, rm.my_user_id ) manageNum, ri.id, ri.address_id address, ri.risk_type riskType, ri.risk_desc riskDesc, ri.risk_level riskLevel FROM t_b_risk_manage_task rmt LEFT JOIN t_b_risk_manage rm ON rm.risk_id = rmt.risk_id ");
        }
        sql.append(" LEFT JOIN t_b_risk_identification ri ON ri.id = rmt.risk_id WHERE rmt.manage_type != 'post' ");
        if(StringUtil.isNotEmpty(manageDate)){
            sql.append(" and rmt.create_date >= '"+manageDate+"-01' and rmt.create_date <= '"+manageDate+"-31' ");
        }
        sql.append(" GROUP BY rmt.risk_id ");
        if(StringUtil.isNotEmpty(sort)){
            if(sort.equals("manageNum")){
                sql.append(" ORDER BY manageNum "+sortOrder+" ");
            }
            if(sort.equals("implNum")){
                sql.append(" ORDER BY implNum "+sortOrder+" ");
            }
            if(sort.equals("hdNum")){
                sql.append(" ORDER BY hdNum "+sortOrder+" ");
            }
            if(sort.equals("riskLevel")){
                sql.append(" ORDER BY riskLevel "+sortOrder+" ");
            }
        }else{
            sql.append(" ORDER BY manageNum desc ");
        }
        queryList = this.systemService.findForJdbc(sql.toString());
        int currentPage = dataGrid.getPage();
        int pageSize = dataGrid.getRows();
        int endIndex = pageSize * currentPage;

        if(endIndex > queryList.size()){
            endIndex = queryList.size();
        }
        List<Map<String,Object>> resultList = queryList.subList(pageSize * (currentPage - 1), endIndex);
        dataGrid.setResults(resultList);
        dataGrid.setTotal(queryList.size());

        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            String manageNumSql = "SELECT COUNT(DISTINCT risk_id, my_user_id) manageNum, risk_id riskId FROM t_b_risk_manage WHERE manage_type != 'post' and (my_user_id !=NULL or my_user_id!='') GROUP BY risk_id";
            List<Map<String, Object>> manageNumList = systemService.findForJdbc(manageNumSql);
            Map<String, String> manageNumMap = new HashMap<>();
            if (manageNumList !=null && manageNumList.size()>0) {
                for (Map<String, Object> obj : manageNumList) {
                    String manageNum = String.valueOf(obj.get("manageNum"));
                    String riskId = String.valueOf(obj.get("riskId"));
                    manageNumMap.put(riskId, manageNum);
                }
            }
            String implNumSql = "SELECT COUNT(impl_detail) implNum, risk_id riskId FROM t_b_risk_manage_hazard_factor WHERE manage_type != 'post' GROUP BY risk_id";
            List<Map<String, Object>> implNumList = systemService.findForJdbc(implNumSql);
            Map<String, String> implNumMap = new HashMap<>();
            if (implNumList !=null && implNumList.size()>0) {
                for (Map<String, Object> obj : implNumList) {
                    String implNum = String.valueOf(obj.get("implNum"));
                    String riskId = String.valueOf(obj.get("riskId"));
                    implNumMap.put(riskId, implNum);
                }
            }
            String hdNumSql = "SELECT COUNT(rm.risk_id) hdNum,rm.risk_id riskId FROM t_b_risk_manage_rel_hd rmhd LEFT JOIN t_b_risk_manage_hazard_factor rm on  rmhd.risk_manage_hazard_factor_id = rm.id WHERE rm.manage_type != 'post' GROUP BY rm.risk_id";
            List<Map<String, Object>> hdNumList = systemService.findForJdbc(hdNumSql);
            Map<String, String> hdNumMap = new HashMap<>();
            if (hdNumList !=null && hdNumList.size()>0) {
                for (Map<String, Object> obj : hdNumList) {
                    String hdNum = String.valueOf(obj.get("hdNum"));
                    String riskId = String.valueOf(obj.get("riskId"));
                    hdNumMap.put(riskId, hdNum);
                }
            }
            List<Map<String,Object>>  list = dataGrid.getResults();
            for ( Map<String, Object>  bean : list) {
                String riskId = String.valueOf(bean.get("id"));
                if(StringUtil.isNotEmpty(riskId)){
                    RiskIdentificationEntity riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class,riskId);
                    if(riskIdentificationEntity!=null){
                        List<RiskFactortsRel>  relList = riskIdentificationEntity.getRelList();
                        if (relList == null) {
                            bean.put("hazardFactortsNum","0");
                        }
                        bean.put("hazardFactortsNum",relList.size() + "");
                    }else{
                        bean.put("hazardFactortsNum","0");
                    }
                }
                String manageNum = manageNumMap.get(bean.get("id"));
                if(StringUtil.isNotEmpty(manageNum)){
                    bean.put("manageNum",manageNum);
                }else{
                    bean.put("manageNum","0");
                }
                String implNum = implNumMap.get(bean.get("id"));
                if(StringUtil.isNotEmpty(implNum)){
                    bean.put("implNum",implNum);
                }else{
                    bean.put("implNum","0");
                }
                String hdNum = hdNumMap.get(bean.get("id"));
                if(StringUtil.isNotEmpty(hdNum)){
                    bean.put("hdNum",hdNum);
                }else{
                    bean.put("hdNum","0");
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "manageNumList")
    public ModelAndView manageNumList(HttpServletRequest request) {
        String riskId = request.getParameter("id");
        String load = request.getParameter("load");
        request.setAttribute("load",load);
        request.setAttribute("riskId",riskId);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/manageNumList");
    }

    @RequestMapping(params = "manageNumDatagrid")
    public void manageNumDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(RiskManageEntity.class, dataGrid);
        String riskId = request.getParameter("riskId");
        try {
            cq.createAlias("risk","risk");
            cq.eq("risk.id",riskId);
            cq.or(Restrictions.isNotNull("myUserId"), Restrictions.ne("myUserId", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "implNumList")
    public ModelAndView implNumList(HttpServletRequest request) {
        String riskId = request.getParameter("id");
        String load = request.getParameter("load");
        request.setAttribute("load",load);
        request.setAttribute("riskId",riskId);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/implNumList");
    }

    @RequestMapping(params = "implNumDatagrid")
    public void implNumDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(RiskManageHazardFactorEntity.class, dataGrid);
        String riskId = request.getParameter("riskId");
        try {
            cq.createAlias("risk","risk");
            cq.eq("risk.id",riskId);
            cq.or(Restrictions.isNotNull("implDetail"), Restrictions.ne("implDetail", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "hdNumList")
    public ModelAndView hdNumList(HttpServletRequest request) {
        String riskId = request.getParameter("id");
        String load = request.getParameter("load");
        request.setAttribute("load",load);
        request.setAttribute("riskId",riskId);
        String addressId = request.getParameter("addressId");
        request.setAttribute("addressId",addressId);
        String riskType = request.getParameter("riskType");
        request.setAttribute("riskType",riskType);
        String departId = request.getParameter("departId");
        request.setAttribute("departId",departId);
        String month = request.getParameter("month");
        request.setAttribute("month",month);
        String year = request.getParameter("year");
        request.setAttribute("year",year);
        String type = request.getParameter("type");
        request.setAttribute("type",type);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hdNumList");
    }

    @RequestMapping(params = "hdNumListDatagrid")
    public void hdNumListDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerExamEntity.class, dataGrid);
        String riskId = request.getParameter("riskId");
        String addressId = request.getParameter("addressId");
        String riskType = request.getParameter("riskType");
        String departId = request.getParameter("departId");
        String year = request.getParameter("year");
        String month = request.getParameter("month");
        String type = request.getParameter("type");
        try {
            if(StringUtil.isNotEmpty(riskId)){
                cq.add(Restrictions.sqlRestriction("this_.id in (SELECT hd_id FROM t_b_risk_manage_rel_hd rmhd JOIN t_b_risk_manage_hazard_factor rm on  rmhd.risk_manage_hazard_factor_id = rm.id WHERE rm.manage_type != 'post' AND risk_id = '"+riskId+"')"));
            }
            if(StringUtil.isNotEmpty(addressId)){
                cq.eq("address",addressId);
            }
            if(StringUtil.isNotEmpty(riskType)){
                cq.eq("riskType",riskType);
            }
            if(StringUtil.isNotEmpty(riskType)){
                cq.eq("riskType",riskType);
            }if(StringUtils.isNotBlank(departId)){
                cq.eq("dutyUnit.id",departId);
            }
            if(StringUtil.isNotEmpty(year)&&StringUtil.isNotEmpty(month)&&StringUtil.isNotEmpty(type)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String startDate = year+"-"+month+"-01";
                String endDate = year+"-"+month+"-31";
                if(type.equals("1")){
                    cq.ge("examDate",sdf.parse(year+"-01-01"));
                    cq.le("examDate",sdf.parse(endDate));
                }else if(type.equals("2")){
                    cq.ge("examDate",sdf.parse(startDate));
                    cq.le("examDate",sdf.parse(endDate));
                }else if(type.equals("3")){
                    cq.ge("examDate",sdf.parse(startDate));
                    cq.le("examDate",sdf.parse(endDate));
                    cq.createAlias("handleEntity", "handleEntity");
                    cq.eq("handleEntity.handlelStatus", "5");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null) {
            if (dataGrid.getResults().size() > 0) {
                List<TBHiddenDangerExamEntity> list = dataGrid.getResults();
                for (TBHiddenDangerExamEntity t : list) {
                    String names = "";
                    String querySql = "select fill_card_manids man from t_b_hidden_danger_exam where id = '" + String.valueOf(t.getId()) + "'";
                    List<Map<String, Object>> maplist = systemService.findForJdbc(querySql, null);
                    for (Map map : maplist) {
                        String mans = String.valueOf(map.get("man"));
                        if (StringUtils.isNotBlank(mans)) {
                            String[] userIdArray = mans.split(",");

                            for (String userid : userIdArray) {
                                TSUser user = systemService.getEntity(TSUser.class, userid);
                                if (user != null) {
                                    if (names == "") {
                                        names = names + user.getRealName() + "-" + user.getUserName();
                                    } else {
                                        names = names + "," + user.getRealName() + "-" + user.getUserName();
                                    }
                                } else if (StringUtil.isNotEmpty(userid)) {
                                    if (names == "") {
                                        names = names + userid;
                                    } else {
                                        names = names + "," + userid;
                                    }
                                }
                            }
                        }
                    }
                    t.setFillCardManNames(names);
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 管控 页面跳转
     * @return
     */
    @RequestMapping(params = "riskManageStatisticsSheet")
    public ModelAndView riskManageStatisticsSheet(HttpServletRequest request){
        String yearMonth = request.getParameter("yearMonth");
        if(StringUtil.isEmpty(yearMonth)){
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            yearMonth = sdf.format(date);
        }
        request.setAttribute("yearMonth", yearMonth);
        request.setAttribute("startDate", yearMonth+"-01");
        request.setAttribute("endDate", yearMonth+"-31");
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/riskManageStatisticsSheet");
    }

    /**
     * 风险管控人数 页面跳转
     * @return
     */
    @RequestMapping(params = "goManageNumStatistics")
    public ModelAndView goManageNumStatistics(HttpServletRequest request){
        Map<String,String> params = new HashMap<String,String>();
        params.put("startDate", request.getParameter("startDate"));
        params.put("endDate", request.getParameter("endDate"));
        params.put("flag", request.getParameter("flag"));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/manageNumStatistics",params);
    }

    /**
     * 风险落实次数 页面跳转
     * @return
     */
    @RequestMapping(params = "goImplNumStatistics")
    public ModelAndView goImplNumStatistics(HttpServletRequest request){
        Map<String,String> params = new HashMap<String,String>();
        params.put("startDate", request.getParameter("startDate"));
        params.put("endDate", request.getParameter("endDate"));
        params.put("flag", request.getParameter("flag"));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/implNumStatistics",params);
    }

    /**
     * 风险隐患数量 页面跳转
     * @return
     */
    @RequestMapping(params = "goHdNumStatistics")
    public ModelAndView goHdNumStatistics(HttpServletRequest request){
        Map<String,String> params = new HashMap<String,String>();
        params.put("startDate", request.getParameter("startDate"));
        params.put("endDate", request.getParameter("endDate"));
        params.put("flag", request.getParameter("flag"));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hdNumStatistics",params);
    }


    /**
     * 风险危害因素 页面跳转
     * @return
     */
    @RequestMapping(params = "goHazardFactortsNumStatistics")
    public ModelAndView goHazardFactortsNumStatistics(HttpServletRequest request){
        Map<String,String> params = new HashMap<String,String>();
        params.put("startDate", request.getParameter("startDate"));
        params.put("endDate", request.getParameter("endDate"));
        params.put("flag", request.getParameter("flag"));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hazardFactortsNumStatistics",params);
    }


    /**
     * 风险管控人数 数据组装
     * @param request
     * @return
     */
    @RequestMapping(params = "manageNumStatistics")
    @ResponseBody
    public JSONObject manageNumStatistics(HttpServletRequest request){
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String flag = request.getParameter("flag");

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(DISTINCT risk_id, my_user_id) count, risk_id riskId, ri.risk_desc riskDesc FROM t_b_risk_manage rm JOIN t_b_risk_identification ri on  rm.risk_id =ri.id WHERE rm.manage_type != 'post' and (rm.my_user_id !=NULL or rm.my_user_id!='')");
        if(StringUtils.isNotBlank(startDate)){
            sql.append(" AND rm.create_date >= '").append(startDate).append("-01'");
        }
        if(StringUtils.isNotBlank(endDate)){
            sql.append(" AND rm.create_date <= '").append(endDate).append("-31'");
        }
        sql.append(" GROUP BY rm.risk_id ORDER BY count desc  LIMIT 5");
        if(!"1".equals(flag)){
            sql.append("");
        }
        List<Map<String,Object>> mapList = systemService.findForJdbc(sql.toString());
        StringBuffer riskDescStr = new StringBuffer().append("[");
        StringBuffer countStr = new StringBuffer().append("[");
        StringBuffer riskIdStr = new StringBuffer().append("[");
        if(!mapList.isEmpty() && mapList.size()>0){
            for(Map<String,Object> map : mapList){
                String riskDesc = (String)map.get("riskDesc");
                String count = String.valueOf(map.get("count"));
                String riskId = String.valueOf(map.get("riskId"));
                if(riskDescStr.length() > 1){
                    riskDescStr.append(",");
                }
                if(countStr.length() > 1){
                    countStr.append(",");
                }
                if(riskIdStr.length() > 1){
                    riskIdStr.append(",");
                }

                riskDescStr.append("'").append(riskDesc.replaceAll("\n","").replace("\r","")).append("-").append(riskId).append("'");
                countStr.append(count);
                riskIdStr.append(riskId);
            }
        }
        riskDescStr.append("]");
        countStr.append("]");
        riskIdStr.append("]");
        JSONObject jo = new JSONObject();
        jo.put("riskDescStr",riskDescStr.toString());
        jo.put("countStr",countStr.toString());
        jo.put("riskIdStr",riskIdStr.toString());
        return jo;
    }

    /**
     * 风险落实次数 数据组装
     * @param request
     * @return
     */
    @RequestMapping(params = "implNumStatistics")
    @ResponseBody
    public JSONObject implNumStatistics(HttpServletRequest request){
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String flag = request.getParameter("flag");

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(impl_detail) count, risk_id riskId, ri.risk_desc riskDesc FROM t_b_risk_manage_hazard_factor rmhf LEFT JOIN t_b_risk_identification ri ON rmhf.risk_id = ri.id WHERE rmhf.manage_type != 'post'  and rmhf.impl_detail is not NULL and rmhf.impl_detail <> ''  ");
        if(StringUtils.isNotBlank(startDate)){
            sql.append(" AND rmhf.create_date >= '").append(startDate).append("-01'");
        }
        if(StringUtils.isNotBlank(endDate)){
            sql.append(" AND rmhf.create_date <= '").append(endDate).append("-31'");
        }
        sql.append(" GROUP BY rmhf.risk_id ORDER BY count desc  LIMIT 5");
        if(!"1".equals(flag)){
            sql.append("");
        }
        List<Map<String,Object>> mapList = systemService.findForJdbc(sql.toString());
        StringBuffer riskDescStr = new StringBuffer().append("[");
        StringBuffer countStr = new StringBuffer().append("[");
        StringBuffer riskIdStr = new StringBuffer().append("[");
        if(!mapList.isEmpty() && mapList.size()>0){
            for(Map<String,Object> map : mapList){
                String riskDesc = (String)map.get("riskDesc");
                String count = String.valueOf(map.get("count"));
                String riskId = String.valueOf(map.get("riskId"));
                if(riskDescStr.length() > 1){
                    riskDescStr.append(",");
                }
                if(countStr.length() > 1){
                    countStr.append(",");
                }
                if(riskIdStr.length() > 1){
                    riskIdStr.append(",");
                }

                riskDescStr.append("'").append(riskDesc.replaceAll("\n","").replace("\r","")).append("-").append(riskId).append("'");
                countStr.append(count);
                riskIdStr.append(riskId);
            }
        }
        riskDescStr.append("]");
        countStr.append("]");
        riskIdStr.append("]");
        JSONObject jo = new JSONObject();
        jo.put("riskDescStr",riskDescStr.toString());
        jo.put("countStr",countStr.toString());
        jo.put("riskIdStr",riskIdStr.toString());
        return jo;
    }

    /**
     * 风险隐患数量 数据组装
     * @param request
     * @return
     */
    @RequestMapping(params = "hdNumStatistics")
    @ResponseBody
    public JSONObject hdNumStatistics(HttpServletRequest request){
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String flag = request.getParameter("flag");

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(rm.risk_id) count, rm.risk_id riskId, ri.risk_desc riskDesc FROM t_b_risk_manage_rel_hd rmhd LEFT JOIN t_b_risk_manage_hazard_factor rm ON rmhd.risk_manage_hazard_factor_id = rm.id LEFT JOIN t_b_risk_identification ri ON ri.id = rm.risk_id WHERE rm.manage_type != 'post'  ");
        if(StringUtils.isNotBlank(startDate)){
            sql.append(" AND rmhd.create_date >= '").append(startDate).append("-01'");
        }
        if(StringUtils.isNotBlank(endDate)){
            sql.append(" AND rmhd.create_date <= '").append(endDate).append("-31'");
        }
        sql.append(" GROUP BY rm.risk_id ORDER BY count desc  LIMIT 5");
        if(!"1".equals(flag)){
            sql.append("");
        }
        List<Map<String,Object>> mapList = systemService.findForJdbc(sql.toString());
        StringBuffer riskDescStr = new StringBuffer().append("[");
        StringBuffer countStr = new StringBuffer().append("[");
        StringBuffer riskIdStr = new StringBuffer().append("[");
        if(!mapList.isEmpty() && mapList.size()>0){
            for(Map<String,Object> map : mapList){
                String riskDesc = (String)map.get("riskDesc");
                String count = String.valueOf(map.get("count"));
                String riskId = String.valueOf(map.get("riskId"));
                if(riskDescStr.length() > 1){
                    riskDescStr.append(",");
                }
                if(countStr.length() > 1){
                    countStr.append(",");
                }
                if(riskIdStr.length() > 1){
                    riskIdStr.append(",");
                }

                riskDescStr.append("'").append(riskDesc.replaceAll("\n","").replace("\r","")).append("-").append(riskId).append("'");
                countStr.append(count);
                riskIdStr.append(riskId);
            }
        }
        riskDescStr.append("]");
        countStr.append("]");
        riskIdStr.append("]");
        JSONObject jo = new JSONObject();
        jo.put("riskDescStr",riskDescStr.toString());
        jo.put("countStr",countStr.toString());
        jo.put("riskIdStr",riskIdStr.toString());
        return jo;
    }

    /**
     * 风险危害因素 数据组装
     * @param request
     * @return
     */
    @RequestMapping(params = "hazardFactortsNumStatistics")
    @ResponseBody
    public JSONObject hazardFactortsNumStatistics(HttpServletRequest request){
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String flag = request.getParameter("flag");

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT COUNT(rfr.id) count,ri.id riskId, ri.risk_desc riskDesc FROM t_b_risk_factors_rel rfr LEFT JOIN t_b_risk_identification ri ON ri.id = rfr.risk_identification_id WHERE  ri.status = '3' and ri.is_del = '0' and ri.identification_type !='4' ");
        if(StringUtils.isNotBlank(startDate)){
            sql.append(" AND ri.identifi_date <= '").append(startDate).append("-31'");
        }
        if(StringUtils.isNotBlank(endDate)){
            sql.append(" AND ri.exp_date >= '").append(endDate).append("-31'");
        }
        sql.append(" GROUP BY ri.id ORDER BY count desc  LIMIT 5");
        if(!"1".equals(flag)){
            sql.append("");
        }
        List<Map<String,Object>> mapList = systemService.findForJdbc(sql.toString());
        StringBuffer riskDescStr = new StringBuffer().append("[");
        StringBuffer countStr = new StringBuffer().append("[");
        StringBuffer riskIdStr = new StringBuffer().append("[");
        if(!mapList.isEmpty() && mapList.size()>0){
            for(Map<String,Object> map : mapList){
                String riskDesc = (String)map.get("riskDesc");
                String count = String.valueOf(map.get("count"));
                String riskId = String.valueOf(map.get("riskId"));
                if(riskDescStr.length() > 1){
                    riskDescStr.append(",");
                }
                if(countStr.length() > 1){
                    countStr.append(",");
                }
                if(riskIdStr.length() > 1){
                    riskIdStr.append(",");
                }

                riskDescStr.append("'").append(riskDesc.replaceAll("\n","").replace("\r","")).append("-").append(riskId).append("'");
                countStr.append(count);
                riskIdStr.append(riskId);
            }
        }
        riskDescStr.append("]");
        countStr.append("]");
        riskIdStr.append("]");
        JSONObject jo = new JSONObject();
        jo.put("riskDescStr",riskDescStr.toString());
        jo.put("countStr",countStr.toString());
        jo.put("riskIdStr",riskIdStr.toString());
        return jo;
    }

    @RequestMapping(params = "riskManageStatisticsMenu")
    public ModelAndView riskManageStatisticsMenu( HttpServletRequest request) {
        String riskId = request.getParameter("riskId");
        request.setAttribute("riskId",riskId);
        String type = request.getParameter("type");
        request.setAttribute("type",type);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/riskManageStatisticsMenu");
    }

    @RequestMapping(params = "riskManageStatisticsDatagridMenu")
    public void riskManageStatisticsDatagridMenu( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        List<Map<String,Object>> queryList = new ArrayList<>();
        String id = request.getParameter("riskId");
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT ri.id, ri.address_id address, ri.risk_type riskType, ri.risk_desc riskDesc, ri.risk_level riskLevel  ");
        sql.append(" FROM t_b_risk_identification ri    WHERE  ri.id = '"+id+"'");
        queryList = this.systemService.findForJdbc(sql.toString());
        int currentPage = dataGrid.getPage();
        int pageSize = dataGrid.getRows();
        int endIndex = pageSize * currentPage;

        if(endIndex > queryList.size()){
            endIndex = queryList.size();
        }
        List<Map<String,Object>> resultList = queryList.subList(pageSize * (currentPage - 1), endIndex);
        dataGrid.setResults(resultList);
        dataGrid.setTotal(queryList.size());

        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            String manageNumSql = "SELECT COUNT(DISTINCT risk_id, my_user_id) manageNum, risk_id riskId FROM t_b_risk_manage WHERE manage_type != 'post' and (my_user_id !=NULL or my_user_id!='') GROUP BY risk_id";
            List<Map<String, Object>> manageNumList = systemService.findForJdbc(manageNumSql);
            Map<String, String> manageNumMap = new HashMap<>();
            if (manageNumList !=null && manageNumList.size()>0) {
                for (Map<String, Object> obj : manageNumList) {
                    String manageNum = String.valueOf(obj.get("manageNum"));
                    String riskId = String.valueOf(obj.get("riskId"));
                    manageNumMap.put(riskId, manageNum);
                }
            }
            String implNumSql = "SELECT COUNT(impl_detail) implNum, risk_id riskId FROM t_b_risk_manage_hazard_factor WHERE manage_type != 'post' GROUP BY risk_id";
            List<Map<String, Object>> implNumList = systemService.findForJdbc(implNumSql);
            Map<String, String> implNumMap = new HashMap<>();
            if (implNumList !=null && implNumList.size()>0) {
                for (Map<String, Object> obj : implNumList) {
                    String implNum = String.valueOf(obj.get("implNum"));
                    String riskId = String.valueOf(obj.get("riskId"));
                    implNumMap.put(riskId, implNum);
                }
            }
            String hdNumSql = "SELECT COUNT(rm.risk_id) hdNum,rm.risk_id riskId FROM t_b_risk_manage_rel_hd rmhd LEFT JOIN t_b_risk_manage_hazard_factor rm on  rmhd.risk_manage_hazard_factor_id = rm.id WHERE rm.manage_type != 'post' GROUP BY rm.risk_id";
            List<Map<String, Object>> hdNumList = systemService.findForJdbc(hdNumSql);
            Map<String, String> hdNumMap = new HashMap<>();
            if (hdNumList !=null && hdNumList.size()>0) {
                for (Map<String, Object> obj : hdNumList) {
                    String hdNum = String.valueOf(obj.get("hdNum"));
                    String riskId = String.valueOf(obj.get("riskId"));
                    hdNumMap.put(riskId, hdNum);
                }
            }
            List<Map<String,Object>>  list = dataGrid.getResults();
            for ( Map<String, Object>  bean : list) {
                String riskId = String.valueOf(bean.get("id"));
                if(StringUtil.isNotEmpty(riskId)){
                    RiskIdentificationEntity riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class,riskId);
                    if(riskIdentificationEntity!=null){
                        List<RiskFactortsRel>  relList = riskIdentificationEntity.getRelList();
                        if (relList == null) {
                            bean.put("hazardFactortsNum","0");
                        }
                        bean.put("hazardFactortsNum",relList.size() + "");
                    }else{
                        bean.put("hazardFactortsNum","0");
                    }
                }
                String manageNum = manageNumMap.get(bean.get("id"));
                if(StringUtil.isNotEmpty(manageNum)){
                    bean.put("manageNum",manageNum);
                }else{
                    bean.put("manageNum","0");
                }
                String implNum = implNumMap.get(bean.get("id"));
                if(StringUtil.isNotEmpty(implNum)){
                    bean.put("implNum",implNum);
                }else{
                    bean.put("implNum","0");
                }
                String hdNum = hdNumMap.get(bean.get("id"));
                if(StringUtil.isNotEmpty(hdNum)){
                    bean.put("hdNum",hdNum);
                }else{
                    bean.put("hdNum","0");
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 管理人员管控情况分析
     * */

    @RequestMapping(params = "loginHiddenStatistics")
    public ModelAndView loginHiddenStatistics( HttpServletRequest request) {
        String yearMonth = request.getParameter("yearMonth");
        if(StringUtils.isBlank(yearMonth)){
            SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
            yearMonth = date_sdf.format(new Date());
        }
        request.setAttribute("yearMonth",yearMonth);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/loginHiddenStatistics");
    }



    @RequestMapping(params = "loginHiddenStatisticsDatagrid")
    public void loginHiddenStatisticsDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        List<Map<String,Object>> queryList = new ArrayList<>();
        String createDate = request.getParameter("createDate");
        String sort = dataGrid.getSort();
        String sortOrder = dataGrid.getOrder();
        StringBuffer sql = new StringBuffer();
        if(StringUtil.isNotEmpty(sort)){
            if(sort.equals("loginNum")){
                sql.append("SELECT count(base.id) loginNum, u.id, u.realname, u.userName FROM t_s_base_user u LEFT JOIN t_s_log base ON u.id = base.userid  and base.loglevel = '1' and base.operatetype = '1'  WHERE u.delete_flag = '0'");
                if(StringUtil.isNotEmpty(createDate)){
                    sql.append(" and base.operatetime >= '"+createDate+"-01' and base.operatetime <= '"+createDate+"-31' ");
                }
            }
            if(sort.equals("hiddenNum")){
                sql.append("SELECT count(base.id) hiddenNum, u.id, u.realname, u.userName FROM t_s_base_user u LEFT JOIN t_b_hidden_danger_exam base ON u.username = base.create_by WHERE u.delete_flag = '0'   ");
                if(StringUtil.isNotEmpty(createDate)){
                    sql.append(" and base.create_date >= '"+createDate+"-01' and base.create_date <= '"+createDate+"-31' ");
                }
            }
            if(sort.equals("hazardNum")){
                sql.append("SELECT count(base.id) hazardNum, u.id, u.realname, u.userName FROM t_s_base_user u LEFT JOIN t_b_risk_manage_hazard_factor base ON u.username = base.update_by  and (base.impl_detail is not null or base.impl_detail = '')  WHERE u.delete_flag = '0'");
                if(StringUtil.isNotEmpty(createDate)){
                    sql.append(" and base.update_date >= '"+createDate+"-01' and base.update_date <= '"+createDate+"-31' ");
                }
            }
        }else{
            sql.append("SELECT count(base.id) hiddenNum, u.id, u.realname, u.userName FROM t_s_base_user u LEFT JOIN t_b_hidden_danger_exam base ON u.username = base.create_by WHERE u.delete_flag = '0' ");
            if(StringUtil.isNotEmpty(createDate)){
                sql.append(" and base.create_date >= '"+createDate+"-01' and base.create_date <= '"+createDate+"-31' ");
            }

        }
        sql.append(" GROUP BY u.id ");
        if(StringUtil.isNotEmpty(sort)){
            if(sort.equals("loginNum")){
                sql.append(" ORDER BY loginNum "+sortOrder+" ");
            }
            if(sort.equals("hiddenNum")){
                sql.append(" ORDER BY hiddenNum "+sortOrder+" ");
            }
            if(sort.equals("hazardNum")){
                sql.append(" ORDER BY hazardNum "+sortOrder+" ");
            }
        }else{
            sql.append(" ORDER BY hiddenNum DESC");
        }
        queryList = this.systemService.findForJdbc(sql.toString());
        int currentPage = dataGrid.getPage();
        int pageSize = dataGrid.getRows();
        int endIndex = pageSize * currentPage;

        if(endIndex > queryList.size()){
            endIndex = queryList.size();
        }
        List<Map<String,Object>> resultList = queryList.subList(pageSize * (currentPage - 1), endIndex);
        dataGrid.setResults(resultList);
        dataGrid.setTotal(queryList.size());

        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            StringBuffer hiddenNumSql = new StringBuffer();
            hiddenNumSql.append("SELECT COUNT(id) hiddenNum , create_by userName from t_b_hidden_danger_exam ");
            if(StringUtil.isNotEmpty(createDate)){
                hiddenNumSql.append(" where create_date >= '"+createDate+"-01' and create_date <= '"+createDate+"-31' ");
            }
            hiddenNumSql.append(" GROUP BY create_by");
            List<Map<String, Object>> hiddenNumList = systemService.findForJdbc(hiddenNumSql.toString());
            Map<String, String> hiddenNumMap = new HashMap<>();
            if (hiddenNumList !=null && hiddenNumList.size()>0) {
                for (Map<String, Object> obj : hiddenNumList) {
                    String hiddenNum = String.valueOf(obj.get("hiddenNum"));
                    String userName = String.valueOf(obj.get("userName"));
                    hiddenNumMap.put(userName, hiddenNum);
                }
            }

            StringBuffer loginNumSql = new StringBuffer();
            loginNumSql.append("SELECT count(id) loginNum, userid from t_s_log WHERE operatetype = '1' and loglevel = '1'   ");
            if(StringUtil.isNotEmpty(createDate)){
                loginNumSql.append(" and  operatetime >= '"+createDate+"-01' and operatetime <= '"+createDate+"-31' ");
            }
            loginNumSql.append(" GROUP BY userid");
            List<Map<String, Object>> loginNumList = systemService.findForJdbc(loginNumSql.toString());
            Map<String, String> loginNumMap = new HashMap<>();
            if (loginNumList !=null && loginNumList.size()>0) {
                for (Map<String, Object> obj : loginNumList) {
                    String loginNum = String.valueOf(obj.get("loginNum"));
                    String userid = String.valueOf(obj.get("userid"));
                    loginNumMap.put(userid, loginNum);
                }
            }

            StringBuffer hazardNumSql = new StringBuffer();
            hazardNumSql.append("SELECT COUNT(id) hazardNum, update_by userName from t_b_risk_manage_hazard_factor WHERE (impl_detail is not null or impl_detail !='')    ");
            if(StringUtil.isNotEmpty(createDate)){
                hazardNumSql.append(" and  update_date >= '"+createDate+"-01' and update_date <= '"+createDate+"-31' ");
            }
            hazardNumSql.append(" GROUP BY update_by");
            List<Map<String, Object>> hazardNumList = systemService.findForJdbc(hazardNumSql.toString());
            Map<String, String> hazardNumMap = new HashMap<>();
            if (hazardNumList !=null && hazardNumList.size()>0) {
                for (Map<String, Object> obj : hazardNumList) {
                    String hazardNum = String.valueOf(obj.get("hazardNum"));
                    String userName = String.valueOf(obj.get("userName"));
                    hazardNumMap.put(userName, hazardNum);
                }
            }


            List<Map<String,Object>>  list = dataGrid.getResults();
            for ( Map<String, Object>  bean : list) {
                String hiddenNum = hiddenNumMap.get(bean.get("userName"));
                if(StringUtil.isNotEmpty(hiddenNum)){
                    bean.put("hiddenNum",hiddenNum);
                }else{
                    bean.put("hiddenNum","0");
                }
                String loginNum = loginNumMap.get(bean.get("id"));
                if(StringUtil.isNotEmpty(loginNum)){
                    bean.put("loginNum",loginNum);
                }else{
                    bean.put("loginNum","0");
                }

                String hazardNum = hazardNumMap.get(bean.get("userName"));
                if(StringUtil.isNotEmpty(hazardNum)){
                    bean.put("hazardNum",hazardNum);
                }else{
                    bean.put("hazardNum","0");
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "loginHiddenStatisticsNew")
    public ModelAndView loginHiddenStatisticsNew(HttpServletRequest request) {
        String yearMonth = request.getParameter("yearMonth");
        if(StringUtils.isBlank(yearMonth)){
            SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
            yearMonth = date_sdf.format(new Date());
        }
        request.setAttribute("yearMonth",yearMonth);
        String loginHiddenStatisticsType = request.getParameter("loginHiddenStatisticsType");
        if(StringUtil.isEmpty(loginHiddenStatisticsType)){
            loginHiddenStatisticsType="1";
        }

        String unitId = request.getParameter("unitId");
        request.setAttribute("unitId",unitId);
        String userId = request.getParameter("userId");
        request.setAttribute("userId",userId);
        request.setAttribute("loginHiddenStatisticsType",loginHiddenStatisticsType);
        request.setAttribute("loginHiddenStatisticsTypeName",DicUtil.getTypeNameByCode("loginHiddenStatisticsType",loginHiddenStatisticsType));
        if(loginHiddenStatisticsType.equals("4")){
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            if(StringUtil.isEmpty(startDate)){
                startDate = DateUtils.getDate("yyyy-MM") + "-01";
            }
            if(StringUtil.isEmpty(endDate)){
                endDate = DateUtils.getDate("yyyy-MM-dd");
            }
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/loginHiddenStatisticsNewHZ");
        }else if(loginHiddenStatisticsType.equals("5")){
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            if(StringUtil.isEmpty(startDate)){
                startDate = DateUtils.getDate("yyyy-MM") + "-01";
            }
            if(StringUtil.isEmpty(endDate)){
                endDate = DateUtils.getDate("yyyy-MM-dd");
            }
            String addressId = request.getParameter("addressId");
            request.setAttribute("addressId", addressId);
            request.setAttribute("startDate", startDate);
            request.setAttribute("endDate", endDate);
            return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/addressHiddenStatistics");
        }else{
            return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/loginHiddenStatisticsNew");
        }
    }


    @RequestMapping(params = "loginHiddenStatisticsNewDatagrid")
    public void loginHiddenStatisticsNewDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String yearMonth = request.getParameter("yearMonth");
        String loginHiddenStatisticsType = request.getParameter("loginHiddenStatisticsType");
        StringBuffer sql = new StringBuffer();
        String unitId = request.getParameter("unitId");
        String userId = request.getParameter("userId");
        if(StringUtil.isNotEmpty(loginHiddenStatisticsType)){
            if(loginHiddenStatisticsType.equals("1")){

                sql.append("SELECT count(base.id) countNum, u.id, u.realname name, u.userName FROM t_s_base_user u LEFT JOIN t_s_user_org o ON o.user_id = u.id LEFT JOIN t_b_hidden_danger_exam base ON u.username = base.create_by WHERE u.delete_flag = '0'   ");
                if(StringUtil.isNotEmpty(yearMonth)){
                    sql.append(" and base.create_date >= '"+yearMonth+"-01' and base.create_date <= '"+yearMonth+"-31' ");
                }
                if(StringUtil.isNotEmpty(unitId)){
                    sql.append(" and o.org_id='"+unitId+"' ");
                }
                if(StringUtil.isNotEmpty(userId)){
                    sql.append(" and u.id = '"+userId+"' ");
                }
                sql.append(" GROUP BY u.id  ORDER BY countNum DESC  ");
            }else if(loginHiddenStatisticsType.equals("2")){
                sql.append("SELECT count(base.id) countNum, u.id, u.realname name, u.userName FROM t_s_base_user u LEFT JOIN t_s_user_org o ON o.user_id = u.id LEFT JOIN t_s_log base ON u.id = base.userid  WHERE u.delete_flag = '0' and base.loglevel = '1' and base.operatetype = '1' ");
                if(StringUtil.isNotEmpty(yearMonth)){
                    sql.append(" and base.operatetime >= '"+yearMonth+"-01' and base.operatetime <= '"+yearMonth+"-31' ");
                }
                if(StringUtil.isNotEmpty(unitId)){
                    sql.append(" and o.org_id='"+unitId+"' ");
                }
                if(StringUtil.isNotEmpty(userId)){
                    sql.append(" and u.id = '"+userId+"' ");
                }
                sql.append(" GROUP BY u.id  ORDER BY countNum DESC   ");
            }else{
                sql.append("SELECT count(base.id) countNum, u.id, u.realname name, u.userName FROM t_s_base_user u LEFT JOIN t_s_user_org o ON o.user_id = u.id LEFT JOIN t_b_risk_manage_hazard_factor base ON u.username = base.update_by WHERE u.delete_flag = '0' and (base.impl_detail is not null or base.impl_detail = '') ");
                if(StringUtil.isNotEmpty(yearMonth)){
                    sql.append(" and base.update_date >= '"+yearMonth+"-01' and base.update_date <= '"+yearMonth+"-31' ");
                }
                if(StringUtil.isNotEmpty(unitId)){
                    sql.append(" and o.org_id='"+unitId+"' ");
                }
                if(StringUtil.isNotEmpty(userId)){
                    sql.append(" and u.id = '"+userId+"' ");
                }
                sql.append(" GROUP BY u.id  ORDER BY countNum DESC   ");
            }
        }

        List<Map<String, Object>> quertList = systemService.findForJdbc(sql.toString());
        dataGrid.setTotal(quertList.size());
        dataGrid.setResults(quertList);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "riskHiddenStatistics")
    public ModelAndView riskHiddenStatistics(HttpServletRequest request) {
        String year = request.getParameter("year");
        if(StringUtils.isBlank(year)){
            Calendar ca = Calendar.getInstance();
            year =  ca.get(Calendar.YEAR)+"";
        }
        request.setAttribute("year",year);
        String addressId = request.getParameter("addressId");
        request.setAttribute("addressId",addressId);
        String riskType = request.getParameter("riskType");
        request.setAttribute("riskType",riskType);
        generateDate(request,year);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/riskHiddenStatistics");
    }


    @RequestMapping(params = "hiddenStatistics")
    public ModelAndView hiddenStatistics(HttpServletRequest request) {
        String year = request.getParameter("year");
        if(org.apache.commons.lang.StringUtils.isNotBlank(year)){
            request.setAttribute("year",year);
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            request.setAttribute("year",sdf.format(new Date()));
        }

        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hiddenStatistics");
    }


    @RequestMapping(params = "hiddenStatisticsDatagrid")
    @ResponseBody
    public JSONObject hiddenStatisticsDatagrid(HttpServletRequest request, HttpServletResponse response) throws Exception {
        JSONObject jo = new JSONObject();

        String queryYear = request.getParameter("queryYear");
        String year = "";
        if(org.apache.commons.lang.StringUtils.isNotBlank(queryYear)){
            year = queryYear;
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            year = sdf.format(new Date());
        }
        String addressId = request.getParameter("addressId");
        String riskType = request.getParameter("riskType");
        String departId = request.getParameter("departId");
        String totalNum = "", newNum = "", closeNum = "";

        for (int i = 1; i < 13; i++) {
            String month = "";
            if (i < 10) {
                month = "0" + i;
            } else {
                month = String.valueOf(i);
            }
            String queryMonth = year + "-" + month;

            StringBuffer sql = new StringBuffer();
            sql.append("select count(id) from t_b_hidden_danger_handle where hidden_danger_id in( " +
                    "select id from t_b_hidden_danger_exam where exam_date >= '"+ year+"-01-01" +"' and exam_date < '"+ queryMonth+"-32" +"'" );
            if(StringUtil.isNotEmpty(addressId)){
                sql.append(" and address = '"+addressId+"' ");
            }
            if(StringUtil.isNotEmpty(riskType)){
                sql.append(" and risk_type = '"+riskType+"' ");
            }
            if(StringUtil.isNotEmpty(departId)){
                sql.append(" and duty_unit = '"+departId+"' ");
            }
            sql.append(" )");

            List<BigInteger> countList = systemService.findListbySql(sql.toString());
            if (!countList.isEmpty() && countList.size() > 0) {
                String count = String.valueOf(countList.get(0));
                totalNum += count + ",";
            }
        }

        StringBuffer newNumSql = new StringBuffer();
        newNumSql.append("select DATE_FORMAT(e.exam_date,'%Y-%m'),count(h.id) from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id where e.exam_date like '"+year+"%' ");
        if(StringUtil.isNotEmpty(addressId)){
            newNumSql.append(" and e.address = '"+addressId+"' ");
        }
        if(StringUtil.isNotEmpty(riskType)){
            newNumSql.append(" and e.risk_type = '"+riskType+"' ");
        }
        if(StringUtil.isNotEmpty(departId)){
            newNumSql.append(" and duty_unit = '"+departId+"' ");
        }
        newNumSql.append(" GROUP BY DATE_FORMAT(e.exam_date,'%Y-%m')");
        List<Object[]> countList = systemService.findListbySql(newNumSql.toString());
        newNum = countListToArray(countList);
        countList.clear();
        StringBuffer closeNumSql = new StringBuffer();
        closeNumSql.append("select DATE_FORMAT(e.exam_date,'%Y-%m'),count(h.id) from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e ON h.hidden_danger_id=e.id where e.exam_date like '"+year+"%' and h.handlel_status='5' ");
        if(StringUtil.isNotEmpty(addressId)){
            closeNumSql.append(" and e.address = '"+addressId+"' ");
        }
        if(StringUtil.isNotEmpty(riskType)){
            closeNumSql.append(" and e.risk_type = '"+riskType+"' ");
        }
        if(StringUtil.isNotEmpty(departId)){
            closeNumSql.append(" and duty_unit = '"+departId+"' ");
        }
        closeNumSql.append(" GROUP BY DATE_FORMAT(e.exam_date,'%Y-%m')");
        countList = systemService.findListbySql(closeNumSql.toString());
        closeNum = countListToArray(countList);
        countList.clear();

        totalNum = totalNum.substring(0, (totalNum.length() - 1));
        newNum = newNum.substring(0, (newNum.length() - 1));
        closeNum = closeNum.substring(0, (closeNum.length() - 1));

        jo.put("totalNum", totalNum);
        jo.put("newNum", newNum);
        jo.put("closeNum", closeNum);

        return jo;
    }


    private String countListToArray(List<Object[]> countList){
        String retArray = "";

        if (countList!=null && countList.size()>0){
            int countListIndex = 0;
            for (int i=1; i<=12; i++){
                String month = "";
                if (i<10){
                    month = "0" + i;
                }else{
                    month = "" + i;
                }

                if (countListIndex<countList.size()){   //不能超过得到的数组的长度
                    if (month.equals(countList.get(countListIndex)[0].toString().substring(5))){
                        retArray += countList.get(countListIndex)[1].toString() + ",";
                        countListIndex ++;
                    }else{
                        retArray += "0,";
                    }
                }else{
                    retArray += "0,";
                }
            }
        }else{
            retArray = "0,0,0,0,0,0,0,0,0,0,0,0,";
        }

        return retArray;
    }


    @RequestMapping(params = "riskStatistics")
    public ModelAndView riskStatistics(HttpServletRequest request) {
        String yearMonth = request.getParameter("yearMonth");
        if(StringUtils.isBlank(yearMonth)){
            SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
            yearMonth = date_sdf.format(new Date());
        }
        request.setAttribute("yearMonth",yearMonth);
        String riskStatisticsType = request.getParameter("riskStatisticsType");
        if(StringUtil.isEmpty(riskStatisticsType)){
            riskStatisticsType="1";
        }
        request.setAttribute("riskStatisticsType",riskStatisticsType);
        request.setAttribute("riskStatisticsTypeName",DicUtil.getTypeNameByCode("riskStatisticsType",riskStatisticsType));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/riskStatistics");
    }


    @RequestMapping(params = "riskStatisticsDatagrid")
    public void riskStatisticsDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String yearMonth = request.getParameter("yearMonth");
        String riskStatisticsType = request.getParameter("riskStatisticsType");
        StringBuffer sql = new StringBuffer();
        if(StringUtil.isNotEmpty(riskStatisticsType)){
            if(riskStatisticsType.equals("1")){
                sql.append("SELECT ri.address_id id ,ai.address name, count(ri.id) riskNum FROM t_b_risk_identification ri LEFT JOIN t_b_address_info ai on ri.address_id = ai.id WHERE ri.is_del = '0' and ri.status = '3' ");
                if(StringUtil.isNotEmpty(yearMonth)){
                    sql.append(" and DATE_FORMAT(ri.create_date,'%Y-%m')= '"+yearMonth+"' ");
                }
                sql.append(" GROUP BY ri.address_id ORDER BY riskNum desc LIMIT 10  ");

            }else {
                sql.append("SELECT ri.risk_type id ,t.typename name, count(ri.id) riskNum FROM t_b_risk_identification ri LEFT JOIN t_s_type t on ri.risk_type = t.typecode LEFT JOIN t_s_typegroup tg on tg.id = t.typegroupid WHERE ri.is_del = '0' and ri.status = '3' and tg.typegroupcode = 'risk_type' ");
                if(StringUtil.isNotEmpty(yearMonth)){
                    sql.append(" and DATE_FORMAT(ri.create_date,'%Y-%m')= '"+yearMonth+"' ");
                }
                sql.append(" GROUP BY ri.risk_type ORDER BY riskNum desc  ");
            }
        }

        List<Map<String, Object>> quertList = systemService.findForJdbc(sql.toString());
        dataGrid.setTotal(quertList.size());
        dataGrid.setResults(quertList);
        TagUtil.datagrid(response, dataGrid);
    }


    @RequestMapping(params = "hazardFactorStatistics")
    public ModelAndView hazardFactorStatistics(HttpServletRequest request) {
        String yearMonth = request.getParameter("yearMonth");
        request.setAttribute("yearMonth",yearMonth);
        String hazardFactorStatisticsType = request.getParameter("hazardFactorStatisticsType");
        if(StringUtil.isEmpty(hazardFactorStatisticsType)){
            hazardFactorStatisticsType="0";
        }
        request.setAttribute("hazardFactorStatisticsType",hazardFactorStatisticsType);
        request.setAttribute("hazardFactorStatisticsTypeName",DicUtil.getTypeNameByCode("hazardFactorStatisticsType",hazardFactorStatisticsType));
        if(!hazardFactorStatisticsType.equals("0")){
            return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hazardFactorStatistics");
        }else{
            String year = request.getParameter("year");
            if(StringUtils.isBlank(year)){
                Calendar ca = Calendar.getInstance();
                year =  ca.get(Calendar.YEAR)+"";
            }
            request.setAttribute("year",year);
            generateDateKJ(request,year);
            return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hazardFactorStatisticsKJ");
        }
    }


    @RequestMapping(params = "hazardFactorStatisticsDatagrid")
    public void hazardFactorStatisticsDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String yearMonth = request.getParameter("yearMonth");
        String hazardFactorStatisticsType = request.getParameter("hazardFactorStatisticsType");
        StringBuffer sql = new StringBuffer();
        if(StringUtil.isNotEmpty(hazardFactorStatisticsType)){
            if(hazardFactorStatisticsType.equals("1")){
                sql.append("SELECT sum(cnt) countNum ,a.name  name,a.id id  FROM (");
                sql.append("SELECT count(0) cnt, ai.address name ,ai.id from t_b_risk_manage_hazard_factor hf LEFT JOIN t_b_risk_identification ri on hf.risk_id = ri.id LEFT JOIN t_b_address_info ai on ai.id = ri.address_id ");
                if(StringUtil.isNotEmpty(yearMonth)){
                    sql.append(" where  hf.create_date >= '"+yearMonth+"-01' and hf.create_date <= '"+yearMonth+"-31' ");
                }
                sql.append(" GROUP BY ri.address_id   ");
                sql.append(" UNION ALL ");
                sql.append(" SELECT COUNT(0) cnt,ai.address name,ai.id  FROM t_b_hidden_danger_exam hd LEFT JOIN t_b_address_info ai ON ai.id = hd.address ");
                if(StringUtil.isNotEmpty(yearMonth)){
                    sql.append(" where  hd.create_date >= '"+yearMonth+"-01' and hd.create_date <= '"+yearMonth+"-31' ");
                }
                sql.append(" GROUP BY hd.address   ");
                sql.append(" ) a GROUP BY a.id ORDER BY countNum DESC LIMIT 10 ");
            }else if(hazardFactorStatisticsType.equals("2")){
                sql.append("SELECT sum(cnt) countNum ,a.name  name,a.id id  FROM (");
                sql.append("SELECT count(0) cnt, t.typename name ,t.typecode id from t_b_risk_manage_hazard_factor hf LEFT JOIN t_b_risk_identification ri on hf.risk_id = ri.id LEFT JOIN t_s_type t on ri.risk_type = t.typecode LEFT JOIN t_s_typegroup tg on tg.id = t.typegroupid WHERE tg.typegroupcode = 'risk_type' ");
                if(StringUtil.isNotEmpty(yearMonth)){
                    sql.append(" and  hf.create_date >= '"+yearMonth+"-01' and hf.create_date <= '"+yearMonth+"-31' ");
                }
                sql.append(" GROUP BY ri.risk_type   ");
                sql.append(" UNION ALL ");
                sql.append(" SELECT COUNT(0) cnt,t.typename name,t.typecode id   FROM t_b_hidden_danger_exam hd LEFT JOIN t_s_type t on hd.risk_type = t.typecode LEFT JOIN t_s_typegroup tg on tg.id = t.typegroupid WHERE tg.typegroupcode = 'risk_type' ");
                if(StringUtil.isNotEmpty(yearMonth)){
                    sql.append(" and  hd.create_date >= '"+yearMonth+"-01' and hd.create_date <= '"+yearMonth+"-31' ");
                }
                sql.append(" GROUP BY hd.risk_type   ");
                sql.append(" ) a GROUP BY a.id ORDER BY countNum DESC LIMIT 10 ");
            }else{
                sql.append("SELECT count(0) countNum, t.typename name ,t.typecode id from t_b_risk_manage_hazard_factor hf LEFT JOIN t_b_hazard_factors f on f.id = hf.hazard_factor_id  LEFT JOIN t_s_type t on f.major = t.typecode LEFT JOIN t_s_typegroup tg on tg.id = t.typegroupid WHERE tg.typegroupcode = 'major' " );
                if(StringUtil.isNotEmpty(yearMonth)){
                    sql.append(" and hf.create_date >= '"+yearMonth+"-01' and hf.create_date <= '"+yearMonth+"-31' ");
                }
                sql.append(" GROUP BY f.major ORDER BY countNum DESC LIMIT 10 ");
            }
        }

        String typeAll = request.getParameter("typeAll");
        if(StringUtil.isNotEmpty(typeAll)){
            if(typeAll.equals("2")){
                sql.append("SELECT count(hd.id) countNum,ai.address name,ai.id id from t_b_hidden_danger_exam hd LEFT JOIN t_b_address_info ai on ai.id = hd.address   ");
                if(StringUtil.isNotEmpty(yearMonth)){
                    sql.append(" where  hd.create_date >= '"+yearMonth+"-01' and hd.create_date <= '"+yearMonth+"-31' ");
                }
                sql.append(" GROUP BY ai.id ORDER BY countNum DESC    ");
            }else if(typeAll.equals("4")){
                sql.append("SELECT count(hd.id) countNum,t.typename name,t.typecode id  from t_b_hidden_danger_exam hd  LEFT JOIN t_s_type t on hd.risk_type = t.typecode LEFT JOIN t_s_typegroup tg on tg.id = t.typegroupid WHERE tg.typegroupcode = 'risk_type' ");
                if(StringUtil.isNotEmpty(yearMonth)){
                    sql.append(" and  hd.create_date >= '"+yearMonth+"-01' and hd.create_date <= '"+yearMonth+"-31' ");
                }
                sql.append(" GROUP BY hd.risk_type ORDER BY countNum DESC   ");
            }else if(typeAll.equals("5")){
                sql.append("SELECT count(0) countNum,f.id id,f.hazard_factors name FROM t_b_risk_manage_hazard_factor hf LEFT JOIN t_b_risk_manage_rel_hd rh on hf.id = rh.risk_manage_hazard_factor_id LEFT JOIN t_b_hazard_factors f on hf.hazard_factor_id = f.id \n" +
                        "WHERE rh.hd_id in (SELECT id from t_b_hidden_danger_exam hd ");
                if(StringUtil.isNotEmpty(yearMonth)){
                    sql.append(" where  hd.create_date >= '"+yearMonth+"-01' and hd.create_date <= '"+yearMonth+"-31' )");
                }
                sql.append(" GROUP BY f.id ORDER BY countNum DESC  ");
            }
        }

        List<Map<String, Object>> quertList = systemService.findForJdbc(sql.toString());
        dataGrid.setTotal(quertList.size());
        dataGrid.setResults(quertList);
        TagUtil.datagrid(response, dataGrid);
    }


    @RequestMapping(params = "hazardFactorStatisticsKJDatagrid")
    public void hazardFactorStatisticsKJDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String year = request.getParameter("year");
        if(StringUtils.isBlank(year)){
            Calendar ca = Calendar.getInstance();
            year =  ca.get(Calendar.YEAR)+"";
        }
        request.setAttribute("year",year);
        dataGrid.setResults(generateDateKJ(request,year));
        TagUtil.datagrid(response, dataGrid);

    }


    private List<MineYearRiskTrendEntity> generateDateKJ(HttpServletRequest request, String year){
        String countNumData = "";             //highcharts中data数据——管控
        String yearMonth = "";
        int iMonth = 1;

        Long countNum = null;      //管控


        if(StringUtils.isBlank(year)){
            Calendar ca = Calendar.getInstance();
            year = ca.get(Calendar.YEAR)+"";
        }
        List<MineYearRiskTrendEntity> resultList = new ArrayList();

        //这里查询一年的十二个月份
        for(;iMonth <= 12; iMonth++){
            if(iMonth < 10){
                yearMonth = year + "-0" + iMonth;
            }else{
                yearMonth = year + "-" + iMonth;
            }



           String sql = "SELECT\n" +
                   "\tsum(cnt) countNum\n" +
                   "FROM\n" +
                   "\t(\n" +
                   "\t\tSELECT\n" +
                   "\t\t\tcount(0) cnt\n" +
                   "\t\tFROM\n" +
                   "\t\t\tt_b_risk_manage_hazard_factor hf WHERE hf.create_date like '"+yearMonth+"%'\n" +
                   "\t\tUNION ALL\n" +
                   "\t\t\tSELECT\n" +
                   "\t\t\t\tCOUNT(0) cnt\n" +
                   "\t\t\tFROM\n" +
                   "\t\t\t\tt_b_hidden_danger_exam hd WHERE hd.create_date like '"+yearMonth+"%'\n" +
                   "\t) a\n" +
                   "ORDER BY\n" +
                   "\tcountNum DESC\n" +
                   "";
            Long count = systemService.getCountForJdbc(sql);
            //获取当月对应的管控的数量
            countNum = count;
            /**统计隐患——结束*/
            MineYearRiskTrendEntity vo = new MineYearRiskTrendEntity();
            vo.setMonth(yearMonth);

            vo.setDanger(countNum + "");

            resultList.add(vo);
        }

        for(MineYearRiskTrendEntity single : resultList){

            countNumData = countNumData + "," + single.getDanger();

        }

        countNumData = countNumData.substring(1 ,countNumData.length());



        request.setAttribute("countNumData",countNumData);


        return resultList;
    }



    @RequestMapping(params = "hiddenStatisticsAll")
    public ModelAndView hiddenStatisticsAll(HttpServletRequest request) {
        String typeAll = request.getParameter("typeAll");
        if(StringUtil.isNotEmpty(typeAll)){
            request.setAttribute("typeAll",typeAll);
            if(typeAll.equals("2")){
                String yearMonth = request.getParameter("yearMonth");
                if(StringUtils.isBlank(yearMonth)){
                    SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
                    yearMonth = date_sdf.format(new Date());
                }
                request.setAttribute("yearMonth",yearMonth);
                return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/riskHiddenStatistics2");
            }else if(typeAll.equals("3")){
                String yearMonth = request.getParameter("yearMonth");
                if(StringUtils.isBlank(yearMonth)){
                    SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
                    yearMonth = date_sdf.format(new Date());
                }
                request.setAttribute("yearMonth",yearMonth);
                return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/riskHiddenStatistics3");
            }else if(typeAll.equals("4")){
                String yearMonth = request.getParameter("yearMonth");
                if(StringUtils.isBlank(yearMonth)){
                    SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
                    yearMonth = date_sdf.format(new Date());
                }
                request.setAttribute("yearMonth",yearMonth);
                return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/riskHiddenStatistics4");
            }else if(typeAll.equals("5")){
                String yearMonth = request.getParameter("yearMonth");
                if(StringUtils.isBlank(yearMonth)){
                    SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
                    yearMonth = date_sdf.format(new Date());
                }
                request.setAttribute("yearMonth",yearMonth);
                return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/riskHiddenStatistics5");
            }else{
                String year = request.getParameter("year");
                if(StringUtils.isBlank(year)){
                    Calendar ca = Calendar.getInstance();
                    year =  ca.get(Calendar.YEAR)+"";
                }
                request.setAttribute("year",year);
                String addressId = request.getParameter("addressId");
                request.setAttribute("addressId",addressId);
                String riskType = request.getParameter("riskType");
                request.setAttribute("riskType",riskType);
                generateDate(request,year);
                return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/riskHiddenStatistics1");
            }
        }else{
            request.setAttribute("typeAll","1");
            String year = request.getParameter("year");
            if(StringUtils.isBlank(year)){
                Calendar ca = Calendar.getInstance();
                year =  ca.get(Calendar.YEAR)+"";
            }
            request.setAttribute("year",year);
            String addressId = request.getParameter("addressId");
            request.setAttribute("addressId",addressId);
            String riskType = request.getParameter("riskType");
            request.setAttribute("riskType",riskType);
            generateDate(request,year);
            return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/riskHiddenStatistics1");
        }
    }

    @RequestMapping(params = "statisticsDatagrid")
    public void statisticsDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String userId= request.getParameter("userId");
        String unitId = request.getParameter("unitId");
        StringBuffer sql = new StringBuffer("select u.id, concat(u.realName, ' - ' ,u.userName) realName ,u.userName, d.id departId, d.departName,IFNULL(u.spelling,'') spelling, IFNULL(u.full_spelling,'') fullSpelling from t_s_base_user u join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id ");
        String sort = dataGrid.getSort();
        String sortOrder = dataGrid.getOrder();
        String yearMonth = request.getParameter("yearMonth");
        SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
        if(yearMonth==null){
            yearMonth = date_sdf.format(new Date());
        }
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if(StringUtil.isNotEmpty(sort)){
            if(sort.equals("count1")){
                StringBuffer sql1 = new StringBuffer();
                sql1.append("SELECT count(0) count,create_by  from t_b_risk_identification WHERE 1=1 ");
                if(StringUtil.isNotEmpty(startDate)){
                    sql1.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
                }
                if(StringUtil.isNotEmpty(endDate)){
                    sql1.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
                }
                sql1.append(" and is_del = '0' and `status` = '3' GROUP BY create_by");
                sql.append(" left join ("+sql1+") sort on sort.create_by = u.username ");
            }
            if(sort.equals("count2")){
                StringBuffer sql2 = new StringBuffer();
                sql2.append("SELECT count(0) count,modifyMan  from t_b_risk_identification WHERE 1=1 ");
                if(StringUtil.isNotEmpty(startDate)){
                    sql2.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
                }
                if(StringUtil.isNotEmpty(endDate)){
                    sql2.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
                }
                sql2.append(" and is_del = '0' and `status` = '3'  GROUP BY modifyMan");
                sql.append(" left join ("+sql2+") sort on sort.modifyMan = u.id ");
            }
            if(sort.equals("count3")){
                StringBuffer sql3 = new StringBuffer();
                sql3.append("SELECT count(0) count,create_by  from t_b_hidden_danger_exam WHERE 1=1  ");
                if(StringUtil.isNotEmpty(startDate)){
                    sql3.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
                }
                if(StringUtil.isNotEmpty(endDate)){
                    sql3.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
                }
                sql3.append(" GROUP BY create_by");
                sql.append(" left join ("+sql3+") sort on sort.create_by = u.username ");
            }
            if(sort.equals("count4")){
                StringBuffer sql4 = new StringBuffer();
                sql4.append("SELECT count(0) count,userid  from t_s_log where 1=1 ");
                if(StringUtil.isNotEmpty(startDate)){
                    sql4.append( " and  LEFT(operatetime,10) >= '"+startDate+"' ");
                }
                if(StringUtil.isNotEmpty(endDate)){
                    sql4.append( " and  LEFT(operatetime,10) <= '"+endDate+"' ");
                }
                sql4.append(" and loglevel = '1' and operatetype = '1' GROUP BY userid");
                sql.append(" left join ("+sql4+") sort on sort.userid = u.id ");
            }
            if(sort.equals("count5")){
                StringBuffer sql5 = new StringBuffer();
                sql5.append("SELECT count(0) count,organizer_man  from t_b_risk_manage_task_all_manage WHERE 1=1 ");
                if(StringUtil.isNotEmpty(startDate)){
                    sql5.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
                }
                if(StringUtil.isNotEmpty(endDate)){
                    sql5.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
                }
                sql5.append(" and manage_type='comprehensive' GROUP BY organizer_man");
                sql.append(" left join ("+sql5+") sort on sort.organizer_man = u.id ");
            }
            if(sort.equals("count6")){
                StringBuffer sql6 = new StringBuffer();
                sql6.append("SELECT count(0) count,organizer_man  from t_b_risk_manage_task_all_manage WHERE 1=1 ");
                if(StringUtil.isNotEmpty(startDate)){
                    sql6.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
                }
                if(StringUtil.isNotEmpty(endDate)){
                    sql6.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
                }
                sql6.append(" and manage_type='profession' GROUP BY organizer_man");
                sql.append(" left join ("+sql6+") sort on sort.organizer_man = u.id ");
            }
            if(sort.equals("count7")){
                StringBuffer sql7 = new StringBuffer();
                sql7.append("SELECT count(0) count,check_man  from t_b_risk_manage_task_all_manage_check_rel WHERE 1=1 ");
                if(StringUtil.isNotEmpty(startDate)){
                    sql7.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
                }
                if(StringUtil.isNotEmpty(endDate)){
                    sql7.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
                }
                sql7.append("  and task_all_manage_id in (SELECT id from t_b_risk_manage_task_all_manage WHERE manage_type='comprehensive')  GROUP BY check_man");
                sql.append(" left join ("+sql7+") sort on sort.check_man = u.id ");
            }
            if(sort.equals("count8")){
                StringBuffer sql8 = new StringBuffer();
                sql8.append("SELECT count(0) count,check_man  from t_b_risk_manage_task_all_manage_check_rel WHERE 1=1  ");
                if(StringUtil.isNotEmpty(startDate)){
                    sql8.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
                }
                if(StringUtil.isNotEmpty(endDate)){
                    sql8.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
                }
                sql8.append("  and task_all_manage_id in (SELECT id from t_b_risk_manage_task_all_manage WHERE manage_type='profession')   GROUP BY check_man");
                sql.append(" left join ("+sql8+") sort on sort.check_man = u.id ");
            }
            if(sort.equals("count9")){
                StringBuffer sql9 = new StringBuffer();
                sql9.append("SELECT count(0) count,update_by from  t_b_risk_manage_hazard_factor WHERE 1=1  ");
                if(StringUtil.isNotEmpty(startDate)){
                    sql9.append( " and  LEFT(update_date,10) >= '"+startDate+"' ");
                }
                if(StringUtil.isNotEmpty(endDate)){
                    sql9.append( " and   LEFT(update_date,10) <= '"+endDate+"' ");
                }
                sql9.append("  and (impl_detail is not null or impl_detail = '')  GROUP BY update_by");
                sql.append(" left join ("+sql9+") sort on sort.update_by = u.username ");
            }
            if(sort.equals("count10")){
                StringBuffer sql10 = new StringBuffer();
                sql10.append("SELECT  count(DISTINCT DATE(create_date),shift) count ,create_by FROM t_b_hidden_danger_exam where 1=1  ");
                if(StringUtil.isNotEmpty(startDate)){
                    sql10.append( " and   LEFT(create_date,10) >= '"+startDate+"' ");
                }
                if(StringUtil.isNotEmpty(endDate)){
                    sql10.append( " and   LEFT(create_date,10)<= '"+endDate+"' ");
                }
                sql10.append("  GROUP BY create_by");
                sql.append(" left join ("+sql10+") sort on sort.create_by = u.username ");
            }
            if(sort.equals("count11")){
                StringBuffer sql11 = new StringBuffer();
                sql11.append("SELECT  count(id) count ,create_by FROM t_b_three_violations  where 1=1  ");
                if(StringUtil.isNotEmpty(startDate)){
                    sql11.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
                }
                if(StringUtil.isNotEmpty(endDate)){
                    sql11.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
                }
                sql11.append("  GROUP BY create_by");
                sql.append(" left join ("+sql11+") sort on sort.create_by = u.username ");
            }
        }
        sql.append(" where u.delete_flag='0'");
        sql.append("and u.status in ('").append(Globals.User_Normal).append("','").append(Globals.User_ADMIN).append("','").append(Globals.User_Forbidden).append("') ");
        if (StringUtil.isNotEmpty(unitId)){
            sql.append("and o.org_id in ('").append(unitId.replace(",","','")).append("') ");
        }
        if (StringUtil.isNotEmpty(userId)){
            sql.append("and u.id = '"+userId+"'");
        }
        if(StringUtil.isNotEmpty(sort)){
            if(StringUtil.isNotEmpty(sortOrder)){
                sql.append( "ORDER BY count "+sortOrder+"");
            }
        }
        List<Map<String,Object>> queryList = this.systemService.findForJdbc(sql.toString());
        int currentPage = dataGrid.getPage();
        int pageSize = dataGrid.getRows();
        int endIndex = pageSize * currentPage;

        if(endIndex > queryList.size()){
            endIndex = queryList.size();
        }
        List<Map<String,Object>> resultList = queryList.subList(pageSize * (currentPage - 1), endIndex);
        dataGrid.setResults(resultList);
        dataGrid.setTotal(queryList.size());
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            StringBuffer sql1 = new StringBuffer();
            sql1.append("SELECT count(0) count,create_by  from t_b_risk_identification WHERE 1=1 ");
            if(StringUtil.isNotEmpty(startDate)){
                sql1.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql1.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql1.append(" and is_del = '0' and `status` = '3' GROUP BY create_by");
            List<Map<String, Object>> list1 = systemService.findForJdbc(sql1.toString());
            Map<String, String> map1 = new HashMap<>();
            if (list1 !=null && list1.size()>0) {
                for (Map<String, Object> obj : list1) {
                    String createBy = String.valueOf(obj.get("create_by"));
                    String count = String.valueOf(obj.get("count"));
                    map1.put(createBy, count);
                }
            }
            StringBuffer sql2 = new StringBuffer();
            sql2.append("SELECT count(0) count,modifyMan  from t_b_risk_identification WHERE 1=1 ");
            if(StringUtil.isNotEmpty(startDate)){
                sql2.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql2.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql2.append(" and is_del = '0' and `status` = '3'  GROUP BY modifyMan");
            List<Map<String, Object>> list2 = systemService.findForJdbc(sql2.toString());
            Map<String, String> map2 = new HashMap<>();
            if (list2 !=null && list2.size()>0) {
                for (Map<String, Object> obj : list2) {
                    String createBy = String.valueOf(obj.get("modifyMan"));
                    String count = String.valueOf(obj.get("count"));
                    map2.put(createBy, count);
                }
            }
            StringBuffer sql3 = new StringBuffer();
            sql3.append("SELECT count(0) count,create_by  from t_b_hidden_danger_exam WHERE 1=1  ");
            if(StringUtil.isNotEmpty(startDate)){
                sql3.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql3.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql3.append(" GROUP BY create_by");
            List<Map<String, Object>> list3 = systemService.findForJdbc(sql3.toString());
            Map<String, String> map3 = new HashMap<>();
            if (list3 !=null && list3.size()>0) {
                for (Map<String, Object> obj : list3) {
                    String createBy = String.valueOf(obj.get("create_by"));
                    String count = String.valueOf(obj.get("count"));
                    map3.put(createBy, count);
                }
            }
            StringBuffer sql4 = new StringBuffer();
            sql4.append("SELECT count(0) count,userid  from t_s_log where 1=1 ");
            if(StringUtil.isNotEmpty(startDate)){
                sql4.append( " and  LEFT(operatetime,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql4.append( " and  LEFT(operatetime,10) <= '"+endDate+"' ");
            }
            sql4.append(" and loglevel = '1' and operatetype = '1' GROUP BY userid");
            List<Map<String, Object>> list4 = systemService.findForJdbc(sql4.toString());
            Map<String, String> map4 = new HashMap<>();
            if (list4 !=null && list4.size()>0) {
                for (Map<String, Object> obj : list4) {
                    String createBy = String.valueOf(obj.get("userid"));
                    String count = String.valueOf(obj.get("count"));
                    map4.put(createBy, count);
                }
            }
            StringBuffer sql5 = new StringBuffer();
            sql5.append("SELECT count(0) count,organizer_man  from t_b_risk_manage_task_all_manage WHERE 1=1 ");
            if(StringUtil.isNotEmpty(startDate)){
                sql5.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql5.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql5.append(" and manage_type='comprehensive' GROUP BY organizer_man");
            List<Map<String, Object>> list5 = systemService.findForJdbc(sql5.toString());
            Map<String, String> map5 = new HashMap<>();
            if (list5 !=null && list5.size()>0) {
                for (Map<String, Object> obj : list5) {
                    String createBy = String.valueOf(obj.get("organizer_man"));
                    String count = String.valueOf(obj.get("count"));
                    map5.put(createBy, count);
                }
            }
            StringBuffer sql6 = new StringBuffer();
            sql6.append("SELECT count(0) count,organizer_man  from t_b_risk_manage_task_all_manage WHERE 1=1 ");
            if(StringUtil.isNotEmpty(startDate)){
                sql6.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql6.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql6.append(" and manage_type='profession' GROUP BY organizer_man");
            List<Map<String, Object>> list6 = systemService.findForJdbc(sql6.toString());
            Map<String, String> map6 = new HashMap<>();
            if (list6 !=null && list6.size()>0) {
                for (Map<String, Object> obj : list6) {
                    String createBy = String.valueOf(obj.get("organizer_man"));
                    String count = String.valueOf(obj.get("count"));
                    map6.put(createBy, count);
                }
            }
            StringBuffer sql7 = new StringBuffer();
            sql7.append("SELECT count(0) count,check_man  from t_b_risk_manage_task_all_manage_check_rel WHERE 1=1 ");
            if(StringUtil.isNotEmpty(startDate)){
                sql7.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql7.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql7.append("  and task_all_manage_id in (SELECT id from t_b_risk_manage_task_all_manage WHERE manage_type='comprehensive')  GROUP BY check_man");
            List<Map<String, Object>> list7 = systemService.findForJdbc(sql7.toString());
            Map<String, String> map7 = new HashMap<>();
            if (list7 !=null && list7.size()>0) {
                for (Map<String, Object> obj : list7) {
                    String createBy = String.valueOf(obj.get("check_man"));
                    String count = String.valueOf(obj.get("count"));
                    map7.put(createBy, count);
                }
            }
            StringBuffer sql8 = new StringBuffer();
            sql8.append("SELECT count(0) count,check_man  from t_b_risk_manage_task_all_manage_check_rel WHERE 1=1  ");
            if(StringUtil.isNotEmpty(startDate)){
                sql8.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql8.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql8.append("  and task_all_manage_id in (SELECT id from t_b_risk_manage_task_all_manage WHERE manage_type='profession')   GROUP BY check_man");
            List<Map<String, Object>> list8 = systemService.findForJdbc(sql8.toString());
            Map<String, String> map8 = new HashMap<>();
            if (list8 !=null && list8.size()>0) {
                for (Map<String, Object> obj : list8) {
                    String createBy = String.valueOf(obj.get("check_man"));
                    String count = String.valueOf(obj.get("count"));
                    map8.put(createBy, count);
                }
            }
            StringBuffer sql9 = new StringBuffer();
            sql9.append("SELECT count(0) count,update_by from  t_b_risk_manage_hazard_factor WHERE 1=1  ");
            if(StringUtil.isNotEmpty(startDate)){
                sql9.append( " and LEFT(update_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql9.append( " and  LEFT(update_date,10) <= '"+endDate+"' ");
            }
            sql9.append("  and (impl_detail is not null or impl_detail = '')  GROUP BY update_by");
            List<Map<String, Object>> list9 = systemService.findForJdbc(sql9.toString());
            Map<String, String> map9 = new HashMap<>();
            if (list9 !=null && list9.size()>0) {
                for (Map<String, Object> obj : list9) {
                    String createBy = String.valueOf(obj.get("update_by"));
                    String count = String.valueOf(obj.get("count"));
                    map9.put(createBy, count);
                }
            }
            StringBuffer sql10 = new StringBuffer();
            sql10.append("SELECT  count(DISTINCT DATE(create_date),shift) count ,create_by FROM t_b_hidden_danger_exam where 1=1  ");
            if(StringUtil.isNotEmpty(startDate)){
                sql10.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql10.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql10.append("  GROUP BY create_by");
            List<Map<String, Object>> list10 = systemService.findForJdbc(sql10.toString());
            Map<String, String> map10 = new HashMap<>();
            if (list10 !=null && list10.size()>0) {
                for (Map<String, Object> obj : list10) {
                    String createBy = String.valueOf(obj.get("create_by"));
                    String count = String.valueOf(obj.get("count"));
                    map10.put(createBy, count);
                }
            }
            StringBuffer sql11 = new StringBuffer();
            sql11.append("SELECT  count(id) count ,create_by FROM t_b_three_violations  where 1=1  ");
            if(StringUtil.isNotEmpty(startDate)){
                sql11.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql11.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql11.append("  GROUP BY create_by");
            List<Map<String, Object>> list11 = systemService.findForJdbc(sql11.toString());
            Map<String, String> map11 = new HashMap<>();
            if (list11 !=null && list11.size()>0) {
                for (Map<String, Object> obj : list11) {
                    String createBy = String.valueOf(obj.get("create_by"));
                    String count = String.valueOf(obj.get("count"));
                    map11.put(createBy, count);
                }
            }
            List<Map<String,Object>>  list = dataGrid.getResults();
            for ( Map<String, Object>  bean : list) {
                String createBy = String.valueOf(bean.get("userName"));
                String id = String.valueOf(bean.get("id"));
                String count1 = map1.get(createBy);
                String count2 = map2.get(id);
                String count3 = map3.get(createBy);
                String count4 = map4.get(id);
                String count5 = map5.get(id);
                String count6 = map6.get(id);
                String count7 = map7.get(id);
                String count8 = map8.get(id);
                String count9 = map9.get(createBy);
                String count10 = map10.get(createBy);
                String count11 = map11.get(createBy);
                if(StringUtil.isNotEmpty(count1)){
                    bean.put("count1",count1.replace(".0",""));
                }else {
                    bean.put("count1","0");
                }
                if(StringUtil.isNotEmpty(count2)){
                    bean.put("count2",count2.replace(".0",""));
                }else {
                    bean.put("count2","0");
                }
                if(StringUtil.isNotEmpty(count3)){
                    bean.put("count3",count3.replace(".0",""));
                }else {
                    bean.put("count3","0");
                }
                if(StringUtil.isNotEmpty(count4)){
                    bean.put("count4",count4.replace(".0",""));
                }else {
                    bean.put("count4","0");
                }
                if(StringUtil.isNotEmpty(count5)){
                    bean.put("count5",count5.replace(".0",""));
                }else {
                    bean.put("count5","0");
                }
                if(StringUtil.isNotEmpty(count6)){
                    bean.put("count6",count6.replace(".0",""));
                }else {
                    bean.put("count6","0");
                }
                if(StringUtil.isNotEmpty(count7)){
                    bean.put("count7",count7.replace(".0",""));
                }else {
                    bean.put("count7","0");
                }
                if(StringUtil.isNotEmpty(count8)){
                    bean.put("count8",count8.replace(".0",""));
                }else {
                    bean.put("count8","0");
                }
                if(StringUtil.isNotEmpty(count9)){
                    bean.put("count9",count9.replace(".0",""));
                }else {
                    bean.put("count9","0");
                }
                if(StringUtil.isNotEmpty(count10)){
                    bean.put("count10",count10.replace(".0",""));
                }else {
                    bean.put("count10","0");
                }
                if(StringUtil.isNotEmpty(count11)){
                    bean.put("count11",count11.replace(".0",""));
                }else {
                    bean.put("count11","0");
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "exportXlsDatagrid")
    public String exportXlsDatagrid(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, DataGrid dataGrid) {
        String userId= request.getParameter("userId");
        String unitId = request.getParameter("unitId");
        StringBuffer sql = new StringBuffer("select u.id, concat(u.realName, ' - ' ,u.userName) realName ,u.userName, d.id departId, d.departName,IFNULL(u.spelling,'') spelling, IFNULL(u.full_spelling,'') fullSpelling from t_s_base_user u join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id where u.delete_flag='0'");
        sql.append("and u.status in ('").append(Globals.User_Normal).append("','").append(Globals.User_ADMIN).append("','").append(Globals.User_Forbidden).append("') ");
        if (StringUtil.isNotEmpty(unitId)){
            sql.append("and o.org_id in ('").append(unitId.replace(",","','")).append("') ");
        }
        if (StringUtil.isNotEmpty(userId)){
            sql.append("and u.id = '"+userId+"'");
        }
        List<Map<String,Object>> queryList = this.systemService.findForJdbc(sql.toString());
        String yearMonth = request.getParameter("yearMonth");
        SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
        if(yearMonth==null){
            yearMonth = date_sdf.format(new Date());
        }
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if (queryList != null && queryList.size()>0) {
            StringBuffer sql1 = new StringBuffer();
            sql1.append("SELECT count(0) count,create_by  from t_b_risk_identification WHERE 1=1 ");
            if(StringUtil.isNotEmpty(startDate)){
                sql1.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql1.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql1.append(" and is_del = '0' and `status` = '3' GROUP BY create_by");
            List<Map<String, Object>> list1 = systemService.findForJdbc(sql1.toString());
            Map<String, String> map1 = new HashMap<>();
            if (list1 !=null && list1.size()>0) {
                for (Map<String, Object> obj : list1) {
                    String createBy = String.valueOf(obj.get("create_by"));
                    String count = String.valueOf(obj.get("count"));
                    map1.put(createBy, count);
                }
            }
            StringBuffer sql2 = new StringBuffer();
            sql2.append("SELECT count(0) count,modifyMan  from t_b_risk_identification WHERE 1=1 ");
            if(StringUtil.isNotEmpty(startDate)){
                sql2.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql2.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql2.append(" and is_del = '0' and `status` = '3'  GROUP BY modifyMan");
            List<Map<String, Object>> list2 = systemService.findForJdbc(sql2.toString());
            Map<String, String> map2 = new HashMap<>();
            if (list2 !=null && list2.size()>0) {
                for (Map<String, Object> obj : list2) {
                    String createBy = String.valueOf(obj.get("modifyMan"));
                    String count = String.valueOf(obj.get("count"));
                    map2.put(createBy, count);
                }
            }
            StringBuffer sql3 = new StringBuffer();
            sql3.append("SELECT count(0) count,create_by  from t_b_hidden_danger_exam WHERE 1=1  ");
            if(StringUtil.isNotEmpty(startDate)){
                sql3.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql3.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql3.append(" GROUP BY create_by");
            List<Map<String, Object>> list3 = systemService.findForJdbc(sql3.toString());
            Map<String, String> map3 = new HashMap<>();
            if (list3 !=null && list3.size()>0) {
                for (Map<String, Object> obj : list3) {
                    String createBy = String.valueOf(obj.get("create_by"));
                    String count = String.valueOf(obj.get("count"));
                    map3.put(createBy, count);
                }
            }
            StringBuffer sql4 = new StringBuffer();
            sql4.append("SELECT count(0) count,userid  from t_s_log where 1=1 ");
            if(StringUtil.isNotEmpty(startDate)){
                sql4.append( " and  LEFT(operatetime,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql4.append( " and  LEFT(operatetime,10) <= '"+endDate+"' ");
            }
            sql4.append(" and loglevel = '1' and operatetype = '1' GROUP BY userid");
            List<Map<String, Object>> list4 = systemService.findForJdbc(sql4.toString());
            Map<String, String> map4 = new HashMap<>();
            if (list4 !=null && list4.size()>0) {
                for (Map<String, Object> obj : list4) {
                    String createBy = String.valueOf(obj.get("userid"));
                    String count = String.valueOf(obj.get("count"));
                    map4.put(createBy, count);
                }
            }
            StringBuffer sql5 = new StringBuffer();
            sql5.append("SELECT count(0) count,organizer_man  from t_b_risk_manage_task_all_manage WHERE 1=1 ");
            if(StringUtil.isNotEmpty(startDate)){
                sql5.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql5.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql5.append(" and manage_type='comprehensive' GROUP BY organizer_man");
            List<Map<String, Object>> list5 = systemService.findForJdbc(sql5.toString());
            Map<String, String> map5 = new HashMap<>();
            if (list5 !=null && list5.size()>0) {
                for (Map<String, Object> obj : list5) {
                    String createBy = String.valueOf(obj.get("organizer_man"));
                    String count = String.valueOf(obj.get("count"));
                    map5.put(createBy, count);
                }
            }
            StringBuffer sql6 = new StringBuffer();
            sql6.append("SELECT count(0) count,organizer_man  from t_b_risk_manage_task_all_manage WHERE 1=1 ");
            if(StringUtil.isNotEmpty(startDate)){
                sql6.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql6.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql6.append(" and manage_type='profession' GROUP BY organizer_man");
            List<Map<String, Object>> list6 = systemService.findForJdbc(sql6.toString());
            Map<String, String> map6 = new HashMap<>();
            if (list6 !=null && list6.size()>0) {
                for (Map<String, Object> obj : list6) {
                    String createBy = String.valueOf(obj.get("organizer_man"));
                    String count = String.valueOf(obj.get("count"));
                    map6.put(createBy, count);
                }
            }
            StringBuffer sql7 = new StringBuffer();
            sql7.append("SELECT count(0) count,check_man  from t_b_risk_manage_task_all_manage_check_rel WHERE 1=1 ");
            if(StringUtil.isNotEmpty(startDate)){
                sql7.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql7.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql7.append("  and task_all_manage_id in (SELECT id from t_b_risk_manage_task_all_manage WHERE manage_type='comprehensive')  GROUP BY check_man");
            List<Map<String, Object>> list7 = systemService.findForJdbc(sql7.toString());
            Map<String, String> map7 = new HashMap<>();
            if (list7 !=null && list7.size()>0) {
                for (Map<String, Object> obj : list7) {
                    String createBy = String.valueOf(obj.get("check_man"));
                    String count = String.valueOf(obj.get("count"));
                    map7.put(createBy, count);
                }
            }
            StringBuffer sql8 = new StringBuffer();
            sql8.append("SELECT count(0) count,check_man  from t_b_risk_manage_task_all_manage_check_rel WHERE 1=1  ");
            if(StringUtil.isNotEmpty(startDate)){
                sql8.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql8.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql8.append("  and task_all_manage_id in (SELECT id from t_b_risk_manage_task_all_manage WHERE manage_type='profession')   GROUP BY check_man");
            List<Map<String, Object>> list8 = systemService.findForJdbc(sql8.toString());
            Map<String, String> map8 = new HashMap<>();
            if (list8 !=null && list8.size()>0) {
                for (Map<String, Object> obj : list8) {
                    String createBy = String.valueOf(obj.get("check_man"));
                    String count = String.valueOf(obj.get("count"));
                    map8.put(createBy, count);
                }
            }
            StringBuffer sql9 = new StringBuffer();
            sql9.append("SELECT count(0) count,update_by from  t_b_risk_manage_hazard_factor WHERE 1=1  ");
            if(StringUtil.isNotEmpty(startDate)){
                sql9.append( " and LEFT(update_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql9.append( " and  LEFT(update_date,10) <= '"+endDate+"' ");
            }
            sql9.append("  and (impl_detail is not null or impl_detail = '')  GROUP BY update_by");
            List<Map<String, Object>> list9 = systemService.findForJdbc(sql9.toString());
            Map<String, String> map9 = new HashMap<>();
            if (list9 !=null && list9.size()>0) {
                for (Map<String, Object> obj : list9) {
                    String createBy = String.valueOf(obj.get("update_by"));
                    String count = String.valueOf(obj.get("count"));
                    map9.put(createBy, count);
                }
            }
            StringBuffer sql10 = new StringBuffer();
            sql10.append("SELECT  count(DISTINCT DATE(create_date),shift) count ,create_by FROM t_b_hidden_danger_exam where 1=1  ");
            if(StringUtil.isNotEmpty(startDate)){
                sql10.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql10.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql10.append("  GROUP BY create_by");
            List<Map<String, Object>> list10 = systemService.findForJdbc(sql10.toString());
            Map<String, String> map10 = new HashMap<>();
            if (list10 !=null && list10.size()>0) {
                for (Map<String, Object> obj : list10) {
                    String createBy = String.valueOf(obj.get("create_by"));
                    String count = String.valueOf(obj.get("count"));
                    map10.put(createBy, count);
                }
            }
            StringBuffer sql11 = new StringBuffer();
            sql11.append("SELECT  count(id) count ,create_by FROM t_b_three_violations  where 1=1  ");
            if(StringUtil.isNotEmpty(startDate)){
                sql11.append( " and  LEFT(create_date,10) >= '"+startDate+"' ");
            }
            if(StringUtil.isNotEmpty(endDate)){
                sql11.append( " and  LEFT(create_date,10) <= '"+endDate+"' ");
            }
            sql11.append("  GROUP BY create_by");
            List<Map<String, Object>> list11 = systemService.findForJdbc(sql11.toString());
            Map<String, String> map11 = new HashMap<>();
            if (list11 !=null && list11.size()>0) {
                for (Map<String, Object> obj : list11) {
                    String createBy = String.valueOf(obj.get("create_by"));
                    String count = String.valueOf(obj.get("count"));
                    map11.put(createBy, count);
                }
            }
            for ( Map<String, Object>  bean : queryList) {
                String createBy = String.valueOf(bean.get("userName"));
                String id = String.valueOf(bean.get("id"));
                String count1 = map1.get(createBy);
                String count2 = map2.get(id);
                String count3 = map3.get(createBy);
                String count4 = map4.get(id);
                String count5 = map5.get(id);
                String count6 = map6.get(id);
                String count7 = map7.get(id);
                String count8 = map8.get(id);
                String count9 = map9.get(createBy);
                String count10 = map10.get(createBy);
                String count11 = map11.get(createBy);
                if(StringUtil.isNotEmpty(count1)){
                    bean.put("count1",count1.replace(".0",""));
                }else {
                    bean.put("count1","0");
                }
                if(StringUtil.isNotEmpty(count2)){
                    bean.put("count2",count2.replace(".0",""));
                }else {
                    bean.put("count2","0");
                }
                if(StringUtil.isNotEmpty(count3)){
                    bean.put("count3",count3.replace(".0",""));
                }else {
                    bean.put("count3","0");
                }
                if(StringUtil.isNotEmpty(count4)){
                    bean.put("count4",count4.replace(".0",""));
                }else {
                    bean.put("count4","0");
                }
                if(StringUtil.isNotEmpty(count5)){
                    bean.put("count5",count5.replace(".0",""));
                }else {
                    bean.put("count5","0");
                }
                if(StringUtil.isNotEmpty(count6)){
                    bean.put("count6",count6.replace(".0",""));
                }else {
                    bean.put("count6","0");
                }
                if(StringUtil.isNotEmpty(count7)){
                    bean.put("count7",count7.replace(".0",""));
                }else {
                    bean.put("count7","0");
                }
                if(StringUtil.isNotEmpty(count8)){
                    bean.put("count8",count8.replace(".0",""));
                }else {
                    bean.put("count8","0");
                }
                if(StringUtil.isNotEmpty(count9)){
                    bean.put("count9",count9.replace(".0",""));
                }else {
                    bean.put("count9","0");
                }
                if(StringUtil.isNotEmpty(count10)){
                    bean.put("count10",count10.replace(".0",""));
                }else {
                    bean.put("count10","0");
                }
                if(StringUtil.isNotEmpty(count11)){
                    bean.put("count11",count11.replace(".0",""));
                }else {
                    bean.put("count11","0");
                }
            }
        }
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_rglryzhfx.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", queryList);
        modelMap.put(NormalExcelConstants.FILE_NAME,"管理人员综合分析列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    @RequestMapping(params = "addressDatagrid")
    public void addressDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String addressId= request.getParameter("addressId");
        String unitId = request.getParameter("unitId");
        StringBuffer sql = new StringBuffer("SELECT ad.address, de.departname, IFNULL(hz.number, 0) count FROM t_b_address_info ad JOIN t_s_depart de LEFT JOIN ( SELECT t1.duty_unit dutyunit, t2.departname departname, t1.address address, t3.address addressname, count(*) number FROM t_b_hidden_danger_exam t1 LEFT JOIN t_s_depart t2 ON t1.duty_unit = t2.ID LEFT JOIN t_b_address_info t3 ON t1.address = t3.id WHERE t1.duty_unit IS NOT NULL AND t1.address IS NOT NULL  ");
        String sort = dataGrid.getSort();
        String sortOrder = dataGrid.getOrder();
        String yearMonth = request.getParameter("yearMonth");
        SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
        if(yearMonth==null){
            yearMonth = date_sdf.format(new Date());
        }
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if(StringUtil.isNotEmpty(startDate)){
            sql.append( " and  LEFT(t1.create_date,10) >= '"+startDate+"' ");
        }
        if(StringUtil.isNotEmpty(endDate)){
            sql.append( " and  LEFT(t1.create_date,10) <= '"+endDate+"' ");
        }
        sql.append( "GROUP BY t1.duty_unit, t1.address ) hz ON hz.address = ad.id AND hz.dutyunit = de.id WHERE de.delete_flag = '0' AND ad.is_delete = '0'  ");
        if (StringUtil.isNotEmpty(unitId)){
            sql.append("and de.id = '"+unitId+"'");
        }
        if (StringUtil.isNotEmpty(addressId)){
            sql.append("and ad.id = '"+addressId+"'");
        }
        if(StringUtil.isNotEmpty(sort)){
            sql.append( "ORDER BY  count "+sortOrder+"");
        }else if(StringUtil.isNotEmpty(unitId)||StringUtil.isNotEmpty(addressId)){
            sql.append( "ORDER BY  count DESC");
        }else{
            sql.append( "ORDER BY de.departname, count DESC");
        }
        List<Map<String,Object>> queryList = this.systemService.findForJdbc(sql.toString());
        int currentPage = dataGrid.getPage();
        int pageSize = dataGrid.getRows();
        int endIndex = pageSize * currentPage;

        if(endIndex > queryList.size()){
            endIndex = queryList.size();
        }
        List<Map<String,Object>> resultList = queryList.subList(pageSize * (currentPage - 1), endIndex);
        dataGrid.setResults(resultList);
        dataGrid.setTotal(queryList.size());
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "exportXlsAddressDatagrid")
    public String exportXlsAddressDatagrid(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, DataGrid dataGrid) {
        String addressId= request.getParameter("addressId");
        String unitId = request.getParameter("unitId");
        StringBuffer sql = new StringBuffer("SELECT ad.address, de.departname, IFNULL(hz.number, 0) count FROM t_b_address_info ad JOIN t_s_depart de LEFT JOIN ( SELECT t1.duty_unit dutyunit, t2.departname departname, t1.address address, t3.address addressname, count(*) number FROM t_b_hidden_danger_exam t1 LEFT JOIN t_s_depart t2 ON t1.duty_unit = t2.ID LEFT JOIN t_b_address_info t3 ON t1.address = t3.id WHERE t1.duty_unit IS NOT NULL AND t1.address IS NOT NULL  ");
        String yearMonth = request.getParameter("yearMonth");
        SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM");
        if(yearMonth==null){
            yearMonth = date_sdf.format(new Date());
        }
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if(StringUtil.isNotEmpty(startDate)){
            sql.append( " and  LEFT(t1.create_date,10) >= '"+startDate+"' ");
        }
        if(StringUtil.isNotEmpty(endDate)){
            sql.append( " and  LEFT(t1.create_date,10) <= '"+endDate+"' ");
        }
        sql.append( "GROUP BY t1.duty_unit, t1.address ) hz ON hz.address = ad.id AND hz.dutyunit = de.id WHERE de.delete_flag = '0' AND ad.is_delete = '0'  ");
        if (StringUtil.isNotEmpty(unitId)){
            sql.append("and de.id = '"+unitId+"'");
        }
        if (StringUtil.isNotEmpty(addressId)){
            sql.append("and ad.id = '"+addressId+"'");
        }
        if(StringUtil.isNotEmpty(unitId)||StringUtil.isNotEmpty(addressId)){
            sql.append( "ORDER BY  count DESC");
        }else{
            sql.append( "ORDER BY de.departname, count DESC");
        }
        List<Map<String,Object>> queryList = this.systemService.findForJdbc(sql.toString());
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_fxdjcqk.xlsx");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", queryList);
        modelMap.put(NormalExcelConstants.FILE_NAME,"风险点隐患情况列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }
}