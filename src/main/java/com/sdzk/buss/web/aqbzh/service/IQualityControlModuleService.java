/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.service;

import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlModule;
import com.sdzk.buss.web.aqbzh.pojo.dto.QualityControlModuleDTO;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;

import java.util.List;

/**
 * @Description iService
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
public interface IQualityControlModuleService {
	/**
	 * 渲染表格
	 * @return
	 */
	List<QualityControlModuleDTO> findByList(String year, String month, String duokuangCode);
	/**
	 * @Description 根据条件查找-分页
	 */
	List<QualityControlModule> findByPage(PageQuery pageQuery, QualityControlModule qualityControlModule);

	PageInfo<QualityControlModule> findByPage2(PageQuery pageQuery, QualityControlModule qualityControlModule);

	/**
	 * @Description 根据id查询
	 */
	 QualityControlModule findById(Integer id);

	/**
	 * @Description 根据id查询
	 */
	List<QualityControlModule> findByIdList(List<Integer> idList);

	/**
	 * @Description 添加数据
	 */
	void insert(QualityControlModule qualityControlModule);

	/**
	 * @Description 修改数据
	 */
	void update(QualityControlModule qualityControlModule);

	/**
	 * @Description 根据id删除
	 */
	void deleteById(Integer id);

}
