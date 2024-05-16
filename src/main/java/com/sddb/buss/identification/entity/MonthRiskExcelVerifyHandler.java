package com.sddb.buss.identification.entity;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

import java.util.Date;
import java.util.Map;

public class MonthRiskExcelVerifyHandler implements IExcelVerifyHandler<MonthRiskIdentificationEntity> {

    private Map<String, TBAddressInfoEntity> addressMap;
    private String type;

    public MonthRiskExcelVerifyHandler(Map<String, TBAddressInfoEntity> addressMap, String type) {
        this.addressMap=addressMap;
        this.type=type;
    }

    @Override
    public ExcelVerifyHanlderResult verifyHandler(MonthRiskIdentificationEntity obj) {
        StringBuilder builder = new StringBuilder();
        boolean success = true;
        obj.setType(type);

        if(type.equals("month")){
            if (StringUtils.isBlank(obj.getMonth())) {
                builder.append("月份不能为空。");
            }else{
                Date date=   DateUtils.str2Date(obj.getMonth()+"-01", DateUtils.date_sdf);
                if(date==null){
                    builder.append("月份格式不正确。举例：2023-01");
                }
            }
        }else{
            if (StringUtils.isBlank(obj.getYear())) {
                builder.append("年份不能为空。");
            }else{
                Date date=   DateUtils.str2Date(obj.getYear()+"-01-01", DateUtils.date_sdf);
                if(date==null){
                    builder.append("年份格式不正确。举例：2023");
                }else{
                    obj.setMonth(obj.getYear());
                }
            }

            if(StringUtils.isBlank(obj.getQuarter())){
                builder.append("季度不能为空。");
            }else{
                String quarter = DicUtil.getTypeCodeByName("quarter", obj.getQuarter());
                if (StringUtils.isBlank(quarter)){
                    builder.append("季度["+obj.getQuarter()+"]不存在。");
                } else {
                    obj.setQuarter(quarter);
                }
            }

        }

        if (obj.getUnitSpecialty() == null) {
            builder.append("单位专业不能为空。");
        }
        if (obj.getHazardFactors() == null) {
            builder.append("危害因素不能为空。");
        }

        if (StringUtils.isBlank(obj.getRiskType())) {
            builder.append("风险类型不能为空。");
        }else {
            String riskType = DicUtil.getTypeCodeByName("risk_type", obj.getRiskType());
            if (StringUtils.isBlank(riskType)){
                builder.append("风险类型["+obj.getRiskType()+"]不存在。");
            } else {
                obj.setRiskType(riskType);
            }
        }

        if (StringUtils.isBlank(obj.getIdentificationType())) {
            builder.append("信息来源不能为空。");
        }else {
            String identificationType = DicUtil.getTypeCodeByName("month_risk_source", obj.getIdentificationType());
            if (StringUtils.isBlank(identificationType)){
                builder.append("信息来源["+obj.getIdentificationType()+"]不存在。");
            } else {
                obj.setIdentificationType(identificationType);
            }
        }

        if (StringUtils.isBlank(obj.getRiskLevel())) {
            builder.append("风险等级不能为空。");
        }else {
            String riskLevel = DicUtil.getTypeCodeByName("factors_level", obj.getRiskLevel());
            if (StringUtils.isBlank(riskLevel)){
                builder.append("风险等级["+obj.getRiskLevel()+"]不存在。");
            } else {
                obj.setRiskLevel(riskLevel);
            }
        }

        if (StringUtils.isBlank(obj.getAddress())) {
            builder.append("风险地点不能为空。");
        } else {
            TBAddressInfoEntity addressInfoEntity = addressMap.get(obj.getAddress());
            if(addressInfoEntity == null){
                builder.append("风险地点["+obj.getAddress()+"]不存在。");
            } else {
                obj.setAddress(addressInfoEntity.getId());
            }
        }

        if (StringUtils.isBlank(obj.getRiskDesc())) {
            builder.append("风险描述不能为空。");
        }

        if (StringUtils.isBlank(obj.getControlMeasures())) {
            builder.append("管控措施不能为空。");
        }
        obj.setIsDel("0");
        if (StringUtil.isNotEmpty(builder.toString())) {
            success = false;
        }
        return new ExcelVerifyHanlderResult(success, builder.toString());
    }
}
