package com.sdzk.buss.web.dangersource.service.impl;
import com.sdzk.buss.web.address.entity.TBAddressDepartRelEntity;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.StaticDataMap;
import com.sdzk.buss.web.common.utils.AesUtil;
import com.sdzk.buss.web.common.utils.CommonUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerAddresstRelEntity;
import com.sdzk.buss.web.dangersource.entity.TbActivityManageEntity;
import com.sdzk.buss.web.dangersource.entity.TbHazardManageEntity;
import com.sdzk.buss.web.dangersource.service.TBDangerSourceServiceI;
import com.sdzk.buss.web.dsmanagerecord.entity.TBDsManageRecordEntity;
import com.sdzk.buss.web.specialevaluation.entity.TBSeDsRelationEntity;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import com.sdzk.buss.web.uploadthreads.UploadThread;
import com.sdzk.sys.synctocloud.service.SyncToCloudService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.web.system.pojo.base.TSBaseUser;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.Serializable;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;

import org.jeecgframework.core.util.*;
import org.jeecgframework.web.cgform.exception.NetServiceException;

@Service("tBDangerSourceService")
@Transactional
public class TBDangerSourceServiceImpl extends CommonServiceImpl implements TBDangerSourceServiceI {

    @Autowired
    private SystemService systemService;
    @Autowired
    private SyncToCloudService syncToCloudService;

	
 	public void delete(TBDangerSourceEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TBDangerSourceEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TBDangerSourceEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TBDangerSourceEntity t) throws Exception{
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
	private void doUpdateBus(TBDangerSourceEntity t) throws Exception{
		//-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
        if (Constants.IS_DELETE_Y.equals(t.getIsDelete())) {
            //删除风险点关联关系
            systemService.executeSql("delete from t_b_danger_address_rel where danger_id = '"+t.getId()+"'");
        }
 	}
 	/**
	 * 删除操作增强业务
	 * @return
	 */
	private void doDelBus(TBDangerSourceEntity t) throws Exception{
	    //-----------------sql增强 start----------------------------
	 	//-----------------sql增强 end------------------------------
	 	
	 	//-----------------java增强 start---------------------------
	 	//-----------------java增强 end-----------------------------
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
					javaInter.execute("t_b_danger_source",data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}

    //上报年度风险
    public Map<String,String> reportYearRisk(String ids,boolean isFromTask){
        Map<String,String> retMap =new HashMap<>();
        retMap.put("code", Constants.LOCAL_RESULT_CODE_SUCCESS);
        retMap.put("message", "上报成功");

        CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class);
        try{
         //   String []status = new String[]{Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE};
            cq.eq("auditStatus", Constants.DANGER_SOURCE_AUDITSTATUS_CLOSE);
            cq.eq("reportStatus",Constants.DANGER_SOURCE_REPORT_UNREPORT);
            cq.eq("origin", Constants.DANGER_SOURCE_ORIGIN_MINE);
            cq.eq("isDelete", Constants.IS_DELETE_N);
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
        List<TBDangerSourceEntity> list = getListByCriteriaQuery(cq, false);
        if(null==list || list.size()<=0){
            retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
            retMap.put("message", "上报失败");
            return retMap;
        }

        Date date = null;
        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < list.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(list.get(i).getId());
            TBDangerSourceEntity entity = list.get(i);
            Map<String,String> sendMap = new HashMap<>();


            sendMap.put("id",entity.getId());
            sendMap.put("yeMhazardDesc",entity.getYeMhazardDesc());
            String yeProfession = entity.getYeProfession();
            String professionTemp = StaticDataMap.yeProfessionMap.get(yeProfession);
            professionTemp = professionTemp == null?StaticDataMap.PROFESSION_OTHER:yeProfession;
            sendMap.put("yeProfession",professionTemp);
            String accident = entity.getYeAccident();
            String accidentTemp = "";
            StringBuffer sb = new StringBuffer();
            if(StringUtils.isNotBlank(accident)){
                String aa [] = accident.split(",");

                boolean isContainsOther = false;
                for(String str : aa){
                  String bb =  StaticDataMap.yeAccidentMap.get(str);
                  if(StringUtils.isBlank(bb)){
                      bb = StaticDataMap.ACCIDENT_OTHER;
                      isContainsOther = true;
                      if(StringUtils.isNotBlank(sb.toString())){
                          sb.append(",");
                      }
                      sb.append(bb);
                  }else{
                      bb = str;
                  }
                  if(!isContainsOther){
                      if(StringUtils.isNotBlank(sb.toString())){
                          sb.append(",");
                      }
                      sb.append(bb);
                  }
                }
            }
            accidentTemp = sb.toString();
            sendMap.put("yeAccident",accidentTemp);
            sendMap.put("yeStandard",entity.getYeStandard());
            sendMap.put("yePossiblyHazard",entity.getYePossiblyHazard());
            sendMap.put("yeHazardCate",entity.getYeHazardCate());
            sendMap.put("yeRiskGrade",entity.getYeRiskGrade());
            sendMap.put("yeCaseNum",entity.getYeCaseNum());
            sendMap.put("yeRecognizeTime",DateUtils.date2Str(entity.getYeRecognizeTime(), DateUtils.datetimeFormat));
            sendMap.put("isMajor",entity.getIsMajor());
            sendMap.put("riskValue",entity.getRiskValue());
            sendMap.put("manageMeasure",entity.getManageMeasure());
            sendMap.put("LECRiskValue",entity.getLecRiskValue() == null ?"":entity.getLecRiskValue().toString());
            sendMap.put("docSource",entity.getDocSource());
            sendMap.put("sectionName",entity.getSectionName());
            sendMap.put("dutyPost",entity.getPost()==null ?"":entity.getPost().getPostName());
            sendMap.put("activity",entity.getActivity() == null ? "":entity.getActivity().getActivityName());
            sendMap.put("hazardManageName",entity.getHazard()==null?"":entity.getHazard().getHazardName());
            String damageType = entity.getDamageType();
            String damageTypeTemp = StaticDataMap.damageTypeMap.get(damageType);
            damageTypeTemp = damageTypeTemp == null?StaticDataMap.DAMAGETYPE_OTHER:damageType;
            sendMap.put("damageType",damageTypeTemp);
            sendMap.put("hiddenLevel",entity.getHiddenLevel());
            sendList.add(sendMap);
        }

        String reportContent=JSONHelper.toJSONString(sendList);

        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportYearRisk");

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
        // 上传风险点
        String addressReportUrl = ResourceUtil.getConfigByName("addressReportUrl");
        String addressReportContent = getAddressReportContent();
        Map<String,String> addressParamMap = new HashMap<String,String>();
        addressParamMap.put("token", ciphertext);
        addressParamMap.put("mineCode", mineCode);
        addressParamMap.put("reportContent", addressReportContent);
        UploadThread addressThread = new UploadThread(addressReportUrl,"UTF-8",addressParamMap);
        addressThread.start();
        // 上传一类危险源
        String hazardReportUrl = ResourceUtil.getConfigByName("hazardReportUrl");
        String hazardReportContent = getHazardReportContent();
        Map<String,String> hazardParamMap = new HashMap<String,String>();
        hazardParamMap.put("token", ciphertext);
        hazardParamMap.put("mineCode", mineCode);
        hazardParamMap.put("reportContent", hazardReportContent);
        UploadThread hazardThread = new UploadThread(hazardReportUrl,"UTF-8",hazardParamMap);
        hazardThread.start();
        // 上传风险点关联单位
        String addressDepartRelUrl = ResourceUtil.getConfigByName("addressDepartRelUrl");
        String addressDepartRelContent = getAddressDepartRelContent();
        Map<String,String> addressDepartRelParamMap = new HashMap<String,String>();
        addressDepartRelParamMap.put("token", ciphertext);
        addressDepartRelParamMap.put("mineCode", mineCode);
        addressDepartRelParamMap.put("reportContent", addressDepartRelContent);
        UploadThread addressDepartRelThread = new UploadThread(addressDepartRelUrl,"UTF-8",addressDepartRelParamMap);
        addressDepartRelThread.start();
        // 上传风险点关联风险
        String addressDangerRelUrl = ResourceUtil.getConfigByName("addressDangerRelUrl");
        String addressDangerRelContent = getAddressDangerRelContent(idSb);
        Map<String,String> addressDangerRelParamMap = new HashMap<String,String>();
        addressDangerRelParamMap.put("token", ciphertext);
        addressDangerRelParamMap.put("mineCode", mineCode);
        addressDangerRelParamMap.put("reportContent", addressDangerRelContent);
        UploadThread addressDangerRelThread = new UploadThread(addressDangerRelUrl,"UTF-8",addressDangerRelParamMap);
        addressDangerRelThread.start();
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
            String curUser;
            try {
                //定时任务无法获取session
                curUser = ResourceUtil.getSessionUserName().getRealName();
            } catch (Exception e) {
                curUser = "定时任务";
            }
            String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
            if (StringUtil.isNotEmpty(idSb.toString())) {
                if(!isFromTask){
                    executeSql("update t_b_danger_source set report_name = '"+curUser+"' , report_date = '"+reportDate+"' , report_status = '"+Constants.DANGER_SOURCE_REPORT_REPORT+"' where id in ('"+idSb.toString().replace(",","','")+"')");
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
     * 上传
     * @param idSb
     * @return
     */
    private String getAddressDangerRelContent(StringBuffer idSb) {
        List<Object> sendList = new ArrayList<Object>();
        String[] idArray = idSb.toString().split(",");
        CriteriaQuery cq = new CriteriaQuery(TBDangerAddresstRelEntity.class);
        try{
            cq.in("dangerId",idArray);
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        List<TBDangerAddresstRelEntity> list = systemService.getListByCriteriaQuery(cq,false);
        if(list != null && !list.isEmpty()){
            for(TBDangerAddresstRelEntity entity : list){
                Map<String,String> sendMap = new HashMap<>();

                sendMap.put("id",entity.getId());
                sendMap.put("darDangerId",entity.getDangerId());
                sendMap.put("darAddressId",entity.getAddressId());
                sendMap.put("darRiskLevel",entity.getRiskLevel());
                sendList.add(sendMap);
            }
        }
        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 查询所有风险点关联的组织机构
     * @return
     */
    private String getAddressDepartRelContent() {
        List<TBAddressDepartRelEntity> hazardList = systemService.getList(TBAddressDepartRelEntity.class);
        List<Object> sendList = new ArrayList<Object>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < hazardList.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(hazardList.get(i).getId());
            TBAddressDepartRelEntity entity = hazardList.get(i);
            Map<String,String> sendMap = new HashMap<>();
            String departId = entity.getDepartId();
            if(StringUtils.isNotBlank(departId)){
                TSDepart depart = systemService.getEntity(TSDepart.class,departId);
                if(depart != null){
                    sendMap.put("adrDepartName",depart.getDepartname());
                }else{
                    sendMap.put("adrDepartName","");
                }
            }
            sendMap.put("id",entity.getId());
            sendMap.put("adrAddressId",entity.getAddressId());
            sendMap.put("adrDutyMan",entity.getDutyMan());
            sendList.add(sendMap);
        }

        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 查询所有一类危险源
     * @return
     */
    private String getHazardReportContent() {
        List<TbHazardManageEntity> hazardList = systemService.getList(TbHazardManageEntity.class);
        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < hazardList.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(hazardList.get(i).getId());
            TbHazardManageEntity entity = hazardList.get(i);
            Map<String,String> sendMap = new HashMap<>();

            sendMap.put("id",entity.getId());
            sendMap.put("hmHazardName",entity.getHazardName());
            sendMap.put("hmHazardType",entity.getHazardType());
            sendMap.put("hmDamageType",entity.getDamageType());
            sendMap.put("hmProfessionType",entity.getProfessionType());
            sendMap.put("hmAccidentType",entity.getAccidentType());
            sendMap.put("isDelete",entity.getIsDelete());
            sendList.add(sendMap);
        }

        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 查询所有风险点
     * @return
     */
    private String getAddressReportContent() {
        List<TBAddressInfoEntity> addressList = systemService.getList(TBAddressInfoEntity.class);
        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < addressList.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(addressList.get(i).getId());
            TBAddressInfoEntity entity = addressList.get(i);
            Map<String,String> sendMap = new HashMap<>();

            sendMap.put("id",entity.getId());
            sendMap.put("aiAddressName",entity.getAddress());
            sendMap.put("aiManageMan",entity.getManageMan());
            sendMap.put("belongLayer",entity.getBelongLayer().getId());
            sendMap.put("isDelete",entity.getIsDelete());
            sendList.add(sendMap);
        }
        return JSONHelper.toJSONString(sendList);
    }

    //撤回年度风险
    public Map<String,String> callbackYearRisk(String ids){
        Map<String,String> retMap =new HashMap<>();
        retMap.put("code", Constants.LOCAL_RESULT_CODE_SUCCESS);
        retMap.put("message", "上报成功");
        String token = ResourceUtil.getConfigByName("token");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("callbackYearRisk");
        String result = null;

        /**
         * 加密过程
         * */
        String tempToken = "token=" + token + "&mineCode=" + mineCode + "&riskIds=" + ids;
        String ciphertext = null;
        try {
            ciphertext = AesUtil.encryptWithIV(tempToken, token);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(StringUtil.isNotEmpty(ids)){
            try {
                Map<String,String> paramMap = new HashMap<String,String>();
                paramMap.put("token", ciphertext);
                paramMap.put("mineCode", mineCode);
//                paramMap.put("riskIds", ids);

                result = HttpClientUtils.post(url, paramMap, "UTF-8");
            }catch (NetServiceException e) {
                retMap.put("code",Constants.LOCAL_RESULT_CODE_FAILURE);
                retMap.put("message","撤回失败："+e.getMessage());
                return retMap;
            }

            //解析rpc返回的json
            try{
                if(result!=null){
                    JSONObject resultJson = JSONHelper.jsonstr2json(result);
                    String code = resultJson.getString("code");
                    if(!code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)){//请求成功
                        retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                        retMap.put("message", "撤回失败");
                        return retMap;
                    }
                }else {
                    retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                    retMap.put("message", "撤回失败");
                    return retMap;
                }
            }catch (Exception e){
                retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                retMap.put("message", "撤回失败");
                return retMap;
            }

            //更新本地数据库
            try {
                for(String id : ids.split(",")){
                    //设置本地数据为待上报
                    TBDangerSourceEntity entity = get(TBDangerSourceEntity.class, id);
                    entity.setReportStatus(Constants.DANGER_SOURCE_REPORT_UNREPORT);
                    saveOrUpdate(entity);
                }
            }catch (Exception e){
                retMap.put("code", Constants.LOCAL_RESULT_CODE_FAILURE);
                retMap.put("message", "撤回成功，但本地数据库操作失败");
                return retMap;
            }

        }
        return retMap;
    }

    /**
     * 重大风险上报
     * @param ids
     * @return
     */
    @Override
    public AjaxJson reportMajorYearRisk(String ids) {
        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setMsg("上报成功");
        if (StringUtil.isNotEmpty(ids)) {
            JSONArray jsonArray = new JSONArray();
            for (String id : ids.split(",")){
                TBDangerSourceEntity entity = this.getEntity(TBDangerSourceEntity.class, id);
                if (entity != null && Constants.IS_MAJORDangerSource_Y.equals(entity.getIsMajor())) {
                    JSONObject dsJson = new JSONObject();
                    dsJson.put("id", entity.getId());
                    dsJson.put("yeMhazardDesc", entity.getYeMhazardDesc());
                    dsJson.put("yeProfession", entity.getYeProfession());
                    dsJson.put("yeAccident", entity.getYeAccident());
                    dsJson.put("yeReference", entity.getYeReference());
                    dsJson.put("yeLocation", entity.getYeLocation());
                    dsJson.put("yeDistance", entity.getYeDistance());
                    dsJson.put("yeSurrounding", entity.getYeSurrounding());
                    dsJson.put("yeStandard", entity.getYeStandard());
                    dsJson.put("yeMonitor", entity.getYeMonitor());
                    dsJson.put("yeEmergency", entity.getYeEmergency());
                    dsJson.put("yeResDepart", entity.getYeResDepart());
                    dsJson.put("yePossiblyHazard", entity.getYePossiblyHazard());
                    dsJson.put("yeProbability", entity.getYeProbability());
                    dsJson.put("yeCost", entity.getYeCost());
                    dsJson.put("yeHazardCate", entity.getYeHazardCate());
                    dsJson.put("yeRiskGrade", entity.getYeRiskGrade());
                    dsJson.put("yeCaseNum", entity.getYeCaseNum());
                    dsJson.put("yeRecognizeTime", DateUtils.date2Str(entity.getYeRecognizeTime(), DateUtils.datetimeFormat));
                    dsJson.put("isMajor", entity.getIsMajor());
                    dsJson.put("yeOrigin", entity.getOrigin());
                    //获取管控记录
                    List<TBDsManageRecordEntity> manageRecordList = findByProperty(TBDsManageRecordEntity.class, "dangerId", entity.getId());
                    if (manageRecordList != null && manageRecordList.size() >0){
                        JSONArray recordArray = new JSONArray();
                        for (TBDsManageRecordEntity recordEntity : manageRecordList) {
                            JSONObject recordObject = new JSONObject();
                            if (StringUtil.isNotEmpty(recordEntity.getController())) {
                                TSBaseUser user = getEntity(TSBaseUser.class, recordEntity.getController());
                                recordObject.put("controller", user!=null?user.getRealName():null);
                            } else {
                                recordObject.put("controller", null);
                            }
                            recordObject.put("controlleDate", DateUtils.date2Str(recordEntity.getControlleDate(), DateUtils.date_sdf));
                            recordObject.put("workContent", recordEntity.getWorkContent());
                            recordObject.put("achieveEffect", recordEntity.getAchieveEffect());
                            recordArray.add(recordObject);
                        }
                        dsJson.put("manageRecord", recordArray);
                    }
                    //获取管控报告
                    List<Map<String, Object>> reportList = findForJdbc("select tsa.realpath, tsa.attachmenttitle FROM t_s_document as document , t_s_attachment as tsa where document.id = tsa.id and document.status = '1' and businesskey = '"+id+"'");
                    if (reportList != null && reportList.size() >0) {
                        JSONArray reportArray = new JSONArray();
                        for (Map<String, Object> report : reportList) {
                            JSONObject reportObject = new JSONObject();
                            reportObject.put("realpath", report.get("realpath"));//TODO 附件上传处理
                            reportObject.put("attachmenttitle", report.get("attachmenttitle"));
                            reportArray.add(reportObject);
                        }
                        dsJson.put("attachments", reportArray);
                    }
                    jsonArray.add(dsJson);
                }
            }
            if (jsonArray.size() > 0) {
                //上传
                String mineCode = ResourceUtil.getConfigByName("mine_code");
                String token = ResourceUtil.getConfigByName("token");

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

                String reportContent = jsonArray.toString();

                String reportMajorYearRiskUrl = ResourceUtil.getConfigByName("reportMajorYearRisk");
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("token", ciphertext);
                paramMap.put("mineCode", mineCode);
                paramMap.put("reportContent", reportContent);
                String response = null;
                try {
                    response = HttpClientUtils.post(reportMajorYearRiskUrl, paramMap, "UTF-8");
                } catch (NetServiceException e) {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("上报接口调用失败, 网络连接错误");
                }
                if (StringUtil.isNotEmpty(response)) {
                    JSONObject result = JSONHelper.jsonstr2json(response);
                    String code = (String) result.get("code");
                    //根据请求返回状态码更新数据库状态
                    if ("200".equals(code)) {
                        String userName;
                        try {
                            userName = ResourceUtil.getSessionUserName().getRealName();
                        } catch (Exception e) {
                            userName = "定时任务";
                        }
                        String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
                        this.updateBySqlString("update t_b_danger_source set report_status='"+Constants.DANGER_SOURCE_REPORT_REPORT
                                +"', report_name='"+userName+"', report_date='"+reportDate+"' where id in ('"+ids.replace(",","','")+"')");
                    } else {
                        ajaxJson.setMsg("上报失败,"+result.get("message"));
                    }
                }
            } else {
                ajaxJson.setMsg("所选中记录不存在或状态已改变");
            }
        } else {
            ajaxJson.setMsg("请选择要上报的数据");
        }

        return ajaxJson;

    }

    /**
     * 重大风险上报集团
     * @param ids
     * @return
     */
    @Override
    public AjaxJson reportMajorYearRiskToGroup(String ids) {
        AjaxJson ajaxJson = new AjaxJson();
        ajaxJson.setMsg("上报成功");
        if (StringUtil.isNotEmpty(ids)) {
            JSONArray jsonArray = new JSONArray();
            for (String id : ids.split(",")){
                TBDangerSourceEntity entity = this.getEntity(TBDangerSourceEntity.class, id);
                if (entity != null && Constants.IS_MAJORDangerSource_Y.equals(entity.getIsMajor())) {
                    JSONObject dsJson = new JSONObject();
                    dsJson.put("id",entity.getId());
                    dsJson.put("yeMhazardDesc",entity.getYeMhazardDesc());
                    dsJson.put("yeProfession", entity.getYeProfession());//proCate_gradeControl
                    dsJson.put("yeAccident",entity.getYeAccident());
                    dsJson.put("yeStandard",entity.getYeStandard());
                    dsJson.put("yePossiblyHazard",entity.getYePossiblyHazard());
                    dsJson.put("yeProbability", entity.getYeProbability());//probability
                    dsJson.put("yeCost", entity.getYeCost());
                    dsJson.put("yeHazardCate", entity.getYeHazardCate());//hazardCate
                    dsJson.put("yeRiskGrade", entity.getYeRiskGrade());//riskLevel
                    dsJson.put("riskValue",entity.getRiskValue());
                    //dsJson.put("yeCaseNum",entity.getYeCaseNum());
                    dsJson.put("isMajor",entity.getIsMajor());
                    dsJson.put("origin",entity.getOrigin());
                    dsJson.put("yeRecognizeTime",DateUtils.date2Str(entity.getYeRecognizeTime(), DateUtils.datetimeFormat));
                    dsJson.put("lecRiskPossibility",entity.getLecRiskPossibility()==null?"":String.valueOf(entity.getLecRiskPossibility()));
                    dsJson.put("lecRiskLoss",entity.getLecRiskLoss()==null?"":String.valueOf(entity.getLecRiskLoss()));
                    dsJson.put("lecExposure",entity.getLecExposure()==null?"":String.valueOf(entity.getLecExposure()));
                    dsJson.put("lecRiskValue",entity.getLecRiskValue()==null?"":String.valueOf(entity.getLecRiskValue()));
                    dsJson.put("manageMeasure", entity.getManageMeasure());
                    dsJson.put("docSource", entity.getDocSource());
                    dsJson.put("sectionName", entity.getSectionName());
                    dsJson.put("dutyPost",entity.getPost()==null ?"":entity.getPost().getId());
                    dsJson.put("activity",entity.getActivity() == null ? "":entity.getActivity().getId());
                    dsJson.put("hazardManageId",entity.getHazard()==null?"":entity.getHazard().getId());
                    String damageType = entity.getDamageType();
                    String damageTypeTemp = StaticDataMap.damageTypeMap.get(damageType);
                    damageTypeTemp = damageTypeTemp == null?StaticDataMap.DAMAGETYPE_OTHER:damageType;
                    dsJson.put("damageType",damageTypeTemp);
                    dsJson.put("hiddenLevel",entity.getHiddenLevel());
                    dsJson.put("fineMoney",entity.getFineMoney());
                    //获取管控记录
                    List<TBDsManageRecordEntity> manageRecordList = findByProperty(TBDsManageRecordEntity.class, "dangerId", entity.getId());
                    if (manageRecordList != null && manageRecordList.size() >0){
                        JSONArray recordArray = new JSONArray();
                        for (TBDsManageRecordEntity recordEntity : manageRecordList) {
                            JSONObject recordObject = new JSONObject();
                            recordObject.put("id", recordEntity.getId());
                            if (StringUtil.isNotEmpty(recordEntity.getController())) {
                                TSBaseUser user = getEntity(TSBaseUser.class, recordEntity.getController());
                                recordObject.put("controller", user!=null?user.getRealName():null);
                            } else {
                                recordObject.put("controller", null);
                            }
                            recordObject.put("controlleDate", DateUtils.date2Str(recordEntity.getControlleDate(), DateUtils.date_sdf));
                            recordObject.put("workContent", recordEntity.getWorkContent());
                            recordObject.put("achieveEffect", recordEntity.getAchieveEffect());
                            recordArray.add(recordObject);
                        }
                        dsJson.put("manageRecord", recordArray);
                    }
                    //获取管控报告
                    /*List<Map<String, Object>> reportList = findForJdbc("select tsa.realpath, tsa.attachmenttitle FROM t_s_document as document , t_s_attachment as tsa where document.id = tsa.id and document.status = '1' and businesskey = '"+id+"'");
                    if (reportList != null && reportList.size() >0) {
                        JSONArray reportArray = new JSONArray();
                        for (Map<String, Object> report : reportList) {
                            JSONObject reportObject = new JSONObject();
                            reportObject.put("realpath", report.get("realpath"));//TODO 附件上传处理
                            reportObject.put("attachmenttitle", report.get("attachmenttitle"));
                            reportArray.add(reportObject);
                        }
                        dsJson.put("attachments", reportArray);
                    }*/
                    jsonArray.add(dsJson);
                }
            }
            if (jsonArray.size() > 0) {
                //上传
                String mineCode = ResourceUtil.getConfigByName("mine_code");
                String token = ResourceUtil.getConfigByName("token_group");

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

                String reportContent = jsonArray.toString();

                String reportMajorYearRiskUrl = ResourceUtil.getConfigByName("reportMajorYearRiskUrl");
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("token", ciphertext);
                paramMap.put("mineCode", mineCode);
                paramMap.put("reportContent", reportContent);
                String response = null;
                try {
                    response = HttpClientUtils.post(reportMajorYearRiskUrl, paramMap, "UTF-8");
                } catch (NetServiceException e) {
                    ajaxJson.setSuccess(false);
                    ajaxJson.setMsg("上报接口调用失败, 网络连接错误");
                }
                if (StringUtil.isNotEmpty(response)) {
                    JSONObject result = JSONHelper.jsonstr2json(response);
                    String code = (String) result.get("code");
                    //根据请求返回状态码更新数据库状态
                    if ("200".equals(code)) {
                        String userName;
                        try {
                            userName = ResourceUtil.getSessionUserName().getRealName();
                        } catch (Exception e) {
                            userName = "定时任务";
                        }
                        String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
                        this.updateBySqlString("update t_b_danger_source set report_group_status='1', report_group_man='"+userName+"', report_group_time='"+reportDate+"' where id in ('"+ids.replace(",","','")+"')");
                    } else {
                        ajaxJson.setMsg("上报失败,"+result.get("message"));
                    }
                }
            } else {
                ajaxJson.setMsg("所选中记录不存在或状态已改变");
            }
        } else {
            ajaxJson.setMsg("请选择要上报的数据");
        }

        return ajaxJson;

    }

    /**
     * 上报年度风险相关数据到集团
     * @param list
     * @return
     */
    public AjaxJson reportYearRiskExToGroup(List<String> idList){
        AjaxJson j = new AjaxJson();
        String token = ResourceUtil.getConfigByName("token_group");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
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

        StringBuffer ids = new StringBuffer();
        for(int i = 0 ; i < idList.size() ; i++) {
            if (StringUtil.isNotEmpty(ids.toString())) {
                ids.append(",");
            }
            ids.append(idList.get(i));
        }

        String result = null;
        // 上传风险点
        String addressReportUrl = ResourceUtil.getConfigByName("addressInfoReportUrl");
        String addressReportContent = getAddressReportGroupContent();
        Map<String,String> addressParamMap = new HashMap<String,String>();
        addressParamMap.put("token", ciphertext);
        addressParamMap.put("mineCode", mineCode);
        addressParamMap.put("reportContent", addressReportContent);
        UploadThread addressThread = new UploadThread(addressReportUrl,"UTF-8",addressParamMap);
        addressThread.start();
        // 上传一类危险源
        String hazardReportUrl = ResourceUtil.getConfigByName("hazardInfoReportUrl");
        String hazardReportContent = getHazardReportGroupContent();
        Map<String,String> hazardParamMap = new HashMap<String,String>();
        hazardParamMap.put("token", ciphertext);
        hazardParamMap.put("mineCode", mineCode);
        hazardParamMap.put("reportContent", hazardReportContent);
        UploadThread hazardThread = new UploadThread(hazardReportUrl,"UTF-8",hazardParamMap);
        hazardThread.start();
        // 上传风险点关联风险
        String addressDangerRelUrl = ResourceUtil.getConfigByName("addressDangerRelGroupUrl");
        String addressDangerRelContent = getAddressDangerRelGroupContent(ids);
        Map<String,String> addressDangerRelParamMap = new HashMap<String,String>();
        addressDangerRelParamMap.put("token", ciphertext);
        addressDangerRelParamMap.put("mineCode", mineCode);
        addressDangerRelParamMap.put("reportContent", addressDangerRelContent);
        UploadThread addressDangerRelThread = new UploadThread(addressDangerRelUrl,"UTF-8",addressDangerRelParamMap);
        addressDangerRelThread.start();
        // 上传作业活动
        String activityReportUrl = ResourceUtil.getConfigByName("activityInfoReportUrl");
        String activityReportContent = getActivityReportGroupContent();
        Map<String,String> activityParamMap = new HashMap<String,String>();
        activityParamMap.put("token", ciphertext);
        activityParamMap.put("mineCode", mineCode);
        activityParamMap.put("reportContent", activityReportContent);
        UploadThread activityThread = new UploadThread(activityReportUrl,"UTF-8",activityParamMap);
        activityThread.start();
        // 上传岗位
        String postReportUrl = ResourceUtil.getConfigByName("postInfoReportUrl");
        String postReportContent = getPostReportGroupContent();
        Map<String,String> postParamMap = new HashMap<String,String>();
        postParamMap.put("token", ciphertext);
        postParamMap.put("mineCode", mineCode);
        postParamMap.put("reportContent", postReportContent);
        UploadThread postThread = new UploadThread(postReportUrl,"UTF-8",postParamMap);
        postThread.start();
        return j;
    }

    /**
     * 上报年度风险到集团
     * @param list
     * @return
     */
    public AjaxJson reportYearRiskToGroup(List<TBDangerSourceEntity> list){
        AjaxJson j = new AjaxJson();
        StringBuffer ids = new StringBuffer();

        j.setMsg("上报成功");

        if(null==list || list.size()<=0){
            j.setMsg("上报失败");
            return j;
        }

        List<Object> sendList = new ArrayList<>();
        for(int i = 0 ; i < list.size() ; i++) {
            TBDangerSourceEntity entity = list.get(i);
            Map<String,String> sendMap = new HashMap<>();
            if (StringUtil.isNotEmpty(ids.toString())) {
                ids.append(",");
            }
            ids.append(entity.getId());
            sendMap.put("id",entity.getId());
            sendMap.put("yeMhazardDesc",entity.getYeMhazardDesc());
            sendMap.put("yeProfession", entity.getYeProfession());//proCate_gradeControl
            sendMap.put("yeAccident",entity.getYeAccident());
            sendMap.put("yeStandard",entity.getYeStandard());
            sendMap.put("yePossiblyHazard",entity.getYePossiblyHazard());
            sendMap.put("yeProbability", entity.getYeProbability());//probability
            sendMap.put("yeCost", entity.getYeCost());
            sendMap.put("yeHazardCate", entity.getYeHazardCate());//hazardCate
            sendMap.put("yeRiskGrade", entity.getYeRiskGrade());//riskLevel
            sendMap.put("yeRecognizeTime",DateUtils.date2Str(entity.getYeRecognizeTime(), DateUtils.datetimeFormat));
            sendMap.put("riskValue",entity.getRiskValue());
            sendMap.put("manageMeasure", entity.getManageMeasure());
            //sendMap.put("yeCaseNum",entity.getYeCaseNum());
            sendMap.put("origin",entity.getOrigin());
            sendMap.put("isMajor",entity.getIsMajor());
            sendMap.put("lecRiskPossibility",entity.getLecRiskPossibility()==null?"":String.valueOf(entity.getLecRiskPossibility()));
            sendMap.put("lecRiskLoss",entity.getLecRiskLoss()==null?"":String.valueOf(entity.getLecRiskLoss()));
            sendMap.put("lecExposure",entity.getLecExposure()==null?"":String.valueOf(entity.getLecExposure()));
            sendMap.put("lecRiskValue",entity.getLecRiskValue()==null?"":String.valueOf(entity.getLecRiskValue()));
            sendMap.put("docSource", entity.getDocSource());
            sendMap.put("sectionName", entity.getSectionName());
            sendMap.put("dutyPost",entity.getPost()==null ?"":entity.getPost().getId());
            sendMap.put("activity",entity.getActivity() == null ? "":entity.getActivity().getId());
            sendMap.put("hazardManageId",entity.getHazard()==null?"":entity.getHazard().getId());
            String damageType = entity.getDamageType();
            String damageTypeTemp = StaticDataMap.damageTypeMap.get(damageType);
            damageTypeTemp = damageTypeTemp == null?StaticDataMap.DAMAGETYPE_OTHER:damageType;
            sendMap.put("damageType",damageTypeTemp);
            sendMap.put("hiddenLevel",entity.getHiddenLevel());
            sendMap.put("fineMoney",entity.getFineMoney());
            sendMap.put("isDelete",entity.getIsDelete());
            sendMap.put("auditStatus",entity.getAuditStatus());
            sendList.add(sendMap);
        }

        String reportContent=JSONHelper.toJSONString(sendList);

        String token = ResourceUtil.getConfigByName("token_group");
        String mineCode = ResourceUtil.getConfigByName("mine_code");
        String url = ResourceUtil.getConfigByName("reportGroupRootUrl")+ResourceUtil.getConfigByName("reportYearRiskToGroup");

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
 /*       // 上传风险点
        String addressReportUrl = ResourceUtil.getConfigByName("addressInfoReportUrl");
        String addressReportContent = getAddressReportGroupContent();
        Map<String,String> addressParamMap = new HashMap<String,String>();
        addressParamMap.put("token", ciphertext);
        addressParamMap.put("mineCode", mineCode);
        addressParamMap.put("reportContent", addressReportContent);
        UploadThread addressThread = new UploadThread(addressReportUrl,"UTF-8",addressParamMap);
        addressThread.start();
        // 上传一类危险源
        String hazardReportUrl = ResourceUtil.getConfigByName("hazardInfoReportUrl");
        String hazardReportContent = getHazardReportGroupContent();
        Map<String,String> hazardParamMap = new HashMap<String,String>();
        hazardParamMap.put("token", ciphertext);
        hazardParamMap.put("mineCode", mineCode);
        hazardParamMap.put("reportContent", hazardReportContent);
        UploadThread hazardThread = new UploadThread(hazardReportUrl,"UTF-8",hazardParamMap);
        hazardThread.start();
        // 上传风险点关联风险
        String addressDangerRelUrl = ResourceUtil.getConfigByName("addressDangerRelGroupUrl");
        String addressDangerRelContent = getAddressDangerRelGroupContent(ids);
        Map<String,String> addressDangerRelParamMap = new HashMap<String,String>();
        addressDangerRelParamMap.put("token", ciphertext);
        addressDangerRelParamMap.put("mineCode", mineCode);
        addressDangerRelParamMap.put("reportContent", addressDangerRelContent);
        UploadThread addressDangerRelThread = new UploadThread(addressDangerRelUrl,"UTF-8",addressDangerRelParamMap);
        addressDangerRelThread.start();
        // 上传作业活动
        String activityReportUrl = ResourceUtil.getConfigByName("activityInfoReportUrl");
        String activityReportContent = getActivityReportGroupContent();
        Map<String,String> activityParamMap = new HashMap<String,String>();
        activityParamMap.put("token", ciphertext);
        activityParamMap.put("mineCode", mineCode);
        activityParamMap.put("reportContent", activityReportContent);
        UploadThread activityThread = new UploadThread(activityReportUrl,"UTF-8",activityParamMap);
        activityThread.start();
        // 上传岗位
        String postReportUrl = ResourceUtil.getConfigByName("postInfoReportUrl");
        String postReportContent = getPostReportGroupContent();
        Map<String,String> postParamMap = new HashMap<String,String>();
        postParamMap.put("token", ciphertext);
        postParamMap.put("mineCode", mineCode);
        postParamMap.put("reportContent", postReportContent);
        UploadThread postThread = new UploadThread(postReportUrl,"UTF-8",postParamMap);
        postThread.start();
*/
        try {
            Map<String,String> paramMap = new HashMap<String,String>();
            paramMap.put("token", ciphertext);
            paramMap.put("mineCode", mineCode);
            paramMap.put("reportContent", reportContent);
            LogUtil.info("url=" + url);
            LogUtil.info("list.size()=" + list.size() + ",reportContent="+reportContent.length());
            result = HttpClientUtils.post(url, paramMap, "UTF-8");
            LogUtil.info("上报接口调用成功返回：" + result);
        }catch (NetServiceException e) {
            j.setMsg("网络问题,上报失败");
            LogUtil.error("上报集团失败,网络问题",e);
            return j;
        }

        //解析rpc返回的json
        try{
            if(result!=null){
                JSONObject resultJson = JSONHelper.jsonstr2json(result);
                String code = resultJson.optString("code");
                if(!code.equals(Constants.PLATFORM_RESULT_CODE_SUCCESS)){//请求成功
                    j.setMsg("上报失败");
                    LogUtil.error("上报集团失败,网络问题");
                    return j;
                } else {
                    //TODO 更新本地上报状态
                    //更新本地数据库
                    try {
                        String curUser;
                        try {
                            //定时任务无法获取session
                            curUser = ResourceUtil.getSessionUserName().getRealName();
                        } catch (Exception e) {
                            curUser = "定时任务";
                        }
                        String reportDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
                        if (StringUtil.isNotEmpty(ids.toString())) {
                            executeSql("update t_b_danger_source set report_group_man = '"+curUser+"' , report_group_time = '"+reportDate+"' , report_group_status = '1' where id in ('"+ids.toString().replace(",","','")+"')");
                        }
                    } catch (Exception e) {
                        j.setMsg("上报成功，但本地数据库操作失败");
                        LogUtil.error("上报集团失败,网络问题");
                        return j;
                    }
                }
            }else {
                j.setMsg("上报失败");
                LogUtil.error("上报集团失败,网络问题");
                return j;
            }
        }catch (Exception e){
            j.setMsg("上报失败");
            LogUtil.error("上报集团失败,网络问题");
            return j;
        }
        return j;
    }

    /**
     * 组装危险源上报集团报文
     * @return
     */
    private String getHazardReportGroupContent() {
        List<TbHazardManageEntity> hazardList = systemService.getList(TbHazardManageEntity.class);
        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < hazardList.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(hazardList.get(i).getId());
            TbHazardManageEntity entity = hazardList.get(i);
            Map<String,String> sendMap = new HashMap<>();

            sendMap.put("id",entity.getId());
            sendMap.put("hazardName",entity.getHazardName());
            sendMap.put("hazardType",entity.getHazardType());
            sendMap.put("damageType",entity.getDamageType());
            sendMap.put("professionType",entity.getProfessionType());
            sendMap.put("accidentType",entity.getAccidentType());
            sendMap.put("isDelete",entity.getIsDelete());
            sendList.add(sendMap);
        }

        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 组装风险点关联风险报文
     * @return
     */
    private String getAddressDangerRelGroupContent(StringBuffer idSb) {
        List<Object> sendList = new ArrayList<Object>();
        String[] idArray = idSb.toString().split(",");
        CriteriaQuery cq = new CriteriaQuery(TBDangerAddresstRelEntity.class);
        try{
            cq.in("dangerId",idArray);
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        List<TBDangerAddresstRelEntity> list = systemService.getListByCriteriaQuery(cq,false);
        if(list != null && !list.isEmpty()){
            for(TBDangerAddresstRelEntity entity : list){
                Map<String,String> sendMap = new HashMap<>();

                sendMap.put("id",entity.getId());
                sendMap.put("dangerId",entity.getDangerId());
                sendMap.put("addressId",entity.getAddressId());
                sendMap.put("riskLevel",entity.getRiskLevel());
                sendList.add(sendMap);
            }
        }
        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 组装风险点上报集团报文
     * @return
     */
    private String getAddressReportGroupContent() {
        List<TBAddressInfoEntity> addressList = systemService.getList(TBAddressInfoEntity.class);
        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < addressList.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(addressList.get(i).getId());
            TBAddressInfoEntity entity = addressList.get(i);
            Map<String,String> sendMap = new HashMap<>();

            sendMap.put("id",entity.getId());
            sendMap.put("address",entity.getAddress());
            sendMap.put("lon",entity.getLon());
            sendMap.put("lat",entity.getLat());
            sendMap.put("isshow",entity.getIsshow());
            sendMap.put("manageMan",entity.getManageMan());
            sendMap.put("isDelete",entity.getIsDelete());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sendMap.put("createDate",entity.getCreateDate()==null?"":sdf.format(entity.getCreateDate()));
            sendList.add(sendMap);
        }

        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 组装作业活动上报集团报文
     * @return
     */
    private String getActivityReportGroupContent() {
        List<TbActivityManageEntity> activityList = systemService.getList(TbActivityManageEntity.class);
        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < activityList.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(activityList.get(i).getId());
            TbActivityManageEntity entity = activityList.get(i);
            Map<String,String> sendMap = new HashMap<>();

            sendMap.put("id",entity.getId());
            sendMap.put("professionType",entity.getProfessionType());
            sendMap.put("activityName", entity.getActivityName());
            sendMap.put("isDelete",entity.getIsDelete());
            sendList.add(sendMap);
        }
        return JSONHelper.toJSONString(sendList);
    }

    /**
     * 组装岗位上报集团报文
     * @return
     */
    private String getPostReportGroupContent() {
        List<TBPostManageEntity> postList = systemService.getList(TBPostManageEntity.class);
        List<Object> sendList = new ArrayList<>();
        StringBuffer idSb = new StringBuffer();
        for(int i = 0 ; i < postList.size() ; i++) {
            if(StringUtils.isNotBlank(idSb.toString())){
                idSb.append(",");
            }
            idSb.append(postList.get(i).getId());
            TBPostManageEntity entity = postList.get(i);
            Map<String,String> sendMap = new HashMap<>();

            sendMap.put("id",entity.getId());
            sendMap.put("professionType",entity.getProfessionType());
            sendMap.put("postName",entity.getPostName());
            sendMap.put("isDelete",entity.getIsDelete());
            sendList.add(sendMap);
        }
        return JSONHelper.toJSONString(sendList);
    }

    @Override
    public void importDataSava(ExcelImportResult<TBDangerSourceEntity> result, String seId, String type) {
        Date yeRecognizeTime = new Date();
        List<TBDangerSourceEntity> TBDangerSourceEntityList = new ArrayList<>();
        for (int i = 0; i < result.getList().size(); i++) {
            TBDangerSourceEntity tBDangerSource = (TBDangerSourceEntity)result.getList().get(i);
            tBDangerSource.setYeRecognizeTime(yeRecognizeTime);
            tBDangerSource.setYeHazardCate(DicUtil.getTypeCodeByName("hazardCate", tBDangerSource.getYeHazardCate()));
            tBDangerSource.setYeRiskGrade(DicUtil.getTypeCodeByName("riskLevel", tBDangerSource.getYeRiskGrade()));
            tBDangerSource.setYeProfession(DicUtil.getTypeCodeByName("proCate_gradeControl", tBDangerSource.getYeProfession()));
            //tBDangerSource.setDamageType(DicUtil.getTypeCodeByName("danger_Category",tBDangerSource.getDamageType()));
            // 事故类型
            String yeAccident = tBDangerSource.getYeAccident();
            if(StringUtils.isNotBlank(yeAccident)){
                String[] sgs = yeAccident.split(",");
                StringBuffer sb = new StringBuffer();
                for(String sg : sgs){
                    String temp = DicUtil.getTypeCodeByName("accidentCate", sg);
                    if(StringUtils.isNotBlank(sb.toString())){
                        sb.append(",");
                    }
                    sb.append(temp);
                }
                tBDangerSource.setYeAccident(sb.toString());
            }
            //伤害类型
            String damageType = tBDangerSource.getDamageType();
            if(StringUtils.isNotBlank(damageType)){
                String[] sgs = damageType.split(",");
                StringBuffer sb = new StringBuffer();
                for(String sg : sgs){
                    String temp = DicUtil.getTypeCodeByName("danger_Category", sg);
                    if(StringUtils.isNotBlank(sb.toString())){
                        sb.append(",");
                    }
                    sb.append(temp);
                }
                tBDangerSource.setDamageType(sb.toString());
            }

            //隐患等级
            tBDangerSource.setHiddenLevel(DicUtil.getTypeCodeByName("hiddenLevel", tBDangerSource.getHiddenLevelTemp()));
            tBDangerSource.setIsMajor("0");
            /**
             * 计算LEC风险值
             * */
            if(tBDangerSource.getLecRiskPossibilityTemp() != null && tBDangerSource.getLecRiskLossTemp() != null && tBDangerSource.getLecExposureTemp() != null){
                double lecRiskValue = (tBDangerSource.getLecRiskPossibility()) * (tBDangerSource.getLecRiskLoss()) * (tBDangerSource.getLecExposure());
                tBDangerSource.setLecRiskValue(lecRiskValue);
                String majorLevelThreshold = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "重大风险阀值");
                String superiorLevelThreshold = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "较大风险阀值");
                String commonlyLevelThreshold = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC, "一般风险阀值");
                if(lecRiskValue>=Integer.parseInt(StringUtils.isNoneBlank(majorLevelThreshold)?majorLevelThreshold:"270")){
                    tBDangerSource.setIsMajor("1");
                    tBDangerSource.setYeRiskGrade(DicUtil.getTypeCodeByName("riskLevel","重大风险"));
                }else if(lecRiskValue>=Integer.parseInt(StringUtils.isNoneBlank(superiorLevelThreshold)?superiorLevelThreshold:"140")){
                    tBDangerSource.setYeRiskGrade(DicUtil.getTypeCodeByName("riskLevel","较大风险"));
                }else if(lecRiskValue>=Integer.parseInt(StringUtils.isNoneBlank(commonlyLevelThreshold)?commonlyLevelThreshold:"70")){
                    tBDangerSource.setYeRiskGrade(DicUtil.getTypeCodeByName("riskLevel","一般风险"));
                }else{
                    tBDangerSource.setYeRiskGrade(DicUtil.getTypeCodeByName("riskLevel","低风险"));
                }
            }

            /**
             * 计算风险值
             */
            if (tBDangerSource.getYeProbability() != null && tBDangerSource.getYeProbability() != "" && tBDangerSource.getYeCost() != null && tBDangerSource.getYeCost() != ""){
                int riskValue = Integer.parseInt(DicUtil.getTypeNameByCode("hazard_fxss", tBDangerSource.getYeCost())) * Integer.parseInt(DicUtil.getTypeNameByCode("probability", tBDangerSource.getYeProbability()));
                tBDangerSource.setRiskValue(String.valueOf(riskValue));
                String majorLevelThreshold = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "重大风险阀值");
                String superiorLevelThreshold = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "较大风险阀值");
                String commonlyLevelThreshold = DicUtil.getTypeCodeByName(Constants.THRESHOLD_DIC_HIDDENDANGER_LEVEL, "一般风险阀值");
                if(riskValue>=Integer.parseInt(StringUtils.isNoneBlank(majorLevelThreshold)?majorLevelThreshold:"25")){
                    tBDangerSource.setIsMajor("1");
                    tBDangerSource.setYeRiskGrade(DicUtil.getTypeCodeByName("riskLevel","重大风险"));
                }else if(riskValue>=Integer.parseInt(StringUtils.isNoneBlank(superiorLevelThreshold)?superiorLevelThreshold:"16")){
                    tBDangerSource.setYeRiskGrade(DicUtil.getTypeCodeByName("riskLevel","较大风险"));
                }else if(riskValue>=Integer.parseInt(StringUtils.isNoneBlank(commonlyLevelThreshold)?commonlyLevelThreshold:"8")){
                    tBDangerSource.setYeRiskGrade(DicUtil.getTypeCodeByName("riskLevel","一般风险"));
                }else{
                    tBDangerSource.setYeRiskGrade(DicUtil.getTypeCodeByName("riskLevel","低风险"));
                }
            }

            String postId = CommonUtil.getPostIdByName(tBDangerSource.getPostTemp());
            TBPostManageEntity post = systemService.getEntity(TBPostManageEntity.class,postId);
            if(post != null){
                tBDangerSource.setPost(post);
            }

            String activityId = CommonUtil.getActivityIdByName(tBDangerSource.getActivityTemp());
            TbActivityManageEntity activity = systemService.getEntity(TbActivityManageEntity.class,activityId);
            if(activity != null){
                tBDangerSource.setActivity(activity);
            }

            String hazardId = CommonUtil.getHazardIdByName(tBDangerSource.getHazardTemp());
            TbHazardManageEntity hazard = systemService.getEntity(TbHazardManageEntity.class,hazardId);
            if(hazard != null){
                tBDangerSource.setHazard(hazard);
            }

            if (Constants.TYPE_RISK_IMPORT_SPECIAL.equals(type) && StringUtil.isNotEmpty(seId)) {
                tBDangerSource.setOrigin(Constants.DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION);
            } else if (Constants.TYPE_RISK_IMPORT_DEPART_REPORT.equals(type)){
                tBDangerSource.setOrigin(Constants.DANGER_SOURCE_ORIGIN_MINE);
            }
            tBDangerSource.setAuditStatus(Constants.DANGER_SOURCE_AUDITSTATUS_TOREPORT);
            tBDangerSource.setReportStatus(Constants.DANGER_SOURCE_REPORT_UNREPORT);
            tBDangerSource.setId(UUIDGenerator.generate());
            TSUser sessionUser = ResourceUtil.getSessionUserName();
            tBDangerSource.setReportDepart(sessionUser.getCurrentDepart());
            tBDangerSource.setDepartReportTime(new Date());
            tBDangerSource.setDepartReportMan(sessionUser);
            tBDangerSource.setIsDelete(Constants.IS_DELETE_N);

            String addressCateNameTemp = tBDangerSource.getAddressCatetemp();
            //if(StringUtil.isNotEmpty(addressCateNameTemp) && addressCateNameTemp.indexOf(",|，|、")!=-1){
            if(StringUtil.isNotEmpty(addressCateNameTemp) && ((addressCateNameTemp.indexOf(",")!=-1) ||
                    (addressCateNameTemp.indexOf("，")!=-1) || (addressCateNameTemp.indexOf("、")!=-1))){
                String[] addressCateNameArr = addressCateNameTemp.split(",|，|、");
                for(int index=0;index<addressCateNameArr.length;index++){
                    String addressCateName = addressCateNameArr[index];
                    TBDangerSourceEntity dse = new TBDangerSourceEntity();
                    try{
                        MyBeanUtils.copyBean2Bean(dse,tBDangerSource);
                        dse.setId(UUIDGenerator.generate());
                    }catch(Exception e) {
                        e.printStackTrace();
                        throw new BusinessException(e.getMessage());
                    }
                    dse.setAddressCate(DicUtil.getTypeCodeByName("addressCate",addressCateName));
                    TBDangerSourceEntityList.add(dse);
                }
            } else {
                //systemService.save(tBDangerSource);
                tBDangerSource.setAddressCate(DicUtil.getTypeCodeByName("addressCate",tBDangerSource.getAddressCatetemp()));
                TBDangerSourceEntityList.add(tBDangerSource);
            }

        }
        systemService.batchSave(TBDangerSourceEntityList);

        List<String> idList = new ArrayList<>();
        if (Constants.TYPE_RISK_IMPORT_SPECIAL.equals(type) && StringUtil.isNotEmpty(seId)) {
            List<TBSeDsRelationEntity> relationEntitieList = new ArrayList<>();
            for (TBDangerSourceEntity tBDangerSource :TBDangerSourceEntityList) {
                idList.add(tBDangerSource.getId());
                //保存风险辨识评估和风险关系
                TBSeDsRelationEntity relationEntity = new TBSeDsRelationEntity();
                relationEntity.setDangerSourceId(tBDangerSource.getId());
                relationEntity.setSepcialEvaluationId(seId);
//                    systemService.save(relationEntity);
                relationEntitieList.add(relationEntity);
                systemService.addLog("专项风险辨识评估\"" + tBDangerSource.getId() + "\"导入成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
            }
            systemService.batchSave(relationEntitieList);
        } else {
            for (TBDangerSourceEntity tBDangerSource :TBDangerSourceEntityList) {
                idList.add(tBDangerSource.getId());
                systemService.addLog("危险源\"" + tBDangerSource.getId() + "\"导入成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
            }
        }

        if(!idList.isEmpty() && idList.size()>0){
            syncToCloudService.dangerSourceBatchReport(StringUtils.join(idList, ","));
        }
    }
}