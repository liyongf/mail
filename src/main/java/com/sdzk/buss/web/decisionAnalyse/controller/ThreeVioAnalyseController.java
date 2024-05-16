package com.sdzk.buss.web.decisionAnalyse.controller;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.DateRangeUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DateUtils;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 三违报表
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/threeVioAnalyseController")
public class ThreeVioAnalyseController extends BaseController {

    private static final Logger logger = Logger.getLogger(ThreeVioAnalyseController.class);

    @Autowired
    private SystemService systemService;
    /**
     * 三违报表页面
     * @param request
     * @return
     */
    @RequestMapping(params = "threeVioJournalSheet")
    public ModelAndView threeVioJournalSheet( HttpServletRequest request) {
        request.setAttribute("startDate",DateRangeUtil.getCurrentWeekRangeNoTime().get(0));
        request.setAttribute("endDate",DateRangeUtil.getCurrentWeekRangeNoTime().get(1));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/threeVio/threeVioJournalSheet");
    }

    /**
     * 部门三违分布
     * @param request
     * @return
     */
    @RequestMapping(params = "goDepartThreeVioStatistics")
    public ModelAndView goDepartThreeVioStatistics( HttpServletRequest request) {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        request.setAttribute("startDate",startDate);
        request.setAttribute("endDate",endDate);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/threeVio/goDepartThreeVioStatistics");
    }

    /**
     * 部门三违统计
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "departThreeVioStatistics")
    @ResponseBody
    public JSONObject departThreeVioStatistics(HttpServletRequest request, HttpServletResponse response){
        JSONObject jo = new JSONObject();
        JSONArray departNameList = new JSONArray();
        JSONArray departIdList = new JSONArray();
        JSONArray numList = new JSONArray();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        StringBuffer numSql = new StringBuffer();
        List<Object> param =  new ArrayList<>();
        numSql.append("SELECT count(1) cnt, vio.vio_units dutyunit, d.departname from t_b_three_violations vio join t_s_depart d on vio.vio_units = d.id where 1=1 and vio.VIO_LEVEL in ("+ Constants.THREE_VIO_LEVEL_HIDE_WHERE+") ");
        if(StringUtil.isNotEmpty(startDate)){
            numSql.append(" and vio_date >= ? ");
            param.add(startDate);
        }
        if(StringUtil.isNotEmpty(endDate)){
            numSql.append(" and vio_date <= ? ");
            param.add(endDate);
        }
        numSql.append(" GROUP BY vio.vio_units ");

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
     * 违章变化趋势
     * @param request
     * @return
     */
    @RequestMapping(params = "goThreeVioTrendStatistics")
    public ModelAndView goThreeVioTrendStatistics( HttpServletRequest request) {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        request.setAttribute("startDate",startDate);
        request.setAttribute("endDate",endDate);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/threeVio/goThreeVioTrendStatistics");
    }

    /**
     * 违章变化趋势统计
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "threeVioTrendStatistics")
    @ResponseBody
    public JSONObject threeVioTrendStatistics(HttpServletRequest request, HttpServletResponse response){
        JSONObject jo = new JSONObject();
        JSONArray vioDateList = new JSONArray();
        JSONArray numList = new JSONArray();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        StringBuffer numSql = new StringBuffer();
        List<Object> param =  new ArrayList<>();
        numSql.append("select count(1) cnt, DATE_FORMAT(vio_date,'%Y-%m-%d') vioDate from t_b_three_violations ")
                .append(" WHERE 1=1 and VIO_LEVEL in ("+ Constants.THREE_VIO_LEVEL_HIDE_WHERE+") ");
        if(StringUtil.isNotEmpty(startDate)){
            numSql.append(" and vio_date >= ? ");
            param.add(startDate);
        }
        if(StringUtil.isNotEmpty(endDate)){
            numSql.append(" and vio_date <= ? ");
            param.add(endDate);
        }
        numSql.append("  GROUP BY vio_date order by vio_date ");

        List<Map<String, Object>> queryList = systemService.findForJdbc(numSql.toString(),param.toArray());
        if (queryList != null && queryList.size() > 0) {
            if (StringUtil.isEmpty(startDate)) {
                startDate = (String) queryList.get(0).get("vioDate");
            }
            if (StringUtil.isEmpty(endDate)) {
                endDate = (String) queryList.get(queryList.size()-1).get("vioDate");
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date start = sdf.parse(startDate);
                Date end = sdf.parse(endDate);
                for (Date d = start; d.compareTo(end)<=0; d = DateUtils.getNextDay(d)) {
                    boolean doAdd = false;
                    for (int i =0; i < queryList.size(); i++) {
                        Map<String, Object> r = queryList.get(i);
                        if (d.compareTo(sdf.parse((String) r.get("vioDate"))) == 0){
                            vioDateList.add(r.get("vioDate"));
                            numList.add(r.get("cnt"));
                            doAdd = true;
                            queryList.remove(i);
                        }
                    }
                    if (!doAdd) {
                        vioDateList.add(sdf.format(d));
                        numList.add(0);
                    }
                }
            } catch (ParseException e) {
            }
        }

//        if (StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
//            try {
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                Date start = sdf.parse(startDate);
//                Date end = sdf.parse(endDate);
//                start.compareTo(start);
//                for (Date d = start; d.compareTo(end)<=0; d = DateUtils.getNextDay(d)) {
//                    System.out.println(d.toString());
//                }
//            } catch (ParseException e) {
//            }
//        }
//        if (queryList != null && queryList.size() > 0) {
//            for (int i =0; i < queryList.size(); i++) {
//                Map<String, Object> r = queryList.get(i);
//                vioDateList.add(r.get("vioDate"));
//                numList.add(r.get("cnt"));
//            }
//        }
        if (vioDateList.size() == 0) {
            vioDateList.add("无符合数据");
            numList.add(0);
        }
        jo.put("vioDateList", vioDateList);
        jo.put("numList", numList);
        return jo;
    }

    /**
     * 三违等级分布
     * @param request
     * @return
     */
    @RequestMapping(params = "goThreeVioLevelStatistics")
    public ModelAndView goThreeVioLevelStatistics( HttpServletRequest request) {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        request.setAttribute("startDate",startDate);
        request.setAttribute("endDate",endDate);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/threeVio/goThreeVioLevelStatistics");
    }

    /**
     * 三违等级统计
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "threeVioLevelStatistics")
    @ResponseBody
    public JSONObject threeVioLevelStatistics(HttpServletRequest request) throws Exception {
        StringBuffer sb = new StringBuffer();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        List<Object> param =  new ArrayList<>();
        sb.append("select count(1) count, vio_level code from t_b_three_violations ")
            .append(" where 1=1 and VIO_LEVEL in ("+ Constants.THREE_VIO_LEVEL_HIDE_WHERE+") ");
        if(StringUtil.isNotEmpty(startDate)){
            sb.append(" and vio_date >= ? ");
            param.add(startDate);
        }
        if(StringUtil.isNotEmpty(endDate)){
            sb.append(" and vio_date <= ? ");
            param.add(endDate);
        }
        sb.append(" GROUP BY vio_level");
        List<Map<String, Object>> queryList = systemService.findForJdbc(sb.toString(), param.toArray());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pieData",generatePieData("vio_level", queryList));
        return jsonObject;
    }

    /**
     * 专项辨识类型分布
     * @param request
     * @return
     */
    @RequestMapping(params = "goThreeVioTypeStatistics")
    public ModelAndView goThreeVioTypeStatistics( HttpServletRequest request) {
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        request.setAttribute("startDate",startDate);
        request.setAttribute("endDate",endDate);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/threeVio/goThreeVioTypeStatistics");
    }

    /**
     * 专项辨识类型统计
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "threeVioTypeStatistics")
    @ResponseBody
    public JSONObject threeVioTypeStatistics(HttpServletRequest request) throws Exception {
        StringBuffer sb = new StringBuffer();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        List<Object> param =  new ArrayList<>();
        sb.append("select count(1) count, vio_category code from t_b_three_violations ")
                .append(" where 1=1 and VIO_LEVEL in ("+ Constants.THREE_VIO_LEVEL_HIDE_WHERE+") ");
        if(StringUtil.isNotEmpty(startDate)){
            sb.append(" and vio_date >= ? ");
            param.add(startDate);
        }
        if(StringUtil.isNotEmpty(endDate)){
            sb.append(" and vio_date <= ? ");
            param.add(endDate);
        }
        sb.append(" GROUP BY vio_category");
        List<Map<String, Object>> queryList = systemService.findForJdbc(sb.toString(),param.toArray());
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pieData",generatePieData("violaterule_wzfl", queryList));
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
     * 三违列表信息
     * @param request
     * @return
     */
    @RequestMapping(params = "threeVioList")
    public ModelAndView threeVioList( HttpServletRequest request) {
        String threeVioLevel = request.getParameter("threeVioLevel");
        String threeVioType = request.getParameter("threeVioType");
        String departId = request.getParameter("departId");
        String vioDate = request.getParameter("vioDate");
        request.setAttribute("threeVioLevel", threeVioLevel);
        request.setAttribute("threeVioType", threeVioType);
        request.setAttribute("departId", departId);
        request.setAttribute("vioDate", vioDate);
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/threeVio/threeVioList");
    }

    /**
     * 三违列表
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "threeVioDatagrid")
    public void threeVioDatagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String threeVioLevel = request.getParameter("threeVioLevel");
        String threeVioType = request.getParameter("threeVioType");
        String departId = request.getParameter("departId");
        String vioDate = request.getParameter("vioDate");
        try{
            CriteriaQuery cq = new CriteriaQuery(TBThreeViolationsEntity.class,dataGrid);
            if (StringUtil.isNotEmpty(threeVioLevel)) {//三违等级分布-查看各三违等级的三违列表
                cq.eq("vioLevel", threeVioLevel);
            } else if (StringUtil.isNotEmpty(threeVioType)) {//三违类型分布-查看各三违类型的三违列表
                cq.eq("vioCategory", threeVioType);
            } else if (StringUtil.isNotEmpty(departId)) {//部门三违分布-查看各三违类型的三违列表
                cq.eq("vioUnits", departId);
            }  else if (StringUtil.isNotEmpty(vioDate)) {//违章变化趋势
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cq.eq("vioDate", sdf.parse(vioDate));
            } else {
                cq.add(Restrictions.sqlRestriction(" 1=2 "));
            }
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
        }catch(Exception e){
            e.printStackTrace();
        }
        TagUtil.datagrid(response, dataGrid);

    }
}