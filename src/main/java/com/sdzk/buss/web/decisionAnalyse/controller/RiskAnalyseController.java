package com.sdzk.buss.web.decisionAnalyse.controller;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.DateRangeUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 风险报表
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/riskAnalyseController")
public class RiskAnalyseController extends BaseController {

    private static final Logger logger = Logger.getLogger(RiskAnalyseController.class);
    private static final String specialEvaluationType = "specialEvaluationType";

    @Autowired
    private SystemService systemService;
    /**
     * 风险报表页面
     * @param request
     * @return
     */
    @RequestMapping(params = "riskJournalSheet")
    public ModelAndView hiddenDangerList( HttpServletRequest request) {
        request.setAttribute("startDate",DateRangeUtil.getCurrentWeekRangeNoTime().get(0));
        request.setAttribute("endDate",DateRangeUtil.getCurrentWeekRangeNoTime().get(1));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/risk/riskJournalSheet");
    }

    /**
     * 部门风险分布
     * @param request
     * @return
     */
    @RequestMapping(params = "goDepartRiskStatistics")
    public ModelAndView goDepartRiskStatistics( HttpServletRequest request) {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        request.setAttribute("startDate",startDate);
        request.setAttribute("endDate",endDate);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/risk/goDepartRiskStatistics");
    }

    /**
     * 部门风险统计
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "departRiskStatistics")
    @ResponseBody
    public JSONObject departRiskStatistics(HttpServletRequest request, HttpServletResponse response){
        JSONObject jo = new JSONObject();
        JSONArray departNameList = new JSONArray();
        JSONArray departIdList = new JSONArray();
        JSONArray numList = new JSONArray();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        StringBuffer numSql = new StringBuffer();
        List<Object> param =  new ArrayList<>();
        numSql.append("SELECT count(1) cnt,t.duty_unit dutyunit,t.departname FROM")
                .append(" (SELECT DISTINCT h.danger_id,h.duty_unit,d.departname FROM t_b_hidden_danger_exam h JOIN t_s_depart d ON h.duty_unit = d.id ")
                .append(" WHERE danger_id IS NOT NULL AND danger_id <> ''  ");

        if(StringUtil.isNotEmpty(startDate)){
            numSql.append(" and h.exam_date >= ? ");
            param.add(startDate);
        }
        if(StringUtil.isNotEmpty(endDate)){
            numSql.append(" and h.exam_date <= ? ");
            param.add(endDate);
        }
        numSql.append(") t")
                .append(" GROUP BY t.duty_unit,t.departname ORDER BY cnt DESC");

        List<Map<String, Object>> queryList = systemService.findForJdbc(numSql.toString(),param.toArray());
        if (queryList != null && queryList.size() > 0) {
            for (int i =0; i < queryList.size(); i++) {
                Map<String, Object> r = queryList.get(i);
                departNameList.add(r.get("departname"));
                departIdList.add(r.get("dutyunit"));
                numList.add(r.get("cnt"));
                if (i == 10){//统计前十数据
                    break;
                }
            }
        }
        if (departNameList.size() == 0) {
            departNameList.add("无符合数据");
            numList.add(0);
        }
        jo.put("departList", departNameList);
        jo.put("departIdList", departIdList);
        jo.put("numList", numList);
        return jo;
    }

    /**
     * 风险频次
     * @param request
     * @return
     */
    @RequestMapping(params = "goRiskFrequencyStatistics")
    public ModelAndView goRiskFrequencyStatistics( HttpServletRequest request) {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        request.setAttribute("startDate",startDate);
        request.setAttribute("endDate",endDate);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/risk/goRiskFrequencyStatistics");
    }

    /**
     * 风险频次统计
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "riskFrequencyStatistics")
    @ResponseBody
    public JSONObject riskFrequencyStatistics(HttpServletRequest request, HttpServletResponse response){
        JSONObject jo = new JSONObject();
        JSONArray riskNameList = new JSONArray();
        JSONArray riskIdList = new JSONArray();
        JSONArray numList = new JSONArray();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        StringBuffer numSql = new StringBuffer();
        List<Object> param =  new ArrayList<>();
        numSql.append("SELECT count(1) cnt,t.mid id, t.hazard_name hazardname from (")
                .append(" select DISTINCT  d.id did, m.id mid, m.hazard_name from t_b_hidden_danger_exam h join t_b_danger_source d on h.danger_id = d.id join t_b_hazard_manage m on d.hazard_manage_id = m.id ")
                .append(" WHERE 1=1  and d.YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+") ");
                //TODO
        if(StringUtil.isNotEmpty(startDate)){
            numSql.append(" and h.exam_date >= ? ");
            param.add(startDate);
        }
        if(StringUtil.isNotEmpty(endDate)){
            numSql.append(" and h.exam_date <= ? ");
            param.add(endDate);
        }
        numSql.append(") t")
                .append("  group by t.mid order by cnt desc");

        List<Map<String, Object>> queryList = systemService.findForJdbc(numSql.toString(),param.toArray());
        if (queryList != null && queryList.size() > 0) {
            for (int i =0; i < queryList.size(); i++) {
                Map<String, Object> r = queryList.get(i);
                riskNameList.add(0,r.get("hazardname"));
                riskIdList.add(0,r.get("id"));
                numList.add(0,r.get("cnt"));
                if (i == 10){//统计前十数据
                    break;
                }
            }
        }
        if (riskNameList.size() == 0) {
            riskNameList.add("无符合数据");
            numList.add(0);
        }
        jo.put("riskNameList", riskNameList);
        jo.put("riskIdList", riskIdList);
        jo.put("numList", numList);
        return jo;
    }

    /**
     * 风险等级分布
     * @param request
     * @return
     */
    @RequestMapping(params = "goRiskLevelStatistics")
    public ModelAndView goRiskLevelStatistics( HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/risk/goRiskLevelStatistics");
    }

    /**
     * 风险等级统计
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "riskLevelStatistics")
    @ResponseBody
    public JSONObject riskLevelStatistics(HttpServletRequest request) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT COUNT(1) count,ye_risk_grade code FROM t_b_danger_source ")
            .append(" where 1=1 and YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+") ")
            .append(" and is_delete = 0 and audit_status = 4")
            .append(" GROUP BY ye_risk_grade");
        List<Map<String, Object>> queryList = systemService.findForJdbc(sb.toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pieData",generatePieData("risklevel", queryList));
        return jsonObject;
    }

    /**
     * 专项辨识类型分布
     * @param request
     * @return
     */
    @RequestMapping(params = "goSpeEvaTypeStatistics")
    public ModelAndView goSpeEvaTypeStatistics( HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/risk/goSpeEvaTypeStatistics");
    }

    /**
     * 专项辨识类型统计
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "speEvaTypeStatistics")
    @ResponseBody
    public JSONObject speEvaTypeStatistics(HttpServletRequest request) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("select count(1) count, se.type code from t_b_special_evaluation se join t_b_se_ds_relation r on se.id = r.sepcial_evaluation_id ")
                .append("join t_b_danger_source ds on ds.id = r.danger_source_id ")
                .append("where 1=1 and ds.YE_RISK_GRADE in("+Constants.RISK_LEVEL_HIDE_WHERE+")")
                .append("and se.is_delete = 0 and ds.is_delete = 0 and ds.audit_status = 4 ")
                .append(" GROUP BY se.type");
        List<Map<String, Object>> queryList = systemService.findForJdbc(sb.toString());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pieData",generatePieData(specialEvaluationType, queryList));
        return jsonObject;
    }

    /**
     * 生产饼图数据
     * @param typeGroup
     * @param queryList
     * @return
     */
    private String generatePieData (String typeGroup, List<Map<String, Object>> queryList) {
        StringBuffer result = new StringBuffer("{\'pieData\':[");
        List<TSType> types = ResourceUtil.allTypes.get(typeGroup.toLowerCase());
        for (TSType type : types) {
            boolean doAdd = false;
            for(int i =0 ;i<queryList.size();i++){
                Map<String,Object> tempMap = queryList.get(i);
                String code = tempMap.get("code").toString();
                String count = tempMap.get("count").toString();
                if (type.getTypecode().equalsIgnoreCase(code)) {
                    result.append("{\'name\':\'").append(type.getTypename())
                            .append("\'," + "\'id\':\'").append(code)
                            .append("\'," + "\'y\':")
                            .append(Integer.valueOf(count))
                            .append("},");
                    doAdd = true;
                    queryList.remove(i);
                    continue;
                }
            }
            if (!doAdd) {
                result.append("{\'name\':\'").append(type.getTypename())
                        .append("\'," + "\'id\':\'").append(type.getTypecode())
                        .append("\'," + "\'y\':")
                        .append(0)
                        .append("},");
            }
        }
        result.append("]}");
        return result.toString();
    }

    /**
     * 风险列表信息
     * @param request
     * @return
     */
    @RequestMapping(params = "riskList")
    public ModelAndView riskList( HttpServletRequest request) {
        String riskLevel = request.getParameter("riskLevel");
        String seType = request.getParameter("seType");
        String departId = request.getParameter("departId");
        String hazardId = request.getParameter("hazardId");
        request.setAttribute("riskLevel", riskLevel);
        request.setAttribute("seType", seType);
        request.setAttribute("departId", departId);
        request.setAttribute("hazardId", hazardId);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/risk/riskList");
    }

    /**
     * 风险列表
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "riskDatagrid")
    public void riskDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String riskLevel = request.getParameter("riskLevel");
        String seType = request.getParameter("seType");
        String departId = request.getParameter("departId");
        String hazardId = request.getParameter("hazardId");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        try{
            CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class,dataGrid);
            if (StringUtil.isNotEmpty(riskLevel)) {//风险等级分布-查看各风险等级的风险列表
                cq.eq("yeRiskGrade", riskLevel);
            } else if (StringUtil.isNotEmpty(seType)) {//专项辨识类型分布-查看各专项辨识类型的风险列表
                StringBuffer sql = new StringBuffer();
                sql.append("select re.danger_source_id from t_b_se_ds_relation re join t_b_special_evaluation se on re.sepcial_evaluation_id = se.id where se.type = ?");
                cq.add(Restrictions.sqlRestriction(" id in ("+sql.toString()+")", seType, StringType.INSTANCE));
            } else if (StringUtil.isNotEmpty(departId) || StringUtil.isNotEmpty(hazardId)) {
                StringBuffer sql = new StringBuffer();
                List<String> paramValue = new ArrayList<>();
                List<Type> paramType = new ArrayList<>();
                Type[]  types;
                if (StringUtil.isNotEmpty(departId)) {//部门风险统计-查看各部门的风险列表
                    sql.append("select danger_id from t_b_hidden_danger_exam h where danger_id IS NOT NULL AND danger_id <> '' and duty_unit = ?  ");
                    paramValue.add(departId);
                    paramType.add(StringType.INSTANCE);
                } else { //风险频次-查看一类危险源关联的风险列表
                    sql.append("select d.id from t_b_hidden_danger_exam h join t_b_danger_source d on h.danger_id = d.id join t_b_hazard_manage m on d.hazard_manage_id = m.id where m.id = ?  ");
                    paramValue.add(hazardId);
                    paramType.add(StringType.INSTANCE);
                }
                if(StringUtil.isNotEmpty(startDate)){
                    sql.append(" and h.exam_date >= ? ");
                    paramValue.add(startDate);
                    paramType.add(StringType.INSTANCE);
                }
                if(StringUtil.isNotEmpty(endDate)){
                    sql.append(" and h.exam_date <= ? ");
                    paramValue.add(endDate);
                    paramType.add(StringType.INSTANCE);
                }
                types = new Type[paramType.size()];
                for (int i=0;i<paramType.size();i++) {
                    types[i] = paramType.get(i);
                }
                cq.add(Restrictions.sqlRestriction(" id in ("+sql+")", paramValue.toArray(), types));
            } else {
                cq.add(Restrictions.sqlRestriction(" 1=2 "));
            }
            if (StringUtil.isEmpty(departId) && StringUtil.isEmpty(hazardId)) {
                cq.eq("auditStatus", Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
                cq.eq("isDelete",Constants.IS_DELETE_N);
            }
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
        }catch(Exception e){
            e.printStackTrace();
        }
        TagUtil.datagrid(response, dataGrid);

    }
}