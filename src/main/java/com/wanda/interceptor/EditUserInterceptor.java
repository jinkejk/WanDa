package com.wanda.interceptor;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.StrutsStatics;
import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.wanda.beans.Role;
import com.wanda.beans.User;
import com.wanda.service.RoleService;
import com.wanda.service.UserService;

/**
 * 修改用户的拦截器
 * 主要功能是将user和roles数据保存起来
 * 防止验证失败返回input视图无数据
 * @author jinkejk
 *
 */
@Component("EditUserInterceptor")
public class EditUserInterceptor extends MethodFilterInterceptor {

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest request= (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);

		//获取user
		Integer userId = Integer.parseInt(request.getParameter("userId"));		
		User user = userService.getUserById(userId);
		
		//获取所有roles
		Set<Role> roles = roleService.getAllRoles();
		
		//存入request
		user.setPosition(request.getParameter("position"));
		user.setDept(request.getParameter("dept"));
		user.setCompany(request.getParameter("company"));
		user.setTelephone(request.getParameter("telephone"));
		user.setQQ(request.getParameter("QQ"));
		ctx.put("remark", request.getParameter("remark"));		
		ctx.put("user", user);
		ctx.put("roles", roles);
		
		return invocation.invoke();
	}

}
