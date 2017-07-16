package com.wanda.action;

import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.service.TrainingMaterialsCategoryService;

@Controller("OperateCategory")
@Scope("prototype")
public class OperateCategory {
	@Resource
	private TrainingMaterialsCategoryService trainingMaterialsCategoryService;
	private int TMCId;
	private String TMCName;
	private String remark;
	private int firstLevelTMCId;
	private String searchContent;
	private String module;

	public String getTMCName() {
		return TMCName;
	}
	public void setTMCName(String tMCName) {
		TMCName = tMCName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getFirstLevelTMCId() {
		return firstLevelTMCId;
	}
	public void setFirstLevelTMCId(int firstLevelTMCId) {
		this.firstLevelTMCId = firstLevelTMCId;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
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

	/**
	 * 根据id删除分类
	 * @return
	 */
	public String deleteCategoryById(){
		try{
			trainingMaterialsCategoryService.deleteTrainingMaterialsCategoryById(TMCId);
			ActionContext.getContext().put("deleteResult", "success");
		}catch (Exception e) {
			ActionContext.getContext().put("deleteResult", "failed");
		}

		return (searchContent==null)? "page":"content";
	}

	/**
	 * 上传/更新类
	 */
	public String uploadCategory(){
		TrainingMaterialsCategory firstCategory = null;
		TrainingMaterialsCategory category = null;
		//获取类别
		if(firstLevelTMCId != 0)
		    firstCategory = trainingMaterialsCategoryService//
		    .getTrainingMaterialsCategoryById(firstLevelTMCId);
		//更新操作
		if(TMCId != 0){
			category = trainingMaterialsCategoryService.getTrainingMaterialsCategoryById(TMCId);
		}else{
			//添加操作
			category = new TrainingMaterialsCategory();
		}
		category.setTMCName(TMCName);
		category.setRemark(remark);
		category.setModule(module);
		category.setParentTMC(firstCategory);

		//更新
		if(TMCId != 0){
			try{
				trainingMaterialsCategoryService.updateTrainingMaterialsCategory(category);
				ActionContext.getContext().put("updateResult", "success");
			}catch (Exception e) {
				ActionContext.getContext().put("updateResult", "failed");
			}

		}
		else{
			try{
				trainingMaterialsCategoryService.addTrainingMaterialsCategory(category); 
				ActionContext.getContext().put("addResult", "success");
			}catch (Exception e) {
				ActionContext.getContext().put("addResult", "failed");
			}
		}

		return (searchContent==null)? "page":"content";
	}

	/**
	 * 异步获取第一级的类别
	 * @return
	 */
	public String getFirstLevelCategory(){
		//获取输入流
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = null;
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码 

		try {
			out = response.getWriter();
			String json = trainingMaterialsCategoryService.getFirstLevelTMCJsonByFModule(module);
			if(json != null)
				out.write(json);
			else
				out.write("null");
		} catch (Exception e) {			
			out.write("wrong");
		}finally {
			if(out != null){
				out.flush();  
				out.close();
			}
		}
		return null;
	}
}
