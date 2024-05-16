package com.sdzk.buss.api.service.impl;

import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.riskmanage.entity.RiskManageHazardFactorEntity;
import com.sddb.buss.riskmanage.entity.RiskManageRelHd;
import com.sddb.buss.riskmanage.entity.RiskManageRelRisk;
import com.sddb.buss.riskmanage.entity.RiskManageTaskAllEntity;
import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.api.service.ApiServiceI;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.service.DataMapServiceI;
import com.sdzk.buss.web.common.utils.CommonUtil;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleStepEntity;
import com.sdzk.buss.web.quartz.QuartzJob;
import com.sdzk.buss.web.quartz.service.QrtzManagerServiceI;
import com.sdzk.buss.web.riskUpgrade.service.RiskUpgradeServiceI;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.MutiLangServiceI;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("apiService")
@Transactional
public class ApiServiceImpl implements ApiServiceI {
    @Autowired
    private UserService userService;
	@Autowired
	private SystemService systemService;
    @Resource(name="quartzScheduler")
    private Scheduler scheduler;
    @Autowired
    private QrtzManagerServiceI qrtzManagerServiceI;
    @Autowired
    private RiskUpgradeServiceI riskUpgradeService;
    @Autowired
    private DataMapServiceI dataMapService;
    private String sessionCache="sessionCache";

    @Autowired
    private MutiLangServiceI mutiLangService;

    /**
     * 获取token
     * */
    public String getLocalToken(){
        String localToken = null;
        localToken = ResourceUtil.getConfigByName("token");
        return localToken;
    }

    /**
     * 验证登录
     * */
    public ApiResultJson mobileLogin(TSUser user){
        TSUser u = userService.checkUserExits(user);
        if (u == null) {
            return new ApiResultJson(ApiResultJson.CODE_202,"用户名密码不正确",null);
        }
        //用户修改密码登录密码
        if("2".equals(u.getDeleteFlag().toString())){
            new ApiResultJson(ApiResultJson.CODE_202,"请登录pc端修改密码",null);
        }
        if(!Globals.Delete_Normal.equals(u.getDeleteFlag())){
            return new ApiResultJson(ApiResultJson.CODE_202,"该用户已被删除",null);
        }
        if (Globals.User_Forbidden.equals(u.getStatus())) {
            return new ApiResultJson(ApiResultJson.CODE_202,"该用户已被禁用",null);
        }
        //记录当前用户
        String sessionId = ContextHolderUtils.getSession().getId();
//        Client client = new Client();
//        client.setIp("");
//        client.setLogindatetime(new Date());
//        client.setUser(u);
//        ClientManager.getInstance().addClinet(sessionId, client);
        String userId = u.getId();
        String getAppFunctionsSql = "SELECT functioncode from t_s_app_function WHERE id in ( SELECT functionid FROM t_s_app_role_function WHERE roleid in (SELECT roleid FROM t_s_role_user WHERE userid='"+userId+"')) ORDER BY functioncode";
        List<String> appFunctionsList = systemService.findListbySql(getAppFunctionsSql);

        u.setCurrentDepart(u.getTSDepart());
        EhcacheUtil.put(sessionCache, sessionId, u);
        JSONObject object = new JSONObject();
        object.put("sessionId",sessionId);
        object.put("username",u.getRealName());
        object.put("userId",u.getId());
        object.put("functions",appFunctionsList);

        String message = null;
        message = mutiLangService.getLang("common.user") + ": " + user.getUserName() + "["+ u.getTSDepart().getDepartname() + "]" + mutiLangService.getLang("common.login.success");
        // 添加登陆日志
        systemService.addLogAppLogin(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO,userId);
        return new ApiResultJson(object);
    }

    @Override
    public ApiResultJson mobileLogout(String sessionId) {
        EhcacheUtil.remove(sessionCache,sessionId);
//        ClientManager.getInstance().removeClinet(sessionId);
        return new ApiResultJson();
    }

    /**
     * 修改密码
     * */
    public ApiResultJson changePassword(TSUser user, String newPassword){
        String password = user.getPassword();
        String username = user.getUserName();
        String pString = PasswordUtil.encrypt(username, password, PasswordUtil.getStaticSalt());

        List<TSUser> users=systemService.findByProperty(TSUser.class,"userName",username);
        if(users.size()>0 && users!=null){
            user = users.get(0);
            if (!pString.equals(user.getPassword())) {
                return new ApiResultJson(ApiResultJson.CODE_202,"原密码不正确",null);
            } else {
                try {
                    user.setPassword(PasswordUtil.encrypt(user.getUserName(), newPassword, PasswordUtil.getStaticSalt()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                systemService.updateEntitie(user);
                return new ApiResultJson("修改成功");
            }
        }else{
            return new ApiResultJson(ApiResultJson.CODE_202,"用户已被意外删除",null);
        }
    }


    /**
     * 同步数据字典
     * */
    @Override
    public List<Map<String, Object>> mobileSyncDataDic(){
        String syncGroupCode = "examType,workShift,hiddenCate,hiddenLevel,risk_type,handelStatus,violaterule_wzfl,vio_level,violaterule_wzdx,manag eType,major,factors_level,taskManageType,manageType";
        List<Map<String, Object>> typeList = systemService.findForJdbc("select t.typecode, t.typename, g.typegroupcode from t_s_typegroup g, t_s_type t where g.id=t.typegroupid and g.typegroupcode in ('"+syncGroupCode.replace(",","','")+"') order by t.typecode asc");
        return typeList;
    }

    @Override
    public List<Map<String, Object>> mobileSyncDataLocation() {
        return systemService.findForJdbc("select id, address from t_b_address_info where is_delete=0");
    }

    @Override
    public List<Map<String, Object>> mobileSyncDataPostName() {
        return systemService.findForJdbc("SELECT post_name from t_b_post_manage WHERE is_delete != '1'");
    }

    @Override
    public List<Map<String, Object>> mobileSyncDataRisk() {
        return systemService.findForJdbc("select id,IFNULL(address_id,'') address_id,risk_type,risk_desc from t_b_risk_identification where status = '3' and is_del = '0' ");
    }

    @Override
    public List<Map<String, Object>> mobileSyncDataRiskManage(TSUser user) {

        List<Map<String, Object>> result=null;
        String sql = "SELECT rm.id riskManageId, rm.risk_id riskId, rm.manage_type manageType,IFNULL(ai.address,'')  address,IFNULL(ai.id,'')  addressId,IFNULL(pm.post_name,'') postName, ri.risk_type riskType," +
                " ri.risk_desc riskDesc, ri.risk_level riskLevel, ri.manage_level manageLevel, ri.duty_manager dutyManager," +
                " DATE_FORMAT(ri.identifi_date , '%Y-%m-%d') identifiDate, DATE_FORMAT(ri.exp_date , '%Y-%m-%d') expDate,ri.identification_type identificationType FROM t_b_risk_manage rm " +
                "LEFT JOIN t_b_risk_identification ri ON rm.risk_id = ri.id " +
                "LEFT JOIN t_b_address_info ai ON ai.id = ri.address_id " +
                "LEFT JOIN t_b_post_manage pm ON pm.id = ri.post_id " +
                "WHERE rm.my_user_id = '"+user.getId()+"' ORDER BY rm.create_date DESC";
        result = systemService.findForJdbc(sql);
        if (result != null && result.size() > 0) {
            for (Map<String, Object> map: result) {
               // map.put("manageType", DicUtil.getTypeNameByCode("manageType", (String) map.get("manageType")));
                map.put("address", (String) map.get("address")+"||"+(String) map.get("addressId"));
                map.put("riskType", DicUtil.getTypeNameByCode("risk_type", (String) map.get("riskType"))+"||"+(String) map.get("riskType"));
                map.put("riskLevel", DicUtil.getTypeNameByCode("factors_level", (String) map.get("riskLevel")));
                map.put("manageLevel", DicUtil.getTypeNameByCode("identifi_mange_level", (String) map.get("manageLevel")).equals("")?(String) map.get("manageLevel"):DicUtil.getTypeNameByCode("identifi_mange_level", (String) map.get("manageLevel")));
                map.put("identificationType", DicUtil.getTypeNameByCode("identifi_from", (String) map.get("identificationType")));
                if(StringUtil.isNotEmpty((String) map.get("riskId"))){
                    RiskIdentificationEntity bean =systemService.getEntity(RiskIdentificationEntity.class,(String) map.get("riskId"));
                    List<RiskFactortsRel> relList = bean.getRelList();
                    map.put("hazardFactortsNum", relList.size() + "");
                }
            }
        }
        return result;
    }


    @Override
    public List<Map<String, Object>> mobileSyncDataRiskManageHazardFactor(TSUser user) {

        List<Map<String, Object>> result=null;
        String sql = "SELECT rfr.id id,rfr.risk_identification_id riskId, rfr.hazard_factors_id hazardFactorsId,hf.risk_type riskType, hf.major major,hf.post_name postName,hf.hazard_factors hazardFactors, rfr.hfManageMeasure hfManageMeasure, " +
                "rfr.hfLevel hfLevel, IFNULL(d.departname,'') manageDepartName,IFNULL(b.realname,'')  manageUserName FROM t_b_risk_factors_rel rfr " +
                "LEFT JOIN t_b_hazard_factors hf ON rfr.hazard_factors_id = hf.id " +
                "LEFT JOIN t_s_depart d ON rfr.manage_depart = d.id " +
                "LEFT JOIN t_s_base_user b ON rfr.manage_user = b.id " +
                "WHERE rfr.risk_identification_id IN ( SELECT risk_id FROM t_b_risk_manage WHERE my_user_id = '"+user.getId()+"' );";
        result = systemService.findForJdbc(sql);
        if (result != null && result.size() > 0) {
            for (Map<String, Object> map: result) {
                map.put("riskType", DicUtil.getTypeNameByCode("risk_type", (String) map.get("riskType")));
                map.put("major", DicUtil.getTypeNameByCode("major", (String) map.get("major")));
                map.put("hfLevel", DicUtil.getTypeNameByCode("factors_level", (String) map.get("hfLevel")));
            }
        }
        return result;
    }


    @Override
    public List<Map<String, Object>> mobileSyncDataRiskManageTask(TSUser user) {

        List<Map<String, Object>> result=null;
        String sql = "SELECT id taskAllId,DATE_FORMAT(create_date , '%Y-%m-%d') createDate, create_name crateName, manage_type manageType,DATE_FORMAT(manage_time , '%Y-%m-%d') manageTime,manage_shift manageShift," +
                "status, IFNULL(remark,'') remark  FROM t_b_risk_manage_task_all WHERE create_by = '"+user.getUserName()+"' and manage_type != 'post'";
        result = systemService.findForJdbc(sql);
        if (result != null && result.size() > 0) {
            for (Map<String, Object> map: result) {
                String id = String.valueOf(map.get("taskAllId"));
                RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.get(RiskManageTaskAllEntity.class,id);
                if(riskManageTaskAllEntity.getTaskAllManage()!=null){
                    TSUser tsUser = systemService.get(TSUser.class,riskManageTaskAllEntity.getTaskAllManage().getOrganizerMan());
                    map.put("crateName", tsUser.getRealName());
                }
                map.put("manageShift", DicUtil.getTypeNameByCode("workShift", (String) map.get("manageShift")));
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> mobileSyncDataRiskManageTaskRisk(TSUser user) {

        List<Map<String, Object>> result=null;
        String sql = "SELECT rmt.task_all_id taskAllId, rmt.id taskId,rmt.risk_manage_id riskManageId,rmt.risk_id riskId, rmt.manage_type manageType, " +
                "DATE_FORMAT( rmt.create_date, '%Y-%m-%d %h:%m:%s' ) createDate, IFNULL(ai.address, '') address, ai.id addressId, " +
                "IFNULL(pm.post_name, '') postName, ri.risk_type riskType, ri.risk_desc riskDesc, ri.risk_level riskLevel, " +
                "ri.manage_level manageLevel, ri.duty_manager dutyManager, DATE_FORMAT( ri.identifi_date, '%Y-%m-%d' ) identifiDate, " +
                "DATE_FORMAT(ri.exp_date, '%Y-%m-%d') expDate, ri.identification_type identificationType,rmt.handle_status handleStatus FROM t_b_risk_manage_task rmt " +
                "LEFT JOIN t_b_risk_identification ri ON rmt.risk_id = ri.id " +
                "LEFT JOIN t_b_address_info ai ON ri.address_id = ai.id " +
                "LEFT JOIN t_b_post_manage pm ON ri.post_id = pm.id "+
                "LEFT JOIN t_b_risk_manage_task_all rmta on rmta.id = rmt.task_all_id " +
                "where rmta.create_by = '"+user.getUserName()+"'";
        result = systemService.findForJdbc(sql);
        String numTotalSql = "SELECT count(0) count,risk_manage_task_id taskId  from t_b_risk_manage_hazard_factor GROUP BY risk_manage_task_id";
        List<Map<String,Object>> numTotalList = systemService.findForJdbc(numTotalSql.toString());
        Map<String,String> numTotalMap = new HashMap<>();
        for(Map<String,Object> map:numTotalList){
            String count = String.valueOf(map.get("count"));
            String taskId = String.valueOf(map.get("taskId"));
            numTotalMap.put(taskId,count);
        }
        String numFinishedSql = "SELECT count(0) count,risk_manage_task_id taskId from t_b_risk_manage_hazard_factor WHERE (impl_detail is not null or impl_detail !='') GROUP BY risk_manage_task_id;";
        List<Map<String,Object>> numFinishedList = systemService.findForJdbc(numFinishedSql.toString());
        Map<String,String> numFinishedMap = new HashMap<>();
        for(Map<String,Object> map:numFinishedList){
            String count = String.valueOf(map.get("count"));
            String taskId = String.valueOf(map.get("taskId"));
            numFinishedMap.put(taskId,count);
        }
        if (result != null && result.size() > 0) {
            for (Map<String, Object> map: result) {
                // map.put("manageType", DicUtil.getTypeNameByCode("manageType", (String) map.get("manageType")));
                map.put("address", (String) map.get("address")+"||"+(String) map.get("addressId"));
                map.put("riskType", DicUtil.getTypeNameByCode("risk_type", (String) map.get("riskType"))+"||"+(String) map.get("riskType"));
                map.put("riskLevel", DicUtil.getTypeNameByCode("factors_level", (String) map.get("riskLevel")));
                map.put("manageLevel", DicUtil.getTypeNameByCode("identifi_mange_level", (String) map.get("manageLevel")).equals("")?(String) map.get("manageLevel"):DicUtil.getTypeNameByCode("identifi_mange_level", (String) map.get("manageLevel")));
                map.put("identificationType", DicUtil.getTypeNameByCode("identifi_from", (String) map.get("identificationType")));
                if(StringUtil.isNotEmpty((String) map.get("taskId"))){
                    /*RiskManageTaskEntity bean =systemService.getEntity(RiskManageTaskEntity.class,(String) map.get("taskId"));
                    int numTotal = 0;
                    int numFinished = 0;
                    List<RiskManageHazardFactorEntity> riskManageHazardFactorEntityList = bean.getRiskManageHazardFactorEntityList();
                    if(null!=riskManageHazardFactorEntityList){
                        numTotal = riskManageHazardFactorEntityList.size();
                        for(int i=0;i<riskManageHazardFactorEntityList.size();i++){
                            if(StringUtil.isNotEmpty(riskManageHazardFactorEntityList.get(i).getImplDetail())){
                                numFinished ++;
                            }
                        }
                    }
                    map.put("hazardFactorNumFinished", numFinished + "");
                    map.put("hazardFactorNum", numTotal+"");*/
                    String taskId = String.valueOf(map.get("taskId"));
                    String numFinished = numFinishedMap.get(taskId);
                    if(StringUtil.isNotEmpty(numFinished)){
                        map.put("hazardFactorNumFinished", numFinished + "");
                    }else{
                        map.put("hazardFactorNumFinished", "0");
                    }
                    String numTotal = numTotalMap.get(taskId);
                    if(StringUtil.isNotEmpty(numTotal)){
                        map.put("hazardFactorNum", numTotal + "");
                    }else{
                        map.put("hazardFactorNum", "0");
                    }
                }
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> mobileSyncDataRiskManageTaskHazardFactor(TSUser user) {

        List<Map<String, Object>> result=null;
        String sql = "SELECT rmhf.id riskManageHazardFactorId, rmhf.risk_manage_task_id taskId,rmhf.risk_id riskId,rmhf.hazard_factor_id hazardFactorId,rmhf.manage_type manageType, hf.risk_type riskType, hf.major major, hf.hazard_factors, rfr.hfManageMeasure,rfr.hfLevel hfLevel," +
                " IFNULL(rmhf.impl_detail,'') implDeail,IFNULL(rmhf.remark,'') remark, rmhf.handle_status handleStatus FROM t_b_risk_manage_hazard_factor rmhf " +
                "LEFT JOIN t_b_risk_factors_rel rfr ON rfr.risk_identification_id = rmhf.risk_id AND rfr.hazard_factors_id = rmhf.hazard_factor_id " +
                "LEFT JOIN t_b_hazard_factors hf ON hf.id = rmhf.hazard_factor_id " +
                "LEFT JOIN t_b_risk_manage_task rmt ON rmhf.risk_manage_task_id = rmt.id " +
                "LEFT JOIN t_b_risk_manage_task_all rmta on rmta.id = rmt.task_all_id " +
                "WHERE rmta.create_by = '"+user.getUserName()+"' GROUP BY riskManageHazardFactorId";
        result = systemService.findForJdbc(sql);
        if (result != null && result.size() > 0) {
            for (Map<String, Object> map: result) {
                map.put("riskType", DicUtil.getTypeNameByCode("risk_type", (String) map.get("riskType")));
                map.put("major", DicUtil.getTypeNameByCode("major", (String) map.get("major")));
                map.put("hfLevel", DicUtil.getTypeNameByCode("factors_level", (String) map.get("hfLevel")));
                if(StringUtil.isNotEmpty((String) map.get("riskManageHazardFactorId"))){
                    RiskManageHazardFactorEntity bean =systemService.getEntity(RiskManageHazardFactorEntity.class,(String) map.get("riskManageHazardFactorId"));
                    int riskNum = 0;
                    List<RiskManageRelRisk> riskList = bean.getRiskList();
                    if(null!=riskList && riskList.size()>0){
                        riskNum = riskList.size();
                    }
                    map.put("riskCount", riskNum + "");

                    int hdNum = 0;
                    List<RiskManageRelHd> hdList = bean.getHdList();
                    if(null!=hdList && hdList.size()>0){
                        hdNum = hdList.size();
                    }
                    map.put("hdCount", hdNum + "");
                }
            }
        }
        return result;
    }



    @Override
    public List<Map<String, Object>> mobileSyncDataUser() {
        String longyun = ResourceUtil.getConfigByName("longyun");
        if(longyun.equals("true")){
            return systemService.findForJdbc("SELECT u.id id, realname, o.org_id departId FROM t_s_base_user u LEFT JOIN t_s_user_org o ON u.id = o.user_id WHERE delete_Flag = 0");
        }else{
            return systemService.findForJdbc("select id, realname,username from t_s_base_user where delete_Flag=0");
        }
    }

    @Override
    public List<Map<String, Object>> mobileSyncDataUserOrg() {
        return systemService.findForJdbc("SELECT u.id id, realname, o.org_id orgid FROM t_s_base_user u LEFT JOIN t_s_user_org o ON u.id = o.user_id WHERE delete_Flag = 0");
    }

    @Override
    public List<Map<String, Object>> mobileSyncDataDepart() {
        return  systemService.findForJdbc("select id, departname from t_s_depart where delete_Flag=0");
    }

    @Override
    public void rectify(TBHiddenDangerHandleEntity entity) {
        systemService.saveOrUpdate(entity);
        TBHiddenDangerExamEntity tBHiddenDangerExam = entity.getHiddenDanger();
        //删除隐患升级定时任务
        qrtzManagerServiceI.removeJob(scheduler,tBHiddenDangerExam.getId());
        riskUpgradeService.execute(tBHiddenDangerExam.getId());


        int handleStep = 0;
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleStepEntity.class);
        cq.eq("hiddenDanger.id",tBHiddenDangerExam.getId());
        cq.addOrder("handleStep", SortDirection.asc);
        cq.add();
        List<TBHiddenDangerHandleStepEntity> handleStepList = systemService.getListByCriteriaQuery(cq,false);
        if(!handleStepList.isEmpty() && handleStepList.size() > 0 ){
            handleStep = handleStepList.get((handleStepList.size()-1)).getHandleStep() + 1;

            TBHiddenDangerHandleStepEntity handleStepEntity = new TBHiddenDangerHandleStepEntity();
            if (Constants.HANDELSTATUS_ROLLBACK_REPORT.equals(entity.getHandlelStatus())) {
                handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
                handleStepEntity.setHandleStep(handleStep);
                handleStepEntity.setHandleDate(new Date());
                handleStepEntity.setHandleMan(entity.getUpdateName());
                handleStepEntity.setRemark(entity.getRollBackRemark());
                handleStepEntity.setHandleStatus(entity.getHandlelStatus());
            } else {
                handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
                handleStepEntity.setHandleStep(handleStep);
                handleStepEntity.setHandleDate(getHandleTime(entity.getModifyDate()));
                TSUser user = systemService.get(TSUser.class, entity.getModifyMan());
                handleStepEntity.setHandleMan(user!=null?user.getRealName():"");
                handleStepEntity.setHandleStatus(entity.getHandlelStatus());
            }
            systemService.save(handleStepEntity);
        }
    }

    private Date getHandleTime(Date date){
        String dateTime = DateUtils.date2Str(date, DateUtils.date_sdf) +" "+DateUtils.formatShortDateTime();
        return DateUtils.str2Date(dateTime, DateUtils.datetimeFormat);
    }

    @Override
    public void review(TBHiddenDangerHandleEntity entity, String limitDate) {
        systemService.saveOrUpdate(entity);
        TBHiddenDangerExamEntity tBHiddenDangerExam = entity.getHiddenDanger();
        tBHiddenDangerExam.setUpdateBy(entity.getUpdateBy());
        tBHiddenDangerExam.setUpdateDate(new Date());
        tBHiddenDangerExam.setUpdateName(entity.getUpdateName());
        if(Constants.HANDELSTATUS_ROLLBACK_REPORT.equals(entity.getHandlelStatus())){
            //复查不合格，退回整改 是否添加升级任务，待确定
            Date limit = DateUtils.str2Date(limitDate, DateUtils.date_sdf);
            tBHiddenDangerExam.setLimitDate(limit);
            systemService.saveOrUpdate(tBHiddenDangerExam);
            //TODO 添加升级任务
            String job_name = tBHiddenDangerExam.getId();
            Date limitDate2 = tBHiddenDangerExam.getLimitDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String limitStr = sdf.format(limitDate2);
            limitStr = limitStr + " 23:59:59";
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                limitDate2 = sdf.parse(limitStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(limitDate2);
            int year = calendar.get(calendar.YEAR);
            int month = calendar.get(calendar.MONTH) + 1;
            int day = calendar.get(calendar.DATE);
            int hour = calendar.get(calendar.HOUR_OF_DAY);
            int minute = calendar.get(calendar.MINUTE);
            int second = calendar.get(calendar.SECOND);

            StringBuffer sb = new StringBuffer();
            sb.append(second).append(" ").append(minute).append(" ").append(hour).append(" ").append(day).append(" ").append(month).append(" ").append("? ").append(year);
            riskUpgradeService.execute(tBHiddenDangerExam.getId());
            try{
            qrtzManagerServiceI.addJob(scheduler,tBHiddenDangerExam.getId(), job_name, QuartzJob.class, sb.toString());
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            //隐患等级降为初始等级
            TBHiddenDangerExamEntity hiddenDangerExamEntity = entity.getHiddenDanger();
            hiddenDangerExamEntity.setHiddenNature(hiddenDangerExamEntity.getHiddenNatureOriginal());
            systemService.saveOrUpdate(hiddenDangerExamEntity);
            riskUpgradeService.execute(tBHiddenDangerExam.getId());
        }

        int handleStep = 0;
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleStepEntity.class);
        cq.eq("hiddenDanger.id",tBHiddenDangerExam.getId());
        cq.addOrder("handleStep",SortDirection.asc);
        cq.add();
        List<TBHiddenDangerHandleStepEntity> handleStepList = systemService.getListByCriteriaQuery(cq,false);
        if(!handleStepList.isEmpty() && handleStepList.size() > 0 ){
            handleStep = handleStepList.get((handleStepList.size()-1)).getHandleStep() + 1;

            TBHiddenDangerHandleStepEntity handleStepEntity = new TBHiddenDangerHandleStepEntity();
            handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
            handleStepEntity.setHandleStep(handleStep);
            handleStepEntity.setHandleDate(getHandleTime(entity.getReviewDate()));
            TSUser user = systemService.get(TSUser.class, entity.getReviewMan());
            handleStepEntity.setHandleMan(user!=null?user.getRealName():"");
            handleStepEntity.setHandleStatus(entity.getHandlelStatus());
            handleStepEntity.setRemark(entity.getReviewReport());

            systemService.save(handleStepEntity);
        }

    }

    /**
     * 待办任务统计
     * */
    public ApiResultJson hiddenDangerTaskCount(TSUser user){


        //修改为管理员角色
        boolean isAdmin = false;
        CriteriaQuery cqru = new CriteriaQuery(TSRoleUser.class);
        try{
            cqru.eq("TSUser.id",user.getId());
        }catch(Exception e){
            e.printStackTrace();
        }
        cqru.add();
        List<TSRoleUser> roleList = systemService.getListByCriteriaQuery(cqru,false);
        if(roleList != null && !roleList.isEmpty()){
            for(TSRoleUser ru : roleList){
                TSRole role = ru.getTSRole();
                if(role != null && role.getRoleName().equals("管理员")){
                    isAdmin = true;
                    break;
                }
            }
        }
        boolean isAJY= false;
        if(roleList != null && !roleList.isEmpty()){
            for(TSRoleUser ru : roleList){
                TSRole role = ru.getTSRole();
                if(role != null && role.getRoleName().equals("安监员")){
                    isAJY = true;
                    break;
                }
            }
        }



        JSONObject object = new JSONObject();
        object.put("unRectify","0");
        object.put("unReview","0");
        List<TSUserOrg> uoList = systemService.findByProperty(TSUserOrg.class, "tsUser", user);
        TSDepart dept = null;
        if(null!=uoList && uoList.size()>0) {
            dept = uoList.get(0).getTsDepart();
            String unRectifyStr = "select count(*) from t_b_hidden_danger_exam e, t_b_hidden_danger_handle h\n" +
                    "where e.id=h.hidden_danger_id and h.handlel_status in ('" + Constants.HANDELSTATUS_REPORT + "','" + Constants.HANDELSTATUS_ROLLBACK_CHECK + "')";
            if(!isAdmin){
                //unRectifyStr = unRectifyStr + " and e.duty_unit='" + user.getDepartid() + "'";
                unRectifyStr = unRectifyStr + " and (e.duty_unit='" + dept.getId() + "' or e.duty_man = '"+user.getRealName()+"')";
            }
            Long unRectifyCount = systemService.getCountForJdbc(unRectifyStr);
            object.put("unRectify",unRectifyCount+"");

            String unReviewStr = "select count(*) from t_b_hidden_danger_exam e, t_b_hidden_danger_handle h\n" +
                    "where e.id=h.hidden_danger_id and h.handlel_status in ('" + Constants.HANDELSTATUS_REVIEW + "')";
            if(!isAdmin&&!isAJY){
                //unReviewStr = unReviewStr + " and e.duty_unit='" + user.getDepartid() + "'";
                unReviewStr = unReviewStr +"and (EXISTS (\t\n" +
                        "\t\tselect * from t_s_base_user u, t_s_user_org uo\n" +
                        "\t\twhere uo.user_id=u.id and uo.org_id='"+ dept.getId() + "'\n" +
                        "\t\tand (u.username=e.create_by or FIND_IN_SET(u.id,e.fill_card_manids))\n" +
                        "\t) or e.manage_duty_unit = '" + dept.getId() + "' or e.manage_duty_man_id = '" + user.getId() + "')";
            }

            List<String> unReviewList = systemService.findListbySql(unReviewStr);
            if(null!=unReviewList && unReviewList.size()>0){
                object.put("unReview",unReviewList.get(0));
            }

        }
        return new ApiResultJson(object);
    }


    /**
     *  获取集团推送的隐患
     *  这个方法处理的业务情景基本上都是一条一条的数据，很少出现大量数据并发的情况。但是为了防止以后可能出现大规模数据推送使用这个方法，对其进行优化，
     *  但是考虑到需要反馈哪些数据保存成功了，所以需要逐条保存；
     * */
    public JSONArray pushedHiddenDangerReceive(String mineCode, String reportContent){
        Map<String, String> userMap = dataMapService.getUserMap();
        Map<String, String> departMap = dataMapService.getDepartMap();
        //返回保存成功的报文
        JSONArray retJa = new JSONArray();
        //TODO token校验
        JSONArray ja = JSONHelper.toJSONArray(reportContent);
        if (ja != null && ja.size() > 0) {
            try {
                for (int i = 0; i < ja.size(); i++) {
                    JSONObject jhandle = ja.optJSONObject(i);
                    if (jhandle == null) {
                        continue;
                    }
                    JSONObject jexam = (JSONObject) jhandle.get("object");
                    TBHiddenDangerHandleEntity handle = new TBHiddenDangerHandleEntity();
                    TBHiddenDangerExamEntity exam = new TBHiddenDangerExamEntity();

                    /** 分割 exam 数据 */
                    /**来源集团的数据设置origin='2'，来源集团*/
                    exam.setOrigin("2");
                    exam.setId(CommonUtil.cutMineCode(jexam.getString("id")));
                    exam.setExamDate(DateUtils.stringToDate(jexam.getString("examDate")));
                    exam.setShift(jexam.getString("shift"));
                    TBAddressInfoEntity addressInfoEntity = new TBAddressInfoEntity();
                    addressInfoEntity.setId(CommonUtil.cutMineCode(jexam.getString("address")));
                    exam.setAddress(addressInfoEntity);
                    TSDepart depart = new TSDepart();
                    depart.setId(jexam.getString("dutyUnit"));
                    exam.setDutyUnit(depart);
                    exam.setDutyMan(StringUtil.isNotEmpty(userMap.get(jexam.getString("dutyMan"))) ? userMap.get(jexam.getString("dutyMan")) : jexam.getString("dutyMan"));
                    exam.setProblemDesc(jexam.getString("problemDesc"));
                    exam.setHiddenCategory(jexam.getString("hiddenCategory"));
                    exam.setHiddenNature(jexam.getString("hiddenNature"));
                    exam.setHiddenNatureOriginal(jexam.getString("hiddenNatureOriginal"));
                    exam.setBeginWellDate(DateUtils.stringToDate(jexam.getString("beginWellDate")));
                    exam.setEndWellDate(DateUtils.stringToDate(jexam.getString("endWellDate")));
                    exam.setDealType(jexam.getString("dealType"));
                    exam.setLimitDate(DateUtils.stringToDate(jexam.getString("limitDate")));
                    exam.setLimitShift(jexam.getString("limitShift"));
                    exam.setHiddenType(jexam.getString("hiddenType"));
                    /**集团推送过来的数据默认是隐患**/
                    exam.setExamType("hy");
                    exam.setRiskType(jexam.getString("riskType"));
                    exam.setFillCardManId(jexam.getString("fillCardMan"));
                    exam.setManageType(jexam.getString("manageType"));
                    RiskIdentificationEntity risk = systemService.getEntity(RiskIdentificationEntity.class,CommonUtil.cutMineCode(jexam.getString("riskId")));
                    exam.setRiskId(risk);

                    exam.setSjjcDept(jexam.getString("sjjcDept"));
                    exam.setSjjcCheckMan(jexam.getString("sjjcCheckMan"));
                    exam.setProType(jexam.getString("proType"));
                    /**集团推送过来的数据一定是限期整改的**/
                    exam.setCheckType("1");
                    TSUser user = new TSUser();     user.setId(jexam.getString("reviewMan"));       exam.setReviewMan(user);
                    exam.setRemark(jexam.getString("remark"));
                    exam.setKeyWords(jexam.getString("keyWords"));
                    exam.setCreateDate(DateUtils.stringToDate(jexam.getString("createDate")));
                    exam.setCreateName(jexam.getString("createName"));

                    /** 分割 handle 数据 */
                    handle.setHiddenDanger(exam);
                    handle.setId(CommonUtil.cutMineCode(jhandle.getString("id")));
                    handle.setHandlelStatus(jhandle.getString("handlelStatus"));
                    handle.setReportStatus("1");

                    if(StringUtils.isNotBlank(exam.getId()) && StringUtils.isNotBlank(handle.getId())){
                        //删除现有记录
                        systemService.executeSql("delete from t_b_hidden_danger_handle where id='" +handle.getId()+"'");
                        systemService.executeSql("delete from t_b_hidden_danger_exam where id='" +exam.getId()+"'");
                        //保存隐患记录

                        saveObject(exam);
                        saveObject(handle);
//                        systemService.save(exam);
//                        systemService.save(handle);
                        //组装反馈报文，哪些成功了哪些失败了
                        JSONObject retJo = new JSONObject();
                        retJo.put("id", jexam.getString("id"));
                        //retJo.put("success", true);       //这里只反馈上报成功的id
                        retJa.add(retJo);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return retJa;
    }

    @Override
    public List<Map<String, Object>> mobileSyncDataHiddenTask(TSUser user) {
        String hiddenTaskSql = " SELECT\n" +
                "\tid,DATE_FORMAT(create_date,'%Y-%m-%d') createDate,create_name createName,manage_type manageType,manage_type manageTypeId,DATE_FORMAT(manage_time,'%Y-%m-%d') manageTime, " +
//                " manage_shift manageShift,remark,case when manage_type in ('comprehensive','profession','team','group','post') then '0' else '1' end as isExport" +
                " manage_shift manageShift,remark, '0' as isExport" +
                "  ,task_all_manage_id endTask,status \n" +
                "FROM\n" +
                "\tt_b_risk_manage_task_all\n" +
                "WHERE\n" +
                "\t`status` = '0' " +
                " and manage_type in ('glryrcjc','ajyrcjc','sjldjc','klddbjc') \n" + //因为app隐患排查我的任务，没做风险相关功能，所以只查这几个管控类型下的数据
                "AND create_by = '"+user.getUserName()+"' ";
        List<Map<String, Object>> result = systemService.findForJdbc(hiddenTaskSql);
        for (Map<String, Object> map:result) {
            Object manageType = map.get("manageType");
            Object manageShift = map.get("manageShift");
            Object endTask = map.get("endTask");
            if (null!=manageType&&StringUtils.isNotBlank(manageType.toString())){
                map.put("manageType",DicUtil.getTypeNameByCode("manageType", map.get("manageType").toString()));
            }
            if (null!=manageShift&&StringUtils.isNotBlank(manageShift.toString())){
                map.put("manageShift",DicUtil.getTypeNameByCode("workShift", map.get("manageShift").toString()));
            }
            if (null!=endTask&&StringUtils.isNotBlank(endTask.toString())){
                map.put("endTask","1");
            }else {
                map.put("endTask","0");
            }
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> mobileSyncDataNotUploadHiddenTask(TSUser user,String examIds) {
        String hiddenSql = " SELECT\n" +
                "\tt1.id,task_all_id taskId,\n" +
                "\tshift hiddenShift,\n" +
                "\tt2.id hiddenAddress,\n" +
                "\tt1.duty_unit dutyUnit,\n" +
                "\tt1.duty_man_id dutyMan,\n" +
                "\tt1.review_man reviewMan,\n" +
                "\tt1.hidden_nature hiddenLevel,\n" +
                "\tt1.risk_type  hiddenType,\n" +
                "\tt1.problem_desc problemFactDesc,\n" +
                "\tt3.rect_measures modifyMeasure,\n" +
                "\tt3.review_report reviewDetail,\n" +
                "\tt1.manage_type infoSource,\n" +
                "\tt1.deal_type dealType,\n" +
                "\t'' as infoSourceName,\n" +
                "\tt4.departname as dutyUnitName,\n" +
                "\tDATE_FORMAT(t1.exam_date,'%Y-%m-%d') checkDate,\n" +
                "\tDATE_FORMAT(t1.limit_date,'%Y-%m-%d') limitDate,\n" +
                "\tt1.fill_card_manids checkMan,\n" +
                "\t'' as checkManName,\n" +
                "\tt1.risk_id riskDesc,\n" +
                "\tt1.manage_duty_man_id controlDutyMan,\n" +
                "\t'' as controlDutyManName,\n" +
                "\t'' as ccontrolDutyUnitName,\n" +
                "\tt1.manage_duty_unit controlDutyUnit\n" +
                "FROM\n" +
                "\tt_b_hidden_danger_exam t1\n" +
                "LEFT JOIN t_b_address_info t2 ON t1.address = t2.id\n" +
                "left join t_b_hidden_danger_handle t3 on t1.id = t3.hidden_danger_id   " +
                "left join t_s_depart t4 on t1.duty_unit = t4.id\n" +
                " where t1.task_all_id in ("+examIds+")" +
                "   and t2.isShowData = '1'\n" +
                "and t3.handlel_status = '00'\n" +
                "and t1.exam_type = 'yh' ";
        boolean isAdmin = false;
        CriteriaQuery cqru = new CriteriaQuery(TSRoleUser.class);
        try{
            cqru.eq("TSUser.id",user.getId());
        }catch(Exception e){
            e.printStackTrace();
        }
        cqru.add();
        List<TSRoleUser> roleList = systemService.getListByCriteriaQuery(cqru,false);
        if(roleList != null && !roleList.isEmpty()){
            for(TSRoleUser ru : roleList){
                TSRole role = ru.getTSRole();
                if(role != null && role.getRoleName().equals("管理员")){
                    isAdmin = true;
                    break;
                }
            }
        }

        if(!isAdmin) {
            hiddenSql+= " and t1.create_by = '"+user.getUserName()+"' ";
        }
        List<Map<String, Object>> result = systemService.findForJdbc(hiddenSql);
        return result;
    }

    private void saveObject(Object object) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class objClass = object.getClass();
        Table table = (Table) objClass.getAnnotation(Table.class);
        Method[] objMethod = objClass.getMethods();
        StringBuffer fields = new StringBuffer();
        StringBuffer placeholder = new StringBuffer();
        List<Object> param = new ArrayList<>();
        for (Method method : objMethod) {
            Column column = method.getAnnotation(Column.class);
            JoinColumn joinColumn = method.getAnnotation(JoinColumn.class);
            Object value;
            if (method.getName().startsWith("get") && column!=null &&  (value = method.invoke(object)) != null) {
                if (StringUtil.isNotEmpty(fields.toString())) {
                    fields.append(",");
                    placeholder.append(",");
                }
                fields.append(column.name());
                placeholder.append("?");
                param.add(value);
            } else if (method.getName().startsWith("get") && joinColumn!=null &&  (value = method.invoke(object)) != null) {
                if (StringUtil.isNotEmpty(fields.toString())) {
                    fields.append(",");
                    placeholder.append(",");
                }
                fields.append(joinColumn.name());
                placeholder.append("?");
                param.add(value.getClass().getMethod("getId").invoke(value));
            }
        }
        if (StringUtil.isNotEmpty(fields.toString())) {
            systemService.executeSql("insert into "+table.name()+" ("+fields.toString()+") VALUES ("+placeholder.toString()+")", param.toArray());
        }
    }


    /**
     *  去掉集团版中的id类字段的mineCode
     * */
    public String subMineCode(String mineCode, String str){
        String returnStr = "";
        if (StringUtil.isNotEmpty(str) && str.length()>mineCode.length()){
            returnStr = str.substring(mineCode.length());
        }
        return returnStr;
    }

}