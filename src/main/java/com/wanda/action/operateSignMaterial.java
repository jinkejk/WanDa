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
import com.wanda.beans.SignMaterial;
import com.wanda.beans.TrainingMaterial;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.beans.User;
import com.wanda.service.SecurityLevelService;
import com.wanda.service.SignMaterialService;
import com.wanda.service.TrainingMaterialsCategoryService;
import com.wanda.service.UserService;

@Controller("operateSignMaterial")
@Scope("prototype") 
public class operateSignMaterial extends ActionSupport {

	@Resource
	private TrainingMaterialsCategoryService trainingMaterialsCategoryService;
	@Resource
	private SignMaterialService signMaterialService;
	@Resource
	private UserService userService;
	@Resource
	private SecurityLevelService securityLevelService;
	private int SMId;
	private String searchContent; //记实是否为搜索内容结果，返回页面不同
	private String securityLevelName;
	private String title;
	private String remark;
	private String signFile;
	private int firstLevelTMCId;
	private int secondLevelTMCId;
	private String SMIds;

	public String getSMIds() {
		return SMIds;
	}
	public void setSMIds(String sMIds) {
		SMIds = sMIds;
	}
	public String getSecurityLevelName() {
		return securityLevelName;
	}
	public void setSecurityLevelName(String securityLevelName) {
		this.securityLevelName = securityLevelName;
	}
	public int getSMId() {
		return SMId;
	}
	public void setSMId(int sMId) {
		SMId = sMId;
	}
	public String getSearchContent() {
		return searchContent;
	}
	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSignFile() {
		return signFile;
	}
	public void setSignFile(String signFile) {
		this.signFile = signFile;
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

	/**
	 * 根据id删除签批资料
	 * @return
	 */
	public String deleteSMById(){
		try{
			signMaterialService.deleteSignMaterial(signMaterialService.getSignMaterialById(SMId));			
			ActionContext.getContext().put("deleteResult", "success");
		}catch (Exception e) {
			ActionContext.getContext().put("deleteResult", "failed");
		}

		return (searchContent==null)? "page":"content";
	}

	/**
	 * 上传/更新培训资料
	 */
	public String uploadSM(){
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
		SignMaterial sm;
		//更新操作
		if(SMId != 0){
			sm = signMaterialService.getSignMaterialById(SMId);
			sm.setAlterDate(new Date());
			sm.setLastAlter(user);
		}
		else{
			//添加操作
			sm = new SignMaterial();
			sm.setCreateDate(new Date());
			sm.setAuthor(user);
			sm.setClickNum(0);
		}
		sm.setTitle(title);
		sm.setSignFile(signFile);
		sm.setRemark(remark);
		sm.setCategory(tmc);
		sm.setSecurityLevel(securityLevel);

		//添加
		if(SMId != 0){
			try{
				signMaterialService.updateSignMaterial(sm);
				ActionContext.getContext().put("updateResult", "success");
			}catch (Exception e) {
				ActionContext.getContext().put("updateResult", "failed");
			}

		}
		else{
			try{
				signMaterialService.addSignMaterial(sm); 
				ActionContext.getContext().put("addResult", "success");
			}catch (Exception e) {
				ActionContext.getContext().put("addResult", "failed");
			}
		}

		return (searchContent==null)? "page":"content";
	}
	
	//批量删除
	public String delectSignMaterials(){
		if(SMIds != null){
			String[] ids = SMIds.split(",");
			List<Integer> idList = new ArrayList<>();
			try{
				for(String id: ids){
					idList.add(Integer.parseInt(id));
				}
				//批量删除
				if(idList.size() > 0)
					signMaterialService.deleteSignMaterials(idList);
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
