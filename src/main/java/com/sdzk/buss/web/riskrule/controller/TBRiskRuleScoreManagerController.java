package com.sdzk.buss.web.riskrule.controller;


import com.sdzk.buss.web.riskrule.entity.TBRiskRuleScoreManagerEntity;
import org.apache.commons.collections.map.HashedMap;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Scope("prototype")
@Controller
@RequestMapping("/riskRuleScoreManagerController")
public class TBRiskRuleScoreManagerController {

    @Autowired
    private SystemService systemService;

    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/riskrule/ruleScoreList");
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBRiskRuleScoreManagerEntity tbriskRuleScoreManagerEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tbriskRuleScoreManagerEntity.getId())) {
            tbriskRuleScoreManagerEntity = systemService.getEntity(TBRiskRuleScoreManagerEntity.class, tbriskRuleScoreManagerEntity.getId());
            req.setAttribute("tbriskRuleScoreManagerEntity", tbriskRuleScoreManagerEntity);
        }
        return new ModelAndView("com/sdzk/buss/web/riskrule/ruleScore-add");
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBRiskRuleScoreManagerEntity tbriskRuleScoreManagerEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "息添加成功";
        try{
            String riskType = tbriskRuleScoreManagerEntity.getRiskType();
            CriteriaQuery cq = new CriteriaQuery(TBRiskRuleScoreManagerEntity.class);
            try{
                cq.eq("riskType",riskType);
            }catch(Exception e){
                e.printStackTrace();
            }
            cq.add();
            List<TBRiskRuleScoreManagerEntity> list = systemService.getListByCriteriaQuery(cq,false);
            if(list != null && !list.isEmpty()){
                message = "该等级已经配置,请重新选择";
                j.setSuccess(false);
            }else {
                systemService.save(tbriskRuleScoreManagerEntity);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "信息添加失败";
            j.setSuccess(false);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TBRiskRuleScoreManagerEntity tbriskRuleScoreManagerEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tbriskRuleScoreManagerEntity.getId())) {
            tbriskRuleScoreManagerEntity = systemService.getEntity(TBRiskRuleScoreManagerEntity.class, tbriskRuleScoreManagerEntity.getId());
            req.setAttribute("tbriskRuleScoreManagerEntity", tbriskRuleScoreManagerEntity);
        }
        return new ModelAndView("com/sdzk/buss/web/riskrule/ruleScore-update");
    }


    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBRiskRuleScoreManagerEntity tbriskRuleScoreManagerEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "更新成功";
        TBRiskRuleScoreManagerEntity t = systemService.get(TBRiskRuleScoreManagerEntity.class, tbriskRuleScoreManagerEntity.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tbriskRuleScoreManagerEntity, t);
            systemService.saveOrUpdate(t);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TBRiskRuleScoreManagerEntity tbriskRuleScoreManagerEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        tbriskRuleScoreManagerEntity = systemService.getEntity(TBRiskRuleScoreManagerEntity.class, tbriskRuleScoreManagerEntity.getId());
        message = "删除成功";
        try{
            systemService.delete(tbriskRuleScoreManagerEntity);
        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids,HttpServletRequest request, HttpServletResponse response){
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "删除成功";
        try{
            for(String id:ids.split(",")){
                TBRiskRuleScoreManagerEntity bean = systemService.getEntity(TBRiskRuleScoreManagerEntity.class, id);
                systemService.delete(bean);
            }
        }catch(Exception e){
            e.printStackTrace();
            message = "删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(TBRiskRuleScoreManagerEntity tbriskRuleScoreManagerEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBRiskRuleScoreManagerEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tbriskRuleScoreManagerEntity, request.getParameterMap());
        Map<String, Object> orderMap = new HashedMap();
        orderMap.put("riskType",SortDirection.asc);
        cq.setOrder(orderMap);
        cq.add();
        systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "goDetail")
    public ModelAndView goDetail(TBRiskRuleScoreManagerEntity tbriskRuleScoreManagerEntity,   HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tbriskRuleScoreManagerEntity.getId())) {
            tbriskRuleScoreManagerEntity = systemService.getEntity( TBRiskRuleScoreManagerEntity.class, tbriskRuleScoreManagerEntity.getId());
            req.setAttribute("tbriskRuleScoreManagerEntity", tbriskRuleScoreManagerEntity);
        }
        return new ModelAndView("com/sdzk/buss/web/riskrule/ruleScore-detail");
    }
}
