package com.wanda.service.impl.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wanda.beans.Permission;
import com.wanda.beans.Role;
import com.wanda.beans.SecurityLevel;
import com.wanda.service.RoleService;

public class RoleServiceImplTest {

	private static RoleService roleService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try{
			@SuppressWarnings("resource")
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			roleService = (RoleService) applicationContext.getBean("roleService");
		}catch(RuntimeException e){
			e.printStackTrace();
		}
	}

	@Test
	public void testGetRoleById() {
//		System.out.println(roleService.getRoleById(1));	
	}

	@Test
	public void testGetRoleByRoleName() {
//		System.out.println(roleService.getRoleByRoleName("经理"));
	}

	@Test
	public void testAddRole() {
//		Role role = new Role();
//		role.setRoleName("主管");
//		role.setRoleRemark("sdasdas");
//		SecurityLevel securityLevel = new SecurityLevel();
//		securityLevel.setSecurityLevelID(2);
//		role.setSecurityLevel(securityLevel);
//		Permission permission1 = new Permission();
//		permission1.setPermissionName("经理");
//		Permission permission2 = new Permission();
//		permission2.setPermissionName("主管");
//		Set<Permission> permissions = new HashSet<Permission>();
//		permissions.add(permission1);
//		permissions.add(permission2);
//		role.setPermissions(permissions);
//		roleService.addRole(role);
	}

	@Test
	public void testDeleteRoleById() {
//		roleService.deleteRoleById(3);
	}

	@Test
	public void testAddPermissionById(){
//		roleService.addPermissionById(1, 7);
//		roleService.addPermissionById(2, 7);
	}
	
	@Test
	public void testGetAllRoles(){
//		Set<Role> roles = roleService.getAllRoles();
//		
//		for(Role role: roles){
//			System.out.println(role);
//		}
	}
}
