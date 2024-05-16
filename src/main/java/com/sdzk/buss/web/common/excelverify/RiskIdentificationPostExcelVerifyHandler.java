package com.sdzk.buss.web.common.excelverify;

import com.sddb.buss.identification.entity.RiskIdentificationPostEntity;

import com.sddb.common.Constants;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.p3.core.util.MD5Util;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;
import org.jeecgframework.web.system.pojo.base.TSDepart;


import java.util.Map;

public class RiskIdentificationPostExcelVerifyHandler implements IExcelVerifyHandler<RiskIdentificationPostEntity> {

	private Map<String,TSDepart> postUnitMap;
	private Map<String, TBPostManageEntity> postMap;
	private Map<String, String> riskIdentificationPostMap;
	private Map<String, String> hazardFactorsPostEntityMap;
	private Map<String, String> hazardRiskMap;
	public RiskIdentificationPostExcelVerifyHandler( Map<String,TSDepart> postUnitMap, Map<String, TBPostManageEntity> postMap,Map<String, String> riskIdentificationPostMap,Map<String, String> hazardFactorsPostEntityMap,Map<String, String> hazardRiskMap){
		this.postUnitMap=postUnitMap;
		this.postMap=postMap;
		this.riskIdentificationPostMap=riskIdentificationPostMap;
		this.hazardFactorsPostEntityMap=hazardFactorsPostEntityMap;
		this.hazardRiskMap=hazardRiskMap;
	}

	@Override
	public ExcelVerifyHanlderResult verifyHandler(RiskIdentificationPostEntity obj) {
		StringBuilder builder = new StringBuilder();
		boolean success = true;
		if (StringUtils.isBlank(obj.getPostUnitTemp())) {
			builder.append("单位不能为空。");
		} else {
			TSDepart depart = postUnitMap.get(obj.getPostUnitTemp());
			if(depart == null){
				builder.append("单位["+obj.getPostUnitTemp()+"]不存在。");
			} else {
				obj.setPostUnit(depart);
			}
		}
		if (StringUtils.isBlank(obj.getPostTemp())) {
			builder.append("岗位不能为空。");
		} else {
			TBPostManageEntity post = postMap.get(obj.getPostTemp());
			if(post == null){
				builder.append("岗位["+obj.getPostTemp()+"]不存在。");
			} else {
				obj.setPost(post);
			}
		}


		if (StringUtils.isBlank(obj.getRiskTypeTemp())) {
			builder.append("风险类型不能为空。");
		} else{
			String riskType = DicUtil.getTypeCodeByName("risk_type", obj.getRiskTypeTemp());
			if(StringUtils.isBlank(riskType)) {
				builder.append("风险类型["+obj.getRiskTypeTemp()+"]不存在。");
			}else{
				obj.setRiskType(riskType);
			}
		}
		if (StringUtils.isBlank(obj.getRiskLevelTemp())) {
			builder.append("风险等级不能为空。");
		} else{
			String riskLevel = DicUtil.getTypeCodeByName("factors_level", obj.getRiskLevelTemp());
			if(StringUtils.isBlank(riskLevel)) {
				builder.append("风险等级["+obj.getRiskLevelTemp()+"]不存在。");
			}else{
				obj.setRiskLevel(riskLevel);
			}
		}

		String postUnit = "";
		TSDepart depart = postUnitMap.get(obj.getPostUnitTemp());
		if(depart != null){
			postUnit = depart.getId();
		}
		String post = "";
		TBPostManageEntity postEntity = postMap.get(obj.getPostTemp());
		if(postEntity != null){
			post=postEntity.getId();
		}
		String riskType = DicUtil.getTypeCodeByName("risk_type", obj.getRiskTypeTemp());
		String riskLevel = DicUtil.getTypeCodeByName("factors_level", obj.getRiskLevelTemp());
		String newRiskKey = MD5Util.MD5Encode(postUnit+post+riskType+riskLevel, "UTF-8");
		String riskIdentificationPostId = riskIdentificationPostMap.get(newRiskKey);
		if(StringUtil.isNotEmpty(riskIdentificationPostId)){
			String newHazardKey = MD5Util.MD5Encode(obj.getHazardFactorsPostTemp().trim(), "UTF-8");
			String hazardFactorsPostId = hazardFactorsPostEntityMap.get(newHazardKey);
			if(StringUtil.isNotEmpty(hazardFactorsPostId)){
				String hazardKey = MD5Util.MD5Encode(hazardFactorsPostId, "UTF-8");
				String rikKey = MD5Util.MD5Encode(riskIdentificationPostId, "UTF-8");
				String hazardRiskRelId = hazardRiskMap.get(hazardKey+rikKey);
				if(StringUtil.isNotEmpty(hazardRiskRelId)){
					builder.append("此风险已存在");
				}
			}
		}

		if(StringUtil.isNotEmpty(builder.toString())){
			success = false;
		}
		return new ExcelVerifyHanlderResult(success,builder.toString());
	}

}
