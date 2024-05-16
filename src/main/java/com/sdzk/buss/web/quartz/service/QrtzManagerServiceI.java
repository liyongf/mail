package com.sdzk.buss.web.quartz.service;

import org.quartz.Scheduler;

/**
 * Created by Administrator on 17-9-20.
 */
public interface QrtzManagerServiceI {
    /**
     * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
     * @param jobName
     *            任务名
     * @param cls
     *            任务
     * @param time
     *            时间设置，参考quartz说明文档
     *
     * @Title: QuartzManager.java
     */
    void addJob(Scheduler sche, String hiddenDangerId, String jobName, @SuppressWarnings("rawtypes") Class cls, String time) ;

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
    void addJob(Scheduler sche, String jobName, String jobGroupName, String triggerName, String triggerGroupName, @SuppressWarnings("rawtypes") Class jobClass, String time) ;


    /**
     * @Description: 修改一个任务的触发时间
     * @param triggerName
     * @param triggerGroupName
     * @param time
     *
     * @Title: QuartzManager.java
     */
    void modifyJobTime(Scheduler sche, String triggerName, String triggerGroupName, String time) ;

    /**
     * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
     * @param jobName
     *
     * @Title: QuartzManager.java
     */
    void removeJob(Scheduler sche, String jobName) ;

    /**
     * @Description: 移除一个任务
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     *
     * @Title: QuartzManager.java
     */
    void removeJob(Scheduler sche, String jobName, String jobGroupName, String triggerName, String triggerGroupName) ;

    /**
     * @Description:启动所有定时任务
     * @Title: QuartzManager.java
     */
    void startJobs(Scheduler sche);

    /**
     * @Description:关闭所有定时任务
     * @Title: QuartzManager.java
     */
    void shutdownJobs(Scheduler sche) ;

    void resumeAll(Scheduler sched);
}
