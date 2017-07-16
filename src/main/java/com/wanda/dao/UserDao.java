package com.wanda.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import com.wanda.beans.User;

/**
 * 用户类数据库操作
 * @author jinkejk
 *
 */
public interface UserDao {
	
	/**
	 * 根据id获取用户
	 * @param userId
	 */
    User getUserById(Integer userId);
    
    /**
     * 根据登录名获取用户 
     */
    User getUserByLoginName(String loginName);
    
    /**
     * 根据真实姓名获取用户,不唯一
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
     * 分页获取所有用户
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
}
