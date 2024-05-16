package com.sdzk.buss.api.controller;

import com.sddb.buss.riskmanage.entity.RiskManageHazardFactorEntity;
import com.sdzk.buss.api.model.ApiResultJson;
import com.sdzk.buss.api.service.ApiServiceI;
import com.sdzk.buss.api.utils.Base64Util;
import com.sdzk.buss.api.utils.CreateFileUtil;
import com.sdzk.buss.api.utils.ReduceImgUtil;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.hiddendanger.entity.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.common.UploadFile;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.pojo.base.*;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @user xuran
 * 隐患查询,接收录入数据,整改复查接口
 */
@Controller
@RequestMapping("/mobile/mobileHiddenDangerController")
public class MobileHiddenDangerController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiServiceI apiService;

    private String sessionCache="sessionCache";

    private static final String QUERYTYPE_COMMITED="commited";
    private static final String QUERYTYPE_PENDING_RECTIFY="pendingRectify";
    private static final String QUERYTYPE_RECTIFIED="rectified";
    private static final String QUERYTYPE_PENDING_REVIEW="pendingReview";
    private static final String QUERYTYPE_REVIEWED="reviewed";
    private static final String QUERYTYPE_TIMEOUT="timeout";


    /**
     *
     * @param token
     * param sessionId     用户当前登录的sessionid
     * param queryType     查询类型
     * param page          当前页数
     * param rows          查询行数
     * @return
     */
    @RequestMapping("/departList")
    @ResponseBody
    public ApiResultJson departList(String token, HttpServletRequest request){
        //TODO TOKEN验证
        try {
            String sessionId = request.getParameter("sessionId");
            String queryType = request.getParameter("queryType");
            String addressId = request.getParameter("addressId");
            String page = request.getParameter("page");
            String rows = request.getParameter("rows");
            //李楼的二维码扫描依据
            String ewmgn=ResourceUtil.getConfigByName("ewmgn");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            if (StringUtils.isBlank(queryType)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"查询类型不能为空",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            List<Map<String, Object>> result=null;
            StringBuffer sql = new StringBuffer("SELECT distinct e.duty_unit as dutyUnit FROM t_b_hidden_danger_exam e JOIN t_b_hidden_danger_handle h ON e.id = h.hidden_danger_id JOIN t_s_depart d ON e.duty_unit = d.ID left join t_b_address_info ai on ai.id = e.address LEFT JOIN t_b_risk_identification ri ON ri.id = e.risk_id  left join (select ds.id,h.hazard_name from t_b_danger_source ds join t_b_hazard_manage h on ds.hazard_manage_id=h.id) hm on hm.id = e.danger_id left join t_s_base_user mMan on mMan.id = h.modify_man left join t_s_base_user rMan on rMan.id = h.review_man ");
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
            if (QUERYTYPE_COMMITED.equals(queryType)) {//录入人查看已提交的数据, 查询条件-当前登录人创建的记录
                sql.append(" where h.handlel_status != '00' and e.create_by = '"+user.getUserName()+"' ");

            } else if(QUERYTYPE_PENDING_RECTIFY.equals(queryType)){//待整改页面查询责任单位为当前登录人所属单位的所有待整改及复查退回的记录
                sql.append(" where h.handlel_status in ('1','4') ");
                if (!"admin".equals(user.getUserName())&&!isAdmin) {
                    sql.append(" and e.duty_unit = '"+user.getTSDepart().getId()+"' ");
                }
                if("true".equals(ewmgn) && StringUtils.isNotBlank(addressId)){
                    sql.append(" and e.address='"+addressId+"'");
                }
            } else if (QUERYTYPE_RECTIFIED.equals(queryType)) {//已整改页面查询责任单位为当前登录人所属单位的所有待复查及已复查的记录
                sql.append(" where h.handlel_status in ('3','5') ");
                if (!"admin".equals(user.getUserName())&&!isAJY&&!isAdmin) {
                    sql.append(" and e.duty_unit = '"+user.getTSDepart().getId()+"' ");
                }
                if("true".equals(ewmgn) && StringUtils.isNotBlank(addressId)){
                    sql.append(" and e.address='"+addressId+"'");
                }
            } else if (QUERYTYPE_PENDING_REVIEW.equals(queryType)) {//待复查页面显示状态为待复查且当前登录人在录入人/检查人相同部门的记录
                sql.append(" where h.handlel_status = '3' ");
                if (!"admin".equals(user.getUserName())&&!isAJY&&!isAdmin) {
                    sql.append(" and (EXISTS ( select * from t_s_base_user u, t_s_user_org uo\n" +
                            " where uo.user_id=u.id and uo.org_id='" + user.getTSDepart().getId() + "'\n" +
                            " and (u.username=e.create_by or FIND_IN_SET(u.id,e.fill_card_manids)) ) or  e.manage_duty_unit = '" + user.getTSDepart().getId() + "' or e.manage_duty_man_id = '" + user.getId() + "')");
                }
                if("true".equals(ewmgn) && StringUtils.isNotBlank(addressId)){
                    sql.append(" and e.address='"+addressId+"'");
                }
            } else if (QUERYTYPE_REVIEWED.equals(queryType)) {//已复查页面显示状态为已复查且当前登录人在录入人/检查人相同部门的记录
                sql.append(" where h.handlel_status = '5' ");
                if (!"admin".equals(user.getUserName())&&!isAJY&&!isAdmin) {
                    sql.append(" and (EXISTS ( select * from t_s_base_user u, t_s_user_org uo\n" +
                            " where uo.user_id=u.id and uo.org_id='" + user.getTSDepart().getId() + "'\n" +
                            " and (u.username=e.create_by or FIND_IN_SET(u.id,e.fill_card_manids)) ) or  e.manage_duty_unit = '" + user.getTSDepart().getId() + "' or e.manage_duty_man_id = '" + user.getId() + "')");
                }
            } else if (QUERYTYPE_TIMEOUT.equals(queryType)) {//超期未整改
                sql.append(" where h.handlel_status in ('1','4') and e.limit_date < curdate() and e.duty_unit = '"+user.getTSDepart().getId()+"'");
            } else {
                return new ApiResultJson(ApiResultJson.CODE_202,"查询类型不存在",null);
            }


            if (QUERYTYPE_RECTIFIED.equals(queryType)){
                sql.append(" order by h.update_date desc ");
            } else {
                sql.append(" order by e.exam_date desc");
            }
            result = systemService.findForJdbc(sql.toString(), Integer.parseInt(page), Integer.parseInt(rows));
            if (result != null && result.size() > 0) {
                for (Map<String, Object> map: result) {
                    TSDepart depart=systemService.getEntity(TSDepart.class,map.get("dutyUnit").toString());
                    map.put("id", depart.getId());
                    map.put("dutyUnitName", depart.getDepartname());
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
     * param sessionId     用户当前登录的sessionid
     * param id            隐患id
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
                //result.put("fillCardMan", examEntity.getFillCardMan()!=null?examEntity.getFillCardMan().getRealName():"");
//                result.put("fillCardMan", examEntity.getFillCardMan()!=null?examEntity.getFillCardMan():"");
                if (StringUtils.isNotBlank(examEntity.getFillCardManId())) {
                    List<Map<String, Object>> userList = systemService.findForJdbc("select realname from t_s_base_user where id in ('"+examEntity.getFillCardManId().replace(",","','")+"')");
                    if (userList != null && userList.size() > 0) {
                        StringBuffer username = new StringBuffer();
                        for (Map<String, Object> userMap : userList) {
                            if (StringUtils.isNoneBlank(username)) {
                                username.append(",");
                            }
                            username.append(userMap.get("realname"));
                        }
                        result.put("fillCardMan", username.toString());
                    } else {
                        result.put("fillCardMan", "");
                    }
                } else {
                    result.put("fillCardMan", "");
                }
                result.put("hazardName", examEntity.getDangerId()!=null&&examEntity.getDangerId().getHazard()!=null?examEntity.getDangerId().getHazard().getHazardName():"");
                result.put("dutyUnit", examEntity.getDutyUnit()!=null?examEntity.getDutyUnit().getDepartname():"");
                result.put("dutyMan", examEntity.getDutyMan());
                result.put("hiddenCategory", DicUtil.getTypeNameByCode("hiddenCate", examEntity.getHiddenCategory()));
                result.put("hiddenNature", DicUtil.getTypeNameByCode("hiddenLevel", examEntity.getHiddenNature()));
                result.put("hiddenType", DicUtil.getTypeNameByCode("hiddenType", examEntity.getHiddenType()));
                result.put("riskType",DicUtil.getTypeNameByCode("risk_type", examEntity.getRiskType()));
                result.put("problemDesc", examEntity.getProblemDesc());
                result.put("examType", DicUtil.getTypeNameByCode("examType", examEntity.getExamType()));
                result.put("dealType", "1".equals(examEntity.getDealType())?"限期整改":"现场处理");
                result.put("limitDate", examEntity.getLimitDate()!=null?DateUtils.date2Str(examEntity.getLimitDate(), DateUtils.date_sdf):"");
                result.put("examDate", examEntity.getExamDate()!=null?DateUtils.date2Str(examEntity.getExamDate(), DateUtils.date_sdf):"");
                result.put("shift", DicUtil.getTypeNameByCode("workShift", examEntity.getShift()));
                result.put("manageType",DicUtil.getTypeNameByCode("manageType",examEntity.getManageType()));
                if(StringUtil.isNotEmpty(examEntity.getRiskId())&&StringUtils.isNotBlank(examEntity.getRiskId().getRiskDesc())){
                    result.put("riskResult",examEntity.getRiskId().getRiskDesc());
                }else {
                    result.put("riskResult","");
                }
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

                //组装图片路径报文,格式[{'imgPath':'uploadfile/402880f7604322680160433e7e6a0009/1.jpg','reduceImgPath','uploadfile/402880f7604322680160433e7e6a0009/reduce_1.jpg'},{}]
                JSONArray fileContent = new JSONArray();
                String imgSql = "select img_path from t_b_hidden_danger_img_rel where mobile_hidden_id='"+examEntity.getMobileId()+"'";
                List<String> imgPathList = systemService.findListbySql(imgSql);
                if(!imgPathList.isEmpty() && imgPathList.size()>0){
                    for(String imgPath:imgPathList){
                        String dir = "uploadfile/"+examEntity.getMobileId()+"/";
                        JSONObject jo = new JSONObject();
                        jo.put("imgPath",dir + imgPath);
                        jo.put("reduceImgPath",dir + "reduce_" + imgPath);

                        fileContent.add(jo);
                    }
                }
                result.put("imgContents",fileContent);

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
     * param sessionId         用户当前登录的sessionid
     * param id                手机端主键
     * param examType          检查类型(对应字典值 examType)
     * param examDate          检查时间
     * param shift             班次(对应字典值 workShift)
     * param addressId         地点id
     * param fillCardManId     检查人id
     * param dutyUnitId        责任部门id
     * param dutyMan           责任人姓名
     * param hiddenCategory    隐患类别(对应字典值 hiddenCate)
     * param hiddenNature      隐患等级(对应字典值 hiddenLevel)
     * param riskType          隐患类型(对应字典值 risk_type)
     * param problemDesc       问题描述
     * param dealType          处理方式-1=限期整改;2=现场整改
     * param reviewManId       复查人id(处理方式为现场整改必填)
     * param limitDate         限期日期(处理方式为限期整改必填)
     * param manageType        信息来源(必填)
     * param riskId            风险ID
     * param
     * @return
     */
    @RequestMapping("/add")
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
                List<MobileTBHiddenDangerExamEntity> entities = new ArrayList<>();
                for (int i=0; i<jsonArray.size(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                        MobileTBHiddenDangerExamEntity entity = new MobileTBHiddenDangerExamEntity();
                        entity.setMobileId(object.optString("id"));
                        entity.setExamDate(DateUtils.str2Date(object.optString("examDate"), DateUtils.date_sdf));
                        entity.setShift(object.optString("shift"));
                        entity.setAddress(object.optString("addressId"));
                        entity.setFillCardMan(object.optString("fillCardManId"));
                        entity.setDutyUnit(object.optString("dutyUnitId"));
                        entity.setDutyMan(object.optString("dutyMan"));
                        entity.setHiddenCategory(object.optString("hiddenCategory"));
                        entity.setHiddenNature(object.optString("hiddenNature"));
                        //entity.setHiddenType(object.optString("hiddenType"));
                        entity.setRiskType(object.optString("riskType"));
                        entity.setTaskAllId(object.optString("taskAllId"));
                        entity.setManageDutyManId(object.optString("manageDutyManId"));
                        entity.setManageDutyUnit(object.optString("manageDutyUnitId"));
                        if(StringUtil.isNotEmpty(object.optString("riskId"))){
                            entity.setRiskId(object.optString("riskId"));
                        }else{
                            entity.setRiskId(null);
                        }
                        entity.setManageType(object.optString("manageType"));
                        entity.setProblemDesc(object.optString("problemDesc"));
                        entity.setDealType(object.optString("dealType"));
                        entity.setHiddenNatureOriginal(entity.getHiddenNature());
                        entity.setIsLsProv(Constants.HDBIISLS_STATE_UNDO);
                        entity.setIsLsSub(Constants.HDBIISLS_STATE_UNDO);
                        entity.setReportStatus(Constants.REPORT_STATUS_N);
                        entity.setCreateBy(user.getUserName());
                        entity.setCreateName(user.getRealName());
                        entity.setCreateDate(new Date());
                        //管控新增隐患
                        String riskManageHazardFactorId = object.optString("riskManageHazardFactorId");
                        if(StringUtil.isNotEmpty(riskManageHazardFactorId)){
                            entity.setExamType(object.optString("examType"));
                            entity.setRiskManageHazardFactorId(riskManageHazardFactorId);
                            if(StringUtil.isNotEmpty(riskManageHazardFactorId)){
                                RiskManageHazardFactorEntity riskManageHazardFactorEntity = this.systemService.getEntity(RiskManageHazardFactorEntity.class,riskManageHazardFactorId);
                                if(riskManageHazardFactorEntity!=null){
                                    if(riskManageHazardFactorEntity.getRisk()!=null){
                                        entity.setRiskId(riskManageHazardFactorEntity.getRisk().getId());
                                    }
                                }
                            }
                        }else{
                            entity.setExamType("yh");
                            entity.setRiskManageHazardFactorId(null);
                        }
                    String huayuan = ResourceUtil.getConfigByName("huayuan");
                    if(huayuan.equals("true")){
                        String hiddenNumber = "";
                        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
                        TBCount countEntity = systemService.get(TBCount.class,"1");
                        String countNum = countEntity.getCount();
                        if(countNum.equals("1000")){
                            countEntity.setCount("1");
                        }else{
                            Integer countTemp = Integer.valueOf(countNum);
                            countTemp++;
                            countEntity.setCount(String.valueOf(countTemp));
                        }
                        systemService.saveOrUpdate(countEntity);
                        while(countNum.length() < 4){
                            countNum = "0" + countNum;
                        }
                        String temp = sdf.format(new Date());
                        hiddenNumber = temp+countNum;
                        entity.setHiddenNumber(hiddenNumber);
                    }
                        MobileTBHiddenDangerHandleEntity handleEntity = new MobileTBHiddenDangerHandleEntity();
//                    handleEntity.setHiddenDanger(entity);
                        handleEntity.setHandlelStatus(Constants.HANDELSTATUS_REPORT);
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
                            if(StringUtil.isNotEmpty(object.optString("rectMeasures"))){
                                handleEntity.setRectMeasures(object.optString("rectMeasures"));
                            }else{
                                handleEntity.setRectMeasures(null);
                            }
                            if(StringUtil.isNotEmpty(object.optString("reviewReport"))){
                                handleEntity.setReviewReport(object.optString("reviewReport"));
                            }else{
                                handleEntity.setReviewReport(null);
                            }
                            handleEntity.setHandlelStatus(Constants.REVIEWSTATUS_PASS);
                        } else {//限期整改,改为草稿状态
                            entity.setReviewMan(null);
                            entity.setLimitDate(DateUtils.str2Date(object.optString("limitDate"), DateUtils.date_sdf));
                        }
                        entity.setHandleEntity(handleEntity);

                        //根据隐患描述自动关联风险
                        /*if (StringUtil.isNotEmpty(entity.getProblemDesc()) && StringUtil.isEmpty(entity.getDangerId())) {
                            if (false == SemanticSimilarityUtil.getDSDartDone()) {
                                handleEntity.setHandlelStatus(Constants.HANDELSTATUS_REPORT);
                                LogUtil.log("隐患自动关联风险失败", "风险描述分词尚未完成，暂无法进行关联");
                            } else {
                                String sql = "select ds.id from t_b_danger_source ds, t_b_danger_address_rel rel where ds.id=rel.danger_id\n" +
                                        " and ds.is_delete!='1' and ds.audit_status='4' and rel.address_id='" + entity.getAddress() + "'";
                                List<String> dsList = systemService.findListbySql(sql);
                                if (null != dsList && dsList.size() > 0) {
                                    Map<String, Vector<String>> dsPartMap = new HashMap<>();
                                    for (int j = 0; j < dsList.size(); j++) {
                                        String dsId = dsList.get(j);
                                        dsPartMap.put(dsId, SemanticSimilarityUtil.dsPartMap.get(dsId));
                                    }
                                    String dsId = SemanticSimilarityUtil.getMatchedDangerSourceId(entity.getProblemDesc(), dsPartMap);
                                    if (StringUtil.isNotEmpty(dsId)) {
                                        entity.setDangerId(dsId);
                                        handleEntity.setHandlelStatus(Constants.HANDELSTATUS_REPORT);
                                    } else {
                                        handleEntity.setHandlelStatus(Constants.HANDELSTATUS_REPORT);
                                        LogUtil.log("隐患自动关联风险失败", "隐患描述未匹配到风险，状态置为(草稿)，仍然上报");
                                    }
                                } else {
                                    handleEntity.setHandlelStatus(Constants.HANDELSTATUS_REPORT);
                                    LogUtil.log("隐患自动关联风险失败", "风险点未关联风险");
                                }
                            }
                        }*/
                    if (checkExistHiddenDanger(entity.getMobileId())) {
                        updateMobileHiddenDanger(entity.getMobileId(),entity);
                        successIdsList.add(entity.getMobileId());
                    } else {
                        entities.add(entity);
                        successIdsList.add(entity.getMobileId());
                    }
                }
                systemService.batchSave(entities);
                if(entities.size()>0&&entities!=null){
                    for(MobileTBHiddenDangerExamEntity bean:entities){
                        if(StringUtil.isNotEmpty(bean.getRiskManageHazardFactorId())){
                            MobileRiskManageRelHd beanTemp = new MobileRiskManageRelHd();
                            beanTemp.setRiskManageHazardFactor(bean.getRiskManageHazardFactorId());
                            beanTemp.setHd(bean.getId());
                            systemService.save(beanTemp);
                        }
                    }
                }
            }
            return new ApiResultJson(ApiResultJson.CODE_200,ApiResultJson.CODE_200_MSG, successIdsList);
        } catch (Exception e) {
            LogUtil.error("隐患上报错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    @RequestMapping("/addTask")
    @ResponseBody
    public ApiResultJson addTask(String token, String reportContent, HttpServletRequest request){
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
                    String id = object.optString("id");
                    String manageTime = object.optString("manageTime");
                    String manageType = object.optString("manageType");
                    String manageShift = object.optString("manageShift");
                    String remark = object.optString("remark");
                    String sql = "INSERT INTO t_b_risk_manage_task_all VALUES ('"+id+"','"+manageType+"','"+manageTime+"','"+manageShift+"','"+remark+"','0','"+user.getUserName()+"','"+user.getRealName()+"',now(),null,null,null,null)";
                    try {
                        systemService.executeSql(sql);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    successIdsList.add(id);
                }
            }
            return new ApiResultJson(ApiResultJson.CODE_200,ApiResultJson.CODE_200_MSG, successIdsList);
        } catch (Exception e) {
            LogUtil.error("任务上报失败",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }


    @RequestMapping("/endTask")
    @ResponseBody
    public ApiResultJson endTask(String token, String reportContent, HttpServletRequest request){
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
                    String id = object.optString("id");
                    String sql = "update t_b_risk_manage_task_all SET status = '1' WHERE id = '"+id+"'";
                    systemService.executeSql(sql);
                    successIdsList.add(id);
                }
            }
            return new ApiResultJson(ApiResultJson.CODE_200,ApiResultJson.CODE_200_MSG, successIdsList);
        } catch (Exception e) {
            LogUtil.error("任务结束失败",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    private boolean checkExistHiddenDanger(String mobileId){
        boolean flag = false;
        CriteriaQuery cq = new CriteriaQuery(MobileTBHiddenDangerExamEntity.class);
        cq.eq("mobileId",mobileId);
        cq.add();
        List<MobileTBHiddenDangerExamEntity> resultList = systemService.getListByCriteriaQuery(cq,false);
        if(resultList.size()>0&&resultList!=null){
            flag = true;
        }
        return flag;
    }

    private void updateMobileHiddenDanger(String mobileId,MobileTBHiddenDangerExamEntity baseEntity) throws  Exception{
        CriteriaQuery cq = new CriteriaQuery(MobileTBHiddenDangerExamEntity.class);
        cq.eq("mobileId",mobileId);
        cq.add();
        List<MobileTBHiddenDangerExamEntity> resultList = systemService.getListByCriteriaQuery(cq,false);
        for(MobileTBHiddenDangerExamEntity toEntity : resultList){
            MyBeanUtils.copyBeanNotNull2Bean(baseEntity.getHandleEntity(),toEntity.getHandleEntity());
            baseEntity.setHandleEntity(toEntity.getHandleEntity());
            MyBeanUtils.copyBeanNotNull2Bean(baseEntity,toEntity);
            toEntity.getHandleEntity().getHiddenDanger().setId(toEntity.getId());
            systemService.saveOrUpdate(toEntity);
        }
    }

    /**
     * 整改
     * @param token
     * param sessionId             用户当前登录的sessionId
     * param id                    隐患id
     * param checkStatus           0=驳回;1=整改完成
     * param modifyDate            整改时间
     * param modifyMan             整改人
     * param modifyShift           整改班次
     * param rectMeasures          整改情况
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
            String rollBackRemark=request.getParameter("rollBackRemark");
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
                entity.setRollBackRemark(rollBackRemark);
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
     * param sessionId                 用户当前登录的sessionid
     * param id                        隐患id
     * param checkStatus               复查状态-0=复查不通过;1=复查通过
     * param limitDate                 限期日期(复查不通过是必填)
     * param reviewMan                 复查人
     * param reviewDate                复查时间
     * param reviewShift               复查班次
     * param reviewReport              复查情况
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

    @RequestMapping("/taskList")
    @ResponseBody
    public ApiResultJson taskList(String token, HttpServletRequest request, TSDocument document){
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

            return apiService.hiddenDangerTaskCount(user);
        } catch (Exception e) {
            LogUtil.error("获取待办任务失败",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }

    /**
     * 文件上传
     *
     * @param hiddenId    隐患ID
     * @param fileExtend  文件扩展名名
     * @param fileContent 文件base64字符
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public ApiResultJson uploadFile(String hiddenId, String fileExtend, String fileContent, HttpServletRequest request) {
        try {
            //测试暂时注掉
            /*String sessionId = request.getParameter("sessionId");

            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }*/

            if (StringUtils.isNotBlank(hiddenId) && StringUtils.isNotBlank(fileExtend) && StringUtils.isNotBlank(fileContent)) {
                String sql = "select count(id) from t_b_hidden_danger_exam where mobile_id='" + hiddenId + "'";
                List<BigInteger> temp = systemService.findListbySql(sql);
                if (temp.isEmpty() || temp.size() == 0 || temp.get(0).intValue() == 0) {
                    return new ApiResultJson(ApiResultJson.CODE_202, "隐患不存在", null);
                }

                //重命名文件
                String fileName = getNewFileName() + "." + fileExtend;

                //创建文件夹
                String dir = ResourceUtil.getConfigByName("mobileHiddenImgPath") + "//" + hiddenId;
                CreateFileUtil.createDir(dir);

                //保存文件到本地
                Base64Util.decoderBase64File(fileContent, dir + "//" + fileName);

                //图片压缩
                File srcFile = new File(dir + "//" + fileName);
                ReduceImgUtil.reduceImg(dir + "//" + fileName, dir + "//reduce_" + fileName, ReduceImgUtil.getReduceWidthAndHeight(100, srcFile)[0], ReduceImgUtil.getReduceWidthAndHeight(100, srcFile)[1], null);

                //保存关联关系
                TBHiddenDangerImgRelEntity rel = new TBHiddenDangerImgRelEntity();
                rel.setMobileHiddenId(hiddenId);
                rel.setImgPath(fileName);
                systemService.save(rel);
                return new ApiResultJson(ApiResultJson.CODE_200, "文件上传成功", null);
            } else {
                return new ApiResultJson(ApiResultJson.CODE_400, ApiResultJson.CODE_400_MSG, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResultJson(ApiResultJson.CODE_500, ApiResultJson.CODE_500_MSG+"："+e.getMessage(), null);
        }
    }

    /**
     * 多文件上传
     *
     * @param hiddenId          隐患ID
     * @param fileContent       文件报文
     */
    @RequestMapping("/uploadMultipleFiles")
    @ResponseBody
    public ApiResultJson uploadMultipleFiles(String hiddenId, String fileContent,HttpServletRequest request) throws IOException {
        String sessionId = request.getParameter("sessionId");
        if (StringUtil.isEmpty(hiddenId)){
            hiddenId = request.getParameter("hiddenId");
        }
        if (StringUtil.isEmpty(fileContent)){
            fileContent = request.getParameter("fileContent");
        }

        try {
            //测试暂时注掉
            /*String sessionId = request.getParameter("sessionId");

            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }*/

            if (StringUtils.isNotBlank(hiddenId)) {
                String sql = "select count(id) from t_b_hidden_danger_exam where mobile_id='" + hiddenId + "'";
                List<BigInteger> temp = systemService.findListbySql(sql);
                if (temp.isEmpty() || temp.size() == 0 || temp.get(0).intValue() == 0) {
                    return new ApiResultJson(ApiResultJson.CODE_202, "隐患不存在", null);
                }

                JSONArray jsonArray = JSONArray.fromObject(fileContent);
                if (jsonArray !=null && jsonArray.size() > 0) {
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);

                        String fileExtend = object.getString("fileExtend");
                        String filebase64 = object.getString("filebase64");

                        if (StringUtils.isNotBlank(fileExtend) && StringUtils.isNotBlank(filebase64)) {
                            //重命名文件
                            String fileName = getNewFileName() + "." + fileExtend;

                            //创建文件夹
                            String dir = ResourceUtil.getConfigByName("mobileHiddenImgPath") + "//" + hiddenId;
                            CreateFileUtil.createDir(dir);

                            //保存文件到本地
                            Base64Util.decoderBase64File(filebase64, dir + "//" + fileName);

                            //图片压缩
                            File srcFile = new File(dir + "//" + fileName);
                            ReduceImgUtil.reduceImg(dir + "//" + fileName, dir + "//reduce_" + fileName, ReduceImgUtil.getReduceWidthAndHeight(100, srcFile)[0], ReduceImgUtil.getReduceWidthAndHeight(100, srcFile)[1], null);

                            //保存关联关系
                            TBHiddenDangerImgRelEntity rel = new TBHiddenDangerImgRelEntity();
                            rel.setMobileHiddenId(hiddenId);
                            rel.setImgPath(fileName);
                            systemService.save(rel);
                        }
                    }
                }
                return new ApiResultJson(ApiResultJson.CODE_200, "文件上传成功", null);
            } else {
                return new ApiResultJson(ApiResultJson.CODE_400, ApiResultJson.CODE_400_MSG, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResultJson(ApiResultJson.CODE_500, ApiResultJson.CODE_500_MSG+"："+e.getMessage(), null);
        }
    }

    private static String getNewFileName() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @RequestMapping("/getHiddenNum")
    @ResponseBody
    public ApiResultJson getHiddenNum(HttpServletRequest request){
        String sessionId = request.getParameter("sessionId");
        if (StringUtils.isBlank(sessionId)) {
            return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
        }
        TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
        if (user == null) {
            return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
        }
        boolean isAdmin = false;
        boolean isAJY= false;
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
            for(TSRoleUser ru : roleList){
                TSRole role = ru.getTSRole();
                if(role != null && role.getRoleName().equals("安监员")){
                    isAJY = true;
                    break;
                }
            }
        }
        Map<String,Object> map=new HashMap<>();

        String sql="SELECT COUNT(id) FROM t_b_hidden_danger_handle WHERE handlel_status IN ('1','4')";
        String sql2="SELECT COUNT(id) FROM t_b_hidden_danger_handle WHERE handlel_status ='3'";
        if(!isAdmin){
            sql=" SELECT\n" +
                    "\tCOUNT(t1.id)\n" +
                    "FROM\n" +
                    "\tt_b_hidden_danger_handle t1\n" +
                    " JOIN t_b_hidden_danger_exam t2 on t1.hidden_danger_id = t2.id \n" +
                    "WHERE\n" +
                    "\tt1.handlel_status IN ('1', '4')\n" +
                    " and t2.duty_unit = '" +user.getCurrentDepart().getId()+"' "+
                    "and t2.duty_man =  '" +user.getRealName()+"' ";
        }
        if (!isAdmin&&!isAJY) {
            sql2 = " SELECT\n" +
                    "\tCOUNT(h.id)\n" +
                    "FROM\n" +
                    "\tt_b_hidden_danger_handle h\n" +
                    "join t_b_hidden_danger_exam e on h.hidden_danger_id = e.id\n" +
                    "WHERE\n" +
                    "\th.handlel_status = '3' and (EXISTS (\t\n" +
                    " select * from t_s_base_user u, t_s_user_org uo\n" +
                    " where uo.user_id=u.id and uo.org_id='" + user.getCurrentDepart().getId() + "'\n" +
                    " and (u.username=e.create_by or FIND_IN_SET(u.id,e.fill_card_manids) or FIND_IN_SET(u.realName,REPLACE(e.fill_card_manids,' ',',')) or u.id=e.review_man) or  e.manage_duty_unit = '" + user.getCurrentDepart().getId() + "' or e.manage_duty_man_id = '" + user.getId() + "') \n" +
                    "\t)";
        }

        Long count = systemService.getCountForJdbc(sql);
        map.put("dzg",count);
        Long count2 = systemService.getCountForJdbc(sql2);
        map.put("dfc",count2);
        return new ApiResultJson(map);
    }

    @RequestMapping("/list")
    @ResponseBody
    public ApiResultJson list(String token, HttpServletRequest request){
        //TODO TOKEN验证
        try {
            String sessionId = request.getParameter("sessionId");
            String queryType = request.getParameter("queryType");
            String addressId = request.getParameter("addressId");
            String page = request.getParameter("page");
            String rows = request.getParameter("rows");
            String departId = request.getParameter("departId");
            //李楼的二维码扫描依据
            String ewmgn=ResourceUtil.getConfigByName("ewmgn");
            if (StringUtils.isBlank(sessionId)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            if (StringUtils.isBlank(queryType)) {
                return new ApiResultJson(ApiResultJson.CODE_202,"查询类型不能为空",null);
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ApiResultJson(ApiResultJson.CODE_202,"用户未登录",null);
            }
            List<Map<String, Object>> result=null;
            StringBuffer sql = new StringBuffer("SELECT e.risk_type riskType,e.manage_type manageType,e.itemId item, e.itemUserId itemUser, e.sjjc_check_man sjjcCheckMan, e.sjjc_dept sjjcDept, h.roll_back_remark rollBackRemark, e.id, ai.address,ri.risk_desc riskDesc,  e.fill_card_manids fillCardMan, hm.hazard_name hazardName, e.duty_man dutyMan, e.hidden_category hiddenCategory, e.hidden_nature hiddenNature, e.hidden_type hiddenType, e.deal_type dealType, e.shift, mMan.realname modifyMan, DATE_FORMAT(h.modify_date, '%Y-%m-%d') modifyDate, h.modify_shift modifyShift, h.rect_measures rectMeasures, rMan.realname reviewMan, DATE_FORMAT(h.review_date, '%Y-%m-%d') reviewDate, h.review_shift reviewShift, h.review_report reviewReport, h.review_result reviewResult, e.exam_type examType, d.departname dutyUnit, DATE_FORMAT(e.exam_date, '%Y-%m-%d') examDate, DATE_FORMAT(e.limit_date, '%Y-%m-%d') limitDate, e.problem_desc problemDesc, h.handlel_status handelStatus, CASE WHEN e.mobile_id IS NOT NULL THEN '移动端' ELSE '手机端' END pcOrMobile FROM t_b_hidden_danger_exam e JOIN t_b_hidden_danger_handle h ON e.id = h.hidden_danger_id JOIN t_s_depart d ON e.duty_unit = d.ID left join t_b_address_info ai on ai.id = e.address LEFT JOIN t_b_risk_identification ri ON ri.id = e.risk_id  left join (select ds.id,h.hazard_name from t_b_danger_source ds join t_b_hazard_manage h on ds.hazard_manage_id=h.id) hm on hm.id = e.danger_id left join t_s_base_user mMan on mMan.id = h.modify_man left join t_s_base_user rMan on rMan.id = h.review_man ");
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
            if (QUERYTYPE_COMMITED.equals(queryType)) {//录入人查看已提交的数据, 查询条件-当前登录人创建的记录
                sql.append(" where h.handlel_status != '00' and e.create_by = '"+user.getUserName()+"' ");

            } else if(QUERYTYPE_PENDING_RECTIFY.equals(queryType)){//待整改页面查询责任单位为当前登录人所属单位的所有待整改及复查退回的记录
                sql.append(" where h.handlel_status in ('1','4') ");

                    if (!"admin".equals(user.getUserName())&&!isAdmin) {
                        sql.append(" and e.duty_unit = '"+user.getTSDepart().getId()+"' ");
                    }
                if("true".equals(ewmgn) && StringUtils.isNotBlank(addressId)){
                    sql.append(" and e.address='"+addressId+"'");
                }
            } else if (QUERYTYPE_RECTIFIED.equals(queryType)) {//已整改页面查询责任单位为当前登录人所属单位的所有待复查及已复查的记录
                sql.append(" where h.handlel_status in ('3','5') ");
                if (!"admin".equals(user.getUserName())&&!isAJY&&!isAdmin) {
                    sql.append(" and e.duty_unit = '"+user.getTSDepart().getId()+"' ");
                }
                if("true".equals(ewmgn) && StringUtils.isNotBlank(addressId)){
                    sql.append(" and e.address='"+addressId+"'");
                }
            } else if (QUERYTYPE_PENDING_REVIEW.equals(queryType)) {//待复查页面显示状态为待复查且当前登录人在录入人/检查人相同部门的记录
                sql.append(" where h.handlel_status = '3' ");
                if (!"admin".equals(user.getUserName())&&!isAJY&&!isAdmin) {
                    sql.append(" and (EXISTS ( select * from t_s_base_user u, t_s_user_org uo\n" +
                            " where uo.user_id=u.id and uo.org_id='" + user.getTSDepart().getId() + "'\n" +
                            " and (u.username=e.create_by or FIND_IN_SET(u.id,e.fill_card_manids)) ) or  e.manage_duty_unit = '" + user.getTSDepart().getId() + "' or e.manage_duty_man_id = '" + user.getId() + "')");
                }
                if(StringUtils.isNotBlank(departId)){
                    sql.append(" and e.duty_unit = '"+departId+"' ");
                }
                if("true".equals(ewmgn) && StringUtils.isNotBlank(addressId)){
                    sql.append(" and e.address='"+addressId+"'");
                }
            } else if (QUERYTYPE_REVIEWED.equals(queryType)) {//已复查页面显示状态为已复查且当前登录人在录入人/检查人相同部门的记录
                sql.append(" where h.handlel_status = '5' ");
                if (!"admin".equals(user.getUserName())&&!isAJY&&!isAdmin) {
                    sql.append(" and (EXISTS ( select * from t_s_base_user u, t_s_user_org uo\n" +
                            " where uo.user_id=u.id and uo.org_id='" + user.getTSDepart().getId() + "'\n" +
                            " and (u.username=e.create_by or FIND_IN_SET(u.id,e.fill_card_manids)) ) or  e.manage_duty_unit = '" + user.getTSDepart().getId() + "' or e.manage_duty_man_id = '" + user.getId() + "')");
                }
                if(StringUtils.isNotBlank(departId)){
                    sql.append(" and e.duty_unit = '"+departId+"' ");
                }

            } else if (QUERYTYPE_TIMEOUT.equals(queryType)) {//超期未整改
                sql.append(" where h.handlel_status in ('1','4') and e.limit_date < curdate() and e.duty_unit = '"+user.getTSDepart().getId()+"'");
            } else {
                return new ApiResultJson(ApiResultJson.CODE_202,"查询类型不存在",null);
            }

            if (QUERYTYPE_RECTIFIED.equals(queryType)){
                sql.append(" order by h.update_date desc ");
            } else {
                sql.append(" order by e.exam_date desc");
            }
            result = systemService.findForJdbc(sql.toString(), Integer.parseInt(page), Integer.parseInt(rows));
            if (result != null && result.size() > 0) {
                List<TSUser> userList = systemService.getList(TSUser.class);
                Map<String, String> realnameMap = new HashMap<>();
                if (userList != null && userList.size() >0 ) {
                    for (TSUser u: userList) {
                        realnameMap.put(u.getId(), u.getRealName());
                    }
                }
                for (Map<String, Object> map: result) {
                    map.put("examTypeDesc", DicUtil.getTypeNameByCode("examType", (String) map.get("examType")));
                    map.put("handelStatusDesc", DicUtil.getTypeNameByCode("handelStatus", (String) map.get("handelStatus")));
                    map.put("manageType", DicUtil.getTypeNameByCode("manageType", (String) map.get("manageType")));
                    map.put("riskType", DicUtil.getTypeNameByCode("risk_type", (String) map.get("riskType")));
                    map.put("hiddenCategory", DicUtil.getTypeNameByCode("hiddenCate", (String) map.get("hiddenCategory")));
                    map.put("hiddenNature", DicUtil.getTypeNameByCode("hiddenLevel", (String) map.get("hiddenNature")));
                    map.put("hiddenType", DicUtil.getTypeNameByCode("hiddenType", (String) map.get("hiddenType")));
                    map.put("shift", DicUtil.getTypeNameByCode("workShift", (String) map.get("shift")));
                    map.put("modifyShift", DicUtil.getTypeNameByCode("workShift", (String) map.get("modifyShift")));
                    map.put("reviewShift", DicUtil.getTypeNameByCode("workShift", (String) map.get("reviewShift")));
                    map.put("dealTypeDesc", Constants.DEALTYPE_XIANCAHNG.equals((String) map.get("dealType")) ? "现场整改" : "限时整改");
                    if (Constants.HIDDENCHECK_EXAMTYPE_KUANGJINGANQUANDAJIANCHA.equals(map.get("examType"))) {
                        map.put("item", DicUtil.getTypeNameByCode("group", (String) map.get("item")));
                        String itemUser = (String) map.get("itemUser");
                        StringBuffer sb = new StringBuffer();
                        if (StringUtils.isNotBlank(itemUser)) {
                            for (String userId : itemUser.split(",")) {
                                TSUser tsUser = systemService.getEntity(TSUser.class, userId);
                                if (tsUser != null) {
                                    if (StringUtils.isNotBlank(sb.toString())) {
                                        sb.append(",");
                                    }
                                    sb.append(tsUser.getRealName());
                                }
                            }
                            map.put("itemUser", sb.toString());
                        }
                    }
                    String fillCardMan = (String) map.get("fillCardMan");
                    if (StringUtils.isNotBlank(fillCardMan)) {
                        StringBuffer names = new StringBuffer();
                        for (String man : fillCardMan.split(",")) {
                            if (StringUtils.isNoneBlank(names.toString())) {
                                names.append(",");
                            }
                            names.append(realnameMap.get(man));
                        }
                        map.put("fillCardMan", names.toString());
                    }

                    String hiddenId = (String) map.get("id");
                    TBHiddenDangerExamEntity examEntity = systemService.getEntity(TBHiddenDangerExamEntity.class, hiddenId);
                    //组装图片路径报文,格式[{'imgPath':'uploadfile/402880f7604322680160433e7e6a0009/1.jpg','reduceImgPath','uploadfile/402880f7604322680160433e7e6a0009/reduce_1.jpg'},{}]
                    JSONArray fileContent = new JSONArray();
                    if (StringUtil.isNotEmpty(examEntity)) {
                        if (StringUtil.isNotEmpty(examEntity.getMobileId())) {
                            String imgSql = "select img_path from t_b_hidden_danger_img_rel where mobile_hidden_id='" + examEntity.getMobileId() + "'";
                            List<String> imgPathList = systemService.findListbySql(imgSql);
                            if (!imgPathList.isEmpty() && imgPathList.size() > 0) {
                                for (String imgPath : imgPathList) {
                                    String dir = "uploadfile/" + examEntity.getMobileId() + "/";
                                    JSONObject jo = new JSONObject();
                                    jo.put("imgPath", dir + imgPath);
                                    jo.put("reduceImgPath", dir + "reduce_" + imgPath);

                                    fileContent.add(jo);
                                }
                            }
                            map.put("imgContents", fileContent);
                        }
                    }
                }
            }
            return new ApiResultJson(result);
        } catch (Exception e) {
            LogUtil.error("隐患列表查询错误",e);
            return new ApiResultJson(ApiResultJson.CODE_500,ApiResultJson.CODE_500_MSG,null);
        }
    }
}