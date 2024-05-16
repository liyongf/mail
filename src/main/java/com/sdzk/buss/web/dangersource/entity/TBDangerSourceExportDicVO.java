package com.sdzk.buss.web.dangersource.entity;

/**这里面的字段保持与excel导出的数据字典中的存在数据校验的字段一致。
 * 这里面需要注意的地方是，不能有excel列为空列，不赋t.xxxxx，否则会报错。
 * */
public class TBDangerSourceExportDicVO {
    /**风险点类型*/
    private String addressCate;
    /**专业*/
    private String profession;
    /**危险源名称*/
    private String dangerName;
    /**伤害类别*/
    private String injuryCategory;
    /**事故类型*/
    private String accidentType;
    /**作业活动*/
    private String activity;
    /**可能性*/
    private String possibility;
    /**损失*/
    private String loss;
    /**风险类型*/
    private String riskType;
    /**责任岗位*/
    private String responsibilityPost ;
    /**隐患等级*/
    private String hiddenDangerGrade;

    //下面的是lec的风险可能性，风险损失和暴露在风险环境中的概率
    /**lec风险可能性*/
    private String lecProbability;
    /**lec风险损失*/
    private String lecLoss;
    /**lec暴露在环境中的概率*/
    private String lecExposure;

    public String getLecProbability() {
        return lecProbability;
    }

    public void setLecProbability(String lecProbability) {
        this.lecProbability = lecProbability;
    }

    public String getLecLoss() {
        return lecLoss;
    }

    public void setLecLoss(String lecLoss) {
        this.lecLoss = lecLoss;
    }

    public String getLecExposure() {
        return lecExposure;
    }

    public void setLecExposure(String lecExposure) {
        this.lecExposure = lecExposure;
    }

    public String getAddressCate() {
        return addressCate;
    }

    public void setAddressCate(String addressCate) {
        this.addressCate = addressCate;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDangerName() {
        return dangerName;
    }

    public void setDangerName(String dangerName) {
        this.dangerName = dangerName;
    }

    public String getInjuryCategory() {
        return injuryCategory;
    }

    public void setInjuryCategory(String injuryCategory) {
        this.injuryCategory = injuryCategory;
    }

    public String getAccidentType() {
        return accidentType;
    }

    public void setAccidentType(String accidentType) {
        this.accidentType = accidentType;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getPossibility() {
        return possibility;
    }

    public void setPossibility(String possibility) {
        this.possibility = possibility;
    }

    public String getLoss() {
        return loss;
    }

    public void setLoss(String loss) {
        this.loss = loss;
    }

    public String getRiskType() {
        return riskType;
    }

    public void setRiskType(String riskType) {
        this.riskType = riskType;
    }

    public String getResponsibilityPost() {
        return responsibilityPost;
    }

    public void setResponsibilityPost(String responsibilityPost) {
        this.responsibilityPost = responsibilityPost;
    }

    public String getHiddenDangerGrade() {
        return hiddenDangerGrade;
    }

    public void setHiddenDangerGrade(String hiddenDangerGrade) {
        this.hiddenDangerGrade = hiddenDangerGrade;
    }
}
