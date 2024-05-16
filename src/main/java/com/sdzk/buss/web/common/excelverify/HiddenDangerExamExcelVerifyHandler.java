package com.sdzk.buss.web.common.excelverify;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.CommonUtil;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class HiddenDangerExamExcelVerifyHandler implements IExcelVerifyHandler<TBHiddenDangerExamEntity> {

	private String examType=null;
	private Map<String,TBAddressInfoEntity> addressMap;
	private Map<String,TSDepart> departMap;
	private Map<String, TSUser> userMap;
	public HiddenDangerExamExcelVerifyHandler(String examType, Map<String,TBAddressInfoEntity> addressMap,Map<String,TSDepart> departMap,Map<String, TSUser> userMap){
		this.examType=examType;
		this.addressMap=addressMap;
		this.departMap=departMap;
		this.userMap=userMap;
	}

	@Override
	public ExcelVerifyHanlderResult verifyHandler(TBHiddenDangerExamEntity obj) {
		StringBuilder builder = new StringBuilder();
		boolean success = true;
		if (obj.getExamDate() == null) {
			builder.append("日期不能为空，且格式为yyyy/MM/dd，例2018/06/06。");
		}
		if(StringUtils.isBlank(obj.getShift())){
			builder.append("班次不能为空。");
		} else {
			String shift = DicUtil.getTypeCodeByName("workShift", obj.getShift());
			if (StringUtils.isBlank(shift)){
				builder.append("班次["+obj.getShift()+"]不存在。");
			} else {
				obj.setShift(shift);
			}
		}
		if(StringUtils.isBlank(obj.getAddressName())){
			builder.append("地点不能为空。");
		} else {
			TBAddressInfoEntity addressInfoEntity = addressMap.get(obj.getAddressName());
			if(addressInfoEntity == null){
				builder.append("地点["+obj.getAddressName()+"]不存在。");
			} else {
				obj.setAddress(addressInfoEntity);
			}
		}

		if("sjjc".equals(examType)){//上级检查
			if (StringUtils.isBlank(obj.getSjjcDept())) {
				builder.append("上级检查部门不能为空。");
			}
			if (StringUtils.isBlank(obj.getSjjcCheckMan())) {
				builder.append("检查人不能为空");
			}
		}else if("kjaqdjc".equals(examType)){//矿井安全大检查
			if(StringUtils.isBlank(obj.getItemId())){
				builder.append("组别不能为空。");
			} else {
				String item = DicUtil.getTypeCodeByName("group", obj.getItemId());
				if (StringUtils.isBlank(item)){
					builder.append("组别["+obj.getItemId()+"]不存在。");
				} else {
					obj.setItemId(item);
				}
			}
			if (StringUtils.isBlank(obj.getItemUserId())) {
				builder.append("组员不能为空");
			} else {
				StringBuffer userId = new StringBuffer();
				StringBuffer notExistUser = new StringBuffer();
				for (String userName : obj.getItemUserId().split(",")) {
					TSUser user = userMap.get(userName.replaceAll(" ",""));
					if (user == null) {
						if (StringUtils.isNotBlank(notExistUser.toString())) {
							notExistUser.append(",");
						}
						notExistUser.append(userName);
					} else {
						if (StringUtils.isNotBlank(userId.toString())) {
							userId.append(",");
						}
						userId.append(user.getId());
					}
				}
				if (StringUtils.isNotBlank(notExistUser.toString())) {
					builder.append("组员["+notExistUser.toString()+"]不存在。");
				} else {
					obj.setItemUserId(userId.toString());
				}
			}
		}else if("yh".equals(examType)){
			if (StringUtils.isBlank(obj.getSjjcCheckMan())) {
				builder.append("检查人不能为空");
			} else {
				String ids="";
				String manlist = String.valueOf(obj.getSjjcCheckMan());
				String[] mans = manlist.split(",|，|、");
				for(String man : mans){
					if(man!=null){
						TSUser user =  userMap.get(man.trim());
						if(user!=null){
							if(ids == ""){
								ids = ids + user.getId();
							}else{
								ids = ids + "," + user.getId();
							}
						}else if(StringUtil.isNotEmpty(man.trim())){
							if(ids == ""){
								ids = ids + man.trim();
							}else{
								ids = ids + "," + man.trim();
							}
						}
					}
				}

				obj.setFillCardManId(ids);
				/*TSUser user = userMap.get(obj.getSjjcCheckMan());
				if (user == null) {
					builder.append("检查人["+obj.getSjjcCheckMan()+"]不存在。");
				} else {
					obj.setFillCardMan(user);
					obj.setSjjcCheckMan(null);
				}*/
			}
		}else{
			if (StringUtils.isBlank(obj.getSjjcCheckMan())) {
				builder.append("检查人不能为空");
			} else {
                String ids="";
                String manlist = String.valueOf(obj.getSjjcCheckMan());
                String[] mans = manlist.split(",|，|、");
                for(String man : mans){
                    if(man!=null){
                        TSUser user =  userMap.get(man.trim());
                        if(user!=null){
                            if(ids == ""){
                                ids = ids + user.getId();
                            }else{
                                ids = ids + "," + user.getId();
                            }
                        }else{
                            builder.append("检查人["+man+"]不存在。");
                        }
                    }
                }

                obj.setFillCardManId(ids);
				/*TSUser user = userMap.get(obj.getSjjcCheckMan());
				if (user == null) {
					builder.append("检查人["+obj.getSjjcCheckMan()+"]不存在。");
				} else {
					obj.setFillCardMan(user);
					obj.setSjjcCheckMan(null);
				}*/
			}
		}
		if (StringUtils.isBlank(obj.getDutyUnitName())) {
			builder.append("责任单位不能为空。");
		} else {
			TSDepart depart = departMap.get(obj.getDutyUnitName());
			if(depart == null){
				builder.append("责任部门["+obj.getDutyUnitName()+"]不存在。");
			} else {
				obj.setDutyUnit(depart);
			}
		}
		if (StringUtils.isBlank(obj.getDutyMan())) {
			builder.append("责任人不能为空。");
		}else{
			TSUser user = userMap.get(obj.getDutyMan());
			if(user != null) {
				obj.setDutyManId(user.getId());
			}

		}

		/*if(StringUtils.isBlank(obj.getHiddenCategory())){
			builder.append("隐患类别不能为空。");
		}else{
			String hiddenCategory = DicUtil.getTypeCodeByName("hiddenCate", obj.getHiddenCategory());
			if(StringUtils.isBlank(hiddenCategory)) {
				builder.append("隐患类别["+obj.getHiddenCategory() + "]不存在。");
			}else{
				obj.setHiddenCategory(hiddenCategory);
			}
		}*/

		if (StringUtils.isBlank(obj.getHiddenNature())) {
			builder.append("隐患等级不能为空。");
		} else{
			String hiddenLevel = DicUtil.getTypeCodeByName("hiddenLevel", obj.getHiddenNature());
			if(StringUtils.isBlank(hiddenLevel)) {
				builder.append("隐患等级["+obj.getHiddenNature()+"]不存在。");
			}else{
				obj.setHiddenNature(hiddenLevel);
				obj.setHiddenNatureOriginal(hiddenLevel);
			}
		}
		//风险类型修改为隐患类型
		if (StringUtils.isBlank(obj.getRiskType())) {
			builder.append("隐患类型不能为空。");
		} else{
			String riskType = DicUtil.getTypeCodeByName("risk_type", obj.getRiskType());
			if(StringUtils.isBlank(riskType)) {
				builder.append("隐患类型["+obj.getRiskType()+"]不存在。");
			}else{
				obj.setRiskType(riskType);
			}
		}

		if (StringUtils.isBlank(obj.getManageType())) {
			builder.append("信息来源不能为空。");
		} else{
			String manageType = DicUtil.getTypeCodeByName("manageType", obj.getManageType());
			if(StringUtils.isBlank(manageType)) {
				builder.append("信息来源["+obj.getManageType()+"]不存在。");
			}else{
				obj.setManageType(manageType);
			}
		}

		if(StringUtils.isBlank(obj.getProblemDesc())){
			builder.append("问题描述不能为空。");
		}

		if("现场处理".equals(obj.getDealType())){
			obj.setDealType(Constants.DEALTYPE_XIANCAHNG);
			if (StringUtils.isBlank(obj.getReviewManTemp())) {
				builder.append("复查人不能为空。");
			} else{
				TSUser user = userMap.get(obj.getReviewManTemp());
				if(user == null) {
					builder.append("复查人["+obj.getReviewManTemp()+"]不存在。");
				}else{
					obj.setReviewMan(user);
				}
			}
		} else {
			if (obj.getLimitDate() == null) {
				builder.append("限期日期不能为空，且格式为yyyy/MM/dd,例2018/06/06。");
			}
			obj.setDealType(Constants.DEALTYPE_XIANQI);
		}

		if (StringUtils.isBlank(obj.getManageDutyUnitName())) {
			builder.append("管控责任单位不能为空。");
		} else {
			TSDepart depart = departMap.get(obj.getManageDutyUnitName());
			if(depart == null){
				builder.append("管控责任单位["+obj.getManageDutyUnitName()+"]不存在。");
			} else {
				obj.setManageDutyUnit(depart);
			}
		}
		if (StringUtils.isBlank(obj.getManageDutyManTemp())) {
			builder.append("管控责任人不能为空。");
		} else {
			TSUser user = userMap.get(obj.getManageDutyManTemp());
			if(user == null){
				builder.append("管控责任人["+obj.getManageDutyManTemp()+"]不存在。");
			} else {
				obj.setManageDutyManId(user.getId());
			}
		}

		if(StringUtil.isNotEmpty(builder.toString())){
			success = false;
		}
		return new ExcelVerifyHanlderResult(success,builder.toString());
	}

}
