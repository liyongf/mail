package com.sdzk.buss.web.hiddendanger.controller;

import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.riskmanage.entity.*;
import com.sdzk.buss.api.service.WeChartGetToken;
import com.sdzk.buss.api.utils.WebChatUtil;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.excelverify.HiddenDangerExamExcelVerifyHandler;
import com.sdzk.buss.web.common.service.ReportToProvinceService;
import com.sdzk.buss.web.common.service.SfService;
import com.sdzk.buss.web.common.taskProvince.TBReportDeleteIdEntity;
import com.sdzk.buss.web.common.utils.SMSSenderUtil;
import com.sdzk.buss.web.common.utils.SemanticSimilarityUtil;
import com.sdzk.buss.web.dangersource.entity.TBDangerSourceEntity;
import com.sdzk.buss.web.gjj.entity.SfHiddenRelEntity;
import com.sdzk.buss.web.hiddendanger.entity.*;
import com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerExamServiceI;
import com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerHandleLogServiceI;
import com.sdzk.buss.web.quartz.QuartzJob;
import com.sdzk.buss.web.quartz.service.QrtzManagerServiceI;
import com.sdzk.buss.web.riskUpgrade.service.RiskUpgradeServiceI;
import com.sdzk.buss.web.riskcontrol.entity.TBTaskManagerOrder;
import com.sdzk.buss.web.riskcontrol.entity.TBTaskOrderHidden;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import com.sdzk.sys.synctocloud.service.SyncToCloudService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.*;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.result.ExcelImportResult;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSRole;
import org.jeecgframework.web.system.pojo.base.TSRoleUser;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 隐患检查
 * @author onlineGenerator
 * @date 2016-04-21 10:24:05
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBHiddenDangerExamController")
public class TBHiddenDangerExamController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TBHiddenDangerExamController.class);

    @Autowired
    private RiskUpgradeServiceI riskUpgradeService;
    @Autowired
    private SyncToCloudService syncToCloudService;
    @Autowired
    private WeChartGetToken weChartGetToken;
    @Resource(name="quartzScheduler")
    private Scheduler scheduler;

    @Autowired
    private SystemService systemService;
    @Autowired
    private TBHiddenDangerExamServiceI tBHiddenDangerExamService;
    @Autowired
    private QrtzManagerServiceI qrtzManagerServiceI;
    @Autowired
    private TBHiddenDangerHandleLogServiceI tBHiddenDangerHandleLogService;


    @Autowired
    private SfService sfService;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Map<String, String> colorlist = new HashMap<String, String>();
    static{
        colorlist.put("低风险", "#0d60bd");//蓝
        colorlist.put("一般风险", "#dbed74");//黄
        colorlist.put("较大风险", "#e1b96c");//橙
        colorlist.put("重大风险", "#ea3232");//红

        colorlist.put("E", "#0d60bd");//蓝
        colorlist.put("D", "#dbed74");//黄
        colorlist.put("C", "#e1b96c");//橙
        colorlist.put("B", "#ea3232");//红

        colorlist.put("人", "#005AB5");
        colorlist.put("机", "#009100");
        colorlist.put("环", "#8CEA00");
        colorlist.put("管", "#D9B300");

        colorlist.put("1", "#FF0000");
        colorlist.put("2", "#ee6eff");
        colorlist.put("3", "#DFDF00");
        colorlist.put("4", "#01FFFF");
        colorlist.put("5", "#02ca02");
        colorlist.put("6", "#005AB5");
        colorlist.put("7", "#708f11");
        colorlist.put("8", "#8f6d10");
        colorlist.put("9", "#539997");
        colorlist.put("10", "#a2d43e");
        colorlist.put("11", "#a8ff24");
        colorlist.put("12", "#e1e100");
        colorlist.put("13", "#ff9224");
        colorlist.put("14", "#c07abb");
        colorlist.put("15", "#5cadad");
    }

    public static Map<String, String> vioColorList = new HashMap<String, String>();
    static{
        vioColorList.put("E", "#85E6F9");//
        vioColorList.put("D", "#0d60bd");//蓝
        vioColorList.put("C", "#dbed74");//黄
        vioColorList.put("B", "#e1b96c");//橙
        vioColorList.put("A", "#ea3232");//红

    }


    /**
     * 隐患检查列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        String examType = request.getParameter("examType");
        request.setAttribute("examType",examType);//检查类型
        String bRequiredDangerSouce = ResourceUtil.getConfigByName("bRequiredDangerSouce");
        request.setAttribute("bRequiredDangerSouce",bRequiredDangerSouce);
        if(Constants.HIDDENCHECK_EXAMTYPE_SHANGJI.equals(examType)){
            //上级检查列表页面
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamList");
        }else if(Constants.HIDDENCHECK_EXAMTYPE_GUANLIGANBUXIAJING.equals(examType)){
            //管理干部下井
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamList");
        }else if(Constants.HIDDENCHECK_EXAMTYPE_KLDDB.equals(examType)){
            //矿领导带班检查
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamList");
        }else if(Constants.HIDDENCHECK_EXAMTYPE_KUANGJINGANQUANDAJIANCHA.equals(examType)){
            //矿井安全大检查
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamList-kjaqdjc");
        }else if(Constants.HIDDENCHECK_EXAMTYPE_ZHILIANGBIAOZHUNHUA.equals(examType)){
            //质量标准化检查
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamList");
        }else if(Constants.HIDDENCHECK_EXAMTYPE_ZHUANYEKESHI.equals(examType)){
            //专业科室日常检查
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamList");
        }else if(Constants.HIDDENCHECK_EXAMTYPE_ANJIANYUAN.equals(examType)){
            //安监员安全检查
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamList");
        }else if(Constants.HIDDENCHECK_EXAMTYPE_SANJIAYI.equals(examType)){
            //安监员安全检查
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamList");
        }else{
            //如果没有符合条件的页面，统一跳转到此页面
            //隐患检查列表页面
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamList");
        }
    }

    /**
     * 隐患检查列表 页面跳转 (新增)
     *
     * @return
     */
    @RequestMapping(params = "newList")
    public ModelAndView newList(HttpServletRequest request) {
        String examType = request.getParameter("examType");
        request.setAttribute("examType",examType);//检查类型
        String bRequiredDangerSouce = ResourceUtil.getConfigByName("bRequiredDangerSouce");
        request.setAttribute("bRequiredDangerSouce",bRequiredDangerSouce);
        String newPost = ResourceUtil.getConfigByName("newPost");
        request.setAttribute("newPost",newPost);
        String xiezhuang = ResourceUtil.getConfigByName("xiezhuang");
        request.setAttribute("xiezhuang",xiezhuang);
        String beixulou = ResourceUtil.getConfigByName("beixulou");
        request.setAttribute("beixulou",beixulou);
        String taskAllId = request.getParameter("taskAllId");
        request.setAttribute("taskAllId",taskAllId);
        String detail = request.getParameter("detail");
        request.setAttribute("detail",detail);
        String yhdr = ResourceUtil.getConfigByName("yhdr");
        request.setAttribute("yhdr",yhdr);
        String huayuan = ResourceUtil.getConfigByName("huayuan");
        request.setAttribute("huayuan",huayuan);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamList-new");

    }

    /**
     * 待上报记录-撤回待审核的记录
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "toReportCallback")
    @ResponseBody
    public AjaxJson toReportCallback(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "撤回成功";
        try {
            if(StringUtil.isNotEmpty(ids)){
                for(String id : ids.split(",")){
                    TBHiddenDangerExamEntity t = systemService.get(TBHiddenDangerExamEntity.class, id);
                    if (Constants.HANDELSTATUS_REPORT.equals(t.getHandleEntity().getHandlelStatus())) {
                        t.getHandleEntity().setHandlelStatus(Constants.HANDELSTATUS_DRAFT);
                        t.getHandleEntity().setRollBackRemark("");
                        systemService.saveOrUpdate(t);
                        qrtzManagerServiceI.removeJob(scheduler,id);
                        riskUpgradeService.execute(id);
                    }
                    else{
                        message = "只能选择未整改的数据";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "撤回失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 上报隐患
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "reportHiddenDanger")
    @ResponseBody
    public AjaxJson reportHiddenDanger(String ids, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "上报成功";
        try {
            if(StringUtil.isNotEmpty(ids)){
                for(String id : ids.split(",")){
                    TBHiddenDangerExamEntity t = systemService.get(TBHiddenDangerExamEntity.class, id);
                    String statusOrigin = t.getHandleEntity().getHandlelStatus();
                    if (Constants.HANDELSTATUS_DRAFT.equals(t.getHandleEntity().getHandlelStatus()) || Constants.HANDELSTATUS_ROLLBACK_REPORT.equals(t.getHandleEntity().getHandlelStatus())) {
                        if(Constants.DEALTYPE_XIANCAHNG.equals(t.getDealType())) {//现场处理
                            t.getHandleEntity().setHandlelStatus(Constants.REVIEWSTATUS_PASS);
                        }else {
                            t.getHandleEntity().setHandlelStatus(Constants.HANDELSTATUS_REPORT);
                            if(t.getDealType().equals("1") && !(t.getHiddenNature().equals("1"))){
                                // 添加升级任务
                                String job_name = t.getId();

                                Date limitDate = t.getLimitDate();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                String limitStr = sdf.format(limitDate);
                                limitStr = limitStr + " 23:59:59";
                                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                try {
                                    limitDate = sdf.parse(limitStr);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(limitDate);
                                int year = calendar.get(calendar.YEAR);
                                int month = calendar.get(calendar.MONTH) + 1;
                                int day = calendar.get(calendar.DATE);
                                int hour = calendar.get(calendar.HOUR_OF_DAY);
                                int minute = calendar.get(calendar.MINUTE);
                                int second = calendar.get(calendar.SECOND);

                                StringBuffer sb = new StringBuffer();
                                sb.append(second).append(" ").append(minute).append(" ").append(hour).append(" ").append(day).append(" ").append(month).append(" ").append("? ").append(year);
                                try{
                                    if(limitDate.getTime()>(new Date()).getTime()){//限期时间大于当前时间执行，否则隐患保存方法报错
                                        qrtzManagerServiceI.addJob(scheduler,t.getId(), job_name, QuartzJob.class, sb.toString());
                                    }

                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                //添加升级任务结
                            }
                            riskUpgradeService.execute(t.getId());

                        }
                        systemService.saveOrUpdate(t);

                        //提交隐患添加短信提醒，通知整改人（责任单位电话）
                        try{
                            if(Constants.DEALTYPE_XIANQI.equals(t.getDealType())){
                                TSUser sessionUser = ResourceUtil.getSessionUserName();
                                String createName = sessionUser.getRealName();
                                String createTime = DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm");
                                String addressName ="";
                                if(null!=t.getAddress()){
                                    addressName = t.getAddress().getAddress();
                                }
                                String problemDesc=t.getProblemDesc();
                                String limitDate= DateUtils.formatDate(t.getLimitDate(), "yyyy-MM-dd");
                                String content = "【双防平台】通知：尊敬的双防用户，您好！"+createName+"发布了一条新的隐患：" +createTime+
                                        " 地点："+addressName+"，内容：" +problemDesc+
                                        "，限期日期为："+limitDate;
                                TSDepart tsDepart = t.getDutyUnit();
                                String pho1 = tsDepart.getPho1();
                                String tsDepartid=tsDepart.getId();
                                //sql 待测试
                                String sql="select openid from t_b_WexinOpenId  where departId='"+tsDepartid+"'";
                                List <String> openlist=systemService.findListbySql(sql);
                                if(openlist.size()>0) {
                                    for (String openid : openlist) {
                                        postAddTemplate(openid,createName ,createTime,addressName, problemDesc, limitDate);
                                    }
                                }else if(StringUtils.isNotBlank(pho1)){
                                    SMSSenderUtil.sendSMS(content,pho1, Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
                                }
                                //企业微信通知
                                String dutyman= t.getDutyMan();
                                List<String> weChatPhones=systemService.findListbySql("select u.weChatPhone from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.realname='" + dutyman + "'");
                                if(weChatPhones != null && StringUtils.isNotBlank(weChatPhones.get(0))) {
                                    WebChatUtil.sendWeChatMessageToUser(weChatPhones.get(0), content);
                                }
                            }
                        }
                        catch(Exception e){
                            logger.error(ExceptionUtil.getExceptionMessage(e));
                        }

                        //添加处理步骤
                        TSUser user = ResourceUtil.getSessionUserName();
                        if(Constants.HANDELSTATUS_DRAFT.equals(statusOrigin)){
                            TBHiddenDangerHandleStepEntity handleStepEntity = new TBHiddenDangerHandleStepEntity();
                            handleStepEntity.setHiddenDanger(t);
                            handleStepEntity.setHandleStep(1);
                            if(Constants.DEALTYPE_XIANQI.equals(t.getDealType())){
                                handleStepEntity.setHandleDate(new Date());
                                handleStepEntity.setHandleMan(user.getRealName());
                                handleStepEntity.setHandleStatus(Constants.HANDELSTATUS_REPORT);
                            } else {
                                handleStepEntity.setHandleDate(t.getExamDate());
                                handleStepEntity.setHandleMan(t.getCreateName());
                                handleStepEntity.setHandleStatus(Constants.REVIEWSTATUS_PASS);
                            }
                            systemService.save(handleStepEntity);
                        } else if(Constants.HANDELSTATUS_ROLLBACK_REPORT.equals(statusOrigin)) {
                            int handleStep = 0;
                            CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleStepEntity.class);
                            cq.eq("hiddenDanger.id",t.getId());
                            cq.addOrder("handleStep", SortDirection.asc);
                            cq.add();
                            List<TBHiddenDangerHandleStepEntity> handleStepList = systemService.getListByCriteriaQuery(cq,false);
                            if(!handleStepList.isEmpty() && handleStepList.size() > 0 ){
                                handleStep = handleStepList.get((handleStepList.size()-1)).getHandleStep() + 1;
                                TBHiddenDangerHandleStepEntity handleStepEntity = new TBHiddenDangerHandleStepEntity();
                                handleStepEntity.setHiddenDanger(t);
                                handleStepEntity.setHandleStep(handleStep);
                                handleStepEntity.setHandleDate(new Date());
                                handleStepEntity.setHandleMan(user.getRealName());
                                handleStepEntity.setHandleStatus(Constants.HANDELSTATUS_REPORT);
                                systemService.save(handleStepEntity);
                            }
                        }
                    }
                    tBHiddenDangerHandleLogService.addLog(id, Globals.Log_Type_REPORT,"隐患上报成功");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            message = "上报失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 隐患检查编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBHiddenDangerExamEntity tBHiddenDangerExam, HttpServletRequest req) {
        String flag = req.getParameter("flag");
        req.setAttribute("flag",flag);
        String load = req.getParameter("load");
        req.setAttribute("load",load);
        String examType = req.getParameter("examType");
        req.setAttribute("examType",examType);//检查类型
        String bRequiredDangerSouce = ResourceUtil.getConfigByName("bRequiredDangerSouce");
        req.setAttribute("bRequiredDangerSouce",bRequiredDangerSouce);
        String newPost = ResourceUtil.getConfigByName("newPost");
        req.setAttribute("newPost",newPost);
        String xiezhuang = ResourceUtil.getConfigByName("xiezhuang");
        req.setAttribute("xiezhuang",xiezhuang);
        String xiaogang = ResourceUtil.getConfigByName("xiaogang");
        req.setAttribute("xiaogang",xiaogang);
        String beixulou = ResourceUtil.getConfigByName("beixulou");
        req.setAttribute("beixulou",beixulou);
        String jinyuan = ResourceUtil.getConfigByName("jinyuan");
        req.setAttribute("jinyuan",jinyuan);
        String luwa = ResourceUtil.getConfigByName("luwa");
        req.setAttribute("luwa",luwa);
        String anju = ResourceUtil.getConfigByName("anju");
        req.setAttribute("anju",anju);
        String taiping = ResourceUtil.getConfigByName("taiping");
        req.setAttribute("taiping",taiping);
        String hegang = ResourceUtil.getConfigByName("hegang");
        req.setAttribute("hegang",hegang);
        String ezhuang = ResourceUtil.getConfigByName("ezhuang");
        req.setAttribute("ezhuang",ezhuang);
        String huayuan = ResourceUtil.getConfigByName("huayuan");
        req.setAttribute("huayuan",huayuan);
        String lilou = ResourceUtil.getConfigByName("ewmgn");
        if (StringUtil.isNotEmpty(tBHiddenDangerExam.getId())) {
            tBHiddenDangerExam = tBHiddenDangerExamService.getEntity(TBHiddenDangerExamEntity.class, tBHiddenDangerExam.getId());
            if(tBHiddenDangerExam != null){
                String names = "";
                String fillcardmanids="";

                String querySql = "select fill_card_manids man from t_b_hidden_danger_exam where id = '" + String.valueOf(tBHiddenDangerExam.getId())+"'";
                List<Map<String, Object>> maplist = systemService.findForJdbc(querySql, null);
                for (Map map : maplist) {
                    String mans = String.valueOf(map.get("man"));
                    if(StringUtils.isNotBlank(mans)){
                        String[] userIdArray = mans.split(",");



                        for(String userid : userIdArray){
                            if(fillcardmanids == ""){
                                fillcardmanids = fillcardmanids + '"' + userid + '"';
                            }else{
                                fillcardmanids = fillcardmanids + ',' + '"'+ userid + '"';
                            }
                            TSUser user = systemService.getEntity(TSUser.class,userid);
                            if(user!=null){
                                if(StringUtils.isNotBlank(lilou) && "true".equals(lilou)){
                                    if(names == ""){
                                        names = names + user.getRealName();
                                    }else{
                                        names = names + "," + user.getRealName();
                                    }
                                }else {
                                    if (names == "") {
                                        names = names + user.getRealName() + "-" + user.getUserName();
                                    } else {
                                        names = names + "," + user.getRealName() + "-" + user.getUserName();
                                    }
                                }
                            }else if(StringUtil.isNotEmpty(userid)){
                                if(names == ""){
                                    names = names + userid ;
                                }else{
                                    names = names + "," +userid;
                                }
                            }

                        }
                        req.setAttribute("fillcardmanids",fillcardmanids);
                    }
                }
                tBHiddenDangerExam.setFillCardManNames(names);
                if(StringUtils.isNotBlank(tBHiddenDangerExam.getHiddenType())){
                    tBHiddenDangerExam.setHiddenTypeTemp(DicUtil.getTypeNameByCode("hiddenType", tBHiddenDangerExam.getHiddenType()));
                }

                String hiddenCategory = tBHiddenDangerExam.getHiddenCategory();
                if(StringUtils.isNotBlank(hiddenCategory)){
                    String hiddenCategoryTemp = DicUtil.getTypeNameByCode("hiddenCate",hiddenCategory);
                    tBHiddenDangerExam.setHiddenCategoryTemp(hiddenCategoryTemp);
                }
                String hiddenNature = tBHiddenDangerExam.getHiddenNature();
                if(StringUtils.isNotBlank(hiddenNature)){
                    String hiddenNatureTemp = DicUtil.getTypeNameByCode("hiddenLevel",hiddenNature);
                    tBHiddenDangerExam.setHiddenNatureTemp(hiddenNatureTemp);
                }
                String hiddenLevel = tBHiddenDangerExam.getHiddenLevel();
                if(StringUtils.isNotBlank(hiddenLevel)){
                    String hiddenLevelTemp = DicUtil.getTypeCodeByName("hiddenLevel",hiddenLevel);
                    tBHiddenDangerExam.setHiddenLevelTemp(hiddenLevelTemp);
                }
                String itemId = tBHiddenDangerExam.getItemId();
                if (StringUtils.isNotBlank(itemId)) {
                    req.setAttribute("itemDesc", DicUtil.getTypeNameByCode("group",itemId));
                }
                String manageDutyManId = tBHiddenDangerExam.getManageDutyManId();
                if (StringUtils.isNotBlank(manageDutyManId)) {
                    TSUser user = systemService.getEntity(TSUser.class,manageDutyManId);
                    if(user!=null){
                        tBHiddenDangerExam.setManageDutyManTemp(user.getRealName());
                    }
                }
            }

            String superviseUnitID = tBHiddenDangerExam.getSuperviseUnitId();
            if (StringUtil.isNotEmpty(superviseUnitID)) {
                String departNameTemp = "";
                String sql = "select departname from t_s_depart where ID = '"+superviseUnitID+"';";
                List<String> listDepart = systemService.findListbySql(sql);
                if(!listDepart.isEmpty()||listDepart.size()>0) {
                    departNameTemp = listDepart.get(0);
                    req.setAttribute("departNameTemp",departNameTemp);
                }else {
                    departNameTemp = superviseUnitID;
                    req.setAttribute("departNameTemp",departNameTemp);
                }
            }

            if(tBHiddenDangerExam.getDangerId() != null && StringUtils.isNotBlank(tBHiddenDangerExam.getDangerId().getYeRiskGrade())){

                //风险等级
                String dangerSourceRiskValue;
                dangerSourceRiskValue = DicUtil.getTypeNameByCode("riskLevel",tBHiddenDangerExam.getDangerId().getYeRiskGrade());
                req.setAttribute("dangerSourceRiskValueTemp",dangerSourceRiskValue);
                //风险颜色
                String dangerSourceAlertColor;
                if("重大风险".equals(dangerSourceRiskValue)){
                    dangerSourceAlertColor= Constants.ALERT_COLOR_ZDFX;
                }else if("较大风险".equals(dangerSourceRiskValue)){
                    dangerSourceAlertColor= Constants.ALERT_COLOR_JDFX;
                }else if("一般风险".equals(dangerSourceRiskValue)){
                    dangerSourceAlertColor= Constants.ALERT_COLOR_YBFX;
                }else{
                    dangerSourceAlertColor= Constants.ALERT_COLOR_DFX;
                }
                req.setAttribute("dangerSourceAlertColor",dangerSourceAlertColor);
            }


            tBHiddenDangerExam.setShiftTemp(DicUtil.getTypeNameByCode("workShift", tBHiddenDangerExam.getShift()));
            tBHiddenDangerExam.setOriginTemp(DicUtil.getTypeNameByCode("hiddenDangerOrigin", tBHiddenDangerExam.getOrigin()));
            tBHiddenDangerExam.setLimitShiftTemp(DicUtil.getTypeNameByCode("workShift", tBHiddenDangerExam.getLimitShift()));
            req.setAttribute("tBHiddenDangerExamPage", tBHiddenDangerExam);

            CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class);
            cq.eq("hiddenDanger.id",tBHiddenDangerExam.getId());
            cq.add();
            List<TBHiddenDangerHandleEntity> handleEntityList = systemService.getListByCriteriaQuery(cq,false);
            if(!handleEntityList.isEmpty() && handleEntityList.size() > 0 ){
                TBHiddenDangerHandleEntity handleEntity = handleEntityList.get(0);
                String handleStatus = handleEntity.getHandlelStatus();
                tBHiddenDangerExam.setHandleStatusTemp(handleStatus);
                if(handleStatus.equals(Constants.HANDELSTATUS_ROLLBACK_REPORT)) {
                    //退回上报
                    tBHiddenDangerExam.setRollBackRemarkTemp(handleEntity.getRollBackRemark());
                }

                /***** 整改和复查信息 begin *****/
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(handleStatus.equals(Constants.HANDELSTATUS_REVIEW)){
                    // 已整改待复查
                    if(StringUtils.isNotBlank(handleEntity.getModifyMan())){
                        tBHiddenDangerExam.setModifyManTemp(handleEntity.getModifyMan());
                    }
                    if(handleEntity.getModifyDate() != null){
                        String modifyDate = sdf.format(handleEntity.getModifyDate());
                        tBHiddenDangerExam.setModifyDateTemp(modifyDate);
                    }
                    if(StringUtils.isNotBlank(handleEntity.getRectMeasures())){
                        tBHiddenDangerExam.setModifyRemarkTemp(handleEntity.getRectMeasures());
                    }

                }
                if(handleStatus.equals(Constants.REVIEWSTATUS_PASS)){
                    // 复查通过
                    if(StringUtils.isNotBlank(handleEntity.getModifyMan())){
                        tBHiddenDangerExam.setModifyManTemp(handleEntity.getModifyMan());
                    }
                    if(handleEntity.getModifyDate() != null){
                        String modifyDate = sdf.format(handleEntity.getModifyDate());
                        tBHiddenDangerExam.setModifyDateTemp(modifyDate);
                    }

                    if(handleEntity.getReviewDate() != null){
                        String reviewDate = sdf.format(handleEntity.getReviewDate());
                        tBHiddenDangerExam.setReviewDateTemp(reviewDate);
                    }

                    if(StringUtils.isNotBlank(handleEntity.getReviewMan())){
                        tBHiddenDangerExam.setReviewManTemp(handleEntity.getReviewMan());
                    }
                    if(StringUtils.isNotBlank(handleEntity.getRectMeasures())){
                        tBHiddenDangerExam.setModifyRemarkTemp(handleEntity.getRectMeasures());
                    }
                    tBHiddenDangerExam.setReviewResultTemp("复查通过");
                    if(StringUtils.isNotBlank(handleEntity.getReviewReport())){
                        tBHiddenDangerExam.setReviewDetailTemp(handleEntity.getReviewReport());
                    }
                }
                if(handleStatus.equals(Constants.HANDELSTATUS_ROLLBACK_CHECK)){
                    // 复查不通过退回整改
                    if(StringUtils.isNotBlank(handleEntity.getModifyMan())){
                        tBHiddenDangerExam.setModifyManTemp(handleEntity.getModifyMan());
                    }
                    if(handleEntity.getModifyDate() != null){
                        String modifyDate = sdf.format(handleEntity.getModifyDate());
                        tBHiddenDangerExam.setModifyDateTemp(modifyDate);
                    }

                    if(handleEntity.getReviewDate() != null){
                        String reviewDate = sdf.format(handleEntity.getReviewDate());
                        tBHiddenDangerExam.setReviewDateTemp(reviewDate);
                    }

                    if(StringUtils.isNotBlank(handleEntity.getReviewMan())){
                        tBHiddenDangerExam.setReviewManTemp(handleEntity.getReviewMan());
                    }
                    if(StringUtils.isNotBlank(handleEntity.getRectMeasures())){
                        tBHiddenDangerExam.setModifyRemarkTemp(handleEntity.getRectMeasures());
                    }
                    tBHiddenDangerExam.setReviewResultTemp("复查未通过");
                    if(StringUtils.isNotBlank(handleEntity.getReviewReport())){
                        tBHiddenDangerExam.setReviewDetailTemp(handleEntity.getReviewReport());
                    }
                }
                /***** 整改和复查信息 end *****/
            }

            cq = new CriteriaQuery(TBHiddenDangerHandleStepEntity.class);
            cq.eq("hiddenDanger.id",tBHiddenDangerExam.getId());
            cq.addOrder("handleStep", SortDirection.desc);
            cq.add();
            List<TBHiddenDangerHandleStepEntity> handleStepList = systemService.getListByCriteriaQuery(cq,false);
            if(!handleStepList.isEmpty() && handleStepList.size() > 0 ){
                for(TBHiddenDangerHandleStepEntity t:handleStepList){
                    String status = t.getHandleStatus();
                    if(status.equals(Constants.HANDELSTATUS_REPORT)){
                        t.setHandleTypeTemp("上报");
                    }
                    if(status.equals(Constants.HANDELSTATUS_ROLLBACK_REPORT)){
                        t.setHandleTypeTemp("退回上报");
                    }
                    if(status.equals(Constants.HANDELSTATUS_REVIEW)){
                        t.setHandleTypeTemp("整改");
                    }
                    if(status.equals(Constants.REVIEWSTATUS_PASS)){
                        t.setHandleTypeTemp("复查通过");
                    }
                    if(status.equals(Constants.HANDELSTATUS_ROLLBACK_CHECK)){
                        t.setHandleTypeTemp("复查未通过");
                    }

                }
                req.setAttribute("handleStepList",handleStepList);
            }
        }
        String manageType = tBHiddenDangerExam.getManageType();
        if(StringUtil.isNotEmpty(manageType)){
            String manageTypeTemp = DicUtil.getTypeNameByCode("manageType",manageType);
            tBHiddenDangerExam.setManageTypeTemp(manageTypeTemp);
        }

        String riskType = tBHiddenDangerExam.getRiskType();
        if(StringUtil.isNotEmpty(riskType)){
            String riskTypeTemp = DicUtil.getTypeNameByCode("risk_type",riskType);
            tBHiddenDangerExam.setRiskTypeTemp(riskTypeTemp);
        }

        /**获取隐患对应图片*/
        if(StringUtil.isNotEmpty(tBHiddenDangerExam.getMobileId())) {
            String imgSql = "select img_path from t_b_hidden_danger_img_rel where mobile_hidden_id='" + tBHiddenDangerExam.getMobileId() + "'";
            List<String> imglist = systemService.findListbySql(imgSql);
            if (!imglist.isEmpty() && imglist.size() > 0) {
                req.setAttribute("imagelists", imglist);
            }
        }
        if(Constants.HIDDENCHECK_EXAMTYPE_GUANLIGANBUXIAJING.equals(examType) || Constants.HIDDENCHECK_EXAMTYPE_KUANGJINGANQUANDAJIANCHA.equals(examType)
                || Constants.HIDDENCHECK_EXAMTYPE_ZHILIANGBIAOZHUNHUA.equals(examType) || Constants.HIDDENCHECK_EXAMTYPE_ZHUANYEKESHI.equals(examType)
                || Constants.HIDDENCHECK_EXAMTYPE_ANJIANYUAN.equals(examType) || Constants.HIDDENCHECK_EXAMTYPE_KLDDB.equals(examType) || Constants.HIDDENCHECK_EXAMTYPE_SANJIAYI.equals(examType)){
            //管理干部下井、矿井安全大检查、质量标准化检查、专业科室日常检查、安监员安全检查、矿领导带班检查
            if(Constants.HIDDENCHECK_EXAMTYPE_ZHUANYEKESHI.equals(examType)){
                //专业科室
                String proType = tBHiddenDangerExam.getProType();
                if(StringUtils.isNotBlank(proType)){
                    String proTypeTemp = DicUtil.getTypeNameByCode("proCate_gradeControl",proType);
                    tBHiddenDangerExam.setProTypeTemp(proTypeTemp);
                }
            }
            if(Constants.HIDDENCHECK_EXAMTYPE_KUANGJINGANQUANDAJIANCHA.equals(examType)){
                //return 矿井安全大检查  修改、查看页面
                String itemUserId = tBHiddenDangerExam.getItemUserId();
                if(StringUtils.isNotBlank(itemUserId)){
                    String ids [] = itemUserId.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String id : ids){
                        TSUser user = systemService.getEntity(TSUser.class,id);
                        if(user != null){
                            if(StringUtils.isNotBlank(sb.toString())){
                                sb.append(",");
                            }
                            sb.append(user.getRealName());
                        }

                    }
                    tBHiddenDangerExam.setItemUserNameTemp(sb.toString());
                }
                if("detail".equals(load)){
                    //TODO  return to detail page
                    return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExam-detail");
                }else {
                    return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamWithAddressList-kjaqdjc-update");
                }
            }
            if ("detail".equals(load)){
                //TODO return to detail page
                return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExam-detail");
            }else{
                return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamWithAddressList-update");
            }
        }
        //新增更新界面
        if("yh".equals(examType)){
            if("detail".equals(load)){
                //TODO return to detail page
                return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExam-detail");
            }else{
                String xiezhuangEdit = req.getParameter("xiezhuangEdit");
                if(StringUtil.isEmpty(xiezhuangEdit)){
                    return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExam-updateNew");
                }else{
                    return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExam-updateNewXiezhuang");
                }

            }
        }
        if("detail".equals(load)){
            //TODO return to detail page
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExam-detail");
        }else{
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExam-update");
        }
    }


    /**
     * 其他检查列表
     * @param request
     * @return
     */
    @RequestMapping(params = "otherList")
    public ModelAndView otherList(HttpServletRequest request) {
        request.setAttribute("examType","other");//检查类型
        String bRequiredDangerSouce = ResourceUtil.getConfigByName("bRequiredDangerSouce");
        request.setAttribute("bRequiredDangerSouce",bRequiredDangerSouce);
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamOtherList");
    }

    /**
     * 隐患图片跳转到大图展示
     * @param req
     * @return
     */
    @RequestMapping(params = "goLargerimage")
    public ModelAndView goLargerimage(HttpServletRequest req) {

        req.setAttribute("path", req.getParameter("path"));
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerLargerImage");
    }

    /**
     * 井口信息办录入
     *
     * @return
     */
    @RequestMapping(params = "jkxxbList")
    public ModelAndView jkxxbList(TBHiddenDangerExamEntity tBHiddenDangerExam, HttpServletRequest req) {
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        req.setAttribute("loginUser",sessionUser.getUserName());
        return new ModelAndView("com/yk/buss/web/hiddendanger/iddenDtBHangerExamJkxxbList");
    }



    /**
     * 隐患检查新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBHiddenDangerExamEntity tBHiddenDangerExam, HttpServletRequest req) {
        String taskId = req.getParameter("taskId");
        req.setAttribute("taskId",taskId);
        //查询井下区队组织结构编号
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class);

        cq.add();
        List<TSDepart> listTemp = systemService.getListByCriteriaQuery(cq,false);
        if(listTemp != null && !listTemp.isEmpty()){
            req.setAttribute("tsDepart",listTemp.get(0));
        }
        if (StringUtil.isNotEmpty(tBHiddenDangerExam.getId())) {
            tBHiddenDangerExam = tBHiddenDangerExamService.getEntity(TBHiddenDangerExamEntity.class, tBHiddenDangerExam.getId());
            req.setAttribute("tBHiddenDangerExamPage", tBHiddenDangerExam);
        }
        String examType = req.getParameter("examType");
        req.setAttribute("examType",examType);//检查类型
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        req.setAttribute("sessionUser",sessionUser);
        String bRequiredDangerSouce = ResourceUtil.getConfigByName("bRequiredDangerSouce");
        req.setAttribute("bRequiredDangerSouce",bRequiredDangerSouce);
        try{
            String hancheng = ResourceUtil.getConfigByName("hancheng");
            req.setAttribute("hancheng",hancheng);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(Constants.HIDDENCHECK_EXAMTYPE_GUANLIGANBUXIAJING.equals(examType) || Constants.HIDDENCHECK_EXAMTYPE_KLDDB.equals(examType) ){
            //管理干部下井|| 矿领导带班下井
            String from = req.getParameter("from");
            req.setAttribute("from",from);
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamWithAddressList-glgbxj-add");
        }else if(Constants.HIDDENCHECK_EXAMTYPE_KUANGJINGANQUANDAJIANCHA.equals(examType)){
            //矿井安全大检查
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamWithAddressList-kjaqdjc-add");
        }

        if( Constants.HIDDENCHECK_EXAMTYPE_ZHILIANGBIAOZHUNHUA.equals(examType) || Constants.HIDDENCHECK_EXAMTYPE_ZHUANYEKESHI.equals(examType)
                || Constants.HIDDENCHECK_EXAMTYPE_ANJIANYUAN.equals(examType) || Constants.HIDDENCHECK_EXAMTYPE_SANJIAYI.equals(examType)){
            //质量标准化检查|||专业科室日常检查|安监员安全检查
            String from = req.getParameter("from");
            req.setAttribute("from",from);
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamWithAddressList-add");
        }
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExam-add");
    }

    /**
     * 隐患检查新增页面跳转 （新增）
     *
     * @return
     */
    @RequestMapping(params = "goAddNew")
    public ModelAndView goAddNew(TBHiddenDangerExamEntity tBHiddenDangerExam, HttpServletRequest req) {
        String taskId = req.getParameter("taskId");
        req.setAttribute("taskId",taskId);
        //查询井下区队组织结构编号
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class);

        cq.add();
        List<TSDepart> listTemp = systemService.getListByCriteriaQuery(cq,false);
        if(listTemp != null && !listTemp.isEmpty()){
            req.setAttribute("tsDepart",listTemp.get(0));
        }
        if (StringUtil.isNotEmpty(tBHiddenDangerExam.getId())) {
            tBHiddenDangerExam = tBHiddenDangerExamService.getEntity(TBHiddenDangerExamEntity.class, tBHiddenDangerExam.getId());
            req.setAttribute("tBHiddenDangerExamPage", tBHiddenDangerExam);
        }
        String examType = req.getParameter("examType");
        req.setAttribute("examType",examType);//检查类型
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        req.setAttribute("sessionUser",sessionUser);
        String bRequiredDangerSouce = ResourceUtil.getConfigByName("bRequiredDangerSouce");
        req.setAttribute("bRequiredDangerSouce",bRequiredDangerSouce);
        try{
            String hancheng = ResourceUtil.getConfigByName("hancheng");
            req.setAttribute("hancheng",hancheng);
        }catch (Exception e){
            e.printStackTrace();
        }
        String newPost = ResourceUtil.getConfigByName("newPost");
        req.setAttribute("newPost",newPost);
        String xiezhuang = ResourceUtil.getConfigByName("xiezhuang");
        req.setAttribute("xiezhuang",xiezhuang);
        String xiaogang = ResourceUtil.getConfigByName("xiaogang");
        req.setAttribute("xiaogang",xiaogang);
        String beixulou = ResourceUtil.getConfigByName("beixulou");
        req.setAttribute("beixulou",beixulou);
        String jinyuan = ResourceUtil.getConfigByName("jinyuan");
        req.setAttribute("jinyuan",jinyuan);
        String luwa = ResourceUtil.getConfigByName("luwa");
        req.setAttribute("luwa",luwa);
        String anju = ResourceUtil.getConfigByName("anju");
        req.setAttribute("anju",anju);
        String taskAllId = req.getParameter("taskAllId");
        req.setAttribute("taskAllId",taskAllId);
        if(StringUtil.isNotEmpty(taskAllId)){
            RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.get(RiskManageTaskAllEntity.class,taskAllId);
            if(riskManageTaskAllEntity!=null){
                req.setAttribute("manageType",riskManageTaskAllEntity.getManageType());
            }

        }
        TSUser tsUser = ResourceUtil.getSessionUserName();
        req.setAttribute("tsUser",tsUser);
        String taiping = ResourceUtil.getConfigByName("taiping");
        req.setAttribute("taiping",taiping);
        String ezhuang = ResourceUtil.getConfigByName("ezhuang");
        req.setAttribute("ezhuang",ezhuang);
        String xinan = ResourceUtil.getConfigByName("xinan");
        req.setAttribute("xinan",xinan);
        String hegang = ResourceUtil.getConfigByName("hegang");
        req.setAttribute("hegang",hegang);
        boolean isAdmin = false;
        CriteriaQuery cqru = new CriteriaQuery(TSRoleUser.class);
        try{
            cqru.eq("TSUser.id",sessionUser.getId());
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
        if (isAdmin) { //管理员数据补录
            String yhbl = ResourceUtil.getConfigByName("yhbl");
            req.setAttribute("yhbl",yhbl);
        }else{
            req.setAttribute("yhbl","false");
        }
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExam-addNew");
    }

    /**
     * 添加隐患检查
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBHiddenDangerExamEntity tBHiddenDangerExam, HttpServletRequest request) {
        //设置默认来源于本矿
        tBHiddenDangerExam.setOrigin("1");
        //根据关键字获取关联危险源
//        String dangerId = getDangerIdByKeyword(tBHiddenDangerExam.getKeyWords());
//        if (StringUtil.isNotEmpty(dangerId)) {
//            TBDangerSourceEntity dangerSourceEntity = new TBDangerSourceEntity();
//            dangerSourceEntity.setId(dangerId);
//            tBHiddenDangerExam.setDangerId(dangerSourceEntity);
//        }
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        //挂牌督办默认设置为未挂牌
        tBHiddenDangerExam.setIsLsProv(Constants.HDBIISLS_STATE_UNDO);
        tBHiddenDangerExam.setIsLsSub(Constants.HDBIISLS_STATE_UNDO);
        tBHiddenDangerExam.setReportStatus(Constants.REPORT_STATUS_N);
        String from = request.getParameter("from");
        String reportStatus = request.getParameter("reportStatus");
        if(StringUtils.isNotBlank(from)){
            //信息来源于进口信息办
            tBHiddenDangerExam.setIsFromJkxxb("1");
        }
        String examType = request.getParameter("examType");
        if(Constants.HIDDENCHECK_EXAMTYPE_KLDDB.equals(examType)){
            tBHiddenDangerExam.setIsWithClass("1");
        }
        //List<TBFineInfoEntity> fineList = fineMainPage.getFineList();
        String gzap = tBHiddenDangerExam.getGzap();
        if(StringUtils.isBlank(gzap)){
            tBHiddenDangerExam.setGzap("0");
        }
        AjaxJson j = new AjaxJson();
        message = "隐患检查添加成功";
        try{
            String dealType = tBHiddenDangerExam.getDealType();
            if(Constants.DEALTYPE_XIANCAHNG.equals(dealType)){
                tBHiddenDangerExam.setLimitDate(null);
                tBHiddenDangerExam.setLimitShift(null);
            }else {
                if (Constants.HIDDENCHECK_EXAMTYPE_SHANGJI.equals(examType)) {
//                tBHiddenDangerExam.setReviewMan(null);

                } else {
                    tBHiddenDangerExam.setReviewMan(null);
                }
            }
            String address = request.getParameter("address.address");
            TBAddressInfoEntity addressInfoEntity = systemService.getEntity(TBAddressInfoEntity.class,address);
            tBHiddenDangerExam.setAddress(addressInfoEntity);
            String post = request.getParameter("post.postname");
            if(StringUtil.isNotEmpty(post)){
                TBPostManageEntity postManageEntity= systemService.getEntity(TBPostManageEntity.class,post);
                tBHiddenDangerExam.setPost(postManageEntity);
            }else{
                tBHiddenDangerExam.setPost(null);
            }
            String riskId = request.getParameter("riskId.id");
            if(StringUtil.isNotEmpty(riskId)){
                RiskIdentificationEntity riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class,riskId);
                tBHiddenDangerExam.setRiskId(riskIdentificationEntity);
            }else{
                tBHiddenDangerExam.setRiskId(null);
            }
            String dutyUnitId = request.getParameter("dutyUnitId");
            TSDepart tsd = this.systemService.getEntity(TSDepart.class,dutyUnitId);
            tBHiddenDangerExam.setDutyUnit(tsd);
            String manageDutyUnitId = request.getParameter("manageDutyUnitId");
            TSDepart manageDutyUnit = this.systemService.getEntity(TSDepart.class,manageDutyUnitId);
            tBHiddenDangerExam.setManageDutyUnit(manageDutyUnit);
//            String dutyMan = tBHiddenDangerExam.getDutyMan();
//            List<TBHiddenDangerRelExamEntity> tempList = new ArrayList<TBHiddenDangerRelExamEntity>();
//            if(StringUtils.isNotBlank(dutyMan)){
//                String[] ids = dutyMan.split(",");
//                TBHiddenDangerRelExamEntity relExamEntity= null;
//                for(String id : ids){
//                    relExamEntity = new TBHiddenDangerRelExamEntity();
//                    relExamEntity.settBHiddenDangerExamEntity(tBHiddenDangerExam);
//                    relExamEntity.setDuty_man_id(id);
//                    tempList.add(relExamEntity);
//                }
//            }
//            tBHiddenDangerExam.setDutyManList(tempList);
//处理表添加一条数据
            TBHiddenDangerHandleEntity handleEntity = new TBHiddenDangerHandleEntity();
            //handleEntity.setFineType(Constants.FINE_TYPE_YINHUAN);
            handleEntity.setHiddenDanger(tBHiddenDangerExam);
            if(Constants.DEALTYPE_XIANCAHNG.equals(dealType)){//现场处理
                handleEntity.setReviewMan(tBHiddenDangerExam.getReviewMan()!=null?tBHiddenDangerExam.getReviewMan().getId():null);
                handleEntity.setModifyDate(tBHiddenDangerExam.getExamDate());
                handleEntity.setModifyShift(tBHiddenDangerExam.getShift());
                handleEntity.setRectMeasures(tBHiddenDangerExam.getHandleEntity().getRectMeasures());
                handleEntity.setReviewReport(tBHiddenDangerExam.getHandleEntity().getReviewReport());
//                String du = tBHiddenDangerExam.getDutyMan();
//                if(StringUtils.isNotBlank(du)){
//                    handleEntity.setModifyMan(du.split(",")[0]);//取第一个责任人为整改人
//                }
                handleEntity.setModifyMan(tBHiddenDangerExam.getDutyMan());
                handleEntity.setReviewDate(tBHiddenDangerExam.getExamDate());
                handleEntity.setReviewShift(tBHiddenDangerExam.getShift());
                handleEntity.setReviewResult(Constants.REVIEWSTATUS_OK);
                if(reportStatus.equals("0")){
                    handleEntity.setHandlelStatus(Constants.HANDELSTATUS_DRAFT);
                }else{
                    handleEntity.setHandlelStatus(Constants.REVIEWSTATUS_PASS);
                }


                Date curdate = new Date();
                handleEntity.setReportStatus("1");
                handleEntity.setReportName(sessionUser.getRealName());


                handleEntity.setUpdateDate(curdate);
                handleEntity.setUpdateBy(sessionUser.getUserName());
                handleEntity.setUpdateName(sessionUser.getRealName());

            }else{//限期整改
                if(reportStatus.equals("0")){
                    Date curdate = new Date();
                    handleEntity.setHandlelStatus(Constants.HANDELSTATUS_DRAFT);
                    handleEntity.setReportStatus("1");
                    handleEntity.setReportName(sessionUser.getRealName());


                    handleEntity.setUpdateDate(curdate);
                    handleEntity.setUpdateBy(sessionUser.getUserName());
                    handleEntity.setUpdateName(sessionUser.getRealName());
                }else{
                    Date curdate = new Date();
                    handleEntity.setHandlelStatus(Constants.HANDELSTATUS_REPORT);
                    handleEntity.setReportStatus("1");
                    handleEntity.setReportName(sessionUser.getRealName());


                    handleEntity.setUpdateDate(curdate);
                    handleEntity.setUpdateBy(sessionUser.getUserName());
                    handleEntity.setUpdateName(sessionUser.getRealName());
                }

            }

            tBHiddenDangerExam.setHandleEntity(handleEntity);

            if(tBHiddenDangerExam.getProblemDesc() != null && tBHiddenDangerExam.getProblemDesc() != ""){
                tBHiddenDangerExam.setProblemDesc(tBHiddenDangerExam.getProblemDesc().trim());
            }
            if(tBHiddenDangerExam.getRemark() != null && tBHiddenDangerExam.getRemark() != ""){
                tBHiddenDangerExam.setRemark(tBHiddenDangerExam.getRemark().trim());
            }
            tBHiddenDangerExam.setHiddenNatureOriginal(tBHiddenDangerExam.getHiddenNature());
            String riskManageHazardFactorId = request.getParameter("riskManageHazardFactorId");
            if(StringUtil.isNotEmpty(riskManageHazardFactorId)){
                RiskManageHazardFactorEntity riskManageHazardFactorEntity = this.systemService.getEntity(RiskManageHazardFactorEntity.class,riskManageHazardFactorId);
                if(riskManageHazardFactorEntity!=null){
                    tBHiddenDangerExam.setRiskId(riskManageHazardFactorEntity.getRisk());
                }
            }

            String riskIdHazard = request.getParameter("riskIdHazard");
            if(StringUtil.isNotEmpty(riskIdHazard)){
                RiskIdentificationEntity riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class,riskIdHazard);
                tBHiddenDangerExam.setRiskId(riskIdentificationEntity);
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
                tBHiddenDangerExam.setHiddenNumber(hiddenNumber);
            }



            tBHiddenDangerExamService.save(tBHiddenDangerExam);
            //添加隐患上报国家局标识
            sfService.saveOrUpdateSfHiddenRel(tBHiddenDangerExam.getId());

            //添加风险管控生成的隐患
            if(StringUtil.isNotEmpty(riskManageHazardFactorId)){
                RiskManageHazardFactorEntity riskManageHazardFactorEntity = this.systemService.getEntity(RiskManageHazardFactorEntity.class,riskManageHazardFactorId);
                RiskManageRelHd riskManageRelHd = new RiskManageRelHd();
                riskManageRelHd.setRiskManageHazardFactor(riskManageHazardFactorEntity);
                riskManageRelHd.setHd(tBHiddenDangerExam);
                this.systemService.save(riskManageRelHd);
                RiskManageHazardFactorEntity entity = this.systemService.getEntity(RiskManageHazardFactorEntity.class,riskManageHazardFactorId);
                entity.setImplDetail("未落实,已形成隐患");
                this.systemService.saveOrUpdate(entity);

                RiskManageTaskEntity task = entity.getRiskManageTaskEntity();
                List<RiskManageHazardFactorEntity> riskManageHazardFactorEntityList = task.getRiskManageHazardFactorEntityList();
                int numFinished = 0;
                if(null!=riskManageHazardFactorEntityList && riskManageHazardFactorEntityList.size()>0){

                    for(int i=0;i<riskManageHazardFactorEntityList.size();i++){
                        if(StringUtil.isNotEmpty(riskManageHazardFactorEntityList.get(i).getImplDetail())){
                            numFinished ++;
                        }
                    }
                }
                if(numFinished>=riskManageHazardFactorEntityList.size()){
                    task.setHandleStatus("1");
                    this.systemService.saveOrUpdate(task);
                    String sql = "SELECT id FROM t_b_risk_manage_task WHERE handle_status = '0' AND task_all_id = '"+task.getTaskAllId()+"'";

                    //保存国家局上报信息
                    sfService.saveSfRiskControl(task.getTaskAllId(),task.getRisk().getId());

                    List<String> temp = this.systemService.findListbySql(sql);
                    if(temp==null||temp.size()==0){
                        RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,task.getTaskAllId());
                        riskManageTaskAllEntity.setStatus("1");
                        this.systemService.saveOrUpdate(riskManageTaskAllEntity);
                    }
                }
            }

            //添加风险管控生成的隐患 岗位
            String riskManagePostHazardFactorId = request.getParameter("riskManagePostHazardFactorId");
            if(StringUtil.isNotEmpty(riskManagePostHazardFactorId)){
                RiskManagePostHazardFactorEntity riskManagePostHazardFactorEntity = this.systemService.getEntity(RiskManagePostHazardFactorEntity.class,riskManagePostHazardFactorId);
                RiskManagePostRelHd riskManagePostRelHd = new RiskManagePostRelHd();
                riskManagePostRelHd.setRiskManagePostHazardFactor(riskManagePostHazardFactorEntity);
                riskManagePostRelHd.setHd(tBHiddenDangerExam);
                this.systemService.save(riskManagePostRelHd);
                RiskManagePostHazardFactorEntity entity = this.systemService.getEntity(RiskManagePostHazardFactorEntity.class,riskManagePostHazardFactorId);
                entity.setImplDetail("未落实,已形成隐患");
                this.systemService.saveOrUpdate(entity);

                RiskManagePostTaskEntity postTask = entity.getRiskManagePostTaskEntity();
                List<RiskManagePostHazardFactorEntity> riskManagePostHazardFactorEntityList = postTask.getRiskManagePostHazardFactorEntityList();
                int numFinished = 0;
                if(null!=riskManagePostHazardFactorEntityList && riskManagePostHazardFactorEntityList.size()>0){

                    for(int i=0;i<riskManagePostHazardFactorEntityList.size();i++){
                        if(StringUtil.isNotEmpty(riskManagePostHazardFactorEntityList.get(i).getImplDetail())){
                            numFinished ++;
                        }
                    }
                }
                if(numFinished>=riskManagePostHazardFactorEntityList.size()){
                    postTask.setHandleStatus(com.sddb.common.Constants.RISK_MANAGE_TASK_RISK_STATUS_FINISHED);
                    this.systemService.saveOrUpdate(postTask);
                    String sql = "SELECT id FROM t_b_risk_manage_post_task WHERE handle_status = '0' AND post_task_all_id = '"+postTask.getPostTaskAllId()+"'";
                    List<String> temp = this.systemService.findListbySql(sql);
                    if(temp==null||temp.size()==0){
                        RiskManagePostTaskAllEntity riskManagePostTaskAllEntity = systemService.getEntity(RiskManagePostTaskAllEntity.class,postTask.getPostTaskAllId());
                        if(riskManagePostTaskAllEntity!=null){
                            riskManagePostTaskAllEntity.setStatus("1");
                            this.systemService.saveOrUpdate(riskManagePostTaskAllEntity);
                        }


                        RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,postTask.getPostTaskAllId());
                        if(riskManageTaskAllEntity!=null){
                            riskManageTaskAllEntity.setStatus("1");
                            this.systemService.saveOrUpdate(riskManageTaskAllEntity);
                        }

                    }
                }

            }

            String taskId = request.getParameter("taskId");
            if(StringUtils.isNotBlank(taskId)){
                //TODO 隐患管控清单关联表添加纪录    houbin
                TBTaskOrderHidden orderHidden = new TBTaskOrderHidden();
                TBTaskManagerOrder taskManagerOrder = systemService.getEntity(TBTaskManagerOrder.class,taskId);
                orderHidden.setTaskManagerOrder(taskManagerOrder);
                orderHidden.setHiddenDangerExamEntity(tBHiddenDangerExam);
//                orderHidden.setCreateBy(user.getUserName());
//                orderHidden.setCreateName(user.getRealName());
//                orderHidden.setCreateDate(new Date());
                systemService.save(orderHidden);

            }
            //提交隐患添加短信提醒，通知整改人（责任单位电话）
            try{
                if(reportStatus.equals(Constants.HANDELSTATUS_REPORT)  && tBHiddenDangerExam.getDealType().equals("1")){
                    String createName = sessionUser.getRealName();
                    String createTime = DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm");
                    String addressName =addressInfoEntity.getAddress();
                    String problemDesc=tBHiddenDangerExam.getProblemDesc();
                    String limitDate= DateUtils.formatDate(tBHiddenDangerExam.getLimitDate(), "yyyy-MM-dd");
                    String content = "【双防平台】通知：尊敬的双防用户，您好！"+createName+"发布了一条新的隐患：" +createTime+
                            " 地点："+addressName+"，内容：" +problemDesc+
                            "，限期日期为："+limitDate;
                    TSDepart tsDepart = tBHiddenDangerExam.getDutyUnit();
                    String pho1 = tsDepart.getPho1();
                    String tsDepartid=tsDepart.getId();
                    //sql 待测试
                    String sql="select openid from t_b_WexinOpenId  where departId='"+tsDepartid+"'";
                    List <String> openlist=systemService.findListbySql(sql);
                    if(openlist.size()>0) {
                        for (String openid : openlist) {
                            postAddTemplate(openid,createName ,createTime,addressName, problemDesc, limitDate);
                        }
                    }else if(StringUtils.isNotBlank(pho1)){
                        SMSSenderUtil.sendSMS(content,pho1, Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
                    }
                    List<String> mobilePhone=systemService.findListbySql("select u.mobilePhone from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.realname='" + tBHiddenDangerExam.getDutyMan() + "'");
                    if(mobilePhone != null && mobilePhone.size()>0 && StringUtils.isNotBlank(mobilePhone.get(0))){
                        SMSSenderUtil.sendSMS(content,mobilePhone.get(0), Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
                    }
                    //郭屯特殊处理开始
                    String guotun = ResourceUtil.getConfigByName("guotun");
                    if(guotun.equals("true")){
                        List<String>  mobilePhoneGT=systemService.findListbySql("select mobilePhone from t_s_user  where id  = '"+tBHiddenDangerExam.getManageDutyManId()+"'");
                        if(mobilePhoneGT != null && mobilePhoneGT.size()>0 && StringUtils.isNotBlank(mobilePhoneGT.get(0))){
                            SMSSenderUtil.sendSMS(content,mobilePhoneGT.get(0), Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
                        }
                    }
                    //郭屯特殊处理结束
                    //企业微信通知
                    String dutyman= tBHiddenDangerExam.getDutyMan();
                    List<String> weChatPhones=systemService.findListbySql("select u.weChatPhone from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.realname='" + dutyman + "'");
                    if(weChatPhones != null && weChatPhones.size()>0 && StringUtils.isNotBlank(weChatPhones.get(0))){
                        WebChatUtil.sendWeChatMessageToUser(weChatPhones.get(0),content);
                    }
                }
            }catch(Exception e){
                logger.error(ExceptionUtil.getExceptionMessage(e));
            }


            if(reportStatus.equals(Constants.HANDELSTATUS_REPORT)  && tBHiddenDangerExam.getDealType().equals("1") && !(tBHiddenDangerExam.getHiddenNature().equals("1"))){
                // 添加升级任务
                String job_name = tBHiddenDangerExam.getId();

                Date limitDate = tBHiddenDangerExam.getLimitDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String limitStr = sdf.format(limitDate);
                limitStr = limitStr + " 23:59:59";
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    limitDate = sdf.parse(limitStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(limitDate);
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH) + 1;
                int day = calendar.get(calendar.DATE);
                int hour = calendar.get(calendar.HOUR_OF_DAY);
                int minute = calendar.get(calendar.MINUTE);
                int second = calendar.get(calendar.SECOND);

                StringBuffer sb = new StringBuffer();
                sb.append(second).append(" ").append(minute).append(" ").append(hour).append(" ").append(day).append(" ").append(month).append(" ").append("? ").append(year);
                try{
                    qrtzManagerServiceI.addJob(scheduler,tBHiddenDangerExam.getId(), job_name, QuartzJob.class, sb.toString());
                }catch(Exception e){
                    e.printStackTrace();
                }

                //添加升级任务结
            }
            if(Constants.HANDELSTATUS_REPORT.equals(reportStatus)  && Constants.DEALTYPE_XIANQI.equals(tBHiddenDangerExam.getDealType())){
                riskUpgradeService.execute(tBHiddenDangerExam.getId());
            }

            systemService.addLog("隐患检查\""+tBHiddenDangerExam.getId()+"\"添加成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
            if(reportStatus.equals(Constants.HANDELSTATUS_REPORT)){
                tBHiddenDangerHandleLogService.addLog(tBHiddenDangerExam.getId(), Globals.Log_Type_ADD,"隐患检查录入并上报成功");
            } else if(reportStatus.equals(Constants.HANDELSTATUS_DRAFT)){
                tBHiddenDangerHandleLogService.addLog(tBHiddenDangerExam.getId(), Globals.Log_Type_ADD,"隐患检查草稿录入成功");
            }
            if (!Constants.HANDELSTATUS_DRAFT.equals(handleEntity.getHandlelStatus())) {
                TBHiddenDangerHandleStepEntity handleStepEntity = new TBHiddenDangerHandleStepEntity();
                handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
                handleStepEntity.setHandleStep(1);
                handleStepEntity.setHandleDate(tBHiddenDangerExam.getCreateDate());
                handleStepEntity.setHandleMan(tBHiddenDangerExam.getCreateName());
                handleStepEntity.setHandleStatus(tBHiddenDangerExam.getHandleEntity().getHandlelStatus());

                systemService.save(handleStepEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "隐患检查添加失败";
            systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_INSERT);
            //这里暂时注掉危险源
            //throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 根据关键字获取相关联的风险id
     * @param keywords
     * @return
     */
//    private String getDangerIdByKeyword(String keywords) {
//        String id = null;
//        if (StringUtil.isNotEmpty(keywords)) {
//            String[] keywordsArr = keywords.split(",");
//            String sql = "select id, ye_mhazard_desc from t_b_danger_source where ( origin='1' or audit_status='4')";
//            List<Map<String, Object>> list = systemService.findForJdbc(sql);
//            if (list != null && list.size() >0) {
//                int max = 0;
//                for (Map<String, Object> entity : list) {
//                    String name = (String) entity.get("ye_mhazard_desc");
//                    if (StringUtil.isNotEmpty(name)) {
//                        int count = 0;
//                        for (String keyword : keywordsArr) {
//                            if (name.contains(keyword)){
//                                count++;
//                            }
//                        }
//                        if (count > max) {
//                            id = (String) entity.get("id");
//                            max = count;
//                        }
//                    }
//                }
//            }
//
//        }
//        return id;
//    }



    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TBHiddenDangerExamEntity tBHiddenDangerExam, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        //检查类型
        String examType = request.getParameter("examType");

        //String belongMine = (String) ContextHolderUtils.getSession().getAttribute("belongMine");

        String queryHandleStatus = request.getParameter("queryHandleStatus");
        if (queryHandleStatus == null || queryHandleStatus.equals("")) {
            queryHandleStatus = "00";
        }
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerExamEntity.class, dataGrid);


        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBHiddenDangerExam, request.getParameterMap());
        // session user
        TSUser sessionUser = ResourceUtil.getSessionUserName();

        try {
            cq.createAlias("address","address");
            cq.eq("address.isShowData","1");
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
            //自定义追加查询条件
            cq.eq("examType", examType);
            String taskAllId = request.getParameter("taskAllId");
            if(StringUtil.isNotEmpty(taskAllId)){
                cq.eq("taskAllId",taskAllId);
            }
            boolean isAdmin = false;
            CriteriaQuery cqru = new CriteriaQuery(TSRoleUser.class);
            try{
                cqru.eq("TSUser.id",sessionUser.getId());
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
                cq.eq("createBy", sessionUser.getUserName());
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tBHiddenDangerExamService.getDataGridReturn(cq, true);
        String lilou = ResourceUtil.getConfigByName("ewmgn");
        if (dataGrid != null && dataGrid.getResults() != null) {
            if (dataGrid.getResults().size() > 0){
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
                                TSUser user = systemService.getEntity(TSUser.class, userid);
                                if (user != null) {
                                    if(StringUtils.isNotBlank(lilou) && "true".equals(lilou)){
                                        if(names == ""){
                                            names = names + user.getRealName();
                                        }else{
                                            names = names + "," + user.getRealName();
                                        }
                                    }else {
                                        if (names == "") {
                                            names = names + user.getRealName() + "-" + user.getUserName();
                                        } else {
                                            names = names + "," + user.getRealName() + "-" + user.getUserName();
                                        }
                                    }

                                }else if(StringUtil.isNotEmpty(userid)){
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

                    String itemUserId = t.getItemUserId();
                    if (StringUtils.isNotBlank(itemUserId)) {
                        String[] userIdArray = itemUserId.split(",");
                        StringBuffer itemUserNameSb = new StringBuffer();
                        for (String userId : userIdArray) {
                            TSUser user = systemService.getEntity(TSUser.class, userId);
                            if (user != null) {
                                if (StringUtils.isNotBlank(itemUserNameSb.toString())) {
                                    itemUserNameSb.append(",");
                                }
                                itemUserNameSb.append(user.getRealName());
                            }
                        }
                        t.setItemUserNameTemp(itemUserNameSb.toString());
                    }

//                String dutyMan = t.getDutyMan();
//                StringBuffer sb = new StringBuffer();
//                if(StringUtils.isNotBlank(dutyMan)){
//                    String ids [] = dutyMan.split(",");
//                    for(String id : ids){
//                        TSUser user = systemService.getEntity(TSUser.class,id);
//                        if(user != null){
//                            if(StringUtils.isNotBlank(sb.toString())){
//                                sb.append(",");
//                            }
//                            sb.append(user.getRealName());
//                        }
//                    }
//                }
                    if (t.getReviewMan() != null && t.getReviewMan().getId().length() > 0) {

                    } else {
                        t.setReviewMan(null);
                    }
                    t.setDutyManTemp(t.getDutyMan());
                }
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }



    @RequestMapping(params = "getHandleStepList")
    @ResponseBody
    public JSONArray getHandleStepList(HttpServletRequest request) throws Exception {
        String examId = request.getParameter("examId");

        JSONArray ja = new JSONArray();
        if(StringUtils.isNotBlank(examId)){
            CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleStepEntity.class);
            cq.eq("hiddenDanger.id",examId);
            cq.addOrder("handleStep", SortDirection.asc);
            cq.add();
            List<TBHiddenDangerHandleStepEntity> handleStepList = systemService.getListByCriteriaQuery(cq,false);
            if(!handleStepList.isEmpty() && handleStepList.size() > 0 ){
                //上报-整改-复查通过，最短三条
                for (TBHiddenDangerHandleStepEntity t : handleStepList) {

                    String status = t.getHandleStatus();
                    StringBuffer sb = new StringBuffer();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    if (status.equals(Constants.HANDELSTATUS_REPORT)) {
                        t.setHandleTypeTemp("上报");
                        sb.append("上报人：").append(t.getHandleMan()).append("　上报时间：").append(sdf.format(t.getHandleDate())).append("");
                    }
                    if (status.equals(Constants.HANDELSTATUS_ROLLBACK_REPORT)) {
                        t.setHandleTypeTemp("退回上报");
                        sb.append("驳回人：").append(t.getHandleMan()).append("　驳回时间：").append(sdf.format(t.getHandleDate())).append("　");
                        sb.append("驳回备注：").append(t.getRemark());
                    }
                    if (status.equals(Constants.HANDELSTATUS_REVIEW)) {
                        t.setHandleTypeTemp("整改");
                        sb.append("整改人：").append(t.getHandleMan()).append("　整改时间：").append(sdf.format(t.getHandleDate())).append("  ");
                    }
                    if (status.equals(Constants.REVIEWSTATUS_PASS)) {
                        t.setHandleTypeTemp("复查通过");
                        sb.append("复查人：").append(t.getHandleMan()).append("　复查时间：").append(sdf.format(t.getHandleDate())).append("  ");
                    }
                    if (status.equals(Constants.HANDELSTATUS_ROLLBACK_CHECK)) {
                        t.setHandleTypeTemp("复查未通过");
                        sb.append("复查人：").append(t.getHandleMan()).append("　复查时间：").append(sdf.format(t.getHandleDate())).append("  ");
                        sb.append("复查备注：").append(t.getRemark());
                    }

                    JSONObject jo = new JSONObject();
                    jo.put("title", t.getHandleTypeTemp());
                    jo.put("content", sb.toString());
                    ja.add(jo);

                    if (t.getHandleStep() == handleStepList.size()) {
                        //list最后一条，写入最简的后续流程，前台以灰色显示
                        boolean flag = true;
                        String currentStatus = status;
                        while (flag){
                            if (currentStatus.equals(Constants.HANDELSTATUS_REPORT)) {
                                //上报
                                JSONObject jo1 = new JSONObject();
                                jo1.put("title", "整改");
                                jo1.put("content", "未整改");
                                ja.add(jo1);
                                currentStatus = Constants.HANDELSTATUS_REVIEW;
                            }
                            if (currentStatus.equals(Constants.HANDELSTATUS_ROLLBACK_REPORT)) {
                                //退回上报
                                JSONObject jo1 = new JSONObject();
                                jo1.put("title", "上报");
                                jo1.put("content", "重新编辑上报");
                                ja.add(jo1);
                                currentStatus = Constants.HANDELSTATUS_REPORT;
                            }
                            if (currentStatus.equals(Constants.HANDELSTATUS_REVIEW)) {
                                //整改
                                JSONObject jo1 = new JSONObject();
                                jo1.put("title", "复查");
                                jo1.put("content", "未复查");
                                ja.add(jo1);
                                flag = false;
                            }
                            if (currentStatus.equals(Constants.REVIEWSTATUS_PASS)) {
                                //复查通过
                                flag = false;
                            }
                            if (currentStatus.equals(Constants.HANDELSTATUS_ROLLBACK_CHECK)) {
                                //复查未通过
                                JSONObject jo1 = new JSONObject();
                                jo1.put("title", "整改");
                                jo1.put("content", "重新整改");
                                ja.add(jo1);
                                currentStatus = Constants.HANDELSTATUS_REVIEW;
                            }
                            if (Constants.HANDELSTATUS_DRAFT.equals(currentStatus)) {
                                currentStatus = Constants.HANDELSTATUS_REPORT;
                            }
                        }
                    }
                }
            }
        }
        return ja;
    }


    /**
     * 更新隐患检查
     *
     * @return
     */
    @RequestMapping(params = "doUpdateXiezhuang")
    @ResponseBody
    public AjaxJson doUpdateXiezhuang(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "隐患检查更新成功";
        String id = request.getParameter("id");
        if(StringUtil.isNotEmpty(id)){
            TBHiddenDangerExamEntity tBHiddenDangerExam = systemService.getEntity(TBHiddenDangerExamEntity.class,id);
            if(tBHiddenDangerExam!=null){
                String riskId = request.getParameter("riskId.id");
                if(StringUtil.isNotEmpty(riskId)){
                    RiskIdentificationEntity riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class,riskId);
                    tBHiddenDangerExam.setRiskId(riskIdentificationEntity);
                }else{
                    tBHiddenDangerExam.setRiskId(null);
                }
                String hazardFactorId = request.getParameter("hazardFactorId");
                String hazardFactorName = request.getParameter("hazardFactorName");
                tBHiddenDangerExam.setHazardFactorId(hazardFactorId);
                tBHiddenDangerExam.setHazardFactorName(hazardFactorName);
                systemService.saveOrUpdate(tBHiddenDangerExam);
            }
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBHiddenDangerExamEntity tBHiddenDangerExam, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "隐患检查更新成功";
        String gzap = tBHiddenDangerExam.getGzap();
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        String reportStatus = request.getParameter("reportStatus");
        if(StringUtils.isBlank(gzap)){
            tBHiddenDangerExam.setGzap("0");
        }
        //List<TBFineInfoEntity> fineList = fineMainPage.getFineList();
        String dealType = tBHiddenDangerExam.getDealType();
        TBHiddenDangerExamEntity t = tBHiddenDangerExamService.get(TBHiddenDangerExamEntity.class, tBHiddenDangerExam.getId());
        try {
            //如果关键字有改变则重新匹配风险
//            if (!tBHiddenDangerExam.getKeyWords().equals(t.getKeyWords())) {
//                String dangerId = getDangerIdByKeyword(tBHiddenDangerExam.getKeyWords());
//                if (StringUtil.isNotEmpty(dangerId)) {
//                    TBDangerSourceEntity dangerSourceEntity = new TBDangerSourceEntity();
//                    dangerSourceEntity.setId(dangerId);
//                    tBHiddenDangerExam.setDangerId(dangerSourceEntity);
//                }
//            }
            String address = request.getParameter("address.address");
            TBAddressInfoEntity addressInfoEntity = systemService.getEntity(TBAddressInfoEntity.class,address);
            tBHiddenDangerExam.setAddress(addressInfoEntity);
            String post = request.getParameter("post.postname");
            if(StringUtil.isNotEmpty(post)){
                TBPostManageEntity postManageEntity= systemService.getEntity(TBPostManageEntity.class,post);
                tBHiddenDangerExam.setPost(postManageEntity);
            }else{
                tBHiddenDangerExam.setPost(null);
                t.setPost(null);
            }
            String riskId = request.getParameter("riskId.id");
            if(StringUtil.isNotEmpty(riskId)){
                RiskIdentificationEntity riskIdentificationEntity = systemService.getEntity(RiskIdentificationEntity.class,riskId);
                tBHiddenDangerExam.setRiskId(riskIdentificationEntity);
            }else{
                tBHiddenDangerExam.setRiskId(null);
                t.setRiskId(null);
            }
            String dutyUnitId = request.getParameter("dutyUnitId");
            TSDepart tsd = this.systemService.getEntity(TSDepart.class,dutyUnitId);
            tBHiddenDangerExam.setDutyUnit(tsd);
//            String dutyMan = tBHiddenDangerExam.getDutyMan();
//            List<TBHiddenDangerRelExamEntity> tempList = new ArrayList<TBHiddenDangerRelExamEntity>();
//            if(StringUtils.isNotBlank(dutyMan)){
//                String[] ids = dutyMan.split(",");
//                TBHiddenDangerRelExamEntity relExamEntity= null;
//                for(String id : ids){
//                    relExamEntity = new TBHiddenDangerRelExamEntity();
//                    relExamEntity.settBHiddenDangerExamEntity(tBHiddenDangerExam);
//                    relExamEntity.setDuty_man_id(id);
//                    tempList.add(relExamEntity);
//                }
//            }
//            tBHiddenDangerExam.setDutyManList(tempList);

            String handleStatus = "";
            CriteriaQuery criteriaQuery = new CriteriaQuery(TBHiddenDangerHandleEntity.class);
            criteriaQuery.eq("hiddenDanger.id",tBHiddenDangerExam.getId());
            criteriaQuery.add();
            List<TBHiddenDangerHandleEntity> handleEntities = systemService.getListByCriteriaQuery(criteriaQuery,false);
            if(!handleEntities.isEmpty() && handleEntities.size()>0){
                handleStatus = handleEntities.get(0).getHandlelStatus();
            }

            if ((Constants.HANDELSTATUS_ROLLBACK_REPORT.equals(handleStatus) || Constants.HANDELSTATUS_ROLLBACK_CHECK.equals(handleStatus))&&"1".equals(reportStatus)) {
                // 只有退回上报或复查未通过状态下，才对编辑操作做“上报”记录
                int handleStep = 0;
                CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleStepEntity.class);
                cq.eq("hiddenDanger.id",tBHiddenDangerExam.getId());
                cq.addOrder("handleStep", SortDirection.asc);
                cq.add();
                List<TBHiddenDangerHandleStepEntity> handleStepList = systemService.getListByCriteriaQuery(cq,false);
                if(!handleStepList.isEmpty() && handleStepList.size() > 0 ){
                    handleStep = handleStepList.get((handleStepList.size()-1)).getHandleStep() + 1;

                    TBHiddenDangerHandleStepEntity handleStepEntity = new TBHiddenDangerHandleStepEntity();
                    handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
                    handleStepEntity.setHandleStep(handleStep);
                    handleStepEntity.setHandleDate(new Date());

                    TSUser editUser = ResourceUtil.getSessionUserName();
                    handleStepEntity.setHandleMan(editUser.getRealName());
                    if(Constants.DEALTYPE_XIANCAHNG.equals(dealType)){//现场处理
                        handleStepEntity.setHandleStatus(Constants.REVIEWSTATUS_PASS);
                    }else{//限期整改
                        handleStepEntity.setHandleStatus(Constants.HANDELSTATUS_REPORT);
                    }

                    systemService.save(handleStepEntity);
                }
            }
            String flag = request.getParameter("flag");
            if(!"queryList".equals(flag)){
                //处理表添加一条数据
                CriteriaQuery handleCq = new CriteriaQuery(TBHiddenDangerHandleEntity.class);
                handleCq.eq("hiddenDanger.id",tBHiddenDangerExam.getId());
                handleCq.add();
                List<TBHiddenDangerHandleEntity> handleEntityList = systemService.getListByCriteriaQuery(handleCq,false);

                TBHiddenDangerHandleEntity handleEntity = new TBHiddenDangerHandleEntity();
                if(handleEntityList != null && handleEntityList.size()>0){
                    handleEntity = handleEntityList.get(0);
                }

                handleEntity.setHiddenDanger(tBHiddenDangerExam);
                if(Constants.DEALTYPE_XIANCAHNG.equals(dealType)){//现场处理
                    handleEntity.setReviewMan(tBHiddenDangerExam.getReviewMan()!=null?tBHiddenDangerExam.getReviewMan().getId():null);
                    handleEntity.setModifyDate(tBHiddenDangerExam.getExamDate());
                    handleEntity.setModifyShift(tBHiddenDangerExam.getShift());
                    handleEntity.setModifyMan(tBHiddenDangerExam.getDutyMan());
                    handleEntity.setReviewDate(tBHiddenDangerExam.getExamDate());
                    handleEntity.setReviewShift(tBHiddenDangerExam.getShift());
                    handleEntity.setReviewResult(Constants.REVIEWSTATUS_OK);
                    if(reportStatus.equals("0")){
                        handleEntity.setHandlelStatus(Constants.HANDELSTATUS_DRAFT);
                    }else{
                        handleEntity.setHandlelStatus(Constants.REVIEWSTATUS_PASS);
                    }
                }else{//限期整改
                    if(reportStatus.equals("1")){
                        handleEntity.setHandlelStatus(Constants.HANDELSTATUS_REPORT);
                    }

                }
                handleEntity.setRollBackRemark(null);
                tBHiddenDangerExam.setHandleEntity(handleEntity);
            }


            MyBeanUtils.copyBeanNotNull2Bean(tBHiddenDangerExam, t);
//            if(Constants.DEALTYPE_XIANCAHNG.equals(dealType)){
//                t.setLimitDate(null);
//                t.setLimitShift(null);
//            }else{
//                t.setReviewMan(null);
//            }

            if(StringUtils.isBlank(tBHiddenDangerExam.getIsWithClass())){
                t.setIsWithClass(null  );
            }
            String examType=t.getExamType();
            if(Constants.DEALTYPE_XIANCAHNG.equals(dealType)){
                t.setLimitDate(null);
                t.setLimitShift(null);
            }else {
                if (Constants.HIDDENCHECK_EXAMTYPE_SHANGJI.equals(examType)) {


                } else {
                    t.setReviewMan(null);
                }
            }
            if(t.getProblemDesc()!=null && t.getProblemDesc()!=""){
                String problemupdate = t.getProblemDesc().trim();
                t.setProblemDesc(problemupdate);
            }
            if(t.getRemark()!=null && t.getRemark()!=""){
                String remarkupdate = t.getRemark().trim();
                t.setRemark(remarkupdate);
            }
            ///自动计算关联风险,待完成

            //添加国家局上报标识
            sfService.saveOrUpdateSfHiddenRel(t.getId());
            tBHiddenDangerExamService.updateEntitie(t);
            if(Constants.HANDELSTATUS_DRAFT.equals(handleStatus)&&"1".equals(reportStatus)) {
                TBHiddenDangerHandleStepEntity handleStepEntity = new TBHiddenDangerHandleStepEntity();
                if(StringUtil.isNotEmpty(t.getUpdateDate())){
                    handleStepEntity.setHandleDate(t.getUpdateDate());
                }else{
                    handleStepEntity.setHandleDate(new Date());
                }
                if(StringUtil.isEmpty(t.getUpdateName())){
                    String curUser = ResourceUtil.getSessionUserName().getRealName();
                    handleStepEntity.setHandleMan(curUser);
                }else{
                    handleStepEntity.setHandleMan(t.getUpdateName());
                }
                handleStepEntity.setHandleStatus(t.getHandleEntity().getHandlelStatus());
                handleStepEntity.setHiddenDanger(t);
                handleStepEntity.setHandleStep(1);
                systemService.save(handleStepEntity);
            }
            systemService.addLog("隐患检查信息\""+t.getId()+"\"更新成功", Globals.Log_Leavel_INFO, Globals.Log_Type_UPDATE);
            //提交隐患添加短信提醒，通知整改人（责任单位电话）
            try{
                if(reportStatus.equals(Constants.HANDELSTATUS_REPORT)  && tBHiddenDangerExam.getDealType().equals("1")){
                    String createName = sessionUser.getRealName();
                    String createTime = DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm");
                    String addressName =addressInfoEntity.getAddress();
                    String problemDesc=t.getProblemDesc();
                    String limitDate= DateUtils.formatDate(t.getLimitDate(), "yyyy-MM-dd");
                    String content = "【双防平台】通知：尊敬的双防用户，您好！"+createName+"发布了一条新的隐患：" +createTime+
                            " 地点："+addressName+"，内容：" +problemDesc+
                            "，限期日期为："+limitDate;
                    TSDepart tsDepart = t.getDutyUnit();
                    String pho1 = tsDepart.getPho1();
                    String tsDepartid=tsDepart.getId();
                    //sql 待测试
                    String sql="select openid from t_b_WexinOpenId  where departId='"+tsDepartid+"'";
                    List <String> openlist=systemService.findListbySql(sql);
                    if(openlist.size()>0) {
                        for (String openid : openlist) {
                            postAddTemplate(openid,createName ,createTime,addressName, problemDesc, limitDate);
                        }
                    }else if(StringUtils.isNotBlank(pho1)){
                        SMSSenderUtil.sendSMS(content,pho1, Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
                    }
                    //企业微信通知
                    String dutyman= t.getDutyMan();
                    List<String> weChatPhones=systemService.findListbySql("select u.weChatPhone from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.realname='" + dutyman + "'");
                    if(weChatPhones != null && weChatPhones.size()>0 && StringUtils.isNotBlank(weChatPhones.get(0))){
                        WebChatUtil.sendWeChatMessageToUser(weChatPhones.get(0),content);
                    }
                }
            }catch(Exception e){
                logger.error(ExceptionUtil.getExceptionMessage(e));
            }
            if(reportStatus.equals(Constants.HANDELSTATUS_REPORT)  && tBHiddenDangerExam.getDealType().equals("1") && !(tBHiddenDangerExam.getHiddenNature().equals("1"))){
                // 添加升级任务
                String job_name = tBHiddenDangerExam.getId();

                Date limitDate = tBHiddenDangerExam.getLimitDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String limitStr = sdf.format(limitDate);
                limitStr = limitStr + " 23:59:59";
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    limitDate = sdf.parse(limitStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(limitDate);
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH) + 1;
                int day = calendar.get(calendar.DATE);
                int hour = calendar.get(calendar.HOUR_OF_DAY);
                int minute = calendar.get(calendar.MINUTE);
                int second = calendar.get(calendar.SECOND);

                StringBuffer sb = new StringBuffer();
                sb.append(second).append(" ").append(minute).append(" ").append(hour).append(" ").append(day).append(" ").append(month).append(" ").append("? ").append(year);
                try{
                    qrtzManagerServiceI.addJob(scheduler,tBHiddenDangerExam.getId(), job_name, QuartzJob.class, sb.toString());
                }catch(Exception e){
                    e.printStackTrace();
                }
                //添加升级任务结
            }
            //升级风险点等级
            if(Constants.HANDELSTATUS_REPORT.equals(reportStatus)  && Constants.DEALTYPE_XIANQI.equals(tBHiddenDangerExam.getDealType())){
                riskUpgradeService.execute(tBHiddenDangerExam.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "隐患检查更新失败";
            systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPDATE);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 保存安全大检查
     * @param tBHiddenDangerExam
     * @param request
     * @return
     */
    @RequestMapping(params = "doAddAqdjc")
    @ResponseBody
    public AjaxJson doAddAqdjc(TBHiddenDangerExamEntity tBHiddenDangerExam, HttpServletRequest request) {
        String gzap = tBHiddenDangerExam.getGzap();
        String reportStatus = request.getParameter("reportStatus");
        if(StringUtils.isBlank(gzap)){
            tBHiddenDangerExam.setGzap("0");
        }
        TSUser sessionUser = ResourceUtil.getSessionUserName();
        AjaxJson j = new AjaxJson();
        message = "隐患检查添加成功";
        try{
            tBHiddenDangerExam.setIsLsProv(Constants.HDBIISLS_STATE_UNDO);
            tBHiddenDangerExam.setIsLsSub(Constants.HDBIISLS_STATE_UNDO);
            tBHiddenDangerExam.setReportStatus(Constants.REPORT_STATUS_N);
            String dealType = tBHiddenDangerExam.getDealType();
            //隐患处理: 限期整改  现场处理
            if(Constants.DEALTYPE_XIANCAHNG.equals(dealType)){
                //现场处理
                tBHiddenDangerExam.setLimitDate(null);
                tBHiddenDangerExam.setLimitShift(null);
            }else{
                //限期整改
                tBHiddenDangerExam.setReviewMan(null);
            }
            //地点
            String address = request.getParameter("address.address");
            TBAddressInfoEntity addressInfoEntity = systemService.getEntity(TBAddressInfoEntity.class,address);
            tBHiddenDangerExam.setAddress(addressInfoEntity);
            String dutyUnitId = request.getParameter("dutyUnitId");
            TSDepart tsd = this.systemService.getEntity(TSDepart.class,dutyUnitId);
            tBHiddenDangerExam.setDutyUnit(tsd);
//            String dutyMan = tBHiddenDangerExam.getDutyMan();
//            List<TBHiddenDangerRelExamEntity> tempList = new ArrayList<TBHiddenDangerRelExamEntity>();
//            if(StringUtils.isNotBlank(dutyMan)){
//                String[] ids = dutyMan.split(",");
//                TBHiddenDangerRelExamEntity relExamEntity= null;
//                for(String id : ids){
//                    relExamEntity = new TBHiddenDangerRelExamEntity();
//                    relExamEntity.settBHiddenDangerExamEntity(tBHiddenDangerExam);
//                    relExamEntity.setDuty_man_id(id);
//                    tempList.add(relExamEntity);
//                }
//            }
//            tBHiddenDangerExam.setDutyManList(tempList);

            //处理表添加一条数据
            TBHiddenDangerHandleEntity handleEntity = new TBHiddenDangerHandleEntity();
            handleEntity.setHiddenDanger(tBHiddenDangerExam);
            if(Constants.DEALTYPE_XIANCAHNG.equals(dealType)){//现场处理
                handleEntity.setReviewMan(tBHiddenDangerExam.getReviewMan()!=null?tBHiddenDangerExam.getReviewMan().getId():null);
                handleEntity.setModifyDate(tBHiddenDangerExam.getExamDate());
                handleEntity.setModifyShift(tBHiddenDangerExam.getShift());
//                String du = tBHiddenDangerExam.getDutyMan();
//                if(StringUtils.isNotBlank(du)){
//                    handleEntity.setModifyMan(du.split(",")[0]);//取第一个责任人为整改人
//                }
                handleEntity.setModifyMan(tBHiddenDangerExam.getDutyMan());
                handleEntity.setReviewDate(tBHiddenDangerExam.getExamDate());
                handleEntity.setReviewShift(tBHiddenDangerExam.getShift());
                handleEntity.setReviewResult(Constants.REVIEWSTATUS_OK);
                handleEntity.setHandlelStatus(Constants.REVIEWSTATUS_PASS);
            }else{//限期整改
                if(reportStatus.equals("0")){
                    handleEntity.setHandlelStatus(Constants.HANDELSTATUS_DRAFT);
                }else{
                    handleEntity.setHandlelStatus(Constants.HANDELSTATUS_REPORT);
                }

            }

            tBHiddenDangerExam.setHandleEntity(handleEntity);
            //隐患性质？？
            //  tBHiddenDangerExam.setHiddenNature(StringUtils.isBlank(tBHiddenDangerExam.getHiddenNature())?"0":"1");
            String problemgdjc = tBHiddenDangerExam.getProblemDesc().trim();
            tBHiddenDangerExam.setProblemDesc(problemgdjc);
            tBHiddenDangerExam.setHiddenNatureOriginal(tBHiddenDangerExam.getHiddenNature());
            //自动计算关联风险,待完成

            tBHiddenDangerExamService.save(tBHiddenDangerExam);

            //隐患发短信
            //提交隐患添加短信提醒，通知整改人（责任单位电话）
            try{
                if(reportStatus.equals(Constants.HANDELSTATUS_REPORT)  && tBHiddenDangerExam.getDealType().equals("1")){
                    String createName = sessionUser.getRealName();
                    String createTime = DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm");
                    String addressName =addressInfoEntity.getAddress();
                    String problemDesc=tBHiddenDangerExam.getProblemDesc();
                    String limitDate= DateUtils.formatDate(tBHiddenDangerExam.getLimitDate(), "yyyy-MM-dd");
                    String content = "【双防平台】通知：尊敬的双防用户，您好！"+createName+"发布了一条新的隐患：" +createTime+
                            " 地点："+addressName+"，内容：" +problemDesc+
                            "，限期日期为："+limitDate;
                    TSDepart tsDepart = tBHiddenDangerExam.getDutyUnit();
                    String pho1 = tsDepart.getPho1();
                    String tsDepartid=tsDepart.getId();
                    //sql 待测试
                    String sql="select openid from t_b_WexinOpenId  where departId='"+tsDepartid+"'";
                    List <String> openlist=systemService.findListbySql(sql);
                    if(openlist.size()>0) {
                        for (String openid : openlist) {
                            postAddTemplate(openid,createName ,createTime,addressName, problemDesc, limitDate);
                        }
                    }else if(StringUtils.isNotBlank(pho1)){
                        SMSSenderUtil.sendSMS(content,pho1, Constants.SMS_TYPE_HIDDEN_DANGER_REPORT);
                    }
                    //企业微信通知
                    String dutyman= tBHiddenDangerExam.getDutyMan();
                    List<String> weChatPhones=systemService.findListbySql("select u.weChatPhone from t_s_user u,t_s_base_user bu where bu.delete_flag=0 and u.id=bu.id and bu.realname='" + dutyman + "'");
                    if(weChatPhones != null && weChatPhones.size()>0 && StringUtils.isNotBlank(weChatPhones.get(0))){
                        WebChatUtil.sendWeChatMessageToUser(weChatPhones.get(0),content);
                    }
                }
            }catch(Exception e){
                logger.error(ExceptionUtil.getExceptionMessage(e));
            }

            //隐患升级
            if(reportStatus.equals(Constants.HANDELSTATUS_REPORT)  && tBHiddenDangerExam.getDealType().equals("1") && !(tBHiddenDangerExam.getHiddenNature().equals("1"))){
                // 添加升级任务
                String job_name = tBHiddenDangerExam.getId();

                Date limitDate = tBHiddenDangerExam.getLimitDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String limitStr = sdf.format(limitDate);
                limitStr = limitStr + " 23:59:59";
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    limitDate = sdf.parse(limitStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(limitDate);
                int year = calendar.get(calendar.YEAR);
                int month = calendar.get(calendar.MONTH) + 1;
                int day = calendar.get(calendar.DATE);
                int hour = calendar.get(calendar.HOUR_OF_DAY);
                int minute = calendar.get(calendar.MINUTE);
                int second = calendar.get(calendar.SECOND);

                StringBuffer sb = new StringBuffer();
                sb.append(second).append(" ").append(minute).append(" ").append(hour).append(" ").append(day).append(" ").append(month).append(" ").append("? ").append(year);
                try{
                    qrtzManagerServiceI.addJob(scheduler,tBHiddenDangerExam.getId(), job_name, QuartzJob.class, sb.toString());
                }catch(Exception e){
                    e.printStackTrace();
                }

                //添加升级任务结
            }
            //风险升级
            if(Constants.HANDELSTATUS_REPORT.equals(reportStatus)  && Constants.DEALTYPE_XIANQI.equals(tBHiddenDangerExam.getDealType())){
                riskUpgradeService.execute(tBHiddenDangerExam.getId());
            }
            systemService.addLog("隐患检查\""+tBHiddenDangerExam.getId()+"\"添加成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);

            //非草稿状态下保存处理历史
            if (!Constants.HANDELSTATUS_DRAFT.equals(handleEntity.getHandlelStatus())) {
                TBHiddenDangerHandleStepEntity handleStepEntity = new TBHiddenDangerHandleStepEntity();
                handleStepEntity.setHiddenDanger(tBHiddenDangerExam);
                handleStepEntity.setHandleStep(1);
                handleStepEntity.setHandleDate(tBHiddenDangerExam.getCreateDate());

//                CriteriaQuery cq1 = new CriteriaQuery(TSUser.class);
//                cq1.eq("userName", tBHiddenDangerExam.getCreateBy());
//                cq1.eq("realName", tBHiddenDangerExam.getCreateName());
//                cq1.add();
//                List<TSUser> userList = systemService.getListByCriteriaQuery(cq1, false);
//                if (!userList.isEmpty() && userList.size() > 0) {
//                    handleStepEntity.setHandleMan(userList.get(0).getRealName());
//                }
                handleStepEntity.setHandleMan(tBHiddenDangerExam.getCreateName());
                handleStepEntity.setHandleStatus(tBHiddenDangerExam.getHandleEntity().getHandlelStatus());

                systemService.save(handleStepEntity);
            }

        }catch(Exception e){
            e.printStackTrace();
            message = "隐患检查添加失败";
            systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 批量删除隐患检查
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        message = "隐患检查删除成功";
        try{
            for(String id:ids.split(",")){
                TBHiddenDangerExamEntity tBHiddenDangerExam = systemService.getEntity(TBHiddenDangerExamEntity.class, id);

                String sql = "delete from t_b_hidden_danger_handle where hidden_danger_id='" + tBHiddenDangerExam.getId() + "'";
                TBReportDeleteIdEntity reportDeleteIdEntity = new TBReportDeleteIdEntity();
                reportDeleteIdEntity.setDeleteId(tBHiddenDangerExam.getHandleEntity().getId());
                reportDeleteIdEntity.setType("hd");
                systemService.save(reportDeleteIdEntity);
                systemService.executeSql(sql);

                //2.清空责任人表
                sql = "delete from t_b_hidden_danger_exam_rel where bus_id='" + tBHiddenDangerExam.getId() + "'";
                systemService.executeSql(sql);

                sql = "delete from t_b_hidden_danger_exam where id='" + tBHiddenDangerExam.getId() + "'";
                systemService.executeSql(sql);

                //添加隐患上报国家局标识
                sfService.deleteSfHiddenRel(tBHiddenDangerExam.getId());

                systemService.addLog("隐患检查\""+tBHiddenDangerExam.getId()+"\"删除成功", Globals.Log_Leavel_INFO, Globals.Log_Type_DEL);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "隐患检查删除失败";
            systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }



    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TBHiddenDangerExamEntity tBHiddenDangerExam, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        //检查类型
        String examType = request.getParameter("examType");
        String taskAllId = request.getParameter("taskAllId");
        String queryHandleStatus = request.getParameter("queryHandleStatus");

        List<TBHiddenDangerExamEntity> list = new ArrayList<>();
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerExamEntity.class,dataGrid);
        String idTemp = request.getParameter("ids");
        if(StringUtils.isNotBlank(idTemp)&&idTemp!=null){
            List<String> idList = new ArrayList<>();
            for(String id : idTemp.split(",")){
                idList.add(id);
            }

            cq.in("id",idList.toArray());
            if(StringUtil.isNotEmpty(taskAllId)){
                cq.eq("taskAllId", taskAllId);
            }
            cq.add();
            list = this.tBHiddenDangerExamService.getListByCriteriaQuery(cq, false);
        }else {


            if (queryHandleStatus == null || queryHandleStatus.equals("")) {
                queryHandleStatus = "00";
            }
            cq = new CriteriaQuery(TBHiddenDangerExamEntity.class, dataGrid);


            //查询条件组装器
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBHiddenDangerExam, request.getParameterMap());
            TSUser sessionUser = ResourceUtil.getSessionUserName();
            boolean isAdmin = false;
            CriteriaQuery cqru = new CriteriaQuery(TSRoleUser.class);
            try{
                cqru.eq("TSUser.id",sessionUser.getId());
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


            try {
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
                //自定义追加查询条件
                cq.eq("examType", examType);
                if(!isAdmin){
                    cq.eq("createBy", sessionUser.getUserName());
                }
                if(StringUtil.isNotEmpty(taskAllId)){
                    cq.eq("taskAllId", taskAllId);
                }
                cq.addOrder("examDate", SortDirection.desc);
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
            cq.add();
            list = this.tBHiddenDangerExamService.getListByCriteriaQuery(cq, false);
        }
        List<TSUser> userList = systemService.getList(TSUser.class);
        Map<String, String> userMap = new HashMap<>();
        for (TSUser user : userList) {
            userMap.put(user.getId(), user.getRealName());
        }
        Map<String, String> userNameMap = new HashMap<>();
        for (TSUser user : userList) {
            userNameMap.put(user.getId(), user.getUserName());
        }
        for(TBHiddenDangerExamEntity t : list){


            if (StringUtils.isNotBlank(t.getFillCardManId())){
                String[] useids = t.getFillCardManId().split(",");
                StringBuffer userNames = new StringBuffer();
                for(String userId : useids){
                    if(StringUtils.isNotBlank(userNames.toString())) {
                        userNames.append(",");
                        if(userMap.get(userId)==null&& StringUtil.isNotEmpty(userId)){
                            userNames.append(userId);
                        }else {
                            userNames.append(userMap.get(userId)+"-"+userNameMap.get(userId));
                        }
                    }else{
                        if(userMap.get(userId)==null&& StringUtil.isNotEmpty(userId)){
                            userNames.append(userId);
                        }else{
                            userNames.append(userMap.get(userId)+"-"+userNameMap.get(userId));
                        }
                    }
                }


                t.setFillCardManNames(userNames.toString());
            }


//            String usenames="";
//
//            CriteriaQuery usercq = new CriteriaQuery(TSUser.class);
//            try{
//                usercq.in("id",useids);
//            }catch (Exception e) {
//                throw new BusinessException(e.getMessage());
//            }
//            usercq.add();
//            List<TSUser> userlist = this.tBHiddenDangerExamService.getListByCriteriaQuery(usercq, false);
//            if(userlist.size() > 0){
//                for(TSUser u : userlist){
//                    if(usenames == ""){
//                        usenames = usenames + u.getRealName();
//                    }else{
//                        usenames = usenames + "," + u.getRealName();
//                    }
//
//                }
//            }

            String itemUserId = t.getItemUserId();
            if(StringUtils.isNotBlank(itemUserId)){
                String[] userIdArray = itemUserId.split(",");
                StringBuffer itemUserNameSb = new StringBuffer();
                for(String userId : userIdArray){
                    if(StringUtils.isNotBlank(itemUserNameSb.toString())){
                        itemUserNameSb.append(",");
                    }
                    itemUserNameSb.append(userMap.get(userId));
                }
                t.setItemUserNameTemp(itemUserNameSb.toString());
            }



//            String dutyMan = t.getDutyMan();
//            StringBuffer sb = new StringBuffer();
//            if(StringUtils.isNotBlank(dutyMan)){
//                String ids [] = dutyMan.split(",");
//                for(String id : ids){
//                    if(StringUtils.isNotBlank(sb.toString())){
//                        sb.append(",");
//                    }
//                    sb.append(userMap.get(id));
//                }
//            }
            if(t.getReviewMan() == null){
                t.setReviewMan(new TSUser());
            }
            t.setDutyManTemp(t.getDutyMan());
            String dealType = t.getDealType();
            if("1".equals(dealType)){
                t.setDealTypeTemp("限期整改");
            }else if("2".equals(dealType)){
                t.setDealTypeTemp("现场处理");
            }

            String handlelStatus = t.getHandleEntity().getHandlelStatus();
            String protype =  DicUtil.getTypeNameByCode("handelStatus",handlelStatus);
            t.setHandleStatusTemp(protype);
            if (StringUtils.isNotBlank(t.getShift())) {
                t.setShiftTemp(DicUtil.getTypeNameByCode("workShift", t.getShift()));
            }
            if (t.getHandleEntity() != null && t.getHandleEntity().getReviewMan()!=null) {
                t.setReviewManTemp(userMap.get(t.getHandleEntity().getReviewMan()));
            }
            if (t.getHandleEntity() != null && t.getHandleEntity().getModifyMan() != null) {
                t.setModifyManTemp(userMap.get(t.getHandleEntity().getModifyMan()));
            }
            if (t.getHandleEntity() != null && t.getHandleEntity().getModifyDate() != null) {
                t.setModifyDateTemp(DateUtils.date2Str(t.getHandleEntity().getModifyDate(), DateUtils.date_sdf));
            }
            if (t.getHandleEntity() != null && t.getHandleEntity().getReviewDate() != null) {
                t.setReviewDateTemp(DateUtils.date2Str(t.getHandleEntity().getReviewDate(), DateUtils.date_sdf));
            }
            if (StringUtils.isNotBlank(t.getHiddenNature())){
                t.setHiddenNatureTemp(DicUtil.getTypeNameByCode("hiddenLevel", t.getHiddenNature()));
            }
            if (StringUtils.isNotBlank(t.getExamType())){
                t.setExamTypeTemp(DicUtil.getTypeNameByCode("examType", t.getExamType()));
            }
            if (t.getHandleEntity() != null) {
                t.setRollBackRemarkTemp(t.getHandleEntity().getRollBackRemark());
            }
            if (StringUtils.isNotBlank(t.getItemId())){
                t.setItemId(DicUtil.getTypeNameByCode("group", t.getItemId()));
            }
            if (StringUtils.isNotBlank(t.getRiskType())){
                t.setRiskTypeTemp(DicUtil.getTypeNameByCode("risk_type", t.getRiskType()));
            }
            if(StringUtil.isNotEmpty(t.getRiskId())){
                if (StringUtils.isNotBlank(t.getRiskId().getRiskDesc())){
                    t.setRiskDescTemp(t.getRiskId().getRiskDesc());
                }
            }
            if(StringUtil.isNotEmpty(t.getManageDutyManId())){
                t.setManageDutyManTemp(userMap.get(t.getManageDutyManId()));
            }
        }

        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);

        //Author: 张赛超
        if("sjjc".equals(examType)){
            templateExportParams.setSheetName("上级检查隐患列表");
        }else if("glgbxj".equals(examType)){
            templateExportParams.setSheetName("管理干部下井隐患列表");
        }else if("klddb".equals(examType)){
            templateExportParams.setSheetName("矿领导带班隐患列表");
        }else if("ajy".equals(examType)){
            templateExportParams.setSheetName("安监员安全检查隐患列表");
        }else if("zyks".equals(examType)){
            templateExportParams.setSheetName("专业科室日常检查隐患列表");
        }else if("zlbzh".equals(examType)){
            templateExportParams.setSheetName("质量标准化检查隐患列表");
        }else if("yh".equals(examType)){
            templateExportParams.setSheetName("隐患列表");
        }else{
            templateExportParams.setSheetName("矿井安全大检查隐患列表");
        }

        if("sjjc".equals(examType)){
            templateExportParams.setTemplateUrl("export/template/exportTemp_hiddenDangerExamSjjc.xls");
        }else if("kjaqdjc".equals(examType)){
            templateExportParams.setTemplateUrl("export/template/exportTemp_hiddenDangerExamKjandjc.xls");
        }else if("yh".equals(examType)) {
            templateExportParams.setTemplateUrl("export/template/exportTemp_hiddenDangerExamYH.xls");
        }else{
            templateExportParams.setTemplateUrl("export/template/exportTemp_hiddenDangerExam.xls");
        }
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String,List<TBHiddenDangerExamEntity>> map = new HashMap<String,List<TBHiddenDangerExamEntity>>();
        map.put("list", list);
        if("sjjc".equals(examType)){
            modelMap.put(NormalExcelConstants.FILE_NAME,"上级检查隐患列表");
        }else if("glgbxj".equals(examType)){
            modelMap.put(NormalExcelConstants.FILE_NAME,"管理干部下井隐患列表");
        }else if("klddb".equals(examType)){
            modelMap.put(NormalExcelConstants.FILE_NAME,"矿领导带班隐患列表");
        }else if("ajy".equals(examType)){
            modelMap.put(NormalExcelConstants.FILE_NAME,"安监员安全检查隐患列表");
        }else if("zyks".equals(examType)){
            modelMap.put(NormalExcelConstants.FILE_NAME,"专业科室日常检查隐患列表");
        }else if("zlbzh".equals(examType)){
            modelMap.put(NormalExcelConstants.FILE_NAME,"质量标准化检查隐患列表");
        }else if("yh".equals(examType)){
            modelMap.put(NormalExcelConstants.FILE_NAME,"隐患列表");
        }else{
            modelMap.put(NormalExcelConstants.FILE_NAME,"矿井安全大检查隐患列表");
        }
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }
/****************************************************导入*************************************************************/
    /**
     * 导入功能跳转
     *
     * @return
     */

    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name","tBHiddenDangerExamController");
        String examType = ResourceUtil.getParameter("examType");
        String taskAllId = ResourceUtil.getParameter("taskAllId");
        req.setAttribute("function_name", "importExcelT&taskAllId="+taskAllId+"&examType="+examType);
        return new ModelAndView("common/upload/pub_excel_upload");
    }
    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TBHiddenDangerExamEntity tbHiddenDangerExamEntity, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME,"隐患导入模板");
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(1);
        templateExportParams.setScanAllsheet(true);

        Map<String, Object> param =new HashMap<String, Object>();

        //检查类型
        String examType = request.getParameter("examType");
        if("yh".equals(examType)){
            templateExportParams.setTemplateUrl("export/template/importTemp_hiddenDangerExamYH.xls");
            exportXlsByTAllHaveDicList();

            Map<String, List<String>> dicListMap = exportXlsByTAllHaveDicList();
            List<TBHiddenDangerExamExportDicVO> dicVOList = new ArrayList<TBHiddenDangerExamExportDicVO>();

            //查询    检查人
            String inspectorSql = "select u.realname realName from t_s_base_user u join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id where u.delete_flag='0' and u.status in ('1','0','-1')";
            List<String> inspectorList = systemService.findListbySql(inspectorSql);


            //得到这几串数列的最长的一列，excel导出的行数即为最长一列的长度
            int[] listLength = {dicListMap.get("shiftList").size(), dicListMap.get("addressList").size(), dicListMap.get("dutyUnitList").size(), dicListMap.get("dutyManList").size(), dicListMap.get("hiddenDangerCategoryList").size(), dicListMap.get("hiddenDangerLevelList").size(), dicListMap.get("hiddenDangerTypeList").size(), dicListMap.get("processModeList").size(), dicListMap.get("reviewManList").size(),
                    inspectorList.size(),dicListMap.get("manageTypeList").size(),dicListMap.get("riskTypeList").size()};         /*后面这个查询的除了公有以外的私有的*/
            int maxLength = listLength[0];
            for (int i = 0; i < listLength.length; i++) {   //开始循环一维数组
                if (listLength[i] > maxLength) {  //循环判断数组元素
                    maxLength = listLength[i]; }  //赋值给num，然后再次循环
            }

            for (int j=0; j<maxLength; j++) {
                TBHiddenDangerExamExportDicVO vo = new TBHiddenDangerExamExportDicVO();
                if (j < dicListMap.get("shiftList").size()) {
                    vo.setShift(dicListMap.get("shiftList").get(j));
                }
                if (j < dicListMap.get("addressList").size()) {
                    vo.setAddress(dicListMap.get("addressList").get(j));
                }
                if (j < dicListMap.get("dutyUnitList").size()) {
                    vo.setDutyUnit(dicListMap.get("dutyUnitList").get(j));
                }
                if (j < dicListMap.get("dutyManList").size()) {
                    vo.setDutyMan(dicListMap.get("dutyManList").get(j));
                }
                if (j < dicListMap.get("hiddenDangerCategoryList").size()) {
                    vo.setHiddenDangerCategory(dicListMap.get("hiddenDangerCategoryList").get(j));
                }
                if (j < dicListMap.get("hiddenDangerLevelList").size()) {
                    vo.setHiddenDangerLevel(dicListMap.get("hiddenDangerLevelList").get(j));
                }
                if (j < dicListMap.get("hiddenDangerTypeList").size()) {
                    vo.setHiddenDangerType(dicListMap.get("hiddenDangerTypeList").get(j));
                }
                if (j < dicListMap.get("processModeList").size()) {
                    vo.setProcessMode(dicListMap.get("processModeList").get(j));
                }
                if (j < dicListMap.get("reviewManList").size()) {
                    vo.setReviewMan(dicListMap.get("reviewManList").get(j));
                }
                if (j < inspectorList.size()) {
                    vo.setInspector(inspectorList.get(j));
                }
                if (j < dicListMap.get("manageTypeList").size()) {
                    vo.setManageType(dicListMap.get("manageTypeList").get(j));
                }
                if (j < dicListMap.get("riskTypeList").size()) {
                    vo.setRiskType(dicListMap.get("riskTypeList").get(j));
                }

                dicVOList.add(vo);
            }

            //将字典赋值到param中，写到sheet2中
            param.put("dicVoList", dicVOList);

        } else if("sjjc".equals(examType)){
            templateExportParams.setTemplateUrl("export/template/importTemp_hiddenDangerExamSjjc.xls");
            exportXlsByTAllHaveDicList();

            Map<String, List<String>> dicListMap = exportXlsByTAllHaveDicList();
            List<TBHiddenDangerExamExportDicVO> dicVOList = new ArrayList<TBHiddenDangerExamExportDicVO>();

            //得到这几串数列的最长的一列，excel导出的行数即为最长一列的长度
            int[] listLength = {dicListMap.get("shiftList").size(), dicListMap.get("addressList").size(), dicListMap.get("dutyUnitList").size(), dicListMap.get("dutyManList").size(), dicListMap.get("hiddenDangerCategoryList").size(), dicListMap.get("hiddenDangerLevelList").size(), dicListMap.get("hiddenDangerTypeList").size(), dicListMap.get("processModeList").size(), dicListMap.get("reviewManList").size(),
            };         /*后面这个查询的除了公有以外的私有的*/
            int maxLength = listLength[0];
            for (int i = 0; i < listLength.length; i++) {   //开始循环一维数组
                if (listLength[i] > maxLength) {  //循环判断数组元素
                    maxLength = listLength[i]; }  //赋值给num，然后再次循环
            }

            for (int j=0; j<maxLength; j++) {
                TBHiddenDangerExamExportDicVO vo = new TBHiddenDangerExamExportDicVO();
                if (j < dicListMap.get("shiftList").size()) {
                    vo.setShift(dicListMap.get("shiftList").get(j));
                }
                if (j < dicListMap.get("addressList").size()) {
                    vo.setAddress(dicListMap.get("addressList").get(j));
                }
                if (j < dicListMap.get("dutyUnitList").size()) {
                    vo.setDutyUnit(dicListMap.get("dutyUnitList").get(j));
                }
                if (j < dicListMap.get("dutyManList").size()) {
                    vo.setDutyMan(dicListMap.get("dutyManList").get(j));
                }
                if (j < dicListMap.get("hiddenDangerCategoryList").size()) {
                    vo.setHiddenDangerCategory(dicListMap.get("hiddenDangerCategoryList").get(j));
                }
                if (j < dicListMap.get("hiddenDangerLevelList").size()) {
                    vo.setHiddenDangerLevel(dicListMap.get("hiddenDangerLevelList").get(j));
                }
                if (j < dicListMap.get("hiddenDangerTypeList").size()) {
                    vo.setHiddenDangerType(dicListMap.get("hiddenDangerTypeList").get(j));
                }
                if (j < dicListMap.get("processModeList").size()) {
                    vo.setProcessMode(dicListMap.get("processModeList").get(j));
                }
                if (j < dicListMap.get("reviewManList").size()) {
                    vo.setReviewMan(dicListMap.get("reviewManList").get(j));
                }

                dicVOList.add(vo);
            }

            //将字典赋值到param中，写到sheet2中
            param.put("dicVoList", dicVOList);
        }else if("kjaqdjc".equals(examType)){
            templateExportParams.setTemplateUrl("export/template/importTemp_hiddenDangerExamKjandjc.xls");
            exportXlsByTAllHaveDicList();

            Map<String, List<String>> dicListMap = exportXlsByTAllHaveDicList();
            List<TBHiddenDangerExamExportDicVO> dicVOList = new ArrayList<TBHiddenDangerExamExportDicVO>();

            //查询    组别
            String groupSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='group')";
            List<String> groupList = systemService.findListbySql(groupSql);

            //查询    组员
            String memberSql = "select u.realname realName from t_s_base_user u join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id where u.delete_flag='0' and u.status in ('1','0','-1')";
            List<String> memberList = systemService.findListbySql(memberSql);

            //得到这几串数列的最长的一列，excel导出的行数即为最长一列的长度
            int[] listLength = {dicListMap.get("shiftList").size(), dicListMap.get("addressList").size(), dicListMap.get("dutyUnitList").size(), dicListMap.get("dutyManList").size(), dicListMap.get("hiddenDangerCategoryList").size(), dicListMap.get("hiddenDangerLevelList").size(), dicListMap.get("hiddenDangerTypeList").size(), dicListMap.get("processModeList").size(), dicListMap.get("reviewManList").size(),
                    groupList.size(), memberList.size()};         /*后面这个查询的除了公有以外的私有的*/
            int maxLength = listLength[0];
            for (int i = 0; i < listLength.length; i++) {   //开始循环一维数组
                if (listLength[i] > maxLength) {  //循环判断数组元素
                    maxLength = listLength[i]; }  //赋值给num，然后再次循环
            }

            for (int j=0; j<maxLength; j++) {
                TBHiddenDangerExamExportDicVO vo = new TBHiddenDangerExamExportDicVO();
                if (j < dicListMap.get("shiftList").size()) {
                    vo.setShift(dicListMap.get("shiftList").get(j));
                }
                if (j < dicListMap.get("addressList").size()) {
                    vo.setAddress(dicListMap.get("addressList").get(j));
                }
                if (j < dicListMap.get("dutyUnitList").size()) {
                    vo.setDutyUnit(dicListMap.get("dutyUnitList").get(j));
                }
                if (j < dicListMap.get("dutyManList").size()) {
                    vo.setDutyMan(dicListMap.get("dutyManList").get(j));
                }
                if (j < dicListMap.get("hiddenDangerCategoryList").size()) {
                    vo.setHiddenDangerCategory(dicListMap.get("hiddenDangerCategoryList").get(j));
                }
                if (j < dicListMap.get("hiddenDangerLevelList").size()) {
                    vo.setHiddenDangerLevel(dicListMap.get("hiddenDangerLevelList").get(j));
                }
                if (j < dicListMap.get("hiddenDangerTypeList").size()) {
                    vo.setHiddenDangerType(dicListMap.get("hiddenDangerTypeList").get(j));
                }
                if (j < dicListMap.get("processModeList").size()) {
                    vo.setProcessMode(dicListMap.get("processModeList").get(j));
                }
                if (j < dicListMap.get("reviewManList").size()) {
                    vo.setReviewMan(dicListMap.get("reviewManList").get(j));
                }
                if (j < groupList.size()){
                    vo.setGroup(groupList.get(j));
                }
                if (j < memberList.size()){
                    vo.setMember(memberList.get(j));
                }

                dicVOList.add(vo);
            }

            //将字典赋值到param中，写到sheet2中
            param.put("dicVoList", dicVOList);

        }else{
            templateExportParams.setTemplateUrl("export/template/importTemp_hiddenDangerExam.xls");
            exportXlsByTAllHaveDicList();

            Map<String, List<String>> dicListMap = exportXlsByTAllHaveDicList();
            List<TBHiddenDangerExamExportDicVO> dicVOList = new ArrayList<TBHiddenDangerExamExportDicVO>();

            //查询    检查人
            String inspectorSql = "select u.realname realName from t_s_base_user u join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id where u.delete_flag='0' and u.status in ('1','0','-1')";
            List<String> inspectorList = systemService.findListbySql(inspectorSql);

            //得到这几串数列的最长的一列，excel导出的行数即为最长一列的长度
            int[] listLength = {dicListMap.get("shiftList").size(), dicListMap.get("addressList").size(), dicListMap.get("dutyUnitList").size(), dicListMap.get("dutyManList").size(), dicListMap.get("hiddenDangerCategoryList").size(), dicListMap.get("hiddenDangerLevelList").size(), dicListMap.get("hiddenDangerTypeList").size(), dicListMap.get("processModeList").size(), dicListMap.get("reviewManList").size(),
                    inspectorList.size()};         /*后面这个查询的除了公有以外的私有的*/
            int maxLength = listLength[0];
            for (int i = 0; i < listLength.length; i++) {   //开始循环一维数组
                if (listLength[i] > maxLength) {  //循环判断数组元素
                    maxLength = listLength[i]; }  //赋值给num，然后再次循环
            }

            for (int j=0; j<maxLength; j++) {
                TBHiddenDangerExamExportDicVO vo = new TBHiddenDangerExamExportDicVO();
                if (j < dicListMap.get("shiftList").size()) {
                    vo.setShift(dicListMap.get("shiftList").get(j));
                }
                if (j < dicListMap.get("addressList").size()) {
                    vo.setAddress(dicListMap.get("addressList").get(j));
                }
                if (j < dicListMap.get("dutyUnitList").size()) {
                    vo.setDutyUnit(dicListMap.get("dutyUnitList").get(j));
                }
                if (j < dicListMap.get("dutyManList").size()) {
                    vo.setDutyMan(dicListMap.get("dutyManList").get(j));
                }
                if (j < dicListMap.get("hiddenDangerCategoryList").size()) {
                    vo.setHiddenDangerCategory(dicListMap.get("hiddenDangerCategoryList").get(j));
                }
                if (j < dicListMap.get("hiddenDangerLevelList").size()) {
                    vo.setHiddenDangerLevel(dicListMap.get("hiddenDangerLevelList").get(j));
                }
                if (j < dicListMap.get("hiddenDangerTypeList").size()) {
                    vo.setHiddenDangerType(dicListMap.get("hiddenDangerTypeList").get(j));
                }
                if (j < dicListMap.get("processModeList").size()) {
                    vo.setProcessMode(dicListMap.get("processModeList").get(j));
                }
                if (j < dicListMap.get("reviewManList").size()) {
                    vo.setReviewMan(dicListMap.get("reviewManList").get(j));
                }
                if (j < inspectorList.size()) {
                    vo.setInspector(inspectorList.get(j));
                }

                dicVOList.add(vo);
            }

            //将字典赋值到param中，写到sheet2中
            param.put("dicVoList", dicVOList);
        }

        modelMap.put(TemplateExcelConstants.PARAMS, templateExportParams);
        modelMap.put(TemplateExcelConstants.MAP_DATA, param);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }
    /**
     *  导出的Excel模板里面都有的公共数据字典
     */
    private Map<String, List<String>> exportXlsByTAllHaveDicList(){
        Map dicListMap = new HashMap();

        //查询    班次          0
        String shiftSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='workShift')";
        List<String> shiftList = systemService.findListbySql(shiftSql);

        //查询    地点          1
        String addressListSql = "select address from t_b_address_info where is_delete='0'";
        List<String> addressList = systemService.findListbySql(addressListSql);

        //查询    责任单位    2
        String dutyUnitSql = "select departName from t_s_depart where delete_flag='0'";
        List<String> dutyUnitList = systemService.findListbySql(dutyUnitSql);

        //查询    责任人      3
        String dutyManSql = "select u.realname realName from t_s_base_user u join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id where u.delete_flag='0' and u.status in ('1','0','-1')";
        List<String> dutyManList = systemService.findListbySql(dutyManSql);

        //查询    隐患类别    4
        String hiddenDangerCategorySql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='hiddenCate')";
        List<String> hiddenDangerCategoryList = systemService.findListbySql(hiddenDangerCategorySql);

        //查询    隐患等级    5
        String hiddenDangerLevelSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='hiddenLevel')";
        List<String> hiddenDangerLevelList = systemService.findListbySql(hiddenDangerLevelSql);

        //查询    隐患类型    6
        String hiddenDangerTypeSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='hiddenType')";
        List<String> hiddenDangerTypeList = systemService.findListbySql(hiddenDangerTypeSql);

        //查询    处理方式    7
        List<String> processModeList = new ArrayList<String>();
        processModeList.add("限期整改");
        processModeList.add("现场处理");

        //查询    复查人     8
        String reviewManSql = "select u.realname realName from t_s_base_user u join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id where u.delete_flag='0' and u.status in ('1','0','-1')";
        List<String> reviewManList = systemService.findListbySql(reviewManSql);

        //查询    信息来源    6
        String manageTypeSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='manageType')";
        List<String> manageTypeList = systemService.findListbySql(manageTypeSql);
        //风险类型改为隐患类型
        //查询    隐患类型    6
        String riskTypeSql = "select typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='risk_type')";
        List<String> riskTypeList = systemService.findListbySql(riskTypeSql);

        dicListMap.put("shiftList", shiftList);
        dicListMap.put("addressList", addressList);
        dicListMap.put("dutyUnitList", dutyUnitList);
        dicListMap.put("dutyManList", dutyManList);
        dicListMap.put("hiddenDangerCategoryList", hiddenDangerCategoryList);
        dicListMap.put("hiddenDangerLevelList", hiddenDangerLevelList);
        dicListMap.put("hiddenDangerTypeList", hiddenDangerTypeList);
        dicListMap.put("processModeList", processModeList);
        dicListMap.put("reviewManList", reviewManList);
        dicListMap.put("manageTypeList", manageTypeList);
        dicListMap.put("riskTypeList", riskTypeList);
        return dicListMap;
    }

    //导入隐患
    @SuppressWarnings("unchecked")
    @RequestMapping(params = "importExcelT", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcelT(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();

        String examType = ResourceUtil.getParameter("examType");
        String taskAllId = ResourceUtil.getParameter("taskAllId");
        if (StringUtils.isBlank(examType)) {
            j.setSuccess(false);
            j.setMsg("导入数据校验失败, 请求参数不正确");
            return j;
        }
        List<TBAddressInfoEntity> addressList = systemService.findByProperty(TBAddressInfoEntity.class,"isDelete","0");
        Map<String, TBAddressInfoEntity> addressMap = new HashedMap();
        if (addressList != null && addressList.size()>0) {
            for (TBAddressInfoEntity entity : addressList) {
                addressMap.put(entity.getAddress(), entity);
            }
        }
        List<TSDepart> departList = systemService.findByProperty(TSDepart.class,"deleteFlag",new Short("0"));
        Map<String, TSDepart> departMap = new HashedMap();
        if (departList != null && departList.size()>0) {
            for (TSDepart entity : departList) {
                departMap.put(entity.getDepartname(), entity);
            }
        }
        List<TSUser> userList = systemService.findByProperty(TSUser.class,"deleteFlag",new Short("0"));
        Map<String, TSUser> userMap = new HashedMap();
        if (userList != null && userList.size()>0) {
            for (TSUser entity : userList) {
                userMap.put(entity.getRealName().replaceAll(" ",""), entity);
            }
        }


        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            params.setNeedSave(false);
            params.setNeedVerfiy(true);
            params.setVerifyHanlder(new HiddenDangerExamExcelVerifyHandler(examType,addressMap,departMap,userMap));
            try {
                ExcelImportResult<TBHiddenDangerExamEntity> result  = ExcelImportUtil.importExcelVerify(file.getInputStream(),TBHiddenDangerExamEntity.class,params);
                if(result.isVerfiyFail()){
                    String uploadpathtemp = ResourceUtil.getConfigByName("uploadpathtemp");
                    String realPath = multipartRequest.getSession().getServletContext().getRealPath("/") + "/" + uploadpathtemp+"/";// 文件的硬盘真实路径
                    File fileTemp = new File(realPath);
                    if (!fileTemp.exists()) {
                        fileTemp.mkdirs();// 创建根目录
                    }
                    String name = DateUtils.getDataString(DateUtils.yyyymmddhhmmss)+".xls";
                    realPath+=name;
                    FileOutputStream fos = new FileOutputStream(realPath);
                    result.getWorkbook().write(fos);
                    fos.close();
                    Map<String, Object> attributes = new HashMap<String, Object>();
                    attributes.put("path", uploadpathtemp+"/"+name);
                    j.setAttributes(attributes);
                    j.setSuccess(false);
                    j.setMsg("导入数据校验失败");
                }else{
                    List<TBHiddenDangerExamEntity> examEntityList = new ArrayList<>();
                    for (int i = 0; i < result.getList().size(); i++) {
                        TBHiddenDangerExamEntity tBHiddenDangerExam = result.getList().get(i);



                        //挂牌督办默认设置为未挂牌
                        tBHiddenDangerExam.setIsLsProv(Constants.HDBIISLS_STATE_UNDO);
                        tBHiddenDangerExam.setIsLsSub(Constants.HDBIISLS_STATE_UNDO);
                        tBHiddenDangerExam.setReportStatus(Constants.REPORT_STATUS_N);
                        tBHiddenDangerExam.setExamType(examType);
                        tBHiddenDangerExam.setTaskAllId(taskAllId);
                        message = "隐患检查添加成功";
                        //处理表添加一条数据
                        TBHiddenDangerHandleEntity handleEntity = new TBHiddenDangerHandleEntity();
                        handleEntity.setHiddenDanger(tBHiddenDangerExam);
                        String dealType = tBHiddenDangerExam.getDealType();
                        if (Constants.DEALTYPE_XIANCAHNG.equals(dealType)) {//现场处理
                            tBHiddenDangerExam.setLimitDate(null);
                            tBHiddenDangerExam.setLimitShift(null);
                            handleEntity.setReviewMan(tBHiddenDangerExam.getReviewMan() != null ? tBHiddenDangerExam.getReviewMan().getId() : null);
                            handleEntity.setModifyDate(tBHiddenDangerExam.getExamDate());
                            handleEntity.setModifyShift(tBHiddenDangerExam.getShift());
                            handleEntity.setModifyMan(tBHiddenDangerExam.getDutyMan());
                            handleEntity.setReviewDate(tBHiddenDangerExam.getExamDate());
                            handleEntity.setReviewShift(tBHiddenDangerExam.getShift());
                            handleEntity.setHandlelStatus(Constants.HANDELSTATUS_DRAFT);
                        } else {//限期整改,改为草稿状态
                            tBHiddenDangerExam.setReviewMan(null);
                            handleEntity.setHandlelStatus(Constants.HANDELSTATUS_DRAFT);
                        }

                        tBHiddenDangerExam.setHandleEntity(handleEntity);

                        if (tBHiddenDangerExam.getProblemDesc() != null && tBHiddenDangerExam.getProblemDesc() != "") {
                            tBHiddenDangerExam.setProblemDesc(tBHiddenDangerExam.getProblemDesc().trim());
                        }
                        if (tBHiddenDangerExam.getRemark() != null && tBHiddenDangerExam.getRemark() != "") {
                            tBHiddenDangerExam.setRemark(tBHiddenDangerExam.getRemark().trim());
                        }
                        tBHiddenDangerExam.setHiddenNatureOriginal(tBHiddenDangerExam.getHiddenNature());
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
                            tBHiddenDangerExam.setHiddenNumber(hiddenNumber);
                        }
                        examEntityList.add(tBHiddenDangerExam);
                    }
                    tBHiddenDangerExamService.batchSave(examEntityList);

                    List<String> idList = new ArrayList<>();
                    if(!examEntityList.isEmpty() && examEntityList.size()>0){
                        for(TBHiddenDangerExamEntity bean:examEntityList){
                            idList.add(bean.getId());
                        }
                        syncToCloudService.hiddenDangerBatchReport(StringUtils.join(idList, ","));
                    }

                    j.setMsg("文件导入成功！");
                    systemService.addLog(j.getMsg(), Globals.Log_Leavel_INFO, Globals.Log_Type_UPLOAD);
                    for (TBHiddenDangerExamEntity tBHiddenDangerExam : examEntityList){
                        systemService.addLog("隐患检查\"" + tBHiddenDangerExam.getId() + "\"导入成功", Globals.Log_Leavel_INFO, Globals.Log_Type_INSERT);
                    }
                }
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                systemService.addLog(j.getMsg()+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_UPLOAD);
                logger.error(ExceptionUtil.getExceptionMessage(e));
            }finally{
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    LogUtil.error("日常隐患导入失败",e);
                }
            }
        }
        return j;
    }


    /**
     * 隐患自动关联风险
     * @param request
     * @return
     */
    @RequestMapping(params = "attachRisk")
    @ResponseBody
    public AjaxJson attachRisk(String addressId, String problemDesc, HttpServletRequest request) {
        String message = null;
        AjaxJson retJson = new AjaxJson();
        message = "关联成功";
        try {
            if(StringUtil.isNotEmpty(addressId) && StringUtil.isNotEmpty(problemDesc)){
                if (false == SemanticSimilarityUtil.getDSDartDone()){
                    retJson.setSuccess(false);
                    message = "风险描述分词尚未完成，暂无法进行关联";
                }else {
                    String sql = "select ds.id from t_b_danger_source ds, t_b_danger_address_rel rel where ds.id=rel.danger_id\n" +
                            " and ds.is_delete!='1' and ds.audit_status='4' and rel.address_id='" + addressId + "'";
                    List<String> dsList = systemService.findListbySql(sql);
                    if(null!=dsList && dsList.size()>0){
                        Map<String, Vector<String>> dsPartMap = new HashMap<>();
                        for(int j=0;j<dsList.size();j++){
                            String dsId = dsList.get(j);
                            dsPartMap.put(dsId, SemanticSimilarityUtil.dsPartMap.get(dsId));
                        }
                        List<Map<String,Object>> dsIds = SemanticSimilarityUtil.getMatchedDangerSourceIds(problemDesc, dsPartMap, 1);
                        if(null!=dsIds && dsIds.size()>0){
                            String dsId = (String)dsIds.get(0).get("id");
                            TBDangerSourceEntity dse = this.systemService.get(TBDangerSourceEntity.class,dsId);
                            String yeRiskGradeTemp = DicUtil.getTypeNameByCode("riskLevel", dse.getYeRiskGrade());
                            if("重大风险".equals(yeRiskGradeTemp)){
                                dse.setAlertColor(Constants.ALERT_COLOR_ZDFX);
                            }else if("较大风险".equals(yeRiskGradeTemp)){
                                dse.setAlertColor(Constants.ALERT_COLOR_JDFX);
                            }else if("一般风险".equals(yeRiskGradeTemp)){
                                dse.setAlertColor(Constants.ALERT_COLOR_YBFX);
                            }else{
                                dse.setAlertColor(Constants.ALERT_COLOR_DFX);
                            }
                            dse.setYeRiskGradeTemp(yeRiskGradeTemp);

                            Map<String,String> retMap = new HashMap<>();
                            retMap.put("dangerName",dse.getHazard()!=null?dse.getHazard().getHazardName():"");
                            retMap.put("problemDesc",dse.getYeMhazardDesc());
                            retMap.put("hiddenLevel",dse.getHiddenLevel());
                            retMap.put("yePossiblyHazard",dse.getYePossiblyHazard());
                            retMap.put("manageMeasure",dse.getManageMeasure());
                            retMap.put("id",dse.getId());
                            retMap.put("yeRiskGradeTemp",dse.getYeRiskGradeTemp());
                            retMap.put("alertColor",dse.getAlertColor());
                            retJson.setObj(retMap);
                        } else {
                            retJson.setSuccess(false);
                            message = "未匹配到风险";
                        }
                    }else {
                        retJson.setSuccess(false);
                        message = "该地点未关联风险";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "关联出现异常";
            throw new BusinessException(e.getMessage());
        }
        retJson.setMsg(message);
        return retJson;
    }

    /**
     * 动态获取风险的相似隐患描述
     * @param request
     * @return
     */
    @RequestMapping(params = "getSimilarRisk")
    @ResponseBody
    public AjaxJson getSimilarRisk(String addressId, String problemDesc, HttpServletRequest request) {
        String message = null;
        AjaxJson retJson = new AjaxJson();
        message = "获取风险的相似隐患描述成功";
        try {
            if(StringUtil.isNotEmpty(addressId) && StringUtil.isNotEmpty(problemDesc)){
                if (false == SemanticSimilarityUtil.getDSDartDone()){
                    retJson.setSuccess(false);
                    message = "风险描述分词尚未完成，暂无法获取";
                }else {
                    String sql = "select ds.id from t_b_danger_source ds, t_b_danger_address_rel rel where ds.id=rel.danger_id\n" +
                            " and ds.is_delete!='1' and ds.audit_status='4' and rel.address_id='" + addressId + "'";
                    List<String> dsList = systemService.findListbySql(sql);
                    if(null!=dsList && dsList.size()>0){
                        Map<String, Vector<String>> dsPartMap = new HashMap<>();
                        for(int j=0;j<dsList.size();j++){
                            String dsId = dsList.get(j);
                            dsPartMap.put(dsId, SemanticSimilarityUtil.dsPartMap.get(dsId));
                        }
                        List<Map<String,Object>> dsIds = SemanticSimilarityUtil.getMatchedDangerSourceIds(problemDesc, dsPartMap, 10);
                        if(null!=dsIds && dsIds.size()>0){
                            JSONArray ja = new JSONArray();
                            for(int k=0;k<dsIds.size();k++) {
                                Map<String,Object> dsMap = dsIds.get(k);
                                TBDangerSourceEntity dse = this.systemService.get(TBDangerSourceEntity.class, (String)dsMap.get("id"));
                                if(null!=dse){
                                    Map<String, Object> retMap = new HashMap<>();
                                    retMap.put("id", dsMap.get("id"));
                                    retMap.put("similar", dsMap.get("similarity"));
                                    retMap.put("desc", dse.getYeMhazardDesc());
                                    ja.add(retMap);
                                }
                            }
                            retJson.setObj(ja);
                        } else {
                            retJson.setSuccess(false);
                            message = "获取风险的相似隐患描述失败";
                        }
                    }else {
                        retJson.setSuccess(false);
                        message = "该地点未关联风险";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "获取风险的相似隐患描述异常";
            throw new BusinessException(e.getMessage());
        }
        retJson.setMsg(message);
        return retJson;
    }

    public JSONObject postAddTemplate(String openid,String createName,String createTime,String addressName,String problemDesc,String limitDate) throws IOException {
        JSONObject jo = new JSONObject();
        String access_token = "";
        String sql = "select accessToken from t_b_token";
        List<String> tmpList = systemService.findListbySql(sql);
        if (!tmpList.isEmpty() && tmpList.size() > 0) {
            access_token = tmpList.get(0);
        } else {
            String appid = ResourceUtil.getConfigByName("appID");
            String appsecret = ResourceUtil.getConfigByName("appsecret");
            access_token = weChartGetToken.getAccessToken(appid, appsecret);

        }
        String templateAdd_id= ResourceUtil.getConfigByName("templateAdd_id");
        JSONObject postJO = new JSONObject();
        JSONObject text=new JSONObject();
        JSONObject first=new JSONObject();
        JSONObject keyword1=new JSONObject();
        JSONObject keyword2=new JSONObject();
        JSONObject keyword3=new JSONObject();
        JSONObject remark=new JSONObject();

//        openid="oHNqj1HtUlGBotWPQdFMqV9JdDSk";
//        createName="习近平";
//        createTime="2018年3月32日";
//        addressName="A515";
//        problemDesc="殴打小朋友";
//        limitDate="1995年";

        postJO.put("touser",openid);
        postJO.put("template_id",templateAdd_id);
        first.put("value",createName);
        first.put("color","#007f80");
        keyword1.put("value",createTime);
        keyword1.put("color","#007f80");
        keyword2.put("value",addressName);
        keyword2.put("color","#007f80");
        keyword3.put("value",problemDesc);
        keyword3.put("color","#007f80");
        remark.put("value",limitDate);
        remark.put("color","#007f80");

        text.put("first",first);
        text.put("keyword1",keyword1);
        text.put("keyword2",keyword2);
        text.put("keyword3",keyword3);
        text.put("remark",remark);
        postJO.put("data",text);

        String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;

        try {
            String returnData = HttpClientUtils.post(url, postJO.toString());
            jo = JSONObject.fromObject(returnData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jo;
    }

    /*********************************************************风险管控状态清单 start***************************************************/

    /**
     * 隐患列表
     * @param request
     * @return
     */
    @RequestMapping(params = "hiddenDangerList")
    public ModelAndView hiddenDangerList(HttpServletRequest request) {
        request.setAttribute("replaceId", ResourceUtil.getParameter("dangerId"));
        return new ModelAndView("com/sdzk/buss/web/hiddendanger/hiddenDangerList");
    }


    @RequestMapping(params = "hiddenDangerDatagrid")
    public void hiddenDangerDatagrid(TBHiddenDangerExamEntity tBHiddenDangerExam, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerExamEntity.class, dataGrid);
        String dangerId = ResourceUtil.getParameter("replaceId");
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBHiddenDangerExam, request.getParameterMap());
        try{
            //自定义追加查询条件
            if (StringUtil.isNotEmpty(dangerId)) {
                //获取已关联的风险点
                cq.add(Restrictions.sqlRestriction(" this_.id in (select t.id from t_b_hidden_danger_exam t where t.danger_id='"+dangerId+"')"));
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tBHiddenDangerExamService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 风险管控状态清单详情跳转
     *
     * @return
     */
    @RequestMapping(params = "goRiskDetail")
    public ModelAndView goRiskDetail(TBHiddenDangerExamEntity tBHiddenDangerExam, HttpServletRequest req) {
        String flag = req.getParameter("flag");
        req.setAttribute("flag",flag);
        String load = req.getParameter("load");
        req.setAttribute("load",load);
        String examType = req.getParameter("examType");
        req.setAttribute("examType",examType);//检查类型
        String bRequiredDangerSouce = ResourceUtil.getConfigByName("bRequiredDangerSouce");
        req.setAttribute("bRequiredDangerSouce",bRequiredDangerSouce);
        String lilou = ResourceUtil.getConfigByName("ewmgn");
        String hid = req.getParameter("hid");
        tBHiddenDangerExam.setId(hid);
        String replaceId = req.getParameter("replaceId");
        TBDangerSourceEntity en = new TBDangerSourceEntity();
        en.setId(replaceId);
        tBHiddenDangerExam.setDangerId(en);

        if (StringUtil.isNotEmpty(tBHiddenDangerExam.getId())) {
            tBHiddenDangerExam = tBHiddenDangerExamService.getEntity(TBHiddenDangerExamEntity.class, tBHiddenDangerExam.getId());
            if(tBHiddenDangerExam != null){
                String names = "";
                String fillcardmanids="";

                String querySql = "select fill_card_manids man from t_b_hidden_danger_exam where id = '" + String.valueOf(tBHiddenDangerExam.getId())+"'";
                List<Map<String, Object>> maplist = systemService.findForJdbc(querySql, null);
                for (Map map : maplist) {
                    String mans = String.valueOf(map.get("man"));
                    if(StringUtils.isNotBlank(mans)){
                        String[] userIdArray = mans.split(",");



                        for(String userid : userIdArray){
                            if(fillcardmanids == ""){
                                fillcardmanids = fillcardmanids + '"' + userid + '"';
                            }else{
                                fillcardmanids = fillcardmanids + ',' + '"'+ userid + '"';
                            }
                            TSUser user = systemService.getEntity(TSUser.class,userid);
                            if(user!=null){
                                if(StringUtils.isNotBlank(lilou) && "true".equals(lilou)){
                                    if(names == ""){
                                        names = names + user.getRealName();
                                    }else{
                                        names = names + "," + user.getRealName();
                                    }
                                }else {
                                    if (names == "") {
                                        names = names + user.getRealName() + "-" + user.getUserName();
                                    } else {
                                        names = names + "," + user.getRealName() + "-" + user.getUserName();
                                    }
                                }
                            }

                        }
                        req.setAttribute("fillcardmanids",fillcardmanids);
                    }
                }
                tBHiddenDangerExam.setFillCardManNames(names);
                if(StringUtils.isNotBlank(tBHiddenDangerExam.getHiddenType())){
                    tBHiddenDangerExam.setHiddenTypeTemp(DicUtil.getTypeNameByCode("hiddenType", tBHiddenDangerExam.getHiddenType()));
                }

                String hiddenCategory = tBHiddenDangerExam.getHiddenCategory();
                if(StringUtils.isNotBlank(hiddenCategory)){
                    String hiddenCategoryTemp = DicUtil.getTypeNameByCode("hiddenCate",hiddenCategory);
                    tBHiddenDangerExam.setHiddenCategoryTemp(hiddenCategoryTemp);
                }
                String hiddenNature = tBHiddenDangerExam.getHiddenNature();
                if(StringUtils.isNotBlank(hiddenNature)){
                    String hiddenNatureTemp = DicUtil.getTypeNameByCode("hiddenLevel",hiddenNature);
                    tBHiddenDangerExam.setHiddenNatureTemp(hiddenNatureTemp);
                }
                String hiddenLevel = tBHiddenDangerExam.getHiddenLevel();
                if(StringUtils.isNotBlank(hiddenLevel)){
                    String hiddenLevelTemp = DicUtil.getTypeCodeByName("hiddenLevel",hiddenLevel);
                    tBHiddenDangerExam.setHiddenLevelTemp(hiddenLevelTemp);
                }
                String itemId = tBHiddenDangerExam.getItemId();
                if (StringUtils.isNotBlank(itemId)) {
                    req.setAttribute("itemDesc", DicUtil.getTypeNameByCode("group",itemId));
                }
            }


            if(tBHiddenDangerExam.getDangerId() != null && StringUtils.isNotBlank(tBHiddenDangerExam.getDangerId().getYeRiskGrade())){

                //风险等级
                String dangerSourceRiskValue;
                dangerSourceRiskValue = DicUtil.getTypeNameByCode("riskLevel",tBHiddenDangerExam.getDangerId().getYeRiskGrade());
                req.setAttribute("dangerSourceRiskValueTemp",dangerSourceRiskValue);
                //风险颜色
                String dangerSourceAlertColor;
                if("重大风险".equals(dangerSourceRiskValue)){
                    dangerSourceAlertColor= Constants.ALERT_COLOR_ZDFX;
                }else if("较大风险".equals(dangerSourceRiskValue)){
                    dangerSourceAlertColor= Constants.ALERT_COLOR_JDFX;
                }else if("一般风险".equals(dangerSourceRiskValue)){
                    dangerSourceAlertColor= Constants.ALERT_COLOR_YBFX;
                }else{
                    dangerSourceAlertColor= Constants.ALERT_COLOR_DFX;
                }
                req.setAttribute("dangerSourceAlertColor",dangerSourceAlertColor);
            }


            tBHiddenDangerExam.setShiftTemp(DicUtil.getTypeNameByCode("workShift", tBHiddenDangerExam.getShift()));
            tBHiddenDangerExam.setOriginTemp(DicUtil.getTypeNameByCode("hiddenDangerOrigin", tBHiddenDangerExam.getOrigin()));

            req.setAttribute("tBHiddenDangerExamPage", tBHiddenDangerExam);

            CriteriaQuery cq = new CriteriaQuery(TBHiddenDangerHandleEntity.class);
            cq.eq("hiddenDanger.id",tBHiddenDangerExam.getId());
            cq.add();
            List<TBHiddenDangerHandleEntity> handleEntityList = systemService.getListByCriteriaQuery(cq,false);
            if(!handleEntityList.isEmpty() && handleEntityList.size() > 0 ){
                TBHiddenDangerHandleEntity handleEntity = handleEntityList.get(0);
                String handleStatus = handleEntity.getHandlelStatus();
                tBHiddenDangerExam.setHandleStatusTemp(handleStatus);
                if(handleStatus.equals(Constants.HANDELSTATUS_ROLLBACK_REPORT)) {
                    //退回上报
                    tBHiddenDangerExam.setRollBackRemarkTemp(handleEntity.getRollBackRemark());
                }

                /***** 整改和复查信息 begin *****/
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(handleStatus.equals(Constants.HANDELSTATUS_REVIEW)){
                    // 已整改待复查
                    if(StringUtils.isNotBlank(handleEntity.getModifyMan())){
                        tBHiddenDangerExam.setModifyManTemp(handleEntity.getModifyMan());
                    }
                    if(handleEntity.getModifyDate() != null){
                        String modifyDate = sdf.format(handleEntity.getModifyDate());
                        tBHiddenDangerExam.setModifyDateTemp(modifyDate);
                    }
                    if(StringUtils.isNotBlank(handleEntity.getRectMeasures())){
                        tBHiddenDangerExam.setModifyRemarkTemp(handleEntity.getRectMeasures());
                    }

                }
                if(handleStatus.equals(Constants.REVIEWSTATUS_PASS)){
                    // 复查通过
                    if(StringUtils.isNotBlank(handleEntity.getModifyMan())){
                        tBHiddenDangerExam.setModifyManTemp(handleEntity.getModifyMan());
                    }
                    if(handleEntity.getModifyDate() != null){
                        String modifyDate = sdf.format(handleEntity.getModifyDate());
                        tBHiddenDangerExam.setModifyDateTemp(modifyDate);
                    }

                    if(handleEntity.getReviewDate() != null){
                        String reviewDate = sdf.format(handleEntity.getReviewDate());
                        tBHiddenDangerExam.setReviewDateTemp(reviewDate);
                    }

                    if(StringUtils.isNotBlank(handleEntity.getReviewMan())){
                        tBHiddenDangerExam.setReviewManTemp(handleEntity.getReviewMan());
                    }
                    if(StringUtils.isNotBlank(handleEntity.getRectMeasures())){
                        tBHiddenDangerExam.setModifyRemarkTemp(handleEntity.getRectMeasures());
                    }
                    tBHiddenDangerExam.setReviewResultTemp("复查通过");
                    if(StringUtils.isNotBlank(handleEntity.getReviewReport())){
                        tBHiddenDangerExam.setReviewDetailTemp(handleEntity.getReviewReport());
                    }
                }
                if(handleStatus.equals(Constants.HANDELSTATUS_ROLLBACK_CHECK)){
                    // 复查不通过退回整改
                    if(StringUtils.isNotBlank(handleEntity.getModifyMan())){
                        tBHiddenDangerExam.setModifyManTemp(handleEntity.getModifyMan());
                    }
                    if(handleEntity.getModifyDate() != null){
                        String modifyDate = sdf.format(handleEntity.getModifyDate());
                        tBHiddenDangerExam.setModifyDateTemp(modifyDate);
                    }

                    if(handleEntity.getReviewDate() != null){
                        String reviewDate = sdf.format(handleEntity.getReviewDate());
                        tBHiddenDangerExam.setReviewDateTemp(reviewDate);
                    }

                    if(StringUtils.isNotBlank(handleEntity.getReviewMan())){
                        tBHiddenDangerExam.setReviewManTemp(handleEntity.getReviewMan());
                    }
                    if(StringUtils.isNotBlank(handleEntity.getRectMeasures())){
                        tBHiddenDangerExam.setModifyRemarkTemp(handleEntity.getRectMeasures());
                    }
                    tBHiddenDangerExam.setReviewResultTemp("复查未通过");
                    if(StringUtils.isNotBlank(handleEntity.getReviewReport())){
                        tBHiddenDangerExam.setReviewDetailTemp(handleEntity.getReviewReport());
                    }
                }
                /***** 整改和复查信息 end *****/
            }

            cq = new CriteriaQuery(TBHiddenDangerHandleStepEntity.class);
            cq.eq("hiddenDanger.id",tBHiddenDangerExam.getId());
            cq.addOrder("handleStep", SortDirection.desc);
            cq.add();
            List<TBHiddenDangerHandleStepEntity> handleStepList = systemService.getListByCriteriaQuery(cq,false);
            if(!handleStepList.isEmpty() && handleStepList.size() > 0 ){
                for(TBHiddenDangerHandleStepEntity t:handleStepList){
                    String status = t.getHandleStatus();
                    if(status.equals(Constants.HANDELSTATUS_REPORT)){
                        t.setHandleTypeTemp("上报");
                    }
                    if(status.equals(Constants.HANDELSTATUS_ROLLBACK_REPORT)){
                        t.setHandleTypeTemp("退回上报");
                    }
                    if(status.equals(Constants.HANDELSTATUS_REVIEW)){
                        t.setHandleTypeTemp("整改");
                    }
                    if(status.equals(Constants.REVIEWSTATUS_PASS)){
                        t.setHandleTypeTemp("复查通过");
                    }
                    if(status.equals(Constants.HANDELSTATUS_ROLLBACK_CHECK)){
                        t.setHandleTypeTemp("复查未通过");
                    }

                }
                req.setAttribute("handleStepList",handleStepList);
            }
        }

        /**获取隐患对应图片*/
        if(StringUtil.isNotEmpty(tBHiddenDangerExam.getMobileId())) {
            String imgSql = "select img_path from t_b_hidden_danger_img_rel where mobile_hidden_id='" + tBHiddenDangerExam.getMobileId() + "'";
            List<String> imglist = systemService.findListbySql(imgSql);
            if (!imglist.isEmpty() && imglist.size() > 0) {
                req.setAttribute("imagelists", imglist);
            }
        }
        if(Constants.HIDDENCHECK_EXAMTYPE_GUANLIGANBUXIAJING.equals(examType) || Constants.HIDDENCHECK_EXAMTYPE_KUANGJINGANQUANDAJIANCHA.equals(examType)
                || Constants.HIDDENCHECK_EXAMTYPE_ZHILIANGBIAOZHUNHUA.equals(examType) || Constants.HIDDENCHECK_EXAMTYPE_ZHUANYEKESHI.equals(examType)
                || Constants.HIDDENCHECK_EXAMTYPE_ANJIANYUAN.equals(examType) || Constants.HIDDENCHECK_EXAMTYPE_KLDDB.equals(examType) || Constants.HIDDENCHECK_EXAMTYPE_SANJIAYI.equals(examType)){
            //管理干部下井、矿井安全大检查、质量标准化检查、专业科室日常检查、安监员安全检查、矿领导带班检查
            if(Constants.HIDDENCHECK_EXAMTYPE_ZHUANYEKESHI.equals(examType)){
                //专业科室
                String proType = tBHiddenDangerExam.getProType();
                if(StringUtils.isNotBlank(proType)){
                    String proTypeTemp = DicUtil.getTypeNameByCode("proCate_gradeControl",proType);
                    tBHiddenDangerExam.setProTypeTemp(proTypeTemp);
                }
            }
            if(Constants.HIDDENCHECK_EXAMTYPE_KUANGJINGANQUANDAJIANCHA.equals(examType)){
                //return 矿井安全大检查  修改、查看页面
                String itemUserId = tBHiddenDangerExam.getItemUserId();
                if(StringUtils.isNotBlank(itemUserId)){
                    String ids [] = itemUserId.split(",");
                    StringBuffer sb = new StringBuffer();
                    for(String id : ids){
                        TSUser user = systemService.getEntity(TSUser.class,id);
                        if(user != null){
                            if(StringUtils.isNotBlank(sb.toString())){
                                sb.append(",");
                            }
                            sb.append(user.getRealName());
                        }

                    }
                    tBHiddenDangerExam.setItemUserNameTemp(sb.toString());
                }
                if("detail".equals(load)){
                    //TODO  return to detail page
                    return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamWithAddressList-kjaqdjc-detail");
                }else {
                    return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamWithAddressList-kjaqdjc-update");
                }
            }
            if ("detail".equals(load)){
                //TODO return to detail page
                return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamWithAddressList-detail");
            }else{
                return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExamWithAddressList-update");
            }
        }
        if("detail".equals(load)){
            //TODO return to detail page
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExam-detail");
        }else{
            return new ModelAndView("com/sdzk/buss/web/hiddendanger/tBHiddenDangerExam-update");
        }
    }

    /*********************************************************风险管控状态清单 end***************************************************/
}