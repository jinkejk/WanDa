package com.wanda.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 培训资料的分类表
 * @author jinkejk
 *
 */
@Entity
@Table(name="trainingMaterialsCategory")
public class TrainingMaterialsCategory {
	@Id @Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer TMCId;	
	private String TMCName;
	//分类名的介绍
	private String remark;
	//父分类，若不存在则为null
	@ManyToOne(targetEntity=TrainingMaterialsCategory.class)  
    @JoinColumn(name = "parentTMCId")
	private TrainingMaterialsCategory parentTMC;
	//属于哪个模块：培训资料，签批资料，解决方案
	private String module;	
	
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Integer getTMCId() {
		return TMCId;
	}
	public void setTMCId(Integer tMCId) {
		TMCId = tMCId;
	}
	public String getTMCName() {
		return TMCName;
	}
	public void setTMCName(String tMCName) {
		TMCName = tMCName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public TrainingMaterialsCategory getParentTMC() {
		return parentTMC;
	}
	public void setParentTMC(TrainingMaterialsCategory parentTMC) {
		this.parentTMC = parentTMC;
	}
	
	@Override
	public String toString() {
		return "TrainingMaterialsCategory [TMCId=" + TMCId + ", TMCName=" + TMCName + ", Rermark=" + remark
				+ ", parentTMC=" + parentTMC + ", module=" + module + "]";
	}	
	
}











