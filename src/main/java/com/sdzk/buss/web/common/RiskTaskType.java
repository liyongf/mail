package com.sdzk.buss.web.common;

/**
 * 风险辨识任务类型 枚举对照
 * risk_task_type
 *
 * @author ljh
 * @version 1.0
 * @date 2023/10/25 18:34
 */
public enum RiskTaskType {

    /**
     * 年度风险辨识
     */
    T2("2", "1"),
    /**
     * 专项风险辨识
     */
    T3("3", "2"),
    /**
     * 岗位风险辨识
     */
    T4("4", "3"),
    /**
     * 临时施工风险辨识
     */
    T5("5", "3"),

    /**
     * 三违风险等级  vio_level  402880f65cbdedfb015cbe247b910010
     *  矿                国家局
     * 1：重大A级       严重三违：2
     * 2：一般B级       一般三违：1
     * 3：轻微C级       一般三违：1
     *
     */
    VIO_LEVEL_1("1", "2"),
    VIO_LEVEL_2("2", "1"),
    VIO_LEVEL_3("3", "1"),

    /**
     * 违章定性  violaterule_wzdx  402880f65cbdedfb015cbe22df61000e
     *  矿                国家局
     * 1：0	一般三违       一般三违：1
     * 2：1	典型三违       一般三违：1
     * 3：2	较严重三违       一般三违：1
     * 4：3	严重三违        严重三违：2
     *
     */
    VIO_LEVEL_11("0", "1"),
    VIO_LEVEL_12("1", "1"),
    VIO_LEVEL_13("2", "1"),
    VIO_LEVEL_14("3", "2"),

    /**
     * 三违班次  workShift  402881e65cbed0a9015cbf1c411d000e
     * 矿                国家局
     *1：早             一班或早班：1
     * 2：中              二班或中班：2
     * 3：晚              三班或晚班：3
     * 4：晚              一班或早班：1
     * 5：晚              一班或早班：1
     * 6：晚              一班或早班：1
     * 7：晚              一班或早班：1
     */
    VIO_SHIFT_1("1", "1"),
    VIO_SHIFT_2("2", "2"),
    VIO_SHIFT_3("3", "3"),
    VIO_SHIFT_4("4", "1"),
    VIO_SHIFT_5("5", "1"),
    VIO_SHIFT_6("6", "1"),
    VIO_SHIFT_7("7", "1"),

    /**
     * 违章分类  violaterule_wzfl  402880f65cbdedfb015cbe21f6d4000b
     * 矿                国家局
     * 1：违章指挥             违章指挥：1
     * 2：违规作业              违规作业：2
     * 3：违反劳动纪律              违反劳动纪律：3
     * 4：其他              其他：3
     */
    VIO_CATE_1("1", "1"),
    VIO_CATE_2("2", "2"),
    VIO_CATE_3("3", "3"),
    VIO_CATE_4("4", "4"),

    /**
     * 隐患 排查类型
     * 4028833d661f20c001661f23e6340002      manageType
     * 40287d996a2578fc016a25838f0a0002    taskManageType
     * 矿                国家局
     * 1：glryrcjc ：   管理人员日常检查             日常检查：4
     * 2：ajyrcjc      安监员日常检查             日常检查：4
     * 3：sjldjc       上级领导检查            上级检查：6
     * 4：klddbjc      矿领导带班检查             矿领导带班排查：3
     * 5：comprehensive综合管控              其他：8
     * 6：profession   专业管控                其他：8
     * 7：team         区队（车间）管控       日常检查：4
     * 8：group        班组管控              日常检查：4
     * 9：post         岗位管控              岗位排查：5
     */
    INVE_TYPE_1("glryrcjc", "4"),
    INVE_TYPE_2("ajyrcjc", "4"),
    INVE_TYPE_3("sjldjc", "6"),
    INVE_TYPE_4("klddbjc", "3"),
    INVE_TYPE_5("comprehensive", "8"),
    INVE_TYPE_6("profession", "8"),
    INVE_TYPE_7("team", "4"),
    INVE_TYPE_8("group", "4"),
    INVE_TYPE_9("post", "5"),


    /**
     * 隐患 专业
     * hiddenProf   402880ea5d87cf0c015d87d34428000a
     * 矿                国家局
     * 1	采煤            1
     * 2	掘进             2
     * 3	机电              3
     * 4	运输
     * 5	通风
     * 6	其他
     * 7	矿井
     * 8	地测
     * 9	煤与瓦斯突出
     * 10	冲击地压
     * 11	防治水
     * 12	井下爆破
     * 13	监控与通信
     * 14	职业病危害防治
     * 15	应急救援
     * 16	防灭火
     *
     *
     *
     */
    /**
     * 采煤
     */
    PRO_TYPE_1("1", "1"),
    /**
     * 掘进
     */
    PRO_TYPE_2("2", "2"),
    /**
     * 机电
     */
    PRO_TYPE_3("3", "3"),
    /**
     * 运输
     */
    PRO_TYPE_4("4", "4"),
    /**
     * 通风
     */
    PRO_TYPE_5("5", "5"),
    /**
     * 其他
     */
    PRO_TYPE_6("6", "17"),
    /**
     * 矿井
     */
    PRO_TYPE_7("7", "17"),
    /**
     * 地测
     */
    PRO_TYPE_8("8", "6"),
    /**
     * 煤与瓦斯突出
     */
    PRO_TYPE_9("9", "17"),
    /**
     * 冲击地压
     */
    PRO_TYPE_10("10", "17"),
    /**
     * 防治水
     */
    PRO_TYPE_11("11", "12"),
    /**
     * 井下爆破
     */
    PRO_TYPE_12("12", "8"),
    /**
     * 监控与通信
     */
    PRO_TYPE_13("13", "13"),
    /**
     * 职业病危害防治
     */
    PRO_TYPE_14("14", "15"),
    /**
     * 应急救援
     */
    PRO_TYPE_15("15", "14"),
    /**
     * 防灭火
     */
    PRO_TYPE_16("16", "17"),


    /**
     * hiddenLevel    402880ea5d87cf0c015d87d3411e0003
     */
    /**
     * 重大隐患
     */
    HIDDEN_LEVEL_1("1", "1" ),
    /**
     * 一般隐患A级
     */
    HIDDEN_LEVEL_2("2", "2" ),
    /**
     * 一般隐患B级
     */
    HIDDEN_LEVEL_3("3", "3" ),
    /**
     * 一般隐患C级
     */
    HIDDEN_LEVEL_4("4", "4" ),
    /**
     * 一般隐患D级
     */
    HIDDEN_LEVEL_5("5", "5" ),
    /**
     * 一般隐患E级
     */
    HIDDEN_LEVEL_6("6", "5" ),


    /**
     * 402880f05ce719d4015ce71ee74e0003    investigatePlan_status
     */
    /**
     * 未整改-待整改
     */
    HIDDEN_STATE_1("1", "1"),
//    /**
//     * 退回上报-待整改
//     */
//    HIDDEN_STATE_2("2", "1"),
    /**
     * 待复查-待验收
     */
    HIDDEN_STATE_3("3", "2"),
    /**
     * 复查未通过-待整改
     */
    HIDDEN_STATE_4("4", "1"),

    /**
     * 已复查-已销号
     */
    HIDDEN_STATE_5("5", "3"),


    /**
     * 限期整改
     */
    HIDDEN_SHIFT_1("1","1"),
    /**
     * 现场整改
     */
    HIDDEN_SHIFT_2("2","2"),


    /**
     * 国家局风险等级  4：重大风险   3：较大风险  2：一般风险   1：低风险
     * 402880e55cc4afda015cc4b73d230020
     */
    /**
     * 重大风险
     */
    RISK_LEVEL_1("1","4"),
    /**
     * 较大风险
     */
    RISK_LEVEL_2("2","3"),
    /**
     * 一般风险
     */
    RISK_LEVEL_3("3","2"),
    /**
     * 低风险
     */
    RISK_LEVEL_4("4","1"),


    /**
     * 风险类型   国家局
     * 402883f565ea74e70165ea7e02470001 风险类型
     * 1：物体打击
     * 2：车辆伤害
     * 3：机械伤害
     * 4：起重伤害
     * 5：触电
     * 6：淹溺
     * 7：灼烫
     * 8：火灾
     * 9：高处坠落
     * 10：坍塌
     * 11：冒顶片帮
     * 12：水灾
     * 13：放炮
     * 14：火药爆炸
     * 15：瓦斯爆炸
     * 16：锅炉爆炸
     * 17：容器爆炸
     * 18：其他爆炸
     * 19：中毒和窒息
     * 20：冲击地压
     * 21：煤和瓦斯突出
     * 22：煤尘爆炸
     * 23：职业病伤害（粉尘、噪音、辐射、热害等）
     * 24：其他
     *
     */
    /**
     * 放炮
     */
    RISK_TYPE_1("1","13"),
    /**
     * 火灾
     */
    RISK_TYPE_2("2","8"),
    /**
     * 高处坠落
     */
    RISK_TYPE_3("3","9"),
    /**
     * 锅炉爆炸
     */
    RISK_TYPE_4("4","16"),
    /**
     * 机电（触电、机械伤害）
     */
    RISK_TYPE_5("5","5"),
    /**
     * 冒顶（片帮）
     */
    RISK_TYPE_6("6","11"),
    /**
     * 职业病危害
     */
    RISK_TYPE_7("7","23"),
    /**
     * 瓦斯
     */
    RISK_TYPE_8("8","15"),
    /**
     * 物体打击
     */
    RISK_TYPE_9("9","1"),
    /**
     * 其它
     */
    RISK_TYPE_10("10","24"),
    /**
     * 起重伤害
     */
    RISK_TYPE_11("11","4"),
    /**
     * 运输     ------
     */
    RISK_TYPE_12("12","24"),
    /**
     * 灼烫
     */
    RISK_TYPE_13("13","7"),
    /**
     * 煤尘爆炸
     */
    RISK_TYPE_14("14","22"),
    /**
     * 淹溺
     */
    RISK_TYPE_15("15","6"),
    /**
     * 水灾
     */
    RISK_TYPE_16("16","12"),
    /**
     * 容器爆炸
     */
    RISK_TYPE_17("17","17"),
    /**
     * 冲击地压
     */
    RISK_TYPE_18("18","20"),
    /**
     * 坍塌
     */
    RISK_TYPE_19("19","10"),


    /**
     * 风险 管控类型
     * 1：日
     * 2：月
     * 3：年
     * 4：其他
     */
    /**
     * 风险 管控类型
     * 矿                国家局
     * 1：glryrcjc ：   管理人员日常检查             1日常
     * 2：ajyrcjc      安监员日常检查             1日常
     * 3：sjldjc       上级领导检查            4：其他
     * 4：klddbjc      矿领导带班检查               1日常
     * 5：comprehensive综合管控               2：月
     * 6：profession   专业管控                 2：月
     * 7：team         区队（车间）管控       4：其他
     * 8：group        班组管控              4：其他
     * 9：post         岗位管控               2：月
     */
    CONTROL_TYPE_1("glryrcjc", "1"),
    CONTROL_TYPE_2("ajyrcjc", "1"),
    CONTROL_TYPE_3("sjldjc", "4"),
    CONTROL_TYPE_4("klddbjc", "1"),
    CONTROL_TYPE_5("comprehensive", "2"),
    CONTROL_TYPE_6("profession", "2"),
    CONTROL_TYPE_7("team", "4"),
    CONTROL_TYPE_8("group", "4"),
    CONTROL_TYPE_9("post", "2"),
    ;

    RiskTaskType(String province, String country) {
        this.province = province;
        this.country = country;
    }

    /**
     * 省局数据的编码
     */
    private final String province;
    /**
     * 国家局数据编码
     */
    private final String country;

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }
}
