package com.sdzk.buss.web.common.service.impl;

import com.sddb.buss.identification.entity.RiskTaskEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.service.SfService;
import com.sdzk.buss.web.gjj.entity.*;
import com.sdzk.buss.web.layer.entity.TBLayerEntity;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("sfService" )
@Transactional
public class SfServiceImpl  extends CommonServiceImpl implements SfService {

	@Autowired
	private SystemService systemService;



//===========================风险管控业务升级开始==============================
	/**
	 * 保存  国家局上报
	 * @param taskAllId     taskAllId
	 * @param riskId    风险id
	 */
	@Override
	public void saveSfRiskControl(String taskAllId,String riskId){

		String reportContentSql="SELECT\n" +
				"\t'1' stateFlag ,task.id riskControlCode,risk.id riskCode,risk.risk_level riskLevel,taskAll.manage_type controlType ,taskAll.manage_time controlDate,depart.departname controlDepart,task.create_name controlMan\n" +
				"FROM\n" +
				"\tt_b_risk_manage_task task\n" +
				"\tLEFT JOIN t_b_risk_manage_task_all taskAll ON task.task_all_id = taskAll.id\n" +
				"\tleft join t_s_base_user baseUser on task.create_by=baseUser.username\n" +
				"\tleft join t_s_user_org userOrg on userOrg.user_id=baseUser.id\n" +
				"\tleft join t_s_depart depart on userOrg.org_id=depart.id\n" +
				"\tLEFT JOIN t_b_risk_identification risk on task.risk_id= risk.id\n" +
				"\t where taskAll.id='"+taskAllId+"' and task.risk_id='"+riskId+"'";
		List<SfRiskControlEntity> sfRiskControlEntityList = systemService.findObjForJdbc(reportContentSql,1,9999,SfRiskControlEntity.class);

		for (SfRiskControlEntity riskControl : sfRiskControlEntityList) {
			String unImplemented="";
			String implemented="";
			String implementedSql="select id from sf_risk_measure where (risk_factors_id,risk_code) in ( select hazard_factor_id,risk_id from t_b_risk_manage_hazard_factor factor where 1=1 and factor.risk_manage_task_id='"+ riskControl.getRiskControlCode() +"' and handle_status='1')";
			String unImplementedSql="select id from sf_risk_measure where (risk_factors_id,risk_code) in ( select hazard_factor_id,risk_id from t_b_risk_manage_hazard_factor factor where 1=1 and factor.risk_manage_task_id='"+ riskControl.getRiskControlCode() +"' and handle_status='0')";
//                List<String> implementedList = systemService.findListbySql("select hazard_factor_id id from t_b_risk_manage_hazard_factor factor where 1=1 and factor.risk_manage_task_id='" + riskControl.getRiskControlCode() + "' and handle_status='1'");
//                List<String> unImplementedList = systemService.findListbySql("select hazard_factor_id id from t_b_risk_manage_hazard_factor factor where 1=1 and factor.risk_manage_task_id='" + riskControl.getRiskControlCode() + "' and handle_status='0'");
			List<Integer> implementedList = systemService.findListbySql(implementedSql);
			List<Integer> unImplementedList = systemService.findListbySql(unImplementedSql);

			for (Integer integer : implementedList) {
				if (StringUtils.isBlank(implemented)){
					implemented=String.valueOf(integer);
				}else {
					implemented+="&"+String.valueOf(integer);
				}
			}
			for (Integer integer : unImplementedList) {
				if (StringUtils.isBlank(unImplemented)){
					unImplemented=String.valueOf(integer);
				}else {
					unImplemented+="&"+String.valueOf(integer);
				}
			}
//			for (String s : implementedList) {
//				if (StringUtils.isBlank(implemented)){
//					implemented=s;
//				}else {
//					implemented+="&"+s;
//				}
//			}
//			for (String s : unImplementedList) {
//				if (StringUtils.isBlank(unImplemented)){
//					unImplemented=s;
//				}else {
//					unImplemented+="&"+s;
//				}
//			}
			riskControl.setImplemented(implemented);
			riskControl.setUnimplemented(unImplemented);
			riskControl.setDataTime(new Date());
			riskControl.setIsDelete(Constants.IS_DELETE_N);
		}


		systemService.batchSave(sfRiskControlEntityList);
	}

	//==========================风险管控业务升级结束===============================


	@Override
	public SFPictureInfoEntity savePictureInfo(TBLayerEntity layerEntity) {

		SFPictureInfoEntity entity=new SFPictureInfoEntity();
//		entity.setLayerName(layerEntity.getLayerDetailName());
		entity.setLayerId(layerEntity.getId());
		entity.setStateFlag(Constants.GJJ_STATE_FLAG_1);
		entity.setFileType("1");//风险四色图
		entity.setFileName(layerEntity.getLayerDetailName());
		entity.setIsDelete(Constants.IS_DELETE_N);
		systemService.save(entity);
		return entity;
	}

	@Override
	public String isExistPicture(String layerId) {
		String sql="select count(1) count,id from sf_picture_info where 1=1 and is_delete='0' and file_type='1' and layer_id='"+layerId+"'";
		Map<String, Object> oneForJdbc = systemService.findOneForJdbc(sql);
		if (oneForJdbc==null||"0".equals(oneForJdbc.get("count").toString())){
			return "";
		}else {
			return oneForJdbc.get("id").toString();
		}
	}

	@Override
	public void saveOrUpdateSfHiddenRel(String hiddenId) {
		String sql="select id from sf_hidden_rel where 1=1 and is_delete='0' and hidden_id='"+hiddenId+"' limit 1";
		Map<String, Object> oneForJdbc = systemService.findOneForJdbc(sql);
		SfHiddenRelEntity sfHiddenRel=new SfHiddenRelEntity();
		if (oneForJdbc!=null&&oneForJdbc.get("id")!=null){
			sfHiddenRel.setId(Integer.parseInt(oneForJdbc.get("id").toString()));
			sfHiddenRel.setStateFlag(Constants.GJJ_STATE_FLAG_2);
		}else {
			sfHiddenRel.setStateFlag(Constants.GJJ_STATE_FLAG_1);
		}
		sfHiddenRel.setHiddenId(hiddenId);
		sfHiddenRel.setIsDelete(Constants.IS_DELETE_N);

		systemService.saveOrUpdate(sfHiddenRel);
	}

	@Override
	public void deleteSfHiddenRel(String hiddenId) {
		String sql="UPDATE sf_hidden_rel set is_delete='"+ Constants.IS_DELETE_Y+"',state_flag='"+ Constants.GJJ_STATE_FLAG_3+"' where hidden_id='"+hiddenId+"'";
		systemService.executeSql(sql);
	}

	@Override
	public void saveOrUpdateSfVioRel(String vioId) {
		String sql="select id from sf_vio_rel where 1=1 and is_delete='0' and vio_id='"+vioId+"' limit 1";
		Map<String, Object> oneForJdbc = systemService.findOneForJdbc(sql);
		SfVioRelEntity sfVioRel=new SfVioRelEntity();
		if (oneForJdbc!=null&&oneForJdbc.get("id")!=null){
			sfVioRel.setId(Integer.parseInt(oneForJdbc.get("id").toString()));
			sfVioRel.setStateFlag(Constants.GJJ_STATE_FLAG_2);
		}else {
			sfVioRel.setStateFlag(Constants.GJJ_STATE_FLAG_1);
		}
		sfVioRel.setVioId(vioId);
		sfVioRel.setIsDelete(Constants.IS_DELETE_N);

		systemService.saveOrUpdate(sfVioRel);
	}

	@Override
	public void deleteSfVioRel(String vioId) {
		String sql="UPDATE sf_vio_rel set is_delete='"+ Constants.IS_DELETE_Y+"',state_flag='"+ Constants.GJJ_STATE_FLAG_3+"' where vio_id='"+vioId+"'";
		systemService.executeSql(sql);
	}

	@Override
	public void saveOrUpdateRiskTask(RiskTaskEntity riskTask, String stateFlag) {
		SfRiskTaskRelEntity riskTaskRelEntity=new SfRiskTaskRelEntity();
		//查询riskTaskId是否存在  不存在则新增
		Map<String, Object> oneForJdbc = systemService.findOneForJdbc("select id from sf_risk_task_rel where 1=1 and is_delete='"+ Constants.IS_DELETE_N+"' and risk_task_id='"+riskTask.getId()+"' limit 1");
		if (oneForJdbc!=null&&oneForJdbc.get("id")!=null){
			riskTaskRelEntity.setId(Integer.parseInt(oneForJdbc.get("id").toString()));
		}
		riskTaskRelEntity.setRiskTaskId(riskTask.getId());
		//如果是删除标识  同时将删除标识设置为删除
		if (Constants.GJJ_STATE_FLAG_3.equals(stateFlag)){
			riskTaskRelEntity.setIsDelete(Constants.IS_DELETE_Y);
		}else {
			riskTaskRelEntity.setIsDelete(Constants.IS_DELETE_N);
		}
		riskTaskRelEntity.setStateFlag(stateFlag);

		systemService.saveOrUpdate(riskTaskRelEntity);
	}

	@Override
	public void saveOrUpdateSfRiskMeasureEntity(String riskId,String addressId,String dutyManager,String factorId,String measures,String postName,String isUpload) {
		String sql="select state_flag stateFlag,id from sf_risk_measure where risk_factors_id='"+ factorId+"' and risk_code='"+ riskId+"'";
		List<Map<String, Object>> oneForJdbc = systemService.findForJdbc(sql);
		if (oneForJdbc==null||oneForJdbc.size()==0){
			SfRiskMeasureEntity sfRiskMeasure=new SfRiskMeasureEntity();
			sfRiskMeasure.setRiskFactorsId(factorId);
			sfRiskMeasure.setStateFlag(Constants.GJJ_STATE_FLAG_1);
			sfRiskMeasure.setRiskCode(riskId);
			sfRiskMeasure.setRiskPointCode(addressId);
			sfRiskMeasure.setMeasures(measures);
			sfRiskMeasure.setPost(postName);
			if ("".equals(dutyManager)){
				sfRiskMeasure.setDepart("");
			}else {
				String departSql = "select d.id from t_s_user_org uo, t_s_depart d " +
						" where uo.org_id=d.id and d.delete_flag='0' and uo.user_id='" + dutyManager + "'";
				List<String> departnameList = this.systemService.findListbySql(departSql);
				if (departnameList!=null&&departnameList.size()>0){
					sfRiskMeasure.setDepart(departnameList.get(0));
				}else {
					sfRiskMeasure.setDepart("");
				}
			}
			sfRiskMeasure.setDateTime(new Date());
			sfRiskMeasure.setIsUpload(isUpload);

			systemService.save(sfRiskMeasure);
		}else {
			SfRiskMeasureEntity sfRiskMeasure=new SfRiskMeasureEntity();
			sfRiskMeasure.setId(Integer.parseInt(oneForJdbc.get(0).get("id").toString()));
			sfRiskMeasure.setRiskFactorsId(factorId);
			sfRiskMeasure.setStateFlag(Constants.GJJ_STATE_FLAG_2);
			sfRiskMeasure.setRiskCode(riskId);
			sfRiskMeasure.setRiskPointCode(addressId);
			sfRiskMeasure.setMeasures(measures);
			sfRiskMeasure.setPost(postName);
			if ("".equals(dutyManager)){
				sfRiskMeasure.setDepart("");
			}else {
				String departSql = "select d.id from t_s_user_org uo, t_s_depart d " +
						" where uo.org_id=d.id and d.delete_flag='0' and uo.user_id='" + dutyManager + "'";
				List<String> departnameList = this.systemService.findListbySql(departSql);
				if (departnameList!=null&&departnameList.size()>0){
					sfRiskMeasure.setDepart(departnameList.get(0));
				}else {
					sfRiskMeasure.setDepart("");
				}
			}
			sfRiskMeasure.setIsUpload(isUpload);
			systemService.saveOrUpdate(sfRiskMeasure);
		}
	}
}
