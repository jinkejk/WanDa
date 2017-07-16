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
import com.wanda.beans.Drawing;
import com.wanda.beans.Solution;
import com.wanda.beans.User;
import com.wanda.service.DrawingService;
import com.wanda.service.SolutionService;
import com.wanda.service.UserService;

@Controller("SearchDrawing")
@Scope("prototype")  
public class SearchDrawing extends ActionSupport {

	@Resource
	private DrawingService drawingService;

	private int pageSize;
	private int currentPage;
	private int drawingId;
	private String searchContent;

	public int getDrawingId() {
		return drawingId;
	}

	public void setDrawingId(int drawingId) {
		this.drawingId = drawingId;
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

	//根据内容分页搜索图纸
	@Override
	public String execute() throws Exception {		
		//去掉首尾空格,且替换多个空格为一个
		if(searchContent==null || searchContent.isEmpty()){
			return searchDrawingByPage();
		}
		searchContent = searchContent.trim().replaceAll("\\s+", " ");		

		//分页获得图纸
		List<Drawing> drawings = drawingService.getDrawingByContentByPage(searchContent, pageSize, currentPage);

		//获得总数目
		int drawingTotalNum = drawingService.getTotalDrawingNumByContent(searchContent).intValue();		

		//计算总页数
		int totalPage = (int)Math.ceil((double)drawingTotalNum/pageSize);

		//设置标题长度，以及对相关字标红
		String[] searchArray = searchContent.split(" ");

		//去除重复的关键字
		Set<String> searchSet = new HashSet<String>(Arrays.asList(searchArray));

		//保存更改后的title
		List<String> titleList = new ArrayList<String>();

		for(int i=0; i<drawings.size(); i++){
			Drawing drawing = drawings.get(i);
			//判断标题的长度
			String tempName = drawing.getDrawingName().length() > 45?
					drawing.getDrawingName().substring(0, 45) : drawing.getDrawingName();            

					//标红关键字
					for(String str: searchSet){ 		  
						if(tempName.contains(str)){
							tempName = tempName.replaceAll(str, "<font color=\"red\">" + str + "</font>");
						} 
					}
					titleList.add(tempName);
		}

		ActionContext.getContext().put("drawingTotalNum", drawingTotalNum);
		ActionContext.getContext().put("drawings", drawings);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("titleList", titleList);
		ActionContext.getContext().put("currentPage", currentPage);
		ActionContext.getContext().put("pageSize", pageSize);

		return "drawingListResult";
	}

	/**
	 * 分页搜索图纸
	 */
	public String searchDrawingByPage() throws Exception {
		//设置默认值
		if(pageSize == 0 || currentPage ==0){
			pageSize = 6;
			currentPage = 1;
		}

		List<Drawing> drawings = drawingService.getDrawingsByPage(pageSize, currentPage);
		//保存更改后的title,这里截取一下长度
		List<String> titleList = new ArrayList<String>();
		for(int i=0; i<drawings.size(); i++){
			Drawing drawing = drawings.get(i);
			//判断标题的长度
			String tempName = drawing.getDrawingName().length() > 40?
					drawing.getDrawingName().substring(0, 40) : drawing.getDrawingName();            

					titleList.add(tempName);
		}	
		
		//获得总共的文章数目
		Long drawingTotalNum = drawingService.getTotalDrawingNum();		

		//计算总页数
		int totalPage = (int)Math.ceil((double)drawingTotalNum/pageSize);

		ActionContext.getContext().put("drawingTotalNum", drawingTotalNum);
		ActionContext.getContext().put("drawings", drawings);
		ActionContext.getContext().put("titleList", titleList);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("currentPage", currentPage);
		ActionContext.getContext().put("pageSize", pageSize);

		return "drawingListResult";
	}

	/**
	 * 根据drawing Id获取drawing
	 * @return
	 */
	public String searchDrawingById(){
		Drawing drawing = drawingService.getDrawingById(drawingId);
		ActionContext.getContext().put("drawing", drawing);

		return "showDrawing";
	}

}
