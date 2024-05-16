package com.sdzk.buss.api.controller;

import com.sdzk.buss.api.service.ApiServiceI;
import com.sdzk.buss.web.common.Constants;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.util.DicUtil;
import org.jeecgframework.core.util.EhcacheUtil;
import org.jeecgframework.core.util.LogUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @user hanxudong
 * 隐患查询,接收录入数据,整改复查接口
 */
@Controller
@RequestMapping("/mobile/mobileDecisionAnalyseController")
public class MobileDecisionAnalyseController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private ApiServiceI apiService;

    private String sessionCache="sessionCache";

    /**
     *
     * @param token
     * @param sessionId     用户当前登录的sessionid
     * @param queryType     查询类型
     * @param page          当前页数
     * @param rows          查询行数
     * @return
     */
    @RequestMapping("/test")
    public ModelAndView test(String token, HttpServletRequest request){
        //TODO TOKEN验证
        return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/test");
        //return new ModelAndView("redirect:tBDecisionAnalyseController.do?riskHiddenDepartDistribution");
        //return new ModelAndView("forward:/tBDecisionAnalyseController.do?riskHiddenDepartDistribution");
    }


    /**
     *
     * @param token
     * @param sessionId     用户当前登录的sessionid
     * @param queryType     查询类型
     * @return
     */
    @RequestMapping("/hdStatistics")
    public ModelAndView hdStatistics(String token, HttpServletRequest request){
        //TODO TOKEN验证
        //TODO token校验
        try {
            String sessionId = request.getParameter("sessionId");
            if (StringUtils.isBlank(sessionId)) {
                return new ModelAndView("mobile/mobileLogin/login.do");
            }
            TSUser user = (TSUser)EhcacheUtil.get(sessionCache, sessionId);
            if (user == null) {
                return new ModelAndView("mobile/mobileLogin/login.do");
            }
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            request.setAttribute("date", sdf.format(date));
            return new ModelAndView("com/sdzk/buss/web/decisionAnalyse/hiddenDangerStatistics");
        }catch (Exception e) {
            LogUtil.error("隐患统计查询错误",e);
            return new ModelAndView("mobile/mobileLogin/login.do");
        }
    }

    /**
     * 隐患类型图表数据
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "hdType")
    @ResponseBody
    public JSONObject hdType(HttpServletRequest request) throws Exception {
        String date = request.getParameter("date");
        StringBuffer sb = new StringBuffer();

        sb.append("select e.hidden_type hdType, count(*) count from t_b_hidden_danger_exam e, t_b_hidden_danger_handle h where e.id=h.hidden_danger_id " +
                " and h.handlel_status not in ('" + Constants.HANDELSTATUS_DRAFT + "','" + Constants.HANDELSTATUS_ROLLBACK_REPORT + "')");
        if(StringUtils.isNotBlank(date)){
            sb.append(" and DATE_FORMAT(e.exam_date,'%Y-%m-%d')='").append(date).append("' ");
        }
        sb.append("group by e.hidden_type");
        List<Map<String, Object>> queryList = systemService.findForJdbc(sb.toString());
        String result = "{\'pieData\':[";
        if(queryList != null && !queryList.isEmpty()){
            int index = 0;
            for(int i =0 ;i<queryList.size();i++){
                index++;
                Map<String,Object> tempMap = queryList.get(i);
                if(tempMap.get("hdType") != null && tempMap.get("count") != null){
                    String hdType = tempMap.get("hdType").toString();
                    String count = tempMap.get("count").toString();
                    String hdTypeName = DicUtil.getTypeNameByCode("hiddenType",hdType);
                    result = result + "{\'name\':\'" +hdTypeName
                            + "\'," + "\'id\':\'" + hdType
                            + "\'," + "\'y\':"
                            + Integer.valueOf(count)
                            + "},";
                }
            }
        }else{
            result = result + "{\'name\':\'暂无数据\'," + "\'id\':\'暂无数据\'," + "\'y\':100,\'color\':\'#68AD30\'},";
        }
        result += "]}";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pieData",result);
        return jsonObject;
    }

    /**
     * 隐患等级图表数据
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "hdLevel")
    @ResponseBody
    public JSONObject hdLevel(HttpServletRequest request) throws Exception {
        String date = request.getParameter("date");
        StringBuffer sb = new StringBuffer();

        sb.append("select e.hidden_nature hdLevel, count(*) count from t_b_hidden_danger_exam e, t_b_hidden_danger_handle h where e.id=h.hidden_danger_id " +
                " and h.handlel_status not in ('" + Constants.HANDELSTATUS_DRAFT + "','" + Constants.HANDELSTATUS_ROLLBACK_REPORT + "')");
        if(StringUtils.isNotBlank(date)){
            sb.append(" and DATE_FORMAT(e.exam_date,'%Y-%m-%d')='").append(date).append("' ");
        }
        sb.append("group by e.hidden_nature");
        List<Map<String, Object>> queryList = systemService.findForJdbc(sb.toString());
        String result = "{\'pieData\':[";
        if(queryList != null && !queryList.isEmpty()){
            int index = 0;
            for(int i =0 ;i<queryList.size();i++){
                index++;
                Map<String,Object> tempMap = queryList.get(i);
                if(tempMap.get("hdLevel") != null && tempMap.get("count") != null){
                    String hdLevel = tempMap.get("hdLevel").toString();
                    String count = tempMap.get("count").toString();
                    String hdLevelName = DicUtil.getTypeNameByCode("hiddenLevel",hdLevel);
                    result = result + "{\'name\':\'" +hdLevelName
                            + "\'," + "\'id\':\'" + hdLevel
                            + "\'," + "\'y\':"
                            + Integer.valueOf(count)
                            + "},";
                }
            }
        }else{
            result = result + "{\'name\':\'暂无数据\'," + "\'id\':\'暂无数据\'," + "\'y\':100,\'color\':\'#68AD30\'},";
        }
        result += "]}";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pieData",result);
        return jsonObject;
    }
}