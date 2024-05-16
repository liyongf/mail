package com.sdzk.buss.web.quartz;


import com.sddb.buss.riskmanage.entity.RiskManageTaskAllEntity;
import com.sddb.buss.riskmanage.entity.RiskManageTaskAllManageEntity;
import com.sdzk.buss.api.service.WeChartGetToken;
import com.sdzk.buss.api.utils.WebChatUtil;
import com.sdzk.buss.web.common.Constants;
import com.sdzk.buss.web.common.utils.SMSSenderUtil;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerExamEntity;
import com.sdzk.buss.web.hiddendanger.entity.TBHiddenDangerHandleEntity;
import com.sdzk.buss.web.hiddendanger.service.TBHiddenDangerHandleLogServiceI;
import com.sdzk.buss.web.quartz.service.QrtzManagerServiceI;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.*;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 17-9-19.
 */
@Service
public class QuartzJobTask implements Job {

    @Autowired
    private SystemService systemService;

    @Autowired
    private WeChartGetToken weChartGetToken;
    @Autowired
    private QrtzManagerServiceI qrtzManagerServiceI;
    @Autowired
    private TBHiddenDangerHandleLogServiceI tBHiddenDangerHandleLogService;
    private static final Logger logger = Logger.getLogger(QuartzJobTask.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Scheduler scheduler = context.getScheduler();
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        //不做修改了 其实是总任务
        String taskAllManageId = (String) dataMap.get("hiddenDangerId");
        String sql = "update t_b_risk_manage_task_all SET status = '1' WHERE task_all_manage_id = '"+taskAllManageId+"'";
        systemService.executeSql(sql);
        qrtzManagerServiceI.removeJob(scheduler, taskAllManageId);
    }



}
