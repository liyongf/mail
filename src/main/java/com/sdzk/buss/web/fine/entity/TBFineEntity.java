package com.sdzk.buss.web.fine.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.web.system.pojo.base.TSDepart;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 罚款信息
 * @author onlineGenerator
 * @date 2017-09-26 10:21:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_b_fine", schema = "")
@SuppressWarnings("serial")
public class TBFineEntity implements java.io.Serializable {

    /**唯一标识*/
    private String id;
    /**罚款单号*/
    private String fineNum;
    /**罚款日期*/
    @Excel(name="罚款日期")
    private Date fineDate;
    /**责任单位*/
    private TSDepart dutyUnit;
    /**被罚款人*/
    @Excel(name="被罚款人")
    private String beFinedMan;
    private String beFineManTemp;
    /**罚款人*/
    @Excel(name="罚款人")
    private String fineMan;
    private String fineManTemp;
    /**创建人,录入人*/
    @Excel(name="录入人")
    private String createBy;
    /**创建人名称*/
    private String createName;
    /**创建日期*/
    private Date createDate;
    /**更新人登陆名*/
    private String updateBy;
    /**更新人名称*/
    private String updateName;
    /**更新时间*/
    private Date updateDate;
    /**内容*/
    @Excel(name="内容")
    private String content;
    /**罚款金额*/
    @Excel(name="罚款金额")
    private String fineMoney;
    @Excel(name="责任人罚款金额")
    private String fineMoneyTemp;
    @Excel(name="单位罚款金额")
    private String unitFineMoney;
    /**罚款性质*/
    private String fineProperty;
    /**是否有效*/
    private String isValidity;

    private String fineDateTemp;
    private String createDateTemp;
    @Excel(name="责任单位")
    private String dutyUnitTemp;
    @Excel(name="罚款性质")
    private String finePropertyTemp;


    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  唯一标识
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="id",nullable=false,length=32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  fineNum
     */
    @Column(name ="fine_num",nullable=true,length=32)
    public String getFineNum() {
        return fineNum;
    }

    public void setFineNum(String fineNum) {
        this.fineNum = fineNum;
    }

    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  罚款日期
     */
    @Column(name ="fine_date",nullable=true)
    public Date getFineDate() {
        return fineDate;
    }

    public void setFineDate(Date fineDate) {
        this.fineDate = fineDate;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  责任单位
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DUTY_UNIT")
    @NotFound(action= NotFoundAction.IGNORE)
    public TSDepart getDutyUnit() {
        return dutyUnit;
    }

    public void setDutyUnit(TSDepart dutyUnit) {
        this.dutyUnit = dutyUnit;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  被罚款人
     */
    @Column(name ="be_fined_man",nullable=true,length=36)
    public String getBeFinedMan() {
        return beFinedMan;
    }

    public void setBeFinedMan(String beFinedMan) {
        this.beFinedMan = beFinedMan;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  罚款人
     */
    @Column(name ="fine_man",nullable=true,length=36)
    public String getFineMan() {
        return fineMan;
    }

    public void setFineMan(String fineMan) {
        this.fineMan = fineMan;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  创建人,录入人
     */
    @Column(name ="create_by",nullable=true,length=50)
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String 创建人名称
     */
    @Column(name ="create_name",nullable=true,length=100)
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String 创建日期
     */
    @Column(name ="create_date",nullable=true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  更新人登录名
     */
    @Column(name ="update_by",nullable=true,length=50)
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  更新人名称
     */
    @Column(name ="update_name",nullable=true,length=100)
    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String 更新时间
     */
    @Column(name ="update_date",nullable=true)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String 内容
     */
    @Column(name ="content",nullable=true,length=2000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 方法: 取得java.lang.String
     * @return: java.lang.String 罚款金额
     * */
    @Column(name ="fine_money",nullable=true,length=30)
     public String getFineMoney() {
        return fineMoney;
    }

    public void setFineMoney(String fineMoney) {
        this.fineMoney = fineMoney;
    }

    /**
     * 方法: 取得java.lang.String
     * @return: java.lang.String 罚款性质
     * */
    @Column(name ="fine_property",nullable=true,length=30)
     public String getFineProperty() {
        return fineProperty;
    }

    public void setFineProperty(String fineProperty) {
        this.fineProperty = fineProperty;
    }

    /**
     * 方法: 取得java.lang.String
     * @return: java.lang.String 是否有效
     * null或者0代表有效，1代表无效
     * */
    @Column(name ="is_validity",nullable=true,length=30)
    public String getIsValidity() {
        return isValidity;
    }

    public void setIsValidity(String isValidity) {
        this.isValidity = isValidity;
    }

    @Transient
    public String getFineDateTemp() {
        return fineDateTemp;
    }
    public void setFineDateTemp(String fineDateTemp) {
        this.fineDateTemp = fineDateTemp;
    }

    @Transient
    public String getFinePropertyTemp() {
        return finePropertyTemp;
    }
    public void setFinePropertyTemp(String finePropertyTemp) {
        this.finePropertyTemp = finePropertyTemp;
    }

    @Transient
    public String getCreateDateTemp() {
        return createDateTemp;
    }
    public void setCreateDateTemp(String createDateTemp) {
        this.createDateTemp = createDateTemp;
    }

    @Transient
    public String getDutyUnitTemp() {
        return dutyUnitTemp;
    }
    public void setDutyUnitTemp(String dutyUnitTemp) {
        this.dutyUnitTemp = dutyUnitTemp;
    }

    @Transient
    public String getFineManTemp() {
        return fineManTemp;
    }

    public void setFineManTemp(String fineManTemp) {
        this.fineManTemp = fineManTemp;
    }
    @Transient
    public String getBeFineManTemp() {
        return beFineManTemp;
    }

    public void setBeFineManTemp(String beFinedManTemp) {
        this.beFineManTemp = beFinedManTemp;
    }
    @Transient
    public String getFineMoneyTemp() {
        return fineMoneyTemp;
    }

    public void setFineMoneyTemp(String fineMoneyTemp) {
        this.fineMoneyTemp = fineMoneyTemp;
    }
    @Column(name ="unit_fine_money",nullable=true)
    public String getUnitFineMoney() {
        return unitFineMoney;
    }

    public void setUnitFineMoney(String unitFineMoney) {
        this.unitFineMoney = unitFineMoney;
    }
}
