/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年06月03日
 * 修改日期：2020年06月03日
 */
package com.sdzk.buss.web.aqbzh.pojo.bo;

import com.sdzk.buss.web.aqbzh.util.StringUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.text.DecimalFormat;

/**
 * @Description Bean
 * @author：Tony
 * @version：V1.0
 * @since：2020年06月03日
 */
@Entity
@Table(name = "total_asses", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class TotalAsses implements java.io.Serializable {

    /**
     * @Description 主键
     */
    private Integer id;
    /**
     * @Description 年
     */
    private String year;
    /**
     * @Description 季度
     */
    private String month;
    /**
     * @Description 模块id
     */
    private Integer moduleId;
    /**
     * @Description 打分
     */
    private String playScore;
    /**
     * @Description 得分
     */
    private String score;

    private String scoreName;

    private String monthName;
    /**
     * @Description 排序
     */
    private Integer a1;
    /**
     * @Description 是否结束打分
     */
    private Integer a2;
    /**
     * @Description
     */
    private String a3;
    /**
     * @Description
     */
    private String a4;
    /**
     * @Description
     */
    private String a5;

    private String scores;
    @Transient
    public String getScores() {
        if (StringUtil.isNotBlank(this.getScore())) {
            DecimalFormat df = new DecimalFormat("0.00");
            scores = df.format(Double.parseDouble(this.getScore()));
        }
        return scores;
    }
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
    @Column(name ="year",nullable=true)
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    @Column(name ="month",nullable=true)
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
    @Column(name ="module_id",nullable=true)
    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }
    @Column(name ="play_score",nullable=true)
    public String getPlayScore() {
        return playScore;
    }

    public void setPlayScore(String playScore) {
        this.playScore = playScore;
    }
    @Column(name ="score",nullable=true)
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
    @Transient
    public String getScoreName() {
        return scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }
    @Transient
    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }
    @Column(name ="a1",nullable=true)
    public Integer getA1() {
        return a1;
    }

    public void setA1(Integer a1) {
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

    public void setScores(String scores) {
        this.scores = scores;
    }

    public TotalAsses(Integer id, String year, String month, Integer moduleId, String playScore, String score, String scoreName, String monthName, Integer a1, Integer a2, String a3, String a4, String a5, String scores) {
        this.id = id;
        this.year = year;
        this.month = month;
        this.moduleId = moduleId;
        this.playScore = playScore;
        this.score = score;
        this.scoreName = scoreName;
        this.monthName = monthName;
        this.a1 = a1;
        this.a2 = a2;
        this.a3 = a3;
        this.a4 = a4;
        this.a5 = a5;
        this.scores = scores;
    }

    public TotalAsses() {
    }
}
