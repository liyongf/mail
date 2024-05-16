/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年06月05日
 * 修改日期：2020年06月05日
 */
package com.sdzk.buss.web.aqbzh.service;


import com.sdzk.buss.web.aqbzh.pojo.bo.DetailsAsses;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;

import java.util.List;

/**
 * @Description iService
 * @author：Tony
 * @version：V1.0
 * @since：2020年06月05日
 */
public interface IDetailsAssesService {
	/**
	 * @Description 根据条件查找-分页
	 */
	List<DetailsAsses> findByPage(PageQuery pageQuery, DetailsAsses detailsAsses);
	PageInfo<DetailsAsses> findByPage2(PageQuery pageQuery, DetailsAsses detailsAsses);
	/**
	 * @Description 根据id查询
	 */
	 DetailsAsses findById(Integer id);


	/**
	 * @Description 添加数据
	 */
	int insert(DetailsAsses detailsAsses);

	/**
	 * @Description 修改数据
	 */
	void update(DetailsAsses detailsAsses);

	/**
	 * 修改数据
	 */
	void updateByIDetailsAsses(DetailsAsses detailsAsses);

	/**
	 * @Description 根据id删除
	 */
	void deleteById(Integer id);

	/**
	 * @Description 计算分数
	 */
	void calculateScore(Integer totalAssesId, String moduleId);

}
