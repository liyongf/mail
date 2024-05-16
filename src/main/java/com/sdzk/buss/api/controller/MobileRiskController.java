package com.sdzk.buss.api.controller;

import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.riskdata.entity.HazardFactorsEntity;
import com.sddb.buss.riskmanage.entity.RiskManageHazardFactorEntity;
import com.sddb.buss.riskmanage.entity.RiskManageTaskAllEntity;
import com.sddb.buss.riskmanage.entity.RiskManageTaskEntity;
import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.api.service.ApiServiceI;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.hiddendanger.entity.MobileTBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.MobileTBHiddenDangerHandleEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.pojo.base.TSDocument;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @user xuran
 * 危险源,风险查询,接收录入数据,整改复查接口
 */
@Controller
@RequestMapping("/mobile/MobileRiskController")
public class MobileRiskController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiServiceI apiService;

    private String sessionCache="sessionCache";


    /**
     * 重大风险列表
     * @param token
     * @param sessionId     用户当前登录的sessionid
     * @param page          当前页数
     * @param rows          查询行数
     * @return
     */
    @RequestMapping("/majorRiskList")
    @ResponseBody
    public ApiResultJson majorRiskList(String token, HttpServletRequest request){
        //TODO TOKEN验证
        try {
            String sessionId = request.getParameter("sessionId");
            String page = request.getParameter("page");
            String rows = request.getParameter("rows");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            List<Map<String, Object>> result=null;
            StringBuffer sql = new StringBuffer("select ds.id, ds.ye_risk_grade riskLevel,ds.manage_measure manageMeasure,am.activity_name activityName  from t_b_danger_source ds join t_b_activity_manage am on ds.activity_id=am.id where ds.ismajor = 1 ");
            result = systemService.findForJdbc(sql.toString(), Integer.parseInt(page), Integer.parseInt(rows));
            if (result != null && result.size() > 0) {
                for (Map<String, Object> map: result) {
                    map.put("exam_type", map.get("exam_type")!=null?DicUtil.getTypeNameByCode("examType", (String) map.get("exam_type")):"");
                    map.put("handlel_status", map.get("handlel_status")!=null?DicUtil.getTypeNameByCode("handelStatus", (String) map.get("handlel_status")):"");
                }
            }
            return new ApiResultJson(result);
        } catch (Exception e) {
            LogUtil.error("隐患列表查询错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    /**
     * 查看隐患详情
     * @param token
     * @param sessionId     用户当前登录的sessionid
     * @param id            隐患id
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ApiResultJson detail(String token, HttpServletRequest request){
        //TODO TOKEN验证
        try {
            String sessionId = request.getParameter("sessionId");
            String id = request.getParameter("id");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            if (StringUtils.isBlank(id)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"请选择要查询的隐患",null);
            }

            TBHiddenDangerExamEntity examEntity = systemService.getEntity(TBHiddenDangerExamEntity.class, id);
            if (examEntity != null) {
                JSONObject result = new JSONObject();
                result.put("address", examEntity.getAddress()!=null?examEntity.getAddress().getAddress():"");
                result.put("fillCardMan", examEntity.getFillCardMan()!=null?examEntity.getFillCardMan().getRealName():"");
                result.put("hazardName", examEntity.getDangerId()!=null&&examEntity.getDangerId().getHazard()!=null?examEntity.getDangerId().getHazard().getHazardName():"");
                result.put("dutyUnit", examEntity.getDutyUnit()!=null?examEntity.getDutyUnit().getDepartname():"");
                result.put("dutyMan", examEntity.getDutyMan());
                result.put("hiddenCategory", DicUtil.getTypeNameByCode("hiddenCate", examEntity.getHiddenCategory()));
                result.put("hiddenNature", DicUtil.getTypeNameByCode("hiddenLevel", examEntity.getHiddenNature()));
                result.put("hiddenType", DicUtil.getTypeNameByCode("hiddenType", examEntity.getHiddenType()));
                result.put("problemDesc", examEntity.getProblemDesc());
                result.put("examType", DicUtil.getTypeNameByCode("examType", examEntity.getExamType()));
                result.put("dealType", examEntity.getDealType());
                result.put("limitDate", examEntity.getLimitDate()!=null?DateUtils.date2Str(examEntity.getLimitDate(), DateUtils.date_sdf):"");
                result.put("examDate", examEntity.getExamDate()!=null?DateUtils.date2Str(examEntity.getExamDate(), DateUtils.date_sdf):"");
                result.put("shift", DicUtil.getTypeNameByCode("workShift", examEntity.getShift()));
                TBHiddenDangerHandleEntity handleEntity = examEntity.getHandleEntity();
                if (handleEntity != null) {
                    String handelStatus = handleEntity.getHandlelStatus();
                    TSUser tsUser = null;
                    result.put("handelStatus", handelStatus);
                    if (Constants.HANDELSTATUS_REVIEW.equals(handelStatus)
                            || Constants.REVIEWSTATUS_PASS.equals(handelStatus)) {
                        if (StringUtils.isNoneBlank(handleEntity.getModifyMan())) {
                            tsUser = systemService.getEntity(TSUser.class, handleEntity.getModifyMan());
                            if(tsUser != null) {
                                result.put("modifyMan", tsUser.getRealName());
                            }
                        }
                        result.put("modifyDate", handleEntity.getModifyDate()!=null?DateUtils.date2Str(handleEntity.getModifyDate(), DateUtils.date_sdf):"");
                        result.put("modifyShift", DicUtil.getTypeNameByCode("workShift", handleEntity.getModifyShift()));
                        result.put("rectMeasures", handleEntity.getRectMeasures());
                    } else {
                        result.put("modifyMan", null);
                        result.put("modifyDate", null);
                        result.put("modifyShift", null);
                        result.put("rectMeasures", null);
                    }
                    if (Constants.REVIEWSTATUS_PASS.equals(handelStatus)) {
                        if (StringUtils.isNoneBlank(handleEntity.getReviewMan())) {
                            tsUser = systemService.getEntity(TSUser.class, handleEntity.getReviewMan());
                            if(tsUser != null) {
                                result.put("reviewMan", tsUser.getRealName());
                            }
                        }
                        result.put("reviewDate", handleEntity.getReviewDate()!=null?DateUtils.date2Str(handleEntity.getReviewDate(), DateUtils.date_sdf):"");
                        result.put("reviewShift", DicUtil.getTypeNameByCode("workShift", handleEntity.getReviewShift()));
                        result.put("reviewReport", handleEntity.getReviewReport());
                    } else {
                        result.put("reviewMan", null);
                        result.put("reviewDate", null);
                        result.put("reviewShift", null);
                        result.put("reviewReport", null);
                    }
                }
                return new ApiResultJson(result);
            } else {
                return new ApiResultJson(ApiResultJson.CODE_202,"隐患不存在或已被删除",null);
            }

        } catch (Exception e) {
            LogUtil.error("隐患详情查询错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    /**
     * 上报录入隐患接口
     * @param token
     * @param sessionId         用户当前登录的sessionid
     * @param id                手机端主键
     * @param examType          检查类型(对应字典值 examType)
     * @param examDate          检查时间
     * @param shift             班次(对应字典值 workShift)
     * @param addressId         地点id
     * @param fillCardManId     检查人id
     * @param dutyUnitId        责任部门id
     * @param dutyMan           责任人姓名
     * @param hiddenCategory    隐患类别(对应字典值 hiddenCate)
     * @param hiddenNature      隐患等级(对应字典值 hiddenLevel)
     * @param hiddenType        隐患类型(对应字典值 hiddenType)
     * @param problemDesc       问题描述
     * @param dealType          处理方式-1=限期整改;2=现场整改
     * @param reviewManId       复查人id(处理方式为现场整改必填)
     * @param limitDate         限期日期(处理方式为限期整改必填)
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ApiResultJson syncData(String token, String reportContent, HttpServletRequest request){
        //TODO token校验
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            JSONArray jsonArray = JSONArray.fromObject(reportContent);
            if (jsonArray !=null && jsonArray.size() > 0) {
                List<MobileTBHiddenDangerExamEntity> entities = new ArrayList<>();
                for (int i=0; i<jsonArray.size(); i++) {
                    MobileTBHiddenDangerExamEntity entity = new MobileTBHiddenDangerExamEntity();
                    JSONObject object = jsonArray.getJSONObject(i);
                    entity.setMobileId(object.optString("id"));
                    entity.setExamType(object.optString("examType"));
                    entity.setExamDate(DateUtils.str2Date(object.optString("examDate"), DateUtils.date_sdf));
                    entity.setShift(object.optString("shift"));
                    entity.setAddress(object.optString("addressId"));
                    entity.setFillCardMan(object.optString("fillCardManId"));
                    entity.setDutyUnit(object.optString("dutyUnitId"));
                    entity.setDutyMan(object.optString("dutyMan"));
                    entity.setHiddenCategory(object.optString("hiddenCategory"));
                    entity.setHiddenNature(object.optString("hiddenNature"));
                    entity.setHiddenType(object.optString("hiddenType"));
                    entity.setProblemDesc(object.optString("problemDesc"));
                    entity.setDealType(object.optString("dealType"));
                    entity.setHiddenNatureOriginal(entity.getHiddenNature());
                    entity.setIsLsProv(Constants.HDBIISLS_STATE_UNDO);
                    entity.setIsLsSub(Constants.HDBIISLS_STATE_UNDO);
                    entity.setReportStatus(Constants.REPORT_STATUS_N);
                    entity.setCreateBy(user.getUserName());
                    entity.setCreateName(user.getRealName());
                    entity.setCreateDate(new Date());
                    MobileTBHiddenDangerHandleEntity handleEntity = new MobileTBHiddenDangerHandleEntity();
//                    handleEntity.setHiddenDanger(entity);
                    handleEntity.setHandlelStatus(Constants.HANDELSTATUS_DRAFT);
                    handleEntity.setHiddenDanger(entity);
                    handleEntity.setCreateBy(user.getUserName());
                    handleEntity.setCreateName(user.getRealName());
                    handleEntity.setCreateDate(new Date());
                    if (Constants.DEALTYPE_XIANCAHNG.equals(entity.getDealType())) {//现场处理
                        entity.setReviewMan(object.optString("reviewManId"));
                        entity.setLimitDate(null);
                        entity.setLimitShift(null);
                        handleEntity.setReviewMan(entity.getReviewMan());
                        handleEntity.setModifyDate(entity.getExamDate());
                        handleEntity.setModifyShift(entity.getShift());
                        handleEntity.setModifyMan(entity.getDutyMan());
                        handleEntity.setReviewDate(entity.getExamDate());
                        handleEntity.setReviewShift(entity.getShift());
                    } else {//限期整改,改为草稿状态
                        entity.setReviewMan(null);
                        entity.setLimitDate(DateUtils.str2Date(object.optString("limitDate"), DateUtils.date_sdf));
                    }
                    entity.setHandleEntity(handleEntity);
                    entities.add(entity);
                }
                systemService.batchSave(entities);
            }
            return new ApiResultJson(ApiResultJson.CODE_200,ApiResultJson.CODE_200_MSG, null);
        } catch (Exception e) {
            LogUtil.error("隐患上报错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    /**
     * 整改
     * @param token
     * @param sessionId             用户当前登录的sessionid
     * @param id                    隐患id
     * @param checkStatus           0=驳回;1=整改完成
     * @param modifyDate            整改时间
     * @param modifyMan             整改人
     * @param modifyShift           整改班次
     * @param rectMeasures          整改情况
     * @return
     */
    @RequestMapping("/rectify")
    @ResponseBody
    public ApiResultJson rectify(String token, HttpServletRequest request){
        String msg = "整改通过成功";
        try {
            String sessionId = request.getParameter("sessionId");
            String id = request.getParameter("id");
            String checkStatus = request.getParameter("checkStatus");
            String modifyDate = request.getParameter("modifyDate");
            String modifyMan = request.getParameter("modifyMan");
            String modifyShift = request.getParameter("modifyShift");
            String rectMeasures = request.getParameter("rectMeasures");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            if (StringUtils.isBlank(id)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"请选择要整改的隐患",null);
            }

            TBHiddenDangerExamEntity tBHiddenDangerExam = systemService.getEntity(TBHiddenDangerExamEntity.class, id);
            if (tBHiddenDangerExam == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"隐患不存在或已被删除",null);
            }
            TBHiddenDangerHandleEntity entity = tBHiddenDangerExam.getHandleEntity();
            if (!(Constants.HANDELSTATUS_REPORT.equals(entity.getHandlelStatus())||Constants.HANDELSTATUS_ROLLBACK_CHECK.equals(entity.getHandlelStatus()))) {
                return new ApiResultJson(ApiResultJson.CODE_202,"该隐患已被其他人员处理, 请查看最新状态.",null);
            }
            if ("0".equals(checkStatus)) {
                entity.setHandlelStatus(Constants.HANDELSTATUS_ROLLBACK_REPORT);
                entity.setRollBackRemark(rectMeasures);
                entity.setReviewMan(null);
                entity.setReviewDate(null);
                entity.setReviewShift(null);
                entity.setReviewReport(null);
                entity.setReviewResult(null);
                msg = "驳回成功";
            } else {
                entity.setHandlelStatus(Constants.HANDELSTATUS_REVIEW);
                entity.setModifyDate(DateUtils.str2Date(modifyDate, DateUtils.date_sdf));
                entity.setModifyMan(modifyMan);
                entity.setModifyShift(modifyShift);
                entity.setRectMeasures(rectMeasures);
                entity.setReviewMan(null);
                entity.setReviewDate(null);
                entity.setReviewShift(null);
                entity.setReviewReport(null);
                entity.setReviewResult(null);
            }
            entity.setUpdateBy(user.getUserName());
            entity.setUpdateName(user.getRealName());
            entity.setUpdateDate(new Date());
            apiService.rectify(entity);
            return new ApiResultJson(msg);
        } catch (Exception e) {
            LogUtil.error("隐患整改查询错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    /**
     * 复查
     * @param sessionId                 用户当前登录的sessionid
     * @param id                        隐患id
     * @param checkStatus               复查状态-0=复查不通过;1=复查通过
     * @param limitDate                 限期日期(复查不通过是必填)
     * @param reviewMan                 复查人
     * @param reviewDate                复查时间
     * @param reviewShift               复查班次
     * @param reviewReport              复查情况
     * @return
     */
    @RequestMapping("/review")
    @ResponseBody
    public ApiResultJson review(String token, HttpServletRequest request){
        try {
            String sessionId = request.getParameter("sessionId");
            String id = request.getParameter("id");
            String checkStatus = request.getParameter("checkStatus");
            String limitDate = request.getParameter("limitDate");
            String reviewMan = request.getParameter("reviewMan");
            String reviewDate = request.getParameter("reviewDate");
            String reviewShift = request.getParameter("reviewShift");
            String reviewReport = request.getParameter("reviewReport");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            if (StringUtils.isBlank(id)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"请选择要整改的隐患",null);
            }

            TBHiddenDangerExamEntity tBHiddenDangerExam = systemService.getEntity(TBHiddenDangerExamEntity.class, id);
            if (tBHiddenDangerExam == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"隐患不存在或已被删除",null);
            }
            TBHiddenDangerHandleEntity entity = tBHiddenDangerExam.getHandleEntity();
            if (!Constants.HANDELSTATUS_REVIEW.equals(entity.getHandlelStatus())) {
                return new ApiResultJson(ApiResultJson.CODE_202,"该隐患已被其他人员处理, 请查看最新状态.",null);
            }
            String msg = "复查通过成功";
            if ("0".equals(checkStatus)) {
                msg = "退回成功";
                entity.setReviewMan(reviewMan);
                entity.setReviewDate(DateUtils.str2Date(reviewDate, DateUtils.date_sdf));
                entity.setReviewShift(reviewShift);
                entity.setReviewReport(reviewReport);
                entity.setReviewResult(checkStatus);
                entity.setModifyDate(null);
                entity.setModifyMan(null);
                entity.setModifyShift(null);
                entity.setRectMeasures(null);
                entity.setRollBackRemark(null);
                //复查不通过，退回整改
                entity.setHandlelStatus(Constants.HANDELSTATUS_ROLLBACK_CHECK);
            } else {
                entity.setReviewMan(reviewMan);
                entity.setReviewDate(DateUtils.str2Date(reviewDate, DateUtils.date_sdf));
                entity.setReviewShift(reviewShift);
                entity.setReviewReport(reviewReport);
                entity.setReviewResult(checkStatus);
                entity.setHandlelStatus(Constants.REVIEWSTATUS_PASS);
            }
            entity.setUpdateBy(user.getUserName());
            entity.setUpdateName(user.getRealName());
            entity.setUpdateDate(new Date());
            apiService.review(entity, limitDate);
            return new ApiResultJson(msg);
        } catch (Exception e) {
            LogUtil.error("隐患复查错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }


    @RequestMapping("/uploadImg")
    @ResponseBody
    public ApiResultJson uploadImg(String token, HttpServletRequest request, TSDocument document){
        try {
            String sessionId = request.getParameter("sessionId");
            String id = request.getParameter("id");

            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
                TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }

            TSType tsType = null;
            List<TSType> types = ResourceUtil.allTypes.get("fieltype".toLowerCase());
            if (types!=null && types.size()>0) {
                for (TSType tSType : types) {
                    if ("hiddenDangerImage".equals(tSType.getTypecode())) {
                        tsType = tSType;
                        document.setBusinessKey(tSType.getTypename());
                        break;
                    }
                }
            }
            document.setStatus(1);
            document.setSubclassname(MyClassLoader.getPackPath(document));
            document.setCreatedate(DateUtils.gettimestamp());
            document.setTSType(tsType);
            document.setDocumentTitle("隐患照片");

            document.setTSUser(user);

            UploadFile uploadFile = new UploadFile(request, document);
            uploadFile.setCusPath("files");
            uploadFile.setSwfpath("swfpath");
            systemService.uploadFile(uploadFile);
            return new ApiResultJson("照片上传成功");
        } catch (Exception e) {
            LogUtil.error("照片上传失败",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    //上报危害因素
    @RequestMapping("/addHazardFactors")
    @ResponseBody
    public ApiResultJson add(String token, String reportContent, HttpServletRequest request){
        //TODO token校验
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            JSONArray jsonArray = JSONArray.fromObject(reportContent);
            List<String> successIdsList = new ArrayList<String>();
            if (jsonArray !=null && jsonArray.size() > 0) {
                for (int i=0; i<jsonArray.size(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    HazardFactorsEntity entity = new HazardFactorsEntity();
                    entity.setRiskType(object.optString("riskType"));
                    entity.setMajor(object.optString("major"));
                    entity.setRiskLevel(object.optString("riskLevel"));
                    entity.setPostName(object.optString("postName"));
                    entity.setHazardFactors(object.optString("hazardFactors"));
                    entity.setManageMeasure(object.optString("manageMeasure"));
                    entity.setReviewMan(object.optString("reviewMan"));
                    entity.setEquipment(object.optString("equipment"));
                    entity.setActivity(object.optString("activity"));
                    entity.setDocSource(object.optString("docSource"));
                    entity.setSectionName(object.optString("sectionName"));
                    entity.setStatus("1");
                    entity.setIsDel("0");
                    entity.setCreateDate(new Date());
                    entity.setCreateName(user.getRealName());
                    entity.setCreateBy(user.getUserName());
                    String taskManageId = object.optString("taskId");
                    if(StringUtil.isNotEmpty(taskManageId)){
                        RiskManageTaskEntity riskManageTaskEntity = systemService.getEntity(RiskManageTaskEntity.class,taskManageId);
                        if(riskManageTaskEntity!=null){
                            entity.setFrom("riskManage_"+riskManageTaskEntity.getManageType());
                            entity.setSubmitMan(user.getId());
                            systemService.save(entity);
                            riskManageTaskEntity.setHandleStatus(com.sddb.common.Constants.RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED);
                            systemService.saveOrUpdate(riskManageTaskEntity);
                            String riskId = riskManageTaskEntity.getRisk().getId();
                            RiskIdentificationEntity t = systemService.get(RiskIdentificationEntity.class, riskId);
                            RiskFactortsRel rel = new RiskFactortsRel();
                            rel.setHazardFactorsEntity(entity);
                            rel.setRiskIdentificationEntity(t);
                            rel.setHfLevel(entity.getRiskLevel());
                            rel.setHfManageMeasure(entity.getManageMeasure());
                            systemService.save(rel);
                            String sql = "INSERT INTO t_b_risk_manage_hazard_factor VALUES ( '"+object.optString("id")+"', '"+riskManageTaskEntity.getId()+"', '"+riskManageTaskEntity.getTaskId()+"', '"+riskManageTaskEntity.getRisk().getId()+"', '"+riskManageTaskEntity.getManageType()+"', '"+entity.getId()+"', null, '0', null, '"+user.getUserName()+"', '"+user.getRealName()+"', now(), null, null, null );";
                            systemService.executeSql(sql);
                            RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,riskManageTaskEntity.getTaskAllId());
                            riskManageTaskAllEntity.setStatus("0");
                            systemService.saveOrUpdate(riskManageTaskAllEntity);
                        }
                    }
                }

            }
            return new ApiResultJson(ApiResultJson.CODE_200,ApiResultJson.CODE_200_MSG, successIdsList);
        } catch (Exception e) {
            LogUtil.error("危害因素上报错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }


    //落实危害因素
    @RequestMapping("/updateHazardFactors")
    @ResponseBody
    public ApiResultJson updateHazardFactors(String token, String reportContent, HttpServletRequest request){
        //TODO token校验
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            JSONArray jsonArray = JSONArray.fromObject(reportContent);
            List<String> successIdsList = new ArrayList<String>();
            if (jsonArray !=null && jsonArray.size() > 0) {
                for (int i=0; i<jsonArray.size(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    RiskManageHazardFactorEntity entity = this.systemService.getEntity(RiskManageHazardFactorEntity.class,object.optString("id"));
                    if(entity!=null){
                        entity.setImplDetail(object.optString("implDetail"));
                        entity.setHandleStatus(object.optString("handleStatus"));
                        entity.setRemark(object.optString("remark"));
                        this.systemService.saveOrUpdate(entity);
                        RiskManageTaskEntity task = entity.getRiskManageTaskEntity();
                        List<RiskManageHazardFactorEntity> riskManageHazardFactorEntityList = task.getRiskManageHazardFactorEntityList();
                        int numFinished = 0;
                        if(null!=riskManageHazardFactorEntityList && riskManageHazardFactorEntityList.size()>0){

                            for(int j=0;j<riskManageHazardFactorEntityList.size();j++){
                                if(StringUtil.isNotEmpty(riskManageHazardFactorEntityList.get(j).getImplDetail())){
                                    numFinished ++;
                                }
                            }
                        }
                        if(numFinished>=riskManageHazardFactorEntityList.size()){
                            task.setHandleStatus(com.sddb.common.Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
                            this.systemService.saveOrUpdate(task);
                            String sql = "SELECT id FROM t_b_risk_manage_task WHERE handle_status = '0' AND task_all_id = '"+task.getTaskAllId()+"'";
                            List<String> temp = this.systemService.findListbySql(sql);
                            if(temp==null||temp.size()==0){
                                RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,task.getTaskAllId());
                                riskManageTaskAllEntity.setStatus("1");
                                this.systemService.saveOrUpdate(riskManageTaskAllEntity);
                            }
                        }
                    }
                }
            }
            return new ApiResultJson(ApiResultJson.CODE_200,ApiResultJson.CODE_200_MSG, successIdsList);
        } catch (Exception e) {
            LogUtil.error("危害因素落实失败",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    //添加管控任务
    //总任务
    @RequestMapping("/addRiskManageTaskAll")
    @ResponseBody
    public ApiResultJson addRiskManageTaskAll(String token, String reportContent, HttpServletRequest request){
        //TODO token校验
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            JSONArray jsonArray = JSONArray.fromObject(reportContent);
            List<String> successIdsList = new ArrayList<String>();
            if (jsonArray !=null && jsonArray.size() > 0) {
                for (int i=0; i<jsonArray.size(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String sql = "INSERT INTO t_b_risk_manage_task_all VALUES ('"+object.getString("id")+"', '"+object.getString("manageType")+"', '"+object.optString("manageTime")+"', '"+object.getString("manageShift")+"', '"+object.getString("remark")+"', '0', '"+user.getUserName()+"', '"+user.getRealName()+"', now(), null, null, null)";
                    systemService.executeSql(sql);
                }
            }
            return new ApiResultJson(ApiResultJson.CODE_200,ApiResultJson.CODE_200_MSG, successIdsList);
        } catch (Exception e) {
            LogUtil.error("添加任务失败",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }
    //任务关联得风险
    @RequestMapping("/addRiskManageTask")
    @ResponseBody
    public ApiResultJson addRiskManageTask(String token, String reportContent, HttpServletRequest request){
        //TODO token校验
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            JSONArray jsonArray = JSONArray.fromObject(reportContent);
            List<String> successIdsList = new ArrayList<String>();
            if (jsonArray !=null && jsonArray.size() > 0) {
                for (int i=0; i<jsonArray.size(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String sql = "INSERT INTO t_b_risk_manage_task VALUES ('"+object.getString("taskId")+"', '"+object.getString("riskManageId")+"', '"+object.getString("sameId")+"', '"+object.getString("riskId")+"', '"+object.getString("manageType")+"', '0', '"+object.getString("taskAllId")+"', '"+user.getUserName()+"', '"+user.getRealName()+"', now(), null, null, null)";
                    systemService.executeSql(sql);
                }
            }
            return new ApiResultJson(ApiResultJson.CODE_200,ApiResultJson.CODE_200_MSG, successIdsList);
        } catch (Exception e) {
            LogUtil.error("添加任务失败",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }
    //风险关联的危害因素
    @RequestMapping("/addRiskManageHazardFactor")
    @ResponseBody
    public ApiResultJson addRiskManageHazardFactor(String token, String reportContent, HttpServletRequest request){
        //TODO token校验
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            JSONArray jsonArray = JSONArray.fromObject(reportContent);
            List<String> successIdsList = new ArrayList<String>();
            if (jsonArray !=null && jsonArray.size() > 0) {
                for (int i=0; i<jsonArray.size(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    String sql ="INSERT INTO t_b_risk_manage_hazard_factor VALUES ('"+object.getString("riskManageHazardFactorId")+"', '"+object.getString("taskId")+"', '"+object.getString("sameId")+"', '"+object.getString("riskId")+"', '"+object.getString("manageType")+"', '"+object.getString("hazardFactorId")+"', null, '0', null, '"+user.getUserName()+"', '"+user.getRealName()+"', now(), null, null, null)";
                    systemService.executeSql(sql);
                }
            }
            return new ApiResultJson(ApiResultJson.CODE_200,ApiResultJson.CODE_200_MSG, successIdsList);
        } catch (Exception e) {
            LogUtil.error("添加任务失败",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

}