package com.sdzk.buss.web.common.service;

import com.sddb.buss.identification.entity.RiskTaskEntity;
import com.sdzk.buss.web.gjj.entity.SFPictureInfoEntity;
import com.sdzk.buss.web.layer.entity.TBLayerEntity;
import org.jeecgframework.core.common.service.CommonService;

/**
 * 上报国家局升级改造service
 */
public interface SfService  extends CommonService {

	/**
	 * 保存  国家局上报
	 * @param taskAllId     taskAllId
	 * @param riskId    风险id
	 */
	public void saveSfRiskControl(String taskAllId, String riskId);

	/**
	 * 图形文件增加风险四色图  基本信息
	 */
	/**
	 * 图形文件增加风险四色图  基本信息
	 * @param layerEntity  图层名称
	 * @return
	 */
	public SFPictureInfoEntity savePictureInfo(TBLayerEntity layerEntity);

	/**
	 * 风险四色图是否存在  根据layerid查询
	 * @param layerId   图层编码
	 * @return
	 */
	public String isExistPicture(String layerId);

	/**
	 * 增加隐患关联
	 */
	public void saveOrUpdateSfHiddenRel(String hiddenId);

	/**
	 * 删除隐患关联
	 * @param hiddenId   隐患id
	 */
	public void deleteSfHiddenRel(String hiddenId);
	/**
	 * 增加三违关联
	 */
	public void saveOrUpdateSfVioRel(String vioId);

	/**
	 * 删除三违关联
	 * @param vioId   隐患id
	 */
	public void deleteSfVioRel(String vioId);

	/**
	 * 保存风险辨识任务关联表
	 * @param riskTask
	 * @param stateFlag
	 */
	public void saveOrUpdateRiskTask(RiskTaskEntity riskTask, String stateFlag);

	/**
	 * 保存危害因素和风险关联关系用于国际局上报
	 * @param riskId   风险id
	 * @param addressId  风险点id
	 * @param dutyManager  管控责任人
	 * @param factorId   危害因素id
	 * @param measures   危害因素描述
	 * @param postName	岗位名称
	 * @param isUpload	是否上传  0：上传  1：不上传
	 */
	public void saveOrUpdateSfRiskMeasureEntity(String riskId, String addressId, String dutyManager, String factorId, String measures, String postName, String isUpload);
}
