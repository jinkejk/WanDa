package com.wanda.realm;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import com.wanda.beans.Permission;
import com.wanda.beans.User;
import com.wanda.service.UserService;

/**
 * 自定义realm
 * 获取权限信息和登陆信息
 * @author jinkejk
 *
 */
public class MyRealm extends AuthorizingRealm{

	@Resource
	private UserService userService;

	/**
	 * 设置权限信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		
		//获取角色和权限
		Set<String> rolesString = new HashSet<String>();
		Set<String> permissionsString = new HashSet<String>();
		
		rolesString.add(userService.getUserByLoginName(userName).getRole().getRoleName());
		Set<Permission> permissions = userService.getPermissionsByUserName(userName);
		for(Permission permission: permissions){
			permissionsString.add(permission.getPermissionName());
		}
		
		authorizationInfo.setRoles(rolesString);
		authorizationInfo.setStringPermissions(permissionsString);
		
		return authorizationInfo;
	}

	/**
	 * 设置登陆信息的数据
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName=(String)token.getPrincipal();
		User user=userService.getUserByLoginName(userName);
		if(user!=null){
			AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(user.getLoginName(),user.getPassword(),"xx");
			return authcInfo;
		}else{
			return null;				
		}
	}
}
