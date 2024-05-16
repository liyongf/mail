package com.sdzk.buss.web.common.task;

/**
 * Created by Administrator on 17-9-27.
 */

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.specialevaluation.service.SpecialEvaluationRPC;
import com.sdzk.buss.web.violations.service.TBThreeViolationsServiceI;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时上报三违数据
 */
@Service("threeViolationReportTask")
public class ThreeViolationReportTask {

    @Autowired
    private SystemService systemService;
    @Autowired
    private TBThreeViolationsServiceI tBThreeViolationsService;

    public void run() {
        long start = System.currentTimeMillis();
        long count = 0;
        org.jeecgframework.core.util.LogUtil.info("===================定时上报三违数据开始===================");
        try {
            //TODO
            List<String> idList = systemService.findListbySql("select id from t_b_three_violations where report_status='0' or report_status is null ");
            if (idList != null && idList.size()>0) {
                count = 0;
                /**
                 * 步数
                 */
                int distance = 3000;
                List<String> tempList;
                for(int i = idList.size();i>0;i=i-distance){
                    if(i>distance){
                        tempList = idList.subList(i-distance,i);
                    }else{
                        tempList = idList.subList(0,i);
                    }
                    if(!tempList.isEmpty() && tempList.size()>0){
                        StringBuffer ids = new StringBuffer();
                        for(String id:tempList){
                            if(ids.length()>0){
                                ids.append(",");
                            }
                            ids.append(id);
                        }
                        tBThreeViolationsService.reportViolation(ids.toString(),true);
                    }
                }
            }
        } catch (Exception e) {
            org.jeecgframework.core.util.LogUtil.error("定时上报三违数据失败", e);
        }
        org.jeecgframework.core.util.LogUtil.info("===================定时上报三违数据结束===================");
        long end = System.currentTimeMillis();
        long times = end - start;
        org.jeecgframework.core.util.LogUtil.info("总耗时"+times+"毫秒,共同步"+count+"条数据.");
    }
}
