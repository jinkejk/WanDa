package com.wanda.beans;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="signMaterial")
public class SignMaterial {
	@Id @Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer SMId;
	//标题
	private String title;
	//简介
	private String remark;	
	//类别，存二级类，若不存在二级分类，则存为一级分类
	@ManyToOne(targetEntity=TrainingMaterialsCategory.class)
	@JoinColumn(name="TMCId")
	private TrainingMaterialsCategory category;
	//创建人
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="authorId")
	private User author;
	//下载次数
	@Column(nullable=false,columnDefinition="INT default 0")
	private int clickNum;
	//文件名称，包括后缀
	private String signFile;
	//最近访问日期
	@Temporal(TemporalType.TIMESTAMP)
	private Date visitDate;
	//修改日期
	@Temporal(TemporalType.TIMESTAMP)
	private Date alterDate;
	//创建日期
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	//最近修改者
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="lastAlterId")
	private User lastAlter;	
	//密级
	@ManyToOne(targetEntity=SecurityLevel.class)
	@JoinColumn(name="securityLevelId")
	private SecurityLevel securityLevel;
	public Integer getSMId() {
		return SMId;
	}
	public void setSMId(Integer sMId) {
		SMId = sMId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public TrainingMaterialsCategory getCategory() {
		return category;
	}
	public void setCategory(TrainingMaterialsCategory category) {
		this.category = category;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public int getClickNum() {
		return clickNum;
	}
	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
	}
	public String getSignFile() {
		return signFile;
	}
	public void setSignFile(String signFile) {
		this.signFile = signFile;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public Date getAlterDate() {
		return alterDate;
	}
	public void setAlterDate(Date alterDate) {
		this.alterDate = alterDate;
	}
	public User getLastAlter() {
		return lastAlter;
	}
	public void setLastAlter(User lastAlter) {
		this.lastAlter = lastAlter;
	}
	public SecurityLevel getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(SecurityLevel securityLevel) {
		this.securityLevel = securityLevel;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "SignMaterial [SMId=" + SMId + ", title=" + title + ", remark=" + remark + ", category=" + category
				+ ", author=" + author + ", clickNum=" + clickNum + ", signFile=" + signFile + ", visitDate="
				+ visitDate + ", alterDate=" + alterDate + ", lastAlter=" + lastAlter + ", securityLevel="
				+ securityLevel + "]";
	}


}
