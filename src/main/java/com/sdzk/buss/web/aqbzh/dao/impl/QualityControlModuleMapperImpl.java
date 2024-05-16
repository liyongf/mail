package com.sdzk.buss.web.aqbzh.dao.impl;

import com.sdzk.buss.web.aqbzh.dao.QualityControlModuleMapper;
import com.sdzk.buss.web.aqbzh.pojo.bo.HistoricalRecord;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlModule;
import com.sdzk.buss.web.aqbzh.pojo.dto.QualityControlTableIds;
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

@Repository(value = "qualityControlModuleMapper")
public class QualityControlModuleMapperImpl implements QualityControlModuleMapper {
    @Autowired
    private SystemService systemService;
    public ICommonDao commonDao = null;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    public void setCommonDao(ICommonDao commonDao) {
        this.commonDao = commonDao;
    }

    String qualityControlModuleColumn = " quality_control_module.id as id," +
            "    quality_control_module.module_name as moduleName," +
            "    quality_control_module.parent_id as parentId," +
            "    quality_control_module.is_asses as isAsses," +
            "    quality_control_module.type_code as typeCode," +
            "    quality_control_module.score as score," +
            "    quality_control_module.the_weight as theWeight," +
            "    quality_control_module.sort as sort," +
            "    quality_control_module.a1 as a1," +
            "    quality_control_module.a2 as a2," +
            "    quality_control_module.a3 as a3," +
            "    quality_control_module.a4 as a4";

    @Override
    public List<QualityControlModule> findByPage(PageQuery pageQuery, QualityControlModule qualityControlModule) {
        String sql = "SELECT "+qualityControlModuleColumn+"  FROM quality_control_module  WHERE 1=1 ";
        if(StringUtil.isNotBlank(qualityControlModule.getModuleName())) {
            sql += " and module_name like concat('%',"+qualityControlModule.getModuleName()+",'%') ";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getParentId())) {
            sql += "  and parent_id='"+qualityControlModule.getParentId()+"' ";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getIsAsses())) {
            sql += " and  and is_asses='"+qualityControlModule.getIsAsses()+"'  ";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getTypeCode())) {
            sql += " and type_code='"+qualityControlModule.getTypeCode()+"'";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getScore())) {
            sql += " and score='"+qualityControlModule.getScore()+"'";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getTheWeight())) {
            sql += " and the_weight='"+qualityControlModule.getTheWeight()+"'";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getSort())) {
            sql += " and sort='"+qualityControlModule.getSort()+"'";
        }

        if(StringUtil.isNotBlank(qualityControlModule.getA1())) {
            sql += " and a1='"+qualityControlModule.getA1()+"'";
        }

        if(StringUtil.isNotBlank(qualityControlModule.getA2())) {
            sql += " and a2='"+qualityControlModule.getA2()+"'";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getA3())) {
            sql += " and a3='"+qualityControlModule.getA3()+"'";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getA4())) {
            sql += " and a4='"+qualityControlModule.getA4()+"'";
        }
        List<QualityControlModule> list = new ArrayList<>();
        if (pageQuery != null && pageQuery.getPageNo()>0 && pageQuery.getPageSize()>0) {
            list =    commonDao.findObjForJdbc(sql,pageQuery.getPageNo(),pageQuery.getPageSize(),QualityControlModule.class);
        } else {
            list =  jdbcTemplate.query(sql, new BeanPropertyRowMapper(QualityControlModule.class));
        }
        return list;
    }
    @Override
    public PageInfo<QualityControlModule> findByPage2(PageQuery pageQuery, QualityControlModule qualityControlModule) {
        String sqlHead = " SELECT   "+qualityControlModuleColumn+"";
        String countSql = " SELECT   count(1) ";

        String sql = "   FROM quality_control_module  WHERE 1=1 ";
        if(StringUtil.isNotBlank(qualityControlModule.getModuleName())) {
            sql += " and module_name like concat('%',"+qualityControlModule.getModuleName()+",'%') ";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getParentId())) {
            sql += "  and parent_id='"+qualityControlModule.getParentId()+"' ";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getIsAsses())) {
            sql += " and  and is_asses= '"+qualityControlModule.getIsAsses()+"'  ";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getTypeCode())) {
            sql += " and type_code='"+qualityControlModule.getTypeCode()+"'";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getScore())) {
            sql += " and score='"+qualityControlModule.getScore()+"'";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getTheWeight())) {
            sql += " and the_weight='"+qualityControlModule.getTheWeight()+"'";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getSort())) {
            sql += " and sort='"+qualityControlModule.getSort()+"'";
        }

        if(StringUtil.isNotBlank(qualityControlModule.getA1())) {
            sql += " and a1='"+qualityControlModule.getA1()+"'";
        }

        if(StringUtil.isNotBlank(qualityControlModule.getA2())) {
            sql += " and a2='"+qualityControlModule.getA2()+"'";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getA3())) {
            sql += " and a3='"+qualityControlModule.getA3()+"'";
        }
        if(StringUtil.isNotBlank(qualityControlModule.getA4())) {
            sql += " and a4='"+qualityControlModule.getA4()+"'";
        }
        List<QualityControlModule> list = new ArrayList<>();
        if (pageQuery != null && pageQuery.getPageNo()>0 && pageQuery.getPageSize()>0) {
            list =    commonDao.findObjForJdbc(sql,pageQuery.getPageNo(),pageQuery.getPageSize(),QualityControlModule.class);
        } else {
            list =   jdbcTemplate.query(sqlHead+sql, new BeanPropertyRowMapper(QualityControlModule.class));
        }
        long count = commonDao.getCountForJdbc(countSql+sql);
        PageInfo<QualityControlModule>  pageInfo = new PageInfo();
        pageInfo.setList(list);
        pageInfo.setTotal(count);
        pageInfo.setPageSize(pageQuery.getPageSize());
        pageInfo.setPageNum(pageQuery.getPageNo());
        return pageInfo;
    }
    /**
     * 查询最后一级模块
     *
     * @return
     */
    @Override
    public List<QualityControlModule> findByList() {
        String sql = "        select   "+qualityControlModuleColumn+"" +
                "        from quality_control_module quality_control_module, quality_control_module quality_control_module2" +
                "        where quality_control_module.id = quality_control_module2.parent_id" +
                "        and quality_control_module.a2 = '1' and quality_control_module2.a2 = '2'" +
                "        and quality_control_module.is_asses = '1'" +
                "        group by quality_control_module2.parent_id ";
        List<QualityControlModule> list =  jdbcTemplate.query(sql, new BeanPropertyRowMapper(QualityControlModule.class));
        return list;
    }
    /**
     * 根据父id分组查询拼接后的表id
     * @return
     */
    @Override
    public List<QualityControlTableIds> findByPageIds() {
        String sql = "      SELECT parent_id,GROUP_CONCAT(id ORDER BY id DESC  SEPARATOR  ',' ) ids FROM quality_control_module" +
                "      WHERE a2 = 2 AND is_asses = '1'" +
                "      GROUP BY parent_id ";
        List<QualityControlTableIds> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(QualityControlTableIds.class));
        return list;
    }

    @Override
    public List<QualityControlModule> findByListIds(String year, String month, String typeCode, String duokuangCode) {
        String sql = "      select qcm.*, ta.play_score,ta.score scoreTa,ta.id totalAssesId, ta.a2 status, ta.a3 idList, ta.year, ta.month" +
                "        from total_asses" +
                "        ta,quality_control_module qcm" +
                "        where ta.module_id = qcm.id" ;

        if(StringUtil.isNotBlank(month)) {
            sql += " and ta.month ='"+month+"'";
        }
        if(StringUtil.isNotBlank(year)) {
            sql += " and ta.year ='"+year+"'";
        }
        if(StringUtil.isNotBlank(typeCode)) {
            sql += " and  (qcm.type_code = '"+typeCode+"' or qcm.type_code = '1')";
        }
        List<QualityControlModule> list =   jdbcTemplate.query(sql, new BeanPropertyRowMapper(QualityControlModule.class));
        return list;
    }

    @Override
    public QualityControlModule findById(Integer id) {
        return commonDao.getEntity(QualityControlModule.class,id);
    }

    @Override
    public List<QualityControlModule> findByIdList(List<Integer> idList) {
        String sql = "     SELECT "+qualityControlModuleColumn+"" +
                "        FROM quality_control_module" +
                "        WHERE id IN (" ;
        String ids = "";
        for(Integer id : idList ) {
            ids += "','"+id;
        }
        sql +=  ids.substring(2)+ "')";
        List<QualityControlModule> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(QualityControlModule.class));
        return list;
    }

    @Override
    public void insert(QualityControlModule qualityControlModule) {
        commonDao.save(qualityControlModule);
    }

    @Override
    public void update(QualityControlModule qualityControlModule) {
        //commonDao.updateEntitie(qualityControlModule);
        QualityControlModule t = systemService.get(QualityControlModule.class, qualityControlModule.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(qualityControlModule, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        systemService.saveOrUpdate(t);
    }

    @Override
    public int updateByParentId(Integer parentId, Integer isAsses) {
        String sql = "    update quality_control_module set is_asses="+isAsses+"" +
                "        where a1 like concat('%',"+parentId+",'%') ";
        return commonDao.updateBySqlString(sql);
    }

    @Override
    public void deleteById(Integer id) {
          //commonDao.deleteEntityById(QualityControlModule.class,id);;
          systemService.delete(systemService.get(QualityControlModule.class,id));
    }
}
