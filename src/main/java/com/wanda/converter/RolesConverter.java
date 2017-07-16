package com.wanda.converter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.util.StrutsTypeConverter;

import com.wanda.beans.Role;

/**
 * 将roles转换为set集合
 * @author jinkejk
 * 未使用
 */
public class RolesConverter extends StrutsTypeConverter{

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		Set<Role> roles = new HashSet<Role>();
		for(int i=0; i<values.length; i++){
			Role role = new Role();
			String[] roleValues = values[i].split(",");
			
			//设置值
			role.setRoleId(Integer.parseInt(roleValues[0]));
			role.setRoleName(roleValues[1]);
			role.setRoleRemark(roleValues[2]);
			
			roles.add(role);
		}
		return roles;
	}

	@Override
	public String convertToString(Map context, Object o) {
		if(o.getClass() == Set.class){
			Set<Role> roles = (Set<Role>) o;
			String result = "[";
			
			for(Object obj: roles){
				Role role = (Role) obj;
				result += "<" + role.getRoleId() + "," + role.getRoleName() + "," + 
				    role.getRoleRemark() + ">";
			}
			
			return result + "]";
		}else
			return "";
	}

}
