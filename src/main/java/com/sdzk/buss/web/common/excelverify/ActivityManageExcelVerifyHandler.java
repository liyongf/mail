package com.sdzk.buss.web.common.excelverify;

import com.sdzk.buss.web.dangersource.entity.TbActivityManageEntity;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;

public class ActivityManageExcelVerifyHandler implements IExcelVerifyHandler<TbActivityManageEntity> {

    /**
     * 去除字符串中的空格
     */
    private String deleteNull(String str){
        String s=str.replaceAll("\\s*","");
        return s;
    }
    @Override
    public ExcelVerifyHanlderResult verifyHandler(TbActivityManageEntity obj) {
        StringBuilder builder = new StringBuilder();
        boolean success = true;
        //添加判断
        //作业活动名称
        if(StringUtil.isEmpty(obj.getActivityName())){
            builder.append("作业活动名称不能为空。");
        }else{
            obj.setActivityName(obj.getActivityName().trim());
            obj.setActivityName(deleteNull(obj.getActivityName()));
        }
      /*  if(StringUtil.isEmpty(obj.getProfessionType())){
            builder.append("专业不能为空。");
        }else {
            String professionalType = DicUtil.getTypeCodeByName("proCate_gradeControl", obj.getProfessionType());
            if(StringUtil.isEmpty(professionalType)){
                builder.append("专业分类["+obj.getProfessionType()+"]不存在。");
            } else {
                obj.setProfessionType(professionalType);
            }
        }
*/
        if(StringUtil.isNotEmpty(builder.toString())){
            success = false;
        }
        return new ExcelVerifyHanlderResult(success,builder.toString());
    }

}