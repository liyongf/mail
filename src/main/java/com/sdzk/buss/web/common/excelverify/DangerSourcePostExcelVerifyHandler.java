package com.sdzk.buss.web.common.excelverify;

import com.sdzk.buss.web.tbdangersourceapost.entity.TBDangerPostRelEntity;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.service.SystemService;
import com.sdzk.buss.web.tbdangersourceapost.entity.TBDangerSourceaPostEntity;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

import java.util.List;

public class DangerSourcePostExcelVerifyHandler implements IExcelVerifyHandler<TBDangerSourceaPostEntity> {
	private SystemService systemService = (SystemService) ApplicationContextUtil.getContext().getBean("systemService");
	@Override
	public ExcelVerifyHanlderResult verifyHandler(TBDangerSourceaPostEntity obj) {
		StringBuilder builder = new StringBuilder();
        //判断其使用的风险值计算方法是LEC法还是矩阵法. yes 代表LEC法， no 代表矩阵法
        String lec = ResourceUtil.getInitParam("les");
		boolean success = true;

		//辨识时间
		if(obj.getRecognizeTime()==null){
			builder.append("辨识时间不能为空。");
		}
		//专业分类
		if(StringUtil.isEmpty(obj.getProfessionaltype())){
			builder.append("专业分类不能为空。");
		} else {
            obj.setProfessionaltype(obj.getProfessionaltype().trim());
			String professionalType = DicUtil.getTypeCodeByName("proCate_gradeControl", obj.getProfessionaltype());
			if(StringUtil.isEmpty(professionalType)){
				builder.append("专业分类["+obj.getProfessionaltype()+"]不存在。");
			} else {
				obj.setProfessionaltype(professionalType);
			}
		}
		//风险类型
		if (StringUtil.isEmpty(obj.getRiskType())) {
			builder.append("风险类型不能为空。");
		} else {
            obj.setRiskType(obj.getRiskType().trim());
			String riskType = DicUtil.getTypeCodeByName("hazardCate", obj.getRiskType());
			if(StringUtil.isEmpty(riskType)){
				builder.append("风险类型["+obj.getRiskType()+"]不存在。");
			} else {
				obj.setRiskType(riskType);
			}
		}
		//工种
		if(StringUtil.isEmpty(obj.getPostName())){
			builder.append("岗位不能为空。");
		} else {
            obj.setPostName(obj.getPostName().trim());
			List<TBPostManageEntity> postManageEntities = systemService.findByProperty(TBPostManageEntity.class, "postName", obj.getPostName());
			if (postManageEntities == null || postManageEntities.size() == 0){
				builder.append("岗位["+obj.getPostName()+"]不存在。");
			} else {
				obj.setPostId(postManageEntities.get(0).getId());
			}
		}
		//风险名称
		if(StringUtil.isEmpty(obj.getDangerName())){
			builder.append("风险名称不能为空。");
		}else{
            obj.setDangerName(obj.getDangerName().trim());
        }
		//风险等级
		if(StringUtil.isNotEmpty(obj.getRiskLevel()) &&
				StringUtil.isEmpty(DicUtil.getTypeCodeByName("riskLevel", obj.getRiskLevel()))) {
			builder.append("风险等级["+obj.getRiskLevel()+"]不存在。");
		}
		//事故类型
		if(StringUtil.isNotEmpty(obj.getAccidentType())){
            obj.setAccidentType(obj.getAccidentType().trim());
			StringBuffer accidents = new StringBuffer();
			StringBuffer exists = new StringBuffer();
			for(String accident : obj.getAccidentType().split(",")){
				String cate = DicUtil.getTypeCodeByName("accidentCate", accident);
				if(StringUtil.isEmpty(cate)){
					if (StringUtil.isNotEmpty(accidents.toString())) {
						accidents.append(",");
					}
					accidents.append(accident);
				} else {
					if (StringUtil.isNotEmpty(exists.toString())) {
						exists.append(",");
					}
					exists.append(cate);
				}
			}
			if(StringUtil.isNotEmpty(accidents.toString())){
				builder.append("事故类型["+accidents.toString()+"]不存在。");
			} else {
				obj.setAccidentType(exists.toString());
			}
		}

        //如果不用lec法计算风险值，使用矩阵法的话
        if("no".equals(lec)){
            //风险可能性
            if(StringUtil.isEmpty(obj.getRiskPossibility())){
                builder.append("可能性类型不能为空。");
            }else{
                obj.setRiskPossibility(obj.getRiskPossibility().trim());
                String riskProssibility = DicUtil.getTypeCodeByName("probability", obj.getRiskPossibility());
                if (StringUtil.isEmpty(riskProssibility)){
                    builder.append("可能性类型["+obj.getRiskPossibility()+"]不存在。");
                } else {
                    obj.setRiskPossibility(riskProssibility);
                }
            }
            //风险损失
            if(StringUtil.isEmpty(obj.getRiskLoss())){
                builder.append("风险损失不能为空。");
            }else {
                obj.setRiskLoss(obj.getRiskLoss().trim());
                String riskLoss = DicUtil.getTypeCodeByName("hazard_fxss", obj.getRiskLoss());
                if (StringUtil.isEmpty(riskLoss)){
                    builder.append("风险损失["+obj.getRiskLoss()+"]不存在。");
                } else {
                    obj.setRiskLoss(riskLoss);
                }
            }
        }
        //如果使用lec法计算风险值的话
        if("yes".equals(lec)){
            //LEC风险可能性
            if(StringUtil.isEmpty(obj.getLecRiskPossibilityTemp())){
                builder.append("LEC风险可能性不能为空。");
            }else{
                obj.setLecRiskPossibilityTemp(obj.getLecRiskPossibilityTemp().trim());
                String lecRiskPossibility = DicUtil.getTypeCodeByName("lec_risk_probability", obj.getLecRiskPossibilityTemp());
                if (StringUtil.isEmpty(lecRiskPossibility)){
                    builder.append("LEC风险可能性["+obj.getLecRiskPossibilityTemp()+"]不存在。");
                } else {
                    obj.setLecRiskPossibility(Double.parseDouble(lecRiskPossibility));
                }
            }
            //LEC风险损失
            if(StringUtil.isEmpty(obj.getLecRiskLossTemp())){
                builder.append("LEC风险损失不能为空。");
            }else {
                obj.setLecRiskLossTemp(obj.getLecRiskLossTemp().trim());
                String lecRiskLoss = DicUtil.getTypeCodeByName("lec_risk_loss", obj.getLecRiskLossTemp());
                if (StringUtil.isEmpty(lecRiskLoss)){
                    builder.append("LEC风险损失["+obj.getLecRiskLossTemp()+"]不存在。");
                } else {
                    obj.setLecRiskLoss(Double.parseDouble(lecRiskLoss));
                }
            }
            //LEC人员暴露在危险中的频率
            if(StringUtil.isEmpty(obj.getLecExposureTemp())){
                builder.append("LEC暴露在危险中的概率不能为空。");
            }else {
                obj.setLecExposureTemp(obj.getLecExposureTemp().trim());
                String lecExposure = DicUtil.getTypeCodeByName("lec_exposure", obj.getLecExposureTemp());
                if (StringUtil.isEmpty(lecExposure)){
                    builder.append("LEC暴露在危险中的概率["+obj.getLecExposureTemp()+"]不存在。");
                } else {
                    obj.setLecExposure(Double.parseDouble(lecExposure));
                }
            }
        }

		//内部市场价格
		if(StringUtil.isEmpty(obj.getInternalMarketPrice())){
			builder.append("内部市场价格不能为空。");
		}else{
            obj.setInternalMarketPrice(obj.getInternalMarketPrice().trim());
        }
		//备案号
		if(StringUtil.isEmpty(obj.getCaseNum())){
			builder.append("备案号不能为空。");
		}else{
            obj.setCaseNum(obj.getCaseNum().trim());
        }
		//管理标准
		if(StringUtil.isEmpty(obj.getMangStandards())){
			builder.append("管理标准不能为空。");
		}else{
            obj.setMangStandards(obj.getMangStandards().trim());
        }
		//标准依据
		if(StringUtil.isEmpty(obj.getStandardAccordance())){
			builder.append("标准依据不能为空。");
		}else{
            obj.setStandardAccordance(obj.getStandardAccordance());
        }

//		//危险距离
//		if(StringUtil.isEmpty(obj.getDistance())){
//			builder.append("危险距离不能为空。");
//		}
//		//周边情况及相互影响因素
//		if(StringUtil.isEmpty(obj.getSurrounding())){
//			builder.append("周边情况及相互影响因素不能为空。");
//		}
		//风险后果描述
		if(StringUtil.isEmpty(obj.getRiskAffectDesc())){
			builder.append("风险后果描述不能为空。");
		}else{
            obj.setRiskAffectDesc(obj.getRiskAffectDesc().trim());
        }
//		//监控措施
//		if(StringUtil.isEmpty(obj.getMonitor())){
//			builder.append("监控措施不能为空。");
//		}
//		//应急措施
//		if(StringUtil.isEmpty(obj.getEmergency())){
//			builder.append("应急措施不能为空。");
//		}
		//管理措施
		if(StringUtil.isEmpty(obj.getMangMeasures())){
			builder.append("管理措施不能为空。");
		}else{
            obj.setMangMeasures(obj.getMangMeasures().trim());
        }
		//责任部门
		if(StringUtil.isEmpty(obj.getResDepart())){
			builder.append("风险损失不能为空。");
		}else{
            obj.setResDepart(obj.getResDepart().trim());
        }
		if(StringUtil.isNotEmpty(builder.toString())){
			success = false;
		}
		return new ExcelVerifyHanlderResult(success,builder.toString());
	}

}
