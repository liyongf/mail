package com.sdzk.buss.web.tbdangersourceapost.service.impl;
import com.sdzk.buss.web.tbdangersourceapost.service.TBDangerSourceaPostServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.sdzk.buss.web.tbdangersourceapost.entity.TBDangerSourceaPostEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;

@Service("tBDangerSourceaPostService")
@Transactional
public class TBDangerSourceaPostServiceImpl extends CommonServiceImpl implements TBDangerSourceaPostServiceI {

	
 	public void delete(TBDangerSourceaPostEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TBDangerSourceaPostEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TBDangerSourceaPostEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TBDangerSourceaPostEntity t) throws Exception{
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
	private void doUpdateBus(TBDangerSourceaPostEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	/**
	 * 删除操作增强业务
	 * @param /id
	 * @return
	 */
	private void doDelBus(TBDangerSourceaPostEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
 	}
 	
 	private Map<String,Object> populationMap(TBDangerSourceaPostEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("danger_name", t.getDangerName());
		map.put("accident_type", t.getAccidentType());
		map.put("risk_type", t.getRiskType());
		map.put("risk_possibility", t.getRiskPossibility());
		map.put("risk_level", t.getRiskLevel());
		map.put("risk_loss", t.getRiskLoss());
		map.put("risk_affect_desc", t.getRiskAffectDesc());
		map.put("risk_value", t.getRiskValue());
		map.put("resp_measures", t.getRespMeasures());
		map.put("mang_measures", t.getMangMeasures());
		map.put("mang_standards", t.getMangStandards());
		map.put("internal_market_price", t.getInternalMarketPrice());
		map.put("standard_accordance", t.getStandardAccordance());
		map.put("based_entry", t.getBasedEntry());
		map.put("professionaltype", t.getProfessionaltype());
		map.put("ismajor", t.getIsmajor());
		map.put("distance", t.getDistance());
		map.put("surrounding", t.getSurrounding());
		map.put("monitor", t.getMonitor());
		map.put("emergency", t.getEmergency());
		map.put("res_depart", t.getResDepart());
		map.put("case_num", t.getCaseNum());
		map.put("recognize_time", t.getRecognizeTime());
		map.put("create_name", t.getCreateName());
		map.put("create_by", t.getCreateBy());
		map.put("create_date", t.getCreateDate());
		map.put("update_name", t.getUpdateName());
		map.put("update_by", t.getUpdateBy());
		map.put("update_date", t.getUpdateDate());

        map.put("LEC_risk_possibility", t.getLecRiskPossibility());
        map.put("LEC_risk_loss", t.getLecRiskLoss());
        map.put("LEC_exposure", t.getLecExposure());
        map.put("LEC_risk_value", t.getLecRiskValue());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TBDangerSourceaPostEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{danger_name}",String.valueOf(t.getDangerName()));
 		sql  = sql.replace("#{accident_type}",String.valueOf(t.getAccidentType()));
 		sql  = sql.replace("#{risk_type}",String.valueOf(t.getRiskType()));
 		sql  = sql.replace("#{risk_possibility}",String.valueOf(t.getRiskPossibility()));
 		sql  = sql.replace("#{risk_level}",String.valueOf(t.getRiskLevel()));
 		sql  = sql.replace("#{risk_loss}",String.valueOf(t.getRiskLoss()));
 		sql  = sql.replace("#{risk_affect_desc}",String.valueOf(t.getRiskAffectDesc()));
 		sql  = sql.replace("#{risk_value}",String.valueOf(t.getRiskValue()));
 		sql  = sql.replace("#{resp_measures}",String.valueOf(t.getRespMeasures()));
 		sql  = sql.replace("#{mang_measures}",String.valueOf(t.getMangMeasures()));
 		sql  = sql.replace("#{mang_standards}",String.valueOf(t.getMangStandards()));
 		sql  = sql.replace("#{internal_market_price}",String.valueOf(t.getInternalMarketPrice()));
 		sql  = sql.replace("#{standard_accordance}",String.valueOf(t.getStandardAccordance()));
 		sql  = sql.replace("#{based_entry}",String.valueOf(t.getBasedEntry()));
 		sql  = sql.replace("#{professionaltype}",String.valueOf(t.getProfessionaltype()));
 		sql  = sql.replace("#{ismajor}",String.valueOf(t.getIsmajor()));
 		sql  = sql.replace("#{distance}",String.valueOf(t.getDistance()));
 		sql  = sql.replace("#{surrounding}",String.valueOf(t.getSurrounding()));
 		sql  = sql.replace("#{monitor}",String.valueOf(t.getMonitor()));
 		sql  = sql.replace("#{emergency}",String.valueOf(t.getEmergency()));
 		sql  = sql.replace("#{res_depart}",String.valueOf(t.getResDepart()));
 		sql  = sql.replace("#{case_num}",String.valueOf(t.getCaseNum()));
 		sql  = sql.replace("#{recognize_time}",String.valueOf(t.getRecognizeTime()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));

//        sql  = sql.replace("#{LEC_risk_possibility}", String.valueOf(t.getLecRiskPossibility()));
//        sql  = sql.replace("#{LEC_risk_loss}", String.valueOf(t.getLecRiskLoss()));
//        sql  = sql.replace("#{LEC_exposure}", String.valueOf(t.getLecExposure()));
//        sql  = sql.replace("#{LEC_risk_value}", String.valueOf(t.getLecRiskValue()));

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
					javaInter.execute("t_b_danger_sourcea_post",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}
}