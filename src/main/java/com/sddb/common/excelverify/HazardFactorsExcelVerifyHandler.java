package com.sddb.common.excelverify;

import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

public class HazardFactorsExcelVerifyHandler implements IExcelVerifyHandler<HazardFactorsEntity> {
    @Override
    public ExcelVerifyHanlderResult verifyHandler(HazardFactorsEntity obj) {
        StringBuilder builder = new StringBuilder();
        boolean success = true;

        if(StringUtil.isEmpty(obj.getMajorTemp())){
            builder.append("专业不能为空。");
        }else{
            obj.setMajorTemp(obj.getMajorTemp().trim());
            if(StringUtil.isEmpty(DicUtil.getTypeCodeByName("major", obj.getMajorTemp()))){
                builder.append("专业["+obj.getMajorTemp()+"]不存在。");
            }
        }
        if(StringUtil.isEmpty(obj.getRiskLevelTemp())){
            builder.append("危害因素等级不能为空。");
        }else{
            obj.setRiskLevelTemp(obj.getRiskLevelTemp().trim());
            if(StringUtil.isEmpty(DicUtil.getTypeCodeByName("factors_level", obj.getRiskLevelTemp()))){
                builder.append("危害因素等级["+obj.getRiskLevelTemp()+"]不存在。");
            }
        }
        if(StringUtil.isEmpty(obj.getRiskTypeTemp())){
            builder.append("风险类型不能为空。");
        }else{
            obj.setRiskTypeTemp(obj.getRiskTypeTemp().trim());
            if(StringUtil.isEmpty(DicUtil.getTypeCodeByName("risk_type", obj.getRiskTypeTemp()))){
                builder.append("风险类型["+obj.getRiskTypeTemp()+"]不存在。");
            }
        }
        if(StringUtil.isEmpty(obj.getHazardFactors())){
            builder.append("危害因素不能为空。");
        } else {
            obj.setHazardFactors(obj.getHazardFactors().trim());
        }
        if (StringUtil.isEmpty(obj.getManageMeasure())) {
            builder.append("管控措施不能为空。");
        } else {
            obj.setManageMeasure(obj.getManageMeasure().trim());
        }

        if (StringUtil.isEmpty(obj.getPostName())) {
            builder.append("岗位不能为空。");
        } else {
            obj.setPostName(obj.getPostName().trim());
        }

        if(StringUtil.isNotEmpty(builder.toString())){
            success = false;
        }
        return new ExcelVerifyHanlderResult(success,builder.toString());
    }
}
