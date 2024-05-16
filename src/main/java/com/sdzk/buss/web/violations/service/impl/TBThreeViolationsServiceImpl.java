package com.sdzk.buss.web.violations.service.impl;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.StaticDataMap;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.uploadthreads.UploadThread;
import com.sdzk.buss.web.violations.service.TBThreeViolationsServiceI;
import com.sun.swing.internal.plaf.synth.resources.synth_sv;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.io.Serializable;

import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;

@Service("tBThreeViolationsService")
@Transactional
public class TBThreeViolationsServiceImpl extends CommonServiceImpl implements TBThreeViolationsServiceI {

    @Autowired
    private SystemService systemService;
	
 	public void delete(TBThreeViolationsEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TBThreeViolationsEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TBThreeViolationsEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}


    @Override
    public Map<String, String> reportViolation(String ids,boolean isFromTask) {
        Map<String,String> retMap =new HashMap<>();
        retMap.put("code", Constants.LOCAL_RESULT_CODE_SUCCESS);
        retMap.put("message", "上报成功");

        CriteriaQuery cq = new CriteriaQuery(TBThreeViolationsEntity.class);
        try{
            cq.eq("reportStatus","0");
            if(StringUtil.isNotEmpty(ids)){
                String []idArr = ids.split(",");
                cq.in("id", idArr);
            }
        }catch (Exception e) {
            retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message", "上报失败");
            return retMap;
        }
        cq.add();
        List<TBThreeViolationsEntity> list = getListByCriteriaQuery(cq, false);
        if(null==list || list.size()<=0){
            retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message", "上报失败");
            return retMap;
        }

        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < list.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(list.get(i).getId());
            TBThreeViolationsEntity entity = list.get(i);
            Map<String,String> sendMap = new HashMap<>();


            sendMap.put("id",entity.getId());
            sendMap.put("tvVioDate",DateUtils.date2Str(entity.getVioDate(), DateUtils.date_sdf));
            String shiftCode = entity.getShift();
            if(StringUtils.isNotBlank(shiftCode)){
                String shift = DicUtil.getTypeNameByCode("workShift",shiftCode);
                sendMap.put("tvShift",shift);
            }else{
                sendMap.put("tvShift","");
            }

            sendMap.put("tvVioAddress",entity.getVioAddress());
            String vioUnit = entity.getVioUnits();
            if(StringUtils.isNotBlank(vioUnit)){
                TSDepart depart = systemService.getEntity(TSDepart.class,vioUnit);
                if(depart != null){
                    sendMap.put("tvVioUnits",depart.getDepartname());
                }else{
                    sendMap.put("tvVioUnits","");
                }
            }else{
                sendMap.put("tvVioUnits","");
            }

            sendMap.put("tvWorkType",entity.getWorkType());
            sendMap.put("tvVioCategory",entity.getVioCategory());
            sendMap.put("tvVioQualitative", entity.getVioQualitative());
            sendMap.put("tvVioLevel",entity.getVioLevel());
            sendMap.put("tvStopPeople",entity.getStopPeople());
            String findUnitId = entity.getFindUnits();
            if(StringUtils.isNotBlank(findUnitId)){
                TSDepart depart = systemService.getEntity(TSDepart.class,findUnitId);
                if(depart != null){
                    sendMap.put("tvFindUnits",depart.getDepartname());
                }else{
                    sendMap.put("tvFindUnits","");
                }
            }else{
                sendMap.put("tvFindUnits","");
            }

            sendMap.put("tvVioFactDesc",entity.getVioFactDesc());
            sendMap.put("tvRemark",entity.getRemark());
            sendMap.put("tvVioPeople",entity.getVioPeople());
            sendList.add(sendMap);
        }

        String reportContent= JSONHelper.toJSONString(sendList);

        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportViolation");

        /**
         * 加密过程
         * */
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = null;

        //上报年度风险辨识
        try {
            Map<String,String> paramMap = new HashMap<String,String>();
            paramMap.put("token", ciphertext);
            paramMap.put("mineCode", mineCode);
            paramMap.put("reportContent", reportContent);
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        }catch (NetServiceException e) {
            retMap.put("code",Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message","上报失败："+e.getMessage());
            return retMap;
        }

        //解析rpc返回的json
        try{
            if(result!=null){
                JSONObject resultJson = JSONHelper.jsonstr2json(result);
                String code = resultJson.getString("code");
                if(!code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)){//请求成功
                    retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                    retMap.put("message", "上报失败");
                    return retMap;
                }
            }else {
                retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                retMap.put("message", "上报失败");
                return retMap;
            }
        }catch (Exception e){
            retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message", "上报失败");
            return retMap;
        }

        //更新本地数据库
        try {
            if (StringUtil.isNotEmpty(idSb.toString())) {
                if(!isFromTask){
                    executeSql("update t_b_three_violations set report_status = '1' where id in ('"+idSb.toString().replace(",","','")+"')");
                }
            }
        } catch (Exception e) {
            retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message", "上报成功，但本地数据库操作失败");
            return retMap;
        }
        return retMap;
    }


    /**
     * 上报到集团
     * */
    @Override
    public Map<String, String> reportViolationToGroup(String ids,boolean isFromTask) {
        Map<String,String> retMap =new HashMap<>();
        retMap.put("code", Constants.LOCAL_RESULT_CODE_SUCCESS);
        retMap.put("message", "上报成功");

        CriteriaQuery cq = new CriteriaQuery(TBThreeViolationsEntity.class);
        try{
            cq.eq("reportStatus","0");
            if(StringUtil.isNotEmpty(ids)){
                String []idArr = ids.split(",");
                cq.in("id", idArr);
            }
        }catch (Exception e) {
            retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message", "上报失败");
            return retMap;
        }
        cq.add();
        List<TBThreeViolationsEntity> list = getListByCriteriaQuery(cq, false);
        if(null==list || list.size()<=0){
            retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message", "上报失败");
            return retMap;
        }

        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < list.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(list.get(i).getId());
            TBThreeViolationsEntity entity = list.get(i);
            Map<String,String> sendMap = new HashMap<>();


            sendMap.put("id",entity.getId());
            sendMap.put("vioDate",DateUtils.date2Str(entity.getVioDate(), DateUtils.date_sdf));
            String shiftCode = entity.getShift();
            if(StringUtils.isNotBlank(shiftCode)){
                String shift = DicUtil.getTypeNameByCode("workShift",shiftCode);
                sendMap.put("shift",shift);
            }else{
                sendMap.put("shift","");
            }

            sendMap.put("vioAddress",entity.getVioAddress());
            String vioUnit = entity.getVioUnits();
            if(StringUtils.isNotBlank(vioUnit)){
                TSDepart depart = systemService.getEntity(TSDepart.class,vioUnit);
                if(depart != null){
                    sendMap.put("vioUnits",depart.getDepartname());
                }else{
                    sendMap.put("vioUnits","");
                }
            }else{
                sendMap.put("vioUnits","");
            }

            sendMap.put("workType",entity.getWorkType());
            sendMap.put("vioCategory",entity.getVioCategory());
            sendMap.put("vioQualitative", entity.getVioQualitative());
            sendMap.put("vioLevel",entity.getVioLevel());
            sendMap.put("stopPeople",entity.getStopPeople());
            String findUnitId = entity.getFindUnits();
            if(StringUtils.isNotBlank(findUnitId)){
                TSDepart depart = systemService.getEntity(TSDepart.class,findUnitId);
                if(depart != null){
                    sendMap.put("findUnits",depart.getDepartname());
                }else{
                    sendMap.put("findUnits","");
                }
            }else{
                sendMap.put("findUnits","");
            }

            sendMap.put("vioFactDesc",entity.getVioFactDesc());
            sendMap.put("remark",entity.getRemark());
            sendMap.put("vioPeople",entity.getVioPeople());
            sendMap.put("createName", entity.getCreateName());
            sendMap.put("createBy", entity.getCreateBy());
            sendMap.put("createDate", DateUtils.date2Str(entity.getCreateDate(), DateUtils.date_sdf));
            sendMap.put("updateName", entity.getUpdateName());
            sendMap.put("updateBy", entity.getUpdateBy());
            sendMap.put("updateDate", DateUtils.date2Str(entity.getUpdateDate(), DateUtils.date_sdf));
            sendList.add(sendMap);
        }

        String reportContent= JSONHelper.toJSONString(sendList);

        String token = ResourceUtil.getConfigByName("token_group");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("uploadThreeViolenceUrl");

        /**
         * 加密过程
         * */
        String tempToken = "token=" + token + "&mineCode=" + mineCode;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = null;

        //上报年度风险辨识
        try {
            Map<String,String> paramMap = new HashMap<String,String>();
            paramMap.put("token", ciphertext);
            paramMap.put("mineCode", mineCode);
            paramMap.put("reportContent", reportContent);
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
        }catch (NetServiceException e) {
            retMap.put("code",Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message","上报失败："+e.getMessage());
            return retMap;
        }

        //解析rpc返回的json
        try{
            if(result!=null){
                JSONObject resultJson = JSONHelper.jsonstr2json(result);
                String code = resultJson.getString("code");
                if(!code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)){//请求成功
                    retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                    retMap.put("message", "上报失败");
                    return retMap;
                }
            }else {
                retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                retMap.put("message", "上报失败");
                return retMap;
            }
        }catch (Exception e){
            retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message", "上报失败");
            return retMap;
        }

        //更新本地数据库
        try {
            if (StringUtil.isNotEmpty(idSb.toString())) {
                String curUser;
                try {
                    //定时任务无法获取session
                    curUser = ResourceUtil.getSessionUserName().getRealName();
                } catch (Exception e) {
                    curUser = "定时任务";
                }
                executeSql("update t_b_three_violations set report_group_status = '1',report_group_time = NOW(),report_group_man = '"+ curUser +"' where id in ('"+ids.replace(",","','")+"')");
                //executeSql("update t_b_three_violations set report_status = '1' where id in ('"+idSb.toString().replace(",","','")+"')");

            }
        } catch (Exception e) {
            retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message", "上报成功，但本地数据库操作失败");
            return retMap;
        }
        return retMap;
    }


    /**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TBThreeViolationsEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(TBThreeViolationsEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 删除操作增强业务
	 * @return
	 */
	private void doDelBus(TBThreeViolationsEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(TBThreeViolationsEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("vio_date", t.getVioDate());
		map.put("shift", t.getShift());
		map.put("vio_address", t.getVioAddress());
		map.put("vio_units", t.getVioUnits());
		map.put("work_type", t.getWorkType());
		map.put("vio_category", t.getVioCategory());
		map.put("vio_qualitative", t.getVioQualitative());
		map.put("vio_level", t.getVioLevel());
		map.put("stop_people", t.getStopPeople());
		map.put("find_units", t.getFindUnits());
		map.put("vio_fact_desc", t.getVioFactDesc());
		map.put("remark", t.getRemark());
		map.put("vio_people", t.getVioPeople());
		map.put("create_name", t.getCreateName());
		map.put("create_by", t.getCreateBy());
		map.put("create_date", t.getCreateDate());
		map.put("update_name", t.getUpdateName());
		map.put("update_by", t.getUpdateBy());
		map.put("update_date", t.getUpdateDate());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TBThreeViolationsEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{vio_date}",String.valueOf(t.getVioDate()));
 		sql  = sql.replace("#{shift}",String.valueOf(t.getShift()));
 		sql  = sql.replace("#{vio_address}",String.valueOf(t.getVioAddress()));
 		sql  = sql.replace("#{vio_units}",String.valueOf(t.getVioUnits()));
 		sql  = sql.replace("#{work_type}",String.valueOf(t.getWorkType()));
 		sql  = sql.replace("#{vio_category}",String.valueOf(t.getVioCategory()));
 		sql  = sql.replace("#{vio_qualitative}",String.valueOf(t.getVioQualitative()));
 		sql  = sql.replace("#{vio_level}",String.valueOf(t.getVioLevel()));
 		sql  = sql.replace("#{stop_people}",String.valueOf(t.getStopPeople()));
 		sql  = sql.replace("#{find_units}",String.valueOf(t.getFindUnits()));
 		sql  = sql.replace("#{vio_fact_desc}",String.valueOf(t.getVioFactDesc()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{vio_people}",String.valueOf(t.getVioPeople()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
	 * 执行JAVA增强
	 */
 	private void executeJavaExtend(String cgJavaType,String cgJavaValue,Map<String,Object> data) throws Exception {
 		if(StringUtil.isNotEmpty(cgJavaValue)){
			Object obj = null;
			try {
				if("class".equals(cgJavaType)){
					//因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
					obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
				}else if("spring".equals(cgJavaType)){
					obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
				}
				if(obj instanceof CgformEnhanceJavaInter){
					CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
					javaInter.execute("t_b_three_violations",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}