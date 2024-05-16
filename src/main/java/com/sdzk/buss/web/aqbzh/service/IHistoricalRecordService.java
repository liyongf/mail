/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年06月10日
 * 修改日期：2020年06月10日
 */
package com.sdzk.buss.web.aqbzh.service;


import com.sdzk.buss.web.aqbzh.pojo.bo.HistoricalRecord;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;

import java.util.List;

/**
 * @Description iService
 * @author：Tony
 * @version：V1.0
 * @since：2020年06月10日
 */
public interface IHistoricalRecordService {
	/**
	 * @Description 根据条件查找-分页
	 */
	List<HistoricalRecord> findByPage(PageQuery pageQuery, HistoricalRecord historicalRecord);

	PageInfo<HistoricalRecord> findByPage2(PageQuery pageQuery, HistoricalRecord historicalRecord);
	/**
	 * @Description 添加数据
	 */
	void insert(HistoricalRecord historicalRecord);

	/**
	 * @Description 修改数据
	 */
	void update(HistoricalRecord historicalRecord);

	/**
	 * @Description 根据id删除
	 */
	void deleteById(Integer id);

}
