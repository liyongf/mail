package com.sdzk.buss.api.controller;

import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.riskmanage.entity.RiskManageTaskAllEntity;
import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.api.service.ApiServiceI;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

@Scope("prototype")
@Controller
@RequestMapping("/mobileHiddenUniappController")
public class MobileHiddenUniappController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiServiceI apiService;

    private String sessionCache = "sessionCache";

    //app我的任务-已上报隐患列表
    @RequestMapping("/hiddenList")
    @ResponseBody
    public ApiResultJson hiddenList(HttpServletRequest request) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        try {
            String sessionId = request.getParameter("sessionId");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //检查类型
            String examType = request.getParameter("examType");
            String dataId = request.getParameter("dataId");
            String taskAllId = request.getParameter("taskAllId");
            String queryHandleStatus = "ALL";
            String page = request.getParameter("page");
            String rows = request.getParameter("rows");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202, "用户未登录", null);
            }
            TSUser user = (TSUser) EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202, "用户未登录", null);
            }
            ArrayList<HashMap> result = new ArrayList<>();
            DataGrid dataGrid = new DataGrid();
            CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerExamEntity.class,dataGrid);
            if (StringUtil.isNotEmpty(dataId)){
                cq.eq("id", dataId);
            }else {
                cq.createAlias("address", "address");
                cq.eq("address.isShowData", "1");
                if (Constants.HANDELSTATUS_DRAFT.equals(queryHandleStatus)) {
                    String[] rollbackStatus = new String[]{Constants.HANDELSTATUS_DRAFT};
                    cq.createAlias("handleEntity", "handleEntity");
                    cq.in("handleEntity.handlelStatus", rollbackStatus);
                } else if (Constants.HANDELSTATUS_ROLLBACK.equals(queryHandleStatus)) {
                    String[] rollbackStatus = new String[]{Constants.HANDELSTATUS_ROLLBACK_REPORT};
                    cq.createAlias("handleEntity", "handleEntity");
                    cq.in("handleEntity.handlelStatus", rollbackStatus);
                } else {
                    String[] rollbackStatus = new String[]{Constants.HANDELSTATUS_REPORT, Constants.HANDELSTATUS_REVIEW, Constants.REVIEWSTATUS_PASS, Constants.HANDELSTATUS_ROLLBACK_CHECK};
                    cq.createAlias("handleEntity", "handleEntity");
                    cq.in("handleEntity.handlelStatus", rollbackStatus);
                }
                if (StringUtil.isNotEmpty(examType)) {
                    cq.eq("examType", examType);
                }
                if (StringUtil.isNotEmpty(taskAllId)) {
                    cq.eq("taskAllId", taskAllId);
                }

                boolean isAdmin = false;
                CriteriaQuery cqru = new CriteriaQuery(TSRoleUser.class);
                try {
                    cqru.eq("TSUser.id", user.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cqru.add();
                List<TSRoleUser> roleList = systemService.getListByCriteriaQuery(cqru, false);
                if (roleList != null && !roleList.isEmpty()) {
                    for (TSRoleUser ru : roleList) {
                        TSRole role = ru.getTSRole();
                        if (role != null && role.getRoleName().equals("管理员")) {
                            isAdmin = true;
                            break;
                        }
                    }
                }

                if (!isAdmin) {
                    cq.eq("createBy", user.getUserName());
                }
                cq.addOrder("createDate", SortDirection.desc);
                cq.setCurPage(Integer.parseInt(page));
                cq.setPageSize(Integer.parseInt(rows));
            }
            cq.add();
            this.systemService.getDataGridReturn(cq, true);
            if (dataGrid != null && dataGrid.getResults() != null) {
                if (dataGrid.getResults().size() > 0) {
                    List<TBHiddenDangerExamEntity> list = dataGrid.getResults();
                    for (TBHiddenDangerExamEntity t : list) {
                        String names = "";

                        String querySql = "select fill_card_manids man from t_b_hidden_danger_exam where id = '" + String.valueOf(t.getId()) + "'";
                        List<Map<String, Object>> maplist = systemService.findForJdbc(querySql, null);
                        for (Map map : maplist) {
                            String mans = String.valueOf(map.get("man"));
                            if (StringUtils.isNotBlank(mans)) {
                                String[] userIdArray = mans.split(",");

                                for (String userid : userIdArray) {
                                    TSUser fillUser = systemService.getEntity(TSUser.class, userid);
                                    if (fillUser != null) {
                                        if (names == "") {
                                            names = names + fillUser.getRealName() + "-" + fillUser.getUserName();
                                        } else {
                                            names = names + "," + fillUser.getRealName() + "-" + fillUser.getUserName();
                                        }

                                    } else if (StringUtil.isNotEmpty(userid)) {
                                        if (names == "") {
                                            names = names + userid;
                                        } else {
                                            names = names + "," + userid;
                                        }
                                    }
                                }
                            }
                        }


                        t.setFillCardManNames(names);

                        cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class);
                        cq.eq("hiddenDanger.id", t.getId());
                        cq.add();
                        List<TBHiddenDangerHandleEntity> handleEntityList = systemService.getListByCriteriaQuery(cq, false);
                        if (!handleEntityList.isEmpty() && handleEntityList.size() > 0) {
                            t.setRollBackRemarkTemp(handleEntityList.get(0).getRollBackRemark());
                        }


                        if (t.getReviewMan() != null && t.getReviewMan().getId().length() > 0) {

                        } else {
                            t.setReviewMan(null);
                        }
                        t.setDutyManTemp(t.getDutyMan());

                        HashMap map = new HashMap();
                        map.put("id",t.getId());
                        map.put("examDate",dateFormat.format(t.getExamDate()));
                        map.put("shift",DicUtil.getTypeNameByCode("workShift", t.getShift()));
                        map.put("examMan",names);
                        map.put("manageType",DicUtil.getTypeNameByCode("manageType", t.getManageType()));
                        map.put("address",null==t.getAddress()?"":t.getAddress().getAddress());
                        map.put("riskType",DicUtil.getTypeNameByCode("risk_type", t.getRiskType()));
                        map.put("riskDesc",null==t.getRiskId()?"":t.getRiskId().getRiskDesc());
                        map.put("dutyUnit",null==t.getDutyUnit()?"":t.getDutyUnit().getDepartname());
                        map.put("dutyMan",t.getDutyMan());
                        map.put("hiddenLevel",DicUtil.getTypeNameByCode("hiddenLevel", t.getHiddenNature()));
                        map.put("problemDesc",t.getProblemDesc());

                        if (StringUtil.isNotEmpty(t.getDealType())){
                            map.put("dealType",t.getDealType().equals("1")?"限时整改":"现场处理");
                            map.put("limitDate",t.getDealType().equals("1")?dateFormat.format(t.getLimitDate()):null);
                            map.put("reviewMan",t.getDealType().equals("1")?"":t.getReviewMan().getRealName());
                            map.put("modifyMeasure",t.getDealType().equals("1")?"":t.getHandleEntity().getRectMeasures());
                            map.put("reviewDetail",t.getDealType().equals("1")?"":t.getHandleEntity().getReviewReport());
                        }

                        map.put("handleStatus",DicUtil.getTypeNameByCode("handelStatus", t.getHandleEntity().getHandlelStatus()));

                        result.add(map);
                    }

                }
            }
            return new ApiResultJson(result);
        } catch (Exception e) {
            LogUtil.error("隐患查询错误", e);
            return new ApiResultJson(ApiResultJson.CODE_500, ApiResultJson.CODE_500_MSG, null);
        }
    }


    /**
     *  隐患上报
     */
    @RequestMapping("/uploadHidden")
    @ResponseBody
    public ApiResultJson uploadHidden(HttpServletRequest request){
        ApiResultJson j = new ApiResultJson();
        j.setMessage("保存成功！");
        try {
            String sessionId = request.getParameter("sessionId");
            String offId = request.getParameter("id");
            String taskId = request.getParameter("taskId");
            String hiddenShift = request.getParameter("hiddenShift");
            String hiddenAddress = request.getParameter("hiddenAddress");
            String dutyUnit = request.getParameter("dutyUnit");
            String dutyMan = request.getParameter("dutyMan");
            String reviewMan = request.getParameter("reviewMan");
            String hiddenLevel = request.getParameter("hiddenLevel");
            String hiddenType = request.getParameter("hiddenType");
            String problemFactDesc = request.getParameter("problemFactDesc");
            String modifyMeasure = request.getParameter("modifyMeasure");
            String reviewDetail = request.getParameter("reviewDetail");
            String infoSource = request.getParameter("infoSource");
            String dealType = request.getParameter("dealType");
            String checkDate = request.getParameter("checkDate");
            String limitDate = request.getParameter("limitDate");
            String checkMan = request.getParameter("checkMan");
            String checkManName = request.getParameter("checkManName");
            String riskDesc = request.getParameter("riskDesc");
            String controlDutyMan = request.getParameter("controlDutyMan");
            String controlDutyUnitName = request.getParameter("controlDutyUnitName");
            String controlDutyUnit = request.getParameter("controlDutyUnit");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TBHiddenDangerExamEntity hidden = null;
            TBHiddenDangerHandleEntity handleEntity = new TBHiddenDangerHandleEntity();
            if (StringUtil.isNotEmpty(offId)){
                hidden = this.systemService.getEntity(TBHiddenDangerExamEntity.class,offId);
            }
            Boolean isNullObj = false;
            if (null==hidden){
                hidden = new TBHiddenDangerExamEntity();
                isNullObj = true;
            }else {
                handleEntity = this.systemService.get(TBHiddenDangerHandleEntity.class,hidden.getHandleEntity().getId());
            }

            hidden.setExamType("yh");
            hidden.setTaskAllId(taskId);
            hidden.setAddress(systemService.get(TBAddressInfoEntity.class,hiddenAddress));
            if (StringUtil.isNotEmpty(checkDate)){
                hidden.setExamDate(DateUtils.parseDate(checkDate,"yyyy-MM-dd"));
            }
//            hidden.setHiddenType(hiddenType);
            hidden.setRiskType(hiddenType);
//            hidden.setHiddenLevel(hiddenLevel);
            hidden.setHiddenNature(hiddenLevel);
            hidden.setHiddenNatureOriginal(hiddenLevel);
            hidden.setDutyUnit(systemService.get(TSDepart.class,dutyUnit));
            TSUser dutyManName = this.systemService.get(TSUser.class, dutyMan);
            hidden.setDutyManId(dutyMan);
            hidden.setDutyMan(dutyManName.getRealName());
            hidden.setDealType(dealType);
            hidden.setCreateBy(user.getUserName());
            hidden.setCreateName(user.getRealName());
            hidden.setCreateDate(new Date());
            hidden.setShift(hiddenShift);
            if (StringUtil.isNotEmpty(limitDate)&&dealType.equals("1")){
                hidden.setLimitDate(DateUtils.parseDate(limitDate,"yyyy-MM-dd"));
            }
            hidden.setReviewMan(systemService.get(TSUser.class,reviewMan));
            hidden.setManageType(infoSource);
            hidden.setFillCardManId(checkMan);
            hidden.setManageDutyManId(controlDutyMan);
            hidden.setManageDutyUnit(systemService.get(TSDepart.class,controlDutyUnit));
            hidden.setRiskId(systemService.get(RiskIdentificationEntity.class,riskDesc));
            hidden.setProblemDesc(problemFactDesc);

            if (isNullObj){
                this.systemService.save(hidden);
            }else {
                this.systemService.updateEntitie(hidden);
            }

            //创建handle数据
            handleEntity.setHiddenDanger(hidden);
            if(Constants.DEALTYPE_XIANCAHNG.equals(dealType)){//现场处理
                handleEntity.setReviewMan(reviewMan);
                handleEntity.setModifyDate(hidden.getExamDate());
                handleEntity.setModifyShift(hidden.getShift());
                handleEntity.setRectMeasures(modifyMeasure);
                handleEntity.setReviewReport(reviewDetail);
                handleEntity.setModifyMan(hidden.getDutyMan());
                handleEntity.setReviewDate(hidden.getExamDate());
                handleEntity.setReviewShift(hidden.getShift());
                handleEntity.setReviewResult(Constants.REVIEWSTATUS_OK);

                Date curdate = new Date();
                handleEntity.setReportStatus("1");
                handleEntity.setReportName(user.getRealName());


                handleEntity.setUpdateDate(curdate);
                handleEntity.setUpdateBy(user.getUserName());
                handleEntity.setUpdateName(user.getRealName());

            }else{//限期整改
                Date curdate = new Date();
                handleEntity.setReportStatus("1");
                handleEntity.setReportName(user.getRealName());


                handleEntity.setUpdateDate(curdate);
                handleEntity.setUpdateBy(user.getUserName());
                handleEntity.setUpdateName(user.getRealName());
            }
            handleEntity.setHandlelStatus(Constants.HANDELSTATUS_REPORT);
            if (isNullObj){
                this.systemService.save(handleEntity);
            }else {
                this.systemService.updateEntitie(handleEntity);
            }
            hidden.setHandleEntity(handleEntity);
            this.systemService.updateEntitie(hidden);

        } catch (Exception e) {
            String message =ApiResultJson.CODE_500_MSG;
            if (StringUtil.isNotEmpty(e.getMessage())){
                message = e.getMessage();
            }
            j.setMessage(message);
            return new ApiResultJson(ApiResultJson.CODE_500,message,null);
        }
        return j;
    }
    /**
     *  隐患上报
     */
    @RequestMapping("/uploadHiddenTask")
    @ResponseBody
    public ApiResultJson uploadHiddenTask(HttpServletRequest request){
        ApiResultJson j = new ApiResultJson();
        j.setMessage("保存成功！");
        try {
            String sessionId = request.getParameter("sessionId");
            String offId = request.getParameter("id");//缓存数据主键
            if(StringUtils.isNotBlank(offId)){
                offId = offId.substring(2,offId.length()-2);
            }
            String createDate = request.getParameter("createDate");
            String manageTypeId = request.getParameter("manageTypeId");
            String manageTime = request.getParameter("manageTime");
            String manageShiftId = request.getParameter("manageShiftId");
            if(StringUtils.isBlank(manageShiftId)){
                String manageShift = request.getParameter("manageShift");
                manageShiftId=DicUtil.getTypeCodeByName("workShift",manageShift);
            }
            String remark = request.getParameter("remark");
            String status = request.getParameter("status");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            RiskManageTaskAllEntity taskAllEntity = null;
            if (StringUtil.isNotEmpty(offId)){
                taskAllEntity = this.systemService.getEntity(RiskManageTaskAllEntity.class,offId);
            }
            Boolean isNullObj = false;
            if (null==taskAllEntity){
                taskAllEntity = new RiskManageTaskAllEntity();
                isNullObj = true;
            }
            taskAllEntity.setStatus(status);
            taskAllEntity.setCreateDate(DateUtils.parseDate(createDate,"yyyy-MM-dd"));
            taskAllEntity.setManageTime(DateUtils.parseDate(manageTime,"yyyy-MM-dd"));
            taskAllEntity.setManageType(manageTypeId);
            taskAllEntity.setManageShift(manageShiftId);
            taskAllEntity.setRemark(remark);
            taskAllEntity.setCreateBy(user.getUserName());
            taskAllEntity.setCreateName(user.getRealName());

            if (isNullObj){
                this.systemService.save(taskAllEntity);
            }else {
                this.systemService.updateEntitie(taskAllEntity);
            }

            j.setData(taskAllEntity.getId());

        } catch (Exception e) {
            String message =ApiResultJson.CODE_500_MSG;
            if (StringUtil.isNotEmpty(e.getMessage())){
                message = e.getMessage();
            }
            j.setMessage(message);
            return new ApiResultJson(ApiResultJson.CODE_500,message,null);
        }
        return j;
    }

}
