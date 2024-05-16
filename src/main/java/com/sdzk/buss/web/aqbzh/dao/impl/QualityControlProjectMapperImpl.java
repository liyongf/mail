package com.sdzk.buss.web.aqbzh.dao.impl;

import com.sdzk.buss.web.aqbzh.dao.QualityControlProjectMapper;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlModule;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlProject;
import com.sdzk.buss.web.aqbzh.pojo.dto.QualityControlProjectDTO;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import com.sdzk.buss.web.aqbzh.util.StringUtil;
import com.sdzk.buss.web.yearreport.entity.TBYearReportEntity;
import org.jeecgframework.core.common.dao.ICommonDao;
import org.jeecgframework.core.common.dao.jdbc.JdbcDao;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository(value = "qualityControlProjectMapper")
public class QualityControlProjectMapperImpl implements QualityControlProjectMapper {
    @Autowired
    private SystemService systemService;
    public ICommonDao commonDao = null;
    @Autowired
    private JdbcDao jdbcDao;
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Resource
    public void setCommonDao(ICommonDao commonDao) {
        this.commonDao = commonDao;
    }

    String qualityControlModuleColumn = " quality_control_project.id as id," +
            "    quality_control_project.project_name as projectName," +
            "    quality_control_project.total_score as totalScore," +
            "    quality_control_project.sort as sort," +
            "    quality_control_project.parent_id as parentId," +
            "    quality_control_project.tree_id as treeId," +
            "    quality_control_project.type as type," +
            "    quality_control_project.module_id as moduleId," +
            "    quality_control_project.status as status," +
            "    quality_control_project.a1 as a1," +
            "    quality_control_project.a2 as a2," +
            "    quality_control_project.a3 as a3," +
            "    quality_control_project.a4 as a4";


    //三表联查所有行数据
    @Override
    public List<QualityControlProjectDTO> findByList(Integer moduleId, Integer a3) {
        String sql = "      select qcr.id, qcr.requirements, qcr.standard_score, qcr.score_method, qcr.level, qcr.level_type,qcr.a2,qcr.a4,qcr.a5," +
                "        qcp.project_name,qcp.sort,qcp.parent_id,CONCAT(qcp.tree_id,'.',qcr.a1) as treeId,qcp.type,qcp.module_id,qcp.status,qcp.a1,qcp.a2 qcpA2," +
                "        qcm.id mid, qcm.module_name, qcm.type_code" +
                "        from quality_control_requirements qcr" +
                "        left join quality_control_project qcp on qcr.a1 = qcp.id" +
                "        left join quality_control_module qcm on qcp.module_id = qcm.id" +
                "        where 1=1 ";
            if (StringUtil.isNotBlank(a3)){
                sql += " and qcr.a3 = '1' and qcp.status = '1' and qcm.is_asses = '1'";
            }
            if (StringUtil.isNotBlank(moduleId)){
                sql += " and qcm.id = '"+moduleId+"'";
            }
        List<QualityControlProjectDTO> list =  jdbcTemplate.query(sql, new BeanPropertyRowMapper(QualityControlProjectDTO.class));
        return list;
    }

    @Override
    public List<QualityControlProject> findByPage(PageQuery pageQuery, QualityControlProject qualityControlProject) {
        String sql = " SELECT   "+qualityControlModuleColumn+"" +
                "        FROM quality_control_project" +
                "        WHERE 1=1 ";

        if (StringUtil.isNotBlank(qualityControlProject.getProjectName())) {
            sql += " and project_name='"+qualityControlProject.getProjectName()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getTotalScore())) {
            sql += " and total_score='"+qualityControlProject.getTotalScore()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getSort())) {
            sql += " and sort='"+qualityControlProject.getSort()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getParentId())) {
            sql += " and parent_id='"+qualityControlProject.getParentId()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getTreeId())) {
            sql += " and tree_id='"+qualityControlProject.getTreeId()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getType())) {
            sql += " and type='"+qualityControlProject.getType()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getModuleId())) {
            sql += " and module_id='"+qualityControlProject.getModuleId()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getStatus())) {
            sql += " and status='"+qualityControlProject.getStatus()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getA1())) {
            sql += " and a1='"+qualityControlProject.getA1()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getA2())) {
            sql += " and a2='"+qualityControlProject.getA2()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getA3())) {
            sql += " and a3='"+qualityControlProject.getA3()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getA4())) {
            sql += " and a4='"+qualityControlProject.getA4()+"' ";
        }
        List<QualityControlProject> list = new ArrayList<>();
        if (pageQuery != null && pageQuery.getPageNo()>0 && pageQuery.getPageSize()>0) {
            list =  commonDao.findObjForJdbc(sql,pageQuery.getPageNo(),pageQuery.getPageSize(),QualityControlProject.class);
        } else {
            list =   jdbcTemplate.query(sql, new BeanPropertyRowMapper(QualityControlProject.class));
        }
        return list;
    }
    @Override
    public PageInfo<QualityControlProject> findByPage2(PageQuery pageQuery, QualityControlProject qualityControlProject) {
        String sqlHead = " SELECT   "+qualityControlModuleColumn+"";
        String countSql = " SELECT   count(1) ";

        String sql =          "        FROM quality_control_project" +
                "        WHERE 1=1 ";

        if (StringUtil.isNotBlank(qualityControlProject.getProjectName())) {
            sql += " and project_name='"+qualityControlProject.getProjectName()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getTotalScore())) {
            sql += " and total_score='"+qualityControlProject.getTotalScore()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getSort())) {
            sql += " and sort='"+qualityControlProject.getSort()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getParentId())) {
            sql += " and parent_id='"+qualityControlProject.getParentId()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getTreeId())) {
            sql += " and tree_id='"+qualityControlProject.getTreeId()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getType())) {
            sql += " and type='"+qualityControlProject.getType()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getModuleId())) {
            sql += " and module_id='"+qualityControlProject.getModuleId()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getStatus())) {
            sql += " and status='"+qualityControlProject.getStatus()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getA1())) {
            sql += " and a1='"+qualityControlProject.getA1()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getA2())) {
            sql += " and a2='"+qualityControlProject.getA2()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getA3())) {
            sql += " and a3='"+qualityControlProject.getA3()+"' ";
        }
        if (StringUtil.isNotBlank(qualityControlProject.getA4())) {
            sql += " and a4='"+qualityControlProject.getA4()+"' ";
        }
        List<QualityControlProject> list = new ArrayList<>();
        if (pageQuery != null && pageQuery.getPageNo()>0 && pageQuery.getPageSize()>0) {
            list =  commonDao.findObjForJdbc(sqlHead+sql,pageQuery.getPageNo(),pageQuery.getPageSize(),QualityControlProject.class);
        } else {
            list=  jdbcTemplate.query(sqlHead+sql, new BeanPropertyRowMapper(QualityControlProject.class));
           // list =  commonDao.findListbySql(sqlHead+sql);
          //  list =  jdbcDao.find(sql,QualityControlProject.class,null);
        }

        long count = commonDao.getCountForJdbc(countSql+sql);
        PageInfo<QualityControlProject> pageInfo = new PageInfo();
        pageInfo.setList(list);
        pageInfo.setTotal(count);
        pageInfo.setPageSize(pageQuery.getPageSize());
        pageInfo.setPageNum(pageQuery.getPageNo());
        return pageInfo;
    }

    @Override
    public QualityControlProject findById(Integer id) {
        return  commonDao.getEntity(QualityControlProject.class,id);
    }

    @Override
    public void insert(QualityControlProject qualityControlProject) {
        commonDao.save(qualityControlProject);
    }

    @Override
    public void update(QualityControlProject qualityControlProject) {
        //commonDao.updateEntitie(qualityControlProject);
        QualityControlProject t = systemService.get(QualityControlProject.class, qualityControlProject.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(qualityControlProject, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        systemService.saveOrUpdate(t);
    }

    @Override
    public int updateByParentId(Integer parentId, Integer status, String a1, String a2) {
        String sql = " update quality_control_project set status='"+status+"',a1='"+a1+"',a2='"+a2+"'" +
                "   where tree_id like concat('%',"+parentId+",'%')";
        return commonDao.updateBySqlString(sql);
    }

    @Override
    public void deleteById(Integer id) {
        //commonDao.deleteEntityById(QualityControlProject.class,id);;
        systemService.delete(systemService.get(QualityControlProject.class,id));
    }
}
