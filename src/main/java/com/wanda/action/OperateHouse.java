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
import com.wanda.beans.House;
import com.wanda.beans.SecurityLevel;
import com.wanda.beans.User;
import com.wanda.service.DrawingService;
import com.wanda.service.HouseService;
import com.wanda.service.SecurityLevelService;
import com.wanda.service.UserService;

@Controller("OperateHouse")
@Scope("prototype")  
public class OperateHouse extends ActionSupport {

	@Resource
	private HouseService houseService;;
	@Resource
	private UserService userService;

	private int houseId;
	private String LPName;
	private String HXName;
	private float area;
	private float rate;
	private int layer;
	private String outWinType;
	private String warmMaterial;
	private String insulation;
	private String soundProof;
	private String shadeType;
	private String hasSolar;
	private String imgName;
	private String searchContent; //记实是否为搜索内容结果，返回页面不同

	public String getLPName() {
		return LPName;
	}

	public void setLPName(String lPName) {
		LPName = lPName;
	}

	public String getHXName() {
		return HXName;
	}

	public void setHXName(String hXName) {
		HXName = hXName;
	}

	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public int getLayer() {
		return layer;
	}

	public void setLayer(int layer) {
		this.layer = layer;
	}

	public String getOutWinType() {
		return outWinType;
	}

	public void setOutWinType(String outWinType) {
		this.outWinType = outWinType;
	}

	public String getWarmMaterial() {
		return warmMaterial;
	}

	public void setWarmMaterial(String warmMaterial) {
		this.warmMaterial = warmMaterial;
	}

	public String getInsulation() {
		return insulation;
	}

	public void setInsulation(String insulation) {
		this.insulation = insulation;
	}

	public String getSoundProof() {
		return soundProof;
	}

	public void setSoundProof(String soundProof) {
		this.soundProof = soundProof;
	}

	public String getShadeType() {
		return shadeType;
	}

	public void setShadeType(String shadeType) {
		this.shadeType = shadeType;
	}

	public String getHasSolar() {
		return hasSolar;
	}

	public void setHasSolar(String hasSolar) {
		this.hasSolar = hasSolar;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public String getSearchContent() {
		return searchContent;
	}

	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}

	/**
	 * 根据houseId 删除house
	 */
	public String deleteHouseById(){
		try{
			houseService.deleteHouseById(houseId);
			ActionContext.getContext().put("deleteResult", "success");
		}catch (Exception e) {
			ActionContext.getContext().put("deleteResult", "failed");
		}

		//判断是否是在搜索内容中删除的
		return (searchContent==null)? "page":"content";
	}

	/**
	 * 上传/更新house
	 */
	public String uploadHouse(){
		//获取上传者的userId
		Subject subject = SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User user = userService.getUserByLoginName(loginName);

		//封装Drawing类
		House house;
		//更新操作
		if(houseId != 0){
			house = houseService.getHouseById(houseId);
			house.setAlterDate(new Date());
			house.setLastAlter(user);
		}
		else{
			//添加操作
			house = new House();
			house.setAttention(0);
			house.setCreateDate(new Date());
			house.setAuthor(user);
		}
		house.setLPName(LPName);
		house.setHXName(HXName);
		house.setArea(area);
		house.setRate(rate);
		house.setLayer(layer);
		house.setOutWinType(outWinType);
		house.setWarmMaterial(warmMaterial);
		house.setInsulation(insulation);
		house.setSoundProof(soundProof);
		house.setShadeType(shadeType);
		house.setHasSolar(hasSolar);
        house.setImgName(imgName);
        
		//添加
        if(houseId != 0){
        	try{        		
        		houseService.updateHouse(house);
    			ActionContext.getContext().put("updateHouseResult", "success");
        	}catch (Exception e) {
    			ActionContext.getContext().put("updateHouseResult", "failed");
			}
        }
        else{
        	try{
        		houseService.addHouse(house); 
        		ActionContext.getContext().put("addHouseResult", "success");
        	}catch (Exception e) {
        		ActionContext.getContext().put("addHouseResult", "failed");
			}
        }
	
		return (searchContent==null)? "page":"content";
	}	
}
