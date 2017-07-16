package com.wanda.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * 解决方案实体类
 * @author jinkejk
 *
 */
@Entity
@Table(name="solution")
public class Solution {
	@Id @Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer solutionId;
	@Column(columnDefinition="Text")
	private String title;
	@Column(columnDefinition="LongText")
	private String solution;
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="authorId")
	private User author;
	//创建日期
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	//访问次数
	@Column(nullable=false,columnDefinition="INT default 0")
	private int clickNum;
	//最近访问日期
	@Temporal(TemporalType.TIMESTAMP)
	private Date visitDate;
	//修改日期
	@Temporal(TemporalType.TIMESTAMP)
	private Date alterDate;
	//最近修改者
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="lastAlterId")
	private User lastAlter;	
	//密级
	@ManyToOne(targetEntity=SecurityLevel.class)
	@JoinColumn(name="securityLevelId")
	private SecurityLevel securityLevel;
	//类别，存二级类，若不存在二级分类，则存为一级分类
	@ManyToOne(targetEntity=TrainingMaterialsCategory.class)
	@JoinColumn(name="TMCId")
	private TrainingMaterialsCategory category;

	public TrainingMaterialsCategory getCategory() {
		return category;
	}
	public void setCategory(TrainingMaterialsCategory category) {
		this.category = category;
	}
	public SecurityLevel getSecurityLevel() {
		return securityLevel;
	}
	public void setSecurityLevel(SecurityLevel securityLevel) {
		this.securityLevel = securityLevel;
	}
	public Integer getSolutionId() {
		return solutionId;
	}
	public void setSolutionId(Integer solutionId) {
		this.solutionId = solutionId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getClickNum() {
		return clickNum;
	}
	public void setClickNum(int clickNum) {
		this.clickNum = clickNum;
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

	@Override
	public String toString() {
		return "Solution [solutionId=" + solutionId + ", title=" + title  + ", author="
				+ author + ", createDate=" + createDate + ", clickNum=" + clickNum + ", visitDate=" + visitDate
				+ ", alterDate=" + alterDate + ", lastAlter=" + lastAlter + ", securityLevel=" + securityLevel + "]";
	}

}
