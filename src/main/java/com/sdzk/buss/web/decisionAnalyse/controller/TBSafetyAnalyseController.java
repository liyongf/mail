package com.sdzk.buss.web.decisionAnalyse.controller;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.DateRangeUtil;
import com.sdzk.buss.web.decisionAnalyse.service.TBSafetyAnalyseServiceI;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import sun.awt.im.SimpleInputMethodWindow;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Scope("prototype")
@Controller
@RequestMapping("/tBSafetyAnalyseController")
public class TBSafetyAnalyseController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBDecisionAnalyseController.class);

    @Autowired
    private SystemService systemService;
    @Autowired
    private TBSafetyAnalyseServiceI tBSafetyAnalyseService;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 隐患报表 页面跳转
     * @return
     */
    @RequestMapping(params = "hiddenDangerJournalSheet")
    public ModelAndView hiddenDangerJournalSheet(HttpServletRequest request){
        Map<String,String> params = new HashMap<>();
        //默认加载本周
        params.put("startDate", DateRangeUtil.getCurrentWeekRangeNoTime().get(0));
        params.put("endDate", DateRangeUtil.getCurrentWeekRangeNoTime().get(1));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hiddenDanger/hiddenDangerJournalSheet",params);
    }

    /**
     * 隐患地点分布 页面跳转
     * @return
     */
    @RequestMapping(params = "goHiddenDangerAddressStatistics")
    public ModelAndView goHiddenDangerAddressStatistics(HttpServletRequest request){
        Map<String,String> params = new HashMap<String,String>();
        params.put("startDate", request.getParameter("startDate"));
        params.put("endDate", request.getParameter("endDate"));
        params.put("flag", request.getParameter("flag"));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hiddenDanger/hiddenDangerAddressStatistics",params);
    }

    /**
     * 隐患等级分布 页面跳转
     * @return
     */
    @RequestMapping(params = "goHiddenDangerLevelStatistics")
    public ModelAndView goHiddenDangerLevelStatistics(HttpServletRequest request){
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        List<TSType> hiddenLevelList = ResourceUtil.allTypes.get("hiddenLevel".toLowerCase());
        Map<String,Object> hiddenLevelMap = new HashMap<>();
        if(!hiddenLevelList.isEmpty() && hiddenLevelList.size()>0){
            for(TSType type : hiddenLevelList){
                hiddenLevelMap.put(type.getTypename(),0);
            }
        }

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT tp.typename hiddenLevel, count(hde.id) count FROM t_s_type tp")
                .append(" LEFT JOIN t_b_hidden_danger_exam hde ON tp.typecode = hde.hidden_nature LEFT JOIN t_s_typegroup tg ON tg.id = tp.typegroupid")
                .append(" WHERE tg.typegroupcode = 'hiddenLevel'");
        if(StringUtils.isNotBlank(startDate)){
            sql.append(" AND hde.exam_date >= '").append(startDate).append("'");
        }
        if(StringUtils.isNotBlank(endDate)){
            sql.append(" AND hde.exam_date <= '").append(endDate).append("'");
        }
        sql.append(" GROUP BY tp.typecode ORDER BY count DESC");

        List<Map<String,Object>> mapList = systemService.findForJdbc(sql.toString());
        Map<String,String> result = new HashMap<>();
        if(!mapList.isEmpty() && mapList.size()>0){
            for(Map<String,Object> map : mapList){
                String hiddenLevel = (String)map.get("hiddenLevel");
                String count = String.valueOf(map.get("count"));

                result.put(hiddenLevel,count);
                hiddenLevelMap.remove(hiddenLevel);
            }
        }

        for(String hiddenLevel:hiddenLevelMap.keySet()){
            Map<String,Object> map = new HashMap<>();
            map.put("hiddenLevel",hiddenLevel);
            map.put("count",0);
            mapList.add(map);
        }

        //按重大~A~E排序
        Collections.sort(mapList, new Comparator<Map<String,Object>>() {
            @Override
            public int compare(Map<String,Object> map1, Map<String,Object> map2) {
                String hiddenLevel1 = (String)map1.get("hiddenLevel");
                String hiddenLevel2 = (String)map2.get("hiddenLevel");

                String typeCode1 = DicUtil.getTypeCodeByName("hiddenLevel",hiddenLevel1);
                String typeCode2 = DicUtil.getTypeCodeByName("hiddenLevel",hiddenLevel2);

                return Integer.parseInt(typeCode1) - Integer.parseInt(typeCode2);
            }
        });

        request.setAttribute("hiddenLevelList",hiddenLevelList);
        request.setAttribute("resultList",mapList);
        request.setAttribute("startDate",startDate);
        request.setAttribute("endDate",endDate);
        request.setAttribute("flag", request.getParameter("flag"));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hiddenDanger/hiddenDangerLevelStatistics");
    }

    /**
     * 隐患专业分布 页面跳转 //修改为类型
     * @return
     */
    @RequestMapping(params = "goHiddenDangerProfessionStatistics")
    public ModelAndView goHiddenDangerProfessionStatistics(HttpServletRequest request){
        Map<String,String> params = new HashMap<String,String>();
        params.put("startDate", request.getParameter("startDate"));
        params.put("endDate", request.getParameter("endDate"));
        params.put("flag", request.getParameter("flag"));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hiddenDanger/hiddenDangerProfessionStatistics",params);
    }

    /**
     * 超期隐患列表 页面跳转
     * @return
     */
    @RequestMapping(params = "goOverdueHiddenDangerList")
    public ModelAndView goOverdueHiddenDangerList(HttpServletRequest request){
        Map<String,String> params = new HashMap<String,String>();
        params.put("startDate", request.getParameter("startDate"));
        params.put("endDate", request.getParameter("endDate"));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hiddenDanger/overdueHiddenDangerList",params);
    }

    /**
     * 未闭环隐患列表 页面跳转
     * @return
     */
    @RequestMapping(params = "goUnclosedHiddenDangerList")
    public ModelAndView goUnclosedHiddenDangerList(HttpServletRequest request){
        Map<String,String> params = new HashMap<String,String>();
        params.put("startDate", request.getParameter("startDate"));
        params.put("endDate", request.getParameter("endDate"));
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hiddenDanger/unclosedHiddenDangerList",params);
    }

    /**
     * 隐患详情列表 页面跳转
     * @return
     */
    @RequestMapping(params = "goHiddenList")
    public ModelAndView goHiddenList(HttpServletRequest request) throws Exception{
        Map<String,String> params = new HashMap<String,String>();
        params.put("startDate", request.getParameter("startDate"));
        params.put("endDate", request.getParameter("endDate"));
        params.put("isCloseLoop", request.getParameter("isCloseLoop"));

        String departName = decodeURL(request.getParameter("departName"));
        String addressName = decodeURL(request.getParameter("addressName"));
        String hiddenLevelName = decodeURL(request.getParameter("hiddenLevelName"));
        String professionName = decodeURL(request.getParameter("profession"));

        if(StringUtils.isNotBlank(departName)){
            String sql = "SELECT id FROM t_s_depart WHERE departname='"+departName+"' AND delete_flag != '1'";
            List<String> tmpList = systemService.findListbySql(sql);
            if(!tmpList.isEmpty() && tmpList.size()>0){
                params.put("dutyUnitId", tmpList.get(0));
            }
        }
        if(StringUtils.isNotBlank(addressName)){
            String sql = "SELECT id FROM t_b_address_info WHERE address = '"+addressName+"' AND is_delete != '1'";
            List<String> tmpList = systemService.findListbySql(sql);
            if(!tmpList.isEmpty() && tmpList.size()>0){
                params.put("addressId", tmpList.get(0));
            }
        }
        if(StringUtils.isNotBlank(hiddenLevelName)){
            params.put("hiddenLevel", DicUtil.getTypeCodeByName("hiddenLevel",hiddenLevelName));
        }
        if(StringUtils.isNotBlank(professionName)){
            params.put("profession", DicUtil.getTypeCodeByName("proCate_gradeControl",professionName));
        }
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hiddenDanger/hiddenDangerDetailList",params);
    }

    private String decodeURL(String str) throws Exception{
        if(StringUtils.isNotBlank(str)){
            return URLDecoder.decode(str,"UTF-8");
        }else{
            return null;
        }
    }

    /**
     * 隐患地点分布 数据组装
     * @param request
     * @return
     */
    @RequestMapping(params = "hiddenDangerAddressStatistics")
    @ResponseBody
    public JSONObject hiddenDangerAddressStatistics(HttpServletRequest request){
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String flag = request.getParameter("flag");

        StringBuffer sql = new StringBuffer();
        sql.append("SELECT addr.address, count(hde.id) count FROM t_b_hidden_danger_exam hde LEFT JOIN t_b_address_info addr ON hde.address = addr.id WHERE 1=1 ");
        if(StringUtils.isNotBlank(startDate)){
            sql.append(" AND hde.exam_date >= '").append(startDate).append("'");
        }
        if(StringUtils.isNotBlank(endDate)){
            sql.append(" AND hde.exam_date <= '").append(endDate).append("'");
        }
        sql.append(" GROUP BY hde.address ORDER BY count DESC");
        if(!"1".equals(flag)){
            sql.append(" LIMIT 10");
        }

        List<Map<String,Object>> mapList = systemService.findForJdbc(sql.toString());
        StringBuffer addressNameStr = new StringBuffer().append("[");
        StringBuffer countStr = new StringBuffer().append("[");
        if(!mapList.isEmpty() && mapList.size()>0){
            for(Map<String,Object> map : mapList){
                String address = (String)map.get("address");
                String count = String.valueOf(map.get("count"));

                if(addressNameStr.length() > 1){
                    addressNameStr.append(",");
                }
                if(countStr.length() > 1){
                    countStr.append(",");
                }

                addressNameStr.append("'").append(address).append("'");
                countStr.append(count);
            }
        }
        addressNameStr.append("]");
        countStr.append("]");
        JSONObject jo = new JSONObject();
        jo.put("addressNameStr",addressNameStr.toString());
        jo.put("countStr",countStr.toString());
        return jo;
    }

    /**
     * 隐患专业分布 数据组装 //类型
     * @param request
     * @return
     */
    @RequestMapping(params = "hiddenDangerProfessionStatistics")
    @ResponseBody
    public JSONObject hiddenDangerProfessionStatistics(HttpServletRequest request){
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        StringBuffer sql = new StringBuffer();
        /*sql.append("SELECT tp.typename profession, count(hde.id) count FROM t_b_hidden_danger_exam hde LEFT JOIN t_b_danger_source ds ON hde.danger_id = ds.id")
                .append(" LEFT JOIN t_s_type tp ON tp.typecode = ds.ye_profession LEFT JOIN t_s_typegroup tg ON tg.id = tp.typegroupid")
                .append(" WHERE hde.danger_id IS NOT NULL AND ds.ye_profession IS NOT NULL AND tg.typegroupcode = 'proCate_gradeControl' ");
        if(StringUtils.isNotBlank(startDate)){
            sql.append(" AND hde.exam_date >= '").append(startDate).append("'");
        }
        if(StringUtils.isNotBlank(endDate)){
            sql.append(" AND hde.exam_date <= '").append(endDate).append("'");
        }
        sql.append(" GROUP BY ds.ye_profession ORDER BY count DESC");*/
        sql.append("SELECT tp.typename profession, count(hde.id) count FROM t_b_hidden_danger_exam hde LEFT JOIN t_s_type tp ON tp.typecode = hde.risk_type LEFT JOIN t_s_typegroup tg ON tg.id = tp.typegroupid WHERE hde.risk_type IS NOT NULL AND hde.risk_type <> '' AND tg.typegroupcode = 'risk_type'  ");
        if(StringUtils.isNotBlank(startDate)){
            sql.append(" AND hde.exam_date >= '").append(startDate).append("'");
        }
        if(StringUtils.isNotBlank(endDate)){
            sql.append(" AND hde.exam_date <= '").append(endDate).append("'");
        }
        sql.append(" GROUP BY hde.risk_type ORDER BY count DESC");

        List<Map<String,Object>> mapList = systemService.findForJdbc(sql.toString());
        StringBuffer professionStr = new StringBuffer().append("[");
        StringBuffer countStr = new StringBuffer().append("[");
        if(!mapList.isEmpty() && mapList.size()>0){
            for(Map<String,Object> map : mapList){
                String profession = (String)map.get("profession");
                String count = String.valueOf(map.get("count"));

                if(professionStr.length() > 1){
                    professionStr.append(",");
                }
                if(countStr.length() > 1){
                    countStr.append(",");
                }

                professionStr.append("'").append(profession).append("'");
                countStr.append(count);
            }
        }
        professionStr.append("]");
        countStr.append("]");
        JSONObject jo = new JSONObject();
        jo.put("professionStr",professionStr.toString());
        jo.put("countStr",countStr.toString());
        return jo;
    }

    /**
     * 隐患详情列表 数据组装
     * @param request
     * @return
     */
    @RequestMapping(params = "hiddenDangerDetailDataGrid")
    public void hiddenDangerDetailDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws Exception{
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String dutyUnitId = request.getParameter("dutyUnitId");
        String addressId = request.getParameter("addressId");
        String hiddenLevel = request.getParameter("hiddenLevel");
        String profession = request.getParameter("profession");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class,dataGrid);
        cq.createAlias("hiddenDanger","hiddenDanger");
        if(StringUtils.isNotBlank(startDate)){
            cq.ge("hiddenDanger.examDate",sdf.parse(startDate));
        }
        if(StringUtils.isNotBlank(endDate)){
            cq.le("hiddenDanger.examDate",sdf.parse(endDate));
        }
        if(StringUtils.isNotBlank(addressId)){
            cq.eq("hiddenDanger.address.id",addressId);
        }
        if(StringUtils.isNotBlank(hiddenLevel)){
            cq.eq("hiddenDanger.hiddenNature",hiddenLevel);
        }
        if(StringUtils.isNotBlank(profession)){
            String sql = "SELECT id FROM t_b_danger_source WHERE ye_profession = '"+profession+"'";
            List<String> dangerIds = systemService.findListbySql(sql);
            cq.in("hiddenDanger.dangerId.id",dangerIds.toArray());
        }
        if(StringUtils.isNotBlank(dutyUnitId)){
            String isCloseLoop = request.getParameter("isCloseLoop");
            cq.eq("hiddenDanger.dutyUnit.id",dutyUnitId);

            if("1".equals(isCloseLoop)){
                cq.eq("handlelStatus",Constants.REVIEWSTATUS_PASS);
            }else if("0".equals(isCloseLoop)){
                cq.notEq("handlelStatus",Constants.HANDELSTATUS_DRAFT);
                cq.notEq("handlelStatus",Constants.REVIEWSTATUS_PASS);
            }else{
                cq.notEq("handlelStatus",Constants.HANDELSTATUS_DRAFT);
            }
        }

        cq.addOrder("hiddenDanger.examDate", SortDirection.desc);
        cq.add();

        systemService.getDataGridReturn(cq,true);
        if(dataGrid != null && dataGrid.getResults() != null){
            List<TBHiddenDangerHandleEntity> list = dataGrid.getResults();
            for(TBHiddenDangerHandleEntity t : list){
                if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                    String[] ids = t.getHiddenDanger().getFillCardManId().split(",");
                    String name = "";

                    for(String id : ids){
                        TSUser user = systemService.getEntity(TSUser.class,id);
                        if(user!=null){
                            if(name==""){
                                name = name + user.getUserName()+"-"+user.getRealName();
                            }else{
                                name = name + "," + user.getUserName()+"-"+user.getRealName();
                            }
                        }else  if (StringUtil.isNotEmpty(id)){
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
     * 超期隐患列表 数据组装
     * @param request
     * @return
     */
    @RequestMapping(params = "overdueHiddenDangerDataGrid")
    public void overdueHiddenDangerDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        int currentPage = dataGrid.getPage();
        int pageSize = dataGrid.getRows();
        int endIndex = pageSize * currentPage;
        int startIndex = endIndex - pageSize;
        String limit = " LIMIT " + startIndex + "," + pageSize;

        String sql = " FROM t_b_hidden_danger_handle handle LEFT JOIN t_b_hidden_danger_exam exam ON handle.hidden_danger_id = exam.id";
        sql += " WHERE handle.handlel_status IN ('1', '4') AND exam.limit_date < DATE_SUB(NOW(), INTERVAL 1 DAY) ";

        if(StringUtils.isNotBlank(startDate)){
            sql += " AND exam.exam_date >= '" + startDate + "'";
        }
        if(StringUtils.isNotBlank(endDate)){
            sql += " AND exam.exam_date <= '" + endDate + "'";
        }

        Long count = systemService.getCountForJdbc("SELECT count(exam.id)"+sql);
        dataGrid.setTotal(count.intValue());

        List<String> handleIdList = systemService.findListbySql("SELECT handle.id"+sql+limit);
        List<TBHiddenDangerHandleEntity> list = new ArrayList<>();
        if(!handleIdList.isEmpty() && handleIdList.size()>0){
            CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class);
            cq.in("id",handleIdList.toArray());
            cq.createAlias("hiddenDanger","hiddenDanger");
            cq.addOrder("hiddenDanger.examDate", SortDirection.desc);
            cq.add();
            list = systemService.getListByCriteriaQuery(cq,false);
            if(!list.isEmpty() && list.size()>0){
                for(TBHiddenDangerHandleEntity t : list){
                    if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                        String[] ids = t.getHiddenDanger().getFillCardManId().split(",");
                        String name = "";

                        for(String id : ids){
                            TSUser user = systemService.getEntity(TSUser.class,id);
                            if(user!=null){
                                if(name==""){
                                    name = name + user.getUserName()+"-"+user.getRealName();
                                }else{
                                    name = name + "," + user.getUserName()+"-"+user.getRealName();
                                }
                            }else if (StringUtil.isNotEmpty(id)){
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
        }

        dataGrid.setResults(list);

        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 未闭环隐患列表 数据组装
     * @param request
     * @return
     */
    @RequestMapping(params = "unclosedHiddenDangerDataGrid")
    public void unclosedHiddenDangerDataGrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws Exception{
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class,dataGrid);

        String[] handleStatus = {Constants.REVIEWSTATUS_PASS,Constants.HANDELSTATUS_DRAFT};
        cq.notIn("handlelStatus",handleStatus);

        cq.createAlias("hiddenDanger","hiddenDanger");
        if(StringUtils.isNotBlank(startDate)){
            cq.ge("hiddenDanger.examDate",sdf.parse(startDate));
        }
        if(StringUtils.isNotBlank(endDate)){
            cq.le("hiddenDanger.examDate",sdf.parse(endDate));
        }
        cq.addOrder("hiddenDanger.examDate", SortDirection.desc);
        cq.add();

        systemService.getDataGridReturn(cq,true);
        if(dataGrid != null && dataGrid.getResults() != null){
            List<TBHiddenDangerHandleEntity> list = dataGrid.getResults();
            for(TBHiddenDangerHandleEntity t : list){
                if (StringUtils.isNotBlank(t.getHiddenDanger().getFillCardManId())){
                    String[] ids = t.getHiddenDanger().getFillCardManId().split(",");
                    String name = "";

                    for(String id : ids){
                        TSUser user = systemService.getEntity(TSUser.class,id);
                        if(user!=null){
                            if(name==""){
                                name = name + user.getUserName()+"-"+user.getRealName();
                            }else{
                                name = name + "," + user.getUserName()+"-"+user.getRealName();
                            }
                        }else if (StringUtil.isNotEmpty(id)){
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
     * 根据统计维度获取起止日期
     * @param request
     * @return
     */
    @RequestMapping(params = "getDateRangeByDimension")
    @ResponseBody
    public JSONObject getDateRangeByDimension(String dimension,String month,String week,HttpServletRequest request) throws Exception{
        JSONObject jo = new JSONObject();
        String startDate = "";
        String endDate = "";
        if("week".equals(dimension)){
            if("lastWeek".equals(week)){
                startDate = DateRangeUtil.getLastWeekRangeNoTime().get(0);
                endDate = DateRangeUtil.getLastWeekRangeNoTime().get(1);
            }else{
                startDate = DateRangeUtil.getCurrentWeekRangeNoTime().get(0);
                endDate = DateRangeUtil.getCurrentWeekRangeNoTime().get(1);
            }
        }
        if("month".equals(dimension)){
            if("lastMonth".equals(month)){
                startDate = DateRangeUtil.getLastMonthRangeNoTime().get(0);
                endDate = DateRangeUtil.getLastMonthRangeNoTime().get(1);
            }else{
                startDate = DateRangeUtil.getCurrentMonthRangeNoTime().get(0);
                endDate = DateRangeUtil.getCurrentMonthRangeNoTime().get(1);
            }
        }
        jo.put("startDate",startDate);
        jo.put("endDate",endDate);
        return jo;
    }
}
