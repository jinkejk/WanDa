package com.wanda.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wanda.beans.SecurityLevel;
import com.wanda.beans.Solution;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.beans.User;
import com.wanda.service.SecurityLevelService;
import com.wanda.service.SolutionService;
import com.wanda.service.TrainingMaterialsCategoryService;
import com.wanda.service.UserService;

@Controller("OperateSolution")
@Scope("prototype")  
public class OperateSolution extends ActionSupport {

	@Resource
	private SolutionService solutionService;
	@Resource
	private UserService userService;
	@Resource
	private SecurityLevelService securityLevelService;
	@Resource
	private TrainingMaterialsCategoryService trainingMaterialsCategoryService;

	private int solutionId; 
	private String title;
	private String solution;
	private String securityLevelName;
	private String searchContent; //记实是否为搜索内容结果，返回页面不同
	private String category;
	private int firstLevelTMCId;
	private int secondLevelTMCId;
    private String solutionIds;
    
	public String getSolutionIds() {
		return solutionIds;
	}

	public void setSolutionIds(String solutionIds) {
		this.solutionIds = solutionIds;
	}

	public int getFirstLevelTMCId() {
		return firstLevelTMCId;
	}

	public void setFirstLevelTMCId(int firstLevelTMCId) {
		this.firstLevelTMCId = firstLevelTMCId;
	}

	public int getSecondLevelTMCId() {
		return secondLevelTMCId;
	}

	public void setSecondLevelTMCId(int secondLevelTMCId) {
		this.secondLevelTMCId = secondLevelTMCId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	public String getSecurityLevelName() {
		return securityLevelName;
	}

	public void setSecurityLevelName(String securityLevelName) {
		this.securityLevelName = securityLevelName;
	}

	public int getSolutionId() {
		return solutionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public void setSolutionId(int solutionId) {
		this.solutionId = solutionId;
	}

	/**
	 * 根据solutionId 删除solution
	 */
	public String deleteSolutionById(){
		try{
			solutionService.deleteSolutionById(solutionId);			
			ActionContext.getContext().put("deleteResult", "success");
		}catch (Exception e) {
			ActionContext.getContext().put("deleteResult", "failed");
		}
		if(category != null)
			return "category";
		return (searchContent==null)? "page":"content";
	}

	/**
	 * 上传solution
	 */
	public String uploadSolution(){
		//获取上传者的userId
		Subject subject=SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		System.out.println("loginName:" + loginName);
		User user = userService.getUserByLoginName(loginName);

		//获取solution的密级
		SecurityLevel securityLevel = securityLevelService.getSecurityLevelByName(securityLevelName);
		System.out.println("securityLevel:" + securityLevel);

		//判断是否存在二级菜单
		TrainingMaterialsCategory tmc = null;
		if(secondLevelTMCId == 0)
			tmc = trainingMaterialsCategoryService.getTrainingMaterialsCategoryById(firstLevelTMCId);
		else
			tmc = trainingMaterialsCategoryService.getTrainingMaterialsCategoryById(secondLevelTMCId);

		//封装Solution类
		Solution sl = new Solution();
		sl.setTitle(title);
		sl.setSolution(solution);
		sl.setAuthor(user);
		sl.setCategory(tmc);
		//默认最近修改者为上传者
		sl.setLastAlter(user);
		sl.setCreateDate(new Date());
		sl.setSecurityLevel(securityLevel);

		//添加
		try{
			solutionService.addSolution(sl);
			ActionContext.getContext().put("addSolutionResult", "success");
		}catch (Exception e) {
			ActionContext.getContext().put("addSolutionResult", "failed");
		}

		return (searchContent==null)? "page":"content";
	}

	/**
	 * 更新solution
	 */
	public String updateSolution(){
		//获取上传者的userId
		Subject subject=SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		System.out.println("loginName:" + loginName);
		User user = userService.getUserByLoginName(loginName);

		//获取solution的密级
		SecurityLevel securityLevel = securityLevelService.getSecurityLevelByName(securityLevelName);
		System.out.println("securityLevel:" + securityLevel);

		//判断是否存在二级菜单
		TrainingMaterialsCategory tmc = null;
		if(secondLevelTMCId == 0)
			tmc = trainingMaterialsCategoryService.getTrainingMaterialsCategoryById(firstLevelTMCId);
		else
			tmc = trainingMaterialsCategoryService.getTrainingMaterialsCategoryById(secondLevelTMCId);

		//封装Solution类
		Solution sl = solutionService.getSolutionById(solutionId);
		sl.setTitle(title);
		sl.setSolution(solution);
		sl.setLastAlter(user);
		sl.setCategory(tmc);
		sl.setAlterDate(new Date());
		sl.setSecurityLevel(securityLevel);

		//更新solution
		try{
			solutionService.updateSolution(sl);;
			ActionContext.getContext().put("updateSolutionResult", "success");
		}catch (Exception e) {			
			ActionContext.getContext().put("updateSolutionResult", "failed");
		}

		return (searchContent==null)? "page":"content";
	}

	//批量删除
	public String delectSolutions(){
		if(solutionIds != null){
			String[] ids = solutionIds.split(",");
			List<Integer> idList = new ArrayList<>();
			try{
				for(String id: ids){
					idList.add(Integer.parseInt(id));
				}
				//批量删除
				if(idList.size() > 0)
					solutionService.deleteSolutions(idList);
				ActionContext.getContext().put("deleteResult", "success");
			}catch (Exception e) {
				ActionContext.getContext().put("deleteResult", "failed");
			}
		}else{
			ActionContext.getContext().put("deleteResult", "failed");
		}

		return (searchContent==null)? "page":"content";
	}
}
