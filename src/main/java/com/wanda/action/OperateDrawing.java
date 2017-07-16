package com.wanda.action;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wanda.beans.Drawing;
import com.wanda.beans.SecurityLevel;
import com.wanda.beans.User;
import com.wanda.service.DrawingService;
import com.wanda.service.SecurityLevelService;
import com.wanda.service.UserService;

@Controller("OperateDrawing")
@Scope("prototype")  
public class OperateDrawing extends ActionSupport {

	@Resource
	private DrawingService drawingService;
	@Resource
	private UserService userService;
	@Resource
	private SecurityLevelService securityLevelService;

	private int drawingId;
	private String drawingName;
	private String remark;
	private String stringId;
	private String imgName;
	//记实是否为搜索内容结果，返回页面不同
	private String searchContent;	
	private String securityLevelName;

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getStringId() {
		return stringId;
	}

	public void setStringId(String stringId) {
		this.stringId = stringId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDrawingName() {
		return drawingName;
	}

	public void setDrawingName(String drawingName) {
		this.drawingName = drawingName;
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

	public int getDrawingId() {
		return drawingId;
	}

	public void setDrawingId(int drawingId) {
		this.drawingId = drawingId;
	}

	/**
	 * 根据drawingId 删除drawing
	 */
	public String deleteDrawingById(){
		try{
			drawingService.deleteDrawingById(drawingId);
			ActionContext.getContext().put("deleteResult", "success");
		}catch (Exception e) {
			ActionContext.getContext().put("deleteResult", "failed");
		}

		//判断是否是在搜索内容中删除的
		return (searchContent==null)? "page":"dcontent";
	}

	/**
	 * 上传/更新drawing
	 */
	public String uploadDrawing(){
		//获取上传者的userId
		Subject subject = SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User user = userService.getUserByLoginName(loginName);

		//获取图纸的密级
		SecurityLevel securityLevel = securityLevelService.getSecurityLevelByName(securityLevelName);

		//封装Drawing类
		Drawing dr;
		//更新操作
		if(stringId != null){
			dr = drawingService.getDrawingByStringId(stringId);
			dr.setAlterDate(new Date());
			dr.setLastAlter(user);
		}
		else{
			//添加操作
			dr = new Drawing();
			dr.setStringId(drawingService.createUploadDrawingStringId());			
			dr.setUploadDate(new Date());
			dr.setAuthor(user);
		}
		dr.setDrawingName(drawingName);
		dr.setRemark(remark);
		dr.setSecurityLevel(securityLevel);
        dr.setImgName(imgName);
        
		//添加
        if(stringId != null){
        	try{
        		drawingService.updateDrawing(dr);
        		ActionContext.getContext().put("updateDrawingResult", "success");
        	}catch (Exception e) {
        		ActionContext.getContext().put("updateDrawingResult", "failed");
			}
        	
        }
        else{
        	try{
        		drawingService.addDrawing(dr); 
        		ActionContext.getContext().put("addDrawingResult", "success");
        	}catch (Exception e) {
        		ActionContext.getContext().put("addDrawingResult", "failed");
			}
        }
	
		return (searchContent==null)? "page":"content";
	}	
}
