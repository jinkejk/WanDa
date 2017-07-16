package com.wanda.service.impl.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wanda.beans.Solution;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.service.SolutionService;

public class SolutionServiceImplTest {

	private static SolutionService solutionService;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception{
		try{
			@SuppressWarnings("resource")
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
			solutionService = (SolutionService) applicationContext.getBean("solutionService");
		}catch(RuntimeException e){
			e.printStackTrace();
		}

	}
	@Test
	public void testGetSolutionById() {
//		System.out.println(solutionService.getSolutionById(33).getSecurityLevel());
	}

	@Test
	public void testAddSolution() {
		for(int i = 1; i < 34; i++){
			Solution solution = new Solution();
			solution.setCreateDate(new Date());
			solution.setTitle("solution" + i);
			solution.setSolution("content" + i);
			solutionService.addSolution(solution);
		}
//		//添加密级
//		SecurityLevel securityLevel = new SecurityLevel();
//		securityLevel.setSecurityLevelID(2);
//		
//		Solution solution = new Solution();
//		solution.setCreateDate(new Date());
//		solution.setTitle("solution securityLevel 2");
//		solution.setSolution("content securityLevel 2");
//        solution.setSecurityLevel(securityLevel);
//     
//		solutionService.addSolution(solution);

	}

	@Test
	public void testDeleteSolutionById() {
//		solutionService.deleteSolutionById(32);
	}

	@Test
	public void testVisitSolutionById(){
//		System.out.println(solutionService.VisitSolutionById(4));
	}

	@Test
	public void testGetSolutionByPage(){
		TrainingMaterialsCategory tmc = new TrainingMaterialsCategory();
		tmc.setTMCId(38);
		List<Solution> solutions = solutionService.getSolutionsByCategory(tmc,10, 1);

		for(Solution solution: solutions){
			System.out.println(solution);
		}
	}

	@Test
	public void testGetTotalSolutionNum(){
        solutionService.deleteSolutionById(900);
	}

	@Test
	public void testGetSolutionByContentByPage(){
		List<Solution> solutions = solutionService.getLastSolutions(6);
//		System.out.println(solutions);
		for(Solution solution: solutions){
			System.out.println(solution);
		}
	}
	
	@Test
	public void testGetTotalSolutionNumByContent(){
		TrainingMaterialsCategory tmc = new TrainingMaterialsCategory();
		tmc.setTMCId(38);
	    System.out.println(solutionService.getSolutionsNumByCategory(tmc));
	}
	
	@Test
	public void testUpdateSolution(){
        List<Integer> solutionIds = new ArrayList<>();
        solutionIds.add(89);
        solutionIds.add(91);
        solutionIds.add(92);
        solutionIds.add(93);
        solutionIds.add(94);
        solutionService.deleteSolutions(solutionIds);
	} 
}
