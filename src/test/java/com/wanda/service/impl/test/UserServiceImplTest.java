package com.wanda.service.impl.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wanda.beans.Permission;
import com.wanda.beans.Role;
import com.wanda.beans.Solution;
import com.wanda.beans.User;
import com.wanda.service.UserService;
import com.wanda.util.PasswordEncode;


public class UserServiceImplTest {

	private static UserService userService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		try{
			@SuppressWarnings("resource")
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			userService = (UserService) applicationContext.getBean("userService");
		}catch(RuntimeException e){
			e.printStackTrace();
		}

	}
	
	@Test
	public void testGetUserByLoginName() {	
		System.out.println(userService.getUserNumByContentByPage("金"));
	}

	@Test
	public void testGetUserById() {
//		System.out.println(userService.getUserById(4));
	}

	@Test
	public void testGetUserByTrueName() {
//		System.out.println(userService.getUserByTrueName("金科"));
	}

	@Test
	public void testAddUser() {
		for(int i=10; i<40; i++){			
			User user = new User();
			user.setLoginName("testt" + i);
			user.setTrueName("测试");
			user.setPassword(PasswordEncode.md5("123", "jinke"));
			user.setRegisterTime(new Date());
			user.setRole(new Role(5));
			user.setLogFlag(true);
			userService.addUser(user);			
		}
	}

	@Test
	public void testDeleteUserById() {
//			userService.deleteUserById(30);
	}

	@Test
	public void testGetSolutions() {
//		Set<Solution> alterSolutions = userService.getAlterSolutions("jinke");
//		
//		//输出的时候正式加载
//		System.out.println("alterSolutions:" + alterSolutions);
	}

	@Test
	public void testGetUsersByPage(){
//		List<User> users = userService.getUsersByPage(15, 2);
//		
//		for(User user: users){
//			System.out.println("user:" + user);
//		}
	}
	
	@Test
	public void testGetTotalUserNum(){
//		System.out.println(userService.getTotalUserNum());
	}
	
	@Test
    public void testUpdateUser(){
//		User user = userService.getUserById(3);
//		user.setLastLogin(new Date());
//		
//		userService.updateUser(user);
	}
	
	@Test
	public void testgetLogFlag(){
		List<Integer> ids = new ArrayList<>();
		ids.add(90);
		ids.add(91);
		ids.add(92);
		ids.add(93);
		ids.add(94);
		ids.add(95);
		ids.add(96);
		userService.changeUsersLogflag(ids);
	}
	
	@Test
	public void testgetPermissionsByUserName(){
		List<User> users = userService.getUsersByContentByPage("金", 6, 1);
		
		for(User user: users){
			System.out.println(user);
		}
	}
}
