package com.sdzk.buss.web.aqbzh.pojo.dto;


public class QualityControlProjectDTO {
    /**
     * id
     */
    private Integer id;
    /**
     * 基本要求
     */
    private String requirements;
    /**
     * 标准分值
     */
    private String standardScore;
    /**
     * 煤矿类型
     */
    private String typeCode;
    /**
     * 评分方法
     */
    private String scoreMethod;
    /**
     * 层级 1 一层  2二层
     */
    private String level;
    /**
     * 层级类型
     */
    private String levelType;
    /**
     * 最后一层排序
     */
    private Integer a2;

    /**
     * 是否为附加项 1是  null不是
     */
    private String a4;

    /**
     * 是否合并分数
     */
    private String a5;

    /**
     * 考核方式 0默认  1取最低  2取最高   3取平均
     */
    private Integer qcpA2;
    /**
     * 项目名称 三级
     */
    private String projectName;
    /**
     * 项目名称 二级
     */
    private String projectName2;
    /**
     * 项目名称 一级
     */
    private String projectName1;
    /**
     * 排序 第3层排序
     */
    private Integer sort;
    /**
     * 状态
     */
    private Integer status;

    /**
     * 页面地址
     */
    private String url;
    /**
     * 父类id
     */
    private Integer parentId;
    /**
     * 树id
     */
    private String treeId;
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 总排序
     */
    private Integer totalSort;

    private String totalSort1;

    /**
     * 是否多点 1单点 2多点
     */
    private Integer a1;

    /**
     * 分数
     */
    private String scoreDetails;

    /**
     * 是否录入隐患 1是   2否
     */
    private Integer isHidden;

    /**
     * 层级 1 一层  2二层
     */
    private String levelName;
    /**
     * 层级类型
     */
    private String levelTypeName;

    /**
     * 考核明细表id
     */
    private Integer daId;
    /**
     * treeId
     */
    private String projectTreeId;
    /**
     * 打分点code
     */
    private String placeCode;
    /**
     * 考核汇总id
     */
    private Integer totalAssesId;

    public String getLevelName (){
        return "";
    }

    public String getLevelTypeName (){
        return this.getRequirements();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }

    public String getStandardScore() {
        return standardScore;
    }

    public void setStandardScore(String standardScore) {
        this.standardScore = standardScore;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getScoreMethod() {
        return scoreMethod;
    }

    public void setScoreMethod(String scoreMethod) {
        this.scoreMethod = scoreMethod;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelType() {
        return levelType;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType;
    }

    public Integer getA2() {
        return a2;
    }

    public void setA2(Integer a2) {
        this.a2 = a2;
    }

    public String getA4() {
        return a4;
    }

    public void setA4(String a4) {
        this.a4 = a4;
    }

    public String getA5() {
        return a5;
    }

    public void setA5(String a5) {
        this.a5 = a5;
    }

    public Integer getQcpA2() {
        return qcpA2;
    }

    public void setQcpA2(Integer qcpA2) {
        this.qcpA2 = qcpA2;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName2() {
        return projectName2;
    }

    public void setProjectName2(String projectName2) {
        this.projectName2 = projectName2;
    }

    public String getProjectName1() {
        return projectName1;
    }

    public void setProjectName1(String projectName1) {
        this.projectName1 = projectName1;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getTotalSort() {
        return totalSort;
    }

    public void setTotalSort(Integer totalSort) {
        this.totalSort = totalSort;
    }

    public String getTotalSort1() {
        return totalSort1;
    }

    public void setTotalSort1(String totalSort1) {
        this.totalSort1 = totalSort1;
    }

    public Integer getA1() {
        return a1;
    }

    public void setA1(Integer a1) {
        this.a1 = a1;
    }

    public String getScoreDetails() {
        return scoreDetails;
    }

    public void setScoreDetails(String scoreDetails) {
        this.scoreDetails = scoreDetails;
    }

    public Integer getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Integer isHidden) {
        this.isHidden = isHidden;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public void setLevelTypeName(String levelTypeName) {
        this.levelTypeName = levelTypeName;
    }

    public Integer getDaId() {
        return daId;
    }

    public void setDaId(Integer daId) {
        this.daId = daId;
    }

    public String getProjectTreeId() {
        return projectTreeId;
    }

    public void setProjectTreeId(String projectTreeId) {
        this.projectTreeId = projectTreeId;
    }

    public String getPlaceCode() {
        return placeCode;
    }

    public void setPlaceCode(String placeCode) {
        this.placeCode = placeCode;
    }

    public Integer getTotalAssesId() {
        return totalAssesId;
    }

    public void setTotalAssesId(Integer totalAssesId) {
        this.totalAssesId = totalAssesId;
    }
}
