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

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="drawing")
public class Drawing {
	@Id @Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer drawingId;
	@Column(unique=true)
	private String stringId;
	private String drawingName;
	//更新日期
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;
	//图纸说明少于1000字
	@Column(length=1000)
	private String remark;
	//密级
	@ManyToOne(targetEntity=SecurityLevel.class)
	@JoinColumn(name="securityLevelId")
	private SecurityLevel securityLevel;
	//修改日期
	@Temporal(TemporalType.TIMESTAMP)
	private Date alterDate;
	//上传人
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="authorId")
	private User author;
	//最近修改者
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="lastAlterId")
	private User lastAlter;	
	//图片类型
	private String imgName;
	//唯一的fileId
	private String fieldId;
		
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getImgName() {
		return imgName;
	}
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public User getLastAlter() {
		return lastAlter;
	}
	public void setLastAlter(User lastAlter) {
		this.lastAlter = lastAlter;
	}
	public Integer getDrawingId() {
		return drawingId;
	}
	public void setDrawingId(Integer drawingId) {
		this.drawingId = drawingId;
	}
	public String getStringId() {
		return stringId;
	}
	public void setStringId(String stringId) {
		this.stringId = stringId;
	}
	public String getDrawingName() {
		return drawingName;
	}
	public void setDrawingName(String drawingName) {
		this.drawingName = drawingName;
	}
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public SecurityLevel getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(SecurityLevel securityLevel) {
		this.securityLevel = securityLevel;
	}
	public Date getAlterDate() {
		return alterDate;
	}
	public void setAlterDate(Date alterDate) {
		this.alterDate = alterDate;
	}
	@Override
	public String toString() {
		return "Drawing [drawingId=" + drawingId + ", stringId=" + stringId + ", drawingName=" + drawingName
				+ ", uploadDate=" + uploadDate + ", remark=" + remark + ", securityLevel=" + securityLevel
				+ ", alterDate=" + alterDate + ", author=" + author + ", lastAlter=" + lastAlter + ", imgName="
				+ imgName + ", fieldId=" + fieldId + "]";
	}
	
	
}
