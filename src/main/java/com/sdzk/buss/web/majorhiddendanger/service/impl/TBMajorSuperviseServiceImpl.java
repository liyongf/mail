package com.sdzk.buss.web.majorhiddendanger.service.impl;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.majorhiddendanger.entity.SFListedSupervisionInfoEntity;
import com.sdzk.buss.web.majorhiddendanger.service.TBMajorSuperviseServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import com.sdzk.buss.web.majorhiddendanger.entity.TBMajorHiddenDangerEntity;
import java.text.SimpleDateFormat;
/**
 * Created by Lenovo on 17-7-7.
 */
@Service("tBMajorSuperviseService")
@Transactional
public class TBMajorSuperviseServiceImpl extends CommonServiceImpl implements TBMajorSuperviseServiceI {

    @Override
    public void delete(SFListedSupervisionInfoEntity entity) throws Exception {
        super.delete(entity);
    }

    @Override
    public Serializable save(SFListedSupervisionInfoEntity entity) throws Exception {
        Serializable t = super.save(entity);
        return t;
    }

    @Override
    public void saveOrUpdate(SFListedSupervisionInfoEntity entity) throws Exception {
        super.saveOrUpdate(entity);
    }

    //根据接口返回的数据更新数据库：重大隐患表，重大隐患督办信息表
    private Map<String,String> updateMajorSupervise(List<SFListedSupervisionInfoEntity> list){
        Map<String,String> retMap = new HashMap<String,String>();
        try{
            if(null!=list &&  !list.isEmpty()){
                for(int i=0;i<list.size();i++){
                    SFListedSupervisionInfoEntity majorSuperviseEntity = list.get(i);
                    SFListedSupervisionInfoEntity targetEntity = null;

                    CriteriaQuery cq = new CriteriaQuery(SFListedSupervisionInfoEntity.class);
                    cq.eq("fkHiddenInfoId",majorSuperviseEntity.getFkHiddenInfoId());
                    cq.eq("lsiIsLevel",majorSuperviseEntity.getLsiIsLevel());
                    cq.add();
                    List<SFListedSupervisionInfoEntity> targetEntiyList = getListByCriteriaQuery(cq, false);
                    if(null!=targetEntiyList && targetEntiyList.size()>0) {
                        targetEntity = targetEntiyList.get(0);
                    }

                    //更新对应的重大隐患记录
                    TBMajorHiddenDangerEntity majorHiddenDangerEntity = getEntity(TBMajorHiddenDangerEntity.class, majorSuperviseEntity.getFkHiddenInfoId());

                    if(Constants.HDBIISLS_STATE_DO.equals(majorSuperviseEntity.getLsType())){
                        //更新或添加督办表项
                        if(null!=targetEntity){
                            targetEntity.setLsiLsTime(majorSuperviseEntity.getLsiLsTime());
                            targetEntity.setLsiIsLevel(majorSuperviseEntity.getLsiIsLevel());
                            targetEntity.setLsiLsUnit(majorSuperviseEntity.getLsiLsUnit());
                            targetEntity.setLsiShNum(majorSuperviseEntity.getLsiShNum());
                            saveOrUpdate(targetEntity);
                        }else{
                            save(majorSuperviseEntity);
                        }

                        //更新重大隐患表督办标志
                        if(null!=majorHiddenDangerEntity){
                            if(Constants.LSILSLEVEL_BRANCH.equals(majorSuperviseEntity.getLsiIsLevel())){
                                majorHiddenDangerEntity.setIsLsSub(Constants.HDBIISLS_STATE_DO);
                            }else if(Constants.LSILSLEVEL_GENERAL_ADMINISTRATION.equals(majorSuperviseEntity.getLsiIsLevel())){
                                majorHiddenDangerEntity.setIsLsProv(Constants.HDBIISLS_STATE_DO);
                            }
                            saveOrUpdate(majorHiddenDangerEntity);
                        }
                    }else if (Constants.HDBIISLS_STATE_UNDO.equals(majorSuperviseEntity.getLsType())){
                        //删除督办表项
                        if(null!=targetEntity)
                            delete(targetEntity);

                        //更新重大隐患督办标志
                        if(null!=majorHiddenDangerEntity){
                            if(Constants.LSILSLEVEL_BRANCH.equals(majorSuperviseEntity.getLsiIsLevel())){
                                majorHiddenDangerEntity.setIsLsSub(Constants.HDBIISLS_STATE_UNDO);
                            }else if(Constants.LSILSLEVEL_GENERAL_ADMINISTRATION.equals(majorSuperviseEntity.getLsiIsLevel())){
                                majorHiddenDangerEntity.setIsLsProv(Constants.HDBIISLS_STATE_UNDO);
                            }
                            saveOrUpdate(majorHiddenDangerEntity);
                        }
                    }
                }
            }
        }
        catch (Exception e){
            retMap.put("code",Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message","重大隐患督办同步失败: 数据库操作失败");
            return retMap;
        }
        retMap.put("code",Constants.LOCAL_RESULT_CODE_SUCCESS);
        retMap.put("message","success");
        return retMap;

    }

    //同步重大隐患督办信息
    public Map<String,String> synMajorSupervise(){
        Map<String,String> retMap = new HashMap<String,String>();
        retMap.put("code", Constants.LOCAL_RESULT_CODE_SUCCESS);
        String msg = "重大隐患督办同步成功";
        retMap.put("message",msg);

        String token = ResourceUtil.getConfigByName("token");
        String dataSource = Constants.IS_MAJORSUPERVISE_Y;
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("synSupervisionUrl");
        String result = null;
        List<SFListedSupervisionInfoEntity> list = new ArrayList<SFListedSupervisionInfoEntity>();
        try {
            Map<String,String> paramMap = new HashMap<String,String>();


            /**
             * 加密过程
             * */
            String tempToken = "token=" + token + "&dataSource=" + dataSource + "&mineCode=" + mineCode;
            String ciphertext = null;
            try {
                ciphertext = AesUtil.encryptWithIV(tempToken, token);
            } catch (Exception e) {
                e.printStackTrace();
            }


            paramMap.put("token", ciphertext);
//            paramMap.put("dataSource", dataSource);
            paramMap.put("mineCode", mineCode);
            result = HttpClientUtils.get(url, paramMap);

        }catch (NetServiceException e) {
            retMap.put("code",Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message","重大隐患督办同步失败："+e.getMessage());
            return retMap;
        }

        //解析rpc返回的json组装成List,然后修改数据库中重大隐患的督办信息
        try{
            if(result!=null){
                JSONObject resultJson = JSONHelper.jsonstr2json(result);
                String code = resultJson.getString("code");

                System.out.println(resultJson);

                if(code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)){//请求成功
                    JSONArray dataArray = resultJson.getJSONArray("data");
                    if (dataArray != null && dataArray.size() > 0){
                        for (int i = 0; i < dataArray.size(); i++) {
                            JSONObject data = (JSONObject)dataArray.get(i);
                            SFListedSupervisionInfoEntity majorSuperviseEntity = new  SFListedSupervisionInfoEntity();
                            majorSuperviseEntity.setFkHiddenInfoId(nullToEmpty(data.optString("hiddenId")));
                            String dateStr = data.optString("lsiLsTime");

                            //临时使用
                            //java.util.Date datetime = new java.util.Date(dateStr);
//                            java.sql.Timestamp dateTime = new java.sql.Timestamp(Long.parseLong(dateStr));
//                            java.util.Date datetime = dateTime;
                            majorSuperviseEntity.setLsiLsTime(DateUtils.stringToDate(dateStr));

                            majorSuperviseEntity.setLsiIsLevel(nullToEmpty(data.optString("lsiLsLevel")));
                            majorSuperviseEntity.setLsiLsUnit(nullToEmpty(data.optString("lsiLsUnit")));
                            majorSuperviseEntity.setLsiShNum(nullToEmpty(data.optString("lsiShNum")));
                            majorSuperviseEntity.setLsType(nullToEmpty(data.optString("lsType")));
                            list.add(majorSuperviseEntity);
                        }
                    }
                }else if(code.equals(Constants.PLATFORM_RESULT_CODE_REPEART)){
                    retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                    retMap.put("message","数据已同步，无需再次同步");
                    return retMap;
                }
            }
        }catch (Exception e){
            retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
            msg = "重大隐患督办同步失败: 数据解析失败";
            retMap.put("message",msg);
            return retMap;
        }
        //更新数据库
        Map<String,String> retUpdateMap = updateMajorSupervise(list);
        if(null!=retUpdateMap){
            retMap.put("code",retUpdateMap.get("code"));
            retMap.put("message",retUpdateMap.get("message"));
            return retMap;
        }
        retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
        retMap.put("message","重大隐患督办同步失败");
        return retMap;
    }

    //同步一般（非重大）隐患督办信息
    public Map<String,String> synCommonSupervise(){
        Map<String,String> retMap = new HashMap<String,String>();
        retMap.put("code", Constants.LOCAL_RESULT_CODE_SUCCESS);
        String msg = "非重大隐患督办同步成功";
        retMap.put("message",msg);

        String token = ResourceUtil.getConfigByName("token");
        String dataSource = Constants.IS_MAJORSUPERVISE_N;
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("synSupervisionUrl");
        String result = null;
        List<SFListedSupervisionInfoEntity> list = new ArrayList<SFListedSupervisionInfoEntity>();
        try {
            Map<String,String> paramMap = new HashMap<String,String>();
            paramMap.put("token", token);
            paramMap.put("dataSource", dataSource);
            paramMap.put("mineCode", mineCode);
            result = HttpClientUtils.get(url, paramMap);
        }catch (NetServiceException e) {
            retMap.put("code",Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message","非重大隐患督办同步失败："+e.getMessage());
            return retMap;
        }

        //解析rpc返回的json组装成List,然后修改数据库中重大隐患的督办信息
        try{
            if(result!=null){
                JSONObject resultJson = JSONHelper.jsonstr2json(result);
                String code = resultJson.getString("code");

                System.out.println(resultJson);

                if(code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)){//请求成功
                    JSONArray dataArray = resultJson.getJSONArray("data");
                    if (dataArray != null && dataArray.size() > 0){
                        for (int i = 0; i < dataArray.size(); i++) {
                            JSONObject data = (JSONObject)dataArray.get(i);
                            SFListedSupervisionInfoEntity majorSuperviseEntity = new  SFListedSupervisionInfoEntity();
                            majorSuperviseEntity.setFkHiddenInfoId(nullToEmpty(data.optString("hiddenId")));
                            String dateStr = data.optString("lsiLsTime");
//                            //临时使用
//                            //java.util.Date datetime = new java.util.Date(dateStr);
//                            java.sql.Timestamp dateTime = new java.sql.Timestamp(Long.parseLong(dateStr));
//                            java.util.Date datetime = dateTime;
                            majorSuperviseEntity.setLsiLsTime(DateUtils.stringToDate(dateStr));

                            majorSuperviseEntity.setLsiIsLevel(nullToEmpty(data.optString("lsiLsLevel")));
                            majorSuperviseEntity.setLsiLsUnit(nullToEmpty(data.optString("lsiLsUnit")));
                            majorSuperviseEntity.setLsiShNum(nullToEmpty(data.optString("lsiShNum")));
                            majorSuperviseEntity.setLsType(nullToEmpty(data.optString("lsType")));
                            list.add(majorSuperviseEntity);
                        }
                    }
                }else if(code.equals(Constants.PLATFORM_RESULT_CODE_REPEART)){
                    retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                    retMap.put("message","数据已同步，无需再次同步");
                    return retMap;
                }

            }
        }catch (Exception e){
            retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
            msg = "非重大隐患督办同步失败: 数据解析失败";
            retMap.put("message",msg);
            return retMap;
        }
        //更新数据库,临时使用和更新重大隐患相同的做法
        Map<String,String> retUpdateMap = updateMajorSupervise(list);
        if(null!=retUpdateMap){
            retMap.put("code",retUpdateMap.get("code"));
            retMap.put("message",retUpdateMap.get("message"));
            return retMap;
        }
        retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
        retMap.put("message","非重大隐患督办同步失败");
        return retMap;
    }

    public static String nullToEmpty(String paraStr){

        return paraStr==null||paraStr.trim().equals("")||paraStr.trim().equals("null")?"":paraStr;

    }
}
