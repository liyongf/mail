package com.sddb.common;

public class Constants {

    /**
     * 是否删除：未删除
     */
    public static final String HAZARDFACTORS_IS_DEL_FALSE = "0";
    /**
     * 是否删除：已删除
     */
    public static final String HAZARDFACTORS_IS_DEL_TRUE = "1";

    /**
     * 危害因素来源：基础库
     */
    public static final String HAZARDFACTORS_FROM_BASE = "1";
    /**
     * 危害因素来源：年度辨识
     */
    public static final String HAZARDFACTORS_FROM_YEAR = "2";
    /**
     * 危害因素来源：专业辨识
     */
    public static final String HAZARDFACTORS_FROM_MAJOR = "3";
    /**
     * 危害因素来源：岗位辨识
     */
    public static final String HAZARDFACTORS_FROM_POST = "4";
    /**
     * 危害因素来源：临时工作辨识
     */
    public static final String HAZARDFACTORS_FROM_TEMPWORK = "5";

    /**
     * 危害因素审批状态：草稿
     */
    public static final String HAZARDFACTORS_STATUS_DRAFT = "0";
    /**
     * 危害因素审批状态：提交审批
     */
    public static final String HAZARDFACTORS_STATUS_TOVIEW = "1";
    /**
     * 危害因素审批状态:驳回
      */
    public static final String HAZARDFACTORS_STATUS_ROLLBACK = "2";
    /**
     * 危害因素审批状态:审批通过
     */
    public static final String HAZARDFACTORS_STATUS_REVIEW = "3";

    /**
     * 风险辨识：草稿
     */
    public static final String RISK_IDENTIFI_STATUS_DRAFT = "0";
    /**
     * 风险辨识：提交审核
     */
    public static final String RISK_IDENTIFI_STATUS_TOVIEW = "1";
    /**
     * 风险辨识：驳回
     */
    public static final String RISK_IDENTIFI_STATUS_ROLLBACK  = "2";
    /**
     * 风险辨识：审批通过
     */
    public static final String RISK_IDENTIFI_STATUS_REVIEW  = "3";

    /**
     * 是否删除：未删除
     */
    public static final String RISK_IS_DEL_FALSE = "0";
    /**
     * 是否删除：已删除
     */
    public static final String RISK_IS_DEL_TRUE = "1";


    ///////////////////////////////////////////////////////////////
    /**
     * 风险管控任务中风险的管控状态
     *
     * 0：未处理
     *
     * 1：已处理
     */
    public static final String RISK_MANAGE_TASK_RISK_STATUS_UNFINISHED = "0";
    public static final String RISK_MANAGE_TASK_RISK_STATUS_FINISHED = "1";

    /**
     * 风险管控任务中的风险的关联危害因素的处理状态
     *
     * 0：未处理
     *
     * 1：已处理
     */
    public static final String RISK_MANAGE_HAZARD_FACTOR_STATUS_UNFINISHED = "0";
    public static final String RISK_MANAGE_HAZARD_FACTOR_STATUS_FINISHED = "1";
    //////////////////////////////////////////////////////////////

    /**
     * 超图发布类型
     *
     * firm：双防厂商发布
     *
     * self：本矿自主发布
     */
    public static final String SUPERMAP_PUBLISH_TYPE_FIRM = "firm";
    public static final String SUPERMAP_PUBLISH_TYPE_SELF = "self";
}
