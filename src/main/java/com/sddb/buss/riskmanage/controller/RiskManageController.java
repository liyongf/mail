package com.sddb.buss.riskmanage.controller;

import com.sddb.buss.identification.entity.RiskFactortsPostRel;
import com.sddb.buss.identification.entity.RiskFactortsRel;
import com.sddb.buss.identification.entity.RiskIdentificationEntity;
import com.sddb.buss.identification.entity.RiskIdentificationPostEntity;
import com.sddb.buss.riskdata.entity.HazardModuleEntity;
import com.sddb.buss.riskmanage.entity.*;
import com.sddb.common.Constants;
import com.sdzk.buss.web.address.entity.TBAddressInfoEntity;
import com.sdzk.buss.web.homePage.entity.MajorRiskEntityVO;
import com.sdzk.buss.web.tbpostmanage.entity.TBPostManageEntity;
import com.sdzk.buss.web.violations.entity.TBThreeViolationsEntity;
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
import org.jeecgframework.poi.excel.entity.TemplateExportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.vo.TemplateExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSType;
import org.jeecgframework.web.system.pojo.base.TSTypegroup;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**   
 * @Title: Controller  
 * @Description: t_b_danger_source
 * @author onlineGenerator
 * @date 2017-06-20 14:18:52
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/riskManageController")
public class RiskManageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(RiskManageController.class);

	@Autowired
	private SystemService systemService;


    /**
     * 管控清单
     * @param request
     * @return
     */
    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) {
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        String riskManageName = DicUtil.getTypeNameByCode("manageType",manageType);
        request.setAttribute("riskManageName",riskManageName);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageList");
    }
    //我的管控清单
    @RequestMapping(params = "myList")
    public ModelAndView myList(HttpServletRequest request, HttpServletResponse response) {
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        String riskManageName = DicUtil.getTypeNameByCode("manageType",manageType);
        request.setAttribute("riskManageName",riskManageName);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageMyList");
    }

    //我的专业管控清单 动态管控也要这个模式，直接复制过来 major 不仅是专业
    @RequestMapping(params = "myMajorList")
    public ModelAndView myMajorList(HttpServletRequest request, HttpServletResponse response) {
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        String riskManageName = DicUtil.getTypeNameByCode("manageType",manageType);
        request.setAttribute("riskManageName",riskManageName);
        String riskManager = riskManageName.replace("管控","");
        request.setAttribute("riskManager",riskManager);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageMyMajorList");
    }


    //我的岗位管控清单 做一个新的
    @RequestMapping(params = "myPostListNew")
    public ModelAndView myPostListNew(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageMyPostListNew");
    }

    //专业管控清单 动态管控也要这个模式，直接复制过来 major 不仅是专业
    @RequestMapping(params = "majorList")
    public ModelAndView majorList(HttpServletRequest request, HttpServletResponse response) {
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        String riskManageName = DicUtil.getTypeNameByCode("manageType",manageType);
        request.setAttribute("riskManageName",riskManageName);
        String riskManager = riskManageName.replace("管控","");
        request.setAttribute("riskManager",riskManager);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageMajorList");
    }
    //岗位单独做一个岗位清单
    @RequestMapping(params = "postListNew")
    public ModelAndView postListNew(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManagepostListNew");
    }

    @RequestMapping(params = "relRiskDatagrid")
    public void relRiskDatagrid(RiskManageEntity riskManage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(RiskManageEntity.class,dataGrid);
        if(null!=riskManage && null!=riskManage.getRisk() && null!=riskManage.getRisk().getAddress()){
            cq.createAlias("risk.address","risk_address");
            riskManage.getRisk().getAddress().setAddress(null);
        }
        if(null!=riskManage && null!=riskManage.getRisk() && null!=riskManage.getRisk().getPost()){
            cq.createAlias("risk.post","risk_post");
            riskManage.getRisk().getPost().setPostName(null);
        }
        if(null!=riskManage && null!=riskManage.getRisk()){
            if(StringUtil.isNotEmpty(riskManage.getRisk().getDutyManager())){
                riskManage.getRisk().setDutyManager("*"+riskManage.getRisk().getDutyManager()+"*");
            }
        }
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskManage, request.getParameterMap());

        try{

            String addressName = request.getParameter("risk.address.address");
            if(StringUtil.isNotEmpty(addressName)){
                cq.like("risk_address.address","%"+addressName+"%");
            }
            String postName = request.getParameter("risk.post.postName");
            if(StringUtil.isNotEmpty(postName)){
                cq.like("risk_post.postName","%"+postName+"%");
            }

            String addressId = request.getParameter("addressId");
            if(StringUtil.isNotEmpty(addressId)){
                cq.createAlias("risk.address","risk_address");
                cq.eq("risk_address.id",addressId);
            }

            String postId = request.getParameter("postId");
            if(StringUtil.isNotEmpty(postId)){
                cq.createAlias("risk.post","risk_post");
                cq.eq("risk_post.id",postId);
            }
            String majorId = request.getParameter("majorId");
            if(StringUtil.isNotEmpty(majorId)){
                cq.eq("majorId",majorId);
            }
            String majorRelId = request.getParameter("majorRelId");
            if(StringUtil.isNotEmpty(majorRelId)){
                RiskManageMajorRelEntity riskManageMajorRelEntity = systemService.getEntity(RiskManageMajorRelEntity.class,majorRelId);
                cq.eq("majorId",riskManageMajorRelEntity.getMajorId());
                cq.eq("myUserId",riskManageMajorRelEntity.getUserId());
            }else{
                cq.add(
                        Restrictions.or(Restrictions.eq("myUserId", ""),
                                Restrictions.isNull("myUserId"))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.createAlias("risk","risk");
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskManageEntity> list = dataGrid.getResults();
            for (RiskManageEntity riskManageEntity : list) {
                RiskIdentificationEntity bean = riskManageEntity.getRisk();
                List<RiskFactortsRel> relList = bean.getRelList();
                if (relList == null) {
                    bean.setHazardFactortsNum("0");
                }
                bean.setHazardFactortsNum(relList.size() + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    //岗位单独做一个
    @RequestMapping(params = "relPostRiskDatagrid")
    public void relPostRiskDatagrid(RiskManagePostEntity riskManagePostEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(RiskManagePostEntity.class,dataGrid);
        try{
            String postRelId = request.getParameter("postRelId");
            if(StringUtil.isNotEmpty(postRelId)){
                RiskManagePostRelEntity riskManagePostRelEntity = systemService.getEntity(RiskManagePostRelEntity.class,postRelId);
                cq.eq("myUserId",riskManagePostRelEntity.getUserId());
            }
            String riskType = request.getParameter("risk.riskType");
            if(StringUtil.isNotEmpty(riskType)){
                cq.eq("risk.riskType",riskType);
            }
            String riskLevel = request.getParameter("risk.riskLevel");
            if(StringUtil.isNotEmpty(riskLevel)){
                cq.eq("risk.riskLevel",riskLevel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.createAlias("risk","risk");
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskManagePostEntity> list = dataGrid.getResults();
            for (RiskManagePostEntity riskManagePost : list) {
                RiskIdentificationPostEntity bean = riskManagePost.getRisk();
                List<RiskFactortsPostRel> relList = bean.getPostRelList();
                if (relList == null) {
                    bean.setHazardFactortsPostNum("0");
                }
                bean.setHazardFactortsPostNum(relList.size() + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    //专业管控 动态管控直接复制过来
    @RequestMapping(params = "datagridMajor")
    public void datagridMajor(RiskManageMajorEntity riskManageMajorEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(RiskManageMajorEntity.class,dataGrid);
        try{
            String majorName = request.getParameter("majorName");
            String manageType = request.getParameter("manageType");
            if(StringUtil.isNotEmpty(majorName)){
                cq.like("majorName", "%"+majorName+"%");
            }
            if(StringUtil.isNotEmpty(manageType)){
                cq.eq("manageType", manageType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "datagridMyMajor")
    public void datagridMajor(RiskManageMajorRelEntity riskManageMajorRelEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(RiskManageMajorRelEntity.class,dataGrid);
        try{
            String manageType = request.getParameter("manageType");
            TSUser user = ResourceUtil.getSessionUserName();
            cq.eq("userId",user.getId());
            if(StringUtil.isNotEmpty(manageType)){
                cq.eq("manageType",manageType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskManageMajorRelEntity> list = dataGrid.getResults();
            if(list.size()>0){
                request.setAttribute("yes","1");
            }
            for (RiskManageMajorRelEntity riskManageMajorRel : list) {
               String sql = "SELECT major_name from  t_b_risk_manage_major WHERE id = '"+riskManageMajorRel.getMajorId()+"'";
               List<String> majorName = systemService.findListbySql(sql);
                riskManageMajorRel.setMajorNameTemp(majorName.get(0).toString());
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "datagridMyPost")
    public void datagridMyPost(RiskManagePostRelEntity riskManagePostRelEntity, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(RiskManagePostRelEntity.class,dataGrid);
        try{
            TSUser user = ResourceUtil.getSessionUserName();
            cq.eq("userId",user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 获取当前状态
     * @param id
     * @return
     */
    @RequestMapping(params = "getMyMajor")
    @ResponseBody
    public String getMyMajor(String id,HttpServletRequest request){
        String manageType = request.getParameter("manageType");
        TSUser user = ResourceUtil.getSessionUserName();
        String sql = "SELECT id from  t_b_risk_manage_major_rel WHERE user_id = '"+user.getId()+"' and manageType = '"+manageType+"'";
        List<String> ids = systemService.findListbySql(sql);
        if (ids == null||ids.size()==0){
            return "1";
        }
        return null;
    }

    /**
     * 获取当前状态
     * @param id
     * @return
     */
    @RequestMapping(params = "getMyPost")
    @ResponseBody
    public String getMyPost(String id,HttpServletRequest request){
        TSUser user = ResourceUtil.getSessionUserName();
        String sql = "SELECT id from  t_b_risk_manage_post_rel WHERE user_id = '"+user.getId()+"'";
        List<String> ids = systemService.findListbySql(sql);
        if (ids == null||ids.size()==0){
            return "1";
        }
        return null;
    }


    @RequestMapping(params = "goAddMyMajor")
    public ModelAndView goAddMyMajor(RiskManageMajorRelEntity riskManageMajorRelEntity , HttpServletRequest request) {
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        String riskManageName = DicUtil.getTypeNameByCode("manageType",manageType);
        request.setAttribute("riskManageName",riskManageName);
        String riskManager = riskManageName.replace("管控","");
        request.setAttribute("riskManager",riskManager);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageMyMajor-add");
    }

    @RequestMapping(params = "goAddMyPost")
    public ModelAndView goAddMyPost(RiskManagePostRelEntity riskManagePostRelEntity , HttpServletRequest request) {
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageMyPost-add");
    }

    @RequestMapping(params = "doAddMyMajor")
    @ResponseBody
    public AjaxJson doAddMyMajor(RiskManageMajorRelEntity riskManageMajorRelEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        try{
            TSUser user = ResourceUtil.getSessionUserName();
            riskManageMajorRelEntity.setUserId(user.getId());
            systemService.save(riskManageMajorRelEntity);
        }catch(Exception e){
            e.printStackTrace();
            message = "添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doAddMyPost")
    @ResponseBody
    public AjaxJson doAddMyPost(RiskManagePostRelEntity riskManagePostRelEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        try{
            TSUser user = ResourceUtil.getSessionUserName();
            riskManagePostRelEntity.setUserId(user.getId());
            String postId = request.getParameter("postId");
            if(StringUtil.isNotEmpty(postId)){
                TBPostManageEntity postManageEntity = systemService.getEntity(TBPostManageEntity.class,postId);
                riskManagePostRelEntity.setPost(postManageEntity);
            }
            systemService.save(riskManagePostRelEntity);
        }catch(Exception e){
            e.printStackTrace();
            message = "添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goUpdateMyMajor")
    public ModelAndView goUpdateMyMajor(RiskManageMajorRelEntity riskManageMajorRelEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(riskManageMajorRelEntity.getId())) {
            riskManageMajorRelEntity = systemService.getEntity(RiskManageMajorRelEntity.class, riskManageMajorRelEntity.getId());
            req.setAttribute("riskManageMajorRelPage", riskManageMajorRelEntity);
        }
        String manageType = req.getParameter("manageType");
        req.setAttribute("manageType",manageType);
        String riskManageName = DicUtil.getTypeNameByCode("manageType",manageType);
        req.setAttribute("riskManageName",riskManageName);
        String riskManager = riskManageName.replace("管控","");
        req.setAttribute("riskManager",riskManager);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageMyMajor-update");
    }

    @RequestMapping(params = "doUpdateMyMajor")
    @ResponseBody
    public AjaxJson doUpdateMyMajor(RiskManageMajorRelEntity riskManageMajorRelEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "更新成功";
        RiskManageMajorRelEntity t = systemService.get(RiskManageMajorRelEntity.class, riskManageMajorRelEntity.getId());
        String majorOldId = request.getParameter("majorOldId");
        try {
            if(!majorOldId.equals(riskManageMajorRelEntity.getMajorId())){
                String delSql = "DELETE from t_b_risk_manage  WHERE my_user_id = '"+t.getUserId()+"' and major_id= '"+t.getMajorId()+"'";
                this.systemService.executeSql(delSql);
            }
            MyBeanUtils.copyBeanNotNull2Bean(riskManageMajorRelEntity, t);
            systemService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    //岗位单独做一个
    @RequestMapping(params = "goUpdateMyPost")
    public ModelAndView goUpdateMyPost(RiskManagePostRelEntity riskManagePostRelEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(riskManagePostRelEntity.getId())) {
            riskManagePostRelEntity = systemService.getEntity(RiskManagePostRelEntity.class, riskManagePostRelEntity.getId());
            req.setAttribute("riskManagePostRelPage", riskManagePostRelEntity);
        }
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageMyPost-update");
    }

    //岗位单独做一个
    @RequestMapping(params = "doUpdateMyPost")
    @ResponseBody
    public AjaxJson doUpdateMyPost(RiskManagePostRelEntity riskManagePostRelEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "更新成功";
        RiskManagePostRelEntity t = systemService.get(RiskManagePostRelEntity.class, riskManagePostRelEntity.getId());
        String postOldId = request.getParameter("postOldId");
        String postId = request.getParameter("postId");
        try {
            if(!postOldId.equals(postId)){
                String delSql = "DELETE from t_b_risk_manage_post  WHERE my_user_id = '"+t.getUserId()+"'";
                this.systemService.executeSql(delSql);
            }
            TBPostManageEntity post = systemService.getEntity(TBPostManageEntity.class,postId);
            riskManagePostRelEntity.setPost(post);
            MyBeanUtils.copyBeanNotNull2Bean(riskManagePostRelEntity, t);
            systemService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goAddMajor")
    public ModelAndView goAddMajor(RiskManageMajorEntity riskManageMajorEntity , HttpServletRequest request) {
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        String riskManageName = DicUtil.getTypeNameByCode("manageType",manageType);
        request.setAttribute("riskManageName",riskManageName);
        String riskManager = riskManageName.replace("管控","");
        request.setAttribute("riskManager",riskManager);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageMajor-add");
    }

    @RequestMapping(params = "majorNameExists")
    @ResponseBody
    public Map<String, String> majorNameExists(HttpServletRequest request){
        Map<String, String> result = new HashMap();
        String majorName = ResourceUtil.getParameter("param");
        String manageType = request.getParameter("manageType");
        String riskManageName = DicUtil.getTypeNameByCode("manageType",manageType);
        request.setAttribute("riskManageName",riskManageName);
        String riskManager = riskManageName.replace("管控","");
        if (checkMajorNameExists(majorName,manageType)){
            result.put("status", "n");
            result.put("info","此"+riskManager+"已存在！");
        } else {
            result.put("status", "y");
            result.put("info","通过信息验证！");
        }
        String riskManageMajorId = request.getParameter("riskManageMajorId");
        if(StringUtil.isNotEmpty(riskManageMajorId)&&StringUtil.isNotEmpty(majorName)){
            RiskManageMajorEntity riskManageMajorEntity = systemService.getEntity(RiskManageMajorEntity.class,riskManageMajorId);
            if(StringUtil.isNotEmpty(riskManageMajorEntity)){
                if(majorName.equals(riskManageMajorEntity.getMajorName())){
                    result.put("status", "y");
                    result.put("info","通过信息验证！");
                }
            }
        }
        return result;
    }
    private boolean checkMajorNameExists(String majorName,String manageType){
        String sql = "SELECT * FROM t_b_risk_manage_major WHERE manageType = '"+manageType+"' AND major_name = '"+majorName+"'";
        List<String> majorList = systemService.findListbySql(sql);
        if(majorList!=null&&majorList.size()>0){
            return  true;
        }
        return false;
    }

    @RequestMapping(params = "doAddMajor")
    @ResponseBody
    public AjaxJson doAddMajor(RiskManageMajorEntity riskManageMajorEntity, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        try{
            systemService.save(riskManageMajorEntity);
        }catch(Exception e){
            e.printStackTrace();
            message = "添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goUpdateMajor")
    public ModelAndView goUpdateMajor(RiskManageMajorEntity riskManageMajorEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(riskManageMajorEntity.getId())) {
            riskManageMajorEntity = systemService.getEntity(RiskManageMajorEntity.class, riskManageMajorEntity.getId());
            req.setAttribute("riskManageMajorPage", riskManageMajorEntity);
        }
        String manageType = req.getParameter("manageType");
        req.setAttribute("manageType",manageType);
        String riskManageName = DicUtil.getTypeNameByCode("manageType",manageType);
        req.setAttribute("riskManageName",riskManageName);
        String riskManager = riskManageName.replace("管控","");
        req.setAttribute("riskManager",riskManager);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageMajor-update");
    }

    @RequestMapping(params = "doUpdateMajor")
    @ResponseBody
    public AjaxJson doUpdateMajor(RiskManageMajorEntity riskManageMajorEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "更新成功";
        RiskManageMajorEntity t = systemService.get(RiskManageMajorEntity.class, riskManageMajorEntity.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(riskManageMajorEntity, t);
            systemService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doDelMajor")
    @ResponseBody
    public AjaxJson doDelMajor(String id, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "删除成功";
        try{
            String delSql1 = "DELETE FROM t_b_risk_manage WHERE major_id= '"+id+"'";
            String delSql2 = "DELETE FROM t_b_risk_manage_major WHERE id = '"+id+"'";
            String delSql3 = "DELETE FROM t_b_risk_manage_major_rel WHERE major_id = '"+id+"'";
            systemService.executeSql(delSql1);
            systemService.executeSql(delSql2);
            systemService.executeSql(delSql3);
        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "goRiskList")
    public ModelAndView goRiskList(RiskManageMajorEntity riskManageMajorEntity, HttpServletRequest req) {
        String majorId = req.getParameter("majorId");
        req.setAttribute("majorId",majorId);
        String rel = req.getParameter("rel");
        req.setAttribute("rel",rel);
        String manageType = req.getParameter("manageType");
        req.setAttribute("manageType",manageType);
        String majorRelId = req.getParameter("majorRelId");
        req.setAttribute("majorRelId",majorRelId);
        if(StringUtil.isNotEmpty(rel) && rel.equals("noRel")){
            return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageMajorNoRelList");
        }
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageMajorRelList");
    }


    @RequestMapping(params = "goPostRiskList")
    public ModelAndView goPostRiskList(RiskManageMajorEntity riskManageMajorEntity, HttpServletRequest req) {
        String postRelId = req.getParameter("postRelId");
        req.setAttribute("postRelId",postRelId);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManagePostRelList");
    }

    //获取风险库列表，从中选取风险进入风险清单
    @RequestMapping(params = "selectRiskByMajorId")
    public ModelAndView selectRiskByMajorId(HttpServletRequest request) {
        request.setAttribute("year", DateUtils.getYear());
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        String majorId = request.getParameter("majorId");
        request.setAttribute("majorId",majorId);
        String majorRelId = request.getParameter("majorRelId");
        request.setAttribute("majorRelId",majorRelId);
        String rel = request.getParameter("rel");
        request.setAttribute("rel",rel);
        return new ModelAndView("com/sddb/buss/web/riskmanage/selectManageRiskByMajorId");
    }

    //获取风险库列表，从中选取风险进入风险清单
    @RequestMapping(params = "selectRiskByPostId")
    public ModelAndView selectRiskByPostId(HttpServletRequest request) {
        String postRelId = request.getParameter("postRelId");
        request.setAttribute("postRelId",postRelId);
        return new ModelAndView("com/sddb/buss/web/riskmanage/selectManageRiskByPostId");
    }

    //我的管控清单
    @RequestMapping(params = "relRiskMyDatagrid")
    public void relRiskMyDatagrid(RiskManageEntity riskManage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(RiskManageEntity.class,dataGrid);
        if(null!=riskManage && null!=riskManage.getRisk() && null!=riskManage.getRisk().getAddress()){
            cq.createAlias("risk.address","risk_address");
            riskManage.getRisk().getAddress().setAddress(null);
        }
        if(null!=riskManage && null!=riskManage.getRisk() && null!=riskManage.getRisk().getPost()){
            cq.createAlias("risk.post","risk_post");
            riskManage.getRisk().getPost().setPostName(null);
        }
        if(null!=riskManage && null!=riskManage.getRisk()){
            if(StringUtil.isNotEmpty(riskManage.getRisk().getDutyManager())){
                riskManage.getRisk().setDutyManager("*"+riskManage.getRisk().getDutyManager()+"*");
            }
        }
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskManage, request.getParameterMap());

        try{

            String addressName = request.getParameter("risk.address.address");
            if(StringUtil.isNotEmpty(addressName)){
                cq.like("risk_address.address","%"+addressName+"%");
            }
            String postName = request.getParameter("risk.post.postName");
            if(StringUtil.isNotEmpty(postName)){
                cq.like("risk_post.postName","%"+postName+"%");
            }

            String addressId = request.getParameter("addressId");
            if(StringUtil.isNotEmpty(addressId)){
                cq.createAlias("risk.address","risk_address");
                cq.eq("risk_address.id",addressId);
            }

            String postId = request.getParameter("postId");
            if(StringUtil.isNotEmpty(postId)){
                cq.createAlias("risk.post","risk_post");
                cq.eq("risk_post.id",postId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.createAlias("risk","risk");
        //个人看到自己的
        TSUser user = ResourceUtil.getSessionUserName();
        cq.eq("myUserId",user.getId());
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskManageEntity> list = dataGrid.getResults();
            for (RiskManageEntity riskManageEntity : list) {
                RiskIdentificationEntity bean = riskManageEntity.getRisk();
                List<RiskFactortsRel> relList = bean.getRelList();
                if (relList == null) {
                    bean.setHazardFactortsNum("0");
                }
                bean.setHazardFactortsNum(relList.size() + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    //获取风险库列表，从中选取风险进入风险清单
    @RequestMapping(params = "selectRisk")
    public ModelAndView selectRisk(HttpServletRequest request) {
        request.setAttribute("year", DateUtils.getYear());
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        return new ModelAndView("com/sddb/buss/web/riskmanage/selectManageRisk");
    }



    //获取风险库列表，从中选取风险进入我的风险清单
    @RequestMapping(params = "selectMyRisk")
    public ModelAndView selectMyRisk(HttpServletRequest request) {
        request.setAttribute("year", DateUtils.getYear());
        String manageType = request.getParameter("manageType");
        request.setAttribute("manageType",manageType);
        return new ModelAndView("com/sddb/buss/web/riskmanage/selectManageMyRisk");
    }

    @RequestMapping(params = "riskDatagrid")
    public void riskDatagrid(RiskIdentificationEntity riskIdentification,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class,dataGrid);
        riskIdentification.setAddress(null);
        riskIdentification.setPost(null);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskIdentification, request.getParameterMap());

        try{
            String addressName = request.getParameter("address.address");
            if(StringUtil.isNotEmpty(addressName)) {
                cq.createAlias("address", "address");
                cq.like("address.address", "%" + addressName + "%");
            }
            String postName = request.getParameter("post.postName");
            if(StringUtil.isNotEmpty(postName)) {
                cq.createAlias("post", "post");
                cq.like("post.postName", "%" + postName + "%");
            }

            String addressId = request.getParameter("addressId");
            if(StringUtil.isNotEmpty(addressId)){
                cq.createAlias("address","address");
                cq.eq("address.id",addressId);
            }

            String postId = request.getParameter("postId");
            if(StringUtil.isNotEmpty(postId)){
                cq.createAlias("post","post");
                cq.eq("post.id",postId);
            }

            String manageType = request.getParameter("manageType");
            if(StringUtil.isNotEmpty(manageType)){
                String majorId = request.getParameter("majorId");
                String majorRelId = request.getParameter("majorRelId");
                if(StringUtil.isNotEmpty(majorId)){
                    cq.add(Restrictions.sqlRestriction(" this_.id not in (select risk_id from t_b_risk_manage where manage_type='" + manageType + "' and major_id = '"+majorId+"')"));
                }else if(StringUtil.isNotEmpty(majorRelId)){
                    RiskManageMajorRelEntity riskManageMajorRelEntity = systemService.getEntity(RiskManageMajorRelEntity.class,majorRelId);
                    cq.add(Restrictions.sqlRestriction(" this_.id in (select risk_id from t_b_risk_manage where manage_type='" + manageType + "' and major_id = '"+riskManageMajorRelEntity.getMajorId()+"' " +
                            "and risk_id not in (select risk_id from t_b_risk_manage where manage_type='" + manageType + "' and my_user_id = '"+ResourceUtil.getSessionUserName().getId()+"'  and major_id = '"+riskManageMajorRelEntity.getMajorId()+"'  ))"));
                }else{
                    cq.add(Restrictions.sqlRestriction(" this_.id not in (select risk_id from t_b_risk_manage where manage_type='" + manageType + "')"));
                }

                if("post".equals(manageType)){
                    cq.eq("identificationType","4");
                } else {
                    cq.notEq("identificationType","4");
                }
            }

            String modular = request.getParameter("modular");
            if(StringUtil.isNotEmpty(modular)){
                cq.add(Restrictions.sqlRestriction(" this_.id  in (SELECT risk_identification_id FROM t_b_risk_factors_rel WHERE hazard_factors_id in (SELECT hazard_id FROM t_b_hazard_module_rel WHERE modular_id = '"+modular+"'))"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.eq("status", Constants.HAZARDFACTORS_STATUS_REVIEW);
        cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskIdentificationEntity> list = dataGrid.getResults();
            for (RiskIdentificationEntity bean : list) {
                List<RiskFactortsRel> relList = bean.getRelList();
                if (relList == null) {
                    bean.setHazardFactortsNum("0");
                }
                bean.setHazardFactortsNum(relList.size() + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }


    @RequestMapping(params = "riskPostDatagrid")
    public void riskPostDatagrid(RiskIdentificationPostEntity riskIdentificationPostEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationPostEntity.class,dataGrid);
        try{
            cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
            String postRelId = request.getParameter("postRelId");
            if(StringUtil.isNotEmpty(postRelId)){
                RiskManagePostRelEntity riskManagePostRelEntity = systemService.getEntity(RiskManagePostRelEntity.class,postRelId);
                cq.eq("post",riskManagePostRelEntity.getPost());
            }
            String riskType = request.getParameter("riskType");
            if(StringUtil.isNotEmpty(riskType)){
                cq.eq("riskType",riskType);
            }
            String riskLevel = request.getParameter("riskLevel");
            if(StringUtil.isNotEmpty(riskLevel)){
                cq.eq("riskLevel",riskLevel);
            }
            cq.add(Restrictions.sqlRestriction(" this_.id not in (select risk_id from t_b_risk_manage_post where  my_user_id = '"+ResourceUtil.getSessionUserName().getId()+"')"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskIdentificationPostEntity> list = dataGrid.getResults();
            for (RiskIdentificationPostEntity bean : list) {
                List<RiskFactortsPostRel> relList = bean.getPostRelList();
                if (relList == null) {
                    bean.setHazardFactortsPostNum("0");
                }
                bean.setHazardFactortsPostNum(relList.size() + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "riskMyDatagrid")
    public void riskMyDatagrid(RiskIdentificationEntity riskIdentification,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {

        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class,dataGrid);
        riskIdentification.setAddress(null);
        riskIdentification.setPost(null);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskIdentification, request.getParameterMap());

        try{
            String addressName = request.getParameter("address.address");
            if(StringUtil.isNotEmpty(addressName)) {
                cq.createAlias("address", "address");
                cq.like("address.address", "%" + addressName + "%");
            }
            String postName = request.getParameter("post.postName");
            if(StringUtil.isNotEmpty(postName)) {
                cq.createAlias("post", "post");
                cq.like("post.postName", "%" + postName + "%");
            }

            String addressId = request.getParameter("addressId");
            if(StringUtil.isNotEmpty(addressId)){
                cq.createAlias("address","address");
                cq.eq("address.id",addressId);
            }

            String postId = request.getParameter("postId");
            if(StringUtil.isNotEmpty(postId)){
                cq.createAlias("post","post");
                cq.eq("post.id",postId);
            }

            String manageType = request.getParameter("manageType");
            if(StringUtil.isNotEmpty(manageType)){
                cq.add(Restrictions.sqlRestriction(" this_.id  in (select risk_id from t_b_risk_manage where manage_type='" + manageType + "' and " +
                        "risk_id not in (select risk_id from t_b_risk_manage where manage_type='" + manageType + "' and my_user_id = '"+ResourceUtil.getSessionUserName().getId()+"'))"));
                if("post".equals(manageType)){
                    cq.eq("identificationType","4");
                } else {
                    cq.notEq("identificationType","4");
                }
            }
            String modular = request.getParameter("modular");
            if(StringUtil.isNotEmpty(modular)){
                cq.add(Restrictions.sqlRestriction(" this_.id  in (SELECT risk_identification_id FROM t_b_risk_factors_rel WHERE hazard_factors_id in (SELECT hazard_id FROM t_b_hazard_module_rel WHERE modular_id = '"+modular+"'))"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.eq("status", Constants.HAZARDFACTORS_STATUS_REVIEW);
        cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskIdentificationEntity> list = dataGrid.getResults();
            for (RiskIdentificationEntity bean : list) {
                List<RiskFactortsRel> relList = bean.getRelList();
                if (relList == null) {
                    bean.setHazardFactortsNum("0");
                }
                bean.setHazardFactortsNum(relList.size() + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "saveRelRisk")
    @ResponseBody
    public AjaxJson saveRelRisk(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "风险清单添加风险成功";
        String ids = request.getParameter("ids");//选择的危险源
        String manageType = request.getParameter("manageType");
        String majorId = request.getParameter("majorId");
        try{
            if(org.apache.commons.lang.StringUtils.isNotBlank(ids)){
                String[] idArray = ids.split(",");
                RiskManageEntity riskManageEntity = null;
                for(String id : idArray){
                    riskManageEntity = new RiskManageEntity();
                    RiskIdentificationEntity t = this.systemService.getEntity(RiskIdentificationEntity.class,id);
                    riskManageEntity.setRisk(t);
                    riskManageEntity.setManageType(manageType);
                    if(StringUtil.isNotEmpty(majorId)){
                        riskManageEntity.setMajorId(majorId);
                    }
                    this.systemService.save(riskManageEntity);
                    systemService.addLog("风险清单添加风险\""+ riskManageEntity.getId()+"\"成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "风险清单添加风险失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "saveRelMyRisk")
    @ResponseBody
    public AjaxJson saveRelMyRisk(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "我的风险清单添加风险成功";
        String ids = request.getParameter("ids");
        String manageType = request.getParameter("manageType");
        String majorRelId = request.getParameter("majorRelId");
        RiskManageMajorRelEntity riskManageMajorRelEntity = new RiskManageMajorRelEntity();
        if(StringUtil.isNotEmpty(majorRelId)){
             riskManageMajorRelEntity = this.systemService.getEntity(RiskManageMajorRelEntity.class,majorRelId);
        }
        TSUser user = ResourceUtil.getSessionUserName();
        try{
            if(StringUtil.isNotEmpty(ids)){
                String[] idArray = ids.split(",");
                for(String id : idArray){
                    RiskManageEntity riskManageEntity = new RiskManageEntity();
                    RiskIdentificationEntity t = this.systemService.getEntity(RiskIdentificationEntity.class,id);
                    riskManageEntity.setRisk(t);
                    riskManageEntity.setManageType(manageType);
                    riskManageEntity.setMyUserId(user.getId());
                    if(StringUtil.isNotEmpty(riskManageMajorRelEntity.getMajorId())){
                        riskManageEntity.setMajorId(riskManageMajorRelEntity.getMajorId());
                    }
                    this.systemService.save(riskManageEntity);
                    systemService.addLog("我的风险清单添加风险\""+ riskManageEntity.getId()+"\"成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "我的风险清单添加风险失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "saveRelMyPostRisk")
    @ResponseBody
    public AjaxJson saveRelMyPostRisk(HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "我的风险清单添加风险成功";
        String ids = request.getParameter("ids");
        TSUser user = ResourceUtil.getSessionUserName();
        try{
            if(StringUtil.isNotEmpty(ids)){
                String[] idArray = ids.split(",");
                for(String id : idArray){
                    RiskManagePostEntity riskManagePostEntity = new RiskManagePostEntity();
                    RiskIdentificationPostEntity t = this.systemService.getEntity(RiskIdentificationPostEntity.class,id);
                    riskManagePostEntity.setRisk(t);
                    riskManagePostEntity.setMyUserId(user.getId());
                    this.systemService.save(riskManagePostEntity);
                    systemService.addLog("我的风险清单添加风险\""+ riskManagePostEntity.getId()+"\"成功",Globals.Log_Leavel_INFO,Globals.Log_Type_INSERT);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "我的风险清单添加风险失败";
            systemService.addLog(message+"："+e.toString(),Globals.Log_Leavel_ERROR,Globals.Log_Type_INSERT);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }




    /**
     * 批量删除
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String message = "删除成功";
        String riskManageAll = request.getParameter("riskManageAll");
        String delSql = "";
        try{
            if(StringUtil.isNotEmpty(ids)) {
                String idArr[] = ids.split(",");
                CriteriaQuery cq = new CriteriaQuery(RiskManageEntity.class);
                cq.in("id", idArr);
                cq.add();
                List<RiskManageEntity> riskManageEntityList = this.systemService.getListByCriteriaQuery(cq, false);
                if (null != riskManageEntityList && riskManageEntityList.size() > 0) {
                    if (StringUtil.isNotEmpty(riskManageAll)) {
                        if (riskManageAll.equals("true")) {//综合管控删除
                            for (RiskManageEntity riskManageEntity : riskManageEntityList) {
                                delSql = "delete from t_b_risk_manage where manage_type='" + riskManageEntity.getManageType() + "'  and risk_id = '" + riskManageEntity.getRisk().getId() + "'";
                                this.systemService.executeSql(delSql); //删除我的综合管控
                            }
                        }else{
                            String majorId = request.getParameter("majorId");//剩余4个管控
                            if (StringUtil.isNotEmpty(majorId)) {
                                for (RiskManageEntity riskManageEntity : riskManageEntityList) {
                                    delSql = "delete from t_b_risk_manage where manage_type='" + riskManageEntity.getManageType() + "'  and major_id = '" + majorId + "' and risk_id = '" + riskManageEntity.getRisk().getId() + "'";
                                    this.systemService.executeSql(delSql);//删除我的管控
                                }
                            }else{
                                this.systemService.deleteAllEntitie(riskManageEntityList);
                            }
                        }
                    } else {
                        this.systemService.deleteAllEntitie(riskManageEntityList);
                    }
                }
            }
//            String idArr[] = ids.split(",");
//            if(StringUtil.isNotEmpty(ids) && StringUtil.isNotEmpty(manageType)){
//                ids = "'" + ids.replace(",","','") + "'";
//                String delSql = "delete from t_b_risk_manage where manage_type='" + manageType + "' and risk_id in (" + ids + ")";
//                this.systemService.executeSql(delSql);
//            }
        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            systemService.addLog(message+"："+e.toString(), Globals.Log_Leavel_ERROR, Globals.Log_Type_DEL);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "doBatchDelPost")
    @ResponseBody
    public AjaxJson doBatchDelPost(String ids, HttpServletRequest request){
        AjaxJson j = new AjaxJson();
        String message = "删除成功";
        String delSql = "";
        try{
            if(StringUtil.isNotEmpty(ids)) {
                String idArr[] = ids.split(",");
                CriteriaQuery cq = new CriteriaQuery(RiskManagePostEntity.class);
                cq.in("id", idArr);
                cq.add();
                List<RiskManagePostEntity> riskManagePostEntityList = this.systemService.getListByCriteriaQuery(cq, false);
                if (null != riskManagePostEntityList && riskManagePostEntityList.size() > 0) {
                    this.systemService.deleteAllEntitie(riskManagePostEntityList);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
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
    public String exportXls(RiskManageEntity riskManage, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {

        CriteriaQuery cq = new CriteriaQuery(RiskManageEntity.class, dataGrid);
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskManage, request.getParameterMap());

        List<RiskManageEntity> riskManageEntityList = new ArrayList<>();
        String idTemp = request.getParameter("ids");
        if(StringUtils.isNotBlank(idTemp)&&idTemp!=null){
            List<String> idList = new ArrayList<>();
            for(String id : idTemp.split(",")){
                idList.add(id);
            }

            cq.in("id",idList.toArray());
            cq.add();
        }else{
            if(null!=riskManage && null!=riskManage.getRisk() && null!=riskManage.getRisk().getAddress()){
                cq.createAlias("risk.address","risk_address");
                riskManage.getRisk().getAddress().setAddress(null);
            }
            if(null!=riskManage && null!=riskManage.getRisk() && null!=riskManage.getRisk().getPost()){
                cq.createAlias("risk.post","risk_post");
                riskManage.getRisk().getPost().setPostName(null);
            }
            if(null!=riskManage && null!=riskManage.getRisk()){
                if(StringUtil.isNotEmpty(riskManage.getRisk().getDutyManager())){
                    riskManage.getRisk().setDutyManager("*"+riskManage.getRisk().getDutyManager()+"*");
                }
            }
            org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskManage, request.getParameterMap());
            try{
                String addressName = request.getParameter("risk.address.address");
                if(StringUtil.isNotEmpty(addressName)){
                    cq.like("risk_address.address","%"+addressName+"%");
                }
                String postName = request.getParameter("risk.post.postName");
                if(StringUtil.isNotEmpty(postName)){
                    cq.like("risk_post.postName","%"+postName+"%");
                }

                String addressId = request.getParameter("addressId");
                if(StringUtil.isNotEmpty(addressId)){
                    cq.createAlias("risk.address","risk_address");
                    cq.eq("risk_address.id",addressId);
                }

                String postId = request.getParameter("postId");
                if(StringUtil.isNotEmpty(postId)){
                    cq.createAlias("risk.post","risk_post");
                    cq.eq("risk_post.id",postId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            cq.createAlias("risk","risk");
            //我的导出自己的清单
            String myList = request.getParameter("myList");
            if(StringUtil.isNotEmpty(myList)){
                if(myList.equals("true")){
                    TSUser user = ResourceUtil.getSessionUserName();
                    cq.eq("myUserId",user.getId());
                }
            }
            cq.add();
        }
        riskManageEntityList = this.systemService.getListByCriteriaQuery(cq,false);
        List<RiskManageEntity> riskManageEntityListTemp = new ArrayList<>();
        if (riskManageEntityList != null && riskManageEntityList.size() > 0) {
            for (RiskManageEntity entity : riskManageEntityList) {
                entity.getRisk().setRiskTypeTemp(DicUtil.getTypeNameByCode("risk_type",entity.getRisk().getRiskType()));
                entity.getRisk().setRiskLevelTemp(DicUtil.getTypeNameByCode("factors_level",entity.getRisk().getRiskLevel()));
                entity.getRisk().setManageLevelTemp(DicUtil.getTypeNameByCode("identifi_mange_level",entity.getRisk().getManageLevel()));
                entity.getRisk().setIdentificationTypeTemp(DicUtil.getTypeNameByCode("identifi_from",entity.getRisk().getIdentificationType()));
            }
            List<RiskManageEntity> list = riskManageEntityList;
            for (RiskManageEntity riskManageEntity : list) {
                RiskIdentificationEntity bean = riskManageEntity.getRisk();
                List<RiskFactortsRel> relList = bean.getRelList();
                if (relList != null&&relList.size()>0) {
                    for (RiskFactortsRel riskFactorts : relList) {
                        RiskManageEntity temp = new RiskManageEntity();
                        try {
                            MyBeanUtils.copyBeanNotNull2Bean(riskManageEntity, temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        temp.setHazardFactorsTemp(riskFactorts.getHazardFactorsEntity().getHazardFactors());
                        temp.setManageMeasureTemp(riskFactorts.getHfManageMeasure());
                        riskManageEntityListTemp.add(temp);
                    }
                }else{
                    riskManageEntityListTemp.add(riskManageEntity);
                }
            }
        }
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        String manageType = request.getParameter("manageType");
        if(StringUtil.isNotEmpty(manageType)){
            if(manageType.equals("post")){
                templateExportParams.setTemplateUrl("export/template/exportTemp_riskManageEntityPostList.xls");
            }else{
                templateExportParams.setTemplateUrl("export/template/exportTemp_riskManageEntityList.xls");
            }
        }else{
            templateExportParams.setTemplateUrl("export/template/exportTemp_riskManageEntityList.xls");
        }
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String,List<RiskManageEntity>> map = new HashMap<String,List<RiskManageEntity>>();
        map.put("list", riskManageEntityListTemp);
        modelMap.put(NormalExcelConstants.FILE_NAME,"管控清单列表");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsPost")
    public String exportXlsPost( HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(RiskManagePostEntity.class, dataGrid);
        try {
            String idTemp = request.getParameter("ids");
            if(StringUtils.isNotBlank(idTemp)&&idTemp!=null) {
                List<String> idList = new ArrayList<>();
                for (String id : idTemp.split(",")) {
                    idList.add(id);
                }
                cq.in("id", idList.toArray());
            }else{
                String riskType = request.getParameter("risk.riskType");
                if(StringUtil.isNotEmpty(riskType)){
                    cq.eq("risk.riskType",riskType);
                }
                String riskLevel = request.getParameter("risk.riskLevel");
                if(StringUtil.isNotEmpty(riskLevel)){
                    cq.eq("risk.riskLevel",riskLevel);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.createAlias("risk","risk");
        cq.add();

        List<RiskManagePostEntity> riskManagePostEntityList = this.systemService.getListByCriteriaQuery(cq,false);
        List<RiskManagePostEntity> riskManagePostEntityListTemp = new ArrayList<>();
        if (riskManagePostEntityList != null && riskManagePostEntityList.size() > 0) {
            for (RiskManagePostEntity entity : riskManagePostEntityList) {
                entity.getRisk().setRiskTypeTemp(DicUtil.getTypeNameByCode("risk_type",entity.getRisk().getRiskType()));
                entity.getRisk().setRiskLevelTemp(DicUtil.getTypeNameByCode("factors_level",entity.getRisk().getRiskLevel()));
                List<RiskFactortsPostRel> relList = entity.getRisk().getPostRelList();
                if (relList != null&&relList.size()>0) {
                    for (RiskFactortsPostRel riskFactortsPost : relList) {
                        RiskManagePostEntity temp = new RiskManagePostEntity();
                        try {
                            MyBeanUtils.copyBeanNotNull2Bean(entity, temp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        temp.setHazardFactorsTemp(riskFactortsPost.getHazardFactorsPostEntity().getHazardFactors());
                        riskManagePostEntityListTemp.add(temp);
                    }
                }else{
                    riskManagePostEntityListTemp.add(entity);
                }
            }
        }
        TemplateExportParams templateExportParams = new TemplateExportParams();
        templateExportParams.setSheetNum(0);
        templateExportParams.setTemplateUrl("export/template/exportTemp_riskManageMyPostList.xls");
        modelMap.put(TemplateExcelConstants.PARAMS,templateExportParams);
        Map<String,List<RiskManagePostEntity>> map = new HashMap<String,List<RiskManagePostEntity>>();
        map.put("list", riskManagePostEntityListTemp);
        modelMap.put(NormalExcelConstants.FILE_NAME,"管控清单");
        modelMap.put(TemplateExcelConstants.MAP_DATA,map);
        return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
    }



    @RequestMapping(params = "myManageList")
    public ModelAndView myManageList(HttpServletRequest request, HttpServletResponse response) {
        TSUser user = ResourceUtil.getSessionUserName();
        String sql = "SELECT id FROM t_b_my_manage WHERE my_user_id = '"+user.getId()+"'";
        List<String>  list = systemService.findListbySql(sql);
        if(list==null||list.size()==0){
            List<TSType> types = ResourceUtil.allTypes.get("risk_type");
            for (TSType tsType:types){
                MyManageEntity myManageEntity = new MyManageEntity();
                myManageEntity.setIsDel("1");
                myManageEntity.setMyUserId(user.getId());
                myManageEntity.setRiskType(tsType.getTypecode());
                systemService.save(myManageEntity);
            }
        }
        String manageLevelSql =  "select manage_level from t_b_my_manage where my_user_id = '"+user.getId()+"' and manage_level!='' and manage_level is not null";
        List<String>  manageLevelList = systemService.findListbySql(manageLevelSql);
        if(manageLevelList!=null&&manageLevelList.size()>0){
            request.setAttribute("manangeLevel",manageLevelList.get(0));
        }
        String manageNumSql =  "select manage_num from t_b_my_manage where my_user_id = '"+user.getId()+"' and manage_num!='' and manage_num is not null";
        List<String>  manageNumList = systemService.findListbySql(manageNumSql);
        if(manageNumList!=null&&manageNumList.size()>0){
            request.setAttribute("manageNum",manageNumList.get(0));
        }
        String majorSql =  "select major from t_b_my_manage where my_user_id = '"+user.getId()+"' and major!='' and major is not null";
        List<String>  majorList = systemService.findListbySql(majorSql);
        if(majorList!=null&&majorList.size()>0){
            request.setAttribute("major",majorList.get(0));
        }
        String wenhe = ResourceUtil.getConfigByName("wenhe");
        request.setAttribute("wenhe",wenhe);
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskManageMyManageList");
    }

    @RequestMapping(params = "datagridMyManage")
    public void datagridMyPost(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
       TSUser user = ResourceUtil.getSessionUserName();
        CriteriaQuery cq = new CriteriaQuery(MyManageEntity.class,dataGrid);
        try{
            cq.eq("myUserId",user.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "delete")
    @ResponseBody
    public AjaxJson delete(MyManageEntity myManageEntity, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        try {
            String delete_flag = "0";
            String msg = "风险类型已激活";
            if (delete_flag.equals(ResourceUtil.getParameter("deleteFlag"))) {
                delete_flag = "1";
                msg = "风险类型已失效";
            }
            systemService.executeSql("update t_b_my_manage SET is_del ="+delete_flag+" where id = '"+ResourceUtil.getParameter("id")+"'");
            j.setMsg(msg);
        } catch (Exception e) {
            j.setMsg("删除逻辑参数异常,请重试.");
        }
        return j;
    }

    @RequestMapping(params = "all")
    @ResponseBody
    public String all(MyManageEntity myManageEntity, HttpServletRequest req) {
        String  d = "0";
        systemService.executeSql("update t_b_my_manage SET is_del ='0' where my_user_id = '"+ResourceUtil.getSessionUserName().getId()+"'");
        return d;
    }

    @RequestMapping(params = "setManageLevel")
    @ResponseBody
    public AjaxJson setManageLevel(HttpServletRequest request)
            throws Exception {
        String message = null;
        AjaxJson retJson = new AjaxJson();
        message = "设置管控层级成功";
        String manageLevel = request.getParameter("manageLevel");
        TSUser tsUser = ResourceUtil.getSessionUserName();
        String sql = "update t_b_my_manage SET manage_level = '"+manageLevel+"' WHERE my_user_id = '"+tsUser.getId()+"'";
        systemService.executeSql(sql);
        retJson.setMsg(message);
        retJson.setSuccess(true);
        return retJson;
    }

    /**
     * 是否可以激活
     * @return
     */
    @RequestMapping(params = "isDel")
    @ResponseBody
    public String isDel(){
        TSUser user = ResourceUtil.getSessionUserName();
        String isDel = "0";
        String sql = "SELECT * from t_b_my_manage WHERE manage_level is not null and manage_level != '' and major is not null and major != '' and my_user_id = '"+user.getId()+"'";
        List<String> list = systemService.findListbySql(sql);
        if(list!=null&&list.size()>0){
            isDel = "1";
        }
        return isDel;
    }

    @RequestMapping(params = "goRiskDetail")
    public ModelAndView goRiskDetail(  HttpServletRequest req) {
        String id = req.getParameter("myManageId");
        MyManageEntity myManageEntity = systemService.getEntity(MyManageEntity.class,id);
        if(myManageEntity!=null){
            req.setAttribute("riskType",myManageEntity.getRiskType());
            req.setAttribute("manageLevel",myManageEntity.getManageLevel());
        }
        return new ModelAndView("com/sddb/buss/web/riskmanage/riskList");
    }

    @RequestMapping(params = "queryListByRiskTypeDatagrid")
    public void queryListByRiskTypeDatagrid( HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String riskType = request.getParameter("riskType");
        String manageLevel = request.getParameter("manageLevel");
        String addressId  =request.getParameter("addressId");
        String riskLevel  =request.getParameter("riskLevel");
        CriteriaQuery cq = new CriteriaQuery(RiskIdentificationEntity.class, dataGrid);
        try {
            if(StringUtil.isNotEmpty(riskType)){
                cq.eq("riskType",riskType);
            }
            if(StringUtil.isNotEmpty(riskLevel)){
                cq.eq("riskLevel",riskLevel);
            }
            if(StringUtil.isNotEmpty(addressId)){
                cq.createAlias("address","address");
                cq.eq("address.id",addressId);
            }
            if(StringUtil.isNotEmpty(manageLevel)){
                if(manageLevel.equals("4")){
                    cq.in("manageLevel",new String[]{"1","2","3","4"});
                }else if(manageLevel.equals("3")){
                    cq.in("manageLevel",new String[]{"1","2","3"});
                }else if(manageLevel.equals("2")){
                    cq.in("manageLevel",new String[]{"1","2"});
                }else{
                    cq.in("manageLevel",new String[]{"1"});
                }

            }
            cq.eq("status",Constants.RISK_IDENTIFI_STATUS_REVIEW);
            cq.eq("isDel",Constants.RISK_IS_DEL_FALSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cq.add();
        this.systemService.getDataGridReturn(cq, true);
        if (dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()) {
            List<RiskIdentificationEntity> list = dataGrid.getResults();
            for (RiskIdentificationEntity bean : list) {
                List<RiskFactortsRel> relList = bean.getRelList();
                if (relList == null) {
                    bean.setHazardFactortsNum("0");
                }
                bean.setHazardFactortsNum(relList.size() + "");
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "setManageNum")
    @ResponseBody
    public AjaxJson setManageNum(HttpServletRequest request)
            throws Exception {
        String message = null;
        AjaxJson retJson = new AjaxJson();
        message = "设置管控条数成功";
        String manageNum = request.getParameter("manageNum");
        TSUser tsUser = ResourceUtil.getSessionUserName();
        String sql = "update t_b_my_manage SET manage_num = '"+manageNum+"' WHERE my_user_id = '"+tsUser.getId()+"'";
        systemService.executeSql(sql);
        retJson.setMsg(message);
        retJson.setSuccess(true);
        return retJson;
    }

    @RequestMapping(params = "setMajor")
    @ResponseBody
    public AjaxJson setMajor(HttpServletRequest request)
            throws Exception {
        String message = null;
        AjaxJson retJson = new AjaxJson();
        message = "设置专业成功";
        String major = request.getParameter("major");
        TSUser tsUser = ResourceUtil.getSessionUserName();
        String sql = "update t_b_my_manage SET major = '"+major+"' WHERE my_user_id = '"+tsUser.getId()+"'";
        systemService.executeSql(sql);
        retJson.setMsg(message);
        retJson.setSuccess(true);
        return retJson;
    }


    @RequestMapping(params = "getManageType")
    @ResponseBody
    public String getManageType(String id){
        RiskManageTaskAllEntity riskManageTaskAllEntity = systemService.getEntity(RiskManageTaskAllEntity.class,id);
        String manageType = riskManageTaskAllEntity.getManageType();
        return manageType;
    }


}



