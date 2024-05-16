/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.dao;

import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlModule;
import com.sdzk.buss.web.aqbzh.pojo.dto.QualityControlTableIds;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description iMapper
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
public interface QualityControlModuleMapper {

    /**
     * @Description 分页查询
     */
    List<QualityControlModule> findByPage(PageQuery pageQuery, QualityControlModule qualityControlModule);

    /**
     * 查询最后一级模块
     *
     * @return
     */
    List<QualityControlModule> findByList();

    /**
     * 根据父id分组查询拼接后的表id
     * @return
     */
    List<QualityControlTableIds> findByPageIds();

    /**
     * 查询最后一级模块
     *
     * @return
     */
    List<QualityControlModule> findByListIds(@Param("year") String year, @Param("month") String month, @Param("typeCode") String typeCode, @Param("duokuangCode") String duokuangCode);

    /**
     * @Description 根据id查找
     */
    QualityControlModule findById(@Param("id") Integer id);

    /**
     * @Description 根据id查找
     */
    List<QualityControlModule> findByIdList(@Param("idList") List<Integer> idList);

    /**
     * @Description 新增
     */
    void insert(QualityControlModule qualityControlModule);

    /**
     * @Description 修改
     */
    void update(QualityControlModule qualityControlModule);

    /**
     * @Description 修改
     */
    int updateByParentId(@Param("parentId") Integer parentId, @Param("isAsses") Integer isAsses);

    /**
     * @Description 根据id删除
     */
    void deleteById(@Param("id") Integer id);

    PageInfo<QualityControlModule> findByPage2(PageQuery pageQuery, QualityControlModule qualityControlModule);
}
