package com.wanda.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wanda.beans.TrainingMaterial;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.service.TrainingMaterialsCategoryService;

@Controller("SearchCategory")
@Scope("prototype") 
public class SearchCategory extends ActionSupport {
	@Resource
	private TrainingMaterialsCategoryService trainingMaterialsCategoryService;
	private int pageSize;
	private int currentPage;
	private String searchContent;	
	private String module;

	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
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

	/**
	 * 分页搜索类别
	 */
	public String searchCategoryByPage(){
		//设置默认值
		if(pageSize==0 || currentPage==0){
			pageSize = 6;
			currentPage = 1;
		}

		//分页获得分类
		List<TrainingMaterialsCategory> categorys = null;
		Long categoryNum = 0L;
		if(module != null && !module.isEmpty() && module.length()>0){
			//如果是按模块搜索的
			categorys = trainingMaterialsCategoryService.getAllTMCByModule(module, pageSize, currentPage);
			//获得总共的分类数目
			categoryNum = trainingMaterialsCategoryService.getAllTMCNumByModule(module);		
		}else{
			categorys = trainingMaterialsCategoryService.getTMCByPage(pageSize, currentPage);
			//获得总共的分类数目
			categoryNum = trainingMaterialsCategoryService.getTotalTMCNum();		
		}	    

		//计算总页数
		int totalPage = (int)Math.ceil((double)categoryNum/pageSize);

		//保存更改后的title
		List<String> titleList = new ArrayList<String>();

		if(categorys != null && categorys.size() > 0){
			for(int i=0; i<categorys.size(); i++){
				TrainingMaterialsCategory category = categorys.get(i);
				//判断标题的长度
				String tempName = category.getTMCName().length() > 40?
						category.getTMCName().substring(0, 40) : category.getTMCName();            

						titleList.add(tempName);
			}			
		}
		ActionContext.getContext().put("categoryNum", categoryNum);
		ActionContext.getContext().put("categorys", categorys);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("titleList", titleList);
		ActionContext.getContext().put("currentPage", currentPage);
		ActionContext.getContext().put("pageSize", pageSize);

		return "showCategoryList";
	}

	/**
	 * 按照内容分页搜索类别
	 */
	public String searchCategoryBycontent(){
		//去掉首尾空格,且替换多个空格为一个
		if(searchContent==null || searchContent.isEmpty()){
			return searchCategoryByPage();
		}
		//去掉首尾空格,且替换多个空格为一个
		searchContent = searchContent.trim().replaceAll("\\s+", " ");		

		//分页获得培训资料
		List<TrainingMaterialsCategory> categorys = null;
		int categoryNum;
		if(module != null && !module.isEmpty() && module.length()>0){
			//如果是按模块搜索的
			categorys = trainingMaterialsCategoryService.getTMCByContentByModuleByPage(searchContent, module, pageSize, currentPage);
			//获得总共的分类数目
			categoryNum = trainingMaterialsCategoryService.getTMCNumByContentByModule(searchContent, module).intValue();		
		}else{
			categorys = trainingMaterialsCategoryService.getTMCByContentByPage(searchContent, pageSize, currentPage);
			//获得总共的分类数目
			categoryNum = trainingMaterialsCategoryService.getTMCNumByContent(searchContent).intValue();
		}			

		//计算总页数
		int totalPage = (int)Math.ceil((double)categoryNum/pageSize);

		//设置标题长度，以及对相关字标红
		String[] searchArray = searchContent.split(" ");

		//去除重复的关键字
		Set<String> searchSet = new HashSet<String>(Arrays.asList(searchArray));

		//保存更改后的title
		List<String> titleList = new ArrayList<String>();

		if(categorys != null && categorys.size() > 0){
			for(int i=0; i<categorys.size(); i++){
				TrainingMaterialsCategory category = categorys.get(i);
				//判断标题的长度
				String tempName = category.getTMCName().length() > 40?
						category.getTMCName().substring(0, 40) : category.getTMCName();            

						//标红关键字
						for(String str: searchSet){ 		  
							if(tempName.contains(str)){
								tempName = tempName.replaceAll(str, "<font color=\"red\">" + str + "</font>");
							} 
						}
						titleList.add(tempName);
			}			
		}

		ActionContext.getContext().put("categoryNum", categoryNum);
		ActionContext.getContext().put("categorys", categorys);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("titleList", titleList);

		return "showCategoryList";
	}

	/**
	 * 按模块分页搜索类别
	 */
	public String searchCategoryByModule(){
		//设置默认值
		if(pageSize==0 || currentPage==0){
			pageSize = 6;
			currentPage = 1;
		}

		//按模块分页获得分类
		List<TrainingMaterialsCategory> categorys = null;
		if(module != null)
			categorys = trainingMaterialsCategoryService.getAllTMCByModule(module, pageSize, currentPage);

		//获得该模块总共的分类数目
		Long categoryNum = trainingMaterialsCategoryService.getAllTMCNumByModule(module);		

		//计算总页数
		int totalPage = (int)Math.ceil((double)categoryNum/pageSize);

		//保存更改后的title
		List<String> titleList = new ArrayList<String>();

		if(categorys != null && categorys.size() > 0){
			for(int i=0; i<categorys.size(); i++){
				TrainingMaterialsCategory category = categorys.get(i);
				//判断标题的长度
				String tempName = category.getTMCName().length() > 40?
						category.getTMCName().substring(0, 40) : category.getTMCName();            

						titleList.add(tempName);
			}			
		}
		ActionContext.getContext().put("categoryNum", categoryNum);
		ActionContext.getContext().put("categorys", categorys);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("titleList", titleList);
		ActionContext.getContext().put("currentPage", currentPage);
		ActionContext.getContext().put("pageSize", pageSize);

		return "showCategoryList";
	}
}
