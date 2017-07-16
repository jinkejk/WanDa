package com.wanda.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.wanda.util.IsMobile;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wanda.beans.Drawing;
import com.wanda.beans.House;
import com.wanda.beans.QRInfor;
import com.wanda.beans.Role;
import com.wanda.beans.SecurityLevel;
import com.wanda.beans.SignMaterial;
import com.wanda.beans.Solution;
import com.wanda.beans.TrainingMaterial;
import com.wanda.beans.TrainingMaterialsCategory;
import com.wanda.beans.User;
import com.wanda.service.DrawingService;
import com.wanda.service.HouseService;
import com.wanda.service.RoleService;
import com.wanda.service.SecurityLevelService;
import com.wanda.service.SignMaterialService;
import com.wanda.service.SolutionSearchHistoryService;
import com.wanda.service.SolutionService;
import com.wanda.service.TrainingMaterialService;
import com.wanda.service.TrainingMaterialsCategoryService;
import com.wanda.service.UserService;
import com.wanda.util.QRcodeHandler;
import com.wanda.util.UtilCommon;

/**
 * 处理各种跳转
 * 方便判断权限
 * @author jinkejk
 *
 */
@Controller("CommonAction")
@Scope("prototype")
public class CommonAction extends ActionSupport{

	@Resource
	private SolutionService solutionService;
	@Resource
	private SecurityLevelService securityLevelService;
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private DrawingService drawingService;
	@Resource
	private HouseService houseService;
	@Resource
	private SolutionSearchHistoryService solutionSearchHistoryService;
	@Resource
	private TrainingMaterialService trainingMaterialService;
	@Resource
	private TrainingMaterialsCategoryService trainingMaterialsCategoryService;
	private int solutionId;
	private int drawingId;
	private int houseId;
	private int pageSize;
	private int currentPage;
	private Integer userId;
	private int userCurrentPage;
	private int userPageSize;   
	private File QRfile;
	private String LPsearch;
	private String LPName;
	private String HXName;
	private Integer TMId;
	private int TMCId;

	public int getTMCId() {
		return TMCId;
	}
	public void setTMCId(int tMCId) {
		TMCId = tMCId;
	}
	public Integer getTMId() {
		return TMId;
	}
	public void setTMId(Integer tMId) {
		TMId = tMId;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
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
	public String getLPsearch() {
		return LPsearch;
	}
	public void setLPsearch(String lPsearch) {
		LPsearch = lPsearch;
	}
	public File getQRfile() {
		return QRfile;
	}
	public void setQRfile(File qRfile) {
		QRfile = qRfile;
	}
	public int getDrawingId() {
		return drawingId;
	}
	public void setDrawingId(int drawingId) {
		this.drawingId = drawingId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public int getUserCurrentPage() {
		return userCurrentPage;
	}
	public void setUserCurrentPage(int userCurrentPage) {
		this.userCurrentPage = userCurrentPage;
	}
	public int getUserPageSize() {
		return userPageSize;
	}
	public void setUserPageSize(int userPageSize) {
		this.userPageSize = userPageSize;
	}
	public int getSolutionId() {
		return solutionId;
	}
	public void setSolutionId(int solutionId) {
		this.solutionId = solutionId;
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
	 * 跳转到解决方案列表页面
	 */
	public String showSolutionList() throws Exception {
		//设置默认值
		if(pageSize==0 || currentPage==0){
			pageSize = 6;
			currentPage = 1;
		}
		//分页获得文章
		List<Solution> solutions = solutionService.getSolutionByPage(pageSize, currentPage);

		//获得总共的文章数目
		Long solutionTotalNum = solutionService.getTotalSolutionNum();		

		//计算总页数
		int totalPage = (int)Math.ceil((double)solutionTotalNum/pageSize);
		//保存更改长度后的title
		List<String> titleList = new ArrayList<String>();

		if(solutions != null && solutions.size() > 0){
			for(int i=0; i<solutions.size(); i++){
				Solution solution = solutions.get(i);
				//判断标题的长度
				String tempName = solution.getTitle().length() > 40?
						solution.getTitle().substring(0, 40) : solution.getTitle();            

						titleList.add(tempName);
			}			
		}
		ActionContext.getContext().put("solutionTotalNum", solutionTotalNum);
		ActionContext.getContext().put("solutions", solutions);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("currentPage", currentPage);
		ActionContext.getContext().put("titleList", titleList);

		return "showSolutionList";
	}

	/**
	 * 跳转到图纸方案列表页面
	 */
	public String showDrawingList() throws Exception {		
		//设置默认值
		if(pageSize==0 || currentPage==0){
			pageSize = 6;
			currentPage = 1;
		}

		//分页获得图纸
		List<Drawing> drawings = drawingService.getDrawingsByPage(pageSize, currentPage);

		//保存更改后的title,这里截取一下长度
		List<String> titleList = new ArrayList<String>();
		for(int i=0; i<drawings.size(); i++){
			Drawing drawing = drawings.get(i);
			if(drawing.getDrawingName()!=null){
				//判断标题的长度
				String tempName = drawing.getDrawingName().length() > 40?
						drawing.getDrawingName().substring(0, 40) : drawing.getDrawingName();            

						titleList.add(tempName);				
			}else{
				titleList.add("");
			}
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

		return "showDrawingList";
	}

	/**
	 * 跳转到房型筛选页面
	 */
	public String showHouseList() throws Exception {
		//设置默认值
		if(pageSize==0 || currentPage==0){
			pageSize = 6;
			currentPage = 1;
		}

		//分页获得房型
		List<House> houses = houseService.getHousesByPage(pageSize, currentPage);

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
	 * 跳转到上传解决方案页面
	 */
	public String uploadSolution() throws Exception {
		//获取所有securityLevels 生成下拉列表
		List<SecurityLevel> securityLevels = securityLevelService.getAllSecurityLevels();
		ActionContext.getContext().put("securityLevels", securityLevels);

		//获取所有的培训资料一级类别
		List<TrainingMaterialsCategory> firstLevelTMCs = trainingMaterialsCategoryService.getAllFirstLevelTMCByModule("solution");
		ActionContext.getContext().put("firstLevelTMCs", firstLevelTMCs);

		//获取第一个一级类别的二级类别
		if(firstLevelTMCs != null){
			List<TrainingMaterialsCategory> secondLevelTMCs = trainingMaterialsCategoryService.getSecondLevelTMCByFirstLevelTMCId(
					firstLevelTMCs.get(0).getTMCId());
			ActionContext.getContext().put("secondLevelTMCs", secondLevelTMCs);			
		}

		return "uploadSolution";
	}

	/**
	 * 跳转到上传图纸方案页面
	 */
	public String uploadDrawing() throws Exception {
		//获取所有securityLevels 生成下拉列表
		List<SecurityLevel> securityLevels = securityLevelService.getAllSecurityLevels();
		ActionContext.getContext().put("securityLevels", securityLevels);

		return "uploadDrawing";
	}

	/**
	 * 跳转到上传户型方案页面
	 */
	public String uploadHouse() throws Exception {

		return "uploadHouse";
	}

	/**
	 * 跳转到更新解决方案页面
	 */
	public String updateSolution() throws Exception {
		//获取所有securityLevels 生成下拉列表
		List<SecurityLevel> securityLevels = securityLevelService.getAllSecurityLevels();
		ActionContext.getContext().put("securityLevels", securityLevels);

		//获取所有的培训资料一级类别
		List<TrainingMaterialsCategory> firstLevelTMCs = trainingMaterialsCategoryService.getAllFirstLevelTMCByModule("solution");
		ActionContext.getContext().put("firstLevelTMCs", firstLevelTMCs);

		//获取解决方案
		Solution solution = solutionService.getSolutionById(solutionId);
		ActionContext.getContext().put("solution", solution);

		//若训练资料是二级分类，则获取该二级下的所有一级
		if(solution!=null && solution.getCategory()!=null && solution.getCategory().getParentTMC()!=null){
			List<TrainingMaterialsCategory> secondLevelTMCs = trainingMaterialsCategoryService.getSecondLevelTMCByFirstLevelTMCId(
					solution.getCategory().getParentTMC().getTMCId());
			ActionContext.getContext().put("secondLevelTMCs", secondLevelTMCs);
		}	

		return "updateSolution";
	}

	/**
	 * 跳转到更新图纸页面
	 */
	public String updateDrawing() throws Exception {
		//获取所有securityLevels 生成下拉列表
		List<SecurityLevel> securityLevels = securityLevelService.getAllSecurityLevels();
		ActionContext.getContext().put("securityLevels", securityLevels);

		//获取图纸方案
		Drawing drawing = drawingService.getDrawingById(drawingId);
		ActionContext.getContext().put("drawing", drawing);

		return "updateDrawing";
	}

	/**
	 * 跳转到更新户型页面
	 */
	public String updateHouse(){
		House house = houseService.getHouseById(houseId);
		ActionContext.getContext().put("house", house);

		return "updateHouse";
	}

	/**
	 * 跳转到用户列表页面
	 */
	public String showUserList(){
		//获取当前用户
		Subject subject = SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User currentUser = userService.getUserByLoginName(loginName);

		//设置默认值
		if(userPageSize==0 || userCurrentPage==0){
			userPageSize = 6;
			userCurrentPage = 1;
		}
		//分页获得用户
		List<User> users = userService.getUsersByPage(userPageSize, userCurrentPage);

		//获得总共的用户数目
		Long userTotalNum = userService.getTotalUserNum();		

		//计算总页数
		int totalPage = (int)Math.ceil((double)userTotalNum/userPageSize);
		//保存更改后的title
		List<String> loginNameList = new ArrayList<String>();
		List<String> trueNameList = new ArrayList<String>();

		if(users != null && users.size() > 0){
			for(int i=0; i<users.size(); i++){
				User user = users.get(i);
				//判断标题的长度
				String temploginName = user.getLoginName();            
				String temptrueName = (user.getTrueName()!=null)? user.getTrueName():"";

				loginNameList.add(temploginName);
				trueNameList.add(temptrueName);
			}			
		}
		ActionContext.getContext().put("userTotalNum", userTotalNum);
		ActionContext.getContext().put("users", users);
		ActionContext.getContext().put("currentUser", currentUser);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("userCurrentPage", userCurrentPage);
		ActionContext.getContext().put("userPageSize", userPageSize);
		ActionContext.getContext().put("loginNameList", loginNameList);
		ActionContext.getContext().put("trueNameList", trueNameList);

		return "showUserList";
	}

	/**
	 * 跳转到修改用户页面
	 */
	public String editUser(){
		//获取当前用户
		Subject subject = SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User currentUser = userService.getUserByLoginName(loginName);

		ActionContext.getContext().put("currentUser", currentUser);
		//获得需要修改的用户
		User user = userService.getUserById(userId);

		//判断权限是否足够
		if(currentUser!=null && user!=null){
			if(currentUser.getUserId() != user.getUserId())
				if(!currentUser.getRole().getRoleName().equals("administrator")
						|| user.getRole().getRoleName().equals("administrator"))
					return "noAuthority";
		}else
			return "noAuthority";

		//获得所有角色
		Set<Role> roles = roleService.getAllRoles();

		ActionContext.getContext().put("roles", roles);
		ActionContext.getContext().put("user", user);

		ActionContext.getContext().put("changeLogFlagResult", "null");
		ActionContext.getContext().put("deleteResult", "null");

		return "editUser";
	}

	/**
	 * 跳转到添加用户页面
	 */
	public String addUser(){
		//获得低于当前用户的所有角色
		Set<Role> roles = roleService.getAllRoles();

		ActionContext.getContext().put("roles", roles);

		return "addUser";
	}

	/**
	 * 异步识别二维码
	 */
	public String decodeQRImg(){
		String decodeString = null;
		try{
			//第二个参数是变换图片的大小
			decodeString = QRcodeHandler.decode(QRfile,600);		
		}catch (Exception e) {
			ActionContext.getContext().put("msg", "无结果！");
			ActionContext.getContext().put("decodeResult", "无结果！");
			return "input";
		}

		try {
			//是图纸的二维码
			if(decodeString !=null){
				//判断是否是json格式
				if(UtilCommon.isGoodJson(decodeString)){
					try{
						JsonParser parser = new JsonParser();  //创建JSON解析器
						JsonObject object = (JsonObject) parser.parse(decodeString);  //创建JsonObject对象			        
						JsonArray array = object.get("image").getAsJsonArray();  //创建JsonArray对象
						String imageUrl = array.get(0).getAsJsonObject().get("imageUrl").getAsString();

						//找到fiieldId
						String fieldId = imageUrl.substring(imageUrl.lastIndexOf("=") + 1);
						Drawing drawing = drawingService.getDrawingByFieldId(fieldId);

						if(drawing != null){
							ActionContext.getContext().put("drawing", drawing);
							return "showQRDrawingDetail";
						}else{
							//未获取到drawing
							ActionContext.getContext().put("msg", "二维码已失效！");
							ActionContext.getContext().put("decodeResult", "二维码已失效！");
							return "input";
						}
					}catch (Exception e) {
						ActionContext.getContext().put("msg", "出错啦!");
						ActionContext.getContext().put("decodeResult", "出错啦!");
						return "input";
					}
				}else{
					//不是json格式
					Drawing drawing = drawingService.getDrawingByImgName(decodeString);

					//判断二维码是否失效
					if(drawing == null && isDrawingQRcode(decodeString)){
						ActionContext.getContext().put("msg", "二维码已失效！");
						ActionContext.getContext().put("decodeResult", "二维码已失效！");
						return "input";
					}

					//识别正确
					if(drawing != null){
						ActionContext.getContext().put("drawing", drawing);
						return "showQRDrawingDetail";
					}

					//其他二维码
					if(drawing == null || !isDrawingQRcode(decodeString)){
						//若是特定的二维码
						if(isDefiniteQRcode(decodeString)){
							QRInfor qRInfo = UtilCommon.readQRInfo(decodeString);
							ActionContext.getContext().put("qRInfo", qRInfo);
						}
						ActionContext.getContext().put("decodeString", decodeString);
						return "showQRString";					
					}

				}
			}else{
				ActionContext.getContext().put("msg", "无结果！");
				ActionContext.getContext().put("decodeResult", "无结果！");
				return "input";
			}
		} catch (Exception e) {
			ActionContext.getContext().put("msg", "出错啦!");
			ActionContext.getContext().put("decodeResult", "出错啦!");
			return "input";
		}
		return "input";
	}

	//判断二维码是否是图纸二维码
	private boolean isDrawingQRcode(String decodeString){
		// 验证规则
		String regEx = "^\\d{4}\\_\\d{2}\\_\\d{2}\\_\\d{2}\\_\\d{2}\\_\\d{2}\\_\\d{3}$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		// 忽略大小写的写法
		// Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(decodeString);

		// 字符串是否与正则表达式相匹配
		return matcher.matches();			
	}

	//判断二维码是否是指定二维码，待完善、
	private boolean isDefiniteQRcode(String decodeString){
		List<String> split = Arrays.asList(decodeString.split("\n"));

		if(split.get(0).substring(0, 2).equals("图号") && split.get(1).substring(0, 2).equals("图名")
				&& split.get(2).substring(0, 4).equals("工程名称"))
			return true;
		else
			return false;
	}

	//获取楼盘名的json数据
	public String getLPNameJson(){
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = null;
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码 

		try {
			out = response.getWriter();
			String json = houseService.getAllLPName();
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

	//获取楼盘名的json数据
	public String getHXNameJson(){
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = null;
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码 

		try {
			out = response.getWriter();
			String json = houseService.getHXNameByLPName(LPsearch);
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

	//根据楼盘和户型查询具体户型
	public String getHouseByLPAndHXName(){
		HttpServletResponse response = ServletActionContext.getResponse();
		PrintWriter out = null;
		response.setCharacterEncoding("utf-8"); // 务必，防止返回文件名是乱码 

		try {
			out = response.getWriter();
			House newHouse = houseService.getHouseByLPAndHXName(LPName, HXName);
			if(newHouse != null){
				//转为json,为保证可以转换，设置author等为null
				newHouse.setAuthor(null);
				newHouse.setLastAlter(null);
				String data = new Gson().toJson(newHouse);
				out.write(data);
			}
			else
				out.write("failed");
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

	//跳转到专家查询系统页面
	public String searchSystem(){
		int num = 6;
		//获取最近查询关键字
		List<String> lastKeywords = solutionSearchHistoryService.getLastSearchContent(num);

		//获取最近访问的条目
		List<Solution> lastVisitSolution = solutionService.getLastVisitSolutionTitle(num);

		//获取热门专家库条目
		List<Solution> hotVisitSolution = solutionService.getHotVisitSolutionTitle(num);

		ActionContext.getContext().put("lastKeywords", lastKeywords);
		ActionContext.getContext().put("lastVisitSolution", lastVisitSolution);
		ActionContext.getContext().put("hotVisitSolution", hotVisitSolution);
		ActionContext.getContext().put("num", num);
		ActionContext.getContext().put("pageSize", 15);
		ActionContext.getContext().put("currentPage", 1);
		ActionContext.getContext().put("totalPage", 0);
		ActionContext.getContext().put("solutionTotalNum", 0);

		return "searchSystem";
	}

	/**
	 * 后台登陆前判断
	 * 若是管理员账户则直接跳转到管理员界面
	 * @return
	 */
	public String manageLogin(){
		//获取当前用户
		Subject subject = SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User currentUser = userService.getUserByLoginName(loginName);

		if(currentUser == null)
			return "manageLogin";

		//判断是否是后台账户
		if(currentUser.getRole().getRoleRemark().equals("管理员") || currentUser.getRole().getRoleRemark().equals("编辑员")){
			//后台账户
			return "manage";			
		}else{
			return "manageLogin";
		}
	}

	/**
	 * 跳转到前台培训资料页面
	 * 默认查找最新的num条记录
	 */
	public String lastTrainingMaterial(){
		int num = 10;

		//查找最新的num条记录
		List<TrainingMaterial> lastTrainingMaterials = trainingMaterialService.getLastTrainingMaterial(num);
		
		//查询一级二级目录
		List<TrainingMaterialsCategory> allTMCs = trainingMaterialsCategoryService.getAllTMCByModule("training", Integer.MAX_VALUE, 1);
		List<TrainingMaterialsCategory> firstLevelTMC = trainingMaterialsCategoryService.getAllFirstLevelTMCByModule("training");
		List<TrainingMaterialsCategory> secondLevelTMC = new ArrayList<>();
		if(allTMCs != null && allTMCs.size() > 0){
			//划分成一级和二级目录
			for(TrainingMaterialsCategory allTMC: allTMCs){
				if(allTMC.getParentTMC()!=null)
					secondLevelTMC.add(allTMC);
			}
		}

		ActionContext.getContext().put("firstLevelTMC", UtilCommon.listToJson(firstLevelTMC));
		ActionContext.getContext().put("secondLevelTMC", UtilCommon.listToJson(secondLevelTMC));
		ActionContext.getContext().put("lastTrainingMaterials", lastTrainingMaterials);

		return "lastTrainingMaterials";
	}

	/**
	 * 跳转到前台签批资料页面
	 * 默认查找最新的num条记录
	 */
	public String lastSignMaterial(){
		int num = 6;

		//查找最新的num条记录
		List<SignMaterial> lastSignMaterials = signMaterialService.getLastSignMaterial(num);

		//查询一级二级目录
		List<TrainingMaterialsCategory> allTMCs = trainingMaterialsCategoryService.getAllTMCByModule("sign", Integer.MAX_VALUE, 1);
		List<TrainingMaterialsCategory> firstLevelTMC = trainingMaterialsCategoryService.getAllFirstLevelTMCByModule("sign");
		List<TrainingMaterialsCategory> secondLevelTMC = new ArrayList<>();
		if(allTMCs != null && allTMCs.size() > 0){
			//划分成一级和二级目录
			for(TrainingMaterialsCategory allTMC: allTMCs){
				if(allTMC.getParentTMC()!=null)
					secondLevelTMC.add(allTMC);
			}
		}
		ActionContext.getContext().put("firstLevelTMC", UtilCommon.listToJson(firstLevelTMC));
		ActionContext.getContext().put("secondLevelTMC", UtilCommon.listToJson(secondLevelTMC));
		ActionContext.getContext().put("lastSignMaterials", lastSignMaterials);

		return "lastSignMaterials";
	}

	/**
	 * 跳转到前台签批资料页面
	 * 默认查找最新的num条记录
	 */
	public String lastSolutions() throws IOException{
		//获取最近添加的方案
		List<Solution> solutions = solutionService.getLastSolutions(10);

		//获取最近查询关键字
		List<String> lastKeywords = solutionSearchHistoryService.getLastSearchContent(6);

		//获取热门专家库条目
		List<Solution> hotVisitSolution = solutionService.getHotVisitSolutionTitle(6);

		//查询一级二级目录
		List<TrainingMaterialsCategory> allTMCs = trainingMaterialsCategoryService.getAllTMCByModule("solution", Integer.MAX_VALUE, 1);
		List<TrainingMaterialsCategory> firstLevelTMC = trainingMaterialsCategoryService.getAllFirstLevelTMCByModule("solution");
		List<TrainingMaterialsCategory> secondLevelTMC = new ArrayList<>();
		if(allTMCs != null && allTMCs.size() > 0){
			//划分成一级和二级目录
			for(TrainingMaterialsCategory allTMC: allTMCs){
				if(allTMC.getParentTMC()!=null)
					secondLevelTMC.add(allTMC);
			}
		}

		ActionContext.getContext().put("firstLevelTMC", UtilCommon.listToJson(firstLevelTMC));
		ActionContext.getContext().put("secondLevelTMC", UtilCommon.listToJson(secondLevelTMC));
		ActionContext.getContext().put("solutions", solutions);
		ActionContext.getContext().put("lastKeywords", lastKeywords);
		ActionContext.getContext().put("hotVisitSolution", hotVisitSolution);

		return IsMobile.check(ServletActionContext.getRequest())? "lastSolutions_mobile":"lastSolutions";
	}

	/**
	 * 跳转到显示详细信息页面
	 */
	public String showTrainingMaterialDetail(){
		//获取当前用户
		Subject subject = SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User currentUser = userService.getUserByLoginName(loginName);

		int  userLevel = currentUser.getRole().getSecurityLevel().getSecurityLevelValue();
		TrainingMaterial trainingMaterial = null;

		if(TMId != null)
			trainingMaterial = trainingMaterialService.visitTrainingMaterialById(TMId);

		//判断密级
		if(trainingMaterial.getSecurityLevel()!=null && userLevel < trainingMaterial.getSecurityLevel().getSecurityLevelValue()){
			//无权限
			return "unauthorized";
		}

		ActionContext.getContext().put("trainingMaterial", trainingMaterial);
		return "showTrainingMaterialDetail";
	}

	/**
	 * 跳转到培训资料列表页面
	 */
	public String showTrainingMaterialList(){
		//设置默认值
		if(pageSize==0 || currentPage==0){
			pageSize = 6;
			currentPage = 1;
		}

		//分页获得培训资料
		List<TrainingMaterial> trainingMaterials = trainingMaterialService.getTrainingMaterialByPage(pageSize, currentPage);

		//获得总共的培训资料数目
		Long trainingMaterialNum = trainingMaterialService.getTotalTrainingMaterialNum();		

		//计算总页数
		int totalPage = (int)Math.ceil((double)trainingMaterialNum/pageSize);

		List<String> titleList = new ArrayList<String>();
		if(trainingMaterials != null){			
			//保存更改后的title
			for(int i=0; i<trainingMaterials.size(); i++){
				TrainingMaterial trainingMaterial = trainingMaterials.get(i);

				String tempTitle = "";
				if(trainingMaterial.getTitle() != null){
					//判断标题的长度
					tempTitle = trainingMaterial.getTitle().length() > 40?
							trainingMaterial.getTitle().substring(0, 40) : trainingMaterial.getTitle();			
				}

				titleList.add(tempTitle);
			}
		}
		ActionContext.getContext().put("trainingMaterialNum", trainingMaterialNum);
		ActionContext.getContext().put("trainingMaterials", trainingMaterials);
		ActionContext.getContext().put("titleList", titleList);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("currentPage", currentPage);
		ActionContext.getContext().put("pageSize", pageSize);

		return "showTrainingMaterialList";
	}

	/**
	 * 跳转到上传培训资料页面
	 */
	public String uploadTrainingMaterial(){
		//获取所有securityLevels 生成下拉列表
		List<SecurityLevel> securityLevels = securityLevelService.getAllSecurityLevels();
		ActionContext.getContext().put("securityLevels", securityLevels);

		//获取所有的培训资料一级类别
		List<TrainingMaterialsCategory> firstLevelTMCs = trainingMaterialsCategoryService.getAllFirstLevelTMCByModule("training");
		ActionContext.getContext().put("firstLevelTMCs", firstLevelTMCs);

		//获取第一个一级类别的二级类别
		if(firstLevelTMCs != null){
			List<TrainingMaterialsCategory> secondLevelTMCs = trainingMaterialsCategoryService.getSecondLevelTMCByFirstLevelTMCId(
					firstLevelTMCs.get(0).getTMCId());
			ActionContext.getContext().put("secondLevelTMCs", secondLevelTMCs);
		}
		return "uploadTrainingMaterial";
	}

	/**
	 * 跳转到更新培训资料页面
	 * @return
	 */
	public String updateTrainingMaterial(){
		//获取所有securityLevels 生成下拉列表
		List<SecurityLevel> securityLevels = securityLevelService.getAllSecurityLevels();
		ActionContext.getContext().put("securityLevels", securityLevels);

		//获取所有的培训资料一级类别
		List<TrainingMaterialsCategory> firstLevelTMCs = trainingMaterialsCategoryService.getAllFirstLevelTMCByModule("training");
		ActionContext.getContext().put("firstLevelTMCs", firstLevelTMCs);

		TrainingMaterial trainingMaterial = trainingMaterialService.getTrainingMaterialById(TMId);
		ActionContext.getContext().put("trainingMaterial", trainingMaterial);

		//若训练资料是二级分类，则获取该二级下的所有一级
		if(trainingMaterial!=null && trainingMaterial.getCategory()!=null && trainingMaterial.getCategory().getParentTMC()!=null){
			List<TrainingMaterialsCategory> secondLevelTMCs = trainingMaterialsCategoryService.getSecondLevelTMCByFirstLevelTMCId(
					trainingMaterial.getCategory().getParentTMC().getTMCId());
			ActionContext.getContext().put("secondLevelTMCs", secondLevelTMCs);
		}

		return "updateTrainingMaterial";
	}

	/**
	 * 跳转到管理分类页面
	 */
	public String showCategoryList(){
		//设置默认值
		if(pageSize==0 || currentPage==0){
			pageSize = 6;
			currentPage = 1;
		}

		//分页获得分类
		List<TrainingMaterialsCategory> categorys = trainingMaterialsCategoryService.getTMCByPage(pageSize, currentPage);

		//获得总共的分类数目
		Long categoryNum = trainingMaterialsCategoryService.getTotalTMCNum();		

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
	 * 跳转到上传分类页
	 */
	public String uploadCategory(){

		return "uploadCategory";
	}

	/**
	 * 跳转到更新分类页
	 */
	public String updateCategory(){
		//获取该类别
		TrainingMaterialsCategory category = trainingMaterialsCategoryService.getTrainingMaterialsCategoryById(TMCId);
		ActionContext.getContext().put("category", category);

		return "updateCategory";
	}

	@Resource
	private SignMaterialService signMaterialService;
	private int SMId;

	public int getSMId() {
		return SMId;
	}
	public void setSMId(int sMId) {
		SMId = sMId;
	}
	/**
	 * 跳转到签批资料列表页面
	 */
	public String showSignMaterialList(){
		//设置默认值
		if(pageSize==0 || currentPage==0){
			pageSize = 5;
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
		ActionContext.getContext().put("titleList", titleList);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("currentPage", currentPage);
		ActionContext.getContext().put("pageSize", pageSize);

		return "showSignMaterialsList";
	}

	/**
	 * 跳转到上传签批资料页面
	 */
	public String uploadSignMaterial(){
		//获取所有securityLevels 生成下拉列表
		List<SecurityLevel> securityLevels = securityLevelService.getAllSecurityLevels();
		ActionContext.getContext().put("securityLevels", securityLevels);

		//获取所有的培训资料一级类别
		List<TrainingMaterialsCategory> firstLevelTMCs = trainingMaterialsCategoryService.getAllFirstLevelTMCByModule("sign");
		ActionContext.getContext().put("firstLevelTMCs", firstLevelTMCs);

		//获取第一个一级类别的二级类别
		if(firstLevelTMCs != null){
			List<TrainingMaterialsCategory> secondLevelTMCs = trainingMaterialsCategoryService.getSecondLevelTMCByFirstLevelTMCId(
					firstLevelTMCs.get(0).getTMCId());
			ActionContext.getContext().put("secondLevelTMCs", secondLevelTMCs);			
		}
		return "uploadSignMaterial";
	}

	/**
	 * 跳转到更新签批资料页面
	 * @return
	 */
	public String updateSignMaterial(){
		//获取所有securityLevels 生成下拉列表
		List<SecurityLevel> securityLevels = securityLevelService.getAllSecurityLevels();
		ActionContext.getContext().put("securityLevels", securityLevels);

		//获取所有的培训资料一级类别
		List<TrainingMaterialsCategory> firstLevelTMCs = trainingMaterialsCategoryService.getAllFirstLevelTMCByModule("sign");
		ActionContext.getContext().put("firstLevelTMCs", firstLevelTMCs);

		SignMaterial signMaterial = signMaterialService.getSignMaterialById(SMId);
		ActionContext.getContext().put("signMaterial", signMaterial);

		//若训练资料是二级分类，则获取该二级下的所有一级
		if(signMaterial!=null && signMaterial.getCategory()!=null && signMaterial.getCategory().getParentTMC()!=null){
			List<TrainingMaterialsCategory> secondLevelTMCs = trainingMaterialsCategoryService.getSecondLevelTMCByFirstLevelTMCId(
					signMaterial.getCategory().getParentTMC().getTMCId());
			ActionContext.getContext().put("secondLevelTMCs", secondLevelTMCs);
		}

		return "updateSignMaterial";
	}

	/**
	 * 跳转到显示详细信息页面
	 */
	public String showSignMaterialDetail(){
		SignMaterial signMaterial = null;

		if(SMId != 0)
			signMaterial = signMaterialService.getSignMaterialById(SMId);

		ActionContext.getContext().put("signMaterial", signMaterial);

		return "showSignMaterialDetail";
	}

	/**
	 * 下载签批资料
	 */
	public String downloadSignMaterial(){

		return "";
	}
}
