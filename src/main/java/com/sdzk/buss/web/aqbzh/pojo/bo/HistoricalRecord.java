/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年06月10日
 * 修改日期：2020年06月10日
 */
package com.sdzk.buss.web.aqbzh.pojo.bo;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Description Bean
 * @author：Tony
 * @version：V1.0
 * @since：2020年06月10日
 */
@Entity
@Table(name = "historical_record", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class HistoricalRecord implements java.io.Serializable {

    /**
     * @Description 主键
     */
    private Integer id;

    /**
     * @Description 表id
     */
    private String moduleId;
    /**
     * @Description 汇总表Id
     */
    private Integer totalAssesId;
    /**
     * @Description List集合
     */
    private String detailsListDto;
    /**
     * @Description 应打分分数
     */
    private String score;
    /**
     * @Description 标准分值
     */
    private String methodScore;
    /**
     * @Description 权重
     */
    private String theWeight;
    /**
     * 公式计算权重
     */
    private String a1;
    /**
     * 1打分 2查看
     */
    private Integer a2;

    private String a3;

    private String a4;

    private String a5;

    private String a6;
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "native")
    @Column(name ="ID",nullable=false,length=100)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name ="module_id",nullable=true)
    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
    @Column(name ="total_asses_id",nullable=true)
    public Integer getTotalAssesId() {
        return totalAssesId;
    }

    public void setTotalAssesId(Integer totalAssesId) {
        this.totalAssesId = totalAssesId;
    }
    @Column(name ="details_list_dto",nullable=true)
    public String getDetailsListDto() {
        return detailsListDto;
    }

    public void setDetailsListDto(String detailsListDto) {
        this.detailsListDto = detailsListDto;
    }
    @Column(name ="score",nullable=true)
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    @Column(name ="method_score",nullable=true)
    public String getMethodScore() {
        return methodScore;
    }

    public void setMethodScore(String methodScore) {
        this.methodScore = methodScore;
    }
    @Column(name ="the_weight",nullable=true)
    public String getTheWeight() {
        return theWeight;
    }

    public void setTheWeight(String theWeight) {
        this.theWeight = theWeight;
    }
    @Column(name ="a1",nullable=true)
    public String getA1() {
        return a1;
    }

    public void setA1(String a1) {
        this.a1 = a1;
    }
    @Column(name ="a2",nullable=true)
    public Integer getA2() {
        return a2;
    }

    public void setA2(Integer a2) {
        this.a2 = a2;
    }
    @Column(name ="a3",nullable=true)
    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }
    @Column(name ="a4",nullable=true)
    public String getA4() {
        return a4;
    }

    public void setA4(String a4) {
        this.a4 = a4;
    }
    @Column(name ="a5",nullable=true)
    public String getA5() {
        return a5;
    }

    public void setA5(String a5) {
        this.a5 = a5;
    }
    @Column(name ="a6",nullable=true)
    public String getA6() {
        return a6;
    }

    public void setA6(String a6) {
        this.a6 = a6;
    }

    public HistoricalRecord(Integer id, String moduleId, Integer totalAssesId, String detailsListDto, String score, String methodScore, String theWeight, String a1, Integer a2, String a3, String a4, String a5, String a6) {
        this.id = id;
        this.moduleId = moduleId;
        this.totalAssesId = totalAssesId;
        this.detailsListDto = detailsListDto;
        this.score = score;
        this.methodScore = methodScore;
        this.theWeight = theWeight;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
        this.a6 = a6;
    }

    public HistoricalRecord() {
    }
}
