package com.sdzk.buss.web.tbpostmanage.service.impl;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.tbpostmanage.service.TBPostManageServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
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

@Service("tBPostManageService")
@Transactional
public class TBPostManageServiceImpl extends CommonServiceImpl implements TBPostManageServiceI {

	@Autowired
	private SystemService systemService;

 	public void delete(TBPostManageEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TBPostManageEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TBPostManageEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TBPostManageEntity t) throws Exception{
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
	private void doUpdateBus(TBPostManageEntity t) throws Exception{
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
	private void doDelBus(TBPostManageEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(TBPostManageEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("post_name", t.getPostName());
		map.put("create_name", t.getCreateName());
		map.put("create_by", t.getCreateBy());
		map.put("create_date", t.getCreateDate());
		map.put("update_name", t.getUpdateName());
		map.put("update_by", t.getUpdateBy());
		map.put("update_date", t.getUpdateDate());
        map.put("is_delete",t.getIsDelete());
        map.put("profession_type",t.getProfessionType());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TBPostManageEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{post_name}",String.valueOf(t.getPostName()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
        sql  = sql.replace("#{is_delete}",String.valueOf(t.getIsDelete()));
        sql  = sql.replace("#{profession_type}",String.valueOf(t.getProfessionType()));
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
					javaInter.execute("t_b_post_manage",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}

	@Override
	public AjaxJson tBPostManageReportToGroup(String ids){
		AjaxJson j = new AjaxJson();
		JSONArray jsonArray = new JSONArray();
		for (String id : ids.split(",")) {
			JSONObject data = new JSONObject();
			TBPostManageEntity tbPostManageEntity = systemService.get(TBPostManageEntity.class, id);
			if (tbPostManageEntity != null){
				data.put("id", tbPostManageEntity.getId());
				data.put("postName", tbPostManageEntity.getPostName());
				data.put("isDelete",tbPostManageEntity.getIsDelete());
				jsonArray.add(data);
			}
		}

		if (jsonArray.size() > 0){
			//上传
			String mineCode = ResourceUtil.getConfigByName("mine_code");
			String token = ResourceUtil.getConfigByName("token_group");
			String reportContent = jsonArray.toString();
			String tBPostManageReport = ResourceUtil.getConfigByName("tBPostManageReport_group");
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
			LogUtil.info("tBPostManageReport=" + tBPostManageReport);
			LogUtil.info("jsonArray.size()=" + jsonArray.size() + ",reportContent="+reportContent.length());
			try {
				response = HttpClientUtils.post(tBPostManageReport, paramMap, "UTF-8");
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
					systemService.executeSql("update t_b_post_manage set report_group_status = '1',report_group_time = NOW(),report_group_man = '"+ curUser +"' where id in ('"+ids.replace(",","','")+"')");
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