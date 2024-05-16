package com.sdzk.buss.web.common.excelverify;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.entity.result.ExcelVerifyHanlderResult;
import org.jeecgframework.poi.handler.inter.IExcelVerifyHandler;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 17-9-26.
 */
public class ThreeViolationsExcelVerifyHandler implements IExcelVerifyHandler<TBThreeViolationsEntity> {

    private SystemService systemService = (SystemService) ApplicationContextUtil.getContext().getBean("systemService");

    @Override
    public ExcelVerifyHanlderResult verifyHandler(TBThreeViolationsEntity bean) {
        StringBuilder sb = new StringBuilder();
        boolean success = true;
        if(bean.getVioDate() == null){
            sb.append("违章时间不能为空，且格式为yyyy/MM/dd，例2008/08/08。");
        }

        String shift = bean.getShift();
        if(StringUtils.isBlank(shift)){
            sb.append("班次不能为空。");
        }else{
            String shiftTemp = DicUtil.getTypeCodeByName("workShift", shift.trim());
            if(StringUtils.isBlank(shiftTemp)){
                sb.append("班次["+shift+"]不存在。");
            }else{
                bean.setShift(shiftTemp);
            }
        }
        String findUnits = bean.getFindUnits();
        if(StringUtils.isBlank(findUnits)){
            sb.append("查处单位不能为空。");
        }else{
            CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
            try{
                Short isDeleteNo = 0;
                cq.eq("departname",findUnits.trim());
                cq.eq("deleteFlag",isDeleteNo);
            }catch(Exception e){
                e.printStackTrace();
            }
            cq.add();
            List<TSDepart> departList = systemService.getListByCriteriaQuery(cq,false);
            if(departList == null || departList.size() == 0){
                sb.append("查处单位["+findUnits+"]不存在。");
            }else{
                bean.setFindUnits(departList.get(0).getId());
            }
        }

        String vioAddress = bean.getVioAddress();
        if(StringUtils.isBlank(vioAddress)){
            sb.append("违章地点不能为空。");
        }else{
            CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class);
            try{
                cq.eq("address",vioAddress.trim());
                cq.eq("isDelete","0");
            }catch(Exception e){
                e.printStackTrace();
            }
            cq.add();
            List<TBAddressInfoEntity> addressList = systemService.getListByCriteriaQuery(cq,false);
            if(addressList == null || addressList.size() == 0){
                sb.append("违章地点["+vioAddress+"]不存在。");
            }else{
                bean.setVioAddress(addressList.get(0).getId());
            }
        }

        String vioUnit = bean.getVioUnits();
        if(StringUtils.isBlank(vioUnit)){
            sb.append("违章单位不能为空。");
        }else{
            CriteriaQuery cq = new CriteriaQuery(TSDepart.class);
            try{
                Short isDeleteNo = 0;
                cq.eq("departname",vioUnit.trim());
                cq.eq("deleteFlag",isDeleteNo);
            }catch(Exception e){
                e.printStackTrace();
            }
            cq.add();
            List<TSDepart> departList = systemService.getListByCriteriaQuery(cq,false);
            if(departList == null || departList.size() == 0){
                sb.append("违章单位["+vioUnit+"]不存在");
            }else{
                bean.setVioUnits(departList.get(0).getId());
            }
        }

        String vioPeo = bean.getVioPeople();
        if(StringUtils.isBlank(vioPeo)){
            sb.append("违章人员不能为空。");
        }
        String xinchazhuang = ResourceUtil.getConfigByName("xinchazhuang");
        if(StringUtil.isNotEmpty(xinchazhuang)){
            if(xinchazhuang.equals("true")){
                String employeeNum = bean.getEmployeeNum();
                if(StringUtils.isBlank(employeeNum)){
                    sb.append("职工编号不能为空。");
                }
            }
        }


        String vioCategory = bean.getVioCategory();
        if(StringUtils.isBlank(vioCategory)){
            sb.append("违章分类不能为空。");
        }else{
            String vioCategoryTemp = DicUtil.getTypeCodeByName("violaterule_wzfl", vioCategory.trim());
            if(StringUtils.isBlank(vioCategoryTemp)){
                sb.append("违章分类["+vioCategory+"]不存在。");
            }else{
                bean.setVioCategory(vioCategoryTemp);
            }
        }

        String vioQualitative = bean.getVioQualitative();
        if(StringUtils.isBlank(vioQualitative)){
            sb.append("违章定性不能为空。");
        }else{
            String vioQualitativeTemp =  DicUtil.getTypeCodeByName("violaterule_wzdx", vioQualitative.trim());
            if(StringUtils.isBlank(vioQualitativeTemp)){
                sb.append("违章定性["+vioQualitative+"]不存在");
            }else{
                bean.setVioQualitative(vioQualitativeTemp);
            }
        }

        String vioLevel = bean.getVioLevel();
        if(StringUtils.isBlank(vioLevel)){
            sb.append("三违级别不能为空。");
        }else{
            String vioLevelTemp = DicUtil.getTypeCodeByName("vio_level", vioLevel.trim());
            if(StringUtils.isBlank(vioLevelTemp)){
                sb.append("三违级别["+vioLevel+"]不存在。");
            }else{
                bean.setVioLevel(vioLevelTemp);
            }
        }

        String stopPeo = bean.getStopPeople();
        if(StringUtils.isBlank(stopPeo)){
            sb.append("制止人不能为空。");
        }

        String isFineTemp = bean.getIsFineTemp();
        if(StringUtils.isNotBlank(isFineTemp)){
           if(isFineTemp.equals("是")){
               String fineProperty =  bean.getFinePropertyTemp();
               if(StringUtils.isBlank(fineProperty)){
                   sb.append("罚款性质不能为空。");
               }else{
                   String finePropertyTemp = DicUtil.getTypeCodeByName("fineProperty", fineProperty.trim());
                   if(StringUtils.isBlank(finePropertyTemp)){
                       sb.append("罚款性质["+fineProperty+"]不存在。");
                   }else{
                       bean.setFinePropertyTemp(finePropertyTemp);
                   }
               }
               String fineMoneyTemp =  bean.getFineMoneyTemp();
               if(StringUtils.isBlank(fineMoneyTemp)){
                   sb.append("罚款金额不能为空");
               }else{
                   try {
                       int num = Integer.parseInt(fineMoneyTemp);
                       if(num >0){
                           bean.setFineMoneyTemp(String.valueOf(num));
                       }else{
                           sb.append("罚款金额必须输入正整数");
                       }
                   }catch (NumberFormatException e){
                       sb.append("罚款金额必须输入正整数");
                   }
               }
               bean.setIsFineTemp("1");
           }
        }


        if(StringUtil.isNotEmpty(sb.toString())){
            success = false;
        }
        return new ExcelVerifyHanlderResult(success,sb.toString());
    }
}
