package com.sdzk.buss.web.quartz.service.impl;

import com.sdzk.buss.web.quartz.service.QrtzManagerServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.quartz.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 17-9-19.
 */
@Service("qrtzManagerServiceImpl")
public class QrtzManagerServiceImpl extends CommonServiceImpl implements QrtzManagerServiceI {

    /**
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     *
     * @param jobName
     *            任务名
     * @param cls
     *            任务
     * @param time
     *            时间设置，参考quartz说明文档
     *
     * @Title: QuartzManager.java
     */
    public void addJob(Scheduler sched,String hiddenDangerId, String jobName, @SuppressWarnings("rawtypes") Class cls, String time) {
        try {
            JobDetail jobDetail = new JobDetail(jobName, jobName, cls);// 任务名，任务组，任务执行类
            jobDetail.getJobDataMap().put("hiddenDangerId",hiddenDangerId);
            // 触发器
            CronTrigger trigger = new CronTrigger(jobName, jobName);// 触发器名,触发器组
            trigger.setCronExpression(time);// 触发器时间设定
            sched.scheduleJob(jobDetail, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 添加一个定时任务
     * @param jobName
     *            任务名
     * @param jobGroupName
     *            任务组名
     * @param triggerName
     *            触发器名
     * @param triggerGroupName
     *            触发器组名
     * @param jobClass
     *            任务
     * @param time
     *            时间设置，参考quartz说明文档
     *
     * @Title: QuartzManager.java
     */
    public void addJob(Scheduler sche,String jobName, String jobGroupName, String triggerName, String triggerGroupName, @SuppressWarnings("rawtypes") Class jobClass, String time) {
        try {
            JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jobClass);// 任务名，任务组，任务执行类
            // 触发器
            CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);// 触发器名,触发器组
            trigger.setCronExpression(time);// 触发器时间设定
            sche.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
     * @param jobName
     * @param time
     *
     * @Title: QuartzManager.java
     */
    @SuppressWarnings("rawtypes")
    public void modifyJobTime(Scheduler sched,String hiddenDangerId, String jobName, String time) {
        try {
            CronTrigger trigger = (CronTrigger) sched.getTrigger(jobName, jobName);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                JobDetail jobDetail = sched.getJobDetail(jobName, jobName);
                Class objJobClass = jobDetail.getJobClass();
                removeJob(sched, jobName);
                addJob(sched,hiddenDangerId, jobName, objJobClass, time);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     * @param jobName
     *
     * @Title: QuartzManager.java
     */
    public void removeJob(Scheduler sched, String jobName) {
        try {
            sched.pauseTrigger(jobName, jobName);// 停止触发器
            sched.unscheduleJob(jobName, jobName);// 移除触发器
            sched.deleteJob(jobName, jobName);// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description: 移除一个任务
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     *
     * @Title: QuartzManager.java
     */
    public void removeJob(Scheduler sched, String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            sched.pauseTrigger(triggerName, triggerGroupName);// 停止触发器
            sched.unscheduleJob(triggerName, triggerGroupName);// 移除触发器
            sched.deleteJob(jobName, jobGroupName);// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:启动所有定时任务
     * @Title: QuartzManager.java
     */
    public void startJobs(Scheduler sched) {
        try {
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @Description:关闭所有定时任务
     * @Title: QuartzManager.java
     */
    public void shutdownJobs(Scheduler sched) {
        try {
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从数据库中找到已经存在的job，并重新开户调度
     */
    public  void resumeAll(Scheduler sched) {
        try {
            sched.resumeAll();
           // sched.start();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
