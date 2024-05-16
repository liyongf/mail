package com.sddb.common.excelverify;

import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import com.sddb.buss.riskdata.entity.HazardModuleVoEntity;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

public class HazardFactorsModuleExcelVerifyHandler implements IExcelVerifyHandler<HazardModuleVoEntity> {
    @Override
    public ExcelVerifyHanlderResult verifyHandler(HazardModuleVoEntity obj) {
        StringBuilder builder = new StringBuilder();
        boolean success = true;


        if(StringUtil.isEmpty(obj.getRiskTypeTemp())){
            builder.append("风险类型不能为空。");
        }else{
            obj.setRiskTypeTemp(obj.getRiskTypeTemp().trim());
            if(StringUtil.isEmpty(DicUtil.getTypeCodeByName("risk_type", obj.getRiskTypeTemp()))){
                builder.append("风险类型["+obj.getRiskTypeTemp()+"]不存在。");
            }
        }
        if(StringUtil.isEmpty(obj.getHazardFactorsTemp())){
            builder.append("危害因素不能为空。");
        } else {
            obj.setHazardFactorsTemp(obj.getHazardFactorsTemp().trim());
        }
        if(StringUtil.isEmpty(obj.getModuleNameTemp())){
            builder.append("模块危害因素不能为空。");
        } else {
            obj.setModuleNameTemp(obj.getModuleNameTemp().trim().replaceAll("\n","").replaceAll("\r","").replaceAll(" ",""));
        }


        if(StringUtil.isNotEmpty(builder.toString())){
            success = false;
        }
        return new ExcelVerifyHanlderResult(success,builder.toString());
    }
}
