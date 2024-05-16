package com.sdzk.buss.web.mapmanage.entity;

import com.sdzk.buss.web.common.Constants;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.web.system.pojo.base.TSUser;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_b_map_manage", schema = "")
@SuppressWarnings("serial")
public class TBMapManageEntity implements java.io.Serializable {
    private String id;
    private String filePath;
    private Date uploadTime;
    private String uploadBy;
    private String uploadName;
    private String isUsed;
    private String isDelete;
    private String uploadType;
    private String status;

    public TBMapManageEntity(){

    }

    public TBMapManageEntity(String filePath,TSUser user,String uploadType){
        this.filePath = filePath;
        this.uploadTime = new Date();
        this.uploadBy = user.getUserName();
        this.uploadName = user.getRealName();
        this.isDelete = Constants.IS_DELETE_N;
        this.isUsed = "0";
        this.uploadType = uploadType;
    }

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="ID",nullable=false,length=36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name ="FILE_PATH",nullable=true,length=300)
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Column(name ="UPLOAD_TIME",nullable=false)
    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Column(name ="UPLOAD_BY",nullable=true,length=36)
    public String getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(String uploadBy) {
        this.uploadBy = uploadBy;
    }

    @Column(name ="UPLOAD_NAME",nullable=true,length=100)
    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    @Column(name ="IS_USED",nullable=true,length=5)
    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    @Column(name ="IS_DELETE",nullable=true,length=5)
    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    @Column(name ="UPLOAD_TYPE",nullable=true,length=36)
    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    @Column(name ="STATUS",nullable=true,length=5)
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
