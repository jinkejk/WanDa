package com.wanda.action;

import java.util.Date;

import javax.annotation.Resource;

import com.wanda.util.IsMobile;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wanda.beans.User;
import com.wanda.service.UserService;
import com.wanda.util.PasswordEncode;

@Controller("UserLogin")
@Scope("prototype")  
public class UserLogin extends ActionSupport{

	@Resource
	private UserService userService;
	//获取用户名和密码
	private String userName;
	private String password;
	private String manage;
	private String urlFlag;
	
	public String getUrlFlag() {
		return urlFlag;
	}
	public void setUrlFlag(String urlFlag) {
		this.urlFlag = urlFlag;
	}
	public String getManage() {
		return manage;
	}
	public void setManage(String manage) {
		this.manage = manage;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 *登陆验证
	 */
	
	@Override
	public String execute() throws Exception {
		if(userName==null || password==null){
			ActionContext.getContext().put("message", "用户名或密码不能为空！");
			return "input";
		}
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(userName, PasswordEncode.md5(password, "jinke"));
		try{
			subject.login(token);
			//通过密码认证，进行登陆标志认证
			if(!userService.getLogFlag(userName)){
//				ActionContext.getContext().put("message", "管理员审核中！");
				ActionContext.getContext().put("result", "verify");
				return "input";
			}
			
			//可以通过，更新最近登录
			User user = userService.getUserByLoginName(userName);
			user.setLastLogin(new Date());
			userService.updateUser(user);
			ActionContext.getContext().put("message", "登陆成功！");
			return IsMobile.check(ServletActionContext.getRequest())? "success_mobile":"success";
		}catch(Exception e){
			e.printStackTrace();
			ActionContext.getContext().put("message", "用户名或密码错误！");
			return "input";
		}
	}
	
	/**
	 * 后台登陆
	 * @return
	 */
	public String manageLogin() {
		if(userName==null || password==null){
			ActionContext.getContext().put("message", "用户名或密码不能为空！");
			return "manageInput";
		}
		User user = userService.getUserByLoginName(userName);
		if(user == null){
			ActionContext.getContext().put("message", "用户名或密码错误！");
			return "manageInput";
		}
		//判断是否是后台账户
		if(!user.getRole().getRoleRemark().equals("管理员") && !user.getRole().getRoleRemark().equals("编辑员")){
			//非后台账户
			ActionContext.getContext().put("message", "非管理员账户！");
			return "manageInput";			
		}
		
		//判断密码是否正确
		Subject subject=SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(userName, PasswordEncode.md5(password, "jinke"));
		try{
			subject.login(token);
			//通过密码认证，进行登陆标志认证
			if(!userService.getLogFlag(userName)){
				ActionContext.getContext().put("result", "verify");
				return "manageInput";
			}
			
			//可以通过，更新最近登录
			user.setLastLogin(new Date());
			userService.updateUser(user);
			ActionContext.getContext().put("message", "登陆成功！");
			return (urlFlag.equals("manage"))? "manageFrame":"manage";
		}catch(Exception e){
			e.printStackTrace();
			ActionContext.getContext().put("message", "用户名或密码错误！");
			return "manageInput";
		}
	}
	/**
	 * 退出登录
	 */
	public String logout(){
		Subject subject=SecurityUtils.getSubject();
		subject.logout();
		
		return "logout";
	}
}
