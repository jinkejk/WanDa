package com.wanda.service.impl.test;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.service.TrainingMaterialsCategoryService;
import com.wanda.util.UtilCommon;

public class TrainingMaterialsCategoryServiceImplTest {

	private static TrainingMaterialsCategoryService trainingMaterialsCategoryService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try{
			@SuppressWarnings("resource")
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			trainingMaterialsCategoryService = (TrainingMaterialsCategoryService) applicationContext.getBean("trainingMaterialsCategoryService");
		}catch(RuntimeException e){
			e.printStackTrace();
		}
	}

	@Test
	public void testAddTrainingMaterialsCategory() {
        for(int i=1;i<20;i++){
        	TrainingMaterialsCategory tmc = new TrainingMaterialsCategory();
        	tmc.setTMCName("test" + i);
        	trainingMaterialsCategoryService.addTrainingMaterialsCategory(tmc);
        }
//		tmc2.setTMCName("child");
//		tmc2.setParentTMC(tmc);
//		trainingMaterialsCategoryService.addTrainingMaterialsCategory(tmc2);
	}

	@Test
	public void testDeleteTrainingMaterialsCategoryById() {
		trainingMaterialsCategoryService.deleteTrainingMaterialsCategoryById(3);
	}

	@Test
	public void testUpdateTrainingMaterialsCategory() {
		TrainingMaterialsCategory tmc = trainingMaterialsCategoryService.getTrainingMaterialsCategoryById(2);
	    tmc.setRemark("?????");
	    trainingMaterialsCategoryService.updateTrainingMaterialsCategory(tmc);
	}

	@Test
	public void testGetTrainingMaterialsCategoryById() {
		System.out.println(trainingMaterialsCategoryService.getTrainingMaterialsCategoryById(2));
	}

	@Test
	public void testGetAllTrainingMaterialsCategory() {
		System.out.println(trainingMaterialsCategoryService.getTMCNumByContentByModule("项目","training"));
	}

	@Test
	public void testgetAllFirstLevelTMC(){
		List<TrainingMaterialsCategory> tmcs = trainingMaterialsCategoryService.getAllTMCByModule("training", 10, 1);
//		JsonArray jsonArray = new JsonArray();
//		Gson gson = new Gson();  
//		for(TrainingMaterialsCategory tmc: tmcs){
//			JsonObject object = new JsonParser().parse(gson.toJson(tmc)).getAsJsonObject();
//			jsonArray.add(object);
//	    }
//		System.out.println(jsonArray.toString());
		System.out.println(UtilCommon.listToJson(tmcs));
	}
}
