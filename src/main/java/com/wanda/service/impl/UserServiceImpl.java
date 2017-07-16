package com.wanda.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wanda.beans.Permission;
import com.wanda.beans.Solution;
import com.wanda.beans.User;
import com.wanda.dao.UserDao;
import com.wanda.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Resource
	private UserDao userDao;
	
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public User getUserByLoginName(String loginName) {
		return userDao.getUserByLoginName(loginName);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public User getUserById(Integer userId) {
		return userDao.getUserById(userId);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Set<User> getUserByTrueName(String trueName) {
		return userDao.getUserByTrueName(trueName);
	}

	public void addUser(User user) {
		userDao.addUser(user);
		
	}

	public void deleteUserById(Integer userId) {
		userDao.deleteUserById(userId);		
	}

//	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
//	public Set<Solution> getCreateSolutions(String loginName) {
//		return userDao.getUserByLoginName(loginName).getCreateSolutions();
//	}
//
//	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
//	public Set<Solution> getAlterSolutions(String loginName) {
//		//输出的时候正式加载
////		System.out.println(userDao.getUserByLoginName(loginName).getAlterSolutions());
//		return userDao.getUserByLoginName(loginName).getAlterSolutions();
//	}

	public List<User> getUsersByPage(int pageSize, int currentPage) {
		return userDao.getUsersByPage(pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Long getTotalUserNum() {
		return userDao.getTotalUserNum();
	}

	public void updateUser(User user) {
		userDao.updateUser(user);		
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Boolean getLogFlag(String userName) {
		return userDao.getLogFlag(userName);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public Set<Permission> getPermissionsByUserName(String userName) {
		return userDao.getUserByLoginName(userName).getRole().getPermissions();
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public List<User> getUsersByContentByPage(String searchContent, int pageSize, int currentPage) {
		return userDao.getUsersByContentByPage(searchContent, pageSize, currentPage);
	}

	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public BigInteger getUserNumByContentByPage(String searchContent) {
		return userDao.getUserNumByContentByPage(searchContent);
	}

	@Override
	public void deleteUsers(List<Integer> userIds) {
		for(int userId: userIds){
			userDao.deleteUserById(userId);
		}		
	}

	@Override
	public void changeUsersLogflag(List<Integer> userIds) {
		for(int userId: userIds){
			User user = userDao.getUserById(userId);
			
			if(user != null){
				user.setLogFlag(!user.getLogFlag());
				userDao.updateUser(user);
			}
		}
		
	}

}
