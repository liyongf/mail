package com.sdzk.buss.web.common.task;

import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerHandleServiceI;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.sms.service.TSSmsServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 定时上报隐患数据
 */
@Service("hiddenDangerReportTask")
public class HiddenDangerReportTask {
	
	@Autowired
	private TBHiddenDangerHandleServiceI tbHiddenDangerHandleService;

	public void run() {
		long start = System.currentTimeMillis();
		long count = 0;
		org.jeecgframework.core.util.LogUtil.info("===================定时上报隐患数据开始===================");
		try {
			//TODO 获取需要上报的数据id
            List<String> idList = tbHiddenDangerHandleService.findListbySql("select id from t_b_hidden_danger_handle h where hidden_danger_id in (select id from t_b_hidden_danger_exam where (report_status='"+Constants.DANGER_SOURCE_REPORT_UNREPORT+"' or report_status is null  or update_date > report_date or h.update_date > report_date ))");

            if(idList.size()>1000){
                idList = idList.subList(0,1000);
            }
            if (idList != null && idList.size()>0) {
                count = 0;
                /**
                 * 步数
                 */
                int distance = 200;
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
                        tbHiddenDangerHandleService.hiddenDangerReport(ids.toString(),true);
                        count += 1;
                    }
                }
			}
		} catch (Exception e) {
			org.jeecgframework.core.util.LogUtil.error("定时上报隐患数据失败", e);
		}
		org.jeecgframework.core.util.LogUtil.info("===================定时上报隐患数据结束===================");
		long end = System.currentTimeMillis();
		long times = end - start;
		org.jeecgframework.core.util.LogUtil.info("定时上报隐患数据总耗时"+times+"毫秒,共同步"+count+"条数据.");
	}
}
