package com.sdzk.buss.web.common.task;

import com.sddb.buss.identification.service.RiskFactortsRelServiceI;
import com.sddb.buss.identification.service.RiskIdentificationServiceI;
import com.sddb.buss.riskdata.service.HazardFactorsServiceI;
import com.sdzk.buss.web.address.service.TBAddressInfoServiceI;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.dangersource.service.TBDangerSourceServiceI;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerHandleServiceI;
import com.sdzk.buss.web.investigateplan.service.UploadInvestigationPlan;
import com.sdzk.buss.web.layer.service.TBLayerServiceI;
import com.sdzk.buss.web.majorhiddendanger.service.MajorDangerReportRPC;
import com.sdzk.buss.web.specialevaluation.entity.TBSpecialEvaluationEntity;
import com.sdzk.buss.web.specialevaluation.service.SpecialEvaluationRPC;
import com.sdzk.buss.web.tbpostmanage.service.TBPostManageServiceI;
import com.sdzk.buss.web.violations.service.TBThreeViolationsServiceI;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 定时上报到集团
 * 上报风险时，基础数据连带上报，因此不添加几个基础数据的定时上报方法
 */
@Service("reportGroupTask")
public class ReportGroupTask {

    @Autowired
    private SystemService systemService;
    @Autowired
    private TBDangerSourceServiceI tbDangerSourceService;
    @Autowired
    private SpecialEvaluationRPC specialEvaluationRPC;
    @Autowired
    private TBHiddenDangerHandleServiceI tbHiddenDangerHandleService;
    @Autowired
    private MajorDangerReportRPC majorDangerReportRPC;
    @Autowired
    private TBThreeViolationsServiceI tbThreeViolationsService;
    @Autowired
    private UploadInvestigationPlan uploadInvestigationPlan;
    @Autowired
    private TBPostManageServiceI tBPostManageService;
    @Autowired
    private HazardFactorsServiceI hazardFactorsServiceI;
    @Autowired
    private RiskIdentificationServiceI riskIdentificationServiceI;
    @Autowired
    private RiskFactortsRelServiceI riskFactortsRelServiceI;
    @Autowired
    private TBAddressInfoServiceI tbAddressInfoServiceI;
    @Autowired
    private TBLayerServiceI tbLayerServiceI;

    public void reportYearRisk(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报年度风险开始===================");
        try {
            String sql = "select id from t_b_danger_source ds where ds.origin='"+Constants.DANGER_SOURCE_ORIGIN_MINE+"' and (ds.report_group_status='0' or ISNULL(ds.report_group_status) or ds.update_date>=report_group_time)";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                tbDangerSourceService.reportYearRiskExToGroup(idList);
                int step = 100;
                int times = (idList.size()-1)/step;
                for(int i=0;i<=times;i++){
                    int startIndex = i*step;
                    int endIndex = (i+1)*step > idList.size()?idList.size(): (i+1)*step;

                    CriteriaQuery cq = new CriteriaQuery(TBDangerSourceEntity.class);
                    cq.in("id", idList.subList(startIndex,endIndex).toArray());
                    cq.add();
                    List<TBDangerSourceEntity> list = systemService.getListByCriteriaQuery(cq,false);
                    if(!list.isEmpty() && list.size()>0){
                        count = count + (endIndex-startIndex);
                        tbDangerSourceService.reportYearRiskToGroup(list);
                    }
                }
            }
        } catch (Exception e) {
            count = 0;
            org.jeecgframework.core.util.LogUtil.error("定时上报年度风险失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报年度风险结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报年度风险总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }

    public void reportSpecialEvaluation(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报专项风险开始===================");
        try {
            String sql = "select id from t_b_special_evaluation ds where ds.report_group_status='0' or ISNULL(ds.report_group_status) or ds.update_date>=report_group_time";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                CriteriaQuery cq = new CriteriaQuery(TBSpecialEvaluationEntity.class);
                cq.in("id", idList.toArray());
                cq.add();
                List<TBSpecialEvaluationEntity> list = systemService.getListByCriteriaQuery(cq,false);
                if(!list.isEmpty() && list.size()>0){
                    count = idList.size();
                    specialEvaluationRPC.specialEvaluateReportToGroup(list);
                }
            }
        } catch (Exception e) {
            count = 0;
            org.jeecgframework.core.util.LogUtil.error("定时上报专项风险失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报专项风险结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报专项风险总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }

    public void reportHiddenDanger(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报日常隐患开始===================");
        try {
            String sql = "select id from t_b_hidden_danger_handle ds where ds.report_group_status='0' or ISNULL(ds.report_group_status) or ds.report_group_status='' or ds.update_date>=report_group_time";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                int step = 100;
                int times = (idList.size()-1)/step;
                for(int i=0;i<=times;i++){
                    String ids = "";
                    int startIndex = i*step;
                    int endIndex = (i+1)*step > idList.size()?idList.size(): (i+1)*step;
                    for(int k=startIndex;k<endIndex;k++){
                        if(ids.length()>0){
                            ids += ",";
                        }
                        ids += idList.get(k);
                    }

                    count = count + (endIndex-startIndex);
                    tbHiddenDangerHandleService.hiddenDangerReportToGroup(ids);
                }
            }
        } catch (Exception e) {
            count = 0;
            org.jeecgframework.core.util.LogUtil.error("定时上报日常隐患失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报日常隐患结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报日常隐患总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }

    public void reportLoginCount(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报登录统计开始===================");
        try {
            String querySql = "";
            StringBuffer qp = new StringBuffer(" where 1=1 and bsuser.status = 1");

            querySql = "select id,userid, operatetime from t_s_log where operatetype='1'";

            StringBuffer sb = new StringBuffer();
            sb.append("select bsuser.id userid,t.id id,t.operatetime operatetime from t_s_base_user bsuser  LEFT JOIN ( ");
            sb.append(querySql);
            sb.append(" ) t on bsuser.id = t.userid  ");
            sb.append(qp.toString());
            sb.append(" ORDER BY operatetime desc ");
            List<Map<String, Object>> maplist = systemService.findForJdbc(sb.toString());
            if(maplist!=null&&maplist.size()>0){
                for (Map map : maplist) {

                    // 根据单位ID获取单位名称
                    if(StringUtil.isNotEmpty(map.get("userid"))){
                        TSUser t = systemService.getEntity(TSUser.class,String.valueOf(map.get("userid")));
                        map.put("username",t.getUserName());
                        map.put("realname",t.getRealName());
                    }
                    String mineCode = ResourceUtil.getConfigByName("mine_code");
                    map.put("minecode",mineCode);
                }
                int step = 100;
                int times = (maplist.size()-1)/step;
                for(int i=0;i<=times;i++){
                    List<Map<String, Object>>  ids = new ArrayList<>();
                    int startIndex = i*step;
                    int endIndex = (i+1)*step > maplist.size()?maplist.size(): (i+1)*step;
                    for(int k=startIndex;k<endIndex;k++){
                        ids.add(maplist.get(k));
                    }
                    count = count + (endIndex-startIndex);
                    tbHiddenDangerHandleService.loginCountReportToGroup(ids);
                }
            }
        } catch (Exception e) {
            count = 0;
            org.jeecgframework.core.util.LogUtil.error("定时上报登录统计失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报登录统计结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报登录统计总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }

    public void reportMajorHiddenDanger(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报重大隐患开始===================");
        try {
            String sql = "select id from t_b_major_hidden_danger ds where ds.report_group_status='0' or ISNULL(ds.report_group_status) or ds.update_date>=report_group_time";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                String ids = "";
                for(String id:idList){
                    if(ids.length()>0){
                        ids += ",";
                    }
                    ids += id;
                }
                count = idList.size();
                majorDangerReportRPC.majorDangerReportToGroup(ids);
            }
        } catch (Exception e) {
            count = 0;
            org.jeecgframework.core.util.LogUtil.error("定时上报重大隐患失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报重大隐患结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报重大隐患总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }

    public void reportMajorYearRisk(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报重大风险开始===================");
        try {
            String sql = "select id from t_b_danger_source ds where ds.origin!='"+Constants.DANGER_SOURCE_ORIGIN_NOMAL+"' and ds.is_delete='0' and ds.ismajor='1'";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                String ids = "";
                for(String id:idList){
                    if(ids.length()>0){
                        ids += ",";
                    }
                    ids += id;
                }
                count = idList.size();
                tbDangerSourceService.reportMajorYearRiskToGroup(ids);
            }
        } catch (Exception e) {
            count = 0;
            org.jeecgframework.core.util.LogUtil.error("定时上报重大风险失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报重大风险结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报重大风险总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }

    public void reportThreeViolations(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报三违数据开始===================");
        try {
            String sql = "select id from t_b_three_violations ds where ds.report_group_status='0' or ISNULL(ds.report_group_status) or ds.report_group_status='' or ds.update_date>=report_group_time";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                String ids = "";
                for(String id:idList){
                    if(ids.length()>0){
                        ids += ",";
                    }
                    ids += id;
                }
                count = idList.size();
                tbThreeViolationsService.reportViolationToGroup(ids,true);
            }
        } catch (Exception e) {
            count = 0;
            org.jeecgframework.core.util.LogUtil.error("定时上报三违数据失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报三违数据结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报三违数据总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }

    public void reportInvestigatePlan(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报排查计划开始===================");
        try {
            String sql = "select id from t_b_investigate_plan ds where ds.report_group_status='0' or ISNULL(ds.report_group_status) or ds.update_date>=report_group_time";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                String ids = "";
                for(String id:idList){
                    if(ids.length()>0){
                        ids += ",";
                    }
                    ids += id;
                }
                count = idList.size();
                uploadInvestigationPlan.uploadInvestigationPlan(ids);
            }
        } catch (Exception e) {
            count = 0;
            org.jeecgframework.core.util.LogUtil.error("定时上报排查计划失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报排查计划结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报排查计划总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }

    public void reportPost(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报岗位开始===================");
        try {
            String sql = "select id from t_b_post_manage where report_group_status='0' or ISNULL(report_group_status) or report_group_status='' or update_date>=report_group_time";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                int step = 100;
                int times = (idList.size()-1)/step;
                for(int i=0;i<=times;i++){
                    String ids = "";
                    int startIndex = i*step;
                    int endIndex = (i+1)*step > idList.size()?idList.size(): (i+1)*step;
                    for(int k=startIndex;k<endIndex;k++){
                        if(ids.length()>0){
                            ids += ",";
                        }
                        ids += idList.get(k);
                    }

                    count = count + (endIndex-startIndex);
                    tBPostManageService.tBPostManageReportToGroup(ids);
                }
            }
        } catch (Exception e) {
            count = 0;
            org.jeecgframework.core.util.LogUtil.error("定时上报岗位失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报岗位结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报岗位总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }

    public void reportHazardFactors(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报危害因素开始===================");
        try {
            String sql = "select id from t_b_hazard_factors where status = '3' and (report_group_status='0' or ISNULL(report_group_status) or report_group_status='' or update_date>=report_group_time)";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                int step = 100;
                int times = (idList.size()-1)/step;
                for(int i=0;i<=times;i++){
                    String ids = "";
                    int startIndex = i*step;
                    int endIndex = (i+1)*step > idList.size()?idList.size(): (i+1)*step;
                    for(int k=startIndex;k<endIndex;k++){
                        if(ids.length()>0){
                            ids += ",";
                        }
                        ids += idList.get(k);
                    }

                    count = count + (endIndex-startIndex);
                    hazardFactorsServiceI.hazardFactorsReportToGroup(ids);
                }
            }
        } catch (Exception e) {
            count = 0;
            org.jeecgframework.core.util.LogUtil.error("定时上报危害因素失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报危害因素结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报危害因素总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }


    public void reportRiskIdentification(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报风险开始===================");
        try {
            String sql = "SELECT id FROM t_b_risk_identification WHERE status = '3' AND identification_type != '4' and (report_group_status='0' or ISNULL(report_group_status) or report_group_status = '' or update_date>=report_group_time)";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                int step = 100;
                int times = (idList.size()-1)/step;
                for(int i=0;i<=times;i++){
                    String ids = "";
                    int startIndex = i*step;
                    int endIndex = (i+1)*step > idList.size()?idList.size(): (i+1)*step;
                    for(int k=startIndex;k<endIndex;k++){
                        if(ids.length()>0){
                            ids += ",";
                        }
                        ids += idList.get(k);
                    }

                    count = count + (endIndex-startIndex);
                    riskIdentificationServiceI.riskIdentificationReportToGroup(ids);
                }
            }
        } catch (Exception e) {
            count = 0;
            org.jeecgframework.core.util.LogUtil.error("定时上报风险失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报风险结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报风险总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }


    public void reportRiskFactortsRel(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报风险和危害因素关联关系开始===================");
        try {
            String sql = "SELECT id FROM t_b_risk_factors_rel WHERE report_group_status='0' or ISNULL(report_group_status)  or report_group_status = '' or update_date>=report_group_time";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                int step = 100;
                int times = (idList.size()-1)/step;
                for(int i=0;i<=times;i++){
                    String ids = "";
                    int startIndex = i*step;
                    int endIndex = (i+1)*step > idList.size()?idList.size(): (i+1)*step;
                    for(int k=startIndex;k<endIndex;k++){
                        if(ids.length()>0){
                            ids += ",";
                        }
                        ids += idList.get(k);
                    }

                    count = count + (endIndex-startIndex);
                    riskFactortsRelServiceI.riskFactortsRelReportToGroup(ids);
                }
            }
        } catch (Exception e) {
            count = 0;
            org.jeecgframework.core.util.LogUtil.error("定时上报风险和危害因素关联关系失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报风险和危害因素关联关系结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报风险和危害因素关联关系总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }

    public void reportTBAddressInfo(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报地点开始===================");
        try {
            String sql = "SELECT id FROM t_b_address_info WHERE report_group_status='0' or ISNULL(report_group_status)  or report_group_status = '' or update_date>=report_group_time";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                int step = 100;
                int times = (idList.size()-1)/step;
                for(int i=0;i<=times;i++){
                    String ids = "";
                    int startIndex = i*step;
                    int endIndex = (i+1)*step > idList.size()?idList.size(): (i+1)*step;
                    for(int k=startIndex;k<endIndex;k++){
                        if(ids.length()>0){
                            ids += ",";
                        }
                        ids += idList.get(k);
                    }

                    count = count + (endIndex-startIndex);
                    tbAddressInfoServiceI.tBAddressInfoReportToGroup(ids);
                }
            }
        } catch (Exception e) {
            count = 0;
            org.jeecgframework.core.util.LogUtil.error("定时上报地点失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报地点结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报地点总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }


    public void reportTBLayer(){
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报煤层开始===================");
        try {
            String sql = "SELECT id FROM t_b_layer WHERE report_group_status='0' or ISNULL(report_group_status) or report_group_status = '' or update_date>=report_group_time";
            List<String> idList = systemService.findListbySql(sql);
            if(!idList.isEmpty() && idList.size()>0){
                int step = 100;
                int times = (idList.size()-1)/step;
                for(int i=0;i<=times;i++){
                    String ids = "";
                    int startIndex = i*step;
                    int endIndex = (i+1)*step > idList.size()?idList.size(): (i+1)*step;
                    for(int k=startIndex;k<endIndex;k++){
                        if(ids.length()>0){
                            ids += ",";
                        }
                        ids += idList.get(k);
                    }

                    count = count + (endIndex-startIndex);
                    tbLayerServiceI.tBLayerReportToGroup(ids);
                }
            }
        } catch (Exception e) {
            count = 0;
            org.jeecgframework.core.util.LogUtil.error("定时上报煤层失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报煤层结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("定时上报煤层总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }



}
