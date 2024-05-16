package com.sdzk.buss.web.common.excelverify;

import com.sdzk.buss.web.dangersource.entity.TbHazardManageEntity;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;




public class HazardManageExcelVerifyHandler implements IExcelVerifyHandler<TbHazardManageEntity> {

    /**
     * 去除字符串中的空格
     */
    private String deleteNull(String str){
        String s=str.replaceAll("\\s*","");
        return s;
    }
    @Override
    public ExcelVerifyHanlderResult verifyHandler(TbHazardManageEntity obj) {
        StringBuilder builder = new StringBuilder();
        boolean success = true;
        //添加判断
        //危险源名称
        if(StringUtil.isEmpty(obj.getHazardName())){
            builder.append("危险源名称不能为空。");
        }else{
            obj.setHazardName(deleteNull(obj.getHazardName()));
        }

        if(StringUtil.isEmpty(obj.getHazardType())){
            builder.append("危险源种类不能为空。");
        }else {
            String hazardType = DicUtil.getTypeCodeByName("dangerSource_type", obj.getHazardType());
            if(StringUtil.isEmpty(hazardType)){
                builder.append("危险源种类["+obj.getHazardType()+"]不存在。");
            } else {
                obj.setHazardType(hazardType);
            }
        }
        //危害类型
       /* if(StringUtil.isNotEmpty(obj.getDamageType())){
            StringBuffer damages = new StringBuffer();
            StringBuffer exists = new StringBuffer();
            for(String damage : obj.getDamageType().split("[，、,]+")){
                String cate = DicUtil.getTypeCodeByName("danger_Category", damage);
                if(StringUtil.isEmpty(cate)){
                    if (StringUtil.isNotEmpty(damages.toString())) {
                        damages.append(",");
                    }
                    damages.append(damage);
                } else {
                    if (StringUtil.isNotEmpty(exists.toString())) {
                        exists.append(",");
                    }
                    exists.append(cate);
                }
            }
            if(StringUtil.isNotEmpty(damages.toString())){
                builder.append("危害类别["+damages.toString()+"]不存在。");
            } else {
                obj.setDamageType(exists.toString());
            }
        }*/
        //事故类型
        /*if(StringUtil.isNotEmpty(obj.getAccidentType())){
            StringBuffer accidents = new StringBuffer();
            StringBuffer exists = new StringBuffer();
            for(String accident : obj.getAccidentType().split("[，、,]+")){
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
                builder.append("事故类别["+accidents.toString()+"]不存在。");
            } else {
                obj.setAccidentType(exists.toString());
            }
        }*/

        if(StringUtil.isNotEmpty(builder.toString())){
            success = false;
        }
        return new ExcelVerifyHanlderResult(success,builder.toString());
    }

}