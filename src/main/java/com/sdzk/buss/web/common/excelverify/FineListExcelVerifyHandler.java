package com.sdzk.buss.web.common.excelverify;

import com.sdzk.buss.web.fine.entity.TBFineEntity;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.*;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import sun.util.calendar.BaseCalendar;

import java.text.SimpleDateFormat;
import java.util.List;

public class FineListExcelVerifyHandler implements IExcelVerifyHandler<TBFineEntity> {
    private SystemService systemService = (SystemService) ApplicationContextUtil.getContext().getBean("systemService");

//    private boolean checkFineExit(String postName){
//        List<TBPostManageEntity> entitys = systemService.findByProperty(TBPostManageEntity.class, "postName", postName);
//        if (entitys != null && entitys.size() > 0) {
//            for(TBPostManageEntity bean : entitys){
//                if(!(bean.getIsDelete().isEmpty())) {
//                    if (bean.getIsDelete().equals("0")) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }
//        return false;
//    }
    List<TBFineEntity> tbFineEntityList = systemService.getList(TBFineEntity.class);
    int count = tbFineEntityList.size()%1000;

    private String generateNum(){
        String strNum = "";
        java.util.Date strDate = DateUtils.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");

        strNum = strNum + count;

        while(strNum.length() < 4){
            strNum = "0" + strNum;
            count++;
        }
        String temp = DateUtils.date2Str(strDate,sdf);
        temp = temp.substring(2, temp.length());

        return temp + strNum;
    }

    @Override
    public ExcelVerifyHanlderResult verifyHandler(TBFineEntity obj) {
        StringBuilder builder = new StringBuilder();
        boolean success = true;
        if(StringUtil.isEmpty(obj.getBeFinedMan())){
            builder.append("被罚款人不能为空。");
        }

        if(StringUtil.isEmpty(obj.getDutyUnitTemp())){
            builder.append("责任单位不能为空。");
        }else{
            List<TSDepart> tsDeparts = systemService.findByQueryString("from TSDepart where deleteFlag = '0' and departname = '"+ obj.getDutyUnitTemp() +"'");
            if(tsDeparts != null && tsDeparts.size() > 0){
                obj.setDutyUnit(tsDeparts.get(0));
            }else{
                builder.append("该责任单位不存在。");
            }
        }

        //罚款性质转换
        if(StringUtil.isEmpty(obj.getFinePropertyTemp())){
            builder.append("罚款性质不能为空。");
        }else{
            String fp = DicUtil.getTypeCodeByName("fineProperty", obj.getFinePropertyTemp());
            if(StringUtil.isEmpty(fp)){
                builder.append("罚款性质不存在！");
            }else{
                obj.setFineProperty(fp);
            }
        }

        if (StringUtil.isEmpty(obj.getFineMan())){
            builder.append("罚款人不能为空。");
        }
        if(StringUtil.isEmpty(obj.getContent())){
            builder.append("内容不能为空。");
        }
        String xiezhuang = ResourceUtil.getConfigByName("xiezhuang");
       if(xiezhuang.equals("true")){
           if(StringUtil.isEmpty(obj.getFineMoneyTemp())){
               builder.append("责任人罚款金额不能为空");
           }else{
               try {
                   int num = Integer.parseInt(obj.getFineMoneyTemp());
                   if(num >0){
                       obj.setFineMoney(String.valueOf(num));
                   }else{
                       builder.append("责任人罚款金额必须输入正整数");
                   }
               }catch(NumberFormatException e){
                   builder.append("责任人罚款金额必须输入正整数");
               }
           }
           if(StringUtil.isEmpty(obj.getUnitFineMoney())){
               builder.append("单位罚款金额不能为空");
           }else{
               try {
                   int num = Integer.parseInt(obj.getUnitFineMoney());
                   if(num >0){
                       obj.setFineMoney(String.valueOf(num));
                   }else{
                       builder.append("单位罚款金额必须输入正整数");
                   }
               }catch(NumberFormatException e){
                   builder.append("单位罚款金额必须输入正整数");
               }
           }
       }else{
           if(StringUtil.isEmpty(obj.getFineMoney())){
               builder.append("罚款金额不能为空");
           }else{
               try {
                   int num = Integer.parseInt(obj.getFineMoney());
                   if(num >0){
                       obj.setFineMoney(String.valueOf(num));
                   }else{
                       builder.append("罚款金额必须输入正整数");
                   }
               }catch(NumberFormatException e){
                   builder.append("罚款金额必须输入正整数");
               }
           }
       }


        if(StringUtil.isNotEmpty(builder.toString())){
            success = false;
        }

        obj.setFineNum(generateNum());
        obj.setUpdateDate(DateUtils.getDate());
        obj.setIsValidity("0");
        return new ExcelVerifyHanlderResult(success,builder.toString());
    }

}
