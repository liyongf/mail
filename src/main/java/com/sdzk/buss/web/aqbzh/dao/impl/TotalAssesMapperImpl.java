package com.sdzk.buss.web.aqbzh.dao.impl;

import com.sdzk.buss.web.aqbzh.dao.TotalAssesMapper;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlRequirements;
import com.sdzk.buss.web.aqbzh.pojo.bo.TotalAsses;
import com.sdzk.buss.web.aqbzh.pojo.dto.QualityControlModuleDTO;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import com.sdzk.buss.web.aqbzh.util.StringUtil;
import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository(value = "totalAssesMapper")
public class TotalAssesMapperImpl implements TotalAssesMapper {

    @Autowired
    private SystemService systemService;
    public ICommonDao commonDao = null;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    public void setCommonDao(ICommonDao commonDao) {
        this.commonDao = commonDao;
    }

    String qualityControlModuleColumn = "     total_asses.id as id," +
            "    total_asses.year as year," +
            "    total_asses.month as month," +
            "    total_asses.module_id as moduleId," +
            "    total_asses.play_score as playScore," +
            "    total_asses.score as score," +
            "    total_asses.a1 as a1," +
            "    total_asses.a2 as a2," +
            "    total_asses.a3 as a3," +
            "    total_asses.a4 as a4," +
            "    total_asses.a5 as a5";


    @Override
    public List<TotalAsses> findByPage(PageQuery pageQuery, TotalAsses totalAsses) {
        String sql = "  SELECT year,month,module_id,play_score,a1,a2,a3,a4,a5,sum(score) score" +
                "        FROM total_asses" +
                "        WHERE 1=1" ;
        if(StringUtil.isNotBlank(totalAsses.getYear())) {
            sql += "  and year='"+totalAsses.getYear()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getMonth())) {
            sql += "  and month='"+totalAsses.getMonth()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getModuleId())) {
            sql += "  and module_id='"+totalAsses.getModuleId()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getPlayScore())) {
            sql += "  and play_score='"+totalAsses.getPlayScore()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getScore())) {
            sql += "  and score='"+totalAsses.getScore()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getA1())) {
            sql += "  and a1='"+totalAsses.getA1()+"'";
        }

        if(StringUtil.isNotBlank(totalAsses.getA2())) {
            sql += "  and a2='"+totalAsses.getA2()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getA3())) {
            sql += "  and a3='"+totalAsses.getA3()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getA4())) {
            sql += "  and a4='"+totalAsses.getA4()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getA5())) {
            sql += "  and a5='"+totalAsses.getA5()+"'";
        }
        sql +=         "        group by month" +
                "        order by a1";
        List<TotalAsses> list = new ArrayList<>();
        if (pageQuery != null && pageQuery.getPageNo()>0 && pageQuery.getPageSize()>0) {
            list =  commonDao.findObjForJdbc(sql,pageQuery.getPageNo(),pageQuery.getPageSize(),TotalAsses.class);
        } else {
            list =  jdbcTemplate.query(sql, new BeanPropertyRowMapper(TotalAsses.class));
        }
        return list;
    }
    @Override
    public PageInfo<TotalAsses> findByPage2(PageQuery pageQuery, TotalAsses totalAsses) {
        String sql = "  SELECT COUNT(1)    FROM total_asses   WHERE 1=1   " ;
        if(StringUtil.isNotBlank(totalAsses.getYear())) {
            sql += "  and year='"+totalAsses.getYear()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getMonth())) {
            sql += "  and month='"+totalAsses.getMonth()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getModuleId())) {
            sql += "  and module_id='"+totalAsses.getModuleId()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getPlayScore())) {
            sql += "  and play_score='"+totalAsses.getPlayScore()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getScore())) {
            sql += "  and score='"+totalAsses.getScore()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getA1())) {
            sql += "  and a1='"+totalAsses.getA1()+"'";
        }

        if(StringUtil.isNotBlank(totalAsses.getA2())) {
            sql += "  and a2='"+totalAsses.getA2()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getA3())) {
            sql += "  and a3='"+totalAsses.getA3()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getA4())) {
            sql += "  and a4='"+totalAsses.getA4()+"'";
        }
        if(StringUtil.isNotBlank(totalAsses.getA5())) {
            sql += "  and a5='"+totalAsses.getA5()+"'";
        }


        long count = commonDao.getCountForJdbc(sql);
        PageInfo<TotalAsses>  pageInfo = new PageInfo();
        pageInfo.setTotal(count);
        pageInfo.setPageSize(pageQuery.getPageSize());
        pageInfo.setPageNum(pageQuery.getPageNo());
        return pageInfo;
    }
    @Override
    public TotalAsses findById(Integer id) {
        return  commonDao.getEntity(TotalAsses.class,id);
    }

    @Override
    public Double findByData(String year, String month) {
        String sql = " select sum(score) as  scoresum from total_asses where year = '"+year+"' and month = '"+month+"' group by month";
        List<Map<String, Object>> resultList2 = commonDao.findForJdbc(sql);
        Object scoresum = resultList2.get(0).get("scoresum");
        if (scoresum != null) {
            //转double
            Double sum = Double.parseDouble(scoresum.toString());
            return sum;
        }
        return 0.00;
    }

    @Override
    public void insert(TotalAsses totalAsses) {
        commonDao.save(totalAsses);
    }

    @Override
    public void insertAll(List<TotalAsses> totalAsses) {
        if (totalAsses != null &&  totalAsses.size() >0 ) {
            String sql = " insert into total_asses " +
                    "        (year,month,module_id,play_score,score,a1,a2,a3,a4,a5)" +
                    "        values  ";

            for (TotalAsses totalAssess : totalAsses) {
                sql += "('"+totalAssess.getYear()+"','"+totalAssess.getMonth()+"','"+totalAssess.getModuleId()+"','"+totalAssess.getPlayScore()+"','"+totalAssess.getScore()+"'" +
                        "  ,'"+totalAssess.getA1()+"','"+totalAssess.getA2()+"','"+totalAssess.getA3()+"','"+totalAssess.getA4()+"','"+totalAssess.getA5()+"'),";
            }
            commonDao.executeSql(sql.substring(0,sql.length()-1).replaceAll("'null'","null"));
        }
    }

    @Override
    public void update(TotalAsses totalAsses) {
        //commonDao.updateEntitie(totalAsses);
        TotalAsses t = systemService.get(TotalAsses.class, totalAsses.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(totalAsses, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        systemService.saveOrUpdate(t);

    }

    @Override
    public void deleteById(Integer id) {
        //commonDao.deleteEntityById(TotalAsses.class,id);
        systemService.delete(systemService.get(TotalAsses.class,id));

    }

    @Override
    public void deleteByDate(String year, List<String> monthList) {
      String sql = "        DELETE FROM total_asses" +
              "        WHERE year = "+year+"" +
              "        and month in (" ;
        String idLists = "";
        for(String id : monthList ) {
            idLists += "','"+id;
        }
        idLists += "'";
        sql +=  idLists.substring(2)+ ")";
        commonDao.executeSql(sql);
    }

    @Override
    public List<Integer> selectByDate(String year, List<String> monthList) {
        String sql = "   select id as id from total_asses" +
                "        WHERE year = '"+year+"'" +
                "        and month in (";
        String idLists = "";
        for(String id : monthList ) {
            idLists += "','"+id;
        }
        idLists += "'";
        sql +=  idLists.substring(2)+ ")";
        List<Map<String, Object>> resultList2 = commonDao.findForJdbc(sql);
        List<Integer> list = new ArrayList<>();
        for(Map<String, Object> map : resultList2 ) {
            list.add(Integer.parseInt(map.get("id").toString()));
        }
        return list;
    }

    @Override
    public List<QualityControlModuleDTO> reportedScores() {
        String sql = "select id,year,month,a1,sum(score) score from total_asses" +
                "        group by year,month order by year,a1 ";
        List<QualityControlModuleDTO> list =  commonDao.findListbySql(sql);
        return list;
    }
}
