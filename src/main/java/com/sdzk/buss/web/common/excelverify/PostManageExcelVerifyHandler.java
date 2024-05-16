package com.sdzk.buss.web.common.excelverify;

import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;


public class PostManageExcelVerifyHandler implements IExcelVerifyHandler<TBPostManageEntity> {

    /**
     * 去除字符串中的空格
     */
    private String deleteNull(String str){
        String s=str.replaceAll("\\s*","");
        return s;
    }
    @Override
    public ExcelVerifyHanlderResult verifyHandler(TBPostManageEntity obj) {
        StringBuilder builder = new StringBuilder();
        boolean success = true;
        //添加判断
        //岗位名称
        if(StringUtil.isEmpty(obj.getPostName())){
            builder.append("岗位名称不能为空。");
        }else{
            obj.setPostName(deleteNull(obj.getPostName()));
        }
     /*   if(StringUtil.isEmpty(obj.getProfessionType())){
            builder.append("专业不能为空。");
        }else {
            String professionalType = DicUtil.getTypeCodeByName("proCate_gradeControl", obj.getProfessionType());
            if(StringUtil.isEmpty(professionalType)){
                builder.append("专业分类["+obj.getProfessionType()+"]不存在。");
            } else {
                obj.setProfessionType(professionalType);
            }
        }*/

        if(StringUtil.isNotEmpty(builder.toString())){
            success = false;
        }
        return new ExcelVerifyHanlderResult(success,builder.toString());
    }
}
