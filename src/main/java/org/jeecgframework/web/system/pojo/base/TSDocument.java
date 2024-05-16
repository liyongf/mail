package org.jeecgframework.web.system.pojo.base;

import javax.persistence.*;

import com.sdzk.buss.web.dangersource.entity.TBDocumentSourceEntity;

/**
 * 文档下载,新闻,法规表
 * @author  张代浩
 */
@Entity
@Table(name = "t_s_document")
@PrimaryKeyJoinColumn(name = "id")
public class TSDocument extends TSAttachment implements java.io.Serializable {
    //文档标题
	private String documentTitle;
    //焦点图导航
	private byte[] pictureIndex;
    //状态：0未发布，1已发布
	private Short documentState;
    //是否首页显示
	private Short showHome;
    //文档分类
	private TSType TSType;
    private Integer status;

    private TBDocumentSourceEntity documentSource;
    private String countDangerSrc;

    @Column(name = "status", length = 100)
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "typeid")
	public TSType getTSType() {
		return TSType;
	}
	public void setTSType(TSType tSType) {
		TSType = tSType;
	}
	@Column(name = "documenttitle", length = 100)
	public String getDocumentTitle() {
		return documentTitle;
	}
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	@Column(name = "pictureindex",length=3000)
	public byte[] getPictureIndex() {
		return pictureIndex;
	}
	public void setPictureIndex(byte[] pictureIndex) {
		this.pictureIndex = pictureIndex;
	}
	@Column(name = "documentstate")
	public Short getDocumentState() {
		return documentState;
	}
	public void setDocumentState(Short documentState) {
		this.documentState = documentState;
	}
	@Column(name = "showhome")
	public Short getShowHome() {
		return showHome;
	}
	public void setShowHome(Short showHome) {
		this.showHome = showHome;
	}

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_source")
    public TBDocumentSourceEntity getDocumentSource(){
        return  this.documentSource;
    }

    public void setDocumentSource(TBDocumentSourceEntity documentSource){
        this.documentSource = documentSource;
    }

    @Transient
    public String getCountDangerSrc(){
        return this.countDangerSrc;
    }

    public void setCountDangerSrc(String countDangerSrc){
        this.countDangerSrc = countDangerSrc;
    }
}