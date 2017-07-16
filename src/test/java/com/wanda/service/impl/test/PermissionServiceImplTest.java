package com.wanda.service.impl.test;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wanda.beans.Permission;
import com.wanda.service.PermissionService;

public class PermissionServiceImplTest {

	private static PermissionService permissionService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try{
			@SuppressWarnings("resource")
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			permissionService = (PermissionService) applicationContext.getBean("permissionService");
		}catch(RuntimeException e){
			e.printStackTrace();
		}
	}

	@Test
	public void testGetPermissionById() {
//		System.out.println(permissionService.getPermissionById(1));
    }

	@Test
	public void testGetPermissionByName() {
//		System.out.println(permissionService.getPermissionByName("user:read"));
	}

	@Test
	public void testAddPermission() {
//		Permission permission = new Permission();
//		permission.setPermissionName("user:add");
//		permission.setPermissionRemark("添加");
//		permissionService.addPermission(permission);
	}

	@Test
	public void testDeletePermissionById() {
//		permissionService.deletePermissionById(2);
	}

}
