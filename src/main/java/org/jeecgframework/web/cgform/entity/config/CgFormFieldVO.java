package org.jeecgframework.web.cgform.entity.config;

import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelTarget;



/**   
 * @Title: Entity
 * @Description: 自动生成表的列属性
 * @author jueyue
 * @date 2013-06-30 11:37:32
 * @version V1.0   
 *
 */
@ExcelTarget("cgFormFieldVO")
public class CgFormFieldVO implements java.io.Serializable {
	private static final long serialVersionUID = 8248068871232905945L;
	/**id*/
	private String id;
	/**字段名称*/
	@Excel(name="字段名称",orderNum="1")
	private String fieldName;
	/**功能注释*/
	@Excel(name="字段备注",orderNum="2")
	private String content;
	/**字段类型*/
	@Excel(name="字段类型",orderNum="3")
	private String type;
	/**字段长度*/
	@Excel(name="字段长度",orderNum="4")
	private String length;
	/**小数点长度*/
	@Excel(name="小数点长度",orderNum="5")
	private String pointLength;
	/**默认值*/
	@Excel(name="默认值",orderNum="6")
	private String fieldDefault;
	/**是否允许空值*/
	@Excel(name="允许空值",orderNum="7")
	private String isNull;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getPointLength() {
		return pointLength;
	}
	public void setPointLength(String pointLength) {
		this.pointLength = pointLength;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIsNull() {
		return isNull;
	}
	public void setIsNull(String isNull) {
		this.isNull = isNull;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getFieldDefault() {
		return fieldDefault;
	}
	public void setFieldDefault(String fieldDefault) {
		this.fieldDefault = fieldDefault;
	}
	@Override
	public String toString() {
		return "CgFormFieldVO [id=" + id + ", fieldName=" + fieldName
				+ ", content=" + content + ", type=" + type + ", length="
				+ length + ", pointLength=" + pointLength + ", fieldDefault="
				+ fieldDefault + ", isNull=" + isNull + "]";
	}

}
