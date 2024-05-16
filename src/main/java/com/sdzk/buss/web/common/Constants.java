package com.sdzk.buss.web.common;

import org.apache.commons.collections.map.HashedMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017/6/19.
 */
public class Constants {


    /**
     * 与煤监局接口错误代码
     */
    public static final String PLATFORM_RESULT_CODE_SUCCESS = "200";    //请求成功
    public static final String PLATFORM_RESULT_CODE_REPEART = "201";    //请求重复

    /**
     * 与煤监局接口：本地处理结果编码
     */
    public static final String LOCAL_RESULT_CODE_SUCCESS = "0";  //本地接口处理成功
    public static final String LOCAL_RESULT_CODE_FAILURE = "1";  //本地接口处理失败

    /**
     * 与煤监局接口：隐患督办信息同步 datasource 标志
     */
    public static final String IS_MAJORSUPERVISE_Y = "1";  //重大隐患督办
    public static final String IS_MAJORSUPERVISE_N = "0";  //非重大隐患督办

    /**
     * 数据库重大隐患上报煤监局状态：report_status in t_b_major_hidden_danger
     */
    public static final String REPORT_STATUS_N = "1";  //未上报煤监局
    public static final String REPORT_STATUS_Y = "2";  //已上报煤监局

    /**未删除**/
    public static final String IS_DELETE_N = "0";
    /**已删除**/
    public static final String IS_DELETE_Y = "1";

    public static final String IS_SHOWDATA_N = "0";
    public static final String IS_SHOWDATA_Y = "1";
    /**
     * 隐患检查_检查类型
     */
    public static final String HIDDENCHECK_EXAMTYPE_SHANGJI = "sjjc";//上级检查
    public static final String HIDDENCHECK_EXAMTYPE_YINHUANPAICHA = "yhpc";//隐患排查
    public static final String HIDDENCHECK_EXAMTYPE_GUANLIGANBUXIAJING = "glgbxj";//管理干部下井
    public static final String HIDDENCHECK_EXAMTYPE_KLDDB = "klddb";//矿领导带班下井
    public static final String HIDDENCHECK_EXAMTYPE_KUANGJINGANQUANDAJIANCHA = "kjaqdjc";//矿安全大检查
    public static final String HIDDENCHECK_EXAMTYPE_ZHILIANGBIAOZHUNHUA = "zlbzh";//质量标准化检查
    public static final String HIDDENCHECK_EXAMTYPE_ZHUANYEKESHI = "zyks";//专业科室日常检查
    public static final String HIDDENCHECK_EXAMTYPE_ANJIANYUAN = "ajy";//安监员安全检查
    public static final String HIDDENCHECK_EXAMTYPE_SANJIAYI = "thrplus";//3+1


    public static final String DEALTYPE_XIANCAHNG="2";//现场整改
    public static final String DEALTYPE_XIANQI = "1";//限期整改




    /**
     * 罚款类型 fine_type 罚款类型(隐患、挂牌、三违、其他)
     */
    public static final String FINE_TYPE_YINHUAN = "1";
    public static final String FINE_TYPE_GUAPAI = "2";
    public static final String FINE_TYPE_SANWEI = "3";
    public static final String FINE_TYPE_QITA = "4";
    public static final String FINE_TYPE_ZHLIANG = "5";


    public static final String HANDELSTATUS_ROLLBACK = "ROLLBACK";//驳回(仅用于页面查询退回数据)
    public static final String HANDELSTATUS_ROLLBACK_CHECK = "4";//复查未通过，退回整改
    public static final String HANDELSTATUS_ROLLBACK_REPORT = "2";//退回上报

    public static final String HANDELSTATUS_DRAFT = "00";//草稿文件
    public static final String HANDELSTATUS_REPORT = "1";//上报(待整改)
    public static final String HANDELSTATUS_REVIEW = "3";//(代复查)

    public static final String REVIEWSTATUS_PASS = "5";//复查通过
    public static final String REVIEWSTATUS_FALSE = "0";//复查通过
    public static final String REVIEWSTATUS_OK = "1";//复查通过

    public static final String REVIEWSTATUS_All = "0";//


    //隐患销号
    public static final String HIDDEN_DONE = "3";
    public static final String IS_SHOW_Y = "Y";
    public static final String IS_SHOW_N = "N";
    public static final String STATUS_VAL = "激活";
    public static final String STATUS_UNVAL = "未激活";
    public static final String STATUS_ADMIN = "超级管理员";
    public static final String DEFAULT_PASSWORD = "123456";


    /**
     * 微信用户列表   已关联用户： 1 未关联用户： 2
     */
    public static final String  WEIXIN_RELATION="1";
    public static final String  WEIXIN_UNRELATED="0";
    /**
     * 危险源来源   通用:1 本矿:2  专项风险评估:3
     */
    public static final String DANGER_SOURCE_ORIGIN_NOMAL = "1";
    public static final String DANGER_SOURCE_ORIGIN_MINE = "2";
    public static final String DANGER_SOURCE_ORIGIN_SPECIAL_EVALUATION = "3";

    /**
     * 标准化考核矿井自评状态
     *
     * 1：待上报 2：已上报
     */
    public static final String SF_MINE_STANDARD_ASSESS_STATUS_1 = "1";
    public static final String SF_MINE_STANDARD_ASSESS_STATUS_2 = "2";
    /**
     * 矿井考核自评汇总 数据提交
     */
    public static final String SF_MINE_STANDARD_ASSESS_SCORE_SSASCURRENTSTATUS_COMMIT = "2";


    /**
     * 危险源审核状态  待上报
     */
    public static final String DANGER_SOURCE_AUDITSTATUS_TOREPORT = "1";
    /**
     * 危险源审核状态  审核中
     */
    public static final String DANGER_SOURCE_AUDITSTATUS_REVIEW = "2";
    /**
     * 危险源审核状态  审核退回
     */
    public static final String DANGER_SOURCE_AUDITSTATUS_ROLLBANK = "3";
    /**
     * 危险源审核状态   闭环
     */
    public static final String DANGER_SOURCE_AUDITSTATUS_CLOSE = "4";
    /**
     * 危险源上报煤监局状态       未上报
     */
    public static final String DANGER_SOURCE_REPORT_UNREPORT = "1";
    /**
     * 危险源上报煤监局状态       已上报
     */
    public static final String DANGER_SOURCE_REPORT_REPORT = "2";
    /**
     * 危险源上报处理环节 1 ：上报
     */
    public static final Integer DANGER_SOURCE_AUDIT_HIS_STEP_REPORT =2;
    /**
     * 危险源上报处理环节 2：驳回上报
     */
    public static final Integer DANGER_SOURCE_AUDIT_HIS_STEP_ROLLBANK = 3;
    /**
     * 危险源上报处理环节 3：审核
     */
    public static final Integer DANGER_SOURCE_AUDIT_HIS_STEP_CLOSE = 4;
    /**
     * 危险源上报处理环节 4：撤回
     */
    public static final Integer DANGER_SOURCE_AUDIT_HIS_STEP_SELFBACK = 5;

    /**
     * 危险源上报处理环节 5：去审
     * */
    public static final Integer DANGER_SOURCE_AUDIT_HIS_STEP_DRAWBACK = 6;

    /**
     * 查询条件：所有
     */
    public static final String QUERY_STATUS_ALL = "all";
    /**
     * 查询条件：未上报 ，待审核 ，待上报
     */
    public static final String QUERY_STATUS_UNREPORT = "0";
    /**
     * 查询条件：已上报，已审核，已上报
     */
    public static final String QUERY_STATUS_REPORT = "1";

    /**
     * 标准化考核矿井自评汇总状态
     *
     * 1：待上报 2：已上报
     */
    public static final String SF_MINE_STANDARD_ASSESS_SCORE_STATUS_1 = "1";
    public static final String SF_MINE_STANDARD_ASSESS_SCORE_STATUS_2 = "2";

    /**
     *
     * 标准化考核矿井自评状态
     * 1：井工煤矿  2：露天煤矿
     */
    public static final String SF_MINE_STANDARD_ASSESS_MINE_TYPE_1 = "1";
    public static final String SF_MINE_STANDARD_ASSESS_MINE_TYPE_2 = "2";

    /**
     * 标准化考核矿井自评-权重
     */
    public  static Map<String, String> SSA_ASSESS_WEIGHT = new HashedMap();
    static {
        SSA_ASSESS_WEIGHT.put("jgAqfxfjgk","0.1");//井工安全风险分级管控
        SSA_ASSESS_WEIGHT.put("jgYhpczl","0.1");//井工事故隐患排查治理
        SSA_ASSESS_WEIGHT.put("jgTf","0.16");//井工通风
        SSA_ASSESS_WEIGHT.put("jgDzzhfzycl","0.11");//井工地质灾害防治与测量
        SSA_ASSESS_WEIGHT.put("jgCm","0.09");//井工采煤
        SSA_ASSESS_WEIGHT.put("jgJj","0.09");//井工掘进
        SSA_ASSESS_WEIGHT.put("jgJd","0.09");//井工机电
        SSA_ASSESS_WEIGHT.put("jgYs","0.08");//井工运输
        SSA_ASSESS_WEIGHT.put("jgZyws","0.06");//井工职业卫生
        SSA_ASSESS_WEIGHT.put("jgAqpxhyjgl","0.06");//井工安全培训和应急管理
        SSA_ASSESS_WEIGHT.put("jgDdhdmss","0.06");//井工调度和地面设施

        SSA_ASSESS_WEIGHT.put("ltAqfxfjgk","0.1");//露天煤矿安全风险分级管控
        SSA_ASSESS_WEIGHT.put("ltYhpczl","0.1");//露天煤矿事故隐患排查治理
        SSA_ASSESS_WEIGHT.put("ltZk","0.05");//露天煤矿钻孔
        SSA_ASSESS_WEIGHT.put("ltBp","0.11");//露天煤矿爆破
        SSA_ASSESS_WEIGHT.put("ltCz","0.11");//露天煤矿采装
        SSA_ASSESS_WEIGHT.put("ltYs","0.12");//露天煤矿运输
        SSA_ASSESS_WEIGHT.put("ltJd","0.09");//露天煤矿机电
        SSA_ASSESS_WEIGHT.put("ltPt","0.09");//露天煤矿排土
        SSA_ASSESS_WEIGHT.put("ltBianpo","0.05");//露天煤矿边坡
        SSA_ASSESS_WEIGHT.put("ltSgps","0.05");//露天煤矿疏干排水
        SSA_ASSESS_WEIGHT.put("ltZyws","0.05");//露天煤矿职业卫生
        SSA_ASSESS_WEIGHT.put("ltAqpxhyjgl","0.04");//露天煤矿安全培训和应急管理
        SSA_ASSESS_WEIGHT.put("ltDdhdmss","0.04");//露天煤矿调度和地面设施
    }

    /**
     * 重大隐患整改汇报完毕
     */
    public static final String MAJOR_HIDDEN_DANGER_RECTIFY_COMPLETE = "1";

    /**
     *隐患状态
     * 100 草稿
     * 200 待核查
     * 300 待整改
     * 400 待验收
     * 500 待复查
     * 600 已闭环
     */
    public static final String HIDDEN_DANGER_CLSTATUS_DRAFT = "100";
    public static final String HIDDEN_DANGER_CLSTATUS_VERIFY = "200";
    public static final String HIDDEN_DANGER_CLSTATUS_RECFITY = "300";
    public static final String HIDDEN_DANGER_CLSTATUS_ACCEPT = "400";
    public static final String HIDDEN_DANGER_CLSTATUS_REVIEW = "500";
    public static final String HIDDEN_DANGER_CLSTATUS_FINISHED = "600";

    /**
     * 审核结果：审核通过
     */
    public static final String REVIEW_RESULT_PASS = "1";
    /**
     * 审核结果：驳回上报
     */
    public static final String REVIEW_RESULT_ROLLBANK = "0";


    /**
     * 隐患处理状态字典组Id
     */
    public static final String HIDDEN_HANDEL_STATUS_TYPE_GROUP_ID = "40288005545a49db01545a59b25a0001";
    /**
     * 隐患性质字典组ID
     */
    public static final String HIDDEN_NATURE_TYPE_GROUP_ID = "402880055456a4c7015456dba6c10008";
    /**
     * 隐患检查类型字典组ID
     */
    public static final String HIDDEN_EXAM_TYPE_GROUP_ID = "40288005545b7f5c01545bab64330001";


    public static final String TASKSTATUS_CONFIRM="1";
    public static final String TASKSTATUS_CLOSE="2";
    //科室待上报到矿
    public static final String REPORTSTATUS_UNREPORT_COAL = "0";
    //科室已上报到矿，矿未上报到集团
    public static final String REPORTSTATUS_REPORT_COAL = "1";
    //矿上报到集团
    public static final String REPORTSTATUS_REPORT_GROUP = "2";
    //修正系数管理
    public static final String TBDEPARTFACTORRELTYPE_XZXS = "xzxs";
    //任务积分管理
    public static final String TBDEPARTFACTORRELTYPE_RWJF = "rwjf";
    //区队
    public static final String DEPTORGCODE_QD = "4";
    //科室
    public static final String DEPTORGCODE_KS = "3";
    //隐患新增
    public static final String HIDDEN_NEW = "1";
    //隐患延续
    public static final String HIDDEN_CONTINUE = "2";
    //隐患销号


    /**
     * 专项风险辨识状态
     */
    public static final String SE_REPORT_STATUS_PENDING_REPORT = "0";
    public static final String SE_REPORT_STATUS_DEPART_REPORT = "1";
    public static final String SE_REPORT_STATUS_MINE_REPORT = "2";


    /**
     * 挂牌督办状态
     *
     * 0：未督办
     *
     * 1：督办
     */
    public static final String HDBIISLS_STATE_UNDO = "0";
    public static final String HDBIISLS_STATE_DO = "1";

    /**
     * 挂牌督办级别
     *
     * 1：分局督办
     *
     * 2：省局督办
     */
    public static final String LSILSLEVEL_BRANCH = "1";
    public static final String LSILSLEVEL_GENERAL_ADMINISTRATION = "2";

    /**
     * 数据字典来源
     * 1 = 煤监局
     * 2 = 矿
     */
    public static final String TYPE_GROUP_ORIGIN_MJJ = "1";
    public static final String TYPE_GROUP_ORIGIN_MINE = "2";

    /**
     * 风险模板导入类型
     */
    public static final String TYPE_RISK_IMPORT_SPECIAL = "special";
    public static final String TYPE_RISK_IMPORT_DEPART_REPORT = "departReport";


    /**
     * 是否重大风险：ismajor in t_b_danger_source
     */
    public static final String IS_MAJORDangerSource_Y = "1";  //重大风险
    public static final String IS_MAJORDangerSource_N = "0";  //非重大风险

    /**
     * 排查计划状态
     *
     * 1 = 草稿
     * 2 = 退回待处理
     * 3 = 待受理
     * 4 = 待回复
     * 5 = 已回复
     */
    public static final String INVESTIGATEPLAN_STATUS_PENDINGCOMMIT = "1";
    public static final String INVESTIGATEPLAN_STATUS_BACK = "2";
    public static final String INVESTIGATEPLAN_STATUS_PENDINGACCEPT = "3";
    public static final String INVESTIGATEPLAN_STATUS_PENDINGINVESTIGATE = "4";
    public static final String INVESTIGATEPLAN_STATUS_INVESTIGATE = "5";

    /**
     * 排查计划类型
     * 1 = 月排查
     * 2 = 旬排查
     * 3 = 周排查
     */
    public static final String INVESTIGATEPLAN_TYPE_MONTH = "1";
    public static final String INVESTIGATEPLAN_TYPE_XUN = "2";
    public static final String INVESTIGATEPLAN_TYPE_WEEK = "3";

    /**
     * 排查计划-风险点类型
     * 1 = 区域
     * 2 = 工作流程
     */
    public static final String INVESTIGATEPLAN_RISKPOINT_TYPE_LOCATION = "1";
    public static final String INVESTIGATEPLAN_RISKPOINT_TYPE_WORK = "2";

    /**
     * 排查计划-关系类型
     * 1 = 关联风险点
     * 2 = 关联危险源
     * 3 = 关联隐患
     */
    public static final String INVESTIGATEPLAN_REL_TYPE_RISKPOINT = "1";
    public static final String INVESTIGATEPLAN_REL_TYPE_RISK = "2";
    public static final String INVESTIGATEPLAN_REL_TYPE_HIDDENDANGER = "3";

    /**
     * 预警管理—单位类型(1:矿井 2:单位)
     */
    public static final String ALERT_MANAGE_UNIT_TYPE_1 = "1";
    public static final String ALERT_MANAGE_UNIT_TYPE_2 = "2";

    /**
     * 预警管理—预警指标(1:隐患三违个数 2:检查次数)
     */
    public static final String ALERT_MANAGE_ALERT_INDEX_1 = "1";
    public static final String ALERT_MANAGE_ALERT_INDEX_2 = "2";

    /**
     * 预警管理—风险等级预警颜色
     */
    public static final String ALERT_COLOR_ZDFX = "#ff0000";
    public static final String ALERT_COLOR_JDFX = "#ff7300";
    public static final String ALERT_COLOR_YBFX = "#fff719";
    public static final String ALERT_COLOR_DFX = "#0051ff";
    public static final String ALERT_COLOR_NULL = "#D3D3D3";

    /**
     * 同步接口调用结果
     */
    public static final String SYNC_RESULT_SUCCESS = "1";
    public static final String SYNC_RESULT_FAILED = "2";

    public static Map<String, String> MINE_INFO = new HashMap<String, String>();

    public static final String API_PARAM_KEY_TOKEN = "token";
    public static final String API_PARAM_KEY_MINECODE = "mineCode";
    public static final String API_PARAM_KEY_DATASOURCE = "dataSource";

    /**
     * 字典组编码
     */
    public interface TYPE_GROUP_CODE {
        String HIDDEN_LEVEL = "hiddenLevel";
        String RISK_LEVEL = "riskLevel";
        String VIO_LEVEL = "vio_level";
    }

    /**
     * 矩阵-隐患等级阀值字典组编码
     */
    public static final String THRESHOLD_DIC_HIDDENDANGER_LEVEL = "hiddenDangerLevelThreshold";

    /**
     * LEC-隐患等级阀值字典组编码
     */
    public static final String THRESHOLD_DIC_HIDDENDANGER_LEVEL_LEC = "hiddenDangerLevelLECThreshold";

    public static final String HIDDEN_LEVEL_HIDE_WHERE = "select t.typecode from t_s_type t where t.typegroupid = (select g.id from t_s_typegroup g where g.typegroupcode = '"+ TYPE_GROUP_CODE.HIDDEN_LEVEL+"') and t.is_hide = 0";
    public static final String THREE_VIO_LEVEL_HIDE_WHERE = "select t.typecode from t_s_type t where t.typegroupid = (select g.id from t_s_typegroup g where g.typegroupcode = '"+ TYPE_GROUP_CODE.VIO_LEVEL+"') and t.is_hide = 0";
    public static final String RISK_LEVEL_HIDE_WHERE = "select t.typecode from t_s_type t where t.typegroupid = (select g.id from t_s_typegroup g where g.typegroupcode = '"+ TYPE_GROUP_CODE.RISK_LEVEL+"') and t.is_hide = 0";

    /**
     * 发送短信类型
     */
    public static final String SMS_TYPE_HIDDEN_DANGER_REPORT = "1";     //隐患上报
    public static final String SMS_TYPE_HIDDEN_DANGER_RECTIFY = "2";    //隐患整改
    public static final String SMS_TYPE_HIDDEN_DANGER_EXPIRED = "3";    //隐患超期

    /**
     * 隐患整改人和复查人对应code
     */
    public static final String HIDDEN_DANGER_REVIEW_ROLE = "yhfcr";
    public static final String HIDDEN_DANGER_MODIFY_ROLE = "yhzgr";

    //手机端上传图片及视频关联数据类型
//    public static  final  String MOBILE_FILE_TYPE_RISK = "risk";
    public static  final  String MOBILE_FILE_TYPE_HD = "hiddenDanger";
    public static  final  String MOBILE_FILE_TYPE_VIO = "vio";


    /**
     * 国家局数据操作标识（增1，改2，删3，上报成功0）
     */
    public static final String GJJ_STATE_FLAG_0 = "0";
    public static final String GJJ_STATE_FLAG_1 = "1";
    public static final String GJJ_STATE_FLAG_2 = "2";
    public static final String GJJ_STATE_FLAG_3 = "3";

    //隐患等级
    public static final String HIDDEN_LEVEL_1 = "1";   //重大隐患
    public static final String HIDDEN_LEVEL_2 = "2";   //一般隐患A级
    public static final String HIDDEN_LEVEL_3 = "3";   //一般隐患B级
    public static final String HIDDEN_LEVEL_4 = "4";   //一般隐患C级
    public static final String HIDDEN_LEVEL_5 = "5";   //一般隐患D级
    public static final String HIDDEN_LEVEL_6 = "6";   //一般隐患E级


    //风险等级
    public static final String FACTORS_LEVEL_1 = "1";   //重大风险
    public static final String FACTORS_LEVEL_2 = "2";   //较大风险
    public static final String FACTORS_LEVEL_3 = "3";   //一般风险
    public static final String FACTORS_LEVEL_4 = "4";   //低风险
}
