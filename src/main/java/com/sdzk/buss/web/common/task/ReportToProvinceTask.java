package com.sdzk.buss.web.common.task;

import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.aqbzh.util.StringUtil;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.service.ReportToProvinceService;
import com.sdzk.buss.web.gjj.entity.*;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSLog;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 矿端上报省局升级
 * 1.图形文件-文件
 * 2.风险点
 * 3.风险辨识任务-文件
 * 4.风险清单
 * 5.风险管控措施
 * 6.风险管控记录
 * 7.隐患
 * 8.三违
 * 9.重大风险管控方案-文件
 * 10.重大隐患档案信息-文件
 * 11.培训档案-文件
 * 12.双重预防工作制度-文件
 * 13.报告文件-文件
 * 14.登录数据
 *
 * @author ljh
 * @version 1.0
 * @date 2023/10/23 13:44
 */
@Service("reportToProvinceTask")
public class ReportToProvinceTask {
    @Autowired
    private ReportToProvinceService reportToProvinceService;
    @Autowired
    private SystemService systemService;

    /**
     * 1.图形文件数据上报接口
     * 查询规则：stateFlag不为0
     * 数据量小 无需分页 每天执行一次
     * 数据表：sf_picture_info（新增功能）
     */
    public void reportPictureTask() {
        long start = System.currentTimeMillis();
        String name = "图形文件数据信息";
        LogUtil.info(start + "~~~~~定时上报" + name + "开始~~~~~");
        try {
            CriteriaQuery cq = new CriteriaQuery(SFPictureInfoEntity.class);
            cq.notEq("stateFlag", Constants.GJJ_STATE_FLAG_0);
            cq.add();
            List<SFPictureInfoEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list != null && !list.isEmpty()) {
                AjaxJson r = reportToProvinceService.reportPicture(list);
                LogUtil.info("上报结果:" + r.getMsg());
            }else{
                LogUtil.info("上报结果:暂无需要上报数据");
            }
        } catch (Exception e) {
            LogUtil.error("定时上报" + name + "失败", e);
        }
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info(end + "~~~~~定时上报" + name + "结束，总耗时" + times + "毫秒~~~~~");
    }

    /**
     * 2.风险点上报接口
     */
    public void reportRiskPointTask() {
        long start = System.currentTimeMillis();
        String name = "风险点信息";
        LogUtil.info(start + "~~~~~定时上报" + name + "开始~~~~~");
        try {
            CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class);
            cq.notEq("stateFlag", Constants.GJJ_STATE_FLAG_0);
            cq.setPageSize(500);
            cq.setCurPage(1);
            cq.add();
            List<TBAddressInfoEntity> list = systemService.getListByCriteriaQuery(cq, true);
            if (list != null && !list.isEmpty()) {
                AjaxJson r = reportToProvinceService.reportRiskPoint(list);
                LogUtil.info("上报结果:" + r.getMsg());
            }else{
                LogUtil.info("上报结果:暂无需要上报数据");
            }
        } catch (Exception e) {
            LogUtil.error("定时上报" + name + "失败", e);
        }
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info(end + "~~~~~定时上报" + name + "结束，总耗时" + times + "毫秒~~~~~");
    }

    /**
     * 3.风险辨识任务
     * 查询规则：stateFlag不为0
     * 数据量小 无需分页 每天执行一次
     * 数据表：t_b_risk_task（原）
     */
    public void reportRiskIdentTask() {
        long start = System.currentTimeMillis();
        String name = "风险辨识任务";
        LogUtil.info(start + "~~~~~定时上报" + name + "开始~~~~~");
        try {
            String sql = "SELECT\n" +
                    "\ttaskRel.id riskIdentTaskCode,\n" +
                    "\ttaskRel.state_flag stateFlag,\n" +
                    "\ttask.task_type type,\n" +
                    "\ttask.task_name `name`,\n" +
                    "\tDATE_FORMAT(task.start_date ,'%Y-%m-%d') startDate,\n" +
                    "\tDATE_FORMAT(task.end_date,'%Y-%m-%d') endDate,\n" +
                    "\ttask.organizer_man officer,\n" +
                    "\ttask.participant_man participant,\n" +
                    "\t taskRel.file_id fileId,\n" +
                    "\t taskRel.file_name fileName,\n" +
                    "\t\tDATE_FORMAT(task.create_date ,'%Y-%m-%d %H:%i:%s') dataTime\n" +
                    "FROM sf_risk_task_rel taskRel\n" +
                    "\tLEFT JOIN t_b_risk_task task ON taskRel.risk_task_id = task.id where 1=1 and taskRel.state_flag in ('1','2','3')";
            List<Map<String, Object>> list = systemService.findForJdbc(sql);
            if (list != null && !list.isEmpty()) {
                AjaxJson r = reportToProvinceService.reportRiskIdentTask(list);
                LogUtil.info("上报结果:" + r.getMsg());
            }else{
                LogUtil.info("上报结果:暂无需要上报数据");
            }
        } catch (Exception e) {
            LogUtil.error("定时上报" + name + "失败", e);
        }
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info(end + "~~~~~定时上报" + name + "结束，总耗时" + times + "毫秒~~~~~");
    }

    /**
     * 4.风险清单上报接口
     */
    public void reportRiskTask() {
        long start = System.currentTimeMillis();
        String name = "风险清单信息";
        LogUtil.info(start + "~~~~~定时上报" + name + "开始~~~~~");
        try {
            CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class);
            cq.notEq("stateFlag", Constants.GJJ_STATE_FLAG_0);
            cq.eq("status", "3");
            cq.setPageSize(500);
            cq.setCurPage(1);
            cq.add();
            List<RiskIdentificationEntity> list = systemService.getListByCriteriaQuery(cq, true);
            if (list != null && !list.isEmpty()) {
                AjaxJson r = reportToProvinceService.reportRisk(list);
                LogUtil.info("上报结果:" + r.getMsg());
            }else{
                LogUtil.info("上报结果:暂无需要上报数据");
            }
        } catch (Exception e) {
            LogUtil.error("定时上报" + name + "失败", e);
        }
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info(end + "~~~~~定时上报" + name + "结束，总耗时" + times + "毫秒~~~~~");
    }

    /**
     * 5.风险管控措施信息上报接口
     */
    public void reportRiskMeasureTask() {
        long start = System.currentTimeMillis();
        String name = "风险管控措施信息";
        LogUtil.info(start + "~~~~~定时上报" + name + "开始~~~~~");
        try {
            CriteriaQuery cq = new CriteriaQuery(SfRiskMeasureEntity.class);
            cq.notEq("stateFlag", Constants.GJJ_STATE_FLAG_0);
            cq.eq("isUpload", "0");
            cq.setPageSize(500);
            cq.setCurPage(1);
            cq.add();
            List<SfRiskMeasureEntity> list = systemService.getListByCriteriaQuery(cq, true);
            if (list != null && !list.isEmpty()) {
                AjaxJson r = reportToProvinceService.reportRiskMeasure(list);
                LogUtil.info("上报结果:" + r.getMsg());
            }else{
                LogUtil.info("上报结果:暂无需要上报数据");
            }
        } catch (Exception e) {
            LogUtil.error("定时上报" + name + "失败", e);
        }
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info(end + "~~~~~定时上报" + name + "结束，总耗时" + times + "毫秒~~~~~");
    }

    /**
     * 6.风险管控记录信息上报接口
     */
    public void reportRiskControlTask() {
        long start = System.currentTimeMillis();
        String name = "风险管控记录信息";
        LogUtil.info(start + "~~~~~定时上报" + name + "开始~~~~~");
        try {
            CriteriaQuery cq = new CriteriaQuery(SfRiskControlEntity.class);
            cq.notEq("stateFlag", Constants.GJJ_STATE_FLAG_0);
            cq.add();
            List<SfRiskControlEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list != null && !list.isEmpty()) {
                AjaxJson r = reportToProvinceService.reportRiskControl(list);
                LogUtil.info("上报结果:" + r.getMsg());
            }else{
                LogUtil.info("上报结果:暂无需要上报数据");
            }
        } catch (Exception e) {
            LogUtil.error("定时上报" + name + "失败", e);
        }
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info(end + "~~~~~定时上报" + name + "结束，总耗时" + times + "毫秒~~~~~");
    }

    /**
     * 7.隐患上报接口
     */
    public void reportHiddenTask() {
        long start = System.currentTimeMillis();
        String name = "隐患信息";
        LogUtil.info(start + "~~~~~定时上报" + name + "开始~~~~~");
        try {
            String sql = "SELECT\n" +
                    "hiddenRel.id hdCode,\n" +
                    "hiddenRel.state_flag stateFlag,\n" +
                    "measure.id riskMeasureCode,\n" +
                    "exam.risk_id riskCode,\n" +
                    "DATE_FORMAT(exam.exam_date,'%Y-%m-%d') inveDate,\n" +
                    "exam.exam_type inveType,\n" +
                    "exam.fill_card_manids  inveMan,\n" +
                    "exam.address hiddenAddress,\n" +
                    "exam.address riskPointCode,\n" +
                    "exam.pro_type proType,\n" +
                    "exam.hidden_nature hiddenLevel,\n" +
                    "exam.problem_desc problemDesc,\n" +
                    "\"\" imgUrlList,\n" +
                    "exam.deal_type dealType,\n" +
                    "DATE_FORMAT(exam.limit_date,'%Y-%m-%d') limitDate,\n" +
                    "handle.handlel_status  `status`,\n" +
                    "DATE_FORMAT(handle.modify_date,'%Y-%m-%d') modifyDate,\n" +
                    "handle.modify_man modifyUnit,\n" +
                    "handle.modify_man modifyMan,\n" +
                    "handle.rect_measures modifyMeasures,\n" +
                    "\"\" modifyImgUrlList,\n" +
                    "DATE_FORMAT(handle.review_date,'%Y-%m-%d') reviewDate,\n" +
                    "handle.review_man reviewUnit,\n" +
                    "handle.review_man reviewMan,\n" +
                    "exam.manage_duty_unit supervisionUnit,\n" +
                    "exam.manage_duty_man_id supervisor,\n" +
                    "\"\" supervisionInfo,\n" +
                    "DATE_FORMAT(exam.create_date,'%Y-%m-%d %H:%i:%s') dataTime\n" +
                    "FROM\n" +
                    "\tsf_hidden_rel hiddenRel\n" +
                    "\tLEFT JOIN t_b_hidden_danger_exam exam ON hiddenRel.hidden_id = exam.id \n" +
                    "\tLEFT JOIN t_b_hidden_danger_handle handle on handle.hidden_danger_id=exam.id\n" +
                    "\tLEFT JOIN t_b_risk_manage_hazard_factor factor on exam.risk_manage_hazard_factor_id=factor.id\n" +
                    "\tLEFT JOIN sf_risk_measure measure on factor.risk_id=measure.risk_code and factor.hazard_factor_id=measure.risk_factors_id\n" +
                    "WHERE 1 = 1 AND hiddenRel.state_flag IN ('1','2','3') and handle.handlel_status in ('1','3','4','5') limit 500";
            List<Map<String, Object>> list = systemService.findForJdbc(sql);
            if (list != null && !list.isEmpty()) {
                AjaxJson r = reportToProvinceService.reportHidden(list);
                LogUtil.info("上报结果:" + r.getMsg());
            }else{
                LogUtil.info("上报结果:暂无需要上报数据");
            }
        } catch (Exception e) {
            LogUtil.error("定时上报" + name + "失败", e);
        }
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info(end + "~~~~~定时上报" + name + "结束，总耗时" + times + "毫秒~~~~~");
    }

    /**
     * 8.三违上报接口
     */
    public void reportThreeVioTask() {
        long start = System.currentTimeMillis();
        String name = "三违信息";
        LogUtil.info(start + "~~~~~定时上报" + name + "开始~~~~~");
        try {
            String sql = "SELECT\n" +
                    "\tvioRel.id vioCode,\n" +
                    "\tvioRel.state_flag stateFlag,\n" +
                    "\tDATE_FORMAT(vio.vio_date,'%Y-%m-%d') vioDate,\n" +
                    "\tvio.shift vioShift,\n" +
                    "\tvio.vio_address riskPointCode,\n" +
                    "\tvioUnit.departname vioUnit,\n" +
                    "\tvio.vio_people vioPeople,\n" +
                    "\tvio.vio_category vioCategory,\n" +
                    "\tvio.vio_qualitative vioQualitative,\n" +
                    "\tvio.vio_level vioLevel,\n" +
                    "\tfindUnit.departname findUnit,\n" +
                    "\tvio.stop_people stopPeople,\n" +
                    "\tvio.vio_fact_desc vioDesc,\n" +
                    "\tvio.remark vioResult,\n" +
                    "\tDATE_FORMAT(vio.create_date,'%Y-%m-%d %H:%i:%s') dataTime\n" +
                    "FROM sf_vio_rel vioRel\n" +
                    "\tLEFT JOIN t_b_three_violations vio ON vioRel.vio_id = vio.id \n" +
                    "\tLEFT JOIN  t_s_depart vioUnit on vio.vio_units=vioUnit.id\n" +
                    "\tLEFT JOIN t_s_depart findUnit on vio.find_units=findUnit.id\n" +
                    "WHERE 1 = 1 \n" +
                    "\tAND vioRel.state_flag IN ('1','2','3') limit 500";
            List<Map<String, Object>> list = systemService.findForJdbc(sql);
            if (list != null && !list.isEmpty()) {
                AjaxJson r = reportToProvinceService.reportThreeVio(list);
                LogUtil.info("上报结果:" + r.getMsg());
            }else{
                LogUtil.info("上报结果:暂无需要上报数据");
            }
        } catch (Exception e) {
            LogUtil.error("定时上报" + name + "失败", e);
        }
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info(end + "~~~~~定时上报" + name + "结束，总耗时" + times + "毫秒~~~~~");
    }

    /**
     * 9.重大风险管控方案信息数据上报接口
     */
    public void sfPlanInfoTask() {
        long start = System.currentTimeMillis();
        String name = "重大风险管控方案信息";
        LogUtil.info(start + "~~~~~定时上报" + name + "开始~~~~~");
        try {
            CriteriaQuery cq = new CriteriaQuery(SfPlanInfoEntity.class);
            cq.notEq("stateFlag", Constants.GJJ_STATE_FLAG_0);
            cq.add();
            List<SfPlanInfoEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list != null && !list.isEmpty()) {
                AjaxJson r = reportToProvinceService.sfPlanInfo(list);
                LogUtil.info("上报结果:" + r.getMsg());
            }else{
                LogUtil.info("上报结果:暂无需要上报数据");
            }
        } catch (Exception e) {
            LogUtil.error("定时上报" + name + "失败", e);
        }
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info(end + "~~~~~定时上报" + name + "结束，总耗时" + times + "毫秒~~~~~");
    }

    /**
     * 10.重大隐患档案信息数据上报接口
     */
    public void sfArchiveInfoTask() {
        long start = System.currentTimeMillis();
        String name = "重大隐患档案信息";
        LogUtil.info(start + "~~~~~定时上报" + name + "开始~~~~~");
        try {
            CriteriaQuery cq = new CriteriaQuery(SfArchiveInfoEntity.class);
            cq.notEq("stateFlag", Constants.GJJ_STATE_FLAG_0);
            cq.add();
            List<SfArchiveInfoEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list != null && !list.isEmpty()) {
                AjaxJson r = reportToProvinceService.sfArchiveInfo(list);
                LogUtil.info("上报结果:" + r.getMsg());
            }else{
                LogUtil.info("上报结果:暂无需要上报数据");
            }
        } catch (Exception e) {
            LogUtil.error("定时上报" + name + "失败", e);
        }
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info(end + "~~~~~定时上报" + name + "结束，总耗时" + times + "毫秒~~~~~");
    }

    /**
     * 11.培训档案信息数据上报接口
     */
    public void sfTrainingInfoTask() {
        long start = System.currentTimeMillis();
        String name = "培训档案信息";
        LogUtil.info(start + "~~~~~定时上报" + name + "开始~~~~~");
        try {
            CriteriaQuery cq = new CriteriaQuery(SfTrainingInfoEntity.class);
            cq.notEq("stateFlag", Constants.GJJ_STATE_FLAG_0);
            cq.add();
            List<SfTrainingInfoEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list != null && !list.isEmpty()) {
                AjaxJson r = reportToProvinceService.sfTrainingInfo(list);
                LogUtil.info("上报结果:" + r.getMsg());
            }else{
                LogUtil.info("上报结果:暂无需要上报数据");
            }
        } catch (Exception e) {
            LogUtil.error("定时上报" + name + "失败", e);
        }
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info(end + "~~~~~定时上报" + name + "结束，总耗时" + times + "毫秒~~~~~");
    }

    /**
     * 12.双重预防工作制度信息数据上报接口
     */
    public void sfSysFileInfoTask() {
        long start = System.currentTimeMillis();
        String name = "双重预防工作制度信息";
        LogUtil.info(start + "~~~~~定时上报" + name + "开始~~~~~");
        try {
            CriteriaQuery cq = new CriteriaQuery(SfSysFileInfoEntity.class);
            cq.notEq("stateFlag", Constants.GJJ_STATE_FLAG_0);
            cq.add();
            List<SfSysFileInfoEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list != null && !list.isEmpty()) {
                AjaxJson r = reportToProvinceService.sfSysFileInfo(list);
                LogUtil.info("上报结果:" + r.getMsg());
            }else{
                LogUtil.info("上报结果:暂无需要上报数据");
            }
        } catch (Exception e) {
            LogUtil.error("定时上报" + name + "失败", e);
        }
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info(end + "~~~~~定时上报" + name + "结束，总耗时" + times + "毫秒~~~~~");
    }

    /**
     * 13.报告文件数据上报接口
     */
    public void reportReportInfoTask() {
        long start = System.currentTimeMillis();
        String name = "报告文件数据信息";
        LogUtil.info(start + "~~~~~定时上报" + name + "开始~~~~~");
        try {
            CriteriaQuery cq = new CriteriaQuery(SFReportInfoEntity.class);
            cq.notEq("stateFlag", Constants.GJJ_STATE_FLAG_0);
            cq.add();
            List<SFReportInfoEntity> list = systemService.getListByCriteriaQuery(cq, false);
            if (list != null && !list.isEmpty()) {
                AjaxJson r = reportToProvinceService.reportReportInfo(list);
                LogUtil.info("上报结果:" + r.getMsg());
            }else{
                LogUtil.info("上报结果:暂无需要上报数据");
            }
        } catch (Exception e) {
            LogUtil.error("定时上报" + name + "失败", e);
        }
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info(end + "~~~~~定时上报" + name + "结束，总耗时" + times + "毫秒~~~~~");
    }

    /**
     * 14.登录数据上报接口
     */
    public void reportLoginLog() {
        long start = System.currentTimeMillis();
        String name = "登录数据";
        LogUtil.info("~~~~~定时上报登录数据开始~~~~~");
        try {
            String userSql = "select distinct userid from t_s_log where state_flag='1'";
            List<Map<String, Object>> userIds = systemService.findForJdbc(userSql);
            if (userIds != null && !userIds.isEmpty()) {
                for (Map<String, Object> user : userIds) {
                    if (StringUtil.isNotBlank(user.get("userid"))) {
                        String id = user.get("userid").toString();
                        CriteriaQuery cq = new CriteriaQuery(TSLog.class);
                        cq.eq("stateFlag", "1");
                        cq.eq("TSUser.id", id);
                        cq.addOrder("operatetime", SortDirection.asc);
                        cq.add();
                        // 待上报的数据
                        List<TSLog> list = systemService.getListByCriteriaQuery(cq, false);
                        if (list != null && !list.isEmpty()) {
                            AjaxJson r = reportToProvinceService.reportLoginLog(list, id);
                            LogUtil.info("上报结果:" + r.getMsg());
                        }else{
                            LogUtil.info("上报结果:暂无需要上报数据");
                        }
                    }
                }
            }

        } catch (Exception e) {
            LogUtil.error("定时上报" + name + "失败", e);
        }
        long end = System.currentTimeMillis();
        long times = end - start;
        LogUtil.info(end + "~~~~~定时上报" + name + "结束，总耗时" + times + "毫秒~~~~~");
    }
}