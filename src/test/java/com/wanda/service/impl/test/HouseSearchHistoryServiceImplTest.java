package com.wanda.service.impl.test;

import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wanda.beans.HouseSearchHistory;
import com.wanda.service.HouseSearchHistoryService;

public class HouseSearchHistoryServiceImplTest {
	
	private static HouseSearchHistoryService houseSearchHistoryService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try{
			@SuppressWarnings("resource")
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			houseSearchHistoryService = (HouseSearchHistoryService) applicationContext.getBean("houseSearchHistoryService");
		}catch(RuntimeException e){
			e.printStackTrace();
		}
	}

	@Test
	public void testGetHouseSearchHistoryById() {
		System.out.println(houseSearchHistoryService.getHouseSearchHistoryById(2));
	}

	@Test
	public void testAddHouseSearchHistory() {		
		HouseSearchHistory houseSearchHistory = new HouseSearchHistory();
		houseSearchHistory.setSearchDate(new Date());
		houseSearchHistoryService.addHouseSearchHistory(houseSearchHistory);
	}

	@Test
	public void testDeleteHouseSearchHistory() {
		houseSearchHistoryService.deleteHouseSearchHistory(houseSearchHistoryService.getHouseSearchHistoryById(2));
		}

}
