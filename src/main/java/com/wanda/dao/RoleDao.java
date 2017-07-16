package com.wanda.dao;

import java.util.Set;

import com.wanda.beans.Role;

/**
 * 角色数据库操作
 * @author jinkejk
 *
 */
public interface RoleDao {
	/**
	 * 根据id获取角色
	 * @param roleId
	 */
	Role getRoleById(Integer roleId);
    
    /**
     * 根据角色名获取角色
     */
	Role getRoleByRoleName(String roleName);
    
    /**
     * 添加角色
     */
    void addRole(Role role);
    
    /**
     * 根据id删除角色
     */
    void deleteRoleById(Integer roleId);
    
    /**
     * 跟新角色
     */
    void updateRole(Role role);
    
    /**
     *获取所有角色
     */
    Set<Role> getAllRoles();
    
}
