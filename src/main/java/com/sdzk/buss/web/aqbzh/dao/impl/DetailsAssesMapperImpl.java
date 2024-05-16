package com.sdzk.buss.web.aqbzh.dao.impl;

import com.sdzk.buss.web.aqbzh.dao.DetailsAssesMapper;
import com.sdzk.buss.web.aqbzh.pojo.bo.DetailsAsses;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlModule;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlProject;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import com.sdzk.buss.web.aqbzh.util.StringUtil;
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
import java.util.Map;

@Repository(value = "detailsAssesMapper")
public class DetailsAssesMapperImpl implements DetailsAssesMapper {
    @Autowired
    private SystemService systemService;
    public ICommonDao commonDao = null;

    @Resource
    public void setCommonDao(ICommonDao commonDao) {
        this.commonDao = commonDao;
    }
    @Resource
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private JdbcDao jdbcDao;
    String detailsAssesColumn = "details_asses.id as id," +
            "details_asses.module_id as moduleId," +
            "details_asses.project_tree_id as projectTreeId," +
            "details_asses.requirements_id as requirementsId," +
            "details_asses.score_details as scoreDetails," +
            "details_asses.place_code as placeCode," +
            "details_asses.create_by as createBy," +
            "details_asses.create_time as createTime," +
            "details_asses.update_by as updateBy," +
            "details_asses.update_time as updateTime," +
            "details_asses.total_asses_id as totalAssesId," +
            "details_asses.a1 as a1," +
            "details_asses.a2 as a2," +
            "details_asses.a3 as a3," +
            "details_asses.a4 as a4," +
            "details_asses.a5 as a5," +
            "details_asses.a6 as a6," +
            "details_asses.a7 as a7";
    @Override
    public List<DetailsAsses> findByPage(PageQuery pageQuery,DetailsAsses detailsAsses) {
        String sqlHead = " SELECT   "+detailsAssesColumn+"";

        String sql = "   FROM details_asses WHERE 1=1 ";
        if (StringUtil.isNotBlank( detailsAsses.getModuleId())) {
            sql += " and module_id = '"+ detailsAsses.getModuleId()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getProjectTreeId())) {
            sql += " and project_tree_id = '"+ detailsAsses.getProjectTreeId()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getScoreDetails())) {
            sql += " and score_details = '"+ detailsAsses.getScoreDetails()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getCreateTime())) {
            sql += " and create_time = '"+ detailsAsses.getCreateTime()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getUpdateBy())) {
            sql += " and update_by = '"+ detailsAsses.getUpdateBy()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getUpdateTime())) {
            sql += " and update_time = '"+ detailsAsses.getUpdateTime()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getTotalAssesId())) {
            sql += " and total_asses_id = '"+ detailsAsses.getTotalAssesId()+"'";
        }

        if (StringUtil.isNotBlank( detailsAsses.getA1())) {
            sql += " and a1 = '"+ detailsAsses.getA1()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getA2())) {
            sql += " and a2 = '"+ detailsAsses.getA2()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getA3())) {
            sql += " and a3 = '"+ detailsAsses.getA3()+"'";
        }    if (StringUtil.isNotBlank( detailsAsses.getA4())) {
            sql += " and a4 = '"+ detailsAsses.getA4()+"'";
        }    if (StringUtil.isNotBlank( detailsAsses.getA5())) {
            sql += " and a5 = '"+ detailsAsses.getA5()+"'";
        }    if (StringUtil.isNotBlank( detailsAsses.getA6())) {
            sql += " and a6 = '"+ detailsAsses.getA6()+"'";
        }    if (StringUtil.isNotBlank( detailsAsses.getA7())) {
            sql += " and a7 = '"+ detailsAsses.getA7()+"'";
        }
        List<DetailsAsses> list = new ArrayList<>();
        if (pageQuery != null && pageQuery.getPageNo()>0 && pageQuery.getPageSize()>0) {
            list =  commonDao.findObjForJdbc(sql,pageQuery.getPageNo(),pageQuery.getPageSize(),DetailsAsses.class);
        } else {
            list =   jdbcTemplate.query(sqlHead+sql, new BeanPropertyRowMapper(DetailsAsses.class));
        }
        return list;
    }

    public PageInfo<DetailsAsses> findByPage2(PageQuery pageQuery, DetailsAsses detailsAsses)  {
        String sqlHead = " SELECT   "+detailsAssesColumn+"";
        String countSql = " SELECT   count(1) ";

        String sql = "   FROM details_asses WHERE 1=1 ";
        if (StringUtil.isNotBlank( detailsAsses.getModuleId())) {
            sql += " and module_id = '"+ detailsAsses.getModuleId()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getProjectTreeId())) {
            sql += " and project_tree_id = '"+ detailsAsses.getProjectTreeId()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getScoreDetails())) {
            sql += " and score_details = '"+ detailsAsses.getScoreDetails()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getCreateTime())) {
            sql += " and create_time = '"+ detailsAsses.getCreateTime()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getUpdateBy())) {
            sql += " and update_by = '"+ detailsAsses.getUpdateBy()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getUpdateTime())) {
            sql += " and update_time = '"+ detailsAsses.getUpdateTime()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getTotalAssesId())) {
            sql += " and total_asses_id = '"+ detailsAsses.getTotalAssesId()+"'";
        }

        if (StringUtil.isNotBlank( detailsAsses.getA1())) {
            sql += " and a1 = '"+ detailsAsses.getA1()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getA2())) {
            sql += " and a2 = '"+ detailsAsses.getA2()+"'";
        }
        if (StringUtil.isNotBlank( detailsAsses.getA3())) {
            sql += " and a3 = '"+ detailsAsses.getA3()+"'";
        }    if (StringUtil.isNotBlank( detailsAsses.getA4())) {
            sql += " and a4 = '"+ detailsAsses.getA4()+"'";
        }    if (StringUtil.isNotBlank( detailsAsses.getA5())) {
            sql += " and a5 = '"+ detailsAsses.getA5()+"'";
        }    if (StringUtil.isNotBlank( detailsAsses.getA6())) {
            sql += " and a6 = '"+ detailsAsses.getA6()+"'";
        }    if (StringUtil.isNotBlank( detailsAsses.getA7())) {
            sql += " and a7 = '"+ detailsAsses.getA7()+"'";
        }
        List<DetailsAsses> list = new ArrayList<>();


        if (pageQuery != null && pageQuery.getPageNo()>0 && pageQuery.getPageSize()>0) {
            list =  commonDao.findObjForJdbc(sql,pageQuery.getPageNo(),pageQuery.getPageSize(),DetailsAsses.class);
        } else {
            list =  jdbcTemplate.query(sqlHead+sql, new BeanPropertyRowMapper(DetailsAsses.class));
        }
        long count = commonDao.getCountForJdbc(countSql+sql);
        PageInfo<DetailsAsses>  pageInfo = new PageInfo();
        pageInfo.setList(list);
        pageInfo.setTotal(count);
        pageInfo.setPageSize(pageQuery.getPageSize());
        pageInfo.setPageNum(pageQuery.getPageNo());
        return pageInfo;
    }

    @Override
    public List<Map<String, Object>> findSumByModuleId(Integer totalAssesId,String projectTreeId) {
        String sql = "SELECT module_id, sum(score_details) sumScore FROM details_asses\n" +
                " where total_asses_id = '"+totalAssesId+"' and a1 = '1'   and project_tree_id = '"+projectTreeId+"'  \n" +
                "  group by module_id";

        return jdbcDao.findForListMap(sql,null);
    }

    @Override
    public List<Map<String, Object>> findSumSmallByModuleId(Integer totalAssesId,String projectTreeId) {
        String sql = "       select module_id,scoreDetail from (SELECT module_id,sum(score_details) scoreDetail FROM details_asses\n" +
                "        where total_asses_id = '"+totalAssesId+"' and a1 = '2' and a2 = '1'  and project_tree_id = '"+projectTreeId+"' \n" +
                "        group by place_code\n" +
                "        having place_code is not NULL\n" +
                "        order by sum(score_details)) b\n" +
                "        group by b.module_id";
        return   jdbcDao.findForListMap(sql,null);
    }

    @Override
    public List<Map<String, Object>> findSumAvgByModuleId(Integer totalAssesId,String projectTreeId) {
        String sql = "   select module_id,sum(scoreAvg) sumAvgScore from (SELECT module_id,avg(score_details) scoreAvg FROM details_asses\n" +
                "        where total_asses_id = '"+totalAssesId+"' and a1 = '2' and a2 = '3'  and project_tree_id = '"+projectTreeId+"' \n" +
                "        group by requirements_id) b group by b.module_id ";
        return    jdbcDao.findForListMap(sql,null);
    }
	
	@Override
    public List<Map<String, Object>> findSumSmallByModuleIdMax(Integer totalAssesId,String projectTreeId) {
        String sql = "       select module_id,scoreDetail from (SELECT module_id,sum(score_details) scoreDetail FROM details_asses\n" +
                "        where total_asses_id = '"+totalAssesId+"' and a1 = '2' and a2 = '2'  and project_tree_id = '"+projectTreeId+"' \n" +
                "        group by place_code\n" +
                "        having place_code is not NULL\n" +
                "        order by sum(score_details)  DESC) b\n" +
                "        group by b.module_id";
        return   jdbcDao.findForListMap(sql,null);
    }

    @Override
    public DetailsAsses findById(Integer id) {
        return commonDao.getEntity(DetailsAsses.class,id);
    }

    @Override
    public void insert(DetailsAsses detailsAsses) {
         commonDao.save(detailsAsses);
    }

    @Override
    public void update(DetailsAsses detailsAsses) {
        //commonDao.saveOrUpdate(detailsAsses);
        DetailsAsses t = systemService.get(DetailsAsses.class, detailsAsses.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(detailsAsses, t);
        } catch (Exception e) {
            e.printStackTrace();
        }
        systemService.saveOrUpdate(t);
    }

    @Override
    public int updateByIDetailsAsses(DetailsAsses detailsAsses) {
        String sql = "   update details_asses set a3='"+detailsAsses.getModuleId()+"'\n" +
                "    where total_asses_id='"+detailsAsses.getTotalAssesId()+"' and requirements_id='"+detailsAsses.getRequirementsId()+"' and module_id= '"+detailsAsses.getModuleId()+"' ";
        return commonDao.updateBySqlString(sql);
    }

    @Override
    public void deleteById(Integer id) {
         //commonDao.deleteEntityById(DetailsAsses.class,String.valueOf(id));
        systemService.delete(systemService.get(DetailsAsses.class,id));
    }
}
