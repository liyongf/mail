package com.sdzk.buss.web.layer.service.impl;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.layer.service.TBLayerServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.sdzk.buss.web.layer.entity.TBLayerEntity;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.exception.NetServiceException;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;

import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;

@Service("tBLayerService")
@Transactional
public class TBLayerServiceImpl extends CommonServiceImpl implements TBLayerServiceI {

	@Autowired
	private SystemService systemService;

 	public void delete(TBLayerEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TBLayerEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TBLayerEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TBLayerEntity t) throws Exception{
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
	private void doUpdateBus(TBLayerEntity t) throws Exception{
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
	private void doDelBus(TBLayerEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(TBLayerEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("layer_code", t.getLayerCode());
		map.put("layer_detail_name", t.getLayerDetailName());
		map.put("url", t.getUrl());
		map.put("is_show", t.getIsShow());
		map.put("remark", t.getRemark());
		map.put("create_by", t.getCreateBy());
		map.put("create_name", t.getCreateName());
		map.put("create_date", t.getCreateDate());
		map.put("update_by", t.getUpdateBy());
		map.put("update_name", t.getUpdateName());
		map.put("update_date", t.getUpdateDate());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TBLayerEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{layer_code}",String.valueOf(t.getLayerCode()));
 		sql  = sql.replace("#{layer_detail_name}",String.valueOf(t.getLayerDetailName()));
 		sql  = sql.replace("#{url}",String.valueOf(t.getUrl()));
 		sql  = sql.replace("#{is_show}",String.valueOf(t.getIsShow()));
 		sql  = sql.replace("#{remark}",String.valueOf(t.getRemark()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
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
					javaInter.execute("t_b_layer",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}

	@Override
	public AjaxJson tBLayerReportToGroup(String ids){
		AjaxJson j = new AjaxJson();
		JSONArray jsonArray = new JSONArray();
		for (String id : ids.split(",")) {
			JSONObject data = new JSONObject();
			TBLayerEntity tbLayerEntity = systemService.get(TBLayerEntity.class, id);
			if (tbLayerEntity != null){
				data.put("id", tbLayerEntity.getId());
				data.put("layerCode", tbLayerEntity.getLayerCode());
				data.put("layerDetailName", tbLayerEntity.getLayerDetailName());
				data.put("url", tbLayerEntity.getUrl());
				data.put("center", tbLayerEntity.getCenter());
				data.put("isShow", tbLayerEntity.getIsShow());
				data.put("remark", tbLayerEntity.getRemark());
				jsonArray.add(data);
			}
		}

		if (jsonArray.size() > 0){
			//上传
			String mineCode = ResourceUtil.getConfigByName("mine_code");
			String token = ResourceUtil.getConfigByName("token_group");
			String reportContent = jsonArray.toString();
			String tBLayerReport = ResourceUtil.getConfigByName("tBLayerReportToGroup_group");
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
			LogUtil.info("tBLayerReport=" + tBLayerReport);
			LogUtil.info("jsonArray.size()=" + jsonArray.size() + ",reportContent="+reportContent.length());
			try {
				response = HttpClientUtils.post(tBLayerReport, paramMap, "UTF-8");
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
					systemService.executeSql("update t_b_layer set report_group_status = '1',report_group_time = NOW(),report_group_man = '"+ curUser +"' where id in ('"+ids.replace(",","','")+"')");
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