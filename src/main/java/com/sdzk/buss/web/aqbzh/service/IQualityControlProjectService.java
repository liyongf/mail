/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.service;

import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlProject;
import com.sdzk.buss.web.aqbzh.pojo.dto.QualityControlProjectDTO;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;


import java.util.List;

/**
 * @Description iService
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
public interface IQualityControlProjectService {

	/**
	 * @Description 渲染表格
	 */
	List<QualityControlProjectDTO> findByList(String moduleId, String moduleIds, Integer status, String url, Integer totalAssesId, String score, String theWeight);

	/**
	 * @Description 根据条件查找-分页
	 */
	List<QualityControlProject> findByPage(PageQuery pageQuery, QualityControlProject qualityControlProject);

	/**
	 * @Description 根据id查询
	 */
	 QualityControlProject findById(Integer id);


	/**
	 * @Description 添加数据
	 */
	void insert(QualityControlProject qualityControlProject);

	/**
	 * @Description 修改数据
	 */
	void update(QualityControlProject qualityControlProject);

	/**
	 * @Description 根据id删除
	 */
	void deleteById(Integer id);

    PageInfo<QualityControlProject> findByPage2(PageQuery pageQuery, QualityControlProject qualityControlProject);
}
