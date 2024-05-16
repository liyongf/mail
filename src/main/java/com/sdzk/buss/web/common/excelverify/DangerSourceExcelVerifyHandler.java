package com.sdzk.buss.web.common.excelverify;

import com.sdzk.buss.web.common.utils.CommonUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

public class DangerSourceExcelVerifyHandler implements IExcelVerifyHandler<TBDangerSourceEntity> {

    //判断其使用的风险值计算方法是LEC法还是矩阵法. yes 代表LEC法， no 代表矩阵法
    String lec = ResourceUtil.getInitParam("les");

	@Override
	public ExcelVerifyHanlderResult verifyHandler(TBDangerSourceEntity obj) {
		StringBuilder builder = new StringBuilder();
		boolean success = true;
		/*if(StringUtil.isEmpty(obj.getDocSource())){
			builder.append("来源不能为空");
		}
		if(StringUtil.isEmpty(obj.getSectionName())){
			builder.append("章节不能为空");
		}
		if(StringUtil.isEmpty(obj.getClauseName())){
			builder.append("条款不能为空");
		}*/
		if(StringUtil.isEmpty(obj.getAddressCatetemp())){
//			builder.append("风险点类型不能为空。");
		}else{
            String[] addressCateNameArr = obj.getAddressCatetemp().split(",|，|、");
            for(int i = 0 ; i < addressCateNameArr.length; i++){
                String addressCateName = addressCateNameArr[i];
                if(StringUtil.isEmpty(DicUtil.getTypeCodeByName("addressCate",addressCateName))){
    				builder.append("风险点类型["+addressCateName+"]不存在。");
                }
            }
		}
		if(StringUtil.isEmpty(obj.getYeStandard())){
			builder.append("标准内容不能为空。");
		}else{
            obj.setYeStandard(obj.getYeStandard().trim());
        }
		if(StringUtil.isEmpty(obj.getYeMhazardDesc())){
			builder.append("隐患描述不能为空。");
		}else{
            obj.setYeMhazardDesc(obj.getYeMhazardDesc().trim());
        }
		if(StringUtil.isEmpty(obj.getYeProfession())){
			builder.append("专业属性不能为空。");
		} else {
            obj.setYeProfession(obj.getYeProfession().trim());
			if(StringUtil.isEmpty(DicUtil.getTypeCodeByName("proCate_gradeControl", obj.getYeProfession()))){
				builder.append("专业属性["+obj.getYeProfession()+"]不存在。");
			}
		}
		/*if(obj.getYeRecognizeTime()==null){
			builder.append("辨识时间不能为空。");
		}*/
		if (StringUtil.isEmpty(obj.getYeHazardCate())) {
			builder.append("风险类型不能为空。");
		} else {
            obj.setYeHazardCate(obj.getYeHazardCate().trim());
			if(StringUtil.isEmpty(DicUtil.getTypeCodeByName("hazardCate", obj.getYeHazardCate()))){
				builder.append("风险类型["+obj.getYeHazardCate()+"]不存在。");
			}
		}
	/*	if (StringUtil.isEmpty(obj.getYeRiskGrade())) {
			builder.append("风险等级不能为空。");
		} else{
			if(StringUtil.isEmpty(DicUtil.getTypeCodeByName("riskLevel", obj.getYeRiskGrade()))) {
				builder.append("风险等级["+obj.getYeRiskGrade()+"]不存在。");
			}
		}*/
		/*if (StringUtil.isEmpty(obj.getHiddenLevelTemp())) {
			builder.append("隐患等级不能为空。");
		} else{
			if(StringUtil.isEmpty(DicUtil.getTypeCodeByName("hiddenLevel", obj.getHiddenLevelTemp()))) {
				builder.append("隐患等级["+obj.getHiddenLevelTemp()+"]不存在。");
			}
		}*/
		if(StringUtil.isNotEmpty(obj.getYeAccident())){
            obj.setYeAccident(obj.getYeAccident().trim());
			StringBuffer accidents = new StringBuffer();
			for(String accident : obj.getYeAccident().split(",")){
				if(StringUtil.isEmpty(DicUtil.getTypeCodeByName("accidentCate", accident))){
					if (StringUtil.isNotEmpty(accidents.toString())){
						accidents.append(",");
					}
					accidents.append(accident);
				}
			}
			if(StringUtil.isNotEmpty(accidents.toString())){
				builder.append("事故类型["+accidents.toString()+"]不存在。");
			}
		}
		if(StringUtil.isEmpty(obj.getPostTemp())){
			builder.append("岗位不能为空。");
		}else{
            obj.setPostTemp(obj.getPostTemp().trim());
			if(StringUtil.isEmpty(CommonUtil.getPostIdByName(obj.getPostTemp()))) {
				builder.append("岗位["+obj.getPostTemp() + "]不存在。");
			}
		}
		if(StringUtil.isEmpty(obj.getActivityTemp())){
			builder.append("作业活动不能为空。");
		}else{
            obj.setActivityTemp(obj.getActivityTemp().trim());
			if(StringUtil.isEmpty(CommonUtil.getActivityIdByName(obj.getActivityTemp()))) {
				builder.append("作业活动["+obj.getActivityTemp() + "]不存在。");
			}
		}
		if(StringUtil.isEmpty(obj.getYePossiblyHazard())){
			builder.append("风险描述不能为空");
		}else{
            obj.setYePossiblyHazard(obj.getYePossiblyHazard().trim());
        }
		if(StringUtil.isEmpty(obj.getManageMeasure())){
			builder.append("风险管控措施不能为空");
		}else{
            obj.setManageMeasure(obj.getManageMeasure().trim());
        }

        //如果不用lec法计算风险值，使用矩阵法的话
        if("no".equals(lec)){
            if(StringUtil.isEmpty(obj.getYeCost())){
                builder.append("风险损失不能为空。");
            }else {
                obj.setYeCost(obj.getYeCost().trim());
                /*if (StringUtil.isEmpty(DicUtil.getTypeCodeByName("hazard_fxss", obj.getYeCost()))){
                    builder.append("风险损失["+obj.getYeCost()+"]不存在。");
                }*/
				if (StringUtil.isEmpty(DicUtil.getTypeNameByCode("hazard_fxss", obj.getYeCost()))){
					builder.append("风险损失["+obj.getYeCost()+"]不存在。");
				}
            }
            if(StringUtil.isEmpty(obj.getYeProbability())){
                builder.append("风险可能性不能为空。");
            }else{
                obj.setYeProbability(obj.getYeProbability().trim());
                /*if (StringUtil.isEmpty(DicUtil.getTypeCodeByName("probability", obj.getYeProbability()))){
                    builder.append("风险可能性["+obj.getYeProbability()+"]不存在。");
                }*/
				if (StringUtil.isEmpty(DicUtil.getTypeNameByCode("probability", obj.getYeProbability()))){
					builder.append("风险可能性["+obj.getYeProbability()+"]不存在。");
				}
            }
        }
        //如果使用lec法计算风险值的话
		if("yes".equals(lec)){
            if(obj.getLecRiskPossibilityTemp() == null || obj.getLecRiskPossibilityTemp() == ""){
                builder.append("LEC风险可能性不能为空。");
            }else {
                obj.setLecRiskPossibilityTemp(obj.getLecRiskPossibilityTemp().trim());
                /*if (StringUtil.isEmpty(DicUtil.getTypeCodeByName("lec_risk_probability", obj.getLecRiskPossibilityTemp().toString()))){
                    builder.append("LEC风险可能性["+obj.getLecRiskPossibilityTemp()+"]不存在。");
                }*/
				if (StringUtil.isEmpty(DicUtil.getTypeNameByCode("lec_risk_probability", obj.getLecRiskPossibilityTemp().toString()))
						&&StringUtil.isEmpty(DicUtil.getTypeNameByCode("lec_risk_probability", obj.getLecRiskPossibilityTemp().toString()+".0"))){
                    if(StringUtil.isEmpty(DicUtil.getTypeCodeByName("lec_risk_probability", obj.getLecRiskPossibilityTemp()))) {
                        builder.append("LEC风险可能性["+obj.getLecRiskPossibilityTemp()+"]不存在。");
                    } else {
                        obj.setLecRiskPossibility(new Double(DicUtil.getTypeCodeByName("lec_risk_probability", obj.getLecRiskPossibilityTemp())));
                    }
				} else {
                    obj.setLecRiskPossibility(new Double(obj.getLecRiskPossibilityTemp().toString()));
                }
            }
            if(obj.getLecRiskLossTemp() == null || obj.getLecRiskLossTemp() == ""){
                builder.append("LEC风险损失不能为空。");
            }else{
                obj.setLecRiskLossTemp(obj.getLecRiskLossTemp().trim());
                /*if (StringUtil.isEmpty(DicUtil.getTypeCodeByName("lec_risk_loss", obj.getLecRiskLossTemp().toString()))){
                    builder.append("LEC风险损失["+obj.getLecRiskLossTemp()+"]不存在。");
                }*/
                if (StringUtil.isEmpty(DicUtil.getTypeNameByCode("lec_risk_loss", obj.getLecRiskLossTemp().toString()))
                        &&StringUtil.isEmpty(DicUtil.getTypeNameByCode("lec_risk_loss", obj.getLecRiskLossTemp().toString()+".0"))){
                    if(StringUtil.isEmpty(DicUtil.getTypeCodeByName("lec_risk_loss", obj.getLecRiskLossTemp()))) {
                        builder.append("LEC风险损失["+obj.getLecRiskLossTemp()+"]不存在。");
                    } else {
                        obj.setLecRiskLoss(new Double(DicUtil.getTypeCodeByName("lec_risk_loss", obj.getLecRiskLossTemp())));
                    }
                } else {
                    obj.setLecRiskLoss(new Double(obj.getLecRiskLossTemp().toString()));
                }
            }
            if(obj.getLecExposureTemp() == null || obj.getLecExposureTemp() == ""){
                builder.append("LEC暴露在危险中的概率不能为空。");
            }else{
                obj.setLecExposureTemp(obj.getLecExposureTemp().trim());
                /*if (StringUtil.isEmpty(DicUtil.getTypeCodeByName("lec_exposure", obj.getLecExposureTemp().toString()))){
                    builder.append("LEC暴露在危险中的概率["+obj.getLecExposureTemp()+"]不存在。");
                }*/
                if (StringUtil.isEmpty(DicUtil.getTypeNameByCode("lec_exposure", obj.getLecExposureTemp().toString()))
                        &&StringUtil.isEmpty(DicUtil.getTypeNameByCode("lec_exposure", obj.getLecExposureTemp().toString()+".0"))){
                    if(StringUtil.isEmpty(DicUtil.getTypeCodeByName("lec_exposure", obj.getLecExposureTemp()))) {
                        builder.append("LEC暴露在危险中的概率["+obj.getLecExposureTemp()+"]不存在。");
                    } else {
                        obj.setLecExposure(new Double(DicUtil.getTypeCodeByName("lec_exposure", obj.getLecExposureTemp())));
                    }
                } else {
                    obj.setLecExposure(new Double(obj.getLecExposureTemp().toString()));
                }
            }
        }
		if(StringUtil.isEmpty(obj.getHazardTemp())){
			builder.append("危险源名称不能为空。");
		}else{
            obj.setHazardTemp(obj.getHazardTemp().trim());
			if(StringUtil.isEmpty(CommonUtil.getHazardIdByName(obj.getHazardTemp()))) {
				builder.append("危险源名称["+obj.getHazardTemp() + "]不存在。");
			}
		}
		/*if (StringUtil.isEmpty(obj.getDamageType())) {
			builder.append("伤害类别不能为空。");
		} else {
			if(StringUtil.isEmpty(DicUtil.getTypeCodeByName("danger_Category", obj.getDamageType()))){
				builder.append("伤害类别["+obj.getDamageType()+"]不存在。");
			}
		}*/
		if(StringUtil.isNotEmpty(obj.getDamageTypeTemp())){
            obj.setDamageTypeTemp(obj.getDamageTypeTemp().trim());
            StringBuffer noDamangeTypes = new StringBuffer();
			StringBuffer damageTypes = new StringBuffer();

			for(String damageType : obj.getDamageTypeTemp().split(",")){
				if(StringUtil.isNotEmpty(DicUtil.getTypeCodeByName("danger_Category", damageType))){
					if (StringUtil.isNotEmpty(damageTypes.toString())){
						damageTypes.append(",");
					}
                    damageTypes.append(damageType);
				}else{
                    if(StringUtil.isNotEmpty(noDamangeTypes.toString())){
                        noDamangeTypes.append(',');
                    }
                    noDamangeTypes = noDamangeTypes.append(damageType);
                }
			}
            obj.setDamageType(damageTypes.toString());
			if(StringUtil.isNotEmpty(noDamangeTypes.toString())){
				builder.append("伤害类别["+noDamangeTypes.toString()+"]不存在。");
			}
		}else{
			builder.append("伤害类别不能为空。");
		}

		if(StringUtil.isNotEmpty(builder.toString())){
			success = false;
		}
		return new ExcelVerifyHanlderResult(success,builder.toString());
	}

}
