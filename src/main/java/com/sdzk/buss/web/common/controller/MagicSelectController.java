package com.sdzk.buss.web.common.controller;

import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
import com.sdzk.buss.web.wechattemplatemanagement.entity.WechatTemplateManagementEntity;
import net.sf.json.JSONArray;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.hibernate.qbc.PageList;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by dell on 2017/6/19.
 * 初始化下拉菜单
 */
@Controller
@RequestMapping("/magicSelectController")
public class MagicSelectController {
    private SystemService systemService;
    @Autowired
    public void setSystemService(SystemService systemService) {
        this.systemService = systemService;
    }


    /**
     * 微信获取模板初始化
     * @return
     */
    @RequestMapping(params = "getWeChatModelList")
    @ResponseBody
    public AjaxJson getWeChatModelList(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Map<String, Object> resMap = new HashMap<String, Object>();
        //查询条件组装器
        String modelType = request.getParameter("modelType");
        StringBuffer sql = new StringBuffer("select b.id,b.model_name modelName from wechat_template_management b where b.parent_model_name in(select id from wechat_template_management a where a.model_type is not null)");
        if (StringUtils.isNotBlank(modelType)) {
            sql = new StringBuffer("select b.id,b.model_name modelName from wechat_template_management b where b.parent_model_name=(select id from wechat_template_management a where a.model_type='"+modelType+"')");
        }

        List<Map<String, Object>> result = systemService.findForJdbc(sql.toString());
        JSONArray jsonArray = JSONArray.fromObject(result);
        j.setObj(jsonArray);
        return j;
    }

    /**
     * 根据id获取模板的内容并替换掉相应的字段，获取内容用对象替换
     * @return
     */
    @RequestMapping(params = "getWeChatContentById")
    @ResponseBody
    public AjaxJson getWeChatContentByIdForHidden(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        Object obj = null;
        String resultContent = "";
        //获取模板内容
        String content = "";
        String modelId = request.getParameter("modelId");
        if (StringUtils.isNotBlank(modelId)) {
            WechatTemplateManagementEntity entity = systemService.getEntity(WechatTemplateManagementEntity.class, modelId);
            content = entity.getModelContent().toString();
        }
        //获取模块实体类类型
        String modelType = request.getParameter("modelType");
        if (StringUtils.isNotBlank(modelType)) {
            if ("2".equals(modelType)) {//隐患
                //查询条件组装器
                String HandleId = request.getParameter("HandleId");
                //直接获取，因为懒加载会取缓存里的null
//		        TBHiddenDangerHandleEntity hidden = systemService.getEntity(TBHiddenDangerHandleEntity.class, HandleId);
//		        String examId = hidden.getHiddenDanger().getId();
                Map<String, Object> hideID = systemService.findOneForJdbc("select hidden_danger_id from t_b_hidden_danger_handle where id=?", HandleId);
                String examId = "";
                if (StringUtils.isNotBlank(hideID.get("hidden_danger_id").toString())) {
                    examId = String.valueOf(hideID.get("hidden_danger_id"));
                }
                TBHiddenDangerExamEntity examEntity = systemService.getEntity(TBHiddenDangerExamEntity.class, examId);
                //处理一些字段
                examEntity.setAddressName(examEntity.getAddress().getAddress());
                examEntity.setDutyUnitName(examEntity.getDutyUnit().getDepartname());
                examEntity.setExamDateTemp(getTempDate(examEntity.getExamDate()));
                if (examEntity.getLimitDate()!=null) {
                    examEntity.setLimitDateTemp(getTempDate(examEntity.getLimitDate()));
                }
                obj = examEntity;
            }else if("3".equals(modelType)) {//三违
                //查询条件组装器
                String threeId = request.getParameter("threeId");
                TBThreeViolationsEntity tBThreeViolations = null;
                String departname = "";
                String address = "";
                Map<String, Object> codeData = systemService.findOneForJdbc("select a.departname,b.address from t_b_three_violations v LEFT JOIN t_s_depart a  on v.vio_units=a.id LEFT JOIN t_b_address_info b on v.vio_address=b.id where v.id=?", threeId);
                if (codeData!=null&&codeData.size()>0) {
                    departname = String.valueOf(codeData.get("departname"));
                    address = String.valueOf(codeData.get("address"));
                }
                if (StringUtils.isNotBlank(threeId)) {
                    tBThreeViolations = systemService.getEntity(TBThreeViolationsEntity.class, threeId);
                }

                //处理一些字段
                tBThreeViolations.setAddressTemp(address);
                tBThreeViolations.setVioUnitesNameTemp(departname);
                if (tBThreeViolations.getVioDate()!=null) {
                    //用remark当做违章时间
                    tBThreeViolations.setRemark(getTempDate(tBThreeViolations.getVioDate()));
                }
                obj = tBThreeViolations;
            }

            //下面要映射的是内容的类，抽成公用方法
            resultContent = getReflectResult(content,obj);

        }

        j.setObj(resultContent);
        return j;
    }
    private String getTempDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd");
        String dateString = df.format(date);
        return dateString;
    }

    //利用反射获取属性的值并进行替换
    public String getReflectResult(String content,Object entity) {
        if (StringUtils.isNotBlank(content)&&entity!=null) {
            Field[] f=entity.getClass().getDeclaredFields();
            for(int i=0;i<f.length;i++){
                f[i].setAccessible(true);
                String attributeName=f[i].getName();
                String value = "";
                if(f[i].getType().getName().equals(
                        String.class.getName())){
                    try {
                        value = (String) f[i].get(entity);
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                if (content.indexOf(attributeName)!=-1) {
                    content = content.replace("{"+attributeName+"}", value+","+"\r\n");
                }
            }
            if (content.indexOf("&&")!=-1) {
                content = content.replace("&&","\r\n");
            }
        }else{
            content = "警告：模板没有内容或者字段不匹配，请勿发送！";
        }
        return content;
    }

    /**
     * 用户信息初始化
     * @param user
     * @param request
     * @param response
     * @param dataGrid
     * @param dataGrid
     * @return
     */
    @RequestMapping(params = "getUserList")
    @ResponseBody
    public JSONArray getUserList(TSUser user, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        //查询条件组装器
        StringBuffer sql = new StringBuffer("select u.id, concat(u.realName, ' - ' ,u.userName) realName ,u.userName, d.id departId, d.departName,IFNULL(u.spelling,'') spelling, IFNULL(u.full_spelling,'') fullSpelling from t_s_base_user u join t_s_user_org o on u.id=o.user_id JOIN t_s_depart d ON o.org_id = d.id where u.delete_flag='0'");

        sql.append("and u.status in ('").append(Globals.User_Normal).append("','").append(Globals.User_ADMIN).append("','").append(Globals.User_Forbidden).append("') ");
        String selected = request.getParameter("selected");
        if(StringUtils.isNotBlank(selected)){
            sql.append("and u.status not in('").append(selected.replace(",","','")).append("') ");
        }
        //添加组织机构查询条件
        String orgIds = request.getParameter("orgIds");
        if (orgIds != null && orgIds.length() > 0){
            sql.append("and o.org_id in ('").append(orgIds.replace(",","','")).append("') ");
        }
        List<Map<String, Object>> result = systemService.findForJdbc(sql.toString());
        JSONArray jsonArray = JSONArray.fromObject(result);
        return jsonArray;
    }

    /**
     * 地点信息初始化
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "getAddressList")
    @ResponseBody
    public JSONArray getAddressList(HttpServletRequest request)
            throws Exception {
        CriteriaQuery cq = new CriteriaQuery(TBAddressInfoEntity.class);
//        cq.setPageSize(10000);
        cq.eq("isDelete", Constants.IS_DELETE_N);
        cq.eq("isShowData", Constants.IS_SHOWDATA_Y);
        cq.add();
        List<TBAddressInfoEntity> addressInfoEntityList = this.systemService.getListByCriteriaQuery(cq, false);
        String addressId = request.getParameter("addressId");
        if(StringUtil.isNotEmpty(addressId)){
            TBAddressInfoEntity addressInfoEntity = systemService.getEntity(TBAddressInfoEntity.class,addressId);
            if (addressInfoEntity!=null){
                addressInfoEntityList.add(addressInfoEntity);
            }
        }
        List<Map<String, Object>> result = new ArrayList<>();
        if (addressInfoEntityList != null && addressInfoEntityList.size() > 0){
            for (TBAddressInfoEntity address: addressInfoEntityList) {
                Map<String, Object> map = new HashedMap();
                map.put("id", address.getId());
                map.put("address", address.getAddress());
                result.add(map);
            }
        }
        JSONArray jsonArray = JSONArray.fromObject(result);
        return jsonArray;
    }

    /**
     * 部门信息初始化
     * @param request
     * @param dataGrid
     */
    @RequestMapping(params = "departSelectDataGridMagic")
    @ResponseBody
    public net.sf.json.JSONArray  departSelectDataGridMagic(HttpServletRequest request, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TSDepart.class, dataGrid);
        cq.eq("deleteFlag", Globals.Delete_Normal);         //Globals.Delete_Normal用来判断该数据是否被删除，Delete_Normal表示正常，Delete_Forbidden表示被删除
//        cq.setPageSize(10000);
        cq.add();
        List<TSDepart> list = this.systemService.getListByCriteriaQuery(cq, false);
        List<Map<String, Object>> result = new ArrayList<>();
        if (list != null && list.size() > 0){
            for (TSDepart depart : list) {
                Map<String, Object> map = new HashedMap();
                map.put("id", depart.getId());
                map.put("departName", depart.getDepartname());
                map.put("spelling", depart.getSpelling()!=null?depart.getSpelling():"");
                map.put("fullSpelling", depart.getFullSpelling()!=null?depart.getFullSpelling():"");
                result.add(map);
            }
        }
        net.sf.json.JSONArray jsonArray = net.sf.json.JSONArray.fromObject(result);
        return jsonArray;
    }

    /**
     * 工种信息初始化
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "getPostList")
    @ResponseBody
    public JSONArray getPostList(HttpServletRequest request)
            throws Exception {
        String sql = "select id, post_name from t_b_post_manage where is_delete != '1'";
        List<Map<String, Object>> postList = systemService.findForJdbc(sql);
        JSONArray jsonArray = JSONArray.fromObject(postList);
        return jsonArray;
    }

    /**
     * 作业活动初始化
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "getActivityList")
    @ResponseBody
    public JSONArray getActivityList(HttpServletRequest request)
            throws Exception {
        String sql = "select id, activity_name from t_b_activity_manage where is_delete != '1' ";
        List<Map<String, Object>> activityList = systemService.findForJdbc(sql);
        JSONArray jsonArray = JSONArray.fromObject(activityList);
        return jsonArray;
    }

    /**
     * 第一类危险源初始化
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "getHazardList")
    @ResponseBody
    public JSONArray getHazardList(HttpServletRequest request)
            throws Exception {
        String sql = "select id, hazard_name from t_b_hazard_manage where is_delete != '1' ";
        List<Map<String, Object>> hazardList = systemService.findForJdbc(sql);
        JSONArray jsonArray = JSONArray.fromObject(hazardList);
        return jsonArray;
    }

    /**
     * 隐患录入根据风险点和类型筛选风险
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "getRiskList")
    @ResponseBody
    public JSONArray getRiskList(HttpServletRequest request)
            throws Exception {
        String riskId = request.getParameter("riskId");
        String address = request.getParameter("address");
        String riskType = request.getParameter("riskType");
        String sql = "select id,risk_desc,risk_level from t_b_risk_identification where risk_type = '"+riskType+"' and address_id = '"+address+"' and status = '3'  and is_del = '0' ";
        String post = request.getParameter("post");
        if(StringUtil.isNotEmpty(post)){
            sql = "select id,risk_desc,risk_level from t_b_risk_identification where risk_type = '"+riskType+"' and (post_id = '"+post+"' or address_id = '"+address+"') and status = '3' and is_del = '0' ";
        }
        List<Map<String, Object>> risks = systemService.findForJdbc(sql);
        if(StringUtil.isNotEmpty(riskId)){
            sql = "select id,risk_desc,risk_level from t_b_risk_identification where id = '"+riskId+"'";
            risks = systemService.findForJdbc(sql);
        }
        JSONArray jsonArray = JSONArray.fromObject(risks);
        return jsonArray;
    }


    /**
     * 隐患录入根据风险筛选危害因素
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "getHazardFactorList")
    @ResponseBody
    public JSONArray getHazardFactorList(HttpServletRequest request)
            throws Exception {
        String hazardFactorId = request.getParameter("hazardFactorId");
        String riskId = request.getParameter("riskId");
        String sql = "SELECT id,hazard_factors from t_b_hazard_factors WHERE id in (SELECT hazard_factors_id from t_b_risk_factors_rel WHERE risk_identification_id = '"+riskId+"')";
        List<Map<String, Object>> hazardFactors = systemService.findForJdbc(sql);
        if(StringUtil.isNotEmpty(hazardFactorId)){
            sql = "select id,hazard_factors from t_b_hazard_factors where id = '"+hazardFactorId+"'";
            hazardFactors = systemService.findForJdbc(sql);
        }
        JSONArray jsonArray = JSONArray.fromObject(hazardFactors);
        return jsonArray;
    }


    /**
     * 隐患录入根据风险点和类型筛选风险
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "getMajorList")
    @ResponseBody
    public JSONArray getMajorList(HttpServletRequest request)
            throws Exception {
        String manageType = request.getParameter("manageType");
        String sql = "select id,major_name from t_b_risk_manage_major where manageType = '"+manageType+"'";
        List<Map<String, Object>> majors = systemService.findForJdbc(sql);
        JSONArray jsonArray = JSONArray.fromObject(majors);
        return jsonArray;
    }


    @RequestMapping(params = "getPostNameNoExists")
    @ResponseBody
    public JSONArray getPostNameNoExists(HttpServletRequest request)
            throws Exception {
        String sql = "SELECT post_name from t_b_post_manage WHERE is_delete != '1'  AND post_name NOT IN (SELECT major_name FROM t_b_risk_manage_major WHERE manageType = 'post');";
        List<Map<String, Object>> postName = systemService.findForJdbc(sql);
        JSONArray jsonArray = JSONArray.fromObject(postName);
        return jsonArray;
    }


    @RequestMapping(params = "getPostName")
    @ResponseBody
    public JSONArray getPostName(HttpServletRequest request)
            throws Exception {
        String sql = "SELECT post_name from t_b_post_manage WHERE is_delete != '1';";
        List<Map<String, Object>> postName = systemService.findForJdbc(sql);
        JSONArray jsonArray = JSONArray.fromObject(postName);
        return jsonArray;
    }


    @RequestMapping(params = "getModularList")
    @ResponseBody
    public JSONArray getModularList(HttpServletRequest request)
            throws Exception {
        String sql = "SELECT id,module_name FROM t_b_hazard_module";
        List<Map<String, Object>> moduleName = systemService.findForJdbc(sql);
        JSONArray jsonArray = JSONArray.fromObject(moduleName);
        return jsonArray;
    }


    @RequestMapping(params = "getHiddenNature")
    @ResponseBody
    public JSONArray getHiddenNature(HttpServletRequest request)
            throws Exception {
        String sql = "select typecode,typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='hiddenLevel')";
        List<Map<String, Object>> hiddenNature = systemService.findForJdbc(sql);
        JSONArray jsonArray = JSONArray.fromObject(hiddenNature);
        return jsonArray;
    }


    @RequestMapping(params = "getRiskType")
    @ResponseBody
    public JSONArray getRiskType(HttpServletRequest request)
            throws Exception {
        String sql = "select typecode,typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='risk_Type')";
        List<Map<String, Object>> riskType = systemService.findForJdbc(sql);
        JSONArray jsonArray = JSONArray.fromObject(riskType);
        return jsonArray;
    }

    @RequestMapping(params = "getMajor")
    @ResponseBody
    public JSONArray getMajor(HttpServletRequest request)
            throws Exception {
        String sql = "select typecode,typename from t_s_type where typegroupid=(select id from t_s_typegroup where typegroupcode='major')";
        List<Map<String, Object>> riskType = systemService.findForJdbc(sql);
        JSONArray jsonArray = JSONArray.fromObject(riskType);
        return jsonArray;
    }

    //获取专项辨识活动
    @RequestMapping(params = "getSpeIdeTask")
    @ResponseBody
    public JSONArray getSpeIdeTask(HttpServletRequest request)
            throws Exception {
        String riskTaskId = request.getParameter("riskTaskId");
        String speIdeType = request.getParameter("speIdeType");
        String sql = "select id, task_name taskname from t_b_risk_task where task_type='3' and id <> ? and id in (select risk_task_id from t_b_risk_identification where is_del='0' and status='3')";
        List<Map<String, Object>> speIdeTask;
        if (StringUtil.isNotEmpty(speIdeType)) {
            speIdeTask = systemService.findForJdbc(sql+" and spe_ide_type=?",riskTaskId,speIdeType);
        } else {
            speIdeTask = systemService.findForJdbc(sql,riskTaskId);
        }
        JSONArray jsonArray = JSONArray.fromObject(speIdeTask);
        return jsonArray;
    }

    @RequestMapping(params = "getLinShiIdeTask")
    @ResponseBody
    public JSONArray getLinShiIdeTask(HttpServletRequest request)
            throws Exception {
        String riskTaskId = request.getParameter("riskTaskId");
        String sql = "select id, task_name taskname from t_b_risk_task where task_type='5' and id <> ? and id in (select risk_task_id from t_b_risk_identification where is_del='0' and status='3')";
        List<Map<String, Object>> speIdeTask = systemService.findForJdbc(sql,riskTaskId);
        JSONArray jsonArray = JSONArray.fromObject(speIdeTask);
        return jsonArray;
    }

}
