package com.sdzk.buss.web.homePage.controller;

import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.address.service.TBAddressInfoServiceI;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.hiddendangerhistory.controller.TBHiddenDangerHistoryController;
import com.sdzk.buss.web.homePage.entity.HiddenDangerByGroupVO;
import com.sdzk.buss.web.homePage.entity.HiddenDangerByProfessionVO;
import com.sdzk.buss.web.homePage.entity.MajorRiskEntityVO;
import com.sdzk.buss.web.homePage.entity.OvertimeHiddenDangerVO;
import com.sdzk.buss.web.layer.entity.TBLayerEntity;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/homePageController")
public class HomePageController extends BaseController{
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBHiddenDangerHistoryController.class);
    @Autowired
    private SystemService systemService;
    @Autowired
    private TBAddressInfoServiceI tBAddressInfoService;

    /**
     *  首页 跳转
     * */
    @RequestMapping(params = "goHomePage")
    public ModelAndView goHomePage(HttpServletRequest request, HttpServletResponse response){
        String startDate = DateUtils.getDate("yyyy-MM") + "-01";
        String endDate = DateUtils.getDate("yyyy-MM-dd");

        /********************隐患超期未整改公告**********************/

        String sql = "select exam.exam_date,exam.duty_unit,exam.duty_man,exam.problem_desc from t_b_hidden_danger_handle handle LEFT JOIN t_b_hidden_danger_exam exam on handle.hidden_danger_id = exam.id" +
                " where handle.handlel_status in('1', '4') and exam.limit_date < DATE_FORMAT(NOW(),'%Y-%m-%d')  order by exam.exam_date";
        List<Object[]> overtimeList = systemService.findListbySql(sql);
        List<OvertimeHiddenDangerVO> voList = new ArrayList<OvertimeHiddenDangerVO>();
        for(Object[] o : overtimeList){
            OvertimeHiddenDangerVO vo = new OvertimeHiddenDangerVO();
            if (o[0].toString().length()>9 && o[0]!=null){
                vo.setFineDate(o[0].toString().substring(0, 10));
            }

            TSDepart tsDepart = systemService.get(TSDepart.class, o[1].toString());
            if(tsDepart!=null){
                vo.setDutyUnit(tsDepart.getDepartname());
            }
            TSUser tsUser = systemService.get(TSUser.class, o[2].toString());
            if(tsUser!=null){
                vo.setDutyMan(tsUser.getRealName());
            }
            vo.setDesc(o[3].toString());

            voList.add(vo);
        }

        request.setAttribute("retList", voList);
        /***************************************************************/

        request.setAttribute("startDate", startDate);
        request.setAttribute("endDate", endDate);
        return new ModelAndView("com/sdzk/buss/web/homePage/homePage");
    }


    /**
     *  代办任务页面 跳转
     * */
    @RequestMapping(params = "waitingTask")
    public ModelAndView waitingTask(HttpServletRequest request, HttpServletResponse response){
        String yunhe = ResourceUtil.getConfigByName("yunhe");
        request.setAttribute("yunhe",yunhe);
        String jinyuan = ResourceUtil.getConfigByName("jinyuan");
        request.setAttribute("jinyuan",jinyuan);
        return new ModelAndView("com/sdzk/buss/web/homePage/waitingTask");
    }

    /**
     *  代办任务 数据组装
     * */
    @RequestMapping(params = "homePageData")
    @ResponseBody
    public JSONObject homePageData(HttpServletRequest request, HttpServletResponse response){
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        JSONObject jo = new JSONObject();
//        String beginDate = request.getParameter("beginDate");
//        String endDate = request.getParameter("endDate");

//        String rectSql = "SELECT count(*) from t_b_hidden_danger_handle handle LEFT JOIN t_b_hidden_danger_exam exam ON handle.hidden_danger_id = exam.id where handle.handlel_status in ('1','4')";
        String rectSql = "SELECT count(1) count from t_b_hidden_danger_handle hdh LEFT JOIN t_b_hidden_danger_exam hde on hdh.hidden_danger_id=hde.id where  hdh.handlel_status in ('1','4')  ";
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
        if(!isAdmin&&!DicUtil.modifyPower(sessionUser.getId())){
            rectSql += " AND (hde.duty_unit IN (\n" +
                    "SELECT org_id\n" +
                    "FROM t_s_base_user tsbu,t_s_user_org\n" +
                    "where tsbu.ID = t_s_user_org.user_id AND tsbu.username = '"+sessionUser.getUserName()+
                    "') or hde.duty_man = '"+sessionUser.getRealName()+"' )";
        }
        String applySql = "SELECT count(1) count from t_b_hidden_danger_handle hdh LEFT JOIN t_b_hidden_danger_exam hde on hdh.hidden_danger_id=hde.id LEFT JOIN t_b_hidden_danger_apply apy on hde.apply_delay = apy.id where apy.deal_status = '1' ";
        if(!isAdmin) {
            applySql += "  and apply_man = '"+sessionUser.getId()+"'";
        }
        //            String reviewSql = "SELECT count(*) from t_b_hidden_danger_handle handle LEFT JOIN t_b_hidden_danger_exam exam ON handle.hidden_danger_id = exam.id where handle.handlel_status = '3'";
        String reviewSql = "SELECT count(1) count from t_b_hidden_danger_handle hdh LEFT JOIN t_b_hidden_danger_exam hde on hdh.hidden_danger_id=hde.id where  hdh.handlel_status='3' ";
        String ezhuang = ResourceUtil.getConfigByName("ezhuang");
        if(ezhuang.equals("true")){
            if(!isAdmin){
                reviewSql += "and EXISTS (\t\n" +
                        "\t\tselect * from t_s_base_user u, t_s_user_org uo\n" +
                        "\t\twhere '"+sessionUser.getId()+"'=u.id \n" +
                        "\t\tand (u.username=hde.create_by or FIND_IN_SET(u.id,hde.fill_card_manids))\n" +
                        "\t)";
            }
        }else{
            if(!isAdmin&&!isAJY&&!DicUtil.reviewPower(sessionUser.getId())){
                reviewSql += "and (EXISTS (\t\n" +
                        "\t\tselect * from t_s_base_user u, t_s_user_org uo\n" +
                        "\t\twhere uo.user_id=u.id and uo.org_id='" + sessionUser.getCurrentDepart().getId() + "'\n" +
                        "\t\tand (u.username=hde.create_by or FIND_IN_SET(u.id,hde.fill_card_manids))\n" +
                        "\t) or  hde.manage_duty_unit = '" + sessionUser.getCurrentDepart().getId() + "' or hde.manage_duty_man_id = '" + sessionUser.getId() + "')";
            }
        }
        String examSql = "select count(id) from t_b_risk_identification where is_del='0' and status ='1' ";
        if(!isAdmin){
            examSql = "select count(id) from t_b_risk_identification where is_del='0' and status ='1' and review_man = '"+sessionUser.getId()+"'";
        }
        //待受理计划
        String y_proSql = "select count(id) from t_b_investigate_plan where INVESTIGATE_TYPE='1' and STATUS='3'";       //月排查计划
        String x_proSql = "select count(id) from t_b_investigate_plan where INVESTIGATE_TYPE='2' and STATUS='3'";       //旬排查计划
        String z_proSql = "select count(id) from t_b_investigate_plan where INVESTIGATE_TYPE='3' and STATUS='3'";       //周排查计划

        //查询未到期的通知公告
        String endtimesql="select count(id) from t_s_notice where notice_term>=Date(NOW())";
//        if(StringUtil.isNotEmpty(beginDate)){
//            rectSql = rectSql + " and exam.exam_date>'" + beginDate + "'";
//            reviewSql = reviewSql + " and exam.exam_date>'" + beginDate + "'";
//            examSql = examSql + " and ye_recognize_time>'" + beginDate + "'";
//        }
//        if(StringUtil.isNotEmpty(endDate)){
//            rectSql = rectSql + " and exam.exam_date<'" + endDate + "'";
//            reviewSql = reviewSql + " and exam.exam_date<'" + endDate + "'";
//            examSql = examSql + " and ye_recognize_time<'" + endDate + "'";
//        }
//        if(StringUtil.isEmpty(beginDate) && StringUtil.isEmpty(endDate)){
//            rectSql = rectSql + " and exam.exam_date like '" + DateUtils.getDate("yyyy-MM") + "%'";
//            reviewSql = reviewSql + " and exam.exam_date like '" + DateUtils.getDate("yyyy-MM") + "%'";
//            examSql = examSql + " and ye_recognize_time like '" + DateUtils.getDate("yyyy-MM") + "%'";
//        }
        //待审核的辨识任务
        String riskTaskSql = "SELECT COUNT(0) FROM t_b_risk_task_participant_rel WHERE  participant_man_id = '"+sessionUser.getId()+"' and STATUS = '0'";
        //待处理的管控任务
        String manageTaskSql = "SELECT COUNT(0) from t_b_risk_manage_task_all WHERE create_by = '"+sessionUser.getUserName()+"' and status = '0' ";

        List<Object> rectList = systemService.findListbySql(rectSql);
        List<Object> applyList = systemService.findListbySql(applySql);
        List<Object> reviewList = systemService.findListbySql(reviewSql);
        List<Object> examList = systemService.findListbySql(examSql);
        List<Object> y_proList = systemService.findListbySql(y_proSql);
        List<Object> x_proList = systemService.findListbySql(x_proSql);
        List<Object> z_proList = systemService.findListbySql(z_proSql);
      List<Object> endtimelist=systemService.findListbySql(endtimesql);

        List<Object> riskTaskList=systemService.findListbySql(riskTaskSql);
        List<Object> manageTaskList=systemService.findListbySql(manageTaskSql);
        jo.put("daizhenggai", rectList.get(0).toString());
        jo.put("daipf", applyList.get(0).toString());
        jo.put("daifucha", reviewList.get(0).toString());
        jo.put("daishenhe", examList.get(0).toString());
        jo.put("y_weishouli", y_proList.get(0).toString());
        jo.put("x_weishouli", x_proList.get(0).toString());
        jo.put("z_weishouli", z_proList.get(0).toString());
        jo.put("endtimelist",endtimelist.get(0).toString());
        jo.put("riskTaskList",riskTaskList.get(0).toString());
        jo.put("manageTaskList",manageTaskList.get(0).toString());
        return jo;
    }


    /**
     *  双防总体趋势 跳转
     * */
    @RequestMapping(params = "overallTrend")
    public ModelAndView overallTrend(HttpServletRequest request, HttpServletResponse response){
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

        return new ModelAndView("com/sdzk/buss/web/homePage/overallTrend");
    }

    /**
     *  双防总体趋势 数据组装
     * */
    @RequestMapping(params = "overallTrendData")
    @ResponseBody
    public JSONObject overallTrendData(HttpServletRequest request, HttpServletResponse response){
        JSONObject jo = new JSONObject();

        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        //获取当月风险点数量
        String addrSql = "select count(id) from t_b_address_info where is_delete='0' and isShowData = '1' and (start_date <= '"+startDate+"' or start_date is null)  and (end_date  >= '"+endDate+"' or end_date is null)";
        //获取年度风险总数
        String yearRiskSql = "select count(id) from t_b_risk_identification where status='3' and is_del = '0' and identifi_date <= now() and (exp_date >= now() or exp_date is null) ";
        //获取当月隐患闭环总数
        String closeHiddSql = "select count(*) from t_b_hidden_danger_exam exam RIGHT JOIN t_b_hidden_danger_handle handle ON handle.hidden_danger_id = exam.id where handle.handlel_status!='5' and handle.handlel_status!='00'  ";

        if(StringUtil.isEmpty(startDate) && StringUtil.isEmpty(endDate)){
//            addrSql += " and create_date like '"+ DateUtils.getDate("yyyy-MM") +"%'";
            //yearRiskSql += " and ye_recognize_time like '"+ DateUtils.getDate("yyyy-MM") +"%'";
           // closeHiddSql += " and exam.exam_date like '" + DateUtils.getDate("yyyy-MM") + "%'";
        }else{
            //yearRiskSql += " and ye_recognize_time like '"+ DateUtils.getDate("yyyy") +"%'";
        }
        if(StringUtil.isNotEmpty(startDate)){
//            addrSql += " and create_date > '" + startDate +"'";
            //yearRiskSql += " and ye_recognize_time > '" + startDate + "'";
           // closeHiddSql += " and exam.exam_date >= '" + startDate + "'";
        }
        if(StringUtil.isNotEmpty(endDate)){
//            addrSql += " and create_date < '" + endDate + "'";
            //yearRiskSql += " and ye_recognize_time < '" + endDate + "'";
          //  closeHiddSql += " and exam.exam_date <= '" + endDate + "'";
        }


        List<Object> addrList = systemService.findListbySql(addrSql);
        List<Object> yearRiskList = systemService.findListbySql(yearRiskSql);
        List<Object> closeHiddList = systemService.findListbySql(closeHiddSql);

        jo.put("addrNum", addrList.get(0).toString());
        jo.put("yearRiskNum", yearRiskList.get(0).toString());
        jo.put("closeHiddNum", closeHiddList.get(0).toString());

        return jo;
    }


    /**
     *  重大风险 跳转
     * */
    @RequestMapping(params = "majorRisk")
    public ModelAndView majorRisk(HttpServletRequest request, HttpServletResponse response){
        String yunhe = ResourceUtil.getConfigByName("yunhe");
        request.setAttribute("yunhe",yunhe);
        return new ModelAndView("com/sdzk/buss/web/homePage/majorRisk");
    }
    /**
     *  重大风险 数据组装
     * */
    @RequestMapping(params = "majorRiskDatagrid")
    public void majorRiskDatagrid(MajorRiskEntityVO majorRiskEntityVO, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
        String yunhe = ResourceUtil.getConfigByName("yunhe");
        if(yunhe.equals("true")){
            String sql = "select r.risk_type riskType,address from t_b_risk_identification r LEFT JOIN t_b_address_info a on r.address_id = a.id where status='3' and is_del = '0' and risk_level = '1' and identifi_date <= now()  and (exp_date >= now() or exp_date is null)  and address_id is not null and address_id != '' and r.risk_type is not null and r.risk_type != '' GROUP BY address_id,r.risk_type  ";
            List<Map<String, Object>> list = systemService.findForJdbc(sql);
            dataGrid.setTotal(list.size());

            int currentPage = dataGrid.getPage();
            int pageSize = dataGrid.getRows();
            int endIndex = pageSize *currentPage;
            if(pageSize *currentPage > list.size()){
                endIndex = list.size();
            }
            List<Map<String, Object>> resultList = list.subList(pageSize *(currentPage -1), endIndex);

            dataGrid.setResults(resultList);
            TagUtil.datagrid(response, dataGrid);
        }else{
            CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
            cq.eq("riskLevel", "1");
//        cq.eq("isMajor", "1");
            cq.eq("status","3");
            cq.eq("isDel","0");
            cq.add();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            cq.add(Restrictions.sqlRestriction("this_.id in (select id from t_b_risk_identification where status='3' and is_del = '0' and identifi_date <= '"+sdf.format(new Date())+"'  and (exp_date >= '"+sdf.format(new Date())+"' or exp_date is null) )"));
            List<RiskIdentificationEntity> riskIdentificationEntityList = systemService.getListByCriteriaQuery(cq, false);
            List<MajorRiskEntityVO> majorRiskEntityVOList = new ArrayList<MajorRiskEntityVO>();

            for(RiskIdentificationEntity t : riskIdentificationEntityList){
                MajorRiskEntityVO vo = new MajorRiskEntityVO();
                vo.setRiskName(t.getRiskDesc());
                vo.setRiskLevel(t.getRiskLevel());

           /* String sql = "select id from t_b_hidden_danger_exam where risk_id='"+ t.getId() +"'";
            StringBuffer sb = new StringBuffer();
            List<Object> idList = new ArrayList<Object>();
            idList = systemService.findListbySql(sql);
            for (Object o : idList){
                sb.append(o.toString());
                if(StringUtil.isNotEmpty(o.toString())){
                    sb.append(",");
                }
            }

            vo.setRelHiddenDanger(idList.size());
            vo.setRelHiddenDangerIds(t.getId());*/
                vo.setId(t.getId());
                majorRiskEntityVOList.add(vo);
            }
            dataGrid.setTotal(majorRiskEntityVOList.size());

            int currentPage = dataGrid.getPage();
            int pageSize = dataGrid.getRows();
            int endIndex = pageSize *currentPage;
            if(pageSize *currentPage > majorRiskEntityVOList.size()){
                endIndex = majorRiskEntityVOList.size();
            }
            List<MajorRiskEntityVO> resultList = majorRiskEntityVOList.subList(pageSize *(currentPage -1), endIndex);

            dataGrid.setResults(resultList);
            TagUtil.datagrid(response, dataGrid);
        }

       /* CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class, dataGrid);
        cq.eq("isDelete", Constants.IS_DELETE_N);
//        cq.eq("isMajor", "1");
        cq.eq("yeRiskGrade","1");
        cq.eq("auditStatus",Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
        cq.add();
        List<TBDangerSourceEntity> tbDangerSourceEntityList = systemService.getListByCriteriaQuery(cq, false);
        List<MajorRiskEntityVO> majorRiskEntityVOList = new ArrayList<MajorRiskEntityVO>();

        for(TBDangerSourceEntity t : tbDangerSourceEntityList){
            MajorRiskEntityVO vo = new MajorRiskEntityVO();
            vo.setRiskName(t.getYePossiblyHazard());
            vo.setRiskLevel(t.getYeRiskGrade());

            String sql = "select id from t_b_hidden_danger_exam where danger_id='"+ t.getId() +"'";
            StringBuffer sb = new StringBuffer();
            List<Object> idList = new ArrayList<Object>();
            idList = systemService.findListbySql(sql);
            for (Object o : idList){
                sb.append(o.toString());
                if(StringUtil.isNotEmpty(o.toString())){
                    sb.append(",");
                }
            }

            vo.setRelHiddenDanger(idList.size());
            vo.setRelHiddenDangerIds(t.getId());
            vo.setId(t.getId());
            majorRiskEntityVOList.add(vo);
        }
        dataGrid.setTotal(majorRiskEntityVOList.size());

        int currentPage = dataGrid.getPage();
        int pageSize = dataGrid.getRows();
        int endIndex = pageSize *currentPage;
        if(pageSize *currentPage > majorRiskEntityVOList.size()){
            endIndex = majorRiskEntityVOList.size();
        }
        List<MajorRiskEntityVO> resultList = majorRiskEntityVOList.subList(pageSize *(currentPage -1), endIndex);

        dataGrid.setResults(resultList);
        TagUtil.datagrid(response, dataGrid);*/
    }
    /**
     *  重大风险关联隐患  跳转
     * */
    @RequestMapping(params = "majorRiskRelHiddenDanger")
    public ModelAndView majorRiskRelHiddenDanger(HttpServletRequest request, HttpServletResponse response){
        String ids = request.getParameter("ids");
        request.setAttribute("ids", ids);

        return new ModelAndView("com/sdzk/buss/web/homePage/majorRiskRelHiddenDangerList");
    }
    /**
     *  重大风险关联隐患 数据组装
     * */
    @RequestMapping(params = "majorRiskRelHiddenDangerDatagtid")
    @ResponseBody
    public void majorRiskRelHiddenDangerDatagtid(TBHiddenDangerHandleEntity tBHiddenDangerHandle,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
        String ids = request.getParameter("ids");
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class, dataGrid);

        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBHiddenDangerHandle, request.getParameterMap());
        cq.createAlias("hiddenDanger","hiddenDanger");
        try{
            cq.eq("hiddenDanger.riskId.id", ids);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null) {
            if (dataGrid.getResults().size() > 0) {
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
                                TSUser user = systemService.getEntity(TSUser.class, userid);
                                if (user != null) {
                                    if (names == "") {
                                        names = names + user.getRealName() + "-" + user.getUserName();
                                    } else {
                                        names = names + "," + user.getRealName() + "-" + user.getUserName();
                                    }
                                }else if (StringUtil.isNotEmpty(userid)){
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
     *  风险动态分级预警 跳转
     * */
    @RequestMapping(params = "riskDynamicClassification")
    public ModelAndView riskDynamicClassification(HttpServletRequest request, HttpServletResponse response){

        /*return new ModelAndView("com/sdzk/buss/web/homePage/riskDynamicClassification");*/
        /*return new ModelAndView("com/sdzk/buss/web/homePage/riskDynamicClassificationNew");*/
        return new ModelAndView("com/sdzk/buss/web/homePage/riskDynamicClassificationNew1");
    }

    /**
     *  风险动态分级预警 数据组装
     * */
    @RequestMapping(params = "riskDynamicClassficationData")
    @ResponseBody
    public JSONObject riskDynamicClassficationData(HttpServletRequest request, HttpServletResponse response){
        JSONObject jo = new JSONObject();
        /*

        String sql = "SELECT info.id,info.address,t.myLevel from t_b_address_info info LEFT JOIN (" +
                "select MIN(risk_level) myLevel, address_id from t_b_danger_address_rel group by address_id" +
                ") t ON info.id = t.address_id where info.is_delete='0' and info.isShow='Y' and info.isShowData = '1' and myLevel<='"+ showLevel +"'";

        List<Object[]> list = systemService.findListbySql(sql);*/
        StringBuffer addressScoreSb = new StringBuffer();
        addressScoreSb.append("SELECT\n" +
                "\tai.id,\n" +
                "\tai.address,\n" +
                "\t0 as myLevel\n" +
                "FROM\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\texam.address,\n" +
                "\t\t\texam.hidden_nature,\n" +
                "\t\t\tcount(1) num\n" +
                "\t\tFROM\n" +
                "\t\t\tt_b_hidden_danger_exam exam\n" +
                "\t\tLEFT JOIN t_b_hidden_danger_handle handle ON handle.hidden_danger_id = exam.id\n" +
                "\t\tWHERE\n" +
                "\t\t\thandle.handlel_status IN ('1', '4')\n" +
                "\t\tGROUP BY\n" +
                "\t\t\texam.address,\n" +
                "\t\t\texam.hidden_nature\n" +
                "\t) addressLevelInfo\n" +
                "LEFT JOIN t_b_risk_rule_score_manager rrsm ON addressLevelInfo.hidden_nature = rrsm.risk_type\n" +
                "LEFT JOIN t_b_address_info ai ON ai.id = addressLevelInfo.address\n" +
                "GROUP BY addressLevelInfo.address");
        List<Object[]> templist = systemService.findListbySql(addressScoreSb.toString());
        List<Object[]> list = new ArrayList<>();
        Map<String, Object> dynamicLevel = this.tBAddressInfoService.getDynamicLevel();
        for(int i=0;i< templist.size();i++){
            String addressId  = (String)templist.get(i)[0];
            String level = (String)dynamicLevel.get(addressId);
            if(StringUtil.isNotEmpty(level)){
                Integer newlevel = Integer.parseInt(level);
                if(newlevel<=4){
                    templist.get(i)[2]=level;
                    list.add(templist.get(i));
                }
            }
        }


        StringBuffer nodeList = new StringBuffer();
        StringBuffer linkList = new StringBuffer();

        nodeList.append("{category:0, name: '风险动态分级', value : 10, label: '风险动态分级'},");

        for(int i=0; i<list.size(); i++){
            nodeList.append("{category:").append(nullToFour(list.get(i)[2])).append(", id: '").append(list.get(i)[0].toString()).append("', name: '").append(list.get(i)[1].toString()).append("',value : ").append(nullToStr(list.get(i)[2])).append("}");
            linkList.append("{source : '风险动态分级', target : '").append(list.get(i)[1].toString()).append("', weight : ").append(changeValue(list.get(i)[2])).append("}");

            if(i != list.size()-1){
                nodeList.append(",");
                linkList.append(",");
            }
        }

        jo.put("nodeLists", "["+nodeList.toString()+"]");
        jo.put("linkLists", "["+linkList.toString()+"]");
        return jo;
    }


    /**
     *  风险动态分级预警 数据组装
     * */
    @RequestMapping(params = "riskDynamicClassficationDataNew")
    @ResponseBody
    public JSONObject riskDynamicClassficationDataNew(HttpServletRequest request, HttpServletResponse response){
        JSONObject jo = new JSONObject();
        Map<String, Object> dynamicLevel = this.tBAddressInfoService.getDynamicLevel();
        Map<Object,Integer> res=new HashMap<>();
        for (Map.Entry<String,Object> entry:dynamicLevel.entrySet()){
            if (res.containsKey(entry.getValue())){
                res.put(entry.getValue(),res.get(entry.getValue())+1);
            }else{
                res.put(entry.getValue(),1);
            }
        }
        res.remove(null);

        List<Object[]> list = new ArrayList<>();
        for (Map.Entry<Object,Integer> entry:res.entrySet()){
            Object[] temp = new Object[4];
            temp[0]=entry.getKey();
            if(entry.getKey().equals("1")){
                temp[1]="重大风险点:"+entry.getValue().toString();
            }else  if(entry.getKey().equals("2")){
                temp[1]="较大风险点:"+entry.getValue().toString();
            }else  if(entry.getKey().equals("3")){
                temp[1]="一般风险点:"+entry.getValue().toString();
            }else  if(entry.getKey().equals("4")){
                temp[1]="低风险点:"+entry.getValue().toString();
            }
            temp[2]=entry.getKey();
            list.add(temp);
        }

        StringBuffer nodeList = new StringBuffer();
        StringBuffer linkList = new StringBuffer();

        nodeList.append("{category:0, name: '风险动态分级', value : 10, label: '风险动态分级'},");

        for(int i=0; i<list.size(); i++){
            nodeList.append("{category:").append(nullToFour(list.get(i)[2])).append(", level: '").append(list.get(i)[0].toString()).append("', name: '").append(list.get(i)[1].toString()).append("',value : ").append(nullToStr(list.get(i)[2])).append("}");
            linkList.append("{source : '风险动态分级', target : '").append(list.get(i)[1].toString()).append("', weight : ").append(changeValue(list.get(i)[2])).append("}");

            if(i != list.size()-1){
                nodeList.append(",");
                linkList.append(",");
            }
        }


        jo.put("nodeLists", "["+nodeList.toString()+"]");
        jo.put("linkLists", "["+linkList.toString()+"]");
        return jo;
    }


    @RequestMapping(params = "riskDynamicClassficationDataNew1")
    @ResponseBody
    public JSONObject riskDynamicClassficationDataNew1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String aNum = "";
        String bNum = "";
        String cNum = "";
        String dNum="";
        JSONObject jo = new JSONObject();
        Map<String, Object> dynamicLevel = this.tBAddressInfoService.getDynamicLevel();
        Map<Object,Integer> res=new HashMap<>();
        for (Map.Entry<String,Object> entry:dynamicLevel.entrySet()){
            if (res.containsKey(entry.getValue())){
                res.put(entry.getValue(),res.get(entry.getValue())+1);
            }else{
                res.put(entry.getValue(),1);
            }
        }
        res.remove(null);
        if(res.get("1")!=null){
            aNum = String.valueOf(res.get("1"));
        }else{
            aNum = "0";
        }
        if(res.get("2")!=null){
            bNum = String.valueOf(res.get("2"));
        }else{
            bNum = "0";
        }
        if(res.get("3")!=null){
            cNum = String.valueOf(res.get("3"));
        }else{
            cNum = "0";
        }
        if(res.get("4")!=null){
            dNum = String.valueOf(res.get("4"));
        }else{
            dNum = "0";
        }


        jo.put("aNum", aNum);
        jo.put("bNum", bNum);
        jo.put("cNum", cNum);
        jo.put("dNum", dNum);
        return jo;
    }
    //转换value的值
    private String changeValue(Object o){
        o = nullToStr(o);
        String v = o.toString();
        String ret = "";
        switch (v){
            case "0":
                ret = "0"; break;
            case "1":
                ret = "4"; break;
            case "2":
                ret = "3"; break;
            case "3":
                ret = "2"; break;
            case "4":
                ret = "1"; break;
        }
        return  ret;
    }
    //转换空值
    private String nullToStr(Object o){
        String ret = "";
        if (o==null){
            ret = "0";
        }else{
            ret = o.toString();
            if(StringUtil.isEmpty(o.toString())){
                ret = "0";
            }
        }
        return ret;
    }
    //如果风险没有等级，默认显示第四级
    private String nullToFour(Object o){
        String ret = "";
        if (o==null){
            ret = "4";
        }else{
            ret = o.toString();
            if(StringUtil.isEmpty(o.toString())){
                ret = "4";
            }
        }
        return ret;
    }

    /**
     * 隐患部门分布 跳转
     * */
    @RequestMapping(params = "hiddenDangerByGroup")
    public ModelAndView hiddenDangerByGroup(HttpServletRequest request, HttpServletResponse response){
        String flag= request.getParameter("flag");
        request.setAttribute("flag",flag);
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if(StringUtil.isEmpty(startDate)){
            startDate = DateUtils.getDate("yyyy-MM") + "-01";
        }
        if(StringUtil.isEmpty(endDate)){
            endDate = DateUtils.getDate("yyyy-MM-dd");
        }
        request.setAttribute("startDate",startDate);
        request.setAttribute("endDate",endDate);
        return new ModelAndView("com/sdzk/buss/web/homePage/hiddenDangerByGroup");
    }

    /**
     *  隐患部门分布 数据组装
     * */
    @RequestMapping(params = "hiddenDangerByGroupData")
    @ResponseBody
    public JSONObject hiddenDangerByGroupData(HttpServletRequest request, HttpServletResponse response){
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        JSONObject jo = new JSONObject();
        StringBuffer departNameList = new StringBuffer();
        StringBuffer numList = new StringBuffer();
        StringBuffer closeNumList = new StringBuffer();
        StringBuffer notClosedNumList = new StringBuffer();
        String flag=request.getParameter("flag");//判断是否是全屏
        /*//获取所有不重复的部门
        String sql = "select DISTINCT duty_unit from t_b_hidden_danger_exam hde left join t_b_address_info address on hde.address = address.id left join t_s_depart depart on hde.duty_unit = depart.id where duty_unit is not null  and address.isShowdata = '1' and depart.delete_flag = '0' ";
        List<Object> departList = systemService.findListbySql(sql);
        List<HiddenDangerByGroupVO> voList = new ArrayList<HiddenDangerByGroupVO>();

        for(int i=0; i<departList.size(); i++){
            //获得指定部门的全部隐患
            //只要数据没有异常的话，这个是不会出现分母为0的情况的，这里暂时不做验证处理
            String totalSql = "select count(id) count from(\n" +
                    "SELECT\n" +
                    "\thandle.id,handle.hidden_danger_id,exam.exam_date,handle.handlel_status,exam.duty_unit\n" +
                    "FROM\n" +
                    "\tt_b_hidden_danger_handle handle\n" +
                    "LEFT JOIN t_b_hidden_danger_exam exam ON handle.hidden_danger_id = exam.id where duty_unit='"+departList.get(i).toString()+"'\n" + " and handle.handlel_status<>'00'  ";
            String closeSql = "select count(id) count from(\n" +
                    "SELECT\n" +
                    "\thandle.id,handle.hidden_danger_id,exam.exam_date,handle.handlel_status,exam.duty_unit\n" +
                    "FROM\n" +
                    "\tt_b_hidden_danger_handle handle\n" +
                    "LEFT JOIN t_b_hidden_danger_exam exam ON handle.hidden_danger_id = exam.id where duty_unit='"+departList.get(i).toString()+"' and handlel_status='5'  \n";
            if(StringUtil.isEmpty(startDate) && StringUtil.isEmpty(endDate)){
                totalSql += " and exam.exam_date like '" + DateUtils.getDate("yyyy-MM") + "%'";
                closeSql += " and exam.exam_date like '" + DateUtils.getDate("yyyy-MM") + "%'";
            }
            if(StringUtils.isNotBlank(startDate)){
                totalSql += " and exam.exam_date>='"+startDate+"' ";
                closeSql += " and exam.exam_date>='"+startDate+"' ";
            }
            if(StringUtils.isNotBlank(endDate)){
                totalSql += " and exam.exam_date<='"+endDate+"' ";
                closeSql += " and exam.exam_date<='"+endDate+"' ";

            }

            totalSql += ")as t GROUP BY t.duty_unit";
            closeSql += ")as t GROUP BY t.duty_unit";
//            String notCloseSql = "";      总数减去已闭合的就是未闭合的
            List<Object> totalList = systemService.findListbySql(totalSql);
            List<Object> closeList = systemService.findListbySql(closeSql);
//            List<Object[]> notCloseList = systemService.findListbySql(notCloseSql);       总数减去已闭合的就是未闭合的

            HiddenDangerByGroupVO vo = new HiddenDangerByGroupVO();

            vo.setId(departList.get(i).toString());
            TSDepart tsDepart = systemService.getEntity(TSDepart.class, departList.get(i).toString());
            if(tsDepart!=null){
                vo.setName(tsDepart.getDepartname());
            }
            if(totalList!=null && totalList.size()>0){
                vo.setTotal(Double.parseDouble(totalList.get(0).toString()));
            }else {
                vo.setTotal(0.0);
            }
            if(closeList!=null && closeList.size()>0){
                vo.setClose(Double.parseDouble(closeList.get(0).toString()));
            }else {
                vo.setClose(0.0);
            }
            vo.setNotClose(vo.getTotal()-vo.getClose());
            //过滤分母为0的情况
            if(vo.getTotal()==0.0){
                vo.setPercent(vo.getNotClose()/vo.getTotal());
            }else{
                vo.setPercent(0.0);
            }

            voList.add(vo);
        }*/

        List<HiddenDangerByGroupVO> voList = new ArrayList<HiddenDangerByGroupVO>();
        //获得指定部门的全部隐患
        //只要数据没有异常的话，这个是不会出现分母为0的情况的，这里暂时不做验证处理
        String totalSql = "select count(id) totalcount, 0 closecount, t.duty_unit dutyUnitId from(\n" +
                "SELECT\n" +
                "\thandle.id,handle.hidden_danger_id,exam.exam_date,handle.handlel_status,exam.duty_unit\n" +
                "FROM\n" +
                "\tt_b_hidden_danger_handle handle\n" +
                "LEFT JOIN t_b_hidden_danger_exam exam ON handle.hidden_danger_id = exam.id where duty_unit in (select id from t_s_depart where delete_flag = '0') and handle.handlel_status<>'00'  ";
        String closeSql = "select 0 totalcount,count(id) closecount ,t.duty_unit dutyUnitId from(\n" +
                "SELECT\n" +
                "\thandle.id,handle.hidden_danger_id,exam.exam_date,handle.handlel_status,exam.duty_unit\n" +
                "FROM\n" +
                "\tt_b_hidden_danger_handle handle\n" +
                "LEFT JOIN t_b_hidden_danger_exam exam ON handle.hidden_danger_id = exam.id where duty_unit in (select id from t_s_depart where delete_flag = '0')  and handlel_status='5'  \n";
        if(StringUtil.isEmpty(startDate) && StringUtil.isEmpty(endDate)){
            totalSql += " and exam.exam_date like '" + DateUtils.getDate("yyyy-MM") + "%'";
            closeSql += " and exam.exam_date like '" + DateUtils.getDate("yyyy-MM") + "%'";
        }
        if(StringUtils.isNotBlank(startDate)){
            totalSql += " and exam.exam_date>='"+startDate+"' ";
            closeSql += " and exam.exam_date>='"+startDate+"' ";
        }
        if(StringUtils.isNotBlank(endDate)){
            totalSql += " and exam.exam_date<='"+endDate+"' ";
            closeSql += " and exam.exam_date<='"+endDate+"' ";

        }

        totalSql += ")as t GROUP BY t.duty_unit";
        closeSql += ")as t GROUP BY t.duty_unit";
        String hiddenNumSql = "SELECT temp.dutyUnitId, sum(totalcount) totalcount, sum(closecount) closecount FROM ("+totalSql+" union all "+closeSql+" ) temp GROUP BY temp.dutyUnitId ORDER BY totalcount desc";
        List<Map<String,Object>> hiddenNumList = systemService.findForJdbc(hiddenNumSql);


        for(Map<String,Object> map :hiddenNumList){
            HiddenDangerByGroupVO vo = new HiddenDangerByGroupVO();
            vo.setId(String.valueOf(map.get("dutyUnitId")));
            TSDepart tsDepart = systemService.getEntity(TSDepart.class,String.valueOf(map.get("dutyUnitId")));
            if(tsDepart!=null){
                vo.setName(tsDepart.getDepartname());
            }
            vo.setTotal(Double.parseDouble(String.valueOf(map.get("totalcount"))));
            vo.setClose(Double.parseDouble(String.valueOf(map.get("closecount"))));
            vo.setNotClose(vo.getTotal()-vo.getClose());
            voList.add(vo);
        }
        departNameList.append("[");
        numList.append("[");
        closeNumList.append("[");
        notClosedNumList.append("[");
        /**
         *如果flag存在值就是全屏显示
         * 为空就只显示六个
         **/
        if(StringUtils.isNotBlank(flag)) {
            for (int a = 0; a < voList.size(); a++) {
                departNameList.append("'").append(voList.get(a).getName()).append("'");
                numList.append(voList.get(a).getTotal());
                closeNumList.append(voList.get(a).getClose());
                notClosedNumList.append(voList.get(a).getNotClose());
                if (a < voList.size() - 1) {
                    departNameList.append(",");
                    numList.append(",");
                    closeNumList.append(",");
                    notClosedNumList.append(",");
                }
            }
        }else {
            if (voList.size() > 6) {
                for (int a = 0; a < 6; a++) {
                    departNameList.append("'").append(voList.get(a).getName()).append("'");
                    numList.append(voList.get(a).getTotal());
                    closeNumList.append(voList.get(a).getClose());
                    notClosedNumList.append(voList.get(a).getNotClose());
                    if (a < 5) {
                        departNameList.append(",");
                        numList.append(",");
                        closeNumList.append(",");
                        notClosedNumList.append(",");
                    }
                }
            } else {
                for (int a = 0; a < voList.size(); a++) {
                    departNameList.append("'").append(voList.get(a).getName()).append("'");
                    numList.append(voList.get(a).getTotal());
                    closeNumList.append(voList.get(a).getClose());
                    notClosedNumList.append(voList.get(a).getNotClose());
                    if (a < voList.size() - 1) {
                        departNameList.append(",");
                        numList.append(",");
                        closeNumList.append(",");
                        notClosedNumList.append(",");
                    }
                }
            }
        }
        departNameList.append("]");
        numList.append("]");
        closeNumList.append("]");
        notClosedNumList.append("]");

        jo.put("departList", departNameList.toString());
        jo.put("numList", numList.toString());
        jo.put("closeNumList", closeNumList.toString());
        jo.put("notClosedNumList", notClosedNumList.toString());

        return jo;
    }


    /**
     *  隐患专业分布 跳转 修改为类型
     * */
    @RequestMapping(params = "hiddenDangerByProfession")
    public ModelAndView hiddenDangerByProfession(HttpServletRequest request, HttpServletResponse response){
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        if(StringUtil.isEmpty(startDate)){
            startDate = DateUtils.getDate("yyyy-MM") + "-01";
        }
        if(StringUtil.isEmpty(endDate)){
            endDate = DateUtils.getDate("yyyy-MM-dd");
        }
        request.setAttribute("startDate",startDate);
        request.setAttribute("endDate",endDate);
        return new ModelAndView("com/sdzk/buss/web/homePage/hiddenDangerByProfession");
    }

    /**
     *  隐患专业分布 数据组装
     * */
    @RequestMapping(params = "hiddenDangerByProfessionData")
    @ResponseBody
    public JSONObject hiddenDangerByProfessionData(HttpServletRequest request, HttpServletResponse response){
        JSONObject jo = new JSONObject();

        //查询出来所有关联风险的隐患关联风险的id      排除为关联风险的，即danger_id为null或者为空的情况，因为那个无法统计专业
        //String sql = "select count(id) count,danger_id from t_b_hidden_danger_exam where danger_id IS NOT NULL and danger_id<>''  GROUP BY danger_id ORDER BY count DESC";
        //修改为类型
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String sql = "SELECT count(id) count, risk_type FROM t_b_hidden_danger_exam WHERE exam_date >= '"+startDate+"' and exam_date <= '"+endDate+"' and risk_type IS NOT NULL AND risk_type <> '' GROUP BY risk_type ORDER BY count DESC";
        List<Object[]> riskList = systemService.findListbySql(sql);
        List<HiddenDangerByProfessionVO> voList = new ArrayList<HiddenDangerByProfessionVO>();

        if(riskList!=null && riskList.size()>0){
            for(int i=0; i<riskList.size(); i++){
                HiddenDangerByProfessionVO vo = new HiddenDangerByProfessionVO();

                /*vo.setRelRiskId(riskList.get(i)[1].toString());
                vo.setNumber(Double.parseDouble(riskList.get(i)[0].toString()));

                TBDangerSourceEntity t = systemService.get(TBDangerSourceEntity.class, riskList.get(i)[1].toString());
                if(t!=null){
                    vo.setProfession(t.getYeProfession());
                    vo.setProfessionName(DicUtil.getTypeNameByCode("proCate_gradeControl", t.getYeProfession()));
                    voList.add(vo);
                }*/
                vo.setNumber(Double.parseDouble(riskList.get(i)[0].toString()));
                vo.setProfession(riskList.get(i)[1].toString());
                vo.setProfessionName(DicUtil.getTypeNameByCode("risk_type", riskList.get(i)[1].toString()));
                voList.add(vo);

            }
        }

        List<HiddenDangerByProfessionVO> resultList = new ArrayList<HiddenDangerByProfessionVO>();
        String[] yeProfessionList = new String[voList.size()];
        int step = 0;
        for(int i=0; i<voList.size(); i++){
            int flag = 0;
            for(int j=0; j<yeProfessionList.length; j++){
                if (voList.get(i).getProfession().equals(yeProfessionList[j])){
                    flag = 1;
                }else{
                    continue;
                }
            }
            if(0==flag){
                yeProfessionList[step] = voList.get(i).getProfession();
                step ++;
            }
        }

        for (int m=0; m<step; m++){
            HiddenDangerByProfessionVO temp = new HiddenDangerByProfessionVO();
            temp.setNumber(0.0);
            temp.setProfession(yeProfessionList[m]);
            //temp.setProfessionName(DicUtil.getTypeNameByCode("proCate_gradeControl", yeProfessionList[m]));
            temp.setProfessionName(DicUtil.getTypeNameByCode("risk_type", yeProfessionList[m]));
            for (HiddenDangerByProfessionVO v : voList){
                if (yeProfessionList[m].equals(v.getProfession())){
                    temp.setNumber(temp.getNumber() + v.getNumber());
                }
            }
            resultList.add(temp);
        }

        //voList数组进行排序
        Collections.sort(resultList, new Comparator<HiddenDangerByProfessionVO>() {
            @Override
            public int compare(HiddenDangerByProfessionVO o1, HiddenDangerByProfessionVO o2) {
                if(o1.getNumber() < o2.getNumber()){
                    return 0;
                }else{
                    return -1;
                }
            }
        });

//        String allSql = "select count(id) count from t_b_hidden_danger_exam where danger_id IS NOT NULL and danger_id<>''";
//        List<Object> all = systemService.findListbySql(allSql);
//        Double allHidden = Double.parseDouble(all.get(0).toString());

        StringBuffer indicatorList = new StringBuffer();
        StringBuffer valueList = new StringBuffer();
        indicatorList.append("[");
        valueList.append("[");

        if (resultList.size()<6){
            for (int c=0; c<resultList.size(); c++){
                indicatorList.append("{ text: '").append(resultList.get(c).getProfessionName()).append("', max:").append(resultList.get(0).getNumber()).append("},");
                valueList.append(resultList.get(c).getNumber());
                if(c!=resultList.size()-1){
                    valueList.append(",");
                }
            }
        }else{
            for (int c=0; c<6; c++){
                indicatorList.append("{ text: '").append(resultList.get(c).getProfessionName()).append("', max:").append(resultList.get(0).getNumber()).append("},");
                valueList.append(resultList.get(c).getNumber());
                if(c!=5){
                    valueList.append(",");
                }
            }
        }

        indicatorList.append("]");
        valueList.append("]");

        jo.put("indicatorList", indicatorList.toString());
        jo.put("valueList", valueList.toString());
        return jo;
    }

    /**
     * 风险管控预警图
     *
     * @return
     */
    @RequestMapping(params = "fxgkyjt")
    public ModelAndView fxgkyjt(HttpServletRequest request) {

        CriteriaQuery cq = new CriteriaQuery(TBLayerEntity.class);
        cq.eq("isShow",Constants.IS_SHOW_Y);
        cq.add();
        List<TBLayerEntity> layerList = this.systemService.getListByCriteriaQuery(cq, false);
        request.setAttribute("layerList",layerList);
        if(null!=layerList&&layerList.size()>0){
            request.setAttribute("initLayerCode",layerList.get(0).getId());
        }
        return new ModelAndView("com/sdzk/buss/web/homePage/undergroundSafetyRiskAlert_dynamic");
    }

}
