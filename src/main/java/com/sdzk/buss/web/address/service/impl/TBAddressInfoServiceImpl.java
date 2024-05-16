package com.sdzk.buss.web.address.service.impl;

import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sdzk.buss.web.address.entity.TBAddressDepartRelEntity;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.address.service.TBAddressInfoServiceI;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.dangersource.entity.TBDeptDangerRelEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.*;
import org.jeecgframework.p3.core.utils.common.StringUtils;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.*;

@Service("tBAddressInfoService")
@Transactional
public class TBAddressInfoServiceImpl extends CommonServiceImpl implements TBAddressInfoServiceI {
	@Autowired
	private SystemService systemService;
	
 	public void delete(TBAddressInfoEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TBAddressInfoEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TBAddressInfoEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}

    @Override
    public void saveAddressDepartRel(String addressId, String departIds) {
        if (StringUtils.isBlank(addressId)) {
            return;
        }
        List<TBAddressDepartRelEntity> tBAddressDepartRelEntitys = findByProperty(TBAddressDepartRelEntity.class, "addressId", addressId);
        Map<String, TBAddressDepartRelEntity> map = new HashMap<>();
        //获取已关联的部门
        for (TBAddressDepartRelEntity entity : tBAddressDepartRelEntitys) {
            map.put(entity.getDepartId(), entity);
        }
        if (StringUtils.isNotBlank(departIds)) {
            List<TBAddressDepartRelEntity> list = new ArrayList<>();
            for (String departId : departIds.split(",")){
                if (map.get(departId) != null){
                    //从map里去除已勾选的且已关联的部门
                    map.remove(departId);
                    continue;
                }
                TBAddressDepartRelEntity entity = new TBAddressDepartRelEntity();
                entity.setDepartId(departId);
                entity.setAddressId(addressId);
                list.add(entity);
            }
            batchSave(list);
        }
        //删除未勾选的部门
//        for (String key : map.keySet()) {
//            delete(map.get(key));
//        }
    }

	@Override
	public Map<String, Object> getDynamicLevel() {
 		Map<String,Object> result = new HashedMap();//存储结果集

		Map<String,Map<String,Double>> dynamicStandard = getDynamicStandard();//获取动态风险等级标准

 		StringBuffer addressScoreSb = new StringBuffer();
 		addressScoreSb.append("SELECT\n" +
				"\taddressLevelInfo.address,\n" +
				"\tsum(cast(addressLevelInfo.num as SIGNED) * cast(rrsm.score as SIGNED)) score\n" +
				"FROM\n" +
				"\t(\n" +
				"\t\tSELECT\n" +
				"\t\t\texam.address,\n" +
				"\t\t\texam.hidden_nature,\n" +
				"\t\t\tcount(1) num\n" +
				"\t\tFROM\n" +
				"\t\t\tt_b_hidden_danger_exam exam\n" +
				"\t\tLEFT JOIN t_b_hidden_danger_handle handle ON handle.hidden_danger_id = exam.id\n" +
				"\t\tWHERE\n" +
				"\t\t\thandle.handlel_status IN ('1', '4')\n" +
				"\t\tGROUP BY\n" +
				"\t\t\texam.address,\n" +
				"\t\t\texam.hidden_nature\n" +
				"\t) addressLevelInfo\n" +
				"LEFT JOIN t_b_risk_rule_score_manager rrsm ON addressLevelInfo.hidden_nature = rrsm.risk_type\n" +
				"GROUP BY addressLevelInfo.address");
 		List<Map<String,Object>> addressScoreList = this.commonDao.findForJdbc(addressScoreSb.toString());
 		if(addressScoreList != null && addressScoreList.size()>0){
			for(Map<String,Object> addressScoreMap : addressScoreList){
				result.put(String.valueOf(addressScoreMap.get("address")),marryAddressDynamicLevel(dynamicStandard,String.valueOf(addressScoreMap.get("score"))));
			}
		}

		return result;
	}
	private String marryAddressDynamicLevel(Map<String,Map<String,Double>> dynamicStandard,String score){
		if(StringUtil.isEmpty(score) || "null".equals(score)){
			return null;
		}
		for(String key : dynamicStandard.keySet()){
			Map<String,Double> map = dynamicStandard.get(key);
			int i = BigDecimal.valueOf(map.get("begin")).compareTo(BigDecimal.valueOf(Double.parseDouble(score)));
			int j = BigDecimal.valueOf(map.get("end")).compareTo(BigDecimal.valueOf(Double.parseDouble(score)));
			if((i == -1 || i == 0) && j == 1){
				/*return DicUtil.getTypeNameByCode("riskLevel", key);*/
				return key;
			}
		}
 		return null;
	}
	private Map<String,Map<String,Double>> getDynamicStandard(){
		Map<String,Map<String,Double>> result = new HashedMap();

 		StringBuffer dynamicStandard = new StringBuffer();
		dynamicStandard.append("SELECT risk_type,score_between,score_end from t_b_risk_rule_manager");
 		List<Map<String,Object>> dynamicStandardList = this.commonDao.findForJdbc(dynamicStandard.toString());
 		if(dynamicStandardList != null && dynamicStandardList.size()>0){
 			for(Map<String,Object> dynamicStandardMap : dynamicStandardList){
				Map<String,Double> temp = new HashedMap();
				temp.put("begin",Double.parseDouble(String.valueOf(StringUtil.isNotEmpty(dynamicStandardMap.get("score_between"))?dynamicStandardMap.get("score_between"):"0")));
				temp.put("end",Double.parseDouble(String.valueOf(StringUtil.isNotEmpty(dynamicStandardMap.get("score_end"))?dynamicStandardMap.get("score_end"):"99999999999999")));
				result.put(String.valueOf(dynamicStandardMap.get("risk_type")),temp);
			}
		}

 		return result;
	}

	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TBAddressInfoEntity t) throws Exception{
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
	private void doUpdateBus(TBAddressInfoEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(TBAddressInfoEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(TBAddressInfoEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("address", t.getAddress());
		map.put("lon", t.getLon());
		map.put("lat", t.getLat());
		map.put("isshow", t.getIsshow());
		map.put("is_delete", t.getIsDelete());
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
 	public String replaceVal(String sql,TBAddressInfoEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{lon}",String.valueOf(t.getLon()));
 		sql  = sql.replace("#{lat}",String.valueOf(t.getLat()));
 		sql  = sql.replace("#{isshow}",String.valueOf(t.getIsshow()));
 		sql  = sql.replace("#{is_delete}",String.valueOf(t.getIsDelete()));
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
					javaInter.execute("t_b_address_info",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}

	@Override
	public AjaxJson tBAddressInfoReportToGroup(String ids){
		AjaxJson j = new AjaxJson();
		JSONArray jsonArray = new JSONArray();
		for (String id : ids.split(",")) {
			JSONObject data = new JSONObject();
			TBAddressInfoEntity tbAddressInfoEntity = systemService.get(TBAddressInfoEntity.class, id);
			if (tbAddressInfoEntity != null){
				data.put("id", tbAddressInfoEntity.getId());
				data.put("address", tbAddressInfoEntity.getAddress());
				data.put("lon", tbAddressInfoEntity.getLon());
				data.put("lat", tbAddressInfoEntity.getLat());
				data.put("isshow", tbAddressInfoEntity.getIsshow());
				data.put("isDelete", tbAddressInfoEntity.getIsDelete());
				data.put("createDate", DateUtils.date2Str(tbAddressInfoEntity.getCreateDate(), DateUtils.date_sdf));
				data.put("updateDate", DateUtils.date2Str(tbAddressInfoEntity.getUpdateDate(), DateUtils.date_sdf));
				data.put("cate", tbAddressInfoEntity.getCate());
				data.put("description", tbAddressInfoEntity.getDescription());
				data.put("isShowData", tbAddressInfoEntity.getIsShowData());
				data.put("manageMan", tbAddressInfoEntity.getManageMan());
				data.put("pointStr", tbAddressInfoEntity.getPointStr());
				data.put("belongLayer", StringUtil.isNotEmpty(tbAddressInfoEntity.getBelongLayer())?tbAddressInfoEntity.getBelongLayer().getId():"");
				data.put("investigationDate", DateUtils.date2Str(tbAddressInfoEntity.getInvestigationDate(), DateUtils.date_sdf));
				data.put("startDate", DateUtils.date2Str(tbAddressInfoEntity.getStartDate(), DateUtils.date_sdf));
				data.put("endDate", DateUtils.date2Str(tbAddressInfoEntity.getEndDate(), DateUtils.date_sdf));
				data.put("remark", tbAddressInfoEntity.getRemark());
				jsonArray.add(data);
			}
		}

		if (jsonArray.size() > 0){
			//上传
			String mineCode = ResourceUtil.getConfigByName("mine_code");
			String token = ResourceUtil.getConfigByName("token_group");
			String reportContent = jsonArray.toString();
			String tBAddressInfoReport = ResourceUtil.getConfigByName("tBAddressInfoReport_group");
			Map<String, Object> paramMap = new HashMap<>();

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
			paramMap.put("token", ciphertext);
			paramMap.put("mineCode", mineCode);
			paramMap.put("reportContent", reportContent);
			String response = null;
			LogUtil.info("tBAddressInfoReport=" + tBAddressInfoReport);
			LogUtil.info("jsonArray.size()=" + jsonArray.size() + ",reportContent="+reportContent.length());
			try {
				response = HttpClientUtils.post(tBAddressInfoReport, paramMap, "UTF-8");
				LogUtil.info("上报接口调用成功返回：" + response);
			} catch (NetServiceException e) {
				j.setSuccess(false);
				j.setMsg("上报接口调用失败, 网络连接错误");
				LogUtil.error("上报接口调用异常",e);
				return j;
			}
			JSONObject jsonObject = JSONObject.fromObject(response);

			String retCode = jsonObject.getString("code");
			if("200".equals(retCode)){
				j.setMsg("上报成功");
				try{
					String curUser;
					try {
						//定时任务无法获取session
						curUser = ResourceUtil.getSessionUserName().getRealName();
					} catch (Exception e) {
						curUser = "定时任务";
					}
					systemService.executeSql("update t_b_address_info set report_group_status = '1',report_group_time = NOW(),report_group_man = '"+ curUser +"' where id in ('"+ids.replace(",","','")+"')");
				}catch (Exception e){
					j.setMsg("上报成功但是本地数据库操作失败！");
				}
			}else{
				j.setMsg(jsonObject.optString("message"));
			}
		} else {
			j.setMsg("所选中记录不存在或状态已改变");
		}
		return j;
	}
}