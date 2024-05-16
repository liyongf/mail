package com.sdzk.buss.web.riskrule.controller;

import com.sdzk.buss.web.riskrule.entity.TBRiskRuleManagerEntity;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.DicUtil;
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
@RequestMapping("/riskRuleController")
public class RiskRuleController {

    @Autowired
    private SystemService systemService;

    @RequestMapping(params = "list")
    public ModelAndView list(HttpServletRequest request) {
        return new ModelAndView("com/sdzk/buss/web/riskrule/riskRuleList");
    }

    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TBRiskRuleManagerEntity riskRuleManagerEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(riskRuleManagerEntity.getId())) {
            riskRuleManagerEntity = systemService.getEntity(TBRiskRuleManagerEntity.class, riskRuleManagerEntity.getId());
            req.setAttribute("riskRuleManagerEntity", riskRuleManagerEntity);
        }
        return new ModelAndView("com/sdzk/buss/web/riskrule/riskRule-add");
    }

    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TBRiskRuleManagerEntity riskRuleManagerEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "息添加成功";
        try{
            String riskType = riskRuleManagerEntity.getRiskType();
            CriteriaQuery cq = new CriteriaQuery(TBRiskRuleManagerEntity.class);
            try{
                cq.eq("riskType",riskType);
            }catch (Exception e){
                e.printStackTrace();
            }
            cq.add();
            List<TBRiskRuleManagerEntity> list = systemService.getListByCriteriaQuery(cq,false);
            if(list != null && !list.isEmpty()){
                message = "该等级已经配置,请重新选择";
                j.setSuccess(false);
            }else {
                systemService.save(riskRuleManagerEntity);
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
    public ModelAndView goUpdate(TBRiskRuleManagerEntity riskRuleManagerEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(riskRuleManagerEntity.getId())) {
            riskRuleManagerEntity = systemService.getEntity(TBRiskRuleManagerEntity.class, riskRuleManagerEntity.getId());
            req.setAttribute("riskRuleManagerEntity", riskRuleManagerEntity);
        }
        return new ModelAndView("com/sdzk/buss/web/riskrule/riskRule-update");
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TBRiskRuleManagerEntity riskRuleManagerEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        message = "更新成功";
        TBRiskRuleManagerEntity t = systemService.get(TBRiskRuleManagerEntity.class, riskRuleManagerEntity.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(riskRuleManagerEntity, t);
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
    public AjaxJson doDel(TBRiskRuleManagerEntity riskRuleManagerEntity, HttpServletRequest request) {
        String message = null;
        AjaxJson j = new AjaxJson();
        riskRuleManagerEntity = systemService.getEntity(TBRiskRuleManagerEntity.class, riskRuleManagerEntity.getId());
        message = "删除成功";
        try{
            systemService.delete(riskRuleManagerEntity);
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
                TBRiskRuleManagerEntity bean = systemService.getEntity(TBRiskRuleManagerEntity.class, id);
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

    @RequestMapping(params = "goDetail")
    public ModelAndView goDetail(TBRiskRuleManagerEntity riskRuleManagerEntity,   HttpServletRequest req) {
        if (StringUtil.isNotEmpty(riskRuleManagerEntity.getId())) {
            riskRuleManagerEntity = systemService.getEntity( TBRiskRuleManagerEntity.class, riskRuleManagerEntity.getId());
            req.setAttribute("riskRuleManagerEntity", riskRuleManagerEntity);
        }
        return new ModelAndView("com/sdzk/buss/web/riskrule/riskRule-detail");
    }

    @RequestMapping(params = "datagrid")
    public void datagrid(TBRiskRuleManagerEntity riskRuleManagerEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TBRiskRuleManagerEntity.class, dataGrid);
        //查询条件组装器
        org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, riskRuleManagerEntity, request.getParameterMap());
        try{
            //自定义追加查询条件
            Map<String, Object> orderMap = new HashedMap();
            orderMap.put("riskType",SortDirection.asc);
            cq.setOrder(orderMap);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        systemService.getDataGridReturn(cq, true);
        if(dataGrid != null && dataGrid.getResults() != null && !dataGrid.getResults().isEmpty()){
            List<TBRiskRuleManagerEntity> list = dataGrid.getResults();
            for(TBRiskRuleManagerEntity bean : list){
                String riskType = bean.getRiskType();
                String between = bean.getScoreBetween();
                String end = bean.getScoreEnd();

                StringBuffer sb = new StringBuffer();
                String riskLevelName = DicUtil.getTypeNameByCode("riskLevel",riskType);
                if(StringUtils.isNotBlank(between)){
                    sb.append(between).append("≤").append(riskLevelName);
                }
                if(StringUtils.isNotBlank(end)){
                    if(StringUtils.isNotBlank(sb.toString())) {
                        sb.append("＜").append(end);
                    }else{
                        sb.append(riskLevelName).append("＜").append(end);
                    }
                }
                bean.setScoreTemp(sb.toString());
            }
        }
        TagUtil.datagrid(response, dataGrid);
    }
}
