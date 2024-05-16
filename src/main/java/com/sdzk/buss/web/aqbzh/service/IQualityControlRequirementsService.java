/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.service;


import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlRequirements;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;

import java.util.List;

/**
 * @Description iService
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
public interface IQualityControlRequirementsService {
	/**
	 * @Description 根据条件查找-分页
	 */
	List<QualityControlRequirements> findByPage(PageQuery pageQuery, QualityControlRequirements qualityControlRequirements);

	/**
	 * @Description 根据id查询
	 */
	 QualityControlRequirements findById(Integer id);


	/**
	 * @Description 添加数据
	 */
	void insert(QualityControlRequirements qualityControlRequirements);

	/**
	 * @Description 修改数据
	 */
	void update(QualityControlRequirements qualityControlRequirements);

	/**
	 * @Description 修改数据
	 */
	void updateByIds(String ids, Integer a3);

	/**
	 * @Description 修改数据
	 */
	void updateA4ByIds(String ids, String a4);

	/**
	 * @Description 修改数据
	 */
	void updateA5ByIds(String ids, String a5);

	/**
	 * @Description 根据id删除
	 */
	void deleteById(Integer id);

    PageInfo<QualityControlRequirements> findByPage2(PageQuery pageQuery, QualityControlRequirements qualityControlRequirements);
}
