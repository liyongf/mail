package com.liyf.boot.smnyj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.RSAUtils;
import com.sdzk.buss.web.mine.entity.TBOrgEntity;
import com.sdzk.buss.web.mine.service.TBOrgServiceI;
import com.sdzk.buss.web.org.entity.TbOrgEntity;
import com.sdzk.sys.common.redis.RedisClient;
import com.sdzk.sys.common.redis.RedisConstants;
import com.sdzk.sys.util.CommonUtil;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TBCirculateAnotice;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangchen
 * @version V1.0
 * @Title: Controller
 * @Description: 首页窗口
 * @date 2020-04-08
 */
@Controller
@RequestMapping("/apiScreenController")
public class apiScreenController extends BaseController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private TBOrgServiceI tbOrgServiceI;
    public ICommonDao commonDao = null;


    /**
     * 获取矿井信息
     */
    @RequestMapping(params = "getOrg")
    @ResponseBody
    public ApiResultJson getOrg(HttpServletRequest request) {
        String token = request.getParameter("token");
        String unitType = request.getParameter("unitType");
        boolean result = verify(token);
        if (!result) {
            return new ApiResultJson(ApiResultJson.CODE_401, ApiResultJson.CODE_401_MSG, null);
        }
        List<Map<String, String>> area = new ArrayList();
        List<String> idList = new ArrayList<>();
        if (StringUtils.isNotBlank(unitType)) {
            if (unitType.equals("1")) {
                idList = systemService.findListbySql("select id from t_b_org where unit_type='1' order by sort_no+0 asc ");
            } else if (unitType.equals("2")) {
                idList = systemService.findListbySql("select id from t_b_org where unit_type='2' order by sort_no+0 asc ");
            } else if (unitType.equals("3")) {
                idList = systemService.findListbySql("select id from t_b_org where unit_type='3' order by sort_no+0 asc ");
            }
        } else {
            idList = systemService.findListbySql("select id from t_b_org  order by sort_no+0 asc ");
        }
        for (String mid : idList) {
            TBOrgEntity orgEntity = systemService.getEntity(TBOrgEntity.class, mid);
            if (orgEntity != null) {
                Map<String, String> map = new HashMap<>();
                map.put("mineName", orgEntity.getMineName());
                map.put("id", orgEntity.getId());
                map.put("parentId", orgEntity.getParentId());
                map.put("sortNo", orgEntity.getSortNo());
                area.add(map);
            }
        }
        return new ApiResultJson(ApiResultJson.CODE_200, ApiResultJson.CODE_200_MSG, area);
    }

    /**
     * 首页消息滚动条 页面  超期未闭环隐患滚动
     */
    @RequestMapping(params = "getMsgs")
    @ResponseBody
    public AjaxJson getMsgs(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> attributes = new HashMap<>();
        String id = request.getParameter("id");
        String token = request.getParameter("token");
        boolean result = verify(token);
        if (!result) {
            j.setMsg("非法请求");
            j.setSuccess(false);
            return j;
        }
        String redisKey = String.format(RedisConstants.SESSION_MSGS, id);
        String value = redisClient.get(redisKey);
        if (StringUtils.isBlank(value)) {
            StringBuffer sql = new StringBuffer();
            sql.append(" select a0.id,a0.mine_name as mineName, IFNULL(a1.nums,0) as fxnums,IFNULL(a2.nums,0) as wbhyh,IFNULL(a3.nums,0) as allyh,IFNULL(a4.nums,0) as swnums,IFNULL(a5.nums,0) as gznums from t_b_org a0 ");
            sql.append(" left join (select belong_mine,COUNT(id) as nums from t_b_danger_source where is_submit = ? ");
            sql.append(" and is_delete != ? GROUP BY belong_mine) a1 on a1.belong_mine = a0.id ");
            sql.append(" left join (select b.belong_mine,COUNT(b.id) as nums from t_b_hidden_danger_handle a ");
            sql.append(" inner join t_b_hidden_danger_exam b on a.hidden_id = b.id where b.is_delete != ? and a.handle_status !='0'and a.handle_status != ? ");
            sql.append("  GROUP BY belong_mine) as a2 on a0.id = a2.belong_mine ");
            sql.append(" left join (select b.belong_mine,COUNT(b.id) as nums from t_b_hidden_danger_handle a ");
            sql.append(" inner join t_b_hidden_danger_exam b on a.hidden_id = b.id where a.handle_status !='0'and b.is_delete != ? ");
            sql.append(" GROUP BY belong_mine) as a3 on a0.id = a3.belong_mine ");
            sql.append(" left join (select belong_mine,COUNT(id) as nums from t_b_three_violations where is_delete != ? ");
            sql.append(" GROUP BY belong_mine) as a4 on a0.id = a4.belong_mine ");
            sql.append(" left join (select belong_mine,COUNT(id) as nums from t_b_important_work_month where is_delete != ? ");
            sql.append(" GROUP BY belong_mine) as a5 on a0.id = a5.belong_mine ");
            sql.append(" where a0.unit_type = '3'  ");
            // 根据id获取belongmine
            List<String> idList = new ArrayList<>();
            if (StringUtils.isBlank(id)) {
                id = systemService.getSessionBelongMine();
            }
            try {
                if ("true".equals(systemService.getSessionTestMineFlag())) {
                    idList.add(systemService.getSessionUser().getUserName());
                } else {
                    idList = systemService.getMineIdList(id, systemService.getSessionUser());
                }

            } catch (Exception e) {

            }

            //查询风险数量，隐患数量，三违数量
            if (idList != null && !idList.isEmpty()) {
                sql.append(" and a0.id in ( ");
                for (int i = 0; i < idList.size(); i++) {
                    if (i == (idList.size() - 1)) {
                        sql.append(" '").append(idList.get(i)).append("'");
                    } else {
                        sql.append(" '").append(idList.get(i)).append("',");
                    }
                }
                sql.append(" ) ");
            }
            sql.append("  order by a0.sort_no+0 asc,(a1.nums+a2.nums+a3.nums+a4.nums+a5.nums) desc ");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            List<Map<String, Object>> list = systemService.findForJdbc(sql.toString(), Constants.RISK_SUBMIT_YES, Constants.IS_DELETE_Y, Constants.IS_DELETE_Y, Constants.HIDDEN_HANDLE_STATUS_FCTG, Constants.IS_DELETE_Y, Constants.IS_DELETE_Y, Constants.IS_DELETE_Y);
            systemService.getSession().clear();
            attributes.put("item", list);
            redisClient.set(redisKey, CommonUtil.gson.toJson(attributes), RedisConstants.HALF_HOUR);
        } else {
            attributes = CommonUtil.gson.fromJson(value, new TypeToken<Map<String, Object>>() {
            }.getType());
        }
        j.setAttributes(attributes);
        return j;
    }

    /**
     * 总体情况统计
     */
    @RequestMapping(params = "preventChart")
    @ResponseBody
    public AjaxJson preventChart(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String token = request.getParameter("token");
        boolean result = verify(token);
        if (!result) {
            j.setMsg("非法请求");
            j.setSuccess(false);
            return j;
        }
        Map<String, Object> attributes = new HashMap<>();
        try {
            String id = request.getParameter("id");
            //类型等于空 默认查询所有
            if (StringUtils.isBlank(id)) {
                j.setMsg("缺少请求参数id");
                j.setSuccess(false);
                return j;
            }
            String redisKey = String.format(RedisConstants.SESSION_PREVENT, id);
            // 根据类型查询遍历煤矿
            List<String> mineList = new ArrayList<>();
            TBOrgEntity orgEntity = systemService.getEntity(TBOrgEntity.class, id);
            if (orgEntity==null) {
                j.setMsg("参数id不存在");
                j.setSuccess(false);
                return j;
            }
            if ("1".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org where unit_type='3' order by sort_no+0 asc ");
            } else if ("2".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org where parent_id='"+id+"' order by sort_no+0 asc ");
            } else {
                mineList = systemService.findListbySql("select id from t_b_org where id='"+id+"' order by sort_no+0 asc ");
            }

            List<String> category = new ArrayList<>();
            List<Integer> series = new ArrayList<>();

            String mineListStr = JSON.toJSONString(mineList);

            if (mineList.size() <= 0) {
                category.add("风险");
                series.add(0);
                category.add("隐患");
                series.add(0);
                category.add("三违");
                series.add(0);
                attributes.put("category", category);
                attributes.put("series", series);
                j.setAttributes(attributes);
                return j;
            } else {
                category.add("风险");
                category.add("隐患");
                category.add("三违");
                category.add("重点工作");
            }
            String value = redisClient.get(redisKey);
            if (StringUtils.isBlank(value)) {
                // 安全风险
                String riskSql = "select count(1) from t_b_danger_source where is_delete = '0' and is_submit='" + Constants.RISK_SUBMIT_YES + "'  and belong_mine in (" + mineListStr.substring(1, mineListStr.length() - 1) + ") ";
                int riskCount = systemService.getCountForJdbc(riskSql).intValue();
                category.add("风险");
                series.add(riskCount);

                // 安全隐患
                String hiddenSql = "select count(h.id) from t_b_hidden_danger_handle h LEFT JOIN t_b_hidden_danger_exam e on h.hidden_id=e.id where e.is_delete = '0' and h.handle_status !='0' and e.belong_mine in (" + mineListStr.substring(1, mineListStr.length() - 1) + ") ";
                int hiddenCount = systemService.getCountForJdbc(hiddenSql).intValue();
                category.add("隐患");
                series.add(hiddenCount);


                String threevioSql = " SELECT	count( v.belong_mine ) 	 NAME FROM	t_b_three_violations v where 	v.is_delete = '0' and v.belong_mine in (" + mineListStr.substring(1, mineListStr.length() - 1) + ") ";
                int threevioCount = systemService.getCountForJdbc(threevioSql).intValue();
                category.add("三违");
                series.add(threevioCount);

                //重点工作
                String importSql = "SELECT	count(1) FROM	t_b_important_work_month WHERE	is_delete != '1'  and belong_mine in (" + mineListStr.substring(1, mineListStr.length() - 1) + ") ";
                int importSqlCount = systemService.getCountForJdbc(importSql).intValue();
                category.add("重点工作");
                series.add(importSqlCount);
                redisClient.set(redisKey, CommonUtil.gson.toJson(series), RedisConstants.HALF_HOUR);
            } else {
                series = CommonUtil.gson.fromJson(value, new TypeToken<List<Integer>>() {
                }.getType());
            }
            attributes.put("category", category);
            attributes.put("series", series);
            j.setAttributes(attributes);
            return j;
        } catch (Exception e) {
            j.setMsg("服务器内部错误");
            j.setSuccess(false);
        }
        j.setAttributes(attributes);
        return j;
    }

    /**
     * 风险等级数量统计
     */
    @RequestMapping(params = "riskLevelChart")
    @ResponseBody
    public AjaxJson riskLevelChart(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String token = request.getParameter("token");
        boolean result = verify(token);
        if (!result) {
            j.setMsg("非法请求");
            j.setSuccess(false);
            return j;
        }
        Map<String, Object> attributes = new HashMap<>();
        try {
            String id = request.getParameter("id");
            if (StringUtils.isBlank(id)) {
                j.setMsg("缺少请求参数id");
                j.setSuccess(false);
                return j;
            }
            // 根据类型查询遍历煤矿
            List<String> mineList = new ArrayList<>();
            TBOrgEntity orgEntity = systemService.getEntity(TBOrgEntity.class, id);
            if (orgEntity==null) {
                j.setMsg("参数id不存在");
                j.setSuccess(false);
                return j;
            }
            if ("1".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org where unit_type='3' order by sort_no+0 asc ");
            } else if ("2".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org where parent_id='"+id+"' order by sort_no+0 asc ");
            } else {
                mineList = systemService.findListbySql("select id from t_b_org where id='"+id+"' order by sort_no+0 asc ");
            }


            String mineListStr = JSON.toJSONString(mineList);

            String sql = "";
            sql += " SELECT  t.typename as name,count(b.id) as value , t.typecode as typecode  FROM t_b_danger_source b LEFT JOIN t_s_type  t on";
            sql += " b.risk_level=t.typecode LEFT JOIN t_s_typegroup  ty on t.typegroupid=ty.ID ";
            sql += " WHERE ty.typegroupcode='risk_level' and ";
            sql += " b.is_delete='0'";
            sql += " and b.is_submit='" + Constants.RISK_SUBMIT_YES + "' ";
            sql += " and b.risk_level !=''";
            sql += " and b.belong_mine in (" + mineListStr.substring(1, mineListStr.length() - 1) + ") ";
            sql += " GROUP BY";
            sql += " b.risk_level";
            List<Map<String, Object>> dataList = systemService.findForJdbc(sql);
            ;
            List<List<String>> list = new ArrayList<>();
            List<String> category = new ArrayList<>();
            category.add("risk");
            category.add("num");
            list.add(category);
            for (Map<String, Object> temp : dataList) {
                List<String> category2 = new ArrayList<>();
                category2.add(temp.get("name").toString());
                category2.add(temp.get("value").toString());
                category2.add(temp.get("typecode").toString());
                list.add(category2);
            }
            attributes.put("series", list);
            j.setAttributes(attributes);
            return j;
        } catch (Exception e) {
            j.setMsg("服务器内部错误");
            j.setSuccess(false);
        }
        j.setAttributes(attributes);
        return j;
    }

    /**
     * 风险隐患三违数量统计
     */
    @RequestMapping(params = "riskHiddenVioCount")
    @ResponseBody
    public AjaxJson riskHiddenVioCount(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String token = request.getParameter("token");
        boolean result = verify(token);
        if (!result) {
            j.setMsg("非法请求");
            j.setSuccess(false);
            return j;
        }
        Map<String, Object> attributes = new HashMap<>();
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        try {
            String id = request.getParameter("id");
            //排序 asc 正序 desc 倒序
            String sortTyp = request.getParameter("sortTyp");
            String queryBeginDate = request.getParameter("queryBeginDate");
            if (StringUtils.isBlank(id)) {
                j.setMsg("缺少请求参数id");
                j.setSuccess(false);
                return j;
            }
            String hiddenSql = "";
            String riskSql = " ";
            String vioSql = "";
            String redisKey = String.format(RedisConstants.SESSION_RISK_HIDDEN_THREE, id);
            if (StringUtils.isNotEmpty(queryBeginDate)) {
                hiddenSql = "AND DATE_FORMAT(e.exam_date,'%Y-%m') = '" + queryBeginDate + "' ";
                riskSql = "AND DATE_FORMAT(create_date,'%Y-%m') = '" + queryBeginDate + "' ";
                vioSql = "AND DATE_FORMAT(vio_date,'%Y-%m') = '" + queryBeginDate + "' ";
                redisKey = String.format(RedisConstants.SESSION_RISK_HIDDEN_THREE, id + "_" + queryBeginDate);
            }

            //根据类型查询遍历煤矿
            List<String> mineList = new ArrayList<>();
            TBOrgEntity orgEntity = systemService.getEntity(TBOrgEntity.class, id);
            if (orgEntity==null) {
                j.setMsg("参数id不存在");
                j.setSuccess(false);
                return j;
            }
            if ("1".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org where unit_type='3' order by sort_no+0 asc ");
            } else if ("2".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org where parent_id='"+id+"' order by sort_no+0 asc ");
            } else {
                mineList = systemService.findListbySql("select id from t_b_org where id='"+id+"' order by sort_no+0 asc ");
            }

            //查询每个矿的数据
            List<Map<String, Object>> list = new ArrayList<>();
            if (mineList != null && mineList.size() > 0) {
                String value = redisClient.get(redisKey);
                if (StringUtils.isBlank(value)) {
                    for (String key : mineList) {
                        TBOrgEntity mineInfo = systemService.getEntity(TBOrgEntity.class, key);
                        Map<String, Object> mineInfoObj = new HashMap<>();
                        String sql = "\n" +
                                "select sum(a.hiddenCount+b.riskCount+c.vioCount) as totalCount,a.hiddenCount,b.riskCount,c.vioCount,d.hiddenCloseCount from \n" +
                                "(select  count(1) as hiddenCount from   t_b_hidden_danger_exam e left join t_b_hidden_danger_handle h on e.id=h.hidden_id where e.is_delete !='1' and h.handle_status !='0' " + hiddenSql + " and e.belong_mine='" + key + "') as a,\n" +
                                "(select count(1) as  riskCount  from t_b_danger_source where is_delete!='1' " + riskSql + " and is_submit='0' and belong_mine='" + key + "') as b,\n" +
                                "(select count(1) as vioCount   from t_b_three_violations where is_delete!='1' " + vioSql + " and belong_mine='" + key + "') as c,\n" +
                                "(select  count(1) as hiddenCloseCount from   t_b_hidden_danger_exam e left join t_b_hidden_danger_handle h on e.id=h.hidden_id where e.is_delete !='1' " + hiddenSql + " and h.handle_status ='4' and e.belong_mine='" + key + "') as d\t\t";
                        List<Map<String, Object>> data = systemService.findForJdbc(sql);
                        mineInfoObj.put("totalCount", data.get(0).get("totalCount").toString());
                        mineInfoObj.put("hiddenCount", data.get(0).get("hiddenCount").toString());
                        mineInfoObj.put("riskCount", data.get(0).get("riskCount").toString());
                        mineInfoObj.put("vioCount", data.get(0).get("vioCount").toString());
                        mineInfoObj.put("mineName", mineInfo.getMineName());
                        mineInfoObj.put("dateStr", DateUtils.formatDate(new Date(), "yyyy-MM-dd"));
                        //计算整改率
                        BigDecimal a = new BigDecimal(data.get(0).get("hiddenCount").toString());
                        BigDecimal b = new BigDecimal(data.get(0).get("hiddenCloseCount").toString());
                        if (a.compareTo(BigDecimal.ZERO) == 0) {
                            mineInfoObj.put("closeRatio", "100%");
                        } else {
                            BigDecimal ratio = b.divide(a, 4, BigDecimal.ROUND_HALF_UP);
                            mineInfoObj.put("closeRatio", percent.format(ratio.doubleValue()));
                        }
                        list.add(mineInfoObj);
                    }
                    redisClient.set(redisKey, CommonUtil.gson.toJson(list), RedisConstants.HALF_HOUR);
                } else {
                    list = CommonUtil.gson.fromJson(value, new TypeToken<List<Map<String, Object>>>() {
                    }.getType());
                }
                list.sort((m1, m2) -> {
                    if (StringUtils.equals(sortTyp, "desc")) {
                        return Integer.valueOf(m2.get("totalCount").toString()).compareTo(Integer.valueOf(m1.get("totalCount").toString()));
                    } else {
                        return Integer.valueOf(m1.get("totalCount").toString()).compareTo(Integer.valueOf(m2.get("totalCount").toString()));
                    }
                });
            }
            j.setObj(list);
            return j;
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            j.setMsg("服务器内部错误");
            j.setSuccess(false);
        }
        j.setAttributes(attributes);
        return j;
    }

    /**
     * 用户活跃记录
     */
    @RequestMapping(params = "userActivityRecord")
    @ResponseBody
    public AjaxJson UserActivityRecord(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String token = request.getParameter("token");
        boolean result = verify(token);
        if (!result) {
            j.setMsg("非法请求");
            j.setSuccess(false);
            return j;
        }
        try {
            String id = request.getParameter("id");
            //排序 asc 正序 desc 倒序
            String sortTyp = request.getParameter("sortTyp");
            String queryBeginDate = request.getParameter("queryBeginDate");
            String sql2 = "";
            if (StringUtils.isBlank(id)) {
                j.setMsg("缺少请求参数id");
                j.setSuccess(false);
                return j;
            }
            sql2 = "or g.belong_mine='" + id + "'";
            String redisKey = String.format(RedisConstants.SESSION_ACTIVITY_RECORD, id);
            String logSql = "";
            if (StringUtils.isNotEmpty(queryBeginDate)) {
                logSql = "AND DATE_FORMAT(g.operatetime,'%Y-%m') = '" + queryBeginDate + "' ";
                redisKey = String.format(RedisConstants.SESSION_ACTIVITY_RECORD, id + "_" + queryBeginDate);
            }
            List<Map<String, Object>> list = new ArrayList<>();
            String value = redisClient.get(redisKey);
            if (StringUtils.isBlank(value)) {
                // 根据类型查询遍历煤矿
                List<String> mineList = new ArrayList<>();
                TBOrgEntity orgEntity = systemService.getEntity(TBOrgEntity.class, id);
                if (orgEntity==null) {
                    j.setMsg("参数id不存在");
                    j.setSuccess(false);
                    return j;
                }
                if ("1".equals(orgEntity.getUnitType())) {
                    mineList = systemService.findListbySql("select id from t_b_org where unit_type='3' order by sort_no+0 asc ");
                } else if ("2".equals(orgEntity.getUnitType())) {
                    mineList = systemService.findListbySql("select id from t_b_org where parent_id='"+id+"' order by sort_no+0 asc ");
                } else {
                    mineList = systemService.findListbySql("select id from t_b_org where id='"+id+"' order by sort_no+0 asc ");
                }

                String mineListStr = JSON.toJSONString(mineList);
                String sql = "select count(1)count ,g.belong_mine as mine,CASE o.mine_name WHEN o.mine_name is  null  THEN  o.mine_name ELSE '能源局' END  as mineName\n" +
                        "from t_s_log g   left  join  t_b_org  o  on  g.belong_mine=o.id where g.loglevel='1' " + logSql + " and (g.belong_mine in (" + mineListStr.substring(1, mineListStr.length() - 1) + ") " + sql2 + ") GROUP BY g.belong_mine";
                List<Map<String, Object>> data = systemService.findForJdbc(sql);
                if (null != data && data.size() > 0) {
                    for (Map map : data) {
                        Map<String, Object> mineInfoObj = new HashMap<>();
                        mineInfoObj.put("mine", map.get("mine").toString());
                        mineInfoObj.put("count", map.get("count").toString());
                        mineInfoObj.put("mineName", map.get("mineName").toString());
                        list.add(mineInfoObj);
                    }
                }
                redisClient.set(redisKey, CommonUtil.gson.toJson(list), RedisConstants.HALF_HOUR);
            } else {
                list = CommonUtil.gson.fromJson(value, new TypeToken<List<Map<String, Object>>>() {
                }.getType());
            }
            list.sort((m1, m2) -> {
                if (StringUtils.equals(sortTyp, "desc")) {
                    return Integer.valueOf(m2.get("count").toString()).compareTo(Integer.valueOf(m1.get("count").toString()));
                } else {
                    return Integer.valueOf(m1.get("count").toString()).compareTo(Integer.valueOf(m2.get("count").toString()));
                }
            });
            j.setObj(list);
            return j;
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            j.setMsg("服务器内部错误");
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 数据联网统计
     */
    @RequestMapping(params = "mineData")
    @ResponseBody
    public AjaxJson mineData(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String token = request.getParameter("token");
        boolean result = verify(token);
        if (!result) {
            j.setMsg("非法请求");
            j.setSuccess(false);
            return j;
        }
        try {
            String id = request.getParameter("id");
            if (StringUtils.isBlank(id)) {
                j.setMsg("缺少请求参数id");
                j.setSuccess(false);
                return j;
            }
            // 根据类型查询遍历煤矿
            List<String> mineList = new ArrayList<>();

            TBOrgEntity orgEntity = systemService.getEntity(TBOrgEntity.class, id);
            if (orgEntity==null) {
                j.setMsg("参数id不存在");
                j.setSuccess(false);
                return j;
            }
            if ("1".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org_networking where unit_type='3' order by sort_no+0 asc ");
            } else if ("2".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org_networking where parent_id='" + id + "' order by sort_no+0 asc ");
            } else {
                mineList = systemService.findListbySql("select id from t_b_org_networking where id='" + id + "' order by sort_no+0 asc ");
            }
            String mineListStr = JSON.toJSONString(mineList);
            //查询每个矿的数据
            List<Map<String, Object>> list = new ArrayList<>();
            Map<String, Object> map = new HashMap<>();
            //全部
            String sql = "select count(1) count from t_b_org_networking  where unit_type='3' and (id in (" + mineListStr.substring(1, mineListStr.length() - 1) + ") or parent_id='ff80808171f309530171f3108eae0003')";
            List<String> allCount = systemService.findListbySql(sql);
            map.put("allCount", allCount.get(0));
            //应接
            String sql2 = "select count(1) count2 from t_b_org_networking  where unit_type='3' and (id in (" + mineListStr.substring(1, mineListStr.length() - 1) + ") or parent_id='ff80808171f309530171f3108eae0003') and  is_networking in('0','2')";
            List<String> count2 = systemService.findListbySql(sql2);
            map.put("count2", count2.get(0));
            //已接
            String sql3 = "select count(1) count3 from t_b_org_networking  where unit_type='3' and (id in (" + mineListStr.substring(1, mineListStr.length() - 1) + ") or parent_id='ff80808171f309530171f3108eae0003') and  is_networking='0'";
            List<String> count3 = systemService.findListbySql(sql3);
            map.put("count3", count3.get(0));
            //未接入
            String sql4 = "select count(1) count4 from t_b_org_networking  where unit_type='3' and (id in (" + mineListStr.substring(1, mineListStr.length() - 1) + ") or parent_id='ff80808171f309530171f3108eae0003') and  is_networking in('1','2')";
            List<String> count4 = systemService.findListbySql(sql4);
            map.put("count4", count4.get(0));
            list.add(map);
            j.setObj(list);
            return j;
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            j.setMsg("服务器内部错误");
            j.setSuccess(false);
        }
        return j;
    }

    /**
     * 数据联网统计
     */
    @RequestMapping(params = "netWorkData")
    @ResponseBody
    public AjaxJson netWorkData(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String token = request.getParameter("token");
        boolean result = verify(token);
        if (!result) {
            j.setMsg("非法请求");
            j.setSuccess(false);
            return j;
        }
        Map<String, Object> attributes = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String id = request.getParameter("id");
            if (StringUtils.isBlank(id)) {
                j.setMsg("缺少请求参数id");
                j.setSuccess(false);
                return j;
            }
            // 根据类型查询遍历煤矿
            List<String> mineList = new ArrayList<>();
            TBOrgEntity orgEntity = systemService.getEntity(TBOrgEntity.class, id);
            if (orgEntity==null) {
                j.setMsg("参数id不存在");
                j.setSuccess(false);
                return j;
            }
            if ("1".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org where unit_type='3' order by sort_no+0 asc ");
            } else if ("2".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org where parent_id='"+id+"' order by sort_no+0 asc ");
            } else {
                mineList = systemService.findListbySql("select id from t_b_org where id='"+id+"' order by sort_no+0 asc ");
            }
            //查询联网状态
            CriteriaQuery cq = new CriteriaQuery(TbOrgEntity.class);
            cq.eq("unitType", "3");
            cq.in("id", mineList.toArray());
            Map orderMap = new HashMap();
            orderMap.put("parentId", SortDirection.asc);
            orderMap.put("isNetwork", SortDirection.desc);
            cq.setOrder(orderMap);
            cq.add();
            List<TbOrgEntity> list = systemService.getListByCriteriaQuery(cq, false);
            List<Map<String,Object>> area=new ArrayList<>();
            systemService.getSession().clear();
            if (list != null && list.size() > 0) {
                for (TbOrgEntity entity : list) {
                    Map map=new HashMap();
                    map.put("mineName",entity.getMineName());
                    switch (entity.getIsNetwork()) {
                        case "0":
                            map.put("isNetwork","网络正常");
                            break;
                        case "1":
                            map.put("isNetwork","网络中断");
                            break;
                    }
                    try {
                        if (entity.getNetDate() == null) {
                            map.put("netDateTemp","暂未联网");
                        } else {
                            String  date= sdf.format(entity.getNetDate());
                            map.put("netDateTemp",date);
                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    area.add(map);
                }

            }
            j.setObj(area);
            return j;
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            j.setMsg("服务器内部错误");
            j.setSuccess(false);
        }
        j.setAttributes(attributes);
        return j;
    }

    /**
     * 通报信息
     */
    @RequestMapping(params = "noticeData")
    @ResponseBody
    public AjaxJson noticeData(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String token = request.getParameter("token");
        boolean result = verify(token);
        if (!result) {
            j.setMsg("非法请求");
            j.setSuccess(false);
            return j;
        }
        Map<String, Object> attributes = new HashMap<>();
        try {
            String id = request.getParameter("id");
            String queryBeginDate = request.getParameter("queryBeginDate");
            if (StringUtils.isBlank(id)) {
                j.setMsg("缺少请求参数id");
                j.setSuccess(false);
                return j;
            }
            // 根据类型查询遍历煤矿
            List<String> mineList = new ArrayList<>();
            TBOrgEntity orgEntity = systemService.getEntity(TBOrgEntity.class, id);
            if (orgEntity==null) {
                j.setMsg("参数id不存在");
                j.setSuccess(false);
                return j;
            }
            if ("1".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org where unit_type='3' order by sort_no+0 asc ");
            } else if ("2".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org where parent_id='"+id+"' order by sort_no+0 asc ");
            } else {
                mineList = systemService.findListbySql("select id from t_b_org where id='"+id+"' order by sort_no+0 asc ");
            }
            //查询联网状态
            CriteriaQuery cq = new CriteriaQuery(TBCirculateAnotice.class);
            cq.in("mineId", mineList.toArray());
            if (StringUtil.isNotEmpty(queryBeginDate)) {
                cq.eq("month", queryBeginDate);
            }
            Map orderMap = new HashMap();
            cq.setOrder(orderMap);
            cq.add();
            List<TBCirculateAnotice> list = systemService.getListByCriteriaQuery(cq, false);
            j.setObj(list);
            return j;
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            j.setMsg("服务器内部错误");
            j.setSuccess(false);
        }
        j.setAttributes(attributes);
        return j;
    }

    /**
     * 文件下发情况
     */
    @RequestMapping(params = "issueHappening")
    @ResponseBody
    public AjaxJson issueHappening(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String token = request.getParameter("token");
        boolean result = verify(token);
        if (!result) {
            j.setMsg("非法请求");
            j.setSuccess(false);
            return j;
        }
        try {
            // 根据类型查询遍历煤矿
            List<String> mineList = new ArrayList<>();
            mineList = systemService.findListbySql("select id from t_b_org where unit_type='3' order by sort_no+0 asc ");

            String mineListStr = JSON.toJSONString(mineList);
            // 查询文件列表
            String sql = " select mine_id mineId,issue_status issueStatus, receive_status receiveStatus from t_b_file_issuance_record where mine_id in (" + mineListStr.substring(1, mineListStr.length() - 1) + ") ";
            List<Map<String, Object>> list = systemService.findForJdbc(sql);
            Map<String, Map<String, Object>> map = new HashMap<>();
            AtomicInteger noIssue = new AtomicInteger(0);
            AtomicInteger hasIssue = new AtomicInteger(0);
            AtomicInteger noReceive = new AtomicInteger(0);
            AtomicInteger hasReceive = new AtomicInteger(0);
            if (list.size() > 0) {
                for (Map<String, Object> objectMap : list) {
                    String mineId = objectMap.get("mineId").toString();
                    map.put(mineId, objectMap);
                    String issueStatus = objectMap.get("issueStatus").toString();
                    String receiveStatus = objectMap.get("receiveStatus").toString();
                    if ("1".equals(issueStatus)) {
                        hasIssue.addAndGet(1);
                    } else {
                        noIssue.addAndGet(1);
                    }
                    if ("1".equals(receiveStatus)) {
                        hasReceive.addAndGet(1);
                    } else {
                        noReceive.addAndGet(1);
                    }
                }
            }
            Map<String, Integer> data = new HashMap<>();
            data.put("noIssue", noIssue.intValue());
            data.put("hasIssue", hasIssue.intValue());
            data.put("noReceive", noReceive.intValue());
            data.put("hasReceive", hasReceive.intValue());
            j.setObj(data);
            j.setMsg("请求成功");
        }catch (Exception e) {
                LogUtil.error(e.getMessage());
                j.setMsg("服务器内部错误");
                j.setSuccess(false);
            }
        return j;
    }

    /**
     * 隐患闭环率统计
     */
    @RequestMapping(params = "hiddenclosePiechart")
    @ResponseBody
    public AjaxJson hiddenclosePiechart(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String token = request.getParameter("token");
        boolean result = verify(token);
        if (!result) {
            j.setMsg("非法请求");
            j.setSuccess(false);
            return j;
        }
        Map<String, Object> attributes = new HashMap<>();
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
        try {
            String id = request.getParameter("id");
            if (StringUtils.isBlank(id)) {
                j.setMsg("缺少请求参数id");
                j.setSuccess(false);
                return j;
            }
            // 根据类型查询遍历煤矿
            List<String> mineList = new ArrayList<>();
            TBOrgEntity orgEntity = systemService.getEntity(TBOrgEntity.class, id);
            if (orgEntity==null) {
                j.setMsg("参数id不存在");
                j.setSuccess(false);
                return j;
            }
            if ("1".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org where unit_type='3' order by sort_no+0 asc ");
            } else if ("2".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org where parent_id='"+id+"' order by sort_no+0 asc ");
            } else {
                mineList = systemService.findListbySql("select id from t_b_org where id='"+id+"' order by sort_no+0 asc ");
            }


            String mineListStr = JSON.toJSONString(mineList);
            String sql = "\n" +
                    "select a.hiddenCount,d.hiddenCloseCount from \n" +
                    "(select  count(1) as hiddenCount from   t_b_hidden_danger_exam e left join t_b_hidden_danger_handle h on e.id=h.hidden_id where e.is_delete !='1' and h.handle_status !='0' and e.belong_mine in (" + mineListStr.substring(1, mineListStr.length() - 1) + ")) as a,\n" +
                    "(select  count(1) as hiddenCloseCount from   t_b_hidden_danger_exam e left join t_b_hidden_danger_handle h on e.id=h.hidden_id where e.is_delete !='1' and h.handle_status ='4' and e.belong_mine in (" + mineListStr.substring(1, mineListStr.length() - 1) + ")) as d\t\t";
            List<Map<String, Object>> data = systemService.findForJdbc(sql);
            //计算整改率
            BigDecimal a = new BigDecimal(data.get(0).get("hiddenCount").toString());
            BigDecimal b = new BigDecimal(data.get(0).get("hiddenCloseCount").toString());
            JSONObject mineInfoObj = new JSONObject();
            if (a.compareTo(BigDecimal.ZERO) == 0) {
                mineInfoObj.put("closeRatio", 100);
                mineInfoObj.put("unCloseRatio", 100 - 100);
            } else {
                BigDecimal ratio = b.divide(a, 4, BigDecimal.ROUND_HALF_UP);
                mineInfoObj.put("closeRatio", percent.format(ratio.doubleValue()));
                mineInfoObj.put("unCloseRatio", percent.format(new BigDecimal(1).subtract(ratio)));
                mineInfoObj.put("close", ratio.multiply(new BigDecimal(100)));
                mineInfoObj.put("unClose", (new BigDecimal(1).subtract(ratio)).multiply(new BigDecimal(100)));
            }
            attributes.put("series", mineInfoObj);
            j.setAttributes(attributes);
            return j;
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            j.setMsg("服务器内部错误");
            j.setSuccess(false);
        }
        j.setAttributes(attributes);
        return j;
    }

    /**
     * 三违分类分布
     */
    @RequestMapping(params = "threePiechart")
    @ResponseBody
    public AjaxJson threePiechart(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String token = request.getParameter("token");
        boolean result = verify(token);
        if (!result) {
            j.setMsg("非法请求");
            j.setSuccess(false);
            return j;
        }
        Map<String, Object> attributes = new HashMap<>();
        try {
            String id = request.getParameter("id");
            if (StringUtils.isBlank(id)) {
                j.setMsg("缺少请求参数id");
                j.setSuccess(false);
                return j;
            }
            // 根据类型查询遍历煤矿
            List<String> mineList = new ArrayList<>();
            TBOrgEntity orgEntity = systemService.getEntity(TBOrgEntity.class, id);
            if (orgEntity==null) {
                j.setMsg("参数id不存在");
                j.setSuccess(false);
                return j;
            }
            if ("1".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org where unit_type='3' order by sort_no+0 asc ");
            } else if ("2".equals(orgEntity.getUnitType())) {
                mineList = systemService.findListbySql("select id from t_b_org where parent_id='"+id+"' order by sort_no+0 asc ");
            } else {
                mineList = systemService.findListbySql("select id from t_b_org where id='"+id+"' order by sort_no+0 asc ");
            }
            String mineListStr = JSON.toJSONString(mineList);
            String sql = "select count(1) as value,t.typename as name,t.typecode from t_b_three_violations  v\n" +
                    "left join  t_s_type t  on v.vio_category=t.typecode\n" +
                    "left join t_s_typegroup  p  on  t.typegroupid=p.id\n" +
                    "where p.typegroupcode='vio_category' and v.is_delete='0' and v.belong_mine in (" + mineListStr.substring(1, mineListStr.length() - 1) + ")  GROUP BY v.vio_category";
            List<Map<String, Object>> dataList = systemService.findForJdbc(sql);
            attributes.put("series", dataList);
            j.setAttributes(attributes);
            return j;
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            j.setMsg("服务器内部错误");
            j.setSuccess(false);
        }
        j.setAttributes(attributes);
        return j;
    }

    /**
     * 用户活跃记录
     */
    @RequestMapping(params = "userActivityRecord2")
    @ResponseBody
    public AjaxJson UserActivityRecord2(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String token = request.getParameter("token");
        boolean result = verify(token);
        if (!result) {
            j.setMsg("非法请求");
            j.setSuccess(false);
            return j;
        }

        try {
            //排序 asc 正序 desc 倒序
            String sortTyp = request.getParameter("sortTyp");
            String queryBeginDate = request.getParameter("queryBeginDate");
            String id = request.getParameter("id");
            if (StringUtils.isBlank(id)) {
                j.setMsg("缺少请求参数id");
                j.setSuccess(false);
                return j;
            }
            TBOrgEntity orgEntity = systemService.getEntity(TBOrgEntity.class, id);
            if (orgEntity==null) {
                j.setMsg("参数id不存在");
                j.setSuccess(false);
                return j;
            }
            String redisKey = String.format(RedisConstants.SESSION_ACTIVITY_RECORD_COALMINE, id);
            String logSql = "";
            if (StringUtils.isNotEmpty(queryBeginDate)) {
                logSql = "AND DATE_FORMAT(g.operatetime,'%Y-%m') = '" + queryBeginDate + "' ";
                redisKey = String.format(RedisConstants.SESSION_ACTIVITY_RECORD_COALMINE, id + "_" + queryBeginDate);
            }
            // 根据类型查询遍历煤矿
            List<Map<String, Object>> list = new ArrayList<>();
            String value = redisClient.get(redisKey);
            if (StringUtils.isBlank(value)) {
                String sql = "select count(1)count,d.departname from  t_s_log  g  \n" +
                        "left  join t_s_user_org  uo  on g.userid=uo.user_id\n" +
                        "left join t_s_depart d  on uo.org_id=d.ID \n" +
                        "where g.loglevel='1' and  g.belong_mine='" + id + "' " + logSql + " and d.delete_flag='0' GROUP BY d.ID";
                List<Map<String, Object>> data = systemService.findForJdbc(sql);
                if (null != data && data.size() > 0) {
                    for (Map map : data) {
                        Map<String, Object> mineInfoObj = new HashMap<>();
                        mineInfoObj.put("count", map.get("count").toString());
                        mineInfoObj.put("mineName", map.get("departname").toString());
                        list.add(mineInfoObj);
                    }
                }
                redisClient.set(redisKey, CommonUtil.gson.toJson(list), RedisConstants.HALF_HOUR);
            } else {
                list = CommonUtil.gson.fromJson(value, new TypeToken<List<Map<String, Object>>>() {
                }.getType());
            }
            list.sort((m1, m2) -> {
                if (StringUtils.equals(sortTyp, "desc")) {
                    return Integer.valueOf(m2.get("count").toString()).compareTo(Integer.valueOf(m1.get("count").toString()));
                } else {
                    return Integer.valueOf(m1.get("count").toString()).compareTo(Integer.valueOf(m2.get("count").toString()));
                }
            });
            j.setObj(list);
            return j;
        } catch (Exception e) {
            LogUtil.error(e.getMessage());
            j.setMsg("服务器内部错误");
            j.setSuccess(false);

        }
        return j;
    }

    /**
     * 效验token
     */
    public static boolean verify(String token) {
        boolean result = false;
        if (StringUtils.isBlank(token)) {
            return result;
        }
        String temp = token.substring(14);
        temp = temp.replaceAll(" ", "+");
        String outStr = RSAUtils.decrypt(temp, RSAUtils.privateKey);
        String now = token.substring(0, 14);
        if (now.equals(outStr)) {
            result = true;
        }
        return result;
    }
}

