package com.wanda.beans;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Type;

/**
 * 用户实体类
 * @author jinkejk
 *
 */
@Entity
@Table(name="user")
public class User {
	@Id @Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer userId;
	@Column(nullable=false, unique=true)
	private String loginName;
	private String trueName;
	@Column(nullable=false)
	private String password;
	//备注说明
	private String remark;
	//单位名称
	private String dept;
	private String telephone;
	//最近登录时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLogin;
	//注册时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date registerTime;
	
	private String company;
	//职位
	private String position;
	private String QQ;
	//是否允许登陆
	@Column(columnDefinition="char default false")
	@Type(type="true_false")
	private boolean logFlag;
	
	//专家库搜索记录
	//设置级联删除
	@OneToMany(targetEntity=SolutionSearchHistory.class,mappedBy="user",fetch=FetchType.LAZY)
	@Cascade(value = {CascadeType.REMOVE})  
	private Set<SolutionSearchHistory> solutionSearchHistories = new HashSet<SolutionSearchHistory>();
	
	//对应的角色
	@ManyToOne(targetEntity=Role.class)
	@JoinColumn(name="roleId")
	private Role role;	
	
	public User(){}
	
	public User(Integer userId){
		this.userId = userId;
	}
	
	public boolean getLogFlag() {
		return logFlag;
	}
	public void setLogFlag(boolean logFlag) {
		this.logFlag = logFlag;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Set<SolutionSearchHistory> getSolutionSearchHistories() {
		return solutionSearchHistories;
	}
	public void setSolutionSearchHistories(Set<SolutionSearchHistory> solutionSearchHistories) {
		this.solutionSearchHistories = solutionSearchHistories;
	}	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", loginName=" + loginName + ", trueName=" + trueName + ", password="
				+ password + ", remark=" + remark + ", dept=" + dept + ", telephone=" + telephone + ", lastLogin="
				+ lastLogin + ", registerTime=" + registerTime + ", company=" + company + ", position=" + position
				+ ", QQ=" + QQ + ", role=" + role + "]";
	}	

}
