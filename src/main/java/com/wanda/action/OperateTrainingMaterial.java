package com.wanda.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wanda.beans.SecurityLevel;
import com.wanda.beans.TrainingMaterial;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.beans.User;
import com.wanda.service.SecurityLevelService;
import com.wanda.service.TrainingMaterialService;
import com.wanda.service.TrainingMaterialsCategoryService;
import com.wanda.service.UserService;

@Controller("OperateTrainingMaterial")
@Scope("prototype") 
public class OperateTrainingMaterial extends ActionSupport {

	@Resource
	private TrainingMaterialService trainingMaterialService;
	@Resource
	private TrainingMaterialsCategoryService trainingMaterialsCategoryService;
	@Resource
	private UserService userService;
	@Resource
	private SecurityLevelService securityLevelService;
	private int TMId;
	private String searchContent; //记实是否为搜索内容结果，返回页面不同
	private String securityLevelName;
	private String title;
	private String pptName;
	private String remark;
	private int firstLevelTMCId;
	private int secondLevelTMCId;
	private String TMIds;
	
	public String getTMIds() {
		return TMIds;
	}
	public void setTMIds(String tMIds) {
		TMIds = tMIds;
	}
	public int getSecondLevelTMCId() {
		return secondLevelTMCId;
	}
	public void setSecondLevelTMCId(int secondLevelTMCId) {
		this.secondLevelTMCId = secondLevelTMCId;
	}
	public int getFirstLevelTMCId() {
		return firstLevelTMCId;
	}
	public void setFirstLevelTMCId(int firstLevelTMCId) {
		this.firstLevelTMCId = firstLevelTMCId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getPptName() {
		return pptName;
	}
	public void setPptName(String pptName) {
		this.pptName = pptName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSecurityLevelName() {
		return securityLevelName;
	}
	public void setSecurityLevelName(String securityLevelName) {
		this.securityLevelName = securityLevelName;
	}
	public String getSearchContent() {
		return searchContent;
	}
	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	public int getTMId() {
		return TMId;
	}
	public void setTMId(int tMId) {
		TMId = tMId;
	}

	/**
	 * 根据id删除训练资料
	 * @return
	 */
	public String deleteTMById(){
		try{
			trainingMaterialService.deleteTrainingMaterial(
					trainingMaterialService.getTrainingMaterialById(TMId));			
			ActionContext.getContext().put("deleteResult", "success");
		}catch (Exception e) {
			ActionContext.getContext().put("deleteResult", "failed");
		}

		return (searchContent==null)? "page":"content";
	}

	/**
	 * 上传/更新培训资料
	 */
	public String uploadTM(){
		//获取上传者的userId
		Subject subject = SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User user = userService.getUserByLoginName(loginName);

		//获取的密级
		SecurityLevel securityLevel = securityLevelService.getSecurityLevelByName(securityLevelName); 

		//判断是否存在二级菜单
		TrainingMaterialsCategory tmc = null;
		if(secondLevelTMCId == 0)
			tmc = trainingMaterialsCategoryService.getTrainingMaterialsCategoryById(firstLevelTMCId);
		else
			tmc = trainingMaterialsCategoryService.getTrainingMaterialsCategoryById(secondLevelTMCId);

		//封装TM类
		TrainingMaterial tm;
		//更新操作
		if(TMId != 0){
			tm = trainingMaterialService.getTrainingMaterialById(TMId);
			tm.setAlterDate(new Date());
			tm.setLastAlter(user);
			tm.setCategory(tmc);
		}
		else{
			//添加操作
			tm = new TrainingMaterial();
			tm.setCreateDate(new Date());
			tm.setAuthor(user);
			tm.setClickNum(0);
		}
		tm.setTitle(title);
		tm.setPptName(pptName);
		tm.setRemark(remark);
		tm.setCategory(tmc);
		tm.setSecurityLevel(securityLevel);

		//添加
		if(TMId != 0){
			try{
				trainingMaterialService.updateTrainingMaterial(tm);
				ActionContext.getContext().put("updateResult", "success");
			}catch (Exception e) {
				ActionContext.getContext().put("updateResult", "failed");
			}

		}
		else{
			try{
				trainingMaterialService.addTrainingMaterial(tm); 
				ActionContext.getContext().put("addResult", "success");
			}catch (Exception e) {
				ActionContext.getContext().put("addResult", "failed");
			}
		}

		return (searchContent==null)? "page":"content";
	}	

	/**
	 * 异步获取二级分类菜单
	 */
	public String getSecondLevelTMCs(){
		//获取输入流
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = null;
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码 

		try {
			out = response.getWriter();
			String json = trainingMaterialsCategoryService.getSecondLevelTMCJsonByFirstLevelTMCId(firstLevelTMCId);
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

	//批量删除
	public String delectTrainingMaterials(){
		if(TMIds != null){
			String[] ids = TMIds.split(",");
			List<Integer> idList = new ArrayList<>();
			try{
				for(String id: ids){
					idList.add(Integer.parseInt(id));
				}
				//批量删除
				if(idList.size() > 0)
					trainingMaterialService.deleteTrainingMaterials(idList);
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
