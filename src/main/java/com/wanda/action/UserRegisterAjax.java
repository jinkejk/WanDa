package com.wanda.action;

import javax.annotation.Resource;

import org.apache.struts2.json.annotations.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.wanda.service.UserService;

@Controller("UserRegisterAjax")
@Scope("prototype")
public class UserRegisterAjax extends ActionSupport {
	
	@Resource
	private UserService userService;
	private String nameResult;
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@JSON(name="nameResult")
	public String getNameResult() {
		return nameResult;
	}

	public void setNameResult(String nameResult) {
		this.nameResult = nameResult;
	}

	/**
	 * 判断用户名是否存在
	 */
	@Override
	public String execute() throws Exception {
		//判断用户是否存在
		if(userService.getUserByLoginName(userName) != null)
		    nameResult = "exist";
		else
			nameResult = "not exist";
		
		return "success";
	}
	
	
}
