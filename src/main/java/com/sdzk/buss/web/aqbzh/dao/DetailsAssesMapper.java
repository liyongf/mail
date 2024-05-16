/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年06月05日
 * 修改日期：2020年06月05日
 */
package com.sdzk.buss.web.aqbzh.dao;

import com.sdzk.buss.web.aqbzh.pojo.bo.DetailsAsses;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;

import java.util.List;
import java.util.Map;

/**
 * @Description iMapper
 * @author：Tony
 * @version：V1.0
 * @since：2020年06月05日
 */
public interface DetailsAssesMapper{

	/**
	 * @Description 分页查询
	 */
	List<DetailsAsses> findByPage(PageQuery pageQuery, DetailsAsses detailsAsses);

	PageInfo<DetailsAsses> findByPage2(PageQuery pageQuery, DetailsAsses detailsAsses);

	/**
	 *  单点分数求和 根据表分组
	 */
	List<Map<String, Object>> findSumByModuleId(Integer totalAssesId, String projectTreeId);

	/**
	 *  多点分数求和 根据点分组求最低
	 */
	List<Map<String, Object>> findSumSmallByModuleId(Integer totalAssesId, String projectTreeId);

	/**
	 *  多点分数取平均数的和 根据点分组求平均在求和
	 */
	List<Map<String, Object>> findSumAvgByModuleId(Integer totalAssesId, String projectTreeId);
	
	
	/**
	 *  多点分数求和 根据点分组求最高
	 */
	List<Map<String,Object>> findSumSmallByModuleIdMax(Integer totalAssesId, String projectTreeId);

	

	/**
	 * @Description 根据id查找
	 */
	DetailsAsses findById(Integer id);

	/**
	 * @Description 新增
	 */
	void insert(DetailsAsses detailsAsses);

	/**
	 * @Description 修改
	 */
	void update(DetailsAsses detailsAsses);

	/**
	 * @Description 修改
	 */
	int updateByIDetailsAsses(DetailsAsses detailsAsses);

	/**
	 * @Description 根据id删除
	 */
	void deleteById(Integer id);
}
