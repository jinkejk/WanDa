package com.wanda.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;

import com.wanda.beans.Permission;
import com.wanda.beans.Solution;
import com.wanda.beans.User;

/**
 * 用户数据操作服务类
 * @author jinkejk
 *
 */
public interface UserService {
	
	 /**
     * 根据登录名获取用户 
     */
    User getUserByLoginName(String loginName);
	
	/**
	 * 根据id获取用户
	 * @param userId
	 */
    User getUserById(Integer userId);
    
    /**
     * 根据真实姓名获取用户
     */
    Set<User> getUserByTrueName(String trueName);
    
    /**
     * 添加用户
     */
    void addUser(User user);
    
    /**
     * 根据id删除用户
     */
    void deleteUserById(Integer userId);

    /**
     * 根据用户登录名获取创建的文章
     */
//    Set<Solution> getCreateSolutions(String loginName);
    
    /**
     * 根据用户登录名获取修改的文章
     */
//    Set<Solution> getAlterSolutions(String loginName);
    
    /**
     * 分页搜索用户
     * @param pageSize
     * @param currentPage
     * @return
     */
    List<User> getUsersByPage(int pageSize, int currentPage);
    
    /**
     * 按照内容搜索用户
     */
    List<User> getUsersByContentByPage(String searchContent, int pageSize, int currentPage);
    
    /**
     * 按照内容搜索用户数量
     */
    BigInteger getUserNumByContentByPage(String searchContent);
    
    /**
     * 获得用户的总数
     */
    Long getTotalUserNum();
    
    /**
     * 更新用户
     */
    void updateUser(User user);
    
    /**
     * 根据用户名获取用户登陆标志
     */
    Boolean getLogFlag(String userName);
    
    /**
     * 根据用户名获取所有权限
     */
    Set<Permission> getPermissionsByUserName(String userName);
    
    /**
     * 批量删除
     */
    void deleteUsers(List<Integer> userIds);
    
    /**
     * 批量修改用户的登陆flag
     */
    void changeUsersLogflag(List<Integer> userIds);
    
}
