package com.wanda.service.impl.test;

import java.util.Date;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.wanda.beans.House;
import com.wanda.beans.User;
import com.wanda.service.HouseService;

public class HouseServiceImplTest {

	private static HouseService houseService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try{
			@SuppressWarnings("resource")
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			houseService = (HouseService) applicationContext.getBean("houseService");
		}catch(RuntimeException e){
			e.printStackTrace();
		}
	}

	@Test
	public void testGetHouseById() {
		System.out.println(houseService.getTotalHouseNum());
	}

	@Test
	public void testVisitHouseById() {
		System.out.println(houseService.visitHouseById(98, new User(32)));
	}

	@Test
	public void testGetHousesByPage() {
		List<House> houses = houseService.getHousesByPage(8, 8);
		for(House house: houses){
			System.out.println(house);
		}
	}

	@Test
	public void testDeleteHouseById() {
		houseService.deleteHouseById(99);
	}

	@Test
	public void testAddHouse() {
		for(int i=0; i<33; i++){
			House house = new House();
			house.setCreateDate(new Date());
			house.setHasSolar("t");
			house.setAuthor(new User(32));
			houseService.addHouse(house);
		}
	}

	@Test
	public void testUpdateHouse() {
		for(int i=1; i<=98; i++){			
			House house = houseService.getHouseById(i);
			house.setLPName(i+"");
			houseService.updateHouse(house);
		}
	}

	@Test
	public void testgetHousesByContentByPage(){
		List<House> houses = houseService.getHousesByContentByPage("9 1", 35, 1);

		for(House house: houses){
			System.out.println(house);
		}
	}

	@Test
	public void testgetTotalHouseNumByContent(){
		System.out.println(houseService.getTotalHouseNumByContent("9 1"));
	}

	@Test
	public void testgetAllLPName(){
		House newHouse = houseService.getHouseByLPAndHXName("sadaaaaaaaaaaaa", "1asadsssssssss");
		if(newHouse != null){
			//转为json
			newHouse.setAuthor(null);
			newHouse.setLastAlter(null);
			String data = new Gson().toJson(newHouse);
			System.out.println(data);
		}	
	}
}
