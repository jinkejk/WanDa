package com.wanda.action;

import java.math.BigInteger;
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
import com.wanda.beans.SignMaterial;
import com.wanda.beans.TrainingMaterial;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.service.TrainingMaterialService;
import com.wanda.service.TrainingMaterialsCategoryService;

@Controller("SearchTrainingMaterial")
@Scope("prototype") 
public class SearchTrainingMaterial extends ActionSupport {

	@Resource
	private TrainingMaterialService trainingMaterialService;
	@Resource
	private TrainingMaterialsCategoryService trainingMaterialsCategoryService;

	private int pageSize;
	private int currentPage;
	private String searchContent;
	private int TMCId;
	private int flag; //判断是否返回iframe页面

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

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
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

	/**
	 * 分页搜索培训资料
	 * @return
	 */
	public String searchTMByPage(){
		//设置默认值
		if(pageSize == 0 || currentPage ==0){
			pageSize = 6;
			currentPage = 1;
		}

		//分页获得培训资料
		List<TrainingMaterial> trainingMaterials = trainingMaterialService.getTrainingMaterialByPage(pageSize, currentPage);

		//获得总共的培训资料数目
		Long trainingMaterialNum = trainingMaterialService.getTotalTrainingMaterialNum();		

		//计算总页数
		int totalPage = (int)Math.ceil((double)trainingMaterialNum/pageSize);

		//保存更改长度后的title
		List<String> titleList = new ArrayList<String>();

		if(trainingMaterials != null && trainingMaterials.size() > 0){
			for(int i=0; i<trainingMaterials.size(); i++){
				TrainingMaterial trainingMaterial = trainingMaterials.get(i);
				//判断标题的长度
				String tempName = trainingMaterial.getTitle().length() > 40?
						trainingMaterial.getTitle().substring(0, 40) : trainingMaterial.getTitle();            

						titleList.add(tempName);
			}			
		}

		ActionContext.getContext().put("trainingMaterialNum", trainingMaterialNum);
		ActionContext.getContext().put("trainingMaterials", trainingMaterials);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("titleList", titleList);

		return "showTMList";
	}

	/**
	 * 按照内容分页搜索培训资料
	 */
	public String searchTMBycontent(){
		//设置默认值
		if(pageSize == 0 || currentPage ==0){
			pageSize = 10;
			currentPage = 1;
		}
		//去掉首尾空格,且替换多个空格为一个
		if(searchContent==null || searchContent.isEmpty()){
			return searchTMByPage();
		}
		//去掉首尾空格,且替换多个空格为一个
		searchContent = searchContent.trim().replaceAll("\\s+", " ");		

		//分页获得培训资料
		List<TrainingMaterial> trainingMaterials = trainingMaterialService.getTrainingMaterialByContentByPage(searchContent, pageSize, currentPage);

		//获得总共的培训资料数目
		int trainingMaterialNum = trainingMaterialService.getTotalTrainingMaterialNumByContent(searchContent).intValue();		

		//计算总页数
		int totalPage = (int)Math.ceil((double)trainingMaterialNum/pageSize);

		//设置标题长度，以及对相关字标红
		String[] searchArray = searchContent.split(" ");

		//去除重复的关键字
		Set<String> searchSet = new HashSet<String>(Arrays.asList(searchArray));

		//保存更改后的title
		List<String> titleList = new ArrayList<String>();

		if(trainingMaterials != null && trainingMaterials.size() > 0){
			for(int i=0; i<trainingMaterials.size(); i++){
				TrainingMaterial trainingMaterial = trainingMaterials.get(i);
				//判断标题的长度
				String tempName = trainingMaterial.getTitle().length() > 40?
						trainingMaterial.getTitle().substring(0, 40) : trainingMaterial.getTitle();            

						//标红关键字
						for(String str: searchSet){ 		  
							if(tempName.contains(str)){
								tempName = tempName.replaceAll(str, "<font color=\"red\">" + str + "</font>");
							} 
						}
						titleList.add(tempName);
			}			
		}

		ActionContext.getContext().put("trainingMaterialNum", trainingMaterialNum);
		ActionContext.getContext().put("trainingMaterials", trainingMaterials);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("titleList", titleList);

		return flag==1? "trainingMaterial_frame":"showTMList";
	}

	/**
	 * 分类搜索资料
	 */
	public String searchTrainingMaterialsByCategory() throws Exception {
		//设置默认值
		if(pageSize == 0 || currentPage ==0){
			pageSize = 10;
			currentPage = 1;
		}

		List<TrainingMaterial> trainingMaterials = null;
		long trainingMaterialNum = 0L;
		if(TMCId != 0){
			TrainingMaterialsCategory tmc = trainingMaterialsCategoryService.getTrainingMaterialsCategoryById(TMCId);
			trainingMaterials = trainingMaterialService.getTrainingMaterialsByCategory(tmc, pageSize, currentPage);

			//获得总共的文章数目
			trainingMaterialNum = trainingMaterialService.getTMNumByCategory(tmc);
		}


		//计算总页数
		int totalPage = (int)Math.ceil((double)trainingMaterialNum/pageSize);

		//保存更改长度后的title
		List<String> titleList = new ArrayList<String>();

		if(trainingMaterials != null && trainingMaterials.size() > 0){
			for(int i=0; i<trainingMaterials.size(); i++){
				TrainingMaterial trainingMaterial = trainingMaterials.get(i);
				titleList.add(trainingMaterial.getTitle());
			}			
		}

		ActionContext.getContext().put("trainingMaterialNum", trainingMaterialNum);
		ActionContext.getContext().put("trainingMaterials", trainingMaterials);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("titleList", titleList);

		return "trainingMaterial_frame";
	}
}
