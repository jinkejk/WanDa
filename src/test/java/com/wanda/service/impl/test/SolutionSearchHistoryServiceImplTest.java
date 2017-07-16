package com.wanda.service.impl.test;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wanda.beans.SolutionSearchHistory;
import com.wanda.beans.User;
import com.wanda.service.SolutionSearchHistoryService;


public class SolutionSearchHistoryServiceImplTest {

	private static SolutionSearchHistoryService solutionSearchHistoryService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		try{
			@SuppressWarnings("resource")
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			solutionSearchHistoryService = (SolutionSearchHistoryService) applicationContext.getBean("solutionSearchHistoryService");
		}catch(RuntimeException e){
			e.printStackTrace();
		}

	}

	@Test
	public void testGetHistoryByUserId() {
//		System.out.println(solutionSearchHistoryService.getHistoryByUserId(2));	
	}

	@Test
	public void testGetHistoryById() {
//		System.out.println(solutionSearchHistoryService.getHistoryById(1));	
    }

	@Test
	public void testAddSolutionSearchHistory() {
//		SolutionSearchHistory solutionSearchHistory = new SolutionSearchHistory();
//		solutionSearchHistory.setKeyword("jinke ");
//		solutionSearchHistory.setSearchDate(new Date());
//		User user = new User();
//		user.setUserId(2);
//		solutionSearchHistory.setUser(user);
//		solutionSearchHistoryService.addSolutionSearchHistory(solutionSearchHistory);
	}

	@Test
	public void testDeleteSolutionSearchHistory() {
		List<String> historys = solutionSearchHistoryService.getLastSearchContent(4);
		for(String history: historys){
			System.out.println(history);
		}
	}

	@Test
	public void testUpdateSolutionSearchHistory() {
//		SolutionSearchHistory solutionSearchHistory = solutionSearchHistoryService.getHistoryById(1);
//		solutionSearchHistory.setKeyword("update word");
		
//		solutionSearchHistoryService.updateSolutionSearchHistory(solutionSearchHistory);
	}

}
