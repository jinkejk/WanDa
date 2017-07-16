package com.wanda.action;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wanda.beans.Role;
import com.wanda.beans.User;
import com.wanda.service.UserService;
import com.wanda.util.PasswordEncode;

@Controller("UserRegister")
@Scope("prototype")
public class UserRegister extends ActionSupport{

	@Resource
	private UserService userService;

	//获取用户名和密码
	private String userName;
	private String trueName;
	private String password;
	private String position;
	private String dept;
	private String company;
	private String telephone;
	private String QQ;
	private String remark;	
    private Integer roleId;   
    private boolean logFlag;   

	public boolean isLogFlag() {
		return logFlag;
	}


	public void setLogFlag(boolean logFlag) {
		this.logFlag = logFlag;
	}


	public Integer getRoleId() {
		return roleId;
	}


	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
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


	public String getPosition() {
		return position;
	}


	public void setPosition(String position) {
		this.position = position;
	}


	public String getDept() {
		return dept;
	}


	public void setDept(String dept) {
		this.dept = dept;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getTelephone() {
		return telephone;
	}


	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	public String getQQ() {
		return QQ;
	}


	public void setQQ(String qQ) {
		QQ = qQ;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	/**
	 * 用户注册
	 */
	@Override
	public String execute(){
		//创建用户
		User user = new User();
		user.setLoginName(userName);
		user.setTrueName(trueName);
		user.setPassword(PasswordEncode.md5(password, "jinke"));
		user.setPosition(position);
		user.setRemark(remark);
		user.setCompany(company);
		user.setTelephone(telephone);
		user.setDept(dept);
		user.setQQ(QQ);
		user.setRegisterTime(new Date());
		user.setRole(new Role(5));
        user.setLogFlag(false);
        
		//添加用户
        try{
        	userService.addUser(user);
    		ActionContext.getContext().put("registerResult", "success");
    		ActionContext.getContext().put("userName", userName);
        }catch (Exception e) {
        	ActionContext.getContext().put("registerResult", "failed");
		}

		return "success";
	}

	/**
	 * 管理员添加用户
	 */
	public String addUser(){
		//创建用户
		User user = new User();	
		user.setLoginName(userName);
		user.setTrueName(trueName);
		user.setPassword(PasswordEncode.md5(password, "jinke"));
		user.setPosition(position);
		user.setRemark(remark);
		user.setCompany(company);
		user.setTelephone(telephone);
		user.setDept(dept);
		user.setQQ(QQ);
		user.setRegisterTime(new Date());
		user.setRole(new Role(roleId));
		user.setLogFlag(logFlag);
		
		//添加用户
		userService.addUser(user);
		return "add";
	}

}
