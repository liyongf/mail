package com.sdzk.buss.web.aqbzh.dao.impl;

import com.sdzk.buss.web.aqbzh.dao.QualityControlRequirementsMapper;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlModule;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlProject;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlRequirements;
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

@Repository(value = "qualityControlRequirementsMapper")
public class QualityControlRequirementsMapperImpl implements QualityControlRequirementsMapper {
    @Autowired
    private SystemService systemService;
    public ICommonDao commonDao = null;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    public void setCommonDao(ICommonDao commonDao) {
        this.commonDao = commonDao;
    }

    String qualityControlModuleColumn = " quality_control_requirements.id as id," +
            "    quality_control_requirements.requirements as requirements," +
            "    quality_control_requirements.standard_score as standardScore," +
            "    quality_control_requirements.score_method as scoreMethod," +
            "    quality_control_requirements.level as level," +
            "    quality_control_requirements.level_type as levelType," +
            "    quality_control_requirements.a1 as a1," +
            "    quality_control_requirements.a2 as a2," +
            "    quality_control_requirements.a3 as a3," +
            "    quality_control_requirements.a4 as a4," +
            "    quality_control_requirements.a5 as a5," +
            "    quality_control_requirements.a6 as a6," +
            "    quality_control_requirements.a7 as a7";



    @Override
    public List<QualityControlRequirements> findByPage(PageQuery pageQuery, QualityControlRequirements qualityControlRequirements) {
       String sql = "     SELECT  "+qualityControlModuleColumn+"    FROM quality_control_requirements" +
               "        WHERE 1=1";

       if (StringUtil.isNotBlank(qualityControlRequirements.getRequirements())) {
           sql += " and requirements= '"+qualityControlRequirements.getRequirements()+"'";
       }
        if (StringUtil.isNotBlank(qualityControlRequirements.getStandardScore())) {
            sql += " and standard_score= '"+qualityControlRequirements.getStandardScore()+"'";
        }

        if (StringUtil.isNotBlank(qualityControlRequirements.getScoreMethod())) {
            sql += " and score_method= '"+qualityControlRequirements.getScoreMethod()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getLevel())) {
            sql += " and level= '"+qualityControlRequirements.getLevel()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getLevelType())) {
            sql += " and level_type= '"+qualityControlRequirements.getLevelType()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getA1())) {
            sql += " and a1= '"+qualityControlRequirements.getA1()+"'";
        }

        if (StringUtil.isNotBlank(qualityControlRequirements.getA2())) {
            sql += " and a2= '"+qualityControlRequirements.getA2()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getA3())) {
            sql += " and a3= '"+qualityControlRequirements.getA3()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getA4())) {
            sql += " and a4= '"+qualityControlRequirements.getA4()+"'";
        }

        if (StringUtil.isNotBlank(qualityControlRequirements.getA5())) {
            sql += " and a5= '"+qualityControlRequirements.getA5()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getA6())) {
            sql += " and a6= '"+qualityControlRequirements.getA6()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getA7())) {
            sql += " and a7= '"+qualityControlRequirements.getA7()+"'";
        }
        List<QualityControlRequirements> list = new ArrayList<>();
        if (pageQuery != null && pageQuery.getPageNo()>0 && pageQuery.getPageSize()>0) {
            list = commonDao.findObjForJdbc(sql,pageQuery.getPageNo(),pageQuery.getPageSize(),QualityControlRequirements.class);
        } else {
            list =  jdbcTemplate.query(sql, new BeanPropertyRowMapper(QualityControlRequirements.class));
        }
        return list;
    }
    @Override
    public PageInfo<QualityControlRequirements> findByPage2(PageQuery pageQuery, QualityControlRequirements qualityControlRequirements) {


        String sqlHead = " SELECT   "+qualityControlModuleColumn+"";
        String countSql = " SELECT   count(1) ";

        String sql = "    FROM quality_control_requirements  WHERE 1=1";
        if (StringUtil.isNotBlank(qualityControlRequirements.getRequirements())) {
            sql += " and requirements= '"+qualityControlRequirements.getRequirements()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getStandardScore())) {
            sql += " and standard_score= '"+qualityControlRequirements.getStandardScore()+"'";
        }

        if (StringUtil.isNotBlank(qualityControlRequirements.getScoreMethod())) {
            sql += " and score_method= '"+qualityControlRequirements.getScoreMethod()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getLevel())) {
            sql += " and level= '"+qualityControlRequirements.getLevel()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getLevelType())) {
            sql += " and level_type= '"+qualityControlRequirements.getLevelType()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getA1())) {
            sql += " and a1= '"+qualityControlRequirements.getA1()+"'";
        }

        if (StringUtil.isNotBlank(qualityControlRequirements.getA2())) {
            sql += " and a2= '"+qualityControlRequirements.getA2()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getA3())) {
            sql += " and a3= '"+qualityControlRequirements.getA3()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getA4())) {
            sql += " and a4= '"+qualityControlRequirements.getA4()+"'";
        }

        if (StringUtil.isNotBlank(qualityControlRequirements.getA5())) {
            sql += " and a5= '"+qualityControlRequirements.getA5()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getA6())) {
            sql += " and a6= '"+qualityControlRequirements.getA6()+"'";
        }
        if (StringUtil.isNotBlank(qualityControlRequirements.getA7())) {
            sql += " and a7= '"+qualityControlRequirements.getA7()+"'";
        }
        List<QualityControlRequirements> list = new ArrayList<>();
        if (pageQuery != null && pageQuery.getPageNo()>0 && pageQuery.getPageSize()>0) {
            list = commonDao.findObjForJdbc(sql,pageQuery.getPageNo(),pageQuery.getPageSize(),QualityControlRequirements.class);
        } else {
            list =   jdbcTemplate.query(sqlHead+sql, new BeanPropertyRowMapper(QualityControlRequirements.class));
        }
        long count = commonDao.getCountForJdbc(countSql+sql);
        PageInfo<QualityControlRequirements>  pageInfo = new PageInfo();
        pageInfo.setList(list);
        pageInfo.setTotal(count);
        pageInfo.setPageSize(pageQuery.getPageSize());
        pageInfo.setPageNum(pageQuery.getPageNo());
        return pageInfo;
    }
    @Override
    public QualityControlRequirements findById(Integer id) {
        return  commonDao.getEntity(QualityControlRequirements.class,id);
    }

    @Override
    public void insert(QualityControlRequirements qualityControlRequirements) {
        commonDao.save(qualityControlRequirements);
    }

    @Override
    public void update(QualityControlRequirements qualityControlRequirements) {
       // commonDao.updateEntitie(qualityControlRequirements);
        QualityControlRequirements t = systemService.get(QualityControlRequirements.class, qualityControlRequirements.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(qualityControlRequirements, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        systemService.saveOrUpdate(t);

    }

    @Override
    public void updateByIds(List<String> ids, Integer a3) {
        String sql = "         update quality_control_requirements" +
                "        set a3='"+a3+"'" +
                "        where id in(" ;
        String idLists = "";
        for(String id : ids ) {
            idLists += "','"+id;
        }
        sql +=  idLists.substring(2)+ "')";
         commonDao.updateBySqlString(sql);
    }

    @Override
    public void updateA4ByIds(List<String> ids, String a4) {
        String sql = "         update quality_control_requirements" +
                "        set a4='"+a4+"'" +
                "        where id in(" ;
        String idLists = "";
        for(String id : ids ) {
            idLists += "','"+id;
        }
        sql +=  idLists.substring(2)+ "')";
        commonDao.updateBySqlString(sql);
    }

    @Override
    public void updateA5ByIds(List<String> ids, String a5) {
        String sql = "         update quality_control_requirements" +
                "        set a5='"+a5+"'" +
                "        where id in(" ;
        String idLists = "";
        for(String id : ids ) {
            idLists += "','"+id;
        }
        sql +=  idLists.substring(2)+ "')";
        commonDao.updateBySqlString(sql);
    }

    @Override
    public void deleteById(Integer id) {
        //commonDao.deleteEntityById(QualityControlRequirements.class,id);
        systemService.delete(systemService.get(QualityControlRequirements.class,id));

    }
}
