package com.wanda.service.impl.test;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wanda.beans.SignMaterial;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.service.SignMaterialService;

public class SignMaterialServiceImplTest {

	private static SignMaterialService signMaterialService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		try{
			@SuppressWarnings("resource")
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			signMaterialService = (SignMaterialService) applicationContext.getBean("signMaterialService");
		}catch(RuntimeException e){
			e.printStackTrace();
		}
	}

	@Test
	public void testAddSignMaterial() {
		for(int i=0; i<33; i++){
			SignMaterial sm = new SignMaterial();
			sm.setCreateDate(new Date());
			sm.setTitle("test" + (i+1));
			signMaterialService.addSignMaterial(sm);
		}
	}

	@Test
	public void testDeleteSignMaterial() {
		signMaterialService.deleteSignMaterial(signMaterialService.getSignMaterialById(33));
	}

	@Test
	public void testUpdateSignMaterial() {
		SignMaterial sm = signMaterialService.getSignMaterialById(32);
		sm.setTitle("update");
		signMaterialService.updateSignMaterial(sm);
	}

	@Test
	public void testGetSignMaterialByPage() {
		List<SignMaterial> sms = signMaterialService.getSignMaterialByPage(6, 1);
		for(SignMaterial sm: sms){
			System.out.println(sm);
		}
	}

	@Test
	public void testGetTotalSignMaterialNum() {
		System.out.println(signMaterialService.getSignMaterialByFileName("2017_04_05_04_30_26_157.docx"));
	}

	@Test
	public void testGetSignMaterialByContentByPage() {
		TrainingMaterialsCategory tmc = new TrainingMaterialsCategory();
		tmc.setTMCId(20);
		List<SignMaterial> sms = signMaterialService.getSignMaterialsByCategory(tmc, 10, 1);
		for(SignMaterial sm: sms){
			System.out.println(sm);
		}
	}

	@Test
	public void testGetTotalSignMaterialNumByContent() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSignMaterialById() {
		TrainingMaterialsCategory tmc = new TrainingMaterialsCategory();
		tmc.setTMCId(20);
		System.out.println(signMaterialService.getSignMaterialNumByCategory(tmc));
	}

	@Test
	public void testGetLastSignMaterial() {
		signMaterialService.getLastSignMaterial(8);
	}

	@Test
	public void testAddSignMaterialDownloadNum() {
		List<Integer> ids = new ArrayList<>();
		ids.add(25);
		ids.add(26);
		ids.add(27);
		signMaterialService.deleteSignMaterials(ids);
	}

}
