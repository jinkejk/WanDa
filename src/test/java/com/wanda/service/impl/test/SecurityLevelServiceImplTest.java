package com.wanda.service.impl.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wanda.dao.SecurityLevelDao;
import com.wanda.service.SecurityLevelService;

public class SecurityLevelServiceImplTest {

	private static SecurityLevelService securityLevelService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		try{
			@SuppressWarnings("resource")
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			securityLevelService = (SecurityLevelService) applicationContext.getBean("securityLevelService");
		}catch(RuntimeException e){
			e.printStackTrace();
		}

	}
	
	@Test
	public void testGetSecurityLevelById() {
//		System.out.println(securityLevelService.getSecurityLevelById(1));
	}
	
	@Test
	public void testGetSecurityLevelByName() {
//		System.out.println(securityLevelService.getSecurityLevelByName("密级1"));
	}

	@Test
	public void testGetAllSecurityLevels() {
//		System.out.println(securityLevelService.getAllSecurityLevels());
	}
	
}
