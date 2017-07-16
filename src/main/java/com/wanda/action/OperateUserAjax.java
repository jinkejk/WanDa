package com.wanda.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.wanda.beans.User;
import com.wanda.service.UserService;
import com.wanda.util.PasswordEncode;

/**
 * 用户操作的异步类
 * @author jinkejk
 *
 */
@Controller("OperateUserAjax")
@Scope("prototype")
public class OperateUserAjax extends ActionSupport {
	
	@Resource
	private UserService userService;
	private String loginName;
	private String resetPasswordResult;
	
	
	public String getLoginName() {
		return loginName;
	}


	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}


	public String getResetPasswordResult() {
		return resetPasswordResult;
	}


	public void setResetPasswordResult(String resetPasswordResult) {
		this.resetPasswordResult = resetPasswordResult;
	}


	/**
	 * 重置密码操作
	 */
	public String resetPassword() throws Exception {
		User user = userService.getUserByLoginName(loginName);
		user.setPassword(PasswordEncode.md5("000", "jinke"));
		
		try{			
			userService.updateUser(user);
		}catch (Exception e) {
			resetPasswordResult = "failed";
		}
		resetPasswordResult = "success";
		
		return "resetPassword";
	}

	
}
