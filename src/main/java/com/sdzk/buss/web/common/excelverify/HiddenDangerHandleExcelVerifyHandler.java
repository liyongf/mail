package com.sdzk.buss.web.common.excelverify;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleExportEntity;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;

import java.util.Map;

public class HiddenDangerHandleExcelVerifyHandler implements IExcelVerifyHandler<TBHiddenDangerHandleExportEntity> {

    private Map<String,TBAddressInfoEntity> addressMap;
    private Map<String,TSDepart> departMap;
    private Map<String, TSUser> userMap;

    public HiddenDangerHandleExcelVerifyHandler(Map<String,TBAddressInfoEntity> addressMap, Map<String,TSDepart> departMap, Map<String, TSUser> userMap) {
        this.addressMap=addressMap;
        this.departMap=departMap;
        this.userMap=userMap;
    }

    @Override
    public ExcelVerifyHanlderResult verifyHandler(TBHiddenDangerHandleExportEntity obj) {
        StringBuilder builder = new StringBuilder();
        boolean success = true;

        if (obj.getExamType() == null) {
            builder.append("检查类型不能为空");
        }else {
            String examType = DicUtil.getTypeCodeByName("examType", obj.getExamType());
            if (StringUtils.isBlank(examType)){
                builder.append("检查类型["+obj.getExamType()+"]不存在。");
            } else {
                obj.setExamType(examType);
            }
        }

        if (obj.getExamDate() == null) {
            builder.append("日期不能为空，且格式为yyyy/MM/dd，例2018/06/06。");
        }

        if (StringUtils.isBlank(obj.getShift())) {
            builder.append("班次不能为空。");
        }else {
            String shift = DicUtil.getTypeCodeByName("workShift", obj.getShift());
            if (StringUtils.isBlank(shift)){
                builder.append("班次["+obj.getShift()+"]不存在。");
            } else {
                obj.setShift(shift);
            }
        }

        if (StringUtils.isBlank(obj.getAddress())) {
            builder.append("地点不能为空。");
        } else {
            TBAddressInfoEntity addressInfoEntity = addressMap.get(obj.getAddress());
            if(addressInfoEntity == null){
                builder.append("地点["+obj.getAddress()+"]不存在。");
            } else {
                obj.setAddressId(addressInfoEntity);
            }
        }

        if ("sjjc".equals(obj.getExamType())) {
            if (StringUtils.isBlank(obj.getSjjcDept())) {
                builder.append("上级检查部门不能为空。");
            }
            if (StringUtils.isBlank(obj.getSjjcCheckMan())) {
                builder.append("检查人（上级检查）不能为空。");
            }
        } else if ("kjaqdjc".equals(obj.getExamType())) {
            if (StringUtils.isBlank(obj.getItemId())) {
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
                builder.append("组员不能为空。");
            } else {
                StringBuffer userId = new StringBuffer();
                StringBuffer notExistUser = new StringBuffer();
                for (String realname : obj.getItemUserId().split(",")) {
                    TSUser user = userMap.get(realname.replaceAll(" ", ""));
                    if (user == null) {
                        if (StringUtils.isNotBlank(notExistUser.toString())) {
                            notExistUser.append(",");
                        }
                        notExistUser.append(realname);
                    } else {
                        if (StringUtils.isNotBlank(userId.toString())) {
                            userId.append(",");
                        }
                        userId.append(user.getId());
                    }
                }
                if (StringUtils.isNotBlank(notExistUser.toString())) {
                    builder.append("组员[" + notExistUser.toString() + "]不存在。");
                } else {
                    obj.setItemUserId(userId.toString());
                }
            }
        } else {
            if (StringUtils.isBlank(obj.getFillCardManId())) {
                builder.append("检查人不能为空。");
            } else {
                String ids = "";
                String manlist = String.valueOf(obj.getFillCardManId());
                String[] mans = manlist.split(",|，|、");
                for (String man : mans) {
                    if (man != null) {
                        TSUser user = userMap.get(man.trim());
                        if (user != null) {
                            if (ids == "") {
                                ids = ids + user.getId();
                            } else {
                                ids = ids + "," + user.getId();
                            }
                        } else {
                            builder.append("检查人[" + man + "]不存在。");
                        }
                    }
                }
                obj.setFillCardManName(obj.getFillCardManId());
                obj.setFillCardManId(ids);
            }
        }

        if (StringUtils.isBlank(obj.getDutyUnit())) {
            builder.append("责任单位不能为空。");
        }else {
            TSDepart depart = departMap.get(obj.getDutyUnit());
            if(depart == null){
                builder.append("责任单位["+obj.getDutyUnit()+"]不存在。");
            } else {
                obj.setDutyUnitId(depart);
            }
        }

        if (StringUtils.isBlank(obj.getDutyMan())) {
            builder.append("责任人不能为空。");
        }

        if (StringUtils.isBlank(obj.getSuperviseUnit())) {
            builder.append("督办单位不能为空。");
        }else {
            TSDepart depart = departMap.get(obj.getSuperviseUnit());
            if(depart == null){
                builder.append("督办单位["+obj.getSuperviseUnit()+"]不存在。");
            } else {
                obj.setSuperviseUnitId(depart);
            }
        }


        if (StringUtils.isBlank(obj.getHiddenDangerLevel())) {
            builder.append("隐患等级不能为空。");
        }else{
            String hiddenLevel = DicUtil.getTypeCodeByName("hiddenLevel", obj.getHiddenDangerLevel());
            if(StringUtils.isBlank(hiddenLevel)) {
                builder.append("隐患等级["+obj.getHiddenDangerLevel()+"]不存在。");
            }else{
                obj.setHiddenDangerLevel(hiddenLevel);
            }
        }

        if (StringUtils.isBlank(obj.getHiddenCategory())) {
            builder.append("隐患类别不能为空。");
        }else{
            String hiddenCategory = DicUtil.getTypeCodeByName("hiddenCate", obj.getHiddenCategory());
            if(StringUtils.isBlank(hiddenCategory)) {
                builder.append("隐患类别["+obj.getHiddenCategory() + "]不存在。");
            }else{
                obj.setHiddenCategory(hiddenCategory);
            }
        }

        if (StringUtils.isBlank(obj.getHiddenDangerType())) {
            builder.append("隐患类型不能为空。");
        }else{
            String hiddenType = DicUtil.getTypeCodeByName("hiddenType", obj.getHiddenDangerType());
            if(StringUtils.isBlank(hiddenType)) {
                builder.append("隐患类型["+obj.getHiddenDangerType()+"]不存在。");
            }else{
                obj.setHiddenDangerType(hiddenType);
            }
        }

        if (StringUtils.isBlank(obj.getProblemDesc())) {
            builder.append("问题描述不能为空。");
        }
        if (StringUtils.isBlank(obj.getDealType())) {
            builder.append("处理方式不能为空。");
        }
        if ("现场处理".equals(obj.getDealType())) {
            obj.setDealType(Constants.DEALTYPE_XIANCAHNG);
            if (StringUtils.isBlank(obj.getRectMeasures())) {
                builder.append("整改措施不能为空。");
            }
            if (StringUtils.isBlank(obj.getReviewMan())) {
                builder.append("复查人不能为空。");
            }else {
                TSUser user = userMap.get(obj.getReviewMan());
                if(user == null){
                    builder.append("复查人["+obj.getReviewMan()+"]不存在。");
                } else {
                    obj.setReviewManId(user);
                }
            }
            if (StringUtils.isBlank(obj.getReviewReport())) {
                builder.append("复查情况不能为空。");
            }
        } else if("限期整改".equals(obj.getDealType())){
            obj.setDealType(Constants.DEALTYPE_XIANQI);
            if (obj.getLimitDate() == null) {
                builder.append("限期日期不能为空，且格式为yyyy/MM/dd,例2018/06/06。");
            }
            if (StringUtils.isBlank(obj.getModifyMan())) {
                builder.append("整改人不能为空。");
            }else {
                TSUser user = userMap.get(obj.getModifyMan());
                if(user == null){
                    builder.append("整改人["+obj.getModifyMan()+"]不存在。");
                } else {
                    obj.setModifyMan(user.getId());
                }
            }
            if (obj.getModifyDate() == null) {
                builder.append("整改日期不能为空，且格式为yyyy/MM/dd,例2018/06/06。");
            }
            if (StringUtils.isBlank(obj.getModifyShift())) {
                builder.append("整改班次不能为空。");
            }else {
                String shift = DicUtil.getTypeCodeByName("workShift", obj.getModifyShift());
                if (StringUtils.isBlank(shift)){
                    builder.append("整改班次["+obj.getModifyShift()+"]不存在。");
                } else {
                    obj.setModifyShift(shift);
                }
            }
            if (StringUtils.isBlank(obj.getRectMeasures())) {
                builder.append("整改措施不能为空。");
            }
            if (StringUtils.isBlank(obj.getReviewMan())) {
                builder.append("复查人不能为空。");
            }else {
                TSUser user = userMap.get(obj.getReviewMan());
                if(user == null){
                    builder.append("复查人["+obj.getReviewMan()+"]不存在。");
                } else {
                    obj.setReviewMan(user.getId());
                }
            }
            if (obj.getReviewDate() == null) {
                builder.append("复查日期不能为空，且格式为yyyy/MM/dd,例2018/06/06。");
            }
            if (StringUtils.isBlank(obj.getReviewShift())) {
                builder.append("复查班次不能为空。");
            }else {
                String shift = DicUtil.getTypeCodeByName("workShift", obj.getReviewShift());
                if (StringUtils.isBlank(shift)){
                    builder.append("复查班次["+obj.getReviewShift()+"]不存在。");
                } else {
                    obj.setReviewShift(shift);
                }
            }
            if (StringUtils.isBlank(obj.getReviewReport())) {
                builder.append("复查情况不能为空。");
            }
        }
        if (StringUtil.isNotEmpty(builder.toString())) {
            success = false;
        }
        return new ExcelVerifyHanlderResult(success, builder.toString());
    }
}
