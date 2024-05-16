/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.service.impl;

import com.sdzk.buss.web.aqbzh.dao.QualityControlRequirementsMapper;
import com.sdzk.buss.web.aqbzh.pojo.bo.QualityControlRequirements;
import com.sdzk.buss.web.aqbzh.service.IQualityControlRequirementsService;
import com.sdzk.buss.web.aqbzh.util.PageInfo;
import com.sdzk.buss.web.aqbzh.util.PageQuery;
import com.sdzk.buss.web.aqbzh.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description ServiceImpl
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
@Service(value = "qualityControlRequirementsServiceImpl")
public class QualityControlRequirementsServiceImpl implements IQualityControlRequirementsService {

	@Autowired
	private QualityControlRequirementsMapper qualityControlRequirementsMapper;

	/**
	 * @Description 根据条件查询-分页
	 */
	public List<QualityControlRequirements> findByPage (PageQuery pageQuery, QualityControlRequirements qualityControlRequirements){
		List<QualityControlRequirements> qualityControlRequirementsList = qualityControlRequirementsMapper.findByPage(pageQuery, qualityControlRequirements);
		return qualityControlRequirementsList;
	}
	/**
	 * @Description 根据条件查询-分页
	 */
	public PageInfo<QualityControlRequirements> findByPage2 (PageQuery pageQuery, QualityControlRequirements qualityControlRequirements){
		PageInfo<QualityControlRequirements> qualityControlRequirementsList = qualityControlRequirementsMapper.findByPage2(pageQuery, qualityControlRequirements);
		return qualityControlRequirementsList;
	}
	/**
	 * @Description 根据id查询
	 */
	public QualityControlRequirements findById(Integer id){
		return qualityControlRequirementsMapper.findById(id);
	}

	/**
	 * @Description 添加数据
	 */
	public void insert(QualityControlRequirements qualityControlRequirements){
		 qualityControlRequirementsMapper.insert(qualityControlRequirements);
	}

	/**
	 * @Description 修改数据
	 */
	public void update(QualityControlRequirements qualityControlRequirements){
		 qualityControlRequirementsMapper.update(qualityControlRequirements);
	}

	/**
	 * @Description 修改数据
	 */
	public void updateByIds(String ids, Integer a3){
		List<String> idList = StringUtil.splitToListStr(ids);
		 qualityControlRequirementsMapper.updateByIds(idList,a3);
	}

	/**
	 * @Description 修改数据
	 */
	public void updateA4ByIds(String ids, String a4){
		List<String> idList = StringUtil.splitToListStr(ids);
		 qualityControlRequirementsMapper.updateA4ByIds(idList,a4);
	}

	/**
	 * @Description 修改数据
	 */
	public void updateA5ByIds(String ids, String a5){
		List<String> idList = StringUtil.splitToListStr(ids);
		 qualityControlRequirementsMapper.updateA5ByIds(idList,a5);
	}

	/**
	 * @Description 根据id删除
	 */
	public void deleteById(Integer id){
		 qualityControlRequirementsMapper.deleteById(id);
	}

}
