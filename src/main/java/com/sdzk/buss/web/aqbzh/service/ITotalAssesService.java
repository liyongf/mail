/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年06月03日
 * 修改日期：2020年06月03日
 */
package com.sdzk.buss.web.aqbzh.service;



import com.sdzk.buss.web.aqbzh.pojo.bo.TotalAsses;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;

import java.util.List;

/**
 * @Description iService
 * @author：Tony
 * @version：V1.0
 * @since：2020年06月03日
 */
public interface ITotalAssesService {
	/**
	 * @Description 根据条件查找-分页
	 */
	List<TotalAsses> findByPage(PageQuery pageQuery, TotalAsses totalAsses, String dataduokuang, boolean duokuangFlag);

	/**
	 * @Description 根据id查询
	 */
	 TotalAsses findById(Integer id);


	/**
	 * @Description 添加数据
	 */
	void insert(TotalAsses totalAsses);

	/**
	 * @Description 修改数据
	 */
	void update(TotalAsses totalAsses);

	/**
	 * @Description 根据id删除
	 */
	void deleteById(Integer id);

	/**
	 * @Description 根据年月并且分数为0的删除
	 */
	void deleteByDate(String dataduokuang, boolean duokuangFlag);

    PageInfo<TotalAsses> findByPage2(PageQuery pageQuery, TotalAsses totalAsses, String dataduokuang, boolean duokuangFlag);
}
