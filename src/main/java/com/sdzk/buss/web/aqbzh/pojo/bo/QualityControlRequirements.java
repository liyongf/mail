/**
 * 版权所属： 中矿安华
 * 作者：唐参
 * 版本：V1.0
 * 创建日期：2020年05月27日
 * 修改日期：2020年05月27日
 */
package com.sdzk.buss.web.aqbzh.pojo.bo;


import com.alibaba.fastjson.JSON;
import com.sdzk.buss.web.aqbzh.util.StringUtil;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * @Description Bean
 * @author：Tony
 * @version：V1.0
 * @since：2020年05月27日
 */
@Entity
@Table(name = "quality_control_requirements", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class QualityControlRequirements implements java.io.Serializable{

	/**
	 * @Description 主键
	 */
	private Integer id;
	/**
	 * @Description 基本要求
	 */
	private String requirements;
	/**
	 * @Description 标准分值
	 */
	private String standardScore;
	/**
	 * @Description 评分方法
	 */
	private String scoreMethod;
	/**
	 * @Description 层级  1 一层  2 二层
	 */
	private String level;
	/**
	 * @Description 层级类型  1层的默认 9999999  2层的给对用的code
	 */
	private String levelType;
	/**
	 * @Description 项目内容id
	 */
	private Integer a1;
	/**
	 * @Description 排序
	 */
	private Integer a2;
	/**
	 * @Description 是否考核
	 */
	private Integer a3;
	/**
	 * @Description 附加项标识
	 */
	private String a4;

	/**
	 * @Description 标准分值合并
	 */
	private String a5;

	private String a6;

	private String a7;

	/**
	 * 层级 1 一层  2二层
	 */
	private String levelName;
	/**
	 * 层级类型
	 */
	private String levelTypeName;


	String data3026 = "[{'codDesc':'一层','code':'30260001'},{'codDesc':'二层','code':'30260002'}]";

	String data3027 = "[{'codDesc':'板式给料机','code':'30270004'}," +
			"{'codDesc':'润滑系统','code':'30270005'},{'codDesc':'卸料口挡车器','code':'30270002'},{'codDesc':'减速机','code':'30270003'},{'codDesc':'清料','code':'30270001'},{'codDesc':'液压系统','code':'30270006'}]";


	@Transient
	public String getLevelName (){
		if (StringUtil.isNotBlank(level)) {
			List<Map<String, Object>> vendors = (List<Map<String, Object>>) JSON.parse(data3026);
			for (int i=0;i<vendors.size();i++) {
				if(vendors.get(i).get("code").equals(level)) {
					levelName = (String) vendors.get(i).get("codDesc");
					break;
				}
			}
		} else {
			levelName = "";
		}
		return levelName;
	}
	@Transient
	public String getLevelTypeName (){
		if (StringUtil.isNotBlank(levelType)) {
			List<Map<String, Object>> vendors = (List<Map<String, Object>>) JSON.parse(data3027);
			for (int i=0;i<vendors.size();i++) {
				if(vendors.get(i).get("code").equals(levelType)) {
					levelTypeName = (String) vendors.get(i).get("codDesc");
					break;
				}
			}
		} else {
			levelTypeName = "无";
		}
		return levelTypeName;
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
	@Column(name ="requirements",nullable=true)
	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}
	@Column(name ="standard_score",nullable=true)
	public String getStandardScore() {
		return standardScore;
	}

	public void setStandardScore(String standardScore) {
		this.standardScore = standardScore;
	}
	@Column(name ="score_method",nullable=true)
	public String getScoreMethod() {
		return scoreMethod;
	}

	public void setScoreMethod(String scoreMethod) {
		this.scoreMethod = scoreMethod;
	}
	@Column(name ="level",nullable=true)
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	@Column(name ="level_type",nullable=true)
	public String getLevelType() {
		return levelType;
	}

	public void setLevelType(String levelType) {
		this.levelType = levelType;
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
	public Integer getA3() {
		return a3;
	}

	public void setA3(Integer a3) {
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
	@Column(name ="a7",nullable=true)
	public String getA7() {
		return a7;
	}

	public void setA7(String a7) {
		this.a7 = a7;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public void setLevelTypeName(String levelTypeName) {
		this.levelTypeName = levelTypeName;
	}

	public QualityControlRequirements(Integer id, String requirements, String standardScore, String scoreMethod, String level, String levelType, Integer a1, Integer a2, Integer a3, String a4, String a5, String a6, String a7, String levelName, String levelTypeName) {
		this.id = id;
		this.requirements = requirements;
		this.standardScore = standardScore;
		this.scoreMethod = scoreMethod;
		this.level = level;
		this.levelType = levelType;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.a4 = a4;
		this.a5 = a5;
		this.a6 = a6;
		this.a7 = a7;
		this.levelName = levelName;
		this.levelTypeName = levelTypeName;
	}

	public QualityControlRequirements() {
	}
}
