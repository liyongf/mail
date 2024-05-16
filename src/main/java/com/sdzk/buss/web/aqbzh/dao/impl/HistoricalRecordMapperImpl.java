package com.sdzk.buss.web.aqbzh.dao.impl;

import com.sdzk.buss.web.aqbzh.dao.HistoricalRecordMapper;
import com.sdzk.buss.web.aqbzh.pojo.bo.DetailsAsses;
import com.sdzk.buss.web.aqbzh.pojo.bo.HistoricalRecord;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import com.sdzk.buss.web.aqbzh.util.StringUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository(value = "historicalRecordMapper")
public class HistoricalRecordMapperImpl implements HistoricalRecordMapper {

    public ICommonDao commonDao = null;
    @Autowired
    private JdbcDao jdbcDao;
    @Resource
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SystemService systemService;

    @Resource
    public void setCommonDao(ICommonDao commonDao) {
        this.commonDao = commonDao;
    }

    String historicalRecordColumn = "historical_record.id as id," +
            "historical_record.total_asses_id as totalAssesId," +
            "historical_record.details_list_dto as detailsListDto," +
            "historical_record.score as score," +
            "historical_record.method_score as methodScore," +
            "historical_record.the_weight as theWeight," +
            "historical_record.module_id as moduleId," +
            "historical_record.a1 as a1," +
            "historical_record.a2 as a2," +
            "historical_record.a3 as a3," +
            "historical_record.a4 as a4," +
            "historical_record.a5 as a5," +
            "historical_record.a6 as a6 ";

    @Override
    public List<HistoricalRecord> findByPage(PageQuery pageQuery,HistoricalRecord historicalRecord) {


        String sql = " SELECT  "+historicalRecordColumn+" FROM historical_record    WHERE 1=1";
        if (StringUtil.isNotBlank( historicalRecord.getTotalAssesId())) {
            sql += " and total_asses_id = '"+ historicalRecord.getTotalAssesId()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getDetailsListDto())) {
            sql += " and details_list_dto = '"+ historicalRecord.getDetailsListDto()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getScore())) {
            sql += " and score = '"+ historicalRecord.getScore()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getMethodScore())) {
            sql += " and method_score = '"+ historicalRecord.getMethodScore()+"'";
        }

        if (StringUtil.isNotBlank( historicalRecord.getTheWeight())) {
            sql += " and the_weight = '"+ historicalRecord.getTheWeight()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getModuleId())) {
            sql += " and module_id = '"+ historicalRecord.getModuleId()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getA1())) {
            sql += " and a1 = '"+ historicalRecord.getA1()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getA2())) {
            sql += " and a2 = '"+ historicalRecord.getA2()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getA3())) {
            sql += " and a3 = '"+ historicalRecord.getA3()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getA4())) {
            sql += " and a4 = '"+ historicalRecord.getA4()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getA5())) {
            sql += " and a5 = '"+ historicalRecord.getA5()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getA6())) {
            sql += " and a6 = '"+ historicalRecord.getA6()+"'";
        }
        List<HistoricalRecord> list = new ArrayList<>();
        if (pageQuery != null && pageQuery.getPageNo()>0 && pageQuery.getPageSize()>0) {
            list =   commonDao.findObjForJdbc(sql,pageQuery.getPageNo(),pageQuery.getPageSize(),HistoricalRecord.class);
        } else {
            list =  jdbcTemplate.query(sql, new BeanPropertyRowMapper(HistoricalRecord.class));
         //   list =  commonDao.findListbySql(sql);
        }
        return list;
    }

    @Override
    public PageInfo<HistoricalRecord> findByPage2(PageQuery pageQuery, HistoricalRecord historicalRecord) {
        String sqlHead = " SELECT   "+historicalRecordColumn+"";
        String countSql = " SELECT   count(1) ";

        String sql = "  FROM historical_record    WHERE 1=1";
        if (StringUtil.isNotBlank( historicalRecord.getTotalAssesId())) {
            sql += " and total_asses_id = '"+ historicalRecord.getTotalAssesId()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getDetailsListDto())) {
            sql += " and details_list_dto = '"+ historicalRecord.getDetailsListDto()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getScore())) {
            sql += " and score = '"+ historicalRecord.getScore()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getMethodScore())) {
            sql += " and method_score = '"+ historicalRecord.getMethodScore()+"'";
        }

        if (StringUtil.isNotBlank( historicalRecord.getTheWeight())) {
            sql += " and the_weight = '"+ historicalRecord.getTheWeight()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getModuleId())) {
            sql += " and module_id = '"+ historicalRecord.getModuleId()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getA1())) {
            sql += " and a1 = '"+ historicalRecord.getA1()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getA2())) {
            sql += " and a2 = '"+ historicalRecord.getA2()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getA3())) {
            sql += " and a3 = '"+ historicalRecord.getA3()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getA4())) {
            sql += " and a4 = '"+ historicalRecord.getA4()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getA5())) {
            sql += " and a5 = '"+ historicalRecord.getA5()+"'";
        }
        if (StringUtil.isNotBlank( historicalRecord.getA6())) {
            sql += " and a6 = '"+ historicalRecord.getA6()+"'";
        }
        List<HistoricalRecord> list = new ArrayList<>();
        if (pageQuery != null && pageQuery.getPageNo()>0 && pageQuery.getPageSize()>0) {
            list =   commonDao.findObjForJdbc(sql,pageQuery.getPageNo(),pageQuery.getPageSize(),HistoricalRecord.class);
        } else {
            list =  jdbcTemplate.query(sqlHead+sql, new BeanPropertyRowMapper(HistoricalRecord.class));
        }
        long count = commonDao.getCountForJdbc(countSql+sql);
        PageInfo<HistoricalRecord>  pageInfo = new PageInfo();
        pageInfo.setList(list);
        pageInfo.setTotal(count);
        pageInfo.setPageSize(pageQuery.getPageSize());
        pageInfo.setPageNum(pageQuery.getPageNo());
        return pageInfo;
    }
    @Override
    public List<HistoricalRecord> findById(Integer id, List<Integer> moduleId, Integer a2) {
        String sql = " SELECT  "+historicalRecordColumn+" FROM historical_record    WHERE 1=1 and a2 = '"+a2+"'" +
                " and total_asses_id= '"+id+"' and module_id in ( ";
        String ids = "";
        for (int i=0;i< moduleId.size();i++){
            if (i==0) {
                ids = moduleId.get(i)+"";
            }else{
                ids += ","+moduleId.get(i)+"";
            }
        }
        sql += ids+") ";
        List<HistoricalRecord> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(HistoricalRecord.class));
        return list;
    }

    @Override
    public void insert(HistoricalRecord historicalRecord) {
        commonDao.save(historicalRecord);
    }

    @Override
    public void update(HistoricalRecord historicalRecord) {
        //commonDao.updateEntitie(historicalRecord);
        HistoricalRecord t = systemService.get(HistoricalRecord.class, historicalRecord.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(historicalRecord, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        systemService.saveOrUpdate(t);
    }

    @Override
    public void deleteById(Integer id) {
        //commonDao.deleteEntityById(HistoricalRecord.class,id);
        systemService.delete(systemService.get(HistoricalRecord.class,id));
    }

    @Override
    public void deleteByIds(List<Integer> idList) {
       String sql = "DELETE FROM historical_record  WHERE total_asses_id in (";
        String ids = "";
        for (int i=0;i< idList.size();i++){
            ids += "','"+idList.get(i);
        }
        sql += ids.substring(2)+"') ";
        commonDao.executeSql(sql);
    }
}
