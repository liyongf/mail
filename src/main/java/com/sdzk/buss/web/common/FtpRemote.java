package com.sdzk.buss.web.common;

/**
 * ftp 上传路径
 *
 * @author ljh
 * @version 1.0
 * @date 2023/11/3 11:06
 */
public enum FtpRemote {

    /**
     * 主文件夹
     */
    MAIN("/ftpscyf/"),
    /**
     * 风险辨识任务
     */
    RISK("/risk/"),
    /**
     * 图形文件
     */
    PICTURE("/picture/"),
    /**
     * 隐患台账
     */
    HIDDEN("/hidden/"),
    /**
     * 报告文件
     */
    REPORT("/report/"),
    /**
     * 重大风险管控方案
     */
    PLAN("/plan/"),
    /**
     * 重大隐患档案信息
     */
    ARCHIVE("/archive/"),
    /**
     * 培训档案
     */
    TRAIN("/train/"),
    /**
     * 双重预防工作制度
     */
    sys("/sys/");

    FtpRemote(String remote) {
        this.remote = remote;
    }

    /**
     * 路径
     */
    private final String remote;

    public String getRemote() {
        return remote;
    }
}
