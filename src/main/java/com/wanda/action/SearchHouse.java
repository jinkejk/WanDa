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
import com.wanda.beans.House;
import com.wanda.beans.User;
import com.wanda.service.HouseService;
import com.wanda.service.UserService;

@Controller("SearchHouse")
@Scope("prototype")  
public class SearchHouse extends ActionSupport {

	@Resource
	private HouseService houseService;
	@Resource
	private UserService userService;

	private int pageSize;
	private int currentPage;
	private int houseId;
	private String searchContent;

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
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

	//根据内容分页搜索户型
	@Override
	public String execute() throws Exception {		
		//去掉首尾空格,且替换多个空格为一个
		if(searchContent==null || searchContent.isEmpty()){
			return searchHouseByPage();
		}
		searchContent = searchContent.trim().replaceAll("\\s+", " ");		

		//分页获得户型
		List<House> houses = houseService.getHousesByContentByPage(searchContent, pageSize, currentPage);

		//获得总数目
		int houseTotalNum = houseService.getTotalHouseNumByContent(searchContent).intValue();		

		//计算总页数
		int totalPage = (int)Math.ceil((double)houseTotalNum/pageSize);

		//设置标题长度，以及对相关字标红
		String[] searchArray = searchContent.split(" ");

		//去除重复的关键字
		Set<String> searchSet = new HashSet<String>(Arrays.asList(searchArray));

		//保存更改后的title
		List<String> HXNameList = new ArrayList<String>();
		List<String> LPNameList = new ArrayList<String>();

		for(int i=0; i<houses.size(); i++){
			House house = houses.get(i);

			String tempHXName = "";
			String tempLPName = "";
			if(house.getHXName() != null){
				//判断标题的长度
				tempHXName = house.getHXName().length() > 40?
						house.getHXName().substring(0, 40) : house.getHXName();			
			}

			if(house.getLPName() != null){
				tempLPName = house.getLPName().length() > 40?
						house.getLPName().substring(0, 40) : house.getLPName();		
			}

			//标红关键字
			for(String str: searchSet){ 		  
				if(tempHXName.contains(str)){
					tempHXName = tempHXName.replaceAll(str, "<font color=\"red\">" + str + "</font>");
				} 
				if(tempLPName.contains(str)){
					tempLPName = tempLPName.replaceAll(str, "<font color=\"red\">" + str + "</font>");
				} 
			}
			HXNameList.add(tempHXName);
			LPNameList.add(tempLPName);
		}

		ActionContext.getContext().put("houseTotalNum", houseTotalNum);
		ActionContext.getContext().put("houses", houses);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("HXNameList", HXNameList);
		ActionContext.getContext().put("LPNameList", LPNameList);
		ActionContext.getContext().put("currentPage", currentPage);
		ActionContext.getContext().put("pageSize", pageSize);

		return "showHouseList";
	}

	/**
	 * 分页搜索户型
	 */
	public String searchHouseByPage() throws Exception {
		//设置默认值
		if(pageSize == 0 || currentPage ==0){
			pageSize = 6;
			currentPage = 1;
		}

		List<House> houses = houseService.getHousesByPage(pageSize, currentPage);
		if(houses == null)
			return "showHouseList";

		//获得总共的户型数目
		Long houseTotalNum = houseService.getTotalHouseNum();		

		//计算总页数
		int totalPage = (int)Math.ceil((double)houseTotalNum/pageSize);

		//保存更改后的title
		List<String> HXNameList = new ArrayList<String>();
		List<String> LPNameList = new ArrayList<String>();
		for(int i=0; i<houses.size(); i++){
			House house = houses.get(i);

			String tempHXName = "";
			String tempLPName = "";
			if(house.getHXName() != null){
				//判断标题的长度
				tempHXName = house.getHXName().length() > 40?
						house.getHXName().substring(0, 40) : house.getHXName();			
			}

			if(house.getLPName() != null){
				tempLPName = house.getLPName().length() > 40?
						house.getLPName().substring(0, 40) : house.getLPName();		
			}

			HXNameList.add(tempHXName);
			LPNameList.add(tempLPName);
		}

		ActionContext.getContext().put("houseTotalNum", houseTotalNum);
		ActionContext.getContext().put("houses", houses);
		ActionContext.getContext().put("HXNameList", HXNameList);
		ActionContext.getContext().put("LPNameList", LPNameList);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("currentPage", currentPage);
		ActionContext.getContext().put("pageSize", pageSize);

		return "showHouseList";
	}

	/**
	 * 根据houseId获取house
	 * @return
	 */
	public String searchHouseById(){
		//获取搜索者
		Subject subject = SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User user = userService.getUserByLoginName(loginName);
		
		House house = houseService.visitHouseById(houseId, user);
		ActionContext.getContext().put("house", house);				

		return "showHouseDetail";
	}

}
