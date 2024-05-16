package com.sdzk.buss.web.system.controller;

import com.sdzk.buss.web.system.entity.TaskRemindEntity;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 17-3-28.
 */
@Scope("prototype")
@Controller
@RequestMapping("/taskRemindController")
public class TaskRemindController extends BaseController {

    @Autowired
    private SystemService systemService;
    private String message;

    /**
     * 查询提醒信息
     *
     * @return
     */
    @RequestMapping(params = "getRemind")
    @ResponseBody
    public AjaxJson getRemind(HttpServletRequest request) {
        TSUser user = ResourceUtil.getSessionUserName();
        //1.查询待整改数据
        CriteriaQuery cq = new CriteriaQuery(TaskRemindEntity.class);
        try{
            cq.eq("dutyUserId",user.getId());
            cq.eq("bussType","1");
            cq.eq("status","0");
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        //未提醒,待整改数据
        List<TaskRemindEntity> list_1 = systemService.getListByCriteriaQuery(cq,false);
        cq = new CriteriaQuery(TaskRemindEntity.class);
        try{
            cq.eq("dutyUserId",user.getId());
            cq.eq("bussType","1");
            cq.eq("status","1");
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        //已提醒,待整改数据
        List<TaskRemindEntity> list_2 = systemService.getListByCriteriaQuery(cq,false);


        //查询待复查数据
        cq = new CriteriaQuery(TaskRemindEntity.class);
        try{
            cq.eq("dutyUserId",user.getUserName());
            cq.eq("bussType","3");
            cq.eq("status","0");
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        //未提醒，待复查数据
        List<TaskRemindEntity> list2_1 = systemService.getListByCriteriaQuery(cq,false);
        cq = new CriteriaQuery(TaskRemindEntity.class);
        try{
            cq.eq("dutyUserId",user.getUserName());
            cq.eq("bussType","3");
            cq.eq("status","1");
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        //已提醒，待复查数据
        List<TaskRemindEntity> list2_2 = systemService.getListByCriteriaQuery(cq,false);

        //查询退回上报数据
        cq = new CriteriaQuery(TaskRemindEntity.class);
        try{
            cq.eq("dutyUserId",user.getId());
            cq.eq("bussType","5");
            cq.eq("status","0");
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        //未提醒，退回上报数据
        List<TaskRemindEntity> list4_1 = systemService.getListByCriteriaQuery(cq,false);
        cq = new CriteriaQuery(TaskRemindEntity.class);
        try{
            cq.eq("dutyUserId",user.getId());
            cq.eq("bussType","5");
            cq.eq("status","1");
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        //已提醒，待复查数据
        List<TaskRemindEntity> list4_2 = systemService.getListByCriteriaQuery(cq,false);


        //查询任务闭环数据
        cq = new CriteriaQuery(TaskRemindEntity.class);
        try{
            cq.eq("dutyUserId",user.getId());
            cq.eq("bussType","4");
            cq.eq("status","0");
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        //未提醒待复查数据
        List<TaskRemindEntity> list3_1 = systemService.getListByCriteriaQuery(cq,false);
        cq = new CriteriaQuery(TaskRemindEntity.class);
        try{
            cq.eq("dutyUserId",user.getId());
            cq.eq("bussType","4");
            cq.eq("status","1");
        }catch(Exception e){
            e.printStackTrace();
        }
        cq.add();
        //未提醒待复查数据
        List<TaskRemindEntity> list3_2 = systemService.getListByCriteriaQuery(cq,false);

        //组装返回数据
        AjaxJson j = new AjaxJson();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("list_1",list_1.size());
        map.put("list_2",list_2.size());
        map.put("list2_1",list2_1.size());
        map.put("list2_2",list2_2.size());
        map.put("list3_1",list3_1.size());
        map.put("list3_2",list3_2.size());
        map.put("list4_1",list4_1.size());
        map.put("list4_2",list4_2.size());
        //将未读数据改为已读
        for(TaskRemindEntity bean : list_1){
            bean.setStatus("1");
            systemService.saveOrUpdate(bean);
        }
        for(TaskRemindEntity bean : list2_1){
            bean.setStatus("1");
            systemService.saveOrUpdate(bean);
        }
        for(TaskRemindEntity bean : list3_1){
            bean.setStatus("1");
            systemService.saveOrUpdate(bean);
        }
        for(TaskRemindEntity bean : list4_1){
            bean.setStatus("1");
            systemService.saveOrUpdate(bean);
        }
        j.setAttributes(map);
        return j;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
