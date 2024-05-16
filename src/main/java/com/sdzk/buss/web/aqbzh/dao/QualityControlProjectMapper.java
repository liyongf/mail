/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.dao;

import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlProject;
import com.sdzk.buss.web.aqbzh.pojo.dto.QualityControlProjectDTO;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;

import java.util.List;

/**
 * @Description iMapper
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
public interface QualityControlProjectMapper{

	List<QualityControlProjectDTO> findByList(Integer moduleId, Integer a3);

	/**
	 * @Description 分页查询
	 */
	List<QualityControlProject> findByPage(PageQuery pageQuery, QualityControlProject qualityControlProject);


	/**
	 * @Description 根据id查找
	 */
	QualityControlProject findById(Integer id);

	/**
	 * @Description 新增
	 */
	void insert(QualityControlProject qualityControlProject);

	/**
	 * @Description 修改
	 */
	void update(QualityControlProject qualityControlProject);

	/**
	 * 修改
	 * @return
	 */
	int updateByParentId(Integer parentId, Integer status, String a1, String a2);

	/**
	 * @Description 根据id删除
	 */
	void deleteById(Integer id);

    PageInfo<QualityControlProject> findByPage2(PageQuery pageQuery, QualityControlProject qualityControlProject);
}
