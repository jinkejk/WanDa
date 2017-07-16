package com.wanda.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.IdentifierLoadAccess;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.wanda.beans.SignMaterial;
import com.wanda.beans.User;
import com.wanda.service.RoleService;
import com.wanda.service.UserService;

@Controller("OperateUser")
@Scope("prototype")  
public class OperateUser extends ActionSupport{

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;

	//获取用户名和密码
	private int userId;
	private String userName;
	private String trueName;
	private String password;
	private int userPageSize;
	private int userCurrentPage;
	private Integer roleId;
	private String position;
	private String dept;
	private String company;
	private String telephone;
	private String QQ;
	private String remark;
	private boolean logFlag;
	private String searchContent; //搜索用户关键字
    private String userIds;
    
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getSearchContent() {
		return searchContent;
	}
	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}
	public boolean isLogFlag() {
		return logFlag;
	}
	public void setLogFlag(boolean logFlag) {
		this.logFlag = logFlag;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getQQ() {
		return QQ;
	}
	public void setQQ(String qQ) {
		QQ = qQ;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}	
	public int getUserPageSize() {
		return userPageSize;
	}
	public void setUserPageSize(int userPageSize) {
		this.userPageSize = userPageSize;
	}
	public int getUserCurrentPage() {
		return userCurrentPage;
	}
	public void setUserCurrentPage(int userCurrentPage) {
		this.userCurrentPage = userCurrentPage;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 分页获取用户
	 * @return
	 */
	public String searchUserByPage(){
		//stuts 没有自动装配
		if(userPageSize == 0 || userCurrentPage ==0){
			userPageSize = 15;
			userCurrentPage = 1;			
		}

		//获取当前用户
		Subject subject = SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User currentUser = userService.getUserByLoginName(loginName);

		ActionContext.getContext().put("currentUser", currentUser);

		//获得总共的用户数目
		Long userTotalNum = userService.getTotalUserNum();		
		
		//计算总页数
		int totalPage = (int)Math.ceil((double)userTotalNum/userPageSize);
		
		if(userCurrentPage > totalPage)
			userCurrentPage = totalPage;
		//分页获得用户
		List<User> users = userService.getUsersByPage(userPageSize, userCurrentPage);


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
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("loginNameList", loginNameList);
		ActionContext.getContext().put("trueNameList", trueNameList);

		return "showUserList";
	}

	/**
	 * 根据id删除用户
	 * @return
	 */
	public String deleteUserById(){
		//获取当前用户
		Subject subject = SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User currentUser = userService.getUserByLoginName(loginName);

		//获得需要删除的用户
		User user = userService.getUserById(userId);

		ActionContext.getContext().put("currentUser", currentUser);
		//判断权限是否足够
		if(currentUser!=null && user!=null){
			if(currentUser.getUserId() != user.getUserId())
				if(!currentUser.getRole().getRoleName().equals("administrator")
						|| user.getRole().getRoleName().equals("administrator")){
					ActionContext.getContext().put("deleteResult", "failed");
					return searchUserByPage();
				}
		}else
			return searchUserByPage();

		userService.deleteUserById(userId);

		ActionContext.getContext().put("deleteResult", "success");

		return (searchContent==null)? "delete":"content";
	}

	/**
	 * 更新user
	 */
	public String updateUser(){
		//获取user
		User user = userService.getUserById(userId);
		//设置属性
		user.setPosition(position);
		user.setDept(dept);
		user.setCompany(company);
		user.setTelephone(telephone);
		user.setQQ(QQ);
		user.setRemark(remark);
		user.setRole(roleService.getRoleById(roleId));
		user.setLogFlag(logFlag);
		//更新
		userService.updateUser(user);

		return "update";
	}	

	/**
	 * 允许该用户登陆
	 */
	public String changeLogFlag(){
		//获取当前用户
		Subject subject = SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User currentUser = userService.getUserByLoginName(loginName);

		//获取该用户
		User user = userService.getUserById(userId);

		ActionContext.getContext().put("currentUser", currentUser);
		//判断权限是否足够
		if(currentUser!=null && user!=null){
			if(currentUser.getUserId() != user.getUserId())
				if(!currentUser.getRole().getRoleName().equals("administrator")){
					ActionContext.getContext().put("changeLogFlagResult", "failed");
					return searchUserByPage();				
				}
		}else{
			ActionContext.getContext().put("changeLogFlagResult", "failed");
			return searchUserByPage();
		}

		//修改为相反权限
		user.setLogFlag(!user.getLogFlag());

		try{
			userService.updateUser(user);			
			ActionContext.getContext().put("changeLogFlagResult", "success");
		}catch (Exception e) {
			ActionContext.getContext().put("changeLogFlagResult", "failed");
		}

		//返回修改结果
		return (searchContent==null)? "update":"content";
	}

	/**
	 * 按用户名或者真实姓名搜索用户
	 */
	public String searchUserByContent(){
		//去掉首尾空格,且替换多个空格为一个
		if(searchContent==null || searchContent.isEmpty()){
			return searchUserByPage();
		}
		//去掉首尾空格,且替换多个空格为一个
		searchContent = searchContent.trim().replaceAll("\\s+", " ");	
		
		//获取当前用户
		Subject subject = SecurityUtils.getSubject();
		String loginName = (String) subject.getPrincipal();
		User currentUser = userService.getUserByLoginName(loginName);

		ActionContext.getContext().put("currentUser", currentUser);
		
		//分页获得用户
		List<User> users = userService.getUsersByContentByPage(searchContent, userPageSize, userCurrentPage);

		//获得总共的符合要求的用户数目
		int userTotalNum = userService.getUserNumByContentByPage(searchContent).intValue();		

		//计算总页数
		int totalPage = (int)Math.ceil((double)userTotalNum/userPageSize);

		//设置标题长度，以及对相关字标红
		String[] searchArray = searchContent.split(" ");

		//去除重复的关键字
		Set<String> searchSet = new HashSet<String>(Arrays.asList(searchArray));

		//保存更改后的title
		List<String> loginNameList = new ArrayList<String>();
		List<String> trueNameList = new ArrayList<String>();

		if(users != null && users.size() > 0){
			for(int i=0; i<users.size(); i++){
				User user = users.get(i);
				//判断标题的长度
				String temploginName = user.getLoginName();            
				String temptrueName = (user.getTrueName()!=null)? user.getTrueName():"";

				//标红关键字
				for(String str: searchSet){ 		  
					if(temploginName.contains(str)){
						temploginName = temploginName.replaceAll(str, "<font color=\"red\">" + str + "</font>");
					} 
				}
				for(String str: searchSet){ 		  
					if(temptrueName.contains(str)){
						temptrueName = temptrueName.replaceAll(str, "<font color=\"red\">" + str + "</font>");
					} 
				}
				loginNameList.add(temploginName);
				trueNameList.add(temptrueName);
			}			
		}

		ActionContext.getContext().put("userTotalNum", userTotalNum);
		ActionContext.getContext().put("users", users);
		ActionContext.getContext().put("totalPage", totalPage);
		ActionContext.getContext().put("loginNameList", loginNameList);
		ActionContext.getContext().put("trueNameList", trueNameList);

		return "showUserList";
	}
	
	//批量删除user
	public String deleteUsers(){
		if(userIds != null){
			String[] ids = userIds.split(",");
			List<Integer> idList = new ArrayList<>();
			try{
				for(String id: ids){
					idList.add(Integer.parseInt(id));
				}
				//批量删除
				if(idList.size() > 0)
					userService.deleteUsers(idList);
				ActionContext.getContext().put("deleteResult", "success");
			}catch (Exception e) {
				ActionContext.getContext().put("deleteResult", "failed");
			}
		}else{
			ActionContext.getContext().put("deleteResult", "failed");
		}
		
		return (searchContent==null)? "delete":"content";
	}
	
	//批量认证
	public String changeUsersLogflag(){
		if(userIds != null){
			String[] ids = userIds.split(",");
			List<Integer> idList = new ArrayList<>();
			try{
				for(String id: ids){
					idList.add(Integer.parseInt(id));
				}
				//批量删除
				if(idList.size() > 0)
					userService.changeUsersLogflag(idList);
				ActionContext.getContext().put("changeLogFlagResult", "success");
			}catch (Exception e) {
				ActionContext.getContext().put("changeLogFlagResult", "failed");
			}
		}else{
			ActionContext.getContext().put("changeLogFlagResult", "failed");
		}
		
		return (searchContent==null)? "delete":"content";
	}
}
