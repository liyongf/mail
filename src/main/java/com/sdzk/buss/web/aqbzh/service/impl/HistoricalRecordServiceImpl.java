/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年06月10日
 * 修改日期：2020年06月10日
 */
package com.sdzk.buss.web.aqbzh.service.impl;

import com.sdzk.buss.web.aqbzh.dao.HistoricalRecordMapper;
import com.sdzk.buss.web.aqbzh.pojo.bo.HistoricalRecord;
import com.sdzk.buss.web.aqbzh.service.IHistoricalRecordService;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description ServiceImpl
 * @author：Tony
 * @version：V1.0
 * @since：2020年06月10日
 */
@Service(value = "historicalRecordServiceImpl")
public class HistoricalRecordServiceImpl implements IHistoricalRecordService {

	@Autowired
	private HistoricalRecordMapper historicalRecordMapperImpl;

	/**
	 * @Description 根据条件查询-分页
	 */
	public List<HistoricalRecord> findByPage (PageQuery pageQuery, HistoricalRecord historicalRecord){
		List<HistoricalRecord> historicalRecordList = historicalRecordMapperImpl.findByPage(pageQuery,historicalRecord);
		return historicalRecordList;
	}

	public PageInfo<HistoricalRecord> findByPage2(PageQuery pageQuery, HistoricalRecord historicalRecord){
		PageInfo<HistoricalRecord> historicalRecordList = historicalRecordMapperImpl.findByPage2(pageQuery,historicalRecord);
		return historicalRecordList;
	}
	/**
	 * @Description 添加数据
	 */
	public void insert(HistoricalRecord historicalRecord){
		 historicalRecordMapperImpl.insert(historicalRecord);
	}

	/**
	 * @Description 修改数据
	 */
	public void update(HistoricalRecord historicalRecord){
		 historicalRecordMapperImpl.update(historicalRecord);
	}

	/**
	 * @Description 根据id删除
	 */
	public void deleteById(Integer id){
		 historicalRecordMapperImpl.deleteById(id);
	}

}
