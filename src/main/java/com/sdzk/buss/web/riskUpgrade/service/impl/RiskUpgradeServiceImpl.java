package com.sdzk.buss.web.riskUpgrade.service.impl;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.majorhiddendanger.entity.TBMajorHiddenDangerEntity;
import com.sdzk.buss.web.riskUpgrade.entity.TBHiddenDangerSourceLevelMapping;
import com.sdzk.buss.web.riskUpgrade.service.RiskUpgradeServiceI;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jeecgframework.web.system.service.SystemService;

import java.util.*;

/**
 * Created by Administrator on 17-9-23.
 */
@Service("riskUpgradeService")
@Transactional
public class RiskUpgradeServiceImpl extends CommonServiceImpl implements RiskUpgradeServiceI {

    @Autowired
    public SystemService systemService;

    @Override
    public void execute(String hiddenId) {
        WorkThread thread = new WorkThread();
        thread.setHiddenId(hiddenId);
        thread.run();
    }

    public synchronized static void upgradeRisk(String hiddenId,SystemService systemService){
        //1.  查出当前隐患关联的风险（危险源）和风险点（地点）
        //1.1先查看是否是重大隐患
        boolean isMajorHidden = isMajorHidden(hiddenId,systemService);
        //1.2取出危险源编号
        String dangerSourceId = null;
        if(isMajorHidden){
            //重大隐患对应危险源编号
            dangerSourceId = getMajorHiddenDangerSourceId(hiddenId,systemService);
        }else{
            //日常隐患对应危险源编号
            dangerSourceId = getHiddenDangerSourceId(hiddenId,systemService);
        }
        String address = getCurrHiddenAddress(isMajorHidden,hiddenId,systemService);
        //2.  根据当前危险源查询当前未闭合的隐患（包含重大隐患）
        //2.1 取出未闭合重大隐患列表
        List<TBMajorHiddenDangerEntity> majorHiddenDangerEntityList = getMajorHiddenList(address,dangerSourceId,systemService);
        //2.2 取出未闭合日常隐患列表
        List<TBHiddenDangerExamEntity> hiddenDangerExamEntityList = getHiddenDangerExamList(address,dangerSourceId,systemService);
        //2.1 如果包含重大隐患，则该风险点（地点）对应的风险（危险源）等级为最高等级即重大隐患对应的风险等级（对应关系查询对应关系表）
        if((majorHiddenDangerEntityList != null && !majorHiddenDangerEntityList.isEmpty()) || (hiddenDangerExamEntityList != null && !hiddenDangerExamEntityList.isEmpty())){
            //有未闭合隐患
            if(majorHiddenDangerEntityList != null && !majorHiddenDangerEntityList.isEmpty()){
                TBMajorHiddenDangerEntity bean =  majorHiddenDangerEntityList.get(0);
                String hiddenLevel = bean.getHdLevel();
                CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerSourceLevelMapping.class);
                try{
                    cq.eq("hiddenLevel",hiddenLevel);
                }catch(Exception e){
                    e.printStackTrace();
                }
                cq.add();
                List<TBHiddenDangerSourceLevelMapping> mappingList = systemService.getListByCriteriaQuery(cq,false);
                if(mappingList != null && !mappingList.isEmpty()){
                    TBHiddenDangerSourceLevelMapping mapping = mappingList.get(0);
                    String resultLevel = mapping.getDangerSourceLevel();

                    if(StringUtils.isNotBlank(address)){
                        systemService.executeSql("update t_b_danger_address_rel set risk_level = '"+resultLevel+"' where danger_id='"+dangerSourceId+"' and address_id = '"+address+"'");
                    }
                }

            }else{
                //2.2 如果不包含重大隐患，则根据升级策略进行计算：3个低级风险 等同于一个高一级的风险，如3个C级 = 1个B级
                String topLevel = getTopLevel(hiddenDangerExamEntityList);

                CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerSourceLevelMapping.class);
                try{
                    cq.eq("hiddenLevel",topLevel);
                }catch(Exception e){
                    e.printStackTrace();
                }
                cq.add();
                List<TBHiddenDangerSourceLevelMapping> mappingList = systemService.getListByCriteriaQuery(cq,false);
                if(mappingList != null && !mappingList.isEmpty()){
                    TBHiddenDangerSourceLevelMapping mapping = mappingList.get(0);
                    String resultLevel = mapping.getDangerSourceLevel();
                    //3.  升级计算之后，将最高等级同步到风险点关联风险表中，对应字段：risk_level
                    //3.1 升级计算之后，若最高等级 < (低于)当前风险表中的等级，则不修改
                    //3.2 升级计算之后，若最高等级 >= (高于)当前风险表中的等级，则修改关联关系表等级
                    TBDangerSourceEntity tbDangerSourceEntity = systemService.getEntity(TBDangerSourceEntity.class,dangerSourceId);
                    String dangerLevel = tbDangerSourceEntity.getYeRiskGrade();

                    Integer resultLevelInt = Integer.parseInt(resultLevel);
                    Integer dangerLevelInt = Integer.parseInt(dangerLevel);
                    if(resultLevelInt <= dangerLevelInt ){
                        if(StringUtils.isNotBlank(address)){
                            systemService.executeSql("update t_b_danger_address_rel set risk_level = '"+resultLevel+"' where danger_id='"+dangerSourceId+"' and address_id = '"+address+"'");
                        }
                    }
                }
            }
        }else{
            //4.  闭合之后降到初始等级
            if(StringUtils.isNotBlank(dangerSourceId)){
                TBDangerSourceEntity dangerSourceEntity = systemService.getEntity(TBDangerSourceEntity.class,dangerSourceId);
                if(StringUtils.isNotBlank(address) && dangerSourceEntity != null){
                    String resultLevel = dangerSourceEntity.getYeRiskGrade();
                    systemService.executeSql("update t_b_danger_address_rel set risk_level = '"+resultLevel+"' where danger_id='"+dangerSourceId+"' and address_id = '"+address+"'");
                    //升级次数降回
                    systemService.executeSql("update t_b_danger_address_rel set upgrade_count = '0' where danger_id='"+dangerSourceId+"' and address_id = '"+address+"'");
                }
            }
        }
    }

    /**
     * 计算升级
     * @param hiddenDangerExamEntityList
     * @return
     */
    private static String getTopLevel(List<TBHiddenDangerExamEntity> hiddenDangerExamEntityList) {
        if(hiddenDangerExamEntityList != null && !hiddenDangerExamEntityList.isEmpty()){
            Map<String,Integer> groupMap = new HashMap<String,Integer>();
            for(TBHiddenDangerExamEntity bean : hiddenDangerExamEntityList){
                //取出隐患等级
                String level = bean.getHiddenNature();
                Integer count = groupMap.get(level);
                if(count == null){
                    count = 0;
                }
                count = count + 1;
                groupMap.put(level,count);
            }

            Set<String> set = groupMap.keySet();
            List levelKeyList = Arrays.asList(set.toArray());
            Collections.sort(levelKeyList);
            String currMaxLevel = "";
                Integer num = groupMap.get(levelKeyList.get(levelKeyList.size() -1 ));
                if(num == null){
                    num = 0;
                }
                currMaxLevel =(String) levelKeyList.get(levelKeyList.size() -1 );
            return upgrade(groupMap,currMaxLevel,num,3);

        }
        return null;
    }

    /**
     * 升级算法
     * @param currLevel
     * @param currNum
     * @param radix
     * @return
     */
    private static String upgrade(Map<String,Integer> groupMap,String currLevel,Integer currNum,int radix){
        int step = currNum/radix;
        int cc = Integer.parseInt(currLevel);

        Integer nextLevelNum = groupMap.get((cc -1)+"");
        nextLevelNum = nextLevelNum == null?0:nextLevelNum;
        int temp = nextLevelNum + step;
        if(temp >=3){
            return upgrade( groupMap, (cc -1)+"",temp,3);
        }else if( temp >0&& temp<3){
            if(cc <=2){
                return currLevel;
            }else{
                return (cc -1)+"";
            }
        }else if(temp <=0){
            return currLevel;
        }
       return null;
    }
    /**
     * 取出未闭合状态日常隐患列表
     * @param dangerSourceId
     * @param systemService
     * @return
     */
    private static List<TBHiddenDangerExamEntity> getHiddenDangerExamList(String address,String dangerSourceId, SystemService systemService) {
        if(StringUtils.isNotBlank(dangerSourceId)){
            CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class);
            try{
                cq.createAlias("hiddenDanger","hiddenDanger");
                cq.eq("hiddenDanger.dangerId.id",dangerSourceId);
                cq.eq("hiddenDanger.address.id",address);
                String[] clStatus = new String[]{
                        Constants.HANDELSTATUS_REPORT,
                        Constants.HANDELSTATUS_REVIEW,
                        Constants.HANDELSTATUS_ROLLBACK_CHECK
                };
                cq.in("handlelStatus",clStatus);
            }catch (Exception e){
                e.printStackTrace();
            }
            cq.add();
            List<TBHiddenDangerHandleEntity> list = systemService.getListByCriteriaQuery(cq,false);
            List<TBHiddenDangerExamEntity> retList = null;
            if(list != null && !list.isEmpty()){
                retList = new ArrayList<TBHiddenDangerExamEntity>();
                for(TBHiddenDangerHandleEntity handleEntity : list){
                    retList.add(handleEntity.getHiddenDanger());
                }
            }
            return retList;
        }else{
            return null;
        }

    }

    /**
     * 取出未闭合重大隐患列表
     * @param dangerSourceId
     * @param systemService
     * @return
     */
    private static List<TBMajorHiddenDangerEntity> getMajorHiddenList(String address,String dangerSourceId, SystemService systemService) {
        if(StringUtils.isNotBlank(dangerSourceId)){
            CriteriaQuery cq = new CriteriaQuery(TBMajorHiddenDangerEntity.class);
            try{
                cq.eq("dangerId.id",dangerSourceId);
                cq.eq("hdLocation",address);
                String[] clStatus = new String[]{
                        Constants.HIDDEN_DANGER_CLSTATUS_RECFITY,
                        Constants.HIDDEN_DANGER_CLSTATUS_ACCEPT,
                        Constants.HIDDEN_DANGER_CLSTATUS_REVIEW
                };
                cq.in("clStatus",clStatus);
            }catch (Exception e){
                e.printStackTrace();
            }
            cq.add();
            List<TBMajorHiddenDangerEntity> list = systemService.getListByCriteriaQuery(cq,false);
            return list;
        }else{
            return null;
        }
    }

    /**
     * 获取当前隐患对应的风险点
     * @param isMajor
     * @param hiddenId
     * @param systemService
     * @return
     */
    private static String getCurrHiddenAddress(boolean isMajor,String hiddenId, SystemService systemService) {
        if(isMajor){
            //重大隐患
            TBMajorHiddenDangerEntity bean = systemService.getEntity(TBMajorHiddenDangerEntity.class,hiddenId);
            return bean.getHdLocation();
        }else{
            //日常隐患
            TBHiddenDangerExamEntity bean = systemService.getEntity(TBHiddenDangerExamEntity.class,hiddenId);
            TBAddressInfoEntity addressInfoEntity = bean.getAddress();
            if(addressInfoEntity == null){
                return null;
            }else{
                return addressInfoEntity.getId();
            }
        }
    }

    /**
     * 查询日常隐患对应的危险源编号
     * @param hiddenId
     * @param systemService
     * @return
     */
    private static String getHiddenDangerSourceId(String hiddenId, SystemService systemService) {
        TBHiddenDangerExamEntity bean = systemService.getEntity(TBHiddenDangerExamEntity.class,hiddenId);
        if(bean != null){
            TBDangerSourceEntity dangerSourceEntity = bean.getDangerId();
            if(dangerSourceEntity != null){
                return dangerSourceEntity.getId();
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 查询重大隐患对应的危险源编号
     * @param hiddenId
     * @param systemService
     * @return
     */
    private static String getMajorHiddenDangerSourceId(String hiddenId,SystemService systemService) {
        TBMajorHiddenDangerEntity bean = systemService.getEntity(TBMajorHiddenDangerEntity.class, hiddenId);
        if(bean != null){
            TBDangerSourceEntity dangerSourceEntity = bean.getDangerId();
            if(dangerSourceEntity != null){
                return dangerSourceEntity.getId();
            }else{
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * 判断是否是重大隐患
     * @param hiddenId
     * @return
     */
    private static boolean isMajorHidden(String hiddenId,SystemService systemService) {
        TBMajorHiddenDangerEntity bean = systemService.getEntity(TBMajorHiddenDangerEntity.class, hiddenId);
        if(bean == null){
            return false;
        }else{
            return true;
        }
    }


    private class WorkThread extends Thread{

        private String hiddenId;

        public void run() {
            upgradeRisk(hiddenId,systemService);
        }

        public String getHiddenId() {
            return hiddenId;
        }

        public void setHiddenId(String hiddenId) {
            this.hiddenId = hiddenId;
        }
    }
}
