package com.wanda.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wanda.beans.SignMaterial;
import com.wanda.beans.Solution;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.beans.User;
import com.wanda.service.SignMaterialService;
import com.wanda.service.TrainingMaterialsCategoryService;
import com.wanda.service.UserService;
import com.wanda.util.UtilCommon;

@Controller("SearchSignMaterial")
@Scope("prototype")
public class SearchSignMaterial extends ActionSupport{
	@Resource
	private SignMaterialService signMaterialService;
	@Resource
	private UserService userService;
	@Resource
	private TrainingMaterialsCategoryService trainingMaterialsCategoryService;

	private int pageSize;
	private int currentPage;
	private String searchContent;
	private int TMCId; //类别
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
	 * 分页搜索签批资料
	 * @return
	 */
	public String searchSMByPage(){
		//设置默认值
		if(pageSize == 0 || currentPage ==0){
			pageSize = 6;
			currentPage = 1;
		}

		//分页获得培训资料
		List<SignMaterial> signMaterials = signMaterialService.getSignMaterialByPage(pageSize, currentPage);

		//获得总共的培训资料数目
		Long signMaterialNum = signMaterialService.getTotalSignMaterialNum();		

		//计算总页数
		int totalPage = (int)Math.ceil((double)signMaterialNum/pageSize);

		List<String> titleList = new ArrayList<String>();
		if(signMaterials != null){			
			//保存更改后的title
			for(int i=0; i<signMaterials.size(); i++){
				SignMaterial signMaterial = signMaterials.get(i);

				String tempTitle = "";
				if(signMaterial.getTitle() != null){
					//判断标题的长度
					tempTitle = signMaterial.getTitle().length() > 40?
							signMaterial.getTitle().substring(0, 40) : signMaterial.getTitle();			
				}

				titleList.add(tempTitle);
			}
		}

		ActionContext.getContext().put("signMaterialNum", signMaterialNum);
		ActionContext.getContext().put("signMaterials", signMaterials);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("titleList", titleList);

		return "showSMList";
	}

	/**
	 * 按照内容分页搜索签批资料
	 */
	public String searchSMBycontent(){
		//设置默认值
		if(pageSize == 0 || currentPage ==0){
			pageSize = 5;
			currentPage = 1;
		}
		//去掉首尾空格,且替换多个空格为一个
		if(searchContent==null || searchContent.isEmpty()){
			return searchSMByPage();
		}
		//去掉首尾空格,且替换多个空格为一个
		searchContent = searchContent.trim().replaceAll("\\s+", " ");		

		//分页获得培训资料
		List<SignMaterial> signMaterials = signMaterialService.getSignMaterialByContentByPage(searchContent, pageSize, currentPage);

		//获得总共的培训资料数目
		int signMaterialNum = signMaterialService.getTotalSignMaterialNumByContent(searchContent).intValue();		

		//计算总页数
		int totalPage = (int)Math.ceil((double)signMaterialNum/pageSize);

		//设置标题长度，以及对相关字标红
		String[] searchArray = searchContent.split(" ");

		//去除重复的关键字
		Set<String> searchSet = new HashSet<String>(Arrays.asList(searchArray));

		//保存更改后的title
		List<String> titleList = new ArrayList<String>();

		if(signMaterials != null && signMaterials.size() > 0){
			for(int i=0; i<signMaterials.size(); i++){
				SignMaterial signMaterial = signMaterials.get(i);
				//判断标题的长度
				String tempName = signMaterial.getTitle().length() > 40?
						signMaterial.getTitle().substring(0, 40) : signMaterial.getTitle();            

						//标红关键字
						for(String str: searchSet){ 		  
							if(tempName.contains(str)){
								tempName = tempName.replaceAll(str, "<font color=\"red\">" + str + "</font>");
							} 
						}
						titleList.add(tempName);
			}			
		}

		ActionContext.getContext().put("signMaterialNum", signMaterialNum);
		ActionContext.getContext().put("signMaterials", signMaterials);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("titleList", titleList);

		return flag==1? "signMaterial_frame":"showSMList";
	}

	/**
	 * 分类搜索解决签批资料
	 */
	public String searchSignMaterialsByCategory() throws Exception {
		//设置默认值
		if(pageSize == 0 || currentPage ==0){
			pageSize = 5;
			currentPage = 1;
		}

		List<SignMaterial> signMaterials = null;
		long signMaterialNum = 0L;
		if(TMCId != 0){
			TrainingMaterialsCategory tmc = trainingMaterialsCategoryService.getTrainingMaterialsCategoryById(TMCId);
			signMaterials = signMaterialService.getSignMaterialsByCategory(tmc, pageSize, currentPage);				

			//获得总共的文章数目
			signMaterialNum = signMaterialService.getSignMaterialNumByCategory(tmc);
		}


		//计算总页数
		int totalPage = (int)Math.ceil((double)signMaterialNum/pageSize);

		//保存更改长度后的title
		List<String> titleList = new ArrayList<String>();

		if(signMaterials != null && signMaterials.size() > 0){
			for(int i=0; i<signMaterials.size(); i++){
				SignMaterial signMaterial = signMaterials.get(i);
				titleList.add(signMaterial.getTitle());
			}			
		}

		ActionContext.getContext().put("signMaterialNum", signMaterialNum);
		ActionContext.getContext().put("signMaterials", signMaterials);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("titleList", titleList);

		return "signMaterial_frame";
	}
}
