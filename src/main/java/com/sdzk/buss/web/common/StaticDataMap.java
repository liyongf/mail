package com.sdzk.buss.web.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 17-9-21.
 */
public class StaticDataMap {

    /**
     * 伤害类别
     */
    public static Map<String,String> damageTypeMap = new HashMap<String,String>();
    public static final String DAMAGETYPE_OTHER = "20";
    /**
     * 隐患等级
     */
    public static Map<String,String> hiddenLevelMap = new HashMap<String,String>();
    /**
     * 风险类别
     */
    public static Map<String,String> yeHazardCateMap = new HashMap<String,String>();
    /**
     * 风险等级
     */
    public static Map<String,String> yeRiskGradeMap = new HashMap<String,String>();
    /**
     * 专业
     */
    public static Map<String,String> yeProfessionMap = new HashMap<String,String>();

    /**
     * 其他专业
     */
    public static final String PROFESSION_OTHER = "6";
    /**
     * 事故类型
     */
    public static Map<String,String> yeAccidentMap = new HashMap<String,String>();

    /**
     * 其他事故
     */
    public static final String ACCIDENT_OTHER = "8";
    static{
        damageTypeMap.put("01","物体打击");
        damageTypeMap.put("02","车辆伤害");
        damageTypeMap.put("03","机械伤害");
        damageTypeMap.put("04","起重伤害");
        damageTypeMap.put("05","触电");
        damageTypeMap.put("06","淹溺");
        damageTypeMap.put("07","灼烫");
        damageTypeMap.put("08","火灾");
        damageTypeMap.put("09","高处坠落");
        damageTypeMap.put("10","坍塌");
        damageTypeMap.put("11","冒顶片帮");
        damageTypeMap.put("12","透水");
        damageTypeMap.put("13","放炮");
        damageTypeMap.put("14","瓦斯爆炸");
        damageTypeMap.put("15","火药爆炸");
        damageTypeMap.put("16","锅炉爆炸");
        damageTypeMap.put("17","容器爆炸");
        damageTypeMap.put("18","其他爆炸");
        damageTypeMap.put("19","中毒和窒息");
        damageTypeMap.put("20","其他伤害");

        hiddenLevelMap.put("1","重大隐患");
        hiddenLevelMap.put("2","一般隐患A级");
        hiddenLevelMap.put("3","一般隐患B级");
        hiddenLevelMap.put("4","一般隐患C级");

        yeHazardCateMap.put("1","人");
        yeHazardCateMap.put("2","机");
        yeHazardCateMap.put("3","环");
        yeHazardCateMap.put("4","管");
        yeHazardCateMap.put("5","技");

        yeRiskGradeMap.put("1","重大风险");
        yeRiskGradeMap.put("2","较大风险");
        yeRiskGradeMap.put("3","一般风险");
        yeRiskGradeMap.put("4","低风险");

        yeProfessionMap.put("1","采煤");
        yeProfessionMap.put("2","掘进");
        yeProfessionMap.put("3","机电");
        yeProfessionMap.put("4","运输");
        yeProfessionMap.put("5","通风");
        yeProfessionMap.put("6","其他");

        yeAccidentMap.put("1","顶板");
        yeAccidentMap.put("2","瓦斯");
        yeAccidentMap.put("3","机电");
        yeAccidentMap.put("4","运输");
        yeAccidentMap.put("5","放炮");
        yeAccidentMap.put("6","水害");
        yeAccidentMap.put("7","火灾");
        yeAccidentMap.put("8","其他");
    }
}
