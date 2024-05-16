/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.dao;

import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlRequirements;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;

import java.util.List;

/**
 * @Description iMapper
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
public interface QualityControlRequirementsMapper{

	/**
	 * @Description 分页查询
	 */
	List<QualityControlRequirements> findByPage(PageQuery pageQuery, QualityControlRequirements qualityControlRequirements);


	/**
	 * @Description 根据id查找
	 */
	QualityControlRequirements findById(Integer id);

	/**
	 * @Description 新增
	 */
	void insert(QualityControlRequirements qualityControlRequirements);

	/**
	 * @Description 修改
	 */
	void update(QualityControlRequirements qualityControlRequirements);

	/**
	 * @Description 修改
	 */
	void updateByIds(List<String> ids, Integer a3);

	/**
	 * @Description 修改
	 */
	void updateA4ByIds(List<String> ids, String a4);

	/**
	 * @Description 修改
	 */
	void updateA5ByIds(List<String> ids, String a5);

	/**
	 * @Description 根据id删除
	 */
	void deleteById(Integer id);

    PageInfo<QualityControlRequirements> findByPage2(PageQuery pageQuery, QualityControlRequirements qualityControlRequirements);
}
