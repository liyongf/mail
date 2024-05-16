/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年06月03日
 * 修改日期：2020年06月03日
 */
package com.sdzk.buss.web.aqbzh.dao;

import com.sdzk.buss.web.aqbzh.pojo.bo.TotalAsses;
import com.sdzk.buss.web.aqbzh.pojo.dto.QualityControlModuleDTO;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description iMapper
 * @author：Tony
 * @version：V1.0
 * @since：2020年06月03日
 */
public interface TotalAssesMapper{

	/**
	 * @Description 分页查询
	 */
	List<TotalAsses> findByPage(PageQuery pageQuery, TotalAsses totalAsses);


	/**
	 * @Description 根据id查找
	 */
	TotalAsses findById(@Param("id") Integer id);

	/**
	 * 查询是否打分
	 */
	Double findByData(@Param("year") String year, @Param("month") String month);

	/**
	 * @Description 新增
	 */
	void insert(TotalAsses totalAsses);

	/**
	 * @Description 新增
	 */
	void insertAll(@Param("totalAsses") List<TotalAsses> totalAsses);

	/**
	 * @Description 修改
	 */
	void update(TotalAsses totalAsses);

	/**
	 * @Description 根据id删除
	 */
	void deleteById(@Param("id") Integer id);

	/**
	 * @Description 根据年月并且分数为0的删除
	 */
	void deleteByDate(@Param("year") String year, @Param("monthList") List<String> monthList);

	/**
	 * @Description 根据年月查询id
	 */
	List<Integer> selectByDate(@Param("year") String year, @Param("monthList") List<String> monthList);

	/**
	 * 暂时没用
	 */
	List<QualityControlModuleDTO> reportedScores();

	PageInfo<TotalAsses> findByPage2(PageQuery pageQuery, TotalAsses totalAsses);
}
