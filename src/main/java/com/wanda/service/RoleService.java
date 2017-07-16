package com.wanda.service;

import java.util.Set;

import com.wanda.beans.Role;

public interface RoleService {
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
     * 根据权限ID添加权限
     */
    void addPermissionById(Integer permissionId, Integer roleId);
    
    /**
     *获取所有角色
     */
    Set<Role> getAllRoles();
}
