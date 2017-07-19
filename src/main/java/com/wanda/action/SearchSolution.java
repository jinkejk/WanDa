package com.wanda.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

import com.wanda.util.IsMobile;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wanda.beans.Solution;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.beans.User;
import com.wanda.service.SolutionSearchHistoryService;
import com.wanda.service.SolutionService;
import com.wanda.service.TrainingMaterialsCategoryService;
import com.wanda.service.UserService;
import com.wanda.util.UtilCommon;

@Controller("SearchSolution")
@Scope("prototype")  
public class SearchSolution extends ActionSupport {

	@Resource
	private SolutionService solutionService;
	@Resource
	private UserService userService;
	@Resource
	private SolutionSearchHistoryService solutionSearchHistoryService;
	@Resource
	private TrainingMaterialsCategoryService trainingMaterialsCategoryService;

	private int pageSize;
	private int currentPage;
	private int solutionId;
	private String searchContent;
	private int isNewContent;    
	private int TMCId;
    private int flag; //用来判断从哪个页面跳转的，返回不同视图
    
	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getTMCId() {
		return TMCId;
	}

	public void setTMCId(int tMCId) {
		TMCId = tMCId;
	}

	public int getIsNewContent() {
		return isNewContent;
	}

	public void setIsNewContent(int isNewContent) {
		this.isNewContent = isNewContent;
	}

	public int getSolutionId() {
		return solutionId;
	}

	public void setSolutionId(int solutionId) {
		this.solutionId = solutionId;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	//根据内容分页搜索解决方案
	@Override
	public String execute() throws Exception {		
		//去掉首尾空格,且替换多个空格为一个
		if(searchContent==null || searchContent.isEmpty()){
			return "homePage";
		}
		//去掉首尾空格,且替换多个空格为一个
		searchContent = searchContent.trim().replaceAll("\\s+", " ");		

		//获取用户对象
		Subject subject=SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User user = userService.getUserByLoginName(loginName);

		//获得总共的文章数目

		int solutionTotalNum = 0;
		int totalPage = 0;
		List<Solution> solutions = null;
		try {
			solutionTotalNum = solutionService.getTotalSolutionNumByContent(searchContent).intValue();
			//计算总页数
			totalPage = (int)Math.ceil((double)solutionTotalNum/pageSize);

			if(currentPage > totalPage)
                currentPage = totalPage;

			//分页获得文章并增加一条搜索记录
			solutions = solutionService.getSolutionByContentByPage(user, searchContent, pageSize, currentPage, isNewContent);
		} catch (Exception e) {
			e.printStackTrace();
		}


		//设置标题长度，以及对相关字标红
		String[] searchArray = searchContent.split(" ");

		//去除重复的关键字
		Set<String> searchSet = new HashSet<String>(Arrays.asList(searchArray));

		//保存更改后的title
		List<String> titleList = new ArrayList<String>();

		if(solutions != null && solutions.size() > 0){
			for(int i=0; i<solutions.size(); i++){
				Solution solution = solutions.get(i);
				//判断标题的长度
				String tempName = solution.getTitle().length() > 40?
						solution.getTitle().substring(0, 40) : solution.getTitle();            

						//标红关键字
						for(String str: searchSet){ 		  
							if(tempName.contains(str)){
								tempName = tempName.replaceAll(str, "<font color=\"red\">" + str + "</font>");
							} 
						}
						titleList.add(tempName);
			}			
		}		

		//		int num = 5;
		//		//获取最近查询关键字
		//		List<String> lastKeywords = solutionSearchHistoryService.getLastSearchContent(num);
		//
		//		//获取最近访问的条目
		//		List<Solution> lastVisitSolution = solutionService.getLastVisitSolutionTitle(num);
		//
		//		//获取热门专家库条目
		//		List<Solution> hotVisitSolution = solutionService.getHotVisitSolutionTitle(num);
		//
		//		ActionContext.getContext().put("lastKeywords", lastKeywords);
		//		ActionContext.getContext().put("lastVisitSolution", lastVisitSolution);
		//		ActionContext.getContext().put("hotVisitSolution", hotVisitSolution);
		//		ActionContext.getContext().put("num", num);
		//查询一级二级目录
		List<TrainingMaterialsCategory> allTMCs = trainingMaterialsCategoryService.getAllTMCByModule("solution", Integer.MAX_VALUE, 1);
		List<TrainingMaterialsCategory> firstLevelTMC = trainingMaterialsCategoryService.getAllFirstLevelTMCByModule("solution");
		List<TrainingMaterialsCategory> secondLevelTMC = new ArrayList<>();
		if(allTMCs != null && allTMCs.size() > 0){
			//划分成一级和二级目录
			for(TrainingMaterialsCategory allTMC: allTMCs){
				if(allTMC.getParentTMC()!=null)
					secondLevelTMC.add(allTMC);
			}
		}

		ActionContext.getContext().put("firstLevelTMC", UtilCommon.listToJson(firstLevelTMC));
		ActionContext.getContext().put("secondLevelTMC", UtilCommon.listToJson(secondLevelTMC));
		ActionContext.getContext().put("solutionTotalNum", solutionTotalNum);
		ActionContext.getContext().put("solutions", solutions);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("titleList", titleList);

		return IsMobile.check(ServletActionContext.getRequest())? "searchResult_mobile":"searchResult";
	}

	//在管理界面根据内容分页搜索解决方案
	public String searchSolutionByContent() throws Exception {		
		//去掉首尾空格,且替换多个空格为一个
		if(searchContent==null || searchContent.isEmpty()){
			return searchSolutionByPage();
		}
		//去掉首尾空格,且替换多个空格为一个
		searchContent = searchContent.trim().replaceAll("\\s+", " ");		

		//获取用户对象
		Subject subject=SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User user = userService.getUserByLoginName(loginName);

		//分页获得文章并增加一条搜索记录
		List<Solution> solutions = solutionService.getSolutionByContentByPage(user, searchContent, pageSize, currentPage, isNewContent);

		//获得总共的文章数目
		int solutionTotalNum = solutionService.getTotalSolutionNumByContent(searchContent).intValue();		

		//计算总页数
		int totalPage = (int)Math.ceil((double)solutionTotalNum/pageSize);

		//设置标题长度，以及对相关字标红
		String[] searchArray = searchContent.split(" ");

		//去除重复的关键字
		Set<String> searchSet = new HashSet<String>(Arrays.asList(searchArray));

		//保存更改后的title
		List<String> titleList = new ArrayList<String>();

		if(solutions != null && solutions.size() > 0){
			for(int i=0; i<solutions.size(); i++){
				Solution solution = solutions.get(i);
				//判断标题的长度
				String tempName = solution.getTitle().length() > 40?
						solution.getTitle().substring(0, 40) : solution.getTitle();            

						//标红关键字
						for(String str: searchSet){ 		  
							if(tempName.contains(str)){
								tempName = tempName.replaceAll(str, "<font color=\"red\">" + str + "</font>");
							} 
						}
						titleList.add(tempName);
			}			
		}
		
		ActionContext.getContext().put("solutionTotalNum", solutionTotalNum);
		ActionContext.getContext().put("solutions", solutions);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("titleList", titleList);

		return  "searchResult_manage";
	}

	/**
	 * 分页搜索所有解决方案
	 */
	public String searchSolutionByPage() throws Exception {
		//设置默认值
		if(pageSize == 0 || currentPage ==0){
			pageSize = 6;
			currentPage = 1;
		}

		List<Solution> solutions = solutionService.getSolutionByPage(pageSize, currentPage);				
		//获得总共的文章数目
		Long solutionTotalNum = solutionService.getTotalSolutionNum();

		//计算总页数
		int totalPage = (int)Math.ceil((double)solutionTotalNum/pageSize);

		//保存更改长度后的title
		List<String> titleList = new ArrayList<String>();

		if(solutions != null && solutions.size() > 0){
			for(int i=0; i<solutions.size(); i++){
				Solution solution = solutions.get(i);
				//判断标题的长度
				String tempName = solution.getTitle().length() > 40?
						solution.getTitle().substring(0, 40) : solution.getTitle();            

						titleList.add(tempName);
			}			
		}

		ActionContext.getContext().put("solutionTotalNum", solutionTotalNum);
		ActionContext.getContext().put("solutions", solutions);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("titleList", titleList);

		return "solutionListResult";
	}

	/**
	 * 分类搜索解决方案
	 */
	public String searchSolutionByCategory() throws Exception {
		//设置默认值
		if(pageSize == 0 || currentPage ==0){
			pageSize = 15;
			currentPage = 1;
		}

		List<Solution> solutions = null;
		Long solutionTotalNum = 0L;
		if(TMCId != 0){
			TrainingMaterialsCategory tmc = trainingMaterialsCategoryService.getTrainingMaterialsCategoryById(TMCId);
			solutions = solutionService.getSolutionsByCategory(tmc, pageSize, currentPage);				

			//获得总共的文章数目
			solutionTotalNum = solutionService.getSolutionsNumByCategory(tmc);
		}


		//计算总页数
		int totalPage = (int)Math.ceil((double)solutionTotalNum/pageSize);

		//保存更改长度后的title
		List<String> titleList = new ArrayList<String>();

		if(solutions != null && solutions.size() > 0){
			for(int i=0; i<solutions.size(); i++){
				Solution solution = solutions.get(i);
				//判断标题的长度
				String tempName = solution.getTitle().length() > 40?
						solution.getTitle().substring(0, 40) : solution.getTitle();            

						titleList.add(tempName);
			}			
		}
		
		if(flag==1){
			//查询一级二级目录
			List<TrainingMaterialsCategory> allTMCs = trainingMaterialsCategoryService.getAllTMCByModule("solution", Integer.MAX_VALUE, 1);
			List<TrainingMaterialsCategory> firstLevelTMC = trainingMaterialsCategoryService.getAllFirstLevelTMCByModule("solution");
			List<TrainingMaterialsCategory> secondLevelTMC = new ArrayList<>();
			if(allTMCs != null && allTMCs.size() > 0){
				//划分成一级和二级目录
				for(TrainingMaterialsCategory allTMC: allTMCs){
					if(allTMC.getParentTMC()!=null)
						secondLevelTMC.add(allTMC);
				}
			}
			
			ActionContext.getContext().put("firstLevelTMC", UtilCommon.listToJson(firstLevelTMC));
			ActionContext.getContext().put("secondLevelTMC", UtilCommon.listToJson(secondLevelTMC));			
		}
		ActionContext.getContext().put("solutionTotalNum", solutionTotalNum);
		ActionContext.getContext().put("solutions", solutions);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("titleList", titleList);

		if(IsMobile.check(ServletActionContext.getRequest())){
			//手机端
			return "searchResult_mobile";
		}else{
			return flag==1? "searchResult":"searchResult_frame";
		}
	}

	/**
	 * 根据solution Id获取solution
	 * @return
	 */
	public String searchSolutionById() throws IOException{
		//获取当前用户
		Subject subject = SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User currentUser = userService.getUserByLoginName(loginName);
		int  userLevel = currentUser.getRole().getSecurityLevel().getSecurityLevelValue();

		Solution solution = solutionService.VisitSolutionById(solutionId);
		//判断密级
		if(solution.getSecurityLevel()!=null && userLevel < solution.getSecurityLevel().getSecurityLevelValue()){
			//无权限
			return "unauthorized";
		}

		ActionContext.getContext().put("solution", solution);

		return IsMobile.check(ServletActionContext.getRequest())? "showSolution_mobile":"showSolution";
	}

}
