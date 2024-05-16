package com.sdzk.buss.web.hiddendanger.controller;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.investigateplan.entity.TBInvestigatePlanEntity;
import com.sdzk.buss.web.investigateplan.entity.TBInvestigatePlanRelEntity;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.StringType;
import com.sun.star.i18n.*;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;




import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.decisionAnalyse.entity.MineYearRiskTrendEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.decisionAnalyse.entity.DecisionAnalyseVO;
import com.sdzk.buss.web.decisionAnalyse.service.TBDecisionAnalyseServiceI;
import com.sdzk.buss.web.majorhiddendanger.entity.TBMajorHiddenDangerEntity;
import com.sdzk.buss.web.specialevaluation.entity.TBSpecialEvaluationEntity;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.common.model.json.DataGridReturn;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import org.jeecgframework.web.system.pojo.base.TSDepart;

/**
 * Created by Lenovo on 17-8-17.
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBHiddenDangerCheckPlanCountController")
public class TBHiddenDangerCheckPlanCountController {
    @Autowired
    private SystemService systemService;

    @RequestMapping(params = "checkPlanCount")
    public ModelAndView majorHDAndRiskReport(HttpServletRequest request) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        request.setAttribute("yearMonth", sdf.format(date));
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerCheckPlanSta");
    }

    @RequestMapping(params = "checkPlanCountQuery")
    @ResponseBody
    public AjaxJson majorHDAndRiskReportQuery(String beginMonth, String endMonth,HttpServletRequest request) {
        AjaxJson j = new AjaxJson();

        String planCount = "";
        String riskCount = "";
        String hiddenCount = "";

        //排查计划统计
        //月排查
        String planMonthCountQuery = "0";
        String planXunCountQuery = "0";
        String planWeekCountQuery = "0";

        String planMonthCountSql = "select count(*) count from t_b_investigate_plan where INVESTIGATE_TYPE = 1 and id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )";
        String planXunCountSql = "select count(*) count from t_b_investigate_plan where INVESTIGATE_TYPE = 2 and id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )";
        String planWeekCountSql = "select count(*) count from t_b_investigate_plan where INVESTIGATE_TYPE = 3 and id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )";
        if(StringUtil.isNotEmpty(beginMonth)){
            planMonthCountSql = planMonthCountSql + " and DATE_FORMAT(START_TIME,'%Y-%m')>='" + beginMonth + "'";
            planXunCountSql = planXunCountSql + " and DATE_FORMAT(START_TIME,'%Y-%m')>='" + beginMonth + "'";
            planWeekCountSql = planWeekCountSql + " and DATE_FORMAT(START_TIME,'%Y-%m')>='" + beginMonth + "'";
        }
        if(StringUtil.isNotEmpty(endMonth)){
            planMonthCountSql = planMonthCountSql + " and DATE_FORMAT(START_TIME,'%Y-%m')<='" + endMonth + "'";
            planXunCountSql = planXunCountSql + " and DATE_FORMAT(START_TIME,'%Y-%m')<='" + endMonth + "'";
            planWeekCountSql = planWeekCountSql + " and DATE_FORMAT(START_TIME,'%Y-%m')<='" + endMonth + "'";
        }

        List<Map<String,Object>> listMonth = systemService.findForJdbc(planMonthCountSql);
        List<Map<String,Object>> listXun = systemService.findForJdbc(planXunCountSql);
        List<Map<String,Object>> listWeek = systemService.findForJdbc(planWeekCountSql);

        if(null!=listMonth && !listMonth.isEmpty()){
            Map<String,Object> data = listMonth.get(0);
            planMonthCountQuery = String.valueOf(data.get("count"));
        }
        if(null!=listXun && !listXun.isEmpty()){
            Map<String,Object> data = listXun.get(0);
            planXunCountQuery = String.valueOf(data.get("count"));
        }
        if(null!=listWeek && !listWeek.isEmpty()){
            Map<String,Object> data = listWeek.get(0);
            planWeekCountQuery = String.valueOf(data.get("count"));
        }

        planCount = planCount + planMonthCountQuery + "," + planXunCountQuery + "," + planWeekCountQuery;




        //排查风险数量统计
        String riskMonthCountQuery = "0";
        String riskXunCountQuery = "0";
        String riskWeekCountQuery = "0";


        List<String> listMonthRiskId = new ArrayList<String>();
        List<String> listXunRiskId = new ArrayList<String>();
        List<String> listWeekRiskId = new ArrayList<String>();


        //查询月
        CriteriaQuery cq = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cq.eq("investigateType","1");
            cq.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cq.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cq.le("startTime",dateend);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        List<TBInvestigatePlanEntity> listMonthRisk = systemService.getListByCriteriaQuery(cq,false);

        if(listMonthRisk != null && !listMonthRisk.isEmpty()){
            for(TBInvestigatePlanEntity temp : listMonthRisk){
                listMonthRiskId.add(temp.getId());
            }
        }
        cq = new CriteriaQuery(TBInvestigatePlanRelEntity.class);
        try{
            if(!listMonthRiskId.isEmpty()){
                cq.in("planId", listMonthRiskId.toArray());
                cq.eq("relType","1");
                cq.add();
                List<TBInvestigatePlanRelEntity> im = systemService.getListByCriteriaQuery(cq, false);
                if(im != null && !im.isEmpty()){
                    riskMonthCountQuery =String.valueOf(im.size());
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        //查询旬
        CriteriaQuery cx = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cx.eq("investigateType","2");
            cx.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cx.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cx.le("startTime", dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cx.add();
        List<TBInvestigatePlanEntity> listXunRisk = systemService.getListByCriteriaQuery(cx,false);

        if(listXunRisk != null && !listXunRisk.isEmpty()){
            for(TBInvestigatePlanEntity temp : listXunRisk){
                listXunRiskId.add(temp.getId());
            }
        }
        cx = new CriteriaQuery(TBInvestigatePlanRelEntity.class);
        try{
            if(! listXunRiskId.isEmpty()){
                cx.in("planId", listXunRiskId.toArray());
                cx.eq("relType","1");
                cx.add();

                List<TBInvestigatePlanRelEntity> ix = systemService.getListByCriteriaQuery(cx, false);
                if(ix != null && !ix.isEmpty()){
                    riskXunCountQuery = String.valueOf(ix.size());
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        //查询周
        CriteriaQuery cw = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cw.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            cw.eq("investigateType","3");
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cw.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cw.le("startTime", dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cw.add();
        List<TBInvestigatePlanEntity> listWeekRisk = systemService.getListByCriteriaQuery(cw,false);

        if(listWeekRisk.size()>0){
            for(TBInvestigatePlanEntity temp : listWeekRisk){
                listWeekRiskId.add(temp.getId());
            }
        }
        cw = new CriteriaQuery(TBInvestigatePlanRelEntity.class);
        try{
            if(listWeekRiskId.size()>0){
                cw.in("planId", listWeekRiskId.toArray());
                cw.eq("relType","1");
                cw.add();

                List<TBInvestigatePlanRelEntity> iw = systemService.getListByCriteriaQuery(cw, false);
                if(iw != null && !iw.isEmpty()){
                    riskWeekCountQuery = String.valueOf(iw.size());
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        riskCount = riskCount +  riskMonthCountQuery + "," + riskXunCountQuery + "," +riskWeekCountQuery;










        //关联隐患数量统计
        String hiddenMonthCountQuery = "0";
        String hiddenXunCountQuery = "0";
        String hiddenWeekCountQuery = "0";


        List<String> listMonthHiddenId = new ArrayList<String>();
        List<String> listXunHiddenId = new ArrayList<String>();
        List<String> listWeekHiddenId = new ArrayList<String>();


        //查询月
        CriteriaQuery cqh = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cqh.eq("investigateType","1");
            cqh.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cqh.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cqh.le("startTime",dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cqh.add();
        List<TBInvestigatePlanEntity> listMonthHidden = systemService.getListByCriteriaQuery(cqh,false);

        if(listMonthHidden != null && !listMonthHidden.isEmpty()){
            for(TBInvestigatePlanEntity tbipe : listMonthHidden){
                listMonthHiddenId.add(tbipe.getId());
            }
        }
        cqh = new CriteriaQuery(TBInvestigatePlanRelEntity.class);
        try{
            if(!listMonthHiddenId.isEmpty()){
                cqh.eq("relType","3");
                cqh.in("planId", listMonthHiddenId.toArray());

                cqh.add();
                List<TBInvestigatePlanRelEntity> im = systemService.getListByCriteriaQuery(cqh, false);
                if(im != null && !im.isEmpty()){
                    hiddenMonthCountQuery = String.valueOf(im.size());
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        //查询旬
        CriteriaQuery cxh = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cxh.eq("investigateType","2");
            cxh.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cxh.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cxh.le("startTime",dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cxh.add();
        List<TBInvestigatePlanEntity> listXunHidden = systemService.getListByCriteriaQuery(cxh,false);

        if(listXunHidden != null && !listXunHidden.isEmpty()){
            for(TBInvestigatePlanEntity tbipex : listXunHidden){
                listXunHiddenId.add(tbipex.getId());
            }
        }
        cxh = new CriteriaQuery(TBInvestigatePlanRelEntity.class);
        try{
            if(! listXunHiddenId.isEmpty()){
                cxh.in("planId", listXunHiddenId.toArray());
                cxh.eq("relType","3");
                cxh.add();

                List<TBInvestigatePlanRelEntity> ix = systemService.getListByCriteriaQuery(cxh, false);
                if(ix != null && !ix.isEmpty()){
                   hiddenXunCountQuery = String.valueOf(ix.size());
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }


        //查询周
        CriteriaQuery cwh = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cwh.eq("investigateType","3");
            cwh.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cwh.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cwh.le("startTime",dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cwh.add();
        List<TBInvestigatePlanEntity> listWeekHidden = systemService.getListByCriteriaQuery(cwh,false);

        if(listWeekHidden.size()>0){
            for(TBInvestigatePlanEntity temp : listWeekHidden){
                listWeekHiddenId.add(temp.getId());
            }
        }
        cwh = new CriteriaQuery(TBInvestigatePlanRelEntity.class);
        try{
            if(listWeekHiddenId.size()>0){
                cwh.in("planId", listWeekHiddenId.toArray());
                cwh.eq("relType","3");
                cwh.add();

                List<TBInvestigatePlanRelEntity> iw = systemService.getListByCriteriaQuery(cwh, false);
                if(iw != null && !iw.isEmpty()){
                    hiddenWeekCountQuery = String.valueOf(iw.size());
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        hiddenCount = hiddenCount +  hiddenMonthCountQuery + "," + hiddenXunCountQuery + "," + hiddenWeekCountQuery;







        //排查计划完成情况统计

        //待受理
        String planForAcceptCount = "";

        String planForAcceptMonth = "0";
        String planForAcceptXun = "0";
        String planForAcceptWeek = "0";

        //月待受理
        CriteriaQuery cqpam = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cqpam.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            cqpam.eq("investigateType","1");
            cqpam.eq("status","3");
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cqpam.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cqpam.le("startTime",dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cqpam.add();
        List<TBInvestigatePlanEntity> listMonthPlanForAccept = systemService.getListByCriteriaQuery(cqpam,false);

        if(listMonthPlanForAccept != null && !listMonthPlanForAccept.isEmpty()){
            planForAcceptMonth = String.valueOf(listMonthPlanForAccept.size());
        }

        //旬待受理
        CriteriaQuery cqpax = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cqpax.eq("investigateType","2");
            cqpax.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            cqpax.eq("status","3");
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cqpax.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cqpax.le("startTime",dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cqpax.add();
        List<TBInvestigatePlanEntity> listXunPlanForAccept = systemService.getListByCriteriaQuery(cqpax,false);

        if(listXunPlanForAccept != null && !listXunPlanForAccept.isEmpty()){
            planForAcceptXun = String.valueOf(listXunPlanForAccept.size());
        }

        //周待受理
        CriteriaQuery cqpaw = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cqpaw.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            cqpaw.eq("investigateType","3");
            cqpaw.eq("status","3");
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cqpaw.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cqpaw.le("startTime",dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cqpaw.add();
        List<TBInvestigatePlanEntity> listWeekPlanForAccept = systemService.getListByCriteriaQuery(cqpaw,false);

        if(listWeekPlanForAccept != null && !listWeekPlanForAccept.isEmpty()){
            planForAcceptWeek = String.valueOf(listWeekPlanForAccept.size());
        }
        planForAcceptCount = planForAcceptCount + planForAcceptMonth + "," + planForAcceptXun + "," + planForAcceptWeek;



        //待回复
        String planForReplyCount = "";

        String planForReplyMonth = "0";
        String planForReplyXun = "0";
        String planForReplyWeek = "0";

        //月待回复
        CriteriaQuery cqprm = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cqprm.eq("investigateType","1");
            cqprm.eq("status","4");
            cqprm.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cqprm.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cqprm.le("startTime",dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cqprm.add();
        List<TBInvestigatePlanEntity> listMonthPlanForReply = systemService.getListByCriteriaQuery(cqprm,false);

        if(listMonthPlanForReply != null && !listMonthPlanForReply.isEmpty()){
            planForReplyMonth = String.valueOf(listMonthPlanForReply.size());
        }

        //旬待回复
        CriteriaQuery cqprx = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cqprx.eq("investigateType","2");
            cqprx.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            cqprx.eq("status","4");
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cqprx.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cqprx.le("startTime",dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cqprx.add();
        List<TBInvestigatePlanEntity> listXunPlanForReply = systemService.getListByCriteriaQuery(cqprx,false);

        if(listXunPlanForReply != null && !listXunPlanForReply.isEmpty()){
            planForReplyXun = String.valueOf(listXunPlanForReply.size());
        }

        //周待回复
        CriteriaQuery cqprw = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cqprw.eq("investigateType","3");
            cqprw.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            cqprw.eq("status","4");
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cqprw.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cqprw.le("startTime",dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cqprw.add();
        List<TBInvestigatePlanEntity> listWeekPlanForReplay = systemService.getListByCriteriaQuery(cqprw,false);

        if(listWeekPlanForReplay != null && !listWeekPlanForReplay.isEmpty()){
            planForReplyWeek = String.valueOf(listWeekPlanForReplay.size());
        }
        planForReplyCount = planForReplyCount + planForReplyMonth + "," + planForReplyXun + "," + planForReplyWeek;


        //已回复
        String planYetReplyCount = "";

        String planYetReplyMonth = "0";
        String planYetReplyXun = "0";
        String planYetReplyWeek = "0";

        //月已回复
        CriteriaQuery cqpyrm = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cqpyrm.eq("investigateType","1");
            cqpyrm.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            cqpyrm.eq("status","5");
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cqpyrm.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cqpyrm.le("startTime",dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cqpyrm.add();
        List<TBInvestigatePlanEntity> listMonthPlanYetReply = systemService.getListByCriteriaQuery(cqpyrm,false);

        if(listMonthPlanYetReply != null && !listMonthPlanYetReply.isEmpty()){
            planYetReplyMonth = String.valueOf(listMonthPlanYetReply.size());
        }

        //旬已回复
        CriteriaQuery cqpyrx = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cqpyrx.eq("investigateType","2");
            cqpyrx.eq("status","5");
            cqpyrx.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cqpyrx.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cqpyrx.le("startTime",dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cqpyrx.add();
        List<TBInvestigatePlanEntity> listXunPlanYetReply = systemService.getListByCriteriaQuery(cqpyrx,false);

        if(listXunPlanYetReply != null && !listXunPlanYetReply.isEmpty()){
            planYetReplyXun = String.valueOf(listXunPlanYetReply.size());
        }

        //周已回复
        CriteriaQuery cqpyrw = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cqpyrw.eq("investigateType","3");
            cqpyrw.eq("status","5");
            cqpyrw.add(Restrictions.sqlRestriction(" this_.id not in (select t.plan_id from t_b_investigate_plan_rel t where t.obj_id in ( select address.id from t_b_address_info address where address.isshowdata = '0') )"));
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cqpyrw.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cqpyrw.le("startTime",dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cqpyrw.add();
        List<TBInvestigatePlanEntity> listWeekPlanYetReplay = systemService.getListByCriteriaQuery(cqpyrw,false);

        if(listWeekPlanYetReplay != null && !listWeekPlanYetReplay.isEmpty()){
            planYetReplyWeek = String.valueOf(listWeekPlanYetReplay.size());
        }
        planYetReplyCount = planYetReplyCount + planYetReplyMonth + "," + planYetReplyXun + "," + planYetReplyWeek;









        Map<String,String> retMap = new HashMap<>();
        retMap.put("planCount",planCount);
        retMap.put("riskCount",riskCount);
        retMap.put("hiddenCount",hiddenCount);

        retMap.put("planForAcceptCount",planForAcceptCount);
        retMap.put("planForReplyCount",planForReplyCount);
        retMap.put("planYetReplyCount",planYetReplyCount);
        j.setObj(retMap);
        return j;
    }


    /**
     * 排查计划统计页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "checkPlanCountList")
    public ModelAndView checkPlanCountList( HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>();
        //1：矿年度风险隐患变化趋势 2：月度风险隐患部门分布情况 3：专项辨识情况统计 4：未关联危险源隐患列表 5：重大隐患风险上报情况统计
        String beginMonth=request.getParameter("beginMonth");
        params.put("beginMonth",beginMonth);
        String endMonth=request.getParameter("endMonth");
        params.put("endMonth",endMonth);
        String type=request.getParameter("type");
        params.put("type",type);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/checkPlanCountList",params);
    }

    @RequestMapping(params = "checkPlanCountListDatagrid")
    public void checkPlanCountListDatagrid(TBInvestigatePlanEntity tBInvestigatePlanEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(TBInvestigatePlanEntity.class, dataGrid);
        String beginMonth=ResourceUtil.getParameter("beginMonth");

        String endMonth=ResourceUtil.getParameter("endMonth");

        String type=ResourceUtil.getParameter("type");

        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBInvestigatePlanEntity, request.getParameterMap());
        try{
            cq.eq("investigateType",type);
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cq.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cq.le("startTime",dateend);
            }

        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        List<TBInvestigatePlanEntity> list = dataGrid.getResults();
        if (list != null && list.size() > 0) {
            for (TBInvestigatePlanEntity t : list) {
                String acceptDept = t.getAcceptDepart();
                String acceptMan = t.getAcceptUser();

                if(StringUtils.isNotBlank(acceptDept)){
                    TSDepart dept = systemService.getEntity(TSDepart.class,acceptDept);
                    if(dept!= null){
                        t.setAcceptDepartName(dept.getDepartname());
                    }
                }
                if(StringUtils.isNotBlank(acceptMan)){
                    TSUser acceptor = systemService.getEntity(TSUser.class,acceptMan);

                    if(acceptor != null){
                        t.setAcceptUserRealName(acceptor.getRealName());
                    }
                }
            }
        }


        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 待受理计划统计页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "planForAcceptList")
    public ModelAndView planForAcceptList( HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>();
        String beginMonth=request.getParameter("beginMonth");
        params.put("beginMonth",beginMonth);
        String endMonth=request.getParameter("endMonth");
        params.put("endMonth",endMonth);
        String type=request.getParameter("type");
        params.put("type",type);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/planForAcceptList",params);
    }

    @RequestMapping(params = "planForAcceptListDatagrid")
    public void planForAcceptListDatagrid(TBInvestigatePlanEntity tBInvestigatePlanEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(TBInvestigatePlanEntity.class, dataGrid);
        String beginMonth=ResourceUtil.getParameter("beginMonth");

        String endMonth=ResourceUtil.getParameter("endMonth");

        String type=ResourceUtil.getParameter("type");

        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBInvestigatePlanEntity, request.getParameterMap());
        try{
            cq.eq("investigateType",type);
            cq.eq("status","3");
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cq.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cq.le("startTime",dateend);
            }

        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);

        List<TBInvestigatePlanEntity> list = dataGrid.getResults();
        if (list != null && list.size() > 0) {
            for (TBInvestigatePlanEntity t : list) {
                String acceptDept = t.getAcceptDepart();
                String acceptMan = t.getAcceptUser();
                if(StringUtils.isNotBlank(acceptDept)){
                    TSDepart dept = systemService.getEntity(TSDepart.class,acceptDept);
                    if(dept!= null){
                        t.setAcceptDepartName(dept.getDepartname());
                    }
                }
                if(StringUtils.isNotBlank(acceptMan)){
                    TSUser acceptor = systemService.getEntity(TSUser.class,acceptMan);
                    if(acceptor != null){
                        t.setAcceptUserRealName(acceptor.getRealName());
                    }
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 待回复计划统计页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "planForReplyList")
    public ModelAndView planForReplyList( HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>();
        //1：矿年度风险隐患变化趋势 2：月度风险隐患部门分布情况 3：专项辨识情况统计 4：未关联危险源隐患列表 5：重大隐患风险上报情况统计
        String beginMonth=request.getParameter("beginMonth");
        params.put("beginMonth",beginMonth);
        String endMonth=request.getParameter("endMonth");
        params.put("endMonth",endMonth);
        String type=request.getParameter("type");
        params.put("type",type);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/planForReplyList",params);
    }

    @RequestMapping(params = "planForReplyListDatagrid")
    public void planForReplyListDatagrid(TBInvestigatePlanEntity tBInvestigatePlanEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(TBInvestigatePlanEntity.class, dataGrid);
        String beginMonth=ResourceUtil.getParameter("beginMonth");

        String endMonth=ResourceUtil.getParameter("endMonth");

        String type=ResourceUtil.getParameter("type");

        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBInvestigatePlanEntity, request.getParameterMap());
        try{
            cq.eq("investigateType",type);
            cq.eq("status","4");
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cq.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cq.le("startTime",dateend);
            }

        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        List<TBInvestigatePlanEntity> list = dataGrid.getResults();
        if (list != null && list.size() > 0) {
            for (TBInvestigatePlanEntity t : list) {
                String acceptDept = t.getAcceptDepart();
                String acceptMan = t.getAcceptUser();
                if(StringUtils.isNotBlank(acceptDept)){
                    TSDepart dept = systemService.getEntity(TSDepart.class,acceptDept);
                    if(dept!= null){
                        t.setAcceptDepartName(dept.getDepartname());
                    }
                }
                if(StringUtils.isNotBlank(acceptMan)){
                    TSUser acceptor = systemService.getEntity(TSUser.class,acceptMan);
                    if(acceptor != null){
                        t.setAcceptUserRealName(acceptor.getRealName());
                    }
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 已回复计划统计页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "planYetReplyList")
    public ModelAndView planYetReplyList( HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>();
        //1：矿年度风险隐患变化趋势 2：月度风险隐患部门分布情况 3：专项辨识情况统计 4：未关联危险源隐患列表 5：重大隐患风险上报情况统计
        String beginMonth=request.getParameter("beginMonth");
        params.put("beginMonth",beginMonth);
        String endMonth=request.getParameter("endMonth");
        params.put("endMonth",endMonth);
        String type=request.getParameter("type");
        params.put("type",type);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/planYetReplyList",params);
    }

    @RequestMapping(params = "planYetReplyListDatagrid")
    public void planYetReplyListDatagrid(TBInvestigatePlanEntity tBInvestigatePlanEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(TBInvestigatePlanEntity.class, dataGrid);
        String beginMonth=ResourceUtil.getParameter("beginMonth");

        String endMonth=ResourceUtil.getParameter("endMonth");

        String type=ResourceUtil.getParameter("type");

        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBInvestigatePlanEntity, request.getParameterMap());
        try{
            cq.eq("investigateType",type);
            cq.eq("status","5");
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cq.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cq.le("startTime",dateend);
            }

        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        List<TBInvestigatePlanEntity> list = dataGrid.getResults();
        if (list != null && list.size() > 0) {
            for (TBInvestigatePlanEntity t : list) {
                String acceptDept = t.getAcceptDepart();
                String acceptMan = t.getAcceptUser();
                if(StringUtils.isNotBlank(acceptDept)){
                    TSDepart dept = systemService.getEntity(TSDepart.class,acceptDept);
                    if(dept!= null){
                        t.setAcceptDepartName(dept.getDepartname());
                    }
                }
                if(StringUtils.isNotBlank(acceptMan)){
                    TSUser acceptor = systemService.getEntity(TSUser.class,acceptMan);
                    if(acceptor != null){
                        t.setAcceptUserRealName(acceptor.getRealName());
                    }
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 关联隐患页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "planYetHiddenList")
    public ModelAndView planYetHiddenList( HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>();
        String beginMonth=request.getParameter("beginMonth");
        params.put("beginMonth",beginMonth);
        String endMonth=request.getParameter("endMonth");
        params.put("endMonth",endMonth);
        String type=request.getParameter("type");
        params.put("type",type);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/planYetHiddenList",params);
    }

    @RequestMapping(params = "planYetHiddenListDatagrid")
    public void planYetHiddenListDatagrid(TBInvestigatePlanRelEntity tBInvestigatePlanRelEntity,TBInvestigatePlanEntity tBInvestigatePlanEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {


        String beginMonth=ResourceUtil.getParameter("beginMonth");

        String endMonth=ResourceUtil.getParameter("endMonth");

        String type=ResourceUtil.getParameter("type");

        /*CriteriaQuery cq = new CriteriaQuery(TBInvestigatePlanEntity.class, dataGrid);

        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBInvestigatePlanEntity, request.getParameterMap());
        try{
            cq.eq("investigateType",type);
            cq.eq("status","5");
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cq.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cq.le("startTime",dateend);
            }

        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);*/


        List<String> listHiddenId = new ArrayList<String>();
        CriteriaQuery cqh = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cqh.eq("investigateType",type);
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cqh.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cqh.le("startTime",dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cqh.add();
        List<TBInvestigatePlanEntity> listMonthHidden = systemService.getListByCriteriaQuery(cqh,false);

        if(listMonthHidden != null && !listMonthHidden.isEmpty()){
            for(TBInvestigatePlanEntity tbipe : listMonthHidden){
                listHiddenId.add(tbipe.getId());
            }
        }
        cqh = new CriteriaQuery(TBInvestigatePlanRelEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cqh, tBInvestigatePlanRelEntity, request.getParameterMap());
        try{
            if(!listHiddenId.isEmpty()){
                cqh.eq("relType","3");
                cqh.in("planId", listHiddenId.toArray());

                cqh.add();
                this.systemService.getDataGridReturn(cqh, true);
                List<TBInvestigatePlanRelEntity> list = dataGrid.getResults();
                if (list != null && list.size() > 0) {
                    for (TBInvestigatePlanRelEntity t : list) {
                        String planId = t.getPlanId();
                        String objId = t.getObjId();

                        TBInvestigatePlanEntity tipe = systemService.getEntity(TBInvestigatePlanEntity.class,planId);

                        String acceptDept = tipe.getAcceptDepart();
                        String acceptMan = tipe.getAcceptUser();
                        if(StringUtils.isNotBlank(acceptDept)){
                            TSDepart dept = systemService.getEntity(TSDepart.class,acceptDept);
                            if(dept!= null){
                                tipe.setAcceptDepartName(dept.getDepartname());
                            }
                        }
                        if(StringUtils.isNotBlank(acceptMan)){
                            TSUser acceptor = systemService.getEntity(TSUser.class,acceptMan);
                            if(acceptor != null){
                                tipe.setAcceptUserRealName(acceptor.getRealName());
                            }
                        }

                        TBHiddenDangerHandleEntity tdhe = systemService.getEntity(TBHiddenDangerHandleEntity.class,objId);

                        t.settBInvestigatePlanEntity(tipe);
                        t.settBHiddenDangerHandleEntity(tdhe);
                        t.setRelTypeName("隐患");


                    }
                }

                TagUtil.datagrid(response, dataGrid);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 关风险患页面跳转
     * @param request
     * @return
     */
    @RequestMapping(params = "planYetRiskList")
    public ModelAndView planYetRiskList( HttpServletRequest request) {
        Map<String,String> params = new HashMap<String,String>();
        String beginMonth=request.getParameter("beginMonth");
        params.put("beginMonth",beginMonth);
        String endMonth=request.getParameter("endMonth");
        params.put("endMonth",endMonth);
        String type=request.getParameter("type");
        params.put("type",type);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/planYetRiskList",params);
    }

    @RequestMapping(params = "planYetRiskListDatagrid")
    public void planYetRiskListDatagrid(TBInvestigatePlanRelEntity tBInvestigatePlanRelEntity,TBInvestigatePlanEntity tBInvestigatePlanEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {


        String beginMonth=ResourceUtil.getParameter("beginMonth");

        String endMonth=ResourceUtil.getParameter("endMonth");

        String type=ResourceUtil.getParameter("type");

        /*CriteriaQuery cq = new CriteriaQuery(TBInvestigatePlanEntity.class, dataGrid);

        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBInvestigatePlanEntity, request.getParameterMap());
        try{
            cq.eq("investigateType",type);
            cq.eq("status","5");
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cq.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cq.le("startTime",dateend);
            }

        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);*/


        List<String> listHiddenId = new ArrayList<String>();
        CriteriaQuery cqh = new CriteriaQuery(TBInvestigatePlanEntity.class);
        try{
            cqh.eq("investigateType",type);
            if(StringUtil.isNotEmpty(beginMonth)){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date datebegin = sdf.parse(beginMonth);
                cqh.ge("startTime",datebegin);
            }
            if(StringUtil.isNotEmpty(endMonth)){
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                Date dateend = sdf.parse(endMonth);
                cqh.le("startTime",dateend);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        cqh.add();
        List<TBInvestigatePlanEntity> listMonthHidden = systemService.getListByCriteriaQuery(cqh,false);

        if(listMonthHidden != null && !listMonthHidden.isEmpty()){
            for(TBInvestigatePlanEntity tbipe : listMonthHidden){
                listHiddenId.add(tbipe.getId());
            }
        }
        cqh = new CriteriaQuery(TBInvestigatePlanRelEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cqh, tBInvestigatePlanRelEntity, request.getParameterMap());
        try{
            if(!listHiddenId.isEmpty()){
                cqh.eq("relType","1");
                cqh.in("planId", listHiddenId.toArray());

                cqh.add();
                this.systemService.getDataGridReturn(cqh, true);
                List<TBInvestigatePlanRelEntity> list = dataGrid.getResults();
                if (list != null && list.size() > 0) {
                    for (TBInvestigatePlanRelEntity t : list) {
                        String planId = t.getPlanId();
                        String objId = t.getObjId();

                        TBInvestigatePlanEntity tipe = systemService.getEntity(TBInvestigatePlanEntity.class,planId);

                        String acceptDept = tipe.getAcceptDepart();
                        String acceptMan = tipe.getAcceptUser();
                        if(StringUtils.isNotBlank(acceptDept)){
                            TSDepart dept = systemService.getEntity(TSDepart.class,acceptDept);
                            if(dept!= null){
                                tipe.setAcceptDepartName(dept.getDepartname());
                            }
                        }
                        if(StringUtils.isNotBlank(acceptMan)){
                            TSUser acceptor = systemService.getEntity(TSUser.class,acceptMan);
                            if(acceptor != null){
                                tipe.setAcceptUserRealName(acceptor.getRealName());
                            }
                        }


                        t.settBInvestigatePlanEntity(tipe);
                        t.setRelTypeName("风险点");


                    }
                }
                TagUtil.datagrid(response, dataGrid);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
