package com.wanda.service.impl.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wanda.beans.TrainingMaterial;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.service.TrainingMaterialService;

public class TrainingMaterialServiceImplTest {

	private static TrainingMaterialService trainingMaterialService;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try{
			@SuppressWarnings("resource")
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			trainingMaterialService = (TrainingMaterialService) applicationContext.getBean("trainingMaterialService");
		}catch(RuntimeException e){
			e.printStackTrace();
		}
	}

	@Test
	public void testAddTrainingMaterial() {
		for(int i=1; i<35; i++){
			TrainingMaterial tm = new TrainingMaterial();
			tm.setTitle("test" + i);
			tm.setCreateDate(new Date());
			tm.setRemark("remark" + i);
			trainingMaterialService.addTrainingMaterial(tm);
		}
	}

	@Test
	public void testDeleteTrainingMaterial() {
		trainingMaterialService.deleteTrainingMaterial(trainingMaterialService.getTrainingMaterialById(52));
	}

	@Test
	public void testUpdateTrainingMaterial() {
		TrainingMaterial tm = trainingMaterialService.getTrainingMaterialById(33);
		tm.setPptName("none");
		trainingMaterialService.updateTrainingMaterial(tm);
		
	}

	@Test
	public void trainingMaterialService() {
		TrainingMaterialsCategory tmc = new TrainingMaterialsCategory();
		tmc.setTMCId(1);
		List<TrainingMaterial> tms = trainingMaterialService.getTrainingMaterialsByCategory(tmc,8, 1);
		for(TrainingMaterial tm: tms){
			System.out.println(tm);
		}
	}

	@Test
	public void testGetTotalTrainingMaterialNum() {
	    System.out.println(trainingMaterialService.getTotalTrainingMaterialNum());
	}

	@Test
	public void testGetTrainingMaterialByContentByPage() {
		List<TrainingMaterial> tms = trainingMaterialService.getLastTrainingMaterial(5);
		for(TrainingMaterial tm: tms){
			System.out.println(tm);
		}
	}

	@Test
	public void testGetTotalSolutionNumByContent() {
		System.out.println(trainingMaterialService.getTotalTrainingMaterialNumByContent("3"));
	}

	@Test
	public void testGetTrainingMaterialById() {
		TrainingMaterialsCategory tmc = new TrainingMaterialsCategory();
		tmc.setTMCId(1);
		System.out.println(trainingMaterialService.getTMNumByCategory(tmc));
	}

	@Test
	public void testVisitTrainingMaterialById() {
		List<Integer> ids = new ArrayList<>();
		ids.add(89);
		ids.add(90);
		ids.add(91);
		ids.add(92);
		ids.add(93);
		ids.add(94);
		trainingMaterialService.deleteTrainingMaterials(ids);
	}

}
